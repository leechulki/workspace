package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0090D;
import hdel.sd.ses.domain.Ses0090;
import hdel.sd.ses.domain.Ses0090ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0090S extends SrmService {
	private final String ZRQFLG_1 = "1";	// 견적요청 - 국내
	private final String ZRQFLG_3 = "3";	// 견적원가 요청 - 해외대리점

	private final String ZAPFLG_APPROVAL  = "30";	// 승인
	private final String ZAPFLG_REJECT    = "40";	// 반려

	private final String ZPRGFLG_APPROVAL = "23";
	private final String ZPRGFLG_REJECT   = "24";

	// 해외대리점용
	private final String ZPRGFLG_AGENT_61 = "61";	// 승인시 견적상태 처리
	private final String ZPRGFLG_AGENT_62 = "62";	// 반려시 견적상태 처리


	private Ses0090D Ses0090Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0090Dao = sqlSession.getMapper(Ses0090D.class);
	}
	
	public List<Ses0090> searchHeader(Ses0090ParamVO param) {
		return Ses0090Dao.selectListHeader(param);
	}

	public List<Ses0090> searchDetail(Ses0090ParamVO param) {
		return Ses0090Dao.selectListDetail(param);
	}

	// 해외대리점 Header 조회
	public List<Ses0090> searchAgentHeader(Ses0090ParamVO param) {
		return Ses0090Dao.selectListAgentHeader(param);
	}


	public List<Ses0090> searchAgentDetail(Ses0090ParamVO param) {
		return Ses0090Dao.selectListAgentDetail(param);
	}

	// 해외대리점 정보 조회
	public List<Ses0090> searchKunnr(Ses0090ParamVO param) {
		return Ses0090Dao.selectKunnr(param);
	}

	// SPEC정보 조회
	public List<Ses0090> searchSpecList(Ses0090ParamVO param) {
		return Ses0090Dao.searchSpecList(param);
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
			Ses0090 	param 		= new Ses0090();						// 저장 파라메터
			
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
				param.setZrqflg(ZRQFLG_1);
				param.setZapflg( ZAPFLG_APPROVAL );
				param.setZprgflg( ZPRGFLG_APPROVAL );
				param.setUSER_ID(paramVO.getVariable("G_USER_ID"));

				updateZSDT1057(param); 
				updateFlagZSDT1046(param);
			} // end of for
		 
		}  catch (Exception e) { 
		}
	}   

	// (ZSDT1057) 변경
	public void updateZSDT1057(Ses0090 param) {
		Ses0090Dao.updateZSDT1057(param);
	}  

	public void updateFlagZSDT1046(Ses0090 param) {
		Ses0090Dao.updateFlagZSDT1046(param);
	}

	public void updateFlagZSDT1001(Ses0090 param) {
		Ses0090Dao.updateFlagZSDT1001(param);
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
			Ses0090 	param 		= new Ses0090();						// 수주 저장 파라메터
			
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
				param.setZrtrsn(DatasetUtility.getString(ds_list, irow, "ZRTRSN"));
				param.setZrqflg(ZRQFLG_1);
				param.setZapflg( ZAPFLG_REJECT );
				param.setZprgflg( ZPRGFLG_REJECT );
				param.setUSER_ID(paramVO.getVariable("G_USER_ID"));

				updateZSDT1057(param); 
				updateFlagZSDT1046(param);
			} // end of for
			
		}  catch (Exception e) { 
			//TransactionManager.rollback(); 
		}
	}


	// 견적원가승인 - 해외대리점
	public void agentApproval(MipParameterVO paramVO) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list = paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
 	
		try
		{
			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0090 param = new Ses0090();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// 견적승인요청 접수 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));

				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				param.setZrqflg(ZRQFLG_3);
				param.setZapflg( ZAPFLG_APPROVAL );
				param.setZprgflg( ZPRGFLG_AGENT_61 );
				param.setUSER_ID(paramVO.getVariable("G_USER_ID"));

				updateZSDT1057(param); 
				updateFlagZSDT1046(param);
				updateFlagZSDT1001(param);			// 수주계획상태 변경 승인 61
			} // end of for
		 
		}  catch (Exception e) { 
			throw e;
		}
	}

	// 견적원가반려 - 해외대리점
	public void agentReject(MipParameterVO paramVO) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list = paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
		
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0090 param = new Ses0090();						// 수주 저장 파라메터
			
			//------------------------------------------------------------------------
			// 견적승인요청 접수 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				param.setZrtrsn(DatasetUtility.getString(ds_list, irow, "ZRTRSN"));
				param.setZrqflg(ZRQFLG_3);
				param.setZapflg( ZAPFLG_REJECT );
				param.setZprgflg( ZPRGFLG_AGENT_62 );
				param.setUSER_ID(paramVO.getVariable("G_USER_ID"));

				updateZSDT1057(param); 
				updateFlagZSDT1046(param);
				updateFlagZSDT1001(param);			// 수주계획상태 변경 -> 반려 62
			} // end of for
			
		}  catch (Exception e) { 
			//TransactionManager.rollback(); 
		}
	}
}