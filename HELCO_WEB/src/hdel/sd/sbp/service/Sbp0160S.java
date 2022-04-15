package hdel.sd.sbp.service;


import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.com.service.AutoSoNumberS;
import hdel.sd.sbp.dao.Sbp0160D;
import hdel.sd.sbp.domain.ComboParamVO;
import hdel.sd.sbp.domain.ComboVO;
import hdel.sd.sbp.domain.Sbp0160;
import hdel.sd.sbp.domain.Sbp0160ParamVO;
import hdel.sd.sbp.domain.Sbp0161;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

@Service
public class Sbp0160S extends SrmService {

	
	@Autowired
	private AutoSoNumberS autoSoService;

	@Autowired
	private Sbp0161S sbp0161Service;
	
	private Sbp0160D sbp0160D;

	@Override
	public void createDao(SqlSession sqlSession) {
		sbp0160D = sqlSession.getMapper(Sbp0160D.class);
	}

	// ���ְ��ɼ�
	public List<ComboVO> selectDD07T(ComboParamVO param) {
		return sbp0160D.selectDD07T(param);
	}

	// ����
	public List<ComboVO> selectGtype() {
		return sbp0160D.selectGtype();
	}

	// ��ġ����
	public List<ComboVO> selectREGION(ComboParamVO param) {
		return sbp0160D.selectREGION(param);
	}
	
	public List<Sbp0160> selectZSDT1001(Sbp0160ParamVO param) {
		return sbp0160D.selectZSDT1001(param);
	}
	
	public List<Sbp0160> selectZSDT1001E(Sbp0160ParamVO param) {
		return sbp0160D.selectZSDT1001E(param);
	}

	// ��������� ���ø� ���� ��ȸ (���ø���ȣ�� ��ȸ)
	public List<Sbp0160> selectListTempletInfo(Sbp0160ParamVO param) {
		return sbp0160D.selectListTempletInfo(param);
	}

	// �������������� �����Ѵ�.
	public void saveCost(Sbp0160 param) {
		sbp0160D.updateCostZSDT1001(param);
	}

}
