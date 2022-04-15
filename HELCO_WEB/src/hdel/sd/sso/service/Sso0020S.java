package hdel.sd.sso.service;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.DatasetUtil;
import com.sap.domain.Vbeln;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.ZSDT0195D;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT0195;
import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0020D;
import hdel.sd.sso.domain.Sso0020ParamVO;
import hdel.sd.sso.domain.Sso0020VO;
import hdel.sd.sso.domain.Sso0020Partner;
import hdel.sd.sso.domain.ZSDT1058;
import hdel.sd.sso.domain.ZSDT0090;

@Service
public class Sso0020S extends SrmService {

	private Sso0020D dao;
	private ZSDT0195D zsdt0195d;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Sso0020D.class);
		zsdt0195d = sqlSession.getMapper(hdel.lib.dao.ZSDT0195D.class);
	}
	
	public List<Sso0020VO> selectAutoLP(Sso0020ParamVO paramV) {
		return dao.SelectAutoLP(paramV);
	}
	public List<Sso0020VO> selectSpecE(Sso0020ParamVO paramV) {
		return dao.SelectSpecE(paramV);		
	}
	public void configEmailInfo(MipParameterVO paramVO, MipResultVO resultVO) throws Exception {
		String mandt	= paramVO.getVariable("G_MANDT");
		String vbeln	= paramVO.getVariable("vbeln");

		Dataset dsOutput = paramVO.getDataset("ds_output");
		Dataset dsPartner = paramVO.getDataset("ds_partner");
		Dataset dsMailList = paramVO.getDataset("ds_mailList");

		List<Sso0020Partner> lstSso0020partner = dao.getPartner(mandt, new Vbeln(vbeln));
		List<ZSDT0195> lstZsdt0195 = zsdt0195d.select(mandt, "", "", "");

		dsPartner.deleteAll();
		DatasetUtil.moveListToDs(lstSso0020partner, dsPartner);

		dsMailList.deleteAll();
		for(int i=0; i< dsOutput.getRowCount(); i++) {
			if(dsOutput.getColumnAsInteger(i, "SEQ") < 2)
				continue;

			String atnam = dsOutput.getColumnAsString(i, "CHARACTERISTIC");
			for(ZSDT0195 zsdt0195 : lstZsdt0195) {
				if(atnam.equals(zsdt0195.getAtnam())) {
					dsMailList.appendRow();
					dsMailList.setColumn(dsMailList.getRowCount()-1, "hogi", dsOutput.getColumnAsString(i, "HOGI"));
					dsMailList.setColumn(dsMailList.getRowCount()-1, "atnam", atnam);
					dsMailList.setColumn(dsMailList.getRowCount()-1, "atbez", dsOutput.getColumnAsString(i, "ATBEZ"));
					dsMailList.setColumn(dsMailList.getRowCount()-1, "atwrt", dsOutput.getColumnAsString(i, "VALUE"));
					lstZsdt0195.remove(zsdt0195);
					break;
				}
			}
		}
		if(dsMailList.getRowCount() >= 7)
			dsMailList.deleteAll();

		resultVO.addDataset(dsPartner);
		resultVO.addDataset(dsMailList);
	}
	public Dataset checkTransportable(String mandt, Dataset dsInput, Dataset dsError) {
		List<ZSDT1058> arrZsdt1058 = dao.select1058Info(mandt, dsInput.getColumnAsString(0, "I_FR_SO"));
		ZSDT1058 z1058 = arrZsdt1058.get(0);

		Dataset dsTransCheck = new Dataset();
		dsTransCheck.addColumn("transportable", ColumnInfo.COLUMN_TYPE_INT, 1);

		dsTransCheck.appendRow();
		if(z1058.getKnrz2().substring(0, 1).equals("A")) {
			dsTransCheck.setColumn(0, "transportable", 0);

			if(z1058.getZrqflg().equals("A") && z1058.getLp_is().equals("Y") && !z1058.getLp_chn().equals("Y") && !z1058.getNonstd().equals("Y"))
				dsTransCheck.setColumn(0, "transportable", 1);

			if(z1058.getZrqflg().equals("B") && z1058.getLp_is().equals("Y") && z1058.getLp_chn().equals("Y") && !z1058.getNonstd().equals("Y"))
				dsTransCheck.setColumn(0, "transportable", 1);

			if(z1058.getZrqflg().equals("B") && !z1058.getLp_is().equals("Y") && !z1058.getLp_chn().equals("Y") && z1058.getNonstd().equals("Y"))
				dsTransCheck.setColumn(0, "transportable", 1);

		} else {
			dsTransCheck.setColumn(0, "transportable", 1);
		}

		if(dsTransCheck.getColumnAsInteger(0, "transportable") == 0)
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", "∞Ì∞¥Ω¬¿Œ ¡¢ºˆ¿œ »Æ¿Œ.", "Y", "Y");

		return dsTransCheck;
	}
	public Dataset checkChnsoFinl(String mandt, Dataset dsInput, Dataset dsError) {
		List<ZSDT0090> arrZsdt0090 = dao.selectChnsoFinl(mandt, dsInput.getColumnAsString(0, "I_FR_SO"));
		
		Dataset dsFinlCheck = new Dataset();
		dsFinlCheck.addColumn("finl", ColumnInfo.COLUMN_TYPE_STRING, 1);
		
		dsFinlCheck.appendRow();
/*		
		if (arrZsdt0090.isEmpty()) {
			dsFinlCheck.setColumn(0, "finl", "");
		} else {
			ZSDT0090 z0090 = arrZsdt0090.get(0);
			dsFinlCheck.setColumn(0, "finl", z0090.getFINL());
		}
*/	
		ZSDT0090 z0090 = arrZsdt0090.get(0);
		dsFinlCheck.setColumn(0, "finl", z0090.getFINL());
		
		return dsFinlCheck;
	}
	public Integer getLast90Seq(String mandt, String vbeln) {
		Integer last90Seq = dao.getLast90Seq(mandt, vbeln); 
		return last90Seq == null ? 0 : last90Seq;
	}
	
	public Integer getGspCnt(String mandt, String vbeln) {
		Integer gspCnt = dao.getGspCnt(mandt, vbeln); 
		return gspCnt == null ? 0 : gspCnt;		 
	}
		
}
