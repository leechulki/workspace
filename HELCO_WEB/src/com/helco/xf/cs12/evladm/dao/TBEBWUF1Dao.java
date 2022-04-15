/**
 * 파 일 명 : TBEBWUF1Dao.java
 * 설    명 : 일시중지보류요청 정보(TBEBWUF1)  Dao
 * 작 성 자 : moono
 * 작 성 일 : 2006-03-23 오후  02:50:31
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
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWUF1Vo;

// import org.apache.log4j.Logger;

public class TBEBWUF1Dao extends AbstractBusinessService implements FrwCrudDAO
{
//   static Logger logger = Logger.getLogger(TBEBWVF1Dao.class);
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
           
            if("GY050201".equals(subMethodFlag))  // 2006.04.11 mhcho
               insertRowForGY050201(alist, conn);              
            if("GY050202".equals(subMethodFlag))  // 2006.04.14 mhcho
               insertRowForGY050202(alist, conn);                             
                             
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
    * 작성자: jkkoo
    * 작성일: 2006-05-12
    * 설  명: 기성비/매축계획 생성하기 위한 일시중지/복구 정보 가져오기.
    * 기  타: 공통메소드에서 일시중지/복구 Key정보를 가지고 관련 항목들을 조회 한다.
    */
   public Object select(Object condVO) throws Exception
   {
      ComMethodVo vo = (ComMethodVo) condVO;
      TBEBWUF1Vo rtnVo = new TBEBWUF1Vo();
      StringBuffer sqlBuff = new StringBuffer();
      LoggablePreparedStatement pstmt = null;
      ResultSet rs = null;
      Connection conn = null;
      
      sqlBuff.append(" SELECT                      \n");
      sqlBuff.append("        MANDT,               \n");
      sqlBuff.append("        CS146_UPN,           \n");
      sqlBuff.append("        CS146_CST,           \n");
      sqlBuff.append("        CS146_PJT,           \n");
      sqlBuff.append("        CS146_HNO,           \n");
      sqlBuff.append("        CS146_SEQ,           \n");
      sqlBuff.append("        CS146_ISR,           \n");
      sqlBuff.append("        CS146_STA,           \n");
      sqlBuff.append("        CS146_DGB,           \n");
      sqlBuff.append("        CS146_JHD,           \n");
      sqlBuff.append("        CS146_SSA,           \n");
      sqlBuff.append("        CS146_REQ,           \n");
      sqlBuff.append("        CS146_RLT,           \n");
      sqlBuff.append("        CS146_RMK,           \n");
      sqlBuff.append("        CS146_SCP,           \n");
      sqlBuff.append("        CS146_YMD,           \n");
      sqlBuff.append("        CS146_UNT,           \n");
      sqlBuff.append("        CS146_SDT            \n");
      sqlBuff.append("  FROM                       \n");
      sqlBuff.append("        SAPHEE.ZCST146       \n");
      sqlBuff.append("  WHERE 1=1                  \n");
      sqlBuff.append("  AND   MANDT     = ?        \n");
      sqlBuff.append("  AND   CS146_UPN = ?        \n");
      sqlBuff.append("  AND   CS146_CST = ?        \n");
      sqlBuff.append("  AND   CS146_PJT = ?        \n");
      sqlBuff.append("  AND   CS146_HNO = ?        \n");
      sqlBuff.append("  AND   CS146_SEQ = ?        \n");
      sqlBuff.append("  AND   CS146_ISR = ?        \n");
    
      try
      {
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
 	     conn  = getConnection(db_con);
    	 
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         pstmt.setString(1, vo.getMandt());
         pstmt.setString(2, vo.getUpn());
         pstmt.setString(3, vo.getCst());
         pstmt.setString(4, vo.getPjt());
         pstmt.setString(5, vo.getHno());
         pstmt.setString(6, vo.getSeq());   
         pstmt.setString(7, vo.getIsr());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
            rtnVo.setMandt(rs.getString("MANDT"));
            rtnVo.setCebwuupn(rs.getString("CS146_UPN"));
            rtnVo.setCebwucst(rs.getString("CS146_CST"));
            rtnVo.setCebwupjt(rs.getString("CS146_PJT"));
            rtnVo.setCebwuhno(rs.getString("CS146_HNO"));
            rtnVo.setCebwuseq(rs.getString("CS146_SEQ"));
            rtnVo.setCebwuisr(rs.getString("CS146_ISR"));
            rtnVo.setCebwusta(rs.getString("CS146_STA"));
            rtnVo.setCebwudgb(rs.getString("CS146_DGB"));
            rtnVo.setCebwujhd(rs.getString("CS146_JHD"));
            rtnVo.setCebwussa(rs.getString("CS146_SSA"));
            rtnVo.setCebwureq(rs.getString("CS146_REQ"));
            rtnVo.setCebwurlt(rs.getString("CS146_RLT"));
            rtnVo.setCebwurmk(rs.getString("CS146_RMK"));
            rtnVo.setCebwuscp(rs.getString("CS146_SCP"));
            rtnVo.setCebwuymd(rs.getString("CS146_YMD"));
            rtnVo.setCebwuunt(String.valueOf(rs.getBigDecimal("CS146_UNT")).toString());        
            rtnVo.setCebwuwym(rs.getString("CS146_SDT"));
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
      /*
      Connection  conn  = null ;
      ArrayList alist = (ArrayList) pkList;
      try{
         if(alist.size() > 0 ) {
            conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
           
            if("GY050302".equals(subMethodFlag))  // 2006.03.30 mhcho
               deleteRowForGY050302(alist, conn);
         }
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
         DBUtil.close(null, null, conn);
      } 
      */         
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
            
            if("GY050201".equals(subMethodFlag))      // 2006.04.12 mhcho
               updateRowForGY050201(alist, conn);                                      
            if("GY050505_PA5".equals(subMethodFlag))      // 2006.04.14 mhcho
               updateRowForGY050505_PA5(alist, conn);                
            if("GY050505_JA6".equals(subMethodFlag))      // 2006.04.14 mhcho
               updateRowForGY050505_JA6(alist, conn);
            if("GY050506_PA5".equals(subMethodFlag))      // 2006.04.15 mhcho
               updateRowForGY050506_PA5(alist, conn);                
            if("GY050506_JA6".equals(subMethodFlag))      // 2006.04.15 mhcho
               updateRowForGY050506_JA6(alist, conn);
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
    * 작성자 : mhcho
    * 작성일 : 2006.04.11
    * 설명   : 일시중지보류요청시 insert 한다 . 
    * 기타   : 
    */
   public void insertRowForGY050201(ArrayList alist, Connection conn) throws Exception {

      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWUF1Vo                    vo       = new TBEBWUF1Vo();     

      try {
         sqlBuff.append("INSERT  INTO  EVLADM.TBEBWUF1 ( \n");            
         sqlBuff.append("  CEBWUGBN,      \n");
         sqlBuff.append("  CEBWUWYM,      \n");
         sqlBuff.append("  CEBWUISR,      \n");
         sqlBuff.append("  CEBWUUPN,      \n");
         sqlBuff.append("  CEBWUCST,      \n");
         sqlBuff.append("  CEBWUPJT,      \n");
         sqlBuff.append("  CEBWUHGB,      \n");
         sqlBuff.append("  CEBWUHNO,      \n");
         sqlBuff.append("  CEBWUSEQ,      \n");
         sqlBuff.append("  CEBWUGND,      \n");
         sqlBuff.append("  CEBWUSTA,      \n");
         sqlBuff.append("  CEBWUJHD,      \n");
         sqlBuff.append("  CEBWUSSA,      \n");
         sqlBuff.append("  CEBWUREQ,      \n");
         sqlBuff.append("  CEBWURLT,      \n");
         sqlBuff.append("  CEBWURMK,      \n");
         sqlBuff.append("  CEBWUSCP,      \n");
         sqlBuff.append("  CEBWUYMD,      \n");
         sqlBuff.append("  CEBWUUNT,      \n");
         sqlBuff.append("  CEBWUHST)      \n");             
         sqlBuff.append("VALUES     (     \n");                   
         sqlBuff.append("  ?,             \n");                              
         sqlBuff.append("  ?,             \n");                              
         sqlBuff.append("  ?,             \n");                                  
         sqlBuff.append("  ?,             \n");                             
         sqlBuff.append("  ?,             \n");                    
         sqlBuff.append("  ?,             \n");                              
         sqlBuff.append("  ?,             \n");                       
         sqlBuff.append("  ?,             \n");                          
         sqlBuff.append("  ?,             \n");                            
         sqlBuff.append("  ?,             \n");                            
         sqlBuff.append("  ?,             \n");                            
         sqlBuff.append("  ?,             \n");                            
         sqlBuff.append("  ?,             \n");                           
         sqlBuff.append("  ?,             \n");                             
         sqlBuff.append("  ?,             \n");                  
         sqlBuff.append("  ?,             \n");                 
         sqlBuff.append("  ?,             \n");                   
         sqlBuff.append("  ?,             \n");                     
         sqlBuff.append("  ?,             \n");                           
         sqlBuff.append("  ? )            \n");                                   

         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < alist.size(); i++) {
            int idxparam = 1;
            vo = new TBEBWUF1Vo();
            vo = (TBEBWUF1Vo) alist.get(i);
      
            pstmt.setString(idxparam++, vo.getCebwugbn());
            pstmt.setString(idxparam++, vo.getCebwuwym());
            pstmt.setString(idxparam++, vo.getCebwuisr());
            pstmt.setString(idxparam++, vo.getCebwuupn());
            pstmt.setString(idxparam++, vo.getCebwucst());
            pstmt.setString(idxparam++, vo.getCebwupjt());
            pstmt.setString(idxparam++, vo.getCebwuhgb());
            pstmt.setString(idxparam++, vo.getCebwuhno());
            pstmt.setString(idxparam++, vo.getCebwuseq());
            pstmt.setString(idxparam++, vo.getCebwugnd());
            pstmt.setString(idxparam++, vo.getCebwusta());
            pstmt.setString(idxparam++, vo.getCebwujhd());
            pstmt.setString(idxparam++, vo.getCebwussa());
            pstmt.setString(idxparam++, vo.getCebwureq());
            pstmt.setString(idxparam++, vo.getCebwurlt());
            pstmt.setString(idxparam++, vo.getCebwurmk());
            pstmt.setString(idxparam++, vo.getCebwuscp());
            pstmt.setString(idxparam++, vo.getCebwuymd());
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwuunt()));
            pstmt.setString(idxparam++, vo.getCebwuhst());

  
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }              
         pstmt.executeBatch();      
   
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      }        
   }         
      
   /**
    * 작성자 : mhcho
    * 작성일 : 2006.04.11
    * 설명   : 일시중지보류요청 수정시 update 한다 . 
    * 기타   : 
    */
   public void updateRowForGY050201(ArrayList alist, Connection conn) throws Exception {

      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWUF1Vo                    vo       = new TBEBWUF1Vo();     

      try {
         sqlBuff.append("UPDATE EVLADM.TBEBWUF1 SET \n");          
         sqlBuff.append("  CEBWUUPN=?,      \n");
         sqlBuff.append("  CEBWUCST=?,      \n");
         sqlBuff.append("  CEBWUPJT=?,      \n");
         sqlBuff.append("  CEBWUHGB=?,      \n");
         sqlBuff.append("  CEBWUHNO=?,      \n");
         sqlBuff.append("  CEBWUSEQ=?,      \n");
         sqlBuff.append("  CEBWUGND=?,      \n");
         sqlBuff.append("  CEBWUSTA=?,      \n");
         sqlBuff.append("  CEBWUJHD=?,      \n");
         sqlBuff.append("  CEBWUSSA=?,      \n");
         sqlBuff.append("  CEBWUREQ=?,      \n");
         sqlBuff.append("  CEBWURLT=?,      \n");
         sqlBuff.append("  CEBWURMK=?,      \n");
         sqlBuff.append("  CEBWUSCP=?,      \n");
         sqlBuff.append("  CEBWUYMD=?,      \n");
         sqlBuff.append("  CEBWUUNT=?,      \n");
         sqlBuff.append("  CEBWUHST=?       \n");             
         sqlBuff.append("WHERE CEBWUGBN=?   \n");                   
         sqlBuff.append("  AND CEBWUWYM=?   \n");                              
         sqlBuff.append("  AND CEBWUISR=?   \n");     
         sqlBuff.append("  AND CEBWUUPN=?   \n");    
         sqlBuff.append("  AND CEBWUCST=?   \n");    
         sqlBuff.append("  AND CEBWUPJT=?   \n");    
         sqlBuff.append("  AND CEBWUHGB=?   \n");    
         sqlBuff.append("  AND CEBWUHNO=?   \n");
         sqlBuff.append("  AND CEBWUSEQ=?   \n");                                 
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < alist.size(); i++) {
            int idxparam = 1;
            vo = new TBEBWUF1Vo();
            vo = (TBEBWUF1Vo) alist.get(i);      

            pstmt.setString(idxparam++, vo.getCebwuupn());
            pstmt.setString(idxparam++, vo.getCebwucst());
            pstmt.setString(idxparam++, vo.getCebwupjt());
            pstmt.setString(idxparam++, vo.getCebwuhgb());
            pstmt.setString(idxparam++, vo.getCebwuhno());
            pstmt.setString(idxparam++, vo.getCebwuseq());
            pstmt.setString(idxparam++, vo.getCebwugnd());
            pstmt.setString(idxparam++, vo.getCebwusta());
            pstmt.setString(idxparam++, vo.getCebwujhd());
            pstmt.setString(idxparam++, vo.getCebwussa());
            pstmt.setString(idxparam++, vo.getCebwureq());
            pstmt.setString(idxparam++, vo.getCebwurlt());
            pstmt.setString(idxparam++, vo.getCebwurmk());
            pstmt.setString(idxparam++, vo.getCebwuscp());
            pstmt.setString(idxparam++, vo.getCebwuymd());
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwuunt()));
            pstmt.setString(idxparam++, vo.getCebwuhst());
            pstmt.setString(idxparam++, vo.getCebwugbn());
            pstmt.setString(idxparam++, vo.getCebwuwym());
            pstmt.setString(idxparam++, vo.getCebwuisr());
            pstmt.setString(idxparam++, vo.getCebwuupn());
            pstmt.setString(idxparam++, vo.getCebwucst());
            pstmt.setString(idxparam++, vo.getCebwupjt());
            pstmt.setString(idxparam++, vo.getCebwuhgb());
            pstmt.setString(idxparam++, vo.getCebwuhno());
            pstmt.setString(idxparam++, vo.getCebwuseq());

  
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }              
         pstmt.executeBatch();      
   
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      }        
   }     
   
   /**
    * 작성자 : mhcho
    * 작성일 : 2006.04.14
    * 설명   : 일시중지보류요청 (보수PM)접수시 update 한다 . 
    * 기타   : 
    */
   public void updateRowForGY050505_PA5(ArrayList alist, Connection conn) throws Exception {

      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWUF1Vo                    vo       = new TBEBWUF1Vo();     

      try {
         sqlBuff.append("UPDATE EVLADM.TBEBWUF1 SET \n");              
         sqlBuff.append("  CEBWUSTA=?,      \n");
         sqlBuff.append("  CEBWUJHD=?,      \n");  
         sqlBuff.append("  CEBWUSSA=?,      \n");  
         sqlBuff.append("  CEBWUREQ=?,      \n");  
         sqlBuff.append("  CEBWURLT=?,      \n");  
         sqlBuff.append("  CEBWURMK=?,      \n");  
         sqlBuff.append("  CEBWUSCP=?,      \n");   
         sqlBuff.append("  CEBWUYMD=?,      \n");  
         sqlBuff.append("  CEBWUUNT=?       \n");
         sqlBuff.append("WHERE CEBWUGBN=?   \n");                   
         sqlBuff.append("  AND CEBWUWYM=?   \n");                              
         sqlBuff.append("  AND CEBWUISR=?   \n");     
         sqlBuff.append("  AND CEBWUUPN=?   \n");    
         sqlBuff.append("  AND CEBWUCST=?   \n");    
         sqlBuff.append("  AND CEBWUPJT=?   \n");    
         sqlBuff.append("  AND CEBWUHGB=?   \n");    
         sqlBuff.append("  AND CEBWUHNO=?   \n");
         sqlBuff.append("  AND CEBWUSEQ=?   \n");                                 
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < alist.size(); i++) {
            int idxparam = 1;
            vo = new TBEBWUF1Vo();
            vo = (TBEBWUF1Vo) alist.get(i);   
            
            pstmt.setString(idxparam++, vo.getCebwusta());
            pstmt.setString(idxparam++, vo.getCebwujhd());
            pstmt.setString(idxparam++, vo.getCebwussa());
            pstmt.setString(idxparam++, vo.getCebwureq());
            pstmt.setString(idxparam++, vo.getCebwurlt());
            pstmt.setString(idxparam++, vo.getCebwurmk());
            pstmt.setString(idxparam++, vo.getCebwuscp());
            pstmt.setString(idxparam++, vo.getCebwuymd());
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwuunt()));            
            
            pstmt.setString(idxparam++, vo.getCebwugbn());
            pstmt.setString(idxparam++, vo.getCebwuwym());
            pstmt.setString(idxparam++, vo.getCebwuisr());
            pstmt.setString(idxparam++, vo.getCebwuupn());
            pstmt.setString(idxparam++, vo.getCebwucst());
            pstmt.setString(idxparam++, vo.getCebwupjt());
            pstmt.setString(idxparam++, vo.getCebwuhgb());
            pstmt.setString(idxparam++, vo.getCebwuhno());
            pstmt.setString(idxparam++, vo.getCebwuseq());            
             
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }              
         pstmt.executeBatch();      
   
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      }        
   }     
   /**
    * 작성자 : mhcho
    * 작성일 : 2006.04.14
    * 설명   : 일시중지보류요청 (고객지원부)승인시 update 한다 . 
    * 기타   : 
    */
   public void updateRowForGY050505_JA6(ArrayList alist, Connection conn) throws Exception {

      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWUF1Vo                    vo       = new TBEBWUF1Vo();     
      ComMethodVo                   comVo;

      try {
         sqlBuff.append("UPDATE EVLADM.TBEBWUF1 SET \n");  
         sqlBuff.append("  CEBWUSEQ=?,      \n");         
         sqlBuff.append("  CEBWUGND=?,      \n");            
         sqlBuff.append("  CEBWUSTA=?,      \n");
         sqlBuff.append("  CEBWUJHD=?,      \n");  
         sqlBuff.append("  CEBWUSSA=?,      \n");  
         sqlBuff.append("  CEBWUREQ=?,      \n");  
         sqlBuff.append("  CEBWURLT=?,      \n");  
         sqlBuff.append("  CEBWURMK=?,      \n");  
         sqlBuff.append("  CEBWUSCP=?,      \n");   
         sqlBuff.append("  CEBWUYMD=?,      \n");  
         sqlBuff.append("  CEBWUUNT=?       \n");
         sqlBuff.append("WHERE CEBWUGBN=?   \n");                   
         sqlBuff.append("  AND CEBWUWYM=?   \n");                              
         sqlBuff.append("  AND CEBWUISR=?   \n");     
         sqlBuff.append("  AND CEBWUUPN=?   \n");    
         sqlBuff.append("  AND CEBWUCST=?   \n");    
         sqlBuff.append("  AND CEBWUPJT=?   \n");    
         sqlBuff.append("  AND CEBWUHGB=?   \n");    
         sqlBuff.append("  AND CEBWUHNO=?   \n");
         sqlBuff.append("  AND CEBWUSEQ=?   \n");                                 
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < alist.size(); i++) {
            int idxparam = 1;
            vo = new TBEBWUF1Vo();
            vo = (TBEBWUF1Vo) alist.get(i);   
            
            pstmt.setString(idxparam++, vo.getCebwuseq());
            pstmt.setString(idxparam++, vo.getCebwugnd());
            pstmt.setString(idxparam++, vo.getCebwusta());
            pstmt.setString(idxparam++, vo.getCebwujhd());
            pstmt.setString(idxparam++, vo.getCebwussa());
            pstmt.setString(idxparam++, vo.getCebwureq());
            pstmt.setString(idxparam++, vo.getCebwurlt());
            pstmt.setString(idxparam++, vo.getCebwurmk());
            pstmt.setString(idxparam++, vo.getCebwuscp());
            pstmt.setString(idxparam++, vo.getCebwuymd());
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebwuunt()));            
            
            pstmt.setString(idxparam++, vo.getCebwugbn());
            pstmt.setString(idxparam++, vo.getCebwuwym());
            pstmt.setString(idxparam++, vo.getCebwuisr());
            pstmt.setString(idxparam++, vo.getCebwuupn());
            pstmt.setString(idxparam++, vo.getCebwucst());
            pstmt.setString(idxparam++, vo.getCebwupjt());
            pstmt.setString(idxparam++, vo.getCebwuhgb());
            pstmt.setString(idxparam++, vo.getCebwuhno());
            pstmt.setString(idxparam++, vo.getCebwuseq());            
             
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());

            comVo = new ComMethodVo();
                  
            comVo.setDataId("52");            
            comVo.setJobGubun("D");
            if(vo.getCebwugnd().equals("C") || vo.getCebwugnd().equals("D")){              //유상
               comVo.setUpn(vo.getCebwuupn());
               comVo.setCst(vo.getCebwucst());               
            }else if(vo.getCebwugnd().equals("A") || vo.getCebwugnd().equals("B")){       //무상
               comVo.setUpn("ZZZZZ");
               comVo.setCst("ZZZ");               
            }else
               throw new BizException("발주종류가 정의 되지 않았습니다");

            comVo.setPjt(vo.getCebwupjt());
            comVo.setHgb(vo.getCebwuhgb());
            comVo.setHno(vo.getCebwuhno());
            comVo.setSeq(vo.getCebwuseq());
            comVo.setGbn(vo.getCebwugbn());
            comVo.setWym(vo.getCebwuwym());
            comVo.setIsr(vo.getCebwuisr());
            comVo.setFirstUserId(vo.getFirstUserId());
            
//            ComMethodDao.SetBXRBXL(comVo);            
         }              
         pstmt.executeBatch();      
   
      }catch (BizException be) {
            throw be;         
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      }        
   }     
   
   /**
    * 작성자 : mhcho
    * 작성일 : 2006.04.11
    * 설명   : 일시중지보류 복구요청시 insert 한다 . 
    * 기타   : 
    */
   public void insertRowForGY050202(ArrayList alist, Connection conn) throws Exception {

      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWUF1Vo                    vo       = new TBEBWUF1Vo();     

      try {
         sqlBuff.append("INSERT  INTO  EVLADM.TBEBWUF1 ( \n");            
         sqlBuff.append("  CEBWUGBN,      \n");
         sqlBuff.append("  CEBWUWYM,      \n");
         sqlBuff.append("  CEBWUISR,      \n");
         sqlBuff.append("  CEBWUUPN,      \n");
         sqlBuff.append("  CEBWUCST,      \n");
         sqlBuff.append("  CEBWUPJT,      \n");
         sqlBuff.append("  CEBWUHGB,      \n");
         sqlBuff.append("  CEBWUHNO,      \n");
         sqlBuff.append("  CEBWUSEQ,      \n");
         sqlBuff.append("  CEBWUGND,      \n");
         sqlBuff.append("  CEBWUSTA,      \n");
         sqlBuff.append("  CEBWUJHD,      \n");
         sqlBuff.append("  CEBWUSSA,      \n");
         sqlBuff.append("  CEBWUREQ,      \n");
         sqlBuff.append("  CEBWURLT,      \n");
         sqlBuff.append("  CEBWURMK,      \n");
         sqlBuff.append("  CEBWUSCP,      \n");
         sqlBuff.append("  CEBWUYMD,      \n");         
         sqlBuff.append("  CEBWUHST)      \n");             
         sqlBuff.append("VALUES     (     \n");                   
         sqlBuff.append("  ?,             \n");                              
         sqlBuff.append("  ?,             \n");                              
         sqlBuff.append("  ?,             \n");                                  
         sqlBuff.append("  ?,             \n");                             
         sqlBuff.append("  ?,             \n");                    
         sqlBuff.append("  ?,             \n");                              
         sqlBuff.append("  ?,             \n");                       
         sqlBuff.append("  ?,             \n");                          
         sqlBuff.append("  ?,             \n");                            
         sqlBuff.append("  ?,             \n");                            
         sqlBuff.append("  ?,             \n");                            
         sqlBuff.append("  ?,             \n");                            
         sqlBuff.append("  ?,             \n");                           
         sqlBuff.append("  ?,             \n");                             
         sqlBuff.append("  ?,             \n");                  
         sqlBuff.append("  ?,             \n");
         sqlBuff.append("  ?,             \n");                     
         sqlBuff.append("  ?,             \n");                           
         sqlBuff.append("  ? )            \n");                                   

         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < alist.size(); i++) {
            int idxparam = 1;
            vo = new TBEBWUF1Vo();
            vo = (TBEBWUF1Vo) alist.get(i);
      
            pstmt.setString(idxparam++, vo.getCebwugbn());
            pstmt.setString(idxparam++, vo.getCebwuwym());
            pstmt.setString(idxparam++, vo.getCebwuisr());
            pstmt.setString(idxparam++, vo.getCebwuupn());
            pstmt.setString(idxparam++, vo.getCebwucst());
            pstmt.setString(idxparam++, vo.getCebwupjt());
            pstmt.setString(idxparam++, vo.getCebwuhgb());
            pstmt.setString(idxparam++, vo.getCebwuhno());
            pstmt.setString(idxparam++, vo.getCebwuseq());
            pstmt.setString(idxparam++, vo.getCebwugnd());
            pstmt.setString(idxparam++, vo.getCebwusta());
            pstmt.setString(idxparam++, vo.getCebwujhd());
            pstmt.setString(idxparam++, vo.getCebwussa());
            pstmt.setString(idxparam++, vo.getCebwureq());
            pstmt.setString(idxparam++, vo.getCebwurlt());
            pstmt.setString(idxparam++, vo.getCebwurmk());
            pstmt.setString(idxparam++, vo.getCebwuscp());
            pstmt.setString(idxparam++, vo.getCebwuymd());            
            pstmt.setString(idxparam++, vo.getCebwuhst());

  
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }              
         pstmt.executeBatch();      
   
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      }        
   }         
      
   /**
    * 작성자 : mhcho
    * 작성일 : 2006.04.11
    * 설명   : 일시중지보류 복구요청 수정시 update 한다 . 
    * 기타   : 
    */
   public void updateRowForGY050202(ArrayList alist, Connection conn) throws Exception {

      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWUF1Vo                    vo       = new TBEBWUF1Vo();     

      try {
         sqlBuff.append("UPDATE EVLADM.TBEBWUF1 SET \n");          
         sqlBuff.append("  CEBWUUPN=?,      \n");
         sqlBuff.append("  CEBWUCST=?,      \n");
         sqlBuff.append("  CEBWUPJT=?,      \n");
         sqlBuff.append("  CEBWUHGB=?,      \n");
         sqlBuff.append("  CEBWUHNO=?,      \n");
         sqlBuff.append("  CEBWUSEQ=?,      \n");
         sqlBuff.append("  CEBWUGND=?,      \n");
         sqlBuff.append("  CEBWUSTA=?,      \n");
         sqlBuff.append("  CEBWUJHD=?,      \n");
         sqlBuff.append("  CEBWUSSA=?,      \n");
         sqlBuff.append("  CEBWUREQ=?,      \n");
         sqlBuff.append("  CEBWURLT=?,      \n");
         sqlBuff.append("  CEBWURMK=?,      \n");
         sqlBuff.append("  CEBWUSCP=?,      \n");
         sqlBuff.append("  CEBWUYMD=?,      \n");         
         sqlBuff.append("  CEBWUHST=?       \n");             
         sqlBuff.append("WHERE CEBWUGBN=?   \n");                   
         sqlBuff.append("  AND CEBWUWYM=?   \n");                              
         sqlBuff.append("  AND CEBWUISR=?   \n");     
         sqlBuff.append("  AND CEBWUUPN=?   \n");    
         sqlBuff.append("  AND CEBWUCST=?   \n");    
         sqlBuff.append("  AND CEBWUPJT=?   \n");    
         sqlBuff.append("  AND CEBWUHGB=?   \n");    
         sqlBuff.append("  AND CEBWUHNO=?   \n");
         sqlBuff.append("  AND CEBWUSEQ=?   \n");                                 
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < alist.size(); i++) {
            int idxparam = 1;
            vo = new TBEBWUF1Vo();
            vo = (TBEBWUF1Vo) alist.get(i);      

            pstmt.setString(idxparam++, vo.getCebwuupn());
            pstmt.setString(idxparam++, vo.getCebwucst());
            pstmt.setString(idxparam++, vo.getCebwupjt());
            pstmt.setString(idxparam++, vo.getCebwuhgb());
            pstmt.setString(idxparam++, vo.getCebwuhno());
            pstmt.setString(idxparam++, vo.getCebwuseq());
            pstmt.setString(idxparam++, vo.getCebwugnd());
            pstmt.setString(idxparam++, vo.getCebwusta());
            pstmt.setString(idxparam++, vo.getCebwujhd());
            pstmt.setString(idxparam++, vo.getCebwussa());
            pstmt.setString(idxparam++, vo.getCebwureq());
            pstmt.setString(idxparam++, vo.getCebwurlt());
            pstmt.setString(idxparam++, vo.getCebwurmk());
            pstmt.setString(idxparam++, vo.getCebwuscp());
            pstmt.setString(idxparam++, vo.getCebwuymd());            
            pstmt.setString(idxparam++, vo.getCebwuhst());
            pstmt.setString(idxparam++, vo.getCebwugbn());
            pstmt.setString(idxparam++, vo.getCebwuwym());
            pstmt.setString(idxparam++, vo.getCebwuisr());
            pstmt.setString(idxparam++, vo.getCebwuupn());
            pstmt.setString(idxparam++, vo.getCebwucst());
            pstmt.setString(idxparam++, vo.getCebwupjt());
            pstmt.setString(idxparam++, vo.getCebwuhgb());
            pstmt.setString(idxparam++, vo.getCebwuhno());
            pstmt.setString(idxparam++, vo.getCebwuseq());
  
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }              
         pstmt.executeBatch();      
   
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      }        
   } 
   
   /**
    * 작성자 : mhcho
    * 작성일 : 2006.04.15
    * 설명   : 일시중지보류 복구요청 (보수PM)접수시 update 한다 . 
    * 기타   : 
    */
   public void updateRowForGY050506_PA5(ArrayList alist, Connection conn) throws Exception {

      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWUF1Vo                    vo       = new TBEBWUF1Vo();     

      try {
         sqlBuff.append("UPDATE EVLADM.TBEBWUF1 SET \n"); 
         sqlBuff.append("  CEBWUJHD=?,      \n");  
         sqlBuff.append("  CEBWUYMD=?       \n");  
         
         sqlBuff.append("WHERE CEBWUGBN=?   \n");                   
         sqlBuff.append("  AND CEBWUWYM=?   \n");                              
         sqlBuff.append("  AND CEBWUISR=?   \n");     
         sqlBuff.append("  AND CEBWUUPN=?   \n");    
         sqlBuff.append("  AND CEBWUCST=?   \n");    
         sqlBuff.append("  AND CEBWUPJT=?   \n");    
         sqlBuff.append("  AND CEBWUHGB=?   \n");    
         sqlBuff.append("  AND CEBWUHNO=?   \n");
         sqlBuff.append("  AND CEBWUSEQ=?   \n");                                 
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < alist.size(); i++) {
            int idxparam = 1;
            vo = new TBEBWUF1Vo();
            vo = (TBEBWUF1Vo) alist.get(i); 
            
            pstmt.setString(idxparam++, vo.getCebwujhd());            
            pstmt.setString(idxparam++, vo.getCebwuymd());                  
            
            pstmt.setString(idxparam++, vo.getCebwugbn());
            pstmt.setString(idxparam++, vo.getCebwuwym());
            pstmt.setString(idxparam++, vo.getCebwuisr());
            pstmt.setString(idxparam++, vo.getCebwuupn());
            pstmt.setString(idxparam++, vo.getCebwucst());
            pstmt.setString(idxparam++, vo.getCebwupjt());
            pstmt.setString(idxparam++, vo.getCebwuhgb());
            pstmt.setString(idxparam++, vo.getCebwuhno());
            pstmt.setString(idxparam++, vo.getCebwuseq());            
             
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }              
         pstmt.executeBatch();      
   
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      }        
   }     
   /**
    * 작성자 : mhcho
    * 작성일 : 2006.04.15
    * 설명   : 일시중지보류 복구요청 (고객지원부)승인시 update 한다 . 
    * 기타   : 
    */
   public void updateRowForGY050506_JA6(ArrayList alist, Connection conn) throws Exception {

      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWUF1Vo                    vo       = new TBEBWUF1Vo();  
      ComMethodVo                   comVo;   

      try {
         sqlBuff.append("UPDATE EVLADM.TBEBWUF1 SET \n");  
         sqlBuff.append("  CEBWUSEQ=?,      \n");     
         sqlBuff.append("  CEBWUGND=?,      \n"); 
         sqlBuff.append("  CEBWUJHD=?,      \n"); 
         sqlBuff.append("  CEBWUYMD=?       \n");          
         sqlBuff.append("WHERE CEBWUGBN=?   \n");                   
         sqlBuff.append("  AND CEBWUWYM=?   \n");                              
         sqlBuff.append("  AND CEBWUISR=?   \n");     
         sqlBuff.append("  AND CEBWUUPN=?   \n");    
         sqlBuff.append("  AND CEBWUCST=?   \n");    
         sqlBuff.append("  AND CEBWUPJT=?   \n");    
         sqlBuff.append("  AND CEBWUHGB=?   \n");    
         sqlBuff.append("  AND CEBWUHNO=?   \n");
         sqlBuff.append("  AND CEBWUSEQ=?   \n");                                 
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         for (int i = 0; i < alist.size(); i++) {
            int idxparam = 1;
            vo = new TBEBWUF1Vo();
            vo = (TBEBWUF1Vo) alist.get(i);   
            
            pstmt.setString(idxparam++, vo.getCebwuseq());
            pstmt.setString(idxparam++, vo.getCebwugnd());            
            pstmt.setString(idxparam++, vo.getCebwujhd());            
            pstmt.setString(idxparam++, vo.getCebwuymd());
            
            pstmt.setString(idxparam++, vo.getCebwugbn());
            pstmt.setString(idxparam++, vo.getCebwuwym());
            pstmt.setString(idxparam++, vo.getCebwuisr());
            pstmt.setString(idxparam++, vo.getCebwuupn());
            pstmt.setString(idxparam++, vo.getCebwucst());
            pstmt.setString(idxparam++, vo.getCebwupjt());
            pstmt.setString(idxparam++, vo.getCebwuhgb());
            pstmt.setString(idxparam++, vo.getCebwuhno());
            pstmt.setString(idxparam++, vo.getCebwuseq());            
             
            pstmt.addBatch();
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
            
            comVo = new ComMethodVo();
                  
            comVo.setDataId("53");            
            comVo.setJobGubun("R");            

            if(vo.getCebwugnd().equals("C") || vo.getCebwugnd().equals("D")){              //유상
               comVo.setUpn(vo.getCebwuupn());
               comVo.setCst(vo.getCebwucst());               
            }else if(vo.getCebwugnd().equals("A") || vo.getCebwugnd().equals("B")){       //무상
               comVo.setUpn("ZZZZZ");
               comVo.setCst("ZZZ");               
            }else
               throw new BizException("발주종류가 정의 되지 않았습니다");

            comVo.setPjt(vo.getCebwupjt());
            comVo.setHgb(vo.getCebwuhgb());
            comVo.setHno(vo.getCebwuhno());
            comVo.setSeq(vo.getCebwuseq());
            comVo.setGbn(vo.getCebwugbn());
            comVo.setWym(vo.getCebwuwym());
            comVo.setIsr(vo.getCebwuisr());
            comVo.setFirstUserId(vo.getFirstUserId());
            comVo.setStopDate(vo.getStopDate());            
            
//            ComMethodDao.SetBXRBXL(comVo);                       
         }              
         pstmt.executeBatch();      
      }catch (BizException be) {
            throw be; 
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      }        
   }                                                             
}
