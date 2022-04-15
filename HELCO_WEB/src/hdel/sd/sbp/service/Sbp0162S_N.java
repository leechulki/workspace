package hdel.sd.sbp.service;


import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoSoNumberS;
import hdel.sd.sbp.dao.Sbp0162D;
import hdel.sd.sbp.domain.Sbp0162ParamVO;
import hdel.sd.sso.domain.ZCOBS001;
import hdel.sd.sbp.domain.ZSDS0072;
import hdel.sd.sbp.domain.ZSDT0014;
import hdel.sd.sbp.domain.ZSDT0014S;
import hdel.sd.sbp.domain.ZSDT1001;
import hdel.sd.sbp.domain.ZSDT1005;
import hdel.sd.sbp.domain.ZSDT1012;
import hdel.sd.sbp.domain.ZSDT1023;
import hdel.sd.ses.domain.Ses0180;
import hdel.sd.smp.control.SmpComC;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

@Service
public class Sbp0162S_N extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private final String SORLT_NEW = "10";

//	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private AutoSoNumberS autoSoService;

	private Sbp0162D sbp0162D;

	@Override
	public void createDao(SqlSession sqlSession) {
		this.sqlSession = (SrmSqlSession) sqlSession;
		sbp0162D = sqlSession.getMapper(Sbp0162D.class);
	}

	/**
	 *
	 * 종결처리시 공사취소(71),공사중단(72),미참여(73)시 수주결과(sorlt)를 갱신
	 *
	 * @param paramVO
	 */
	public void updateSORLT(MipParameterVO paramVO) throws Exception{

		Dataset dsInput = paramVO.getDatasetList().getDataset("ds_input");

		logger.info("@@@@@@@@@ 종결처리 update in @@@@@@@@@@@@@@");
		logger.info("@@@@@@@@@ 종결처리 update in dsCond : "+dsInput.toString() );

		try {

			Sbp0162ParamVO param = new Sbp0162ParamVO();
			param.setMANDT(paramVO.getVariable("G_MANDT"));			// 클라이언트
			param.setSORLT(dsInput.getColumnAsString(0, "SORLT"));	// 수주결과
			param.setSONNR(dsInput.getColumnAsString(0, "SONNR"));	// 수주계획번호
			param.setZPYM(dsInput.getColumnAsString(0, "ZPYM"));		// 계획년월
			param.setDATE(DateTime.getDateString());
			param.setTIME(DateTime.getShortTimeString());
			param.setUSER(paramVO.getVariable("G_USER_ID"));

			sbp0162D.updateSORLT(param);

			if ("73".equals(dsInput.getColumnAsString(0, "SORLT")))		{	// 미참여
				if ("Y".equals(dsInput.getColumnAsString(0, "CHKFLG")))		{	// 낙찰사정보 등록 시
					Ses0180 ses0180 = new Ses0180();
					// 정보 Mapping
					ses0180.setMANDT(paramVO.getVariable("G_MANDT"));
					ses0180.setQTNUM(dsInput.getColumnAsString(0, "SONNR"));	// 견적번호필드에 수주계획번호 입력
					ses0180.setQTSER(0);
					ses0180.setBDDAT(dsInput.getColumnAsString(0, "BDDAT"));	// 입찰예정일
					ses0180.setSBDCM(dsInput.getColumnAsString(0, "ZSEC"));		// 낙찰사
					ses0180.setSBDAM(new BigDecimal(DatasetUtility.getDouble(dsInput, 0, "SBDAM")).setScale(2, RoundingMode.DOWN));	// 낙찰가
					ses0180.setZPRGFLG("31");	// 낙찰실패
					ses0180.setCDATE(DateTime.getDateString());
					ses0180.setCTIME(DateTime.getShortTimeString());
					ses0180.setCUSER(paramVO.getVariable("G_USER_ID"));
					//ses0180.setCTDAT(" ");
					//ses0180.setZCOST(new BigDecimal("0").setScale(2, RoundingMode.DOWN));	// 원가
					//ses0180.setPBDAM(new BigDecimal("0").setScale(2, RoundingMode.DOWN));	// 투찰가
					//ses0180.setZFRSN(" ");
					//ses0180.setZSEC("");		// 2위낙찰사
					//ses0180.setZSECAM(new BigDecimal("0").setScale(2, RoundingMode.DOWN));	// 2위투찰가
					//ses0180.setZFOREC(new BigDecimal("0").setScale(2, RoundingMode.DOWN));	// 2위시행율
					//ses0180.setZSFLG(" ");	// 성공여부
					//ses0180.setZRMK(" ");		// 비고

					sbp0162D.insertZSDT1055(ses0180);
				}
			}

		} catch (Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 *
	 * 종결처리시 이월(70)일경우 copy처리
	 *
	 * @param paramVO
	 */
	public void insertSORLT(MipParameterVO paramVO) throws Exception{
		Dataset dsInput  = paramVO.getDatasetList().getDataset("ds_input");

		// 수주계획등록 RFC호출 후 처리 2012.11.16
		Dataset dsInput2 = paramVO.getDatasetList().getDataset("ds_input2");

		Object obj[] = null;

		ZQMSEMSG[] listMsg = new ZQMSEMSG[] {}; // WEB I/F 처리 오류 결과

		ZSDT0014[] listZSDT0014 = new ZSDT0014[] {}; // 월 수주계획
		ZSDT1001[] listZSDT1001 = new ZSDT1001[] {}; // 수주계획
		ZCOBS001[] listZCOBS001 = new ZCOBS001[] {}; // 원가정보
		ZSDT1005[] listZSDT1005 = new ZSDT1005[] {}; // 수주계획(보수)
		ZSDT0014S[] listZSDT0014S = new ZSDT0014S[] {}; // 사업계획 DATA (WEB에서 전송)
		ZSDT1012[] listZSDT1012 = new ZSDT1012[] {}; // 사업계획-수주
		ZSDT1023[] listZSDT1023 = new ZSDT1023[] {}; // 사업계획(보수)-수주
		ZSDS0072[] listZSDS0072 = new ZSDS0072[] {}; // [SD] 수주/사업 계획 자동 계산

		Dataset dsZSDT0014 = ZSDT0014.getDataset(); // sap dataset정보로 초기화
		Dataset dsZSDT1001 = ZSDT1001.getDataset(); // sap dataset정보로 초기화
		Dataset dsZCOBS001 = ZCOBS001.getDataset(); // sap dataset정보로 초기화

		logger.info("@@@@@@@@@ 종결처리 insert in @@@@@@@@@@@@@@");
		logger.info("@@@@@@@@@ 종결처리 insert in dsCond : "+dsInput.toString() );

		try {

			// 자동채번(수주계획번호)--------------------------------------------------
			// DAO생성
			autoSoService.createDao( sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")) );

			AutoNumberParamVO sonnrParam = new AutoNumberParamVO();
			sonnrParam.setDeptFlag(DatasetUtility.getString(dsInput, "AUART"));	// 판매문서유형
			sonnrParam.setSoQtFlag("1");// 채번구분(0:사업계획, 1:수주계획, 2:견적)
			sonnrParam.setMandt(paramVO.getVariable("G_MANDT")); // SAP CLIENT

			List<AutoNumberVO> sonnrList = autoSoService.AutoSoNumber(sonnrParam);
			// ----------------------------------------------------------------------

			Sbp0162ParamVO param = new Sbp0162ParamVO();
			param.setMANDT(paramVO.getVariable("G_MANDT"));
			param.setSORLT(dsInput.getColumnAsString(0, "SORLT"));		// 종결처리 상태
			param.setSONNR(dsInput.getColumnAsString(0, "SONNR"));		// 종결전 수주계획번호
			param.setZPYM(dsInput.getColumnAsString(0, "ZPYM"));
			param.setNEWSONNR(sonnrList.get(0).getNumber());// 채번 된 수주계획번호
			param.setNEWZPYM(dsInput.getColumnAsString(0, "NEWZPYM"));
			param.setDELDAT(dsInput.getColumnAsString(0, "DELDAT"));	// 납품예정일
			param.setDATE(DateTime.getDateString());
			param.setTIME(DateTime.getShortTimeString());
			param.setUSER(paramVO.getVariable("G_USER_ID"));

			// 종결처리 이전 상태
			param.setPRESORLT(dsInput.getColumnAsString(0, "PRESORLT"));
			param.setNEWSORLT(SORLT_NEW);	// 계획

			logger.info("MANDT    ==> " + param.getMANDT()    + "\n");
			logger.info("SORLT    ==> " + param.getSORLT()    + "\n");
			logger.info("SONNR    ==> " + param.getSONNR()    + "\n");
			logger.info("ZPYM     ==> " + param.getZPYM()     + "\n");
			logger.info("NEWSONNR ==> " + param.getNEWSONNR() + "\n");
			logger.info("NEWZPYM  ==> " + param.getNEWZPYM()  + "\n");
			logger.info("DATE     ==> " + param.getDATE()     + "\n");
			logger.info("TIME     ==> " + param.getTIME()     + "\n");
			logger.info("USER     ==> " + param.getUSER()     + "\n");

			// 수주계획등록 RFC호출  2012.11.16
			dsZSDT0014.deleteAll(); // 월 수주계획
			dsZSDT1001.deleteAll(); // 수주계획
			dsZCOBS001.deleteAll(); // 원가정보

			// input리스트중
			String status = "insert"; // 이월의 상태는 무조건 isert
			int nRow = 0; // 각 데이타셋(ZSDT1001, ZSDT0014, ZCOBS001)에 저장될 row number

			// 이월 처리는 무조건 단일건이나 기존 월 수주계획과 동일한 로직 구현을 위하여 재 사용
			// 단 Duty처리는 불필요.
			for (int i = 0; i < dsInput2.getRowCount(); i++) {

				String matnr = dsInput2.getColumn(i, "MATNR").toString(); // 자재번호

				String gtype = dsInput2.getColumn(i, "GTYPE").toString(); // 대표기종

				dsZSDT0014.appendRow(); // 사업계획 복사테이블
				dsZSDT1001.appendRow(); // 사업계획

				// 계획년월 날짜포맷(-)만들기 -> 계획년월로 변경
				String biddat = DatasetUtility.getString(dsInput, 0, "NEWZPYM").substring(0, 4) + "-" +
							    DatasetUtility.getString(dsInput, 0, "NEWZPYM").substring(4) + "-01";

				// deldat 날짜포맷(-)만들기 -> 변경된 납품예정일
				String deldat = DatasetUtility.getString(dsInput, 0, "DELDAT");
				deldat = deldat.substring(0, 4) + "-" +
						 deldat.substring(4).substring(0, 2) + "-" + deldat.substring(6);

				// 현재날짜 포맷(-)만들기
				String date = DateTime.getDateString();
				date = date.substring(0, 4) + "-" +
					   date.substring(4).substring(0, 2) + "-" + date.substring(6);

				// 현재시간 포맷(:)만들기
				String time = DateTime.getShortTimeString();
				time = time.substring(0, 2) + ":" +
					   time.substring(2).substring(0, 2) + ":" + time.substring(4);

				// 수주계획번호 -> 이월 시 신규로 채번된 수주계획번호
				String sonnr = sonnrList.get(0).getNumber();

				dsZSDT1001.setColumn(nRow, "CDATE", date); // 생성일자
				dsZSDT1001.setColumn(nRow, "CTIME", time); // 생성시간
				dsZSDT1001.setColumn(nRow, "CUSER", paramVO.getVariable("G_USER_ID")); // 생성자

				dsZSDT0014.setColumn(nRow, "CDATE", dsZSDT1001.getColumn(nRow, "CDATE")); // 생성일자
				dsZSDT0014.setColumn(nRow, "CTIME", dsZSDT1001.getColumn(nRow, "CTIME")); // 생성시간
				dsZSDT0014.setColumn(nRow, "CUSER", dsZSDT1001.getColumn(nRow, "CUSER")); // 생성자

				String zmpflg = " ";
				dsZSDT1001.setColumn(nRow, "ZMPFLG", zmpflg);	// 이동계획구분
				dsZSDT1001.setColumn(nRow, "ZDELI",  dsInput2.getColumnAsString(i, "ZDELI"));	// 단납기구분
				dsZSDT1001.setColumn(nRow, "ZINTER", dsInput2.getColumnAsString(i, "ZINTER"));	// 중계무역구분

				// 0014에 없는 필드임
				// dsZSDT0014.setColumn(nRow, "ZMPFLG",
				// dsZSDT1001.getColumn(nRow, "ZMPFLG") ); // 이동계획구분
				dsZSDT0014.setColumn(nRow, "ZDELI",  dsZSDT1001.getColumn(nRow, "ZDELI")); // 단납기구분
				dsZSDT0014.setColumn(nRow, "ZINTER", dsZSDT1001.getColumn(nRow, "ZINTER")); // 중계무역구분(Y)

				dsZSDT1001.setColumn(nRow, "SONNR", sonnr); // 수주계획번호
				dsZSDT0014.setColumn(nRow, "SONNR", dsZSDT1001.getColumn(nRow, "SONNR")); // 수주계획번호
				dsZSDT0014.setColumn(nRow, "PSPID", dsZSDT1001.getColumn(nRow, "SONNR")); // 프로젝트번호

				dsZSDT1001.setColumn(nRow, "MANDT", paramVO.getVariable("G_MANDT")); // 클라이언트
				dsZSDT0014.setColumn(nRow, "MANDT", dsZSDT1001.getColumn(nRow, "MANDT")); // 클라이언트

				dsZSDT1001.setColumn(nRow, "ZPYM",  dsInput.getColumnAsString(0, "NEWZPYM")); // 계획년월
				dsZSDT0014.setColumn(nRow, "BIDYM", dsZSDT1001.getColumn(nRow, "ZPYM")); // 계획년월

				dsZSDT1001.setColumn(nRow, "SPART", dsInput2.getColumnAsString(i, "SPART")); // 제품군
				dsZSDT0014.setColumn(nRow, "SPART", dsZSDT1001.getColumn(nRow, "SPART")); // 제품군

				dsZSDT1001.setColumn(nRow, "AUART", dsInput2.getColumnAsString(i, "AUART")); // 판매문서유형
				dsZSDT0014.setColumn(nRow, "AUART", dsZSDT1001.getColumn(nRow, "AUART")); // 판매문서유형

				dsZSDT1001.setColumn(nRow, "MATNR", matnr); // 자재번호
				dsZSDT0014.setColumn(nRow, "MATNR", dsZSDT1001.getColumn(nRow, "MATNR")); // 자재번호

				dsZSDT1001.setColumn(nRow, "VKBUR", dsInput2.getColumnAsString(i, "VKBUR")); // 부서
				dsZSDT0014.setColumn(nRow, "VKBUR", dsZSDT1001.getColumn(nRow, "VKBUR")); // 부서

				dsZSDT1001.setColumn(nRow, "VKGRP", dsInput2.getColumnAsString(i, "VKGRP")); // 팀
				dsZSDT0014.setColumn(nRow, "VKGRP", dsZSDT1001.getColumn(nRow, "VKGRP")); // 팀

				dsZSDT1001.setColumn(nRow, "ZKUNNR", dsInput2.getColumnAsString(i, "ZKUNNR")); // 영업담당자
				dsZSDT0014.setColumn(nRow, "ZKUNNR", dsZSDT1001.getColumn(nRow, "ZKUNNR")); // 영업담당자

				dsZSDT1001.setColumn(nRow, "GTYPE", gtype); // 대표기종
				dsZSDT0014.setColumn(nRow, "GTYPE", dsZSDT1001.getColumn(nRow, "GTYPE")); // 대표기종

				dsZSDT1001.setColumn(nRow, "RTYPE",     dsInput2.getColumnAsString(i, "RTYPE")); // 실기
				dsZSDT0014.setColumn(nRow, "GTYPE_OLD", dsZSDT1001.getColumn(nRow, "RTYPE")); // 실기종 GTYPE_OLD->RTYPE

				dsZSDT1001.setColumn(nRow, "TYPE1",  dsInput2.getColumnAsString(i, "TYPE1")); // 타입1
				dsZSDT0014.setColumn(nRow, "GSPEC1", dsZSDT1001.getColumn(nRow, "TYPE1")); // 스펙1

				dsZSDT1001.setColumn(nRow, "TYPE2",  dsInput2.getColumnAsString(i, "TYPE2")); // 타입2
				dsZSDT0014.setColumn(nRow, "GSPEC2", dsZSDT1001.getColumn(nRow, "TYPE2")); // 스펙2

				dsZSDT1001.setColumn(nRow, "TYPE3",  dsInput2.getColumnAsString(i, "TYPE3")); // 타입3
				dsZSDT0014.setColumn(nRow, "GSPEC3", dsZSDT1001.getColumn(nRow, "TYPE3")); // 스펙3

				dsZSDT1001.setColumn(nRow, "ZNUMBER", dsInput2.getColumnAsDouble(i, "ZNUMBER")); // 대수
				dsZSDT0014.setColumn(nRow, "ZNUMBER", dsZSDT1001.getColumn(nRow, "ZNUMBER")); // 대수

				dsZSDT1001.setColumn(nRow, "KUNNR", dsInput2.getColumnAsString(i, "KUNNR")); // 고객
				dsZSDT0014.setColumn(nRow, "KUNNR", dsZSDT1001.getColumn(nRow, "KUNNR")); // 고객

				dsZSDT1001.setColumn(nRow, "ZAGNT", dsInput2.getColumnAsString(i, "ZAGNT")); // 대리점
				dsZSDT0014.setColumn(nRow, "ZAGNT", dsZSDT1001.getColumn(nRow, "ZAGNT")); // 대리점

				dsZSDT1001.setColumn(nRow, "LAND1", dsInput2.getColumnAsString(i, "LAND1")); // 국가
				dsZSDT0014.setColumn(nRow, "LAND1", dsZSDT1001.getColumn(nRow, "LAND1")); // 국가

				dsZSDT1001.setColumn(nRow, "GSNAM", dsInput2.getColumnAsString(i, "GSNAM")); // 현장명
				dsZSDT0014.setColumn(nRow, "GSNAM", dsZSDT1001.getColumn(nRow, "GSNAM")); // 현장명

				dsZSDT1001.setColumn(nRow, "REGION", dsInput2.getColumnAsString(i, "REGION")); // 설치지역
				dsZSDT0014.setColumn(nRow, "REGION", dsZSDT1001.getColumn(nRow, "REGION")); // 설치지역

				dsZSDT1001.setColumn(nRow, "SHANGH", dsInput2.getColumnAsString(i, "SHANGH")); // 국내/상해구분
				dsZSDT0014.setColumn(nRow, "SHANGH", dsZSDT1001.getColumn(nRow, "SHANGH")); // 국내/상해구분

				dsZSDT1001.setColumn(nRow, "SOFOCA", dsInput2.getColumnAsString(i, "SOFOCA"));// 수주예상액

				// 수주예상액
				double tempDbl = 0;
				if (DatasetUtility.getDouble(dsInput2, i, "SOFOCA") == 0) {
					dsZSDT0014.setColumn(nRow, "SOFOCA", 0);
				} else {
					// 통화(WAERK)단위에 따라 환산금액 계산
					if (   "KRW".equals(DatasetUtility.getString(dsInput2, i, "WAERK"))
						|| "JPY".equals(DatasetUtility.getString(dsInput2, i, "WAERK"))) {

						tempDbl = DatasetUtility.getDouble(dsInput2, i, "SOFOCA") / 100.0;

					} else {
						tempDbl = DatasetUtility.getDouble(dsInput2, i, "SOFOCA");
					}

					dsZSDT0014.setColumn(nRow, "SOFOCA", tempDbl);
				}

				dsZSDT1001.setColumn(nRow, "ZFORE", DatasetUtility.getString(dsInput2, i, "ZFORE")); // 예상시행율
				dsZSDT0014.setColumn(nRow, "ZFORE", dsZSDT1001.getColumn(nRow, "ZFORE")); // 예상시행율

				dsZSDT1001.setColumn(nRow, "WAERK", DatasetUtility.getString(dsInput2, i, "WAERK")); // 통화
				dsZSDT0014.setColumn(nRow, "WAERK", dsZSDT1001.getColumn(nRow, "WAERK")); // 통화

				dsZSDT1001.setColumn(nRow, "DELDAT", deldat); // 납기예정일
				dsZSDT0014.setColumn(nRow, "DELDAT", dsZSDT1001.getColumn(nRow, "DELDAT")); // 납기예정일

				dsZSDT1001.setColumn(nRow, "ZRMK", DatasetUtility.getString(dsInput2, i, "ZRMK")); // 비고

				dsZSDT1001.setColumn(nRow, "SOABLE", DatasetUtility.getString(dsInput2, i, "SOABLE")); // 수주가능성
				dsZSDT0014.setColumn(nRow, "SOABLE", dsZSDT1001.getColumn(nRow, "SOABLE")); // 수주가능성

				dsZSDT1001.setColumn(nRow, "SORLT", DatasetUtility.getString(dsInput, 0, "PRESORLT")); // 수주결과(이월전상태)
				dsZSDT0014.setColumn(nRow, "SORLT", dsZSDT1001.getColumn(nRow, "SORLT")); // 수주결과

				// 이전수주계획 번호 mapping
				dsZSDT1001.setColumn(nRow, "SONNRB", DatasetUtility.getString(dsInput, 0, "SONNR")); // 이전수주계획번호

				// 이동계획마감여부
				String zclflg = "이월";
				dsZSDT1001.setColumn(nRow, "PGUBN", zclflg); // 계획구분
				// 0014에 없는 필드
				// dsZSDT0014.setColumn(nRow, "PGUBN", zclflg ); // 계획구분

				dsZSDT1001.setColumn(nRow, "SOPRICE", DatasetUtility.getDouble(dsInput2, i, "SOPRICE"));// 수주금액

				// 수주금액
				tempDbl = 0;
				if (DatasetUtility.getDouble(dsInput2, i, "SOPRICE") == 0) {
					dsZSDT0014.setColumn(nRow, "SOPRICE", 0);
				} else {
					// 통화(WAERK)단위에 따라 환산금액 계산
					if (    "KRW".equals(DatasetUtility.getString(dsInput2, i, "WAERK"))
						 || "JPY".equals(DatasetUtility.getString(dsInput2, i, "WAERK"))) {
						tempDbl = DatasetUtility.getDouble(dsInput, i, "SOPRICE") / 100.0;
					} else {
						tempDbl = DatasetUtility.getDouble(dsInput, i, "SOPRICE");
					}
					dsZSDT0014.setColumn(nRow, "SOPRICE", tempDbl + "");
				}

				dsZSDT1001.setColumn(nRow, "SHANG", DatasetUtility.getString(dsInput2, i, "SHANG")); // 당사시행율
				dsZSDT0014.setColumn(nRow, "SHANG", dsZSDT1001.getColumn(nRow, "SHANG")); // 당사시행율

				dsZSDT1001.setColumn(nRow, "VBELN", DatasetUtility.getString(dsInput2, i, "VBELN")); // 수주번호
				dsZSDT0014.setColumn(nRow, "VBELN", dsZSDT1001.getColumn(nRow, "VBELN")); // 수주번호

				dsZSDT0014.setColumn(nRow, "POSID", f_posid_make(sonnr, matnr)); // 호기번호
				dsZSDT0014.setColumn(nRow, "BIDDAT", biddat); // 입찰예정일
				dsZSDT0014.setColumn(nRow, "NAME1", DatasetUtility.getString(dsInput2, i, "KUNNR_NM"));// 고객명

				dsZSDT1001.setColumn(nRow, "ZBPNNR", DatasetUtility.getString(dsInput2, i, "ZBPNNR")); // 사업계획번호
				dsZSDT0014.setColumn(nRow, "ZBPNNR", dsZSDT1001.getColumn(nRow, "ZBPNNR")); // 사업계획번호

				dsZSDT1001.setColumn(nRow, "ZCOST", DatasetUtility.getString(dsInput2, i, "ZCOST")); // 원가
				dsZSDT0014.setColumn(nRow, "ZCOST", dsZSDT1001.getColumn(nRow, "ZCOST")); // 원가

				dsZSDT0014.setColumn(nRow, "ENTP3", "");

				nRow++;
			}// end for

			// 매출/청구/수금 산출 RFC
			listZSDT0014 = (ZSDT0014[]) SmpComC.moveDs2Obj(dsZSDT0014, ZSDT0014.class, "");
			listZSDT1001 = (ZSDT1001[]) SmpComC.moveDs2Obj(dsZSDT1001, ZSDT1001.class, "");

			// 원가산출 RFC
			// 원가는 태우지 않음. - 송인덕 차장(2012.10.16)
			// listZCOBS001 = (ZCOBS001[])SmpComC.moveDs2Obj(dsZCOBS001,
			// ZCOBS001.class, "");
			// logger.info(" @@@@@@ save 2 : go ");
			// save2( paramVO, listZCOBS001, model );

			// 리스트가 없는 상태에서 addrow후 저장시
			// 매출/청구/수금에 대한 데이터가 존재하지 않으므로 그에대한 처리를 한다.
			// RFC FUCNTION 호출
			obj = new Object[] {
					  "S" // 보수/영업 구분
					, "" // delete구분(수주계획에선 사용안함)
					, "M" // 사업/수주 구분
					, "" // 특정필드(TYPE1,TYPE2,TYPE3,RTYPE,SOFOCA) 변경구분(수주계획에서 사용하지 않음)
					, listMsg
					, listZSDT0014
					, listZSDT1001 // 수주계획
					, listZSDT1005
					, listZSDT0014S
					, listZSDT1012
					, listZSDT1023
					, listZSDS0072
			};

			// logger.info(" @@@@@@ SapFunction stub" );
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sbp.domain.ZWEB_SD_PLAN_COMPUTE", obj, false);

			listMsg = (ZQMSEMSG[]) stub.getOutput("O_ETAB");

			// 오류메시지가 return될 경우
			if (listMsg.length != 0)
			{
				if ("4".equals(listMsg[0].getIDX().toString()))
				{
					throw new BizException("CE00001");
				}
			}

			sbp0162D.updateSORLT(param);

			// 2012.11.13 종결처리 이전상태에 따른 견적정보의 수주계획번호 연계
			if ( param.getPRESORLT() != null &&
				 ( "20".equals(param.getPRESORLT()) || "50".equals(param.getPRESORLT()) )  )	{	// 견적작성, 입찰상태
				param.setNEWSORLT(param.getPRESORLT());	// 이월상태
				sbp0162D.updateZSDRT1047Sonnr(param);
			}

			// 2012.11.16 RFC호출 형식으로 변경
			/*
			sbp0162D.insertSORLT1(param);
			sbp0162D.insertSORLT2(param);
			sbp0162D.insertSORLT3(param);
			sbp0162D.insertSORLT4(param);
			 */
		}catch (BizException be) {
			be.printStackTrace();
			throw be;
		} catch (Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 사업계획번호, 자재번호로 호기번호 make하여 return
	 *
	 * @param sonnr
	 * @param matnr
	 * @return
	 */
	private String f_posid_make(String sonnr, String matnr) {
		// 호기번호
		String posid = "";
		if ("NS-100".equals(matnr)) {
			posid = sonnr + "NS";
		} else {
			posid = sonnr + StringUtils.substring(matnr, 0, 1) + "01";
		}
		return posid;
	}
}
