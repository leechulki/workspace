package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.sbi.domain.ZSDT1143;

/**
 * �귣�� �ұ׷� Dao Ŭ����
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

public interface Sbi0093D {

	/** 
	 * Ư���׷� ��ȸ
	 * @param ZPRDGBN(��ǰ����)
	 * @return List<ZSDT1143>
	 * @throws Exception
	 */
	public List<ZSDT1143> selectZsdt1143List(Map<String, Object> param) throws Exception;
	
	/** 
	 * �귣�� �ұ׷� Ÿ��Ʋ ����
	 * @param Zsdt1143
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1143(ZSDT1143 Zsdt1143) throws Exception;

	/** 
	 * �귣�� �ұ׷� Ÿ��Ʋ ���� ������ ����
	 * @param Zsdt1143
	 * @return int
	 * @throws Exception
	 */
	public int insertZeaitZsdt1143(ZSDT1143 Zsdt1143) throws Exception;
	
	/** 
	 * �귣�� �ұ׷� Ÿ��Ʋ ����
	 * @param Zsdt1143
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1143(ZSDT1143 Zsdt1143) throws Exception;

	/** 
	 * �귣�� �ұ׷� Ÿ��Ʋ ����
	 * @param Zsdt1143
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1143(ZSDT1143 Zsdt1143) throws Exception;

}
