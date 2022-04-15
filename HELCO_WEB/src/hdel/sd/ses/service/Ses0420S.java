package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0420D;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.domain.Ses0420;
import hdel.sd.ses.domain.Ses0420ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0420S extends SrmService {

	private Ses0420D Ses0420Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0420Dao = sqlSession.getMapper(Ses0420D.class);
	}
	
	public List<Ses0420> getListHeader(Ses0420ParamVO param) {
		
		return Ses0420Dao.selectList(param);
		
	}
	
	public void saveAddData(MipParameterVO paramVO, Model model,SqlSession session) throws Exception{ //(String sMandt, String sZrqseq, String sLfindat ){
		Dataset dsList = paramVO.getDataset("ds_list");

		try {	
			/*
			String mandt      = "";
			String zrqseq     = "";
			String lfindat    = "";
		
			mandt   = paramVO.getVariable("G_MANDT");
			for (int i = 0; i < dsList.getRowCount(); i++) {							
				zrqseq  = DatasetUtility.getString(dsList, i, "ZRQSEQ");
				lfindat  = DatasetUtility.getString(dsList, i, "LFINDAT");				
				savelfindat(mandt, zrqseq, lfindat);				
			}		
			*/
			Ses0420 param  = new Ses0420();
			for (int i = 0; i < dsList.getRowCount(); i++) {
			param.setMandt(paramVO.getVariable("G_MANDT"));	 // SAP CLIENT
			param.setZrqseq(DatasetUtility.getString(dsList, i, "ZRQSEQ"));
			param.setLfindat(DatasetUtility.getString(dsList, i, "LFINDAT"));
			param.setAppdodat(DatasetUtility.getString(dsList, i, "APPDODAT"));
			param.setAppdwdat(DatasetUtility.getString(dsList, i, "APPDWDAT"));			
			param.setZdesc(DatasetUtility.getString(dsList, i, "ZDESC"));
			param.setApproval(DatasetUtility.getString(dsList, i, "APPROVAL"));
			Ses0420Dao.saveAdditionalData(param);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
