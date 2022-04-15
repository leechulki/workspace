package hdel.sd.com.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.com.domain.ZRType;
import hdel.sd.com.domain.ZRTypes;
import hdel.sd.com.service.ComboS;
import hdel.sd.sch.service.Sch0060S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("combo")
public class ComboC {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private ComboS service;

	@RequestMapping(value="comboXml")
	public View comboXml(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDatasetList().getDataset(0);

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setLang(paramVO.getVariable("G_LANG"));
		param.setsCode(dsCond.getColumnAsString(0, 0));

		List<ComboVO> listCombo = service.selectXml(param);

		dsCond.deleteAll();

		for (int i = 0; i < listCombo.size(); i++) {
			dsCond.appendRow();
			dsCond.setColumn(i, 0, listCombo.get(i).getCode());
			dsCond.setColumn(i, 1, listCombo.get(i).getCodeName());
			dsCond.setColumn(i, 2, listCombo.get(i).getFilter1());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsCond);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
	
	@RequestMapping(value="comboClass")
	public View comboClass(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond     = paramVO.getDatasetList().getDataset("ds_cond");
		Dataset dsZprdgbn = paramVO.getDatasetList().getDataset("ds_zprdgbn");
		
		String mclass = "";
		if (dsCond != null) mclass = dsCond.getColumnAsString(0, "ZPRDGBN");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setMclass(mclass);

		List<ComboVO> listCombo = service.selectClass(param);

		dsZprdgbn.deleteAll();
		
		for (int i = 0; i < listCombo.size(); i++) {
			dsZprdgbn.appendRow();
			dsZprdgbn.setColumn(i, 0, listCombo.get(i).getCode());
			dsZprdgbn.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsZprdgbn);	
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}

	@RequestMapping(value="vkburByDc")
	public View vkburByDc(MipParameterVO paramVO, Model model) {
		Dataset dsVkbur = paramVO.getDataset("ds_vkbur");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setVkgrp(paramVO.getVariable("G_SAP_USER_VGCD"));
		param.setZkunnr("H"+paramVO.getVariable("G_USER_ID"));

		List<ComboVO> listCombo = service.selectVkburByDc(param);

		for (int i = 0; i < listCombo.size(); i++) {
			dsVkbur.appendRow();
			dsVkbur.setColumn(i, 0, listCombo.get(i).getCode());
			dsVkbur.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsVkbur);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="vkgrpByDc")
	public View vkgrpByDc(MipParameterVO paramVO, Model model) {
		Dataset dsVkgrp = paramVO.getDataset("ds_vkgrp");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setVkbur(dsVkgrp.getColumnAsString(0, 0));
		param.setVkgrp(paramVO.getVariable("G_SAP_USER_VGCD"));
		param.setZkunnr("H"+paramVO.getVariable("G_USER_ID"));

		List<ComboVO> listCombo = service.selectVkgrpByDc(param);

		for (int i = 0; i < listCombo.size(); i++) {
			dsVkgrp.appendRow();
			dsVkgrp.setColumn(i, 0, listCombo.get(i).getCode());
			dsVkgrp.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsVkgrp);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="comboFrVb")
	public View comboFrVb(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsFrVbCd = paramVO.getDataset("ds_frvb_cd");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));

		List<ComboVO> listCombo = service.selectVkbur(param);

		for (int i = 0; i < listCombo.size(); i++) {
			dsFrVbCd.appendRow();
			dsFrVbCd.setColumn(i, 0, listCombo.get(i).getCode());
			dsFrVbCd.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsFrVbCd);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="comboToVb")
	public View comboToVb(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsToVbCd = paramVO.getDataset("ds_tovb_cd");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));

		List<ComboVO> listCombo = service.selectVkbur(param);

		for (int i = 0; i < listCombo.size(); i++) {
			dsToVbCd.appendRow();
			dsToVbCd.setColumn(i, 0, listCombo.get(i).getCode());
			dsToVbCd.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsToVbCd);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="comboFrVgAll")
	public View comboFrVgAll(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsFrVgCd = paramVO.getDataset("ds_frvg_cd");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setVkbur(dsFrVgCd.getColumnAsString(0, 0));

		List<ComboVO> listCombo = service.selectVkgrpAll(param);

		dsFrVgCd.deleteAll();

		for (int i = 0; i < listCombo.size(); i++) {
			dsFrVgCd.appendRow();
			dsFrVgCd.setColumn(i, 0, listCombo.get(i).getCode());
			dsFrVgCd.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsFrVgCd);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="comboToVgAll")
	public View comboToVgAll(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsToVgCd = paramVO.getDataset("ds_tovg_cd");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setVkbur(dsToVgCd.getColumnAsString(0, 0));

		List<ComboVO> listCombo = service.selectVkgrpAll(param);

		dsToVgCd.deleteAll();

		for (int i = 0; i < listCombo.size(); i++) {
			dsToVgCd.appendRow();
			dsToVgCd.setColumn(i, 0, listCombo.get(i).getCode());
			dsToVgCd.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsToVgCd);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="comboFrVg")
	public View comboFrVg(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsFrVgCd = paramVO.getDataset("ds_frvg_cd");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setVkbur(dsFrVgCd.getColumnAsString(0, 0));

		List<ComboVO> listCombo = service.selectVkgrp(param);

		dsFrVgCd.deleteAll();

		for (int i = 0; i < listCombo.size(); i++) {
			dsFrVgCd.appendRow();
			dsFrVgCd.setColumn(i, 0, listCombo.get(i).getCode());
			dsFrVgCd.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsFrVgCd);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="comboToVg")
	public View comboToVg(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsToVgCd = paramVO.getDataset("ds_tovg_cd");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setVkbur(dsToVgCd.getColumnAsString(0, 0));

		List<ComboVO> listCombo = service.selectVkgrp(param);

		dsToVgCd.deleteAll();

		for (int i = 0; i < listCombo.size(); i++) {
			dsToVgCd.appendRow();
			dsToVgCd.setColumn(i, 0, listCombo.get(i).getCode());
			dsToVgCd.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsToVgCd);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="comboAuart")
	public View comboAuart(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsAuartCd = paramVO.getDataset("ds_auart_cd");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));

		List<ComboVO> listCombo = service.selectAuart(param);

		for (int i = 0; i < listCombo.size(); i++) {
			dsAuartCd.appendRow();
			dsAuartCd.setColumn(i, 0, listCombo.get(i).getCode());
			dsAuartCd.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsAuartCd);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	
	// 2012.05.30 : GRY : 추가 : 공통코드정보
	@RequestMapping(value="comboDD07T") 
	public View comboReason(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_cond_dd07t = null;
		Dataset ds_list_dd07t = null;
		if (paramVO.getDatasetList().getDataset(0).getId().startsWith("ds_cond"))
		{
			ds_cond_dd07t = paramVO.getDatasetList().getDataset(0);	// INPUT
			ds_list_dd07t = paramVO.getDatasetList().getDataset(1);	// OUTPUT
		}
		else
		{
			ds_cond_dd07t = paramVO.getDatasetList().getDataset(1);	// OUTPUT
			ds_list_dd07t = paramVO.getDatasetList().getDataset(0);	// INPUT
		}  
		
		ds_list_dd07t.deleteAll();
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		//param.setMandt(paramVO.getVariable("G_MANDT"));							// 채널
		param.setDomname(DatasetUtility.getString(ds_cond_dd07t,"DOMNAME"));   		// 도메인명
		param.setDomvalue_l(DatasetUtility.getString(ds_cond_dd07t,"DOMVALUE_L")); 	// 코드 

		List<ComboVO> listCombo = null;
		String concat_yn = DatasetUtility.getString(ds_cond_dd07t,"CONCAT_YN");     // 코드명에 코드도 같이 보여줄지 여부
		
		// 대표기종은 별도 처리
		if ("ZGTYPE".equals(param.getDomname())) {
			param.setMandt(paramVO.getVariable("G_MANDT"));							// 채널
			param.setVkbur(paramVO.getVariable("G_SAP_USER_VBCD"));
			param.setVkgrp(paramVO.getVariable("G_SAP_USER_VGCD"));
			param.setZkunnr(paramVO.getVariable("G_USER_ID"));
			
			if (concat_yn.equals("Y")){  // 코드명에 코드 + '  ' + 코드명으로 가져올지 여부
				listCombo= service.selectGtypeConcat(param);
			} else {
				listCombo= service.selectGtype(param);
			}
		} else {
			if (concat_yn.equals("Y")){  // 코드명에 코드 + '  ' + 코드명으로 가져올지 여부
				listCombo= service.selectListConcatDD07T(param);
			}
			else{
				listCombo = service.selectListDD07T(param);
			}
		}

		// 호출결과(list)를 데이타셋(dsList)에 복사
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{   
			// 행추가
			ds_list_dd07t.appendRow();  
    		// 컬럼SET 
			ds_list_dd07t.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list_dd07t.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		//ds_list_dd07t.setId	("ds_list_dd07t");  
		resultVO.addDataset	(ds_list_dd07t);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	

	@RequestMapping(value="gridVb")
	/* 2012.06.12 : GRY : 추가 : 부서목록 조회 */ 
	public View gridVbView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_obj_list = paramVO.getDataset("ds_obj_list"); 	// 데이타셋 이름 목록
		Dataset ds_list_vb 	= paramVO.getDataset(DatasetUtility.getString(ds_obj_list,"DS_VB")); // 부서목록 출력 데이타셋

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));

		List<ComboVO> listCombo = service.selectVkbur(param);

		for (int i = 0; i < listCombo.size(); i++) {
			ds_list_vb.appendRow();
			ds_list_vb.setColumn(i, "CODE"		, listCombo.get(i).getCode());		// 코드
			ds_list_vb.setColumn(i, "CODE_NAME"	, listCombo.get(i).getCodeName());	// 코드명
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list_vb.setId	(DatasetUtility.getString(ds_obj_list,"DS_VB"));  
		resultVO.addDataset	(ds_list_vb);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	} 

	@RequestMapping(value="gridVg") 
	/* 2012.06.12 : GRY : 추가 : 부서코드에 해당하는 팀목록 조회  */
	public View gridVgView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_obj_list = paramVO.getDataset("ds_obj_list");	// 데이타셋 이름 목록
		Dataset ds_cond_vg 	= paramVO.getDataset(DatasetUtility.getString(ds_obj_list,"DS_COND_VG")); 	// 팀조회 조건데이타셋
		Dataset ds_list_vg 	= paramVO.getDataset(DatasetUtility.getString(ds_obj_list,"DS_VG"));		// 팀목록 출력데이타셋

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		System.out.print("\n@@@ paramVO.G_MANDT 		===>"+paramVO.getVariable("G_MANDT") 			+"\n");
		//System.out.print("\n@@@ paramVO.G_LANG		===>"+paramVO.getVariable("G_LANG") 			+"\n"); 
		System.out.print("\n@@@ ds_cond_vg.VB	===>"+DatasetUtility.getString(ds_cond_vg,"VB")+"\n"); 
		//--------------------------------------------------------------------------------------------
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setVkbur(DatasetUtility.getString(ds_cond_vg,"VB"));

		List<ComboVO> listCombo = service.selectVkgrp(param); 

		for (int i = 0; i < listCombo.size(); i++) {
			ds_list_vg.appendRow();
			ds_list_vg.setColumn(i, "CODE"		, listCombo.get(i).getCode());
			ds_list_vg.setColumn(i, "CODE_NAME"	, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list_vg.setId	(DatasetUtility.getString(ds_obj_list,"DS_VG"));  
		resultVO.addDataset	(ds_list_vg);
		model.addAttribute("resultVO", resultVO);   
		
		return view;
	}

	// 2012.06.26 : 이지훈 : 추가
	@RequestMapping(value="comboLand")
	public View comboLand(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsLandCd = paramVO.getDataset("ds_land_cd");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));

		List<ComboVO> listCombo = service.selectLand(param);

		for (int i = 0; i < listCombo.size(); i++) {
			dsLandCd.appendRow();
			dsLandCd.setColumn(i, 0, listCombo.get(i).getCode());
			dsLandCd.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsLandCd);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	// 2012.06.28 : 이지훈 : 추가
	@RequestMapping(value="comboGtype")
	public View comboGtype(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsGtype = paramVO.getDataset("ds_gtype");
		Dataset dsGtypeCd = paramVO.getDataset("ds_gtype_cd");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setVkbur(paramVO.getVariable("G_SAP_USER_VBCD"));
		param.setVkgrp(paramVO.getVariable("G_SAP_USER_VGCD"));

		List<ComboVO> listCombo = service.selectGtype(param);

		for (int i = 0; i < listCombo.size(); i++) {
			dsGtypeCd.appendRow();
			dsGtypeCd.setColumn(i, 0, listCombo.get(i).getCode());
			dsGtypeCd.setColumn(i, 1, listCombo.get(i).getCodeName());
		}

		dsGtype.deleteAll();

		ZGTypes zgtype = new ZGTypes();
		CallSAPHdel.moveListToDs(dsGtype, ZGType.class, zgtype.find());
		dsGtype.setId("ds_gtype");
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsGtype);
		resultVO.addDataset(dsGtypeCd);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	// 2012.06.28 : 이지훈 : 추가
	@RequestMapping(value="comboRtype")
	public View comboRtype(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsRtype = paramVO.getDataset("ds_rtype");
		Dataset dsRtypeCd = paramVO.getDataset("ds_rtype_cd");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setVkbur(paramVO.getVariable("G_SAP_USER_VBCD"));
		param.setVkgrp(paramVO.getVariable("G_SAP_USER_VGCD"));

		List<ComboVO> listCombo = service.selectRtype(param);

		for (int i = 0; i < listCombo.size(); i++) {
			dsRtypeCd.appendRow();
			dsRtypeCd.setColumn(i, 0, listCombo.get(i).getCode());
			dsRtypeCd.setColumn(i, 1, listCombo.get(i).getCodeName());
		}

		dsRtype.deleteAll();

		ZRTypes zrtype = new ZRTypes();
		CallSAPHdel.moveListToDs(dsRtype, ZRType.class, zrtype.find());
		dsRtype.setId("ds_rtype");
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsRtype);
		resultVO.addDataset(dsRtypeCd);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	// 2012.07.23 : 이지훈 : 추가
	@RequestMapping(value="comboJ1AACTT")
	public View comboJ1AACTT(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDatasetList().getDataset(0);

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));

		List<ComboVO> listCombo = service.selectJAACTT(param);

		for (int i = 0; i < listCombo.size(); i++) {
			dsCond.appendRow();
			dsCond.setColumn(i, 0, listCombo.get(i).getCode());
			dsCond.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsCond);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	// 2012.07.23 : 이지훈 : 추가
	@RequestMapping(value="comboDD07V")
	public View comboDD07V(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDatasetList().getDataset(0);

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		ComboParamVO param = new ComboParamVO();
		param.setsCode(dsCond.getColumnAsString(0, 0));

		List<ComboVO> listCombo = service.selectDD07V(param);

		dsCond.deleteAll();

		for (int i = 0; i < listCombo.size(); i++) {
			dsCond.appendRow();
			dsCond.setColumn(i, 0, listCombo.get(i).getCode());
			dsCond.setColumn(i, 1, listCombo.get(i).getCodeName());
		}
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsCond);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
	
	// 2012.07.31 : GRY : 추가 : 지급조건 조회
	@RequestMapping(value="comboZterm") 
	public View comboZterm(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_list = paramVO.getDatasetList().getDataset(0);	// INPUT/OUTPUT   
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectListZterm(param); 	// 지급조건 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	// 2012.07.31 : GRY : 추가 : 인도조건 조회
	@RequestMapping(value="comboInco") 
	public View comboInco(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_list = paramVO.getDatasetList().getDataset(0);	// INPUT/OUTPUT   
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectListInco(param); 	// 인도조건 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	} 
	
	// 2012.07.31 : GRY : 추가 : 종별구분 조회
	@RequestMapping(value="comboMlbez") 
	public View comboMlbez(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_list = paramVO.getDatasetList().getDataset(0);	// INPUT/OUTPUT   
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectListMlbez(param); 	// 종별구분 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	

	// 2012.08.2 : GRY : 추가 : 오더유형코드, 오더유형명 조회
	@RequestMapping(value="comboAuartNm") 
	public View comboAuartNm(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_list =paramVO.getDataset("ds_auart_cd");
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectAuartNm(param); 	// 오더유형코드, 오더유형명 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	// 2012.08.2 : GRY : 추가 : 보수협력사 조회(지역)
	@RequestMapping(value="comboBosuArea") 
	public View comboBosuArea(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_list =paramVO.getDataset("ds_mid");
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		param.setLang(paramVO.getVariable("G_LANG"));
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectBosuArea(param); 	// 오더유형코드, 오더유형명 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	@RequestMapping(value="comboBosuAreaDetail") 
	public View comboBosuAreaDetail(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_list =paramVO.getDataset("ds_small");
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		param.setLang(paramVO.getVariable("G_LANG"));
		param.setsCode(ds_list.getColumnAsString(0, 0));
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectBosuAreaDetail(param); 	// 오더유형코드, 오더유형명 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	// 2012.08.2 : GRY : 추가 : 보수협력사 조회(PM)
	@RequestMapping(value="comboBosuPm") 
	public View comboBosuPm(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_list =paramVO.getDataset("ds_mid");
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		param.setLang(paramVO.getVariable("G_LANG"));
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectBosuPm(param); 	// 오더유형코드, 오더유형명 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	@RequestMapping(value="comboBosuPmDetail") 
	public View comboBosuPmDetail(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_list =paramVO.getDataset("ds_small");
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		param.setLang(paramVO.getVariable("G_LANG"));
		param.setsCode(ds_list.getColumnAsString(0, 0));
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectBosuPmDetail(param); 	// 오더유형코드, 오더유형명 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	// 2012.08.2 : GRY : 추가 : 보수협력사 조회(팀)
	@RequestMapping(value="comboBosuTeam") 
	public View comboBosuTeam(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_list =paramVO.getDataset("ds_mid");
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		param.setLang(paramVO.getVariable("G_LANG"));
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectBosuTeam(param); 	// 오더유형코드, 오더유형명 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	@RequestMapping(value="comboBosuTeamDetail") 
	public View comboBosuTeamDetail(MipParameterVO paramVO, Model model) {
		
		// INPUT/OUTPUT DATASET GET 
		Dataset ds_list =paramVO.getDataset("ds_small");
		
		// Connection
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		
		// PARAM SET
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		param.setLang(paramVO.getVariable("G_LANG"));
		param.setsCode(ds_list.getColumnAsString(0, 0));
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectBosuTeamDetail(param); 	// 오더유형코드, 오더유형명 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="comboTemno")
	public View comboTemno(MipParameterVO paramVO, Model model) {
		Dataset dsTemno = paramVO.getDataset("ds_temno");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		List<hdel.sd.sch.domain.ComboVO> listCombo = service.selectTemno(paramVO.getVariable("G_MANDT"));

		dsTemno.deleteAll();

		for (int i = 0; i < listCombo.size(); i++) {
			dsTemno.appendRow();
			dsTemno.setColumn(i, 0, listCombo.get(i).getCODE());
			dsTemno.setColumn(i, 1, listCombo.get(i).getCODE_NAME());
		}
		
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsTemno);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
/*	@RequestMapping(value="comboZzactss")
	public View comboZzactss(MipParameterVO paramVO, Model model) {
		Dataset dsZzactss = paramVO.getDataset("ds_zzactss");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		List<hdel.sd.sch.domain.ComboVO> listCombo = service.selectZzactss(paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_LANG"));

		dsZzactss.deleteAll();

		for (int i = 0; i < listCombo.size(); i++) {
			dsZzactss.appendRow();
			dsZzactss.setColumn(i, 0, listCombo.get(i).getCODE());
			dsZzactss.setColumn(i, 1, listCombo.get(i).getCODE_NAME());
		}
		
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsZzactss);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}*/
}
