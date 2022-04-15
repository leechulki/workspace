package hdel.sd.sag.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sag.domain.ZSDS0037;

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
@RequestMapping("Sag0030")
public class Sag0030C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@RequestMapping(value="search")
	public View main(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");

		// RFC INPUT PARAM DECLARE 
		ZSDS0037[] 	list 	= new ZSDS0037[]{};  // 프로젝트별 수금관리목록 RFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI로 return할 out dataset
		Dataset 	dsError = null;				// UI로 return할 오류메세지 dataset
		
		// RFC INPUT SET
		Object obj[] = new Object[]{
                  DatasetUtility.getString(dsCond, "I_FR_SO")		// SO번호
                , DatasetUtility.getString(dsCond, "I_FR_VB")		// 부서코드
                , DatasetUtility.getString(dsCond, "I_FR_VG")		// 팀코드
                , DatasetUtility.getString(dsCond, "I_FR_YMD")	// 지급일
                , DatasetUtility.getString(dsCond, "I_LIFNR")		// 협력업체
                , DatasetUtility.getString(dsCond, "I_SMAN")		// 담당자
                , DatasetUtility.getString(dsCond, "I_TO_SO")		// SO번호
                , DatasetUtility.getString(dsCond, "I_TO_VB")		// 부서코드
                , DatasetUtility.getString(dsCond, "I_TO_VG")		// 팀코드
                , DatasetUtility.getString(dsCond, "I_TO_YMD")	// 지급일
				, listMsg
				, list
		};

		try {
			
			// RFC CALL
			
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sag.domain.ZWEB_SD_CHN_139", obj, false);	
			// RFC 출력구조체로 out dataset 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0037.getDataset();
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
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

}
