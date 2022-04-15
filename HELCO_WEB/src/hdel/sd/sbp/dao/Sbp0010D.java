package hdel.sd.sbp.dao;

import hdel.lib.domain.MipParameterVO;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO; 
import hdel.sd.sbp.domain.Sbp00101;
import hdel.sd.sbp.domain.Sbp00102;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;


/**
 * �����ȹ����(Sbp0010D) DAO
 * @Comment 
 *		- List selectOpenDtm  	( �����ȹ �����Ͻ�(14�ڸ�) ��ȸ )
 *		- List selectList 		( �����ȹ ��� ��ȸ )
 *		- List selectListDetail	( �����ȹ��ȣ �� �ǿ� ��ϵ� �����ȹ ����/û��/���ݸ�� ��ȸ )  
 *		- void insertZSDT1012 	( �����ȹ(ZSDT1012) ��� )
 *		- void updateZSDT1012	( �����ȹ(ZSDT1012) ���� )
 *		- void deleteZSDT1012 	( �����ȹ(ZSDT1012) ���� )
 *		- void insertZSDT1013 	( �����ȹ-����(ZSDT1013) ��� (�����ȹ��ȣ�� ��������) )
 *		- void deleteZSDT1013 	( �����ȹ-����(ZSDT1013) ���� (�����ȹ��ȣ��) )
 *		- void insertZSDT1014 	( �����ȹ-û��(ZSDT1014) ��� (�����ȹ��ȣ�� û�������) )
 *		- void deleteZSDT1014 	( �����ȹ-û��(ZSDT1014) ���� (�����ȹ��ȣ��)  )
 *		- void insertZSDT1015 	( �����ȹ-����(ZSDT1015) ��� (�����ȹ��ȣ�� ���ݳ����) )
 *		- void deleteZSDT1015 	( �����ȹ-����(ZSDT1015) ���� (�����ȹ��ȣ��)  )
 *		- void insertZSDT1016 	( �����ȹ-����(ZSDT1016) ��� (�����ȹ��ȣ�� ���ݳ����) )
 *		- void deleteZSDT1016 	( �����ȹ-����(ZSDT1016) ���� (�����ȹ��ȣ��)  )
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */

public interface Sbp0010D {  
	
	// �����ȹ �����Ͻ�(14�ڸ�) ��ȸ
	public List<Sbp0010> selectOpenDtm(Sbp0010ParamVO param);

	// �����ȹ ���ֿ����, ��ȭ ��ȸ : ����/û��/���� �ڵ����� ��� ��������
	public List<Sbp0010> selectListComputeItem(Sbp0010ParamVO param);
		
	// �����ȹ��� ��ȸ
	public List<Sbp0010> selectList(Sbp0010ParamVO param);
	
	// �����ȹ��ȣ�� �� �ǿ� ��ϵ� �����ȹ ����/û��/���ݸ�� ��ȸ
	public List<Sbp0010> selectListDetail(Sbp0010ParamVO param);	
	
	// �����ȹ ���� �� ������� ����
	public void updateCostZSDT1012(Sbp0010 param);

	// �븮������ ��ȸ
	public List<Sbp0010> selectListZagnt(Sbp0010ParamVO paramV);
	
	// �Ǳ��� ����  ��ȸ
	public List<Sbp0010> selectListRGtype(Sbp0010ParamVO paramV);

}
