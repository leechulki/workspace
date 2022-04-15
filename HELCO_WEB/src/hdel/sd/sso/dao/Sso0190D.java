package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0190ParamVO;
import hdel.sd.sso.domain.Sso0190VO;

import java.util.List;

/**
 * ���ְ��� - �̵���ȹ ��Ȳ(��������)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sso0190D {

	public List<Sso0190VO> SelectWaerk(Sso0190ParamVO param);					// ��ȭ���� ��ȸ
	public List<Sso0190VO> SelectOrder(Sso0190ParamVO param);					// ��ȭ�� ����
	public List<Sso0190VO> SelectOrderExitWaerk(Sso0190ParamVO param);			// ���� ����
	public List<Sso0190VO> SelectSales(Sso0190ParamVO param);					// ��ȭ�� ����
	public List<Sso0190VO> SelectSalesExitWaerk(Sso0190ParamVO param);			// ���� ����
	public List<Sso0190VO> SelectCharge(Sso0190ParamVO param);					// ��ȭ�� û��
	public List<Sso0190VO> SelectChargeExitWaerk(Sso0190ParamVO param);			// ���� û��
	public List<Sso0190VO> SelectCollection(Sso0190ParamVO param);				// ��ȭ�� ����
	public List<Sso0190VO> SelectCollectionExitWaerk(Sso0190ParamVO param);		// ���� ����
}
 