package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;

import java.math.BigDecimal;

/**
 * 건설사(시공사)보정데이터
 */
public class ZPSTW1710 extends ParameterVO {
    private String KUNNR;
    private BigDecimal REV;
    private String BIGO;

    public String getKUNNR() {
        return KUNNR;
    }

    public void setKUNNR(String KUNNR) {
        this.KUNNR = KUNNR;
    }

    public BigDecimal getREV() {
        return REV;
    }

    public void setREV(BigDecimal REV) {
        this.REV = REV;
    }

    public String getBIGO() {
        return BIGO;
    }

    public void setBIGO(String BIGO) {
        this.BIGO = BIGO;
    }
}
