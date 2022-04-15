package hdel.sd.com.dao;

import hdel.sd.com.domain.Com0060ParamVO;

import java.util.List;

public interface Com0060D {  
	
	// 코드정보 조회
	public List<Com0060ParamVO> nation(Com0060ParamVO param);		// 국가
	public List<Com0060ParamVO> area(Com0060ParamVO param);			// 운송지역
	public List<Com0060ParamVO> zkunnr(Com0060ParamVO param);		// 영업사원
	public List<Com0060ParamVO> spart(Com0060ParamVO param);		// 제품
	public List<Com0060ParamVO> matnr(Com0060ParamVO param);		// 자재
	public List<Com0060ParamVO> matnrClass(Com0060ParamVO param);	// 자재코드별 클래스
	public List<Com0060ParamVO> gtype(Com0060ParamVO param);		// 기종
	public List<Com0060ParamVO> zdeli(Com0060ParamVO param);		// 단납구분
	public List<Com0060ParamVO> shangh(Com0060ParamVO param);		// 국내상해
	public List<Com0060ParamVO> region(Com0060ParamVO param);		// 설치지역
	public List<Com0060ParamVO> zterm(Com0060ParamVO param);		// 지급조건
	public List<Com0060ParamVO> mlbez(Com0060ParamVO param);		// 종별구분
	public List<Com0060ParamVO> nature(Com0060ParamVO param);		// 사양특성리스트
	public List<Com0060ParamVO> brndfind(Com0060ParamVO param);		// 브랜드사양특성리스트
	public List<Com0060ParamVO> lifnr(Com0060ParamVO param);		// 협력업체
	public List<Com0060ParamVO> kunnr(Com0060ParamVO param);		// 대리점
	public List<Com0060ParamVO> lifnrKunnr(Com0060ParamVO param);	// 협력업체 & 대리점 
	public List<Com0060ParamVO> auart(Com0060ParamVO param);		// 오더유형(판매문서유형)
	public List<Com0060ParamVO> depart(Com0060ParamVO param);		// 부서
	public List<Com0060ParamVO> rtype(Com0060ParamVO param);		// 실기종
	public List<Com0060ParamVO> sorlt(Com0060ParamVO param);		// 수주결과
	public List<Com0060ParamVO> zlcode(Com0060ParamVO param);		// zlcode
	public List<Com0060ParamVO> teamCd(Com0060ParamVO param);		// 팀조회
	public List<Com0060ParamVO> abgru(Com0060ParamVO param);		// 거부사유
}
