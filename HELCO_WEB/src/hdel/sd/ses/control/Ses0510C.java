package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0510S;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping("ses0510")
public class Ses0510C {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0510S service;
	
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
	 *  �ù漭 ���� ��û ����Ʈ ��ȸ
	 ------------------------------------------------------*/
	@RequestMapping(value="selectDeviationList") 
	public View selectDeviationList(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
						
			// ����ȣ��
			resultVO = service.selectDeviationList(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0510TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}

}