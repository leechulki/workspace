package hdel.sd.sbp.control;

/**
 * 사업계획 현황(보수집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.sbp.domain.Sbp0120ParamVO;
import hdel.sd.sbp.domain.Sbp0120VO;
import hdel.sd.sbp.service.Sbp0120S;
import hdel.sd.smp.control.SmpComC;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sbp0120")
public class Sbp0120C extends CallSAP{
	Logger logger = Logger.getLogger(this.getClass());
 
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0120S service;
	
	/**
	 * 전체 조회
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View find(MipParameterVO paramVO, Model model) {

		Dataset dsInput = paramVO.getDataset("ds_input");
		logger.info("@@@@@@@@@ dsInput in @@@@@@@@@@@@@@");
		logger.info(dsInput.toString());
		 
		try { 

			//CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
			//CW00002=필수 입력항목입니다.\n확인하여 주십시오.
			if ( "".equals( dsInput.getColumn(0, "ZPYEAR").toString() ) 
					|| dsInput.getColumn(0, "ZPYEAR").toString() == null )
			{
				throw new BizException("CW00002,계획년도");
			}
			if ( "".equals( dsInput.getColumn(0, "WHERE").toString() ) 
					|| dsInput.getColumn(0, "WHERE").toString() == null )
			{
				throw new BizException("CW00001,조회구분");
			}

			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			Sbp0120ParamVO paramV = new Sbp0120ParamVO();					// vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); 				// 세션권한
			paramV.setZPYEAR(dsInput.getColumn(0, "ZPYEAR").toString());	// 계획년도
			paramV.setWHERE(dsInput.getColumn(0, "WHERE").toString());		// 조회구분
			paramV.setAUART(dsInput.getColumn(0, "AUART").toString());		// 오더유형(판매유형문서)
			paramV.setVKBUR_F(dsInput.getColumn(0, "VKBUR_F").toString());	// 사업장 from
			paramV.setVKBUR_T(dsInput.getColumn(0, "VKBUR_T").toString());	// 사업장 to
			paramV.setVKGRP_F(dsInput.getColumn(0, "VKGRP_F").toString());	// 영업그룹 from
			paramV.setVKGRP_T(dsInput.getColumn(0, "VKGRP_T").toString());	// 영업그룹 to
			paramV.setZKUNNR(dsInput.getColumn(0, "ZKUNNR").toString());	// 영업사원

			logger.info("@@@@@@@@@ paramV in @@@@@@@@@@@@@@");
			logger.info(paramV.toString());
			
			List<Sbp0120VO> list = service.find(paramV);
			
			// list결과를 dataset으로 담기
			Dataset dsOutput = new Dataset();
			SmpComC.moveDs2List(dsOutput, Sbp0120VO.class, list );
			
			logger.info("@@@@@@@@@ dsOutput in @@@@@@@@@@@@@@");
			logger.info(dsOutput.toString());
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput); 
			model.addAttribute("resultVO", resultVO);     
		}  catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if ( message.length <= 1)
			{
				SmpComC.moveDs2Msg(message[0], message[0], model);
			}
			else
			{
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
		}  catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	
	
	
}
