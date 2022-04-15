package hdel.sd.sso.control;

/**
 * ���ֺ��� - ǰ���簪 �ϰ�����
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ϴܿ� ǥ��
 */
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.ses.domain.ZSDS0016;
import hdel.sd.ses.domain.ZSDS0017;
import hdel.sd.smp.control.SmpComC;
import hdel.sd.sso.domain.Sso0051;
import hdel.sd.sso.domain.Sso0051ParamVO;
import hdel.sd.sso.service.Sso0051S;

@Controller
@RequestMapping("sso0051")
public class Sso0051C extends CallSAP{
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sso0051S service;  
	
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
		logger.info("@@@@@@@@@ ���ֺ��� control in @@@@@@@@@@@@@@");
		logger.info(" @@@@@ pspId :"+i_pspHogi);
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0016[] list_ZSDS0016			= new ZSDS0016[]{};  // sap output list
		ZSDS0017[] list_ZSDS0017			= new ZSDS0017[]{};  // sap output list
		ZQMSEMSG[] listMsg  				= new ZQMSEMSG[]{};  // sap �����޽��� return��
		logger.info("@@@@@@@@ ZSDS0016 list : "+ list_ZSDS0016.toString());
		logger.info("@@@@@@@@ ZSDS0017 list : "+ list_ZSDS0017.toString());
		logger.info("@@@@@@@@ ZQMSEMSG list : "+ listMsg.toString());
		
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
		
		logger.info("@@@@@@@ ds_output_ZSDS0016.toString ===>" + ds_output_ZSDS0016.toString());
		logger.info("@@@@@@@ ds_output_ZSDS0017.toString ===>" + ds_output_ZSDS0017.toString());
				
		resultVO.addDataset(ds_output_ZSDS0016); 			// ó���������
		resultVO.addDataset(ds_output_ZSDS0017); 			// ó���������
		model.addAttribute("resultVO", resultVO);  
		return view;
	}

	// ��ຯ�� ȭ�鿡�� �Էµ� ȣ���� ����� ��ȸ  20130307 �輱ȣ 
	@RequestMapping(value="findQ")
	public View findQ(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		String i_pspHogi 	= paramVO.getVariable("pspHogi"); // ȣ���ȣ
		String i_seq        = paramVO.getVariable("fa_seq"); // ���� ����  

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_output_ZSDS0016");	// UI�� return�� out dataset 

		// �������� DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO(); 			// ��� dataset�� return
		Sso0051ParamVO param = new Sso0051ParamVO();

		param.setMANDT(paramVO.getVariable("G_MANDT"));  								// ����_SAP CLIENT  
		param.setHOGI(i_pspHogi);        //ȣ��
		param.setSEQ(i_seq);             //���� 
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0016[] list_ZSDS0016			= new ZSDS0016[]{};  // sap output list
		ZSDS0017[] list_ZSDS0017			= new ZSDS0017[]{};  // sap output list
		ZQMSEMSG[] listMsg  				= new ZQMSEMSG[]{};  // sap �����޽��� return��
		
		
		try { 
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			List<Sso0051> list = service.selectListSpec(param);
			for ( int i = 0 ; i < list.size(); i++ ) {
				dsList.appendRow();
				dsList.setColumn(i, "NAM_CHAR"       , list.get(i).getNAM_CHAR());
				dsList.setColumn(i, "ATBEZ"       , list.get(i).getATBEZ());
				dsList.setColumn(i, "VALUE1"       , list.get(i).getVALUE1());
				dsList.setColumn(i, "ATWTB1"       , list.get(i).getATWTB1());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}
		resultVO.addDataset(dsList);
		
		System.out.print("\n@@@ dsList Row Count ==>"+dsList.getRowCount()   +"\n");
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
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

		// INPUT DATASET GET
		Dataset ds_ZSDS0016_input 	= paramVO.getDataset("ds_ZSDS0016_input"); 	// ������ ���
		Dataset ds_ZSDT0091_input 	= paramVO.getDataset("ds_ZSDT0091_input"); 	// ������ ��󸮽�Ʈ
		Dataset ds_ZSDT0094_input 	= paramVO.getDataset("ds_ZSDT0094_input"); 	// ������ ��󸮽�Ʈ�� ����
		String flag 				= paramVO.getVariable("flag"); 				// ���� ����
		Dataset ds_result = new Dataset();
		
		logger.info("@@@@@@@@@ ���ֺ��� ��纹�� control in @@@@@@@@@@@@@@");
		//logger.info(" @@@@@ flag :"+flag);
		//logger.info(" @@@@@ ds_ZSDS0016_input :"+ds_ZSDS0016_input.getRowCount());
		//logger.info(" @@@@@ ds_ZSDT0091_input :"+ds_ZSDT0091_input.getRowCount());
		//logger.info(" @@@@@ ds_ZSDT0094_input :"+ds_ZSDT0094_input.getRowCount());
		//logger.info(" @@@@@ ds_ZSDS0016_input :"+ds_ZSDS0016_input.toString());
		//logger.info(" @@@@@ ds_ZSDT0091_input :"+ds_ZSDT0091_input.toString());
		//logger.info(" @@@@@ ds_ZSDT0094_input :"+ds_ZSDT0094_input.toString());

		//flag = 1 �̸� �κк���
		//flag = 2 �̸� ��ü����
		try{	
			int match = 0;
			int ZSDS0016_cnt = 0;
			String[] matchCount = new String[ds_ZSDS0016_input.getRowCount()];
			
			for ( int a = 0 ; a < ds_ZSDS0016_input.getRowCount() ; a++ )
			{
				matchCount[a] = "";
				//logger.info(" @@@@@ matchCount[a] :"+matchCount[a].toString());
			}
			
			// ������ ��� �ѰǼ�
			for ( int i = 0 ; i < ds_ZSDS0016_input.getRowCount() ; i++ )
			{
				// ������ ����Ѱ� �� üũ�� �� �ο��ϰ��
				if ( "1".equals( ds_ZSDS0016_input.getColumnAsObject(i, "CHK") ) )
				{
					ZSDS0016_cnt++;
					
					// ������ ��󸮽�Ʈ �� �Ǽ�
					for ( int j = 0 ; j < ds_ZSDT0091_input.getRowCount() ; j++ )
					{
						
						// ������ ��츮��Ʈ �� ���� üũ�� �� ����Ʈ�� ���
						if ( "1".equals( ds_ZSDT0091_input.getColumnAsObject(j, "CHK") ) )
						{
							String nam_char = ds_ZSDS0016_input.getColumnAsString(i, "NAM_CHAR");	// Ư��
							String atbez 	= ds_ZSDS0016_input.getColumnAsString(i, "ATBEZ");		// Ư������
							String value1 	= ds_ZSDS0016_input.getColumnAsString(i, "VALUE1");		// Ư����
							String atwtb1 	= ds_ZSDS0016_input.getColumnAsString(i, "ATWTB1");		// Ư���� ����
							
							String posnr	= ds_ZSDT0091_input.getColumnAsString(j, "POSNR"); 		// ǰ���ȣ
							String matnr 	= ds_ZSDT0091_input.getColumnAsString(j, "MATNR"); 		// �����ȣ
						
							//logger.info(" ds_output_ZSDT0094_change cnt :"+ds_ZSDT0094_input.getRowCount());
							//logger.info(" 16 POSNR :"+posnr);
							//logger.info(" 16 NAM_CHAR :"+nam_char);
							
							// ó����� �޽�����
							matchCount[i] = nam_char;
							
							// ������ ��� ����Ʈ�� �ش��ϴ� ���Ǽ� ��
							for ( int k = 0 ; k < ds_ZSDT0094_input.getRowCount() ; k++ )
							{
								// [�Ǹ�ǰ��]�� �����ϰ�
								if( posnr.equals( ds_ZSDT0094_input.getColumnAsObject(k, "POSNR") ) )
								{
	
									
									// [Ư��]�� �����ϴٸ�
									if( nam_char.equals( ds_ZSDT0094_input.getColumnAsObject(k, "NAM_CHAR") ) )
									{
										//logger.info( ds_ZSDT0094_input.getColumnAsObject(k, "NAM_CHAR") );
										//logger.info( nam_char );
										
										// ó����� �޽�����
										matchCount[i] = "Y";//","+(String)ds_ZSDT0094_input.getColumnAsObject(k, "NAM_CHAR");
										match++;
										//logger.info(" @@@ matchCount : ["+i+"] : "+ matchCount[i].toString() );
										
										// �ش������� ����
										// 2013.03.04 STATUS �÷� �߰��� STATUS ����
										String strCharValue = ds_ZSDT0094_input.getColumnAsString(k, "CHAR_VALUE");
										String strAtwtb     = ds_ZSDT0094_input.getColumnAsString(k, "ATWTB");
										String strStatus    = ds_ZSDT0094_input.getColumnAsString(k, "STATUS");
										
										if ( strCharValue == null ) strCharValue = "";
										if ( strAtwtb     == null ) strAtwtb     = "";
										if ( strStatus    == null ) strStatus    = "";
										
										if ( !strCharValue.equals(value1) || !strAtwtb.equals(atwtb1) ) {
											if ( !strStatus.equals("insert") ) {
												ds_ZSDT0094_input.setColumn(k, "STATUS", "update");
											}
										}
										
										ds_ZSDT0094_input.setColumn(k, "CHAR_VALUE", value1 );	// Ư����
										ds_ZSDT0094_input.setColumn(k, "ATWTB", atwtb1 );		// Ư���� ����
										
									}										
									/*else
									{
										// �κк���
										if ( "1".equals(flag) )
										{
										}
										// ��ü����
										else if ( "2".equals(flag) )
										{
											String is_spec = "";
											// �ش������� ����
											for ( int ii = 0 ; ii < ds_ZSDS0016_input.getRowCount() ; ii++ )
											{
												nam_char = ds_ZSDS0016_input.getColumnAsString(ii, "NAM_CHAR");	// Ư��
												if( nam_char.equals( ds_ZSDT0094_input.getColumnAsObject(k, "NAM_CHAR") ) )
												{	
													is_spec = "Y";
												}
											}	
											if ( is_spec != "Y" )
											{
												// 2013.03.04 STATUS �÷� �߰��� STATUS ����
												String strCharValue = ds_ZSDT0094_input.getColumnAsString(k, "CHAR_VALUE");
												String strAtwtb     = ds_ZSDT0094_input.getColumnAsString(k, "ATWTB");
												String strStatus    = ds_ZSDT0094_input.getColumnAsString(k, "STATUS");
												
												if ( strCharValue == null ) strCharValue = "";
												if ( strAtwtb     == null ) strAtwtb     = "";
												if ( strStatus    == null ) strStatus    = "";
												
												if ( !strCharValue.equals(value1) || !strAtwtb.equals(atwtb1) ) {
													if ( !strStatus.equals("insert") ) {
														ds_ZSDT0094_input.setColumn(k, "STATUS", "update");
													}
												}
												
												ds_ZSDT0094_input.setColumn(k, "CHAR_VALUE", "" );	// Ư����
												ds_ZSDT0094_input.setColumn(k, "ATWTB", "" );		// Ư���� ����
											}											
											
										}
										
									}*/
									
								}
							}
							//logger.info(" 1���� �ƴҽ� ������� : "+ds_ZSDT0094_input.getRowCount());
							
						}
					}
				}
			}
	        
			//TEST STARTTTTTTT
			if ( "2".equals(flag) )
			{
						for ( int i = 0 ; i < ds_ZSDS0016_input.getRowCount() ; i++ )
						{
							// ������ ����Ѱ� �� üũ�� �� �ο��ϰ��
							if ( "1".equals( ds_ZSDS0016_input.getColumnAsObject(i, "CHK") ) )
							{					
								// ������ ��󸮽�Ʈ �� �Ǽ�
								for ( int j = 0 ; j < ds_ZSDT0091_input.getRowCount() ; j++ )
								{						
									// ������ ��츮��Ʈ �� ���� üũ�� �� ����Ʈ�� ���
									if ( "1".equals( ds_ZSDT0091_input.getColumnAsObject(j, "CHK") ) )
									{
										String nam_char = ds_ZSDS0016_input.getColumnAsString(i, "NAM_CHAR");	// Ư��
										String atbez 	= ds_ZSDS0016_input.getColumnAsString(i, "ATBEZ");		// Ư������
										String value1 	= ds_ZSDS0016_input.getColumnAsString(i, "VALUE1");		// Ư����
										String atwtb1 	= ds_ZSDS0016_input.getColumnAsString(i, "ATWTB1");		// Ư���� ����
										
										String posnr	= ds_ZSDT0091_input.getColumnAsString(j, "POSNR"); 		// ǰ���ȣ
										
										// ������ ��� ����Ʈ�� �ش��ϴ� ���Ǽ� ��
										for ( int k = 0 ; k < ds_ZSDT0094_input.getRowCount() ; k++ )
										{
											// [�Ǹ�ǰ��]�� �����ϰ�
											if( posnr.equals( ds_ZSDT0094_input.getColumnAsObject(k, "POSNR") ) )
											{
												//if ( "2".equals(flag) )
												//{
													String is_spec = "";
													// �ش������� ����
													for ( int ii = 0 ; ii < ds_ZSDS0016_input.getRowCount() ; ii++ )
													{
														nam_char = ds_ZSDS0016_input.getColumnAsString(ii, "NAM_CHAR");	// Ư��
														if( nam_char.equals( ds_ZSDT0094_input.getColumnAsObject(k, "NAM_CHAR") ) )
														{	
															is_spec = "Y";
														}
													}	
													if ( is_spec != "Y" )
													{
														// 2013.03.04 STATUS �÷� �߰��� STATUS ����
														String strCharValue = ds_ZSDT0094_input.getColumnAsString(k, "CHAR_VALUE");
														String strAtwtb     = ds_ZSDT0094_input.getColumnAsString(k, "ATWTB");
														String strStatus    = ds_ZSDT0094_input.getColumnAsString(k, "STATUS");
														
														if ( strCharValue == null ) strCharValue = "";
														if ( strAtwtb     == null ) strAtwtb     = "";
														if ( strStatus    == null ) strStatus    = "";
														
														if ( !strCharValue.equals(value1) || !strAtwtb.equals(atwtb1) ) {
															if ( !strStatus.equals("insert") ) {
																ds_ZSDT0094_input.setColumn(k, "STATUS", "update");
															}
														}
														
														ds_ZSDT0094_input.setColumn(k, "CHAR_VALUE", "" );	// Ư����
														ds_ZSDT0094_input.setColumn(k, "ATWTB", "" );		// Ư���� ����
													}											
													
												//}	
																					
											}
										}
										//logger.info(" 1���� �ƴҽ� ������� : "+ds_ZSDT0094_input.getRowCount());
										
									}
								}
							}
						}
			}			
			//TEST ENDDDDDDDDD
			
			//logger.info(" ds_ZSDT0094_input.toString() : "+ds_ZSDT0094_input.toString());
			//logger.info("@@@@@@@ end !!!");
	
			//logger.info(" ZSDS0016_cnt : "+ZSDS0016_cnt);
			//logger.info(" matchCount.length : "+matchCount.length);

			// ó����� �� ������ Ư�� ����Ʈ
			String failVal = "";
			for ( int a = 0 ; a < ZSDS0016_cnt ; a++ )
			{
				//logger.info(" @@@ : "+matchCount[0].toString());
				if ( !matchCount[a].toString().equals("Y") && matchCount[a].toString() != null )
				{
					failVal += " "+matchCount[a].toString();
				}
			}
			
			//logger.info(" �� �Ǽ� : "+ZSDS0016_cnt);
			//logger.info(" �ݿ��Ǽ� : "+match);
			//logger.info(" ������ Ư�� : "+failVal);
			
			String result_msg = ""+ZSDS0016_cnt ;
			result_msg += "," + match;
			result_msg += "," + failVal;
			
			ds_result.setId("ds_result");
			ds_result.addColumn("RESULT", ColumnInfo.COLTYPE_STRING, 256);
			ds_result.appendRow();
			ds_result.setColumn(0, "RESULT", result_msg);
	
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
		
		// miplatform���� return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds_ZSDT0094_input); 			// ó���������
		resultVO.addDataset(ds_result); 					// ó���������
		model.addAttribute("resultVO", resultVO);  
		return view;
		
		
	}
	
}
