package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0035S;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0035")
public class Ses0035C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0035S service;
	
	/*------------------------------------------------------------
	 *  ������û���, ������ ���� ��û ���
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Ses0035SaveView(MipParameterVO paramVO, Model model) { 
 
		// �������� DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));


		// DAO Create
		service.createDao(session);		
		
		try {
			// ���� ȣ��
			service.save(paramVO);

		} catch (Exception e ) {
			e.printStackTrace();
			// ȣ����  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO();
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// ����INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}

}
