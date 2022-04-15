package hdel.ps.domain;

import hdel.lib.domain.ParameterVO;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PS17ParamVO extends ParameterVO {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    private boolean DB2 = true;

    private String req;

    private String tableName;
    private String tableName2;

    private String mandt;
    private String mdate;

    private String zzactss;
    private String zzlifnr;
    private String zzpmnum;

    private String chk;

    private String load;
    private String l0;
    private String l1;
    private String l2;

    private int diffNthNext;

    public boolean isDB2() {
        return DB2;
    }

    public void setDB2(boolean DB2) {
        this.DB2 = DB2;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName2() {
        return tableName2;
    }

    public void setTableName2(String tableName2) {
        this.tableName2 = tableName2;
    }

    public String getMandt() {
        return mandt;
    }

    public void setMandt(String mandt) {
        this.mandt = mandt;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getZzactss() {
        return zzactss;
    }

    public void setZzactss(String zzactss) {
        this.zzactss = zzactss;
    }

    public String getZzlifnr() {
        return zzlifnr;
    }

    public void setZzlifnr(String zzlifnr) {
        this.zzlifnr = zzlifnr;
    }

    public String getZzpmnum() {
        return zzpmnum;
    }

    public void setZzpmnum(String zzpmnum) {
        this.zzpmnum = zzpmnum;
    }

    public String getChk() {
        return chk;
    }

    public void setChk(String chk) {
        this.chk = chk;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getL0() {
        return l0;
    }

    public void setL0(String l0) {
        this.l0 = l0;
    }

    public String getL1() {
        return l1;
    }

    public void setL1(String l1) {
        this.l1 = l1;
    }

    public String getL2() {
        return l2;
    }

    public void setL2(String l2) {
        this.l2 = l2;
    }

    public int getDiffNthNext() {
        return diffNthNext;
    }

    public void setDiffNthNext(int diffNthNext) {
        this.diffNthNext = diffNthNext;
    }

    public String getFstDateOfMdate() {
        if (mdate == null) {
            return null;
        }
        return StringUtils.substring(mdate, 0, 6) + "01";
    }

    public String getMdateAfter30Days() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.DATE, 30);
        return DATE_FORMAT.format(c.getTime());
    }

    @SuppressWarnings("DuplicatedCode")
    public String getFstDateOf1thNextMonth() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return DATE_FORMAT.format(c.getTime());
    }

    @SuppressWarnings("DuplicatedCode")
    public String getFstDateOf2thNextMonth() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.MONTH, 2);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return DATE_FORMAT.format(c.getTime());
    }

    @SuppressWarnings("DuplicatedCode")
    public String getFstDateOf3thNextMonth() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.MONTH, 3);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return DATE_FORMAT.format(c.getTime());
    }

    @SuppressWarnings("DuplicatedCode")
    public String getFstDateOf4thNextMonth() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.MONTH, 4);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return DATE_FORMAT.format(c.getTime());
    }

    @SuppressWarnings("DuplicatedCode")
    public String getLastDateOf1MonthAgo() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DATE_FORMAT.format(c.getTime());
    }

    @SuppressWarnings("DuplicatedCode")
    public String getLastDateOfMonth() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DATE_FORMAT.format(c.getTime());
    }

    @SuppressWarnings("DuplicatedCode")
    public String getLastDateOf1thNextMonth() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DATE_FORMAT.format(c.getTime());
    }

    @SuppressWarnings("DuplicatedCode")
    public String getLastDateOf2thNextMonth() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.MONTH, 2);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DATE_FORMAT.format(c.getTime());
    }

    @SuppressWarnings("DuplicatedCode")
    public String getLastDateOf3thNextMonth() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.MONTH, 3);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DATE_FORMAT.format(c.getTime());
    }

    @SuppressWarnings("DuplicatedCode")
    public String getLastDateOf4thNextMonth() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.MONTH, 4);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DATE_FORMAT.format(c.getTime());
    }

    public String getOneYearAgo() throws ParseException {
        if (mdate == null) {
            return null;
        }
        Date _mdate = DATE_FORMAT.parse(mdate);
        Calendar c = Calendar.getInstance();
        c.setTime(_mdate);
        c.add(Calendar.YEAR, -1);
        return DATE_FORMAT.format(c.getTime());
    }
}
