package hdel.sd.ses.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT0115;
import hdel.lib.service.ZSDT0115S;
import hdel.sd.ses.domain.Ses0403;
import hdel.sd.ses.domain.Ses0403ParamVO;
import hdel.sd.ses.service.Ses0403S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0403")
public class Ses0403C {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0403S service;
	@Autowired
	private ZSDT0115S zsdt0115s;

	@RequestMapping(value="findListDetail")
	public View Ses0403FindListDetail(MipParameterVO paramVO, Model model) {
		logger.debug("@@@ Ses0403FindListDetail START");

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try
		{ 
			Ses0403ParamVO param = new Ses0403ParamVO();
			Ses0403 paramSet = new Ses0403();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setFrvkbur(DatasetUtility.getString(dsCond,"FR_VKBUR"));
			param.setTovkbur(DatasetUtility.getString(dsCond,"TO_VKBUR"));
			param.setFrvkgrp(DatasetUtility.getString(dsCond,"FR_VKGRP"));
			param.setTovkgrp(DatasetUtility.getString(dsCond,"TO_VKGRP"));
			param.setLifnr(DatasetUtility.getString(dsCond,"LIFNR"));
			param.setDelfg(DatasetUtility.getString(dsCond,"DELFG"));

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			List<Ses0403> ses0403List = service.getListDetail(param);
			
			for (int i = 0; i < ses0403List.size(); i++) {

				dsList.appendRow();
				dsList.setColumn(i, "MANDT",		ses0403List.get(i).getMandt());
				dsList.setColumn(i, "VKBUR",		ses0403List.get(i).getVkbur());
				dsList.setColumn(i, "BURNM",		ses0403List.get(i).getBurNm()); 
				dsList.setColumn(i, "VKGRP",		ses0403List.get(i).getVkgrp()); 
				dsList.setColumn(i, "GRPNM",		ses0403List.get(i).getGrpNm()); 
				dsList.setColumn(i, "LIFNR", 		ses0403List.get(i).getLifnr()); 
				dsList.setColumn(i, "LIFNRNM",		ses0403List.get(i).getLifnrNm()); 
				dsList.setColumn(i, "DELFG",		ses0403List.get(i).getDelfg());
				dsList.setColumn(i, "SPRTFG",		ses0403List.get(i).getSprtfg());
				dsList.setColumn(i, "SSPRTDAT",		ses0403List.get(i).getSsprtdat());
				dsList.setColumn(i, "ESPRTDAT",		ses0403List.get(i).getEsprtdat());
				//dsList.setColumn(i, "SPRTCNT",		ses0403List.get(i).getSprtcnt());
				dsList.setColumn(i, "SPRTLIFNR",	ses0403List.get(i).getSprtLifnr());
				dsList.setColumn(i, "SPRTLIFNRNM",	ses0403List.get(i).getSprtLifnrNm());
				dsList.setColumn(i, "CHN_SNO",	ses0403List.get(i).getChn_sno());
				dsList.setColumn(i, "CHN_SNM",	ses0403List.get(i).getChn_snm());
				String lifnr 		= dsList==null?"":dsList.getColumnAsString(i, "LIFNR")==null?"":dsList.getColumnAsString(i, "LIFNR");
				String sprtlifnr	= dsList==null?"":dsList.getColumnAsString(i, "SPRTLIFNR")==null?"":dsList.getColumnAsString(i, "SPRTLIFNR");
				String ssprtdat		= dsList==null?"":dsList.getColumnAsString(i, "SSPRTDAT")==null?"":dsList.getColumnAsString(i, "SSPRTDAT");
				String esprtdat		= dsList==null?"":dsList.getColumnAsString(i, "ESPRTDAT")==null?"":dsList.getColumnAsString(i, "ESPRTDAT");
				
				if("X".equals(dsList.getColumnAsString(i, "SPRTFG")) && !"".equals(lifnr) && !"".equals(sprtlifnr)) {
					
					Map<String, Object> sprtCsResult = new HashMap<String, Object>();
					String transSprtCs = "";
					
					paramSet.setMandt(dsList.getColumnAsString(i, "MANDT"));
					paramSet.setVkbur(dsList.getColumnAsString(i, "VKBUR"));
					paramSet.setVkgrp(dsList.getColumnAsString(i, "VKGRP"));
					paramSet.setLifnr(lifnr);
					paramSet.setSprtLifnr(sprtlifnr);
					paramSet.setSsprtdat(ssprtdat);
					paramSet.setEsprtdat(esprtdat);
					
					sprtCsResult = service.selectSprtCs(paramSet);
					
					if(sprtCsResult != null) {
						
						if(lifnr.equals(sprtlifnr)) {
							transSprtCs = sprtCsResult.get("LIFNRCNT").toString();
						} else {
							transSprtCs = sprtCsResult.get("LIFNRCNT") + " / " + sprtCsResult.get("SPRTLIFNRCNT");
						}
					}
					
					dsList.setColumn(i, "SPRTCS", transSprtCs);
				}
			}

			resultVO.addDataset(dsList);    
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	/**
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="save")
	public View Ses0403SaveView(MipParameterVO paramVO, Model model) {
		Dataset ds_list_chg    = paramVO.getDataset("ds_list_chg");
		
		String mandt = paramVO.getVariable("G_MANDT");
		String uuser = paramVO.getVariable("G_USER_ID");
		String gvbcd = paramVO.getVariable("G_SAP_USER_VGCD");
		
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			for ( int i = 0 ; i < ds_list_chg.getRowCount() ; i++ ) {
				
				Ses0403 ses0403Param = new Ses0403();

				ses0403Param.setMandt(mandt);
				ses0403Param.setVkgrp(DatasetUtility.getString(ds_list_chg, i, "VKGRP", ""));
				ses0403Param.setVkbur(DatasetUtility.getString(ds_list_chg, i, "VKBUR", ""));
				ses0403Param.setLifnr(DatasetUtility.getString(ds_list_chg, i, "LIFNR", ""));
				ses0403Param.setDelfg(DatasetUtility.getString(ds_list_chg, i, "DELFG", ""));
				ses0403Param.setSprtfg(DatasetUtility.getString(ds_list_chg, i, "SPRTFG", ""));
				ses0403Param.setSsprtdat(DatasetUtility.getString(ds_list_chg, i, "SSPRTDAT", ""));
				ses0403Param.setEsprtdat(DatasetUtility.getString(ds_list_chg, i, "ESPRTDAT", ""));
				ses0403Param.getTstmp().setTimeStamp(paramVO.getVariable("G_USER_ID"));
				ses0403Param.setSprtLifnr(DatasetUtility.getString(ds_list_chg, i, "SPRTLIFNR", ""));
				service.save(ses0403Param);
				
				if(!"".equals(ses0403Param.getLifnr())) {
					if(!"".equals(ses0403Param.getSprtfg()) && !"".equals(ses0403Param.getSprtLifnr())) {
						service.setSeparate(ses0403Param, "U");
					} else if("".equals(ses0403Param.getSprtfg())) {
						service.setSeparate(ses0403Param, "D");
					}
				}
			}
			
			resultVO.addDataset(ds_list_chg);
			
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value="findExternalVendor")
	public View FindExternalVendor(MipParameterVO paramVO, Model model) {
		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsExtvdAssgn = paramVO.getDataset("ds_extvd_assigned");
		Dataset dsExtvdMaster = paramVO.getDataset("ds_extvd_master");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try
		{ 
			Ses0403 param = new Ses0403();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setVkbur(DatasetUtility.getString(dsHeader,"VKBUR"));
			param.setVkgrp(DatasetUtility.getString(dsHeader,"VKGRP"));
			param.setQtnum(DatasetUtility.getString(dsHeader,"QTSO_NO"));
			param.setDelfg("");

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			zsdt0115s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			List<ZSDT0115> extVdMasterList = zsdt0115s.getAll(paramVO.getVariable("G_MANDT"), new String(""));
			List<Ses0403> ses0403List = service.getCadVendorAbbr(param);
			
			Map<String, Object> selectZSDT0198 = service.selectZsdt0198(param);
			String z0198Cvabr	= selectZSDT0198==null?"":selectZSDT0198.get("CVABR")==null?"":selectZSDT0198.get("CVABR").toString();
			String z0198Cvabrt	= selectZSDT0198==null?"":selectZSDT0198.get("CVABRT")==null?"":selectZSDT0198.get("CVABRT").toString();
			
			boolean z0198Flag = 0 < z0198Cvabr.length() + z0198Cvabrt.length() ? true : false;
			
			Map<String, Object>lifnrMap =  new HashMap<String, Object>();
			lifnrMap = service.selectLifnr(param);
			
			String sprtfg		= lifnrMap==null?"":lifnrMap.get("SPRTFG")==null?"":lifnrMap.get("SPRTFG").toString();
			String cvabr		= lifnrMap==null?"":lifnrMap.get("CVABR")==null?"":lifnrMap.get("CVABR").toString();
			String sprtCvabr	= lifnrMap==null?"":lifnrMap.get("SPRTCVABR")==null?"":lifnrMap.get("SPRTCVABR").toString();
			String cvabrt		= lifnrMap==null?"":lifnrMap.get("CVABRT")==null?"":lifnrMap.get("CVABRT").toString();
			String sprtCvabrt	= lifnrMap==null?"":lifnrMap.get("SPRTCVABRT")==null?"":lifnrMap.get("SPRTCVABRT").toString();
			
			boolean sprtFlag = 0 < sprtCvabr.length() + sprtCvabrt.length() ? true : false;
			
			if("X".equals(sprtfg) && !z0198Flag && sprtFlag) {
				
				String lifnr		= lifnrMap==null?"":lifnrMap.get("LIFNR")==null?"":lifnrMap.get("LIFNR").toString();
				String sprtLifnr	= lifnrMap==null?"":lifnrMap.get("SPRTLIFNR")==null?"":lifnrMap.get("SPRTLIFNR").toString();
				String ssprtdat		= lifnrMap==null?"":lifnrMap.get("SSPRTDAT")==null?"":lifnrMap.get("SSPRTDAT").toString();
				String esprtdat		= lifnrMap==null?"":lifnrMap.get("ESPRTDAT")==null?"":lifnrMap.get("ESPRTDAT").toString();
				
				param.setLifnr(lifnr);
				param.setSprtLifnr(sprtLifnr);
				param.setSsprtdat(ssprtdat);
				param.setEsprtdat(esprtdat);
				
				Map<String, Object> sprtCsResult = service.selectSprtCs(param);
				
				int lifnr_cnt = Integer.parseInt(sprtCsResult.get("LIFNRCNT").toString());
				int sprt_lifnr_cnt = Integer.parseInt(sprtCsResult.get("SPRTLIFNRCNT").toString());
				
				if(lifnr_cnt < sprt_lifnr_cnt) {
					ses0403List.get(0).setCvabr(cvabr);
					ses0403List.get(0).setCvabrt(cvabrt);
				} else if(lifnr_cnt >= sprt_lifnr_cnt) {
					ses0403List.get(0).setCvabr(sprtCvabr);
					ses0403List.get(0).setCvabrt(sprtCvabrt);
				}
			}
			
			DatasetUtil.moveListToDs(extVdMasterList, dsExtvdMaster);
			DatasetUtil.moveListToDs(ses0403List, dsExtvdAssgn);

			if(dsExtvdAssgn != null)
				resultVO.addDataset(dsExtvdAssgn);
			if(dsExtvdMaster != null)
				resultVO.addDataset(dsExtvdMaster);

		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	@RequestMapping(value="getExternalVendorMaster")
	public View getExternalVendorMaster(MipParameterVO paramVO, Model model) {
		Dataset dsExtvdMaster = paramVO.getDataset("ds_extvd_master");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try {
			Ses0403 param = new Ses0403();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setDelfg("");

			zsdt0115s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			List<ZSDT0115> extVdMasterList = zsdt0115s.getAll(paramVO.getVariable("G_MANDT"), new String(""));
			
			DatasetUtil.moveListToDs(extVdMasterList, dsExtvdMaster);

			if(dsExtvdMaster != null)
				resultVO.addDataset(dsExtvdMaster);

		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
}