package hdel.sd.sbp.dao;

import hdel.lib.domain.MipParameterVO;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO;
import hdel.sd.sbp.domain.Sbp0040;
import hdel.sd.sbp.domain.Sbp0040ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;


/**
 * �����ȹ����(����)(Sbp0040D) DAO
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


public interface Sbp0040D {  
	
	// �����ȹ �����Ͻ�(14�ڸ�) ��ȸ
	public List<Sbp0040> selectOpenDtm(Sbp0040ParamVO param);  
		
	// �����ȹ��� ��ȸ
	public List<Sbp0040> selectList(Sbp0040ParamVO param);
	
	// ����������Ʈ��ȣ�� ���ֿ����SUM
	public Double selectSumSofoca(Sbp0040ParamVO param);
	
	// �����ȹ��ȣ�� �� �ǿ� ��ϵ� �����ȹ ����/û��/���ݸ�� ��ȸ
	public List<Sbp0040> selectListDetail(Sbp0040ParamVO param);		

	// �����ȹ ���ֿ����, ��ȭ ��ȸ : ����/û��/���� �ڵ����� ��� ��������
	public List<Sbp0040> selectListComputeItem(Sbp0040ParamVO param);
	
	// �����ȹ ����  
	public void save(MipParameterVO paramVO, Model mode, String newZbpnnr);
	
	// �����ȹ ���
	public void insertZSDT1023(Sbp0040 param);
	
	// �����ȹ ����
	public void updateZSDT1023(Sbp0040 param);

	// �����ȹ ������� ����
	public void updateSaveStat(Sbp0040 param);
	
	// �����ȹ ����
	public void deleteZSDT1023(Sbp0040 param);
	
	// �����ȹ-���� ��� (�����ȹ��ȣ�� ��������)
	public void insertZSDT1024(Sbp0040 param); 
	
	// �����ȹ-���� ���� (�����ȹ��ȣ��)
	public void deleteZSDT1024(Sbp0040 param);
	
	// �����ȹ-û�� ��� (�����ȹ��ȣ�� ��������)
	public void insertZSDT1025(Sbp0040 param); 
	
	// �����ȹ-û�� ���� (�����ȹ��ȣ��)
	public void deleteZSDT1025(Sbp0040 param);
	
	// �����ȹ-���� ��� (�����ȹ��ȣ�� ��������)
	public void insertZSDT1026(Sbp0040 param); 
	
	// �����ȹ-���� ���� (�����ȹ��ȣ��)
	public void deleteZSDT1026(Sbp0040 param);  
	
	// �����ȹ-���� ��� (�����ȹ��ȣ�� ��������)
	public void insertZSDT1027(Sbp0040 param); 
	
	// �����ȹ-���� ���� (�����ȹ��ȣ��)
	public void deleteZSDT1027(Sbp0040 param); 

	// SAP�� �����ȹ ���
	public void insertZSDT0014S(Sbp0040 param);

	// SAP�� �����ȹ ����
	public void updateZSDT0014S(Sbp0040 param);
	
	// SAP�� �����ȹ ����
	public void deleteZSDT0014S(Sbp0040 param);

	// ���¾�ü���� ��ȸ
	public List<Sbp0040> selectListLifnr(Sbp0040ParamVO param);
}
