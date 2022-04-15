package hdel.sd.ses.dao;

import hdel.sd.ses.domain.Ses0330;
import hdel.sd.ses.domain.Ses0330ParamVO;

import java.util.List;

public interface Ses0330D {  
	
	public List<Ses0330> selectListHeader(Ses0330ParamVO param);
	public List<Ses0330> selectListDetail(Ses0330ParamVO param);
	public List<Ses0330> searchAttribute(Ses0330ParamVO param);
	public List<Ses0330> selectHdrDtlList(Ses0330ParamVO param);
	public List<Ses0330> checkVbak(Ses0330ParamVO param);
	
	public List<Ses0330> getSeq(Ses0330ParamVO param);
	
	public void updateDetail(Ses0330 param);
	public void sendMailheader(Ses0330 param);
	public void updateHeader(Ses0330 param);
	public void insertVblen(Ses0330ParamVO param);
	public void insertVblenDetail(Ses0330ParamVO param);
	public void insertSap(Ses0330 param);

}
