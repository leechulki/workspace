package hdel.sd.sso.service;
 
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0031D;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.sso.dao.Sso0050D;
import hdel.sd.sso.dao.Sso0060D;
import hdel.sd.sso.domain.Sso0050ParamVO;
import hdel.sd.sso.domain.Sso0050VO;
import hdel.sd.sso.domain.Sso0060ParamVO;
import hdel.sd.sso.domain.Sso0060;
import hdel.sd.sso.domain.ZSDS0060;
import hdel.sd.sso.domain.ZSDS0060T;
import hdel.sd.sso.domain.ZSDS0061;
import hdel.sd.sso.domain.ZSDS0062;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;



/**
 * 수주생성(Sso0060S) Service
 * @Comment
 *     	1.  void createDao 
 *		2.  List find_zclose  	( 마감여부 조회 ) 
 *     
 * @since 1.0 
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version 
 * @version 1.0
 */


@Service
public class Sso0060S extends SrmService {
	
	Logger logger = Logger.getLogger(this.getClass());

	private Sso0060D dao;
	private Ses0031D ses0031Dao;
	private Sso0050D sso0050Dao;
	
	public void createDao(SqlSession sqlSession) { 
		dao = sqlSession.getMapper(Sso0060D.class);
		ses0031Dao = sqlSession.getMapper(Ses0031D.class);
		sso0050Dao = sqlSession.getMapper(Sso0050D.class);
	} 
	
	// 프로젝트번호로 견적번호 조회
	public List<Sso0060> selectListQtnum(Sso0060ParamVO paramV) {  
		return dao.selectListQtnum(paramV); 
	}
	
	// 견적번호로 입찰성공 견적차수 및 프로젝트번호 조회
	public List<Sso0060> selectListQtser(Sso0060ParamVO paramV) {  
		return dao.selectListQtser(paramV); 
	}
	
	// 견적번호로 견적상세정보 조회
	public List<Sso0060> selectListQtnumInfo(Sso0060ParamVO paramV) {  
		return dao.selectListQtnumInfo(paramV); 
	}
	
	// 견적번호로 견적품목상세정보 조회
	public List<Sso0060> selectListQtnumItemInfo(Sso0060ParamVO paramV) {  
		return dao.selectListQtnumItemInfo(paramV); 
	}
	
	// 부서명,팀명 조회
	public List<Sso0060> selectListVbVg(Sso0060ParamVO paramV) {  
		return dao.selectListVbVg(paramV); 
	}
		
	// 판매처 정보 조회
	public List<Sso0060> selectListKunnrRg(Sso0060ParamVO paramV) {  
		return dao.selectListKunnrRg(paramV); 
	}

	// 납품처 정보 조회
	public List<Sso0060> selectListKunnrWe(Sso0060ParamVO paramV) {  
		return dao.selectListKunnrWe(paramV); 
	}

	// 대리점정보 조회
	public List<Sso0060> selectListKunnrZ1(Sso0060ParamVO paramV) {  
		return dao.selectListKunnrZ1(paramV); 
	}

	// 담당자정보 조회
	public List<Sso0060> selectListKunnrZ2(Sso0060ParamVO paramV) {  
		return dao.selectListKunnrZ2(paramV); 
	}

	// 기술영업담당자정보 조회
	public List<Sso0060> selectListKunnrZ3(Sso0060ParamVO paramV) {  
		return dao.selectListKunnrZ3(paramV); 
	}
	
	// 2012.10.22 견적진행상태 Update
	public void updateZsdt1046Zprgflg(Sso0060 param)	{
		dao.updateZsdt1046Zprgflg(param);
		return;
	}

	// 2012.10.23 수주계획상태 Update
	public void updateZsdt1001Sorlt(Sso0060 param)	{
		dao.updateZsdt1001Sorlt(param);
		return;
	}
	// 2014.10.07 수주생성 임시저장
	public void saveTemp(ZSDS0060 param)	{
		dao.saveTemp(param);
		return;
	}
	
	/**
	 * 매매기준환율 조회 2013.02.20
	 * 2018.03.06 - 환율조회 공통 모듈화 (com.ExchangeS.getExchangeRate)
	public List<Sso0050VO> searchExchange0050(Sso0050ParamVO param) {
		return sso0050Dao.selectListExchange(param);
	}
	
	public Ses0031 searchExchange0031(Ses0031ParamVO param) {
		return ses0031Dao.selectNewExchange(param.getMandt(), param.getQtdat());
	}
	
	//2018.03.06 - source 갱신 (sso.dao.Sso0060NS.class) 
	public Sso0060 selectExchangeRate (Sso0060ParamVO param) {
		return dao.selectExchangeRate(param);
	}
	 */	
}
