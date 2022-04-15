package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.Com0060D;
import hdel.sd.com.domain.Com0060ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Com0060S extends SrmService {

	private Com0060D dao;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Com0060D.class);
	}

	// code
	public List<Com0060ParamVO> code( Com0060ParamVO param ) {
		
		List<Com0060ParamVO> list = null;
		
		// ����
		if ( "nation".equals( param.getFilter2() ) )
		{
			list =  dao.nation(param);
		}
		// �������
		else if ( "area".equals( param.getFilter2() ) )
		{
			list =  dao.area(param);
		}
		// �������
		else if ( "zkunnr".equals( param.getFilter2() ) )
		{
			list =  dao.zkunnr(param);
		}
		// ��ǰ
		else if ( "spart".equals( param.getFilter2() ) )
		{
			list =  dao.spart(param);
		}
		// ����
		else if ( "matnr".equals( param.getFilter2() ) )
		{
			list =  dao.matnr(param);
		}
		// �����ڵ庰 Ŭ����
		else if ( "matnrClass".equals( param.getFilter2() ) )
		{
			list =  dao.matnrClass(param);
		}
		// ����
		else if ( "gtype".equals( param.getFilter2() ) )
		{
			list =  dao.gtype(param);
		}
		// �ܳ�����
		else if ( "zdeli".equals( param.getFilter2() ) )
		{
			list =  dao.zdeli(param);
		}
		// ��������
		else if ( "shangh".equals( param.getFilter2() ) )
		{
			list =  dao.shangh(param);
		}
		// ��ġ����
		else if ( "region".equals( param.getFilter2() ) )
		{
			list =  dao.region(param);
		}
		// ��������
		else if ( "zterm".equals( param.getFilter2() ) )
		{
			list =  dao.zterm(param);
		}
		// ��������
		else if ( "mlbez".equals( param.getFilter2() ) )
		{
			list =  dao.mlbez(param);
		}
		// ���Ư������Ʈ
		else if ( "nature".equals( param.getFilter2() ) )
		{
			param.setFilter1( param.getFilter1().replaceAll(",", " ") );
			list =  dao.nature(param);
		}
		// ���¾�ü
		else if ( "lifnr".equals( param.getFilter2() ) )
		{
			list =  dao.lifnr(param);
		}
		// �븮�� 
		else if ( "kunnr".equals( param.getFilter2() ) )
		{
			list =  dao.kunnr(param);
		}
		// ���¾�ü & �븮�� ����
		else if ( "lifnrKunnr".equals( param.getFilter2() ) )
		{
			list =  dao.lifnrKunnr(param);
		}
		// ��������(�ǸŹ�������)
		else if ( "auart".equals( param.getFilter2() ) )
		{
			list =  dao.auart(param);
		}
		// �μ�
		else if ( "depart".equals( param.getFilter2() ) )
		{
			list =  dao.depart(param);
		}
		// �Ǳ���
		else if ( "rtype".equals( param.getFilter2() ) )
		{
			list =  dao.rtype(param);
		}
		// ���ְ��
		else if ( "sorlt".equals( param.getFilter2() ) )
		{
			String[] gubun = param.getFilter1().split(",");
			param.setFilter1(gubun[0]);
			param.setFilter3(gubun[1]);
			list =  dao.sorlt(param);
		} 
		// zlcode
		else if ( "zlcode".equals( param.getPopFlag() ) )
		{
			list =  dao.zlcode(param);
		}
		// �źλ��� 
		else if ( "abgru".equals( param.getFilter2() ) )
		{
			list =  dao.abgru(param);
		}		
		
		return list;
	}  

	// �� ��ȸ
	public List<Com0060ParamVO> teamCd(Com0060ParamVO param) {
		return dao.teamCd(param);
	}

	// �ڵ� �귣�� ���͸� ����
	public List<Com0060ParamVO> brndfind( Com0060ParamVO param ) {
		
		List<Com0060ParamVO> list = null;
		// �귣�� ���Ư������Ʈ
		list =  dao.brndfind(param);
		return list;
	}  
	
}
