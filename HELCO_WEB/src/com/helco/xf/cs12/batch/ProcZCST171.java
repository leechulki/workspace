/**
 * 파 일 명 : ProcZCST171.java
 * 설    명 : 보수 사업계획정보 배치처리
 *            시스템의 cron job에 등록되어 매일 새벽에 실행 됨.
 *            로그파일 위치는 실행 파일인 bwz_batch.sh 또는 bwz_batch.bat 파일의 LOG_DIR 환경변수에 세팅되어있으며
 *            기본 값으로는 Current폴더의 logs라는 서브폴더로 정의
 *            예) set LOG_DIR = ./logs
 * 작 성 자 : jhlee
 * 작 성 일 : 2009-06-24 오후 03:30:00
 * 변경내역 :
 */
package com.helco.xf.cs12.batch;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.service.core.logger.Logger;
import tit.service.resource.ResourceFactory;
import tit.service.resource.TransactionResource;
import tit.service.scheduler.ScheduleTask;
import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import tit.service.business.config.ConfigUtility;

public class ProcZCST171 extends AbstractBusinessService implements ScheduleTask
{
	static Logger logger; 
	static String seq = "";
	private String mandt = "183";
	private String mName = null;

	public void run() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		System.out.println(format.format(new Date()) + " : " + mName);

		logger = ServiceManagerFactory.getLogger();

		logger.debug("\n[보수 사업계획 정보] 배치 프로세스 Start : " + CommonUtil.getToday() + " " + CommonUtil.getCurrentTimeHHMMSS());
		Connection conn = null;

		TransForCST171 cs171 = new TransForCST171(logger); //ZCST171

//		mandt = ConfigUtility.getString("MANDT");
		mandt = "183";
		String yym1 = CommonUtil.getToday2().substring(0,4) + "01";
		String yym2 = CommonUtil.getToday2().substring(0,6);

		try
		{
			String db_con = Utillity.getSapJndi(mandt);
			conn = getConnection(db_con);
			if( conn == null ) return;
			conn.setAutoCommit(false);

			//현재 월 보수 사업계획 정보  DELETE
			cs171.delProcForCST171(mandt, conn, yym2);

			//현재 월 보수 사업계획 정보 INSERT
			cs171.ProcTransForCST171(mandt, conn, yym1, yym2);

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
		logger.debug("\n [보수 사업계획 정보] 배치프로세스 End : " + CommonUtil.getToday() + " " + CommonUtil.getCurrentTimeHHMMSS());
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	/**
	* 작성자 : 
	* 작성일 : 
	* 설  명 : DB의 커넥션 가져오기 
	* 기  타 :
	*/ 
	public Connection getConnection(String mdt) throws SQLException, Exception
	{
		ResourceFactory resourceFactory =(ResourceFactory)ServiceManagerFactory.getServiceObject("DB2_HEQ"); //아무거나 사용해도 무방

		if( resourceFactory == null ) {
			return null;
		} 
		TransactionResource res = resourceFactory.makeResource(mdt); //mdt를 jdbc/hed, jdbc/heq, jdbc/hep 중에 어느것을 사용할지가 중요

		return (Connection) res.getResource();
	}
}
