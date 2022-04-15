package hdel.sd.com.service;

import java.util.List;

import hdel.lib.exception.BizException;
import hdel.lib.exception.RequireException;
import hdel.lib.service.SrmService;
import hdel.sd.com.dao.LockD;
import hdel.sd.com.domain.LockVO;


import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class LockS extends SrmService
{

	private Logger logger = Logger.getLogger(this.getClass());
	
	private LockD dao;
	
	public void createDao(SqlSession sqlSession)
	{
		dao = sqlSession.getMapper(LockD.class);
	}
	
	
	/**
	 * 작업 시작 전에 락이 걸려있는지 조회한다.
	 * @param lockVo
	 * @return
	 */
	public List<LockVO> selectLock(LockVO lockVo)
	{
		// 필수 입력 항목 체크
		if ( lockVo.getMANDT().equals("") ) {
			throw new RequireException("MANDT");
		}
		if ( lockVo.getPGMCODE().equals("") ) {
			throw new RequireException("PGMCODE");
		}
		if ( lockVo.getKEYID().equals("") ) {
			throw new RequireException("KEYID");
		}
		
		String tmpUserId = lockVo.getUSERID();
		
		List<LockVO> list = dao.selectLock(lockVo);
		logger.debug("LOCK 건수 : " + list.size());
		if ( list != null && list.size() > 0 ) {
			if ( ! list.get(0).getUSERID().equals(tmpUserId) ) { // 내가 아닌 다른 사용자가 락을 걸고 있으면
				throw new BizException("현재 다른 사용자("+ list.get(0).getUSERID() + " : " + list.get(0).getUSERNAME() +")가 사용중 입니다.");
			}
		}
		return list;
	}
	
	/**
	 * 현재 락이 걸려있는 프로그램의 목록을 조회한다. (관리자용 페이지에서 목록 조회용)
	 * @param lockVo
	 * @return
	 */
	public List<LockVO> selectLockList(LockVO lockVo)
	{
		List<LockVO> list = dao.selectLockList(lockVo);
		logger.debug("LOCK 건수 : " + list.size());
		return list;
	}
	
	/**
	 * 작업 시작을 위해 잠금을 설정한다.
	 * @param lockVo
	 * @return
	 */
	public int setLock(LockVO lockVo, SqlSession session)
	{
		int iReturn = 0;
		// 필수 입력 항목 체크
		if ( lockVo.getMANDT().equals("") ) {
			throw new RequireException("MANDT");
		}
		if ( lockVo.getPGMCODE().equals("") ) {
			throw new RequireException("PGMCODE");
		}
		if ( lockVo.getKEYID().equals("") ) {
			throw new RequireException("KEYID");
		}
		if ( lockVo.getUSERID().equals("") ) {
			throw new RequireException("USERID");
		}
		
		String tmpUserId = lockVo.getUSERID();
		List<LockVO> list = null;

		// DAO생성
		createDao(session);

		// 현재 다른 사용자가 락을 걸고 있는지 확인한다.
		try
		{
			list = dao.selectLock(lockVo);
			iReturn = dao.deleteUnUseKey(lockVo);	// 등록된 Key 이외의 값 제거
		}catch	(Exception e)	{
			e.printStackTrace();
			throw new BizException("CE00001");		// 처리에 실패했습니다.\n확인 후 다시 처리해 주십시오.
		}

		logger.debug("LOCK 건수 : " + list.size());
		if ( list != null && list.size() > 0 ) { // 락이 걸려 있음
			if ( ! list.get(0).getUSERID().equals(tmpUserId) ) { // 내가 아닌 다른 사용자가 락을 걸고 있으면
				throw new BizException("현재 다른 사용자("+ list.get(0).getUSERID() + " : " + list.get(0).getUSERNAME() +")가 사용중입니다.");
			}
		}else	{
			try
			{
				iReturn = dao.insertLock(lockVo);		// Lock 설정
			}catch(Exception e)	{
				e.printStackTrace();
				throw new BizException("CE00001");		// 처리에 실패했습니다.\n확인 후 다시 처리해 주십시오.
			}
		}

		return iReturn;
	}
	
	/**
	 * 작업 종료를 위해 잠금을 해제한다.
	 * @param lockVo
	 * @return
	 */
	public int releaseLock(LockVO lockVo, SqlSession session)
	{
		// 필수 입력 항목 체크
		if ( lockVo.getMANDT().equals("") ) {
			throw new RequireException("MANDT");
		}
		if ( lockVo.getPGMCODE().equals("") ) {
			throw new RequireException("PGMCODE");
		}
		if ( lockVo.getKEYID().equals("") ) {
			throw new RequireException("KEYID");
		}
		if ( lockVo.getUSERID().equals("") ) {
			throw new RequireException("USERID");
		}

		try
		{
			// DAO생성
			createDao(session);

			return dao.deleteLock(lockVo);
		}catch	(Exception e)	{
			e.printStackTrace();
			throw new BizException("CE00001");		// 처리에 실패했습니다.\n확인 후 다시 처리해 주십시오.
		}
	}
}
