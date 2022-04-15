package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;

import java.math.BigDecimal;

/**
 * 지역 보정 데이터
 */
public class ZPSTW1711 extends ParameterVO {
    private String ZCITY;
    private BigDecimal REV;
    private String BIGO;

    public String getZCITY() {
        return ZCITY;
    }

    public void setZCITY(String ZCITY) {
        this.ZCITY = ZCITY;
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
