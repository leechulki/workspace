package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.sbi.domain.ZSDT1143;

/**
 * 브랜드 소그룹 Dao 클래스
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

public interface Sbi0093D {

	/** 
	 * 특성그룹 조회
	 * @param ZPRDGBN(제품구분)
	 * @return List<ZSDT1143>
	 * @throws Exception
	 */
	public List<ZSDT1143> selectZsdt1143List(Map<String, Object> param) throws Exception;
	
	/** 
	 * 브랜드 소그룹 타이틀 저장
	 * @param Zsdt1143
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1143(ZSDT1143 Zsdt1143) throws Exception;

	/** 
	 * 브랜드 소그룹 타이틀 연동 데이터 저장
	 * @param Zsdt1143
	 * @return int
	 * @throws Exception
	 */
	public int insertZeaitZsdt1143(ZSDT1143 Zsdt1143) throws Exception;
	
	/** 
	 * 브랜드 소그룹 타이틀 수정
	 * @param Zsdt1143
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1143(ZSDT1143 Zsdt1143) throws Exception;

	/** 
	 * 브랜드 소그룹 타이틀 삭제
	 * @param Zsdt1143
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1143(ZSDT1143 Zsdt1143) throws Exception;

}
