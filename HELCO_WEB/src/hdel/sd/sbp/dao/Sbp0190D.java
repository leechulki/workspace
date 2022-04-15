package hdel.sd.sbp.dao;
 
import hdel.sd.sbp.domain.ComboParamVO;
import hdel.sd.sbp.domain.ComboVO;
import hdel.sd.sbp.domain.Sbp0190;
import hdel.sd.sbp.domain.Sbp0190ParamVO;

import java.util.List;

public interface Sbp0190D {

	// ���ְ��ɼ�
	public List<ComboVO> selectDD07T(ComboParamVO param);

	// ����
	public List<ComboVO> selectGtype();

	// ��ġ����
	public List<ComboVO> selectREGION(ComboParamVO param);
	
	List<Sbp0190> selectZSDT1005(Sbp0190ParamVO param);
	
	void mergeZSDT1005(Sbp0190 param);
	
	void deleteZSDT1005(Sbp0190 param);
}
