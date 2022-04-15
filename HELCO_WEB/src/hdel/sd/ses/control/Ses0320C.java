package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0320;
import hdel.sd.ses.domain.Ses0320ParamVO;
import hdel.sd.ses.domain.ZSDS0016;
import hdel.sd.ses.domain.ZSDS0017;

import hdel.sd.ses.service.Ses0320S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import hdel.sd.com.domain.ZGType;
import hdel.sd.com.domain.ZGTypes;
import hdel.sd.com.domain.ZLang;

@Controller
@RequestMapping("ses0320")
public class Ses0320C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0320S service;
	
	@RequestMapping(value="findTemp")
	public View Ses0320TempFindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_temp");	// UI로 return할 out dataset  
		// 오류정보 DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return

		try { 
			dsList = templateToDataset(paramVO.getVariable("G_SYSID"), dsList, dsCond);
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}
		resultVO.addDataset(dsList);

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	
	@RequestMapping(value="find")
	public View Ses0320FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_temp");	// UI로 return할 out dataset 
		
		// 오류정보 DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();	// 출력 dataset을 return
		try {
			dsList = listToDataset(paramVO.getVariable("G_SYSID"), dsList, dsCond);
			
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}	    
		resultVO.addDataset(dsList);

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);

		model.addAttribute("resultVO", resultVO);

		return view;
	}	

	@RequestMapping(value="findQt")  //견적의 사양을 fetch 
	public View Ses0320FindQtView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_sap");	// UI로 return할 out dataset 

		// 오류정보 DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
		
		try { 
			dsList = qtlistToDataset(paramVO, dsList, dsCond);
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}
		resultVO.addDataset(dsList);
		
//		System.out.print("\n@@@ dsList Row Count ==>"+dsList.getRowCount()   +"\n");
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	
	@RequestMapping(value="receive")
	public View Ses0320Receive(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0016[] 	list 	= new ZSDS0016[]{};  // 호기 SPEC 내역 현재및 전송값 output list
		ZSDS0017[] 	list2 	= new ZSDS0017[]{};  // RFC 호기 SPEC 내역에 대한 HEADER RFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset ds 		 = null;				// UI로 return할 out dataset
		Dataset ds2 	 = null;				// UI로 return할 out dataset2
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
//		System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	        +"\n");  
//		System.out.print("\n@@@ dsCond.HOGI	===>"+DatasetUtility.getString(dsCond,"HOGI")	+"\n");
		//--------------------------------------------------------------------------------------------
		 
		try { 
			// WFC INPUT SET
			Object obj[] = new Object[]{
					  list2
					, DatasetUtility.getString(dsCond, "HOGI")
					, listMsg
					, list
			}; 

			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ses.domain.ZWEB_SD_HOGI_SPEC", obj, false);

			// RFC 출력구조체로 out dataset 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0016.getDataset();
			ds2 = CallSAP.isJCO() ? new Dataset() : ZSDS0017.getDataset();

			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			if ( "4".equals(stub.getOutput("E_TYPE").toString()) ) {	// 실패
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
						, listMsg[0].getIDX().toString()
						, listMsg[0].getERRMSG().toString()
						, "Y"
						, "Y");
			} else {
				// // RFC 호출결과를 out dataset에 옮기기  
				CallSAP.moveObj2Ds(ds,  stub.getOutput("T_ITAB"));
				CallSAP.moveObj2Ds(ds2, stub.getOutput("H_ITAB"));
			}
		} catch (CommRfcException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");
		}	 
		
		// 데이터건수 INFO
//		System.out.print("\n@@@ ds_list.getRowCount ===>" + ds.getRowCount() + "\n");
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds.setId("ds_list");
		resultVO.addDataset(ds);

		ds2.setId("ds_list2");
		resultVO.addDataset(ds2);

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);

		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}	
	
	@RequestMapping(value="save")
	public View Ses0320HeaderSave(MipParameterVO paramVO, Model model) {

		Dataset dsSave   = paramVO.getDataset("ds_save" );
		Dataset dsHeader = paramVO.getDataset("ds_head");
		// 오류정보 DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return

		try {
			dsHeader = service.save(paramVO, model, session);
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}
		resultVO.addDataset(dsSave);

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);

		model.addAttribute("resultVO", resultVO);

		return view;
	}

	private Dataset listToDataset(String sysid, Dataset dsList, Dataset dsCond) {
		Ses0320ParamVO param = new Ses0320ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(sysid)); // DAO생성

		//param.setMandt(paramVO.getVariable("G_MANDT"));	
		String mandt = DatasetUtility.getString(dsCond,"MANDT" );
		String ztplno = DatasetUtility.getString(dsCond,"ZTPLNO" );
		String zprdgbn = DatasetUtility.getString(dsCond,"ZPRDGBN");
		String ztplgbn = DatasetUtility.getString(dsCond,"ZTPLGBN");
		String zrmk = DatasetUtility.getString(dsCond,"ZRMK");
		String spras = ZLang.convSapLang(DatasetUtility.getString(dsCond,"SPRAS"));
		
		// 2020 브랜드
		String brndcd = DatasetUtility.getString(dsCond,"BRNDCD");
		
		if (mandt  == null) mandt  = "";
		if (ztplgbn == null) ztplgbn = "";
		if (zrmk    == null) zrmk    = "";
		if (zprdgbn  == null) zprdgbn  = "";
		if (spras  == null) spras  = "3";

//		param.setZtplgbn(DatasetUtility.getString(dsCond, 0, "ZTPLGBN"));
//		param.setZrmk(DatasetUtility.getString(dsCond, 0, "ZRMK"));

		param.setMandt(mandt);
		param.setZprdgbn(zprdgbn);
		param.setZtplgbn(ztplgbn);
		param.setZrmk(zrmk);
		param.setSpras(spras);

		// 2020 브랜드
		if(brndcd == null) {
			brndcd = "NOBRND";
		} else {
			if("".equals(brndcd)) {
				brndcd = "NOBRND";
		    }
		}
		
		if (ztplno == null) {

			List<Ses0320> minList = service.searchMinTemplate(param);
			ztplno = minList.get(0).getZtplno().toString();
		}

		param.setZtplno(ztplno);

		// 2020 브랜드
		param.setBrndcd(brndcd);
		
		List<Ses0320> list = service.find(param); // 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MCLASS"  , list.get(i).getMclass());
			dsList.setColumn(i, "ATKLA"    , list.get(i).getAtkla());
			dsList.setColumn(i, "PRH"       , list.get(i).getPrh());
			dsList.setColumn(i, "PRD"       , list.get(i).getPrd());
			dsList.setColumn(i, "PRHNAME", list.get(i).getPrhname());
			dsList.setColumn(i, "ZTPLSEQ"  , list.get(i).getZtplseq());
			dsList.setColumn(i, "CNT"  , list.get(i).getCnt());
			dsList.setColumn(i, "ATFOR", list.get(i).getAtfor()); // 2013.03.07 추가
			
			
			// 2020 브랜드
			dsList.setColumn(i, "PCNCD"  , list.get(i).getPcncd());
			dsList.setColumn(i, "BRNDCD"  , list.get(i).getBrndcd());
			dsList.setColumn(i, "BRNDSEQ"  , list.get(i).getBrndseq());
			dsList.setColumn(i, "DISPTP"  , list.get(i).getDisptp());
			dsList.setColumn(i, "MODITP"  , list.get(i).getModitp());
		}

		return dsList;
	}	

	private Dataset templateToDataset(String sysid, Dataset dsList, Dataset dsCond) {

		Ses0320ParamVO param = new Ses0320ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(sysid)); // DAO생성

		param.setMandt(DatasetUtility.getString(dsCond,"MANDT"));
		param.setZprdgbn(DatasetUtility.getString(dsCond,"ZPRDGBN"));
		param.setSpras(ZLang.convSapLang(DatasetUtility.getString(dsCond,"SPRAS")));
		
		
		// 2020 브랜드
		if(DatasetUtility.getString(dsCond,"BRNDCD") == null) {
			param.setBrndcd("NOBRND");
		} else {
			if("".equals(DatasetUtility.getString(dsCond,"BRNDCD"))) {
			    param.setBrndcd("NOBRND");
		    } else {
		    	param.setBrndcd(DatasetUtility.getString(dsCond,"BRNDCD"));
		    }
		}
		
		List<Ses0320> list = service.findTemp(param);	// 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MCLASS"  , list.get(i).getMclass());
			dsList.setColumn(i, "ATKLA"    , list.get(i).getAtkla());
			dsList.setColumn(i, "PRH"       , list.get(i).getPrh());
			dsList.setColumn(i, "PRD"       , list.get(i).getPrd());
			dsList.setColumn(i, "FLAG"      , list.get(i).getFlag());
			dsList.setColumn(i, "PRHNAME", list.get(i).getPrhname());
			
			//System.out.println("====================== CNT = ");
			dsList.setColumn(i, "CNT"  , list.get(i).getCnt());
			dsList.setColumn(i, "ATFOR", list.get(i).getAtfor()); // 2013.03.07 추가
			
			
			// 2020 브랜드
			dsList.setColumn(i, "PCNCD"  , list.get(i).getPcncd());
			dsList.setColumn(i, "BRNDCD"  , list.get(i).getBrndcd());
			dsList.setColumn(i, "BRNDSEQ"  , list.get(i).getBrndseq());
			dsList.setColumn(i, "DISPTP"  , list.get(i).getDisptp());
			dsList.setColumn(i, "MODITP"  , list.get(i).getModitp());
		}
				
		return dsList;
	}

	private Dataset qtlistToDataset(MipParameterVO paramVO, Dataset dsList, Dataset dsCond) {

		Ses0320ParamVO param = new Ses0320ParamVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setZprdgbn(DatasetUtility.getString(dsCond,"ZPRDGBN"));
		param.setSpras(DatasetUtility.getString(dsCond,"SPRAS"));
		param.setQtnum(DatasetUtility.getString(dsCond,"QTNUM"));
		param.setQtser(DatasetUtility.getInt(dsCond,"QTSER"));
		param.setQtseq(DatasetUtility.getInt(dsCond,"QTSEQ"));

//		System.out.print("\n@@@ param.ZPRDGBN ===>"+ param.getZprdgbn()	        +"\n");    
		
		List<Ses0320> list = service.findQt(param);	// 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "NAM_CHAR"       , list.get(i).getPrh());
			dsList.setColumn(i, "VALUE1"       , list.get(i).getPrd());
			
		}
				
		return dsList;
	}
	
	
	@RequestMapping(value="findPt")
	public View Ses0320findPt(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		
		// OUTPUT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_sap");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		 
		try {
			dsList = ptlistToDataset(paramVO, dsList, dsCond);
			
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");
		} finally {
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);

			ds_error.setId("ds_error");
			resultVO.addDataset(ds_error);

			model.addAttribute("resultVO", resultVO);  
		}
		
		return view;
	}

	// 2020 브랜드
	private Dataset ptlistToDataset(MipParameterVO paramVO, Dataset dsList, Dataset dsCond) {
		Ses0320ParamVO param = new Ses0320ParamVO();
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setHogi(DatasetUtility.getString(dsCond,"HOGI"));

		List<Ses0320> list = service.findPt(param);	// 서비스CALL
		dsList.deleteAll();

		for (int i=0;i<list.size();i++) {
			dsList.appendRow();
			dsList.setColumn(i, "NAM_CHAR"       , list.get(i).getPrh());
			dsList.setColumn(i, "VALUE1"       , list.get(i).getPrd());
		}

		return dsList;
	}
		
}
