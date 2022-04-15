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

public class CS1801001A_ServiceImpl extends AbstractBusinessService implements
		CS1801001A_Service {

	public void save(BusinessContext ctx, Dataset ds_temp3) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 매출계획정보 select(zcst131)
		DatasetSqlRequest zcst131s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs18:CS1801001A_S3");
		zcst131s.addParamObject("ds_temp3", ds_temp3);

		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs18:CS1801001A_U1");
		zcst111u.addParamObject("ds_temp3", ds_temp3);

		//  update(zcst146)
		DatasetSqlRequest zcst146i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs18:CS1801001A_I1");
		zcst146i.addParamObject("ds_temp3", ds_temp3);

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst146i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst146i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		ComMethodVo comVo;
		ComMethodDao comDao;

		for(int i=0; i<ds_temp3.getRowCount(); i++) {
			if("U".equals(ds_temp3.getColumnAsString(i, "FLAG"))) {
				zcst131s.setRowIndex(i);
				Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();
				
				if(dsRtn131.getRowCount() > 0 && dsRtn131.getColumnAsString(0, "GBN").equals("X")) { //통합프로젝트 호기별로 매출확정이 한건이라도 발행되었으면 중지/보류 처리 중지
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 매출 확정이 완료되었습니다. 중지/보류 처리를 중지 합니다.");
				} else {
					zcst146i.setRowIndex(i);
					zcst111u.setRowIndex(i);
	
					executor.execute(zcst146i);
					executor.execute(zcst111u);
	
					//기성비 연동
					comVo = new ComMethodVo();
					comDao = new ComMethodDao();
	
					comVo.setDataId("52");

//					comVo.setJobGubun("I/D");
//					comVo.setJobGubun("I");
					comVo.setJobGubun("D");

					comVo.setMandt(ctx.getInputVariable("G_MANDT"));
					comVo.setUpn(ds_temp3.getColumnAsString(i, "UPN"));
					comVo.setCst(ds_temp3.getColumnAsString(i, "CST"));
					comVo.setPjt(ds_temp3.getColumnAsString(i, "PJT"));
					comVo.setHno(ds_temp3.getColumnAsString(i, "HNO"));
					comVo.setSeq(ds_temp3.getColumnAsString(i, "SEQ"));
					comVo.setGnd(ds_temp3.getColumnAsString(i, "GND"));
					comVo.setJhd(ds_temp3.getColumnAsString(i, "JHD"));
					comVo.setUserId(ctx.getInputVariable("G_USER_ID"));
	
					int rtnCode = comDao.SetBXRBXL(comVo);
				}
			}
		}		
	}

	public void sendback(BusinessContext ctx, Dataset ds_temp3) throws Exception {

	}
}
