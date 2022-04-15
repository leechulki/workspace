package hdel.sd.ses.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.ses.domain.Ses0403;
import hdel.sd.ses.domain.Ses0403ParamVO;

public interface Ses0403D {  
	public List<Ses0403> selectListDetail(Ses0403ParamVO param);
	public List<Ses0403> existing(Ses0403 entity);
	public List<Ses0403> getCadVendorAbbr(Ses0403 entity);
	public void insertEntity(Ses0403 entity);
	public void updateEntity(Ses0403 entity);
	public List<Ses0403> sprtExisting(Ses0403 sprtEntity);
	public Map<String, Object> selectSprtCs(Ses0403 paramSet);
	public Map<String, Object> selectLifnr(Ses0403 paramSet);
	public Map<String, Object> selectZsdt0198(Ses0403 param);
	public void insertSeparate(Ses0403 ses0403Entity);
	public void updateSeparate(Ses0403 ses0403Entity);
	public void deleteSeparate(Ses0403 ses0403Entity);
}
