/**
 * �� �� �� : TBEBWEF1Dao.java
 * ��    �� : ���󺸼��⺻����(TBEBWEF1)  Dao
 * �� �� �� : shhwang
 * �� �� �� : 2006-03-22 ����  12:42:31
 * ���泻�� :
 *
 */
package com.helco.xf.cs12.evladm.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import tit.base.ServiceManagerFactory;
import tit.service.core.logger.Logger;

//import org.apache.log4j.Logger;
import tit.biz.AbstractBusinessService;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;

public class TBEBWEF1Dao extends AbstractBusinessService implements FrwCrudDAO
{
	static Logger logger = ServiceManagerFactory.getLogger();
//	static Logger logger = Logger.getLogger(TBEBWEF1Dao.class);
	/**
	 * �ۼ���: shhwang
	 * �ۼ���: 2006-03-22 ���� 1:57:45
	 * ��  ��: 
	 * ��  Ÿ:
	 */

	
	public void insert(Object vo) throws Exception
	{
	}
	/**
	 * �ۼ���: shhwang
	 * �ۼ���: 2006-03-22 ���� 1:57:45
	 * ��  ��: 
	 * ��  Ÿ:
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
				if ("NW010114".equals(subMethodFlag)) // 2006.03.21 shwhang
					insertRowForNW010114(alist, conn);
				if ("GY020105".equals(subMethodFlag)) // 2006.05.25 shwhang �ܿ��Ⱓ����� �μ�Ʈ�� �ش� �� 
					insertRowForGY020105_all(alist, conn);
				if ("GY010501".equals(subMethodFlag)) // 2006.09.04 YKCHOI 
					insertRowForGY010501(alist, conn);					
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
	 * �ۼ���: shhwang
	 * �ۼ���: 2006-03-22 ���� 1:57:45
	 * ��  ��: 
	 * ��  Ÿ:
	 */
	public ArrayList selectList(Object searchCondVO) throws Exception
	{
		return null;
	}
	/**
	 * �ۼ���: jkkoo
	 * �ۼ���: 2006-05-01 
	 * ��  ��: 
	 * ��  Ÿ:
	 */
	public Object select(Object condVO) throws Exception
	{
      ComMethodVo                vo       = (ComMethodVo) condVO;
      TBEBWEF1Vo                 rtnVo    = new TBEBWEF1Vo();
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      Connection                 conn     = null;

      sqlBuff.append(" SELECT                  		\n");
      sqlBuff.append("        A.CS116_GND,     		\n");
      sqlBuff.append("        A.CS116_HTY,     		\n");
      sqlBuff.append("        A.CS116_ARA,     		\n");
      sqlBuff.append("        A.CS116_BSU,     		\n");
      sqlBuff.append("        A.CS116_AGB,     		\n");
      sqlBuff.append("        A.CS116_ABG,     		\n");
      sqlBuff.append("        A.CS116_MG1,     		\n");
      sqlBuff.append("        A.CS116_BGT,     		\n");
      sqlBuff.append("        A.CS116_BMT,     		\n");
      sqlBuff.append("        A.CS116_PLT,    		\n");
      sqlBuff.append("        A.CS116_GNO,     		\n");
      sqlBuff.append("        '' BDGBN,        		\n");
      
      sqlBuff.append("        B.VKGRP,         		\n");
      sqlBuff.append("        B.VKGRP_CO,      		\n");      
      sqlBuff.append("        INT(C.ZMUSAOCNT)  AS ZMUSAOCNT,     \n");
      sqlBuff.append("        INT(C.ZMUSAOCNT2) AS ZMUSAOCNT2,    \n");
      sqlBuff.append("        INT(C.ZMUSAUCNT)  AS ZMUSAUCNT,     \n");
      sqlBuff.append("        INT(C.ZMUSAUCNT2) AS ZMUSAUCNT2,	  \n");   
      sqlBuff.append("        (INT(C.ZMUSAOCNT) + INT(C.ZMUSAOCNT2)) AS ZMUSOSUM,                                         \n");     
      sqlBuff.append("        (INT(C.ZMUSAOCNT) + INT(C.ZMUSAOCNT2) - INT(C.ZMUSAUCNT) - INT(C.ZMUSAUCNT2) ) AS ZMUSOGAP  \n");
      
      sqlBuff.append("   FROM                       \n");
      sqlBuff.append("        SAPHEE.ZCST116 AS A   \n");
      
      sqlBuff.append("   LEFT OUTER JOIN SAPHEE.ZWBT010 AS B \n");      
      sqlBuff.append("     ON A.MANDT = B.MANDT              \n");      
      sqlBuff.append("    AND A.CS116_BSU = B.LGORT          \n");            
      sqlBuff.append("   LEFT OUTER JOIN SAPHEE.ZMASTER02 AS C     \n"); 
      sqlBuff.append("   ON A.MANDT = C.MANDT                      \n"); 
      sqlBuff.append("   AND A.CS116_PJT = C.POSID                 \n"); 
      sqlBuff.append("   AND A.CS116_HNO = SUBSTR(C.POSID_1, 7,3)  \n");     
            
      sqlBuff.append("  WHERE 1=1              		\n");
      sqlBuff.append("    AND A.MANDT = ?        	\n");
      sqlBuff.append("    AND A.CS116_PJT = ?    	\n");
      sqlBuff.append("    AND A.CS116_HNO = ?    	\n");
      sqlBuff.append("    AND A.CS116_SEQ = ?    	\n");
      
      try
      {
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
    	 conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         pstmt.setString(1, vo.getMandt());
         pstmt.setString(2, vo.getPjt());
         pstmt.setString(3, vo.getHno());
         pstmt.setString(4, vo.getSeq());

         rs = pstmt.executeQuery();

         if (rs.next())
         {
            rtnVo.setMandt(vo.getMandt());
            rtnVo.setCebwepjt(vo.getPjt());
            rtnVo.setCebwehno(vo.getHno());
            rtnVo.setCebweseq(vo.getSeq());
            rtnVo.setCebwegnd(rs.getString("CS116_GND"));
            rtnVo.setCebwehty(rs.getString("CS116_HTY"));
            rtnVo.setCebweara(rs.getString("CS116_ARA"));
            rtnVo.setCebwebsu(rs.getString("CS116_BSU"));
            rtnVo.setCebweagb(rs.getString("CS116_AGB"));
            rtnVo.setCebweabg(rs.getString("CS116_ABG"));
            rtnVo.setCebwemg1(rs.getString("CS116_MG1"));
            rtnVo.setCebwebgt(rs.getString("CS116_BGT"));
            rtnVo.setCebwebmt(rs.getString("CS116_BMT"));
            rtnVo.setCebweplt(rs.getString("CS116_PLT"));
            rtnVo.setGno(rs.getString("CS116_GNO"));
            rtnVo.setCebwebdgbn(rs.getString("BDGBN"));
            
            rtnVo.setCevwevkgrp(rs.getString("VKGRP"));
            rtnVo.setCevwevkgrpco(rs.getString("VKGRP_CO"));
            
            rtnVo.setCevwezmusaocnt(rs.getString("ZMUSAOCNT"));
            rtnVo.setCevwezmusaocnt2(rs.getString("ZMUSAOCNT2"));
            rtnVo.setCevwezmusaucnt(rs.getString("ZMUSAUCNT"));
            rtnVo.setCevwezmusaucnt2(rs.getString("ZMUSAUCNT2"));
            rtnVo.setCevwezmusosum(rs.getString("ZMUSOSUM"));
            rtnVo.setCevwezmusogap(rs.getString("ZMUSOGAP"));
            
            
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
	 * �ۼ���: shhwang
	 * �ۼ���: 2006-03-22 ���� 1:57:45
	 * ��  ��: 
	 * ��  Ÿ:
	 */
	public Object select(ArrayList compPk, String subMethodFlag) throws Exception
	{
		return null;
	}
	
	public Object select2(Object condVO) throws Exception
	{
		return null;
	}
	/**
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.03.26
	 * ��  �� : ������ ���� ��⿡�� TBEBWEF1 ���̺� Delete �ϱ� ���� Interface
	 * ��  Ÿ : 
	 *          2006.03.27 - GY010103 ������ Delete�� �ƴ� Row �ʱ�ȭ(Update)�� ��
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
				if ("GY010103".equals(subMethodFlag)) // 2006.03.27 jkkoo
					deleteRowForGY010103(alist, conn);
				if ("GY010501".equals(subMethodFlag)) // 2006.09.04 ykchoi
					deleteRowForGY010501(alist, conn);					
				if ("GY010111".equals(subMethodFlag)) // 2006.09.05 jhlee
					deleteRowForGY010111(alist, conn);
				if ("NW010116".equals(subMethodFlag)) // 2006.11.30 jhlee
					deleteRowForNW010116(alist, conn);
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
	 * �ۼ���: shhwang
	 * �ۼ���: 2006-03-22 ���� 1:57:45
	 * ��  ��: 
	 * ��  Ÿ:
	 */
	public void update(Object vo) throws Exception
	{
	}
	/**
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.03.24
	 * ��  �� : ������ ���� ��⿡�� TBEBWEF1 ���̺� Update �ϱ� ���� Interface
	 * ��  Ÿ : 
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
				if ("GY010102".equals(subMethodFlag)) // 2006.03.23 jkkoo(����û���߼�ó��)
					updateRowForGY010102(alist, conn);
				if ("GY010103".equals(subMethodFlag)) // 2006.03.27 jkkoo(���ֳ�������)
					updateRowForGY010103(alist, conn);
				if ("GY010104".equals(subMethodFlag)) // 2006.03.28 jkkoo(����û������ó��)
					updateRowForGY010104(alist, conn);
				if ("GY010107".equals(subMethodFlag)) // 2006.03.30 jkkoo(���󺸼�����ó��)
					updateRowForGY010107(alist, conn);             
				if ("GY010107_03".equals(subMethodFlag)) // 2006.03.30 jkkoo(���󺸼��ݼ�ó��)
					updateRowForGY010107_03(alist, conn);
				if ("GY020105".equals(subMethodFlag)) // 2006.04.11 shhwang(������ ����)
					updateRowForGY020105(alist, conn);
            if ("GY030202_JA6".equals(subMethodFlag)) // 2006.07.06 mhcho ���к���� ���ν� 
               updateRowForGY030202_JA6(alist, conn);                      
			   if("GY090201".equals(subMethodFlag))  // 2006.08.14 jhlee
				   updateRowForGY090201(alist, conn);
			   if("GY090301".equals(subMethodFlag))  // 2006.08.16 jhlee
				   updateRowForGY090301(alist, conn);		   
				if ("GY010111".equals(subMethodFlag)) // 2006.09.05 jhlee(�߰�����)
					updateRowForGY010111(alist, conn);
				if ("GY010701".equals(subMethodFlag)) // 2006.09.14 YKCHOI(����û���߼�ó��)
					updateRowForGY010701(alist, conn);					
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
	 * �ۼ���: shhwang
	 * �ۼ���: 2006-03-22 ���� 1:57:45
	 * ��  ��: 
	 * ��  Ÿ:
	 */
	public int multiSaveForGrid(Object iobj, Object uobj, Object dobj) throws Exception
	{
		return 0;
	}
	/**
	* �ۼ��� : shhwang
	* �ۼ��� : 2006.03.22
	* ����    : �μ� ���ΰ��� ���νÿ�  insert �Ѵ� . 
	* ��Ÿ    : 
	*/
	public void insertRowForNW010114(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = null;
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				sqlBuff = new StringBuffer();
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				//					ȣ�⸶���� (TBEBWEF1)  �Է� 36�� 				  
				sqlBuff.append("INSERT  INTO  EVLADM.TBEBWEF1 (  \n");
				sqlBuff.append("			CEBWEPJT  , \n");
				sqlBuff.append("        CEBWEHGB  , \n");
				sqlBuff.append("           CEBWEHNO , \n");
				sqlBuff.append("           CEBWESEQ  , \n");
				sqlBuff.append("           CEBWEMNO , \n");
				sqlBuff.append("           CEBWETYP , \n");
				sqlBuff.append("           CEBWEHTY , \n");
				sqlBuff.append("           CEBWEARA , \n");
				sqlBuff.append("           CEBWEJUJ , \n");
				sqlBuff.append("           CEBWEBPM  , \n");
				sqlBuff.append("           CEBWEBSU  , \n");
				sqlBuff.append("           CEBWEFDT  , \n");
				sqlBuff.append("           CEBWECBS  , \n");
				sqlBuff.append("           CEBWEPST  , \n");
				sqlBuff.append("           CEBWEJUC  , \n");
				sqlBuff.append("           CEBWEGND  , \n");
				sqlBuff.append("           CEBWEGBN  , \n");
				sqlBuff.append("           CEBWEBGT  , \n");
				sqlBuff.append("           CEBWEMG1  , \n");
				sqlBuff.append("           CEBWEBMT  , \n");
				sqlBuff.append("           CEBWEBHD  , \n");
				sqlBuff.append("           CEBWEAGB  , \n");
				sqlBuff.append("           CEBWEMUT  , \n");
				sqlBuff.append("           CEBWEMBG  , \n");
				sqlBuff.append("           CEBWEBYT  , \n");
				sqlBuff.append("           CEBWEYCJ  , \n");
				sqlBuff.append("           CEBWEBJT  , \n");
				sqlBuff.append("           CEBWEBJJ  , \n");
				sqlBuff.append("           CEBWEHYN  , \n");
				sqlBuff.append("           CEBWEBST  , \n");
				sqlBuff.append("           CEBWEAPP  , \n");
				sqlBuff.append("           CEBWEBCC  , \n");
				sqlBuff.append("           CEBWEBCT  , \n");
				sqlBuff.append("           CEBWERMK  ) \n");
				sqlBuff.append("	SELECT '" + vo.getCebwepjt() + "',  \n"); //cebwepjt             
				sqlBuff.append("				 '" + vo.getCebwehgb() + "',  \n"); //cebwehgb     
				sqlBuff.append("				 '" + vo.getCebwehno() + "',  \n"); //  cebwehno    
				sqlBuff.append("		 SUBSTR(DIGITS(CAST(COALESCE(MAX(CEBWESEQ),'0') AS INTEGER)+1),9,2),  \n");
				sqlBuff.append("				 ' ',  \n"); //  cebwemno    
				sqlBuff.append("				 ' ',  \n"); //  cebwetyp    
				sqlBuff.append("				 ' ',  \n"); //  cebwehty   
				sqlBuff.append("				 '" + vo.getCebweara() + "',  \n"); //  cebweara    
				sqlBuff.append("				 '" + vo.getCebwejuj() + "',  \n"); //  cebwejuj    
				sqlBuff.append("				 '" + vo.getCebwebpm() + "',  \n"); //  cebwebpm     
				sqlBuff.append("				 '" + vo.getCebwebsu() + "',  \n"); //  cebwebsu     
				sqlBuff.append("				 '" + vo.getCebwefdt() + "',  \n"); // cebwefdt  
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); //    
				sqlBuff.append("				 '" + vo.getCebwegnd() + "',  \n"); // cebwegnd     
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); //    
				sqlBuff.append("				 0,  \n"); //  cebwemg1     
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); // 
				sqlBuff.append("				 0,  \n"); //  cebwemut     
				sqlBuff.append("				 0,  \n"); // cebwembg     
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); //   
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); //   
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); //      
				sqlBuff.append("				 ' ',  \n"); //     
				sqlBuff.append("				 ' '  \n"); //  cebwermk
				sqlBuff.append(" FROM EVLADM.TBEBWEF1  \n"); //
				sqlBuff.append(" WHERE CEBWEPJT = '" + vo.getCebwepjt() + "' AND CEBWEHGB= '" + vo.getCebwehgb() + "' AND CEBWEHNO = '" + vo.getCebwehno() + "'  \n"); //  AND CEBWEGND = '" + vo.getCebwegnd() + "'         
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
	* �ۼ��� : ykchoi
	* �ۼ��� : 2006.09.04
	* ����   : �߰�������� insert �Ѵ� . 
	* ��Ÿ   : 
	*/
	public void insertRowForGY010501(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = null;
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				sqlBuff = new StringBuffer();
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				//					ȣ�⸶���� (TBEBWEF1)  �Է� 36�� 				  
				sqlBuff.append("INSERT  INTO  EVLADM.TBEBWEF1 (  \n");
				sqlBuff.append("			CEBWEPJT ,   \n");
				sqlBuff.append("        CEBWEHGB ,   \n");
				sqlBuff.append("        CEBWEHNO ,   \n");
				sqlBuff.append("        CEBWESEQ ,   \n");
				sqlBuff.append("        CEBWEGND ,   \n");
				sqlBuff.append("        CEBWEGBN ,   \n");
				sqlBuff.append("        CEBWEARA ,   \n");
				sqlBuff.append("        CEBWEBPM ,   \n");
				sqlBuff.append("        CEBWEBSU ,   \n");				
				sqlBuff.append("        CEBWEFDT)    \n");
				sqlBuff.append(" VALUES (            \n");
				sqlBuff.append("        ?,?,?,?,'L', \n");
				sqlBuff.append("        'Y',?,?,?,?  \n");
				sqlBuff.append("        )            \n");

				
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
								
				pstmt.setString(idxparam++, vo.getCebwepjt()); //
				pstmt.setString(idxparam++, vo.getCebwehgb()); //
				pstmt.setString(idxparam++, vo.getCebwehno()); //
				pstmt.setString(idxparam++, vo.getCebweseq()); //
				pstmt.setString(idxparam++, vo.getCebweara()); //
				pstmt.setString(idxparam++, vo.getCebwebpm()); //
				pstmt.setString(idxparam++, vo.getCebwebsu()); //
				pstmt.setString(idxparam++, vo.getCebwefdt()); //
				
			//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.03.24
	 * ��  �� : ����û�� ȭ��(GY010102.jsp)���� �߼�ó���� TBEBWEF1�� ������¸� UPDATE
	 * ��  Ÿ : 
	 */
	public void updateRowForGY010102(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		try
		{
			sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
			sqlBuff.append("SET     CEBWEPST    = ?,     \n");
			sqlBuff.append("        CEBWEBYT    = ?,     \n");
			sqlBuff.append("        CEBWEYCJ    = ?      \n");
			sqlBuff.append("WHERE   CEBWEPJT    = ?      \n");
			sqlBuff.append("AND     CEBWEBSU    = ?      \n");
			sqlBuff.append("AND     CEBWEGND    = ?      \n");
			sqlBuff.append("AND     (CEBWEPST = 'A1' OR CEBWEPST = 'A2')	\n");
			
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebwepst());
				pstmt.setString(idxparam++, vo.getCebwebyt());
				pstmt.setString(idxparam++, vo.getCebweycj());
				pstmt.setString(idxparam++, vo.getCebwepjt());
				pstmt.setString(idxparam++, vo.getCebwebsu());
				pstmt.setString(idxparam++, vo.getCebwegnd());
			//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}
		catch (Exception e)
		{
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
	 * �ۼ��� : YKCHOI
	 * �ۼ��� : 2006.03.24
	 * ��  �� : ����û�� ȭ��(GY010701.jsp)���� �߼�ó���� TBEBWEF1�� ������¸� UPDATE
	 * ��  Ÿ : 
	 */
	public void updateRowForGY010701(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		try
		{
			sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
			sqlBuff.append("SET     CEBWEPST    = ?,     \n");
			sqlBuff.append("        CEBWEBYT    = ?,     \n");
			sqlBuff.append("        CEBWEYCJ    = ?      \n");
			sqlBuff.append("WHERE   CEBWEPJT    = ?      \n");
			sqlBuff.append("AND     CEBWEBSU    = ?      \n");
			sqlBuff.append("AND     CEBWEGND    = ?      \n");
			sqlBuff.append("AND     (CEBWEPST = 'A1' OR CEBWEPST = 'A2')	\n");
			
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebwepst());
				pstmt.setString(idxparam++, vo.getCebwebyt());
				pstmt.setString(idxparam++, vo.getCebweycj());
				pstmt.setString(idxparam++, vo.getCebwepjt());
				pstmt.setString(idxparam++, vo.getCebwebsu());
				pstmt.setString(idxparam++, vo.getCebwegnd());

			//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}
		catch (Exception e)
		{
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
	  * �ۼ��� : jkkoo
	  * �ۼ��� : 2006.03.27
	  * ��  �� : ����û���� ȭ��(GY010103.jsp)���� ����ó���� TBEBWEF1�� UPDATE
	  * ��  Ÿ : 
	  */
	public void updateRowForGY010103(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		TBEBWEF1Vo vo;
		String bweMno = "";
		String bwePjt = "";
		String bweJuj = "";
		String firstYN = "";
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				
				//===================================================
				//���� 060824. 
				// ������ : Ȳ���� 
				// ���� ���� : bwemno �� ' ' �� ���� 
				//===================================================
				
//				if ("A".equals(vo.getCebwegnd()) && !bwePjt.equals(vo.getCebwepjt()))
//				{
//					bweMno = "MK" + getMNOForGY010103(vo.getCebwepjt(), conn);
//					bwePjt = vo.getCebwepjt();
//				}
//				else if ("B".equals(vo.getCebwegnd()))
//					bweMno = "M" + EJBUtil.getMonth() + EJBUtil.getDate();

				bweMno = " ";
					
				bweJuj = getBwejuj(vo.getCebwepjt(),vo.getCebwehgb(),vo.getCebwehno(), conn)	;
				firstYN = getFirstYN(vo.getCebwepjt(),vo.getCebwehgb(),vo.getCebwehno(),vo.getCebwegnd(), conn);
					
				StringBuffer sqlBuff = new StringBuffer();
				
				//System.out.println(vo.getCebweseq());
				
				sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
				sqlBuff.append("SET                          \n");
				if (vo.getJobType() == 1) // usertype �� 1(���»� �̸� ���°��� A1���� Update �Ѵ�.
					sqlBuff.append("        CEBWEPST    = ?,     \n");
				sqlBuff.append("        CEBWEMNO    = ?,     \n");
				sqlBuff.append("        CEBWETYP    = ?,     \n");
				sqlBuff.append("        CEBWEHTY    = ?,     \n");
				//if ("01".equals(vo.getCebweseq())) // ����(cebweseq) = '01'�� ��쿡�� �Էµ� ���ְ�����Setting.
				if ("Y".equals(firstYN)) // ���ֱ��к� ù��° ������� ��
					sqlBuff.append("        CEBWECBS    = ?,     \n"); // ���ʹ��ְ����� - �����׸�
				sqlBuff.append("        CEBWEBGT    = ?,     \n");
				sqlBuff.append("        CEBWEMG1    = ?,     \n");
				sqlBuff.append("        CEBWEBMT    = ?,     \n");
				sqlBuff.append("        CEBWEBHD    = ?,     \n"); // ���������� - �����׸�
				sqlBuff.append("        CEBWEJUC    = ?,     \n");
				sqlBuff.append("        CEBWEAGB    = ?,     \n"); // �ó����� - �߰��׸�  			
				sqlBuff.append("        CEBWEMUT    = 0,     \n"); // ���󺸼����شܰ�-�߰��׸�
				sqlBuff.append("        CEBWEMBG    = 0,     \n"); // ��������-�߰��׸�
				sqlBuff.append("        CEBWEBYT    = ?,     \n");
				sqlBuff.append("        CEBWEYCJ    = ?,     \n");
				sqlBuff.append("        CEBWEJUJ    = ?,      \n"); // �������� - 6.26 �߰�
				sqlBuff.append("        CEBWEABG    = ?       \n"); // �������� - 6.26 �߰�
				sqlBuff.append("WHERE   CEBWEPJT    = ?      \n");
				sqlBuff.append("AND     CEBWEHGB    = ?      \n");
				sqlBuff.append("AND     CEBWEHNO    = ?      \n");
				sqlBuff.append("AND     CEBWESEQ    = ?      \n");
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				if (vo.getJobType() == 1) // usertype �� 1(���»� �̸� ���°��� A1���� Update �Ѵ�.
					pstmt.setString(idxparam++, vo.getCebwepst());
				pstmt.setString(idxparam++, bweMno);
				pstmt.setString(idxparam++, vo.getCebwetyp());
				pstmt.setString(idxparam++, vo.getCebwehty());
				//if ("01".equals(vo.getCebweseq())) // ����(cebweseq) = '01'�� ��쿡�� �Էµ� ���ְ�����Setting.
				if ("Y".equals(firstYN)) // ���ֱ��к� ù��° ������� ��
					pstmt.setString(idxparam++, vo.getCebwecbs()); // ���ʹ��ְ���
				pstmt.setString(idxparam++, vo.getCebwebgt()); // ���ְ�����
				pstmt.setString(idxparam++, vo.getCebwemg1()); // ���󰳿���
				pstmt.setString(idxparam++, vo.getCebwebmt()); // ���ָ�����
				pstmt.setString(idxparam++, vo.getCebwebmt()); // ����������
				pstmt.setString(idxparam++, vo.getCebwejuc()); // ���ð�����ü
				pstmt.setString(idxparam++, vo.getCebweagb()); // �ó�����
				pstmt.setString(idxparam++, vo.getCebwebyt());
				pstmt.setString(idxparam++, vo.getCebweycj());
				pstmt.setString(idxparam++, bweJuj);
				pstmt.setString(idxparam++, vo.getCebweabg());

				pstmt.setString(idxparam++, vo.getCebwepjt());
				pstmt.setString(idxparam++, vo.getCebwehgb());
				pstmt.setString(idxparam++, vo.getCebwehno());
				pstmt.setString(idxparam++, vo.getCebweseq());
				
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
	  * �ۼ��� : jhlee
	  * �ۼ��� : 2006.09.05
	  * ��  �� : �߰����ֻ� ȭ��(GY010111.jsp)���� ����ó���� TBEBWEF1�� UPDATE
	  * ��  Ÿ : 
	  */
	public void updateRowForGY010111(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		TBEBWEF1Vo vo;
		String bweMno = "";
		String bwePjt = "";
		String bweJuj = "";
		String firstYN = "";
		
		try
		{
			for(int i=0; i<list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);

				StringBuffer sqlBuff = new StringBuffer();

				sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
				sqlBuff.append("SET                          \n");
				sqlBuff.append("        CEBWEGND    = 'L',   \n");
				if(vo.getJobType() == 1) // usertype �� 1(���»� �̸� ���°��� A1���� Update �Ѵ�.
					sqlBuff.append("        CEBWEPST    = ?,     \n");
				sqlBuff.append("        CEBWETYP    = ?,     \n");
				sqlBuff.append("        CEBWEHTY    = ?,     \n");
				sqlBuff.append("        CEBWECBS    = ' ',   \n"); // ���ʹ��ְ�����
				sqlBuff.append("        CEBWEJUJ    = ?,     \n"); // �������� - 6.26 �߰�
				sqlBuff.append("        CEBWEBGT    = ?,     \n");
				sqlBuff.append("        CEBWEMG1    = ?,     \n");
				sqlBuff.append("        CEBWEBMT    = ?,     \n");
				sqlBuff.append("        CEBWEBHD    = ?,     \n"); // ���������� - �����׸�
				sqlBuff.append("        CEBWEJUC    = ' ',   \n");
				sqlBuff.append("        CEBWEAGB    = ?,     \n"); // �ó����� - �߰��׸�  			
				sqlBuff.append("        CEBWEABG    = ?,     \n"); // �������� - 6.26 �߰�
				sqlBuff.append("        CEBWEMUT    = 0,     \n"); // ���󺸼����شܰ�-�߰��׸�
				sqlBuff.append("        CEBWEMBG    = 0,     \n"); // ��������-�߰��׸�
				sqlBuff.append("        CEBWEBYT    = ?,     \n");
				sqlBuff.append("        CEBWEYCJ    = ?,     \n");
				sqlBuff.append("        CEBWEGBN    = 'Y'    \n");
				sqlBuff.append("WHERE   CEBWEPJT    = ?      \n");
				sqlBuff.append("AND     CEBWEHGB    = ?      \n");
				sqlBuff.append("AND     CEBWEHNO    = ?      \n");
				sqlBuff.append("AND     CEBWESEQ    = ?      \n");
				
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				
				if(vo.getJobType() == 1) // usertype �� 1(���»� �̸� ���°��� A1���� Update �Ѵ�.
					pstmt.setString(idxparam++, vo.getCebwepst());
				pstmt.setString(idxparam++, vo.getCebwetyp());
				pstmt.setString(idxparam++, vo.getCebwehty());
				pstmt.setString(idxparam++, vo.getCebwejuj());
				pstmt.setString(idxparam++, vo.getCebwebgt());
				pstmt.setString(idxparam++, vo.getCebwemg1());
				pstmt.setString(idxparam++, vo.getCebwebmt());
				pstmt.setString(idxparam++, vo.getCebwebhd());
				pstmt.setString(idxparam++, vo.getCebweagb());
				pstmt.setString(idxparam++, vo.getCebweabg());
				pstmt.setString(idxparam++, vo.getCebwebyt());
				pstmt.setString(idxparam++, vo.getCebweycj());
				pstmt.setString(idxparam++, vo.getCebwepjt());
				pstmt.setString(idxparam++, vo.getCebwehgb());
				pstmt.setString(idxparam++, vo.getCebwehno());
				pstmt.setString(idxparam++, vo.getCebweseq());
				
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
	  * �ۼ��� : jkkoo
	  * �ۼ��� : 2006.03.27
	  * ��  �� : ����û���� ȭ��(GY010103.jsp)���� ����ó���� TBEBWEF1�� Row �ʱ�ȭ(Update)
	  * ��  Ÿ : 
	  */
	public void deleteRowForGY010103(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo;
		try
		{
			sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
			sqlBuff.append("SET                          \n");
			sqlBuff.append("        CEBWEPST    = ' ',   \n");
			sqlBuff.append("        CEBWEMNO    = ' ',   \n");
			sqlBuff.append("        CEBWEBHD    = ' ',   \n");
			sqlBuff.append("        CEBWEBGT    = ' ',   \n");
			sqlBuff.append("        CEBWEBMT    = ' ',   \n");
			sqlBuff.append("        CEBWEJUC    = ' ',   \n");
			sqlBuff.append("        CEBWEBYT    = ' ',   \n");
			sqlBuff.append("        CEBWEYCJ    = ' '    \n");
			sqlBuff.append("WHERE   CEBWEPJT    = ?      \n");
			sqlBuff.append("AND     CEBWEHGB    = ?      \n");
			sqlBuff.append("AND     CEBWEHNO    = ?      \n");
			sqlBuff.append("AND     CEBWESEQ    = ?      \n");
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebwepjt());
				pstmt.setString(idxparam++, vo.getCebwehgb());
				pstmt.setString(idxparam++, vo.getCebwehno());
				pstmt.setString(idxparam++, vo.getCebweseq());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
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
	  * �ۼ��� : jhlee
	  * �ۼ��� : 2006.09.05
	  * ��  �� : �߰����ֻ� ȭ��(GY010111.jsp)���� ����ó���� TBEBWEF1�� Row �ʱ�ȭ(Update)
	  * ��  Ÿ : 
	  */
	public void deleteRowForGY010111(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo;
		
		try
		{
			sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
			sqlBuff.append("SET                          \n");
			sqlBuff.append("        CEBWEPST    = ' ',   \n");
			sqlBuff.append("        CEBWEHTY    = ' ',   \n");
			sqlBuff.append("        CEBWECBS    = ' ',   \n");
			sqlBuff.append("        CEBWEMG1    = 0  ,   \n");
			sqlBuff.append("        CEBWEBGT    = ' ',   \n");
			sqlBuff.append("        CEBWEBMT    = ' ',   \n");
			sqlBuff.append("        CEBWEBHD    = ' ',   \n");
			sqlBuff.append("        CEBWEJUC    = ' ',   \n");
//			sqlBuff.append("        CEBWEMG2    = 0  ,   \n");
			sqlBuff.append("        CEBWEBYT    = ' ',   \n");
			sqlBuff.append("        CEBWEYCJ    = ' '    \n");
			sqlBuff.append("WHERE   CEBWEPJT    = ?      \n");
			sqlBuff.append("AND     CEBWEHGB    = ?      \n");
			sqlBuff.append("AND     CEBWEHNO    = ?      \n");
			sqlBuff.append("AND     CEBWESEQ    = ?      \n");
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			
			for(int i=0; i<list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				
				pstmt.setString(idxparam++, vo.getCebwepjt());
				pstmt.setString(idxparam++, vo.getCebwehgb());
				pstmt.setString(idxparam++, vo.getCebwehno());
				pstmt.setString(idxparam++, vo.getCebweseq());
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
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
	  * �ۼ��� : jhlee
	  * �ۼ��� : 2006.11.30
	  * ��  �� : ������ֽ������
	  * ��  Ÿ : 
	  */
	public void deleteRowForNW010116(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo;
		
		try
		{
			sqlBuff.append(" DELETE                   \n");
			sqlBuff.append(" FROM   EVLADM.TBEBWEF1   \n");
			sqlBuff.append(" WHERE  CEBWEPJT = ?      \n");  
			sqlBuff.append("   AND  CEBWEHGB = ?      \n");
			sqlBuff.append("   AND  CEBWEHNO = ?      \n");
			sqlBuff.append("   AND  CEBWEFDT = ?      \n");
			sqlBuff.append("   AND  CEBWEGND IN ('A','B') \n");
			
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			
			for(int i=0; i<list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				
				pstmt.setString(idxparam++, vo.getCebwepjt());
				pstmt.setString(idxparam++, vo.getCebwehgb());
				pstmt.setString(idxparam++, vo.getCebwehno());
				pstmt.setString(idxparam++, vo.getCebwefdt());
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
		* �ۼ��� : ykchoi
		* �ۼ��� : 2006.09.04
		* ����   :  
		* ��Ÿ   : 
		*/
	public void deleteRowForGY010501(ArrayList list, Connection conn) throws Exception 
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		ResultSet rs = null;
		try
		{ 
			sqlBuff.append(" DELETE                   \n");
			sqlBuff.append(" FROM   EVLADM.TBEBWEF1   \n");
			sqlBuff.append(" WHERE  CEBWEPJT = ?      \n");  
			sqlBuff.append("   AND  CEBWEHGB = ?      \n");
			sqlBuff.append("   AND  CEBWEHNO = ?      \n");
			sqlBuff.append("   AND  CEBWEGND = 'L'    \n");
		
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
		
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebwepjt());
				pstmt.setString(idxparam++, vo.getCebwehgb());
				pstmt.setString(idxparam++, vo.getCebwehno());
				
				pstmt.addBatch();					
			}
			pstmt.executeBatch();			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try{if (pstmt != null) pstmt.close();}catch(Exception e){}
		}
	} 		
	/**
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.03.28
	 * ��  �� : ����û�� ȭ��(GY010104.jsp)���� ����ó���� TBEBWEF1�� ������¸� UPDATE
	 * ��  Ÿ : 
	 */
	public void updateRowForGY010104(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		try
		{
			sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
			sqlBuff.append("SET     CEBWEPST    = ?,     \n");
			sqlBuff.append("        CEBWEMUT    = ?,     \n");
			sqlBuff.append("        CEBWEBJT    = ?,     \n");
			sqlBuff.append("        CEBWEBJJ    = ?      \n");
			sqlBuff.append("WHERE   CEBWEPJT    = ?      \n");
			sqlBuff.append("AND     CEBWEBSU    = ?      \n");
			sqlBuff.append("AND     CEBWEGND    = ?      \n");
			sqlBuff.append("AND     CEBWEPST    = ?      \n");
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebwepst());
				pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebwemut()));
				pstmt.setString(idxparam++, vo.getCebwebjt());
				pstmt.setString(idxparam++, vo.getCebwebjj());
				pstmt.setString(idxparam++, vo.getCebwepjt());
				pstmt.setString(idxparam++, vo.getCebwebsu());
				pstmt.setString(idxparam++, vo.getCebwegnd());
				pstmt.setString(idxparam++, vo.getPrePst());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
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
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.03.30
	 * ��  �� : ���󺸼�����ȭ��(GY010107.jsp)���� ����ó���� TBEBWEF1�� ������¸� UPDATE
	 * ��  Ÿ : 
	 */
	public void updateRowForGY010107(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
      ComMethodVo comVo;
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				int rsCnt = 0;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				sqlBuff = new StringBuffer();
				sqlBuff.append(
					"SELECT COUNT(*) as CNT FROM EVLADM.TBEBWEF1 WHERE CEBWEPJT='"
						+ vo.getCebwepjt()
						+ "' AND CEBWEHGB='"
						+ vo.getCebwehgb()
						+ "' AND CEBWEHNO='"
						+ vo.getCebwehno()
						+ "' AND CEBWEBGT='"
						+ vo.getCebwebgt()
						+ "'");
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					rsCnt = rs.getInt("CNT");
				}
				else
				{
					rsCnt = 0;
				}
				if (rsCnt > 0)
					updateRowForGY010107_04(vo, conn);
				else
					insertRowForGY010107_05(vo, conn); //������ 20060907


            comVo = new ComMethodVo();
                  
            if("A".equals(vo.getCebwegnd()))
               comVo.setDataId("11");
            else if("B".equals(vo.getCebwegnd()))
               comVo.setDataId("12");
		    else if("L".equals(vo.getCebwegnd())) //2006.09.08 �߰����� by ohb
			   comVo.setDataId("13");
               
            else
               throw new BizException("Data Id�� ���� ���� �ʾҽ��ϴ�");
               
            comVo.setJobGubun("I");
            comVo.setUpn(vo.getCebwepjt());
            comVo.setCst("Z99");
            comVo.setPjt(vo.getCebwepjt());
            comVo.setHgb(vo.getCebwehgb());
            comVo.setHno(vo.getCebwehno());
            comVo.setSeq(vo.getCebweseq());
            comVo.setFirstUserId(vo.getCebweapp());
            
//            ComMethodDao.SetBXRBXL(comVo);

			}
			
			updateRowForGY010107_01(list, conn); // ������簡 �����ִ��� üũ �ϰ� �ִٸ� ���� ��Ű�� �޼ҵ�.
			updateRowForGY010107_02(list, conn); // ������簡 �����ִ��� üũ �ϰ� �ִٸ� ���� ��Ű�� �޼ҵ�.
		}
      catch (BizException e)
      {
         throw e;
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
				rs.close();
				pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	/**
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.03.30
	 * ��  �� : ���󺸼�����ȭ��(GY010107.jsp)���� ����ó���� TBEBWEF1�� ������¸� UPDATE
	 * ��  Ÿ : �����Ϲ� ���ֽ����� ������簡 �Ϸ���� �ʾҴٸ� ������ ������縦 ��
	 */
	public void updateRowForGY010107_01(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo;
		int mdy = 0;
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
            
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				
				if("B".equals(vo.getCebwegnd()))
				{
					sqlBuff = new StringBuffer();
					
					sqlBuff.append("SELECT                 \n");
					sqlBuff.append("     CEBWEPJT,         \n");
					sqlBuff.append("     CEBWEHGB,         \n");
					sqlBuff.append("     CEBWEHNO,         \n");
					sqlBuff.append("     CEBWEMNO,         \n");
					sqlBuff.append("     CEBWEBGT          \n");
					sqlBuff.append("FROM                   \n");
					sqlBuff.append("     EVLADM.TBEBWEF1   \n");
					sqlBuff.append("WHERE 1=1              \n");
					sqlBuff.append("AND  CEBWEPJT = ?      \n");
					sqlBuff.append("AND  CEBWEHGB = ?      \n");
					sqlBuff.append("AND  CEBWEHNO = ?      \n");
					
					sqlBuff.append("AND  CEBWEGND = 'A'   \n");
					sqlBuff.append("AND  CEBWEBMT > ?      \n");
					pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
					pstmt.setString(1, vo.getCebwepjt());
					pstmt.setString(2, vo.getCebwehgb());
					pstmt.setString(3, vo.getCebwehno());
					pstmt.setString(4, vo.getCebwebgt());
					rs = pstmt.executeQuery();
					
					while (rs.next())
					{
						sqlBuff = new StringBuffer();
						sqlBuff.append("UPDATE    EVLADM.TBEBWEF1   \n");
						//sqlBuff.append("SET       CEBWEMDY = ? ,    \n");
	               sqlBuff.append("SET                         \n");
						sqlBuff.append("          CEBWEBHD = ?      \n");
						sqlBuff.append("WHERE     CEBWEPJT = ?      \n");
						sqlBuff.append("AND       CEBWEHGB = ?      \n");
						sqlBuff.append("AND       CEBWEHNO = ?      \n");
						sqlBuff.append("AND       CEBWEMNO = ?      \n");
						pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
						pstmt.clearParameters();
						mdy = Integer.parseInt(CommonUtil.diffDate(vo.getCebwebgt(), rs.getString("CEBWEBGT")));
	               int idxparam = 1;
						//pstmt.setInt(idxparam++, mdy);
						pstmt.setString(idxparam++, CommonUtil.replace(CommonUtil.addDatePrevNext(vo.getCebwebgt(), -1), "-", ""));
						pstmt.setString(idxparam++, rs.getString("CEBWEPJT"));
						pstmt.setString(idxparam++, rs.getString("CEBWEHGB"));
						pstmt.setString(idxparam++, rs.getString("CEBWEHNO"));
						pstmt.setString(idxparam++, rs.getString("CEBWEMNO"));
					//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
						pstmt.executeUpdate();
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BizException("1");
		}
		finally
		{
			try
			{
				pstmt.close();
				rs.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	/**
	* �ۼ��� : jkkoo
	* �ۼ��� : 2006.03.30
	* ��  �� : ���󺸼�����ȭ��(GY010107.jsp)���� ����ó���� TBEBWEF1�� ������¸� UPDATE
	* ��  Ÿ : �����Ϲ� ���ֽ����� ������簡 �Ϸ���� �ʾҴٸ� ������ ������縦 ����
	*/
	public void updateRowForGY010107_02(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo;
		int mdy = 0;
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				
				if("B".equals(vo.getCebwegnd()))
				{
					sqlBuff = new StringBuffer();
					sqlBuff.append("SELECT                                                   \n");
					sqlBuff.append("   CEBWMUPN,                                             \n");
					sqlBuff.append("   CEBWMCST,                                             \n");
					sqlBuff.append("   CEBWMPJT,                                             \n");
					sqlBuff.append("   CEBWMHGB,                                             \n");
					sqlBuff.append("   CEBWMHNO,                                             \n");
					sqlBuff.append("   CEBWMMNO                                              \n");
					sqlBuff.append("FROM                                                     \n");
					sqlBuff.append("   (                                                     \n");
					sqlBuff.append("   SELECT                                                \n");
					sqlBuff.append("      CEBSKUPN,                                          \n");
					sqlBuff.append("      CEBSKCST,                                          \n");
					sqlBuff.append("      CEBSKPJT,                                          \n");
					sqlBuff.append("      CEBSKHGB,                                          \n");
					sqlBuff.append("      CEBSKHNO                                           \n");
					sqlBuff.append("   FROM                                                  \n");
					sqlBuff.append("      EVLADM.TBEBIKF1                                    \n");
					sqlBuff.append("   WHERE CEBSKPJT   = ?                                  \n");
					sqlBuff.append("   AND   CEBSKHGB   = ?                                  \n");
					sqlBuff.append("   AND   CEBSKHNO   = ?                                  \n");
					sqlBuff.append("   AND (RTRIM(CEBSKSTP) = '' OR CEBSKSTP > ? )           \n");
					sqlBuff.append("   ) BSK,                                                \n");
					sqlBuff.append("   EVLADM.TBEBWMF1                                       \n");
					sqlBuff.append("WHERE    CEBSKUPN = CEBWMUPN                             \n");
					sqlBuff.append("AND      CEBSKCST = CEBWMCST                             \n");
					sqlBuff.append("AND      CEBSKPJT = CEBWMPJT                             \n");
					sqlBuff.append("AND      CEBSKHGB = CEBWMHGB                             \n");
					sqlBuff.append("AND      CEBSKHNO = CEBWMHNO                             \n");
					sqlBuff.append("AND      CEBWMGND = 'C'                     \n");
					sqlBuff.append("AND      CEBWMUHJ > ?                                    \n");
					pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
					pstmt.setString(1, vo.getCebwepjt());
					pstmt.setString(2, vo.getCebwehgb());
					pstmt.setString(3, vo.getCebwehno());
					pstmt.setString(4, CommonUtil.getToday2());
					pstmt.setString(5, CommonUtil.getToday2());
					rs = pstmt.executeQuery();
					while (rs.next())
					{
						sqlBuff = new StringBuffer();
					//	logger.debug("������� ���� [" + i + "] : " + vo.getCebwepjt());
						sqlBuff.append("UPDATE    EVLADM.TBEBWMF1   \n");
						sqlBuff.append("SET                         \n");
						sqlBuff.append("          CEBWMUHJ = ?      \n");
						sqlBuff.append("WHERE     CEBWMUPN = ?      \n");
						sqlBuff.append("AND       CEBWMCST = ?      \n");
						sqlBuff.append("AND       CEBWMPJT = ?      \n");
						sqlBuff.append("AND       CEBWMHGB = ?      \n");
						sqlBuff.append("AND       CEBWMHNO = ?      \n");
						sqlBuff.append("AND       CEBWMMNO = ?      \n");
						pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
						pstmt.clearParameters();
						pstmt.setString(1, CommonUtil.replace(CommonUtil.addDatePrevNext(vo.getCebwebgt(), -1), "-", ""));
						pstmt.setString(2, rs.getString("CEBWMUPN"));
						pstmt.setString(3, rs.getString("CEBWMCST"));
						pstmt.setString(4, rs.getString("CEBWMPJT"));
						pstmt.setString(5, rs.getString("CEBWMHGB"));
						pstmt.setString(6, rs.getString("CEBWMHNO"));
						pstmt.setString(6, rs.getString("CEBWMMNO"));
					//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
						pstmt.executeUpdate();
					}
				}
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
				rs.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	/**
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.03.28
	 * ��  �� : ���󺸼����� ȭ��(GY010107.jsp)���� �ݼ�ó���� TBEBWEF1�� ������¸� UPDATE
	 * ��  Ÿ : 
	 */
	public void updateRowForGY010107_03(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		try
		{
			sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
			sqlBuff.append("SET     CEBWEPST    = ?,     \n");
			sqlBuff.append("        CEBWEMBG    = ?,     \n");
			sqlBuff.append("        CEBWEBST    = ?,     \n");
			sqlBuff.append("        CEBWEAPP    = ?      \n");
			sqlBuff.append("WHERE   CEBWEPJT    = ?      \n");
			sqlBuff.append("AND     CEBWEHGB    = ?      \n");
			sqlBuff.append("AND     CEBWEHNO    = ?      \n");
			sqlBuff.append("AND     CEBWESEQ    = ?      \n");
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebwepst());
				pstmt.setBigDecimal(idxparam++, new BigDecimal(0));
				pstmt.setString(idxparam++, vo.getCebwebst());
				pstmt.setString(idxparam++, vo.getCebweapp());
				pstmt.setString(idxparam++, vo.getCebwepjt());
				pstmt.setString(idxparam++, vo.getCebwehgb());
				pstmt.setString(idxparam++, vo.getCebwehno());
				pstmt.setString(idxparam++, vo.getCebweseq());
			//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
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
		 * �ۼ��� : shhwang
		 * �ۼ��� : 2006.05.25
		 * ��  �� : ������ν� ������� �ܿ��Ⱓ����� ����ó�� 
		 * ��  Ÿ : 
		 */
		public void updateRowForGY020105_01(ArrayList list, Connection conn) throws Exception
		{
			LoggablePreparedStatement pstmt = null;
			StringBuffer sqlBuff = new StringBuffer();
			TBEBWEF1Vo vo = new TBEBWEF1Vo();
			try
			{
				sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
				sqlBuff.append("SET     CEBWEBHD    = ?    \n");				
				sqlBuff.append("WHERE   CEBWEPJT    = ?      \n");
				sqlBuff.append("AND     CEBWEHGB    = ?      \n");
				sqlBuff.append("AND     CEBWEHNO    = ?      \n");
				sqlBuff.append("AND CEBWEGND = 'A'  \n");
				sqlBuff.append("AND     CEBWESEQ    = (      \n");
				sqlBuff.append("SELECT MAX(CEBWESEQ)   \n");
				sqlBuff.append("						  FROM EVLADM.TBEBWEF1   \n");
				sqlBuff.append("						  WHERE 1=1   \n");
				sqlBuff.append("						  AND CEBWEPJT= ? \n");
				sqlBuff.append("						  AND CEBWEHGB= ? \n");
				sqlBuff.append("						  AND CEBWEHNO = ?)   \n");
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				
				for (int i = 0; i < list.size(); i++)
				{
					int idxparam = 1;
					vo = new TBEBWEF1Vo();
					vo = (TBEBWEF1Vo) list.get(i);
					pstmt.setString(idxparam++, vo.getCebwebhd());
					pstmt.setString(idxparam++, vo.getCebwepjt());
					pstmt.setString(idxparam++, vo.getCebwehgb());
					pstmt.setString(idxparam++, vo.getCebwehno());
					pstmt.setString(idxparam++, vo.getCebwepjt());
					pstmt.setString(idxparam++, vo.getCebwehgb());
					pstmt.setString(idxparam++, vo.getCebwehno());
					
				//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
		 * �ۼ��� : shhwang
		 * �ۼ��� : 2006.05.25
		 * ��  �� : ��������ν� ���� �ܿ��Ⱓ����� �������� �������� ������Ʈ�� �������� �μ�Ʈ  ó�� 
		 * ��  Ÿ : 
		 */
		public void insertRowForGY020105_all(ArrayList list, Connection conn) throws Exception
		{
			
			//������Ʈ 
		   //updateRowForGY020105_01(list, conn);//updateRowForGY020105 �� ���ԵǼ� ���� 
			//			�μ�Ʈ 
			insertRowForGY020105(list, conn);
			
		}	
	/**
		 * �ۼ��� : shhwang
		 * �ۼ��� : 2006.05.25
		 * ��  �� : ��������ν� ���� �ܿ��Ⱓ����� �������� �μ�Ʈ 
		 * ��  Ÿ : 
		 */
		public void insertRowForGY020105(ArrayList list, Connection conn) throws Exception
		{
			LoggablePreparedStatement pstmt = null;
			TBEBWEF1Vo vo = new TBEBWEF1Vo();
			StringBuffer sqlBuff= null;
			
			try
			{	
				
				for (int i = 0; i < list.size(); i++)
				{
					int idxparam = 1;
					vo = new TBEBWEF1Vo();
					vo = (TBEBWEF1Vo) list.get(i);	
					sqlBuff = new StringBuffer();
					sqlBuff.append("INSERT INTO EVLADM.TBEBWEF1 (CEBWEPJT,        \n");          
					sqlBuff.append("		  CEBWEHGB,                  \n");
					sqlBuff.append("		  CEBWEHNO,     \n");
					sqlBuff.append("		  CEBWESEQ,                  \n");
					sqlBuff.append("		  CEBWEMNO,       \n");
					sqlBuff.append("		  CEBWETYP,      \n");
					sqlBuff.append("		  CEBWEHTY,     \n");
					sqlBuff.append("		  CEBWEARA,     \n");
					sqlBuff.append("		  CEBWEJUJ,     \n");
					sqlBuff.append("		  CEBWEBPM,     \n");
					sqlBuff.append("		  CEBWEBSU,     \n");
					sqlBuff.append("		  CEBWEFDT,     \n");
					sqlBuff.append("		  CEBWECBS,     \n");
					sqlBuff.append("		  CEBWEPST,     \n");
					sqlBuff.append("		  CEBWEJUC,     \n");
					sqlBuff.append("		  CEBWEGND,     \n");
					sqlBuff.append("		  CEBWEGBN,     \n");
					sqlBuff.append("		  CEBWEMG1,     \n");
					sqlBuff.append("		  CEBWEBGT,     \n");
					sqlBuff.append("		  CEBWEBMT,     \n");
					sqlBuff.append("		  CEBWEBHD,     \n");
					sqlBuff.append("		  CEBWEABG,     \n");
					sqlBuff.append("		  CEBWEAGB,     \n");
					sqlBuff.append("		  CEBWEMUT,     \n");
					sqlBuff.append("		  CEBWEMBG,    \n");
					sqlBuff.append("		  CEBWEBYT,     \n");
					sqlBuff.append("		  CEBWEYCJ,     \n");
					sqlBuff.append("		  CEBWEBJT,     \n");
					sqlBuff.append("		  CEBWEBJJ,     \n");
					sqlBuff.append("		  CEBWEHYN,     \n");
					sqlBuff.append("		  CEBWEBST,     \n");
					sqlBuff.append("		  CEBWEAPP,     \n");
					sqlBuff.append("		  CEBWEBCC,     \n");
					sqlBuff.append("		  CEBWEBCT,     \n");
					sqlBuff.append("		  CEBWERMK)    \n");
					sqlBuff.append("SELECT              CEBWEPJT,    \n");              
					sqlBuff.append("		  CEBWEHGB,                  \n");
					sqlBuff.append("		  CEBWEHNO,     \n");
					sqlBuff.append("		  '"+ vo.getCebweseq()+"',                  \n");
					sqlBuff.append("		  CEBWEMNO,       \n");
					sqlBuff.append("		  CEBWETYP,      \n");
					sqlBuff.append("		  CEBWEHTY,     \n");
					sqlBuff.append("		  CEBWEARA,     \n");
					sqlBuff.append("		  CEBWEJUJ,     \n");
					sqlBuff.append("		  CEBWEBPM,     \n");
					sqlBuff.append("		  CEBWEBSU,     \n");
					sqlBuff.append("		  CEBWEFDT,     \n");
					sqlBuff.append("		  CEBWECBS,     \n");
					sqlBuff.append("		  CEBWEPST,     \n");
					sqlBuff.append("		  CEBWEJUC,     \n");
					sqlBuff.append("		  CEBWEGND,     \n");
					sqlBuff.append("		  CEBWEGBN,     \n");
					sqlBuff.append("		  "+ vo.getCebwemg1()+" ,     \n");
					sqlBuff.append("		   '"+ vo.getCebwebgt()+"' ,   \n");
					sqlBuff.append("		   '"+ vo.getCebwebmt()+"' ,     \n");
					sqlBuff.append("		  '"+ vo.getCebwebhd1()+"',     \n");//�������� bhd1 : �ܿ��Ⱓ �μ�Ʈ��
					sqlBuff.append("		  CEBWEABG,     \n");
					sqlBuff.append("		  CEBWEAGB,     \n");
					sqlBuff.append("		  CEBWEMUT,     \n");
					sqlBuff.append("		  CEBWEMBG,    \n");
					sqlBuff.append("		  CEBWEBYT,     \n");
					sqlBuff.append("		  CEBWEYCJ,     \n");
					sqlBuff.append("		  CEBWEBJT,     \n");
					sqlBuff.append("		  CEBWEBJJ,     \n");
					sqlBuff.append("		  CEBWEHYN,     \n");
					sqlBuff.append("		  CEBWEBST,     \n");
					sqlBuff.append("		  CEBWEAPP,     \n");
					sqlBuff.append("		  CEBWEBCC,     \n");
					sqlBuff.append("		  CEBWEBCT,     \n");
					sqlBuff.append("		  CEBWERMK     \n");
					sqlBuff.append("		  FROM EVLADM.TBEBWEF1    \n"); 
					sqlBuff.append("		  WHERE 1=1    \n");
					sqlBuff.append("		  AND CEBWEPJT= ?   \n");
					sqlBuff.append("		  AND CEBWEHGB= ?   \n");
					sqlBuff.append("		  AND CEBWEHNO = ?    \n");
					sqlBuff.append("		  AND CEBWEGND = 'A'    \n");
					sqlBuff.append("		  AND CEBWESEQ = (     \n");
					sqlBuff.append("		  SELECT MAX(CEBWESEQ)    \n");
					sqlBuff.append("		  FROM EVLADM.TBEBWEF1    \n");
					sqlBuff.append("		  WHERE 1=1    \n");
					sqlBuff.append("		  AND CEBWEPJT= ?   \n");
					sqlBuff.append("		  AND CEBWEHGB= ?   \n");
					sqlBuff.append("		  AND CEBWEHNO =?)            \n");    

					pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
								
					pstmt.setString(idxparam++, vo.getCebwepjt()); //1.������Ʈ��ȣ
					pstmt.setString(idxparam++, vo.getCebwehgb()); //2.ȣ�ⱸ��
					pstmt.setString(idxparam++, vo.getCebwehno()); //3.ȣ���ȣ
					pstmt.setString(idxparam++, vo.getCebwepjt()); //1.������Ʈ��ȣ
					pstmt.setString(idxparam++, vo.getCebwehgb()); //2.ȣ�ⱸ��
					pstmt.setString(idxparam++, vo.getCebwehno()); //3.ȣ���ȣ
					
				//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
					pstmt.executeUpdate();
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
					pstmt.close();
				}
				catch (Exception e)
				{
				}
			}
		}
		
	/**
	 * �ۼ��� : shhwang
	 * �ۼ��� : 2006.04.11
	 * ��  �� : ��������� ���� ����ó���� : �����������ڷ� ����� ����ó��  TBEBWEF1 UPDATE
	 * ��  Ÿ : 
	 */
	public void updateRowForGY020105(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = null;
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		String CEBWEBMT = "";
		String CEBWEBMTB = "";
		int daycnt = 0;
		int daycnt2 = 0;
		int daycnt3 = 0;
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBWEF1Vo();
				vo = (TBEBWEF1Vo) list.get(i);			
				
				sqlBuff = new StringBuffer();
				sqlBuff.append("SELECT   \n");
				sqlBuff.append("            CEBWEBMT \n");
				sqlBuff.append("FROM  EVLADM.TBEBWEF1           \n");
				sqlBuff.append("WHERE CEBWEPJT   =	 '" + vo.getCebwepjt() + "'       \n");
				sqlBuff.append("AND  CEBWEHGB   =	  '" + vo.getCebwehgb() + "'      \n");
				sqlBuff.append("AND  CEBWEHNO   =	  '" + vo.getCebwehno() + "' 		       \n");
				sqlBuff.append("AND  CEBWESEQ   =	(SELECT MAX(CEBWESEQ) FROM EVLADM.TBEBWEF1      \n");
				sqlBuff.append("                                   WHERE  CEBWEPJT   =	'"+vo.getCebwepjt()+"' AND CEBWEHGB= '"+vo.getCebwehgb()+"' AND CEBWEHNO = '"+vo.getCebwehno()+"' AND  CEBWEGND = 'A' )   \n");   

				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());												
				rs = pstmt.executeQuery();
				while(rs.next()){							
						CEBWEBMT=  CommonUtil.removeSpace(rs.getString("CEBWEBMT"));// ������� ���ָ����� 
				}
				
				sqlBuff = new StringBuffer();
				sqlBuff.append("SELECT   \n");
				sqlBuff.append("            CEBWEBMT AS CEBWEBMTB\n");
				sqlBuff.append("FROM  EVLADM.TBEBWEF1           \n");
				sqlBuff.append("WHERE CEBWEPJT   =	 '" + vo.getCebwepjt() + "'       \n");
				sqlBuff.append("AND  CEBWEHGB   =	  '" + vo.getCebwehgb() + "'      \n");
				sqlBuff.append("AND  CEBWEHNO   =	  '" + vo.getCebwehno() + "' 		       \n");
				sqlBuff.append("AND  CEBWESEQ   =	(SELECT MAX(CEBWESEQ) FROM EVLADM.TBEBWEF1      \n");
				sqlBuff.append("                                   WHERE  CEBWEPJT   =	'"+vo.getCebwepjt()+"' AND CEBWEHGB= '"+vo.getCebwehgb()+"' AND CEBWEHNO = '"+vo.getCebwehno()+"' AND  CEBWEGND = 'B' )   \n");   

				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());												
				rs = pstmt.executeQuery();
				while(rs.next()){							
					CEBWEBMTB = CommonUtil.removeSpace(rs.getString("CEBWEBMTB"));//�����Ϲ� ���ָ����� 
					
				}					
				
				if ("C".equals(vo.getCebwegnd()))
				{ //���������� C
					if (CEBWEBMT != null && CEBWEBMT.length() == 8)
					{
						//������� ����, ������� �ڷᰡ ����ÿ��� ����ó�� :2	
						//���ָ�����(cebwebmt) > ����(Body)�� ��ళ����
						daycnt = Integer.parseInt(CommonUtil.diffDate(CEBWEBMT, vo.getGubun())); //vo.getGubun() �� ��ళ���� 	
					}
				}
				else if ("D".equals(vo.getCebwegnd()))
				{ //���������� D
					//�����Ϲ��϶� ��౸���� �ű��ϰ�츸 �������� ó���� �����Ѵ�. 
					if("1".equals(vo.getGkd()) || "5".equals(vo.getGkd()) ){
						 //2���� ���� ����ó���� ���� ���� ���� 
						 //������� ����, ������� �ڷᰡ ����ÿ��� ����ó��		
						 if (CEBWEBMT != null && CEBWEBMT.length() == 8)
						 {
							 daycnt2 = Integer.parseInt(CommonUtil.diffDate(CEBWEBMT, vo.getGubun()));
						 }
						 //9.2.3.5.2 �����Ϲ� ����, ������� �ڷᰡ ����ÿ��� ����ó��
						 if (CEBWEBMTB != null && CEBWEBMTB.length() == 8)
						 {
							 daycnt3 = Integer.parseInt(CommonUtil.diffDate(CEBWEBMTB, vo.getGubun()));
						 }
					}
					
				}
				if (daycnt < 0 || daycnt2 < 0 || daycnt3 < 0)
				{ //3���� ����ó���� �ϳ��� �ش�ɰ�� 
					sqlBuff = new StringBuffer();
					sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
					sqlBuff.append("SET     CEBWEBHD    = ?    \n"); //1.���������� 
					sqlBuff.append("WHERE   CEBWEPJT    = ?      \n"); //12.������Ʈ��ȣ
					sqlBuff.append("AND     CEBWEHGB    = ?      \n"); //13.ȣ�ⱸ��
					sqlBuff.append("AND     CEBWEHNO    = ?      \n"); //14.ȣ���ȣ
					sqlBuff.append("AND     CEBWESEQ    = ?      \n"); // 
					pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
					pstmt.setString(idxparam++, vo.getCebwebhd()); //					
					pstmt.setString(idxparam++, vo.getCebwepjt()); //12.������Ʈ��ȣ
					pstmt.setString(idxparam++, vo.getCebwehgb()); //13.ȣ�ⱸ��
					pstmt.setString(idxparam++, vo.getCebwehno()); //14.ȣ���ȣ
					pstmt.setString(idxparam++, vo.getCebweseq()); //
				//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
					pstmt.executeUpdate();
				}
			} //for�� �� 
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
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.03.28
	 * ��  �� : ���󺸼����� ȭ��(GY010107.jsp)���� �ݼ�ó���� TBEBWEF1�� ������¸� UPDATE
	 * ��  Ÿ : 
	 */
	public String getMNOForGY010103(String pjt, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = new StringBuffer();
		String mno = "";
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		try
		{
			sqlBuff.append("SELECT                             \n");
			sqlBuff.append("     COALESCE(SUBSTR(DIGITS((CAST(MAX(SUBSTR(CEBWEMNO,3,3)) as integer)+1)),8,3),'001') mno  \n");
			sqlBuff.append("FROM                               \n");
			sqlBuff.append("     EVLADM.TBEBWEF1               \n");
			sqlBuff.append("WHERE 1=1                          \n");
			sqlBuff.append("AND  CEBWEPJT = ?                  \n");
			sqlBuff.append("AND  SUBSTR(CEBWEMNO,1,2) = 'MK'   \n");
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			pstmt.setString(1, pjt);
		//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			rs = pstmt.executeQuery();
			if (rs.next())
				mno = rs.getString("mno");
			else
				mno = "001";
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
				rs.close();
			}
			catch (Exception e)
			{
			}
		}
		return mno;
	}
	/**
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.04.12
	 * ��  �� : ���󺸼����� ȭ��(GY010107.jsp)���� ����ó���� TBEBWEF1�� ���� UPDATE
	 * ��  Ÿ : ������ 20060907
	 */
	public void updateRowForGY010107_04(TBEBWEF1Vo vo, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		String bweMno = "";
		try
		{
			
			if ("A".equals(vo.getCebwegnd()))
			{
				bweMno = "MK" + getMNOForGY010103(vo.getCebwepjt(), conn);
			}
			else if ("B".equals(vo.getCebwegnd()))
				bweMno = "M" + vo.getCebwebgt().substring(2, 6);
			else if ("L".equals(vo.getCebwegnd()))
				bweMno = "M" + vo.getCebwebgt().substring(2, 6);			
			
			
			sqlBuff.append("UPDATE  EVLADM.TBEBWEF1      \n");
			sqlBuff.append("SET     CEBWEPST    = ?,     \n"); //1.�������
			sqlBuff.append("        CEBWEBGT    = ?,     \n"); //2.���ְ�����
			sqlBuff.append("        CEBWEBMT    = ?,     \n"); //3.���ָ����� 
			sqlBuff.append("        CEBWEBHD    = ?,     \n"); //4.����������
			sqlBuff.append("        CEBWEMG1    = ?,     \n"); //5.���󰳿���
			sqlBuff.append("        CEBWEGBN    = ?,     \n"); //6.�����߰�����        
			sqlBuff.append("        CEBWEMBG    = ?,     \n"); //7.��������
			sqlBuff.append("        CEBWEHYN    = ?,     \n"); //8.Ȯ�μ���������
			sqlBuff.append("        CEBWEBST    = ?,     \n"); //9.���ֽ�������
			sqlBuff.append("        CEBWEAPP    = ?,     \n"); //10.���ֽ�����
			sqlBuff.append("        CEBWEMNO    = ?      \n"); //11.MO-NO
			sqlBuff.append("WHERE   CEBWEPJT    = ?      \n"); //12.������Ʈ��ȣ
			sqlBuff.append("AND     CEBWEHGB    = ?      \n"); //13.ȣ�ⱸ��
			sqlBuff.append("AND     CEBWEHNO    = ?      \n"); //14.ȣ���ȣ
			sqlBuff.append("AND     CEBWESEQ    = ?      \n"); //15.SEQ
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			int idxparam = 1;
			pstmt.setString(idxparam++, vo.getCebwepst()); //1.�������
			pstmt.setString(idxparam++, vo.getCebwebgt()); //2.���ְ�����
			pstmt.setString(idxparam++, vo.getCebwebmt()); //3.���ָ����� 
			pstmt.setString(idxparam++, vo.getCebwebmt()); //4.���������� -�����ϰ� ������
			pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebwemg1())); //5.���󰳿��� 
			if("L".equals(vo.getCebwegnd())) {
				pstmt.setString(idxparam++, "Y"); //6.�����߰�����
			} else {
				pstmt.setString(idxparam++, ""); //6.�����߰�����        
			}
			pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwembg())); //7.��������
			pstmt.setString(idxparam++, vo.getCebwehyn()); //8.Ȯ�μ���������
			pstmt.setString(idxparam++, vo.getCebwebst()); //9.���ֽ�������
			pstmt.setString(idxparam++, vo.getCebweapp()); //10.���ֽ�����
			pstmt.setString(idxparam++, bweMno); //11.MO-NO
			pstmt.setString(idxparam++, vo.getCebwepjt()); //12.������Ʈ��ȣ
			pstmt.setString(idxparam++, vo.getCebwehgb()); //13.ȣ�ⱸ��
			pstmt.setString(idxparam++, vo.getCebwehno()); //14.ȣ���ȣ
			pstmt.setString(idxparam++, vo.getCebweseq()); //15.SEQ
			
		//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
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
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.04.12
	 * ��  �� : ���󺸼����� ȭ��(GY010107.jsp)���� ����ó���� TBEBWEF1�� ���� INSERT
	 * ��  Ÿ : 2006-04-06 ����� ���󺸼����ֽ��� SPEC ���� �߰� �ۼ�.
	 */
	public void insertRowForGY010107_05(TBEBWEF1Vo vo, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		String bweMno = "";
		String bweSeq = "";
		int mg1 = 0;
		try
		{
			TBEBWEF1Vo selVo = getBWEForGY010107_05(vo, conn);
			if ("A".equals(vo.getCebwegnd()))
			{
				bweMno = "MK" + getMNOForGY010103(vo.getCebwepjt(), conn);
			}
			else if ("B".equals(vo.getCebwegnd()))
				bweMno = "M" + vo.getCebwebgt().substring(2, 6);
			else if ("L".equals(vo.getCebwegnd()))
				bweMno = "M" + vo.getCebwebgt().substring(2, 6);
				
			bweSeq = getBweSeq(vo.getCebwepjt(), vo.getCebwehgb(), vo.getCebwehno(), conn);

				
			sqlBuff.append("INSERT  INTO  EVLADM.TBEBWEF1      \n");
			sqlBuff.append("        (                          \n");
			sqlBuff.append("           CEBWEPJT ,        \n"); //1.������Ʈ��ȣ(pk)
			sqlBuff.append("           CEBWEHGB ,        \n"); //2.ȣ�ⱸ��(pk)
			sqlBuff.append("           CEBWEHNO ,        \n"); //3.ȣ���ȣ(pk)
			sqlBuff.append("           CEBWESEQ ,        \n"); //4.����(pk)
			sqlBuff.append("           CEBWETYP ,        \n"); //5.����
			sqlBuff.append("           CEBWEHTY ,        \n"); //6.��ӱ���
			sqlBuff.append("           CEBWEARA ,        \n"); //7.������
			sqlBuff.append("           CEBWEJUJ ,        \n"); //8.��������
			sqlBuff.append("           CEBWEBPM ,        \n"); //9.����PM
			sqlBuff.append("           CEBWEBSU ,        \n"); //10.�������»�
			sqlBuff.append("           CEBWEFDT ,        \n"); //11.�����μ���������
			sqlBuff.append("           CEBWECBS ,        \n"); //12.���ʹ��ְ�����
			sqlBuff.append("           CEBWEJUC ,        \n"); //13.���ð�����ü
			sqlBuff.append("           CEBWEPST ,        \n"); //14.�������
			sqlBuff.append("           CEBWEGND ,        \n"); //15.��������
			sqlBuff.append("           CEBWEMNO ,        \n"); //16.MO-NO
			sqlBuff.append("           CEBWEGBN ,        \n"); //17.�����߰�����
			sqlBuff.append("           CEBWEBGT ,        \n"); //18.���ְ�����
			sqlBuff.append("           CEBWEBMT ,        \n"); //19.���ָ�����
			sqlBuff.append("           CEBWEBHD ,        \n"); //20.����������
			sqlBuff.append("           CEBWEMG1 ,        \n"); //21.���󰳿���
			sqlBuff.append("           CEBWEAGB ,        \n"); //22.�ó��ܱ���
			sqlBuff.append("           CEBWEMUT ,        \n"); //23.���󺸼����شܰ�
			sqlBuff.append("           CEBWEMBG ,        \n"); //24.��������
			sqlBuff.append("           CEBWEHYN ,        \n"); //25.Ȯ�μ���������
			sqlBuff.append("           CEBWEBST ,        \n"); //26.���ֽ�������
			sqlBuff.append("           CEBWEAPP ,        \n"); //27.���ֽ�����
			sqlBuff.append("           CEBWEABG         \n"); //28.��������
			sqlBuff.append("        )                    \n");
			sqlBuff.append("VALUES  (                    \n");
			sqlBuff.append("           ?, ?, ?, ?,       \n"); //1-4.PK�׷�
			sqlBuff.append("           ?, ?, ?, ?,       \n"); //5-8
			sqlBuff.append("           ?, ?, ?, ?,       \n"); //9-12
			sqlBuff.append("           ?, ?, ?, ?,       \n"); //13-16
			sqlBuff.append("           ?, ?, ?, ?,       \n"); //17-20
			sqlBuff.append("           ?, ?, ?, ?,       \n"); //21-24
			sqlBuff.append("           ?, ?, ?, ?        \n"); //25-28
			sqlBuff.append("        )                    \n");
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			int idxparam = 1;
			pstmt.setString(idxparam++, vo.getCebwepjt()); //1.������Ʈ��ȣ
			pstmt.setString(idxparam++, vo.getCebwehgb()); //2.ȣ�ⱸ��
			pstmt.setString(idxparam++, vo.getCebwehno()); //3.ȣ���ȣ
			pstmt.setString(idxparam++, bweSeq); //4.SEQ
			pstmt.setString(idxparam++, selVo.getCebwetyp()); //5.����
			pstmt.setString(idxparam++, vo.getCebwehty()); //6.��ӱ���
			pstmt.setString(idxparam++, selVo.getCebweara()); //7.������
			pstmt.setString(idxparam++, selVo.getCebwejuj()); //8.�������� 
			pstmt.setString(idxparam++, selVo.getCebwebpm()); //9.����PM
			pstmt.setString(idxparam++, selVo.getCebwebsu()); //10.�������»�
			pstmt.setString(idxparam++, selVo.getCebwefdt()); //11.�����μ���������
			
			
			if ("".equals(vo.getCebwecbs()))
				pstmt.setString(idxparam++, vo.getCebwebgt()); //12.���ʹ��ְ����� 
			else
				pstmt.setString(idxparam++, " "); //12.���ʹ��ְ����� 
				
			pstmt.setString(idxparam++, vo.getCebwejuc()); //13.���ð�����ü
			pstmt.setString(idxparam++, vo.getCebwepst()); //14.�������
			pstmt.setString(idxparam++, vo.getCebwegnd()); //15.�������� 
			pstmt.setString(idxparam++, bweMno); //16.MO-NO 
			pstmt.setString(idxparam++, "Y"); //17.�����߰�����
			pstmt.setString(idxparam++, vo.getCebwebgt()); //18.���ְ�����
			
			mg1 = Integer.parseInt(vo.getCebwemg1());
			pstmt.setString(idxparam++, DateTime.addDays(DateTime.addMonths(vo.getCebwebgt(),mg1),-1)); //19.���ָ����� 
			pstmt.setString(idxparam++, DateTime.addDays(DateTime.addMonths(vo.getCebwebgt(),mg1),-1)); //20.���������� - ������
			pstmt.setInt(idxparam++, mg1); //21.���󰳿���
			
			pstmt.setString(idxparam++, selVo.getCebweagb()); //22.�ó��ܱ���
			pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebwemut())); //23.���󺸼����شܰ� 
			pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebwembg())); //24.�������� 
			pstmt.setString(idxparam++, vo.getCebwehyn()); //25.Ȯ�μ���������
			pstmt.setString(idxparam++, vo.getCebwebst()); //26.���ֽ�������
			pstmt.setString(idxparam++, vo.getCebweapp()); //27.���ֽ����� 
			pstmt.setString(idxparam++, selVo.getCebweabg()); //28.��������
		//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
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
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.03.28
	 * ��  �� : ���󺸼����� ȭ��(GY010107.jsp)���� �ݼ�ó���� TBEBWEF1�� ������¸� UPDATE
	 * ��  Ÿ : 
	 */
	public TBEBWEF1Vo getBWEForGY010107_05(TBEBWEF1Vo vo, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = new StringBuffer();
		String mno = "";
		int spd = 0;
		try
		{
			sqlBuff.append("SELECT                             \n");
			sqlBuff.append("       CEBSBSPD,                   \n");
			sqlBuff.append("       CEBWATYP,                   \n");
			sqlBuff.append("       CEBSDJUJ,                   \n");
			sqlBuff.append("       SUBSTR(CEBSIMDL,2,2) CEBSIMDL, \n");
			sqlBuff.append("       CEBWABPM,                   \n");
			sqlBuff.append("       CEBWABSU,                   \n");
			sqlBuff.append("       CEBQFFDT,                   \n");
			sqlBuff.append("       CEBWAAGB,                   \n");
			sqlBuff.append("       CEBWAABG                    \n");
			sqlBuff.append("FROM                               \n");
			sqlBuff.append("     EVLADM.TBEBWAF1,              \n");
			sqlBuff.append("     EVLADM.TBEBSDF1,              \n");
			sqlBuff.append("     EVLADM.TBEBSBF1,              \n");
			sqlBuff.append("     EVLADM.TBEBSIF1,              \n");
			sqlBuff.append("     EVLADM.TBEBQFF1               \n");
			sqlBuff.append("WHERE 1=1                          \n");
			sqlBuff.append("AND  CEBWAPJT = ?                  \n");
			sqlBuff.append("AND  CEBWAHGB = ?                  \n");
			sqlBuff.append("AND  CEBWAHNO = ?                  \n");
			sqlBuff.append("AND  CEBWAPJT = CEBSDPJT           \n");
			sqlBuff.append("AND  CEBWAHGB = CEBSDHGB           \n");
			sqlBuff.append("AND  CEBWAHNO = CEBSDHNO           \n");
			sqlBuff.append("AND  CEBWAPJT = CEBSIPJT           \n");
			sqlBuff.append("AND  CEBWAHGB = CEBSIHGB           \n");
			sqlBuff.append("AND  CEBWAHNO = CEBSIHNO           \n");
			sqlBuff.append("AND  CEBWAPJT = CEBSBPJT           \n");
			sqlBuff.append("AND  CEBWAHGB = CEBSBHGB           \n");
			sqlBuff.append("AND  CEBWAHNO = CEBSBHNO           \n");
			sqlBuff.append("AND  CEBWAPJT = CEBQFPJT           \n");
			sqlBuff.append("AND  CEBWAHGB = CEBQFHGB           \n");
			sqlBuff.append("AND  CEBWAHNO = CEBQFHNO           \n");			
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			pstmt.setString(1, vo.getCebwepjt());
			pstmt.setString(2, vo.getCebwehgb());
			pstmt.setString(3, vo.getCebwehno());
		//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				vo.setCebwetyp(rs.getString("CEBWATYP"));
				vo.setCebwejuj(rs.getString("CEBSDJUJ"));
				vo.setCebweara(rs.getString("CEBSIMDL"));
				vo.setCebwebpm(rs.getString("CEBWABPM"));
				vo.setCebwebsu(rs.getString("CEBWABSU"));
				vo.setCebwefdt(rs.getString("CEBQFFDT"));
				vo.setCebweagb(rs.getString("CEBWAAGB"));
				vo.setCebweabg(rs.getString("CEBWAABG"));
				spd = rs.getInt("CEBSBSPD");
				if (spd > Integer.parseInt(vo.getCebwehty()))
					vo.setCebwehty("Y");
				else
					vo.setCebwehty("N");
			}
			else
			{
				vo.setCebwetyp("");
				vo.setCebwejuj("");
				vo.setCebweara("");
				vo.setCebwebpm("");
				vo.setCebwebsu("");
				vo.setCebwefdt("");
				vo.setCebweagb("");
				vo.setCebwehty("");
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
				rs.close();
			}
			catch (Exception e)
			{
			}
		}
		return vo;
	}
	
	/**
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.06.26
	 * ��  �� : �������� ��������
	 * ��  Ÿ : 
	 */
	public String getBwejuj(String pjt, String hgb, String hno, Connection conn)
		throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		String juj = "";
		try
		{

			sqlBuff.append("	SELECT 						\n");
			sqlBuff.append("		RTRIM(CEBSDJUJ) CEBSDJUJ		\n");
			sqlBuff.append("	FROM 	EVLADM.TBEBSDF1 	\n");
			sqlBuff.append("	WHERE CEBSDPJT = ? 		\n");
			sqlBuff.append("	AND 	CEBSDHGB = ? 		\n");
			sqlBuff.append("	AND 	CEBSDHNO = ? 		\n");

			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			pstmt.setString(1, pjt);
			pstmt.setString(2, hgb);
			pstmt.setString(3, hno);
			
			
		//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				juj = rs.getString("CEBSDJUJ");
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
				rs.close();
				pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
		return juj;
	}

	/**
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.06.30
	 * ��  �� : ���ʵ�� ���� ��������(���ֱ��к�)
	 * ��  Ÿ : 
	 */
	public String getFirstYN(String pjt, String hgb, String hno, String gnd, Connection conn)
		throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		String firstYn = "";
		try
		{

			sqlBuff.append("	SELECT 						\n");
			sqlBuff.append("		CEBWEPJT					\n");
			sqlBuff.append("	FROM 	EVLADM.TBEBWEF1 	\n");
			sqlBuff.append("	WHERE CEBWEPJT = ? 		\n");
			sqlBuff.append("	AND 	CEBWEHGB = ? 		\n");
			sqlBuff.append("	AND 	CEBWEHNO = ? 		\n");
			sqlBuff.append("	AND 	CEBWEGND = ? 		\n");
			sqlBuff.append("	AND 	RTRIM(CEBWEPST) != '' 		\n");

			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			pstmt.setString(1, pjt);
			pstmt.setString(2, hgb);
			pstmt.setString(3, hno);
			pstmt.setString(4, gnd);
			
		//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				firstYn = "N";
			}
			else
			{
				firstYn = "Y";
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
				rs.close();
				pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
		return firstYn;
	}
	/**
	 * �ۼ��� : jkkoo
	 * �ۼ��� : 2006.06.26
	 * ��  �� : �������� ��������
	 * ��  Ÿ : 
	 */
	public String getBweSeq(String pjt, String hgb, String hno, Connection conn)
		throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBWEF1Vo vo = new TBEBWEF1Vo();
		String seq = "";
		try
		{
			sqlBuff.append("	SELECT 						\n");
			sqlBuff.append("		 SUBSTR(DIGITS(CAST(COALESCE(MAX(CEBWESEQ),'0') as integer)+1),9,2) as SEQ		\n");
			sqlBuff.append("	FROM EVLADM.TBEBWEF1 	\n");
			sqlBuff.append("	WHERE CEBWEPJT = ? 		\n");
			sqlBuff.append("	AND 	CEBWEHGB = ? 		\n");
			sqlBuff.append("	AND 	CEBWEHNO = ? 		\n");

			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			pstmt.setString(1, pjt);
			pstmt.setString(2, hgb);
			pstmt.setString(3, hno);
			
			
		//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				seq = rs.getString("SEQ");
			}
			else
				seq = "01";

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
				rs.close();
				pstmt.close();
			}
			catch (Exception e)
			{
			}
		}
		return seq;
	}	
   
   
   
   /**
    * �ۼ��� : mhcho
    * �ۼ��� : 2006.07.06
    * ����   : ���к����(�������� - ����) update �Ѵ� . 
    * ��Ÿ   : 
    */
   public void updateRowForGY030202_JA6(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement   pstmt    = null;
      StringBuffer                sqlBuff  = new StringBuffer();
      TBEBWEF1Vo                  vo       = new TBEBWEF1Vo();
      ComMethodVo                 comVo;   
      try
      {
         sqlBuff.append("UPDATE EVLADM.TBEBWEF1  SET \n");
         sqlBuff.append("  CEBWEBHD = ?       \n"); // ��������           
         sqlBuff.append("WHERE   CEBWEPJT = ? \n"); // ProjNo                    
         sqlBuff.append("AND     CEBWEHGB = ? \n"); // ȣ�ⱸ��                
         sqlBuff.append("AND     CEBWEHNO = ? \n"); // ȣ���ȣ        
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBWEF1Vo();
            vo = (TBEBWEF1Vo) list.get(i);
            pstmt.setString(idxparam++, vo.getCebwebhd());            
            pstmt.setString(idxparam++, vo.getCebwepjt());
            pstmt.setString(idxparam++, vo.getCebwehgb());
            pstmt.setString(idxparam++, vo.getCebwehno());
            pstmt.addBatch();
        //    logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
                           
         }
         pstmt.executeBatch();
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
	* �ۼ��� : jhlee
	* �ۼ��� : 2006.08.14
	* ����   : ����PM����ÿ� update �Ѵ�. 
	* ��Ÿ   : 
	*/
   public void updateRowForGY090201(ArrayList list, Connection conn) throws Exception {
	  LoggablePreparedStatement  pstmt   = null;
	  StringBuffer               sqlBuff = null; 
	  TBEBWEF1Vo                 vo      = null;

	  try {
		 for(int i=0; i<list.size(); i++) {
			int idxparam = 1;
			sqlBuff = new StringBuffer(); 
			vo = new TBEBWEF1Vo();
			vo = (TBEBWEF1Vo) list.get(i);

			sqlBuff.append("UPDATE                 \n");
			sqlBuff.append("       EVLADM.TBEBWEF1 \n");
			sqlBuff.append("   SET                 \n");
			sqlBuff.append("       CEBWEBPM = ?    \n");
			sqlBuff.append(" WHERE                 \n");
			sqlBuff.append("       CEBWEBSU = ?    \n");
			sqlBuff.append("   AND CEBWEBPM = ?    \n");

			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());             

			pstmt.setString(idxparam++, vo.getCebwebpm());
			pstmt.setString(idxparam++, vo.getCebwebsu());
			pstmt.setString(idxparam++, vo.getCebwebpm_f());

		//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			pstmt.executeUpdate();
		 }
	  } catch(Exception e) {
		 e.printStackTrace();
		 throw e;
	  } finally {
		 try {
			if(pstmt != null) pstmt.close();
		 } catch (Exception e) {}
	  }
   }
   
   /**
	* �ۼ��� : jhlee
	* �ۼ��� : 2006.08.16
	* ����   : ��(��)������ ����ÿ� update �Ѵ�. 
	* ��Ÿ   : 
	*/
   public void updateRowForGY090301(ArrayList list, Connection conn) throws Exception {
	  LoggablePreparedStatement  pstmt   = null;
	  StringBuffer               sqlBuff = null; 
	  TBEBWEF1Vo                 vo      = null;

	  try {
		 for(int i=0; i<list.size(); i++) {
			int idxparam = 1;
			sqlBuff = new StringBuffer(); 
			vo = new TBEBWEF1Vo();
			vo = (TBEBWEF1Vo) list.get(i);

			sqlBuff.append("UPDATE                 \n");
			sqlBuff.append("       EVLADM.TBEBWEF1 \n");
			sqlBuff.append("   SET                 \n");
			sqlBuff.append("       CEBWEJUJ = ?    \n");
			sqlBuff.append(" WHERE                 \n");
			sqlBuff.append("       CEBWEPJT = ?    \n");
			sqlBuff.append("   AND CEBWEHGB = ?    \n");
			sqlBuff.append("   AND CEBWEHNO = ?    \n");
			sqlBuff.append("   AND CEBWEBSU = ?    \n");
			sqlBuff.append("   AND RTRIM(CEBWEBHD) > REPLACE(CHAR(CURRENT DATE),'-','') \n");

			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());             

			pstmt.setString(idxparam++, vo.getCebwejuj());
			pstmt.setString(idxparam++, vo.getCebwepjt());
			pstmt.setString(idxparam++, vo.getCebwehgb());
			pstmt.setString(idxparam++, vo.getCebwehno());
			pstmt.setString(idxparam++, vo.getCebwebsu());

		//	logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			pstmt.executeUpdate();
		 }
	  } catch(Exception e) {
		 e.printStackTrace();
		 throw e;
	  } finally {
		 try {
			if(pstmt != null) pstmt.close();
		 } catch (Exception e) {}
	  }
   }
}
