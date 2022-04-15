package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.AutoBpNumberD;
import hdel.sd.com.dao.AutoBpNumberBosuD;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.domain.AutoNumberParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class AutoBpNumberS extends SrmService  {
	
	private AutoBpNumberD AutoNumberDao;
	
	private AutoBpNumberBosuD AutoNumberBosuDao;
	
	public void createDao(SqlSession sqlSession) {
		AutoNumberDao = sqlSession.getMapper(AutoBpNumberD.class);
		
		AutoNumberBosuDao = sqlSession.getMapper(AutoBpNumberBosuD.class);
	}

	public List<AutoNumberVO> AutoBpNumber(AutoNumberParamVO param) {

		if (param.getDeptFlag().equals("ZS01")) {				// 국내승강기
			param.setDeptFlag("A");
		} else if (param.getDeptFlag().equals("ZS02")) {		// 국내승강기(대리점)
			param.setDeptFlag("A");
		} else if (param.getDeptFlag().equals("ZN01")) {		// 교체공사
			param.setDeptFlag("N");
		} else if (param.getDeptFlag().equals("ZN02")) {		// 교체공사(대리점)
			param.setDeptFlag("N");
		} else if (param.getDeptFlag().equals("ZJ01")) {		// 주차기
			param.setDeptFlag("J");
		} else if (param.getDeptFlag().equals("ZJ02")) {		// 주차기(대리점)
			param.setDeptFlag("J");
		} else if (param.getDeptFlag().equals("ZF01")) {		// 물류
			param.setDeptFlag("F");
		} else if (param.getDeptFlag().equals("ZG01")) {		// PSD
			param.setDeptFlag("G");
		} else if (param.getDeptFlag().equals("ZE01")) {		//해외일반수출
			param.setDeptFlag("E");
		} else if (param.getDeptFlag().equals("ZT01")) {		//중계무역
			param.setDeptFlag("T");
		} else if (param.getDeptFlag().equals("ZC01")) {		// 부품수출
			param.setDeptFlag("C");
		} else if (param.getDeptFlag().equals("ZR11")) {		// 정기보수
			param.setDeptFlag("U");
		} else if (param.getDeptFlag().equals("ZR12")) {		// 부품판매
			param.setDeptFlag("B");
		} else if (param.getDeptFlag().equals("ZR13")) {		// 수리공사
			param.setDeptFlag("S");
		}
		
		System.out.print("\n@@@ param.getDeptFlag().toString()!!!" + param.getDeptFlag().toString() + "\n");
		
		if (param.getDeptFlag().toString() == "U" || param.getDeptFlag().toString() == "B" ||param.getDeptFlag().toString() == "S") {
			System.out.print("\n@@@ AutoNumberBosuDao.AutoBpNumberBosu!!!" + "\n");
			return AutoNumberBosuDao.AutoBpNumberBosu(param);
		} else {
			System.out.print("\n@@@ AutoNumberDao.AutoBpNumber!!!" + "\n");
			return AutoNumberDao.AutoBpNumber(param);
		}
		
	}
}