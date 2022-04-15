package hdel.sd.ses.control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.DateUtil;
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0400;
import hdel.sd.ses.domain.Ses0400ParamVO;
import hdel.sd.ses.domain.Ses0404;
import hdel.sd.ses.domain.Ses0404ParamVO;
import hdel.sd.ses.domain.ZSDT1048;
import hdel.sd.ses.domain.ZSDT1079;
import hdel.sd.ses.service.Ses0400S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0400")
public class Ses0400C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0400S service;
	
	@RequestMapping(value="findListHeader")
	public View Ses0400FindListHeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_header");
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Ses0400ParamVO param = new Ses0400ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			param.setMandt(paramVO.getVariable("G_MANDT"));
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

			List<Ses0400> list = service.getListHeader(param);
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) {

				dsList.appendRow();

				dsList.setColumn(i, "GUBUN",   list.get(i).getGubun());
				dsList.setColumn(i, "MANDT",   list.get(i).getMandt());
				dsList.setColumn(i, "QTSO_NO", list.get(i).getQtso_no());
				dsList.setColumn(i, "QTSER",   list.get(i).getQtser());
				dsList.setColumn(i, "QTSEQ",   list.get(i).getQtseq());
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
				dsList.setColumn(i, "ELNUM",  list.get(i).getElnum());
				dsList.setColumn(i, "ESNUM",  list.get(i).getEsnum());
				dsList.setColumn(i, "MWNUM",  list.get(i).getMwnum());
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
				dsList.setColumn(i, "AUTOLP"   , list.get(i).getAutolp());
				dsList.setColumn(i, "VBELN"   , list.get(i).getVbeln());
				dsList.setColumn(i, "HOGI"   , list.get(i).getHogi());
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
	public View Ses0400FindListDetail(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_detail");  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try
		{ 
			Ses0400ParamVO param = new Ses0400ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			//param.setGubun(DatasetUtility.getString(dsCond,"GUBUN"));
			param.setQtso_no(DatasetUtility.getString(dsCond,"QTSO_NO"));
			param.setQtser(DatasetUtility.getString(dsCond,"QTSER"));
			param.setQtseq(DatasetUtility.getString(dsCond,"QTSEQ"));
			param.setHogi(DatasetUtility.getString(dsCond,"HOGI"));

			
			List<Ses0400> list = service.getListDetail(param);
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) {
				dsList.appendRow();
				dsList.setColumn(i, "MANDT"      , list.get(i).getMandt());
				dsList.setColumn(i, "QTSO_NO"    , list.get(i).getQtso_no());
				dsList.setColumn(i, "QTSER"      , list.get(i).getQtser()); 
				dsList.setColumn(i, "QTSEQ"      , list.get(i).getQtseq()); 
				dsList.setColumn(i, "HOGI"       , list.get(i).getHogi()); 
				dsList.setColumn(i, "ZRQSEQ"    , list.get(i).getZrqseq()); 
				dsList.setColumn(i, "ZRQFLG"        , list.get(i).getZrqflg()); 
				dsList.setColumn(i, "ZRQDAT"      , list.get(i).getZrqdat()); 
				dsList.setColumn(i, "LODAT"      , list.get(i).getLodat()); 
				dsList.setColumn(i, "ZRQCN"      , list.get(i).getZrqcn()); 
				dsList.setColumn(i, "ZRSRLT"      , list.get(i).getZrsrlt()); 
				dsList.setColumn(i, "FINDAT", list.get(i).getFindat());
				dsList.setColumn(i, "KUNNR_Z3"       , list.get(i).getKunnr_z3());
				dsList.setColumn(i, "KUNNR_Z3_NM"       , list.get(i).getKunnr_z3_nm());
				dsList.setColumn(i, "ZRQSTAT"      , list.get(i).getZrqstat());
				dsList.setColumn(i, "CISTAT"      , list.get(i).getCistat());
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

		Calendar calendar = Calendar.getInstance(); 
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try
		{ 
			Ses0400ParamVO param = new Ses0400ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrzrqdat(DatasetUtility.getString(dsCond,"FR_ZRQDAT"));
			param.setTozrqdat(DatasetUtility.getString(dsCond,"TO_ZRQDAT"));
			param.setFrvkbur(DatasetUtility.getString(dsCond,"FR_VKBUR"));
			param.setTovkbur(DatasetUtility.getString(dsCond,"TO_VKBUR"));
			param.setFrvkgrp(DatasetUtility.getString(dsCond,"FR_VKGRP"));
			param.setTovkgrp(DatasetUtility.getString(dsCond,"TO_VKGRP"));
			param.setZagnt(DatasetUtility.getString(dsCond,"ZAGNT"));
			param.setZkunnr(DatasetUtility.getString(dsCond,"ZKUNNR"));
			param.setZkunnr_z3(DatasetUtility.getString(dsCond,"ZKUNNR_Z3"));
			param.setZrqstat(DatasetUtility.getString(dsCond,"ZRQSTAT"));
			param.setZrqflg(DatasetUtility.getString(dsCond,"ZRQFLG"));
			param.setQtso_no(DatasetUtility.getString(dsCond,"QTSO_NO"));
			param.setPjt(DatasetUtility.getString(dsCond,"PJT"));
			param.setOutgbn(DatasetUtility.getString(dsCond,"OUTGBN"));
			
			if ("Y".equals(DatasetUtility.getString(dsCond,"SPDOCU")))
				param.setSpdocu(DatasetUtility.getString(dsCond,"SPDOCU"));
			
			if (param.getFrvkbur() == null)
				param.setFrvkbur("");

			if (param.getTovkbur() == null)
				param.setTovkbur("");

			if (param.getFrvkgrp() == null)
				param.setFrvkgrp("");

			if (param.getTovkgrp() == null)
				param.setTovkgrp("");

			List<Ses0400> list = service.getRequestList(param);
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) {
				dsList.appendRow(); 
				dsList.setColumn(i, "MANDT"      , list.get(i).getMandt());
				dsList.setColumn(i, "GUBUN"      , list.get(i).getGubun());
				dsList.setColumn(i, "QTSO_NO"    , list.get(i).getQtso_no());
				dsList.setColumn(i, "QTSER"      , list.get(i).getQtser()); 
				dsList.setColumn(i, "QTSEQ"      , list.get(i).getQtseq()); 
				dsList.setColumn(i, "HOGI"       , list.get(i).getHogi()); 
				dsList.setColumn(i, "ZRQSEQ"     , list.get(i).getZrqseq()); 
				dsList.setColumn(i, "ZRQFLG"     , list.get(i).getZrqflg()); 
				dsList.setColumn(i, "ZRQDAT"     , list.get(i).getZrqdat()); 
				dsList.setColumn(i, "LODAT"      , list.get(i).getLodat()); 
				dsList.setColumn(i, "ZRQCN"      , list.get(i).getZrqcn()); 
				dsList.setColumn(i, "ZRSRLT"     , list.get(i).getZrsrlt()); 
				dsList.setColumn(i, "FINDAT"     , list.get(i).getFindat()); 
				dsList.setColumn(i, "OUTGBN"     , list.get(i).getOutgbn()); 
				dsList.setColumn(i, "OUTSRCDT"     , list.get(i).getOutsrcdt()); 
				dsList.setColumn(i, "OUTFINDT"     , list.get(i).getOutfindt()); 
				dsList.setColumn(i, "OUTCANDT"     , list.get(i).getOutcandt()); 
				dsList.setColumn(i, "OUTACTDT"     , list.get(i).getOutactdt()); 
				dsList.setColumn(i, "OUTRETDT"     , list.get(i).getOutretdt());
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
				dsList.setColumn(i, "ZNUMBER" , list.get(i).getZnumber());
				dsList.setColumn(i, "ELNUM" , list.get(i).getElnum());
				dsList.setColumn(i, "ESNUM" , list.get(i).getEsnum());
				dsList.setColumn(i, "MWNUM" , list.get(i).getMwnum());
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
				if(list.get(i).getLp_is().equals("Y") && list.get(i).getLp_chn().equals("Y"))
					dsList.setColumn(i, "STDCAD"   , "N");
				else
					if(list.get(i).getLp_is().equals("N") && list.get(i).getNonstd().equals("Y"))
						dsList.setColumn(i, "STDCAD"   , "N");
					else
						if(list.get(i).getLp_is().equals("Y"))
							dsList.setColumn(i, "STDCAD"   , "Y");
						else
							dsList.setColumn(i, "STDCAD"   , "N");
				dsList.setColumn(i, "LP_IS"   , list.get(i).getLp_is());
				dsList.setColumn(i, "TP_DATE4" , list.get(i).getTp_date4());
				dsList.setColumn(i, "BSTDK" , list.get(i).getBstdk());
				dsList.setColumn(i, "TP_DATE2" , list.get(i).getTp_date2());
				dsList.setColumn(i, "ADMITNO" , list.get(i).getAdmitno());
				dsList.setColumn(i, "SPDOCU" , list.get(i).getSpdocu());
				dsList.setColumn(i, "LP_CHN"   , list.get(i).getLp_chn());
				dsList.setColumn(i, "LP_OLD"   , list.get(i).getLp_old());
				dsList.setColumn(i, "NONSTD"   , list.get(i).getNonstd());
				dsList.setColumn(i, "BEZEI"   , list.get(i).getBezei());
				dsList.setColumn(i, "DREUSE" , list.get(i).getDreuse());
				dsList.setColumn(i, "ZBDTYP", list.get(i).getZbdtyp());
				dsList.setColumn(i, "LHSITE", list.get(i).getLhsite());
				dsList.setColumn(i, "YCONT", list.get(i).getYcont());
				dsList.setColumn(i, "WRKDAYS",	list.get(i).getWrkdays());
				dsList.setColumn(i, "ACTDT", list.get(i).getActdt());
				dsList.setColumn(i, "RETDT", list.get(i).getRetdt());
				
				if(NumberUtils.toFloat(list.get(i).getRetts()) > 0 && NumberUtils.toFloat(list.get(i).getRetts()) <= NumberUtils.toFloat(list.get(i).getActts()))
					dsList.setColumn(i, "RRQDT", list.get(i).getActdt());
				if(list.get(i).getOutsrcdt().equals("") != true && list.get(i).getWrkdays() != null) {
					calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(list.get(i).getOutsrcdt().equals("") ? "00000000" : list.get(i).getOutsrcdt()));
					calendar.add(Calendar.DATE, new DateUtil().getRunningDays(calendar, NumberUtils.toInt(list.get(i).getWrkdays(),0)));
					calendar.add(Calendar.DATE, new DateUtil().getRunningDays(calendar, NumberUtils.toInt(list.get(i).getDelaydays(),0)));	// -1 ??
					
					list.get(i).setCadduedt(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
					dsList.setColumn(i, "CADDUEDT",	list.get(i).getCadduedt());
					
					//외주완료 예정일자 추가 건 : 2020.09.21 jss
					if("E".equals(list.get(i).getCadreqtype())){ //사양배포+도면
						calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(list.get(i).getCadduedt()));
						calendar.add(Calendar.DATE, new DateUtil().getRunningDays(calendar, 3));
						list.get(i).setCadduedt(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
						dsList.setColumn(i, "CADDUEDT",	list.get(i).getCadduedt());
					}else if("D".equals(list.get(i).getCadreqtype())){
						//calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse( list.get(i).getOutsrcdt()));
						calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(list.get(i).getOutsrcdt().equals("") ? "00000000" : list.get(i).getOutsrcdt()));
						calendar.add(Calendar.DATE, new DateUtil().getRunningDays(calendar, 3));
						list.get(i).setCadduedt(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
						dsList.setColumn(i, "CADDUEDT",	list.get(i).getCadduedt());
					}
					
				}
				dsList.setColumn(i, "CADBACKDT", list.get(i).getCadbackdt());
				dsList.setColumn(i, "CADCONFDT", list.get(i).getCadconfdt());
				dsList.setColumn(i, "CISTAT", list.get(i).getCistat());
				dsList.setColumn(i, "FRCMFCDAT", list.get(i).getFrcmfcdat());
				dsList.setColumn(i, "ZZCHAKD" , list.get(i).getZzchakd());
				dsList.setColumn(i, "ZDESC",list.get(i).getZdesc());
				
				List<ZSDT1079> z1079 = list.get(i).getZsdt1079();
				for(int j=0; j < z1079.size(); j++) {
					if(z1079.get(j).getChgchrpt() == null)
						continue;
					if(z1079.get(j).getChgchrpt().equals("A"))
						dsList.setColumn(i, "CHGCHRPTA" , "A");
					if(z1079.get(j).getChgchrpt().equals("B"))
						dsList.setColumn(i, "CHGCHRPTB" , "B");
					if(z1079.get(j).getChgchrpt().equals("C"))
						dsList.setColumn(i, "CHGCHRPTC" , "C");
					if(z1079.get(j).getChgchrpt().equals("D"))
						dsList.setColumn(i, "CHGCHRPTD" , "D");
					if(z1079.get(j).getChgchrpt().equals("E"))
						dsList.setColumn(i, "CHGCHRPTE" , "E");
				}
				if(list.get(i).getContr_da().equals("00000000") == false) {
					calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(list.get(i).getContr_da()));
					calendar.add(Calendar.DATE, new DateUtil().getRunningDays(calendar, 10));
					dsList.setColumn(i, "SURVEYDUEDT" , new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
				}
				dsList.setColumn(i, "SURVEYRCPDT" , list.get(i).getSurveyrcpdt());
				dsList.setColumn(i, "CADREQTYPE" , list.get(i).getCadreqtype());
				dsList.setColumn(i, "ZPRGFLG" , list.get(i).getZprgflg()); //2021.02 jss

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

	@RequestMapping(value="queryOutsourcingBlueprint")
	public View queryOutsourcingBlueprint(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");  
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try
		{ 
			Ses0404ParamVO param = new Ses0404ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrzrqdat(DatasetUtility.getString(dsCond,"FR_ZRQDAT"));
			param.setTozrqdat(DatasetUtility.getString(dsCond,"TO_ZRQDAT"));
			param.setFrvkbur(DatasetUtility.getString(dsCond,"FR_VKBUR"));
			param.setTovkbur(DatasetUtility.getString(dsCond,"TO_VKBUR"));
			param.setFrvkgrp(DatasetUtility.getString(dsCond,"FR_VKGRP"));
			param.setTovkgrp(DatasetUtility.getString(dsCond,"TO_VKGRP"));

			param.setPtnz2(DatasetUtility.getString(dsCond,"PTNZ2"));
			param.setPtnz3(DatasetUtility.getString(dsCond,"PTNZ3"));
			param.setZrqstat(DatasetUtility.getString(dsCond,"ZRQSTAT"));
			param.setZrqflg(DatasetUtility.getString(dsCond,"ZRQFLG"));
			param.setPjt(DatasetUtility.getString(dsCond,"PJT"));
			param.setQtnum(DatasetUtility.getString(dsCond,"QTNUM"));
			
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
			List<Ses0404> list = service.queryOutsourcingBlueprint(param);

			dsList.deleteAll();
			for (int i = 0; i < list.size(); i++) {
				dsList.appendRow();
				dsList.setColumn(i, "ZRQSEQ",	list.get(i).getZrqseq());
				dsList.setColumn(i, "QTNUM",	list.get(i).getQtnum());
				dsList.setColumn(i, "QTSER",	list.get(i).getQtser());
				dsList.setColumn(i, "ZZPJT_ID",	list.get(i).getPjt());
				dsList.setColumn(i, "GSNAM",	list.get(i).getGsnam());
				dsList.setColumn(i, "OUTGBN",	list.get(i).getOutgbn());
				dsList.setColumn(i, "ZNUMBER",	list.get(i).getZnumber());
				dsList.setColumn(i, "OUTGRD",	list.get(i).getOutgrd());
				dsList.setColumn(i, "OUTLOQ",	list.get(i).getOutloq());
				dsList.setColumn(i, "OUTAMT",	list.get(i).getOutamt());
				dsList.setColumn(i, "ZRQDAT",	list.get(i).getZrqdat());
				dsList.setColumn(i, "OUTSRCDT",	list.get(i).getOutsrcdt());
				dsList.setColumn(i, "OUTACTDT",	list.get(i).getOutactdt());
				dsList.setColumn(i, "OUTRETDT",	list.get(i).getOutretdt());
				dsList.setColumn(i, "OUTFINDT",	list.get(i).getOutfindt());
				dsList.setColumn(i, "FINDAT",	list.get(i).getFindat());
				dsList.setColumn(i, "DELAYDAYS",	list.get(i).getDelaydays());
				dsList.setColumn(i, "WRKDAYS",	list.get(i).getWrkdays());
				
				if(list.get(i).getOutsrcdt().equals("") != true && list.get(i).getWrkdays() != null) {
					calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(list.get(i).getOutsrcdt().equals("") ? "00000000" : list.get(i).getOutsrcdt()));
					calendar.add(Calendar.DATE, new DateUtil().getRunningDays(calendar, NumberUtils.toInt(list.get(i).getWrkdays(),0)) - 1);
					//calendar.add(Calendar.DATE, NumberUtils.toInt(list.get(i).getDelaydays(),0)); // -1 ????
					calendar.add(Calendar.DATE, new DateUtil().getRunningDays(calendar, NumberUtils.toInt(list.get(i).getDelaydays(),0)));	// -1 ??
					
					list.get(i).setDuedt(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
					dsList.setColumn(i, "DUEDT",	list.get(i).getDuedt());
					if(NumberUtils.toFloat(list.get(i).getOutfindt(),0) == 0) {
						if(NumberUtils.toFloat(list.get(i).getDuedt(),0) < NumberUtils.toFloat(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()),0))
							list.get(i).setInduedt("N");
						else
							list.get(i).setInduedt("");
					} else {
						if(NumberUtils.toFloat(list.get(i).getOutfindt(),0) <= NumberUtils.toFloat(list.get(i).getDuedt(),0))
							list.get(i).setInduedt("Y");
						else
							list.get(i).setInduedt("N");
					}
				}
				dsList.setColumn(i, "INDUEDT",	list.get(i).getInduedt());
				dsList.setColumn(i, "OUTMAN",	list.get(i).getOutman());
				dsList.setColumn(i, "PTNZ3NM",	list.get(i).getPtnz3nm());
				dsList.setColumn(i, "PTNZ2NM",	list.get(i).getPtnz2nm());
				dsList.setColumn(i, "VKBUR",	list.get(i).getVkbur());
				dsList.setColumn(i, "VKGRP",	list.get(i).getVkgrp());
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

	@RequestMapping(value="updateDelayDays")
	public View updateDelayDays(MipParameterVO paramVO, Model model) {
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); 
		try {
			service.updateDelayDays(paramVO);
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	@RequestMapping(value = "checkAtnam")
	public View Ses0400CheckAtnam(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset ds_template_a = paramVO.getDataset("ds_template_a");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();
		try {
			Ses0400ParamVO param = new Ses0400ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			param.setMandt(paramVO.getVariable("G_MANDT")); // SAP CLIENT
			param.setQtso_no(DatasetUtility.getString(dsCond, "QTSO_NO"));
			param.setQtser(DatasetUtility.getString(dsCond, "QTSER"));

			ds_template_a.deleteAll();
			List<ZSDT1048> list = service.getAtnam(param);

			for (int i = 0; i < list.size(); i++) {
				ds_template_a.appendRow();
				ds_template_a.setColumn(i, "PRD", list.get(i).getPrd());
				ds_template_a.setColumn(i, "ATBEZ", list.get(i).getAtbez());
			}

			if (ds_template_a.getRowCount() == 0) {
				resultVO.addVariable("ChkAtnam", "true");
			}

		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		resultVO.addDataset(ds_template_a);
		model.addAttribute("resultVO", resultVO);

		return view;
	}

}