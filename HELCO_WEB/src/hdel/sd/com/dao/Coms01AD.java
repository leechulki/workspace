package hdel.sd.com.dao;

import java.util.List;
import java.util.Map;

import hdel.sd.com.domain.Coms01A;
import hdel.sd.sbi.domain.ZSDT1143;
import hdel.sd.com.domain.BrndSayang;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;

public interface Coms01AD {
	public List<Coms01A> selectBrandGtypeList(Coms01A param);
	
	public String selectBrandFlag(Coms01A param);
	
	// 2020�� �귣�� ������� Ư���ڵ� �޺� - vkbur
	public List<ComboVO> selectAtwrt(ComboParamVO param);

	// 2020�� �귣�� ������� Ư����
	public List<BrndSayang> selectsSyangPrd(Map<String, String> param);

	/** 
	 * �ұ׷� Ÿ��Ʋ ��ȸ
	 * @param ZPRDGBN(��ǰ����)
	 * @return List<ZSDT1143>
	 * @throws Exception
	 */
	public List<ZSDT1143> selectGroupTitList(Map<String, Object> param) throws Exception;
	
	public String selectMinCdateQt(Coms01A param);
	
}