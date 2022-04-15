package com.helco.xf.comm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import tit.base.Utility;
import tit.util.StringOperator;


public class MakeWSUtility {

	public static String mObjName = "ZPPS142";
	public static String filePath = "D:/dev/work_src/HELCO_WEB/src/";
	public static String packInfo = "com.helco.xf.pp01.ws";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			URL url = Utility.convertServicePathToURL("com/helco/xf/comm/data.txt");
			
			InputStream in = url.openStream();
			InputStreamReader ir = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(ir);
			String str = null;
			
			Map colNm = new HashMap();
			Map colSize = new HashMap();
			Map colType = new HashMap();
			
			int cnt = 0;
			while( ( str = br.readLine()) != null ) {
				int i = str.indexOf("name");				
				String str2 = str.substring(i+6);
				
				int j = str2.indexOf("type=");
				String name = str2.substring(0, j-2);
				
				str2 = str2.substring(str2.indexOf(":")+1, str2.length()-5);
				String type = "java.lang.String";
				String size = "10";
				
				if ( str2.startsWith("int")) {
					type = "int";
				} else if ( str2.startsWith("curr") || str2.startsWith("quantum")) {
					type = "java.math.BigDecimal";
					size = "13";
				} else if ( str2.startsWith("date")) {
					size = "8";
				} else {
					size = "";
					for ( int a = str2.length()-1; a >= 0; a--) {
						if ( Character.isDigit(str2.charAt(a)) ) {
							size = str2.charAt(a) + size;
						} else {
							break;
						}
					}
				}
				
				colNm.put(cnt++, name);
				colSize.put(name, size);
				colType.put(name, type);
			}// end while
			
			// java class 累己 
			String javaStr = getJavaStr(colNm, colSize, colType);
			
			File f = new File(filePath + StringOperator.replaceString(packInfo, ".", "/"), mObjName + ".java" );
			FileWriter fi = new FileWriter(f);
			fi.write(javaStr);
			fi.close();
			
			System.out.println("# Make Java File :" + f.getName());
			
			String dsStr = getDsStr(colNm, colSize, colType);
			
			System.out.println("# Dataset Info :");
			System.out.println(dsStr.toUpperCase());
			br.close();
			ir.close();
			in.close();
		} catch( Exception e) {
			e.printStackTrace();
		} 
	}

	public static String getDsStr(Map colInfo, Map sizeInfo, Map typeInfo) throws Exception {
		String str = "";
		for( int i = 0; i < colInfo.size(); i++) {
			str += "	<colinfo id=\"" + colInfo.get(i) + "\" size=\"" 
				+ sizeInfo.get(colInfo.get(i)) + "\" type=\"" 
				+ CallSAP.getMiTypeStr((String)typeInfo.get(colInfo.get(i))) + "\" />\n";
		}
		return str;
	}
	public static String getJavaStr(Map colInfo, Map sizeInfo, Map typeInfo) throws Exception {
		String str = "package " + packInfo + ";\n"
			+ "import com.tobesoft.platform.data.Dataset;\n"
			// \n\textends com.helco.xf.wb01.ws.CommVO
			+ "public class " + mObjName +"   \n\t\timplements java.io.Serializable {";
		
		// 积己磊 积己
		
		str += "\n\n";
		str += "	public " + mObjName +"() {\n";
//		str += "		mSize = new int[]{\n";
		
//		for( int i = 0; i < colInfo.size(); i++) {
//			str += "				" + ( i > 0 ? "," : "") + sizeInfo.get(colInfo.get(i)) + "\n";
//		}
//		str += "		};\n";
		str += "	}\n";	// 积己磊 end 
		
		// 鞘靛 积己 
		for( int i = 0; i < colInfo.size(); i++) {
			str += "	public " + typeInfo.get(colInfo.get(i)) + " " + colInfo.get(i).toString().toUpperCase() + ";\n";
		}
		
		// get 积己 
		for( int i = 0; i < colInfo.size(); i++) {
			str += "	public " +  typeInfo.get(colInfo.get(i)) + " get" + colInfo.get(i).toString().toUpperCase() + "(){\n";
			str += "		return " + colInfo.get(i).toString().toUpperCase() + ";\n";
			str += "	}\n";
		}
		
		// set 积己 
		for( int i = 0; i < colInfo.size(); i++) {
			str += "	public void set" + colInfo.get(i).toString().toUpperCase() + "(" + typeInfo.get(colInfo.get(i)) + " aValue) {\n";
			str += "		" + colInfo.get(i).toString().toUpperCase() + "=aValue;\n";
			str += "	}\n";
		}
		
		str += getDefString();
		
		// Type 沥焊 积己 
		str += " static {\n";
		str += "		typeDesc.setXmlType(new javax.xml.namespace.QName(\"urn:sap-com:document:sap:rfc:functions\", \"" + mObjName + "\"));\n";
		str += "		org.apache.axis.description.ElementDesc elemField =null;\n";
		
		for( int i = 0; i < colInfo.size(); i++) {
			str += "		elemField = new org.apache.axis.description.ElementDesc();\n";
	        str += "		elemField.setFieldName(\"" + colInfo.get(i).toString().toUpperCase() + "\");\n";
	        str += "		elemField.setXmlName(new javax.xml.namespace.QName(\"\", \"" + colInfo.get(i) + "\"));\n";
	        str += "		elemField.setXmlType(new javax.xml.namespace.QName(\"http://www.w3.org/2001/XMLSchema\", \"" 
	        			+ CallSAP.getXmlType((String)typeInfo.get(colInfo.get(i))) + "\"));\n";
	        str += "		elemField.setNillable(true);\n";
	        str += "		typeDesc.addFieldDesc(elemField);\n";
		}
		str += "	}\n";
		
		// Dataset 积己 备巩 
		str += "	public static Dataset getDataset() {\n";
		str += "		Dataset ds = new Dataset();\n";
		for( int i = 0; i < colInfo.size(); i++) {
			str += "		ds.addColumn(\"" + colInfo.get(i).toString().toUpperCase() + "\""
				+ ",(short)" + CallSAP.getMiType((String)typeInfo.get(colInfo.get(i)))
				+ "," + sizeInfo.get(colInfo.get(i)) + ");\n";
		}
		str += "		return ds;\n";
		str += "	}\n";
		str += "\n}";	// class end 
		return str;
	}

	public static String getDefString() {
		String str = ""
		+ "	// Type metadata \n"
	    + "	private static org.apache.axis.description.TypeDesc typeDesc \n"
	    +"		= new org.apache.axis.description.TypeDesc(" + mObjName + ".class, true);\n"
//	    + "		= CallSAP.makeTypeDesc(" + mObjName + ".class);\n"
	    + "\n"
	    + "	/**\n"
	    + " 	* Return type metadata object\n"
	    + " 	*/\n"
	    + "	public static org.apache.axis.description.TypeDesc getTypeDesc() {\n"
	    + "    		return typeDesc;\n"
	    + "	}\n"
	    + "\n"
	    + "	/**\n"
	    + "	 * Get Custom Serializer\n"
	    + " 	*/\n"
	    + "	public static org.apache.axis.encoding.Serializer getSerializer(\n"
	    + "      	 java.lang.String mechType, \n"
	    + "      	 java.lang.Class _javaType,  \n"
	    + "     	  javax.xml.namespace.QName _xmlType) {\n"
	    + "    		return \n"
	    + "      		new  org.apache.axis.encoding.ser.BeanSerializer(\n"
	    + "       		 _javaType, _xmlType, typeDesc);\n"
	    + "	}\n"
	    + "\n"
	    + "	/**\n"
	    + " 	* Get Custom Deserializer\n"
	    + " 	*/\n"
	    + "	public static org.apache.axis.encoding.Deserializer getDeserializer(\n"
	    + "     	  java.lang.String mechType, \n"
	    + "     	  java.lang.Class _javaType,  \n"
	    + "     	  javax.xml.namespace.QName _xmlType) {\n"
	    + "    		return \n"
	    + "      		new  org.apache.axis.encoding.ser.BeanDeserializer(\n"
	    + "     	 	  _javaType, _xmlType, typeDesc);\n"
	    + "\n	}";
	  
		return str;
	}
}
