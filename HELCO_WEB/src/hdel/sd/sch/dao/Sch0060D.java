package hdel.sd.sch.dao;

import hdel.sd.sch.domain.ComboParamVO;
import hdel.sd.sch.domain.ComboVO;
import hdel.sd.sch.domain.Sch0060;
import hdel.sd.sch.domain.Sch0060ParamVO;

import java.util.List;

public interface Sch0060D {

	// ���1, ���2, ���3, ���4
	public List<ComboVO> selectZSPEC(ComboParamVO param);

	// ���س��
	public List<ComboVO> selectCDATE(ComboParamVO param);

	// ����2, ����3
	public List<ComboVO> selectGUBUN(ComboParamVO param);

	// �ſ���
	public List<ComboVO> selectJUDGE(ComboParamVO param);

	// ��ġ1
	public List<ComboVO> selectZZACTSS(ComboParamVO param);

	// ��ġ2
	public List<ComboVO> selectTEMNO(ComboParamVO param);

	// ����1, ��������, ������������
	public List<ComboVO> selectGUBUNC(ComboParamVO param);

	// �ǹ��뵵
	public List<ComboVO> selectWWBLD(ComboParamVO param);

	// �������¾�ü, ��ġ���¾�ü
	public List<ComboVO> selectKUNZ1(ComboParamVO param);

	// ȣ��
	public List<Sch0060> selectHOKI(Sch0060ParamVO param);

	// ������Ʈ
	public List<Sch0060> selectPROJECT(Sch0060ParamVO param);

	// û��
	public List<Sch0060> selectCHEONG(Sch0060ParamVO param);

	// ����
	public List<Sch0060> selectSUKEUM(Sch0060ParamVO param);

	public void mergeZSDT1010(Sch0060 param);

	public void mergeZSDT1011(Sch0060 param);

}
