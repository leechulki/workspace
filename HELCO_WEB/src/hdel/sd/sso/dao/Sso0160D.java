package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0160ParamVO;
import hdel.sd.sso.domain.Sso0160VO;

import java.util.List;

/**
 * ���ְ��� - �̵���ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0160D { 

	public List<Sso0160VO> SelectOrder(Sso0160ParamVO param);		// ����
	public List<Sso0160VO> SelectSales(Sso0160ParamVO param);		// ����
	public List<Sso0160VO> SelectCharge(Sso0160ParamVO param);		// û��
	public List<Sso0160VO> SelectCollection(Sso0160ParamVO param);	// ����
}
