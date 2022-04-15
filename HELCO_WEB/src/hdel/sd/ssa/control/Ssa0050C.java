package hdel.sd.ssa.control;

/**
 * On-Hand �����ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.smp.control.SmpComC;
import hdel.sd.ssa.domain.Ssa0050ParamVO;
import hdel.sd.ssa.domain.Ssa0050VO;
import hdel.sd.ssa.service.Ssa0050S;

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
@RequestMapping("ssa0050")
public class Ssa0050C extends CallSAP{
	Logger logger = Logger.getLogger(this.getClass());
 
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ssa0050S service;
	
	/**
	 * ��ü ��ȸ
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

			//CW00001=[${}] �׸� ���� �����Ǿ����ϴ�.\nȮ���Ͽ� �ֽʽÿ�.
			//CW00002=�ʼ� �Է��׸��Դϴ�.\nȮ���Ͽ� �ֽʽÿ�.
			if ( "".equals( dsInput.getColumn(0, "ZPYM").toString() ) 
					|| dsInput.getColumn(0, "ZPYM").toString() == null )
			{
				throw new BizException("CW00002,��ȹ���f");
			}
			if ( "".equals( dsInput.getColumn(0, "ZPYM_TO").toString() ) 
					|| dsInput.getColumn(0, "ZPYM_TO").toString() == null )
			{
				throw new BizException("CW00002,��ȹ���t");
			}

			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			Ssa0050ParamVO paramV = new Ssa0050ParamVO();					// vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); 				// ���Ǳ���
			paramV.setZPYM(dsInput.getColumn(0, "ZPYM").toString());		// ��ȹ���f
			paramV.setZPYM_TO(dsInput.getColumn(0, "ZPYM_TO").toString());	// ��ȹ���t
			paramV.setVKBUR_F(dsInput.getColumn(0, "VKBUR_F").toString());	// ����� from
			paramV.setVKBUR_T(dsInput.getColumn(0, "VKBUR_T").toString());	// ����� to
			paramV.setVKGRP_F(dsInput.getColumn(0, "VKGRP_F").toString());	// �����׷� from
			paramV.setVKGRP_T(dsInput.getColumn(0, "VKGRP_T").toString());	// �����׷� to
			paramV.setZKUNNR(dsInput.getColumn(0, "ZKUNNR").toString());	// �������

			logger.info("@@@@@@@@@ paramV in @@@@@@@@@@@@@@");
			logger.info(paramV.toString());
			
			List<Ssa0050VO> list = service.find(paramV);
			
			// list����� dataset���� ���
			Dataset dsOutput = new Dataset();
			SmpComC.moveDs2List(dsOutput, Ssa0050VO.class, list );
			
			logger.info("@@@@@@@@@ dsOutput in @@@@@@@@@@@@@@");
			logger.info(dsOutput.toString());
			
			// RFC ��� dataset�� return
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