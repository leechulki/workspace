package hdel.sd.sbp.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.sbp.domain.Sbp0090ParamVO;
import hdel.sd.sbp.domain.Sbp0090VO;
import hdel.sd.sbp.service.Sbp0090S;
import hdel.sd.smp.control.SmpComC;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sbp0090")
public class Sbp0090C extends CallSAP{
	Logger logger = Logger.getLogger(this.getClass());
 
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0090S service;
	
	@RequestMapping(value="find")
	public View find(MipParameterVO paramVO, Model model) {

		Dataset dsInput = paramVO.getDataset("ds_input");
		Dataset dsOutput = paramVO.getDataset("ds_output");

		try { 

			if ( "".equals( dsInput.getColumn(0, "ZPYEAR").toString() ) || dsInput.getColumn(0, "ZPYEAR").toString() == null ) {
				throw new BizException("CW00002,계획년도");
			}
			if ( "".equals( dsInput.getColumn(0, "WHERE").toString() ) || dsInput.getColumn(0, "WHERE").toString() == null ) {
				throw new BizException("CW00001,조회구분");
			}

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			Float exchg_usdkrw = new Float(0);
			String bsdat = dsInput.getColumn(0, "ZPYEAR").toString() + "0101";
			exchg_usdkrw = service.getExchangeBPUSD(paramVO.getVariable("G_MANDT"), bsdat);
			if(exchg_usdkrw == null) exchg_usdkrw = (float) 0.0000;  /* 환율값이 null인경우 처리 - 20190130 by lyk */

			Sbp0090ParamVO paramV = new Sbp0090ParamVO();
			paramV.setMANDT(paramVO.getVariable("G_MANDT"));
			paramV.setZPYEAR(dsInput.getColumn(0, "ZPYEAR").toString());
			paramV.setSPART(dsInput.getColumnAsString(0, "SPART"));
			paramV.setWHERE(dsInput.getColumn(0, "WHERE").toString());
			paramV.setVKBUR_F(dsInput.getColumn(0, "VKBUR_F").toString());
			paramV.setVKBUR_T(dsInput.getColumn(0, "VKBUR_T").toString());
			paramV.setVKGRP_F(dsInput.getColumn(0, "VKGRP_F").toString());
			paramV.setVKGRP_T(dsInput.getColumn(0, "VKGRP_T").toString());
			paramV.setZKUNNR(dsInput.getColumn(0, "ZKUNNR").toString());

			List<Sbp0090VO> list = service.find(paramV);
		
			DatasetUtil.moveListToDs(list, dsOutput);
			
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput);
			resultVO.addVariable("exchg_usdkrw", exchg_usdkrw.toString());
			model.addAttribute("resultVO", resultVO);     
		}  catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if ( message.length <= 1) {
				SmpComC.moveDs2Msg(message[0], message[0], model);
			}
			else {
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
		}  catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
}