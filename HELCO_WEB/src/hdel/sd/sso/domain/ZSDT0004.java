package hdel.sd.sso.domain;

import org.apache.commons.lang.math.NumberUtils;

public class ZSDT0004 {
	String MANDT;
	String HOGI;
	String POSNR;
	String ZZPJT_ID;
	String TP_DATE2;
	String TP_DATE3;

	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getHOGI() {
		return HOGI;
	}
	public void setHOGI(String hOGI) {
		HOGI = hOGI;
	}
	public String getPOSNR() {
		return POSNR;
	}
	public void setPOSNR(String pOSNR) {
		POSNR = pOSNR;
	}
	public String getZZPJT_ID() {
		return ZZPJT_ID;
	}
	public void setZZPJT_ID(String zZPJT_ID) {
		ZZPJT_ID = zZPJT_ID;
	}
	public String getTP_DATE2() {
		return TP_DATE2;
	}
	public void setTP_DATE2(String tP_DATE2) {
		TP_DATE2 = tP_DATE2;
	}
	public String getTP_DATE3() {
		return String.format("%08d", NumberUtils.toInt(TP_DATE3, 0));
	}
	public void setTP_DATE3(String tP_DATE3) {
		TP_DATE3 = String.format("%08d", NumberUtils.toInt(tP_DATE3, 0));
	}
}
