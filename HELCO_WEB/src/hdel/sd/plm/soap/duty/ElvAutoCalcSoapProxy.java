package hdel.sd.plm.soap.duty;

public class ElvAutoCalcSoapProxy implements hdel.sd.plm.soap.duty.ElvAutoCalcSoap {
  private String _endpoint = null;
  private hdel.sd.plm.soap.duty.ElvAutoCalcSoap elvAutoCalcSoap = null;
  
  public ElvAutoCalcSoapProxy() {
    _initElvAutoCalcSoapProxy();
  }
  
  public ElvAutoCalcSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initElvAutoCalcSoapProxy();
  }
  
  private void _initElvAutoCalcSoapProxy() {
    try {
      elvAutoCalcSoap = (new hdel.sd.plm.soap.duty.ElvAutoCalcSoapServiceLocator()).getElvAutoCalcSoap();
      if (elvAutoCalcSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)elvAutoCalcSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)elvAutoCalcSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (elvAutoCalcSoap != null)
      ((javax.xml.rpc.Stub)elvAutoCalcSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public hdel.sd.plm.soap.duty.ElvAutoCalcSoap getElvAutoCalcSoap() {
    if (elvAutoCalcSoap == null)
      _initElvAutoCalcSoapProxy();
    return elvAutoCalcSoap;
  }
  
  public java.lang.String calc(java.lang.String jsonData, java.lang.String opt1, java.lang.String opt2) throws java.rmi.RemoteException{
    if (elvAutoCalcSoap == null)
      _initElvAutoCalcSoapProxy();
    return elvAutoCalcSoap.calc(jsonData, opt1, opt2);
  }
  
  public java.lang.String reponse(java.lang.String jsonData, java.lang.String opt1, java.lang.String opt2) throws java.rmi.RemoteException{
    if (elvAutoCalcSoap == null)
      _initElvAutoCalcSoapProxy();
    return elvAutoCalcSoap.reponse(jsonData, opt1, opt2);
  }
  
  
}