package hdel.sd.sbp.dao;
 
import hdel.sd.com.domain.Com0060ParamVO;
import hdel.sd.sbp.domain.Sbp0160ParamVO;
import hdel.sd.sbp.domain.Sbp0160VO_N;
import hdel.sd.sbp.domain.Sbp0161ParamVO;
import hdel.sd.sbp.domain.Sbp0161VO_N;

import java.util.List;

public interface Sbp0160D_N {

	// 조회
	public List<Sbp0160VO_N> selectZSDT1001_N(Sbp0160ParamVO param);

	// 해외대리점 조회
	public List<Sbp0160VO_N> selectZSDT1001E_N(Sbp0160ParamVO param);
	
	// 실기종
	public List<Com0060ParamVO> selectRTYPE(Com0060ParamVO param);

	// 원가산출용 템플릿 정보 조회 (템플릿번호로 조회)
	public List<Sbp0160VO_N> selectListTempletInfo(Sbp0160ParamVO param);

	// 수주계획번호별 매출청구수금 조회(존재하는 데이터)
	public List<Sbp0161VO_N> selectListDetail_N(Sbp0161ParamVO param);

	// 수주계획번호별 매출청구수금 조회(존재하는 않는 데이터)
	public List<Sbp0161VO_N> selectListDetail_Null(Sbp0161ParamVO param);

	// 이동계획 1차 마감여부
	public List<Sbp0160VO_N> selectZclflg(Sbp0160ParamVO param);
	
}
