/**
 * 파 일 명 : TBEBXRF1Dao.java
 * 설    명 : 
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-04-28 오후 1:57:45
 * 변경내역 :
 *
 * struts-Config : 
 * 
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
import com.helco.xf.cs12.evladm.vo.TBEBXRF1Vo;

//import org.apache.log4j.Logger;

public class TBEBXRF1Dao extends AbstractBusinessService implements FrwCrudDAO
{
//   static Logger logger = Logger.getLogger(TBEBXRF1Dao.class);
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:57:45
	 * 설  명: 
	 * 기  타:
	 */
	public void insert(Object vo) throws Exception
	{
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:57:45
	 * 설  명: 
	 * 기  타:
	 */
	public void insert(Object vo, String subMethodFlag) throws Exception
	{
      Connection conn = null;
      ArrayList alist = (ArrayList) vo;
      TBEBXRF1Vo vo1;
      
      try
      {
         if (alist.size() > 0)
         { 
//            conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
        	vo1 = new TBEBXRF1Vo();
            vo1 = (TBEBXRF1Vo) alist.get(0);
        	String db_con = Utillity.getSapJndi(vo1.getMandt());
        	
        	conn = getConnection(db_con);
            if ("ComMethodForAB".equals(subMethodFlag)) // 2006.04.28 jkkoo
               insertRowComMethodForAB(alist, conn);
            if ("ComMethodForC".equals(subMethodFlag)) // 2006.04.28 jkkoo
               insertRowComMethodForC(alist, conn);
            if ("ComMethodForD".equals(subMethodFlag)) // 2006.05.10 jkkoo
               insertRowComMethodForD(alist, conn);       
            if ("ComMethodForETC".equals(subMethodFlag)) // 2006.05.10 jkkoo - 인원상주
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
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:57:45
	 * 설  명: 
	 * 기  타:
	 */
	public ArrayList selectList(Object searchCondVO) throws Exception
	{
      
      TBEBXRF1Vo                 vo       = (TBEBXRF1Vo) searchCondVO;
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      Connection                 conn     = null;
      
      TBEBXRF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     CEBXRPJT,                  \n");
      sqlBuff.append("     CEBXRHGB,                  \n");
      sqlBuff.append("     CEBXRHNO,                  \n");
      sqlBuff.append("     CEBXRSEQ,                  \n");
      sqlBuff.append("     CEBXRGND,                  \n");
      sqlBuff.append("     CEBXRJYM,                  \n");
      sqlBuff.append("     CEBXRISR,                  \n");
      sqlBuff.append("     CEBXRBDA,                  \n");
      sqlBuff.append("     CEBXRMNO,                  \n");
      sqlBuff.append("     CEBXRHTY,                  \n");
      sqlBuff.append("     CEBXRARA,                  \n");
      sqlBuff.append("     CEBXRJUJ,                  \n");
      sqlBuff.append("     CEBXRBSU,                  \n");
      sqlBuff.append("     CEBXRGBU,                  \n");
      sqlBuff.append("     CEBXRAGB,                  \n");
      sqlBuff.append("     CEBXRABG,                  \n");
      sqlBuff.append("     CEBXRPST,                  \n");
      sqlBuff.append("     CEBXRGGB,                  \n");
      sqlBuff.append("     CEBXRGYM,                  \n");
      sqlBuff.append("     CEBXRMGB,                  \n");
      sqlBuff.append("     CEBXRMSA,                  \n");
      sqlBuff.append("     CEBXRMDT,                  \n");
      sqlBuff.append("     CEBXRJYD,                  \n");
      sqlBuff.append("     CEBXRJSD,                  \n");
      sqlBuff.append("     CEBXRJYQ,                  \n");
      sqlBuff.append("     CEBXRIDQ,                  \n");
      sqlBuff.append("     CEBXRBAM                   \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     EVLADM.TBEBXRF1            \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   CEBXRPJT = ?            \n");
      sqlBuff.append("  AND   CEBXRHGB = ?            \n");
      sqlBuff.append("  AND   CEBXRHNO = ?            \n");
      //sqlBuff.append("  AND   CEBXRSEQ = ?            \n");
      sqlBuff.append("  AND   CEBXRGND = ?            \n");
      sqlBuff.append("  AND   CEBXRJYM >= ?            \n");
      
      try
      {
         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getCebxrpjt());
         pstmt.setString(idxparam++, vo.getCebxrhgb());
         pstmt.setString(idxparam++, vo.getCebxrhno());
         //pstmt.setString(idxparam++, vo.getCebxrseq());
         pstmt.setString(idxparam++, vo.getCebxrgnd());
         pstmt.setString(idxparam++, vo.getCebxrjym());
         
         
//         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         

         while (rs.next())
         {
            rtnVo = new TBEBXRF1Vo();
            rtnVo.setCebxrpjt(rs.getString("CEBXRPJT"));
            rtnVo.setCebxrhgb(rs.getString("CEBXRHGB"));
            rtnVo.setCebxrhno(rs.getString("CEBXRHNO"));
            rtnVo.setCebxrseq(rs.getString("CEBXRSEQ"));
            rtnVo.setCebxrgnd(rs.getString("CEBXRGND"));
            rtnVo.setCebxrjym(rs.getString("CEBXRJYM"));
            rtnVo.setCebxrisr(rs.getString("CEBXRISR"));
            rtnVo.setCebxrbda(rs.getString("CEBXRBDA"));

            rtnVo.setCebxrmno(rs.getString("CEBXRMNO"));//8.MNO               
            rtnVo.setCebxrhty(rs.getString("CEBXRHTY"));//9.고속기종여부      
            rtnVo.setCebxrara(rs.getString("CEBXRARA"));//10.대지역           
            rtnVo.setCebxrjuj(rs.getString("CEBXRJUJ"));//11.주점검자         
            rtnVo.setCebxrbsu(rs.getString("CEBXRBSU"));//12.보수협력사        
            rtnVo.setCebxrgbu(rs.getString("CEBXRGBU"));//13.직영협력사여부   
            rtnVo.setCebxragb(rs.getString("CEBXRAGB"));//14.시내외구분       
            rtnVo.setCebxrabg(rs.getString("CEBXRABG"));//15.빌딩구분         
            rtnVo.setCebxrpst(rs.getString("CEBXRPST"));//16.진행상태  
            rtnVo.setCebxrggb(rs.getString("CEBXRGGB"));//27.지급구분         
            rtnVo.setCebxrgym(rs.getString("CEBXRGYM"));//28.지급년월          
            rtnVo.setCebxrmgb(rs.getString("CEBXRMGB"));//29.미지급구분       
            rtnVo.setCebxrmsa(rs.getString("CEBXRMSA"));//30.미지급사유       
            rtnVo.setCebxrmdt(rs.getString("CEBXRMDT"));//31.미지급등록일
            rtnVo.setCebxrjyd(rs.getString("CEBXRJYD"));//24.지급요청일
            rtnVo.setCebxrjsd(rs.getString("CEBXRJSD"));//21.지급대상생성일 
            rtnVo.setCebxrjyq(rs.getString("CEBXRJYQ"));
            rtnVo.setCebxridq(rs.getString("CEBXRIDQ"));
            rtnVo.setCebxrbam(rs.getString("CEBXRBAM"));

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
            DBUtil.close(rs, pstmt, conn);
         }
         catch (Exception e)
         {
         }
      }
      return list;
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:57:45
	 * 설  명: 
	 * 기  타:
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
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:57:45
	 * 설  명: 
	 * 기  타:
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
            //conn  = getConnection();
            if ("ComMethod".equals(subMethodFlag)) // 2006.05.11 jkkoo(기 등록된 기성비 가져오기)
               rtnList = selectRowComMethodForCancel(alist, conn);             
            if ("ComMethod_stop".equals(subMethodFlag)) // 2009.04.21 jkkoo(기 등록된 기성비 가져오기)
                rtnList = selectRowComMethodForCancelStop(alist, conn);             
            if ("ComMethod_silpae".equals(subMethodFlag)) // 2008.12.11 jhlee(기 등록된 기성비 가져오기)
                rtnList = selectRowComMethodForCancelSilPae(alist, conn);             
            if ("ComMethodPre".equals(subMethodFlag)) // 2006.05.18 jkkoo(기 등록된 기성비 가져오기)
               rtnList = selectRowComMethodForCancelPre(alist, conn);                      
            if ("ComMethodForRestore".equals(subMethodFlag)) // 2008.09.02 jhlee(보류중지 데이터 가져오기)
                rtnList = selectRowComMethodForRestore(alist, conn);                      
            if ("ComMethodForRestore_stop".equals(subMethodFlag)) // 2009.04.21 jhlee(보류중지 데이터 가져오기)
                rtnList = selectRowComMethodForRestoreStop(alist, conn);                      
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
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:57:45
	 * 설  명: 
	 * 기  타:
	 */
	public void deleteList(Object pkList, String subMethodFlag) throws Exception
	{
		Connection  conn  = null ;
	  ArrayList alist = (ArrayList) pkList;
	  try{
		 if(alist.size() > 0 ) {
			conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);	
			
				if("GY020105".equals(subMethodFlag))  // 2006.08.25
					deleteRowForGY020105(alist, conn); 
			   if("GY020103_04".equals(subMethodFlag))  // 2006.08.28
					deleteRowForGY020103_04(alist, conn); 
		     
		 }
	  }catch(Exception e){
		 e.printStackTrace();
		 throw e;
	  }finally{
		 DBUtil.close(null, null, conn);
	  }
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:57:45
	 * 설  명: 
	 * 기  타:
	 */
	public void update(Object vo) throws Exception
	{
	}
	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:57:45
	 * 설  명: 
	 * 기  타:
	 */
	public void update(Object vo, String subMethodFlag) throws Exception
	{
      Connection conn = null;
      TBEBXRF1Vo vo1;
      ArrayList alist = (ArrayList) vo;
      try
      {
         if (alist.size() > 0)
         {
//            conn = DBUtil.getConnection(DBConstants.EVLADMPOOL);
            //conn = getConnection();
        	vo1 = new TBEBXRF1Vo();
     		vo1 = (TBEBXRF1Vo) alist.get(0);
     		String db_con = Utillity.getSapJndi(vo1.getMandt());
			conn = getConnection(db_con);
			
            if ("ComMethodForCancelForA".equals(subMethodFlag)) // 
               updateRowComMethod1(alist, conn);
            if ("ComMethodForCancelForC".equals(subMethodFlag)) // 
               updateRowComMethod1(alist, conn);
            if ("ComMethodForCancelForChg".equals(subMethodFlag)) // 계약 변경에 의한 취소
               updateRowComMethod2(alist, conn);               
            if ("ComMethodForCancelForETC1".equals(subMethodFlag)) // 
               ComMethodForCancelForETC1(alist, conn);       
            if ("ComMethodForCancelForETC2".equals(subMethodFlag)) // 
               ComMethodForCancelForETC2(alist, conn);    
            if ("ComMethodForCancelForETC3".equals(subMethodFlag)) // 
               ComMethodForCancelForETC3(alist, conn);   
            if ("ComMethodForCancelForETC4".equals(subMethodFlag)) // 
                ComMethodForCancelForETC4(alist, conn);       
			if ("KS030109_01".equals(subMethodFlag)) // 2006.05.19 기성비지급예정현황에서의 발송 처리
			   updateRowForKS030109_01(alist, conn); 
			if ("KS030109_02".equals(subMethodFlag)) // 2006.05.19 기성비지급예정현황에서의 접수 처리
			   updateRowForKS030109_02(alist, conn);    
			if ("KS030109_03".equals(subMethodFlag)) // 2006.05.19 기성비지급예정현황에서의 승인(서울) 처리
			   updateRowForKS030109_03(alist, conn, "1");     
			if ("KS030109_04".equals(subMethodFlag)) // 2006.05.19 기성비지급예정현황에서의 승인(지방) 처리
			   updateRowForKS030109_03(alist, conn, "2");     
			if ("KS030109_05".equals(subMethodFlag)) // 2006.05.19 기성비지급예정현황에서의 승인(전체) 처리
			   updateRowForKS030109_03(alist, conn, "3");    
            if ("KS030102_01".equals(subMethodFlag)) // 2006.05.20 기성비지급예정내역에서의 발송 처리
               updateRowForKS030102_01(alist, conn); 
            if ("KS030102_02".equals(subMethodFlag)) // 2006.05.20 기성비지급예정내역에서의 접수 처리(지방사무소)
               updateRowForKS030102_02(alist, conn);    
            if ("KS030102_03".equals(subMethodFlag)) // 2006.05.20 기성비지급예정내역에서의 승인1 처리(고객지원부)
               updateRowForKS030102_03(alist, conn);    
            if ("KS030102_04".equals(subMethodFlag)) // 2006.05.20 기성비지급예정내역에서의 승인2 처리(지방사무소)
               updateRowForKS030102_04(alist, conn);        
			if ("MC010101".equals(subMethodFlag)) //2006.05.24 jhlee 
				updateRowForMC010101(alist, conn);      
			if ("MC010201".equals(subMethodFlag)) //2006.09.20 jhlee 
				updateRowForMC010201(alist, conn);      
			if("GY090101".equals(subMethodFlag)) // 2006.08.11 jhlee
				updateRowForGY090101(alist, conn, "GY090101");   
			if("GY090101_ALL".equals(subMethodFlag)) // 2006.08.11 jhlee
				updateRowForGY090101(alist, conn, "GY090101_ALL");   
			if("GY090301".equals(subMethodFlag)) // 2006.08.16 jhlee
				updateRowForGY090301(alist, conn);
			if("GY100101".equals(subMethodFlag)) // 2006.09.18 ykchoi
				updateRowForGY100101(alist, conn);				
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
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 1:57:45
	 * 설  명: 
	 * 기  타:
	 */
	public int multiSaveForGrid(Object iobj, Object uobj, Object dobj) throws Exception
	{
		return 0;
	}
   /**
    * 작성자 : jkkoo
    * 작성일 : 2006.04.28
    * 설  명 : 공통 메소드에서 각 업무ID에 따라 Insert
    * 기  타 : 
    */
   public void insertRowComMethodForAB(ArrayList list, Connection conn) throws Exception
   {
      
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();

      try
      {
          sqlBuff.append("INSERT  INTO  SAPHEE.ZCST136 \n");
          sqlBuff.append("        (                    \n");
          sqlBuff.append("           MANDT ,           \n");
          sqlBuff.append("           CS136_PJT ,       \n");
          sqlBuff.append("           CS136_HNO ,       \n");
          sqlBuff.append("           CS136_SEQ ,       \n");
          sqlBuff.append("           CS136_GND ,       \n");
          sqlBuff.append("           CS136_JYM ,       \n");
          sqlBuff.append("           CS136_ISR ,       \n");
          sqlBuff.append("           CS136_HTY ,       \n");
          sqlBuff.append("           CS136_ARA ,       \n");
          sqlBuff.append("           CS136_BSU ,       \n");
          sqlBuff.append("           CS136_GBU ,       \n");
          sqlBuff.append("           CS136_AGB ,       \n");
          sqlBuff.append("           CS136_ABG ,       \n");
          sqlBuff.append("           CS136_PST ,       \n");
          sqlBuff.append("           CS136_FDT ,       \n");
          sqlBuff.append("           CS136_TOD ,       \n");
          sqlBuff.append("           CS136_JDQ ,       \n");
          sqlBuff.append("           CS136_BDA ,       \n");
          sqlBuff.append("           CS136_JSD ,       \n");
          sqlBuff.append("           CS136_JYQ ,       \n");
          sqlBuff.append("           CS136_BYA ,       \n");
          sqlBuff.append("           CS136_JYD ,       \n");
          sqlBuff.append("           CS136_IDQ ,       \n");
          sqlBuff.append("           CS136_BAM ,       \n");
          sqlBuff.append("           CS136_GGB ,       \n");
          sqlBuff.append("           CS136_GYM ,       \n");
          sqlBuff.append("           CS136_MGB ,       \n");
          sqlBuff.append("           CS136_MSA ,       \n");
          sqlBuff.append("           CS136_MDT ,       \n");
          sqlBuff.append("           CS136_UPN ,       \n");
          sqlBuff.append("           CS136_PLT ,       \n");
          sqlBuff.append("           CS136_MBS ,       \n");
          sqlBuff.append("           CS136_GNO ,       \n");
          sqlBuff.append("           CS136_BDGBN,      \n");
          sqlBuff.append("           CRDAT,            \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
          sqlBuff.append("           CRTIM             \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
          sqlBuff.append("        )                    \n");
          sqlBuff.append("VALUES  (                    \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?        \n");
          sqlBuff.append("        )                    \n");

         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         TBEBXRF1Vo vo;
			for (int i = 0; i < list.size(); i++)
			{
				int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            
            String isr = getIsr(vo, conn);
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, isr);
            pstmt.setString(idxparam++, vo.getCebxrhty());
            pstmt.setString(idxparam++, vo.getCebxrara());
            pstmt.setString(idxparam++, vo.getCebxrbsu());
            pstmt.setString(idxparam++, vo.getCebxrgbu());
            pstmt.setString(idxparam++, vo.getCebxragb());
            pstmt.setString(idxparam++, vo.getCebxrabg());
            pstmt.setString(idxparam++, vo.getCebxrpst());
            pstmt.setString(idxparam++, vo.getCebxrfdt());
            pstmt.setString(idxparam++, vo.getCebxrtod());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjdq()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbda()));
            pstmt.setString(idxparam++, vo.getCebxrjsd());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjyq()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbya()));
            pstmt.setString(idxparam++, vo.getCebxrjyd());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxridq()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbam()));
            pstmt.setString(idxparam++, vo.getCebxrggb());
            pstmt.setString(idxparam++, vo.getCebxrgym());
            pstmt.setString(idxparam++, vo.getCebxrmgb());
            pstmt.setString(idxparam++, vo.getCebxrmsa());
            pstmt.setString(idxparam++, vo.getCebxrmdt());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            if(vo.getCebxrhno().substring(0, 1).equals("J")) { //주차일 경우 PLT수를  셋팅
            	pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrplt()));
            } else {
            	pstmt.setDouble(idxparam++, 0);
            }
            pstmt.setString(idxparam++, "Y"); //무상
            pstmt.setString(idxparam++, vo.getGno());
            pstmt.setString(idxparam++, vo.getCebxrbdgbn());
            
            //===========================생성일자 및 생성일시 추가 20201130 Han.JH=============================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//CRDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//CRTIM. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            //=======================================================================================            
            
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.04.28
    * 설  명 : 공통 메소드에서 각 업무ID에 따라 Insert
    * 기  타 : 
    */
   public void insertRowComMethodForC(ArrayList list, Connection conn) throws Exception
   {
      
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();

      try
      {
          sqlBuff.append("INSERT  INTO  SAPHEE.ZCST136 \n");
          sqlBuff.append("        (                    \n");
          sqlBuff.append("           MANDT ,           \n");
          sqlBuff.append("           CS136_PJT ,       \n");
          sqlBuff.append("           CS136_HNO ,       \n");
          sqlBuff.append("           CS136_SEQ ,       \n");
          sqlBuff.append("           CS136_GND ,       \n");
          sqlBuff.append("           CS136_JYM ,       \n");
          sqlBuff.append("           CS136_ISR ,       \n");
          sqlBuff.append("           CS136_UPN ,       \n");
          sqlBuff.append("           CS136_CST ,       \n");
          sqlBuff.append("           CS136_HTY ,       \n");
          sqlBuff.append("           CS136_ARA ,       \n");
          sqlBuff.append("           CS136_BSU ,       \n");
          sqlBuff.append("           CS136_GBU ,       \n");
          sqlBuff.append("           CS136_AGB ,       \n");
          sqlBuff.append("           CS136_ABG ,       \n");
          sqlBuff.append("           CS136_RGB ,       \n");
          sqlBuff.append("           CS136_PST ,       \n");
          sqlBuff.append("           CS136_FDT ,       \n");
          sqlBuff.append("           CS136_TOD ,       \n");
          sqlBuff.append("           CS136_JDQ ,       \n");
          sqlBuff.append("           CS136_IDQ ,       \n");
          sqlBuff.append("           CS136_JDJ ,       \n");
          sqlBuff.append("           CS136_BDA ,       \n");
          sqlBuff.append("           CS136_HDA ,       \n");
          sqlBuff.append("           CS136_DDA ,       \n");
          sqlBuff.append("           CS136_CPR ,       \n");
          sqlBuff.append("           CS136_JSD ,       \n");
          sqlBuff.append("           CS136_JYQ ,       \n");
          sqlBuff.append("           CS136_JYC ,       \n");
          sqlBuff.append("           CS136_BYA ,       \n");
          sqlBuff.append("           CS136_HYA ,       \n");
          sqlBuff.append("           CS136_DYA ,       \n");
          sqlBuff.append("           CS136_IS4 ,       \n");
          sqlBuff.append("           CS136_JYD ,       \n");
          sqlBuff.append("           CS136_GGB ,       \n");
          sqlBuff.append("           CS136_GYM ,       \n");
          sqlBuff.append("           CS136_MGB ,       \n");
          sqlBuff.append("           CS136_MSA ,       \n");
          sqlBuff.append("           CS136_MDT ,       \n");
          sqlBuff.append("           CS136_BAM ,       \n");
          sqlBuff.append("           CS136_HAM ,       \n");
          sqlBuff.append("           CS136_DAM ,       \n");
          sqlBuff.append("           CS136_IY4 ,       \n");
          sqlBuff.append("           CS136_IS1 ,       \n");
          sqlBuff.append("           CS136_IY1 ,       \n");
          sqlBuff.append("           CS136_BPD ,       \n");
          sqlBuff.append("           CS136_JYJ ,       \n");
          sqlBuff.append("           CS136_IS3 ,       \n");
          sqlBuff.append("           CS136_IY3 ,       \n");
          sqlBuff.append("           CS136_GNO ,       \n");
          sqlBuff.append("           CS136_PLT ,       \n");
          sqlBuff.append("           CS136_BDGBN,      \n");
          sqlBuff.append("           CS136_ACDA,       \n");
          sqlBuff.append("           CS136_ACYA,       \n");
          sqlBuff.append("           CS136_ACAM,       \n");
          //===========================생성일자 및 생성일시 추가 20201130 Han.JH=============================
          sqlBuff.append("           CRDAT,            \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
          sqlBuff.append("           CRTIM             \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
          //=======================================================================================
          sqlBuff.append("        )                    \n");
          sqlBuff.append("VALUES  (                    \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
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
         
         TBEBXRF1Vo vo;
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            
            String isr = getIsr(vo, conn);
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, isr);             
            pstmt.setString(idxparam++, vo.getCebxrupn());
            pstmt.setString(idxparam++, vo.getCebxrcst());
            pstmt.setString(idxparam++, vo.getCebxrhty());
            pstmt.setString(idxparam++, vo.getCebxrara());
            pstmt.setString(idxparam++, vo.getCebxrbsu());
            pstmt.setString(idxparam++, vo.getCebxrgbu());
            pstmt.setString(idxparam++, vo.getCebxragb());
            pstmt.setString(idxparam++, vo.getCebxrabg());
            pstmt.setString(idxparam++, vo.getCebxrrgb());
            pstmt.setString(idxparam++, vo.getCebxrpst());
            pstmt.setString(idxparam++, vo.getCebxrfdt());
            pstmt.setString(idxparam++, vo.getCebxrtod());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjdq()));
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjdq()));
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjdj()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbda()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrhda()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrdda()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrcpr()));
            pstmt.setString(idxparam++, vo.getCebxrjsd());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjyq()));
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjyc()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbya()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrhya()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrdya()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxris4())));
            pstmt.setString(idxparam++, vo.getCebxrjyd());
            pstmt.setString(idxparam++, vo.getCebxrggb());
            pstmt.setString(idxparam++, vo.getCebxrgym()); 
            pstmt.setString(idxparam++, vo.getCebxrmgb());
            pstmt.setString(idxparam++, vo.getCebxrmsa());
            pstmt.setString(idxparam++, vo.getCebxrmdt());
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbya()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrhya()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrdya()));
//            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxris4()));
//            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxris1()));
//            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxris1()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxris4())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxris1())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxris1())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxrbpd())));
            pstmt.setString(idxparam++, vo.getCebxrjyj());
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxris3())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxris3())));
            pstmt.setString(idxparam++, vo.getGno());
            if(vo.getCebxrhno().substring(0, 1).equals("J")) { //주차일 경우 PLT수를  셋팅
            	pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrplt()));
            } else {
            	pstmt.setDouble(idxparam++, 0);
            }
            pstmt.setString(idxparam++, vo.getCebxrbdgbn());
            //===========================공기청정기 추가 20200515 Han.JH==================================
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxracda()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxracya()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxracya())));
            //======================================================================================
            
            //===========================생성일자 및 생성일시 추가 20201130 Han.JH=============================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//CRDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//CRTIM. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            //=======================================================================================
            
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.10
    * 설  명 : 공통 메소드에서 각 업무ID에 따라 Insert
    * 기  타 : 
    */
   public void insertRowComMethodForD(ArrayList list, Connection conn) throws Exception
   {
      
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();

      try
      {
          sqlBuff.append("INSERT INTO SAPHEE.ZCST136    \n");
          sqlBuff.append("        (                     \n");
          sqlBuff.append("           MANDT     ,        \n");
          sqlBuff.append("           CS136_PJT ,        \n");
          sqlBuff.append("           CS136_HNO ,        \n");
          sqlBuff.append("           CS136_SEQ ,        \n");
          sqlBuff.append("           CS136_GND ,        \n");
          sqlBuff.append("           CS136_JYM ,        \n");
          sqlBuff.append("           CS136_ISR ,        \n");
          sqlBuff.append("           CS136_UPN ,        \n");
          sqlBuff.append("           CS136_CST ,        \n");
          sqlBuff.append("           CS136_HTY ,        \n");
          sqlBuff.append("           CS136_ARA ,        \n");
          sqlBuff.append("           CS136_BSU ,        \n");
          sqlBuff.append("           CS136_GBU ,        \n");
          sqlBuff.append("           CS136_AGB ,        \n");
          sqlBuff.append("           CS136_ABG ,        \n");
          sqlBuff.append("           CS136_RGB ,        \n");
          sqlBuff.append("           CS136_PST ,        \n");
          sqlBuff.append("           CS136_FDT ,        \n");
          sqlBuff.append("           CS136_TOD ,        \n");
          sqlBuff.append("           CS136_JDQ ,        \n");
          sqlBuff.append("           CS136_JDJ ,        \n");
          sqlBuff.append("           CS136_BDA ,        \n");
          sqlBuff.append("           CS136_HDA ,        \n");
          sqlBuff.append("           CS136_DDA ,        \n");
          sqlBuff.append("           CS136_CPR ,        \n");
          sqlBuff.append("           CS136_JSD ,        \n");
          sqlBuff.append("           CS136_JYQ ,        \n");
          sqlBuff.append("           CS136_JYC ,        \n");
          sqlBuff.append("           CS136_BYA ,        \n");
          sqlBuff.append("           CS136_HYA ,        \n");
          sqlBuff.append("           CS136_DYA ,        \n");
          sqlBuff.append("           CS136_IS1 ,        \n");
          sqlBuff.append("           CS136_IS2 ,        \n");
          sqlBuff.append("           CS136_IS3 ,        \n");
          sqlBuff.append("           CS136_IS4 ,        \n");
          sqlBuff.append("           CS136_JYD ,        \n");
          sqlBuff.append("           CS136_GGB ,        \n");
          sqlBuff.append("           CS136_GYM ,        \n");
          sqlBuff.append("           CS136_MGB ,        \n");
          sqlBuff.append("           CS136_MSA ,        \n");
          sqlBuff.append("           CS136_MDT ,        \n");
          sqlBuff.append("           CS136_BAM ,        \n");
          sqlBuff.append("           CS136_HAM ,        \n");
          sqlBuff.append("           CS136_DAM ,        \n");
          sqlBuff.append("           CS136_IY1 ,        \n");
          sqlBuff.append("           CS136_IY2 ,        \n");
          sqlBuff.append("           CS136_IY3 ,        \n");
          sqlBuff.append("           CS136_IY4 ,        \n");
          sqlBuff.append("           CS136_BPD ,        \n");
          sqlBuff.append("           CS136_JYJ ,        \n");
          sqlBuff.append("           CS136_IDQ ,        \n");
          sqlBuff.append("           CS136_GNO ,        \n");
          sqlBuff.append("           CS136_RET ,        \n");
          sqlBuff.append("           CS136_RMK ,        \n");
          sqlBuff.append("           CS136_IIS ,        \n");
          sqlBuff.append("           CS136_PLT ,        \n");
          sqlBuff.append("           CS136_BDGBN,       \n");
          sqlBuff.append("           CS136_ACDA,        \n");
          sqlBuff.append("           CS136_ACYA,        \n");
          sqlBuff.append("           CS136_ACAM,        \n");
          //===========================생성일자 및 생성일시 추가 20201130 Han.JH=============================
          sqlBuff.append("           CRDAT,            \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
          sqlBuff.append("           CRTIM             \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
          //=======================================================================================          
          sqlBuff.append("        )                     \n");
          sqlBuff.append("VALUES  (                     \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?,        \n");
          sqlBuff.append("           ?, ?, ?, ?, ?,     \n");
          sqlBuff.append("           ?, ?, ?, ?, ?      \n");
          sqlBuff.append("        )                     \n");
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         TBEBXRF1Vo vo;
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);

            String isr = getIsr(vo, conn);
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, isr);
            pstmt.setString(idxparam++, vo.getCebxrupn());
            pstmt.setString(idxparam++, vo.getCebxrcst());
            pstmt.setString(idxparam++, vo.getCebxrhty());
            pstmt.setString(idxparam++, vo.getCebxrara());
            pstmt.setString(idxparam++, vo.getCebxrbsu());
            pstmt.setString(idxparam++, vo.getCebxrgbu());
            pstmt.setString(idxparam++, vo.getCebxragb());
            pstmt.setString(idxparam++, vo.getCebxrabg());
            pstmt.setString(idxparam++, vo.getCebxrrgb());
            pstmt.setString(idxparam++, vo.getCebxrpst());
            pstmt.setString(idxparam++, vo.getCebxrfdt());
            pstmt.setString(idxparam++, vo.getCebxrtod());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjdq()));
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjdj()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbda()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrhda()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrdda()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrcpr()));
            pstmt.setString(idxparam++, vo.getCebxrjsd());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjyq()));
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjyc()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbya()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrhya()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrdya()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxris1()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxris2()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxris3()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxris4()));
            pstmt.setString(idxparam++, vo.getCebxrjyd());
            pstmt.setString(idxparam++, vo.getCebxrggb());
            pstmt.setString(idxparam++, vo.getCebxrgym());
            pstmt.setString(idxparam++, vo.getCebxrmgb());
            pstmt.setString(idxparam++, vo.getCebxrmsa());
            pstmt.setString(idxparam++, vo.getCebxrmdt());
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxrbya())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxrhya())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxrdya())));        
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxris1())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxris2())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxris3())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxris4())));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxrbpd())));
            pstmt.setString(idxparam++, vo.getCebxrjyj());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjyq()));
            pstmt.setString(idxparam++, vo.getGno());
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxrret())));
            pstmt.setString(idxparam++, CommonUtil.NullToBlank(vo.getCebxrrmk()));
            pstmt.setString(idxparam++, CommonUtil.NullToBlank(vo.getCebxriis()));
            if(vo.getCebxrhno().substring(0, 1).equals("J")) { //주차일 경우 PLT수를  셋팅
            	pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrplt()));
            } else {
            	pstmt.setDouble(idxparam++, 0);
            }
            pstmt.setString(idxparam++, vo.getCebxrbdgbn());
            
            //===========================공기청정기 추가 20200515 Han.JH==================================
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxracda()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxracya()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(CommonUtil.NullBlankToZero(vo.getCebxracya())));
            //======================================================================================
            
            //===========================생성일자 및 생성일시 추가 20201130 Han.JH=============================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//CRDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//CRTIM. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            //=======================================================================================            
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.10
    * 설  명 : 공통 메소드에서 각 업무ID에 따라 Insert
    * 기  타 : 인원상주
    */
   public void insertRowComMethodForETC(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();

      try
      {
          sqlBuff.append("INSERT INTO SAPHEE.ZCST136   \n");
          sqlBuff.append("        (                    \n");
          sqlBuff.append("           MANDT    ,        \n");
          sqlBuff.append("           CS136_PJT ,       \n");
          sqlBuff.append("           CS136_HNO ,       \n");
          sqlBuff.append("           CS136_SEQ ,       \n");
          sqlBuff.append("           CS136_GND ,       \n");
          sqlBuff.append("           CS136_JYM ,       \n");
          sqlBuff.append("           CS136_ISR ,       \n");
          sqlBuff.append("           CS136_UPN ,       \n");
          sqlBuff.append("           CS136_CST ,       \n");
          sqlBuff.append("           CS136_BSU ,       \n");
          sqlBuff.append("           CS136_GBU ,       \n");
          sqlBuff.append("           CS136_PST ,       \n");
          sqlBuff.append("           CS136_FDT ,       \n");
          sqlBuff.append("           CS136_TOD ,       \n");
          sqlBuff.append("           CS136_JDQ ,       \n");
          sqlBuff.append("           CS136_BDA ,       \n");
          sqlBuff.append("           CS136_JSD ,       \n");
          sqlBuff.append("           CS136_JYQ ,       \n");
          sqlBuff.append("           CS136_BYA ,       \n");
          sqlBuff.append("           CS136_JYD ,       \n");
          sqlBuff.append("           CS136_IDQ ,       \n");
          sqlBuff.append("           CS136_BAM ,       \n");
          sqlBuff.append("           CS136_GGB ,       \n");
          sqlBuff.append("           CS136_GYM ,       \n");
          sqlBuff.append("           CS136_MGB ,       \n");
          sqlBuff.append("           CS136_MSA ,       \n");
          sqlBuff.append("           CS136_MDT ,       \n");
          sqlBuff.append("           CS136_ARA ,       \n");
          sqlBuff.append("           CS136_IIS ,       \n");
          sqlBuff.append("           CS136_JDJ ,       \n");
          sqlBuff.append("           CS136_IDJ ,       \n");
          sqlBuff.append("           CS136_JYJ ,       \n");
          sqlBuff.append("           CS136_JYC ,       \n");
          sqlBuff.append("           CS136_GNO ,       \n");
          sqlBuff.append("           CS136_PLT ,       \n");
          sqlBuff.append("           CS136_BDGBN,      \n");
          //===========================생성일자 및 생성일시 추가 20201130 Han.JH=============================
          sqlBuff.append("           CRDAT,            \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
          sqlBuff.append("           CRTIM             \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
          //=======================================================================================          
          sqlBuff.append("        )                    \n");
          sqlBuff.append("VALUES  (                    \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?, ?, ?,       \n");
          sqlBuff.append("           ?, ?              \n");          
          sqlBuff.append("        )                    \n");
         
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         TBEBXRF1Vo vo;
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            
            String isr = getIsr(vo, conn);
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, isr);
            pstmt.setString(idxparam++, vo.getCebxrupn());
            pstmt.setString(idxparam++, vo.getCebxrcst());
            pstmt.setString(idxparam++, vo.getCebxrbsu());
            pstmt.setString(idxparam++, vo.getCebxrgbu());
            pstmt.setString(idxparam++, vo.getCebxrpst());
            pstmt.setString(idxparam++, vo.getCebxrfdt());
            pstmt.setString(idxparam++, vo.getCebxrtod());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjdq()));
//            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbda()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbda()).setScale(3, BigDecimal.ROUND_HALF_UP));
            pstmt.setString(idxparam++, vo.getCebxrjsd());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjyq()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbya()).setScale(3, BigDecimal.ROUND_HALF_UP));
            pstmt.setString(idxparam++, vo.getCebxrjyd());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxridq()));
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrbam()).setScale(3, BigDecimal.ROUND_HALF_UP));
            pstmt.setString(idxparam++, vo.getCebxrggb());  
            pstmt.setString(idxparam++, vo.getCebxrgym());   
            pstmt.setString(idxparam++, vo.getCebxrmgb());  
            pstmt.setString(idxparam++, vo.getCebxrmsa());  
            pstmt.setString(idxparam++, vo.getCebxrmdt());
            pstmt.setString(idxparam++, vo.getCebxrara());
            pstmt.setString(idxparam++, vo.getCebxriis());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjdj()));
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjdj()));
            pstmt.setString(idxparam++, vo.getCebxrjyj());
            pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrjdj()));
            pstmt.setString(idxparam++, vo.getGno());
            if(vo.getCebxrhno().substring(0, 1).equals("J")) { //주차일 경우 PLT수를  셋팅
            	pstmt.setDouble(idxparam++, Double.parseDouble(vo.getCebxrplt()));
            } else {
            	pstmt.setDouble(idxparam++, 0);
            }
            pstmt.setString(idxparam++, vo.getCebxrbdgbn());

            //===========================생성일자 및 생성일시 추가 20201130 Han.JH=============================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//CRDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//CRTIM. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            //=======================================================================================            
            
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
     * 작성자: jkkoo
     * 작성일: 2006-05-01 
     * 설  명: 
     * 기  타:
     */
   public static String getIsr(TBEBXRF1Vo vo, Connection conn) throws Exception
   {
      String bxrIsr = "";

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
//      Connection                 conn     = null;
      
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     SAPHEE.FILLINZERO((CAST(COALESCE(MAX(CS136_ISR),'0') AS INTEGER)+1),2) AS ISR  \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     SAPHEE.ZCST136             \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("     AND   MANDT = ?            \n");
//      sqlBuff.append("     AND   CS136_UPN = ?        \n");
//      sqlBuff.append("     AND   CS136_CST = ?        \n");
      sqlBuff.append("     AND   CS136_PJT = ?        \n");
      sqlBuff.append("     AND   CS136_HNO = ?        \n");
      sqlBuff.append("     AND   CS136_SEQ = ?        \n");
      sqlBuff.append("     AND   CS136_GND = ?        \n");
      sqlBuff.append("     AND   CS136_JYM = ?        \n");
      sqlBuff.append("  GROUP BY                      \n");
      sqlBuff.append("     MANDT,                     \n");
//      sqlBuff.append("     CS136_UPN,                 \n");
//      sqlBuff.append("     CS136_CST,                 \n");
      sqlBuff.append("     CS136_PJT,                 \n");
      sqlBuff.append("     CS136_HNO,                 \n");
      sqlBuff.append("     CS136_SEQ,                 \n");
      sqlBuff.append("     CS136_GND,                 \n");
      sqlBuff.append("     CS136_JYM                  \n");
      
      try
      {
//    	 conn  = getConnection();
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         pstmt.setString(1, vo.getMandt());
//         pstmt.setString(2, vo.getCebxrupn());
//         pstmt.setString(3, vo.getCebxrcst());
         pstmt.setString(2, vo.getCebxrpjt());
         pstmt.setString(3, vo.getCebxrhno());
         pstmt.setString(4, vo.getCebxrseq());
         pstmt.setString(5, vo.getCebxrgnd());
         pstmt.setString(6, vo.getCebxrjym());
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
				bxrIsr = new Integer(rs.getInt("ISR")).toString();
            if(bxrIsr.length() == 1)
               bxrIsr = "0" + bxrIsr;
         }
         else
            bxrIsr = "01";
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
      return bxrIsr;
   }       
   /**
    * 작성자 : jkkoo
    * 작성일 : 2006.05.02
    * 설  명 : UPDATE
    * 기  타 : 
    */
   public void updateRowComMethod1(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         sqlBuff.append("UPDATE  SAPHEE.ZCST136       \n");
         sqlBuff.append("SET     CS136_MGB    = 'Y',  \n");
         sqlBuff.append("        CS136_MSA    = '91', \n");
         sqlBuff.append("        CS136_MDT    = ?,    \n");
         //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
         sqlBuff.append("        UPDAT    = ?,        \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
         sqlBuff.append("        UPZET    = ?         \n"); // BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
         //=======================================================================================         
         sqlBuff.append("WHERE   CS136_PJT    = ?     \n");
         sqlBuff.append("AND     CS136_HNO    = ?     \n");
         sqlBuff.append("AND     CS136_SEQ    = ?     \n");
         sqlBuff.append("AND     CS136_GND    = ?     \n");
         sqlBuff.append("AND     CS136_JYM    = ?     \n");
         sqlBuff.append("AND     CS136_ISR    = ?     \n");
         sqlBuff.append("AND     MANDT        = ?     \n");
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//UPDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//UPZET. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            //=======================================================================================
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, vo.getCebxrisr());
            pstmt.setString(idxparam++, vo.getMandt());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.02
    * 설  명 : UPDATE
    * 기  타 : 
    */
   public void updateRowComMethod2(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
          sqlBuff.append("UPDATE  SAPHEE.ZCST136        \n");
          sqlBuff.append("SET     CS136_MGB    = 'Y',   \n");
          sqlBuff.append("        CS136_MSA    = '33',  \n");
          sqlBuff.append("        CS136_MDT    = ?,     \n");
          //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
          sqlBuff.append("        UPDAT        = ?,     \n");
          sqlBuff.append("        UPZET        = ?     \n");
          //=======================================================================================          
          sqlBuff.append("WHERE   MANDT        = ?      \n");
          sqlBuff.append("AND     CS136_PJT    = ?      \n");
          sqlBuff.append("AND     CS136_HNO    = ?      \n");
          sqlBuff.append("AND     CS136_SEQ    = ?      \n");
          sqlBuff.append("AND     CS136_GND    = ?      \n");
          sqlBuff.append("AND     CS136_JYM    = ?      \n");
          sqlBuff.append("AND     CS136_ISR    = ?      \n");
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//UPDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//UPZET. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            //=======================================================================================            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, vo.getCebxrisr());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.10
    * 설  명 : 실패보고 승인시 기성비계획 취소 
    * 기  타 : 
    */
   public void ComMethodForCancelForETC1(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         sqlBuff.append("UPDATE  SAPHEE.ZCST136      \n");
         sqlBuff.append("SET     CS136_MGB   = 'Y',  \n");
         sqlBuff.append("        CS136_MSA   = '51', \n");
         sqlBuff.append("        CS136_MDT   = ?,    \n");
         //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
         sqlBuff.append("        UPDAT        = ?,   \n");
         sqlBuff.append("        UPZET        = ?    \n");
         //=======================================================================================          
         sqlBuff.append("WHERE   MANDT       = ?     \n");
         sqlBuff.append("AND     CS136_PJT   = ?     \n");
         sqlBuff.append("AND     CS136_HNO   = ?     \n");
         sqlBuff.append("AND     CS136_SEQ   = ?     \n");
         sqlBuff.append("AND     CS136_GND   = ?     \n");
         sqlBuff.append("AND     CS136_JYM   = ?     \n");
         sqlBuff.append("AND     CS136_ISR   = ?     \n");
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//UPDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//UPZET. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            //=======================================================================================            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, vo.getCebxrisr());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.10
    * 설  명 : 실패보고 승인시 기성비계획 취소 
    * 기  타 : 
    */
   public void ComMethodForCancelForETC2(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         sqlBuff.append("UPDATE  SAPHEE.ZCST136       \n");
         sqlBuff.append("SET     CS136_MGB   = 'Y',   \n");
         sqlBuff.append("        CS136_MSA   = '52',  \n");
         sqlBuff.append("        CS136_MDT   = ?,     \n");
         //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
         sqlBuff.append("        UPDAT       = ?,     \n");
         sqlBuff.append("        UPZET       = ?      \n");
         //=======================================================================================          
         sqlBuff.append("WHERE   MANDT       = ?      \n");
         sqlBuff.append("AND     CS136_PJT   = ?      \n");
         sqlBuff.append("AND     CS136_HNO   = ?      \n");
         sqlBuff.append("AND     CS136_SEQ   = ?      \n");
         sqlBuff.append("AND     CS136_GND   = ?      \n");
         sqlBuff.append("AND     CS136_JYM   = ?      \n");
         sqlBuff.append("AND     CS136_ISR   = ?      \n");
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//UPDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//UPZET. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            //=======================================================================================            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, vo.getCebxrisr());
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
    * 작성자: mhcho
    * 작성일: 2006-08-14 오전 11:32:45
    * 설  명: 일시중지복구시 직영인지 여부를 가져오기
    * 기  타:
    */
   public String getGbuForComMethodForCancelForETC3(TBEBXRF1Vo vo, Connection conn) throws Exception
   {
      
      
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      String                     cebxrgbu = "";
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("  SELECT                          \n");      
      sqlBuff.append("         MAX(CS136_GBU) CS136_GBU \n");
      sqlBuff.append("  FROM                            \n");
      sqlBuff.append("         SAPHEE.ZCST136           \n");
      sqlBuff.append("  WHERE   MANDT       = ?         \n");
      sqlBuff.append("  AND     CS136_PJT   = ?         \n");
      sqlBuff.append("  AND     CS136_HNO   = ?         \n");
      sqlBuff.append("  AND     CS136_SEQ   = ?         \n");
      sqlBuff.append("  AND     CS136_GND   = ?         \n");
      sqlBuff.append("  AND     CS136_JYM   = ?         \n");
//      sqlBuff.append("  AND     CS136_ISR   = ?         \n");
      
      try
      {
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxrpjt());
         pstmt.setString(idxparam++, vo.getCebxrhno());
         pstmt.setString(idxparam++, vo.getCebxrseq());
         pstmt.setString(idxparam++, vo.getCebxrgnd());
         pstmt.setString(idxparam++, vo.getCebxrjym());
//         pstmt.setString(idxparam++, vo.getCebxrisr());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         
         if (rs.next())
         {            
            cebxrgbu = rs.getString("CS136_GBU").trim();            
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
      return cebxrgbu;
   }             
   
   /**
    * 작성자 : jkkoo
    * 작성일 : 2006.05.10
    * 설  명 : 실패보고 승인시 기성비계획 취소 
    * 기  타 : 
    */
   public void ComMethodForCancelForETC3(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         sqlBuff.append("UPDATE  SAPHEE.ZCST136       \n");
         sqlBuff.append("SET     CS136_MGB   = ?,     \n");
         sqlBuff.append("        CS136_MSA   = ?,     \n");
         sqlBuff.append("        CS136_MDT   = ?,     \n");
         sqlBuff.append("        CS136_GYM   = ?,     \n");
         //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
         sqlBuff.append("        UPDAT       = ?,     \n");
         sqlBuff.append("        UPZET       = ?      \n");
         //=======================================================================================          
         sqlBuff.append("WHERE   MANDT       = ?      \n");
         sqlBuff.append("AND     CS136_PJT   = ?      \n");
         sqlBuff.append("AND     CS136_HNO   = ?      \n");
         sqlBuff.append("AND     CS136_SEQ   = ?      \n");
         sqlBuff.append("AND     CS136_GND   = ?      \n");
         sqlBuff.append("AND     CS136_JYM   = ?      \n");
         sqlBuff.append("AND     CS136_ISR   = ?      \n");

         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            
            String cebxrgbu = getGbuForComMethodForCancelForETC3(vo, conn);
            if(cebxrgbu.equals("1")){
               pstmt.setString(idxparam++, "Y");
               pstmt.setString(idxparam++, "09");
               pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));               
            }else{
               pstmt.setString(idxparam++, "");
               pstmt.setString(idxparam++, "");
               pstmt.setString(idxparam++, "");                        
            }
            
            pstmt.setString(idxparam++, vo.getCebxrgym());
            //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//UPDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//UPZET. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            //=======================================================================================            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, vo.getCebxrisr());
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
    * 작성자 : jhlee
    * 작성일 : 2008.06.20
    * 설  명 : 중도해지시 기성비계획 취소 
    * 기  타 : 
    */
   public void ComMethodForCancelForETC4(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         sqlBuff.append("UPDATE  SAPHEE.ZCST136      \n");
         sqlBuff.append("SET     CS136_MGB   = 'Y',  \n");
         sqlBuff.append("        CS136_MSA   = '66', \n");
         sqlBuff.append("        CS136_MDT   = ?,    \n");
         //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
         sqlBuff.append("        UPDAT       = ?,     \n");
         sqlBuff.append("        UPZET       = ?      \n");
         //=======================================================================================          
         sqlBuff.append("WHERE   MANDT       = ?     \n");
         sqlBuff.append("AND     CS136_PJT   = ?     \n");
         sqlBuff.append("AND     CS136_HNO   = ?     \n");
         sqlBuff.append("AND     CS136_SEQ   = ?     \n");
         sqlBuff.append("AND     CS136_GND   = ?     \n");
         sqlBuff.append("AND     CS136_JYM   = ?     \n");
         sqlBuff.append("AND     CS136_ISR   = ?     \n");
         sqlBuff.append("AND     CS136_MGB   = ''    \n");
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            pstmt.setString(idxparam++, DateTime.getDateString("yyyyMMdd"));
            //===========================수정일자 및 수정일시 추가 20201130 Han.JH=============================
            pstmt.setString(idxparam++, DateTime.getShortDateString());//UPDAT. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            pstmt.setString(idxparam++, DateTime.getShortTimeString());//UPZET. BS ETL 연계를 위한 식별컬럼 추가. 2020.11.30 Han J.H
            //=======================================================================================            
            pstmt.setString(idxparam++, vo.getMandt());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, vo.getCebxrisr());
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
    * 작성자: jkkoo
    * 작성일: 2006-04-28 오후 1:57:45
    * 설  명: 기 생성된 기성비계획중 무상발주기간과 중복되는 무상공사에 대한 기성비계획을 가져온다
    * 기  타:
    */
   public ArrayList selectRowComMethodForCancel1(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXRF1Vo                 vo       = (TBEBXRF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXRF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     CEBXRPJT,                  \n");
      sqlBuff.append("     CEBXRHGB,                  \n");
      sqlBuff.append("     CEBXRHNO,                  \n");
      sqlBuff.append("     CEBXRSEQ,                  \n");
      sqlBuff.append("     CEBXRGND,                  \n");
      sqlBuff.append("     CEBXRJYM,                  \n");
      sqlBuff.append("     CEBXRISR,                  \n");
      sqlBuff.append("     CEBXRBDA,                  \n");
      sqlBuff.append("     CEBXRMNO,                  \n");
      sqlBuff.append("     CEBXRHTY,                  \n");
      sqlBuff.append("     CEBXRARA,                  \n");
      sqlBuff.append("     CEBXRJUJ,                  \n");
      sqlBuff.append("     CEBXRBSU,                  \n");
      sqlBuff.append("     CEBXRGBU,                  \n");
      sqlBuff.append("     CEBXRAGB,                  \n");
      sqlBuff.append("     CEBXRABG,                  \n");
      sqlBuff.append("     CEBXRPST,                  \n");
      sqlBuff.append("     CEBXRGGB,                  \n");
      sqlBuff.append("     CEBXRGYM,                  \n");
      sqlBuff.append("     CEBXRMGB,                  \n");
      sqlBuff.append("     CEBXRMSA,                  \n");
      sqlBuff.append("     CEBXRMDT,                  \n");
      sqlBuff.append("     CEBXRJYD,                  \n");
      sqlBuff.append("     CEBXRJSD,                  \n");
      sqlBuff.append("     CEBXRJYQ,                  \n");
      sqlBuff.append("     CEBXRIDQ,                  \n");
      sqlBuff.append("     CEBXRBAM                   \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     EVLADM.TBEBXRF1            \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   CEBXRPJT = ?            \n");
      sqlBuff.append("  AND   CEBXRHGB = ?            \n");
      sqlBuff.append("  AND   CEBXRHNO = ?            \n");
      //sqlBuff.append("  AND   CEBXRSEQ = ?            \n");
      sqlBuff.append("  AND   CEBXRGND = ?            \n");
      sqlBuff.append("  AND   CEBXRJYM >= ?           \n");
      
      try
      {
         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getCebxrpjt());
         pstmt.setString(idxparam++, vo.getCebxrhgb());
         pstmt.setString(idxparam++, vo.getCebxrhno());
         //pstmt.setString(idxparam++, vo.getCebxrseq());
         pstmt.setString(idxparam++, vo.getCebxrgnd());
         pstmt.setString(idxparam++, vo.getCebxrjym());
         
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         

         while (rs.next())
         {
            rtnVo = new TBEBXRF1Vo();
            rtnVo.setCebxrpjt(rs.getString("CEBXRPJT"));
            rtnVo.setCebxrhgb(rs.getString("CEBXRHGB"));
            rtnVo.setCebxrhno(rs.getString("CEBXRHNO"));
            rtnVo.setCebxrseq(rs.getString("CEBXRSEQ"));
            rtnVo.setCebxrgnd(rs.getString("CEBXRGND"));
            rtnVo.setCebxrjym(rs.getString("CEBXRJYM"));
            rtnVo.setCebxrisr(rs.getString("CEBXRISR"));
            rtnVo.setCebxrbda(rs.getString("CEBXRBDA"));

            rtnVo.setCebxrmno(rs.getString("CEBXRMNO"));//8.MNO               
            rtnVo.setCebxrhty(rs.getString("CEBXRHTY"));//9.고속기종여부      
            rtnVo.setCebxrara(rs.getString("CEBXRARA"));//10.대지역           
            rtnVo.setCebxrjuj(rs.getString("CEBXRJUJ"));//11.주점검자         
            rtnVo.setCebxrbsu(rs.getString("CEBXRBSU"));//12.보수협력사        
            rtnVo.setCebxrgbu(rs.getString("CEBXRGBU"));//13.직영협력사여부   
            rtnVo.setCebxragb(rs.getString("CEBXRAGB"));//14.시내외구분       
            rtnVo.setCebxrabg(rs.getString("CEBXRABG"));//15.빌딩구분         
            rtnVo.setCebxrpst(rs.getString("CEBXRPST"));//16.진행상태  
            rtnVo.setCebxrggb(rs.getString("CEBXRGGB"));//27.지급구분         
            rtnVo.setCebxrgym(rs.getString("CEBXRGYM"));//28.지급년월          
            rtnVo.setCebxrmgb(rs.getString("CEBXRMGB"));//29.미지급구분       
            rtnVo.setCebxrmsa(rs.getString("CEBXRMSA"));//30.미지급사유       
            rtnVo.setCebxrmdt(rs.getString("CEBXRMDT"));//31.미지급등록일
            rtnVo.setCebxrjyd(rs.getString("CEBXRJYD"));//24.지급요청일
            rtnVo.setCebxrjsd(rs.getString("CEBXRJSD"));//21.지급대상생성일 
            rtnVo.setCebxrjyq(rs.getString("CEBXRJYQ"));
            rtnVo.setCebxridq(rs.getString("CEBXRIDQ"));
            rtnVo.setCebxrbam(rs.getString("CEBXRBAM"));

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
    * 작성자: jkkoo
    * 작성일: 2006-05-02 오후 1:57:45
    * 설  명: 기 생성된 기성비계획중 무상발주기간과 중복되는 유상공사에 대한 기성비계획을 가져온다
    * 기  타:
    */
   public ArrayList selectRowComMethodForCancel2(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXRF1Vo                 vo       = (TBEBXRF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXRF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     CEBXRPJT,                  \n");
      sqlBuff.append("     CEBXRHGB,                  \n");
      sqlBuff.append("     CEBXRHNO,                  \n");
      sqlBuff.append("     CEBXRSEQ,                  \n");
      sqlBuff.append("     CEBXRGND,                  \n");
      sqlBuff.append("     CEBXRJYM,                  \n");
      sqlBuff.append("     CEBXRISR,                  \n");
      sqlBuff.append("     CEBXRBDA,                  \n");
      sqlBuff.append("     CEBXRMNO,                  \n");
      sqlBuff.append("     CEBXRHTY,                  \n");
      sqlBuff.append("     CEBXRARA,                  \n");
      sqlBuff.append("     CEBXRJUJ,                  \n");
      sqlBuff.append("     CEBXRBSU,                  \n");
      sqlBuff.append("     CEBXRGBU,                  \n");
      sqlBuff.append("     CEBXRAGB,                  \n");
      sqlBuff.append("     CEBXRABG,                  \n");
      sqlBuff.append("     CEBXRPST,                  \n");
      sqlBuff.append("     CEBXRGGB,                  \n");
      sqlBuff.append("     CEBXRGYM,                  \n");
      sqlBuff.append("     CEBXRMGB,                  \n");
      sqlBuff.append("     CEBXRMSA,                  \n");
      sqlBuff.append("     CEBXRMDT,                  \n");
      sqlBuff.append("     CEBXRJYD,                  \n");
      sqlBuff.append("     CEBXRJSD,                  \n");
      sqlBuff.append("     CEBXRJYQ,                  \n");
      sqlBuff.append("     CEBXRIDQ,                  \n");
      sqlBuff.append("     CEBXRBAM                   \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     EVLADM.TBEBXRF1            \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   CEBXRPJT = ?            \n");
      sqlBuff.append("  AND   CEBXRHGB = ?            \n");
      sqlBuff.append("  AND   CEBXRHNO = ?            \n");
      //sqlBuff.append("  AND   CEBXRSEQ = ?            \n");
      sqlBuff.append("  AND   CEBXRGND = ?            \n");
      sqlBuff.append("  AND   CEBXRJYM >= ?           \n");
      
      try
      {
         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getCebxrpjt());
         pstmt.setString(idxparam++, vo.getCebxrhgb());
         pstmt.setString(idxparam++, vo.getCebxrhno());
         //pstmt.setString(idxparam++, vo.getCebxrseq());
         pstmt.setString(idxparam++, vo.getCebxrgnd());
         pstmt.setString(idxparam++, vo.getCebxrjym());
         
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         

         while (rs.next())
         {
            rtnVo = new TBEBXRF1Vo();
            rtnVo.setCebxrpjt(rs.getString("CEBXRPJT"));
            rtnVo.setCebxrhgb(rs.getString("CEBXRHGB"));
            rtnVo.setCebxrhno(rs.getString("CEBXRHNO"));
            rtnVo.setCebxrseq(rs.getString("CEBXRSEQ"));
            rtnVo.setCebxrgnd(rs.getString("CEBXRGND"));
            rtnVo.setCebxrjym(rs.getString("CEBXRJYM"));
            rtnVo.setCebxrisr(rs.getString("CEBXRISR"));
            rtnVo.setCebxrbda(rs.getString("CEBXRBDA"));

            rtnVo.setCebxrmno(rs.getString("CEBXRMNO"));//8.MNO               
            rtnVo.setCebxrhty(rs.getString("CEBXRHTY"));//9.고속기종여부      
            rtnVo.setCebxrara(rs.getString("CEBXRARA"));//10.대지역           
            rtnVo.setCebxrjuj(rs.getString("CEBXRJUJ"));//11.주점검자         
            rtnVo.setCebxrbsu(rs.getString("CEBXRBSU"));//12.보수협력사        
            rtnVo.setCebxrgbu(rs.getString("CEBXRGBU"));//13.직영협력사여부   
            rtnVo.setCebxragb(rs.getString("CEBXRAGB"));//14.시내외구분       
            rtnVo.setCebxrabg(rs.getString("CEBXRABG"));//15.빌딩구분         
            rtnVo.setCebxrpst(rs.getString("CEBXRPST"));//16.진행상태  
            rtnVo.setCebxrggb(rs.getString("CEBXRGGB"));//27.지급구분         
            rtnVo.setCebxrgym(rs.getString("CEBXRGYM"));//28.지급년월          
            rtnVo.setCebxrmgb(rs.getString("CEBXRMGB"));//29.미지급구분       
            rtnVo.setCebxrmsa(rs.getString("CEBXRMSA"));//30.미지급사유       
            rtnVo.setCebxrmdt(rs.getString("CEBXRMDT"));//31.미지급등록일
            rtnVo.setCebxrjyd(rs.getString("CEBXRJYD"));//24.지급요청일
            rtnVo.setCebxrjsd(rs.getString("CEBXRJSD"));//21.지급대상생성일 
            rtnVo.setCebxrjyq(rs.getString("CEBXRJYQ"));
            rtnVo.setCebxridq(rs.getString("CEBXRIDQ"));
            rtnVo.setCebxrbam(rs.getString("CEBXRBAM"));

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
    * 작성자: jkkoo
    * 작성일: 2006-05-11 
    * 설  명: 실패보고시에 기 생성된 기성비계획중 실패보고 승인일자 다음달 부터의 기성비계획을 가져온다
    * 기  타:
    */
   public ArrayList selectRowComMethodForCancel(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXRF1Vo                 vo       = (TBEBXRF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXRF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("SELECT                \n");
      sqlBuff.append("       MANDT,         \n");
      sqlBuff.append("       CS136_PJT,     \n");
      sqlBuff.append("       CS136_HNO,     \n");
      sqlBuff.append("       CS136_SEQ,     \n");
      sqlBuff.append("       CS136_GND,     \n");
      sqlBuff.append("       CS136_JYM,     \n");
      sqlBuff.append("       CS136_ISR,     \n");
      sqlBuff.append("       CS136_UPN,     \n");
      sqlBuff.append("       CS136_CST,     \n");
      sqlBuff.append("       CS136_HTY,     \n");
      sqlBuff.append("       CS136_ARA,     \n");
      sqlBuff.append("       CS136_BSU,     \n");
      sqlBuff.append("       CS136_GBU,     \n");
      sqlBuff.append("       CS136_AGB,     \n");      
      sqlBuff.append("       CS136_ABG,     \n");
      sqlBuff.append("       CS136_RGB,     \n");
      sqlBuff.append("       CS136_IIS,     \n");
      sqlBuff.append("       CS136_PLT,     \n");
      sqlBuff.append("       CS136_PST,     \n");
      sqlBuff.append("       CS136_FDT,     \n");
      sqlBuff.append("       CS136_TOD,     \n");
      sqlBuff.append("       CS136_JDQ,     \n");
      sqlBuff.append("       CS136_JDJ,     \n");
      sqlBuff.append("       CS136_BDA,     \n");
      sqlBuff.append("       CS136_HDA,     \n");
      sqlBuff.append("       CS136_DDA,     \n");
      sqlBuff.append("       CS136_BPD,     \n");
      sqlBuff.append("       CS136_CPR,     \n");
      sqlBuff.append("       CS136_JSD,     \n");
      sqlBuff.append("       CS136_JYQ,     \n");   
      sqlBuff.append("       CS136_JYC,     \n");
      sqlBuff.append("       CS136_BYA,     \n");
      sqlBuff.append("       CS136_HYA,     \n");
      sqlBuff.append("       CS136_DYA,     \n");
      sqlBuff.append("       CS136_IS1,     \n");
      sqlBuff.append("       CS136_IS2,     \n");
      sqlBuff.append("       CS136_IS3,     \n");
      sqlBuff.append("       CS136_IS4,     \n");
      sqlBuff.append("       CS136_JYD,     \n");      
      sqlBuff.append("       CS136_JYJ,     \n");
      sqlBuff.append("       CS136_IDQ,     \n");
      sqlBuff.append("       CS136_IDJ,     \n");
      sqlBuff.append("       CS136_BAM,     \n");
      sqlBuff.append("       CS136_HAM,     \n");
      sqlBuff.append("       CS136_DAM,     \n");
      sqlBuff.append("       CS136_IY1,     \n");
      sqlBuff.append("       CS136_IY2,     \n"); 
      sqlBuff.append("       CS136_IY3,     \n");
      sqlBuff.append("       CS136_IY4,     \n");
      sqlBuff.append("       CS136_RET,     \n");
      sqlBuff.append("       CS136_RMK,     \n");
      sqlBuff.append("       CS136_JAR,     \n");
      sqlBuff.append("       CS136_SS1,     \n");
      sqlBuff.append("       CS136_SDT,     \n");
      sqlBuff.append("       CS136_APP,     \n");
      sqlBuff.append("       CS136_GGB,     \n");      
      sqlBuff.append("       CS136_GYM,     \n");
      sqlBuff.append("       CS136_MGB,     \n");
      sqlBuff.append("       CS136_MSA,     \n");       
      sqlBuff.append("       CS136_MDT,     \n");
      sqlBuff.append("       CS136_ECB,     \n");
      sqlBuff.append("       CS136_YDT,     \n");
      sqlBuff.append("       CS136_YPP,     \n");
      sqlBuff.append("       CS136_GNO,     \n");
      sqlBuff.append("       CS136_BDGBN,    \n");
      sqlBuff.append("       CS136_ACDA,     \n");
      sqlBuff.append("       CS136_ACYA,     \n");
      sqlBuff.append("       CS136_ACAM     \n");
      sqlBuff.append("  FROM                \n");         
      sqlBuff.append("       SAPHEE.ZCST136 \n");
      sqlBuff.append(" WHERE                \n");
      sqlBuff.append("       MANDT = ?      \n");
      sqlBuff.append("   AND CS136_PJT = ?  \n");
      sqlBuff.append("   AND CS136_HNO = ?  \n");
      sqlBuff.append("   AND CS136_SEQ = ?  \n");
      sqlBuff.append("   AND CS136_GND = ?  \n");
      sqlBuff.append("   AND CS136_JYM >= ? \n");
      sqlBuff.append("   AND CS136_UPN = ?  \n");
      sqlBuff.append("   AND CS136_CST = ?  \n");
      sqlBuff.append("   AND CS136_MGB = '' \n");
      
      try
      {
//         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
 	     conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxrpjt());
         pstmt.setString(idxparam++, vo.getCebxrhno());
         pstmt.setString(idxparam++, vo.getCebxrseq());
         pstmt.setString(idxparam++, vo.getCebxrgnd());
         pstmt.setString(idxparam++, vo.getCebxrjym());
         pstmt.setString(idxparam++, vo.getCebxrupn());
         pstmt.setString(idxparam++, vo.getCebxrcst());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();

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
            rtnVo.setCebxrhda(rs.getString("CS136_HDA"));
            rtnVo.setCebxrdda(rs.getString("CS136_DDA"));
            rtnVo.setCebxrbpd(rs.getString("CS136_BPD"));
            rtnVo.setCebxrcpr(rs.getString("CS136_CPR"));
            rtnVo.setCebxrjsd(rs.getString("CS136_JSD"));
            rtnVo.setCebxrjyq(rs.getString("CS136_JYQ"));
            rtnVo.setCebxrjyc(rs.getString("CS136_JYC"));
            rtnVo.setCebxrbya(rs.getString("CS136_BYA"));
            rtnVo.setCebxrhya(rs.getString("CS136_HYA"));
            rtnVo.setCebxrdya(rs.getString("CS136_DYA"));
            rtnVo.setCebxris1(rs.getString("CS136_IS1"));
            rtnVo.setCebxris2(rs.getString("CS136_IS2"));
            rtnVo.setCebxris3(rs.getString("CS136_IS3"));
            rtnVo.setCebxris4(rs.getString("CS136_IS4"));
            rtnVo.setCebxrjyd(rs.getString("CS136_JYD"));
            rtnVo.setCebxrjyj(rs.getString("CS136_JYJ"));
            rtnVo.setCebxridq(rs.getString("CS136_IDQ"));
            rtnVo.setCebxridj(rs.getString("CS136_IDJ"));
            rtnVo.setCebxrbam(rs.getString("CS136_BAM"));
            rtnVo.setCebxrham(rs.getString("CS136_HAM"));
            rtnVo.setCebxrdam(rs.getString("CS136_DAM"));
            rtnVo.setCebxriy1(rs.getString("CS136_IY1"));
            rtnVo.setCebxriy2(rs.getString("CS136_IY2"));
            rtnVo.setCebxriy3(rs.getString("CS136_IY3"));
            rtnVo.setCebxriy4(rs.getString("CS136_IY4"));
            rtnVo.setCebxrret(rs.getString("CS136_RET"));
            rtnVo.setCebxrrmk(rs.getString("CS136_RMK"));
            rtnVo.setCebxrsdt(rs.getString("CS136_SDT"));
            rtnVo.setCebxrss1(rs.getString("CS136_SS1"));
            rtnVo.setCebxrapp(rs.getString("CS136_APP"));
            rtnVo.setCebxrggb(rs.getString("CS136_GGB"));
            rtnVo.setCebxrgym(rs.getString("CS136_GYM"));
            rtnVo.setCebxrmgb(rs.getString("CS136_MGB"));
            rtnVo.setCebxrmsa(rs.getString("CS136_MSA"));
            rtnVo.setCebxrmdt(rs.getString("CS136_MDT"));
            rtnVo.setCebxrecb(rs.getString("CS136_ECB"));
            rtnVo.setCebxrydt(rs.getString("CS136_YDT"));
            rtnVo.setCebxrypp(rs.getString("CS136_YPP"));
            rtnVo.setGno(rs.getString("CS136_GNO"));
            rtnVo.setCebxrbdgbn(rs.getString("CS136_BDGBN"));
            rtnVo.setCebxracda(rs.getString("CS136_ACDA"));
            rtnVo.setCebxracya(rs.getString("CS136_ACYA"));
            rtnVo.setCebxracam(rs.getString("CS136_ACAM"));
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
    * 작성자: jhlee
    * 작성일: 2009-04-21 
    * 설  명: 중지/보류에 기 생성된 기성비계획중 중지/보류일자 다음달 부터의 기성비계획을 가져온다
    * 기  타:
    */
   public ArrayList selectRowComMethodForCancelStop(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXRF1Vo                 vo       = (TBEBXRF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXRF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("SELECT                \n");
      sqlBuff.append("       MANDT,         \n");
      sqlBuff.append("       CS136_PJT,     \n");
      sqlBuff.append("       CS136_HNO,     \n");
      sqlBuff.append("       CS136_SEQ,     \n");
      sqlBuff.append("       CS136_GND,     \n");
      sqlBuff.append("       CS136_JYM,     \n");
      sqlBuff.append("       CS136_ISR,     \n");
      sqlBuff.append("       CS136_UPN,     \n");
      sqlBuff.append("       CS136_CST,     \n");
      sqlBuff.append("       CS136_HTY,     \n");
      sqlBuff.append("       CS136_ARA,     \n");
      sqlBuff.append("       CS136_BSU,     \n");
      sqlBuff.append("       CS136_GBU,     \n");
      sqlBuff.append("       CS136_AGB,     \n");      
      sqlBuff.append("       CS136_ABG,     \n");
      sqlBuff.append("       CS136_RGB,     \n");
      sqlBuff.append("       CS136_IIS,     \n");
      sqlBuff.append("       CS136_PLT,     \n");
      sqlBuff.append("       CS136_PST,     \n");
      sqlBuff.append("       CS136_FDT,     \n");
      sqlBuff.append("       CS136_TOD,     \n");
      sqlBuff.append("       CS136_JDQ,     \n");
      sqlBuff.append("       CS136_JDJ,     \n");
      sqlBuff.append("       CS136_BDA,     \n");
      sqlBuff.append("       CS136_BPD,     \n");
      sqlBuff.append("       CS136_CPR,     \n");
      sqlBuff.append("       CS136_JSD,     \n");
      sqlBuff.append("       CS136_JYQ,     \n");   
      sqlBuff.append("       CS136_JYC,     \n");
      sqlBuff.append("       CS136_BYA,     \n");
      sqlBuff.append("       CS136_IS1,     \n");
      sqlBuff.append("       CS136_IS2,     \n");
      sqlBuff.append("       CS136_IS3,     \n");
      sqlBuff.append("       CS136_IS4,     \n");
      sqlBuff.append("       CS136_JYD,     \n");      
      sqlBuff.append("       CS136_JYJ,     \n");
      sqlBuff.append("       CS136_IDQ,     \n");
      sqlBuff.append("       CS136_IDJ,     \n");
      sqlBuff.append("       CS136_BAM,     \n");
      sqlBuff.append("       CS136_IY1,     \n");
      sqlBuff.append("       CS136_IY2,     \n"); 
      sqlBuff.append("       CS136_IY3,     \n");
      sqlBuff.append("       CS136_IY4,     \n");
      sqlBuff.append("       CS136_RET,     \n");
      sqlBuff.append("       CS136_RMK,     \n");
      sqlBuff.append("       CS136_JAR,     \n");
      sqlBuff.append("       CS136_SS1,     \n");
      sqlBuff.append("       CS136_SDT,     \n");
      sqlBuff.append("       CS136_APP,     \n");
      sqlBuff.append("       CS136_GGB,     \n");      
      sqlBuff.append("       CS136_GYM,     \n");
      sqlBuff.append("       CS136_MGB,     \n");
      sqlBuff.append("       CS136_MSA,     \n");       
      sqlBuff.append("       CS136_MDT,     \n");
      sqlBuff.append("       CS136_ECB,     \n");
      sqlBuff.append("       CS136_YDT,     \n");
      sqlBuff.append("       CS136_YPP,     \n");
      sqlBuff.append("       CS136_GNO,     \n");
      sqlBuff.append("       CS136_BDGBN    \n");
      
      sqlBuff.append("  FROM                \n");         
      sqlBuff.append("       SAPHEE.ZCST136 \n");
      sqlBuff.append(" WHERE                \n");
      sqlBuff.append("       MANDT = ?      \n");
      sqlBuff.append("   AND CS136_PJT = ?  \n");
      sqlBuff.append("   AND CS136_HNO = ?  \n");
      sqlBuff.append("   AND CS136_SEQ >= ? \n");
      sqlBuff.append("   AND CS136_GND = ?  \n");
      sqlBuff.append("   AND CS136_JYM >= ? \n");
      sqlBuff.append("   AND CS136_UPN = ?  \n");
      sqlBuff.append("   AND CS136_CST = ?  \n");
      
      try
      {
//         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
 	     conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxrpjt());
         pstmt.setString(idxparam++, vo.getCebxrhno());
         pstmt.setString(idxparam++, vo.getCebxrseq());
         pstmt.setString(idxparam++, vo.getCebxrgnd());
         pstmt.setString(idxparam++, vo.getCebxrjym());
         pstmt.setString(idxparam++, vo.getCebxrupn());
         pstmt.setString(idxparam++, vo.getCebxrcst());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();

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
            rtnVo.setCebxrret(rs.getString("CS136_RET"));
            rtnVo.setCebxrrmk(rs.getString("CS136_RMK"));
            rtnVo.setCebxrsdt(rs.getString("CS136_SDT"));
            rtnVo.setCebxrss1(rs.getString("CS136_SS1"));
            rtnVo.setCebxrapp(rs.getString("CS136_APP"));
            rtnVo.setCebxrggb(rs.getString("CS136_GGB"));
            rtnVo.setCebxrgym(rs.getString("CS136_GYM"));
            rtnVo.setCebxrmgb(rs.getString("CS136_MGB"));
            rtnVo.setCebxrmsa(rs.getString("CS136_MSA"));
            rtnVo.setCebxrmdt(rs.getString("CS136_MDT"));
            rtnVo.setCebxrecb(rs.getString("CS136_ECB"));
            rtnVo.setCebxrydt(rs.getString("CS136_YDT"));
            rtnVo.setCebxrypp(rs.getString("CS136_YPP"));
            rtnVo.setGno(rs.getString("CS136_GNO"));
            rtnVo.setCebxrbdgbn(rs.getString("CS136_BDGBN"));

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
    * 작성자: jhlee
    * 작성일: 2008-12-11 
    * 설  명: 실패보고시에 기 생성된 기성비계획중 실패보고 승인일자 다음달 부터의 기성비계획을 가져온다(FM계약 포함)
    * 기  타:
    */
   public ArrayList selectRowComMethodForCancelSilPae(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXRF1Vo                 vo       = (TBEBXRF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXRF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("SELECT                \n");
      sqlBuff.append("       MANDT,         \n");
      sqlBuff.append("       CS136_PJT,     \n");
      sqlBuff.append("       CS136_HNO,     \n");
      sqlBuff.append("       CS136_SEQ,     \n");
      sqlBuff.append("       CS136_GND,     \n");
      sqlBuff.append("       CS136_JYM,     \n");
      sqlBuff.append("       CS136_ISR,     \n");
      sqlBuff.append("       CS136_UPN,     \n");
      sqlBuff.append("       CS136_CST,     \n");
      sqlBuff.append("       CS136_HTY,     \n");
      sqlBuff.append("       CS136_ARA,     \n");
      sqlBuff.append("       CS136_BSU,     \n");
      sqlBuff.append("       CS136_GBU,     \n");
      sqlBuff.append("       CS136_AGB,     \n");      
      sqlBuff.append("       CS136_ABG,     \n");
      sqlBuff.append("       CS136_RGB,     \n");
      sqlBuff.append("       CS136_IIS,     \n");
      sqlBuff.append("       CS136_PLT,     \n");
      sqlBuff.append("       CS136_PST,     \n");
      sqlBuff.append("       CS136_FDT,     \n");
      sqlBuff.append("       CS136_TOD,     \n");
      sqlBuff.append("       CS136_JDQ,     \n");
      sqlBuff.append("       CS136_JDJ,     \n");
      sqlBuff.append("       CS136_BDA,     \n");
      sqlBuff.append("       CS136_BPD,     \n");
      sqlBuff.append("       CS136_CPR,     \n");
      sqlBuff.append("       CS136_JSD,     \n");
      sqlBuff.append("       CS136_JYQ,     \n");   
      sqlBuff.append("       CS136_JYC,     \n");
      sqlBuff.append("       CS136_BYA,     \n");
      sqlBuff.append("       CS136_IS1,     \n");
      sqlBuff.append("       CS136_IS2,     \n");
      sqlBuff.append("       CS136_IS3,     \n");
      sqlBuff.append("       CS136_IS4,     \n");
      sqlBuff.append("       CS136_JYD,     \n");      
      sqlBuff.append("       CS136_JYJ,     \n");
      sqlBuff.append("       CS136_IDQ,     \n");
      sqlBuff.append("       CS136_IDJ,     \n");
      sqlBuff.append("       CS136_BAM,     \n");
      sqlBuff.append("       CS136_IY1,     \n");
      sqlBuff.append("       CS136_IY2,     \n"); 
      sqlBuff.append("       CS136_IY3,     \n");
      sqlBuff.append("       CS136_IY4,     \n");
      sqlBuff.append("       CS136_RET,     \n");
      sqlBuff.append("       CS136_RMK,     \n");
      sqlBuff.append("       CS136_JAR,     \n");
      sqlBuff.append("       CS136_SS1,     \n");
      sqlBuff.append("       CS136_SDT,     \n");
      sqlBuff.append("       CS136_APP,     \n");
      sqlBuff.append("       CS136_GGB,     \n");      
      sqlBuff.append("       CS136_GYM,     \n");
      sqlBuff.append("       CS136_MGB,     \n");
      sqlBuff.append("       CS136_MSA,     \n");       
      sqlBuff.append("       CS136_MDT,     \n");
      sqlBuff.append("       CS136_ECB,     \n");
      sqlBuff.append("       CS136_YDT,     \n");
      sqlBuff.append("       CS136_YPP,     \n");
      sqlBuff.append("       CS136_GNO,     \n");
      sqlBuff.append("       CS136_BDGBN    \n");
      sqlBuff.append("  FROM                \n");         
      sqlBuff.append("       SAPHEE.ZCST136 \n");
      sqlBuff.append(" WHERE                \n");
      sqlBuff.append("       MANDT = ?      \n");
      sqlBuff.append("   AND CS136_PJT = ?  \n");
      sqlBuff.append("   AND CS136_HNO = ?  \n");
      sqlBuff.append("   AND CS136_SEQ >= ? \n"); //FM계약도 전체 계약이 취소되도록 수정 20081211 LJH
      sqlBuff.append("   AND CS136_GND = ?  \n");
      sqlBuff.append("   AND CS136_JYM >= ? \n");
      sqlBuff.append("   AND CS136_UPN = ?  \n");
      sqlBuff.append("   AND CS136_CST = ?  \n");
      sqlBuff.append("   AND CS136_MGB = '' \n");
      sqlBuff.append("   AND CS136_PST <> 'A6' \n");
      
      try
      {
//         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
 	     conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxrpjt());
         pstmt.setString(idxparam++, vo.getCebxrhno());
         pstmt.setString(idxparam++, vo.getCebxrseq()); //FM계약도 전체 계약이 취소되도록 수정 20081211 LJH
         pstmt.setString(idxparam++, vo.getCebxrgnd());
         pstmt.setString(idxparam++, vo.getCebxrjym());
         pstmt.setString(idxparam++, vo.getCebxrupn());
         pstmt.setString(idxparam++, vo.getCebxrcst());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();

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
            rtnVo.setCebxrret(rs.getString("CS136_RET"));
            rtnVo.setCebxrrmk(rs.getString("CS136_RMK"));
            rtnVo.setCebxrsdt(rs.getString("CS136_SDT"));
            rtnVo.setCebxrss1(rs.getString("CS136_SS1"));
            rtnVo.setCebxrapp(rs.getString("CS136_APP"));
            rtnVo.setCebxrggb(rs.getString("CS136_GGB"));
            rtnVo.setCebxrgym(rs.getString("CS136_GYM"));
            rtnVo.setCebxrmgb(rs.getString("CS136_MGB"));
            rtnVo.setCebxrmsa(rs.getString("CS136_MSA"));
            rtnVo.setCebxrmdt(rs.getString("CS136_MDT"));
            rtnVo.setCebxrecb(rs.getString("CS136_ECB"));
            rtnVo.setCebxrydt(rs.getString("CS136_YDT"));
            rtnVo.setCebxrypp(rs.getString("CS136_YPP"));
            rtnVo.setGno(rs.getString("CS136_GNO"));
            rtnVo.setCebxrbdgbn(rs.getString("CS136_BDGBN"));

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
    * 작성자: jkkoo
    * 작성일: 2006-05-11 
    * 설  명: 실패보고시에 기 생성된 기성비계획중 실패보고 승인일자 다음달 부터의 기성비계획을 가져온다
    * 기  타:
    */
   public ArrayList selectRowComMethodForCancelPre(ArrayList paramList, Connection conn) throws Exception
   {
      TBEBXRF1Vo                 vo       = (TBEBXRF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXRF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();

      sqlBuff.append("  SELECT                           \n");
      sqlBuff.append("     A.MANDT,                      \n");
      sqlBuff.append("     A.CS136_PJT,                  \n");
      sqlBuff.append("     A.CS136_HNO,                  \n");
      sqlBuff.append("     A.CS136_SEQ,                  \n");
      sqlBuff.append("     A.CS136_GND,                  \n");
      sqlBuff.append("     A.CS136_JYM,                  \n");
      sqlBuff.append("     A.CS136_ISR,                  \n");
      sqlBuff.append("     A.CS136_UPN,                  \n");
      sqlBuff.append("     A.CS136_CST,                  \n");
      sqlBuff.append("     A.CS136_HTY,                  \n");
      sqlBuff.append("     A.CS136_ARA,                  \n");
      sqlBuff.append("     A.CS136_BSU,                  \n");
      sqlBuff.append("     A.CS136_GBU,                  \n");
      sqlBuff.append("     A.CS136_AGB,                  \n");
      sqlBuff.append("     A.CS136_ABG,                  \n");
      sqlBuff.append("     A.CS136_RGB,                  \n");
      sqlBuff.append("     A.CS136_PST,                  \n");
      sqlBuff.append("     A.CS136_FDT,                  \n");
      sqlBuff.append("     A.CS136_TOD,                  \n");
      sqlBuff.append("     A.CS136_JDQ,                  \n");
      sqlBuff.append("     A.CS136_JDJ,                  \n");
      sqlBuff.append("     A.CS136_BDA,                  \n");
      sqlBuff.append("     A.CS136_BPD,                  \n");
      sqlBuff.append("     A.CS136_CPR,                  \n");
      sqlBuff.append("     A.CS136_JSD,                  \n");
      sqlBuff.append("     A.CS136_JYQ,                  \n");
      sqlBuff.append("     A.CS136_JYC,                  \n");
      sqlBuff.append("     A.CS136_BYA,                  \n");
      sqlBuff.append("     A.CS136_IS1,                  \n");
      sqlBuff.append("     A.CS136_IS2,                  \n");
      sqlBuff.append("     A.CS136_IS3,                  \n");
      sqlBuff.append("     A.CS136_IS4,                  \n");
      sqlBuff.append("     A.CS136_JYD,                  \n");
      sqlBuff.append("     A.CS136_JYJ,                  \n");
      sqlBuff.append("     A.CS136_IDQ,                  \n");
      sqlBuff.append("     A.CS136_IDJ,                  \n");
      sqlBuff.append("     A.CS136_BAM,                  \n");
      sqlBuff.append("     A.CS136_IY1,                  \n");
      sqlBuff.append("     A.CS136_IY2,                  \n");
      sqlBuff.append("     A.CS136_IY3,                  \n");
      sqlBuff.append("     A.CS136_IY4,                  \n");
      sqlBuff.append("     A.CS136_SDT,                  \n");
      sqlBuff.append("     A.CS136_SS1,                  \n");
      sqlBuff.append("     A.CS136_APP,                  \n");
      sqlBuff.append("     A.CS136_GGB,                  \n");
      sqlBuff.append("     A.CS136_GYM,                  \n");
      sqlBuff.append("     A.CS136_MGB,                  \n");
      sqlBuff.append("     A.CS136_MSA,                  \n");
      sqlBuff.append("     A.CS136_MDT,                  \n");
      sqlBuff.append("     A.CS136_BDGBN                 \n");
      sqlBuff.append("  FROM                             \n");
      sqlBuff.append("     SAPHEE.ZCST136 A,             \n");
      sqlBuff.append("     (                             \n");
      sqlBuff.append("     SELECT                        \n");
      sqlBuff.append("        MANDT,                     \n");
      sqlBuff.append("        CS136_PJT,                 \n");
      sqlBuff.append("        CS136_HNO,                 \n");
      sqlBuff.append("        CS136_SEQ,                 \n");
      sqlBuff.append("        CS136_GND,                 \n");
      sqlBuff.append("        CS136_JYM,                 \n");
      sqlBuff.append("        MAX(CS136_ISR) CS136_ISR   \n");
      sqlBuff.append("     FROM                          \n");
      sqlBuff.append("        SAPHEE.ZCST136             \n");
      sqlBuff.append("     GROUP BY                      \n");
      sqlBuff.append("        MANDT,                     \n");
      sqlBuff.append("        CS136_PJT,                 \n");
      sqlBuff.append("        CS136_HNO,                 \n");
      sqlBuff.append("        CS136_SEQ,                 \n");
      sqlBuff.append("        CS136_GND,                 \n");
      sqlBuff.append("        CS136_JYM                  \n");
      sqlBuff.append("     ) B                           \n");
      sqlBuff.append("  WHERE    1=1                     \n");
      sqlBuff.append("  AND   A.MANDT = ?                \n");
      sqlBuff.append("  AND   A.CS136_PJT = ?            \n");
      sqlBuff.append("  AND   A.CS136_HNO = ?            \n");
      sqlBuff.append("  AND   A.CS136_SEQ = ?            \n");
      sqlBuff.append("  AND   A.CS136_GND = ?            \n");
      sqlBuff.append("  AND   A.CS136_JYM < ?            \n");
      sqlBuff.append("  AND   A.CS136_UPN = ?            \n");
      sqlBuff.append("  AND   A.CS136_CST = ?            \n");
      sqlBuff.append("  AND   A.MANDT = B.MANDT          \n");
      sqlBuff.append("  AND   A.CS136_PJT = B.CS136_PJT  \n");
      sqlBuff.append("  AND   A.CS136_HNO = B.CS136_HNO  \n");
      sqlBuff.append("  AND   A.CS136_SEQ = B.CS136_SEQ  \n");
      sqlBuff.append("  AND   A.CS136_GND = B.CS136_GND  \n");
      sqlBuff.append("  AND   A.CS136_JYM = B.CS136_JYM  \n");
      sqlBuff.append("  AND   A.CS136_ISR = B.CS136_ISR  \n");

      try
      {
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
 	     conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxrpjt());
         pstmt.setString(idxparam++, vo.getCebxrhno());
         pstmt.setString(idxparam++, vo.getCebxrseq());
         pstmt.setString(idxparam++, vo.getCebxrgnd());
         pstmt.setString(idxparam++, vo.getCebxrjym());
         pstmt.setString(idxparam++, vo.getCebxrupn());
         pstmt.setString(idxparam++, vo.getCebxrcst());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();

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
            rtnVo.setCebxrsdt(rs.getString("CS136_SDT"));
            rtnVo.setCebxrss1(rs.getString("CS136_SS1"));
            rtnVo.setCebxrapp(rs.getString("CS136_APP"));
            rtnVo.setCebxrggb(rs.getString("CS136_GGB"));
            rtnVo.setCebxrgym(rs.getString("CS136_GYM"));
            rtnVo.setCebxrmgb(rs.getString("CS136_MGB"));
            rtnVo.setCebxrmsa(rs.getString("CS136_MSA"));
            rtnVo.setCebxrmdt(rs.getString("CS136_MDT"));
            rtnVo.setCebxrbdgbn(rs.getString("CS136_BDGBN"));

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
    * 작성자: jhlee
    * 작성일: 2008-09-02 
    * 설  명: 일시중지 보류 복구 등록시 복구할 데이터를 가져온다.
    * 기  타:
    */
   public ArrayList selectRowComMethodForRestore(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXRF1Vo                 vo       = (TBEBXRF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXRF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("SELECT                \n");
      sqlBuff.append("       MANDT,         \n");
      sqlBuff.append("       CS136_PJT,     \n");
      sqlBuff.append("       CS136_HNO,     \n");
      sqlBuff.append("       CS136_SEQ,     \n");
      sqlBuff.append("       CS136_GND,     \n");
      sqlBuff.append("       CS136_JYM,     \n");
      sqlBuff.append("       CS136_ISR,     \n");
      sqlBuff.append("       CS136_UPN,     \n");
      sqlBuff.append("       CS136_CST,     \n");
      sqlBuff.append("       CS136_HTY,     \n");
      sqlBuff.append("       CS136_ARA,     \n");
      sqlBuff.append("       CS136_BSU,     \n");
      sqlBuff.append("       CS136_GBU,     \n");
      sqlBuff.append("       CS136_AGB,     \n");      
      sqlBuff.append("       CS136_ABG,     \n");
      sqlBuff.append("       CS136_RGB,     \n");
      sqlBuff.append("       CS136_IIS,     \n");
      sqlBuff.append("       CS136_PLT,     \n");
      sqlBuff.append("       CS136_PST,     \n");
      sqlBuff.append("       CS136_FDT,     \n");
      sqlBuff.append("       CS136_TOD,     \n");
      sqlBuff.append("       CS136_JDQ,     \n");
      sqlBuff.append("       CS136_JDJ,     \n");
      sqlBuff.append("       CS136_BDA,     \n");
      sqlBuff.append("       CS136_BPD,     \n");
      sqlBuff.append("       CS136_CPR,     \n");
      sqlBuff.append("       CS136_JSD,     \n");
      sqlBuff.append("       CS136_JYQ,     \n");   
      sqlBuff.append("       CS136_JYC,     \n");
      sqlBuff.append("       CS136_BYA,     \n");
      sqlBuff.append("       CS136_IS1,     \n");
      sqlBuff.append("       CS136_IS2,     \n");
      sqlBuff.append("       CS136_IS3,     \n");
      sqlBuff.append("       CS136_IS4,     \n");
      sqlBuff.append("       CS136_JYD,     \n");      
      sqlBuff.append("       CS136_JYJ,     \n");
      sqlBuff.append("       CS136_IDQ,     \n");
      sqlBuff.append("       CS136_IDJ,     \n");
      sqlBuff.append("       CS136_BAM,     \n");
      sqlBuff.append("       CS136_IY1,     \n");
      sqlBuff.append("       CS136_IY2,     \n"); 
      sqlBuff.append("       CS136_IY3,     \n");
      sqlBuff.append("       CS136_IY4,     \n");
      sqlBuff.append("       CS136_JAR,     \n");
      sqlBuff.append("       CS136_SS1,     \n");
      sqlBuff.append("       CS136_SDT,     \n");
      sqlBuff.append("       CS136_APP,     \n");
      sqlBuff.append("       CS136_GGB,     \n");      
      sqlBuff.append("       CS136_GYM,     \n");
      sqlBuff.append("       CS136_MGB,     \n");
      sqlBuff.append("       CS136_MSA,     \n");       
      sqlBuff.append("       CS136_MDT,     \n");
      sqlBuff.append("       CS136_ECB,     \n");
      sqlBuff.append("       CS136_YDT,     \n");
      sqlBuff.append("       CS136_YPP,     \n");
      sqlBuff.append("       CS136_BDGBN    \n");
      sqlBuff.append("  FROM                \n");         
      sqlBuff.append("       SAPHEE.ZCST136 \n");
      sqlBuff.append(" WHERE                \n");
      sqlBuff.append("       MANDT = ?      \n");
      sqlBuff.append("   AND CS136_PJT = ?  \n");
      sqlBuff.append("   AND CS136_HNO = ?  \n");
      sqlBuff.append("   AND CS136_SEQ = ?  \n");
      sqlBuff.append("   AND CS136_GND = ?  \n");
      sqlBuff.append("   AND CS136_JYM >= ? \n");
      sqlBuff.append("   AND CS136_UPN = ?  \n");
      sqlBuff.append("   AND CS136_CST = ?  \n");
      
      try
      {
//         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
 	     conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxrpjt());
         pstmt.setString(idxparam++, vo.getCebxrhno());
         pstmt.setString(idxparam++, vo.getCebxrseq());
         pstmt.setString(idxparam++, vo.getCebxrgnd());
         pstmt.setString(idxparam++, vo.getCebxrjym());
         pstmt.setString(idxparam++, vo.getCebxrupn());
         pstmt.setString(idxparam++, vo.getCebxrcst());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();

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
            rtnVo.setCebxrsdt(rs.getString("CS136_SDT"));
            rtnVo.setCebxrss1(rs.getString("CS136_SS1"));
            rtnVo.setCebxrapp(rs.getString("CS136_APP"));
            rtnVo.setCebxrggb(rs.getString("CS136_GGB"));
            rtnVo.setCebxrgym(rs.getString("CS136_GYM"));
            rtnVo.setCebxrmgb(rs.getString("CS136_MGB"));
            rtnVo.setCebxrmsa(rs.getString("CS136_MSA"));
            rtnVo.setCebxrmdt(rs.getString("CS136_MDT"));
            rtnVo.setCebxrecb(rs.getString("CS136_ECB"));
            rtnVo.setCebxrydt(rs.getString("CS136_YDT"));
            rtnVo.setCebxrypp(rs.getString("CS136_YPP"));
            rtnVo.setCebxrbdgbn(rs.getString("CS136_BDGBN"));

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
    * 작성자: jhlee
    * 작성일: 2009-04-21 
    * 설  명: 일시중지 보류 복구 등록시 복구할 데이터를 가져온다.
    * 기  타:
    */
   public ArrayList selectRowComMethodForRestoreStop(ArrayList paramList, Connection conn) throws Exception
   {
      
      TBEBXRF1Vo                 vo       = (TBEBXRF1Vo) paramList.get(0);
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      
      TBEBXRF1Vo                 rtnVo;
      ArrayList                  list     = new ArrayList();
      
      sqlBuff.append("SELECT                \n");
      sqlBuff.append("       MANDT,         \n");
      sqlBuff.append("       CS136_PJT,     \n");
      sqlBuff.append("       CS136_HNO,     \n");
      sqlBuff.append("       CS136_SEQ,     \n");
      sqlBuff.append("       CS136_GND,     \n");
      sqlBuff.append("       CS136_JYM,     \n");
      sqlBuff.append("       CS136_ISR,     \n");
      sqlBuff.append("       CS136_UPN,     \n");
      sqlBuff.append("       CS136_CST,     \n");
      sqlBuff.append("       CS136_HTY,     \n");
      sqlBuff.append("       CS136_ARA,     \n");
      sqlBuff.append("       CS136_BSU,     \n");
      sqlBuff.append("       CS136_GBU,     \n");
      sqlBuff.append("       CS136_AGB,     \n");      
      sqlBuff.append("       CS136_ABG,     \n");
      sqlBuff.append("       CS136_RGB,     \n");
      sqlBuff.append("       CS136_IIS,     \n");
      sqlBuff.append("       CS136_PLT,     \n");
      sqlBuff.append("       CS136_PST,     \n");
      sqlBuff.append("       CS136_FDT,     \n");
      sqlBuff.append("       CS136_TOD,     \n");
      sqlBuff.append("       CS136_JDQ,     \n");
      sqlBuff.append("       CS136_JDJ,     \n");
      sqlBuff.append("       CS136_BDA,     \n");
      sqlBuff.append("       CS136_BPD,     \n");
      sqlBuff.append("       CS136_CPR,     \n");
      sqlBuff.append("       CS136_JSD,     \n");
      sqlBuff.append("       CS136_JYQ,     \n");   
      sqlBuff.append("       CS136_JYC,     \n");
      sqlBuff.append("       CS136_BYA,     \n");
      sqlBuff.append("       CS136_IS1,     \n");
      sqlBuff.append("       CS136_IS2,     \n");
      sqlBuff.append("       CS136_IS3,     \n");
      sqlBuff.append("       CS136_IS4,     \n");
      sqlBuff.append("       CS136_JYD,     \n");      
      sqlBuff.append("       CS136_JYJ,     \n");
      sqlBuff.append("       CS136_IDQ,     \n");
      sqlBuff.append("       CS136_IDJ,     \n");
      sqlBuff.append("       CS136_BAM,     \n");
      sqlBuff.append("       CS136_IY1,     \n");
      sqlBuff.append("       CS136_IY2,     \n"); 
      sqlBuff.append("       CS136_IY3,     \n");
      sqlBuff.append("       CS136_IY4,     \n");
      sqlBuff.append("       CS136_JAR,     \n");
      sqlBuff.append("       CS136_SS1,     \n");
      sqlBuff.append("       CS136_SDT,     \n");
      sqlBuff.append("       CS136_APP,     \n");
      sqlBuff.append("       CS136_GGB,     \n");      
      sqlBuff.append("       CS136_GYM,     \n");
      sqlBuff.append("       CS136_MGB,     \n");
      sqlBuff.append("       CS136_MSA,     \n");       
      sqlBuff.append("       CS136_MDT,     \n");
      sqlBuff.append("       CS136_ECB,     \n");
      sqlBuff.append("       CS136_YDT,     \n");
      sqlBuff.append("       CS136_YPP,     \n");
      sqlBuff.append("       CS136_BDGBN    \n");
      sqlBuff.append("  FROM                \n");         
      sqlBuff.append("       SAPHEE.ZCST136 \n");
      sqlBuff.append(" WHERE                \n");
      sqlBuff.append("       MANDT = ?      \n");
      sqlBuff.append("   AND CS136_PJT = ?  \n");
      sqlBuff.append("   AND CS136_HNO = ?  \n");
      sqlBuff.append("   AND CS136_SEQ >= ? \n");
      sqlBuff.append("   AND CS136_GND = ?  \n");
      sqlBuff.append("   AND CS136_JYM >= ? \n");
      sqlBuff.append("   AND CS136_UPN = ?  \n");
      sqlBuff.append("   AND CS136_CST = ?  \n");
      sqlBuff.append("   AND CS136_MGB = 'Y'  \n");
      sqlBuff.append("   AND CS136_MSA = '52' \n");

      try
      {
//         conn  = DBUtil.getConnection(DBConstants.EVLADMPOOL);
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
 	     conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         int idxparam = 1;
         pstmt.setString(idxparam++, vo.getMandt());
         pstmt.setString(idxparam++, vo.getCebxrpjt());
         pstmt.setString(idxparam++, vo.getCebxrhno());
         pstmt.setString(idxparam++, vo.getCebxrseq());
         pstmt.setString(idxparam++, vo.getCebxrgnd());
         pstmt.setString(idxparam++, vo.getCebxrjym());
         pstmt.setString(idxparam++, vo.getCebxrupn());
         pstmt.setString(idxparam++, vo.getCebxrcst());
         
         // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();

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
            rtnVo.setCebxrsdt(rs.getString("CS136_SDT"));
            rtnVo.setCebxrss1(rs.getString("CS136_SS1"));
            rtnVo.setCebxrapp(rs.getString("CS136_APP"));
            rtnVo.setCebxrggb(rs.getString("CS136_GGB"));
            rtnVo.setCebxrgym(rs.getString("CS136_GYM"));
            rtnVo.setCebxrmgb(rs.getString("CS136_MGB"));
            rtnVo.setCebxrmsa(rs.getString("CS136_MSA"));
            rtnVo.setCebxrmdt(rs.getString("CS136_MDT"));
            rtnVo.setCebxrecb(rs.getString("CS136_ECB"));
            rtnVo.setCebxrydt(rs.getString("CS136_YDT"));
            rtnVo.setCebxrypp(rs.getString("CS136_YPP"));
            rtnVo.setCebxrbdgbn(rs.getString("CS136_BDGBN"));

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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.19
    * 설  명 : 기성비계획의 발송 처리 
    * 기  타 : 
    */
   public void updateRowForKS030109_01(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         sqlBuff.append("UPDATE  EVLADM.TBEBXRF1      \n");
         sqlBuff.append("SET     CEBXRPST    = 'A3'   \n");
         sqlBuff.append("WHERE   CEBXRGYM    = ?      \n");
         sqlBuff.append("AND     CEBXRGND    = ?      \n");
         sqlBuff.append("AND     CEBXRBSU    = ?      \n");
		 sqlBuff.append("AND     CEBXRPST    < 'A3'   \n");
		 sqlBuff.append("AND     RTRIM(CEBXRMGB) = '' \n");
		 
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            
            pstmt.setString(idxparam++, vo.getCebxrgym());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrbsu());
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.19
    * 설  명 : 기성비계획의 접수 처리 
    * 기  타 : 
    */
   public void updateRowForKS030109_02(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         sqlBuff.append("UPDATE  EVLADM.TBEBXRF1      \n");
         sqlBuff.append("SET     CEBXRPST    = 'A5'   \n");
         sqlBuff.append("WHERE   CEBXRGYM    = ?      \n");
         sqlBuff.append("AND     CEBXRGND    = ?      \n");
         sqlBuff.append("AND     CEBXRBSU    = ?      \n");
		 sqlBuff.append("AND     CEBXRPST    < 'A5'   \n");
		 sqlBuff.append("AND     RTRIM(CEBXRMGB) = '' \n");
		
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            
            pstmt.setString(idxparam++, vo.getCebxrgym());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrbsu());
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.19
    * 설  명 : 기성비계획의 승인 처리 
    * 기  타 : 서울지역(E5), 지방(지역 NOT = E5)
    */
   public void updateRowForKS030109_03(ArrayList list, Connection conn, String gbn) throws Exception
   {
	  LoggablePreparedStatement pstmt1 = null;
	  LoggablePreparedStatement pstmt2 = null;
	  StringBuffer sqlBuff1 = new StringBuffer();
	  StringBuffer sqlBuff2 = new StringBuffer();
	  TBEBXRF1Vo vo1 = new TBEBXRF1Vo();
	  TBEBXRF1Vo vo2 = new TBEBXRF1Vo();
      
	  try
	  {
		if(gbn.equals("1") || gbn.equals("3")) { //승인(전체,서울)
			sqlBuff1.append("UPDATE  EVLADM.TBEBXRF1      \n");
			sqlBuff1.append("SET     CEBXRPST    = 'A6'   \n");
			sqlBuff1.append("WHERE   CEBXRGYM    = ?      \n");
			sqlBuff1.append("AND     CEBXRGND    = ?      \n");
			sqlBuff1.append("AND     CEBXRBSU    = ?      \n");
			sqlBuff1.append("AND     RTRIM(CEBXRMGB) = '' \n");
			
			pstmt1 = new LoggablePreparedStatement(conn, sqlBuff1.toString());
	         
			for(int i=0; i<list.size(); i++)
			{
			   int idxparam = 1;
			   vo1 = new TBEBXRF1Vo();
			   vo1 = (TBEBXRF1Vo)list.get(i);
	            
			   pstmt1.setString(idxparam++, vo1.getCebxrgym());
			   pstmt1.setString(idxparam++, vo1.getCebxrgnd());
			   pstmt1.setString(idxparam++, vo1.getCebxrbsu());
			   // logger.debug("\n" + ((LoggablePreparedStatement) pstmt1).getQueryString());
			   pstmt1.executeUpdate();
			}
		}
	  	
		if(gbn.equals("2") || gbn.equals("3")) { //승인(전체,지방)
			sqlBuff2.append("UPDATE  EVLADM.TBEBXRF1      \n");
			sqlBuff2.append("SET     CEBXRPST    = 'A6'   \n");
			sqlBuff2.append("WHERE   CEBXRGYM    = ?      \n");
			sqlBuff2.append("AND     CEBXRGND    = ?      \n");
			sqlBuff2.append("AND     CEBXRBSU    = ?      \n");
			sqlBuff2.append("AND     RTRIM(CEBXRMGB) = '' \n");
			
			pstmt2 = new LoggablePreparedStatement(conn, sqlBuff2.toString());
	         
			for(int i=0; i<list.size(); i++)
			{
			   int idxparam = 1;
			   vo2 = new TBEBXRF1Vo();
			   vo2 = (TBEBXRF1Vo)list.get(i);
	            
			   pstmt2.setString(idxparam++, vo2.getCebxrgym());
			   pstmt2.setString(idxparam++, vo2.getCebxrgnd());
			   pstmt2.setString(idxparam++, vo2.getCebxrbsu());
			   // logger.debug("\n" + ((LoggablePreparedStatement) pstmt2).getQueryString());
			   pstmt2.executeUpdate();
			}
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
			pstmt1.close();
			pstmt2.close();
		 }
		 catch (Exception e)
		 {
		 }
	  }
   }
     
   /**
    * 작성자 : jkkoo
    * 작성일 : 2006.05.20
    * 설  명 : 기성비계획의 조정내역 저장 
    * 기  타 : 협력사에서 조정요청내역을 수정.
    */
   public void updateRowForKS030102_01(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         sqlBuff.append("UPDATE  EVLADM.TBEBXRF1      \n");
         sqlBuff.append("SET     CEBXRJRQ    = ?,     \n"); //1.조정요청금액
         sqlBuff.append("        CEBXRYSY    = ?,     \n"); //2.조정요청사유
		 sqlBuff.append("        CEBXRJYD    = ?,     \n"); //3.조정요청일자
		 sqlBuff.append("        CEBXRJYJ    = ?      \n"); //4.조정요청자
         
         sqlBuff.append("WHERE   CEBXRPJT    = ?      \n"); //3.프로젝트
         sqlBuff.append("AND     CEBXRHGB    = ?      \n"); //4.호기구분
         sqlBuff.append("AND     CEBXRHNO    = ?      \n"); //5.호기번호
         sqlBuff.append("AND     CEBXRSEQ    = ?      \n"); //6.일련번호(BWE,BWM)
         sqlBuff.append("AND     CEBXRGND    = ?      \n"); //7.발주종류
         sqlBuff.append("AND     CEBXRJYM    = ?      \n"); //8.예정년월
         sqlBuff.append("AND     CEBXRISR    = ?      \n"); //9.일련번호
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);

            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrjrq()));
            pstmt.setString(idxparam++, vo.getCebxrysy());
			pstmt.setString(idxparam++, vo.getCebxrjyd());
			pstmt.setString(idxparam++, vo.getCebxrjyj());
            pstmt.setString(idxparam++, vo.getCebxrpjt());
            pstmt.setString(idxparam++, vo.getCebxrhgb());
            pstmt.setString(idxparam++, vo.getCebxrhno());
            pstmt.setString(idxparam++, vo.getCebxrseq());
            pstmt.setString(idxparam++, vo.getCebxrgnd());
            pstmt.setString(idxparam++, vo.getCebxrjym());
            pstmt.setString(idxparam++, vo.getCebxrisr());

            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.20
    * 설  명 : 기성비계획의 조정내역 저장 
    * 기  타 : 지방사무소에서  조정접수내역을 수정 저장.
    */
   public void updateRowForKS030102_02(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         sqlBuff.append("UPDATE  EVLADM.TBEBXRF1      \n");
         sqlBuff.append("SET     CEBXRJRV    = ?,     \n"); //1.조정접수금액
         sqlBuff.append("        CEBXRJSY    = ?,     \n"); //2.조정접수사유
		 sqlBuff.append("        CEBXRRDT    = ?,     \n"); //3.조정접수일자
		 sqlBuff.append("        CEBXRREQ    = ?      \n"); //4.조정접수자
		
         sqlBuff.append("WHERE   CEBXRPJT    = ?      \n"); //3.프로젝트
         sqlBuff.append("AND     CEBXRHGB    = ?      \n"); //4.호기구분
         sqlBuff.append("AND     CEBXRHNO    = ?      \n"); //5.호기번호
         sqlBuff.append("AND     CEBXRSEQ    = ?      \n"); //6.일련번호(BWE,BWM)
         sqlBuff.append("AND     CEBXRGND    = ?      \n"); //7.발주종류
         sqlBuff.append("AND     CEBXRJYM    = ?      \n"); //8.예정년월
         sqlBuff.append("AND     CEBXRISR    = ?      \n"); //9.일련번호
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);

            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrjrv())); //1.조정접수금액
            pstmt.setString(idxparam++, vo.getCebxrjsy());//2.조정접수사유
			pstmt.setString(idxparam++, vo.getCebxrrdt());
			pstmt.setString(idxparam++, vo.getCebxrreq());
            pstmt.setString(idxparam++, vo.getCebxrpjt());//3.프로젝트
            pstmt.setString(idxparam++, vo.getCebxrhgb());//4.호기구분
            pstmt.setString(idxparam++, vo.getCebxrhno());//5.호기번호
            pstmt.setString(idxparam++, vo.getCebxrseq());//6.일련번호(BWE,BWM)
            pstmt.setString(idxparam++, vo.getCebxrgnd());//7.발주종류
            pstmt.setString(idxparam++, vo.getCebxrjym());//8.예정년월
            pstmt.setString(idxparam++, vo.getCebxrisr());//9.일련번호

            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.20
    * 설  명 : 기성비계획의 조정내역 저장 
    * 기  타 : 고객지원부의 접수/승인내역을 수정 저장.
    */
   public void updateRowForKS030102_03(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         for(int i = 0; i < list.size(); i++)
         {
			int idxparam = 1;
			pstmt = null;
			sqlBuff = new StringBuffer();
			
			vo = new TBEBXRF1Vo();
			vo = (TBEBXRF1Vo) list.get(i);
			String bsu = vo.getCebxrbsu().trim();
			sqlBuff.append("UPDATE  EVLADM.TBEBXRF1      \n");
			sqlBuff.append("SET                          \n");

			if(bsu.substring(1,3).equals("E5")) { //서울현장일 경우 접수금액 및 사유 저장
				sqlBuff.append("        CEBXRJRV    = ?,     \n"); //1.조정접수금액
				sqlBuff.append("        CEBXRJSY    = ?,     \n"); //2.조정접수사유
				sqlBuff.append("        CEBXRRDT    = ?,     \n"); //3.조정접수일자
				sqlBuff.append("        CEBXRREQ    = ?,     \n"); //4.조정접수자
			}

			//서울외 현장일 경우 승인금액 및 사유만 저장
			sqlBuff.append("        CEBXRJAR    = ?,     \n"); //3.조정승인금액
			sqlBuff.append("        CEBXRSS1    = ?,     \n"); //4.조정승인사유
			sqlBuff.append("        CEBXRSDT    = ?,     \n"); //3.조정승인일자
			sqlBuff.append("        CEBXRAPP    = ?      \n"); //4.조정승인자

			sqlBuff.append("WHERE   CEBXRPJT    = ?      \n"); //5.프로젝트
			sqlBuff.append("AND     CEBXRHGB    = ?      \n"); //6.호기구분
			sqlBuff.append("AND     CEBXRHNO    = ?      \n"); //7.호기번호
			sqlBuff.append("AND     CEBXRSEQ    = ?      \n"); //8.일련번호(BWE,BWM)
			sqlBuff.append("AND     CEBXRGND    = ?      \n"); //9.발주종류
			sqlBuff.append("AND     CEBXRJYM    = ?      \n"); //10.예정년월
			sqlBuff.append("AND     CEBXRISR    = ?      \n"); //11.일련번호
			
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			
			if(bsu.substring(1,3).equals("E5")) { //서울현장일 경우 접수,승인금액 및 사유 저장
	            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrjar())); //1.조정접수금액
	            pstmt.setString(idxparam++, vo.getCebxrss1());//2.조정접수사유
				pstmt.setString(idxparam++, vo.getCebxrrdt());
				pstmt.setString(idxparam++, vo.getCebxrreq());
			}
			
			//서울외 현장일 경우 승인금액 및 사유만 저장
            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrjar())); //3.조정승인금액
            pstmt.setString(idxparam++, vo.getCebxrss1());//4.조정승인사유
			pstmt.setString(idxparam++, vo.getCebxrsdt());
			pstmt.setString(idxparam++, vo.getCebxrapp());

            pstmt.setString(idxparam++, vo.getCebxrpjt());//5.프로젝트
            pstmt.setString(idxparam++, vo.getCebxrhgb());//6.호기구분
            pstmt.setString(idxparam++, vo.getCebxrhno());//7.호기번호
            pstmt.setString(idxparam++, vo.getCebxrseq());//8.일련번호(BWE,BWM)
            pstmt.setString(idxparam++, vo.getCebxrgnd());//9.발주종류
            pstmt.setString(idxparam++, vo.getCebxrjym());//10.예정년월
            pstmt.setString(idxparam++, vo.getCebxrisr());//11.일련번호
            
            // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
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
    * 작성자 : jkkoo
    * 작성일 : 2006.05.20
    * 설  명 : 기성비계획의 조정내역 저장 
    * 기  타 : 지방사무소에서  조정승인내역을 수정 저장.
    */
   public void updateRowForKS030102_04(ArrayList list, Connection conn) throws Exception
   {
      LoggablePreparedStatement pstmt = null;
      StringBuffer sqlBuff = new StringBuffer();
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      try
      {
         sqlBuff.append("UPDATE  EVLADM.TBEBXRF1      \n");
         sqlBuff.append("SET     CEBXRJAR    = ?,     \n"); //1.조정접수금액
         sqlBuff.append("        CEBXRSS1    = ?      \n"); //2.조정접수사유
         
         sqlBuff.append("WHERE   CEBXRPJT    = ?      \n"); //3.프로젝트
         sqlBuff.append("AND     CEBXRHGB    = ?      \n"); //4.호기구분
         sqlBuff.append("AND     CEBXRHNO    = ?      \n"); //5.호기번호
         sqlBuff.append("AND     CEBXRSEQ    = ?      \n"); //6.일련번호(BWE,BWM)
         sqlBuff.append("AND     CEBXRGND    = ?      \n"); //7.발주종류
         sqlBuff.append("AND     CEBXRJYM    = ?      \n"); //8.예정년월
         sqlBuff.append("AND     CEBXRISR    = ?      \n"); //9.일련번호
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         for (int i = 0; i < list.size(); i++)
         {
            int idxparam = 1;
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);

            pstmt.setBigDecimal(idxparam++, new BigDecimal(vo.getCebxrjar())); //1.조정승인금액
            pstmt.setString(idxparam++, vo.getCebxrss1());//2.조정승인사유
            pstmt.setString(idxparam++, vo.getCebxrpjt());//3.프로젝트
            pstmt.setString(idxparam++, vo.getCebxrhgb());//4.호기구분
            pstmt.setString(idxparam++, vo.getCebxrhno());//5.호기번호
            pstmt.setString(idxparam++, vo.getCebxrseq());//6.일련번호(BWE,BWM)
            pstmt.setString(idxparam++, vo.getCebxrgnd());//7.발주종류
            pstmt.setString(idxparam++, vo.getCebxrjym());//8.예정년월
            pstmt.setString(idxparam++, vo.getCebxrisr());//9.일련번호

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
	* 작성자 : jhlee
	* 작성일 : 2006.05.24
	* 설  명 : 매출계획 및 기성비계획 변경 
	* 기  타 : 
	*/
   public void updateRowForMC010101(ArrayList list, Connection conn) throws Exception {
	  LoggablePreparedStatement pstmt = null;
	  StringBuffer sqlBuff = new StringBuffer();
	  TBEBXRF1Vo vo = new TBEBXRF1Vo();
	  
	  try {
		sqlBuff.append("UPDATE                                             \n");
		sqlBuff.append("       EVLADM.TBEBXRF1                             \n");
		sqlBuff.append("   SET                                             \n");
		sqlBuff.append("       CEBXRGYM = ?                                \n");
		sqlBuff.append(" WHERE                                             \n");
		sqlBuff.append("       (RTRIM(CEBXRMGB) = ''                       \n");
		sqlBuff.append("    OR (CEBXRGGB = '9' AND RTRIM(CEBXRMGB) <> '')) \n");
		sqlBuff.append("   AND CEBXRUPN = ?                                \n");
		sqlBuff.append("   AND CEBXRCST = ?                                \n");
		sqlBuff.append("   AND CEBXRJYM = ?                                \n");
//		sqlBuff.append("   AND CEBXRGYM = ?                                \n");
		 
		 pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
		 
		 for(int i=0; i<list.size(); i++) {
			int idxparam = 1;
			vo = new TBEBXRF1Vo();
			vo = (TBEBXRF1Vo) list.get(i);

			pstmt.setString(idxparam++, vo.getCebxrgy1());
			pstmt.setString(idxparam++, vo.getCebxrupn());
			pstmt.setString(idxparam++, vo.getCebxrcst());
			pstmt.setString(idxparam++, vo.getCebxrjym());

			// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			pstmt.addBatch();
		 }
		 
		 pstmt.executeBatch();
	  } catch (Exception e) {
		 throw e;
	  } finally {
		 try {
			if(pstmt != null) pstmt.close();
		 } catch (Exception e) {
		 }
	  }
   }

   /**
	* 작성자 : jhlee
	* 작성일 : 2006.09.20
	* 설  명 : 무상 기성비지급 계획 
	* 기  타 : 
	*/
   public void updateRowForMC010201(ArrayList list, Connection conn) throws Exception {
	  LoggablePreparedStatement pstmt = null;
	  StringBuffer sqlBuff = new StringBuffer();
	  TBEBXRF1Vo vo = new TBEBXRF1Vo();
	  
	  try {
		sqlBuff.append("UPDATE                                             \n");
		sqlBuff.append("       EVLADM.TBEBXRF1                             \n");
		sqlBuff.append("   SET                                             \n");
		sqlBuff.append("       CEBXRGYM = ?                                \n");
		sqlBuff.append(" WHERE                                             \n");
		sqlBuff.append("       (RTRIM(CEBXRMGB) = ''                       \n");
		sqlBuff.append("    OR (CEBXRGGB = '9' AND RTRIM(CEBXRMGB) <> '')) \n");
		sqlBuff.append("   AND CEBXRPJT = ?                                \n");
		sqlBuff.append("   AND CEBXRHGB = ?                                \n");
		sqlBuff.append("   AND CEBXRHNO = ?                                \n");
		sqlBuff.append("   AND CEBXRGND = ?                                \n");
		sqlBuff.append("   AND CEBXRJYM = ?                                \n");
		sqlBuff.append("   AND CEBXRMNO = ?                                \n");
		 
		 pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
		 
		 for(int i=0; i<list.size(); i++) {
			int idxparam = 1;
			vo = new TBEBXRF1Vo();
			vo = (TBEBXRF1Vo) list.get(i);
            
			pstmt.setString(idxparam++, vo.getCebxrgy1());
			pstmt.setString(idxparam++, vo.getCebxrpjt());
			pstmt.setString(idxparam++, vo.getCebxrhgb());
			pstmt.setString(idxparam++, vo.getCebxrhno());
			pstmt.setString(idxparam++, vo.getCebxrgnd());
			pstmt.setString(idxparam++, vo.getCebxrjym());
			pstmt.setString(idxparam++, vo.getCebxrmno());

			// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			pstmt.addBatch();
		 }
		 
		 pstmt.executeBatch();
	  } catch (Exception e) {
		 throw e;
	  } finally {
		 try {
			if(pstmt != null) pstmt.close();
		 } catch (Exception e) {
		 }
	  }
   }
   
   /**
	* 작성자 : jhlee
	* 작성일 : 2006.08.11
	* 설  명 : 월마감 관리
	* 기  타 : 
	*/
   public void updateRowForGY090101(ArrayList list, Connection conn, String gbn) throws Exception {
	  LoggablePreparedStatement pstmt = null;
	  StringBuffer sqlBuff = new StringBuffer();
	  TBEBXRF1Vo vo = new TBEBXRF1Vo();
	  
	  try {
		sqlBuff.append("UPDATE                       \n");
		sqlBuff.append("       EVLADM.TBEBXRF1       \n");
		sqlBuff.append("   SET                       \n");
		sqlBuff.append("       CEBXRPST = ?          \n");
		sqlBuff.append(" WHERE                       \n");
		sqlBuff.append("       CEBXRGYM = ?          \n");
		sqlBuff.append("   AND CEBXRARA = ?          \n");
		sqlBuff.append("   AND RTRIM(CEBXRMGB) = ''  \n");
		sqlBuff.append("   AND RTRIM(CEBXRMSA) = ''  \n");
		 
		 pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
		 
		 for(int i=0; i<list.size(); i++) {
			int idxparam = 1;
			vo = new TBEBXRF1Vo();
			vo = (TBEBXRF1Vo) list.get(i);
            
			if(gbn.equals("GY090101_ALL")) { //고객지원부일 경우
				if(vo.getCebxrara().trim().equals("E5")) { //지역이 서울일 경우
					pstmt.setString(idxparam++, "A1");
				} else {
					pstmt.setString(idxparam++, "A5");
				}
			} else {
				pstmt.setString(idxparam++, "A1");
			}
			pstmt.setString(idxparam++, vo.getCebxrgym());
			pstmt.setString(idxparam++, vo.getCebxrara());

			// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
//			pstmt.addBatch();
			pstmt.executeUpdate();
		 }

//		 pstmt.executeBatch();
	  } catch (Exception e) {
		 throw e;
	  } finally {
		 try {
			if(pstmt != null) pstmt.close();
		 } catch (Exception e) {
		 }
	  }
   }

	/**
	* 작성자 : ykchoi
	* 작성일 : 2006.09.18
	* 설  명 : 월마감 관리
	* 기  타 : 
	*/
	public void updateRowForGY100101(ArrayList list, Connection conn) throws Exception {
	  LoggablePreparedStatement pstmt = null;
	  StringBuffer sqlBuff = new StringBuffer();
	  TBEBXRF1Vo vo = new TBEBXRF1Vo();
		 	  
	  try {
		sqlBuff.append("UPDATE                       \n");
		sqlBuff.append("       EVLADM.TBEBXRF1       \n");
		sqlBuff.append("   SET                       \n");
		sqlBuff.append("       CEBXRPST = 'A1'       \n");
		sqlBuff.append(" WHERE                       \n");
		sqlBuff.append("       CEBXRGYM = ?          \n");
		sqlBuff.append("   AND CEBXRBSU = ?          \n");
		sqlBuff.append("   AND CEBXRGND = ?          \n");		
		sqlBuff.append("   AND RTRIM(CEBXRMGB) = ''  \n");
		 
		 pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
		 
		 for(int i=0; i<list.size(); i++) {
			int idxparam = 1;
			vo = new TBEBXRF1Vo();
			vo = (TBEBXRF1Vo) list.get(i);
            
			pstmt.setString(idxparam++, vo.getCebxrgym());
			pstmt.setString(idxparam++, vo.getCebxrbsu());
			pstmt.setString(idxparam++, vo.getCebxrgnd());			

			// logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
			pstmt.executeUpdate();
		 }
	  } catch (Exception e) {
		 throw e;
	  } finally {
		 try {
			if(pstmt != null) pstmt.close();
		 } catch (Exception e) {
		 }
	  }
	}   
   /**
	* 작성자 : jhlee
	* 작성일 : 2006.08.16
	* 설명   : 주(부)점검자 변경시에 update 한다. 
	* 기타   : 
	*/
   public void updateRowForGY090301(ArrayList list, Connection conn) throws Exception {
	  LoggablePreparedStatement  pstmt   = null;
	  StringBuffer               sqlBuff = null; 
	  TBEBXRF1Vo                 vo      = null;

	  try {
		 for(int i=0; i<list.size(); i++) {
			int idxparam = 1;
			sqlBuff = new StringBuffer(); 
			vo = new TBEBXRF1Vo();
			vo = (TBEBXRF1Vo) list.get(i);

			sqlBuff.append("UPDATE                                                                     \n");
			sqlBuff.append("       EVLADM.TBEBXRF1                                                     \n");
			sqlBuff.append("   SET                                                                     \n");
			sqlBuff.append("       CEBXRJUJ = ?                                                        \n");
			sqlBuff.append(" WHERE                                                                     \n");
			sqlBuff.append("       EXISTS (                                                            \n");
			sqlBuff.append("               SELECT                                                      \n");
			sqlBuff.append("                      'X'                                                  \n");
			sqlBuff.append("                 FROM                                                      \n");
			sqlBuff.append("                      EVLADM.TBEBWEF1                                      \n");
			sqlBuff.append("                WHERE                                                      \n");
			sqlBuff.append("                      CEBWEPJT = CEBXRPJT                                  \n");
			sqlBuff.append("                  AND CEBWEHGB = CEBXRHGB                                  \n");
			sqlBuff.append("                  AND CEBWEHNO = CEBXRHNO                                  \n");
			sqlBuff.append("                  AND CEBWESEQ = CEBXRSEQ                                  \n");
			sqlBuff.append("                  AND CEBWEGND = CEBXRGND                                  \n");
			sqlBuff.append("                  AND CEBWEPJT = ?                                         \n");
			sqlBuff.append("                  AND CEBWEHGB = ?                                         \n");
			sqlBuff.append("                  AND CEBWEHNO = ?                                         \n");
			sqlBuff.append("                  AND CEBWEBSU = ?                                         \n");
			sqlBuff.append("                  AND RTRIM(CEBWEBHD) > REPLACE(CHAR(CURRENT DATE),'-','') \n");
			sqlBuff.append("              )                                                            \n");
			sqlBuff.append("    OR EXISTS (                                                            \n");
			sqlBuff.append("               SELECT                                                      \n");
			sqlBuff.append("                      'X'                                                  \n");
			sqlBuff.append("                 FROM                                                      \n");
			sqlBuff.append("                      EVLADM.TBEBWMF1                                      \n");
			sqlBuff.append("                WHERE                                                      \n");
			sqlBuff.append("                      CEBWMUPN = CEBXRUPN                                  \n");
			sqlBuff.append("                  AND CEBWMCST = CEBXRCST                                  \n");
			sqlBuff.append("                  AND CEBWMPJT = CEBXRPJT                                  \n");
			sqlBuff.append("                  AND CEBWMHGB = CEBXRHGB                                  \n");
			sqlBuff.append("                  AND CEBWMHNO = CEBXRHNO                                  \n");
			sqlBuff.append("                  AND CEBWMSEQ = CEBXRSEQ                                  \n");
			sqlBuff.append("                  AND CEBWMGND = CEBXRGND                                  \n");
			sqlBuff.append("                  AND CEBWMPJT = ?                                         \n");
			sqlBuff.append("                  AND CEBWMHGB = ?                                         \n");
			sqlBuff.append("                  AND CEBWMHNO = ?                                         \n");
			sqlBuff.append("                  AND CEBWMBSU = ?                                         \n");
			sqlBuff.append("                  AND RTRIM(CEBWMUHJ) > REPLACE(CHAR(CURRENT DATE),'-','') \n");
			sqlBuff.append("              )                                                            \n");

			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());             

			pstmt.setString(idxparam++, vo.getCebxrjuj());
			pstmt.setString(idxparam++, vo.getCebxrpjt());
			pstmt.setString(idxparam++, vo.getCebxrhgb());
			pstmt.setString(idxparam++, vo.getCebxrhno());
			pstmt.setString(idxparam++, vo.getCebxrbsu());
			pstmt.setString(idxparam++, vo.getCebxrpjt());
			pstmt.setString(idxparam++, vo.getCebxrhgb());
			pstmt.setString(idxparam++, vo.getCebxrhno());
			pstmt.setString(idxparam++, vo.getCebxrbsu());

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
				  * 작성자 : shhwang
				  * 작성일 : 2006.08.28
				  * 설명    : 인원상주  승인취소시에 delete 한다 . 
				  * 기타    : 
		  */
		 public void deleteRowForGY020103_04(ArrayList list, Connection conn) throws Exception 
		 {
			LoggablePreparedStatement  pstmt    = null ;
			StringBuffer               sqlBuff  = new StringBuffer(); 
			TBEBXRF1Vo                 vo       = new TBEBXRF1Vo();     
        
			try { 
			
			   sqlBuff.append("  DELETE            \n");
			   sqlBuff.append("  FROM     EVLADM.TBEBXRF1 \n");
			   sqlBuff.append("WHERE  CEBXRPJT   = ?               \n");  
			   sqlBuff.append("AND  CEBXRHGB   =   ?               \n");
			   sqlBuff.append("AND  CEBXRHNO   =   ?               \n");
			   sqlBuff.append("AND  CEBXRSEQ   =   ?               \n");			   
			   sqlBuff.append("AND  CEBXRGND   =   ?               \n");
//			   sqlBuff.append("AND CEBXRJYM BETWEEN ? AND ?               \n");
//			   sqlBuff.append("AND  CEBXRISR   =   ?               \n");

			   pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

			   for (int i = 0; i < list.size(); i++) {
				  int idxparam = 1;
				  vo = new TBEBXRF1Vo();
				  vo = (TBEBXRF1Vo) list.get(i);
				
				  pstmt.setString(idxparam++, vo.getCebxrpjt());
				  pstmt.setString(idxparam++, vo.getCebxrhgb());
				  pstmt.setString(idxparam++, vo.getCebxrhno());   
				  pstmt.setString(idxparam++, vo.getCebxrseq()); 
				  pstmt.setString(idxparam++, vo.getCebxrgnd());   
//				  pstmt.setString(idxparam++, vo.getCebxrjymfrom());   
//				  pstmt.setString(idxparam++, vo.getCebxrjymto());   
//				  pstmt.setString(idxparam++, vo.getCebxrisr()); 
			   
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

   /**
			   * 작성자 : shhwang
			   * 작성일 : 2006.08.25
			   * 설명    : 유상계약 승인취소시에 delete 한다 . 
			   * 기타    : 
	   */
	  public void deleteRowForGY020105(ArrayList list, Connection conn) throws Exception 
	  {
		 LoggablePreparedStatement  pstmt    = null ;
		 StringBuffer               sqlBuff  = new StringBuffer(); 
		 TBEBXRF1Vo                 vo       = new TBEBXRF1Vo();     
        
		 try { 
			
			sqlBuff.append("  DELETE            \n");
			sqlBuff.append("  FROM     EVLADM.TBEBXRF1 \n");
			sqlBuff.append("WHERE  CEBXRPJT   = ?               \n");  
			sqlBuff.append("AND  CEBXRHGB   =   ?               \n");
			sqlBuff.append("AND  CEBXRHNO   =   ?               \n");
			sqlBuff.append("AND  CEBXRSEQ   =   ?               \n");
			sqlBuff.append("AND  CEBXRGND   =   ?               \n");
			//sqlBuff.append("AND CEBXRJYM BETWEEN ? AND ?               \n");

			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

			for (int i = 0; i < list.size(); i++) {
			   int idxparam = 1;
			   vo = new TBEBXRF1Vo();
			   vo = (TBEBXRF1Vo) list.get(i);
				
			   pstmt.setString(idxparam++, vo.getCebxrpjt());
			   pstmt.setString(idxparam++, vo.getCebxrhgb());
			   pstmt.setString(idxparam++, vo.getCebxrhno());   
			   pstmt.setString(idxparam++, vo.getCebxrseq());   
			   pstmt.setString(idxparam++, vo.getCebxrgnd());   
//			   pstmt.setString(idxparam++, vo.getCebxrjymfrom());   
//			   pstmt.setString(idxparam++, vo.getCebxrjymto());   
			   
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
