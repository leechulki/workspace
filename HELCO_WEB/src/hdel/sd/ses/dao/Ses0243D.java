package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0243;
import hdel.sd.ses.domain.Ses0243ParamVO;

import java.util.List;

public interface Ses0243D {  
	
	public List<Ses0243> selectListHeader(Ses0243ParamVO param);
	public List<Ses0243> selectListDetail(Ses0243ParamVO param);
	public List<Ses0243> searchAttribute(Ses0243ParamVO param);
	public List<Ses0243> selectListHogi(Ses0243ParamVO param);
	public List<Ses0243> checkVbak(Ses0243ParamVO param);
	
	public List<Ses0243> getSeq(Ses0243ParamVO param);
	
	public void updateDetail(Ses0243 param);
	public void sendMailheader(Ses0243 param);
	public void updateHeader(Ses0243 param);
	public void updateHeaderReceiptDate(Ses0243 param);
	public void insertVblen(Ses0243ParamVO param);
	public void insertVblenDetail(Ses0243ParamVO param);
	public void insertSap(Ses0243 param);

}
