package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0160D;
import hdel.sd.ses.domain.Ses0160;
import hdel.sd.ses.domain.Ses0160ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0160S extends SrmService {

	private Ses0160D Ses0160Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0160Dao = sqlSession.getMapper(Ses0160D.class);
	}
	
	public List<Ses0160> searchHeader(Ses0160ParamVO param) {
		return Ses0160Dao.selectListHeader(param);
	}

	public List<Ses0160> searchDetail(Ses0160ParamVO param) {
		return Ses0160Dao.selectListDetail(param);
	}

	// 기술검토요청 접수
	public void receipt(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
 	
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0160 	param 		= new Ses0160();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// 기술검토요청 접수 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));

				receiptZSDT1058(param); 
 
			} // end of for
		 
		}  catch (Exception e) { 
		}
	}   

	// 견적Header(ZSDT1058) 변경
	public void receiptZSDT1058(Ses0160 param) {
		Ses0160Dao.receiptZSDT1058(param);
	}  

	
	// 기술검토요청 접수
	public void receiptcancel(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
		
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0160 	param 		= new Ses0160();						// 수주 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// 기술검토접수 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));

				receiptcancelZSDT1058(param); 
				
			} // end of for
			
		}  catch (Exception e) { 
			//TransactionManager.rollback(); 
		}
	}   

	// 견적Header(ZSDT1058) 변경
	public void receiptcancelZSDT1058(Ses0160 param) {
		
		Ses0160Dao.receiptcancelZSDT1058(param);
	}  

}
