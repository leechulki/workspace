package hdel.sd.ses.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.ses.domain.Ses0030;
import hdel.sd.ses.domain.Ses0030ParamVO;


public interface Ses0030ED {
	
	public List<Ses0030> selectEgisListHeader(Ses0030ParamVO param);
	public Map selectQtnumZsdt1164(Ses0030ParamVO param);
	public int insertSmlZsdt1046(Ses0030ParamVO param);
	public int insertSmlZsdt1091(Ses0030ParamVO param);
	public int insertSmlZsdt1047(Ses0030ParamVO param);
	public int insertSmlZsdt1093(Ses0030ParamVO param);
	public int insertSmlZsdt1048(Ses0030ParamVO param);
	public int insertSmlZcobt309(Ses0030ParamVO param);
	public int insertSmlZcobt302(Ses0030ParamVO param);
	public int insertSmlZcobt202(Ses0030ParamVO param);
	public int insertSmlZsdt1054D(Ses0030ParamVO param);
	public int insertSmlZsdt1050(Ses0030ParamVO param);
	public int insertSmlZsdt0713(Ses0030ParamVO param);
	public int insertSmlZsdt0712(Ses0030ParamVO param);
	public int insertSmlZsdt0711(Ses0030ParamVO param);
	public int insertSmlZsdt1054H(Ses0030ParamVO param);
	public int insertSmlZSDT1164(Ses0030ParamVO param);
	
	
	public int deleteSmlZsdt1164(Ses0030ParamVO param);
	public int deleteSmlZsdt1046(Ses0030ParamVO param);
	public int deleteSmlZsdt1091(Ses0030ParamVO param);
	public int deleteSmlZsdt1047(Ses0030ParamVO param);
	public int deleteSmlZsdt1093(Ses0030ParamVO param);
	public int deleteSmlZsdt1048(Ses0030ParamVO param);
	public int deleteSmlZcobt309(Ses0030ParamVO param);
	public int deleteSmlZcobt302(Ses0030ParamVO param);
	public int deleteSmlZcobt202(Ses0030ParamVO param);
	public int deleteSmlZsdt1054D(Ses0030ParamVO param);
	public int deleteSmlZsdt1050(Ses0030ParamVO param);
	public int deleteSmlZsdt0713(Ses0030ParamVO param);
	public int deleteSmlZsdt0712(Ses0030ParamVO param);
	public int deleteSmlZsdt0711(Ses0030ParamVO param);
	public int deleteSmlZsdt1054H(Ses0030ParamVO param);
}
