package hdel.sd.sbp.dao;
 
import hdel.sd.sbp.domain.ComboParamVO;
import hdel.sd.sbp.domain.ComboVO;
import hdel.sd.sbp.domain.Sbp00101;
import hdel.sd.sbp.domain.Sbp00102;
import hdel.sd.sbp.domain.Sbp0160;
import hdel.sd.sbp.domain.Sbp0160ParamVO;

import java.util.List;

public interface Sbp0160D {

	// 수주가능성
	public List<ComboVO> selectDD07T(ComboParamVO param);

	// 기종
	public List<ComboVO> selectGtype();

	// 설치지역
	public List<ComboVO> selectREGION(ComboParamVO param);
	
	public List<Sbp0160> selectZSDT1001(Sbp0160ParamVO param);
	
	public List<Sbp0160> selectZSDT1001E(Sbp0160ParamVO param);

	// 사업계획테이블 원가변경
	public void updateCostZSDT1001(Sbp0160 param);

	// 원가산출용 템플릿 정보 조회 (템플릿번호로 조회)
	public List<Sbp0160> selectListTempletInfo(Sbp0160ParamVO param);

}
