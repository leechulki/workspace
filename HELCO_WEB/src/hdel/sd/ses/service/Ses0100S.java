package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0100D;
import hdel.sd.ses.domain.Ses0100;
import hdel.sd.ses.domain.Ses0100ParamVO;
import hdel.sd.ses.domain.Ses0110;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0100S extends SrmService {

	private Ses0100D Ses0100Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0100Dao = sqlSession.getMapper(Ses0100D.class);
	}
	
	public List<Ses0100> searchHeader(Ses0100ParamVO param) {
		return Ses0100Dao.selectListHeader(param);
	}

	public List<Ses0100> searchDetail(Ses0100ParamVO param) {
		return Ses0100Dao.selectListDetail(param);
	}

	// 견적승인
	public void approval(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
 	
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0100 	param 		= new Ses0100();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// 견적승인요청 접수 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));

				approvalZSDT1057(param); 
				updateFlagConfirmZSDT1046(param);
			} // end of for
		 
		}  catch (Exception e) { 
		}
	}   

	// 견적Header(ZSDT1057) 변경
	public void approvalZSDT1057(Ses0100 param) {
		Ses0100Dao.approvalZSDT1057(param);
	}  

	public void updateFlagConfirmZSDT1046(Ses0100 param) {
		Ses0100Dao.updateFlagConfirmZSDT1046(param);
	}
	
	// 견적반려
	public void reject(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
		
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0100 	param 		= new Ses0100();						// 수주 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// 견적승인요청 접수 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));

				rejectZSDT1057(param); 
				updateFlagCancelZSDT1046(param);
			} // end of for
			
		}  catch (Exception e) { 
			//TransactionManager.rollback(); 
		}
	}   

	// 견적Header(ZSDT1057) 변경
	public void rejectZSDT1057(Ses0100 param) {
		
		Ses0100Dao.rejectZSDT1057(param);
	}  
	
	public void updateFlagCancelZSDT1046(Ses0100 param) {
		
		Ses0100Dao.updateFlagCancelZSDT1046(param);
	}
}
