package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0140ParamVO;
import hdel.sd.sso.domain.Sso0140VO;

import java.util.List;

/**
 * ���ְ�ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0140D { 

	public List<Sso0140VO> SelectOrder(Sso0140ParamVO param);		// ����
	public List<Sso0140VO> SelectSales(Sso0140ParamVO param);		// ����
	public List<Sso0140VO> SelectCharge(Sso0140ParamVO param);		// û��
	public List<Sso0140VO> SelectCollection(Sso0140ParamVO param);	// ����
}
