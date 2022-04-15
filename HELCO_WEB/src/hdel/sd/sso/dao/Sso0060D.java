package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0060;
import hdel.sd.sso.domain.Sso0060ParamVO;
import hdel.sd.sso.domain.ZSDS0060;
import hdel.sd.sso.domain.ZSDS0060T;
import hdel.sd.sso.domain.ZSDS0061;
import hdel.sd.sso.domain.ZSDS0062;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


/**
 * ���ֻ���(Sso0060D) DAO
 * @Comment 
 *		- List selectListZclose  	( �������� ��ȸ ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


public interface Sso0060D {  
	
	// ������Ʈ��ȣ�� ������ȣ ��ȸ
	public List<Sso0060> selectListQtnum(Sso0060ParamVO param);
	
	// ������ȣ�� �������� �������� �� ������Ʈ��ȣ ��ȸ
	public List<Sso0060> selectListQtser(Sso0060ParamVO param);
	
	// ������ȣ�� ���������� ��ȸ
	public List<Sso0060> selectListQtnumInfo(Sso0060ParamVO param); 
	
	// ������ȣ�� ����ǰ������� ��ȸ
	public List<Sso0060> selectListQtnumItemInfo(Sso0060ParamVO param); 
	
	// �Ǹ�ó���� ��ȸ
	public List<Sso0060> selectListKunnrRg(Sso0060ParamVO param); 

	// �μ���,���� ��ȸ
	public List<Sso0060> selectListVbVg(Sso0060ParamVO paramV);
	
	// ��ǰó��� ��ȸ
	public List<Sso0060> selectListKunnrWe(Sso0060ParamVO param);
	
	// �븮������ ��ȸ
	public List<Sso0060> selectListKunnrZ1(Sso0060ParamVO param);
	
	// ��������� ��ȸ
	public List<Sso0060> selectListKunnrZ2(Sso0060ParamVO param);
	
	// ���������������� ��ȸ
	public List<Sso0060> selectListKunnrZ3(Sso0060ParamVO param);
	
	// ����������� ����
	public void updateZsdt1046Zprgflg(Sso0060 param);

	// ���ְ�ȹ���� ����
	public void updateZsdt1001Sorlt(Sso0060 param);
	
	//SO DATA �ӽ�����
	public void saveTemp(ZSDS0060 param);
	
	/* 2018.03.06 - source ���� (sso.dao.Sso0060ND.class)
	public Sso0060 selectExchangeRate(Sso0060ParamVO param);
	*/
}
