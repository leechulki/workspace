package hdel.sd.sch.dao;

import hdel.sd.sch.domain.Sch0063;
import hdel.sd.sch.domain.Sch0063ParamVO;

import java.util.List;

public interface Sch0063D {

	public List<Sch0063> selectZFIT1015(Sch0063ParamVO param);

	public void mergeZFIT1015(Sch0063 param);

	public void deleteZFIT1015(Sch0063 param);

}
