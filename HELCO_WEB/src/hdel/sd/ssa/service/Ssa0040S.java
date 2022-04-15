package hdel.sd.ssa.service;

import hdel.lib.service.SrmService;
import hdel.sd.ssa.dao.Ssa0020D;
import hdel.sd.ssa.dao.Ssa0040D;
import hdel.sd.ssa.domain.Ssa0040;
import hdel.sd.ssa.domain.Ssa0040ParamVO;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


/**
 * ���ݰ�꼭Cû����Ȳ(Ssa0040S) Service
 * @Comment
 *		1.  List reason_list  	( �����ڵ� ��ȸ ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.11.13  ������  :  initial version 
 * @version 1.0
 */


@Service
public class Ssa0040S extends SrmService {

	private Ssa0040D ssa0040Dao;
	
	public void createDao(SqlSession sqlSession) {
		ssa0040Dao = sqlSession.getMapper(Ssa0040D.class);
	}
	
	//�������� ��ȸ
	public List<Ssa0040> find_auart(Ssa0040ParamVO param) {
		return ssa0040Dao.selectListAuart(param);
	} 
	
	//�����ڵ� ��ȸ
	public List<Ssa0040> find_reason() {
		return ssa0040Dao.selectListReason();
	}  
	
}
