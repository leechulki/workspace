package hdel.sd.sbp.service;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.sd.com.domain.Com0040;
import hdel.sd.com.domain.Com0040ParamVO;
import hdel.sd.com.service.Com0040S;
import hdel.sd.sbp.dao.Sbp0010D;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// 매출/청구/수금 자동산출용 INPUT
// 매출/청구/수금 자동산출용 OUTPUT
// 원가산출결과 저장용
// 원가산출결과 저장용


/**
 * 사업계획관리(Sbp0010S) Service
 * @Comment
 *     	1.  void createDao
 *		2.  List selectOpenDtm  	( 사업계획 오픈일시(14자리) 조회 )
 *		3.  List selectList 		( 사업계획 목록 조회 )
 *		4.  List selectListDetail	( 사업계획번호 한 건에 등록된 사업계획 매출/청구/수금목록 조회 )
 *		5.  void save 				( 수주 저장 )
 *		6.  void detail_save 		( 매출/청구/수금 저장 )
 *
 * @since 1.0
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version
 * @version 1.0
 */


@Service
public class Sbp0010S {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

//	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Com0040S BuyrService ;	// 거래선조회 서비스

	private Sbp0010D sbp0010Dao;

	public void createDao(SqlSession sqlSession) {
		sbp0010Dao = sqlSession.getMapper(Sbp0010D.class);
	}

	// 사업계획 오픈일시(14자리) 조회
	public List<Sbp0010> selectOpenDtm(Sbp0010ParamVO param) {
		return sbp0010Dao.selectOpenDtm(param);
	}

	// 사업계획 수주예상액, 통화 조회 : 매출/청구/수금 자동산출 대상 변경조건
	public List<Sbp0010> selectListComputeItem(Sbp0010ParamVO param) {
		return sbp0010Dao.selectListComputeItem(param);
	}

	// 사업계획 목록 조회
	public List<Sbp0010> selectList(Sbp0010ParamVO param) {
		return sbp0010Dao.selectList(param);
	}

	// 사업계획번호 한 건에 등록된 사업계획 매출/청구/수금목록 조회
	public List<Sbp0010> selectListDetail(Sbp0010ParamVO param) {
		return sbp0010Dao.selectListDetail(param);
	}

	// 오픈일시 존재여부 체크
	public Boolean chkOpenDtm(SqlSession session
					            , String mandt
								, String zpyear		// 편성년도
								)
	{
		logger.debug("@@@ Sbp0010S.chkOpenDtm START!!!" + "");
		logger.debug("@@@ Sbp0010S.chkOpenDtm.mandt	: "	+ mandt + "");
		logger.debug("@@@ Sbp0010S.chkOpenDtm.zpyear	: "	+ zpyear + "");

		Sbp0010ParamVO param = new Sbp0010ParamVO();

		// 파라메터SET
		param.setMANDT	(mandt);  	// SAP CLIENT
		param.setZPYEAR	(zpyear);	// 편성년도

		// 서비스CALL (사업계획년도에 해당하는 오픈일자 조회)
		List<Sbp0010> list = selectOpenDtm(param);

		// 조회된 오픈일시 정보가 없을 경우 오류로 리턴한다.(저장불가처리)
		if (list.size()==0) return false;

		return true;
	}

	// 거래선코드 유효성 체크
	public Boolean chkBuyrCd(SqlSession session
			                    , String mandt
								, String kunnr		// 거래선코드
								)
	{

		logger.debug("@@@ Sbp0010S.chkBuyrCd START!!!" 	+ "");
		logger.debug("@@@ Sbp0010S.chkBuyrCd.mandt : " 	+ mandt + "");
		logger.debug("@@@ Sbp0010S.chkBuyrCd.kunnr : " 	+ kunnr + "");

		Com0040ParamVO param = new Com0040ParamVO();

		// DAO생성
		BuyrService.createDao(session);

		// Parameter SET
		param.setMandt		(mandt);  	// SAP CLIENT
		param.setBuyr_cd	(kunnr);	// 거래선코드

		// 거래선명 조회
		List<Com0040> list = BuyrService.find(param);

		// 조회된 거래선정보가 없을 경우 오류로 리턴한다.(저장불가처리)
		if (list.size()==0) return false;

		return true;
	}

	// 사업계획 원가 및 저장상태 변경
	public void saveCost(Sbp0010 param) {
		sbp0010Dao.updateCostZSDT1012(param);
	}

	// 대리점정보 조회
	public List<Sbp0010> selectListZagnt(Sbp0010ParamVO paramV) {
		return sbp0010Dao.selectListZagnt(paramV);
	}

	// 실기종 정보  조회
	public List<Sbp0010> selectListRGtype(Sbp0010ParamVO paramV) {
		return sbp0010Dao.selectListRGtype(paramV);
	}
}
