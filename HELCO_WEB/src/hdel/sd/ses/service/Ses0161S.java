package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0161D;
import hdel.sd.ses.domain.Ses0161;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0161S extends SrmService {

	private Ses0161D Ses0161Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0161Dao = sqlSession.getMapper(Ses0161D.class);
	}
	
	// 기술검토결과등록
	public void save(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_header");  	// 수주 등록,수정,삭제 대상정보
		
		Dataset ds_file 		= paramVO.getDataset("ds_header_file");     	// 수주 등록,수정,삭제 대상정보	
 	
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0161 	param 		= new Ses0161();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// 기술검토결과등록 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setZrsrlt(DatasetUtility.getString(ds_list, irow, "ZRSRLT"));
				
				//param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				//param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				//param.setSonnr(DatasetUtility.getString(ds_list, irow, "SONNR"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				
			
				param.setUuser(paramVO.getVariable("G_USER_ID"));				
				

				saveZSDT1058(param); 
 
			} // end of for
			
			for( int irow = 0; irow < ds_file.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_file, irow, "MANDT"));
	
				param.setZrqseq(DatasetUtility.getString(ds_file, irow, "ZRQSEQ"));
				
				param.setZattseq(DatasetUtility.getString(ds_file, irow, "ZATTSEQ"));
				param.setZattpath(DatasetUtility.getString(ds_file, irow, "ZATTPATH"));
				param.setZattfn(DatasetUtility.getString(ds_file, irow, "ZATTFN"));				
				
				param.setCuser(paramVO.getVariable("G_USER_ID"));

				saveZSDT1059(param); 
 
			} // end of for						
			
		 
		}  catch (Exception e) { 
		}
	}   

	// 기술검토요청/결과(ZSDT1058) 변경
	public void saveZSDT1058(Ses0161 param) {
		Ses0161Dao.saveZSDT1058(param);
	} 
	
	// 기술검토요청/결과(ZSDT1058) 변경
	public void saveZSDT1059(Ses0161 param) {
		Ses0161Dao.saveZSDT1059(param);
	}  
	

}
