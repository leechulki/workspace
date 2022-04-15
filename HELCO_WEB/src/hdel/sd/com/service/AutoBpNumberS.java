package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.AutoBpNumberD;
import hdel.sd.com.dao.AutoBpNumberBosuD;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.domain.AutoNumberParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class AutoBpNumberS extends SrmService  {
	
	private AutoBpNumberD AutoNumberDao;
	
	private AutoBpNumberBosuD AutoNumberBosuDao;
	
	public void createDao(SqlSession sqlSession) {
		AutoNumberDao = sqlSession.getMapper(AutoBpNumberD.class);
		
		AutoNumberBosuDao = sqlSession.getMapper(AutoBpNumberBosuD.class);
	}

	public List<AutoNumberVO> AutoBpNumber(AutoNumberParamVO param) {

		if (param.getDeptFlag().equals("ZS01")) {				// �����°���
			param.setDeptFlag("A");
		} else if (param.getDeptFlag().equals("ZS02")) {		// �����°���(�븮��)
			param.setDeptFlag("A");
		} else if (param.getDeptFlag().equals("ZN01")) {		// ��ü����
			param.setDeptFlag("N");
		} else if (param.getDeptFlag().equals("ZN02")) {		// ��ü����(�븮��)
			param.setDeptFlag("N");
		} else if (param.getDeptFlag().equals("ZJ01")) {		// ������
			param.setDeptFlag("J");
		} else if (param.getDeptFlag().equals("ZJ02")) {		// ������(�븮��)
			param.setDeptFlag("J");
		} else if (param.getDeptFlag().equals("ZF01")) {		// ����
			param.setDeptFlag("F");
		} else if (param.getDeptFlag().equals("ZG01")) {		// PSD
			param.setDeptFlag("G");
		} else if (param.getDeptFlag().equals("ZE01")) {		//�ؿ��Ϲݼ���
			param.setDeptFlag("E");
		} else if (param.getDeptFlag().equals("ZT01")) {		//�߰蹫��
			param.setDeptFlag("T");
		} else if (param.getDeptFlag().equals("ZC01")) {		// ��ǰ����
			param.setDeptFlag("C");
		} else if (param.getDeptFlag().equals("ZR11")) {		// ���⺸��
			param.setDeptFlag("U");
		} else if (param.getDeptFlag().equals("ZR12")) {		// ��ǰ�Ǹ�
			param.setDeptFlag("B");
		} else if (param.getDeptFlag().equals("ZR13")) {		// ��������
			param.setDeptFlag("S");
		}
		
		System.out.print("\n@@@ param.getDeptFlag().toString()!!!" + param.getDeptFlag().toString() + "\n");
		
		if (param.getDeptFlag().toString() == "U" || param.getDeptFlag().toString() == "B" ||param.getDeptFlag().toString() == "S") {
			System.out.print("\n@@@ AutoNumberBosuDao.AutoBpNumberBosu!!!" + "\n");
			return AutoNumberBosuDao.AutoBpNumberBosu(param);
		} else {
			System.out.print("\n@@@ AutoNumberDao.AutoBpNumber!!!" + "\n");
			return AutoNumberDao.AutoBpNumber(param);
		}
		
	}
}