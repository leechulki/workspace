package com.helco.xf.comm;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Field;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import tit.biz.BusinessException;
import tit.service.business.config.ConfigUtility;
import tit.util.DatasetUtility;
import tit.util.StringOperator;
import tit.util.TitUtility;

/**
 * SAP ȣ�� 
 */
public class CallSAP {
	public static String NO_DATA_FOUND = "NO_DATA_FOUND";
	public static String INVALID_TMODE = "INVALID_TMODE";
	public static String INVALID_RQDATE = "INVALID_RQDATE";
	public static String INVALID_DATA = "INVALID_DATA";
	
	public static SapFunction callSap(String clsName, Object[] obj) throws CommRfcException, Exception {
		return callSap(ConfigUtility.getString("MANDT"), clsName, obj);
	}
	
	public static SapFunction callSap(String mandt, String clsName, Object[] obj) throws CommRfcException, Exception {
		return callSap(mandt, clsName, obj, false);
	}

	/**
	 * SAP RPC ȣ�� 
	 * @param cls
	 * @param obj
	 * @return
	 * @throws CommRfcException
	 * @throws Exception
	 */
	public static SapFunction callSap(String clsName, Object[] obj, boolean isUserDef) throws CommRfcException, Exception {
		return callSap(ConfigUtility.getString("MANDT"), clsName, obj, isUserDef);
	}
	/**
	 * SAP RPC ȣ�� 
	 * @param cls
	 * @param obj
	 * @return
	 * @throws CommRfcException
	 * @throws Exception
	 */
	public static SapFunction callSap(String mandt, String clsName, Object[] obj, boolean isUserDef) throws CommRfcException, Exception {
		return callSap("", mandt, clsName, obj, isUserDef);
	}	

	
	public static SapFunction callSap(String sysid, String mandt, String clsName, Object[] obj, boolean isUserDef) throws CommRfcException, Exception {
		return callSap(sysid, mandt, "", clsName, obj, isUserDef);
	}

	
	public static SapFunction callSap(String sysid, String mandt, String langu, String clsName, Object[] obj, boolean isUserDef) throws CommRfcException, Exception {
		SapFunction stub = null;
		try {
			if ( !isUserDef ) {
				clsName = clsName + (isJCO() ? "_JCO": "");
			}	
			Class cls = Class.forName(clsName);
			stub = (SapFunction)cls.newInstance();
		} catch( InstantiationException e) {
			throw new Exception( "Sap Rpc [ " + clsName + "] is not found.");
		}
		//stub.setIsEai("");  //�߰� 
		// ȣ�� 
		callOperation(stub, obj, sysid, mandt, langu.toUpperCase());
		return stub;
	}
	
	
	
    //==========eai�����߰�===================
	/*
	public static SapFunction callSapEai(String sysid, String mandt, String clsName, Object[] obj, boolean isUserDef) throws CommRfcException, Exception {
		SapFunction stub = null;		
		try {
			if ( !isUserDef ) {
				clsName = clsName + (isJCO() ? "_JCO": "");
			}	
			Class cls = Class.forName(clsName);
			stub = (SapFunction)cls.newInstance();
		} catch( InstantiationException e) {
			throw new Exception( "Sap Rpc [ " + clsName + "] is not found.");
		}
		stub.setIsEai("EAI");
		// ȣ�� 
		callOperation(stub, obj, sysid, mandt, "");
		return stub;		
	}		
    */
	
	/**
	 * WS �Ǵ� JCO ȣ�� 
	 * @param f
	 * @param obj
	 * @throws Exception
	 */
	public static void callOperation(SapFunction f, Object[] obj, String sysid, String mandt, String langu) throws Exception {
		try {			
			int r = f.callOperation(obj, sysid, mandt, langu);
			if ( r != 0 ) {
				// ������ �ƴ�. 
			}
		} catch( CommRfcException c) {
			if ( c.getFaultString().equals(CallSAP.NO_DATA_FOUND)) {
				// ó�� ���� ������ ����.
			} else {
				c.printStackTrace();  // ���ʿ��� ���� �α� ���� 20121012 hss
				throw new BusinessException(c.getFaultString());
			}
		} catch( JCO.AbapException a ) {
			a.printStackTrace();
		//	if ( a.getKey().equals(CallSAP.NO_FATA_FOUND)) {
				//
		//	} else {
				throw new BusinessException(a.getKey());
		//	}
		} catch( Exception e) {
			throw e;
		}
	}
	

	/**
	 * WS �Ǵ� JCO ȣ�� 
	 * @param f
	 * @param obj
	 * @throws Exception
	 */
	public static void callInteral(SapFunction f, Object[] obj, String mandt ) throws Exception {
		callInteral(f, obj, "", mandt);
	}
	public static void callInteral(SapFunction f, Object[] obj, String sysid, String mandt ) throws Exception {
		//SapFunction stub = null;
		//f.setIsEai(""); // �߰�
		callInteral(f, obj, sysid, mandt, "");
	}
	
    //==========eai�����߰�===================
	/*
	public static void callInteralEai(SapFunction f, Object[] obj, String sysid, String mandt ) throws Exception {
		//SapFunction stub = null;
		f.setIsEai("EAI");
		callInteral(f, obj, sysid, mandt, "");
	}
	*/
	
	public static void callInteral(SapFunction f, Object[] obj, String sysid, String mandt, String langu) throws Exception {
		f.callOperation(obj, sysid, mandt, langu);
	}	
	/**
	 * Dataset�� �÷� ����� 
	 * @param ds
	 * @param id
	 */
	public static void makeDsCol(Dataset ds , String id) {
		makeDsCol(ds , id, 255);
	}
	
	public static void makeDsCol(Dataset ds , String id, int size) {
		makeDsCol(ds, id, size, ColumnInfo.COLTYPE_STRING);
	}
	
	public static void makeDsCol(Dataset ds , String id, int size, short type) {
		ds.addColumn(id, type, size);
	}
	
	public static Dataset makeErrorInfo( ZQMSEMSG[] list) {
		Dataset dsError = new Dataset("ds_error");
		makeDsCol(dsError , "IDX");
		makeDsCol(dsError , "ERRMSG", 200);
		
		if( list != null && list.length > 0 ) {
			for( int i = 0; i < list.length; i++) {
				dsError.appendRow();
				dsError.setColumn(i, "IDX", list[i].getIDX().toString());
				dsError.setColumn(i, "ERRMSG", list[i].getERRMSG());
			}
		}
		
		return dsError;
	}
	
	public static String getFormatDate(String date) {
		if( date != null && date.length() == 10 ) {
			return date;
		}
		
		if ( date == null || date.trim().equals("") || date.length() != 8) {
			return "0000-00-00";
		}
		
		return date.substring(0, 4) 
			+ "-" + date.substring(4,6) 
			+ "-" + date.substring(6) ;
	}
	
	public static String removeFormatDate(String date) {
		if ( date == null && date.length() != 10) {
			return date;
		}
		
		if ( date.equals("0000-00-00")) {
			return "";
		}
		
		return StringOperator.replaceString(date, "-", "");
	}
	public static String getCheckValue(String orgValue) {
		if ( orgValue == null || orgValue.equals("")) {
			return "";
		} else if ( orgValue.equals("0")) {
			return "";
		}
		
		return orgValue;
	}
	
	public static void moveObj2Ds(Dataset ds, Object obj){
		moveObj2Ds(ds, obj, null);
	}
	/**
	 * Object�� ���� Dataset���� ��ȯ 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveObj2Ds(Dataset ds, Object obj, DatasetHelper dsConvert) {
		if ( obj instanceof JCO.Table ) {
			moveObj2DsForJCO(ds, obj, dsConvert);
			return;
		}
		
    	if ( obj == null ) {
    		return;
    	}
    	
    	Object[] list = null;
    	if ( obj instanceof Object[] ) {
    		list = (Object[]) obj;
    	} else {
    		list = new Object[]{obj};
    	}
    	
    	if ( list.length == 0 || list[0] == null ) {
    		return;
    	}
    	
    	Class cls = list[0].getClass();
    	
    	// Method ���� ���� 
    	HashMap mData = new HashMap();
    	for( int i = 0; i < ds.getColumnCount(); i++ ) {
    		try {
    			Method m = cls.getMethod("get" + ds.getColumnID(i) , null);
    			mData.put(ds.getColumnID(i), m);
    		} catch( NoSuchMethodException e) {
    			// ����
    		}
    	}
    	
    	Method m = null;
    	Object tmpObj = null;
    	Object tmpValue = null;
    	for( int r = 0; r < list.length; r++ ) {
    		tmpObj = list[r];
    		ds.appendRow();
    		for( int c = 0; c < ds.getColumnCount(); c++ ) {
    			m = (Method)mData.get(ds.getColumnID(c));
    			tmpValue = null;
    			if (m != null ) {
    				try {
    					tmpValue = m.invoke(tmpObj, null);
    					
    					// DT�� ����Ǹ�, �⺻������ 8 �ڸ��� �Ȱ� ��¥�� ó�� 
    					if ( ds.getColumnID(c).endsWith("DT")
    						&& ds.getColumnInfo(c).getColumnSize() == 8) {
    						tmpValue = CallSAP.removeFormatDate((String)tmpValue);
    					}
    				} catch( Exception e){
    					
    				}
    			}
    			
    			if ( dsConvert != null ) {
    				tmpValue = dsConvert.getDsValue(ds.getColumnID(c), tmpValue, tmpObj);
    			}
    			
    			if ( tmpValue == null ) {
    				ds.setColumn(r, ds.getColumnID(c), (String)null);
    			} else if ( tmpValue instanceof BigDecimal ) {
    				ds.setColumn(r, ds.getColumnID(c), ((BigDecimal)tmpValue).doubleValue());
    			} else {
    				ds.setColumn(r, ds.getColumnID(c), tmpValue.toString());
    			}
    		}	// end column
    	}	// end row
    }
	
	/**
	 * Object�� ���� Dataset���� ��ȯ 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveObj2DsForJCO(Dataset ds, Object obj, DatasetHelper dsConvert) {
    	if ( obj == null ) {
    		return;
    	}
    	
    	if ( !(obj instanceof JCO.Table)) {
    		return;
    	}
    	
    	JCO.Table t = (JCO.Table)obj;
//    	if ( ds.getColumnCount() == 0 ) {
    		// Dataset ����
    		Field f = null;
    		for( int i = 0; i < t.getFieldCount(); i++ ) {
    			f = t.getField(i);
				// 0 - String, 1 - date , 2 - ����( decimal ), 6 - ���� 
    			ds.addColumn(f.getName()
    				, getMiTypeForJco(f.getType())
    				, f.getLength());
    		}
//    	}
    	
    	if ( t.getNumRows() < 1) {
    		return;
    	}
    	Object tmpValue = null;
    	do {
    		int r = ds.appendRow();
    		for( int c = 0; c < ds.getColumnCount(); c++ ) {
    			try {
    				tmpValue = t.getValue(ds.getColumnID(c));
				} catch( JCO.Exception e) {
					if( e.getKey().equals("JCO_ERROR_FIELD_NOT_FOUND") ){
						// ���� 
						continue;
					}
					throw e;
				}
				
    			if ( dsConvert != null ) {
    				tmpValue = dsConvert.getDsValue(ds.getColumnID(c), tmpValue, t);
    			}
    			
    			if ( tmpValue == null ) {
    				ds.setColumn(r, ds.getColumnID(c), (String)null);
    			} else if ( tmpValue instanceof Date ) {
    				String tmp = TitUtility.getDateTimeString("yyyyMMdd", (Date) tmpValue);
    				ds.setColumn(r, ds.getColumnID(c), tmp);
    			} else if ( tmpValue instanceof BigDecimal ) {
    				ds.setColumn(r, ds.getColumnID(c), ((BigDecimal)tmpValue).doubleValue());
    			} else {
    				ds.setColumn(r, ds.getColumnID(c), tmpValue.toString());
    			}
    		}
    	} while( t.nextRow() );
    }
	/**
	 * Dataset�� �÷��� �������� �־��� Class �� �ش� ��ü�� �����Ͽ� ���� Move �Ѵ�. 
	 * 
	 * @param ds �����ͼ� 
	 * @param cls ó���� ��ü 
	 * @param flag �÷��� �÷� : TMODE�� ��Ī�Ǵ� �̸� (���� ��� ���� �ʿ� ����. )
	 * @return 
	 */
	public static Object moveDs2Obj(Dataset ds, Class cls, String flag) {
		return moveDs2Obj(ds, cls, flag, null);
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
	public static Object moveDs2Obj(Dataset ds, Class cls, String flag, Ds2ObjHelper helper) {
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
							// 2016.01.29 framework ���� ����
							b = new BigDecimal(DatasetUtility.getStringSpace(ds, i, ds.getColumnID(c), "0"));
							b = b.setScale(3, RoundingMode.DOWN);
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
	
	public static Object moveDs2ObjForJCO(Dataset ds, JCO.Table t
				, String flag, Ds2ObjHelper helper) throws Exception {
		for( int i = ds.getRowCount()-1; i >= 0; i--) {
			// Flag �÷� �ܴ̿� ���� 
			if ( DatasetUtility.getString(ds,i,flag, "").equals("") ) {
				ds.deleteRow(i);
			}
		}

		Field f = null;
		// Dataset �Ǽ� ��ŭ �ѱ�.
		for( int r = 0; r < ds.getRowCount(); r++ ) {
			t.appendRow();
			try {
				for( int c = 0; c < t.getFieldCount(); c++) {
					f = t.getField(c);
					if ( ds.getColumnIndex(f.getName()) < 0 ) {
						continue;
					}
					if ( f.getType() == 1 ) {
						// ��¥ 
						Date d = 
							new Date(TitUtility.getTime(ds.getColumnAsString(r, f.getName()), "yyyyMMdd"));
						f.setValue(d);
					} else {
						f.setValue(ds.getColumnAsObject(r, f.getName()));
					}	
				}	// end for 
				
				if ( flag != null && !flag.trim().equals("") 
						&& !flag.equals("TMODE")) {
					f = t.getField("TMODE");
					f.setValue(ds.getColumnAsString(r, flag));
				}
				// ó�� ���� �˸�. 
				if ( helper != null ) {
					helper.endMoveRow(ds, r, t);
				}
			} catch( Exception e) {
				throw e;
			}
		}	// end for 
		return t;
	}
    /**
     * �ڹ� Ÿ�Կ� �ش��ϴ� Dataset �÷� Ÿ�� �������� 
     * @param javaClsName �ڹ� Ŭ���� Ǯ Ÿ�Ը� 
     * @return Dataset �÷� Ÿ�� 
     */
    public static short getMiType(String javaClsName) {
    	if ( javaClsName.equalsIgnoreCase("java.math.BigDecimal")) {
    		return ColumnInfo.COLTYPE_DECIMAL;
    	} else if ( javaClsName.equalsIgnoreCase("int")) {
    		return ColumnInfo.COLTYPE_INT;
    	}
    	
    	return ColumnInfo.COLTYPE_STRING;
    }
    /**
     * �ڹ� Ÿ�Կ� �ش��ϴ� Dataset �÷� Ÿ�� ���ڿ��� �������� 
     * @param javaClsName �ڹ� Ŭ���� Ǯ Ÿ�Ը� 
     * @return Dataset �÷� ���� Ÿ�� 
     */
    public static String getMiTypeStr(String javaClsName) {
    	if ( javaClsName.equals("int")) {
    		return "INT";
    	} else if ( javaClsName.equals("java.math.BigDecimal")) {
    		return "DECIMAL";
    	} 
    	return "STRING";
    }
    /**
     * JCO �÷��� �����ϴ� Dataset �÷� Ÿ�� ��������  
     * @param fType JCO ���� ������ Ÿ�� 
     * @return �ش� Miplatform Ÿ�� 
     */
    public static short getMiTypeForJco(int fType) {
    	if( fType == 2) {
    		return ColumnInfo.COLTYPE_DECIMAL;
    	} else {
    		return ColumnInfo.COLTYPE_STRING;
    	}	
    }
    /**
     * Java Ÿ���� XML Ÿ������ ��ȯ 
     * @param StrT �ڹ� Ŭ���� Ǯ Ÿ�� 
     * @return �ڹ� Ÿ�Կ� �ش��ϴ� XML Ÿ�� �� 
     */
    public static String getXmlType(String strT) {
    	if ( strT.equalsIgnoreCase("int")) {
    		return "int";
    	} else if ( strT.equalsIgnoreCase("java.math.BigDecimal")) {
    		return "decimal";
    	}
    	return "string";
    }
    /**
     * JCO Ÿ������ SAP�� �����ؾ� �ϴ��� ���� 
     * @return
     */
    public static boolean isJCO() {
    	return ConfigUtility.getString("SAP_CONN_TYPE").equals("JCO");
    }
    
	/**
	 * DB���� SELECT�Ҷ� �ݾ�, ������ ��ȯ. 
	 * 
	 * @param curr(��ȭ), num(�ݾ�,����) 
	 * @return num(�ݾ�,����) 
	 */
    public static double exChangeSapToWeb(String curr, double num ) {
    	if(curr.equals("USDN") ) //�Ҽ��� 5�ڸ�
    		num /= 1000;
    	else if(curr.equals("BHD") || curr.equals("DEM3") || curr.equals("IQD") || curr.equals("JOD") ||
                curr.equals("KWD") || curr.equals("LYD") || curr.equals("OMR") || curr.equals("TND") 
                ) //�Ҽ��� 3�ڸ�
    		num /= 10;
    	else if(curr.equals("JPY") ) //�Ҽ��� 1�ڸ�
    		num *= 10;
    	else if(curr.equals("ADP") || curr.equals("AFA") || curr.equals("BEF") || curr.equals("BIF") ||
                curr.equals("BYB") || curr.equals("BYR") || curr.equals("CLP") || curr.equals("COP") || 
                curr.equals("DJF") || curr.equals("ECS") || curr.equals("ESP") || curr.equals("GNF") ||
                curr.equals("GRD") || curr.equals("HUF") || curr.equals("ITL") || curr.equals("KMF") ||
                curr.equals("KRW") || curr.equals("LAK") || curr.equals("LUF") || curr.equals("MGF") ||
                curr.equals("MZM") || curr.equals("PTE") || curr.equals("PYG") || curr.equals("ROL") ||
                curr.equals("RWF") || curr.equals("TJR") || curr.equals("TMM") || curr.equals("TPE") ||
                curr.equals("TRL") || curr.equals("TWD") || curr.equals("UGX") || curr.equals("VND") ||
                curr.equals("VUV") || curr.equals("XAF") || curr.equals("XOF") || curr.equals("XPF") 
                ) //�Ҽ��� 0�ڸ�
    		num *= 100;
    	return num;
    }
    
	/**
	 * DB�� INSERT, UPDATE�Ҷ� �ݾ�, ������ ��ȯ.
	 * 
	 * @param curr(��ȭ), num(�ݾ�,����) 
	 * @return num(�ݾ�,����) 
	 */
    public static double exChangeWebToSap(String curr, double num ) {
    	if(curr.equals("USDN") ) //�Ҽ��� 5�ڸ�
    		num *= 1000;
    	else if(curr.equals("BHD") || curr.equals("DEM3") || curr.equals("IQD") || curr.equals("JOD") ||
                curr.equals("KWD") || curr.equals("LYD") || curr.equals("OMR") || curr.equals("TND") 
                ) //�Ҽ��� 3�ڸ�
    		num *= 10;
    	else if(curr.equals("JPY") ) //�Ҽ��� 1�ڸ�
    		num /= 10;
    	else if(curr.equals("ADP") || curr.equals("AFA") || curr.equals("BEF") || curr.equals("BIF") ||
                curr.equals("BYB") || curr.equals("BYR") || curr.equals("CLP") || curr.equals("COP") || 
                curr.equals("DJF") || curr.equals("ECS") || curr.equals("ESP") || curr.equals("GNF") ||
                curr.equals("GRD") || curr.equals("HUF") || curr.equals("ITL") || curr.equals("KMF") ||
                curr.equals("KRW") || curr.equals("LAK") || curr.equals("LUF") || curr.equals("MGF") ||
                curr.equals("MZM") || curr.equals("PTE") || curr.equals("PYG") || curr.equals("ROL") ||
                curr.equals("RWF") || curr.equals("TJR") || curr.equals("TMM") || curr.equals("TPE") ||
                curr.equals("TRL") || curr.equals("TWD") || curr.equals("UGX") || curr.equals("VND") ||
                curr.equals("VUV") || curr.equals("XAF") || curr.equals("XOF") || curr.equals("XPF") 
                ) //�Ҽ��� 0�ڸ�
    		num /= 100;
    	return num;
    }
}
