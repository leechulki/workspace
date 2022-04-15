package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0520S;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping("ses0520")
public class Ses0520C {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0520S service;
	
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
	 *  시방서 검토 요청 헤더 저장
	 ------------------------------------------------------*/
	@RequestMapping(value="insertDeviationHeader") 
	public View insertDeviationHeader(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// 서비스호출
			resultVO = service.insertDeviationHeader(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	/*-----------------------------------------------------
	 *  시방서 검토 요청 저장
	 ------------------------------------------------------*/
	@RequestMapping(value="saveDeviationDetail") 
	public View saveDeviationDetail(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// 서비스호출
			resultVO = service.saveDeviationDetail(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}

}