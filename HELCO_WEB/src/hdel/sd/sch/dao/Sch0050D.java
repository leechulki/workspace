package hdel.sd.sch.dao;

import hdel.sd.sch.domain.Sch0050ParamVO;
import hdel.sd.sch.domain.Sch0050VO;

import java.util.List;

/**
 * On-Hand û����ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sch0050D {

	public List<Sch0050VO> SelectWaerk(Sch0050ParamVO param);				// ��ȭ���� ��ȸ
	public List<Sch0050VO> SelectCharge(Sch0050ParamVO param);				// ��ȭ�� û��
	public List<Sch0050VO> SelectChargeExitWaerk(Sch0050ParamVO param);		// ���� û��
}
 