package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0540S;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping("ses0540")
public class Ses0540C {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0540S service;
	
	/*-----------------------------------------------------
	 *  파트 코드 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="queryPartCd") 
	public View queryPartCd(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// 서비스호출
			resultVO = service.searchPartCd(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}	
	
	/*-----------------------------------------------------
	 *  세부파트 코드 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="queryDetailCd") 
	public View queryDetailCd(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// 서비스호출
			resultVO = service.searchDetailCd(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	/*-----------------------------------------------------
	 *  검토항목 코드 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="queryCheckCd") 
	public View queryCheckCd(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// 서비스호출
			resultVO = service.searchCheckId(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	/*-----------------------------------------------------
	 *  시방서 검토 요청 상세 리스트 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="selectDeviationListHeader") 
	public View selectDeviationListHeader(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// 서비스호출
			resultVO = service.selectDeviationListHeader(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	/*-----------------------------------------------------
	 *  시방서 검토 요청 상세 리스트 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="selectDeviationListDetail") 
	public View selectDeviationListDetail(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// 서비스호출
			resultVO = service.selectDeviationListDetail(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	


	/*-----------------------------------------------------
	 *  시방서 검토 요청 접수
	 ------------------------------------------------------*/
	@RequestMapping(value="saveDeviationDetailListReceipt") 
	public View saveDeviationDetailListReceipt(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// 서비스호출
			resultVO = service.insertDeviationDetailListReceipt(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	/*-----------------------------------------------------
	 *  시방서 검토 요청 반송
	 ------------------------------------------------------*/
	@RequestMapping(value="saveDeviationDetailListSendback") 
	public View saveDeviationDetailListSendback(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// 서비스호출
			resultVO = service.saveDeviationDetailListSendback(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	/*-----------------------------------------------------
	 *  시방서 검토 내용 저장
	 ------------------------------------------------------*/
	@RequestMapping(value="saveDeviationDetailListReview") 
	public View saveDeviationDetailListReview(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// 서비스호출
			resultVO = service.saveDeviationDetailListReview(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	/*-----------------------------------------------------
	 *  시방서 검토 완료
	 ------------------------------------------------------*/
	@RequestMapping(value="saveDeviationDetailListFinish") 
	public View saveDeviationDetailListFinish(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// 서비스호출
			resultVO = service.saveDeviationDetailListFinish(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
}