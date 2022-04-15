package hdel.sd.com.dao;

import hdel.sd.com.domain.Testind;

import org.apache.ibatis.annotations.Param;

public interface TestindD {
	
	public Testind selectTestUser(@Param("mandt") String sMandt, @Param("pname") String spName, @Param("srmid") String suserId);
		
}
