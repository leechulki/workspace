package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0180;
import hdel.sd.com.domain.Com0180ParamVO;

import java.util.List;
 
/**
 * �����ȣ ��ȸ (Com0180D) DAO
 * @Comment
 *     	1. List selectListBuyr ( �����ȣ ��ȸ )
 * @since 1.0
 * 		History
 * 		1.0  2012.08.01  ���翵  :  initial version
 * @version 1.0
 */

public interface Com0180D {

	public List<Com0180> selectListBuyr(Com0180ParamVO param);	// �ŷ�ó��� ��ȸ
}
