/**
 * �� �� �� : TBEBXLF1Dao.java
 * ��    �� : 
 * �� �� �� : jkkoo
 * �� �� �� : 2006-03-10 ���� 6:42:31
 * ���泻�� :
 ** * by shhwang : BSK --> BIK( ���߿� host�� �ø����� �ٽ� BSK���̺�� �Է��ϴ� ������ �������ƾߵ� 
 *  ���̺� �̸��� BIK�� �ٲٰ� ���ϸ��� BSKDao�� �״�� ����� : ���̺��� Į������ bsk�� ������ ���� naming
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
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWMF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBXLF1Vo;

//import org.apache.log4j.Logger;

public class TBEBXLF1Dao extends AbstractBusinessService implements FrwCrudDAO
{
//	static Logger logger = Logger.getLogger(TBEBSKF1Dao.class);
	/**
	 * <pre>
	 * @see com.helco.bosu.frw.ejb.cruddao.FrwCrudDAO#insert(java.lang.Object)
	 * @param  
	 * @throws 
	 * </pre>
	 */
	public void insert(Object vo) throws Exception
	{
	}
	/**
	 * <pre>
	 * @see com.helco.bosu.frw.ejb.cruddao.FrwCrudDAO#insert(java.lang.Object, java.lang.String)
	 * @param  
	 * @throws 
	 * </pre>
	 */
	public void insert(Object vo, String subMethodFlag) throws Exception
	{
		Connection conn = null;
		TBEBXLF1Vo vo1;
		ArrayList alist = (ArrayList) vo;
		try
		{ 
			if (alist.size() > 0)
			{
//				conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
				vo1 = new TBEBXLF1Vo();
				vo1 = (TBEBXLF1Vo) alist.get(0);
				String db_con = Utillity.getSapJndi(vo1.getMandt());
				conn = getConnection(db_con);
				if ("MC020101".equals(subMethodFlag)) // 2006.04.21 shhwang
					insertRowForMC020101(alist, conn);
				if ("MC030101".equals(subMethodFlag)) // 2006.04.21 shhwang
					insertRowForMC030101(alist, conn);
	            if ("ComMethodForC".equals(subMethodFlag)) // 2006.05.10 jkkoo
	               insertRowComMethodForC(alist, conn); 
	            if ("ComMethodForETC".equals(subMethodFlag)) // 2006.05.15 �ο�����
	               insertRowComMethodForETC(alist, conn);                               
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			DBUtil.close(null, null, null);
		}
	}
	/**
	 * <pre>
	 * @see com.helco.bosu.frw.ejb.cruddao.FrwCrudDAO#selectList(java.lang.Object)
	 * @param  
	 * @throws 
	 * </pre>
	 */
	public ArrayList selectList(Object searchCondVO) throws Exception
	{
		return null;
	}
	/**
	 * <pre>
	 * @see com.helco.bosu.frw.ejb.cruddao.FrwCrudDAO#select(java.lang.Object)
	 * @param  
	 * @throws 
	 * </pre>
	 */
	public Object select(Object condVO) throws Exception
	{
		return null;
	}
	public Object select2(Object condVO) throws Exception
	{
		return null;
	}
	/**
	 * <pre>
	 * @see com.helco.bosu.frw.ejb.cruddao.FrwCrudDAO#select(java.util.ArrayList, java.lang.String)
	 * @param  
	 * @throws 
	 * </pre>
	 */
	public Object select(ArrayList compPk, String subMethodFlag) throws Exception
	{
      Connection conn = null;
      ArrayList alist = (ArrayList) compPk;
      ArrayList rtnList = new ArrayList();
      try
      {
         if (alist.size() > 0)
         {
//            conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
            //conn = getConnection();
            if ("ComMethod".equals(subMethodFlag)) // 2006.05.02 jkkoo(�� ��ϵ� �����ȹ ���� ��������)
               rtnList = selectRowComMethodForCancel(alist, conn);
            if ("ComMethod_stop".equals(subMethodFlag)) // 2009.04.21 jhlee(�� ��ϵ� �����ȹ ���� ��������)
                rtnList = selectRowComMethodForCancelStop(alist, conn);
            if ("ComMethod_silpae".equals(subMethodFlag)) // 2008.12.11 jhlee(�� ��ϵ� �����ȹ ���� ��������)
                rtnList = selectRowComMethodForCancelSilPae(alist, conn);
            if ("ComMethodForRestore".equals(subMethodFlag)) // 2006.05.12 jkkoo(�����ȹ ������� ��ȸ)
               rtnList = selectRowComMethodForRestore(alist, conn);    
            if ("ComMethodForRestore_stop".equals(subMethodFlag)) // 2009.04.21 jhlee(����/���� �����ȹ ������� ��ȸ)
                rtnList = selectRowComMethodForRestoreStop(alist, conn);  
            if ("ComMethodPre".equals(subMethodFlag)) // 2006.06.05 jkkoo(������ ������ �̹� ���޿Ϸ�� ���� �����ȹ ��������)
               rtnList = selectRowComMethodForCancelPre(alist, conn);                       
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         DBUtil.close(null, null, null);
      }
      return rtnList;
	}
	/**
	 * <pre>
	 * @see com.helco.bosu.frw.ejb.cruddao.FrwCrudDAO#deleteList(java.lang.Object, java.lang.String)
	 * @param  
	 * @throws 
	 * </pre>
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
				if ("MC020101".equals(subMethodFlag)) // 2006.03.10 shwhang
					deleteRowForMC020101(alist, conn);
				if ("MC030101".equals(subMethodFlag)) // 2006.04.28 shwhang
					deleteRowForMC030101(alist, conn);
				if ("GY020103_04".equals(subMethodFlag)) // 2006.08.27 shwhang
					deleteRowForGY020103_04(alist, conn);
				if ("GY020105".equals(subMethodFlag)) // 2006.08.27 shwhang
					deleteRowForGY020105(alist, conn);
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
	 * <pre>
	 * @see com.helco.bosu.frw.ejb.cruddao.FrwCrudDAO#update(java.lang.Object)
	 * @param  
	 * @throws 
	 * </pre>
	 */
	public void update(Object vo) throws Exception
	{
	}
	/**
	 * <pre>
	 * @see com.helco.bosu.frw.ejb.cruddao.FrwCrudDAO#update(java.lang.Object, java.lang.String)
	 * @param  
	 * @throws 
	 * </pre>
	 */
	public void update(Object vo, String subMethodFlag) throws Exception
	{
		Connection conn = null;
		TBEBXLF1Vo vo1;
		ArrayList alist = (ArrayList) vo;
		try
		{
			if (alist.size() > 0)
			{
//				conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
				vo1 = new TBEBXLF1Vo();
				vo1 = (TBEBXLF1Vo) alist.get(0);
				String db_con = Utillity.getSapJndi(vo1.getMandt());
				conn = getConnection(db_con);
				//conn = getConnection();
				if ("MC020101".equals(subMethodFlag)) // 2006.04.13 shhwang
					updateRowForMC020101(alist, conn);
				if ("MC030101".equals(subMethodFlag)) // 2006.04.28 shhwang
					updateRowForMC030101(alist, conn);
				if ("TX020101".equals(subMethodFlag)) // 2006.04.25 mhcho
					updateRowForTX020101(alist, conn);               
            if("TX020201".equals(subMethodFlag))     // 2006.04.26 mhcho
               updateRowForTX020201(alist, conn);                
            if("TX020401_S".equals(subMethodFlag))   // 2006.04.29 mhcho
               updateRowForTX020401_S(alist, conn);                
            if("TX020401_C".equals(subMethodFlag))   // 2006.04.29 mhcho
               updateRowForTX020401_C(alist, conn);                              
            if("TX020301".equals(subMethodFlag))     // 2006.05.01 mhcho
               updateRowForTX020301(alist, conn);           
            if("TX030101".equals(subMethodFlag))     // 2006.05.02 mhcho
               updateRowForTX030101(alist, conn);         
            if("ComMethodForCancelForC".equals(subMethodFlag))     // 2006.05.02 jkkoo
               updateRowForCancelForC(alist, conn);         
            if("ComMethodForCancelForETC1".equals(subMethodFlag))     // 2006.05.11 jkkoo
               updateRowForCancelForETC1(alist, conn);     
            if("ComMethodForCancelForETC2".equals(subMethodFlag))     // 2006.05.12 jkkoo
               updateRowForCancelForETC2(alist, conn);         
            if("ComMethodForCancelForETC3".equals(subMethodFlag))     // 2006.05.12 jkkoo
               updateRowForCancelForETC3(alist, conn);     
            if("ComMethodForCancelForETC4".equals(subMethodFlag))     // 2008.06.20 jhlee
                updateRowForCancelForETC4(alist, conn);     
            if("ComMethodForCancelForCHG".equals(subMethodFlag))     // 2006.06.05 jkkoo
               updateRowForCancelForCHG(alist, conn);                                    
			   if("MC010101".equals(subMethodFlag)) // 2006.05.24 jhlee
			      updateRowForMC010101(alist, conn);
            if ("MC020101_TI".equals(subMethodFlag)) // 2006.08.16 mhcho
               updateRowForMC020101_TI(alist, conn);
            if ("MC020101_TD".equals(subMethodFlag)) // 2006.08.16 mhcho
               updateRowForMC020101_TD(alist, conn);                              
			   if("GY090301".equals(subMethodFlag)) // 2006.08.16 jhlee
			      updateRowForGY090301(alist, conn);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			DBUtil.close(null, null, null);
		}
	}
	/**
	 * <pre>
	 * @see com.helco.bosu.frw.ejb.cruddao.FrwCrudDAO#multiSaveForGrid(java.lang.Object, java.lang.Object, java.lang.Object)
	 * @param  
	 * @throws 
	 * </pre>
	 */
	public int multiSaveForGrid(Object iobj, Object uobj, Object dobj) throws Exception
	{
		return 0;
	}
	/**
			* �ۼ��� : shhwang
			* �ۼ��� : 2006.04.20
			* ����    : ������ǰ��������� �μ�Ʈ�Ѵ�. 
			* ��Ÿ    : 
	*/
	public void insertRowForMC020101(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = null;
		TBEBXLF1Vo vo = new TBEBXLF1Vo();
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBXLF1Vo();
				vo = (TBEBXLF1Vo) list.get(i);
				sqlBuff = new StringBuffer();
				sqlBuff = new StringBuffer();
				sqlBuff.append("INSERT  INTO  EVLADM.TBEBXLF1 (  \n");
				sqlBuff.append("			 CEBXLUPN,  \n");
				sqlBuff.append("			 CEBXLCST,  \n");
				sqlBuff.append("			 CEBXLPJT,  \n");
				sqlBuff.append("			 CEBXLHGB,  \n");
				sqlBuff.append("			 CEBXLHNO,  \n");
				sqlBuff.append("			 CEBXLSEQ,  \n");
				sqlBuff.append("			 CEBXLGND,  \n");
				sqlBuff.append("			 CEBXLMYM,  \n");
				sqlBuff.append("			 CEBXLISR,  \n");
				sqlBuff.append("			 CEBXLMNO,  \n");
				sqlBuff.append("			 CEBXLARA,  \n");
				sqlBuff.append("			 CEBXLGBN,  \n");
				sqlBuff.append("			 CEBXLGJP,  \n");
				sqlBuff.append("			 CEBXLYDT,  \n");
				sqlBuff.append("			 CEBXLBGB,  \n");
				sqlBuff.append("			 CEBXLBHB,  \n");
				sqlBuff.append("			 CEBXLZER,  \n");
				sqlBuff.append("			 CEBXLSJU,  \n");
				sqlBuff.append("			 CEBXLAMD,  \n");
				sqlBuff.append("			 CEBXLVAD,  \n");
				sqlBuff.append("			 CEBXLTOD,  \n");
				sqlBuff.append("			 CEBXLAMT,  \n");
				sqlBuff.append("			 CEBXLVAT,  \n");
				sqlBuff.append("			 CEBXLTOT,  \n");
				sqlBuff.append("			 CEBXLNDT,  \n");
				sqlBuff.append("			 CEBXLNTM,  \n");
				sqlBuff.append("           CEBXLBGD ,        \n"); //35.��౸��---- 0510 ���� 
				sqlBuff.append("           CEBXLJGB ,        \n"); //36.�����ڷᱸ��
				sqlBuff.append("           CEBXLUP1 ,        \n"); //37.����������Ʈ��ȣ
				sqlBuff.append("           CEBXLCS1 ,        \n"); //38.�ŷ�ó
				sqlBuff.append("           CEBXLYYM ,        \n"); //39.������س��
				sqlBuff.append("			 CEBXLCSJ,  \n");
            sqlBuff.append("         CEBXLBSU,  \n");
            sqlBuff.append("         CEBXLGBU)  \n");
				sqlBuff.append(" SELECT '" + vo.getCebxlupn() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlcst() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlpjt() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlhgb() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlhno() + "',				 \n"); //
				sqlBuff.append(" 			 '" + vo.getCebxlseq() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlgnd() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlmym() + "',				 \n");
				sqlBuff.append(" 			 SUBSTR(DIGITS(CAST(COALESCE(MAX(CEBXLISR),'0') AS INTEGER)+1),9,2),				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlmno() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlara() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlgbn() + "',				 \n");
				sqlBuff.append(" 			 " + vo.getCebxlgjp() + ",				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlydt() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlbgb() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlbhb() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlzer() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlsju() + "',				 \n");
				sqlBuff.append(" 			 " + vo.getCebxlamd() + ",				 \n");
				sqlBuff.append(" 			 " + vo.getCebxlvad() + ",				 \n");
				sqlBuff.append(" 			 " + vo.getCebxltod() + ",				 \n");
				sqlBuff.append(" 			 " + vo.getCebxlamt() + ",				 \n");
				sqlBuff.append(" 			 " + vo.getCebxlvat() + ",				 \n");
				sqlBuff.append(" 			 " + vo.getCebxltot() + ",				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlndt() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlntm() + "',				 \n");				
				sqlBuff.append(" 			 '" + vo.getCebxlbgd() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxljgb() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlup1() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlcs1() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlyym() + "',				 \n");	
				sqlBuff.append(" 			 '" + vo.getCebxlcsj() + "',  			 \n");
            sqlBuff.append("         '" + vo.getCebxlbsu() + "',            \n");
            sqlBuff.append("         '" + vo.getCebxlgbu() + "'             \n");
				sqlBuff.append(" FROM  EVLADM.TBEBXLF1 WHERE CEBXLUPN= ? AND CEBXLCST= ? AND CEBXLPJT= ?  \n");
				sqlBuff.append(" AND CEBXLHGB= ?  AND CEBXLHNO= ?  AND CEBXLSEQ= ?  AND CEBXLGND= ? AND CEBXLMYM= ?  \n");
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				pstmt.setString(idxparam++, vo.getCebxlupn());
				pstmt.setString(idxparam++, vo.getCebxlcst());
				pstmt.setString(idxparam++, vo.getCebxlpjt());
				pstmt.setString(idxparam++, vo.getCebxlhgb());
				pstmt.setString(idxparam++, vo.getCebxlhno());
				pstmt.setString(idxparam++, vo.getCebxlseq());
				pstmt.setString(idxparam++, vo.getCebxlgnd());
				pstmt.setString(idxparam++, vo.getCebxlmym());
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
	* �ۼ��� : shhwang
	* �ۼ��� : 2006.04.21
	* ����    : ������ü��������� �μ�Ʈ�Ѵ�. 
	* ��Ÿ    : 
	*/
	public void insertRowForMC030101(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = null;
		TBEBXLF1Vo vo = new TBEBXLF1Vo();
		
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBXLF1Vo();
				vo = (TBEBXLF1Vo) list.get(i);
				sqlBuff = new StringBuffer();
				
				sqlBuff.append("INSERT  INTO  EVLADM.TBEBXLF1 (  \n");
				sqlBuff.append("			 CEBXLUPN,  \n");
				sqlBuff.append("			 CEBXLCST,  \n");
				sqlBuff.append("			 CEBXLPJT,  \n");
				sqlBuff.append("			 CEBXLHGB,  \n");
				sqlBuff.append("			 CEBXLHNO,  \n");
				sqlBuff.append("			 CEBXLSEQ,  \n");
				sqlBuff.append("			 CEBXLGND,  \n");
				sqlBuff.append("			 CEBXLMYM,  \n");
				sqlBuff.append("			 CEBXLISR,  \n");
				sqlBuff.append("			 CEBXLMNO,  \n");
				sqlBuff.append("			 CEBXLARA,  \n");
				sqlBuff.append("			 CEBXLGBN,  \n");
				sqlBuff.append("			 CEBXLGJP,  \n");
				sqlBuff.append("			 CEBXLYDT,  \n");
				sqlBuff.append("			 CEBXLBGB,  \n");
				sqlBuff.append("			 CEBXLBHB,  \n");
				sqlBuff.append("			 CEBXLZER,  \n");
				sqlBuff.append("			 CEBXLSJU,  \n");
				sqlBuff.append("			 CEBXLAMD,  \n");
				sqlBuff.append("			 CEBXLVAD,  \n");
				sqlBuff.append("			 CEBXLTOD,  \n");
				sqlBuff.append("			 CEBXLAMT,  \n");
				sqlBuff.append("			 CEBXLVAT,  \n");
				sqlBuff.append("			 CEBXLTOT,  \n");
				sqlBuff.append("			 CEBXLNDT,  \n");
				sqlBuff.append("			 CEBXLNTM,  \n");
				sqlBuff.append("           CEBXLBGD,  \n"); //35.��౸�� -- 0510���� 
				sqlBuff.append("           CEBXLJGB,   \n"); //36.�����ڷᱸ��
				sqlBuff.append("           CEBXLUP1,   \n"); //37.����������Ʈ��ȣ
				sqlBuff.append("           CEBXLCS1,  \n"); //38.�ŷ�ó
				sqlBuff.append("           CEBXLYYM,  \n"); //39.������س��
				sqlBuff.append("			 CEBXLCSJ,   \n");
				sqlBuff.append("			 CEBXLHAM )  \n"); //�Һΰ�����				
				sqlBuff.append(" SELECT  '"+ vo.getCebxlupn()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlcst()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlpjt()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlhgb()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlhno()+"',				 \n"); //CEBXLHNO
				sqlBuff.append(" 			 '"+ vo.getCebxlseq()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlgnd()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlmym()+"',				 \n");
				sqlBuff.append(" 			 SUBSTR(DIGITS(CAST(COALESCE(MAX(CEBXLISR),'0') AS INTEGER)+1),9,2),				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlmno()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlara()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlgbn()+"',				 \n");
				sqlBuff.append(" 			 "+ vo.getCebxlgjp()+",				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlydt()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlbgb()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlbhb()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlzer()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlsju()+"',				 \n");
				sqlBuff.append(" 			 "+ vo.getCebxlamd()+",				 \n");
				sqlBuff.append(" 			 "+ vo.getCebxlvad()+",				 \n");
				sqlBuff.append(" 			 "+ vo.getCebxltod()+",				 \n");
				sqlBuff.append(" 			 "+ vo.getCebxlamt()+",				 \n");
				sqlBuff.append(" 			 "+ vo.getCebxlvat()+",				 \n");
				sqlBuff.append(" 			 "+ vo.getCebxltot()+",				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlndt()+"',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlntm()+"',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlbgd() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxljgb() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlup1() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlcs1() + "',				 \n");
				sqlBuff.append(" 			 '" + vo.getCebxlyym() + "',				 \n");
				sqlBuff.append(" 			 '"+ vo.getCebxlcsj()+"',				 \n");
				sqlBuff.append(" 			 "+ vo.getCebxlham()+"				 \n");								
				sqlBuff.append(" 	FROM  EVLADM.TBEBXLF1 WHERE CEBXLUPN= ?  \n");
				sqlBuff.append(" 	AND CEBXLCST= ?  \n");
				sqlBuff.append(" 	AND CEBXLPJT= ?  \n");
				sqlBuff.append(" 	AND CEBXLHGB= ?  \n");
				sqlBuff.append(" 	AND CEBXLHNO= ?  \n");
				sqlBuff.append(" 	AND CEBXLSEQ= ?  \n");
				sqlBuff.append(" 	AND CEBXLGND= ?  \n");
				sqlBuff.append(" 	AND CEBXLMYM= ?  \n");		

						pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
		
				pstmt.setString(idxparam++, vo.getCebxlupn());
				pstmt.setString(idxparam++, vo.getCebxlcst());
				pstmt.setString(idxparam++, vo.getCebxlpjt());
				pstmt.setString(idxparam++, vo.getCebxlhgb());
				pstmt.setString(idxparam++, vo.getCebxlhno());
				pstmt.setString(idxparam++, vo.getCebxlseq());
				pstmt.setString(idxparam++, vo.getCebxlgnd());
				pstmt.setString(idxparam++, vo.getCebxlmym());
				
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
	* �ۼ��� : shhwang
	* �ۼ��� : 2006.04.20
	* ����    : ������ǰ ������� ���� 
	* ��Ÿ    : 
	*/
	public void deleteRowForMC020101(ArrayList list, Connection conn) throws Exception
	{
		LoggablePreparedStatement pstmt = null;
		StringBuffer sqlBuff = new StringBuffer();
		TBEBXLF1Vo vo = new TBEBXLF1Vo();
		try
		{
			sqlBuff.append("  DELETE            \n");
			sqlBuff.append("  FROM     EVLADM.TBEBXLF1 \n");
			sqlBuff.append("  WHERE CEBXLUPN   = ?   \n");
			sqlBuff.append("  AND   CEBXLCST   =   ?     \n");
			sqlBuff.append("  AND   CEBXLMNO   =   ?      \n");
			sqlBuff.append("  AND   CEBXLGND   =   ?     \n");
			sqlBuff.append("  AND   CEBXLYDT   =   ?    \n");
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
				vo = new TBEBXLF1Vo();
				vo = (TBEBXLF1Vo) list.get(i);
				pstmt.setString(idxparam++, vo.getCebxlupn());
				pstmt.setString(idxparam++, vo.getCebxlcst());
				pstmt.setString(idxparam++, vo.getCebxlmno());
				pstmt.setString(idxparam++, vo.getCebxlgnd());
				pstmt.setString(idxparam++, vo.getCebxlydt());
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
		* �ۼ��� : shhwang
		* �ۼ��� : 2006.04.28
		* ����    : ������ü  ������� ���� 
		* ��Ÿ    : 
		*/
		public void deleteRowForMC030101(ArrayList list, Connection conn) throws Exception
		{
			LoggablePreparedStatement pstmt  = null;         
			StringBuffer sqlBuff    = new StringBuffer();         
			TBEBXLF1Vo vo = new TBEBXLF1Vo();
			try
			{ 
				sqlBuff.append("  DELETE                    \n");
				sqlBuff.append("  FROM     EVLADM.TBEBXLF1  \n");
				sqlBuff.append("  WHERE CEBXLUPN   = ?      \n");
				sqlBuff.append("  AND   CEBXLCST   = ?      \n");
				sqlBuff.append("  AND   CEBXLPJT   = ?      \n");
				sqlBuff.append("  AND   CEBXLGND   = ?      \n");
				sqlBuff.append("  AND   CEBXLYDT   = ?      \n");
							
				pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
				for (int i = 0; i < list.size(); i++)
				{
					int idxparam = 1;
					vo = new TBEBXLF1Vo();
					vo = (TBEBXLF1Vo) list.get(i);
					pstmt.setString(idxparam++, vo.getCebxlupn());
					pstmt.setString(idxparam++, vo.getCebxlcst());
					pstmt.setString(idxparam++, vo.getCebxlpjt());
					pstmt.setString(idxparam++, vo.getCebxlgnd());
					pstmt.setString(idxparam++, vo.getCebxlydt());
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
	 * �ۼ��� : shhwang
	 * �ۼ��� : 2006.04.25
	 * ����   : ������ǰ ��������� �����ȹ  ������Ʈ 
	 * ��Ÿ   : 
	 */
	public void updateRowForMC020101(ArrayList alist, Connection conn) throws Exception
	{
		try
		{
			//delete 
			deleteRowForMC020101(alist, conn);
			//insert 
			insertRowForMC020101(alist, conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	/**
		 * �ۼ��� : shhwang
		 * �ۼ��� : 2006.04.28
		 * ����   : ������ǰ ��������� �����ȹ  ������Ʈ 
		 * ��Ÿ   : 
		 */
		public void updateRowForMC030101(ArrayList alist, Connection conn) throws Exception
		{
			try
			{
				//delete 
				deleteRowForMC030101(alist, conn);
				//insert 
				insertRowForMC030101(alist, conn);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
   /**
    * �ۼ��� : mhcho
    * �ۼ��� : 2006.04.25
    * ��  �� : ���ݰ�꼭�������� ����
    * ��  Ÿ : 
   */
   public void updateRowForTX020101(ArrayList list, Connection conn) throws Exception 
   {
      LoggablePreparedStatement  pstmt    = null ;
      StringBuffer               sqlBuff  = new StringBuffer(); 
      TBEBXLF1Vo                 vo       = new TBEBXLF1Vo();  
           
      try { 
         sqlBuff.append("UPDATE EVLADM.TBEBXLF1  \n");
         sqlBuff.append("SET                     \n");
         sqlBuff.append(" CEBXLBGD = ?,          \n");  
         sqlBuff.append(" CEBXLJGB = ?,          \n");
         sqlBuff.append(" CEBXLUP1 = ?,          \n"); 
         sqlBuff.append(" CEBXLCS1 = ?,          \n"); 
         sqlBuff.append(" CEBXLYYM = ?,          \n"); 
         sqlBuff.append(" CEBXLNDT = ?,          \n"); 
         sqlBuff.append(" CEBXLNTM = ?,          \n"); 
         sqlBuff.append(" CEBXLCSJ = ?           \n"); 
         sqlBuff.append("WHERE                   \n"); 
         sqlBuff.append(" CEBXLUPN = ?           \n"); 
         sqlBuff.append(" AND CEBXLCST = ?       \n"); 
         sqlBuff.append(" AND CEBXLPJT = ?       \n"); 
         sqlBuff.append(" AND CEBXLHGB = ?       \n"); 
         sqlBuff.append(" AND CEBXLHNO = ?       \n"); 
         sqlBuff.append(" AND CEBXLSEQ = ?       \n"); 
         sqlBuff.append(" AND CEBXLGND = ?       \n");
         sqlBuff.append(" AND CEBXLMYM = ?       \n"); 
         sqlBuff.append(" AND CEBXLISR = ?       \n");          
               
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
            
         for (int i = 0; i < list.size(); i++) {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);
            pstmt.setString(idxparam++, vo.getCebxlbgd());  
            pstmt.setString(idxparam++, vo.getCebxljgb());  
            pstmt.setString(idxparam++, vo.getCebxlupn());
            pstmt.setString(idxparam++, vo.getCebxlcst());
            pstmt.setString(idxparam++, vo.getCebxlyym());     
            pstmt.setString(idxparam++, vo.getCebxlndt()); 
            pstmt.setString(idxparam++, vo.getCebxlntm()); 
            pstmt.setString(idxparam++, vo.getCebxlcsj());    
            pstmt.setString(idxparam++, vo.getCebxlupn());   
            pstmt.setString(idxparam++, vo.getCebxlcst());   
            pstmt.setString(idxparam++, vo.getCebxlpjt());   
            pstmt.setString(idxparam++, vo.getCebxlhgb());   
            pstmt.setString(idxparam++, vo.getCebxlhno());   
            pstmt.setString(idxparam++, vo.getCebxlseq());   
            pstmt.setString(idxparam++, vo.getCebxlgnd());   
            pstmt.setString(idxparam++, vo.getCebxlmym());   
            pstmt.setString(idxparam++, vo.getCebxlisr());    
            
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ��� : mhcho
    * �ۼ��� : 2006.04.25
    * ��  �� : ���ݰ�꼭���� �۾�
    * ��  Ÿ : 
    */
   public void updateRowForTX020201(ArrayList list, Connection conn) throws Exception 
   {
      LoggablePreparedStatement  pstmt    = null ;
      StringBuffer               sqlBuff  = new StringBuffer(); 
      TBEBXLF1Vo                 vo       = new TBEBXLF1Vo();  
           
      try { 
         sqlBuff.append("UPDATE EVLADM.TBEBXLF1  \n");
         sqlBuff.append("SET                     \n");
         sqlBuff.append(" CEBXLTIS = ?,          \n");  
         sqlBuff.append(" CEBXLTNO = ?,          \n");
         sqlBuff.append(" CEBXLNDT = ?,          \n"); 
         sqlBuff.append(" CEBXLNTM = ?,          \n"); 
         sqlBuff.append(" CEBXLCSJ = ?           \n");
         sqlBuff.append("WHERE                   \n"); 
         sqlBuff.append("     CEBXLUPN = ?       \n"); 
         sqlBuff.append(" AND CEBXLCST = ?       \n"); 
         sqlBuff.append(" AND CEBXLYYM = ?       \n"); 
         sqlBuff.append(" AND CEBXLARA = ?       \n"); 
         sqlBuff.append(" AND CEBXLBGD = ?       \n"); 
         sqlBuff.append(" AND CEBXLBHB = ?       \n"); 
         sqlBuff.append(" AND CEBXLBGB = ?       \n");
         sqlBuff.append(" AND CEBXLSJU = ?       \n"); 
         sqlBuff.append(" AND CEBXLJGB = ?       \n"); 
         sqlBuff.append(" AND RTRIM(CEBXLTIS) = ''  \n");   //2006.08.18 mhcho �߰� 
         sqlBuff.append(" AND CEBXLMYM = ?  \n");           //2006.08.21 mhcho �߰� 
               
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
            
         for (int i = 0; i < list.size(); i++) {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);
            pstmt.setString(idxparam++, vo.getCebxltis());  
            pstmt.setString(idxparam++, vo.getCebxltno());  
            pstmt.setString(idxparam++, vo.getCebxlndt());
            pstmt.setString(idxparam++, vo.getCebxlntm());
            pstmt.setString(idxparam++, vo.getCebxlcsj());     
            pstmt.setString(idxparam++, vo.getCebxlupn()); 
            pstmt.setString(idxparam++, vo.getCebxlcst()); 
            pstmt.setString(idxparam++, vo.getCebxlyym());    
            pstmt.setString(idxparam++, vo.getCebxlara());   
            pstmt.setString(idxparam++, vo.getCebxlbgd());   
            pstmt.setString(idxparam++, vo.getCebxlbhb());   
            pstmt.setString(idxparam++, vo.getCebxlbgb());   
            pstmt.setString(idxparam++, vo.getCebxlsju());   
            pstmt.setString(idxparam++, vo.getCebxljgb());  
            pstmt.setString(idxparam++, vo.getCebxlmym());  
            
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ��� : mhcho
    * �ۼ��� : 2006.04.29
    * ��  �� : ���⼼�ݰ�꼭 ��� ������
    * ��  Ÿ : 
    */
   public void updateRowForTX020401_S(ArrayList list, Connection conn) throws Exception 
   {
      LoggablePreparedStatement  pstmt    = null ;
      StringBuffer               sqlBuff  = new StringBuffer(); 
      TBEBXLF1Vo                 vo       = new TBEBXLF1Vo();  
           
      try { 
         sqlBuff.append("UPDATE EVLADM.TBEBXLF1  \n");
         sqlBuff.append("SET                     \n");
         sqlBuff.append(" CEBXLTIS = ?,          \n");  
         sqlBuff.append(" CEBXLTNO = ?           \n");         
         sqlBuff.append("WHERE                   \n"); 
         sqlBuff.append("     CEBXLUP1 = ?       \n"); 
         sqlBuff.append(" AND CEBXLCS1 = ?       \n"); 
         sqlBuff.append(" AND CEBXLYYM = ?       \n");         
         sqlBuff.append(" AND CEBXLMNO = ?       \n"); 
               
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
            
         for (int i = 0; i < list.size(); i++) {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);
            pstmt.setString(idxparam++, vo.getCebxltis());  
            pstmt.setString(idxparam++, vo.getCebxltno());            
            pstmt.setString(idxparam++, vo.getCebxlup1()); 
            pstmt.setString(idxparam++, vo.getCebxlcs1()); 
            pstmt.setString(idxparam++, vo.getCebxlyym());            
            pstmt.setString(idxparam++, vo.getCebxlmno());  
            
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ��� : mhcho
    * �ۼ��� : 2006.04.25
    * ��  �� : ���⼼�ݰ�꼭��� ��ҽ�
    * ��  Ÿ : 
    */
   public void updateRowForTX020401_C(ArrayList list, Connection conn) throws Exception 
   {
      LoggablePreparedStatement  pstmt    = null ;
      StringBuffer               sqlBuff  = new StringBuffer(); 
      TBEBXLF1Vo                 vo       = new TBEBXLF1Vo();  
           
      try { 
         sqlBuff.append("UPDATE EVLADM.TBEBXLF1  \n");
         sqlBuff.append("SET                     \n");
         sqlBuff.append(" CEBXLTIS = '',         \n");  
         sqlBuff.append(" CEBXLTNO = ''          \n");         
         sqlBuff.append("WHERE                   \n"); 
         sqlBuff.append("     CEBXLTNO = ?       \n");         
               
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
            
         for (int i = 0; i < list.size(); i++) {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);            
            pstmt.setString(idxparam++, vo.getCebxltno());             
            
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ��� : mhcho
    * �ۼ��� : 2006.05.01
    * ��  �� : ���ݰ�꼭������ update�Ѵ�.
    * ��  Ÿ : 
    */
   public void updateRowForTX020301(ArrayList list, Connection conn) throws Exception 
   {
      LoggablePreparedStatement  pstmt    = null ;
      StringBuffer               sqlBuff  = new StringBuffer(); 
      TBEBXLF1Vo                 vo       = new TBEBXLF1Vo();  
           
      try { 
         sqlBuff.append("UPDATE EVLADM.TBEBXLF1  \n");
         sqlBuff.append("SET                     \n");
         sqlBuff.append(" CEBXLTIS = '',         \n");  
         sqlBuff.append(" CEBXLTNO = ''          \n");         
         sqlBuff.append("WHERE                   \n"); 
         sqlBuff.append("     CEBXLTNO = ?       \n");         
               
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
            
         for (int i = 0; i < list.size(); i++) {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);            
            pstmt.setString(idxparam++, vo.getCebxltno());             
            
            int result  = pstmt.executeUpdate();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }         
      
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
         try{if (pstmt != null) pstmt.close();}catch(Exception e){}
      }
   }            
   
   /**
    * �ۼ��� : mhcho
    * �ۼ��� : 2006.05.02
    * ��  �� : ���ݰ�꼭���� update�Ѵ�.
    * ��  Ÿ : 
    */
   public void updateRowForTX030101(ArrayList list, Connection conn) throws Exception 
   {
      LoggablePreparedStatement  pstmt    = null ;
      StringBuffer               sqlBuff  = new StringBuffer(); 
      TBEBXLF1Vo                 vo       = new TBEBXLF1Vo();  
           
      try { 
         sqlBuff.append("UPDATE EVLADM.TBEBXLF1  \n");
         sqlBuff.append("SET                     \n");
         sqlBuff.append(" CEBXLTIS = ?           \n");
         sqlBuff.append("WHERE                   \n"); 
         sqlBuff.append("     CEBXLTNO = ?       \n");         
               
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
            
         for (int i = 0; i < list.size(); i++) {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);  
            pstmt.setString(idxparam++, vo.getCebxltis()); 
            pstmt.setString(idxparam++, vo.getCebxltno());             
            
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ��� : 2006.04.28
    * ��  �� : ���� �޼ҵ忡�� �� ����ID�� ���� Insert
    * ��  Ÿ : 
    */
   public void insertRowComMethodForC(ArrayList list, Connection conn) throws Exception
   {
      
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();

      try
      {
          sqlBuff.append("INSERT INTO SAPHEE.ZCST131    \n");
          sqlBuff.append("        (                     \n");
          sqlBuff.append("           MANDT     ,        \n");
          sqlBuff.append("           CS131_UPN ,        \n");
          sqlBuff.append("           CS131_CST ,        \n");
          sqlBuff.append("           CS131_PJT ,        \n");
          sqlBuff.append("           CS131_HNO ,        \n");
          sqlBuff.append("           CS131_SEQ ,        \n");
          sqlBuff.append("           CS131_GND ,        \n");
          sqlBuff.append("           CS131_MYM ,        \n");
          sqlBuff.append("           CS131_ISR ,        \n");
          sqlBuff.append("           CS131_TYP ,        \n");
          sqlBuff.append("           CS131_ARA ,        \n");
          sqlBuff.append("           CS131_BSU ,        \n");
          sqlBuff.append("           CS131_GBU ,        \n");
          sqlBuff.append("           CS131_AGB ,        \n");
          sqlBuff.append("           CS131_ABG ,        \n");
          sqlBuff.append("           CS131_YDT ,        \n");
          sqlBuff.append("           CS131_BGB ,        \n");
          sqlBuff.append("           CS131_BHB ,        \n");
          sqlBuff.append("           CS131_SJU ,        \n");
          sqlBuff.append("           CS131_ZER ,        \n");
          sqlBuff.append("           CS131_FDT ,        \n");
          sqlBuff.append("           CS131_TDT ,        \n");
          sqlBuff.append("           CS131_JDQ ,        \n");
          sqlBuff.append("           CS131_AMD ,        \n");
          sqlBuff.append("           CS131_HMD ,        \n");
          sqlBuff.append("           CS131_DMD ,        \n");
          sqlBuff.append("           CS131_VAD ,        \n");
          sqlBuff.append("           CS131_TOD ,        \n");
          sqlBuff.append("           CS131_AMT ,        \n");
          sqlBuff.append("           CS131_HMT ,        \n");
          sqlBuff.append("           CS131_DMT ,        \n");
          sqlBuff.append("           CS131_VAT ,        \n");
          sqlBuff.append("           CS131_TOT ,        \n");
          sqlBuff.append("           CS131_MYB ,        \n");
          sqlBuff.append("           CS131_MSA ,        \n");
          sqlBuff.append("           CS131_MDT ,        \n");
          sqlBuff.append("           CS131_BGD ,        \n");
          sqlBuff.append("           CS131_JGB ,        \n");
          sqlBuff.append("           CS131_UP1 ,        \n");
          sqlBuff.append("           CS131_CS1 ,        \n");
          sqlBuff.append("           CS131_YYM ,        \n");
          sqlBuff.append("           CS131_CSJ ,        \n");
          sqlBuff.append("           CS131_GNO ,        \n");
          sqlBuff.append("           CS131_IIS ,        \n");
          sqlBuff.append("           CS131_BDGBN,        \n");
          //===========================����û���� �߰� 20200515 Han.JH====================================
          sqlBuff.append("           CS131_ACMD,         \n");
          sqlBuff.append("           CS131_ACMT,         \n");
          //========================================================================================
          //===========================�������� �� �����Ͻ� �߰� 20201119 Han.JH=============================
          sqlBuff.append("           ERDAT,              \n");
          sqlBuff.append("           ERZEIT              \n");
          //=======================================================================================
          sqlBuff.append("        )                     \n");
          sqlBuff.append("VALUES  (                     \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, SAPHEE.GET_LAST_DAY(CAST(? AS VARCHAR(10))), \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?, ?,     \n");
          sqlBuff.append("           ?, ?, ?, ?         \n");
          sqlBuff.append("        )                     \n");
          
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         TBEBXLF1Vo vo;
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);
            
            String isr = getIsrForBXL(vo, conn);
            /*2006.07.31 mhcho �߰�*/
            String bhb = getCebsjbhb(vo.getMandt(),vo.getCebxlupn(),vo.getCebxlcst(),conn);
            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxlupn());
            pstmt.setString(idxparam++, vo.getCebxlcst());
            pstmt.setString(idxparam++, vo.getCebxlpjt());
            pstmt.setString(idxparam++, vo.getCebxlhno());
            pstmt.setString(idxparam++, vo.getCebxlseq());
            pstmt.setString(idxparam++, vo.getCebxlgnd());
            pstmt.setString(idxparam++, vo.getCebxlmym());
            pstmt.setString(idxparam++, isr);             
            pstmt.setString(idxparam++, vo.getCebxltyp());
            pstmt.setString(idxparam++, vo.getCebxlara());
            pstmt.setString(idxparam++, vo.getCebxlbsu());
            pstmt.setString(idxparam++, vo.getCebxlgbu());
            pstmt.setString(idxparam++, vo.getCebxlagb());
            pstmt.setString(idxparam++, vo.getCebxlabg());
            pstmt.setString(idxparam++, vo.getCebxlydt());
            pstmt.setString(idxparam++, vo.getCebxlbgb());
            pstmt.setString(idxparam++, bhb);               
            pstmt.setString(idxparam++, vo.getCebxlsju());
            pstmt.setString(idxparam++, vo.getCebxlzer());
            pstmt.setString(idxparam++, vo.getCebxlfdt());
            pstmt.setString(idxparam++, vo.getCebxltdt());
            pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebxljdq()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxlamt())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxlhmd())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxldmd())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxlvat())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxltot())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxlamd())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxlhmt())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxldmt())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxlvad())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxltod())));
            pstmt.setString(idxparam++, vo.getCebxlmyb());
            pstmt.setString(idxparam++, vo.getCebxlmsa());
            pstmt.setString(idxparam++, vo.getCebxlmdt());
            pstmt.setString(idxparam++, vo.getCebxlbgd());
            pstmt.setString(idxparam++, vo.getCebxljgb());
            pstmt.setString(idxparam++, vo.getCebxlup1());
            pstmt.setString(idxparam++, vo.getCebxlcs1());
            pstmt.setString(idxparam++, vo.getCebxlyym());
            pstmt.setString(idxparam++, " ");
            pstmt.setString(idxparam++, vo.getGno());
            pstmt.setString(idxparam++, CommonUtil.NullToBlank(vo.getCebxliis()));
            pstmt.setString(idxparam++, vo.getCebxlbdgbn());
            
            //===========================����û���� �߰� 20200515 Han.JH==================================
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxlacmd())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(new Double(vo.getCebxlacmt())));
            //======================================================================================
            
            //===========================�������� �� �����Ͻ� �߰� 20201119 Han.JH===========================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//ERDAT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//ERZEIT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H            
            //======================================================================================
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
    * �ۼ��� : jkkoo
    * �ۼ��� : 2006.05.15
    * ��  �� : ���� �޼ҵ忡�� �� ����ID�� ���� Insert
    * ��  Ÿ : �ο����ֿ� ���� ����� ���
    */
   public void insertRowComMethodForETC(ArrayList list, Connection conn) throws Exception
   {
      
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();

      try
      {
          sqlBuff.append("INSERT INTO SAPHEE.ZCST131   \n");
          sqlBuff.append("        (                    \n");
          sqlBuff.append("           MANDT    ,        \n");
          sqlBuff.append("           CS131_UPN ,       \n");
          sqlBuff.append("           CS131_CST ,       \n");
          sqlBuff.append("           CS131_PJT ,       \n");
          sqlBuff.append("           CS131_HNO ,       \n");
          sqlBuff.append("           CS131_SEQ ,       \n");
          sqlBuff.append("           CS131_GND ,       \n");
          sqlBuff.append("           CS131_MYM ,       \n");
          sqlBuff.append("           CS131_ISR ,       \n");
          sqlBuff.append("           CS131_BSU ,       \n");
          sqlBuff.append("           CS131_GBU ,       \n");
          sqlBuff.append("           CS131_YDT ,       \n");
          sqlBuff.append("           CS131_BGB ,       \n");
          sqlBuff.append("           CS131_BHB ,       \n");
          sqlBuff.append("           CS131_SJU ,       \n");
          sqlBuff.append("           CS131_ZER ,       \n");
          sqlBuff.append("           CS131_FDT ,       \n");
          sqlBuff.append("           CS131_TDT ,       \n");
          sqlBuff.append("           CS131_JDQ ,       \n");
          sqlBuff.append("           CS131_AMD ,       \n");
          sqlBuff.append("           CS131_HMD ,       \n");
          sqlBuff.append("           CS131_DMD ,       \n");
          sqlBuff.append("           CS131_VAD ,       \n");
          sqlBuff.append("           CS131_TOD ,       \n");
          sqlBuff.append("           CS131_AMT ,       \n");
          sqlBuff.append("           CS131_HMT ,       \n");
          sqlBuff.append("           CS131_DMT ,       \n");
          sqlBuff.append("           CS131_VAT ,       \n");
          sqlBuff.append("           CS131_TOT ,       \n");
          sqlBuff.append("           CS131_BGD ,       \n");
          sqlBuff.append("           CS131_JGB ,       \n");
          sqlBuff.append("           CS131_UP1 ,       \n");
          sqlBuff.append("           CS131_CS1 ,       \n");
          sqlBuff.append("           CS131_YYM ,       \n");
          sqlBuff.append("           CS131_CSJ ,       \n");
          sqlBuff.append("           CS131_ARA ,       \n");
          sqlBuff.append("           CS131_IIS ,       \n");
          sqlBuff.append("           CS131_GNO ,       \n");
          sqlBuff.append("           CS131_BDGBN,      \n");
          sqlBuff.append("           ERDAT,            \n");
          sqlBuff.append("           ERZEIT            \n");
          sqlBuff.append("        )                    \n");
          sqlBuff.append("VALUES  (                    \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, SAPHEE.GET_LAST_DAY(CAST(? AS VARCHAR(10))), \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?                 \n");
          sqlBuff.append("        )                    \n");
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         TBEBXLF1Vo vo;
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);
            
            String isr = getIsrForBXL(vo, conn);
            TBEBWMF1Vo bwmVo = getBwmForBxl(vo, conn);
            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxlupn());
            pstmt.setString(idxparam++, vo.getCebxlcst());
            pstmt.setString(idxparam++, vo.getCebxlpjt());
            pstmt.setString(idxparam++, vo.getCebxlhno());
            pstmt.setString(idxparam++, vo.getCebxlseq());
            pstmt.setString(idxparam++, vo.getCebxlgnd());
            pstmt.setString(idxparam++, vo.getCebxlmym());
            pstmt.setString(idxparam++, isr);
            pstmt.setString(idxparam++, vo.getCebxlbsu());
            pstmt.setString(idxparam++, vo.getCebxlgbu());
            pstmt.setString(idxparam++, vo.getCebxlydt());
            pstmt.setString(idxparam++, bwmVo.getCebwmbgb());
            pstmt.setString(idxparam++, bwmVo.getCebwmgbn());
            pstmt.setString(idxparam++, CommonUtil.NullToBlank(bwmVo.getCebwmsju()));
            pstmt.setString(idxparam++, CommonUtil.NullToBlank(bwmVo.getCebwmzer()));
            pstmt.setString(idxparam++, vo.getCebxlfdt());
            pstmt.setString(idxparam++, vo.getCebxltdt());
            pstmt.setInt(idxparam++, Integer.parseInt(vo.getCebxljdq()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxlamd()).setScale(3, BigDecimal.ROUND_HALF_UP));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxlhmd()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxldmd()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxlvad()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxltod()).setScale(3, BigDecimal.ROUND_HALF_UP));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxlamt()).setScale(3, BigDecimal.ROUND_HALF_UP));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxlhmt()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxldmt()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxlvat()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxltot()).setScale(3, BigDecimal.ROUND_HALF_UP));
            pstmt.setString(idxparam++, vo.getCebxlbgd());
            pstmt.setString(idxparam++, vo.getCebxljgb());
            pstmt.setString(idxparam++, vo.getCebxlup1());
            pstmt.setString(idxparam++, vo.getCebxlcs1());
            pstmt.setString(idxparam++, vo.getCebxlyym());
            pstmt.setString(idxparam++, vo.getCebxlcsj());
            pstmt.setString(idxparam++, vo.getCebxlara());
            pstmt.setString(idxparam++, CommonUtil.NullToBlank(vo.getCebxliis()));
            pstmt.setString(idxparam++, vo.getGno());
            pstmt.setString(idxparam++, vo.getCebxlbdgbn());
            //===========================�������� �� �����Ͻ� �߰� 20201119 Han.JH===========================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//ERDAT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//ERZEIT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H            
            //======================================================================================            

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
     * �ۼ���: jkkoo
     * �ۼ���: 2006-05-04
     * ��  ��: 
     * ��  Ÿ:
     */
   public static String getIsrForBXL(TBEBXLF1Vo vo, Connection conn) throws Exception
   {
      String bxlIsr = "";

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;

      sqlBuff.append("  SELECT                        \n");
      //sqlBuff.append("     SUBSTR(DIGITS(CAST(COALESCE(MAX(CS131_ISR),'0') AS INTEGER)+1),9,2) AS ISR \n");
      sqlBuff.append("     SUBSTR(DIGITS(MAX(CAST(COALESCE(CS131_ISR,'0') AS INTEGER))+1),9,2) AS ISR \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     SAPHEE.ZCST131             \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("     AND   MANDT     = ?        \n");
//      sqlBuff.append("     AND   CS131_UPN = ?        \n");
//      sqlBuff.append("     AND   CS131_CST = ?        \n");
      sqlBuff.append("     AND   CS131_PJT = ?        \n");
      sqlBuff.append("     AND   CS131_HNO = ?        \n");
      sqlBuff.append("     AND   CS131_SEQ = ?        \n");
      sqlBuff.append("     AND   CS131_GND = ?        \n");
      sqlBuff.append("     AND   CS131_MYM = ?        \n");
      sqlBuff.append("  GROUP BY                      \n");
      sqlBuff.append("     MANDT,                     \n");
//      sqlBuff.append("     CS131_UPN,                 \n");
//      sqlBuff.append("     CS131_CST,                 \n");
      sqlBuff.append("     CS131_PJT,                 \n");
      sqlBuff.append("     CS131_HNO,                 \n");
      sqlBuff.append("     CS131_SEQ,                 \n");
      sqlBuff.append("     CS131_GND,                 \n");
      sqlBuff.append("     CS131_MYM                  \n");
      
      try
      {
//         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         pstmt.setString(1, vo.getMandt());
//         pstmt.setString(2, vo.getCebxlupn());
//         pstmt.setString(3, vo.getCebxlcst());
         pstmt.setString(2, vo.getCebxlpjt());
         pstmt.setString(3, vo.getCebxlhno());
         pstmt.setString(4, vo.getCebxlseq());
         pstmt.setString(5, vo.getCebxlgnd());
         pstmt.setString(6, vo.getCebxlmym());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
            bxlIsr = new Integer(rs.getInt("ISR")).toString();
            if(bxlIsr.length() == 1)
               bxlIsr = "0" + bxlIsr;
         }
         else
            bxlIsr = "01";
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
      return bxlIsr;
   }            
   
   /**
    * �ۼ��� : jkkoo
    * �ۼ��� : 2006.05.02
    * ��  �� : UPDATE
    * ��  Ÿ : 
    */
   public void updateRowForCancelForC(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXLF1Vo vo = new TBEBXLF1Vo();
      try
      {
          sqlBuff.append("UPDATE  SAPHEE.ZCST131       \n");
          sqlBuff.append("SET     CS131_MYB   = 'Y',   \n");
          sqlBuff.append("        CS131_MSA   = '91',  \n");
          sqlBuff.append("        CS131_MDT   = ?,     \n");
          sqlBuff.append("        CS131_NDT   = ?,     \n");
          sqlBuff.append("        CS131_CSJ   = ?,     \n");
          sqlBuff.append("        AEDAT       = ?,     \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
          sqlBuff.append("        AEZEIT      = ?      \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
          sqlBuff.append("WHERE   MANDT       = ?      \n");
          sqlBuff.append("AND     CS131_UPN   = ?      \n");
          sqlBuff.append("AND     CS131_CST   = ?      \n");
          sqlBuff.append("AND     CS131_PJT   = ?      \n");
          sqlBuff.append("AND     CS131_HNO   = ?      \n");
          sqlBuff.append("AND     CS131_SEQ   = ?      \n");
          sqlBuff.append("AND     CS131_GND   = ?      \n");
          sqlBuff.append("AND     CS131_MYM   = ?      \n");
          sqlBuff.append("AND     CS131_ISR   = ?      \n");
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);
            
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, vo.getUserId());
            pstmt.setString(idxparam++, DateTime.getShortDateString());//AEDAT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//AEZEIT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxlupn());
            pstmt.setString(idxparam++, vo.getCebxlcst());
            pstmt.setString(idxparam++, vo.getCebxlpjt());
            pstmt.setString(idxparam++, vo.getCebxlhno());
            pstmt.setString(idxparam++, vo.getCebxlseq());
            pstmt.setString(idxparam++, vo.getCebxlgnd());
            pstmt.setString(idxparam++, vo.getCebxlmym());
            pstmt.setString(idxparam++, vo.getCebxlisr());
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ��� : 2006.05.11
    * ��  �� : ���к���� �����ȹ ���
    * ��  Ÿ : ���к��� ���ν� �������� �ش�� ���� �ܿ��ϱ����� �����ȹ�� ��� ��� ó�� �Ѵ�.
    */
   public void updateRowForCancelForETC1(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXLF1Vo vo = new TBEBXLF1Vo();
      try
      {
         sqlBuff.append("UPDATE  SAPHEE.ZCST131       \n");
         sqlBuff.append("SET     CS131_MYB   = 'Y',   \n");
         sqlBuff.append("        CS131_MSA   = '51',  \n");
         sqlBuff.append("        CS131_MDT   = ?,     \n");
         sqlBuff.append("        CS131_NDT   = ?,     \n");
         sqlBuff.append("        CS131_CSJ   = ?,     \n");
         sqlBuff.append("        AEDAT       = ?,     \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
         sqlBuff.append("        AEZEIT      = ?      \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H         
         sqlBuff.append("WHERE   MANDT       = ?      \n");
         sqlBuff.append("AND     CS131_UPN   = ?      \n");
         sqlBuff.append("AND     CS131_CST   = ?      \n");
         sqlBuff.append("AND     CS131_PJT   = ?      \n");
         sqlBuff.append("AND     CS131_HNO   = ?      \n");
         sqlBuff.append("AND     CS131_SEQ   = ?      \n");
         sqlBuff.append("AND     CS131_GND   = ?      \n");
         sqlBuff.append("AND     CS131_MYM   = ?      \n");
         sqlBuff.append("AND     CS131_ISR   = ?      \n");
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, vo.getUserId());
            
            pstmt.setString(idxparam++, DateTime.getShortDateString());//AEDAT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//AEZEIT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxlupn());
            pstmt.setString(idxparam++, vo.getCebxlcst());
            pstmt.setString(idxparam++, vo.getCebxlpjt());
            pstmt.setString(idxparam++, vo.getCebxlhno());
            pstmt.setString(idxparam++, vo.getCebxlseq());
            pstmt.setString(idxparam++, vo.getCebxlgnd());
            pstmt.setString(idxparam++, vo.getCebxlmym());
            pstmt.setString(idxparam++, vo.getCebxlisr());
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ��� : 2006.05.11
    * ��  �� : �Ͻ��������ν� �����ȹ ���
    * ��  Ÿ : �Ͻ��������ν� �������� �ش�� ���� �ܿ��ϱ����� �����ȹ�� ��� ��� ó�� �Ѵ�.
    */
   public void updateRowForCancelForETC2(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXLF1Vo vo = new TBEBXLF1Vo();
      try
      {
         sqlBuff.append("UPDATE  SAPHEE.ZCST131       \n");
         sqlBuff.append("SET     CS131_MYB   = 'Y',   \n");
         sqlBuff.append("        CS131_MSA   = '52',  \n");
         sqlBuff.append("        CS131_MDT   = ?,     \n");
         sqlBuff.append("        CS131_NDT   = ?,     \n");
         sqlBuff.append("        CS131_CSJ   = ?,     \n");
         sqlBuff.append("        AEDAT       = ?,     \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
         sqlBuff.append("        AEZEIT      = ?      \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H         
         sqlBuff.append("WHERE   MANDT       = ?      \n");
         sqlBuff.append("AND     CS131_UPN   = ?      \n");
         sqlBuff.append("AND     CS131_CST   = ?      \n");
         sqlBuff.append("AND     CS131_PJT   = ?      \n");
         sqlBuff.append("AND     CS131_HNO   = ?      \n");
         sqlBuff.append("AND     CS131_SEQ   = ?      \n");
         sqlBuff.append("AND     CS131_GND   = ?      \n");
         sqlBuff.append("AND     CS131_MYM   = ?      \n");
         sqlBuff.append("AND     CS131_ISR   = ?      \n");
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, vo.getCebxlcsj());
            
            pstmt.setString(idxparam++, DateTime.getShortDateString());//AEDAT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//AEZEIT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H            
            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxlupn());
            pstmt.setString(idxparam++, vo.getCebxlcst());
            pstmt.setString(idxparam++, vo.getCebxlpjt());
            pstmt.setString(idxparam++, vo.getCebxlhno());
            pstmt.setString(idxparam++, vo.getCebxlseq());
            pstmt.setString(idxparam++, vo.getCebxlgnd());
            pstmt.setString(idxparam++, vo.getCebxlmym());
            pstmt.setString(idxparam++, vo.getCebxlisr());
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ��� : 2006.05.11
    * ��  �� : �Ͻ��������ν� �����ȹ ���
    * ��  Ÿ : �Ͻ��������ν� �������� �ش�� ���� �ܿ��ϱ����� �����ȹ�� ��� ��� ó�� �Ѵ�.
    */
   public void updateRowForCancelForETC3(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXLF1Vo vo = new TBEBXLF1Vo();
      try
      {
         sqlBuff.append("UPDATE  SAPHEE.ZCST131       \n");
         sqlBuff.append("SET     CS131_MYB   = '',    \n");
         sqlBuff.append("        CS131_MSA   = '',    \n");
         sqlBuff.append("        CS131_MDT   = '',    \n");
         sqlBuff.append("        CS131_YYM   = ?,     \n");
		 sqlBuff.append("        CS131_YDT   = ?||SUBSTR(CS131_YDT,7),  \n");          
         sqlBuff.append("        CS131_NDT   = ?,     \n");
         sqlBuff.append("        CS131_CSJ   = ?,     \n");
         sqlBuff.append("        AEDAT       = ?,     \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
         sqlBuff.append("        AEZEIT      = ?      \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H         
         sqlBuff.append("WHERE   MANDT       = ?      \n");
         sqlBuff.append("AND     CS131_UPN   = ?      \n");
         sqlBuff.append("AND     CS131_CST   = ?      \n");
         sqlBuff.append("AND     CS131_PJT   = ?      \n");
         sqlBuff.append("AND     CS131_HNO   = ?      \n");
         sqlBuff.append("AND     CS131_SEQ   = ?      \n");
         sqlBuff.append("AND     CS131_GND   = ?      \n");
         sqlBuff.append("AND     CS131_MYM   = ?      \n");
//         sqlBuff.append("AND     CS131_ISR   = ?      \n");
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);
            pstmt.setString(idxparam++, vo.getCebxlyym());
			pstmt.setString(idxparam++, vo.getCebxlyym());            
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, vo.getCebxlcsj());
            pstmt.setString(idxparam++, DateTime.getShortDateString());//AEDAT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//AEZEIT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxlupn());
            pstmt.setString(idxparam++, vo.getCebxlcst());
            pstmt.setString(idxparam++, vo.getCebxlpjt());
            pstmt.setString(idxparam++, vo.getCebxlhno());
            pstmt.setString(idxparam++, vo.getCebxlseq());
            pstmt.setString(idxparam++, vo.getCebxlgnd());
            pstmt.setString(idxparam++, vo.getCebxlmym());
//            pstmt.setString(idxparam++, vo.getCebxlisr());
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ��� : jhlee
    * �ۼ��� : 2008.06.20
    * ��  �� : �ߵ������� �����ȹ ���
    * ��  Ÿ : �ߵ����� ���ν� �������� �ش�� ���� �ܿ��ϱ����� �����ȹ�� ��� ��� ó�� �Ѵ�.
    */
   public void updateRowForCancelForETC4(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXLF1Vo vo = new TBEBXLF1Vo();
      try
      {
         sqlBuff.append("UPDATE  SAPHEE.ZCST131       \n");
         sqlBuff.append("SET     CS131_MYB   = 'Y',   \n");
         sqlBuff.append("        CS131_MSA   = '66',  \n");
         sqlBuff.append("        CS131_MDT   = ?,     \n");
         sqlBuff.append("        CS131_NDT   = ?,     \n");
         sqlBuff.append("        CS131_CSJ   = ?,     \n");
         sqlBuff.append("        AEDAT       = ?,     \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
         sqlBuff.append("        AEZEIT      = ?      \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H         
         sqlBuff.append("WHERE   MANDT       = ?      \n");
         sqlBuff.append("AND     CS131_UPN   = ?      \n");
         sqlBuff.append("AND     CS131_CST   = ?      \n");
         sqlBuff.append("AND     CS131_PJT   = ?      \n");
         sqlBuff.append("AND     CS131_HNO   = ?      \n");
         sqlBuff.append("AND     CS131_SEQ   = ?      \n");
         sqlBuff.append("AND     CS131_GND   = ?      \n");
         sqlBuff.append("AND     CS131_MYM   = ?      \n");
         sqlBuff.append("AND     CS131_ISR   = ?      \n");
         sqlBuff.append("AND     CS131_MYB   = ''     \n");
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, vo.getUserId());
            
            pstmt.setString(idxparam++, DateTime.getShortDateString());//AEDAT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//AEZEIT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxlupn());
            pstmt.setString(idxparam++, vo.getCebxlcst());
            pstmt.setString(idxparam++, vo.getCebxlpjt());
            pstmt.setString(idxparam++, vo.getCebxlhno());
            pstmt.setString(idxparam++, vo.getCebxlseq());
            pstmt.setString(idxparam++, vo.getCebxlgnd());
            pstmt.setString(idxparam++, vo.getCebxlmym());
            pstmt.setString(idxparam++, vo.getCebxlisr());
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ��� : 2006.05.11
    * ��  �� : ��ຯ��� ���� �����ȹ ���
    * ��  Ÿ : ��ຯ�� �������ڷκ��� �ܿ��ϱ����� �����ȹ�� ��� ��� ó�� �Ѵ�.
    */
   public void updateRowForCancelForCHG(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXLF1Vo vo = new TBEBXLF1Vo();
      try
      {
          sqlBuff.append("UPDATE  SAPHEE.ZCST131        \n");
          sqlBuff.append("SET     CS131_MYB    = 'Y',   \n");
          sqlBuff.append("        CS131_MSA    = '33',  \n");
          sqlBuff.append("        CS131_MDT    = ?,     \n");
          sqlBuff.append("        CS131_NDT    = ?,     \n");
          sqlBuff.append("        CS131_CSJ    = ?,     \n");
          sqlBuff.append("        AEDAT        = ?,     \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
          sqlBuff.append("        AEZEIT       = ?      \n"); // BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H          
          sqlBuff.append("WHERE   MANDT        = ?      \n");
          sqlBuff.append("AND     CS131_UPN    = ?      \n");
          sqlBuff.append("AND     CS131_CST    = ?      \n");
          sqlBuff.append("AND     CS131_PJT    = ?      \n");
          sqlBuff.append("AND     CS131_HNO    = ?      \n");
          sqlBuff.append("AND     CS131_SEQ    = ?      \n");
          sqlBuff.append("AND     CS131_GND    = ?      \n");
          sqlBuff.append("AND     CS131_MYM    = ?      \n");
          sqlBuff.append("AND     CS131_ISR    = ?      \n");
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXLF1Vo();
            vo = (TBEBXLF1Vo) list.get(i);

            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            pstmt.setString(idxparam++, vo.getCebxlcsj());
            pstmt.setString(idxparam++, DateTime.getShortDateString());//AEDAT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//AEZEIT. BS ETL ���踦 ���� �ĺ��÷� �߰�. 2020.11.19 Han J.H            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxlupn());
            pstmt.setString(idxparam++, vo.getCebxlcst());
            pstmt.setString(idxparam++, vo.getCebxlpjt());
            pstmt.setString(idxparam++, vo.getCebxlhno());
            pstmt.setString(idxparam++, vo.getCebxlseq());
            pstmt.setString(idxparam++, vo.getCebxlgnd());
            pstmt.setString(idxparam++, vo.getCebxlmym());
            pstmt.setString(idxparam++, vo.getCebxlisr());
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-02 ���� 1:57:45
    * ��  ��: �� ������ �⼺���ȹ�� ������ֱⰣ�� �ߺ��Ǵ� ������翡 ���� �⼺���ȹ�� �����´�
    * ��  Ÿ:
    */
   public ArrayList selectRowComMethodForCancel(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXLF1Vo                 vo       = (TBEBXLF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXLF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     MANDT,                     \n");
      sqlBuff.append("     CS131_UPN,                 \n");
      sqlBuff.append("     CS131_CST,                 \n");
      sqlBuff.append("     CS131_PJT,                 \n");
      sqlBuff.append("     CS131_HNO,                 \n");
      sqlBuff.append("     CS131_SEQ,                 \n");
      sqlBuff.append("     CS131_GND,                 \n");
      sqlBuff.append("     CS131_MYM,                 \n");
      sqlBuff.append("     CS131_ISR,                 \n");
      sqlBuff.append("     CS131_TYP ,                \n");
      sqlBuff.append("     CS131_ARA ,                \n");
      sqlBuff.append("     CS131_BSU ,                \n");
      sqlBuff.append("     CS131_GBU ,                \n");
      sqlBuff.append("     CS131_GBN ,                \n");
      sqlBuff.append("     CS131_AGB ,                \n");
      sqlBuff.append("     CS131_ABG ,                \n");
      sqlBuff.append("     CS131_YDT ,                \n");
      sqlBuff.append("     CS131_BGB ,                \n");
      sqlBuff.append("     CS131_BHB ,                \n");
      sqlBuff.append("     CS131_SJU ,                \n");
      sqlBuff.append("     CS131_ZER ,                \n");
      sqlBuff.append("     CS131_FDT ,                \n");
      sqlBuff.append("     CS131_TDT ,                \n");
      sqlBuff.append("     CS131_JDQ ,                \n");
      sqlBuff.append("     CS131_AMD ,                \n");
      sqlBuff.append("     CS131_HMD ,                \n");
      sqlBuff.append("     CS131_DMD ,                \n");
      sqlBuff.append("     CS131_VAD ,                \n");
      sqlBuff.append("     CS131_TOD ,                \n");
      sqlBuff.append("     CS131_AMT ,                \n");
      sqlBuff.append("     CS131_HMT ,                \n");
      sqlBuff.append("     CS131_DMT ,                \n");
      sqlBuff.append("     CS131_VAT ,                \n");
      sqlBuff.append("     CS131_TOT ,                \n");
      sqlBuff.append("     CS131_MYB ,                \n");
      sqlBuff.append("     CS131_MSA ,                \n");
      sqlBuff.append("     CS131_MDT ,                \n");
      sqlBuff.append("     CS131_BGD ,                \n");
      sqlBuff.append("     CS131_JGB ,                \n");
      sqlBuff.append("     CS131_UP1 ,                \n");
      sqlBuff.append("     CS131_CS1 ,                \n");
      sqlBuff.append("     CS131_YYM ,                \n");
      sqlBuff.append("     CS131_TIS ,                \n");
      sqlBuff.append("     CS131_TNO ,                \n");
      sqlBuff.append("     CS131_NDT ,                \n");
      sqlBuff.append("     CS131_CSJ ,                \n");
      sqlBuff.append("     CS131_GNO ,                \n");
      sqlBuff.append("     CS131_IIS ,                \n");
      sqlBuff.append("     CS131_BDGBN,                \n");
      //===========================����û���� �߰� 20200515 Han.JH====================================
      sqlBuff.append("     CS131_ACMD,                 \n");
      sqlBuff.append("     CS131_ACMT                 \n");
	      //======================================================================================
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     SAPHEE.ZCST131             \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   MANDT = ?               \n");
      if(!"".equals(vo.getCebxlupn()))
         sqlBuff.append("  AND   CS131_UPN = ?           \n");
      if(!"".equals(vo.getCebxlcst()))
         sqlBuff.append("  AND   CS131_CST = ?           \n");
      sqlBuff.append("  AND   CS131_PJT = ?           \n");
      sqlBuff.append("  AND   CS131_HNO = ?           \n");
      sqlBuff.append("  AND   CS131_SEQ = ?           \n");
      sqlBuff.append("  AND   CS131_GND = ?           \n");
      sqlBuff.append("  AND   CS131_MYM >= ?          \n");
      sqlBuff.append("  AND   CS131_MYB = ''          \n");

      try
      {
//         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
    	 conn  = getConnection(db_con);
         //conn  = getConnection();
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         if(!"".equals(vo.getCebxlupn()))
            pstmt.setString(idxparam++, vo.getCebxlupn());
         if(!"".equals(vo.getCebxlcst()))
            pstmt.setString(idxparam++, vo.getCebxlcst());
         pstmt.setString(idxparam++, vo.getCebxlpjt());
         pstmt.setString(idxparam++, vo.getCebxlhno());
         pstmt.setString(idxparam++, vo.getCebxlseq());
         pstmt.setString(idxparam++, vo.getCebxlgnd());
         pstmt.setString(idxparam++, vo.getCebxlmym());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         
         while (rs.next())
         {
            rtnVo = new TBEBXLF1Vo();
            rtnVo.setMandt(rs.getString("MANDT"));
            rtnVo.setCebxlupn(rs.getString("CS131_UPN"));
            rtnVo.setCebxlcst(rs.getString("CS131_CST"));
            rtnVo.setCebxlpjt(rs.getString("CS131_PJT"));
            rtnVo.setCebxlhno(rs.getString("CS131_HNO"));
            rtnVo.setCebxlseq(rs.getString("CS131_SEQ"));
            rtnVo.setCebxlgnd(rs.getString("CS131_GND"));
            rtnVo.setCebxlmym(rs.getString("CS131_MYM"));
            rtnVo.setCebxlisr(rs.getString("CS131_ISR"));
            rtnVo.setCebxltyp(rs.getString("CS131_TYP"));
            rtnVo.setCebxlara(rs.getString("CS131_ARA"));
            rtnVo.setCebxlbsu(rs.getString("CS131_BSU"));
            rtnVo.setCebxlgbu(rs.getString("CS131_GBU"));
            rtnVo.setCebxlgbn(rs.getString("CS131_GBN"));
            rtnVo.setCebxlagb(rs.getString("CS131_AGB"));
            rtnVo.setCebxlabg(rs.getString("CS131_ABG"));
            rtnVo.setCebxlydt(rs.getString("CS131_YDT"));
            rtnVo.setCebxlbgb(rs.getString("CS131_BGB"));
            rtnVo.setCebxlbhb(rs.getString("CS131_BHB"));
            rtnVo.setCebxlsju(rs.getString("CS131_SJU"));
            rtnVo.setCebxlzer(rs.getString("CS131_ZER"));
            rtnVo.setCebxlfdt(rs.getString("CS131_FDT"));
            rtnVo.setCebxltdt(rs.getString("CS131_TDT"));
            rtnVo.setCebxljdq(rs.getString("CS131_JDQ"));
            rtnVo.setCebxlamd(rs.getString("CS131_AMD"));
            rtnVo.setCebxlhmd(rs.getString("CS131_HMD"));
            rtnVo.setCebxldmd(rs.getString("CS131_DMD"));
            rtnVo.setCebxlvad(rs.getString("CS131_VAD"));
            rtnVo.setCebxltod(rs.getString("CS131_TOD"));
            rtnVo.setCebxlamt(rs.getString("CS131_AMT"));
            rtnVo.setCebxlhmt(rs.getString("CS131_HMT"));
            rtnVo.setCebxldmt(rs.getString("CS131_DMT"));
            rtnVo.setCebxlvat(rs.getString("CS131_VAT"));
            rtnVo.setCebxltot(rs.getString("CS131_TOT"));
            rtnVo.setCebxlmyb(rs.getString("CS131_MYB"));
            rtnVo.setCebxlmsa(rs.getString("CS131_MSA"));
            rtnVo.setCebxlmdt(rs.getString("CS131_MDT"));
            rtnVo.setCebxlbgd(rs.getString("CS131_BGD"));
            rtnVo.setCebxljgb(rs.getString("CS131_JGB"));
            rtnVo.setCebxlup1(rs.getString("CS131_UP1"));
            rtnVo.setCebxlcs1(rs.getString("CS131_CS1"));
            rtnVo.setCebxlyym(rs.getString("CS131_YYM"));
            rtnVo.setCebxltis(rs.getString("CS131_TIS"));
            rtnVo.setCebxltno(rs.getString("CS131_TNO"));
            rtnVo.setCebxlndt(rs.getString("CS131_NDT"));
            rtnVo.setCebxlcsj(rs.getString("CS131_CSJ"));
            rtnVo.setCebxliis(rs.getString("CS131_IIS"));
            rtnVo.setCebxlbdgbn(rs.getString("CS131_BDGBN"));
            rtnVo.setGno(rs.getString("CS131_GNO"));
            rtnVo.setUserId(vo.getUserId());
            //===========================����û���� �߰� 20200515 Han.JH====================================
            rtnVo.setCebxlacmd(rs.getString("CS131_ACMD"));
            rtnVo.setCebxlacmt(rs.getString("CS131_ACMT"));
            //======================================================================================
            list.add(rtnVo);         
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
      return list;
   }      
   /**
    * �ۼ���: jhlee
    * �ۼ���: 2009-04-21 ���� 1:57:45
    * ��  ��: ����/������ �� ������ �����ȹ�� ����/�������� ������ ������ �����ȹ�� �����´�
    * ��  Ÿ:
    */
   public ArrayList selectRowComMethodForCancelStop(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXLF1Vo                 vo       = (TBEBXLF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXLF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     MANDT,                     \n");
      sqlBuff.append("     CS131_UPN,                 \n");
      sqlBuff.append("     CS131_CST,                 \n");
      sqlBuff.append("     CS131_PJT,                 \n");
      sqlBuff.append("     CS131_HNO,                 \n");
      sqlBuff.append("     CS131_SEQ,                 \n");
      sqlBuff.append("     CS131_GND,                 \n");
      sqlBuff.append("     CS131_MYM,                 \n");
      sqlBuff.append("     CS131_ISR,                 \n");
      sqlBuff.append("     CS131_TYP ,                \n");
      sqlBuff.append("     CS131_ARA ,                \n");
      sqlBuff.append("     CS131_BSU ,                \n");
      sqlBuff.append("     CS131_GBU ,                \n");
      sqlBuff.append("     CS131_GBN ,                \n");
      sqlBuff.append("     CS131_AGB ,                \n");
      sqlBuff.append("     CS131_ABG ,                \n");
      sqlBuff.append("     CS131_YDT ,                \n");
      sqlBuff.append("     CS131_BGB ,                \n");
      sqlBuff.append("     CS131_BHB ,                \n");
      sqlBuff.append("     CS131_SJU ,                \n");
      sqlBuff.append("     CS131_ZER ,                \n");
      sqlBuff.append("     CS131_FDT ,                \n");
      sqlBuff.append("     CS131_TDT ,                \n");
      sqlBuff.append("     CS131_JDQ ,                \n");
      sqlBuff.append("     CS131_AMD ,                \n");
      sqlBuff.append("     CS131_VAD ,                \n");
      sqlBuff.append("     CS131_TOD ,                \n");
      sqlBuff.append("     CS131_AMT ,                \n");
      sqlBuff.append("     CS131_VAT ,                \n");
      sqlBuff.append("     CS131_TOT ,                \n");
      sqlBuff.append("     CS131_MYB ,                \n");
      sqlBuff.append("     CS131_MSA ,                \n");
      sqlBuff.append("     CS131_MDT ,                \n");
      sqlBuff.append("     CS131_BGD ,                \n");
      sqlBuff.append("     CS131_JGB ,                \n");
      sqlBuff.append("     CS131_UP1 ,                \n");
      sqlBuff.append("     CS131_CS1 ,                \n");
      sqlBuff.append("     CS131_YYM ,                \n");
      sqlBuff.append("     CS131_TIS ,                \n");
      sqlBuff.append("     CS131_TNO ,                \n");
      sqlBuff.append("     CS131_NDT ,                \n");
      sqlBuff.append("     CS131_CSJ ,                \n");
      sqlBuff.append("     CS131_GNO ,                \n");
      sqlBuff.append("     CS131_IIS ,                \n");
      sqlBuff.append("     CS131_BDGBN                \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     SAPHEE.ZCST131             \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   MANDT = ?               \n");
      if(!"".equals(vo.getCebxlupn()))
         sqlBuff.append("  AND   CS131_UPN = ?           \n");
      if(!"".equals(vo.getCebxlcst()))
         sqlBuff.append("  AND   CS131_CST = ?           \n");
      sqlBuff.append("  AND   CS131_PJT = ?           \n");
      sqlBuff.append("  AND   CS131_HNO = ?           \n");
      sqlBuff.append("  AND   CS131_SEQ >= ?          \n");
      sqlBuff.append("  AND   CS131_GND = ?           \n");
      sqlBuff.append("  AND   CS131_MYM >= ?          \n");

      try
      {
//         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
    	 conn  = getConnection(db_con);
         //conn  = getConnection();
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         if(!"".equals(vo.getCebxlupn()))
            pstmt.setString(idxparam++, vo.getCebxlupn());
         if(!"".equals(vo.getCebxlcst()))
            pstmt.setString(idxparam++, vo.getCebxlcst());
         pstmt.setString(idxparam++, vo.getCebxlpjt());
         pstmt.setString(idxparam++, vo.getCebxlhno());
         pstmt.setString(idxparam++, vo.getCebxlseq());
         pstmt.setString(idxparam++, vo.getCebxlgnd());
         pstmt.setString(idxparam++, vo.getCebxlmym());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         
         while (rs.next())
         {
            rtnVo = new TBEBXLF1Vo();
            rtnVo.setMandt(rs.getString("MANDT"));
            rtnVo.setCebxlupn(rs.getString("CS131_UPN"));
            rtnVo.setCebxlcst(rs.getString("CS131_CST"));
            rtnVo.setCebxlpjt(rs.getString("CS131_PJT"));
            rtnVo.setCebxlhno(rs.getString("CS131_HNO"));
            rtnVo.setCebxlseq(rs.getString("CS131_SEQ"));
            rtnVo.setCebxlgnd(rs.getString("CS131_GND"));
            rtnVo.setCebxlmym(rs.getString("CS131_MYM"));
            rtnVo.setCebxlisr(rs.getString("CS131_ISR"));
            rtnVo.setCebxltyp(rs.getString("CS131_TYP"));
            rtnVo.setCebxlara(rs.getString("CS131_ARA"));
            rtnVo.setCebxlbsu(rs.getString("CS131_BSU"));
            rtnVo.setCebxlgbu(rs.getString("CS131_GBU"));
            rtnVo.setCebxlgbn(rs.getString("CS131_GBN"));
            rtnVo.setCebxlagb(rs.getString("CS131_AGB"));
            rtnVo.setCebxlabg(rs.getString("CS131_ABG"));
            rtnVo.setCebxlydt(rs.getString("CS131_YDT"));
            rtnVo.setCebxlbgb(rs.getString("CS131_BGB"));
            rtnVo.setCebxlbhb(rs.getString("CS131_BHB"));
            rtnVo.setCebxlsju(rs.getString("CS131_SJU"));
            rtnVo.setCebxlzer(rs.getString("CS131_ZER"));
            rtnVo.setCebxlfdt(rs.getString("CS131_FDT"));
            rtnVo.setCebxltdt(rs.getString("CS131_TDT"));
            rtnVo.setCebxljdq(rs.getString("CS131_JDQ"));
            rtnVo.setCebxlamd(rs.getString("CS131_AMD"));
            rtnVo.setCebxlvad(rs.getString("CS131_VAD"));
            rtnVo.setCebxltod(rs.getString("CS131_TOD"));
            rtnVo.setCebxlamt(rs.getString("CS131_AMT"));
            rtnVo.setCebxlvat(rs.getString("CS131_VAT"));
            rtnVo.setCebxltot(rs.getString("CS131_TOT"));
            rtnVo.setCebxlmyb(rs.getString("CS131_MYB"));
            rtnVo.setCebxlmsa(rs.getString("CS131_MSA"));
            rtnVo.setCebxlmdt(rs.getString("CS131_MDT"));
            rtnVo.setCebxlbgd(rs.getString("CS131_BGD"));
            rtnVo.setCebxljgb(rs.getString("CS131_JGB"));
            rtnVo.setCebxlup1(rs.getString("CS131_UP1"));
            rtnVo.setCebxlcs1(rs.getString("CS131_CS1"));
            rtnVo.setCebxlyym(rs.getString("CS131_YYM"));
            rtnVo.setCebxltis(rs.getString("CS131_TIS"));
            rtnVo.setCebxltno(rs.getString("CS131_TNO"));
            rtnVo.setCebxlndt(rs.getString("CS131_NDT"));
            rtnVo.setCebxlcsj(rs.getString("CS131_CSJ"));
            rtnVo.setCebxliis(rs.getString("CS131_IIS"));
            rtnVo.setCebxlbdgbn(rs.getString("CS131_BDGBN"));
            rtnVo.setGno(rs.getString("CS131_GNO"));
            rtnVo.setUserId(vo.getUserId());
            list.add(rtnVo);         
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
      return list;
   }
   /**
    * �ۼ���: jhlee
    * �ۼ���: 2008-12-11 
    * ��  ��: ���к���ÿ� �� ������ �����ȹ�� ���к��� �������� ������ ������ �����ȹ�� �����´�(FM��� ����)
    * ��  Ÿ:
    */
   public ArrayList selectRowComMethodForCancelSilPae(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXLF1Vo                 vo       = (TBEBXLF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXLF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     MANDT,                     \n");
      sqlBuff.append("     CS131_UPN,                 \n");
      sqlBuff.append("     CS131_CST,                 \n");
      sqlBuff.append("     CS131_PJT,                 \n");
      sqlBuff.append("     CS131_HNO,                 \n");
      sqlBuff.append("     CS131_SEQ,                 \n");
      sqlBuff.append("     CS131_GND,                 \n");
      sqlBuff.append("     CS131_MYM,                 \n");
      sqlBuff.append("     CS131_ISR,                 \n");
      sqlBuff.append("     CS131_TYP ,                \n");
      sqlBuff.append("     CS131_ARA ,                \n");
      sqlBuff.append("     CS131_BSU ,                \n");
      sqlBuff.append("     CS131_GBU ,                \n");
      sqlBuff.append("     CS131_GBN ,                \n");
      sqlBuff.append("     CS131_AGB ,                \n");
      sqlBuff.append("     CS131_ABG ,                \n");
      sqlBuff.append("     CS131_YDT ,                \n");
      sqlBuff.append("     CS131_BGB ,                \n");
      sqlBuff.append("     CS131_BHB ,                \n");
      sqlBuff.append("     CS131_SJU ,                \n");
      sqlBuff.append("     CS131_ZER ,                \n");
      sqlBuff.append("     CS131_FDT ,                \n");
      sqlBuff.append("     CS131_TDT ,                \n");
      sqlBuff.append("     CS131_JDQ ,                \n");
      sqlBuff.append("     CS131_AMD ,                \n");
      sqlBuff.append("     CS131_VAD ,                \n");
      sqlBuff.append("     CS131_TOD ,                \n");
      sqlBuff.append("     CS131_AMT ,                \n");
      sqlBuff.append("     CS131_VAT ,                \n");
      sqlBuff.append("     CS131_TOT ,                \n");
      sqlBuff.append("     CS131_MYB ,                \n");
      sqlBuff.append("     CS131_MSA ,                \n");
      sqlBuff.append("     CS131_MDT ,                \n");
      sqlBuff.append("     CS131_BGD ,                \n");
      sqlBuff.append("     CS131_JGB ,                \n");
      sqlBuff.append("     CS131_UP1 ,                \n");
      sqlBuff.append("     CS131_CS1 ,                \n");
      sqlBuff.append("     CS131_YYM ,                \n");
      sqlBuff.append("     CS131_TIS ,                \n");
      sqlBuff.append("     CS131_TNO ,                \n");
      sqlBuff.append("     CS131_NDT ,                \n");
      sqlBuff.append("     CS131_CSJ ,                \n");
      sqlBuff.append("     CS131_GNO ,                \n");
      sqlBuff.append("     CS131_BDGBN                \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     SAPHEE.ZCST131             \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   MANDT = ?               \n");
      if(!"".equals(vo.getCebxlupn()))
         sqlBuff.append("  AND   CS131_UPN = ?           \n");
      if(!"".equals(vo.getCebxlcst()))
         sqlBuff.append("  AND   CS131_CST = ?           \n");
      sqlBuff.append("  AND   CS131_PJT = ?           \n");
      sqlBuff.append("  AND   CS131_HNO = ?           \n");
      sqlBuff.append("  AND   CS131_SEQ >= ?          \n"); //FM��൵ ��ü ����� ��ҵǵ��� ���� 20081211 LJH
      sqlBuff.append("  AND   CS131_GND = ?           \n");
      sqlBuff.append("  AND   CS131_MYM >= ?          \n");

      try
      {
//         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
    	 conn  = getConnection(db_con);
         //conn  = getConnection();
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         if(!"".equals(vo.getCebxlupn()))
            pstmt.setString(idxparam++, vo.getCebxlupn());
         if(!"".equals(vo.getCebxlcst()))
            pstmt.setString(idxparam++, vo.getCebxlcst());
         pstmt.setString(idxparam++, vo.getCebxlpjt());
         pstmt.setString(idxparam++, vo.getCebxlhno());
         pstmt.setString(idxparam++, vo.getCebxlseq()); //FM��൵ ��ü ����� ��ҵǵ��� ���� 20081211 LJH
         pstmt.setString(idxparam++, vo.getCebxlgnd());
         pstmt.setString(idxparam++, vo.getCebxlmym());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         
         while (rs.next())
         {
            rtnVo = new TBEBXLF1Vo();
            rtnVo.setMandt(rs.getString("MANDT"));
            rtnVo.setCebxlupn(rs.getString("CS131_UPN"));
            rtnVo.setCebxlcst(rs.getString("CS131_CST"));
            rtnVo.setCebxlpjt(rs.getString("CS131_PJT"));
            rtnVo.setCebxlhno(rs.getString("CS131_HNO"));
            rtnVo.setCebxlseq(rs.getString("CS131_SEQ"));
            rtnVo.setCebxlgnd(rs.getString("CS131_GND"));
            rtnVo.setCebxlmym(rs.getString("CS131_MYM"));
            rtnVo.setCebxlisr(rs.getString("CS131_ISR"));
            rtnVo.setCebxltyp(rs.getString("CS131_TYP"));
            rtnVo.setCebxlara(rs.getString("CS131_ARA"));
            rtnVo.setCebxlbsu(rs.getString("CS131_BSU"));
            rtnVo.setCebxlgbu(rs.getString("CS131_GBU"));
            rtnVo.setCebxlgbn(rs.getString("CS131_GBN"));
            rtnVo.setCebxlagb(rs.getString("CS131_AGB"));
            rtnVo.setCebxlabg(rs.getString("CS131_ABG"));
            rtnVo.setCebxlydt(rs.getString("CS131_YDT"));
            rtnVo.setCebxlbgb(rs.getString("CS131_BGB"));
            rtnVo.setCebxlbhb(rs.getString("CS131_BHB"));
            rtnVo.setCebxlsju(rs.getString("CS131_SJU"));
            rtnVo.setCebxlzer(rs.getString("CS131_ZER"));
            rtnVo.setCebxlfdt(rs.getString("CS131_FDT"));
            rtnVo.setCebxltdt(rs.getString("CS131_TDT"));
            rtnVo.setCebxljdq(rs.getString("CS131_JDQ"));
            rtnVo.setCebxlamd(rs.getString("CS131_AMD"));
            rtnVo.setCebxlvad(rs.getString("CS131_VAD"));
            rtnVo.setCebxltod(rs.getString("CS131_TOD"));
            rtnVo.setCebxlamt(rs.getString("CS131_AMT"));
            rtnVo.setCebxlvat(rs.getString("CS131_VAT"));
            rtnVo.setCebxltot(rs.getString("CS131_TOT"));
            rtnVo.setCebxlmyb(rs.getString("CS131_MYB"));
            rtnVo.setCebxlmsa(rs.getString("CS131_MSA"));
            rtnVo.setCebxlmdt(rs.getString("CS131_MDT"));
            rtnVo.setCebxlbgd(rs.getString("CS131_BGD"));
            rtnVo.setCebxljgb(rs.getString("CS131_JGB"));
            rtnVo.setCebxlup1(rs.getString("CS131_UP1"));
            rtnVo.setCebxlcs1(rs.getString("CS131_CS1"));
            rtnVo.setCebxlyym(rs.getString("CS131_YYM"));
            rtnVo.setCebxltis(rs.getString("CS131_TIS"));
            rtnVo.setCebxltno(rs.getString("CS131_TNO"));
            rtnVo.setCebxlndt(rs.getString("CS131_NDT"));
            rtnVo.setCebxlcsj(rs.getString("CS131_CSJ"));
            rtnVo.setCebxlbdgbn(rs.getString("CS131_BDGBN"));
            rtnVo.setGno(rs.getString("CS131_GNO"));
            rtnVo.setUserId(vo.getUserId());
            list.add(rtnVo);         
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
      return list;
   }      
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-02 ���� 1:57:45
    * ��  ��: �� ������ �⼺���ȹ�� ������ֱⰣ�� �ߺ��Ǵ� ������翡 ���� �⼺���ȹ�� �����´�
    * ��  Ÿ:
    */
   public ArrayList selectRowComMethodForCancelPre(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXLF1Vo                 vo       = (TBEBXLF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXLF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     MANDT,                     \n");
      sqlBuff.append("     CS131_UPN,                 \n");
      sqlBuff.append("     CS131_CST,                 \n");
      sqlBuff.append("     CS131_PJT,                 \n");
      sqlBuff.append("     CS131_HNO,                 \n");
      sqlBuff.append("     CS131_SEQ,                 \n");
      sqlBuff.append("     CS131_GND,                 \n");
      sqlBuff.append("     CS131_MYM,                 \n");
      sqlBuff.append("     CS131_ISR,                 \n");
      sqlBuff.append("     CS131_TYP,                 \n");
      sqlBuff.append("     CS131_ARA,                 \n");
      sqlBuff.append("     CS131_BSU,                 \n");
      sqlBuff.append("     CS131_GBU,                 \n");
      sqlBuff.append("     CS131_GBN,                 \n");
      sqlBuff.append("     CS131_AGB,                 \n");
      sqlBuff.append("     CS131_ABG,                 \n");
      sqlBuff.append("     CS131_YDT,                 \n");
      sqlBuff.append("     CS131_BGB,                 \n");
      sqlBuff.append("     CS131_BHB,                 \n");
      sqlBuff.append("     CS131_SJU,                 \n");
      sqlBuff.append("     CS131_ZER,                 \n");
      sqlBuff.append("     CS131_FDT,                 \n");
      sqlBuff.append("     CS131_TDT,                 \n");
      sqlBuff.append("     CS131_JDQ,                 \n");
      sqlBuff.append("     CS131_AMD,                 \n");
      sqlBuff.append("     CS131_VAD,                 \n");
      sqlBuff.append("     CS131_TOD,                 \n");
      sqlBuff.append("     CS131_AMT,                 \n");
      sqlBuff.append("     CS131_VAT,                 \n");
      sqlBuff.append("     CS131_TOT,                 \n");
      sqlBuff.append("     CS131_MYB,                 \n");
      sqlBuff.append("     CS131_MSA,                 \n");
      sqlBuff.append("     CS131_MDT,                 \n");
      sqlBuff.append("     CS131_BGD,                 \n");
      sqlBuff.append("     CS131_JGB,                 \n");
      sqlBuff.append("     CS131_UP1,                 \n");
      sqlBuff.append("     CS131_CS1,                 \n");
      sqlBuff.append("     CS131_YYM,                 \n");
      sqlBuff.append("     CS131_TIS,                 \n");
      sqlBuff.append("     CS131_TNO,                 \n");
      sqlBuff.append("     CS131_NDT,                 \n");
      sqlBuff.append("     CS131_CSJ,                 \n");
      sqlBuff.append("     CS131_BDGBN                \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     SAPHEE.ZCST131             \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   MANDT     = ?           \n");
      sqlBuff.append("  AND   CS131_UPN = ?           \n");
      sqlBuff.append("  AND   CS131_CST = ?           \n");
      sqlBuff.append("  AND   CS131_PJT = ?           \n");
      sqlBuff.append("  AND   CS131_HNO = ?           \n");
      sqlBuff.append("  AND   CS131_SEQ = ?           \n");
      sqlBuff.append("  AND   CS131_GND = ?           \n");
      sqlBuff.append("  AND   CS131_MYM < ?           \n");
      
      try
      {
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
 	     conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxlupn());
         pstmt.setString(idxparam++, vo.getCebxlcst());
         pstmt.setString(idxparam++, vo.getCebxlpjt());
         pstmt.setString(idxparam++, vo.getCebxlhno());
         pstmt.setString(idxparam++, vo.getCebxlseq());
         pstmt.setString(idxparam++, vo.getCebxlgnd());
         pstmt.setString(idxparam++, vo.getCebxlmym());

         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         while (rs.next())
         {
            rtnVo = new TBEBXLF1Vo();
            rtnVo.setMandt(rs.getString("MANDT"));
            rtnVo.setCebxlupn(rs.getString("CS131_UPN"));
            rtnVo.setCebxlcst(rs.getString("CS131_CST"));
            rtnVo.setCebxlpjt(rs.getString("CS131_PJT"));
            rtnVo.setCebxlhno(rs.getString("CS131_HNO"));
            rtnVo.setCebxlseq(rs.getString("CS131_SEQ"));
            rtnVo.setCebxlgnd(rs.getString("CS131_GND"));
            rtnVo.setCebxlmym(rs.getString("CS131_MYM"));
            rtnVo.setCebxlisr(rs.getString("CS131_ISR"));
            rtnVo.setCebxltyp(rs.getString("CS131_TYP"));
            rtnVo.setCebxlara(rs.getString("CS131_ARA"));
            rtnVo.setCebxlbsu(rs.getString("CS131_BSU"));
            rtnVo.setCebxlgbu(rs.getString("CS131_GBU"));
            rtnVo.setCebxlgbn(rs.getString("CS131_GBN"));
            rtnVo.setCebxlagb(rs.getString("CS131_AGB"));
            rtnVo.setCebxlabg(rs.getString("CS131_ABG"));
            rtnVo.setCebxlydt(rs.getString("CS131_YDT"));
            rtnVo.setCebxlbgb(rs.getString("CS131_BGB"));
            rtnVo.setCebxlbhb(rs.getString("CS131_BHB"));
            rtnVo.setCebxlsju(rs.getString("CS131_SJU"));
            rtnVo.setCebxlzer(rs.getString("CS131_ZER"));
            rtnVo.setCebxlfdt(rs.getString("CS131_FDT"));
            rtnVo.setCebxltdt(rs.getString("CS131_TDT"));
            rtnVo.setCebxljdq(rs.getString("CS131_JDQ"));
            rtnVo.setCebxlamd(rs.getString("CS131_AMD"));
            rtnVo.setCebxlvad(rs.getString("CS131_VAD"));
            rtnVo.setCebxltod(rs.getString("CS131_TOD"));
            rtnVo.setCebxlamt(rs.getString("CS131_AMT"));
            rtnVo.setCebxlvat(rs.getString("CS131_VAT"));
            rtnVo.setCebxltot(rs.getString("CS131_TOT"));
            rtnVo.setCebxlmyb(rs.getString("CS131_MYB"));
            rtnVo.setCebxlmsa(rs.getString("CS131_MSA"));
            rtnVo.setCebxlmdt(rs.getString("CS131_MDT"));
            rtnVo.setCebxlbgd(rs.getString("CS131_BGD"));
            rtnVo.setCebxljgb(rs.getString("CS131_JGB"));
            rtnVo.setCebxlup1(rs.getString("CS131_UP1"));
            rtnVo.setCebxlcs1(rs.getString("CS131_CS1"));
            rtnVo.setCebxlyym(rs.getString("CS131_YYM"));
            rtnVo.setCebxltis(rs.getString("CS131_TIS"));
            rtnVo.setCebxltno(rs.getString("CS131_TNO"));
            rtnVo.setCebxlndt(rs.getString("CS131_NDT"));
            rtnVo.setCebxlcsj(rs.getString("CS131_CSJ"));
            rtnVo.setCebxlbdgbn(rs.getString("CS131_BDGBN"));
            list.add(rtnVo);         
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
      return list;
   }      
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-02 ���� 1:57:45
    * ��  ��: �� ������ �⼺���ȹ�� ������ֱⰣ�� �ߺ��Ǵ� ������翡 ���� �⼺���ȹ�� �����´�
    * ��  Ÿ:
    */
   public ArrayList selectRowComMethodForRestore(ArrayList paramList, Connection conn) throws Exception
   {
      TBEBXLF1Vo                 vo       = (TBEBXLF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXLF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append(" SELECT                         \n");
      sqlBuff.append("        MANDT,                  \n");
      sqlBuff.append("        CS131_UPN,              \n");
      sqlBuff.append("        CS131_CST,              \n");
      sqlBuff.append("        CS131_PJT,              \n");
      sqlBuff.append("        CS131_HNO,              \n");
      sqlBuff.append("        CS131_SEQ,              \n");
      sqlBuff.append("        CS131_GND,              \n");
      sqlBuff.append("        CS131_MYM,              \n");
      sqlBuff.append("        CS131_ISR,              \n");
      sqlBuff.append("        CS131_TYP ,             \n");
      sqlBuff.append("        CS131_ARA ,             \n");
      sqlBuff.append("        CS131_AGB ,             \n");
      sqlBuff.append("        CS131_ABG ,             \n");
      sqlBuff.append("        CS131_BSU ,             \n");
      sqlBuff.append("        CS131_GBU ,             \n");
      sqlBuff.append("        CS131_GBN ,             \n");
      sqlBuff.append("        CS131_YDT ,             \n");
      sqlBuff.append("        CS131_BGB ,             \n");
      sqlBuff.append("        CS131_BHB ,             \n");
      sqlBuff.append("        CS131_SJU ,             \n");
      sqlBuff.append("        CS131_ZER ,             \n");
      sqlBuff.append("        CS131_FDT ,             \n");
      sqlBuff.append("        CS131_TDT ,             \n");
      sqlBuff.append("        CS131_JDQ ,             \n");
      sqlBuff.append("        CS131_AMD ,             \n");
      sqlBuff.append("        CS131_VAD ,             \n");
      sqlBuff.append("        CS131_TOD ,             \n");
      sqlBuff.append("        CS131_AMT ,             \n");
      sqlBuff.append("        CS131_VAT ,             \n");
      sqlBuff.append("        CS131_TOT ,             \n");
      sqlBuff.append("        CS131_MYB ,             \n");
      sqlBuff.append("        CS131_MSA ,             \n");
      sqlBuff.append("        CS131_MDT ,             \n");
      sqlBuff.append("        CS131_BGD ,             \n");
      sqlBuff.append("        CS131_JGB ,             \n");
      sqlBuff.append("        CS131_UP1 ,             \n");
      sqlBuff.append("        CS131_CS1 ,             \n");
      sqlBuff.append("        CS131_YYM ,             \n");
      sqlBuff.append("        CS131_TIS ,             \n");
      sqlBuff.append("        CS131_TNO ,             \n");
      sqlBuff.append("        CS131_NDT ,             \n");
      sqlBuff.append("        CS131_CSJ ,             \n");
      sqlBuff.append("        CS131_BDGBN             \n");
      sqlBuff.append("   FROM                         \n");
      sqlBuff.append("        SAPHEE.ZCST131          \n");
      sqlBuff.append("  WHERE 1=1                     \n");
      sqlBuff.append("  AND   MANDT     = ?           \n");
      sqlBuff.append("  AND   CS131_UPN = ?           \n");
      sqlBuff.append("  AND   CS131_CST = ?           \n");
      sqlBuff.append("  AND   CS131_PJT = ?           \n");
      sqlBuff.append("  AND   CS131_HNO = ?           \n");
      sqlBuff.append("  AND   CS131_SEQ = ?           \n");
      sqlBuff.append("  AND   CS131_GND = ?           \n");
      sqlBuff.append("  AND   CS131_MYM >= ?          \n");
      sqlBuff.append("  AND   CS131_MYB = 'Y'         \n");
      sqlBuff.append("  AND   CS131_MSA = '52'        \n");
      
      try
      {
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
  	     conn  = getConnection(db_con);
  	     
    	 pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxlupn());
         pstmt.setString(idxparam++, vo.getCebxlcst());
         pstmt.setString(idxparam++, vo.getCebxlpjt());
         pstmt.setString(idxparam++, vo.getCebxlhno());
         pstmt.setString(idxparam++, vo.getCebxlseq());
         pstmt.setString(idxparam++, vo.getCebxlgnd());
         pstmt.setString(idxparam++, vo.getCebxlmym());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         
         while (rs.next())
         {
            rtnVo = new TBEBXLF1Vo();
            rtnVo.setMandt(rs.getString("MANDT"));
            rtnVo.setCebxlupn(rs.getString("CS131_UPN"));
            rtnVo.setCebxlcst(rs.getString("CS131_CST"));
            rtnVo.setCebxlpjt(rs.getString("CS131_PJT"));
            rtnVo.setCebxlhno(rs.getString("CS131_HNO"));
            rtnVo.setCebxlseq(rs.getString("CS131_SEQ"));
            rtnVo.setCebxlgnd(rs.getString("CS131_GND"));
            rtnVo.setCebxlmym(rs.getString("CS131_MYM"));
            rtnVo.setCebxlisr(rs.getString("CS131_ISR"));
            rtnVo.setCebxltyp(rs.getString("CS131_TYP"));
            rtnVo.setCebxlara(rs.getString("CS131_ARA"));
            rtnVo.setCebxlagb(rs.getString("CS131_AGB"));
            rtnVo.setCebxlabg(rs.getString("CS131_ABG"));
            rtnVo.setCebxlbsu(rs.getString("CS131_BSU"));
            rtnVo.setCebxlgbu(rs.getString("CS131_GBU"));
            rtnVo.setCebxlgbn(rs.getString("CS131_GBN"));
            rtnVo.setCebxlydt(rs.getString("CS131_YDT"));
            rtnVo.setCebxlbgb(rs.getString("CS131_BGB"));
            rtnVo.setCebxlbhb(rs.getString("CS131_BHB"));
            rtnVo.setCebxlsju(rs.getString("CS131_SJU"));
            rtnVo.setCebxlzer(rs.getString("CS131_ZER"));
            rtnVo.setCebxlfdt(rs.getString("CS131_FDT"));
            rtnVo.setCebxltdt(rs.getString("CS131_TDT"));
            rtnVo.setCebxljdq(rs.getString("CS131_JDQ"));
            rtnVo.setCebxlamd(rs.getString("CS131_AMD"));
            rtnVo.setCebxlvad(rs.getString("CS131_VAD"));
            rtnVo.setCebxltod(rs.getString("CS131_TOD"));
            rtnVo.setCebxlamt(rs.getString("CS131_AMT"));
            rtnVo.setCebxlvat(rs.getString("CS131_VAT"));
            rtnVo.setCebxltot(rs.getString("CS131_TOT"));
            rtnVo.setCebxlmyb(rs.getString("CS131_MYB"));
            rtnVo.setCebxlmsa(rs.getString("CS131_MSA"));
            rtnVo.setCebxlmdt(rs.getString("CS131_MDT"));
            rtnVo.setCebxlbgd(rs.getString("CS131_BGD"));
            rtnVo.setCebxljgb(rs.getString("CS131_JGB"));
            rtnVo.setCebxlup1(rs.getString("CS131_UP1"));
            rtnVo.setCebxlcs1(rs.getString("CS131_CS1"));
            rtnVo.setCebxlyym(rs.getString("CS131_YYM"));
            rtnVo.setCebxltis(rs.getString("CS131_TIS"));
            rtnVo.setCebxltno(rs.getString("CS131_TNO"));
            rtnVo.setCebxlndt(rs.getString("CS131_NDT"));
            rtnVo.setCebxlcsj(rs.getString("CS131_CSJ"));
            rtnVo.setCebxlbdgbn(rs.getString("CS131_BDGBN"));

            list.add(rtnVo);         
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
      return list;
   }
   /**
    * �ۼ���: jhlee
    * �ۼ���: 2009-04-21 ���� 1:57:45
    * ��  ��: �Ͻ����� ���� ���� ��Ͻ� ������ �����͸� �����´�.
    * ��  Ÿ:
    */
   public ArrayList selectRowComMethodForRestoreStop(ArrayList paramList, Connection conn) throws Exception
   {
      TBEBXLF1Vo                 vo       = (TBEBXLF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXLF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append(" SELECT                         \n");
      sqlBuff.append("        MANDT,                  \n");
      sqlBuff.append("        CS131_UPN,              \n");
      sqlBuff.append("        CS131_CST,              \n");
      sqlBuff.append("        CS131_PJT,              \n");
      sqlBuff.append("        CS131_HNO,              \n");
      sqlBuff.append("        CS131_SEQ,              \n");
      sqlBuff.append("        CS131_GND,              \n");
      sqlBuff.append("        CS131_MYM,              \n");
      sqlBuff.append("        CS131_ISR,              \n");
      sqlBuff.append("        CS131_TYP ,             \n");
      sqlBuff.append("        CS131_ARA ,             \n");
      sqlBuff.append("        CS131_AGB ,             \n");
      sqlBuff.append("        CS131_ABG ,             \n");
      sqlBuff.append("        CS131_BSU ,             \n");
      sqlBuff.append("        CS131_GBU ,             \n");
      sqlBuff.append("        CS131_GBN ,             \n");
      sqlBuff.append("        CS131_YDT ,             \n");
      sqlBuff.append("        CS131_BGB ,             \n");
      sqlBuff.append("        CS131_BHB ,             \n");
      sqlBuff.append("        CS131_SJU ,             \n");
      sqlBuff.append("        CS131_ZER ,             \n");
      sqlBuff.append("        CS131_FDT ,             \n");
      sqlBuff.append("        CS131_TDT ,             \n");
      sqlBuff.append("        CS131_JDQ ,             \n");
      sqlBuff.append("        CS131_AMD ,             \n");
      sqlBuff.append("        CS131_VAD ,             \n");
      sqlBuff.append("        CS131_TOD ,             \n");
      sqlBuff.append("        CS131_AMT ,             \n");
      sqlBuff.append("        CS131_VAT ,             \n");
      sqlBuff.append("        CS131_TOT ,             \n");
      sqlBuff.append("        CS131_MYB ,             \n");
      sqlBuff.append("        CS131_MSA ,             \n");
      sqlBuff.append("        CS131_MDT ,             \n");
      sqlBuff.append("        CS131_BGD ,             \n");
      sqlBuff.append("        CS131_JGB ,             \n");
      sqlBuff.append("        CS131_UP1 ,             \n");
      sqlBuff.append("        CS131_CS1 ,             \n");
      sqlBuff.append("        CS131_YYM ,             \n");
      sqlBuff.append("        CS131_TIS ,             \n");
      sqlBuff.append("        CS131_TNO ,             \n");
      sqlBuff.append("        CS131_NDT ,             \n");
      sqlBuff.append("        CS131_CSJ ,             \n");
      sqlBuff.append("        CS131_BDGBN             \n");
      sqlBuff.append("   FROM                         \n");
      sqlBuff.append("        SAPHEE.ZCST131          \n");
      sqlBuff.append("  WHERE 1=1                     \n");
      sqlBuff.append("  AND   MANDT     = ?           \n");
      sqlBuff.append("  AND   CS131_UPN = ?           \n");
      sqlBuff.append("  AND   CS131_CST = ?           \n");
      sqlBuff.append("  AND   CS131_PJT = ?           \n");
      sqlBuff.append("  AND   CS131_HNO = ?           \n");
      sqlBuff.append("  AND   CS131_SEQ >= ?          \n");
      sqlBuff.append("  AND   CS131_GND = ?           \n");
      sqlBuff.append("  AND   CS131_MYM >= ?          \n");
      sqlBuff.append("  AND   CS131_MYB = 'Y'         \n");
      sqlBuff.append("  AND   CS131_MSA = '52'        \n");
      
      try
      {
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
  	     conn  = getConnection(db_con);
  	     
    	 pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxlupn());
         pstmt.setString(idxparam++, vo.getCebxlcst());
         pstmt.setString(idxparam++, vo.getCebxlpjt());
         pstmt.setString(idxparam++, vo.getCebxlhno());
         pstmt.setString(idxparam++, vo.getCebxlseq());
         pstmt.setString(idxparam++, vo.getCebxlgnd());
         pstmt.setString(idxparam++, vo.getCebxlmym());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         
         while (rs.next())
         {
            rtnVo = new TBEBXLF1Vo();
            rtnVo.setMandt(rs.getString("MANDT"));
            rtnVo.setCebxlupn(rs.getString("CS131_UPN"));
            rtnVo.setCebxlcst(rs.getString("CS131_CST"));
            rtnVo.setCebxlpjt(rs.getString("CS131_PJT"));
            rtnVo.setCebxlhno(rs.getString("CS131_HNO"));
            rtnVo.setCebxlseq(rs.getString("CS131_SEQ"));
            rtnVo.setCebxlgnd(rs.getString("CS131_GND"));
            rtnVo.setCebxlmym(rs.getString("CS131_MYM"));
            rtnVo.setCebxlisr(rs.getString("CS131_ISR"));
            rtnVo.setCebxltyp(rs.getString("CS131_TYP"));
            rtnVo.setCebxlara(rs.getString("CS131_ARA"));
            rtnVo.setCebxlagb(rs.getString("CS131_AGB"));
            rtnVo.setCebxlabg(rs.getString("CS131_ABG"));
            rtnVo.setCebxlbsu(rs.getString("CS131_BSU"));
            rtnVo.setCebxlgbu(rs.getString("CS131_GBU"));
            rtnVo.setCebxlgbn(rs.getString("CS131_GBN"));
            rtnVo.setCebxlydt(rs.getString("CS131_YDT"));
            rtnVo.setCebxlbgb(rs.getString("CS131_BGB"));
            rtnVo.setCebxlbhb(rs.getString("CS131_BHB"));
            rtnVo.setCebxlsju(rs.getString("CS131_SJU"));
            rtnVo.setCebxlzer(rs.getString("CS131_ZER"));
            rtnVo.setCebxlfdt(rs.getString("CS131_FDT"));
            rtnVo.setCebxltdt(rs.getString("CS131_TDT"));
            rtnVo.setCebxljdq(rs.getString("CS131_JDQ"));
            rtnVo.setCebxlamd(rs.getString("CS131_AMD"));
            rtnVo.setCebxlvad(rs.getString("CS131_VAD"));
            rtnVo.setCebxltod(rs.getString("CS131_TOD"));
            rtnVo.setCebxlamt(rs.getString("CS131_AMT"));
            rtnVo.setCebxlvat(rs.getString("CS131_VAT"));
            rtnVo.setCebxltot(rs.getString("CS131_TOT"));
            rtnVo.setCebxlmyb(rs.getString("CS131_MYB"));
            rtnVo.setCebxlmsa(rs.getString("CS131_MSA"));
            rtnVo.setCebxlmdt(rs.getString("CS131_MDT"));
            rtnVo.setCebxlbgd(rs.getString("CS131_BGD"));
            rtnVo.setCebxljgb(rs.getString("CS131_JGB"));
            rtnVo.setCebxlup1(rs.getString("CS131_UP1"));
            rtnVo.setCebxlcs1(rs.getString("CS131_CS1"));
            rtnVo.setCebxlyym(rs.getString("CS131_YYM"));
            rtnVo.setCebxltis(rs.getString("CS131_TIS"));
            rtnVo.setCebxltno(rs.getString("CS131_TNO"));
            rtnVo.setCebxlndt(rs.getString("CS131_NDT"));
            rtnVo.setCebxlcsj(rs.getString("CS131_CSJ"));
            rtnVo.setCebxlbdgbn(rs.getString("CS131_BDGBN"));

            list.add(rtnVo);         
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
      return list;
   }
   /**
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-14 
    * ��  ��: ����������Ʈ+�ŷ�ó+������ Key�� �������������� ���ݰ�꼭���������� �����´�
    * ��  Ÿ:
    */
   public TBEBWMF1Vo getBwmForBxl(TBEBXLF1Vo vo, Connection conn) throws Exception
   {
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBWMF1Vo                 rtnVo;

      //2006.09.25  ���� ���� by ohb
	  sqlBuff.append(" SELECT                         \n");
	  sqlBuff.append("      CS126_BGB,                \n");
	  sqlBuff.append("      CS121_BHB,                \n");
	  sqlBuff.append("      CS121_SJU,                \n");
	  sqlBuff.append("      CS126_ZER                 \n");
	  sqlBuff.append("   FROM                         \n");
	  sqlBuff.append("      SAPHEE.ZCST126 A,         \n");
	  sqlBuff.append("      SAPHEE.ZCST121 B          \n");
	  sqlBuff.append("   WHERE    1=1                 \n");
	  sqlBuff.append("   AND   A.MANDT   = ?          \n");
	  sqlBuff.append("   AND   CS126_UPN = ?          \n");
	  sqlBuff.append("   AND   CS126_CST = ?          \n");
	  sqlBuff.append("   AND   A.MANDT = B.MANDT      \n");
	  sqlBuff.append("   AND   CS126_UPN = CS121_UPN  \n");
	  sqlBuff.append("   AND   CS126_CST = CS121_CST  \n");
/*   2006.11.01 �ӽ÷� �ּ�ó�� by ohb 	  */
//	  sqlBuff.append("   AND   CS126_UGS <= ?         \n");
//	  sqlBuff.append("   AND   CS126_UMR >= ?         \n");
/**/
      try
      {
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
 	     conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxlupn());
         pstmt.setString(idxparam++, vo.getCebxlcst());
/*  2006.11.01 �ӽ÷� �ּ�ó�� by ohb  */
//         pstmt.setString(idxparam++, vo.getCebxlydt());
//		 pstmt.setString(idxparam++, vo.getCebxlydt());
/**/
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();

         if (rs.next())
         {
            rtnVo = new TBEBWMF1Vo();
            rtnVo.setCebwmbgb(rs.getString("CS126_BGB"));
            rtnVo.setCebwmgbn(rs.getString("CS121_BHB"));
            rtnVo.setCebwmsju(rs.getString("CS121_SJU"));
            rtnVo.setCebwmzer(rs.getString("CS126_ZER"));     
         }
         else
         {
            rtnVo = new TBEBWMF1Vo();
            rtnVo.setCebwmbgb("");
            rtnVo.setCebwmgbn("");
            rtnVo.setCebwmbjg("");
            rtnVo.setCebwmtis("");  
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
    * �ۼ���: mhcho
    * �ۼ���: 2006-07-31 
    * ��  ��: ����������Ʈ+�ŷ�ó Key�� ���౸�������� �����´�
    * ��  Ÿ:
    */
   public String getCebsjbhb(String mandt, String upn, String cst, Connection conn) throws Exception
   {
      
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      String                     bhb      = "";
      
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     CS121_BHB                  \n"); 
      sqlBuff.append("  FROM                          \n");      
      sqlBuff.append("     SAPHEE.ZCST121             \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   MANDT     = ?           \n");
      sqlBuff.append("  AND   CS121_UPN = ?           \n");
      sqlBuff.append("  AND   CS121_CST = ?           \n");      

      try
      {
//         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, mandt);
         pstmt.setString(idxparam++, upn);
         pstmt.setString(idxparam++, cst);

         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();

         if (rs.next())
         {
            bhb = rs.getString("CS121_BHB");     
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
      return bhb;
   }    
   
   /**
	* �ۼ��� : jhlee
	* �ۼ��� : 2006.05.24
	* ��  �� : �����ȹ �� �⼺���ȹ ����
	* ��  Ÿ : 
   */
   public void updateRowForMC010101(ArrayList list, Connection conn) throws Exception {
	  LoggablePreparedStatement pstmt1   = null ;
	  LoggablePreparedStatement pstmt2   = null ;
	  StringBuffer              sqlBuff1 = new StringBuffer(); 
	  StringBuffer              sqlBuff2 = new StringBuffer();
	  TBEBXLF1Vo                vo       = new TBEBXLF1Vo();  
           
	  try { 
		sqlBuff1.append("UPDATE                      \n");
		sqlBuff1.append("       EVLADM.TBEBXLF1      \n");
		sqlBuff1.append("   SET                      \n");  
		sqlBuff1.append("       CEBXLYDT = ?,        \n");
		sqlBuff1.append("       CEBXLYYM = ?         \n"); 
		sqlBuff1.append(" WHERE                      \n"); 
		sqlBuff1.append("       RTRIM(CEBXLMYB) = '' \n"); 
		sqlBuff1.append("   AND CEBXLUPN = ?         \n"); 
		sqlBuff1.append("   AND CEBXLCST = ?         \n"); 
		sqlBuff1.append("   AND CEBXLMYM = ?         \n");           
//		sqlBuff1.append("   AND CEBXLYYM = ?         \n");           

		pstmt1 = new LoggablePreparedStatement(conn, sqlBuff1.toString());
            
		for(int i=0; i<list.size(); i++) {
			int idxparam1 = 1;
			vo = new TBEBXLF1Vo();
			vo = (TBEBXLF1Vo) list.get(i);
			
			pstmt1.setString(idxparam1++, vo.getCebxlydt());  
			pstmt1.setString(idxparam1++, vo.getCebxlyy1());  
			pstmt1.setString(idxparam1++, vo.getCebxlupn());
			pstmt1.setString(idxparam1++, vo.getCebxlcst());     
			pstmt1.setString(idxparam1++, vo.getCebxlmym());  
            
			pstmt1.addBatch();
			// logger.debug("\n" + ((LoggablePreparedStatement) pstmt1).getQueryString());
		}
		 
		pstmt1.executeBatch();
		 
		sqlBuff2.append("UPDATE                      \n");
		sqlBuff2.append("       EVLADM.TBEBXLF1      \n");
		sqlBuff2.append("   SET                      \n");  
		sqlBuff2.append("       CEBXLJGB = ?         \n"); 
		sqlBuff2.append(" WHERE                      \n"); 
		sqlBuff2.append("       RTRIM(CEBXLMYB) = '' \n"); 
		sqlBuff2.append("   AND CEBXLUPN = ?         \n"); 
		sqlBuff2.append("   AND CEBXLCST = ?         \n"); 
		sqlBuff2.append("   AND CEBXLYYM = ?         \n");

		pstmt2 = new LoggablePreparedStatement(conn, sqlBuff2.toString());
    
		for(int j=0; j<list.size(); j++) {
			int idxparam2 = 1;
			vo = new TBEBXLF1Vo();
			vo = (TBEBXLF1Vo) list.get(j);
	
			pstmt2.setString(idxparam2++, vo.getCebxljgb());  
			pstmt2.setString(idxparam2++, vo.getCebxlupn());
			pstmt2.setString(idxparam2++, vo.getCebxlcst());     
			pstmt2.setString(idxparam2++, vo.getCebxlyy1());  

//    		if(vo.getCebxljgb().trim().equals("3")) {
				pstmt2.addBatch();
//    		}

			// logger.debug("\n" + ((LoggablePreparedStatement) pstmt2).getQueryString());
		}

		pstmt2.executeBatch();		   
	  } catch(Exception e) {
		 e.printStackTrace();
		 throw e;
	  } finally {
		 try {
		 	if(pstmt1 != null) pstmt1.close();
			if(pstmt2 != null) pstmt2.close();
		 } catch(Exception e) {
		 }
	  }
   }           
   
   
   /**
   * �ۼ��� : mhcho
   * �ۼ��� : 2006.08.16
   * ��  �� : ������ǰ������� ��꼭 ������ ����
   * ��  Ÿ : 
   */
   public void updateRowForMC020101_TI(ArrayList list, Connection conn) throws Exception {
     LoggablePreparedStatement pstmt   = null ;     
     StringBuffer              sqlBuff = new StringBuffer(); 
     
     TBEBXLF1Vo                vo       = new TBEBXLF1Vo();  
           
     try { 
      sqlBuff.append("UPDATE                      \n");
      sqlBuff.append("       EVLADM.TBEBXLF1      \n");
      sqlBuff.append("   SET                      \n");  
      sqlBuff.append("       CEBXLTIS = ?,        \n");
      sqlBuff.append("       CEBXLTNO = ?,        \n"); 
      sqlBuff.append("       CEBXLNDT = ?,        \n"); 
      sqlBuff.append("       CEBXLNTM = ?,        \n"); 
      sqlBuff.append("       CEBXLCSJ = ?         \n");       
      sqlBuff.append("WHERE   CEBXLUPN   =   ?    \n");
      sqlBuff.append("  AND   CEBXLCST   =   ?    \n");
      sqlBuff.append("  AND   CEBXLMNO   =   ?    \n");
      sqlBuff.append("  AND   CEBXLGND   =   ?    \n");
      sqlBuff.append("  AND   CEBXLYDT   =   ?    \n");     
               
      pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
            
      for(int i=0; i<list.size(); i++) {
         int idxparam = 1;
         vo = new TBEBXLF1Vo();
         vo = (TBEBXLF1Vo) list.get(i);
         
         pstmt.setString(idxparam++, vo.getCebxltis());  
         pstmt.setString(idxparam++, vo.getCebxltno());  
         pstmt.setString(idxparam++, vo.getCebxlndt());  
         pstmt.setString(idxparam++, vo.getCebxlntm());  
         pstmt.setString(idxparam++, vo.getCebxlcsj());           
         pstmt.setString(idxparam++, vo.getCebxlupn());
         pstmt.setString(idxparam++, vo.getCebxlcst());     
         pstmt.setString(idxparam++, vo.getCebxlmno());  
         pstmt.setString(idxparam++, vo.getCebxlgnd());  
         pstmt.setString(idxparam++, vo.getCebxlydt());  
            
         pstmt.addBatch();
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
      }
       
      pstmt.executeBatch();       
           
     } catch(Exception e) {
       e.printStackTrace();
       throw e;
     } finally {
       try {
         if(pstmt != null) pstmt.close();         
       } catch(Exception e) {
       }
     }
   }              
   
   /**
   * �ۼ��� : mhcho
   * �ۼ��� : 2006.08.16
   * ��  �� : ������ǰ������� ��꼭 ��ҽ� ����
   * ��  Ÿ : 
   */
   public void updateRowForMC020101_TD(ArrayList list, Connection conn) throws Exception {
     LoggablePreparedStatement pstmt   = null ;     
     StringBuffer              sqlBuff = new StringBuffer(); 
     
     TBEBXLF1Vo                vo       = new TBEBXLF1Vo();  
           
     try { 
      sqlBuff.append("UPDATE                      \n");
      sqlBuff.append("       EVLADM.TBEBXLF1      \n");
      sqlBuff.append("   SET                      \n");  
      sqlBuff.append("       CEBXLTIS = ?,        \n");
      sqlBuff.append("       CEBXLTNO = '',       \n"); 
      sqlBuff.append("       CEBXLNDT = ?,        \n"); 
      sqlBuff.append("       CEBXLNTM = ?,        \n"); 
      sqlBuff.append("       CEBXLCSJ = ?         \n");       
		sqlBuff.append("WHERE   CEBXLTNO   =   ?    \n");
    /*sqlBuff.append("WHERE   CEBXLUPN   =   ?    \n");
		sqlBuff.append("  AND   CEBXLMNO   =   ?    \n");      
      sqlBuff.append("  AND   CEBXLCST   =   ?    \n");
      sqlBuff.append("  AND   CEBXLMNO   =   ?    \n");
      sqlBuff.append("  AND   CEBXLGND   =   ?    \n");
      sqlBuff.append("  AND   CEBXLYDT   =   ?    \n");*/     
               
      pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
            
      for(int i=0; i<list.size(); i++) {
         int idxparam = 1;
         vo = new TBEBXLF1Vo();
         vo = (TBEBXLF1Vo) list.get(i);
         
         pstmt.setString(idxparam++, vo.getCebxltis());    
         pstmt.setString(idxparam++, vo.getCebxlndt());  
         pstmt.setString(idxparam++, vo.getCebxlntm());  
         pstmt.setString(idxparam++, vo.getCebxlcsj());
			pstmt.setString(idxparam++, vo.getCebxltno());         
                    
   /*    pstmt.setString(idxparam++, vo.getCebxlupn());
         pstmt.setString(idxparam++, vo.getCebxlcst());     
         pstmt.setString(idxparam++, vo.getCebxlmno());  
         pstmt.setString(idxparam++, vo.getCebxlgnd());  
         pstmt.setString(idxparam++, vo.getCebxlydt());*/ 
            
         pstmt.addBatch();
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
      }
       
      pstmt.executeBatch();       
           
     } catch(Exception e) {
       e.printStackTrace();
       throw e;
     } finally {
       try {
         if(pstmt != null) pstmt.close();         
       } catch(Exception e) {
       }
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
	  TBEBXLF1Vo                 vo      = null;

	  try {
		 for(int i=0; i<list.size(); i++) {
			int idxparam = 1;
			sqlBuff = new StringBuffer(); 
			vo = new TBEBXLF1Vo();
			vo = (TBEBXLF1Vo) list.get(i);

			sqlBuff.append("UPDATE                                                                     \n");
			sqlBuff.append("       EVLADM.TBEBXLF1                                                     \n");
			sqlBuff.append("   SET                                                                     \n");
			sqlBuff.append("       CEBXLJUJ = ?                                                        \n");
			sqlBuff.append(" WHERE                                                                     \n");
			sqlBuff.append("       EXISTS (                                                            \n");
			sqlBuff.append("               SELECT                                                      \n");
			sqlBuff.append("                      'X'                                                  \n");
			sqlBuff.append("                 FROM                                                      \n");
			sqlBuff.append("                      EVLADM.TBEBWMF1                                      \n");
			sqlBuff.append("                WHERE                                                      \n");
			sqlBuff.append("                      CEBWMUPN = CEBXLUPN                                  \n");
			sqlBuff.append("                  AND CEBWMCST = CEBXLCST                                  \n");
			sqlBuff.append("                  AND CEBWMPJT = CEBXLPJT                                  \n");
			sqlBuff.append("                  AND CEBWMHGB = CEBXLHGB                                  \n");
			sqlBuff.append("                  AND CEBWMHNO = CEBXLHNO                                  \n");
			sqlBuff.append("                  AND CEBWMSEQ = CEBXLSEQ                                  \n");
			sqlBuff.append("                  AND CEBWMPJT = ?                                         \n");
			sqlBuff.append("                  AND CEBWMHGB = ?                                         \n");
			sqlBuff.append("                  AND CEBWMHNO = ?                                         \n");
			sqlBuff.append("                  AND CEBWMBSU = ?                                         \n");
			sqlBuff.append("                  AND RTRIM(CEBWMUHJ) > REPLACE(CHAR(CURRENT DATE),'-','') \n");
			sqlBuff.append("              )                                                            \n");
			sqlBuff.append("   AND RTRIM(CEBXLMYB) > ''                                                \n");

			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());             

			pstmt.setString(idxparam++, vo.getCebxljuj());
			pstmt.setString(idxparam++, vo.getCebxlpjt());
			pstmt.setString(idxparam++, vo.getCebxlhgb());
			pstmt.setString(idxparam++, vo.getCebxlhno());
			pstmt.setString(idxparam++, vo.getCebxlbsu());

			// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
			   * �ۼ��� : shhwang
			   * �ۼ��� : 2006.08.28
			   * ����    : �ο����� ������ҽ� ���� 
			   * ��Ÿ    : 
			   */
			   public void deleteRowForGY020103_04(ArrayList list, Connection conn) throws Exception
			   {
				   LoggablePreparedStatement pstmt  = null;         
				   StringBuffer sqlBuff    = new StringBuffer();         
				   TBEBXLF1Vo vo = new TBEBXLF1Vo();
				   try
				   { 
					   sqlBuff.append("  DELETE                    \n");
					   sqlBuff.append("  FROM     EVLADM.TBEBXLF1  \n");
					   sqlBuff.append("  WHERE CEBXLUPN   = ?      \n");
					   sqlBuff.append("  AND   CEBXLCST   = ?      \n");
					   sqlBuff.append("  AND   CEBXLPJT   = ?      \n");
					   sqlBuff.append("  AND   CEBXLHGB   = ?      \n");
					   sqlBuff.append("  AND   CEBXLHNO   = ?      \n");
					   sqlBuff.append("  AND   CEBXLSEQ   = ?      \n");
					   sqlBuff.append("AND  CEBXLGND   =   ?               \n");
//						sqlBuff.append("AND CEBXLMYM BETWEEN ? AND ?               \n");
//					   sqlBuff.append("  AND   CEBXLISR   = ?      \n");
												
					   pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
					   for (int i = 0; i < list.size(); i++)
					   {
						   int idxparam = 1;
						   vo = new TBEBXLF1Vo();
						   vo = (TBEBXLF1Vo) list.get(i);
						   pstmt.setString(idxparam++, vo.getCebxlupn());
						   pstmt.setString(idxparam++, vo.getCebxlcst());
						   pstmt.setString(idxparam++, vo.getCebxlpjt());
						   pstmt.setString(idxparam++, vo.getCebxlhgb());
						   pstmt.setString(idxparam++, vo.getCebxlhno());
						   pstmt.setString(idxparam++, vo.getCebxlseq());
						   pstmt.setString(idxparam++, vo.getCebxlgnd());   
//							pstmt.setString(idxparam++, vo.getCebxlmymfrom());   
//							pstmt.setString(idxparam++, vo.getCebxlmymto());   
//						   pstmt.setString(idxparam++, vo.getCebxlisr());
						
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
				  * �ۼ��� : shhwang
				  * �ۼ��� : 2006.08.25
				  * ����    : ������ ������ҽÿ� delete �Ѵ� . 
				  * ��Ÿ    : 
		  */
		 public void deleteRowForGY020105(ArrayList list, Connection conn) throws Exception 
		 {
			LoggablePreparedStatement  pstmt    = null ;
			StringBuffer               sqlBuff  = new StringBuffer(); 
			TBEBXLF1Vo                 vo       = new TBEBXLF1Vo();     
        
			try { 
			
			   sqlBuff.append("  DELETE            \n");
			   sqlBuff.append("  FROM     EVLADM.TBEBXLF1 \n");
			   sqlBuff.append("WHERE  CEBXLUPN   = ?               \n");  
			   sqlBuff.append("AND  CEBXLCST   = ?               \n");  
			   sqlBuff.append("AND  CEBXLPJT   = ?               \n");  
			   sqlBuff.append("AND  CEBXLHGB   =   ?               \n");
			   sqlBuff.append("AND  CEBXLHNO   =   ?               \n");
			   sqlBuff.append("AND  CEBXLSEQ   =   ?               \n");
			   sqlBuff.append("AND  CEBXLGND   =   ?               \n");
			   //sqlBuff.append("AND CEBXLMYM BETWEEN ? AND ?               \n");

			   pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

			   for (int i = 0; i < list.size(); i++) {
				  int idxparam = 1;
				  vo = new TBEBXLF1Vo();
				  vo = (TBEBXLF1Vo) list.get(i);
				
				  pstmt.setString(idxparam++, vo.getCebxlupn());
				  pstmt.setString(idxparam++, vo.getCebxlcst());
				  pstmt.setString(idxparam++, vo.getCebxlpjt());
				  pstmt.setString(idxparam++, vo.getCebxlhgb());
				  pstmt.setString(idxparam++, vo.getCebxlhno());   
				  pstmt.setString(idxparam++, vo.getCebxlseq());   
				  pstmt.setString(idxparam++, vo.getCebxlgnd());   
//				  pstmt.setString(idxparam++, vo.getCebxlmymfrom());   
//				  pstmt.setString(idxparam++, vo.getCebxlmymto());   
			   
				   // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
				   pstmt.executeUpdate();
			   }
			
      
			}catch(Exception e){
			   e.printStackTrace();
			   throw e;
			}finally{
			   try{if (pstmt != null) pstmt.close();}catch(Exception e){}
			}
		 }    
   
}
