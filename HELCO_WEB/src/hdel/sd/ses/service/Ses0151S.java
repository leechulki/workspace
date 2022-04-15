package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0151D;
import hdel.sd.ses.domain.Ses0150;
import hdel.sd.ses.domain.Ses0150ParamVO;
import hdel.sd.ses.domain.Ses0151;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0151S extends SrmService {

	private Ses0151D Ses0151Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0151Dao = sqlSession.getMapper(Ses0151D.class);
	}
	
	// ������������
	public void save(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_header");  	// ���� ���,����,���� �������
		
		Dataset ds_file 		= paramVO.getDataset("ds_header_file");     	// ���� ���,����,���� �������	
 	
		try
		{  

			//------------------------------------------------------------------------
			// ���� ����
			//------------------------------------------------------------------------
			Ses0151 	param 		= new Ses0151();						// ���� �Ķ����
			
			//------------------------------------------------------------------------
			// DAO ����
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// ������������ ó�� START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
	
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));
				param.setSonnr(DatasetUtility.getString(ds_list, irow, "SONNR"));
				
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				
				
				param.setCuser(paramVO.getVariable("G_USER_ID"));

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

	// ��������û/���(ZSDT1058) ����
	public void saveZSDT1058(Ses0151 param) {
		Ses0151Dao.saveZSDT1058(param);
	}  
	
	// �������÷��(ZSDT1059) ����
	public void saveZSDT1059(Ses0151 param) {
		Ses0151Dao.saveZSDT1059(param);
	}  	
	
	public List<Ses0151> searchDetail(Ses0150ParamVO param) {
		return Ses0151Dao.selectListDetail(param);
	}	
	
	
	public List<Ses0151> searchSeq(Ses0150ParamVO param) {
		return Ses0151Dao.selectListSeq(param);
	}	
	

}
