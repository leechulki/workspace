package hdel.srm.sample.dao;

import hdel.srm.sample.domain.Sample;
import hdel.srm.sample.domain.SampleParamVO;

import java.util.List;

/**
 * 基敲 Data Access Object
 * <br>
 * 基敲 抗力肺 备泅等 Data Access Object 努贰胶
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
	 * 基敲 Select
	 * @param param
	 * @return
	 */
	public List<Sample> select(SampleParamVO param);
}
