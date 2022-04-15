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
	 *  ��Ʈ �ڵ� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="queryPartCd") 
	public View queryPartCd(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// ����ȣ��
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
	 *  ������Ʈ �ڵ� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="queryDetailCd") 
	public View queryDetailCd(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// ����ȣ��
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
	 *  �����׸� �ڵ� ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="queryCheckCd") 
	public View queryCheckCd(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// ����ȣ��
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
	 *  �ù漭 ���� ��û �� ����Ʈ ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="selectDeviationListHeader") 
	public View selectDeviationListHeader(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// ����ȣ��
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
	 *  �ù漭 ���� ��û �� ����Ʈ ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="selectDeviationListDetail") 
	public View selectDeviationListDetail(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// ����ȣ��
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
	 *  �ù漭 ���� ��û ��� ����
	 ------------------------------------------------------*/
	@RequestMapping(value="insertDeviationHeader") 
	public View insertDeviationHeader(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// ����ȣ��
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
	 *  �ù漭 ���� ��û ����
	 ------------------------------------------------------*/
	@RequestMapping(value="saveDeviationDetail") 
	public View saveDeviationDetail(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// ����ȣ��
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