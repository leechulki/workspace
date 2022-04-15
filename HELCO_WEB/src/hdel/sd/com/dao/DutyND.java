package hdel.sd.com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.Datum;
import com.sap.domain.Posnr;
import com.sap.domain.Vbeln;

import hdel.sd.com.domain.Duty;
import hdel.sd.com.domain.DutyDet;

public interface DutyND {
	public List<Duty> selectDuty(@Param("mandt") String mandt, @Param("gtype") String gtype, @Param("anyType") Boolean anyType, @Param("blockgbn") String blockgbn, @Param("zflg") String zflg, @Param("lstExcpBlockNo") List<String> lstExcpBlockNo, @Param("lang") String lang);
	public List<Duty> selectDuty(@Param("mandt") String mandt, @Param("gtype") String gtype, @Param("anyType") Boolean anyType, @Param("blockgbn") String blockgbn, @Param("zflg") String zflg, @Param("lstExcpBlockNo") List<String> lstExcpBlockNo);
	public List<DutyDet> selectDutydList(@Param("mandt") String mandt, @Param("gtype")  String gtype, @Param("anyType") Boolean anyType, @Param("blockgbn")  String blockgbn, @Param("zflg")  String zflg, @Param("lstExcpBlockNo") List<String> lstExcpBlockNo);
	public Map<?, ?> selectError(@Param("mandt") String mandt, @Param("class") String cls, @Param("atnam") String atnam, @Param("lang") String lang);
	public Datum getIldat(@Param("mandt") String mandt, @Param("vbeln") Vbeln vbeln, @Param("posnr") Posnr posnr);
}