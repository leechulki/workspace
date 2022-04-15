package hdel.sd.set.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.DatasetUtil;
import com.sap.domain.Datum;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.ZSDT1087D;
import hdel.lib.dao.ZSDT1106D;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.ZSDT1087;
import hdel.lib.domain.ZSDT1106;
import hdel.lib.service.SrmService;
import hdel.sd.set.dao.Set0010D;
import hdel.sd.set.domain.Set0010;

@Service
public class Set0010S extends SrmService {
	Set0010D set0010d;
	ZSDT1087D zsdt1087d;
	ZSDT1106D zsdt1106d;

	public void createDao(SqlSession sqlSession) {
		set0010d = sqlSession.getMapper(Set0010D.class);
		zsdt1087d = sqlSession.getMapper(hdel.lib.dao.ZSDT1087D.class);
		zsdt1106d = sqlSession.getMapper(hdel.lib.dao.ZSDT1106D.class);
	}

	public Dataset searchCostSection(MipParameterVO paramVO) {
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsCrtsc = paramVO.getDataset("ds_crtsc");

		List<ZSDT1087> lstCrtsc = new ArrayList<ZSDT1087>();

		lstCrtsc = zsdt1087d.select(paramVO.getVariable("G_MANDT"), new Datum(dsCond.getColumnAsString(0, "bsdat")));
		DatasetUtil.moveListToDs(lstCrtsc, dsCrtsc);

		return dsCrtsc;
	}

	public List<Set0010> searchMatrixMap(MipParameterVO paramVO) {
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsSummary = paramVO.getDataset("ds_summary");
		Dataset dsList = paramVO.getDataset("ds_list");
		Dataset dsCrtsc = paramVO.getDataset("ds_crtsc");

		List<ZSDT1087> lstCrtsc = new ArrayList<ZSDT1087>();
		List<Set0010> lstMatrixMap = new ArrayList<Set0010>();
		ZSDT1106 zsdt1106 = zsdt1106d.select(paramVO.getVariable("G_MANDT"), dsCond.getColumnAsString(0, "bsdat").substring(0, 6), dsCond.getColumnAsString(0, "sman"));
		zsdt1106 = zsdt1106==null? new ZSDT1106() : zsdt1106;

		lstCrtsc = zsdt1087d.select(paramVO.getVariable("G_MANDT"), new Datum(dsCond.getColumnAsString(0, "bsdat")));
		DatasetUtil.moveListToDs(lstCrtsc, dsCrtsc);
		dsSummary.appendRow();
		DatasetUtil.moveObjToDsRow(zsdt1106, dsSummary, 0);

		dsList.addColumn("srtsc",ColumnInfo.COLUMN_TYPE_DECIMAL,8);
		dsList.addColumn("srtfr",ColumnInfo.COLUMN_TYPE_DECIMAL,6);
		dsList.addColumn("srtto",ColumnInfo.COLUMN_TYPE_DECIMAL,6);
		dsList.addColumn("srtdesc",ColumnInfo.COLUMN_TYPE_STRING,6);
		dsList.addColumn("crtfr",ColumnInfo.COLUMN_TYPE_DECIMAL,6);
		dsList.addColumn("crtto",ColumnInfo.COLUMN_TYPE_DECIMAL,6);
		dsList.addColumn("inctv",ColumnInfo.COLUMN_TYPE_DECIMAL,6);
		for(ZSDT1087 crtsc : lstCrtsc) {
			dsList.addColumn("crtsc"+crtsc.getCrtsc(),ColumnInfo.COLUMN_TYPE_DECIMAL,8);
		}
		dsList.addColumn("cal_srtsc",ColumnInfo.COLUMN_TYPE_INT,6);
		dsList.addColumn("cal_crtsc",ColumnInfo.COLUMN_TYPE_INT,6);

		lstMatrixMap = set0010d.searchMatrixMap(paramVO.getVariable("G_MANDT"), new Datum(dsCond.getColumnAsString(0, "bsdat")), dsCond.getColumnAsString(0, "sprno"));
		for(Set0010 matrixMap : lstMatrixMap) {
			dsList.appendRow();
			DatasetUtil.moveObjToDsRow(matrixMap, dsList, dsList.getRowCount() - 1);
			List<Set0010> lstCrtSection = matrixMap.getLstCrtSection();
			for(Set0010 crtsc : lstCrtSection) {
				dsList.setColumn(dsList.getRowCount() - 1, "crtsc"+crtsc.getCrtsc(), crtsc.getInctv()==null?0:crtsc.getInctv().doubleValue());
				dsList.setColumn(dsList.getRowCount() - 1, "cal_srtsc", zsdt1106.getSrtsc());
				dsList.setColumn(dsList.getRowCount() - 1, "cal_crtsc", zsdt1106.getCrtsc());
			}
		}
		
		return lstMatrixMap;
	}
}