package com.helco.xf.comm;

import java.util.ArrayList;

import tit.service.business.config.ConfigUtility;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.tobesoft.platform.data.Dataset;

/**
 * JCO 관리 
 */
public abstract class JCOMgr extends SapFunction {
	private static IRepository mRepository;
	// client 
	protected JCO.Client client = null;
	protected JCO.Function function = null;
	static {
		try {
			// Connection Pool 생성
			JCO.addClientPool(ConfigUtility.getString("JCO_SID"), 				// Pool 이름
				 			ConfigUtility.getInt("JCO_MAX_CONN_CNT"), 				// 최대 허용 연결 수
				 			ConfigUtility.getString("JCO_CLIENT"),			// SAP client 
				 			ConfigUtility.getString("JCO_USER_ID"),  		// 사용자
				 			ConfigUtility.getString("JCO_USER_PWD"),	 	// password 
				 			ConfigUtility.getString("JCO_LANG"),   			// 언어
				 			ConfigUtility.getString("JCO_HOST"),	// host name
				 			ConfigUtility.getString("JCO_SYS_NUM"));			// System Number
	
			// 신규 repository 생성
			mRepository = JCO.createRepository(ConfigUtility.getString("JCO_REPOSITORY_NAME")
						, ConfigUtility.getString("JCO_SID"));
		} catch( Exception e) {
			// 무시 
			e.printStackTrace();
		}
	}
	
	public int callOperation( Object[] param ) throws Exception {
		return callOperation(param, ConfigUtility.getString("MANDT"));
	}
	
	public int callOperation( Object[] param , String mandt) throws Exception {
		return callOperation(param, "", ConfigUtility.getString("MANDT"));
	}
	public int callOperation( Object[] param , String sysid, String mandt) throws Exception {
		return callOperation(param, sysid, mandt, "");
	}
	public int callOperation( Object[] param , String sysid, String mandt, String langu) throws Exception {
		// 함수 초기화 
		IFunctionTemplate ft = mRepository.getFunctionTemplate(getOperName());
		if( ft == null ) {
			// exception 
			throw new IllegalArgumentException(getOperName() + " is not found.");
		}
		
		try {
			// function 생성 
			function = ft.getFunction();
			// Parameter 정보 
			JCO.ParameterList input = function.getImportParameterList();
			// Get a client from the pool
			client = JCO.getClient(ConfigUtility.getString("JCO_SID"));
			
			// 파라메터 값 설정하기 
			ArrayList p = new ArrayList();
			// Operation 초기화 
			initOperation(p);
			for( int i = 0; i < p.size(); i++) {
				// Table일 경우 
				if ( param[i] instanceof Dataset ) {
					addTableParameter(function, (Dataset) param[i]);
				} else {
					input.setValue(param[i], (String)p.get(i));
				}	
			}
			client.execute(function);
		} catch( Exception e) {
			throw e;
		} finally {
			close();
		}
		return (Integer)function.getExportParameterList().getValue("E_TYPE");
	}
	
	public Object getOutput(String name) {
		return function.getTableParameterList().getTable(name);
	}
	
	public void close() {
		if ( client != null ) {
			JCO.releaseClient(client);
		}
	}
	/**
	 * 해당 테이블 파라메터 정보 생성 
	 * @param f
	 */
	public void addTableParameter(JCO.Function f, Dataset ds) throws Exception {
		JCO.Table t = f.getTableParameterList().getTable("O_TAB");
		CallSAP.moveDs2ObjForJCO(ds, t, "FLAG", null);
	}
}
