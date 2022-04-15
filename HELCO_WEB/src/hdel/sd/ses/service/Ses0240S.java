package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0240D;
import hdel.sd.ses.domain.Ses0020;
import hdel.sd.ses.domain.Ses0020ParamVO;
import hdel.sd.ses.domain.Ses0240;
import hdel.sd.ses.domain.Ses0240ParamVO;
import hdel.sd.smp.domain.Smp0070ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0240S extends SrmService {

	private Ses0240D Ses0240Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0240Dao = sqlSession.getMapper(Ses0240D.class);
	}
	
	public List<Ses0240> searchHeader(Ses0240ParamVO param) {
		return Ses0240Dao.selectListHeader(param);
	}

	public List<Ses0240> searchDetail(Ses0240ParamVO param) {
		return Ses0240Dao.selectListDetail(param);
	}
	
	public List<Ses0240> searchFile(Ses0240ParamVO param) {
		return Ses0240Dao.selectListFile(param);
	}
	
	public int findCountQtnum(Ses0240 param) {
		return Ses0240Dao.findCountQtnum(param);
	}
	
	public List<Ses0240> findMaxSeq(Ses0240 param) {
		return Ses0240Dao.findMaxSeq(param);
	}
	
	public List<Ses0240> findMaxDocReqNo(Ses0240 param) {
		return Ses0240Dao.findMaxDocReqNo(param);
	}

	// �����Ƿ�Header insert
	public String insertHeader(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_header");  	// ���� ���,����,���� �������
		Ses0240 param = new Ses0240();						// ���� �Ķ����
		
		try
		{  
			
			createDao(session);	 
			
			// �����Ƿ� Insert
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	               					    // SAP CLIENT					
				List<Ses0240> Headerseq= findMaxDocReqNo(param);									//�����Ƿڹ�ȣ Max�� ��ȸ
				param.setDocreqno(Headerseq.get(0).getDocreqno());										//�����Ƿڹ�ȣ
				param.setZrqflg(DatasetUtility.getString(ds_list, irow, "ZRQFLG"));						//�����Ƿڱ���
				param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));						//��û����
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));							//��û����
				param.setZwriter(DatasetUtility.getString(ds_list, irow, "ZWRITER"));						//�ۼ���
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));							//���
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));						//������ȣ
				param.setCdate(DatasetUtility.getString(ds_list, irow, "CDATE"));
				param.setCtime(DatasetUtility.getString(ds_list, irow, "CTIME"));
				param.setCuser(DatasetUtility.getString(ds_list, irow, "CUSER"));
				param.setUdate(DatasetUtility.getString(ds_list, irow, "UDATE"));
				param.setUtime(DatasetUtility.getString(ds_list, irow, "UTIME"));				
				param.setUuser(DatasetUtility.getString(ds_list, irow, "UUSER"));
				
				insertHeader(param); 	//Header Insert
				insertDetail(param);		//Detail insert
				
			} // end of for		 
			
		}  catch (Exception e) { 
		}
		
		return param.getDocreqno();
	}   


	// ����Header(ZSDT1046) ����  (�űԵ����Ƿڽ� �����ϵ� �Բ� ����)
	public void insertHeader(Ses0240 param) {
		Ses0240Dao.insertHeader(param);
	}  

	
	// Detail insert
	public void insertDetail(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list = paramVO.getDataset("ds_list");  	
		try
		{  
			Ses0240 param = new Ses0240();						
			createDao(session);	 
			
			//Detail Insert
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
				param.setDocreqno(DatasetUtility.getString(ds_list, irow, "DOCREQNO"));
				//param.setZseq(DatasetUtility.getString(ds_list, irow, "ZSEQ"));
				//param.setZseq("10");		//�����Ƿ�/ MN���� �ű��Է�
				param.setZrqflg(DatasetUtility.getString(ds_list, irow, "ZRQFLG"));
				param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setZwriter(DatasetUtility.getString(ds_list, irow, "ZWRITER"));
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
				param.setCdate(DatasetUtility.getString(ds_list, irow, "CDATE"));
				param.setCuser(DatasetUtility.getString(ds_list, irow, "CUSER"));
				param.setUdate(DatasetUtility.getString(ds_list, irow, "UDATE"));
				param.setUtime(DatasetUtility.getString(ds_list, irow, "UTIME"));				
				param.setUuser(DatasetUtility.getString(ds_list, irow, "UUSER"));	
				insertDetail(param); 				
			} // end of for
			
		}  catch (Exception e) { 
			//TransactionManager.rollback(); 
		}
	}   

	// ����Header(ZSDT1046) ����
	public void insertDetail(Ses0240 param) {
		
		Ses0240Dao.insertDetail(param);
	}  
	
	// �����Ƿ� ����ܰ�
	public void addDetail(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list = paramVO.getDataset("ds_list");  	// ���� ���,����,���� �������
		try
		{  
			Ses0240 param = new Ses0240();						// ���� ���� �Ķ����
			createDao(session);	 
			
			// �������ο�û ���� ó�� START
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
				param.setDocreqno(DatasetUtility.getString(ds_list, irow, "DOCREQNO"));
				List<Ses0240> seqlist = findMaxSeq(param);				
				param.setZseq(seqlist.get(0).getZseq());
				param.setZrqflg(DatasetUtility.getString(ds_list, irow, "ZRQFLG"));
				param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setZwriter(DatasetUtility.getString(ds_list, irow, "ZWRITER"));
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
				param.setCdate(DatasetUtility.getString(ds_list, irow, "CDATE"));
				param.setCuser(DatasetUtility.getString(ds_list, irow, "CUSER"));
				param.setUdate(DatasetUtility.getString(ds_list, irow, "UDATE"));
				param.setUtime(DatasetUtility.getString(ds_list, irow, "UTIME"));				
				param.setUuser(DatasetUtility.getString(ds_list, irow, "UUSER"));
//System.out.println( "setZrqcn > " + param.getZrqcn());
//System.out.println( "setZrmk > " + param.getZrmk());
//System.out.println( "setZwriter > " + param.getZwriter());
				addDetail(param); 				
			} // end of for
			
		}  catch (Exception e) { 
			//TransactionManager.rollback(); 
		}
	}   

	// ����Header(ZSDT1046) ����
	public void addDetail(Ses0240 param) {
		
		Ses0240Dao.addDetail(param);
	}  
	
	// Header update
	public void updateHeader(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_list");
 	
		try
		{  
			Ses0240 param = new Ses0240();						// ���� �Ķ����
			createDao(session);	 			
			
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setDocreqno(DatasetUtility.getString(ds_list, irow, "DOCREQNO"));
				param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));
				param.setZrqflg(DatasetUtility.getString(ds_list, irow, "ZRQFLG"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setZwriter(DatasetUtility.getString(ds_list, irow, "ZWRITER"));
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));	
				param.setUdate(DatasetUtility.getString(ds_list, irow, "UDATE"));
				param.setUtime(DatasetUtility.getString(ds_list, irow, "UTIME"));				
				param.setUuser(DatasetUtility.getString(ds_list, irow, "UUSER"));
				
				updateHeader(param); 
			} // end of for		 
		}  catch (Exception e) { 
		}
	}   
	
	// �����Ƿ�Header update
	public void updateDetail(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_list");  	
 	
		try
		{  
			Ses0240 param = new Ses0240();						// ���� �Ķ����
			createDao(session);	 			
			
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				if (ds_list.getRowType(irow) == ds_list.ROWTYPE_UPDATE) {
					param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
					param.setDocreqno(DatasetUtility.getString(ds_list, irow, "DOCREQNO"));
					param.setZseq(DatasetUtility.getString(ds_list, irow, "ZSEQ"));
					param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));
					param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
					param.setZwriter(DatasetUtility.getString(ds_list, irow, "ZWRITER"));
					param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
					param.setUdate(DatasetUtility.getString(ds_list, irow, "UDATE"));
					param.setUtime(DatasetUtility.getString(ds_list, irow, "UTIME"));				
					param.setUuser(DatasetUtility.getString(ds_list, irow, "UUSER"));
					
					updateDetail(param); 
				}
			} // end of for		 
		}  catch (Exception e) { 
		}
	}   	
	
	// �����Ƿ� ���º���
	public void statusUpdate(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
			
			// INPUT DATASET GET 
			Dataset ds_header	= paramVO.getDataset("ds_header"); 
	 	
			try
			{  
				Ses0240 param = new Ses0240();						// ���� �Ķ����
				createDao(session);	 			
				
				for( int irow = 0; irow < ds_header.getRowCount(); irow++ ) 
				{  
						param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
						param.setDocreqno(DatasetUtility.getString(ds_header, irow, "DOCREQNO"));						
						param.setZrqflg(DatasetUtility.getString(ds_header, irow, "ZRQFLG"));		
						param.setZrqcn(DatasetUtility.getString(ds_header, irow, "ZRQCN"));		
						param.setZrmk(DatasetUtility.getString(ds_header, irow, "ZRMK"));		
						param.setUuser(DatasetUtility.getString(ds_header, irow, "UUSER"));
						
						statusUpdate(param); 
				} // end of for		 
			}  catch (Exception e) { 
			}
			
		}   
	
	// Header/Detail Save
	public boolean save(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
			
			// INPUT DATASET GET 
			Dataset ds_header = paramVO.getDataset("ds_header");  	
			Dataset ds_detail = paramVO.getDataset("ds_detail");  	
			Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
			
			try
			{  
				
				Ses0240 param = new Ses0240();			
				
				createDao(session);	 
				
				//Header save
				for( int irow = 0; irow < ds_header.getRowCount(); irow++ ) 
				{  
					param.setMandt(paramVO.getVariable("G_MANDT"));	               
					param.setDocreqno(DatasetUtility.getString(ds_header, irow, "DOCREQNO"));		
					param.setZrqflg(DatasetUtility.getString(ds_header, irow, "ZRQFLG"));
					param.setZrqcn(DatasetUtility.getString(ds_header, irow, "ZRQCN"));
					param.setZrmk(DatasetUtility.getString(ds_header, irow, "ZRMK"));
					param.setQtnum(DatasetUtility.getString(ds_header, irow, "QTNUM"));	
					param.setQtser(DatasetUtility.getString(ds_header, irow, "QTSER"));	
					param.setUuser(DatasetUtility.getString(ds_header, irow, "UUSER"));
					
					
					String qtnum = DatasetUtility.getString(ds_header, irow, "QTNUM");
					String org_qtnum =DatasetUtility.getString( ds_header, irow, "ORG_QTNUM") ;
					
					
					//������ȣ�� ����� ���
					if (!qtnum.equals(org_qtnum)) {
						int cnt = findCountQtnum(param);	
						if (cnt > 0) 	return false;						
					} 

					if (ds_header.getRowType(irow) == ds_header.ROWTYPE_UPDATE) {
						updateHeader(param); 				
					} 

				}
				
				//Detail save
				for( int irow = 0; irow < ds_detail.getRowCount(); irow++ ) 
				{  
					param.setMandt(paramVO.getVariable("G_MANDT"));	               
					param.setDocreqno(DatasetUtility.getString(ds_detail, irow, "DOCREQNO"));	
					param.setZseq(DatasetUtility.getString(ds_detail, irow, "ZSEQ"));	
					param.setZrqcn(DatasetUtility.getString(ds_detail, irow, "ZRQCN"));
					param.setZwriter(DatasetUtility.getString(ds_detail, irow, "ZWRITER"));
					param.setZrmk(DatasetUtility.getString(ds_detail, irow, "ZRMK"));
					param.setUuser(DatasetUtility.getString(ds_detail, irow, "UUSER"));
					
					if (ds_detail.getRowType(irow) == ds_detail.ROWTYPE_UPDATE) {
						updateDetail(param); 				
					}
				}
				
			}  catch (Exception e) { 
				
			}
			
			return true;
		}   

	// ���Ͼ��ε�
	public void fileSave(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET  	
		Dataset ds_file	= paramVO.getDataset("ds_file");  	
		Dataset ds_delFile = paramVO.getDataset("ds_delFile");
 	
		try
		{  
			Ses0240 param = new Ses0240();						// ���� �Ķ����
			createDao(session);	 
			
			//����
			for( int irow = 0; irow < ds_file.getRowCount(); irow++ ) 
			{
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setZwgbn(DatasetUtility.getString(ds_file, irow, "ZWGBN"));
				param.setDocreqno(DatasetUtility.getString(ds_file, irow, "DOCREQNO"));
				param.setZseq(DatasetUtility.getString(ds_file, irow, "ZSEQ"));
				param.setZattfn(DatasetUtility.getString(ds_file, irow, "ZATTFN"));
				param.setZattfn_or(DatasetUtility.getString(ds_file, irow, "ZATTFN_OR"));
				param.setZattpath(DatasetUtility.getString(ds_file, irow, "ZATTPATH"));
				param.setCuser(DatasetUtility.getString(ds_file, irow, "CUSER"));
				param.setUuser(DatasetUtility.getString(ds_file, irow, "UUSER"));
				
				// �������� insert
				if(ds_file.getRowType(irow) == ds_file.ROWTYPE_INSERT) {
					fileUpload(param); 
				} 
			} // end of for		 
			
			//���ϻ���
			for( int d = 0; d < ds_delFile.getRowCount(); d++ ) 
			{
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setZwgbn(DatasetUtility.getString(ds_delFile, d, "ZWGBN"));
				param.setDocreqno(DatasetUtility.getString(ds_delFile, d, "DOCREQNO"));
				param.setZseq(DatasetUtility.getString(ds_delFile, d, "ZSEQ"));
				param.setZattseq(DatasetUtility.getString(ds_delFile, d, "ZATTSEQ"));
				
					fileDelete(param);	
			}
		}  catch (Exception e) { 
		}
	}   	
	
	// Header���� 
	public void updateHeader(Ses0240 param) {
		Ses0240Dao.updateHeader(param);
	}
	
	public void updateDetail(Ses0240 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0240Dao.updateDetail(param);		
	}
	
	public void statusUpdate(Ses0240 param) {
		Ses0240Dao.statusUpdate(param);
	}
	
	public void deleteHeader(Ses0240 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0240Dao.deleteHeader(param);		
	}
	
	public void deleteDetail(Ses0240 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0240Dao.deleteDetail(param);		
	}
	
	// ���ϻ���
	public void fileDelete(Ses0240 param) {
		Ses0240Dao.fileDelete(param);		//Insert
	}  		
	
	// ���Ͼ��ε�
	public void fileUpload(Ses0240 param) {
		Ses0240Dao.fileUpload(param);		//Insert
	}  	

}
