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
 * 브랜드 등록 Dao 클래스
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

public interface Sbi0090D {

	
	/** 
	 * 차수별 브랜드 코드 조회
	 * @param Map<String, Object> parmMap
	 * @return Coms01A
	 * @throws Exception
	 */
	public List<ComboVO> selectBrndSeqAtwrt(ComboParamVO param) throws Exception;

	
	/** 
	 * 브랜드 영업특성 조회
	 * @param Map<String, Object> parmMap
	 * @return ZSDT1141
	 * @throws Exception
	 */
	public List<SanyangPrh> selectZsdt1141List(Map<String, Object> parmMap) throws Exception;

	/** 
	 * 브랜드 영업특성값 조회
	 * @param Map<String, Object> parmMap
	 * @return Com040SanyangPrh
	 * @throws Exception
	 */
	public List<SanyangPrd> selectZsdt1142List(Map<String, Object> parmMap) throws Exception;

	/** 
	 * 브랜드 엑셀다운로드 영업특성값 조회
	 * @param Map<String, Object> parmMap
	 * @return Com040SanyangPrh
	 * @throws Exception
	 */
	public List<SanyangPrd> selectExcelZsdt1142List(Map<String, Object> parmMap) throws Exception;
	
	/** 
	 * 브랜드별 영업특성 등록 건수
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public int selectBrndCdZsdt1141Cnt(Map<String, Object> paramMap) throws Exception;
	
	/** 
	 * 브랜드 영업특성 저장
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1141(ZSDT1141 Zsdt1141) throws Exception;

	/** 
	 * 브랜드 영업특성 연동 데이터 저장
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int insertZeaitZsdt1141(ZSDT1141 Zsdt1141) throws Exception;
	
	/** 
	 * 브랜드 영업특성 수정
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1141(ZSDT1141 Zsdt1141) throws Exception;

	/** 
	 * 브랜드 영업특성 삭제
	 * @param Zsdt1141
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1141(ZSDT1141 Zsdt1141) throws Exception;

	/** 
	 * 브랜드 영업특성값 저장
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int insertZsdt1142(ZSDT1142 Zsdt1142) throws Exception;

	/** 
	 * 브랜드 영업특성값 연동 데이터 저장
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int insertZeaitZsdt1142(ZSDT1142 Zsdt1142) throws Exception;
	
	/** 
	 * 브랜드 영업특성값 수정
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int updateZsdt1142(ZSDT1142 Zsdt1142) throws Exception;

	/** 
	 * 브랜드 영업특성값 삭제
	 * @param Zsdt1142
	 * @return int
	 * @throws Exception
	 */
	public int deleteZsdt1142(ZSDT1142 Zsdt1142) throws Exception;
	
}
