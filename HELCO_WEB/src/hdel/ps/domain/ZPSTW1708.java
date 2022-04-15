package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;

import java.math.BigDecimal;

/**
 * 건물용도보정데이터
 */
public class ZPSTW1708 extends ParameterVO {
    private String WWBLD;
    private BigDecimal REV;
    private String BIGO;

    public String getWWBLD() {
        return WWBLD;
    }

    public void setWWBLD(String WWBLD) {
        this.WWBLD = WWBLD;
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
