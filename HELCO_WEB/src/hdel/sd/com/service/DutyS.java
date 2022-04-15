package hdel.sd.com.service;

import hdel.lib.exception.BizException;
import hdel.lib.exception.NoMatchException;
import hdel.lib.exception.RequireException;
import hdel.lib.service.SrmService;
import hdel.sd.com.dao.DutyD;
import hdel.sd.com.domain.Duty;
import hdel.sd.com.domain.DutyDet;
import hdel.sd.com.domain.DutyOpr;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.tobesoft.platform.data.Dataset;

@Service
public class DutyS extends SrmService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private DutyD dao;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(DutyD.class);
	}


	public HashMap genSpec(String mandt, String gtype, String blockgbn, HashMap mapInput, String lang) {
		// Output Data Map
		HashMap mapOut = new HashMap();

		// Input data����
		HashMap mapWork = (HashMap) mapInput.clone();
		
    	//20190420 spras�߰� by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";
		// Duty���� �о���� (������)
		List<Duty> listDuty = dao.selectDuty(mandt, gtype, blockgbn, spras);

		// Duty�� �������� �ʴ� ��� �޼��� ó��
		if ( listDuty == null )	{
			throw new BizException("No Data Duty Master");
		}
		
		for (int i=0; i<listDuty.size(); i++) {
			Duty duty = listDuty.get(i);
			int specMaxIdx = getSpecMaxIdx( duty );	// ���üũ�׸� ���� ��������
			
			// �ʼ� üũ �� �⺻�� Ȯ��
			try	{
				// 2012.12.27 �޼��� LANG�߰� 
				duty.setLANG("3");

				if (!"ko".equals(lang))		{
					duty.setLANG("E");
				}

				// �������� 3���� ������ ����Ǿ� ����

				if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )	{
					duty.setLANG("3");
				}

				validateDuty(duty, mapWork, mapOut, specMaxIdx);
			}catch	( RuntimeException e )	{
				throw e;
			}
			
			// Duty Detail ���� �о����
			List<DutyDet> listDutyD = dao.selectListDutyD(duty.getMANDT(), duty.getBLOCKNO());

			if ( listDutyD == null )	{
				throw new BizException("No Data Duty Detail");
			}

			// �������
			int matchIdx = getMatchIdx(duty, mapWork, specMaxIdx, listDutyD);
			if (-1 == matchIdx) {
				logger.error(mapWork.toString());
				throw new NoMatchException(duty.getBLOCKNM());	// ������� �� match ���� ��� ����
			}
	
			int outMaxIdx = getOutMaxIdx( duty );	// ���Out�׸� ���� ��������
			
			// ��� �����׸� ����
			getOut(duty, mapWork, mapOut, listDutyD.get(matchIdx), outMaxIdx);
		}
		
		return mapOut;
	}

	// 2013.01.08 ������� ��ǰ���� 
	public HashMap stdGenSpec(String mandt, String gtype, String blockgbn, HashMap mapInput, String lang) {
		// Output Data Map
		HashMap mapOut = new HashMap();

		// Input data����
		HashMap mapWork = (HashMap) mapInput.clone();
		
    	//20190420 spras�߰� by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";
		// Duty���� �о���� (������)
		List<Duty> listDuty = dao.selectDuty(mandt, gtype, blockgbn, spras);

		// Duty�� �������� �ʴ� ���� ó��
		if ( listDuty == null )	{
			return null;
		}
		
		for (int i = 0; i < listDuty.size(); i++) {
			Duty duty = listDuty.get(i);
			int specMaxIdx = getSpecMaxIdx( duty );	// ���üũ�׸� ���� ��������

			// �ʼ� üũ �� �⺻�� Ȯ��
			try	{
				duty.setLANG("3");

				if (!"ko".equals(lang))		{
					duty.setLANG("E");
				}

				// �������� 3���� ������ ����Ǿ� ����
				if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )	{
					duty.setLANG("3");
				}

				validateDuty(duty, mapWork, mapOut, specMaxIdx);
			}catch	( RuntimeException e )	{
				throw e;
			}
			
			// Duty Detail ���� �о����
			List<DutyDet> listDutyD = dao.selectListDutyD(duty.getMANDT(), duty.getBLOCKNO());

			if ( listDutyD == null )	{
				return null;
			}

			// �������
			int matchIdx = getMatchIdx(duty, mapWork, specMaxIdx, listDutyD);
			if (-1 == matchIdx) {
				logger.error(mapWork.toString());
				throw new NoMatchException(duty.getBLOCKNM());	// ������� �� match ���� ��� ����
			}
	
			int outMaxIdx = getOutMaxIdx( duty );	// ���Out�׸� ���� ��������
			
			// ��� �����׸� ����
			getOut(duty, mapWork, mapOut, listDutyD.get(matchIdx), outMaxIdx);
		}
		
		return mapOut;
	}

	// 2013.03.06 ���Ӽ� ó���� (ǥ��Template ȣ�� �� ó�������� �и�)
	public HashMap dependSpec(String mandt, String gtype, String blockgbn, HashMap mapInput, String lang) {
		// Output Data Map
		HashMap mapOut = new HashMap();

		// Input data����
		HashMap mapWork = (HashMap) mapInput.clone();
		
    	//20190420 spras�߰� by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";
		// Duty���� �о���� (������)
		List<Duty> listDuty = dao.selectDuty(mandt, gtype, blockgbn, spras);

		// Duty�� �������� �ʴ� ���� ó��
		if ( listDuty == null )	{
			return null;
		}
		
		for (int i = 0; i < listDuty.size(); i++) {
			Duty duty = listDuty.get(i);
			int specMaxIdx = getSpecMaxIdx( duty );	// ���üũ�׸� ���� ��������

			// �ʼ� üũ �� �⺻�� Ȯ��
			try	{
				duty.setLANG("3");

				if (!"ko".equals(lang))		{
					duty.setLANG("E");
				}

				// �������� 3���� ������ ����Ǿ� ����
				if ("SHN".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )	{
					duty.setLANG("3");
				}

				validateDuty(duty, mapWork, mapOut, specMaxIdx);
			}catch	( RuntimeException e )	{
				throw e;
			}
			
			// Duty Detail ���� �о����
			List<DutyDet> listDutyD = dao.selectListDutyD(duty.getMANDT(), duty.getBLOCKNO());

			if ( listDutyD == null )	{
				return null;
			}

			// �������
			int matchIdx = getMatchIdx(duty, mapWork, specMaxIdx, listDutyD);
			if (-1 == matchIdx) {
				logger.error(mapWork.toString());
				throw new NoMatchException(duty.getBLOCKNM());	// ������� �� match ���� ��� ����
			}
	
			int outMaxIdx = getOutMaxIdx( duty );	// ���Out�׸� ���� ��������
			
			// ��� �����׸� ����
			getOut(duty, mapWork, mapOut, listDutyD.get(matchIdx), outMaxIdx);
		}
		
		return mapOut;
	}

	// ���SPEC���� �� �ʼ� �Է°� üũ ����
	// ���� Q, ��� P
	public boolean genSpecCheck(String mandt, String flag, String item, Dataset template, Dataset log, String lang) {
		boolean rtnValue = true;

		HashMap mapInput = new HashMap();
        
		String sItem = "";
		int    cnt   = 0;
		
		if ("Q".equals(flag))	{
			for (int i = 0; i < template.getRowCount(); i++) {
				sItem = template.getColumnAsString(i, "QTSEQ");
				if(!item.equals(sItem))	{
					continue;
				}

				String value = template.getColumnAsString(i, "VALUE");	// ������ PRD -> VALUE
				if (null != value && ! "".equals(value)) {
					mapInput.put(template.getColumnAsString(i, "ATNAM"), value);	// ������ PRH -> ATNAM
				}
			}
		}else	{
			sItem = template.getColumnAsString(0, "HOGI");

			for (int i = 0; i < template.getRowCount(); i++) {

				if(!item.equals(sItem))	{
					continue;
				}

				String value = template.getColumnAsString(i, "PRD");
				if (null != value && ! "".equals(value)) {
					mapInput.put(template.getColumnAsString(i, "PRH"), value);
				}
			}
		}

		// Output Data Map
		HashMap mapOut = new HashMap();

		// Input data����
		HashMap mapWork = (HashMap) mapInput.clone();

		String gtype = "";
		String sGubun = "";

		// 2013.01.29 Duty �� Ÿ�� ���� �߰�
		String sGubun1 = (String) mapWork.get("EL_DITEMUSE");
		String sGubun2 = (String) mapWork.get("EL_ASPLY");
		String sGubun3 =  template.getColumnAsString(0, "PSPID");
		if ( sGubun1 == null ) sGubun1 = "";
		if ( sGubun2 == null ) sGubun2 = "";
		if ( sGubun1.equals("Y") || sGubun2.equals("Y") ) {
			return true;
		}
		if (sGubun3.equals("126394")){ //20131030 kt û������ ��ǥ������ �ʼ�üũ ���� -�輱ȣ
			return true;
		}

		sGubun = (String) mapWork.get("EL_ATYP");
		if (sGubun != null && !"".equals(sGubun))	{
			gtype = "ELV1";

			// ���ڿ� ó��
			if ("STSH1".equals(sGubun) || "STSH5".equals(sGubun))	{
				gtype = "ELV2";
			}
		}else {
			// �����÷�����
			sGubun = (String) mapWork.get("ES_ATYP");
			if (sGubun != null && !"".equals(sGubun))	{
				gtype = "ESC1";	// �����÷����ʹ� Default�� ó��
			}else {
				// ������ũ
				sGubun = (String) mapWork.get("MW_ATYP");
				if (sGubun != null && !"".equals(sGubun))	{
					gtype = "MWK1";	// ������ũ�� Default�� ó��
				} else {
					sGubun = (String) mapInput.get("AP_ATYP");
					if (sGubun != null && !"".equals(sGubun))	{
						gtype = "ZPK1";	// ���� Default�� ó��
					} else	{	// 2013.01.03 �̿ܿ��� �ʼ� üũ ���ʿ�
						return true;
					}
				}
			}
		}
		

		if (null == gtype || "".equals(gtype))	 {
			if (!"ko".equals(lang))		{
				throw new RequireException("TYPE");
			}else	{
				throw new RequireException("����");
			}
		}

		//gtype = gtype + "_CHECK";
    	//20190420 spras�߰� by lyk
    	String spras = "3";
		if (lang.equals("ko")) spras = "3";
		else                     spras = "E";

		// Duty���� �о���� (������)
		List<Duty> listDuty = dao.selectDuty(mandt, gtype, "", spras);

		// Duty�� �������� �ʴ� ��� �޼��� ó��
		if ( listDuty == null || listDuty.size() == 0)	{
			rtnValue = false;
			//throw new BizException("No Data Duty Master");
		}else	{

			for (int i = 0; i < listDuty.size(); i++) {
				Duty duty = listDuty.get(i);
	
				int specMaxIdx = getSpecMaxIdx( duty );	// ���üũ�׸� ���� ��������
				
				// �ʼ� üũ �� �⺻�� Ȯ��
				try	{
					duty.setLANG("3");
	
					if (!"ko".equals(lang))		{
						duty.setLANG("E");
					}

					// �������� 3���� ������ ����Ǿ� ����
					if ("SHN_01".equals(duty.getZPRDGBN().substring(0, 3)) || "SHN_02".equals(duty.getZPRDGBN().substring(0, 3)) )	{
						duty.setLANG("3");
					}
	
					//validateDutyCheck(duty, mapWork, specMaxIdx); �ʼ� üũ���� �Է� ���� üũ ���濡 ���� ���ʿ�
					validateDuty(duty, mapWork, mapOut, specMaxIdx);
				}catch	( RuntimeException e )	{
					e.printStackTrace();
					rtnValue = false;
					throw e;
				}
				
				// Duty Detail ���� �о����
				List<DutyDet> listDutyD = dao.selectListDutyD(duty.getMANDT(), duty.getBLOCKNO());

				if ( listDutyD != null && listDutyD.size() > 0 )	{
					// �������
					int matchIdx = getMatchIdx(duty, mapWork, specMaxIdx, listDutyD);
					if (-1 == matchIdx) {
						logger.error(mapWork.toString());
						//throw new NoMatchException(duty.getBLOCKNM());	// ������� �� match ���� ��� ����   -�ӽ� �輱ȣ 201504

						log.appendRow();
						log.setColumn(cnt, "IDX", cnt + 1);
						log.setColumn(cnt, "LOGMSG",  "[" + item + "]: " + duty.getBLOCKNM());
						cnt++;
						
					} else {

						int outMaxIdx = getOutMaxIdx( duty );	// ���Out�׸� ���� ��������
					
						// ��� �����׸� ����
						getOut(duty, mapWork, mapOut, listDutyD.get(matchIdx), outMaxIdx);
					}
				}
			}
		}
		
		return rtnValue;
	}

	// HashMap mapWork �� in/out ó��
	// HashMap mapOut  �� in/out ó��
	private void validateDuty(Duty duty, HashMap mapWork, HashMap mapOut, int specMaxIdx) {
		String value = "";
		
		if (0 == specMaxIdx) return;

		try	{
		
			if (1 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC1());
	
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS1())) {
						errorRaise(duty, duty.getSPEC1());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV1())) {
						mapOut.put(duty.getSPEC1(), duty.getSPECDEFV1());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC1(), duty.getSPECDEFV1());
					}
				}
			}
			if (2 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC2());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS2())) {
						errorRaise(duty, duty.getSPEC2());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV2())) {
						mapOut.put(duty.getSPEC2(), duty.getSPECDEFV2());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC2(), duty.getSPECDEFV2());
					}
				}
			}
			if (3 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC3());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS3())) {
						errorRaise(duty, duty.getSPEC3());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV3())) {
						mapOut.put(duty.getSPEC3(), duty.getSPECDEFV3());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC3(), duty.getSPECDEFV3());
					}
				}
			}
			if (4 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC4());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS4())) {
						errorRaise(duty, duty.getSPEC4());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV4())) {
						mapOut.put(duty.getSPEC4(), duty.getSPECDEFV4());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC4(), duty.getSPECDEFV4());
					}
				}
			}
			if (5 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC5());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS5())) {
						errorRaise(duty, duty.getSPEC5());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV5())) {
						mapOut.put(duty.getSPEC5(), duty.getSPECDEFV5());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC5(), duty.getSPECDEFV5());
					}
				}
			}
			if (6 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC6());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS6())) {
						errorRaise(duty, duty.getSPEC6());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV6())) {
						mapOut.put(duty.getSPEC6(), duty.getSPECDEFV6());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC6(), duty.getSPECDEFV6());
					}
				}
			}
			if (7 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC7());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS7())) {
						errorRaise(duty, duty.getSPEC7());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV7())) {
						mapOut.put(duty.getSPEC7(), duty.getSPECDEFV7());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC7(), duty.getSPECDEFV7());
					}
				}
			}
			if (8 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC8());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS8())) {
						errorRaise(duty, duty.getSPEC8());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV8())) {
						mapOut.put(duty.getSPEC8(), duty.getSPECDEFV8());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC8(), duty.getSPECDEFV8());
					}
				}
			}
			if (9 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC9());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS9())) {
						errorRaise(duty, duty.getSPEC9());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV9())) {
						mapOut.put(duty.getSPEC9(), duty.getSPECDEFV9());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC9(), duty.getSPECDEFV9());
					}
				}
			}
			if (10 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC10());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS10())) {
						errorRaise(duty, duty.getSPEC10());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV10())) {
						mapOut.put(duty.getSPEC10(), duty.getSPECDEFV10());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC10(), duty.getSPECDEFV10());
					}
				}
			}
			if (11 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC11());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS11())) {
						errorRaise(duty, duty.getSPEC11());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV11())) {
						mapOut.put(duty.getSPEC11(), duty.getSPECDEFV11());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC11(), duty.getSPECDEFV11());
					}
				}
			}
			if (12 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC12());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS12())) {
						errorRaise(duty, duty.getSPEC12());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV12())) {
						mapOut.put(duty.getSPEC12(), duty.getSPECDEFV12());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC12(), duty.getSPECDEFV12());
					}
				}
			}
			if (13 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC13());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS13())) {
						errorRaise(duty, duty.getSPEC13());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV13())) {
						mapOut.put(duty.getSPEC13(), duty.getSPECDEFV13());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC13(), duty.getSPECDEFV13());
					}
				}
			}
			if (14 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC14());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS14())) {
						errorRaise(duty, duty.getSPEC14());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV14())) {
						mapOut.put(duty.getSPEC14(), duty.getSPECDEFV14());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC14(), duty.getSPECDEFV14());
					}
				}
			}
			if (15 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC15());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS15())) {
						errorRaise(duty, duty.getSPEC15());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV15())) {
						mapOut.put(duty.getSPEC15(), duty.getSPECDEFV15());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC15(), duty.getSPECDEFV15());
					}
				}
			}
			if (16 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC16());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS16())) {
						errorRaise(duty, duty.getSPEC16());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV16())) {
						mapOut.put(duty.getSPEC16(), duty.getSPECDEFV16());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC16(), duty.getSPECDEFV16());
					}
				}
			}
			if (17 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC17());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS17())) {
						errorRaise(duty, duty.getSPEC17());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV17())) {
						mapOut.put(duty.getSPEC17(), duty.getSPECDEFV17());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC17(), duty.getSPECDEFV17());
					}
				}
			}
			if (18 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC18());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS18())) {
						errorRaise(duty, duty.getSPEC18());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV18())) {
						mapOut.put(duty.getSPEC18(), duty.getSPECDEFV18());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC18(), duty.getSPECDEFV18());
					}
				}
			}
			if (19 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC19());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS19())) {
						errorRaise(duty, duty.getSPEC19());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV19())) {
						mapOut.put(duty.getSPEC19(), duty.getSPECDEFV19());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC19(), duty.getSPECDEFV19());
					}
				}
			}
			if (20 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC20());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS20())) {
						errorRaise(duty, duty.getSPEC20());
					}
					// Duty�� ���⺻���� ������ ��� �⺻�� ����
					if (! "".equals(duty.getSPECDEFV20())) {
						mapOut.put(duty.getSPEC20(), duty.getSPECDEFV20());	// Default �κ��� ��� �ǵ��� �߰� 2012.10.12
						mapWork.put(duty.getSPEC20(), duty.getSPECDEFV20());
					}
				}
			}
		}catch	( RuntimeException e )	{
			throw e;
		}
	}

	// �ʼ�üũ �뵵 Ȱ��(Default �� ó�� ����)
	private void validateDutyCheck(Duty duty, HashMap mapWork, int specMaxIdx) {
		String value = "";
		
		if (0 == specMaxIdx) return;

		try	{
		
			if (1 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC1());
	
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS1())) {
						errorRaise(duty, duty.getSPEC1());
					}
				}
			}
			if (2 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC2());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS2())) {
						errorRaise(duty, duty.getSPEC2());
					}
				}
			}
			if (3 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC3());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS3())) {
						errorRaise(duty, duty.getSPEC3());
					}
				}
			}
			if (4 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC4());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS4())) {
						errorRaise(duty, duty.getSPEC4());
					}
				}
			}
			if (5 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC5());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS5())) {
						errorRaise(duty, duty.getSPEC5());
					}
				}
			}
			if (6 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC6());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS6())) {
						errorRaise(duty, duty.getSPEC6());
					}
				}
			}
			if (7 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC7());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS7())) {
						errorRaise(duty, duty.getSPEC7());
					}
				}
			}
			if (8 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC8());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS8())) {
						errorRaise(duty, duty.getSPEC8());
					}
				}
			}
			if (9 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC9());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS9())) {
						errorRaise(duty, duty.getSPEC9());
					}
				}
			}
			if (10 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC10());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS10())) {
						errorRaise(duty, duty.getSPEC10());
					}
				}
			}
			if (11 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC11());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS11())) {
						errorRaise(duty, duty.getSPEC11());
					}
				}
			}
			if (12 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC12());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS12())) {
						errorRaise(duty, duty.getSPEC12());
					}
				}
			}
			if (13 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC13());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS13())) {
						errorRaise(duty, duty.getSPEC13());
					}
				}
			}
			if (14 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC14());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS14())) {
						errorRaise(duty, duty.getSPEC14());
					}
				}
			}
			if (15 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC15());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS15())) {
						errorRaise(duty, duty.getSPEC15());
					}
				}
			}
			if (16 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC16());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS16())) {
						errorRaise(duty, duty.getSPEC16());
					}
				}
			}
			if (17 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC17());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS17())) {
						errorRaise(duty, duty.getSPEC17());
					}
				}
			}
			if (18 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC18());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS18())) {
						errorRaise(duty, duty.getSPEC18());
					}
				}
			}
			if (19 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC19());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS19())) {
						errorRaise(duty, duty.getSPEC19());
					}
				}
			}
			if (20 <= specMaxIdx) {
				value = (String) mapWork.get(duty.getSPEC20());
				// ��� �Է°��� ���� ���
				if (null == value || "".equals(value)) {
					// Duty�� ��簪�� �ʼ��� ��� ���� ó��
					if ("X".equals(duty.getSPECESS20())) {
						errorRaise(duty, duty.getSPEC20());
					}
				}
			}
		}catch	( RuntimeException e )	{
			throw e;
		}
	}
	
	private int getMatchIdx(Duty duty, HashMap mapWork, int specMaxIdx, List<DutyDet> listDutyD) {
		int matchIdx = -1;
		
		// Duty�� Spec�� �������� ���� ���
		if (0 == specMaxIdx) {
			// Spec ����Ʈ�� 1���� ���� �ش� ���ϰ����� ����
			if (1 == listDutyD.size()) return 0;
			// �̿ܿ��� ���� (0 �Ǵ� ������ ó�� �Ұ�)
			else throw new BizException("No Spec & Multi Out Exception");
		}
		
		for (int i = 0; i < listDutyD.size(); i++) {
			boolean match = false;
			DutyDet dutyDet = listDutyD.get(i);
			
			if (1 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC1()), dutyDet.getSPEC1(), mapWork);
				if (! match) continue;
				if (1 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (2 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC2()), dutyDet.getSPEC2(), mapWork);
				if (! match) continue;
				if (2 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (3 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC3()), dutyDet.getSPEC3(), mapWork);
				if (! match) continue;
				if (3 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (4 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC4()), dutyDet.getSPEC4(), mapWork);
				if (! match) continue;
				if (4 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (5 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC5()), dutyDet.getSPEC5(), mapWork);
				if (! match) continue;
				if (5 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (6 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC6()), dutyDet.getSPEC6(), mapWork);
				if (! match) continue;
				if (6 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (7 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC7()), dutyDet.getSPEC7(), mapWork);
				if (! match) continue;
				if (7 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (8 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get( duty.getSPEC8()), dutyDet.getSPEC8(), mapWork);
				if (! match) continue;
				if (8 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (9 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get( duty.getSPEC9()), dutyDet.getSPEC9(), mapWork);
				if (! match) continue;
				if (9 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (10 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC10()), dutyDet.getSPEC10(), mapWork);
				if (! match) continue;
				if (10 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (11 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC11()), dutyDet.getSPEC11(), mapWork);
				if (! match) continue;
				if (11 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (12 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC12()), dutyDet.getSPEC12(), mapWork);
				if (! match) continue;
				if (12 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (13 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get( duty.getSPEC13()), dutyDet.getSPEC13(), mapWork);
				if (! match) continue;
				if (13 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (14 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC14()), dutyDet.getSPEC14(), mapWork);
				if (! match) continue;
				if (14 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (15 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC15()), dutyDet.getSPEC15(), mapWork);
				if (! match) continue;
				if (15 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (16 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC16()), dutyDet.getSPEC16(), mapWork);
				if (! match) continue;
				if (16 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (17 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC17()), dutyDet.getSPEC17(), mapWork);
				if (! match) continue;
				if (17 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (18 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get( duty.getSPEC18()), dutyDet.getSPEC18(), mapWork);
				if (! match) continue;
				if (18 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (19 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC19()), dutyDet.getSPEC19(), mapWork);
				if (! match) continue;
				if (19 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
			if (20 <= specMaxIdx) {
				match = DutyOpr.compare((String) mapWork.get(duty.getSPEC20()), dutyDet.getSPEC20(), mapWork);
				if (! match) continue;
				if (20 == specMaxIdx && match) {
					matchIdx = i;
					break;
				}
			}
		}
		return matchIdx;
	}

	// outValue ���ȭ ����
	private String getOutValue(String outName, Duty duty, String outDutyD, HashMap mapWork)	{

		String outValue = "";

		
		
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= outDutyD.indexOf("|")) {
				outValue = outDutyD.replace("|", "");
		    // ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) 1900 + {EL_ECCH}
			} else if (0 <= outDutyD.indexOf("+") ||
				0 <= outDutyD.indexOf("-") ||
				0 <= outDutyD.indexOf("*") ||
				0 <= outDutyD.indexOf("/")) {
				if ("ES".equals(outName.substring(0,2))){
					outValue = outDutyD;
				}else if("GTYPE".equals(outName)){
					outValue = outDutyD; //GTYPE OUT2 ���ڿ�'-'�������� ���� ���� ���� jss
				}else {
					if ("R/L".equals(outDutyD) || "R/R".equals(outDutyD)){
						// CAR GOVERNOR ��ġ ���� R/L R/R -> ���� ������� 0 ���� ��µǴ� ���� ���� 20141229 �輱ȣ
						outValue = outDutyD;
					}else{
						outValue = calc(duty, mapWork, outDutyD);
				    }
				}	
		    // ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= outDutyD.indexOf("#")) {
				outValue = calcFix( duty, mapWork, outDutyD);
		    // ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
		    } else if (0 <= outDutyD.indexOf("{") ||
		    		0 <= outDutyD.indexOf("}") ) {
		    	outValue = (String) mapWork.get(outDutyD.replace("{", "").replace("}", ""));
		    } else {
		    	outValue = outDutyD;
		    }
		
		return outValue;
	}

	// HashMap mapWork �� in/out ó��
	// HashMap mapOut  �� in/out ó��
	private void getOut(Duty duty, HashMap mapWork, HashMap mapOut, DutyDet dutyDet, int outMaxIdx) {
		String outName = "";
		String outValue = "";

		if (1 <= outMaxIdx) {

			outName = duty.getOUT1();
			// outValue ���ȭ ����.
			outValue = getOutValue(outName, duty, dutyDet.getOUT1(), mapWork);

			/*
			outName = duty.getOUT1();
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= dutyDet.getOUT1().indexOf("|")) {
				outValue = dutyDet.getOUT1().replace("|", "");
			// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) 1900 + {EL_ECCH}
			} else if (0 <= dutyDet.getOUT1().indexOf("+") ||
				0 <= dutyDet.getOUT1().indexOf("-") ||
				0 <= dutyDet.getOUT1().indexOf("*") ||
				0 <= dutyDet.getOUT1().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT1());
			// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= dutyDet.getOUT1().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT1());
			// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT1().indexOf("{") ||
					   0 <= dutyDet.getOUT1().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT1().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT1();
			}
			*/

			// 2012.10.29 ī������ ��� 1���ڸ����� ������ �ø� ��� 1184.88 -> 1190���� �����ó��
			// ó�� group -> 01, ���� out1�� EL_ECW�� ��츸 ó�� ==> ī����
			if("01".equals(duty.getBLOCKGBN()))	{
				if ("EL_ECW".equals(outName))	{
					BigDecimal bd = new BigDecimal(outValue);
					outValue = (String) (bd.setScale(-1, BigDecimal.ROUND_UP).intValue() + "");
				}
			}

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (2 <= outMaxIdx) {
			outName = duty.getOUT2();
			// outValue ���ȭ ����.
			outValue = getOutValue(outName, duty, dutyDet.getOUT2(), mapWork);

			/*
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= dutyDet.getOUT2().indexOf("|")) {
				outValue = dutyDet.getOUT2().replace("|", "");
			// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����
			} else if (0 <= dutyDet.getOUT2().indexOf("+") ||
				0 <= dutyDet.getOUT2().indexOf("-") ||
				0 <= dutyDet.getOUT2().indexOf("*") ||
				0 <= dutyDet.getOUT2().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT2());
			// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= dutyDet.getOUT2().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT2());
			// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT2().indexOf("{") ||
					   0 <= dutyDet.getOUT2().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT2().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT2();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (3 <= outMaxIdx) {
			outName = duty.getOUT3();
			// outValue ���ȭ ����.
			outValue = getOutValue(outName, duty, dutyDet.getOUT3(), mapWork);

			/*
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= dutyDet.getOUT3().indexOf("|")) {
				outValue = dutyDet.getOUT3().replace("|", "");
			// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����
			} else if (0 <= dutyDet.getOUT3().indexOf("+") ||
				0 <= dutyDet.getOUT3().indexOf("-") ||
				0 <= dutyDet.getOUT3().indexOf("*") ||
				0 <= dutyDet.getOUT3().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT3());
			// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= dutyDet.getOUT3().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT3());
			// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT3().indexOf("{") ||
					   0 <= dutyDet.getOUT3().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT3().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT3();
			}
			 */

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (4 <= outMaxIdx) {
			outName = duty.getOUT4();
			// outValue ���ȭ ����.
			outValue = getOutValue(outName, duty, dutyDet.getOUT4(), mapWork);

			/*
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= dutyDet.getOUT4().indexOf("|")) {
				outValue = dutyDet.getOUT4().replace("|", "");
			// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����
			} else if (0 <= dutyDet.getOUT4().indexOf("+") ||
				0 <= dutyDet.getOUT4().indexOf("-") ||
				0 <= dutyDet.getOUT4().indexOf("*") ||
				0 <= dutyDet.getOUT4().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT4());
			// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= dutyDet.getOUT4().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT4());
			// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT4().indexOf("{") ||
					   0 <= dutyDet.getOUT4().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT4().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT4();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (5 <= outMaxIdx) {
			outName = duty.getOUT5();
			// outValue ���ȭ ����.
			outValue = getOutValue(outName, duty, dutyDet.getOUT5(), mapWork);

			/*
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= dutyDet.getOUT5().indexOf("|")) {
				outValue = dutyDet.getOUT5().replace("|", "");
			// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����
			} else if (0 <= dutyDet.getOUT5().indexOf("+") ||
				0 <= dutyDet.getOUT5().indexOf("-") ||
				0 <= dutyDet.getOUT5().indexOf("*") ||
				0 <= dutyDet.getOUT5().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT5());
			// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= dutyDet.getOUT5().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT5());
			// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT5().indexOf("{") ||
					   0 <= dutyDet.getOUT5().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT5().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT5();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (6 <= outMaxIdx) {
			outName = duty.getOUT6();
			// outValue ���ȭ ����.
			outValue = getOutValue(outName, duty, dutyDet.getOUT6(), mapWork);

			/*
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= dutyDet.getOUT6().indexOf("|")) {
				outValue = dutyDet.getOUT6().replace("|", "");
			// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����
			} else if (0 <= dutyDet.getOUT6().indexOf("+") ||
				0 <= dutyDet.getOUT6().indexOf("-") ||
				0 <= dutyDet.getOUT6().indexOf("*") ||
				0 <= dutyDet.getOUT6().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT6());
			// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= dutyDet.getOUT6().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT6());
			// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT6().indexOf("{") ||
					   0 <= dutyDet.getOUT6().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT6().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT6();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (7 <= outMaxIdx) {
			outName = duty.getOUT7();
			// outValue ���ȭ ����.
			outValue = getOutValue(outName, duty, dutyDet.getOUT7(), mapWork);

			/*
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= dutyDet.getOUT7().indexOf("|")) {
				outValue = dutyDet.getOUT7().replace("|", "");
			// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����
			} else if (0 <= dutyDet.getOUT7().indexOf("+") ||
				0 <= dutyDet.getOUT7().indexOf("-") ||
				0 <= dutyDet.getOUT7().indexOf("*") ||
				0 <= dutyDet.getOUT7().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT7());
			// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= dutyDet.getOUT7().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT7());
			// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT7().indexOf("{") ||
					   0 <= dutyDet.getOUT7().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT7().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT7();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (8 <= outMaxIdx) {
			outName = duty.getOUT8();
			// outValue ���ȭ ����.
			outValue = getOutValue(outName, duty, dutyDet.getOUT8(), mapWork);

			/*
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= dutyDet.getOUT8().indexOf("|")) {
				outValue = dutyDet.getOUT8().replace("|", "");
			// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����
			} else if (0 <= dutyDet.getOUT8().indexOf("+") ||
				0 <= dutyDet.getOUT8().indexOf("-") ||
				0 <= dutyDet.getOUT8().indexOf("*") ||
				0 <= dutyDet.getOUT8().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT8());
			// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= dutyDet.getOUT8().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT8());
			// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT8().indexOf("{") ||
					   0 <= dutyDet.getOUT8().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT8().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT8();
			}
			*/
			
			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (9 <= outMaxIdx) {
			outName = duty.getOUT9();
			// outValue ���ȭ ����.
			outValue = getOutValue(outName, duty, dutyDet.getOUT9(), mapWork);

			/*
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= dutyDet.getOUT9().indexOf("|")) {
				outValue = dutyDet.getOUT9().replace("|", "");
			// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����
			} else if (0 <= dutyDet.getOUT9().indexOf("+") ||
				0 <= dutyDet.getOUT9().indexOf("-") ||
				0 <= dutyDet.getOUT9().indexOf("*") ||
				0 <= dutyDet.getOUT9().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT9());
			// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= dutyDet.getOUT9().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT9());
			// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT9().indexOf("{") ||
					   0 <= dutyDet.getOUT9().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT9().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT9();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
		}
		if (10 <= outMaxIdx) {
			outName = duty.getOUT10();
			// outValue ���ȭ ����.
			outValue = getOutValue(outName, duty, dutyDet.getOUT10(), mapWork);

			/*
			// ���Ⱚ�� '|' �� ���� �ش� ���� �״�� ���, ��ȣ�� ����
			if (0 <= dutyDet.getOUT10().indexOf("|")) {
				outValue = dutyDet.getOUT10().replace("|", "");
			// ���Ⱚ�� ����(��Ģ����)�� ��� ������ ���� �� ���Ⱚ ����
			} else if (0 <= dutyDet.getOUT10().indexOf("+") ||
				0 <= dutyDet.getOUT10().indexOf("-") ||
				0 <= dutyDet.getOUT10().indexOf("*") ||
				0 <= dutyDet.getOUT10().indexOf("/")) {
				outValue = calc(duty, mapWork, dutyDet.getOUT10());
			// ���Ⱚ�� ����(���Ģ����)�� ��� ������ ���� �� ���Ⱚ ����, ex) #MC1#
			} else if (0 <= dutyDet.getOUT10().indexOf("#")) {
				outValue = calcFix( duty, mapWork, dutyDet.getOUT10());
			// ���Ⱚ�� Ư���� �Ͽ�, ex) {EL_ECWAD}
			} else if (0 <= dutyDet.getOUT10().indexOf("{") ||
					   0 <= dutyDet.getOUT10().indexOf("}") ) {
				outValue = (String) mapWork.get(dutyDet.getOUT10().replace("{", "").replace("}", ""));
			} else {
				outValue = dutyDet.getOUT10();
			}
			*/

			mapOut.put(outName, outValue);
			mapWork.put(outName, outValue);
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
	private String calc(Duty duty, HashMap mapWork, String expr) {
		JexlEngine jexl = new JexlEngine();
		// ����� Parsing (�������� ���б�ȣ�� '{', '}'�� ����)
	    Expression e = jexl.createExpression(expr.replace("{", "").replace("}", ""));
	    
	    // ����Ŀ� ���Ե� Ư���ڵ带 ���� ex 3300 * {EL_ASTQ}, {EL_EHO} + {EL_EHTRH} + {EL_EHP}
	    List listArgs = new ArrayList();
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
	private String calcFix(Duty duty, HashMap mapWork, String expr) {
		if (null == expr || "".equals(expr.trim())) return "";
		
		// [MC]�� �Ҽ��� ��°�ڸ����� ���� �� + 0.1 (ex. 5.402356�� �� 5.5)
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
			
			// �Ҽ��� ��°�ڸ����� ����
			//float x = Float.parseFloat(mc);
			//x = x * 10;
			//x = (float) Math.floor((double)x);
			//x = x / 10;
			
			//BigDecimal bd = new BigDecimal((double)x);
			//return String.valueOf(bd.setScale(0, BigDecimal.ROUND_CEILING));
			//return String.valueOf((long) Math.ceil(Double.parseDouble(mc)));
			
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
//			float x = Float.parseFloat(value);
//			long xl = (long) Math.ceil((double)x);
//			return String.valueOf(xl);
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
			logger.info("test #V1C#"+bd);
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
			logger.info("test #V1W#"+bd);
			return String.valueOf(bd.setScale(0, BigDecimal.ROUND_DOWN));
		}
		return "";
	}
	
	private void errorRaise(Duty duty, String field) {
		String error = "require field : " + duty.getGTYPE() + " : " + field;
		
		if (0 > field.indexOf("XC_") &&
			!"PCELV".equals(duty.getGTYPE()) &&
			!"PCESMW".equals(duty.getGTYPE()) &&
			!"PCAP".equals(duty.getGTYPE())) {
			try {
				Map map = this.dao.selectError(duty.getMANDT(), duty.getZPRDGBN(), field, duty.getLANG());
				error = (String) map.get("ATBEZ");
				
				Logger logger = Logger.getLogger(this.getClass());
				logger.debug("require field : " + map.get("CLASSNM") + " " + map.get("ATKLA") + " " + map.get("ATBEZ"));
			} catch (Exception e) {	}
		}
		
		throw new RequireException(error);
	}
}
