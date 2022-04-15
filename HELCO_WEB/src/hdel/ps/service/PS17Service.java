package hdel.ps.service;

import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.service.SrmService;
import hdel.ps.dao.PS1701002Dao;
import hdel.ps.domain.PS17ParamVO;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PS17Service extends SrmService {
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    private SrmSqlSession sqlSession;

    private PS1701002Dao ps1701002Dao;

    private List<HashMap<String, Object>> groupByLifnr;

    private List<HashMap<String, Object>> groupByActss;

    private List<HashMap<String, Object>> groupByTeam;

    private List<HashMap<String, Object>> holidays;



    public void createDao(SqlSession sqlSession) {
        ps1701002Dao = sqlSession.getMapper(PS1701002Dao.class);
    }

    private boolean isLastMonth(String _mdate) throws ParseException {
        Date mdate = DATE_FORMAT.parse(_mdate);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(mdate);
        int monthOfMdate = c1.get(Calendar.MONTH);

        Date now = new Date();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(now);
        int monthOfNow = c2.get(Calendar.MONTH);
        return monthOfMdate < monthOfNow;
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public List<HashMap<String, Object>> groupByTeam(PS17ParamVO param) throws ParseException {
        String mdate = param.getMdate();
        if (isLastMonth(mdate)) {
            param.setTableName("ZPSTW1714");
            param.setTableName2("ZPSTW1717");
        } else {
            param.setTableName("ZPSTW1713");
            param.setTableName2("ZPSTW1716");
        }
        groupByTeam = ps1701002Dao.selectGroupByTeam(param);
        // MH = 0, CP > 0의 데이터 본 소속지사에 +하기, 지원 지사에 -하기
        if (groupByTeam != null && groupByTeam.size() > 0) {
            List<HashMap<String, Object>> plusMinusList = ps1701002Dao.selectGroupByTeamPlusMinus(param);
            if (plusMinusList != null) {
                for (int i = 0; i < groupByTeam.size(); i++) {
                    String GRUP = (String) groupByTeam.get(i).get("GRUP");
                    String ACTSS = (String) groupByTeam.get(i).get("ACTSS");
                    BigDecimal MH0 = (BigDecimal) groupByTeam.get(i).get("MH0");
                    BigDecimal CP0 = (BigDecimal) groupByTeam.get(i).get("CP0");
                    BigDecimal LOAD = (BigDecimal) groupByTeam.get(i).get("LOAD");
                    BigDecimal MH1 = (BigDecimal) groupByTeam.get(i).get("MH1");
                    BigDecimal CP1 = (BigDecimal) groupByTeam.get(i).get("CP1");
                    BigDecimal L1 = (BigDecimal) groupByTeam.get(i).get("L1");
                    BigDecimal MH2 = (BigDecimal) groupByTeam.get(i).get("MH2");
                    BigDecimal CP2 = (BigDecimal) groupByTeam.get(i).get("CP2");
                    BigDecimal L2 = (BigDecimal) groupByTeam.get(i).get("L2");
                    BigDecimal MH3 = (BigDecimal) groupByTeam.get(i).get("MH3");
                    BigDecimal CP3 = (BigDecimal) groupByTeam.get(i).get("CP3");
                    BigDecimal L3 = (BigDecimal) groupByTeam.get(i).get("L3");
                    String B0 = StringUtils.EMPTY;
                    String B1 = StringUtils.EMPTY;
                    String B2 = StringUtils.EMPTY;
                    String B3 = StringUtils.EMPTY;
                    boolean isGrup = false;
                    if (StringUtils.isNotEmpty(GRUP) && StringUtils.isEmpty(ACTSS)) {
                        isGrup = true;
                    }
                    for (int j = 0; j < plusMinusList.size(); j++) {
                        String _GRUP = (String) plusMinusList.get(j).get("GRUP");
                        String _ACTSS = (String) plusMinusList.get(j).get("ACTSS");
                        BigDecimal _CP0 = (BigDecimal) plusMinusList.get(j).get("CP0");
                        BigDecimal _CP1 = (BigDecimal) plusMinusList.get(j).get("CP1");
                        BigDecimal _CP2 = (BigDecimal) plusMinusList.get(j).get("CP2");
                        BigDecimal _CP3 = (BigDecimal) plusMinusList.get(j).get("CP3");
                        String LIFNR_NM = (String) plusMinusList.get(j).get("LIFNR_NM");
                        if ((isGrup && StringUtils.equals(GRUP, _GRUP)) || StringUtils.equals(ACTSS, _ACTSS)) {
                            if (_CP0.compareTo(BigDecimal.valueOf(0)) != 0) {
                                CP0 = CP0.add(_CP0);
                                B0 = addBigo(B0, LIFNR_NM + " : " + (_CP0.compareTo(BigDecimal.valueOf(0)) > 0 ? "+" + _CP0 : _CP0));
                            }
                            if (_CP1.compareTo(BigDecimal.valueOf(0)) != 0) {
                                CP1 = CP1.add(_CP1);
                                B1 = addBigo(B1, LIFNR_NM + " : " + (_CP1.compareTo(BigDecimal.valueOf(0)) > 0 ? "+" + _CP1 : _CP1));
                            }
                            if (_CP2.compareTo(BigDecimal.valueOf(0)) != 0) {
                                CP2 = CP2.add(_CP2);
                                B2 = addBigo(B2, LIFNR_NM + " : " + (_CP2.compareTo(BigDecimal.valueOf(0)) > 0 ? "+" + _CP2 : _CP2));
                            }
                            if (_CP3.compareTo(BigDecimal.valueOf(0)) != 0) {
                                CP3 = CP3.add(_CP3);
                                B3 = addBigo(B3, LIFNR_NM + " : " + (_CP3.compareTo(BigDecimal.valueOf(0)) > 0 ? "+" + _CP3 : _CP3));
                            }
                        }
                    }

                    LOAD = CP0.compareTo(BigDecimal.valueOf(0)) == 0 ? LOAD : MH0.divide(CP0, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    L1 = CP1.compareTo(BigDecimal.valueOf(0)) == 0 ? CP1 : MH1.divide(CP1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    L2 = CP2.compareTo(BigDecimal.valueOf(0)) == 0 ? CP2 : MH2.divide(CP2, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    L3 = CP3.compareTo(BigDecimal.valueOf(0)) == 0 ? CP3 : MH3.divide(CP3, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP);

                    groupByTeam.get(i).put("LOAD", LOAD);
                    groupByTeam.get(i).put("L1", L1);
                    groupByTeam.get(i).put("L2", L2);
                    groupByTeam.get(i).put("L3", L3);
                    groupByTeam.get(i).put("CP0", CP0);
                    groupByTeam.get(i).put("CP1", CP1);
                    groupByTeam.get(i).put("CP2", CP2);
                    groupByTeam.get(i).put("CP3", CP3);

                    groupByTeam.get(i).put("B0", B0);
                    groupByTeam.get(i).put("B1", B1);
                    groupByTeam.get(i).put("B2", B2);
                    groupByTeam.get(i).put("B3", B3);
                }
            }
        }
        for (int i = 0; i < groupByTeam.size(); i++) {
            BigDecimal LOAD = (BigDecimal) groupByTeam.get(i).get("L1");
            int HSTATUS = -1;
            if (!StringUtils.equals("현대EL", (String) groupByTeam.get(i).get("ACTSS_NM"))) {
                HSTATUS = getHStatusAbs(LOAD);
            }
            // 신호등
            groupByTeam.get(i).put("HSTATUS", HSTATUS);
        }
        return groupByTeam;
    }

    private String addBigo(String all, String added) {
        if (!StringUtils.isEmpty(all)) {
            all += "\r\n" + added;
        } else {
            all = added;
        }
        return all;
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public List<HashMap<String, Object>> groupByActss(PS17ParamVO param) throws ParseException {
        groupByTeam(param);

        String mdate = param.getMdate();
        if (isLastMonth(mdate)) {
            param.setTableName("ZPSTW1714");
            param.setTableName2("ZPSTW1717");
        } else {
            param.setTableName("ZPSTW1713");
            param.setTableName2("ZPSTW1716");
        }
        groupByActss = ps1701002Dao.selectGroupByActss(param);
        BigDecimal avg = findLoadOfAll();
        for (int i = 0; i < groupByActss.size(); i++) {
            BigDecimal LOAD = (BigDecimal) groupByActss.get(i).get("L1");
            int HSTATUS = getHStatus(LOAD, avg);
            // 신호등
            groupByActss.get(i).put("HSTATUS", HSTATUS);
        }
        return groupByActss;
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public List<HashMap<String, Object>> groupByLifnr(PS17ParamVO param) throws ParseException {
        groupByTeam(param);

        String mdate = param.getMdate();
        if (isLastMonth(mdate)) {
            param.setTableName("ZPSTW1714");
            param.setTableName2("ZPSTW1717");
        } else {
            param.setTableName("ZPSTW1713");
            param.setTableName2("ZPSTW1716");
        }
        groupByLifnr = ps1701002Dao.selectGroupByLifnr(param);
        for (int i = 0; i < groupByLifnr.size(); i++) {
            String ACTSS = (String) groupByLifnr.get(i).get("ACTSS");
            BigDecimal avg = findLoadByActss(ACTSS);
            BigDecimal LOAD = (BigDecimal) groupByLifnr.get(i).get("L1");
            int HSTATUS = getHStatus(LOAD, avg);
            // 신호등
            groupByLifnr.get(i).put("HSTATUS", HSTATUS);
        }
        return groupByLifnr;
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public List<HashMap<String, Object>> groupByTemno(PS17ParamVO param) throws ParseException {
        groupByTeam(param);

        String mdate = param.getMdate();
        if (isLastMonth(mdate)) {
            param.setTableName("ZPSTW1714");
            param.setTableName2("ZPSTW1717");
        } else {
            param.setTableName("ZPSTW1713");
            param.setTableName2("ZPSTW1716");
        }
        List<HashMap<String, Object>> result = ps1701002Dao.selectGroupByTemno(param);
        int diffNthNext = getDiffNthNext(mdate);
        if (diffNthNext > 0) {
            int diff = diffOfDate(DATE_FORMAT.parse(getMdate()), DATE_FORMAT.parse(mdate));
            for (int i = 0; i < result.size(); i++) {
                result.get(i).put("L0", result.get(i).get("L" + diffNthNext));
                for (int j = 0; j <= 153; j++) {
                    result.get(i).put("N" + j, j + diff > 153 ? 0.0 : result.get(i).get("N" + (j + diff)));
                }
            }
        }

        BigDecimal avg = findLoadByActss(param.getZzactss());
        for (int i = 0; i < result.size(); i++) {
            String POSID = (String) result.get(i).get("POSID");
            String category = (String) result.get(i).get("CATEGORY");
            BigDecimal LOAD = BigDecimal.valueOf(0);
            for (int j = 0; j < result.size(); j++) {
                if (StringUtils.equalsIgnoreCase("Load", category) && StringUtils.equals(POSID, (String) result.get(j).get("POSID"))) {
                    LOAD = (BigDecimal) result.get(j).get("L0");
                    break;
                }
            }
            result.get(i).put("L0", LOAD);
            if (StringUtils.equalsIgnoreCase("MH", category)) {
                int HSTATUS = getHStatus(LOAD, avg);
                // 신호등
                result.get(i).put("HSTATUS", HSTATUS);
            } else {
                result.get(i).put("HSTATUS", -1);
            }
            // 공정중 or 공정예정
            result.get(i).put("MDATE", DATE_FORMAT.parse(param.getMdate()));
            setGubun(result.get(i));
        }
        return result;
    }

    private int getHStatus(BigDecimal source, BigDecimal avg) {	// 평균치와 비교.
        BigDecimal substracted = source.subtract(avg);
        if (substracted.compareTo(BigDecimal.valueOf(20)) >= 0) {
            return 2;
        } else if (substracted.compareTo(BigDecimal.valueOf(20)) < 0 && substracted.compareTo(BigDecimal.valueOf(-10)) >= 0) {
            return 3;
        }
        return 1;
    }

    private int getHStatusAbs(BigDecimal source) {
        BigDecimal substracted = source.subtract(BigDecimal.valueOf(100));
        if (substracted.compareTo(BigDecimal.valueOf(20)) >= 0) {
            return 2;	// 신호등 : 빨강
        } else if (substracted.compareTo(BigDecimal.valueOf(20)) < 0 && substracted.compareTo(BigDecimal.valueOf(0)) >= 0) {
            return 3;	// 신호등 : 노랑
        }
        return 1;	// 신호등 : 초록
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    private BigDecimal findLoadByActss(String actss) {
        for (int i = 0; i < groupByTeam.size(); i++) {
            if (StringUtils.equals(actss, (String) groupByTeam.get(i).get("ACTSS"))) {
                return (BigDecimal) groupByTeam.get(i).get("LOAD");
            }
        }
        return null;
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    private BigDecimal findLoadOfAll() {
        for (int i = 0; i < groupByTeam.size(); i++) {
            if (StringUtils.equals("현대EL", (String) groupByTeam.get(i).get("ACTSS_NM"))) {
                return (BigDecimal) groupByTeam.get(i).get("LOAD");
            }
        }
        return null;
    }

    /**
     * 진행 구분 설정
     *
     * @param item
     * @throws ParseException
     */
    private void setGubun(HashMap<String, Object> item) throws ParseException {
        Date mdate = (Date) item.get("MDATE");
        String _ZZSHIP1 = (String) item.get("ZZSHIP1");
        if (_ZZSHIP1 == null) {
            item.put("GUBUN", "");
            item.put("GUBUN_NM", "착공예정일미지정");
            return;
        }
        Date ZZSHIP1 = DATE_FORMAT.parse(_ZZSHIP1);
        if (mdate.compareTo(ZZSHIP1) > 0) {
            item.put("GUBUN", "Y");
            item.put("GUBUN_NM", "공정중");
        } else {
            item.put("GUBUN", "N");
            item.put("GUBUN_NM", "착공예정");
        }
    }

    private int diffOfDate(Date begin, Date end) {
        long diff = end.getTime() - begin.getTime();
        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    private int diffOfMonth(Date begin, Date end) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(begin);
        c2.setTime(end);
        int m1 = c1.get(Calendar.MONTH) + 1;
        int m2 = c2.get(Calendar.MONTH) + 1;

        if (Math.abs(m1 - m2) >= 10) {
            if (begin.compareTo(end) < 0) {
                return (m1 - m2 - 12);
            } else {
                return (12 + m1 - m2);
            }
        }
        return m1 - m2;
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    private int countOfHoliday(Date d1, Date d2) throws ParseException {
        if (holidays == null) {
            holidays = getAllHoliday();
        }
        int count = 0;
        for (int i = 0; i < holidays.size(); i++) {
            Date holiday = DATE_FORMAT.parse((String) holidays.get(i).get("HOLIDAY_YMD"));
            if (d1.compareTo(holiday) <= 0 && d2.compareTo(holiday) >= 0 && !StringUtils.equals("SAT", (String) holidays.get(i).get("DESC"))) {
                count++;
            }
        }
        return count;
    }

    /**
     * 휴일 정보 취득
     *
     * @return
     */
    public List<HashMap<String, Object>> getAllHoliday() {
        if (holidays == null || holidays.size() == 0) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            holidays = ps1701002Dao.selectHoliday(param);
        }
        return holidays;
    }

    /**
     * list to Dataset
     *
     * @param resultList
     * @param ds
     */
    @SuppressWarnings({"rawtypes", "WhileLoopReplaceableByForEach"})
    public void list2Ds(List<HashMap<String, Object>> resultList, Dataset ds) {
        ds.setId("ds_output");
        if (resultList == null || resultList.size() == 0) {
            return;
        }

        Set keySet = resultList.get(0).keySet();
        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            ds.addColumn(key, ColumnInfo.COLTYPE_STRING, 256);
        }

        for (int i = 0; i < resultList.size(); i++) {
            ds.appendRow();
            HashMap<String, Object> item = resultList.get(i);
            Set set = item.entrySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                ds.setColumn(i, (String) entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : "");
            }
        }
    }

    public SrmSqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SrmSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int getDiffNthNext(String mdate) throws ParseException {
        Date actual = DATE_FORMAT.parse(mdate);
        Date expected = DATE_FORMAT.parse(getMdate());
        return diffOfMonth(actual, expected);
    }

    @SuppressWarnings("DuplicatedCode")
    private String getMdate() {
        Date mdate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(mdate);
        c.add(Calendar.DATE, -1);
        mdate = c.getTime();

        HashMap<String, Object> holiday = findHoliday(mdate);
        if (holiday == null) {
            return DATE_FORMAT.format(mdate);
        } else {
            while (true) {
                c.add(Calendar.DATE, -1);
                holiday = findHoliday(c.getTime());
                if (holiday == null) {
                    return DATE_FORMAT.format(c.getTime());
                }
            }
        }
    }

    @SuppressWarnings({"ForLoopReplaceableByForEach", "DuplicatedCode"})
    private HashMap<String, Object> findHoliday(Date ndate) {
        String NDATE = DATE_FORMAT.format(ndate);
        if (holidays == null) {
            holidays = getAllHoliday();
        }
        for (int i = 0; i < holidays.size(); i++) {
            String HOLIDAY_YMD = (String) holidays.get(i).get("HOLIDAY_YMD");
            if (StringUtils.equals(NDATE, HOLIDAY_YMD)) {
                return holidays.get(i);
            }
        }
        return null;
    }

}
