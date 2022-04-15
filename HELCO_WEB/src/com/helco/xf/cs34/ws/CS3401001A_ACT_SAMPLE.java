package com.helco.xf.cs34.ws;

import java.util.Date;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.sapjco3.Rfc;
import tit.service.sapjco3.RfcFactory;
import tit.service.sapjco3.ValueHelper;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

public class CS3401001A_ACT_SAMPLE extends AbstractAction   {
	 /*public static final String ZWEB_CS_GET_MISU
		= "com.helco.xf.cs34.ws.ZWEB_CS_GET_MISU";*/
	/**
	 * ��ȸ ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");

		// RFC �Լ��� ���� ���� �������� 
		RfcFactory rfcFactory = (RfcFactory) ServiceManagerFactory.getServiceObject("RfcFactoryService");

		// ȣ���ϴ�  RFC �Լ� �� 
		String rfcName = "ZWEB_CS_GET_MISU"; // ȣ���ϴ� rfc �Լ��� 

		try {
			

			// RFC �Լ��� Factory���� �����Ѵ�. 
			Rfc rfc = rfcFactory.createRfc(rfcName);

			rfc.setImportValue("ENDDA", CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "ENDDA")));
			rfc.setImportValue("STADA", CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "STADA")));
			rfc.setImportValue("I_AREA", DatasetUtility.getString(dsCond, "AREA"));
			rfc.setImportValue("I_DDAT", CallSAP.getFormatDate(DatasetUtility.getString(dsCond, "DDAT")));
			rfc.setImportValue("I_GBN", DatasetUtility.getString(dsCond, "GBN"));
			rfc.setImportValue("I_LGORT", DatasetUtility.getString(dsCond, "LGORT"));
			rfc.setImportValue("I_PM", DatasetUtility.getString(dsCond, "PM"));
			rfc.setImportValue("I_VKGRP", DatasetUtility.getString(dsCond, "VKGRP"));
			rfc.setImportValue("I_PROJ", DatasetUtility.getString(dsCond, "PROJ"));

			
			long sTime = System.currentTimeMillis();
			System.out.println("start : " + sTime);
			//rfc ȣ��
			rfc.execute();
			long mTime = System.currentTimeMillis() ;
			System.out.println("����ð� : " + (mTime - sTime));

			String tableName = "T_ITAB";
			
			Dataset ds = new Dataset();

			//MyValueHelper mvalueHelper = new MyValueHelper();
			//rfc.setValueHelper(mvalueHelper);

			ds = rfc.getDatasetFromJcoTable(tableName);

			Dataset dsError = rfc.getDatasetFromJcoTable("��O_ETAB"); 
			
			ds.setId("ds_list");


			System.out.println("��ȯ�ð� : " + ( System.currentTimeMillis()  - sTime));
			
			System.out.println(ds.getRowCount());


			ctx.addOutputDataset(ds);

			if( dsError != null ) {
				ctx.addOutputDataset("ds_error", dsError );
			}

		} catch( Throwable e) {
			e.printStackTrace();
		} finally {

		}
	

	}

	class MyValueHelper implements ValueHelper {
		public Object getJCoValue (String colName, Object colVal, int miFieldType) {
			return colVal;
		}

		@Override
		public Object getDSValue(String colName, Object colVal, int miFieldType) {
			if ( colVal == null ) {
				return null;
			} 
			
			if ( colName.equals("BSTDK") || colName.equals("CONTR_DA") || colName.equals("RKFKDAT")) {
				System.out.println("#######" + colName + "," + colVal.toString());
				return ((Date)colVal).toString().equals("0000-00-00") ? "" : ((Date)colVal).toString();  
			} 
			
			return colVal;
		}

	}
}