package hdel.sd.sbp.dao;

import hdel.lib.domain.MipParameterVO;
import hdel.sd.sbp.domain.Sbp0070;
import hdel.sd.sbp.domain.Sbp0070ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;
 

/**
 * 사업계획 오픈/마감(Sbp0070D) DAO
 * @Comment  
 *		- List selectList 		( 사업계획차수  목록 조회  )
 *		- List selectListLast 	( 최종 사업계획차수 정보 조회  ) 
 *  	- void insertZSDT1017	( 사업계획차수 등록 ) 
 *  	- void deleteZSDT1017	( 사업계획차수 삭제 ) 
 *  	- void openZSDT1017		( 사업계획차수 오픈/오픈취소 ) 
 *  	- void closeZSDT1017	( 사업계획차수 마감 /마감취소) 
 *  	- void intoZSDT1018		( 마감  시 사업계획차수-수주 백업 ) 
 *  	- void intoZSDT1019		( 마감  시 사업계획차수-매출 백업 ) 
 *  	- void intoZSDT1020		( 마감  시 사업계획차수-청구 백업 ) 
 *  	- void intoZSDT1021		( 마감  시 사업계획차수-수금 백업 ) 
 *  	- void intoZSDT1022		( 마감  시 사업계획차수-원가 백업 ) 
 *  	- void intoZSDT1028		( 마감  시 사업계획차수(보수)-수주 백업 ) 
 *  	- void intoZSDT1029		( 마감  시 사업계획차수(보수)-매출 백업 ) 
 *  	- void intoZSDT1030		( 마감  시 사업계획차수(보수)-청구 백업 ) 
 *  	- void intoZSDT1031		( 마감  시 사업계획차수(보수)-수금 백업 ) 
 *  	- void intoZSDT1032		( 마감  시 사업계획차수(보수)-원가 백업 ) 
 *  	- void deleteZSDT1018	( 마감취소  시 사업계획차수-수주 백업자료 삭제 ) 
 *  	- void deleteZSDT1019	( 마감취소  시 사업계획차수-매출 백업자료 삭제 ) 
 *  	- void deleteZSDT1020	( 마감취소  시 사업계획차수-청구 백업자료 삭제 ) 
 *  	- void deleteZSDT1021	( 마감취소  시 사업계획차수-수금 백업자료 삭제 ) 
 *  	- void deleteZSDT1022	( 마감취소  시 사업계획차수-원가 백업자료 삭제 ) 
 *  	- void deleteZSDT1028	( 마감취소  시 사업계획차수(보수)-수주 백업자료 삭제 ) 
 *  	- void deleteZSDT1029	( 마감취소  시 사업계획차수(보수)-매출 백업자료 삭제 ) 
 *  	- void deleteZSDT1030	( 마감취소  시 사업계획차수(보수)-청구 백업자료 삭제 ) 
 *  	- void deleteZSDT1031	( 마감취소  시 사업계획차수(보수)-수금 백업자료 삭제 ) 
 *  	- void deleteZSDT1032	( 마감취소  시 사업계획차수(보수)-원가 백업자료 삭제 );
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */ 

public interface Sbp0070D {  
	 
	// 사업계획차수목록 조회
	public List<Sbp0070> selectList(Sbp0070ParamVO param); 
	
	// 최종사업계획차수정보 조회
	public List<Sbp0070> selectListLast(Sbp0070ParamVO param); 
	 
	// 사업계획차수 등록
	public void insertZSDT1017(Sbp0070ParamVO param);
	 
	// 사업계획차수 삭제
	public void deleteZSDT1017(Sbp0070 param);
	
	// 사업계획차수 오픈
	public void openZSDT1017(Sbp0070 param);

	// 사업계획차수 마감
	public void closeZSDT1017(Sbp0070 param);
	
	// 마감  시 사업계획차수-수주 백업
	public void intoZSDT1018(Sbp0070 param);
	
	// 마감  시 사업계획차수-매출 백업
	public void intoZSDT1019(Sbp0070 param);
	
	// 마감  시 사업계획차수-청구 백업
	public void intoZSDT1020(Sbp0070 param);
	
	// 마감  시 사업계획차수-수금 백업
	public void intoZSDT1021(Sbp0070 param);
	
	// 마감  시 사업계획차수-원가 백업
	public void intoZSDT1022(Sbp0070 param);
	
	// 마감  시 사업계획차수(보수)-수주 백업
	public void intoZSDT1028(Sbp0070 param);
	
	// 마감  시 사업계획차수(보수)-매출 백업
	public void intoZSDT1029(Sbp0070 param);
	
	// 마감  시 사업계획차수(보수)-청구 백업
	public void intoZSDT1030(Sbp0070 param);
	
	// 마감  시 사업계획차수(보수)-수금 백업
	public void intoZSDT1031(Sbp0070 param);
	
	// 마감  시 사업계획차수(보수)-원가 백업
	public void intoZSDT1032(Sbp0070 param);
	
	// 마감취소  시 사업계획차수-수주 백업자료 삭제
	public void deleteZSDT1018(Sbp0070 param);
		
	// 마감취소  시 사업계획차수-매출 백업자료 삭제
	public void deleteZSDT1019(Sbp0070 param);
	
	// 마감취소  시 사업계획차수-청구 백업자료 삭제
	public void deleteZSDT1020(Sbp0070 param);
	
	// 마감취소  시 사업계획차수-수금 백업자료 삭제
	public void deleteZSDT1021(Sbp0070 param);
	
	// 마감취소  시 사업계획차수-원가 백업자료 삭제
	public void deleteZSDT1022(Sbp0070 param);
	
	// 마감취소  시 사업계획차수(보수)-수주 백업자료 삭제
	public void deleteZSDT1028(Sbp0070 param);
	
	// 마감취소  시 사업계획차수(보수)-매출 백업자료 삭제
	public void deleteZSDT1029(Sbp0070 param);
	
	// 마감취소  시 사업계획차수(보수)-청구 백업자료 삭제
	public void deleteZSDT1030(Sbp0070 param);
	
	// 마감취소  시 사업계획차수(보수)-수금 백업자료 삭제
	public void deleteZSDT1031(Sbp0070 param);
	
	// 마감취소  시 사업계획차수(보수)-원가 백업자료 삭제
	public void deleteZSDT1032(Sbp0070 param);
	
}
