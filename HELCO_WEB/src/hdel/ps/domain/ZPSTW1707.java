package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;

import java.math.BigDecimal;

/**
 * 기종별 보정값 마스터
 */
public class ZPSTW1707 extends ParameterVO {
    private String ZSPEC1;
    private BigDecimal REV;

    public String getZSPEC1() {
        return ZSPEC1;
    }

    public void setZSPEC1(String ZSPEC1) {
        this.ZSPEC1 = ZSPEC1;
    }

    public BigDecimal getREV() {
        return REV;
    }

    public void setREV(BigDecimal REV) {
        this.REV = REV;
    }
}
