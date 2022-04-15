package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;

import java.math.BigDecimal;

/**
 * ����MH�й� ������
 */
public class ZPSTW1712 extends ParameterVO {
    // �����ڵ�
    private String ACTSS;
    // ���� : Y, ������ : N
    private String GUBUN;
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
        return "����MH�й� " + "ACTSS:" + ACTSS + ",GUBUN:" + GUBUN + ",RF:" + RF + ",RT:" + RT + ",R1:" + R1 + ",R2:" + R2 + ",R3:" + R3 + ",R4:" + R4;
    }

    public String getACTSS() {
        return ACTSS;
    }

    public void setACTSS(String ACTSS) {
        this.ACTSS = ACTSS;
    }

    public String getGUBUN() {
        return GUBUN;
    }

    public void setGUBUN(String GUBUN) {
        this.GUBUN = GUBUN;
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
        return R2;
    }

    public void setR2(BigDecimal r2) {
        this.R2 = r2;
    }

    public BigDecimal getR3() {
        return R3;
    }

    public void setR3(BigDecimal r3) {
        this.R3 = r3;
    }

    public BigDecimal getR4() {
        return R4;
    }

    public void setR4(BigDecimal r4) {
        this.R4 = r4;
    }
}
