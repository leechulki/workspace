package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0180ParamVO;
import hdel.sd.sso.domain.Sso0180VO;

import java.util.List;

/**
 * ���ְ��� - �̵���ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0180D { 

	public List<Sso0180VO> SelectOrder(Sso0180ParamVO param);		// ����
	public List<Sso0180VO> SelectSales(Sso0180ParamVO param);		// ����
	public List<Sso0180VO> SelectCharge(Sso0180ParamVO param);		// û��
	public List<Sso0180VO> SelectCollection(Sso0180ParamVO param);	// ����
}
