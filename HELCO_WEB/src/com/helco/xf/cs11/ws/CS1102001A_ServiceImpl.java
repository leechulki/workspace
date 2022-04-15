package com.helco.xf.cs11.ws;

import java.sql.Connection;
import java.sql.ResultSet;

import com.tobesoft.platform.data.Dataset;

import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;
import com.helco.xf.comm.Utillity;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.TitUtility;

public class CS1102001A_ServiceImpl extends AbstractBusinessService implements
		CS1102001A_Service {
	static Logger logger;

	public void save2(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		logger = ServiceManagerFactory.getLogger();

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 무상보수발주정보 select(zcst116)
		DatasetSqlRequest zcst116s1
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_S6");
		zcst116s1.addParamObject("ds_list", ds_list);

		// 무상보수발주정보 select(zcst116)
		DatasetSqlRequest zcst116s2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_S7");
		zcst116s2.addParamObject("ds_list", ds_list);

		// 무상보수발주정보 select(zcst116) - 추가발주의 경우 발주기간 중복 체크
		DatasetSqlRequest zcst116s3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_S8");
		zcst116s3.addParamObject("ds_list", ds_list);

		// 무상보수발주정보 insert(zcst116)
		DatasetSqlRequest zcst116i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_I1");
		zcst116i.addParamObject("ds_list", ds_list);

		// 무상보수발주정보 update(zcst116)
		DatasetSqlRequest zcst116u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_U3");
		zcst116u.addParamObject("ds_list", ds_list);

		// 마스터호기테이블 update(zmaster02)
		DatasetSqlRequest zmaster02u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_U2");
		zmaster02u.addParamObject("ds_list", ds_list);

		zcst116s1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for(int i=0; i<ds_list.getRowCount(); i++) {
			
			if("I".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst116i.setRowIndex(i);
				executor.execute(zcst116i);

				zmaster02u.setRowIndex(i);
				executor.execute(zmaster02u);
			} else if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst116u.setRowIndex(i);
				executor.execute(zcst116u);

				zmaster02u.setRowIndex(i);
				executor.execute(zmaster02u);
			}

			if("I".equals(ds_list.getColumnAsString(i, "FLAG")) || "U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				//추가발주 체크
				zcst116s3.setRowIndex(i);
				Dataset dsRtn116s3 = (Dataset)executor.query(zcst116s3).getResultObject();
				
				if(dsRtn116s3.getColumnAsInteger(0, "CNT") > 0) {
					throw new BizException(String.valueOf(i + 1) + " 번째 행의 데이타는 중복되었습니다. 발주기간을 확인하세요.");			
				}
				
				zcst116s1.setRowIndex(i);
				Dataset dsRtn116a = (Dataset)executor.query(zcst116s1).getResultObject();
	
//				logger.debug("dsRtn116a.getColumnAsInteger(0, 'CNT') ==> [" + dsRtn116a.getColumnAsInteger(0, "CNT") + "]");
	
				if(dsRtn116a.getColumnAsInteger(0, "CNT") > 0) { //무상발주데이터 생성시 발주개월수과 사용개월수 체크
					throw new BizException("SAP와 WEB의 무상발주개월수가 다릅니다. 저장처리를 취소합니다.");
				}
	
				zcst116s2.setRowIndex(i);
				Dataset dsRtn116b = (Dataset)executor.query(zcst116s2).getResultObject();
	
//				logger.debug("dsRtn116b.getColumnAsInteger(0, 'CNT') ==> [" + dsRtn116b.getColumnAsInteger(0, "CNT") + "]");
	
				if(dsRtn116b.getColumnAsInteger(0, "CNT") > 1) { //무상발주데이터 생성시 중복생성여부 체크
					throw new BizException("동일한 호기가 중복생성되었습니다. 저장처리를 취소합니다.");
				}
			}
		}
	}

	public void save(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		String t_gno = "";
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_U6");
		zcst111u.addParamObject("ds_list", ds_list);

		// 무상보수 발주정보 insert(zcst116)
		DatasetSqlRequest zcst116i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_I2");
		zcst116i.addParamObject("ds_list", ds_list);

		// 마스터호기테이블 update(zmaster02)
		DatasetSqlRequest zmaster02u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_U5");
		zmaster02u.addParamObject("ds_list", ds_list);

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		ComMethodVo comVo;
		ComMethodDao comDao;

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				t_gno = ds_list.getColumnAsString(i, "PJT") + 
				        ds_list.getColumnAsString(i, "HNO") + 
				        "-" + "R";

				zcst116i.addParamObject("GNO", t_gno);

				zcst116i.setRowIndex(i);
				zcst111u.setRowIndex(i);
				zmaster02u.setRowIndex(i);

				executor.execute(zcst116i);
				executor.execute(zcst111u);
				executor.execute(zmaster02u);

				comVo = new ComMethodVo();
				comDao = new ComMethodDao();

				String t_seq = getSeq(ctx.getInputVariable("G_MANDT"), ds_list.getColumnAsString(i, "PJT"), ds_list.getColumnAsString(i, "HNO"), ds_list.getColumnAsString(i, "GND"));

				if("A".equals(ds_list.getColumnAsString(i, "GND"))) //무상공사
	            	comVo.setDataId("11");
	            else if("B".equals(ds_list.getColumnAsString(i, "GND"))) //무상일반
	            	comVo.setDataId("12");
	            else
	            	throw new BizException("Data Id가 정의 되지 않았습니다");

	            comVo.setMandt(ctx.getInputVariable("G_MANDT"));
	            comVo.setJobGubun("I");
	            comVo.setUpn(ds_list.getColumnAsString(i, "PJT"));
	            comVo.setCst("Z99");
	            comVo.setPjt(ds_list.getColumnAsString(i, "PJT"));
	            comVo.setHno(ds_list.getColumnAsString(i, "HNO"));
	            comVo.setSeq(t_seq);
	            comVo.setFirstUserId("");
	            comVo.setGno(t_gno);
	            
	            int rtnCode = comDao.SetBXRBXL(comVo);
			}
		}
	}

	public void sendback(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_U3");
		zcst111u.addParamObject("ds_list", ds_list);

		// 무상보수 발주정보 update(zcst116)
		DatasetSqlRequest zcst116u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_U4");
		zcst116u.addParamObject("ds_list", ds_list);

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst116u.setRowIndex(i);
				zcst111u.setRowIndex(i);

				executor.execute(zcst116u);
				executor.execute(zcst111u);
			}
		}
	}

	   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-01
     * 설  명:
     * 기  타:
     */
   public String getSeq(String mandt, String pjt, String hno, String gnd) throws Exception
   {
      String seq = "";

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      Connection                 conn     = null;

      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("        MAX(CS116_SEQ) SEQ      \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("        SAPHEE.ZCST116          \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   MANDT = ?               \n");
      sqlBuff.append("  AND   CS116_PJT = ?           \n");
      sqlBuff.append("  AND   CS116_HNO = ?           \n");
      sqlBuff.append("  AND   CS116_GND = ?           \n");

      try
      {
     	 String db_con = Utillity.getSapJndi(mandt);
         conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         pstmt.setString(1, mandt);
         pstmt.setString(2, pjt);
         pstmt.setString(3, hno);
         pstmt.setString(4, gnd);
       //  logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());

         rs = pstmt.executeQuery();
         if (rs.next())
         {
        	 seq = rs.getString("SEQ");
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
      return seq;
   }
   
   public void tempSave(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
//		logger = ServiceManagerFactory.getLogger();

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		System.out.println(ds_list.toString());

		// 무상보수발주정보 select(zcst116)
		System.out.println("무상보수발주정보 select(zcst116)");
		DatasetSqlRequest zcst116s1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_S6");
		zcst116s1.addParamObject("ds_list", ds_list);

		// 무상보수발주정보 select(zcst116)
		System.out.println("무상보수발주정보 select(zcst116)");
		DatasetSqlRequest zcst116s2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_S7");
		zcst116s2.addParamObject("ds_list", ds_list);

		// 무상보수발주정보 insert(zcst116)
		System.out.println("무상보수발주정보 insert(zcst116)");
		DatasetSqlRequest zcst116i  = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_I1");
		zcst116i.addParamObject("ds_list", ds_list);

		// 무상보수발주정보 update(zcst116)
		System.out.println("무상보수발주정보 update(zcst116)");
		DatasetSqlRequest zcst116u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_U3");
		zcst116u.addParamObject("ds_list", ds_list);
		
		// 마스터호기테이블 update(zmaster02)
		DatasetSqlRequest zmaster02u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1102001A_U2");
		zmaster02u.addParamObject("ds_list", ds_list);

		zcst116s1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for( int i=0; i < ds_list.getRowCount(); i++ ) {
			if("I".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst116i.setRowIndex(i);
				System.out.println("무상발주등록중.....");
				System.out.println("ABG      :" + zcst116i.getParamValue("ABG")   		);
				System.out.println("AGB      :" + zcst116i.getParamValue("AGB")     );
				System.out.println("ARA      :" + zcst116i.getParamValue("ARA")     );
				System.out.println("BGT      :" + zcst116i.getParamValue("BGT")     );
				System.out.println("BHD      :" + zcst116i.getParamValue("BHD")     );
				System.out.println("BPM      :" + zcst116i.getParamValue("BPM")     );
				System.out.println("BSU      :" + zcst116i.getParamValue("BSU")     );
				System.out.println("CBS      :" + zcst116i.getParamValue("CBS")     );
				System.out.println("FDT      :" + zcst116i.getParamValue("FDT")     );
				System.out.println("GND      :" + zcst116i.getParamValue("GND")     );
				System.out.println("HNO      :" + zcst116i.getParamValue("HNO")     );
				System.out.println("HTY      :" + zcst116i.getParamValue("HTY")     );
				System.out.println("JUC      :" + zcst116i.getParamValue("JUC")     );
				System.out.println("MON      :" + zcst116i.getParamValue("MON")     );
				System.out.println("PJT      :" + zcst116i.getParamValue("PJT")     );
				System.out.println("ZSPEC1   :" + zcst116i.getParamValue("ZSPEC1")     );
				System.out.println("G_USER_ID:" + zcst116i.getParamValue("G_USER_ID")     );
				System.out.println("ZSPEC    :" + zcst116i.getParamValue("ZSPEC")     );
				System.out.println("GBU      :" + zcst116i.getParamValue("GBU")     );
				System.out.println("G_MANDT  :" + zcst116i.getParamValue("G_MANDT")     );
				if( executor.execute(zcst116i) < 1 ) {
					throw new BizException("저장되지 않았습니다.");
				}

//				zmaster02u.setRowIndex(i);
//				executor.execute(zmaster02u);
			} else if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst116u.setRowIndex(i);
				executor.execute(zcst116u);

				zmaster02u.setRowIndex(i);
				executor.execute(zmaster02u);
			}
			/*
			if("I".equals(ds_list.getColumnAsString(i, "FLAG")) || "U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst116s1.setRowIndex(i);
				Dataset dsRtn116a = (Dataset)executor.query(zcst116s1).getResultObject();
	
//				logger.debug("dsRtn116a.getColumnAsInteger(0, 'CNT') ==> [" + dsRtn116a.getColumnAsInteger(0, "CNT") + "]");
	
				if(dsRtn116a.getColumnAsInteger(0, "CNT") > 0) { //무상발주데이터 생성시 발주개월수과 사용개월수 체크
					throw new BizException("SAP와 WEB의 무상발주개월수가 다릅니다. 저장처리를 취소합니다.");
				}
	
				zcst116s2.setRowIndex(i);
				Dataset dsRtn116b = (Dataset)executor.query(zcst116s2).getResultObject();
	
//				logger.debug("dsRtn116b.getColumnAsInteger(0, 'CNT') ==> [" + dsRtn116b.getColumnAsInteger(0, "CNT") + "]");
	
				if(dsRtn116b.getColumnAsInteger(0, "CNT") > 1) { //무상발주데이터 생성시 중복생성여부 체크
					throw new BizException("동일한 호기가 중복생성되었습니다. 저장처리를 취소합니다.");
				}
			}
			*/
			
			
		}
	}
}
