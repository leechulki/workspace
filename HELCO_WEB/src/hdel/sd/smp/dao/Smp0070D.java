package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0070ParamVO;
import hdel.sd.smp.domain.Smp0070VO;

import java.util.List;

/**
 * �̵���ȹ ����/����
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Smp0070D {

	public List<Smp0070VO> Select(Smp0070ParamVO param);				// ��ȸ
	public int InsertCopyOrder(Smp0070ParamVO param);					// copy - ����
	public int InsertCopyOrderRepair(Smp0070ParamVO param);				// copy - ����(����)
	public int InsertCopySales(Smp0070ParamVO param);					// copy - ����
	public int InsertCopySalesRepair(Smp0070ParamVO param);				// copy - ����(����)
	public int InsertCopyCharge(Smp0070ParamVO param);					// copy - û��
	public int InsertCopyChargeRepair(Smp0070ParamVO param);			// copy - û��(����)
	public int InsertCopyCollection(Smp0070ParamVO param);				// copy - ����
	public int InsertCopyCollectionRepair(Smp0070ParamVO param);		// copy - ����(����)
	public int DeleteCancelOrder(Smp0070ParamVO param);					// cancel - ����
	public int DeleteCancelOrderRepair(Smp0070ParamVO param);			// cancel - ����(����)
	public int DeleteCancelSales(Smp0070ParamVO param);					// cancel - ����
	public int DeleteCancelSalesRepair(Smp0070ParamVO param);			// cancel - ����(����)
	public int DeleteCancelCharge(Smp0070ParamVO param);				// cancel - û��
	public int DeleteCancelChargeRepair(Smp0070ParamVO param);			// cancel - û��(����)
	public int DeleteCancelCollection(Smp0070ParamVO param);			// cancel - ����
	public int DeleteCancelCollectionRepair(Smp0070ParamVO param);		// cancel - ����(����)
	public int MergeCopyNext(Smp0070ParamVO param);						// ���� �� ����
	public int MergeCancelNextUpd(Smp0070ParamVO param);				// ��� �� ���� - ����
	public int MergeCancelNextDel(Smp0070ParamVO param);				// ��� �� ���� - ����
}
