package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0120ParamVO;
import hdel.sd.sso.domain.Sso0120VO;

import java.util.List;

/**
 * ���ְ�ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0120D { 

	public List<Sso0120VO> SelectOrder(Sso0120ParamVO param);		// ����
	public List<Sso0120VO> SelectSales(Sso0120ParamVO param);		// ����
	public List<Sso0120VO> SelectCharge(Sso0120ParamVO param);		// û��
	public List<Sso0120VO> SelectCollection(Sso0120ParamVO param);	// ����
}
