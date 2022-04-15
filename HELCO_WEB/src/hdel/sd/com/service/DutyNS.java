package hdel.sd.com.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sap.domain.Datum;
import com.sap.domain.OrderItem;
import com.sap.domain.OrderNo;
import com.sap.domain.Posnr;
import com.sap.domain.Vbeln;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.ZSDTDUTYEXCPTDD;
import hdel.lib.exception.BizException;
import hdel.lib.exception.NoMatchException;
import hdel.lib.exception.RequireException;
import hdel.lib.service.SrmService;
import hdel.sd.com.dao.DutyND;
import hdel.sd.com.domain.Duty;
import hdel.sd.com.domain.DutyDet;
import hdel.sd.com.domain.DutyObj;
import hdel.sd.com.domain.DutyOpr;

@Service
public class DutyNS extends SrmService {

    private Logger logger = Logger.getLogger(this.getClass());

    private DutyND dao;
    private ZSDTDUTYEXCPTDD dutyExcptDDao;

    public void createDao(SqlSession sqlSession) {
        dao = sqlSession.getMapper(DutyND.class);
        dutyExcptDDao = sqlSession.getMapper(ZSDTDUTYEXCPTDD.class);
    }

    // duck �� ����Ʈ ���� �Է��ϱ� ���� ó��
    // duckCheck ���� �Էµ� ���� üũ�� �����Ѵ�.
    //=========================================================================================
    //  �Լ���� : ������-Duty Call
    //  �Ķ���� : mandt, gtype, blockgbn, mapInput, lang
    //  ���ϰ�   : dsTemplate1(��缭 �Է�üũ ���� üũ �� �⺻�������� ����� ��缭����)
    //            ,ds_error(��缭 ���� �� �ʼ����� ���� ��� �����޽��� ������)
    //  ���ID   : UF003
    //  �������� : ��� �������� �� ��� üũ ������ ó�� ���� ����
    //  HISTORY  : 2016.01.28 ���� �ڵ� �ڼ���
    //=========================================================================================
    public void genSpecDutySingleMo(String mandt, String gtype, String blockgbn, HashMap<String, String> mapInput, 
    		                          String lang, String zflg) {

        // Duty���� �о���� (������)
        // ***** ��缭 üũ ������ ���� ó�� �ӵ� ��� ���� ������ listDuty ������ �޷� �ִ� ****** //
    	//20190420 spras�߰� by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";
		
    	DutyObj dutyObj;
        if("LXVF".equals(gtype) || "SSVF".equals(gtype) || "STVF".equals(gtype) || "HSVF".equals(gtype)) {
        	dutyObj = genDutyObj(mandt, gtype, true, blockgbn, zflg, spras);
        } else {
        	dutyObj = genDutyObj(mandt, gtype, false, blockgbn, zflg, spras);
        }
    	List<Duty> listDuty = dutyObj.getDutyList();
        

        // Duty�� �������� �ʴ� ��� �޼��� ó��
        if(listDuty.size() == 0) {
            throw new BizException("No Data Duty Master");
        }

        int specMaxIdx;

        for (int i=0; i<listDuty.size(); i++) {
            Duty duty = listDuty.get(i);
            // Duty �ѱ� ��� ������ ����
            // 2012.12.27 �޼��� LANG�߰�
            duty.setLANG("3");
            if (!"ko".equals(lang)) {
                duty.setLANG("E");
            }

            // �������� 3���� ������ ����Ǿ� ����
            if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )    {
                duty.setLANG("3");
            }

            specMaxIdx = getSpecMaxIdx( duty );	// ���üũ�׸� ���� ��������

            // ���� Duty�� ���� ���������� Mo ��翡 ���� �ʼ� �Է°� üũ�� �����Ѵ�.
            try    {
                validateDuty(duty, mapInput, specMaxIdx);
            } catch    ( RuntimeException e )    {
                throw e;
            }

            // Duty Detail ���� �о����
            List<DutyDet> listDutyD = dutyObj.getBlockDutyDList(duty.getBLOCKNO());

            if ( listDutyD == null )    {
                throw new BizException("No Data Duty Detail");
            }

            // �������
            int matchIdx = getMatchIdx(duty, mapInput, specMaxIdx, listDutyD);
            if (-1 == matchIdx) {
    			String message = System.getProperty("line.separator") + "[Block No.] " + duty.getBLOCKNO()
    			+ System.getProperty("line.separator") + "[Block Name] " + duty.getBLOCKNM()
//    			+ System.getProperty("line.separator") + "[Field] " + duty.getGTYPE() + " : " + outName
    			;
    			throw new NoMatchException(message);	// ������� �� match ���� ��� ����
            } else {
            }

            // üũ ����� ������ ����Ʈ�� �ش�Ǵ� ���� �����ϰ� �� �ش� �����Ϳ� ��¿����� ���ǵǴ� PRH ���� ���� �κп�
            // ���� ��� �������� �����Ѵ�.-> �� ��갪�� ���� �׸񰪵��� �Էµǰ� �ȴ�.
            // int outMaxIdx = getOutMaxIdx( duty );    // ���Out�׸� ���� ��������
            // ��缭 �����׸� ����
            // getOut(duty, mapWork, mapOut, listDutyD.get(matchIdx), outMaxIdx);
            int outMaxIdx = getOutMaxIdx( duty );	// ���Out�׸� ���� ��������

			// ��� �����׸� ����
			getOut(duty, mapInput, listDutyD.get(matchIdx), outMaxIdx);
        }
    }

    //=========================================================================================
    //  �Լ���� : ������-���Ӽ�(���� MO�� ó�� ����)
    //  �Ķ���� : ds_template1(��缭���� �׸��� ȭ�鰪)
    //  ���ϰ�   : dsTemplate1(��缭 �Է�üũ ���� üũ �� �⺻�������� ����� ��缭����)
    //            ,ds_error(��缭 ���� �� �ʼ����� ���� ��� �����޽��� ������)
    //  ���ID   : UF003
    //  �������� : ��� �������� �� ��� üũ ������ ó�� ���� ����
    //  HISTORY  : 2016.01.28 ���� �ڵ� �ڼ��� dependDutySingleMo
    //=========================================================================================
	// 2013.03.06 ���Ӽ� ó���� (ǥ��Template ȣ�� �� ó�������� �и�)
	public void dependDutySingleMo(String mandt, String gtype, String blockgbn, HashMap<String, String> mapInput, 
			                         String lang, String zflg) {

    	//20190420 spras�߰� by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";

		// Duty���� �о���� (������)
		DutyObj dutyObj = genDutyObj(mandt, gtype, blockgbn, zflg,spras);
		List<Duty> listDuty = dutyObj.getDutyList();

		if( listDuty == null ) {
			mapInput = null;
		}

        int specMaxIdx;

        for (int i=0; i<listDuty.size(); i++) {
            Duty duty = listDuty.get(i);
            // Duty �ѱ� ��� ������ ����
            // 2012.12.27 �޼��� LANG�߰�
            duty.setLANG("3");
            if (!"ko".equals(lang)) {
                duty.setLANG("E");
            }

            // �������� 3���� ������ ����Ǿ� ����
            if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )    {
                duty.setLANG("3");
            }

            specMaxIdx = getSpecMaxIdx( duty );	// ���üũ�׸� ���� ��������

            // ���� Duty�� ���� ���������� Mo ��翡 ���� �ʼ� �Է°� üũ�� �����Ѵ�.
            try    {
            	validateDuty(duty, mapInput, specMaxIdx);
            } catch    ( RuntimeException e )    {
                throw e;
            }

            List<DutyDet> listDutyD = dutyObj.getBlockDutyDList(duty.getBLOCKNO());
            if( listDutyD == null ) {
            	mapInput = null;
            	break;
            }

            // �������
			if( listDutyD != null && listDutyD.size() > 0 ) {
	            int matchIdx = getMatchIdx(duty, mapInput, specMaxIdx, listDutyD);
	            if (-1 == matchIdx) {
					throw new NoMatchException(duty.getBLOCKNM());	// ������� �� match ���� ��� ����
				}

	            int outMaxIdx = getOutMaxIdx( duty );	// ���Out�׸� ���� ��������

				// ��� �����׸� ����
				getOut(duty, mapInput, listDutyD.get(matchIdx), outMaxIdx);
			}
		}
	}

    //=========================================================================================
    //  �Լ���� : �����ǿ� ���� ��系�� ���� ���üũ ó�� ��
    //  �Ķ���� : ds_template1(��缭���� �׸��� ȭ�鰪)
    //  ���ϰ�   : dsTemplate1(��缭 �Է�üũ ���� üũ �� �⺻�������� ����� ��缭����)
    //            ,ds_error(��缭 ���� �� �ʼ����� ���� ��� �����޽��� ������)
    //  ���ID   : UF003
    //  �������� : ��� �������� �� ��� üũ ������ ó�� ���� ����
    //  HISTORY  : 2016.01.28 ���� �ڵ� �ڼ��� dependDutySingleMo
    //=========================================================================================
	public boolean genSpecCheck(String mandt, String flag, String item, Dataset template, Dataset log, 
            String lang, DutyObj dutyObj) {
		return genSpecCheck(mandt, flag, item, template, log, lang, dutyObj, new Dataset());
	}
	public boolean genSpecCheck(String mandt, String flag, String item, Dataset template, Dataset log, 
			                      String lang, DutyObj dutyObj, Dataset dsOrder) {
		boolean rtnValue = true;
        HashMap<String, String> mapInput = new HashMap<String, String>();
        String sItem = "";
        int cnt   = 0;
        int idxItem = 0;

        // ������ ��系�� N�� 1�� ������ ������ 1�� N���������� ��ȯ�Ͽ� HashMap�� �Է��Ѵ�.
        // SAPHEE.CABN ATNAM  -> SAPHEE.ZSDT1048 PRH
		if ("Q".equals(flag)) {
			for (int i = 0; i < template.getRowCount(); i++) {
				sItem = template.getColumnAsString(i, "QTSEQ");
				if(!item.equals(sItem))	{
					continue;
				}
				idxItem = i;

				String value = template.getColumnAsString(i, "VALUE");	// ������ PRD -> VALUE
				if (null != value && ! "".equals(value)) {
					if("NUM".equals(template.getColumnAsString(i, "ATFOR"))) {
						mapInput.put(template.getColumnAsString(i, "ATNAM").replaceAll(",", ""), value);	// ������ PRH -> ATNAM
					} else {
						mapInput.put(template.getColumnAsString(i, "ATNAM"), value);	// ������ PRH -> ATNAM
					}
				}
			}
		}else	{
			sItem = template.getColumnAsString(0, "HOGI");
			for (int i = 0; i < template.getRowCount(); i++) {
				if(!item.equals(sItem))	{
					continue;
				}
				idxItem = i;

				String value = template.getColumnAsString(i, "PRD");
				if (null != value && ! "".equals(value)) {
					if("NUM".equals(template.getColumnAsString(i, "CNGBN"))) {
						mapInput.put(template.getColumnAsString(i, "PRH").replaceAll(",", ""), value);	// ������ PRH -> ATNAM
					} else {
						mapInput.put(template.getColumnAsString(i, "PRH"), value);	// ������ PRH -> ATNAM
					}
				}
			}
		}

        // ��� üũ �� �߻��� ���� �޽��� ������ 
        //Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
        //try
        //{
        String gtype = "";
        String el_atyp = "";

		// 2013.01.29 Duty �� Ÿ�� ���� �߰�
		String el_ditemuse = (String) mapInput.get("EL_DITEMUSE");
		String el_asply = (String) mapInput.get("EL_ASPLY");
		String pspid = template.getColumnAsString(idxItem, "PSPID");
		String uch_duty = template.getColumnAsString(idxItem, "UCH_DUTY");
		if ( el_ditemuse == null ) el_ditemuse = "";
		if ( el_asply == null ) el_asply = "";

		if ( el_ditemuse.equals("Y") || el_asply.equals("Y") ) {
			return true;
		}

		if (pspid.equals("126394")){ //20131030 kt û������ ��ǥ������ �ʼ�üũ ���� -�輱ȣ
			return true;
		}
		
		if (uch_duty.equals("1")){
			return true;
		}
				
		el_atyp = (String) mapInput.get("EL_ATYP");
		
		if (el_atyp != null && !"".equals(el_atyp))	{
			gtype = "ELV1";
			// ���ڿ� ó��
			if ("STSH1".equals(el_atyp) || "STSH5".equals(el_atyp))	{
				gtype = "ELV2";
			}
		} else {
			// �����÷�����
			el_atyp = (String) mapInput.get("ES_ATYP");
			if (el_atyp != null && !"".equals(el_atyp))	{
				gtype = "ESC1";	// �����÷����ʹ� Default�� ó��
			}else {
				// ������ũ
				el_atyp = (String) mapInput.get("MW_ATYP");
				if (el_atyp != null && !"".equals(el_atyp))	{
					gtype = "MWK1";	// ������ũ�� Default�� ó��
				} else {
					el_atyp = (String) mapInput.get("AP_ATYP");
					if (el_atyp != null && !"".equals(el_atyp))	{
						gtype = "ZPK1";	// ���� Default�� ó��
					} else	{	// 2013.01.03 �̿ܿ��� �ʼ� üũ ���ʿ�
						return true;
					}
				}
			}
		}
		genCostDutySingleMo(mandt, gtype, mapInput, log, lang, item, cnt, dutyObj, flag, dsOrder);

        return rtnValue;
	}

    //=========================================================================================
    //  �Լ���� : ������-�������� ���Ӽ� üũ(Single MO�� ó�� ����)
	//             ������ �������� ó�� �� �Էµ� ���� ���� ������ üũ�� �����Ѵ�.
	//             ���Ӽ� üũ ���� �� ���� ���� üũ �߻� �� �޽����� ���� üũ ������ �����Ѵ�.
    //  �Ķ���� : ds_template1(��缭���� �׸��� ȭ�鰪)
    //  ���ϰ�   : dsTemplate1(��缭 �Է�üũ ���� üũ �� �⺻�������� ����� ��缭����)
    //            ,ds_error(��缭 ���� �� �ʼ����� ���� ��� �����޽��� ������)
    //  ���ID   : UF003
    //  �������� : ��� �������� �� ��� üũ ������ ó�� ���� ����
    //  HISTORY  : 2016.01.28 ���� �ڵ� �ڼ��� dependDutySingleMo
    //=========================================================================================
    public void genCostDutySingleMo(String mandt, String gtype, HashMap<String, String> mapInput, Dataset log, 
    		                         String lang, String item, int cnt, DutyObj dutyObj, String zflg, Dataset dsOrder) {

    	List<String> lstBlockNo = new ArrayList<String>();
    	if(dsOrder != null) {
    		lstBlockNo = dutyExcptDDao.getExceptionalBlockNoList(mandt, new OrderNo(dsOrder.getColumnAsString(0, "ordno")), dsOrder.getColumnAsInteger(0, "ordseq"), new OrderItem(dsOrder.getColumnAsString(0, "orditem")));
    		Datum ildat = dao.getIldat(mandt, new Vbeln(dsOrder.getColumnAsString(0, "ordno")), new Posnr(dsOrder.getColumnAsString(0, "orditem")));
    		String today = (new SimpleDateFormat("yyyyMMdd")).format(Calendar.getInstance().getTime());
    		if(ildat != null && !ildat.isInitial() && Float.valueOf(ildat.toString()) < Float.valueOf(today))
    			return;
    	}

    	//20190420 spras�߰� by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                   spras = "E";
    	
    	// Duty���� �о���� (������)
    	if(dutyObj == null) {
    		// �ʱⰪ ������ ���� ��� ���� ����
    		dutyObj = genDutyObj(mandt, gtype, "", zflg, lstBlockNo,spras);
    	} else {
        	// �˻��� ���� �Ķ���ͷ� �����Ͽ� �ߺ��� �Ķ���Ϳ� ���� ���� ���� ���� �ڷᰡ
        	// �����ϸ� �ߺ��� ���� ������ �����Ͽ� �̹� �����ϴ� �����͸� ������ ���Ѵ�.
        	boolean isData = dutyObj.getDataExists(mandt, gtype);
    		if( !isData ) {
    			// ������ �� ���� �� ���� ����
    			dutyObj = genDutyObj(mandt, gtype, false, "", zflg, dutyObj, lstBlockNo,spras);
    		}
        }

        List<Duty> listDuty = dutyObj.getDutyList();
        int specMaxIdx;
        
		for (int i = 0; i < listDuty.size(); i++) {
			Duty duty = listDuty.get(i);

            // Duty �ѱ� ��� ������ ����
            // 2012.12.27 �޼��� LANG�߰�
            duty.setLANG("3");
            if (!"ko".equals(lang)) {
                duty.setLANG("E");
            }

            // �������� 3���� ������ ����Ǿ� ����
            if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )    {
                duty.setLANG("3");
            }
            specMaxIdx = getSpecMaxIdx( duty );	// ���üũ�׸� ���� ��������

            // ���� Duty�� ���� ���������� Mo ��翡 ���� �ʼ� �Է°� üũ�� �����Ѵ�.
            try {
                validateDuty(duty, mapInput, specMaxIdx);
            } catch( RuntimeException e ) {
            	cnt = log.appendRow();
				log.setColumn(cnt, "IDX", cnt + 1);
				// 2020 �귣��
				log.setColumn(cnt, "PRCSYS",  "SRM");
				log.setColumn(cnt, "HOGI",  item);
				log.setColumn(cnt, "LOGMSG",  e.getMessage());
				//throw e;
            }
            
            List<DutyDet> listDutyD = dutyObj.getBlockDutyDList(duty.getBLOCKNO());
			if ( listDutyD != null && listDutyD.size() > 0 ) {
				// �������
				//2019.02.10 try catch�� getMatchIdx���� exception�߻��� catch�� ����
	            int matchIdx = 0;
	            try {
	            		matchIdx = getMatchIdx(duty, mapInput, specMaxIdx, listDutyD);
	    				if (-1 == matchIdx) {
	    					cnt = log.appendRow();
	    					log.setColumn(cnt, "IDX", cnt + 1);
	    					log.setColumn(cnt, "PRCSYS",  "SRM");
	    					log.setColumn(cnt, "HOGI",  item);
	    					log.setColumn(cnt, "LOGMSG",  duty.getBLOCKNM());
	    				} else {
	    					int outMaxIdx = getOutMaxIdx( duty );	// ���Out�׸� ���� ��������
	    					// ��� �����׸� ����
	    				    getOut(duty, mapInput, listDutyD.get(matchIdx), outMaxIdx);
	    				}
	            } catch (RuntimeException e) {
	            	e.printStackTrace();
					cnt = log.appendRow();
					log.setColumn(cnt, "IDX", cnt + 1);
					log.setColumn(cnt, "PRCSYS",  "SRM");
					log.setColumn(cnt, "HOGI",  item);
					log.setColumn(cnt, "LOGMSG",  duty.getBLOCKNM() +":"+ e.getMessage());
	            }
			}
		}
	}

    //=========================================================================================
    //  �Լ���� : ��缭 üũ�� ���� SAPHEE.ZSDTDUTY, SAPHEE.ZSDTDUTYD BLOCKNO�� ������
    //             ��ȸ
    //  �Ķ���� : String mandt, String gtype, String blockgbn
    //  ���ϰ�   : DutyObj
    //  ���ID   : UF003
    //  �������� : BLOCKNO�� ���� ������ ��ȸ ����� ��ü BLOCKNO ������ ��ȸ �������� ����ó��
    //  HISTORY  : 2016.01.28 ���� �ڵ� �ڼ���
    //=========================================================================================
    private DutyObj genDutyObj(String mandt, String gtype, String blockgbn, String zflg, List<String> lstExcpBlockNo ,String lang) {
    	return genDutyObj(mandt, gtype, false, blockgbn, zflg, new DutyObj(), lstExcpBlockNo, lang);
    }
    private DutyObj genDutyObj(String mandt, String gtype, String blockgbn, String zflg,String lang) {
    	DutyObj dutyObj = new DutyObj();
    	return genDutyObj(mandt, gtype, false, blockgbn, zflg, dutyObj, lang);
    }
    private DutyObj genDutyObj(String mandt, String gtype, Boolean anyType, String blockgbn, String zflg,String lang) {
    	DutyObj dutyObj = new DutyObj();
    	return genDutyObj(mandt, gtype, anyType, blockgbn, zflg, dutyObj, lang);
    }

    //=========================================================================================
    //  �Լ���� : ��缭 üũ�� ���� SAPHEE.ZSDTDUTY, SAPHEE.ZSDTDUTYD BLOCKNO�� ������
    //             ��ȸ
    //  �Ķ���� : String mandt, String gtype, String blockgbn, DutyObj dutyObj
    //  ���ϰ�   : DutyObj
    //  ���ID   : UF003
    //  �������� : BLOCKNO�� ���� ������ ��ȸ ����� ��ü BLOCKNO ������ ��ȸ �������� ����ó��
    //  HISTORY  : 2016.01.28 ���� �ڵ� �ڼ���
    //=========================================================================================
    private DutyObj genDutyObj(String mandt, String gtype, Boolean anyType, String blockgbn, String zflg, DutyObj dutyObj,String lang) {
    	return genDutyObj(mandt, gtype, anyType, blockgbn, zflg, dutyObj, new ArrayList<String>(), lang);
    }
	private DutyObj genDutyObj(String mandt, String gtype, Boolean anyType, String blockgbn, String zflg, DutyObj dutyObj, List<String> lstExcpBlockNo,String lang) {

    	List<Duty> dutyList = dao.selectDuty(mandt, gtype, anyType, blockgbn, zflg, lstExcpBlockNo,lang);
    	// �˻��� ���� �Ķ���ͷ� �����Ͽ� �ߺ��� �Ķ���Ϳ� ���� ���� ���� ���� �ڷᰡ
    	// �����ϸ� �ߺ��� ���� ������ �����Ѵ�.
    	dutyObj.setDutyList(dutyList, mandt, gtype, zflg);

    	DutyDet dutyDet=null;
		DutyDet dutyDetNext=null;
        List<DutyDet> dutyDList = dao.selectDutydList(mandt, gtype, anyType, blockgbn, zflg, lstExcpBlockNo);
        int MaxSize = 0;
        if( dutyDList.size() > 1 ) {
        	MaxSize = dutyDList.size()-1;
        }
		List<DutyDet> tmpDutyDList = new ArrayList<DutyDet>();
        for(int i=0; i < dutyDList.size(); i++ ) {
        	if( i < MaxSize ) {
            	dutyDet = dutyDList.get(i);
            	dutyDetNext = dutyDList.get(i+1);
            	if( dutyDet.getBLOCKNO().equals(dutyDetNext.getBLOCKNO())) {
            		tmpDutyDList.add(dutyDet);
            	} else {
            		tmpDutyDList.add(dutyDet);
            		dutyObj.setBlockDutyDList(dutyDet.getBLOCKNO(), tmpDutyDList);
            		tmpDutyDList = new ArrayList<DutyDet>();
            	}
        	} else {
            	dutyDet = dutyDList.get(i);
        		tmpDutyDList.add(dutyDet);
        		dutyObj.setBlockDutyDList(dutyDet.getBLOCKNO(), tmpDutyDList);
        	}
        }

        return dutyObj;
    }

	// HashMap mapWork �� in/out ó��
	// HashMap mapOut  �� in/out ó��
	private void validateDuty(Duty duty, HashMap<String,String> mapInput, int specMaxIdx) {
		String value = "";
		if (0 == specMaxIdx) return;

		Method dutySpecX, dutySpecDefvX, dutySpecEssX;
		int idx=1;
		try	{
			while(idx <= 20) {
					dutySpecX = duty.getClass().getMethod("getSPEC"+String.valueOf(idx));
					dutySpecDefvX = duty.getClass().getMethod("getSPECDEFV"+String.valueOf(idx));
					dutySpecEssX = duty.getClass().getMethod("getSPECESS"+String.valueOf(idx));

					value = (String)mapInput.get(dutySpecX.invoke(duty).toString());
					if (null == value || value.equals("")) {
						if (dutySpecEssX.invoke(duty).toString().equals("X")) {
							errorRaise(duty, dutySpecX.invoke(duty).toString());
						}

						if (!dutySpecDefvX.invoke(duty).toString().equals("")) {
							mapInput.put(dutySpecX.invoke(duty).toString(), dutySpecDefvX.invoke(duty).toString()); // Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						}
					}

				idx++;
	        }
		} catch(NoSuchMethodException e) {
			throw new BizException(e.getMessage());
		} catch(InvocationTargetException e) {
			throw new BizException(e.getMessage());
		} catch(IllegalAccessException e) {
			throw new BizException(e.getMessage());
		} catch(RuntimeException e) {
			throw e;
		}
	}

	private int getMatchIdx(Duty duty, HashMap<String, String> mapWork, int specMaxIdx, List<DutyDet> listDutyD) {
		int matchIdx = -1;

		// Duty�� Spec�� �������� ���� ���
		if (0 == specMaxIdx) {
			// Spec ����Ʈ�� 1���� ���� �ش� ���ϰ����� ����
			if (1 == listDutyD.size()) return 0;
			// �̿ܿ��� ���� (0 �Ǵ� ������ ó�� �Ұ�)
			else throw new BizException("No Spec & Multi Out Exception");
		}
		Method dutySpecX, dutyDSpecX;
		DutyDet dutyDet;
		for (int i = 0; i < listDutyD.size(); i++) {
			boolean matched = false;
			dutyDet = listDutyD.get(i);
//			Method dutySpecX, dutyDSpecX;

			int idx=1;
			try {
				while(idx <= 20) {
					if(idx > specMaxIdx) break;
	
					dutySpecX = duty.getClass().getMethod("getSPEC"+String.valueOf(idx));
					dutyDSpecX = dutyDet.getClass().getMethod("getSPEC"+String.valueOf(idx));
					
					matched = DutyOpr.compare((String) mapWork.get(dutySpecX.invoke(duty).toString()), dutyDSpecX.invoke(dutyDet).toString(), mapWork);
					if(!matched) break;
	
					idx++;
				}
				if (matched) {
					matchIdx = i;
					break;
				}
			} catch (NoSuchMethodException e) {
				throw new BizException(e.getMessage());
			} catch (InvocationTargetException e) {
				throw new BizException(e.getMessage());
			} catch (IllegalAccessException e) {
				throw new BizException(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException(e.getMessage());
			}
		}
		return matchIdx;
	}

	// outValue ���ȭ ����
	private String getOutValue(String outName, Duty duty, String outDutyD, HashMap<String, String> mapInput)	{

		String outValue = "";

		// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
		if (0 <= outDutyD.indexOf("|")) {
			outValue = outDutyD.replace("|", "");
		// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) 1900 + {EL_ECCH}
		} else if (0 <= outDutyD.indexOf("+") ||
			0 <= outDutyD.indexOf("-") ||
			0 <= outDutyD.indexOf("*") ||
			0 <= outDutyD.indexOf("/")) {
				if ("ES".equals(outName.substring(0,2)) || "MW".equals(outName.substring(0,2))){
					outValue = outDutyD;
				} else {
					if ("R/L".equals(outDutyD) || "R/R".equals(outDutyD)){
						// CAR GOVERNOR ��ġ ���� R/L R/R -> ���� ������� 0 ���� ��µǴ� ���� ���� 20141229 �輱ȣ
						outValue = outDutyD;
					}else{
						outValue = calc(duty, mapInput, outDutyD);
				    }
				}	
		// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
		} else if (0 <= outDutyD.indexOf("#")) {
			outValue = calcFix( duty, mapInput, outDutyD);
		// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
		} else if (0 <= outDutyD.indexOf("{") ||
				   0 <= outDutyD.indexOf("}") ) {
			outValue = (String) mapInput.get(outDutyD.replace("{", "").replace("}", ""));
		} else {
			outValue = outDutyD;
		}

		return outValue;
	}

	// HashMap mapWork �� in/out ó��
	// HashMap mapOut  �� in/out ó��
	private void getOut(Duty duty, HashMap<String, String> mapInput, DutyDet dutyDet, int outMaxIdx) {
		String outName = "";
		String outValue = "";
		Method dutyOutX, dutyDetOutX;

		int idx=1;
		try {
			while(idx <= 10) {
					dutyOutX = duty.getClass().getMethod("getOUT"+String.valueOf(idx));
					dutyDetOutX = dutyDet.getClass().getMethod("getOUT"+String.valueOf(idx));
					outName = dutyOutX.invoke(duty).toString();
					outValue = getOutValue(outName, duty, dutyDetOutX.invoke(dutyDet).toString(), mapInput);
	
		            // 2012.10.29 ī������ ��� 1���ڸ����� ������ �ø� ��� 1184.88 -> 1190���� �����ó��
	                if (outName.equals("EL_ECW") || outName.equals("EL_YECW_1") || outName.equals("EL_YECWR")) {
	                    outValue = (String) (new BigDecimal(outValue).setScale(-1, BigDecimal.ROUND_UP).intValue() + "");
	                }
	
					mapInput.put(outName, outValue);
	
				idx++;
	        }
		} catch (NoSuchMethodException e) {
			throw new BizException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new BizException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new BizException(e.getMessage());
		} catch(Exception e) {
			String message = "[Block No.] " + duty.getBLOCKNO()
			+ System.getProperty("line.separator") + "[Block Name] " + duty.getBLOCKNM()
			+ System.getProperty("line.separator") + "[Field] " + duty.getGTYPE() + " : " + outName
			+ System.getProperty("line.separator") + "[Class] " + e.getClass().toString()
			+ System.getProperty("line.separator") + "[Message] " + e.getMessage();
			throw new BizException(message);
		}
	}

	private int getSpecMaxIdx(Duty duty) {
		int idx = 0;
		String spec = "";

		spec = duty.getSPEC1().trim();	if (! "".equals(spec)) idx = 1;
		spec = duty.getSPEC2().trim();	if (! "".equals(spec)) idx = 2;
		spec = duty.getSPEC3().trim();	if (! "".equals(spec)) idx = 3;
		spec = duty.getSPEC4().trim();	if (! "".equals(spec)) idx = 4;
		spec = duty.getSPEC5().trim();	if (! "".equals(spec)) idx = 5;
		spec = duty.getSPEC6().trim();	if (! "".equals(spec)) idx = 6;
		spec = duty.getSPEC7().trim();	if (! "".equals(spec)) idx = 7;
		spec = duty.getSPEC8().trim();	if (! "".equals(spec)) idx = 8;
		spec = duty.getSPEC9().trim();	if (! "".equals(spec)) idx = 9;
		spec = duty.getSPEC10().trim();	if (! "".equals(spec)) idx = 10;
		spec = duty.getSPEC11().trim();	if (! "".equals(spec)) idx = 11;
		spec = duty.getSPEC12().trim();	if (! "".equals(spec)) idx = 12;
		spec = duty.getSPEC13().trim();	if (! "".equals(spec)) idx = 13;
		spec = duty.getSPEC14().trim();	if (! "".equals(spec)) idx = 14;
		spec = duty.getSPEC15().trim();	if (! "".equals(spec)) idx = 15;
		spec = duty.getSPEC16().trim();	if (! "".equals(spec)) idx = 16;
		spec = duty.getSPEC17().trim();	if (! "".equals(spec)) idx = 17;
		spec = duty.getSPEC18().trim();	if (! "".equals(spec)) idx = 18;
		spec = duty.getSPEC19().trim();	if (! "".equals(spec)) idx = 19;
		spec = duty.getSPEC20().trim();	if (! "".equals(spec)) idx = 20;

		return idx;
	}

	private int getOutMaxIdx(Duty duty) {
		int idx = -1;
		String out = "";

		out = duty.getOUT1().trim();	if (! "".equals(out)) idx = 1;
		out = duty.getOUT2().trim();	if (! "".equals(out)) idx = 2;
		out = duty.getOUT3().trim();	if (! "".equals(out)) idx = 3;
		out = duty.getOUT4().trim();	if (! "".equals(out)) idx = 4;
		out = duty.getOUT5().trim();	if (! "".equals(out)) idx = 5;
		out = duty.getOUT6().trim();	if (! "".equals(out)) idx = 6;
		out = duty.getOUT7().trim();	if (! "".equals(out)) idx = 7;
		out = duty.getOUT8().trim();	if (! "".equals(out)) idx = 8;
		out = duty.getOUT9().trim();	if (! "".equals(out)) idx = 9;
		out = duty.getOUT10().trim();	if (! "".equals(out)) idx = 10;

		return idx;
	}

	/**
	 * ����� ó��, ��Ģ���길 ���� ( + - * / )
	 * @param expr
	 * @return
	 */
	private String calc(Duty duty, HashMap<String, String> mapWork, String expr) {
		JexlEngine jexl = new JexlEngine();
		// ����� Parsing (�������� ���б�ȣ�� '{', '}'�� ����)
	    Expression e = jexl.createExpression(expr.replace("{", "").replace("}", ""));

	    // ����Ŀ� ���Ե� Ư���ڵ带 ���� ex 3300 * {EL_ASTQ}, {EL_EHO} + {EL_EHTRH} + {EL_EHP}
	    List<String> listArgs = new ArrayList<String>();
	    int pos = 0;
	    while (true) {
	    	// �� �̻� ����� ���� ��� ����
			if (0 > expr.indexOf("{", pos)) break;

			int start = expr.indexOf("{", pos) + 1;
			int end = expr.indexOf("}", pos);

			listArgs.add(expr.substring(start, end).trim());

			pos = end + 1;
	    }

	    // �������� ����
	    JexlContext context = new MapContext();
	    for (int i=0; i<listArgs.size(); i++) {
	    	if (null == mapWork.get((String)listArgs.get(i)) || "".equals((String)mapWork.get((String)listArgs.get(i)))) errorRaise(duty, (String)listArgs.get(i));	// ���Ŀ� ����Ǵ� ���� ���� ��� ����

	    	context.set((String)listArgs.get(i), mapWork.get((String)listArgs.get(i)));
	    }

		return String.valueOf(e.evaluate(context));
	}

	/**
	 * ����� ó��, ��Ģ���� �̿��� ó��
	 * @param expr
	 * @return
	 */
	private String calcFix(Duty duty, HashMap<String, String> mapWork, String expr) {
		if (null == expr || "".equals(expr.trim())) return "";

		// [MC]�� �Ҽ��� ��°�ڸ����� �ø��� �� (ex. 5.432356�� �� 5.5)
		if ("#MC1#".equals(expr)) {
			String mc = (String) mapWork.get("XC_MC");
			if (null == mc || "".equals(mc)) errorRaise(duty, "XC_MC");

			BigDecimal bd = new BigDecimal(mc);
			return String.valueOf(bd.setScale(1, BigDecimal.ROUND_FLOOR).add(new BigDecimal("0.1")));
		}
		// [MC]�� �Ҽ��� ù°�ڸ����� �ø��� ���� �� (ex. 5.432356�� �� 6)
		if ("#MC2#".equals(expr)) {
			String mc = (String) mapWork.get("XC_MC");
			if (null == mc || "".equals(mc)) errorRaise(duty, "XC_MC");

			// 2012.11.01  �Ҽ��� 2°�ڸ� �ݿø� �� �ٽ� �Ҽ��� ù°�ڸ����� �ݿø��� ����
			BigDecimal bd = new BigDecimal(mc);
			return String.valueOf( bd.setScale(1, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP) );
		}
		// CRCK
		if ("#CRCK#".equals(expr)) {
			String elEcrl = (String) mapWork.get("EL_ECRL");
			if (null == elEcrl || "".equals(elEcrl)) errorRaise(duty, "EL_ECRL");
			String crck = (String) mapWork.get("XC_CRCK");
			if (null == crck || "".equals(crck)) errorRaise(duty, "XC_CRCK");

			if (Float.parseFloat(elEcrl) >= Float.parseFloat(crck)) {
				return elEcrl;
			} else {
				return crck;
				//return (String) this.mapWork.get("XC_CR1");
			}
		}
		// CWRCK
		if ("#CWRCK#".equals(expr)) {
			String elEcwrl = (String) mapWork.get("EL_ECWRL");
			if (null == elEcwrl || "".equals(elEcwrl)) errorRaise(duty, "EL_ECWRL");
			String cwrck = (String) mapWork.get("XC_CWRCK");
			if (null == cwrck || "".equals(cwrck)) errorRaise(duty, "XC_CWRCK");

			if ("LXVF".equals(duty.getGTYPE()) || "STVF".equals(duty.getGTYPE())) {	// 2012.11.01 LXVF�ݿ�
				// ������ STVF�� ��쿡 EL_ECWRL���� CWRCK���� 7,8�� ���� 7�� ū����
				if ("8".equals(elEcwrl) && "7".equals(cwrck)) {
					return cwrck;
				} else if ("7".equals(elEcwrl) && "8".equals(cwrck)) {
					return elEcwrl;
				} else {
					if (Float.parseFloat(elEcwrl) >= Float.parseFloat(cwrck)) {
						return elEcwrl;
					} else {
						return cwrck;
						//return (String) this.mapWork.get("XC_CR2");
					}
				}
			} else {
				if (Float.parseFloat(elEcwrl) >= Float.parseFloat(cwrck)) {
					return elEcwrl;
				} else {
					return cwrck;
					//return (String) this.mapWork.get("XC_CR2");
				}
			}
		}
		// LWCK
		if ("#LWCK#".equals(expr)) {
			String elErpr = (String) mapWork.get("EL_ERPR");
			if (null == elErpr || "".equals(elErpr)) errorRaise(duty, "EL_ERPR");
			String elErpw = (String) mapWork.get("EL_ERPW");
			if (null == elErpw || "".equals(elErpw)) errorRaise(duty, "EL_ERPW");
			if ("1:1".equals(elErpr)) {
				String xcLwck = (String) mapWork.get("XC_LWCK");
				if (null == xcLwck || "".equals(xcLwck)) errorRaise(duty, "XC_LWCK");
				if (0 <= elErpw.compareTo(xcLwck)) return elErpw;
				else return xcLwck;
			} else {
				return elErpw;
			}
		}
		// EL_ERPW�� �Ҽ��� ù°�ڸ����� �ø��� ���� �� (ex. 5.1�� �� 6)
		if ("#EL_ERPW_CELL0#".equals(expr)) {
			String value = (String) mapWork.get("EL_ERPW");
			if (null == value || "".equals(value)) errorRaise(duty, "EL_ERPW");

			// �Ҽ��� ��°�ڸ����� ����
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_CEILING));
		}

		// 2013.03.07 ���Ӽ����� ó�� �Ҽ��� ù°�ڸ����� �ø��� ���� �� (ex. 5.5�� �� 6)
		if ("#RBQ1#".equals(expr)) {
			String value = (String) mapWork.get("XC_RBQ");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_RBQ");

			BigDecimal bd = new BigDecimal(value + "");
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_HALF_UP));
		}

		// 2013.03.20 ���Ӽ����� ó�� �Ҽ��� ù°�ڸ����� �ø��� ���� �� (ex. 5.5�� �� 6)
		if ("#SPBQ1#".equals(expr)) {
			String value = ((String) mapWork.get("XC_SPBQ")).trim();
			if (null != value && !"".equals(value)) 	{
				BigDecimal bd = new BigDecimal(value + "");
				return String.valueOf(bd.setScale(0, BigDecimal.ROUND_HALF_UP));
			}else	{
				return value;
			}
		}
		// 2014.12.29 ���Ӽ����� ó�� �Ҽ��� ù°�ڸ����� �ø��� ���� �� (ex. 5.5�� �� 6)
		if ("#RAL#".equals(expr)) {
			String value = (String) mapWork.get("XC_RAL");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_RAL");

			// �Ҽ��� ��°�ڸ����� ����
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		if ("#BRKU#".equals(expr)) {
			String value = (String) mapWork.get("XC_RAL2");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_RAL2");

			// �Ҽ��� ��°�ڸ����� ����
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_UP));
		}
		if ("#BRKD#".equals(expr)) {
			String value = (String) mapWork.get("XC_RAL2");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_RAL2");

			// �Ҽ��� ��°�ڸ����� ����
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		
		//RAIL ���� ����
		if ("#V1C#".equals(expr)) {
			String value = (String) mapWork.get("XC_V1C");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_V1C");

			// �Ҽ��� ��°�ڸ����� ����
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;
			
			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		if ("#V1W#".equals(expr)) {
			String value = (String) mapWork.get("XC_V1W");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_V1W");

			// �Ҽ��� ��°�ڸ����� ����
			float x = Float.parseFloat(value);
			x = x * 10;
			x = (float) Math.floor((double)x);
			x = x / 10;
			
			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		//���𵨸� �߰� 5���� �ݿø�
		if ("#R5R#".equals(expr)) {
			String value = (String) mapWork.get("XC_R5R");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_R5R");
			
			float x = Float.parseFloat(value);
			x = x / 10;
			x = (float) (Math.ceil( x * 2 ) / 2);
			x = x * 10;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_UP));
		}
		//���𵨸� �߰� 10���� �ݿø�
		if ("#R10R#".equals(expr)) {
			String value = (String) mapWork.get("XC_R10R");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_R10R");
			
			float x = Float.parseFloat(value);
			
			x = x / 10;
			// round, ceil ?
			x = (float) (Math.round( x ));
			x = x * 10;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_UP));
		}
		//���𵨸� �߰� 10���� ����
		if ("#R10T#".equals(expr)) {
			String value = (String) mapWork.get("XC_R10T");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_R10T");
			
			float x = Float.parseFloat(value);
			
			x = x / 10;
			x = (float) (Math.floor( x ));
			x = x * 10;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		//���𵨸� �߰� 50���� �ݿø�
		if ("#R50R#".equals(expr)) {
			String value = (String) mapWork.get("XC_R50R");
			if (null == value || "".equals(value)) errorRaise(duty, "XC_R50R");
			
			float x = Float.parseFloat(value);
			
			x = x / 100;
			x = (float)Math.ceil( x * 2 ) / 2;
			x = x * 100;

			BigDecimal bd = new BigDecimal((double)x);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_UP));
		}

		return "";
	}

	private void errorRaise(Duty duty, String field) {
		String error = System.getProperty("line.separator") + "[Block No.] " + duty.getBLOCKNO()
					+ System.getProperty("line.separator") + "[Block Name] " + duty.getBLOCKNM()
					+ System.getProperty("line.separator") + "[Field] " + duty.getGTYPE() + " : " + field;

		if (0 > field.indexOf("XC_") &&
			!"PCELV".equals(duty.getGTYPE()) &&
			!"PCESMW".equals(duty.getGTYPE()) &&
			!"PCAP".equals(duty.getGTYPE())) {
			try {
				Map<?, ?> map = this.dao.selectError(duty.getMANDT(), duty.getZPRDGBN(), field, duty.getLANG());
				error = (String) map.get("ATBEZ");

			} catch (Exception e) {	}
		}

		throw new RequireException(error);
	}
}
