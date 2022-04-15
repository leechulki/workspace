package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0290;
import hdel.sd.ses.domain.Ses0290ParamVO;
import hdel.sd.ses.service.Ses0290S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0290")
public class Ses0290C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0290S service;
	
	@RequestMapping(value="header")
	public View Ses0290HeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_header");	// UI로 return할 out dataset  
		 
		dsList = headerToDataset(dsList, dsCond, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
			resultVO.addDataset(dsList);
//			System.out.println("============== ds_header.getRowCount() " + dsList.getRowCount());
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	@RequestMapping(value="detail")
	public View Ses0290DtlView(MipParameterVO paramVO, Model model) {

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
	
	@RequestMapping(value="saveh")
	public View Ses0290SaveView(MipParameterVO paramVO, Model model) { 
 
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));


		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.saveh(paramVO, model, session);

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
	
	@RequestMapping(value="saved")
	public View Ses0290SaveView1(MipParameterVO paramVO, Model model) { 
 
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));


		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.saved(paramVO, model, session);

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

	private Dataset headerToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {

		Ses0290ParamVO param = new Ses0290ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		//20190420 lang 추가 by lyk
		String lang = 	paramVO.getVariable("G_LANG");
		String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";
		param.setLang(spras);
		
		String gtype    = DatasetUtility.getString(dsCond,"GTYPE"     );
		
		if (gtype    == null) gtype    = "";


		param.setGtype    (gtype);   // SAP CLIENT


/*		// INPUT PARAM INFO --------------------------------------------------------------------------------------------
		System.out.print("\n@@@ dsCond.MANDT       ===>"+DatasetUtility.getString(dsCond,"MANDT")      +"\n");  
		System.out.print("\n@@@ dsCond.FR_QTDAT 	===>"+DatasetUtility.getString(dsCond,"FR_QTDAT")	 +"\n");
		System.out.print("\n@@@ dsCond.TO_QTDAT	===>"+DatasetUtility.getString(dsCond,"TO_QTDAT")	 +"\n");
		System.out.print("\n@@@ dsCond.FR_ZESTDAT	===>"+DatasetUtility.getString(dsCond,"FR_ZESTDAT")+"\n");
		System.out.print("\n@@@ dsCond.TO_ZESTDAT ===>"+DatasetUtility.getString(dsCond,"TO_ZESTDAT")+"\n");
		System.out.print("\n@@@ dsCond.VKBUR        ===>"+DatasetUtility.getString(dsCond,"VKBUR")	     +"\n");
		System.out.print("\n@@@ dsCond.VKGRP        ===>"+DatasetUtility.getString(dsCond,"VKGRP")	     +"\n");
		System.out.print("\n@@@ dsCond.ZPRGFLG     ===>"+DatasetUtility.getString(dsCond,"ZPRGFLG")	     +"\n");
		System.out.print("\n@@@ dsCond.SMAN         ===>"+DatasetUtility.getString(dsCond,"SMAN")	     +"\n");
		System.out.print("\n@@@ dsCond.QTNUM      ===>"+DatasetUtility.getString(dsCond,"QTNUM")	     +"\n");
		System.out.print("\n@@@ dsCond.QTSER         ===>"+DatasetUtility.getString(dsCond,"QTSER")	     +"\n");*/
	
		List<Ses0290> list = service.searchHeader(param); // 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"            , list.get(i).getMandt());
			dsList.setColumn(i, "BLOCKNO"          , list.get(i).getBlockno());
			dsList.setColumn(i, "BLOCKNM"          , list.get(i).getBlocknm()); 
			dsList.setColumn(i, "GTYPE"            , list.get(i).getGtype());
			dsList.setColumn(i, "BLOCKGBN"         , list.get(i).getBlockgbn());
			dsList.setColumn(i, "ZPRDGBN"          , list.get(i).getZprdgbn());
			dsList.setColumn(i, "SPEC1"            , list.get(i).getSpec1());
			dsList.setColumn(i, "SPECESS1"         , list.get(i).getSpecess1());
			dsList.setColumn(i, "SPECDEFV1"        , list.get(i).getSpecdefv1());
			dsList.setColumn(i, "SPEC2"            , list.get(i).getSpec2());
			dsList.setColumn(i, "SPECESS2"         , list.get(i).getSpecess2());
			dsList.setColumn(i, "SPECDEFV2"        , list.get(i).getSpecdefv2());
			dsList.setColumn(i, "SPEC3"            , list.get(i).getSpec3());
			dsList.setColumn(i, "SPECESS3"         , list.get(i).getSpecess3());
			dsList.setColumn(i, "SPECDEFV3"        , list.get(i).getSpecdefv3());
			dsList.setColumn(i, "SPEC4"            , list.get(i).getSpec4());
			dsList.setColumn(i, "SPECESS4"         , list.get(i).getSpecess4());
			dsList.setColumn(i, "SPECDEFV4"        , list.get(i).getSpecdefv4());
			dsList.setColumn(i, "SPEC5"            , list.get(i).getSpec5());
			dsList.setColumn(i, "SPECESS5"         , list.get(i).getSpecess5());
			dsList.setColumn(i, "SPECDEFV5"        , list.get(i).getSpecdefv5());
			dsList.setColumn(i, "SPEC6"            , list.get(i).getSpec6());
			dsList.setColumn(i, "SPECESS6"         , list.get(i).getSpecess6());
			dsList.setColumn(i, "SPECDEFV6"        , list.get(i).getSpecdefv6());
			dsList.setColumn(i, "SPEC7"            , list.get(i).getSpec7());
			dsList.setColumn(i, "SPECESS7"         , list.get(i).getSpecess7());
			dsList.setColumn(i, "SPECDEFV7"        , list.get(i).getSpecdefv7());
			dsList.setColumn(i, "SPEC8"            , list.get(i).getSpec8());
			dsList.setColumn(i, "SPECESS8"         , list.get(i).getSpecess8());
			dsList.setColumn(i, "SPECDEFV8"        , list.get(i).getSpecdefv8());
			dsList.setColumn(i, "SPEC9"            , list.get(i).getSpec9());
			dsList.setColumn(i, "SPECESS9"         , list.get(i).getSpecess9());
			dsList.setColumn(i, "SPECDEFV9"        , list.get(i).getSpecdefv9());
			dsList.setColumn(i, "SPEC10"           , list.get(i).getSpec10());
			dsList.setColumn(i, "SPECESS10"        , list.get(i).getSpecess10());
			dsList.setColumn(i, "SPECDEFV10"       , list.get(i).getSpecdefv10());
			dsList.setColumn(i, "SPEC11"           , list.get(i).getSpec11());
			dsList.setColumn(i, "SPECESS11"        , list.get(i).getSpecess11());
			dsList.setColumn(i, "SPECDEFV11"       , list.get(i).getSpecdefv11());
			dsList.setColumn(i, "SPEC12"           , list.get(i).getSpec12());
			dsList.setColumn(i, "SPECESS12"        , list.get(i).getSpecess12());
			dsList.setColumn(i, "SPECDEFV12"       , list.get(i).getSpecdefv12());
			dsList.setColumn(i, "SPEC13"           , list.get(i).getSpec13());
			dsList.setColumn(i, "SPECESS13"        , list.get(i).getSpecess13());
			dsList.setColumn(i, "SPECDEFV13"       , list.get(i).getSpecdefv13());
			dsList.setColumn(i, "SPEC14"           , list.get(i).getSpec14());
			dsList.setColumn(i, "SPECESS14"        , list.get(i).getSpecess14());
			dsList.setColumn(i, "SPECDEFV14"       , list.get(i).getSpecdefv14());
			dsList.setColumn(i, "SPEC15"           , list.get(i).getSpec15());
			dsList.setColumn(i, "SPECESS15"        , list.get(i).getSpecess15());
			dsList.setColumn(i, "SPECDEFV15"       , list.get(i).getSpecdefv15());
			dsList.setColumn(i, "SPEC16"           , list.get(i).getSpec16());
			dsList.setColumn(i, "SPECESS16"        , list.get(i).getSpecess16());
			dsList.setColumn(i, "SPECDEFV16"       , list.get(i).getSpecdefv16());
			dsList.setColumn(i, "SPEC17"           , list.get(i).getSpec17());
			dsList.setColumn(i, "SPECESS17"        , list.get(i).getSpecess17());
			dsList.setColumn(i, "SPECDEFV17"       , list.get(i).getSpecdefv17());
			dsList.setColumn(i, "SPEC18"           , list.get(i).getSpec18());
			dsList.setColumn(i, "SPECESS18"        , list.get(i).getSpecess18());
			dsList.setColumn(i, "SPECDEFV18"       , list.get(i).getSpecdefv18());
			dsList.setColumn(i, "SPEC19"           , list.get(i).getSpec19());
			dsList.setColumn(i, "SPECESS19"        , list.get(i).getSpecess19());
			dsList.setColumn(i, "SPECDEFV19"       , list.get(i).getSpecdefv19());
			dsList.setColumn(i, "SPEC20"           , list.get(i).getSpec20());
			dsList.setColumn(i, "SPECESS20"        , list.get(i).getSpecess20());
			dsList.setColumn(i, "SPECDEFV20"       , list.get(i).getSpecdefv20());
			dsList.setColumn(i, "OUT1"             , list.get(i).getOut1());
			dsList.setColumn(i, "OUT2"             , list.get(i).getOut2());
			dsList.setColumn(i, "OUT3"             , list.get(i).getOut3());
			dsList.setColumn(i, "OUT4"             , list.get(i).getOut4());
			dsList.setColumn(i, "OUT5"             , list.get(i).getOut5());
			dsList.setColumn(i, "OUT6"             , list.get(i).getOut6());
			dsList.setColumn(i, "OUT7"             , list.get(i).getOut7());
			dsList.setColumn(i, "OUT8"             , list.get(i).getOut8());
			dsList.setColumn(i, "OUT9"             , list.get(i).getOut9());
			dsList.setColumn(i, "OUT10"            , list.get(i).getOut10());
			dsList.setColumn(i, "ZRMK"             , list.get(i).getZrmk());
		}

		return dsList;
	}

	private Dataset detailToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {

		Ses0290ParamVO param = new Ses0290ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		String mandt = DatasetUtility.getString(dsCond, "MANDT");
		String blockno = DatasetUtility.getString(dsCond, "BLOCKNO");


		if (mandt == null) mandt = "";
		if (blockno == null) blockno = "";
		
		param.setMandt(mandt);	// SAP CLIENT
		param.setBlockno(blockno);

/*		// INPUT PARAM INFO --------------------------------------------------------------------------------------------    		
		System.out.print("\n@@@ dsCond.MANDT ===>"+DatasetUtility.getString(dsCond,"MANDT")+"\n");
		System.out.print("\n@@@ dsCond.QTNUM ===>"+DatasetUtility.getString(dsCond,"QTNUM")+"\n");
		System.out.print("\n@@@ dsCond.QTSER	 ===>"+DatasetUtility.getString(dsCond,"QTSER")	+"\n");*/
		
		List<Ses0290> list = service.searchDetail(param);  	// 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) { // 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"    , list.get(i).getMandt());
			dsList.setColumn(i, "BLOCKNO"    , list.get(i).getBlockno());
			dsList.setColumn(i, "ZSEQ"      , list.get(i).getZseq()); 
			dsList.setColumn(i, "SPEC1"      , list.get(i).getSpec1()); 
			dsList.setColumn(i, "SPEC2"   , list.get(i).getSpec2()); 
			dsList.setColumn(i, "SPEC3"    , list.get(i).getSpec3()); 
			dsList.setColumn(i, "SPEC4"        , list.get(i).getSpec4()); 
			dsList.setColumn(i, "SPEC5"      , list.get(i).getSpec5()); 
			dsList.setColumn(i, "SPEC6"      , list.get(i).getSpec6()); 
			dsList.setColumn(i, "SPEC7"      , list.get(i).getSpec7()); 
			dsList.setColumn(i, "SPEC8"      , list.get(i).getSpec8()); 
			dsList.setColumn(i, "SPEC9", list.get(i).getSpec9());
			dsList.setColumn(i, "SPEC10"       , list.get(i).getSpec10());
			dsList.setColumn(i, "SPEC11"      , list.get(i).getSpec11());
			dsList.setColumn(i, "SPEC12"       , list.get(i).getSpec12());
			dsList.setColumn(i, "SPEC13"     , list.get(i).getSpec13());
			dsList.setColumn(i, "SPEC14"     , list.get(i).getSpec14());
			dsList.setColumn(i, "SPEC15"      , list.get(i).getSpec15());
			dsList.setColumn(i, "SPEC16"    , list.get(i).getSpec16());
			dsList.setColumn(i, "SPEC17"      , list.get(i).getSpec17());
			dsList.setColumn(i, "SPEC18"    , list.get(i).getSpec18());
			dsList.setColumn(i, "SPEC19"     , list.get(i).getSpec19());
			dsList.setColumn(i, "SPEC20"     , list.get(i).getSpec20());
			dsList.setColumn(i, "OUT1"     , list.get(i).getOut1());
			dsList.setColumn(i, "OUT2"     , list.get(i).getOut2());
			dsList.setColumn(i, "OUT3"     , list.get(i).getOut3());
			dsList.setColumn(i, "OUT4"     , list.get(i).getOut4());
			dsList.setColumn(i, "OUT5"     , list.get(i).getOut5());
			dsList.setColumn(i, "OUT6"     , list.get(i).getOut6());
			dsList.setColumn(i, "OUT7"     , list.get(i).getOut7());
			dsList.setColumn(i, "OUT8"     , list.get(i).getOut8());
			dsList.setColumn(i, "OUT9"     , list.get(i).getOut9());
			dsList.setColumn(i, "OUT10"     , list.get(i).getOut10());
			dsList.setColumn(i, "ZRMK"     , list.get(i).getZrmk());
		}
				
		return dsList;
	}
}
