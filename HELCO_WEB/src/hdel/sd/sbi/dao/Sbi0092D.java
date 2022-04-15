package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.sbi.domain.ZSDT1140;

/**
 * �귣�� �������� ��� Dao Ŭ����
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

public interface Sbi0092D {

	/** 
	 * �귣�� �������� ��ȸ
	 * @param Map<String, Object> paraMap
	 * @return List<Zsdt1140>
	 * @throws Exception
	 */
	public List<ZSDT1140> selectZsdt1140List(Map<String, Object> paraMap) throws Exception;

	/** 
	 * �귣�� �������� ����
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * �귣�� �������� ���� ������ ����
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int insertZeaitZsdt1140(ZSDT1140 Zsdt1140) throws Exception;
	
	/** 
	 * �귣�� �������� ����
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * �귣�� ���� ���� �������� �ڵ� ����
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int updateTodatZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * �귣�� ���� ���� �������� 99991231�� ó��
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int updateLastTodatZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * �귣�� ���� �� �������� ���� ���� ������ ��ȸ
	 * @param Map<String, Object> paraMap
	 * @return List<Zsdt1140>
	 * @throws Exception
	 */
	public ZSDT1140 selectPreMaxUpdateBrndZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * �귣�� ���� �� �������� ���� ���� ������ ��ȸ
	 * @param Map<String, Object> paraMap
	 * @return List<Zsdt1140>
	 * @throws Exception
	 */
	public ZSDT1140 selectPreMaxDeleteBrndZsdt1140(ZSDT1140 Zsdt1140) throws Exception;
	
	/** 
	 * �귣�� �������� ����
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * �귣�� �������� ��ȸ
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public String selectBrnFrDat(ZSDT1140 Zsdt1140) throws Exception;
	
	/** 
	 * �귣�������� �귣�� �������� ��� �Ǽ�
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public int selectBrndSeqZsdt1140Cnt(Map<String, Object> paramMap) throws Exception;
	
}
