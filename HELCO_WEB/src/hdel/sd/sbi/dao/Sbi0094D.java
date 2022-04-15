package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;
import hdel.sd.sbi.domain.ZSDT1141;
import hdel.sd.sbi.domain.ZSDT1142;

/**
 * �귣�� Batch ��� Dao Ŭ����
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

public interface Sbi0094D {

	/** 
	 * �귣�� ����Ư�� ��ġó�� ������ ����
	 * @param Map<String, Object> paraMap
	 * @return List<ZSDT1141>
	 * @throws Exception
	 */
	public List<ZSDT1141> selectFlagZsdt1141List(Map<String, Object> paraMap) throws Exception;

	/** 
	 * �귣�� ����Ư���� ��ġó�� ������ ����
	 * @param Map<String, Object> paraMap
	 * @return List<ZSDT1142>
	 * @throws Exception
	 */
	public List<ZSDT1142> selectFlagZsdt1142List(Map<String, Object> paraMap) throws Exception;

	/** 
	 * �귣�� ����Ư�� ��ġ ����
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int insertBatchZsdt1141(List<ZSDT1141> zsdt1141) throws Exception;

	/** 
	 * �귣�� ����Ư�� ��ġ ���� ������ ����
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int insertBatchZeaitZsdt1141(List<ZSDT1141> zsdt1141) throws Exception;
	
	/** 
	 * �귣�� ����Ư�� ��ġ ����
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int updateBatchZsdt1141(List<ZSDT1141> zsdt1141) throws Exception;
	
	/** 
	 * �귣�� ����Ư�� ��ġ ����
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int deleteBatchZsdt1141(List<ZSDT1141> zsdt1141) throws Exception;
	
	/** 
	 * �귣�� ����Ư���� ��ġ ����
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int insertBatchZsdt1142(List<ZSDT1142> zsdt1142) throws Exception;

	/** 
	 * �귣�� ����Ư���� ��ġ ���������� ����
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int insertBatchZeaitZsdt1142(List<ZSDT1142> zsdt1142) throws Exception;
	
	/** 
	 * �귣�� ����Ư���� ��ġ ����
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int updateBatchZsdt1142(List<ZSDT1142> zsdt1142) throws Exception;

	/** 
	 * �귣�� ����Ư���� ��ġ ����
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int deleteBatchZsdt1142(List<ZSDT1142> zsdt1142) throws Exception;

}
