package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;

import java.math.BigDecimal;

/**
 * 시행사보정데이터
 */
public class ZPSTW1709 extends ParameterVO {
    private String ENFOR;
    private String CRT;
    private BigDecimal REV;

    public String getENFOR() {
        return ENFOR;
    }

    public void setENFOR(String ENFOR) {
        this.ENFOR = ENFOR;
    }

    public String getCRT() {
        return CRT;
    }

    public void setCRT(String CRT) {
        this.CRT = CRT;
    }

    public BigDecimal getREV() {
        return REV;
    }

    public void setREV(BigDecimal REV) {
        this.REV = REV;
    }
}
