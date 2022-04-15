package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0490S;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0490")
public class Ses0490C {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0490S service;
	
	/*-----------------------------------------------------
	 *  견적상세 사양 조회
	 ------------------------------------------------------*/
	@RequestMapping(value="searchTemplate") 
	public View Ses0490TemplateView(MipParameterVO paramVO, Model model) {
		// 결과 VIEW
		MipResultVO resultVO = new MipResultVO();
		
		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// 서비스호출
			resultVO = service.searchTemplate(paramVO);
		} catch (Exception e) {
			logger.error("@@@ Ses0490TemplateView Exception ERROR!!!");
			e.printStackTrace(); 
			model.addAttribute("resultVO", resultVO);
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}	
	
	/*-----------------------------------------------------
	 *  영업사양 제외 대상 저장
	 ------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Ses0490Save(MipParameterVO paramVO, Model model) {
		
		//SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return

		try {
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// 서비스호출
			service.save(paramVO, resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		model.addAttribute("resultVO", resultVO);

		return view;
	}
}