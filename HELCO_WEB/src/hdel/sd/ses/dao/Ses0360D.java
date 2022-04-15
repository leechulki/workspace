package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0360;
import hdel.sd.ses.domain.Ses0360ParamVO;

import java.util.List;

public interface Ses0360D {  
	
	public List<Ses0360> selectList(Ses0360ParamVO param);	
	public List<Ses0360> selectList2(Ses0360ParamVO param);
	public void insertExcel(Ses0360 param);
	public void insertExcel2(Ses0360 param);
	public void deleteExcel(Ses0360 param);
	public void deleteExcel2(Ses0360 param);
	
	// 2013.02.21 첨부파일 기능 추가 - 견적원가등록
	public List<Ses0360> selectListFile(Ses0360ParamVO param);
	public void insertFile(Ses0360 param);
	public void updateFile(Ses0360 param);
	public void deleteFile(Ses0360 param);
	
	// 2013.02.21 첨부파일 기능 추가 - 수주원가등록
	public List<Ses0360> selectListFile2(Ses0360ParamVO param);
	public void insertFile2(Ses0360 param);
	public void updateFile2(Ses0360 param);
	public void deleteFile2(Ses0360 param);
}
