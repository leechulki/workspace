package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0050ParamVO;
import hdel.sd.sso.domain.Sso0050VO;

import java.util.List;

/**
 * ���ֺ���
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0050D { 

	public List<Sso0050VO> SelectSayang(Sso0050ParamVO param);		// ����
	public List<Sso0050VO> SelectVbeln(Sso0050ParamVO param);		// ����
	public List<Sso0050VO> SelectSayangForPrint(Sso0050ParamVO param);
	public void setCostState(Sso0050ParamVO param); // �����Ƿ�
	public List<Sso0050VO> searchZcobt304(Sso0050ParamVO param);
	public List<Sso0050VO> searchZcobt304D(Sso0050ParamVO param);
	public List<Sso0050VO> selectListBlockName(Sso0050ParamVO param);
	
	/*	2018.03.06 - ȯ����ȸ ���� ���ȭ (com.ExchangeS.class , selectSoExchange)
	public List<Sso0050VO> selectListExchange(Sso0050ParamVO param); // �Ÿű���ȯ�� ��ȸ 2013.02.20
	*/
	
	public List<Sso0050VO> searchZcobt204D(Sso0050ParamVO param);
	public List<Sso0050VO> selectListBlockNameD(Sso0050ParamVO param);
} 
