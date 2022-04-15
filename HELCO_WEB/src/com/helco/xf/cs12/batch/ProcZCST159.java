/**
 * �� �� �� : ProcTBEBWZF1.java
 * ��    �� : �����̰���ġ ó��
 *            �ý����� cron job�� ��ϵǾ� �ſ� 1�� ������  ���� ��.
 *            �α����� ��ġ�� ���� ������ bwz_batch.sh �Ǵ� bwz_batch.bat ������ LOG_DIR ȯ�溯���� ���õǾ�������
 *            �⺻ �����δ� Current������ logs��� ���������� ����
 *            ��) set LOG_DIR = ./logs
 * �� �� �� : jkkoo
 * �� �� �� : 2006-05-24 ���� 8:46:06
 * ���泻�� :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.cs12.batch;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsn.jdf.Utility;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.service.core.logger.Logger;
import tit.service.resource.ResourceFactory;
import tit.service.resource.TransactionResource;
import tit.service.scheduler.ScheduleTask;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ZCST159Vo;
import tit.service.business.config.ConfigUtility;

public class ProcZCST159 extends AbstractBusinessService implements ScheduleTask
{
   static Logger logger; 
   static String seq = "";
   private String mandt = "183";
   private String mName = null;

   public void run() throws Exception {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
      System.out.println(format.format(new Date()) + ": " + mName);


      logger = ServiceManagerFactory.getLogger();
        
      logger.debug("\n�������� ������� ��ġ ���μ��� Start : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
      Connection conn = null;

      ArrayList list = new ArrayList();
      ZCST159Vo vo = new ZCST159Vo();
 	  TransForCST159 cs159 = new TransForCST159(logger); //ZCST159
 	  
 	  mandt = ConfigUtility.getString("MANDT");
 	  String yym = CommonUtil.getToday2().substring(0,6);
 	  

	  try
      {
		 String db_con = Utillity.getSapJndi(mandt);
	     conn = getConnection(db_con);
         if( conn == null ) return;
         conn.setAutoCommit(false);
         
         
         list = getCST159(mandt, conn);
         
         // ����� ������ ����ó��
         cs159.delProcForCST159(mandt, conn, yym);
         
//         for(int i=0; i<list.size();i++)
//         {
//            vo = new ZCST159Vo();
//            vo = (ZCST159Vo) list.get(i);
//            
//            // ������� INSERT
			cs159.ProcTransForCST159(mandt, conn, list);
//                 
//         }
         conn.commit();
      }
      catch(SQLException se)
      {
         logger.debug("\n" +  se.toString());
         se.printStackTrace();
         try
         {
            conn.rollback();
            BatchConstants.close(null,null,conn);
         }
         catch(Exception e)
         {
         }
      }
      catch(Exception e)
      {
         logger.debug("\n" +  e.toString());
         e.printStackTrace();
         try
         {
            conn.rollback();
            BatchConstants.close(null,null,conn);
         }
         catch(Exception te)
         {
         }
      }      
      finally
      {
         try
         {
            BatchConstants.close(null,null,conn);
         }
         catch(Exception e)
         {
            e.printStackTrace();
            logger.debug("\n" + e.toString());
         }
      }
      logger.debug("\n �������� ������� ��ġ���μ��� End : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
   }

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}
	
   /**
    * �ۼ��� : ȫ����
    * �ۼ��� :
    * ��  �� : ������� ȣ�� ������ �����´�. 
    * ��  Ÿ :
    */ 
   public ArrayList getCST159(String mandt, Connection conn) throws Exception
   {  
      ArrayList rtnList = new ArrayList();
      
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      ZCST159Vo                 rtnVo;
      
      sqlBuff.append(" SELECT                     \n");
      sqlBuff.append(" 	   MANDT AS MANDT         \n");
      sqlBuff.append(" 	  , PJT AS PJT            \n");
      sqlBuff.append(" 	  , HNO AS HNO            \n");
      sqlBuff.append(" 	  , VALUE(BSU,'') AS BSU  \n");
      sqlBuff.append(" 	  , VALUE(MPS,'') AS MPS  \n");
      sqlBuff.append(" 	  , VALUE(MPT,'') AS MPT  \n");
      sqlBuff.append(" 	  , VALUE(MMF,'') AS MMF  \n");
      sqlBuff.append(" 	  , VALUE(MMT,'') AS MMT  \n");
      sqlBuff.append(" 	  , VALUE(MYS,'') AS MYS  \n");
      sqlBuff.append(" 	  , VALUE(MYT,'') AS MYT  \n");
      sqlBuff.append(" 	  , VALUE(JKD,'') AS JKD  \n");
      sqlBuff.append(" 	  , VALUE(ST2,'') AS ST2  \n");
      sqlBuff.append(" 	  , VALUE(SMT,'') AS SMT  \n");
      sqlBuff.append(" 	  , VALUE(IT2,'') AS IT2  \n");
      sqlBuff.append(" 	  , VALUE(UMD,'') AS UMD  \n");
      sqlBuff.append(" 	  , VALUE(JJD,'') AS JJD  \n");
      sqlBuff.append(" 	  , VALUE(UDT,'') AS UDT  \n");
      sqlBuff.append(" 	  , VALUE(FSD,'') AS FSD  \n");
      sqlBuff.append(" 	  , VALUE(HST,'') AS HST  \n");
      sqlBuff.append("  FROM SAPHEE.ZCST111       \n");
      sqlBuff.append("  WHERE                     \n");
      sqlBuff.append(" 	 MANDT = ?                \n");
      sqlBuff.append(" 	 AND BSU > ''             \n");
      sqlBuff.append(" 	 AND SUBSTR(HNO,1,1) NOT IN ('J','G') \n");
      sqlBuff.append("  ORDER BY BSU              \n");
      sqlBuff.append("  WITH UR                   \n");
      
      try
      {
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         pstmt.setString(1, mandt);
         rs = pstmt.executeQuery();
         
         String bsu = "";
         String date = CommonUtil.getToday2();
         String yym = date.substring(0,6);
         int[] arr = new int[11];
         
         while (rs.next())
         {
            if ( !bsu.equals("") && !bsu.equals(rs.getString("BSU").trim())){
            	rtnVo = new ZCST159Vo();
            	rtnVo.setMandt(mandt);
            	rtnVo.setCs159_yym(yym);
            	rtnVo.setCs159_bsu(bsu);
            	rtnVo.setCs159_nrv(arr[0]);
            	rtnVo.setCs159_mkj(arr[1]);
            	rtnVo.setCs159_ukj(arr[2]);
            	rtnVo.setCs159_mij(arr[3]);
            	rtnVo.setCs159_uij(arr[4]);
            	rtnVo.setCs159_sil(arr[5]);
            	rtnVo.setCs159_isj(arr[6]);
            	rtnVo.setCs159_je1(0);
            	rtnVo.setCs159_je2(0);
            	rtnVo.setCs159_je3(0);
            	rtnVo.setCs159_mkm(arr[7]);
            	rtnVo.setCs159_ukm(arr[8]);
            	rtnVo.setCs159_mim(arr[9]);
            	rtnVo.setCs159_uim(arr[10]);
            	rtnVo.setCs159_me1(0);
            	rtnVo.setCs159_me2(0);
            	rtnVo.setCs159_me3(0);
            	rtnVo.setCs159_rdt(date);
            	rtnVo.setCs159_udt("");

            	Utility.fixNullAndTrim(rtnVo);
                rtnList.add(rtnVo);
                
                arr = new int[11];
            }
            
//            if (Utility.trim(rs.getString("HST")).equals("F") && !Utility.trim(rs.getString("FSD")).equals("") 
//            		&& Integer.parseInt(CommonUtil.NullBlankToZero(rs.getString("FSD"))) > Integer.parseInt(CommonUtil.NullBlankToZero(rs.getString("IT2"))))
            if (Utility.trim(rs.getString("HST")).equals("F") && !Utility.trim(rs.getString("FSD")).equals(""))
            {
            	arr[5]++;           // cs159_sil ���� ���� ���
            } 
            else if (Utility.trim(rs.getString("HST")).equals("S") && !Utility.trim(rs.getString("JJD")).equals("")
            		&& ((!Utility.trim(rs.getString("UDT")).equals("") 
                			&& (Integer.parseInt(CommonUtil.NullBlankToZero(rs.getString("JJD"))) > Integer.parseInt(CommonUtil.NullBlankToZero(rs.getString("UDT")))))
//                        	|| (!Utility.trim(rs.getString("UDT")).equals("") && !Utility.trim(rs.getString("JJD")).equals("")))) 
            				|| (Utility.trim(rs.getString("UDT")).equals("") && !Utility.trim(rs.getString("JJD")).equals("")))) //20081124 LJH ���� (���� ����)
            {
        		arr[6]++;       // cs159_isj �Ͻ�����/�������
			}
        	else if (!Utility.trim(rs.getString("IT2")).equals(""))
        	{
				if (Integer.parseInt(CommonUtil.NullBlankToZero(rs.getString("UMD"))) > Integer.parseInt(date)) 
				{
					arr[4]++;   // cs159_uij �����Ϲ� ������
				} 
				else 
				{
					arr[10]++;   // cs159_uim �����Ϲ� ������
				}
			}
        	else if (!Utility.trim(rs.getString("MYS")).equals(""))
        	{
				if (Integer.parseInt(CommonUtil.NullBlankToZero(rs.getString("MYT"))) > Integer.parseInt(date)) 
				{
					arr[3]++;   // cs159_mij �����Ϲ� ������
				} 
				else 
				{
					arr[9]++;   // cs159_mim �����Ϲ� ������
				}
			}
        	else if (!Utility.trim(rs.getString("ST2")).equals(""))
        	{
				if (Integer.parseInt(CommonUtil.NullBlankToZero(rs.getString("SMT"))) > Integer.parseInt(date)) 
				{
					arr[2]++;   // cs159_ukj ������� ������
				} 
				else if (Integer.parseInt(CommonUtil.NullBlankToZero(rs.getString("SMT"))) > Integer.parseInt(CommonUtil.NullBlankToZero(rs.getString("MPS"))))
				{
					arr[8]++;   // cs159_ukm ������� ������
				}
			}
        	else if (!Utility.trim(rs.getString("MPS")).equals(""))
        	{
				if (Integer.parseInt(CommonUtil.NullBlankToZero(rs.getString("MPT"))) > Integer.parseInt(date)) 
				{
					arr[1]++;   // cs159_mkj ������� ������
				} 
				else 
				{
					arr[7]++;   // cs159_mkm ������� ������
				}
			}
        	else
        	{
        		arr[0]++;       // cs159_nrv �ű��μ� �������
        	}
            
            bsu = rs.getString("BSU").trim();
         }
         logger.debug("\n Step1. �������� ȣ�� ���� Select : " + rtnList.size() );
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
      return rtnList;
   }
 
   /**
    * �ۼ��� : 
    * �ۼ��� : 
    * ��  �� : DB�� Ŀ�ؼ� �������� 
    * ��  Ÿ :
    */ 

   public Connection getConnection(String mdt) throws SQLException, Exception
   {
	   ResourceFactory resourceFactory =(ResourceFactory)
	   		ServiceManagerFactory.getServiceObject("DB2_HEQ"); //�ƹ��ų� ����ص� ����
	   
	   if( resourceFactory == null ) {
		   return null;
	   } 
	   TransactionResource res = resourceFactory.makeResource(mdt); //mdt�� jdbc/hed, jdbc/heq, jdbc/hep �߿� ������� ��������� �߿�

	   return (Connection) res.getResource();
   }
}
