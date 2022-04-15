package hdel.sd.sso.dao;
 
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.Vbeln;

import hdel.sd.sso.domain.Sso0020ParamVO;
import hdel.sd.sso.domain.Sso0020VO;
import hdel.sd.sso.domain.Sso0020Partner;
import hdel.sd.sso.domain.ZSDT1058; 
import hdel.sd.sso.domain.ZSDT0090; 

public interface Sso0020D {  

	public List<Sso0020VO> SelectAutoLP(Sso0020ParamVO param);
	public List<Sso0020VO> SelectSpecE(Sso0020ParamVO param);
	public List<ZSDT1058> select1058Info(@Param("mandt") String sMandt, @Param("vbeln") String vbeln);
	public Integer getLast90Seq(@Param("mandt") String mandt, @Param("vbeln") String vbeln);
	public List<Sso0020Partner> getPartner(@Param("mandt") String mandt, @Param("vbeln") Vbeln vbeln);
	public Integer getGspCnt(@Param("mandt") String mandt, @Param("vbeln") String vbeln);
	public List<ZSDT0090> selectChnsoFinl(@Param("mandt") String mandt, @Param("vbeln") String vbeln);
}