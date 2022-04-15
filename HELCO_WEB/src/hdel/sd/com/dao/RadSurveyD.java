package hdel.sd.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hdel.sd.com.domain.FloorNmVO;
import hdel.sd.com.domain.SuvFloorVO;
import hdel.sd.com.domain.SuvPrhVO;

/**
 * ���𵨸��� �������� ���� Dao
 * 
 * @author  �ڼ���
 * @since 2021.01.26
 * @version 1.0
 * @see 
 * <pre>
 *  == �����̷�(Modification Information) ==
 *   
 *          ������          ������           ��������
 *  ----------------    ------------    ---------------------------
 *   2021.01.26         �ڼ���          ���� ����
 * 
 * </pre>
 */

public interface RadSurveyD {
	
	// ���� ��� ���� ��ȸ
	public Map<String, Object> selectEstSuvYsno(Map<String, Object> inMap);

	// ���� ��� ���� ��ȸ
	public Map<String, Object> selectOrderSuvYsno(Map<String, Object> inMap);
	
	// ������ ���𵨸� ������� ��ȸ
	public List<SuvPrhVO> selectSuvPrhList(Map<String, Object> inMap);

	// sap ���𵨸� ������� ��ȸ
	public List<SuvPrhVO> selectSapSuvPrhList(Map<String, Object> inMap);

	// ���� ���𵨸� ������� ��ȸ
	public List<SuvPrhVO> selectOrderSuvPrhList(Map<String, Object> inMap);
	
    // ���� ǥ��� �Է� ������ ��ȸ
	public List<SuvFloorVO> selectSuvFloorList(Map<String, Object> inMap);

	// ���𵨸� ���嵵�� �ǳ� ���� ��ȸ
	public List<Map> selectElrEPnlhList(Map<String, Object> inMap);

	// �׷��ε� JAMB �°����̸� ��ȸ����.
	public List<Map> selectJampPrhSubPrhList(Map<String, Object> inMap);
	
	// ���𵨸���������������� ����
	public int deleteZPST1138(Map<String, Object> inMap);
	
	// ���𵨸���������������� ����
	public int deleteZPST1139(Map<String, Object> inMap);

	// ���𵨸� ���� ���갪 �ʱ�ȭ
	public int updateHZPST1139(Map<String, Object> inMap);
	
	// ���𵨸���������������� ����
	public int insertZPST1138(FloorNmVO floorNmVO);

	// ���𵨸���������������� ����
    public int insertZPST1139(FloorNmVO floorNmVO);


	// ���𵨸� hpi �Ӽ��� ��ȸ
	public Map<String, Object> selectHpiZPST1133(Map<String, Object> inMap);

	// ���𵨸� hpb �Ӽ��� ��ȸ
	public Map<String, Object> selectHpbZPST1133(Map<String, Object> inMap);

	// ���𵨸� hip �Ӽ��� ��ȸ
	public Map<String, Object> selectHipZPST1133(Map<String, Object> inMap);

	// ���𵨸� hlt �Ӽ��� ��ȸ
	public Map<String, Object> selectHltZPST1133(Map<String, Object> inMap);

	// ���𵨸� fsw �Ӽ��� ��ȸ
	public Map<String, Object> selectFswZPST1133(Map<String, Object> inMap);
	
	// ���𵨸� ���嵵���ǳڳ��� ��ȸ
	public Map<String, Object> selectElrEPnlh(Map<String, Object> inMap);
	
    // �°� ���Ŀ� ���ؼ� ����� ���� �����Ѵ�.
	public int deleteEnterElcData(Map<String, Object> inMap);
	
	// ���𵨸� ���갪 ����(���� ���굥���� �� SRM ������ ���� ó��
	public int insertCalZPST1136(Map<String, Object> inMap);

	// ���𵨸� ������ ����
	public int updateModyPST1136(Map<String, Object> inMap);

	// ���𵨸� ���갪 ����
	public int updateCalPST1136(Map<String, Object> inMap);
	
	// ������簪 ���� ��ȸ
	public String selectATWTB(Map<String, Object> inMap);
	
	// ���� �� ���� ȣ������ ��ȸ
	public List<HashMap<String, Object>> selectQtHGList(Map<String, Object> inMap);
	
	// ���� �� ���� ȣ������ ��ȸ
	public List<HashMap<String, Object>> selectOrderHGList(Map<String, Object> inMap);

	// ����������ȣ�� ������� ��ȸ
	public List<HashMap<String, Object>> selectSuvDataList(Map<String, Object> inMap);

	// ����������ȣ�� ������� ǥ�ñ� ��ȸ
	public List<HashMap<String, Object>> selectSuvViewDataList(Map<String, Object> inMap);

	// ����������ȣ�� ������� JAMB ��ȸ
	public List<HashMap<String, Object>> selectSuvJamDataList(Map<String, Object> inMap);
	
}

