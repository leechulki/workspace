package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.sbi.domain.ZSDT1144;

/**
 * 브랜드-기종 등록 Dao 클래스
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

public interface Sbi0095D {

	/** 
	 * 브랜드-기종 조회
	 * @param Map<String, Object> paraMap
	 * @return List<ZSDT1144>
	 * @throws Exception
	 */
	public List<ZSDT1144> selectZsdt1144List(Map<String, Object> paraMap) throws Exception;

	/** 
	 * 브랜드-기종 저장
	 * @param ZSDT1144
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1144(ZSDT1144 zsdt1144) throws Exception;

	/** 
	 * 브랜드-기종 수정
	 * @param ZSDT1144
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1144(ZSDT1144 zsdt1144) throws Exception;

	/** 
	 * 브랜드-기종 삭제
	 * @param ZSDT1144
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1144(ZSDT1144 zsdt1144) throws Exception;

}
