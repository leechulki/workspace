package hdel.sd.ses.domain;

import hdel.lib.domain.CommonDomain;

public class Ses0440 extends CommonDomain {
 
    private String mandt;
    private String pspid;      //프로젝트
    private String seq;        //차수
    private String status;     //상태
    private String erdat;      //입력일
    private String vkbur;      //부서
    private String vkburt;     //부서명
    private String vkgrp;      //팀
    private String vkgrpt;     //팀명
    private String zkunnr;     //담당자
    private String zkunnr_nm;  //담당자명
    private String bstnk;      //현장명
    private String bigo01;       //특기사항
    private String repl01;       //회신내용
    private String findat;     //완료일
    private String reqdat;     //요청일
    private String rqser;     //요청차수
    private String is_lo;      //레이아웃 유무
    private String is_dm;      //기타서류 유부
    private String kunnr_z3;   //기술영업담당
	private String kunnr_z3_nm;
	
    public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getPspid() {
		return pspid;
	}
	public void setPspid(String pspid) {
		this.pspid = pspid;
	}
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErdat() {
		return erdat;
	}
	public void setErdat(String erdat) {
		this.erdat = erdat;
	}
	public String getVkbur() {
		return vkbur;
	}
	public void setVkbur(String vkbur) {
		this.vkbur = vkbur;
	}

	public String getVkburt() {
		return vkburt;
	}
	public void setVkburt(String vkburt) {
		this.vkburt = vkburt;
	}
	public String getVkgrp() {
		return vkgrp;
	}
	public void setVkgrp(String vkgrp) {
		this.vkgrp = vkgrp;
	}
	public String getVkgrpt() {
		return vkgrpt;
	}
	public void setVkgrpt(String vkgrpt) {
		this.vkgrpt = vkgrpt;
	}
	public String getZkunnr() {
		return zkunnr;
	}
	public void setZkunnr(String zkunnr) {
		this.zkunnr = zkunnr;
	}
	public String getZkunnr_nm() {
		return zkunnr_nm;
	}
	public void setZkunnr_nm(String zkunnr_nm) {
		this.zkunnr_nm = zkunnr_nm;
	}
	public String getBstnk() {
		return bstnk;
	}
	public void setBstnk(String bstnk) {
		this.bstnk = bstnk;
	}
	public String getBigo01() {
		return bigo01;
	}
	public void setBigo01(String bigo01) {
		this.bigo01 = bigo01;
	}
	public String getRepl01() {
		return repl01;
	}
	public void setRepl01(String repl01) {
		this.repl01 = repl01;
	}
	public String getFindat() {
		return findat;
	}
	public void setFindat(String findat) {
		this.findat = findat;
	}
	public String getReqdat() {
		return reqdat;
	}
	public void setRqser(String rqser) {
		this.rqser = rqser;
	}
	public String getRqser() {
		return rqser;
	}
	public void setReqdat(String reqdat) {
		this.reqdat = reqdat;
	}
	public String getIs_lo() {
		return is_lo;
	}
	public void setIs_lo(String is_lo) {
		this.is_lo = is_lo;
	}
	public String getIs_dm() {
		return is_dm;
	}
	public void setIs_dm(String is_dm) {
		this.is_dm = is_dm;
	}
	public String getKunnr_z3() {
		return kunnr_z3;
	}
	public void setKunnr_z3(String kunnr_z3) {
		this.kunnr_z3 = kunnr_z3;
	}
	public String getKunnr_z3_nm() {
		return kunnr_z3_nm;
	}
	public void setKunnr_z3_nm(String kunnr_z3_nm) {
		this.kunnr_z3_nm = kunnr_z3_nm;
	}
}
