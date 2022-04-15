package hdel.sd.sbi.domain;

import hdel.lib.domain.CommonDomain;


/**
 * SanyangPrh(�������) Value Of Class
 * 
 * @author  �ڼ���
 * @since 2020.09.01
 * @version 1.0
 * @see 
 * <pre>
 *  == �����̷�(Modification Information) ==
 *   
 *          ������          ������           ��������
 *  ----------------    ------------    ---------------------------
 *   2020.09.01         �ڼ���          ���� ����
 * 
 * </pre>
 */

public class SanyangPrh extends CommonDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String MANDT;
	private  String ZPRDGBN;
	private  String BRNDCD;
	private  String ATKLA;
	private  String PRH;
	private  String PRHNAME;
	private  String GRPDESC;
	private  String DATATYPE;

	public String getMANDT() {
		return MANDT;
	}
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}
	public String getZPRDGBN() {
		return ZPRDGBN;
	}
	public void setZPRDGBN(String zPRDGBN) {
		ZPRDGBN = zPRDGBN;
	}
	public String getBRNDCD() {
		return BRNDCD;
	}
	public void setBRNDCD(String bRNDCD) {
		BRNDCD = bRNDCD;
	}
	public String getATKLA() {
		return ATKLA;
	}
	public void setATKLA(String aTKLA) {
		ATKLA = aTKLA;
	}
	public String getPRH() {
		return PRH;
	}
	public void setPRH(String pRH) {
		PRH = pRH;
	}
	public String getPRHNAME() {
		return PRHNAME;
	}
	public void setPRHNAME(String pRHNAME) {
		PRHNAME = pRHNAME;
	}
	public String getGRPDESC() {
		return GRPDESC;
	}
	public void setGRPDESC(String gRPDESC) {
		GRPDESC = gRPDESC;
	}
	public String getDATATYPE() {
		return DATATYPE;
	}
	public void setDATATYPE(String dATATYPE) {
		DATATYPE = dATATYPE;
	}
	@Override
	public String toString() {
		return "Com040SanyangPrh [MANDT=" + MANDT + ", ZPRDGBN=" + ZPRDGBN + ", BRNDCD=" + BRNDCD + ", ATKLA=" + ATKLA
				+ ", PRH=" + PRH + ", PRHNAME=" + PRHNAME + ", GRPDESC=" + GRPDESC + ", DATATYPE=" + DATATYPE + "]";
	}
}
