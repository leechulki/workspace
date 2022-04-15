package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0150D;
import hdel.sd.ses.domain.Ses0150;
import hdel.sd.ses.domain.Ses0150ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0150S extends SrmService {

	private Ses0150D Ses0150Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0150Dao = sqlSession.getMapper(Ses0150D.class);
	}
	
	public List<Ses0150> searchHeader(Ses0150ParamVO param) {
		return Ses0150Dao.selectListHeader(param);
	}

	public List<Ses0150> searchDetail(Ses0150ParamVO param) {
		return Ses0150Dao.selectListDetail(param);
	}

	// ��������û ����
	public void receipt(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list");  	// ���� ���,����,���� �������
 	
		try
		{  

			//------------------------------------------------------------------------
			// ���� ����
			//------------------------------------------------------------------------
			Ses0150 	param 		= new Ses0150();						// ���� �Ķ����
			
			//------------------------------------------------------------------------
			// DAO ����
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// ��������û ���� ó�� START
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

	// ����Header(ZSDT1058) ����
	public void receiptZSDT1058(Ses0150 param) {
		Ses0150Dao.receiptZSDT1058(param);
	}  

	
	// ��������û ����
	public void receiptcancel(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list");  	// ���� ���,����,���� �������
		
		try
		{  

			//------------------------------------------------------------------------
			// ���� ����
			//------------------------------------------------------------------------
			Ses0150 	param 		= new Ses0150();						// ���� ���� �Ķ����
			
			//------------------------------------------------------------------------
			// DAO ����
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// ����������� ó�� START
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

	// ����Header(ZSDT1058) ����
	public void receiptcancelZSDT1058(Ses0150 param) {
		
		Ses0150Dao.receiptcancelZSDT1058(param);
	}  

}
