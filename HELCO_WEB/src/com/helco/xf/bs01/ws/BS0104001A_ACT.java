package com.helco.xf.bs01.ws;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;

public class BS0104001A_ACT extends AbstractAction {
	public static final String ZWEB_SD_AGENT_ONHAND
		= "com.helco.xf.bs01.ws.ZWEB_SD_AGENT_ONHAND";
	public static final String ZWEB_SD_GATE0_MANAGEMENT
		= "com.helco.xf.bs01.ws.ZWEB_SD_GATE0_MANAGEMENT";
	/**
	 * ��ȸ ó�� 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		// �Է� �Ķ���� ó�� 
		Dataset dsError = null;
		ZSDS0023[] list = new ZSDS0023[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		SapFunction stub = null;

		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "CBOX1")
				, DatasetUtility.getString(dsCond, "CBOX2")
				, DatasetUtility.getString(dsCond, "CBOX3")
				, DatasetUtility.getString(dsCond, "CBOX4")
				, DatasetUtility.getString(dsCond, "CBOX5")
				, DatasetUtility.getString(dsCond, "CBOX6")
				, DatasetUtility.getString(dsCond, "CBOX7")
				, DatasetUtility.getString(dsCond, "KUNNR")
				, DatasetUtility.getString(dsCond, "LIFNR")
				, DatasetUtility.getString(dsCond, "VKBUR")
				, DatasetUtility.getString(dsCond, "ZZPJT_ID")
				, listMsg
				, list
		};

		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_SD_AGENT_ONHAND, obj);
		
		// ��� Dataset ���� 
		Dataset ds = CallSAP.isJCO() ? new Dataset() : ZSDS0023.getDataset();
		
		ds.setId("ds_list");

		// ��� ���� �ű�� 
		CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));

		//���尳������ Yes, No�� ġȯ
		for(int i=0; i<ds.getRowCount(); i++) {
			if("X".equals(ds.getColumnAsString(i, "SOPTP"))) {
				ds.setColumn(i, "SOPTP", "Yes");
			} else if("".equals(ds.getColumnAsString(i, "SOPTP")) || ds.getColumnAsString(i, "SOPTP") == null) {
				ds.setColumn(i, "SOPTP", "No");
			}
		}
		
		listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
		dsError = CallSAP.makeErrorInfo(listMsg);
		
		if (dsError.getRowCount() == 0) {
			ctx.addOutputDataset(ds);
		} else {
			ctx.addOutputDataset(dsError);
		}
	}
	
	public void save(BusinessContext ctx) throws Exception {
		
		Dataset ds_list_chg = ctx.getInputDataset("ds_list_chg");
		Dataset dsError = null;
		ZSDS0091[] list =  (ZSDS0091[])moveDs2Obj2(ds_list_chg, ZSDS0091.class,"");
		SapFunction stub = null;

		String gSysid = ctx.getInputVariable("G_SYSID");
		
		Object obj[] = new Object[]{
				list
		};

		try {
			
			stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"), ZWEB_SD_GATE0_MANAGEMENT, obj);
						
		} catch (Exception e) {
			System.out.print("\n@@@ ZWEB_SD_GATE0_MANAGEMENT ERROR!!!"); 
			e.printStackTrace();
		}
	}

	/**
	 * Dataset�� �÷��� �������� �־��� Class �� �ش� ��ü�� �����Ͽ� ���� Move �Ѵ�. 
	 * 
	 * @param ds �����ͼ� 
	 * @param cls ó���� ��ü 
	 * @param flag �÷��� �÷� : TMODE�� ��Ī�Ǵ� �̸� (���� ��� ���� �ʿ� ����. )
	 * @return 
	 */
	public static Object moveDs2Obj2(Dataset ds, Class cls, String flag) {
		return moveDs2Obj2(ds, cls, flag, null);
	}
	
	/**
	 * Dataset�� �÷��� �������� �־��� Class �� �ش� ��ü�� �����Ͽ� ���� Move �Ѵ�. 
	 * �Ѱ��� �����͸� ��ü�� �ű� �� Ds2ObjHelper.endRow()�� ȣ���Ͽ� ó�� ���Ḧ �˸���. 
	 * 
	 * @param ds �����ͼ� 
	 * @param cls ó���� ��ü 
	 * @param flag �÷��� �÷� : TMODE�� ��Ī�Ǵ� �̸� (���� ��� ���� �ʿ� ����. )
	 * @return 
	 */	
	public static Object moveDs2Obj2(Dataset ds, Class cls, String flag, Ds2ObjHelper helper) {
		if ( flag != null && !flag.equals("")) {
			for( int i = ds.getRowCount()-1; i >= 0; i--) {
				// Flag �÷� �ܴ̿� ���� 
				if ( DatasetUtility.getString(ds,i,flag, "").equals("") 
					|| DatasetUtility.getString(ds,i,flag, "").equals("0")) {
					ds.deleteRow(i);
				}
			}
		}
		
		// �ش� Type���� �迭 ���� 
		Object obj = Array.newInstance(cls, ds.getRowCount());
		Method[] mArr = cls.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			// Set �޼ҵ常 �������� 
			if ( mArr[i].getName().startsWith("set")) {
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
							b = b.setScale(2, RoundingMode.DOWN);
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
				
				// ó�� ���� �˸�. 
				if ( helper != null ) {
					helper.endMoveRow(ds, i, tmpObj);
				}
			} catch( Exception e){
				e.printStackTrace();
			}
			
			// Array ���� ���� 
			Array.set(obj, i, tmpObj);
		}
		return obj;
	}	
}
