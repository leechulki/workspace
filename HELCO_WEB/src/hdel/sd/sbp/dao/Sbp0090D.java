package hdel.sd.sbp.dao;

import hdel.sd.sbp.domain.Sbp0090ParamVO;
import hdel.sd.sbp.domain.Sbp0090VO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * �����ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sbp0090D { 

	public List<Sbp0090VO> SelectOrder(Sbp0090ParamVO param);		// ����
	public List<Sbp0090VO> SelectSales(Sbp0090ParamVO param);		// ����
	public List<Sbp0090VO> SelectCharge(Sbp0090ParamVO param);		// û��
	public List<Sbp0090VO> SelectCollection(Sbp0090ParamVO param);	// ����
	public Float getExchangeBPUSD(@Param("mandt") String mandt, @Param("bsdat") String bsdat);
}
