package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.sbi.domain.SanyangPrd;

/**
 * �귣�� ��ȸ Dao Ŭ����
 * 
 * @author  �ڼ���
 * @since 2020.08.20
 * @version 1.0
 * @see 
 * <pre>
 *  == �����̷�(Modification Information) ==
 *   
 *          ������          ������           ��������
 *  ----------------    ------------    ---------------------------
 *   2020.08.20         �ڼ���          ���� ����
 * 
 * </pre>
 */

public interface Sbi0100D {

	/** 
	 * �귣�� �����ٿ�ε� ����Ư���� ��ȸ
	 * @param Map<String, Object> parmMap
	 * @return Com040SanyangPrh
	 * @throws Exception
	 */
	public List<SanyangPrd> selectSayangList(Map<String, Object> parmMap) throws Exception;

}
