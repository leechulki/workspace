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
 * SAP 호출 
 */
public class CallSAPHdel { 

	Logger logger = Logger.getLogger(this.getClass());
    
	/**
	 * Dataset에 컬럼 만들기 
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
	 * Dataset의 컬럼을 기준으로 주어진 Class 에 해당 객체를 생성하여 값을 Move 한다. 
	 * 
	 * 2012.06.01 : GRY : 추가 : moveDs2Obj(Dataset ds, Class cls, String flag) 에서 
	 *                          BigDecimal 3자리로 맵핑되면서 발생하는 오류 때문에 임시로 추가함
	 * @param ds 데이터셋 
	 * @param cls 처리할 객체 
	 * @param flag 플래그 컬럼 : TMODE와 대칭되는 이름 (같을 경우 정의 필요 없음. )
	 * @return 
	 */
	public static Object moveDs2Obj2(Dataset ds, Class cls, String flag) {
		return moveDs2Obj2(ds, cls, flag, null);
	}
	
	/**
	 * Dataset의 컬럼을 기준으로 주어진 Class 에 해당 객체를 생성하여 값을 Move 한다. 
	 * 한건의 데이터를 객체로 옮긴 후 Ds2ObjHelper.endRow()를 호출하여 처리 종료를 알린다. 
	 * 
	 * 2012.06.01 : GRY : 추가 : moveDs2Ob(Dataset ds, Class cls, String flag, Ds2ObjHelper helper)j에서 
	 *                          BigDecimal 3자리로 맵핑되면서 발생하는 오류 때문에 임시로 추가함
	 * @param ds 데이터셋 
	 * @param cls 처리할 객체 
	 * @param flag 플래그 컬럼 : TMODE와 대칭되는 이름 (같을 경우 정의 필요 없음. )
	 * @return 
	 */	
	public static Object moveDs2Obj2(Dataset ds, Class cls, String flag, Ds2ObjHelper helper) {
		if ( flag != null && !flag.equals("")) {
			for( int i = ds.getRowCount()-1; i >= 0; i--) {
				// Flag 컬럼 이외는 삭제 
				if ( DatasetUtility.getString(ds,i,flag, "").equals("") 
					|| DatasetUtility.getString(ds,i,flag, "").equals("0")) {
					ds.deleteRow(i);
				}
			}
		}
		
		// 해당 Type으로 배열 생성 
		Object obj = Array.newInstance(cls, ds.getRowCount());
		Method[] mArr = cls.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			// Set 메소드만 가져오기 
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
							// 2013.04.08 Miplatform상에서 Value가 Double의 경우 소수점 값의 미스 matching 해소
							//b = new BigDecimal(DatasetUtility.getString(ds, i, ds.getColumnID(c), "0"));
							// 2016.01.27 framework 수정 적용
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
				
				// 처리 종료 알림. 
				if ( helper != null ) {
					helper.endMoveRow(ds, i, tmpObj);
				}
			} catch( Exception e){
				e.printStackTrace();
			}
			
			// Array 정보 설정 
			Array.set(obj, i, tmpObj);
		}
		return obj;
	}
	
	// 2012.06.25 : 추가 : 구난이 : 오류정보 데이타셋 생성
	public static Dataset makeErrorInfoCreateDs(String DatasetName) {
		Dataset ds_error = new Dataset(DatasetName);
		makeDsCol(ds_error , "ERRCODE"	, 10	, ColumnInfo.COLTYPE_STRING);
		makeDsCol(ds_error , "ERRMSG"	, 200	, ColumnInfo.COLTYPE_STRING);   
		return ds_error;
	}
	// 2012.06.25 : 추가 : 구난이 : 오류정보 데이타셋에 데이타값SET
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
	 * Object의 값을 Dataset으로 변환 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveListToDs(Dataset ds, Class cls, List list){
		moveListToDs(ds, cls, list, null);
	}
	/**
	 * Object의 값을 Dataset으로 변환 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveListToDs(Dataset ds, Class cls, List list, DatasetHelper dsConvert) {
		
		 
    	if ( list.size()  == 0 ) {
    		return;
    	}
    	  
    	Class cl = cls.getClass(); 
    	
    	// Method 정보 생성 
    	HashMap mData = new HashMap();
    	for( int i = 0; i < ds.getColumnCount(); i++ ) {
    		try {
    			Method m = cls.getMethod("get" + ds.getColumnID(i) , null);
    			mData.put(ds.getColumnID(i), m);
    		} catch( NoSuchMethodException e) {
    			// 무시
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
    					
    					// DT로 종료되며, 기본적으로 8 자리로 된것 날짜로 처리 
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
	 * Object의 값을 Dataset으로 변환 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveComboListToDs(Dataset ds, Class cls, List list){
		moveComboListToDs(ds, cls, list, null);
	}
	/**
	 * Object의 값을 Dataset으로 변환 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveComboListToDs(Dataset ds, Class cls, List list, DatasetHelper dsConvert) {
		
 
    	if ( list.size()  == 0 ) {
    		return;
    	}
    	  
    	Class cl = cls.getClass(); 
    	
    	// Method 정보 생성 
    	HashMap mData = new HashMap();
    	Method m = null;
		try {
			m = cls.getMethod("getCode", null);
			mData.put("CODE", m);
			m = cls.getMethod("getCodeName", null);
			mData.put("CODE_NAME", m);
		} catch( NoSuchMethodException e) {
			// 무시
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
    					
    					// DT로 종료되며, 기본적으로 8 자리로 된것 날짜로 처리 
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
	
	// 날짜 타입의 데이타를 8자리로 재설정
	public static String dateReplace(String pText)
	{ 
		String retText = StringUtils.trim(pText); 
		retText = StringUtils.replace(retText, "0000-00-00", "");
		retText = StringUtils.replace(retText, "-", "");
		retText = StringUtils.replace(retText, ".", "");   
		return retText;
	} 

	//=========================================================================================
    //  함수기능 	: list형식의 data를 dataset 형식으로 바꾸는 펑션
    //  개선내역 	: 패턴 2개 추가
    //  HISTORY : 2016.02.15 최초 코딩 hsi
    //=========================================================================================
	/**
	 * Object의 값을 Dataset으로 변환 
	 * @param ds
	 * @param obj
	 * @param dsConvert
	 */
	public static void moveListToDsSub(Dataset ds, Class cls, List list){
		moveListToDsSub(ds, cls, list, null);
	}
	/**
	 * Object의 값을 Dataset으로 변환 
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
    	
    	// Method 정보 생성 
    	HashMap mData = new HashMap();
    	for( int i = 0; i < ds.getColumnCount(); i++ ) {
    		try {
    			Method m = cls.getMethod("get" + ds.getColumnID(i) , null);
    			mData.put(ds.getColumnID(i), m);
    		} catch( NoSuchMethodException e) {
    			// 무시
    			chkCol = "CASE1";
    		}
    	} 
    	 
    	// vo값이 대소문자로 구분되어 있는 경우에 처리.
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
        			// 무시
    				chkCol = "CASE2";
        		}
    			
    		}
    	}
    	
    	// vo값이 대소문자로 구분되지만 _ 는 그냥 사용할 경우경우에 처리.
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
        			// 무시
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
    					
    					// DT로 종료되며, 기본적으로 8 자리로 된것 날짜로 처리 
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
