package hdel.sd.ses.domain;

import hdel.lib.domain.ParameterVO;

public class Ses0240ParamVO extends ParameterVO {

	private String mandt;   		// No  
	private String zwgbn;			// ��������(Q:���� M:����)
	private String docreqno;  		// Ŭ���̾�Ʈ  
	private String zrqflg;    		// ������ȣ or �����Ƿڹ�ȣ
	private String zrqdat;    		// �Ϸù�ȣ    
	private String zrqcn;     		// ��û����    
	private String zwriter;   		// ��û��
	private String zrqdatfr;   		// ��û��Fr
	private String zrqdatto;   		// ��û��To
	private String zrmk;      		// ��û����    
	private String qtnum;     		// ������ȣ
	private String org_qtnum;     // ������ȣ org  
	private String gsnam;     		// �����
	private String qtser;     		// ��������        
	private String cdate;     		// �Է���      
	private String ctime;     		// �Է½ð�    
	private String cuser;     		// �Է���      
	private String udate;     		// ������      
	private String utime;     		// ����ð�    
	private String uuser;     		// ������  
	
	private String zseq;     		// ���� or �Ϸù�ȣ
	private String zattseq;       	// ÷���Ϸù�ȣ
	private String zattpath;     	// ���ϰ��
	private String zattfn;     		// ���ϸ�
	private byte[] fdata;     		// ���ϵ�����
	

	public String getZwgbn() {
		return zwgbn;
	}

	public void setZwgbn(String zwgbn) {
		this.zwgbn = zwgbn;
	}

	public String getZattseq() {
		return zattseq;
	}

	public void setZattseq(String zattseq) {
		this.zattseq = zattseq;
	}

	public String getZattpath() {
		return zattpath;
	}

	public void setZattpath(String zattpath) {
		this.zattpath = zattpath;
	}

	public String getZattfn() {
		return zattfn;
	}

	public void setZattfn(String zattfn) {
		this.zattfn = zattfn;
	}


	public String getMandt() {
		return mandt;
	}

	public void setMandt(String mandt) {
		this.mandt = mandt;
	}

	public String getDocreqno() {
		return docreqno;
	}

	public void setDocreqno(String docreqno) {
		this.docreqno = docreqno;
	}

	public String getZrqflg() {
		return zrqflg;
	}

	public void setZrqflg(String zrqflg) {
		this.zrqflg = zrqflg;
	}

	public String getZrqdat() {
		return zrqdat;
	}

	public void setZrqdat(String zrqdat) {
		this.zrqdat = zrqdat;
	}
	
	public String getZrqdatfr() {
		return zrqdatfr;
	}

	public void setZrqdatfr(String zrqdatfr) {
		this.zrqdatfr = zrqdatfr;
	}

	public String getZrqdatto() {
		return zrqdatto;
	}

	public void setZrqdatto(String zrqdatto) {
		this.zrqdatto = zrqdatto;
	}

	public String getZrqcn() {
		return zrqcn;
	}

	public void setZrqcn(String zrqcn) {
		this.zrqcn = zrqcn;
	}

	public String getZwriter() {
		return zwriter;
	}

	public void setZwriter(String zwriter) {
		this.zwriter = zwriter;
	}

	public String getZrmk() {
		return zrmk;
	}

	public void setZrmk(String zrmk) {
		this.zrmk = zrmk;
	}

	public String getQtnum() {
		return qtnum;
	}

	public void setQtnum(String qtnum) {
		this.qtnum = qtnum;
	}

	public String getOrg_qtnum() {
		return org_qtnum;
	}

	public void setOrg_qtnum(String org_qtnum) {
		this.org_qtnum = org_qtnum;
	}

	public String getGsnam() {
		return gsnam;
	}

	public void setGsnam(String gsnam) {
		this.gsnam = gsnam;
	}

	public String getQtser() {
		return qtser;
	}

	public void setQtser(String qtser) {
		this.qtser = qtser;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getCuser() {
		return cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getUdate() {
		return udate;
	}

	public void setUdate(String udate) {
		this.udate = udate;
	}

	public String getUtime() {
		return utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getUuser() {
		return uuser;
	}

	public void setUuser(String uuser) {
		this.uuser = uuser;
	}

	public String getZseq() {
		return zseq;
	}

	public void setZseq(String zseq) {
		this.zseq = zseq;
	}

	public byte[] getFdata() {
		return fdata;
	}

	public void setFdata(byte[] fdata) {
		this.fdata = fdata;
	}

}
