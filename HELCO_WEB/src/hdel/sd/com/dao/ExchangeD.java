package hdel.sd.com.dao;

import org.apache.ibatis.annotations.Param;

import hdel.sd.com.domain.ExchangeVO;

public interface ExchangeD {

	public ExchangeVO selectQtExchange(ExchangeVO param);		// ����ȯ�� - �������� ����(ZTCURR)
	public ExchangeVO selectSoExchange(ExchangeVO param);		// ����ȯ�� - ���ֻ������� �Ǵ� ���� ���� �Ÿű��� ȯ��(TCURR)
	
	public ExchangeVO  selectCurrencyQt(@Param("mandt") String sMandt, @Param("qtnum") String sQtnum, @Param("qtser") String sQtser);
	public ExchangeVO  selectCurrencySo(@Param("mandt") String sMandt, @Param("pspid") String sPspid, @Param("seq") String sSeq);		
	public String searchWaerkBase(@Param("mandt") String sMandt, @Param("basedt") String sBasedt);	
}
