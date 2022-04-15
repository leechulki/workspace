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
	 * �۾� ���� ���� ���� �ɷ��ִ��� ��ȸ�Ѵ�.
	 * @param lockVo
	 * @return
	 */
	public List<LockVO> selectLock(LockVO lockVo)
	{
		// �ʼ� �Է� �׸� üũ
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
		logger.debug("LOCK �Ǽ� : " + list.size());
		if ( list != null && list.size() > 0 ) {
			if ( ! list.get(0).getUSERID().equals(tmpUserId) ) { // ���� �ƴ� �ٸ� ����ڰ� ���� �ɰ� ������
				throw new BizException("���� �ٸ� �����("+ list.get(0).getUSERID() + " : " + list.get(0).getUSERNAME() +")�� ����� �Դϴ�.");
			}
		}
		return list;
	}
	
	/**
	 * ���� ���� �ɷ��ִ� ���α׷��� ����� ��ȸ�Ѵ�. (�����ڿ� ���������� ��� ��ȸ��)
	 * @param lockVo
	 * @return
	 */
	public List<LockVO> selectLockList(LockVO lockVo)
	{
		List<LockVO> list = dao.selectLockList(lockVo);
		logger.debug("LOCK �Ǽ� : " + list.size());
		return list;
	}
	
	/**
	 * �۾� ������ ���� ����� �����Ѵ�.
	 * @param lockVo
	 * @return
	 */
	public int setLock(LockVO lockVo, SqlSession session)
	{
		int iReturn = 0;
		// �ʼ� �Է� �׸� üũ
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

		// DAO����
		createDao(session);

		// ���� �ٸ� ����ڰ� ���� �ɰ� �ִ��� Ȯ���Ѵ�.
		try
		{
			list = dao.selectLock(lockVo);
			iReturn = dao.deleteUnUseKey(lockVo);	// ��ϵ� Key �̿��� �� ����
		}catch	(Exception e)	{
			e.printStackTrace();
			throw new BizException("CE00001");		// ó���� �����߽��ϴ�.\nȮ�� �� �ٽ� ó���� �ֽʽÿ�.
		}

		logger.debug("LOCK �Ǽ� : " + list.size());
		if ( list != null && list.size() > 0 ) { // ���� �ɷ� ����
			if ( ! list.get(0).getUSERID().equals(tmpUserId) ) { // ���� �ƴ� �ٸ� ����ڰ� ���� �ɰ� ������
				throw new BizException("���� �ٸ� �����("+ list.get(0).getUSERID() + " : " + list.get(0).getUSERNAME() +")�� ������Դϴ�.");
			}
		}else	{
			try
			{
				iReturn = dao.insertLock(lockVo);		// Lock ����
			}catch(Exception e)	{
				e.printStackTrace();
				throw new BizException("CE00001");		// ó���� �����߽��ϴ�.\nȮ�� �� �ٽ� ó���� �ֽʽÿ�.
			}
		}

		return iReturn;
	}
	
	/**
	 * �۾� ���Ḧ ���� ����� �����Ѵ�.
	 * @param lockVo
	 * @return
	 */
	public int releaseLock(LockVO lockVo, SqlSession session)
	{
		// �ʼ� �Է� �׸� üũ
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
			// DAO����
			createDao(session);

			return dao.deleteLock(lockVo);
		}catch	(Exception e)	{
			e.printStackTrace();
			throw new BizException("CE00001");		// ó���� �����߽��ϴ�.\nȮ�� �� �ٽ� ó���� �ֽʽÿ�.
		}
	}
}
