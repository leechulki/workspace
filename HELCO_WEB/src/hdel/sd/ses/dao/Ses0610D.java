package hdel.sd.ses.dao;

import java.util.List;

import hdel.sd.ses.domain.Ses0610;
import hdel.sd.ses.domain.Ses0610ParamVO;

public interface Ses0610D {
	// 리모델링 수요정보 조회
	public List<Ses0610> find(Ses0610ParamVO param);
	
	// 리모델링 수요정보 merge
	public void merge(List<Ses0610> param);
	
	// 예외수정자 조회
	public List<Ses0610> findzsdt0222(Ses0610ParamVO param);
}
