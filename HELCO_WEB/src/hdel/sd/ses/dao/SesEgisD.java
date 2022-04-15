package hdel.sd.ses.dao;

import hdel.sd.com.domain.ExchangeVO;
import hdel.sd.ses.domain.SesEgis;
import hdel.sd.ses.domain.SesEgisParamVO;

import java.util.List;

public interface SesEgisD {

	public int updateHQPO1046(SesEgisParamVO param);
	
	public int updateHQPO1091(SesEgisParamVO param);
	
	public int updateTradeZSDT1050(SesEgisParamVO param);

	public int updateTradeZSDT1047(SesEgisParamVO param);

	public int updateTradeZSDT1046(SesEgisParamVO param);
	
	public int updateCostZSDT1046(SesEgisParamVO param);
	
	public int updateCostZSDT1047(SesEgisParamVO param);
	
	public int updateZeamZSDT1047(SesEgisParamVO param);

	public int updateZeamZSDT1046(SesEgisParamVO param);

	public List<SesEgis> selectEgisPrice(SesEgisParamVO param);

	public List<SesEgis> selectQtdata(SesEgisParamVO param);

	public ExchangeVO selectQtExchange(ExchangeVO param);				// ����ȯ�� - �������� ����(ZTCURR)

	public SesEgisParamVO selectTradeRatio(SesEgisParamVO param);		// ������

	public SesEgisParamVO selectUrate(SesEgisParamVO param);			// ������

	public List<SesEgis> selectEgisNegoPrice(SesEgisParamVO param);

	public int updateZSDT1050(SesEgisParamVO param);
	
}
