package hdel.sd.com.domain;

public class FloorNmVO {
	// Ŭ���̾�Ʈ
	private String MANDT;
	
	// ����������ȣ
	private String SUVSN;

	// ��
	private String FLOOR;

	// �� DB 
	private String FLOORDB;
	
	// �� ������ -5 ~ 40
	private int FLOORNUM = 0;
	
	// ����
	private String FLOORNM  = "";
	
	// 'JAMBGRP-JAMBMODEL' 
	private String JAMBINFO = "";
    
	// JAMB�׷� ���� 1,2,3 3�� �׷츸 ����
	private String JAMBGRP  = "";
    
	// JAMB�׷� ��
	private String JAMBMODEL  = "";

	// ������ġǥ�ñ��
	private String HPIMODEL  = "";

	// �����ư��
	private String HPBMODEL  = "";
    
	// ��������ġǥ�ñ��
	private String HIPMODEL  = "";

	// Ȧ���ϸ�
	private String HLTNMODEL  = "";
    
	// �ҹ潺��ġ��
	private String FSWMODEL  = "";
	
	// ���� ������ġǥ�ñ��簪
	private String HPIPRD  = "";
	
	// ���� �����ư��簪
	private String HPBPRD  = "";
	
	// ���� ��������ġǥ�ñ��簪
	private String HIPPRD  = "";

	// ���� Ȧ���ϻ�簪
	private String HLTNPRD  = "";

	// ���� PARKING SW��簪
	private String PRKSWPRD  = "";
	
	// ���� �ҹ潺��ġ��簪
	private String FSWPRD  = "";

	// ���� ������ġǥ�ñ�������
	private String HPIAPPLYFLOOR  = "";

	// ���� �����ư������
	private String HPBAPPLYFLOOR  = "";

	// ���� ��������ġǥ�ñ�������
	private String HIPAPPLYFLOOR  = "";

	// ���� Ȧ����������
	private String HLTNAPPLYFLOOR  = "";

	// ���� PARKING SW������
	private String PRKSWAPPLYFLOOR  = "";

	// ���� �ҹ潺��ġ������
	private String FSWAPPLYFLOOR  = "";
	
    // �����ID	
	private  String AENAM = "";
	
	private  String ERNAM = "";
	
	// ����
	private String FLOORPRD = "";
	
	// ���� JAMB Ÿ��
	private String JAMBTP = "";
	
	// ���� ������ ���翩��
	private String FLOORYSNO = "";
	
	// ǥ�ñ� ���� ����
	private boolean ISFLOOR = false;
	
	private String SUVPRH = "";	

	private String SUVPRD = "";
	
	// ���� �������� : �������: O
	private String INPUTTYPE = "";

	public String getMANDT() {
		return MANDT;
	}

	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}

	public String getSUVSN() {
		return SUVSN;
	}

	public void setSUVSN(String sUVSN) {
		SUVSN = sUVSN;
	}

	public String getFLOOR() {
		return FLOOR;
	}

	public void setFLOOR(String fLOOR) {
		FLOOR = fLOOR;
	}

	public String getFLOORDB() {
		return FLOORDB;
	}

	public void setFLOORDB(String fLOORDB) {
		FLOORDB = fLOORDB;
	}

	public int getFLOORNUM() {
		return FLOORNUM;
	}

	public void setFLOORNUM(int fLOORNUM) {
		FLOORNUM = fLOORNUM;
	}

	public String getFLOORNM() {
		return FLOORNM;
	}

	public void setFLOORNM(String fLOORNM) {
		FLOORNM = fLOORNM;
	}

	public String getJAMBINFO() {
		return JAMBINFO;
	}

	public void setJAMBINFO(String jAMBINFO) {
		JAMBINFO = jAMBINFO;
	}

	public String getJAMBGRP() {
		return JAMBGRP;
	}

	public void setJAMBGRP(String jAMBGRP) {
		JAMBGRP = jAMBGRP;
	}

	public String getJAMBMODEL() {
		return JAMBMODEL;
	}

	public void setJAMBMODEL(String jAMBMODEL) {
		JAMBMODEL = jAMBMODEL;
	}

	public String getHPIMODEL() {
		return HPIMODEL;
	}

	public void setHPIMODEL(String hPIMODEL) {
		HPIMODEL = hPIMODEL;
	}

	public String getHPBMODEL() {
		return HPBMODEL;
	}

	public void setHPBMODEL(String hPBMODEL) {
		HPBMODEL = hPBMODEL;
	}

	public String getHIPMODEL() {
		return HIPMODEL;
	}

	public void setHIPMODEL(String hIPMODEL) {
		HIPMODEL = hIPMODEL;
	}

	public String getHLTNMODEL() {
		return HLTNMODEL;
	}

	public void setHLTNMODEL(String hLTNMODEL) {
		HLTNMODEL = hLTNMODEL;
	}

	public String getFSWMODEL() {
		return FSWMODEL;
	}

	public void setFSWMODEL(String fSWMODEL) {
		FSWMODEL = fSWMODEL;
	}

	public String getHPIPRD() {
		return HPIPRD;
	}

	public void setHPIPRD(String hPIPRD) {
		HPIPRD = hPIPRD;
	}

	public String getHPBPRD() {
		return HPBPRD;
	}

	public void setHPBPRD(String hPBPRD) {
		HPBPRD = hPBPRD;
	}

	public String getHIPPRD() {
		return HIPPRD;
	}

	public void setHIPPRD(String hIPPRD) {
		HIPPRD = hIPPRD;
	}

	public String getHLTNPRD() {
		return HLTNPRD;
	}

	public void setHLTNPRD(String hLTNPRD) {
		HLTNPRD = hLTNPRD;
	}

	public String getPRKSWPRD() {
		return PRKSWPRD;
	}

	public void setPRKSWPRD(String pRKSWPRD) {
		PRKSWPRD = pRKSWPRD;
	}

	public String getFSWPRD() {
		return FSWPRD;
	}

	public void setFSWPRD(String fSWPRD) {
		FSWPRD = fSWPRD;
	}

	public String getHPIAPPLYFLOOR() {
		return HPIAPPLYFLOOR;
	}

	public void setHPIAPPLYFLOOR(String hPIAPPLYFLOOR) {
		HPIAPPLYFLOOR = hPIAPPLYFLOOR;
	}

	public String getHPBAPPLYFLOOR() {
		return HPBAPPLYFLOOR;
	}

	public void setHPBAPPLYFLOOR(String hPBAPPLYFLOOR) {
		HPBAPPLYFLOOR = hPBAPPLYFLOOR;
	}

	public String getHIPAPPLYFLOOR() {
		return HIPAPPLYFLOOR;
	}

	public void setHIPAPPLYFLOOR(String hIPAPPLYFLOOR) {
		HIPAPPLYFLOOR = hIPAPPLYFLOOR;
	}

	public String getHLTNAPPLYFLOOR() {
		return HLTNAPPLYFLOOR;
	}

	public void setHLTNAPPLYFLOOR(String hLTNAPPLYFLOOR) {
		HLTNAPPLYFLOOR = hLTNAPPLYFLOOR;
	}

	public String getPRKSWAPPLYFLOOR() {
		return PRKSWAPPLYFLOOR;
	}

	public void setPRKSWAPPLYFLOOR(String pRKSWAPPLYFLOOR) {
		PRKSWAPPLYFLOOR = pRKSWAPPLYFLOOR;
	}

	public String getFSWAPPLYFLOOR() {
		return FSWAPPLYFLOOR;
	}

	public void setFSWAPPLYFLOOR(String fSWAPPLYFLOOR) {
		FSWAPPLYFLOOR = fSWAPPLYFLOOR;
	}

	public String getAENAM() {
		return AENAM;
	}

	public void setAENAM(String aENAM) {
		AENAM = aENAM;
	}

	public String getERNAM() {
		return ERNAM;
	}

	public void setERNAM(String eRNAM) {
		ERNAM = eRNAM;
	}

	public String getFLOORPRD() {
		return FLOORPRD;
	}

	public void setFLOORPRD(String fLOORPRD) {
		FLOORPRD = fLOORPRD;
	}

	public String getJAMBTP() {
		return JAMBTP;
	}

	public void setJAMBTP(String jAMBTP) {
		JAMBTP = jAMBTP;
	}

	public String getFLOORYSNO() {
		return FLOORYSNO;
	}

	public void setFLOORYSNO(String fLOORYSNO) {
		FLOORYSNO = fLOORYSNO;
	}

	public boolean isISFLOOR() {
		return ISFLOOR;
	}

	public void setISFLOOR(boolean iSFLOOR) {
		ISFLOOR = iSFLOOR;
	}

	public String getSUVPRH() {
		return SUVPRH;
	}

	public void setSUVPRH(String sUVPRH) {
		SUVPRH = sUVPRH;
	}

	public String getSUVPRD() {
		return SUVPRD;
	}

	public void setSUVPRD(String sUVPRD) {
		SUVPRD = sUVPRD;
	}
	
	public String getINPUTTYPE() {
		return INPUTTYPE;
	}

	public void setINPUTTYPE(String iNPUTTYPE) {
		INPUTTYPE = iNPUTTYPE;
	}

	@Override
	public String toString() {
		return "FloorNmVO [MANDT=" + MANDT + ", SUVSN=" + SUVSN + ", FLOOR="
				+ FLOOR + ", FLOORDB=" + FLOORDB + ", FLOORNUM=" + FLOORNUM
				+ ", FLOORNM=" + FLOORNM + ", JAMBINFO=" + JAMBINFO
				+ ", JAMBGRP=" + JAMBGRP + ", JAMBMODEL=" + JAMBMODEL
				+ ", HPIMODEL=" + HPIMODEL + ", HPBMODEL=" + HPBMODEL
				+ ", HIPMODEL=" + HIPMODEL + ", HLTNMODEL=" + HLTNMODEL
				+ ", FSWMODEL=" + FSWMODEL + ", HPIPRD=" + HPIPRD + ", HPBPRD="
				+ HPBPRD + ", HIPPRD=" + HIPPRD + ", HLTNPRD=" + HLTNPRD
				+ ", PRKSWPRD=" + PRKSWPRD + ", FSWPRD=" + FSWPRD
				+ ", HPIAPPLYFLOOR=" + HPIAPPLYFLOOR + ", HPBAPPLYFLOOR="
				+ HPBAPPLYFLOOR + ", HIPAPPLYFLOOR=" + HIPAPPLYFLOOR
				+ ", HLTNAPPLYFLOOR=" + HLTNAPPLYFLOOR + ", PRKSWAPPLYFLOOR="
				+ PRKSWAPPLYFLOOR + ", FSWAPPLYFLOOR=" + FSWAPPLYFLOOR
				+ ", AENAM=" + AENAM + ", ERNAM=" + ERNAM + ", FLOORPRD="
				+ FLOORPRD + ", JAMBTP=" + JAMBTP + ", FLOORYSNO=" + FLOORYSNO
				+ ", ISFLOOR=" + ISFLOOR + ", SUVPRH=" + SUVPRH + ", SUVPRD="
				+ SUVPRD + ", INPUTTYPE=" + INPUTTYPE + "]";
	}
}
