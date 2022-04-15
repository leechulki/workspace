package com.helco.xf.comm;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sz.ui.data.DataSet;
import com.sz.ui.data.DataSetList;
import com.sz.ui.data.DataTypes;
import com.sz.ui.data.PlatformData;
import com.sz.ui.data.Variable;
import com.sz.ui.data.VariableList;


public class FSPPopClientHelper {
	protected JSONObject mInputObj = null;
	private DataSetList mInDsList;
	private VariableList mInVlList;
	// Sender IP
	private static String strEgisUrl       = null;
	private static Properties props = null;

	public static void main(String args[]) {
		try {
			
/*			VariableList vl = new VariableList();
			Variable v = new Variable("MENU_ID", DataTypes.STRING,"1111");
			vl.add(v);
			
			PlatformData pd = new PlatformData();
			
			pd.setDataSetList(new DataSetList());
			//pd.setVariableList(vl);
			
			
			DataSet ds_egis_price = new DataSet("ds_egis_price");
			ds_egis_price.addColumn("QTNUM", DataTypes.STRING);
			ds_egis_price.addColumn("QTSEQ", DataTypes.INT);
			ds_egis_price.addColumn("QTSER", DataTypes.INT);
			ds_egis_price.addColumn("ZUAM", DataTypes.STRING);
			ds_egis_price.addColumn("ZEAM", DataTypes.STRING);
			
			ds_egis_price.newRow();
			ds_egis_price.set(0,"QTNUM","1222221111");
			ds_egis_price.set(0,"QTSEQ",1000);
			ds_egis_price.set(0,"QTSER",20);
			ds_egis_price.set(0,"ZUAM","AAAA10");
			ds_egis_price.set(0,"ZEAM","XXXXX10");
			
			pd.getDataSetList().add(ds_egis_price);
			
			FSPPopClientHelper fspPop = new FSPPopClientHelper("SetSRMCost", JSONUtil.makeToJSON(pd).toJSONString());
			DataSet ds_list = fspPop.getInputDataSet("ds_egis_block");
			System.out.println("getError====>"+fspPop.getError());
			if(ds_list != null) {
				for(int i=0;i<ds_list.getRowCount();i++) {
					System.out.println(ds_list.getString(i,"QTNUM"));
				}
			}
*/			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DataSet getInputDataSet(String dsName) {
		DataSet ds = this.mInDsList.get(dsName);
		return ds;
	}
	public Object getInputVariable(String keyNm) {
		Object obj = this.mInVlList.get(keyNm);
		return obj;
	}
	
//	public String getReturn(String keyNm) {
//		String str = this.mInVlList.get(keyNm);
//		
//		return str;
//	}	
	public FSPPopClientHelper(String serviceId,String sendData, String sysid) throws Exception{
		this.mInDsList = new DataSetList();
		this.mInVlList = new VariableList();
		
		if(props == null) {
			try {
				// ÇÁ·ÎÆÛÆ® ÆÄÀÏ ·Îµå
				props = new java.util.Properties();
				props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("EGIS_SRM.properties"));
				// Sender IP 
				InetAddress local = InetAddress.getLocalHost();
//				strSenderIp = local.getHostAddress();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}	

//		strEgisUrl = "http://"+props.getProperty("EGIS_"+sysid)+":"+props.getProperty("EGIS_PORT")+"/service/" + serviceId;
		strEgisUrl = "http://"+props.getProperty("EGIS_"+sysid)+"/service/" + serviceId;
		
		System.out.println(strEgisUrl);
		
//		URL url = new URL("http://10.51.5.34:8081/service/" + serviceId);
		URL url = new URL(strEgisUrl);
		HttpURLConnection  conn = (HttpURLConnection)url.openConnection();
		
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5000);
		conn.connect();
		
		OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
		byte[] binaryData = new byte[1024];
				
		osw.write(sendData);
		osw.flush();		
		
		JSONParser localJSONParser = new JSONParser();
		InputStreamReader localInputStreamReader = new InputStreamReader(conn.getInputStream());
		
		Object responseData = localJSONParser.parse(localInputStreamReader);
		this.mInputObj = (JSONObject)responseData;
		
		// ï¿½Ý±ï¿½
		osw.close();
		localInputStreamReader.close();
		parseInput();
		
		if(!this.mInVlList.get("ErrorCode").getString().equals("S")) {
			throw new Exception(this.mInVlList.get("ErrorMsg").getString());
		} else {
			getError();
		}
		
	}
	  
	protected void parseInput(){
		if (this.mInputObj == null) {
		  this.mInputObj = new JSONObject();
		}

		Iterator localIterator = this.mInputObj.keySet().iterator();
		String str = null;
		while (localIterator.hasNext())
		{
		  str = (String)localIterator.next();
		  Object localObject = getXPObject(str);
		  if ((localObject instanceof DataSet)) {
		    this.mInDsList.add((DataSet)localObject);
		  } else if (localObject != null) {
		    this.mInVlList.add(new Variable(str, getDataSetDataType(localObject), localObject));
		  }
		}
	 }
	
	  private Object getXPObject(String paramString)
	  {
	    if (this.mInputObj == null) {
	      return null;
	    }
	    Object localObject = this.mInputObj.get(paramString);
	    if (localObject == null) {
	      return null;
	    }
	    if ((localObject instanceof JSONArray))
	    {
	      DataSet localDataSet = new DataSet();
	      localDataSet.setName(paramString);
	      JSONArray localJSONArray = (JSONArray)localObject;
	      if (localJSONArray.get(0) != null)
	      {
	        JSONObject localJSONObject = (JSONObject)localJSONArray.get(0);
	        Iterator localIterator = localJSONObject.keySet().iterator();
	        String str = null;
	        while (localIterator.hasNext())
	        {
	          str = (String)localIterator.next();
	          localDataSet.addColumn(str, getDataSetDataType(localJSONArray, str));
	        }
	        for (int i = 0; i < localJSONArray.size(); i++)
	        {
	          localJSONObject = (JSONObject)localJSONArray.get(i);
	          localDataSet.newRow();
	          for (int j = 0; j < localDataSet.getColumnCount(); j++) {
	            localDataSet.set(i, localDataSet.getColumn(j).getName(), localJSONObject.get(localDataSet.getColumn(j).getName()));
	          }
	        }
	      }
	      return localDataSet;
	    }
	    return localObject;
	  }	
	
	  
	  private int getDataSetDataType(JSONArray paramJSONArray, String paramString)
	  {
	    int i = 2;
	    Object localObject = null;
	    JSONObject localJSONObject = null;
	    for (int j = 0; j < paramJSONArray.size(); j++)
	    {
	      localJSONObject = (JSONObject)paramJSONArray.get(j);
	      localObject = localJSONObject.get(paramString);
	      if (localObject != null) {
	        break;
	      }
	    }
	    if (localObject == null) {
	      return i;
	    }
	    if ((localObject instanceof Long)) {
	      i = 5;
	    } else if ((localObject instanceof Double)) {
	      i = 8;
	    }
	    return i;
	  }
	  
	  private int getDataSetDataType(Object paramObject)
	  {
	    int i = 2;
	    if (paramObject == null) {
	      return i;
	    }
	    if ((paramObject instanceof Long)) {
	      i = 5;
	    } else if ((paramObject instanceof Double)) {
	      i = 8;
	    }
	    return i;
	  }	 
	  
	  public String getError() {
		  String rtn;
		  rtn = this.mInVlList.get("ErrorCode").getString();
		  return rtn ;
	  }
}
