package com.helco.xf.cs06.ws;
 
import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;


import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs06.ws.ZSDS0075;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.ws.CS1201001B_Service;
import com.helco.xf.wb01.batch.FileFilter;
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.data.Dataset;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.biz.BusinessException;
import tit.service.business.ServiceFactoryManager;
import tit.service.mail.MailSendService;
import tit.service.mail.MailSender;
import tit.service.resource.TransactionManager;
import tit.service.sqlmap.SimpleSqlExecutor;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

public class CS0609001A_ACT extends AbstractAction {

	public static final String ZWEB_SD_INSURANCE
		= "com.helco.xf.cs06.ws.ZWEB_SD_INSURANCE";

	public static boolean flag = false;
	private static final String PATH1 =  "/srm/HELCO_WEB/HELCO_WEB.war/upload/CS/";
	private static final String HOST  = "smtp.hdel.co.kr";
	private static final int    PORT  = 25;	
	/**
	 * ����
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsList = ctx.getInputDataset("ds_list");
		Dataset dsError = null;
		SapFunction stub = null;
		
		// �Է� �Ķ���� ó�� 
		if ( CallSAP.isJCO() ) {
			dsError = new Dataset("ds_error");						
			//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{dsList});
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{dsList}, false);		 // ����  SAP ����
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{dsList}, false);  // EAI ����


			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			ZSDS0075[] list = (ZSDS0075[])
					CallSAP.moveDs2Obj(dsList, ZSDS0075.class, "FLAG");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			
			// ���� ó�� 
			//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{msgList, list});
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{msgList, list}, false);		 // ����  SAP ����
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{msgList, list}, false);  // EAI ����
			
			msgList = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo( msgList );
		}

		ctx.addOutputDataset(dsError);

	}
	public void query(BusinessContext ctx) throws Exception {
		
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
		
		DatasetSqlRequest zcst111s = SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0606001B_S1");
		zcst111s.addParamObject("ds_list", ds_list);
		zcst111s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		DatasetSqlRequest zcst107s = SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0606001B_S2");
		zcst107s.addParamObject("ds_list", ds_list);
		zcst107s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst107s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		for(int i=0; i<ds_list.getRowCount(); i++) 
		{
			zcst111s.setRowIndex(i);
			zcst111s.addParamObject("PJT", ds_list.getColumnAsObject(i, "PJT"));
			zcst111s.addParamObject("HNO", ds_list.getColumnAsObject(i, "HNO"));
			
			Dataset dsRtn111 = (Dataset)executor.query(zcst111s).getResultObject();
			
			if(dsRtn111.getRowCount() > 0) { //���� ������ ��� ���� Ȯ��
				ds_list.setColumn(i, "FAIL", "");
			} else {
				ds_list.setColumn(i, "FAIL", "Y");
				ds_list.setColumn(i, "FAIL_TXT", "�� �μ�");
			}
			
			zcst107s.setRowIndex(i);
			zcst107s.addParamObject("PJT", ds_list.getColumnAsObject(i, "PJT"));
			zcst107s.addParamObject("HNO", ds_list.getColumnAsObject(i, "HNO"));
			
			Dataset dsRtn107 = (Dataset)executor.query(zcst107s).getResultObject();
			
			if(dsRtn107.getRowCount() > 0) { //������ ��� ���� Ȯ��
				ds_list.setColumn(i, "FAIL", "Y");
				ds_list.setColumn(i, "FAIL_TXT", "������ ��� ���� ��");
			} 
		}
		
		ctx.addOutputDataset(ds_list);
		
	}

	public void save2(BusinessContext ctx) throws Exception {
	
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_list = ctx.getInputDataset("ds_list");

		DatasetSqlRequest zcst107i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0605001B_I1");
		zcst107i.addParamObject("ds_list", ds_list);
		zcst107i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst107i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		DatasetSqlRequest zcst108i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0605001B_I2");
		zcst108i.addParamObject("ds_list", ds_list);
		zcst108i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst108i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
				
		for(int i=0; i<ds_list.getRowCount(); i++) {
			zcst107i.setRowIndex(i);
			executor.execute(zcst107i);
			
			zcst108i.setRowIndex(i);
			executor.execute(zcst108i);
			ds_list.setColumn(i, "RESULT", "���强��");
		}
		ctx.addOutputDataset(ds_list);
	}

	public void sendMailToPM(BusinessContext ctx) throws Exception 
	{
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor =  new DatasetSqlExecutor(getConnection(db_con));
		//mail ���� �̷� 
		DatasetSqlRequest zcstMailLogi
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:MAIL_LOG");

		Dataset ds_header = ctx.getInputDataset("ds_list_h");
		Dataset ds_detail = ctx.getInputDataset("ds_list");
		Dataset ds_MailRvcList = ctx.getInputDataset("ds_MailRvcList");

		
		/* ���� */
		String  recipient = ctx.getInputVariable("RECE_USER"); //������ 
		Address fromAdr   = new InternetAddress(ctx.getInputVariable("SEND_USER")); //�߽���
		String  subject ; //����
		String  fpath ;   //÷������ ���
		
		String h_pjt    = ds_header.getColumnAsString(0, "CS101_PJT");
		String h_hno    = ds_header.getColumnAsString(0, "CS101_HNO");
		String h_hna    = ds_header.getColumnAsString(0, "CS101_HNA");
		String zsite_nm = ds_header.getColumnAsString(0,"ZSITE_NM");
		String kunnr_nm = ds_header.getColumnAsString(0,"KUNNR_NM");
		String pst_nm   = ds_header.getColumnAsString(0,"CS107_PST_NM");
		
		zcstMailLogi.addParamObject("G_MANDT", mdt);
		zcstMailLogi.addParamObject("PJT", h_pjt);
		zcstMailLogi.addParamObject("HNO", h_hno);				

		if(ds_MailRvcList.getRowCount() == 0) {
			zcstMailLogi.setRowIndex(0);
			executor.execute(zcstMailLogi);			
		} else {				
				try {
		
					
					//����
					subject = " [SRM ������ü������] "+h_pjt+" / "+h_hno+" / "+zsite_nm+" WEB�� ��ġ��� �Է� ��û";
					//����
					StringBuffer bodyBuff = new StringBuffer();
					bodyBuff.append("<style type='text/css'>                                      \n");
					bodyBuff.append("	.bdx  { font-size:12px;}                                  \n");
					bodyBuff.append("	.tbx { font-size:12px; background: #e6e6e6; border:1px #f1e0d0 solid; table-layout:fixed;}                               \n");
					bodyBuff.append("	.thx { background:#f8f5ee; color:#bf9d82; height:25px; }   \n");
					bodyBuff.append("	.tdx { background:#FFFFFF; height:25px; }                  \n");
					bodyBuff.append("</style>                                                     \n");
					bodyBuff.append("<body class='bdx'>                                           \n");
					
					bodyBuff.append("<b>1. ���� ���� </b></br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx'>\n");
					bodyBuff.append("<col width='10%'><col width='10%'><col width='10%'><col width='30%'><col width='10%'><col width='30%'>\n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx'>������Ʈ</th>                 \n");
					bodyBuff.append("			<td class='tdx'>"+h_pjt+"</td>               \n");
					bodyBuff.append("			<th class='thx'>����</th>                   \n");
					bodyBuff.append("			<td class='tdx'>"+kunnr_nm+"</td>            \n");
					bodyBuff.append("			<th class='thx'>�����</th>                   \n");
					bodyBuff.append("			<td class='tdx'>"+zsite_nm+"</td>            \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx'>ȣ��</th>                     \n");
					bodyBuff.append("			<td class='tdx'>"+h_hno+"</td>               \n");
					bodyBuff.append("			<th class='thx'>ȣ���</th>                   \n");
					bodyBuff.append("			<td class='tdx'>"+h_hna+"</td>               \n");
					bodyBuff.append("			<th class='thx'>����ܰ�</th>                  \n");
					bodyBuff.append("			<td class='tdx'>"+pst_nm+"</td>               \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("	</table>                                \n");
					bodyBuff.append("</br>\n");
					bodyBuff.append(" <b>2. ���� ���� </b></br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx' style='table-layout:fixed; word-break:break-all;'>\n");
					bodyBuff.append("<col width='10%'><col width='10%'><col width='10%'><col width='30%'><col width='10%'><col width='30%'>\n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>�湮����</th>                 \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_VSD")+"</td>                 \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>����</th>                     \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "MM028_CTEXT2")+"</td>              \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>��������</th>                 \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "NAME1")+"</td>                     \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>�湮��</th>                   \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_VSM")+"</td>                  \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>�ۼ���</th>                   \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_JSD")+"</td>                  \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>�����ڿ���ó</th>             \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "JUJ_TEL")+"</td>                    \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("	</table>                              \n");
					bodyBuff.append("</br>\n");
					bodyBuff.append(" <b>3. �˻�� ���� </b></br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx'>\n");
					bodyBuff.append("<col width='10%'><col width='10%'><col width='10%'><col width='30%'><col width='10%'><col width='30%'>\n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx'>�湮����</th>                 \n");
					bodyBuff.append("			<td class='tdx'>"+ds_header.getColumnAsString(0, "CS107_VSD_QC")+"</td>                 \n");
					bodyBuff.append("			<th class='thx'>���κμ�</th>                 \n");
					bodyBuff.append("			<td class='tdx'>"+ds_header.getColumnAsString(0, "CS107_NTC_QC_NM")+"</td>               \n");
					bodyBuff.append("			<th class='thx'>�߻�����</th>                 \n");
					bodyBuff.append("			<td class='tdx'>"+ds_header.getColumnAsString(0, "CS107_RSN_QC_NM")+"</td>              \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx'>�湮��</th>                   \n");
					bodyBuff.append("			<td class='tdx'>"+ds_header.getColumnAsString(0, "CS107_VSM_QC")+"</td>                 \n");
					bodyBuff.append("			<th class='thx'>�ۼ���</th>                   \n");
					bodyBuff.append("			<td class='tdx'>"+ds_header.getColumnAsString(0, "CS107_JSD_QC")+"</td>                 \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("	</table>                              \n");
					bodyBuff.append("</br>\n");
					bodyBuff.append(" <b>4. ������ ���� </b></br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx'>\n");
					bodyBuff.append("<col width='10%'><col width='20%'><col width='30%'><col width='10%'><col width='30%'>\n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx'>NO</th>                      \n");
					bodyBuff.append("			<th class='thx'>�˻��׸�</th>                 \n");
					bodyBuff.append("			<th class='thx'>������</th>                   \n");
					bodyBuff.append("			<th class='thx'>��ġ�μ�</th>                 \n");
					bodyBuff.append("			<th class='thx'>QCȮ�ΰ��</th>               \n");
					bodyBuff.append("		</tr>                               \n");
					
					for(int i=0; i<ds_detail.getRowCount(); i++) {
						bodyBuff.append("		<tr>                                \n");
						bodyBuff.append("			<td class='tdx'>"+(i+1)+"</td>                        \n");
						bodyBuff.append("			<td class='tdx'>"+ ds_detail.getColumnAsString(i, "CS106_QMR") +"</td>                \n");
						bodyBuff.append("			<td class='tdx'>"+ ds_detail.getColumnAsString(i, "CS108_QII") +"</td>                \n");
						bodyBuff.append("			<td class='tdx'>"+ ds_detail.getColumnAsString(i, "CS108_NTC_QC_NM") +"</td>          \n");
						bodyBuff.append("			<td class='tdx'>"+ ds_detail.getColumnAsString(i, "CS108_TXT_QC") +"</td>             \n");
						bodyBuff.append("		</tr>                               \n");
					}			
					bodyBuff.append("	</table>                              \n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("�ش����� ��ü������ ��ġ�۾��� ��ġ��� Ȯ�� �Ŀ� �׸񺰷� �Է��Ͽ� �ֽñ� �ٶ��ϴ�. ");
					bodyBuff.append("<font color='red'>(��ġ�μ� ��ġ ������)</font> </br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("�� �Է¹�� :  HELCO_WEB - MENU �� ��ġ���� ��������� �� ���� �ΰ�/���ΰ���� �� ������ ó����Ȳ</br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("        ������Ʈ ��ȸ �� �ش�ȣ�� ���������� ����, ��ġ��� �Է� �ʿ�</br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("�� ��ġ��� �Է� �����Ͽ� ���ǻ����� ������ ��� ȿ������������ �����ֽñ� �ٶ��ϴ�. (jw.kim@hdel.co.kr / �� 02-3670-1213)</br>\n");
					bodyBuff.append("		</body>                                \n");
		
					
					/* ���� ���� */		
					Properties props = System.getProperties();
					props.setProperty("mail.transport.protocol", "smtp");
					props.setProperty("mail.host", HOST);
					props.put("mail.smpt.port", PORT);
					
					Session session = Session.getDefaultInstance(props);
					
			        MimeMessage sdmail = new MimeMessage(session);
			        sdmail.setSubject(subject);
			        sdmail.setFrom(fromAdr);
			        sdmail.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));        
			        
			        Multipart mtpart = new MimeMultipart();
			        BodyPart bdpart = new MimeBodyPart();
			      
			        bdpart.setContent(bodyBuff.toString(), "text/html;charset=euc-kr");
			        mtpart.addBodyPart(bdpart);
			        System.out.println(bodyBuff.toString());
		
		
					BodyPart attachmt = new MimeBodyPart();
			        DataSource source ;
					for(int i=0; i<ds_detail.getRowCount(); i++) {
						if(ds_detail.getColumnAsString(i,"CS108_QAD").length() > 0){
							attachmt = new MimeBodyPart();
							fpath = PATH1 + ds_detail.getColumnAsString(i,"CS108_QAD");
					        source = new FileDataSource(fpath);
					        attachmt.setDataHandler(new DataHandler(source) );
					        attachmt.setFileName(MimeUtility.encodeText(source.getName()));
					        mtpart.addBodyPart(attachmt);
						}
					}
			        sdmail.setContent(mtpart);
			        Transport.send(sdmail);
		
					zcstMailLogi.addParamObject("ds_MailRvcList", ds_MailRvcList);
					zcstMailLogi.setRowIndex(0);
					executor.execute(zcstMailLogi);
					
					TransactionManager.commit();
					
				} catch (Exception e) {
					TransactionManager.rollback();
					throw new BusinessException("CE00001");
				}
		
		}

	}

	public void sendMailToInspection(BusinessContext ctx) throws Exception 
	{
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor =  new DatasetSqlExecutor(getConnection(db_con));
		//mail ���� �̷� 
		DatasetSqlRequest zcstMailLogi
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:MAIL_LOG");

		Dataset ds_header = ctx.getInputDataset("ds_list_h");
		Dataset ds_detail = ctx.getInputDataset("ds_list");
		Dataset ds_MailRvcList = ctx.getInputDataset("ds_MailRvcList");

		
		/* ���� */
		String  recipient = ctx.getInputVariable("RECE_USER"); //������ 
		Address fromAdr   = new InternetAddress(ctx.getInputVariable("SEND_USER")); //�߽���
		String  subject ; //����
		String  fpath ;   //÷������ ���
		
		String h_pjt    = ds_header.getColumnAsString(0, "CS101_PJT");
		String h_hno    = ds_header.getColumnAsString(0, "CS101_HNO");
		String h_hna    = ds_header.getColumnAsString(0, "CS101_HNA");
		String zsite_nm = ds_header.getColumnAsString(0,"ZSITE_NM");
		String kunnr_nm = ds_header.getColumnAsString(0,"KUNNR_NM");
		String pst_nm   = ds_header.getColumnAsString(0,"CS107_PST_NM");
		String h_rcd    = ds_header.getColumnAsString(0,"CS101_RCD");
		String h_igd    = ds_header.getColumnAsString(0,"CS101_IGD");
		String h_qjd    = ds_header.getColumnAsString(0,"CS107_QJD");
		String h_name6  = ds_header.getColumnAsString(0,"NAME6");
		String h_namet2 = ds_header.getColumnAsString(0,"NAMET2");
		String h_cellp5 = ds_header.getColumnAsString(0,"CELLP5");
		
		
		zcstMailLogi.addParamObject("G_MANDT", mdt);
		zcstMailLogi.addParamObject("PJT", h_pjt);
		zcstMailLogi.addParamObject("HNO", h_hno);				

		if(ds_MailRvcList.getRowCount() == 0) {
			zcstMailLogi.setRowIndex(0);
			executor.execute(zcstMailLogi);			
		} else {
			try {
	
				
				//����
				subject = " [SRM ������ü������] "+h_pjt+" / "+h_hno+" / "+zsite_nm+" ��ü������ ���� ��û";
				//����
				StringBuffer bodyBuff = new StringBuffer();
				bodyBuff.append("<style type='text/css'>                                      \n");
				bodyBuff.append("	.bdx  { font-size:12px;}                                  \n");
				bodyBuff.append("	.tbx { font-size:12px; background: #e6e6e6; border:1px #f1e0d0 solid; table-layout:fixed;}                               \n");
				bodyBuff.append("	.thx { background:#f8f5ee; color:#bf9d82; height:25px; }   \n");
				bodyBuff.append("	.tdx { background:#FFFFFF; height:25px; }                  \n");
				bodyBuff.append("</style>                                                     \n");
				bodyBuff.append("<body class='bdx'>                                           \n");
				
				bodyBuff.append("<b>1. ���� ���� </b></br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx'>\n");
				bodyBuff.append("<col width='10%'><col width='10%'><col width='10%'><col width='30%'><col width='10%'><col width='30%'>\n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx'>������Ʈ</th>                 \n");
				bodyBuff.append("			<td class='tdx'>"+h_pjt+"</td>               \n");
				bodyBuff.append("			<th class='thx'>����</th>                   \n");
				bodyBuff.append("			<td class='tdx'>"+kunnr_nm+"</td>            \n");
				bodyBuff.append("			<th class='thx'>�����</th>                   \n");
				bodyBuff.append("			<td class='tdx'>"+zsite_nm+"</td>            \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx'>ȣ��</th>                     \n");
				bodyBuff.append("			<td class='tdx'>"+h_hno+"</td>               \n");
				bodyBuff.append("			<th class='thx'>ȣ���</th>                   \n");
				bodyBuff.append("			<td class='tdx'>"+h_hna+"</td>               \n");
				bodyBuff.append("			<th class='thx'>����ܰ�</th>                  \n");
				bodyBuff.append("			<td class='tdx'>"+pst_nm+"</td>               \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx'>QE�˻���</th>                     \n");
				bodyBuff.append("			<td class='tdx'>"+h_rcd+"</td>               \n");
				bodyBuff.append("			<th class='thx'>�ΰ���</th>                   \n");
				bodyBuff.append("			<td class='tdx'>"+h_igd+"</td>               \n");
				bodyBuff.append("			<th class='thx'>����������</th>                  \n");
				bodyBuff.append("			<td class='tdx'>"+h_qjd+"</td>               \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx'>��ġ���»�</th>                     \n");
				bodyBuff.append("			<td class='tdx'>"+h_name6+"</td>               \n");
				bodyBuff.append("			<th class='thx'>�۾�����</th>                   \n");
				bodyBuff.append("			<td class='tdx'>"+h_namet2+"</td>               \n");
				bodyBuff.append("			<th class='thx'>��ȭ��ȣ</th>                  \n");
				bodyBuff.append("			<td class='tdx'>"+h_cellp5+"</td>               \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("	</table>                                \n");
				bodyBuff.append("</br>\n");
				bodyBuff.append(" <b>2. ���� ���� </b></br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx' style='table-layout:fixed; word-break:break-all;'>\n");
				bodyBuff.append("<col width='10%'><col width='10%'><col width='10%'><col width='30%'><col width='10%'><col width='30%'>\n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>�湮����</th>                 \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_VSD")+"</td>                 \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>����</th>                     \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "MM028_CTEXT2")+"</td>              \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>��������</th>                 \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "NAME1")+"</td>                     \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>�湮��</th>                   \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_VSM")+"</td>                  \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>�ۼ���</th>                   \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_JSD")+"</td>                  \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>�����ڿ���ó</th>             \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "JUJ_TEL")+"</td>                    \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("	</table>                              \n");
				bodyBuff.append("</br>\n");
				bodyBuff.append(" <b>3. ������ ���� </b></br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx'>\n");
				bodyBuff.append("<col width='10%'><col width='30%'><col width='30%'><col width='30%'>\n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx'>NO</th>                      \n");
				bodyBuff.append("			<th class='thx'>�˻��׸�</th>                 \n");
				bodyBuff.append("			<th class='thx'>��������</th>                   \n");
				bodyBuff.append("			<th class='thx'>������</th>                   \n");
				bodyBuff.append("		</tr>                               \n");
				
				for(int i=0; i<ds_detail.getRowCount(); i++) {
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<td class='tdx'>"+(i+1)+"</td>                        \n");
					bodyBuff.append("			<td class='tdx'>"+ ds_detail.getColumnAsString(i, "CS106_QMR") +"</td>                \n");
					bodyBuff.append("			<td class='tdx'>"+ ds_detail.getColumnAsString(i, "CS106_MIR") +"</td>                \n");
					bodyBuff.append("			<td class='tdx'>"+ ds_detail.getColumnAsString(i, "CS108_QII") +"</td>                \n");
					bodyBuff.append("		</tr>                               \n");
				}			
				bodyBuff.append("	</table>                              \n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("�ش����� ��ü������ ���� Ȯ�� �� �������� ���� �׸񺰷� ���� �����Ͽ� �ֽñ� �ٶ��ϴ�. </br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("<font color='blue'>-> ��ġ������ ��ġPM���� ��ġ����Է� ���Ϲ߼۵˴ϴ�. ���� �� ��ġ��ȹ ��Ȯ�� ���� �ٶ��ϴ�.</font> </br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("�� �Է¹�� :  ��   HELCO_WEB - MENU �� ��ġ���� ��������� �� ���� �ΰ�/���ΰ���� �� ������ ó����Ȳ</br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("            ��  MSRM �� �μ�����  �� ������ ó����Ȳ</br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("        ������Ʈ ��ȸ �� �ش�ȣ�� ���������� ����, �׸� ����(��ġ���� �۾����뺸) �� �߻����� ���� �ʿ�</br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("		</body>                                \n");
	
				/* ���� ���� */		
				Properties props = System.getProperties();
				props.setProperty("mail.transport.protocol", "smtp");
				props.setProperty("mail.host", HOST);
				props.put("mail.smpt.port", PORT);
				
				Session session = Session.getDefaultInstance(props);
				
		        MimeMessage sdmail = new MimeMessage(session);
		        sdmail.setSubject(subject);
		        sdmail.setFrom(fromAdr);
		        sdmail.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));        
		        
		        Multipart mtpart = new MimeMultipart();
		        BodyPart bdpart = new MimeBodyPart();
		      
		        bdpart.setContent(bodyBuff.toString(), "text/html;charset=euc-kr");
		        mtpart.addBodyPart(bdpart);
	
				BodyPart attachmt = new MimeBodyPart();
		        DataSource source ;
				for(int i=0; i<ds_detail.getRowCount(); i++) {
					if(ds_detail.getColumnAsString(i,"CS108_QAD").length() > 0){
						attachmt = new MimeBodyPart();
						fpath = PATH1 + ds_detail.getColumnAsString(i,"CS108_QAD");
				        source = new FileDataSource(fpath);
				        attachmt.setDataHandler(new DataHandler(source) );
				        attachmt.setFileName(MimeUtility.encodeText(source.getName()));
				        mtpart.addBodyPart(attachmt);
					}
				}
		        sdmail.setContent(mtpart);
		        Transport.send(sdmail);
	
				zcstMailLogi.addParamObject("ds_MailRvcList", ds_MailRvcList);
				zcstMailLogi.setRowIndex(0);
				executor.execute(zcstMailLogi);
				
				TransactionManager.commit();
				
			} catch (Exception e) {
				TransactionManager.rollback();
				throw new BusinessException("CE00001");
			}
		}
	
	}

}