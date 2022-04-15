package com.helco.xf.comm;

import org.apache.axis.client.Stub;
import org.apache.axis.description.ParameterDesc;

public abstract class SapFunction extends Stub {
	
	public static byte PARAM_IN = ParameterDesc.IN;
	public static byte PARAM_OUT = ParameterDesc.OUT;
	public static byte PARAM_INOUT = ParameterDesc.INOUT;
	
    //�߰�
    //public String isEai = ""; 
	
	protected int cnt = 0;
	/**
	 * ����� �������� 
	 * @param name
	 * @return
	 */
	public abstract Object getOutput(String name);
	
	/**
     * Operation �ʱ�ȭ - ��ӹ޾� ���� 
     * @return
     */
    protected abstract void initOperation(Object oper);
    
    /**
     * Operation Name - ��� 
     * @return
     */
    protected abstract String getOperName();
    
    /**
     * Operation ȣ�� 
     * @param param
     * @return
     * @throws Exception
     */
    public abstract int callOperation( Object[] param ) throws Exception;
    
    public abstract int callOperation( Object[] param, String mandt ) throws Exception;
    public abstract int callOperation( Object[] param, String sysid, String mandt, String langu) throws Exception;
    
    //�߰�
    //public abstract int callOperation( Object[] param, String sysid, String mandt, String langu, String eai) throws Exception;
    
    /**
     * ���� 
     */
    public abstract void close();
    
    //eai�����߰�
    /*
	public String getIsEai() {
		return isEai;
	}

	public void setIsEai(String isEai) {
		this.isEai = isEai;
	}
	*/
}
