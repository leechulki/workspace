package com.helco.xf.wb01;

import java.security.MessageDigest;
import java.sql.ResultSet;

import com.helco.xf.comm.Utillity;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.util.Base64Converter;
import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.config.ConfigUtility;
import tit.service.sqlmap.SimpleSqlExecutor;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;


/**
 * 공통 요청 정보 가져오기 
 */
public class PDALoginAction extends AbstractAction {
	/**
	 * PDA사용자 로그인 
	 * @param ctx
	 * @throws Exception
	 */
	public void execute(BusinessContext ctx) throws Exception{
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("pcc04:LOGIN_S01");
		sqlRequest.addParamObject("USER_ID", ctx.getInputVariable("USER_ID"));	
		sqlRequest.addParamObject("USER_PW", ctx.getInputVariable("USER_PW"));	
		sqlRequest.addParamObject("MANDT", ctx.getInputVariable("G_MANDT"));

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor SqlExecutor  = new DatasetSqlExecutor(getConnection(db_con));
		Dataset ds_Login =  (Dataset)SqlExecutor.query(sqlRequest).getResultObject();
		/*
		if(ds_Login.getRowCount() > 0){
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(ctx.getInputVariable("USER_ID").getBytes());
			byte[] digest = md.digest();
			ds_Login.setColumn(0, "GV_LOGINKEY", Base64Converter.encode(digest));
		}
		*/
		
		// 권한 정보 가져오기 
		Dataset dsAuth = makeAuthData(ctx.getInputVariable("USER_ID"),"KO",ctx.getInputVariable("G_MANDT"));
		if(dsAuth != null) {
			ctx.addOutputDataset("gds_auth", dsAuth);
		}

		// 숙당직 정보 가져오기 
		String w_flag = chkWorkData(ctx.getInputVariable("USER_ID"));
		ds_Login.setColumn(0, "WORK_FLAG", w_flag);

		ctx.addOutputDataset("ds_Login",ds_Login);
		ctx.addOutputVariable("ErrorCode", 0);
		ctx.addOutputVariable("ErrorMsg", "OK");
	}
	
	/**
	 * 프로그램 사용권한
	 */
	public Dataset makeAuthData(String userId, String lang, String mandt) throws Exception {
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("wb01:MENU_S3");
		sqlRequest.addParamObject("USER_ID", userId);
		sqlRequest.addParamObject("LANG", lang.toLowerCase());
		sqlRequest.addParamObject("MANDT", mandt);
		
		DatasetSqlExecutor db = null;
		String db_con = Utillity.getSapJndi(mandt);
		db = new DatasetSqlExecutor(getConnection(db_con));
		
		return (Dataset)db.query(sqlRequest).getResultObject();
	}
	
	/**
	 * 숙당직 여부 체크
	 */
	public String chkWorkData(String userId) throws Exception {
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSqlRequest("pcc04:LOGIN_S02");
		sqlRequest.addParamObject("USER_ID", userId);

		String t_flag = "";
		SqlExecutor db = null;
		String db_con = "jdbc/cs_uc";

		db = new SimpleSqlExecutor(getConnection(db_con));
		ResultSet rs = (ResultSet)db.query(sqlRequest).getResultObject();

		if(rs.next()) {
			if(rs.getInt("CNT") > 0) {
				t_flag = "1"; //숙당직 있음
			} else {
				t_flag = "0"; //숙당직 없음
			}
		}
		
		rs.close();

		return t_flag;
	}
}
