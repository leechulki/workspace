package hdel.sd.ssa.dao;

import hdel.sd.ssa.domain.Ssa0060ParamVO;
import hdel.sd.ssa.domain.Ssa0060VO;

import java.util.List;

/**
 * On-Hand �����ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Ssa0060D {

	public List<Ssa0060VO> SelectWaerk(Ssa0060ParamVO param);				// ��ȭ���� ��ȸ
	public List<Ssa0060VO> SelectSales(Ssa0060ParamVO param);				// ��ȭ�� ����
	public List<Ssa0060VO> SelectSalesExitWaerk(Ssa0060ParamVO param);		// ���� ����
}
