package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0210;
import hdel.sd.com.domain.Com0210ParamVO;

import java.util.List;
 
/**
 * ���ְ�ȹ��ȣ ��ȸ (Com0210D) DAO
 * @Comment
 *     	1. List selectListSonnr ( ���ְ�ȹ��ȣ ��ȸ )
 * @since 1.0
 * 		History
 * 		1.0  2012.08.01  ���翵  :  initial version
 * @version 1.0
 */

public interface Com0210D {

	public List<Com0210> selectListSonnr(Com0210ParamVO param);	// �ŷ�ó��� ��ȸ
}
