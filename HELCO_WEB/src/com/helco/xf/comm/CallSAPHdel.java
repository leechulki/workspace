package com.helco.xf.comm;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import tit.util.DatasetUtility;
import tit.util.StringOperator;

import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
/**
 * SAP ȣ�� 
 */
public class CallSAPHdel { 

	Logger logger = Logger.getLogger(this.getClass());
    
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

	public static String removeFormatDate(String date) {
		if ( date == null && date.length() != 10) {
			return date;
		}
		
		if ( date.equals("0000-00-00")) {
			return "";
		}
		
		return StringOperator.replaceString(date, "-", "");
	}
	
	
	/**
	 * Dataset�� �÷��� �������� �־��� Class �� �ش� ��ü�� �����Ͽ� ���� Move �Ѵ�. 
	 * 
	 * 2012.06.01 : GRY : �߰� : moveDs2Obj(Dataset ds, Class cls, String flag) ���� 
	 *                          BigDecimal 3�ڸ��� ���εǸ鼭 �߻��ϴ� ���� ������ �ӽ÷� �߰���
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
	 * 2012.06.01 : GRY : �߰� : moveDs2Ob(Dataset ds, Class cls, String flag, Ds2ObjHelper helper)j���� 
	 *                          BigDecimal 3�ڸ��� ���εǸ鼭 �߻��ϴ� ���� ������ �ӽ÷� �߰���
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
							//b = new BigDecimal(DatasetUtility.getDbl(ds, i, ds.getColumnID(c), 0));
							// 2013.04.08 Miplatform�󿡼� Value�� Double�� ��� �Ҽ��� ���� �̽� matching �ؼ�
							//b = new BigDecimal(DatasetUtility.getString(ds, i, ds.getColumnID(c), "0"));
							// 2016.01.27 framework ���� ����
							b = new BigDecimal(DatasetUtility.getStringSpace(ds, i, ds.getColumnID(c), "0"));
							b = new BigDecimal(b.toPlainString());

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
	
	// 2012.06.25 : �߰� : ������ : �������� ����Ÿ�� ����
	public static Dataset makeErrorInfoCreateDs(String DatasetName) {
		Dataset ds_error = new Dataset(DatasetName);
		makeDsCol(ds_error , "ERRCODE"	, 10	, ColumnInfo.COLTYPE_STRING);
		makeDsCol(ds_error , "ERRMSG"	, 200	, ColumnInfo.COLTYPE_STRING);   
		return ds_error;
	}
	// 2012.06.25 : �߰� : ������ : �������� ����Ÿ�¿� ����Ÿ��SET
	public static Dataset makeErrorInfoColSet(Dataset Ds
											, String ErrCode
											, String ErrMessage
											, String DsDelYN
											, String DsAddYN
											) 
	{ 
		if ("Y".equals(DsDelYN)){
			Ds.deleteAll();
		}
		if ("Y".equals(DsAddYN)){
			Ds.appendRow();
		}    
		Ds.setColumn(0, "ERRCODE"	, ErrCode);
		Ds.setColumn(0, "ERRMSG"	, ErrMessage);  
		return Ds;
	}
	

	/**
	 * Object�� ���� Dataset���� ��ȯ 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveListToDs(Dataset ds, Class cls, List list){
		moveListToDs(ds, cls, list, null);
	}
	/**
	 * Object�� ���� Dataset���� ��ȯ 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveListToDs(Dataset ds, Class cls, List list, DatasetHelper dsConvert) {
		
		 
    	if ( list.size()  == 0 ) {
    		return;
    	}
    	  
    	Class cl = cls.getClass(); 
    	
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
    	for( int r = 0; r < list.size(); r++ ) {
    		tmpObj = list.get(r); 
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
    						tmpValue = CallSAPHdel.removeFormatDate((String)tmpValue);
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
	public static void moveComboListToDs(Dataset ds, Class cls, List list){
		moveComboListToDs(ds, cls, list, null);
	}
	/**
	 * Object�� ���� Dataset���� ��ȯ 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveComboListToDs(Dataset ds, Class cls, List list, DatasetHelper dsConvert) {
		
 
    	if ( list.size()  == 0 ) {
    		return;
    	}
    	  
    	Class cl = cls.getClass(); 
    	
    	// Method ���� ���� 
    	HashMap mData = new HashMap();
    	Method m = null;
		try {
			m = cls.getMethod("getCode", null);
			mData.put("CODE", m);
			m = cls.getMethod("getCodeName", null);
			mData.put("CODE_NAME", m);
		} catch( NoSuchMethodException e) {
			// ����
		}
    	 
    	m = null;
    	Object tmpObj = null;
    	Object tmpValue = null;
    	for( int r = 0; r < list.size(); r++ ) {
    		tmpObj = list.get(r); 
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
    						tmpValue = CallSAPHdel.removeFormatDate((String)tmpValue);
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
	
	// ��¥ Ÿ���� ����Ÿ�� 8�ڸ��� �缳��
	public static String dateReplace(String pText)
	{ 
		String retText = StringUtils.trim(pText); 
		retText = StringUtils.replace(retText, "0000-00-00", "");
		retText = StringUtils.replace(retText, "-", "");
		retText = StringUtils.replace(retText, ".", "");   
		return retText;
	} 

	//=========================================================================================
    //  �Լ���� 	: list������ data�� dataset �������� �ٲٴ� ���
    //  �������� 	: ���� 2�� �߰�
    //  HISTORY : 2016.02.15 ���� �ڵ� hsi
    //=========================================================================================
	/**
	 * Object�� ���� Dataset���� ��ȯ 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveListToDsSub(Dataset ds, Class cls, List list){
		moveListToDsSub(ds, cls, list, null);
	}
	/**
	 * Object�� ���� Dataset���� ��ȯ 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveListToDsSub(Dataset ds, Class cls, List list, DatasetHelper dsConvert) {
		
 
    	if ( list.size()  == 0 ) {
    		return;
    	}
    	  
    	Class cl = cls.getClass();
    	String chkCol = "N";
    	char c;
    	
    	// Method ���� ���� 
    	HashMap mData = new HashMap();
    	for( int i = 0; i < ds.getColumnCount(); i++ ) {
    		try {
    			Method m = cls.getMethod("get" + ds.getColumnID(i) , null);
    			mData.put(ds.getColumnID(i), m);
    		} catch( NoSuchMethodException e) {
    			// ����
    			chkCol = "CASE1";
    		}
    	} 
    	 
    	// vo���� ��ҹ��ڷ� ���еǾ� �ִ� ��쿡 ó��.
    	if("CASE1".equals(chkCol)) {
    		String colId = "";
    		mData.clear();
    		
    		for(int i = 0; i < ds.getColumnCount(); i++) {
    			colId = "";
    			for(int j = 0; j < ds.getColumnID(i).length(); j++) {
    				c = ds.getColumnID(i).charAt(j);
    				if(j == 0) {
    					colId = String.valueOf(c).toUpperCase();;
    				} else {
    					if("_".equals(String.valueOf(c))) {
    						j++;
    						c = ds.getColumnID(i).charAt(j);
    						colId += String.valueOf(c).toUpperCase();
    					} else {
    						colId += String.valueOf(c).toLowerCase();
    					}
    				}
    			}
    			
    			try {
    				Method m = cls.getMethod("get" + colId , null);
        			mData.put(ds.getColumnID(i), m);
    			} catch( NoSuchMethodException e) {
        			// ����
    				chkCol = "CASE2";
        		}
    			
    		}
    	}
    	
    	// vo���� ��ҹ��ڷ� ���е����� _ �� �׳� ����� ����쿡 ó��.
    	if("CASE2".equals(chkCol)) {
    		String colId = "";
    		mData.clear();
    		
    		for(int i = 0; i < ds.getColumnCount(); i++) {
    			colId = "";
    			for(int j = 0; j < ds.getColumnID(i).length(); j++) {
    				c = ds.getColumnID(i).charAt(j);
    				if(j == 0) {
    					colId = String.valueOf(c).toUpperCase();;
    				} else {
    					colId += String.valueOf(c).toLowerCase();
    				}
    			}
    			
    			try {
    				Method m = cls.getMethod("get" + colId , null);
        			mData.put(ds.getColumnID(i), m);
    			} catch( NoSuchMethodException e) {
        			// ����
        		}
    			
    		}
    	}
    	
    	Method m = null;
    	Object tmpObj = null;
    	Object tmpValue = null;
    	for( int r = 0; r < list.size(); r++ ) {
    		tmpObj = list.get(r); 
    		int nRow = ds.appendRow(); 
    		
    		for( int x = 0; x < ds.getColumnCount(); x++ ) {
    			m = (Method)mData.get(ds.getColumnID(x));
    			tmpValue = null;
    			if (m != null ) {
    				try {
    					tmpValue = m.invoke(tmpObj, null);
    					
    					// DT�� ����Ǹ�, �⺻������ 8 �ڸ��� �Ȱ� ��¥�� ó�� 
    					if ( ds.getColumnID(x).endsWith("DT") 
    						&& ds.getColumnInfo(x).getColumnSize() == 8) {
    						tmpValue = CallSAPHdel.removeFormatDate((String)tmpValue);
    					}
    				} catch( Exception e){
    					
    				}
    			} 
    			
    			if ( dsConvert != null ) {
    				tmpValue = dsConvert.getDsValue(ds.getColumnID(x), tmpValue, tmpObj);
    			} 
    			
    			if ( tmpValue == null ) {
    				ds.setColumn(nRow, ds.getColumnID(x), (String)null);
    			} else if ( tmpValue instanceof BigDecimal ) { 
    				ds.setColumn(nRow, ds.getColumnID(x), ((BigDecimal)tmpValue).doubleValue());
    			} else { 
    				ds.setColumn(nRow, ds.getColumnID(x), tmpValue.toString());
    			}
    		}	// end column
    	}	// end row
    }
}
