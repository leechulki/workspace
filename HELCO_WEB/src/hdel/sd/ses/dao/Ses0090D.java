package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0090;
import hdel.sd.ses.domain.Ses0090ParamVO;

import java.util.List;

public interface Ses0090D {  
	
	public List<Ses0090> selectListHeader(Ses0090ParamVO param);


	// ��������/�ݷ�
	public void updateZSDT1057(Ses0090 param);

	public void updateFlagZSDT1046(Ses0090 param);

	public void updateFlagZSDT1001(Ses0090 param);

	// �ؿܴ븮�� ���� ó��
	public List<Ses0090> selectListAgentHeader(Ses0090ParamVO param);

	// Detail ���� ��ȸ ���� (����, �ؿܴ븮��)
	public List<Ses0090> selectListDetail(Ses0090ParamVO param);
	
	public List<Ses0090> selectListAgentDetail(Ses0090ParamVO param);

	// �ؿܴ븮�� ���� ��ȸ
	public List<Ses0090> selectKunnr(Ses0090ParamVO param);

	// SPEC ���� ��ȸ
	public List<Ses0090> searchSpecList(Ses0090ParamVO param);
}
