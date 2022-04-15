package hdel.sd.com.dao;

import org.apache.ibatis.annotations.Param;

import hdel.sd.com.domain.ExchangeVO;

public interface ExchangeD {

	public ExchangeVO selectQtExchange(ExchangeVO param);		// 견적환율 - 견적일자 기준(ZTCURR)
	public ExchangeVO selectSoExchange(ExchangeVO param);		// 수주환율 - 수주생성일자 또는 수주 일자 매매기준 환율(TCURR)
	
	public ExchangeVO  selectCurrencyQt(@Param("mandt") String sMandt, @Param("qtnum") String sQtnum, @Param("qtser") String sQtser);
	public ExchangeVO  selectCurrencySo(@Param("mandt") String sMandt, @Param("pspid") String sPspid, @Param("seq") String sSeq);		
	public String searchWaerkBase(@Param("mandt") String sMandt, @Param("basedt") String sBasedt);	
}
