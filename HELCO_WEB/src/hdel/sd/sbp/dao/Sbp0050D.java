package hdel.sd.sbp.dao;

import hdel.lib.domain.MipParameterVO;
import hdel.sd.sbp.domain.Sbp0050;
import hdel.sd.sbp.domain.Sbp0050ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;


/**
 * 사업계획매출채권관리(정기보수)(Sbp0050D) DAO
 * @Comment 
 *		- List selectOpenDtm  	( 사업계획(보수) 오픈일시(14자리) 조회 )
 *		- List selectList 		( 사업계획(보수) 목록 조회 )
 *		- List selectListDetail	( 사업계획번호 한 건에 등록된 사업계획(보수) 매출/청구/수금목록 조회 ) 
 *		- void insertZSDT1023  	( 사업계획(보수)(ZSDT1023) 등록 )
 *		- void updateZSDT1023 	( 사업계획(보수)(ZSDT1023) 변경 )
 *		- void deleteZSDT1023  	( 사업계획(보수)(ZSDT1023) 삭제 )
 *		- void insertZSDT1024  	( 사업계획(보수)-매출(ZSDT1024) 등록 (사업계획번호별 매출년월별) )
 *		- void deleteZSDT1024  	( 사업계획(보수)-매출(ZSDT1024) 삭제 (사업계획번호별) )
 *		- void insertZSDT1025  	( 사업계획(보수)-청구(ZSDT1025) 등록 (사업계획번호별 청구년월별) )
 *		- void deleteZSDT1025  	( 사업계획(보수)-청구(ZSDT1025) 삭제 (사업계획번호별)  )
 *		- void insertZSDT1026  	( 사업계획(보수)-수금(ZSDT1026) 등록 (사업계획번호별 수금년월별) )
 *		- void deleteZSDT1026  	( 사업계획(보수)-수금(ZSDT1026) 삭제 (사업계획번호별)  ) 
 *
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


public interface Sbp0050D {  
	
	// 사업계획 오픈일시(14자리) 조회
	public List<Sbp0050> selectOpenDtm(Sbp0050ParamVO param);  
		
	// 사업계획목록 조회
	public List<Sbp0050> selectList(Sbp0050ParamVO param);
	
	// 사업계획번호에 한 건에 등록된 사업계획 매출/청구/수금목록 조회
	public List<Sbp0050> selectListDetail(Sbp0050ParamVO param);		
	
	// 사업계획 저장  
	public void save(MipParameterVO paramVO, Model mode, String newZbpnnr);
	
	// 사업계획 등록
	public void insertZSDT1023(Sbp0050 param);
	
	// 사업계획 변경
	public void updateZSDT1023(Sbp0050 param);
	
	// 사업계획 삭제
	public void deleteZSDT1023(Sbp0050 param);
	
	// 사업계획-매출 등록 (사업계획번호별 매출년월별)
	public void insertZSDT1024(Sbp0050 param); 
	
	// 사업계획-매출 삭제 (사업계획번호별)
	public void deleteZSDT1024(Sbp0050 param);
	
	// 사업계획-청구 등록 (사업계획번호별 매출년월별)
	public void insertZSDT1025(Sbp0050 param); 
	
	// 사업계획-청구 삭제 (사업계획번호별)
	public void deleteZSDT1025(Sbp0050 param);
	
	// 사업계획-수금 등록 (사업계획번호별 매출년월별)
	public void insertZSDT1026(Sbp0050 param); 
	
	// 사업계획-수금 삭제 (사업계획번호별)
	public void deleteZSDT1026(Sbp0050 param); 
	
}
