package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0031NS;
import hdel.sd.ses.service.SesEgisS;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sesEgis")
public class SesEgisC {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Ses0031NS service;

	@Autowired
	private SesEgisS egisService;

	/**
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "searchCost")
	public View Ses0031CostView(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			// DAO생성
			service.createDao(session);
			// 서비스호출
			resultVO = service.searchCost(paramVO, session);
		} catch (Exception e) {
			logger.error("@@@ Ses0031N searchCost Exception ERROR!!!");
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	} 

//	'-. E-GIS에서 "HQ PO발송" 버튼 클릭 시 SRM의 견적상태를 수정
//	-. 법인의 프로젝트 번호를  I/F
//	-. E-GIS에서 SRM의 Controller를 호출, URL을 통지, I/F 전문 추가
//	관련 갱신 테이블은 ZSDT1046 table (ZPRGFLG = ‘61’ , UDATE, UTIME, UUSER) 과 zsdt1091 table(PSPID_CO)를 갱신해주세요.
//	Service, domain, dao를 분리하여 작성 바랍니다.

	@RequestMapping(value = "updateHQPOState")
	public View updateHQPOState(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			dsHeader = egisService.updateHQPOState(paramVO, model, session);
			MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return

			resultVO.addDataset(dsHeader);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

/*
	
	*/
	
	@RequestMapping(value = "updateTradeAmt")
	public View updateTradeAmt(MipParameterVO paramVO, Model model) {

		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			resultVO = egisService.updateTradeAmt(paramVO, session);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
/*	
	Nego금액 update에 대한 인터페이스정의서 입니다.
	호출 서비스는 “sesEgis”/”updateNegoPrice”  작업 바랍니다.
	F_AMTTP = 1 (원가), 2 = (견적가) 
	원가는 중계무역인 경우 e-GIS에서 SRM으로 I/F됩니다.  
	ZUAM, ZUAMS, ZCOST, 시행율등 연관 필드를 갱신하시기 바랍니다. (중계무역 “updateTradeAmt”를 참고하시기 바랍니다.)
	견적가는 ZEAM 필드를 갱신하시기 바랍니다.
	
*/	@RequestMapping(value = "updateNegoPrice")
	public View updateNegoPrice(MipParameterVO paramVO, Model model) {

		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			resultVO = egisService.updateNegoPrice(paramVO, session);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}	
	
}