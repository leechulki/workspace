package hdel.sd.sbp.dao;
 
import hdel.sd.sbp.domain.Sbp0162ParamVO;
import hdel.sd.ses.domain.Ses0180;

public interface Sbp0162D {

	int updateSORLT(Sbp0162ParamVO param);

	int insertSORLT1(Sbp0162ParamVO param);

	int insertSORLT2(Sbp0162ParamVO param);

	int insertSORLT3(Sbp0162ParamVO param);

	int insertSORLT4(Sbp0162ParamVO param);
	
	int updateZSDRT1047Sonnr(Sbp0162ParamVO param);
	
	int insertZSDT1055(Ses0180 ses0180);
}
