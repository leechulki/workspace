package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0040;
import hdel.sd.com.domain.Com0040ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
 
/**
 * �ŷ������ ��ȸ (Com0040D) DAO
 * @Comment 
 *     	1. List selectListBuyr ( �ŷ������ ��ȸ )
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  ������  :  initial version 
 * @version 1.0
 */

public interface Com0040D {  
	
	// �ŷ�ó��� ��ȸ
	public List<Com0040> selectListBuyr(Com0040ParamVO param);
	
}
