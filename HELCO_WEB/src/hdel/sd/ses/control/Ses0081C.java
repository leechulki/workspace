package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0081;
import hdel.sd.ses.domain.Ses0081ParamVO;
import hdel.sd.ses.service.Ses0081S;

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
@RequestMapping("ses0081")
public class Ses0081C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0081S service;

	@RequestMapping(value="query")
	public View Ses0081DtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond   = paramVO.getDataset("ds_cond");
		Dataset dsList	 = paramVO.getDataset("ds_list");	// UI로 return할 out dataset  
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		try {
			dsList = detailToDataset(dsList, dsCond, paramVO);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			ds_error.setId("ds_error");
		}
		MipResultVO resultVO = new MipResultVO();	// 출력 dataset을 return
		resultVO.addDataset(dsList);
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	private Dataset detailToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {

		Ses0081ParamVO param = new Ses0081ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		param.setMandt(paramVO.getVariable("G_MANDT"));   // SAP CLIENT

		param.setFrzrqdat(DatasetUtility.getString(dsCond,"FCDT"));
		param.setTozrqdat(DatasetUtility.getString(dsCond,"TCDT"));
		param.setVbgrp(DatasetUtility.getString(dsCond,"VKGRP"));
		param.setZprgflg(DatasetUtility.getString(dsCond,"PST"));
		param.setSpart(DatasetUtility.getString(dsCond,"GBN"));
		param.setOrder1(DatasetUtility.getString(dsCond,"ORD1"));
		param.setOrder2(DatasetUtility.getString(dsCond,"ORD2"));
		param.setZapflg(DatasetUtility.getString(dsCond,"JRT"));
		param.setZagnt(DatasetUtility.getString(dsCond,"ZAGNT"));
		param.setZkunnr(DatasetUtility.getString(dsCond,"USER"));
		param.setGno(DatasetUtility.getString(dsCond,"GNO"));
		param.setQtgbn(DatasetUtility.getString(dsCond,"QTGBN"));	//견적구분 조회조건 추가[2013.05.31/김은영]	
		param.setQtnum(DatasetUtility.getString(dsCond,"QTNUM"));	//견적구분 조회조건 추가[2017.01.09/강동훈]
		
		if ("B1".equals(DatasetUtility.getString(dsCond,"ARA"))){  //서울/경기는 코드를 변환해줘야 함 
			param.setZregn("E5");	
		}else{
			param.setZregn(DatasetUtility.getString(dsCond,"ARA"));
		}

		List<Ses0081> list = service.searchDetail(param);  	// 서비스CALL
		dsList.deleteAll();
		
		for (int i = 0; i < list.size(); i++) { // 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"    , list.get(i).getMandt());
			dsList.setColumn(i, "QTNUM"    , list.get(i).getQtnum());
			dsList.setColumn(i, "QTSER"      , list.get(i).getQtser()); 
			dsList.setColumn(i, "SPART"      , list.get(i).getSpart()); 
			dsList.setColumn(i, "ZDEV"   , list.get(i).getZdev()); 
			dsList.setColumn(i, "ZGNM"    , list.get(i).getZgnm()); 
			dsList.setColumn(i, "GSNAM"        , list.get(i).getGsnam()); 
			dsList.setColumn(i, "ZNUMBER"      , list.get(i).getZnumber()); 
			dsList.setColumn(i, "DELDAT"      , list.get(i).getDeldat()); 
			dsList.setColumn(i, "ZADDR_ADR1"      , list.get(i).getZaddr_adr1()); 
			dsList.setColumn(i, "ZPID", list.get(i).getZpid());
			dsList.setColumn(i, "ZTEL", list.get(i).getZtel());
			dsList.setColumn(i, "ZICIF_PPCT"       , list.get(i).getZicif_ppct());
			dsList.setColumn(i, "ZICIF_PPRT"      , list.get(i).getZicif_pprt());
			dsList.setColumn(i, "ZICIF_1STCT"       , list.get(i).getZicif_1stct());
			dsList.setColumn(i, "ZICIF_1STRT"     , list.get(i).getZicif_1strt());
			dsList.setColumn(i, "ZICIF_2STCT"     , list.get(i).getZicif_2stct());
			dsList.setColumn(i, "ZICIF_2STRT"      , list.get(i).getZicif_2strt());
			dsList.setColumn(i, "ZICIF_RMCT"    , list.get(i).getZicif_rmct());
			dsList.setColumn(i, "ZICIF_RMRT"      , list.get(i).getZicif_rmrt());
			dsList.setColumn(i, "KUNNR"        , list.get(i).getKunnr());
			dsList.setColumn(i, "KUNNR_NM"        , list.get(i).getKunnr_nm());
			dsList.setColumn(i, "ZAGNT"        , list.get(i).getZagnt());
			dsList.setColumn(i, "ZAGNT_NM"        , list.get(i).getZagnt_nm());
			dsList.setColumn(i, "ZPRGFLG"      , list.get(i).getZprgflg());
			dsList.setColumn(i, "MAXQTSER_ZPRGFLG",    list.get(i).getMaxqtser_zprgflg()); // 최종차수 진행상태 추가 2021.06.15.
			dsList.setColumn(i, "ZRQCN"		   , list.get(i).getZrqcn());
			dsList.setColumn(i, "ZRQDAT"      , list.get(i).getZrqdat()); 
			dsList.setColumn(i, "ZAPFLG"      , list.get(i).getZapflg());
			dsList.setColumn(i, "ZAPDAT"      , list.get(i).getZapdat());
			dsList.setColumn(i, "ZRTRSN"      , list.get(i).getZrtrsn());
			dsList.setColumn(i, "UUSER"     , list.get(i).getUuser());
			dsList.setColumn(i, "ZKUNNR_NM"        , list.get(i).getZkunnr_nm());
			dsList.setColumn(i, "QTGBN"        , list.get(i).getQtgbn());
			//dsList.setColumn(i, "PRD"        , list.get(i).getPrd());
			dsList.setColumn(i, "VKBUR"        , list.get(i).getVkbur());
			dsList.setColumn(i, "FILECNT"        , list.get(i).getFilecnt());
			//dsList.setColumn(i, "SID"        , list.get(i).getSid());
		}
				
		return dsList;
	}
}
