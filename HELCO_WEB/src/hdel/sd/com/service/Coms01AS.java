package hdel.sd.com.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Coms01AD;
import hdel.sd.com.domain.Coms01A;
import hdel.sd.sbi.domain.ZSDT1143;
import hdel.sd.com.domain.BrndSayang;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;


@Service
public class Coms01AS extends SrmService {
	private Coms01AD Coms01ADao;

	public void createDao(SqlSession sqlSession) {
		Coms01ADao = sqlSession.getMapper(Coms01AD.class);
	}

	public List<Coms01A> selectBrandGtypeList(Coms01A param) {
		
		String cdate = Coms01ADao.selectMinCdateQt(param);		
		param.setCDATE(cdate);
	
		return Coms01ADao.selectBrandGtypeList(param);
	}

	public String selectBrandFlag(Coms01A param) {
		return Coms01ADao.selectBrandFlag(param);
	}
	
    // 2020 브랜드
	public List<ComboVO> selectAtwrt(ComboParamVO param) {
		return Coms01ADao.selectAtwrt(param);
	}

    // 브랜드 영업특성값 조회
	public List<BrndSayang> sayangPrdList(Map<String, String> param) {
		return Coms01ADao.selectsSyangPrd(param);
	}
	
	/** 
	 * 소그룹 타이틀 조회
	 * @param ZPRDGBN(제품구분)
	 * @return List<ZSDT1143>
	 * @throws Exception
	 */
	public List<ZSDT1143> selectGroupTitList(Map<String, Object> param) throws Exception {
		List<ZSDT1143> zSdt1143List = null;
		zSdt1143List = Coms01ADao.selectGroupTitList(param);
		return zSdt1143List; 
	}
	
}