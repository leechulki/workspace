package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0150ParamVO;
import hdel.sd.sso.domain.Sso0150VO;

import java.util.List;

/**
 * ���ְ�ȹ ��Ȳ(��������)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0150D {

	public List<Sso0150VO> SelectWaerk(Sso0150ParamVO param);					// ��ȭ���� ��ȸ
	public List<Sso0150VO> SelectOrder(Sso0150ParamVO param);					// ��ȭ�� ����
	public List<Sso0150VO> SelectOrderExitWaerk(Sso0150ParamVO param);			// ���� ����
	public List<Sso0150VO> SelectSales(Sso0150ParamVO param);					// ��ȭ�� ����
	public List<Sso0150VO> SelectSalesExitWaerk(Sso0150ParamVO param);			// ���� ����
	public List<Sso0150VO> SelectCharge(Sso0150ParamVO param);					// ��ȭ�� û��
	public List<Sso0150VO> SelectChargeExitWaerk(Sso0150ParamVO param);			// ���� û��
	public List<Sso0150VO> SelectCollection(Sso0150ParamVO param);				// ��ȭ�� ����
	public List<Sso0150VO> SelectCollectionExitWaerk(Sso0150ParamVO param);		// ���� ����
}
 