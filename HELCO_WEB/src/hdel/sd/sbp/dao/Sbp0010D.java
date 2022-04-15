package hdel.sd.sbp.dao;

import hdel.lib.domain.MipParameterVO;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO; 
import hdel.sd.sbp.domain.Sbp00101;
import hdel.sd.sbp.domain.Sbp00102;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;


/**
 * 사업계획관리(Sbp0010D) DAO
 * @Comment 
 *		- List selectOpenDtm  	( 사업계획 오픈일시(14자리) 조회 )
 *		- List selectList 		( 사업계획 목록 조회 )
 *		- List selectListDetail	( 사업계획번호 한 건에 등록된 사업계획 매출/청구/수금목록 조회 )  
 *		- void insertZSDT1012 	( 사업계획(ZSDT1012) 등록 )
 *		- void updateZSDT1012	( 사업계획(ZSDT1012) 변경 )
 *		- void deleteZSDT1012 	( 사업계획(ZSDT1012) 삭제 )
 *		- void insertZSDT1013 	( 사업계획-매출(ZSDT1013) 등록 (사업계획번호별 매출년월별) )
 *		- void deleteZSDT1013 	( 사업계획-매출(ZSDT1013) 삭제 (사업계획번호별) )
 *		- void insertZSDT1014 	( 사업계획-청구(ZSDT1014) 등록 (사업계획번호별 청구년월별) )
 *		- void deleteZSDT1014 	( 사업계획-청구(ZSDT1014) 삭제 (사업계획번호별)  )
 *		- void insertZSDT1015 	( 사업계획-수금(ZSDT1015) 등록 (사업계획번호별 수금년월별) )
 *		- void deleteZSDT1015 	( 사업계획-수금(ZSDT1015) 삭제 (사업계획번호별)  )
 *		- void insertZSDT1016 	( 사업계획-원가(ZSDT1016) 등록 (사업계획번호별 수금년월별) )
 *		- void deleteZSDT1016 	( 사업계획-원가(ZSDT1016) 삭제 (사업계획번호별)  )
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */

public interface Sbp0010D {  
	
	// 사업계획 오픈일시(14자리) 조회
	public List<Sbp0010> selectOpenDtm(Sbp0010ParamVO param);

	// 사업계획 수주예상액, 통화 조회 : 매출/청구/수금 자동산출 대상 변경조건
	public List<Sbp0010> selectListComputeItem(Sbp0010ParamVO param);
		
	// 사업계획목록 조회
	public List<Sbp0010> selectList(Sbp0010ParamVO param);
	
	// 사업계획번호에 한 건에 등록된 사업계획 매출/청구/수금목록 조회
	public List<Sbp0010> selectListDetail(Sbp0010ParamVO param);	
	
	// 사업계획 원가 및 저장상태 변경
	public void updateCostZSDT1012(Sbp0010 param);

	// 대리점정보 조회
	public List<Sbp0010> selectListZagnt(Sbp0010ParamVO paramV);
	
	// 실기종 정보  조회
	public List<Sbp0010> selectListRGtype(Sbp0010ParamVO paramV);

}
