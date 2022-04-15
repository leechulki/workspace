package hdel.sd.ses.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
//import com.tivoli.pd.jras.pdjlog.jlog.Logger;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.ZLang;
import hdel.sd.ses.domain.Ses0030;
import hdel.sd.ses.domain.Ses0030ParamVO;
import hdel.sd.ses.service.Ses0030S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0030")
public class Ses0030C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0030S service;
	
	@RequestMapping(value="header")
	public View Ses0030HeaderView(MipParameterVO paramVO, Model model) {
	
		Dataset dsCond	= paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_header"); 

		dsList = headerToDataset(dsList, dsCond, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); 	
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	@RequestMapping(value="detail")
	public View Ses0030DtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_detail");  

		dsList = detailToDataset(dsList, dsCond, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO();	
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	private Dataset headerToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {

		Ses0030ParamVO param = new Ses0030ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		String lang     = paramVO.getVariable("G_LANG");
		String mandt    = DatasetUtility.getString(dsCond,"MANDT"     );
		String frqtdat   = DatasetUtility.getString(dsCond,"FR_QTDAT"  );
		String toqtdat   = DatasetUtility.getString(dsCond,"TO_QTDAT"  );
		String frzestdat = DatasetUtility.getString(dsCond,"FR_ZESTDAT");
		String tozestdat = DatasetUtility.getString(dsCond,"TO_ZESTDAT");
		String vkbur     = DatasetUtility.getString(dsCond,"VKBUR"       );
		String vkgrp     = DatasetUtility.getString(dsCond,"VKGRP"       );
		String zprgflg   = DatasetUtility.getString(dsCond,"ZPRGFLG"     );
		String zkunnr    = DatasetUtility.getString(dsCond,"SMAN"        );
		String auart     = DatasetUtility.getString(dsCond,"AGNTGB"        );
		String qtnum    = DatasetUtility.getString(dsCond,"QTNUM"     );
		String qtser      = DatasetUtility.getString(dsCond,"QTSER"       );
		
		String zregn    = DatasetUtility.getString(dsCond,"ARA"        );
		String gno    = DatasetUtility.getString(dsCond,"GNO"     );
		String darijumzkunnr      = DatasetUtility.getString(dsCond,"DARIJUMUSER"       );
		
		String max_qtser   = DatasetUtility.getString(dsCond,"MAX_QTSER"	);		//2016.12.09 최종 차수 조회 여부 추가 by mj.Shin
		String qtgbn = DatasetUtility.getString(dsCond,"QTGBN"        ); //견적구분 조회조건 추가 2021.04.16.
		
		if (mandt    == null) mandt    = "";
		if (frqtdat    == null) frqtdat   = "";
		if (toqtdat   == null) toqtdat   = "";
		if (frzestdat  == null) frzestdat = "";
		if (tozestdat == null) tozestdat = "";
		if (vkbur     == null) vkbur     = "";
		if (vkgrp     == null) vkgrp     = "";
		if (zprgflg   == null) zprgflg    = "";
		if (zkunnr   == null) zkunnr    = "";
		if (auart   == null)  auart    = "";
		if (qtnum    == null) qtnum    = "";
		if (qtser      == null) qtser      = "";
		if (zregn   == null)  zregn    = "";
		if (gno    == null) gno    = "";
		if (darijumzkunnr      == null) darijumzkunnr      = "";
		if (max_qtser	== null) max_qtser	= "";	//2016.12.09 최종 차수 조회 여부 추가 by mj.Shin
		if (qtgbn	== null) qtgbn	= ""; //견적구분 조회조건 추가 2021.04.16.
		
		param.setLang     (lang);    // 'en' OR 'ko'
		param.setMandt    (mandt);   // SAP CLIENT
		param.setFrqtdat   (frqtdat);
		param.setToqtdat  (toqtdat);
		param.setFrzestdat (frzestdat);
		param.setTozestdat(tozestdat);
		param.setVkbur    (vkbur);
		param.setVkgrp    (vkgrp);
		param.setZprgflg  (zprgflg);
		param.setZkunnr  (zkunnr);
		param.setAuart   (auart);
		param.setQtnum   (qtnum);
		param.setQtser     (qtser);
		
		if ("B1".equals(zregn)){
			param.setZregn("E5");	
		}else{
			param.setZregn(zregn);
		}
		param.setGno(gno);
		param.setDarijumzkunnr(darijumzkunnr);
		param.setMax_qtser(max_qtser);
		param.setQtgbn(qtgbn);  //견적구분 조회조건 추가 2021.04.16.
		param.setTquot(paramVO.getVariable("TQUOT"));
		// 모의견적여부 추가 2021.06.08
		param.setSmlysno(paramVO.getVariable("SMLYSNO"));
		
		List<Ses0030> list = service.searchHeader(param);
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {

			dsList.appendRow();
			dsList.setColumn(i, "MANDT"            , list.get(i).getMandt());
			dsList.setColumn(i, "QTNUM"            , list.get(i).getQtnum());
			dsList.setColumn(i, "QTSER"              , list.get(i).getQtser()); 
			dsList.setColumn(i, "QTDAT"             , list.get(i).getQtdat());
			dsList.setColumn(i, "SPART"              , list.get(i).getSpart());
			dsList.setColumn(i, "QTGBN"             , list.get(i).getQtgbn());
			dsList.setColumn(i, "ZBDTYP"             , list.get(i).getZbdtyp());
			dsList.setColumn(i, "ZPRGFLG"            , list.get(i).getZprgflg());
			dsList.setColumn(i, "VKBUR"              , list.get(i).getVkbur());
			dsList.setColumn(i, "VKGRP"              , list.get(i).getVkgrp());
			dsList.setColumn(i, "ZKUNNR"           , list.get(i).getZkunnr());
			dsList.setColumn(i, "ZAGNT"             , list.get(i).getZagnt());
			dsList.setColumn(i, "KUNNR"             , list.get(i).getKunnr());
			dsList.setColumn(i, "ZCST"                , list.get(i).getZcst());
			dsList.setColumn(i, "ZGNM"              , list.get(i).getZgnm());
			dsList.setColumn(i, "ZDEV"               , list.get(i).getZdev());
			dsList.setColumn(i, "GSNAM"            , list.get(i).getGsnam());
			dsList.setColumn(i, "ZREGN"             , list.get(i).getZregn());
			dsList.setColumn(i, "ZPID"                , list.get(i).getZpid());
			dsList.setColumn(i, "ZTEL"                , list.get(i).getZtel());
			dsList.setColumn(i, "ZADDR_ZPOST"    , list.get(i).getZaddr_zpost());
			dsList.setColumn(i, "ZADDR_ADR1"     , list.get(i).getZaddr_adr1());
			dsList.setColumn(i, "ZADDR_ADR2"     , list.get(i).getZaddr_adr2());
			dsList.setColumn(i, "SOABLE"            , list.get(i).getSoable());
			dsList.setColumn(i, "ZESTDAT"          , list.get(i).getZestdat());
			dsList.setColumn(i, "DELDAT"            , list.get(i).getDeldat());
			dsList.setColumn(i, "ZDVMTS"           , list.get(i).getZdvmts());
			dsList.setColumn(i, "ZSUM_ZNUMBER", list.get(i).getZsum_znumber());
			dsList.setColumn(i, "ZSUM_ZCOST"     , list.get(i).getZsum_zcost());
			dsList.setColumn(i, "ZSUM_ZEAM"      , list.get(i).getZsum_zeam());
			dsList.setColumn(i, "ZSUM_SHANG"    , list.get(i).getZsum_shang());
			dsList.setColumn(i, "INCO"               , list.get(i).getInco());
			dsList.setColumn(i, "ZICIF_PPRT"        , list.get(i).getZicif_pprt());
			dsList.setColumn(i, "ZICIF_PPCT"        , list.get(i).getZicif_ppct());
			dsList.setColumn(i, "ZICIF_PPCD"        , list.get(i).getZicif_ppcd());
			dsList.setColumn(i, "ZICIF_1STRT"       , list.get(i).getZicif_1strt());
			dsList.setColumn(i, "ZICIF_1STCT"       , list.get(i).getZicif_1stct());
			dsList.setColumn(i, "ZICIF_1STCD"       , list.get(i).getZicif_1stcd());
			dsList.setColumn(i, "ZICIF_2STRT"       , list.get(i).getZicif_2strt());
			dsList.setColumn(i, "ZICIF_2STCT"       , list.get(i).getZicif_2stct());
			dsList.setColumn(i, "ZICIF_2STCD"       , list.get(i).getZicif_2stcd());
			dsList.setColumn(i, "ZICIF_RMRT"       , list.get(i).getZicif_rmrt());
			dsList.setColumn(i, "ZICIF_RMCT"       , list.get(i).getZicif_rmct());
			dsList.setColumn(i, "ZICIF_RMCD"       , list.get(i).getZicif_rmcd());
			dsList.setColumn(i, "SPRMK1"            , list.get(i).getSprmk1());
			dsList.setColumn(i, "SPRMK2"            , list.get(i).getSprmk2());
			dsList.setColumn(i, "SPRMK3"            , list.get(i).getSprmk3());
			dsList.setColumn(i, "SPRMK4"            , list.get(i).getSprmk4());
			dsList.setColumn(i, "SPRMK5"            , list.get(i).getSprmk5());
			dsList.setColumn(i, "SPRMK6"            , list.get(i).getSprmk6());
			dsList.setColumn(i, "SPRMK7"            , list.get(i).getSprmk7());
			dsList.setColumn(i, "SPRMK8"            , list.get(i).getSprmk8());
			dsList.setColumn(i, "SPRMK9"            , list.get(i).getSprmk9());
			dsList.setColumn(i, "SPRMK10"           , list.get(i).getSprmk10());
			dsList.setColumn(i, "ZSOFLG"             , list.get(i).getZsoflg());
			dsList.setColumn(i, "SONNR"             , list.get(i).getSonnr());
			dsList.setColumn(i, "VBELN"              , list.get(i).getVbeln());
			dsList.setColumn(i, "WAERK"             , list.get(i).getWaerk());
			dsList.setColumn(i, "AUART"             , list.get(i).getAuart());
			dsList.setColumn(i, "CO_DDVRTQ"       , list.get(i).getCo_ddvrtq());
			dsList.setColumn(i, "CO_DEPHTQ"       , list.get(i).getCo_dephtq());
			dsList.setColumn(i, "CO_DSSQ"          , list.get(i).getCo_dssq());
			dsList.setColumn(i, "CO_BCLCDTQ"     , list.get(i).getCo_bclcdtq());
			dsList.setColumn(i, "CO_CHPITQ"       , list.get(i).getCo_chpitq());
			dsList.setColumn(i, "CO_ASPC"          , list.get(i).getCo_aspc());
			dsList.setColumn(i, "CO_DSV1"          , list.get(i).getCo_dsv1());
			dsList.setColumn(i, "CO_DSV1TQ"       , list.get(i).getCo_dsv1tq());
			dsList.setColumn(i, "CO_DSV2"          , list.get(i).getCo_dsv2());
			dsList.setColumn(i, "CO_DSV2TQ"       , list.get(i).getCo_dsv2tq());
			dsList.setColumn(i, "CDATE"              , list.get(i).getCdate());
			dsList.setColumn(i, "CTIME"              , list.get(i).getCtime());
			dsList.setColumn(i, "CUSER"              , list.get(i).getCuser());
			dsList.setColumn(i, "UDATE"              , list.get(i).getUdate());
			dsList.setColumn(i, "UTIME"              , list.get(i).getUtime());
			dsList.setColumn(i, "UUSER"              , list.get(i).getUuser());
			dsList.setColumn(i, "DDATE"              , list.get(i).getDdate());
			dsList.setColumn(i, "DTIME"              , list.get(i).getDtime());
			dsList.setColumn(i, "DUSER"              , list.get(i).getDuser());
			dsList.setColumn(i, "ZLZONE"            , list.get(i).getZlzone());
			dsList.setColumn(i, "REGION"            , list.get(i).getRegion());
			dsList.setColumn(i, "BUYR_NM"          , list.get(i).getBuyr_nm());
			dsList.setColumn(i, "ZKUNNR_NM"      , list.get(i).getZkunnr_nm());
			dsList.setColumn(i, "ZAGNT_NM"        , list.get(i).getZagnt_nm());
			dsList.setColumn(i, "ZEAM"               , list.get(i).getZeam());
			dsList.setColumn(i, "ZAGNTFLG"          , list.get(i).getZagntflg());
			dsList.setColumn(i, "INCO2"               , list.get(i).getInco2());
			dsList.setColumn(i, "ZATTPATH"          , list.get(i).getZattpath());
			dsList.setColumn(i, "ZATTFN"             , list.get(i).getZattfn());
			dsList.setColumn(i, "ZATTFN_OR"        , list.get(i).getZattfn_or());
			dsList.setColumn(i, "PART_CNT"          , list.get(i).getPart_cnt());
			dsList.setColumn(i, "FA_BYRGBN"         , list.get(i).getFa_byrgbn());
			dsList.setColumn(i, "ZDELI"                , list.get(i).getZdeli());
			dsList.setColumn(i, "TEL"                , list.get(i).getTel());
			dsList.setColumn(i, "FAX"                , list.get(i).getFax());
			dsList.setColumn(i, "CEL"                , list.get(i).getCel());
			dsList.setColumn(i, "ZRTRSN"             , list.get(i).getZrtrsn());
			dsList.setColumn(i, "MAIL"             , list.get(i).getMail());
			dsList.setColumn(i, "LAND1"             , list.get(i).getLand1());
			dsList.setColumn(i, "LANDX"             , list.get(i).getLandx());
			dsList.setColumn(i, "JGBN"             , list.get(i).getJgbn());
			dsList.setColumn(i, "AQTNUM"             , list.get(i).getAqtnum());
			dsList.setColumn(i, "OPPID"             , list.get(i).getOppid());
			dsList.setColumn(i, "AUTOLP"            , list.get(i).getAutolp());
			dsList.setColumn(i, "UCH_DUTY"            , list.get(i).getUch_duty());
			dsList.setColumn(i, "FRCMFCDAT"			, list.get(i).getFrcmfcdat());
			dsList.setColumn(i, "CONQTY20"          , list.get(i).getConqty20());
			dsList.setColumn(i, "CONQTY40"			, list.get(i).getConqty40());
			
			dsList.setColumn(i, "EGIS_FLAG"         , list.get(i).getEgis_flag());
			dsList.setColumn(i, "EGIS_EST_NO"		, list.get(i).getEgis_est_no());
			dsList.setColumn(i, "EGIS_EST_SEQ"      , list.get(i).getEgis_est_seq());
			dsList.setColumn(i, "PSPID_CD"			, list.get(i).getPspid_co());
			dsList.setColumn(i, "OPENDT"			, list.get(i).getOpendt());
			dsList.setColumn(i, "DEPSPORT"			, list.get(i).getDestport());
			dsList.setColumn(i, "LIFNRCHK"			, list.get(i).getLifnrchk());
			dsList.setColumn(i, "SID"			    , list.get(i).getSid());
			dsList.setColumn(i, "ACHDT"				, list.get(i).getAchdt());
			dsList.setColumn(i, "QTVADT"		    , list.get(i).getQtvadt());
			dsList.setColumn(i, "FA_CHK1"		    , list.get(i).getFa_chk1());
			dsList.setColumn(i, "FA_CHK2"		    , list.get(i).getFa_chk2());
		}

		return dsList;
	}

	private Dataset detailToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {

		Ses0030ParamVO param = new Ses0030ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		String mandt = DatasetUtility.getString(dsCond, "MANDT");
		String qtnum = DatasetUtility.getString(dsCond, "QTNUM");
		String qtser   = DatasetUtility.getString(dsCond, "QTSER");
		String spras  = ZLang.convSapLang(paramVO.getVariable("G_LANG"));
		
		if (mandt == null) mandt = "";
		if (qtnum == null) qtnum = "";
		if (qtser == null) qtser   = "0";
		if (spras == null || spras == "" ) spras = "3";
		
		param.setMandt(mandt);	// SAP CLIENT
		param.setQtnum(qtnum);
		param.setQtser  (qtser  );
		param.setLang (spras);   // 'en' OR 'ko' => 'E', '3'

/*		// INPUT PARAM INFO --------------------------------------------------------------------------------------------    		
		System.out.print("\n@@@ dsCond.MANDT ===>"+DatasetUtility.getString(dsCond,"MANDT")+"\n");
		System.out.print("\n@@@ dsCond.QTNUM ===>"+DatasetUtility.getString(dsCond,"QTNUM")+"\n");
		System.out.print("\n@@@ dsCond.QTSER	 ===>"+DatasetUtility.getString(dsCond,"QTSER")	+"\n");*/
		
		List<Ses0030> list = service.searchDetail(param);
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {

			dsList.appendRow();
			dsList.setColumn(i, "MANDT"    , list.get(i).getMandt());
			dsList.setColumn(i, "QTNUM"    , list.get(i).getQtnum());
			dsList.setColumn(i, "QTSER"      , list.get(i).getQtser()); 
			dsList.setColumn(i, "QTSEQ"      , list.get(i).getQtseq()); 
			dsList.setColumn(i, "SHANGH"   , list.get(i).getShangh()); 
			dsList.setColumn(i, "ZSAREA"    , list.get(i).getZsarea());
			dsList.setColumn(i, "MATNR"    , list.get(i).getMatnr()); 
			dsList.setColumn(i, "ZPRDGBN"  , list.get(i).getZprdgbn());
			dsList.setColumn(i, "SPEC"        , list.get(i).getSpec());
			dsList.setColumn(i, "GTYPE"      , list.get(i).getGtype()); 
			dsList.setColumn(i, "TYPE1"      , list.get(i).getType1()); 
			dsList.setColumn(i, "TYPE2"      , list.get(i).getType2()); 
			dsList.setColumn(i, "TYPE3"      , list.get(i).getType3()); 
			dsList.setColumn(i, "ZNUMBER", list.get(i).getZnumber());
			dsList.setColumn(i, "ZACAPA"   , list.get(i).getZacapa());
			dsList.setColumn(i, "ZUSE"       , list.get(i).getZuse());
			dsList.setColumn(i, "ZOPN"      , list.get(i).getZopn());
			dsList.setColumn(i, "ZLEN"       , list.get(i).getZlen());
			dsList.setColumn(i, "ZOUTC"       , list.get(i).getZoutc());
			dsList.setColumn(i, "ZUAM"     , list.get(i).getZuam());
			dsList.setColumn(i, "ZUAMS"     , list.get(i).getZuams());
			dsList.setColumn(i, "ZURATE"     , list.get(i).getZurate()); 		
			dsList.setColumn(i, "ZCOST"     , list.get(i).getZcost());
			dsList.setColumn(i, "ZCOSTM"   , list.get(i).getZcostm());
			dsList.setColumn(i, "ZEAM"      , list.get(i).getZeam());
			dsList.setColumn(i, "ZEAM_ORG"      , list.get(i).getZeam_org());
			dsList.setColumn(i, "SHANG"    , list.get(i).getShang());
			dsList.setColumn(i, "ZRMK"      , list.get(i).getZrmk());
			dsList.setColumn(i, "SONNR"    , list.get(i).getSonnr());
			dsList.setColumn(i, "CDATE"     , list.get(i).getCdate());
			dsList.setColumn(i, "CTIME"     , list.get(i).getCtime());
			dsList.setColumn(i, "CUSER"     , list.get(i).getCuser());
			dsList.setColumn(i, "UDATE"     , list.get(i).getUdate());
			dsList.setColumn(i, "UTIME"     , list.get(i).getUtime());
			dsList.setColumn(i, "UUSER"     , list.get(i).getUuser());
			dsList.setColumn(i, "ZUSE_NM"   , list.get(i).getZuse_nm());
			dsList.setColumn(i, "ZPRGFLG"   , list.get(i).getZprgflg());
			dsList.setColumn(i, "CONQTY20"  , list.get(i).getConqty20());
			dsList.setColumn(i, "CONQTY40"	, list.get(i).getConqty40());			
			dsList.setColumn(i, "EGIS_EST_NO"		, list.get(i).getEgis_est_no());
			dsList.setColumn(i, "EGIS_EST_SEQ"      , list.get(i).getEgis_est_seq());
			dsList.setColumn(i, "EGIS_EST_SERNO"    , list.get(i).getEgis_est_serno());
			dsList.setColumn(i, "EGIS_SPEC_TYPE"    , list.get(i).getEgis_spec_type());
			dsList.setColumn(i, "INCRTF_TP", list.get(i).getIncrtf_tp());

			dsList.setColumn(i, "BRNDCD", list.get(i).getBrndcd());
			dsList.setColumn(i, "BRNDNM", list.get(i).getBrndnm());
			dsList.setColumn(i, "BRNDSEQ", list.get(i).getBrndseq());
			dsList.setColumn(i, "SNDSYS", list.get(i).getSndSys());
			dsList.setColumn(i, "SID", list.get(i).getSid());
			dsList.setColumn(i, "MFLIFNRT", list.get(i).getMflifnrt());
			dsList.setColumn(i, "DMSTAT", list.get(i).getDmstat());
			dsList.setColumn(i, "CSLIFNRT", list.get(i).getCslifnrt());
			
            // 마진율 표준원가 추가 21.07.01			
			dsList.setColumn(i, "MARGIN", list.get(i).getMargin());			
			dsList.setColumn(i, "ZUAMP", list.get(i).getZuamp());			
			dsList.setColumn(i, "ZCOSTP", list.get(i).getZcostp());
			
			dsList.setColumn(i, "DELVNO", list.get(i).getDelvno());
		}

		return dsList;
	}

	@RequestMapping(value="findAgentHeader")
	public View Ses0030FindAgentHeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_header");

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		try {
			dsList = agentHeaderToDataset(dsList, dsCond, paramVO);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}	    
		MipResultVO resultVO = new MipResultVO(); 	

		resultVO.addDataset(dsList);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	private Dataset agentHeaderToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {

		Ses0030ParamVO param = new Ses0030ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		param.setLang(paramVO.getVariable("G_LANG"));   // ko / en
		param.setMandt(paramVO.getVariable("G_MANDT"));   // SAP CLIENT
		param.setFrqtdat(DatasetUtility.getString(dsCond,"FR_QTDAT"));
		param.setToqtdat(DatasetUtility.getString(dsCond,"TO_QTDAT"));
		param.setFrzestdat(DatasetUtility.getString(dsCond,"FR_ZESTDAT"));
		param.setTozestdat(DatasetUtility.getString(dsCond,"TO_ZESTDAT"));
		param.setVkbur(DatasetUtility.getString(dsCond,"VKBUR"));
		param.setVkgrp(DatasetUtility.getString(dsCond,"VKGRP"));
		param.setZprgflg(DatasetUtility.getString(dsCond,"ZPRGFLG"));
		param.setKunnr(DatasetUtility.getString(dsCond,"KUNNR"));
		
		param.setMax_qtser(DatasetUtility.getString(dsCond, "MAX_QTSER"));  //2016.12.09 최종 차수 조회 여부 추가 by mj.Shin
		param.setTquot(paramVO.getVariable("TQUOT"));						//2017.02.22 가견적 구분 추가 by mj.Shin 
		param.setQtgbn(DatasetUtility.getString(dsCond, "QTGBN")); //견적구분 조회조건 추가 2021.04.21.
		
		List<Ses0030> list = service.searchHeader(param);
		dsList.deleteAll();
		
		for (int i = 0 ; i < list.size() ; i++) {

			dsList.appendRow();
			dsList.setColumn(i, "MANDT"            , list.get(i).getMandt());
			dsList.setColumn(i, "QTNUM"            , list.get(i).getQtnum());
			dsList.setColumn(i, "QTSER"              , list.get(i).getQtser()); 
			dsList.setColumn(i, "QTDAT"             , list.get(i).getQtdat());
			dsList.setColumn(i, "SPART"              , list.get(i).getSpart());
			dsList.setColumn(i, "QTGBN"             , list.get(i).getQtgbn());
			dsList.setColumn(i, "ZBDTYP"             , list.get(i).getZbdtyp());
			dsList.setColumn(i, "ZPRGFLG"            , list.get(i).getZprgflg());
			dsList.setColumn(i, "VKBUR"              , list.get(i).getVkbur());
			dsList.setColumn(i, "VKGRP"              , list.get(i).getVkgrp());
			dsList.setColumn(i, "ZKUNNR"           , list.get(i).getZkunnr());
			dsList.setColumn(i, "ZAGNT"             , list.get(i).getZagnt());
			dsList.setColumn(i, "KUNNR"             , list.get(i).getKunnr());
			dsList.setColumn(i, "ZCST"                , list.get(i).getZcst());
			dsList.setColumn(i, "ZGNM"              , list.get(i).getZgnm());
			dsList.setColumn(i, "ZDEV"               , list.get(i).getZdev());
			dsList.setColumn(i, "GSNAM"            , list.get(i).getGsnam());
			dsList.setColumn(i, "ZREGN"             , list.get(i).getZregn());
			dsList.setColumn(i, "ZPID"                , list.get(i).getZpid());
			dsList.setColumn(i, "ZTEL"                , list.get(i).getZtel());
			dsList.setColumn(i, "ZADDR_ZPOST"    , list.get(i).getZaddr_zpost());
			dsList.setColumn(i, "ZADDR_ADR1"     , list.get(i).getZaddr_adr1());
			dsList.setColumn(i, "ZADDR_ADR2"     , list.get(i).getZaddr_adr2());
			dsList.setColumn(i, "SOABLE"            , list.get(i).getSoable());
			dsList.setColumn(i, "ZESTDAT"          , list.get(i).getZestdat());
			dsList.setColumn(i, "DELDAT"            , list.get(i).getDeldat());
			dsList.setColumn(i, "ZDVMTS"           , list.get(i).getZdvmts());
			dsList.setColumn(i, "ZSUM_ZNUMBER", list.get(i).getZsum_znumber());
			dsList.setColumn(i, "ZSUM_ZCOST"     , list.get(i).getZsum_zcost());
			dsList.setColumn(i, "ZSUM_ZEAM"      , list.get(i).getZsum_zeam());
			dsList.setColumn(i, "ZSUM_SHANG"    , list.get(i).getZsum_shang());
			dsList.setColumn(i, "INCO"               , list.get(i).getInco());
			dsList.setColumn(i, "ZICIF_PPRT"        , list.get(i).getZicif_pprt());
			dsList.setColumn(i, "ZICIF_PPCT"        , list.get(i).getZicif_ppct());
			dsList.setColumn(i, "ZICIF_PPCD"        , list.get(i).getZicif_ppcd());
			dsList.setColumn(i, "ZICIF_1STRT"       , list.get(i).getZicif_1strt());
			dsList.setColumn(i, "ZICIF_1STCT"       , list.get(i).getZicif_1stct());
			dsList.setColumn(i, "ZICIF_1STCD"       , list.get(i).getZicif_1stcd());
			dsList.setColumn(i, "ZICIF_2STRT"       , list.get(i).getZicif_2strt());
			dsList.setColumn(i, "ZICIF_2STCT"       , list.get(i).getZicif_2stct());
			dsList.setColumn(i, "ZICIF_2STCD"       , list.get(i).getZicif_2stcd());
			dsList.setColumn(i, "ZICIF_RMRT"       , list.get(i).getZicif_rmrt());
			dsList.setColumn(i, "ZICIF_RMCT"       , list.get(i).getZicif_rmct());
			dsList.setColumn(i, "ZICIF_RMCD"       , list.get(i).getZicif_rmcd());
			dsList.setColumn(i, "SPRMK1"            , list.get(i).getSprmk1());
			dsList.setColumn(i, "SPRMK2"            , list.get(i).getSprmk2());
			dsList.setColumn(i, "SPRMK3"            , list.get(i).getSprmk3());
			dsList.setColumn(i, "SPRMK4"            , list.get(i).getSprmk4());
			dsList.setColumn(i, "SPRMK5"            , list.get(i).getSprmk5());
			dsList.setColumn(i, "SPRMK6"            , list.get(i).getSprmk6());
			dsList.setColumn(i, "SPRMK7"            , list.get(i).getSprmk7());
			dsList.setColumn(i, "SPRMK8"            , list.get(i).getSprmk8());
			dsList.setColumn(i, "SPRMK9"            , list.get(i).getSprmk9());
			dsList.setColumn(i, "SPRMK10"           , list.get(i).getSprmk10());
			dsList.setColumn(i, "ZSOFLG"             , list.get(i).getZsoflg());
			dsList.setColumn(i, "SONNR"             , list.get(i).getSonnr());
			dsList.setColumn(i, "VBELN"              , list.get(i).getVbeln());
			dsList.setColumn(i, "WAERK"             , list.get(i).getWaerk());
			dsList.setColumn(i, "AUART"             , list.get(i).getAuart());
			dsList.setColumn(i, "CO_DDVRTQ"       , list.get(i).getCo_ddvrtq());
			dsList.setColumn(i, "CO_DEPHTQ"       , list.get(i).getCo_dephtq());
			dsList.setColumn(i, "CO_DSSQ"          , list.get(i).getCo_dssq());
			dsList.setColumn(i, "CO_BCLCDTQ"     , list.get(i).getCo_bclcdtq());
			dsList.setColumn(i, "CO_CHPITQ"       , list.get(i).getCo_chpitq());
			dsList.setColumn(i, "CO_ASPC"          , list.get(i).getCo_aspc());
			dsList.setColumn(i, "CO_DSV1"          , list.get(i).getCo_dsv1());
			dsList.setColumn(i, "CO_DSV1TQ"       , list.get(i).getCo_dsv1tq());
			dsList.setColumn(i, "CO_DSV2"          , list.get(i).getCo_dsv2());
			dsList.setColumn(i, "CO_DSV2TQ"       , list.get(i).getCo_dsv2tq());
			dsList.setColumn(i, "CDATE"              , list.get(i).getCdate());
			dsList.setColumn(i, "CTIME"              , list.get(i).getCtime());
			dsList.setColumn(i, "CUSER"              , list.get(i).getCuser());
			dsList.setColumn(i, "UDATE"              , list.get(i).getUdate());
			dsList.setColumn(i, "UTIME"              , list.get(i).getUtime());
			dsList.setColumn(i, "UUSER"              , list.get(i).getUuser());
			dsList.setColumn(i, "DDATE"              , list.get(i).getDdate());
			dsList.setColumn(i, "DTIME"              , list.get(i).getDtime());
			dsList.setColumn(i, "DUSER"              , list.get(i).getDuser());
			dsList.setColumn(i, "ZLZONE"            , list.get(i).getZlzone());
			dsList.setColumn(i, "REGION"            , list.get(i).getRegion());
			dsList.setColumn(i, "BUYR_NM"          , list.get(i).getBuyr_nm());
			dsList.setColumn(i, "ZKUNNR_NM"      , list.get(i).getZkunnr_nm());
			dsList.setColumn(i, "ZAGNT_NM"        , list.get(i).getZagnt_nm());
			dsList.setColumn(i, "ZEAM"            , list.get(i).getZeam());
			dsList.setColumn(i, "ZATTPATH"        , list.get(i).getZattpath());
			dsList.setColumn(i, "ZATTFN"        , list.get(i).getZattfn());
			dsList.setColumn(i, "ZATTFN_OR"        , list.get(i).getZattfn_or());
		}

		return dsList;
	}

	@RequestMapping(value="findAgentDetail")
	public View Ses0030FindAgentDetailView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_detail");  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		try
		{
			dsList = detailToDataset(dsList, dsCond, paramVO);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}	    
		MipResultVO resultVO = new MipResultVO();

		resultVO.addDataset(dsList);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);

		model.addAttribute("resultVO", resultVO);

		return view;
	}
}
