/**
 * 파 일 명 : TransForBXR.java
 * 설    명 :
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-05-24 오후 4:25:51
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
import java.util.ArrayList;

import tit.service.core.logger.Logger;

import com.helco.xf.cs12.evladm.vo.TBEBWMF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBXRF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.BizException;

public class TransForBXR
{
   static Logger logger;
   static double [] basGisungbi = {0,0,0,0};
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 점검이관처리에 대한 기성비계획 정보 갱신(해지후 생성)
    * 기  타 :
    */
   public int ProcTransForBXR (String mandt, Connection conn, TBEBWZF1Vo vo, String seq) throws SQLException, Exception
   {
      //logger.info("-- 기성비계획 처리 시작 ");
      logger.debug("-- 기성비계획 처리 시작 ");

      String jrol = "";
      if("1".equals(vo.getCebwzgha()))
      {
         jrol = "26002";
      }
      else if("3".equals(vo.getCebwzgha()))
      {
         jrol = "26006";
      }
      String juj = BatchConstants.getCcsb1emp(mandt, vo.getCebwzcha(), jrol, conn);
      
      updateBXR(mandt, conn, vo, seq);
/*
      ArrayList alist = getBXR(mandt, conn, vo);
      TBEBXRF1Vo bxrVo ;

	  int newIsr = 0; 
      
      for(int i=0;i<alist.size();i++)
      {
         bxrVo = new TBEBXRF1Vo();
         bxrVo = (TBEBXRF1Vo)alist.get(i);

		 updateBXR(mandt, conn, bxrVo, vo.getCebwzchb());

         newIsr = Integer.parseInt(bxrVo.getCebxrisr()) + 1;
         if(newIsr < 10)
            bxrVo.setCebxrisr("0"+newIsr);
         else
            bxrVo.setCebxrisr(new Integer(newIsr).toString());

         bxrVo.setCebxrbsu(vo.getCebwzcha());
		 bxrVo.setCebxrgbu(vo.getCebwzgha()); //2006.09.11 직영/협력사 여부          
         bxrVo.setCebxragb(vo.getCebwzgba());
         bxrVo.setCebxrjuj(juj);

         if(!vo.getCebwzgbb().equals(vo.getCebwzgba()))
         {
            if("A".equals(bxrVo.getCebxrgnd()) || "B".equals(bxrVo.getCebxrgnd()))
            {
               double bda = getBasGisungbi1(bxrVo);
               bxrVo.setCebxrbda(new Double(bda).toString());
               if(isMangunChk(bxrVo.getCebxrfdt(), bxrVo.getCebxrtod()))
               {
                  bxrVo.setCebxrbya(new Double(bda).toString());
				  bxrVo.setCebxrbda(new Double(bda).toString());
                  bxrVo.setCebxrbam(new Double(bda).toString());
               }
               else
               {
                  double jdq = Double.parseDouble(bxrVo.getCebxrjdj());
                  bxrVo.setCebxrbya(new Double((bda / 30) * jdq).toString());
				  bxrVo.setCebxrbda(new Double((bda / 30) * jdq).toString());
                  bxrVo.setCebxrbam(new Double((bda / 30) * jdq).toString());
               }
            }
            else if("D".equals(bxrVo.getCebxrgnd()))
            {
               double machul = getMaechul(mandt, vo, seq, conn);
               double bda = getBasGisungbi2(bxrVo, vo, machul);
               if(isMangunChk(bxrVo.getCebxrfdt(), bxrVo.getCebxrtod()))
               {
                  bxrVo.setCebxrbya(new Double(bda).toString());
				  bxrVo.setCebxrbda(new Double(bda).toString());
                  bxrVo.setCebxrbam(new Double(bda).toString());
               }
               else
               {
                  double jdq = Double.parseDouble(bxrVo.getCebxrjdq());
                  bxrVo.setCebxrbya(new Double((bda / 30) * jdq).toString());
				  bxrVo.setCebxrbda(new Double((bda / 30) * jdq).toString());
                  bxrVo.setCebxrbam(new Double((bda / 30) * jdq).toString());
               }
            }
         }
		 
         insertBXR(mandt, conn, bxrVo);
      }
*/
      //logger.info("-- 기성비계획 처리 종료 ");
      logger.debug("-- 기성비계획 처리 종료 ");
      return 0;
   }
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.26
    * 설  명 : 해당 자료에 대한 기존의 무상 보수 자료를 가져온다.
    * 기  타 :
    */
   public ArrayList getBXR(String mandt, Connection conn, TBEBWZF1Vo vo) throws SQLException, Exception
   {
      ArrayList                  rtnList  = new ArrayList();
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;

      sqlBuff.append("SELECT                             \n");
      sqlBuff.append("       MANDT,                      \n"); //MANDT
      sqlBuff.append("       CS136_PJT,                  \n"); //CS136_PJT
      sqlBuff.append("       CS136_HNO,                  \n"); //CS136_HNO
      sqlBuff.append("       CS136_SEQ,                  \n"); //CS136_SEQ
      sqlBuff.append("       CS136_GND,                  \n"); //CS136_GND
      sqlBuff.append("       CS136_JYM,                  \n"); //CS136_JYM
      sqlBuff.append("       CS136_ISR,                  \n"); //CS136_ISR
      sqlBuff.append("       CS136_UPN,                  \n"); //CS136_UPN
      sqlBuff.append("       CS136_CST,                  \n"); //CS136_CST
      sqlBuff.append("       CS136_HTY,                  \n"); //CS136_HTY
      sqlBuff.append("       CS136_ARA,                  \n"); //CS136_ARA
      sqlBuff.append("       CS136_BSU,                  \n"); //CS136_BSU
      sqlBuff.append("       CS136_GBU,                  \n"); //CS136_GBU
      sqlBuff.append("       CS136_AGB,                  \n"); //CS136_AGB
      sqlBuff.append("       CS136_ABG,                  \n"); //CS136_ABG
      sqlBuff.append("       CS136_RGB,                  \n"); //CS136_RGB
      sqlBuff.append("       CS136_IIS,                  \n"); //CS136_IIS
      sqlBuff.append("       CS136_PLT,                  \n"); //CS136_PLT
      sqlBuff.append("       CS136_PST,                  \n"); //CS136_PST
      sqlBuff.append("       CS136_FDT,                  \n"); //CS136_FDT
      sqlBuff.append("       CS136_TOD,                  \n"); //CS136_TOD
      sqlBuff.append("       CS136_JDQ,                  \n"); //CS136_JDQ
      sqlBuff.append("       CS136_JDJ,                  \n"); //CS136_JDJ
      sqlBuff.append("       CS136_BDA,                  \n"); //CS136_BDA
      sqlBuff.append("       CS136_BPD,                  \n"); //CS136_BPD
      sqlBuff.append("       CS136_CPR,                  \n"); //CS136_CPR
      sqlBuff.append("       CS136_JSD,                  \n"); //CS136_JSD
      sqlBuff.append("       CS136_JYQ,                  \n"); //CS136_JYQ
      sqlBuff.append("       CS136_JYC,                  \n"); //CS136_JYC
      sqlBuff.append("       CS136_BYA,                  \n"); //CS136_BYA
      sqlBuff.append("       CS136_IS1,                  \n"); //CS136_IS1
      sqlBuff.append("       CS136_IS2,                  \n"); //CS136_IS2
      sqlBuff.append("       CS136_IS3,                  \n"); //CS136_IS3
      sqlBuff.append("       CS136_IS4,                  \n"); //CS136_IS4
      sqlBuff.append("       CS136_JYD,                  \n"); //CS136_JYD
      sqlBuff.append("       CS136_JYJ,                  \n"); //CS136_JYJ
      sqlBuff.append("       CS136_IDQ,                  \n"); //CS136_IDQ
      sqlBuff.append("       CS136_IDJ,                  \n"); //CS136_IDJ
      sqlBuff.append("       CS136_BAM,                  \n"); //CS136_BAM
      sqlBuff.append("       CS136_IY1,                  \n"); //CS136_IY1
      sqlBuff.append("       CS136_IY2,                  \n"); //CS136_IY2
      sqlBuff.append("       CS136_IY3,                  \n"); //CS136_IY3
      sqlBuff.append("       CS136_IY4,                  \n"); //CS136_IY4
      sqlBuff.append("       CS136_JAR,                  \n"); //CS136_JAR
      sqlBuff.append("       CS136_SS1,                  \n"); //CS136_SS1
      sqlBuff.append("       CS136_SDT,                  \n"); //CS136_SDT
      sqlBuff.append("       CS136_APP,                  \n"); //CS136_APP
      sqlBuff.append("       CS136_GGB,                  \n"); //CS136_GGB
      sqlBuff.append("       CS136_GYM,                  \n"); //CS136_GYM
      sqlBuff.append("       CS136_MGB,                  \n"); //CS136_MGB
      sqlBuff.append("       CS136_MSA,                  \n"); //CS136_MSA
      sqlBuff.append("       CS136_MDT                   \n"); //CS136_MDT
      sqlBuff.append("  FROM                             \n");
      sqlBuff.append("       SAPHEE.ZCST136              \n");
      sqlBuff.append(" WHERE 1=1                         \n");
      sqlBuff.append("   AND MANDT = ?                   \n");
      sqlBuff.append("   AND CS136_PJT = ?               \n");
      sqlBuff.append("   AND CS136_HNO = ?               \n");
      sqlBuff.append("   AND CS136_GND = ?               \n");
      sqlBuff.append("   AND CS136_JYM >= ?              \n");
      sqlBuff.append("   AND RTRIM(CS136_MSA) = ''       \n");
      sqlBuff.append("  WITH UR                          \n");

      try
      {
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(1, mandt);
         pstmt.setString(2, vo.getCebwzpjt());
         pstmt.setString(3, vo.getCebwzhno());
         pstmt.setString(4, vo.getCebwzgnd());
         pstmt.setString(5, vo.getCebwzigm());

         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();

         TBEBXRF1Vo rtnVo ;
         while (rs.next())
         {
            rtnVo = new TBEBXRF1Vo();
            rtnVo.setMandt(rs.getString("MANDT"));
            rtnVo.setCebxrpjt(rs.getString("CS136_PJT"));
            rtnVo.setCebxrhno(rs.getString("CS136_HNO"));
            rtnVo.setCebxrseq(rs.getString("CS136_SEQ"));
            rtnVo.setCebxrgnd(rs.getString("CS136_GND"));
            rtnVo.setCebxrjym(rs.getString("CS136_JYM"));
            rtnVo.setCebxrisr(rs.getString("CS136_ISR"));
            rtnVo.setCebxrupn(rs.getString("CS136_UPN"));
            rtnVo.setCebxrcst(rs.getString("CS136_CST"));
            rtnVo.setCebxrhty(rs.getString("CS136_HTY"));
            rtnVo.setCebxrara(rs.getString("CS136_ARA"));
            rtnVo.setCebxrbsu(rs.getString("CS136_BSU"));
            rtnVo.setCebxrgbu(rs.getString("CS136_GBU"));
            rtnVo.setCebxragb(rs.getString("CS136_AGB"));
            rtnVo.setCebxrabg(rs.getString("CS136_ABG"));
            rtnVo.setCebxrrgb(rs.getString("CS136_RGB"));
            rtnVo.setCebxriis(rs.getString("CS136_IIS"));
            rtnVo.setCebxrplt(rs.getString("CS136_PLT"));
            rtnVo.setCebxrpst(rs.getString("CS136_PST"));
            rtnVo.setCebxrfdt(rs.getString("CS136_FDT"));
            rtnVo.setCebxrtod(rs.getString("CS136_TOD"));
            rtnVo.setCebxrjdq(rs.getString("CS136_JDQ"));
            rtnVo.setCebxrjdj(rs.getString("CS136_JDJ"));
            rtnVo.setCebxrbda(rs.getString("CS136_BDA"));
            rtnVo.setCebxrbpd(rs.getString("CS136_BPD"));
            rtnVo.setCebxrcpr(rs.getString("CS136_CPR"));
            rtnVo.setCebxrjsd(rs.getString("CS136_JSD"));
            rtnVo.setCebxrjyq(rs.getString("CS136_JYQ"));
            rtnVo.setCebxrjyc(rs.getString("CS136_JYC"));
            rtnVo.setCebxrbya(rs.getString("CS136_BYA"));
            rtnVo.setCebxris1(rs.getString("CS136_IS1"));
            rtnVo.setCebxris2(rs.getString("CS136_IS2"));
            rtnVo.setCebxris3(rs.getString("CS136_IS3"));
            rtnVo.setCebxris4(rs.getString("CS136_IS4"));
            rtnVo.setCebxrjyd(rs.getString("CS136_JYD"));
            rtnVo.setCebxrjyj(rs.getString("CS136_JYJ"));
            rtnVo.setCebxridq(rs.getString("CS136_IDQ"));
            rtnVo.setCebxridj(rs.getString("CS136_IDJ"));
            rtnVo.setCebxrbam(rs.getString("CS136_BAM"));
            rtnVo.setCebxriy1(rs.getString("CS136_IY1"));
            rtnVo.setCebxriy2(rs.getString("CS136_IY2"));
            rtnVo.setCebxriy3(rs.getString("CS136_IY3"));
            rtnVo.setCebxriy4(rs.getString("CS136_IY4"));
            rtnVo.setCebxrjar(rs.getString("CS136_JAR"));
            rtnVo.setCebxrss1(rs.getString("CS136_SS1"));
            rtnVo.setCebxrsdt(rs.getString("CS136_SDT"));
            rtnVo.setCebxrapp(rs.getString("CS136_APP"));
            rtnVo.setCebxrggb(rs.getString("CS136_GGB"));
            rtnVo.setCebxrgym(rs.getString("CS136_GYM"));
            rtnVo.setCebxrmgb(rs.getString("CS136_MGB"));
            rtnVo.setCebxrmsa(rs.getString("CS136_MSA"));
            rtnVo.setCebxrmdt(rs.getString("CS136_MDT"));
            rtnList.add(rtnVo);
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

      return rtnList;
   }
   /**
     * 작성자 : 구자경
     * 작성일 : 2006.05.24
     * 설  명 : 호기관리(협력사) 에 신규 정보를 Insert 한다.
     * 기  타 :
     */
    public int insertBXR (String mandt, Connection conn, TBEBXRF1Vo vo) throws SQLException, Exception
    {
       StringBuffer               sqlBuff  = new StringBuffer();
       LoggablePreparedStatement  pstmt    = null;

       sqlBuff.append(" INSERT INTO SAPHEE.ZCST136    \n");
       sqlBuff.append("             (                 \n");
       sqlBuff.append("              MANDT,           \n");
       sqlBuff.append("              CS136_PJT,       \n");
       sqlBuff.append("              CS136_HNO,       \n");
       sqlBuff.append("              CS136_SEQ,       \n");
       sqlBuff.append("              CS136_GND,       \n");
       sqlBuff.append("              CS136_JYM,       \n");
       sqlBuff.append("              CS136_ISR,       \n");
       sqlBuff.append("              CS136_UPN,       \n");
       sqlBuff.append("              CS136_CST,       \n");
       sqlBuff.append("              CS136_HTY,       \n");
       sqlBuff.append("              CS136_ARA,       \n");
       sqlBuff.append("              CS136_BSU,       \n");
       sqlBuff.append("              CS136_GBU,       \n");
       sqlBuff.append("              CS136_AGB,       \n");
       sqlBuff.append("              CS136_ABG,       \n");
       sqlBuff.append("              CS136_RGB,       \n");
       sqlBuff.append("              CS136_IIS,       \n");
       sqlBuff.append("              CS136_PLT,       \n");
       sqlBuff.append("              CS136_PST,       \n");
       sqlBuff.append("              CS136_FDT,       \n");
       sqlBuff.append("              CS136_TOD,       \n");
       sqlBuff.append("              CS136_JDQ,       \n");
       sqlBuff.append("              CS136_JDJ,       \n");
       sqlBuff.append("              CS136_BDA,       \n");
       sqlBuff.append("              CS136_BPD,       \n");
       sqlBuff.append("              CS136_CPR,       \n");
       sqlBuff.append("              CS136_JSD,       \n");
       sqlBuff.append("              CS136_JYQ,       \n");
       sqlBuff.append("              CS136_JYC,       \n");
       sqlBuff.append("              CS136_BYA,       \n");
       sqlBuff.append("              CS136_IS1,       \n");
       sqlBuff.append("              CS136_IS2,       \n");
       sqlBuff.append("              CS136_IS3,       \n");
       sqlBuff.append("              CS136_IS4,       \n");
       sqlBuff.append("              CS136_JYD,       \n");
       sqlBuff.append("              CS136_JYJ,       \n");
       sqlBuff.append("              CS136_IDQ,       \n");
       sqlBuff.append("              CS136_IDJ,       \n");
       sqlBuff.append("              CS136_BAM,       \n");
       sqlBuff.append("              CS136_IY1,       \n");
       sqlBuff.append("              CS136_IY2,       \n");
       sqlBuff.append("              CS136_IY3,       \n");
       sqlBuff.append("              CS136_IY4,       \n");
       sqlBuff.append("              CS136_JAR,       \n");
       sqlBuff.append("              CS136_SS1,       \n");
       sqlBuff.append("              CS136_SDT,       \n");
       sqlBuff.append("              CS136_APP,       \n");
       sqlBuff.append("              CS136_GGB,       \n");
       sqlBuff.append("              CS136_GYM,       \n");
       sqlBuff.append("              CS136_MGB,       \n");
       sqlBuff.append("              CS136_MSA,       \n");
       sqlBuff.append("              CS136_MDT,       \n");
       //===========================생성일자 및 생성일시 추가 20201130 Han.JH=============================
       sqlBuff.append("              CRDAT,           \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
       sqlBuff.append("              CRTIM            \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
       //=======================================================================================
       sqlBuff.append("             )                 \n");
       sqlBuff.append("             VALUES            \n");
       sqlBuff.append("             (                 \n");
       sqlBuff.append("              ?,               \n"); // MANDT
       sqlBuff.append("              ?,               \n"); // CS136_PJT
       sqlBuff.append("              ?,               \n"); // CS136_HNO
       sqlBuff.append("              ?,               \n"); // CS136_SEQ
       sqlBuff.append("              ?,               \n"); // CS136_GND
       sqlBuff.append("              ?,               \n"); // CS136_JYM
       sqlBuff.append("              ?,               \n"); // CS136_ISR
       sqlBuff.append("              ?,               \n"); // CS136_UPN
       sqlBuff.append("              ?,               \n"); // CS136_CST
       sqlBuff.append("              ?,               \n"); // CS136_HTY
       sqlBuff.append("              ?,               \n"); // CS136_ARA
       sqlBuff.append("              ?,               \n"); // CS136_BSU
       sqlBuff.append("              ?,               \n"); // CS136_GBU
       sqlBuff.append("              ?,               \n"); // CS136_AGB
       sqlBuff.append("              ?,               \n"); // CS136_ABG
       sqlBuff.append("              ?,               \n"); // CS136_RGB
       sqlBuff.append("              ?,               \n"); // CS136_IIS
       sqlBuff.append("              ?,               \n"); // CS136_PLT
       sqlBuff.append("              ?,               \n"); // CS136_PST
       sqlBuff.append("              ?,               \n"); // CS136_FDT
       sqlBuff.append("              ?,               \n"); // CS136_TOD
       sqlBuff.append("              ?,               \n"); // CS136_JDQ
       sqlBuff.append("              ?,               \n"); // CS136_JDJ
       sqlBuff.append("              ?,               \n"); // CS136_BDA
       sqlBuff.append("              ?,               \n"); // CS136_BPD
       sqlBuff.append("              ?,               \n"); // CS136_CPR
       sqlBuff.append("              ?,               \n"); // CS136_JSD
       sqlBuff.append("              ?,               \n"); // CS136_JYQ
       sqlBuff.append("              ?,               \n"); // CS136_JYC
       sqlBuff.append("              ?,               \n"); // CS136_BYA
       sqlBuff.append("              ?,               \n"); // CS136_IS1
       sqlBuff.append("              ?,               \n"); // CS136_IS2
       sqlBuff.append("              ?,               \n"); // CS136_IS3
       sqlBuff.append("              ?,               \n"); // CS136_IS4
       sqlBuff.append("              ?,               \n"); // CS136_JYD
       sqlBuff.append("              ?,               \n"); // CS136_JYJ
       sqlBuff.append("              ?,               \n"); // CS136_IDQ
       sqlBuff.append("              ?,               \n"); // CS136_IDJ
       sqlBuff.append("              ?,               \n"); // CS136_BAM
       sqlBuff.append("              ?,               \n"); // CS136_IY1
       sqlBuff.append("              ?,               \n"); // CS136_IY2
       sqlBuff.append("              ?,               \n"); // CS136_IY3
       sqlBuff.append("              ?,               \n"); // CS136_IY4
       sqlBuff.append("              ?,               \n"); // CS136_JAR
       sqlBuff.append("              ?,               \n"); // CS136_SS1
       sqlBuff.append("              ?,               \n"); // CS136_SDT
       sqlBuff.append("              ?,               \n"); // CS136_APP
       sqlBuff.append("              ?,               \n"); // CS136_GGB
       sqlBuff.append("              ?,               \n"); // CS136_GYM
       sqlBuff.append("              ?,               \n"); // CS136_MGB
       sqlBuff.append("              ?,               \n"); // CS136_MSA
       sqlBuff.append("              ?,               \n"); // CS136_MDT
       sqlBuff.append("              ?,               \n"); // CRDAT
       sqlBuff.append("              ?                \n"); // CRTIM
       sqlBuff.append("             )                 \n");

       try
       {
          int idxparam = 1;
          pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
          pstmt.setString(idxparam++, mandt);//MADNT
          pstmt.setString(idxparam++, vo.getCebxrpjt());//CS136_PJT
          pstmt.setString(idxparam++, vo.getCebxrhno());//CS136_HNO
          pstmt.setString(idxparam++, vo.getCebxrseq());//CS136_SEQ
          pstmt.setString(idxparam++, vo.getCebxrgnd());//CS136_GND
          pstmt.setString(idxparam++, vo.getCebxrjym());//CS136_JYM
          pstmt.setString(idxparam++, vo.getCebxrisr());//CS136_ISR
          pstmt.setString(idxparam++, vo.getCebxrupn());//CS136_UPN
          pstmt.setString(idxparam++, vo.getCebxrcst());//CS136_CST
          pstmt.setString(idxparam++, vo.getCebxrhty());//CS136_HTY
          pstmt.setString(idxparam++, vo.getCebxrara());//CS136_ARA
          pstmt.setString(idxparam++, vo.getCebxrbsu());//CS136_BSU
          pstmt.setString(idxparam++, vo.getCebxrgbu());//CS136_GBU
          pstmt.setString(idxparam++, vo.getCebxragb());//CS136_AGB
          pstmt.setString(idxparam++, vo.getCebxrabg());//CS136_ABG
          pstmt.setString(idxparam++, vo.getCebxrrgb());//CS136_RGB
          pstmt.setString(idxparam++, vo.getCebxriis());//CS136_IIS
          pstmt.setDouble(idxparam++, new Double(vo.getCebxrplt()).doubleValue());//CS136_PLT
          pstmt.setString(idxparam++, vo.getCebxrpst());//CS136_PST
          pstmt.setString(idxparam++, vo.getCebxrfdt());//CS136_FDT
          pstmt.setString(idxparam++, vo.getCebxrtod());//CS136_TOD
          pstmt.setDouble(idxparam++, new Double(vo.getCebxrjdq()).doubleValue());//CS136_JDQ
          pstmt.setDouble(idxparam++, new Double(vo.getCebxrjdj()).doubleValue());//CS136_JDJ
          pstmt.setDouble(idxparam++, new Double(vo.getCebxrbda()).doubleValue());//CS136_BDA
          pstmt.setDouble(idxparam++, new Double(vo.getCebxrbpd()).doubleValue());//CS136_BPD
          pstmt.setDouble(idxparam++, new Double(vo.getCebxrcpr()).doubleValue());//CS136_CPR
          pstmt.setString(idxparam++, vo.getCebxrjsd());//CS136_JSD
          pstmt.setDouble(idxparam++, new Double(vo.getCebxrjyq()).doubleValue());//CS136_JYQ
          pstmt.setDouble(idxparam++, new Double(vo.getCebxrjyc()).doubleValue());//CS136_JYC
          pstmt.setDouble(idxparam++, new Double(vo.getCebxrbya()).doubleValue());//CS136_BYA
          pstmt.setDouble(idxparam++, new Double(vo.getCebxris1()).doubleValue());//CS136_IS1
          pstmt.setDouble(idxparam++, new Double(vo.getCebxris2()).doubleValue());//CS136_IS2
          pstmt.setDouble(idxparam++, new Double(vo.getCebxris3()).doubleValue());//CS136_IS3
          pstmt.setDouble(idxparam++, new Double(vo.getCebxris4()).doubleValue());//CS136_IS4
          pstmt.setString(idxparam++, vo.getCebxrjyd());//CS136_JYD
          pstmt.setString(idxparam++, vo.getCebxrjyj());//CS136_JYJ
          pstmt.setDouble(idxparam++, new Double(vo.getCebxridq()).doubleValue());//CS136_IDQ
          pstmt.setDouble(idxparam++, new Double(vo.getCebxridj()).doubleValue());//CS136_IDJ
          pstmt.setDouble(idxparam++, new Double(vo.getCebxrbam()).doubleValue());//CS136_BAM
          pstmt.setDouble(idxparam++, new Double(vo.getCebxriy1()).doubleValue());//CS136_IY1
          pstmt.setDouble(idxparam++, new Double(vo.getCebxriy2()).doubleValue());//CS136_IY2
          pstmt.setDouble(idxparam++, new Double(vo.getCebxriy3()).doubleValue());//CS136_IY3
          pstmt.setDouble(idxparam++, new Double(vo.getCebxriy4()).doubleValue());//CS136_IY4
          pstmt.setString(idxparam++, vo.getCebxrjar());//CS136_JAR
          pstmt.setString(idxparam++, vo.getCebxrss1());//CS136_SS1
          pstmt.setString(idxparam++, vo.getCebxrsdt());//CS136_SDT
          pstmt.setString(idxparam++, vo.getCebxrapp());//CS136_APP
          pstmt.setString(idxparam++, vo.getCebxrggb());//CS136_GGB
          pstmt.setString(idxparam++, vo.getCebxrgym());//CS136_GYM
          pstmt.setString(idxparam++, "Y");//CS136_MGB
          pstmt.setString(idxparam++, "51");//CS136_MSA
          pstmt.setString(idxparam++, DateTime.getShortDateString());//CS136_MDT
          
          //===========================생성일자 및 생성일시 추가 20201130 Han.JH=============================
          pstmt.setString(idxparam++, DateTime.getShortDateString());//CRDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
          pstmt.setString(idxparam++, DateTime.getShortTimeString());//CRTIM. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
          //=======================================================================================

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
	  * 작성자 : 오현범
	  * 작성일 : 2006.09.11
	  * 설  명 : 이전 보수협력사의 기성비계획취소처리
	  * 기  타 : 
	  */ 
/*
	 public int updateBXR (String mandt, Connection conn, TBEBXRF1Vo vo, String chb) throws SQLException, Exception
	 {
			StringBuffer               sqlBuff  = new StringBuffer();
			LoggablePreparedStatement  pstmt    = null;
	      
			sqlBuff.append(" UPDATE                         \n");
			sqlBuff.append("        SAPHEE.ZCST136          \n");
			sqlBuff.append("    SET                         \n");
			sqlBuff.append("        CS136_MGB =  'Y',       \n");
			sqlBuff.append("        CS136_MSA =  '61',      \n");
			sqlBuff.append("        CS136_MDT =  ?          \n");
			sqlBuff.append("  WHERE 1=1                     \n");
			sqlBuff.append("    AND MANDT =  ?              \n");
			sqlBuff.append("    AND CS136_PJT =  ?          \n");
			sqlBuff.append("    AND CS136_HNO =  ?          \n");
			sqlBuff.append("    AND CS136_SEQ =  ?          \n");
			sqlBuff.append("    AND CS136_GND =  ?          \n");
			sqlBuff.append("    AND CS136_JYM >= ?          \n");
			sqlBuff.append("    AND CS136_ISR =  ?          \n");
			sqlBuff.append("    AND CS136_BSU =  ?          \n");
			sqlBuff.append("    AND RTRIM(CS136_MSA) =  ''  \n");
	
		try
		{         
			int idxparam = 1;
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());	      

			pstmt.setString(idxparam++, DateTime.getShortDateString());	      
			pstmt.setString(idxparam++, mandt);
			pstmt.setString(idxparam++, vo.getCebxrpjt());
			pstmt.setString(idxparam++, vo.getCebxrhno());
			pstmt.setString(idxparam++, vo.getCebxrseq());
			pstmt.setString(idxparam++, vo.getCebxrgnd());			
			pstmt.setString(idxparam++, vo.getCebxrjym());
			pstmt.setString(idxparam++, vo.getCebxrisr());
			pstmt.setString(idxparam++, chb);			
		
	
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
	  * 설  명 : 기성비 계획에서 현재월 이후 데이터만 이관후 협력사로 UPDATE
	  * 기  타 : 
	  */ 
	 public int updateBXR (String mandt, Connection conn, TBEBWZF1Vo vo, String seq) throws SQLException, Exception
	 {
		StringBuffer               sqlBuff  = new StringBuffer();
		LoggablePreparedStatement  pstmt    = null;
      
		sqlBuff.append(" UPDATE                                                                                         \n");
		sqlBuff.append("        SAPHEE.ZCST136 A                                                                        \n");
		sqlBuff.append("    SET                                                                                         \n");
		sqlBuff.append("        (                                                                                       \n");
		sqlBuff.append("         A.CS136_ARA,                                                                           \n");
		sqlBuff.append("         A.CS136_BSU,                                                                           \n");
		sqlBuff.append("         A.CS136_GBU,                                                                           \n");
		sqlBuff.append("         A.CS136_GGB,                                                                           \n");
		sqlBuff.append("         A.CS136_MGB,                                                                           \n");
		sqlBuff.append("         A.CS136_MSA,                                                                           \n");
		sqlBuff.append("         A.CS136_AGB,                                                                           \n");
		sqlBuff.append("         A.UPDAT,                                                                               \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
		sqlBuff.append("         A.UPZET                                                                                \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
		sqlBuff.append("        )                                                                                       \n");
		sqlBuff.append("        =                                                                                       \n");
		sqlBuff.append("        (                                                                                       \n");
		sqlBuff.append("         SELECT                                                                                 \n");
		sqlBuff.append("                B.BSU_ARA,                                                                      \n");
		sqlBuff.append("                (SELECT C.LGORT FROM SAPHEE.ZMMT005 C WHERE C.MANDT = ? AND C.LIFNR = B.LIFNR), \n");
		sqlBuff.append("                B.BSU_GB,                                                                       \n");
		sqlBuff.append("                CASE B.BSU_GB WHEN '1' THEN '9' WHEN '3' THEN '' ELSE '' END,                   \n");
		sqlBuff.append("                CASE B.BSU_GB WHEN '1' THEN 'Y' WHEN '3' THEN '' ELSE '' END,                   \n");
		sqlBuff.append("                CASE B.BSU_GB WHEN '1' THEN '09' WHEN '3' THEN '' ELSE '' END,                  \n");
		sqlBuff.append("                CAST(? AS VARCHAR(5)),                                                          \n");
		sqlBuff.append("                HEX(CURRENT DATE),                                                              \n");
		sqlBuff.append("                HEX(CURRENT TIME)                                                               \n");
		sqlBuff.append("           FROM                                                                                 \n");
		sqlBuff.append("                SAPHEE.ZWBT010 B                                                                \n");
		sqlBuff.append("          WHERE                                                                                 \n");
		sqlBuff.append("                B.MANDT = ?                                                                     \n");
		sqlBuff.append("            AND B.LGORT = ?                                                                     \n");
		sqlBuff.append("        )                                                                                       \n");
		sqlBuff.append("  WHERE 1=1                                                                                     \n");
		sqlBuff.append("    AND A.MANDT     =  ?                                                                        \n");
		sqlBuff.append("    AND A.CS136_PJT =  ?                                                                        \n");
		sqlBuff.append("    AND A.CS136_HNO =  ?                                                                        \n");
//		sqlBuff.append("    AND A.CS136_SEQ >= ?                                                                        \n");
		sqlBuff.append("    AND A.CS136_GND =  ?                                                                        \n");
		sqlBuff.append("    AND A.CS136_GYM >= ?                                                                        \n");
		sqlBuff.append("    AND (RTRIM(A.CS136_MGB) =  '' OR (RTRIM(A.CS136_MGB) = 'Y' AND RTRIM(A.CS136_MSA) = '09'))  \n");

		try
		{         
			int idxparam = 1;
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());	      

			pstmt.setString(idxparam++, mandt);
			pstmt.setString(idxparam++, vo.getCebwzgba());
			pstmt.setString(idxparam++, mandt);
			pstmt.setString(idxparam++, vo.getCebwzcha());
			pstmt.setString(idxparam++, mandt);
			pstmt.setString(idxparam++, vo.getCebwzpjt());
			pstmt.setString(idxparam++, vo.getCebwzhno());
//			pstmt.setString(idxparam++, seq);
			pstmt.setString(idxparam++, vo.getCebwzgnd());			
			pstmt.setString(idxparam++, vo.getCebwzigm());
		
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
     * 작성자: jkkoo
     * 작성일: 2006-04-30
     * 설  명:
     * 기  타: basGisungbi[53224, 47924, 47151, 44824]
     */
   public static double getBasGisungbi1(TBEBXRF1Vo bxrVo)
   {
      double tmpGisungbi = 0;

      if("Y".equals(bxrVo.getCebxrhty()))
      {
         tmpGisungbi = basGisungbi[0];  // 53224
      }
      else if(!("E5".equals(bxrVo.getCebxrara()) || "G8".equals(bxrVo.getCebxrara())))
      {
         if("1".equals(bxrVo.getCebxragb()))
            tmpGisungbi = basGisungbi[2]; // 47151;
         else
            tmpGisungbi = basGisungbi[0]; // 53224;
      }
      else if(!"1".equals(bxrVo.getCebxragb()))
      {
         tmpGisungbi = basGisungbi[0]; // 53224;
      }
      else if("A".equals(bxrVo.getCebxrabg()))
      {
         tmpGisungbi = basGisungbi[3];  //44824;
      }
      else
      {
         tmpGisungbi = basGisungbi[1]; // 47924;
      }
      return tmpGisungbi;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-30
     * 설  명:
     * 기  타:
     */
   public static double getBasGisungbi2(TBEBXRF1Vo bxrVo, TBEBWZF1Vo bwzVo, double machul) throws Exception
   {
      double   tmpGisungbi    = 0;
      double   monthSaleAmt   = machul;
      double   calcGisungbi   = monthSaleAmt * 0.9;

      String   ara = bwzVo.getCebwzchb().substring(1,3);
      if("Y".equals(bxrVo.getCebxrhty()))
      {
         tmpGisungbi = 53224;
      }
      else if(!("E5".equals(ara) || "G8".equals(ara)))
      {
         if("1".equals(bwzVo.getCebwzgba()))
         {
            if(monthSaleAmt < 52390)
            {
               tmpGisungbi = calcGisungbi;
            }
            else
            {
               tmpGisungbi = 47151;
            }
         }
         else
         {
            if(monthSaleAmt < 59138)
            {
               tmpGisungbi = calcGisungbi;
            }
            else
            {
               tmpGisungbi = 53224;
            }
         }
      }
      else if(!"1".equals(bwzVo.getCebwzgba()))
      {
         if(monthSaleAmt < 59138)
         {
            tmpGisungbi = calcGisungbi;
         }
         else
         {
            tmpGisungbi = 53224;
         }
      }
      else if("A".equals(bxrVo.getCebxrabg()))
      {
         if(monthSaleAmt < 50000)
         {
            tmpGisungbi = calcGisungbi;
         }
         else
         {
            if(monthSaleAmt >= 100000)
            {
               tmpGisungbi = 44824;
            }
            else
            {
               tmpGisungbi = 47924;
            }
         }
      }
      else
      {
         tmpGisungbi = 47924;
      }
      return tmpGisungbi;
   }
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 유상계약정보에서 매출액을 가져온다.
    * 기  타 :
    */
   public double getMaechul(String mandt, TBEBWZF1Vo vo, String seq, Connection conn) throws Exception
   {

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      double  rtnValue = 0;

      sqlBuff.append(" SELECT                    \n");
      sqlBuff.append("        CS126_TOT,         \n");
      sqlBuff.append("        CS126_VAT,         \n");
      sqlBuff.append("        CS126_AMT          \n");
      sqlBuff.append("   FROM                    \n");
      sqlBuff.append("        SAPHEE.ZCST126     \n");
      sqlBuff.append("  WHERE 1=1                \n");
      sqlBuff.append("    AND MANDT = ?          \n");
      sqlBuff.append("    AND CS126_UPN = ?      \n");
      sqlBuff.append("    AND CS126_CST = ?      \n");
      sqlBuff.append("    AND CS126_PJT = ?      \n");
      sqlBuff.append("    AND CS126_HNO = ?      \n");
      sqlBuff.append("    AND CS126_SEQ = ?      \n");
      sqlBuff.append("  WITH UR                  \n");

      try
      {
         int idxparam = 1;
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, vo.getCebwzupn());
         pstmt.setString(idxparam++, vo.getCebwzcst());
         pstmt.setString(idxparam++, vo.getCebwzpjt());
         pstmt.setString(idxparam++, vo.getCebwzhno());
         pstmt.setString(idxparam++, seq);


         //logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
            rtnValue = rs.getDouble("CS126_TOT");
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
      return rtnValue;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-11
     * 설  명: 입력된 날짜를 기준으로 만근 여부 체크 .
     * 기  타:
     */
   public static boolean isMangunChk(String fdt, String tod) throws Exception
   {
      boolean returnVal = false;

      if(fdt.substring(6,8).equals("01") && DateTime.lastDayOfMonth(tod).equals(tod))
         returnVal = true;
      else
         returnVal = false;


      return returnVal;
   }
   /**
    * 작성자 : 구자경
    * 작성일 : 2006.05.24
    * 설  명 : 기성비계획 정보에 대한 점검이관 처리 생성자..
    * 기  타 :
    */

   public TransForBXR(Logger pLogger)
   {
      logger = pLogger;
   }
}