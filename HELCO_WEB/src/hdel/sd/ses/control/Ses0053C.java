package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0053;
import hdel.sd.ses.domain.Ses0053ParamVO;
import hdel.sd.ses.service.Ses0053S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0053")
public class Ses0053C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0053S service;

	@RequestMapping(value="find")
	public View Ses0053DtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_detail");	// UI로 return할 out dataset  

		dsList = detailToDataset(dsList, dsCond, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO();	// 출력 dataset을 return
			resultVO.addDataset(dsList);
//			System.out.println("============== ds_detail.getRowCount() " + dsList.getRowCount());
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	private Dataset detailToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {

		Ses0053ParamVO param = new Ses0053ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		param.setMandt    (DatasetUtility.getString(dsCond,"MANDT"    ));   // SAP CLIENT
		param.setFrqtdat  (DatasetUtility.getString(dsCond,"FR_QTDAT" ));
		param.setToqtdat  (DatasetUtility.getString(dsCond,"TO_QTDAT" ));
		param.setVkbur    (DatasetUtility.getString(dsCond,"VKBUR"    ));
		param.setVkgrp    (DatasetUtility.getString(dsCond,"VKGRP"    ));
		param.setJtype1   (DatasetUtility.getString(dsCond,"JTYPE1"   ));
		param.setJtype4   (DatasetUtility.getString(dsCond,"JTYPE4"   ));
		param.setZipcd    (DatasetUtility.getString(dsCond,"ZIPCD"    ));


/*		// INPUT PARAM INFO --------------------------------------------------------------------------------------------    		
		System.out.print("\n@@@ dsCond.MANDT ===>"+DatasetUtility.getString(dsCond,"MANDT")+"\n");
		System.out.print("\n@@@ dsCond.QTNUM ===>"+DatasetUtility.getString(dsCond,"QTNUM")+"\n");
		System.out.print("\n@@@ dsCond.QTSER	 ===>"+DatasetUtility.getString(dsCond,"QTSER")	+"\n");*/
		
		List<Ses0053> list = service.searchDetail(param);  	// 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) { // 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"      , list.get(i).getMandt());
			dsList.setColumn(i, "QTNUM"      , list.get(i).getQtnum());
			dsList.setColumn(i, "QTSER"      , list.get(i).getQtser()); 
			dsList.setColumn(i, "CDATE"      , list.get(i).getCdate()); 
			dsList.setColumn(i, "VKBUR"      , list.get(i).getVkbur()); 
			dsList.setColumn(i, "VKGRP"      , list.get(i).getVkgrp()); 
			dsList.setColumn(i, "ZKUNNR"     , list.get(i).getZkunnr()); 
			dsList.setColumn(i, "GSNAM"      , list.get(i).getGsnam()); 
			dsList.setColumn(i, "JTYPE1"     , list.get(i).getJtype1()); 
			dsList.setColumn(i, "JTYPE2"     , list.get(i).getJtype2()); 
			dsList.setColumn(i, "JTYPE3"     , list.get(i).getJtype3());
			dsList.setColumn(i, "JTYPE4"     , list.get(i).getJtype4());
			dsList.setColumn(i, "KUNNR"      , list.get(i).getKunnr());
			dsList.setColumn(i, "ZADDR_ADR1" , list.get(i).getZaddr_adr1());
			dsList.setColumn(i, "QTGBN"      , list.get(i).getQtgbn());
			dsList.setColumn(i, "ZAGNT"      , list.get(i).getZagnt());
			dsList.setColumn(i, "TEXT"       , list.get(i).getText());
			dsList.setColumn(i, "ZTEL"       , list.get(i).getZtel());
		}
				
		return dsList;
	}
}
