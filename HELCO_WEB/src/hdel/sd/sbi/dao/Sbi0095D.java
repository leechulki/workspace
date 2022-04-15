package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.sbi.domain.ZSDT1144;

/**
 * �귣��-���� ��� Dao Ŭ����
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

public interface Sbi0095D {

	/** 
	 * �귣��-���� ��ȸ
	 * @param Map<String, Object> paraMap
	 * @return List<ZSDT1144>
	 * @throws Exception
	 */
	public List<ZSDT1144> selectZsdt1144List(Map<String, Object> paraMap) throws Exception;

	/** 
	 * �귣��-���� ����
	 * @param ZSDT1144
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1144(ZSDT1144 zsdt1144) throws Exception;

	/** 
	 * �귣��-���� ����
	 * @param ZSDT1144
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1144(ZSDT1144 zsdt1144) throws Exception;

	/** 
	 * �귣��-���� ����
	 * @param ZSDT1144
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1144(ZSDT1144 zsdt1144) throws Exception;

}
