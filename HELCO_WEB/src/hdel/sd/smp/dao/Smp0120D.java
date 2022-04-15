package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0110ParamVO;
import hdel.sd.smp.domain.Smp0110VO;
import hdel.sd.smp.domain.Smp0120ParamVO;
import hdel.sd.smp.domain.Smp0120VO;

import java.util.List;

/**
 * �̵���ȹ ��Ȳ(��������)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Smp0120D {

	public List<Smp0120VO> SelectWaerk(Smp0120ParamVO param);					// ��ȭ���� ��ȸ
	public List<Smp0120VO> SelectOrder(Smp0120ParamVO param);					// ��ȭ�� ����
	public List<Smp0120VO> SelectOrderExitWaerk(Smp0120ParamVO param);			// ���� ����
	public List<Smp0120VO> SelectSales(Smp0120ParamVO param);					// ��ȭ�� ����
	public List<Smp0120VO> SelectSalesExitWaerk(Smp0120ParamVO param);			// ���� ����
	public List<Smp0120VO> SelectCharge(Smp0120ParamVO param);					// ��ȭ�� û��
	public List<Smp0120VO> SelectChargeExitWaerk(Smp0120ParamVO param);			// ���� û��
	public List<Smp0120VO> SelectCollection(Smp0120ParamVO param);				// ��ȭ�� ����
	public List<Smp0120VO> SelectCollectionExitWaerk(Smp0120ParamVO param);		// ���� ����
}
 