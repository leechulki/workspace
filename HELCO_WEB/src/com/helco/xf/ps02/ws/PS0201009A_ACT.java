package com.helco.xf.ps02.ws;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


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

public class PS0201009A_ACT extends AbstractAction {
	public static final String ZPP_G_GET_SPEC_LAYO 
	= "com.helco.xf.ps02.ws.ZPP_G_GET_SPEC_LAYO";
	
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		Dataset dsCondList = ctx.getInputDataset("ds_list");
		SapFunction stub = null;
		Dataset dsList = null;
		Dataset ds_list1 = new Dataset();


		dsCond.addColumn("I_POSID", (short)1, 24);
		dsCond.addColumn("I_BLOCK", (short)1, 24);
		
		ds_list1.addColumn("POSID", (short)1, 24);
		ds_list1.addColumn("BLOCK", (short)1, 1);
		ds_list1.addColumn("SPEC", (short)1, 1);
		ds_list1.addColumn("LAYOUT", (short)1, 1);
		ds_list1.addColumn("MONEY", (short)1, 1);
		
		// TYPE(EL_DHK) 세팅
		for(int i=0; i <dsCondList.getRowCount();i++)
		{
			ds_list1.insertRow(i);
			ds_list1.setColumn(i, "POSID", dsCondList.getColumn(i, "POSID"));
			ds_list1.setColumn(i, "BLOCK", dsCond.getColumn(0, "BLOCK"));
		}
		
		
		ZPPTSLAYO[] list1 = (ZPPTSLAYO[])CallSAP.moveDs2Obj(ds_list1, ZPPTSLAYO.class, "");
		
		
		
		ZPPTSLAYO[] list = new ZPPTSLAYO[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "I_POSID","")
				, DatasetUtility.getString(dsCond, "I_BLOCK","")
				, list1

		};
	
		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_SYSID"),ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps02.ws.ZPP_G_GET_SPEC_LAYO", obj,false);
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : ZPPTSLAYO.getDataset();

	
		// 필요 컬럼 - 추가
		//CallSAP.makeDsCol(dsList, "FLAG", 1);
		// ZPSS007[] -> dsList
		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TAB"));
		
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
