package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0172D;
import hdel.sd.ses.domain.Ses0035;
import hdel.sd.ses.domain.Ses0172;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0172S extends SrmService {

	private Ses0172D Ses0172Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0172Dao = sqlSession.getMapper(Ses0172D.class);
	}
	
	// 기술검토결과등록
	public void save(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		Dataset ds_list = paramVO.getDataset("ds_list");
		// INPUT DATASET GET 
		
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0172 	param 		= new Ses0172();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getInt(ds_list, irow, "QTSER"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setCuser(DatasetUtility.getString(ds_list, irow, "CUSER"));


				saveZSDT1054(param); 
				updateZSDT1054(param);
			}
			
		 
		}  catch (Exception e) { 
		}
	}   

	// 기술검토요청/결과(ZSDT1058) 변경
	public void saveZSDT1054(Ses0172 param) {
		Ses0172Dao.saveZSDT1054(param);
	}  
	public void updateZSDT1054(Ses0172 param) {
		Ses0172Dao.updateZSDT1054(param);
	}

}
