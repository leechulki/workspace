/**
 * 파 일 명 : TBEBWNF1Dao.java
 * 작 성 자 : shhwang
 * 작 성 일 : 2006-04-05
 * 설    명 : 인원상주정보 Dao
 * 변경내역 :
 *  
 */
package com.helco.xf.cs12.evladm.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import tit.biz.AbstractBusinessService;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWNF1Vo;

// import org.apache.log4j.Logger;

public class TBEBWNF1Dao extends AbstractBusinessService implements FrwCrudDAO
{
//	static Logger logger = Logger.getLogger(TBEBWMF1Dao.class);
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public void insert(Object obj) throws Exception
	{
		// TODO 자동 생성된 메소드 스텁
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public ArrayList selectList(Object searchCondVO) throws Exception
	{
		ArrayList resultList = new ArrayList();
		return resultList;
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public Object select(Object condVO) throws Exception
	{ 
      ComMethodVo vo = (ComMethodVo) condVO;
      TBEBWNF1Vo rtnVo = new TBEBWNF1Vo();
      StringBuffer sqlBuff = new StringBuffer();
      LoggablePreparedStatement pstmt = null;
      ResultSet rs = null;
      Connection conn = null;
      
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     A.MANDT,                   \n");
      sqlBuff.append("     CS127_UPN,                 \n");
      sqlBuff.append("     CS127_CST,                 \n");
      sqlBuff.append("     CS127_SEQ,                 \n");
      sqlBuff.append("     CS127_ISR,                 \n");
      sqlBuff.append("     CS127_PST,                 \n");
      sqlBuff.append("     CS127_GND,                 \n");
      sqlBuff.append("     CS127_BSU,                 \n");
      sqlBuff.append("     CS127_SFR,                 \n");
      sqlBuff.append("     CS127_STO,                 \n");
      sqlBuff.append("     CS127_CQT,                 \n");
      sqlBuff.append("     CS127_STD,                 \n");
      sqlBuff.append("     CS127_CMD,                 \n");
      sqlBuff.append("     CS127_UHJ,                 \n");
      sqlBuff.append("     CS127_AMT,                 \n");
      sqlBuff.append("     CS127_VAT,                 \n");
      sqlBuff.append("     CS127_TOT,                 \n");
      sqlBuff.append("     CS127_RTO,                 \n");
      sqlBuff.append("     CS127_SDT,                 \n");
      sqlBuff.append("     CS127_APP,                 \n");
      sqlBuff.append("     (SELECT BSU_ARA FROM SAPHEE.ZWBT010 WHERE MANDT = A.MANDT AND LGORT = A.CS127_BSU) CS126_ARA, \n");
      sqlBuff.append("     CS126_BJG,                 \n");
      sqlBuff.append("     CS126_TIS,                 \n");
      sqlBuff.append("     CS127_AMBT,                \n");
      sqlBuff.append("     CS127_VABT,                \n");
      sqlBuff.append("     CS127_TOBT,                \n");
      sqlBuff.append("     CS127_BDGBN                \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     SAPHEE.ZCST127 A,          \n");
      sqlBuff.append("     SAPHEE.ZCST126 B           \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   A.MANDT   = ?           \n");
      sqlBuff.append("  AND   CS127_UPN = ?           \n");
      sqlBuff.append("  AND   CS127_CST = ?           \n");
      sqlBuff.append("  AND   CS127_SEQ = ?           \n");
      sqlBuff.append("  AND   CS127_ISR = ?           \n");
      sqlBuff.append("  AND   A.MANDT = B.MANDT       \n");
      sqlBuff.append("  AND   CS127_UPN = CS126_UPN   \n");
      sqlBuff.append("  AND   CS127_CST = CS126_CST   \n");
      sqlBuff.append("  AND   CS126_USD <= CS127_STD  \n");
      sqlBuff.append("  AND   CS126_UHJ >= CS127_CMD  \n");
          
      try
      {
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
	     conn  = getConnection(db_con);

         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(1, vo.getMandt());
         pstmt.setString(2, vo.getUpn());
         pstmt.setString(3, CommonUtil.NullToBlank(vo.getCst()));
         pstmt.setString(4, vo.getSeq());
         pstmt.setString(5, vo.getIsr());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
             rtnVo.setMandt(rs.getString("MANDT"));
             rtnVo.setCebwnupn(rs.getString("CS127_UPN"));
             rtnVo.setCebwncst(rs.getString("CS127_CST"));
             rtnVo.setCebwnseq(rs.getString("CS127_SEQ"));
             rtnVo.setCebwnisr(rs.getString("CS127_ISR"));
             rtnVo.setCebwnpst(rs.getString("CS127_PST"));
             rtnVo.setCebwngnd(rs.getString("CS127_GND"));
             rtnVo.setCebwnbsu(rs.getString("CS127_BSU"));
             rtnVo.setCebwnsfr(rs.getString("CS127_SFR"));
             rtnVo.setCebwnsto(rs.getString("CS127_STO"));
             rtnVo.setCebwncqt(rs.getString("CS127_CQT"));
             rtnVo.setCebwnstd(rs.getString("CS127_STD"));
             rtnVo.setCebwncmd(rs.getString("CS127_CMD"));
             rtnVo.setCebwnuhj(rs.getString("CS127_UHJ"));
             rtnVo.setCebwnamt(rs.getString("CS127_AMT"));
             rtnVo.setCebwnvat(rs.getString("CS127_VAT"));
             rtnVo.setCebwntot(rs.getString("CS127_TOT"));
             rtnVo.setCebwnrto(rs.getString("CS127_RTO"));
             rtnVo.setCebwnsdt(rs.getString("CS127_SDT"));
             rtnVo.setCebwnapp(rs.getString("CS127_APP"));
             rtnVo.setCebwnara(rs.getString("CS126_ARA"));
             rtnVo.setCebwnbjg(rs.getString("CS126_BJG"));
             rtnVo.setCebwnjdj(vo.getRto());
             rtnVo.setCebwnjyj(vo.getUserId());
             rtnVo.setGno(vo.getGno());
             rtnVo.setSla(vo.getSla());
             rtnVo.setImsitis(rs.getString("CS126_TIS"));
             rtnVo.setCebwnambt(rs.getString("CS127_AMBT"));
             rtnVo.setCebwnvabt(rs.getString("CS127_VABT"));
             rtnVo.setCebwntobt(rs.getString("CS127_TOBT"));
             rtnVo.setCebwnbdgbn(rs.getString("CS127_BDGBN"));
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         try
         {
            DBUtil.close(rs, pstmt, null);
         }
         catch (Exception e)
         {
         }
      }
      return rtnVo;
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public Object select2(Object condVO) throws Exception
	{
      ComMethodVo vo2 = (ComMethodVo) condVO;
      TBEBWNF1Vo rtnVo2 = new TBEBWNF1Vo();
      StringBuffer sqlBuff2 = new StringBuffer();
      LoggablePreparedStatement pstmt2 = null;
      ResultSet rs2 = null;
      Connection conn2 = null;

      sqlBuff2.append("  SELECT                            \n");
      sqlBuff2.append("     A.MANDT,                       \n");
      sqlBuff2.append("     A.CS127_UPN,                   \n");
      sqlBuff2.append("     A.CS127_CST,                   \n");
      sqlBuff2.append("     A.CS127_SEQ,                   \n");
      sqlBuff2.append("     A.CS127_ISR,                   \n");
      sqlBuff2.append("     A.CS127_PST,                   \n");
      sqlBuff2.append("     A.CS127_GND,                   \n");
      sqlBuff2.append("     A.CS127_BSU,                   \n");
      sqlBuff2.append("     A.CS127_SFR,                   \n");
      sqlBuff2.append("     A.CS127_STO,                   \n");
      sqlBuff2.append("     A.CS127_SYO,                   \n");
      sqlBuff2.append("     A.CS127_CQT,                   \n");
      sqlBuff2.append("     A.CS127_STD,                   \n");
      sqlBuff2.append("     A.CS127_CMD,                   \n");
      sqlBuff2.append("     A.CS127_UHJ,                   \n");
      sqlBuff2.append("     A.CS127_AMT,                   \n");
      sqlBuff2.append("     A.CS127_VAT,                   \n");
      sqlBuff2.append("     A.CS127_TOT,                   \n");
      sqlBuff2.append("     A.CS127_RTO,                   \n");
      sqlBuff2.append("     A.CS127_SDT,                   \n");
      sqlBuff2.append("     A.CS127_APP,                   \n");
      sqlBuff2.append("     A.CS127_AMBT,                  \n");
      sqlBuff2.append("     A.CS127_VABT,                  \n");
      sqlBuff2.append("     A.CS127_TOBT,                  \n");
      sqlBuff2.append("     A.CS127_BDGBN                  \n");
      sqlBuff2.append("  FROM                              \n");
      sqlBuff2.append("     SAPHEE.ZCST127 A               \n");
      sqlBuff2.append("  WHERE    1=1                      \n");
      sqlBuff2.append("  AND   A.MANDT     = ?             \n");
      sqlBuff2.append("  AND   A.CS127_UPN = ?             \n");
      sqlBuff2.append("  AND   A.CS127_CST = ?             \n");
      sqlBuff2.append("  AND   A.CS127_SEQ = ?             \n");
      
      try
      {
    	 String db_con = Utillity.getSapJndi(vo2.getMandt());
	     conn2 = getConnection(db_con);

	     int idxparam = 1;
	     pstmt2 = new LoggablePreparedStatement(conn2, sqlBuff2.toString());

	     pstmt2.setString(idxparam++, vo2.getMandt());
         pstmt2.setString(idxparam++, vo2.getUpn());
         pstmt2.setString(idxparam++, CommonUtil.NullToBlank(vo2.getCst()));
         pstmt2.setString(idxparam++, vo2.getMaxSeq());

         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt2).getQueryString());
         rs2 = pstmt2.executeQuery();
//System.out.println("rs2.next() ==> [" + rs2.next() + "]");
         if (rs2.next())
         {
             rtnVo2.setMandt(rs2.getString("MANDT"));
             rtnVo2.setCebwnupn(rs2.getString("CS127_UPN"));
             rtnVo2.setCebwncst(rs2.getString("CS127_CST"));
             rtnVo2.setCebwnseq(rs2.getString("CS127_SEQ"));
             rtnVo2.setCebwnisr(rs2.getString("CS127_ISR"));
             rtnVo2.setCebwnpst(rs2.getString("CS127_PST"));
             rtnVo2.setCebwngnd(rs2.getString("CS127_GND"));
             rtnVo2.setCebwnbsu(rs2.getString("CS127_BSU"));
             rtnVo2.setCebwnsfr(rs2.getString("CS127_SFR"));
             rtnVo2.setCebwnsto(rs2.getString("CS127_STO"));
             rtnVo2.setCebwnsyo(rs2.getString("CS127_SYO"));
             rtnVo2.setCebwncqt(rs2.getString("CS127_CQT"));
             rtnVo2.setCebwnstd(rs2.getString("CS127_STD"));
             rtnVo2.setCebwncmd(rs2.getString("CS127_CMD"));
             rtnVo2.setCebwnuhj(rs2.getString("CS127_UHJ"));
             rtnVo2.setCebwnamt(rs2.getString("CS127_AMT"));
             rtnVo2.setCebwnvat(rs2.getString("CS127_VAT"));
             rtnVo2.setCebwntot(rs2.getString("CS127_TOT"));
             rtnVo2.setCebwnrto(rs2.getString("CS127_RTO"));
             rtnVo2.setCebwnsdt(rs2.getString("CS127_SDT"));
             rtnVo2.setCebwnapp(rs2.getString("CS127_APP"));
             rtnVo2.setCebwnjyj(vo2.getUserId());
             rtnVo2.setGno(vo2.getGno());
             rtnVo2.setCebwnambt(rs2.getString("CS127_AMBT"));
             rtnVo2.setCebwnvabt(rs2.getString("CS127_VABT"));
             rtnVo2.setCebwntobt(rs2.getString("CS127_TOBT"));
             rtnVo2.setCebwnbdgbn(rs2.getString("CS127_BDGBN"));
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         try
         {
            DBUtil.close(rs2, pstmt2, null);
         }
         catch (Exception e)
         {
         }
      }
      return rtnVo2;
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public Object select(ArrayList compPk, String subMethodFlag) throws Exception
	{
		// TODO 자동 생성된 메소드 스텁
		return null;
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public void deleteList(String pkList) throws Exception
	{
		// TODO 자동 생성된 메소드 스텁
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public void deleteList(Object pkList, String subMethodFlag) throws Exception
	{
		Connection conn = null;
		ArrayList alist = (ArrayList) pkList;
		try
		{
			if (alist.size() > 0)
			{
				conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
				if ("GY020103".equals(subMethodFlag)) // 2006.03.10 shwhang
					deleteRowForGY020103(alist, conn);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			DBUtil.close(null, null, conn);
		}
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public void insert(Object vo, String subMethodFlag) throws Exception
	{
		Connection conn = null;
		ArrayList alist = (ArrayList) vo;
		try
		{
			if (alist.size() > 0)
			{
				conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
				if ("GY020103".equals(subMethodFlag)) // 2006.04.05shwhang
					insertRowForGY020103(alist, conn);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			DBUtil.close(null, null, conn);
		}
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public void update(Object obj) throws Exception
	{
		// TODO 자동 생성된 메소드 스텁		
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public void update(Object vo, String subMethodFlag) throws Exception
	{
		Connection conn = null;
		ArrayList alist = (ArrayList) vo;
		try
		{
			if (alist.size() > 0)
			{
				conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
				if ("GY020103".equals(subMethodFlag)) // 2006.04.04 shhwang
					updateRowForGY020103(alist, conn);
				if ("GY020103_02".equals(subMethodFlag)) // 2006.04.07 shhwang
					updateRowForGY020103_02(alist, conn);
				if ("GY020103_04".equals(subMethodFlag)) // 2006.08.25 shhwang
					updateRowForGY020103_04(alist, conn);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			DBUtil.close(null, null, conn);
		}
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public void updateRow(ArrayList list, Connection conn) throws Exception
	{
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public void insertRow(ArrayList alist, Connection conn) throws Exception
	{
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public void deleteRow(ArrayList alist, Connection conn) throws Exception
	{
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-13 
	 * 설  명: 
	 * 기  타:
	 */
	public int multiSaveForGrid(Object iobj, Object uobj, Object dobj) throws Exception
	{
		return 0;
	}
	/**
	* 작성자 : shhwang
	* 작성일 : 2006.04.04
	* 설명    : 인원상주 정보  insert 한다 . 25개 칼럼 
	* 기타    : 
	*/
	public void insertRowForGY020103(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = null;
		TBEBWNF1Vo vo = new TBEBWNF1Vo();
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWNF1Vo();
				vo = (TBEBWNF1Vo) list.get(i);
				sqlBuff = new StringBuffer();
				//					bwm 입력칼럼갯수 25(전체 )									 
				sqlBuff.append("INSERT  INTO  EVLADM.TBEBWNF1 (  \n");
				sqlBuff.append("			 CEBWNUPN,           \n");
				sqlBuff.append("			 CEBWNCST,               \n");
				sqlBuff.append("			 CEBWNSEQ,    \n");
				sqlBuff.append("			 CEBWNISR,  \n");
				sqlBuff.append("			 CEBWNMNO,  \n");
				sqlBuff.append("			 CEBWNPST,  \n");
				sqlBuff.append("			 CEBWNGND,  \n");
				sqlBuff.append("			 CEBWNBSU,  \n");
				sqlBuff.append("			 CEBWNSFR,  \n");
				sqlBuff.append("			 CEBWNSTO,  \n");
				sqlBuff.append("			 CEBWNSYO,  \n");
				sqlBuff.append("			 CEBWNSQT,  \n");
				sqlBuff.append("			 CEBWNSUT,  \n");
				sqlBuff.append("			 CEBWNCQT,  \n");
				sqlBuff.append("			 CEBWNSTD,  \n");
				sqlBuff.append("			 CEBWNCMD,  \n");
				sqlBuff.append("			 CEBWNUYO,  \n");
				sqlBuff.append("			 CEBWNUHJ,  \n");
				sqlBuff.append("			 CEBWNCDQ,  \n");
				sqlBuff.append("			 CEBWNAMT,  \n");
				sqlBuff.append("			 CEBWNVAT,  \n");
				sqlBuff.append("			 CEBWNTOT,  \n");
				sqlBuff.append("			 CEBWNRTO,  \n");
				sqlBuff.append("			 CEBWNBDT,  \n");
				sqlBuff.append("			 CEBWNBSJ)  \n");
				sqlBuff.append(" VALUES ( '" + vo.getCebwnupn() + "',				 \n");
				sqlBuff.append(" 				'" + vo.getCebwncst() + "',				 \n");
				sqlBuff.append("            '" + vo.getCebwnseq() + "',  \n"); //순번 
				sqlBuff.append(" 				'" + vo.getCebwnisr() + "',				 \n");
				sqlBuff.append(" 				'" + vo.getCebwnmno() + "',				 \n");				
				sqlBuff.append(" 				'" + vo.getCebwnpst() + "',				 \n");
				sqlBuff.append(" 				'" + vo.getCebwngnd() + "',				 \n");
				sqlBuff.append(" 				'" + vo.getCebwnbsu() + "',				 \n");
				sqlBuff.append(" 				'" + vo.getCebwnsfr() + "',				 \n");
				sqlBuff.append(" 				'" + vo.getCebwnsto() + "',				 \n");
				sqlBuff.append(" 				" + vo.getCebwnsyo() + ",				 \n");
				sqlBuff.append(" 				" + vo.getCebwnsqt() + ",				 \n");
				sqlBuff.append(" 				" + vo.getCebwnsut() + ",				 \n");
				sqlBuff.append(" 				" + new BigDecimal(vo.getCebwncqt()) + ",				 \n");
				sqlBuff.append(" 				'" + vo.getCebwnsfr() + "',				 \n");
				sqlBuff.append(" 				'" + vo.getCebwnsto() + "',				 \n");
				sqlBuff.append(" 				" + vo.getCebwnsyo() + ",				 \n");
				sqlBuff.append(" 				'" + vo.getCebwnsto() + "',				 \n");
				sqlBuff.append(" 				" + vo.getCebwncdq() + ",				 \n");
				sqlBuff.append(" 				" + new BigDecimal(vo.getCebwnamt()) + ",				 \n");
				sqlBuff.append(" 				" + new BigDecimal(vo.getCebwnvat()) + ",				 \n");
				sqlBuff.append(" 				" + new BigDecimal(vo.getCebwntot()) + ",				 \n");
				sqlBuff.append(" 				" + new BigDecimal(vo.getCebwnrto()) + ",				 \n");
				sqlBuff.append(" 				'" + vo.getCebwnrdt() + "',				 \n");
				sqlBuff.append(" 				'" + vo.getCebwnbsj() + "' )				 \n");
				
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				pstmt.executeUpdate();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	/**
	 * 작성자 : shhwang
	 * 작성일 : 2006.04.05
	 * 설명   : 인원상주정보 저장후 발송시 , 접수시 , 승인시 update 한다 . 
	 * 기타   : 
	 */
	public void updateRowForGY020103(ArrayList alist, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = null;
		TBEBWNF1Vo vo = new TBEBWNF1Vo();
		try
		{
			
			for (int i = 0; i < alist.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWNF1Vo();
				vo = (TBEBWNF1Vo) alist.get(i);
				
				sqlBuff = new StringBuffer();
				
				sqlBuff.append("UPDATE EVLADM.TBEBWNF1 SET  \n");
				sqlBuff.append(" CEBWNPST = ?,   \n");
				sqlBuff.append(" CEBWNGND = ?,   \n");
				sqlBuff.append(" CEBWNBSU = ?,  \n");
				sqlBuff.append(" CEBWNSFR = ?,  \n");
				sqlBuff.append(" CEBWNSTO = ?,  \n");
				sqlBuff.append(" CEBWNSYO = ?,  \n");
				sqlBuff.append(" CEBWNSQT = ?,  \n");
				sqlBuff.append(" CEBWNSUT = ?,  \n");
				sqlBuff.append(" CEBWNCQT = ?,  \n");
				sqlBuff.append(" CEBWNSTD = ?,  \n");
				sqlBuff.append(" CEBWNCMD = ?,  \n");
				sqlBuff.append(" CEBWNUYO = ?,  \n");
				sqlBuff.append(" CEBWNCDQ = ?,  \n");
				sqlBuff.append(" CEBWNAMT = ?,  \n");
				sqlBuff.append(" CEBWNVAT = ?,  \n");
				sqlBuff.append(" CEBWNTOT = ?,  \n");
				if("A1".equals(vo.getCebwnpst()) || "A2".equals(vo.getCebwnpst()))	
					sqlBuff.append(" CEBWNRTO = ? \n");	
				else
					sqlBuff.append(" CEBWNRTO = ?,  \n");
				
				if("A3".equals(vo.getCebwnpst()))		
				{
					sqlBuff.append(" CEBWNBDT = ?,  \n"); //발송일자 
					sqlBuff.append(" CEBWNBSJ = ?   \n");
				}
				if("A5".equals(vo.getCebwnpst()))
				{
					sqlBuff.append("	 CEBWNRDT= ?,  \n"); //접수일자 
					sqlBuff.append("	 CEBWNREQ= ?  \n");
				}
				if("A6".equals(vo.getCebwnpst()))
				{
					sqlBuff.append("	 CEBWNSDT= ?,  \n"); //승인일자 
					sqlBuff.append("	 CEBWNAPP= ?   \n");
				}
				sqlBuff.append("  WHERE CEBWNUPN=? \n"); // 통합ProjNo 
				sqlBuff.append("  AND CEBWNCST=?   \n"); //거래처 
				sqlBuff.append("  AND CEBWNSEQ=?   \n"); //
				sqlBuff.append("  AND CEBWNISR=?   \n"); //   
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				
				pstmt.setString(idxparam++, vo.getCebwnpst());
				pstmt.setString(idxparam++, vo.getCebwngnd());
				pstmt.setString(idxparam++, vo.getCebwnbsu());
				pstmt.setString(idxparam++, vo.getCebwnsfr());
				pstmt.setString(idxparam++, vo.getCebwnsto());
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwnsyo()));
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwnsqt()));
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwnsut()));
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwncqt()));
				pstmt.setString(idxparam++, vo.getCebwnsfr());
				pstmt.setString(idxparam++, vo.getCebwnsto());
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwnsyo()));
				pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwncdq()));
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwnamt()));
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwnvat()));
				pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwntot()));
				pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebwnrto()));				
				if("A3".equals(vo.getCebwnpst()))	
				{
					pstmt.setString(idxparam++, vo.getCebwnbdt());
					pstmt.setString(idxparam++, vo.getCebwnbsj());
				}
				if("A5".equals(vo.getCebwnpst()))
				{
					pstmt.setString(idxparam++, vo.getCebwnrdt()); //
					pstmt.setString(idxparam++, vo.getCebwnreq()); //
				}	
				if("A6".equals(vo.getCebwnpst()))
				{
					pstmt.setString(idxparam++, vo.getCebwnsdt()); //
					pstmt.setString(idxparam++, vo.getCebwnapp()); // 			
				}
				pstmt.setString(idxparam++, vo.getCebwnupn());
				pstmt.setString(idxparam++, vo.getCebwncst());
				pstmt.setString(idxparam++, vo.getCebwnseq());
				pstmt.setString(idxparam++, vo.getCebwnisr());
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				pstmt.executeUpdate();
				
				/* 2006.09.27 for루프안으로 이동 by ohb */
				if("A6".equals(vo.getCebwnpst())){//승인인 경우만 
					//				기성비 연동
					 ComMethodVo comVo;
					 comVo = new ComMethodVo();
			
					 comVo.setDataId("71");//자료id			 
					 comVo.setJobGubun("I");//작업구분		 
					 comVo.setUpn(vo.getCebwnupn());//통합프로젝트번호
					 comVo.setCst(vo.getCebwncst());//거래처
					 comVo.setPjt(vo.getCebwnupn());//pjt 는 통합 pjt를 적용 
					 comVo.setHgb("Z");
					 comVo.setHno("99");
					 comVo.setSeq(vo.getCebwnseq());
					 comVo.setIsr(vo.getCebwnisr());//일련번호 
					 comVo.setFirstUserId(vo.getCebwnapp());//승인자 

//					 ComMethodDao.SetBXRBXL(comVo);
				}			 
			}
			/*
			if("A6".equals(vo.getCebwnpst())){//승인인 경우만 
				//				기성비 연동
				 ComMethodVo comVo;
				 comVo = new ComMethodVo();
			
				 comVo.setDataId("71");//자료id			 
				 comVo.setJobGubun("I");//작업구분		 
				 comVo.setUpn(vo.getCebwnupn());//통합프로젝트번호
				 comVo.setCst(vo.getCebwncst());//거래처
				 comVo.setPjt(vo.getCebwnupn());//pjt 는 통합 pjt를 적용 
				 comVo.setHgb("Z");
				 comVo.setHno("99");
				 comVo.setSeq(vo.getCebwnseq());
				 comVo.setIsr(vo.getCebwnisr());//일련번호 
				 comVo.setFirstUserId(vo.getCebwnapp());//승인자 

				 ComMethodDao.SetBXRBXL(comVo);
			}			 
            */
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	/**
	 * 작성자 : shhwang
	 * 작성일 : 2006.04.07
	 * 설명   : 인원상주정보 보수pm 반송 , 고객지원부 반송 인 경우  update 한다 . 
	 * 기타   : 
	 */
	public void updateRowForGY020103_02(ArrayList alist, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWNF1Vo vo = new TBEBWNF1Vo();
		try
		{
			sqlBuff.append("UPDATE EVLADM.TBEBWNF1 SET  \n");
			sqlBuff.append(" CEBWNPST = ?,   \n");
			if ("4".equals(vo.getGubun()))
			{ //보수pm인경우 
				sqlBuff.append("	 CEBWNRDT= ?,  \n"); //접수일자 
				sqlBuff.append("	 CEBWNREQ= ?,  \n");
			}
			else
			{ //고객지원부일경우 (3)
				sqlBuff.append("	 CEBWNSDT= ?,  \n"); //승인일자 
				sqlBuff.append("	 CEBWNAPP= ? )  \n");
			}
			sqlBuff.append("  WHERE CEBWNUPN=? \n"); // 통합ProjNo 
			sqlBuff.append("  AND CEBWNCST=?   \n"); //거래처 
			sqlBuff.append("  AND CEBWNSEQ=?   \n"); //
			sqlBuff.append("  AND CEBWNISR=?   \n"); //   
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < alist.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWNF1Vo();
				vo = (TBEBWNF1Vo) alist.get(i);
				pstmt.setString(idxparam++, vo.getCebwnpst());
				if ("4".equals(vo.getGubun()))
				{ //보수pm인경우					  
					pstmt.setString(idxparam++, vo.getCebwnrdt()); //
					pstmt.setString(idxparam++, vo.getCebwnreq()); //
				}
				else
				{ //고객지원부일경우 (3)
					pstmt.setString(idxparam++, vo.getCebwnsdt()); //
					pstmt.setString(idxparam++, vo.getCebwnapp()); //  
				}
				pstmt.setString(idxparam++, vo.getCebwnupn());
				pstmt.setString(idxparam++, vo.getCebwncst());
				pstmt.setString(idxparam++, vo.getCebwnseq());
				pstmt.setString(idxparam++, vo.getCebwnisr());
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				pstmt.executeUpdate();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	/**
		 * 작성자 : shhwang
		 * 작성일 : 2006.08.25
		 * 설명   : 인원상주정보승인취소의 경우   update 한다 . 
		 * 기타   : 
		 */
		public void updateRowForGY020103_04(ArrayList alist, Connection conn) throws Exception
		{
			LoggablePreparedStatement pstmt = null;
			StringBuffer sqlBuff = new StringBuffer();
			TBEBWNF1Vo vo = new TBEBWNF1Vo();
			try
			{
				sqlBuff.append("UPDATE EVLADM.TBEBWNF1 SET  \n");
				sqlBuff.append(" CEBWNPST = ?,   \n");	
				sqlBuff.append(" CEBWNSDT = ?,   \n");	
				sqlBuff.append(" CEBWNAPP = ?  \n");				
				sqlBuff.append("  WHERE CEBWNUPN=? \n"); // 통합ProjNo 
				sqlBuff.append("  AND CEBWNCST=?   \n"); //거래처 	
				sqlBuff.append("  AND CEBWNSEQ=?   \n"); //			
				sqlBuff.append("  AND CEBWNISR=?   \n"); //   
				sqlBuff.append("  AND CEBWNMNO=?   \n"); // 
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				for (int i = 0; i < alist.size(); i++)
				{
					int idxparam = 1;
					vo = new TBEBWNF1Vo();
					vo = (TBEBWNF1Vo) alist.get(i);
					pstmt.setString(idxparam++, vo.getCebwnpst());		
					pstmt.setString(idxparam++, vo.getCebwnsdt());	
					pstmt.setString(idxparam++, vo.getCebwnapp());				
					pstmt.setString(idxparam++, vo.getCebwnupn());
					pstmt.setString(idxparam++, vo.getCebwncst());
					pstmt.setString(idxparam++, vo.getCebwnseq());
					pstmt.setString(idxparam++, vo.getCebwnisr());
					pstmt.setString(idxparam++, vo.getCebwnmno());
					// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
					pstmt.executeUpdate();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
			finally
			{
				try
				{
					pstmt.close();
				}
				catch (Exception e)
				{
				}
			}
		}	
	/**
    * 작성자 : shhwang
    * 작성일 : 2006.04.07
    * 설명   : 인원상주정보를 삭제한다.  
    * 기타   : 
    */
	public void deleteRowForGY020103(ArrayList alist, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWNF1Vo vo = new TBEBWNF1Vo();
		try
		{
			sqlBuff.append("DELETE FROM  EVLADM.TBEBWNF1  \n");
			sqlBuff.append("  WHERE CEBWNUPN=? \n"); // 통합ProjNo 
			sqlBuff.append("  AND CEBWNCST=?   \n"); //거래처 
			sqlBuff.append("  AND CEBWNSEQ=?   \n"); //
			sqlBuff.append("  AND CEBWNISR=?   \n"); //   
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < alist.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWNF1Vo();
				vo = (TBEBWNF1Vo) alist.get(i);
				pstmt.setString(idxparam++, vo.getCebwnupn());
				pstmt.setString(idxparam++, vo.getCebwncst());
				pstmt.setString(idxparam++, vo.getCebwnseq());
				pstmt.setString(idxparam++, vo.getCebwnisr());
				// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				pstmt.executeUpdate();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
}
