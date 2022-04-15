package hdel.sd.sbp.dao;

import hdel.lib.domain.MipParameterVO;
import hdel.sd.sbp.domain.Sbp0070;
import hdel.sd.sbp.domain.Sbp0070ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;
 

/**
 * �����ȹ ����/����(Sbp0070D) DAO
 * @Comment  
 *		- List selectList 		( �����ȹ����  ��� ��ȸ  )
 *		- List selectListLast 	( ���� �����ȹ���� ���� ��ȸ  ) 
 *  	- void insertZSDT1017	( �����ȹ���� ��� ) 
 *  	- void deleteZSDT1017	( �����ȹ���� ���� ) 
 *  	- void openZSDT1017		( �����ȹ���� ����/������� ) 
 *  	- void closeZSDT1017	( �����ȹ���� ���� /�������) 
 *  	- void intoZSDT1018		( ����  �� �����ȹ����-���� ��� ) 
 *  	- void intoZSDT1019		( ����  �� �����ȹ����-���� ��� ) 
 *  	- void intoZSDT1020		( ����  �� �����ȹ����-û�� ��� ) 
 *  	- void intoZSDT1021		( ����  �� �����ȹ����-���� ��� ) 
 *  	- void intoZSDT1022		( ����  �� �����ȹ����-���� ��� ) 
 *  	- void intoZSDT1028		( ����  �� �����ȹ����(����)-���� ��� ) 
 *  	- void intoZSDT1029		( ����  �� �����ȹ����(����)-���� ��� ) 
 *  	- void intoZSDT1030		( ����  �� �����ȹ����(����)-û�� ��� ) 
 *  	- void intoZSDT1031		( ����  �� �����ȹ����(����)-���� ��� ) 
 *  	- void intoZSDT1032		( ����  �� �����ȹ����(����)-���� ��� ) 
 *  	- void deleteZSDT1018	( �������  �� �����ȹ����-���� ����ڷ� ���� ) 
 *  	- void deleteZSDT1019	( �������  �� �����ȹ����-���� ����ڷ� ���� ) 
 *  	- void deleteZSDT1020	( �������  �� �����ȹ����-û�� ����ڷ� ���� ) 
 *  	- void deleteZSDT1021	( �������  �� �����ȹ����-���� ����ڷ� ���� ) 
 *  	- void deleteZSDT1022	( �������  �� �����ȹ����-���� ����ڷ� ���� ) 
 *  	- void deleteZSDT1028	( �������  �� �����ȹ����(����)-���� ����ڷ� ���� ) 
 *  	- void deleteZSDT1029	( �������  �� �����ȹ����(����)-���� ����ڷ� ���� ) 
 *  	- void deleteZSDT1030	( �������  �� �����ȹ����(����)-û�� ����ڷ� ���� ) 
 *  	- void deleteZSDT1031	( �������  �� �����ȹ����(����)-���� ����ڷ� ���� ) 
 *  	- void deleteZSDT1032	( �������  �� �����ȹ����(����)-���� ����ڷ� ���� );
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */ 

public interface Sbp0070D {  
	 
	// �����ȹ������� ��ȸ
	public List<Sbp0070> selectList(Sbp0070ParamVO param); 
	
	// ���������ȹ�������� ��ȸ
	public List<Sbp0070> selectListLast(Sbp0070ParamVO param); 
	 
	// �����ȹ���� ���
	public void insertZSDT1017(Sbp0070ParamVO param);
	 
	// �����ȹ���� ����
	public void deleteZSDT1017(Sbp0070 param);
	
	// �����ȹ���� ����
	public void openZSDT1017(Sbp0070 param);

	// �����ȹ���� ����
	public void closeZSDT1017(Sbp0070 param);
	
	// ����  �� �����ȹ����-���� ���
	public void intoZSDT1018(Sbp0070 param);
	
	// ����  �� �����ȹ����-���� ���
	public void intoZSDT1019(Sbp0070 param);
	
	// ����  �� �����ȹ����-û�� ���
	public void intoZSDT1020(Sbp0070 param);
	
	// ����  �� �����ȹ����-���� ���
	public void intoZSDT1021(Sbp0070 param);
	
	// ����  �� �����ȹ����-���� ���
	public void intoZSDT1022(Sbp0070 param);
	
	// ����  �� �����ȹ����(����)-���� ���
	public void intoZSDT1028(Sbp0070 param);
	
	// ����  �� �����ȹ����(����)-���� ���
	public void intoZSDT1029(Sbp0070 param);
	
	// ����  �� �����ȹ����(����)-û�� ���
	public void intoZSDT1030(Sbp0070 param);
	
	// ����  �� �����ȹ����(����)-���� ���
	public void intoZSDT1031(Sbp0070 param);
	
	// ����  �� �����ȹ����(����)-���� ���
	public void intoZSDT1032(Sbp0070 param);
	
	// �������  �� �����ȹ����-���� ����ڷ� ����
	public void deleteZSDT1018(Sbp0070 param);
		
	// �������  �� �����ȹ����-���� ����ڷ� ����
	public void deleteZSDT1019(Sbp0070 param);
	
	// �������  �� �����ȹ����-û�� ����ڷ� ����
	public void deleteZSDT1020(Sbp0070 param);
	
	// �������  �� �����ȹ����-���� ����ڷ� ����
	public void deleteZSDT1021(Sbp0070 param);
	
	// �������  �� �����ȹ����-���� ����ڷ� ����
	public void deleteZSDT1022(Sbp0070 param);
	
	// �������  �� �����ȹ����(����)-���� ����ڷ� ����
	public void deleteZSDT1028(Sbp0070 param);
	
	// �������  �� �����ȹ����(����)-���� ����ڷ� ����
	public void deleteZSDT1029(Sbp0070 param);
	
	// �������  �� �����ȹ����(����)-û�� ����ڷ� ����
	public void deleteZSDT1030(Sbp0070 param);
	
	// �������  �� �����ȹ����(����)-���� ����ڷ� ����
	public void deleteZSDT1031(Sbp0070 param);
	
	// �������  �� �����ȹ����(����)-���� ����ڷ� ����
	public void deleteZSDT1032(Sbp0070 param);
	
}
