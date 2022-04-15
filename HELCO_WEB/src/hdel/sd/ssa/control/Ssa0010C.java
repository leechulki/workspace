package hdel.sd.ssa.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ssa.domain.TLINE;
import hdel.sd.ssa.domain.ZSDS0043;
import hdel.sd.ssa.domain.ZSDS0047;
import hdel.sd.ssa.domain.ZSDS0073;
import hdel.sd.ssa.domain.ZSDS0078;
import hdel.sd.ssa.domain.ZSDS0051;
import hdel.sd.ssa.domain.ZSDS0053;
import hdel.sd.ssa.domain.ZSDS0054;
import hdel.sd.ssa.domain.ZSDS0055;
import hdel.sd.ssa.service.Ssa0010S;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

@Controller
@RequestMapping("ssa0010")
public class Ssa0010C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ssa0010S service;
	
	@RequestMapping(value="find")
	public View Ssa0010FindView(MipParameterVO paramVO, Model model) {
		
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0043[] 	list 	= new ZSDS0043[]{};
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;	// UI�� return�� out dataset
		Dataset 	dsError = null;	// UI�� return�� �����޼��� dataset
		
		Object obj[] = new Object[]{		// RFC INPUT SET
				  DatasetUtility.getString(dsCond, "FR_AUART" )
				 ,DatasetUtility.getString(dsCond, "FR_EMPL" )
				 ,DatasetUtility.getString(dsCond, "FR_KUNZ1")
				 ,DatasetUtility.getString(dsCond, "FR_PAYER")
				 ,DatasetUtility.getString(dsCond, "FR_SO")
				 ,DatasetUtility.getString(dsCond, "FR_SPART")
				 ,DatasetUtility.getString(dsCond, "FR_VB")
				 ,DatasetUtility.getString(dsCond, "FR_VG")
				 ,DatasetUtility.getString(dsCond, "FR_YMD")
				 ,DatasetUtility.getString(dsCond, "TO_AUART")
				 ,DatasetUtility.getString(dsCond, "TO_EMPL" )
				 ,DatasetUtility.getString(dsCond, "TO_KUNZ1")
				 ,DatasetUtility.getString(dsCond, "TO_PAYER")
				 ,DatasetUtility.getString(dsCond, "TO_SO")
				 ,DatasetUtility.getString(dsCond, "TO_SPART")
				 ,DatasetUtility.getString(dsCond, "TO_VB")
				 ,DatasetUtility.getString(dsCond, "TO_VG")
				 ,DatasetUtility.getString(dsCond, "TO_YMD")
				 , listMsg
				 , list
		}; 
		 
		try { 
//			System.out.print("\n@@@ ZWEB_SD_CHN_197 start" + "\n");
			
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ssa.domain.ZWEB_SD_CHN_197", obj, false); // RFC CALL

			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0043.getDataset(); // RFC ��±���ü�� out dataset ����
			ds.setId("ds_list");  
			
			CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB")); // RFC ȣ������ out dataset�� �ű��

			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);

			
		} catch (CommRfcException e) { 
			e.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		MipResultVO resultVO = new MipResultVO();		// RFC ��� dataset�� return
		resultVO.addDataset(ds); 
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	/****
	 * ����ȸ
	 ****/
	@RequestMapping(value="find_dtl")
	public View Ssa0010FindDtlView(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset ds_cs = paramVO.getDataset("ds_cs");
		Dataset ds_is 	= paramVO.getDataset("ds_is");
		
		ZSDS0047[]	Cs 	= (ZSDS0047[])moveDs2Obj(ds_cs, ZSDS0047.class, "", null);
		ZSDS0043[]	ls 	= (ZSDS0043[])CallSAPHdel.moveDs2Obj2(ds_is, ZSDS0043.class, ""); 
		
		ZSDS0047   listCs  = Cs[0];
		ZSDS0043   listIs  = ls[0];

		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		ZSDS0078[] 	 list0078 = new ZSDS0078[]{};
		TLINE[] 	     listTline = new TLINE[]{};
		 
		// OUTPUT DATASET DECLARE
		Dataset 	ds_itab  = null;	// UI�� return�� out dataset
		Dataset 	ds_itab2 = null;	// UI�� return�� out dataset 
		Dataset 	ds_error = null;	// UI�� return�� �����޼��� dataset

		Object obj[] = new Object[]{		// RFC INPUT SET
				   listCs
				 , "D"
				 , listIs
				 , paramVO.getVariable("G_USER_ID")
				 , listMsg
				 , list0078
				 , listTline
		}; 
		 
		try { 

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ssa.domain.ZWEB_SD_CHN_197_DT", obj, false);	// RFC CALL

			ds_cs = CallSAP.isJCO() ? new Dataset() : ZSDS0047.getDataset();	// RFC ��±���ü�� out dataset(ds) ����
			ds_cs.setId("ds_cs");
			
			ds_is = CallSAP.isJCO() ? new Dataset() : ZSDS0047.getDataset();	// RFC ��±���ü�� out dataset(ds) ����
			ds_is.setId("ds_is");
			
			ds_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0078.getDataset();	// RFC ��±���ü�� out dataset(ds) ����
			ds_itab.setId("ds_itab");

			ds_itab2 = CallSAP.isJCO() ? new Dataset() : TLINE.getDataset();
			ds_itab2.setId("ds_itab2");
			
			CallSAP.moveObj2Ds(ds_cs, stub.getOutput("CS_ITAB"));	
			CallSAP.moveObj2Ds(ds_is, stub.getOutput("IS_ITAB"));	
			CallSAP.moveObj2Ds(ds_itab, stub.getOutput("T_ITAB"));		// RFC ȣ����(T_ITAB,T_ITAB2)�� out dataset(ds)�� �ű��
			CallSAP.moveObj2Ds(ds_itab2, stub.getOutput("T_ITAB2"));

			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg);
			ds_error.setId("ds_error"); 

		} catch (CommRfcException e) { 
			e.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace();
		}

		MipResultVO resultVO = new MipResultVO();		// RFC ��� dataset�� return
		resultVO.addDataset(ds_cs);
		resultVO.addDataset(ds_is);
		resultVO.addDataset(ds_itab);
		resultVO.addDataset(ds_itab2);
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	@RequestMapping(value="print")
	public View Ssa0010PrintView(MipParameterVO paramVO, Model model) {

		Dataset ds_cs = paramVO.getDataset("ds_cs");		// INPUT DATASET GET
		
		ZSDS0047[]    list_cs   = (ZSDS0047[])moveDs2Obj(ds_cs, ZSDS0047.class, "", null); // (ZSDS0047[])CallSAPHdel.moveDs2Obj2(ds_cs, ZSDS0047.class, "");		// RFC INPUT PARAM DECLARE
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		ZSDS0053[] 	 list0053 = new ZSDS0053[]{};
		ZSDS0047   listCs  = list_cs[0];
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds_t_itab   = null;	// UI�� return�� out dataset
		Dataset 	ds_cs_itab = null;	// UI�� return�� out dataset
		Dataset 	ds_error   = null;	// UI�� return�� �����޼��� dataset

		String pldat = DatasetUtility.getString(ds_cs, "PLDAT"     ).substring(0,4) +  "-" + DatasetUtility.getString(ds_cs, "PLDAT"     ).substring(4,6)+"-"+DatasetUtility.getString(ds_cs, "PLDAT"     ).substring(6,8);
		
		Object obj[] = new Object[]{	// RFC INPUT SET
				listCs
				, DatasetUtility.getString(ds_cs, "DOCU"     )			//������ȣ
				, DatasetUtility.getString(ds_cs, "KUNNR"    )			//����ó�ڵ�
				, pldat															//��������
				, DatasetUtility.getString(ds_cs, "VAT_RATE" )			//�ΰ�����(�Ҽ���1�ڸ�)
				, listMsg
				, list0053
		};

		try { 
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ssa.domain.ZWEB_SD_CHN_197_PRT", obj, false);	// RFC CALL

			ds_t_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0053.getDataset();	// RFC ��±���ü�� out dataset(ds) ����
			ds_t_itab.setId("ds_itab3");
			
			ds_cs_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0051.getDataset();
			ds_cs_itab.setId("ds_itab4");

			CallSAP.moveObj2Ds(ds_t_itab  , stub.getOutput("T_ITAB" ));  // RFC ȣ����(T_ITAB)�� out dataset(ds)�� �ű��
			CallSAP.moveObj2Ds(ds_cs_itab, stub.getOutput("CS_ITAB"));

			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			ds_error = CallSAP.makeErrorInfo(listMsg);
			ds_error.setId("ds_error"); 

		} catch (CommRfcException e) { 
			e.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace();
		}

		MipResultVO resultVO = new MipResultVO();	// RFC ��� dataset�� return
		resultVO.addDataset(ds_t_itab );
		resultVO.addDataset(ds_cs_itab);
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	@RequestMapping(value="master")
	public View Ssa0010MasterView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsSave = paramVO.getDataset("ds_save" );
		Dataset dsCs    = paramVO.getDataset("ds_cs");
		Dataset dsError = paramVO.getDataset("ds_error");

		// OUTPUT DATASET DECLARE
		Dataset ds_cs_itab = null;

		// RFC INPUT PARAM DECLARE
		ZSDS0047[]    ls        = (ZSDS0047[])moveDs2Obj(dsCs, ZSDS0047.class, "", null);
		ZSDS0047     list0047 = ls[0];
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{}; // �����޼���

		MipResultVO resultVO = new MipResultVO();	// RFC ��� dataset�� return
		
		try {

			Object obj[] = new Object[] { 	                                           // RFC INPUT SET (�Ķ���� ��������)
										  list0047
										, DatasetUtility.getString(dsSave, "PROTP") // I_��������
										, DatasetUtility.getString(dsSave, "PSPID")  // I_������Ʈ��ȣ
										, listMsg 										  // IO_O_ERROR INFO
										};

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ssa.domain.ZWEB_SD_CHN_197_PSPID", obj, false); // RFC CALL

			ds_cs_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0047.getDataset();	// RFC ��±���ü�� out dataset(ds) ����
			ds_cs_itab.setId("ds_cs");

			CallSAP.moveObj2Ds(ds_cs_itab, stub.getOutput("CS_ITAB" ));  // RFC ȣ����(CS_ITAB)�� out dataset(ds)�� �ű��

			String e_type = stub.getOutput("E_TYPE").toString();		// e_type --> ó�����(0:����, 4:����) o_etab --> ó������ ����	
			if( e_type.equals("4")) {
				listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
				dsError = CallSAP.makeErrorInfo(listMsg);
				dsError.setId("ds_error");
				resultVO.addDataset(dsError); 
			}
			
			
		} catch (CommRfcException e) { 
			e.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace(); 
		}

		resultVO.addDataset(ds_cs_itab);
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	@RequestMapping(value="update")
	public View Ssa0010UpdateView(MipParameterVO paramVO, Model model) {
		// INPUT DATASET GET
		Dataset ds_save  = paramVO.getDataset("ds_save" );
		Dataset ds_cs     = paramVO.getDataset("ds_cs"    );
		Dataset ds_itab   = paramVO.getDataset("ds_itab"  );
		Dataset ds_itab2  = paramVO.getDataset("ds_itab2");

		// OUTPUT DATASET DECLARE 
		Dataset ds_error   = null;

		MipResultVO resultVO = new MipResultVO();	// RFC ��� dataset�� return
		
		// RFC INPUT PARAM DECLARE
		ZSDS0047[]    ls        = (ZSDS0047[])moveDs2Obj(ds_cs, ZSDS0047.class, "", null); 
		ZSDS0047     list0047 = ls[0];
		ZSDS0078[] 	 list0078 = (ZSDS0078[])CallSAPHdel.moveDs2Obj2(ds_itab , ZSDS0078.class, ""); // RFC input dataset�� CLASS(ZSDS0078) �� �ű��
		TLINE[] 	     listTline = (TLINE[]     )CallSAPHdel.moveDs2Obj2(ds_itab2, TLINE.class     , ""); // RFC input dataset�� CLASS(TLINE     ) �� �ű��
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{}; // �����޼���
		
		try { 
			Object obj[] = new Object[] { 	                                           // RFC INPUT SET (�Ķ���� ��������)
										  list0047
										, DatasetUtility.getString(ds_save, "GUBUN") // I_����(D:����, S:����)
										, DatasetUtility.getString(ds_save, "UNAME") // I_�۾������
										, listMsg 											// IO_O_ERROR INFO
										, list0078                                         // IO_������ : ZSDS0078
										, listTline
										};

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ssa.domain.ZWEB_SD_CHN_197_SAVE", obj, false); // RFC CALL

			ds_cs = CallSAP.isJCO() ? new Dataset() : ZSDS0047.getDataset();	// RFC ��±���ü�� out dataset(ds) ����
			ds_cs.setId("ds_cs");

			ds_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0078.getDataset();
			ds_itab.setId("ds_itab");

			ds_itab2 = CallSAP.isJCO() ? new Dataset() : TLINE.getDataset();
			ds_itab2.setId("ds_itab2");
			
			CallSAP.moveObj2Ds(ds_cs, stub.getOutput("CS_ITAB" ));  // RFC ȣ����(T_ITAB)�� out dataset(ds)�� �ű��
			CallSAP.moveObj2Ds(ds_itab    , stub.getOutput("T_ITAB"));
			CallSAP.moveObj2Ds(ds_itab2   , stub.getOutput("T_ITAB2"));

			String e_type = stub.getOutput("E_TYPE").toString();		// e_type --> ó�����(0:����, 4:����) o_etab --> ó������ ����	
			if( e_type.equals("4") ) {									
				listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
				ds_error = CallSAP.makeErrorInfo(listMsg);
				
				ds_error.setId("ds_error");
				resultVO.addDataset(ds_error); 	// �����������
			}

			
		} catch (CommRfcException e) { 
			ds_error = CallSAP.makeErrorInfo(listMsg);			
			ds_error.setId("ds_error");
			resultVO.addDataset(ds_error); 
			e.printStackTrace(); 
		} catch (Exception e) { 
			ds_error = CallSAP.makeErrorInfo(listMsg);			
			ds_error.setId("ds_error");
			resultVO.addDataset(ds_error); 
			e.printStackTrace(); 
		}

		resultVO.addDataset(ds_cs);
		resultVO.addDataset(ds_itab    );
		resultVO.addDataset(ds_itab2  );
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	@RequestMapping(value="receipt")
	public View Ssa0010ReceiptView(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset dsSave  = paramVO.getDataset("ds_save");
		Dataset dsItab   = paramVO.getDataset("ds_itab" );

		// OUTPUT DATASET DECLARE 
		Dataset dsError = null;
		Dataset ds_itab  = null;

		MipResultVO resultVO = new MipResultVO();	// RFC ��� dataset�� return

		// RFC INPUT PARAM DECLARE
		ZSDS0055[] 	 list0055 = (ZSDS0055[])CallSAPHdel.moveDs2Obj2(dsItab , ZSDS0055.class, ""); // RFC input dataset�� CLASS(ZSDS0055) �� �ű��
		ZQMSEMSG[] listMsg  = new ZQMSEMSG[]{}; // �����޼���
		ZQMSEMSG[] listMsg2 = new ZQMSEMSG[]{}; // �����޼���

		try { 
			Object obj[] = new Object[] { 	                                           // RFC INPUT SET (�Ķ���� ��������)
										  DatasetUtility.getString(dsSave, "UNAME") // I_�۾������
										, listMsg 											// IO_O_ERROR INFO
										, listMsg2											// IO_O_ERROR INFO
										, list0055                                         // IO_��� : ZSDS0055
										};

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ssa.domain.ZWEB_SD_CHN_197_SUBMIT", obj, false); // RFC CALL

			ds_itab = CallSAP.isJCO() ? new Dataset() : ZSDS0055.getDataset();
			ds_itab.setId("ds_itab");

			CallSAP.moveObj2Ds(ds_itab, stub.getOutput("T_ITAB")); // RFC ȣ����(T_ITAB)�� out dataset(ds)�� �ű��

			String e_type = stub.getOutput("E_TYPE").toString();		// e_type --> ó�����(0:����, 4:����) o_etab --> ó������ ����	
			if(e_type.equals("4") ) {	
				listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
				dsError = CallSAP.makeErrorInfo(listMsg);
				dsError.setId("ds_error");
				resultVO.addDataset(dsError); 	// �����������
			}

			System.out.println("@@@ ZWEB_SD_CHN_197_SUBMIT SUCCESS!!!" + "");
			
		} catch (CommRfcException e) { 
			System.out.println("@@@ ZWEB_SD_CHN_197_SUBMIT CommRfcException ERROR!!!" + "");
			e.printStackTrace(); 
		} catch (Exception e) { 
			System.out.println("@@@ ZWEB_SD_CHN_197_SUBMIT Exception ERROR!!!" + "");
			e.printStackTrace(); 
		}
		System.out.println("@@@ ds_itab:"+ds_itab.getRowCount());

		resultVO.addDataset(ds_itab);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	
	@RequestMapping(value="approval")
	public View Ssa0010ApprovalView(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset dsSave      = paramVO.getDataset("ds_save"     );
		Dataset dsApproval = paramVO.getDataset("ds_approval");

		// OUTPUT DATASET DECLARE
		Dataset dsError       = null;
		Dataset ds_approval = null;

		MipResultVO resultVO = new MipResultVO();	// RFC ��� dataset�� return


		// RFC INPUT PARAM DECLARE
		ZSDS0054[]    ls        = (ZSDS0054[])CallSAPHdel.moveDs2Obj2(dsApproval, ZSDS0054.class, "", null); 
		ZSDS0054     list0054 = ls[0];
		ZQMSEMSG[] listMsg  = new ZQMSEMSG[]{}; // �����޼���

		try { 
			Object obj[] = new Object[] { 	                                           // RFC INPUT SET (�Ķ���� ��������)
										  list0054
										, DatasetUtility.getString(dsSave, "DOCU"  ) // I_������ȣ
										, DatasetUtility.getString(dsSave, "UNAME") // I_�۾������
										, listMsg 											// IO_O_ERROR INFO
										};

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ssa.domain.ZWEB_SD_CHN_197_APPV", obj, false); // RFC CALL

			ds_approval = CallSAP.isJCO() ? new Dataset() : ZSDS0054.getDataset();
			ds_approval.setId("ds_approval");

			CallSAP.moveObj2Ds(ds_approval, stub.getOutput("CS_ITAB")); // RFC ȣ����(T_ITAB)�� out dataset(ds)�� �ű��

			String e_type = stub.getOutput("E_TYPE").toString();		// e_type --> ó�����(0:����, 4:����) o_etab --> ó������ ����	
			if( e_type.equals("4")) {	
				listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
				dsError = CallSAP.makeErrorInfo(listMsg);
				dsError.setId("ds_error");
				resultVO.addDataset(dsError); 	// �����������
			}

			
		} catch (CommRfcException e) { 
			e.printStackTrace(); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		}

		resultVO.addDataset(ds_approval);
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	@RequestMapping(value="cancel")
	public View Ssa0010CancelView(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset dsSave    = paramVO.getDataset("ds_save"  );
		Dataset dsCancel = paramVO.getDataset("ds_approval");

		// OUTPUT DATASET DECLARE
		Dataset dsError       = null;
		Dataset ds_approval = null;

		MipResultVO resultVO = new MipResultVO();	// RFC ��� dataset�� return

		// RFC INPUT PARAM DECLARE
		ZSDS0054[]    ls        = (ZSDS0054[])CallSAPHdel.moveDs2Obj2(dsCancel, ZSDS0054.class, "", null);
		ZSDS0054     list0054 = ls[0];
		ZQMSEMSG[] listMsg  = new ZQMSEMSG[]{}; // �����޼���

		try { 
			Object obj[] = new Object[] { 	                                           // RFC INPUT SET (�Ķ���� ��������)
										  list0054
										, DatasetUtility.getString(dsSave, "DOCU"  ) // I_������ȣ
										, DatasetUtility.getString(dsSave, "UNAME") // I_�۾������
										, listMsg 											// IO_O_ERROR INFO
										};

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.ssa.domain.ZWEB_SD_CHN_197_ACAN", obj, false); // RFC CALL

			ds_approval = CallSAP.isJCO() ? new Dataset() : ZSDS0054.getDataset();
			ds_approval.setId("ds_approval");

			CallSAP.moveObj2Ds(ds_approval, stub.getOutput("CS_ITAB")); // RFC ȣ����(T_ITAB)�� out dataset(ds)�� �ű��
			
			String e_type = stub.getOutput("E_TYPE").toString();		// e_type --> ó�����(0:����, 4:����) o_etab --> ó������ ����	
			if( e_type.equals("4")) {	
				listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
				dsError = CallSAP.makeErrorInfo(listMsg);
				dsError.setId("ds_error");
				resultVO.addDataset(dsError); 	// �����������
			}

			
		} catch (CommRfcException e) { 
			e.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace();
		}

		resultVO.addDataset(ds_approval);
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	/**
	 * Dataset�� �÷��� �������� �־��� Class �� �ش� ��ü�� �����Ͽ� ���� Move �Ѵ�. 
	 * �Ѱ��� �����͸� ��ü�� �ű� �� Ds2ObjHelper.endRow()�� ȣ���Ͽ� ó�� ���Ḧ �˸���. 
	 * 
	 * 2012.07.26 �߰� : moveDs2Ob(Dataset ds, Class cls, String flag, Ds2ObjHelper helper)j���� BigDecimal 3�ڸ��� ���εǸ鼭 �߻��ϴ� ���� ������ �ӽ÷� �߰�
	 * @param ds �����ͼ� 
	 * @param cls ó���� ��ü 
	 * @param flag �÷��� �÷� : TMODE�� ��Ī�Ǵ� �̸� (���� ��� ���� �ʿ� ����. )
	 * @return 
	 */	
	public static Object moveDs2Obj(Dataset ds, Class cls, String flag, Ds2ObjHelper helper) {
		if ( flag != null && !flag.equals("")) {
			for( int i = ds.getRowCount()-1; i >= 0; i--) {
				if ( DatasetUtility.getString(ds,i,flag, "").equals("") || DatasetUtility.getString(ds,i,flag, "").equals("0")) {	// Flag �÷� �ܴ̿� ����
					ds.deleteRow(i);
				}
			}
		}
		
		Object obj = Array.newInstance(cls, ds.getRowCount());	// �ش� Type���� �迭 ���� 
		Method[] mArr = cls.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			if ( mArr[i].getName().startsWith("set")) {			// Set �޼ҵ常 �������� 
				mData.put(mArr[i].getName().substring(3), mArr[i]);
			}
		}
		Object tmpObj = null;
		Method m = null;
		Class setClass = null;
		BigDecimal b = null; 
		
		for( int i = 0; i < ds.getRowCount(); i++ ) {
			try {
				tmpObj = cls.newInstance();
				
				for( int c = 0; c < ds.getColumnCount(); c++) {
					m = (Method) mData.get(ds.getColumnID(c));
					if ( m != null ) {
						setClass = m.getParameterTypes()[0];
						if ( setClass.getName().equals("int")){
							m.invoke(tmpObj, DatasetUtility.getInt(ds,i, ds.getColumnID(c)));
						} else if ( setClass.getName().equals("java.math.BigDecimal")){
							
							b = new BigDecimal(DatasetUtility.getDbl(ds, i, ds.getColumnID(c), 0));

							if (ds.getColumnID(c).equals("KU_ZQNTY")) b = b.setScale(0, RoundingMode.DOWN);
							else 							                    b = b.setScale(0, RoundingMode.DOWN);		//2012.12.24

							m.invoke(tmpObj, b); 							
						} else { 
							m.invoke(tmpObj, DatasetUtility.getString(ds, i, ds.getColumnID(c)));
						}
					}
				}
				
				if ( !flag.equals("TMODE")) {
					m = (Method) mData.get("TMODE");
					if ( m != null ) {
						setClass = m.getParameterTypes()[0];
						m.invoke(tmpObj, ds.getColumnAsString(i, flag));
					}
				}
				
				if ( helper != null ) {	// ó�� ���� �˸�. 
					helper.endMoveRow(ds, i, tmpObj);
				}
			} catch( Exception e){
				e.printStackTrace();
			}
			
			Array.set(obj, i, tmpObj);	// Array ���� ���� 
		}
		return obj;
	}
}
