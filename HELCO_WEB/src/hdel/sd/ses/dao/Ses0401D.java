package hdel.sd.ses.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.sap.domain.Vbeln;

import hdel.lib.domain.ZSDT1084;
import hdel.sd.ses.domain.Ses0401;
import hdel.sd.ses.domain.Ses0401ParamVO;
import hdel.sd.ses.domain.ZSDT1079;

public interface Ses0401D {

	public List<Ses0401> selectListHeader(Ses0401ParamVO param);
	public List<Ses0401> selectMaxZRqSeq(Ses0401 param);
	public List<Ses0401> selectListFile(Ses0401ParamVO param);
	public List<ZSDT1079> searchChangedCharPart(Ses0401ParamVO param);
	
	public List<Ses0401> selectQtnumPrint(Ses0401ParamVO param);
	public List<Ses0401> selectProjectPrint(Ses0401ParamVO param);

	public List<Ses0401> selectQtnumTemplate(Ses0401ParamVO param);
	public List<Ses0401> selectZkunnr_Z3(Ses0401 param);
	public List<Ses0401> selectStat(Ses0401 param);
	
	public List<Ses0401> selectQtseq(Ses0401 param);
	public ZSDT1084 searchLastValidChgContract(@Param("mandt") String mandt, @Param("qtnum") String qtnum, @Param("qtser") String qtser);

	public void insertHeader(Ses0401 param);
	public void updateHeader(Ses0401 param);
	public void updateZrqstat(Ses0401 param);
	public void updateApproval(Ses0401 param);
	public void updateOutdate(Ses0401 param);
	public void updateOutfinish(Ses0401 param);
	public void updateOutcandt(Ses0401 param);
	public void updateOutactdt(Ses0401 param);
	public void updateOutretdt(Ses0401 param);
	
	public void deleteFile(Ses0401 param);
	public void insertFile(Ses0401 param);
	public void updateFile(Ses0401 param);
	
	public void deleteHeader(Ses0401 param);
	public void deleteFile2(Ses0401 param);
	
	public void updateZ3all(Ses0401 param);
	public void UpdateErrortext(@Param("mandt") String mandt, @Param("zrqseq") String zrqseq, @Param("error_text") String error_text) throws DataAccessException;
	
	public List<Ses0401> findUsercode(Ses0401ParamVO param);
}