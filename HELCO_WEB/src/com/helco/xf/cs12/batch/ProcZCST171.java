/**
 * �� �� �� : ProcZCST171.java
 * ��    �� : ���� �����ȹ���� ��ġó��
 *            �ý����� cron job�� ��ϵǾ� ���� ������ ���� ��.
 *            �α����� ��ġ�� ���� ������ bwz_batch.sh �Ǵ� bwz_batch.bat ������ LOG_DIR ȯ�溯���� ���õǾ�������
 *            �⺻ �����δ� Current������ logs��� ���������� ����
 *            ��) set LOG_DIR = ./logs
 * �� �� �� : jhlee
 * �� �� �� : 2009-06-24 ���� 03:30:00
 * ���泻�� :
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

		logger.debug("\n[���� �����ȹ ����] ��ġ ���μ��� Start : " + CommonUtil.getToday() + " " + CommonUtil.getCurrentTimeHHMMSS());
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

			//���� �� ���� �����ȹ ����  DELETE
			cs171.delProcForCST171(mandt, conn, yym2);

			//���� �� ���� �����ȹ ���� INSERT
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
		logger.debug("\n [���� �����ȹ ����] ��ġ���μ��� End : " + CommonUtil.getToday() + " " + CommonUtil.getCurrentTimeHHMMSS());
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	/**
	* �ۼ��� : 
	* �ۼ��� : 
	* ��  �� : DB�� Ŀ�ؼ� �������� 
	* ��  Ÿ :
	*/ 
	public Connection getConnection(String mdt) throws SQLException, Exception
	{
		ResourceFactory resourceFactory =(ResourceFactory)ServiceManagerFactory.getServiceObject("DB2_HEQ"); //�ƹ��ų� ����ص� ����

		if( resourceFactory == null ) {
			return null;
		} 
		TransactionResource res = resourceFactory.makeResource(mdt); //mdt�� jdbc/hed, jdbc/heq, jdbc/hep �߿� ������� ��������� �߿�

		return (Connection) res.getResource();
	}
}
