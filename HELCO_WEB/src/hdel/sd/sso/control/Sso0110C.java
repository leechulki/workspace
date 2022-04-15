package hdel.sd.sso.control;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.ksign.KsignSPinCrypto;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sso.domain.ZSDS0027;  		// 프로젝트별 수금관리대장
import hdel.sd.sso.service.Sso0110S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("Sso0110")
public class Sso0110C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sso0110S service;
	
	@RequestMapping(value="find")
	public View test(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0027[] 	list 	= new ZSDS0027[]{};  // 프로젝트별 수금관리목록 WFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI로 return할 out dataset
		Dataset 	dsError = null;				// UI로 return할 오류메세지 dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	+"\n");  
		System.out.print("\n@@@ dsCond.I_FR_KUN	===>"+DatasetUtility.getString(dsCond,"I_FR_KUN")	+"\n");
		System.out.print("\n@@@ dsCond.I_KTOKD	===>"+DatasetUtility.getString(dsCond,"I_KTOKD")	+"\n");
		System.out.print("\n@@@ dsCond.I_NAME1	===>"+DatasetUtility.getString(dsCond,"I_NAME1")	+"\n");
		System.out.print("\n@@@ dsCond.I_STCD1	===>"+DatasetUtility.getString(dsCond,"I_STCD1")	+"\n");
		System.out.print("\n@@@ dsCond.I_STCD2	===>"+DatasetUtility.getString(dsCond,"I_STCD2")	+"\n");
		System.out.print("\n@@@ dsCond.I_TO_KUN	===>"+DatasetUtility.getString(dsCond,"I_TO_KUN")	+"\n");
		//--------------------------------------------------------------------------------------------
		KsignSPinCrypto crypt = new KsignSPinCrypto(paramVO.getVariable("G_SYSID"));
		// WFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "I_FR_KUN")  // 오더유형
				 ,DatasetUtility.getString(dsCond, "I_KTOKD")  // 고객번호 Fr
				 ,DatasetUtility.getString(dsCond, "I_NAME1" )   // 고객명
				 ,crypt.encJumin(
				 DatasetUtility.getString(dsCond, "I_STCD1" )  // 법인/주민번호
				 )
				 ,DatasetUtility.getString(dsCond, "I_STCD2" )  // 사업자번호
				 ,DatasetUtility.getString(dsCond, "I_TO_KUN")  // 고객번호 To
				, listMsg
				, list
		}; 
		 
		try { 

			System.out.print("\n@@@ ZWEB_SD_CHN_127 start" + "\n");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sso.domain.ZWEB_SD_CHN_127", obj, false);	

			// RFC 출력구조체로 out dataset 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0027.getDataset();
			ds.setId("ds_list");  
			
			// RFC 호출결과를 out dataset에 옮기기
			CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
			for (int i = 0; i < ds.getRowCount(); i++) {
				ds.setColumn(i, "STCD1", crypt.decJumin(ds.getColumnAsString(i, "STCD1")));
			}			
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			dsError.setId("ds_error");
			 
			System.out.print("\n@@@ ZWEB_SD_CHN_127 SUCCESS!!!" + "\n");
			
		} catch (CommRfcException e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_127 CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (Exception e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_127 Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	 
		
		// 데이터건수 INFO
		System.out.print("\n@@@ ds_list.getRowCount ===>" + ds.getRowCount() + "\n");
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds);
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
}

