package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0240;
import hdel.sd.ses.domain.Ses0240ParamVO;

import java.util.List;

public interface Ses0240D {  
	
	public List<Ses0240> selectListHeader(Ses0240ParamVO param);
	public List<Ses0240> selectListDetail(Ses0240ParamVO param);
	public List<Ses0240> selectListFile(Ses0240ParamVO param);
	public int findCountQtnum(Ses0240 param);
	public List<Ses0240> findMaxDocReqNo(Ses0240 param);
	public List<Ses0240> findMaxSeq(Ses0240 param);
	

	// 도면의뢰header 등록
	public void insertHeader(Ses0240 param);

	// 도면의뢰detail 등록
	public void insertDetail(Ses0240 param);
	
	// 도면의뢰 추가
	public void addDetail(Ses0240 param);
	
	public void deleteHeader(Ses0240 param);
	public void deleteDetail(Ses0240 param);
	
	public void updateHeader(Ses0240 param);
	public void updateDetail(Ses0240 param);
	public void statusUpdate(Ses0240 param);
	
	// 파일업로드
	public void fileUpload(Ses0240 param);
	public void fileDelete(Ses0240 param);

}
