package hdel.sd.plm.control;

import hdel.lib.control.SrmView;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.plm.domain.PlmSoapConfig;
import hdel.sd.plm.soap.duty.ElvAutoCalcSoapProxy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

/**
 * PLM 웹서비스 Controller 클래스
 * 
 * @author  박수근
 * @since 2020.10.16
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.10.16         박수근          최초 생성
 * 
 * </pre>
 */

@Controller
@RequestMapping("plmDutySoapC")
public class PlmDutySoapC {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	/** 
	 * PLM Duty, 종속성, 제한조건, 층별사양 연동
	 * @param MipParameterVO
	 * @return "String"
	 * @throws Exception
	 */
	@RequestMapping(value="plmDuty")
	public View plmDuty(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		String gSysid  = paramVO.getVariable("G_SYSID");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		String jsonData  = paramVO.getVariable("jsonData");
		String opt1      = paramVO.getVariable("opt1");
		String opt2      = paramVO.getVariable("opt2");
		String rtnData = null;
		try {
			// 환경설정
			PlmSoapConfig.setSysId(gSysid);
			// default timeout 5분
			// PlmSoapConfig.setTimeOut(300000);
			// 로컬환경으로 변경
			//PlmSoapConfig.setSysId("local");
			if(logger.isDebugEnabled()) {
				byte[] decodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(jsonData.getBytes());
				logger.debug("jsonData:"+new String(decodedBytes, "EUC-KR"));
				logger.debug("opt1:"+opt1);
				logger.debug("opt2:"+opt2);
			}
			ElvAutoCalcSoapProxy proxy = new ElvAutoCalcSoapProxy();
			rtnData = proxy.calc(jsonData, opt1, opt2);
			if(logger.isDebugEnabled()) {
				byte[] decodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(rtnData.getBytes());
				logger.debug("rtnData:"+new String(decodedBytes));
			}
			resultVO.addVariable("G_outputXml", rtnData);
		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError);
		} finally {
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}
}
