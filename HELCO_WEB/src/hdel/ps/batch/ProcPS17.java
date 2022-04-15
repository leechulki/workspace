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
 * ��ġ�ε� ������ ���� ���μ���
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
     * ���嵿 : 'T51688'
     * ����� : 'T99982','T10042'
     * ���ֿ� : 'T10043'
     * ���¿� : 'T10044'
     * �̰��� : 'T10047'
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

        // ��ġ �ð� ������
        try {
            double dValue = Math.random();

            int iValue = (int) (dValue * 10 * 3);

            TimeUnit.SECONDS.sleep(iValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("==============================================================================");
        System.out.println("[��ġ�ε�] RAW ������ ���� ���μ��� ���μ��� START : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
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
            System.out.println("[��ġ�ε�] RAW ������ ���� ���μ��� ���μ��� END : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
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
     * MH ����
     *
     * @param item
     * @param _mdate
     * @return
     * @throws ParseException
     */
    private HashMap<String, Object> adjustMH(HashMap<String, Object> item, String _mdate) throws ParseException {
        // ī�װ� ����
        item.put("CATEGORY", "MH");

        // �ε������ ����
        Date MDATE = DATE_FORMAT.parse(_mdate);
        item.put("MDATE", MDATE);
        
        /*
        if (item.get("POSID").equals("N17427L01"))
        {
        	System.out.println("����");
        }
        */
        // �ܿ� MH = ���� MH * (100 - ������)
        BigDecimal TOT_MH = (BigDecimal) item.get("TOT_MH");

        if (TOT_MH == null || TOT_MH.compareTo(BigDecimal.valueOf(0)) == 0) {
            String POSID = (String) item.get("POSID");
            String ZZGUBUN = (String) item.get("ZZGUBUN");
            if (StringUtils.equalsIgnoreCase("10", ZZGUBUN) || StringUtils.equalsIgnoreCase("13", ZZGUBUN)) {
                // ������������ ���
                String ZSPEC1 = (String) item.get("ZSPEC1");
                if (item.get("EL_ASPD") != null && item.get("EL_AFQ") != null && item.get("EL_AMAN") != null && !"".equals(item.get("EL_ASPD")) && !"".equals(item.get("EL_AFQ")) && !"".equals(item.get("EL_AMAN"))) {
                    // �ν�
                    Integer EL_AMAN = Integer.valueOf((String) item.get("EL_AMAN"));
                    // �ӵ�
                    int EL_ASPD = ((BigDecimal) item.get("EL_ASPD")).intValue();
                    // ����
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
                                addBigo("����:" + ZSPEC1 + ",�ν�:" + EL_AMAN + ",�ӵ�:" + EL_ASPD + ",��:" + EL_AFQ);
                                addBigo("TOT_MH:" + TOT_MH);
                                break;
                            }
                        }
                    }
                }
            } else if (StringUtils.equalsIgnoreCase("11", ZZGUBUN) || StringUtils.equalsIgnoreCase("14", ZZGUBUN)) {
                // �����÷������� ���
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
                // ������ũ�� ���
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

        // �Ϸ�üũ�� �Ϻ� ������
        BigDecimal PRO_R = (BigDecimal) item.get("PRO_R");
        if (PRO_R == null) {
            PRO_R = BigDecimal.valueOf(0);
        }

        if (TOT_MH == null) {
            addBigo("TOT_MH:0");
        } else {
            BigDecimal ZAN_MH = TOT_MH.multiply(BigDecimal.valueOf(1.0 - (PRO_R.floatValue() / 100)));
            item.put("ZAN_MH", ZAN_MH.setScale(4, BigDecimal.ROUND_HALF_UP));

            // ����������
            item.put("ZAN_MH", getZSPEC1(item).setScale(2, BigDecimal.ROUND_HALF_UP));

            // �Ǽ��纸��
            item.put("ZAN_MH", getKUNNR(item).setScale(2, BigDecimal.ROUND_HALF_UP));

            // ����������
            item.put("ZAN_MH", getZCITY(item).setScale(2, BigDecimal.ROUND_HALF_UP));

            // ��üȣ�� ����
            String ISREMD = (String) item.get("ISREMD");
            if (StringUtils.equalsIgnoreCase("Y", ISREMD)) {
                ZAN_MH = (BigDecimal) item.get("ZAN_MH");
                ZAN_MH = ZAN_MH.multiply(BigDecimal.valueOf(1)); // 2.8
                item.put("ZAN_MH", ZAN_MH);
                addBigo("��ü����*2:" + ZAN_MH);
            }
            
            System.out.println("��ü����*2 : "+ZAN_MH);
            addBigo("����MH : " + TOT_MH + ", �ܿ�MH : " + item.get("ZAN_MH"));

            // �ذ������� ����
            setZZCOMP(item);

            // ���ں� MH �й�
            setMHByDate(item);
        }
        return item;
    }

    /**
     * capacity ����
     *
     * @param item
     * @return
     */
    @SuppressWarnings({"DuplicatedCode", "DuplicateExpressions"})
    private HashMap<String, Object> applyCapacity(HashMap<String, Object> item) {
        // ī�װ� ����
        item.put("CATEGORY", "Capacity");

        // ������ ����
        item.put("HSTATUS", null);

        // �ε� ������
        Date MDATE = (Date) item.get("MDATE");

        // ���� ���� �ð�
        Date nextDate = MDATE;
        Date lastDate = getLastDate(nextDate);

        String POSID = (String) item.get("POSID");
        // E/S ����
        boolean isEs = StringUtils.contains(POSID, "S");

        // ���� �̹��� ����
        String TEMNO = (String) item.get("TEMNO");
        boolean noTemNo = TEMNO == null || " ".equals(TEMNO) || "".equals(TEMNO);

        // ���� ���� ����
        boolean isHQTemno = TEMNO != null && ArrayUtils.contains(NO_PARTNER_CORP, TEMNO);

        // �����ο�
        Integer BINWON = (Integer) item.get("BINWON");
        if (BINWON == null || BINWON == 0) {
            addBigo("�����ο� 0->2");
            BINWON = 2;
        } else {
            addBigo("�����ο� :" + BINWON);
        }
        // �����ο�
        Integer JINWON = (Integer) item.get("JINWON");
        if (JINWON == null) {
            JINWON = 0;
        }
        addBigo("�����ο� : -" + JINWON);
        // �������ο�
        Integer PJIWON = (Integer) item.get("PJIWON");
        if (PJIWON == null) {
            PJIWON = 0;
        }
        addBigo("�������ο� : +" + JINWON);
        // �������� => �����ο�7��
        if (isHQTemno) {
            addBigo("�������� => �����ο�7��");
            BINWON = 7;
        }
        // ���� ���� => �����ο�2��
        if (noTemNo) {
            addBigo("������� => �����ο�2��");
            BINWON = 2;
        }
        // ���ο�	= �����ο� - �����ο� + �������ο�
        int HINWON = BINWON - JINWON + PJIWON;	
        addBigo("���ο� :" + HINWON);
        if (HINWON <= 0) {
            HINWON = BINWON;
            addBigo("���ο�0=>�����ο�:" + BINWON);
        }

        // �ε������ �۾��Ϻ� ���� ���� �ð�
        BigDecimal T_MH = (BigDecimal) item.get("T_MH");
        if (T_MH == null) {
            addBigo("�۾��Ϻ� ���� ���� �ð� : 0");
            T_MH = BigDecimal.valueOf(0);
        } else {
            addBigo("�۾��Ϻ� ���� ���� �ð� : " + T_MH);
        }

        // ���� ��� �۾��ð�
        BigDecimal MH_F_D = BigDecimal.valueOf(9);
        if (isEs) {
            MH_F_D = BigDecimal.valueOf(9);
            addBigo("E/S : ����9�ð�");
        }

        // ���� ���Է�
        BigDecimal rev = BigDecimal.valueOf(0.98);
        // ������
        BigDecimal CAREAR = (BigDecimal) item.get("CAREAR");
        if (CAREAR == null) {
            CAREAR = BigDecimal.valueOf(2);
        }
        // ������2��̸��� ���
        if (CAREAR.compareTo(BigDecimal.valueOf(2)) < 0) {
            rev = BigDecimal.valueOf(0.98);
            addBigo("������2��̸� : 98%");
        }
        if (isEs) {
            rev = BigDecimal.valueOf(1);
            addBigo("E/S : ����100%");
        }

        // �ָ� ���Է�
        BigDecimal revSat = BigDecimal.valueOf(0.5);
        if (isEs) {
            revSat = BigDecimal.valueOf(0.85);
            addBigo("E/S : �ָ�85%");
        }

        // ���� ������ Capacity
        // ������ ����ȣ��(�����)
        Integer U_CNT0 = (Integer) item.get("U_CNT0");
        if (U_CNT0 == null || U_CNT0 == 0) {
            U_CNT0 = 1;
            addBigo("����ȣ���: 0=>" + U_CNT0);
        }
        addBigo("������ ����ȣ���(�����):" + U_CNT0);

        // �ջ� ���� ����
        boolean isExcludeSum = isExcludeSum(item);
        if (isExcludeSum) {
            addBigo("�ջ�����:����81.5%�̻�,����70���̻�");
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

            // ���� ���� Ȯ��
            boolean isSaturday = false;
            BigDecimal revFinal = rev;
            HashMap<String, Object> holiday = findHoliday(nextDate);
            if (holiday != null) {
                if (StringUtils.equals("SAT", (String) holiday.get("DESC"))) {
                    // ������� ���
                    isSaturday = true;
                    revFinal = revSat;
                } else {
                    // �Ͽ���, ������ ���
                    revFinal = BigDecimal.valueOf(0);
                    mh = BigDecimal.valueOf(0);
                }
            }

            // ���
            if (lastDateOfMonth.compareTo(nextDate) >= 0) {	// �����ο� X ���� ������ X MH / ������ ���� ȣ�� ��
                capacity = mh.multiply(BigDecimal.valueOf(HINWON)).multiply(revFinal).divide(BigDecimal.valueOf(U_CNT0), 2, BigDecimal.ROUND_HALF_UP);
                if (!isExcludeSum) {
                    LOD = LOD.add(capacity);
            
                }
                if (j == 0) {
                    capacity = T_MH;
                } else {	// ���ο�
                    capacity = mh.multiply(BigDecimal.valueOf(HINWON)).multiply(revFinal).divide(BigDecimal.valueOf(U_CNT0), 2, BigDecimal.ROUND_HALF_UP);
                    
                    if (!isExcludeSum) {
                        L0 = L0.add(capacity);	// ��� Capa�� N1���� ��� (N0 ����)
                    }
                }
                
            } else if (lastDateOfMonth.compareTo(nextDate) < 0 && lastDateOf1thNextMonth.compareTo(nextDate) >= 0) {
                // �Ϳ�
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
                // ����
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
                // ������
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
                // ��������
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
     * ������ MH ����
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
                addBigo("�������� : " + zpstw1707List.get(i).getREV());
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
     * ����뵵, �����, ����纰 MH ����
     *
     * @param item
     * @return
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    private BigDecimal getKUNNR(HashMap<String, Object> item) {
        // ����뵵
        String WWBLD = (String) item.get("WWBLD");
        BigDecimal value = (BigDecimal) item.get("ZAN_MH");
        List<ZPSTW1708> zpstw1708List = getAllZPSTW1708();
        for (int i = 0; i < zpstw1708List.size(); i++) {
            if (WWBLD != null && WWBLD.equals(zpstw1708List.get(i).getWWBLD())) {
                addBigo("����뵵MH���� : " + zpstw1708List.get(i).getREV());
                return value.multiply(BigDecimal.valueOf(1.0).add(zpstw1708List.get(i).getREV()));
            }
        }
        // �����
        String ENFOR = (String) item.get("ENFOR");
        List<ZPSTW1709> zpstw1709List = getAllZPSTW1709();
        for (int i = 0; i < zpstw1709List.size(); i++) {
            if ("like".equalsIgnoreCase(zpstw1709List.get(i).getCRT())) {
                if (ENFOR != null && ENFOR.contains(zpstw1709List.get(i).getENFOR())) {
                    addBigo("�����MH���� : " + zpstw1709List.get(i).getREV());
                    return value.multiply(BigDecimal.valueOf(1.0).add(zpstw1709List.get(i).getREV()));
                }
            }
        }
        // �����(�ð���)
        String KUNNR = (String) item.get("KUNNR");
        List<ZPSTW1710> zpstw1710List = getAllZPSTW1710();
        for (int i = 0; i < zpstw1710List.size(); i++) {
            if (KUNNR != null && KUNNR.equals(zpstw1710List.get(i).getKUNNR())) {
                addBigo("�����MH���� : " + zpstw1710List.get(i).getREV());
                return value.multiply(BigDecimal.valueOf(1.0).add(zpstw1710List.get(i).getREV()));
            }
        }
        return value;
    }

    /**
     * ������ MH ����
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
                addBigo("������MH���� : " + zpstw1711List.get(i).getREV().add(BigDecimal.valueOf(1.0)));
                break;
            }
        }
        return value;
    }

    /**
     * �ذ������� ����
     *
     * @param item
     * @throws ParseException
     */
    private void setZZCOMP(HashMap<String, Object> item) throws ParseException {
        // �ε������
        Date mdate = (Date) item.get("MDATE");
        // �ذ����ο���
        String STATE = (String) item.get("STATE");
        // �ذ�������
        Date ZZCOMP2 = DATE_FORMAT.parse((String) item.get("ZZCOMP2"));
        // QC�˻���
        String PAPRDT = (String) item.get("PAPRDT");
        // Ȯ���˻��û��
        String _REQ_DATE2 = (String) item.get("REQ_DATE2");

        // �ذ������� ���� ���
        Date ZZCOMP;
        Calendar cal = Calendar.getInstance();
        if (StringUtils.isEmpty(PAPRDT) || StringUtils.equals(PAPRDT, "00000000")) {
            // Qc�˻��� ���� ���
            addBigo("Qc�˻��� ����");

            if (StringUtils.equals(_REQ_DATE2, "00000000") || StringUtils.isEmpty(_REQ_DATE2)) {
                ZZCOMP = ZZCOMP2;
                cal.setTime(ZZCOMP);

                addBigo("Ȯ���˻��û�� ����");
            } else {
                ZZCOMP = DATE_FORMAT.parse(_REQ_DATE2);
                cal.setTime(ZZCOMP);
                cal.add(Calendar.DATE, 10);

                addBigo("Ȯ���˻��û�� : " + _REQ_DATE2 + ", +10");
            }
            // �ذ�����Ȯ�� N => +10��
            if (StringUtils.equalsIgnoreCase(STATE, "N")) {
                cal.add(Calendar.DATE, 10);
                addBigo("�ذ�����Ȯ�� N => +10��");
                // �ذ�14�������� �ذ����� N�̸� => +10��
                if (diffOfDate(mdate, ZZCOMP) <= 14) {
                    cal.add(Calendar.DATE, 10);
                    addBigo("�ذ�14�������� �ذ����� N�̸� => +10��");
                }
            }
            // ZZCOMP
            if (cal.getTime().compareTo(ZZCOMP2) > 0) {
                ZZCOMP = cal.getTime();
            } else {
                ZZCOMP = ZZCOMP2;
            }

        } else {
            // Qc�˻��� �ִ� ���
            addBigo("Qc�˻��� ����");
            if (PAPRDT != null && !"00000000".equals(PAPRDT)) {
                ZZCOMP = DATE_FORMAT.parse(PAPRDT);
                cal.setTime(ZZCOMP);
            }
            // QC�˻���
            String DSKURZTEXT = (String) item.get("DSKURZTEXT");
            if (StringUtils.equals(DSKURZTEXT, "�հ�") || StringUtils.equals(DSKURZTEXT, "����")) {
                cal.add(Calendar.DATE, 3);
                addBigo("�հ� +3");
            } else if (StringUtils.equals(DSKURZTEXT, "���Ǻ��հ�")) {
                cal.add(Calendar.DATE, 10);
                addBigo("���Ǻ��հ� +10");
            } else if (StringUtils.equals(DSKURZTEXT, "���հ�")) {
                cal.add(Calendar.DATE, 20);
                addBigo("���հ� +20");
            }
            // ZZCOMP
            if (cal.getTime().compareTo(ZZCOMP2) > 0) {
                ZZCOMP = cal.getTime();
            } else {
                addBigo("�ذ����� ������� < �ذ������� => �ذ�������");
                ZZCOMP = ZZCOMP2;
            }
        }
        item.put("ZZCOMP", ZZCOMP);
        addBigo("�������ذ�������" + DATE_FORMAT.format(ZZCOMP));
    }

    /**
     * ���ں� MH �й�
     *
     * @param item
     * @throws ParseException
     */
    @SuppressWarnings("DuplicatedCode")
    private void setMHByDate(HashMap<String, Object> item) throws ParseException {
    	/*
    	if(item.get("POSID").equals("N17121L01"))
    	{
    		System.out.println("����");
    	}*/
        // �ε������
        Date MDATE = (Date) item.get("MDATE");
        // ����������
        Date ZZSHIP1 = DATE_FORMAT.parse((String) item.get("ZZSHIP1"));

        Date nextDate = MDATE;
        Date lastDate = getLastDate(MDATE);

        Date ZZCOMP = (Date) item.get("ZZCOMP");
        if (ZZCOMP.compareTo(lastDate) <= 0) {
            lastDate = ZZCOMP;
        }

        // ��ġ�Ⱓ = �ذ������� - �ε������
        int mDiff = diffOfDate(MDATE, ZZCOMP);
        item.put("mDiff", mDiff);

        // ���纰 MH �й� ���� ������ ���
        ZPSTW1712 zpstw1712 = findZPSTW1712(item);
        if (zpstw1712 != null) {
            addBigo(zpstw1712.toString());
        }

        // ���ں� ������ ȣ�� MH ���� ������ ���
        ZPSTW1706 zpstw1706 = findZPSTW1706(item);
        if (zpstw1706 != null) {
            if (MDATE.compareTo(ZZSHIP1) < 0) {
                addBigo(zpstw1706.toString());
            }
        }

        // ���ں� ���� ȣ�� MH ���� ������ ���
        ZPSTW1704 zpstw1704 = findZPSTW1704(item);
        if (zpstw1704 != null) {
            if (MDATE.compareTo(ZZSHIP1) >= 0) {
                addBigo(zpstw1704.toString());
            }
        }

        // �ܿ�MH
        BigDecimal ZAN_MH = (BigDecimal) item.get("ZAN_MH");

        // �ջ� ���� ����
        boolean isExcludeSum = isExcludeSum(item);
        if (isExcludeSum) {
            addBigo("�ջ�����:����81.5%�̻�,����70���̻�");
        }

        BigDecimal L0 = BigDecimal.valueOf(0);
        BigDecimal L1 = BigDecimal.valueOf(0);
        BigDecimal L2 = BigDecimal.valueOf(0);
        BigDecimal L3 = BigDecimal.valueOf(0);
        BigDecimal L4 = BigDecimal.valueOf(0);

        int i = 0;
        //���ں��� �ɰ��� ����.
        while (nextDate.compareTo(lastDate) <= 0) {	// �ʱ� �ε� ������ +1, +2, +3 ...
            nextDate = getNextDate(MDATE, i);

            BigDecimal mhByDate;

            // ���� ���� Ȯ��
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
                        // ���纰 �ε� �й� ������ �ش��ϴ� ���
                    	System.out.println("���纰 �ε� �й� ����");
                        mhByDate = getMHByACTSS(MDATE, nextDate, ZZCOMP, i, ZAN_MH, zpstw1712);
                    } else {
                        // ���纰 �ε� �й� ������ �ش����� �ʴ� ���
                        if (MDATE.compareTo(ZZSHIP1) < 0) {
                            // ������ȣ��
                        	System.out.println("������ȣ��");
                            mhByDate = getMHByDateNotOnProcess(MDATE, nextDate, ZZCOMP, diffOfDate(ZZSHIP1, nextDate), ZAN_MH, zpstw1706);
                        } else {
                        	System.out.println("����ȣ��");
                            // ����ȣ��
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
     * �ջ� ����
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
     * ����ȣ�� MH �й�
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
        // ���� ���� ���
        BigDecimal rev;
        if (zpstw1704 == null) {
            // ���ش��� ��� ���� ����
            rev = BigDecimal.valueOf(1);
        } else {
            if (nth >= 91) {
                // 91�� ~
                rev = zpstw1704.getR4();
            } else if (nth >= 61) {
                // 61�� ~ 90��
                rev = zpstw1704.getR3();
            } else if (nth >= 31) {
                // 31�� ~ 60��
                rev = zpstw1704.getR2();
            } else {
                // ~ 30��
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
     * ������ȣ�� MH �й�
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
        // ���� ���� ���
        BigDecimal rev;
        if (zpstw1706 == null) {
            // ���ش��� ��� ���� ����
            rev = BigDecimal.valueOf(1);
        } else {
            if (nth >= 91) {
                // 91�� ~
                rev = zpstw1706.getR4();
            } else if (nth >= 61) {
                // 61�� ~ 90��
                rev = zpstw1706.getR3();
            } else if (nth >= 31) {
                // 31�� ~ 60��
                rev = zpstw1706.getR2();
            } else {
                // ~ 30��
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
     * ���纰 MH �й� ����
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
        // ���� ���� ���
        BigDecimal rev;

        if (nth >= 91) {
            // 91�� ~
            rev = zpstw1712.getR4();
        } else if (nth >= 61) {
            // 61�� ~ 90��
            rev = zpstw1712.getR3();
        } else if (nth >= 31) {
            // 31�� ~ 60��
            rev = zpstw1712.getR2();
        } else {
            // ~ 30��
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
     * ��¥�� ���ϱ�
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

        // ������
        int div = 30;
        // ��
        int share = mDiff / 30;
        // ������
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
     * ���ϼ� ���ϱ�
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
     * ����ϼ� ���ϱ�
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

        // ����
        String ACTSS = (String) item.get("ACTSS");

        // ���� / ������ ����
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
        // �ε������ ~ �ذ�������
        int mDiff = (Integer) item.get("mDiff");

        // ��ü����
        String ISREMD = (String) item.get("ISREMD");
        if (ISREMD != null) {
            ISREMD = StringUtils.trim(ISREMD);
        }
        if (StringUtils.isEmpty(ISREMD)) {
            ISREMD = "N";
        }

        // ���
        String ZSPEC2 = (String) item.get("ZSPEC2");
        if (ZSPEC2 != null) {
            ZSPEC2 = StringUtils.trim(ZSPEC2);
        }
        if (StringUtils.isNotEmpty(ZSPEC2)) {
            ZSPEC2 = ZSPEC2.substring(0, 2);
        }

        // �ӵ�
        int EL_ASPD = item.get("EL_ASPD") == null ? 0 : ((BigDecimal) item.get("EL_ASPD")).intValue();

        // ����
        int EL_AFQ = (Integer) item.get("EL_AFQ");

        // �Ѵ��
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
        // �ε������ ~ �ذ�������
        int mDiff = (Integer) item.get("mDiff");

        // ��ü����
        String ISREMD = (String) item.get("ISREMD");
        if (ISREMD != null) {
            ISREMD = StringUtils.trim(ISREMD);
        }
        if (StringUtils.isEmpty(ISREMD)) {
            ISREMD = "N";
        }

        // ���
        String ZSPEC2 = (String) item.get("ZSPEC2");
        if (ZSPEC2 != null) {
            ZSPEC2 = StringUtils.trim(ZSPEC2);
        }
        if (StringUtils.isNotEmpty(ZSPEC2)) {
            ZSPEC2 = ZSPEC2.substring(0, 2);
        }

        // �ӵ�
        int EL_ASPD = item.get("EL_ASPD") == null ? 0 : ((BigDecimal) item.get("EL_ASPD")).intValue();

        // ����
        int EL_AFQ = (Integer) item.get("EL_AFQ");

        // �Ѵ��
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
        ResourceFactory resourceFactory = (ResourceFactory) ServiceManagerFactory.getServiceObject("DB2_HEQ"); //�ƹ��ų� ����ص� ����
        if (resourceFactory == null) {
            return null;
        }
        TransactionResource res = resourceFactory.makeResource(mdt); //mdt�� jdbc/hed, jdbc/heq, jdbc/hep, jdbc/cs �߿� ������� ��������� �߿�
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
