
/**
 * ZWEB_CO4_COST_INSCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package webservice.stub.sap;

    /**
     *  ZWEB_CO4_COST_INSCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ZWEB_CO4_COST_INSCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ZWEB_CO4_COST_INSCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ZWEB_CO4_COST_INSCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for zWEB_CO4_COST_INS method
            * override this method for handling normal response from zWEB_CO4_COST_INS operation
            */
           public void receiveResultzWEB_CO4_COST_INS(
                    webservice.stub.sap.ZWEB_CO4_COST_INSStub.ZWEB_CO4_COST_INSResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from zWEB_CO4_COST_INS operation
           */
            public void receiveErrorzWEB_CO4_COST_INS(java.lang.Exception e) {
            }
                


    }
    