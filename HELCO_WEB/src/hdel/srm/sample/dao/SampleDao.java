package hdel.srm.sample.dao;

import hdel.srm.sample.domain.Sample;
import hdel.srm.sample.domain.SampleParamVO;

import java.util.List;

/**
 * ���� Data Access Object
 * <br>
 * ���� ������ ������ Data Access Object Ŭ����
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
public interface SampleDao {

	/**
	 * ���� Select
	 * @param param
	 * @return
	 */
	public List<Sample> select(SampleParamVO param);
}
