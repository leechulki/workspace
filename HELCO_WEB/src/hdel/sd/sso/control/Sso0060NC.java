package hdel.sd.sso.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.DatasetUtil;
import com.sap.domain.OrderNo;
import com.sap.domain.OrderItem;
import com.sap.domain.SapBool;
import com.sap.domain.Vbeln;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT0057;
import hdel.lib.domain.ZSDT1046;
import hdel.lib.service.ZSDT0057S;
import hdel.lib.service.ZSDT1046S;
import hdel.sd.com.domain.Com0160;
import hdel.sd.com.domain.Com0160ParamVO;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.com.service.Com0160S;
import hdel.sd.com.service.ComboS;
import hdel.sd.com.service.Coms01AS;
import hdel.sd.com.service.ExchangeS;
import hdel.sd.sso.domain.Sso0060;
import hdel.sd.sso.domain.Sso0060ParamVO;
import hdel.sd.sso.service.Sso0060NS;
import tit.util.DatasetUtility;
import webservice.stub.sap.SAPStub;
import webservice.stub.sap.ZWEB_SD_IS_STO_ORDERStub;
import webservice.stub.sap.ZWEB_SD_SET_STO_INFOStub;

/*
 ******************************************************************************************
 * ��      ��   : ���ֻ��� ��ȸ �� ����(Sso0060C) Control
 * @Comment
 *         1. View find ( ���س���� �ش��ϴ� ������� ��ȸ )
 *         2. View find         ( ����ä�� �� �� ä����Ȳ(û����) ��� ��ȸ )
 *            - T-CODE             :     ZSDR152
 *            - RFC URL              :     (��ȸ)                     http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FBE0081D32E0110E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *            - RFC OPERATION NAME :     (�̼�, �ε� ��ȸ)            ZWEB_SD_CHN_152         (ZSDS0034)
 *                                   (���ݸ���Ʈ ��ȸ)            ZWEB_SD_CHN_152         (ZSDS0036)
 *            - RFC OBJ NAME          :     (�̼�, �ε� ��ȸ)            ZSDS0034
 *                                   (���ݸ���Ʈ ��ȸ)            ZSDS0036
 *         3. View update_plan ( ���س���� �ش��ϴ� �̼�, �ε��� ���ݰ�ȹ���� )
 *            - T-CODE             :     ZSDR152
 *            - RFC URL              :     (�̼�, �ε� ���ݰ�ȹ ����)     http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FC4981489370110E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *            - RFC OPERATION NAME :     (�̼�, �ε� ���ݰ�ȹ ����)    ZWEB_SD_CHN_152_PLAN    (ZSDS0039)
 *            - RFC OBJ NAME          :     (�̼�, �ε� ���ݰ�ȹ ����)    ZSDS0039
 *         4. View update_reason ( ���س���� �ش��ϴ� �̼�, �ε��� ä�ǻ������� )
 *            - T-CODE             :     ZSDR152
 *            - RFC URL              :     (�̼�, �ε� ä�ǻ��� ����)     http://elsdev.hyundaielevator.co.kr:8000/sap/bc/srt/wsdl/bndg_4FC22D3D89930018E1008000CBF22518/wsdl11/allinone/ws_policy/document?sap-client=910
 *            - RFC OPERATION NAME :     (�̼�, �ε� ä�ǻ��� ����)     ZWEB_SD_CHN_152_RES       (ZSDT0053)
 *            - RFC OBJ NAME          :     (�̼�, �ε� ä�ǻ��� ����)     ZSDT0053
 * ��  ��  ��   : �ڼ���
 * �ۼ�  ����   : 2016.02.12
 * ���ID       : UF014
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 ���� ���� �� �ڵ� �ڼ���
 ******************************************************************************************
*/

@Controller
@RequestMapping("sso0060N")
public class Sso0060NC {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private SrmView view;

    @Autowired
    private SrmSqlSession sqlSession;

    @Autowired
    private Sso0060NS sso0060NS;
    @Autowired
    private ZSDT0057S zsdt0057s;

    @Autowired
    private ZSDT1046S zsdt1046s;
    
    @Autowired
    private ComboS comboS;

    @Autowired
    private Com0160S com0160S;
    
    @Autowired
    private ExchangeS ExchangeS;

    // 2020 �귣��
    @Autowired
    private Coms01AS coms01AS;
    
    //=========================================================================================
    //  �Լ����  : ���ֺ��� ��û�� �ʿ��� �����ڵ� ���� ��ȸ(��������, �ǹ��뵵, ��������, �ε�����, ��������)
    //  �Ķ����  : ��������(ds_auart_cd)
    //              �ǹ��뵵(ds_cond_build)
    //              ��������(ds_list_zterm)
    //              �ε�����(ds_list_inco1)
    //              ��������(ds_list_mlbez)
    //  ���ϰ�    :
    //  ���ID    : UF014
    //  ��������  : �����ڵ� ���� �׼� ȣ�� ������ �ϳ��� �׼� ȣ��� ����
    //=========================================================================================
    @RequestMapping(value="CommonCode")
    public View Sso0060CommonCode(MipParameterVO paramVO, Model model) {
        // dataset�� return
        MipResultVO resultVO = new MipResultVO();
        Dataset     ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

        try {
            // DB ���ӿ� ���ؼ�
            comboS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

            // �������� �����ͼ�
            Dataset ds_auart_list = paramVO.getDataset("ds_auart_cd");

            // �Ķ���� ����
            ComboParamVO comboParamVO = new ComboParamVO();
            comboParamVO.setMandt(paramVO.getVariable("G_MANDT")); // ä��

            // ����ȣ��
            List<ComboVO> listAuartCombo = comboS.selectAuartNm(comboParamVO);     // ���������ڵ�, ���������� ��ȸ

            // ȣ����(listCombo)�� ����Ÿ��(ds_list)�� ����
            ds_auart_list.deleteAll();
            for (int iRow=0;iRow<listAuartCombo.size();iRow++) {
                ds_auart_list.appendRow();
                ds_auart_list.setColumn(iRow, "CODE"        , listAuartCombo.get(iRow).getCode());         // �ڵ�
                ds_auart_list.setColumn(iRow, "CODE_NAME"    , listAuartCombo.get(iRow).getCodeName());    // �ڵ��
            }
            resultVO.addDataset(ds_auart_list);

            // OUTPUT DATASET DECLARE
            // �ǹ��뵵 �����ͼ�
            Dataset dsCond = paramVO.getDataset("ds_cond_build");
            
            // UI�� return�� out dataset
            Dataset ds_list_build         = paramVO.getDataset("ds_list_build");
            Dataset ds_list_build_l       = paramVO.getDataset("ds_list_build_l"); //2020.12 jss
            
            com0160S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

            Com0160ParamVO com0160ParamVO = new Com0160ParamVO();
            // SAP CLIENT
            com0160ParamVO.setMandt(paramVO.getVariable("G_MANDT"));
            com0160ParamVO.setBuilduse(DatasetUtility.getString(dsCond,"BUILDUSE"));
            com0160ParamVO.setStext(DatasetUtility.getString(dsCond,"STEXT"));
            com0160ParamVO.setWhere(DatasetUtility.getString(dsCond,"WHERE"));
            
            // ����CALL
            List<Com0160> listBuild = com0160S.find(com0160ParamVO);
            List<Com0160> listBuild_l = com0160S.find_l(com0160ParamVO); //2020.12 jss

            ds_list_build.deleteAll();
            // ȣ����(list)�� ����Ÿ��(ds_list_build)�� ����
            for (int iRow=0;iRow<listBuild.size();iRow++) {
                // ���߰�
                ds_list_build.appendRow();
                // �÷�SET
                for (int iCol=0;iCol<ds_list_build.getColumnCount();iCol++) {
                    if (ds_list_build.getColumnID(iCol).equals("BUILDUSE")) {
                        // �ŷ�ó�ڵ�
                        ds_list_build.setColumn(iRow, iCol    , listBuild.get(iRow).getBuilduse());
                    } 
                    else if (ds_list_build.getColumnID(iCol).equals("STEXT")) {
                        // �ŷ�ó��
                        ds_list_build.setColumn(iRow, iCol    , listBuild.get(iRow).getStext());
                    }
                    //2020.11.30 jss
                    else if (ds_list_build.getColumnID(iCol).equals("BUILDUSE_L")) {
                        //�ǹ��뵵��з�
                        ds_list_build.setColumn(iRow, iCol    , listBuild.get(iRow).getBuilduse_l());
                    }
                }
            }
            
            //2020.12 jss : �ǹ��뵵 ��з� ����
            ds_list_build_l.deleteAll();
            for (int iRow=0;iRow<listBuild_l.size();iRow++) {
                // ���߰�
                ds_list_build_l.appendRow();
                // �÷�SET
                for (int iCol=0;iCol<ds_list_build_l.getColumnCount();iCol++) {
                    if (ds_list_build_l.getColumnID(iCol).equals("BUILDUSE_L")) {
                        // �ŷ�ó�ڵ�
                        ds_list_build_l.setColumn(iRow, iCol    , listBuild_l.get(iRow).getBuilduse_l());
                    } 
                    else if (ds_list_build_l.getColumnID(iCol).equals("STEXT")) {
                        // �ŷ�ó��
                        ds_list_build_l.setColumn(iRow, iCol    , listBuild_l.get(iRow).getStext());
                    }
                }
            }
            
            resultVO.addDataset(ds_list_build);
            resultVO.addDataset(ds_list_build_l); //2020.12 jss

            // �������� ds_list_zterm
            // INPUT/OUTPUT DATASET GET
            Dataset ds_list_zterm = paramVO.getDataset("ds_list_zterm");

            // ����ȣ��
            // �������� ��ȸ
            List<ComboVO> listZtermCombo = comboS.selectListZterm(comboParamVO);

            // ȣ����(listCombo)�� ����Ÿ��(ds_list)�� ����
            ds_list_zterm.deleteAll();
            for (int iRow=0;iRow<listZtermCombo.size();iRow++) {
                ds_list_zterm.appendRow();
                ds_list_zterm.setColumn(iRow, "CODE"        , listZtermCombo.get(iRow).getCode());         // �ڵ�
                ds_list_zterm.setColumn(iRow, "CODE_NAME"    , listZtermCombo.get(iRow).getCodeName());    // �ڵ��
            }
            resultVO.addDataset(ds_list_zterm);

            // �ε�����(ds_list_inco1)
            Dataset ds_list_inco1 = paramVO.getDataset("ds_list_inco1");
            // ����ȣ��
            List<ComboVO> listIncoCombo = comboS.selectListInco(comboParamVO);     // �ε����� ��ȸ
            ds_list_inco1.deleteAll();
            for (int iRow=0;iRow<listIncoCombo.size();iRow++) {
                ds_list_inco1.appendRow();
                ds_list_inco1.setColumn(iRow, "CODE"        , listIncoCombo.get(iRow).getCode());         // �ڵ�
                ds_list_inco1.setColumn(iRow, "CODE_NAME"    , listIncoCombo.get(iRow).getCodeName());    // �ڵ��
            }
            resultVO.addDataset(ds_list_inco1);

            // ��������(ds_list_mlbez)
            Dataset ds_list_mlbez = paramVO.getDataset("ds_list_mlbez");
            // ����ȣ��
            List<ComboVO> listMlbezCombo = comboS.selectListMlbez(comboParamVO);     // �������� ��ȸ

            // ȣ����(listCombo)�� ����Ÿ��(ds_list)�� ����
            ds_list_mlbez.deleteAll();
            for (int iRow=0;iRow<listMlbezCombo.size();iRow++)     {
                ds_list_mlbez.appendRow();
                ds_list_mlbez.setColumn(iRow, "CODE"        , listMlbezCombo.get(iRow).getCode());         // �ڵ�
                ds_list_mlbez.setColumn(iRow, "CODE_NAME"    , listMlbezCombo.get(iRow).getCodeName());    // �ڵ��
            }
            resultVO.addDataset(ds_list_mlbez);
            
            
            // 2020 �귣���ڵ� ��ȸ
            coms01AS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
            Dataset ds_list_abrand = paramVO.getDataset("ds_list_abrand");
            comboParamVO.setVkbur("EL_ABRAND");
            
            // ����ȣ��
            List<ComboVO> listAbrand = coms01AS.selectAtwrt(comboParamVO);     // �������� ��ȸ
            
            ds_list_abrand.deleteAll();
            for (int iRow=0;iRow<listAbrand.size();iRow++)     {
            	ds_list_abrand.appendRow();
            	ds_list_abrand.setColumn(iRow, "CODE"       , listAbrand.get(iRow).getCode());        // �ڵ�
            	ds_list_abrand.setColumn(iRow, "CODE_NAME"  , listAbrand.get(iRow).getCodeName());    // �ڵ��
            }
            resultVO.addDataset(ds_list_abrand);
        } catch(Exception e) {
            // �����ܰ� ȭ��ܿ��� ������ ���� �κ� ó������ �ʿ���
            logger.error(e.getMessage());
            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
        } finally {
            // �ݵ�� ����Ǿ�� �� ó�� ����
            // ���� ���� �ܿ��� error ������  �����ϴ� ��쿡 ���ϰ� ����
        }

        // ��������
        model.addAttribute("resultVO", resultVO);
        return view;
    }

    //=========================================================================================
    //  �Լ����  : ������ȣ �Ǵ� �����ڷ� �Ǵ� ������Ʈ��ȣ�� �ش��ϴ� ���ֻ����ڷ� ��ȸ ���� ����� �Է� data ��ȸ
    //  �Ķ����  : MipParameterVO paramVO, Model model
    //  ���ϰ�    : MipResultVO resultVO
    //  ���ID    : UF014
    //  ��������  : ���� ����� �Է� data ��ȸ(���ֻ����ӽ�����)
    //=========================================================================================
    @RequestMapping(value="findEstimateCreate")
    public View Sso0060EstiMateCreateFindView(MipParameterVO paramVO, Model model) {
        // INPUT DATASET GET
        Dataset ds_cond         = paramVO.getDataset("ds_cond");             // IN_�˻�����
        Dataset ds_dtl          = paramVO.getDataset("ds_dtl");              // UI�� return�� out dataset (HEADER)
        Dataset ds_list_partner = paramVO.getDataset("ds_list_partner");     // OUT_��Ʈ������
        Dataset ds_list_item    = paramVO.getDataset("ds_list_item");        // UI�� return�� out dataset (ITEM)
        Dataset ds_list_bill    = paramVO.getDataset("ds_list_bill");        // UI�� return�� out dataset (BILLING PLAN)
        Dataset ds_list_txt     = paramVO.getDataset("ds_list_txt");         // UI�� return�� out dataset (TEXT)
        Dataset ds_manager		= paramVO.getDataset("ds_manager");

        // ���� GET
        String mandt             = paramVO.getVariable("G_MANDT");                // IN_CLIENT
        String cmd               = DatasetUtility.getString(ds_cond,"CMD") ;      // ó������(DISP:��ȸ, SAVE:���ֻ���, UPDT:���ֽ���������)
        String qry_gb            = DatasetUtility.getString(ds_cond,"QRY_GB") ;   // ��ȸ����(Q:������ȣ,P:������Ʈ)
        String qtnum             = DatasetUtility.getString(ds_cond,"QTNUM")  ;   // ������ȣ
        String qtser             = DatasetUtility.getString(ds_cond,"QTSER")  ;   // ��������
        String pspid             = DatasetUtility.getString(ds_cond,"PSPID") ;    // ������Ʈ��ȣ
        // 2012.10.19 ����������� �ڵ� �߰� �ݿ�
        String zprgflg          = "";                                             // ����������� ��ȸ������.
        
		
        // OUTPUT DATASET DECLARE
        Dataset     ds_error    = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset

        // ��� VIEW
        MipResultVO resultVO = new MipResultVO();
        
        //--------------------------------------------------------------------------------------------
        // INPUT PARAM INFO
        // sap client (���� : 310 or 910)
        logger.debug("paramVO.G_MANDT     ===>"+mandt    );     // CLIENT
        logger.debug("ds_cond.CMD         ===>"+cmd);           // ó������(DISP:��ȸ, SAVE:���ֻ���, UPDT:���ֽ���������)
        logger.debug("ds_cond.QRY_GB      ===>"+qry_gb);        // ��ȸ����(Q:������ȣ,P:������Ʈ)
        logger.debug("ds_cond.QTNUM       ===>"+qtnum);         // ������ȣ
        logger.debug("ds_cond.QTSER       ===>"+qtser);         // ��������
        logger.debug("ds_cond.PSPID       ===>"+pspid );        // ������Ʈ��ȣ
        //--------------------------------------------------------------------------------------------

        try {
            sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
            zsdt1046s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));                        
            ExchangeS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

            // IN, OUT class declare
            List<Sso0060>     list     = null;                    // �������ƮSET
            Sso0060ParamVO    param    = new Sso0060ParamVO();    // ����
            param.setMANDT(mandt);                                // ����_SAP CLIENT
            param.setQTNUM(qtnum);                                // ������ȣ

            logger.debug("1.1 ������ȣ�� ���������� �������� ������� ���� RETURN");
            list = sso0060NS.selectListQtser(param);

            // �������̺� ������Ʈ��ȣ�� �ش��ϴ� �ڷ� ������ ��
            if (list == null || list.size() == 0) {
                // "CW10013", "${}�� �����Ϸ������� �������� �ʴ� ������ȣ�Դϴ�.");
                ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW10013", "", "Y", "Y");
                ds_error.setId("ds_error");               // ERROR
                model.addAttribute("resultVO", resultVO);
                logger.debug("�����Ϸ����� ������ ������ȣ : [" + qtnum + "]");
                return view;
            }

            // 1.2 ��ȸ�� ������Ʈ��ȣ SET
            logger.debug("1.2 ��ȸ�� ������Ʈ��ȣ SET");
            pspid   = list.get(0).getVBELN();    // ������Ʈ��ȣ
            qtnum   = list.get(0).getQTNUM();    // ������ȣ
            qtser   = list.get(0).getQTSER();    // ��������
            zprgflg = list.get(0).getZPRGFLG();  // ������������ڵ�
            logger.debug("�����������:"+zprgflg);
            logger.debug("pspid:"+pspid);
            
            ZSDT1046 zsdt1046 = zsdt1046s.select(mandt, qtnum, Integer.parseInt(qtser));

            // 1.3 ����HEADER T/B�� ������Ʈ��ȣ�� ������ ��� �� ���ֻ����� �����ڷ��̹Ƿ�
            // SAP FUNCTION CALL�� �����Ѵ�.
            logger.debug("1.3 ����HEADER T/B�� ������Ʈ��ȣ�� ������ ��� �� ���ֻ����� �����ڷ��̹Ƿ� SAP FUNCTION CALL�� �����Ѵ�. ");
            if (StringUtils.isNotBlank(pspid))     {
                logger.debug("getSapXmlEstimatDataset ������Ʈ��ȣ�� ���ֻ����ڷ� ��ȸ");
                // 3.1 ������Ʈ��ȣ�� ������ȣ, �������� ��ȸ
                logger.debug("������Ʈ��ȣ�� ������ȣ, �������� ��ȸ");
                param.setQTSER(qtser);    // �����Ϸù�ȣ
                param.setVBELN(pspid);    // ������Ʈ��ȣ
                qry_gb = "P";

                // XML ��� SAP ȣ�� ���
                logger.debug("XML ��� ������ ��ȸ");
                sso0060NS.getSapXmlEstimatDataset(paramVO.getVariable("G_SYSID"), cmd, zprgflg, qry_gb, param, ds_list_partner, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, ds_error, resultVO);
                // JCO ��� SAP ȣ�� ���
                //logger.debug("JCO ��� SAP ȣ�� ���");
                //sso0060NS.getSapJcoEstimatDataset(cmd, zprgflg, qry_gb, param, ds_list_partner, ds_error, resultVO);
                
                resultVO.addVariable("F_TMPSEARCH", "N");
            }
            else {
                // �Ķ���� ����
                logger.debug("2.1 ��ȸ �Ķ���� ����");
                param.setQTSER(qtser);    // �����Ϸù�ȣ
                
                // �ӽ� ������ ������ ��ȸ�Ѵ�.
                boolean bIsData = sso0060NS.getTmpEstimatDataset(zprgflg, qry_gb, param, ds_list_partner, ds_manager, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, ds_error, resultVO);  
                
                // �ӽ� ������ ������ ���� ��� ������ ���̺��� ������ ��ȸ�Ѵ�.
                if( !bIsData ) {
                    // �������̺� �ڷᰡ ���� ��� ������ ���̺��� �ش� �ڷḦ ��ȸ�Ѵ�.
                    // 2.2 ���� HEADER���� ��ȸ   (��Ʈ�������� �Բ� ��ȸ)
                    sso0060NS.getEstimatDataset(zprgflg, qry_gb, param, ds_list_partner, ds_manager, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, ds_error, resultVO);
                    //���ֻ����� ���� ������� ���� -- 2020.07.23 by eunha
                    String lifnrchk = zsdt1046.getLifnrchk();
                    if (lifnrchk.equals("1")) {
                    	ds_dtl.setColumn(0, "LIFNRCHK", "1");
                    	ds_dtl.setColumn(0, "LIFNR", "2000");
                    }
                    else if (lifnrchk.equals("3") || lifnrchk.equals("4")) {
                    	ds_dtl.setColumn(0, "LIFNRCHK", "2");
                    }

                    resultVO.addVariable("F_TMPSEARCH", "N");
                } else {
                	resultVO.addVariable("F_TMPSEARCH", "Y");
                }
                
            }
            
            ds_dtl.setColumn(0, "QTDAT", zsdt1046.getQtdat());
            logger.debug("������ȸ����      : " + qry_gb);
            logger.debug("���� ������Ʈ��ȣ : " + pspid);
            logger.debug("���� ������ȣ     : " + qtnum);
            logger.debug("���� ��������     : " + qtser);
        } catch(DataAccessException de) {
        	SQLException se = (SQLException)((DataAccessException)de).getRootCause();
        	logger.error("DataAccessException:"+se.getMessage());
            logger.error("Exception:"+de.getMessage());
            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", de.getMessage(), "Y", "Y");
        } catch(Exception e) {
            // �����ܰ� ȭ��ܿ��� ������ ���� �κ� ó������ �ʿ���
            logger.error("Exception:"+e.getMessage());
            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
        } finally {
            // �ݵ�� ����Ǿ�� �� ó�� ����
            // ���� ���� �ܿ��� error ������  �����ϴ� ��쿡 ���ϰ� ����
            // 5. RFC ��� dataset�� return
            resultVO.addDataset        (ds_error);            // ERROR
            model.addAttribute        ("resultVO", resultVO);
            logger.debug("Sso0060FindView addAttribute end" + "");
        }
        
        return view;
    }

    //=========================================================================================
    //  �Լ����  : ���ֺ��� �ڷ� ��ȸ
    //  �Ķ����  : MipParameterVO paramVO, Model model
    //  ���ϰ�    : MipResultVO resultVO
    //  ���ID    : UF015
    //  ��������  : ���� ���ֺ��� �ڷ� ��ȸ �޼ҵ� �и� 
    //=========================================================================================
    @RequestMapping(value="findOrderChange")
    public View Sso0060OrderChangeFindView(MipParameterVO paramVO, Model model) {
        // INPUT DATASET GET
        Dataset ds_cond         = paramVO.getDataset("ds_cond");             // IN_�˻�����
        @SuppressWarnings("unused")
		Dataset ds_dtl          = paramVO.getDataset("ds_dtl");              // UI�� return�� out dataset (HEADER)
        Dataset ds_sto          = paramVO.getDataset("ds_sto");
        Dataset ds_list_partner = paramVO.getDataset("ds_list_partner");     // OUT_��Ʈ������
        Dataset ds_manager		= paramVO.getDataset("ds_manager");			 // OUT_�������������
        @SuppressWarnings("unused")
		Dataset ds_list_item    = paramVO.getDataset("ds_list_item");        // UI�� return�� out dataset (ITEM)
        @SuppressWarnings("unused")
		Dataset ds_list_bill    = paramVO.getDataset("ds_list_bill");        // UI�� return�� out dataset (BILLING PLAN)
        @SuppressWarnings("unused")
		Dataset ds_list_txt     = paramVO.getDataset("ds_list_txt");         // UI�� return�� out dataset (TEXT)

        // ���� GET
        String mandt             = paramVO.getVariable("G_MANDT");                // IN_CLIENT
        String cmd               = DatasetUtility.getString(ds_cond,"CMD") ;      // ó������(DISP:��ȸ, SAVE:���ֻ���, UPDT:���ֽ���������)
        String qry_gb            = DatasetUtility.getString(ds_cond,"QRY_GB") ;   // ��ȸ����(Q:������ȣ,P:������Ʈ)
        String qtnum             = DatasetUtility.getString(ds_cond,"QTNUM")  ;   // ������ȣ
        String qtser             = DatasetUtility.getString(ds_cond,"QTSER")  ;   // ��������
        String pspid             = DatasetUtility.getString(ds_cond,"PSPID") ;    // ������Ʈ��ȣ
        // 2012.10.19 ����������� �ڵ� �߰� �ݿ�
        String zprgflg          = "";                                             // ����������� ��ȸ������.

        Dataset     ds_error    = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset

        MipResultVO resultVO = new MipResultVO();

        try {
            sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
            zsdt0057s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

            List<Sso0060>     list     = null;                    // �������ƮSET
            Sso0060ParamVO    param    = new Sso0060ParamVO();    // ����
            param.setMANDT(mandt);                                // ����_SAP CLIENT
			param.setVBELN(pspid);	                              // ������Ʈ��ȣ

			list = sso0060NS.selectListQtnum(param);

			// ������Ʈ ��ȣ�� ������ �����ϴ� ��쿡�� ó��
			if (list != null && list.size() > 0 )
			{
				// 3.2 ��ȸ�� ������ȣ SET
				pspid = list.get(0).getVBELN();	// ������Ʈ��ȣ  
				qtnum = list.get(0).getQTNUM();	// ������ȣ
				qtser = list.get(0).getQTSER();	// ��������
				zprgflg = list.get(0).getZPRGFLG();  // ������������ڵ�
	            param.setQTNUM(qtnum);    // ������ȣ
	            param.setQTSER(qtser);    // �����Ϸù�ȣ
			}
            // 1.3 ����HEADER T/B�� ������Ʈ��ȣ�� ������ ��� �� ���ֻ����� �����ڷ��̹Ƿ�
            // SAP FUNCTION CALL�� �����Ѵ�.
            if (StringUtils.isNotBlank(pspid))     {
                // 3.1 ������Ʈ��ȣ�� ������ȣ, �������� ��ȸ
                param.setQTSER(qtser);    // �����Ϸù�ȣ
                param.setVBELN(pspid);    // ������Ʈ��ȣ
                qry_gb = "P";

                // XML ��� SAP ȣ�� ���
                sso0060NS.getSapXmlEstimatDataset(paramVO.getVariable("G_SYSID"), cmd, zprgflg, qry_gb, param, ds_list_partner, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, ds_error, resultVO);

                // JCO ��� SAP ȣ�� ���
                // logger.debug("JCO ��� SAP ȣ�� ���");
                // sso0060NS.getSapJcoEstimatDataset(cmd, zprgflg, qry_gb, param, ds_list_partner, ds_error, resultVO);
                try {
                    ZSDT0057 zsdt0057 = zsdt0057s.select(mandt, new Vbeln(param.getVBELN()), new SapBool(false));
                    ds_sto.deleteAll();
                    ds_sto.appendRow();
                    DatasetUtil.moveObjToDsRow(zsdt0057, ds_sto, 0);
                    ds_sto.setColumn(0, "ordno", zsdt0057.getVbeln().toString());
                } catch(Exception e) {
                	System.out.println("### zsdt0057s.select error ###");
                	e.printStackTrace();
                	System.out.println("### zsdt0057s.select error ###");
                }
            }
        } catch(DataAccessException de) {
        	SQLException se = (SQLException)((DataAccessException)de).getRootCause();
        	logger.error("DataAccessException:"+se.getMessage());
        } catch(Exception e) {
            // �����ܰ� ȭ��ܿ��� ������ ���� �κ� ó������ �ʿ���
            logger.error("Exception:"+e.getMessage());
            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
        } finally {
            // �ݵ�� ����Ǿ�� �� ó�� ����
            // ���� ���� �ܿ��� error ������  �����ϴ� ��쿡 ���ϰ� ����
            // 5. RFC ��� dataset�� return
			if( ds_error.getRowCount() > 0 ) {
				resultVO.addDataset	(ds_error);  	// ��������
			}
            resultVO.addDataset(ds_sto);
            model.addAttribute        ("resultVO", resultVO);
        }

        return view;
    }

    //=========================================================================================
    //  �Լ����  : �����ڷ� ���� ����
    //  �Ķ����  : MipParameterVO paramVO, Model model
    //  ���ϰ�    : MipResultVO resultVO
    //  ���ID    : UF015
    //  ��������  : ���� ���� ���� ����
    //=========================================================================================
	@RequestMapping(value="saveXmlEstiMateCreate")
	public View Sso0060SaveXmlEstiMateCreateView(MipParameterVO paramVO, Model model) {
		Dataset ds_cond 	 = paramVO.getDataset("ds_cond");
		Dataset ds_dtl 		 = paramVO.getDataset("ds_dtl_save");        	// HEADER  (PARTNER ���� ����)
		Dataset ds_list_item = paramVO.getDataset("ds_list_item_save");  	// ITEM
		Dataset ds_list_bill = paramVO.getDataset("ds_list_bill_save");  	// BILLING PLAN
		Dataset ds_list_txt  = paramVO.getDataset("ds_list_txt_save");  	// TEXT
		Dataset ds_sto = paramVO.getDataset("ds_sto");

		Dataset ds_error	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset

		String mandt  = paramVO.getVariable("G_MANDT");				    // IN_CLIENT
		String cmd 	  = DatasetUtility.getString(ds_cond,"CMD") ;		// ó������(DISP:��ȸ,SAVE:���ֻ���,UPDT:���ֺ���,DELE:���ֻ���)
		String qry_gb = DatasetUtility.getString(ds_cond,"QRY_GB") ;	// ��ȸ����(Q:������ȣ,P:������Ʈ)
		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// ������ȣ
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// ��������
		String pspid  = DatasetUtility.getString(ds_cond,"PSPID") ;		// ������Ʈ��ȣ
		


		MipResultVO resultVO = new MipResultVO();
		try {
			sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			zsdt0057s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			Sso0060ParamVO 	param 	= new Sso0060ParamVO();   	// ����
			List<Sso0060> 	list 	= null;

			if (qtnum != null && !"".equals(qtnum)) 	{
				param.setMANDT(mandt);
				param.setQTNUM(qtnum);
				list = sso0060NS.selectListQtser(param);
				if( list.size() > 0 ) {
				    param.setQTSER(list.get(0).getQTSER().trim());	
				}
			}
			else if (pspid != null && !"".equals(pspid)) {
				param.setMANDT(mandt);
				param.setVBELN(pspid);
				list = sso0060NS.selectListQtnum(param);
			}

			if (list == null || list.size() == 0) {
				Sso0060 sso0060 = new Sso0060();
				sso0060.setVBELN(pspid);

				if (list == null) {
					list = new ArrayList<Sso0060>();
				}
				list.add(sso0060);
			} 
			else if (!"".equals(list.get(0).getVBELN().trim())) {
				pspid = list.get(0).getVBELN().trim();
				qtnum = list.get(0).getQTNUM().trim();
				qtser = list.get(0).getQTSER().trim();
			}

			if ("SAVE".equals(cmd)) {
				if (!"".equals(list.get(0).getVBELN().trim())) {
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW10024", "", "Y", "Y");
				}
				if(sso0060NS.validQtdat(paramVO.getVariable("G_SYSID"), mandt, ds_dtl.getColumnAsString(0, "QTDAT"), ds_dtl.getColumnAsString(0, "KUNNR_Z2")) == false) {
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", 
							"����� ������ ��ȿ�Ⱓ�� ���� ���ֻ����� �Ұ��Ͽ��� ���� ����� �ٶ��ϴ�."+System.getProperty("line.separator")
							+"Cost Engineering Team���� �����Ͻñ� �ٶ��ϴ�.",
							"Y", "Y");
				}
				
			}
			else if ("UPDT".equals(cmd) || "DELE".equals(cmd)) {
				if ("".equals(list.get(0).getVBELN().trim()))
				{
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW00052", "", "Y", "Y");	//���ֻ������� ������ ������Ʈ
				}
			}

			
			System.out.println("!!!!!!! ds_list_item:"+ds_list_item);
			if ( ds_error.getRowCount() == 0 ) {
				
				logger.debug("4. ������ ���� ��� ");
				Vbeln vbeln = sso0060NS.saveSapXmlProject(paramVO.getVariable("G_SYSID"), cmd, paramVO.getVariable("G_USER_ID"), param,
	                                        ds_cond, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, 
	                                        ds_error);
				if(SapBool.parseBoolean(ds_sto.getColumnAsString(0, "changed"))) {
					ds_sto.setColumn(0, "vbeln", vbeln.toString());
					setStoInfoDpcpd(paramVO);
				}
			} else {
				if( "SAVE".equals(cmd)) {
					sso0060NS.deleteTmpEstiMateTable(mandt, ds_cond);
				}
			}
		} catch (CommRfcException e) {
			logger.error("Sso0060SaveView CommRfcException ERROR!!!" + "");
			e.printStackTrace();     
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
	    } catch (Exception e) {
			logger.error("Sso0060SaveView Exception ERROR!!!" + "");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			//----------------------------------------------------------------
			//	3. ��� ����
			//----------------------------------------------------------------
			if( ds_error.getRowCount() > 0 ) {
				logger.debug("3. ��� ����");
				ds_error.setId		("ds_error");
				resultVO.addDataset	(ds_error);  	// ��������
			}
			model.addAttribute	("resultVO", resultVO);
		}
		logger.debug("Sso0060SaveProjectView end" + "");
		return view;
	}
    
    //=========================================================================================
    //  �Լ����  : �����ڷ� ���� ����
    //  �Ķ����  : MipParameterVO paramVO, Model model
    //  ���ϰ�    : MipResultVO resultVO
    //  ���ID    : UF015
    //  ��������  : ���� ���� ���� ����
    //=========================================================================================
	@RequestMapping(value="saveEstiMateCreate")
	public View Sso0060SaveEstiMateCreateView(MipParameterVO paramVO, Model model) {

		logger.debug("Sso0060SaveProjectView START");

		// INPUT DATASET GET
		Dataset ds_cond 	 = paramVO.getDataset("ds_cond");        		// ����
		Dataset ds_dtl 		 = paramVO.getDataset("ds_dtl_save");        	// HEADER  (PARTNER ���� ����)
		Dataset ds_list_item = paramVO.getDataset("ds_list_item_save");  	// ITEM
		Dataset ds_list_bill = paramVO.getDataset("ds_list_bill_save");  	// BILLING PLAN
		Dataset ds_list_txt  = paramVO.getDataset("ds_list_txt_save");  	// TEXT

		// UI�� return�� error out dataset
		Dataset ds_error	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset

		// ���� GET
		String mandt  = paramVO.getVariable("G_MANDT");				    // IN_CLIENT
		String cmd 	  = DatasetUtility.getString(ds_cond,"CMD") ;		// ó������(DISP:��ȸ,SAVE:���ֻ���,UPDT:���ֺ���,DELE:���ֻ���)
		String qry_gb = DatasetUtility.getString(ds_cond,"QRY_GB") ;	// ��ȸ����(Q:������ȣ,P:������Ʈ)
		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// ������ȣ
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// ��������
		String pspid  = DatasetUtility.getString(ds_cond,"PSPID") ;		// ������Ʈ��ȣ

		// RFC ��� dataset�� return
		MipResultVO resultVO = new MipResultVO();

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO
		// sap client (���� : 310 or 910)
		logger.debug("paramVO.G_MANDT 	===>"+mandt	);		// CLIENT
		logger.debug("ds_cond.CMD   	===>"+cmd);		    // ó������(DISP:��ȸ,SAVE:���ֻ���,UPDT:���ֺ���,DELE:���ֻ���)
		logger.debug("ds_cond.QRY_GB   	===>"+qry_gb);		// ��ȸ����(Q:������ȣ,P:������Ʈ)
		logger.debug("ds_cond.QTNUM   	===>"+qtnum);		// ������ȣ
		logger.debug("ds_cond.QTSER   	===>"+qtser);		// ��������
		logger.debug("ds_cond.PSPID   	===>"+pspid );		// ������Ʈ��ȣ
		logger.debug("ds_dtl.getRowCount()	     ===>"	+ds_dtl.getRowCount() +"");
		logger.debug("ds_list_item.getRowCount() ===>"	+ds_list_item.getRowCount() +"");
		logger.debug("ds_list_txt.getRowCount()	 ===>"	+ds_list_txt.getRowCount() +"");
		logger.debug("ds_list_bill.getRowCount() ===>"	+ds_list_bill.getRowCount() +"");

		try {
			// Session GET
			sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			Sso0060ParamVO 	param 	= new Sso0060ParamVO();   	// ����
			List<Sso0060> 	list 	= null;

			// 1. IN_������ȣ ���� �� ������ȣ�� ������Ʈ��ȣ ��ȸ
			if (qtnum != null && !"".equals(qtnum)) 	{
				logger.debug("1. ������ȣ ���� �� ������ȣ�� ������Ʈ��ȣ ��ȸ ");
				param.setMANDT(mandt);  					// ����_SAP CLIENT
				param.setQTNUM(qtnum);						// ������ȣ
				list = sso0060NS.selectListQtser(param);		// ��ȸ ���� ȣ��
				if( list.size() > 0 ) {
				    param.setQTSER(list.get(0).getQTSER().trim());	
				}
			}
			// 1. IN_������Ʈ��ȣ ���� �� ������Ʈ��ȣ�� ������ȣ ��ȸ
			else if (pspid != null && !"".equals(pspid)) {
				logger.debug("1. ������Ʈ��ȣ ���� �� ������Ʈ��ȣ�� ������ȣ ��ȸ");
				param.setMANDT(mandt); 						// ����_SAP CLIENT
				param.setVBELN(pspid);						// ������Ʈ��ȣ
				list = sso0060NS.selectListQtnum(param); 		// ��ȸ ���� ȣ��
			}

			// 2. ��ȸ�� ������ȣ, ��������, ������Ʈ��ȣ �缳��
			logger.debug("2. ��ȸ�� ������Ʈ��ȣ SET");
			if (list == null || list.size() == 0) {
				Sso0060 sso0060 = new Sso0060();
				sso0060.setVBELN(pspid);

				if (list == null) {
					list = new ArrayList<Sso0060>();
				}
				list.add(sso0060);
			} 
			else if (!"".equals(list.get(0).getVBELN().trim())) { // �������̺� ������Ʈ��ȣ ���� ��
				pspid = list.get(0).getVBELN().trim();	// ������Ʈ��ȣ
				qtnum = list.get(0).getQTNUM().trim();	// ������ȣ
				qtser = list.get(0).getQTSER().trim();	// ��������
			}

			// 3. �ű� ���ֻ��� ��û(SAVE)�� ��� �̹� ������ ���ְ����� üũ
			if ("SAVE".equals(cmd)) {
				logger.debug("3. �ű� ���ֻ��� ��û(SAVE)�� ��� �̹� ������ ���ְ����� üũ ");
				if (!"".equals(list.get(0).getVBELN().trim())) {	// �������̺� ������Ʈ��ȣ ���� ��
					// "CW10024", "�̹� ���ֻ����� �Ϸ�� ������ȣ �Դϴ�."
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW10024", "", "Y", "Y");
					logger.info("�̹� ���ֻ����� �Ϸ�� ������ȣ : [" + qtnum + "]");
				}
			} 	// end of if ("SAVE".equals(cmd))
			// 3. ���ֺ��� ��û(UPDT) �̰ų� ���ֻ��� ��û(DELE) �� ���  ��� ������Ʈ ������� ����ó��
			else if ("UPDT".equals(cmd) || "DELE".equals(cmd)) {
				logger.debug("1. ���ֺ��� ��û(UPDT) �̰ų� ���ֻ��� ��û(DELE) �� ���  ��� ������Ʈ ������� ����ó�� ");
				if ("".equals(list.get(0).getVBELN().trim()))		// �������̺� ������Ʈ��ȣ�� �ش��ϴ� �ڷ� ������ ��
				{
					// "CW00052", "��ġ�ϴ� �ڷᰡ �������� �ʽ��ϴ�.");
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW00052", "", "Y", "Y");
					logger.info("���ֻ������� ������ ������Ʈ : [" + qtnum + "]");
				}
			}

			//----------------------------------------------------------------
			//	4. ������ ���� ���
			//----------------------------------------------------------------
			if ( ds_error.getRowCount() == 0 ) {
				
				logger.debug("4. ������ ���� ��� ");
				// jco ����� ���
				sso0060NS.saveSapJcoProject(paramVO.getVariable("G_SYSID"), cmd, paramVO.getVariable("G_USER_ID"), param,
			                                ds_cond, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, 
			                                ds_error, resultVO);

			} else {
				if( "SAVE".equals(cmd)) {
					// �ӽ������ �����͸� ��� ���� ó���Ѵ�.
					sso0060NS.deleteTmpEstiMateTable(mandt, ds_cond);
				}
			}
		} catch (CommRfcException e) {
			logger.error("Sso0060SaveView CommRfcException ERROR!!!" + "");
			e.printStackTrace();     
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
	    } catch (Exception e) {
			logger.error("Sso0060SaveView Exception ERROR!!!" + "");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			//----------------------------------------------------------------
			//	3. ��� ����
			//----------------------------------------------------------------
			if( ds_error.getRowCount() > 0 ) {
				logger.debug("3. ��� ����");
				ds_error.setId		("ds_error");
				resultVO.addDataset	(ds_error);  	// ��������
			}
			model.addAttribute	("resultVO", resultVO);
		}

		logger.debug("Sso0060SaveProjectView end" + "");
		return view;
	}

    //=========================================================================================
    //  �Լ����  : �����ڷ� ���� �ӽ� ����
    //  �Ķ����  : MipParameterVO paramVO, Model model
    //  ���ϰ�    : MipResultVO resultVO
    //  ���ID    : UF015
    //  ��������  : ���� ���� ���� ����
    //=========================================================================================
	@RequestMapping(value="tmpSaveEstiMateCreate")
	public View Sso0060TmpSaveEstiMateCreateView(MipParameterVO paramVO, Model model) {

		logger.debug("Sso0060TmpSaveEstiMateCreateView START");

		// INPUT DATASET GET
		Dataset ds_cond 	 = paramVO.getDataset("ds_cond");        		// ����
		Dataset ds_dtl 		 = paramVO.getDataset("ds_dtl_save");        	// HEADER  (PARTNER ���� ����)
		Dataset ds_list_item = paramVO.getDataset("ds_list_item_save");  	// ITEM
		Dataset ds_list_bill = paramVO.getDataset("ds_list_bill_save");  	// BILLING PLAN
		Dataset ds_list_txt  = paramVO.getDataset("ds_list_txt_save");  	// TEXT

		// UI�� return�� error out dataset
		Dataset ds_error	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset

		// ���� GET
		String mandt  = paramVO.getVariable("G_MANDT");				    // IN_CLIENT
		String cmd 	  = DatasetUtility.getString(ds_cond,"CMD") ;		// ó������(DISP:��ȸ,SAVE:���ֻ���,UPDT:���ֺ���,DELE:���ֻ���)
		String qry_gb = DatasetUtility.getString(ds_cond,"QRY_GB") ;	// ��ȸ����(Q:������ȣ,P:������Ʈ)
		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// ������ȣ
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// ��������
		String pspid  = DatasetUtility.getString(ds_cond,"PSPID") ;		// ������Ʈ��ȣ

		// RFC ��� dataset�� return
		MipResultVO resultVO = new MipResultVO();

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO
		// sap client (���� : 310 or 910)
		logger.debug("paramVO.G_MANDT 	===>"+mandt	);		// CLIENT
		logger.debug("ds_cond.CMD   	===>"+cmd);		    // ó������(DISP:��ȸ,SAVE:���ֻ���,UPDT:���ֺ���,DELE:���ֻ���)
		logger.debug("ds_cond.QRY_GB   	===>"+qry_gb);		// ��ȸ����(Q:������ȣ,P:������Ʈ)
		logger.debug("ds_cond.QTNUM   	===>"+qtnum);		// ������ȣ
		logger.debug("ds_cond.QTSER   	===>"+qtser);		// ��������
		logger.debug("ds_cond.PSPID   	===>"+pspid );		// ������Ʈ��ȣ
		logger.debug("ds_dtl.getRowCount()	===>"		+ds_dtl.getRowCount() +"");
		logger.debug("ds_list_item.getRowCount()	===>"	+ds_list_item.getRowCount() +"");
		logger.debug("ds_list_txt.getRowCount()	===>"	+ds_list_txt.getRowCount() +"");
		logger.debug("ds_list_bill.getRowCount()	===>"	+ds_list_bill.getRowCount() +"");

		try {
			// Session GET
			sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			sso0060NS.saveTmpEstiMate(paramVO.getVariable("G_USER_ID"), mandt, ds_cond, ds_dtl, ds_list_item, ds_list_bill, ds_list_txt, ds_error);

		} catch (Exception e) {
			logger.error("Sso0060SaveView Exception ERROR!!!" + "");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			//----------------------------------------------------------------
			//	3. ��� ����
			//----------------------------------------------------------------
			logger.debug("3. ��� ����");
			ds_error.setId		("ds_error");
			resultVO.addDataset	(ds_error);  	// ��������
			model.addAttribute	("resultVO", resultVO);
		}

		logger.debug("Sso0060TmpSaveEstiMateCreateView end" + "");
		return view;
	}
	
    //=========================================================================================
    //  �Լ����  : �����ڷ� ���� �ӽ� ���� Data ���� 
    //  �Ķ����  : MipParameterVO paramVO, Model model
    //  ���ϰ�    : MipResultVO resultVO
    //  ���ID    : UF015
    //  ��������  : ���� ���� ���� ����
    //=========================================================================================
	@RequestMapping(value="tmpDeleteEstiMateCreate")
	public View Sso0060TmpDeleteEstiMateCreateView(MipParameterVO paramVO, Model model) {

		logger.debug("Sso0060TmpDeleteEstiMateCreateView START");

		// INPUT DATASET GET
		Dataset ds_cond 	 = paramVO.getDataset("ds_cond");        		// ����

		// UI�� return�� error out dataset
		Dataset ds_error	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset

		// ���� GET
		String mandt  = paramVO.getVariable("G_MANDT");				    // IN_CLIENT
		String cmd 	  = DatasetUtility.getString(ds_cond,"CMD") ;		// ó������(DISP:��ȸ,SAVE:���ֻ���,UPDT:���ֺ���,DELE:���ֻ���)
		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// ������ȣ
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// ��������
		String pspid  = DatasetUtility.getString(ds_cond,"PSPID") ;		// ������Ʈ��ȣ

		// RFC ��� dataset�� return
		MipResultVO resultVO = new MipResultVO();

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO
		// sap client (���� : 310 or 910)
		logger.debug("paramVO.G_MANDT 	===>"+mandt	);		// CLIENT
		logger.debug("ds_cond.CMD   	===>"+cmd);		    // ó������(DISP:��ȸ,SAVE:���ֻ���,UPDT:���ֺ���,DELE:���ֻ���)
		logger.debug("ds_cond.QTNUM   	===>"+qtnum);		// ������ȣ
		logger.debug("ds_cond.QTSER   	===>"+qtser);		// ��������
		logger.debug("ds_cond.PSPID   	===>"+pspid );		// ������Ʈ��ȣ

		try {
			// Session GET			
			sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			sso0060NS.deleteTmpEstiMateTable(mandt, ds_cond);
		} catch (Exception e) {
			logger.error("Sso0060SaveView Exception ERROR!!!" + "");
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			//----------------------------------------------------------------
			//	3. ��� ����
			//----------------------------------------------------------------
			logger.debug("3. ��� ����");
			ds_error.setId		("ds_error");
			resultVO.addDataset	(ds_error);  	// ��������
			model.addAttribute	("resultVO", resultVO);
		}

		logger.debug("Sso0060TmpDeleteEstiMateCreateView end" + "");
		return view;
	}	

	@RequestMapping(value="isStoOrder")
	public View isStoOrder(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsSto = paramVO.getDataset("ds_sto");

		OrderNo ordNo = new OrderNo(dsSto.getColumnAsString(0, "ordno"));
		OrderItem orderItem = new OrderItem(dsSto.getColumnAsString(0, "ordseq")); 
		ZWEB_SD_IS_STO_ORDERStub stub = (ZWEB_SD_IS_STO_ORDERStub) new SAPStub(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_LANG"))
				.create(ZWEB_SD_IS_STO_ORDERStub.class);
		ZWEB_SD_IS_STO_ORDERStub.ZWEB_SD_IS_STO_ORDER param;
		param = new ZWEB_SD_IS_STO_ORDERStub.ZWEB_SD_IS_STO_ORDER();
		param.setIV_ORDNO(ZWEB_SD_IS_STO_ORDERStub.Char10.Factory.fromString(ordNo.toString(), ""));
		param.setIV_ORDSEQ(new org.apache.axis2.databinding.types.UnsignedByte(orderItem.toString()));
		ZWEB_SD_IS_STO_ORDERStub.ZWEB_SD_IS_STO_ORDERResponse response;
		try {
			response = stub.zWEB_SD_IS_STO_ORDER(param);
			SapBool isStoOrder = new SapBool(response.getEV_ISSTO().toString());

			resultVO.addVariable("isStoOrder", isStoOrder.toString());
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	public void setStoInfoDpcpd(MipParameterVO paramVO) {
		Dataset dsSto = paramVO.getDataset("ds_sto");
		Vbeln vbeln = new OrderNo(dsSto.getColumnAsString(0, "vbeln"));
		String dpcpd = dsSto.getColumnAsString(0, "dpcpd");

		ZWEB_SD_SET_STO_INFOStub stub = (ZWEB_SD_SET_STO_INFOStub) new SAPStub(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_LANG"))
				.create(ZWEB_SD_SET_STO_INFOStub.class);
		ZWEB_SD_SET_STO_INFOStub.ZWEB_SD_SET_STO_INFO param;
		param = new ZWEB_SD_SET_STO_INFOStub.ZWEB_SD_SET_STO_INFO();
		param.setIV_VBELN(ZWEB_SD_SET_STO_INFOStub.Char10.Factory.fromString(vbeln.getValue(), ""));
		param.setIV_DPCPD(ZWEB_SD_SET_STO_INFOStub.Date.Factory.fromString(dpcpd.replaceFirst("(\\d{4})(\\d{2})(\\d+)", "$1-$2-$3"), ""));

		try {
			stub.zWEB_SD_SET_STO_INFO(param);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="findSopind")
	public View findSopind(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsSopind = paramVO.getDataset("ds_sopind");
		Dataset dsDtl = paramVO.getDataset("ds_dtl");
		
		String mandt = paramVO.getVariable("G_MANDT");
		String auart = DatasetUtility.getString(dsDtl, "AUART");
		
		try {
			Sso0060ParamVO param = new Sso0060ParamVO();
			param.setMANDT(mandt);
			param.setAUART(auart);
			
			sso0060NS.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			List<Sso0060> listSelectSopind = sso0060NS.searchSopind(param);
			dsSopind.deleteAll();
						
			for(int i=0; i<listSelectSopind.size(); i++) {
				dsSopind.appendRow();
				dsSopind.setColumn(i, "AUART", listSelectSopind.get(i).getAUART());
				dsSopind.setColumn(i, "SOPIND", listSelectSopind.get(i).getSOPIND());
			}
			
			resultVO.addDataset(dsSopind);
			
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			if( ds_error.getRowCount() > 0 ) {
				ds_error.setId("ds_error");
				resultVO.addDataset(ds_error);
			}			
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}
}
