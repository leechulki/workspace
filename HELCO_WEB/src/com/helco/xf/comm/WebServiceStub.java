package com.helco.xf.comm;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BaseDeserializerFactory;
import org.apache.axis.encoding.ser.BaseSerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.encoding.ser.EnumDeserializerFactory;
import org.apache.axis.encoding.ser.EnumSerializerFactory;
import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
import org.apache.axis.encoding.ser.SimpleSerializerFactory;
//추가 
import com.helco.xf.wb01.CommAction;
//==============================================
import tit.service.business.config.ConfigUtility;
 
public abstract class WebServiceStub extends SapFunction {
	public static final String DEF_NAME_URI = "urn:sap-com:document:sap:rfc:functions";
	
	protected static Class beanSerF = BeanSerializerFactory.class;
	protected static Class beanDeserF = BeanDeserializerFactory.class;
	protected static Class enumSerF = EnumSerializerFactory.class;
	protected static Class enumDeserF = EnumDeserializerFactory.class;
	protected static Class arraySerF = ArraySerializerFactory.class;
	protected static Class arrayDeserF = ArrayDeserializerFactory.class;
	protected static Class simpleSerF = SimpleSerializerFactory.class;
	protected static Class simpleDeserF = SimpleDeserializerFactory.class;
	protected static Class simpleListSerF = SimpleListSerializerFactory.class;
	protected static Class simpleListDesrF = SimpleListDeserializerFactory.class;
	
	protected static Map operMap = new HashMap();
	
    protected OperationDesc oper = null;
    
    protected Map outData = null;
    //추가
    //public String isEai = ""; 
    
    /**
     * Operation 정보 제공 
     * @return
     */
    protected OperationDesc getOperation() {
    	Object obj = operMap.get(getOperName());
    	if ( obj != null ) {
    		oper = (OperationDesc)obj;
    	}
    	
    	if ( oper == null ) {
    		oper = new OperationDesc();
    		oper.setName(getOperName());
    		
    		// Operation 초기화 
    		initOperation(oper);

            ParameterDesc param = new ParameterDesc(
            		new javax.xml.namespace.QName("", "E_TYPE")
            			, ParameterDesc.OUT
            			, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int")
            		, int.class, false, false);
            oper.addParameter(param);
            
            oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
            oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
            oper.setUse(org.apache.axis.constants.Use.LITERAL);
            
            /*
    		// return type 설정
    		oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
            oper.setReturnClass(int.class);
            oper.setReturnQName(new QName("", "E_TYPE"));
            oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
            oper.setUse(org.apache.axis.constants.Use.LITERAL);
            */
            
            // 에러 설정 
             oper.addFault(new org.apache.axis.description.FaultDesc(
                    new QName(DEF_NAME_URI, getOperName() + ".Exception"),
                    "com.helco.xf.comm.CommRfcException",
                    new QName(DEF_NAME_URI, getOperName() + ".RfcException"), 
                    true
                   ));
           
            operMap.put(getOperName(), oper);
    	}
    	return oper;
    }
    
    /**
     * String에 해당하는 Type 정의 
     * @return
     */
    protected String[] getCharTypes() {
    	return new String[]{
    		"char1"
    		,"date"
    	};
    }
    /**
     * Decimal 에 해당하는 타입 정의 
     * @return
     */
    protected String[] getBigDecimalTypes() {
    	return new String[]{
    			"curr13.2"
    			, "quantum13.3"
        	};
    }
    
    /**
     * 배열 및 일반 타입에 해당하는 타입 정의 
     * @return
     */
    protected abstract Object[][] getCustTypes();
    
    public static ParameterDesc makeParam(String paramName, byte paramType) {
    	return makeParam(paramName, paramType, "char24");
    }
    
    public static ParameterDesc makeParam(String paramName, byte paramType, String sizeStr) {
    	return makeParam(paramName, paramType, sizeStr, String.class);
    }
    
    public static ParameterDesc makeParam(String paramName, byte paramType, String sizeStr, Class cls) {
    	ParameterDesc param = new ParameterDesc(
    			new QName("", paramName)
    			, paramType
    			, new QName(DEF_NAME_URI, sizeStr)
    			, cls
    			, false, false
    	);
    	
    	return param;
    }
    
    public static ParameterDesc makeArrayParam(String paramName, byte paramType, String typeName, Class cls, String arrName) {
    	ParameterDesc param = new ParameterDesc(
    			new QName("", paramName)
    			, paramType
    			, new QName(DEF_NAME_URI, typeName)
    			, cls
    			, false, false
    	);
    	
    	param.setItemQName(new QName("", arrName));
        param.setOmittable(true);
        
    	return param;
    }

    public WebServiceStub() throws AxisFault {
    	super.service = new Service();
        ((Service)super.service).setTypeMappingVersion("1.2");
        
      //  if ( super.cachedUsername == null ) {
        	super.cachedUsername = ConfigUtility.getString("WS_USER_ID");
      //  }
      //  if( super.cachedPassword == null ) {
        	super.cachedPassword = ConfigUtility.getString("WS_USER_PWD");
      //  }
    }
    
    protected Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            
            // 최초 호출 
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);

                    Class cls = null;
                    QName qName = null;
                    QName qName2 = null;
                    
                    String[] strTypes = null;//getCharTypes();
                    if( strTypes != null && strTypes.length > 0 ) {
                    	for ( int i = 0; i < strTypes.length; i++) {
                    		qName = new QName(DEF_NAME_URI, strTypes[i]);
                            cls = java.lang.String.class;
                            _call.registerTypeMapping(cls
                            		, qName
                            		, BaseSerializerFactory.createFactory(simpleSerF, cls, qName)
                            		, BaseDeserializerFactory.createFactory(simpleDeserF, cls, qName)
                            		, false);
                    	}
                    }
                    
                    strTypes = null;//getBigDecimalTypes();
                    if( strTypes != null && strTypes.length > 0 ) {
                    	for ( int i = 0; i < strTypes.length; i++) {
                    		qName = new QName(DEF_NAME_URI, strTypes[i]);
                            cls = BigDecimal.class;
                            _call.registerTypeMapping(cls
                            		, qName
                            		, BaseSerializerFactory.createFactory(simpleSerF, cls, qName)
                            		, BaseDeserializerFactory.createFactory(simpleDeserF, cls, qName)
                            		, false);
                    	}
                    }
                    
                    // Exception Class 설정 
                    qName = new javax.xml.namespace.QName(DEF_NAME_URI, getOperName() + ".RfcException");
                    cls = CommRfcException.class;
                    
                    _call.registerTypeMapping(cls
                    		, qName
                    		, beanSerF
                    		, beanDeserF
                    		, false);
                    
                    qName = new javax.xml.namespace.QName(DEF_NAME_URI, getOperName() + ".RfcExceptions");
                    cls = CommRfcExceptions.class;
                    _call.registerTypeMapping(cls
                    		, qName
                    		, enumSerF
                    		, enumDeserF
                    		, false);
                    
                    qName = new javax.xml.namespace.QName(DEF_NAME_URI, "RfcException.Message");
                    cls = RfcExceptionMessage.class;
                    _call.registerTypeMapping(cls
                    		, qName
                    		, beanSerF
                    		, beanDeserF
                    		, false);
                    
                    qName = new javax.xml.namespace.QName(DEF_NAME_URI, "RfcException.Message.Number");
                    cls = RfcExceptionMessage.class;
                    _call.registerTypeMapping(cls
                    		, qName
                    		, BaseSerializerFactory.createFactory(simpleSerF, cls, qName)
                    		, BaseDeserializerFactory.createFactory(simpleDeserF, cls, qName)
                    		, false);
                   
                    // Array types
                    Object[][] obj = getCustTypes();
                    if ( obj != null ) {
                    	Object[] tmp = null;
                    	for ( int i = 0; i < obj.length; i++) {
                    		tmp = obj[i];
                    		if( tmp.length == 4 ) {	// 배열 
	                    		qName = new QName(DEF_NAME_URI, (String)tmp[0]);
	                    		QName qName3 = new QName(DEF_NAME_URI, (String)tmp[1]);
	                    		QName qName4 = new QName("", (String)tmp[2]);

	                    		_call.registerTypeMapping(
	                    			(Class)tmp[3]
	                            		, qName
	                            		, new org.apache.axis.encoding.ser.ArraySerializerFactory(qName3, qName4)
	                            		, new org.apache.axis.encoding.ser.ArrayDeserializerFactory()
	                            		, false);
	                    	} else {
	                    		qName = new QName(DEF_NAME_URI, (String)tmp[0]);
	                    		_call.registerTypeMapping(
		                    			(Class)tmp[1]
		                    			, qName
		                    			, beanSerF
		                    			, beanDeserF
		                    			, false);
	                    	}
                    	}
                    }
                }
            }
            
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }
    
    public int callOperation( Object[] param ) throws Exception {
    	return callOperation( param , ConfigUtility.getString("MANDT"));
    }
    
    public int callOperation( Object[] param , String mandt) throws Exception {
    	return callOperation(param, "", mandt);
    }
    public int callOperation( Object[] param , String sysid, String mandt) throws Exception {
    	return callOperation(param, sysid, mandt, "");
    }
    public int callOperation( Object[] param , String sysid, String mandt, String langu) throws Exception {
//    	if (super.cachedEndpoint == null) {
//            throw new org.apache.axis.NoEndPointException();
//        }
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(getOperation());
    	_call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        
        _call.setProperty(org.apache.axis.client.Call.CONNECTION_TIMEOUT_PROPERTY, 2000000); //2019.10.07 BGT - 웹서비스 타임아웃 관련 추가   
        
        
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName(DEF_NAME_URI, getOperName()));
        setRequestHeaders(_call);
        setAttachments(_call);

        // 주소 설정 
        try {
        	String sturl = ""; // 개발 
    		// 시스템 판단 
        	if ( ConfigUtility.getString("SYSID_HEP").equals(sysid)) {
    			sturl = "_PRD";
    		} else if ( ConfigUtility.getString("SYSID_HEQ").equals(sysid)) {
    			sturl = "_HEQ";	// 품질
    		} else if ( ConfigUtility.getString("SYSID_HED").equals(sysid)) {
    			sturl = "";
    		} else {
    			if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) {
	    			sturl = "_PRD";
	    		} else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) {
	    			sturl = "_HEQ";	// 품질
	    		}
    		}
            
        	
        	int cnt;
        	try {        		        	
	    		if (sysid != "" && sysid != null )
	    		{
	    			CommAction ca = new CommAction();
	    			cnt = ca.getEaiChk(mandt, sysid, getOperName());
	    		}
	    		else
	    		{
	    			cnt  = 0;
	    		}
        	} catch (Exception e ){
        		cnt  = 0;
	        } finally {
	        	
        	}
        	
        	
        	
        	
        	URL newUrl = null;
        	if (cnt < 1) 
        	{
        		newUrl = new URL( ConfigUtility.getString("WS_START_URL" + sturl ) 
    					+ getOperName().toLowerCase() 
    					+ "/"
    					+ mandt
    					+ "/"
    					+ getOperName().toLowerCase() + "/" + getOperName().toLowerCase() 
    					+ ConfigUtility.getString("WS_END_URL").trim()
    					+ (langu.isEmpty() ? "" : "?sap-language="+langu)
    				);
        	}
        	else {
        		newUrl = new URL( ConfigUtility.getString("WS_URL_EAI" + sturl ) 
        				+ "58001"  // EAI의 SRM PORT 
        				+ "/"
    					+ getOperName()
    					+ "/"
    					+ mandt    					
    				);
        	}
        	
        	
        	
        	
    		_call.setTargetEndpointAddress(newUrl);
    	} catch( MalformedURLException e) {
    		// 무시 
    	}
        
        try {
        	Object res = _call.invoke(param);
        	if (res instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException)res;
            }
        	extractAttachments(_call);
        	
        	// OutData 기억 
        	outData = _call.getOutputParams();
        	
        	try {
            //    return ((java.lang.Integer) res).intValue();
        		return ((java.lang.Integer) outData.get(new javax.xml.namespace.QName("", "E_TYPE"))).intValue();
            } catch (java.lang.Exception _exception) {
            //    return ((java.lang.Integer) org.apache.axis.utils.JavaUtils.convert(res, int.class)).intValue();

            	String name = _call.getOperationName().getLocalPart();

            	try {
            	if ( name.equals("ZWEB_CO4_FIND_COST") || name.equals("ZWEB_CO4_FIND_COST_PP") || name.equals("ZWEB_SD_CHN_197_PSPID") || name.equals("ZWEB_SD_CHN_197_SUBMIT")
            	  || name.equals("ZWEB_SD_CHN_197_APPV")  || name.equals("ZWEB_SD_CHN_197_ACAN") ) {
            		return Integer.parseInt((String)outData.get(new javax.xml.namespace.QName("", "E_TYPE")));
            	} else {
            		return ((java.lang.Integer) org.apache.axis.utils.JavaUtils.convert(outData.get(new javax.xml.namespace.QName("", "E_TYPE")), int.class)).intValue();
            	}
            	} catch (java.lang.Exception e) {
            		return 0;
            	}
            }
        } catch (AxisFault axisFaultException) {
        	if (axisFaultException.detail != null) {
                if (axisFaultException.detail instanceof java.rmi.RemoteException) {
                      throw (java.rmi.RemoteException) axisFaultException.detail;
                 }
                if (axisFaultException.detail instanceof CommRfcException) {
                      throw (CommRfcException) axisFaultException.detail;
                 }
           }
          throw axisFaultException;
        }
    }
    
    public Object getOutput(String name) {
    	if ( outData == null ) {
    		return null;
    	}
    	
    	return outData.get(new QName("", name));
    }
    
    public void close() {}


}
