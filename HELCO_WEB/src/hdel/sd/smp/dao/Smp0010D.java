package hdel.sd.smp.dao;

import hdel.sd.smp.domain.Smp0010ParamVO;
import hdel.sd.smp.domain.SmpComParameterVO;

import java.util.List;

/**
 * 이동계획
 * 이동계획 편성
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Smp0010D {

	public List<Smp0010ParamVO> find(Smp0010ParamVO param);// 전체 조회
	public void update(Smp0010ParamVO param);// 갱신
	public void insert(Smp0010ParamVO param);// 추가
	public void delete(Smp0010ParamVO param);// 삭제
	public List<SmpComParameterVO> teamCd(SmpComParameterVO param);// 코드조회
}
