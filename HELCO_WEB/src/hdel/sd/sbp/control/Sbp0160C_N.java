package hdel.sd.sbp.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.lib.exception.NoMatchException;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.domain.Com0060ParamVO;
import hdel.sd.com.service.AutoSoNumberS;
import hdel.sd.com.service.DutyS;
import hdel.sd.sbp.domain.Sbp0160ParamVO;
import hdel.sd.sbp.domain.Sbp0160VO_N;
import hdel.sd.sbp.domain.Sbp0161ParamVO;
import hdel.sd.sbp.domain.Sbp0161VO_N;
import hdel.sd.sbp.domain.ZSDS0072;
import hdel.sd.sbp.domain.ZSDT0014;
import hdel.sd.sbp.domain.ZSDT0014S;
import hdel.sd.sbp.domain.ZSDT1001;
import hdel.sd.sbp.domain.ZSDT1005;
import hdel.sd.sbp.domain.ZSDT1012;
import hdel.sd.sbp.domain.ZSDT1023;
import hdel.sd.sbp.service.Sbp0160S_N;
import hdel.sd.smp.control.SmpComC;
import hdel.sd.sso.domain.ZCOBS001;

import java.math.BigDecimal;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sbp0160_N")
public class Sbp0160C_N {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Sbp0160S_N service;
	@Autowired
	private DutyS dutyService; // duty 정보
	@Autowired
	private AutoSoNumberS autoSoService; // 자동채번

	private Calendar cal;// = Calendar.getInstance();

	/**
	 * 전체 조회
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "find")
	public View find(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset ds_cond = paramVO.getDataset("ds_cond");
		
		// OUTPUT DATASET DECLARE
		Dataset ds_output 	= paramVO.getDataset("ds_output");				// UI로 return할 out dataset  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  

		try {

			// CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
			// CW00002=필수 입력항목입니다.\n확인하여 주십시오.
			if ("".equals(ds_cond.getColumn(0, "FR_ZPYM").toString())
					|| ds_cond.getColumn(0, "FR_ZPYM").toString() == null) {
				throw new BizException("CW00002,수주년월 from");
			}
			if ("".equals(ds_cond.getColumn(0, "TO_ZPYM").toString())
					|| ds_cond.getColumn(0, "TO_ZPYM").toString() == null) {
				throw new BizException("CW00001,수주년월 to");
			}

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			// parameter 조건 셋팅
			Sbp0160ParamVO paramV = new Sbp0160ParamVO(); // vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT").toString()); // 세션권한
			paramV.setFR_ZPYM(ds_cond.getColumn(0, "FR_ZPYM").toString()); // 수주년월
																			// from
			paramV.setTO_ZPYM(ds_cond.getColumn(0, "TO_ZPYM").toString()); // 수주년월
																			// to
			paramV.setZPYM(ds_cond.getColumn(0, "ZPYM").toString()); // 현재년월
			paramV.setSPART(ds_cond.getColumn(0, "SPART").toString()); // 제품군
			paramV.setZKUNNR(ds_cond.getColumn(0, "ZKUNNR").toString()); // 영업담당자
			paramV.setVKBUR(ds_cond.getColumn(0, "VKBUR").toString()); // 사업장
			paramV.setVKGRP(ds_cond.getColumn(0, "VKGRP").toString()); // 영업그룹
			paramV.setZMPFLG(ds_cond.getColumn(0, "ZMPFLG").toString()); // 이동계혹
																			// 포함
																			// 여부
			paramV.setSORLT(ds_cond.getColumn(0, "SORLT").toString()); // 수주결과
			paramV.setZAGNT(ds_cond.getColumn(0, "ZAGNT").toString()); // 대리점명
			paramV.setPGUBN(ds_cond.getColumn(0, "PGUBN").toString()); // 계획구분
			paramV.setAGNTGB(ds_cond.getColumn(0, "AGNTGB").toString()); // 직영대리점 구분

			// 언어에 따른 Data표시
			if ("KO".equals( paramVO.getVariable("G_LANG").toUpperCase() ) )	{
				paramV.setLANG("3");
			}else	{
				paramV.setLANG("E");
			}

			List<Sbp0160VO_N> list = null;

			// 업체구분(A:설치협력사,B:보수협력사,C국내대리점,D:자재협력사,E:해외대리점
			if (ds_cond.getColumn(0, "WGBN").toString().equals("E")) {
				list = service.selectZSDT1001E_N(paramV);
			} else {
				list = service.selectZSDT1001_N(paramV);
			}

			// 호출결과(list)를 데이타셋(dsList)에 복사 
			CallSAPHdel.moveListToDs(ds_output, Sbp0160VO_N.class, list);  
			
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");   
		ds_output.setId		("ds_output");  
		resultVO.addDataset	(ds_error);  		// 오류정보
		resultVO.addDataset (ds_output);  
		model.addAttribute("resultVO", resultVO);     
		
		return view;
	}

	/**
	 * 전체 조회
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "find_detail")
	public View find_detail(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset ds_cond_detail = paramVO.getDataset("ds_cond_detail");
		
		// OUTPUT DATASET DECLARE
		Dataset ds_output_detail 	= paramVO.getDataset("ds_output_detail");				// UI로 return할 out dataset  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset  
		
		try {
			// detail
			List<Sbp0161VO_N> list_detail = new ArrayList<Sbp0161VO_N>();
			List<Sbp0161VO_N> list_details = new ArrayList<Sbp0161VO_N>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

					Sbp0161ParamVO param = new Sbp0161ParamVO();
					cal = Calendar.getInstance();
//					d = cal.getTime();

					// 파라메터 SET
					param.setMANDT(paramVO.getVariable("G_MANDT")); // SAP
																	// CLIENT
					param.setSONNR(ds_cond_detail.getColumn(0, "SONNR").toString()); // 사업계획번호
					param.setM00(ds_cond_detail.getColumn(0, "ZPYM").toString()); // 계획년월 + 00월
					// 년월에서 증가 시키지
					cal.setTime(sdf.parse(param.getM00()));
					cal.add(cal.MONTH, 1);
					param.setM01(sdf.format(cal.getTime())); // 계획년월 + 01월
					cal.add(cal.MONTH, 1);
					param.setM02(sdf.format(cal.getTime())); // 계획년월 + 02월
					cal.add(cal.MONTH, 1);
					param.setM03(sdf.format(cal.getTime())); // 계획년월 + 03월
					cal.add(cal.MONTH, 1);
					param.setM04(sdf.format(cal.getTime())); // 계획년월 + 04월
					cal.add(cal.MONTH, 1);
					param.setM05(sdf.format(cal.getTime())); // 계획년월 + 05월
					cal.add(cal.MONTH, 1);
					param.setM06(sdf.format(cal.getTime())); // 계획년월 + 06월
					cal.add(cal.MONTH, 1);
					param.setM07(sdf.format(cal.getTime())); // 계획년월 + 07월
					cal.add(cal.MONTH, 1);
					param.setM08(sdf.format(cal.getTime())); // 계획년월 + 08월
					cal.add(cal.MONTH, 1);
					param.setM09(sdf.format(cal.getTime())); // 계획년월 + 09월
					cal.add(cal.MONTH, 1);
					param.setM10(sdf.format(cal.getTime())); // 계획년월 + 10월
					cal.add(cal.MONTH, 1);
					param.setM11(sdf.format(cal.getTime())); // 계획년월 + 11월
					cal.add(cal.MONTH, 1);
					param.setM12(sdf.format(cal.getTime())); // 계획년월 + 12월
					cal.add(cal.MONTH, 1);
					param.setM13(sdf.format(cal.getTime())); // 계획년월 + 13월
					cal.add(cal.MONTH, 1);
					param.setM14(sdf.format(cal.getTime())); // 계획년월 + 14월
					cal.add(cal.MONTH, 1);
					param.setM15(sdf.format(cal.getTime())); // 계획년월 + 15월
					cal.add(cal.MONTH, 1);
					param.setM16(sdf.format(cal.getTime())); // 계획년월 + 16월
					cal.add(cal.MONTH, 1);
					param.setM17(sdf.format(cal.getTime())); // 계획년월 + 17월
					cal.add(cal.MONTH, 1);
					param.setM18(sdf.format(cal.getTime())); // 계획년월 + 18월

					list_detail = null;
					list_detail = (List<Sbp0161VO_N>) service.selectListDetail_N(param);

					//logger.info(" @@@@ list_detail size not null = : "+list_detail.size() );
					if (list_detail.size() > 0) {

						if ( list_detail.size() != 3 )
						{
							//logger.info("@@@@ 매출 청구 수금이 만들어지지 않음.");
							String msg = "매출/청구/수금의 데이터가 형식에 맞지 않게 생성되었습니다. /n";
									msg += "해당메시지는 수주계획번호 건별 매출/청구/수금이 3라인씩 구성에 실패했을 경우 발생합니다.";
							SmpComC.moveDs2Msg("error", msg, model);
						}
						else
						{
							for (int b = 0; b < 3; b++) 
							{
								list_details.add((Sbp0161VO_N) list_detail.get(b));
							}	
						}
					} else {
						//logger.info(" @@@@ list_detail size = 0: ");
						list_detail = null;
						list_detail = (List<Sbp0161VO_N>) service.selectListDetail_Null(param);
						//logger.info(" @@@@ list_detail size = : "+ list_detail.size());
						for (int c = 0; c < 3; c++) {
							list_details.add((Sbp0161VO_N) list_detail.get(c));
						}
					}

			// 호출결과(list)를 데이타셋(dsList)에 복사 
			CallSAPHdel.moveListToDs(ds_output_detail, Sbp0161VO_N.class, list_details);  
			
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");   
		ds_output_detail.setId("ds_output_detail");  
		resultVO.addDataset	(ds_error);  		// 오류정보
		resultVO.addDataset (ds_output_detail);  
		model.addAttribute("resultVO", resultVO);     
		
		return view;
	}	
	
	/**
	 * 실기종 조회
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "rtype")
	public View findRtype(MipParameterVO paramVO, Model model) {
		// 검색 조건 - 부서
		String vkbur = paramVO.getVariable("vkbur");

		try {

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			// 파라메터SET
			Com0060ParamVO param = new Com0060ParamVO(); // VO 생성
			param.setMandt(paramVO.getVariable("G_MANDT")); // SAP CLIENT
			param.setFilter1(vkbur); // 부서

			List<Com0060ParamVO> list = service.selectRTYPE(param);

			// list결과를 dataset으로 담기
			Dataset dsOutput = new Dataset();
			SmpComC.moveDs2List(dsOutput, Com0060ParamVO.class, list);

			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput);
			model.addAttribute("resultVO", resultVO);
		} catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if ( message.length <= 1)
			{
				SmpComC.moveDs2Msg(message[0], message[0], model);
			}
			else
			{
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	/**
	 * 저장
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	public View save(MipParameterVO paramVO, Model model) {

		// 검색조건 dataset
		Dataset dsInput = paramVO.getDataset("ds_input");
		//logger.info(dsInput.toString());

		ZSDT0014[] listZSDT0014 = new ZSDT0014[] {}; // 월 수주계획
		ZSDT1001[] listZSDT1001 = new ZSDT1001[] {}; // 수주계획

		Dataset dsZSDT0014 = ZSDT0014.getDataset(); // sap dataset정보로 초기화
		Dataset dsZSDT1001 = ZSDT1001.getDataset(); // sap dataset정보로 초기화
		Dataset dsZCOBS001 = ZCOBS001.getDataset(); // sap dataset정보로 초기화

		Dataset	ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		ds_error.deleteAll();
		
		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
		
		// ZSDT1001, ZSDT0014, ZCOBS001 Input Parameter Setting
		try {
			dsZSDT0014.deleteAll(); // 월 수주계획
			dsZSDT1001.deleteAll(); // 수주계획
			dsZCOBS001.deleteAll(); // 원가정보

			HashMap<String,String> mapInput =null;
			HashMap mapOutput = null ;

			// input리스트중
			String status = ""; // 상태정보(insert, normal, update, delete) 저장용 변수
			int nRow = 0; // 각 데이타셋(ZSDT1001, ZSDT0014, ZCOBS001)에 저장될 row
							// number

			// logger.info("@@@@@@@@@ 저장시 저장할 건수 :"+dsInput.getRowCount());
			for (int i = 0; i < dsInput.getRowCount(); i++) {
				status = DatasetUtility.getString(dsInput, i, "STATUS");

				// 상태(STATUS)가 inser/update/delete인 데이타만 처리
				if (status != null
						&& (status.equals("update") || status.equals("insert") || status
								.equals("delete"))) {
					// duty DAO생성
					dutyService.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

					// duty in/out용 map
					mapInput = new HashMap<String,String>();
					mapOutput = new HashMap();

					// CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
					// CW00002=필수 입력항목입니다.\n확인하여 주십시오.
					if ("".equals(dsInput.getColumn(0, "MATNR").toString())
							|| dsInput.getColumn(0, "MATNR").toString() == null) {
						throw new BizException("CW00002,자재번호");
					}

					String matnr = dsInput.getColumn(i, "MATNR").toString(); // 자재번호

					// 자재번호 별 듀티구분
					String dutyGbn = "";

					// 엘리베이터(L-1000), 선박용 엘리베이터(L-2000)
					// 에스컬레이터(S-1000), 무빙워크(W-1000)
					if (matnr.equals("L-1000") || matnr.equals("L-2000"))
						dutyGbn = "PCELV";
					else if (matnr.equals("S-1000") || matnr.equals("W-1000"))
						dutyGbn = "PCESMW";
					else
						dutyGbn = "PCELV";

					// 엘레베이터, 선박용 엘리베이터, 에스컬레이터, 무빙워크 일경우 필수
					if (dutyGbn.equals("PCELV") || dutyGbn.equals("PCESMW")) {

						// CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
						// CW00002=필수 입력항목입니다.\n확인하여 주십시오.
						if ("".equals(dsInput.getColumn(0, "RTYPE").toString())
								|| dsInput.getColumn(0, "RTYPE").toString() == null) {
							throw new BizException("CW00002,기종");
						}
						// CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
						// CW00002=필수 입력항목입니다.\n확인하여 주십시오.
						if ("".equals(dsInput.getColumn(0, "TYPE1").toString())
								|| dsInput.getColumn(0, "TYPE1").toString() == null) {
							throw new BizException("CW00002,인승");
						}
						// CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
						// CW00002=필수 입력항목입니다.\n확인하여 주십시오.
						if ("".equals(dsInput.getColumn(0, "TYPE2").toString())
								|| dsInput.getColumn(0, "TYPE2").toString() == null) {
							throw new BizException("CW00002,속도");
						}
						// CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
						// CW00002=필수 입력항목입니다.\n확인하여 주십시오.
						if ("".equals(dsInput.getColumn(0, "TYPE3").toString())
								|| dsInput.getColumn(0, "TYPE3").toString() == null) {
							throw new BizException("CW00002,층수");
						}
					}

					mapInput.put("VKBUR", dsInput.getColumn(i, "VKBUR").toString()); // 부서
					mapInput.put("RTYPE", dsInput.getColumn(i, "RTYPE").toString()); // 기종
					mapInput.put("TYPE1", dsInput.getColumn(i, "TYPE1").toString()); // 인승
					mapInput.put("TYPE2", dsInput.getColumn(i, "TYPE2").toString()); // 속도
					mapInput.put("TYPE3", dsInput.getColumn(i, "TYPE3").toString()); // 층수
					mapInput.put("MATNR", dsInput.getColumn(i, "MATNR").toString()); // 자재번호
					mapInput.put("SHANGH", dsInput.getColumn(i, "SHANGH").toString()); // 중계구분
					mapInput.put("ZINTER", dsInput.getColumn(i, "ZINTER").toString()); //
					
					String zbdtyp = DatasetUtility.getString(dsInput, i, "ZBDTYP");  //건물용도  jss
					String zagnt  = DatasetUtility.getString(dsInput, i, "ZAGNT");   //대리점  jss
					String zdeliD  = dsInput.getColumnAsString( i , "ZDELI");         //단납구분 jss

					//ZSDTDUTYD.SPEC8 사양체크 jss
					if (zagnt.trim().length()>0) zagnt="Y";
					else zagnt="";
					
					//ZSDTDUTYD.SPEC9 사양체크 jss
					if ("X".equals(zdeliD)) zdeliD="Y";
					else zdeliD="";
					
					mapInput.put("ZBDTYP", zbdtyp); //건물용도 jss
					mapInput.put("ZAGNT", zagnt);   //대리점코드 jss
					mapInput.put("ZDELI", zdeliD);   //단납구분 jss
					

					// genSpec(mandt, gtype, blockGbn, list)
					mapOutput = dutyService.genSpec( paramVO.getVariable("G_MANDT"), dutyGbn, "00", mapInput, paramVO.getVariable("G_LANG"));

					// rs정보 = matnr, ztplno, gtype, auart, spart
					if ("".equals(mapOutput.get("GTYPE").toString())
							|| mapOutput.get("GTYPE").toString() == null) {
						throw new BizException("CW00001,duty-result 중 대표기종");
					}
					
					String gtype = mapOutput.get("GTYPE").toString(); // 대표기종

					// 대표기종 존재여부에 따라 처리
					if (gtype != null && !gtype.equals("")) {
						dsZSDT0014.appendRow(); // 사업계획 복사테이블
						dsZSDT1001.appendRow(); // 사업계획

						// 계획년월 날짜포맷(-)만들기
						String biddat = DatasetUtility.getString(dsInput, i,
								"ZPYM").substring(0, 4)
								+ "-"
								+ DatasetUtility.getString(dsInput, i, "ZPYM")
										.substring(4) + "-01";

						// deldat 날짜포맷(-)만들기
						String deldat = DatasetUtility.getString(dsInput, i,
								"DELDAT");
						deldat = deldat.substring(0, 4) + "-"
								+ deldat.substring(4).substring(0, 2) + "-"
								+ deldat.substring(6);

						// 현재날짜 포맷(-)만들기
						String date = DateTime.getDateString();
						date = date.substring(0, 4) + "-"
								+ date.substring(4).substring(0, 2) + "-"
								+ date.substring(6);

						// 현재시간 포맷(:)만들기
						String time = DateTime.getShortTimeString();
						time = time.substring(0, 2) + ":"
								+ time.substring(2).substring(0, 2) + ":"
								+ time.substring(4);

						// 수주계획번호
						String sonnr = "";

						// 상태(status)가 insert일 경우
						if (DatasetUtility.getString(dsInput, i, "STATUS")
								.equals("insert")) {
							
							
							autoSoService.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
							
							// 자동채번(수주계획번호)--------------------------------------------------
							AutoNumberParamVO sonnrParam = new AutoNumberParamVO();
							sonnrParam.setDeptFlag(DatasetUtility.getString(
									dsInput, i, "AUART")); // 판매문서유형
							sonnrParam.setSoQtFlag("1"); // 채번구분(0:사업계획, 1:수주계획
															// , 2:견적)
							sonnrParam.setMandt(paramVO.getVariable("G_MANDT")); // SAP
																					// CLIENT
							List<AutoNumberVO> sonnrList = autoSoService
									.AutoSoNumber(sonnrParam);
							// ----------------------------------------------------------------------
							sonnr = sonnrList.get(0).getNumber(); // 채번 된 수주계획번호

							dsZSDT1001.setColumn(nRow, "CDATE", date); // 생성일자
							dsZSDT1001.setColumn(nRow, "CTIME", time); // 생성시간
							dsZSDT1001.setColumn(nRow, "CUSER", paramVO.getVariable("G_USER_ID")); // 생성자

							dsZSDT0014.setColumn(nRow, "CDATE",
									dsZSDT1001.getColumn(nRow, "CDATE")); // 생성일자
							dsZSDT0014.setColumn(nRow, "CTIME",
									dsZSDT1001.getColumn(nRow, "CTIME")); // 생성시간
							dsZSDT0014.setColumn(nRow, "CUSER",
									dsZSDT1001.getColumn(nRow, "CUSER")); // 생성자

						}
						// 상태(status)가 update일 경우
						else if (DatasetUtility.getString(dsInput, i, "STATUS")
								.equals("update")) {
							// 생성일자 포맷(-)만들기
							String cdate = DatasetUtility.getString(dsInput, i,
									"CDATE");
							cdate = cdate.substring(0, 4) + "-"
									+ cdate.substring(4).substring(0, 2) + "-"
									+ cdate.substring(6);

							// 생성시간 포맷(:)만들기
							String ctime = DatasetUtility.getString(dsInput, i,
									"CTIME");
							ctime = ctime.substring(0, 2) + ":"
									+ ctime.substring(2).substring(0, 2) + ":"
									+ ctime.substring(4);

							// 수주계획번호
							sonnr = DatasetUtility.getString(dsInput, i,
									"SONNR");

							dsZSDT1001.setColumn(nRow, "CDATE", cdate); // 생성일자
							dsZSDT1001.setColumn(nRow, "CTIME", ctime); // 생성시간
							dsZSDT1001.setColumn(nRow, "CUSER", DatasetUtility.getString(dsInput, i, "CUSER")); // 생성자

							dsZSDT0014.setColumn(nRow, "CDATE",
									dsZSDT1001.getColumn(nRow, "CDATE")); // 생성일자
							dsZSDT0014.setColumn(nRow, "CTIME",
									dsZSDT1001.getColumn(nRow, "CTIME")); // 생성시간
							dsZSDT0014.setColumn(nRow, "CUSER",
									dsZSDT1001.getColumn(nRow, "CUSER")); // 생성자

							dsZSDT1001.setColumn(nRow, "UDATE", date); // 갱신일자
							dsZSDT1001.setColumn(nRow, "UTIME", time); // 갱신시간
							dsZSDT1001.setColumn(nRow, "UUSER",
									paramVO.getVariable("G_USER_ID")); // 갱신자
							
							dsZSDT0014.setColumn(nRow, "UDATE",
									dsZSDT1001.getColumn(nRow, "UDATE")); // 갱신일자
							dsZSDT0014.setColumn(nRow, "UTIME",
									dsZSDT1001.getColumn(nRow, "UTIME")); // 갱신시간
							dsZSDT0014.setColumn(nRow, "UUSER",
									dsZSDT1001.getColumn(nRow, "UUSER")); // 갱신자

						}
						// 상태(status)가 delete일 경우
						else {
							// 생성일자 포맷(-)만들기
							String cdate = DatasetUtility.getString(dsInput, i,
									"CDATE");
							cdate = cdate.substring(0, 4) + "-"
									+ cdate.substring(4).substring(0, 2) + "-"
									+ cdate.substring(6);

							// 생성시간 포맷(:)만들기
							String ctime = DatasetUtility.getString(dsInput, i,
									"CTIME");
							ctime = ctime.substring(0, 2) + ":"
									+ ctime.substring(2).substring(0, 2) + ":"
									+ ctime.substring(4);

							// 갱신일자 포맷(-)만들기
							String udate = DatasetUtility.getString(dsInput, i,
									"UDATE");
							udate = udate.substring(0, 4) + "-"
									+ udate.substring(4).substring(0, 2) + "-"
									+ udate.substring(6);

							// 생성시간 포맷(:)만들기
							String utime = DatasetUtility.getString(dsInput, i,
									"UTIME");
							utime = utime.substring(0, 2) + ":"
									+ utime.substring(2).substring(0, 2) + ":"
									+ utime.substring(4);

							// 수주계획번호
							sonnr = DatasetUtility.getString(dsInput, i,
									"SONNR");

							dsZSDT1001.setColumn(nRow, "CDATE", cdate); // 생성일자
							dsZSDT1001.setColumn(nRow, "CTIME", ctime); // 생성시간
							dsZSDT1001.setColumn(nRow, "CUSER", DatasetUtility.getString(dsInput, i, "CUSER")); // 생성자

							dsZSDT0014.setColumn(nRow, "CDATE",
									dsZSDT1001.getColumn(nRow, "CDATE")); // 생성일자
							dsZSDT0014.setColumn(nRow, "CTIME",
									dsZSDT1001.getColumn(nRow, "CTIME")); // 생성시간
							dsZSDT0014.setColumn(nRow, "CUSER",
									dsZSDT1001.getColumn(nRow, "CUSER")); // 생성자

							dsZSDT1001.setColumn(nRow, "UDATE", udate); // 생성일자
							dsZSDT1001.setColumn(nRow, "UTIME", utime); // 생성시간
							dsZSDT1001.setColumn(nRow, "UUSER", DatasetUtility.getString(dsInput, i, "UUSER"));

							dsZSDT0014.setColumn(nRow, "UDATE",
									dsZSDT1001.getColumn(nRow, "UDATE")); // 갱신일자
							dsZSDT0014.setColumn(nRow, "UTIME",
									dsZSDT1001.getColumn(nRow, "UTIME")); // 갱신시간
							dsZSDT0014.setColumn(nRow, "UUSER",
									dsZSDT1001.getColumn(nRow, "UUSER")); // 갱신자

							dsZSDT1001.setColumn(nRow, "DDATE", date); // 삭제일자
							dsZSDT1001.setColumn(nRow, "DTIME", time); // 삭제시간
							dsZSDT1001.setColumn(nRow, "DUSER", paramVO.getVariable("G_USER_ID")); // 삭제자

							dsZSDT0014.setColumn(nRow, "DDATE",dsZSDT1001.getColumn(nRow, "DDATE")); // 삭제일자
							dsZSDT0014.setColumn(nRow, "DTIME",dsZSDT1001.getColumn(nRow, "DTIME")); // 삭제시간
							dsZSDT0014.setColumn(nRow, "DUSER",dsZSDT1001.getColumn(nRow, "DUSER")); // 삭제자

						}

						// 이동계획구분
						String zmpflg = dsInput.getColumnAsString( i , "ZMPFLG");
						if ( ("X").equals(zmpflg) || ("1").equals(zmpflg) ) 
						{
							dsZSDT1001.setColumn(nRow, "ZMPFLG", "X");
						} 
						else 
						{
							dsZSDT1001.setColumn(nRow, "ZMPFLG", " ");
						}

						// 단납기구분
						String zdeli = dsInput.getColumnAsString( i , "ZDELI"); 
						if ( ("X").equals(zdeli) || ("1").equals(zdeli) ) 
						{
							dsZSDT1001.setColumn(nRow, "ZDELI", "X");
						} 
						else 
						{
							dsZSDT1001.setColumn(nRow, "ZDELI", " ");
						}

						// 중계무역구분(Y)
						if ("Y".equals(DatasetUtility.getString(dsInput, i, "ZINTER")) ) {
							dsZSDT1001.setColumn(nRow, "ZINTER", "Y");
						} else {
							dsZSDT1001.setColumn(nRow, "ZINTER", " ");
						}

						dsZSDT0014.setColumn(nRow, "ZDELI",
								dsZSDT1001.getColumn(nRow, "ZDELI")); // 단납기구분
						dsZSDT0014.setColumn(nRow, "ZINTER",
								dsZSDT1001.getColumn(nRow, "ZINTER")); // 중계무역구분(Y)

						dsZSDT1001.setColumn(nRow, "SONNR", sonnr); // 수주계획번호
						dsZSDT0014.setColumn(nRow, "SONNR",
								dsZSDT1001.getColumn(nRow, "SONNR")); // 수주계획번호

						dsZSDT0014.setColumn(nRow, "PSPID",
								dsZSDT1001.getColumn(nRow, "SONNR")); // 프로젝트번호

						dsZSDT1001.setColumn(nRow, "MANDT",
								paramVO.getVariable("G_MANDT")); // 클라이언트
						dsZSDT0014.setColumn(nRow, "MANDT",
								dsZSDT1001.getColumn(nRow, "MANDT")); // 클라이언트

						dsZSDT1001.setColumn(nRow, "ZPYM",
								DatasetUtility.getString(dsInput, i, "ZPYM")); // 계획년월
						dsZSDT0014.setColumn(nRow, "BIDYM",
								dsZSDT1001.getColumn(nRow, "ZPYM")); // 계획년월

						dsZSDT1001.setColumn(nRow, "SPART",
								DatasetUtility.getString(dsInput, i, "SPART")); // 제품군
						dsZSDT0014.setColumn(nRow, "SPART",
								dsZSDT1001.getColumn(nRow, "SPART")); // 제품군

						dsZSDT1001.setColumn(nRow, "AUART",
								DatasetUtility.getString(dsInput, i, "AUART")); // 판매문서유형
						dsZSDT0014.setColumn(nRow, "AUART",
								dsZSDT1001.getColumn(nRow, "AUART")); // 판매문서유형

						dsZSDT1001.setColumn(nRow, "MATNR", matnr); // 자재번호
						dsZSDT0014.setColumn(nRow, "MATNR",
								dsZSDT1001.getColumn(nRow, "MATNR")); // 자재번호

						dsZSDT1001.setColumn(nRow, "VKBUR",
								DatasetUtility.getString(dsInput, i, "VKBUR")); // 부서
						dsZSDT0014.setColumn(nRow, "VKBUR",
								dsZSDT1001.getColumn(nRow, "VKBUR")); // 부서

						dsZSDT1001.setColumn(nRow, "VKGRP",
								DatasetUtility.getString(dsInput, i, "VKGRP")); // 팀
						dsZSDT0014.setColumn(nRow, "VKGRP",
								dsZSDT1001.getColumn(nRow, "VKGRP")); // 팀

						dsZSDT1001.setColumn(nRow, "ZKUNNR",
								DatasetUtility.getString(dsInput, i, "ZKUNNR")); // 영업담당자
						dsZSDT0014.setColumn(nRow, "ZKUNNR",
								dsZSDT1001.getColumn(nRow, "ZKUNNR")); // 영업담당자

						dsZSDT1001.setColumn(nRow, "GTYPE", gtype); // 대표기종
						dsZSDT0014.setColumn(nRow, "GTYPE",
								dsZSDT1001.getColumn(nRow, "GTYPE")); // 대표기종

						dsZSDT1001.setColumn(nRow, "RTYPE",
								DatasetUtility.getString(dsInput, i, "RTYPE")); // 실기종
																				// GTYPE_OLD->RTYPE
						dsZSDT0014.setColumn(nRow, "GTYPE_OLD",
								dsZSDT1001.getColumn(nRow, "RTYPE")); // 실기종
																		// GTYPE_OLD->RTYPE

						dsZSDT1001.setColumn(nRow, "TYPE1",
								DatasetUtility.getString(dsInput, i, "TYPE1")); // 타입1
						dsZSDT0014.setColumn(nRow, "GSPEC1",
								dsZSDT1001.getColumn(nRow, "TYPE1")); // 스펙1

						dsZSDT1001.setColumn(nRow, "TYPE2",
								DatasetUtility.getString(dsInput, i, "TYPE2")); // 타입2
						dsZSDT0014.setColumn(nRow, "GSPEC2",
								dsZSDT1001.getColumn(nRow, "TYPE2")); // 스펙2

						dsZSDT1001.setColumn(nRow, "TYPE3",
								DatasetUtility.getString(dsInput, i, "TYPE3")); // 타입3
						dsZSDT0014.setColumn(nRow, "GSPEC3",
								dsZSDT1001.getColumn(nRow, "TYPE3")); // 스펙3

						dsZSDT1001
								.setColumn(nRow, "ZNUMBER", DatasetUtility
										.getDouble(dsInput, i, "ZNUMBER")); // 대수
						dsZSDT0014.setColumn(nRow, "ZNUMBER",
								dsZSDT1001.getColumn(nRow, "ZNUMBER")); // 대수

						dsZSDT1001.setColumn(nRow, "KUNNR",
								DatasetUtility.getString(dsInput, i, "KUNNR")); // 고객
						dsZSDT0014.setColumn(nRow, "KUNNR",
								dsZSDT1001.getColumn(nRow, "KUNNR")); // 고객

						dsZSDT1001.setColumn(nRow, "ZAGNT",
								DatasetUtility.getString(dsInput, i, "ZAGNT")); // 대리점
						dsZSDT0014.setColumn(nRow, "ZAGNT",
								dsZSDT1001.getColumn(nRow, "ZAGNT")); // 대리점

						dsZSDT1001.setColumn(nRow, "LAND1",
								DatasetUtility.getString(dsInput, i, "LAND1")); // 국가
						dsZSDT0014.setColumn(nRow, "LAND1",
								dsZSDT1001.getColumn(nRow, "LAND1")); // 국가

						dsZSDT1001.setColumn(nRow, "GSNAM",
								DatasetUtility.getString(dsInput, i, "GSNAM")); // 현장명
						dsZSDT0014.setColumn(nRow, "GSNAM",
								dsZSDT1001.getColumn(nRow, "GSNAM")); // 현장명

						dsZSDT1001.setColumn(nRow, "REGION",
								DatasetUtility.getString(dsInput, i, "REGION")); // 설치지역
						dsZSDT0014.setColumn(nRow, "REGION",
								dsZSDT1001.getColumn(nRow, "REGION")); // 설치지역

						dsZSDT1001.setColumn(nRow, "SHANGH",
								DatasetUtility.getString(dsInput, i, "SHANGH")); // 국내/상해구분
						dsZSDT0014.setColumn(nRow, "SHANGH",
								dsZSDT1001.getColumn(nRow, "SHANGH")); // 국내/상해구분

						dsZSDT1001.setColumn(nRow, "SOFOCA",
								DatasetUtility.getString(dsInput, i, "SOFOCA"));// 수주예상액

						// 수주예상액
						double tempDbl = 0;
						if (DatasetUtility.getDouble(dsInput, i, "SOFOCA") == 0) {
							dsZSDT0014.setColumn(nRow, "SOFOCA", 0);
						} else {
							// 통화(WAERK)단위에 따라 환산금액 계산
							if ("KRW".equals(DatasetUtility.getString(dsInput,
									i, "WAERK"))
									|| "JPY".equals(DatasetUtility.getString(
											dsInput, i, "WAERK"))) {
								tempDbl = DatasetUtility.getDouble(dsInput, i, "SOFOCA") / 100.0;
							} else {
								tempDbl = DatasetUtility.getDouble(dsInput, i, "SOFOCA");
							}

							dsZSDT0014.setColumn(nRow, "SOFOCA", tempDbl);
						}

						dsZSDT1001.setColumn(nRow, "ZFORE", DatasetUtility.getString(dsInput, i, "ZFORE")); // 예상시행율
						dsZSDT0014.setColumn(nRow, "ZFORE", dsZSDT1001.getColumn(nRow, "ZFORE")); // 예상시행율

						dsZSDT1001.setColumn(nRow, "WAERK", DatasetUtility.getString(dsInput, i, "WAERK")); // 통화
						dsZSDT0014.setColumn(nRow, "WAERK", dsZSDT1001.getColumn(nRow, "WAERK")); // 통화

						dsZSDT1001.setColumn(nRow, "DELDAT", deldat); // 납기예정일
						dsZSDT0014.setColumn(nRow, "DELDAT", dsZSDT1001.getColumn(nRow, "DELDAT")); // 납기예정일

						dsZSDT1001.setColumn(nRow, "ZRMK", DatasetUtility.getString(dsInput, i, "ZRMK")); // 비고

						dsZSDT1001.setColumn(nRow, "SOABLE", DatasetUtility.getString(dsInput, i, "SOABLE")); // 수주가능성
						dsZSDT0014.setColumn(nRow, "SOABLE", dsZSDT1001.getColumn(nRow, "SOABLE")); // 수주가능성

						dsZSDT1001.setColumn(nRow, "SORLT", DatasetUtility.getString(dsInput, i, "SORLT")); // 수주결과
						dsZSDT0014.setColumn(nRow, "SORLT", dsZSDT1001.getColumn(nRow, "SORLT")); // 수주결과

						// 이동계획마감여부
						String zclflg = "";
						logger.info(" @@@@@ ZCLFLG : "+DatasetUtility.getString(dsInput, i, "ZCLFLG") );
						if (DatasetUtility.getString(dsInput, i, "ZCLFLG").equals("X")) 
						{
							zclflg = "추가";
						} else {
							zclflg = "계획";
						}
						dsZSDT1001.setColumn(nRow, "PGUBN", zclflg); // 계획구분

						dsZSDT1001.setColumn(nRow, "SOPRICE", DatasetUtility
										.getDouble(dsInput, i, "SOPRICE"));// 수주금액

						// 수주금액
						tempDbl = 0;
						if (DatasetUtility.getDouble(dsInput, i, "SOPRICE") == 0) {
							dsZSDT0014.setColumn(nRow, "SOPRICE", 0);
						} else {
							// 통화(WAERK)단위에 따라 환산금액 계산
							if ("KRW".equals(DatasetUtility.getString(dsInput,
									i, "WAERK"))
									|| "JPY".equals(DatasetUtility.getString(
											dsInput, i, "WAERK"))) {
								tempDbl = DatasetUtility.getDouble(dsInput, i, "SOPRICE") / 100.0;
								
							} else {
								tempDbl = DatasetUtility.getDouble(dsInput, i, "SOPRICE");
							}
							dsZSDT0014.setColumn(nRow, "SOPRICE", tempDbl + "");
						}

						dsZSDT1001.setColumn(nRow, "SHANG", DatasetUtility.getString(dsInput, i, "SHANG")); // 당사시행율
						dsZSDT0014.setColumn(nRow, "SHANG", dsZSDT1001.getColumn(nRow, "SHANG")); // 당사시행율

						dsZSDT1001.setColumn(nRow, "VBELN", DatasetUtility.getString(dsInput, i, "VBELN")); // 수주번호
						dsZSDT0014.setColumn(nRow, "VBELN", dsZSDT1001.getColumn(nRow, "VBELN")); // 수주번호

						dsZSDT0014.setColumn(nRow, "POSID", f_posid_make(sonnr, matnr)); // 호기번호
						dsZSDT0014.setColumn(nRow, "BIDDAT", biddat); // 입찰예정일
						dsZSDT0014.setColumn(nRow, "NAME1", DatasetUtility.getString(dsInput, i, "KUNNR_NM"));// 고객명

						dsZSDT1001.setColumn(nRow, "ZBPNNR", DatasetUtility.getString(dsInput, i, "ZBPNNR")); // 사업계획번호
						dsZSDT0014.setColumn(nRow, "ZBPNNR", dsZSDT1001.getColumn(nRow, "ZBPNNR")); // 사업계획번호

						dsZSDT1001.setColumn(nRow, "ZCOST", DatasetUtility.getString(dsInput, i, "ZCOST")); // 원가
						dsZSDT0014.setColumn(nRow, "ZCOST", dsZSDT1001.getColumn(nRow, "ZCOST")); // 원가

						// 기종(rtype), 인승(type1), 속도(type2), 층수(type3), 수주예상액(sofoca)이 변경시 플래그 처리('X')
						dsZSDT0014.setColumn(nRow, "ENTP3", DatasetUtility.getString(dsInput, i, "UFLAG")); 
						
						dsZSDT0014.setColumn(nRow, "ZBDTYP", zbdtyp); // 건물용도 jss
						dsZSDT1001.setColumn(nRow, "ZBDTYP", zbdtyp); // 건물용도 jss
						dsZSDT0014.setColumn(nRow, "ZPYEAR", ""); // jss
						dsZSDT1001.setColumn(nRow, "ZPYEAR", ""); // jss

						dsZSDT1001.setColumn(nRow, "SID", DatasetUtility.getString(dsInput, i, "SID")); //승강기번호
						dsZSDT0014.setColumn(nRow, "SID", dsZSDT1001.getColumn(nRow, "SID")); //승강기번호
						
						nRow++;
					}
					// 템플릿번호가 존재하지 않음
					else 
					{
						logger.info("@@@@@@@@@ duty이후 템플릿번호가 존재하지 않음.");
					}

				}
			}// end for

			// 매출/청구/수금 산출 RFC
			listZSDT0014 = (ZSDT0014[]) SmpComC.moveDs2Obj(dsZSDT0014, ZSDT0014.class, "");
			listZSDT1001 = (ZSDT1001[]) SmpComC.moveDs2Obj(dsZSDT1001, ZSDT1001.class, "");

			// 검색조건 dataset
			// 매출/청구/수금 금액 RFC
			Dataset dsZSDT0072 = ZSDS0072.getDataset();
			
			// 리스트가 없는 상태에서 addrow후 저장시
			// 매출/청구/수금에 대한 데이터가 존재하지 않으므로 그에대한 처리를 한다.
			if ( paramVO.getDataset("ds_output_detail") != null )
			{
				save3(paramVO, paramVO.getDataset("ds_output_detail"), listZSDT0014, listZSDT1001, dsZSDT0072, model);
			}
			else
			{
				save1(paramVO, listZSDT0014, listZSDT1001, model);
			}

		} catch (BizException e) {
			e.printStackTrace();
		 	// 비정상종료되어 오류코드가 리턴된 경우 (e.getMessage()에 오류코드만 담겨져 옴)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
			resultVO.addDataset	(ds_error);  	// 오류INFO  
			model.addAttribute	("resultVO", resultVO);    
		} catch (NoMatchException e) {
			e.printStackTrace();
		 	// 비정상종료되어 오류코드가 리턴된 경우 (e.getMessage()에 오류코드만 담겨져 옴)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), e.getMessage(), "Y", "Y"); 
			// 처리결과 리턴
			resultVO.addDataset	(ds_error);  	// 오류INFO  
			model.addAttribute	("resultVO", resultVO);    
		} catch (Exception e ) {
			e.printStackTrace();
			// 예기치못한 비정상종료인 경우		 (e.getMessage()에 오류메세지 전체가 담겨져 옴)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			resultVO.addDataset	(ds_error);  	// 오류INFO  
			model.addAttribute	("resultVO", resultVO);    
		}


		return view;
	}

	/**
	 * 매출/청구/수금 산출 RFC
	 * 
	 * @param paramVO
	 * @param listZSDT0014
	 * @param listZSDT1001
	 * @param model
	 * @return
	 */
	public View save1(MipParameterVO paramVO, ZSDT0014[] listZSDT0014,
			ZSDT1001[] listZSDT1001, Model model) {

		Object obj[] = null;

		ZQMSEMSG[] listMsg = new ZQMSEMSG[] {}; // WEB I/F 처리 오류 결과
		ZSDT1005[] listZSDT1005 = new ZSDT1005[] {}; // 수주계획(보수)
		ZSDT0014S[] listZSDT0014S = new ZSDT0014S[] {}; // 사업계획 DATA (WEB에서 전송)
		ZSDT1012[] listZSDT1012 = new ZSDT1012[] {}; // 사업계획-수주
		ZSDT1023[] listZSDT1023 = new ZSDT1023[] {}; // 사업계획(보수)-수주
		ZSDS0072[] listZSDS0072 = new ZSDS0072[] {}; // [SD] 수주/사업 계획 자동 계산
														// EXPORT
		
		Dataset	ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		ds_error.deleteAll();
		
		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
		
		try {
			// RFC FUCNTION 호출
			obj = new Object[] {
					"S" // 보수/영업 구분
					,
					"" // delete구분(수주계획에선 사용안함)
					,
					"M" // 사업/수주 구분
					,
					"" // 특정필드(TYPE1,TYPE2,TYPE3,RTYPE,SOFOCA) 변경구분(수주계획에서 사용하지
						// 않음)
					, listMsg, listZSDT0014,
					listZSDT1001 // 수주계획
					, listZSDT1005, listZSDT0014S, listZSDT1012, listZSDT1023,
					listZSDS0072 };

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"),
					"hdel.sd.sbp.domain.ZWEB_SD_PLAN_COMPUTE", obj, false);

			Dataset dsError = null; // sap의 에러메시지를 datSet으로 담기위함.
			listMsg = (ZQMSEMSG[]) stub.getOutput("O_ETAB");

			if (listMsg.length != 0) {
				if ("4".equals(listMsg[0].getIDX().toString())) {
					
					for( int i = 0; i < listMsg.length; i++) {
						ds_error.appendRow();
						ds_error.setColumn(i, "ERRCODE", listMsg[i].getIDX().toString());
						ds_error.setColumn(i, "ERRMSG", listMsg[i].getERRMSG());
					}
					resultVO.addDataset(dsError); // 오류결과내역
					model.addAttribute("resultVO", resultVO);
					return view;
					
				}
			}

		} catch (BizException e) {
			e.printStackTrace();
		 	// 비정상종료되어 오류코드가 리턴된 경우 (e.getMessage()에 오류코드만 담겨져 옴)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
			resultVO.addDataset	(ds_error);  	// 오류INFO  
			model.addAttribute	("resultVO", resultVO);    
		} catch (Exception e ) {
			e.printStackTrace();
			// 예기치못한 비정상종료인 경우		 (e.getMessage()에 오류메세지 전체가 담겨져 옴)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			resultVO.addDataset	(ds_error);  	// 오류INFO  
			model.addAttribute	("resultVO", resultVO);    
		}

		return view;
	}

	/**
	 * 매출/청구/수금 산출 금액 RFC
	 * 
	 * @param paramVO
	 * @param dsInputDetail
	 * @param model
	 * @return
	 */
	public View save3(MipParameterVO paramVO, Dataset dsInputDetail, ZSDT0014[] listZSDT0014,
			ZSDT1001[] listZSDT1001, Dataset dsZSDT0072, Model model) {

		Object obj[] = null;

		
		ZQMSEMSG[] listMsg = new ZQMSEMSG[] {}; // WEB I/F 처리 오류 결과
		ZSDT1005[] listZSDT1005 = new ZSDT1005[] {}; // 수주계획(보수)
		ZSDT0014S[] listZSDT0014S = new ZSDT0014S[] {}; // 사업계획 DATA (WEB에서 전송)
		ZSDT1012[] listZSDT1012 = new ZSDT1012[] {}; // 사업계획-수주
		ZSDT1023[] listZSDT1023 = new ZSDT1023[] {}; // 사업계획(보수)-수주
		ZSDS0072[] listZSDS0072 = new ZSDS0072[] {}; // [SD] 수주/사업 계획 자동 계산
		
		Dataset dsInput = dsInputDetail;
		Dataset dsYearm = paramVO.getDataset("ds_yearm");

		Dataset	ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		ds_error.deleteAll();
		
		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return

		try {
			// 매출/청구/수금 구분
			String gbn = "";
			
			// 컬럼ID
			String colId = "";
			String yearm = "";
			int nDetailRow = 0;
			
			// AMT 컬럼별 예상금액
			BigDecimal amt = null;
			BigDecimal zero = new BigDecimal("0.00");

			// 총건수중
			for (int row = 0 ; row < dsInput.getRowCount() ; row++) 
			{
				// 매출/청구/수금 구분
				gbn = DatasetUtility.getString(dsInput, row, "GBN");

				// 컬럼갯수(AMT00 ~ AMT18)
				for (int col = 0; col < dsInput.getColumnCount(); col++) 
				{
					colId = dsInput.getColumnID(col);

					// AMT 컬럼일 경우만 처리
					int rs =  colId.indexOf("AMT");
					if ( rs >= 0 ) 
					{
						//logger.info(" @@@@@ lozic 3 ====>" );
						// 금액항목에 값이 있는 경우에만 실행
						if (StringUtils.isNotBlank(dsInput.getColumnAsString(row, col))) 
						{
							dsZSDT0072.appendRow();

							// AMT 컬럼별 예상금액
							amt = new BigDecimal(dsInput.getColumnAsDouble(row, col));

							// 금액 > 0 인 경우만 등록
							// compareTo :::: -1은 작다, 0은 같다, 1은 크다
							yearm = "";
							if( amt.compareTo(zero) == 1 ) 
							{	
								if ( colId.endsWith("00")) yearm = dsYearm.getColumnAsString( 0, "YEARM");
								if ( colId.endsWith("01")) yearm = dsYearm.getColumnAsString( 1, "YEARM");
								if ( colId.endsWith("02")) yearm = dsYearm.getColumnAsString( 2, "YEARM");
								if ( colId.endsWith("03")) yearm = dsYearm.getColumnAsString( 3, "YEARM");
								if ( colId.endsWith("04")) yearm = dsYearm.getColumnAsString( 4, "YEARM");
								if ( colId.endsWith("05")) yearm = dsYearm.getColumnAsString( 5, "YEARM");
								if ( colId.endsWith("06")) yearm = dsYearm.getColumnAsString( 6, "YEARM");
								if ( colId.endsWith("07")) yearm = dsYearm.getColumnAsString( 7, "YEARM");
								if ( colId.endsWith("08")) yearm = dsYearm.getColumnAsString( 8, "YEARM");
								if ( colId.endsWith("09")) yearm = dsYearm.getColumnAsString( 9, "YEARM");
								if ( colId.endsWith("10")) yearm = dsYearm.getColumnAsString(10, "YEARM");
								if ( colId.endsWith("11")) yearm = dsYearm.getColumnAsString(11, "YEARM");
								if ( colId.endsWith("12")) yearm = dsYearm.getColumnAsString(12, "YEARM");
								if ( colId.endsWith("13")) yearm = dsYearm.getColumnAsString(13, "YEARM");
								if ( colId.endsWith("14")) yearm = dsYearm.getColumnAsString(14, "YEARM");
								if ( colId.endsWith("15")) yearm = dsYearm.getColumnAsString(15, "YEARM");
								if ( colId.endsWith("16")) yearm = dsYearm.getColumnAsString(16, "YEARM");
								if ( colId.endsWith("17")) yearm = dsYearm.getColumnAsString(17, "YEARM");
								if ( colId.endsWith("18")) yearm = dsYearm.getColumnAsString(18, "YEARM");
							}
							
							if ( yearm != null && !"".equals(yearm) )
							{
								dsZSDT0072.setColumn(nDetailRow, "SONNR", dsInput.getColumnAsString(row, "SONNR") );//SONNR);
								dsZSDT0072.setColumn(nDetailRow, "WAERK", dsInput.getColumnAsString(row, "WAERK") );//"KRW");//WAERK);
								dsZSDT0072.setColumn(nDetailRow, "PLANYM", yearm);
								dsZSDT0072.setColumn(nDetailRow, "PLANAMT", amt.doubleValue());
							}
							

							// 계획구분 (SJ : 수주, MC : 매출, CH : 청구, SG : 수금)
							if ("1".equals(gbn)) 
							{
								dsZSDT0072.setColumn(nDetailRow, "PLANTP", "MC");
							} 
							else if ("2".equals(gbn)) 
							{
								dsZSDT0072.setColumn(nDetailRow, "PLANTP", "CH");
							}
							else if ("3".equals(gbn)) 
							{
								dsZSDT0072.setColumn(nDetailRow, "PLANTP", "SG");
							}

							nDetailRow++;
						}
					}
				}// end for
			}
			

			listZSDS0072 = (ZSDS0072[]) SmpComC.moveDs2Obj(dsZSDT0072, ZSDS0072.class, "");
			
			// RFC FUCNTION 호출
			obj = new Object[]{ 
					  "S"	// 영업구분
					, ""
					, "M"	// 사업,수주(M)구분
					, " "
					, listMsg
					, listZSDT0014
					, listZSDT1001
					, listZSDT1005
					, listZSDT0014S
					, listZSDT1012
					, listZSDT1023
					, listZSDS0072
			};
			
			
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"),
					"hdel.sd.sbp.domain.ZWEB_SD_PLAN_COMPUTE", obj, false);

			Dataset dsError = null; // sap의 에러메시지를 datSet으로 담기위함.
			listMsg = (ZQMSEMSG[]) stub.getOutput("O_ETAB");

			if (listMsg.length != 0) 
			{
				if ("4".equals(listMsg[0].getIDX().toString())) 
				{
					for( int i = 0; i < listMsg.length; i++) {
						ds_error.appendRow();
						ds_error.setColumn(i, "ERRCODE", listMsg[i].getIDX().toString());
						ds_error.setColumn(i, "ERRMSG", listMsg[i].getERRMSG());
					}
					resultVO.addDataset(dsError); // 오류결과내역
					model.addAttribute("resultVO", resultVO);
					return view;
				}
			}

		} catch (BizException e) {
			e.printStackTrace();
		 	// 비정상종료되어 오류코드가 리턴된 경우 (e.getMessage()에 오류코드만 담겨져 옴)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
			resultVO.addDataset	(ds_error);  	// 오류INFO  
			model.addAttribute	("resultVO", resultVO);    
		} catch (Exception e ) {
			e.printStackTrace();
			// 예기치못한 비정상종료인 경우		 (e.getMessage()에 오류메세지 전체가 담겨져 옴)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			resultVO.addDataset	(ds_error);  	// 오류INFO  
			model.addAttribute	("resultVO", resultVO);    
		}

		return view;
	}

	/**
	 * 납기예정일로 workingDay
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deldat")
	public View deldat(MipParameterVO paramVO, Model model) {

		Dataset dsDeldat = new Dataset("ds_Deldat");

		ZQMSEMSG[] listMsg = new ZQMSEMSG[] {};
		String I_DATE_FROM = paramVO.getVariable("I_DATE_FROM");
		String I_DAYS = paramVO.getVariable("I_DAYS");
		String I_WERKS = paramVO.getVariable("I_WERKS");

		Dataset dsError = null;

		MipResultVO resultVO = new MipResultVO();

		try {

			// RFC INPUT SET (파라메터 순서엄수)
			Object obj[] = new Object[] { "", I_DATE_FROM, I_DAYS, I_WERKS,
					listMsg };

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"),
					"hdel.sd.sbp.domain.ZWEB_SD_GET_WORKINGDAY", obj, false);

			listMsg = (ZQMSEMSG[]) stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			if (listMsg.length != 0) {
				if ("4".equals(listMsg[0].getIDX().toString())) {
					resultVO.addDataset(dsError); // 오류결과내역
					model.addAttribute("resultVO", resultVO);
					return view;
				}
			}

			dsDeldat.addColumn("C_WORKINGDAY", ColumnInfo.COLTYPE_STRING, 256);
			dsDeldat.appendRow();
			dsDeldat.setColumn(
					0,
					"C_WORKINGDAY",
					(String) stub.getOutput("C_WORKINGDAY").toString()
							.replace("-", ""));

		} catch (CommRfcException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		resultVO.addDataset(dsDeldat);
		model.addAttribute("resultVO", resultVO);

		return view;
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


	/**
	 * 계획년월로 이동계획1차마감여부
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "zpym")
	public View zpym(MipParameterVO paramVO, Model model) {
		String I_ZPYM = paramVO.getVariable("zpym");

		Dataset dsOutput = new Dataset("ds_zclflg");

		MipResultVO resultVO = new MipResultVO();

		try {

			//CW00001=[${}] 항목에 값이 누락되었습니다.\n확인하여 주십시오.
			//CW00002=필수 입력항목입니다.\n확인하여 주십시오.
			if ( "".equals( I_ZPYM ) || I_ZPYM == null )
			{
				throw new BizException("CW00002,계획년월");
			}

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			// parameter 조건 셋팅
			Sbp0160ParamVO paramV = new Sbp0160ParamVO(); // vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT").toString()); // 세션권한
			paramV.setZPYM( I_ZPYM ); // 현재년월
			
			List<Sbp0160VO_N> list = service.selectZclflg(paramV);
			
			SmpComC.moveDs2List(dsOutput, Sbp0160VO_N.class, list );
			dsOutput.setId("ds_zclflg");

		} catch (Exception e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if ( message.length <= 1)
			{
				SmpComC.moveDs2Msg(message[0], message[0], model);
			}
			else
			{
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
		}

		resultVO.addDataset(dsOutput);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
}
