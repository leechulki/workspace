package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.MailService;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0330D;
import hdel.sd.ses.domain.Ses0330;
import hdel.sd.ses.domain.Ses0330ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0330S extends SrmService {

	private Ses0330D Ses0330Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0330Dao = sqlSession.getMapper(Ses0330D.class);
	}
	
	public List<Ses0330> searchHeader(Ses0330ParamVO param) {
		return Ses0330Dao.selectListHeader(param);
	}

	public List<Ses0330> searchDetail(Ses0330ParamVO param) {
		return Ses0330Dao.selectListDetail(param);
	}
	
	public List<Ses0330> searchAttribute(Ses0330ParamVO param) {
		return Ses0330Dao.searchAttribute(param);
	}

	public List<Ses0330> selectHdrDtlList(Ses0330ParamVO param) {
		return Ses0330Dao.selectHdrDtlList(param);
	}
	
	public List<Ses0330> checkVbak(Ses0330ParamVO param) {
		return Ses0330Dao.checkVbak(param);
	}
	
	public List<Ses0330> getSeq(Ses0330ParamVO param) {
		return Ses0330Dao.getSeq(param);
	}	
	
	
	// detail update
	public void updateDetail(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
 	
		try
		{  
			Ses0330 param = new Ses0330();						// 저장 파라메터
			createDao(session);	 			

			// 
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setVbeln(DatasetUtility.getString(ds_list, irow, "VBELN"));
				param.setZseq(DatasetUtility.getString(ds_list, irow, "ZSEQ"));
				param.setPosnr(DatasetUtility.getString(ds_list, irow, "POSNR"));
				param.setEdatu(DatasetUtility.getString(ds_list, irow, "EDATU"));
				param.setShnetwr(DatasetUtility.getString(ds_list, irow, "SHNETWR"));
				updateDetail(param); 
			} // end of for
					 
		}  catch (Exception e) { 
		}
	}   
	
	public void updateDetail(Ses0330 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0330Dao.updateDetail(param);		
	}
	
	//sendmail header
	public void sendMailheader(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_list");  	//
		Dataset ds_email	= paramVO.getDataset("ds_email");  	//		 
		
		//String from, String to, String head, String body) {
		String sFrom = null;
		String sTo = null;
		String sHead = null;
		String sBody = null;
		
		/*
		sFrom = "";
		sTo = "";
		sHead = "";
		sBody = "Dear Sirs<br> Please find Purchase Order of      as attached<br><br> Best Regards,<br>Hyumdai Global marketing & Sales in Seoul";
 		*/
 	
		try
		{  
			Ses0330 param = new Ses0330();						// 저장 파라메터
			createDao(session);	 			

			// email정보 셋팅/ 전송
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				sFrom = DatasetUtility.getString(ds_email, irow, "FROM");
				sTo = DatasetUtility.getString(ds_email, irow, "TO");
				sHead = DatasetUtility.getString(ds_email, irow, "TITLE");
				sBody = DatasetUtility.getString(ds_email, irow, "CONTENT");
				
//				System.out.println("============== sFrom====" +DatasetUtility.getString(ds_email, irow, "FROM"));
//				System.out.println("============== sTo====" +DatasetUtility.getString(ds_email, irow, "TO"));
//				System.out.println("============== sHead====" +DatasetUtility.getString(ds_email, irow, "TITLE"));
//				System.out.println("============== sBody====" +DatasetUtility.getString(ds_email, irow, "CONTENT"));
				
				MailService ms = new MailService(); 
				ms.send(sFrom, sTo, sHead, sBody);
//				System.out.println("==============  SEND MAIL====");
			} // end of for
			
//			System.out.println("============== AFTER SEND MAIL====");
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setVbeln(DatasetUtility.getString(ds_list, irow, "VBELN"));
				param.setZseq(DatasetUtility.getString(ds_list, irow, "ZSEQ"));
				sendMailheader(param); 
			} // end of for
					 
		}  catch (Exception e) { 
		}
	}   
	
	public void sendMailheader(Ses0330 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0330Dao.sendMailheader(param);		
	}	
	
	//updateHeader
	public void updateHeader(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
 	
		try
		{  
			Ses0330 param = new Ses0330();						// 저장 파라메터
			createDao(session);	 			

			// 견적승인요청 접수 처리 START
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setVbeln(DatasetUtility.getString(ds_list, irow, "VBELN"));
				param.setZseq(DatasetUtility.getString(ds_list, irow, "ZSEQ"));
				param.setInco1(DatasetUtility.getString(ds_list, irow, "INCO1"));
				param.setInco2(DatasetUtility.getString(ds_list, irow, "INCO2"));
				param.setZtermt(DatasetUtility.getString(ds_list, irow, "ZTERMT"));
				param.setSpecdwg(DatasetUtility.getString(ds_list, irow, "SPECDWG"));
				param.setEdatu(DatasetUtility.getString(ds_list, irow, "EDATU"));
				param.setZfwding(DatasetUtility.getString(ds_list, irow, "ZFWDING"));
				param.setRemark1(DatasetUtility.getString(ds_list, irow, "REMARK1"));
				param.setRemark2(DatasetUtility.getString(ds_list, irow, "REMARK2"));
				param.setRemark3(DatasetUtility.getString(ds_list, irow, "REMARK3"));
				param.setRemark4(DatasetUtility.getString(ds_list, irow, "REMARK4"));
				param.setRemark5(DatasetUtility.getString(ds_list, irow, "REMARK5"));
				
				updateHeader(param); 
			} // end of for
					 
		}  catch (Exception e) { 
		}
	}   
	
	public void updateHeader(Ses0330 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0330Dao.updateHeader(param);		
	}		
	
	//PO헤더 insert
	public List<Ses0330> insertVblen(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_hdrdtl	= paramVO.getDataset("ds_hdrdtl");	// 
		Dataset ds_cond		= paramVO.getDataset("ds_cond");	// 
		
		try
		{  
			//Ses0330 param = new Ses0330();						// 저장 파라메터
			Ses0330ParamVO param = new Ses0330ParamVO();						// 저장 파라메터
			createDao(session);

			// PO헤더 insert
			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
			param.setVbeln(DatasetUtility.getString(ds_cond, 0, "VBELN"));
			param.setErnam(DatasetUtility.getString(ds_cond, 0, "ERNAM"));
			
			List<Ses0330> lZseq= getSeq(param);			//zseq가져오기
			param.setZseq(lZseq.get(0).getZseq());
			
			insertVblen(param);
			
			// PO 디테일 insert
			for ( int i = 0 ; i < ds_hdrdtl.getRowCount() ; i++ ) {
				param = new Ses0330ParamVO();
				param.setZseq(lZseq.get(0).getZseq());
				param.setShnetwr(DatasetUtility.getString(ds_hdrdtl, i, "SHNETWR"));
				param.setErnam(DatasetUtility.getString(ds_cond, 0, "ERNAM"));
				param.setMandt(paramVO.getVariable("G_MANDT"));
				param.setVbeln(DatasetUtility.getString(ds_cond, 0, "VBELN"));
				param.setPosnr(DatasetUtility.getString(ds_hdrdtl, i, "POSNR"));
				
				/*System.out.println("["+i+"] "+param.getZseq());
				System.out.println("["+i+"] "+param.getShnetwr());
				System.out.println("["+i+"] "+param.getErnam());
				System.out.println("["+i+"] "+param.getMandt());
				System.out.println("["+i+"] "+param.getVbeln());
				System.out.println("["+i+"] "+param.getPosnr());*/
				insertVblenDetail(param);
			}
			
			List<Ses0330> lPosId= searchDetail(param);			//posid가져오기
			
			return lPosId;	//새로 입력한영업오더 품목의 호기번호 return;
		 
		}  catch (Exception e) {
			return null;	//
		}
	}   
	
	public void insertVblen(Ses0330ParamVO param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0330Dao.insertVblen(param);		
	}			
	
	//PO헤더 insert
	
	public void insertVblenDetail(Ses0330ParamVO param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0330Dao.insertVblenDetail(param);		
	}				
	
	//updateHeader
	public void insertSap(Dataset ds_list, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		//Dataset ds_list	= paramVO.getDataset("ds_sapinsert");  	// 수주 등록,수정,삭제 대상정보
 	
		try
		{  
			Ses0330 param = new Ses0330();						// 저장 파라메터
			createDao(session);	 			

			// 견적승인요청 접수 처리 START
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				//param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setVbeln(DatasetUtility.getString(ds_list, irow, "VBELN"));
				param.setZseq(DatasetUtility.getString(ds_list, irow, "ZSEQ"));
				param.setPosnr(DatasetUtility.getString(ds_list, irow, "POSNR"));
				param.setPosid(DatasetUtility.getString(ds_list, irow, "POSID"));
				param.setCharacteristic(DatasetUtility.getString(ds_list, irow, "CHARACTERISTIC"));
				param.setValue(DatasetUtility.getString(ds_list, irow, "VALUE"));
				param.setErnam(DatasetUtility.getString(ds_list, irow, "ERNAM"));
		
				insertSap(param); 
			} // end of for
					 
		}  catch (Exception e) { 
		}
	}   
	
	public void insertSap(Ses0330 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0330Dao.insertSap(param);		
	}			

}
