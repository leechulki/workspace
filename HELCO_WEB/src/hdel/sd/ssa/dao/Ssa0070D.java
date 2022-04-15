package hdel.sd.ssa.dao;

import hdel.sd.ssa.domain.Ssa0070;
import hdel.sd.ssa.domain.Ssa0070ParamVO;

import java.util.List;

public interface Ssa0070D {

	public List<Ssa0070> selectList(Ssa0070ParamVO param);
	public void mergeList(Ssa0070 param);

}
