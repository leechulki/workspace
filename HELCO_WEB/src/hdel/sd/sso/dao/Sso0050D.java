package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0050ParamVO;
import hdel.sd.sso.domain.Sso0050VO;

import java.util.List;

/**
 * 수주변경
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sso0050D { 

	public List<Sso0050VO> SelectSayang(Sso0050ParamVO param);		// 수주
	public List<Sso0050VO> SelectVbeln(Sso0050ParamVO param);		// 견적
	public List<Sso0050VO> SelectSayangForPrint(Sso0050ParamVO param);
	public void setCostState(Sso0050ParamVO param); // 원가의뢰
	public List<Sso0050VO> searchZcobt304(Sso0050ParamVO param);
	public List<Sso0050VO> searchZcobt304D(Sso0050ParamVO param);
	public List<Sso0050VO> selectListBlockName(Sso0050ParamVO param);
	
	/*	2018.03.06 - 환율조회 공통 모듈화 (com.ExchangeS.class , selectSoExchange)
	public List<Sso0050VO> selectListExchange(Sso0050ParamVO param); // 매매기준환율 조회 2013.02.20
	*/
	
	public List<Sso0050VO> searchZcobt204D(Sso0050ParamVO param);
	public List<Sso0050VO> selectListBlockNameD(Sso0050ParamVO param);
} 
