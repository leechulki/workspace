package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.ZLang;
import hdel.sd.ses.domain.Ses0330;
import hdel.sd.ses.domain.Ses0330ParamVO;
import hdel.sd.ses.domain.ZSDS0016;
import hdel.sd.ses.domain.ZSDS0017;

import hdel.sd.ses.service.Ses0330S;
import com.helco.xf.comm.ZQMSEMSG;

import hdel.lib.service.MailService;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0330")
public class Ses0330C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0330S service;
	
	@RequestMapping(value="header")
	public View Ses0330FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_header");	// UI로 return할 out dataset  
		 
		try { 
			Ses0330ParamVO param = new Ses0330ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			String edatufr   = DatasetUtility.getString(dsCond, "EDATUFR");
			String edatuto   = DatasetUtility.getString(dsCond, "EDATUTO");
			String vbeln   = DatasetUtility.getString(dsCond, "VBELN");
			String bstkd   = DatasetUtility.getString(dsCond, "BSTKD");
			String sedat   = DatasetUtility.getString(dsCond, "SEDAT");
			String spras	= DatasetUtility.getString(dsCond, "SPRAS");
			String zzpjt_id	= DatasetUtility.getString(dsCond, "ZZPJT_ID");		// PROJ.No
			String land1	= DatasetUtility.getString(dsCond, "LAND1");		// 국가코드
			String vkbur	= DatasetUtility.getString(dsCond, "VKBUR");		// 부서
			String vkgrp	= DatasetUtility.getString(dsCond, "VKGRP");		// 팀
			String zkunnr	= DatasetUtility.getString(dsCond, "ZKUNNR");		// 담당자
			String trade	= DatasetUtility.getString(dsCond, "TRADE");		// 중계무역여부
			
			if (vbeln   == null) vbeln   = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setEdatufr(edatufr);
			param.setEdatuto(edatuto);
			param.setVbeln(vbeln);
			param.setBstkd(bstkd);
			param.setSedat(sedat);

			if ("ko".equals(spras))		{
				param.setSpras("3");
				param.setLang(spras);
			}else	{
				param.setSpras("E");
				param.setLang(spras);
			}

			param.setZzpjt_id(zzpjt_id);
			param.setLand1(land1);
			param.setVkbur(vkbur);
			param.setVkgrp(vkgrp);
			param.setZkunnr(zkunnr);
			param.setTrade(trade);
			
			//--------------------------------------------------------------------------------------------
			
			List<Ses0330> list = service.searchHeader(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if (dsList.getColumnID(iCol).equals("MANDT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("VBELN"))        dsList.setColumn(iRow, iCol, list.get(iRow).getVbeln());
					else if (dsList.getColumnID(iCol).equals("ZSEQ"))         dsList.setColumn(iRow, iCol, list.get(iRow).getZseq()); 
					else if (dsList.getColumnID(iCol).equals("BSTKD"))        dsList.setColumn(iRow, iCol, list.get(iRow).getBstkd()); 
					else if (dsList.getColumnID(iCol).equals("REGION"))       dsList.setColumn(iRow, iCol, list.get(iRow).getRegion()); 
					else if (dsList.getColumnID(iCol).equals("INCO1"))        dsList.setColumn(iRow, iCol, list.get(iRow).getInco1()); 
					else if (dsList.getColumnID(iCol).equals("INCO2"))        dsList.setColumn(iRow, iCol, list.get(iRow).getInco2()); 
					else if (dsList.getColumnID(iCol).equals("ZTERMT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZtermt()); 
					else if (dsList.getColumnID(iCol).equals("SPECDWG"))      dsList.setColumn(iRow, iCol, list.get(iRow).getSpecdwg()); 
					else if (dsList.getColumnID(iCol).equals("EDATU"))        dsList.setColumn(iRow, iCol, list.get(iRow).getEdatu()); 
					else if (dsList.getColumnID(iCol).equals("ZFWDING"))      dsList.setColumn(iRow, iCol, list.get(iRow).getZfwding()); 
					else if (dsList.getColumnID(iCol).equals("SEDAT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getSedat()); 
					else if (dsList.getColumnID(iCol).equals("SEZZT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getSezzt()); 
					else if (dsList.getColumnID(iCol).equals("ERDAT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getErdat()); 
					else if (dsList.getColumnID(iCol).equals("ERZZT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getErzzt()); 
					else if (dsList.getColumnID(iCol).equals("ERNAM"))        dsList.setColumn(iRow, iCol, list.get(iRow).getErnam());
					else if (dsList.getColumnID(iCol).equals("RECAD_DA"))     dsList.setColumn(iRow, iCol, list.get(iRow).getRecad_da());
					
					else if (dsList.getColumnID(iCol).equals("NETWR"))     		dsList.setColumn(iRow, iCol, list.get(iRow).getNetwr());
					else if (dsList.getColumnID(iCol).equals("WAERK"))			dsList.setColumn(iRow, iCol, list.get(iRow).getWaerk());
					
					else if (dsList.getColumnID(iCol).equals("ZZPJT_ID"))		dsList.setColumn(iRow, iCol, list.get(iRow).getZzpjt_id());
					else if (dsList.getColumnID(iCol).equals("BSTNK"))			dsList.setColumn(iRow, iCol, list.get(iRow).getBstnk());
					else if (dsList.getColumnID(iCol).equals("LAND1"))			dsList.setColumn(iRow, iCol, list.get(iRow).getLand1());
					else if (dsList.getColumnID(iCol).equals("LANDX"))			dsList.setColumn(iRow, iCol, list.get(iRow).getLandx());
					else if (dsList.getColumnID(iCol).equals("VKBUR"))			dsList.setColumn(iRow, iCol, list.get(iRow).getVkbur());
					else if (dsList.getColumnID(iCol).equals("BURBEZEI"))		dsList.setColumn(iRow, iCol, list.get(iRow).getBurbezei());
					else if (dsList.getColumnID(iCol).equals("VKGRP"))			dsList.setColumn(iRow, iCol, list.get(iRow).getVkgrp());
					else if (dsList.getColumnID(iCol).equals("GRPBEZEI"))		dsList.setColumn(iRow, iCol, list.get(iRow).getGrpbezei());
					else if (dsList.getColumnID(iCol).equals("ZKUNNR"))			dsList.setColumn(iRow, iCol, list.get(iRow).getZkunnr());
					else if (dsList.getColumnID(iCol).equals("NAME1"))			dsList.setColumn(iRow, iCol, list.get(iRow).getName1());
					else if (dsList.getColumnID(iCol).equals("KZWI5"))			dsList.setColumn(iRow, iCol, list.get(iRow).getKzwi5());
					else if (dsList.getColumnID(iCol).equals("SHNETWR"))		dsList.setColumn(iRow, iCol, list.get(iRow).getShnetwr());
					else if (dsList.getColumnID(iCol).equals("SHWAERK"))		dsList.setColumn(iRow, iCol, list.get(iRow).getShwaerk());
					else if (dsList.getColumnID(iCol).equals("MAILADDR"))		dsList.setColumn(iRow, iCol, list.get(iRow).getMailaddr());
					else if (dsList.getColumnID(iCol).equals("ACDAT"))			dsList.setColumn(iRow, iCol, list.get(iRow).getAcdat());
					else if (dsList.getColumnID(iCol).equals("REMARK1"))		dsList.setColumn(iRow, iCol, list.get(iRow).getRemark1());
					else if (dsList.getColumnID(iCol).equals("REMARK2"))		dsList.setColumn(iRow, iCol, list.get(iRow).getRemark2());
					else if (dsList.getColumnID(iCol).equals("REMARK3"))		dsList.setColumn(iRow, iCol, list.get(iRow).getRemark3());
					else if (dsList.getColumnID(iCol).equals("REMARK4"))		dsList.setColumn(iRow, iCol, list.get(iRow).getRemark4());
					else if (dsList.getColumnID(iCol).equals("REMARK5"))		dsList.setColumn(iRow, iCol, list.get(iRow).getRemark5());
				}
			} 
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			
//			System.out.println("============== ds_header.getRowCount() " + dsList.getRowCount());
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	@RequestMapping(value="detail")
	public View Ses0330DtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_detail");	// UI로 return할 out dataset  
		 
		try { 
			Ses0330ParamVO param = new Ses0330ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			String vbeln = DatasetUtility.getString(dsCond, "VBELN");
			String zseq  = DatasetUtility.getString(dsCond, "ZSEQ");
			String spras	= DatasetUtility.getString(dsCond, "SPRAS");
			
			if (vbeln == null) vbeln = "";
			if (zseq  == null) zseq  = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setVbeln(vbeln);
			param.setZseq(zseq);
			param.setSpras(ZLang.convSapLang(spras));
			
			List<Ses0330> list = service.searchDetail(param);  	// 서비스CALL
			dsList.deleteAll();
			
			for (int i=0;i<list.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "MANDT"    		, list.get(i).getMandt());
				dsList.setColumn(i, "VBELN"    		, list.get(i).getVbeln());
				dsList.setColumn(i, "ZSEQ"     	 	, list.get(i).getZseq()); 
				dsList.setColumn(i, "POSNR"     	, list.get(i).getPosnr());	
				dsList.setColumn(i, "POSID"      	, list.get(i).getPosid()); 
				dsList.setColumn(i, "WWMOD"   		, list.get(i).getWwmod()); 
				dsList.setColumn(i, "ARKTX"    		, list.get(i).getArktx()); 
				dsList.setColumn(i, "KWMENG"       	, list.get(i).getKwmeng()); 
				dsList.setColumn(i, "VRKME"      	, list.get(i).getVrkme()); 
				dsList.setColumn(i, "NETWR"      	, list.get(i).getNetwr()); 
				dsList.setColumn(i, "WAERK"      	, list.get(i).getWaerk()); 
				dsList.setColumn(i, "ERDAT"      	, list.get(i).getErdat()); 
				dsList.setColumn(i, "ERZZT"			, list.get(i).getErzzt());
				dsList.setColumn(i, "ERNAM"       	, list.get(i).getErnam());
				dsList.setColumn(i, "EDATU"      	, list.get(i).getEdatu());
				dsList.setColumn(i, "SHNETWR"		, list.get(i).getShnetwr());
				dsList.setColumn(i, "KZWI5"			, list.get(i).getKzwi5());
				dsList.setColumn(i, "SHWAERK"		, list.get(i).getShwaerk());
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

	@RequestMapping(value="attribute")
	public View Ses0330DtlView2(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_attribute");	// UI로 return할 out dataset  
		 
		try { 
			Ses0330ParamVO param = new Ses0330ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			String vbeln   = DatasetUtility.getString(dsCond, "VBELN");
			String zseq   = DatasetUtility.getString(dsCond, "ZSEQ");
			String posnr   = DatasetUtility.getString(dsCond, "POSNR");
			String spras	= DatasetUtility.getString(dsCond, "SPRAS");
			
			if (vbeln   == null) vbeln   = "";
			if (zseq   == null) zseq   = "";
			if (posnr   == null) posnr   = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setVbeln(vbeln);
			param.setZseq(zseq);
			param.setPosnr(posnr);
			param.setSpras(ZLang.convSapLang(spras));
			
			List<Ses0330> list = service.searchAttribute(param);  	// 서비스CALL
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

//			System.out.println("============== attribute.getRowCount() " + dsList.getRowCount());
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	@RequestMapping(value="hdrDtl")
	public View Ses0330HdrDtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_hdrdtl");	// UI로 return할 out dataset  
		
		try {
			Ses0330ParamVO param = new Ses0330ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			String edatufr   = DatasetUtility.getString(dsCond, "EDATUFR");
			String edatuto   = DatasetUtility.getString(dsCond, "EDATUTO");
			String vbeln   = DatasetUtility.getString(dsCond, "VBELN");
			String bstkd   = DatasetUtility.getString(dsCond, "BSTKD");
			String sedat   = DatasetUtility.getString(dsCond, "SEDAT");
			String spras	= DatasetUtility.getString(dsCond, "SPRAS");
			String zzpjt_id	= DatasetUtility.getString(dsCond, "ZZPJT_ID");		// PROJ.No
			String land1	= DatasetUtility.getString(dsCond, "LAND1");		// 국가코드
			String vkbur	= DatasetUtility.getString(dsCond, "VKBUR");		// 부서
			String vkgrp	= DatasetUtility.getString(dsCond, "VKGRP");		// 팀
			String zkunnr	= DatasetUtility.getString(dsCond, "ZKUNNR");		// 담당자
			
			if (vbeln == null) vbeln = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setEdatufr(edatufr);
			param.setEdatuto(edatuto);
			param.setVbeln(vbeln);
			param.setBstkd(bstkd);
			param.setSedat(sedat);
			param.setSpras(ZLang.convSapLang(spras));
			param.setZzpjt_id(zzpjt_id);
			param.setLand1(land1);
			param.setVkbur(vkbur);
			param.setVkgrp(vkgrp);
			param.setZkunnr(zkunnr);
			
			List<Ses0330> list = service.selectHdrDtlList(param);  	// 서비스CALL
			dsList.deleteAll();
			
			for (int i=0;i<list.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사
				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "MANDT"    		, list.get(i).getMandt());
				dsList.setColumn(i, "VBELN"    		, list.get(i).getVbeln());
				dsList.setColumn(i, "POSNR"     	, list.get(i).getPosnr());	
				dsList.setColumn(i, "POSID"      	, list.get(i).getPosid()); 
				dsList.setColumn(i, "WWMOD"   		, list.get(i).getWwmod()); 
				dsList.setColumn(i, "ARKTX"    		, list.get(i).getArktx()); 
				dsList.setColumn(i, "KWMENG"       	, list.get(i).getKwmeng()); 
				dsList.setColumn(i, "VRKME"      	, list.get(i).getVrkme()); 
				dsList.setColumn(i, "NETWR"      	, list.get(i).getNetwr()); 
				dsList.setColumn(i, "WAERK"      	, list.get(i).getWaerk()); 
				dsList.setColumn(i, "EDATU"      	, list.get(i).getEdatu());
				dsList.setColumn(i, "SHNETWR"		, list.get(i).getShnetwr());
				dsList.setColumn(i, "SHWAERK"		, list.get(i).getShwaerk());
				dsList.setColumn(i, "RECAD_DA"		, list.get(i).getRecad_da());
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
	
	/*------------------------------------------------------------
	 *  상해송부PO 수정(updatedetail)
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Ses0330updateDetail(MipParameterVO paramVO, Model model) { 
  
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
//		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}	
	
	/*------------------------------------------------------------
	 *  상해송부PO 메일전송(sendupdateHeader)
	 ------------------------------------------------------------*/
	@RequestMapping(value="sendmail")
	public View Ses0330sendMail(MipParameterVO paramVO, Model model) { 
  
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
	 *  상해송부PO headerUpdate(updateHeader)
	 ------------------------------------------------------------*/
	@RequestMapping(value="updateHeader")
	public View Ses0330updateHeader(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.updateHeader(paramVO, model, session);

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
	
	@RequestMapping(value="checkVbak")
	public View Ses0330checkVbak(MipParameterVO paramVO, Model model)
	{
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list");	// UI로 return할 out dataset
		Dataset dsHogi	= paramVO.getDataset("ds_hogi");	// UI로 return할 out dataset
		Dataset ds_sap	= paramVO.getDataset("ds_sapinsert");
		Dataset ds_hdrdtl	= paramVO.getDataset("ds_hdrdtl");
		
		// RFC INPUT PARAM DECLARE
		ZSDS0016[] 	slist 	= new ZSDS0016[]{};  // WFC output list
		ZSDS0017[] 	slist2 	= new ZSDS0017[]{};  // output list
		ZQMSEMSG[] 	slistMsg = new ZQMSEMSG[]{};	
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI로 return할 out dataset
		Dataset 	ds2 		= null;				// UI로 return할 out dataset2
		Dataset 	ds_Error = null;				// UI로 return할 오류메세지 dataset 
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		
		try
		{
			Ses0330ParamVO param = new Ses0330ParamVO();
			
			service.createDao(session); // DAO생성
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			String vbeln   = DatasetUtility.getString(dsCond, "VBELN");
			
			if (vbeln   == null) vbeln   = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setVbeln(vbeln);
			
			List<Ses0330> lHogi = null;
			
			try
			{
				// 저장 호출
				lHogi = service.insertVblen(paramVO, model, session);
				
				for ( int i = 0 ; i < lHogi.size() ; i++ ) // 호출결과(list)를 데이타셋(dsList)에 복사
				{
					dsHogi.appendRow(); 	// 행추가
					dsHogi.setColumn(i, "MANDT"    		, lHogi.get(i).getMandt());
					dsHogi.setColumn(i, "VBELN"    		, lHogi.get(i).getVbeln());
					dsHogi.setColumn(i, "ZSEQ"     	 	, lHogi.get(i).getZseq()); 
					dsHogi.setColumn(i, "POSNR"     	, lHogi.get(i).getPosnr());	
					dsHogi.setColumn(i, "POSID"      	, lHogi.get(i).getPosid()); 
					
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
					
//					System.out.print("\n@@@ ds.getRowCount()!!!   " + ds.getRowCount());
					
					for(int j=0; j<ds.getRowCount(); j++)
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
					service.insertSap(ds_sap, model, session);
					ds_sap.deleteAll();
				}

			}
			catch (Exception e )
			{
				e.printStackTrace();
				// 호출결과  
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");					
			}
						
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			
			resultVO.addDataset(dsList);
			resultVO.addDataset(dsHogi);
			
//			System.out.println("============== 마무리=== " + dsList.getRowCount());
			model.addAttribute("resultVO", resultVO);     
		}
		catch (Exception e)
		{ 
			e.printStackTrace();
		}	    
		
		return view;
	}
		
	/*------------------------------------------------------------
	 *  txt저장
	 ------------------------------------------------------------*/
	
	@RequestMapping(value="saveTxt")
	public View Ses0320SaveTxt(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		Dataset dsCond 		= paramVO.getDataset("ds_cond");
		Dataset ds_header	= paramVO.getDataset("ds_header");	//
		Dataset ds_detail	= paramVO.getDataset("ds_detail");	//
		Dataset dsDown		= paramVO.getDataset("ds_down");	// UI로 return할 out dataset
		//Dataset ds_attribute	= paramVO.getDataset("ds_attribute");	//
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);
		
		StringBuffer sb = null;
		
		try {
			sb = new StringBuffer();
			
			for ( int i = 0 ; i < ds_header.getRowCount() ; i++ ) {
				sb.append("H");
				sb.append(String.format("%-10s", ds_header.getColumn(i, "VBELN")));		// 수주번호
				sb.append(String.format("%-35s", ds_header.getColumn(i, "BSTKD")));		// 현장명
				sb.append(String.format("%-3s" , ds_header.getColumn(i, "REGION")));	// 납품처 국가
				sb.append(String.format("%-3s" , ds_header.getColumn(i, "INCO1")));		// 인도조건(파트1)
				sb.append(String.format("%-28s", ds_header.getColumn(i, "INCO2")));		// 인도조건(파트2)
				sb.append(String.format("%-30s", ds_header.getColumn(i, "ZTERMT")));	// 지급조건
				sb.append(String.format("%-30s", ds_header.getColumn(i, "SPECDWG")));	// SPEC&DWG
				sb.append(String.format("%-8s" , ds_header.getColumn(i, "EDATU")));		// 납기일자 
				sb.append(String.format("%-30s", ds_header.getColumn(i, "ZFWDING")));	// FORWARDING
				sb.append("\r\n");
			}
			String strPosId = "";
			for ( int i = 0 ; i < ds_detail.getRowCount() ; i++) {
				strPosId = ds_detail.getColumn(i, "POSID").toString();
				if (strPosId.length() > 3) {
					strPosId = strPosId.substring(strPosId.length()-3, strPosId.length());
				}
				sb.append("L");
				sb.append(String.format("%-10s", ds_detail.getColumn(i, "VBELN")));	// 수주번호
				sb.append(String.format("%-3s" , strPosId));						// 호기번호
				sb.append(String.format("%-8s" , ds_detail.getColumn(i, "EDATU")));	// 납기일자
				sb.append(String.format("%-30s", ds_detail.getColumn(i, "WWMOD")));	// 기종
				sb.append(String.format("%-40s", ds_detail.getColumn(i, "ARKTX")));	// 영업사양
				sb.append(String.format("%-15s", ds_detail.getColumn(i, "KWMENG")));// 판매대수
				sb.append(String.format("%-3s" , ds_detail.getColumn(i, "VRKME")));	// 판매단위
				sb.append(String.format("%-15s", ds_detail.getColumn(i, "NETWR")));	// 판매금액 
				sb.append(String.format("%-5s" , ds_detail.getColumn(i, "WAERK")));	// 통화단위
				sb.append(String.format("%-6s" , ds_detail.getColumn(i, "POSNR")));	// 품목 번호
				sb.append("\r\n");
			}
			
			// ds_attribute 는 조회
			Ses0330ParamVO param = new Ses0330ParamVO();
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			String vbeln = DatasetUtility.getString(dsCond, "VBELN");
			String zseq  = DatasetUtility.getString(dsCond, "ZSEQ");
			String posnr = DatasetUtility.getString(dsCond, "POSNR");
			String spras = DatasetUtility.getString(dsCond, "SPRAS");
			
			if (vbeln == null) vbeln = "";
			if (zseq  == null) zseq  = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setVbeln(vbeln);
			param.setZseq(zseq);
			param.setPosnr(posnr);
			param.setSpras(ZLang.convSapLang(spras));
			
			List<Ses0330> list = service.searchAttribute(param);  	// 서비스CALL
			
			for ( int i = 0 ; i < list.size() ; i++ ) {
				String strPosid = list.get(i).getPosid() == null?"":list.get(i).getPosid();
				if ( strPosid.length() > 3 ) strPosid = strPosid.substring(strPosid.length()-3, strPosid.length());
				sb.append("S");
				sb.append(String.format("%-10s", list.get(i).getVbeln()	== null?"":list.get(i).getVbeln()));	// 수주번호
				sb.append(String.format("%-3s" , strPosid));													// 호기번호
				sb.append(String.format("%-30s", list.get(i).getAtnam()	== null?"":list.get(i).getAtnam())); 	// 특성코드
				sb.append(String.format("%-30s", list.get(i).getAtwrt()	== null?"":list.get(i).getAtwrt()));	// 특성값
				sb.append(String.format("%-6s" , list.get(i).getPosnr()	== null?"":list.get(i).getPosnr())); 	// 품목번호
				sb.append(String.format("%-30s", list.get(i).getAtbez()	== null?"":list.get(i).getAtbez())); 	// 특성내역
				sb.append(String.format("%-30s", list.get(i).getAtwtb()	== null?"":list.get(i).getAtwtb())); 	// 특성값내역
				sb.append("\r\n");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
				
		Dataset ds_down = new Dataset("ds_down");
		ds_down.addColumn("txt", ColumnInfo.COLTYPE_BLOB, (short)255);
		
		ds_down.appendRow();
		ds_down.setColumn(0, "txt", sb.toString());
		
		MipResultVO resultVO = new MipResultVO(); 
//		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_down);  		// 다운로드 컨텐츠
		resultVO.addDataset(ds_error);  	// 오류INFO
		model.addAttribute("resultVO", resultVO);   

		// TXT 저장하고 나면 접수일자 기록
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String strCurrDate = dateFormat.format(date); // 현재 날짜
		
		Ses0330 param = new Ses0330();
		param.setAcdat(strCurrDate);
		param.setMandt(DatasetUtility.getString(ds_header, 0, "MANDT"));
		param.setVbeln(DatasetUtility.getString(ds_header, 0, "VBELN"));
		param.setZseq(DatasetUtility.getString(ds_header, 0, "ZSEQ"));
		
		service.updateHeader(param);  	// 서비스CALL
		
		return view;
	}
	 
}
