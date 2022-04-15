package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;

import java.math.BigDecimal;

/**
 * ������ȣ�� MH�й�ǥ ��纰 �ε� �й�ǥ ������2
 */
public class ZPSTW1706 extends ParameterVO {

    // ZPSTW1703 ID
    private Integer ID;
    // �����ͻ����� ~ �ذ������� from
    private BigDecimal RF;
    // �����ͻ����� ~ �ذ������� to
    private BigDecimal RT;
    // ~30��
    private BigDecimal R1;
    // 31�� ~ 60��
    private BigDecimal R2;
    // 61�� ~ 90��
    private BigDecimal R3;
    // 91�� ~
    private BigDecimal R4;

    public String toString() {
        return "������ȣ��MH�й� ID:" + ID + ",RF:" + RF + ",RT:" + RT + ",R1:" + R1 + ",R2:" + R2 + ",R3:" + R3 + ",R4:" + R4;
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
