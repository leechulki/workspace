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
		
		// 국가
		if ( "nation".equals( param.getFilter2() ) )
		{
			list =  dao.nation(param);
		}
		// 운송지역
		else if ( "area".equals( param.getFilter2() ) )
		{
			list =  dao.area(param);
		}
		// 영업사원
		else if ( "zkunnr".equals( param.getFilter2() ) )
		{
			list =  dao.zkunnr(param);
		}
		// 제품
		else if ( "spart".equals( param.getFilter2() ) )
		{
			list =  dao.spart(param);
		}
		// 자재
		else if ( "matnr".equals( param.getFilter2() ) )
		{
			list =  dao.matnr(param);
		}
		// 자재코드별 클래스
		else if ( "matnrClass".equals( param.getFilter2() ) )
		{
			list =  dao.matnrClass(param);
		}
		// 기종
		else if ( "gtype".equals( param.getFilter2() ) )
		{
			list =  dao.gtype(param);
		}
		// 단납구분
		else if ( "zdeli".equals( param.getFilter2() ) )
		{
			list =  dao.zdeli(param);
		}
		// 국내상해
		else if ( "shangh".equals( param.getFilter2() ) )
		{
			list =  dao.shangh(param);
		}
		// 설치지역
		else if ( "region".equals( param.getFilter2() ) )
		{
			list =  dao.region(param);
		}
		// 지급조건
		else if ( "zterm".equals( param.getFilter2() ) )
		{
			list =  dao.zterm(param);
		}
		// 종별구분
		else if ( "mlbez".equals( param.getFilter2() ) )
		{
			list =  dao.mlbez(param);
		}
		// 사양특성리스트
		else if ( "nature".equals( param.getFilter2() ) )
		{
			param.setFilter1( param.getFilter1().replaceAll(",", " ") );
			list =  dao.nature(param);
		}
		// 협력업체
		else if ( "lifnr".equals( param.getFilter2() ) )
		{
			list =  dao.lifnr(param);
		}
		// 대리점 
		else if ( "kunnr".equals( param.getFilter2() ) )
		{
			list =  dao.kunnr(param);
		}
		// 협력업체 & 대리점 통합
		else if ( "lifnrKunnr".equals( param.getFilter2() ) )
		{
			list =  dao.lifnrKunnr(param);
		}
		// 오더유형(판매문서유형)
		else if ( "auart".equals( param.getFilter2() ) )
		{
			list =  dao.auart(param);
		}
		// 부서
		else if ( "depart".equals( param.getFilter2() ) )
		{
			list =  dao.depart(param);
		}
		// 실기종
		else if ( "rtype".equals( param.getFilter2() ) )
		{
			list =  dao.rtype(param);
		}
		// 수주결과
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
		// 거부사유 
		else if ( "abgru".equals( param.getFilter2() ) )
		{
			list =  dao.abgru(param);
		}		
		
		return list;
	}  

	// 팀 조회
	public List<Com0060ParamVO> teamCd(Com0060ParamVO param) {
		return dao.teamCd(param);
	}

	// 코드 브랜드 필터링 적용
	public List<Com0060ParamVO> brndfind( Com0060ParamVO param ) {
		
		List<Com0060ParamVO> list = null;
		// 브랜드 사양특성리스트
		list =  dao.brndfind(param);
		return list;
	}  
	
}
