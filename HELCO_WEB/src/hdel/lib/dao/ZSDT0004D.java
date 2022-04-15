package hdel.lib.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import hdel.lib.domain.ZSDT0004;

public interface ZSDT0004D {
	public List<ZSDT0004> select(ZSDT0004 zsdt0004);
	public List<ZSDT0004> selectByVbeln(ZSDT0004 zsdt0004);
}
