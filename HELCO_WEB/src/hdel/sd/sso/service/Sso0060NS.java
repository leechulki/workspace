package hdel.sd.sso.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.sap.domain.Vbeln;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.ZSDT1074D;
import hdel.lib.domain.BAPIRET2;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT1074;
import hdel.lib.service.SrmService;
import hdel.sd.com.dao.ExchangeD;
import hdel.sd.com.dao.TestindD;
import hdel.sd.com.domain.ExchangeVO;
import hdel.sd.com.service.ExchangeS;
import hdel.sd.ses.dao.Ses0031D;
import hdel.sd.sso.dao.Sso0050D;
import hdel.sd.sso.dao.Sso0060ND;
import hdel.sd.sso.domain.Sso0060;
import hdel.sd.sso.domain.Sso0060ParamVO;
import hdel.sd.sso.domain.ZSDS0060;
import hdel.sd.sso.domain.ZSDS0060T;
import hdel.sd.sso.domain.ZSDS0061;
import hdel.sd.sso.domain.ZSDS0062;
import hdel.sd.sso.domain.ZSDT1100;
import hdel.sd.sso.domain.ZSDT1101;
import hdel.sd.sso.domain.ZSDT1102;
import hdel.sd.sso.domain.ZSDT1103;
import tit.service.sapjco3.RfcException;
import tit.util.DatasetUtility;
import webservice.stub.sap.SAPStub;
import webservice.stub.sap.ZWEB_SD_VALID_QTDATStub;

/*
******************************************************************************************
* ��      ��   : ���ֻ��� ��ȸ �� ����(Sso0060NS) Service
* ��  ��  ��   : �ڼ���
* �ۼ�  ����   : 2016.02.12
* ���ID       : UF014
* ----------------------------------------------------------------------------------------
* HISTORY      :  2016.02.12 ���� ���� �� �ڵ� �ڼ���
******************************************************************************************
*/

@Service
public class Sso0060NS extends SrmService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private Sso0060NJS sso0060NJS;

    private Sso0060ND sso0060NDao;
    private Ses0031D ses0031Dao;
    private Sso0050D sso0050Dao;
    private TestindD TestindDao;
    private ExchangeD ExchangeDao;
    private ZSDT1074D zsdt1074d;
    
	@Autowired
	private ExchangeS ExchangeS;
    
    public void createDao(SqlSession sqlSession) {
        sso0060NDao = sqlSession.getMapper(Sso0060ND.class);
        ses0031Dao = sqlSession.getMapper(Ses0031D.class);
        sso0050Dao = sqlSession.getMapper(Sso0050D.class);
        zsdt1074d = sqlSession.getMapper(ZSDT1074D.class);
        TestindDao = sqlSession.getMapper(TestindD.class);
        ExchangeDao = sqlSession.getMapper(ExchangeD.class);
    }
    
	// ������Ʈ��ȣ�� ������ȣ ��ȸ
	public List<Sso0060> selectListQtnum(Sso0060ParamVO paramV) {
		return sso0060NDao.selectListQtnum(paramV);
	}

    // ������ȣ�� �������� �������� �� ������Ʈ��ȣ ��ȸ
    public List<Sso0060> selectListQtser(Sso0060ParamVO paramV) {
        return sso0060NDao.selectListQtser(paramV);
    }

    //=========================================================================================
    //  �Լ����  : �ӽ� ���� ������ ���� ��ȸ
    //  �Ķ����  : String qry_gb, Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_list_item Dataset ds_list_bill, Dataset ds_list_txt
    //  ���ϰ�    : void( Dataset ds_dtl, Dataset ds_list_item, ds_error
    //  ���ID    : UF014
    //  ��������  : ���� ����� �Է� data ��ȸ(���ֻ����ӽ�����)
    //=========================================================================================
    public boolean getTmpEstimatDataset(String zprgflg, String qry_gb, Sso0060ParamVO param, Dataset ds_list_partner, Dataset ds_manager, Dataset ds_dtl, Dataset ds_list_item,
    		                              Dataset ds_list_bill, Dataset ds_list_txt, Dataset ds_error, MipResultVO resultVO) throws Exception {
    	boolean bIsData = false;

        // RFC INPUT PARAM DECLARE
        List<ZSDT1100>  list_head = null; // S/O ���� HEADER DATA
        List<ZSDT1101>  list_item = null; // S/O ���� ITEM DATA
        List<ZSDT1102>  list_bill = null; // S/O ���� BILLING PLAN
        List<ZSDT1103>  list_txt  = null; // S/O ���� TEXT

        // HEADER ��ȸ
        list_head = sso0060NDao.selectTmpQtHeader(param);
        if( list_head.size() > 0 ) {
        	bIsData = true;
            CallSAPHdel.moveListToDs(ds_dtl, ZSDT1100.class, list_head);
            
            // ds_dtl CNT ���� ������ ������
            ds_dtl.setColumn(0, "CNT", "1");
            
            // ITEM ��ȸ
            list_item = sso0060NDao.selectTmpQtItem(param);
            CallSAPHdel.moveListToDs(ds_list_item, ZSDT1101.class, list_item);

            // û����ȹ ��ȸ
            list_bill = sso0060NDao.selectTmpQtBiliingPlan(param);
            CallSAPHdel.moveListToDs(ds_list_bill, ZSDT1102.class, list_bill);

            // �ؽ�Ʈ ��ȸ
            list_txt = sso0060NDao.selectTmpQtText(param);
            CallSAPHdel.moveListToDs(ds_list_txt, ZSDT1103.class, list_txt);

            if (ds_error.getRowCount() == 0) {
	           	// �������� ��ȸ���� ������ ���� ��� ���������� �μ����� �߰�
	           	setEstimatAddVbVgr(zprgflg, param, ds_dtl);
	
	           	//�������� ��ȸ���� ������ ���� ��� ��Ʈ�� ���� �߰�
	           	setEstimatAddPartner(qry_gb, param, ds_dtl, ds_list_partner, ds_list_item, ds_list_bill);
	           	
	           	// �������� ��ȸ���� ������ ���� ��� ����� ���� �߰�
		       	setEstimatAddManager(param, ds_dtl, ds_manager);
            }
            
            resultVO.addDataset        (ds_dtl);              // HEADER
            resultVO.addDataset        (ds_list_partner);     // PARTNER
            resultVO.addDataset		   (ds_manager);		  // MANAGER
            resultVO.addDataset        (ds_list_item);        // ITEM
            resultVO.addDataset        (ds_list_bill);        // BILLING PLAN
            resultVO.addDataset        (ds_list_txt);         // TEXT
        }

        return bIsData;
    }

    //=========================================================================================
    //  �Լ����  : ������ȣ�� ���ֻ��� �ڷ� �ۼ��� ���� ������ ������ ��ȸ�Ѵ�.
    //  �Ķ����  : Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_list_item
    //  ���ϰ�    : void( Dataset ds_dtl, Dataset ds_list_item, ds_error
    //  ���ID    : UF014
    //  ��������  : ���� ����� �Է� data ��ȸ(���ֻ����ӽ�����)
    //=========================================================================================
    public void getEstimatDataset(String zprgflg, String qry_gb, Sso0060ParamVO param, Dataset ds_list_partner, Dataset ds_manager, Dataset ds_dtl, Dataset ds_list_item,
                                   Dataset ds_list_bill, Dataset ds_list_txt, Dataset ds_error, MipResultVO resultVO) throws Exception {
        List<Sso0060> list = null;
       
        list = sso0060NDao.selectListQtnumInfo(param);

        String sWAERK = "";
        String sAuart = "";
        String sQtdat = "";
        if (list != null && list.size() > 0)    {
            sWAERK = list.get(0).getWAERK();    // ȭ�����(�������)
            sAuart = list.get(0).getAUART();    //
            sQtdat = list.get(0).getQTDAT();    // ��������
        }

        CallSAPHdel.moveListToDs(ds_dtl, Sso0060.class, list);          // DATASET ����

        // 2.3 ���� DETAIL����(ǰ�� ��� ) ��ȸ
        list = null;
        list = sso0060NDao.selectListQtnumItemInfo(param);
        //CallSAPHdel.moveListToDs(ds_list_item, Sso0060.class, list);      // DATASET ����

        // 2.4 ��ȸ�� ���� DETAIL������ ǰ�� ��� ��ŭ ǰ�� ����� ������ϰ� �ݾ�, ������ ����� ������ SETTING�Ѵ�.
        // 2.4 ��ȸ�� ���� DETAIL������ ǰ�� ��� ��ŭ ǰ�� ����� ������ϰ� �ݾ�, ������ ����� ������ SETTING�Ѵ�.
        if ( list != null && list.size() > 0 )
        {
            int         nRow           = -1;          // ǰ�� ����Ÿ�� ���ȣ(0���� ����)
            Integer     posnr           = 0;          // ǰ���ȣ (100���� ����)
            String      matnr           = "";         // �����ȣ
            Double      netwr_bef       = null;      // ���� �� �ݾ�
            Double      wavwr_bef       = null;      // ���� �� ����
            Integer     znumber         = 0;          // ���� ���
            BigDecimal  netwr_div       = null;      // ���� �� �ݾ�
            BigDecimal  wavwr_div       = null;      // ���� �� ����
            Double      netwr_def       = null;      // ���� �� �ݾ� - sum(���� �� �ݾ�)
            Double      wavwr_def       = null;      // ���� �� ���� - sum(���� �� ����)

            /******************************************************************************
            * 2018.03.06 ����ȯ��, ����ȯ�� �������� START 
            ******************************************************************************/
            BigDecimal amtEx0031 = null;
            BigDecimal amtEx0050 = null;
            
            // ���� ��¥ ��������
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String sysDate = dateFormat.format(date);
            
            // ����ȯ�� = �������� ���� 
            String exchangeR = null;
			if (sWAERK != null && !"KRW".equals(sWAERK) && sAuart.substring(0,2).equals("ZE")) {
				exchangeR = ExchangeS.getExchangeRate(param.getMANDT(), "Q", sQtdat, sWAERK, "KRW");		
				if (Double.parseDouble(exchangeR) > 0 ) {
	                amtEx0031 = new BigDecimal(exchangeR);				 
				}
				
				// ����ȯ�� = ���� �������� ����
	            exchangeR = "0";
				exchangeR = ExchangeS.getExchangeRate(param.getMANDT(), "M", sysDate, sWAERK, "KRW");		
				if (Double.parseDouble(exchangeR) > 0 ) {
					amtEx0050 = new BigDecimal(exchangeR);
					
					BigDecimal exchangR_dec = new BigDecimal(exchangeR);
					ds_dtl.setColumn(0, "EXCHRT", exchangR_dec.setScale(1,BigDecimal.ROUND_HALF_UP).toString()); 
				}
			} else if (sAuart.substring(0,2).equals("ZT")) {
				
				String waerkBas = ExchangeDao.searchWaerkBase(param.getMANDT(), sQtdat);
				String waerkCon = ExchangeDao.searchWaerkBase(param.getMANDT(), sysDate);
				String message = "���� �� ���� ��ȭ(" + waerkBas + ") �� ���� �� ������ȭ (" + waerkCon +")�� ���� �մϴ�."; 
				if ( !waerkBas.equals(waerkCon) ){
					ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", 
							message+System.getProperty("line.separator")
							+"������ �ٽ� �����Ͻñ� �ٶ��ϴ�.",
							"Y", "Y");
					return;
				}
				
				amtEx0031 = getExchangeRateTrade(param.getMANDT(), "Q", sQtdat, sWAERK );				
				amtEx0050 = getExchangeRateTrade(param.getMANDT(), "M", sysDate, sWAERK );
				ds_dtl.setColumn(0, "EXCHRT", amtEx0050.setScale(4,BigDecimal.ROUND_HALF_UP).toString()); 

			}			
            /******************************************************************************
            * 2018.03.06 ����ȯ��, ����ȯ�� �������� END  
            ******************************************************************************/
			
/* -----< 2018.03.06 ȯ�� ���� ������ȭExchangeS.getExchangeRate ó���� ���� �ּ� by mj.Shin 		Start
            /******************************************************************************
            * 2013.02.20 ���� ����ȯ�� �������� START
            ******************************************************************************
            BigDecimal amtEx0031 = null;
            BigDecimal amtEx0050 = null;
            // ���� ��¥ ��������
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String sysDate = dateFormat.format(date);
            
            if ( sAuart.substring(0,2).equals("ZE") && !sWAERK.equals("KRW") ) {
                // ���� ��¥ ��������
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String sysDate = dateFormat.format(date);
				
                // ���� ����ȯ�� ��������
                Ses0031 ses0031 = ses0031Dao.selectNewExchange(param.getMANDT(), sQtdat);
                if ( ses0031 == null ) {
                    throw new BizException("CE10007");
                }
                if ( "USD".equals(sWAERK.toUpperCase()) ) {
                    String strAmtEx0031 = ses0031.getKrwusd();
                    if ( strAmtEx0031 == null ) {
                        throw new BizException("CE10007");
                    } else if ( strAmtEx0031.trim() == "" ) {
                        throw new BizException("CE10007");
                    } else if ( Double.parseDouble(strAmtEx0031) <= 0 ) {
                        throw new BizException("CE10007");
                    }
                    amtEx0031 = new BigDecimal(strAmtEx0031);
                } else if ( "JPY".equals(sWAERK.toUpperCase()) ) {
                    String strAmtEx0031 = ses0031.getKrwjpy();
                    if ( strAmtEx0031 == null ) {
                        throw new BizException("CE10007");
                    } else if ( strAmtEx0031.trim() == "" ) {
                        throw new BizException("CE10007");
                    } else if ( Double.parseDouble(strAmtEx0031) <= 0 ) {
                        throw new BizException("CE10007");
                    }
                    amtEx0031 = new BigDecimal(strAmtEx0031);
                } else if ( "EUR".equals(sWAERK.toUpperCase()) ) {
                    String strAmtEx0031 = ses0031.getKrweur();
                    if ( strAmtEx0031 == null ) {
                        throw new BizException("CE10007");
                    } else if ( strAmtEx0031.trim() == "" ) {
                        throw new BizException("CE10007");
                    } else if ( Double.parseDouble(strAmtEx0031) <= 0 ) {
                        throw new BizException("CE10007");
                    }
                    amtEx0031 = new BigDecimal(strAmtEx0031);
                }
				
                // ���� ����ȯ�� ��������
                Sso0050ParamVO sso0050ParamVO = new Sso0050ParamVO();
                sso0050ParamVO.setMANDT(param.getMANDT());
                sso0050ParamVO.setTCURRDATE(sysDate);

                List<Sso0050VO> listEx0050 = sso0050Dao.selectListExchange(sso0050ParamVO);
                if ( listEx0050.size() > 0 ) {
                    if ( "USD".equals(sWAERK.toUpperCase()) ) {
                        String strAmtEx0050 = listEx0050.get(0).getKRWUSD();
                        if ( strAmtEx0050 == null ) {
                            throw new BizException("CE10007");
                        } else if ( strAmtEx0050.trim() == "" ) {
                            throw new BizException("CE10007");
                        } else if ( Double.parseDouble(strAmtEx0050) <= 0 ) {
                            throw new BizException("CE10007");
                        }
                        amtEx0050 = new BigDecimal(strAmtEx0050);
                    } else if ( "JPY".equals(sWAERK.toUpperCase()) ) {
                        String strAmtEx0050 = listEx0050.get(0).getKRWJPY();
                        if ( strAmtEx0050 == null ) {
                            throw new BizException("CE10007");
                        } else if ( strAmtEx0050.trim() == "" ) {
                            throw new BizException("CE10007");
                        } else if ( Double.parseDouble(strAmtEx0050) <= 0 ) {
                            throw new BizException("CE10007");
                        }
                        amtEx0050 = new BigDecimal(strAmtEx0050);
                    } else if ( "EUR".equals(sWAERK.toUpperCase()) ) {
                        String strAmtEx0050 = listEx0050.get(0).getKRWEUR();
                        if ( strAmtEx0050 == null ) {
                            throw new BizException("CE10007");
                        } else if ( strAmtEx0050.trim() == "" ) {
                            throw new BizException("CE10007");
                        } else if ( Double.parseDouble(strAmtEx0050) <= 0 ) {
                            throw new BizException("CE10007");
                        }
                        amtEx0050 = new BigDecimal(strAmtEx0050);
                    }
                }
                if ( amtEx0050 == null ) {
                    throw new BizException("CE10007");
                }
            }

            /******************************************************************************
            * 2013.02.20 ���� ����ȯ�� �������� END
            ******************************************************************************
			
-----> 2018.03.06 ȯ�� ���� ������ȭExchangeS.getExchangeRate ó���� ���� �ּ� by mj.Shin 		End */
				
            String qtnum = param.getQTNUM();

            for( int i = 0; i < list.size(); i++ )
            {
                // 2.4.1 ���� SET
                netwr_bef     = list.get(i).getNETWR().doubleValue() ;                        // ���� �� �ݾ�
                wavwr_bef     = list.get(i).getWAVWR().doubleValue() ;                        // ���� �� ����
                if( qtnum.substring(0,1).equals("J") || qtnum.substring(0,1).equals("G") || qtnum.substring(0,1).equals("F")){
                    znumber     = 1;                                        // ���� ���
                } else {
                    znumber     = list.get(i).getZNUMBER();                                   // ���� ���
                }
                matnr         = list.get(i).getMATNR().toString();                            // �����ȣ
                netwr_div     = new BigDecimal(list.get(i).getNETWR().doubleValue()/znumber);   // ���� �� �ݾ�
                wavwr_div     = new BigDecimal(list.get(i).getWAVWR().doubleValue()/znumber);    // ���� �� ����

                /******************************************************************************
                * 2013.02.20 ���� ����ȯ�� ���� START
                ******************************************************************************/
                if ( sAuart.substring(0,2).equals("ZE") || sAuart.substring(0,2).equals("ZT") ) {
                    if ( amtEx0031 != null && amtEx0050 != null ) {
                        wavwr_div = new BigDecimal(wavwr_div.doubleValue()*amtEx0031.doubleValue());
                        wavwr_div = new BigDecimal(wavwr_div.doubleValue()/amtEx0050.doubleValue());

                        // 2013.03.19 ���� �� ������ ȯ���� ������ �ݾ��� ����Ѵ�.
                        wavwr_bef = wavwr_bef*amtEx0031.doubleValue()/amtEx0050.doubleValue();
                        wavwr_bef = Double.valueOf((new java.text.DecimalFormat("#.##")).format(wavwr_bef));
                    }
                }
                /******************************************************************************
                * 2013.02.20 ���� ����ȯ�� ���� END
                ******************************************************************************/

                // 2013.01.17 ������ ��� USD�� ��� �Ҽ��� ���� 3�ڸ� ����
                if ("USD".equals(sWAERK) || "EUR".equals(sWAERK) || "MYR".equals(sWAERK))    { // 2013.02.26 USD,EUR,MYR�� �Ҽ� 2�ڸ�(����), JPY-�Ҽ� 0�ڸ� (�ݿø�), KRW-�Ҽ� 0�ڸ�
                    netwr_div    = netwr_div.setScale(2, RoundingMode.DOWN);                   // �ݾ��� ���������� ����
                    wavwr_div   = wavwr_div.setScale(2, RoundingMode.DOWN);                    // ������ ���������� ����
                } else if ("JPY".equals(sWAERK)) {
                    netwr_div    = netwr_div.setScale(0, RoundingMode.HALF_UP);               // �ݾ��� �Ҽ� 1�ڸ����� �ݿø�
                    wavwr_div   = wavwr_div.setScale(0, RoundingMode.HALF_UP);                // ������ �Ҽ� 1�ڸ����� �ݿø�
                } else {
                    netwr_div    = netwr_div.setScale(0, RoundingMode.DOWN);                   // �ݾ��� ���������� ����
                    wavwr_div   = wavwr_div.setScale(0, RoundingMode.DOWN);                    // ������ ���������� ����
                }

                // 2.4.2 ���� ��� ��ŭ Loop
                for( int j = 0; j < znumber; j++ )
                {
                    nRow++;                                                                     // ds_list_item ���ȣ
                    posnr = posnr + 100;                                                        // ǰ���ȣ
                    ds_list_item.appendRow();                                                   // ���߰�
                    ds_list_item.setColumn(nRow, "QTSEQ", list.get(i).getQTSEQ().toString());   // �����Ϸù�ȣ
                    ds_list_item.setColumn(nRow, "POSNR", StringUtils.leftPad(Integer.toString(posnr), 6, "0")); // ǰ���ȣ
                    ds_list_item.setColumn(nRow, "MATNR", matnr);                               // �����ȣ
                    ds_list_item.setColumn(nRow, "NETWR", netwr_div.doubleValue());             // ���ұݾ�
                    ds_list_item.setColumn(nRow, "WAVWR", wavwr_div.doubleValue());             // ���ҿ���
                    
                    // 2020 �귣��
                    ds_list_item.setColumn(nRow, "BRNDCD", list.get(i).getBRNDCD());
                    ds_list_item.setColumn(nRow, "BRNDSEQ", list.get(i).getBRNDSEQ());
                }     // end of for( int j = 0; j < znumber; j++ )

                // 2.4.3  ���� ����� 1 ���� ū ��� ǰ�� ������ ���� �ݾ�, ���� �缳��
                //if (znumber > 1)
                //{ //��� = 1��  ��쵵 BigDecimal ���� �� ���� �߻�(�ؿܿ���) - 2014.01.28 ,�Ź���
                    // ���� ����
                    netwr_def = netwr_bef - (netwr_div.doubleValue() * znumber.doubleValue());    // �ݾ� ����
                    wavwr_def = wavwr_bef - (wavwr_div.doubleValue() * znumber.doubleValue());    // ���� ����
                    // �ݾ�, ���� �缳�� (���׸�ŭ �߰��� sumó��)
                    ds_list_item.setColumn(nRow, "NETWR", netwr_div.doubleValue() + netwr_def);  // �ݾ�
                    ds_list_item.setColumn(nRow, "WAVWR", wavwr_div.doubleValue() + wavwr_def);  // ����
                //}
            }         // end of for( int i = 0; i < list.size(); i++ )
            
            
            
        }             // end of if ( list != null && list.size() > 0)

        if (ds_error.getRowCount() == 0) {
           	// �������� ��ȸ���� ������ ���� ��� ���������� �μ����� �߰�
           	setEstimatAddVbVgr(zprgflg, param, ds_dtl);

           	//�������� ��ȸ���� ������ ���� ��� ��Ʈ�� ���� �߰�
           	setEstimatAddPartner(qry_gb, param, ds_dtl, ds_list_partner, ds_list_item, ds_list_bill);
           	
           	// �������� ��ȸ���� ������ ���� ��� ����� ���� �߰�
	       	setEstimatAddManager(param, ds_dtl, ds_manager);
        }

        resultVO.addDataset        (ds_dtl);              // HEADER
        resultVO.addDataset        (ds_list_partner);     // PARTNER
        resultVO.addDataset		   (ds_manager);		  // MANAGER
        resultVO.addDataset        (ds_list_item);        // ITEM
        resultVO.addDataset        (ds_list_bill);        // BILLING PLAN
        resultVO.addDataset        (ds_list_txt);         // TEXT
    }

    //=========================================================================================
    //  �Լ����  : ������ȣ�� ���ֻ��� �ڷ� �ۼ��� ���� ������ ������ SAP XML ������� ȣ���Ѵ�.
    //  �Ķ����  : String cmd, String zprgflg, String qry_gb, Sso0060ParamVO param, Dataset ds_list_partner, Dataset ds_dtl, Dataset ds_list_item
    //              Dataset ds_list_bill, Dataset ds_list_txt, Dataset ds_error
    //  ���ϰ�    : void( Dataset ds_dtl, Dataset ds_list_item, ds_list_bill, ds_list_txt, ds_error )
    //  ���ID    : UF014
    //  ��������  : ���� ����� �Է� data ��ȸ(���ֻ����ӽ�����)
    //=========================================================================================
    public void getSapXmlEstimatDataset(String sysid, String cmd, String zprgflg, String qry_gb, Sso0060ParamVO param, Dataset ds_list_partner,
    		                             Dataset ds_dtl, Dataset ds_list_item,
                                         Dataset ds_list_bill, Dataset ds_list_txt, Dataset ds_error,
                                         MipResultVO resultVO) throws CommRfcException, Exception {
        // RFC INPUT PARAM DECLARE
        ZSDS0060[]  list_head	= new ZSDS0060[]{};      // S/O ���� HEADER DATA
        ZSDS0061[]  list_item	= new ZSDS0061[]{};      // S/O ���� ITEM DATA
        ZSDS0062[]  list_bill	= new ZSDS0062[]{};      // S/O ���� BILLING PLAN
        ZSDS0060T[]	list_txt	= new ZSDS0060T[]{};      // S/O ���� TEXT
        BAPIRET2[]	bapiret2	= new BAPIRET2[]{};

        // 3.3.1 RFC INPUT SET (�Ķ���� ��������)
        Object obj[] = new Object[]{
                                      list_txt            // O_��� : ZSDS0060T : TEXT
                                    , list_bill            // O_��� : ZSDS0062  : BILLING PLAN
                                    , cmd               // ó������ : DISP(��ȸ), SAVE(����), UPDT(����)
                                    , param.getVBELN()    // ������Ʈ��ȣ
                                    , param.getQTNUM()    // ������ȣ
                                    , param.getQTSER()    // ��������
                                    , list_item            // O_��� : ZSDS0061  : ITEM
                                    , bapiret2
                                    , list_head            // O_��� : ZSDS0060  : HEADER
                            };

        // 3.3.2 RFC CALL
        SapFunction stub = CallSAP.callSap(sysid, param.getMANDT() , "hdel.sd.sso.domain.ZWEB_SD_CREATE_SO" , obj, false);

        // 3.3.4 RFC ȣ����(T_ITAB,T_ITAB2)�� out dataset(ds)�� �ű��
        CallSAP.moveObj2Ds(ds_dtl        , stub.getOutput("T_ITAB"));         // HEADER
        CallSAP.moveObj2Ds(ds_list_item  , stub.getOutput("P_ITAB"));         // ITEM
        CallSAP.moveObj2Ds(ds_list_bill  , stub.getOutput("B_ITAB"));         // BILLING PLAN
        CallSAP.moveObj2Ds(ds_list_txt   , stub.getOutput("BIGO"));           // TEXT
        
        // 3.3.6 RFC ȣ���� ��������
		if (((BAPIRET2[])stub.getOutput("TT_BAPIRET2")).length > 0) {
			for(BAPIRET2 bp2 : (BAPIRET2[])stub.getOutput("TT_BAPIRET2")) {
				if(bp2.getTYPE().equals("S"))
					continue;
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", bp2.getMESSAGE().toString(), "Y", "Y");
				break;
			}
			//SAP RFC ������ return message Type�� S�θ� �����Ǿ��µ��� ó�������� ��찡 �߻��ϱ� ������ �ӽ���ġ
			if(ds_error.getRowCount() == 0) {
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", ((BAPIRET2[])stub.getOutput("TT_BAPIRET2"))[0].getMESSAGE().toString(), "Y", "Y");
			}
		} else {
/*			2018.03.30  ȯ�� ���� ������ȭ ExchangeS.searchExchangeRate ó���� ���� �ּ� by mj.Shin 		Start
 * 			���� : ���ݰ������� ȯ�� �о�� / ���� : �������� ȯ�� ��ȸ / ���� : ���ݰ������� ������Ʈ ��� �� ������ ������ ���ڷ� �����.  
			//ȯ��ǥ�� 2015.03.19
			Sso0060 Vo = sso0060NDao.selectExchangeRate(param);
            for(int i = 0; i< ds_dtl.getRowCount(); i++) {
                //ds_dtl.setColumn(i, "WAERK", ds_dtl.getColumn(i, "WAERK")+ "/" + Vo.getEXRATE().setScale(1,BigDecimal.ROUND_UP));
            	ds_dtl.setColumn(i, "EXCHRT", Vo.getEXRATE().setScale(1,BigDecimal.ROUND_UP).toString());
            }	
*/
			String Auart = ds_dtl.getColumnAsString(0,"AUART");
			String Waerk = ds_dtl.getColumnAsString(0, "WAERK");
			BigDecimal exchangR_dec = new BigDecimal("1.0");
			ExchangeVO exchangeRate = new ExchangeVO();
			ExchangeVO paramExchangeVO = new ExchangeVO();
			String Bstdk = dateReplace(ds_dtl.getColumnAsString(0, "BSTDK"));
			
			if (Auart.substring(0,2).equals("ZE")) {
				paramExchangeVO.setMandt(param.getMANDT());
				paramExchangeVO.setKurst("M");
				paramExchangeVO.setBasedt(Bstdk);
				paramExchangeVO.setFcurr(Waerk);
				paramExchangeVO.setTcurr("KRW");
				
				if (Waerk != null && !"KRW".equals(Waerk) ) {
					exchangeRate = ExchangeDao.selectSoExchange(paramExchangeVO);
					if (exchangeRate == null) {
						exchangR_dec = new BigDecimal("0.0");
					} else {
						exchangR_dec = new BigDecimal(exchangeRate.getUkurs());
					}
					ds_dtl.setColumn(0, "EXCHRT", exchangR_dec.setScale(1,BigDecimal.ROUND_UP).toString());
				} 
			} else if (Auart.substring(0,2).equals("ZT")) {
				exchangR_dec = getExchangeRateTrade(param.getMANDT(), "M", Bstdk, Waerk);
				ds_dtl.setColumn(0, "EXCHRT", exchangR_dec.setScale(4,BigDecimal.ROUND_UP).toString());
			}
			

//			2018.03.30 ȯ�� ���� ������ȭ ExchangeS.searchExchangeRate ó���� ���� �ּ� by mj.Shin 		End 
			
        	// �������� ��ȸ���� ������ ���� ��� ���������� �μ����� �߰�
	       	setEstimatAddVbVgr(zprgflg, param, ds_dtl);
	       	//�������� ��ȸ���� ������ ���� ��� ��Ʈ�� ���� �߰�
	       	setEstimatAddPartner(qry_gb, param, ds_dtl, ds_list_partner, ds_list_item, ds_list_bill);

	       	ZSDT1074 zsdt1074 = zsdt1074d.select(param.getMANDT(), new Vbeln(param.getVBELN()));
	       	if(zsdt1074 != null) {
		       	ds_dtl.setColumn(0, "LHCHK"	, zsdt1074.getLh());
		       	ds_dtl.setColumn(0, "KUNAPRV", zsdt1074.getKunaprv());
	       	}
        }

        resultVO.addDataset        (ds_dtl);              // HEADER
        resultVO.addDataset        (ds_list_partner);     // PARTNER
        resultVO.addDataset        (ds_list_item);        // ITEM
        resultVO.addDataset        (ds_list_bill);        // BILLING PLAN
        resultVO.addDataset        (ds_list_txt);         // TEXT
        resultVO.addDataset        (ds_error);	          // ERROR 
    }

    //=========================================================================================
    //  �Լ����  : �������� ��ȸ���� ������ ���� ��� ���������� �μ����� �߰�
    //  �Ķ����  : String zprgflg, Sso0060ParamVO param, Dataset ds_dtl
    //  ���ID    : UF014
    //  ��������  : ���� ����� �Է� data ��ȸ(���ֻ����ӽ�����)
    //=========================================================================================
    public void setEstimatAddVbVgr(String zprgflg, Sso0060ParamVO param, Dataset ds_dtl) throws Exception {

    	// 4.1 HEAD ���  dataset�� ������Ʈ��ȣ, ������ȣ, �������� �缳��
		ds_dtl.setColumn(0, "QTNUM"	, param.getQTNUM());	// ������ȣ
		ds_dtl.setColumn(0, "QTSER"	, param.getQTSER());	// ��������
		ds_dtl.setColumn(0, "VBELN"	, param.getVBELN());	// ������Ʈ��ȣ
		// 2012.10.19 ������������ڵ� �߰��ݿ�
		ds_dtl.setColumn(0, "ZPRGFLG", zprgflg);	// �����������

		// 4.2 HEAD ���  dataset�� �μ���, ����  SET
		param.setVKBUR(ds_dtl.getColumnAsString(0, "VKBUR"));	// �μ��ڵ�
		param.setVKGRP(ds_dtl.getColumnAsString(0, "VKGRP"));	// ���ڵ�
		List<Sso0060> list = null;
		
		list = sso0060NDao.selectListVbVg(param);					// �μ���, ���� ��ȸ
		if (list != null && list.size()>0)
		{
			ds_dtl.setColumn(0, "VKBUR_NM"	, list.get(0).getVKBUR_NM().toString());	// �μ���
			ds_dtl.setColumn(0, "VKGRP_NM"	, list.get(0).getVKGRP_NM().toString()); 	// ����
		}

		// 4.3 HEAD ���  dataset�� ��¥�����׸� �缳��
		ds_dtl.setColumn(0, "CONTR_DA"		, dateReplace(ds_dtl.getColumnAsString(0, "CONTR_DA"))); // �����
		ds_dtl.setColumn(0, "BSTDK"			, dateReplace(ds_dtl.getColumnAsString(0, "BSTDK"	))); // ������
		ds_dtl.setColumn(0, "RECAD_DA"		, dateReplace(ds_dtl.getColumnAsString(0, "RECAD_DA"))); // ���ֽ�����
		ds_dtl.setColumn(0, "VDATU"			, dateReplace(ds_dtl.getColumnAsString(0, "VDATU"	))); // ��ǰ��û��
		ds_dtl.setColumn(0, "COMPL_DA"		, dateReplace(ds_dtl.getColumnAsString(0, "COMPL_DA"))); // �ǹ��ذ���
		ds_dtl.setColumn(0, "FINAL_DA"		, dateReplace(ds_dtl.getColumnAsString(0, "FINAL_DA"))); // �����Ϸ���
		ds_dtl.setColumn(0, "HB_FROM"		, dateReplace(ds_dtl.getColumnAsString(0, "HB_FROM"	))); // ���ں����Ⱓ_FROM
		ds_dtl.setColumn(0, "HB_TO"			, dateReplace(ds_dtl.getColumnAsString(0, "HB_TO"	))); // ���ں����Ⱓ_FROM
		ds_dtl.setColumn(0, "ACHDT"			, dateReplace(ds_dtl.getColumnAsString(0, "ACHDT"	))); // ���� �㰡���� 
		ds_dtl.setColumn(0, "SOPDT"			, dateReplace(ds_dtl.getColumnAsString(0, "SOPDT"	))); // ��������
		ds_dtl.setColumn(0, "SOP_Y_REGDT"	, dateReplace(ds_dtl.getColumnAsString(0, "SOP_Y_REGDT"	))); // �����������
    }

    //=========================================================================================
    //  �Լ����  : �������� ��ȸ���� ������ ���� ��� ��Ʈ�� ���� �߰�
    //  �Ķ����  : String qry_gb, Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_list_partner, Dataset ds_list_item, Dataset ds_list_bill
    //  ���ID    : UF014
    //  ��������  : ���� ����� �Է� data ��ȸ(���ֻ����ӽ�����)
    //=========================================================================================
    public void setEstimatAddPartner(String qry_gb, Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_list_partner, Dataset ds_list_item, Dataset ds_list_bill) throws Exception {
		// 4.4 ��Ʈ�������� ��ȸ�Ͽ�  ��� dataset�� ����
		String kunnr_rg = "";	// �Ǹ�ó
		String kunnr_we = "";	// ��ǰó
		String kunnr_z1 = "";	// �븮��
		String kunnr_z2 = "";	// �����
		String kunnr_z3 = "";	// ������������
		for (int i = 0; i < 6; i++)
		{
			List<Sso0060> list = null;
			ds_list_partner.appendRow();

			if (i == 0)	// �Ǹ�ó
			{
				kunnr_rg = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_RG"));
				if (StringUtils.isNotBlank(kunnr_rg))
				{
					param.setCODE(kunnr_rg);									// ����_�Ǹ�ó�ڵ�
					list = sso0060NDao.selectListKunnrRg(param);					// �Ǹ�ó���� ��ȸ
				}
				ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_RG");		// ��Ʈ�ʱ����÷�ID
			}

			else if (i == 1)	// ������  �߰� kdh
			{
				if ("Q".equals(qry_gb))
				{
						kunnr_rg = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_RG"));
						if (StringUtils.isNotBlank(kunnr_rg))
						{
							param.setCODE(kunnr_rg);									// ����_�Ǹ�ó�ڵ�
							list = sso0060NDao.selectListKunnrRg(param);					// �Ǹ�ó���� ��ȸ
						}
						ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_RG2");		// ��Ʈ�ʱ����÷�ID
				}
				else if ("P".equals(qry_gb))
				{
						kunnr_rg = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_RG2"));
						if (StringUtils.isNotBlank(kunnr_rg))
						{
							param.setCODE(kunnr_rg);									// ����_�Ǹ�ó�ڵ�
							list = sso0060NDao.selectListKunnrRg(param);					// �Ǹ�ó���� ��ȸ
						}
						ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_RG2");		// ��Ʈ�ʱ����÷�ID
				}
			}

			else if (i == 2)	// ��ǰó
			{
				kunnr_we = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_WE"));
				if (StringUtils.isNotBlank(kunnr_we))
				{
					param.setCODE(kunnr_we);									// ����_��ǰó�ڵ�
					list = sso0060NDao.selectListKunnrWe(param);					// ��ǰó���� ��ȸ
				}
				ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_WE");		// ��Ʈ�ʱ����÷�ID
			}

			else if (i == 3)	// �븮��
			{
				kunnr_z1 = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_Z1"));
				if (StringUtils.isNotBlank(kunnr_z1))
				{
					param.setCODE(kunnr_z1);									// ����_�븮�������ڵ�
					list = sso0060NDao.selectListKunnrZ1(param);					// �븮���������� ��ȸ
				}
				ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_Z1");		// ��Ʈ�ʱ����÷�ID
			}

			else if (i == 4)	// �����
			{
				kunnr_z2 = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_Z2"));
				if (StringUtils.isNotBlank(kunnr_z2))
				{
					param.setCODE(kunnr_z2);									// ����_����������ڵ�
					list = sso0060NDao.selectListKunnrZ2(param);					// ������������� ��ȸ
				}
				ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_Z2");		// ��Ʈ�ʱ����÷�ID
			}

			else if (i == 5)	// ������������
			{
				kunnr_z3 = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_Z3"));
				if (StringUtils.isNotBlank(kunnr_z3))
				{
					param.setCODE(kunnr_z3);									// ����_�����������������ڵ�F
					list = sso0060NDao.selectListKunnrZ3(param);					// �������������������� ��ȸ
				}
				ds_list_partner.setColumn(i, "PARTNER_GB"	, "KUNNR_Z3");		// ��Ʈ�ʱ����÷�ID
			}
			// ��ȸ�� �ڷᰡ ������ ��� ���� SET
			if (list != null && list.size() > 0)
			{
				ds_list_partner.setColumn(i, "PARTNER_CD"	, list.get(0).getCODE().toString());
				ds_list_partner.setColumn(i, "PARTNER_NM"	, list.get(0).getNAME().toString());
				ds_list_partner.setColumn(i, "PARTNER_INFO"	, list.get(0).getINFO().toString());
			}
		}  // end of for (int i=0; i<5; i++)

		// 4.5 ITEM ���  dataset�� �÷� �߰� �� ��¥�����׸� �缳��
		for (int i = 0; i < ds_list_item.getRowCount(); i++)
		{
			ds_list_item.setColumn(i, "CHK"	 , "0");
			ds_list_item.setColumn(i, "FLAG" , "" );
			ds_list_item.setColumn(i, "EDATU", dateReplace(ds_list_item.getColumnAsString(i, "EDATU"))); // ������
			ds_list_item.setColumn(i, "CONDA", dateReplace(ds_list_item.getColumnAsString(i, "CONDA"))); // ���ǳ�����
			ds_list_item.setColumn(i, "STADA", dateReplace(ds_list_item.getColumnAsString(i, "STADA"))); // ����������
			ds_list_item.setColumn(i, "ZRMDA", dateReplace(ds_list_item.getColumnAsString(i, "ZRMDA"))); // �����ϷΌ����
		}

		// 4.6 BILLING ���  dataset�� ��¥�����׸� �缳�� �� �������� ����
		for (int i = 0; i < ds_list_bill.getRowCount(); i++)
		{
			ds_list_bill.setColumn(i, "FKDAT", dateReplace(ds_list_bill.getColumnAsString(i, "FKDAT"))); // û����ȹ��
			// �ǸŹ���ǰ���ȣ�� ���� ���
			if ("".equals(ds_list_bill.getColumnAsString(i, "POSNR"))
				 || "000000".equals(ds_list_bill.getColumnAsString(i, "POSNR"))
				)
			{
				ds_list_bill.setColumn(i, "BILL_GB", "H"); // ������Ʈ����
			}
			// �ǸŹ���ǰ���ȣ�� �ִ� ���
			else
			{
				ds_list_bill.setColumn(i, "BILL_GB", "P"); // ȣ�⺰����
			}
		}
    }

    //=========================================================================================
    //  �Լ����  : ���� ���� ����,����
    //  �Ķ����  : String cmd, String userId, Sso0060ParamVO 	param
    //              Dataset ds_cond, Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
    //              Dataset ds_error
    //  ���ϰ�    : void( Dataset ds_dtl, Dataset ds_list_item, ds_list_bill, ds_list_txt, ds_error )
    //  ���ID    : UF014, UF015
    //  ��������  : ���� ���� RFC API ����
    //=========================================================================================
    public Vbeln saveSapXmlProject(String sysid, String cmd, String userId, Sso0060ParamVO 	param,
    		                       Dataset ds_cond, Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
    		                       Dataset ds_error) throws RfcException, Exception {
    	Vbeln vbeln = new Vbeln();
    	// RFC INPUT PARAM DECLARE
		// input dataset�� CLASS() �� �ű��
    	ZSDS0060[] 	list_head 	= (ZSDS0060[])CallSAPHdel.moveDs2Obj2(ds_dtl		, ZSDS0060.class, "");  // HEADER
    	ZSDS0061[] 	list_item 	= (ZSDS0061[])CallSAPHdel.moveDs2Obj2(ds_list_item	, ZSDS0061.class, "");	// ITEM
    	ZSDS0062[] 	list_bill 	= (ZSDS0062[])CallSAPHdel.moveDs2Obj2(ds_list_bill	, ZSDS0062.class, ""); 	// BILLING PLAN
    	ZSDS0060T[] list_txt 	= (ZSDS0060T[])CallSAPHdel.moveDs2Obj2(ds_list_txt	, ZSDS0060T.class, "");	// TEXT
        BAPIRET2[]	bapiret2	= new BAPIRET2[]{};

    	// 2013.02.25 �Ҽ������� �ڸ��� ���� ������ ���� ���� �ٽ� �־���
    	for ( int i = 0 ; i < list_item.length ; i++ ) {
    		BigDecimal bd = new BigDecimal(ds_list_item.getColumnAsString(i, "WAVWR"));
    		list_item[i].setWAVWR(new BigDecimal(bd.toPlainString()));
    	}

		Object obj[] = new Object[] {
				  list_txt										// O_��� : ZSDS0060T : TEXT
				, list_bill										// O_��� : ZSDS0062 : BILLING PLAN
				, cmd											// ó������(DISP:��ȸ,SAVE:����,UPDT:����,DELE:����)
				, DatasetUtility.getString(ds_cond, "PSPID")  	// ������Ʈ��ȣ
				, DatasetUtility.getString(ds_cond, "QTNUM")	// ������ȣ
				, DatasetUtility.getString(ds_cond, "QTSER")	// ��������
				, list_item										// O_��� : ZSDS0061 : ITEM
				, bapiret2
				, list_head										// O_��� : ZSDS0060 : HEADER
		};

		SapFunction stub = CallSAP.callSap(sysid, param.getMANDT()
										   , "hdel.sd.sso.domain.ZWEB_SD_CREATE_SO"
										   , obj, false);

		if (((BAPIRET2[])stub.getOutput("TT_BAPIRET2")).length > 0) {
			for(BAPIRET2 bp2 : (BAPIRET2[])stub.getOutput("TT_BAPIRET2")) {
				if(bp2.getTYPE().equals("S"))
					continue;
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", bp2.getMESSAGE().toString(), "Y", "Y");
				break;
			}
			//SAP RFC ������ return message Type�� S�θ� �����Ǿ��µ��� ó�������� ��찡 �߻��ϱ� ������ �ӽ���ġ
			if(ds_error.getRowCount() == 0) {
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", ((BAPIRET2[])stub.getOutput("TT_BAPIRET2"))[0].getMESSAGE().toString(), "Y", "Y");
			}
		} else {
			vbeln = new Vbeln(((ZSDS0060[])stub.getOutput("T_ITAB"))[0].getVBELN());
			ds_dtl.setColumn(0, "VBELN", vbeln.toString());

			ZSDT1074 zsdt1074 = new ZSDT1074();
			zsdt1074.setMandt(param.getMANDT());
			zsdt1074.setVbeln(vbeln);
			zsdt1074.setLh(DatasetUtility.getString(ds_dtl,"LHCHK"));
			zsdt1074.setKunaprv(DatasetUtility.getString(ds_dtl,"KUNAPRV"));
			zsdt1074.getTstmp().setTimeStamp(userId);

			// 2012.10.22 ����������� ���� ó�� ����������¸� ���ֻ��� '90'
			if ( "SAVE".equals(cmd) || "DELE".equals(cmd) )	{
				Sso0060 uVO = new Sso0060();
				uVO.setMANDT(param.getMANDT());
				uVO.setQTNUM(param.getQTNUM());
				uVO.setQTSER(param.getQTSER());
				uVO.setUDATE(DateTime.getDateString());
				uVO.setUTIME(DateTime.getShortTimeString());
				uVO.setUUSER(userId);

				if ("SAVE".equals(cmd)) {
					uVO.setZPRGFLG("90");		// ����������¸� �����ۼ�
					sso0060NDao.updateZsdt1046Zprgflg(uVO);
					zsdt1074d.insert(zsdt1074);
				} 	// end of if ("SAVE".equals(cmd))
				else if ("DELE".equals(cmd)) {
					uVO.setZPRGFLG("50");		// ����������¸� ������������ �����Ǿ� ���¸� ������ ���� '30' -> 50���� ����
					sso0060NDao.updateZsdt1046Zprgflg(uVO);
					sso0060NDao.updateZsdt1001Sorlt(uVO);
				}
			} else if("UPDT".equals(cmd)) {
				zsdt1074d.update(zsdt1074);
			}
		}

		return vbeln;
    }

    //=========================================================================================
    //  �Լ����  : ���� ����/����
    //  �Ķ����  : String cmd, Sso0060ParamVO 	param
    //              Dataset ds_cond, Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
    //              Dataset ds_error
    //  ���ϰ�    : void( Dataset ds_dtl, Dataset ds_list_item, ds_list_bill, ds_list_txt, ds_error )
    //  ���ID    : UF014, UF015
    //  ��������  : ���� ���� RFC API ����
    //=========================================================================================
    public void saveSapJcoProject(String sysid, String cmd, String userId, Sso0060ParamVO 	param,
            Dataset ds_cond, Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
            Dataset ds_error, MipResultVO resultVO) throws RfcException, Exception {
    	
/*        Rfc rfc = null;

    	// 2013.02.25 �Ҽ������� �ڸ��� ���� ������ ���� ���� �ٽ� �־���
        for ( int i = 0 ; i < ds_list_item.getRowCount() ; i++ ) {
    		BigDecimal bd = new BigDecimal(ds_list_item.getColumnAsString(i, "WAVWR"));
    		ds_list_item.setColumn(i, "WAVWR", bd.toPlainString());
    	}

        // RFC CALL
        rfc = sso0060NJS.callProjectDataSet(sysid, cmd, param.getVBELN(), param.getQTNUM(), param.getQTSER(), "ZWEB_SD_CREATE_SO", ds_dtl, ds_list_item, ds_list_bill, ds_list_txt);
    	*/

    	ZSDS0060[] 	list_head 	= (ZSDS0060[])CallSAPHdel.moveDs2Obj2(ds_dtl		, ZSDS0060.class, "");  // HEADER
    	ZSDS0061[] 	list_item 	= (ZSDS0061[])CallSAPHdel.moveDs2Obj2(ds_list_item	, ZSDS0061.class, "");	// ITEM
    	ZSDS0062[] 	list_bill 	= (ZSDS0062[])CallSAPHdel.moveDs2Obj2(ds_list_bill	, ZSDS0062.class, ""); 	// BILLING PLAN
    	ZSDS0060T[] list_txt 	= (ZSDS0060T[])CallSAPHdel.moveDs2Obj2(ds_list_txt	, ZSDS0060T.class, "");	// TEXT
        BAPIRET2[]	bapiret2	= new BAPIRET2[]{};

		for(int i = 0 ; i < list_item.length ; i++ ) {
    		BigDecimal bd = new BigDecimal(ds_list_item.getColumnAsString(i, "WAVWR"));
    		list_item[i].setWAVWR(new BigDecimal(bd.toPlainString()));
    	}

		Object obj[] = new Object[] {
				  list_txt										// O_��� : ZSDS0060T : TEXT
				, list_bill										// O_��� : ZSDS0062 : BILLING PLAN
				, cmd											// ó������(DISP:��ȸ,SAVE:����,UPDT:����,DELE:����)
				, param.getVBELN()
				, param.getQTNUM()
				, param.getQTSER()
				, list_item										// O_��� : ZSDS0061 : ITEM
				, bapiret2
				, list_head										// O_��� : ZSDS0060 : HEADER
		};

		SapFunction stub = CallSAP.callSap(sysid, param.getMANDT(), "ZWEB_SD_CREATE_SO", obj, false);
		if (((BAPIRET2[])stub.getOutput("TT_BAPIRET2")).length > 0) {
			for(BAPIRET2 bp2 : (BAPIRET2[])stub.getOutput("TT_BAPIRET2")) {
				if(bp2.getTYPE().equals("S"))
					continue;
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", bp2.getMESSAGE().toString(), "Y", "Y");
				resultVO.addDataset	(ds_error);
				break;
			}
			//SAP RFC ������ return message Type�� S�θ� �����Ǿ��µ��� ó�������� ��찡 �߻��ϱ� ������ �ӽ���ġ
			if(ds_error.getRowCount() == 0) {
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "", ((BAPIRET2[])stub.getOutput("TT_BAPIRET2"))[0].getMESSAGE().toString(), "Y", "Y");
			}
		} else {
			// 2012.10.22 ����������� ���� ó�� ����������¸� ���ֻ��� '90'
			if ( "SAVE".equals(cmd) || "DELE".equals(cmd) )	{
				Sso0060 uVO = new Sso0060();
				uVO.setMANDT(param.getMANDT());
				uVO.setQTNUM(param.getQTNUM());
				uVO.setQTSER(param.getQTSER());
				uVO.setUDATE(DateTime.getDateString());
				uVO.setUTIME(DateTime.getShortTimeString());
				uVO.setUUSER(userId);

				if ("SAVE".equals(cmd)) {
					uVO.setZPRGFLG("90");		// ����������¸� �����ۼ�
					sso0060NDao.updateZsdt1046Zprgflg(uVO);
				} 	// end of if ("SAVE".equals(cmd))
				else if ("DELE".equals(cmd)) {
					uVO.setZPRGFLG("50");		// ����������¸� ������������ �����Ǿ� ���¸� ������ ���� '30' -> 50���� ����
					sso0060NDao.updateZsdt1046Zprgflg(uVO);
					sso0060NDao.updateZsdt1001Sorlt(uVO);
				}
			}
		}    	
    }
    
    //=========================================================================================
    //  �Լ����  : �������� �ӽ� ����
    //  �Ķ����  : Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
    //              Dataset ds_error
    //  ���ϰ�    : void
    //  ���ID    : UF014, UF015
    //  ��������  : �������� �ӽ� ���� ��� �߰�
    //=========================================================================================
    public void saveTmpEstiMate(String userId, String mandt, Dataset ds_cond, Dataset ds_dtl, Dataset ds_list_item, 
    		                     Dataset ds_list_bill, Dataset ds_list_txt, Dataset ds_error) throws Exception {

		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// ������ȣ
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// ��������

    	ZSDT1100[] 	list_head 	= (ZSDT1100[])CallSAPHdel.moveDs2Obj2(ds_dtl		, ZSDT1100.class, "");  // HEADER  
    	ZSDT1101[] 	list_item 	= (ZSDT1101[])CallSAPHdel.moveDs2Obj2(ds_list_item	, ZSDT1101.class, "");	 // ITEM 
    	ZSDT1102[] 	list_bill 	= (ZSDT1102[])CallSAPHdel.moveDs2Obj2(ds_list_bill	, ZSDT1102.class, ""); 	 // BILLING PLAN 
    	ZSDT1103[]  list_txt 	= (ZSDT1103[])CallSAPHdel.moveDs2Obj2(ds_list_txt	, ZSDT1103.class, "");	 // TEXT  

    	// header DB ����/����/����
    	// ���� ����ÿ��� ǰ�� ���� �ʱ� ���� ���� Flag���� �ٸ��Ƿ� ���� ó���� �ʿ���
    	for(int i = 0; i < list_head.length; i ++ ) {
        	int iCount = 0;
    		list_head[i].setMANDT(mandt);
    		list_head[i].setQTNUM(qtnum);
    		list_head[i].setQTSER(qtser);
    		// �ش� ���� ���ؼ� ���� �Է°����� Ȯ���Ѵ�.
    		// ��¥�� ������ ����ȯ(YYYYMMDD)
    		
    		list_head[i].setBSTDK(dateReplace(list_head[i].getBSTDK()));
    		list_head[i].setCOMPL_DA(dateReplace(list_head[i].getCOMPL_DA()));
    		list_head[i].setCONTR_DA(dateReplace(list_head[i].getCONTR_DA()));
    		list_head[i].setFINAL_DA(dateReplace(list_head[i].getFINAL_DA()));
    		list_head[i].setFRMDA(dateReplace(list_head[i].getFRMDA()));
    		list_head[i].setHB_FROM(dateReplace(list_head[i].getHB_FROM()));
    		list_head[i].setHB_TO(dateReplace(list_head[i].getHB_TO()));
    		list_head[i].setRECAD_DA(dateReplace(list_head[i].getRECAD_DA()));
    		list_head[i].setSTADA(dateReplace(list_head[i].getSTADA()));
    		list_head[i].setVDATU(dateReplace(list_head[i].getVDATU()));    		
    		list_head[i].setACHDT(dateReplace(list_head[i].getACHDT()));
    		list_head[i].setSOPDT(dateReplace(list_head[i].getSOPDT()));
    		
    		Sso0060ParamVO param = new Sso0060ParamVO();
    		param.setMANDT(mandt);
    		param.setQTNUM(qtnum);
    		param.setQTSER(qtser);
    		iCount = sso0060NDao.selectTmpCount(param);
    		//sso0060NDao.mergeTmpQtHeader(list_head[i]);
    		if( iCount == 0 ) {
    			sso0060NDao.insertTmpQtHeader(list_head[i]);
    		} else {
    			sso0060NDao.updateTmpQtHeader(list_head[i]);
    		}

        	// �����ϰ� �Է��Ѵ�.
        	sso0060NDao.deleteTmpQtBiliing(param);

        	// �����ϰ� �Է��Ѵ�.
        	sso0060NDao.deleteTmpQtMsTxtSer(param);
        	
        	// item DB ����/����/����
    		// int bilMaxSeq = 0;
    		// int txtMaxSeq = 0;
        	for(int j = 0; j < list_item.length; j ++ ) {
                // ���� ��� �� ǰ������ FLAG���� "", "I", "U"�� ��� �ü� ������ ��� ���� ���ؼ� I�� �����ؼ� ó���Ѵ�.
        		if( iCount == 0 ) {
        			list_item[j].setFLAG("I");
        		} else {
        			list_item[j].setFLAG("U");
        			/*
        			if( list_bill.length > 0 ) {
        				bilMaxSeq = sso0060NDao.selectTmpQtBiliingMaxSeq(param);
        			}
                    if( list_txt.length > 0 ) {        			
    				    txtMaxSeq = sso0060NDao.selectTmpQtTxtMaxSeq(param);
                    }
                    **/
        		}

            	// 2013.02.25 �Ҽ������� �ڸ��� ���� ������ ���� ���� �ٽ� �־���
        		BigDecimal bd = new BigDecimal(ds_list_item.getColumnAsString(j, "WAVWR"));
        		list_item[j].setWAVWR(new BigDecimal(bd.toPlainString()));

        		list_item[j].setCONDA(dateReplace(list_item[j].getCONDA()));
        		list_item[j].setEDATU(dateReplace(list_item[j].getEDATU()));
        		list_item[j].setSTADA(dateReplace(list_item[j].getSTADA()));
        		list_item[j].setZRMDA(dateReplace(list_item[j].getZRMDA()));
        		
        		//if( list_item[j].getFLAG() != null ) {
        			list_item[j].setMANDT(mandt);
        			list_item[j].setQTNUM(qtnum);
        			list_item[j].setQTSER(qtser);
        			if( "I".equals(list_item[j].getFLAG())) {
        				sso0060NDao.insertTmpQtItem(list_item[j]);
        			} else if( "U".equals(list_item[j].getFLAG())) {
        				sso0060NDao.updateTmpQtItem(list_item[j]);
        			} else if( "D".equals(list_item[j].getFLAG())) {
        				sso0060NDao.deleteTmpQtItem(list_item[j]);
        			}
        		//}
        	}

        	// billingPlan ����/����/����
        	for(int j = 0; j < list_bill.length; j ++ ) {
        		//if( list_bill[j].getFLAG() != null ) {
        			list_bill[j].setMANDT(mandt);
        			list_bill[j].setQTNUM(qtnum);
        			list_bill[j].setQTSER(qtser);
        			list_bill[j].setSEQ(j);
        			list_bill[j].setFKDAT(dateReplace(list_bill[j].getFKDAT()));
        			
        			if( list_bill[j].getPOSNR() == null ) {
        				list_bill[j].setPOSNR("000000");
        			} else {
        				if( "".equals(list_bill[j].getPOSNR()) ) { 
        				    list_bill[j].setPOSNR("000000");  // ��� ������
        				}
        			}
        			
        			sso0060NDao.insertTmpQtBiliingPlan(list_bill[j]);
        			/*
        			if( "I".equals(list_bill[j].getFLAG())) {
        				sso0060NDao.insertTmpQtBiliingPlan(list_bill[j]);
        			} else if( "U".equals(list_bill[j].getFLAG())) {
        				sso0060NDao.insertTmpQtBiliingPlan(list_bill[j]);
        				//sso0060NDao.updateTmpQtBiliingPlan(list_bill[j]);
        			} //else if( "D".equals(list_bill[j].getFLAG())) {
        			  //	sso0060NDao.deleteTmpQtBiliingPlan(list_bill[j]);
        			  //}
        		   */
        		//}
        	}

        	// �����ϰ� �Է��Ѵ�.
        	// �ؽ�Ʈ ����/����/����
        	for(int j = 0; j < list_txt.length; j ++ ) {
        		//if( list_txt[j].getFLAG() != null ) {
        			list_txt[j].setMANDT(mandt);
        			list_txt[j].setQTNUM(qtnum);
        			list_txt[j].setQTSER(qtser);
        			list_txt[j].setSER(new Integer(j).toString());
        			sso0060NDao.insertTmpQtText(list_txt[j]);
        			
        			/*
        			if( "I".equals(list_txt[j].getFLAG())) {
        				sso0060NDao.insertTmpQtText(list_txt[j]);
        			} else if( "U".equals(list_txt[j].getFLAG())) {
        				sso0060NDao.insertTmpQtText(list_txt[j]);
        				// sso0060NDao.updateTmpQtText(list_txt[j]);
        			} //else if( "D".equals(list_txt[j].getFLAG())) {
        				//sso0060NDao.deleteTmpQtText(list_txt[j]);
        			  //}
        			*/
        		//}
        	}    	
    	}
    }

    //=========================================================================================
    //  �Լ����  : �������� �ӽ� ���� ������ ����
    //  �Ķ����  : Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt,
    //              Dataset ds_error
    //  ���ϰ�    : void
    //  ���ID    : UF014, UF015
    //  ��������  : �������� �ӽ� ���� ����
    //=========================================================================================
    public void deleteTmpEstiMateTable(String mandt, Dataset ds_cond) throws Exception {
		String qtnum  = DatasetUtility.getString(ds_cond,"QTNUM")  ;	// ������ȣ
		String qtser  = DatasetUtility.getString(ds_cond,"QTSER")  ;	// ��������

		ZSDT1100 zSDT1100 = new ZSDT1100();
		zSDT1100.setMANDT(mandt);
		zSDT1100.setQTNUM(qtnum);
		zSDT1100.setQTSER(qtser);
		sso0060NDao.deleteTmpQtHeader(zSDT1100);
		
		ZSDT1101 zSDT1101 = new ZSDT1101();
		zSDT1101.setMANDT(mandt);
		zSDT1101.setQTNUM(qtnum);
		zSDT1101.setQTSER(qtser);
		sso0060NDao.deleteAllTmpQtItem(zSDT1101);

		Sso0060ParamVO param = new Sso0060ParamVO();
		param.setMANDT(mandt);
		param.setQTNUM(qtnum);
		param.setQTSER(qtser);
		sso0060NDao.deleteTmpQtBiliing(param);
		sso0060NDao.deleteTmpQtMsTxtSer(param);
    }
    
    //=========================================================================================
    //  �Լ����  : �������� ��ȸ���� ������ ���� ��� ��������� ���� �߰�
    //  �Ķ����  : Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_manager
    //=========================================================================================
    public void setEstimatAddManager(Sso0060ParamVO param, Dataset ds_dtl, Dataset ds_manager) {
    	ds_manager.appendRow();
    	
    	String auart = StringUtils.trim(ds_dtl.getColumnAsString(0, "AUART"));
    	String zkunnr = StringUtils.trim(ds_dtl.getColumnAsString(0, "KUNNR_Z2"));
    	
    	param.setZKUNNR(zkunnr);
    	
    	if (auart.equals("ZS02")) {
    		param.setSDFIELD("NI");
    	} else if (auart.equals("ZN02")) {
    		param.setSDFIELD("REM");
    	} else if (auart.equals("ZJ02")) {
    		param.setSDFIELD("PRK");
    	} else {
    		param.setSDFIELD("");
    	}
    	
    	List<Sso0060> managerList = sso0060NDao.selectListManager(param);
    	if (managerList.isEmpty()) {
    		ds_manager.setColumn(0, "MANAGER", "N");
    	}
    	else {
    		ds_manager.setColumn(0, "MANAGER", "Y");
    	}
    }

    // ��¥ Ÿ���� ����Ÿ�� 8�ڸ��� �缳��
	public String dateReplace(String pText)
	{ 
		String retText = pText;
		
		if( retText == null ) {
			return null;
		} else {
			retText = retText.replaceAll("[-]", "");
			retText = retText.replaceAll("[.]", "");
			retText = retText.replaceAll("[_]", "");
			if( retText.length() > 8 ) {
				retText = retText.substring(0, 8);	
			}
		}
		return retText;
	}
	
	public Boolean validQtdat(String sysid, String mandt, String qtdat, String kunnrZ2) throws ParseException, RemoteException  {
		if(qtdat == null)
			return false;

		ZWEB_SD_VALID_QTDATStub stub;
		ZWEB_SD_VALID_QTDATStub.ZWEB_SD_VALID_QTDAT param;
		ZWEB_SD_VALID_QTDATStub.ZWEB_SD_VALID_QTDATResponse response;

		stub = (ZWEB_SD_VALID_QTDATStub) new SAPStub(sysid, mandt, "").create(ZWEB_SD_VALID_QTDATStub.class);

		param = new ZWEB_SD_VALID_QTDATStub.ZWEB_SD_VALID_QTDAT();

		param.setIV_KUNNRZ2(ZWEB_SD_VALID_QTDATStub.Char10.Factory.fromString(kunnrZ2, ""));
		param.setIV_QTDAT(ZWEB_SD_VALID_QTDATStub.Date.Factory.fromString(qtdat.replaceFirst("(\\d{4})(\\d{2})(\\d+)", "$1-$2-$3"), ""));

		response = stub.zWEB_SD_VALID_QTDAT(param);
		return Boolean.parseBoolean(response.getEV_VALID().toString());
	}
	
	public BigDecimal getExchangeRateTrade(String mandt, String sKurst, String sGdatu, String sWAERK )  throws Exception {
		String waerkBas = null;
		String exchangeBas = null;				//������ȭ
		String exchangeCon = null;				//�����ȭ
		BigDecimal rtnAmt = null;
		
		waerkBas = ExchangeDao.searchWaerkBase(mandt, sGdatu);
		if (!waerkBas.equals(sWAERK)) {
			exchangeBas = ExchangeS.getExchangeRate(mandt, sKurst, sGdatu, waerkBas, "KRW");		// ������ȭ -> ��ȭ 
			if ("KRW".equals(sWAERK)){
				exchangeCon = "1";
			} else {
				exchangeCon = ExchangeS.getExchangeRate(mandt, sKurst, sGdatu, sWAERK, "KRW");		// �����ȭ -> ��ȭ
			}
		}
		else {
			return new BigDecimal(1.0);
		}

		if (Double.parseDouble(exchangeBas) > 0 && Double.parseDouble(exchangeCon) > 0) {
			BigDecimal exchangeBas_dec = new BigDecimal(exchangeBas);
			BigDecimal exchangeCon_dec = new BigDecimal(exchangeCon);
			rtnAmt = exchangeCon_dec.divide(exchangeBas_dec, 4, BigDecimal.ROUND_HALF_UP);		
		}
		
		return rtnAmt;
	}

	public List<Sso0060> searchSopind(Sso0060ParamVO param) {
		return sso0060NDao.selectSopind(param);
	}
}