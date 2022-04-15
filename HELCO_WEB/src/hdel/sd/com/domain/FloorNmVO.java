package hdel.sd.com.domain;

public class FloorNmVO {
	// 클라이언트
	private String MANDT;
	
	// 실측고유번호
	private String SUVSN;

	// 층
	private String FLOOR;

	// 층 DB 
	private String FLOORDB;
	
	// 층 정수값 -5 ~ 40
	private int FLOORNUM = 0;
	
	// 층명
	private String FLOORNM  = "";
	
	// 'JAMBGRP-JAMBMODEL' 
	private String JAMBINFO = "";
    
	// JAMB그룹 현재 1,2,3 3개 그룹만 존재
	private String JAMBGRP  = "";
    
	// JAMB그룹 모델
	private String JAMBMODEL  = "";

	// 수평위치표시기모델
	private String HPIMODEL  = "";

	// 승장버튼모델
	private String HPBMODEL  = "";
    
	// 수직형위치표시기모델
	private String HIPMODEL  = "";

	// 홀랜턴모델
	private String HLTNMODEL  = "";
    
	// 소방스위치모델
	private String FSWMODEL  = "";
	
	// 실측 수평위치표시기사양값
	private String HPIPRD  = "";
	
	// 실측 승장버튼사양값
	private String HPBPRD  = "";
	
	// 실측 수직형위치표시기사양값
	private String HIPPRD  = "";

	// 실측 홀랜턴사양값
	private String HLTNPRD  = "";

	// 실측 PARKING SW사양값
	private String PRKSWPRD  = "";
	
	// 실측 소방스위치사양값
	private String FSWPRD  = "";

	// 실측 수평위치표시기적용층
	private String HPIAPPLYFLOOR  = "";

	// 실측 승장버튼적용층
	private String HPBAPPLYFLOOR  = "";

	// 실측 수직형위치표시기적용층
	private String HIPAPPLYFLOOR  = "";

	// 실측 홀랜턴적용층
	private String HLTNAPPLYFLOOR  = "";

	// 실측 PARKING SW적용층
	private String PRKSWAPPLYFLOOR  = "";

	// 실측 소방스위치적용층
	private String FSWAPPLYFLOOR  = "";
	
    // 등록자ID	
	private  String AENAM = "";
	
	private  String ERNAM = "";
	
	// 층고값
	private String FLOORPRD = "";
	
	// 실측 JAMB 타입
	private String JAMBTP = "";
	
	// 층고 데이터 존재여부
	private String FLOORYSNO = "";
	
	// 표시기 매핑 여부
	private boolean ISFLOOR = false;
	
	private String SUVPRH = "";	

	private String SUVPRD = "";
	
	// 층고 생성기준 : 영업사양: O
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
