package hdel.sd.sbi.dao;

import java.util.List;
import hdel.sd.sbi.domain.Sbi0010;
import hdel.sd.sbi.domain.Sbi0010ParamVO;

public interface Sbi0010D {
	public List<Sbi0010> selectRegio(Sbi0010ParamVO param);
	
}
