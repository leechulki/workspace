/**
 * 파 일 명 : TransForBWE.java
 * 설    명 : 
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-05-24 오후 4:13:26
 * 변경내역 :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.cs12.batch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import tit.service.core.logger.Logger;

import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

public class TransForBWE
{
   static Logger logger;

   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 무상보수계약에 대한 점검이관 처리 메인.. 
    * 기  타 : 
    */ 
   public int ProcTransForBWE (String mandt, Connection conn, TBEBWZF1Vo vo, String gnd, String seq, String max_seq) throws SQLException, Exception
   {
      //logger.info("-- 무상보수정보 처리 시작 ");
      logger.debug("-- 무상보수정보 처리 시작 ");
      TBEBWEF1Vo bweVo = getBWE(mandt, conn, vo, gnd, seq);
      String jrol = "";
      if("1".equals(vo.getCebwzgha()))
      {
         jrol = "26002";
      }
      else if("3".equals(vo.getCebwzgha()))
      {
         jrol = "26006";
      }
      
      if(bweVo != null)
      {
//         int newSeq = Integer.parseInt(bweVo.getCebweseq()) + 1;
         int newSeq = Integer.parseInt(max_seq) + 1;
         if(newSeq < 10)
            bweVo.setCebweseq("0"+newSeq);
         else
            bweVo.setCebweseq(new Integer(newSeq).toString());
            
         bweVo.setCebwebsu(vo.getCebwzcha());
         bweVo.setCebwejuj(BatchConstants.getCcsb1emp(mandt, vo.getCebwzcha(), jrol, conn));
         bweVo.setCebweagb(vo.getCebwzgba());
         
         if("C".equals(gnd))
         {
            int mno = Integer.parseInt(bweVo.getCebwemno().substring(2,5)) + 1;
            if(mno < 10)
               bweVo.setCebwemno("NK00"+mno);
            else if(mno < 100)
               bweVo.setCebwemno("NK0"+mno);
            else
               bweVo.setCebwemno("NK"+mno);
         }
         else if("D".equals(gnd))
         {
            bweVo.setCebwemno("M" + DateTime.getShortDateString().substring(2,6));
         }
//         insertBWE(mandt, bweVo, conn); // 이관후 보수 협력사에 대한 발주정보를 신규 생성
//         updateBWE(mandt, vo, seq, gnd, conn); // 이관전 보수협력사에 대한 발주정보를 해지      
         updateBWE(mandt, vo, seq, gnd, conn); // 이관전 보수협력사에 대한 발주정보를 해지      
      
      }
      //logger.info("-- 무상보수정보 처리 종료 ");
      logger.debug("-- 무상보수정보 처리 종료 ");
      return 0;
   }
   
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.26
    * 설  명 : 해당 자료에 대한 기존의 무상 보수 자료를 가져온다. 
    * 기  타 : 
    */    
   public TBEBWEF1Vo getBWE(String mandt, Connection conn, TBEBWZF1Vo vo, String gnd, String seq) throws SQLException, Exception
   {
      TBEBWEF1Vo                 rtnVo    = new TBEBWEF1Vo();
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;

      sqlBuff.append("SELECT                  \n");
      sqlBuff.append("       MANDT,           \n"); //MANDT
      sqlBuff.append("       CS116_PJT,       \n"); //CS116_PJT
      sqlBuff.append("       CS116_HNO,       \n"); //CS116_HNO
      sqlBuff.append("       CS116_SEQ,       \n"); //CS116_SEQ
      sqlBuff.append("       CS116_TYP,       \n"); //CS116_TYP
      sqlBuff.append("       CS116_HTY,       \n"); //CS116_HTY
      sqlBuff.append("       CS116_ARA,       \n"); //CS116_ARA
      sqlBuff.append("       CS116_BPM,       \n"); //CS116_BPM
      sqlBuff.append("       CS116_BSU,       \n"); //CS116_BSU
      sqlBuff.append("       CS116_FDT,       \n"); //CS116_FDT
      sqlBuff.append("       CS116_CBS,       \n"); //CS116_CBS
      sqlBuff.append("       CS116_PST,       \n"); //CS116_PST
      sqlBuff.append("       CS116_JUC,       \n"); //CS116_JUC
      sqlBuff.append("       CS116_GND,       \n"); //CS116_GND
      sqlBuff.append("       CS116_MG1,       \n"); //CS116_MG1
      sqlBuff.append("       CS116_BGT,       \n"); //CS116_BGT
      sqlBuff.append("       CS116_BMT,       \n"); //CS116_BMT
      sqlBuff.append("       CS116_BHD,       \n"); //CS116_BHD
      sqlBuff.append("       CS116_ABG,       \n"); //CS116_ABG
      sqlBuff.append("       CS116_AGB,       \n"); //CS116_AGB
      sqlBuff.append("       CS116_PLT,       \n"); //CS116_PLT
      sqlBuff.append("       CS116_MUT,       \n"); //CS116_MUT
      sqlBuff.append("       CS116_MBG,       \n"); //CS116_MBG
      sqlBuff.append("       CS116_BYT,       \n"); //CS116_BYT
      sqlBuff.append("       CS116_YCJ,       \n"); //CS116_YCJ
      sqlBuff.append("       CS116_BJT,       \n"); //CS116_BJT
      sqlBuff.append("       CS116_BJJ,       \n"); //CS116_BJJ
      sqlBuff.append("       CS116_HYN,       \n"); //CS116_HYN
      sqlBuff.append("       CS116_BST,       \n"); //CS116_BST
      sqlBuff.append("       CS116_APP,       \n"); //CS116_APP
      sqlBuff.append("       CS116_BCC,       \n"); //CS116_BCC
      sqlBuff.append("       CS116_BCT,       \n"); //CS116_BCT
      sqlBuff.append("       CS116_RMK,       \n"); //CS116_RMK
      sqlBuff.append("       CS116_MLT,       \n"); //CS116_MLT
      sqlBuff.append("       CS116_TGB,       \n"); //CS116_TGB
      sqlBuff.append("       CS116_GNO        \n"); //CS116_GNO
      sqlBuff.append("  FROM                  \n");
      sqlBuff.append("       SAPHEE.ZCST116   \n");
      sqlBuff.append(" WHERE 1=1              \n");
      sqlBuff.append("   AND MANDT = ?        \n");
      sqlBuff.append("   AND CS116_PJT = ?    \n");
      sqlBuff.append("   AND CS116_HNO = ?    \n");
      sqlBuff.append("   AND CS116_SEQ = ?    \n");
      sqlBuff.append("  WITH UR               \n");
      
      try
      {
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(1, mandt);
         pstmt.setString(2, vo.getCebwzpjt());
         pstmt.setString(3, vo.getCebwzhno());
         pstmt.setString(4, seq);
         
         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
            rtnVo.setMandt(rs.getString("MANDT"));
            rtnVo.setCebwepjt(rs.getString("CS116_PJT"));
            rtnVo.setCebwehno(rs.getString("CS116_HNO"));
            rtnVo.setCebweseq(rs.getString("CS116_SEQ"));
            rtnVo.setCebwetyp(rs.getString("CS116_TYP"));
            rtnVo.setCebwehty(rs.getString("CS116_HTY"));
            rtnVo.setCebweara(rs.getString("CS116_ARA"));
            rtnVo.setCebwebpm(rs.getString("CS116_BPM"));
            rtnVo.setCebwebsu(rs.getString("CS116_BSU"));
            rtnVo.setCebwefdt(rs.getString("CS116_FDT"));
            rtnVo.setCebwecbs(rs.getString("CS116_CBS"));
            rtnVo.setCebwepst(rs.getString("CS116_PST"));
            rtnVo.setCebwejuc(rs.getString("CS116_JUC"));
            rtnVo.setCebwegnd(rs.getString("CS116_GND"));
            rtnVo.setCebwemg1(rs.getString("CS116_MG1"));
            rtnVo.setCebwebgt(rs.getString("CS116_BGT"));
            rtnVo.setCebwebmt(rs.getString("CS116_BMT"));
            rtnVo.setCebwebhd(rs.getString("CS116_BHD"));
            rtnVo.setCebweabg(rs.getString("CS116_ABG"));
            rtnVo.setCebweagb(rs.getString("CS116_AGB"));
            rtnVo.setCebweplt(rs.getString("CS116_PLT"));
            rtnVo.setCebwemut(rs.getString("CS116_MUT"));
            rtnVo.setCebwembg(rs.getString("CS116_MBG"));
            rtnVo.setCebwebyt(rs.getString("CS116_BYT"));
            rtnVo.setCebweycj(rs.getString("CS116_YCJ"));
            rtnVo.setCebwebjt(rs.getString("CS116_BJT"));
            rtnVo.setCebwebjj(rs.getString("CS116_BJJ"));
            rtnVo.setCebwehyn(rs.getString("CS116_HYN"));
            rtnVo.setCebwebst(rs.getString("CS116_BST"));
            rtnVo.setCebweapp(rs.getString("CS116_APP"));
            rtnVo.setCebwebcc(rs.getString("CS116_BCC"));
            rtnVo.setCebwebct(rs.getString("CS116_BCT"));
            rtnVo.setCebwermk(rs.getString("CS116_RMK"));
            rtnVo.setCebwemlt(rs.getString("CS116_MLT"));
            rtnVo.setCebwetgb(rs.getString("CS116_TGB"));
            rtnVo.setCebwegno(rs.getString("CS116_GNO"));
         }
         else
         {
            rtnVo = null;
         }
      }
      catch(Exception e)
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
      
      return rtnVo;
   }   

   public int insertBWE(String mandt, TBEBWEF1Vo vo, Connection conn) throws SQLException, Exception
   {
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;

      sqlBuff.append("INSERT INTO SAPHEE.ZCST116   \n");
      sqlBuff.append("            (                \n");
      sqlBuff.append("             MANDT,          \n");
      sqlBuff.append("             CS116_PJT,       \n");
      sqlBuff.append("             CS116_HNO,       \n");
      sqlBuff.append("             CS116_SEQ,       \n");
      sqlBuff.append("             CS116_TYP,       \n");
      sqlBuff.append("             CS116_HTY,       \n");
      sqlBuff.append("             CS116_ARA,       \n");
      sqlBuff.append("             CS116_BPM,       \n");
      sqlBuff.append("             CS116_BSU,       \n");
      sqlBuff.append("             CS116_FDT,       \n");
      sqlBuff.append("             CS116_CBS,       \n");
      sqlBuff.append("             CS116_PST,       \n");
      sqlBuff.append("             CS116_JUC,       \n");
      sqlBuff.append("             CS116_GND,       \n");
      sqlBuff.append("             CS116_MG1,       \n");
      sqlBuff.append("             CS116_BGT,       \n");
      sqlBuff.append("             CS116_BMT,       \n");
      sqlBuff.append("             CS116_BHD,       \n");
      sqlBuff.append("             CS116_ABG,       \n");
      sqlBuff.append("             CS116_AGB,       \n");
      sqlBuff.append("             CS116_PLT,       \n");
      sqlBuff.append("             CS116_MUT,       \n");
      sqlBuff.append("             CS116_MBG,       \n");
      sqlBuff.append("             CS116_BYT,       \n");
      sqlBuff.append("             CS116_YCJ,       \n");
      sqlBuff.append("             CS116_BJT,       \n");
      sqlBuff.append("             CS116_BJJ,       \n");
      sqlBuff.append("             CS116_HYN,       \n");
      sqlBuff.append("             CS116_BST,       \n");
      sqlBuff.append("             CS116_APP,       \n");
      sqlBuff.append("             CS116_BCC,       \n");
      sqlBuff.append("             CS116_BCT,       \n");
      sqlBuff.append("             CS116_RMK,       \n");
      sqlBuff.append("             CS116_MLT,       \n");
      sqlBuff.append("             CS116_TGB,       \n");
      sqlBuff.append("             CS116_GNO,       \n");
      sqlBuff.append("             CRDAT,           \n");
      sqlBuff.append("             CRTIM           \n");
      sqlBuff.append("            )                \n");
      sqlBuff.append("            VALUES           \n");
      sqlBuff.append("            (                \n");
      sqlBuff.append("             ?,              \n"); //MANDT  
      sqlBuff.append("             ?,              \n"); //CS116_PJT
      sqlBuff.append("             ?,              \n"); //CS116_HNO 
      sqlBuff.append("             ?,              \n"); //CS116_SEQ 
      sqlBuff.append("             ?,              \n"); //CS116_TYP 
      sqlBuff.append("             ?,              \n"); //CS116_HTY 
      sqlBuff.append("             ?,              \n"); //CS116_ARA 
      sqlBuff.append("             ?,              \n"); //CS116_BPM 
      sqlBuff.append("             ?,              \n"); //CS116_BSU 
      sqlBuff.append("             ?,              \n"); //CS116_FDT 
      sqlBuff.append("             ?,              \n"); //CS116_CBS  
      sqlBuff.append("             ?,              \n"); //CS116_PST 
      sqlBuff.append("             ?,              \n"); //CS116_JUC 
      sqlBuff.append("             ?,              \n"); //CS116_GND 
      sqlBuff.append("             ?,              \n"); //CS116_MG1 
      sqlBuff.append("             ?,              \n"); //CS116_BGT 
      sqlBuff.append("             ?,              \n"); //CS116_BMT 
      sqlBuff.append("             ?,              \n"); //CS116_BHD 
      sqlBuff.append("             ?,              \n"); //CS116_ABG 
      sqlBuff.append("             ?,              \n"); //CS116_AGB 
      sqlBuff.append("             ?,              \n"); //CS116_PLT 
      sqlBuff.append("             ?,              \n"); //CS116_MUT  
      sqlBuff.append("             ?,              \n"); //CS116_MBG 
      sqlBuff.append("             ?,              \n"); //CS116_BYT 
      sqlBuff.append("             ?,              \n"); //CS116_YCJ 
      sqlBuff.append("             ?,              \n"); //CS116_BJT 
      sqlBuff.append("             ?,              \n"); //CS116_BJJ 
      sqlBuff.append("             ?,              \n"); //CS116_HYN 
      sqlBuff.append("             ?,              \n"); //CS116_BST 
      sqlBuff.append("             ?,              \n"); //CS116_APP 
      sqlBuff.append("             ?,              \n"); //CS116_BCC 
      sqlBuff.append("             ?,              \n"); //CS116_BCT 
      sqlBuff.append("             ?,              \n"); //CS116_RMK
      sqlBuff.append("             ?,              \n"); //CS116_MLT
      sqlBuff.append("             ?,              \n"); //CS116_TGB
      sqlBuff.append("             ?,              \n"); //CS116_GNO
      sqlBuff.append("             ?,              \n"); //CRDAT
      sqlBuff.append("             ?              \n"); //CRTIM
      sqlBuff.append("            )                \n");
      
      try
      {
         int idxparam = 1;
         pstmt.setString(idxparam++, mandt); //MANDT
         pstmt.setString(idxparam++, vo.getCebwepjt()); //CS116_PJT
         pstmt.setString(idxparam++, vo.getCebwehno()); //CS116_HNO
         pstmt.setString(idxparam++, vo.getCebweseq()); //CS116_SEQ
         pstmt.setString(idxparam++, vo.getCebwetyp()); //CS116_TYP
         pstmt.setString(idxparam++, vo.getCebwehty()); //CS116_HTY
         pstmt.setString(idxparam++, vo.getCebweara()); //CS116_ARA
         pstmt.setString(idxparam++, vo.getCebwebpm()); //CS116_BPM
         pstmt.setString(idxparam++, vo.getCebwebsu()); //CS116_BSU
         pstmt.setString(idxparam++, vo.getCebwefdt()); //CS116_FDT
         pstmt.setString(idxparam++, vo.getCebwecbs()); //CS116_CBS
         pstmt.setString(idxparam++, vo.getCebwepst()); //CS116_PST
         pstmt.setString(idxparam++, vo.getCebwejuc()); //CS116_JUC
         pstmt.setString(idxparam++, vo.getCebwegnd()); //CS116_GND
         pstmt.setDouble(idxparam++, new Double(String.valueOf(DateTime.monthsBetween(DateTime.getShortDateString(),vo.getCebwebmt()))).doubleValue()); //CS116_MG1
         pstmt.setString(idxparam++, DateTime.getShortDateString()); //CS116_BGT
         pstmt.setString(idxparam++, vo.getCebwebmt()); //CS116_BMT
         pstmt.setString(idxparam++, vo.getCebwebhd()); //CS116_BHD
         pstmt.setString(idxparam++, vo.getCebweabg()); //CS116_ABG
         pstmt.setString(idxparam++, vo.getCebweagb()); //CS116_AGB
         pstmt.setDouble(idxparam++, new Double(vo.getCebweplt()).doubleValue()); //CS116_PLT
         pstmt.setDouble(idxparam++, new Double(vo.getCebwemut()).doubleValue()); //CS116_MUT
         pstmt.setDouble(idxparam++, new Double(vo.getCebwembg()).doubleValue()); //CS116_MBG
         pstmt.setString(idxparam++, vo.getCebwebyt()); //CS116_BYT
         pstmt.setString(idxparam++, vo.getCebweycj()); //CS116_YCJ
         pstmt.setString(idxparam++, vo.getCebwebjt()); //CS116_BJT
         pstmt.setString(idxparam++, vo.getCebwebjj()); //CS116_BJJ
         pstmt.setString(idxparam++, vo.getCebwehyn()); //CS116_HYN
         pstmt.setString(idxparam++, vo.getCebwebst()); //CS116_BST
         pstmt.setString(idxparam++, vo.getCebweapp()); //CS116_APP
         pstmt.setString(idxparam++, vo.getCebwebcc()); //CS116_BCC
         pstmt.setString(idxparam++, vo.getCebwebct()); //CS116_BCT
         pstmt.setString(idxparam++, vo.getCebwermk()); //CS116_RMK
         pstmt.setDouble(idxparam++, new Double(vo.getCebwemlt()).doubleValue()); //CS116_MLT
         pstmt.setString(idxparam++, vo.getCebwetgb()); //CS116_TGB
         pstmt.setString(idxparam++, vo.getCebwegno()); //CS116_GNO
         pstmt.setString(idxparam++, DateTime.getShortDateString());//CRDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.10.29 Han J.H
         pstmt.setString(idxparam++, DateTime.getShortTimeString());//CRTIM. BS ETL 연계를 위한 식별컬럼 추가. 2020.10.29 Han J.H
         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         pstmt.executeUpdate();
      }
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         BatchConstants.close(null,pstmt,null);            
      }  
      
      return 0;
   }   
   
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.26
    * 설  명 : 무상발주정보에서 이관전 협력사에 대한 발주정보를 해지처리.
    * 기  타 : 
    */ 
/*
   public int updateBWE(String mandt, TBEBWZF1Vo vo,  String seq, String gnd, Connection conn) throws SQLException, Exception
   {

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      sqlBuff.append(" UPDATE                  \n");
      sqlBuff.append("        SAPHEE.ZCST116   \n");
      sqlBuff.append("    SET                  \n");
      sqlBuff.append("        CS116_BMT = ?,   \n");
      sqlBuff.append("        CS116_BHD = ?,   \n");
      sqlBuff.append("        CS116_MG1 = SAPHEE.MONTH_BETWEEN(CS116_BGT,HEX(CURRENT DATE - 1 day),'2') \n");
      sqlBuff.append("  WHERE 1=1              \n");
      sqlBuff.append("    AND MANDT = ?        \n");
      sqlBuff.append("    AND CS116_PJT = ?    \n");
      sqlBuff.append("    AND CS116_HNO = ?    \n");
      sqlBuff.append("    AND CS116_SEQ = ?    \n");
      sqlBuff.append("    AND CS116_GND = ?    \n");

      try
      {        
         int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(idxparam++, DateTime.addDays(DateTime.getShortDateString(), -1)); // 해지일자
         pstmt.setString(idxparam++, DateTime.addDays(DateTime.getShortDateString(), -1)); // 해지일자
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
         pstmt.setString(idxparam++, seq);
         pstmt.setString(idxparam++, gnd);
         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         pstmt.executeUpdate();
      }
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         BatchConstants.close(null,pstmt,null);            
      }
      return 0;
   }    
*/
   /**
    * 작성자 : 이주형
    * 작성일 : 2008.10.15
    * 설  명 : 무상발주정보에서 이관후 협력사로 UPDATE
    * 기  타 : 
    */ 
   public int updateBWE(String mandt, TBEBWZF1Vo vo,  String seq, String gnd, Connection conn) throws SQLException, Exception
   {

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      sqlBuff.append(" UPDATE                                                                                         \n");
      sqlBuff.append("        SAPHEE.ZCST116 A                                                                        \n");
      sqlBuff.append("    SET                                                                                         \n");
      sqlBuff.append("        (                                                                                       \n");
      sqlBuff.append("         A.CS116_ARA,                                                                           \n");
      sqlBuff.append("         A.CS116_BSU,                                                                           \n");
      sqlBuff.append("         A.CS116_BPM,                                                                           \n");
      sqlBuff.append("         A.CS116_TGB,                                                                           \n");
      sqlBuff.append("         A.UPDAT,                                                                               \n");
      sqlBuff.append("         A.UPZET                                                                                \n");
      sqlBuff.append("        )                                                                                       \n");
      sqlBuff.append("        =                                                                                       \n");
      sqlBuff.append("        (                                                                                       \n");
      sqlBuff.append("         SELECT                                                                                 \n");
      sqlBuff.append("                B.BSU_ARA,                                                                      \n");
      sqlBuff.append("                (SELECT C.LGORT FROM SAPHEE.ZMMT005 C WHERE C.MANDT = ? AND C.LIFNR = B.LIFNR), \n");
      sqlBuff.append("                B.BSU_PM,                                                                       \n");
      sqlBuff.append("                B.BSU_GB,                                                                       \n");
      sqlBuff.append("                HEX(CURRENT DATE) AS UPDAT,                                                     \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.10.29 Han J.H
      sqlBuff.append("                HEX(CURRENT TIME) AS UPZET                                                      \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.10.29 Han J.H
      sqlBuff.append("           FROM                                                                                 \n");
      sqlBuff.append("                SAPHEE.ZWBT010 B                                                                \n");
      sqlBuff.append("          WHERE                                                                                 \n");
      sqlBuff.append("                B.MANDT = ?                                                                     \n");
      sqlBuff.append("            AND B.LGORT = ?                                                                     \n");
      sqlBuff.append("        )                                                                                       \n");
      sqlBuff.append("  WHERE                                                                                         \n");
      sqlBuff.append("        A.MANDT = ?                                                                             \n");
      sqlBuff.append("    AND A.CS116_PJT = ?                                                                         \n");
      sqlBuff.append("    AND A.CS116_HNO = ?                                                                         \n");
//      sqlBuff.append("    AND A.CS116_SEQ >= ?                                                                        \n");
//      sqlBuff.append("    AND A.CS116_GND = ?                                                                         \n");

      try
      {        
         int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzcha());
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
//         pstmt.setString(idxparam++, seq);
//         pstmt.setString(idxparam++, gnd);
         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         pstmt.executeUpdate();
      }
      catch(Exception e)
      {
         throw e;
      }
      finally
      {
         BatchConstants.close(null,pstmt,null);            
      }
      return 0;
   }    

   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 무상보수계약에 대한 점검이관 처리 생성자.. 
    * 기  타 : 
    */ 

   public TransForBWE(Logger pLogger)
   {
      logger = pLogger;
   }
}
