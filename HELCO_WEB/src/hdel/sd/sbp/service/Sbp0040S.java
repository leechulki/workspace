package hdel.sd.sbp.service;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.com.service.Com0040S;
import hdel.sd.com.domain.Com0040;
import hdel.sd.com.domain.Com0040ParamVO;
import hdel.sd.sbp.dao.Sbp0040D;
import hdel.sd.sbp.domain.Sbp0010;
import hdel.sd.sbp.domain.Sbp0010ParamVO;
import hdel.sd.sbp.domain.Sbp0040;
import hdel.sd.sbp.domain.Sbp0040ParamVO;
import hdel.sd.sbp.domain.Sbp0040;
import hdel.sd.sbp.domain.Sbp0040ParamVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.service.resource.TransactionManager;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * 사업계획관리(보수)(Sbp0040S) Service
 * @Comment
 *     	1.  void createDao
 *		2.  List selectOpenDtm  	( 사업계획(보수) 오픈일시(14자리) 조회 )
 *		3.  List selectList 		( 사업계획(보수) 목록 조회 )
 *		4.  List selectListDetail	( 사업계획번호 한 건에 등록된 사업계획(보수) 매출/청구/수금목록 조회 )
 *		5.  void save 				( 수주 저장 )
 *		6.  void bill_save 		( 매출/청구/수금 저장 )
 *
 * @since 1.0
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version
 * @version 1.0
 */


@Service
public class Sbp0040S {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

//	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Com0040S 		BuyrService ;	// 거래선조회 서비스

	private Sbp0040D sbp0040Dao;

	public void createDao(SqlSession sqlSession) {
		sbp0040Dao = sqlSession.getMapper(Sbp0040D.class);
	}

	// 사업계획 오픈일시(14자리) 조회
	public List<Sbp0040> selectOpenDtm(Sbp0040ParamVO param) {
		return sbp0040Dao.selectOpenDtm(param);
	}

	// 사업계획 목록 조회
	public List<Sbp0040> selectList(Sbp0040ParamVO param) {
		return sbp0040Dao.selectList(param);
	}

	// 보수프로젝트번호의 수주예상액SUM
	public Double selectSumSofoca(Sbp0040ParamVO param) {
		return sbp0040Dao.selectSumSofoca(param);
	}

	// 사업계획번호에 한 건에 등록된 사업계획 매출/청구/수금목록 조회
	public List<Sbp0040> selectListDetail(Sbp0040ParamVO param) {
		return sbp0040Dao.selectListDetail(param);
	}

	// 사업계획 수주예상액, 통화 조회 : 매출/청구/수금 자동산출 대상 변경조건
	public List<Sbp0040> selectListComputeItem(Sbp0040ParamVO param) {
		return sbp0040Dao.selectListComputeItem(param);
	}

	// 사업계획_보수 저장
	public void save( Dataset 	ds_detail  		// 사업계획상세
					, Dataset 	ds_list_bill  // 매출/청구/수금 저장 대상정보(예상금액)
					, Dataset 	ds_yearm  		// 매출/청구/수금 저장 대상정보(30개월치 예상년월)
					, String 	flag			// 저장구분(I,U,D)
					, String	sysid
					, String 	mandt			// CLIENT
					, String 	user_id			// 사용자ID
					, String 	zbpnnr			// 사업계획번호
					, String	pspidsv			// 보수프로젝트번호
					, String 	call_sap_yn		// 매출/청구/수금 산출 SAP함수 호출여부(SAP용)
					, String    auto_compute_yn	// 매출/청구/수금 자동산출 대상여부(WEB용)
					, String 	posid			// 호기
					)
	{

		logger.debug("@@@ Sbp0040S.save START!!!" + "");

		String ErrCode = "CE00001";  // "처리에 실패했습니다.확인 후 다시 처리해 주십시오."

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO
		// sap client (개발 : 310 or 910)
		logger.debug("@@@ mandt 	: " + mandt);
		logger.debug("@@@ user_id 	: " + user_id);
		logger.debug("@@@ ds_detail.getRowCount   ===>"+ds_detail.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_detail.getRowCount(); irow++ )
		{
			for( int icol = 0; icol < ds_detail.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_detail["+irow+"Record : "+ds_detail.getColumnID(icol)+"] ===>"
								+ DatasetUtility.getString(ds_detail, irow, ds_detail.getColumnID(icol)));
			}
		}
		logger.debug("@@@ ds_yearm.getRowCount ===>"+ds_yearm.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_yearm.getRowCount(); irow++ )
		{
			for( int icol = 0; icol < ds_yearm.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_yearm["+irow+"Record : "+ds_yearm.getColumnID(icol)+"] ===>"
								+ DatasetUtility.getString(ds_yearm, irow, ds_yearm.getColumnID(icol)));
			}
		}
		logger.debug("@@@ ds_list_bill.getRowCount ===>"+ds_list_bill.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_list_bill.getRowCount(); irow++ )
		{
			for( int icol = 0; icol < ds_list_bill.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_list_bill["+irow+"Record : "+ds_list_bill.getColumnID(icol)+"] ===>"
								+ DatasetUtility.getString(ds_list_bill, irow, ds_list_bill.getColumnID(icol)));
			}
		}
		//--------------------------------------------------------------------------------------------

		logger.debug("@@@ Sbp0040SaveView start" + "");

		//------------------------------------------------------------------------
		// 변수 선언
		//------------------------------------------------------------------------
		Sbp0040[] 	param 		= null;						// 수주 저장 파라메터
		Sbp0040 	paramDetail = null;						// 매출/청구/수금 저장 파라메터
		String 		auart		= "";						// 판매문서유형(오더유형)
		String		zkunnr		= "";						// 담당자코드
		String		kunnr		= "";						// 거래선코드
		String		zpyear		= "";						// 오픈일시
		String 		gbn 		= "";						// 매출/청구/수금 구분	(1:매출, 2:청구, 3:수금)
		BigDecimal 	amt 		= null;						// 매출/청구/수금 금액
		BigDecimal 	zero 		= new BigDecimal("0.00");	// 0.00
		String 		bill_save_yn= "Y"; 						// 매출/청구/수금 자료 저장SKIP여부

		//------------------------------------------------------------------------
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(sysid);
		//------------------------------------------------------------------------

		//------------------------------------------------------------------------
		// DAO 생성
		//------------------------------------------------------------------------
		createDao(session);

		//------------------------------------------------------------------------
		// 오픈일시 존재여부 체크 (오픈일시 미존재시 저장불가처리)
		//------------------------------------------------------------------------
		zpyear	= DatasetUtility.getString(ds_detail, 0, "ZPYEAR");	// 편성년도
		if (chkOpenDtm(session, mandt, zpyear)==false)
		{
			// "CE10002", "오픈되지 않은 편성년도의 사업계획입니다.\n다시 한번 확인 바랍니다."
			throw new BizException("CE10002");
		}

		//------------------------------------------------------------------------
		// 사업계획번호별 처리 START
		//------------------------------------------------------------------------
		for( int irow = 0; irow < ds_detail.getRowCount(); irow++ )
		{

			ds_detail.setColumn(irow, "USER_ID"	,user_id); 	// WEB USER ID
			for( int icol = 0; icol < ds_detail.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_detail["+irow+"Record : "
									+ ds_detail.getColumnID(icol)+"] ===>"
									+ DatasetUtility.getString(ds_detail, irow, ds_detail.getColumnID(icol))
									);
			}

			auart		= DatasetUtility.getString(ds_detail, irow, "AUART");		// 판매문서유형(오더유형)
			kunnr		= DatasetUtility.getString(ds_detail, irow, "KUNNR");		// 거래선코드
			bill_save_yn= DatasetUtility.getString(ds_detail, irow, "BILL_SAVE_YN");// 매출/청구/수금 자료 저장여부

			logger.debug("@@@ bill_save_yn : " + bill_save_yn);

			//------------------------------------------------------------------------
			// 신규 사업계획인 경우
			//------------------------------------------------------------------------
			if ("I".equals(flag))
			{

				// 0.거래선코드 유효성 체크
				if (chkBuyrCd(session, mandt, kunnr)==false)
				{
					// "CE10001", "거래선코드가 유효하지 않습니다.\n다시 한번 확인 바랍니다."
					throw new BizException("CE10001");
				}

				// 1.사업계획번호 GET (계획구분별(사업계획:0), 판매문서유형별 채번)
				logger.debug("@@@ 1.사업계획번호 GET ");
				logger.debug("@@@ getNewZbpnnr.zbpnnr : " + zbpnnr + "");

				// 2.사업계획번호 SET
				logger.debug("@@@ 2.사업계획번호 SET ");
				ds_detail.setColumn(irow, "ZBPNNR"	, zbpnnr);

				// 3.수주 Input Dataset(ds_detail) --> class(Sbp0040)로 copy
				logger.debug("@@@ 3.수주 Input Dataset(ds_detail) --> class(Sbp0040)로 copy  ");
				param = (Sbp0040[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0040.class, "", null);

				// 4.수주등록 서비스CALL
				logger.debug("@@@ 4.수주등록 서비스CALL  ");
				sbp0040Dao.insertZSDT1023(param[0]);

				// 5. SAP용 수주계획 등록(ZSDT1012==>ZSDT0014S )
				logger.debug("@@@ 5. SAP용 수주계획 등록(ZSDT1012==>ZSDT0014S )");
				save_0014S( session
					        , flag				// 저장구분
							, ds_detail			// 수주정보
							, mandt				// CLIENT
							, user_id 			// WEB USER_ID
							, zbpnnr 			// 사업계획번호
							, posid				// 호기
							);

				// 6.매출/청구/수금등록 서비스CALL
				logger.debug("@@@  6.매출/청구/수금등록 서비스CALL   ");
				bill_save(    ds_detail			// 사업계획정보
							, ds_list_bill	// 매출/청구/수금 예상금액
							, ds_yearm			// 매출/청구/수금 예상년월
							, mandt				// CLIENT
							, user_id 			// WEB USER_ID
							, auto_compute_yn	// 자동산출 대상여부 조회
							);

				// 7. 신규 사업계획인 경우 사업계획저장상태는  SAP FUNCTION 호출 후 저장함(SAVE_STAT=Z)
				logger.debug("@@@ 7. 신규 사업계획인 경우 사업계획저장상태는  SAP FUNCTION 호출 후 저장함(SAVE_STAT=Z)  ");

			}

			//------------------------------------------------------------------------
			// 수정인 경우
			//------------------------------------------------------------------------
			else if ("U".equals(DatasetUtility.getString(ds_detail, irow, "FLAG")))
			{

				// 0.거래선코드 유효성 체크
				if (chkBuyrCd(session, mandt, kunnr)==false)
				{
					// "CE10001", "거래선코드가 유효하지 않습니다.\n다시 한번 확인 바랍니다."
					throw new BizException("CE10001");
				}

				// 1.사업계획번호 GET
				logger.debug("@@@ 1.사업계획번호 GET ");
				zbpnnr = DatasetUtility.getString(ds_detail, irow, "ZBPNNR");

				// 2.수주 Input Dataset(ds_detail) --> class(Sbp0040)로 copy
				logger.debug("@@@ 2.수주 Input Dataset(ds_detail) --> class(Sbp0040)로 copy ");
				param  = (Sbp0040[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0040.class, "", null);

				// 3.수주변경 서비스CALL
				logger.debug("@@@ 3.수주변경 서비스CALL ");
				sbp0040Dao.updateZSDT1023(param[0]);

				// 4. SAP용 수주계획 변경(ZSDT1023==>ZSDT0014S )
				logger.debug("@@@ 4. SAP용 수주계획 변경(ZSDT1023==>ZSDT0014S )");
				save_0014S(    session
					        , flag				// 저장구분
							, ds_detail			// 수주정보
							, mandt				// CLIENT
							, user_id 			// WEB USER_ID
							, zbpnnr 			// 사업계획번호
							, posid				// 호기
							);

				// 5. 매출/청구/수금등록 서비스CALL
				logger.debug("@@@ 5.4 매출/청구/수금등록 서비스CALL ");
				bill_save(    ds_detail			// 사업계획정보
							, ds_list_bill		// 매출/청구/수금 예상금액
							, ds_yearm			// 매출/청구/수금 예상년월
							, mandt				// CLIENT
							, user_id 			// WEB USER_ID
							, auto_compute_yn	// 자동산출 대상여부 조회
							);

				// 6. 매출/청구/수금 산출 대상이 아닌 경우 사업계획상태 종료 처리(SAVE_STAT=Z)
				if ("N".equals(call_sap_yn))
				{
					logger.debug("@@@ 6. 매출/청구/수금 산출 대상이 아닌 경우 사업계획상태 종료 처리(SAVE_STAT=Z)");
					updateSaveStat(mandt,zbpnnr,user_id, "Z");   // 정상종료처리
				}

			}

			//------------------------------------------------------------------------
			// 삭제인 경우
			//------------------------------------------------------------------------
			else if ("D".equals(DatasetUtility.getString(ds_detail, irow, "FLAG")))
			{

				// 1.사업계획번호 GET
				logger.debug("@@@ 1.사업계획번호 GET ");
				zbpnnr = DatasetUtility.getString(ds_detail, irow, "ZBPNNR");

				// 2.수주 Input Dataset(ds_detail) --> class(Sbp0040)로 copy
				logger.debug("@@@ 2.수주 Input Dataset(ds_detail) --> class(Sbp0040)로 copy ");
				param = (Sbp0040[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0040.class, "", null);

				// 3.수주삭제 서비스CALL : Update 삭제정보
				logger.debug("@@@ 3.수주삭제 서비스CALL : Update 삭제정보 ");
				sbp0040Dao.deleteZSDT1023(param[0]);

				// 4. SAP용 수주삭제 서비스CALL : Update 삭제정보
				logger.debug("@@@ 4. SAP용 수주삭제 서비스CALL : Update 삭제정보 ");
				sbp0040Dao.deleteZSDT0014S(param[0]);

				// 5. 사업계획상태 종료 처리(SAVE_STAT=Z)
				logger.debug("@@@ 5. 사업계획상태 종료 처리(SAVE_STAT=Z)");
				updateSaveStat(mandt,zbpnnr,user_id, "Z");   // 정상종료처리

			}  // end of else if ("D".equals(DatasetUtility.getString(ds_detail, irow, "FLAG")))


		} // end of for

		logger.debug("@@@ Sbp0040S.save SUCCESS!!!" + "");

	}

	// 매출/청구/수금 등록
	public void bill_save(   Dataset ds_detail			// 사업계획정보
							,Dataset ds_list_bill		// 화면에서 입력받은 매출/청구/수금  정보
							,Dataset ds_yearm			// 예상년월30개월
							,String  mandt				// CLIENT
							,String  user_id 			// WEB USER_ID
							,String  auto_compute_yn	// 자동산출 대상여부 조회
							)
	{

		logger.debug("@@@ Sbp0040SaveView.bill_save START!!!" + "");

		// 1.수주 Input Dataset(ds_detail) --> class(Sbp0040)로 copy
		logger.debug("@@@ 1. 수주 Input Dataset(ds_detail) --> class(Sbp0040)로 copy ");
		Sbp0040[] param = (Sbp0040[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0040.class, "", null);

		// 2.매출삭제 서비스CALL  (사업계획번호 한건당 삭제)
		logger.debug("@@@ 2. 매출삭제 서비스CALL  (사업계획번호 한건당 삭제) ");
		sbp0040Dao.deleteZSDT1024(param[0]);

		// 3.청구삭제 서비스CALL  (사업계획번호 한건당 삭제)
		logger.debug("@@@ 3. 청구삭제 서비스CALL  (사업계획번호 한건당 삭제) ");
		sbp0040Dao.deleteZSDT1025(param[0]);

		// 4.수금삭제 서비스CALL  (사업계획번호 한건당 삭제)
		logger.debug("@@@ 4. 수금삭제 서비스CALL  (사업계획번호 한건당 삭제) ");
		sbp0040Dao.deleteZSDT1026(param[0]);

		// 5.매출/청구/수금 등록
		logger.debug("@@@ 5.매출/청구/수금 등록 ");
		String  flag	= ds_detail.getColumnAsString(0, "FLAG");		// FLAG
		String  zbpnnr 	= ds_detail.getColumnAsString(0, "ZBPNNR");		// 사업계획번호
		String  pspidsv	= ds_detail.getColumnAsString(0, "PSPIDSV");	// 보수프로젝트번호
		String  auart	= ds_detail.getColumnAsString(0, "AUART");		// 오더유형(판매문서유형)
		String  zsvcgbn	= ds_detail.getColumnAsString(0, "ZSVCGBN");	// 보수구분
		Double  sofoca  = ds_detail.getColumnAsDouble(0, "SOFOCA");		// 수주예상액

		logger.debug("@@@ Sbp0040SaveView.bill_save.auto_compute_yn	[" + auto_compute_yn +  "] ");

		// 자동산출 대상인 경우
		if ("Y".equals(auto_compute_yn))
		{

			logger.debug("@@@ Sbp0040SaveView.bill_save.자동산출 대상인 경우 ");

			// 정기보수(오더유형=="ZR11)인 경우
			if ("ZR11".equals(auart))
			{
				logger.debug("@@@ Sbp0040SaveView.bill_save.정기보수(오더유형==ZR11)인 경우 ");

				// 보수프로젝트번호가 있는 경우 (보수구분 IN (20:재계약, 30:회수))
				if ("20".equals(zsvcgbn) || "30".equals(zsvcgbn))
				{
					logger.debug("@@@ Sbp0040SaveView.bill_save.보수프로젝트번호가 있는 경우 (보수구분 IN (20:재계약, 20:회수)) ");
					// 보수프로젝트번호에 등록된 SUM(수주예상액)을 조회하여 SUM한 값을 1/12 분할하여
					// 보수프로젝트단위로 매출/청구/수금을 등록한다.
					Sbp0040ParamVO	 paramSum  = new Sbp0040ParamVO();
					paramSum.setMANDT	(mandt);  					// SAP CLIENT
					paramSum.setPSPIDSV (pspidsv);					// 보수프로젝트번호
					sofoca = sbp0040Dao.selectSumSofoca(paramSum);  // 수주예상액SUM 조회
					zbpnnr = pspidsv;								// 사업계획번호 = 보수프로젝트번호로 SETTING
				}

				// 수주예상금액을 계획년월~계획년월+11월 까지 1/12 하여 매출/청구/수금을 등록한다
				logger.debug("@@@ Sbp0040SaveView.bill_save.수주예상금액을 계획년월~계획년월+11월 까지 1/12 하여 매출/청구/수금을 등록한다 ");
				bill_save_12month(    ds_yearm			// 예상년월30개월
									, mandt				// CLIENT
									, user_id 			// WEB USER_ID
									, zbpnnr			// 사업계획번호
									, sofoca			// 수주예상액
									);
			}
			else
			{
				// 사업계획번호의  수주예상금액을 계획년월~계획년월+2월 까지 30%,30%,40%로 분할하여 매출/청구/수금을 등록한다.
				bill_save_3month(    ds_yearm			// 예상년월30개월
									, mandt				// CLIENT
									, user_id 			// WEB USER_ID
									, zbpnnr			// 사업계획번호
									, sofoca			// 수주예상액
									);
			}
		}
		else
		{
			// 화면에서 입력받은 값으로 매출/청구/수금 등록 서비스 호출
			bill_save_input(  ds_list_bill		// 매출/수금정보
							, ds_yearm			// 예상년월30개월
							, mandt				// CLIENT
							, user_id 			// WEB USER_ID
							, zbpnnr			// 사업계획번호
							);
		}


		logger.debug("@@@ Sbp0040SaveView.bill_save SUCCESS !!!" + "");

		/*  아래 소스 정상 처리됨 : 추후 사용 가능
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
		Calendar date = Calendar.getInstance();
		date.set(2013, 11, 02);
		date.add(Calendar.MONTH, +2);
		logger.debug("@@@ sdf.format(date.getTime()) : " + sdf.format(date.getTime()) );
		*/

	}

	// 매출/청구/수금을 3개월로 분할하여 등록
	private void bill_save_3month(   Dataset ds_yearm			// 예상년월30개월
									,String  mandt				// CLIENT
									,String  user_id 			// WEB USER_ID
									,String  zbpnnr				// 사업계획번호
									,Double  sofoca				// 수주예상액
									)
	{

		logger.debug("@@@ bill_save_3month START ");

		// 3 개월로 분할(30%, 30%, 40%) 하고 원단위에서 절사한다.
		Sbp0040	 	paramDetail  = new Sbp0040();
		BigDecimal 	sofoca_div1  = new BigDecimal((sofoca*3)/10);	// 분할 후 금액(계획년월 + 0개월)
		BigDecimal 	sofoca_div2  = new BigDecimal((sofoca*3)/10);	// 분할 후 금액(계획년월 + 1개월)
		BigDecimal 	sofoca_div3  = new BigDecimal((sofoca*4)/10);	// 분할 후 금액(계획년월 + 2개월)

		sofoca_div1	= sofoca_div1.setScale(0, RoundingMode.DOWN);	// 금액을 원단위에서 절사
		sofoca_div2	= sofoca_div2.setScale(0, RoundingMode.DOWN);	// 금액을 원단위에서 절사
		sofoca_div3	= sofoca_div3.setScale(0, RoundingMode.DOWN);	// 금액을 원단위에서 절사

		// 차액 = 수주예상금액-(월별금액*12)
		BigDecimal diff = new BigDecimal(0);
		diff = new BigDecimal(sofoca - (sofoca_div1.doubleValue()
										+ sofoca_div2.doubleValue()
										+ sofoca_div3.doubleValue()));

		logger.debug("@@@ paramDetail.sofoca_div1 [" + sofoca_div1 +  "] ");
		logger.debug("@@@ paramDetail.sofoca_div2 [" + sofoca_div2 +  "] ");
		logger.debug("@@@ paramDetail.sofoca_div3 [" + sofoca_div3 +  "] ");
		logger.debug("@@@ paramDetail.diff 	      [" + diff       +  "] ");

		for( int i = 0; i <= 2; i++ )
		{
			paramDetail.setMANDT	(mandt);  									// SAP CLIENT
			paramDetail.setUSER_ID	(user_id);  								// WEB USER_ID
			paramDetail.setZBPNNR	(zbpnnr);									// 사업계획번호
			paramDetail.setZSAYM	(ds_yearm.getColumnAsString(i, "YEARM"));	// 예상년월
			// 예상금액
			if (i==0) {
				paramDetail.setNETWR_SA (new BigDecimal(sofoca_div1.doubleValue()));
			} else if (i==1) {
				paramDetail.setNETWR_SA (new BigDecimal(sofoca_div2.doubleValue()));
			} else if (i==2) {
				paramDetail.setNETWR_SA (new BigDecimal(sofoca_div3.doubleValue() + diff.doubleValue()));
			}

			logger.debug("@@@ paramDetail.i 	[" + i 		+  "] ");
			logger.debug("@@@ paramDetail.mandt 	[" + mandt 		+  "] ");
			logger.debug("@@@ paramDetail.user_id 	[" + user_id 	+  "] ");
			logger.debug("@@@ paramDetail.zbpnnr 	[" + zbpnnr 	+  "] ");
			logger.debug("@@@ paramDetail.yearm 	[" + paramDetail.getZSAYM()+  "] ");
			logger.debug("@@@ paramDetail.amt 		[" + paramDetail.getNETWR_SA()	+  "] ");

			// 등록서비스 CALL
			sbp0040Dao.insertZSDT1024(paramDetail);  // 매출등록 서비스CALL
			sbp0040Dao.insertZSDT1025(paramDetail);  // 청구등록 서비스CALL
			sbp0040Dao.insertZSDT1026(paramDetail);  // 수금등록 서비스CALL
		}

		logger.debug("@@@ bill_save_3month SUCCESS ");

	}

	// 매출/청구/수금을 12개월로 분할하여 등록
	private void bill_save_12month(  Dataset ds_yearm			// 예상년월30개월
									,String  mandt				// CLIENT
									,String  user_id 			// WEB USER_ID
									,String  zbpnnr				// 사업계획번호
									,Double  sofoca				// 수주예상액
									)
	{

		logger.debug("@@@ bill_save_12month START ");

		// 12 개월로 분할(1/n)하고 원단위에서 절사한다.
		Sbp0040	 	paramDetail  = new Sbp0040();
		BigDecimal 	sofoca_div  = new BigDecimal(sofoca/12);		// 분할 후 금액
		sofoca_div	= sofoca_div.setScale(0, RoundingMode.DOWN);	// 금액을 원단위에서 절사

		// 차액 = 수주예상금액-(월별금액*12)
		BigDecimal diff = new BigDecimal(0);
		diff = new BigDecimal(sofoca - (sofoca_div.doubleValue() * 12));
		int row = 0;

		for( int i = 0; i <= 11; i++ )
		{

			logger.debug("@@@ paramDetail.sofoca_div 	[" + sofoca_div +  "] ");
			logger.debug("@@@ paramDetail.diff 	[" + diff +  "] ");

		    paramDetail.setMANDT	(mandt);  									// SAP CLIENT
			paramDetail.setUSER_ID	(user_id);  								// WEB USER_ID
			paramDetail.setZBPNNR	(zbpnnr);									// 사업계획번호
			paramDetail.setZSAYM	(ds_yearm.getColumnAsString(i, "YEARM"));	// 예상년월
			// 예상금액
			if (i == 11) {
				paramDetail.setNETWR_SA (new BigDecimal(sofoca_div.doubleValue() + diff.doubleValue()));
			} else {
				paramDetail.setNETWR_SA (new BigDecimal(sofoca_div.doubleValue()));
			}

			logger.debug("@@@ paramDetail.i 	[" + i 		+  "] ");
			logger.debug("@@@ paramDetail.mandt 	[" + mandt 		+  "] ");
			logger.debug("@@@ paramDetail.user_id 	[" + user_id 	+  "] ");
			logger.debug("@@@ paramDetail.zbpnnr 	[" + zbpnnr 	+  "] ");
			logger.debug("@@@ paramDetail.yearm 	[" + ds_yearm.getColumnAsString(i, "YEARM") 		+  "] ");
			logger.debug("@@@ paramDetail.amt 		[" + sofoca_div.doubleValue() + diff.doubleValue() 		+  "] ");

			// 등록서비스 CALL
			sbp0040Dao.insertZSDT1024(paramDetail);  // 매출등록 서비스CALL
			sbp0040Dao.insertZSDT1025(paramDetail);  // 청구등록 서비스CALL
			sbp0040Dao.insertZSDT1026(paramDetail);  // 수금등록 서비스CALL
    	}

		logger.debug("@@@ bill_save_12month SUCCESS ");

	}

	// 매출/청구/수금을 화면에서 입력받은 값으로 등록
	private void bill_save_input(  Dataset ds_list_bill		// 매출/수금정보
									, Dataset ds_yearm		// 예상년월30개월
									,String  mandt			// CLIENT
									,String  user_id 		// WEB USER_ID
									,String  zbpnnr			// 사업계획번호
									)
	{

		logger.debug("@@@ bill_save_input START ");

		Sbp0040 	paramDetail = paramDetail = new Sbp0040(); 	// 매출/청구/수금
		String 		gbn 		= null;							// 매출/청구/수금 구분	(1:매출, 2:청구, 3:수금)
		BigDecimal 	amt 		= null;							// 매출/청구/수금 금액
		BigDecimal 	zero 		= new BigDecimal("0.00");		// 0.00
		String      colId       = null;
		String      yearm 		= null;
		int 		row 		= 0;

		for (int iRow1=0;iRow1<ds_list_bill.getRowCount(); iRow1++ )
		{
			gbn = ds_list_bill.getColumnAsString(iRow1, "GBN"); 	// 매출/청구/수금 구분

			for (int iCol1=0;iCol1<ds_list_bill.getColumnCount(); iCol1++ )
			{

				colId = ds_list_bill.getColumnID(iCol1);

				if (!("MANDT".equals(colId)) && !("GBN".equals(colId)) && !("GBN_NM".equals(colId)))
				{

					// 금액항목에 값이 있는 경우에만 실행
					if (StringUtils.isNotBlank(ds_list_bill.getColumnAsString(iRow1, iCol1)))
					{
						// 예상금액
						amt = new BigDecimal(ds_list_bill.getColumnAsDouble(iRow1, iCol1));

						// 금액 > 0 인 경우만 등록
						if( amt.compareTo(zero) == 1 )  // compareTo :::: -1은 작다, 0은 같다, 1은 크다
						{
							row = Integer.parseInt(colId.substring(3,5));
							yearm = ds_yearm.getColumnAsString(row, "YEARM");
							logger.debug("@@@ row   : " + row);
							logger.debug("@@@ yearm : " + yearm);

							paramDetail.setMANDT	(mandt);  		// SAP CLIENT
							paramDetail.setUSER_ID	(user_id);  	// WEB USER_ID
							paramDetail.setZBPNNR	(zbpnnr);		// 사업계획번호
							paramDetail.setZSAYM	(yearm);		// 예상년월
							paramDetail.setNETWR_SA (amt);			// 예상금액

							logger.debug("@@@ paramDetail.mandt 	[" + mandt 		+  "] ");
							logger.debug("@@@ paramDetail.user_id   [" + user_id 	+  "] ");
							logger.debug("@@@ paramDetail.zbpnnr 	[" + zbpnnr 	+  "] ");
							logger.debug("@@@ paramDetail.yearm 	[" + yearm 		+  "] ");
							logger.debug("@@@ paramDetail.amt 	    [" + amt 		+  "] ");

							// 등록서비스 CALL (매출,청구는 같은 값으로 등록한다.)
							if 		 ("1".equals(gbn)) {
								sbp0040Dao.insertZSDT1024(paramDetail);  // 매출등록 서비스CALL
								sbp0040Dao.insertZSDT1025(paramDetail);  // 청구등록 서비스CALL
							}else if ("3".equals(gbn)) {
								sbp0040Dao.insertZSDT1026(paramDetail);  // 수금등록 서비스CALL
							}
						}  	// end of if( amt.compareTo(zero) == 1 )
					}		// end of if (StringUtils.isNotBlank(ds_list_bill.getColumnAsString(iRow1, iCol1)))
				}			//
			}  				// end of for (int iCol1=2;iCol1<=ds_list_bill.getColumnCount(); iCol1++ )
		}					// end of for (int iRow1=0;iRow1<=ds_list_bill.getRowCount(); iRow1++ )
		logger.debug("@@@ bill_save_input SUCCESS ");

	}


	// 오픈일시 존재여부 체크
	public Boolean chkOpenDtm(SqlSession session
					            , String mandt
								, String zpyear		// 편성년도
								)
	{


		logger.debug("@@@ Sbp0040SaveView.chkOpenDtm START!!!" 	+ "");
		logger.debug("@@@ Sbp0040SaveView.chkOpenDtm.mandt" 		+ mandt + "");
		logger.debug("@@@ Sbp0040SaveView.chkOpenDtm.zpyear" 		+ zpyear + "");

		Sbp0040ParamVO param = new Sbp0040ParamVO();

		// 파라메터SET
		param.setMANDT	(mandt);  	// SAP CLIENT
		param.setZPYEAR	(zpyear);	// 편성년도

		// 서비스CALL (사업계획년도에 해당하는 오픈일자 조회)
		List<Sbp0040> list = selectOpenDtm(param);

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

		logger.debug("@@@ sbp0040SaveView.chkBuyrCd START!!!" 	+ "");
		logger.debug("@@@ sbp0040SaveView.chkBuyrCd.mandt" 		+ mandt + "");
		logger.debug("@@@ sbp0040SaveView.chkBuyrCd.kunnr" 		+ kunnr + "");

		Com0040ParamVO param = new Com0040ParamVO();

		// DAO생성
		BuyrService.createDao(session);

		// Parameter SET
		param.setMandt		(mandt);  	// SAP CLIENT
		param.setBuyr_cd	(kunnr);	// 거래선코드

		// 거래선명 조회
		List<Com0040> list = BuyrService.find(param);

		logger.debug("@@@ sbp0040SaveView.chkBuyrCd SUCCESS !!!" + "");

		// 조회된 거래선정보가 없을 경우 오류로 리턴한다.(저장불가처리)
		if (list.size()==0) return false;

		return true;
	}



	// SAP용 사업계획(ZSDT0014S) 테이블에 사업계획 등록(I) 및 수정(U)
	public void save_0014S(SqlSession session
			                    ,String flag		// 저장구분 (I:신규, U:변경)
								,Dataset ds_detail	// 매출/청구/수금 자동산출결과
								,String mandt		// CLIENT
								,String user_id 	// WEB USER_ID
								,String zbpnnr 		// 사업계획번호
								,String posid		// 호기
								)
	{

		logger.debug("@@@ sbp0040SaveView.save_0014S START!!!" + "");

		// 수주 저장 파라메터
		Sbp0040 	param 	= new Sbp0040();

		// 산출변수
		String 		waerk	= DatasetUtility.getString(ds_detail, 0, "WAERK");				// 통화
		String 		kunnr	= DatasetUtility.getString(ds_detail, 0, "KUNNR");				// 거래선코드
		String 		matnr   = DatasetUtility.getString(ds_detail, 0, "MATNR");				// 자재번호
		String		biddat  = DatasetUtility.getString(ds_detail, 0, "ZPYM") + "01";
		BigDecimal 	zfore	= new BigDecimal(DatasetUtility.getDbl(ds_detail, 0, "ZFORE"));	// 예상시행율
		BigDecimal 	sofoca  = new BigDecimal(0);

		// 원화와 엔화의 경우 100으로 나누어 수주예상금액을 저장한다.
		if ("KRW".equals(waerk) ||"JPY".equals(waerk ))
		{
			sofoca	= new BigDecimal(DatasetUtility.getDbl(ds_detail, 0, "SOFOCA")/100);// 수주예상금액
		}
		else
		{
			sofoca	= new BigDecimal(DatasetUtility.getDbl(ds_detail, 0, "SOFOCA"));// 수주예상금액
		}

		sofoca = sofoca.setScale(2, RoundingMode.DOWN);  // 소수 세째자리에서 반올림

		// 거래선명 조회
		Com0040ParamVO com_param = new Com0040ParamVO();
		BuyrService.createDao(session);
		com_param.setMandt	(mandt);  	// SAP CLIENT
		com_param.setBuyr_cd(kunnr);	// 거래선코드
		List<Com0040> list = BuyrService.find(com_param);
		String kunnr_nm = list.get(0).getBuyr_nm();	// 거래선명

		logger.debug("@@@ sbp0040SaveView.save_0014S.flag : " + flag);
		logger.debug("@@@ sbp0040SaveView.save_0014S.biddat : " + biddat);
		logger.debug("@@@ sbp0040SaveView.save_0014S.sofoca : " + sofoca);
		logger.debug("@@@ sbp0040SaveView.save_0014S.zfore : " + zfore);
		logger.debug("@@@ sbp0040SaveView.save_0014S.matnr : " + matnr);
		logger.debug("@@@ sbp0040SaveView.save_0014S.posid : " + posid);
		logger.debug("@@@ sbp0040SaveView.save_0014S.kunnr_nm : " + kunnr_nm);

		// 저장용 파라메터 SETTING
		param.setMANDT    	( DatasetUtility.getString(ds_detail, 0, "MANDT"	)); // CLIENT
		param.setZBPNNR   	( zbpnnr											) ;	// 사업계획번호
		param.setPSPID    	( zbpnnr											);  // 프로젝트
		param.setPOSID    	( posid												) ; // 호기
		param.setZPYM    	( DatasetUtility.getString(ds_detail, 0, "ZPYM"		)); // 계획년월(입찰년월)
		param.setAUART    	( DatasetUtility.getString(ds_detail, 0, "AUART"	)); // 오더유형
		param.setSPART    	( DatasetUtility.getString(ds_detail, 0, "SPART"	)); // 제품군
		param.setMATNR    	( matnr												) ; // 자재번호
		param.setVKBUR    	( DatasetUtility.getString(ds_detail, 0, "VKBUR"	)); // 부서코드
		param.setVKGRP    	( DatasetUtility.getString(ds_detail, 0, "VKGRP"	)); // 팀코드
		param.setZKUNNR   	( DatasetUtility.getString(ds_detail, 0, "ZKUNNR"	)); // 담당자코드
		param.setBIDDAT   	( biddat											) ; // 입찰일자
		param.setKUNNR    	( DatasetUtility.getString(ds_detail, 0, "KUNNR"	)); // 고객번호
		param.setKUNNR_NM	( kunnr_nm											) ; // 고객명
		param.setGSNAM    	( DatasetUtility.getString(ds_detail, 0, "GSNAM"	)); // 공사명
		param.setREGION   	( DatasetUtility.getString(ds_detail, 0, "REGION"	)); // 설치지역
		param.setGTYPE    	( DatasetUtility.getString(ds_detail, 0, "GTYPE"	)); // 기종
		param.setTYPE1    	( ""												);  // TYPE1
		param.setTYPE2    	( ""												);  // TYPE2
		param.setTYPE3    	( ""												);  // TYPE3
		param.setZNUMBER  	( DatasetUtility.getInt	  (ds_detail, 0, "ZNUMBER"	)); // 대수
		param.setZDELI    	( DatasetUtility.getString(ds_detail, 0, "ZDELI"	)); // 단납기구분
		param.setSHANGH   	( "국내"												);  // 국내/상해구분
		param.setZINTER   	( ""												);  // 중계무역구분
		param.setSOFOCA   	( sofoca											);	// 수주예상액
		param.setZFORE    	( zfore												);	// 예상시행율
		param.setWAERK    	( DatasetUtility.getString(ds_detail, 0, "WAERK"	)); // 통화
		param.setDELDAT   	( DatasetUtility.getString(ds_detail, 0, "DELDAT"	)); // 납기예정일
		//param.setVBELN    ( DatasetUtility.getString(ds_detail, 0, "VBELN"	)); // 판매문서
		param.setUSER_ID   	( DatasetUtility.getString(ds_detail, 0, "USER_ID"	)); // 처리자ID

		// 신규 등록
		if ("I".equals(flag))
		{
			// SAP용 사업계획 등록
			sbp0040Dao.insertZSDT0014S(param);
		}
		// 변경
		else if ("U".equals(flag))
		{
			// SAP용 사업계획 변경
			sbp0040Dao.updateZSDT0014S(param);
		}

		logger.debug("@@@ bill_save_auto.save_0014S SUCCESS !!!" + "");

	}

	// 매출/청구/수금 자동산출결과 등록
	public void bill_save_auto_sap(Dataset ds_compute_out	// 매출/청구/수금 자동산출결과
									,String mandt			// CLIENT
									,String user_id 		// WEB USER_ID
									,String zbpnnr 			// 사업계획번호
									)
	{

		logger.debug("@@@ sbp0040SaveView.bill_save_auto_sap START!!!" + "");

		// 사업계획 저장상태 변경 (SAVE_STAT : Z)
		logger.debug("@@@ Sbp0040SaveView.bill_save_auto_sap.updateSaveStat  ");
		updateSaveStat(mandt, zbpnnr, user_id, "Z");

		logger.debug("@@@ bill_save_auto.bill_save_auto_sap SUCCESS !!!" + "");

		/*
		Sbp0040 	paramDetail = null;						// 매출/청구/수금
		String 		gbn 		= null;						// 매출/청구/수금 구분	(1:매출, 2:청구, 3:수금)
		BigDecimal 	amt 		= null;						// 매출/청구/수금 금액
		BigDecimal 	zero 		= new BigDecimal("0.00");	// 0.00
		String      yearm 		= null;

		for (int i=0;i<ds_compute_out.getRowCount(); i++ )
		{
			//계획구분 (MC : 매출, CH : 청구, SG : 수금)
			gbn = ds_compute_out.getColumnAsString(i, "PLANTP");

			// 금액항목에 값이 있는 경우에만 실행
			if (StringUtils.isNotBlank(ds_compute_out.getColumnAsString(i, "PLANAMT")))
			{
				// 예상금액
				amt = new BigDecimal(ds_compute_out.getColumnAsDouble(i, "PLANAMT"));

				// 금액 > 0 인 경우만 등록
				if( amt.compareTo(zero) == 1 )  // compareTo :::: -1은 작다, 0은 같다, 1은 크다
				{
					// 계획년월
					yearm = ds_compute_out.getColumnAsString(i, "PLANYM");

					paramDetail = new Sbp0040();
				    paramDetail.setMANDT	(mandt);  		// SAP CLIENT
					paramDetail.setUSER_ID	(user_id);  	// WEB USER_ID
					paramDetail.setZBPNNR	(zbpnnr);		// 사업계획번호
					paramDetail.setZSAYM	(yearm);		// 예상년월
					paramDetail.setNETWR_SA (amt);			// 예상금액

					logger.debug("@@@ paramDetail.mandt 	[" + mandt 		+  "] ");
					logger.debug("@@@ paramDetail.user_id 	[" + user_id 	+  "] ");
					logger.debug("@@@ paramDetail.zbpnnr 	[" + zbpnnr 	+  "] ");
					logger.debug("@@@ paramDetail.yearm 	[" + yearm 		+  "] ");
					logger.debug("@@@ paramDetail.amt 		[" + amt 		+  "] ");

					// 등록서비스 CALL
					if 		 ("MC".equals(gbn)) {
						sbp0040Dao.insertZSDT1024(paramDetail);  // 매출등록 서비스CALL
					}else if ("CH".equals(gbn)) {
						sbp0040Dao.insertZSDT1025(paramDetail);  // 청구등록 서비스CALL
					}else if ("SG".equals(gbn)) {
						sbp0040Dao.insertZSDT1026(paramDetail);  // 수금등록 서비스CALL
					}
				}  	// end of if( amt.compareTo(zero) == 1 )
			}		// end of if (StringUtils.isNotBlank(ds_list_bill.getColumnAsString(i, iCol1)))
		}			// end of for (int i=0;i<=ds_list_bill.getRowCount(); i++ )

		*/

	}

	// 사업계획 저장상태 변경
	public void updateSaveStat(String mandt			// CLIENT
								,String zbpnnr		// 사업계획번호
								,String user_id 	// 사용자ID
								,String save_stat	// 저장상태
			                  )
	{
		Sbp0040 param = new Sbp0040();

		param.setMANDT(mandt);
		param.setZBPNNR(zbpnnr);
		param.setUSER_ID(user_id);
		param.setSAVE_STAT(save_stat);

		sbp0040Dao.updateSaveStat(param);
	}

	// 협력업체정보 조회
	public List<Sbp0040> selectListLifnr(Sbp0040ParamVO paramV) {
		return sbp0040Dao.selectListLifnr(paramV);
	}




}
