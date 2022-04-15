package com.helco.xf.ps12.ws;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;

public class PS1209002A_ACT extends AbstractAction 
{
	private static final long serialVersionUID = 5661911687794518235L;

	
	public void execute(BusinessContext ctx) throws Exception{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("ps12:PS1209002A_S1");
		sqlRequest.addParamObject("POSID", DatasetUtility.getString(dsCond, "POSID"));
		sqlRequest.addParamObject("PRT", DatasetUtility.getString(dsCond, "PRT"));
		sqlRequest.addParamObject("EXTWG2", DatasetUtility.getString(dsCond, "EXTWG2"));
		sqlRequest.addParamObject("MATNR_NM", DatasetUtility.getString(dsCond, "MATNR_NM"));
		sqlRequest.addParamObject("POST1", DatasetUtility.getString(dsCond, "POST1"));
		sqlRequest.addParamObject("FROM_DT", DatasetUtility.getString(dsCond, "FROM_DT"));
		sqlRequest.addParamObject("TO_DT", DatasetUtility.getString(dsCond, "TO_DT"));		

		String db_con = "jdbc/mts";
		
		DatasetSqlExecutor SqlExecutor  = new DatasetSqlExecutor(getConnection(db_con));
		Dataset ds_list =  (Dataset)SqlExecutor.query(sqlRequest).getResultObject();

		ctx.addOutputDataset("ds_list",ds_list);
	}
	
	public void query2(BusinessContext ctx) throws Exception{
		Dataset dsCond = ctx.getInputDataset("ds_cond2");
		String sQrNum = DatasetUtility.getString(dsCond, "QR_NUM");
		
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("ps12:PS1209002A_S2");
		sqlRequest.addParamObject("QR_NUM", sQrNum);

		String db_con = "jdbc/mts";
		
		DatasetSqlExecutor SqlExecutor  = new DatasetSqlExecutor(getConnection(db_con));
		Dataset ds_list2 =  (Dataset)SqlExecutor.query(sqlRequest).getResultObject();

		ctx.addOutputDataset("ds_list2",ds_list2);
	}
	
	public void query_invoice(BusinessContext ctx) throws Exception{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("ps12:PS1209003A_S1");
		sqlRequest.addParamObject("INVOICE_NO", DatasetUtility.getString(dsCond, "INVOICE_NO"));
		sqlRequest.addParamObject("PSPID", DatasetUtility.getString(dsCond, "PSPID"));
		sqlRequest.addParamObject("CRT_DTM_FROM", DatasetUtility.getString(dsCond, "CRT_DTM_FROM"));
		sqlRequest.addParamObject("CRT_DTM_TO", DatasetUtility.getString(dsCond, "CRT_DTM_TO"));
		sqlRequest.addParamObject("LIFNR", DatasetUtility.getString(dsCond, "LIFNR"));

		String db_con = "jdbc/mts";
		
		DatasetSqlExecutor SqlExecutor  = new DatasetSqlExecutor(getConnection(db_con));
		Dataset ds_head =  (Dataset)SqlExecutor.query(sqlRequest).getResultObject();

		ctx.addOutputDataset("ds_head",ds_head);
	}
	
	public void query_invoice2(BusinessContext ctx) throws Exception{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("ps12:PS1209003A_S2");
		sqlRequest.addParamObject("INVOICE_NO", DatasetUtility.getString(dsCond, "INVOICE_NO"));

		String db_con = "jdbc/mts";
		
		DatasetSqlExecutor SqlExecutor  = new DatasetSqlExecutor(getConnection(db_con));
		Dataset ds_list =  (Dataset)SqlExecutor.query(sqlRequest).getResultObject();

		ctx.addOutputDataset("ds_list",ds_list);
	}
	
	public void update_invoice(BusinessContext ctx) throws Exception{

		Dataset ds_head1 = ctx.getInputDataset("ds_head1");
		String g_user_id = ctx.getInputVariable("G_USER_ID");

		String db_con = "jdbc/mts";
		
		DatasetSqlExecutor SqlExecutor  = new DatasetSqlExecutor(getConnection(db_con));
		DatasetSqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequestForDataset("ps12:PS1209003A_I1");
		
		sqlRequest.addParamObject("ds_head1", ds_head1);
		sqlRequest.addParamObject("G_USER_ID", g_user_id);
		
		for ( int i=0; i<ds_head1.getRowCount(); i++) {
			sqlRequest.setRowIndex(i);
			SqlExecutor.execute(sqlRequest);
		}
	}
}
