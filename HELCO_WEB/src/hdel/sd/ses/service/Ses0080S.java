package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0080D;
import hdel.sd.ses.domain.Ses0080;
import hdel.sd.ses.domain.Ses0080ParamVO;
import hdel.sd.ses.domain.Ses0110;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0080S extends SrmService {

	private Ses0080D Ses0080Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0080Dao = sqlSession.getMapper(Ses0080D.class);
	}
	
	public List<Ses0080> searchHeader(Ses0080ParamVO param) {
		return Ses0080Dao.selectListHeader(param);
	}

	public List<Ses0080> searchDetail(Ses0080ParamVO param) {
		return Ses0080Dao.selectListDetail(param);
	}

	// �������ο�û ����
	public void receipt(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list");  	// ���� ���,����,���� �������
 	
		try
		{  

			//------------------------------------------------------------------------
			// ���� ����
			//------------------------------------------------------------------------
			Ses0080 	param 		= new Ses0080();						// ���� �Ķ����
			
			//------------------------------------------------------------------------
			// DAO ����
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// �������ο�û ���� ó�� START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				System.out.print("\n@@@ zzzzzzzzzz   ===>"+DatasetUtility.getString(ds_list, irow, "ZRTRSN") 	       +"\n");

				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				//param.setZrtrsn(DatasetUtility.getString(ds_list, irow, "ZRTRSN"));

				receiptZSDT1057(param); 
				updateFlagConfirmZSDT1046(param);
			} // end of for
		 
		}  catch (Exception e) { 
		}
	}   

	// ����Header(ZSDT1057) ����
	public void receiptZSDT1057(Ses0080 param) {
		Ses0080Dao.receiptZSDT1057(param);
	}  

	public void updateFlagConfirmZSDT1046(Ses0080 param) {
		Ses0080Dao.updateFlagConfirmZSDT1046(param);
	}
	
	// �������ο�û ����
	public void receiptcancel(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list");  	// ���� ���,����,���� �������
		
		try
		{  

			//------------------------------------------------------------------------
			// ���� ����
			//------------------------------------------------------------------------
			Ses0080 	param 		= new Ses0080();						// ���� ���� �Ķ����
			
			//------------------------------------------------------------------------
			// DAO ����
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// �������ο�û ���� ó�� START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				//param.setZrtrsn(DatasetUtility.getString(ds_list, irow, "ZRTRSN"));

				receiptcancelZSDT1057(param); 
				updateFlagCancelZSDT1046(param);
			} // end of for
			
		}  catch (Exception e) { 
			//TransactionManager.rollback(); 
		}
	}   

	// ����Header(ZSDT1057) ����
	public void receiptcancelZSDT1057(Ses0080 param) {
		
		Ses0080Dao.receiptcancelZSDT1057(param);
	}  

	public void updateFlagCancelZSDT1046(Ses0080 param) {
		
		Ses0080Dao.updateFlagCancelZSDT1046(param);
	} 
}
