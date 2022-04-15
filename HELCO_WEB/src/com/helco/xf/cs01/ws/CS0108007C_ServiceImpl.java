package com.helco.xf.cs01.ws;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tobesoft.platform.data.Dataset;
import com.helco.xf.comm.Utillity;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

public class CS0108007C_ServiceImpl extends AbstractBusinessService implements CS0108007C_Service {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger;


	/**
	 * ������ üũ ���ռ� ������ ����
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void saveMatchData(BusinessContext ctx) throws Exception {
		logger = ServiceManagerFactory.getLogger();
		Dataset ds_zcstmatchms_insert = ctx.getInputDataset("ds_zcstmatchms_insert");
		Dataset ds_zcstmatchms_update = ctx.getInputDataset("ds_zcstmatchms_update");
		Dataset ds_zcstmatchdt_insert = ctx.getInputDataset("ds_zcstmatchdt_insert");
		Dataset ds_zcstmatchdt_update = ctx.getInputDataset("ds_zcstmatchdt_update");
		Dataset ds_zcstmatchdt_delete = ctx.getInputDataset("ds_zcstmatchdt_delete");
		
		String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

        // �Է� ������ ó��
		if( ds_zcstmatchms_insert.getRowCount() > 0 ) {
			DatasetSqlRequest sqlZcstmatchmsI = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108007A_I1");
			sqlZcstmatchmsI.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			sqlZcstmatchmsI.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
			for(int i=0; i < ds_zcstmatchms_insert.getRowCount(); i++) {
				DatasetSqlRequest sqlKey = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108007A_S3");
				sqlKey.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
				sqlKey.addParamObject("CTYPE", ds_zcstmatchms_insert.getColumnAsString(i, "CTYPE"));
				sqlKey.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
				Dataset ds_zcstmatchms_key = (Dataset)executor.query(sqlKey).getResultObject();
				String TYPE = ds_zcstmatchms_insert.getColumnAsString(i, "TYPE");

				String BLOCKNO = "0001";
				if( ds_zcstmatchms_key.getColumnAsString(0, "BLOCKNO") == null ) {
					BLOCKNO = TYPE+BLOCKNO;
				} else {
					BLOCKNO = TYPE+ds_zcstmatchms_key.getColumnAsString(0, "BLOCKNO");
				}

				// ������ ������ ����
				sqlZcstmatchmsI.addParamObject("CTYPE", ds_zcstmatchms_insert.getColumnAsString(i, "CTYPE"));
				sqlZcstmatchmsI.addParamObject("TYPE", ds_zcstmatchms_insert.getColumnAsString(i, "TYPE"));
				sqlZcstmatchmsI.addParamObject("BLOCKNO", BLOCKNO);
				sqlZcstmatchmsI.addParamObject("MESSAGE", ds_zcstmatchms_insert.getColumnAsString(i, "MESSAGE"));
				sqlZcstmatchmsI.addParamObject("TRANS", ds_zcstmatchms_insert.getColumnAsString(i, "TRANS"));
				sqlZcstmatchmsI.addParamObject("DFLAG", ds_zcstmatchms_insert.getColumnAsString(i, "DFLAG"));
				executor.execute(sqlZcstmatchmsI);

				// ������ ���� �Է��Ѵ�.
				for(int j=0; j < ds_zcstmatchdt_insert.getRowCount(); j++) {
					if( ds_zcstmatchdt_insert.getColumnAsString(j, "BLOCKNO").equals("") ) {
						DatasetSqlRequest sqlZcstmatchdtI = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108007A_I2");
						sqlZcstmatchdtI.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
						sqlZcstmatchdtI.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
						sqlZcstmatchdtI.addParamObject("CTYPE", ds_zcstmatchdt_insert.getColumnAsString(j, "CTYPE"));
						sqlZcstmatchdtI.addParamObject("TYPE", ds_zcstmatchdt_insert.getColumnAsString(j, "TYPE"));
						sqlZcstmatchdtI.addParamObject("BLOCKNO", BLOCKNO);
						sqlZcstmatchdtI.addParamObject("BLOCKNOSEQ", BLOCKNO+ds_zcstmatchdt_insert.getColumnAsString(j, "BLOCKNOSEQ"));
						sqlZcstmatchdtI.addParamObject("ZORDER", ds_zcstmatchdt_insert.getColumnAsString(j, "ZORDER"));
						sqlZcstmatchdtI.addParamObject("SPEC", ds_zcstmatchdt_insert.getColumnAsString(j, "SPEC"));
						sqlZcstmatchdtI.addParamObject("CALCULAT", ds_zcstmatchdt_insert.getColumnAsString(j, "CALCULAT"));
						sqlZcstmatchdtI.addParamObject("CALCULATCK", ds_zcstmatchdt_insert.getColumnAsString(j, "CALCULATCK"));
						sqlZcstmatchdtI.addParamObject("DFLAG", ds_zcstmatchdt_insert.getColumnAsString(j, "DFLAG"));
						executor.execute(sqlZcstmatchdtI);
					}
				}
			}
		}

		// ���ռ� ������ ���� ������ ó��
		for(int i=0; i < ds_zcstmatchms_update.getRowCount(); i++) {
			DatasetSqlRequest sqlZcstmatchmsU = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108007A_U1");
			sqlZcstmatchmsU.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			sqlZcstmatchmsU.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
			sqlZcstmatchmsU.addParamObject("CTYPE", ds_zcstmatchms_update.getColumnAsString(i, "CTYPE"));
			sqlZcstmatchmsU.addParamObject("TYPE", ds_zcstmatchms_update.getColumnAsString(i, "TYPE"));
			sqlZcstmatchmsU.addParamObject("BLOCKNO", ds_zcstmatchms_update.getColumnAsString(i, "BLOCKNO"));
			sqlZcstmatchmsU.addParamObject("MESSAGE", ds_zcstmatchms_update.getColumnAsString(i, "MESSAGE"));
			sqlZcstmatchmsU.addParamObject("TRANS", ds_zcstmatchms_update.getColumnAsString(i, "TRANS"));
			sqlZcstmatchmsU.addParamObject("DFLAG", ds_zcstmatchms_update.getColumnAsString(i, "DFLAG"));
			executor.execute(sqlZcstmatchmsU);
		}

		// ���ռ� ������ ���� ������ ó��
		for(int i=0; i < ds_zcstmatchdt_delete.getRowCount(); i++) {
			DatasetSqlRequest sqlZcstmatchdtD = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108007A_D1");
			sqlZcstmatchdtD.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			sqlZcstmatchdtD.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
			sqlZcstmatchdtD.addParamObject("CTYPE", ds_zcstmatchdt_delete.getColumnAsString(i, "CTYPE"));
			sqlZcstmatchdtD.addParamObject("TYPE", ds_zcstmatchdt_delete.getColumnAsString(i, "TYPE"));
			sqlZcstmatchdtD.addParamObject("BLOCKNO", ds_zcstmatchdt_delete.getColumnAsString(i, "BLOCKNO"));
			sqlZcstmatchdtD.addParamObject("BLOCKNOSEQ", ds_zcstmatchdt_delete.getColumnAsString(i, "BLOCKNOSEQ"));
			sqlZcstmatchdtD.addParamObject("ZORDER", ds_zcstmatchdt_delete.getColumnAsString(i, "ZORDER"));
			executor.execute(sqlZcstmatchdtD);
		}

		// ���ռ� ������ ���� ������ ó��
		for(int i=0; i < ds_zcstmatchdt_update.getRowCount(); i++) {
			DatasetSqlRequest sqlZcstmatchdtU = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108007A_U2");
			sqlZcstmatchdtU.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			sqlZcstmatchdtU.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
			sqlZcstmatchdtU.addParamObject("CTYPE", ds_zcstmatchdt_update.getColumnAsString(i, "CTYPE"));
			sqlZcstmatchdtU.addParamObject("TYPE", ds_zcstmatchdt_update.getColumnAsString(i, "TYPE"));
			sqlZcstmatchdtU.addParamObject("BLOCKNO", ds_zcstmatchdt_update.getColumnAsString(i, "BLOCKNO"));
			sqlZcstmatchdtU.addParamObject("BLOCKNOSEQ", ds_zcstmatchdt_update.getColumnAsString(i, "BLOCKNOSEQ"));
			sqlZcstmatchdtU.addParamObject("ZORDER", ds_zcstmatchdt_update.getColumnAsString(i, "ZORDER"));
			sqlZcstmatchdtU.addParamObject("SPEC", ds_zcstmatchdt_update.getColumnAsString(i, "SPEC"));
			sqlZcstmatchdtU.addParamObject("CALCULAT", ds_zcstmatchdt_update.getColumnAsString(i, "CALCULAT"));
			sqlZcstmatchdtU.addParamObject("CALCULATCK", ds_zcstmatchdt_update.getColumnAsString(i, "CALCULATCK"));
			sqlZcstmatchdtU.addParamObject("DFLAG", ds_zcstmatchdt_update.getColumnAsString(i, "DFLAG"));
			executor.execute(sqlZcstmatchdtU);
		}

		// ���ռ� ������ �Է� ������ ó��
		for(int i=0; i < ds_zcstmatchdt_insert.getRowCount(); i++) {
			if( !ds_zcstmatchdt_insert.getColumnAsString(i, "BLOCKNO").equals("") ) {
				DatasetSqlRequest sqlZcstmatchdtI = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108007A_I2");
				sqlZcstmatchdtI.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
				sqlZcstmatchdtI.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
				sqlZcstmatchdtI.addParamObject("CTYPE", ds_zcstmatchdt_insert.getColumnAsString(i, "CTYPE"));
				sqlZcstmatchdtI.addParamObject("TYPE", ds_zcstmatchdt_insert.getColumnAsString(i, "TYPE"));
				sqlZcstmatchdtI.addParamObject("BLOCKNO", ds_zcstmatchdt_insert.getColumnAsString(i, "BLOCKNO"));
				sqlZcstmatchdtI.addParamObject("BLOCKNOSEQ", ds_zcstmatchdt_insert.getColumnAsString(i, "BLOCKNOSEQ"));
				sqlZcstmatchdtI.addParamObject("ZORDER", ds_zcstmatchdt_insert.getColumnAsString(i, "ZORDER"));
				sqlZcstmatchdtI.addParamObject("SPEC", ds_zcstmatchdt_insert.getColumnAsString(i, "SPEC"));
				sqlZcstmatchdtI.addParamObject("CALCULAT", ds_zcstmatchdt_insert.getColumnAsString(i, "CALCULAT"));
				sqlZcstmatchdtI.addParamObject("CALCULATCK", ds_zcstmatchdt_insert.getColumnAsString(i, "CALCULATCK"));
				sqlZcstmatchdtI.addParamObject("DFLAG", ds_zcstmatchdt_insert.getColumnAsString(i, "DFLAG"));
				executor.execute(sqlZcstmatchdtI);
			}
		}
	}

	/**
	 * ���ռ� ����ľ� ���ǵ� ���Ư���ڵ� ����
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void calSpecInsert(BusinessContext ctx)
			throws Exception {
        // ������� �Ľ��Ͽ� ��� Ư�� ����
	    Dataset ds_zcstmatchid = ctx.getInputDataset("ds_zcstmatchid");
		String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		// ZCSTMATCH_TMP ����
		DatasetSqlRequest sqlZcstmatchTmpD = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108007C_D1");
		DatasetSqlRequest sqlZcstmatchTmpI = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108007C_I2");
		sqlZcstmatchTmpD.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlZcstmatchTmpI.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		for(int i=0; i < ds_zcstmatchid.getRowCount(); i++) {
			String ID_SPEC = ds_zcstmatchid.getColumnAsString(i, "SPEC");
			String CALCULAT = ds_zcstmatchid.getColumnAsString(i, "CALCULAT");
			sqlZcstmatchTmpD.addParamObject("ID_SPEC", ID_SPEC);
			sqlZcstmatchTmpI.addParamObject("ID_SPEC", ID_SPEC);
			
			// ����ó��
			executor.execute(sqlZcstmatchTmpD);
			
			HashMap<String, String> specMap = new HashMap<String, String>();
			// �����͸� �Ľ��Ѵ�.
			String regexp = "\\{(\\w+)\\}";
			Pattern pattern = Pattern.compile( regexp);
			Matcher matcher = pattern.matcher( CALCULAT.replaceAll(System.getProperty("line.separator"), ""));
	        while ( matcher.find()) {
                String propertyName =  matcher.group(1);
                if( !specMap.containsKey(propertyName) ) {
                    specMap.put(propertyName, propertyName);
                }
	        }
			
			// �Ľ̵� �����͸� �Է��Ѵ�.
			Iterator<String> iterator = specMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				sqlZcstmatchTmpI.addParamObject("SPEC", key);
				// �Է�ó��
				executor.execute(sqlZcstmatchTmpI);
			}
		}
	}
}

