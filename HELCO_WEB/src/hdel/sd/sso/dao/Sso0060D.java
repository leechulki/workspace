package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0060;
import hdel.sd.sso.domain.Sso0060ParamVO;
import hdel.sd.sso.domain.ZSDS0060;
import hdel.sd.sso.domain.ZSDS0060T;
import hdel.sd.sso.domain.ZSDS0061;
import hdel.sd.sso.domain.ZSDS0062;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


/**
 * 수주생성(Sso0060D) DAO
 * @Comment 
 *		- List selectListZclose  	( 마감여부 조회 ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


public interface Sso0060D {  
	
	// 프로젝트번호로 견적번호 조회
	public List<Sso0060> selectListQtnum(Sso0060ParamVO param);
	
	// 견적번호로 입찰성공 견적차수 및 프로젝트번호 조회
	public List<Sso0060> selectListQtser(Sso0060ParamVO param);
	
	// 견적번호로 견적상세정보 조회
	public List<Sso0060> selectListQtnumInfo(Sso0060ParamVO param); 
	
	// 견적번호로 견적품목상세정보 조회
	public List<Sso0060> selectListQtnumItemInfo(Sso0060ParamVO param); 
	
	// 판매처정보 조회
	public List<Sso0060> selectListKunnrRg(Sso0060ParamVO param); 

	// 부서명,팀명 조회
	public List<Sso0060> selectListVbVg(Sso0060ParamVO paramV);
	
	// 납품처목록 조회
	public List<Sso0060> selectListKunnrWe(Sso0060ParamVO param);
	
	// 대리점정보 조회
	public List<Sso0060> selectListKunnrZ1(Sso0060ParamVO param);
	
	// 담당자정보 조회
	public List<Sso0060> selectListKunnrZ2(Sso0060ParamVO param);
	
	// 기술영업담당자정보 조회
	public List<Sso0060> selectListKunnrZ3(Sso0060ParamVO param);
	
	// 견적진행상태 변경
	public void updateZsdt1046Zprgflg(Sso0060 param);

	// 수주계획상태 변경
	public void updateZsdt1001Sorlt(Sso0060 param);
	
	//SO DATA 임시저장
	public void saveTemp(ZSDS0060 param);
	
	/* 2018.03.06 - source 갱신 (sso.dao.Sso0060ND.class)
	public Sso0060 selectExchangeRate(Sso0060ParamVO param);
	*/
}
