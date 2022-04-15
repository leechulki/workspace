package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0243;
import hdel.sd.ses.domain.Ses0243ParamVO;
import hdel.sd.ses.domain.ZSDS0016;
import hdel.sd.ses.domain.ZSDS0017;

import hdel.sd.ses.service.Ses0243S;
import com.helco.xf.comm.ZQMSEMSG;

import hdel.lib.service.MailService;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.SapFunction;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0243")
public class Ses0243C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0243S service;
	
	/*------------------------------------------------------------
	 *  Header Select(header)
	 ------------------------------------------------------------*/
	@RequestMapping(value="header")
	public View Ses0243FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_header");	// UI로 return할 out dataset  
		 
		try { 
			Ses0243ParamVO param = new Ses0243ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			String edatufr   = DatasetUtility.getString(dsCond, "EDATUFR");
			String edatuto   = DatasetUtility.getString(dsCond, "EDATUTO");
			String vbeln   = DatasetUtility.getString(dsCond, "VBELN");
			String bstkd   = DatasetUtility.getString(dsCond, "BSTKD");
			String sedat   = DatasetUtility.getString(dsCond, "SEDAT");
			String vkbur   = DatasetUtility.getString(dsCond, "VKBUR");
			String vkgrp   = DatasetUtility.getString(dsCond, "VKGRP");
			String zkunnr   = DatasetUtility.getString(dsCond, "ZKUNNR");
			String region   = DatasetUtility.getString(dsCond, "REGION");
			//String zrqflg   = DatasetUtility.getString(dsCond, "ZRQFLG");
			
//			System.out.println("============== VKBUR >> " + DatasetUtility.getString(dsCond, "VKBUR"));
//			System.out.println("============== VKGRP >> " + DatasetUtility.getString(dsCond, "VKGRP"));
//			System.out.println("============== ZKUNNR >> " + DatasetUtility.getString(dsCond, "ZKUNNR"));
//			System.out.println("============== REGION >> " + DatasetUtility.getString(dsCond, "REGION"));
			
			if (vbeln   == null) vbeln   = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setEdatufr(edatufr);
			param.setEdatuto(edatuto);
			param.setVbeln(vbeln);
			param.setBstkd(bstkd);
			param.setSedat(sedat);
			param.setVkbur(vkbur);
			param.setVkgrp(vkgrp);
			param.setZkunnr(zkunnr);
			param.setRegion(region);
			
			//--------------------------------------------------------------------------------------------
			
			List<Ses0243> list = service.searchHeader(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if (dsList.getColumnID(iCol).equals("MANDT"))                 dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("ZZPJT_ID"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZzpjt_id());
					else if (dsList.getColumnID(iCol).equals("VBELN"))             dsList.setColumn(iRow, iCol, list.get(iRow).getVbeln());
					else if (dsList.getColumnID(iCol).equals("ZSEQ"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZseq()); 
					else if (dsList.getColumnID(iCol).equals("BSTKD"))       	    dsList.setColumn(iRow, iCol, list.get(iRow).getBstkd()); 
					else if (dsList.getColumnID(iCol).equals("REGION"))           dsList.setColumn(iRow, iCol, list.get(iRow).getRegion()); 
					else if (dsList.getColumnID(iCol).equals("LAND1"))      	    dsList.setColumn(iRow, iCol, list.get(iRow).getLand1()); 
					else if (dsList.getColumnID(iCol).equals("LANDNM"))          dsList.setColumn(iRow, iCol, list.get(iRow).getLandnm()); 
					else if (dsList.getColumnID(iCol).equals("NETWR"))     	    dsList.setColumn(iRow, iCol, list.get(iRow).getNetwr()); 
					else if (dsList.getColumnID(iCol).equals("KZWI5"))     		    dsList.setColumn(iRow, iCol, list.get(iRow).getKzwi5()); 
					else if (dsList.getColumnID(iCol).equals("WAERK"))    		    dsList.setColumn(iRow, iCol, list.get(iRow).getWaerk()); 
					else if (dsList.getColumnID(iCol).equals("PONETWR"))         dsList.setColumn(iRow, iCol, list.get(iRow).getPonetwr()); 
					else if (dsList.getColumnID(iCol).equals("POWAERK"))         dsList.setColumn(iRow, iCol, list.get(iRow).getPowaerk()); 
					else if (dsList.getColumnID(iCol).equals("MAILADDR"))        dsList.setColumn(iRow, iCol, list.get(iRow).getMailaddr()); 
					else if (dsList.getColumnID(iCol).equals("INCO1"))        		dsList.setColumn(iRow, iCol, list.get(iRow).getInco1()); 
					else if (dsList.getColumnID(iCol).equals("INCO2"))       		dsList.setColumn(iRow, iCol, list.get(iRow).getInco2()); 
					else if (dsList.getColumnID(iCol).equals("ZTERMT"))           dsList.setColumn(iRow, iCol, list.get(iRow).getZtermt()); 
					else if (dsList.getColumnID(iCol).equals("SPECDWG"))     		dsList.setColumn(iRow, iCol, list.get(iRow).getSpecdwg()); 
					else if (dsList.getColumnID(iCol).equals("EDATU"))      		dsList.setColumn(iRow, iCol, list.get(iRow).getEdatu()); 
					else if (dsList.getColumnID(iCol).equals("ZFWDING"))   	    dsList.setColumn(iRow, iCol, list.get(iRow).getZfwding()); 
					else if (dsList.getColumnID(iCol).equals("SEDAT"))    		    dsList.setColumn(iRow, iCol, list.get(iRow).getSedat()); 
					else if (dsList.getColumnID(iCol).equals("SEZZT"))     		    dsList.setColumn(iRow, iCol, list.get(iRow).getSezzt()); 
					else if (dsList.getColumnID(iCol).equals("ERDAT"))     	    dsList.setColumn(iRow, iCol, list.get(iRow).getErdat()); 
					else if (dsList.getColumnID(iCol).equals("ERZZT"))     		    dsList.setColumn(iRow, iCol, list.get(iRow).getErzzt()); 
					else if (dsList.getColumnID(iCol).equals("ERNAM"))    	    dsList.setColumn(iRow, iCol, list.get(iRow).getErnam());
					else if (dsList.getColumnID(iCol).equals("RECAD_DA"))        dsList.setColumn(iRow, iCol, list.get(iRow).getRecad_da());
					else if (dsList.getColumnID(iCol).equals("REMARK1"))         dsList.setColumn(iRow, iCol, list.get(iRow).getRemark1());
					else if (dsList.getColumnID(iCol).equals("REMARK2"))         dsList.setColumn(iRow, iCol, list.get(iRow).getRemark2());
					else if (dsList.getColumnID(iCol).equals("REMARK3"))         dsList.setColumn(iRow, iCol, list.get(iRow).getRemark3());
					else if (dsList.getColumnID(iCol).equals("REMARK4"))         dsList.setColumn(iRow, iCol, list.get(iRow).getRemark4());
					else if (dsList.getColumnID(iCol).equals("REMARK5"))         dsList.setColumn(iRow, iCol, list.get(iRow).getRemark5());
					else if (dsList.getColumnID(iCol).equals("ACDAT"))            dsList.setColumn(iRow, iCol, list.get(iRow).getAcdat());
				}
				
			} 
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			//resultVO.addDataset(ds_error); 
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	/*------------------------------------------------------------
	 *  Detail Select(detail)
	 ------------------------------------------------------------*/
	@RequestMapping(value="detail")
	public View Ses0243DtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_detail");	// UI로 return할 out dataset  
		 
		try { 
			Ses0243ParamVO param = new Ses0243ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			String mandt = DatasetUtility.getString(dsCond, "MANDT");
			String vbeln   = DatasetUtility.getString(dsCond, "VBELN");
			String zseq   = DatasetUtility.getString(dsCond, "ZSEQ");
			
			if (vbeln   == null) vbeln   = "";
			if (zseq   == null) zseq   = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setVbeln(vbeln);
			param.setZseq(zseq);
			
			List<Ses0243> list = service.searchDetail(param);  	// 서비스CALL
			dsList.deleteAll();
			
			for (int i=0;i<list.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "MANDT"    		, list.get(i).getMandt());
				dsList.setColumn(i, "VBELN"    		, list.get(i).getVbeln());
				dsList.setColumn(i, "ZSEQ"     	 	, list.get(i).getZseq()); 
				dsList.setColumn(i, "POSNR"     		, list.get(i).getPosnr());	
				dsList.setColumn(i, "POSID"      		, list.get(i).getPosid()); 
				dsList.setColumn(i, "WWMOD"   		, list.get(i).getWwmod()); 
				dsList.setColumn(i, "ARKTX"    		, list.get(i).getArktx()); 
				dsList.setColumn(i, "KWMENG"       	, list.get(i).getKwmeng()); 
				dsList.setColumn(i, "VRKME"     	 	, list.get(i).getVrkme()); 
				dsList.setColumn(i, "NETWR"      		, list.get(i).getNetwr()); 
				dsList.setColumn(i, "WAERK"      		, list.get(i).getWaerk()); 
				dsList.setColumn(i, "ERDAT"      		, list.get(i).getErdat()); 
				dsList.setColumn(i, "ERZZT"			, list.get(i).getErzzt());
				dsList.setColumn(i, "ERNAM"       	, list.get(i).getErnam());
				dsList.setColumn(i, "EDATU"      		, list.get(i).getEdatu());
				dsList.setColumn(i, "PONETWR"      	, list.get(i).getPonetwr());
				dsList.setColumn(i, "POWAERK"      	, list.get(i).getPowaerk());
				dsList.setColumn(i, "KZWI5"      		, list.get(i).getKzwi5());
			}
			MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
			resultVO.addDataset(dsList); 

//			System.out.println("============== ds_detail.getRowCount() " + dsList.getRowCount());
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	/*@RequestMapping(value="attribute")
	public View Ses0243DtlView2(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_attribute");	// UI로 return할 out dataset  
		 
		try { 
			Ses0243ParamVO param = new Ses0243ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			String mandt = DatasetUtility.getString(dsCond, "MANDT");
			String vbeln   = DatasetUtility.getString(dsCond, "VBELN");
			String zseq   = DatasetUtility.getString(dsCond, "ZSEQ");
			String posnr   = DatasetUtility.getString(dsCond, "POSNR");
			
			if (vbeln   == null) vbeln   = "";
			if (zseq   == null) zseq   = "";
			if (posnr   == null) posnr   = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setVbeln(vbeln);
			param.setZseq(zseq);
			param.setPosnr(posnr);
			
			List<Ses0243> list = service.searchAttribute(param);  	// 서비스CALL
			dsList.deleteAll();
			
			for (int i=0;i<list.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "MANDT"    		, list.get(i).getMandt());
				dsList.setColumn(i, "VBELN"    		, list.get(i).getVbeln());
				dsList.setColumn(i, "ZSEQ"     	 	, list.get(i).getZseq()); 
				dsList.setColumn(i, "POSNR"     	, list.get(i).getPosnr());					
				dsList.setColumn(i, "CHARACTERISTIC", list.get(i).getCharacteristic());
				dsList.setColumn(i, "CHARACTERISTICNM", list.get(i).getCharacteristicnm());
				dsList.setColumn(i, "POSID"      	, list.get(i).getPosid()); 
				dsList.setColumn(i, "CUOBJ"   		, list.get(i).getCuobj()); 
				dsList.setColumn(i, "VALUE"    		, list.get(i).getValue()); 
				dsList.setColumn(i, "ERDAT"      	, list.get(i).getErdat()); 
				dsList.setColumn(i, "ERZZT"			, list.get(i).getErzzt());
				dsList.setColumn(i, "ERNAM"       	, list.get(i).getErnam());
			}
			MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
			resultVO.addDataset(dsList); 

			System.out.println("============== attribute.getRowCount() " + dsList.getRowCount());
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}*/
	
	/*------------------------------------------------------------
	 *  Popup Select(hogi)
	 ------------------------------------------------------------*/
	@RequestMapping(value="hogi")
	public View Ses0243hogiView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_hogi");	// UI로 return할 out dataset  
		 
		try { 
			Ses0243ParamVO param = new Ses0243ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			String mandt = DatasetUtility.getString(dsCond, "MANDT");
			String edatufr   = DatasetUtility.getString(dsCond, "EDATUFR");
			String edatuto   = DatasetUtility.getString(dsCond, "EDATUTO");
			String vbeln   = DatasetUtility.getString(dsCond, "VBELN");
			String region   = DatasetUtility.getString(dsCond, "REGION");
			String vkbur   = DatasetUtility.getString(dsCond, "VKBUR");
			String vkgrp   = DatasetUtility.getString(dsCond, "VKGRP");
			String zkunnr   = DatasetUtility.getString(dsCond, "ZKUNNR");
			
			
			if (vbeln   == null) vbeln   = "";
			//if (zseq   == null) zseq   = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setEdatufr(edatufr);
			param.setEdatuto(edatuto);
			param.setVbeln(vbeln);
			param.setVkbur(vkbur);
			param.setVkgrp(vkgrp);
			param.setZkunnr(zkunnr);
			param.setRegion(region);
			
			List<Ses0243> list = service.searchHogi(param);  	// 서비스CALL
			dsList.deleteAll();
			
			for (int i=0;i<list.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "MANDT"    		, list.get(i).getMandt());
				dsList.setColumn(i, "ZZPJT_ID"    		, list.get(i).getZzpjt_id());
				dsList.setColumn(i, "VBELN"    		, list.get(i).getVbeln());
				dsList.setColumn(i, "ZSEQ"     	 	, list.get(i).getZseq()); 
				dsList.setColumn(i, "BSTKD"     	 	, list.get(i).getBstkd()); 				
				dsList.setColumn(i, "POSID"      		, list.get(i).getPosid()); 
				dsList.setColumn(i, "POSNR"     		, list.get(i).getPosnr());	
				dsList.setColumn(i, "EDATU"     	 	, list.get(i).getEdatu());
				dsList.setColumn(i, "WWMOD"   		, list.get(i).getWwmod()); 
				dsList.setColumn(i, "ARKTX"    		, list.get(i).getArktx()); 
				dsList.setColumn(i, "KWMENG"       	, list.get(i).getKwmeng()); 
				dsList.setColumn(i, "VRKME"      		, list.get(i).getVrkme()); 
				dsList.setColumn(i, "NETWR"     	 	, list.get(i).getNetwr()); 
				dsList.setColumn(i, "PONETWR"      	, list.get(i).getPonetwr()); 
				dsList.setColumn(i, "WAERK"     	 	, list.get(i).getWaerk()); 
				dsList.setColumn(i, "RECAD_DA"      	, list.get(i).getRecad_da()); 
				dsList.setColumn(i, "REGION"      	, list.get(i).getRegion()); 
				dsList.setColumn(i, "INCO1"      		, list.get(i).getInco1()); 
				dsList.setColumn(i, "INCO2"      		, list.get(i).getInco2()); 
				dsList.setColumn(i, "ZTERMT"      	, list.get(i).getZtermt()); 
				dsList.setColumn(i, "SPECDWG"      	, list.get(i).getSpecdwg()); 
				dsList.setColumn(i, "ZFWDING"      	, list.get(i).getZfwding()); 
				dsList.setColumn(i, "ACDAT"      		, list.get(i).getAcdat()); 
				dsList.setColumn(i, "SEDAT"      		, list.get(i).getSedat()); 
				dsList.setColumn(i, "SEZZT"      		, list.get(i).getSezzt()); 
				dsList.setColumn(i, "POEDATU"		, list.get(i).getPoedatu());
				//dsList.setColumn(i, "ERNAM"       	, list.get(i).getErnam());
				//System.out.println("###SPECDWG >> " + dsList.getColumnAsString(i, "SPECDWG"));
			}
			MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
			resultVO.addDataset(dsList); 

//			System.out.println("============== ds_hogiTemp.getRowCount() " + dsList.getRowCount());
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}	

	/*------------------------------------------------------------
	 *  Header Save(updateHeader)
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Ses0243updateHeader(MipParameterVO paramVO, Model model) { 
  
		//IN DATASET
		Dataset ds_header = paramVO.getDataset("ds_header");
		Dataset ds_detail = paramVO.getDataset("ds_detail");
		
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// ds_header save
			service.updateHeader(paramVO, model, session);
			
			// ds_detail save
			service.updateDetail(paramVO, model, session);

			} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
//		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}		
	
	/*------------------------------------------------------------
	 *  Detail Save(updateDetail)
	 ------------------------------------------------------------*/
	/*@RequestMapping(value="updateHeader")
	public View Ses0243updateDetail(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.updateDetail(paramVO, model, session);

			} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}	*/
	
	/*------------------------------------------------------------
	 *  상해송부PO 메일전송(sendupdateHeader)
	 ------------------------------------------------------------*/
	@RequestMapping(value="sendmail")
	public View Ses0243sendMail(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.sendMailheader(paramVO, model, session);

			} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
//		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}		
	
	/*------------------------------------------------------------
	 * M/N생성(checkVbak)
	 ------------------------------------------------------------*/
	@RequestMapping(value="checkVbak")
	public View Ses0243checkVbak(MipParameterVO paramVO, Model model) {

		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list");	// UI로 return할 out dataset
		Dataset dsHogi	= paramVO.getDataset("ds_hogi");	// UI로 return할 out dataset
		Dataset ds_sap	= paramVO.getDataset("ds_sapinsert");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0016[] 	slist 	= new ZSDS0016[]{};  // WFC output list
		ZSDS0017[] 	slist2 	= new ZSDS0017[]{};  // output list
		ZQMSEMSG[] 	slistMsg = new ZQMSEMSG[]{};		
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI로 return할 out dataset
		Dataset 	ds2 		= null;				// UI로 return할 out dataset2
		Dataset 	ds_Error = null;				// UI로 return할 오류메세지 dataset 
		
		String sRECAD_DA = "00000000";
		String sHogi = null;
			 
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		
		try { 
			Ses0243ParamVO param = new Ses0243ParamVO();

			service.createDao(session); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			String vbeln   = DatasetUtility.getString(dsCond, "VBELN");
		
//			System.out.println("============== VBELN" + DatasetUtility.getString(dsCond, "VBELN"));
						
			if (vbeln   == null) vbeln   = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setVbeln(vbeln);
			
			//--------------------------------------------------------------------------------------------
			
			//List<Ses0243> list = service.checkVbak(param);   			// 서비스CALL
			//dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
		/*	for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if (dsList.getColumnID(iCol).equals("MANDT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("VBELN"))        dsList.setColumn(iRow, iCol, list.get(iRow).getVbeln());
					else if (dsList.getColumnID(iCol).equals("RECAD_DA"))        dsList.setColumn(iRow, iCol, list.get(iRow).getRecad_da());
					
					sRECAD_DA = list.get(iRow).getRecad_da();
				}
			} 
			System.out.println("============== 승인일자확인===" + dsList.getRowCount());
			System.out.println("============== sRECAD_DA===" + sRECAD_DA + "==");
			*/
			//if(!"00000000".equals(sRECAD_DA))	//미승인(승인일자 입력안됐을경우)
			//{			
				List<Ses0243> lHogi = null;
				
				//if(list.size() > 0)
				//{
					try {
						// header,detail save
						lHogi = service.insertVblen(paramVO, model, session);
//						System.out.println("@@list size ===> " +lHogi.size() );
							for (int i=0;i<lHogi.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사
								/*
								dsHogi.appendRow(); 	// 행추가
								dsHogi.setColumn(i, "MANDT"    		, lHogi.get(i).getMandt());
								dsHogi.setColumn(i, "VBELN"    		, lHogi.get(i).getVbeln());
								dsHogi.setColumn(i, "ZSEQ"     	 	, lHogi.get(i).getZseq()); 
								dsHogi.setColumn(i, "POSNR"     	, lHogi.get(i).getPosnr());	
								dsHogi.setColumn(i, "POSID"      	, lHogi.get(i).getPosid()); 
								*/
								//sHogi = dsHogi.getColumni, "POSID").toString();
								
								Object obj[] = new Object[]{
										  slist2
										, lHogi.get(i).getPosid()
										, slistMsg
										, slist
										
								}; 							 
															
								// RFC CALL
								SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ses.domain.ZWEB_SD_HOGI_SPEC", obj, false);
								
								// RFC 출력구조체로 out dataset 생성
								ds = CallSAP.isJCO() ? new Dataset() : ZSDS0016.getDataset();
								ds.setId("ds_list");  
								//ds2 = CallSAP.isJCO() ? new Dataset() : ZSDS0017.getDataset();
								//ds2.setId("ds_list2");  			
								
								// RFC 호출결과를 out dataset에 옮기기  
								CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
								//CallSAP.moveObj2Ds(ds2, stub.getOutput("H_ITAB"));
								
								slistMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
								ds_Error = CallSAP.makeErrorInfo(slistMsg);	
								
//								System.out.print("\n@@@ ds.getRowCount()!!!   " + ds.getRowCount());
								
								//for(int j=0; j<ds.getRowCount(); j++)			//2012.11.23.khs 입력된 hogi count로 변경
								for (int j=0; j<lHogi.size(); j++)
								{
									ds_sap.appendRow();
									ds_sap.setColumn(j, "MANDT"    		, lHogi.get(i).getMandt());
									ds_sap.setColumn(j, "VBELN"    		, lHogi.get(i).getVbeln());
									ds_sap.setColumn(j, "ZSEQ"     	 	, lHogi.get(i).getZseq()); 
									ds_sap.setColumn(j, "POSNR"     	, lHogi.get(i).getPosnr());	
									ds_sap.setColumn(j, "POSID"      	, lHogi.get(i).getPosid());									
									ds_sap.setColumn(j, "CHARACTERISTIC", ds.getColumn(j, "NAM_CHAR")); 
									ds_sap.setColumn(j, "VALUE"      	, ds.getColumn(j, "VALUE1"));
									ds_sap.setColumn(j, "ERNAM"      	, DatasetUtility.getString(dsCond, "ERNAM"));		
									
									/*
									System.out.println("MANDT ======="+    		 lHogi.get(i).getMandt());
									System.out.println("VBELN======="+    		 lHogi.get(i).getVbeln());
									System.out.println("ZSEQ======="+     	 	 lHogi.get(i).getZseq()); 
									System.out.println("POSNR======="+      lHogi.get(i).getPosnr());	
									System.out.println("POSID======="+      	 lHogi.get(i).getPosid());									
									System.out.println("CHARACTERISTIC======="+ ds.getColumn(j, "NAM_CHAR")); 
									System.out.println("VALUE======="+      	 ds.getColumn(j, "VALUE1"));
									*/ 
								}
								service.insertSap(ds_sap, model, session);				//2012.11.21.test 임시주석 khs
								ds_sap.deleteAll();
							}
							
	
						} catch (Exception e ) {
						
						e.printStackTrace();
						// 호출결과  
	//					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");					
					}				
				//}
			//}
						
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			
			//resultVO.addDataset(dsList);
			resultVO.addDataset(ds); 
			
//			System.out.println("============== 마무리=== " + dsList.getRowCount());
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}	
		
	/*------------------------------------------------------------
	 *  Header Receipt Date Update (receipt)
	 ------------------------------------------------------------*/
	@RequestMapping(value="receipt")
	public View Ses0243Receipt(MipParameterVO paramVO, Model model) { 
		  
		//IN DATASET
		Dataset ds_header = paramVO.getDataset("ds_header");
		
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
				// ds_header  Receit Date save
				service.updateHeaderReceiptDate(paramVO, model, session);			
			} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 		
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}	
	
	/*------------------------------------------------------------
	 *  상해송부PO 사양insert(RFC insert)
	 ------------------------------------------------------------*/
	/*
	@RequestMapping(value="saveSap")
	public View Ses0320SaveSap(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.insertSap(paramVO, model, session);

			} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;	
	}
	 */
}
