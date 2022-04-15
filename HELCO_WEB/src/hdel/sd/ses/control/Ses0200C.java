package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0200;
import hdel.sd.ses.domain.Ses0200ParamVO;
import hdel.sd.ses.service.Ses0200S;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0200")
public class Ses0200C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0200S service;

	@RequestMapping(value="detail")
	public View Ses0200DtlView(MipParameterVO paramVO, Model model) {

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
		Logger logger = Logger.getLogger(this.getClass());

		Ses0200ParamVO param = new Ses0200ParamVO();
		String indelvno =  DatasetUtility.getString(dsCond,  "EL_DELVNO"	);
		if(indelvno == null) indelvno = ""; 
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		param.setMandt    (DatasetUtility.getString(dsCond, "MANDT"     ));   // SAP CLIENT
		param.setFrqtdat  (DatasetUtility.getString(dsCond, "FR_QTDAT"  ));
		param.setToqtdat  (DatasetUtility.getString(dsCond, "TO_QTDAT"  ));
		param.setFrzestdat(DatasetUtility.getString(dsCond, "FR_ZESTDAT"));
		param.setTozestdat(DatasetUtility.getString(dsCond, "TO_ZESTDAT"));
		param.setVkbur    (DatasetUtility.getString(dsCond, "VKBUR"     ));
		param.setVkgrp    (DatasetUtility.getString(dsCond, "VKGRP"     ));
		param.setZprgflg  (DatasetUtility.getString(dsCond, "ZPRGFLG"   ));
		param.setZkunnr   (DatasetUtility.getString(dsCond, "SMAN"      ));
		param.setQtnum    (DatasetUtility.getString(dsCond, "QTNUM"     ));
		param.setQtser    (DatasetUtility.getString(dsCond, "QTSER"     ));
		param.setGsnam    (DatasetUtility.getString(dsCond, "GSNAM"     ));
		// 조회조건 운송지역 추가 2020-07-27 by 김은하
		param.setRegion	  (DatasetUtility.getString(dsCond,  "REGION"	));
		// 승강기번호, 최초 견적일자 추가 21.08.03
		param.setEldelvno(indelvno);
		
		// 최초 견적일자 기간 조회 조건 추가 2021.08.24.
		param.setFrfcdate(DatasetUtility.getString(dsCond,  "FR_FCDATE"	));
		param.setTofcdate(DatasetUtility.getString(dsCond,  "TO_FCDATE"	));
		
		logger.info("@@@@@@" + param.getRegion());
//		String mandt = DatasetUtility.getString(dsCond, "MANDT");
//		String qtnum = DatasetUtility.getString(dsCond, "QTNUM");
//		String qtser   = DatasetUtility.getString(dsCond, "QTSER"); 

//		if (mandt == null) mandt = "";
//		if (qtnum == null) qtnum = "";
//		if (qtser   == null) qtser   = "0";
		
//		param.setMandt(mandt);	// SAP CLIENT
//		param.setQtnum(qtnum);
//		param.setQtser  (qtser  );

/*		// INPUT PARAM INFO --------------------------------------------------------------------------------------------    		
		System.out.print("\n@@@ dsCond.MANDT ===>"+DatasetUtility.getString(dsCond,"MANDT")+"\n");
		System.out.print("\n@@@ dsCond.QTNUM ===>"+DatasetUtility.getString(dsCond,"QTNUM")+"\n");
		System.out.print("\n@@@ dsCond.QTSER	 ===>"+DatasetUtility.getString(dsCond,"QTSER")	+"\n");*/
		
		List<Ses0200> list = service.searchDetail(param);  	// 서비스CALL
		dsList.deleteAll();
		
		int iRow = 0;
		for (int i=0;i<list.size();i++) { // 호출결과(list)를 데이타셋(dsList)에 복사
            if("".equals(indelvno)) {
            	iRow = dsList.appendRow(); 	// 행추가
				dsList.setColumn(iRow, "MANDT"      , list.get(i).getMandt());
				dsList.setColumn(iRow, "QTNUM"      , list.get(i).getQtnum());
				dsList.setColumn(iRow, "QTSER"      , list.get(i).getQtser()); 
				dsList.setColumn(iRow, "QTSEQ"      , list.get(i).getQtseq()); 
				dsList.setColumn(iRow, "SHANGH"     , list.get(i).getShangh()); 
				dsList.setColumn(iRow, "MATNR"      , list.get(i).getMatnr()); 
				dsList.setColumn(iRow, "SPEC"       , list.get(i).getSpec()); 
				dsList.setColumn(iRow, "GTYPE"      , list.get(i).getGtype()); 
				dsList.setColumn(iRow, "TYPE1"      , list.get(i).getType1()); 
				dsList.setColumn(iRow, "ZACAPA"	 , list.get(i).getZacapa());
				dsList.setColumn(iRow, "TYPE2"      , list.get(i).getType2()); 
				dsList.setColumn(iRow, "TYPE3"      , list.get(i).getType3()); 
				dsList.setColumn(iRow, "ZNUMBER"    , list.get(i).getZnumber());
				dsList.setColumn(iRow, "ZUSE"       , list.get(i).getZuse());
				dsList.setColumn(iRow, "ZOPN"       , list.get(i).getZopn());
				dsList.setColumn(iRow, "ZLEN"       , list.get(i).getZlen());
				dsList.setColumn(iRow, "ZUAM"       , list.get(i).getZuam());
				dsList.setColumn(iRow, "ZCOST"      , list.get(i).getZcost());
				dsList.setColumn(iRow, "ZEAM"       , list.get(i).getZeam());
				dsList.setColumn(iRow, "SHANG"      , list.get(i).getShang());
				dsList.setColumn(iRow, "ZRMK"       , list.get(i).getZrmk());
				dsList.setColumn(iRow, "CDATE"      , list.get(i).getCdate());
				dsList.setColumn(iRow, "CTIME"      , list.get(i).getCtime());
				dsList.setColumn(iRow, "CUSER"      , list.get(i).getCuser());
				dsList.setColumn(iRow, "UDATE"      , list.get(i).getUdate());
				dsList.setColumn(iRow, "UTIME"      , list.get(i).getUtime());
				dsList.setColumn(iRow, "UUSER"      , list.get(i).getUuser());
				dsList.setColumn(iRow, "ZESTDAT"    , list.get(i).getZestdat());
				dsList.setColumn(iRow, "NAME1"      , list.get(i).getName1());
				dsList.setColumn(iRow, "GSNAM"      , list.get(i).getGsnam());
				//----< 2017.01.16 List field 추가 by mj.Shin Start
				dsList.setColumn(iRow, "QTDAT"      , list.get(i).getQtdat());
				dsList.setColumn(iRow, "VBELN"      , list.get(i).getVbeln());
				dsList.setColumn(iRow, "TYPE4"      , list.get(i).getType4());
				dsList.setColumn(iRow, "QTGBN"      , list.get(i).getQtgbn());
				//----> 2017.01.16 List field 추가 by mj.Shin Start
				dsList.setColumn(iRow, "ZPRGFLG"    , list.get(i).getZprgflg());
				dsList.setColumn(iRow, "ZDEV"      , list.get(i).getZdev());
				dsList.setColumn(iRow, "ZGNM"      , list.get(i).getZgnm());
				dsList.setColumn(iRow, "REGION"	, list.get(i).getRegion()); // 2020.07.27 List field 추가 by eunha
				dsList.setColumn(iRow, "DSETUP", list.get(i).getDsetup());
				
				// 승강기번호, 최초 견적일자 추가 21.08.03
				dsList.setColumn(iRow, "EL_DELVNO", list.get(i).getEldelvno());
				dsList.setColumn(iRow, "FCDATE", list.get(i).getFcdate());
				
				// 부서, 팀 필드 추가 2021.08.24.
				dsList.setColumn(iRow, "VKBUR", list.get(i).getVkbur());
				dsList.setColumn(iRow, "VKGRP", list.get(i).getVkgrp());
            } else {
            	String outdelvno = list.get(i).getEldelvno();
            	// 승강기번호 조회 조건을 쿼리에 추가 시 속도 문제가 발행하여 서비스 로직으로 대체 21.08.10 - 박수근
            	if(outdelvno != null) {
                	if( outdelvno.indexOf(indelvno) > -1) {
                		iRow = dsList.appendRow(); 	// 행추가
        				dsList.setColumn(iRow, "MANDT"      , list.get(i).getMandt());
        				dsList.setColumn(iRow, "QTNUM"      , list.get(i).getQtnum());
        				dsList.setColumn(iRow, "QTSER"      , list.get(i).getQtser()); 
        				dsList.setColumn(iRow, "QTSEQ"      , list.get(i).getQtseq()); 
        				dsList.setColumn(iRow, "SHANGH"     , list.get(i).getShangh()); 
        				dsList.setColumn(iRow, "MATNR"      , list.get(i).getMatnr()); 
        				dsList.setColumn(iRow, "SPEC"       , list.get(i).getSpec()); 
        				dsList.setColumn(iRow, "GTYPE"      , list.get(i).getGtype()); 
        				dsList.setColumn(iRow, "TYPE1"      , list.get(i).getType1()); 
        				dsList.setColumn(iRow, "ZACAPA"	 , list.get(i).getZacapa());
        				dsList.setColumn(iRow, "TYPE2"      , list.get(i).getType2()); 
        				dsList.setColumn(iRow, "TYPE3"      , list.get(i).getType3()); 
        				dsList.setColumn(iRow, "ZNUMBER"    , list.get(i).getZnumber());
        				dsList.setColumn(iRow, "ZUSE"       , list.get(i).getZuse());
        				dsList.setColumn(iRow, "ZOPN"       , list.get(i).getZopn());
        				dsList.setColumn(iRow, "ZLEN"       , list.get(i).getZlen());
        				dsList.setColumn(iRow, "ZUAM"       , list.get(i).getZuam());
        				dsList.setColumn(iRow, "ZCOST"      , list.get(i).getZcost());
        				dsList.setColumn(iRow, "ZEAM"       , list.get(i).getZeam());
        				dsList.setColumn(iRow, "SHANG"      , list.get(i).getShang());
        				dsList.setColumn(iRow, "ZRMK"       , list.get(i).getZrmk());
        				dsList.setColumn(iRow, "CDATE"      , list.get(i).getCdate());
        				dsList.setColumn(iRow, "CTIME"      , list.get(i).getCtime());
        				dsList.setColumn(iRow, "CUSER"      , list.get(i).getCuser());
        				dsList.setColumn(iRow, "UDATE"      , list.get(i).getUdate());
        				dsList.setColumn(iRow, "UTIME"      , list.get(i).getUtime());
        				dsList.setColumn(iRow, "UUSER"      , list.get(i).getUuser());
        				dsList.setColumn(iRow, "ZESTDAT"    , list.get(i).getZestdat());
        				dsList.setColumn(iRow, "NAME1"      , list.get(i).getName1());
        				dsList.setColumn(iRow, "GSNAM"      , list.get(i).getGsnam());
        				//----< 2017.01.16 List field 추가 by mj.Shin Start
        				dsList.setColumn(iRow, "QTDAT"      , list.get(i).getQtdat());
        				dsList.setColumn(iRow, "VBELN"      , list.get(i).getVbeln());
        				dsList.setColumn(iRow, "TYPE4"      , list.get(i).getType4());
        				dsList.setColumn(iRow, "QTGBN"      , list.get(i).getQtgbn());
        				//----> 2017.01.16 List field 추가 by mj.Shin Start
        				dsList.setColumn(iRow, "ZPRGFLG"    , list.get(i).getZprgflg());
        				dsList.setColumn(iRow, "ZDEV"      , list.get(i).getZdev());
        				dsList.setColumn(iRow, "ZGNM"      , list.get(i).getZgnm());
        				dsList.setColumn(iRow, "REGION"	, list.get(i).getRegion()); // 2020.07.27 List field 추가 by eunha
        				dsList.setColumn(iRow, "DSETUP", list.get(i).getDsetup());
        				
        				// 승강기번호, 최초 견적일자 추가 21.08.03
        				dsList.setColumn(iRow, "EL_DELVNO", list.get(i).getEldelvno());
        				dsList.setColumn(iRow, "FCDATE", list.get(i).getFcdate());            		
        				
        				// 부서, 팀 필드 추가 2021.08.24.
        				dsList.setColumn(iRow, "VKBUR", list.get(i).getVkbur());
        				dsList.setColumn(iRow, "VKGRP", list.get(i).getVkgrp());
        				
                	}            		
            	}
            }
		}

		return dsList;
	}
}
