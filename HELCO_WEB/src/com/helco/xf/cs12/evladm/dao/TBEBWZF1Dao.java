/**
 * �� �� �� : TBEBWZF1Dao.java
 * ��    �� : �����̰� ����(TBEBWZF1)  Dao
 * �� �� �� : moono
 * �� �� �� : 2006-04-17 ����  02:50:31
 * ���泻�� :
 *
 */ 
package com.helco.xf.cs12.evladm.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;

//import org.apache.log4j.Logger;

public class TBEBWZF1Dao implements FrwCrudDAO
{
//   static Logger logger = Logger.getLogger(TBEBWZF1Dao.class);
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

      Connection  conn  = null ;
      ArrayList alist = (ArrayList) vo;
      try{
         if(alist.size() > 0 ) {
            conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
           
				if("GY040101".equals(subMethodFlag))  // 2006.04.18 mhcho
               insertRowForGY040101(alist, conn);
         }    
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
         DBUtil.close(null, null, conn);
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
    * �ۼ���: jkkoo
    * �ۼ���: 2006-05-12
    * ��  ��: �⼺��/�����ȹ �����ϱ� ���� �����̰� ���� ��������.
    * ��  Ÿ: ����޼ҵ忡�� �����̰����� Key������ ������ ���� �׸���� ��ȸ �Ѵ�.
    */
	public Object select(Object condVO) throws Exception
	{
		ComMethodVo vo = (ComMethodVo) condVO;
		TBEBWZF1Vo rtnVo = new TBEBWZF1Vo();
		StringBuffer sqlBuff = new StringBuffer();
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     CEBWZUPN,                  \n"); // (����)Proj. No 
      sqlBuff.append("     CEBWZCST,                  \n"); // �ŷ�ó         
      sqlBuff.append("     CEBWZPJT,                  \n"); // (��)Proj. No   
      sqlBuff.append("     CEBWZHGB,                  \n"); // ȣ�ⱸ��       
      sqlBuff.append("     CEBWZHNO,                  \n"); // ȣ���ȣ       
      sqlBuff.append("     CEBWZIGM,                  \n"); // �̰��������   
      sqlBuff.append("     CEBWZGND,                  \n"); // ����(���)���� 
      sqlBuff.append("     CEBWZCHB,                  \n"); // �������»�     
      sqlBuff.append("     CEBWZGBB,                  \n"); // �ó��ܱ���     
      sqlBuff.append("     CEBWZGHB,                  \n"); // ����/���»籸��
      sqlBuff.append("     CEBWZIKD,                  \n"); // �̰��������   
      sqlBuff.append("     CEBWZIKJ,                  \n"); // �̰������     
      sqlBuff.append("     CEBWZCHA,                  \n"); // �������»�     
      sqlBuff.append("     CEBWZGBA,                  \n"); // �ó��ܱ���     
      sqlBuff.append("     CEBWZGHA,                  \n"); // ����/���»籸��
      sqlBuff.append("     CEBWZIJU,                  \n"); // �̰�����       
      sqlBuff.append("     CEBWZIHT,                  \n"); // �̰�����       
      sqlBuff.append("     CEBWZRLT,                  \n"); // �۾����       
      sqlBuff.append("     CEBWZRSN                   \n"); // �۾�����       

      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     EVLADM.TBEBWZF1,           \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   CEBWZUPN = ?            \n");
      sqlBuff.append("  AND   CEBWZCST = ?            \n");
      sqlBuff.append("  AND   CEBWZPJT = ?            \n");
      sqlBuff.append("  AND   CEBWZHGB = ?            \n");
      sqlBuff.append("  AND   CEBWZHNO = ?            \n");
      sqlBuff.append("  AND   CEBWZIGM = ?            \n");
      
		try
		{
			conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			pstmt.setString(1, vo.getUpn());
			pstmt.setString(2, vo.getCst());
			pstmt.setString(3, vo.getPjt());
			pstmt.setString(4, vo.getHgb());
			pstmt.setString(5, vo.getHno());
			pstmt.setString(6, vo.getIgm());
			// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
            rtnVo.setCebwzupn(rs.getString("CEBWZUPN")); // (����)Proj. No   
            rtnVo.setCebwzcst(rs.getString("CEBWZCST")); // �ŷ�ó           
            rtnVo.setCebwzpjt(rs.getString("CEBWZPJT")); // (��)Proj. No     
            rtnVo.setCebwzhgb(rs.getString("CEBWZHGB")); // ȣ�ⱸ��         
            rtnVo.setCebwzhno(rs.getString("CEBWZHNO")); // ȣ���ȣ         
            rtnVo.setCebwzigm(rs.getString("CEBWZIGM")); // �̰��������     
            rtnVo.setCebwzgnd(rs.getString("CEBWZGND")); // ����(���)����   
            rtnVo.setCebwzchb(rs.getString("CEBWZCHB")); // �������»�       
            rtnVo.setCebwzgbb(rs.getString("CEBWZGBB")); // �ó��ܱ���       
            rtnVo.setCebwzghb(rs.getString("CEBWZGHB")); // ����/���»籸��  
            rtnVo.setCebwzikd(rs.getString("CEBWZIKD")); // �̰��������     
            rtnVo.setCebwzikj(rs.getString("CEBWZIKJ")); // �̰������       
            rtnVo.setCebwzcha(rs.getString("CEBWZCHA")); // �������»�       
            rtnVo.setCebwzgba(rs.getString("CEBWZGBA")); // �ó��ܱ���       
            rtnVo.setCebwzgha(rs.getString("CEBWZGHA")); // ����/���»籸��  
            rtnVo.setCebwziju(rs.getString("CEBWZIJU")); // �̰�����         
            rtnVo.setCebwziht(rs.getString("CEBWZIHT")); // �̰�����         
            rtnVo.setCebwzrlt(rs.getString("CEBWZRLT")); // �۾����         
            rtnVo.setCebwzrsn(rs.getString("CEBWZRSN")); // �۾�����       
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
				DBUtil.close(rs, pstmt, conn);
			}
			catch (Exception e)
			{
			}
		}
		return rtnVo;
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
		return null;
	}
	public Object select2(Object condVO) throws Exception
	{
		return null;
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
      Connection  conn  = null ;
      ArrayList alist = (ArrayList) pkList;
      try{
         if(alist.size() > 0 ) {
            conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
           
            if("GY040101".equals(subMethodFlag))  // 2006.04.18 mhcho
               deleteRowForGY040101(alist, conn);
         }
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
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

      Connection  conn  = null ;
      ArrayList alist = (ArrayList) vo;
      try{
         if(alist.size() > 0 ) {
            conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
           
 //           if("GY050302".equals(subMethodFlag))         // 2006.03.28 mhcho
 //              updateRowForGY050302(alist, conn);              
                                                                               
         }
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
         DBUtil.close(null, null, conn);
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
    * �ۼ��� : mhcho
    * �ۼ��� : 2006.04.18
    * ����   : �����̰����� �� Ȯ�� insert �Ѵ� . 
    * ��Ÿ   : 
    */
   public void insertRowForGY040101(ArrayList alist, Connection conn) throws Exception {
      int rtnCd   = 0;
      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWZF1Vo                    vo       = new TBEBWZF1Vo();     

      try {
         sqlBuff.append("INSERT  INTO  EVLADM.TBEBWZF1 ( \n");            
         sqlBuff.append("  CEBWZUPN,      \n");              // ����ProjNo                
         sqlBuff.append("  CEBWZCST,      \n");              // �ŷ�ó                
         sqlBuff.append("  CEBWZPJT,      \n");              // ProjNo                    
         sqlBuff.append("  CEBWZHGB,      \n");              // ȣ�ⱸ��                
         sqlBuff.append("  CEBWZHNO,      \n");              // ȣ���ȣ        
         sqlBuff.append("  CEBWZIGM,      \n");              // �̰��������                  
         sqlBuff.append("  CEBWZGND,      \n");              // ��������          
         sqlBuff.append("  CEBWZCHB,      \n");              // �������»�              
         sqlBuff.append("  CEBWZGBB,      \n");              // �ó��ܱ���                
         sqlBuff.append("  CEBWZGHB,      \n");              // ��ü����                
         sqlBuff.append("  CEBWZIKD,      \n");              // �̰��������                
         sqlBuff.append("  CEBWZIKJ,      \n");              // �̰������                  
         sqlBuff.append("  CEBWZCHA,      \n");              // �̰��ĺ������»�                
         sqlBuff.append("  CEBWZGBA,      \n");              // �̰��Ľó��ܱ���                  
         sqlBuff.append("  CEBWZGHA,      \n");              // �̰��� ����/���»籸��                
         sqlBuff.append("  CEBWZIJU,      \n");              // �̰�����        
         sqlBuff.append("  CEBWZIHT)      \n");              // �̰�����                 
         sqlBuff.append("VALUES     (     \n");                   
         sqlBuff.append("  ?,             \n");              //                 
         sqlBuff.append("  ?,             \n");              //                 
         sqlBuff.append("  ?,             \n");              //                     
         sqlBuff.append("  ?,             \n");              //                 
         sqlBuff.append("  ?,             \n");              //        
         sqlBuff.append("  ?,             \n");              //                  
         sqlBuff.append("  ?,             \n");              //           
         sqlBuff.append("  ?,             \n");              //               
         sqlBuff.append("  ?,             \n");              //                 
         sqlBuff.append("  ?,             \n");              //                
         sqlBuff.append("  ?,             \n");              //                 
         sqlBuff.append("  ?,             \n");              //                   
         sqlBuff.append("  ?,             \n");              //                 
         sqlBuff.append("  ?,             \n");              //                   
         sqlBuff.append("  ?,             \n");              //         
         sqlBuff.append("  ?,             \n");              //               
         sqlBuff.append("  ? )            \n");              //                            

         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < alist.size(); i++) {
            int idxparam = 1;
            vo = new TBEBWZF1Vo();
            vo = (TBEBWZF1Vo) alist.get(i);
      
            pstmt.setString(idxparam++, vo.getCebwzupn());
            pstmt.setString(idxparam++, vo.getCebwzcst());            
            pstmt.setString(idxparam++, vo.getCebwzpjt());
            pstmt.setString(idxparam++, vo.getCebwzhgb());   
            pstmt.setString(idxparam++, vo.getCebwzhno());                      
            pstmt.setString(idxparam++, vo.getCebwzigm());
            pstmt.setString(idxparam++, vo.getCebwzgnd());
            pstmt.setString(idxparam++, vo.getCebwzchb());            
            pstmt.setString(idxparam++, vo.getCebwzgbb());
            pstmt.setString(idxparam++, vo.getCebwzghb());   
            pstmt.setString(idxparam++, vo.getCebwzikd());                      
            pstmt.setString(idxparam++, vo.getCebwzikj());
            pstmt.setString(idxparam++, vo.getCebwzcha());
            pstmt.setString(idxparam++, vo.getCebwzgba());            
            pstmt.setString(idxparam++, vo.getCebwzgha());
            pstmt.setString(idxparam++, vo.getCebwziju());   
            pstmt.setString(idxparam++, vo.getCebwziht());   
  
            rtnCd = pstmt.executeUpdate();
            if(rtnCd!=1) throw new BizException("������ ����");
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }

         
      }catch(BizException e){
         throw new BizException("������ ����");
      }catch(Exception e){         
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      } 
      
   }   
   
   /**
    * �ۼ��� : mhcho
    * �ۼ��� : 2006.04.18
    * ����   : �����̰� ���� �� delete �Ѵ� . 
    * ��Ÿ   : 
    */
   public void deleteRowForGY040101(ArrayList alist, Connection conn) throws Exception {
      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWZF1Vo                    vo       = new TBEBWZF1Vo();     
      int rtnCd   = 0;

      try {
         sqlBuff.append("DELETE FROM EVLADM.TBEBWZF1   \n");
         sqlBuff.append("  WHERE CEBWZUPN=? \n");
         sqlBuff.append("  AND CEBWZCST=?   \n");
         sqlBuff.append("  AND CEBWZPJT=?   \n");   
         sqlBuff.append("  AND CEBWZHGB=?   \n");  
         sqlBuff.append("  AND CEBWZHNO=?   \n");  
         sqlBuff.append("  AND CEBWZIGM=?   \n");                   

         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < alist.size(); i++) {
            int idxparam = 1;
            vo = new TBEBWZF1Vo();
            vo = (TBEBWZF1Vo) alist.get(i);
            pstmt.setString(idxparam++, vo.getCebwzupn());
            pstmt.setString(idxparam++, vo.getCebwzcst());
            pstmt.setString(idxparam++, vo.getCebwzpjt());  
            pstmt.setString(idxparam++, vo.getCebwzhgb());  
            pstmt.setString(idxparam++, vo.getCebwzhno());
            pstmt.setString(idxparam++, vo.getCebwzigm());              
  
            rtnCd = pstmt.executeUpdate();
            if(rtnCd!=1) throw new BizException("������ �����߻�.");
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }             

      }catch(BizException e){
         throw new BizException("������ �����߻�.");   
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      }
   }         
                                        
}
