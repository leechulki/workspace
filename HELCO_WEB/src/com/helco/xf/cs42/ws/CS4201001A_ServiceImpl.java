package com.helco.xf.cs42.ws;

import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.Variant;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.batch.ProcPostReceive;
import com.helco.xf.cs12.batch.TransForBSC;
import com.helco.xf.cs12.batch.TransForBSG;
import com.helco.xf.cs12.batch.TransForBWA;
import com.helco.xf.cs12.batch.TransForBWE;
import com.helco.xf.cs12.batch.TransForBWM;
import com.helco.xf.cs12.batch.TransForBWZ;
import com.helco.xf.cs12.batch.TransForBXL;
import com.helco.xf.cs12.batch.TransForBXR;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWMF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs34.ws.ZCSS010;
import com.helco.xf.cs34.ws.ZCSS011;
import com.helco.xf.cs41.ws.ZCSS012;
import com.helco.xf.wb01.batch.FileFilter;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.TitUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

public class CS4201001A_ServiceImpl extends AbstractBusinessService implements
		CS4201001A_Service {
	static Logger logger;
	private FileOutputStream outputStream;
	public void create(BusinessContext ctx, Dataset ds_list) throws Exception {
		
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		// 경로 지정
		String pathname = "/srm/HELCO_WEB/HELCO_WEB.war/svc/POSTNET_AGENT/env/attachment/send/"
//		String pathname = "C:\\Users\\db2admin\\Desktop\\전자우편 서비스\\TEST\\" 
				        + DateTime.getShortDateString() + DateTime.getShortTimeString()
				        + ".txt";
		
		// 파일 생성
		File myfile = new File(pathname);		
		if(myfile.exists()) {
			System.out.println("파일이 존재합니다.");
		} else {
			System.out.println("파일이 존재하지 않습니다.");

			try {
				boolean result = myfile.createNewFile();

				if(result) {
					System.out.println("파일 생성이 완료되었습니다.");
				}
			} catch(IOException ce) {
				ce.printStackTrace();
			}
		}		
	    //System.out.println(myfile.getAbsolutePath());
	    //System.out.println("getName():"+myfile.getName()); 		//파일명
	    //System.out.println("getParent():"+myfile.getParent()); 	//현재 파일의 경로
	    //System.out.println("getPath():"+myfile.getPath()); 		// 경로 + 파일명 

		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(pathname),"UTF-8") ;
		String arg = "";  
		String fnum = ""; 
/*
		for(int i=0; i<ds_list.getRowCount(); i++) {
			for(int j=1; j<246; j++){
				if(j < 10){
					fnum = "S00" + (j); 
				} else if(j < 100){
					fnum = "S0" + (j);
				} else {
					fnum = "S" + (j);
				}
				arg += ds_list.getColumnAsString(i, fnum );
				arg += "|";
			}
			if( i < ds_list.getRowCount()-1){		
				arg += "\n" ;	
			} else {
			}
		}
	*/		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			arg += ds_list.getColumnAsString(i, "TXT" );
			arg += "\n" ;	
		}
		
		fw.write(arg);
		fw.close();
		// 파일명 바꾸기 "send_" 로 시작하는 파일만 전송 됨
		// 파일경로 + "send_" + 시스템일자시간
		File reFile = new File(myfile.getParent(),"send_" 
		        + DateTime.getShortDateString() + DateTime.getShortTimeString()
		        + ".txt");
		
	    //System.out.println(reFile.getAbsolutePath());
	    //System.out.println("getName():"+reFile.getName()); //파일명
	    //System.out.println("getParent():"+reFile.getParent()); //현재 파일의 경로
	    //System.out.println("getPath():"+reFile.getPath()); // 경로 + 파일명 
	    
	    myfile.renameTo(reFile);
		// 파일 생성 완료 후 전송정보 update
		DatasetSqlRequest zcst701u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4201001A_U2");
		zcst701u.addParamObject("ds_list", ds_list);
		zcst701u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst701u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			zcst701u.addParamObject("S010", ds_list.getColumnAsString(i, "S010" ));
			zcst701u.addParamObject("S018", DateTime.getShortDateString());
			zcst701u.addParamObject("S019", DateTime.getShortTimeString());
			zcst701u.addParamObject("S020", ds_list.getColumnAsString(i, "S020" ));
			zcst701u.addParamObject("S028", ds_list.getColumnAsString(i, "S028" ));
			zcst701u.addParamObject("VKGRP", ds_list.getColumnAsString(i, "VKGRP" ));
				
			zcst701u.setRowIndex(i);
			executor.execute(zcst701u);
		}
		
	}
	// 로컬 테스트 용
	public void read(BusinessContext ctx, Dataset ds_list) throws Exception {

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		Connection conn = null;
		conn = getConnection(db_con);
		
		String tdt  = ds_list.getColumnAsString(0, "TDT");

		try
	      {
			ProcPostReceive PostReceive = new ProcPostReceive();
			
			int rtnCode = PostReceive.isExistFile(tdt);

			System.out.println("rtnCode   ::::::  " + rtnCode);
			// 기준일자 파일 존재 여부  체크 
			// 파일 존재하는 경우 rtnCode == 0
			if(rtnCode == 0){	
				//  파일 존재하는 경우 db insert 
				PostReceive.insertDB(mdt,conn,tdt);
			}
	      }
		catch(Exception se)
	      {
	         logger.debug("\n" +  se.toString());
	         se.printStackTrace();
	      }		
	}




	public static final String ZWEB_CS_GET_PRINT
		= "com.helco.xf.cs42.ws.ZWEB_CS_GET_PRINT";	
	public void getTaxinfo(BusinessContext ctx, Dataset ds_cond, Dataset ds_list) throws Exception {
	
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// 입력 파라메터 처리 
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Dataset dsError = null;

		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "CC_GBN")
				, DatasetUtility.getString(dsCond, "DAT1")
				, DatasetUtility.getString(dsCond, "DAT2")
				, DatasetUtility.getString(dsCond, "PJT")
				, DatasetUtility.getString(dsCond, "PRIFORM")
				, DatasetUtility.getString(dsCond, "PRTNO")
				, DatasetUtility.getString(dsCond, "TAXNO")
				, DatasetUtility.getString(dsCond, "VKBUR")		
				, DatasetUtility.getString(dsCond, "VKGRP")	
				, DatasetUtility.getString(dsCond, "ZDEL")	
				, listMsg
		//		, list
		};

		//SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_PRINT, obj);
		SapFunction stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_PRINT, obj, false);		 // 기존  SAP 연결
		//SapFunction stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"), ctx.getInputVariable("G_MANDT"), ZWEB_CS_GET_PRINT, obj, false);  // EAI 연결



		// 목록 정보 옮기기 
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);

		ctx.addOutputDataset(dsError);		
		
	}
	
    public void insertHeader(BusinessContext ctx, Dataset ds_list) throws Exception {

		logger = ServiceManagerFactory.getLogger();

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		DatasetSqlRequest getConkey
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4299003A_S2");
		getConkey.addParamObject("ds_list", ds_list);
		getConkey.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		DatasetSqlRequest zcst701i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4202001A_I1");
		zcst701i.addParamObject("ds_list", ds_list);
		zcst701i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst701i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		DatasetSqlRequest zcst702i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4202001A_I2");
		zcst702i.addParamObject("ds_list", ds_list);
		zcst702i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst702i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		DatasetSqlRequest zcst709u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4202001A_U1");
		zcst709u.addParamObject("ds_list", ds_list);
		zcst709u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst709u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		DatasetSqlRequest zfit1004u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4202001A_U2");
		zfit1004u.addParamObject("ds_list", ds_list);
		zfit1004u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zfit1004u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		DatasetSqlRequest zfit1078i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4202001A_U3");
		zfit1078i.addParamObject("ds_list", ds_list);
		zfit1078i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zfit1078i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		DatasetSqlRequest zcst701i_2
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4203001A_I1");
		zcst701i_2.addParamObject("ds_list", ds_list);
		zcst701i_2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst701i_2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		DatasetSqlRequest zcst702i_2
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4203001A_I2");
		zcst702i_2.addParamObject("ds_list", ds_list);
		zcst702i_2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst702i_2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		DatasetSqlRequest zcst403u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4203001A_U1");
		zcst403u.addParamObject("ds_list", ds_list);
		zcst403u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst403u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));	

		DatasetSqlRequest zcst701i_3
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4204001A_I1");
		zcst701i_3.addParamObject("ds_list", ds_list);
		zcst701i_3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst701i_3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		DatasetSqlRequest zcst702i_3
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4204001A_I2");
		zcst702i_3.addParamObject("ds_list", ds_list);
		zcst702i_3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst702i_3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		DatasetSqlRequest zcst316u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4204001A_U2");
		zcst316u.addParamObject("ds_list", ds_list);
		zcst316u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst316u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));	

		DatasetSqlRequest zcst704u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4202001A_U4");
		zcst704u.addParamObject("ds_list", ds_list);
		zcst704u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst704u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		DatasetSqlRequest zcst701i_4
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4207001A_I1");
		zcst701i_4.addParamObject("ds_list", ds_list);
		zcst701i_4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst701i_4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));	
		
		DatasetSqlRequest zcst702i_4
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4207001A_I2");
		zcst702i_4.addParamObject("ds_list", ds_list);
		zcst702i_4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst702i_4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));	
		
		DatasetSqlRequest zcst720i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4207001A_I3");
		zcst720i.addParamObject("ds_list", ds_list);
		zcst720i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst720i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));	
		
		DatasetSqlRequest zcst701i_5
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4208001A_I1");
		zcst701i_5.addParamObject("ds_list", ds_list);
		zcst701i_5.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst701i_5.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));	
		
		DatasetSqlRequest zcst702i_5
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4208001A_I2");
		zcst702i_5.addParamObject("ds_list", ds_list);
		zcst702i_5.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst702i_5.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));	
		
		DatasetSqlRequest zcst721i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs42:CS4208001A_I3");
		zcst721i.addParamObject("ds_list", ds_list);
		zcst721i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst721i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));				
		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			String t_seal= "";
			t_seal = ds_list.getColumnAsString(i, "SEAL");

			getConkey.addParamObject("SEAL", t_seal);
			getConkey.addParamObject("S004", ds_list.getColumnAsString(i, "S004"));
			
			getConkey.setRowIndex(i);
			Dataset dsRtngetConkey = (Dataset)executor.query(getConkey).getResultObject();
			
			String conkey ="";
			conkey = dsRtngetConkey.getColumnAsString(0, "ACON");
			//System.out.println(conkey);
			//System.out.println(t_seal);
			
			//내용문 유형에 따라 insert
			if("A".equals(t_seal) || "B".equals(t_seal)){

				zcst701i.setRowIndex(i);
				zcst702i.setRowIndex(i);
				zcst709u.setRowIndex(i);
				zfit1004u.setRowIndex(i);
				zfit1078i.setRowIndex(i);

				executor.execute(zcst701i);
				executor.execute(zcst702i);
				executor.execute(zcst709u);
				executor.execute(zfit1004u);
				executor.execute(zfit1078i);
				
			} else if ("C".equals(t_seal) || "D".equals(t_seal) || "E".equals(t_seal)){

				zcst701i_2.addParamObject("ACON", conkey);
				zcst701i_2.addParamObject("SEAL", t_seal);
				zcst702i_2.addParamObject("ACON", conkey);
				zcst702i_2.addParamObject("SEAL", t_seal);
				zcst403u.addParamObject("ACON", conkey);
				
				zcst701i_2.setRowIndex(i);
				zcst702i_2.setRowIndex(i);
				zcst403u.setRowIndex(i);

				executor.execute(zcst701i_2);
				executor.execute(zcst702i_2);
				executor.execute(zcst403u);
			} else if ("F".equals(t_seal)){
				zcst701i_3.addParamObject("ACON", conkey);
				zcst701i_3.addParamObject("SEAL", t_seal);
				zcst702i_3.addParamObject("ACON", conkey);
				zcst702i_3.addParamObject("SEAL", t_seal);
				zcst316u.addParamObject("ACON", conkey);
				
				zcst701i_3.setRowIndex(i);
				zcst702i_3.setRowIndex(i);
				zcst316u.setRowIndex(i);

				executor.execute(zcst701i_3);
				executor.execute(zcst702i_3);
				executor.execute(zcst316u);
			} else if ("G".equals(t_seal)){
				zcst701i_4.addParamObject("ACON", conkey);
				zcst701i_4.addParamObject("SEAL", t_seal);
				zcst702i_4.addParamObject("ACON", conkey);
				zcst720i.addParamObject("ACON", conkey);
				
				zcst701i_4.setRowIndex(i);
				zcst702i_4.setRowIndex(i);
				zcst720i.setRowIndex(i);

				executor.execute(zcst701i_4);
				executor.execute(zcst702i_4);
				executor.execute(zcst720i);
			} else if ("H".equals(t_seal)){
				zcst701i_5.addParamObject("ACON", conkey);
				zcst701i_5.addParamObject("SEAL", t_seal);
				zcst702i_5.addParamObject("ACON", conkey);
				zcst721i.addParamObject("ACON", conkey);
				
				zcst701i_5.setRowIndex(i);
				zcst702i_5.setRowIndex(i);
				zcst721i.setRowIndex(i);

				executor.execute(zcst701i_5);
				executor.execute(zcst702i_5);
				executor.execute(zcst721i);
			} else {
				throw new BizException(String.valueOf(i + 1) + " 번째 data의 점검이 필요합니다. 실행을 중지 합니다.");
			}
			
		}

		String vkgrp = ds_list.getColumnAsString(0, "VKGRP");
		zcst704u.addParamObject("VKGRP", vkgrp);
		executor.execute(zcst704u);
	}
	
	
}