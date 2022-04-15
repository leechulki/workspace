package hdel.sd.sbp.dao;

import hdel.lib.domain.MipParameterVO;
import hdel.sd.sbp.domain.Sbp0050;
import hdel.sd.sbp.domain.Sbp0050ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;


/**
 * �����ȹ����ä�ǰ���(���⺸��)(Sbp0050D) DAO
 * @Comment 
 *		- List selectOpenDtm  	( �����ȹ(����) �����Ͻ�(14�ڸ�) ��ȸ )
 *		- List selectList 		( �����ȹ(����) ��� ��ȸ )
 *		- List selectListDetail	( �����ȹ��ȣ �� �ǿ� ��ϵ� �����ȹ(����) ����/û��/���ݸ�� ��ȸ ) 
 *		- void insertZSDT1023  	( �����ȹ(����)(ZSDT1023) ��� )
 *		- void updateZSDT1023 	( �����ȹ(����)(ZSDT1023) ���� )
 *		- void deleteZSDT1023  	( �����ȹ(����)(ZSDT1023) ���� )
 *		- void insertZSDT1024  	( �����ȹ(����)-����(ZSDT1024) ��� (�����ȹ��ȣ�� ��������) )
 *		- void deleteZSDT1024  	( �����ȹ(����)-����(ZSDT1024) ���� (�����ȹ��ȣ��) )
 *		- void insertZSDT1025  	( �����ȹ(����)-û��(ZSDT1025) ��� (�����ȹ��ȣ�� û�������) )
 *		- void deleteZSDT1025  	( �����ȹ(����)-û��(ZSDT1025) ���� (�����ȹ��ȣ��)  )
 *		- void insertZSDT1026  	( �����ȹ(����)-����(ZSDT1026) ��� (�����ȹ��ȣ�� ���ݳ����) )
 *		- void deleteZSDT1026  	( �����ȹ(����)-����(ZSDT1026) ���� (�����ȹ��ȣ��)  ) 
 *
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */


public interface Sbp0050D {  
	
	// �����ȹ �����Ͻ�(14�ڸ�) ��ȸ
	public List<Sbp0050> selectOpenDtm(Sbp0050ParamVO param);  
		
	// �����ȹ��� ��ȸ
	public List<Sbp0050> selectList(Sbp0050ParamVO param);
	
	// �����ȹ��ȣ�� �� �ǿ� ��ϵ� �����ȹ ����/û��/���ݸ�� ��ȸ
	public List<Sbp0050> selectListDetail(Sbp0050ParamVO param);		
	
	// �����ȹ ����  
	public void save(MipParameterVO paramVO, Model mode, String newZbpnnr);
	
	// �����ȹ ���
	public void insertZSDT1023(Sbp0050 param);
	
	// �����ȹ ����
	public void updateZSDT1023(Sbp0050 param);
	
	// �����ȹ ����
	public void deleteZSDT1023(Sbp0050 param);
	
	// �����ȹ-���� ��� (�����ȹ��ȣ�� ��������)
	public void insertZSDT1024(Sbp0050 param); 
	
	// �����ȹ-���� ���� (�����ȹ��ȣ��)
	public void deleteZSDT1024(Sbp0050 param);
	
	// �����ȹ-û�� ��� (�����ȹ��ȣ�� ��������)
	public void insertZSDT1025(Sbp0050 param); 
	
	// �����ȹ-û�� ���� (�����ȹ��ȣ��)
	public void deleteZSDT1025(Sbp0050 param);
	
	// �����ȹ-���� ��� (�����ȹ��ȣ�� ��������)
	public void insertZSDT1026(Sbp0050 param); 
	
	// �����ȹ-���� ���� (�����ȹ��ȣ��)
	public void deleteZSDT1026(Sbp0050 param); 
	
}
