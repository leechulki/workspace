package hdel.sd.sch.dao;

import hdel.sd.sch.domain.Sch0040ParamVO;
import hdel.sd.sch.domain.Sch0040VO;

import java.util.List;

/**
 * On-Hand û����ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Sch0040D { 

	public List<Sch0040VO> SelectCharge(Sch0040ParamVO param);		// û��
}
