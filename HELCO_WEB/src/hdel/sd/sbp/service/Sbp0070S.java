package hdel.sd.sbp.service;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;

import hdel.sd.sbp.dao.Sbp0070D;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO;
import hdel.sd.sbp.domain.Sbp0070;
import hdel.sd.sbp.domain.Sbp0070ParamVO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.service.resource.TransactionManager;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import org.apache.log4j.Logger;


/**
 * 사업계획 오픈/마감(Sbp0070S) Service
 * @Comment
 *     	1.  void createDao
 *		2.  List selectList 	( 사업계획차수  목록 조회 )
 *		3.  List selectListLast ( 최종 사업계획차수 정보 조회 )
 *		4.  void save 			( 사업계획차수 정보 저장 )
 *			- 처리구분 : IN(차수생성) DL(차수삭제) OP(오픈) OC(오픈취소) CL(마감) CC(마감취소)
 *
 * @since 1.0
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version
 * @version 1.0
 */


@Service
public class Sbp0070S {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

//	@Autowired
	private SrmSqlSession sqlSession;

	private Sbp0070D sbp0070Dao;

	public void createDao(SqlSession sqlSession) {
		this.sqlSession = (SrmSqlSession) sqlSession;
		sbp0070Dao = sqlSession.getMapper(Sbp0070D.class);
	}

	// 사업계획차수 목록 조회
	public List<Sbp0070> selectList(Sbp0070ParamVO param) {
		return sbp0070Dao.selectList(param);
	}

	// 사업계획차수 최종정보 조회
	public List<Sbp0070> selectListLast(Sbp0070ParamVO param) {
		return sbp0070Dao.selectListLast(param);
	}

	// 사업계획차수 정보 저장
	public void save(MipParameterVO paramVO, Model model) throws Exception{

		logger.debug("@@@ Sbp0070S.save START!!!" + "");

		// INPUT PARAM GET
		Dataset ds_list_save 	= paramVO.getDataset("ds_list_save");  					// 수주 등록,수정,삭제 대상정보

		// 처리구분 (IN:차수생성, DL:차수삭제, OP:오픈, OC:오픈취소, CL:마감, CC:마감취소
		String  proc_gb			= DatasetUtility.getString(ds_list_save, 0, "PROCGB");

		createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		//-----------------------------------------------------------------------
		// 신규차수생성인 경우
		//-----------------------------------------------------------------------
		if ("IN".equals(proc_gb))
		{
			Sbp0070ParamVO 	param 	= new Sbp0070ParamVO();
			String 	mandt 			= paramVO.getVariable("G_MANDT");						// SAP CLIENT
			String 	user_id 		= paramVO.getVariable("G_USER_ID");						// WEB USER ID
			String  zpyear			= DatasetUtility.getString(ds_list_save, 0, "ZPYEAR");	// 편성년도

			param.setMANDT(mandt);
			param.setUSER_ID(user_id);
			param.setZPYEAR(zpyear);

			// 차수생성
			sbp0070Dao.insertZSDT1017(param);
		}

		//-----------------------------------------------------------------------
		// 그외
		//-----------------------------------------------------------------------
		else
		{

			Sbp0070[] param = (Sbp0070[])CallSAPHdel.moveDs2Obj2(ds_list_save, Sbp0070.class, "", null);

			//------------------------------------------
			// 차수 삭제인 경우
			//------------------------------------------
			if ("DL".equals(proc_gb)) {

				// 차수삭제
				sbp0070Dao.deleteZSDT1017(param[0]);

			}
			//------------------------------------------
			// 오픈 또는 오픈취소 인 경우
			//------------------------------------------
			else if ("OP".equals(proc_gb) || "OC".equals(proc_gb)) {

				// 차수오픈 / 차수오픈취소
				sbp0070Dao.openZSDT1017(param[0]);

			}
			//------------------------------------------
			// 마감 또는 마감취소 인 경우
			//------------------------------------------
			else if ("CL".equals(proc_gb) || "CC".equals(proc_gb)) {

				// 차수마감/마감취소
				sbp0070Dao.closeZSDT1017(param[0]);

				if ("CL".equals(proc_gb)){ 		// 마감인 경우
					sbp0070Dao.intoZSDT1018(param[0]);	// 사업계획차수-수주 백업
					sbp0070Dao.intoZSDT1019(param[0]);	// 사업계획차수-매출 백업
					sbp0070Dao.intoZSDT1020(param[0]);	// 사업계획차수-청구 백업
					sbp0070Dao.intoZSDT1021(param[0]);	// 사업계획차수-수금 백업
					sbp0070Dao.intoZSDT1022(param[0]);	// 사업계획차수-원가 백업
					sbp0070Dao.intoZSDT1028(param[0]);	// 사업계획차수(보수)-수주 백업
					sbp0070Dao.intoZSDT1029(param[0]);	// 사업계획차수(보수)-매출 백업
					sbp0070Dao.intoZSDT1030(param[0]);	// 사업계획차수(보수)-청구 백업
					sbp0070Dao.intoZSDT1031(param[0]);	// 사업계획차수(보수)-수금 백업
					sbp0070Dao.intoZSDT1032(param[0]);	// 사업계획차수(보수)-원가 백업
				}
				else if ("CC".equals(proc_gb)){  // 마감취소인 경우
					sbp0070Dao.deleteZSDT1018(param[0]);	// 사업계획차수-수주 백업자료 삭제
					sbp0070Dao.deleteZSDT1019(param[0]);	// 사업계획차수-매출 백업자료 삭제
					sbp0070Dao.deleteZSDT1020(param[0]);	// 사업계획차수-청구 백업자료 삭제
					sbp0070Dao.deleteZSDT1021(param[0]);	// 사업계획차수-수금 백업자료 삭제
					sbp0070Dao.deleteZSDT1022(param[0]);	// 사업계획차수-원가 백업자료 삭제
					sbp0070Dao.deleteZSDT1028(param[0]);	// 사업계획차수(보수)-수주 백업자료 삭제
					sbp0070Dao.deleteZSDT1029(param[0]);	// 사업계획차수(보수)-매출 백업자료 삭제
					sbp0070Dao.deleteZSDT1030(param[0]);	// 사업계획차수(보수)-청구 백업자료 삭제
					sbp0070Dao.deleteZSDT1031(param[0]);	// 사업계획차수(보수)-수금 백업자료 삭제
					sbp0070Dao.deleteZSDT1032(param[0]);	// 사업계획차수(보수)-원가 백업자료 삭제
				}
			}

		}

		logger.debug("@@@ Sbp0070S.save SUCCESS!!!" + "");

	}

}
