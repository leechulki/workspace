package hdel.sd.sbp.dao;
 
import hdel.sd.com.domain.Com0060ParamVO;
import hdel.sd.sbp.domain.Sbp0160ParamVO;
import hdel.sd.sbp.domain.Sbp0160VO_N;
import hdel.sd.sbp.domain.Sbp0161ParamVO;
import hdel.sd.sbp.domain.Sbp0161VO_N;

import java.util.List;

public interface Sbp0160D_N {

	// ��ȸ
	public List<Sbp0160VO_N> selectZSDT1001_N(Sbp0160ParamVO param);

	// �ؿܴ븮�� ��ȸ
	public List<Sbp0160VO_N> selectZSDT1001E_N(Sbp0160ParamVO param);
	
	// �Ǳ���
	public List<Com0060ParamVO> selectRTYPE(Com0060ParamVO param);

	// ��������� ���ø� ���� ��ȸ (���ø���ȣ�� ��ȸ)
	public List<Sbp0160VO_N> selectListTempletInfo(Sbp0160ParamVO param);

	// ���ְ�ȹ��ȣ�� ����û������ ��ȸ(�����ϴ� ������)
	public List<Sbp0161VO_N> selectListDetail_N(Sbp0161ParamVO param);

	// ���ְ�ȹ��ȣ�� ����û������ ��ȸ(�����ϴ� �ʴ� ������)
	public List<Sbp0161VO_N> selectListDetail_Null(Sbp0161ParamVO param);

	// �̵���ȹ 1�� ��������
	public List<Sbp0160VO_N> selectZclflg(Sbp0160ParamVO param);
	
}
