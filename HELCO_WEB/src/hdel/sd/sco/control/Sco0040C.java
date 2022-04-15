package hdel.sd.sco.control;


import hdel.lib.control.SrmView;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sco.domain.ZSDS0065;
import hdel.sd.sco.domain.ZSDS0066;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("Sco0040")
public class Sco0040C {

	@Autowired
	private SrmView view;
	
	@RequestMapping(value="find")
	public View test(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0065[] 	list65 	= new ZSDS0065[]{};  // 리모델링 영업실적(사업계획)
		ZSDS0066[] 	list66 	= new ZSDS0066[]{};  // 리모델링 영업실적(실적)
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds65 	= null;	// UI로 return할 out dataset
		Dataset 	ds66 	= null; // UI로 return할 out dataset
		@SuppressWarnings("unused")
		Dataset 	dsError = null;	// UI로 return할 오류메세지 dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		System.out.print("\n@@@ dsCond.DUED	===>" +DatasetUtility.getString(dsCond,"DUED")	+"\n");
		System.out.print("\n@@@ dsCond.REALT ===>"+DatasetUtility.getString(dsCond,"REALT")	+"\n");
		System.out.print("\n@@@ dsCond.YMD	===>" +DatasetUtility.getString(dsCond,"YMD")	+"\n");
		//--------------------------------------------------------------------------------------------
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "DUED")  		// 마감기준
				 ,DatasetUtility.getString(dsCond, "REALT")  		// 실시간기준
				 ,DatasetUtility.getString(dsCond, "YMD" )   		// 기준일
				, listMsg
				, list65
				, list66
		};
		 
		try {
			
			System.out.print("\n@@@ ZWEB_SD_CHN_236 start" + "\n");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sco.domain.ZWEB_SD_CHN_236", obj, false);	
			
			// RFC 출력구조체로 out dataset 생성
			ds65 = CallSAP.isJCO() ? new Dataset() : ZSDS0065.getDataset();
			ds65.setId("ds_list65");
			ds66 = CallSAP.isJCO() ? new Dataset() : ZSDS0066.getDataset();
			ds66.setId("ds_list66");
			
			// RFC 호출결과를 out dataset에 옮기기  
			CallSAP.moveObj2Ds(ds65, stub.getOutput("T_ITAB1"));
			CallSAP.moveObj2Ds(ds66, stub.getOutput("T_ITAB2"));
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			
			System.out.print("\n@@@ ZWEB_SD_CHN_236 SUCCESS!!!" + "\n");
			
		} catch (CommRfcException e) {
			System.out.print("\n@@@ ZWEB_SD_CHN_236 CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.print("\n@@@ ZWEB_SD_CHN_236 Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}
		
		// 데이터건수 INFO
		System.out.print("\n@@@ ds_list65.getRowCount ===>" + ds65.getRowCount() + "\n");
		System.out.print("\n@@@ ds_list66.getRowCount ===>" + ds66.getRowCount() + "\n");
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds65);
		resultVO.addDataset(ds66);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
}
