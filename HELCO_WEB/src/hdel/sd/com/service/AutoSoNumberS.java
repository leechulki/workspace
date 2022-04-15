package hdel.sd.com.service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.AutoSoNumberD;
import hdel.sd.com.dao.AutoSoNumberBosuD;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.domain.AutoNumberParamVO;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class AutoSoNumberS extends SrmService  {
	
	private AutoSoNumberD AutoNumberDao;
//	private AutoSoNumberBosuD AutoNumberBosuDao;
	
	public void createDao(SqlSession sqlSession) {
		
		AutoNumberDao = sqlSession.getMapper(AutoSoNumberD.class);
//		AutoNumberBosuDao = sqlSession.getMapper(AutoSoNumberBosuD.class);
		
	}

	public List<AutoNumberVO> AutoSoNumber(AutoNumberParamVO param) {

		System.out.print("\n@@@ AutoNumberDao.AutoSoNumber Start!!!" + "\n");
		
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

//		if (param.getDeptFlag().toString() == "U" || param.getDeptFlag().toString() == "B" ||param.getDeptFlag().toString() == "S") {
//			return AutoNumberBosuDao.AutoSoNumberBosu(param);
//		} else {
//			return AutoNumberDao.AutoSoNumber(param);
//		}
		
		// ��ȣ ���翩�� Ȯ��
		Integer count = AutoNumberDao.selectExist(param.getMandt(), param.getDeptFlag(), param.getSoQtFlag());
		long zno = 0;
		if (0 >= count) {
			zno = 1;
			
			AutoNumberDao.insertNo(param.getMandt(), param.getDeptFlag(), param.getSoQtFlag(), zno);
		} else {
			Long curzno = AutoNumberDao.selectNo(param.getMandt(), param.getDeptFlag(), param.getSoQtFlag());
			zno = curzno + 1;
			
			AutoNumberDao.updateNo(param.getMandt(), param.getDeptFlag(), param.getSoQtFlag(), zno);
		}
		
		String number = param.getDeptFlag() + param.getSoQtFlag() + String.format("%08d", zno);
		
		AutoNumberVO vo = new AutoNumberVO();
		vo.setNumber(number);
		
		List list = new ArrayList();
		list.add(vo);
		
		return list;
	}
}