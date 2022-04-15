package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.sbi.domain.ZSDT1139;

/**
 * 브랜드차수 등록 Dao 클래스
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

public interface Sbi0091D {

	/** 
	 * 브랜드 차수 조회
	 * @param Map<String, Object> paraMap
	 * @return List<ZSDT1139>
	 * @throws Exception
	 */
	public List<ZSDT1139> selectZsdt1139List(Map<String, Object> paraMap) throws Exception;

	/** 
	 * 브랜드 차수 저장
	 * @param ZSDT1139
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1139(ZSDT1139 zsdt1139) throws Exception;

	/** 
	 * 브랜드 차수 수정
	 * @param ZSDT1139
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1139(ZSDT1139 zsdt1139) throws Exception;

	/** 
	 * 브랜드 차수 삭제
	 * @param ZSDT1139
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1139(ZSDT1139 zsdt1139) throws Exception;

	/** 
	 * 맥스 브랜드적용차수
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public String selectMaxBrndseq(String mandt) throws Exception;
	
}
