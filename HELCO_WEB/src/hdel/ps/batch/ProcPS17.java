package hdel.ps.batch;

import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import hdel.ps.domain.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.result.DefaultResultHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.service.core.logger.Logger;
import tit.service.resource.ResourceFactory;
import tit.service.resource.TransactionResource;
import tit.service.scheduler.ScheduleTask;

import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 설치로드 데이터 생성 프로세스
 */
public class ProcPS17 extends AbstractBusinessService implements ScheduleTask {
    static Logger logger;

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmmss");
    private static final String RESOURCE = "../config/mybatis-config.xml";
    private static final String MANDT = "183";
    private static final String MDT = "jdbc/hep";
    private static final String ACTSS = " ";
    private static final String LIFNR = " ";

    /**
     * 김장동 : 'T51688'
     * 정재규 : 'T99982','T10042'
     * 김주원 : 'T10043'
     * 김태우 : 'T10044'
     * 이강산 : 'T10047'
     */
    private static final String[] NO_PARTNER_CORP = new String[]{"T10042", "T10043", "T10044", "T10047", "T99982", "T51688"};
    private String mName = null;

    private SqlSession session = null;

    private PS17ParamVO param;

    private List<ZPSTW1703> zpstw1703List;
    private List<ZPSTW1704> zpstw1704List;
    private List<ZPSTW1705> zpstw1705List;
    private List<ZPSTW1706> zpstw1706List;
    private List<ZPSTW1707> zpstw1707List;
    private List<ZPSTW1708> zpstw1708List;
    private List<ZPSTW1709> zpstw1709List;
    private List<ZPSTW1710> zpstw1710List;
    private List<ZPSTW1711> zpstw1711List;
    private List<ZPSTW1712> zpstw1712List;

    private List<HashMap<String, Object>> holidays;

    private Date fstDateOfMonth;
    private Date lastDateOfMonth;
    private Date lastDateOf1thNextMonth;
    private Date lastDateOf2thNextMonth;
    private Date lastDateOf3thNextMonth;
    private Date lastDateOf4thNextMonth;

    private String bigo;

    public void init() {
        holidays = null;

        zpstw1703List = null;
        zpstw1704List = null;
        zpstw1705List = null;
        zpstw1706List = null;
        zpstw1707List = null;
        zpstw1708List = null;
        zpstw1709List = null;
        zpstw1710List = null;
        zpstw1711List = null;
        zpstw1712List = null;

        bigo = null;
    }

    public void run() throws SQLException {
        logger = ServiceManagerFactory.getLogger();

        init();

        // 배치 시간 딜레이
        try {
            double dValue = Math.random();

            int iValue = (int) (dValue * 10 * 3);

            TimeUnit.SECONDS.sleep(iValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("==============================================================================");
        System.out.println("[설치로드] RAW 데이터 생성 프로세스 프로세스 START : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
        System.out.println("==============================================================================\n");

        Reader reader;
        Connection conn = null;

        try {
            reader = Resources.getResourceAsReader(RESOURCE);

            conn = getConnection(MDT);
            if (conn == null) return;

            conn.setAutoCommit(true);

            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            session = sqlMapper.openSession(conn);

            param = new PS17ParamVO();
            param.setMandt(MANDT);
            Date now = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.DATE, -1);
            String MDATE = DATE_FORMAT.format(c.getTime());
            MDATE = getMdate(MDATE);
            param.setMdate(MDATE);
            param.setZzactss(ACTSS);
            param.setZzlifnr(LIFNR);

            fstDateOfMonth = DATE_FORMAT.parse(param.getFstDateOfMdate());
            lastDateOfMonth = DATE_FORMAT.parse(param.getLastDateOfMonth());
            lastDateOf1thNextMonth = DATE_FORMAT.parse(param.getLastDateOf1thNextMonth());
            lastDateOf2thNextMonth = DATE_FORMAT.parse(param.getLastDateOf2thNextMonth());
            lastDateOf3thNextMonth = DATE_FORMAT.parse(param.getLastDateOf3thNextMonth());
            lastDateOf4thNextMonth = DATE_FORMAT.parse(param.getLastDateOf4thNextMonth());

            HashMap<String, Object> holiday = findHoliday(DATE_FORMAT.parse(MDATE));
            if (holiday != null) {
                return;
            }

            ZPSTW1715 ZPSTW1715 = (ZPSTW1715) session.selectOne("selectZPSTW1715ForUpdate", param);
            if (ZPSTW1715 == null) {
                session.insert("insertZPSTW1715", param);
            } else {
                if (StringUtils.equalsIgnoreCase("Y", ZPSTW1715.getON_OFF())) {
                    return;
                }
                session.update("updateZPSTW1715ForStart", param);
            }

            session.delete("deleteZPSTW1713", param);
            if (isfstDay()) {
                session.delete("deleteZPSTW1714", param);
            }

            session.delete("deleteZPSTW1716", param);
            if (isfstDay()) {
                session.delete("deleteZPSTW1717", param);
            }

            final List<String> posids = new ArrayList<String>();
            final List<String> posidsDupl = new ArrayList<String>();

            final SqlSession finalSession = session;
            final Connection finalConn = conn;

            session.select("selectRawData", param, new DefaultResultHandler() {

                @Override
                public void handleResult(ResultContext resultContext) {
                    bigo = null;

                    HashMap<String, Object> obj = (HashMap<String, Object>) resultContext.getResultObject();
                    String posid = (String) obj.get("POSID");
                    if (posids.contains(posid)) {
                        posidsDupl.add(posid);
                        return;
                    }
                    posids.add(posid);

                    String PAPRDT = (String) obj.get("PAPRDT");
                    if (StringUtils.equals("1900-01-00", PAPRDT)) {
                        obj.put("PAPRDT", null);
                    }
                    if (StringUtils.isNotEmpty(PAPRDT) && PAPRDT.length() > 8) {
                        obj.put("PAPRDT", null);
                    }

                    Date now = new Date();
                    String CRTD_AT = TIME_FORMAT.format(now);
                    obj.put("CRTD_AT", CRTD_AT);


                    try {
                        finalSession.insert("insertZPSTW1713", obj);
                        if (isfstDay()) {
                            finalSession.insert("insertZPSTW1714", obj);
                        }
                        
                        HashMap<String, Object> mh = adjustMH(obj, param.getMdate());
                        mh.put("BIGO", bigo);
                        finalSession.insert("insertZPSTW1716", mh);
                        if (isfstDay()) {
                            finalSession.insert("insertZPSTW1717", mh);
                        }

                        bigo = null;
                        
                        HashMap<String, Object> capacity = applyCapacity(mh);
                        capacity.put("BIGO", bigo);

                        finalSession.insert("insertZPSTW1716", capacity);
                        if (isfstDay()) {
                            finalSession.insert("insertZPSTW1717", capacity);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (session != null) {
                            session.close();
                        }
                        try {
                            finalConn.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            session.update("updateZPSTW1715ForEnd", param);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            if (conn != null) {
                conn.close();
            }
            System.out.println("==============================================================================");
            System.out.println("[설치로드] RAW 데이터 생성 프로세스 프로세스 END : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
            System.out.println("==============================================================================\n");
        }
    }

    private int getYear(Date mdate, int ago) {
        Calendar c = Calendar.getInstance();
        c.setTime(mdate);
        c.add(Calendar.YEAR, ago);
        return c.get(Calendar.YEAR);
    }

    private void addBigo(String contents) {
        if (StringUtils.isEmpty(bigo)) {
            bigo = contents;
        } else {
            bigo += " / " + contents;
        }
    }

    /**
     * MH 산출
     *
     * @param item
     * @param _mdate
     * @return
     * @throws ParseException
     */
    private HashMap<String, Object> adjustMH(HashMap<String, Object> item, String _mdate) throws ParseException {
        // 카테고리 설정
        item.put("CATEGORY", "MH");

        // 로드산출일 고정
        Date MDATE = DATE_FORMAT.parse(_mdate);
        item.put("MDATE", MDATE);
        
        /*
        if (item.get("POSID").equals("N17427L01"))
        {
        	System.out.println("여기");
        }
        */
        // 잔여 MH = 발주 MH * (100 - 공정률)
        BigDecimal TOT_MH = (BigDecimal) item.get("TOT_MH");

        if (TOT_MH == null || TOT_MH.compareTo(BigDecimal.valueOf(0)) == 0) {
            String POSID = (String) item.get("POSID");
            String ZZGUBUN = (String) item.get("ZZGUBUN");
            if (StringUtils.equalsIgnoreCase("10", ZZGUBUN) || StringUtils.equalsIgnoreCase("13", ZZGUBUN)) {
                // 엘리베이터인 경우
                String ZSPEC1 = (String) item.get("ZSPEC1");
                if (item.get("EL_ASPD") != null && item.get("EL_AFQ") != null && item.get("EL_AMAN") != null && !"".equals(item.get("EL_ASPD")) && !"".equals(item.get("EL_AFQ")) && !"".equals(item.get("EL_AMAN"))) {
                    // 인승
                    Integer EL_AMAN = Integer.valueOf((String) item.get("EL_AMAN"));
                    // 속도
                    int EL_ASPD = ((BigDecimal) item.get("EL_ASPD")).intValue();
                    // 층수
                    int EL_AFQ = (Integer) item.get("EL_AFQ");
                    HashMap<String, Object> param = new HashMap<String, Object>();
                    param.put("ZSPEC1", ZSPEC1);
                    param.put("EL_AMAN", EL_AMAN);
                    param.put("EL_ASPD", EL_ASPD);
                    param.put("EL_AFQ", EL_AFQ);
                    HashMap<String, Object> ZPSTW1718 = (HashMap<String, Object>) session.selectOne("selectZPSTW1718", param);
                    if (ZPSTW1718 != null) {
                        for (int i = 1; i <= 10; i++) {
                            int year = getYear(MDATE, -i);
                            if (ZPSTW1718.get("Y" + year) != null && ((BigDecimal) ZPSTW1718.get("Y" + year)).compareTo(BigDecimal.valueOf(0)) > 0) {
                                TOT_MH = (BigDecimal) ZPSTW1718.get("Y" + year);
                                addBigo("기종:" + ZSPEC1 + ",인승:" + EL_AMAN + ",속도:" + EL_ASPD + ",층:" + EL_AFQ);
                                addBigo("TOT_MH:" + TOT_MH);
                                break;
                            }
                        }
                    }
                }
            } else if (StringUtils.equalsIgnoreCase("11", ZZGUBUN) || StringUtils.equalsIgnoreCase("14", ZZGUBUN)) {
                // 에스컬레이터인 경우
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("POSID", POSID);
                HashMap<String, Object> es = (HashMap<String, Object>) session.selectOne("selectForES", param);
                if (es != null) {
                    if (es.get("ES_ASUBW") != null && es.get("ES_ARISE") != null && !"".equals(es.get("ES_ASUBW")) && !"".equals(es.get("ES_ARISE"))) {
                        String ES_ASUBW = (String) es.get("ES_ASUBW");
                        Integer ES_ARISE = ((Double) es.get("ES_ARISE")).intValue();
                        param.put("GBN", ES_ASUBW);
                        param.put("SPCKY", ES_ARISE);
                        HashMap<String, Object> ZPSTW1719 = (HashMap<String, Object>) session.selectOne("selectZPSTW1719", param);
                        if (ZPSTW1719 != null) {
                            for (int i = 1; i <= 10; i++) {
                                int year = getYear(MDATE, -i);
                                if (ZPSTW1719.get("Y" + year) != null) {
                                    TOT_MH = (BigDecimal) ZPSTW1719.get("Y" + year);
                                    addBigo("ES_ASUBW:" + ES_ASUBW + ",ES_ARISE:" + ES_ARISE);
                                    addBigo("TOT_MH:" + TOT_MH);
                                    break;
                                }
                            }
                        }
                    }
                }
            } else if (StringUtils.equalsIgnoreCase("12", ZZGUBUN)) {
                // 무빙워크인 경우
                HashMap<String, Object> param = new HashMap<String, Object>();
                param.put("POSID", POSID);
                HashMap<String, Object> es = (HashMap<String, Object>) session.selectOne("selectForMW", param);
                if (es != null) {
                    if (es.get("MW_ARISE") != null && !"".equals(es.get("MW_ARISE"))) {
                        Integer MW_ARISE = ((Double) es.get("MW_ARISE")).intValue();
                        param.put("SPCKY", MW_ARISE);
                        HashMap<String, Object> ZPSTW1720 = (HashMap<String, Object>) session.selectOne("selectZPSTW1720", param);
                        if (ZPSTW1720 != null) {
                            for (int i = 1; i <= 10; i++) {
                                int year = getYear(MDATE, -i);
                                if (ZPSTW1720.get("Y" + year) != null) {
                                    TOT_MH = (BigDecimal) ZPSTW1720.get("Y" + year);
                                    addBigo("MW_ARISE:" + MW_ARISE);
                                    addBigo("TOT_MH:" + TOT_MH);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        // 완료체크된 일보 공정률
        BigDecimal PRO_R = (BigDecimal) item.get("PRO_R");
        if (PRO_R == null) {
            PRO_R = BigDecimal.valueOf(0);
        }

        if (TOT_MH == null) {
            addBigo("TOT_MH:0");
        } else {
            BigDecimal ZAN_MH = TOT_MH.multiply(BigDecimal.valueOf(1.0 - (PRO_R.floatValue() / 100)));
            item.put("ZAN_MH", ZAN_MH.setScale(4, BigDecimal.ROUND_HALF_UP));

            // 기종별보정
            item.put("ZAN_MH", getZSPEC1(item).setScale(2, BigDecimal.ROUND_HALF_UP));

            // 건설사보정
            item.put("ZAN_MH", getKUNNR(item).setScale(2, BigDecimal.ROUND_HALF_UP));

            // 지역별보정
            item.put("ZAN_MH", getZCITY(item).setScale(2, BigDecimal.ROUND_HALF_UP));

            // 교체호기 보정
            String ISREMD = (String) item.get("ISREMD");
            if (StringUtils.equalsIgnoreCase("Y", ISREMD)) {
                ZAN_MH = (BigDecimal) item.get("ZAN_MH");
                ZAN_MH = ZAN_MH.multiply(BigDecimal.valueOf(1)); // 2.8
                item.put("ZAN_MH", ZAN_MH);
                addBigo("교체보정*2:" + ZAN_MH);
            }
            
            System.out.println("교체보정*2 : "+ZAN_MH);
            addBigo("발주MH : " + TOT_MH + ", 잔여MH : " + item.get("ZAN_MH"));

            // 준공예정일 산출
            setZZCOMP(item);

            // 일자별 MH 분배
            setMHByDate(item);
        }
        return item;
    }

    /**
     * capacity 산출
     *
     * @param item
     * @return
     */
    @SuppressWarnings({"DuplicatedCode", "DuplicateExpressions"})
    private HashMap<String, Object> applyCapacity(HashMap<String, Object> item) {
        // 카테고리 설정
        item.put("CATEGORY", "Capacity");

        // 아이콘 설정
        item.put("HSTATUS", null);

        // 로드 산출일
        Date MDATE = (Date) item.get("MDATE");

        // 투입 공정 시간
        Date nextDate = MDATE;
        Date lastDate = getLastDate(nextDate);

        String POSID = (String) item.get("POSID");
        // E/S 여부
        boolean isEs = StringUtils.contains(POSID, "S");

        // 소장 미배정 여부
        String TEMNO = (String) item.get("TEMNO");
        boolean noTemNo = TEMNO == null || " ".equals(TEMNO) || "".equals(TEMNO);

        // 본사 직원 여부
        boolean isHQTemno = TEMNO != null && ArrayUtils.contains(NO_PARTNER_CORP, TEMNO);

        // 보유인원
        Integer BINWON = (Integer) item.get("BINWON");
        if (BINWON == null || BINWON == 0) {
            addBigo("보유인원 0->2");
            BINWON = 2;
        } else {
            addBigo("보유인원 :" + BINWON);
        }
        // 지원인원
        Integer JINWON = (Integer) item.get("JINWON");
        if (JINWON == null) {
            JINWON = 0;
        }
        addBigo("지원인원 : -" + JINWON);
        // 피지원인원
        Integer PJIWON = (Integer) item.get("PJIWON");
        if (PJIWON == null) {
            PJIWON = 0;
        }
        addBigo("피지원인원 : +" + JINWON);
        // 본사직원 => 보유인원7명
        if (isHQTemno) {
            addBigo("본사직원 => 보유인원7명");
            BINWON = 7;
        }
        // 소장 미정 => 보유인원2명
        if (noTemNo) {
            addBigo("소장미정 => 보유인원2명");
            BINWON = 2;
        }
        // 현인원	= 보유인원 - 지원인원 + 피지원인원
        int HINWON = BINWON - JINWON + PJIWON;	
        addBigo("현인원 :" + HINWON);
        if (HINWON <= 0) {
            HINWON = BINWON;
            addBigo("현인원0=>보유인원:" + BINWON);
        }

        // 로드산출일 작업일보 최종 투입 시간
        BigDecimal T_MH = (BigDecimal) item.get("T_MH");
        if (T_MH == null) {
            addBigo("작업일보 최종 투입 시간 : 0");
            T_MH = BigDecimal.valueOf(0);
        } else {
            addBigo("작업일보 최종 투입 시간 : " + T_MH);
        }

        // 일일 평균 작업시간
        BigDecimal MH_F_D = BigDecimal.valueOf(9);
        if (isEs) {
            MH_F_D = BigDecimal.valueOf(9);
            addBigo("E/S : 일일9시간");
        }

        // 평일 투입률
        BigDecimal rev = BigDecimal.valueOf(0.98);
        // 소장경력
        BigDecimal CAREAR = (BigDecimal) item.get("CAREAR");
        if (CAREAR == null) {
            CAREAR = BigDecimal.valueOf(2);
        }
        // 소장경력2년미만인 경우
        if (CAREAR.compareTo(BigDecimal.valueOf(2)) < 0) {
            rev = BigDecimal.valueOf(0.98);
            addBigo("소장경력2년미만 : 98%");
        }
        if (isEs) {
            rev = BigDecimal.valueOf(1);
            addBigo("E/S : 평일100%");
        }

        // 주말 투입률
        BigDecimal revSat = BigDecimal.valueOf(0.5);
        if (isEs) {
            revSat = BigDecimal.valueOf(0.85);
            addBigo("E/S : 주말85%");
        }

        // 투입 예정일 Capacity
        // 소장의 배정호기(현재월)
        Integer U_CNT0 = (Integer) item.get("U_CNT0");
        if (U_CNT0 == null || U_CNT0 == 0) {
            U_CNT0 = 1;
            addBigo("배정호기수: 0=>" + U_CNT0);
        }
        addBigo("소장의 배정호기수(현재월):" + U_CNT0);

        // 합산 제외 여부
        boolean isExcludeSum = isExcludeSum(item);
        if (isExcludeSum) {
            addBigo("합산제외:공정81.5%이상,지연70일이상");
        }

        BigDecimal LOD = BigDecimal.valueOf(0);
        BigDecimal L0 = BigDecimal.valueOf(0);
        BigDecimal L1 = BigDecimal.valueOf(0);
        BigDecimal L2 = BigDecimal.valueOf(0);
        BigDecimal L3 = BigDecimal.valueOf(0);
        BigDecimal L4 = BigDecimal.valueOf(0);

        int j = 0;
        while (nextDate.compareTo(lastDate) <= 0) {
            nextDate = getNextDate(MDATE, j);

            BigDecimal capacity = T_MH;
            BigDecimal mh = MH_F_D;

            // 휴일 여부 확인
            boolean isSaturday = false;
            BigDecimal revFinal = rev;
            HashMap<String, Object> holiday = findHoliday(nextDate);
            if (holiday != null) {
                if (StringUtils.equals("SAT", (String) holiday.get("DESC"))) {
                    // 토요일인 경우
                    isSaturday = true;
                    revFinal = revSat;
                } else {
                    // 일요일, 휴일의 경우
                    revFinal = BigDecimal.valueOf(0);
                    mh = BigDecimal.valueOf(0);
                }
            }

            // 당월
            if (lastDateOfMonth.compareTo(nextDate) >= 0) {	// 보유인원 X 일일 투입율 X MH / 소장의 배정 호기 수
                capacity = mh.multiply(BigDecimal.valueOf(HINWON)).multiply(revFinal).divide(BigDecimal.valueOf(U_CNT0), 2, BigDecimal.ROUND_HALF_UP);
                if (!isExcludeSum) {
                    LOD = LOD.add(capacity);
            
                }
                if (j == 0) {
                    capacity = T_MH;
                } else {	// 현인원
                    capacity = mh.multiply(BigDecimal.valueOf(HINWON)).multiply(revFinal).divide(BigDecimal.valueOf(U_CNT0), 2, BigDecimal.ROUND_HALF_UP);
                    
                    if (!isExcludeSum) {
                        L0 = L0.add(capacity);	// 당월 Capa는 N1부터 계산 (N0 제외)
                    }
                }
                
            } else if (lastDateOfMonth.compareTo(nextDate) < 0 && lastDateOf1thNextMonth.compareTo(nextDate) >= 0) {
                // 익월
                mh = BigDecimal.valueOf(9);
                if (!isEs) {
                    revFinal = BigDecimal.valueOf(0.98);
                    if (isSaturday) {
                        revFinal = BigDecimal.valueOf(0.5);
                    }
                }
                capacity = mh.multiply(BigDecimal.valueOf(HINWON)).multiply(revFinal).divide(BigDecimal.valueOf(U_CNT0), 2, BigDecimal.ROUND_HALF_UP);
                if (!isExcludeSum) {
                    L1 = L1.add(capacity);
                }
            } else if (lastDateOf1thNextMonth.compareTo(nextDate) < 0 && lastDateOf2thNextMonth.compareTo(nextDate) >= 0) {
                // 차월
                mh = BigDecimal.valueOf(9);
                if (!isEs) {
                    revFinal = BigDecimal.valueOf(0.98);
                    if (isSaturday) {
                        revFinal = BigDecimal.valueOf(0.5);
                    }
                }
                capacity = mh.multiply(BigDecimal.valueOf(HINWON)).multiply(revFinal).divide(BigDecimal.valueOf(U_CNT0), 2, BigDecimal.ROUND_HALF_UP);
                if (!isExcludeSum) {
                    L2 = L2.add(capacity);
                }
            } else if (lastDateOf2thNextMonth.compareTo(nextDate) < 0 && lastDateOf3thNextMonth.compareTo(nextDate) >= 0) {
                // 차차월
                mh = BigDecimal.valueOf(9);
                if (!isEs) {
                    revFinal = BigDecimal.valueOf(0.98);
                    if (isSaturday) {
                        revFinal = BigDecimal.valueOf(0.5);
                    }
                }
                capacity = mh.multiply(BigDecimal.valueOf(HINWON)).multiply(revFinal).divide(BigDecimal.valueOf(U_CNT0), 2, BigDecimal.ROUND_HALF_UP);
                if (!isExcludeSum) {
                    L3 = L3.add(capacity);
                }
            } else if (lastDateOf3thNextMonth.compareTo(nextDate) < 0 && lastDateOf4thNextMonth.compareTo(nextDate) >= 0) {
                // 차차차월
                mh = BigDecimal.valueOf(9);
                if (!isEs) {
                    revFinal = BigDecimal.valueOf(0.98);
                    if (isSaturday) {
                        revFinal = BigDecimal.valueOf(0.5);
                    }
                }
                capacity = mh.multiply(BigDecimal.valueOf(HINWON)).multiply(revFinal).divide(BigDecimal.valueOf(U_CNT0), 2, BigDecimal.ROUND_HALF_UP);
                if (!isExcludeSum) {
                    L4 = L4.add(capacity);
                }
            }

            item.put("N" + j, capacity.setScale(4, BigDecimal.ROUND_HALF_UP));

            j++;
        }

        item.put("LOD", LOD);
        item.put("L0", L0);
        item.put("L1", L1);
        item.put("L2", L2);
        item.put("L3", L3);
        item.put("L4", L4);

        return item;
    }

    @SuppressWarnings({"ForLoopReplaceableByForEach", "DuplicatedCode"})
    public HashMap<String, Object> findHoliday(Date ndate) {
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

    /**
     * 기종별 MH 보정
     *
     * @param item
     * @return
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    private BigDecimal getZSPEC1(HashMap<String, Object> item) {
        String ZSPEC1 = (String) item.get("ZSPEC1");
        BigDecimal value = (BigDecimal) item.get("ZAN_MH");
        List<ZPSTW1707> zpstw1707List = getAllZPSTW1707();
        for (int i = 0; i < zpstw1707List.size(); i++) {
            if (ZSPEC1 != null && ZSPEC1.equals(zpstw1707List.get(i).getZSPEC1())) {
                value = value.multiply(zpstw1707List.get(i).getREV());
                addBigo("기종보정 : " + zpstw1707List.get(i).getREV());
                break;
            }
        }
        return value;
    }

    public List<ZPSTW1707> getAllZPSTW1707() {
        if (zpstw1707List == null) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            zpstw1707List = session.selectList("selectZPSTW1707", param);
        }
        return zpstw1707List;
    }

    /**
     * 건축용도, 시행사, 건축사별 MH 보정
     *
     * @param item
     * @return
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    private BigDecimal getKUNNR(HashMap<String, Object> item) {
        // 건축용도
        String WWBLD = (String) item.get("WWBLD");
        BigDecimal value = (BigDecimal) item.get("ZAN_MH");
        List<ZPSTW1708> zpstw1708List = getAllZPSTW1708();
        for (int i = 0; i < zpstw1708List.size(); i++) {
            if (WWBLD != null && WWBLD.equals(zpstw1708List.get(i).getWWBLD())) {
                addBigo("건축용도MH보정 : " + zpstw1708List.get(i).getREV());
                return value.multiply(BigDecimal.valueOf(1.0).add(zpstw1708List.get(i).getREV()));
            }
        }
        // 시행사
        String ENFOR = (String) item.get("ENFOR");
        List<ZPSTW1709> zpstw1709List = getAllZPSTW1709();
        for (int i = 0; i < zpstw1709List.size(); i++) {
            if ("like".equalsIgnoreCase(zpstw1709List.get(i).getCRT())) {
                if (ENFOR != null && ENFOR.contains(zpstw1709List.get(i).getENFOR())) {
                    addBigo("시행사MH보정 : " + zpstw1709List.get(i).getREV());
                    return value.multiply(BigDecimal.valueOf(1.0).add(zpstw1709List.get(i).getREV()));
                }
            }
        }
        // 건축사(시공사)
        String KUNNR = (String) item.get("KUNNR");
        List<ZPSTW1710> zpstw1710List = getAllZPSTW1710();
        for (int i = 0; i < zpstw1710List.size(); i++) {
            if (KUNNR != null && KUNNR.equals(zpstw1710List.get(i).getKUNNR())) {
                addBigo("건축사MH보정 : " + zpstw1710List.get(i).getREV());
                return value.multiply(BigDecimal.valueOf(1.0).add(zpstw1710List.get(i).getREV()));
            }
        }
        return value;
    }

    /**
     * 지역별 MH 보정
     *
     * @param item
     * @return
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    private BigDecimal getZCITY(HashMap<String, Object> item) {
        BigDecimal value = (BigDecimal) item.get("ZAN_MH");
        String ZCITY = (String) item.get("ZCITY");
        List<ZPSTW1711> zpstw1711List = getAllZPSTW1711();
        for (int i = 0; i < zpstw1711List.size(); i++) {
            if (ZCITY != null && ZCITY.equals(zpstw1711List.get(i).getZCITY())) {
                value = value.multiply(zpstw1711List.get(i).getREV().add(BigDecimal.valueOf(1.0)));
                addBigo("지역별MH보정 : " + zpstw1711List.get(i).getREV().add(BigDecimal.valueOf(1.0)));
                break;
            }
        }
        return value;
    }

    /**
     * 준공예정일 보정
     *
     * @param item
     * @throws ParseException
     */
    private void setZZCOMP(HashMap<String, Object> item) throws ParseException {
        // 로드산출일
        Date mdate = (Date) item.get("MDATE");
        // 준공승인여부
        String STATE = (String) item.get("STATE");
        // 준공예정일
        Date ZZCOMP2 = DATE_FORMAT.parse((String) item.get("ZZCOMP2"));
        // QC검사일
        String PAPRDT = (String) item.get("PAPRDT");
        // 확정검사요청일
        String _REQ_DATE2 = (String) item.get("REQ_DATE2");

        // 준공예정일 보정 결과
        Date ZZCOMP;
        Calendar cal = Calendar.getInstance();
        if (StringUtils.isEmpty(PAPRDT) || StringUtils.equals(PAPRDT, "00000000")) {
            // Qc검사일 없는 경우
            addBigo("Qc검사일 없음");

            if (StringUtils.equals(_REQ_DATE2, "00000000") || StringUtils.isEmpty(_REQ_DATE2)) {
                ZZCOMP = ZZCOMP2;
                cal.setTime(ZZCOMP);

                addBigo("확정검사요청일 없음");
            } else {
                ZZCOMP = DATE_FORMAT.parse(_REQ_DATE2);
                cal.setTime(ZZCOMP);
                cal.add(Calendar.DATE, 10);

                addBigo("확정검사요청일 : " + _REQ_DATE2 + ", +10");
            }
            // 준공승인확인 N => +10일
            if (StringUtils.equalsIgnoreCase(STATE, "N")) {
                cal.add(Calendar.DATE, 10);
                addBigo("준공승인확인 N => +10일");
                // 준공14일전까지 준공승인 N이면 => +10일
                if (diffOfDate(mdate, ZZCOMP) <= 14) {
                    cal.add(Calendar.DATE, 10);
                    addBigo("준공14일전까지 준공승인 N이면 => +10일");
                }
            }
            // ZZCOMP
            if (cal.getTime().compareTo(ZZCOMP2) > 0) {
                ZZCOMP = cal.getTime();
            } else {
                ZZCOMP = ZZCOMP2;
            }

        } else {
            // Qc검사일 있는 경우
            addBigo("Qc검사일 있음");
            if (PAPRDT != null && !"00000000".equals(PAPRDT)) {
                ZZCOMP = DATE_FORMAT.parse(PAPRDT);
                cal.setTime(ZZCOMP);
            }
            // QC검사결과
            String DSKURZTEXT = (String) item.get("DSKURZTEXT");
            if (StringUtils.equals(DSKURZTEXT, "합격") || StringUtils.equals(DSKURZTEXT, "면제")) {
                cal.add(Calendar.DATE, 3);
                addBigo("합격 +3");
            } else if (StringUtils.equals(DSKURZTEXT, "조건부합격")) {
                cal.add(Calendar.DATE, 10);
                addBigo("조건부합격 +10");
            } else if (StringUtils.equals(DSKURZTEXT, "불합격")) {
                cal.add(Calendar.DATE, 20);
                addBigo("불합격 +20");
            }
            // ZZCOMP
            if (cal.getTime().compareTo(ZZCOMP2) > 0) {
                ZZCOMP = cal.getTime();
            } else {
                addBigo("준공예정 보정결과 < 준공예정일 => 준공예정일");
                ZZCOMP = ZZCOMP2;
            }
        }
        item.put("ZZCOMP", ZZCOMP);
        addBigo("보정후준공예정일" + DATE_FORMAT.format(ZZCOMP));
    }

    /**
     * 일자별 MH 분배
     *
     * @param item
     * @throws ParseException
     */
    @SuppressWarnings("DuplicatedCode")
    private void setMHByDate(HashMap<String, Object> item) throws ParseException {
    	/*
    	if(item.get("POSID").equals("N17121L01"))
    	{
    		System.out.println("여기");
    	}*/
        // 로드산출일
        Date MDATE = (Date) item.get("MDATE");
        // 착공예정일
        Date ZZSHIP1 = DATE_FORMAT.parse((String) item.get("ZZSHIP1"));

        Date nextDate = MDATE;
        Date lastDate = getLastDate(MDATE);

        Date ZZCOMP = (Date) item.get("ZZCOMP");
        if (ZZCOMP.compareTo(lastDate) <= 0) {
            lastDate = ZZCOMP;
        }

        // 설치기간 = 준공예정일 - 로드산출일
        int mDiff = diffOfDate(MDATE, ZZCOMP);
        item.put("mDiff", mDiff);

        // 지사별 MH 분배 판정 데이터 취득
        ZPSTW1712 zpstw1712 = findZPSTW1712(item);
        if (zpstw1712 != null) {
            addBigo(zpstw1712.toString());
        }

        // 일자별 미진행 호기 MH 보정 데이터 취득
        ZPSTW1706 zpstw1706 = findZPSTW1706(item);
        if (zpstw1706 != null) {
            if (MDATE.compareTo(ZZSHIP1) < 0) {
                addBigo(zpstw1706.toString());
            }
        }

        // 일자별 진행 호기 MH 보정 데이터 취득
        ZPSTW1704 zpstw1704 = findZPSTW1704(item);
        if (zpstw1704 != null) {
            if (MDATE.compareTo(ZZSHIP1) >= 0) {
                addBigo(zpstw1704.toString());
            }
        }

        // 잔여MH
        BigDecimal ZAN_MH = (BigDecimal) item.get("ZAN_MH");

        // 합산 제외 여부
        boolean isExcludeSum = isExcludeSum(item);
        if (isExcludeSum) {
            addBigo("합산제외:공정81.5%이상,지연70일이상");
        }

        BigDecimal L0 = BigDecimal.valueOf(0);
        BigDecimal L1 = BigDecimal.valueOf(0);
        BigDecimal L2 = BigDecimal.valueOf(0);
        BigDecimal L3 = BigDecimal.valueOf(0);
        BigDecimal L4 = BigDecimal.valueOf(0);

        int i = 0;
        //일자별로 쪼개는 로직.
        while (nextDate.compareTo(lastDate) <= 0) {	// 초기 로드 산출일 +1, +2, +3 ...
            nextDate = getNextDate(MDATE, i);

            BigDecimal mhByDate;

            // 휴일 여부 확인
            HashMap<String, Object> holiday = findHoliday(nextDate);
            if (holiday != null && !StringUtils.equals("SAT", (String) holiday.get("DESC"))) {
                mhByDate = BigDecimal.valueOf(0);
            } else {
                if (nextDate.compareTo(ZZCOMP) > 0) {
                    mhByDate = BigDecimal.valueOf(0);
                } else if (nextDate.compareTo(ZZSHIP1) < 0) {
                    mhByDate = BigDecimal.valueOf(0);
                } else {
                    if (zpstw1712 != null) {
                        // 지사별 로드 분배 판정에 해당하는 경우
                    	System.out.println("지사별 로드 분배 판정");
                        mhByDate = getMHByACTSS(MDATE, nextDate, ZZCOMP, i, ZAN_MH, zpstw1712);
                    } else {
                        // 지사별 로드 분배 판정에 해당하지 않는 경우
                        if (MDATE.compareTo(ZZSHIP1) < 0) {
                            // 미진행호기
                        	System.out.println("미진행호기");
                            mhByDate = getMHByDateNotOnProcess(MDATE, nextDate, ZZCOMP, diffOfDate(ZZSHIP1, nextDate), ZAN_MH, zpstw1706);
                        } else {
                        	System.out.println("진행호기");
                            // 진행호기
                            mhByDate = getMHByDateOnProceeding(MDATE, nextDate, ZZCOMP, i, ZAN_MH, zpstw1704);
                        }
                    }
                }
            }

            item.put("N" + i, mhByDate);

            if (!isExcludeSum) {
                if (lastDateOfMonth.compareTo(nextDate) >= 0) {
                    L0 = L0.add(mhByDate);
                } else if (lastDateOfMonth.compareTo(nextDate) < 0 && lastDateOf1thNextMonth.compareTo(nextDate) >= 0) {
                    L1 = L1.add(mhByDate);
                } else if (lastDateOf1thNextMonth.compareTo(nextDate) < 0 && lastDateOf2thNextMonth.compareTo(nextDate) >= 0) {
                    L2 = L2.add(mhByDate);
                } else if (lastDateOf2thNextMonth.compareTo(nextDate) < 0 && lastDateOf3thNextMonth.compareTo(nextDate) >= 0) {
                    L3 = L3.add(mhByDate);
                } else if (lastDateOf3thNextMonth.compareTo(nextDate) < 0 && lastDateOf4thNextMonth.compareTo(nextDate) >= 0) {
                    L4 = L4.add(mhByDate);
                }
            }

            i++;
        }
        item.put("LOD", L0);
        item.put("L0", L0);
        item.put("L1", L1);
        item.put("L2", L2);
        item.put("L3", L3);
        item.put("L4", L4);
    }

    /**
     * 합산 제외
     *
     * @param item
     * @return
     */
    private boolean isExcludeSum(HashMap<String, Object> item) {
        BigDecimal PRO_R = (BigDecimal) item.get("PRO_R");
        if (PRO_R == null || PRO_R.compareTo(BigDecimal.valueOf(81.5)) < 0) {
            return false;
        }
        Integer DAYCNT = (Integer) item.get("DAYCNT");
        if (DAYCNT == null || DAYCNT < 70) {
            return false;
        }
        return PRO_R.compareTo(BigDecimal.valueOf(81.5)) >= 0;
    }

    /**
     * 진행호기 MH 분배
     *
     * @param MDATE
     * @param ndate
     * @param ZZCOMP
     * @param nth
     * @param ZAN_MH
     * @param zpstw1704
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("DuplicatedCode")
    private BigDecimal getMHByDateOnProceeding(Date MDATE, Date ndate, Date ZZCOMP, int nth, BigDecimal ZAN_MH, ZPSTW1704 zpstw1704) throws ParseException {
        // 보정 비율 결과
        BigDecimal rev;
        if (zpstw1704 == null) {
            // 미해당의 경우 보정 없음
            rev = BigDecimal.valueOf(1);
        } else {
            if (nth >= 91) {
                // 91일 ~
                rev = zpstw1704.getR4();
            } else if (nth >= 61) {
                // 61일 ~ 90일
                rev = zpstw1704.getR3();
            } else if (nth >= 31) {
                // 31일 ~ 60일
                rev = zpstw1704.getR2();
            } else {
                // ~ 30일
                rev = zpstw1704.getR1();
            }
        }
        float correction = ZAN_MH.multiply(rev).floatValue();
        int div = getDiv(MDATE, ndate, ZZCOMP);
        if (div == 0) {
            return BigDecimal.valueOf(0);
        }
        correction = correction / div;
        return BigDecimal.valueOf(correction).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 미진행호기 MH 분배
     *
     * @param MDATE
     * @param ndate
     * @param ZZCOMP
     * @param nth
     * @param ZAN_MH
     * @param zpstw1706
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("DuplicatedCode")
    private BigDecimal getMHByDateNotOnProcess(Date MDATE, Date ndate, Date ZZCOMP, int nth, BigDecimal ZAN_MH, ZPSTW1706 zpstw1706) throws ParseException {
        // 보정 비율 결과
        BigDecimal rev;
        if (zpstw1706 == null) {
            // 미해당의 경우 보정 없음
            rev = BigDecimal.valueOf(1);
        } else {
            if (nth >= 91) {
                // 91일 ~
                rev = zpstw1706.getR4();
            } else if (nth >= 61) {
                // 61일 ~ 90일
                rev = zpstw1706.getR3();
            } else if (nth >= 31) {
                // 31일 ~ 60일
                rev = zpstw1706.getR2();
            } else {
                // ~ 30일
                rev = zpstw1706.getR1();
            }
        }
        float correction = ZAN_MH.multiply(rev).floatValue();
        int div = getDiv(MDATE, ndate, ZZCOMP);
        if (div == 0) {
            return BigDecimal.valueOf(0);
        }
        correction = correction / div;

        return BigDecimal.valueOf(correction).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 지사별 MH 분배 판정
     *
     * @param MDATE
     * @param ndate
     * @param ZZCOMP
     * @param nth
     * @param ZAN_MH
     * @param zpstw1712
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("DuplicatedCode")
    private BigDecimal getMHByACTSS(Date MDATE, Date ndate, Date ZZCOMP, int nth, BigDecimal ZAN_MH, ZPSTW1712 zpstw1712) throws ParseException {
        // 보정 비율 결과
        BigDecimal rev;

        if (nth >= 91) {
            // 91일 ~
            rev = zpstw1712.getR4();
        } else if (nth >= 61) {
            // 61일 ~ 90일
            rev = zpstw1712.getR3();
        } else if (nth >= 31) {
            // 31일 ~ 60일
            rev = zpstw1712.getR2();
        } else {
            // ~ 30일
            rev = zpstw1712.getR1();
        }

        float correction = ZAN_MH.multiply(rev).floatValue();
        int div = getDiv(MDATE, ndate, ZZCOMP);
        if (div == 0) {
            return BigDecimal.valueOf(0);
        }
        correction = correction / div;

        return BigDecimal.valueOf(correction);
    }

    /**
     * 날짜수 구하기
     *
     * @param MDATE
     * @param ndate
     * @param ZZCOMP
     * @return
     * @throws ParseException
     */
    private int getDiv(Date MDATE, Date ndate, Date ZZCOMP) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(MDATE);

        int mDiff = diffOfDate(MDATE, ZZCOMP);

        // 나눔수
        int div = 30;
        // 몫
        int share = mDiff / 30;
        // 나머지
        int remainder = mDiff % 30;

        if (share == 0) {
            if (remainder == 0) {
                return 1;
            }
            int holidayCnt = countOfHoliday(MDATE, ZZCOMP);
            return remainder - holidayCnt;
        } else {
            Date prevEndline = MDATE;
            for (int i = 1; i <= share; i++) {
                cal.add(Calendar.DATE, div);
                Date endline = cal.getTime();
                if (ndate.compareTo(endline) <= 0) {
                    return 30 - countOfHoliday(prevEndline, endline);
                }
                prevEndline = endline;
            }
            return remainder - countOfHoliday(ndate, ZZCOMP);
        }
    }

    /**
     * 휴일수 구하기
     *
     * @param d1
     * @param d2
     * @return
     * @throws ParseException
     */
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
     * 토요일수 구하기
     *
     * @param d1
     * @param d2
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    private int countOfSaturday(Date d1, Date d2) throws ParseException {
        if (holidays == null) {
            holidays = getAllHoliday();
        }
        int count = 0;
        for (int i = 0; i < holidays.size(); i++) {
            Date holiday = DATE_FORMAT.parse((String) holidays.get(i).get("HOLIDAY_YMD"));
            if (d1.compareTo(holiday) <= 0 && d2.compareTo(holiday) >= 0 && StringUtils.equals("SAT", (String) holidays.get(i).get("DESC"))) {
                count++;
            }
        }
        return count;
    }

    public List<HashMap<String, Object>> getAllHoliday() {
        if (holidays == null || holidays.size() == 0) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            param.setTableName("DB2WEB.TB_HOLIDAY");
            holidays = session.selectList("selectHoliday", param);
        }
        return holidays;
    }

    private Date getNextDate(Date nowDate, int addDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(nowDate);
        c.add(Calendar.DATE, addDate);
        return c.getTime();
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public ZPSTW1712 findZPSTW1712(HashMap<String, Object> item) throws ParseException {
        int mDiff = (Integer) item.get("mDiff");

        // 지사
        String ACTSS = (String) item.get("ACTSS");

        // 진행 / 미진행 구분
        String GUBUN = (String) item.get("GUBUN");

        List<ZPSTW1712> list = getAllZPSTW1712();
        for (int i = 0; i < list.size(); i++) {
            if (StringUtils.equals(ACTSS, list.get(i).getACTSS()) &&
                    StringUtils.equals(GUBUN, list.get(i).getGUBUN()) &&
                    mDiff >= list.get(i).getRF().intValue() &&
                    mDiff <= list.get(i).getRT().intValue()) {
                return list.get(i);
            }
        }
        return null;
    }

    private ZPSTW1706 findZPSTW1706(HashMap<String, Object> item) {
        // 로드산출일 ~ 준공예정일
        int mDiff = (Integer) item.get("mDiff");

        // 교체여부
        String ISREMD = (String) item.get("ISREMD");
        if (ISREMD != null) {
            ISREMD = StringUtils.trim(ISREMD);
        }
        if (StringUtils.isEmpty(ISREMD)) {
            ISREMD = "N";
        }

        // 사양
        String ZSPEC2 = (String) item.get("ZSPEC2");
        if (ZSPEC2 != null) {
            ZSPEC2 = StringUtils.trim(ZSPEC2);
        }
        if (StringUtils.isNotEmpty(ZSPEC2)) {
            ZSPEC2 = ZSPEC2.substring(0, 2);
        }

        // 속도
        int EL_ASPD = item.get("EL_ASPD") == null ? 0 : ((BigDecimal) item.get("EL_ASPD")).intValue();

        // 층수
        int EL_AFQ = (Integer) item.get("EL_AFQ");

        // 총대수
        int ZQNTY = Integer.parseInt((String) item.get("ZQNTY"));

        List<ZPSTW1705> ZPSTW1705List = getAllZPSTW1705();
        for (int i = 0; ZPSTW1705List != null && i < ZPSTW1705List.size(); i++) {
            if (
                    (
                            StringUtils.equals(ISREMD, ZPSTW1705List.get(i).getISREMD())
                                    && StringUtils.contains(ZPSTW1705List.get(i).getZSPEC2(), ZSPEC2)
                                    && ((EL_ASPD >= ZPSTW1705List.get(i).getEL_ASPD_F() && EL_ASPD <= ZPSTW1705List.get(i).getEL_ASPD_T())
                                    || (EL_AFQ >= ZPSTW1705List.get(i).getEL_AFQ_F() && EL_AFQ <= ZPSTW1705List.get(i).getEL_AFQ_T())
                                    || (ZQNTY >= ZPSTW1705List.get(i).getZQNTY_F() && ZQNTY <= ZPSTW1705List.get(i).getZQNTY_T()))
                    ) ||
                            (
                                    StringUtils.equals(ISREMD, ZPSTW1705List.get(i).getISREMD())
                                            && StringUtils.contains(ZPSTW1705List.get(i).getZSPEC2(), ZSPEC2)
                                            && (ZPSTW1705List.get(i).getEL_ASPD_F() == 1 && ZPSTW1705List.get(i).getEL_ASPD_T() == 999999)
                                            && (ZPSTW1705List.get(i).getEL_AFQ_F() == 1 && ZPSTW1705List.get(i).getEL_AFQ_T() == 999999)
                                            && (ZPSTW1705List.get(i).getZQNTY_F() == 1 && ZPSTW1705List.get(i).getZQNTY_T() == 999999)
                            )
            ) {
                ZPSTW1705 ZPSTW1705 = ZPSTW1705List.get(i);
                List<ZPSTW1706> ZPSTW1706List = getAllZPSTW1706();
                for (int j = 0; ZPSTW1706List != null && j < ZPSTW1706List.size(); j++) {
                    if (ZPSTW1705.getID().equals(ZPSTW1706List.get(j).getID())
                            && mDiff >= ZPSTW1706List.get(j).getRF().intValue() && mDiff <= ZPSTW1706List.get(j).getRT().intValue()) {
                        return ZPSTW1706List.get(j);
                    }
                }
            }
        }
        return null;
    }

    private ZPSTW1704 findZPSTW1704(HashMap<String, Object> item) {
        // 로드산출일 ~ 준공예정일
        int mDiff = (Integer) item.get("mDiff");

        // 교체여부
        String ISREMD = (String) item.get("ISREMD");
        if (ISREMD != null) {
            ISREMD = StringUtils.trim(ISREMD);
        }
        if (StringUtils.isEmpty(ISREMD)) {
            ISREMD = "N";
        }

        // 사양
        String ZSPEC2 = (String) item.get("ZSPEC2");
        if (ZSPEC2 != null) {
            ZSPEC2 = StringUtils.trim(ZSPEC2);
        }
        if (StringUtils.isNotEmpty(ZSPEC2)) {
            ZSPEC2 = ZSPEC2.substring(0, 2);
        }

        // 속도
        int EL_ASPD = item.get("EL_ASPD") == null ? 0 : ((BigDecimal) item.get("EL_ASPD")).intValue();

        // 층수
        int EL_AFQ = (Integer) item.get("EL_AFQ");

        // 총대수
        int ZQNTY = Integer.parseInt((String) item.get("ZQNTY"));

        List<ZPSTW1703> ZPSTW1703List = getAllZPSTW1703();
        for (int i = 0; ZPSTW1703List != null && i < ZPSTW1703List.size(); i++) {
            if (
                    (
                            StringUtils.equals(ISREMD, ZPSTW1703List.get(i).getISREMD())
                                    && StringUtils.contains(ZPSTW1703List.get(i).getZSPEC2(), ZSPEC2)
                                    && ((EL_ASPD >= ZPSTW1703List.get(i).getEL_ASPD_F() && EL_ASPD <= ZPSTW1703List.get(i).getEL_ASPD_T())
                                    || (EL_AFQ >= ZPSTW1703List.get(i).getEL_AFQ_F() && EL_AFQ <= ZPSTW1703List.get(i).getEL_AFQ_T())
                                    || (ZQNTY >= ZPSTW1703List.get(i).getZQNTY_F() && ZQNTY <= ZPSTW1703List.get(i).getZQNTY_T()))
                    ) ||
                            (
                                    StringUtils.equals(ISREMD, ZPSTW1703List.get(i).getISREMD())
                                            && StringUtils.contains(ZPSTW1703List.get(i).getZSPEC2(), ZSPEC2)
                                            && (ZPSTW1703List.get(i).getEL_ASPD_F() == 1 && ZPSTW1703List.get(i).getEL_ASPD_T() == 999999)
                                            && (ZPSTW1703List.get(i).getEL_AFQ_F() == 1 && ZPSTW1703List.get(i).getEL_AFQ_T() == 999999)
                                            && (ZPSTW1703List.get(i).getZQNTY_F() == 1 && ZPSTW1703List.get(i).getZQNTY_T() == 999999)
                            )
            ) {
                ZPSTW1703 ZPSTW1703 = ZPSTW1703List.get(i);
                List<ZPSTW1704> ZPSTW1704List = getAllZPSTW1704();
                for (int j = 0; ZPSTW1704List != null && j < ZPSTW1704List.size(); j++) {
                    if (ZPSTW1703.getID().equals(ZPSTW1704List.get(j).getID()) &&
                            mDiff >= ZPSTW1704List.get(j).getRF().intValue() && mDiff <= ZPSTW1704List.get(j).getRT().intValue()) {
                        return ZPSTW1704List.get(j);
                    }
                }
            }
        }
        return null;
    }

    private Date getLastDate(Date nowDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(nowDate);
        c.add(Calendar.MONTH, 4);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    private int diffOfDate(Date begin, Date end) {
        long diff = end.getTime() - begin.getTime();
        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    public List<ZPSTW1708> getAllZPSTW1708() {
        if (zpstw1708List == null) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            zpstw1708List = session.selectList("selectZPSTW1708", param);
        }
        return zpstw1708List;
    }

    public List<ZPSTW1709> getAllZPSTW1709() {
        if (zpstw1709List == null) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            zpstw1709List = session.selectList("selectZPSTW1709", param);
        }
        return zpstw1709List;
    }

    public List<ZPSTW1710> getAllZPSTW1710() {
        if (zpstw1710List == null) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            zpstw1710List = session.selectList("selectZPSTW1710", param);
        }
        return zpstw1710List;
    }

    public List<ZPSTW1711> getAllZPSTW1711() {
        if (zpstw1711List == null) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            zpstw1711List = session.selectList("selectZPSTW1711", param);
        }
        return zpstw1711List;
    }

    public List<ZPSTW1712> getAllZPSTW1712() {
        if (zpstw1712List == null) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            zpstw1712List = session.selectList("selectZPSTW1712", param);
        }
        return zpstw1712List;
    }

    public List<ZPSTW1705> getAllZPSTW1705() {
        if (zpstw1705List == null) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            zpstw1705List = session.selectList("selectZPSTW1705", param);
        }
        return zpstw1705List;
    }

    public List<ZPSTW1706> getAllZPSTW1706() {
        if (zpstw1706List == null) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            zpstw1706List = session.selectList("selectZPSTW1706", param);
        }
        return zpstw1706List;
    }

    public List<ZPSTW1703> getAllZPSTW1703() {
        if (zpstw1703List == null) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            zpstw1703List = session.selectList("selectZPSTW1703", param);
        }
        return zpstw1703List;
    }

    public List<ZPSTW1704> getAllZPSTW1704() {
        if (zpstw1704List == null) {
            PS17ParamVO param = new PS17ParamVO();
            param.setDB2(true);
            zpstw1704List = session.selectList("selectZPSTW1704", param);
        }
        return zpstw1704List;
    }

    public Connection getConnection(String mdt) throws Exception {
        ResourceFactory resourceFactory = (ResourceFactory) ServiceManagerFactory.getServiceObject("DB2_HEQ"); //아무거나 사용해도 무방
        if (resourceFactory == null) {
            return null;
        }
        TransactionResource res = resourceFactory.makeResource(mdt); //mdt를 jdbc/hed, jdbc/heq, jdbc/hep, jdbc/cs 중에 어느것을 사용할지가 중요
        return (Connection) res.getResource();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    private String getMdate(String _mdate) throws ParseException {
        Date MDATE = DATE_FORMAT.parse(_mdate);
        HashMap<String, Object> holiday = findHoliday(MDATE);
        if (holiday == null) {
            return _mdate;
        } else {
            int i = 1;
            while (true) {
                Calendar c = Calendar.getInstance();
                c.setTime(MDATE);
                c.add(Calendar.DATE, -i);
                holiday = findHoliday(c.getTime());
                if (holiday == null) {
                    return DATE_FORMAT.format(c.getTime());
                }
                i++;
            }
        }
    }

    public boolean isfstDay() throws ParseException {
        String _fstDay = param.getFstDateOfMdate();
        Date fstDay = DATE_FORMAT.parse(_fstDay);
        HashMap<String, Object> holiday = findHoliday(fstDay);
        if (holiday != null) {
            int i = 1;
            while (true) {
                Calendar c = Calendar.getInstance();
                c.setTime(fstDay);
                c.add(Calendar.DATE, i);
                holiday = findHoliday(c.getTime());
                if (holiday == null) {
                    fstDay = c.getTime();
                    break;
                }
                i++;
            }
            _fstDay = DATE_FORMAT.format(fstDay);
        }
        return param.getMdate().equals(_fstDay);
    }
}
