package hdel.sd.sch.dao;

import hdel.sd.sch.domain.Sch0062;
import hdel.sd.sch.domain.Sch0062ParamVO;

import java.util.List;

public interface Sch0062D {

	public List<Sch0062> selectZSDT0045(Sch0062ParamVO param);

	public void mergeZSDT0045 (Sch0062 param);

	public void deleteZSDT0045 (Sch0062 param);

}
