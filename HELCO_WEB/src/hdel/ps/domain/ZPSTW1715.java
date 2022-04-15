package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;

import java.math.BigDecimal;

/**
 * 배치 프로세스 실행 On / Off 데이터
 */
public class ZPSTW1715 extends ParameterVO {
    private String ON_OFF;
    private String START_AT;
    private String END_AT;

    public String getON_OFF() {
        return ON_OFF;
    }

    public void setON_OFF(String ON_OFF) {
        this.ON_OFF = ON_OFF;
    }

    public String getSTART_AT() {
        return START_AT;
    }

    public void setSTART_AT(String START_AT) {
        this.START_AT = START_AT;
    }

    public String getEND_AT() {
        return END_AT;
    }

    public void setEND_AT(String END_AT) {
        this.END_AT = END_AT;
    }
}
