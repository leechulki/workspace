package com.helco.xf.ps04.ws;

import java.sql.Connection;
import java.sql.SQLException;

import com.tobesoft.platform.data.Dataset;

import com.helco.xf.cs12.batch.BatchConstants;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;

public class PS0402001A_ServiceImpl extends AbstractBusinessService implements PS0402001A_Service {
	public void sendMsg(BusinessContext ctx, Dataset ds_list) throws Exception {
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
			sqlBuff.append(" 	   , 'PS_KITA'                      \n");
			sqlBuff.append(" 	   , ''                              \n");
			sqlBuff.append("  )                                   \n");
	
			//EM_TRAN에 INSERT 
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for(int i=0; i<ds_list.getRowCount(); i++) {
				pstmt.setString(1, ds_list.getColumnAsString(i, "CELLP")); //대표 핸드폰번호
//				pstmt.setString(1, "01084029246"); //대표 핸드폰번호
				pstmt.setString(2, ds_list.getColumnAsString(i, "MSG")); //SMS내용
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
}
