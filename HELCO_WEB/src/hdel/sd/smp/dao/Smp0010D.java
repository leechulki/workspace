package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0010ParamVO;
import hdel.sd.smp.domain.SmpComParameterVO;

import java.util.List;

/**
 * �̵���ȹ
 * �̵���ȹ ��
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
public interface Smp0010D {

	public List<Smp0010ParamVO> find(Smp0010ParamVO param);// ��ü ��ȸ
	public void update(Smp0010ParamVO param);// ����
	public void insert(Smp0010ParamVO param);// �߰�
	public void delete(Smp0010ParamVO param);// ����
	public List<SmpComParameterVO> teamCd(SmpComParameterVO param);// �ڵ���ȸ
}
