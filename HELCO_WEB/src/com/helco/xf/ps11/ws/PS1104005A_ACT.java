package com.helco.xf.ps11.ws;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.tobesoft.platform.data.Dataset;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class PS1104005A_ACT extends AbstractAction {
	public static final String ZWEB_SD_CHARS_BY_POSID 
	= "com.helco.xf.ps11.ws.ZWEB_SD_CHARS_BY_POSID";
	
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCondList = ctx.getInputDataset("ds_list");
		SapFunction stub = null;
		Dataset dsList = null;
		Dataset dsTmpList = new Dataset();
		Dataset dsCond = new Dataset();
		Dataset ds_list1 = new Dataset();
		Dataset ds_list2 = new Dataset();
		//BAPI_WBS_LIST_TAB[] list1 = new BAPI_WBS_LIST_TAB[]{};	// POSID
		//CIF_T_DISP_T_CHR[] list2 = new CIF_T_DISP_T_CHR[]{};	// CHARS
		

		dsCond.addColumn("IV_PSPID", (short)1, 24);

		
		dsCond.insertRow(0);
		
		
		ds_list1.addColumn("WBS_ELEMENT", (short)1, 24);
		ds_list2.addColumn("ATNAM", (short)1, 30);
		
		/*
		dsTmpList.addColumn("POSID", (short)1, 24);
		dsTmpList.addColumn("GBN", (short)1, 10);
		dsTmpList.addColumn("ATNAM", (short)1, 30);
		dsTmpList.addColumn("ATBEZ", (short)1, 30);
		dsTmpList.addColumn("ATWRT", (short)1, 30);
		dsTmpList.addColumn("ATFLV", (short)1, 16);
		dsTmpList.addColumn("ATFLB", (short)1, 16);
		dsTmpList.addColumn("ATWTB", (short)1, 30);
*/
		 

		
		int k = 0;
		
		// TYPE(EL_DHK) 세팅
		for(int i=0; i <dsCondList.getRowCount();i++)
		{
			dsCond.setColumn(0, "IV_PSPID", dsCondList.getColumn(i, "PSPID"));
			ds_list1.insertRow(i);
			ds_list1.setColumn(i, "WBS_ELEMENT", dsCondList.getColumn(i, "POSID"));
			//list1[i].setWBS_ELEMENT(dsCondList.getColumn(i, "POSID").toString());
			
		}
		
		
		for(int i=0;i<9;i++)
		{
			ds_list2.insertRow(i);
		}
		
		ds_list2.setColumn(0, "ATNAM", "EL_DHK");
		ds_list2.setColumn(1, "ATNAM", "EL_ATYP");
		ds_list2.setColumn(2, "ATNAM", "EL_AUSE");
		ds_list2.setColumn(3, "ATNAM", "EL_AMAN");
		ds_list2.setColumn(4, "ATNAM", "EL_ASPD");
		ds_list2.setColumn(5, "ATNAM", "EL_ASTQ");
		ds_list2.setColumn(6, "ATNAM", "EL_ETM");
		ds_list2.setColumn(7, "ATNAM", "EL_ACAPA");
		ds_list2.setColumn(8, "ATNAM", "EL_ABANK");
		
		
			 

		
		/*
		list2[0].setATNAM("EL_DHK");
		list2[1].setATNAM("EL_ATYP");
		list2[2].setATNAM("EL_AUSE");
		list2[3].setATNAM("EL_AMAN");
		list2[4].setATNAM("EL_ASPD");
		list2[5].setATNAM("EL_ASTQ");
		list2[6].setATNAM("EL_ETM");*/
		
		BAPI_WBS_LIST_TAB[] list1 = (BAPI_WBS_LIST_TAB[])CallSAP.moveDs2Obj(ds_list1, BAPI_WBS_LIST_TAB.class, "");
		CIF_T_DISP_T_CHR[] list2 = (CIF_T_DISP_T_CHR[])CallSAP.moveDs2Obj(ds_list2, CIF_T_DISP_T_CHR.class, "");
		
		
		
		ZWBS0004[] list = new ZWBS0004[]{};
		BAPIRET2[] listMsg = new BAPIRET2[]{};
		Object obj[] = new Object[]{
				list2
				, list1
				, DatasetUtility.getString(dsCond, "IV_PSPID","")
				, listMsg
				, list
				
				
		};
		
		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps11.ws.ZWEB_SD_CHARS_BY_POSID", obj);
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZWBS0004.getDataset();

	
		// 필요 컬럼 - 추가
		//CallSAP.makeDsCol(dsList, "FLAG", 1);
		// ZPSS007[] -> dsList
		CallSAP.moveObj2Ds(dsList, stub.getOutput("TT_CHARS"));
		
		dsList.setId("ds_list_tmp");
			

		ctx.addOutputDataset(dsList);
	}
	
	public void query2(BusinessContext ctx) throws Exception {
		Dataset dsCondList = ctx.getInputDataset("ds_rfc1");
		Dataset ds_list1 = ctx.getInputDataset("ds_rfc2");
		Dataset ds_list2 = ctx.getInputDataset("ds_rfc3");
		SapFunction stub = null;
		Dataset dsList = null;
		Dataset dsTmpList = new Dataset();
		Dataset dsCond = new Dataset();

		//BAPI_WBS_LIST_TAB[] list1 = new BAPI_WBS_LIST_TAB[]{};	// POSID
		//CIF_T_DISP_T_CHR[] list2 = new CIF_T_DISP_T_CHR[]{};	// CHARS
		

		dsCond.addColumn("IV_PSPID", (short)1, 24);

		
		dsCond.insertRow(0);
		
		
		int k = 0;
		
		// TYPE(EL_DHK) 세팅
		for(int i=0; i <dsCondList.getRowCount();i++)
		{
			dsCond.setColumn(0, "IV_PSPID", dsCondList.getColumn(i, "PSPID"));
		}

		
		BAPI_WBS_LIST_TAB[] list1 = (BAPI_WBS_LIST_TAB[])CallSAP.moveDs2Obj(ds_list1, BAPI_WBS_LIST_TAB.class, "");
		CIF_T_DISP_T_CHR[] list2 = (CIF_T_DISP_T_CHR[])CallSAP.moveDs2Obj(ds_list2, CIF_T_DISP_T_CHR.class, "");
		
		
		
		ZWBS0004[] list = new ZWBS0004[]{};
		BAPIRET2[] listMsg = new BAPIRET2[]{};
		Object obj[] = new Object[]{
				list2
				, list1
				, DatasetUtility.getString(dsCond, "IV_PSPID","")
				, listMsg
				, list
				
				
		};
		
		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps11.ws.ZWEB_SD_CHARS_BY_POSID", obj,false);
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZWBS0004.getDataset();

	
		// 필요 컬럼 - 추가
		//CallSAP.makeDsCol(dsList, "FLAG", 1);
		// ZPSS007[] -> dsList
		CallSAP.moveObj2Ds(dsList, stub.getOutput("TT_CHARS"));
		
		dsList.setId("ds_list_tmp");
			

		ctx.addOutputDataset(dsList);
	}

	class MyDatasetHelper implements DatasetHelper {
		public Object getDsValue(String dsColName, Object colValue,
				Object orgObj) {

			if ( colValue == null ) {
				return null;
			}

			if ( dsColName.equals("EXPORT") ) {
				return ((String) colValue).equals("0") ? "1" : "0";
			} else if ( dsColName.equals("INSGB")) {
				return ((String) colValue).equals("X") ? "1" : "0";
			}
			return colValue;
		}
	}
	
	
	
}
