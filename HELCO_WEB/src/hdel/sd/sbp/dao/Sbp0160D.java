package hdel.sd.sbp.dao;
 
import hdel.sd.sbp.domain.ComboParamVO;
import hdel.sd.sbp.domain.ComboVO;
import hdel.sd.sbp.domain.Sbp00101;
import hdel.sd.sbp.domain.Sbp00102;
import hdel.sd.sbp.domain.Sbp0160;
import hdel.sd.sbp.domain.Sbp0160ParamVO;

import java.util.List;

public interface Sbp0160D {

	// ���ְ��ɼ�
	public List<ComboVO> selectDD07T(ComboParamVO param);

	// ����
	public List<ComboVO> selectGtype();

	// ��ġ����
	public List<ComboVO> selectREGION(ComboParamVO param);
	
	public List<Sbp0160> selectZSDT1001(Sbp0160ParamVO param);
	
	public List<Sbp0160> selectZSDT1001E(Sbp0160ParamVO param);

	// �����ȹ���̺� ��������
	public void updateCostZSDT1001(Sbp0160 param);

	// ��������� ���ø� ���� ��ȸ (���ø���ȣ�� ��ȸ)
	public List<Sbp0160> selectListTempletInfo(Sbp0160ParamVO param);

}
