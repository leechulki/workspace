package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0130ParamVO;
import hdel.sd.sso.domain.Sso0130VO;

import java.util.List;

/**
 * ���ְ�ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0130D {

	public List<Sso0130VO> SelectWaerk(Sso0130ParamVO param);					// ��ȭ���� ��ȸ
	public List<Sso0130VO> SelectOrder(Sso0130ParamVO param);					// ��ȭ�� ����
	public List<Sso0130VO> SelectOrderExitWaerk(Sso0130ParamVO param);			// ���� ����
	public List<Sso0130VO> SelectSales(Sso0130ParamVO param);					// ��ȭ�� ����
	public List<Sso0130VO> SelectSalesExitWaerk(Sso0130ParamVO param);			// ���� ����
	public List<Sso0130VO> SelectCharge(Sso0130ParamVO param);					// ��ȭ�� û��
	public List<Sso0130VO> SelectChargeExitWaerk(Sso0130ParamVO param);			// ���� û��
	public List<Sso0130VO> SelectCollection(Sso0130ParamVO param);				// ��ȭ�� ����
	public List<Sso0130VO> SelectCollectionExitWaerk(Sso0130ParamVO param);		// ���� ����
}
 