package com.helco.xf.comm;

import java.util.ArrayList;

import tit.service.business.config.ConfigUtility;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.tobesoft.platform.data.Dataset;

/**
 * JCO ���� 
 */
public abstract class JCOMgr extends SapFunction {
	private static IRepository mRepository;
	// client 
	protected JCO.Client client = null;
	protected JCO.Function function = null;
	static {
		try {
			// Connection Pool ����
			JCO.addClientPool(ConfigUtility.getString("JCO_SID"), 				// Pool �̸�
				 			ConfigUtility.getInt("JCO_MAX_CONN_CNT"), 				// �ִ� ��� ���� ��
				 			ConfigUtility.getString("JCO_CLIENT"),			// SAP client 
				 			ConfigUtility.getString("JCO_USER_ID"),  		// �����
				 			ConfigUtility.getString("JCO_USER_PWD"),	 	// password 
				 			ConfigUtility.getString("JCO_LANG"),   			// ���
				 			ConfigUtility.getString("JCO_HOST"),	// host name
				 			ConfigUtility.getString("JCO_SYS_NUM"));			// System Number
	
			// �ű� repository ����
			mRepository = JCO.createRepository(ConfigUtility.getString("JCO_REPOSITORY_NAME")
						, ConfigUtility.getString("JCO_SID"));
		} catch( Exception e) {
			// ���� 
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
		// �Լ� �ʱ�ȭ 
		IFunctionTemplate ft = mRepository.getFunctionTemplate(getOperName());
		if( ft == null ) {
			// exception 
			throw new IllegalArgumentException(getOperName() + " is not found.");
		}
		
		try {
			// function ���� 
			function = ft.getFunction();
			// Parameter ���� 
			JCO.ParameterList input = function.getImportParameterList();
			// Get a client from the pool
			client = JCO.getClient(ConfigUtility.getString("JCO_SID"));
			
			// �Ķ���� �� �����ϱ� 
			ArrayList p = new ArrayList();
			// Operation �ʱ�ȭ 
			initOperation(p);
			for( int i = 0; i < p.size(); i++) {
				// Table�� ��� 
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
	 * �ش� ���̺� �Ķ���� ���� ���� 
	 * @param f
	 */
	public void addTableParameter(JCO.Function f, Dataset ds) throws Exception {
		JCO.Table t = f.getTableParameterList().getTable("O_TAB");
		CallSAP.moveDs2ObjForJCO(ds, t, "FLAG", null);
	}
}
