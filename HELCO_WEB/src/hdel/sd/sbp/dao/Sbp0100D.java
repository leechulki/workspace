package hdel.sd.sbp.dao;

import hdel.sd.sbp.domain.Sbp0100ParamVO;
import hdel.sd.sbp.domain.Sbp0100VO;

import java.util.List;

/**
 * �����ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sbp0100D { 

	public List<Sbp0100VO> SelectOrder(Sbp0100ParamVO param);		// ����
	public List<Sbp0100VO> SelectSales(Sbp0100ParamVO param);		// ����
	public List<Sbp0100VO> SelectCharge(Sbp0100ParamVO param);		// û��
	public List<Sbp0100VO> SelectCollection(Sbp0100ParamVO param);	// ����
}
