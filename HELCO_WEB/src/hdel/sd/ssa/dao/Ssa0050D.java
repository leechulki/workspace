package hdel.sd.ssa.dao;

import hdel.sd.ssa.domain.Ssa0050ParamVO;
import hdel.sd.ssa.domain.Ssa0050VO;

import java.util.List;

/**
 * On-Hand �����ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Ssa0050D { 

	public List<Ssa0050VO> SelectSales(Ssa0050ParamVO param);		// ����
}
