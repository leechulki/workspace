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
	 * 승인
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {
		Dataset dsList = ctx.getInputDataset("ds_list");
		Dataset dsError = null;
		SapFunction stub = null;
		
		// 입력 파라메터 처리 
		if ( CallSAP.isJCO() ) {
			dsError = new Dataset("ds_error");						
			//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{dsList});
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{dsList}, false);		 // 기존  SAP 연결
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{dsList}, false);  // EAI 연결


			CallSAP.moveObj2Ds(dsError, stub.getOutput("O_ETAB"));
		} else {
			ZSDS0075[] list = (ZSDS0075[])
					CallSAP.moveDs2Obj(dsList, ZSDS0075.class, "FLAG");
			ZQMSEMSG[] msgList = new ZQMSEMSG[]{};
			
			// 저장 처리 
			//stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{msgList, list});
			stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{msgList, list}, false);		 // 기존  SAP 연결
			//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_SD_INSURANCE, new Object[]{msgList, list}, false);  // EAI 연결
			
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
			
			if(dsRtn111.getRowCount() > 0) { //보수 마스터 등록 여부 확인
				ds_list.setColumn(i, "FAIL", "");
			} else {
				ds_list.setColumn(i, "FAIL", "Y");
				ds_list.setColumn(i, "FAIL_TXT", "미 인수");
			}
			
			zcst107s.setRowIndex(i);
			zcst107s.addParamObject("PJT", ds_list.getColumnAsObject(i, "PJT"));
			zcst107s.addParamObject("HNO", ds_list.getColumnAsObject(i, "HNO"));
			
			Dataset dsRtn107 = (Dataset)executor.query(zcst107s).getResultObject();
			
			if(dsRtn107.getRowCount() > 0) { //문제점 등록 여부 확인
				ds_list.setColumn(i, "FAIL", "Y");
				ds_list.setColumn(i, "FAIL_TXT", "문제점 등록 진행 중");
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
			ds_list.setColumn(i, "RESULT", "저장성공");
		}
		ctx.addOutputDataset(ds_list);
	}

	public void sendMailToPM(BusinessContext ctx) throws Exception 
	{
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor =  new DatasetSqlExecutor(getConnection(db_con));
		//mail 전송 이력 
		DatasetSqlRequest zcstMailLogi
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:MAIL_LOG");

		Dataset ds_header = ctx.getInputDataset("ds_list_h");
		Dataset ds_detail = ctx.getInputDataset("ds_list");
		Dataset ds_MailRvcList = ctx.getInputDataset("ds_MailRvcList");

		
		/* 메일 */
		String  recipient = ctx.getInputVariable("RECE_USER"); //수신자 
		Address fromAdr   = new InternetAddress(ctx.getInputVariable("SEND_USER")); //발신자
		String  subject ; //제목
		String  fpath ;   //첨부파일 경로
		
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
		
					
					//제목
					subject = " [SRM 서비스이체문제점] "+h_pjt+" / "+h_hno+" / "+zsite_nm+" WEB상 조치결과 입력 요청";
					//내용
					StringBuffer bodyBuff = new StringBuffer();
					bodyBuff.append("<style type='text/css'>                                      \n");
					bodyBuff.append("	.bdx  { font-size:12px;}                                  \n");
					bodyBuff.append("	.tbx { font-size:12px; background: #e6e6e6; border:1px #f1e0d0 solid; table-layout:fixed;}                               \n");
					bodyBuff.append("	.thx { background:#f8f5ee; color:#bf9d82; height:25px; }   \n");
					bodyBuff.append("	.tdx { background:#FFFFFF; height:25px; }                  \n");
					bodyBuff.append("</style>                                                     \n");
					bodyBuff.append("<body class='bdx'>                                           \n");
					
					bodyBuff.append("<b>1. 현장 정보 </b></br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx'>\n");
					bodyBuff.append("<col width='10%'><col width='10%'><col width='10%'><col width='30%'><col width='10%'><col width='30%'>\n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx'>프로젝트</th>                 \n");
					bodyBuff.append("			<td class='tdx'>"+h_pjt+"</td>               \n");
					bodyBuff.append("			<th class='thx'>고객명</th>                   \n");
					bodyBuff.append("			<td class='tdx'>"+kunnr_nm+"</td>            \n");
					bodyBuff.append("			<th class='thx'>현장명</th>                   \n");
					bodyBuff.append("			<td class='tdx'>"+zsite_nm+"</td>            \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx'>호기</th>                     \n");
					bodyBuff.append("			<td class='tdx'>"+h_hno+"</td>               \n");
					bodyBuff.append("			<th class='thx'>호기명</th>                   \n");
					bodyBuff.append("			<td class='tdx'>"+h_hna+"</td>               \n");
					bodyBuff.append("			<th class='thx'>진행단계</th>                  \n");
					bodyBuff.append("			<td class='tdx'>"+pst_nm+"</td>               \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("	</table>                                \n");
					bodyBuff.append("</br>\n");
					bodyBuff.append(" <b>2. 보수 정보 </b></br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx' style='table-layout:fixed; word-break:break-all;'>\n");
					bodyBuff.append("<col width='10%'><col width='10%'><col width='10%'><col width='30%'><col width='10%'><col width='30%'>\n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>방문일자</th>                 \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_VSD")+"</td>                 \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>지역</th>                     \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "MM028_CTEXT2")+"</td>              \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>보수협력</th>                 \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "NAME1")+"</td>                     \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>방문자</th>                   \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_VSM")+"</td>                  \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>작성일</th>                   \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_JSD")+"</td>                  \n");
					bodyBuff.append("			<th class='thx' style='word-break:break-all;'>점검자연락처</th>             \n");
					bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "JUJ_TEL")+"</td>                    \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("	</table>                              \n");
					bodyBuff.append("</br>\n");
					bodyBuff.append(" <b>3. 검사원 정보 </b></br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx'>\n");
					bodyBuff.append("<col width='10%'><col width='10%'><col width='10%'><col width='30%'><col width='10%'><col width='30%'>\n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx'>방문일자</th>                 \n");
					bodyBuff.append("			<td class='tdx'>"+ds_header.getColumnAsString(0, "CS107_VSD_QC")+"</td>                 \n");
					bodyBuff.append("			<th class='thx'>원인부서</th>                 \n");
					bodyBuff.append("			<td class='tdx'>"+ds_header.getColumnAsString(0, "CS107_NTC_QC_NM")+"</td>               \n");
					bodyBuff.append("			<th class='thx'>발생사유</th>                 \n");
					bodyBuff.append("			<td class='tdx'>"+ds_header.getColumnAsString(0, "CS107_RSN_QC_NM")+"</td>              \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx'>방문자</th>                   \n");
					bodyBuff.append("			<td class='tdx'>"+ds_header.getColumnAsString(0, "CS107_VSM_QC")+"</td>                 \n");
					bodyBuff.append("			<th class='thx'>작성일</th>                   \n");
					bodyBuff.append("			<td class='tdx'>"+ds_header.getColumnAsString(0, "CS107_JSD_QC")+"</td>                 \n");
					bodyBuff.append("		</tr>                               \n");
					bodyBuff.append("	</table>                              \n");
					bodyBuff.append("</br>\n");
					bodyBuff.append(" <b>4. 문제점 내용 </b></br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx'>\n");
					bodyBuff.append("<col width='10%'><col width='20%'><col width='30%'><col width='10%'><col width='30%'>\n");
					bodyBuff.append("		<tr>                                \n");
					bodyBuff.append("			<th class='thx'>NO</th>                      \n");
					bodyBuff.append("			<th class='thx'>검사항목</th>                 \n");
					bodyBuff.append("			<th class='thx'>문제점</th>                   \n");
					bodyBuff.append("			<th class='thx'>조치부서</th>                 \n");
					bodyBuff.append("			<th class='thx'>QC확인결과</th>               \n");
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
					bodyBuff.append("해당현장 이체문제점 설치작업자 조치결과 확인 후에 항목별로 입력하여 주시기 바랍니다. ");
					bodyBuff.append("<font color='red'>(조치부서 설치 판정분)</font> </br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("★ 입력방법 :  HELCO_WEB - MENU ▶ 설치관리 ▶현장관리 ▶ 보수 인계/미인계관리 ▶ 문제점 처리현황</br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("        프로젝트 조회 후 해당호기 문제점보고 선택, 조치결과 입력 필요</br>\n");
					bodyBuff.append("</br>\n");
					bodyBuff.append("※ 조치결과 입력 관련하여 문의사항이 있으신 경우 효율개선팀으로 연락주시기 바랍니다. (jw.kim@hdel.co.kr / ☏ 02-3670-1213)</br>\n");
					bodyBuff.append("		</body>                                \n");
		
					
					/* 메일 서버 */		
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
		//mail 전송 이력 
		DatasetSqlRequest zcstMailLogi
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:MAIL_LOG");

		Dataset ds_header = ctx.getInputDataset("ds_list_h");
		Dataset ds_detail = ctx.getInputDataset("ds_list");
		Dataset ds_MailRvcList = ctx.getInputDataset("ds_MailRvcList");

		
		/* 메일 */
		String  recipient = ctx.getInputVariable("RECE_USER"); //수신자 
		Address fromAdr   = new InternetAddress(ctx.getInputVariable("SEND_USER")); //발신자
		String  subject ; //제목
		String  fpath ;   //첨부파일 경로
		
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
	
				
				//제목
				subject = " [SRM 서비스이체문제점] "+h_pjt+" / "+h_hno+" / "+zsite_nm+" 이체문제점 판정 요청";
				//내용
				StringBuffer bodyBuff = new StringBuffer();
				bodyBuff.append("<style type='text/css'>                                      \n");
				bodyBuff.append("	.bdx  { font-size:12px;}                                  \n");
				bodyBuff.append("	.tbx { font-size:12px; background: #e6e6e6; border:1px #f1e0d0 solid; table-layout:fixed;}                               \n");
				bodyBuff.append("	.thx { background:#f8f5ee; color:#bf9d82; height:25px; }   \n");
				bodyBuff.append("	.tdx { background:#FFFFFF; height:25px; }                  \n");
				bodyBuff.append("</style>                                                     \n");
				bodyBuff.append("<body class='bdx'>                                           \n");
				
				bodyBuff.append("<b>1. 현장 정보 </b></br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx'>\n");
				bodyBuff.append("<col width='10%'><col width='10%'><col width='10%'><col width='30%'><col width='10%'><col width='30%'>\n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx'>프로젝트</th>                 \n");
				bodyBuff.append("			<td class='tdx'>"+h_pjt+"</td>               \n");
				bodyBuff.append("			<th class='thx'>고객명</th>                   \n");
				bodyBuff.append("			<td class='tdx'>"+kunnr_nm+"</td>            \n");
				bodyBuff.append("			<th class='thx'>현장명</th>                   \n");
				bodyBuff.append("			<td class='tdx'>"+zsite_nm+"</td>            \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx'>호기</th>                     \n");
				bodyBuff.append("			<td class='tdx'>"+h_hno+"</td>               \n");
				bodyBuff.append("			<th class='thx'>호기명</th>                   \n");
				bodyBuff.append("			<td class='tdx'>"+h_hna+"</td>               \n");
				bodyBuff.append("			<th class='thx'>진행단계</th>                  \n");
				bodyBuff.append("			<td class='tdx'>"+pst_nm+"</td>               \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx'>QE검사일</th>                     \n");
				bodyBuff.append("			<td class='tdx'>"+h_rcd+"</td>               \n");
				bodyBuff.append("			<th class='thx'>인계일</th>                   \n");
				bodyBuff.append("			<td class='tdx'>"+h_igd+"</td>               \n");
				bodyBuff.append("			<th class='thx'>보수접수일</th>                  \n");
				bodyBuff.append("			<td class='tdx'>"+h_qjd+"</td>               \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx'>설치협력사</th>                     \n");
				bodyBuff.append("			<td class='tdx'>"+h_name6+"</td>               \n");
				bodyBuff.append("			<th class='thx'>작업팀장</th>                   \n");
				bodyBuff.append("			<td class='tdx'>"+h_namet2+"</td>               \n");
				bodyBuff.append("			<th class='thx'>전화번호</th>                  \n");
				bodyBuff.append("			<td class='tdx'>"+h_cellp5+"</td>               \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("	</table>                                \n");
				bodyBuff.append("</br>\n");
				bodyBuff.append(" <b>2. 보수 정보 </b></br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx' style='table-layout:fixed; word-break:break-all;'>\n");
				bodyBuff.append("<col width='10%'><col width='10%'><col width='10%'><col width='30%'><col width='10%'><col width='30%'>\n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>방문일자</th>                 \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_VSD")+"</td>                 \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>지역</th>                     \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "MM028_CTEXT2")+"</td>              \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>보수협력</th>                 \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "NAME1")+"</td>                     \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>방문자</th>                   \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_VSM")+"</td>                  \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>작성일</th>                   \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "CS107_JSD")+"</td>                  \n");
				bodyBuff.append("			<th class='thx' style='word-break:break-all;'>점검자연락처</th>             \n");
				bodyBuff.append("			<td class='tdx' style='word-break:break-all;'>"+ds_header.getColumnAsString(0, "JUJ_TEL")+"</td>                    \n");
				bodyBuff.append("		</tr>                               \n");
				bodyBuff.append("	</table>                              \n");
				bodyBuff.append("</br>\n");
				bodyBuff.append(" <b>3. 문제점 내용 </b></br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("<table width='700' cellpadding='0' cellspacing='1' class='tbx'>\n");
				bodyBuff.append("<col width='10%'><col width='30%'><col width='30%'><col width='30%'>\n");
				bodyBuff.append("		<tr>                                \n");
				bodyBuff.append("			<th class='thx'>NO</th>                      \n");
				bodyBuff.append("			<th class='thx'>검사항목</th>                 \n");
				bodyBuff.append("			<th class='thx'>중점내용</th>                   \n");
				bodyBuff.append("			<th class='thx'>문제점</th>                   \n");
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
				bodyBuff.append("해당현장 이체문제점 내용 확인 후 빠른시일 내에 항목별로 판정 진행하여 주시기 바랍니다. </br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("<font color='blue'>-> 설치판정시 설치PM에게 조치결과입력 메일발송됩니다. 판정 및 조치계획 명확히 진행 바랍니다.</font> </br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("★ 입력방법 :  ①   HELCO_WEB - MENU ▶ 설치관리 ▶현장관리 ▶ 보수 인계/미인계관리 ▶ 문제점 처리현황</br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("            ②  MSRM ▶ 인수문제  ▶ 문제점 처리현황</br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("        프로젝트 조회 후 해당호기 문제점보고 선택, 항목별 판정(설치판정 작업자통보) 및 발생사유 선택 필요</br>\n");
				bodyBuff.append("</br>\n");
				bodyBuff.append("		</body>                                \n");
	
				/* 메일 서버 */		
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