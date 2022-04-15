package com.helco.xf.cs34.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.batch.BatchConstants;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.tobesoft.platform.data.Dataset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class CS3403001A_ACT extends AbstractAction {

	static Logger logger;
	String mandt = "183";

	/**
	 * 조회 
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception {	
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		String db_con = Utillity.getSapJndi(dsCond.getColumnAsString(0, "MANDT"));
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:VKGRP_S1");

		sqlRequest.addParamObject("ds_cond", dsCond);
		sqlRequest.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		String vkgrp = "";
		String t_ara = CommonUtil.NullToBlank(dsCond.getColumnAsString(0, "ARA"));
		String t_bsu = CommonUtil.NullToBlank(dsCond.getColumnAsString(0, "BSU"));
		String t_bpm = CommonUtil.NullToBlank(dsCond.getColumnAsString(0, "BPM"));

		if(t_ara.trim().equals("") && t_bsu.trim().equals("") && t_bpm.trim().equals("")) {
			vkgrp = "TALL";
		} else if(t_ara.trim().equals("E5") && t_bsu.trim().equals("") && t_bpm.trim().equals("")) {
				vkgrp = "ALL";
		} else {
			Dataset dsRtn = (Dataset)executor.query(sqlRequest).getResultObject();
			vkgrp = dsRtn.getColumnAsString(0, "VKGRP");	
		}

		// 수금계획 select(zfit1014)
		DatasetSqlRequest zfit1014s = SqlMapManagerUtility.makeSqlRequestForDataset("cs34:CS3403001A_S1");
		zfit1014s.addParamObject("ds_cond", dsCond);
		
		zfit1014s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zfit1014s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zfit1014s.addParamObject("VKGRP", vkgrp);

//		zfit1014s.setRowIndex(0);
		Dataset dsRtn1014 = (Dataset)executor.query(zfit1014s).getResultObject();

		dsRtn1014.setId("ds_list");

		// 목록 정보 옮기기 
		ctx.addOutputDataset(dsRtn1014);
	}

	/**
	 * 저장 및 계산 
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;
        CallableStatement cstmt = null;
        logger = ServiceManagerFactory.getLogger();

		try{
			String mandt = "";
			String yymm  = "";
			String bukrs = "";
            String kunnr = "";
            String kidno = "";
            String gjahr = "";
            String belnr = "";
            String pldat = "";
            Double amt   = 0.00;
            String waers = "";
            String text  = "";
            String user  = "";

			Dataset dsList = ctx.getInputDataset("ds_list");
			//Connection을 얻는다.
			String db_con = Utillity.getSapJndi(dsList.getColumnAsString(0, "MANDT"));
		    conn = getConnection(db_con);

			DatasetSqlExecutor executor = new DatasetSqlExecutor(conn);

			DatasetSqlRequest zcst407 = SqlMapManagerUtility.makeSqlRequestForDataset("cs34:CS3403001A_I2");
			zcst407.addParamObject("ds_list", dsList);

			zcst407.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			zcst407.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

			conn.setAutoCommit(false);

			for(int i=0; i<dsList.getRowCount(); i++) {
				cstmt = conn.prepareCall("{CALL SAPHEE.BOSU_YOUNGUP_MISU(?,?,?,?,?,?,?,?,?,?,?,?)}");

				if("U".equals(dsList.getColumnAsString(i, "FLAG"))) {
					mandt = dsList.getColumnAsString(i, "MANDT");
					yymm  = dsList.getColumnAsString(i, "YYMM");
					bukrs = "1000";
		            kunnr = dsList.getColumnAsString(i, "KUNNR");
		            kidno = dsList.getColumnAsString(i, "PSPID");
		            gjahr = dsList.getColumnAsString(i, "GJAHR");
		            belnr = dsList.getColumnAsString(i, "BELNR");
		            pldat = dsList.getColumnAsString(i, "PLDAT");
		            amt   = Double.parseDouble(CommonUtil.NullBlankToZero(dsList.getColumnAsString(i, "AMT_MM"))) / 100;
		            waers = dsList.getColumnAsString(i, "WAERK");
		            text  = dsList.getColumnAsString(i, "INGTEXT");
		            user  = ctx.getInputVariable("G_USER_ID");

		            cstmt.setString( 1, mandt);
		            cstmt.setString( 2, yymm);
		            cstmt.setString( 3, bukrs);
		            cstmt.setString( 4, kunnr);
		            cstmt.setString( 5, kidno);
		            cstmt.setString( 6, gjahr);
		            cstmt.setString( 7, belnr);
		            cstmt.setString( 8, pldat);
		            cstmt.setDouble( 9, amt);
		            cstmt.setString(10, waers);
		            cstmt.setString(11, text);
		            cstmt.setString(12, user);

		            cstmt.executeUpdate();

		            zcst407.setRowIndex(i);
					executor.execute(zcst407);

		            conn.commit();
				}
			}
		} catch(SQLException se) {
			// 에러 메세지 출력
			logger.debug("\n" + se.toString());
			se.printStackTrace();

			try {
	            try {
	            	conn.rollback();
				} catch (SQLException sse) {
					logger.debug("Rollback failed : " + sse.toString());
					sse.printStackTrace();
				}
	            BatchConstants.close(null,null,conn);
	        } catch(Exception te) {}
		} catch(BizException be) {
			// 에러 메세지 출력
			logger.debug("\n" + be.toString());
			be.printStackTrace();

			try {
				try {
	            	conn.rollback();
				} catch (SQLException sse) {
					logger.debug("Rollback failed : " + sse.toString());
					sse.printStackTrace();
				}
	            BatchConstants.close(null,null,conn);
	        } catch(Exception te) {}
		} catch(Exception e) {
			// 에러 메세지 출력
			logger.debug("\n" + e.toString());
			e.printStackTrace();

			try {
				try {
	            	conn.rollback();
				} catch (SQLException sse) {
					logger.debug("Rollback failed : " + sse.toString());
					sse.printStackTrace();
				}
	            BatchConstants.close(null,null,conn);
	        } catch(Exception te) {}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch(SQLException e) {}
			try {
	            BatchConstants.close(null,null,conn);
	        } catch(Exception e) {
	            e.printStackTrace();
	            logger.debug("\n" + e.toString());
	        }
		}
	}
}
