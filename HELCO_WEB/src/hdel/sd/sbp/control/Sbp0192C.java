package hdel.sd.sbp.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sbp.service.Sbp0192S;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sbp0192")
public class Sbp0192C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0192S service;

	@RequestMapping(value="update")
	public View update(MipParameterVO paramVO, Model model) {
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		try {
			// ���� ȣ��
			service.updateSORLT(paramVO, model, session);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		return view;
	}

	@RequestMapping(value="insert")
	public View insert(MipParameterVO paramVO, Model model) {
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		try {
			// ���� ȣ��
			service.insertSORLT(paramVO, model, session);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		return view;
	}
	
}
