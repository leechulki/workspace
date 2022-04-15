package hdel.sd.sbp.dao;

import hdel.sd.sbp.domain.Sbp0110ParamVO;
import hdel.sd.sbp.domain.Sbp0110VO;

import java.util.List;

/**
 * �����ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sbp0110D {

	public List<Sbp0110VO> SelectWaerk(Sbp0110ParamVO param);					// ��ȭ���� ��ȸ
	public List<Sbp0110VO> SelectOrderZpym(Sbp0110ParamVO param);				// ���� ��ȹ��� ��ȸ
	public List<Sbp0110VO> SelectSalesZpym(Sbp0110ParamVO param);				// ���� ��ȹ��� ��ȸ
	public List<Sbp0110VO> SelectChargeZpym(Sbp0110ParamVO param);				// û�� ��ȹ��� ��ȸ
	public List<Sbp0110VO> SelectCollectionZpym(Sbp0110ParamVO param);			// ���� ��ȹ��� ��ȸ
	public List<Sbp0110VO> SelectOrder(Sbp0110ParamVO param);					// ��ȭ�� ����
	public List<Sbp0110VO> SelectOrderExitWaerk(Sbp0110ParamVO param);			// ���� ����
	public List<Sbp0110VO> SelectSales(Sbp0110ParamVO param);					// ��ȭ�� ����
	public List<Sbp0110VO> SelectSalesExitWaerk(Sbp0110ParamVO param);			// ���� ����
	public List<Sbp0110VO> SelectCharge(Sbp0110ParamVO param);					// ��ȭ�� û��
	public List<Sbp0110VO> SelectChargeExitWaerk(Sbp0110ParamVO param);			// ���� û��
	public List<Sbp0110VO> SelectCollection(Sbp0110ParamVO param);				// ��ȭ�� ����
	public List<Sbp0110VO> SelectCollectionExitWaerk(Sbp0110ParamVO param);		// ���� ����
}
 