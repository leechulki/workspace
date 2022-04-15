/**
 * 파 일 명 : ProcTBEBWZF1.java
 * 설    명 : 점검이관배치 처리
 *            시스템의 cron job에 등록되어 매월 1일 새벽에  실행 됨.
 *            로그파일 위치는 실행 파일인 bwz_batch.sh 또는 bwz_batch.bat 파일의 LOG_DIR 환경변수에 세팅되어있으며
 *            기본 값으로는 Current폴더의 logs라는 서브폴더로 정의
 *            예) set LOG_DIR = ./logs
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-05-24 오전 8:46:06
 * 변경내역 :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.cs12.batch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWMF1Vo;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

import tit.service.core.logger.Logger;
import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.business.config.ConfigUtility;
import tit.service.resource.ResourceFactory;
import tit.service.resource.TransactionResource;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.StringOperator;
import tit.util.TitUtility;
import tit.service.scheduler.ScheduleTask;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.qm04.ws.ZQMS027;
import com.helco.xf.qm04.ws.ZQMS027T;
import com.tobesoft.platform.data.Dataset;

public class ProcTBEBWZF1 extends AbstractBusinessService implements ScheduleTask
{
   static Logger logger; 
   static String max_seq = "";
   static String seq = "";
   String mandt = "183";
   private String mName = null;
   
   public void run() throws Exception {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
      System.out.println(format.format(new Date()) + ": " + mName);
        
      logger = ServiceManagerFactory.getLogger();
        
      logger.debug("\n점검이관 배치 프로세스 Start : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
      Connection conn = null;

      ArrayList list = new ArrayList();
      ArrayList list2 = new ArrayList();
      TBEBWZF1Vo vo = new TBEBWZF1Vo();
      TBEBWMF1Vo vo2 = new TBEBWMF1Vo();
    
      TransForBWE bwe = new TransForBWE(logger); //ZCST116
      TransForBWM bwm = new TransForBWM(logger); //ZCST126
      TransForBXL bxl = new TransForBXL(logger); //ZCST131
      TransForBXR bxr = new TransForBXR(logger); //ZCST136
      TransForBWA bwa = new TransForBWA(logger); //ZCST111, ZCST101
      TransForBSG bsg = new TransForBSG(logger); //ZCST162
      TransForBSC bsc = new TransForBSC(logger); //ZMASTER01, ZMASTER02, ZCST123, ZCST127, ZCST141
	  TransForBWZ bwz = new TransForBWZ(logger); //ZCST157, PRPS

	  try
      {
		 String db_con = Utillity.getSapJndi(mandt);
	     conn = getConnection(db_con);
//	     conn = getConnection("jdbc/heq");
//	     conn = getConnection("jdbc/hep");

         if( conn == null ) return;
         conn.setAutoCommit(false);
         
         list = getBWZ(mandt, CommonUtil.getToday2(), conn);

         for(int i=0; i<list.size(); i++)
         {
            vo = new TBEBWZF1Vo();
            vo = (TBEBWZF1Vo) list.get(i);

            max_seq = "";
            seq = "";
            if(isExistYusangBosu(mandt, vo, BatchConstants.YU_ILBAN, conn))
            {
               //a-2. 유상일반보수계약에 대한 점검이관을 처리
               bwm.ProcTransForBWM(mandt, conn, vo, BatchConstants.YU_ILBAN, seq, max_seq);
            }
            else if(isExistYusangBosu(mandt, vo, BatchConstants.YU_GONGSA, conn))
            {
               //a-2. 유상공사보수계약에 대한 점검이관을 처리
               bwm.ProcTransForBWM(mandt, conn, vo, BatchConstants.YU_GONGSA, seq, max_seq);
            }
            else if(isExistMusangBosu(mandt, vo, BatchConstants.MU_ILBAN, conn))
            {
               //a-1. 무상일반정보에 대한 점검이관을 처리
               bwe.ProcTransForBWE(mandt, conn, vo, BatchConstants.MU_ILBAN, seq, max_seq);
            }
            else if(isExistMusangBosu(mandt, vo, BatchConstants.MU_GONGSA, conn))
            {
               //a-1. 무상공사정보에 대한 점검이관을 처리
               bwe.ProcTransForBWE(mandt, conn, vo, BatchConstants.MU_GONGSA, seq, max_seq);
            }
            else
            {
               //2006.09.09 점검이관 실패결과 저장 반영 로직 by ohb 
			   vo.setCebwziju(DateTime.getDateString("yyyyMMdd"));
			   vo.setCebwzrlt("N");
			   vo.setCebwzrsn("계약정보 미존재");
			   //vo.setCebwzigm(CommonUtil.getToday2().substring(0,6));
			   bwz.ProcTransForBWZ(mandt, conn, vo);
               
               logger.debug("\n!!! 계약정보 미존재 : upn:"+vo.getCebwzupn()+ ", cst:"+vo.getCebwzcst()+", pjt:"+vo.getCebwzpjt()+", hno:"+vo.getCebwzhno());
               continue;           
            }
            // b-1. 기성비계획 처리
            bxr.ProcTransForBXR(mandt, conn, vo, seq);
            
            // b-2. 매출계획 처리 (유상공사 또는 유상일반 일때만)
            if("C".equals(vo.getCebwzgnd()) || "D".equals(vo.getCebwzgnd()))
               bxl.ProcTransForBXL(mandt, conn, vo, seq);
            
            // c. 호기마스터정보 갱신
            bwa.ProcTransForBWA(mandt, conn, vo);
            bwa.ProcTransForBWA2(mandt, conn, vo);
            // d. 협력사(점검자이력) 갱신
            bsg.ProcTransForBSG(mandt, conn, vo);
            // e. 호기관리(설치)정보 갱신
            bsc.ProcTransForBSC(mandt, conn, vo);
            bsc.ProcTransForBSC2(mandt, conn, vo);
            bsc.ProcTransForBSC3(mandt, conn, vo);
            bsc.ProcTransForBSC4(mandt, conn, vo);
            //bsc.ProcTransForBSC5(mandt, conn, vo);
            
			//2006.09.09 점검이관 성공 결과 저장 반영 로직 by ohb 
			vo.setCebwziju(DateTime.getDateString("yyyyMMdd"));
			vo.setCebwzrlt("Y");
			vo.setCebwzrsn("");
			//vo.setCebwzigm(CommonUtil.getToday2().substring(0,6));
			// f. 점검이관정보 갱신
			bwz.ProcTransForBWZ(mandt, conn, vo);
			// g. WBS (작업분할구조) 요소 마스터 갱신
			bwz.ProcTransForPRPS(mandt, conn);

			try 
			{
				list2 = getSO(mandt, conn, vo);
				
	            Dataset dsError = null;
	            SapFunction stub = null;
	            ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
	            Object obj[] = new Object[]{};
	            
				for(int j=0; j<list2.size(); j++)
		        {
		            vo2 = new TBEBWMF1Vo();
		            vo2 = (TBEBWMF1Vo) list2.get(j);

					obj = new Object[]{
							vo2.getLifnr()
							, vo2.getVbeln()
							, listMsg
					};

					logger.debug("vo2.getLifnr() ==> [" + vo2.getLifnr() + "]");
					logger.debug("vo2.getVbeln() ==> [" + vo2.getVbeln() + "]");

					// SAP Call
					//stub = CallSAP.callSap(mandt, "com.helco.xf.cs12.batch.ZWEB_CS_SET_COWORKER", obj);
					stub = CallSAP.callSap("HEP", mandt, "com.helco.xf.cs12.batch.ZWEB_CS_SET_COWORKER", obj, false);		 // 기존  SAP 연결
					//stub = CallSAP.callSapEai("HEP", mandt, "com.helco.xf.cs12.batch.ZWEB_CS_SET_COWORKER", obj, false);  // EAI 연결

					

					listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
					dsError = CallSAP.makeErrorInfo( listMsg );

					if ( dsError.getRowCount() > 0 ) {
						// 에러 메세지 출력
						logger.debug("\n" +  dsError.getColumnAsString(0, "IDX"));
						logger.debug("\n" +  dsError.getColumnAsString(0, "ERRMSG"));
						throw new BizException(dsError.getColumnAsString(0, "IDX") + " : " + dsError.getColumnAsString(0, "ERRMSG"));
					}
		        }
			}
			catch(BizException be)
			{
				// 에러 메세지 출력
				logger.debug("\n" +  be.toString());
				be.printStackTrace();
				try
		         {
		            conn.rollback();
		            BatchConstants.close(null,null,conn);
		         }
		         catch(Exception te)
		         {
		         }
			}
			catch(Exception e)
		    {
		         logger.debug("\n" +  e.toString());
		         e.printStackTrace();
		         try
		         {
		            conn.rollback();
		            BatchConstants.close(null,null,conn);
		         }
		         catch(Exception te)
		         {
		         }
		    } 
         }
         conn.commit();
//         conn.rollback();
      }
      catch(SQLException se)
      {
         logger.debug("\n" +  se.toString());
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
      catch(Exception e)
      {
         logger.debug("\n" +  e.toString());
         e.printStackTrace();
         try
         {
            conn.rollback();
            BatchConstants.close(null,null,conn);
         }
         catch(Exception te)
         {
         }
      }      
      finally
      {
         try
         {
            BatchConstants.close(null,null,conn);
         }
         catch(Exception e)
         {
            e.printStackTrace();
            logger.debug("\n" + e.toString());
         }
      }
      logger.debug("\n점검이관 배치 프로세스 End : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
   }

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	   /**
	    * 작성자 : 이주형
	    * 작성일 : 2008.10.29
	    * 설  명 : 배치프로세스의 기준이 되는 점검이관 대상 정보에 해당하는 VBELN, LIFNR을 가져온다. 
	    * 기  타 :
	    */ 
	   public ArrayList getSO(String mandt, Connection conn, TBEBWZF1Vo vo) throws Exception
	   {  
	      ArrayList rtnList = new ArrayList();
	      
	      StringBuffer               sqlBuff  = new StringBuffer();
	      LoggablePreparedStatement  pstmt    = null;
	      ResultSet                  rs       = null;
	      TBEBWMF1Vo                 rtnVo;
	      
	      sqlBuff.append(" SELECT                                 \n");
	      sqlBuff.append("        A.VBELN,                        \n");
	      sqlBuff.append("        (                               \n");
	      sqlBuff.append("         SELECT                         \n");
	      sqlBuff.append("                VALUE(MAX(B.LIFNR),'') LIFNR \n");
	      sqlBuff.append("           FROM                         \n");
	      sqlBuff.append("                SAPHEE.ZWBT010 B        \n");
	      sqlBuff.append("          WHERE                         \n");
	      sqlBuff.append("                B.MANDT = ?             \n");
	      sqlBuff.append("            AND B.LGORT = ?             \n");
	      sqlBuff.append("        ) LIFNR                         \n");
	      sqlBuff.append("   FROM                                 \n");
	      sqlBuff.append("        SAPHEE.ZCST126 A                \n");
	      sqlBuff.append("  WHERE 1=1                             \n");
	      sqlBuff.append("    AND A.MANDT = ?                     \n");
	      sqlBuff.append("    AND A.CS126_UPN = ?                 \n");
	      sqlBuff.append("    AND A.CS126_CST = ?                 \n");
	      sqlBuff.append("    AND A.CS126_PJT = ?                 \n");
	      sqlBuff.append("    AND A.CS126_HNO = ?                 \n");
	      sqlBuff.append("    AND A.CS126_GND = ?                 \n");
	      sqlBuff.append("    AND A.CS126_UMR >= ?                \n");
	      sqlBuff.append("    AND A.CS126_DDT = ''                \n");
	      sqlBuff.append("    AND A.CS126_PST = 'A6'              \n");
	      sqlBuff.append("    AND A.VBELN > ''                    \n");
	      sqlBuff.append(" GROUP BY                               \n");
	      sqlBuff.append("        A.VBELN                         \n");
	      sqlBuff.append("  WITH UR                               \n");
	      
	      try
	      {
	         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
	         int idxparam = 1;
	         pstmt.setString(idxparam++, mandt);
	         pstmt.setString(idxparam++, vo.getCebwzcha());
	         pstmt.setString(idxparam++, mandt);
	         pstmt.setString(idxparam++, vo.getCebwzupn());
	         pstmt.setString(idxparam++, vo.getCebwzcst());
	         pstmt.setString(idxparam++, vo.getCebwzpjt());
	         pstmt.setString(idxparam++, vo.getCebwzhno());
	         pstmt.setString(idxparam++, vo.getCebwzgnd());
	         pstmt.setString(idxparam++, vo.getCebwzigm() + "01");
	         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());

	         rs = pstmt.executeQuery();
	         while (rs.next())
	         {
	            rtnVo = new TBEBWMF1Vo();
	            rtnVo.setVbeln(rs.getString("VBELN"));
	            rtnVo.setLifnr(rs.getString("LIFNR"));
	            rtnList.add(rtnVo);
	         }
	         logger.debug("\n 점검이관 기준정보(RFC처리 데이터) Select : " + rtnList.size() );
	      }
	      catch (Exception e)
	      {
	         throw e;
	      }
	      finally
	      {
	         try
	         {
	            BatchConstants.close(rs, pstmt, null);
	         }
	         catch (Exception e)
	         {
	         }
	      }      
	      return rtnList;
	   }

   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 배치프로세스의 기준이 되는 점검이관 대상 정보를 가져온다. 
    * 기  타 :
    */ 
   public ArrayList getBWZ(String mandt, String ym, Connection conn) throws Exception
   {  
      ArrayList rtnList = new ArrayList();
      
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      TBEBWZF1Vo                 rtnVo;
/*
      sqlBuff.append(" SELECT                           \n");
      sqlBuff.append("        CS157_UPN,                \n");
      sqlBuff.append("        CS157_CST,                \n");
      sqlBuff.append("        CS157_PJT,                \n");
      sqlBuff.append("        CS157_HNO,                \n");
      sqlBuff.append("        CS157_IGM,                \n");
      sqlBuff.append("        CS157_GND,                \n");
      sqlBuff.append("        CS157_SEQ,                \n");
      sqlBuff.append("        CS157_CHB,                \n");
      sqlBuff.append("        CS157_GBB,                \n");
      sqlBuff.append("        CS157_GHB,                \n");
      sqlBuff.append("        CS157_IKD,                \n");
      sqlBuff.append("        CS157_IKJ,                \n");
      sqlBuff.append("        CS157_CHA,                \n");
      sqlBuff.append("        CS157_GBA,                \n");
      sqlBuff.append("        CS157_GHA,                \n");
      sqlBuff.append("        CS157_IJU,                \n");
      sqlBuff.append("        CS157_IHT,                \n");
      sqlBuff.append("        CS157_RLT,                \n");
      sqlBuff.append("        CS157_RSN                 \n");
      sqlBuff.append("   FROM                           \n");
      sqlBuff.append("        SAPHEE.ZCST157            \n");
      sqlBuff.append("  WHERE 1=1                       \n");
      sqlBuff.append("    AND MANDT = ?                 \n");
      sqlBuff.append("    AND CS157_IGM = ?             \n");
      sqlBuff.append("    AND RTRIM(CS157_IJU) = ''     \n");
      sqlBuff.append(" ORDER BY                         \n");
      sqlBuff.append("        CS157_UPN,                \n");
      sqlBuff.append("        CS157_CST,                \n");
      sqlBuff.append("        CS157_PJT,                \n");
      sqlBuff.append("        CS157_HNO,                \n");
      sqlBuff.append("        CS157_GND,                \n");
      sqlBuff.append("        CS157_SEQ                 \n");
*/
      sqlBuff.append(" SELECT                           \n");
      sqlBuff.append("        CS157_UPN,                \n");
      sqlBuff.append("        CS157_CST,                \n");
      sqlBuff.append("        CS157_PJT,                \n");
      sqlBuff.append("        CS157_HNO,                \n");
      sqlBuff.append("        CS157_IGM,                \n");
      sqlBuff.append("        CS157_GND,                \n");
      sqlBuff.append("        CS157_SEQ,                \n");
      sqlBuff.append("        CS157_CHB,                \n");
      sqlBuff.append("        CS157_GBB,                \n");
      sqlBuff.append("        CS157_GHB,                \n");
      sqlBuff.append("        CS157_JUB,                \n");
      sqlBuff.append("        CS157_BUB,                \n");
      sqlBuff.append("        CS157_IKD,                \n");
      sqlBuff.append("        CS157_IKJ,                \n");
      sqlBuff.append("        CS157_CHA,                \n");
      sqlBuff.append("        CS157_GBA,                \n");
      sqlBuff.append("        CS157_GHA,                \n");
      sqlBuff.append("        CS157_JUA,                \n");
      sqlBuff.append("        CS157_BUA,                \n");
      sqlBuff.append("        CS157_IJU,                \n");
      sqlBuff.append("        CS157_IHT,                \n");
      sqlBuff.append("        CS157_RLT,                \n");
      sqlBuff.append("        CS157_RSN                 \n");
      sqlBuff.append("   FROM                           \n");
      sqlBuff.append("        SAPHEE.ZCST157            \n");
      sqlBuff.append("  WHERE 1=1                       \n");
      sqlBuff.append("    AND MANDT = ?                 \n");
      sqlBuff.append("    AND CS157_IGM = ?             \n");
      sqlBuff.append("    AND SUBSTR(CS157_IKD,1,6) = ? \n");
      sqlBuff.append("    AND RTRIM(CS157_IJU) = ''     \n");
      sqlBuff.append(" ORDER BY                         \n");
      sqlBuff.append("        CS157_UPN,                \n");
      sqlBuff.append("        CS157_CST,                \n");
      sqlBuff.append("        CS157_PJT,                \n");
      sqlBuff.append("        CS157_HNO,                \n");
      sqlBuff.append("        CS157_GND,                \n");
      sqlBuff.append("        CS157_SEQ                 \n");
      sqlBuff.append("  WITH UR                         \n");
      
      try
      {
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
/*
         pstmt.setString(1, mandt);
         pstmt.setString(2, ym.substring(0,6));
*/
         pstmt.setString(1, mandt);
         pstmt.setString(2, ym.substring(0,6));
         pstmt.setString(3, CommonUtil.replace(CommonUtil.addMonthDate(-1), "-", "").substring(0,6));
         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());

         rs = pstmt.executeQuery();
         while (rs.next())
         {
            rtnVo = new TBEBWZF1Vo();
            rtnVo.setCebwzupn(rs.getString("CS157_UPN"));
            rtnVo.setCebwzcst(rs.getString("CS157_CST"));
            rtnVo.setCebwzpjt(rs.getString("CS157_PJT"));
            rtnVo.setCebwzhno(rs.getString("CS157_HNO"));
            rtnVo.setCebwzigm(rs.getString("CS157_IGM"));
            rtnVo.setCebwzgnd(rs.getString("CS157_GND"));
            rtnVo.setCebwzseq(rs.getString("CS157_SEQ"));
            rtnVo.setCebwzchb(rs.getString("CS157_CHB"));
            rtnVo.setCebwzgbb(rs.getString("CS157_GBB"));
            rtnVo.setCebwzghb(rs.getString("CS157_GHB"));
            rtnVo.setCebwzjub(rs.getString("CS157_JUB"));
            rtnVo.setCebwzbub(rs.getString("CS157_BUB"));
            rtnVo.setCebwzikd(rs.getString("CS157_IKD"));
            rtnVo.setCebwzikj(rs.getString("CS157_IKJ"));
            rtnVo.setCebwzcha(rs.getString("CS157_CHA"));
            rtnVo.setCebwzgba(rs.getString("CS157_GBA"));
            rtnVo.setCebwzgha(rs.getString("CS157_GHA"));
            rtnVo.setCebwzjua(rs.getString("CS157_JUA"));
            rtnVo.setCebwzbua(rs.getString("CS157_BUA"));
            rtnVo.setCebwziju(rs.getString("CS157_IJU"));
            rtnVo.setCebwziht(rs.getString("CS157_IHT"));
            rtnVo.setCebwzrlt(rs.getString("CS157_RLT"));
            rtnVo.setCebwzrsn(rs.getString("CS157_RSN"));
            rtnList.add(rtnVo);
         }
         logger.debug("\n Step1. 점검이관 기준정보 Select : " + rtnList.size() );
      }
      catch (Exception e)
      {
         throw e;
      }
      finally
      {
         try
         {
            BatchConstants.close(rs, pstmt, null);
         }
         catch (Exception e)
         {
         }
      }      
      return rtnList;
   }
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.25
    * 설  명 : 해당 자료에 대한 유상일반 정보가 있는지 체크 
    * 기  타 :
    */ 
   public boolean isExistYusangBosu(String mandt, TBEBWZF1Vo vo, String gnd, Connection conn) throws Exception
   {  
      boolean returnFlg = false;

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
/*
      sqlBuff.append(" SELECT                                   \n");
      sqlBuff.append("        BWM1.MANDT,                       \n");
      sqlBuff.append("        BWM1.CS126_UPN,                   \n");
      sqlBuff.append("        BWM1.CS126_CST,                   \n");
      sqlBuff.append("        BWM1.CS126_PJT,                   \n");
      sqlBuff.append("        BWM1.CS126_HNO,                   \n");
      sqlBuff.append("        BWM1.CS126_SEQ,                   \n");
      sqlBuff.append("        BWM1.CS126_USD,                   \n");
      sqlBuff.append("        BWM1.CS126_UHJ,                   \n");
      sqlBuff.append("        BWM1.CS126_PST                    \n");
      sqlBuff.append("   FROM                                   \n");
      sqlBuff.append("        SAPHEE.ZCST126 BWM1,              \n");
      sqlBuff.append("        (                                 \n");
      sqlBuff.append("         SELECT                           \n");
      sqlBuff.append("                MANDT,                    \n");
      sqlBuff.append("                CS126_UPN,                \n");
      sqlBuff.append("                CS126_CST,                \n");
      sqlBuff.append("                CS126_PJT,                \n");
      sqlBuff.append("                CS126_HNO,                \n");
      sqlBuff.append("                MAX(CS126_SEQ) CS126_SEQ  \n");
      sqlBuff.append("           FROM                           \n");
      sqlBuff.append("                SAPHEE.ZCST126            \n");
      sqlBuff.append("         GROUP BY                         \n");
      sqlBuff.append("                MANDT,                    \n");
      sqlBuff.append("                CS126_UPN,                \n");
      sqlBuff.append("                CS126_CST,                \n");
      sqlBuff.append("                CS126_PJT,                \n");
      sqlBuff.append("                CS126_HNO                 \n");
      sqlBuff.append("        ) BWM2                            \n");
      sqlBuff.append("  WHERE 1=1                               \n");
      sqlBuff.append("    AND BWM1.MANDT = BWM2.MANDT           \n");
      sqlBuff.append("    AND BWM1.CS126_UPN = BWM2.CS126_UPN   \n");
      sqlBuff.append("    AND BWM1.CS126_CST = BWM2.CS126_CST   \n");
      sqlBuff.append("    AND BWM1.CS126_PJT = BWM2.CS126_PJT   \n");
      sqlBuff.append("    AND BWM1.CS126_HNO = BWM2.CS126_HNO   \n");
      sqlBuff.append("    AND BWM1.CS126_SEQ = BWM2.CS126_SEQ   \n");
      sqlBuff.append("    AND BWM1.MANDT     =  ?               \n");
      sqlBuff.append("    AND BWM1.CS126_UPN =  ?               \n");
      sqlBuff.append("    AND BWM1.CS126_CST =  ?               \n");
      sqlBuff.append("    AND BWM1.CS126_PJT =  ?               \n");
      sqlBuff.append("    AND BWM1.CS126_HNO =  ?               \n");
      sqlBuff.append("    AND BWM1.CS126_GND =  ?               \n");
      sqlBuff.append("    AND BWM1.CS126_USD <= ?               \n");
      sqlBuff.append("    AND BWM1.CS126_UHJ >  ?               \n");
      sqlBuff.append("    AND BWM1.CS126_PST =  ?               \n");
*/
      sqlBuff.append(" SELECT                                \n");
      sqlBuff.append("        BWM1.MANDT,                    \n");
      sqlBuff.append("        BWM1.CS126_UPN,                \n");
      sqlBuff.append("        BWM1.CS126_CST,                \n");
      sqlBuff.append("        BWM1.CS126_PJT,                \n");
      sqlBuff.append("        BWM1.CS126_HNO,                \n");
      sqlBuff.append("        MIN(BWM1.CS126_SEQ) CS126_SEQ, \n");
      sqlBuff.append("        BWM2.MAX_SEQ                   \n");
      sqlBuff.append("   FROM                                \n");
      sqlBuff.append("        SAPHEE.ZCST126 BWM1,           \n");
      sqlBuff.append("        (                              \n");
      sqlBuff.append("         SELECT                        \n");
      sqlBuff.append("                MAX(CS126_SEQ) MAX_SEQ \n");
      sqlBuff.append("           FROM                        \n");
      sqlBuff.append("                SAPHEE.ZCST126         \n");
      sqlBuff.append("          WHERE 1=1                    \n");
      sqlBuff.append("            AND MANDT     =  ?         \n");
      sqlBuff.append("            AND CS126_UPN =  ?         \n");
      sqlBuff.append("            AND CS126_CST =  ?         \n");
      sqlBuff.append("            AND CS126_PJT =  ?         \n");
      sqlBuff.append("            AND CS126_HNO =  ?         \n");
      sqlBuff.append("            AND CS126_GND =  ?         \n");
      sqlBuff.append("            AND CS126_PST =  ?         \n");
      sqlBuff.append("        ) BWM2                         \n");
      sqlBuff.append("  WHERE 1=1                            \n");
      sqlBuff.append("    AND BWM1.MANDT     =  ?            \n");
      sqlBuff.append("    AND BWM1.CS126_UPN =  ?            \n");
      sqlBuff.append("    AND BWM1.CS126_CST =  ?            \n");
      sqlBuff.append("    AND BWM1.CS126_PJT =  ?            \n");
      sqlBuff.append("    AND BWM1.CS126_HNO =  ?            \n");
      sqlBuff.append("    AND BWM1.CS126_GND =  ?            \n");
      sqlBuff.append("    AND BWM1.CS126_PST =  ?            \n");
      sqlBuff.append("    AND ((BWM1.CS126_USD <= ? AND BWM1.CS126_UHJ > ?) OR (BWM1.CS126_USD > ?)) \n");
      sqlBuff.append(" GROUP BY                              \n");
      sqlBuff.append("         BWM1.MANDT,                   \n");
      sqlBuff.append("         BWM1.CS126_UPN,               \n");
      sqlBuff.append("         BWM1.CS126_CST,               \n");
      sqlBuff.append("         BWM1.CS126_PJT,               \n");
      sqlBuff.append("         BWM1.CS126_HNO,               \n");
      sqlBuff.append("         BWM2.MAX_SEQ                  \n");
      sqlBuff.append("  WITH UR                              \n");

      try
      {
         int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzupn());
         pstmt.setString(idxparam++, vo.getCebwzcst());
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
         pstmt.setString(idxparam++, gnd);
         pstmt.setString(idxparam++, "A6");
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzupn());
         pstmt.setString(idxparam++, vo.getCebwzcst());
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
         pstmt.setString(idxparam++, gnd);
         pstmt.setString(idxparam++, "A6");
         pstmt.setString(idxparam++, DateTime.getShortDateString());
         pstmt.setString(idxparam++, DateTime.getShortDateString());
         pstmt.setString(idxparam++, DateTime.getShortDateString());
         
         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
            max_seq = rs.getString("MAX_SEQ");
            seq = rs.getString("CS126_SEQ");
            returnFlg = true;
         }
         else
         {
            max_seq = "";
            seq = "";
            returnFlg = false;
         }
      }
      catch (Exception e)
      {
         throw e;
      }
      finally
      {
         try
         {
            BatchConstants.close(rs, pstmt, null);
         }
         catch (Exception e)
         {
         }
      }      
      return returnFlg;
   }

   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.25
    * 설  명 : 해당 자료에 대한 무상일반/공사 정보가 있는지 체크 
    * 기  타 :
    */ 
   public boolean isExistMusangBosu(String mandt, TBEBWZF1Vo vo, String gnd, Connection conn) throws Exception
   {  
      boolean returnFlg = false;

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
/*
      sqlBuff.append(" SELECT                                   \n");
      sqlBuff.append("        BWE1.MANDT,                       \n");
      sqlBuff.append("        BWE1.CS116_PJT,                   \n");
      sqlBuff.append("        BWE1.CS116_HNO,                   \n");
      sqlBuff.append("        BWE1.CS116_SEQ,                   \n");
      sqlBuff.append("        BWE1.CS116_BGT,                   \n");
      sqlBuff.append("        BWE1.CS116_BHD,                   \n");
      sqlBuff.append("        BWE1.CS116_PST                    \n");
      sqlBuff.append("   FROM                                   \n");
      sqlBuff.append("        SAPHEE.ZCST116 BWE1,              \n");
      sqlBuff.append("        (                                 \n");
      sqlBuff.append("         SELECT                           \n");
      sqlBuff.append("                MANDT,                    \n");
      sqlBuff.append("                CS116_PJT,                \n");
      sqlBuff.append("                CS116_HNO,                \n");
      sqlBuff.append("                MAX(CS116_SEQ) CS116_SEQ  \n");
      sqlBuff.append("           FROM                           \n");
      sqlBuff.append("                SAPHEE.ZCST116            \n");
      sqlBuff.append("         GROUP BY                         \n");
      sqlBuff.append("                MANDT,                    \n");
      sqlBuff.append("                CS116_PJT,                \n");
      sqlBuff.append("                CS116_HNO                 \n");
      sqlBuff.append("        ) BWE2                            \n");
      sqlBuff.append("  WHERE 1=1                               \n");
      sqlBuff.append("    AND BWE1.MANDT = BWE2.MANDT           \n");
      sqlBuff.append("    AND BWE1.CS116_PJT =  BWE2.CS116_PJT  \n");
      sqlBuff.append("    AND BWE1.CS116_HNO =  BWE2.CS116_HNO  \n");
      sqlBuff.append("    AND BWE1.CS116_SEQ =  BWE2.CS116_SEQ  \n");
      sqlBuff.append("    AND BWE1.MANDT     =  ?               \n");
      sqlBuff.append("    AND BWE1.CS116_PJT =  ?               \n");
      sqlBuff.append("    AND BWE1.CS116_HNO =  ?               \n");
      sqlBuff.append("    AND BWE1.CS116_BGT <= ?               \n");
      sqlBuff.append("    AND BWE1.CS116_BHD >  ?               \n");
      sqlBuff.append("    AND BWE1.CS116_GND =  ?               \n");
      sqlBuff.append("    AND BWE1.CS116_PST =  'A6'            \n");
*/
      sqlBuff.append(" SELECT                                   \n");
      sqlBuff.append("        BWE1.MANDT,                       \n");
      sqlBuff.append("        BWE1.CS116_PJT,                   \n");
      sqlBuff.append("        BWE1.CS116_HNO,                   \n");
      sqlBuff.append("        MIN(BWE1.CS116_SEQ) CS116_SEQ,    \n");
      sqlBuff.append("        BWE2.MAX_SEQ                      \n");
      sqlBuff.append("   FROM                                   \n");
      sqlBuff.append("        SAPHEE.ZCST116 BWE1,              \n");
      sqlBuff.append("        (                                 \n");
      sqlBuff.append("         SELECT                           \n");
      sqlBuff.append("                MAX(CS116_SEQ) MAX_SEQ    \n");
      sqlBuff.append("           FROM                           \n");
      sqlBuff.append("                SAPHEE.ZCST116            \n");
      sqlBuff.append("          WHERE 1=1                       \n");
      sqlBuff.append("            AND MANDT     =  ?            \n");
      sqlBuff.append("            AND CS116_PJT =  ?            \n");
      sqlBuff.append("            AND CS116_HNO =  ?            \n");
      sqlBuff.append("            AND CS116_GND =  ?            \n");
      sqlBuff.append("            AND CS116_PST =  ?            \n");
      sqlBuff.append("        ) BWE2                            \n");
      sqlBuff.append("  WHERE 1=1                               \n");
      sqlBuff.append("    AND BWE1.MANDT     =  ?               \n");
      sqlBuff.append("    AND BWE1.CS116_PJT =  ?               \n");
      sqlBuff.append("    AND BWE1.CS116_HNO =  ?               \n");
      sqlBuff.append("    AND BWE1.CS116_GND =  ?               \n");
      sqlBuff.append("    AND BWE1.CS116_PST =  ?               \n");
      sqlBuff.append("    AND ((BWE1.CS116_BGT <= ? AND BWE1.CS116_BHD > ?) OR (BWE1.CS116_BGT > ?)) \n");
      sqlBuff.append(" GROUP BY                                 \n");
      sqlBuff.append("         BWE1.MANDT,                      \n");
      sqlBuff.append("         BWE1.CS116_PJT,                  \n");
      sqlBuff.append("         BWE1.CS116_HNO,                  \n");
      sqlBuff.append("         BWE2.MAX_SEQ                     \n");
      sqlBuff.append("  WITH UR                                 \n");

      try
      {
         int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
         pstmt.setString(idxparam++, gnd);
         pstmt.setString(idxparam++, "A6");
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
         pstmt.setString(idxparam++, gnd);
         pstmt.setString(idxparam++, "A6");
         pstmt.setString(idxparam++, DateTime.getShortDateString());
         pstmt.setString(idxparam++, DateTime.getShortDateString());
         pstmt.setString(idxparam++, DateTime.getShortDateString());
         
         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
        	 max_seq = rs.getString("MAX_SEQ");
             seq = rs.getString("CS116_SEQ");
            returnFlg = true;
         }
         else
         {
        	 max_seq = "";
             seq = "";
            returnFlg = false;
         }
      }
      catch (Exception e)
      {
         throw e;
      }
      finally
      {
         try
         {
            BatchConstants.close(rs, pstmt, null);
         }
         catch (Exception e)
         {
         }
      }      
      return returnFlg;
   }    
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : DB의 커넥션 가져오기 
    * 기  타 :
    */ 

   public Connection getConnection(String mdt) throws SQLException, Exception
   {
	   ResourceFactory resourceFactory =(ResourceFactory)
	   		ServiceManagerFactory.getServiceObject("DB2_HEQ"); //아무거나 사용해도 무방
//	   		ServiceManagerFactory.getServiceObject("DB2_HEQ"); //아무거나 사용해도 무방
	   
	   if( resourceFactory == null ) {
		   return null;
	   } 
	   TransactionResource res = resourceFactory.makeResource(mdt); //mdt를 jdbc/hed, jdbc/heq, jdbc/hep 중에 어느것을 사용할지가 중요

	   return (Connection) res.getResource();
   }
}
