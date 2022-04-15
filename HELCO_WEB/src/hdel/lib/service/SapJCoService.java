package hdel.lib.service;

import tit.base.ServiceManagerFactory;
import tit.service.sapjco3.RfcFactory;

/*
 ******************************************************************************************
 * ��      ��   : SAP RFC ȣ���� ó���ϱ� ���� �ֻ��� Ŭ����
 * ��  ��  ��   : �ڼ���
 * �ۼ�  ����   : 2016.02.12
 * ���ID       : UF001
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 ���� �ڵ� �ڼ���
 ******************************************************************************************
*/
public class SapJCoService {

	private RfcFactory rfcFactoryHEP;
	private RfcFactory rfcFactoryHEQ;
	private RfcFactory rfcFactoryHED;

	//=========================================================================================
    //  �Լ���� : �� ���� ������ SAP ���뿡�� �����ϴ� �� �ν��Ͻ� ȣ��
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
