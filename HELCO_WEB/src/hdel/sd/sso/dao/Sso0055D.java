package hdel.sd.sso.dao;

import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.sso.domain.Sso0055ParamVO;
import hdel.sd.sso.domain.Sso0055VO;
import hdel.sd.sso.domain.ZSDS0063;
import hdel.sd.sso.domain.ZSDT0090;
import hdel.sd.sso.domain.ZSDT0091;
import hdel.sd.sso.domain.ZSDT0092;
import hdel.sd.sso.domain.ZSDT0093;
import hdel.sd.sso.domain.ZSDT0094;

import java.util.List;

/**
 * ���ֺ���
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0055D { 

	public List<ZSDS0063> SelectQtdat(ZSDS0063 param);	  // ������ , ���������� 
	public List<ZSDS0063> SelectProject(ZSDS0063 param);	  // ������Ʈ ������
	public List<ZSDT0090> SelectHeader(ZSDT0090 param);		  // ��ຯ�� header
	public List<ZSDT0091> SelectItem(ZSDT0091 param);		  // ��ຯ�� item
	public List<ZSDT0092> SelectBillO(ZSDT0092 param);		  // ��ຯ�� billing original
	public List<ZSDT0093> SelectBillC(ZSDT0093 param);		  // ��ຯ�� billing change
	public List<ZSDT0094> SelectSpecC(ZSDT0094 param);		  // ��ຯ�� spec change

	public int saveHeader(ZSDT0090 param);                    // ��ຯ�� header save
	public int saveItem(ZSDT0091 param);                    // ��ຯ�� item save
	public int saveBillO(ZSDT0092 param);                    // ��ຯ�� billing original
	public int saveBillC(ZSDT0093 param);                    // ��ຯ�� billing change
	public int saveSpecC(ZSDT0094 param);                    // ��ຯ�� spec chage
	public List<Sso0055VO> getRecad_da(Sso0055ParamVO param);                  
	
	public List<Sso0055VO> SelectSayang(Sso0055ParamVO param);		// ����
	public List<Sso0055VO> SelectVbeln(Sso0055ParamVO param);		// ����
	public List<Sso0055VO> SelectVbeln2(Sso0055ParamVO param);		// ����
	public List<Sso0055VO> SelectMaxSeq(Sso0055ParamVO param);	  // ��ຯ�� ����
	public List<Sso0055VO> SelectSayangForPrint(Sso0055ParamVO param);
	public void setCostState(Sso0055ParamVO param); // �����Ƿ�
	public List<Sso0055VO> searchZcobt304(Sso0055ParamVO param);
	public List<Sso0055VO> searchZcobt304D(Sso0055ParamVO param);
	public List<Sso0055VO> selectListBlockName(Sso0055ParamVO param);
	public List<Sso0055VO> selectListExchange(Sso0055ParamVO param); // �Ÿű���ȯ�� ��ȸ 2013.02.20
	public List<Sso0055VO> searchZcobt204D(Sso0055ParamVO param);
	public List<Sso0055VO> selectListBlockNameD(Sso0055ParamVO param);
} 
