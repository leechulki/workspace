package com.helco.xf.comm;

import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sz.ui.data.ColumnHeader;
import com.sz.ui.data.DataSet;
import com.sz.ui.data.DataSetList;
import com.sz.ui.data.PlatformData;
import com.sz.ui.data.Variable;
import com.sz.ui.data.VariableList;
import com.sz.ui.data.converter.ConverterUtility;

/**
 * JSON Util 클래스. 
 */
public class JSONUtil {

	/**
	 * DataSet -> JSONArray 로 변환한다. 
	 * 
	 * @param ds
	 * @return
	 */
	public static JSONArray makeToJSON(DataSet ds) {
		// list 형태로 변환 필요 
		JSONObject row = null;
		JSONArray array = new JSONArray();
		
		for(int i = 0; i < ds.getRowCount(); i++ ) {
			row = new JSONObject();
			for( int c = 0; c < ds.getColumnCount(); c++) {
				ColumnHeader col = ds.getColumn(c);
				// 2018.04.05 toString으로 변경 
				row.put( col.getName(), ConverterUtility.toString(ds.getObject(i, col.getName())));
			}
			array.add(row);
		}
		
		return array;
	}
	
	public static JSONObject makeToJSON(PlatformData data) {
		JSONObject json = new JSONObject();
		VariableList vlList = data.getVariableList();
		DataSetList dlList = data.getDataSetList();
		
		if( vlList != null ) {
			Iterator keys = vlList.keyList().iterator();
			while(keys.hasNext()) {
				String key = (String) keys.next();
				Variable v = vlList.get(key);
				if( v != null ) {
					json.put(key, v.getString());
				}
			}
		}
		
		if( dlList != null ) {
			for(int i = 0; i < dlList.size(); i++ ) {
				DataSet ds = dlList.get(i);
				json.put(ds.getName(), makeToJSON(ds));
			}
		}
		
		return json;
	}
}
