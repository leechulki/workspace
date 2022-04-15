package hdel.sd.sso.dao;

import hdel.sd.com.domain.BrndSayang;
import hdel.sd.sso.domain.Sso0051;
import hdel.sd.sso.domain.Sso0051ParamVO;

import java.util.List;
import java.util.Map;


/**
 * 사양복사(Sso0051D) DAO
 * @Comment 
 *		- List selectListSpec 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2013.03.11  김선호  :  initial version 
 * @version 1.0
 */


public interface Sso0051ND {  
	
	// 프로젝트번호로 견적번호 조회
	public List<Sso0051> selectListSpec(Sso0051ParamVO param);

	// 2020년 브랜드 영업사양 특성값
	public List<BrndSayang> selectsSyangPrd(Map<String, String> param);
	
}
