
/**
 * ZWEB_SD_CHN_113CallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package webservice.stub.eai;

    /**
     *  ZWEB_SD_CHN_113CallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ZWEB_SD_CHN_113CallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ZWEB_SD_CHN_113CallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ZWEB_SD_CHN_113CallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for zWEB_SD_CHN_113 method
            * override this method for handling normal response from zWEB_SD_CHN_113 operation
            */
           public void receiveResultzWEB_SD_CHN_113(
                    webservice.stub.eai.ZWEB_SD_CHN_113Stub.ZWEB_SD_CHN_113Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from zWEB_SD_CHN_113 operation
           */
            public void receiveErrorzWEB_SD_CHN_113(java.lang.Exception e) {
            }
                


    }
    