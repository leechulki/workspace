/**
 * 파 일 명 : TBEBWZF1Dao.java
 * 설    명 : 점검이관 정보(TBEBWZF1)  Dao
 * 작 성 자 : moono
 * 작 성 일 : 2006-04-17 오후  02:50:31
 * 변경내역 :
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
    * 작성자: jkkoo
    * 작성일: 2006-05-12
    * 설  명: 기성비/매축계획 생성하기 위한 점검이관 정보 가져오기.
    * 기  타: 공통메소드에서 점검이관정보 Key정보를 가지고 관련 항목들을 조회 한다.
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
      sqlBuff.append("     CEBWZUPN,                  \n"); // (통합)Proj. No 
      sqlBuff.append("     CEBWZCST,                  \n"); // 거래처         
      sqlBuff.append("     CEBWZPJT,                  \n"); // (원)Proj. No   
      sqlBuff.append("     CEBWZHGB,                  \n"); // 호기구분       
      sqlBuff.append("     CEBWZHNO,                  \n"); // 호기번호       
      sqlBuff.append("     CEBWZIGM,                  \n"); // 이관예정년월   
      sqlBuff.append("     CEBWZGND,                  \n"); // 발주(계약)종류 
      sqlBuff.append("     CEBWZCHB,                  \n"); // 보수협력사     
      sqlBuff.append("     CEBWZGBB,                  \n"); // 시내외구분     
      sqlBuff.append("     CEBWZGHB,                  \n"); // 직영/협력사구분
      sqlBuff.append("     CEBWZIKD,                  \n"); // 이관등록일자   
      sqlBuff.append("     CEBWZIKJ,                  \n"); // 이관등록자     
      sqlBuff.append("     CEBWZCHA,                  \n"); // 보수협력사     
      sqlBuff.append("     CEBWZGBA,                  \n"); // 시내외구분     
      sqlBuff.append("     CEBWZGHA,                  \n"); // 직영/협력사구분
      sqlBuff.append("     CEBWZIJU,                  \n"); // 이관일자       
      sqlBuff.append("     CEBWZIHT,                  \n"); // 이관형태       
      sqlBuff.append("     CEBWZRLT,                  \n"); // 작업결과       
      sqlBuff.append("     CEBWZRSN                   \n"); // 작업사유       

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
            rtnVo.setCebwzupn(rs.getString("CEBWZUPN")); // (통합)Proj. No   
            rtnVo.setCebwzcst(rs.getString("CEBWZCST")); // 거래처           
            rtnVo.setCebwzpjt(rs.getString("CEBWZPJT")); // (원)Proj. No     
            rtnVo.setCebwzhgb(rs.getString("CEBWZHGB")); // 호기구분         
            rtnVo.setCebwzhno(rs.getString("CEBWZHNO")); // 호기번호         
            rtnVo.setCebwzigm(rs.getString("CEBWZIGM")); // 이관예정년월     
            rtnVo.setCebwzgnd(rs.getString("CEBWZGND")); // 발주(계약)종류   
            rtnVo.setCebwzchb(rs.getString("CEBWZCHB")); // 보수협력사       
            rtnVo.setCebwzgbb(rs.getString("CEBWZGBB")); // 시내외구분       
            rtnVo.setCebwzghb(rs.getString("CEBWZGHB")); // 직영/협력사구분  
            rtnVo.setCebwzikd(rs.getString("CEBWZIKD")); // 이관등록일자     
            rtnVo.setCebwzikj(rs.getString("CEBWZIKJ")); // 이관등록자       
            rtnVo.setCebwzcha(rs.getString("CEBWZCHA")); // 보수협력사       
            rtnVo.setCebwzgba(rs.getString("CEBWZGBA")); // 시내외구분       
            rtnVo.setCebwzgha(rs.getString("CEBWZGHA")); // 직영/협력사구분  
            rtnVo.setCebwziju(rs.getString("CEBWZIJU")); // 이관일자         
            rtnVo.setCebwziht(rs.getString("CEBWZIHT")); // 이관형태         
            rtnVo.setCebwzrlt(rs.getString("CEBWZRLT")); // 작업결과         
            rtnVo.setCebwzrsn(rs.getString("CEBWZRSN")); // 작업사유       
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
    * 작성자 : mhcho
    * 작성일 : 2006.04.18
    * 설명   : 점검이관예약 및 확인 insert 한다 . 
    * 기타   : 
    */
   public void insertRowForGY040101(ArrayList alist, Connection conn) throws Exception {
      int rtnCd   = 0;
      LoggablePreparedStatement     pstmt    = null ;      
      StringBuffer                  sqlBuff  = new StringBuffer(); 
      TBEBWZF1Vo                    vo       = new TBEBWZF1Vo();     

      try {
         sqlBuff.append("INSERT  INTO  EVLADM.TBEBWZF1 ( \n");            
         sqlBuff.append("  CEBWZUPN,      \n");              // 통합ProjNo                
         sqlBuff.append("  CEBWZCST,      \n");              // 거래처                
         sqlBuff.append("  CEBWZPJT,      \n");              // ProjNo                    
         sqlBuff.append("  CEBWZHGB,      \n");              // 호기구분                
         sqlBuff.append("  CEBWZHNO,      \n");              // 호기번호        
         sqlBuff.append("  CEBWZIGM,      \n");              // 이관예정년월                  
         sqlBuff.append("  CEBWZGND,      \n");              // 발주종류          
         sqlBuff.append("  CEBWZCHB,      \n");              // 보수협력사              
         sqlBuff.append("  CEBWZGBB,      \n");              // 시내외구분                
         sqlBuff.append("  CEBWZGHB,      \n");              // 업체구분                
         sqlBuff.append("  CEBWZIKD,      \n");              // 이관등록일자                
         sqlBuff.append("  CEBWZIKJ,      \n");              // 이관등록자                  
         sqlBuff.append("  CEBWZCHA,      \n");              // 이관후보수협력사                
         sqlBuff.append("  CEBWZGBA,      \n");              // 이관후시내외구분                  
         sqlBuff.append("  CEBWZGHA,      \n");              // 이관후 직영/협력사구분                
         sqlBuff.append("  CEBWZIJU,      \n");              // 이관일자        
         sqlBuff.append("  CEBWZIHT)      \n");              // 이관형태                 
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
            if(rtnCd!=1) throw new BizException("저장중 오류");
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }

         
      }catch(BizException e){
         throw new BizException("저장중 오류");
      }catch(Exception e){         
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      } 
      
   }   
   
   /**
    * 작성자 : mhcho
    * 작성일 : 2006.04.18
    * 설명   : 점검이관 삭제 시 delete 한다 . 
    * 기타   : 
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
            if(rtnCd!=1) throw new BizException("삭제중 오류발생.");
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         }             

      }catch(BizException e){
         throw new BizException("삭제중 오류발생.");   
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }finally{
            try{pstmt.close();}catch(Exception e){}
      }
   }         
                                        
}
