package com.helco.xf.ps13.ws;

import java.sql.Connection;
import java.sql.SQLException;

import com.tobesoft.platform.data.Dataset;

import com.helco.xf.cs12.batch.BatchConstants;
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

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.business.config.ConfigUtility;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.StringOperator;
import tit.util.TitUtility;

public class PS1301005A_ServiceImpl extends AbstractBusinessService implements PS1301005A_Service {
	public void approve(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mandt = ctx.getInputVariable("G_MANDT");
		String userId = ctx.getInputVariable("G_USER_ID");
		String sql = "";
		String temno = "";
		
		String db_con = Utillity.getSapJndi(mandt);
		
		SqlRequest sqlRequest = null;
		SqlExecutor db = null;
		SqlResult result = null;
		Dataset returnDs = null;

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		DatasetSqlRequest zpst001 = SqlMapManagerUtility.makeSqlRequestForDataset("ps13:PS1301005A_I1");
		DatasetSqlRequest zpst002 = SqlMapManagerUtility.makeSqlRequestForDataset("ps13:PS1301005A_I2");
		DatasetSqlRequest zpst002u = SqlMapManagerUtility.makeSqlRequestForDataset("ps13:PS1301005A_U1");

		zpst001.addParamObject("ds_list", ds_list);
		zpst002.addParamObject("ds_list", ds_list);
		zpst002u.addParamObject("ds_list", ds_list);

		zpst001.addParamObject("G_MANDT", mandt);
		zpst001.addParamObject("G_USER_ID", userId);
		zpst002.addParamObject("G_MANDT", mandt);
		zpst002.addParamObject("G_USER_ID", userId);
		zpst002u.addParamObject("G_MANDT", mandt);
		zpst002u.addParamObject("G_USER_ID", userId);

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if(ds_list.getColumnAsString(i, "POSIT").trim().equals("X"))
			{
				sql = " SELECT 'T'||TRIM(REPEAT('0',5-LENGTH(TRIM(CHAR(INT(RIGHT(VALUE(MAX(TEMNO),'T00000'),5))+1))))) || TRIM(CHAR(INT(RIGHT(VALUE(MAX(TEMNO),'T00000'),5))+1)) AS TEMNO FROM SAPHEE.ZPST0002 WHERE MANDT = '"+mandt+"' AND LIFNR NOT LIKE '0%' ";
				sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
				db = new DatasetSqlExecutor(getConnection(db_con));
				result = db.query(sqlRequest);
				returnDs = (Dataset)result.getResultObject();
				temno = returnDs.getColumnAsString(0, "TEMNO");
				ds_list.setColumn(i, "TEMNO", temno);
				
				zpst001.setRowIndex(i);
				executor.execute(zpst001);
			}
			zpst002.setRowIndex(i);
			executor.execute(zpst002);
			zpst002u.setRowIndex(i);
			executor.execute(zpst002u);
		}

		Connection conn = null;
		String db_conn = "jdbc/cs";
	    conn = getConnection(db_conn);
        if( conn == null ) return;
        conn.setAutoCommit(false);
		try
	    {
			//HDCS.EM_TRAN 테이블에 N건 INSERT한다.
			StringBuffer               sqlBuff  = new StringBuffer();
			LoggablePreparedStatement  pstmt    = null;
	
			sqlBuff.append(" INSERT INTO HDCS.EM_TRAN(            \n");
			sqlBuff.append("       TRAN_PR                        \n");
			sqlBuff.append(" 	   , TRAN_ID                        \n");
			sqlBuff.append(" 	   , TRAN_PHONE                     \n");
			sqlBuff.append(" 	   , TRAN_CALLBACK                  \n");
			sqlBuff.append(" 	   , TRAN_STATUS                    \n");
			sqlBuff.append(" 	   , TRAN_DATE                      \n");
			sqlBuff.append(" 	   , TRAN_MSG                       \n");
			sqlBuff.append(" 	   , TRAN_TYPE                      \n");
			sqlBuff.append(" 	   , TRAN_ETC1                      \n");
			sqlBuff.append(" 	   , TRAN_ETC2                      \n");
			sqlBuff.append("     ) VALUES (                       \n");
			sqlBuff.append("       NEXTVAL FOR HDCS.SEQ_EM_TRANS  \n");
			sqlBuff.append(" 	   , ''                             \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ''                              \n");
			sqlBuff.append(" 	   , '1'                            \n");
			sqlBuff.append(" 	   , HDCS.TO_DATETIME()             \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , 1                              \n");
			sqlBuff.append(" 	   , 'PS_INWON'                      \n");
			sqlBuff.append(" 	   , ''                              \n");
			sqlBuff.append("  )                                   \n");
	
			//EM_TRAN에 INSERT 
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for(int i=0; i<ds_list.getRowCount(); i++) {
				String msg = ds_list.getColumnAsString(i, "NAME1")+" "+ds_list.getColumnAsString(i, "NAMEE")+"님 승인 완료"; 
//				pstmt.setString(1, ds_list.getColumnAsString(i, "PICTUNM")); //대표 핸드폰번호
				pstmt.setString(1, "01074071287"); //대표 핸드폰번호
//				pstmt.setString(1, "01084029246"); //대표 핸드폰번호
				pstmt.setString(2, msg); //SMS내용
			    pstmt.addBatch();
			}
			pstmt.executeBatch();
	        conn.commit();
	    }
		catch(SQLException se)
	    {
		    System.out.println("\n" +  se.toString());
		    se.printStackTrace();
		    try
		    {
		    	conn.rollback();
		        BatchConstants.close(null,null,conn);
		    }
		    catch(Exception e)
		    {
		    }
	    }    
	}

	public void sendback(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mandt = ctx.getInputVariable("G_MANDT");
		String userId = ctx.getInputVariable("G_USER_ID");
		
		String db_con = Utillity.getSapJndi(mandt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		DatasetSqlRequest zpst002u = SqlMapManagerUtility.makeSqlRequestForDataset("ps13:PS1301005A_U1");

		zpst002u.addParamObject("ds_list", ds_list);
		zpst002u.addParamObject("G_MANDT", mandt);
		zpst002u.addParamObject("G_USER_ID", userId);

		for(int i=0; i<ds_list.getRowCount(); i++) {
			zpst002u.setRowIndex(i);
			executor.execute(zpst002u);
		}

		Connection conn = null;
		String db_conn = "jdbc/cs";
	    conn = getConnection(db_conn);
        if( conn == null ) return;
        conn.setAutoCommit(false);
		try
	    {
			//HDCS.EM_TRAN 테이블에 N건 INSERT한다.
			StringBuffer               sqlBuff  = new StringBuffer();
			LoggablePreparedStatement  pstmt    = null;
	
			sqlBuff.append(" INSERT INTO HDCS.EM_TRAN(            \n");
			sqlBuff.append("       TRAN_PR                        \n");
			sqlBuff.append(" 	   , TRAN_ID                        \n");
			sqlBuff.append(" 	   , TRAN_PHONE                     \n");
			sqlBuff.append(" 	   , TRAN_CALLBACK                  \n");
			sqlBuff.append(" 	   , TRAN_STATUS                    \n");
			sqlBuff.append(" 	   , TRAN_DATE                      \n");
			sqlBuff.append(" 	   , TRAN_MSG                       \n");
			sqlBuff.append(" 	   , TRAN_TYPE                      \n");
			sqlBuff.append(" 	   , TRAN_ETC1                      \n");
			sqlBuff.append(" 	   , TRAN_ETC2                      \n");
			sqlBuff.append("     ) VALUES (                       \n");
			sqlBuff.append("       NEXTVAL FOR HDCS.SEQ_EM_TRANS  \n");
			sqlBuff.append(" 	   , ''                             \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ''                              \n");
			sqlBuff.append(" 	   , '1'                            \n");
			sqlBuff.append(" 	   , HDCS.TO_DATETIME()             \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , 1                              \n");
			sqlBuff.append(" 	   , 'PS_INWON'                      \n");
			sqlBuff.append(" 	   , ''                              \n");
			sqlBuff.append("  )                                   \n");
	
			//EM_TRAN에 INSERT 
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for(int i=0; i<ds_list.getRowCount(); i++) {
				if(!ds_list.getColumnAsString(i, "RETIF").equals("X"))
				{	
					String msg = ds_list.getColumnAsString(i, "NAME1")+" "+ds_list.getColumnAsString(i, "NAMEE")+"님 승인 거부! 관리자 문의 바랍니다."; 
//					pstmt.setString(1, ds_list.getColumnAsString(i, "PICTUNM")); //대표 핸드폰번호
					pstmt.setString(1, "01074071287"); //대표 핸드폰번호
//					pstmt.setString(1, "01084029246"); //대표 핸드폰번호
					pstmt.setString(2, msg); //SMS내용
				    pstmt.addBatch();
				}    
			}
			pstmt.executeBatch();
	        conn.commit();
	    }
		catch(SQLException se)
	    {
		    System.out.println("\n" +  se.toString());
		    se.printStackTrace();
		    try
		    {
		    	conn.rollback();
		        BatchConstants.close(null,null,conn);
		    }
		    catch(Exception e)
		    {
		    }
	    }    
	}	
}
