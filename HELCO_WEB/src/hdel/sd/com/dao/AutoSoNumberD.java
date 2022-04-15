package hdel.sd.com.dao;

import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.domain.AutoNumberParamVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AutoSoNumberD {

	public Integer selectExist(@Param("mandt") String mandt, @Param("zgbn") String zgbn, @Param("spart") String spart);
	
	public Long selectNo(@Param("mandt") String mandt, @Param("zgbn") String zgbn, @Param("spart") String spart);
	
	public void insertNo(@Param("mandt") String mandt, @Param("zgbn") String zgbn, @Param("spart") String spart, @Param("zno") long zno);
	
	public void updateNo(@Param("mandt") String mandt, @Param("zgbn") String zgbn, @Param("spart") String spart, @Param("zno") long zno);
	
	public List<AutoNumberVO> AutoSoNumber(AutoNumberParamVO param);//수주번호채번

}

