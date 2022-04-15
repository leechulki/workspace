package com.helco.xf.cs39.ws;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.sql.SQLException;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.vo.TBEBXLF1Vo;
import com.helco.xf.cs12.ws.CS1299001A_ServiceImpl;
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
import com.helco.xf.cs12.evladm.dbutil.*;
import com.helco.xf.cs18.ws.CS1801001A_Service;

public class CS3902001A_ACT extends AbstractAction {

	/**
	 * 대수 테스트2
	 * @param ctx
	 * @throws Exception
	 */
	public void count(BusinessContext ctx) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list = ctx.getOutputDataset("ds_list");
		Dataset ds_list2 = ctx.getOutputDataset("ds_list2");
		
		String flag1 = ds_cond.getColumnAsString(0, "FLAG1");
		String flag2 = ds_cond.getColumnAsString(0, "FLAG2");
		String flag3 = ds_cond.getColumnAsString(0, "FLAG3");
		String flag4 = ds_cond.getColumnAsString(0, "FLAG4");
		String flag5 = ds_cond.getColumnAsString(0, "FLAG5");
		String flag6 = ds_cond.getColumnAsString(0, "FLAG6");
		String flag7 = ds_cond.getColumnAsString(0, "FLAG7");
		String flag8 = ds_cond.getColumnAsString(0, "FLAG8");
		String flag9 = ds_cond.getColumnAsString(0, "FLAG9");
		String flag10 = ds_cond.getColumnAsString(0, "FLAG10");
		String flag11 = ds_cond.getColumnAsString(0, "FLAG11");
		String flag12 = ds_cond.getColumnAsString(0, "FLAG12");
		String flag13 = ds_cond.getColumnAsString(0, "FLAG13");
		String flag14 = ds_cond.getColumnAsString(0, "FLAG14");
		String flag15 = ds_cond.getColumnAsString(0, "FLAG15");
		String flag_q = ds_cond.getColumnAsString(0, "FLAG_Q");
	
		
		DatasetSqlRequest zcst950d = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_D1");
		zcst950d.addParamObject("ds_cond", ds_cond);
		
		if(flag_q.equals("")) executor.execute(zcst950d);
		
		DatasetSqlRequest zcst950d2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_D2");
		zcst950d2.addParamObject("ds_cond", ds_cond);
		
		if(flag_q.equals("")) executor.execute(zcst950d2);	
		
		DatasetSqlRequest zcst950i = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_I1");
		zcst950i.addParamObject("ds_cond", ds_cond);
		
		if(flag_q.equals("")) executor.execute(zcst950i);		

		
		
		// 인수
		DatasetSqlRequest zcst950u = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U16");
		zcst950u.addParamObject("ds_cond", ds_cond);
		
		//if(flag1.equals("X")) executor.execute(zcst950u);	
		if(flag1.equals("1")) {
			saveInsu(ctx);	
			executor.execute(zcst950u);	
		}

		// 무상만료
		DatasetSqlRequest zcst950u2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U2");
		zcst950u2.addParamObject("ds_cond", ds_cond);
		
		if(flag2.equals("1"))executor.execute(zcst950u2);		
		
		// 미개시
		DatasetSqlRequest zcst950u3 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U3");
		zcst950u3.addParamObject("ds_cond", ds_cond);
		
		if(flag3.equals("1"))executor.execute(zcst950u3);	

		// 무상일반
		DatasetSqlRequest zcst950u4 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U4");
		zcst950u4.addParamObject("ds_cond", ds_cond);
		
		if(flag4.equals("1"))executor.execute(zcst950u4);	

		// 전환계약
//		DatasetSqlRequest zcst950u5 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U5");
		DatasetSqlRequest zcst950u5 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U17");
		zcst950u5.addParamObject("ds_cond", ds_cond);
		
		if(flag5.equals("1")) {
			saveConversion(ctx);	
			executor.execute(zcst950u5);	
		}

		// 전환펜딩
		//DatasetSqlRequest zcst950u6 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U6");
		DatasetSqlRequest zcst950u6 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U18");
		zcst950u6.addParamObject("ds_cond", ds_cond);
		
		if(flag6.equals("1")) {
			saveConversionPending(ctx);	
			executor.execute(zcst950u6);	
		}
		// 갱신
		DatasetSqlRequest zcst950u7 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U7");
		zcst950u7.addParamObject("ds_cond", ds_cond);
		
		if(flag7.equals("1"))executor.execute(zcst950u7);	
		// 갱신펜딩
		DatasetSqlRequest zcst950u8 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U8");
		zcst950u8.addParamObject("ds_cond", ds_cond);
				
		if(flag8.equals("1"))executor.execute(zcst950u8);	
		
		// 펜딩 추가 집계 
		String yearchk = ds_cond.getColumnAsString(0, "YEARCHK");
		DatasetSqlRequest zcst950u8Add;
		if(yearchk.equals("X")){
			zcst950u8Add = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U8ADD");	
			zcst950u8Add.addParamObject("ds_cond", ds_cond);
			if(flag8.equals("1"))executor.execute(zcst950u8Add);
		}
		// 유상만료
		DatasetSqlRequest zcst950u9 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U9");
		zcst950u9.addParamObject("ds_cond", ds_cond);
		
		if(flag9.equals("1"))executor.execute(zcst950u9);	
		// 교체프로젝트(리모델링)
		DatasetSqlRequest zcst950u10 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U10");
		zcst950u10.addParamObject("ds_cond", ds_cond);
		
		if(flag10.equals("1"))executor.execute(zcst950u10);	
		// 실패
		//DatasetSqlRequest zcst950u11 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U11");
		DatasetSqlRequest zcst950u11 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U11B");
		zcst950u11.addParamObject("ds_cond", ds_cond);

		if(flag11.equals("1")) {
			saveFail(ctx);	
			executor.execute(zcst950u11);	
		}	
		// 회수
		//DatasetSqlRequest zcst950u12 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U12");
		DatasetSqlRequest zcst950u12 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U12B");
		zcst950u12.addParamObject("ds_cond", ds_cond);

		if(flag12.equals("1")) {
			saveRecapture(ctx);	
			executor.execute(zcst950u12);	
		}	
		
		// 이관대수
		DatasetSqlRequest zcst950u13 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U13");
		zcst950u13.addParamObject("ds_cond", ds_cond);
		
		if(flag13.equals("1"))executor.execute(zcst950u13);	
		// 유상일반
		//DatasetSqlRequest zcst950u14 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U14");
		DatasetSqlRequest zcst950u14 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U19");
		zcst950u14.addParamObject("ds_cond", ds_cond);
		
		if(flag14.equals("1")) {
			saveUsangIban(ctx);	
			executor.execute(zcst950u14);	
		}	
		// 기초대수 
		DatasetSqlRequest zcst950u15 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U15");
		zcst950u15.addParamObject("ds_cond", ds_cond);
		
		if(flag15.equals("1"))executor.execute(zcst950u15);	
		
		//최초계약여부
		DatasetSqlRequest zcst950u20 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_U20");
		zcst950u20.addParamObject("ds_cond", ds_cond);
		
		if(flag15.equals("1"))executor.execute(zcst950u20);	
				
		DatasetSqlRequest zcst950s = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_S1");
		zcst950s.addParamObject("ds_cond", ds_cond);
		
		DatasetSqlRequest zcst950s6 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_S6");
		zcst950s6.addParamObject("ds_cond", ds_cond);
				
		ds_list = (Dataset)executor.query(zcst950s).getResultObject();	
		ds_list2 = (Dataset)executor.query(zcst950s6).getResultObject();	
		
		ctx.addOutputDataset(ds_list);
		ctx.addOutputDataset(ds_list2);
		
	}
	public void saveUsangIban(BusinessContext ctx) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list = ctx.getInputDataset("ds_list");

		String yearchk = ds_cond.getColumnAsString(0, "YEARCHK");
		DatasetSqlRequest zcst951s5;
		if(yearchk.equals("X")){
			zcst951s5 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_S5N");	
		} else {
			zcst951s5 = SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_S5");	
		}
		zcst951s5.addParamObject("ds_cond", ds_cond);
		
		ds_list = (Dataset)executor.query(zcst951s5).getResultObject();
		
		CS1299001A_ServiceImpl sto;
		sto = new CS1299001A_ServiceImpl();
		sto.getSTO(ds_list);
	
		Connection conn = null;
	    conn = getConnection(db_con);
	    
        if( conn == null ) return;
        conn.setAutoCommit(false);
	    
		PreparedStatement  pstmt    = null ;
	    StringBuffer               sqlBuff  = new StringBuffer(); 
	           
	    try { 
			sqlBuff.append(" INSERT INTO SAPHEE.ZCST951N(         \n");
			sqlBuff.append("       MANDT                      \n");
			sqlBuff.append(" 	   , YYYYMM                   \n");
			sqlBuff.append(" 	   , PJT                      \n");
			sqlBuff.append(" 	   , HNO                      \n");
			sqlBuff.append(" 	   , GUBUN                    \n");
			sqlBuff.append(" 	   , ARA                      \n");
			sqlBuff.append(" 	   , BSU                      \n");
			sqlBuff.append(" 	   , STO                      \n");
			sqlBuff.append(" 	   , ZSPEC3                   \n");
			sqlBuff.append(" 	   , ABG                      \n");
			sqlBuff.append(" 	   , CNT_L                    \n");
			sqlBuff.append(" 	   , CNT_S                    \n");
			sqlBuff.append(" 	   , GNO                      \n");
			sqlBuff.append(" 	   , FM                       \n");
			sqlBuff.append(" 	   , HRTS                       \n");
			sqlBuff.append(" 	   , HEP                       \n");
			sqlBuff.append(" 	   , SLA                       \n");
			sqlBuff.append(" 	   , USD                       \n");
			sqlBuff.append(" 	   , UMR                       \n");
			sqlBuff.append(" 	   , ADT                       \n");
			sqlBuff.append(" 	   , TGB                       \n");
			sqlBuff.append(" 	   , ERDAT                      \n");
			sqlBuff.append(" 	   , ERZZT                      \n");
			
			sqlBuff.append("     ) VALUES (                         \n");
			sqlBuff.append(" 	     ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , HEX(CURRENT DATE)                              \n");
			sqlBuff.append(" 	   , HEX(CURRENT TIME)                              \n");
			sqlBuff.append("  )                                     \n");
	
         pstmt = conn.prepareStatement(sqlBuff.toString());
            
         for (int i = 0; i < ds_list.getRowCount(); i++) {
        	int idxparam = 1;
			
        	pstmt.setString(idxparam++, mdt); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "YYYYMM")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "PJT")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HNO")); 
        	pstmt.setString(idxparam++, "TCQ");
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ARA")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "BSU")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "STO")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ZSPEC3")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ABG")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_L")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_S")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "GNO")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "KND")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HYN")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HEP")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "SLA")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "USD")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "UMR")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ADT")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "TGB")); 
			
	            pstmt.addBatch();
	            pstmt.clearParameters();
		         if(i % 1000 == 0){
			         pstmt.executeBatch();
			         pstmt.clearBatch();
			         conn.commit();
	         	}
	         }
	         
	         pstmt.executeBatch();  
	         conn.commit();
	      
	      } catch(SQLException ex) {
	    	  while (ex != null) {
	    			System.out.println("Message:   "
	    	                                   + ex.getMessage ());
	    			System.out.println("SQLState:  "
	    	                                   + ex.getSQLState ());
	    			System.out.println("ErrorCode: "
	    	                                   + ex.getErrorCode ());
	    			ex = ex.getNextException();
	    			System.out.println("");
	    			throw new BizException("유상일반 집계 실행을 중지 합니다.");
	    		}    	  
	      }finally{
	    	  
	         try{if (pstmt != null) pstmt.close();}catch(Exception e){}
	      }
	}

	public void saveRecapture(BusinessContext ctx) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list = ctx.getInputDataset("ds_list");

		DatasetSqlRequest zcst951s7
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_S8");
		zcst951s7.addParamObject("ds_cond", ds_cond);	
		
		ds_list = (Dataset)executor.query(zcst951s7).getResultObject();
		
		CS1299001A_ServiceImpl sto;
		sto = new CS1299001A_ServiceImpl();
		sto.getSTO(ds_list);
		Connection conn = null;
	    conn = getConnection(db_con);
	    
        if( conn == null ) return;
        conn.setAutoCommit(false);
	    
		PreparedStatement  pstmt    = null ;
	    StringBuffer               sqlBuff  = new StringBuffer(); 
	           
	    try { 
			sqlBuff.append(" INSERT INTO SAPHEE.ZCST951N(         \n");
			sqlBuff.append("       MANDT                      \n");
			sqlBuff.append(" 	   , YYYYMM                   \n");
			sqlBuff.append(" 	   , PJT                      \n");
			sqlBuff.append(" 	   , HNO                      \n");
			sqlBuff.append(" 	   , GUBUN                    \n");
			sqlBuff.append(" 	   , ARA                      \n");
			sqlBuff.append(" 	   , BSU                      \n");
			sqlBuff.append(" 	   , STO                      \n");
			sqlBuff.append(" 	   , ZSPEC3                   \n");
			sqlBuff.append(" 	   , ABG                      \n");
			sqlBuff.append(" 	   , CNT_L                    \n");
			sqlBuff.append(" 	   , CNT_S                    \n");
			sqlBuff.append(" 	   , GNO                      \n");
			sqlBuff.append(" 	   , FM                       \n");
			sqlBuff.append(" 	   , HRTS                       \n");
			sqlBuff.append(" 	   , HEP                       \n");
			sqlBuff.append(" 	   , SLA                       \n");
			sqlBuff.append(" 	   , USD                       \n");
			sqlBuff.append(" 	   , UMR                       \n");
			sqlBuff.append(" 	   , ADT                       \n");
			sqlBuff.append(" 	   , TGB                       \n");
			sqlBuff.append(" 	   , ERDAT                      \n");
			sqlBuff.append(" 	   , ERZZT                      \n");
			
			sqlBuff.append("     ) VALUES (                         \n");
			sqlBuff.append(" 	     ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , HEX(CURRENT DATE)                              \n");
			sqlBuff.append(" 	   , HEX(CURRENT TIME)                              \n");
			sqlBuff.append("  )                                     \n");
	
         pstmt = conn.prepareStatement(sqlBuff.toString());
            
         for (int i = 0; i < ds_list.getRowCount(); i++) {
        	int idxparam = 1;
			
        	pstmt.setString(idxparam++, mdt); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "YYYYMM")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "PJT")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HNO")); 
        	pstmt.setString(idxparam++, "RCQ");
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ARA")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "BSU")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "STO")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ZSPEC3")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ABG")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_L")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_S")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "GNO")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "KND")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HYN")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HEP")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "SLA")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "USD")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "UMR")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ADT")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "TGB")); 
			
	            pstmt.addBatch();
	            pstmt.clearParameters();
		         if(i % 1000 == 0){
			         pstmt.executeBatch();
			         pstmt.clearBatch();
			         conn.commit();
	         	}
	         }
	         
	         pstmt.executeBatch();  
	         conn.commit();
	      
	      } catch(SQLException ex) {
	    	  while (ex != null) {
	    			System.out.println("Message:   "
	    	                                   + ex.getMessage ());
	    			System.out.println("SQLState:  "
	    	                                   + ex.getSQLState ());
	    			System.out.println("ErrorCode: "
	    	                                   + ex.getErrorCode ());
	    			ex = ex.getNextException();
	    			System.out.println("");
	    			throw new BizException("회수 집계 실행을 중지 합니다.");
	    		}    	  
	      }finally{
	    	  
	         try{if (pstmt != null) pstmt.close();}catch(Exception e){}
	      }
	}
	public void saveFail(BusinessContext ctx) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list = ctx.getInputDataset("ds_list");

		DatasetSqlRequest zcst951s8
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_S7");
		zcst951s8.addParamObject("ds_cond", ds_cond);
		
		ds_list = (Dataset)executor.query(zcst951s8).getResultObject();
		
		CS1299001A_ServiceImpl sto;
		sto = new CS1299001A_ServiceImpl();
		sto.getSTO(ds_list);
	
		Connection conn = null;
	    conn = getConnection(db_con);
	    
        if( conn == null ) return;
        conn.setAutoCommit(false);
	    
		PreparedStatement  pstmt    = null ;
	    StringBuffer               sqlBuff  = new StringBuffer(); 
	           
	    try { 
			sqlBuff.append(" INSERT INTO SAPHEE.ZCST951N(         \n");
			sqlBuff.append("       MANDT                      \n");
			sqlBuff.append(" 	   , YYYYMM                   \n");
			sqlBuff.append(" 	   , PJT                      \n");
			sqlBuff.append(" 	   , HNO                      \n");
			sqlBuff.append(" 	   , GUBUN                    \n");
			sqlBuff.append(" 	   , ARA                      \n");
			sqlBuff.append(" 	   , BSU                      \n");
			sqlBuff.append(" 	   , STO                      \n");
			sqlBuff.append(" 	   , ZSPEC3                   \n");
			sqlBuff.append(" 	   , ABG                      \n");
			sqlBuff.append(" 	   , CNT_L                    \n");
			sqlBuff.append(" 	   , CNT_S                    \n");
			sqlBuff.append(" 	   , SLA                       \n");
			sqlBuff.append(" 	   , SEL                       \n");
			sqlBuff.append(" 	   , BMT                       \n");
			sqlBuff.append(" 	   , UMR                       \n");
			sqlBuff.append(" 	   , FSD                       \n");
			sqlBuff.append(" 	   , MON                       \n");
			sqlBuff.append(" 	   , TGB                       \n");
			sqlBuff.append(" 	   , ERDAT                      \n");
			sqlBuff.append(" 	   , ERZZT                      \n");
			
			sqlBuff.append("     ) VALUES (                         \n");
			sqlBuff.append(" 	     ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , HEX(CURRENT DATE)                              \n");
			sqlBuff.append(" 	   , HEX(CURRENT TIME)                              \n");
			sqlBuff.append("  )                                     \n");
	
         pstmt = conn.prepareStatement(sqlBuff.toString());
            
         for (int i = 0; i < ds_list.getRowCount(); i++) {
        	int idxparam = 1;
			
        	pstmt.setString(idxparam++, mdt); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "YYYYMM")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "PJT")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HNO")); 
        	pstmt.setString(idxparam++, "FAIL");
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ARA")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "BSU")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "STO")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ZSPEC3")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ABG")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_L")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_S")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "SLA")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "SEL")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "BMT")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "UMR")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "FSD")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "MON")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "TGB")); 
			
	            pstmt.addBatch();
	            pstmt.clearParameters();
		         if(i % 1000 == 0){
			         pstmt.executeBatch();
			         pstmt.clearBatch();
			         conn.commit();
	         	}
	         }
	         
	         pstmt.executeBatch();  
	         conn.commit();
	      
	      } catch(SQLException ex) {
	    	  while (ex != null) {
	    			System.out.println("Message:   "
	    	                                   + ex.getMessage ());
	    			System.out.println("SQLState:  "
	    	                                   + ex.getSQLState ());
	    			System.out.println("ErrorCode: "
	    	                                   + ex.getErrorCode ());
	    			ex = ex.getNextException();
	    			System.out.println("");
	    			throw new BizException("실패 집계 실행을 중지 합니다.");
	    		}    	  
	      }finally{
	    	  
	         try{if (pstmt != null) pstmt.close();}catch(Exception e){}
	      }
	}
	/**
	/**
	 * 대수 테스트
	 * @param ctx
	 * @throws Exception
	 */
	public void saveInsu(BusinessContext ctx) throws Exception {

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list = ctx.getInputDataset("ds_list");

		DatasetSqlRequest zcst186s
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_S2");
		zcst186s.addParamObject("ds_cond", ds_cond);
		
		ds_list = (Dataset)executor.query(zcst186s).getResultObject();
		
		CS1299001A_ServiceImpl sto;
		sto = new CS1299001A_ServiceImpl();
		sto.getSTO(ds_list);
	
		Connection conn = null;
	    conn = getConnection(db_con);
	    
        if( conn == null ) return;
        conn.setAutoCommit(false);
	    
		PreparedStatement  pstmt    = null ;
	    StringBuffer               sqlBuff  = new StringBuffer(); 
	           
        try { 
				sqlBuff.append(" INSERT INTO SAPHEE.ZCST951N(         \n");
				sqlBuff.append("       MANDT                      \n");
				sqlBuff.append(" 	   , YYYYMM                   \n");
				sqlBuff.append(" 	   , PJT                      \n");
				sqlBuff.append(" 	   , HNO                      \n");
				sqlBuff.append(" 	   , GUBUN                    \n");
				sqlBuff.append(" 	   , ARA                      \n");
				sqlBuff.append(" 	   , BSU                      \n");
				sqlBuff.append(" 	   , STO                      \n");
				sqlBuff.append(" 	   , ZSPEC3                   \n");
				sqlBuff.append(" 	   , ABG                      \n");
				sqlBuff.append(" 	   , CNT_L                    \n");
				sqlBuff.append(" 	   , CNT_S                    \n");
				sqlBuff.append(" 	   , ISD                      \n");
				sqlBuff.append(" 	   , ERDAT                      \n");
				sqlBuff.append(" 	   , ERZZT                      \n");
				
				sqlBuff.append("     ) VALUES (                         \n");
				sqlBuff.append(" 	     ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , HEX(CURRENT DATE)                              \n");
				sqlBuff.append(" 	   , HEX(CURRENT TIME)                              \n");
				sqlBuff.append("  )                                     \n");
		
	         pstmt = conn.prepareStatement(sqlBuff.toString());
	            
	         for (int i = 0; i < ds_list.getRowCount(); i++) {
	        	int idxparam = 1;
				
	        	pstmt.setString(idxparam++, mdt); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "YYYYMM")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "PJT")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HNO")); 
	        	pstmt.setString(idxparam++, "ISQ");
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ARA")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "BSU")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "STO")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ZSPEC3")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ABG")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_L")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_S")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ISD")); 
				
	            pstmt.addBatch();
	            pstmt.clearParameters();
		         if(i % 1000 == 0){
			         pstmt.executeBatch();
			         pstmt.clearBatch();
			         conn.commit();
	         	}
	         }
	         
	         pstmt.executeBatch();  
	         conn.commit();
	      
	      } catch(SQLException ex) {
	    	  while (ex != null) {
	    			System.out.println("Message:   "
	    	                                   + ex.getMessage ());
	    			System.out.println("SQLState:  "
	    	                                   + ex.getSQLState ());
	    			System.out.println("ErrorCode: "
	    	                                   + ex.getErrorCode ());
	    			ex = ex.getNextException();
	    			System.out.println("");
	    			throw new BizException(" 인수 집계 실행을 중지 합니다.");
	    		}

	      }finally{
	    	  
	         try{if (pstmt != null) pstmt.close();}catch(Exception e){}
	      }

	}
	public void saveConversion(BusinessContext ctx) throws Exception {

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list = ctx.getInputDataset("ds_list");

		DatasetSqlRequest zcst951s3
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_S3");
		zcst951s3.addParamObject("ds_cond", ds_cond);
		
		ds_list = (Dataset)executor.query(zcst951s3).getResultObject();
		
		CS1299001A_ServiceImpl sto;
		sto = new CS1299001A_ServiceImpl();
		sto.getSTO(ds_list);
	
		Connection conn = null;
	    conn = getConnection(db_con);
	    
        if( conn == null ) return;
        conn.setAutoCommit(false);
	    
		PreparedStatement  pstmt    = null ;
	    StringBuffer               sqlBuff  = new StringBuffer(); 
	           
        try { 
				sqlBuff.append(" INSERT INTO SAPHEE.ZCST951N(         \n");
				sqlBuff.append("       MANDT                      \n");
				sqlBuff.append(" 	   , YYYYMM                   \n");
				sqlBuff.append(" 	   , PJT                      \n");
				sqlBuff.append(" 	   , HNO                      \n");
				sqlBuff.append(" 	   , GUBUN                    \n");
				sqlBuff.append(" 	   , ARA                      \n");
				sqlBuff.append(" 	   , BSU                      \n");
				sqlBuff.append(" 	   , STO                      \n");
				sqlBuff.append(" 	   , ZSPEC3                   \n");
				sqlBuff.append(" 	   , ABG                      \n");
				sqlBuff.append(" 	   , CNT_L                    \n");
				sqlBuff.append(" 	   , CNT_S                    \n");
				sqlBuff.append(" 	   , ISD                      \n");
				sqlBuff.append(" 	   , BMT                      \n");
				sqlBuff.append(" 	   , FM                       \n");
				sqlBuff.append(" 	   , HRTS                       \n");
				sqlBuff.append(" 	   , TGB                       \n");
				sqlBuff.append(" 	   , USD                       \n");
				sqlBuff.append(" 	   , UMR                       \n");
				sqlBuff.append(" 	   , ADT                       \n");
				sqlBuff.append(" 	   , GNO                       \n");
				sqlBuff.append(" 	   , ERDAT                      \n");
				sqlBuff.append(" 	   , ERZZT                      \n");
				
				sqlBuff.append("     ) VALUES (                         \n");
				sqlBuff.append(" 	     ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , ?                              \n");
				sqlBuff.append(" 	   , HEX(CURRENT DATE)                              \n");
				sqlBuff.append(" 	   , HEX(CURRENT TIME)                              \n");
				sqlBuff.append("  )                                     \n");
		
	         pstmt = conn.prepareStatement(sqlBuff.toString());
	            
	         for (int i = 0; i < ds_list.getRowCount(); i++) {
	        	int idxparam = 1;
				
	        	pstmt.setString(idxparam++, mdt); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "YYYYMM")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "PJT")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HNO")); 
	        	pstmt.setString(idxparam++, "CVQ");
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ARA")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "BSU")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "STO")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ZSPEC3")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ABG")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_L")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_S")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ISD")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "BMT_B")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "KND")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HYN")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "TGB")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "USD")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "UMR")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ADT")); 
				pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "GNO")); 
				
	            pstmt.addBatch();
	            pstmt.clearParameters();
		         if(i % 1000 == 0){
			         pstmt.executeBatch();
			         pstmt.clearBatch();
			         conn.commit();
	         	}
	         }
	         
	         pstmt.executeBatch();  
	         conn.commit();
	      
	      } catch(SQLException ex) {
	    	  while (ex != null) {
	    			System.out.println("Message:   "
	    	                                   + ex.getMessage ());
	    			System.out.println("SQLState:  "
	    	                                   + ex.getSQLState ());
	    			System.out.println("ErrorCode: "
	    	                                   + ex.getErrorCode ());
	    			ex = ex.getNextException();
	    			System.out.println("");
	    			throw new BizException("전환계약 집계 실행을 중지 합니다.");
	    		}

	      }finally{
	    	  
	         try{if (pstmt != null) pstmt.close();}catch(Exception e){}
	      }	
	}
	public void saveConversionPending(BusinessContext ctx) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_list = ctx.getInputDataset("ds_list");

		DatasetSqlRequest zcst951s3
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs39:CS3902001A_S4");
		zcst951s3.addParamObject("ds_cond", ds_cond);
		
		ds_list = (Dataset)executor.query(zcst951s3).getResultObject();
		
		CS1299001A_ServiceImpl sto;
		sto = new CS1299001A_ServiceImpl();
		sto.getSTO(ds_list);
	
		Connection conn = null;
	    conn = getConnection(db_con);
	    
        if( conn == null ) return;
        conn.setAutoCommit(false);
	    
		PreparedStatement  pstmt    = null ;
	    StringBuffer               sqlBuff  = new StringBuffer(); 
	           
	    try { 
			sqlBuff.append(" INSERT INTO SAPHEE.ZCST951N(         \n");
			sqlBuff.append("       MANDT                      \n");
			sqlBuff.append(" 	   , YYYYMM                   \n");
			sqlBuff.append(" 	   , PJT                      \n");
			sqlBuff.append(" 	   , HNO                      \n");
			sqlBuff.append(" 	   , GUBUN                    \n");
			sqlBuff.append(" 	   , ARA                      \n");
			sqlBuff.append(" 	   , BSU                      \n");
			sqlBuff.append(" 	   , STO                      \n");
			sqlBuff.append(" 	   , ZSPEC3                   \n");
			sqlBuff.append(" 	   , ABG                      \n");
			sqlBuff.append(" 	   , CNT_L                    \n");
			sqlBuff.append(" 	   , CNT_S                    \n");
			sqlBuff.append(" 	   , BMT                      \n");
			sqlBuff.append(" 	   , ERDAT                      \n");
			sqlBuff.append(" 	   , ERZZT                      \n");
			
			sqlBuff.append("     ) VALUES (                         \n");
			sqlBuff.append(" 	     ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , ?                              \n");
			sqlBuff.append(" 	   , HEX(CURRENT DATE)                              \n");
			sqlBuff.append(" 	   , HEX(CURRENT TIME)                              \n");
			sqlBuff.append("  )                                     \n");
	
         pstmt = conn.prepareStatement(sqlBuff.toString());
            
         for (int i = 0; i < ds_list.getRowCount(); i++) {
        	int idxparam = 1;
			
        	pstmt.setString(idxparam++, mdt); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "YYYYMM")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "PJT")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "HNO")); 
        	pstmt.setString(idxparam++, "CPQ");
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ARA")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "BSU")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "STO")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ZSPEC3")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "ABG")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_L")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "CNT_S")); 
			pstmt.setString(idxparam++, ds_list.getColumnAsString(i, "BMT_B")); 
			
	            pstmt.addBatch();
	            pstmt.clearParameters();
		         if(i % 1000 == 0){
			         pstmt.executeBatch();
			         pstmt.clearBatch();
			         conn.commit();
	         	}
	         }
	         
	         pstmt.executeBatch();  
	         conn.commit();
	      
	      } catch(SQLException ex) {
	    	  while (ex != null) {
	    			System.out.println("Message:   "
	    	                                   + ex.getMessage ());
	    			System.out.println("SQLState:  "
	    	                                   + ex.getSQLState ());
	    			System.out.println("ErrorCode: "
	    	                                   + ex.getErrorCode ());
	    			ex = ex.getNextException();
	    			System.out.println("");
	    			throw new BizException("전환 Pending 집계 실행을 중지 합니다.");
	    		}
	      }finally{
	    	  
	         try{if (pstmt != null) pstmt.close();}catch(Exception e){}
	      }
	}
	
}
