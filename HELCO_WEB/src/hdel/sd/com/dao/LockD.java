package hdel.sd.com.dao;


import java.util.List;

import hdel.sd.com.domain.LockVO;

public interface LockD {

	public List<LockVO> selectLock(LockVO lockVo);
	public List<LockVO> selectLockList(LockVO lockVo);
	public int insertLock(LockVO lockVo);
	public int deleteLock(LockVO lockVo);
	public int deleteUnUseKey(LockVO lockVo);
}
