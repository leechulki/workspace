package hdel.sd.sbp.service;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.com.service.Com0040S;
import hdel.sd.com.domain.Com0040;
import hdel.sd.com.domain.Com0040ParamVO;
import hdel.sd.sbp.dao.Sbp0050D;
import hdel.sd.sbp.domain.Sbp0040;
import hdel.sd.sbp.domain.Sbp0050;
import hdel.sd.sbp.domain.Sbp0050ParamVO;

import java.math.BigDecimal;
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


/**
 * 사업계획매출채권관리(보수)(Sbp0050S) Service
 * @Comment
 *     	1.  void createDao
 *		2.  List selectOpenDtm  	( 사업계획(보수) 오픈일시(14자리) 조회 )
 *		3.  List selectList 		( 사업계획(보수) 목록 조회 )
 *		4.  List selectListDetail	( 사업계획번호 한 건에 등록된 사업계획(보수) 매출/청구/수금목록 조회 )
 *		5.  void save 				( 수주 저장 )
 *		6.  void detail_save 		( 매출/청구/수금 저장 )
 *
 * @since 1.0
 * 		History
 * 		1.0  2012.09.03  구란이  :  initial version
 * @version 1.0
 */


@Service
public class Sbp0050S {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

//	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Com0040S 		BuyrService ;	// 거래선조회 서비스

	private Sbp0050D Sbp0050Dao;

	public void createDao(SqlSession sqlSession) {
		Sbp0050Dao = sqlSession.getMapper(Sbp0050D.class);
	}

	// 사업계획 오픈일시(14자리) 조회
	public List<Sbp0050> selectOpenDtm(Sbp0050ParamVO param) {
		return Sbp0050Dao.selectOpenDtm(param);
	}

	// 사업계획 목록 조회
	public List<Sbp0050> selectList(Sbp0050ParamVO param) {
		return Sbp0050Dao.selectList(param);
	}

	// 사업계획번호에 한 건에 등록된 사업계획 매출/청구/수금목록 조회
	public List<Sbp0050> selectListDetail(Sbp0050ParamVO param) {
		return Sbp0050Dao.selectListDetail(param);
	}

	// 사업계획번호 저장
	public void save(MipParameterVO paramVO, Model model, String pspidsv) throws Exception {


		logger.debug("@@@ Sbp0050S.save START!!!" + "");


		String ErrCode = "CE00001";  // "처리에 실패했습니다.확인 후 다시 처리해 주십시오."


		// INPUT DATASET GET
		Dataset ds_detail 		= paramVO.getDataset("ds_detail");  	// 수주 등록,수정,삭제 대상정보
		Dataset ds_list_detail 	= paramVO.getDataset("ds_list_detail"); // 매출/청구/수금 저장 대상정보(예상금액)
		Dataset ds_yearm 		= paramVO.getDataset("ds_yearm"); 		// 매출/청구/수금 저장 대상정보(18개월치 예상년월)
		String 	mandt 			= paramVO.getVariable("G_MANDT");		// SAP CLIENT
		String 	user_id 		= paramVO.getVariable("G_USER_ID");		// WEB USER ID

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
		logger.debug("@@@ ds_list_detail.getRowCount ===>"+ds_list_detail.getRowCount() 		+ "");
		for( int irow = 0; irow < ds_list_detail.getRowCount(); irow++ )
		{
			for( int icol = 0; icol < ds_list_detail.getColumnCount(); icol++)
			{
				logger.debug("@@@ ds_list_detail["+irow+"Record : "+ds_list_detail.getColumnID(icol)+"] ===>"
								+ DatasetUtility.getString(ds_list_detail, irow, ds_list_detail.getColumnID(icol)));
			}
		}
		//--------------------------------------------------------------------------------------------

		logger.debug("@@@ Sbp0050SaveView start" + "");

		String zbpnnr = "";		// 사업계획번호

		//------------------------------------------------------------------------
		// 변수 선언
		//------------------------------------------------------------------------
		Sbp0050[] 	param 		= null;						// 수주 저장 파라메터
		Sbp0050 	paramDetail = null;						// 매출/청구/수금 저장 파라메터
		String      flag    	= "";						// 저장구분 			(I:신규수주, U:변경, D:삭제)
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
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
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

			flag 		= DatasetUtility.getString(ds_detail, irow, "FLAG");		// 저장구분(I,U,D)
			auart		= DatasetUtility.getString(ds_detail, irow, "AUART");		// 판매문서유형(오더유형)
			kunnr		= DatasetUtility.getString(ds_detail, irow, "KUNNR");		// 거래선코드
			bill_save_yn= DatasetUtility.getString(ds_detail, irow, "BILL_SAVE_YN");// 매출/청구/수금 자료 저장SKIP여부
			logger.debug("@@@ bill_save_yn : " + bill_save_yn);

			// 1. 보수프로젝트정보 Input Dataset(ds_detail) --> class(Sbp0050)로 copy
			logger.debug("@@@ 1. 보수프로젝트정보 Input Dataset(ds_detail) --> class(Sbp0050)로 copy");
			param = (Sbp0050[])CallSAPHdel.moveDs2Obj2(ds_detail, Sbp0050.class, "", null);

			//------------------------------------------------------------------------
			// 수정인 경우
			//------------------------------------------------------------------------
			if ("U".equals(DatasetUtility.getString(ds_detail, irow, "FLAG")))
			{

					// 4.1 매출삭제 서비스CALL  (사업계획번호 한건당 삭제)
					logger.debug("@@@ 4.1 매출삭제 서비스CALL  (사업계획번호 한건당 삭제) ");
					Sbp0050Dao.deleteZSDT1024(param[0]);

					// 4.2 청구삭제 서비스CALL  (사업계획번호 한건당 삭제)
					logger.debug("@@@ 4.2 청구삭제 서비스CALL  (사업계획번호 한건당 삭제) ");
					Sbp0050Dao.deleteZSDT1025(param[0]);

					// 4.3 수금삭제 서비스CALL  (사업계획번호 한건당 삭제)
					logger.debug("@@@ 4.3 수금삭제 서비스CALL  (사업계획번호 한건당 삭제) ");
					Sbp0050Dao.deleteZSDT1026(param[0]);

					// 4.4 매출/청구/수금등록 서비스CALL
					logger.debug("@@@ 4.4 매출/청구/수금등록 서비스CALL ");
					detail_save(  ds_list_detail	// 매출/청구/수금 예상금액
								, ds_yearm			// 매출/청구/수금 예상년월
								, mandt				// CLIENT
								, user_id 			// WEB USER_ID
								, pspidsv 			// 보수프로젝트번호
								);

			}

		} // end of for

		logger.debug("@@@ Sbp0050S.save SUCCESS!!!" + "");

	}

	// 매출/청구/수금 등록
	public void detail_save(	 Dataset ds_list_detail
								,Dataset ds_yearm	// 예상년월 18개월
								,String mandt		// CLIENT
								,String user_id 	// WEB USER_ID
								,String pspidsv 	// 보수프로젝트번호
								)
	{

		logger.debug("@@@ Sbp0050SaveView.detail_save START!!!" + "");

		Sbp0050 	paramDetail = new Sbp0050(); 			// 매출/청구/수금
		String 		gbn 		= null;						// 매출/청구/수금 구분	(1:매출, 2:청구, 3:수금)
		BigDecimal 	amt 		= null;						// 매출/청구/수금 금액
		BigDecimal 	zero 		= new BigDecimal("0.00");	// 0.00
		String      colId       = null;
		String      yearm 		= null;
		int 		row 		= 0;

		for (int iRow1=0;iRow1<ds_list_detail.getRowCount(); iRow1++ )
		{
			gbn = ds_list_detail.getColumnAsString(iRow1, "GBN"); 	// 매출/청구/수금 구분

			for (int iCol1=0;iCol1<ds_list_detail.getColumnCount(); iCol1++ )
			{

				colId = ds_list_detail.getColumnID(iCol1);

				if (!("MANDT".equals(colId)) && !("GBN".equals(colId)) && !("GBN_NM".equals(colId)))
				{

					// 금액항목에 값이 있는 경우에만 실행
					if (StringUtils.isNotBlank(ds_list_detail.getColumnAsString(iRow1, iCol1)))
					{
						// 예상금액
						amt = new BigDecimal(ds_list_detail.getColumnAsDouble(iRow1, iCol1));

						// 금액 > 0 인 경우만 등록
						if( amt.compareTo(zero) == 1 )  // compareTo :::: -1은 작다, 0은 같다, 1은 크다
						{

							row = Integer.parseInt(colId.substring(3,5));
							yearm = ds_yearm.getColumnAsString(row, "YEARM");
							logger.debug("@@@ row   : " + row);
							logger.debug("@@@ yearm : " + yearm);

							paramDetail.setMANDT	(mandt);  		// SAP CLIENT
							paramDetail.setUSER_ID	(user_id);  	// WEB USER_ID
							paramDetail.setPSPIDSV	(pspidsv);		// 보수프로젝트번호
							paramDetail.setZSAYM	(yearm);		// 예상년월
							paramDetail.setNETWR_SA (amt);			// 예상금액

							logger.debug("@@@ paramDetail.mandt 	[" + mandt 		+  "] ");
							logger.debug("@@@ paramDetail.user_id 	[" + user_id 	+  "] ");
							logger.debug("@@@ paramDetail.pspidsv 	[" + pspidsv 	+  "] ");
							logger.debug("@@@ paramDetail.yearm 	[" + yearm 		+  "] ");
							logger.debug("@@@ paramDetail.amt 		[" + amt 		+  "] ");

							// 등록서비스 CALL (매출,청구는 같은 값으로 등록한다.)
							if 		 ("1".equals(gbn)) {
								Sbp0050Dao.insertZSDT1024(paramDetail);  // 매출등록 서비스CALL
								Sbp0050Dao.insertZSDT1025(paramDetail);  // 청구등록 서비스CALL
							}else if ("3".equals(gbn)) {
								Sbp0050Dao.insertZSDT1026(paramDetail);  // 수금등록 서비스CALL
							}
						}  	// end of if( amt.compareTo(zero) == 1 )
					}		// end of if (StringUtils.isNotBlank(ds_list_detail.getColumnAsString(iRow1, iCol1)))
				}			//
			}  				// end of for (int iCol1=2;iCol1<=ds_list_detail.getColumnCount(); iCol1++ )
		}					// end of for (int iRow1=0;iRow1<=ds_list_detail.getRowCount(); iRow1++ )

		logger.debug("@@@ Sbp0050SaveView.detail_save SUCCESS !!!" + "");

	}

	// 오픈일시 존재여부 체크
	public Boolean chkOpenDtm(SqlSession session
					            , String mandt
								, String zpyear		// 편성년도
								)
	{


		logger.debug("@@@ Sbp0050SaveView.chkOpenDtm START!!!" 	+ "");
		logger.debug("@@@ Sbp0050SaveView.chkOpenDtm.mandt" 		+ mandt + "");
		logger.debug("@@@ Sbp0050SaveView.chkOpenDtm.zpyear" 		+ zpyear + "");

		Sbp0050ParamVO param = new Sbp0050ParamVO();

		// 파라메터SET
		param.setMANDT	(mandt);  	// SAP CLIENT
		param.setZPYEAR	(zpyear);	// 편성년도

		// 서비스CALL (사업계획년도에 해당하는 오픈일자 조회)
		List<Sbp0050> list = selectOpenDtm(param);

		// 조회된 오픈일시 정보가 없을 경우 오류로 리턴한다.(저장불가처리)
		if (list.size()==0) return false;

		return true;
	}


}
