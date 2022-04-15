package hdel.sd.sbi.domain;

import hdel.lib.domain.CommonDomain;


/**
 * ZSDT1143(Ư��ǥ��׷�) Value Of Class
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

public class ZSDT1143 extends CommonDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String MANDT = "";
	private  String ZPRDGBN = "";
	private  String GRPPRH = "";
	private  String GRPDESC = "";
	private  String GRPEDESC = "";
	private  String ERDAT;
	private  String ERNAM = "";
	private  String AEDAT;
	private  String AENAM = "";
	private  String FLAG = "";

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
	public String getGRPPRH() {
		return GRPPRH;
	}
	public void setGRPPRH(String gRPPRH) {
		GRPPRH = gRPPRH;
	}
	public String getGRPDESC() {
		return GRPDESC;
	}
	public void setGRPDESC(String gRPDESC) {
		GRPDESC = gRPDESC;
	}
	public String getGRPEDESC() {
		return GRPEDESC;
	}
	public void setGRPEDESC(String gRPEDESC) {
		GRPEDESC = gRPEDESC;
	}
	public String getERDAT() {
		return ERDAT;
	}
	public void setERDAT(String eRDAT) {
		ERDAT = eRDAT;
	}
	public String getERNAM() {
		return ERNAM;
	}
	public void setERNAM(String eRNAM) {
		ERNAM = eRNAM;
	}
	public String getAEDAT() {
		return AEDAT;
	}
	public void setAEDAT(String aEDAT) {
		AEDAT = aEDAT;
	}
	public String getAENAM() {
		return AENAM;
	}
	public void setAENAM(String aENAM) {
		AENAM = aENAM;
	}
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
	@Override
	public String toString() {
		return "ZSDT1143 [MANDT=" + MANDT + ", ZPRDGBN=" + ZPRDGBN + ", GRPPRH=" + GRPPRH + ", GRPDESC=" + GRPDESC
				+ ", GRPEDESC=" + GRPEDESC + ", ERDAT=" + ERDAT + ", ERNAM=" + ERNAM + ", AEDAT=" + AEDAT + ", AENAM="
				+ AENAM + ", FLAG=" + FLAG + "]";
	}
}
