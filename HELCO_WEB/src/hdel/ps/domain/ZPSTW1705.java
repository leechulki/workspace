package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;

/**
 * 미진행호기 로드분배표 사양별 로드 분배표 마스터1
 */
public class ZPSTW1705 extends ParameterVO {

    // auto increment
    private Integer ID;
    // 교체여부
    private String ISREMD;
    // 사양
    private String ZSPEC2;
    // 속도 from
    private Integer EL_ASPD_F;
    // 속도 to
    private Integer EL_ASPD_T;
    // 층수 from
    private Integer EL_AFQ_F;
    // 층수 to
    private Integer EL_AFQ_T;
    // 총대수 from
    private Integer ZQNTY_F;
    // 총대수 to
    private Integer ZQNTY_T;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getISREMD() {
        return ISREMD;
    }

    public void setISREMD(String ISREMD) {
        this.ISREMD = ISREMD;
    }

    public String getZSPEC2() {
        return ZSPEC2;
    }

    public void setZSPEC2(String ZSPEC2) {
        this.ZSPEC2 = ZSPEC2;
    }

    public Integer getEL_ASPD_F() {
        if (EL_ASPD_F == null || EL_ASPD_F == 0) {
            return 1;
        }
        return EL_ASPD_F;
    }

    public void setEL_ASPD_F(Integer EL_ASPD_F) {
        this.EL_ASPD_F = EL_ASPD_F;
    }

    public Integer getEL_ASPD_T() {
        if (EL_ASPD_T == null || EL_ASPD_T == 0) {
            return 999999;
        }
        return EL_ASPD_T;
    }

    public void setEL_ASPD_T(Integer EL_ASPD_T) {
        this.EL_ASPD_T = EL_ASPD_T;
    }

    public Integer getEL_AFQ_F() {
        if (EL_AFQ_F == null || EL_AFQ_F == 0) {
            return 1;
        }
        return EL_AFQ_F;
    }

    public void setEL_AFQ_F(Integer EL_AFQ_F) {
        this.EL_AFQ_F = EL_AFQ_F;
    }

    public Integer getEL_AFQ_T() {
        if (EL_AFQ_T == null || EL_AFQ_T == 0) {
            return 999999;
        }
        return EL_AFQ_T;
    }

    public void setEL_AFQ_T(Integer EL_AFQ_T) {
        this.EL_AFQ_T = EL_AFQ_T;
    }

    public Integer getZQNTY_F() {
        if (ZQNTY_F == null || ZQNTY_F == 0) {
            return 1;
        }
        return ZQNTY_F;
    }

    public void setZQNTY_F(Integer ZQNTY_F) {
        this.ZQNTY_F = ZQNTY_F;
    }

    public Integer getZQNTY_T() {
        if (ZQNTY_T == null || ZQNTY_T == 0) {
            return 999999;
        }
        return ZQNTY_T;
    }

    public void setZQNTY_T(Integer ZQNTY_T) {
        this.ZQNTY_T = ZQNTY_T;
    }
}
