/**
 * 파 일 명 : TransForBWM.java
 * 설    명 : 
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-05-24 오후 4:17:37
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

import com.helco.xf.cs12.evladm.vo.TBEBWMF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

public class TransForBWM
{
   //static Logger logger = Logger.getLogger(TransForBWM.class);
   static Logger logger;   
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 유상보수계약에 대한 점검이관 처리 메인.. 
    * 기  타 : 
    */ 
   public int ProcTransForBWM (String mandt, Connection conn, TBEBWZF1Vo vo, String gnd, String seq, String max_seq) throws SQLException, Exception
   {
      //logger.info("-- 유상보수계약 처리 시작 ");
      logger.debug("-- 유상보수계약 처리 시작 ");
      TBEBWMF1Vo bwmVo = getBWM(mandt, conn, vo, gnd, seq);
      String jrol = "";
      String brol = "";
      String juj = "";
      String buj = "";
      if("1".equals(vo.getCebwzgha()))
      {
         jrol = "26002";
         brol = "26003";
      }
      else if("3".equals(vo.getCebwzgha()))
      {
         jrol = "26006";
         brol = "26007";
      }
/* 2010.08.30 LJH 수정(주/부점검자 변경) */
//      juj = BatchConstants.getCcsb1emp(mandt, vo.getCebwzcha(), jrol, conn);
//      buj = BatchConstants.getCcsb1emp(mandt, vo.getCebwzcha(), brol, conn);
      juj = vo.getCebwzjua();
      buj = vo.getCebwzbua();
/* */
      if(bwmVo != null)
      {
//         int newSeq = Integer.parseInt(bwmVo.getCebwmseq()) + 1;
         int newSeq = Integer.parseInt(max_seq) + 1;
         if(newSeq < 10)
            bwmVo.setCebwmseq("0"+newSeq);
         else
            bwmVo.setCebwmseq(new Integer(newSeq).toString());
            
         bwmVo.setCebwmbsu(vo.getCebwzcha());
/* 2010.08.30 LJH 수정(주/부점검자 변경) */
//         bwmVo.setCebwmjuj(BatchConstants.getCcsb1emp(mandt, vo.getCebwzcha(), jrol, conn));
//         bwmVo.setCebwmbuj(BatchConstants.getCcsb1emp(mandt, vo.getCebwzcha(), brol, conn));
         bwmVo.setCebwmjuj(vo.getCebwzjua());
         bwmVo.setCebwmbuj(vo.getCebwzbua());
/* */
         bwmVo.setCebwmagb(vo.getCebwzgba());
         bwmVo.setCebwmcha(DateTime.getShortDateString());
         bwmVo.setCebwmcdt("System");
         
         if("C".equals(gnd))
         {
            /*
        	int mno = Integer.parseInt(bwmVo.getCebwmmno().substring(2,5)) + 1;
            if(mno < 10)
               bwmVo.setCebwmmno("UK00"+mno);
            else if(mno < 100)
               bwmVo.setCebwmmno("UK0"+mno);
            else
               bwmVo.setCebwmmno("UK"+mno);
            */
        	bwmVo.setCebwmmno("U" + DateTime.getShortDateString().substring(2,6));
         }
         else if("D".equals(gnd))
         {
            bwmVo.setCebwmmno("U" + DateTime.getShortDateString().substring(2,6));
         }
//         insertBWM(mandt, bwmVo, conn); // 이관후 보수 협력사에 대한 발주정보를 신규 생성
//         updateBWM(mandt, vo, seq, gnd, conn); // 이관전 보수협력사에 대한 발주정보를 해지
         updateBWM(mandt, vo, juj, buj, seq, gnd, conn); // 이관후 보수협력사로 UPDATE
         
      }
      
      //logger.info("-- 유상보수계약 처리 종료 ");
      logger.debug("-- 유상보수계약 처리 종료 ");
      return 0;
   }
   
   public TBEBWMF1Vo getBWM(String mandt, Connection conn, TBEBWZF1Vo vo, String gnd, String seq) throws SQLException, Exception
   {
      TBEBWMF1Vo                 rtnVo    = new TBEBWMF1Vo();
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;

      sqlBuff.append(" SELECT                      \n");
      sqlBuff.append("        MANDT,               \n"); // MANDT,
      sqlBuff.append("        CS126_UPN,           \n"); // CS126_UPN,
      sqlBuff.append("        CS126_CST,           \n"); // CS126_CST,
      sqlBuff.append("        CS126_PJT,           \n"); // CS126_PJT,
      sqlBuff.append("        CS126_HNO,           \n"); // CS126_HNO,
      sqlBuff.append("        CS126_SEQ,           \n"); // CS126_SEQ,
      sqlBuff.append("        CS126_PST,           \n"); // CS126_PST,
      sqlBuff.append("        CS126_PRO,           \n"); // CS126_PRO,
      sqlBuff.append("        CS126_TYP,           \n"); // CS126_TYP,
      sqlBuff.append("        CS126_HTY,           \n"); // CS126_HTY,
      sqlBuff.append("        CS126_ARA,           \n"); // CS126_ARA,
      sqlBuff.append("        CS126_TGB,           \n"); // CS126_TGB,
      sqlBuff.append("        CS126_BPM,           \n"); // CS126_BPM,
      sqlBuff.append("        CS126_BSU,           \n"); // CS126_BSU,
      sqlBuff.append("        CS126_JUJ,           \n"); // CS126_JUJ,
      sqlBuff.append("        CS126_BUJ,           \n"); // CS126_BUJ,
      sqlBuff.append("        CS126_GND,           \n"); // CS126_GND,
      sqlBuff.append("        CS126_GKD,           \n"); // CS126_GKD,
      sqlBuff.append("        CS126_GYB,           \n"); // CS126_GYB,
      sqlBuff.append("        CS126_RGB,           \n"); // CS126_RGB,
      sqlBuff.append("        CS126_CGB,           \n"); // CS126_CGB,
      sqlBuff.append("        CS126_USD,           \n"); // CS126_USD,
      sqlBuff.append("        CS126_UGS,           \n"); // CS126_UGS,
      sqlBuff.append("        CS126_UMS,           \n"); // CS126_UMS,
      sqlBuff.append("        CS126_MMN,           \n"); // CS126_MMN,
      sqlBuff.append("        CS126_UMR,           \n"); // CS126_UMR,
      sqlBuff.append("        CS126_UHJ,           \n"); // CS126_UHJ,
      sqlBuff.append("        CS126_AGB,           \n"); // CS126_AGB,
      sqlBuff.append("        CS126_ABG,           \n"); // CS126_ABG,
      sqlBuff.append("        CS126_PLT,           \n"); // CS126_PLT,
      sqlBuff.append("        CS126_BGB,           \n"); // CS126_BGB,
      sqlBuff.append("        CS126_BJG,           \n"); // CS126_BJG,
      sqlBuff.append("        CS126_TIS,           \n"); // CS126_TIS,
      sqlBuff.append("        CS126_JKH,           \n"); // CS126_JKH,
      sqlBuff.append("        CS126_YYB,           \n"); // CS126_YYB,
      sqlBuff.append("        CS126_RMK,           \n"); // CS126_RMK,
      sqlBuff.append("        CS126_ZER,           \n"); // CS126_ZER,
      sqlBuff.append("        CS126_WYB,           \n"); // CS126_WYB,
      sqlBuff.append("        CS126_AMT,           \n"); // CS126_AMT,
      sqlBuff.append("        CS126_VAT,           \n"); // CS126_VAT,
      sqlBuff.append("        CS126_TOT,           \n"); // CS126_TOT,
      sqlBuff.append("        CS126_KND,           \n"); // CS126_KND,
      sqlBuff.append("        CS126_RTO,           \n"); // CS126_RTO,
      sqlBuff.append("        CS126_IYN,           \n"); // CS126_IYN,
      sqlBuff.append("        CS126_WIL,           \n"); // CS126_WIL 
      sqlBuff.append("        CS126_GBM,           \n"); // CS126_GBM,
      sqlBuff.append("        CS126_RDT,           \n"); // CS126_RDT,
      sqlBuff.append("        CS126_REQ,           \n"); // CS126_REQ,
      sqlBuff.append("        CS126_CSY,           \n"); // CS126_CSY,
      sqlBuff.append("        CS126_CHA,           \n"); // CS126_CHA,
      sqlBuff.append("        CS126_CDT,           \n"); // CS126_CDT,
      sqlBuff.append("        CS126_DDT,           \n"); // CS126_DDT,
      sqlBuff.append("        CS126_DPP,           \n"); // CS126_DPP,
      sqlBuff.append("        CS126_GNO,           \n"); // CS126_GNO,
      sqlBuff.append("        CS126_ADT,           \n"); // CS126_ADT,
      sqlBuff.append("        CS126_AEQ,           \n"); // CS126_AEQ
      sqlBuff.append("        CS126_MLT            \n"); // CS126_MLT
      sqlBuff.append("   FROM                      \n");
      sqlBuff.append("        SAPHEE.ZCST126       \n");
      sqlBuff.append("  WHERE 1=1                  \n");
      sqlBuff.append("    AND MANDT = ?            \n");
      sqlBuff.append("    AND CS126_UPN = ?        \n");
      sqlBuff.append("    AND CS126_CST = ?        \n");
      sqlBuff.append("    AND CS126_PJT = ?        \n");
      sqlBuff.append("    AND CS126_HNO = ?        \n");
      sqlBuff.append("    AND CS126_SEQ = ?        \n");
      sqlBuff.append("  WITH UR                    \n");
      
      try
      {
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(1, mandt);
         pstmt.setString(2, vo.getCebwzupn());
         pstmt.setString(3, vo.getCebwzcst());
         pstmt.setString(4, vo.getCebwzpjt());
         pstmt.setString(5, vo.getCebwzhno());
         pstmt.setString(6, seq);
         
         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
            rtnVo.setMandt(rs.getString("MANDT"));
            rtnVo.setCebwmupn(rs.getString("CS126_UPN"));
            rtnVo.setCebwmcst(rs.getString("CS126_CST"));
            rtnVo.setCebwmpjt(rs.getString("CS126_PJT"));
            rtnVo.setCebwmhno(rs.getString("CS126_HNO"));
            rtnVo.setCebwmseq(rs.getString("CS126_SEQ"));
            rtnVo.setCebwmpst(rs.getString("CS126_PST"));
            rtnVo.setCebwmpro(rs.getString("CS126_PRO"));
            rtnVo.setCebwmtyp(rs.getString("CS126_TYP"));
            rtnVo.setCebwmhty(rs.getString("CS126_HTY"));
            rtnVo.setCebwmara(rs.getString("CS126_ARA"));
            rtnVo.setCebwmtgb(rs.getString("CS126_TGB"));
            rtnVo.setCebwmbpm(rs.getString("CS126_BPM"));
            rtnVo.setCebwmbsu(rs.getString("CS126_BSU"));
            rtnVo.setCebwmjuj(rs.getString("CS126_JUJ"));
            rtnVo.setCebwmbuj(rs.getString("CS126_BUJ"));
            rtnVo.setCebwmgnd(rs.getString("CS126_GND"));
            rtnVo.setCebwmgkd(rs.getString("CS126_GKD"));
            rtnVo.setCebwmgyb(rs.getString("CS126_GYB"));
            rtnVo.setCebwmrgb(rs.getString("CS126_RGB"));
            rtnVo.setCebwmcgb(rs.getString("CS126_CGB"));
            rtnVo.setCebwmusd(rs.getString("CS126_USD"));
            rtnVo.setCebwmugs(rs.getString("CS126_UGS"));
            rtnVo.setCebwmums(rs.getString("CS126_UMS"));
            rtnVo.setCebwmmmn(rs.getString("CS126_MMN"));
            rtnVo.setCebwmumr(rs.getString("CS126_UMR"));
            rtnVo.setCebwmuhj(rs.getString("CS126_UHJ"));
            rtnVo.setCebwmagb(rs.getString("CS126_AGB"));
            rtnVo.setCebwmabg(rs.getString("CS126_ABG"));
            rtnVo.setCebwmplt(rs.getString("CS126_PLT"));
            rtnVo.setCebwmbgb(rs.getString("CS126_BGB"));
            rtnVo.setCebwmbjg(rs.getString("CS126_BJG"));
            rtnVo.setCebwmtis(rs.getString("CS126_TIS"));
            rtnVo.setCebwmjkh(rs.getString("CS126_JKH"));
            rtnVo.setCebwmyyb(rs.getString("CS126_YYB"));
            rtnVo.setCebwmrmk(rs.getString("CS126_RMK"));
            rtnVo.setCebwmzer(rs.getString("CS126_ZER"));
            rtnVo.setCebwmwyb(rs.getString("CS126_WYB"));
            rtnVo.setCebwmamt(rs.getString("CS126_AMT"));
            rtnVo.setCebwmvat(rs.getString("CS126_VAT"));
            rtnVo.setCebwmtot(rs.getString("CS126_TOT"));
            rtnVo.setCebwmknd(rs.getString("CS126_KND"));
            rtnVo.setCebwmrto(rs.getString("CS126_RTO"));
            rtnVo.setCebwmiyn(rs.getString("CS126_IYN"));
            rtnVo.setCebwmwil(rs.getString("CS126_WIL"));
            rtnVo.setCebwmgbm(rs.getString("CS126_GBM"));
            rtnVo.setCebwmrdt(rs.getString("CS126_RDT"));
            rtnVo.setCebwmreq(rs.getString("CS126_REQ"));
            rtnVo.setCebwmcsy(rs.getString("CS126_CSY"));
            rtnVo.setCebwmcha(rs.getString("CS126_CHA"));
            rtnVo.setCebwmcdt(rs.getString("CS126_CDT"));
            rtnVo.setCebwmddt(rs.getString("CS126_DDT"));
            rtnVo.setCebwmdpp(rs.getString("CS126_DPP"));
            rtnVo.setCebwmgno(rs.getString("CS126_GNO"));
            rtnVo.setCebwmadt(rs.getString("CS126_ADT"));
            rtnVo.setCebwmaeq(rs.getString("CS126_AEQ"));
            rtnVo.setCebwmmlt(rs.getString("CS126_MLT"));
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
   public int insertBWM(String mandt, TBEBWMF1Vo vo, Connection conn) throws SQLException, Exception
   {
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      String t_gno = "";

      sqlBuff.append("INSERT INTO SAPHEE.ZCST126    \n");
      sqlBuff.append("            (                 \n"); 
      sqlBuff.append("             MANDT,           \n"); 
      sqlBuff.append("             CS126_UPN,       \n"); 
      sqlBuff.append("             CS126_CST,       \n"); 
      sqlBuff.append("             CS126_PJT,       \n"); 
      sqlBuff.append("             CS126_HNO,       \n"); 
      sqlBuff.append("             CS126_SEQ,       \n"); 
      sqlBuff.append("             CS126_PST,       \n"); 
      sqlBuff.append("             CS126_PRO,       \n"); 
      sqlBuff.append("             CS126_TYP,       \n"); 
      sqlBuff.append("             CS126_HTY,       \n"); 
      sqlBuff.append("             CS126_ARA,       \n"); 
      sqlBuff.append("             CS126_TGB,       \n"); 
      sqlBuff.append("             CS126_BPM,       \n"); 
      sqlBuff.append("             CS126_BSU,       \n"); 
      sqlBuff.append("             CS126_JUJ,       \n"); 
      sqlBuff.append("             CS126_BUJ,       \n"); 
      sqlBuff.append("             CS126_GND,       \n"); 
      sqlBuff.append("             CS126_GKD,       \n"); 
      sqlBuff.append("             CS126_GYB,       \n"); 
      sqlBuff.append("             CS126_RGB,       \n"); 
      sqlBuff.append("             CS126_CGB,       \n"); 
      sqlBuff.append("             CS126_USD,       \n"); 
      sqlBuff.append("             CS126_UGS,       \n"); 
      sqlBuff.append("             CS126_UMS,       \n"); 
      sqlBuff.append("             CS126_MMN,       \n"); 
      sqlBuff.append("             CS126_UMR,       \n"); 
      sqlBuff.append("             CS126_UHJ,       \n"); 
      sqlBuff.append("             CS126_AGB,       \n"); 
      sqlBuff.append("             CS126_ABG,       \n"); 
      sqlBuff.append("             CS126_PLT,       \n"); 
      sqlBuff.append("             CS126_BGB,       \n"); 
      sqlBuff.append("             CS126_BJG,       \n"); 
      sqlBuff.append("             CS126_TIS,       \n"); 
      sqlBuff.append("             CS126_JKH,       \n"); 
      sqlBuff.append("             CS126_YYB,       \n"); 
      sqlBuff.append("             CS126_RMK,       \n"); 
      sqlBuff.append("             CS126_ZER,       \n"); 
      sqlBuff.append("             CS126_WYB,       \n"); 
      sqlBuff.append("             CS126_AMT,       \n"); 
      sqlBuff.append("             CS126_VAT,       \n"); 
      sqlBuff.append("             CS126_TOT,       \n"); 
      sqlBuff.append("             CS126_KND,       \n"); 
      sqlBuff.append("             CS126_RTO,       \n"); 
      sqlBuff.append("             CS126_IYN,       \n"); 
      sqlBuff.append("             CS126_WIL,       \n"); 
      sqlBuff.append("             CS126_GBM,       \n"); 
      sqlBuff.append("             CS126_RDT,       \n"); 
      sqlBuff.append("             CS126_REQ,       \n"); 
      sqlBuff.append("             CS126_CSY,       \n"); 
      sqlBuff.append("             CS126_CHA,       \n"); 
      sqlBuff.append("             CS126_CDT,       \n"); 
      sqlBuff.append("             CS126_DDT,       \n"); 
      sqlBuff.append("             CS126_DPP,       \n"); 
      sqlBuff.append("             CS126_GNO,       \n"); 
      sqlBuff.append("             CS126_ADT,       \n"); 
      sqlBuff.append("             CS126_AEQ,       \n");
      sqlBuff.append("             CS126_MLT        \n"); 
      sqlBuff.append("            )                \n");  
      sqlBuff.append("            VALUES           \n");  
      sqlBuff.append("            (                \n");  
      sqlBuff.append("             ?,              \n"); //MANDT  
      sqlBuff.append("             ?,              \n"); //CS126_UPN  
      sqlBuff.append("             ?,              \n"); //CS126_CST 
      sqlBuff.append("             ?,              \n"); //CS126_PJT 
      sqlBuff.append("             ?,              \n"); //CS126_HNO 
      sqlBuff.append("             ?,              \n"); //CS126_SEQ 
      sqlBuff.append("             ?,              \n"); //CS126_PST 
      sqlBuff.append("             ?,              \n"); //CS126_PRO 
      sqlBuff.append("             ?,              \n"); //CS126_TYP 
      sqlBuff.append("             ?,              \n"); //CS126_HTY 
      sqlBuff.append("             ?,              \n"); //CS126_ARA  
      sqlBuff.append("             ?,              \n"); //CS126_TGB 
      sqlBuff.append("             ?,              \n"); //CS126_BPM 
      sqlBuff.append("             ?,              \n"); //CS126_BSU 
      sqlBuff.append("             ?,              \n"); //CS126_JUJ 
      sqlBuff.append("             ?,              \n"); //CS126_BUJ 
      sqlBuff.append("             ?,              \n"); //CS126_GND 
      sqlBuff.append("             ?,              \n"); //CS126_GKD 
      sqlBuff.append("             ?,              \n"); //CS126_GYB 
      sqlBuff.append("             ?,              \n"); //CS126_RGB 
      sqlBuff.append("             ?,              \n"); //CS126_CGB 
      sqlBuff.append("             ?,              \n"); //CS126_USD  
      sqlBuff.append("             ?,              \n"); //CS126_UGS 
      sqlBuff.append("             ?,              \n"); //CS126_UMS 
      sqlBuff.append("             ?,              \n"); //CS126_MMN 
      sqlBuff.append("             ?,              \n"); //CS126_UMR 
      sqlBuff.append("             ?,              \n"); //CS126_UHJ 
      sqlBuff.append("             ?,              \n"); //CS126_AGB 
      sqlBuff.append("             ?,              \n"); //CS126_ABG 
      sqlBuff.append("             ?,              \n"); //CS126_PLT 
      sqlBuff.append("             ?,              \n"); //CS126_BGB 
      sqlBuff.append("             ?,              \n"); //CS126_BJG 
      sqlBuff.append("             ?,              \n"); //CS126_TIS  
      sqlBuff.append("             ?,              \n"); //CS126_JKH 
      sqlBuff.append("             ?,              \n"); //CS126_YYB 
      sqlBuff.append("             ?,              \n"); //CS126_RMK 
      sqlBuff.append("             ?,              \n"); //CS126_ZER 
      sqlBuff.append("             ?,              \n"); //CS126_WYB 
      sqlBuff.append("             ?,              \n"); //CS126_AMT 
      sqlBuff.append("             ?,              \n"); //CS126_VAT  
      sqlBuff.append("             ?,              \n"); //CS126_TOT 
      sqlBuff.append("             ?,              \n"); //CS126_KND 
      sqlBuff.append("             ?,              \n"); //CS126_RTO 
      sqlBuff.append("             ?,              \n"); //CS126_IYN 
      sqlBuff.append("             ?,              \n"); //CS126_WIL 
      sqlBuff.append("             ?,              \n"); //CS126_GBM 
      sqlBuff.append("             ?,              \n"); //CS126_RDT 
      sqlBuff.append("             ?,              \n"); //CS126_REQ 
      sqlBuff.append("             ?,              \n"); //CS126_CSY 
      sqlBuff.append("             ?,              \n"); //CS126_CHA 
      sqlBuff.append("             ?,              \n"); //CS126_CDT 
      sqlBuff.append("             ?,              \n"); //CS126_DDT 
      sqlBuff.append("             ?,              \n"); //CS126_DPP 
      sqlBuff.append("             ?,              \n"); //CS126_GNO 
      sqlBuff.append("             ?,              \n"); //CS126_ADT 
      sqlBuff.append("             ?,              \n"); //CS126_AEQ
      sqlBuff.append("             ?               \n"); //CS126_MLT
      sqlBuff.append("            )                \n");
      
      try
      {
    	 t_gno = vo.getCebwmupn() + "U" + DateTime.getShortDateString().substring(2,6) + "-" + vo.getCebwmcst();
    	 int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(idxparam++, mandt); //MANDT
         pstmt.setString(idxparam++, vo.getCebwmupn()); //CS126_UPN
         pstmt.setString(idxparam++, vo.getCebwmcst()); //CS126_CST
         pstmt.setString(idxparam++, vo.getCebwmpjt()); //CS126_PJT
         pstmt.setString(idxparam++, vo.getCebwmhno()); //CS126_HNO
         pstmt.setString(idxparam++, vo.getCebwmseq()); //CS126_SEQ
         pstmt.setString(idxparam++, vo.getCebwmpst()); //CS126_PST
         pstmt.setString(idxparam++, vo.getCebwmpro()); //CS126_PRO
         pstmt.setString(idxparam++, vo.getCebwmtyp()); //CS126_TYP
         pstmt.setString(idxparam++, vo.getCebwmhty()); //CS126_HTY
         pstmt.setString(idxparam++, vo.getCebwmara()); //CS126_ARA
         pstmt.setString(idxparam++, vo.getCebwmtgb()); //CS126_TGB
         pstmt.setString(idxparam++, vo.getCebwmbpm()); //CS126_BPM
         pstmt.setString(idxparam++, vo.getCebwmbsu()); //CS126_BSU
         pstmt.setString(idxparam++, vo.getCebwmjuj()); //CS126_JUJ
         pstmt.setString(idxparam++, vo.getCebwmbuj()); //CS126_BUJ
         pstmt.setString(idxparam++, vo.getCebwmgnd()); //CS126_GND
         pstmt.setString(idxparam++, vo.getCebwmgkd()); //CS126_GKD
         pstmt.setString(idxparam++, vo.getCebwmgyb()); //CS126_GYB
         pstmt.setString(idxparam++, vo.getCebwmrgb()); //CS126_RGB
         pstmt.setString(idxparam++, vo.getCebwmcgb()); //CS126_CGB
         pstmt.setString(idxparam++, DateTime.getShortDateString()); //CS126_USD
         pstmt.setString(idxparam++, DateTime.getShortDateString()); //CS126_UGS
         pstmt.setDouble(idxparam++, new Double(String.valueOf(DateTime.monthsBetween(DateTime.getShortDateString(),vo.getCebwmumr()))).doubleValue()); //CS126_UMS
         pstmt.setDouble(idxparam++, new Double(vo.getCebwmmmn()).doubleValue()); //CS126_MMN
         pstmt.setString(idxparam++, vo.getCebwmumr()); //CS126_UMR
         pstmt.setString(idxparam++, vo.getCebwmuhj()); //CS126_UHJ
         pstmt.setString(idxparam++, vo.getCebwmagb()); //CS126_AGB
         pstmt.setString(idxparam++, vo.getCebwmabg()); //CS126_ABG
         pstmt.setDouble(idxparam++, new Double(vo.getCebwmplt()).doubleValue()); //CS126_PLT
         pstmt.setString(idxparam++, vo.getCebwmbgb()); //CS126_BGB
         pstmt.setString(idxparam++, vo.getCebwmbjg()); //CS126_BJG
         pstmt.setString(idxparam++, vo.getCebwmtis()); //CS126_TIS
         pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwmjkh())); //CS126_JKH
         pstmt.setString(idxparam++, vo.getCebwmyyb()); //CS126_YYB
         pstmt.setString(idxparam++, vo.getCebwmrmk()); //CS126_RMK
         pstmt.setString(idxparam++, vo.getCebwmzer()); //CS126_ZER
         pstmt.setString(idxparam++, vo.getCebwmwyb()); //CS126_WYB
         pstmt.setDouble(idxparam++, new Double(vo.getCebwmamt()).doubleValue()); //CS126_AMT
         pstmt.setDouble(idxparam++, new Double(vo.getCebwmvat()).doubleValue()); //CS126_VAT
         pstmt.setDouble(idxparam++, new Double(vo.getCebwmtot()).doubleValue()); //CS126_TOT
         pstmt.setString(idxparam++, vo.getCebwmknd()); //CS126_KND
         pstmt.setDouble(idxparam++, new Double(vo.getCebwmrto()).doubleValue()); //CS126_RTO
         pstmt.setString(idxparam++, vo.getCebwmiyn()); //CS126_IYN
         pstmt.setString(idxparam++, vo.getCebwmwil()); //CS126_WIL
         pstmt.setString(idxparam++, vo.getCebwmgbm()); //CS126_GBM
         pstmt.setString(idxparam++, vo.getCebwmrdt()); //CS126_RDT
         pstmt.setString(idxparam++, vo.getCebwmreq()); //CS126_REQ
         pstmt.setString(idxparam++, vo.getCebwmcsy()); //CS126_CSY
         pstmt.setString(idxparam++, vo.getCebwmcha()); //CS126_CHA
         pstmt.setString(idxparam++, vo.getCebwmcdt()); //CS126_CDT
         pstmt.setString(idxparam++, vo.getCebwmddt()); //CS126_DDT
         pstmt.setString(idxparam++, vo.getCebwmdpp()); //CS126_DPP
         //pstmt.setString(idxparam++, vo.getCebwmgno()); //CS126_GNO
         pstmt.setString(idxparam++, t_gno); //CS126_GNO
         pstmt.setString(idxparam++, vo.getCebwmadt()); //CS126_ADT
         pstmt.setString(idxparam++, vo.getCebwmaeq()); //CS126_AEQ
         pstmt.setDouble(idxparam++, new Double(vo.getCebwmmlt()).doubleValue()); //CS126_MLT

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
    * 작성일 : 2006.05.25
    * 설  명 : 유상발주정보에서 이관전 협력사에 대한 발주정보를 해지처리.
    * 기  타 : 
    */ 
/*
   public int updateBWM (String mandt, TBEBWZF1Vo vo,  String seq, String gnd, Connection conn) throws SQLException, Exception
   {

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      sqlBuff.append(" UPDATE                   \n");
      sqlBuff.append("        SAPHEE.ZCST126    \n");
      sqlBuff.append("    SET                   \n");
      sqlBuff.append("        CS126_UMR = ?,    \n");
      sqlBuff.append("        CS126_UHJ = ?,    \n");
      sqlBuff.append("        CS126_UMS = SAPHEE.MONTH_BETWEEN(CS126_USD,HEX(CURRENT DATE - 1 day),'2'), \n");
//      sqlBuff.append("        CS126_DDT = ?,    \n");
//      sqlBuff.append("        CS126_DPP = 'system', \n");
      sqlBuff.append("        CS126_FLG = 'X'   \n");
      sqlBuff.append("  WHERE 1=1               \n");
      sqlBuff.append("    AND MANDT     = ?     \n");
      sqlBuff.append("    AND CS126_UPN = ?     \n");
      sqlBuff.append("    AND CS126_CST = ?     \n");
      sqlBuff.append("    AND CS126_PJT = ?     \n");
      sqlBuff.append("    AND CS126_HNO = ?     \n");
      sqlBuff.append("    AND CS126_SEQ = ?     \n");
      sqlBuff.append("    AND CS126_GND = ?     \n");

      try
      {        
         int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(idxparam++, DateTime.addDays(DateTime.getShortDateString(), -1)); // 해지일자
         pstmt.setString(idxparam++, DateTime.addDays(DateTime.getShortDateString(), -1)); // 해지일자
         pstmt.setString(idxparam++, DateTime.getShortDateString()); // 삭제일자
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzupn());
         pstmt.setString(idxparam++, vo.getCebwzcst());            
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
    * 설  명 : 유상발주정보에서 이관후 협력사로 UPDATE
    * 기  타 : 
    */ 
   public int updateBWM (String mandt, TBEBWZF1Vo vo, String juj, String buj, String seq, String gnd, Connection conn) throws SQLException, Exception
   {

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      
      sqlBuff.append(" UPDATE                                                                                         \n");
      sqlBuff.append("        SAPHEE.ZCST126 A                                                                        \n");
      sqlBuff.append("    SET                                                                                         \n");
      sqlBuff.append("        (                                                                                       \n");
      sqlBuff.append("         A.CS126_ARA,                                                                           \n");
      sqlBuff.append("         A.CS126_BSU,                                                                           \n");
      sqlBuff.append("         A.CS126_BPM,                                                                           \n");
      sqlBuff.append("         A.CS126_TGB,                                                                           \n");
      sqlBuff.append("         A.CS126_JUJ,                                                                           \n");
      sqlBuff.append("         A.CS126_BUJ,                                                                           \n");
      sqlBuff.append("         A.CS126_AGB                                                                            \n");
      sqlBuff.append("        )                                                                                       \n");
      sqlBuff.append("        =                                                                                       \n");
      sqlBuff.append("        (                                                                                       \n");
      sqlBuff.append("         SELECT                                                                                 \n");
      sqlBuff.append("                B.BSU_ARA,                                                                      \n");
      sqlBuff.append("                (SELECT C.LGORT FROM SAPHEE.ZMMT005 C WHERE C.MANDT = ? AND C.LIFNR = B.LIFNR), \n");
      sqlBuff.append("                B.BSU_PM,                                                                       \n");
      sqlBuff.append("                B.BSU_GB,                                                                       \n");
      sqlBuff.append("                CAST(? AS VARCHAR(10)),                                                         \n");
      sqlBuff.append("                CAST(? AS VARCHAR(10)),                                                         \n");
      sqlBuff.append("                CAST(? AS VARCHAR(1))                                                           \n");
      sqlBuff.append("           FROM                                                                                 \n");
      sqlBuff.append("                SAPHEE.ZWBT010 B                                                                \n");
      sqlBuff.append("          WHERE                                                                                 \n");
      sqlBuff.append("                B.MANDT = ?                                                                     \n");
      sqlBuff.append("            AND B.LGORT = ?                                                                     \n");
      sqlBuff.append("        )                                                                                       \n");
      sqlBuff.append("  WHERE                                                                                         \n");
      sqlBuff.append("        A.MANDT = ?                                                                             \n");
//      sqlBuff.append("    AND A.CS126_UPN = ?                                                                         \n");
//      sqlBuff.append("    AND A.CS126_CST = ?                                                                         \n");
      sqlBuff.append("    AND A.CS126_PJT = ?                                                                         \n");
      sqlBuff.append("    AND A.CS126_HNO = ?                                                                         \n");
//      sqlBuff.append("    AND A.CS126_SEQ >= ?                                                                        \n");
//      sqlBuff.append("    AND A.CS126_GND = ?                                                                         \n");
      sqlBuff.append("    AND A.CS126_DDT = ''                                                                        \n");

      try
      {        
         int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, juj);
         pstmt.setString(idxparam++, buj);
         pstmt.setString(idxparam++, vo.getCebwzgba());
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzcha());
         pstmt.setString(idxparam++, mandt);
//         pstmt.setString(idxparam++, vo.getCebwzupn());
//         pstmt.setString(idxparam++, vo.getCebwzcst());            
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
    * 설  명 : 유상보수계약에 대한 점검이관 처리 생성자.. 
    * 기  타 : 
    */ 

   public TransForBWM(Logger pLogger)
   {
      logger = pLogger;
   }
}
