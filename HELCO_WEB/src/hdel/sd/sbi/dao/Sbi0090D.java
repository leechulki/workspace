package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.sbi.domain.SanyangPrd;
import hdel.sd.sbi.domain.SanyangPrh;
import hdel.sd.sbi.domain.ZSDT1141;
import hdel.sd.sbi.domain.ZSDT1142;

/**
 * �귣�� ��� Dao Ŭ����
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

public interface Sbi0090D {

	
	/** 
	 * ������ �귣�� �ڵ� ��ȸ
	 * @param Map<String, Object> parmMap
	 * @return Coms01A
	 * @throws Exception
	 */
	public List<ComboVO> selectBrndSeqAtwrt(ComboParamVO param) throws Exception;

	
	/** 
	 * �귣�� ����Ư�� ��ȸ
	 * @param Map<String, Object> parmMap
	 * @return ZSDT1141
	 * @throws Exception
	 */
	public List<SanyangPrh> selectZsdt1141List(Map<String, Object> parmMap) throws Exception;

	/** 
	 * �귣�� ����Ư���� ��ȸ
	 * @param Map<String, Object> parmMap
	 * @return Com040SanyangPrh
	 * @throws Exception
	 */
	public List<SanyangPrd> selectZsdt1142List(Map<String, Object> parmMap) throws Exception;

	/** 
	 * �귣�� �����ٿ�ε� ����Ư���� ��ȸ
	 * @param Map<String, Object> parmMap
	 * @return Com040SanyangPrh
	 * @throws Exception
	 */
	public List<SanyangPrd> selectExcelZsdt1142List(Map<String, Object> parmMap) throws Exception;
	
	/** 
	 * �귣�庰 ����Ư�� ��� �Ǽ�
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public int selectBrndCdZsdt1141Cnt(Map<String, Object> paramMap) throws Exception;
	
	/** 
	 * �귣�� ����Ư�� ����
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1141(ZSDT1141 Zsdt1141) throws Exception;

	/** 
	 * �귣�� ����Ư�� ���� ������ ����
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int insertZeaitZsdt1141(ZSDT1141 Zsdt1141) throws Exception;
	
	/** 
	 * �귣�� ����Ư�� ����
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1141(ZSDT1141 Zsdt1141) throws Exception;

	/** 
	 * �귣�� ����Ư�� ����
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1141(ZSDT1141 Zsdt1141) throws Exception;

	/** 
	 * �귣�� ����Ư���� ����
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1142(ZSDT1142 Zsdt1142) throws Exception;

	/** 
	 * �귣�� ����Ư���� ���� ������ ����
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int insertZeaitZsdt1142(ZSDT1142 Zsdt1142) throws Exception;
	
	/** 
	 * �귣�� ����Ư���� ����
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1142(ZSDT1142 Zsdt1142) throws Exception;

	/** 
	 * �귣�� ����Ư���� ����
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1142(ZSDT1142 Zsdt1142) throws Exception;
	
}
