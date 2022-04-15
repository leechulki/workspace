package com.helco.xf.cs18.ws;

import com.tobesoft.platform.data.Dataset;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.TitUtility;

public class CS1801002A_ServiceImpl extends AbstractBusinessService implements
		CS1801002A_Service {

	public void save(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 매출계획정보 select(zcst131)
		DatasetSqlRequest zcst131s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs18:CS1801002A_S3");
		zcst131s.addParamObject("ds_list", ds_list);

		// 기성마감정보 select(zcst166)
		DatasetSqlRequest zcst166s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs18:CS1801002A_S4");
		zcst166s.addParamObject("ds_list", ds_list);

		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs18:CS1801002A_U1");
		zcst111u.addParamObject("ds_list", ds_list);

		// update(zcst146)
		DatasetSqlRequest zcst146u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs18:CS1801002A_U2");
		zcst146u.addParamObject("ds_list", ds_list);

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst166s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst166s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst146u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst146u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		ComMethodVo comVo;
		ComMethodDao comDao;

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("1".equals(ds_list.getColumnAsString(i, "CHECK"))) {
				zcst131s.setRowIndex(i);
				Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();

				if(dsRtn131.getRowCount() > 0 && dsRtn131.getColumnAsString(0, "GBN").equals("X")) { //통합프로젝트 호기별로 매출확정이 한건이라도 발행되었으면 중지/보류 복구 처리 중지
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 매출 확정이 완료되었습니다. 중지/보류 복구 처리를 중지 합니다.");
				} else {
					zcst166s.setRowIndex(i);
					Dataset dsRtn166 = (Dataset)executor.query(zcst166s).getResultObject();

					if(dsRtn166.getColumnAsInteger(0, "CNT") > 0) { //해당 협력사별로 기성마감이 완료되었으면 중지/보류 복구 처리 중지
						throw new BizException("해당 협력사의 [" + ds_list.getColumnAsString(i, "CONDYYM").substring(0, 6) + "] 기성마감이 완료되었습니다. 중지/보류 복구 처리를 중지 합니다.");
					} else {
						zcst146u.setRowIndex(i);
						zcst111u.setRowIndex(i);

						executor.execute(zcst146u);
						executor.execute(zcst111u);

						//기성비 연동
						comVo = new ComMethodVo();
						comDao = new ComMethodDao();

						comVo.setDataId("53");

		//				comVo.setJobGubun("I/D");
		//			    comVo.setJobGubun("I");
						comVo.setJobGubun("R");

						comVo.setMandt(ctx.getInputVariable("G_MANDT"));
						comVo.setUpn(ds_list.getColumnAsString(i, "UPN"));
						comVo.setCst(ds_list.getColumnAsString(i, "CST"));
						comVo.setPjt(ds_list.getColumnAsString(i, "PJT"));
						comVo.setHno(ds_list.getColumnAsString(i, "HNO"));
						comVo.setSeq(ds_list.getColumnAsString(i, "SEQ"));
						comVo.setGnd(ds_list.getColumnAsString(i, "GND"));
						comVo.setJhd(ds_list.getColumnAsString(i, "JHD"));
						comVo.setStopDate(ds_list.getColumnAsString(i, "SDT"));
						comVo.setYym(ds_list.getColumnAsString(i, "CONDYYM"));
						comVo.setUserId(ctx.getInputVariable("G_USER_ID"));

						int rtnCode = comDao.SetBXRBXL(comVo);
					}
				}
			}
		}
	}

	public void sendback(BusinessContext ctx, Dataset ds_list) throws Exception {

	}
}
