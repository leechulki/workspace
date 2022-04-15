package hdel.sd.com.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.com.domain.Coms01A;
import hdel.sd.sbi.domain.ZSDT1143;
import hdel.sd.com.domain.BrndSayang;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;

public interface Coms01AD {
	public List<Coms01A> selectBrandGtypeList(Coms01A param);
	
	public String selectBrandFlag(Coms01A param);
	
	// 2020년 브랜드 영업사양 특성코드 콤보 - vkbur
	public List<ComboVO> selectAtwrt(ComboParamVO param);

	// 2020년 브랜드 영업사양 특성값
	public List<BrndSayang> selectsSyangPrd(Map<String, String> param);

	/** 
	 * 소그룹 타이틀 조회
	 * @param ZPRDGBN(제품구분)
	 * @return List<ZSDT1143>
	 * @throws Exception
	 */
	public List<ZSDT1143> selectGroupTitList(Map<String, Object> param) throws Exception;
	
	public String selectMinCdateQt(Coms01A param);
	
}