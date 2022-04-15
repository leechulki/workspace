package hdel.sd.sso.control;

/**
 * ���ֺ��� - ǰ���簪 �ϰ�����
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ϴܿ� ǥ��
 */
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.ses.domain.ZSDS0016;
import hdel.sd.ses.domain.ZSDS0017;
import hdel.sd.smp.control.SmpComC;
import hdel.sd.sso.service.Sso0051NS;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sso0051n")
public class Sso0051NC extends CallSAP{
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sso0051NS service;  
	
	/**
	 * 
	 * HISTORY  : 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View find(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		String i_pspHogi 	= paramVO.getVariable("pspHogi"); // ȣ�⺰������Ʈ��ȣ

		// RFC INPUT PARAM DECLARE 
		ZSDS0016[] list_ZSDS0016			= new ZSDS0016[]{};  // sap output list
		ZSDS0017[] list_ZSDS0017			= new ZSDS0017[]{};  // sap output list
		ZQMSEMSG[] listMsg  				= new ZQMSEMSG[]{};  // sap �����޽��� return��
		
		// WFC INPUT SET
		Object obj[] = new Object[]{ 
				list_ZSDS0017
				, i_pspHogi  	// ȣ�⺰������Ʈ��ȣ
				, listMsg
				, list_ZSDS0016
		}; 

		// OUTPUT DATASET DECLARE
		Dataset ds_output_ZSDS0016			= null;		// sap�� ������� dataSet���� �������.
		Dataset ds_output_ZSDS0017			= null;		// sap�� ������� dataSet���� �������.
		Dataset dsError 					= null;		// sap�� �����޽����� datSet���� �������.
		
		// miplatform���� return
		MipResultVO resultVO = new MipResultVO();
		 
		try { 

			//CW00002=�ʼ� �Է��׸��Դϴ�.\nȮ���Ͽ� �ֽʽÿ�.
			if ( "".equals( i_pspHogi ) || i_pspHogi == null )
			{
				throw new BizException("CW00002,ȣ�⺰������Ʈ��ȣ");
			}
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT") 	// ���Ǳ���
								, "hdel.sd.ses.domain.ZWEB_SD_HOGI_SPEC"			
								, obj, false);	

			// RFC ��±���ü�� out dataset ����
			ds_output_ZSDS0016 = CallSAP.isJCO() ? new Dataset() : ZSDS0016.getDataset();
			ds_output_ZSDS0017 = CallSAP.isJCO() ? new Dataset() : ZSDS0017.getDataset();
			ds_output_ZSDS0016.setId("ds_output_ZSDS0016");  
			ds_output_ZSDS0017.setId("ds_output_ZSDS0017");  
			
			// RFC ȣ������ out dataset�� �ű��  
			CallSAP.moveObj2Ds(ds_output_ZSDS0016, stub.getOutput("T_ITAB"));
			CallSAP.moveObj2Ds(ds_output_ZSDS0017, stub.getOutput("H_ITAB"));
			
			// o_etab --> ó������ ����
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			if( listMsg.length != 0 )
			{
				if ( "4".equals( listMsg[0].getIDX().toString() ) )
				{
					resultVO.addDataset(dsError); 	// �����������
					model.addAttribute("resultVO", resultVO);  
					return view;
				}
			}
			
			
		} catch (CommRfcException e) { 
			// RfC Exc
			logger.info("@@@@@@@ RfC Exception ERROR!!!");
			e.printStackTrace();
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
			// java Exc
			logger.info("@@@@@@@ java Exception ERROR!!!");
			e.printStackTrace();
		}	 
		
		resultVO.addDataset(ds_output_ZSDS0016); 			// ó���������
		resultVO.addDataset(ds_output_ZSDS0017); 			// ó���������
		model.addAttribute("resultVO", resultVO);  
		return view;
	}

	// ��ຯ�� ȭ�鿡�� �Էµ� ȣ���� ����� ��ȸ  20130307 �輱ȣ 
	@RequestMapping(value="findQ")
	public View findQ(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		try {
			// DAO����
			service.createDao(session);
			
			// ����ȣ��
			resultVO = service.selectListSpec(paramVO, session);
		} catch (Exception e) {
			logger.error("@@@ findQ Exception ERROR!!!");
			e.printStackTrace(); 
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	}

	/**
	 * 
	 * HISTORY  : 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="copy")
	public View findCopy(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		try {
			// DAO����
			service.createDao(session);
			
			// ����ȣ��
			resultVO = service.listSpecCopy(paramVO);
		} catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if ( message.length <= 1) {
				SmpComC.moveDs2Msg(message[0], message[0], model);
			} else {
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
		} catch (Exception e) {
			logger.error("@@@ findQ Exception ERROR!!!");
			e.printStackTrace(); 
		}

		model.addAttribute("resultVO", resultVO);
		return view;
		/*		
		
		*/
		
	}
	
}
