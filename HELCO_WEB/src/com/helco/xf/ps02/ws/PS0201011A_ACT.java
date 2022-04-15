
package com.helco.xf.ps02.ws;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class PS0201011A_ACT extends AbstractAction {
	
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;

		ZPSS033[] list = new ZPSS033[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
					DatasetUtility.getString(dsCond, "I_POSID","")
					, DatasetUtility.getString(dsCond, "I_SMAN","")
					, CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_STND_DATE",""))
					, DatasetUtility.getString(dsCond, "I_VKGRP","")
					, DatasetUtility.getString(dsCond, "I_ZZACTSS","")
					, DatasetUtility.getString(dsCond, "I_ZZPMNUM","")
					, listMsg
					, list
		};
			
			
		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0011", obj,false);
			
		//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0011", obj, false);
			
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZPSS033.getDataset();
		dsList.setId("ds_list");

		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TAB"));
		
		for(int i=0; i<dsList.getRowCount(); i++)
		{
			dsList.setColumn(i,"CDATE",StringOperator.replaceString(dsList.getColumnAsString(i, "CDATE"), "-", ""));
			dsList.setColumn(i,"ZZPMJID",StringOperator.replaceString(dsList.getColumnAsString(i, "ZZPMJID"), "-", ""));
		}
		
		
		ctx.addOutputDataset(dsList);
	}

	public void query2(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;

		ZPSS034[] list = new ZPSS034[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				     CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_DATEF",""))
				    , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_DATET",""))
					, DatasetUtility.getString(dsCond, "I_POSID","")
					, DatasetUtility.getString(dsCond, "I_SMAN","")
					, DatasetUtility.getString(dsCond, "I_VKGRP","")
					, DatasetUtility.getString(dsCond, "I_ZZACTSS","")
					, DatasetUtility.getString(dsCond, "I_ZZPMNUM","")
					, listMsg
					, list
		};
			
			
		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0012", obj,false);
			
		//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0012", obj, false);
			
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZPSS034.getDataset();
		dsList.setId("ds_list");

		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TAB"));
		
		for(int i=0; i<dsList.getRowCount(); i++)
		{
			dsList.setColumn(i,"CDATE",StringOperator.replaceString(dsList.getColumnAsString(i, "CDATE"), "-", ""));
			dsList.setColumn(i,"DPCPD",StringOperator.replaceString(dsList.getColumnAsString(i, "DPCPD"), "-", ""));
			dsList.setColumn(i,"ZZSHIP1",StringOperator.replaceString(dsList.getColumnAsString(i, "ZZSHIP1"), "-", ""));
			dsList.setColumn(i,"MAX_EXPLO",StringOperator.replaceString(dsList.getColumnAsString(i, "MAX_EXPLO"), "-", ""));
		}
		
		
		ctx.addOutputDataset(dsList);
	}

	public void query3(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;

		ZPSS035[] list = new ZPSS035[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
					DatasetUtility.getString(dsCond, "I_POSID","")
					, DatasetUtility.getString(dsCond, "I_SMAN","")
				    , CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "I_STND_DATE",""))
					, DatasetUtility.getString(dsCond, "I_VKGRP","")
					, DatasetUtility.getString(dsCond, "I_ZZACTSS","")
					, DatasetUtility.getString(dsCond, "I_ZZPMNUM","")
					, listMsg
					, list
		};
			
			
		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0013", obj,false);
			
		//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0012", obj, false);
			
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZPSS035.getDataset();
		dsList.setId("ds_list");

		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TAB"));
		
		for(int i=0; i<dsList.getRowCount(); i++)
		{
			dsList.setColumn(i,"CDATE",StringOperator.replaceString(dsList.getColumnAsString(i, "CDATE"), "-", ""));
			dsList.setColumn(i,"ZZSHIP1",StringOperator.replaceString(dsList.getColumnAsString(i, "ZZSHIP1"), "-", ""));
			dsList.setColumn(i,"BEST_DT1",StringOperator.replaceString(dsList.getColumnAsString(i, "BEST_DT1"), "-", ""));
			dsList.setColumn(i,"BEST_DT2",StringOperator.replaceString(dsList.getColumnAsString(i, "BEST_DT2"), "-", ""));
			dsList.setColumn(i,"BEST_DT3",StringOperator.replaceString(dsList.getColumnAsString(i, "BEST_DT3"), "-", ""));
			dsList.setColumn(i,"BEST_DT4",StringOperator.replaceString(dsList.getColumnAsString(i, "BEST_DT4"), "-", ""));
			dsList.setColumn(i,"BEST_DT5",StringOperator.replaceString(dsList.getColumnAsString(i, "BEST_DT5"), "-", ""));
			dsList.setColumn(i,"MAX_EXPLO",StringOperator.replaceString(dsList.getColumnAsString(i, "MAX_EXPLO"), "-", ""));
		}
		
		
		ctx.addOutputDataset(dsList);
	}
	
}
