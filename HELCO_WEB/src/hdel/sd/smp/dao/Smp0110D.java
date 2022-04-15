package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0110ParamVO;
import hdel.sd.smp.domain.Smp0110VO;

import java.util.List;

/**
 * �̵���ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Smp0110D {

	public List<Smp0110VO> SelectWaerk(Smp0110ParamVO param);					// ��ȭ���� ��ȸ
	public List<Smp0110VO> SelectOrder(Smp0110ParamVO param);					// ��ȭ�� ����
	public List<Smp0110VO> SelectOrderExitWaerk(Smp0110ParamVO param);			// ���� ����
	public List<Smp0110VO> SelectSales(Smp0110ParamVO param);					// ��ȭ�� ����
	public List<Smp0110VO> SelectSalesExitWaerk(Smp0110ParamVO param);			// ���� ����
	public List<Smp0110VO> SelectCharge(Smp0110ParamVO param);					// ��ȭ�� û��
	public List<Smp0110VO> SelectChargeExitWaerk(Smp0110ParamVO param);			// ���� û��
	public List<Smp0110VO> SelectCollection(Smp0110ParamVO param);				// ��ȭ�� ����
	public List<Smp0110VO> SelectCollectionExitWaerk(Smp0110ParamVO param);		// ���� ����
}
 