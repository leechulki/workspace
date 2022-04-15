package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.sbi.domain.ZSDT1139;

/**
 * �귣������ ��� Dao Ŭ����
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

public interface Sbi0091D {

	/** 
	 * �귣�� ���� ��ȸ
	 * @param Map<String, Object> paraMap
	 * @return List<ZSDT1139>
	 * @throws Exception
	 */
	public List<ZSDT1139> selectZsdt1139List(Map<String, Object> paraMap) throws Exception;

	/** 
	 * �귣�� ���� ����
	 * @param ZSDT1139
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1139(ZSDT1139 zsdt1139) throws Exception;

	/** 
	 * �귣�� ���� ����
	 * @param ZSDT1139
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1139(ZSDT1139 zsdt1139) throws Exception;

	/** 
	 * �귣�� ���� ����
	 * @param ZSDT1139
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1139(ZSDT1139 zsdt1139) throws Exception;

	/** 
	 * �ƽ� �귣����������
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public String selectMaxBrndseq(String mandt) throws Exception;
	
}
