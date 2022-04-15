package hdel.sd.com.service;

import java.math.BigDecimal;
import java.util.List;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.exception.BizException;
import hdel.lib.exception.RequireException;
import hdel.lib.service.SrmService;
import hdel.sd.com.dao.ExchangeD;
import hdel.sd.com.domain.ExchangeVO;
import tit.util.DatasetUtility;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobesoft.platform.data.Dataset;

@Service
public class ExchangeS extends SrmService {
		
	private ExchangeD dao;

	@Override
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(ExchangeD.class);
	}

	public String getExchangeRate(String Mandt, String Kurst, String Basedt, String Fcurr, String Tcurr) throws Exception{
		
		String exchangeR = "";
		ExchangeVO exchangeRate = null;
		ExchangeVO paramExchangeVO = new ExchangeVO();
		paramExchangeVO.setMandt(Mandt);
		paramExchangeVO.setKurst(Kurst);
		paramExchangeVO.setBasedt(Basedt);
		paramExchangeVO.setFcurr(Fcurr);
		paramExchangeVO.setTcurr(Tcurr);
		
		exchangeRate = searchExchangeRate(paramExchangeVO);
		
		exchangeR = exchangeRate.getUkurs();
		
		if (exchangeR == null || "".equals(exchangeR.trim())) {
			String error = "CE10007";
			throw new RequireException(error);
		}else	{
			if (Double.parseDouble(exchangeR) <= 0)	{
				String error = "CE10007";
				throw new RequireException(error);
			}							
		}
			 
		return exchangeR;		
	}
	
	public ExchangeVO searchExchangeRate(ExchangeVO param) throws Exception{
		try	{
					
			ExchangeVO exchangeRate = null;
			
			if ("Q".equals(param.getKurst()))	{			 
				exchangeRate = dao.selectQtExchange(param);		// 견적환율 - 견적일자
			}else	{
				exchangeRate = dao.selectSoExchange(param);		// 수주환율 - 수주일자
			}
						
			if (exchangeRate == null)	{
				String error = "CE10007";
				throw new RequireException(error);
			}
			return exchangeRate;
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public Dataset searchCostRegiExRate(MipParameterVO paramVO) throws RuntimeException, Exception {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsExchange = paramVO.getDataset("ds_exchange");
		
		try {		
			
			String mandt = paramVO.getVariable("G_MANDT");	                // SAP CLIENT
			String qtnum = DatasetUtility.getString(dsCond,"QTNUM");
			String qtser = DatasetUtility.getString(dsCond,"QTSER");
			String pspid = DatasetUtility.getString(dsCond,"PSPID");
			String seq = DatasetUtility.getString(dsCond,"SEQ");
			String kurst = DatasetUtility.getString(dsCond,"KURST");
            
			ExchangeVO CurBaseinfo = null;
			if ("Q".equals( kurst ) ) {
				CurBaseinfo = dao.selectCurrencyQt(mandt, qtnum, qtser);		// 견적일자, 견적통화 쿼리
			} else {
				CurBaseinfo = dao.selectCurrencySo(mandt, pspid, seq);		// 수주일자, 수주통화 쿼리
			}
			
			String waerkCon = CurBaseinfo.getWaerk();
			String auart = CurBaseinfo.getAuart();
			String gdatu = CurBaseinfo.getGdatu();
			String exchangeBas = null;				//기준통화 
			String exchangeCon = null;				//계약통화

			String waerkBas = dao.searchWaerkBase(mandt, gdatu); 
			
			if (waerkBas != waerkCon && "ZT".equals( auart.substring(0, 2))) {
				exchangeBas = getExchangeRate(mandt, kurst, gdatu, waerkBas, "KRW");		// 기준통화 -> 원화 
				if ("KRW".equals(waerkCon)){
					exchangeCon = "1";
				} else {
					exchangeCon = getExchangeRate(mandt, kurst, gdatu, waerkCon, "KRW");		// 계약통화 -> 원화
				}
			}
			
			dsExchange.deleteAll();
			dsExchange.appendRow();
			dsExchange.setColumn(0, "MANDT", mandt);
			dsExchange.setColumn(0, "KURST", kurst);
			dsExchange.setColumn(0, "FCURR", waerkBas);
			dsExchange.setColumn(0, "TCURR", waerkCon);
			dsExchange.setColumn(0, "GDATU", gdatu);
			if (Double.parseDouble(exchangeBas) > 0 && Double.parseDouble(exchangeCon) > 0) {
				
				BigDecimal exchangeBas_dec = new BigDecimal(exchangeBas);
				BigDecimal exchangeCon_dec = new BigDecimal(exchangeCon);
				BigDecimal exchangeR_dec = exchangeCon_dec.divide(exchangeBas_dec, 4, BigDecimal.ROUND_HALF_UP);
				dsExchange.setColumn(0, "UKURS",exchangeR_dec.toString());
				
			}		
		
		} catch( Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return dsExchange;
	}	

}
