package hdel.lib.service;

import tit.base.ServiceManagerFactory;
import tit.service.sapjco3.RfcFactory;

/*
 ******************************************************************************************
 * 기      능   : SAP RFC 호출을 처리하기 위한 최상위 클래스
 * 작  성  자   : 박수근
 * 작성  일자   : 2016.02.12
 * 기능ID       : UF001
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 최초 코딩 박수근
 ******************************************************************************************
*/
public class SapJCoService {

	private RfcFactory rfcFactoryHEP;
	private RfcFactory rfcFactoryHEQ;
	private RfcFactory rfcFactoryHED;

	//=========================================================================================
    //  함수기능 : 빈 생성 시점에 SAP 공통에서 제공하는 빈 인스턴스 호출
    //=========================================================================================
	protected SapJCoService() {
		this.rfcFactoryHEP = (RfcFactory)ServiceManagerFactory.getServiceObject("RfcFactoryServiceHEP"); 
		this.rfcFactoryHEQ = (RfcFactory)ServiceManagerFactory.getServiceObject("RfcFactoryServiceHEQ");
		this.rfcFactoryHED = (RfcFactory)ServiceManagerFactory.getServiceObject("RfcFactoryServiceHED");
	}

	public RfcFactory getRfcFactory(String sysid) {
		if(sysid.equals("HEP")) return this.rfcFactoryHEP;
		if(sysid.equals("HEQ")) return this.rfcFactoryHEQ;
		if(sysid.equals("HED")) return this.rfcFactoryHED;
		return null;
	}
}
