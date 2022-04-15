package hdel.sd.sbi.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.sbi.domain.SanyangPrd;

/**
 * 브랜드 조회 Dao 클래스
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

public interface Sbi0100D {

	/** 
	 * 브랜드 엑셀다운로드 영업특성값 조회
	 * @param Map<String, Object> parmMap
	 * @return Com040SanyangPrh
	 * @throws Exception
	 */
	public List<SanyangPrd> selectSayangList(Map<String, Object> parmMap) throws Exception;

}
