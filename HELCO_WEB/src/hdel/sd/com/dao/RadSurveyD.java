package hdel.sd.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hdel.sd.com.domain.FloorNmVO;
import hdel.sd.com.domain.SuvFloorVO;
import hdel.sd.com.domain.SuvPrhVO;

/**
 * 리모델링용 실측적용 연산 Dao
 * 
 * @author  박수근
 * @since 2021.01.26
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2021.01.26         박수근          최초 생성
 * 
 * </pre>
 */

public interface RadSurveyD {
	
	// 실적 등록 상태 조회
	public Map<String, Object> selectEstSuvYsno(Map<String, Object> inMap);

	// 실적 등록 상태 조회
	public Map<String, Object> selectOrderSuvYsno(Map<String, Object> inMap);
	
	// 견적서 리모델링 실측사양 조회
	public List<SuvPrhVO> selectSuvPrhList(Map<String, Object> inMap);

	// sap 리모델링 실측사양 조회
	public List<SuvPrhVO> selectSapSuvPrhList(Map<String, Object> inMap);

	// 수주 리모델링 실측사양 조회
	public List<SuvPrhVO> selectOrderSuvPrhList(Map<String, Object> inMap);
	
    // 실측 표기시 입력 데이터 조회
	public List<SuvFloorVO> selectSuvFloorList(Map<String, Object> inMap);

	// 리모델링 승장도어 판넬 실측 조회
	public List<Map> selectElrEPnlhList(Map<String, Object> inMap);

	// 그룹핑된 JAMB 승강데이를 조회힌다.
	public List<Map> selectJampPrhSubPrhList(Map<String, Object> inMap);
	
	// 리모델링영업사양층고전개 삭제
	public int deleteZPST1138(Map<String, Object> inMap);
	
	// 리모델링실측사양층고전개 삭제
	public int deleteZPST1139(Map<String, Object> inMap);

	// 리모델링 층고 연산값 초기화
	public int updateHZPST1139(Map<String, Object> inMap);
	
	// 리모델링영업사양층고전개 저장
	public int insertZPST1138(FloorNmVO floorNmVO);

	// 리모델링실측사양층고전개 저장
    public int insertZPST1139(FloorNmVO floorNmVO);


	// 리모델링 hpi 속성값 조회
	public Map<String, Object> selectHpiZPST1133(Map<String, Object> inMap);

	// 리모델링 hpb 속성값 조회
	public Map<String, Object> selectHpbZPST1133(Map<String, Object> inMap);

	// 리모델링 hip 속성값 조회
	public Map<String, Object> selectHipZPST1133(Map<String, Object> inMap);

	// 리모델링 hlt 속성값 조회
	public Map<String, Object> selectHltZPST1133(Map<String, Object> inMap);

	// 리모델링 fsw 속성값 조회
	public Map<String, Object> selectFswZPST1133(Map<String, Object> inMap);
	
	// 리모델링 승장도어판넬높이 조회
	public Map<String, Object> selectElrEPnlh(Map<String, Object> inMap);
	
    // 승강 계산식에 의해서 산출된 식을 삭제한다.
	public int deleteEnterElcData(Map<String, Object> inMap);
	
	// 리모델링 연산값 저장(층장 연산데이터 및 SRM 연산결과 저장 처리
	public int insertCalZPST1136(Map<String, Object> inMap);

	// 리모델링 보정값 수정
	public int updateModyPST1136(Map<String, Object> inMap);

	// 리모델링 연산값 수정
	public int updateCalPST1136(Map<String, Object> inMap);
	
	// 영업사양값 내역 조회
	public String selectATWTB(Map<String, Object> inMap);
	
	// 견적 시 실측 호기정보 조회
	public List<HashMap<String, Object>> selectQtHGList(Map<String, Object> inMap);
	
	// 수주 시 실측 호기정보 조회
	public List<HashMap<String, Object>> selectOrderHGList(Map<String, Object> inMap);

	// 실측고유번호별 실측사양 조회
	public List<HashMap<String, Object>> selectSuvDataList(Map<String, Object> inMap);

	// 실측고유번호별 실측사양 표시기 조회
	public List<HashMap<String, Object>> selectSuvViewDataList(Map<String, Object> inMap);

	// 실측고유번호별 실측사양 JAMB 조회
	public List<HashMap<String, Object>> selectSuvJamDataList(Map<String, Object> inMap);
	
}

