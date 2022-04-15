package hdel.qm.sag.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.qm.sag.domain.QM0901000;
import hdel.qm.sag.domain.QM0901000ParamVO;
import hdel.qm.sag.service.QM0901000S;


@Controller
@RequestMapping("qm0901000")
public class QM0901000C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private QM0901000S service;
	
	/*-----------------------------------------------------
	 *  초도품검사진행목록 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="find")
	public View QM0901000FindView(MipParameterVO paramVO, Model model) {
		 
		logger.debug("@@@ QM0901000FindView START!!!");
		
		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");  

		// OUTPUT DATASET DECLARE
		Dataset ds_list = paramVO.getDataset("ds_list");				// UI로 return할 out dataset    
					
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		logger.debug( "@@@ paramVO.G_MANDT 	===>"+paramVO.getVariable("G_MANDT")); 
		logger.debug( "@@@ ds_cond.FR_QTDAT	===>"+DatasetUtility.getString(ds_cond,"FR_QTDAT")); 
		logger.debug( "@@@ ds_cond.TO_QTDAT ===>"+DatasetUtility.getString(ds_cond,"TO_QTDAT"));
		logger.debug( "@@@ ds_cond.LIFNR ===>"+DatasetUtility.getString(ds_cond,"LIFNR"));
		logger.debug( "@@@ ds_cond.ZRQSTA ===>"+DatasetUtility.getString(ds_cond,"ZRQSTA"));
		logger.debug( "@@@ ds_cond.PURMAN ===>"+DatasetUtility.getString(ds_cond,"PURMAN"));
		logger.debug( "@@@ ds_cond.QCMAN ===>"+DatasetUtility.getString(ds_cond,"QCMAN"));
		logger.debug( "@@@ ds_cond.GUBN ===>"+DatasetUtility.getString(ds_cond,"GUBN"));
		//--------------------------------------------------------------------------------------------
		 
		try {  		
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			QM0901000ParamVO param = new QM0901000ParamVO();
			// 파라메터SET
			param.setMandt(paramVO.getVariable("G_MANDT"));  				   	// SAP CLIENT 
			param.setFrqtdat(DatasetUtility.getString(ds_cond,"FR_QTDAT"));
			param.setToqtdat(DatasetUtility.getString(ds_cond,"TO_QTDAT"));
			param.setLifnr(DatasetUtility.getString(ds_cond,"LIFNR"));
			param.setZrqsta(DatasetUtility.getString(ds_cond,"ZRQSTA"));
			param.setPurman(DatasetUtility.getString(ds_cond,"PURMAN"));
			param.setQcman(DatasetUtility.getString(ds_cond,"QCMAN"));
			param.setGubn(DatasetUtility.getString(ds_cond,"GUBN"));
			//param.setReqman("2018144");
				
			ds_list.deleteAll();
			// 서비스CALL
			List<QM0901000> list = service.selectList(param);  
			//List<Sag0990> list1 = service.selectfileList(param);  
			
			for (int i = 0; i < list.size(); i++) {

				ds_list.appendRow();
			
				ds_list.setColumn(i, "MANDT",   list.get(i).getMandt());
				ds_list.setColumn(i, "ZRQSEQ",   list.get(i).getZrqseq());
				ds_list.setColumn(i, "ZRQSTA",   list.get(i).getZrqsta());
				ds_list.setColumn(i, "ZRQDAT",   list.get(i).getZrqdat());
				ds_list.setColumn(i, "REQMAN",   list.get(i).getReqman());				
				ds_list.setColumn(i, "QCMAN",   list.get(i).getQcman());
				ds_list.setColumn(i, "QCMAN_NM",   list.get(i).getQcman_nm());
				ds_list.setColumn(i, "QCMAN_MAIL",   list.get(i).getQcman_mail());
				ds_list.setColumn(i, "PURMAN",   list.get(i).getPurman());
				ds_list.setColumn(i, "PURMAN_NM",   list.get(i).getPurman_nm());
				ds_list.setColumn(i, "PURMAN_MAIL",   list.get(i).getPurman_mail());
				ds_list.setColumn(i, "LIFNR",   list.get(i).getLifnr());
				ds_list.setColumn(i, "LIFNR_NM",   list.get(i).getLifnr_nm());
				ds_list.setColumn(i, "PRODC",   list.get(i).getProdc());
				ds_list.setColumn(i, "CONDT",   list.get(i).getCondt());
				ds_list.setColumn(i, "CONMN",   list.get(i).getConmn());
				ds_list.setColumn(i, "TSTDT",   list.get(i).getTstdt());
				ds_list.setColumn(i, "TSTMN",   list.get(i).getTstmn());
				ds_list.setColumn(i, "CDATE",   list.get(i).getCdate());
				ds_list.setColumn(i, "CTIME",   list.get(i).getCtime());
				ds_list.setColumn(i, "CUSER",   list.get(i).getCuser());
				ds_list.setColumn(i, "REJT",   list.get(i).getRejt());
				
				/*ds_file.setColumn(i, "ZATGBN",   list1.get(i).getZatgbn());
				ds_file.setColumn(i, "ZATTPATH",   list1.get(i).getZattpath());
				ds_file.setColumn(i, "ZATTFN",   list1.get(i).getZattfn());*/
			}			
		
			resultVO.addDataset(ds_list); 
			//resultVO.addDataset(ds_file);
			
		} catch (Exception e) { 
			logger.error("@@@ QM0901000FindView Exception ERROR!!!");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    
		 
		// RFC 출력 dataset을 return
		logger.debug("@@@ ds_list.getRowCount ===>" + ds_list.getRowCount()); 
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);		
		
		model.addAttribute("resultVO", resultVO);     

		logger.debug("@@@ QM0901000FindView addAttribute end" + "");
		
		return view;
	}
	
	@RequestMapping(value="findListDetail")
	public View QM0901000FindListDetail(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");  
		Dataset ds_file = paramVO.getDataset("ds_file");  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try
		{ 
			QM0901000 param = new QM0901000();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			param.setZrqseq(DatasetUtility.getString(ds_cond,"ZRQSEQ"));
		
			ds_file.deleteAll();
			List<QM0901000> list = service.getListDetail(param);		
			
			for (int i = 0; i < list.size(); i++) {
				ds_file.appendRow();
				ds_file.setColumn(i, "ZATGBN"      , list.get(i).getZatgbn());
				ds_file.setColumn(i, "ZATTPATH"    , list.get(i).getZattpath());
				ds_file.setColumn(i, "ZATTFN"      , list.get(i).getZattfn()); 			
			}
			
			resultVO.addDataset(ds_file);    
			
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}	

	@RequestMapping(value="save")
	public View QM0901000SaveView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ QM0901000SaveView START");
		
		// get Input Dataset
		Dataset ds_list  = paramVO.getDataset("ds_list");
		Dataset ds_File  = paramVO.getDataset("ds_file");
		
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));	
		
		try {
			
			List<QM0901000> list = new ArrayList<QM0901000>();								
		
			for ( int i = 0 ; i < ds_list.getRowCount() ; i++ )
			{
				String MANDT    = paramVO.getVariable("G_MANDT");
				String ZRQSEQ   = DatasetUtility.getString(ds_list, i, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
				String ZRQDAT   = DatasetUtility.getString(ds_list, i, "ZRQDAT"  ); if (ZRQDAT  == null) ZRQDAT   = "";
				String ZRQSTA   = DatasetUtility.getString(ds_list, i, "ZRQSTA"  ); if (ZRQSTA  == null) ZRQSTA   = "";
				String REQMAN   = DatasetUtility.getString(ds_list, i, "REQMAN"  ); if (REQMAN  == null) REQMAN   = "";
				String QCMAN    = DatasetUtility.getString(ds_list, i, "QCMAN"   ); if (QCMAN   == null) QCMAN    = "";		
				String PURMAN   = DatasetUtility.getString(ds_list, i, "PURMAN"   ); if (PURMAN   == null) PURMAN    = "";
				String LIFNR    = DatasetUtility.getString(ds_list, i, "LIFNR"  ); if (LIFNR  == null) LIFNR   = "";
				String PRODC    = DatasetUtility.getString(ds_list, i, "PRODC"   ); if (PRODC   == null) PRODC    = "";
				String EMAIL    = DatasetUtility.getString(ds_list, i, "EMAIL"   ); if (EMAIL   == null) EMAIL    = "";
				String UUSER    = uuser;
								
				QM0901000 param = new QM0901000();
				
				param.setMandt   (MANDT); // SAP CLIENT
				param.setZrqseq  (ZRQSEQ);
				param.setZrqdat  (ZRQDAT);
				param.setZrqsta  (ZRQSTA);
				param.setReqman  (REQMAN);
				param.setQcman   (QCMAN);
				param.setPurman (PURMAN);
				param.setLifnr  (LIFNR);
				param.setProdc  (PRODC);
				param.setEmail  (EMAIL);
				param.setUuser  (UUSER);
				
				list.add(param);	
			}
					
			List<QM0901000> listFile = new ArrayList<QM0901000>();	
			
			for ( int j = 0 ; j < ds_File.getRowCount() ; j++ ) {
				String fMANDT    = paramVO.getVariable("G_MANDT");
				String fZRQSEQ   = DatasetUtility.getString(ds_File, j, "ZRQSEQ"  ); if (fZRQSEQ   == null) fZRQSEQ   = "";
				String fZATTSEQ  = DatasetUtility.getString(ds_File, j, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";		
				String fZRQSTAT  = DatasetUtility.getString(ds_File, j, "ZRQSTAT" ); if (fZRQSTAT  == null) fZRQSTAT  = "";
				String fZATGBN   = DatasetUtility.getString(ds_File, j, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
				String fZATTPATH = DatasetUtility.getString(ds_File, j, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
				String fZATTFN   = DatasetUtility.getString(ds_File, j, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
				String fFLAG     = DatasetUtility.getString(ds_File, j, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
				String fUUSER    = uuser;	
					
				QM0901000 paramFile = new QM0901000();
					
				paramFile.setMandt   (fMANDT);
				paramFile.setZrqseq  (fZRQSEQ);
				paramFile.setZattseq (fZATTSEQ);
				paramFile.setZrqstat (fZRQSTAT);
				paramFile.setZatgbn  (fZATGBN);
				paramFile.setZattpath(fZATTPATH);
				paramFile.setZattfn  (fZATTFN);
				paramFile.setFlag    (fFLAG);
				paramFile.setUuser   (fUUSER);
			
				listFile.add(paramFile);
									
			}			
			service.save(list, listFile);
				
			//ds_list.setColumn(0, "ZRQSEQ", strNewZrqSeq);
			//ds_File.setColumn(0, "ZRQSEQ", strNewZrqSeq);
				
			resultVO.addDataset(ds_list);
			resultVO.addDataset(ds_File);
						
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	  	
	@RequestMapping(value="request")
	public View QM0901000RequestView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ QM0901000RequestView START");
		
		// get Input Dataset
		Dataset ds_list  = paramVO.getDataset("ds_list");
		
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));	
		
		try {
			
			List<QM0901000> list = new ArrayList<QM0901000>();								
			
			for ( int i = 0 ; i < ds_list.getRowCount() ; i++ )
			{
				String MANDT    = paramVO.getVariable("G_MANDT");
				String ZRQSEQ   = DatasetUtility.getString(ds_list, i, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
				String ZRQDAT   = DatasetUtility.getString(ds_list, i, "ZRQDAT"  ); if (ZRQDAT  == null) ZRQDAT   = "";
				String ZRQSTA   = DatasetUtility.getString(ds_list, i, "ZRQSTA"  ); if (ZRQSTA  == null) ZRQSTA   = "";
				String REQMAN   = DatasetUtility.getString(ds_list, i, "REQMAN"  ); if (REQMAN  == null) REQMAN   = "";
				String QCMAN    = DatasetUtility.getString(ds_list, i, "QCMAN"   ); if (QCMAN   == null) QCMAN    = "";		
				String PURMAN   = DatasetUtility.getString(ds_list, i, "PURMAN"   ); if (PURMAN   == null) PURMAN    = "";
				String LIFNR    = DatasetUtility.getString(ds_list, i, "LIFNR"  ); if (LIFNR  == null) LIFNR   = "";
				String PRODC    = DatasetUtility.getString(ds_list, i, "PRODC"   ); if (PRODC   == null) PRODC    = "";
				String UUSER    = uuser;
								
				QM0901000 param = new QM0901000();
				
				param.setMandt   (MANDT); // SAP CLIENT
				param.setZrqseq  (ZRQSEQ);
				param.setZrqdat  (ZRQDAT);
				param.setZrqsta  (ZRQSTA);
				param.setReqman  (REQMAN);
				param.setQcman   (QCMAN);
				param.setPurman (PURMAN);
				param.setLifnr  (LIFNR);
				param.setProdc  (PRODC);	
				param.setUuser  (UUSER);
				
				list.add(param);	
			}
			service.request(list);
			
			//ds_list.setColumn(0, "ZRQSEQ", strNewZrqSeq);
			//ds_File.setColumn(0, "ZRQSEQ", strNewZrqSeq);
				
			resultVO.addDataset(ds_list);		
						
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	@RequestMapping(value="register")
	public View QM0901000RegisterView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ QM0901000RegisterView START");
		
		// get Input Dataset
		Dataset ds_list  = paramVO.getDataset("ds_list");
		Dataset ds_File  = paramVO.getDataset("ds_file");
		
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));	
		
		try {
			
			List<QM0901000> list = new ArrayList<QM0901000>();								
			
			for ( int i = 0 ; i < ds_list.getRowCount() ; i++ )
			{
				String MANDT    = paramVO.getVariable("G_MANDT");
				String ZRQSEQ   = DatasetUtility.getString(ds_list, i, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
				String ZRQDAT   = DatasetUtility.getString(ds_list, i, "ZRQDAT"  ); if (ZRQDAT  == null) ZRQDAT   = "";
				String ZRQSTA   = DatasetUtility.getString(ds_list, i, "ZRQSTA"  ); if (ZRQSTA  == null) ZRQSTA   = "";
				String REQMAN   = DatasetUtility.getString(ds_list, i, "REQMAN"  ); if (REQMAN  == null) REQMAN   = "";
				String QCMAN    = DatasetUtility.getString(ds_list, i, "QCMAN"   ); if (QCMAN   == null) QCMAN    = "";		
				String PURMAN   = DatasetUtility.getString(ds_list, i, "PURMAN"   ); if (PURMAN   == null) PURMAN    = "";
				String LIFNR    = DatasetUtility.getString(ds_list, i, "LIFNR"  ); if (LIFNR  == null) LIFNR   = "";
				String PRODC    = DatasetUtility.getString(ds_list, i, "PRODC"   ); if (PRODC   == null) PRODC    = "";
				String TSTDT    = DatasetUtility.getString(ds_list, i, "TSTDT"   ); if (TSTDT   == null) TSTDT    = "";			
				String UUSER    = uuser;
				
								
				QM0901000 param = new QM0901000();
				
				param.setMandt   (MANDT); // SAP CLIENT
				param.setZrqseq  (ZRQSEQ);
				param.setZrqdat  (ZRQDAT);
				param.setZrqsta  (ZRQSTA);
				param.setReqman  (REQMAN);
				param.setQcman   (QCMAN);
				param.setPurman (PURMAN);
				param.setLifnr  (LIFNR);
				param.setProdc  (PRODC);	
				param.setTstdt  (TSTDT);
				param.setUuser  (UUSER);
				
				list.add(param);	
			}
		
			List<QM0901000> listFile = new ArrayList<QM0901000>();	
			
			for ( int j = 0 ; j < ds_File.getRowCount() ; j++ ) {
				String fMANDT    = paramVO.getVariable("G_MANDT");
				String fZRQSEQ   = DatasetUtility.getString(ds_File, j, "ZRQSEQ"  ); if (fZRQSEQ   == null) fZRQSEQ   = "";
				String fZATTSEQ  = DatasetUtility.getString(ds_File, j, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";		
				String fZRQSTAT  = DatasetUtility.getString(ds_File, j, "ZRQSTAT" ); if (fZRQSTAT  == null) fZRQSTAT  = "";
				String fZATGBN   = DatasetUtility.getString(ds_File, j, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
				String fZATTPATH = DatasetUtility.getString(ds_File, j, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
				String fZATTFN   = DatasetUtility.getString(ds_File, j, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
				String fFLAG     = DatasetUtility.getString(ds_File, j, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
				String fUUSER    = uuser;	
					
				QM0901000 paramFile = new QM0901000();
					
				paramFile.setMandt   (fMANDT);
				paramFile.setZrqseq  (fZRQSEQ);
				paramFile.setZattseq (fZATTSEQ);
				paramFile.setZrqstat (fZRQSTAT);
				paramFile.setZatgbn  (fZATGBN);
				paramFile.setZattpath(fZATTPATH);
				paramFile.setZattfn  (fZATTFN);
				paramFile.setFlag    (fFLAG);
				paramFile.setUuser   (fUUSER);
			
				listFile.add(paramFile);									
			}			
						
			service.register(list, listFile);
			
			resultVO.addDataset(ds_list);		
			resultVO.addDataset(ds_File);
						
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	@RequestMapping(value="result")
	public View QM0901000ResulttView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ QM0901000ResulttView START");
		
		// get Input Dataset
		Dataset ds_list  = paramVO.getDataset("ds_list");
		
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));	
		
		try {
			
			List<QM0901000> list = new ArrayList<QM0901000>();								
			
			for ( int i = 0 ; i < ds_list.getRowCount() ; i++ )
			{
				String MANDT    = paramVO.getVariable("G_MANDT");
				String ZRQSEQ   = DatasetUtility.getString(ds_list, i, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
				String ZRQDAT   = DatasetUtility.getString(ds_list, i, "ZRQDAT"  ); if (ZRQDAT  == null) ZRQDAT   = "";
				String ZRQSTA   = DatasetUtility.getString(ds_list, i, "ZRQSTA"  ); if (ZRQSTA  == null) ZRQSTA   = "";
				String REQMAN   = DatasetUtility.getString(ds_list, i, "REQMAN"  ); if (REQMAN  == null) REQMAN   = "";
				String QCMAN    = DatasetUtility.getString(ds_list, i, "QCMAN"   ); if (QCMAN   == null) QCMAN    = "";		
				String PURMAN   = DatasetUtility.getString(ds_list, i, "PURMAN"   ); if (PURMAN   == null) PURMAN    = "";
				String LIFNR    = DatasetUtility.getString(ds_list, i, "LIFNR"  ); if (LIFNR  == null) LIFNR   = "";
				String PRODC    = DatasetUtility.getString(ds_list, i, "PRODC"   ); if (PRODC   == null) PRODC    = "";
				String UUSER    = uuser;
								
				QM0901000 param = new QM0901000();
				
				param.setMandt   (MANDT); // SAP CLIENT
				param.setZrqseq  (ZRQSEQ);
				param.setZrqdat  (ZRQDAT);
				param.setZrqsta  (ZRQSTA);
				param.setReqman  (REQMAN);
				param.setQcman   (QCMAN);
				param.setPurman (PURMAN);
				param.setLifnr  (LIFNR);
				param.setProdc  (PRODC);	
				param.setUuser  (UUSER);
				
				list.add(param);	
			}
			service.result(list);
			
			//ds_list.setColumn(0, "ZRQSEQ", strNewZrqSeq);
			//ds_File.setColumn(0, "ZRQSEQ", strNewZrqSeq);
				
			resultVO.addDataset(ds_list);		
						
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	@RequestMapping(value="confirm")
	public View QM0901000ConfirmView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ QM0901000ConfirmView START");
		
		// get Input Dataset
		Dataset ds_list  = paramVO.getDataset("ds_list");
		
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));	
		
		try {
			
			List<QM0901000> list = new ArrayList<QM0901000>();								
			
			for ( int i = 0 ; i < ds_list.getRowCount() ; i++ )
			{
				String MANDT    = paramVO.getVariable("G_MANDT");
				String ZRQSEQ   = DatasetUtility.getString(ds_list, i, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
				String ZRQDAT   = DatasetUtility.getString(ds_list, i, "ZRQDAT"  ); if (ZRQDAT  == null) ZRQDAT   = "";
				String ZRQSTA   = DatasetUtility.getString(ds_list, i, "ZRQSTA"  ); if (ZRQSTA  == null) ZRQSTA   = "";
				String REQMAN   = DatasetUtility.getString(ds_list, i, "REQMAN"  ); if (REQMAN  == null) REQMAN   = "";
				String QCMAN    = DatasetUtility.getString(ds_list, i, "QCMAN"   ); if (QCMAN   == null) QCMAN    = "";		
				String PURMAN   = DatasetUtility.getString(ds_list, i, "PURMAN"   ); if (PURMAN   == null) PURMAN    = "";
				String LIFNR    = DatasetUtility.getString(ds_list, i, "LIFNR"  ); if (LIFNR  == null) LIFNR   = "";
				String PRODC    = DatasetUtility.getString(ds_list, i, "PRODC"   ); if (PRODC   == null) PRODC    = "";
				String UUSER    = uuser;
								
				QM0901000 param = new QM0901000();
				
				param.setMandt   (MANDT); // SAP CLIENT
				param.setZrqseq  (ZRQSEQ);
				param.setZrqdat  (ZRQDAT);
				param.setZrqsta  (ZRQSTA);
				param.setReqman  (REQMAN);
				param.setQcman   (QCMAN);
				param.setPurman (PURMAN);
				param.setLifnr  (LIFNR);
				param.setProdc  (PRODC);	
				param.setUuser  (UUSER);
				
				list.add(param);	
			}
			service.confirm(list);
			
			//ds_list.setColumn(0, "ZRQSEQ", strNewZrqSeq);
			//ds_File.setColumn(0, "ZRQSEQ", strNewZrqSeq);
				
			resultVO.addDataset(ds_list);		
						
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	@RequestMapping(value="reject")
	public View QM0901000RejectView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ QM0901000RejectView START");
		
		// get Input Dataset
		Dataset ds_list  = paramVO.getDataset("ds_list");
		
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));	
		
		try {
			
			List<QM0901000> list = new ArrayList<QM0901000>();								
			
			for ( int i = 0 ; i < ds_list.getRowCount() ; i++ )
			{
				String MANDT    = paramVO.getVariable("G_MANDT");
				String ZRQSEQ   = DatasetUtility.getString(ds_list, i, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
				String ZRQDAT   = DatasetUtility.getString(ds_list, i, "ZRQDAT"  ); if (ZRQDAT  == null) ZRQDAT   = "";
				String ZRQSTA   = DatasetUtility.getString(ds_list, i, "ZRQSTA"  ); if (ZRQSTA  == null) ZRQSTA   = "";
				String REQMAN   = DatasetUtility.getString(ds_list, i, "REQMAN"  ); if (REQMAN  == null) REQMAN   = "";
				String QCMAN    = DatasetUtility.getString(ds_list, i, "QCMAN"   ); if (QCMAN   == null) QCMAN    = "";		
				String PURMAN   = DatasetUtility.getString(ds_list, i, "PURMAN"   ); if (PURMAN   == null) PURMAN    = "";
				String LIFNR    = DatasetUtility.getString(ds_list, i, "LIFNR"  ); if (LIFNR  == null) LIFNR   = "";
				String PRODC    = DatasetUtility.getString(ds_list, i, "PRODC"   ); if (PRODC   == null) PRODC    = "";
				String REJT     = DatasetUtility.getString(ds_list, i, "REJT"   ); if (REJT   == null) REJT    = "";
				String UUSER    = uuser;
								
				QM0901000 param = new QM0901000();
				
				param.setMandt   (MANDT); // SAP CLIENT
				param.setZrqseq  (ZRQSEQ);
				param.setZrqdat  (ZRQDAT);
				param.setZrqsta  (ZRQSTA);
				param.setReqman  (REQMAN);
				param.setQcman   (QCMAN);
				param.setPurman (PURMAN);
				param.setLifnr  (LIFNR);
				param.setProdc  (PRODC);		
				param.setRejt   (REJT);
				param.setUuser  (UUSER);
				
				list.add(param);	
			}
			service.reject(list);
			
			//ds_list.setColumn(0, "ZRQSEQ", strNewZrqSeq);
			//ds_File.setColumn(0, "ZRQSEQ", strNewZrqSeq);
				
			resultVO.addDataset(ds_list);		
						
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
}
