
/**
 * ZWEB_SD_VALID_QTDATCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package webservice.stub.sap;

    /**
     *  ZWEB_SD_VALID_QTDATCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ZWEB_SD_VALID_QTDATCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ZWEB_SD_VALID_QTDATCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ZWEB_SD_VALID_QTDATCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for zWEB_SD_VALID_QTDAT method
            * override this method for handling normal response from zWEB_SD_VALID_QTDAT operation
            */
           public void receiveResultzWEB_SD_VALID_QTDAT(
                    webservice.stub.sap.ZWEB_SD_VALID_QTDATStub.ZWEB_SD_VALID_QTDATResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from zWEB_SD_VALID_QTDAT operation
           */
            public void receiveErrorzWEB_SD_VALID_QTDAT(java.lang.Exception e) {
            }
                


    }
    