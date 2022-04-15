package com.helco.xf.cs14.ws;

import org.apache.log4j.Logger;

import com.tobesoft.platform.data.Dataset;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.comm.Utillity;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

public class CS1403001A_ServiceImpl extends AbstractBusinessService implements
		CS1403001A_Service {

	public void save(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 매출계획정보 select(zcst131)
		DatasetSqlRequest zcst131s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_S10");
		zcst131s.addParamObject("ds_list", ds_list);
		
		// 기성계획정보 select(zcst136)
		DatasetSqlRequest zcst136s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_S11");
		zcst136s.addParamObject("ds_list", ds_list);

		// zmaster01 update
		DatasetSqlRequest zmaster01u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U1");
		zmaster01u.addParamObject("ds_list", ds_list);
		
		// zmaster02 update
		DatasetSqlRequest zmaster02u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U2");
		zmaster02u.addParamObject("ds_list", ds_list);
		
		// zcst101 update
		DatasetSqlRequest zcst101u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U3");
		zcst101u.addParamObject("ds_list", ds_list);
		
		// zcst111 update
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U4");
		zcst111u.addParamObject("ds_list", ds_list);
		
		// zcst116 update
		DatasetSqlRequest zcst116u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U5");
		zcst116u.addParamObject("ds_list", ds_list);
		
		// zcst123 update
		DatasetSqlRequest zcst123u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U6");
		zcst123u.addParamObject("ds_list", ds_list);
		
		// zcst126 update
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U7");
		zcst126u.addParamObject("ds_list", ds_list);
		
		// zcst127 update
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U8");
		zcst127u.addParamObject("ds_list", ds_list);
		
		// zcst131 update
		DatasetSqlRequest zcst131u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U9");
		zcst131u.addParamObject("ds_list", ds_list);
		
		// zcst136 update
		DatasetSqlRequest zcst136u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U10");
		zcst136u.addParamObject("ds_list", ds_list);
		
		// zcst141 update
		DatasetSqlRequest zcst141u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_U11");
		zcst141u.addParamObject("ds_list", ds_list);

//기안등록시 이관정보를 등록하는 것으로 변경하였음 2014.12
		
//		// 점검이관정보 insert(zcst157)
//		DatasetSqlRequest zcst157i
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs14:CS1403001A_I1");
//		zcst157i.addParamObject("ds_list", ds_list);

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst136s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zmaster01u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster01u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst101u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst101u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst123u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst123u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst131u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst136u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst141u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst141u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

//기안등록시 이관정보를 등록하는 것으로 변경하였음 2014.12
		
//		zcst157i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
//		zcst157i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		

//		ds_list.printDataset();
		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst131s.setRowIndex(i);
				Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();
				
				zcst136s.setRowIndex(i);
				Dataset dsRtn136 = (Dataset)executor.query(zcst136s).getResultObject();
				
				if(dsRtn131.getRowCount() > 0 && dsRtn131.getColumnAsString(0, "GBN").equals("X")) { //매출확정이 한건이라도 발행되었으면 점검이관 중지
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 매출 확정이 완료되었습니다. 점검이관 처리를 중지 합니다.");
				} else if(dsRtn136.getColumnAsInteger(0, "CNT") > 0) { //보수월기성 마감 완료 처리 되었으면 점검이관 불가
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 월기성이 마감되었습니다. 점검이관 처리를 중지 합니다.");
				} else {
					zmaster01u.setRowIndex(i);
					zmaster02u.setRowIndex(i);
					zcst101u.setRowIndex(i);
					zcst111u.setRowIndex(i);
					zcst116u.setRowIndex(i);
					zcst123u.setRowIndex(i);
					zcst126u.setRowIndex(i);
					zcst127u.setRowIndex(i);
//					zcst131u.setRowIndex(i);
//					zcst136u.setRowIndex(i);
					
					executor.execute(zmaster01u);
					executor.execute(zmaster02u);
					executor.execute(zcst101u);
					executor.execute(zcst111u);
					executor.execute(zcst116u);
					executor.execute(zcst123u);
					executor.execute(zcst126u);
					executor.execute(zcst127u);
//					executor.execute(zcst131u);
//					executor.execute(zcst136u);

//기안등록시 이관정보를 등록하는 것으로 변경하였음 2014.12
					
//					if("N".equals(ds_list.getColumnAsString(i, "FYN"))) {
//						zcst157i.setRowIndex(i);
//						executor.execute(zcst157i);
//					}
					
				}
			}
		}		
	}
}
