package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.domain.Ses0031;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface Ses0031D {  
	
	public List<Ses0031> selectListGtype(Ses0031ParamVO param);
	public List<Ses0031> selectListSO(Ses0031ParamVO param);
	public List<Ses0031> selectListQtser(Ses0031ParamVO param);
	public List<Ses0031> selectListTemplate(Ses0031ParamVO param);
	public List<Ses0031> selectListKsml(Ses0031ParamVO param);
	public List<Ses0031> selectListSpec(Ses0031ParamVO param);
	public List<Ses0031> selectListZcobt302(Ses0031ParamVO param);
	public List<Ses0031> selectListZcobt309(Ses0031ParamVO param);
	public List<Ses0031> selectListZsdt1054D(Ses0031ParamVO param);
	public List<Ses0031> selectListBlockName(Ses0031ParamVO param);
	public List<Ses0031> selectListClass(Ses0031ParamVO param);
	public List<Ses0031> selectListZuam(Ses0031ParamVO param);
	public List<Ses0031> selectZcobt304(Ses0031ParamVO param);
	public List<Ses0031> selectListAcapa(Ses0031ParamVO param);
	public List<Ses0031> selectListExchange(Ses0031ParamVO param);
	public int mergeHeader(Ses0031ParamVO param);
	public int deleteHeader(Ses0031ParamVO param);
	public int addHeader(Ses0031ParamVO param);
	public int insertQtserZsdt1046(Ses0031ParamVO param);
	public int updateHeader(Ses0031ParamVO param);
	public int mergeDetail(Ses0031ParamVO param);
	public int deleteDetail(Ses0031ParamVO param);
	public int deleteQtserZsdt1047(Ses0031ParamVO param);
	public int addDetail(Ses0031ParamVO param);
	public int insertQtserZsdt1047(Ses0031ParamVO param);
	public int updateDetail(Ses0031ParamVO param);
	public int mergeTemplate(Ses0031ParamVO param);
	public int deleteTemplate(Ses0031ParamVO param);
	public int deleteQtserZsdt1048(Ses0031ParamVO param);
	public int addTemplate(Ses0031ParamVO param);
	public int insertQtserZsdt1048(Ses0031ParamVO param);
	public int mergeZsdt1054H(Ses0031ParamVO param);
	public int mergeZsdt1054D(Ses0031ParamVO param);
	public int deleteZsdt1054H(Ses0031ParamVO param);
	public int deleteZsdt1054D(Ses0031ParamVO param);
	public int deleteZsdt1050(Ses0031ParamVO param);
	public int updateZcobt202(Ses0031ParamVO param);
	public int deleteZcobt202(Ses0031ParamVO param);
	public int updateZcobt204(Ses0031ParamVO param);
	public int updateZcobt302(Ses0031ParamVO param);
	public int deleteZcobt302(Ses0031ParamVO param);
	public int updateZcobt304(Ses0031ParamVO param);
	public int updateZcobt309(Ses0031ParamVO param);
	public int deleteZcobt309(Ses0031ParamVO param);
	public int updateSorlt(Ses0031ParamVO param);
	public int updateHeaderFlag(Ses0031ParamVO param);
	public int updateDwgFlag(Ses0031ParamVO param);
	public int updateInitCostZSDT1047(Ses0031ParamVO param);
	public int updateInitCostZSDT1046(Ses0031ParamVO param);
	public int updateZsdt0090(Ses0031ParamVO param);
	public int updateZsdt0091(Ses0031ParamVO param);
	public int selectSonnrCount(@Param("mandt") String sMandt, @Param("qtnum") String sQtnum, @Param("sonnr") String sSonnr);
	public int updateQtserZsdt1001(@Param("mandt") String sMandt, @Param("sonnr") String sSonnr, @Param("sorlt") String sSorlt);
	
/* 2018.03.06 - 환율조회 공통 모듈화 (com.ExchangeS.getExchangeRate)	
	public Ses0031 selectNewExchange(@Param("mandt") String sMandt, @Param("date") String sDate);
*/
	public Ses0031 selectKunnr(@Param("mandt") String sMandt, @Param("kunnr") String sKunnr);
	public int updateZsdt0090Status(Ses0031ParamVO param);
	public int deleteZsdt0713(Ses0031ParamVO param);
	public int deleteZsdt0712(Ses0031ParamVO param);
	public int updateZsdt0711(Ses0031ParamVO param);
	public int updateZcobt304D(Ses0031ParamVO param);
	
	public int deleteZsdt1091(Ses0031ParamVO param);
	public int deleteZsdt1093(Ses0031ParamVO param);
	public int mergeZsdt1091(Ses0031ParamVO param);
	public int mergeZsdt1093(Ses0031ParamVO param);
	public int addZsdt1091(Ses0031ParamVO param);
	public int addZsdt1093(Ses0031ParamVO param);
	public int updateZsdt1091(Ses0031ParamVO param);
	public int updateZsdt1091Lh(Ses0031ParamVO param);

	public int updateCoElqty(Ses0031ParamVO param);

	public Ses0031ParamVO selectEgisFlag(Ses0031ParamVO param);

	public Ses0031ParamVO selectRatio(Ses0031ParamVO param);

	public List<Ses0031> selectListZuam1(Ses0031ParamVO param);

	public Ses0031ParamVO selectEgisData(Ses0031ParamVO param);

	public int updateDetailEgis(Ses0031ParamVO param);
	public List<Map<String, Object>> selectZsdt1072(Ses0031ParamVO param);
	public List<Map<String, Object>> selectZsdt1073(Ses0031ParamVO param);

}
