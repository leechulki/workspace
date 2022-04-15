package hdel.sd.sso.control;


import hdel.lib.control.SrmView;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.sso.domain.ZSDS0012;
import hdel.sd.sso.domain.ZSDS0013;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sso0040")
public class Sso0040C {

	@Autowired
	private SrmView view;
	
	@RequestMapping(value="find")
	public View Sso0040FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0012[] 	list 	= new ZSDS0012[]{};  // 프로젝트별 수금관리목록 WFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI로 return할 out dataset
		Dataset 	dsError = null;				// UI로 return할 오류메세지 dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		//System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	+"\n");  
		//System.out.print("\n@@@ dsCond.FR_SO	===>"+DatasetUtility.getString(dsCond,"FR_SO")	+"\n");
		//System.out.print("\n@@@ dsCond.TO_SO	===>"+DatasetUtility.getString(dsCond,"TO_SO")	+"\n");
		//System.out.print("\n@@@ dsCond.FR_PO	===>"+DatasetUtility.getString(dsCond,"FR_PO")	+"\n");
		//System.out.print("\n@@@ dsCond.TO_PO	===>"+DatasetUtility.getString(dsCond,"TO_PO")	+"\n");
		//--------------------------------------------------------------------------------------------
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "FR_PO" )  // PO _FR
				 ,DatasetUtility.getString(dsCond, "FR_SO" )  // S/O _FR
				 ,DatasetUtility.getString(dsCond, "TO_PO")  // PO _TO
				 ,DatasetUtility.getString(dsCond, "TO_SO")  // S/O _TO
				, listMsg
				, list
		}; 
		 
		try { 

			//System.out.print("\n@@@ ZWEB_SD_CHN_133 start" + "\n");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sso.domain.ZWEB_SD_CHN_133", obj, false);	

			// RFC 출력구조체로 out dataset 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0012.getDataset();
			ds.setId("ds_list");  
			
			// RFC 호출결과를 out dataset에 옮기기  
			CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);

			//System.out.print("\n@@@ ZWEB_SD_CHN_133 SUCCESS!!!" + "\n");
			
		} catch (CommRfcException e) { 
			// 2013.03.04 에러 데이터 추가
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, e.getMessage(), "", "Y", "Y");
			dsError.setId("ds_error");
			e.printStackTrace();
		} catch (Exception e) { 
			// 2013.03.04 에러 데이터 추가
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, e.getMessage(), "", "Y", "Y");
			dsError.setId("ds_error");
			e.printStackTrace();
		}	 
		
		// 데이터건수 INFO
		//System.out.print("\n@@@ ds_list.getRowCount ===>" + ds.getRowCount() + "\n");
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds);
		if ( dsError != null ) resultVO.addDataset	(dsError); // 2013.03.04 에러 데이터셋 추가
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="report")
	public View Sso0040DetailView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0012[] 	list12 	= new ZSDS0012[]{};  // WFC output list
		ZSDS0013[] 	list13A = new ZSDS0013[]{};  // WFC output list
		ZSDS0013[] 	list13B = new ZSDS0013[]{};  // WFC output list
		ZSDS0013[] 	list13C = new ZSDS0013[]{};  // WFC output list
		ZSDS0013[] 	list13D = new ZSDS0013[]{};  // WFC output list
		ZSDS0013[] 	list13E = new ZSDS0013[]{};  // WFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds	= null;				// UI로 return할 out dataset
		Dataset[] 	dsar	= null;				// UI로 return할 out dataset
		Dataset 	dsError = null;				// UI로 return할 오류메세지 dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		//System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	+"\n");  
		//System.out.print("\n@@@ dsCond.CHK	===>"+DatasetUtility.getString(dsCond,"CHK")	+"\n");
		//System.out.print("\n@@@ dsCond.FR_PO	===>"+DatasetUtility.getString(dsCond,"FR_PO")	+"\n");
		//System.out.print("\n@@@ dsCond.FR_SO	===>"+DatasetUtility.getString(dsCond,"FR_SO")	+"\n");
		//--------------------------------------------------------------------------------------------
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				 list13A
				, list13B
				, list13C
				, list13D
				, list13E
				 , DatasetUtility.getString(dsCond, "CHK")     // CHK
				 ,DatasetUtility.getString(dsCond, "FR_PO" )  // PO _FR
				 ,DatasetUtility.getString(dsCond, "FR_SO" )  // S/O _FR
				, listMsg
				, list12
		}; 
		 
		try { 
			//System.out.print("\n@@@ ZWEB_SD_CHN_133_PRINT start" + "\n");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sso.domain.ZWEB_SD_CHN_133_PRINT", obj, false);

			// RFC 출력구조체로 out dataset 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0012.getDataset();
			ds.setId("ds_F");

			dsar = CallSAP.isJCO() ? null : ZSDS0013.getDataset();
			dsar[0].setId("ds_A");
			dsar[1].setId("ds_B");
			dsar[2].setId("ds_C");
			dsar[3].setId("ds_D");
			dsar[4].setId("ds_E");

			// RFC 호출결과를 out dataset에 옮기기  
			CallSAP.moveObj2Ds(dsar[0], stub.getOutput("A_ITAB"));
			CallSAP.moveObj2Ds(dsar[1], stub.getOutput("B_ITAB"));
			CallSAP.moveObj2Ds(dsar[2], stub.getOutput("C_ITAB"));
			CallSAP.moveObj2Ds(dsar[3], stub.getOutput("D_ITAB"));
			CallSAP.moveObj2Ds(dsar[4], stub.getOutput("E_ITAB"));
			CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));

			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);

			//System.out.print("\n@@@ ZWEB_SD_CHN_133_PRINT SUCCESS!!!" + "\n");
			
		} catch (CommRfcException e) { 
			// 2013.03.04 에러 데이터 추가
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, e.getMessage(), "", "Y", "Y");
			dsError.setId("ds_error");
			e.printStackTrace();
		} catch (Exception e) {
			// 2013.03.04 에러 데이터 추가
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, e.getMessage(), "", "Y", "Y");
			dsError.setId("ds_error");
			e.printStackTrace();
		}	 
		
		// 데이터건수 INFO
		//System.out.print("\n@@@ dsar[0].getRowCount ===>" + dsar[0].getRowCount() + "\n");
		//System.out.print("\n@@@ dsar[1].getRowCount ===>" + dsar[1].getRowCount() + "\n");
		//System.out.print("\n@@@ dsar[2].getRowCount ===>" + dsar[2].getRowCount() + "\n");
		//System.out.print("\n@@@ dsar[3].getRowCount ===>" + dsar[3].getRowCount() + "\n");
		//System.out.print("\n@@@ dsar[3].getRowCount ===>" + dsar[4].getRowCount() + "\n");
		//System.out.print("\n@@@ ds_list.getRowCount ===>" + ds.getRowCount() + "\n");
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds); 
		if ( dsError != null ) resultVO.addDataset(dsError); // 2013.03.04 에러 데이터셋 추가
		
		for (int i=0; i<dsar.length; i++) {
			resultVO.addDataset(dsar[i]);
		}

		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
}
