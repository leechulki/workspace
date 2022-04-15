package hdel.sd.sco.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.sco.service.Sco0030S;
import hdel.sd.sco.domain.ZSDS0033;  		// ������Ʈ�� ���ݰ�������

import java.io.IOException;
	
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
// import com.helco.xf.cs96.ws.ZCSS_TEST;   // �׽�Ʈ�� ����
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

@Controller
@RequestMapping("Sco0030")
public class Sco0030C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sco0030S service;
	
	@RequestMapping(value="find")
	public View test(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0033[] 	list 	= new ZSDS0033[]{};  // ������Ʈ�� ���ݰ������ WFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI�� return�� out dataset
		Dataset 	dsError = null;				// UI�� return�� �����޼��� dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	+"\n");  
		System.out.print("\n@@@ dsCond.I_BOM	===>"+DatasetUtility.getString(dsCond,"I_BOM")	+"\n");
		System.out.print("\n@@@ dsCond.I_FR_FLR	===>"+DatasetUtility.getString(dsCond,"I_FR_FLR")	+"\n");
		System.out.print("\n@@@ dsCond.I_FR_PSN	===>"+DatasetUtility.getString(dsCond,"I_FR_PSN")	+"\n");
		System.out.print("\n@@@ dsCond.I_FR_SPD	===>"+DatasetUtility.getString(dsCond,"I_FR_SPD")	+"\n");
		System.out.print("\n@@@ dsCond.I_FR_VB	===>"+DatasetUtility.getString(dsCond,"I_FR_VB")	+"\n");
		
		System.out.print("\n@@@ dsCond.I_FR_YMD	===>"+DatasetUtility.getString(dsCond,"I_FR_YMD")	+"\n");
		System.out.print("\n@@@ dsCond.I_MOD	===>"+DatasetUtility.getString(dsCond,"I_MOD")	+"\n");
		System.out.print("\n@@@ dsCond.I_OPEN	===>"+DatasetUtility.getString(dsCond,"I_OPEN")	+"\n");
		System.out.print("\n@@@ dsCond.I_TM		===>"+DatasetUtility.getString(dsCond,"I_TM")	+"\n");
		System.out.print("\n@@@ dsCond.I_TO_FLR	===>"+DatasetUtility.getString(dsCond,"I_TO_FLR")	+"\n");
		
		System.out.print("\n@@@ dsCond.I_TO_PSN	===>"+DatasetUtility.getString(dsCond,"I_TO_PSN")	+"\n");
		System.out.print("\n@@@ dsCond.I_TO_SPD	===>"+DatasetUtility.getString(dsCond,"I_TO_SPD")	+"\n");
		System.out.print("\n@@@ dsCond.I_TO_YMD	===>"+DatasetUtility.getString(dsCond,"I_TO_YMD")	+"\n");  
		System.out.print("\n@@@ dsCond.I_USE	===>"+DatasetUtility.getString(dsCond,"I_USE")	+"\n");  
		//--------------------------------------------------------------------------------------------
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "I_BOM")  			// SO��ȣ
				 ,DatasetUtility.getString(dsCond, "I_FR_FLR")  		// ȣ��
				 ,DatasetUtility.getString(dsCond, "I_FR_PSN" )   		// ����� 
				 ,DatasetUtility.getString(dsCond, "I_FR_SPD" )  		// ��
				 ,DatasetUtility.getString(dsCond, "I_FR_VB" )  		// ����ó
				 
				 ,DatasetUtility.getString(dsCond, "I_FR_YMD")  		// ����� 
				 ,DatasetUtility.getString(dsCond, "I_MOD")  			// �����׷�
				 ,DatasetUtility.getString(dsCond, "I_OPEN")  			// ��������� 
				 ,DatasetUtility.getString(dsCond, "I_TM")   			// �������
				 ,DatasetUtility.getString(dsCond, "I_TO_FLR" )  		// ������ 
				 
				 ,DatasetUtility.getString(dsCond, "I_TO_PSN" ) 		// ���ֱݾ�
				 ,DatasetUtility.getString(dsCond, "I_TO_SPD")  		// ����
				 ,DatasetUtility.getString(dsCond, "I_TO_YMD")  		// ���
				 ,DatasetUtility.getString(dsCond, "I_USE")  			// ����
				 
				, listMsg
				, list
		}; 
		 
		try { 

			System.out.print("\n@@@ ZWEB_SD_CHN_189 start" + "\n");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sco.domain.ZWEB_SD_CHN_189", obj, false);	

			// RFC ��±���ü�� out dataset ����
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0033.getDataset();
			ds.setId("ds_list");  
			
			// RFC ȣ������ out dataset�� �ű��  
			CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			 
			System.out.print("\n@@@ ZWEB_SD_CHN_189 SUCCESS!!!" + "\n");
			
		} catch (CommRfcException e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_189 CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (Exception e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_189 Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	 
		
		// �����ͰǼ� INFO
		System.out.print("\n@@@ ds_list.getRowCount ===>" + ds.getRowCount() + "\n");
		
		// RFC ��� dataset�� return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds); 
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
}
