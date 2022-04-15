package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0070D;
import hdel.sd.ses.domain.Ses0070;
import hdel.sd.ses.domain.Ses0070ParamVO;
import hdel.sd.ses.domain.Ses0110;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0070S extends SrmService {

	private final String ZAPFLG_RECEIPT  = "20";
	private final String ZPRGFLG_RECEIPT = "22";
	private final String ZAPFLG_CANCEL   = "10";
	private final String ZPRGFLG_CANCEL  = "21";

	private Ses0070D Ses0070Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0070Dao = sqlSession.getMapper(Ses0070D.class);
	}
	
	public List<Ses0070> searchHeader(Ses0070ParamVO param) {
		return Ses0070Dao.selectListHeader(param);
	}

	public List<Ses0070> searchDetail(Ses0070ParamVO param) {
		return Ses0070Dao.selectListDetail(param);
	}

	// 견적승인요청 접수
	public void receipt(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list = paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
 	
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0070 	param 		= new Ses0070();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// 견적승인요청 접수 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				System.out.print("\n@@@ zzzzzzzzzz   ===>"+DatasetUtility.getString(ds_list, irow, "ZRTRSN") 	       +"\n");

				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				param.setUSER_ID(paramVO.getVariable("G_USER_ID"));
				param.setZapflg( ZAPFLG_RECEIPT );
				param.setZprgflg( ZPRGFLG_RECEIPT );

				updateZSDT1057(param); 
				updateFlagZSDT1046(param);
			} // end of for
		 
		}  catch (Exception e) {
			throw e;
		}
	}   

	// 견적승인요청 접수
	public void receiptcancel(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
		
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0070 	param 		= new Ses0070();						// 수주 저장 파라메터
			
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
				param.setUSER_ID(paramVO.getVariable("G_USER_ID"));
				param.setZapflg( ZAPFLG_CANCEL );
				param.setZprgflg( ZPRGFLG_CANCEL );

				updateZSDT1057(param); 
				updateFlagZSDT1046(param);
			} // end of for
			
		}  catch (Exception e) {
			throw e;
			//TransactionManager.rollback(); 
		}
	}   

	// 견적Header(ZSDT1057) 변경
	public void updateZSDT1057(Ses0070 param) {
		
		Ses0070Dao.updateZSDT1057(param);
	}  

	public void updateFlagZSDT1046(Ses0070 param) {
		
		Ses0070Dao.updateFlagZSDT1046(param);
	} 
}
