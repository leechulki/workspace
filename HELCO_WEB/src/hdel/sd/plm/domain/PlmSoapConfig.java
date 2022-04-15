package hdel.sd.plm.domain;

/**
 * PLM 웹서비스 환경변수
 * 
 * @author  박수근
 * @since 2020.10.16
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.10.16         박수근          최초 생성
 * 
 * </pre>
 */

public class PlmSoapConfig {
    private static String g_sysId = "HEP";
    // 디폴트 연결 타임아웃 5분
    private static int g_timeout = 300000; 
    private static  String plmHepSoapUrl   = "http://plmwebsvc.hdel.co.kr";
    private static  String plmHeqSoapUrl   = "http://plmwebsvc.hdel.co.kr";
    private static  String plmDevSoapUrl   = "http://plmwebsvc.hdel.co.kr";
    //private static  String plmHeqSoapUrl   = "http://10.105.1.45:8082";
    //private static  String plmDevSoapUrl   = "http://10.105.1.45:8082";
    private static  String plmlocalSoapUrl = "http://localhost:8080/plmSoap";

    public static void setSysId(String sysid) {
    	g_sysId = sysid;
    }

    public static void setTimeOut(int timeout) {
    	g_timeout = timeout;
    }

    public static String getPlmSoapUrl() {
    	if("HEP".equals(g_sysId)) {
    		return plmHepSoapUrl;
    	} else if("HEQ".equals(g_sysId)) {
    		return plmHeqSoapUrl;
    	} else if("HED".equals(g_sysId)) {
    		return plmDevSoapUrl;
    	} else {
    		return plmlocalSoapUrl;
    	}
    }

    public static int getTimeOut() {
    	return g_timeout;
    }
}
