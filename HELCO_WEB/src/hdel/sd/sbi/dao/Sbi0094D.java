package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;
import hdel.sd.sbi.domain.ZSDT1141;
import hdel.sd.sbi.domain.ZSDT1142;

/**
 * 브랜드 Batch 등록 Dao 클래스
 * 
 * @author  박수근
 * @since 2020.08.20
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.08.20         박수근          최초 생성
 * 
 * </pre>
 */

public interface Sbi0094D {

	/** 
	 * 브랜드 영업특성 배치처리 데이터 생성
	 * @param Map<String, Object> paraMap
	 * @return List<ZSDT1141>
	 * @throws Exception
	 */
	public List<ZSDT1141> selectFlagZsdt1141List(Map<String, Object> paraMap) throws Exception;

	/** 
	 * 브랜드 영업특성값 배치처리 데이터 생성
	 * @param Map<String, Object> paraMap
	 * @return List<ZSDT1142>
	 * @throws Exception
	 */
	public List<ZSDT1142> selectFlagZsdt1142List(Map<String, Object> paraMap) throws Exception;

	/** 
	 * 브랜드 영업특성 배치 저장
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int insertBatchZsdt1141(List<ZSDT1141> zsdt1141) throws Exception;

	/** 
	 * 브랜드 영업특성 배치 연동 데이터 저장
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int insertBatchZeaitZsdt1141(List<ZSDT1141> zsdt1141) throws Exception;
	
	/** 
	 * 브랜드 영업특성 배치 수정
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int updateBatchZsdt1141(List<ZSDT1141> zsdt1141) throws Exception;
	
	/** 
	 * 브랜드 영업특성 배치 삭제
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int deleteBatchZsdt1141(List<ZSDT1141> zsdt1141) throws Exception;
	
	/** 
	 * 브랜드 영업특성값 배치 저장
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int insertBatchZsdt1142(List<ZSDT1142> zsdt1142) throws Exception;

	/** 
	 * 브랜드 영업특성값 배치 연동데이터 저장
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int insertBatchZeaitZsdt1142(List<ZSDT1142> zsdt1142) throws Exception;
	
	/** 
	 * 브랜드 영업특성값 배치 수정
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int updateBatchZsdt1142(List<ZSDT1142> zsdt1142) throws Exception;

	/** 
	 * 브랜드 영업특성값 배치 삭제
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int deleteBatchZsdt1142(List<ZSDT1142> zsdt1142) throws Exception;

}
