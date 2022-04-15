package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;

import java.math.BigDecimal;

/**
 * 미진행호기 MH분배표 사양별 로드 분배표 마스터2
 */
public class ZPSTW1706 extends ParameterVO {

    // ZPSTW1703 ID
    private Integer ID;
    // 데이터산출일 ~ 준공예정일 from
    private BigDecimal RF;
    // 데이터산출일 ~ 준공예정일 to
    private BigDecimal RT;
    // ~30일
    private BigDecimal R1;
    // 31일 ~ 60일
    private BigDecimal R2;
    // 61일 ~ 90일
    private BigDecimal R3;
    // 91일 ~
    private BigDecimal R4;

    public String toString() {
        return "미진행호기MH분배 ID:" + ID + ",RF:" + RF + ",RT:" + RT + ",R1:" + R1 + ",R2:" + R2 + ",R3:" + R3 + ",R4:" + R4;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public BigDecimal getRF() {
        return RF;
    }

    public void setRF(BigDecimal RF) {
        this.RF = RF;
    }

    public BigDecimal getRT() {
        return RT;
    }

    public void setRT(BigDecimal RT) {
        this.RT = RT;
    }

    public BigDecimal getR1() {
        return R1;
    }

    public void setR1(BigDecimal r1) {
        this.R1 = r1;
    }

    public BigDecimal getR2() {
        if (R2 == null) {
            return getR1();
        }
        return R2;
    }

    public void setR2(BigDecimal r2) {
        this.R2 = r2;
    }

    public BigDecimal getR3() {
        if (R3 == null) {
            return getR2();
        }
        return R3;
    }

    public void setR3(BigDecimal r3) {
        this.R3 = r3;
    }

    public BigDecimal getR4() {
        if (R4 == null) {
            return getR3();
        }
        return R4;
    }

    public void setR4(BigDecimal r4) {
        this.R4 = r4;
    }
}
