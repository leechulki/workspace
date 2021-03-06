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
	private DutyS dutyService; // duty ????
	@Autowired
	private AutoSoNumberS autoSoService; // ????????

	private Calendar cal;// = Calendar.getInstance();

	/**
	 * ???? ????
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
		Dataset ds_output 	= paramVO.getDataset("ds_output");				// UI?? return?? out dataset  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI?? return?? ?????????? dataset  

		try {

			// CW00001=[${}] ?????? ???? ??????????????.\n???????? ????????.
			// CW00002=???? ??????????????.\n???????? ????????.
			if ("".equals(ds_cond.getColumn(0, "FR_ZPYM").toString())
					|| ds_cond.getColumn(0, "FR_ZPYM").toString() == null) {
				throw new BizException("CW00002,???????? from");
			}
			if ("".equals(ds_cond.getColumn(0, "TO_ZPYM").toString())
					|| ds_cond.getColumn(0, "TO_ZPYM").toString() == null) {
				throw new BizException("CW00001,???????? to");
			}

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO????

			// parameter ???? ????
			Sbp0160ParamVO paramV = new Sbp0160ParamVO(); // vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT").toString()); // ????????
			paramV.setFR_ZPYM(ds_cond.getColumn(0, "FR_ZPYM").toString()); // ????????
																			// from
			paramV.setTO_ZPYM(ds_cond.getColumn(0, "TO_ZPYM").toString()); // ????????
																			// to
			paramV.setZPYM(ds_cond.getColumn(0, "ZPYM").toString()); // ????????
			paramV.setSPART(ds_cond.getColumn(0, "SPART").toString()); // ??????
			paramV.setZKUNNR(ds_cond.getColumn(0, "ZKUNNR").toString()); // ??????????
			paramV.setVKBUR(ds_cond.getColumn(0, "VKBUR").toString()); // ??????
			paramV.setVKGRP(ds_cond.getColumn(0, "VKGRP").toString()); // ????????
			paramV.setZMPFLG(ds_cond.getColumn(0, "ZMPFLG").toString()); // ????????
																			// ????
																			// ????
			paramV.setSORLT(ds_cond.getColumn(0, "SORLT").toString()); // ????????
			paramV.setZAGNT(ds_cond.getColumn(0, "ZAGNT").toString()); // ????????
			paramV.setPGUBN(ds_cond.getColumn(0, "PGUBN").toString()); // ????????
			paramV.setAGNTGB(ds_cond.getColumn(0, "AGNTGB").toString()); // ?????????? ????

			// ?????? ???? Data????
			if ("KO".equals( paramVO.getVariable("G_LANG").toUpperCase() ) )	{
				paramV.setLANG("3");
			}else	{
				paramV.setLANG("E");
			}

			List<Sbp0160VO_N> list = null;

			// ????????(A:??????????,B:??????????,C??????????,D:??????????,E:??????????
			if (ds_cond.getColumn(0, "WGBN").toString().equals("E")) {
				list = service.selectZSDT1001E_N(paramV);
			} else {
				list = service.selectZSDT1001_N(paramV);
			}

			// ????????(list)?? ????????(dsList)?? ???? 
			CallSAPHdel.moveListToDs(ds_output, Sbp0160VO_N.class, list);  
			
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");   
		ds_output.setId		("ds_output");  
		resultVO.addDataset	(ds_error);  		// ????????
		resultVO.addDataset (ds_output);  
		model.addAttribute("resultVO", resultVO);     
		
		return view;
	}

	/**
	 * ???? ????
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
		Dataset ds_output_detail 	= paramVO.getDataset("ds_output_detail");				// UI?? return?? out dataset  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI?? return?? ?????????? dataset  
		
		try {
			// detail
			List<Sbp0161VO_N> list_detail = new ArrayList<Sbp0161VO_N>();
			List<Sbp0161VO_N> list_details = new ArrayList<Sbp0161VO_N>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

					Sbp0161ParamVO param = new Sbp0161ParamVO();
					cal = Calendar.getInstance();
//					d = cal.getTime();

					// ???????? SET
					param.setMANDT(paramVO.getVariable("G_MANDT")); // SAP
																	// CLIENT
					param.setSONNR(ds_cond_detail.getColumn(0, "SONNR").toString()); // ????????????
					param.setM00(ds_cond_detail.getColumn(0, "ZPYM").toString()); // ???????? + 00??
					// ???????? ???? ??????
					cal.setTime(sdf.parse(param.getM00()));
					cal.add(cal.MONTH, 1);
					param.setM01(sdf.format(cal.getTime())); // ???????? + 01??
					cal.add(cal.MONTH, 1);
					param.setM02(sdf.format(cal.getTime())); // ???????? + 02??
					cal.add(cal.MONTH, 1);
					param.setM03(sdf.format(cal.getTime())); // ???????? + 03??
					cal.add(cal.MONTH, 1);
					param.setM04(sdf.format(cal.getTime())); // ???????? + 04??
					cal.add(cal.MONTH, 1);
					param.setM05(sdf.format(cal.getTime())); // ???????? + 05??
					cal.add(cal.MONTH, 1);
					param.setM06(sdf.format(cal.getTime())); // ???????? + 06??
					cal.add(cal.MONTH, 1);
					param.setM07(sdf.format(cal.getTime())); // ???????? + 07??
					cal.add(cal.MONTH, 1);
					param.setM08(sdf.format(cal.getTime())); // ???????? + 08??
					cal.add(cal.MONTH, 1);
					param.setM09(sdf.format(cal.getTime())); // ???????? + 09??
					cal.add(cal.MONTH, 1);
					param.setM10(sdf.format(cal.getTime())); // ???????? + 10??
					cal.add(cal.MONTH, 1);
					param.setM11(sdf.format(cal.getTime())); // ???????? + 11??
					cal.add(cal.MONTH, 1);
					param.setM12(sdf.format(cal.getTime())); // ???????? + 12??
					cal.add(cal.MONTH, 1);
					param.setM13(sdf.format(cal.getTime())); // ???????? + 13??
					cal.add(cal.MONTH, 1);
					param.setM14(sdf.format(cal.getTime())); // ???????? + 14??
					cal.add(cal.MONTH, 1);
					param.setM15(sdf.format(cal.getTime())); // ???????? + 15??
					cal.add(cal.MONTH, 1);
					param.setM16(sdf.format(cal.getTime())); // ???????? + 16??
					cal.add(cal.MONTH, 1);
					param.setM17(sdf.format(cal.getTime())); // ???????? + 17??
					cal.add(cal.MONTH, 1);
					param.setM18(sdf.format(cal.getTime())); // ???????? + 18??

					list_detail = null;
					list_detail = (List<Sbp0161VO_N>) service.selectListDetail_N(param);

					//logger.info(" @@@@ list_detail size not null = : "+list_detail.size() );
					if (list_detail.size() > 0) {

						if ( list_detail.size() != 3 )
						{
							//logger.info("@@@@ ???? ???? ?????? ?????????? ????.");
							String msg = "????/????/?????? ???????? ?????? ???? ???? ??????????????. /n";
									msg += "???????????? ???????????? ???? ????/????/?????? 3?????? ?????? ???????? ???? ??????????.";
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

			// ????????(list)?? ????????(dsList)?? ???? 
			CallSAPHdel.moveListToDs(ds_output_detail, Sbp0161VO_N.class, list_details);  
			
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		MipResultVO resultVO = new MipResultVO();
		ds_error.setId		("ds_error");   
		ds_output_detail.setId("ds_output_detail");  
		resultVO.addDataset	(ds_error);  		// ????????
		resultVO.addDataset (ds_output_detail);  
		model.addAttribute("resultVO", resultVO);     
		
		return view;
	}	
	
	/**
	 * ?????? ????
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "rtype")
	public View findRtype(MipParameterVO paramVO, Model model) {
		// ???? ???? - ????
		String vkbur = paramVO.getVariable("vkbur");

		try {

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO????

			// ????????SET
			Com0060ParamVO param = new Com0060ParamVO(); // VO ????
			param.setMandt(paramVO.getVariable("G_MANDT")); // SAP CLIENT
			param.setFilter1(vkbur); // ????

			List<Com0060ParamVO> list = service.selectRTYPE(param);

			// list?????? dataset???? ????
			Dataset dsOutput = new Dataset();
			SmpComC.moveDs2List(dsOutput, Com0060ParamVO.class, list);

			// RFC ???? dataset?? return
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
	 * ????
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	public View save(MipParameterVO paramVO, Model model) {

		// ???????? dataset
		Dataset dsInput = paramVO.getDataset("ds_input");
		//logger.info(dsInput.toString());

		ZSDT0014[] listZSDT0014 = new ZSDT0014[] {}; // ?? ????????
		ZSDT1001[] listZSDT1001 = new ZSDT1001[] {}; // ????????

		Dataset dsZSDT0014 = ZSDT0014.getDataset(); // sap dataset?????? ??????
		Dataset dsZSDT1001 = ZSDT1001.getDataset(); // sap dataset?????? ??????
		Dataset dsZCOBS001 = ZCOBS001.getDataset(); // sap dataset?????? ??????

		Dataset	ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI?? return?? error out dataset
		ds_error.deleteAll();
		
		MipResultVO resultVO = new MipResultVO(); 	// ???? dataset?? return
		
		// ZSDT1001, ZSDT0014, ZCOBS001 Input Parameter Setting
		try {
			dsZSDT0014.deleteAll(); // ?? ????????
			dsZSDT1001.deleteAll(); // ????????
			dsZCOBS001.deleteAll(); // ????????

			HashMap<String,String> mapInput =null;
			HashMap mapOutput = null ;

			// input????????
			String status = ""; // ????????(insert, normal, update, delete) ?????? ????
			int nRow = 0; // ?? ????????(ZSDT1001, ZSDT0014, ZCOBS001)?? ?????? row
							// number

			// logger.info("@@@@@@@@@ ?????? ?????? ???? :"+dsInput.getRowCount());
			for (int i = 0; i < dsInput.getRowCount(); i++) {
				status = DatasetUtility.getString(dsInput, i, "STATUS");

				// ????(STATUS)?? inser/update/delete?? ???????? ????
				if (status != null
						&& (status.equals("update") || status.equals("insert") || status
								.equals("delete"))) {
					// duty DAO????
					dutyService.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

					// duty in/out?? map
					mapInput = new HashMap<String,String>();
					mapOutput = new HashMap();

					// CW00001=[${}] ?????? ???? ??????????????.\n???????? ????????.
					// CW00002=???? ??????????????.\n???????? ????????.
					if ("".equals(dsInput.getColumn(0, "MATNR").toString())
							|| dsInput.getColumn(0, "MATNR").toString() == null) {
						throw new BizException("CW00002,????????");
					}

					String matnr = dsInput.getColumn(i, "MATNR").toString(); // ????????

					// ???????? ?? ????????
					String dutyGbn = "";

					// ??????????(L-1000), ?????? ??????????(L-2000)
					// ????????????(S-1000), ????????(W-1000)
					if (matnr.equals("L-1000") || matnr.equals("L-2000"))
						dutyGbn = "PCELV";
					else if (matnr.equals("S-1000") || matnr.equals("W-1000"))
						dutyGbn = "PCESMW";
					else
						dutyGbn = "PCELV";

					// ??????????, ?????? ??????????, ????????????, ???????? ?????? ????
					if (dutyGbn.equals("PCELV") || dutyGbn.equals("PCESMW")) {

						// CW00001=[${}] ?????? ???? ??????????????.\n???????? ????????.
						// CW00002=???? ??????????????.\n???????? ????????.
						if ("".equals(dsInput.getColumn(0, "RTYPE").toString())
								|| dsInput.getColumn(0, "RTYPE").toString() == null) {
							throw new BizException("CW00002,????");
						}
						// CW00001=[${}] ?????? ???? ??????????????.\n???????? ????????.
						// CW00002=???? ??????????????.\n???????? ????????.
						if ("".equals(dsInput.getColumn(0, "TYPE1").toString())
								|| dsInput.getColumn(0, "TYPE1").toString() == null) {
							throw new BizException("CW00002,????");
						}
						// CW00001=[${}] ?????? ???? ??????????????.\n???????? ????????.
						// CW00002=???? ??????????????.\n???????? ????????.
						if ("".equals(dsInput.getColumn(0, "TYPE2").toString())
								|| dsInput.getColumn(0, "TYPE2").toString() == null) {
							throw new BizException("CW00002,????");
						}
						// CW00001=[${}] ?????? ???? ??????????????.\n???????? ????????.
						// CW00002=???? ??????????????.\n???????? ????????.
						if ("".equals(dsInput.getColumn(0, "TYPE3").toString())
								|| dsInput.getColumn(0, "TYPE3").toString() == null) {
							throw new BizException("CW00002,????");
						}
					}

					mapInput.put("VKBUR", dsInput.getColumn(i, "VKBUR").toString()); // ????
					mapInput.put("RTYPE", dsInput.getColumn(i, "RTYPE").toString()); // ????
					mapInput.put("TYPE1", dsInput.getColumn(i, "TYPE1").toString()); // ????
					mapInput.put("TYPE2", dsInput.getColumn(i, "TYPE2").toString()); // ????
					mapInput.put("TYPE3", dsInput.getColumn(i, "TYPE3").toString()); // ????
					mapInput.put("MATNR", dsInput.getColumn(i, "MATNR").toString()); // ????????
					mapInput.put("SHANGH", dsInput.getColumn(i, "SHANGH").toString()); // ????????
					mapInput.put("ZINTER", dsInput.getColumn(i, "ZINTER").toString()); //
					
					String zbdtyp = DatasetUtility.getString(dsInput, i, "ZBDTYP");  //????????  jss
					String zagnt  = DatasetUtility.getString(dsInput, i, "ZAGNT");   //??????  jss
					String zdeliD  = dsInput.getColumnAsString( i , "ZDELI");         //???????? jss

					//ZSDTDUTYD.SPEC8 ???????? jss
					if (zagnt.trim().length()>0) zagnt="Y";
					else zagnt="";
					
					//ZSDTDUTYD.SPEC9 ???????? jss
					if ("X".equals(zdeliD)) zdeliD="Y";
					else zdeliD="";
					
					mapInput.put("ZBDTYP", zbdtyp); //???????? jss
					mapInput.put("ZAGNT", zagnt);   //?????????? jss
					mapInput.put("ZDELI", zdeliD);   //???????? jss
					

					// genSpec(mandt, gtype, blockGbn, list)
					mapOutput = dutyService.genSpec( paramVO.getVariable("G_MANDT"), dutyGbn, "00", mapInput, paramVO.getVariable("G_LANG"));

					// rs???? = matnr, ztplno, gtype, auart, spart
					if ("".equals(mapOutput.get("GTYPE").toString())
							|| mapOutput.get("GTYPE").toString() == null) {
						throw new BizException("CW00001,duty-result ?? ????????");
					}
					
					String gtype = mapOutput.get("GTYPE").toString(); // ????????

					// ???????? ?????????? ???? ????
					if (gtype != null && !gtype.equals("")) {
						dsZSDT0014.appendRow(); // ???????? ??????????
						dsZSDT1001.appendRow(); // ????????

						// ???????? ????????(-)??????
						String biddat = DatasetUtility.getString(dsInput, i,
								"ZPYM").substring(0, 4)
								+ "-"
								+ DatasetUtility.getString(dsInput, i, "ZPYM")
										.substring(4) + "-01";

						// deldat ????????(-)??????
						String deldat = DatasetUtility.getString(dsInput, i,
								"DELDAT");
						deldat = deldat.substring(0, 4) + "-"
								+ deldat.substring(4).substring(0, 2) + "-"
								+ deldat.substring(6);

						// ???????? ????(-)??????
						String date = DateTime.getDateString();
						date = date.substring(0, 4) + "-"
								+ date.substring(4).substring(0, 2) + "-"
								+ date.substring(6);

						// ???????? ????(:)??????
						String time = DateTime.getShortTimeString();
						time = time.substring(0, 2) + ":"
								+ time.substring(2).substring(0, 2) + ":"
								+ time.substring(4);

						// ????????????
						String sonnr = "";

						// ????(status)?? insert?? ????
						if (DatasetUtility.getString(dsInput, i, "STATUS")
								.equals("insert")) {
							
							
							autoSoService.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
							
							// ????????(????????????)--------------------------------------------------
							AutoNumberParamVO sonnrParam = new AutoNumberParamVO();
							sonnrParam.setDeptFlag(DatasetUtility.getString(
									dsInput, i, "AUART")); // ????????????
							sonnrParam.setSoQtFlag("1"); // ????????(0:????????, 1:????????
															// , 2:????)
							sonnrParam.setMandt(paramVO.getVariable("G_MANDT")); // SAP
																					// CLIENT
							List<AutoNumberVO> sonnrList = autoSoService
									.AutoSoNumber(sonnrParam);
							// ----------------------------------------------------------------------
							sonnr = sonnrList.get(0).getNumber(); // ???? ?? ????????????

							dsZSDT1001.setColumn(nRow, "CDATE", date); // ????????
							dsZSDT1001.setColumn(nRow, "CTIME", time); // ????????
							dsZSDT1001.setColumn(nRow, "CUSER", paramVO.getVariable("G_USER_ID")); // ??????

							dsZSDT0014.setColumn(nRow, "CDATE",
									dsZSDT1001.getColumn(nRow, "CDATE")); // ????????
							dsZSDT0014.setColumn(nRow, "CTIME",
									dsZSDT1001.getColumn(nRow, "CTIME")); // ????????
							dsZSDT0014.setColumn(nRow, "CUSER",
									dsZSDT1001.getColumn(nRow, "CUSER")); // ??????

						}
						// ????(status)?? update?? ????
						else if (DatasetUtility.getString(dsInput, i, "STATUS")
								.equals("update")) {
							// ???????? ????(-)??????
							String cdate = DatasetUtility.getString(dsInput, i,
									"CDATE");
							cdate = cdate.substring(0, 4) + "-"
									+ cdate.substring(4).substring(0, 2) + "-"
									+ cdate.substring(6);

							// ???????? ????(:)??????
							String ctime = DatasetUtility.getString(dsInput, i,
									"CTIME");
							ctime = ctime.substring(0, 2) + ":"
									+ ctime.substring(2).substring(0, 2) + ":"
									+ ctime.substring(4);

							// ????????????
							sonnr = DatasetUtility.getString(dsInput, i,
									"SONNR");

							dsZSDT1001.setColumn(nRow, "CDATE", cdate); // ????????
							dsZSDT1001.setColumn(nRow, "CTIME", ctime); // ????????
							dsZSDT1001.setColumn(nRow, "CUSER", DatasetUtility.getString(dsInput, i, "CUSER")); // ??????

							dsZSDT0014.setColumn(nRow, "CDATE",
									dsZSDT1001.getColumn(nRow, "CDATE")); // ????????
							dsZSDT0014.setColumn(nRow, "CTIME",
									dsZSDT1001.getColumn(nRow, "CTIME")); // ????????
							dsZSDT0014.setColumn(nRow, "CUSER",
									dsZSDT1001.getColumn(nRow, "CUSER")); // ??????

							dsZSDT1001.setColumn(nRow, "UDATE", date); // ????????
							dsZSDT1001.setColumn(nRow, "UTIME", time); // ????????
							dsZSDT1001.setColumn(nRow, "UUSER",
									paramVO.getVariable("G_USER_ID")); // ??????
							
							dsZSDT0014.setColumn(nRow, "UDATE",
									dsZSDT1001.getColumn(nRow, "UDATE")); // ????????
							dsZSDT0014.setColumn(nRow, "UTIME",
									dsZSDT1001.getColumn(nRow, "UTIME")); // ????????
							dsZSDT0014.setColumn(nRow, "UUSER",
									dsZSDT1001.getColumn(nRow, "UUSER")); // ??????

						}
						// ????(status)?? delete?? ????
						else {
							// ???????? ????(-)??????
							String cdate = DatasetUtility.getString(dsInput, i,
									"CDATE");
							cdate = cdate.substring(0, 4) + "-"
									+ cdate.substring(4).substring(0, 2) + "-"
									+ cdate.substring(6);

							// ???????? ????(:)??????
							String ctime = DatasetUtility.getString(dsInput, i,
									"CTIME");
							ctime = ctime.substring(0, 2) + ":"
									+ ctime.substring(2).substring(0, 2) + ":"
									+ ctime.substring(4);

							// ???????? ????(-)??????
							String udate = DatasetUtility.getString(dsInput, i,
									"UDATE");
							udate = udate.substring(0, 4) + "-"
									+ udate.substring(4).substring(0, 2) + "-"
									+ udate.substring(6);

							// ???????? ????(:)??????
							String utime = DatasetUtility.getString(dsInput, i,
									"UTIME");
							utime = utime.substring(0, 2) + ":"
									+ utime.substring(2).substring(0, 2) + ":"
									+ utime.substring(4);

							// ????????????
							sonnr = DatasetUtility.getString(dsInput, i,
									"SONNR");

							dsZSDT1001.setColumn(nRow, "CDATE", cdate); // ????????
							dsZSDT1001.setColumn(nRow, "CTIME", ctime); // ????????
							dsZSDT1001.setColumn(nRow, "CUSER", DatasetUtility.getString(dsInput, i, "CUSER")); // ??????

							dsZSDT0014.setColumn(nRow, "CDATE",
									dsZSDT1001.getColumn(nRow, "CDATE")); // ????????
							dsZSDT0014.setColumn(nRow, "CTIME",
									dsZSDT1001.getColumn(nRow, "CTIME")); // ????????
							dsZSDT0014.setColumn(nRow, "CUSER",
									dsZSDT1001.getColumn(nRow, "CUSER")); // ??????

							dsZSDT1001.setColumn(nRow, "UDATE", udate); // ????????
							dsZSDT1001.setColumn(nRow, "UTIME", utime); // ????????
							dsZSDT1001.setColumn(nRow, "UUSER", DatasetUtility.getString(dsInput, i, "UUSER"));

							dsZSDT0014.setColumn(nRow, "UDATE",
									dsZSDT1001.getColumn(nRow, "UDATE")); // ????????
							dsZSDT0014.setColumn(nRow, "UTIME",
									dsZSDT1001.getColumn(nRow, "UTIME")); // ????????
							dsZSDT0014.setColumn(nRow, "UUSER",
									dsZSDT1001.getColumn(nRow, "UUSER")); // ??????

							dsZSDT1001.setColumn(nRow, "DDATE", date); // ????????
							dsZSDT1001.setColumn(nRow, "DTIME", time); // ????????
							dsZSDT1001.setColumn(nRow, "DUSER", paramVO.getVariable("G_USER_ID")); // ??????

							dsZSDT0014.setColumn(nRow, "DDATE",dsZSDT1001.getColumn(nRow, "DDATE")); // ????????
							dsZSDT0014.setColumn(nRow, "DTIME",dsZSDT1001.getColumn(nRow, "DTIME")); // ????????
							dsZSDT0014.setColumn(nRow, "DUSER",dsZSDT1001.getColumn(nRow, "DUSER")); // ??????

						}

						// ????????????
						String zmpflg = dsInput.getColumnAsString( i , "ZMPFLG");
						if ( ("X").equals(zmpflg) || ("1").equals(zmpflg) ) 
						{
							dsZSDT1001.setColumn(nRow, "ZMPFLG", "X");
						} 
						else 
						{
							dsZSDT1001.setColumn(nRow, "ZMPFLG", " ");
						}

						// ??????????
						String zdeli = dsInput.getColumnAsString( i , "ZDELI"); 
						if ( ("X").equals(zdeli) || ("1").equals(zdeli) ) 
						{
							dsZSDT1001.setColumn(nRow, "ZDELI", "X");
						} 
						else 
						{
							dsZSDT1001.setColumn(nRow, "ZDELI", " ");
						}

						// ????????????(Y)
						if ("Y".equals(DatasetUtility.getString(dsInput, i, "ZINTER")) ) {
							dsZSDT1001.setColumn(nRow, "ZINTER", "Y");
						} else {
							dsZSDT1001.setColumn(nRow, "ZINTER", " ");
						}

						dsZSDT0014.setColumn(nRow, "ZDELI",
								dsZSDT1001.getColumn(nRow, "ZDELI")); // ??????????
						dsZSDT0014.setColumn(nRow, "ZINTER",
								dsZSDT1001.getColumn(nRow, "ZINTER")); // ????????????(Y)

						dsZSDT1001.setColumn(nRow, "SONNR", sonnr); // ????????????
						dsZSDT0014.setColumn(nRow, "SONNR",
								dsZSDT1001.getColumn(nRow, "SONNR")); // ????????????

						dsZSDT0014.setColumn(nRow, "PSPID",
								dsZSDT1001.getColumn(nRow, "SONNR")); // ????????????

						dsZSDT1001.setColumn(nRow, "MANDT",
								paramVO.getVariable("G_MANDT")); // ??????????
						dsZSDT0014.setColumn(nRow, "MANDT",
								dsZSDT1001.getColumn(nRow, "MANDT")); // ??????????

						dsZSDT1001.setColumn(nRow, "ZPYM",
								DatasetUtility.getString(dsInput, i, "ZPYM")); // ????????
						dsZSDT0014.setColumn(nRow, "BIDYM",
								dsZSDT1001.getColumn(nRow, "ZPYM")); // ????????

						dsZSDT1001.setColumn(nRow, "SPART",
								DatasetUtility.getString(dsInput, i, "SPART")); // ??????
						dsZSDT0014.setColumn(nRow, "SPART",
								dsZSDT1001.getColumn(nRow, "SPART")); // ??????

						dsZSDT1001.setColumn(nRow, "AUART",
								DatasetUtility.getString(dsInput, i, "AUART")); // ????????????
						dsZSDT0014.setColumn(nRow, "AUART",
								dsZSDT1001.getColumn(nRow, "AUART")); // ????????????

						dsZSDT1001.setColumn(nRow, "MATNR", matnr); // ????????
						dsZSDT0014.setColumn(nRow, "MATNR",
								dsZSDT1001.getColumn(nRow, "MATNR")); // ????????

						dsZSDT1001.setColumn(nRow, "VKBUR",
								DatasetUtility.getString(dsInput, i, "VKBUR")); // ????
						dsZSDT0014.setColumn(nRow, "VKBUR",
								dsZSDT1001.getColumn(nRow, "VKBUR")); // ????

						dsZSDT1001.setColumn(nRow, "VKGRP",
								DatasetUtility.getString(dsInput, i, "VKGRP")); // ??
						dsZSDT0014.setColumn(nRow, "VKGRP",
								dsZSDT1001.getColumn(nRow, "VKGRP")); // ??

						dsZSDT1001.setColumn(nRow, "ZKUNNR",
								DatasetUtility.getString(dsInput, i, "ZKUNNR")); // ??????????
						dsZSDT0014.setColumn(nRow, "ZKUNNR",
								dsZSDT1001.getColumn(nRow, "ZKUNNR")); // ??????????

						dsZSDT1001.setColumn(nRow, "GTYPE", gtype); // ????????
						dsZSDT0014.setColumn(nRow, "GTYPE",
								dsZSDT1001.getColumn(nRow, "GTYPE")); // ????????

						dsZSDT1001.setColumn(nRow, "RTYPE",
								DatasetUtility.getString(dsInput, i, "RTYPE")); // ??????
																				// GTYPE_OLD->RTYPE
						dsZSDT0014.setColumn(nRow, "GTYPE_OLD",
								dsZSDT1001.getColumn(nRow, "RTYPE")); // ??????
																		// GTYPE_OLD->RTYPE

						dsZSDT1001.setColumn(nRow, "TYPE1",
								DatasetUtility.getString(dsInput, i, "TYPE1")); // ????1
						dsZSDT0014.setColumn(nRow, "GSPEC1",
								dsZSDT1001.getColumn(nRow, "TYPE1")); // ????1

						dsZSDT1001.setColumn(nRow, "TYPE2",
								DatasetUtility.getString(dsInput, i, "TYPE2")); // ????2
						dsZSDT0014.setColumn(nRow, "GSPEC2",
								dsZSDT1001.getColumn(nRow, "TYPE2")); // ????2

						dsZSDT1001.setColumn(nRow, "TYPE3",
								DatasetUtility.getString(dsInput, i, "TYPE3")); // ????3
						dsZSDT0014.setColumn(nRow, "GSPEC3",
								dsZSDT1001.getColumn(nRow, "TYPE3")); // ????3

						dsZSDT1001
								.setColumn(nRow, "ZNUMBER", DatasetUtility
										.getDouble(dsInput, i, "ZNUMBER")); // ????
						dsZSDT0014.setColumn(nRow, "ZNUMBER",
								dsZSDT1001.getColumn(nRow, "ZNUMBER")); // ????

						dsZSDT1001.setColumn(nRow, "KUNNR",
								DatasetUtility.getString(dsInput, i, "KUNNR")); // ????
						dsZSDT0014.setColumn(nRow, "KUNNR",
								dsZSDT1001.getColumn(nRow, "KUNNR")); // ????

						dsZSDT1001.setColumn(nRow, "ZAGNT",
								DatasetUtility.getString(dsInput, i, "ZAGNT")); // ??????
						dsZSDT0014.setColumn(nRow, "ZAGNT",
								dsZSDT1001.getColumn(nRow, "ZAGNT")); // ??????

						dsZSDT1001.setColumn(nRow, "LAND1",
								DatasetUtility.getString(dsInput, i, "LAND1")); // ????
						dsZSDT0014.setColumn(nRow, "LAND1",
								dsZSDT1001.getColumn(nRow, "LAND1")); // ????

						dsZSDT1001.setColumn(nRow, "GSNAM",
								DatasetUtility.getString(dsInput, i, "GSNAM")); // ??????
						dsZSDT0014.setColumn(nRow, "GSNAM",
								dsZSDT1001.getColumn(nRow, "GSNAM")); // ??????

						dsZSDT1001.setColumn(nRow, "REGION",
								DatasetUtility.getString(dsInput, i, "REGION")); // ????????
						dsZSDT0014.setColumn(nRow, "REGION",
								dsZSDT1001.getColumn(nRow, "REGION")); // ????????

						dsZSDT1001.setColumn(nRow, "SHANGH",
								DatasetUtility.getString(dsInput, i, "SHANGH")); // ????/????????
						dsZSDT0014.setColumn(nRow, "SHANGH",
								dsZSDT1001.getColumn(nRow, "SHANGH")); // ????/????????

						dsZSDT1001.setColumn(nRow, "SOFOCA",
								DatasetUtility.getString(dsInput, i, "SOFOCA"));// ??????????

						// ??????????
						double tempDbl = 0;
						if (DatasetUtility.getDouble(dsInput, i, "SOFOCA") == 0) {
							dsZSDT0014.setColumn(nRow, "SOFOCA", 0);
						} else {
							// ????(WAERK)?????? ???? ???????? ????
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

						dsZSDT1001.setColumn(nRow, "ZFORE", DatasetUtility.getString(dsInput, i, "ZFORE")); // ??????????
						dsZSDT0014.setColumn(nRow, "ZFORE", dsZSDT1001.getColumn(nRow, "ZFORE")); // ??????????

						dsZSDT1001.setColumn(nRow, "WAERK", DatasetUtility.getString(dsInput, i, "WAERK")); // ????
						dsZSDT0014.setColumn(nRow, "WAERK", dsZSDT1001.getColumn(nRow, "WAERK")); // ????

						dsZSDT1001.setColumn(nRow, "DELDAT", deldat); // ??????????
						dsZSDT0014.setColumn(nRow, "DELDAT", dsZSDT1001.getColumn(nRow, "DELDAT")); // ??????????

						dsZSDT1001.setColumn(nRow, "ZRMK", DatasetUtility.getString(dsInput, i, "ZRMK")); // ????

						dsZSDT1001.setColumn(nRow, "SOABLE", DatasetUtility.getString(dsInput, i, "SOABLE")); // ??????????
						dsZSDT0014.setColumn(nRow, "SOABLE", dsZSDT1001.getColumn(nRow, "SOABLE")); // ??????????

						dsZSDT1001.setColumn(nRow, "SORLT", DatasetUtility.getString(dsInput, i, "SORLT")); // ????????
						dsZSDT0014.setColumn(nRow, "SORLT", dsZSDT1001.getColumn(nRow, "SORLT")); // ????????

						// ????????????????
						String zclflg = "";
						logger.info(" @@@@@ ZCLFLG : "+DatasetUtility.getString(dsInput, i, "ZCLFLG") );
						if (DatasetUtility.getString(dsInput, i, "ZCLFLG").equals("X")) 
						{
							zclflg = "????";
						} else {
							zclflg = "????";
						}
						dsZSDT1001.setColumn(nRow, "PGUBN", zclflg); // ????????

						dsZSDT1001.setColumn(nRow, "SOPRICE", DatasetUtility
										.getDouble(dsInput, i, "SOPRICE"));// ????????

						// ????????
						tempDbl = 0;
						if (DatasetUtility.getDouble(dsInput, i, "SOPRICE") == 0) {
							dsZSDT0014.setColumn(nRow, "SOPRICE", 0);
						} else {
							// ????(WAERK)?????? ???? ???????? ????
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

						dsZSDT1001.setColumn(nRow, "SHANG", DatasetUtility.getString(dsInput, i, "SHANG")); // ??????????
						dsZSDT0014.setColumn(nRow, "SHANG", dsZSDT1001.getColumn(nRow, "SHANG")); // ??????????

						dsZSDT1001.setColumn(nRow, "VBELN", DatasetUtility.getString(dsInput, i, "VBELN")); // ????????
						dsZSDT0014.setColumn(nRow, "VBELN", dsZSDT1001.getColumn(nRow, "VBELN")); // ????????

						dsZSDT0014.setColumn(nRow, "POSID", f_posid_make(sonnr, matnr)); // ????????
						dsZSDT0014.setColumn(nRow, "BIDDAT", biddat); // ??????????
						dsZSDT0014.setColumn(nRow, "NAME1", DatasetUtility.getString(dsInput, i, "KUNNR_NM"));// ??????

						dsZSDT1001.setColumn(nRow, "ZBPNNR", DatasetUtility.getString(dsInput, i, "ZBPNNR")); // ????????????
						dsZSDT0014.setColumn(nRow, "ZBPNNR", dsZSDT1001.getColumn(nRow, "ZBPNNR")); // ????????????

						dsZSDT1001.setColumn(nRow, "ZCOST", DatasetUtility.getString(dsInput, i, "ZCOST")); // ????
						dsZSDT0014.setColumn(nRow, "ZCOST", dsZSDT1001.getColumn(nRow, "ZCOST")); // ????

						// ????(rtype), ????(type1), ????(type2), ????(type3), ??????????(sofoca)?? ?????? ?????? ????('X')
						dsZSDT0014.setColumn(nRow, "ENTP3", DatasetUtility.getString(dsInput, i, "UFLAG")); 
						
						dsZSDT0014.setColumn(nRow, "ZBDTYP", zbdtyp); // ???????? jss
						dsZSDT1001.setColumn(nRow, "ZBDTYP", zbdtyp); // ???????? jss
						dsZSDT0014.setColumn(nRow, "ZPYEAR", ""); // jss
						dsZSDT1001.setColumn(nRow, "ZPYEAR", ""); // jss

						dsZSDT1001.setColumn(nRow, "SID", DatasetUtility.getString(dsInput, i, "SID")); //??????????
						dsZSDT0014.setColumn(nRow, "SID", dsZSDT1001.getColumn(nRow, "SID")); //??????????
						
						nRow++;
					}
					// ???????????? ???????? ????
					else 
					{
						logger.info("@@@@@@@@@ duty???? ???????????? ???????? ????.");
					}

				}
			}// end for

			// ????/????/???? ???? RFC
			listZSDT0014 = (ZSDT0014[]) SmpComC.moveDs2Obj(dsZSDT0014, ZSDT0014.class, "");
			listZSDT1001 = (ZSDT1001[]) SmpComC.moveDs2Obj(dsZSDT1001, ZSDT1001.class, "");

			// ???????? dataset
			// ????/????/???? ???? RFC
			Dataset dsZSDT0072 = ZSDS0072.getDataset();
			
			// ???????? ???? ???????? addrow?? ??????
			// ????/????/?????? ???? ???????? ???????? ???????? ???????? ?????? ????.
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
		 	// ?????????????? ?????????? ?????? ???? (e.getMessage()?? ?????????? ?????? ??)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
			resultVO.addDataset	(ds_error);  	// ????INFO  
			model.addAttribute	("resultVO", resultVO);    
		} catch (NoMatchException e) {
			e.printStackTrace();
		 	// ?????????????? ?????????? ?????? ???? (e.getMessage()?? ?????????? ?????? ??)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), e.getMessage(), "Y", "Y"); 
			// ???????? ????
			resultVO.addDataset	(ds_error);  	// ????INFO  
			model.addAttribute	("resultVO", resultVO);    
		} catch (Exception e ) {
			e.printStackTrace();
			// ?????????? ???????????? ????		 (e.getMessage()?? ?????????? ?????? ?????? ??)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			resultVO.addDataset	(ds_error);  	// ????INFO  
			model.addAttribute	("resultVO", resultVO);    
		}


		return view;
	}

	/**
	 * ????/????/???? ???? RFC
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

		ZQMSEMSG[] listMsg = new ZQMSEMSG[] {}; // WEB I/F ???? ???? ????
		ZSDT1005[] listZSDT1005 = new ZSDT1005[] {}; // ????????(????)
		ZSDT0014S[] listZSDT0014S = new ZSDT0014S[] {}; // ???????? DATA (WEB???? ????)
		ZSDT1012[] listZSDT1012 = new ZSDT1012[] {}; // ????????-????
		ZSDT1023[] listZSDT1023 = new ZSDT1023[] {}; // ????????(????)-????
		ZSDS0072[] listZSDS0072 = new ZSDS0072[] {}; // [SD] ????/???? ???? ???? ????
														// EXPORT
		
		Dataset	ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI?? return?? error out dataset
		ds_error.deleteAll();
		
		MipResultVO resultVO = new MipResultVO(); 	// ???? dataset?? return
		
		try {
			// RFC FUCNTION ????
			obj = new Object[] {
					"S" // ????/???? ????
					,
					"" // delete????(???????????? ????????)
					,
					"M" // ????/???? ????
					,
					"" // ????????(TYPE1,TYPE2,TYPE3,RTYPE,SOFOCA) ????????(???????????? ????????
						// ????)
					, listMsg, listZSDT0014,
					listZSDT1001 // ????????
					, listZSDT1005, listZSDT0014S, listZSDT1012, listZSDT1023,
					listZSDS0072 };

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"),
					"hdel.sd.sbp.domain.ZWEB_SD_PLAN_COMPUTE", obj, false);

			Dataset dsError = null; // sap?? ???????????? datSet???? ????????.
			listMsg = (ZQMSEMSG[]) stub.getOutput("O_ETAB");

			if (listMsg.length != 0) {
				if ("4".equals(listMsg[0].getIDX().toString())) {
					
					for( int i = 0; i < listMsg.length; i++) {
						ds_error.appendRow();
						ds_error.setColumn(i, "ERRCODE", listMsg[i].getIDX().toString());
						ds_error.setColumn(i, "ERRMSG", listMsg[i].getERRMSG());
					}
					resultVO.addDataset(dsError); // ????????????
					model.addAttribute("resultVO", resultVO);
					return view;
					
				}
			}

		} catch (BizException e) {
			e.printStackTrace();
		 	// ?????????????? ?????????? ?????? ???? (e.getMessage()?? ?????????? ?????? ??)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
			resultVO.addDataset	(ds_error);  	// ????INFO  
			model.addAttribute	("resultVO", resultVO);    
		} catch (Exception e ) {
			e.printStackTrace();
			// ?????????? ???????????? ????		 (e.getMessage()?? ?????????? ?????? ?????? ??)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			resultVO.addDataset	(ds_error);  	// ????INFO  
			model.addAttribute	("resultVO", resultVO);    
		}

		return view;
	}

	/**
	 * ????/????/???? ???? ???? RFC
	 * 
	 * @param paramVO
	 * @param dsInputDetail
	 * @param model
	 * @return
	 */
	public View save3(MipParameterVO paramVO, Dataset dsInputDetail, ZSDT0014[] listZSDT0014,
			ZSDT1001[] listZSDT1001, Dataset dsZSDT0072, Model model) {

		Object obj[] = null;

		
		ZQMSEMSG[] listMsg = new ZQMSEMSG[] {}; // WEB I/F ???? ???? ????
		ZSDT1005[] listZSDT1005 = new ZSDT1005[] {}; // ????????(????)
		ZSDT0014S[] listZSDT0014S = new ZSDT0014S[] {}; // ???????? DATA (WEB???? ????)
		ZSDT1012[] listZSDT1012 = new ZSDT1012[] {}; // ????????-????
		ZSDT1023[] listZSDT1023 = new ZSDT1023[] {}; // ????????(????)-????
		ZSDS0072[] listZSDS0072 = new ZSDS0072[] {}; // [SD] ????/???? ???? ???? ????
		
		Dataset dsInput = dsInputDetail;
		Dataset dsYearm = paramVO.getDataset("ds_yearm");

		Dataset	ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI?? return?? error out dataset
		ds_error.deleteAll();
		
		MipResultVO resultVO = new MipResultVO(); 	// ???? dataset?? return

		try {
			// ????/????/???? ????
			String gbn = "";
			
			// ????ID
			String colId = "";
			String yearm = "";
			int nDetailRow = 0;
			
			// AMT ?????? ????????
			BigDecimal amt = null;
			BigDecimal zero = new BigDecimal("0.00");

			// ????????
			for (int row = 0 ; row < dsInput.getRowCount() ; row++) 
			{
				// ????/????/???? ????
				gbn = DatasetUtility.getString(dsInput, row, "GBN");

				// ????????(AMT00 ~ AMT18)
				for (int col = 0; col < dsInput.getColumnCount(); col++) 
				{
					colId = dsInput.getColumnID(col);

					// AMT ?????? ?????? ????
					int rs =  colId.indexOf("AMT");
					if ( rs >= 0 ) 
					{
						//logger.info(" @@@@@ lozic 3 ====>" );
						// ?????????? ???? ???? ???????? ????
						if (StringUtils.isNotBlank(dsInput.getColumnAsString(row, col))) 
						{
							dsZSDT0072.appendRow();

							// AMT ?????? ????????
							amt = new BigDecimal(dsInput.getColumnAsDouble(row, col));

							// ???? > 0 ?? ?????? ????
							// compareTo :::: -1?? ????, 0?? ????, 1?? ????
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
							

							// ???????? (SJ : ????, MC : ????, CH : ????, SG : ????)
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
			
			// RFC FUCNTION ????
			obj = new Object[]{ 
					  "S"	// ????????
					, ""
					, "M"	// ????,????(M)????
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

			Dataset dsError = null; // sap?? ???????????? datSet???? ????????.
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
					resultVO.addDataset(dsError); // ????????????
					model.addAttribute("resultVO", resultVO);
					return view;
				}
			}

		} catch (BizException e) {
			e.printStackTrace();
		 	// ?????????????? ?????????? ?????? ???? (e.getMessage()?? ?????????? ?????? ??)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y"); 
			resultVO.addDataset	(ds_error);  	// ????INFO  
			model.addAttribute	("resultVO", resultVO);    
		} catch (Exception e ) {
			e.printStackTrace();
			// ?????????? ???????????? ????		 (e.getMessage()?? ?????????? ?????? ?????? ??)
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			resultVO.addDataset	(ds_error);  	// ????INFO  
			model.addAttribute	("resultVO", resultVO);    
		}

		return view;
	}

	/**
	 * ???????????? workingDay
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

			// RFC INPUT SET (???????? ????????)
			Object obj[] = new Object[] { "", I_DATE_FROM, I_DAYS, I_WERKS,
					listMsg };

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"),
					"hdel.sd.sbp.domain.ZWEB_SD_GET_WORKINGDAY", obj, false);

			listMsg = (ZQMSEMSG[]) stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			if (listMsg.length != 0) {
				if ("4".equals(listMsg[0].getIDX().toString())) {
					resultVO.addDataset(dsError); // ????????????
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
	 * ????????????, ?????????? ???????? make???? return
	 * 
	 * @param sonnr
	 * @param matnr
	 * @return
	 */
	private String f_posid_make(String sonnr, String matnr) {
		// ????????
		String posid = "";
		if ("NS-100".equals(matnr)) {
			posid = sonnr + "NS";
		} else {
			posid = sonnr + StringUtils.substring(matnr, 0, 1) + "01";
		}
		return posid;
	}


	/**
	 * ?????????? ????????1??????????
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

			//CW00001=[${}] ?????? ???? ??????????????.\n???????? ????????.
			//CW00002=???? ??????????????.\n???????? ????????.
			if ( "".equals( I_ZPYM ) || I_ZPYM == null )
			{
				throw new BizException("CW00002,????????");
			}

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO????

			// parameter ???? ????
			Sbp0160ParamVO paramV = new Sbp0160ParamVO(); // vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT").toString()); // ????????
			paramV.setZPYM( I_ZPYM ); // ????????
			
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
