package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0100ParamVO;
import hdel.sd.smp.domain.Smp0100VO;

import java.util.List;

/**
 * �̵���ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Smp0100D { 

	public List<Smp0100VO> SelectOrder(Smp0100ParamVO param);		// ����
	public List<Smp0100VO> SelectSales(Smp0100ParamVO param);		// ����
	public List<Smp0100VO> SelectCharge(Smp0100ParamVO param);		// û��
	public List<Smp0100VO> SelectCollection(Smp0100ParamVO param);	// ����
}
