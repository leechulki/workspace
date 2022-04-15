package com.helco.xf.cs11.ws;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.BizException;
import com.tobesoft.platform.data.Dataset;


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


public class CS1107001A_ServiceImpl extends AbstractBusinessService implements
		CS1107001A_Service {

	/*-----------------------------------------------------
	 *  mod 무상 서비스 계약 정보 저장 
	 *  2017-06-20   최초 릴리즈 
	 ------------------------------------------------------*/
	public void save(BusinessContext ctx) throws Exception {

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
	
		DatasetSqlRequest checkModServiceData
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1107001B_S3");
		checkModServiceData.addParamObject("ds_list", ds_list);
		checkModServiceData.addParamObject("G_MANDT", mdt);
		
		// zcst116m insert 
		DatasetSqlRequest zcst116i = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1107001B_I1");
		zcst116i.addParamObject("ds_list"  , ds_list);
		zcst116i.addParamObject("G_MANDT"  , mdt);
		zcst116i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		// zcst116m update 
		DatasetSqlRequest zcst116u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1107001B_U1");
		zcst116u.addParamObject("ds_list"  , ds_list);
		zcst116u.addParamObject("G_MANDT"  , mdt);
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		
		String p_seq = "";
		
		for(int j=0; j < ds_list.getRowCount(); j++) {

			String flag = ds_list.getColumnAsString(j, "FLAG");
			String pjt = ds_list.getColumnAsString(j, "PJT");
			String hno = ds_list.getColumnAsString(j, "HNO");
			checkModServiceData.setRowIndex(j);
			Dataset dsRtnCheckModServiceData = (Dataset)executor.query(checkModServiceData).getResultObject();

			if(flag.equals("I")) {
				
				if(dsRtnCheckModServiceData.getRowCount() > 0 ){
					
					String s_bgt = dsRtnCheckModServiceData.getColumnAsString(0, "BGT");
					String t_bgt = s_bgt.substring(0,4) + "-" + s_bgt.substring(4,6) + "-" + s_bgt.substring(6) ;
					throw new BizException("  \n" + t_bgt + " 무상개시 작성 데이타가 존재 합니다.");
					
				} else {
					
					String sql = "";
					
					sql += "SELECT VALUE(SAPHEE.FILLINZERO(INT(MAX(CS116M_SEQ))+1,2),'01')  AS SEQ ";
					sql += "  FROM SAPHEE.ZCST116M WHERE MANDT = #G_MANDT#";
					sql += "   AND CS116M_PJT = #PJT#";
					sql += "   AND CS116M_HNO = #HNO#";

					// SqlRequest 생성
					SqlRequest sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
					
					sqlRequest.addParamObject("G_MANDT"  , mdt );
					sqlRequest.addParamObject("PJT"     , pjt);
					sqlRequest.addParamObject("HNO"     , hno);
					
					// 조회 처리 
					SqlExecutor db = new DatasetSqlExecutor(getConnection(db_con));
					SqlResult result = db.query(sqlRequest);
					
					// 조회 결과 객체 찾아오기 
					Dataset dsRt = (Dataset)result.getResultObject();
					
					p_seq = dsRt.getColumnAsString(0, "SEQ");

					ds_list.setColumn(j, "SEQ"      , p_seq);
					zcst116i.addParamObject("SEQ", p_seq);
					zcst116i.setRowIndex(j);
					executor.execute(zcst116i);

					ds_list.setColumn(j, "SEQ"      , p_seq);
					
					ctx.addOutputDataset(ds_list);
				}

			} else {
				zcst116u.setRowIndex(j);
				executor.execute(zcst116u);
			}
			 	
		}
	}

	/*-----------------------------------------------------
	 *  mod 무상 서비스 계약 정보 전송/반송/승인 처리
	 *  2017-06-20   최초 릴리즈 
	 ------------------------------------------------------*/
	public void approvalProcess(BusinessContext ctx) throws Exception {

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
	
		// zcst116m update 
		DatasetSqlRequest zcst116u2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1107001B_U2");
		zcst116u2.addParamObject("ds_list"  , ds_list);
		zcst116u2.addParamObject("G_MANDT"  , mdt);
		zcst116u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for(int j=0; j < ds_list.getRowCount(); j++) {
			zcst116u2.setRowIndex(j);
			executor.execute(zcst116u2);
		}
		 	
	}
	/*-----------------------------------------------------
	 *  mod 무상 서비스 계약 정보 삭제 처리 
	 *  2017-06-20   최초 릴리즈 
	 ------------------------------------------------------*/
	public void deleteData(BusinessContext ctx) throws Exception {

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_list = ctx.getInputDataset("ds_list");
	
		// zcst116m update 
		DatasetSqlRequest zcst116d1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1107001B_D1");
		zcst116d1.addParamObject("ds_list"  , ds_list);
		zcst116d1.addParamObject("G_MANDT"  , mdt);
		zcst116d1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for(int j=0; j < ds_list.getRowCount(); j++) {
			zcst116d1.setRowIndex(j);
			executor.execute(zcst116d1);
		}
		 	
	}
}
