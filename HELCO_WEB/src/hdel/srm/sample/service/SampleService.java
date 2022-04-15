package hdel.srm.sample.service;

import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.srm.sample.dao.SampleDao;
import hdel.srm.sample.domain.Sample;
import hdel.srm.sample.domain.SampleParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 샘플 Service
 * <br>
 * 샘플 예제로 구현된 Service 클래스
 * 
 * @since 1.0
 * <p>
 * History<br>
 * 1.0  2007.01.01  jissfa    initial version
 * 
 * @version 1.0
 * 
 * @author <a href="mailto:jissfa@naver.com">jissfa</a>
 */
@Transactional
@Service
public class SampleService extends SrmService {

	private SampleDao sampleDao;
	
	public void createDao(SqlSession sqlSession) {
		sampleDao = sqlSession.getMapper(SampleDao.class);
	}
	
	/**
	 * 샘플 조회
	 * @param param
	 * @return
	 */
	public List<Sample> find(SampleParamVO param) {
		return sampleDao.select(param);
	}
	
	/**
	 * 샘플 메시지
	 */
	public void message() {
		// 로직처리
		if (1 == 1) {
			throw new BizException("CW00005");
		}
	}
}
