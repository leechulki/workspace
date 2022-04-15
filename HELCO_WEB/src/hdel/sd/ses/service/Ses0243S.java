package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.MailService;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0243D;
import hdel.sd.ses.domain.Ses0243;
import hdel.sd.ses.domain.Ses0243ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0243S extends SrmService {

	private Ses0243D Ses0243Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0243Dao = sqlSession.getMapper(Ses0243D.class);
	}
	
	public List<Ses0243> searchHeader(Ses0243ParamVO param) {
		return Ses0243Dao.selectListHeader(param);
	}

	public List<Ses0243> searchDetail(Ses0243ParamVO param) {
		return Ses0243Dao.selectListDetail(param);
	}
	
	public List<Ses0243> searchHogi(Ses0243ParamVO param) {
		return Ses0243Dao.selectListHogi(param);
	}	
	
	public List<Ses0243> searchAttribute(Ses0243ParamVO param) {
		return Ses0243Dao.searchAttribute(param);
	}
	
	public List<Ses0243> checkVbak(Ses0243ParamVO param) {
		return Ses0243Dao.checkVbak(param);
	}
	
	public List<Ses0243> getSeq(Ses0243ParamVO param) {
		return Ses0243Dao.getSeq(param);
	}	
	
	
	// detail update
	public void updateDetail(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_detail");  	//ds_detail
//		System.out.println("@@@detail count >>>>>>  " + ds_list.getRowCount());
		try
		{  
			Ses0243 param = new Ses0243();						// 저장 파라메터
			createDao(session);	 			

			// 
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setVbeln(DatasetUtility.getString(ds_list, irow, "VBELN"));
				param.setZseq(DatasetUtility.getString(ds_list, irow, "ZSEQ"));
				param.setPosnr(DatasetUtility.getString(ds_list, irow, "POSNR"));
				param.setEdatu(DatasetUtility.getString(ds_list, irow, "EDATU"));
				param.setPonetwr(DatasetUtility.getString(ds_list, irow, "PONETWR"));
				
//				System.out.println("@@@getMandt  >> " +param.getMandt() );
//				System.out.println("@@@getVbeln  >> " +param.getVbeln() );
//				System.out.println("@@@getZseq  >> " +param.getZseq() );
//				System.out.println("@@@getPosnr  >> " +param.getPosnr() );
//				System.out.println("@@@getEdatu  >> " +param.getEdatu() );
//				System.out.println("@@@getPonetwr  >> " +param.getPonetwr() );
				
				updateDetail(param); 
			} // end of for
			
					 
		}  catch (Exception e) { 
		}
	}   
	
	public void updateDetail(Ses0243 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0243Dao.updateDetail(param);		
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
			Ses0243 param = new Ses0243();						// 저장 파라메터
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
	
	public void sendMailheader(Ses0243 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0243Dao.sendMailheader(param);		
	}	
	
	//updateHeader
	public void updateHeader(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_header	= paramVO.getDataset("ds_header");  	// header정보
		
		try
		{  
			Ses0243 param = new Ses0243();						// 저장 파라메터
			createDao(session);	 			

			// ds_header save
			for( int irow = 0; irow < ds_header.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setVbeln(DatasetUtility.getString(ds_header, irow, "VBELN"));
				param.setZseq(DatasetUtility.getString(ds_header, irow, "ZSEQ"));
				param.setEdatu(DatasetUtility.getString(ds_header, irow, "EDATU"));
				param.setRemark1(DatasetUtility.getString(ds_header, irow, "REMARK1"));
				param.setRemark2(DatasetUtility.getString(ds_header, irow, "REMARK2"));
				param.setRemark3(DatasetUtility.getString(ds_header, irow, "REMARK3"));
				param.setRemark4(DatasetUtility.getString(ds_header, irow, "REMARK4"));
				param.setRemark5(DatasetUtility.getString(ds_header, irow, "REMARK5"));

				updateHeader(param); 
			} // end of for
					 
		}  catch (Exception e) { 
		}
	}   
	
	//updateHeaderReceiptDate
	public void updateHeaderReceiptDate(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
			
			// INPUT DATASET GET 
			Dataset ds_header	= paramVO.getDataset("ds_header2");  	// header정보
			
			
			try
			{  
				Ses0243 param = new Ses0243();						// 저장 파라메터
				createDao(session);	 			

				// ds_header save
				for( int irow = 0; irow < ds_header.getRowCount(); irow++ ) 
				{  
					param.setMandt(paramVO.getVariable("G_MANDT"));	            
					param.setVbeln(DatasetUtility.getString(ds_header, irow, "VBELN"));
					param.setZseq(DatasetUtility.getString(ds_header, irow, "ZSEQ"));
					param.setAcdat(DatasetUtility.getString(ds_header, irow, "ACDAT"));
					
					updateHeaderReceiptDate(param); 
				} // end of for
						 
			}  catch (Exception e) { 
			}
		}   
		
	public void updateHeader(Ses0243 param) {
		Ses0243Dao.updateHeader(param);		
	}	
	
	public void updateHeaderReceiptDate(Ses0243 param) {
		Ses0243Dao.updateHeaderReceiptDate(param);		
	}	
	
	//PO헤더 insert
	public List<Ses0243> insertVblen(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_hogi =  paramVO.getDataset("ds_hogi");  
		Dataset ds_cond	= paramVO.getDataset("ds_cond");  	// 
 	
		try
		{  
			//Ses0243 param = new Ses0243();						// 저장 파라메터
			Ses0243ParamVO param = new Ses0243ParamVO();						// 저장 파라메터
			createDao(session);	 			
//System.out.println("@@specdwg >>> " + DatasetUtility.getString(ds_hogi, 0, "SPECDWG"));
			// PO헤더 insert
			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
			param.setVbeln(DatasetUtility.getString(ds_hogi, 0, "VBELN"));
			param.setErnam(DatasetUtility.getString(ds_cond, 0, "ERNAM"));			
			param.setBstkd(DatasetUtility.getString(ds_hogi, 0, "BSTKD"));
			param.setPosid(DatasetUtility.getString(ds_hogi, 0, "POSID"));
			param.setPosnr(DatasetUtility.getString(ds_hogi, 0, "POSNR"));
			param.setPoedatu(DatasetUtility.getString(ds_hogi, 0, "POEDATU"));
			param.setEdatu(DatasetUtility.getString(ds_hogi, 0, "EDATU"));
			param.setWwmod(DatasetUtility.getString(ds_hogi, 0, "WWMOD"));
			param.setArktx(DatasetUtility.getString(ds_hogi, 0, "ARKTX"));
			param.setKwmeng(DatasetUtility.getString(ds_hogi, 0, "KWMENG"));
			param.setVrkme(DatasetUtility.getString(ds_hogi, 0, "VRKME"));
			param.setNetwr(DatasetUtility.getString(ds_hogi, 0, "NETWR"));
			param.setPonetwr(DatasetUtility.getString(ds_hogi, 0, "PONETWR"));
			param.setWaerk(DatasetUtility.getString(ds_hogi, 0, "WAERK"));
			param.setRecad_da(DatasetUtility.getString(ds_hogi, 0, "RECAD_DA"));
			param.setInco1(DatasetUtility.getString(ds_hogi, 0, "INCO1"));
			param.setInco2(DatasetUtility.getString(ds_hogi, 0, "INCO2"));
			param.setZtermt(DatasetUtility.getString(ds_hogi, 0, "ZTERMT"));
			param.setSpecdwg(DatasetUtility.getString(ds_hogi, 0, "SPECDWG"));
			param.setZfwding(DatasetUtility.getString(ds_hogi, 0, "ZFWDING"));
			param.setAcdat(DatasetUtility.getString(ds_hogi, 0, "ACDAT"));
			param.setSedat(DatasetUtility.getString(ds_hogi, 0, "SEDAT"));
			param.setSezzt(DatasetUtility.getString(ds_hogi, 0, "SEZZT"));
			param.setRegion(DatasetUtility.getString(ds_hogi, 0, "REGION"));
			param.setRemark1(DatasetUtility.getString(ds_hogi, 0, "REMARK1"));
			param.setRemark2(DatasetUtility.getString(ds_hogi, 0, "REMARK2"));
			param.setRemark3(DatasetUtility.getString(ds_hogi, 0, "REMARK3"));
			param.setRemark4(DatasetUtility.getString(ds_hogi, 0, "REMARK4"));
			param.setRemark5(DatasetUtility.getString(ds_hogi, 0, "REMARK5"));
			
			//Ses0243Dao.getSeq(param);
			
			//zseq max select
			List<Ses0243> lZseq= getSeq(param);			//zseq가져오기
			param.setZseq(lZseq.get(0).getZseq());
//			System.out.println("ds_hogi row count >>>> " + ds_hogi.getRowCount());
//			System.out.println("ds_hogi >>>> " + ds_hogi);
//			System.out.println("@@@getMandt >>>" +param.getMandt());
//			System.out.println("@@@getVbeln >>>" +param.getVbeln());		
//			System.out.println("@@@getZseq >>>" +param.getZseq());
//			System.out.println("@@@getBstkd >>>" +param.getBstkd());
//			System.out.println("@@@getRegion >>>" +param.getRegion());
//			System.out.println("@@@getInco1 >>>" +param.getInco1());
//			System.out.println("@@@getInco2 >>>" +param.getInco2());
//			System.out.println("@@@getZtermt >>>" +param.getZtermt());
//			System.out.println("@@@getSpecdwg >>>" +param.getSpecdwg());
//			System.out.println("@@@getEdatu >>>" +param.getEdatu());
//			System.out.println("@@@getPoedatu >>>" +param.getPoedatu());
//			System.out.println("@@@getZfwding >>>" +param.getZfwding());
//			System.out.println("@@@getSedat >>>" +param.getSedat());
//			System.out.println("@@@getSezzt >>>" +param.getSezzt());
//			System.out.println("@@@getErnam >>>" +param.getErnam());
//			System.out.println("@@@getAcdat >>>" +param.getAcdat());
//			System.out.println("@@@getPosnr >>>" +param.getPosnr());
//			System.out.println("@@@getPosid >>>" +param.getPosid());
//			System.out.println("@@@getWwmod >>>" +param.getWwmod());
//			System.out.println("@@@getArktx >>>" +param.getArktx());			
//			System.out.println("@@@getKwmeng >>>" +param.getKwmeng());
//			System.out.println("@@@getVrkme >>>" +param.getVrkme());
//			System.out.println("@@@getPonetwr >>>" +param.getPonetwr());
//			System.out.println("@@@getWaerk >>>" +param.getWaerk());
//			System.out.println("@@@getRecad_da >>>" +param.getRecad_da());
//			System.out.println("@@@getRemark1 >>>" +param.getRemark1());
//			System.out.println("@@@getRemark2 >>>" +param.getRemark2());
//			System.out.println("@@@getRemark3 >>>" +param.getRemark3());
//			System.out.println("@@@getRemark4 >>>" +param.getRemark4());
//			System.out.println("@@@getRemark5 >>>" +param.getRemark5());
			
			//header insert
			insertVblen(param); 
			
//			System.out.println("@@@@ header insert success !! @@@@");
			
			for (int i=0; i<ds_hogi.getRowCount(); i++) {
//				System.out.println("@@@@ ROW TYPE >>> " + ds_hogi.getRowType(i));
				int row_type =  ds_hogi.getRowType(i);
				
				//row type 4: update
				if( row_type == 4) {
					param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
					param.setVbeln(DatasetUtility.getString(ds_hogi, i, "VBELN"));		
					param.setBstkd(DatasetUtility.getString(ds_hogi, i, "BSTKD"));
					param.setPosid(DatasetUtility.getString(ds_hogi, i, "POSID"));
					param.setPosnr(DatasetUtility.getString(ds_hogi, i, "POSNR"));
					param.setEdatu(DatasetUtility.getString(ds_hogi, i, "EDATU"));
					param.setPoedatu(DatasetUtility.getString(ds_hogi, i, "POEDATU"));
					param.setWwmod(DatasetUtility.getString(ds_hogi, i, "WWMOD"));
					param.setArktx(DatasetUtility.getString(ds_hogi, i, "ARKTX"));
					param.setKwmeng(DatasetUtility.getString(ds_hogi, i, "KWMENG"));
					param.setVrkme(DatasetUtility.getString(ds_hogi, i, "VRKME"));
					param.setNetwr(DatasetUtility.getString(ds_hogi, i, "NETWR"));
					param.setPonetwr(DatasetUtility.getString(ds_hogi, i, "PONETWR"));
					param.setWaerk(DatasetUtility.getString(ds_hogi, i, "WAERK"));
					param.setRecad_da(DatasetUtility.getString(ds_hogi, i, "RECAD_DA"));
					param.setInco1(DatasetUtility.getString(ds_hogi, i, "INCO1"));
					param.setInco2(DatasetUtility.getString(ds_hogi, i, "INCO2"));
					param.setZtermt(DatasetUtility.getString(ds_hogi, i, "ZTERMT"));
					param.setSpecdwg(DatasetUtility.getString(ds_hogi, i, "SPECDWG"));
					param.setZfwding(DatasetUtility.getString(ds_hogi, i, "ZFWDING"));
					
					//detail insert
					insertVblenDetail(param, ds_hogi, session);
				}
			}
						
			List<Ses0243> lPosId= searchDetail(param);			//zseq가져오기
			
			return lPosId;	//새로 입력한영업오더 품목의 호기번호 return;
		 
		}  catch (Exception e) {
			return null;	//
		}
	}   
	
	public void insertVblen(Ses0243ParamVO param) {
//		System.out.println("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0243Dao.insertVblen(param);		
	}			
	
	//updateHeader
	public void insertVblenDetail(Ses0243ParamVO param, Dataset ds_hogi, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		//Dataset ds_list	= paramVO.getDataset("ds_sapinsert");  	// 수주 등록,수정,삭제 대상정보
 	
		try
		{  
			//Ses0243ParamVO param = new Ses0243ParamVO();						// 저장 파라메터
			createDao(session);	 			

			// 견적승인요청 접수 처리 START
			for( int irow = 0; irow < ds_hogi.getRowCount(); irow++ ) 
			{  
					int row_type =  ds_hogi.getRowType(irow);
				
					//row type 4: update
					if( row_type == 4) {
					//param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
					/*param.setMandt(DatasetUtility.getString(ds_hogi, irow, "MANDT"));
					param.setVbeln(DatasetUtility.getString(ds_hogi, irow, "VBELN"));
					param.setPosnr(DatasetUtility.getString(ds_hogi, irow, "POSNR"));
					param.setPosid(DatasetUtility.getString(ds_hogi, irow, "POSID"));*/
			
					insertVblenDetail(param); 
				}
			} // end of for
					 
		}  catch (Exception e) { 
		}
	}   
	
	public void insertVblenDetail(Ses0243ParamVO param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0243Dao.insertVblenDetail(param);		
	}				
	
	//updateHeader
	public void insertSap(Dataset ds_list, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		//Dataset ds_list	= paramVO.getDataset("ds_sapinsert");  	// 수주 등록,수정,삭제 대상정보
 	
		try
		{  
			Ses0243 param = new Ses0243();						// 저장 파라메터
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
	
	public void insertSap(Ses0243 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0243Dao.insertSap(param);		
	}			

}
