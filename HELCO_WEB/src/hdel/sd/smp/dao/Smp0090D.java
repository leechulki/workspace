package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0090ParamVO;
import hdel.sd.smp.domain.Smp0090VO;

import java.util.List;

/**
 * �̵���ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Smp0090D { 

	public List<Smp0090VO> SelectOrder(Smp0090ParamVO param);		// ����
	public List<Smp0090VO> SelectSales(Smp0090ParamVO param);		// ����
	public List<Smp0090VO> SelectCharge(Smp0090ParamVO param);		// û��
	public List<Smp0090VO> SelectCollection(Smp0090ParamVO param);	// ����
}
