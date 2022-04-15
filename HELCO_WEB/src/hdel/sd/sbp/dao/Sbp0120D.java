package hdel.sd.sbp.dao;

import hdel.sd.sbp.domain.Sbp0120ParamVO;
import hdel.sd.sbp.domain.Sbp0120VO;

import java.util.List;

/**
 * �����ȹ ��Ȳ(��������)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sbp0120D {

	public List<Sbp0120VO> SelectWaerk(Sbp0120ParamVO param);					// ��ȭ���� ��ȸ
	public List<Sbp0120VO> SelectOrderZpym(Sbp0120ParamVO param);				// ���� ��ȹ��� ��ȸ
	public List<Sbp0120VO> SelectSalesZpym(Sbp0120ParamVO param);				// ���� ��ȹ��� ��ȸ
	public List<Sbp0120VO> SelectChargeZpym(Sbp0120ParamVO param);				// û�� ��ȹ��� ��ȸ
	public List<Sbp0120VO> SelectCollectionZpym(Sbp0120ParamVO param);			// ���� ��ȹ��� ��ȸ
	public List<Sbp0120VO> SelectOrder(Sbp0120ParamVO param);					// ��ȭ�� ����
	public List<Sbp0120VO> SelectOrderExitWaerk(Sbp0120ParamVO param);			// ���� ����
	public List<Sbp0120VO> SelectSales(Sbp0120ParamVO param);					// ��ȭ�� ����
	public List<Sbp0120VO> SelectSalesExitWaerk(Sbp0120ParamVO param);			// ���� ����
	public List<Sbp0120VO> SelectCharge(Sbp0120ParamVO param);					// ��ȭ�� û��
	public List<Sbp0120VO> SelectChargeExitWaerk(Sbp0120ParamVO param);			// ���� û��
	public List<Sbp0120VO> SelectCollection(Sbp0120ParamVO param);				// ��ȭ�� ����
	public List<Sbp0120VO> SelectCollectionExitWaerk(Sbp0120ParamVO param);		// ���� ����
}
 