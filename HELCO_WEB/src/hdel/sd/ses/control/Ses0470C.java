package hdel.sd.ses.control;

import hdel.lib.DateUtil;
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0470S;
import hdel.sd.ses.domain.Ses0470;
import hdel.sd.ses.domain.Ses0470ParamVO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0470")
public class Ses0470C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0470S service;
	
	@RequestMapping(value="findListHeader")
	public View Ses0470FindListHeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_header");					// UIï¿½ï¿½ returnï¿½ï¿½ out dataset
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UIï¿½ï¿½ returnï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Þ¼ï¿½ï¿½ï¿½ dataset

		// RFC Ãâ·Â datasetÀ» return
		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Ses0470ParamVO param = new Ses0470ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO»ý¼º

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrqtdat(DatasetUtility.getString(dsCond,"FR_QTDAT"));
			param.setToqtdat(DatasetUtility.getString(dsCond,"TO_QTDAT"));
			param.setFrzrqdat(DatasetUtility.getString(dsCond,"FR_ZRQDAT"));
			param.setTozrqdat(DatasetUtility.getString(dsCond,"TO_ZRQDAT"));
			param.setVkbur(DatasetUtility.getString(dsCond,"VKBUR"));
			param.setVkgrp(DatasetUtility.getString(dsCond,"VKGRP"));
			param.setZkunnr(DatasetUtility.getString(dsCond,"ZKUNNR"));
			param.setZrqstat(DatasetUtility.getString(dsCond,"ZRQSTAT"));
			param.setGubun(DatasetUtility.getString(dsCond,"GUBUN"));
			param.setQtso_no(DatasetUtility.getString(dsCond,"QTSO_NO"));

			List<Ses0470> list = service.getListHeader(param);   			// ¼­ºñ½ºCALL
			dsList.deleteAll();
			
			// È£Ãâ°á°ú(list)¸¦ µ¥ÀÌÅ¸¼Â(dsList)¿¡ º¹»ç
			for (int i = 0; i < list.size(); i++) {

				dsList.appendRow();

				dsList.setColumn(i, "GUBUN",   list.get(i).getGubun());
				dsList.setColumn(i, "MANDT",   list.get(i).getMandt());
				dsList.setColumn(i, "QTSO_NO", list.get(i).getQtso_no());
				dsList.setColumn(i, "QTSER",   list.get(i).getQtser());
				dsList.setColumn(i, "VKBUR",   list.get(i).getVkbur());
				dsList.setColumn(i, "VKGRP",   list.get(i).getVkgrp());
				dsList.setColumn(i, "ZKUNNR",  list.get(i).getZkunnr());
				dsList.setColumn(i, "ZKUNNR_NM", list.get(i).getZkunnr_nm());
				dsList.setColumn(i, "ZAGNT",    list.get(i).getZagnt());
				dsList.setColumn(i, "ZAGNT_NM", list.get(i).getZagnt_nm());
				dsList.setColumn(i, "KUNNR",    list.get(i).getKunnr());
				dsList.setColumn(i, "KUNNR_NM", list.get(i).getKunnr_nm());
				dsList.setColumn(i, "GSNAM",    list.get(i).getGsnam());
				dsList.setColumn(i, "SHANGH"  , list.get(i).getShangh()); 
				dsList.setColumn(i, "MATNR"   , list.get(i).getMatnr()); 
				dsList.setColumn(i, "SPEC"    , list.get(i).getSpec()); 
				dsList.setColumn(i, "GTYPE"   , list.get(i).getGtype()); 
				dsList.setColumn(i, "TYPE1"   , list.get(i).getType1()); 
				dsList.setColumn(i, "TYPE2"   , list.get(i).getType2()); 
				dsList.setColumn(i, "TYPE3"   , list.get(i).getType3()); 
				dsList.setColumn(i, "ZNUMBER",  list.get(i).getZnumber());
				dsList.setColumn(i, "ZUSE"    , list.get(i).getZuse());
				dsList.setColumn(i, "ZOPN"    , list.get(i).getZopn());
				dsList.setColumn(i, "ZLEN"    , list.get(i).getZlen());
				dsList.setColumn(i, "ZUAM"    , list.get(i).getZuam());
				dsList.setColumn(i, "ZCOST"   , list.get(i).getZcost());
				dsList.setColumn(i, "ZEAM"    , list.get(i).getZeam());
				dsList.setColumn(i, "SHANG"   , list.get(i).getShang());
				dsList.setColumn(i, "ZRMK"    , list.get(i).getZrmk());
				dsList.setColumn(i, "ZACAPA"  , list.get(i).getZacapa());
				dsList.setColumn(i, "POSNR"   , list.get(i).getPosnr());
			}

			resultVO.addDataset(dsList);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value="findListDetail")
	public View Ses0470FindListDetail(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_detail");	// UI·Î returnÇÒ out dataset    

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI·Î returnÇÒ ¿À·ù¸Þ¼¼Áö dataset

		MipResultVO resultVO = new MipResultVO();
		try
		{ 
			Ses0470ParamVO param = new Ses0470ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAOï¿½ï¿½ï¿½ï¿½

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			//param.setGubun(DatasetUtility.getString(dsCond,"GUBUN"));
			param.setQtso_no(DatasetUtility.getString(dsCond,"QTSO_NO"));
			param.setQtser(DatasetUtility.getString(dsCond,"QTSER"));

			
			List<Ses0470> list = service.getListDetail(param);
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) { 			// È£Ãâ°á°ú(list)¸¦ µ¥ÀÌÅ¸¼Â(dsList)¿¡ º¹»ç

				dsList.appendRow();
				dsList.setColumn(i, "MANDT"      , list.get(i).getMandt());
				dsList.setColumn(i, "QTSO_NO"    , list.get(i).getQtso_no());
				dsList.setColumn(i, "QTSER"      , list.get(i).getQtser());
				dsList.setColumn(i, "ZRQSEQ"    , list.get(i).getZrqseq()); 
				dsList.setColumn(i, "ZRQFLG"        , list.get(i).getZrqflg()); 
				dsList.setColumn(i, "ZRQDAT"      , list.get(i).getZrqdat()); 
				dsList.setColumn(i, "LODAT"      , list.get(i).getLodat()); 
				dsList.setColumn(i, "ZRQCN01"      , list.get(i).getZrqcn01()); 
				dsList.setColumn(i, "ZRSRLT"      , list.get(i).getZrsrlt()); 
				dsList.setColumn(i, "FINDAT", list.get(i).getFindat());
				dsList.setColumn(i, "KUNNR_Z3"       , list.get(i).getKunnr_z3());
				dsList.setColumn(i, "KUNNR_Z3_NM"       , list.get(i).getKunnr_z3_nm());
				dsList.setColumn(i, "ZRQSTAT"      , list.get(i).getZrqstat());
			}

			resultVO.addDataset(dsList);    
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	@RequestMapping(value="findRequestList")
	public View Ses0410FindRequestList(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try
		{ 
			Ses0470ParamVO param = new Ses0470ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrzrqdat(DatasetUtility.getString(dsCond,"FR_ZRQDAT"));
			param.setTozrqdat(DatasetUtility.getString(dsCond,"TO_ZRQDAT"));
			param.setFrvkbur(DatasetUtility.getString(dsCond,"FR_VKBUR"));
			param.setTovkbur(DatasetUtility.getString(dsCond,"TO_VKBUR"));
			param.setFrvkgrp(DatasetUtility.getString(dsCond,"FR_VKGRP"));
			param.setTovkgrp(DatasetUtility.getString(dsCond,"TO_VKGRP"));
			param.setZagnt(DatasetUtility.getString(dsCond,"ZAGNT"));
			param.setZkunnr(DatasetUtility.getString(dsCond,"ZKUNNR"));			// ¿äÃ»ÀÚ
			param.setZkunnr_z3(DatasetUtility.getString(dsCond,"ZKUNNR_Z3"));	// ´ã´çÀÚ
			param.setZrqstat(DatasetUtility.getString(dsCond,"ZRQSTAT"));
			param.setZrqflg(DatasetUtility.getString(dsCond,"ZRQFLG"));
			param.setQtso_no(DatasetUtility.getString(dsCond,"QTSO_NO"));
			param.setPjt(DatasetUtility.getString(dsCond,"PJT"));
			param.setLopl(DatasetUtility.getString(dsCond,"LOPL"));
			
			if (param.getFrvkbur() == null)	{
				param.setFrvkbur("");
			}

			if (param.getTovkbur() == null)	{
				param.setTovkbur("");
			}

			if (param.getFrvkgrp() == null)	{
				param.setFrvkgrp("");
			}

			if (param.getTovkgrp() == null)	{
				param.setTovkgrp("");
			}

			Calendar calendar = Calendar.getInstance();
			List<Ses0470> list = service.getRequestList(param);  	// ï¿½ï¿½ï¿½ï¿½CALL
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) {

				dsList.appendRow(); 	// ï¿½ï¿½ï¿½ß°ï¿½
				dsList.setColumn(i, "MANDT"      , list.get(i).getMandt());
				dsList.setColumn(i, "GUBUN"      , list.get(i).getGubun());
				dsList.setColumn(i, "QTSO_NO"    , list.get(i).getQtso_no());
				dsList.setColumn(i, "QTSER"      , list.get(i).getQtser()); 
				dsList.setColumn(i, "ZRQSEQ"     , list.get(i).getZrqseq()); 
				dsList.setColumn(i, "ZRQFLG"     , list.get(i).getZrqflg()); 
				dsList.setColumn(i, "ZRQDAT"     , list.get(i).getZrqdat()); 

				calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(list.get(i).getZrqdat()));
				if(list.get(i).getJ_stdyn().equals("Y"))
					calendar.add(Calendar.DATE, new DateUtil().getRunningDays(calendar, 3));
				else if(list.get(i).getJ_stdyn().equals("N"))
					calendar.add(Calendar.DATE, new DateUtil().getRunningDays(calendar, 7));
				dsList.setColumn(i, "LODAT"      , new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));

				dsList.setColumn(i, "ZRQCN01"      , list.get(i).getZrqcn01()); 
				dsList.setColumn(i, "ZRSRLT"     , list.get(i).getZrsrlt()); 
				dsList.setColumn(i, "FINDAT"     , list.get(i).getFindat()); 
				dsList.setColumn(i, "OUTSRCDT"     , list.get(i).getOutsrcdt()); 
				dsList.setColumn(i, "OUTFINDT"     , list.get(i).getOutfindt());
				dsList.setColumn(i, "OUTCANDT"     , list.get(i).getOutcandt());
				dsList.setColumn(i, "KUNNR_Z3"    , list.get(i).getKunnr_z3());
				dsList.setColumn(i, "KUNNR_Z3_NM" , list.get(i).getKunnr_z3_nm());
				dsList.setColumn(i, "KUNNR_Z3_TEL", list.get(i).getKunnr_z3_tel());
				dsList.setColumn(i, "ZRQSTAT"     , list.get(i).getZrqstat());
				dsList.setColumn(i, "MATNR"   , list.get(i).getMatnr()); 
				dsList.setColumn(i, "SPEC"    , list.get(i).getSpec()); 
				dsList.setColumn(i, "GTYPE"   , list.get(i).getGtype()); 
				dsList.setColumn(i, "TYPE1"   , list.get(i).getType1()); 
				dsList.setColumn(i, "TYPE2"   , list.get(i).getType2()); 
				dsList.setColumn(i, "TYPE3"   , list.get(i).getType3()); 
				dsList.setColumn(i, "J_USE" , list.get(i).getJ_use());
				dsList.setColumn(i, "ZNUMBER" , list.get(i).getZnumber());
				dsList.setColumn(i, "ZUSE"    , list.get(i).getZuse());
				dsList.setColumn(i, "ZOPN"    , list.get(i).getZopn());
				dsList.setColumn(i, "ZLEN"    , list.get(i).getZlen());
				dsList.setColumn(i, "ZACAPA"  , list.get(i).getZacapa());
				dsList.setColumn(i, "VKBUR"   , list.get(i).getVkbur());
				dsList.setColumn(i, "VKGRP"   , list.get(i).getVkgrp());
				dsList.setColumn(i, "ZKUNNR"  , list.get(i).getZkunnr());
				dsList.setColumn(i, "ZKUNNR_NM",  list.get(i).getZkunnr_nm());
				dsList.setColumn(i, "ZKUNNR_TEL", list.get(i).getZkunnr_tel());
				dsList.setColumn(i, "ZAGNT",      list.get(i).getZagnt());
				dsList.setColumn(i, "ZAGNT_NM",   list.get(i).getZagnt_nm());
				dsList.setColumn(i, "POSNR",      list.get(i).getPosnr());
				dsList.setColumn(i, "GSNAM"   , list.get(i).getGsnam());
				dsList.setColumn(i, "VBELN"   , list.get(i).getVbeln());
				dsList.setColumn(i, "LP_IS"   , list.get(i).getLp_is());
				dsList.setColumn(i, "TP_DATE4" , list.get(i).getTp_date4());
				dsList.setColumn(i, "BSTDK" , list.get(i).getBstdk());
				dsList.setColumn(i, "TP_DATE2" , list.get(i).getTp_date2());
				dsList.setColumn(i, "J_SUBDAT" , list.get(i).getJ_subdat());
				dsList.setColumn(i, "J_LOPL" , list.get(i).getJ_lopl());
				dsList.setColumn(i, "J_STDYN" , list.get(i).getJ_stdyn());
				dsList.setColumn(i, "J_MODL" , list.get(i).getJ_modl());
			}

			resultVO.addDataset(dsList);    
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
}
