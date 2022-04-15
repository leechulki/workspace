package com.helco.xf.cs06.ws;
 
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs06.ws.ZSDS0075;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.ws.CS1201001B_Service;
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.service.sqlmap.SimpleSqlExecutor;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

public class CS0602001A_ACT extends AbstractAction {

	/**
	 * 자동발주
	 * @param ctx
	 * @throws Exception
	 */
	public void musangBalju(BusinessContext ctx) throws Exception {
	
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_list = ctx.getInputDataset("ds_list");

		DatasetSqlRequest zhnoinfo
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602002A_S1");
		zhnoinfo.addParamObject("ds_list", ds_list);
		zhnoinfo.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		// 무상공사
		DatasetSqlRequest zcst1i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602002A_I1");
		zcst1i.addParamObject("ds_list", ds_list);
		zcst1i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst1i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		// 무상일반
		DatasetSqlRequest zcst2i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602002A_I2");
		zcst2i.addParamObject("ds_list", ds_list);
		zcst2i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst2i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		DatasetSqlRequest zcst1u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602002A_U1");
		zcst1u.addParamObject("ds_list", ds_list);
		zcst1u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		DatasetSqlRequest zcst2u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602002A_U2");
		zcst2u.addParamObject("ds_list", ds_list);
		zcst2u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		DatasetSqlRequest zcst3i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602002A_I3");
		zcst3i.addParamObject("ds_list", ds_list);
		zcst3i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst3i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		
		for(int i=0; i<ds_list.getRowCount(); i++) {

			zhnoinfo.setRowIndex(i);
			Dataset dsRtn1 = (Dataset)executor.query(zhnoinfo).getResultObject();

			if(dsRtn1.getRowCount() > 0) {
				// 직영
				if(dsRtn1.getColumnAsString(0, "BSU_GB").equals("1")) { 
					// 승강기
					if(dsRtn1.getColumnAsString(0, "HNO_GB").equals("L") || dsRtn1.getColumnAsString(0, "HNO_GB").equals("S") || dsRtn1.getColumnAsString(0, "HNO_GB").equals("W") ){
						// 무상공사 insert zcst116
						zcst1i.setRowIndex(i);
						executor.execute(zcst1i);
						// 무상일반 insert zcst116
						zcst2i.setRowIndex(i);
						executor.execute(zcst2i);
						// update zmaster02 개월수 소진
						zcst1u.setRowIndex(i);
						executor.execute(zcst1u);
						// update zcst111 무상계약일자
						zcst2u.setRowIndex(i);
						executor.execute(zcst2u);
						// 자동발주 리스트 insert zcst117
						zcst3i.setRowIndex(i);
						executor.execute(zcst3i);

					}
				}
			}
		}
	}
	/**
	 * 인수 
	 * 1. zcst111
	 * 2. zmmt078
	 * 3. zmmt095
	 * 4. zcst121
	 * 5. zcst122
	 * 6. zcst101
	 * 7. zmaster01
	 * 8. zmaster02
	 * @param ctx
	 * @throws Exception
	 */
	public void takeOver(BusinessContext ctx) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String usr = ctx.getInputVariable("G_USER_ID");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_list = ctx.getInputDataset("ds_list");	
		Dataset ds_list_m = ctx.getInputDataset("ds_list_m");
		// 1. zcst111
		DatasetSqlRequest zcst111s
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_S2");
		zcst111s.addParamObject("ds_list", ds_list);
		zcst111s.addParamObject("G_MANDT", mdt);

		DatasetSqlRequest zcst111i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_I1");
		zcst111i.addParamObject("ds_list", ds_list);
		zcst111i.addParamObject("G_MANDT", mdt);
		zcst111i.addParamObject("G_USER_ID", usr);
		
		DatasetSqlRequest zcst111u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_U1");
		zcst111u.addParamObject("ds_list", ds_list);
		zcst111u.addParamObject("G_MANDT", mdt);
		zcst111i.addParamObject("G_USER_ID", usr);		
		// 2. zmmt078
		DatasetSqlRequest zmmt078s
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_S13");
		zmmt078s.addParamObject("ds_list", ds_list);
		zmmt078s.addParamObject("G_MANDT", mdt);

		DatasetSqlRequest zmmt078i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_I6");
		zmmt078i.addParamObject("ds_list", ds_list);
		zmmt078i.addParamObject("G_MANDT", mdt);
		zmmt078i.addParamObject("G_USER_ID", usr);
		
		DatasetSqlRequest zmmt078u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_U9");
		zmmt078u.addParamObject("ds_list", ds_list);
		zmmt078u.addParamObject("G_MANDT", mdt);
		zmmt078u.addParamObject("G_USER_ID", usr);
		// 3. zmmt095
		DatasetSqlRequest zmmt095i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_I7");
		zmmt095i.addParamObject("ds_list", ds_list);
		zmmt095i.addParamObject("G_MANDT", mdt);
		zmmt095i.addParamObject("G_USER_ID", usr);
		// 4. zcst121
		DatasetSqlRequest zcst121s
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_S3");
		zcst121s.addParamObject("ds_list_m", ds_list_m);
		zcst121s.addParamObject("G_MANDT", mdt);

		DatasetSqlRequest zcst121i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_I3");
		zcst121i.addParamObject("ds_list_m", ds_list_m);
		zcst121i.addParamObject("G_MANDT", mdt);
		zcst121i.addParamObject("G_USER_ID", usr);
		// 5. zcst122
		DatasetSqlRequest zcst122s
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_S4");
		zcst122s.addParamObject("ds_list", ds_list);
		zcst122s.addParamObject("G_MANDT", mdt);

		DatasetSqlRequest zcst122i
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_I4");
		zcst122i.addParamObject("ds_list", ds_list);
		zcst122i.addParamObject("G_MANDT", mdt);
		zcst122i.addParamObject("G_USER_ID", usr);
		// 6. zcst101
		DatasetSqlRequest zcst101u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_U2");
		zcst101u.addParamObject("ds_list", ds_list);
		zcst101u.addParamObject("G_MANDT", mdt);
		zcst101u.addParamObject("G_USER_ID", usr);
		// 7. zmaster01
		DatasetSqlRequest zmaster01u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_U8");
		zmaster01u.addParamObject("ds_list", ds_list);
		zmaster01u.addParamObject("G_MANDT", mdt);
		zmaster01u.addParamObject("G_USER_ID", usr);
		// 8. zmaster02
		DatasetSqlRequest zmaster02u
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs06:CS0602001A_U4");
		zmaster02u.addParamObject("ds_list", ds_list);
		zmaster02u.addParamObject("G_MANDT", mdt);
		zmaster02u.addParamObject("G_USER_ID", usr);


		for(int i=0; i<ds_list.getRowCount(); i++) {

		// 1. zcst111
			zcst111s.setRowIndex(i);
			Dataset dsRtn111 = (Dataset)executor.query(zcst111s).getResultObject();
			
			if(dsRtn111.getRowCount() > 0 ) { 
				zcst111u.setRowIndex(i);
				executor.execute(zcst111u);
			} else {
				zcst111i.setRowIndex(i);
				executor.execute(zcst111i);
			}
		// 2. zmmt078
			zcst111s.setRowIndex(i);
			Dataset dsRtn078 = (Dataset)executor.query(zmmt078s).getResultObject();
			
			if(dsRtn078.getRowCount() > 0 ) { 
				zmmt078u.setRowIndex(i);
				executor.execute(zmmt078u);
			} else {
				zmmt078i.setRowIndex(i);
				executor.execute(zmmt078i);
			}
		// 3. zmmt095
			zmmt095i.setRowIndex(i);
			executor.execute(zmmt095i);

		// 5. zcst122
			zcst122s.setRowIndex(i);
			Dataset dsRtn122 = (Dataset)executor.query(zcst122s).getResultObject();
			
			if(dsRtn122.getRowCount() == 0 ) { 
				zcst122i.setRowIndex(i);
				executor.execute(zcst122i);
			}
		// 6. zcst101
			zcst101u.setRowIndex(i);
			executor.execute(zcst101u);
		// 7. zmaster01
			zmaster01u.setRowIndex(i);
			executor.execute(zmaster01u);
		// 8. zmaster02	
			zmaster02u.setRowIndex(i);
			executor.execute(zmaster02u);	
			
		}
		
		for(int j=0; j<ds_list_m.getRowCount(); j++) {
			// 4. zcst121
			zcst121s.setRowIndex(j);
			Dataset dsRtn121 = (Dataset)executor.query(zcst121s).getResultObject();
			
			if(dsRtn121.getRowCount() == 0 ) { 
				zcst121i.setRowIndex(j);
				executor.execute(zcst121i);
			}	
		}
	}
}
