package com.helco.xf.ps01.ws;

import java.sql.ResultSet;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.ps02.ws.ZPSS007;
import com.helco.xf.ps02.ws.ZPSS008;
import com.helco.xf.ps02.ws.ZPSS009;
import com.helco.xf.ps11.ws.BAPI_WBS_LIST_TAB;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.Variant;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SimpleSqlExecutor;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class PS0101001A_ACT extends AbstractAction 
{
	private static final long serialVersionUID = 5661911687794518235L;

	/**
	 * query : Ư�̻��� ��������
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception 
	{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;
		Dataset dsList2 = new Dataset();

		TLINE[] list = new TLINE[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "PSPID","")
				, listMsg
				, list
		};

		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps01.ws.ZWEB_PS_GET_0009", obj,false);
		//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps01.ws.ZWEB_PS_GET_0009", obj, false);
		
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : TLINE.getDataset();
		
		// ZQMS028[] -> dsList
		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TEXT"));

		String textOrg = "";
		String text = "";
		for(int i = 0; i<dsList.getRowCount(); i++)
		{	
			//Ư�̻��׿���  �ݾ� �κ��� �����ϱ�����
			textOrg = RegExp.replaceAll(
					dsList.getColumnAsString(i, "TDLINE"),
					"\\x5c[0-9\\s\\-\\+\\,\\.]+",
					"*");
			// <br>�� line skip���� ��ȯ
			text += RegExp.replaceAll(textOrg,"<(B|b)(R|r)(\\/)?>","\n\n")+"\n\n";
		}
//		dsList.setColumn(0,"TEXT",RegExp.replaceAll(textOrg,"<(B|b)(R|r)(\\/)?>","\n\n"));

		dsList2.setId("ds_list");
		CallSAP.makeDsCol(dsList2, "TEXT", 1);
		dsList2.appendRow();
		dsList2.setColumn(0, "TEXT", text);

		ctx.addOutputDataset(dsList2);
	}

	/**
	 * query : �������� ��������
	 * @param ctx
	 * @throws Exception
	 */
	public void query2(BusinessContext ctx) throws Exception 
	{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		String zmanYn = DatasetUtility.getString(dsCond, "ZMANYN");
		// �Է� �Ķ���� ó�� 
		ZSDS0014[] list1 = new ZSDS0014[]{};
		ZSDS0015[] list2 = new ZSDS0015[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "PSPID")
				, listMsg  
				, list2
				, list1
		};
		// ȣ�� 
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps01.ws.ZWEB_SD_SUGM", obj, false);

		// ��� Dataset ���� 
		Dataset ds1 = CallSAP.isJCO() ? new Dataset() : ZSDS0014.getDataset();
		ds1.setId("ds_list1");
		// ��� ���� �ű�� 
		CallSAP.moveObj2Ds(ds1, stub.getOutput("T_ITAB"));
		ctx.addOutputDataset(ds1);
		//������ Ÿ���� 0000-00-00���·� �Ѿ��. 00000000���·� ��ȯ
		ds1.setColumn(0,"BSTDK",StringOperator.replaceString(ds1.getColumnAsString(0, "BSTDK"), "-", ""));
		ds1.setColumn(0,"NETWR",Double.parseDouble(ds1.getColumnAsString(0,"NETWR"))*100);

		// ��� Dataset ���� 
		Dataset ds2 = CallSAP.isJCO() ? new Dataset() : ZSDS0015.getDataset();
		ds2.setId("ds_list2");
		// ��� ���� �ű�� 
		CallSAP.moveObj2Ds(ds2, stub.getOutput("P_ITAB"));
		ctx.addOutputDataset(ds2);
		
		// �ʿ� �÷� - �߰�
		CallSAP.makeDsCol(ds2, "FAKWRVAT", 1);
		CallSAP.makeDsCol(ds2, "NETWRRATE", 1);
		CallSAP.makeDsCol(ds2, "DMBTRRATE", 1);

		double fakwr = 0;
		double netwr = 0;
		double dmbtr = 0;
		for(int i=0; i<ds2.getRowCount(); i++)
		{
			ds2.setColumn(i,"BILLRT",ds2.getColumnAsString(i,"BILLRT")+"%");
			
			if(ds2.getColumnAsString(i,"ZTERM") == null || ds2.getColumnAsString(i,"ZTERM").equals("")) ds2.setColumn(i,"ZTERM","");
			else ds2.setColumn(i,"ZTERM",(ds2.getColumnAsString(i,"ZTERM")).substring(0, 1));
			
			if(ds2.getColumnAsDouble(i,"FAKWR") == null || ds2.getColumnAsDouble(i,"FAKWR").equals("")) ds2.setColumn(i,"FAKWR",0); 
			else ds2.setColumn(i,"FAKWR",ds2.getColumnAsDouble(i,"FAKWR")*100);

			if(ds2.getColumnAsDouble(i,"FAKWR") == null || ds2.getColumnAsDouble(i,"FAKWR").equals("")) ds2.setColumn(i,"FAKWRVAT",0);
			else ds2.setColumn(i,"FAKWRVAT",Math.round(ds2.getColumnAsDouble(i,"FAKWR")*0.1));
			
			if(ds2.getColumnAsDouble(i,"NETWR") == null || ds2.getColumnAsDouble(i,"NETWR").equals("")) ds2.setColumn(i,"NETWR",0); 
			else ds2.setColumn(i,"NETWR",ds2.getColumnAsDouble(i,"NETWR")*100);
			
			if(ds2.getColumnAsDouble(i,"DMBTR") == null || ds2.getColumnAsDouble(i,"DMBTR").equals("")) ds2.setColumn(i,"DMBTR",0); 
			else ds2.setColumn(i,"DMBTR",ds2.getColumnAsDouble(i,"DMBTR")*100);
			
			if(ds2.getColumnAsString(i,"FKDAT") == null || ds2.getColumnAsString(i,"FKDAT").equals("") || ds2.getColumnAsString(i,"FKDAT").equals("0000-00-00")) ds2.setColumn(i,"FKDAT","");
//			else ds2.setColumn(i,"FKDAT",StringOperator.replaceString(ds2.getColumnAsString(i,"FKDAT"),"-",""));
			
			if(ds2.getColumnAsString(i,"BUDAT") == null || ds2.getColumnAsString(i,"BUDAT").equals("") || ds2.getColumnAsString(i,"BUDAT").equals("0000-00-00")) ds2.setColumn(i,"BUDAT","");
//			else ds2.setColumn(i,"BUDAT",StringOperator.replaceString(ds2.getColumnAsString(i,"BUDAT"),"-",""));

			if(ds2.getColumnAsString(i,"ZFBDT") == null || ds2.getColumnAsString(i,"ZFBDT").equals("") || ds2.getColumnAsString(i,"ZFBDT").equals("0000-00-00")) ds2.setColumn(i,"ZFBDT","");
//			else ds2.setColumn(i,"ZFBDT",StringOperator.replaceString(ds2.getColumnAsString(i,"ZFBDT"),"-",""));
			
			//if(ds2.getColumnAsDouble(i,"FAKWR") > 0) fakwr += ds2.getColumnAsDouble(i,"FAKWR") + ds2.getColumnAsDouble(i,"FAKWRVAT");
			//if(ds2.getColumnAsDouble(i,"NETWR") > 0) netwr += ds2.getColumnAsDouble(i,"NETWR");
			//if(ds2.getColumnAsDouble(i,"DMBTR") > 0) dmbtr += ds2.getColumnAsDouble(i,"DMBTR");
            
			
			//=======================================�ΰ��� �����ؼ� �������� �ҽ�����(��������)=======================================
			/*
			if( dsCond.getColumnAsString(0,"PSPID").substring(0, 1).equals("E") || dsCond.getColumnAsString(0,"PSPID").substring(0, 1).equals("C") || dsCond.getColumnAsString(0,"PSPID").substring(0, 1).equals("T") )
			{	
				fakwr += ds2.getColumnAsDouble(i,"FAKWR");
				netwr += ds2.getColumnAsDouble(i,"NETWR");
				dmbtr += ds2.getColumnAsDouble(i,"DMBTR");
			}
			else
			{
				fakwr += ds2.getColumnAsDouble(i,"FAKWR") + ds2.getColumnAsDouble(i,"FAKWRVAT");
				netwr += ds2.getColumnAsDouble(i,"NETWR");
				dmbtr += ds2.getColumnAsDouble(i,"DMBTR");
			}
			*/
			fakwr += ds2.getColumnAsDouble(i,"FAKWR");
			netwr += ds2.getColumnAsDouble(i,"NETWR");
			dmbtr += ds2.getColumnAsDouble(i,"DMBTR");			
			//=====================================================(������)=====================================================
		}
		Double netwrRate = 0.0;
		Double dmbtrRate = 0.0;
		if(netwr > 0) netwrRate = (netwr/fakwr)*100;
		else netwrRate = 0.0;
		if(dmbtr > 0) dmbtrRate = (dmbtr/fakwr)*100;
		else dmbtrRate = 0.0;
		
		int addRow = ds2.appendRow();
	    
		ds2.setColumn(addRow,"MSTXT","��  ��");
		ds2.setColumn(addRow,"ZTERM","��û�� / �̼���");
		ds2.setColumn(addRow,"NETWRRATE",String.valueOf(Math.round(netwrRate*10)/10.0)+'%');
		ds2.setColumn(addRow,"DMBTRRATE",String.valueOf(Math.round(dmbtrRate*10)/10.0)+'%');		
		if(zmanYn.equals("Y")){
			ds2.setColumn(addRow,"FKDAT",String.valueOf(fakwr-netwr));
			ds2.setColumn(addRow,"BUDAT",String.valueOf(netwr-dmbtr));
			ds2.setColumn(addRow,"DMBTR",dmbtr);
			ds2.setColumn(addRow,"FAKWR",fakwr);
			ds2.setColumn(addRow,"NETWR",netwr);
		}
		else{
			ds2.setColumn(addRow,"FKDAT","");
			ds2.setColumn(addRow,"BUDAT","");
			ds2.setColumn(addRow,"DMBTR","");
			ds2.setColumn(addRow,"FAKWR","");
			ds2.setColumn(addRow,"NETWR","");
		}
	}
	
	public void execute(BusinessContext ctx) throws Exception{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		String sPosid = DatasetUtility.getString(dsCond, "POSID");
		
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("ps01:PS0101001A_S91");
		sqlRequest.addParamObject("POSID", sPosid);

		SqlExecutor db = null;
		String db_con = "jdbc/HESPLM";
		
		DatasetSqlExecutor SqlExecutor  = new DatasetSqlExecutor(getConnection(db_con));
		Dataset ds_pdm =  (Dataset)SqlExecutor.query(sqlRequest).getResultObject();

		ctx.addOutputDataset("ds_pdm",ds_pdm);
	}
	
	// �ŷڼ� �Ƿڿ��� PDM ���� �����ȣ Ȯ�� ���� �߰� 20210715
	public void pdmexecute(BusinessContext ctx) throws Exception{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		String sPosid = DatasetUtility.getString(dsCond, "POSID");
		
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("ps01:PS0101001A_S99");
		sqlRequest.addParamObject("POSID", sPosid);

		SqlExecutor db = null;
		String db_con = "jdbc/HESPLM";
		
		DatasetSqlExecutor SqlExecutor  = new DatasetSqlExecutor(getConnection(db_con));
		Dataset ds_pdm =  (Dataset)SqlExecutor.query(sqlRequest).getResultObject();

		ctx.addOutputDataset("ds_pdm",ds_pdm);
	}
	
	public void getEHDOPinfo(BusinessContext ctx) throws Exception{
		Dataset dsCond = ctx.getInputDataset("ds_cond_tmp");
		String sPosid = DatasetUtility.getString(dsCond, "POSID");
		
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("ps11:PS1102003A_S5");
		sqlRequest.addParamObject("POSID", sPosid);

		SqlExecutor db = null;
		String db_con = "jdbc/HESPLM";
		
		DatasetSqlExecutor SqlExecutor  = new DatasetSqlExecutor(getConnection(db_con));
		Dataset ds_pdm =  (Dataset)SqlExecutor.query(sqlRequest).getResultObject();

		ctx.addOutputDataset("ds_pdm",ds_pdm);
	}
	
	public void getGateInfo(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;
		
		
		ZPPSGATESS[] list = (ZPPSGATESS[])CallSAP.moveDs2Obj(dsCond, ZPPSGATESS.class, "");
		

		Object obj[] = new Object[]{
				list
		};
		
		
		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps01.ws.ZPP_GET_GATE_ALL", obj,false);
		
		//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZWEB_PS_GET_0003", obj, false);
		
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZPPSGATESS.getDataset();
		
		dsList.setId("ds_list");
		// �ʿ� �÷� - �߰�
		//CallSAP.makeDsCol(dsList, "FLAG", 1);
		// ZPSS007[] -> dsList
		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TAB"));

		ctx.addOutputDataset(dsList);
	}
	
	/**
	 * query : ������������(ZPP_GET_PRDUSER) - 2021-01-26 ������GJN ��û���� ����.
	 * @param ctx
	 * @throws Exception
	 */
	public void getPrdUser(BusinessContext ctx) throws Exception 
	{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = new Dataset();
		Dataset dsList2 = new Dataset();

		TLINE[] list = new TLINE[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "POSID","")
				//, listMsg
		};

		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps01.ws.ZPP_GET_PRDUSER", obj,false);
		//stub = CallSAP.callSapEai(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps01.ws.ZWEB_PS_GET_0009", obj, false);
		
		// Dataset make
		//dsList = CallSAP.isJCO() ? new Dataset() : TLINE.getDataset();

		//CallSAP.moveObj2Ds(dsList, stub.getOutput("O_PUSER"));
		//CallSAP.moveObj2Ds(dsList2, stub.getOutput("O_PUID"));
		
		//String pUser = dsList.getColumnAsString(0, "O_PUSER");
		//String pUid  = dsList2.getColumnAsString(0, "O_PUID");

		ctx.addOutputVariable("PUSER", stub.getOutput("O_PUSER"));
		ctx.addOutputVariable("PUID", stub.getOutput("O_PUID"));
	}
	class MyDatasetHelper implements DatasetHelper 
	{
		public Object getDsValue(String dsColName, Object colValue,	Object orgObj) 
		{
			if ( colValue == null ) 
			{
				return null;
			}

			if ( dsColName.equals("EXPORT") ) 
			{
				return ((String) colValue).equals("0") ? "1" : "0";
			} 
			else if ( dsColName.equals("INSGB")) 
			{
				return ((String) colValue).equals("X") ? "1" : "0";
			}

			return colValue;
		}
	}
}
