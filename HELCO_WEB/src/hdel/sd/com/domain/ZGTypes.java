package hdel.sd.com.domain;

import java.util.ArrayList;
import java.util.List;

public class ZGTypes {

	private static List zgtypes;
	
	static {
		zgtypes = new ArrayList();
		ZGType zgtype = null;
		
		zgtype = new ZGType();		zgtype.setZgtype("VF1");				zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");		zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("VF2");				zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("VF3");				zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("SSVF1");				zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("SSVF2");				zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("LXVF1");				zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("LXVF2");				zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("LXVF3");				zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("FIGL");				zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("I-XEL");				zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("�ʰ��(����)");		zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("ES1");				zgtype.setAuart("ZS01");	zgtype.setMatnr("S-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ESC_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("ES2");				zgtype.setAuart("ZS01");	zgtype.setMatnr("S-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ESC_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("ES3");				zgtype.setAuart("ZS01");	zgtype.setMatnr("S-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ESC_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("ES4");				zgtype.setAuart("ZS01");	zgtype.setMatnr("S-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ESC_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("MW");					zgtype.setAuart("ZS01");	zgtype.setMatnr("W-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("MW_01");			zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("STVF(��ü)");			zgtype.setAuart("ZN01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("LXVF(��ü)");			zgtype.setAuart("ZN01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("APG1");				zgtype.setAuart("ZJ01");	zgtype.setMatnr("J-1000");      zgtype.setSpart("20");		zgtype.setZprdgbn("AP_01");			zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("APG2");				zgtype.setAuart("ZJ01");	zgtype.setMatnr("J-1000");      zgtype.setSpart("20");		zgtype.setZprdgbn("AP_01");			zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("APG3");				zgtype.setAuart("ZJ01");	zgtype.setMatnr("J-1000");      zgtype.setSpart("20");		zgtype.setZprdgbn("AP_01");			zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("VF1(�ؿ�)");			zgtype.setAuart("ZE01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("VF2(�ؿ�)");			zgtype.setAuart("ZE01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("VF3(�ؿ�)");			zgtype.setAuart("ZE01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("LXVF1(�ؿ�)");		zgtype.setAuart("ZE01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("LXVF2(�ؿ�)");		zgtype.setAuart("ZE01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("FIGL(�ؿ�)");			zgtype.setAuart("ZE01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("I-XEL(�ؿ�)");		zgtype.setAuart("ZE01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("�ʰ��(�ؿ�)");		zgtype.setAuart("ZE01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("SHIP(�ؿ�)");			zgtype.setAuart("ZE01");	zgtype.setMatnr("L-2000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_02");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("ES(�ؿ�)");			zgtype.setAuart("ZE01");	zgtype.setMatnr("S-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ESC_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("APG(�ؿ�)");			zgtype.setAuart("ZE01");	zgtype.setMatnr("W-1000");      zgtype.setSpart("20");		zgtype.setZprdgbn("AP_01");			zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("VF1(�߰�)");			zgtype.setAuart("ZT01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("SSVF(�߰�)");			zgtype.setAuart("ZT01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("VF2(�߰�)");			zgtype.setAuart("ZT01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("LXVF1(�߰�)");		zgtype.setAuart("ZT01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("ES(�߰�)");			zgtype.setAuart("ZT01");	zgtype.setMatnr("S-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ESC_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("����");				zgtype.setAuart("ZF01");	zgtype.setMatnr("F-1000");      zgtype.setSpart("30");		zgtype.setZprdgbn("");				zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("PSD");				zgtype.setAuart("ZG01");	zgtype.setMatnr("G-1000");      zgtype.setSpart("40");		zgtype.setZprdgbn("PSD_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("SP(�ؿ�)");			zgtype.setAuart("ZC01");	zgtype.setMatnr("Y-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("");				zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("PSD(�ؿ�)");			zgtype.setAuart("ZE01");	zgtype.setMatnr("G-1000");      zgtype.setSpart("40");		zgtype.setZprdgbn("PSD_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("����(�ؿ�)");			zgtype.setAuart("ZE01");	zgtype.setMatnr("F-1000");      zgtype.setSpart("30");		zgtype.setZprdgbn("");				zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("�Ӵ����");			zgtype.setAuart("ZR11");	zgtype.setMatnr("");            zgtype.setSpart("");		zgtype.setZprdgbn("");				zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("U-1000");				zgtype.setAuart("ZR11");	zgtype.setMatnr("U-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("");				zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("U-1000J");			zgtype.setAuart("ZR11");	zgtype.setMatnr("U-1000");      zgtype.setSpart("20");		zgtype.setZprdgbn("");				zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("U-1000F");			zgtype.setAuart("ZR11");	zgtype.setMatnr("U-1000");      zgtype.setSpart("30");		zgtype.setZprdgbn("");				zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("U-1000G");			zgtype.setAuart("ZR11");	zgtype.setMatnr("U-1000");      zgtype.setSpart("40");		zgtype.setZprdgbn("");				zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("NS-100");				zgtype.setAuart("ZR13");	zgtype.setMatnr("NS-100");      zgtype.setSpart("10");		zgtype.setZprdgbn("");				zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("NB-100");				zgtype.setAuart("ZR12");	zgtype.setMatnr("NB-100");      zgtype.setSpart("10");		zgtype.setZprdgbn("");				zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("�����°���(��Ÿ)");	zgtype.setAuart("ZS01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);
		zgtype = new ZGType();		zgtype.setZgtype("�ؿܽ°���(��Ÿ)");	zgtype.setAuart("ZE01");	zgtype.setMatnr("L-1000");      zgtype.setSpart("10");		zgtype.setZprdgbn("ELV_01");		zgtypes.add(zgtype);


	}
	
	public static ZGType find(String type) {
		ZGType zgtype = null;
		
		if (null == zgtypes || 0 >= zgtypes.size()) {
		
		} else {
			for (int i=0; i<zgtypes.size(); i++) {
				ZGType temp = (ZGType) zgtypes.get(i);
				
				if (type.equals(temp.getZgtype())) {
					zgtype = temp;
					break;
				}
			}
		}
		
		return zgtype;
	}
	
	public static List find() {
		return zgtypes;
	}
	
}
