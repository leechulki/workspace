package hdel.sd.scl.dao;

import hdel.sd.scl.domain.Scl0070ParamVO;
import hdel.sd.scl.domain.Scl0070VO;

import java.util.List;

/**
 * On-Hand ���ݰ�ȹ ��Ȳ
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Scl0070D { 

	public List<Scl0070VO> SelectCollection(Scl0070ParamVO param);		// ����
}
