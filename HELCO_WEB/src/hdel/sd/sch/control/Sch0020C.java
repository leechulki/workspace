package hdel.sd.sch.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.RANGE_C10;
import hdel.sd.com.service.ComboS;
import hdel.sd.sch.domain.ZSDS0030;

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
@RequestMapping("sch0020")
public class Sch0020C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private ComboS service;
	
	@RequestMapping(value="search")
	public View main(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsCondSo = paramVO.getDataset("ds_cond_so");
		Dataset dsCondBuyr = paramVO.getDataset("ds_cond_buyr");

		// RFC INPUT PARAM DECLARE 
		ZQMSEMSG[] 	listMsg   = new ZQMSEMSG[]{};
		RANGE_C10[] listBury = (RANGE_C10[])CallSAPHdel.moveDs2Obj2(dsCondBuyr, RANGE_C10.class, "");
		RANGE_C10[] listSo   = (RANGE_C10[])CallSAPHdel.moveDs2Obj2(dsCondSo, RANGE_C10.class, "");
		ZSDS0030[] 	list 	  = new ZSDS0030[]{};  // 프로젝트별 수금관리목록 RFC output list
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI로 return할 out dataset
		Dataset 	dsError = null;				// UI로 return할 오류메세지 dataset
		
		// RFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "I_AUART")		// 오더유형
				, DatasetUtility.getString(dsCond, "I_BUYR")		// 거래선
				, DatasetUtility.getString(dsCond, "I_FR_SO")		// SO
				, DatasetUtility.getString(dsCond, "I_FR_VB")		// 부서코드
				, DatasetUtility.getString(dsCond, "I_FR_VG")		// 팀코드
				, DatasetUtility.getString(dsCond, "I_FR_YMD")	// 수주일
				, DatasetUtility.getString(dsCond, "I_GUBUN")		// 기준일
				, DatasetUtility.getString(dsCond, "I_NAME")		// 현장명
				, DatasetUtility.getString(dsCond, "I_SMAN")		// 담당자
				, DatasetUtility.getString(dsCond, "I_SPART")		// 제품군
				, DatasetUtility.getString(dsCond, "I_TO_SO")		// SO
				, DatasetUtility.getString(dsCond, "I_TO_VB")		// 부서코드
				, DatasetUtility.getString(dsCond, "I_TO_VG")		// 팀코드
				, DatasetUtility.getString(dsCond, "I_TO_YMD")	// 수주일
				, listMsg
				, listBury
				, listSo
				, list
		};

		try {
			
			// RFC CALL
			
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sch.domain.ZWEB_SD_CHN_124", obj, false);	
			// RFC 출력구조체로 out dataset 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0030.getDataset();
			ds.setId("ds_list");  
			
			// RFC 호출결과를 out dataset에 옮기기  
			CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
		} catch (CommRfcException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds);
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
}
