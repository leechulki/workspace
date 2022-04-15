package hdel.sd.scl.control;

/**
 * On-Hand 수금계획 현황(집계)
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.scl.domain.Scl0080ParamVO;
import hdel.sd.scl.domain.Scl0080VO;
import hdel.sd.scl.service.Scl0080S;
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
@RequestMapping("scl0080")
public class Scl0080C extends CallSAP{
	Logger logger = Logger.getLogger(this.getClass());
 
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Scl0080S service;
	
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
			if ( "".equals( dsInput.getColumn(0, "ZPYM").toString() ) 
					|| dsInput.getColumn(0, "ZPYM").toString() == null )
			{
				throw new BizException("CW00002,계획년월f");
			}
			if ( "".equals( dsInput.getColumn(0, "ZPYM_TO").toString() ) 
					|| dsInput.getColumn(0, "ZPYM_TO").toString() == null )
			{
				throw new BizException("CW00002,계획년월t");
			}

			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			Scl0080ParamVO paramV = new Scl0080ParamVO();					// vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); 				// 세션권한
			paramV.setZPYM(dsInput.getColumn(0, "ZPYM").toString());		// 계획년월f
			paramV.setZPYM_TO(dsInput.getColumn(0, "ZPYM_TO").toString());	// 계획년월t
			paramV.setVKBUR_F(dsInput.getColumn(0, "VKBUR_F").toString());	// 사업장 from
			paramV.setVKBUR_T(dsInput.getColumn(0, "VKBUR_T").toString());	// 사업장 to
			paramV.setVKGRP_F(dsInput.getColumn(0, "VKGRP_F").toString());	// 영업그룹 from
			paramV.setVKGRP_T(dsInput.getColumn(0, "VKGRP_T").toString());	// 영업그룹 to
			paramV.setZKUNNR(dsInput.getColumn(0, "ZKUNNR").toString());	// 영업사원
			paramV.setAUART(dsInput.getColumn(0, "AUART").toString());		// 오더유형(판매유형문서)

			logger.info("@@@@@@@@@ paramV in @@@@@@@@@@@@@@");
			logger.info(paramV.toString());
			
			List<Scl0080VO> list = service.find(paramV);
			
			// list결과를 dataset으로 담기
			Dataset dsOutput = new Dataset();
			SmpComC.moveDs2List(dsOutput, Scl0080VO.class, list );
			
			logger.info("@@@@@@@@@ dsOutput in @@@@@@@@@@@@@@");
			logger.info(dsOutput.toString());
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput); 
			model.addAttribute("resultVO", resultVO);     
		} catch (BizException e) {
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
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	
	
	
}
