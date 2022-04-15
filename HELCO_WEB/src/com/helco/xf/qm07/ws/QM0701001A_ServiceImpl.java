package com.helco.xf.qm07.ws;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.resource.TransactionManager;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.BizException;
import com.tobesoft.platform.data.Dataset;

public class QM0701001A_ServiceImpl extends AbstractBusinessService implements
		QM0701001A_Service {

    /******************************원가절감******************************/
	public void save(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 원가절감항목등록 (ZQMT071) 내역의 항목번호 조회
		DatasetSqlRequest zqmt071s
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_S3");	// ITEM seq생성

		zqmt071s.addParamObject("ds_cond", ds_list);
		zqmt071s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 원가절감항목등록 (ZQMT071) 내역 등록
		DatasetSqlRequest zqmt071i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_I1");

		zqmt071i.addParamObject("ds_list",   ds_list);
		zqmt071i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt071i.addParamObject("S_CONFDT",  ctx.getInputVariable("pConfDt"));		// 확정일자

		// 원가절감항목등록 (ZQMT071) 내역 수정
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_U1");

		zqmt071u.addParamObject("ds_list",   ds_list);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 원가절감항목등록 (ZQMT071) 내역 삭제
		DatasetSqlRequest zqmt071d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_D1");

		zqmt071d.addParamObject("ds_list", ds_list);
		zqmt071d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 원가절감항목계획금액등록 (ZQMT072)
		DatasetSqlRequest zqmt072i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_I2");

		zqmt072i.addParamObject("ds_list",   ds_list);
		zqmt072i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt072i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 원가절감항목계획금액삭제 (ZQMT072)
		DatasetSqlRequest zqmt072d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_D2");

		zqmt072d.addParamObject("ds_list", ds_list);
		zqmt072d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 도면/자재 번호 등록 (ZQMT073)
		DatasetSqlRequest zqmt073i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_I3");

		zqmt073i.addParamObject("ds_list",   ds_list);
		zqmt073i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt073i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 도면/자재 번호 삭제 (ZQMT073)
		DatasetSqlRequest zqmt073d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0701001A_D3");

		zqmt073d.addParamObject("ds_list", ds_list);
		zqmt073d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		for(int i = 0; i < ds_list.getRowCount(); i++) {
			if("I".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{

				// 이뤙항목의 입력 처리 ZQMT071
				if(ds_list.getColumnAsString(i, "ITEM") != null && !"".equals(ds_list.getColumnAsString(i, "ITEM")))	{
					zqmt071i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));		// 항목번호
					zqmt072i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));		// 항목번호
				}else	{
					zqmt071s.setRowIndex(i);
					Dataset dsRtn071 = (Dataset)executor.query(zqmt071s).getResultObject();
					
					if(dsRtn071.getRowCount() > 0 ) {
						zqmt071i.addParamObject("S_ITEM",  dsRtn071.getColumnAsString(0, "ITEM"));		// 항목번호
						zqmt072i.addParamObject("S_ITEM",  dsRtn071.getColumnAsString(0, "ITEM"));		// 항목번호
					} else {
						throw new BizException(String.valueOf(i + 1) + " 번째 항목 생성시 오루가 발생하였습니다. 처리를 중지 합니다.");
					}
				}

				// 원가절감항목등록 (ZQMT071)
				zqmt071i.setRowIndex(i);
				executor.execute(zqmt071i);

				// 원가절감계획금액 등록
				try {
					zqmt072Insert(executor, zqmt072i, ds_list, i);
				}catch (Exception e)	{
					throw e;
				}

				// 도면/자재 정보 처리
				if (ds_list.getColumnAsString(i, "ITEM") != null && !"".equals(ds_list.getColumnAsString(i, "ITEM")))	{
					// 도면/자재 번호등록 (ZQMT073)
					zqmt073i.setRowIndex(i);
					executor.execute(zqmt073i);
				}
			}else if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{
				// 원가절감항목수정 (ZQMT071)
				zqmt071u.setRowIndex(i);
				executor.execute(zqmt071u);
				
				// 원가절감계획금액 삭제
				zqmt072d.setRowIndex(i);
				executor.execute(zqmt072d);

				// 원가절감계획금액 등록
				try {
					zqmt072i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));	// 항목번호
					zqmt072Insert(executor, zqmt072i, ds_list, i);
				}catch (Exception e)	{
					throw e;
				}
			}else if("D".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{
				// 원가절감계획금액 삭제
				zqmt072d.setRowIndex(i);
				executor.execute(zqmt072d);

				// 원가절감항목삭제 (ZQMT071)
				zqmt071d.setRowIndex(i);
				executor.execute(zqmt071d);
				
				// 도면/자재 정보 삭제
				zqmt073d.setRowIndex(i);
				executor.execute(zqmt073d);
			}
		}
	}

	// 원가절감계획금액 등록 (ZQMT072)
	public void zqmt072Insert(DatasetSqlExecutor executor, DatasetSqlRequest zqmt072i, Dataset ds_list, int index) throws Exception	{
		String sMonth  = "";
		String sPlanMM = "";
		for (int j = 1; j <= 12; j++)	{
			sPlanMM = String.format("%02d", j);
			sMonth = "M" + sPlanMM;
			if (ds_list.getColumnAsString(index, sMonth) != null)	{
				zqmt072i.addParamObject("S_PLANYM",  ds_list.getColumnAsString(index, "GJAHR") + sPlanMM); // 계획년월
				zqmt072i.addParamObject("S_PLANAMT", ds_list.getColumnAsString(index, sMonth)); // 계획금액
				
				zqmt072i.setRowIndex(index);
				executor.execute(zqmt072i);
			}
		}		
	}

	// 자재/도면 실적 집계 처리
	public void matnrProc(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 자재/도면번호 기 실적집계 삭제 (ZQMT075)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 산출된 기 항목별 실적집계 삭제 (ZQMT076) 시스템에 의하여 기 자동 산출건 삭제
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재(gubun=1) 도면(gubun=2) 번호로 수량 추출
		DatasetSqlRequest matnrs
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_S5");

		matnrs.addParamObject("ds_cond", ds_cond);
		matnrs.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재/도면번호 실적집계 (ZQMT075)
		DatasetSqlRequest zqmt075u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U1");

		zqmt075u.addParamObject("ds_list",   ds_cond);
		zqmt075u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt075u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 산출된 기 항목별 실적집계 삭제 (ZQMT076) 수기 입력건중 시스템에 의하여 산출된건 삭제
		DatasetSqlRequest zqmt076dd
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D3");

		zqmt076dd.addParamObject("ds_list", ds_cond);
		zqmt076dd.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 항목별 실적집계 (ZQMT076)
		DatasetSqlRequest zqmt076i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_I1");

		zqmt076i.addParamObject("ds_list",   ds_cond);
		zqmt076i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt076i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt076i.addParamObject("SGBN",      "S");	// M : 수기,    S : 시스템
		zqmt076i.addParamObject("BIGO",      "");
		zqmt076i.addParamObject("KGBN",      "T");	// T : 총액기준, U : 단가기준

		// 최초 실적월 수정 (ZQMT071)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702002A_U1");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 집계처리 이력 (ZQMT074)
		DatasetSqlRequest zqmt074u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U2");

		zqmt074u.addParamObject("ds_list", ds_cond);
		zqmt074u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt074u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 실적집계대상 항목의 자재/도면번호 대상 조회
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{
			// 기 처리된 실적집계 정보 삭제 처리
			// 자재/도면번호 기 실적집계 삭제 (ZQMT075)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);

			// 산출된 기 항목별 실적집계 삭제 (ZQMT076)
			zqmt076d.addParamObject("SGBN", "S");	// 시스템 산출
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);

			// 자재/도면  집계 대상 조회
			matnrs.setRowIndex(i);
			Dataset dsRtnMatnr = (Dataset)executor.query(matnrs).getResultObject();

			// 산출된 Item 정보
			HashMap hashMap = new HashMap();

			for (int iMatnr = 0; iMatnr < dsRtnMatnr.getRowCount(); iMatnr++)	{

				String sStym = dsRtnMatnr.getColumnAsString(iMatnr, "STYM");
				int iQnty    = dsRtnMatnr.getColumnAsDouble(iMatnr, "QNTY").intValue();

				// 0의 경우 실적 제외 (단 최초 실적월이 존재하지 않아야 함)
				if (iQnty == 0 && (sStym == null || "".equals(sStym)))	{
					continue;
				}

				// 실적 처리
				if (hashMap.get(dsRtnMatnr.getColumnAsString(iMatnr, "ITEM")) == null)	{

					String sRsltym = "";
					if (sStym == null || "".equals(sStym))	{
						sRsltym = ds_cond.getColumnAsString(i, "RSLTYM");
					}

					hashMap.put(dsRtnMatnr.getColumnAsString(iMatnr, "ITEM"), sRsltym);		// 최초 실적년월이 존재하는 경우 ""
				}

				// 둥록된 자재/도면 번호의 실적 처리 
				zqmt075u.addParamObject("ITEM",  dsRtnMatnr.getColumnAsString(iMatnr, "ITEM"));	// 항목
				zqmt075u.addParamObject("GUBUN", dsRtnMatnr.getColumnAsString(iMatnr, "GUBUN"));	// 자재/도면번호 구분
				zqmt075u.addParamObject("MATNR", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR"));	// 자재/도면번호
				zqmt075u.addParamObject("SVAMT", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR_SVAMT"));	// 절감액
				zqmt075u.addParamObject("WAERK", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR_WAERK"));	// 화폐딴위
				zqmt075u.addParamObject("QNTY",  iQnty + "");	// 수량
	
				// 자재/도면 집계 정보 등록(ZQMT075)
				zqmt075u.setRowIndex(i);
				executor.execute(zqmt075u);
			}

			// 항목별 집계 처리
			Collection collection = hashMap.keySet();
			Iterator   iter       = collection.iterator();

			while(iter.hasNext())	{
				String sKey   = ((String) iter.next());
				String sValue = ((String) hashMap.get(sKey));	// ""이 아니면 실적년월 등록 필요

				// 산출된 기 항목별 실적집계전 해당 항목 삭제 (ZQMT076)
				zqmt076dd.addParamObject("ITEM", sKey);

				zqmt076dd.setRowIndex(i);
				executor.execute(zqmt076dd);

				// 항목별 집계 정보 등록 (ZQMT076) :  자재/도면번호별로 집계된 정보 집계
				zqmt076i.addParamObject("ITEM", sKey);	// 항목

				zqmt076i.setRowIndex(i);
				executor.execute(zqmt076i);

				// 최최 실적일자가 없는 경우에만 반영
				if (sValue != null && !"".equals(sValue))	{
					// 최초 실적등록일 정보 Update
					zqmt071u.addParamObject("ITEM", sKey);	// 항목
					zqmt071u.addParamObject("STYM", ds_cond.getColumnAsString(i, "RSLTYM"));	// 최초실적일자

					zqmt071u.setRowIndex(i);
					executor.execute(zqmt071u);
				}
			}

			// 집계 처리 이력 등록(ZQMT074)
			zqmt074u.setRowIndex(i);
			executor.execute(zqmt074u);
		}
	}

	// 자재/도면 실적 집계 처리 (세부조건 추가)
	// 2013.07.17
	public void matnrProcCondAdd(BusinessContext ctx, Dataset ds_cond) throws Exception {
		System.out.println("b");
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 자재/도면번호 기 실적집계 삭제 (ZQMT075)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 산출된 기 항목별 실적집계 삭제 (ZQMT076) 시스템에 의하여 기 자동 산출건 삭제
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재(gubun=1) 도면(gubun=2) 번호로 수량 추출 (상세조건 포함)
		DatasetSqlRequest matnrs
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_S6");

		matnrs.addParamObject("ds_cond", ds_cond);
		matnrs.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재(gubun=1) 도면(gubun=2) 상세조건 조회
		DatasetSqlRequest matnrsDetail
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_S7");

		matnrsDetail.addParamObject("ds_cond", ds_cond);
		matnrsDetail.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재/도면번호 실적집계 (ZQMT075)
		DatasetSqlRequest zqmt075u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U1");

		zqmt075u.addParamObject("ds_list",   ds_cond);
		zqmt075u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt075u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 산출된 기 항목별 실적집계 삭제 (ZQMT076) 입력건중 시스템에 의하여 산출된건 삭제
		DatasetSqlRequest zqmt076dd
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D3");

		zqmt076dd.addParamObject("ds_list", ds_cond);
		zqmt076dd.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 항목별 실적집계 (ZQMT076)
		DatasetSqlRequest zqmt076i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_I1");

		zqmt076i.addParamObject("ds_list",   ds_cond);
		zqmt076i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt076i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt076i.addParamObject("SGBN",      "S");	// M : 수기,    S : 시스템
		zqmt076i.addParamObject("BIGO",      "");
		zqmt076i.addParamObject("KGBN",      "T");	// T : 총액기준, U : 단가기준

		// 최초 실적월 수정 (ZQMT071)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702002A_U1");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 집계처리 이력 (ZQMT074)
		DatasetSqlRequest zqmt074u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U2");

		zqmt074u.addParamObject("ds_list", ds_cond);
		zqmt074u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt074u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 실적집계대상 항목의 자재/도면번호 대상 조회
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{

			// 기 처리된 실적집계 정보 삭제 처리
			// 자재/도면번호 기 실적집계 삭제 (ZQMT075)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);
			System.out.println(i);
			// 산출된 기 항목별 실적집계 삭제 (ZQMT076)
			zqmt076d.addParamObject("SGBN", "S");	// 시스템 산출
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);

			// 자재/도면  집계 대상 조회
			matnrs.setRowIndex(i);
			Dataset dsRtnMatnr = (Dataset)executor.query(matnrs).getResultObject();

			// 자재/도면 상세조회 대상 조회 2013.07.17
			matnrsDetail.setRowIndex(i);
			Dataset dsRtnMatnrDetail = (Dataset)executor.query(matnrsDetail).getResultObject();

			// 상세조건에 따른 조건 확인 및 집계 처리
			Dataset dsTempSum = new Dataset();

			// 컬럼 setting
			dsTempSum.addColumn("ITEM",        (short)1, 256);
			dsTempSum.addColumn("STYM",        (short)1, 256);
			dsTempSum.addColumn("GUBUN",       (short)1, 256);
			dsTempSum.addColumn("MATNR_SVAMT", (short)1, 256);
			dsTempSum.addColumn("MATNR_WAERK", (short)1, 256);
			dsTempSum.addColumn("MATNR",       (short)1, 256);
			dsTempSum.addColumn("QNTY",        (short)1, 256);

			// 자재(도면) 집계 
			int iPos = 0;

			// 세부조건 확인
			for(int iDetail = 0; iDetail < dsRtnMatnrDetail.getRowCount(); iDetail++)		{

				boolean bSumCheck = false;

				String sItem_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ITEM");
				String sMatnr_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR");
				String sSpec12_d = dsRtnMatnrDetail.getColumnAsString(iDetail,  "SPEC12");
				int iSpec3fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3FR");
				int iSpec3to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3TO");
				int iSpec5fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5FR");
				int iSpec5to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5TO");
				String sEcsf_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ECSF");
				String sEtm_d    = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ETM");
				String sMatkl_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATKL");
				String sSvamt_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_SVAMT");
				String sWaerk_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_WAERK");
				String sGubun_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "GUBUN");
				String sStym_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "STYM");
				//신규추가(2016)
				String sAuse_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AUSE");        
				String sAcapa_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACAPA");    
				String sAopen_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AOPEN");    
				int iAfqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "AFQFR");    
				int iAfqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "AFQTO");    
				int iAstqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ASTQFR");    
				int iAstqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ASTQTO");    
				String sAdrv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ADRV");    
				String sAspc_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASPC");    
				String sAcd1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACD1");    
				String sAcd2_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACD2");    
				int iBfthfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "BFTHFR");    
				int iBfthto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "BFTHTO");    
				String sBwm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BWM");    
				String sBmopb_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BMOPB");    
				String sBcdm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BCDM");    
				String sBhr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BHR");    
				String sCjm1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1");    
				int iCjm1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CJM1QFR");    
				int iCjm1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CJM1QTO");    
				String sCjm1m_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1M");    
				String sCjm1fr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1FR");    
				String sChd1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CHD1");    
				int iChd1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHD1QFR");    
				int iChd1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHD1QTO");    
				String sChs1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CHS1");    
				int iChs1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHS1QFR");    
				int iChs1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHS1QTO");    
				String sDeph_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DEPH");    
				int iDephtqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "DEPHTQFR");    
				int iDephtqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "DEPHTQTO");    
				int iEhofr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHOFR");    
				int iEhoto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHOTO");    
				int iEhtrhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHTRHFR");    
				int iEhtrhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHTRHTO");    
				int iEhpto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHPTO");    
				int iEhpfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHPFR");    
				int iEhhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHHFR");    
				int iEhhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHHTO");    
				int iEhvfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHVFR");    
				int iEhvto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHVTO");    
				String sEhm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "EHM");         
				int iEccafr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCAFR");    
				int iEccato_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCATO");    
				int iEccbfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCBFR");    
				int iEccbto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCBTO");    
				int iEcchfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCHFR");    
				int iEcchto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCHTO");    
				int iEcaafr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECAAFR");    
				int iEcaato_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECAATO");    
				int iEcbbfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECBBFR");    
				int iEcbbto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECBBTO");    
				int iEceefr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECEEFR");    
				int iEceeto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECEETO");    
				String sEtmm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ETMM");    
				String sErpd_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPD");    
				String sErpw_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPW");    
				String sErpr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPR");    
				String sEcrl_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECRL");    
				String sEcgv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECGV");    
				String sEcwrl_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWRL");    
				String sEcww_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWW");    
				String sEcwsf_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWSF");    
				String sDsv1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DSV1");    
				String sDcctv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DCCTV");    
				String sDcair_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DCAIR");    
				int iEcjjfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECJJFR");    
				int iEcjjto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECJJTO");    
				int iEchhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECHHFR");    
				int iEchhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECHHTO");    
	 		    String sAstda_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASTDA");
	 		 //신규추가(2017.06)
	 		   String sAinov_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AINOV");
	 		   String sAstd_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASTD");
	 		   String sDeld_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DELD");
	 		   String sDsv2_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DSV2");
	 		   String sDhsbt_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DHSBT");
	 		 
	 		 
				int iQntySum    = 0;	//자재(도면) 누적건수

				
				
				// 자재(도면) 정보 추철건
				for (int iMatnr = iPos; iMatnr < dsRtnMatnr.getRowCount(); iMatnr++)	{

					boolean bSpec12 = false;
					boolean bSpec3  = false;
					boolean bSpec5  = false;
					boolean bEcsf   = false;
					boolean bEtm    = false;
					boolean bMatkl  = false;
					
					boolean bAuse   = false;
					boolean bAcapa  = false;
					boolean bAopen  = false;
					boolean bAfq    = false;
					boolean bAstq   = false;
					boolean bAdrv   = false;
					boolean bAspc   = false;
					boolean bAcd1   = false;
					boolean bAcd2   = false;
					boolean bBfth   = false;
					boolean bBwm    = false;
					boolean bBmopb  = false;
					boolean bBcdm   = false;
					boolean bBhr    = false;
					boolean bCjm1   = false;
					boolean bCjm1q  = false;
					boolean bCjm1m  = false;
					boolean bCjm1fr = false;
					boolean bChd1   = false;
					boolean bChd1q  = false;
					boolean bChs1   = false;
					boolean bChs1q  = false;
					boolean bDeph   = false;
					boolean bDephtq = false;
					boolean bEho    = false;
					boolean bEhtrh  = false;
					boolean bEhp    = false;
					boolean bEhh    = false;
					boolean bEhv    = false;
					boolean bEhm    = false;
					boolean bEcca   = false;
					boolean bEccb   = false;
					boolean bEcch   = false;
					boolean bEcaa   = false;
					boolean bEcbb   = false;
					boolean bEcee   = false;
					boolean bEtmm   = false;
					boolean bErpd   = false;
					boolean bErpw   = false;
					boolean bErpr   = false;
					boolean bEcrl   = false;
					boolean bEcgv   = false;
					boolean bEcwrl  = false;
					boolean bEcww   = false;
					boolean bEcwsf  = false;
					boolean bDsv1   = false;
					boolean bDcctv  = false;
					boolean bDcair  = false;
					boolean bEcjj   = false;
					boolean bEchh   = false;
					boolean bAstda  = false;
					boolean bAinov  = false;
					boolean bAstd   = false;
					boolean bDeld  = false;
					boolean bDsv2  = false;
					boolean bDhsbt  = false;
					

				

					String sItemM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ITEM");
					String sMatnrM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATNR");
					String sSpec12M = dsRtnMatnr.getColumnAsString(iMatnr,  "SPEC12");
					int iSpec3M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC3");
					int iSpec5M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC5");
					String sEcsfM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ECSF");
					String sEtmM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ETM");
					String sMatklM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATKL");
					
					//신규추가(2016)
				    String sAuseM =  dsRtnMatnr.getColumnAsString(iMatnr, "AUSE");
					String sAcapaM =  dsRtnMatnr.getColumnAsString(iMatnr, "ACAPA");
					String sAopenM =  dsRtnMatnr.getColumnAsString(iMatnr, "AOPEN");
					int iAfqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "AFQ");
					int iAstqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ASTQ");
					String sAdrvM =  dsRtnMatnr.getColumnAsString(iMatnr, "ADRV");
					String sAspcM =  dsRtnMatnr.getColumnAsString(iMatnr, "ASPC");
					String sAcd1M =  dsRtnMatnr.getColumnAsString(iMatnr, "ACD1");
					String sAcd2M =  dsRtnMatnr.getColumnAsString(iMatnr, "ACD2");
					int iBfthM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "BFTH");
					String sBwmM =  dsRtnMatnr.getColumnAsString(iMatnr, "BWM");
					String sBmopbM =  dsRtnMatnr.getColumnAsString(iMatnr, "BMOPB");
					String sBcdmM =  dsRtnMatnr.getColumnAsString(iMatnr, "BCDM");
					String sBhrM =  dsRtnMatnr.getColumnAsString(iMatnr, "BHR");
					String sCjm1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1");
					int iCjm1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CJM1Q");
					String sCjm1mM =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1M");
					String sCjm1frM =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1FR");
					String sChd1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CHD1");
					int iChd1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CHD1Q");
					String sChs1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CHS1");
					int iChs1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CHS1Q");
					String sDephM =  dsRtnMatnr.getColumnAsString(iMatnr, "DEPH");
					int iDephtqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "DEPHTQ");
					int iEhoM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHO");
					int iEhtrhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHTRH");
					int iEhpM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHP");
					int iEhhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHH");
					int iEhvM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHV");
					String sEhmM =  dsRtnMatnr.getColumnAsString(iMatnr, "EHM");
					int iEccaM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCA");
					int iEccbM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCB");
					int iEcchM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCH");
					int iEcaaM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECAA");
					int iEcbbM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECBB");
					int iEceeM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECEE");
					String sEtmmM =  dsRtnMatnr.getColumnAsString(iMatnr, "ETMM");
					String sErpdM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPD");
					String sErpwM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPW");
					String sErprM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPR");
					String sEcrlM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECRL");
					String sEcgvM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECGV");
					String sEcwrlM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWRL");
					String sEcwwM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWW");
					String sEcwsfM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWSF");
					String sDsv1M =  dsRtnMatnr.getColumnAsString(iMatnr, "DSV1");
					String sDcctvM =  dsRtnMatnr.getColumnAsString(iMatnr, "DCCTV");
					String sDcairM =  dsRtnMatnr.getColumnAsString(iMatnr, "DCAIR");
					int iEcjjM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECJJ");
					int iEchhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECHH");
					String sAstdaM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ASTDA");
					String sAinovM    = dsRtnMatnr.getColumnAsString(iMatnr,  "AINOV");
					String sAstdM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ASTD");
					String sDeldM    = dsRtnMatnr.getColumnAsString(iMatnr,  "DELD");
					String sDsv2M    = dsRtnMatnr.getColumnAsString(iMatnr,  "DSV2");
					String sDhsbtM    = dsRtnMatnr.getColumnAsString(iMatnr,  "DHSBT");
					
                    // 신규추가  끝	
                  
					int iQntyM      = dsRtnMatnr.getColumnAsDouble(iMatnr,  "QNTY").intValue();

					// ITEM비교
					if (sItem_d != null && sItem_d.equals(sItemM))		{

						// 자재(도면)번호 비교 : 동일하면 확인
						if (sMatnr_d != null && sMatnr_d.equals(sMatnrM))	{

							// SPEC비교 (해당 값이 존재하는 경우)
							if (sSpec12_d == null || sSpec12_d.equals(""))	{
								bSpec12 = true;
							}else if (sSpec12_d != null && sSpec12M != null && !"".equals(sSpec12M) && sSpec12_d.indexOf(sSpec12M) >= 0)	{
								bSpec12 = true;
							}
							
							
							// 속도 비교
							if (iSpec3fr_d <= iSpec3M && iSpec3M <= iSpec3to_d )	{
								bSpec3 = true;
							}

							// 인승 비교
							if (iSpec5fr_d <= iSpec5M && iSpec5M <= iSpec5to_d )	{
								bSpec5 = true;
							}

							// CAR SAFETY 비교 (해당 값이 존재하는 경우)
							if (sEcsf_d == null || sEcsf_d.equals(""))	{
								bEcsf = true;
							}else if (sEcsfM != null && sEcsfM.indexOf(sEcsf_d) >= 0)	{
								bEcsf = true;
							}

							// TM 비교 (해당 값이 존재하는 경우)
						/*	if (sEtm_d == null || sEtm_d.equals(""))	{
								bEtm = true;
							}else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
								bEtm = true;
							}
						*/	
							if (sEtm_d == null || sEtm_d.equals(""))	{
								bEtm = true;
							}else if (sEtm_d != null && sEtmM != null && !"".equals(sEtmM) && sEtm_d.indexOf(sEtmM) >= 0)	{
								bEtm = true;
							}
						    else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
							bEtm = true;
						    }
							

							// BLOK NO 비교 (해당 값이 존재하는 경우)
							if (sMatkl_d == null || sMatkl_d.equals(""))	{
								bMatkl = true;
							}else if (sMatklM != null && sMatklM.indexOf(sMatkl_d) >= 0)	{
								bMatkl = true;
							}
							//2016.01 추가
							// 용도 비교 
					       if (sAuse_d == null || sAuse_d.equals(""))	{
								bAuse = true;
							}else if (sAuseM != null && sAuseM.indexOf(sAuse_d) >= 0)	{
								bAuse  = true;
							}
							// 용량 비교 
						   if (sAcapa_d == null || sAcapa_d.equals(""))	{
								bAcapa = true;
							}else if (sAcapaM != null && sAcapaM.indexOf(sAcapa_d) >= 0)	{
								bAcapa  = true;
							}
							// 열림 비교 
						    if (sAopen_d == null || sAopen_d.equals(""))	{
								bAopen = true;
							}else if (sAopenM != null && sAopenM.indexOf(sAopen_d) >= 0)	{
								bAopen  = true;
							}
							// 층수 비교 
							if (iAfqfr_d <= iAfqM && iAfqM <= iAfqto_d )	{
								bAfq = true;
							}
						//System.out.println(" iAfqfr_d : " +iAfqfr_d
						//		           +  " iAfqM : " + iAfqM  +   " iAfqto_d : " + iAfqto_d );
				              						
					    	// 정지 층수 비교
						   if (iAstqfr_d <= iAstqM && iAstqM <= iAstqto_d )	{
								bAstq = true;
							}
							// 운행방식 비교 
			   		       if (sAdrv_d == null || sAdrv_d.equals(""))	{
								bAdrv = true;
							}else if (sAdrvM != null && sAdrvM.indexOf(sAdrv_d) >= 0)	{
								bAdrv  = true;
							}
							// 시방서 비교 
			   			    if (sAspc_d == null || sAspc_d.equals(""))	{
								bAspc = true;
							}else if (sAspcM != null && sAspcM.indexOf(sAspc_d) >= 0)	{
								bAspc  = true;
							}
							// 국내/해외 구분 비교 
			   			    if (sAcd1_d == null || sAcd1_d.equals(""))	{
								bAcd1 = true;
							}else if (sAcd1M != null && sAcd1M.indexOf(sAcd1_d) >= 0)	{
								bAcd1  = true;
							}
							//  적용코드  비교 
			   			    if (sAcd2_d == null || sAcd2_d.equals(""))	{
								bAcd2 = true;
							}else if (sAcd2M != null && sAcd2M.indexOf(sAcd2_d) >= 0)	{
								bAcd2  = true;
							}
							// FLOOR 두께  비교
							if (iBfthfr_d <= iBfthM && iBfthM <= iBfthto_d )	{
								bBfth = true;
							}
							//  CAR WALL  비교 
			   			   if (sBwm_d == null || sBwm_d.equals(""))	{
								bBwm = true;
							}else if (sBwmM != null && sBwmM.indexOf(sBwm_d) >= 0)	{
								bBwm  = true;
							}
							//  main opb  비교 
			   			    if (sBmopb_d == null || sBmopb_d.equals(""))	{
								bBmopb = true;
							}else if (sBmopbM != null && sBmopbM.indexOf(sBmopb_d) >= 0)	{
								bBmopb  = true;
							}
							//    CAR DOOR 비교 
			   			   if (sBcdm_d == null || sBcdm_d.equals(""))	{
								bBcdm = true;
							}else if (sBcdmM != null && sBcdmM.indexOf(sBcdm_d) >= 0)	{
								bBcdm  = true;
							}
							// handrail  비교 
			   			    if (sBhr_d == null || sBhr_d.equals(""))	{
								bBhr = true;
							}else if (sBhrM != null && sBhrM.indexOf(sBhr_d) >= 0)	{
								bBhr  = true;
							}
							// jamb  비교 
			   			    if (sCjm1_d == null || sCjm1_d.equals(""))	{
								bCjm1 = true;
							}else if (sCjm1M != null && sCjm1M.indexOf(sCjm1_d) >= 0)	{
								bCjm1  = true;
							}
							// jmb 수량  비교
							if (iCjm1qfr_d <= iCjm1qM && iCjm1qM <= iCjm1qto_d )	{
								bCjm1q = true;
							}
							// jamb 재질 비교 
			   			    if (sCjm1m_d == null || sCjm1m_d.equals(""))	{
								bCjm1m = true;
							}else if (sCjm1mM != null && sCjm1mM.indexOf(sCjm1m_d) >= 0)	{
								bCjm1m  = true;
							}
							// jamb 방화도어 비교 
			   			    if (sCjm1fr_d == null || sCjm1fr_d.equals(""))	{
								bCjm1fr = true;
							}else if (sCjm1frM != null && sCjm1frM.indexOf(sCjm1fr_d) >= 0)	{
								bCjm1fr  = true;
							}
							// HATCH DOOR  비교 
			   			    if (sChd1_d == null || sChd1_d.equals(""))	{
								bChd1 = true;
							}else if (sChd1M != null && sChd1M.indexOf(sChd1_d) >= 0)	{
								bChd1  = true;
							}
							// HATCH DOOR 수량  비교
							if (iChd1qfr_d <= iChd1qM && iChd1qM <= iChd1qto_d )	{
								bChd1q = true;
							}
							// HATCH SILL  비교 
			   			   if (sChs1_d == null || sChs1_d.equals(""))	{
								bChs1 = true;
							}else if (sChs1M != null && sChs1M.indexOf(sChs1_d) >= 0)	{
								bChs1  = true;
							}
							// HATCH SILL 수량  비교
							if (iChs1qfr_d <= iChs1qM && iChs1qM <= iChs1qto_d )	{
								bChs1q = true;
							}
							// 비상통화장치  비교 
			   			    if (sDeph_d == null || sDeph_d.equals(""))	{
								bDeph = true;
							}else if (sDephM != null && sDephM.indexOf(sDeph_d) >= 0)	{
								bDeph  = true;
							}
							// 비상통화장치 적용 대수  비교
							if (iDephtqfr_d <= iDephtqM && iDephtqM <= iDephtqto_d )	{
								bDephtq = true;
							}
							// 승강로 overhead  비교
							if (iEhofr_d <= iEhoM && iEhoM <= iEhoto_d )	{
								bEho = true;
							}
							// 승강로 travel heigth   비교
							if (iEhtrhfr_d <= iEhtrhM && iEhtrhM <= iEhtrhto_d )	{
								bEhtrh = true;
							}
							//System.out.println(" iEhtrhfr_d : " +iEhtrhfr_d
								//	           +  " iEhtrhM : " + iEhtrhM  +   " iEhtrhto_d : " + iEhtrhto_d );
							// 승강로 pit   비교
							if (iEhpfr_d <= iEhpM && iEhpM <= iEhpto_d )	{
								bEhp = true;
							}
							// 승강로  가로   비교
							if (iEhhfr_d <= iEhhM && iEhhM <= iEhhto_d )	{
								bEhh = true;
							}
							// 승강로 세로   비교
								if (iEhvfr_d <= iEhvM && iEhvM <= iEhvto_d )	{
								bEhv = true;
							}
							// 승강로 재질   비교
							if (sEhm_d == null || sEhm_d.equals(""))	{
								bEhm = true;
							}else if (sEhmM != null && sEhmM.indexOf(sEhm_d) >= 0)	{
								bEhm  = true;
							}
							// car ca    비교
							if (iEccafr_d <= iEccaM && iEccaM <= iEccato_d )	{
								bEcca = true;
							}
							// car cb    비교
							if (iEccbfr_d <= iEccbM && iEccbM <= iEccbto_d )	{
								bEccb = true;
							}
							// car ch    비교
							if (iEcchfr_d <= iEcchM && iEcchM <= iEcchto_d )	{
								bEcch = true;
							}
							// door jj    비교
							if (iEcjjfr_d <= iEcjjM && iEcjjM <= iEcjjto_d )	{
								bEcjj = true;
							}
							// door hh   비교
							if (iEchhfr_d <= iEchhM && iEchhM <= iEchhto_d )	{
								bEchh = true;
							}
							// car aa  비교
							if (iEcaafr_d <= iEcaaM && iEcaaM <= iEcaato_d )	{
								bEcaa = true;
							}
							// car bb  비교
							if (iEcbbfr_d <= iEcbbM && iEcbbM <= iEcbbto_d )	{
								bEcbb = true;
							}
							// car ee  비교
							if (iEceefr_d <= iEceeM && iEceeM <= iEceeto_d )	{
								bEcee = true;
							}
							// motor 용량  비교 
			   			    if (sEtmm_d == null || sEtmm_d.equals(""))	{
								bEtmm = true;
							}else if (sEtmmM != null && sEtmmM.indexOf(sEtmm_d) >= 0)	{
								bEtmm  = true;
							}					
			   			    // rope 직경  비교 
			   			    if (sErpd_d == null || sErpd_d.equals(""))	{
								bErpd = true;
							}else if (sErpdM != null && sErpdM.indexOf(sErpd_d) >= 0)	{
								bErpd  = true;
							}
			   			    // rope 본수  비교 
			   			    if (sErpw_d == null || sErpw_d.equals(""))	{
								bErpw = true;
							}else if (sErpwM != null && sErpwM.indexOf(sErpw_d) >= 0)	{
								bErpw  = true;
							}
			   			    // rope roping 비교 
			   			    if (sErpr_d == null || sErpr_d.equals(""))	{
								bErpr = true;
							}else if (sErprM != null && sErprM.indexOf(sErpr_d) >= 0)	{
								bErpr  = true;
							}
			   			    // car rail(k) 비교 
			   			    if (sEcrl_d == null || sEcrl_d.equals(""))	{
								bEcrl = true;
							}else if (sEcrlM != null && sEcrlM.indexOf(sEcrl_d) >= 0)	{
								bEcrl  = true;
							}
			   			    // car governor 비교 
			   			    if (sEcgv_d == null || sEcgv_d.equals(""))	{
								bEcgv = true;
							}else if (sEcgvM != null && sEcgvM.indexOf(sEcgv_d) >= 0)	{
								bEcgv  = true;
							}
			   			    // cwt rail (k) 비교 
			   			    if (sEcwrl_d == null || sEcwrl_d.equals(""))	{
								bEcwrl = true;
							}else if (sEcwrlM != null && sEcwrlM.indexOf(sEcwrl_d) >= 0)	{
								bEcwrl  = true;
							}	   			    
			   			    // cwt weight  비교 
			   			    if (sEcww_d == null || sEcww_d.equals(""))	{
								bEcww = true;
							}else if (sEcwwM != null && sEcwwM.indexOf(sEcww_d) >= 0)	{
								bEcww  = true;
							}	   	
			   			    // cwt safety  비교 
			   			    if (sEcwsf_d == null || sEcwsf_d.equals(""))	{
								bEcwsf = true;
							}else if (sEcwsfM != null && sEcwsfM.indexOf(sEcwsf_d) >= 0)	{
								bEcwsf  = true;
							}	  
			   			    // 감시반  비교 
			   			    if (sDsv1_d == null || sDsv1_d.equals(""))	{
								bDsv1 = true;
							}else if (sDsv1M != null && sDsv1M.indexOf(sDsv1_d) >= 0)	{
								bDsv1  = true;
							}	  
			   			    // CCTV카메라  비교 
			   			    if (sDcctv_d == null || sDcctv_d.equals(""))	{
								bDcctv = true;
							}else if (sDcctvM != null && sDcctvM.indexOf(sDcctv_d) >= 0)	{
								bDcctv  = true;
							}	  
			   			    // CAR 에어컨   비교 
			   			    if (sDcair_d == null || sDcair_d.equals(""))	{
								bDcair = true;
							}else if (sDcairM != null && sDcairM.indexOf(sDcair_d) >= 0)	{
								bDcair  = true;
							}
			   			   //건축허가일
			   			    if (sAstda_d == null || sAstda_d.equals(""))	{
								bAstda = true;
							}else if (sAstdaM != null && sAstdaM.indexOf(sAstda_d) >= 0)	{
								bAstda  = true;
							}
				   			//이노버 적용여부
			   			    if (sAinov_d == null || sAinov_d.equals(""))	{
								bAinov = true;
							}else if (sAinovM != null && sAinovM.indexOf(sAinov_d) >= 0)	{
								bAinov  = true;
							}
				   		 //2013년 9월 15일 이후 건축허가
			   			    if (sAstd_d == null || sAstd_d.equals(""))	{
								bAstd = true;
							}else if (sAstdM != null && sAstdM.indexOf(sAstd_d) >= 0)	{
								bAstd  = true;
							}
				   		 //ELD 운전
			   			    if (sDeld_d == null || sDeld_d.equals(""))	{
			   			    	bDeld = true;
							}else if (sDeldM != null && sDeldM.indexOf(sDeld_d) >= 0)	{
								bDeld  = true;
							}
				   		//감시반2
			   			    if (sDsv2_d == null || sDsv2_d.equals(""))	{
								bDsv2 = true;
							}else if (sDsv2M != null && sDsv2M.indexOf(sDsv2_d) >= 0)	{
								bDsv2  = true;
							}
				   	   //세내대버튼
			   			    if (sDhsbt_d == null || sDhsbt_d.equals(""))	{
								bDhsbt = true;
							}else if (sDhsbtM != null && sDhsbtM.indexOf(sDhsbt_d) >= 0)	{
								bDhsbt  = true;
							}
			   			    
							 
                            /*================================적용완료 2016.01 ======================================*/
			   			    
							// 모든조건을 만족 (자재건수 누적)
							if (bSpec12 && bSpec3 && bSpec5 && bEcsf && bEtm && bMatkl && bAuse && bAcapa && bAopen && bAfq && bAstq && bAdrv && bAspc && bAcd1
									&& bAcd2 && bBfth && bBwm  && bBmopb && bBcdm && bBhr && bCjm1 && bCjm1q && bCjm1m && bCjm1fr && bChd1 && bChd1q && bChs1 && bChs1q
									&& bDeph && bDephtq && bEho && bEhtrh && bEhtrh && bEhp && bEhh && bEhv && bEhm && bEcca && bEccb && bEcch && bEcaa && bEcbb && bEcee  
									&& bEtmm && bErpd && bErpw && bErpr && bEcrl && bEcgv && bEcwrl && bEcww && bEcwsf && bDsv1 && bDcctv && bDcair && bEcjj && bEchh && bAstda
									&& bAinov && bAstd && bDeld && bDsv2 && bDhsbt)	{
								
								   iQntySum += iQntyM;

								// 집계 처리용도
								bSumCheck = true;
							}

						}else	{	// 자재(도면) 번호가 상이하면
							// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
							// 집계 처리가 되도록 요청에 의하여 bSumCheck가 false여도 처리

							//if (bSumCheck)	{
								// 임시Dataset에 추가
								int iRow = dsTempSum.appendRow();
								
								dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
								dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
								dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
								dsTempSum.setColumn(iRow, "STYM",  sStym_d);
								dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
								dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
								dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
	
								bSumCheck = false;
							//}

							break;
						}
					}else	{	// ITEM이 상이하면 실적정보가 존재하지 않음 SKIP
						// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
						// 집계 처리가 되도록 요청에 의하여 bSumCheck가 false여도 처리

						//if (bSumCheck)		{
							// 임시Dataset에 추가
							int iRow = dsTempSum.appendRow();
							
							dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
							dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
							dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
							dsTempSum.setColumn(iRow, "STYM",  sStym_d);
							dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
							dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
							dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
							
							bSumCheck = false;
						//}

						break;
					}

					//자료의 위치
					iPos++;
				}
				// 최종집계 내역
				if (iPos == dsRtnMatnr.getRowCount())	{
					// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
					// 집계 처리가 되도록 요청에 의하여 bSumCheck가 false여도 처리

					//if (bSumCheck)		{
						// 임시Dataset에 추가
						int iRow = dsTempSum.appendRow();
						
						dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
						dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
						dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
						dsTempSum.setColumn(iRow, "STYM",  sStym_d);
						dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
						dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
						dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
						
						bSumCheck = false;
					//}
					// 실적 건수 최종으로 처리 불필요
					break;
				}

				//
			}

			/*
			// log출력
			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{
				System.out.println(" ITEM : " + dsTempSum.getColumnAsString(iMatnr, "ITEM") + "     " +
						           " MATNR : " + dsTempSum.getColumnAsString(iMatnr, "MATNR") + "     " +
						           " GUBUN : " + dsTempSum.getColumnAsString(iMatnr, "GUBUN") + "     " +
						           " STYM : " + dsTempSum.getColumnAsString(iMatnr, "STYM") + "     " +
						           " QNTY : " + dsTempSum.getColumnAsString(iMatnr, "QNTY") + "     " +
						           " MATNR_SVAMT : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT") + "     " +
						           " MATNR_WAERK : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK") + "....."
						);
			}

			//TransactionManager.rollback();

			*/
			
//			/* 최종 조회 결과 확인 후 처리
			// 산출된 Item 정보
			HashMap hashMap = new HashMap();

			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{

				String sStym = dsTempSum.getColumnAsString(iMatnr, "STYM");
				int iQnty    = dsTempSum.getColumnAsDouble(iMatnr, "QNTY").intValue();

				// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
				// 집계 처리가 되도록 요청에 의하여 comment

				// 0의 경우 실적 제외 (단 최초 실적월이 존재하지 않아야 함)
				//if (iQnty == 0 && (sStym == null || "".equals(sStym)))	{
				//	continue;
				//}


				// 실적 처리
				if (hashMap.get(dsTempSum.getColumnAsString(iMatnr, "ITEM")) == null)	{

					String sRsltym = "";
					if (sStym == null || "".equals(sStym))	{
						sRsltym = ds_cond.getColumnAsString(i, "RSLTYM");
					}

					hashMap.put(dsTempSum.getColumnAsString(iMatnr, "ITEM"), sRsltym);		// 최초 실적년월이 존재하는 경우 ""
				}

				// 둥록된 자재/도면 번호의 실적 처리 
				zqmt075u.addParamObject("ITEM",  dsTempSum.getColumnAsString(iMatnr, "ITEM"));	// 항목
				zqmt075u.addParamObject("GUBUN", dsTempSum.getColumnAsString(iMatnr, "GUBUN"));	// 자재/도면번호 구분
				zqmt075u.addParamObject("MATNR", dsTempSum.getColumnAsString(iMatnr, "MATNR"));	// 자재/도면번호
				zqmt075u.addParamObject("SVAMT", dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT"));	// 절감액
				zqmt075u.addParamObject("WAERK", dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK"));	// 화폐딴위
				zqmt075u.addParamObject("QNTY",  iQnty + "");	// 수량
	
				// 자재/도면 집계 정보 등록(ZQMT075)
				zqmt075u.setRowIndex(i);
				executor.execute(zqmt075u);
			}

			// 항목별 집계 처리
			Collection collection = hashMap.keySet();
			Iterator   iter       = collection.iterator();

			while(iter.hasNext())	{
				String sKey   = ((String) iter.next());
				String sValue = ((String) hashMap.get(sKey));	// ""이 아니면 실적년월 등록 필요

				// 산출된 기 항목별 실적집계전 해당 항목 삭제 (ZQMT076)
				zqmt076dd.addParamObject("ITEM", sKey);

				zqmt076dd.setRowIndex(i);
				executor.execute(zqmt076dd);

				// 항목별 집계 정보 등록 (ZQMT076) :  자재/도면번호별로 집계된 정보 집계
				zqmt076i.addParamObject("ITEM", sKey);	// 항목

				zqmt076i.setRowIndex(i);
				executor.execute(zqmt076i);

				// 최최 실적일자가 없는 경우에만 반영
				if (sValue != null && !"".equals(sValue))	{
					// 최초 실적등록일 정보 Update
					zqmt071u.addParamObject("ITEM", sKey);	// 항목
					zqmt071u.addParamObject("STYM", ds_cond.getColumnAsString(i, "RSLTYM"));	// 최초실적일자

					zqmt071u.setRowIndex(i);
					executor.execute(zqmt071u);
				}
			}

			// 집계 처리 이력 등록(ZQMT074)
			zqmt074u.setRowIndex(i);
			executor.execute(zqmt074u);
//			*/
		}
	}

	// 자재/도면 실적 집계 처리 (세부조건 추가)
	// TEST
	public void matnrProcCondAdd3(BusinessContext ctx, Dataset ds_cond) throws Exception {
		//System.out.println("a");
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 자재/도면번호 기 실적집계 삭제 (ZQMT075)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 산출된 기 항목별 실적집계 삭제 (ZQMT076) 시스템에 의하여 기 자동 산출건 삭제
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재(gubun=1) 도면(gubun=2) 번호로 수량 추출 (상세조건 포함)
		DatasetSqlRequest matnrs
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001T_S6");

		matnrs.addParamObject("ds_cond", ds_cond);
		matnrs.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재(gubun=1) 도면(gubun=2) 상세조건 조회
		DatasetSqlRequest matnrsDetail
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001T_S7");

		matnrsDetail.addParamObject("ds_cond", ds_cond);
		matnrsDetail.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재/도면번호 실적집계 (ZQMT075)
		DatasetSqlRequest zqmt075u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U1");

		zqmt075u.addParamObject("ds_list",   ds_cond);
		zqmt075u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt075u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 산출된 기 항목별 실적집계 삭제 (ZQMT076) 입력건중 시스템에 의하여 산출된건 삭제
		DatasetSqlRequest zqmt076dd
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D3");

		zqmt076dd.addParamObject("ds_list", ds_cond);
		zqmt076dd.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 항목별 실적집계 (ZQMT076)
		DatasetSqlRequest zqmt076i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_I1");

		zqmt076i.addParamObject("ds_list",   ds_cond);
		zqmt076i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt076i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt076i.addParamObject("SGBN",      "S");	// M : 수기,    S : 시스템
		zqmt076i.addParamObject("BIGO",      "");
		zqmt076i.addParamObject("KGBN",      "T");	// T : 총액기준, U : 단가기준

		// 최초 실적월 수정 (ZQMT071)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702002A_U1");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 집계처리 이력 (ZQMT074)
		DatasetSqlRequest zqmt074u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U2");

		zqmt074u.addParamObject("ds_list", ds_cond);
		zqmt074u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt074u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 실적집계대상 항목의 자재/도면번호 대상 조회
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{
	
			// 기 처리된 실적집계 정보 삭제 처리
			// 자재/도면번호 기 실적집계 삭제 (ZQMT075)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);
			System.out.println(i);
			// 산출된 기 항목별 실적집계 삭제 (ZQMT076)
			zqmt076d.addParamObject("SGBN", "S");	// 시스템 산출
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);

			// 자재/도면  집계 대상 조회
			matnrs.setRowIndex(i);
			Dataset dsRtnMatnr = (Dataset)executor.query(matnrs).getResultObject();

			// 자재/도면 상세조회 대상 조회 2013.07.17
			matnrsDetail.setRowIndex(i);
			Dataset dsRtnMatnrDetail = (Dataset)executor.query(matnrsDetail).getResultObject();

			// 상세조건에 따른 조건 확인 및 집계 처리
			Dataset dsTempSum = new Dataset();

			// 컬럼 setting
			dsTempSum.addColumn("ITEM",        (short)1, 256);
			dsTempSum.addColumn("STYM",        (short)1, 256);
			dsTempSum.addColumn("GUBUN",       (short)1, 256);
			dsTempSum.addColumn("MATNR_SVAMT", (short)1, 256);
			dsTempSum.addColumn("MATNR_WAERK", (short)1, 256);
			dsTempSum.addColumn("MATNR",       (short)1, 256);
			dsTempSum.addColumn("QNTY",        (short)1, 256);

			// 자재(도면) 집계 
			int iPos = 0;

			// 세부조건 확인
			for(int iDetail = 0; iDetail < dsRtnMatnrDetail.getRowCount(); iDetail++)		{

				boolean bSumCheck = false;

				String sItem_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ITEM");
				String sMatnr_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR");
				String sSpec12_d = dsRtnMatnrDetail.getColumnAsString(iDetail,  "SPEC12");
				int iSpec3fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3FR");
				int iSpec3to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3TO");
				int iSpec5fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5FR");
				int iSpec5to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5TO");
				String sEcsf_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ECSF");
				String sEtm_d    = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ETM");
				String sMatkl_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATKL");
				String sSvamt_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_SVAMT");
				String sWaerk_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_WAERK");
				String sGubun_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "GUBUN");
				String sStym_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "STYM");
				//신규추가(2016)
				String sAuse_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AUSE");        
				String sAcapa_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACAPA");    
				String sAopen_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AOPEN");    
				int iAfqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "AFQFR");    
				int iAfqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "AFQTO");    
				int iAstqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ASTQFR");    
				int iAstqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ASTQTO");    
				String sAdrv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ADRV");    
				String sAspc_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASPC");    
				String sAcd1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACD1");    
				String sAcd2_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ACD2");    
				int iBfthfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "BFTHFR");    
				int iBfthto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "BFTHTO");    
				String sBwm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BWM");    
				String sBmopb_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BMOPB");    
				String sBcdm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BCDM");    
				String sBhr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "BHR");    
				String sCjm1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1");    
				int iCjm1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CJM1QFR");    
				int iCjm1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CJM1QTO");    
				String sCjm1m_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1M");    
				String sCjm1fr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CJM1FR");    
				String sChd1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CHD1");    
				int iChd1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHD1QFR");    
				int iChd1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHD1QTO");    
				String sChs1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "CHS1");    
				int iChs1qfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHS1QFR");    
				int iChs1qto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "CHS1QTO");    
				String sDeph_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DEPH");    
				int iDephtqfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "DEPHTQFR");    
				int iDephtqto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "DEPHTQTO");    
				int iEhofr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHOFR");    
				int iEhoto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHOTO");    
				int iEhtrhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHTRHFR");    
				int iEhtrhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHTRHTO");    
				int iEhpto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHPTO");    
				int iEhpfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHPFR");    
				int iEhhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHHFR");    
				int iEhhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHHTO");    
				int iEhvfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHVFR");    
				int iEhvto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "EHVTO");    
				String sEhm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "EHM");         
				int iEccafr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCAFR");    
				int iEccato_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCATO");    
				int iEccbfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCBFR");    
				int iEccbto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCBTO");    
				int iEcchfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCHFR");    
				int iEcchto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECCHTO");    
				int iEcaafr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECAAFR");    
				int iEcaato_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECAATO");    
				int iEcbbfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECBBFR");    
				int iEcbbto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECBBTO");    
				int iEceefr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECEEFR");    
				int iEceeto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECEETO");    
				String sEtmm_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ETMM");    
				String sErpd_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPD");    
				String sErpw_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPW");    
				String sErpr_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ERPR");    
				String sEcrl_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECRL");    
				String sEcgv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECGV");    
				String sEcwrl_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWRL");    
				String sEcww_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWW");    
				String sEcwsf_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ECWSF");    
				String sDsv1_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DSV1");    
				String sDcctv_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DCCTV");    
				String sDcair_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DCAIR");    
				int iEcjjfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECJJFR");    
				int iEcjjto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECJJTO");    
				int iEchhfr_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECHHFR");    
				int iEchhto_d = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "ECHHTO");    
	 		    String sAstda_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASTDA");
	 		   String sDuopb_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DUOPB");
		 		 //신규추가(2017.06)
	 		   String sAinov_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "AINOV");
	 		   String sAstd_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "ASTD");
	 		   String sDeld_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DELD");
	 		   String sDsv2_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DSV2");
	 		   String sDhsbt_d = dsRtnMatnrDetail.getColumnAsString(iDetail, "DHSBT");

				int iQntySum    = 0;	//자재(도면) 누적건수

				// 자재(도면) 정보 추철건
				for (int iMatnr = iPos; iMatnr < dsRtnMatnr.getRowCount(); iMatnr++)	{

					boolean bSpec12 = false;
					boolean bSpec3  = false;
					boolean bSpec5  = false;
					boolean bEcsf   = false;
					boolean bEtm    = false;
					boolean bMatkl  = false;
					
					boolean bAuse   = false;
					boolean bAcapa  = false;
					boolean bAopen  = false;
					boolean bAfq    = false;
					boolean bAstq   = false;
					boolean bAdrv   = false;
					boolean bAspc   = false;
					boolean bAcd1   = false;
					boolean bAcd2   = false;
					boolean bBfth   = false;
					boolean bBwm    = false;
					boolean bBmopb  = false;
					boolean bBcdm   = false;
					boolean bBhr    = false;
					boolean bCjm1   = false;
					boolean bCjm1q  = false;
					boolean bCjm1m  = false;
					boolean bCjm1fr = false;
					boolean bChd1   = false;
					boolean bChd1q  = false;
					boolean bChs1   = false;
					boolean bChs1q  = false;
					boolean bDeph   = false;
					boolean bDephtq = false;
					boolean bEho    = false;
					boolean bEhtrh  = false;
					boolean bEhp    = false;
					boolean bEhh    = false;
					boolean bEhv    = false;
					boolean bEhm    = false;
					boolean bEcca   = false;
					boolean bEccb   = false;
					boolean bEcch   = false;
					boolean bEcaa   = false;
					boolean bEcbb   = false;
					boolean bEcee   = false;
					boolean bEtmm   = false;
					boolean bErpd   = false;
					boolean bErpw   = false;
					boolean bErpr   = false;
					boolean bEcrl   = false;
					boolean bEcgv   = false;
					boolean bEcwrl  = false;
					boolean bEcww   = false;
					boolean bEcwsf  = false;
					boolean bDsv1   = false;
					boolean bDcctv  = false;
					boolean bDcair  = false;
					boolean bEcjj   = false;
					boolean bEchh   = false;
					boolean bAstda  = false;
					boolean bDuopb  = false;
					boolean bAinov  = false;
					boolean bAstd   = false;
					boolean bDeld  = false;
					boolean bDsv2  = false;
					boolean bDhsbt  = false;

					String sItemM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ITEM");
					String sMatnrM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATNR");
					String sSpec12M = dsRtnMatnr.getColumnAsString(iMatnr,  "SPEC12");
					int iSpec3M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC3");
					int iSpec5M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC5");
					String sEcsfM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ECSF");
					String sEtmM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ETM");
					String sMatklM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATKL");
					
					//신규추가(2016)
				    String sAuseM =  dsRtnMatnr.getColumnAsString(iMatnr, "AUSE");
					String sAcapaM =  dsRtnMatnr.getColumnAsString(iMatnr, "ACAPA");
					String sAopenM =  dsRtnMatnr.getColumnAsString(iMatnr, "AOPEN");
					int iAfqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "AFQ");
					int iAstqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ASTQ");
					String sAdrvM =  dsRtnMatnr.getColumnAsString(iMatnr, "ADRV");
					String sAspcM =  dsRtnMatnr.getColumnAsString(iMatnr, "ASPC");
					String sAcd1M =  dsRtnMatnr.getColumnAsString(iMatnr, "ACD1");
					String sAcd2M =  dsRtnMatnr.getColumnAsString(iMatnr, "ACD2");
					int iBfthM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "BFTH");
					String sBwmM =  dsRtnMatnr.getColumnAsString(iMatnr, "BWM");
					String sBmopbM =  dsRtnMatnr.getColumnAsString(iMatnr, "BMOPB");
					String sBcdmM =  dsRtnMatnr.getColumnAsString(iMatnr, "BCDM");
					String sBhrM =  dsRtnMatnr.getColumnAsString(iMatnr, "BHR");
					String sCjm1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1");
					int iCjm1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CJM1Q");
					String sCjm1mM =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1M");
					String sCjm1frM =  dsRtnMatnr.getColumnAsString(iMatnr, "CJM1FR");
					String sChd1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CHD1");
					int iChd1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CHD1Q");
					String sChs1M =  dsRtnMatnr.getColumnAsString(iMatnr, "CHS1");
					int iChs1qM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "CHS1Q");
					String sDephM =  dsRtnMatnr.getColumnAsString(iMatnr, "DEPH");
					int iDephtqM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "DEPHTQ");
					int iEhoM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHO");
					int iEhtrhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHTRH");
					int iEhpM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHP");
					int iEhhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHH");
					int iEhvM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "EHV");
					String sEhmM =  dsRtnMatnr.getColumnAsString(iMatnr, "EHM");
					int iEccaM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCA");
					int iEccbM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCB");
					int iEcchM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECCH");
					int iEcaaM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECAA");
					int iEcbbM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECBB");
					int iEceeM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECEE");
					String sEtmmM =  dsRtnMatnr.getColumnAsString(iMatnr, "ETMM");
					String sErpdM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPD");
					String sErpwM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPW");
					String sErprM =  dsRtnMatnr.getColumnAsString(iMatnr, "ERPR");
					String sEcrlM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECRL");
					String sEcgvM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECGV");
					String sEcwrlM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWRL");
					String sEcwwM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWW");
					String sEcwsfM =  dsRtnMatnr.getColumnAsString(iMatnr, "ECWSF");
					String sDsv1M =  dsRtnMatnr.getColumnAsString(iMatnr, "DSV1");
					String sDcctvM =  dsRtnMatnr.getColumnAsString(iMatnr, "DCCTV");
					String sDcairM =  dsRtnMatnr.getColumnAsString(iMatnr, "DCAIR");
					int iEcjjM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECJJ");
					int iEchhM =  dsRtnMatnr.getColumnAsInteger(iMatnr, "ECHH");
					String sAstdaM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ASTDA");
					String sDuopbM    = dsRtnMatnr.getColumnAsString(iMatnr,  "DUOPB");
                    // 신규추가  끝	
					String sAinovM    = dsRtnMatnr.getColumnAsString(iMatnr,  "AINOV");
					String sAstdM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ASTD");
					String sDeldM    = dsRtnMatnr.getColumnAsString(iMatnr,  "DELD");
					String sDsv2M    = dsRtnMatnr.getColumnAsString(iMatnr,  "DSV2");
					String sDhsbtM    = dsRtnMatnr.getColumnAsString(iMatnr,  "DHSBT");
					
					int iQntyM      = dsRtnMatnr.getColumnAsDouble(iMatnr,  "QNTY").intValue();

					// ITEM비교
					if (sItem_d != null && sItem_d.equals(sItemM))		{

						// 자재(도면)번호 비교 : 동일하면 확인
						if (sMatnr_d != null && sMatnr_d.equals(sMatnrM))	{

							// SPEC비교 (해당 값이 존재하는 경우)
							if (sSpec12_d == null || sSpec12_d.equals(""))	{
								bSpec12 = true;
							}else if (sSpec12_d != null && sSpec12M != null && !"".equals(sSpec12M) && sSpec12_d.indexOf(sSpec12M) >= 0)	{
								bSpec12 = true;
							}
							
							
							// 속도 비교
							if (iSpec3fr_d <= iSpec3M && iSpec3M <= iSpec3to_d )	{
								bSpec3 = true;
							}

							// 인승 비교
							if (iSpec5fr_d <= iSpec5M && iSpec5M <= iSpec5to_d )	{
								bSpec5 = true;
							}

							// CAR SAFETY 비교 (해당 값이 존재하는 경우)
							if (sEcsf_d == null || sEcsf_d.equals(""))	{
								bEcsf = true;
							}else if (sEcsfM != null && sEcsfM.indexOf(sEcsf_d) >= 0)	{
								bEcsf = true;
							}

							// TM 비교 (해당 값이 존재하는 경우)
						/*	if (sEtm_d == null || sEtm_d.equals(""))	{
								bEtm = true;
							}else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
								bEtm = true;
							}
						*/	
							if (sEtm_d == null || sEtm_d.equals(""))	{
								bEtm = true;
							}else if (sEtm_d != null && sEtmM != null && !"".equals(sEtmM) && sEtm_d.indexOf(sEtmM) >= 0)	{
								bEtm = true;
							}
						    else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
							bEtm = true;
						    }
							

							// BLOK NO 비교 (해당 값이 존재하는 경우)
							if (sMatkl_d == null || sMatkl_d.equals(""))	{
								bMatkl = true;
							}else if (sMatklM != null && sMatklM.indexOf(sMatkl_d) >= 0)	{
								bMatkl = true;
							}
							//2016.01 추가
							// 용도 비교 
					       if (sAuse_d == null || sAuse_d.equals(""))	{
								bAuse = true;
							}else if (sAuseM != null && sAuseM.indexOf(sAuse_d) >= 0)	{
								bAuse  = true;
							}
							// 용량 비교 
						   if (sAcapa_d == null || sAcapa_d.equals(""))	{
								bAcapa = true;
							}else if (sAcapaM != null && sAcapaM.indexOf(sAcapa_d) >= 0)	{
								bAcapa  = true;
							}
							// 열림 비교 
						    if (sAopen_d == null || sAopen_d.equals(""))	{
								bAopen = true;
							}else if (sAopenM != null && sAopenM.indexOf(sAopen_d) >= 0)	{
								bAopen  = true;
							}
							// 층수 비교 
							if (iAfqfr_d <= iAfqM && iAfqM <= iAfqto_d )	{
								bAfq = true;
							}
						//System.out.println(" iAfqfr_d : " +iAfqfr_d
						//		           +  " iAfqM : " + iAfqM  +   " iAfqto_d : " + iAfqto_d );
				              						
					    	// 정지 층수 비교
						   if (iAstqfr_d <= iAstqM && iAstqM <= iAstqto_d )	{
								bAstq = true;
							}
							// 운행방식 비교 
			   		       if (sAdrv_d == null || sAdrv_d.equals(""))	{
								bAdrv = true;
							}else if (sAdrvM != null && sAdrvM.indexOf(sAdrv_d) >= 0)	{
								bAdrv  = true;
							}
							// 시방서 비교 
			   			    if (sAspc_d == null || sAspc_d.equals(""))	{
								bAspc = true;
							}else if (sAspcM != null && sAspcM.indexOf(sAspc_d) >= 0)	{
								bAspc  = true;
							}
							// 국내/해외 구분 비교 
			   			    if (sAcd1_d == null || sAcd1_d.equals(""))	{
								bAcd1 = true;
							}else if (sAcd1M != null && sAcd1M.indexOf(sAcd1_d) >= 0)	{
								bAcd1  = true;
							}
							//  적용코드  비교 
			   			    if (sAcd2_d == null || sAcd2_d.equals(""))	{
								bAcd2 = true;
							}else if (sAcd2M != null && sAcd2M.indexOf(sAcd2_d) >= 0)	{
								bAcd2  = true;
							}
							// FLOOR 두께  비교
							if (iBfthfr_d <= iBfthM && iBfthM <= iBfthto_d )	{
								bBfth = true;
							}
							//  CAR WALL  비교 
			   			   if (sBwm_d == null || sBwm_d.equals(""))	{
								bBwm = true;
							}else if (sBwmM != null && sBwmM.indexOf(sBwm_d) >= 0)	{
								bBwm  = true;
							}
							//  main opb  비교 
			   			    if (sBmopb_d == null || sBmopb_d.equals(""))	{
								bBmopb = true;
							}else if (sBmopbM != null && sBmopbM.indexOf(sBmopb_d) >= 0)	{
								bBmopb  = true;
							}
							//    CAR DOOR 비교 
			   			   if (sBcdm_d == null || sBcdm_d.equals(""))	{
								bBcdm = true;
							}else if (sBcdmM != null && sBcdmM.indexOf(sBcdm_d) >= 0)	{
								bBcdm  = true;
							}
							// handrail  비교 
			   			    if (sBhr_d == null || sBhr_d.equals(""))	{
								bBhr = true;
							}else if (sBhrM != null && sBhrM.indexOf(sBhr_d) >= 0)	{
								bBhr  = true;
							}
							// jamb  비교 
			   			    if (sCjm1_d == null || sCjm1_d.equals(""))	{
								bCjm1 = true;
							}else if (sCjm1M != null && sCjm1M.indexOf(sCjm1_d) >= 0)	{
								bCjm1  = true;
							}
							// jmb 수량  비교
							if (iCjm1qfr_d <= iCjm1qM && iCjm1qM <= iCjm1qto_d )	{
								bCjm1q = true;
							}
							// jamb 재질 비교 
			   			    if (sCjm1m_d == null || sCjm1m_d.equals(""))	{
								bCjm1m = true;
							}else if (sCjm1mM != null && sCjm1mM.indexOf(sCjm1m_d) >= 0)	{
								bCjm1m  = true;
							}
							// jamb 방화도어 비교 
			   			    if (sCjm1fr_d == null || sCjm1fr_d.equals(""))	{
								bCjm1fr = true;
							}else if (sCjm1frM != null && sCjm1frM.indexOf(sCjm1fr_d) >= 0)	{
								bCjm1fr  = true;
							}
							// HATCH DOOR  비교 
			   			    if (sChd1_d == null || sChd1_d.equals(""))	{
								bChd1 = true;
							}else if (sChd1M != null && sChd1M.indexOf(sChd1_d) >= 0)	{
								bChd1  = true;
							}
							// HATCH DOOR 수량  비교
							if (iChd1qfr_d <= iChd1qM && iChd1qM <= iChd1qto_d )	{
								bChd1q = true;
							}
							// HATCH SILL  비교 
			   			   if (sChs1_d == null || sChs1_d.equals(""))	{
								bChs1 = true;
							}else if (sChs1M != null && sChs1M.indexOf(sChs1_d) >= 0)	{
								bChs1  = true;
							}
							// HATCH SILL 수량  비교
							if (iChs1qfr_d <= iChs1qM && iChs1qM <= iChs1qto_d )	{
								bChs1q = true;
							}
							// 비상통화장치  비교 
			   			    if (sDeph_d == null || sDeph_d.equals(""))	{
								bDeph = true;
							}else if (sDephM != null && sDephM.indexOf(sDeph_d) >= 0)	{
								bDeph  = true;
							}
							// 비상통화장치 적용 대수  비교
							if (iDephtqfr_d <= iDephtqM && iDephtqM <= iDephtqto_d )	{
								bDephtq = true;
							}
							// 승강로 overhead  비교
							if (iEhofr_d <= iEhoM && iEhoM <= iEhoto_d )	{
								bEho = true;
							}
							// 승강로 travel heigth   비교
							if (iEhtrhfr_d <= iEhtrhM && iEhtrhM <= iEhtrhto_d )	{
								bEhtrh = true;
							}
							//System.out.println(" iEhtrhfr_d : " +iEhtrhfr_d
								//	           +  " iEhtrhM : " + iEhtrhM  +   " iEhtrhto_d : " + iEhtrhto_d );
							// 승강로 pit   비교
							if (iEhpfr_d <= iEhpM && iEhpM <= iEhpto_d )	{
								bEhp = true;
							}
							// 승강로  가로   비교
							if (iEhhfr_d <= iEhhM && iEhhM <= iEhhto_d )	{
								bEhh = true;
							}
							// 승강로 세로   비교
								if (iEhvfr_d <= iEhvM && iEhvM <= iEhvto_d )	{
								bEhv = true;
							}
							// 승강로 재질   비교
							if (sEhm_d == null || sEhm_d.equals(""))	{
								bEhm = true;
							}else if (sEhmM != null && sEhmM.indexOf(sEhm_d) >= 0)	{
								bEhm  = true;
							}
							// car ca    비교
							if (iEccafr_d <= iEccaM && iEccaM <= iEccato_d )	{
								bEcca = true;
							}
							// car cb    비교
							if (iEccbfr_d <= iEccbM && iEccbM <= iEccbto_d )	{
								bEccb = true;
							}
							// car ch    비교
							if (iEcchfr_d <= iEcchM && iEcchM <= iEcchto_d )	{
								bEcch = true;
							}
							// door jj    비교
							if (iEcjjfr_d <= iEcjjM && iEcjjM <= iEcjjto_d )	{
								bEcjj = true;
							}
							// door hh   비교
							if (iEchhfr_d <= iEchhM && iEchhM <= iEchhto_d )	{
								bEchh = true;
							}
							// car aa  비교
							if (iEcaafr_d <= iEcaaM && iEcaaM <= iEcaato_d )	{
								bEcaa = true;
							}
							// car bb  비교
							if (iEcbbfr_d <= iEcbbM && iEcbbM <= iEcbbto_d )	{
								bEcbb = true;
							}
							// car ee  비교
							if (iEceefr_d <= iEceeM && iEceeM <= iEceeto_d )	{
								bEcee = true;
							}
							// motor 용량  비교 
			   			    if (sEtmm_d == null || sEtmm_d.equals(""))	{
								bEtmm = true;
							}else if (sEtmmM != null && sEtmmM.indexOf(sEtmm_d) >= 0)	{
								bEtmm  = true;
							}					
			   			    // rope 직경  비교 
			   			    if (sErpd_d == null || sErpd_d.equals(""))	{
								bErpd = true;
							}else if (sErpdM != null && sErpdM.indexOf(sErpd_d) >= 0)	{
								bErpd  = true;
							}
			   			    // rope 본수  비교 
			   			    if (sErpw_d == null || sErpw_d.equals(""))	{
								bErpw = true;
							}else if (sErpwM != null && sErpwM.indexOf(sErpw_d) >= 0)	{
								bErpw  = true;
							}
			   			    // rope roping 비교 
			   			    if (sErpr_d == null || sErpr_d.equals(""))	{
								bErpr = true;
							}else if (sErprM != null && sErprM.indexOf(sErpr_d) >= 0)	{
								bErpr  = true;
							}
			   			    // car rail(k) 비교 
			   			    if (sEcrl_d == null || sEcrl_d.equals(""))	{
								bEcrl = true;
							}else if (sEcrlM != null && sEcrlM.indexOf(sEcrl_d) >= 0)	{
								bEcrl  = true;
							}
			   			    // car governor 비교 
			   			    if (sEcgv_d == null || sEcgv_d.equals(""))	{
								bEcgv = true;
							}else if (sEcgvM != null && sEcgvM.indexOf(sEcgv_d) >= 0)	{
								bEcgv  = true;
							}
			   			    // cwt rail (k) 비교 
			   			    if (sEcwrl_d == null || sEcwrl_d.equals(""))	{
								bEcwrl = true;
							}else if (sEcwrlM != null && sEcwrlM.indexOf(sEcwrl_d) >= 0)	{
								bEcwrl  = true;
							}	   			    
			   			    // cwt weight  비교 
			   			    if (sEcww_d == null || sEcww_d.equals(""))	{
								bEcww = true;
							}else if (sEcwwM != null && sEcwwM.indexOf(sEcww_d) >= 0)	{
								bEcww  = true;
							}	   	
			   			    // cwt safety  비교 
			   			    if (sEcwsf_d == null || sEcwsf_d.equals(""))	{
								bEcwsf = true;
							}else if (sEcwsfM != null && sEcwsfM.indexOf(sEcwsf_d) >= 0)	{
								bEcwsf  = true;
							}	  
			   			    // 감시반  비교 
			   			    if (sDsv1_d == null || sDsv1_d.equals(""))	{
								bDsv1 = true;
							}else if (sDsv1M != null && sDsv1M.indexOf(sDsv1_d) >= 0)	{
								bDsv1  = true;
							}	  
			   			    // CCTV카메라  비교 
			   			    if (sDcctv_d == null || sDcctv_d.equals(""))	{
								bDcctv = true;
							}else if (sDcctvM != null && sDcctvM.indexOf(sDcctv_d) >= 0)	{
								bDcctv  = true;
							}	  
			   			    // CAR 에어컨   비교 
			   			    if (sDcair_d == null || sDcair_d.equals(""))	{
								bDcair = true;
							}else if (sDcairM != null && sDcairM.indexOf(sDcair_d) >= 0)	{
								bDcair  = true;
							}
			   			   //건축허가일
			   			    if (sAstda_d == null || sAstda_d.equals(""))	{
								bAstda = true;
							}else if (sAstdaM != null && sAstdaM.indexOf(sAstda_d) >= 0)	{
								bAstda  = true;
							}
				   			 //OPB 상부 인출여부 
			   			    if (sDuopb_d == null || sDuopb_d.equals(""))	{
								bDuopb = true;
							}else if (sDuopbM != null && sDuopbM.indexOf(sDuopb_d) >= 0)	{
								bDuopb  = true;
							}
				   			//이노버 적용여부
			   			    if (sAinov_d == null || sAinov_d.equals(""))	{
								bAinov = true;
							}else if (sAinovM != null && sAinovM.indexOf(sAinov_d) >= 0)	{
								bAinov  = true;
							}
				   		 //2013년 9월 15일 이후 건축허가
			   			    if (sAstd_d == null || sAstd_d.equals(""))	{
								bAstd = true;
							}else if (sAstdM != null && sAstdM.indexOf(sAstd_d) >= 0)	{
								bAstd  = true;
							}
				   		 //ELD 운전
			   			    if (sDeld_d == null || sDeld_d.equals(""))	{
			   			    	bDeld = true;
							}else if (sDeldM != null && sDeldM.indexOf(sDeld_d) >= 0)	{
								bDeld  = true;
							}
				   		//감시반2
			   			    if (sDsv2_d == null || sDsv2_d.equals(""))	{
								bDsv2 = true;
							}else if (sDsv2M != null && sDsv2M.indexOf(sDsv2_d) >= 0)	{
								bDsv2  = true;
							}
				   	   //세내대버튼
			   			    if (sDhsbt_d == null || sDhsbt_d.equals(""))	{
								bDhsbt = true;
							}else if (sDhsbtM != null && sDhsbtM.indexOf(sDhsbt_d) >= 0)	{
								bDhsbt  = true;
							}
                            /*================================적용완료 2016.01 ======================================*/
			   			    
							// 모든조건을 만족 (자재건수 누적)
			   			// 모든조건을 만족 (자재건수 누적)
							if (bSpec12 && bSpec3 && bSpec5 && bEcsf && bEtm && bMatkl && bAuse && bAcapa && bAopen && bAfq && bAstq && bAdrv && bAspc && bAcd1
									&& bAcd2 && bBfth && bBwm  && bBmopb && bBcdm && bBhr && bCjm1 && bCjm1q && bCjm1m && bCjm1fr && bChd1 && bChd1q && bChs1 && bChs1q
									&& bDeph && bDephtq && bEho && bEhtrh && bEhtrh && bEhp && bEhh && bEhv && bEhm && bEcca && bEccb && bEcch && bEcaa && bEcbb && bEcee  
									&& bEtmm && bErpd && bErpw && bErpr && bEcrl && bEcgv && bEcwrl && bEcww && bEcwsf && bDsv1 && bDcctv && bDcair && bEcjj && bEchh && bAstda && bDuopb
									&& bAinov && bAstd && bDeld && bDsv2 && bDhsbt)	{								
								   iQntySum += iQntyM;

								// 집계 처리용도
								bSumCheck = true;
							}

						}else	{	// 자재(도면) 번호가 상이하면
							// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
							// 집계 처리가 되도록 요청에 의하여 bSumCheck가 false여도 처리

							//if (bSumCheck)	{
								// 임시Dataset에 추가
								int iRow = dsTempSum.appendRow();
								
								dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
								dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
								dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
								dsTempSum.setColumn(iRow, "STYM",  sStym_d);
								dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
								dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
								dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
	
								bSumCheck = false;
							//}

							break;
						}
					}else	{	// ITEM이 상이하면 실적정보가 존재하지 않음 SKIP
						// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
						// 집계 처리가 되도록 요청에 의하여 bSumCheck가 false여도 처리

						//if (bSumCheck)		{
							// 임시Dataset에 추가
							int iRow = dsTempSum.appendRow();
							
							dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
							dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
							dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
							dsTempSum.setColumn(iRow, "STYM",  sStym_d);
							dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
							dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
							dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
							
							bSumCheck = false;
						//}

						break;
					}

					//자료의 위치
					iPos++;
				}
				// 최종집계 내역
				if (iPos == dsRtnMatnr.getRowCount())	{
					// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
					// 집계 처리가 되도록 요청에 의하여 bSumCheck가 false여도 처리

					//if (bSumCheck)		{
						// 임시Dataset에 추가
						int iRow = dsTempSum.appendRow();
						
						dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
						dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
						dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
						dsTempSum.setColumn(iRow, "STYM",  sStym_d);
						dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
						dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
						dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
						
						bSumCheck = false;
					//}
					// 실적 건수 최종으로 처리 불필요
					break;
				}

				//
			}

			
			// log출력
			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{
				System.out.println(" ITEM : " + dsTempSum.getColumnAsString(iMatnr, "ITEM") + "     " +
						           " MATNR : " + dsTempSum.getColumnAsString(iMatnr, "MATNR") + "     " +
						           " GUBUN : " + dsTempSum.getColumnAsString(iMatnr, "GUBUN") + "     " +
						           " STYM : " + dsTempSum.getColumnAsString(iMatnr, "STYM") + "     " +
						           " QNTY : " + dsTempSum.getColumnAsString(iMatnr, "QNTY") + "     " +
						           " MATNR_SVAMT : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT") + "     " +
						           " MATNR_WAERK : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK") + "....."
						);
			}

			//TransactionManager.rollback();

			
			
//			/* 최종 조회 결과 확인 후 처리
			// 산출된 Item 정보
			HashMap hashMap = new HashMap();

			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{

				String sStym = dsTempSum.getColumnAsString(iMatnr, "STYM");
				int iQnty    = dsTempSum.getColumnAsDouble(iMatnr, "QNTY").intValue();

				// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
				// 집계 처리가 되도록 요청에 의하여 comment

				// 0의 경우 실적 제외 (단 최초 실적월이 존재하지 않아야 함)
				//if (iQnty == 0 && (sStym == null || "".equals(sStym)))	{
				//	continue;
				//}


				// 실적 처리
				if (hashMap.get(dsTempSum.getColumnAsString(iMatnr, "ITEM")) == null)	{

					String sRsltym = "";
					if (sStym == null || "".equals(sStym))	{
						sRsltym = ds_cond.getColumnAsString(i, "RSLTYM");
					}

					hashMap.put(dsTempSum.getColumnAsString(iMatnr, "ITEM"), sRsltym);		// 최초 실적년월이 존재하는 경우 ""
				}

				// 둥록된 자재/도면 번호의 실적 처리 
				zqmt075u.addParamObject("ITEM",  dsTempSum.getColumnAsString(iMatnr, "ITEM"));	// 항목
				zqmt075u.addParamObject("GUBUN", dsTempSum.getColumnAsString(iMatnr, "GUBUN"));	// 자재/도면번호 구분
				zqmt075u.addParamObject("MATNR", dsTempSum.getColumnAsString(iMatnr, "MATNR"));	// 자재/도면번호
				zqmt075u.addParamObject("SVAMT", dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT"));	// 절감액
				zqmt075u.addParamObject("WAERK", dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK"));	// 화폐딴위
				zqmt075u.addParamObject("QNTY",  iQnty + "");	// 수량
	
				// 자재/도면 집계 정보 등록(ZQMT075)
				zqmt075u.setRowIndex(i);
				executor.execute(zqmt075u);
			}

			// 항목별 집계 처리
			Collection collection = hashMap.keySet();
			Iterator   iter       = collection.iterator();

			while(iter.hasNext())	{
				String sKey   = ((String) iter.next());
				String sValue = ((String) hashMap.get(sKey));	// ""이 아니면 실적년월 등록 필요

				// 산출된 기 항목별 실적집계전 해당 항목 삭제 (ZQMT076)
				zqmt076dd.addParamObject("ITEM", sKey);

				zqmt076dd.setRowIndex(i);
				executor.execute(zqmt076dd);

				// 항목별 집계 정보 등록 (ZQMT076) :  자재/도면번호별로 집계된 정보 집계
				zqmt076i.addParamObject("ITEM", sKey);	// 항목

				zqmt076i.setRowIndex(i);
				executor.execute(zqmt076i);

				// 최최 실적일자가 없는 경우에만 반영
				if (sValue != null && !"".equals(sValue))	{
					// 최초 실적등록일 정보 Update
					zqmt071u.addParamObject("ITEM", sKey);	// 항목
					zqmt071u.addParamObject("STYM", ds_cond.getColumnAsString(i, "RSLTYM"));	// 최초실적일자

					zqmt071u.setRowIndex(i);
					executor.execute(zqmt071u);
				}
			}

			// 집계 처리 이력 등록(ZQMT074)
			zqmt074u.setRowIndex(i);
			executor.execute(zqmt074u);
//			*/
		}
	}
	// 자재/도면 실적 집계 처리 취소
	// 2013.08.07
	public void cancelProcess(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 최초 실적월 초기상태로 변경 (ZQMT071)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_U3");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt071u.addParamObject("SGBN",      "S");	// 시스템 산출건만 추출

		// 자재/도면번호 기 실적집계 삭제 (ZQMT075)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 산출된 기 항목별 실적집계 삭제 (ZQMT076) 시스템에 의하여 기 자동 산출건 삭제
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt076d.addParamObject("SGBN",    "S");	// 시스템 산출

		// 집계처리 이력 삭제 (ZQMT074)
		DatasetSqlRequest zqmt074d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702001A_D4");

		zqmt074d.addParamObject("ds_list", ds_cond);
		zqmt074d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 실적집계대상 항목의 자재/도면번호 대상 조회
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{

			// 실적년월 초기화
			zqmt071u.setRowIndex(i);
			executor.execute(zqmt071u);

			// 기 처리된 실적집계 정보 삭제 처리
			// 자재/도면번호 기 실적집계 삭제 (ZQMT075)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);

			// 산출된 기 항목별 실적집계 삭제 (ZQMT076)
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);
			
			// 실적년월 이력 삭제
			zqmt074d.setRowIndex(i);
			executor.execute(zqmt074d);
		}
	}
	
	


    /******************************원가상승******************************/
	public void save2(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 원가상승항목등록 (ZQMT071U) 내역의 항목번호 조회
		DatasetSqlRequest zqmt071s
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_S3");	// ITEM seq생성

		zqmt071s.addParamObject("ds_cond", ds_list);
		zqmt071s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 원가상승항목등록 (ZQMT071U) 내역 등록
		DatasetSqlRequest zqmt071i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_I1");

		zqmt071i.addParamObject("ds_list",   ds_list);
		zqmt071i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt071i.addParamObject("S_CONFDT",  ctx.getInputVariable("pConfDt"));		// 확정일자

		// 원가상승항목등록 (ZQMT071U) 내역 수정
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_U1");

		zqmt071u.addParamObject("ds_list",   ds_list);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 원가상승항목등록 (ZQMT071U) 내역 삭제
		DatasetSqlRequest zqmt071d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_D1");

		zqmt071d.addParamObject("ds_list", ds_list);
		zqmt071d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 원가상승항목계획금액등록 (ZQMT072U)
		DatasetSqlRequest zqmt072i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_I2");

		zqmt072i.addParamObject("ds_list",   ds_list);
		zqmt072i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt072i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 원가상승항목계획금액삭제 (ZQMT072U)
		DatasetSqlRequest zqmt072d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_D2");

		zqmt072d.addParamObject("ds_list", ds_list);
		zqmt072d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// (UP)도면/자재 번호 등록 (ZQMT073U)
		DatasetSqlRequest zqmt073i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_I3");

		zqmt073i.addParamObject("ds_list",   ds_list);
		zqmt073i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt073i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// (UP)도면/자재 번호 삭제 (ZQMT073U)
		DatasetSqlRequest zqmt073d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0706001A_D3");

		zqmt073d.addParamObject("ds_list", ds_list);
		zqmt073d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		for(int i = 0; i < ds_list.getRowCount(); i++) {
			if("I".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{

				// 이뤙항목의 입력 처리 ZQMT071U
				if(ds_list.getColumnAsString(i, "ITEM") != null && !"".equals(ds_list.getColumnAsString(i, "ITEM")))	{
					zqmt071i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));		// 항목번호
					zqmt072i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));		// 항목번호
				}else	{
					zqmt071s.setRowIndex(i);
					Dataset dsRtn071 = (Dataset)executor.query(zqmt071s).getResultObject();
					
					if(dsRtn071.getRowCount() > 0 ) {
						zqmt071i.addParamObject("S_ITEM",  dsRtn071.getColumnAsString(0, "ITEM"));		// 항목번호
						zqmt072i.addParamObject("S_ITEM",  dsRtn071.getColumnAsString(0, "ITEM"));		// 항목번호
					} else {
						throw new BizException(String.valueOf(i + 1) + " 번째 항목 생성시 오루가 발생하였습니다. 처리를 중지 합니다.");
					}
				}

				// 원가상승항목등록 (ZQMT071U)
				zqmt071i.setRowIndex(i);
				executor.execute(zqmt071i);

				// 원가상승계획금액 등록
				try {
					zqmt072Insert2(executor, zqmt072i, ds_list, i);
				}catch (Exception e)	{
					throw e;
				}

				// (UP)도면/자재 정보 처리
				if (ds_list.getColumnAsString(i, "ITEM") != null && !"".equals(ds_list.getColumnAsString(i, "ITEM")))	{
					// 도면/자재 번호등록 (ZQMT073U)
					zqmt073i.setRowIndex(i);
					executor.execute(zqmt073i);
				}
			}else if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{
				// 원가상승항목수정 (ZQMT071U)
				zqmt071u.setRowIndex(i);
				executor.execute(zqmt071u);
				
				// 원가상승계획금액 삭제
				zqmt072d.setRowIndex(i);
				executor.execute(zqmt072d);

				// 원가상승계획금액 등록
				try {
					zqmt072i.addParamObject("S_ITEM",  ds_list.getColumnAsString(i, "ITEM"));	// 항목번호
					zqmt072Insert2(executor, zqmt072i, ds_list, i);
				}catch (Exception e)	{
					throw e;
				}
			}else if("D".equals(ds_list.getColumnAsString(i, "FLAG"))) 	{
				// 원가상승계획금액 삭제
				zqmt072d.setRowIndex(i);
				executor.execute(zqmt072d);

				// 원가상승항목삭제 (ZQMT071U)
				zqmt071d.setRowIndex(i);
				executor.execute(zqmt071d);
				
				// (UP)도면/자재 정보 삭제
				zqmt073d.setRowIndex(i);
				executor.execute(zqmt073d);
			}
		}
	}

	// 원가상승계획금액 등록 (ZQMT072)
	public void zqmt072Insert2(DatasetSqlExecutor executor, DatasetSqlRequest zqmt072i, Dataset ds_list, int index) throws Exception	{
		String sMonth  = "";
		String sPlanMM = "";
		for (int j = 1; j <= 12; j++)	{
			sPlanMM = String.format("%02d", j);
			sMonth = "M" + sPlanMM;
			if (ds_list.getColumnAsString(index, sMonth) != null)	{
				zqmt072i.addParamObject("S_PLANYM",  ds_list.getColumnAsString(index, "GJAHR") + sPlanMM); // 계획년월
				zqmt072i.addParamObject("S_PLANAMT", ds_list.getColumnAsString(index, sMonth)); // 계획금액
				
				zqmt072i.setRowIndex(index);
				executor.execute(zqmt072i);
			}
		}		
	}

	// (UP)자재/도면 실적 집계 처리
	public void matnrProc2(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// (UP)자재/도면번호 기 실적집계 삭제 (ZQMT075U)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// (UP)산출된 기 항목별 실적집계 삭제 (ZQMT076U) 시스템에 의하여 기 자동 산출건 삭제
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// (UP)자재(gubun=1) 도면(gubun=2) 번호로 수량 추출
		DatasetSqlRequest matnrs
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_S5");

		matnrs.addParamObject("ds_cond", ds_cond);
		matnrs.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// (UP)자재/도면번호 실적집계 (ZQMT075U)
		DatasetSqlRequest zqmt075u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_U1");

		zqmt075u.addParamObject("ds_list",   ds_cond);
		zqmt075u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt075u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// (UP)산출된 기 항목별 실적집계 삭제 (ZQMT076U) 수기 입력건중 시스템에 의하여 산출된건 삭제
		DatasetSqlRequest zqmt076dd
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D3");

		zqmt076dd.addParamObject("ds_list", ds_cond);
		zqmt076dd.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// (UP)항목별 실적집계 (ZQMT076U)
		DatasetSqlRequest zqmt076i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_I1");

		zqmt076i.addParamObject("ds_list",   ds_cond);
		zqmt076i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt076i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt076i.addParamObject("SGBN",      "S");	// M : 수기,    S : 시스템
		zqmt076i.addParamObject("BIGO",      "");
		zqmt076i.addParamObject("KGBN",      "T");	// T : 총액기준, U : 단가기준

		// (UP)최초 실적월 수정 (ZQMT071U)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707002A_U1");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// (UP)집계처리 이력 (ZQMT074U)
		DatasetSqlRequest zqmt074u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_U2");

		zqmt074u.addParamObject("ds_list", ds_cond);
		zqmt074u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt074u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// (UP)실적집계대상 항목의 자재/도면번호 대상 조회
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{
			// 기 처리된 실적집계 정보 삭제 처리
			// 자재/도면번호 기 실적집계 삭제 (ZQMT075U)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);

			// 산출된 기 항목별 실적집계 삭제 (ZQMT076U)
			zqmt076d.addParamObject("SGBN", "S");	// 시스템 산출
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);

			// 자재/도면  집계 대상 조회
			matnrs.setRowIndex(i);
			Dataset dsRtnMatnr = (Dataset)executor.query(matnrs).getResultObject();

			// 산출된 Item 정보
			HashMap hashMap = new HashMap();

			for (int iMatnr = 0; iMatnr < dsRtnMatnr.getRowCount(); iMatnr++)	{

				String sStym = dsRtnMatnr.getColumnAsString(iMatnr, "STYM");
				int iQnty    = dsRtnMatnr.getColumnAsDouble(iMatnr, "QNTY").intValue();

				// 0의 경우 실적 제외 (단 최초 실적월이 존재하지 않아야 함)
				if (iQnty == 0 && (sStym == null || "".equals(sStym)))	{
					continue;
				}

				// 실적 처리
				if (hashMap.get(dsRtnMatnr.getColumnAsString(iMatnr, "ITEM")) == null)	{

					String sRsltym = "";
					if (sStym == null || "".equals(sStym))	{
						sRsltym = ds_cond.getColumnAsString(i, "RSLTYM");
					}

					hashMap.put(dsRtnMatnr.getColumnAsString(iMatnr, "ITEM"), sRsltym);		// 최초 실적년월이 존재하는 경우 ""
				}

				// 둥록된 자재/도면 번호의 실적 처리 
				zqmt075u.addParamObject("ITEM",  dsRtnMatnr.getColumnAsString(iMatnr, "ITEM"));	// 항목
				zqmt075u.addParamObject("GUBUN", dsRtnMatnr.getColumnAsString(iMatnr, "GUBUN"));	// 자재/도면번호 구분
				zqmt075u.addParamObject("MATNR", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR"));	// 자재/도면번호
				zqmt075u.addParamObject("SVAMT", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR_SVAMT"));	// 절감액
				zqmt075u.addParamObject("WAERK", dsRtnMatnr.getColumnAsString(iMatnr, "MATNR_WAERK"));	// 화폐딴위
				zqmt075u.addParamObject("QNTY",  iQnty + "");	// 수량
	
				// 자재/도면 집계 정보 등록(ZQMT075U)
				zqmt075u.setRowIndex(i);
				executor.execute(zqmt075u);
			}

			// 항목별 집계 처리
			Collection collection = hashMap.keySet();
			Iterator   iter       = collection.iterator();

			while(iter.hasNext())	{
				String sKey   = ((String) iter.next());
				String sValue = ((String) hashMap.get(sKey));	// ""이 아니면 실적년월 등록 필요

				// 산출된 기 항목별 실적집계전 해당 항목 삭제 (ZQMT076)
				zqmt076dd.addParamObject("ITEM", sKey);

				zqmt076dd.setRowIndex(i);
				executor.execute(zqmt076dd);

				// 항목별 집계 정보 등록 (ZQMT076U) :  자재/도면번호별로 집계된 정보 집계
				zqmt076i.addParamObject("ITEM", sKey);	// 항목

				zqmt076i.setRowIndex(i);
				executor.execute(zqmt076i);

				// 최최 실적일자가 없는 경우에만 반영
				if (sValue != null && !"".equals(sValue))	{
					// 최초 실적등록일 정보 Update
					zqmt071u.addParamObject("ITEM", sKey);	// 항목
					zqmt071u.addParamObject("STYM", ds_cond.getColumnAsString(i, "RSLTYM"));	// 최초실적일자

					zqmt071u.setRowIndex(i);
					executor.execute(zqmt071u);
				}
			}

			// 집계 처리 이력 등록(ZQMT074)
			zqmt074u.setRowIndex(i);
			executor.execute(zqmt074u);
		}
	}

	// (UP)자재/도면 실적 집계 처리 (세부조건 추가)
	// 2013.07.17
	public void matnrProcCondAdd2(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 자재/도면번호 기 실적집계 삭제 (ZQMT075U)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 산출된 기 항목별 실적집계 삭제 (ZQMT076U) 시스템에 의하여 기 자동 산출건 삭제
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재(gubun=1) 도면(gubun=2) 번호로 수량 추출 (상세조건 포함)
		DatasetSqlRequest matnrs
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_S6");

		matnrs.addParamObject("ds_cond", ds_cond);
		matnrs.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재(gubun=1) 도면(gubun=2) 상세조건 조회
		DatasetSqlRequest matnrsDetail
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_S7");

		matnrsDetail.addParamObject("ds_cond", ds_cond);
		matnrsDetail.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 자재/도면번호 실적집계 (ZQMT075U)
		DatasetSqlRequest zqmt075u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_U1");

		zqmt075u.addParamObject("ds_list",   ds_cond);
		zqmt075u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt075u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 산출된 기 항목별 실적집계 삭제 (ZQMT076U) 입력건중 시스템에 의하여 산출된건 삭제
		DatasetSqlRequest zqmt076dd
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D3");

		zqmt076dd.addParamObject("ds_list", ds_cond);
		zqmt076dd.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 항목별 실적집계 (ZQMT076U)
		DatasetSqlRequest zqmt076i
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_I1");

		zqmt076i.addParamObject("ds_list",   ds_cond);
		zqmt076i.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt076i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt076i.addParamObject("SGBN",      "S");	// M : 수기,    S : 시스템
		zqmt076i.addParamObject("BIGO",      "");
		zqmt076i.addParamObject("KGBN",      "T");	// T : 총액기준, U : 단가기준

		// 최초 실적월 수정 (ZQMT071U)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707002A_U1");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 집계처리 이력 (ZQMT074U)
		DatasetSqlRequest zqmt074u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_U2");

		zqmt074u.addParamObject("ds_list", ds_cond);
		zqmt074u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt074u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 실적집계대상 항목의 자재/도면번호 대상 조회
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{

			// 기 처리된 실적집계 정보 삭제 처리
			// 자재/도면번호 기 실적집계 삭제 (ZQMT075U)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);

			// 산출된 기 항목별 실적집계 삭제 (ZQMT076U)
			zqmt076d.addParamObject("SGBN", "S");	// 시스템 산출
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);

			// 자재/도면  집계 대상 조회
			matnrs.setRowIndex(i);
			Dataset dsRtnMatnr = (Dataset)executor.query(matnrs).getResultObject();

			// 자재/도면 상세조회 대상 조회 2013.07.17
			matnrsDetail.setRowIndex(i);
			Dataset dsRtnMatnrDetail = (Dataset)executor.query(matnrsDetail).getResultObject();

			// 상세조건에 따른 조건 확인 및 집계 처리
			Dataset dsTempSum = new Dataset();

			// 컬럼 setting
			dsTempSum.addColumn("ITEM",        (short)1, 256);
			dsTempSum.addColumn("STYM",        (short)1, 256);
			dsTempSum.addColumn("GUBUN",       (short)1, 256);
			dsTempSum.addColumn("MATNR_SVAMT", (short)1, 256);
			dsTempSum.addColumn("MATNR_WAERK", (short)1, 256);
			dsTempSum.addColumn("MATNR",       (short)1, 256);
			dsTempSum.addColumn("QNTY",        (short)1, 256);

			// 자재(도면) 집계 
			int iPos = 0;

			// 세부조건 확인
			for(int iDetail = 0; iDetail < dsRtnMatnrDetail.getRowCount(); iDetail++)		{

				boolean bSumCheck = false;

				String sItem_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ITEM");
				String sMatnr_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR");
				String sSpec12_d = dsRtnMatnrDetail.getColumnAsString(iDetail,  "SPEC12");
				int iSpec3fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3FR");
				int iSpec3to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC3TO");
				int iSpec5fr_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5FR");
				int iSpec5to_d   = dsRtnMatnrDetail.getColumnAsInteger(iDetail, "SPEC5TO");
				String sEcsf_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ECSF");
				String sEtm_d    = dsRtnMatnrDetail.getColumnAsString(iDetail,  "ETM");
				String sMatkl_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATKL");
				String sSvamt_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_SVAMT");
				String sWaerk_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "MATNR_WAERK");
				String sGubun_d  = dsRtnMatnrDetail.getColumnAsString(iDetail,  "GUBUN");
				String sStym_d   = dsRtnMatnrDetail.getColumnAsString(iDetail,  "STYM");

				int iQntySum    = 0;	//자재(도면) 누적건수

				// 자재(도면) 정보 추철건
				for (int iMatnr = iPos; iMatnr < dsRtnMatnr.getRowCount(); iMatnr++)	{

					boolean bSpec12 = false;
					boolean bSpec3  = false;
					boolean bSpec5  = false;
					boolean bEcsf   = false;
					boolean bEtm    = false;
					boolean bMatkl  = false;

					String sItemM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ITEM");
					String sMatnrM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATNR");
					String sSpec12M = dsRtnMatnr.getColumnAsString(iMatnr,  "SPEC12");
					int iSpec3M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC3");
					int iSpec5M     = dsRtnMatnr.getColumnAsInteger(iMatnr, "SPEC5");
					String sEcsfM   = dsRtnMatnr.getColumnAsString(iMatnr,  "ECSF");
					String sEtmM    = dsRtnMatnr.getColumnAsString(iMatnr,  "ETM");
					String sMatklM  = dsRtnMatnr.getColumnAsString(iMatnr,  "MATKL");
					int iQntyM      = dsRtnMatnr.getColumnAsDouble(iMatnr,  "QNTY").intValue();

					// ITEM비교
					if (sItem_d != null && sItem_d.equals(sItemM))		{

						// 자재(도면)번호 비교 : 동일하면 확인
						if (sMatnr_d != null && sMatnr_d.equals(sMatnrM))	{

							// SPEC비교 (해당 값이 존재하는 경우)
							if (sSpec12_d == null || sSpec12_d.equals(""))	{
								bSpec12 = true;
							}else if (sSpec12_d != null && sSpec12M != null && !"".equals(sSpec12M) && sSpec12_d.indexOf(sSpec12M) >= 0)	{
								bSpec12 = true;
							}
							
							// 속도 비교
							if (iSpec3fr_d <= iSpec3M && iSpec3M <= iSpec3to_d )	{
								bSpec3 = true;
							}

							// 인승 비교
							if (iSpec5fr_d <= iSpec5M && iSpec5M <= iSpec5to_d )	{
								bSpec5 = true;
							}

							// CAR SAFETY 비교 (해당 값이 존재하는 경우)
							if (sEcsf_d == null || sEcsf_d.equals(""))	{
								bEcsf = true;
							}else if (sEcsfM != null && sEcsfM.indexOf(sEcsf_d) >= 0)	{
								bEcsf = true;
							}

							// TM 비교 (해당 값이 존재하는 경우)
						/*	if (sEtm_d == null || sEtm_d.equals(""))	{
								bEtm = true;
							}else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
								bEtm = true;
							}
					    */
							if (sEtm_d == null || sEtm_d.equals(""))     {
								bEtm = true;
							 }else if (sEtm_d != null && sEtmM != null && !"".equals(sEtmM) && sEtm_d.indexOf(sEtmM) >= 0)	               {
							  bEtm = true;
							 }
						     else if (sEtmM != null && sEtmM.indexOf(sEtm_d) >= 0)	{
							 bEtm = true;
						    }
										

							// BLOK NO 비교 (해당 값이 존재하는 경우)
							if (sMatkl_d == null || sMatkl_d.equals(""))	{
								bMatkl = true;
							}else if (sMatklM != null && sMatklM.indexOf(sMatkl_d) >= 0)	{
								bMatkl = true;
							}
							
							// 모든조건을 만족 (자재건수 누적)
							if (bSpec12 && bSpec3 && bSpec5 && bEcsf && bEtm && bMatkl)	{
								iQntySum += iQntyM;

								// 집계 처리용도
								bSumCheck = true;
							}

						}else	{	// 자재(도면) 번호가 상이하면
							// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
							// 집계 처리가 되도록 요청에 의하여 bSumCheck가 false여도 처리

							//if (bSumCheck)	{
								// 임시Dataset에 추가
								int iRow = dsTempSum.appendRow();
								
								dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
								dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
								dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
								dsTempSum.setColumn(iRow, "STYM",  sStym_d);
								dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
								dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
								dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
	
								bSumCheck = false;
							//}

							break;
						}
					}else	{	// ITEM이 상이하면 실적정보가 존재하지 않음 SKIP
						// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
						// 집계 처리가 되도록 요청에 의하여 bSumCheck가 false여도 처리

						//if (bSumCheck)		{
							// 임시Dataset에 추가
							int iRow = dsTempSum.appendRow();
							
							dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
							dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
							dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
							dsTempSum.setColumn(iRow, "STYM",  sStym_d);
							dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
							dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
							dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
							
							bSumCheck = false;
						//}

						break;
					}

					//자료의 위치
					iPos++;
				}
				// 최종집계 내역
				if (iPos == dsRtnMatnr.getRowCount())	{
					// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
					// 집계 처리가 되도록 요청에 의하여 bSumCheck가 false여도 처리

					//if (bSumCheck)		{
						// 임시Dataset에 추가
						int iRow = dsTempSum.appendRow();
						
						dsTempSum.setColumn(iRow, "ITEM",  sItem_d);
						dsTempSum.setColumn(iRow, "MATNR", sMatnr_d);
						dsTempSum.setColumn(iRow, "GUBUN", sGubun_d);
						dsTempSum.setColumn(iRow, "STYM",  sStym_d);
						dsTempSum.setColumn(iRow, "QNTY",  iQntySum + "");
						dsTempSum.setColumn(iRow, "MATNR_SVAMT", sSvamt_d + "");
						dsTempSum.setColumn(iRow, "MATNR_WAERK", sWaerk_d);
						
						bSumCheck = false;
					//}
					// 실적 건수 최종으로 처리 불필요
					break;
				}

				//
			}

			/*
			// log출력
			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{
				System.out.println(" ITEM : " + dsTempSum.getColumnAsString(iMatnr, "ITEM") + "     " +
						           " MATNR : " + dsTempSum.getColumnAsString(iMatnr, "MATNR") + "     " +
						           " GUBUN : " + dsTempSum.getColumnAsString(iMatnr, "GUBUN") + "     " +
						           " STYM : " + dsTempSum.getColumnAsString(iMatnr, "STYM") + "     " +
						           " QNTY : " + dsTempSum.getColumnAsString(iMatnr, "QNTY") + "     " +
						           " MATNR_SVAMT : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT") + "     " +
						           " MATNR_WAERK : " + dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK") + "....."
						);
			}

			//TransactionManager.rollback();

			*/
			
//			/* 최종 조회 결과 확인 후 처리
			// 산출된 Item 정보
			HashMap hashMap = new HashMap();

			for (int iMatnr = 0; iMatnr < dsTempSum.getRowCount(); iMatnr++)	{

				String sStym = dsTempSum.getColumnAsString(iMatnr, "STYM");
				int iQnty    = dsTempSum.getColumnAsDouble(iMatnr, "QNTY").intValue();

				// 2013.08.09 시스템 집계의 경우 집계수량이 0의 경우도 
				// 집계 처리가 되도록 요청에 의하여 comment

				// 0의 경우 실적 제외 (단 최초 실적월이 존재하지 않아야 함)
				//if (iQnty == 0 && (sStym == null || "".equals(sStym)))	{
				//	continue;
				//}


				// 실적 처리
				if (hashMap.get(dsTempSum.getColumnAsString(iMatnr, "ITEM")) == null)	{

					String sRsltym = "";
					if (sStym == null || "".equals(sStym))	{
						sRsltym = ds_cond.getColumnAsString(i, "RSLTYM");
					}

					hashMap.put(dsTempSum.getColumnAsString(iMatnr, "ITEM"), sRsltym);		// 최초 실적년월이 존재하는 경우 ""
				}

				// 둥록된 자재/도면 번호의 실적 처리 
				zqmt075u.addParamObject("ITEM",  dsTempSum.getColumnAsString(iMatnr, "ITEM"));	// 항목
				zqmt075u.addParamObject("GUBUN", dsTempSum.getColumnAsString(iMatnr, "GUBUN"));	// 자재/도면번호 구분
				zqmt075u.addParamObject("MATNR", dsTempSum.getColumnAsString(iMatnr, "MATNR"));	// 자재/도면번호
				zqmt075u.addParamObject("SVAMT", dsTempSum.getColumnAsString(iMatnr, "MATNR_SVAMT"));	// 절감액
				zqmt075u.addParamObject("WAERK", dsTempSum.getColumnAsString(iMatnr, "MATNR_WAERK"));	// 화폐딴위
				zqmt075u.addParamObject("QNTY",  iQnty + "");	// 수량
	
				// 자재/도면 집계 정보 등록(ZQMT075U)
				zqmt075u.setRowIndex(i);
				executor.execute(zqmt075u);
			}

			// 항목별 집계 처리
			Collection collection = hashMap.keySet();
			Iterator   iter       = collection.iterator();

			while(iter.hasNext())	{
				String sKey   = ((String) iter.next());
				String sValue = ((String) hashMap.get(sKey));	// ""이 아니면 실적년월 등록 필요

				// 산출된 기 항목별 실적집계전 해당 항목 삭제 (ZQMT076U)
				zqmt076dd.addParamObject("ITEM", sKey);

				zqmt076dd.setRowIndex(i);
				executor.execute(zqmt076dd);

				// 항목별 집계 정보 등록 (ZQMT076U) :  자재/도면번호별로 집계된 정보 집계
				zqmt076i.addParamObject("ITEM", sKey);	// 항목

				zqmt076i.setRowIndex(i);
				executor.execute(zqmt076i);

				// 최최 실적일자가 없는 경우에만 반영
				if (sValue != null && !"".equals(sValue))	{
					// 최초 실적등록일 정보 Update
					zqmt071u.addParamObject("ITEM", sKey);	// 항목
					zqmt071u.addParamObject("STYM", ds_cond.getColumnAsString(i, "RSLTYM"));	// 최초실적일자

					zqmt071u.setRowIndex(i);
					executor.execute(zqmt071u);
				}
			}

			// 집계 처리 이력 등록(ZQMT074U)
			zqmt074u.setRowIndex(i);
			executor.execute(zqmt074u);
//			*/
		}
	}

	// 자재/도면 실적 집계 처리 취소
	// 2013.08.07
	public void cancelProcess2(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 최초 실적월 초기상태로 변경 (ZQMT071U)
		DatasetSqlRequest zqmt071u
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_U3");

		zqmt071u.addParamObject("ds_list",   ds_cond);
		zqmt071u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zqmt071u.addParamObject("SGBN",      "S");	// 시스템 산출건만 추출

		// 자재/도면번호 기 실적집계 삭제 (ZQMT075U)
		DatasetSqlRequest zqmt075d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D1");

		zqmt075d.addParamObject("ds_list", ds_cond);
		zqmt075d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 산출된 기 항목별 실적집계 삭제 (ZQMT076U) 시스템에 의하여 기 자동 산출건 삭제
		DatasetSqlRequest zqmt076d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D2");

		zqmt076d.addParamObject("ds_list", ds_cond);
		zqmt076d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zqmt076d.addParamObject("SGBN",    "S");	// 시스템 산출

		// 집계처리 이력 삭제 (ZQMT074U)
		DatasetSqlRequest zqmt074d
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0707001A_D4");

		zqmt074d.addParamObject("ds_list", ds_cond);
		zqmt074d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		// 실적집계대상 항목의 자재/도면번호 대상 조회
		for(int i = 0; i < ds_cond.getRowCount(); i++)		{

			// 실적년월 초기화
			zqmt071u.setRowIndex(i);
			executor.execute(zqmt071u);

			// 기 처리된 실적집계 정보 삭제 처리
			// 자재/도면번호 기 실적집계 삭제 (ZQMT075)
			zqmt075d.setRowIndex(i);
			executor.execute(zqmt075d);

			// 산출된 기 항목별 실적집계 삭제 (ZQMT076)
			zqmt076d.setRowIndex(i);
			executor.execute(zqmt076d);
			
			// 실적년월 이력 삭제
			zqmt074d.setRowIndex(i);
			executor.execute(zqmt074d);
		}
	}

	// 원가절감 적용 현장 집계 
	// 2015.12.08

	public void workareaProcessAdd(BusinessContext ctx, Dataset ds_list) throws Exception {

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));		
		

		// 원가절감항목등록 (ZQMT072) 내역의 항목번호 조회
	DatasetSqlRequest zqmt072s
		= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702002A_S4");	// ITEM 조회

		zqmt072s.addParamObject("ds_cond", ds_list);
		zqmt072s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
	    Dataset dsRtn072 = (Dataset)executor.query(zqmt072s).getResultObject();  // executeQuery()

		//실적 처리일자 조회
		DatasetSqlRequest zqmt071s
		= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702003B_S2");
	
		zqmt071s.addParamObject("ds_cond",   ds_list);
		zqmt071s.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt071s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		Dataset dsRtn071 = (Dataset)executor.query(zqmt071s).getResultObject();  // executeQuery()
		
		// 적용현장 조회 (ZQMT073S)
		DatasetSqlRequest zqmt073s
			= SqlMapManagerUtility.makeSqlRequestForDataset("qm07:QM0702003B_I1");
		
		zqmt073s.addParamObject("ds_list",   ds_list);
		zqmt073s.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
		zqmt073s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
	
		// 자재/도면번호 실적집계 (ZQMT077i)
		for(int i = 0; i < dsRtn072.getRowCount(); i++) { 
			System.out.println(" DEPT : " + dsRtn072.getColumnAsString(i, "DEPT") + "     " +
			           " GJAHR : " + dsRtn072.getColumnAsString(i, "GJAHR") + "     " +
			           " DEPT : " + dsRtn072.getColumnAsString(i, "DEPT") + "     " +
			           " ITEM : " + dsRtn072.getColumnAsString(i, "ITEM"));
			//System.out.println(i);
	   	   	 zqmt073s.addParamObject("DEPT",      dsRtn072.getColumnAsString(i ,  "DEPT"));
	      	 zqmt073s.addParamObject("GJAHR",     dsRtn072.getColumnAsString(i ,  "GJAHR"));
	      	 zqmt073s.addParamObject("DEPT",      dsRtn072.getColumnAsString(i ,  "DEPT"));	
	      	 zqmt073s.addParamObject("ITEM",      dsRtn072.getColumnAsString(i ,  "ITEM"));	
	      	 zqmt073s.addParamObject("S_CRDAT",   dsRtn071.getColumnAsString(0 , "S_CRDAT"));	
	      	 zqmt073s.addParamObject("E_CRDAT",   dsRtn071.getColumnAsString(0 , "E_CRDAT") );	
	    
			zqmt073s.setRowIndex(i);
		    executor.execute(zqmt073s);
			//Dataset dsRtn073 = (Dataset)executor.query(zqmt073s).getResultObject();  // executeQuery()
			
	/*		// log출력
		for(int j = 0; j < dsRtn073.getRowCount(); j++) { 
				System.out.println(" DEPT : " + dsRtn072.getColumnAsString(i, "DEPT") + "     " +
						           " GJAHR : " + dsRtn072.getColumnAsString(i, "GJAHR") + "     " +
						           " DEPT : " + dsRtn072.getColumnAsString(i, "DEPT") + "     " +
						           " ITEM : " + dsRtn072.getColumnAsString(i, "ITEM") + "     " +
						           " WBS : " + dsRtn073.getColumnAsString(j, "WBS") + "     " +
						           " SVAMT : " + dsRtn073.getColumnAsString(j, "SVAMT") + "     " +
						           " QNTY : " + dsRtn073.getColumnAsString(j, "QNTY") );
              
			//TransactionManager.rollback();
	
	       }
	*/       
			
	        
		  }
	}
}
	

