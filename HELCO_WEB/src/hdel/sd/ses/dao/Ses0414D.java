package hdel.sd.ses.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import hdel.sd.ses.domain.Ses0414;

public interface Ses0414D {

	public List<Ses0414> selectRepPm(@Param("mandt") String mandt, @Param("temno") String temno, @Param("vkgrp") String vkgrp);
	public List<Ses0414> searchPsPm(@Param("mandt") String mandt, @Param("temno") String temno);
	public List<Ses0414> searchRepPmByTeam(@Param("mandt") String mandt, @Param("vkgrp") String vkgrp);
}
