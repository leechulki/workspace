package hdel.sd.sbp.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sbp.service.Sbp0162S_N;

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
@RequestMapping("sbp0162_N")
public class Sbp0162C_N {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0162S_N service;

	/**
	 * 종결처리시 공사취소(71),공사중단(72),미참여(73)시 수주결과(sorlt)를 갱신
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="update")
	public View update(MipParameterVO paramVO, Model model) {

		logger.info("@@@@@@@@@ 종결처리 update in @@@@@@@@@@@@@@");

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		try {
			// 저장 호출
			service.updateSORLT(paramVO);
		} catch (Exception e ) {
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}

		MipResultVO resultVO = new MipResultVO(); 
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	/**
	 * 
	 * 종결처리시 이월(70)일경우 copy처리
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="insert")
	public View insert(MipParameterVO paramVO, Model model) {

		logger.info("@@@@@@@@@ 종결처리 insert in @@@@@@@@@@@@@@");
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		try {
			// 저장 호출
			service.insertSORLT(paramVO);
		} catch (Exception e ) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}

		MipResultVO resultVO = new MipResultVO(); 
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	
}
