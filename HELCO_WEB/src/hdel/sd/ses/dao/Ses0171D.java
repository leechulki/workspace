package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0171;
import hdel.sd.ses.domain.Ses0171ParamVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface Ses0171D {  
	
	public List<Ses0171> selectFormHeader(Ses0171ParamVO param);
	
	public List<Ses0171> selectFormDetail(Ses0171ParamVO param);

	public List<Ses0171> selectList(Ses0171ParamVO param);
	
	public List<Ses0171> selectListDetail(Ses0171ParamVO param);
	
	public List<Ses0171> selectSumCostZSDT1047(Ses0171ParamVO param);

	// 원가의뢰요청 접수처리
	public void updateZSDT1054(Ses0171 param);

	// 원가의뢰요청 접수 취소처리
	public void insertZSDT1050(Ses0171 param);
	
	public void deleteZSDT1050(Ses0171 param);
	
	public void updateFlagConfirmZSDT1046(Ses0171 param);
	
	public void updateFlagCancelZSDT1046(Ses0171 param);
	
	public void updateCostZSDT1047(Ses0171 param);
	
	public void updateCostZSDT1046(Ses0171 param);
	
	public Ses0171 selectCurrencyQt(@Param("mandt") String sMandt, @Param("qtnum") String sQtnum, @Param("qtser") String sQtser);
	
	public Ses0171 selectCurrencySo(@Param("mandt") String sMandt, @Param("pspid") String sPspid, @Param("seq") String sSeq);	
	
	public String searchWaerkBase(@Param("mandt") String sMandt, @Param("basedt") String sBasedt);
	
}
