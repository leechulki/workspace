package com.helco.xf.cs12.ws;

import java.math.BigDecimal;

import com.tobesoft.platform.data.Dataset;

import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;
import com.helco.xf.cs12.evladm.dbutil.*;
import com.helco.xf.comm.Utillity;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.business.config.ConfigUtility;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.StringOperator;
import tit.util.TitUtility;

public class CS1201001B_ServiceImpl extends AbstractBusinessService implements CS1201001B_Service {
	static Logger logger;

	public Dataset searchComboCst(Dataset ds) throws Exception {
		String sql = "SELECT CS121_CST CST FROM SAPHEE.ZCST121 WHERE 1 = 1";
		
		// NULL이 아닌지 여부 확인 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sql += " AND MANDT = #MANDT#";
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sql += " AND CS121_UPN = #UPN#";
		}

		// SqlRequest 생성 
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
		
		// 파라메터 설정하기 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sqlRequest.addParamObject("MANDT", DatasetUtility.getString(ds, "MANDT"));
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sqlRequest.addParamObject("UPN", DatasetUtility.getString(ds, "UPN"));
		}
		
		String mdt = DatasetUtility.getString(ds, "MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		// 조회 처리 
		SqlExecutor db = new DatasetSqlExecutor(getConnection(db_con));
		SqlResult result = db.query(sqlRequest);
		
		// 조회 결과 객체 찾아오기 
		Dataset returnDs = (Dataset)result.getResultObject();
		
		// 사용자에게 값 넘기기 
		return returnDs;
	}

	public Dataset searchNamSpt(Dataset ds) throws Exception {
		String sql = "SELECT CS121_NAM NAM, CS121_SPT SPT FROM SAPHEE.ZCST121 WHERE 1 = 1";

		// NULL이 아닌지 여부 확인 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sql += " AND MANDT = #MANDT#";
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sql += " AND CS121_UPN = #UPN#";
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "CST"))) {
			sql += " AND CS121_CST = #CST#";
		} else {
			sql += " AND CS121_CST = ''";
		}
		
		// SqlRequest 생성
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
		
		// 파라메터 설정하기 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sqlRequest.addParamObject("MANDT", DatasetUtility.getString(ds, "MANDT"));
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sqlRequest.addParamObject("UPN", DatasetUtility.getString(ds, "UPN"));
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "CST"))) {
			sqlRequest.addParamObject("CST", DatasetUtility.getString(ds, "CST"));
		}

		String mdt = DatasetUtility.getString(ds, "MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		DatasetSqlRequest zcst121s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S63");//X
		
		zcst121s.addParamObject("ds_list", ds);
		zcst121s.addParamObject("G_MANDT", mdt);
		
		Dataset returnDs = (Dataset)executor.query(zcst121s).getResultObject();
		
		/*********************************************************************
		// 조회 처리 
		SqlExecutor db = new DatasetSqlExecutor(getConnection(db_con));
		SqlResult result = db.query(sqlRequest);
		
		// 조회 결과 객체 찾아오기 
		Dataset returnDs = (Dataset)result.getResultObject();
		**********************************************************************/
		// 사용자에게 값 넘기기 
		return returnDs;
	}

	public void save(BusinessContext ctx, Dataset ds_gbn, Dataset ds_list, Dataset ds_lists) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		// 수주 12개월 초과 확인 로직 , 전국 권한에 대해 해제 20170829
		// 2020.06.17 수주차감개월수 로직 수정으로 인한 주석처리.
		// 2020.06.29 이준석 과장 요청으로 수주보전로직 동작하지 않도록 변경. Han JH
		String except_flag = ds_gbn.getColumnAsString(0, "EXCEPT");
		
		// 현재 계약의 영업그룹 세팅. 2020.06.17 Han JH
		String bf_bsu = ""; // 이전 보수협력사 영업그룹
		String af_bsu = ""; // 현 보수협력사 영업그룹
		
		if(TitUtility.isNotNull(ds_gbn.getColumnAsString(0, "BF_BSU"))) {
			bf_bsu = ds_gbn.getColumnAsString(0, "BF_BSU");
		}
		
		if(TitUtility.isNotNull(ds_gbn.getColumnAsString(0, "AF_BSU"))) {
			af_bsu = ds_gbn.getColumnAsString(0, "AF_BSU");
		}
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 중지현장보고 Detail select(zcst146)
		DatasetSqlRequest zcst146s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S19");//X
		zcst146s.addParamObject("ds_list", ds_list);

		// 실패현장보고 Detail select(zcst142)
		DatasetSqlRequest zcst142s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S18");//X
		zcst142s.addParamObject("ds_list", ds_list);

		// 유상보수계약정보 select(zcst126)
		DatasetSqlRequest zcst126s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S13");//X
		zcst126s.addParamObject("ds_list", ds_list);
		
		// 무상발주계약정보 select(zcst116)
		DatasetSqlRequest zcst116s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S21");//X
		zcst116s.addParamObject("ds_list", ds_list);

		// 유상보수계약정보 insert(zcst126)
		DatasetSqlRequest zcst126i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I1");//O
		zcst126i.addParamObject("ds_list", ds_list);

		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U7");//O
		zcst126u.addParamObject("ds_list", ds_list);
		
		// 마스터호기정보 select(zmaster02)
		DatasetSqlRequest zmaster02s 
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S24");//X
		zmaster02s.addParamObject("ds_list", ds_list);

		// 유상보수계약정보 select(zcst126)
		DatasetSqlRequest zcst126s2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S26");//X
		zcst126s2.addParamObject("ds_list", ds_list);
		
		// 유상보수계약정보 select(zcst126)
		DatasetSqlRequest zcst126s3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S28");//X
		zcst126s3.addParamObject("ds_list", ds_list);
		
		// 기종교체정보 select(zcst172)
		DatasetSqlRequest zcst172s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S29");//X
		zcst172s.addParamObject("ds_list", ds_list);

		// 유상보수계약정보 select(zcst126)_20121108 LHR
		DatasetSqlRequest zcst126s4
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S36");//X
		zcst126s4.addParamObject("ds_list", ds_list);
		
		// 폐업신고 사업장 select(zsdt0500)_20140903 YAR
		DatasetSqlRequest zsdt0500s1
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S50");//X
		zsdt0500s1.addParamObject("ds_list", ds_list);
				
		// 해당년도 유상계약개월수 sum 값 select(zcst126)_20141014 YAR
		DatasetSqlRequest zcst126s51
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S51");//X
		zcst126s51.addParamObject("ds_list", ds_list);
		
		// 전년도 수주 당해년도 만료 현장 여부 확인_20170919 이하림
		DatasetSqlRequest zcst126s51c
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S51C");//X	
		zcst126s51c.addParamObject("ds_list", ds_list);
		zcst126s51c.addParamObject("G_MANDT", mdt);
		
		// 전년도 수주 당해년도 만료 현장-12개월 초과 입력 가능 여부 확인 select(zcst126)_20170919 이하림
		DatasetSqlRequest zcst126s51n
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S51N");//X
		zcst126s51n.addParamObject("ds_list", ds_list);
		zcst126s51n.addParamObject("G_MANDT", mdt);

		// 이관 기안 진행 여부 체크 select(zcst157)_201506 LHR 
		DatasetSqlRequest zcst157s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S55");//X
		zcst157s.addParamObject("ds_list", ds_list);
		
		// 동일년월 계약 여부 체크 select(zcst126)_20160202 YAR
		DatasetSqlRequest zcst126s5
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S58");//X
		zcst126s5.addParamObject("ds_list", ds_list);
		
		// 전계약 단가 가져오기 select(zcst126)_201601012
		DatasetSqlRequest zcst126s6
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S60");//
		zcst126s6.addParamObject("ds_list", ds_list);
		
		// 기술용역 체크  select(zcst121) 201601102
		DatasetSqlRequest zcst121s1
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S63");//X
		zcst121s1.addParamObject("ds_list", ds_list);
				
		zcst146s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst146s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst142s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst142s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zmaster02s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst172s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst172s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zsdt0500s1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zsdt0500s1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s51.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s51.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
				
		zcst157s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst157s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s5.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s5.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s6.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s6.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst121s1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		// ***********  SEG 표준단가 가져오기   ***********  //
		CS1299001A_ServiceImpl getSegData =  new CS1299001A_ServiceImpl();
		//Dataset ds_hnoinfo = ds_list.
		getSegData.getStdPrice2(ctx,ds_list);
		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("I".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				ds_list.setColumn(i, "UMS", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS"))));
				ds_list.setColumn(i, "MMN", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "MMN"))));
				ds_list.setColumn(i, "JKH", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "JKH"))));
//				ds_list.setColumn(i, "RTO", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "RTO"))));

				String t_gkd = "";			
				String t_gnd = "";			
				t_gkd = CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "GKD"));
				t_gnd = CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "GND"));

				zcst126s2.setRowIndex(i);
				Dataset dsRtn126s = (Dataset)executor.query(zcst126s2).getResultObject();
				
				zmaster02s.setRowIndex(i);
				Dataset dsRtn02 = (Dataset)executor.query(zmaster02s).getResultObject();	

				if(t_gnd.trim().equals("D")) { //유상일반 계약일 경우만 체크(2010.08.23 최인식대리 요청)
					if(t_gkd.trim().equals("1") && dsRtn126s.getColumnAsString(0, "GKD").trim().equals("1")) { //계약구분 체크
						if(Integer.parseInt(dsRtn02.getColumnAsString(0, "CNT")) == 0) { //무상 잔여개월 수 있는 경우 승인 오류 발생
							if(Integer.parseInt(dsRtn02.getColumnAsString(0, "MG_CNT")) > 0 || Integer.parseInt(dsRtn02.getColumnAsString(0, "MI_CNT")) > 0 ){
								throw new BizException(String.valueOf(i + 1) + " 번째 호기는 무상잔여개월수 소진 후 진행하시길 바랍니다. 저장처리를 취소합니다.");
							}
							else{
								throw new BizException(String.valueOf(i + 1) + " 번째 호기는 무상발주 데이터가 존재하지 않습니다. 저장처리를 취소합니다.");
							}
						}
					}
				}
				//폐업신고된 사업장인 경우 Exception YAR
				zsdt0500s1.setRowIndex(i);
				Dataset dsRtn500 = (Dataset)executor.query(zsdt0500s1).getResultObject();

				if(Integer.parseInt(dsRtn500.getColumnAsString(0, "CNT")) > 0) { //페업 신고된 사업장인 경우 Exception
					throw new BizException("       폐업 신고된 사업장 입니다.  (폐업 신고일: " + Integer.parseInt(dsRtn500.getColumnAsString(0, "DAT")) +")" );
				}
				
/* *********************************************************************
 * 20170919 이하림    
 * ****************************************************************Start
 * 1. 기준년도 이전년도 수주    cs126_ugs < 기준년도  && cs126_adt < 기준년도  
 * 2. 기준년도 만료 계약           cs126_umr = 기준년도
 * 3. 기준년도 중도해지 이력    cs126_cha <> ''
 *  위 3가지가 모두 충족하는 경우 차감 개월수 만큼 계약 등록 이후, 연 수주 12개월 초과 가능
 *  해당하지 않는 경우 기존의 연 수주 12개월 초과 로직 적용
 *  ********************************************************************
 *  견적의 경우 연차계획 재성성 로직 개발 적용 필 
 *  관리팀의 경우만 신규 로직 적용 ( 테스트 후 전체 적용 20170919 )
 * **********************************************************************/
				// ***********  전계약단가 가져오기   ***********  //
				zcst126s6.setRowIndex(i);
				Dataset dsRtn1266 = (Dataset)executor.query(zcst126s6).getResultObject();
				
				double p_amt = 0;   // 이전계약 - 유지보수료(직영)
				double p_ambt = 0;  // 이전계약 - 유지보수료(협력사)
				double p_amtt = 0;  // 이전계약 - 유지보수료
				String p_knd = "";  // 이전계약 - FM 여부
				String p_hyn = "";  // 이전계약 - HRTS
				//===========================공기청정기 추가 20200515 Han.JH==================================
				String p_acyn = "";  // 이전계약 - 공기청정기 여부
				//======================================================================================
				
				if(dsRtn1266.getRowCount() > 0){
					p_amt  = dsRtn1266.getColumnAsDouble(0, "CS126_AMT");   // 이전계약 - 유지보수료(직영)
					p_ambt = dsRtn1266.getColumnAsDouble(0, "CS126_AMBT");  // 이전계약 - 유지보수료(협력사)
					p_amtt = dsRtn1266.getColumnAsDouble(0, "CS126_AMTT");  // 이전계약 - 유지보수료
					p_knd = dsRtn1266.getColumnAsString(0, "CS126_KND");    // 이전계약 - FM 여부
					p_hyn = dsRtn1266.getColumnAsString(0, "CS126_HYN");    // 이전계약 - HRTS
					//===========================공기청정기 추가 20200515 Han.JH==================================
					p_acyn = dsRtn1266.getColumnAsString(0, "CS126_ACYN");    // 이전계약 - 공기청정기 여부
					//======================================================================================
				}
								
				double ds_amt  = ds_list.getColumnAsDouble(i, "AMT") ;       // 현재 계약(직영)
				double ds_ambt = ds_list.getColumnAsDouble(i, "AMBT") ;      // 현재 계약(협력사)
				double ds_amtt = ds_list.getColumnAsDouble(i, "AMTT") ;      // 현재 계약
				String ds_knd = ds_list.getColumnAsString(i, "KND") ;        // 현재 계약
				String ds_hyn = ds_list.getColumnAsString(i, "HYN") ;        // 현재 계약
				//===========================공기청정기 추가 20200515 Han.JH==================================
				String ds_acyn = ds_list.getColumnAsString(i, "ACYN") ;        // 현재 계약(공기청정기)
				//======================================================================================
				String chk_amt = "";
				
				/* HRTS 여부 체크시 클라이언트에서 수신된 값과 서버에서 구한값의 불일치로 보정 20200608 Han J.H */
				if(ds_hyn.equals("1")) {
					ds_hyn = "Y";
				}
				
				//if (p_amt == ds_amt) chk_amt = "Y";
				// 전 계약의 유지보수료, FM 여부, HRTS 동일 여부 체크
				if (p_amt == ds_amt && p_knd.endsWith(ds_knd) && p_hyn.endsWith(ds_hyn) && p_acyn.endsWith(ds_acyn)) chk_amt = "Y";
				
				zcst126s51c.setRowIndex(i);
				Dataset dsRtnChk = (Dataset)executor.query(zcst126s51c).getResultObject();	
				
				int chk = Integer.parseInt(dsRtnChk.getColumnAsString(0, "CNT"));
				int estno_len = ds_list.getColumnAsString(i, "SIR").length();
				
				// 이준석 과장이 아니면 수주보전 로직을 작동시킴 20200629 Han JH
				if(!except_flag.equals("Y")) {
					
					//if(chk > 0 && estno_len == 0) {
					//if( chk > 0  && chk_amt.endsWith("Y")) {
					// 이전계약이 모회사이고 현계약이 자회사인 경우 수주보전을 하지 않으므로 아래의 수주보전 로직은 수행하지 않음.
					if(!(bf_bsu.equals("H100")) && af_bsu.equals("H100")) {
						// 자회사 수주보전 안함
						//if(af_bsu.equals("H100")) { // 이관(ZCST157) 데이터의 정합성 문제로 이관전 보수협력사의 값이 부정확 할 시 사용.
					} else {
						if( chk > 0 ) {
							
							int gno_seq = 0;
							// 견적연계의 경우 계약 연차 체크 
							if(estno_len > 0) {
								gno_seq = Integer.parseInt(ds_list.getColumnAsString(0, "LTS"));
							}
							
							//System.out.println("*****************************************");
							//System.out.println("gno_seq =  "+ gno_seq);
							//System.out.println("*****************************************");
							
							// 견적 2차수 계약은 해당 로직 제외
							if ( estno_len > 0 && gno_seq == 1 || estno_len == 0)  {
								
								zcst126s51n.setRowIndex(i);
								Dataset dsRtnUmsChk = (Dataset)executor.query(zcst126s51n).getResultObject();	
								int ums_sum   = Integer.parseInt(dsRtnUmsChk.getColumnAsString(0, "SUM"));
								int ums_minus = Integer.parseInt(dsRtnUmsChk.getColumnAsString(0, "SUM_M"));
								int ums_new   = Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS")));
								
								int ums_limit = Math.abs(ums_sum + ums_minus) ;
								
								if(ums_sum + ums_minus < 0) { 
									if(ums_new > ums_limit){
										throw new BizException("       입력할 수 있는 개월수를 초과하였습니다.      가능개월수 : " + ums_limit + " 개월 " );
									}
								}
								
								ums_limit =  ums_new - (ums_sum + ums_minus) ;
								
								if(ums_sum + ums_minus + ums_new > 12 && chk_amt.endsWith("Y")) { 
									throw new BizException("       해당년도 유상개월수는 12개월이 넘을 수 없습니다.      가능개월수 : " + ums_limit + " 개월 " );
								}
							}	
						}					
						
						//================================================================================================================================================
						//===============================================40권한 이상일때 해당 로직을 수행해야 하나 아래와 같이 동일한 IF문으로 인해 의미없는 부분이라 판단되어 추석처리. 20200612 Han JH========
						//  수주 12개월 초과 확인 로직 , 전국 권한에 대해 해제 20170829	  >> 임시처리 삭제 예정 20170919
						/*if(except_flag.equals("")) {	
						//해당년도 유상보수개월수 12개월 초과한 경우 Exception 20141014 YAR
						zcst126s51.setRowIndex(i);
						Dataset dsRtn501 = (Dataset)executor.query(zcst126s51).getResultObject();
						
						int ums_sum2 = Integer.parseInt(dsRtn501.getColumnAsString(0, "SUM"));
						int ums_new2  =  Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS")));
		                int ums_limit2 = 12 - ums_sum2;
		                
						if(ums_sum2 + ums_new2 > 12) { 
							throw new BizException("       입력할 수 있는 개월수가 초과하였습니다.     해당년도 유상개월수는 12개월이 넘을 수 없습니다.      가능개월수 : " + ums_limit2 + " 개월 " );
						} 
					}*/
						//================================================================================================================================================
						
						// 자회사이면 기존 수주차감에 따른 보전개월수를 체크하는 로직의 경우 수행하지 않음.
					}
					
				}

/* *********************************************************************
 * ****************************************************************End
 * ********************************************************************/		
				
			/*	***** >>>> 안정화 이후 삭제 처리 예정 
				//  수주 12개월 초과 확인 로직 , 전국 권한에 대해 해제 20170829	
			//	if(except_flag.equals("")) {	
					//해당년도 유상보수개월수 12개월 초과한 경우 Exception 20141014 YAR
					zcst126s51.setRowIndex(i);
					Dataset dsRtn501 = (Dataset)executor.query(zcst126s51).getResultObject();
					
					if(Integer.parseInt(dsRtn501.getColumnAsString(0, "SUM"))+ Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS"))) > 12) { 
						throw new BizException("       입력할 수 있는 개월수가 초과하였습니다.     해당년도 유상개월수는 12개월이 넘을 수 없습니다.");
					} 
			//	}		
			*/	
				zcst116s.setRowIndex(i);
				Dataset dsRtn116 = (Dataset)executor.query(zcst116s).getResultObject();

				if(Integer.parseInt(dsRtn116.getColumnAsString(0, "CNT")) > 0) { //무상발주기간 중복현장이 있으면 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 무상발주와 계약기간이 중복됩니다. 계약기간을 다시 한번 확인해 주십시요.");
				}

				zcst146s.setRowIndex(i);
				Dataset dsRtn146 = (Dataset)executor.query(zcst146s).getResultObject();

				if(Integer.parseInt(dsRtn146.getColumnAsString(0, "CNT")) > 0) { //중지등록한 현장이 있으면 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 중지등록 진행중 입니다. 다시 한번 확인해 주십시요.");
				}

				zcst142s.setRowIndex(i);
				Dataset dsRtn142 = (Dataset)executor.query(zcst142s).getResultObject();

				if(Integer.parseInt(dsRtn142.getColumnAsString(0, "CNT")) > 0) { //실패등록한 현장이 있으면 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 실패등록 진행중 입니다. 다시 한번 확인해 주십시요.");
				}

				zcst126s3.setRowIndex(i);
				Dataset dsRtn1263 = (Dataset)executor.query(zcst126s3).getResultObject();

				if(Integer.parseInt(dsRtn1263.getColumnAsString(0, "CNT")) > 0) { //현재 저장되는 유상일자보다 미래일자  현장이 있으면 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 미래계약이 존재합니다. 저장처리를 취소합니다.");
				}
				
				zcst172s.setRowIndex(i);
				Dataset dsRtn172 = (Dataset)executor.query(zcst172s).getResultObject();

				if(Integer.parseInt(dsRtn172.getColumnAsString(0, "CNT")) > 0) { //기종교체 등록한  현장이 있으면 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 기종교체 등록되었습니다. 저장처리를 취소합니다.");
				}

				zcst121s1.setRowIndex(i);
				Dataset dsRtn121 = (Dataset)executor.query(zcst121s1).getResultObject();

				if(dsRtn121.getColumnAsString(0, "SLA_BSU").equals("Y") && !ds_list.getColumnAsString(i, "SLA").equals("Y")) { // 판매처가 보수 업체인 경우 기술용역 계약 등록이 아닌 경우 취소
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 기술용역 계약으로 등록 하십시오.");
				}
				
				
				if(ds_list.getColumnAsString(i, "KND").equals("")){
					ds_list.setColumn(i, "STDAMT", ds_list.getColumnAsDouble(i, "P10"));
					ds_list.setColumn(i, "PREAMT",0);
				} else {
					ds_list.setColumn(i, "STDAMT", ds_list.getColumnAsDouble(i, "F10"));
					ds_list.setColumn(i, "PREAMT",0);
				}

				// ***********  전계약단가 가져오기   ***********  //			
				if(dsRtn1266.getRowCount() > 0){
					ds_list.setColumn(i, "PREAMT", dsRtn1266.getColumnAsDouble(0, "CS126_AMT") + dsRtn1266.getColumnAsDouble(0, "CS126_AMBT"));
				}
				// ***********  달성율 계산    ***********  //
				// conversion & recapture : FM/POG 표준단가 대비 달성율
				// renewal                : FM 표준단가 /POG 전계약단가 대비 달성율
				double t_std = 0 ;
				double t_amt = ds_list.getColumnAsDouble(i, "AMT") + ds_list.getColumnAsDouble(i, "AMBT") ;
				double t_trt = 0 ;

				if(t_gkd.trim().equals("2") && ds_list.getColumnAsString(i, "KND").equals("")){
					
					if( ds_list.getColumnAsDouble(i, "PREAMT") == 0) {
						t_std = ds_list.getColumnAsDouble(i, "STDAMT") ;	
					} else {
						t_std = ds_list.getColumnAsDouble(i, "PREAMT") ;	
					}
				} else {
					t_std = ds_list.getColumnAsDouble(i, "STDAMT") ;	
				}
				
				t_trt = (t_amt / t_std) * 100 ;
				
				ds_list.setColumn(i, "TRT", t_trt);
				// **********************************  //
				
				zcst126s.setRowIndex(i);
				Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();

				if(Integer.parseInt(dsRtn126.getColumnAsString(0, "CNT")) > 0) { //중복된 계약기간이 있으면 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 계약기간이 중복됩니다. 계약기간을 다시 한번 확인해 주십시요.");
				} else {
					zcst126i.setRowIndex(i);
					executor.execute(zcst126i);
				}

				zcst126s4.setRowIndex(i);
				Dataset dsRtn1264 = (Dataset)executor.query(zcst126s4).getResultObject();

				if(t_gkd.trim().equals("2") && Integer.parseInt(dsRtn1264.getColumnAsString(0, "CNT")) > 0) { //기술용역 여부 이전계약과 값이 상이할 경우 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 기술용역 여부를 확인하십시오.");
				}

				zcst157s.setRowIndex(i);
				Dataset dsRtn157s = (Dataset)executor.query(zcst157s).getResultObject();

				if(Integer.parseInt(dsRtn157s.getColumnAsString(0, "CNT")) > 0) { //이관 기안 진행 중인 경우 저장 불가 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 이관 기안 진행 중입니다.");
				}
				
				//동일년월 계약 존재하는 경우 Exception 2016.01.19 YAR
				zcst126s5.setRowIndex(i);
				Dataset dsRtn1265 = (Dataset)executor.query(zcst126s5).getResultObject();

				if(Integer.parseInt(dsRtn1265.getColumnAsString(0, "CNT")) > 0) { //동일년월 계약 존재하는 경우 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 동일 년월 계약이 존재합니다.");
				}
			}

			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				ds_list.setColumn(i, "UMS", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS"))));
				ds_list.setColumn(i, "MMN", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "MMN"))));
				ds_list.setColumn(i, "JKH", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "JKH"))));
//				ds_list.setColumn(i, "RTO", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "RTO"))));

				zcst126u.setRowIndex(i);
				executor.execute(zcst126u);
			}
		}
		// 인원상주 계약 (견적) 201601 이하림
		CS1201001D_ServiceImpl sjsave;
		sjsave = new CS1201001D_ServiceImpl();
		sjsave.save(ctx,ds_lists);
	}
	
	public void delete(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U8");//X
		zcst126u.addParamObject("ds_list", ds_list);
		
		// 인원상주계약정보 update(zcst127)
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U29");//X
		zcst127u.addParamObject("ds_list", ds_list);

		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst126u.setRowIndex(i);
				zcst127u.setRowIndex(i);
				executor.execute(zcst126u);
				executor.execute(zcst127u); // 인원상주 삭제 처리 
			}
		}
	}
	
	public void save3_bak(BusinessContext ctx, Dataset ds_gbn, Dataset ds_list, Dataset ds_cost) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		String kind = ds_gbn.getColumnAsString(0, "KIND");
//		String gbn  = ds_gbn.getColumnAsString(0, "GBN");
		String gbn  = "";
		String t_gno = "";
		double s_cost = ds_cost.getColumnAsDouble(0, "COST");
		double s_rat = ds_cost.getColumnAsDouble(0, "RAT");

		// 인원상주정보 select(zcst127)
		DatasetSqlRequest zcst127s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S14");
		zcst127s.addParamObject("ds_list", ds_list);

		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U1");
		zcst111u1.addParamObject("ds_list", ds_list);
		
		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U3");
		zcst111u2.addParamObject("ds_list", ds_list);
		
		// 무상보수발주정보 select(zcst116)
		DatasetSqlRequest zcst116s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S4");
		zcst116s.addParamObject("ds_list", ds_list);
		
		// 무상보수발주정보 select(zcst116)
		DatasetSqlRequest zcst116s2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S30");
		zcst116s2.addParamObject("ds_list", ds_list);

		// 무상보수발주정보 update(zcst116)
		DatasetSqlRequest zcst116u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U2");
		zcst116u.addParamObject("ds_list", ds_list);
		
		// 무상보수발주정보 update(zcst116)
		DatasetSqlRequest zcst116u2	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U6");
		zcst116u2.addParamObject("ds_list", ds_list);

		// 유상보수계약정보 select(zcst126)
		DatasetSqlRequest zcst126s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S10");
		zcst126s.addParamObject("ds_list", ds_list);
		
		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U4");
		zcst126u1.addParamObject("ds_list", ds_list);

		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u2	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U5");
		zcst126u2.addParamObject("ds_list", ds_list);

		// 수신처 정보 insert(zfit1031)
		DatasetSqlRequest zfit1031i4 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I4");
		zfit1031i4.addParamObject("ds_list", ds_list);

		// 마스터호기정보 update(zmaster02)
		DatasetSqlRequest zmaster02u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U25");
		zmaster02u.addParamObject("ds_list", ds_list);

		// 미발주 및 만료 사유관리 update(zcst113)
		DatasetSqlRequest zcst113u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U27");
		zcst113u.addParamObject("ds_list", ds_list);

		// 매출계획정보 select(zcst131)
		DatasetSqlRequest zcst131s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S25");
		zcst131s.addParamObject("ds_list", ds_list);

		// 시행율 정보 insert(zcst128)
		DatasetSqlRequest zcst128i = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I6");
		zcst128i.addParamObject("ds_cost", ds_cost);

		zcst127s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

//		zfit1031i4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
//		zfit1031i4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst113u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst113u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst128i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		ComMethodVo comVo;
		ComMethodDao comDao;

		int z = 0;

		for(int i=0; i<ds_list.getRowCount(); i++) 
		{
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) 
			{
				gbn = ds_list.getColumnAsString(i, "GKD");

				ds_list.setColumn(i, "UMS", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS"))));
				ds_list.setColumn(i, "MMN", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "MMN"))));
				ds_list.setColumn(i, "JKH", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "JKH"))));
//				ds_list.setColumn(i, "RTO", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "RTO"))));
				
//				if(i == 0) { //최초 한번만 GNO 를 생성한다. ==> USD 가 틀린 경우가 있으므로 row 마다 생성한다.
//					zcst126s.setRowIndex(i);
//					Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();
					
//					zcst126u2.addParamObject("GNO", dsRtn126.getColumnAsString(0, "GNO"));

//					t_gno = "";
					t_gno = ds_list.getColumnAsString(i, "UPN") + 
					        "U" + 
					        ds_list.getColumnAsString(i, "UGS").substring(2,6) + 
					        "-" + 
					        ds_list.getColumnAsString(i, "CST");

					zcst126u2.addParamObject("GNO", t_gno);
//				}
				
				zcst127s.setRowIndex(i);
				Dataset dsRtn127 = (Dataset)executor.query(zcst127s).getResultObject();
				
				if(dsRtn127.getRowCount() > 0) { //인원상주 S/O 생성 데이터가 있으면 승인 오류 발생
					throw new BizException("현재 유상계약 데이터에 해당 하는 인원상주 데이터의 S/O 번호가 존재 합니다. 승인처리를 취소합니다.");
				}

				zcst116s2.setRowIndex(i);
				Dataset dsRtn1162 = (Dataset)executor.query(zcst116s2).getResultObject();

				if(Integer.parseInt(dsRtn1162.getColumnAsString(0, "CNT")) > 0) { //승인이 안된 무상발주 현장이 있으면 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 무상발주 승인처리가 완료되지 않았습니다. 무상발주 현장을 다시 한번 확인해 주십시요.");
				}

				zcst126u2.setRowIndex(i);
//				zcst116u2.setRowIndex(i);
				zcst111u1.setRowIndex(i);
				zcst113u.setRowIndex(i);

				executor.execute(zcst126u2);
//				executor.execute(zcst116u2);
				executor.execute(zcst111u1);
				executor.execute(zcst113u);

				String hgb = "";
				hgb = ds_list.getColumnAsString(i, "HNO").substring(0,1);

				if(z == 0 && (hgb.equals("L") || hgb.equals("S") || hgb.equals("W"))) {
					ds_cost.setColumn(0, "GNO", t_gno); 
					zcst128i.setRowIndex(0);
					executor.execute(zcst128i);
				}

				try
				{
					zfit1031i4.addParamObject("GNO", t_gno);
					zfit1031i4.setRowIndex(i);
					executor.execute(zfit1031i4);
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}

				if(kind.equals("D"))  //유상일반일 경우
				{
					if(gbn.equals("3")) { //계약구분이 계약변경일 경우
						zcst126u1.setRowIndex(i);
						executor.execute(zcst126u1);
					}
					
//					zmaster02u.setRowIndex(i);
//					executor.execute(zmaster02u);
					
					zcst116s.setRowIndex(i);
					Dataset dsRtn116 = (Dataset)executor.query(zcst116s).getResultObject();
	
					if(dsRtn116.getRowCount() > 0) { //무상일반보수를 진행중일 경우에는 기존의 무상일반 관련 정보를 취소 처리
						zcst116u.setRowIndex(i);
						zcst111u2.setRowIndex(i);
	
						executor.execute(zcst116u);
						executor.execute(zcst111u2);
					}
				}
				
				//기성비 연동
				comVo = new ComMethodVo();
				comDao = new ComMethodDao();
	
				if("1".equals(ds_list.getColumnAsString(i, "GKD")) || "5".equals(ds_list.getColumnAsString(i, "GKD")))
					if("C".equals(ds_list.getColumnAsString(i, "GND"))) {
						comVo.setDataId("21");//유상공사
					} else {
						comVo.setDataId("31");//자료id 신규 ,타사신규
					}
				else if("2".equals(ds_list.getColumnAsString(i, "GKD")))
					comVo.setDataId("32");//재계약
				else if("3".equals(ds_list.getColumnAsString(i, "GKD")))
					comVo.setDataId("34");//계약변경(후)
				else if("4".equals(ds_list.getColumnAsString(i, "GKD"))){//실패회수 
					if("3".equals(ds_list.getColumnAsString(i, "WIL"))){					
						comVo.setDataId("35");//타사회수 
					}	else{					
						comVo.setDataId("36");//자사회수 
					}
				}else if("2".equals(ds_list.getColumnAsString(i, "CHK_QUERY"))){//자동연장
					comVo.setDataId("37");
				}else{
					throw new BizException("Data Id가 정의 되지 않았습니다");
				}
	
				if("3".equals(ds_list.getColumnAsString(i, "GKD")))   
					 comVo.setJobGubun("I/D");
				else
					 comVo.setJobGubun("I");
	
				comVo.setMandt(ctx.getInputVariable("G_MANDT"));
				comVo.setUpn(ds_list.getColumnAsString(i, "UPN"));
				comVo.setCst(ds_list.getColumnAsString(i, "CST"));
				comVo.setPjt(ds_list.getColumnAsString(i, "PJT"));
				comVo.setHno(ds_list.getColumnAsString(i, "HNO"));
				comVo.setSeq(ds_list.getColumnAsString(i, "SEQ"));
				comVo.setSla(ds_list.getColumnAsString(i, "SLA"));
				comVo.setUserId(ctx.getInputVariable("G_USER_ID"));
				comVo.setGno(t_gno);
	
				int rtnCode = comDao.SetBXRBXL(comVo);

				if(rtnCode == 0) {
					zcst131s.setRowIndex(i);
					Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();
					
					if(Integer.parseInt(dsRtn131.getColumnAsString(0, "CNT")) > 1) { //매출 데이터가 중복 발생했으면 승인 오류 발생
						throw new BizException("현재 유상계약 데이터에 해당 하는 매출 데이터가 중복발생 되었습니다. 승인처리를 취소합니다.");
					}
				}

				z++;
			}
		}
	}
	
	public void cancel(BusinessContext ctx, Dataset ds_list, Dataset ds_zcst630) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		String t_gno = "";
		String t_gno_tmp = "";
		String chk = "";
		String c_ums = ""; // 계약삭제의 경우 CRM에서 최종 계약의 값을 배치에서 구하지 못하는 경우가 있어 이를 해결하고자 추가. 2021.10.23 HJH
		logger = ServiceManagerFactory.getLogger();

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
			
		// 영업문서  select(vbak)
		DatasetSqlRequest vbaks
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S22");//X
		vbaks.addParamObject("ds_list", ds_list);

		// 보수영업마감정보 select(zcst166)
		DatasetSqlRequest zcst166s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S33");//X
		zcst166s.addParamObject("ds_list", ds_list);

		// 유상보수계약정보  select(zcst126)
		DatasetSqlRequest zcst126s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S23");//X
		zcst126s.addParamObject("ds_list", ds_list);

		// 호기 master select(zcst111)
		DatasetSqlRequest zcst111s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S20");//X
		zcst111s.addParamObject("ds_list", ds_list);

		// 매출계획정보 select(zcst131)
		DatasetSqlRequest zcst131s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S12");//X
		zcst131s.addParamObject("ds_list", ds_list);

		// 매출 및 기성비 계획 동기화 체크
		DatasetSqlRequest plan 
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S32");//X
		plan.addParamObject("ds_list", ds_list);

		// 중지현장보고 Detail select(zcst146)
		DatasetSqlRequest zcst146s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S19");//X
		zcst146s.addParamObject("ds_list", ds_list);
		
		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U9");//X
		zcst126u.addParamObject("ds_list", ds_list);
		
		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U20");//X
		zcst126u2.addParamObject("ds_list", ds_list);
		
		// 인원상주계약정보 update(zcst127)
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U23");//X
		zcst127u.addParamObject("ds_list", ds_list);
		
		// 인원상주계약정보 update(zcst127)
		DatasetSqlRequest zcst127u2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U24");//X
		zcst127u2.addParamObject("ds_list", ds_list);

		// 호기 master update(zcst111)
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U10");//X
		zcst111u.addParamObject("ds_list", ds_list);
		
		// 계약해지정보 log insert(zcst148)
		DatasetSqlRequest zcst148i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I3");//X
		zcst148i.addParamObject("ds_list", ds_list);

		// 승인월 기준 중도해지 체크 select(zcst126)
		DatasetSqlRequest zcst126s2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S34");//X
		zcst126s2.addParamObject("ds_list", ds_list);

		// 월기성 승인여부 체크 Detail select(zcst136)
		DatasetSqlRequest zcst136s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S39");//X
		zcst136s.addParamObject("ds_list", ds_list);

		//중도해지 최초 생성 zcst630
		DatasetSqlRequest cs630i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_I1");//X
		cs630i.addParamObject("ds_zcst630", ds_zcst630);
		//중도해지 기안 올리기 전 수정가능
		DatasetSqlRequest cs630u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U1");//X
		cs630u.addParamObject("ds_zcst630", ds_zcst630);
		//중도해지 수신결재 완료 후 진행상태 업데이트
		DatasetSqlRequest cs630u3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U3");//X
		cs630u3.addParamObject("ds_zcst630", ds_zcst630);
		//중도해지 최초 생성 전 반송 및 재작성 시 삭제여부
		DatasetSqlRequest cs630u4
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U4");//X
		cs630u4.addParamObject("ds_zcst630", ds_zcst630);
		
		// 계약 삭제 시 삭제 이전 계약의 Seq select(zcst126)
		DatasetSqlRequest zcst126s3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_S9");//X
		zcst126s3.addParamObject("ds_list", ds_list);
		
		DatasetSqlRequest zcst126u3
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U35");//X
		zcst126u3.addParamObject("ds_list", ds_list);		
		
		vbaks.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		vbaks.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst166s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst166s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		plan.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		plan.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst146s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst146s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst148i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst148i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst136s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//중도해지 최초 생성 zcst630
		cs630i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//중도해지 기안 올리기 전 수정가능
		cs630u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//중도해지 수신결재 완료 후 진행상태 업데이트
		cs630u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//중도해지 최초 생성 전 반송 및 재작성 시 삭제여부
		cs630u4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		ComMethodVo comVo;
		ComMethodDao comDao;
		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				t_gno = ds_list.getColumnAsString(i, "GNO");
				c_ums = ds_list.getColumnAsString(i, "UMS"); // 개월수
				
				vbaks.setRowIndex(i);
				Dataset dsRtnvbak = (Dataset)executor.query(vbaks).getResultObject();
                //=============결재시 유상번호별로 금액 체크를 위한 로직 추가=============
				chk = "";
				if(t_gno_tmp == "" || !t_gno.equals(t_gno_tmp) ) {
					t_gno_tmp = t_gno;
					chk = "X";
				}
				//=============================================================
				
				zcst126s.setRowIndex(i);
				Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();
				
				zcst111s.setRowIndex(i);
				Dataset dsRtn111 = (Dataset)executor.query(zcst111s).getResultObject();

				zcst131s.setRowIndex(i);
				Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();

				zcst146s.setRowIndex(i);
				Dataset dsRtn146 = (Dataset)executor.query(zcst146s).getResultObject();

				zcst166s.setRowIndex(i);
				Dataset dsRtn166 = (Dataset)executor.query(zcst166s).getResultObject();
				
				zcst126s2.setRowIndex(i);
				Dataset dsRtn126_2 = (Dataset)executor.query(zcst126s2).getResultObject();

				zcst136s.setRowIndex(i);
				Dataset dsRtn136 = (Dataset)executor.query(zcst136s).getResultObject();

				if(dsRtn126_2.getColumnAsInteger(0, "CNT") > 0) { //중도해지 불가(2012.07.25 LJH)
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 해당월은 마감처리 되었습니다. 중도해지 처리를 중지 합니다.");
				}

				if(dsRtn166.getColumnAsInteger(0, "CNT") > 0 || dsRtn136.getColumnAsInteger(0, "CNT") > 0) { //보수월기성 마감 완료 또는 월기성 승인 처리 되었으면 중도해지 불가
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 월기성이 마감되었습니다. 중도해지 처리를 중지 합니다.");
				}

				if(Integer.parseInt(dsRtn146.getColumnAsString(0, "CNT")) > 0) { //중지등록한 현장이 있으면 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 중지등록 진행중 입니다. 다시 한번 확인해 주십시요.");
				}

				//if(!dsRtn126.getColumnAsString(0, "VBELN").trim().equals("") && dsRtnvbak.getColumnAsString(0, "SUJU").equals("N")) { //SO가 있고 VBAK의 수주금액과 ZCST126의 금액이 동일하면 중도해지 처리 중지
				if(!dsRtn126.getColumnAsString(0, "VBELN").trim().equals("") && dsRtnvbak.getColumnAsString(0, "SUJU").equals("N") && chk.equals("X")) { //SO가 있고 VBAK의 수주금액과 ZCST126의 금액이 동일하면 중도해지 처리 중지
					throw new BizException("수주금액을 '" +  String.format("%,d",Integer.parseInt(dsRtnvbak.getColumnAsString(0, "CS126_AMT"))) + "'으로 변경 검토 및 변경후 작업하시기 바랍니다. 중도해지 처리를 중지 합니다.");
				} else {
					if(dsRtn111.getColumnAsInteger(0, "CNT") > 0) { //원프로젝트 호기별로 실패가 한건이라도 있으면 중도해지 처리 중지
						throw new BizException(String.valueOf(i + 1) + " 번째 호기는 현재 실패중입니다. 중도해지 처리를 중지 합니다.");
					} else {
						if(dsRtn131.getRowCount() > 0 && dsRtn131.getColumnAsString(0, "GBN").equals("X")) { //통합프로젝트 호기별로 매출확정이 한건이라도 발행되었으면 중도해지 처리 중지
							throw new BizException(String.valueOf(i + 1) + " 번째 호기의 매출 확정이 완료되었습니다. 중도해지 처리를 중지 합니다.");
						} else {							
							if("A1".equals(ds_list.getColumnAsString(i, "PST"))) {								
								//logger.debug("start Draft");							
							} else {							
							logger.debug("중도해지 처리");																				
							zcst126u.setRowIndex(i);
							zcst111u.setRowIndex(i);
							zcst148i.setRowIndex(i);

							executor.execute(zcst126u);
							executor.execute(zcst111u);
							executor.execute(zcst148i);
							
							//====================연차별 유상번호의 첫번째 행이면서, 인원상주 시퀀스가 존재한다면=======================
							//====================해당 유상번호 이후의 계약까지, 인원상주 종료일을 중도해지일자로 세팅함==================
							//====================기존구문의 경우 무조건 True 값으로 리턴되어 예외조건을 수정함=======================
							//if(i == 0 && ds_list.getColumnAsString(i, "MAXSEQ") != "") {
							if(i == 0 && !(ds_list.getColumnAsString(i, "MAXSEQ").trim().equals(""))) {
								zcst127u.setRowIndex(i);
								executor.execute(zcst127u);
							}

							//기성비 연동
							comVo = new ComMethodVo();
							comDao = new ComMethodDao();

							comVo.setDataId("66"); //중도해지
							comVo.setJobGubun("I");

							comVo.setMandt(CommonUtil.NullToBlank(ctx.getInputVariable("G_MANDT")));
							comVo.setUpn(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "UPN")));
							comVo.setCst(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "CST")));
							comVo.setPjt(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "PJT")));
							comVo.setHno(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "HNO")));
							comVo.setSeq(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "SEQ")));
							//====================기존의 경우 첫번째 유상번호만 인원상주의 시퀀스를 세팅하여==========================
							//====================중도해지 처리를 하였는대 부분적으로만 중도해지 처리가 되어==========================
							//====================SRM 중도해지 소스에서 유상번호별 인원상주 시퀀스를 구하여=========================
							//====================기성계획 및 매출계획이 중도해지 처리되도록 처리하였으며============================
							//====================시퀀스가 없는 경우. 즉 인원상주가 없는 경우에는 인자값이 없으므로=====================
							//====================comDao.SetBXRBXL의 인원상주 중도해지 로직을 수행하지 않는다====================
//							if(i == 0) {
								comVo.setMaxSeq(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "MAXSEQ")));
								System.out.println(CommonUtil.NullToBlank(t_gno) + " : " + i + " : " + comVo.getMaxSeq());
//							} else {
//								comVo.setMaxSeq("");
//							}
							//======================================================================================
							comVo.setUserId(CommonUtil.NullToBlank(ctx.getInputVariable("G_USER_ID")));
							comVo.setGno(CommonUtil.NullToBlank(t_gno));
							
							int rtnCode = comDao.SetBXRBXL(comVo);

							plan.setRowIndex(i);
							Dataset dsRtnPlan = (Dataset)executor.query(plan).getResultObject();

							logger.debug("dsRtnPlan.getColumnAsInteger(0, 'CNT') ==> [" + dsRtnPlan.getColumnAsInteger(0, "CNT") + "]");

							/* 2012.06.11 LJH 대금계획 오청구 체크로직 적용 */
							if(dsRtnPlan.getColumnAsInteger(0, "CNT") > 0) { //매출 및 기성계획 사용여부 체크후 동기화 문제 발생시 중도해지 처리 중지
								logger.debug("매출 및 기성계획에 문제가 발생하였습니다. 중도해지 처리를 중지 합니다.");
								throw new BizException("매출 및 기성계획에 문제가 발생하였습니다. 중도해지 처리를 중지 합니다.");
							}

							logger.debug("zcst126u2 START!!");

							zcst126u2.setRowIndex(i);
							executor.execute(zcst126u2);
							//=======================인원상주 상주기간종료일을 중도해지 -1일 이전일자로 수정하는 로직이며======================
							//=======================기존로직의 경우 첫행의 유상번호만 수정하였으나 부분적으로 데이터의 수정이 발생되었으며===========
							//=======================기존구문의 경우 무조건 True 값으로 리턴되어 예외조건을 수정함==========================
							//=======================중도해지일 이후의 인원상주 매출계획 및 기성계획을 처리하고 나서==========================
							//=======================해당 로직을 수행하여 상주기간종료일을 중도해지 -1일 혹은 미래의 유상계약번호의 경우 상주기간 시작일로 수정함=================
//							if(i == 0 && ds_list.getColumnAsString(i, "MAXSEQ") != "") {
							if(!(ds_list.getColumnAsString(i, "MAXSEQ").trim().equals(""))) {
								zcst127u2.setRowIndex(i);
								executor.execute(zcst127u2);
							}
							//================================================================================================================
							
							// SRM 중도해지 후 CRM에서 계약삭제 내역의 경우 식별이 안되어 이를 보완하고자 로직 추가함. 2021.10.22 HJH
							if (c_ums.equals("0")) { // 개월수가 0. 즉 계약 삭제건 이면
								zcst126s3.setRowIndex(i);
								Dataset dsRtn126_3 = (Dataset)executor.query(zcst126s3).getResultObject();
//								System.out.println("test : " + dsRtn126_3.getColumnAsString(0, "C_SEQ"));
								// 이전 계약의 시퀀스를 구하여
								if(dsRtn126_3.getColumnAsInteger(0, "CNT") > 0) {
									zcst126u3.addParamObject("C_SEQ", dsRtn126_3.getColumnAsString(0, "C_SEQ"));
									
									// 변경일자, 변경일시 컬럼에 값을 현행화 해준다.
									zcst126u3.setRowIndex(i);
									executor.execute(zcst126u3);
								}
							}							
							
							}
						}
					}
				}
			}
			
		}

		if("I".equals(ds_zcst630.getColumnAsString(0, "FLAG"))) {
			executor.execute(cs630u4);
			executor.execute(cs630i);
		} else if("U".equals(ds_zcst630.getColumnAsString(0, "FLAG"))) {
			executor.execute(cs630u);
		} else {
			executor.execute(cs630u3);
		}
	}

	public void cancelJ(BusinessContext ctx, Dataset ds_list, Dataset ds_zcst630) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		String t_gno = "";
		String t_gno_tmp = "";
		String chk = "";
		logger = ServiceManagerFactory.getLogger();

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
			
		// 보수영업마감정보 select(zcst166)
		DatasetSqlRequest zcst166s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S33");//X--
		zcst166s.addParamObject("ds_list", ds_list);

		// 유상보수계약정보  select(zcst126)
		DatasetSqlRequest zcst126s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S23");//X--126에서 수주번호 CHECK
		zcst126s.addParamObject("ds_list", ds_list);

		// 호기 master select(zcst111)
		DatasetSqlRequest zcst111s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S20");//X--실패호기CHECK
		zcst111s.addParamObject("ds_list", ds_list);

		// 매출계획정보 select(zcst131)
		DatasetSqlRequest zcst131s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S12");//X--중도해지월의 매출확정여부 check
		zcst131s.addParamObject("ds_list", ds_list);

		// 매출 및 기성비 계획 동기화 체크
		DatasetSqlRequest plan 
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S32");//X--매출계획(131)없이 기성(136)정보만 있는지 확인
		plan.addParamObject("ds_list", ds_list);

		// 중지현장보고 Detail select(zcst146)
		DatasetSqlRequest zcst146s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S19");//X--일시중지 프로젝트인지 확인
		zcst146s.addParamObject("ds_list", ds_list);
		
		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U9");//X
		zcst126u.addParamObject("ds_list", ds_list);
		
		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U20");//X
		zcst126u2.addParamObject("ds_list", ds_list);
		
		// 인원상주계약정보 update(zcst127)
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U23");//X
		zcst127u.addParamObject("ds_list", ds_list);
		
		// 인원상주계약정보 update(zcst127)
		DatasetSqlRequest zcst127u2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U24");//X
		zcst127u2.addParamObject("ds_list", ds_list);

		// 호기 master update(zcst111)
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U10");//X
		zcst111u.addParamObject("ds_list", ds_list);
		
		// 계약해지정보 log insert(zcst148)
		DatasetSqlRequest zcst148i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I3");//X
		zcst148i.addParamObject("ds_list", ds_list);

		// 승인월 기준 중도해지 체크 select(zcst126)
		DatasetSqlRequest zcst126s2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S34");//X
		zcst126s2.addParamObject("ds_list", ds_list);

		// 월기성 승인여부 체크 Detail select(zcst136)
		DatasetSqlRequest zcst136s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S39");//X
		zcst136s.addParamObject("ds_list", ds_list);

		//중도해지 최초 생성 zcst630
		DatasetSqlRequest cs630i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_I1");//X
		cs630i.addParamObject("ds_zcst630", ds_zcst630);
		//중도해지 기안 올리기 전 수정가능
		DatasetSqlRequest cs630u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U1");//X
		cs630u.addParamObject("ds_zcst630", ds_zcst630);
		//중도해지 수신결재 완료 후 진행상태 업데이트
		DatasetSqlRequest cs630u3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U3");//X
		cs630u3.addParamObject("ds_zcst630", ds_zcst630);
		//중도해지 최초 생성 전 반송 및 재작성 시 삭제여부
		DatasetSqlRequest cs630u4
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U4");//X
		cs630u4.addParamObject("ds_zcst630", ds_zcst630);
		
		zcst166s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst166s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		plan.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		plan.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst146s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst146s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst148i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst148i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst136s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//중도해지 최초 생성 zcst630
		cs630i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//중도해지 기안 올리기 전 수정가능
		cs630u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//중도해지 수신결재 완료 후 진행상태 업데이트
		cs630u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//중도해지 최초 생성 전 반송 및 재작성 시 삭제여부
		cs630u4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		ComMethodVo comVo;
		ComMethodDao comDao;
		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				t_gno = ds_list.getColumnAsString(i, "GNO");
				
                //=============결재시 유상번호별로 금액 체크를 위한 로직 추가=============
				chk = "";
				if(t_gno_tmp == "" || !t_gno.equals(t_gno_tmp) ) {
					t_gno_tmp = t_gno;
					chk = "X";
				}
				//=============================================================
				
				zcst126s.setRowIndex(i);
				Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();
				
				zcst111s.setRowIndex(i);
				Dataset dsRtn111 = (Dataset)executor.query(zcst111s).getResultObject();

				zcst131s.setRowIndex(i);
				Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();

				zcst146s.setRowIndex(i);
				Dataset dsRtn146 = (Dataset)executor.query(zcst146s).getResultObject();

				zcst166s.setRowIndex(i);
				Dataset dsRtn166 = (Dataset)executor.query(zcst166s).getResultObject();
				
				zcst126s2.setRowIndex(i);
				Dataset dsRtn126_2 = (Dataset)executor.query(zcst126s2).getResultObject();

				zcst136s.setRowIndex(i);
				Dataset dsRtn136 = (Dataset)executor.query(zcst136s).getResultObject();

				if(dsRtn126_2.getColumnAsInteger(0, "CNT") > 0) { //중도해지 불가(2012.07.25 LJH)
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 해당월은 마감처리 되었습니다. 중도해지 처리를 중지 합니다.");
				}

				if(dsRtn166.getColumnAsInteger(0, "CNT") > 0 || dsRtn136.getColumnAsInteger(0, "CNT") > 0) { //보수월기성 마감 완료 또는 월기성 승인 처리 되었으면 중도해지 불가
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 월기성이 마감되었습니다. 중도해지 처리를 중지 합니다.");
				}

				if(Integer.parseInt(dsRtn146.getColumnAsString(0, "CNT")) > 0) { //중지등록한 현장이 있으면 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 중지등록 진행중 입니다. 다시 한번 확인해 주십시요.");
				}

				//if(!dsRtn126.getColumnAsString(0, "VBELN").trim().equals("") && dsRtnvbak.getColumnAsString(0, "SUJU").equals("N")) { //SO가 있고 VBAK의 수주금액과 ZCST126의 금액이 동일하면 중도해지 처리 중지

//				if(dsRtn126.getColumnAsString(0, "VBELN").trim().equals("")) { //ZCST126에서 수주번호가 없는 경우 승인취소처리로 한다.
//					throw new BizException("수주번호가 없습니다. '승인취소' 처리 바랍니다.");
//				} else {
					if(dsRtn111.getColumnAsInteger(0, "CNT") > 0) { //원프로젝트 호기별로 실패가 한건이라도 있으면 중도해지 처리 중지
						throw new BizException(String.valueOf(i + 1) + " 번째 호기는 현재 실패중입니다. 중도해지 처리를 중지 합니다.");
					} else {
						if(dsRtn131.getRowCount() > 0 && dsRtn131.getColumnAsString(0, "GBN").equals("X")) { //통합프로젝트 호기별로 매출확정이 한건이라도 발행되었으면 중도해지 처리 중지
							throw new BizException(String.valueOf(i + 1) + " 번째 호기의 매출 확정이 완료되었습니다. 중도해지 처리를 중지 합니다.");
						} else {							
							if("A1".equals(ds_list.getColumnAsString(i, "PST"))) {								
								//logger.debug("start Draft");							
							} else {							
								logger.debug("중도해지 처리");																				
								zcst126u.setRowIndex(i);
								zcst111u.setRowIndex(i);
								zcst148i.setRowIndex(i);
	
								executor.execute(zcst126u);
								executor.execute(zcst111u);
								executor.execute(zcst148i);
								
								//====================연차별 유상번호의 첫번째 행이면서, 인원상주 시퀀스가 존재한다면=======================
								//====================해당 유상번호 이후의 계약까지, 인원상주 종료일을 중도해지일자로 세팅함==================
								//====================기존구문의 경우 무조건 True 값으로 리턴되어 예외조건을 수정함=======================
								//if(i == 0 && ds_list.getColumnAsString(i, "MAXSEQ") != "") {
								if(i == 0 && !(ds_list.getColumnAsString(i, "MAXSEQ").trim().equals(""))) {
									zcst127u.setRowIndex(i);
									executor.execute(zcst127u);
								}
	
								//기성비 연동
								comVo = new ComMethodVo();
								comDao = new ComMethodDao();
	
								comVo.setDataId("66"); //중도해지
								comVo.setJobGubun("I");
	
								comVo.setMandt(CommonUtil.NullToBlank(ctx.getInputVariable("G_MANDT")));
								comVo.setUpn(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "UPN")));
								comVo.setCst(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "CST")));
								comVo.setPjt(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "PJT")));
								comVo.setHno(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "HNO")));
								comVo.setSeq(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "SEQ")));
								//====================기존의 경우 첫번째 유상번호만 인원상주의 시퀀스를 세팅하여==========================
								//====================중도해지 처리를 하였는대 부분적으로만 중도해지 처리가 되어==========================
								//====================SRM 중도해지 소스에서 유상번호별 인원상주 시퀀스를 구하여=========================
								//====================기성계획 및 매출계획이 중도해지 처리되도록 처리하였으며============================
								//====================시퀀스가 없는 경우. 즉 인원상주가 없는 경우에는 인자값이 없으므로=====================
								//====================comDao.SetBXRBXL의 인원상주 중도해지 로직을 수행하지 않는다====================
	//							if(i == 0) {
									comVo.setMaxSeq(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "MAXSEQ")));
									System.out.println(CommonUtil.NullToBlank(t_gno) + " : " + i + " : " + comVo.getMaxSeq());
	//							} else {
	//								comVo.setMaxSeq("");
	//							}
								//======================================================================================
								comVo.setUserId(CommonUtil.NullToBlank(ctx.getInputVariable("G_USER_ID")));
								comVo.setGno(CommonUtil.NullToBlank(t_gno));
								
								int rtnCode = comDao.SetBXRBXL(comVo);
	
								plan.setRowIndex(i);
								Dataset dsRtnPlan = (Dataset)executor.query(plan).getResultObject();
	
								logger.debug("dsRtnPlan.getColumnAsInteger(0, 'CNT') ==> [" + dsRtnPlan.getColumnAsInteger(0, "CNT") + "]");
	
								/* 2012.06.11 LJH 대금계획 오청구 체크로직 적용 */
								if(dsRtnPlan.getColumnAsInteger(0, "CNT") > 0) { //매출 및 기성계획 사용여부 체크후 동기화 문제 발생시 중도해지 처리 중지
									logger.debug("매출 및 기성계획에 문제가 발생하였습니다. 중도해지 처리를 중지 합니다.");
									throw new BizException("매출 및 기성계획에 문제가 발생하였습니다. 중도해지 처리를 중지 합니다.");
								}
	
								logger.debug("zcst126u2 START!!");
	
								zcst126u2.setRowIndex(i);
								executor.execute(zcst126u2);
								//=======================인원상주 상주기간종료일을 중도해지 -1일 이전일자로 수정하는 로직이며======================
								//=======================기존로직의 경우 첫행의 유상번호만 수정하였으나 부분적으로 데이터의 수정이 발생되었으며===========
								//=======================기존구문의 경우 무조건 True 값으로 리턴되어 예외조건을 수정함==========================
								//=======================중도해지일 이후의 인원상주 매출계획 및 기성계획을 처리하고 나서==========================
								//=======================해당 로직을 수행하여 상주기간종료일을 중도해지 -1일 혹은 미래의 유상계약번호의 경우 상주기간 시작일로 수정함=================
	//							if(i == 0 && ds_list.getColumnAsString(i, "MAXSEQ") != "") {
								if(!(ds_list.getColumnAsString(i, "MAXSEQ").trim().equals(""))) {
									zcst127u2.setRowIndex(i);
									executor.execute(zcst127u2);
								}
							}
						}
//					}
				}
			}
			
		}

		if("I".equals(ds_zcst630.getColumnAsString(0, "FLAG"))) {
			executor.execute(cs630u4);
			executor.execute(cs630i);
		} else if("U".equals(ds_zcst630.getColumnAsString(0, "FLAG"))) {
			executor.execute(cs630u);
		} else {
			executor.execute(cs630u3);
		}
	}

	public void seungin_cancel(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 영업문서  select(vbak)//계약번호와 대응되는 수주번호
		DatasetSqlRequest vbaks
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S31");//X
		vbaks.addParamObject("ds_list", ds_list);

		// 매출계획정보 select(zcst131)//
		DatasetSqlRequest zcst131s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S11");//X
		zcst131s.addParamObject("ds_list", ds_list);

		// 보수영업마감정보 select(zcst166)
		DatasetSqlRequest zcst166s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S16");//X
		zcst166s.addParamObject("ds_list", ds_list);
		
		// 호기 master update(zcst111)
		DatasetSqlRequest zcst111u1
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U11");//X
		zcst111u1.addParamObject("ds_list", ds_list);

		DatasetSqlRequest zcst111u2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U16");//X
		zcst111u2.addParamObject("ds_list", ds_list);

		DatasetSqlRequest zcst111u3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U17");//X
		zcst111u3.addParamObject("ds_list", ds_list);
		
		DatasetSqlRequest zcst111u4
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U18");//X
		zcst111u4.addParamObject("ds_list", ds_list);
				
		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U12");//X
		zcst126u.addParamObject("ds_list", ds_list);

		// 인원상주계약정보 update(zcst127)
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U13");//X
		zcst127u.addParamObject("ds_list", ds_list);
		
		// 매출계획정보 update(zcst131)
		DatasetSqlRequest zcst131u
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_D1");
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U14");//X
		zcst131u.addParamObject("ds_list", ds_list);
		
		//임시 사용 2008.10.28 LJH
		// 매출계획정보 update(zcst131)
		DatasetSqlRequest zcst131u2
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_D1");
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U19");//X
		zcst131u2.addParamObject("ds_list", ds_list);
		//임시 사용 2008.10.28 LJH

		// 기성비지급계획정보 update(zcst136)
		DatasetSqlRequest zcst136u
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_D2");
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U15");//X
		zcst136u.addParamObject("ds_list", ds_list);

		// 매출계획정보 update(zcst131)
		DatasetSqlRequest zcst131u3
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_D1");
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U21");//X
		zcst131u3.addParamObject("ds_list", ds_list);
		
		// 기성비지급계획정보 update(zcst136)
		DatasetSqlRequest zcst136u3
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_D2");
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U22");//X
		zcst136u3.addParamObject("ds_list", ds_list);
		
		// 계약해지정보 log insert(zcst148)
		DatasetSqlRequest zcst148i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I5");//X
		zcst148i.addParamObject("ds_list", ds_list);
		
		// 마스터호기정보 update(zmaster02)
		DatasetSqlRequest zmaster02u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U26");//X
		zmaster02u.addParamObject("ds_list", ds_list);

		// 승인월 기준 승인취소 체크 select(zcst126)
		DatasetSqlRequest zcst126s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S35");//X
		zcst126s.addParamObject("ds_list", ds_list);

		// 월 대수마감 여부 체크 select(zeist104)
		DatasetSqlRequest zeist104s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S37");//X
		zeist104s.addParamObject("ds_list", ds_list);

		// 월기성 승인여부 체크 Detail select(zcst136)
		DatasetSqlRequest zcst136s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S39");//X
		zcst136s.addParamObject("ds_list", ds_list);

		vbaks.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		vbaks.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst166s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst166s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst111u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst111u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst111u4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst131u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		//임시 사용 2008.10.28 LJH
		zcst131u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//임시 사용 2008.10.28 LJH

		zcst136u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst131u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst136u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst148i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst148i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zeist104s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zeist104s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst136s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst166s.setRowIndex(i);
				Dataset dsRtn166 = (Dataset)executor.query(zcst166s).getResultObject();

				zcst126s.setRowIndex(i);
				Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();

				zeist104s.setRowIndex(i);
				Dataset dsRtn104 = (Dataset)executor.query(zeist104s).getResultObject();

				zcst136s.setRowIndex(i);
				Dataset dsRtn136 = (Dataset)executor.query(zcst136s).getResultObject();

//				if(dsRtn126.getColumnAsInteger(0, "CNT") > 0) { //승인취소 불가(2012.07.25 LJH)
//					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 해당월은 마감처리 되었습니다. 승인취소 처리를 중지 합니다.");
//				}
/* 2014.02.03 */

				if(dsRtn104.getColumnAsInteger(0, "CNT") > 0) { //보수 월 대수마감 완료 시, 유상개시일 및 승인일-마감일자 체크
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 월 대수마감이 완료되었습니다. 승인취소 처리를 중지 합니다.");
				}

				if(dsRtn166.getColumnAsInteger(0, "CNT") > 0 || dsRtn136.getColumnAsInteger(0, "CNT") > 0) { //보수월기성 마감 완료 처리 되었으면 승인취소 불가(다음달에 매출 및 기성비 - 처리)
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 월기성이 마감되었습니다. 승인취소 처리를 중지 합니다.");
				} else {
					vbaks.setRowIndex(i);
					Dataset dsRtnvbak = (Dataset)executor.query(vbaks).getResultObject();

					zcst131s.setRowIndex(i);
					Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();

					if(!CommonUtil.NullToBlank(dsRtnvbak.getColumnAsString(0, "GBN")).trim().equals("N")) { //SAP에서 수주금액 0원으로 변경 미처리시 승인취소 처리 중지
						throw new BizException("SAP에서 수주금액 변경후 작업하시기 바랍니다. 승인취소 처리를 중지 합니다.");
					} else {
						//임시 사용 2008.10.28 LJH
						//dsRtn131.setColumn(0, "CS131_TIS", "");
						//임시 사용 2008.10.28 LJH
						if(!CommonUtil.NullToBlank(dsRtn131.getColumnAsString(0, "CS131_TIS")).trim().equals("")) { //통합프로젝트 호기별로 매출확정이 한건이라도 발행되었으면 승인취소 처리 중지
							throw new BizException(String.valueOf(i + 1) + " 번째 호기의 매출 확정이 완료되었습니다. 승인취소 처리를 중지 합니다.");
						} else {
							zcst126u.setRowIndex(i);
							zcst127u.setRowIndex(i);
							zcst131u.setRowIndex(i);
							zcst136u.setRowIndex(i);
							zcst148i.setRowIndex(i);
							//zcst136u.setRowIndex(i);
							//임시 사용 2008.10.28 LJH
							zcst131u2.setRowIndex(i);
							//임시 사용 2008.10.28 LJH
							zcst131u3.setRowIndex(i);
							zcst136u3.setRowIndex(i);
	//						zmaster02u.setRowIndex(i);
							if("C".equals(ds_list.getColumnAsString(i, "GND").trim())) { //유상공사
								if(ds_list.getColumnAsInteger(i, "CS2") == 1) { //계약차수가 1일때
									zcst111u1.setRowIndex(i);
								} else if(ds_list.getColumnAsInteger(i, "CS2") > 1) { //계약차수가 1보다 클때
									zcst111u2.setRowIndex(i);
								}
							} else if("D".equals(ds_list.getColumnAsString(i, "GND").trim())) { //유상일반
								if(ds_list.getColumnAsInteger(i, "IS2") == 1) { //계약차수가 1일때
									zcst111u3.setRowIndex(i);
								} else if(ds_list.getColumnAsInteger(i, "IS2") > 1) { //계약차수가 1보다 클때
									zcst111u4.setRowIndex(i);
								}
							}
	
							executor.execute(zcst126u);
							executor.execute(zcst127u);
							executor.execute(zcst131u);
							executor.execute(zcst136u);
							executor.execute(zcst148i);
							//임시 사용 2008.10.28 LJH
							executor.execute(zcst131u2);
							//임시 사용 2008.10.28 LJH
							executor.execute(zcst131u3);
							executor.execute(zcst136u3);
	//						executor.execute(zmaster02u);
							if("C".equals(ds_list.getColumnAsString(i, "GND").trim())) { //유상공사
								if(ds_list.getColumnAsInteger(i, "CS2") == 1) { //계약차수가 1일때
									executor.execute(zcst111u1);
								} else if(ds_list.getColumnAsInteger(i, "CS2") > 1) { //계약차수가 1보다 클때
									executor.execute(zcst111u2);
								}
							} else if("D".equals(ds_list.getColumnAsString(i, "GND").trim())) { //유상일반
								if(ds_list.getColumnAsInteger(i, "IS2") == 1) { //계약차수가 1일때
									executor.execute(zcst111u3);
								} else if(ds_list.getColumnAsInteger(i, "IS2") > 1) { //계약차수가 1보다 클때
									executor.execute(zcst111u4);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void save3(BusinessContext ctx, Dataset ds_gbn, Dataset ds_list, Dataset ds_lists) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		String kind = ds_gbn.getColumnAsString(0, "KIND");
//		String gbn  = ds_gbn.getColumnAsString(0, "GBN");
		String gbn  = "";
		String t_gno = "";

		// 인원상주정보 select(zcst127)
		DatasetSqlRequest zcst127s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S14");//X
		zcst127s.addParamObject("ds_list", ds_list);

		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U1");//X
		zcst111u1.addParamObject("ds_list", ds_list);
		
		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U3");//X
		zcst111u2.addParamObject("ds_list", ds_list);
		
		// 무상보수발주정보 select(zcst116)
		DatasetSqlRequest zcst116s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S4");//X
		zcst116s.addParamObject("ds_list", ds_list);
		
		// 무상보수발주정보 select(zcst116)
		DatasetSqlRequest zcst116s2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S30");//X
		zcst116s2.addParamObject("ds_list", ds_list);

		// 무상보수발주정보 update(zcst116)
		DatasetSqlRequest zcst116u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U2");//X
		zcst116u.addParamObject("ds_list", ds_list);
		
		// 무상보수발주정보 update(zcst116)
		DatasetSqlRequest zcst116u2	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U6");//X
		zcst116u2.addParamObject("ds_list", ds_list);

		// 유상보수계약정보 select(zcst126)
		DatasetSqlRequest zcst126s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S10");//X
		zcst126s.addParamObject("ds_list", ds_list);
		
		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U4");//X
		zcst126u1.addParamObject("ds_list", ds_list);

		// 유상보수계약정보 update(zcst126)
		DatasetSqlRequest zcst126u2	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U5");//X
		zcst126u2.addParamObject("ds_list", ds_list);

		// 수신처 정보 insert(zfit1031)
		DatasetSqlRequest zfit1031i4 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I4");//X
		zfit1031i4.addParamObject("ds_list", ds_list);

		// 마스터호기정보 update(zmaster02)
		DatasetSqlRequest zmaster02u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U25");//X
		zmaster02u.addParamObject("ds_list", ds_list);

		// 미발주 및 만료 사유관리 update(zcst113) : 계약단가, 기간수주예상금액(직영금액을 기준으로 함(일단은))
		DatasetSqlRequest zcst113u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U27");//X
		zcst113u.addParamObject("ds_list", ds_list);

		// 매출계획정보 select(zcst131)
		DatasetSqlRequest zcst131s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S25");//X
		zcst131s.addParamObject("ds_list", ds_list);

		// 정기보수 시행율 update(zcst128)
		DatasetSqlRequest zcst128u1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U30");//X
		zcst128u1.addParamObject("ds_list", ds_list);
		zcst128u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		
		zcst127s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

//		zfit1031i4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
//		zfit1031i4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst113u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst113u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		

		ComMethodVo comVo;
		ComMethodDao comDao;

		int z = 0;

		for(int i=0; i<ds_list.getRowCount(); i++) 
		{
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) 
			{
				gbn = ds_list.getColumnAsString(i, "GKD");

				ds_list.setColumn(i, "UMS", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS"))));
				ds_list.setColumn(i, "MMN", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "MMN"))));
				ds_list.setColumn(i, "JKH", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "JKH"))));
//				ds_list.setColumn(i, "RTO", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "RTO"))));
				
//				if(i == 0) { //최초 한번만 GNO 를 생성한다. ==> USD 가 틀린 경우가 있으므로 row 마다 생성한다.
//					zcst126s.setRowIndex(i);
//					Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();
					
//					zcst126u2.addParamObject("GNO", dsRtn126.getColumnAsString(0, "GNO"));

//					t_gno = "";
					t_gno = ds_list.getColumnAsString(i, "UPN") + 
					        "U" + 
					        ds_list.getColumnAsString(i, "UGS").substring(2,6) + 
					        "-" + 
					        ds_list.getColumnAsString(i, "CST");
					
					//zcst126u2.addParamObject("GNO", t_gno);
					ds_list.setColumn(i, "GNO", t_gno);
//				}
				
				zcst127s.setRowIndex(i);
				Dataset dsRtn127 = (Dataset)executor.query(zcst127s).getResultObject();
				
				if(dsRtn127.getRowCount() > 0) { //인원상주 S/O 생성 데이터가 있으면 승인 오류 발생
					throw new BizException("현재 유상계약 데이터에 해당 하는 인원상주 데이터의 S/O 번호가 존재 합니다. 승인처리를 취소합니다.");
				}

				zcst116s2.setRowIndex(i);
				Dataset dsRtn1162 = (Dataset)executor.query(zcst116s2).getResultObject();

				if(Integer.parseInt(dsRtn1162.getColumnAsString(0, "CNT")) > 0) { //승인이 안된 무상발주 현장이 있으면 Exception
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는 무상발주 승인처리가 완료되지 않았습니다. 무상발주 현장을 다시 한번 확인해 주십시요.");
				}

				zcst126u2.setRowIndex(i);
//				zcst116u2.setRowIndex(i);
				zcst111u1.setRowIndex(i);
				zcst113u.setRowIndex(i);
				zcst128u1.setRowIndex(i);

				executor.execute(zcst126u2);
//				executor.execute(zcst116u2);
				executor.execute(zcst111u1);
				executor.execute(zcst113u);
				executor.execute(zcst128u1);

				try
				{
					zfit1031i4.addParamObject("GNO", t_gno);
					zfit1031i4.setRowIndex(i);
					executor.execute(zfit1031i4);
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}

				if(kind.equals("D"))  //유상일반일 경우
				{
					if(gbn.equals("3")) { //계약구분이 계약변경일 경우
						zcst126u1.setRowIndex(i);
						executor.execute(zcst126u1);
					}
					
//					zmaster02u.setRowIndex(i);
//					executor.execute(zmaster02u);
					
					zcst116s.setRowIndex(i);
					Dataset dsRtn116 = (Dataset)executor.query(zcst116s).getResultObject();
	
					if(dsRtn116.getRowCount() > 0) { //무상일반보수를 진행중일 경우에는 기존의 무상일반 관련 정보를 취소 처리
						zcst116u.setRowIndex(i);
						zcst111u2.setRowIndex(i);
	
						executor.execute(zcst116u);
						executor.execute(zcst111u2);
					}
				}
				
				//기성비 연동
				comVo = new ComMethodVo();
				comDao = new ComMethodDao();
	
				if("1".equals(ds_list.getColumnAsString(i, "GKD")) || "5".equals(ds_list.getColumnAsString(i, "GKD")))
					if("C".equals(ds_list.getColumnAsString(i, "GND"))) {
						comVo.setDataId("21");//유상공사
					} else {
						comVo.setDataId("31");//자료id 신규 ,타사신규
					}
				else if("2".equals(ds_list.getColumnAsString(i, "GKD")))
					comVo.setDataId("32");//재계약
				else if("3".equals(ds_list.getColumnAsString(i, "GKD")))
					comVo.setDataId("34");//계약변경(후)
				else if("4".equals(ds_list.getColumnAsString(i, "GKD"))){//실패회수 
					if("3".equals(ds_list.getColumnAsString(i, "WIL"))){					
						comVo.setDataId("35");//타사회수 
					}	else{					
						comVo.setDataId("36");//자사회수 
					}
				}else if("2".equals(ds_list.getColumnAsString(i, "CHK_QUERY"))){//자동연장
					comVo.setDataId("37");
				}else{
					throw new BizException("Data Id가 정의 되지 않았습니다");
				}
	
				if("3".equals(ds_list.getColumnAsString(i, "GKD")))   
					 comVo.setJobGubun("I/D");
				else
					 comVo.setJobGubun("I");
	
				comVo.setMandt(ctx.getInputVariable("G_MANDT"));
				comVo.setUpn(ds_list.getColumnAsString(i, "UPN"));
				comVo.setCst(ds_list.getColumnAsString(i, "CST"));
				comVo.setPjt(ds_list.getColumnAsString(i, "PJT"));
				comVo.setHno(ds_list.getColumnAsString(i, "HNO"));
				comVo.setSeq(ds_list.getColumnAsString(i, "SEQ"));
				comVo.setSla(ds_list.getColumnAsString(i, "SLA"));
				comVo.setUserId(ctx.getInputVariable("G_USER_ID"));
				comVo.setGno(t_gno);

				int rtnCode = comDao.SetBXRBXL(comVo);

				if(rtnCode == 0) {
					zcst131s.setRowIndex(i);
					Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();
					
					if(Integer.parseInt(dsRtn131.getColumnAsString(0, "CNT")) > 1) { //매출 데이터가 중복 발생했으면 승인 오류 발생
						throw new BizException("현재 유상계약 데이터에 해당 하는 매출 데이터가 중복발생 되었습니다. 승인처리를 취소합니다.");
					}
				}

				z++;
			}
		}
		// 인원상주(견적) 승인 처리 201601
		CS1201001D_ServiceImpl sjsave2;
		sjsave2 = new CS1201001D_ServiceImpl();
		sjsave2.save2(ctx,ds_lists);
	}
	
	public void saveCost(BusinessContext ctx, Dataset ds_list, Dataset ds_cost) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		String t_gno = "";

		// 시행율 정보 insert(zcst128)
		DatasetSqlRequest zcst128i = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I6");//X
		zcst128i.addParamObject("ds_cost", ds_cost);
		zcst128i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		for(int i=0; i<ds_list.getRowCount(); i++) 
		{
//			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) 
//			{
				t_gno = ds_cost.getColumnAsString(i, "GNO");
				String hgb = "";
				hgb = ds_list.getColumnAsString(i, "HNO").substring(0,1);

				if((hgb.equals("L") || hgb.equals("S") || hgb.equals("W"))) {
					ds_cost.setColumn(0, "GNO", t_gno); 
					zcst128i.setRowIndex(0);
					executor.execute(zcst128i);
				}
//			}
		}
	}
	
}

