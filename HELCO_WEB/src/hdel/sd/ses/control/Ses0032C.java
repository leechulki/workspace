package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0032;
import hdel.sd.ses.domain.Ses0032ParamVO;
import hdel.sd.ses.service.Ses0032S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0032")
public class Ses0032C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0032S service;
	
	@RequestMapping(value="search")
	public View Ses0032HeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list");	// UI로 return할 out dataset  

		dsList = listToDataset(dsList, dsCond, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO();	// 출력 dataset을 return
			resultVO.addDataset(dsList);
//			System.out.println("============== ds_list.getRowCount() " + dsList.getRowCount());
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}

		return view;
	}

	private Dataset listToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {
		Ses0032ParamVO param = new Ses0032ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		String lang  = paramVO.getVariable("G_LANG"); 
		String mandt = DatasetUtility.getString(dsCond,"MANDT");
		String zpym   = DatasetUtility.getString(dsCond,"ZPYM");
		String zkunnr = DatasetUtility.getString(dsCond,"ZKUNNR");
		String spart   = DatasetUtility.getString(dsCond,"SPART");
		String zagnt  = DatasetUtility.getString(dsCond,"ZAGNT");
		String kunnr = DatasetUtility.getString(dsCond,"KUNNR");
		String sonnr  = DatasetUtility.getString(dsCond,"SONNR");

		if (mandt == null) mandt = paramVO.getVariable("G_MANDT");
		if (zpym   == null) zpym = "";
		if (zkunnr == null) zkunnr = "";
		if (spart   == null) spart = "";
		if (zagnt  == null) zagnt = "";
		if (kunnr  == null) kunnr = "";
		if (sonnr  == null) sonnr = "";
		
		param.setLang  (lang);    // ko or en
		param.setMandt (mandt);   // SAP CLIENT
		param.setZpym  (zpym);
		param.setZkunnr(zkunnr);
		param.setSpart  (spart);
		param.setZagnt (zagnt);
		param.setKunnr (kunnr);
		param.setSonnr (sonnr);

/*		// INPUT PARAM INFO --------------------------------------------------------------------------------------------
		System.out.print("\n@@@ dsCond.MANDT ===>"+DatasetUtility.getString(dsCond,"MANDT")+"\n");
		System.out.print("\n@@@ dsCond.ZPYM    ===>"+DatasetUtility.getString(dsCond,"ZPYM")	+"\n");
		System.out.print("\n@@@ dsCond.ZKUNNR ===>"+DatasetUtility.getString(dsCond,"ZKUNNR")	+"\n");
		System.out.print("\n@@@ dsCond.SPART   ===>"+DatasetUtility.getString(dsCond,"SPART")	+"\n");
		System.out.print("\n@@@ dsCond.SONNR  ===>"+DatasetUtility.getString(dsCond,"SONNR")	+"\n"); */

		List<Ses0032> list = service.search(param);		// 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"    , list.get(i).getMandt());
			dsList.setColumn(i, "SONNR"     , list.get(i).getSonnr());
			dsList.setColumn(i, "ZPYM"       , list.get(i).getZpym());
			dsList.setColumn(i, "SPART"      , list.get(i).getSpart());
			dsList.setColumn(i, "AUART"     , list.get(i).getAuart());
			dsList.setColumn(i, "MATNR"    , list.get(i).getMatnr());
			dsList.setColumn(i, "VKBUR"     , list.get(i).getVkbur());
			dsList.setColumn(i, "VKGRP"     , list.get(i).getVkgrp());
			dsList.setColumn(i, "ZKUNNR"   , list.get(i).getZkunnr());
			dsList.setColumn(i, "ZAGNT"     , list.get(i).getZagnt());
			dsList.setColumn(i, "GTYPE"      , list.get(i).getGtype());
			dsList.setColumn(i, "RTYPE"      , list.get(i).getRtype());
			dsList.setColumn(i, "TYPE1"      , list.get(i).getType1());
			dsList.setColumn(i, "TYPE2"      , list.get(i).getType2());
			dsList.setColumn(i, "TYPE3"      , list.get(i).getType3());
			dsList.setColumn(i, "ZNUMBER", list.get(i).getZnumber());
			dsList.setColumn(i, "KUNNR"    , list.get(i).getKunnr());
			dsList.setColumn(i, "GSNAM"    , list.get(i).getGsnam());
			dsList.setColumn(i, "REGION"    , list.get(i).getRegion());
			dsList.setColumn(i, "ZDELI"       , list.get(i).getZdeli());
			dsList.setColumn(i, "SHANGH"   , list.get(i).getShangh());
			dsList.setColumn(i, "ZINTER"     , list.get(i).getZinter());
			dsList.setColumn(i, "SOFOCA"    , list.get(i).getSofoca());
			dsList.setColumn(i, "ZFORE"      , list.get(i).getZfore());
			dsList.setColumn(i, "WAERK"     , list.get(i).getWaerk());
			dsList.setColumn(i, "DELDAT"    , list.get(i).getDeldat());
			dsList.setColumn(i, "ZRMK"      , list.get(i).getZrmk());
			dsList.setColumn(i, "SOABLE"    , list.get(i).getSoable());
			dsList.setColumn(i, "SORLT"      , list.get(i).getSorlt());
			dsList.setColumn(i, "SOPRICE"   , list.get(i).getSoprice());
			dsList.setColumn(i, "SHANG"     , list.get(i).getShang());
			dsList.setColumn(i, "ZMPFLG"    , list.get(i).getZmpflg());
			dsList.setColumn(i, "VBELN"      , list.get(i).getVbeln());
			dsList.setColumn(i, "ZBPNNR"    , list.get(i).getZbpnnr());
			dsList.setColumn(i, "CO_DDVRTQ" , "");
			dsList.setColumn(i, "CO_DEPHTQ" , "");
			dsList.setColumn(i, "CO_DSSQ"    , "");
			dsList.setColumn(i, "CO_BCLCDTQ", "");
			dsList.setColumn(i, "CO_CHPITQ"  , "");
			dsList.setColumn(i, "CO_ASPC"     , "");
			dsList.setColumn(i, "CO_DSV1"     , "");
			dsList.setColumn(i, "CO_DSV1TQ" , "");
			dsList.setColumn(i, "CO_DSV2"    , "");
			dsList.setColumn(i, "CO_DSV2TQ" , "");
			dsList.setColumn(i, "CDATE"      , list.get(i).getCdate());
			dsList.setColumn(i, "CTIME"      , list.get(i).getCtime());
			dsList.setColumn(i, "CUSER"      , list.get(i).getCuser());
			dsList.setColumn(i, "UDATE"      , list.get(i).getUdate());
			dsList.setColumn(i, "UTIME"      , list.get(i).getUtime());
			dsList.setColumn(i, "UUSER"      , list.get(i).getUuser());
			dsList.setColumn(i, "DDATE"      , list.get(i).getDdate());
			dsList.setColumn(i, "DTIME"       , list.get(i).getDtime());
			dsList.setColumn(i, "DUSER"       , list.get(i).getDuser());
			dsList.setColumn(i, "BUYR_NM"   , list.get(i).getBuyr_nm());
			dsList.setColumn(i, "ZKUNNR_NM", list.get(i).getZkunnr_nm());
			dsList.setColumn(i, "ZAGNT_NM"  , list.get(i).getZagnt_nm());
			dsList.setColumn(i, "ZEAM"         , "");
			dsList.setColumn(i, "ZLZONE"      , "");
			dsList.setColumn(i, "REGION"      , "");
			dsList.setColumn(i, "ZPRGFLG"     , "");
			dsList.setColumn(i, "INCO2"        , "");
			dsList.setColumn(i, "CONQTY20"        , list.get(i).getConqty20());
			dsList.setColumn(i, "CONQTY40"        , list.get(i).getConqty40());
			dsList.setColumn(i, "OPENDT"        , list.get(i).getOpendt());
			dsList.setColumn(i, "SID"        , list.get(i).getSid());
		}

		return dsList;
	}
}
