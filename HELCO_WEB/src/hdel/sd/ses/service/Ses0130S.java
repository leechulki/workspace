package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0130D;
import hdel.sd.ses.domain.Ses0130;
import hdel.sd.ses.domain.Ses0130;
import hdel.sd.ses.domain.Ses0130ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0130S extends SrmService {

	private final String ZAPFLG_APPROVAL  = "30";
	private final String ZAPFLG_REJECT    = "40";

	private final String ZPRGFLG_APPROVAL = "32";
	private final String ZPRGFLG_REJECT   = "33";

	private Ses0130D Ses0130Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0130Dao = sqlSession.getMapper(Ses0130D.class);
	}
	
	public List<Ses0130> searchHeader(Ses0130ParamVO param) {
		return Ses0130Dao.selectListHeader(param);
	}

	public List<Ses0130> searchDetail(Ses0130ParamVO param) {
		return Ses0130Dao.selectListDetail(param);
	}

	// 원가의뢰승인
	public void approval(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list = paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
 	
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0130 param = new Ses0130();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// 원가의뢰승인요청 접수 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				param.setUSER_ID( paramVO.getVariable("G_USER_ID") );
				param.setZapflg( ZAPFLG_APPROVAL );
				param.setZprgflg( ZPRGFLG_APPROVAL );

				updateZSDT1057(param); 
				updateFlagZSDT1046(param);
 
			} // end of for
		 
		}  catch (Exception e) {
			throw e;
		}
	}   

	// 원가의뢰반려
	public void reject(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list = paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
		
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0130 param = new Ses0130();						// 수주 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// 원가의뢰승인요청 접수 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				param.setZrtrsn(DatasetUtility.getString(ds_list, irow, "ZRTRSN"));
				param.setUSER_ID( paramVO.getVariable("G_USER_ID") );
				param.setZapflg( ZAPFLG_REJECT );
				param.setZprgflg( ZPRGFLG_REJECT );

				updateZSDT1057(param); 
				updateFlagZSDT1046(param);
				
			} // end of for
			
		}  catch (Exception e) { 
			//TransactionManager.rollback(); 
			throw e;
		}
	}   

	public void updateZSDT1057(Ses0130 param) {
		
		Ses0130Dao.updateZSDT1057(param);
	}  

	public void updateFlagZSDT1046(Ses0130 param) {
		
		Ses0130Dao.updateFlagZSDT1046(param);
	} 
}
