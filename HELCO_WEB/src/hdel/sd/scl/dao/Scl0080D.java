package hdel.sd.scl.dao;

import hdel.sd.scl.domain.Scl0080ParamVO;
import hdel.sd.scl.domain.Scl0080VO;

import java.util.List;

/**
 * On-Hand ���ݰ�ȹ ��Ȳ(����)
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Scl0080D {

	public List<Scl0080VO> SelectWaerk(Scl0080ParamVO param);					// ��ȭ���� ��ȸ
	public List<Scl0080VO> SelectCollection(Scl0080ParamVO param);				// ��ȭ�� ����
	public List<Scl0080VO> SelectCollectionExitWaerk(Scl0080ParamVO param);		// ���� ����
}
 