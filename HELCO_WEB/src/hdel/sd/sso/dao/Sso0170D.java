package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0170ParamVO;
import hdel.sd.sso.domain.Sso0170VO;

import java.util.List;

/**
 * ���ְ��� - �̵���ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0170D {

	public List<Sso0170VO> SelectWaerk(Sso0170ParamVO param);					// ��ȭ���� ��ȸ
	public List<Sso0170VO> SelectOrder(Sso0170ParamVO param);					// ��ȭ�� ����
	public List<Sso0170VO> SelectOrderExitWaerk(Sso0170ParamVO param);			// ���� ����
	public List<Sso0170VO> SelectSales(Sso0170ParamVO param);					// ��ȭ�� ����
	public List<Sso0170VO> SelectSalesExitWaerk(Sso0170ParamVO param);			// ���� ����
	public List<Sso0170VO> SelectCharge(Sso0170ParamVO param);					// ��ȭ�� û��
	public List<Sso0170VO> SelectChargeExitWaerk(Sso0170ParamVO param);			// ���� û��
	public List<Sso0170VO> SelectCollection(Sso0170ParamVO param);				// ��ȭ�� ����
	public List<Sso0170VO> SelectCollectionExitWaerk(Sso0170ParamVO param);		// ���� ����
}
 