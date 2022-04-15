package com.helco.xf.cs01.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tobesoft.platform.data.Dataset;
import com.helco.xf.comm.Utillity;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

public class CS0108002B_ServiceImpl extends AbstractBusinessService implements CS0108002B_Service {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static Logger logger;

    /**
     * ���� BOM ���ռ� üũ ����
     * @param BusinessContext ctx, Dataset ds_cond, Dataset ds_list2, Dataset ds_match_rslt
     * @throws Exception
     */
    @Override
    public void specMatchCheck(
                                BusinessContext ctx
                               ,Dataset ds_cond
                               ,Dataset ds_list_match
                               ,Dataset ds_match_rslt
                               ) throws Exception {
        logger = ServiceManagerFactory.getLogger();

        // ���� ��� ����Ʈ�� Ư�� �ڵ带 hashMap ������ ��´�.
        // ���ռ� üũ �޼��� ���� �� üũ ������ ���� �޼����� ������ �ʴ´�.
        // �߻��� ������ ���ؼ� �� ���޿� �����͙V�� ���� �޽����� ����.
        HashMap<String, String> inputKndValue = new HashMap<String, String>();
        HashMap<String, Integer> inputIndex   = new HashMap<String, Integer>();
        // �����ȣ�� ���� ���� inputKndValue�� �Է��� �ش�.
        String posid = ds_cond.getColumnAsString(0, "POSID");
        if( posid != null || !"".equals(posid) ) {
            inputKndValue.put("�����ȣ", ds_cond.getColumnAsString(0, "POSID"));
        }

        for (int i=0; i< ds_list_match.getRowCount(); i++) {
            String value = ds_list_match.getColumnAsString(i, "KNM");
            if (value == null) {
                String NUM = ds_list_match.getColumnAsString(i, "NUM");
                if( "Y".equals(NUM)) {
                    // ������ ���� ���� ��� 0������ ó���Ѵ�.
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                } else {
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                }
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            } else if( "".equals(value) ) {
                String NUM = ds_list_match.getColumnAsString(i, "NUM");
                if( "Y".equals(NUM)) {
                    // ������ ���� ���� ��� 0������ ó���Ѵ�.
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                } else {
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                }
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            } else {
                inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), value);
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            }
        }

        // ���� ����� �ش� �Է°��� ���� ��쿡 ������ �����Ͽ� �޽����� ǥ���Ѵ�.
        try {
            // db ������ ���� ���� ���� ������ ���� ������ �����Ѵ�.
            HashMap<String, MatchDutyCalcu> calcuMap = specCalcuMap(ctx);

            // DB ������ ���� üũ �����͸� �����Ѵ�.
            List<Match> matchList = specMatchList(ctx, ds_cond);

            // ���ռ� üũ ������ �����Ѵ�.
            specMatchExe(ds_cond
                       ,ds_list_match
                       ,ds_match_rslt
                       ,inputKndValue
                       ,inputIndex
                       ,matchList
                       ,calcuMap
                       );
        } catch(Exception e ) {
            e.printStackTrace();
            throw new SpecDutyException(e.getLocalizedMessage());
        }
    }

    /**
     * ���ռ� üũ ����Ʈ ��ȸ
     * @param ctx
     * @throws Exception
     */
    private List<Match> specMatchList(BusinessContext ctx, Dataset ds_cond) throws Exception {
        List<Match> matchList = new ArrayList<Match>();
        String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
        DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
        DatasetSqlRequest sqlReq = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108002C_S1");

        sqlReq.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
        sqlReq.addParamObject("ds_cond", ds_cond);

        Dataset ds_spec_list = (Dataset)executor.query(sqlReq).getResultObject();

        String BLOCKNOSEQ;
        HashMap<String, Match> matchMap = new HashMap<String, Match>();
        List<String> matchKeyList = new ArrayList<String>();
        for (int i=0; i< ds_spec_list.getRowCount(); i++) {
            BLOCKNOSEQ = ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ");

            if( matchMap.containsKey(BLOCKNOSEQ) ) {
                // Ű�� �ִ� ���
                Match match = (Match)matchMap.get(BLOCKNOSEQ);
                MatchDutyD matchDutyD = new MatchDutyD();
                matchDutyD.setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                matchDutyD.setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                matchDutyD.setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                matchDutyD.setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                matchDutyD.setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                matchDutyD.setZORDER(ds_spec_list.getColumnAsString(i, "ZORDER"));
                matchDutyD.setSPEC(ds_spec_list.getColumnAsString(i, "SPEC"));
                matchDutyD.setCALCULAT(ds_spec_list.getColumnAsString(i, "CALCULAT"));
                matchDutyD.setCALCULATCK(ds_spec_list.getColumnAsString(i, "CALCULATCK"));
                matchDutyD.setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));
                match.getMatchDutyList().add(matchDutyD);

            } else {
                // Ű���� ���� ���
                Match match = new Match();
                match.getMatchDuty().setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                match.getMatchDuty().setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                match.getMatchDuty().setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                match.getMatchDuty().setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                match.getMatchDuty().setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                match.getMatchDuty().setMESSAGE(ds_spec_list.getColumnAsString(i, "MESSAGE"));
                match.getMatchDuty().setTRANS(ds_spec_list.getColumnAsString(i, "TRANS"));
                match.getMatchDuty().setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));

                MatchDutyD matchDutyD = new MatchDutyD();
                matchDutyD.setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                matchDutyD.setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                matchDutyD.setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                matchDutyD.setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                matchDutyD.setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                matchDutyD.setZORDER(ds_spec_list.getColumnAsString(i, "ZORDER"));
                matchDutyD.setSPEC(ds_spec_list.getColumnAsString(i, "SPEC"));
                matchDutyD.setCALCULAT(ds_spec_list.getColumnAsString(i, "CALCULAT"));
                matchDutyD.setCALCULATCK(ds_spec_list.getColumnAsString(i, "CALCULATCK"));
                matchDutyD.setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));
                match.getMatchDutyList().add(matchDutyD);

                matchMap.put(BLOCKNOSEQ, match);
                matchKeyList.add(BLOCKNOSEQ);
            }
        }

        // �ش� ������ ����Ʈ ������ ġȯ�Ͽ� ����
        for(int j=0; j < matchKeyList.size(); j++ ) {
            matchList.add(matchMap.get(matchKeyList.get(j)));
        }

        return matchList;
    }

    /**
     * ���ռ� üũ ����
     * @param Dataset ds_list2 Dataset ds_match_rslt HashMap<String, String> inputKndValue HashMap<String, Integer> inputIndex List<Match> matchList HashMap<String, MatchDutyCalcu> calcuMap
     * @throws Exception
     */
    private void specMatchExe(Dataset ds_cond
                               ,Dataset ds_list2
                               ,Dataset ds_match_rslt
                               ,HashMap<String, String> inputKndValue
                               ,HashMap<String, Integer> inputIndex
                               ,List<Match> matchList
                               ,HashMap<String, MatchDutyCalcu> calcuMap
                               ) throws Exception {
        int errIndex = 1;
        // üũ ������ ������� �ִ� ��쿡 �켱 ���� �����͸� ������ �� �Է°��� �����Ѵ�.
        // ���� SPECT : AAAA = {EL_AFQ} * 12
        // �ش� ���庰 ���� ���� ��� ���� ��� �ؽ���
        // ����� ������ ġȯ�� ���� spec �ڵ带 ��´�.
        HashMap<String, String> calcuMapSpec = new HashMap<String,String>();

        // ���ռ� ���࿩�� ��� �V��
        ds_cond.setColumn(0, "MATCHFLAG", "Y");
        ds_cond.setColumn(0, "TRNSYSNO", "Y");

        for(int i=0; i < matchList.size(); i++) {
            Match match = matchList.get(i);
            MatchDuty matchDuty = match.getMatchDuty();
            List<MatchDutyD> matchDutyDList = match.getMatchDutyList();

            boolean bRslt = true;
            for(int j=0; j < matchDutyDList.size(); j++) {
                MatchDutyD matchDutyD = matchDutyDList.get(j);
                // üũ ���� ����
                // ���� out ������ ��쿡�� ����ؼ� inputKndValue�� �Է��� �ش�
                // ���� �Է� ���� ������꿡 ���ؼ� ���� ����Ѵ�.
                // �Ķ����: ���庯ȯ�� �Է°�, ���ǵ� �����, ��ü �Է°�(��ȯ ó���� ���ؼ�)
                try {

                    // ������İ���� ó���Ѵ�.
                    if( "Y".equals(matchDutyD.getCALCULATCK())) {
                        // ����ó�� ��� ������ �� ����ó���� �����Ѵ�.
                        if( !inputKndValue.containsKey(matchDutyD.getSPEC()) ) {
                            // ���ջ��������� �����Ѵ�.
                            // ����� ����� inputKndValue �ؽ��ʿ� ��´�.
                            specCalcuExe(matchDuty
                                        ,matchDutyD
                                        ,inputKndValue
                                        ,calcuMap
                                        ,calcuMapSpec
                                        );
                        }
                    }

                    // üũ ������ ���̸� ���� �޼����� �����Ѵ�.
//                    logger.debug("���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư���ڵ�:"+matchDutyD.getSPEC()+"\n");
//                    if( inputKndValue.containsKey(matchDutyD.getSPEC()) ) {
//                        logger.debug("���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư����:"+inputKndValue.get(matchDutyD.getSPEC())+"\n");
//                    } else {
//                        logger.debug("���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư����:���� ���� ó�� ��� ����");
//                    }
//                    logger.debug("���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����:"+matchDutyD.getCALCULAT()+"\n");

                    if( MatchDutyOpr.serviceCompare(inputKndValue.get(matchDutyD.getSPEC()), matchDutyD.getCALCULAT(), inputKndValue) ) {
                        bRslt = true;
//                        logger.debug("���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ���:"+bRslt);
                    } else {
                        bRslt = false;
//                        logger.debug("���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ���:"+bRslt);
                        break;
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    String error = "���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư���ڵ�:"+matchDutyD.getSPEC()+"\n";
                    error = error +"���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư����:"+inputKndValue.get(matchDutyD.getSPEC())+"\n";
                    error = error +"���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����:"+matchDutyD.getCALCULAT()+"\n";
                    error = error +"���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����޼���:"+e.getLocalizedMessage();
                    throw new SpecDutyException(error);

/*
                    String error = "���� ���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư���ڵ�:"+matchDutyD.getSPEC()+"\n";
                    error = error +"���� ���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư����:"+inputKndValue.get(matchDutyD.getSPEC())+"\n";
                    error = error +"���� ���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����:"+matchDutyD.getCALCULAT()+"\n";
                    error = error +"���� ���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����޼���:"+e.getLocalizedMessage();
                    logger.debug(error);
*/
                }
            }

            //logger.debug("���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDuty.getBLOCKNOSEQ()+"] ���� ������:"+bRslt);
            if( bRslt ) {
                // �߸� �Էµ� �ڷ� �̷̹� �ش� ������ �V ������ ���� ���� �޽����� ����Ѵ�.
                String sendYsno = "���۰���";
                for(int z=0; z < matchDutyDList.size(); z++) {
                    MatchDutyD matchDutyD = matchDutyDList.get(z);

                    int rows = ds_match_rslt.appendRow();
                    ds_match_rslt.setColumn(rows, "BLOCKNO", matchDutyD.getBLOCKNO());
                    ds_match_rslt.setColumn(rows, "BLOCKNOSEQ", matchDutyD.getBLOCKNOSEQ());
                    ds_match_rslt.setColumn(rows, "TRANS", matchDuty.getTRANS());

                    ds_match_rslt.setColumn(rows, "SEQN", new Integer(errIndex).toString());
                    if( !"Y".equals(matchDuty.getTRANS())) {
                        sendYsno = "���ۺҰ�";
                        // ���ռ� ���ۺҰ� ���� �V�� -> ȭ�鿡�� ���� �÷��װ�
                        ds_cond.setColumn(0, "TRNSYSNO", "N");
                    }
                    ds_match_rslt.setColumn(rows, "SENDYSNO", sendYsno);
                    ds_match_rslt.setColumn(rows, "ERR_MESSAGE", matchDuty.getMESSAGE());
                    // CNM
                    if( inputIndex.containsKey(matchDutyD.getSPEC())) {
                        ds_match_rslt.setColumn(rows, "CNM", ds_list2.getColumnAsString(inputIndex.get(matchDutyD.getSPEC()), "CNM"));
                    } else {
                        ds_match_rslt.setColumn(rows, "CNM", matchDutyD.getSPEC());
                    }

                    ds_match_rslt.setColumn(rows, "COD", matchDutyD.getSPEC());
                    ds_match_rslt.setColumn(rows, "CALCULAT", matchDutyD.getCALCULAT());

                    if( "Y".equals(matchDutyD.getCALCULATCK())) {
                        if( calcuMapSpec.containsKey(matchDutyD.getSPEC())) {
                            ds_match_rslt.setColumn(rows, "CALCOD", calcuMapSpec.get(matchDutyD.getSPEC()));
                        }

                        // ����ó�� ����� �����ϸ� ȭ�鿡 �α׷� ǥ���Ѵ�.
                        if( inputKndValue.containsKey(matchDutyD.getSPEC())) {
                            ds_match_rslt.setColumn(rows, "CALRESULT", inputKndValue.get(matchDutyD.getSPEC()));
                        }
                    }

//                    String error = "���ռ� ���� ["+matchDutyD.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư���ڵ�:"+matchDutyD.getSPEC()+"\n";
//                    error = error +"���ռ� ���� ["+matchDutyD.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư����:"+inputKndValue.get(matchDutyD.getSPEC())+"\n";
//                    error = error +"���ռ� ���� ["+matchDutyD.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����:"+matchDutyD.getCALCULAT()+"\n";
                    if( "Y".equals(matchDutyD.getCALCULATCK())) {
                        // ����ó�� ����� �����ϸ� ȭ�鿡 �α׷� ǥ���Ѵ�.
                        if( inputKndValue.containsKey(matchDutyD.getSPEC())) {
//                            error = error +"���ռ� ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ������:"+inputKndValue.get(matchDutyD.getSPEC())+"\n";
                            ds_match_rslt.setColumn(rows, "CALRESULT", inputKndValue.get(matchDutyD.getSPEC()));
                        }
                    }
//                    error = error +"���ռ� ���� ["+matchDutyD.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �޼���:"+matchDuty.getMESSAGE()+"\n";
//                    logger.debug(error);
                }
                errIndex = errIndex + 1;
            }
        }
        //logger.debug("�Է� ���� �޼��� Dataset:"+ds_match_rslt);
    }

    /**
     * ���� BOM Duty ����
     * @param BusinessContext ctx, Dataset ds_cond, Dataset ds_list2, Dataset ds_match_rslt
     * @throws Exception
     */
    @Override
    public void specDutyCheck(
                                BusinessContext ctx
                               ,Dataset ds_cond
                               ,Dataset ds_list_match
                               ,Dataset ds_duty_rslt
                               ) throws Exception {
        logger = ServiceManagerFactory.getLogger();

        // ���� ��� ����Ʈ�� Ư�� �ڵ带 hashMap ������ ��´�.
        // ���ռ� üũ �޼��� ���� �� üũ ������ ���� �޼����� ������ �ʴ´�.
        // �߻��� ������ ���ؼ� �� ���޿� �����͙V�� ���� �޽����� ����.
        HashMap<String, String> inputKndValue = new HashMap<String, String>();
        HashMap<String, String> orgKndValue = new HashMap<String, String>();
        HashMap<String, Integer> inputIndex   = new HashMap<String, Integer>();
        // �����ȣ�� ���� ���� inputKndValue�� �Է��� �ش�.
        String posid = ds_cond.getColumnAsString(0, "POSID");
        if( posid != null || !"".equals(posid) ) {
            inputKndValue.put("�����ȣ", ds_cond.getColumnAsString(0, "POSID"));
        }

        for (int i=0; i< ds_list_match.getRowCount(); i++) {
            String value = ds_list_match.getColumnAsString(i, "KNM");
            if (value == null) {
                String NUM = ds_list_match.getColumnAsString(i, "NUM");
                if( "Y".equals(NUM)) {
                    // ������ ���� ���� ��� 0������ ó���Ѵ�.
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                    orgKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                } else {
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                    orgKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                }
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            } else if( "".equals(value) ) {
                String NUM = ds_list_match.getColumnAsString(i, "NUM");
                if( "Y".equals(NUM)) {
                    // ������ ���� ���� ��� 0������ ó���Ѵ�.
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                    orgKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "0");
                } else {
                    inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                    orgKndValue.put(ds_list_match.getColumnAsString(i, "COD"), "");
                }
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            } else {
                inputKndValue.put(ds_list_match.getColumnAsString(i, "COD"), value);
                inputIndex.put(ds_list_match.getColumnAsString(i, "COD"), i);
            }
        }

        // spec type ���� -> ȭ�鿡�� ����
        //ds_cond.setColumn(0, "PARAMTYPE", "EL");
        //ds_cond.setColumn(0, "PARAMCTYPE", "M");

        // ���� ����� �ش� �Է°��� ���� ��쿡 ������ �����Ͽ� �޽����� ǥ���Ѵ�.
        try {
            // db ������ ���� ���� ���� ������ ���� ������ �����Ѵ�.
            HashMap<String, MatchDutyCalcu> calcuMap = specCalcuMap(ctx);

            // ���ռ� Duty ������ �����Ѵ�.
            // F001 ������Ʈ ���� AN �ű� �Ǵ� ���𵨸� ���翡 ���ؼ��� Duty�� �����Ѵ�.
            List<Match> dutyList = specDutyList(ctx, ds_cond);
            specDutyExe(ds_cond
                       ,ds_list_match
                       ,ds_duty_rslt
                       ,inputKndValue
                       ,inputIndex
                       ,dutyList
                       ,calcuMap
                       ,orgKndValue
                       );
        } catch(Exception e ) {
            e.printStackTrace();
            throw new SpecDutyException(e.getLocalizedMessage());
        }
    }

    /**
     * ���ռ� DUTY ����Ʈ ��ȸ
     * @param ctx
     * @throws Exception
     */
    private List<Match> specDutyList(BusinessContext ctx, Dataset ds_cond) throws Exception {
        List<Match> dutyList = new ArrayList<Match>();
        String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
        DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
        // ���ռ� Duty ����Ʈ�� ��ȸ�Ѵ�.
        DatasetSqlRequest sqlReq = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108002C_S3");

        sqlReq.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
        sqlReq.addParamObject("ds_cond", ds_cond);

        Dataset ds_spec_list = (Dataset)executor.query(sqlReq).getResultObject();

        String BLOCKNOSEQ;
        HashMap<String, Match> matchMap = new HashMap<String, Match>();
        List<String> matchKeyList = new ArrayList<String>();
        for (int i=0; i< ds_spec_list.getRowCount(); i++) {
            BLOCKNOSEQ = ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ");

            if( matchMap.containsKey(BLOCKNOSEQ) ) {
                // Ű�� �ִ� ���
                Match match = (Match)matchMap.get(BLOCKNOSEQ);
                MatchDutyD matchDutyD = new MatchDutyD();
                matchDutyD.setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                matchDutyD.setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                matchDutyD.setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                matchDutyD.setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                matchDutyD.setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                matchDutyD.setZORDER(ds_spec_list.getColumnAsString(i, "ZORDER"));
                matchDutyD.setSPEC(ds_spec_list.getColumnAsString(i, "SPEC"));
                matchDutyD.setCALCULAT(ds_spec_list.getColumnAsString(i, "CALCULAT"));
                matchDutyD.setCALCULATCK(ds_spec_list.getColumnAsString(i, "CALCULATCK"));
                matchDutyD.setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));
                match.getMatchDutyList().add(matchDutyD);

            } else {
                // Ű���� ���� ���
                Match match = new Match();
                match.getMatchDuty().setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                match.getMatchDuty().setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                match.getMatchDuty().setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                match.getMatchDuty().setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                match.getMatchDuty().setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                match.getMatchDuty().setMESSAGE(ds_spec_list.getColumnAsString(i, "MESSAGE"));
                match.getMatchDuty().setTRANS(ds_spec_list.getColumnAsString(i, "TRANS"));
                match.getMatchDuty().setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));

                MatchDutyD matchDutyD = new MatchDutyD();
                matchDutyD.setMANDT(ds_spec_list.getColumnAsString(i, "MANDT"));
                matchDutyD.setCTYPE(ds_spec_list.getColumnAsString(i, "CTYPE"));
                matchDutyD.setTYPE(ds_spec_list.getColumnAsString(i, "TYPE"));
                matchDutyD.setBLOCKNO(ds_spec_list.getColumnAsString(i, "BLOCKNO"));
                matchDutyD.setBLOCKNOSEQ(ds_spec_list.getColumnAsString(i, "BLOCKNOSEQ"));
                matchDutyD.setZORDER(ds_spec_list.getColumnAsString(i, "ZORDER"));
                matchDutyD.setSPEC(ds_spec_list.getColumnAsString(i, "SPEC"));
                matchDutyD.setCALCULAT(ds_spec_list.getColumnAsString(i, "CALCULAT"));
                matchDutyD.setCALCULATCK(ds_spec_list.getColumnAsString(i, "CALCULATCK"));
                matchDutyD.setDFLAG(ds_spec_list.getColumnAsString(i, "DFLAG"));
                match.getMatchDutyList().add(matchDutyD);

                matchMap.put(BLOCKNOSEQ, match);
                matchKeyList.add(BLOCKNOSEQ);
            }
        }

        // �ش� ������ ����Ʈ ������ ġȯ�Ͽ� ����
        for(int j=0; j < matchKeyList.size(); j++ ) {
            dutyList.add(matchMap.get(matchKeyList.get(j)));
        }

        return dutyList;
    }

    /**
     * ���ռ� Duty ����
     * @param Dataset ds_list2 Dataset ds_match_rslt HashMap<String, String> inputKndValue HashMap<String, Integer> inputIndex List<Match> matchList HashMap<String, MatchDutyCalcu> calcuMap
     * @throws Exception
     */
    private void specDutyExe(Dataset ds_cond
                              ,Dataset ds_list2
                              ,Dataset ds_duty_rslt
                              ,HashMap<String, String> inputKndValue
                              ,HashMap<String, Integer> inputIndex
                              ,List<Match> dutyList
                              ,HashMap<String, MatchDutyCalcu> calcuMap
                              ,HashMap<String, String> orgKndValue
                               ) throws Exception {
        // üũ ������ ������� �ִ� ��쿡 �켱 ���� �����͸� ������ �� �Է°��� �����Ѵ�.
        // ���� SPECT : AAAA = {EL_AFQ} * 12
        // �ش� ���庰 ���� ���� ��� ���� ��� �ؽ���
        // ����� ������ ġȯ�� ���� spec �ڵ带 ��´�.
        HashMap<String, String> calcuMapSpec = new HashMap<String,String>();
        for(int i=0; i < dutyList.size(); i++) {
            Match match = dutyList.get(i);
            MatchDuty matchDuty = match.getMatchDuty();
            List<MatchDutyD> matchDutyDList = match.getMatchDutyList();

            boolean bRslt = true;
            for(int j=0; j < matchDutyDList.size(); j++) {
                MatchDutyD matchDutyD = matchDutyDList.get(j);
                // üũ ���� ����
                // ���� out ������ ��쿡�� ����ؼ� inputKndValue�� �Է��� �ش�
                // ���� �Է� ���� ������꿡 ���ؼ� ���� ����Ѵ�.
                // �Ķ����: ���庯ȯ�� �Է°�, ���ǵ� �����, ��ü �Է°�(��ȯ ó���� ���ؼ�)
                try {

                    // ������İ���� ó���Ѵ�.
                    if( "Y".equals(matchDutyD.getCALCULATCK())) {
                        // ����ó�� ��� ������ �� ����ó���� �����Ѵ�.
                        if( !inputKndValue.containsKey(matchDutyD.getSPEC()) ) {
                            // ���ջ��������� �����Ѵ�.
                            // ����� ����� inputKndValue �ؽ��ʿ� ��´�.
                            specCalcuExe(matchDuty
                                        ,matchDutyD
                                        ,inputKndValue
                                        ,calcuMap
                                        ,calcuMapSpec
                                        );
                        }
                    }

                    // ���� ���ռ� üũ ������ ���Ŀ� {} �ش� ������ ��� ds_duty_rslt�� �ش� �Է°��� �ݿ��Ѵ�.
                    if( matchDutyD.getCALCULAT().indexOf("{") > -1 && matchDutyD.getCALCULAT().indexOf("}") > -1 ) {
                        if( bRslt ) {
                        	// ��갪�� �����Ͽ� �Է��Ѵ�.
                            String checkValue = "";
                            String dutyValue = "";
                            checkValue = matchDutyD.getCALCULAT().replace("{", "").replace("}", "");
                            
                            if( inputKndValue.containsKey(checkValue) ) {
                                 dutyValue = (String)inputKndValue.get(checkValue);
                            } else {
                                // duty���� ���� �Է��� ���� Ư�������� �����Ѵ�.
                                dutyValue = checkValue;
                            }

                            if( dutyValue != null ) {
                            	if( orgKndValue.containsKey(matchDutyD.getSPEC())) {
                                    String knm = orgKndValue.get(matchDutyD.getSPEC());
                                    if( !dutyValue.toString().equals("") && !dutyValue.toString().equals(knm) ) {
                                        int rows = ds_duty_rslt.appendRow();
                                        ds_duty_rslt.setColumn(rows, "COD", matchDutyD.getSPEC());
                                        ds_duty_rslt.setColumn(rows, "KNM", dutyValue);
                                        ds_duty_rslt.setColumn(rows, "DUTYMSG", matchDuty.getMESSAGE());
                                    }
                            	} else {
                            		// ���� ����Ŀ� ���ؼ� ���ǵ� ��� input ���� �߰��� �ش�.
                                    if( calcuMap.containsKey(matchDutyD.getSPEC()) ) {
                                		String fomat = (String)calcuMap.get(matchDutyD.getSPEC()).getFOMAT();
                                		if( "E".equals(fomat) ) {
                                        	inputKndValue.put(matchDutyD.getSPEC(), dutyValue);
                                		}
                                    }
                            	}
                            }
                        }
                    } else {
                        // üũ ������ ���̸� ���� �޼����� �����Ѵ�.
                        //logger.debug("Duty ���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư���ڵ�:"+matchDutyD.getSPEC()+"\n");
                        if( inputKndValue.containsKey(matchDutyD.getSPEC()) ) {
                            //logger.debug("Duty ���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư����:"+inputKndValue.get(matchDutyD.getSPEC())+"\n");
                        } else {
                            //logger.debug("Duty ���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư����:���� ���� ó�� ��� ����");
                        }
                        //logger.debug("Duty ���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����:"+matchDutyD.getCALCULAT()+"\n");

                        // DUTY ������ �Է°��� ���� ��� true�� ó���ؼ� �����Ѵ�.
                        if( matchDutyD.getCALCULAT() == null ) {
                            bRslt = true;
                        } else {
                            // duty check ���� null �̸� ������ true
                            if( matchDutyD.getCALCULAT().equals("") ) {
                                bRslt = true;
                            } else {
                                if( MatchDutyOpr.serviceCompare(inputKndValue.get(matchDutyD.getSPEC()), matchDutyD.getCALCULAT(), inputKndValue) ) {
                                    bRslt = true;
//                                    logger.debug("Duty ���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ���:"+bRslt);
                                } else {
                                    bRslt = false;
//                                    logger.debug("Duty ���ǿ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ���:"+bRslt);
                                    break;
                                }
                            }
                        }
                    }
                } catch(Exception e) {
                    String error = "Duty ���ǿ���� ����["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư���ڵ�:"+matchDutyD.getSPEC()+"\n";
                    error = error +"Duty ���ǿ���� ����["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư����:"+inputKndValue.get(matchDutyD.getSPEC())+"\n";
                    error = error +"Duty ���ǿ���� ����["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����:"+matchDutyD.getCALCULAT()+"\n";
                    error = error +"Duty ���ǿ���� ����["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����޼���:"+e.getLocalizedMessage();
                    e.printStackTrace();
                    throw new SpecDutyException(error);
                }
            }
        }
    }

    /**
     * ���ռ� üũ ������� ���� ��ȸ
     * @param ctx
     * @throws Exception
     */
    private HashMap<String, MatchDutyCalcu> specCalcuMap(BusinessContext ctx) throws Exception {
        HashMap<String, MatchDutyCalcu> calcuMap = new HashMap<String, MatchDutyCalcu>();
        String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
        DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
        DatasetSqlRequest sqlReq = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108002C_S2");
        sqlReq.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
        Dataset ds_calcu_list = (Dataset)executor.query(sqlReq).getResultObject();

        String SPEC = "";
        for (int i=0; i< ds_calcu_list.getRowCount(); i++) {
            SPEC = ds_calcu_list.getColumnAsString(i, "SPEC");
            MatchDutyCalcu matchDutyCalcu = new MatchDutyCalcu();
            matchDutyCalcu.setMANDT(ds_calcu_list.getColumnAsString(i, "MANDT"));
            matchDutyCalcu.setSPEC(ds_calcu_list.getColumnAsString(i, "SPEC"));
            matchDutyCalcu.setCALCULAT(ds_calcu_list.getColumnAsString(i, "CALCULAT"));
            matchDutyCalcu.setFOMAT(ds_calcu_list.getColumnAsString(i, "FOMAT"));
            matchDutyCalcu.setBIGO(ds_calcu_list.getColumnAsString(i, "BIGO"));
            calcuMap.put(SPEC, matchDutyCalcu);
        }
        return calcuMap;
    }

    /**
     * ���� ������� ����
     * @param ctx
     * @throws SpecDutyException
     * @throws Exception
     */
    private void specCalcuExe(MatchDuty matchDuty
                              ,MatchDutyD matchDutyD
                              ,HashMap<String, String> inputKndValue
                              ,HashMap<String, MatchDutyCalcu> calcuMap
                              ,HashMap<String, String> calcuMapSpec) throws SpecDutyException {
        if( calcuMap.containsKey(matchDutyD.getSPEC()) ) {
            MatchDutyCalcu matchDutyCalcu = calcuMap.get(matchDutyD.getSPEC());
            // ������� ���� ��쿡�� Dummy ���� �̹Ƿ� ��������� �������� �ʴ´�.
            if( !"".equals(matchDutyCalcu.getCALCULAT()) ) {
                String spec     = matchDutyCalcu.getSPEC();
                String calType  = matchDutyCalcu.getFOMAT();
                String calculat = MatchDutyOpr.calValueChange(spec, matchDutyCalcu.getCALCULAT(), inputKndValue, calcuMapSpec);
                String format = "";
                String calRslt = "";
                try {
                    /* ���� ���� ����(FORMAT)
                    A: �������
                    AT,2: ������� �� �Ҽ���2�ڸ� ����
                    AR,2: ������� �� �Ҽ���2�ڸ� �ݿø�
                    B: ���Ǽ��� ���ڰ�� ����
                    C: ���Ǽ��� ������� ���ڰ�� ����
                    CT,2: ���Ǽ��� ������� �� �Ҽ���2�ڸ� ����
                    CR,2: ���Ǽ��� ������� �� �Ҽ���2�ڸ� �ݿø�
                    D: ���� ���� ���ڰ�� ����
                    */
//                    logger.debug("���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư���ڵ�:"+matchDutyD.getSPEC()+"\n");
//                    logger.debug("���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����:"+matchDutyCalcu.getCALCULAT()+"\n");
//                    logger.debug("���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ����� ����:"+matchDutyCalcu.getFOMAT()+"\n");
//                    logger.debug("���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ġȯ �����:"+calculat+"\n");

                    if( calType.indexOf("A") > -1 ) {
                        if( calType.length() > 1 ) {
                            format = calType.substring(1);
                        }
                        calRslt = MatchDutyOpr.calculation(spec, calculat, format, inputKndValue);
                        inputKndValue.put(spec, calRslt);
//                        logger.debug("���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ������:"+calRslt+"\n");
                    } else if( calType.indexOf("B") > -1 ) {
                        calRslt = MatchDutyOpr.ifElseStringFunction(spec, calculat);
                        inputKndValue.put(spec, calRslt);
//                        logger.debug("���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ������:"+calRslt+"\n");
                    } else if( calType.indexOf("C") > -1 ) {
                        if( calType.length() > 1 ) {
                            format = calType.substring(1);
                        }
                        calRslt = MatchDutyOpr.ifElseNumFunction(spec, format, calculat);
                        inputKndValue.put(spec, calRslt);
//                        logger.debug("���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ������:"+calRslt+"\n");
                    } else if( calType.indexOf("D") > -1 ) {
                        calRslt = MatchDutyOpr.ifStringFunction(spec, calculat);
                        inputKndValue.put(spec, calRslt);
//                        logger.debug("���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ������:"+calRslt+"\n");
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    String error = "���� ���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư���ڵ�:"+matchDutyD.getSPEC()+"\n";
                    error = error +"���� ���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����:"+matchDutyCalcu.getCALCULAT()+"\n";
                    error = error +"���� ���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ġȯ �����:"+calculat+"\n";
                    error = error +"���� ���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����޼���:"+e.getLocalizedMessage();
                    throw new SpecDutyException(error);
                    /*
                    String error = "���� ���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư���ڵ�:"+matchDutyD.getSPEC()+"\n";
                    error = error +"���� ���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����:"+matchDutyCalcu.getCALCULAT()+"\n";
                    error = error +"���� ���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] ġȯ �����:"+calculat+"\n";
                    error = error +"���� ���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����޼���:"+e.getLocalizedMessage();
                    logger.debug(error);
                    */
                }
            }
        } else {
            // ���ǵ� ���� ������ ���� ���
            String error = "���� ���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] Ư���ڵ�:"+matchDutyD.getSPEC()+"\n";
            error = error +"���� ���տ���� ["+matchDuty.getBLOCKNO()+"]["+matchDutyD.getBLOCKNOSEQ()+"]["+matchDutyD.getZORDER()+"] �����޼���:���ǵ� ������� �����ϴ�.";
            logger.debug(error);
            throw new SpecDutyException(error);
        }
    }

    /**
     * ���ռ� ����� ����
     * @param ctx
     * @throws Exception
     */
    @Override
    public void specBomMatchVerify(BusinessContext ctx, Dataset ds_cond)
            throws Exception {

        // ���ռ� ó���� ���� �������� 0������ ���� �� ���ռ� ó�� ���� ����
        String calculation =  ds_cond.getColumnAsString(0, "CALCULAT");
        String calType = ds_cond.getColumnAsString(0, "FOMAT");
        String regexp = "\\{(\\w+)\\}";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(calculation.replaceAll(System.getProperty("line.separator"), ""));
        StringBuffer result = new StringBuffer();
        while ( matcher.find()) {
            matcher.appendReplacement( result, "1");
        }
        matcher.appendTail(result);
        calculation = result.toString();

        try {
            if( calType.indexOf("A") > -1 ) {
                MatchDutyOpr.calculation("A", calculation, calType, null);
            } else if( calType.indexOf("B") > -1 ) {
                MatchDutyOpr.ifElseStringFunction("B", calculation);
            } else if( calType.indexOf("C") > -1 ) {
                MatchDutyOpr.ifElseNumFunction("C", calType, calculation);
            } else if( calType.indexOf("D") > -1 ) {
                MatchDutyOpr.ifStringFunction("D", calculation);
            }
        } catch(Exception e) {
            String error = "����� ���� ����:"+e.getLocalizedMessage();
            throw new SpecDutyException(error);
        }
    }
}
