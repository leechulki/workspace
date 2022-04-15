package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.sbi.domain.ZSDT1140;

/**
 * 브랜드 적용일자 등록 Dao 클래스
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

public interface Sbi0092D {

	/** 
	 * 브랜드 적용일자 조회
	 * @param Map<String, Object> paraMap
	 * @return List<Zsdt1140>
	 * @throws Exception
	 */
	public List<ZSDT1140> selectZsdt1140List(Map<String, Object> paraMap) throws Exception;

	/** 
	 * 브랜드 적용일자 저장
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * 브랜드 적용일자 연동 데이터 저장
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int insertZeaitZsdt1140(ZSDT1140 Zsdt1140) throws Exception;
	
	/** 
	 * 브랜드 적용일자 수정
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * 브랜드 이전 차수 종료일자 자동 생성
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int updateTodatZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * 브랜드 이전 차수 종료일자 99991231로 처리
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int updateLastTodatZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * 브랜드 수정 시 이전차수 적용 연동 데이터 조회
	 * @param Map<String, Object> paraMap
	 * @return List<Zsdt1140>
	 * @throws Exception
	 */
	public ZSDT1140 selectPreMaxUpdateBrndZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * 브랜드 수정 시 이전차수 적용 연동 데이터 조회
	 * @param Map<String, Object> paraMap
	 * @return List<Zsdt1140>
	 * @throws Exception
	 */
	public ZSDT1140 selectPreMaxDeleteBrndZsdt1140(ZSDT1140 Zsdt1140) throws Exception;
	
	/** 
	 * 브랜드 적용일자 삭제
	 * @param Zsdt1140
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1140(ZSDT1140 Zsdt1140) throws Exception;

	/** 
	 * 브랜드 시작일자 조회
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public String selectBrnFrDat(ZSDT1140 Zsdt1140) throws Exception;
	
	/** 
	 * 브랜드차수별 브랜드 적용일자 등록 건수
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public int selectBrndSeqZsdt1140Cnt(Map<String, Object> paramMap) throws Exception;
	
}
