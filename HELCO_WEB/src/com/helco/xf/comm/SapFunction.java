package com.helco.xf.comm;

import org.apache.axis.client.Stub;
import org.apache.axis.description.ParameterDesc;

public abstract class SapFunction extends Stub {
	
	public static byte PARAM_IN = ParameterDesc.IN;
	public static byte PARAM_OUT = ParameterDesc.OUT;
	public static byte PARAM_INOUT = ParameterDesc.INOUT;
	
    //추가
    //public String isEai = ""; 
	
	protected int cnt = 0;
	/**
	 * 결과값 가져오기 
	 * @param name
	 * @return
	 */
	public abstract Object getOutput(String name);
	
	/**
     * Operation 초기화 - 상속받아 구현 
     * @return
     */
    protected abstract void initOperation(Object oper);
    
    /**
     * Operation Name - 상속 
     * @return
     */
    protected abstract String getOperName();
    
    /**
     * Operation 호출 
     * @param param
     * @return
     * @throws Exception
     */
    public abstract int callOperation( Object[] param ) throws Exception;
    
    public abstract int callOperation( Object[] param, String mandt ) throws Exception;
    public abstract int callOperation( Object[] param, String sysid, String mandt, String langu) throws Exception;
    
    //추가
    //public abstract int callOperation( Object[] param, String sysid, String mandt, String langu, String eai) throws Exception;
    
    /**
     * 종료 
     */
    public abstract void close();
    
    //eai관련추가
    /*
	public String getIsEai() {
		return isEai;
	}

	public void setIsEai(String isEai) {
		this.isEai = isEai;
	}
	*/
}
