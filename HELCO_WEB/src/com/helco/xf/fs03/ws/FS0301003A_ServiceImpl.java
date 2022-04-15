package com.helco.xf.fs03.ws;

import com.tobesoft.platform.data.Dataset;
import com.helco.xf.comm.Utillity;

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

public class FS0301003A_ServiceImpl extends AbstractBusinessService implements FS0301003A_Service {

    private static final long serialVersionUID = 1L;
    static Logger logger;

    
	/**
	 * ���� SAP ���� �� �̷� ������ ����
	 * @param ctx
	 * @throws Exception
	 */
    @Override
	public void psPidSapCreate(BusinessContext ctx) throws Exception {
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_zfst0304 = ctx.getInputDataset("ds_zfst0304");
		Dataset ds_zfst0306_list = ctx.getInputDataset("ds_zfst0306_list");
		Dataset ds_zfst0307 = ctx.getInputDataset("ds_zfst0307");
		Dataset ds_zfst0307_list = ctx.getInputDataset("ds_zfst0307_list");
		Dataset ds_zfst0309_list = ctx.getInputDataset("ds_zfst0309_list");
		Dataset ds_zfst0310_list = ctx.getInputDataset("ds_zfst0310_list");
		Dataset ds_zfst0311_list = ctx.getInputDataset("ds_zfst0311_list");
		Dataset ds_doc_appr = ctx.getInputDataset("ds_doc_appr");
		
		String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		// ���������� ZC01 �� ��쿡�� ���� ������ ������ ������ �Ŀ� WBS�� �����Ѵ�.
		
		// ���������� ZR01, ZR02, ZR03�� ��쿡�� WBS ������ ������ �Ŀ� ���� ������ ������ �����Ѵ�.
		

        // SAP���� ���޵� ���� �������� ���� ���̺� ������ �ݿ��� �� ���� ���� ������ �����Ѵ�.
		// zfst0304 ���� ������ ������ȣ, ������Ʈ ��ȣ -> 
		// zfst0307 ���� ǰ���� WBS ���� ��ȣ ����
		// ���� ���� ������ �����Ѵ�.
		DatasetSqlRequest sql_zfst0304 = SqlMapManagerUtility.makeSqlRequestForDataset("fs03:FS0301003A_U1");
		sql_zfst0304.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sql_zfst0304.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		// ���� ���� ���� - ���� ���� �Ϸ� 250
		ds_zfst0304.setColumn(0, "OCNT_STAT", "250");
		sql_zfst0304.addParamObject("ds_zfst0304", ds_zfst0304);
		executor.execute(sql_zfst0304);

		DatasetSqlRequest sql_zfst0307 = SqlMapManagerUtility.makeSqlRequestForDataset("fs03:FS0301003A_U4");
		sql_zfst0307.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sql_zfst0307.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		sql_zfst0307.addParamObject("sql_zfst0307", sql_zfst0307);
		executor.execute(sql_zfst0307);
		
		// ������� ����
		/*
		DatasetSqlRequest sqlM_zfst0200 = SqlMapManagerUtility.makeSqlRequestForDataset("fs01:FS_COM0701_M2");
		sqlM_zfst0200.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlM_zfst0200.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		sqlM_zfst0200.addParamObject("ds_doc_appr", ds_doc_appr);
		executor.execute(sqlM_zfst0200);
*/
		
		// ���� ���� ����
		DatasetSqlRequest sqlU_zfst0200 = SqlMapManagerUtility.makeSqlRequestForDataset("fs01:FS_COM0701_U1");
		sqlU_zfst0200.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlU_zfst0200.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		sqlU_zfst0200.addParamObject("ds_doc_appr", ds_doc_appr);
		executor.execute(sqlU_zfst0200);

		// ���� �Ϸ�� �����ͷ� ��� �����͸� �����Ѵ�.
		// ZFST0305 �̷� ����, ZFST0308 �̷� �����Ѵ�. -> Ȥ�� �𸣴� ���� �� ���� �������� �����Ѵ�.
		DatasetSqlRequest sqlD_zfst0305 = SqlMapManagerUtility.makeSqlRequestForDataset("fs03:FS0301003A_D2");
		sqlD_zfst0305.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlD_zfst0305.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		sqlD_zfst0305.addParamObject("ds_cond", ds_cond);
		executor.execute(sqlD_zfst0305);

		DatasetSqlRequest sqlI_zfst0305 = SqlMapManagerUtility.makeSqlRequestForDataset("fs03:FS0301003A_I2");
		sqlI_zfst0305.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlI_zfst0305.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		sqlI_zfst0305.addParamObject("ds_cond", ds_cond);
		executor.execute(sqlI_zfst0305);
		
		
		DatasetSqlRequest sqlD_zfst0308 = SqlMapManagerUtility.makeSqlRequestForDataset("fs03:FS0301003A_D5");
		sqlD_zfst0308.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlD_zfst0308.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		sqlD_zfst0308.addParamObject("ds_cond", ds_cond);
		executor.execute(sqlD_zfst0308);

		DatasetSqlRequest sqlI_zfst0308 = SqlMapManagerUtility.makeSqlRequestForDataset("fs03:FS0301003A_I5");
		sqlI_zfst0308.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlI_zfst0308.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		sqlI_zfst0308.addParamObject("ds_cond", ds_cond);
		executor.execute(sqlI_zfst0308);
	}

	/**
	 * ���� ���� ������ ����
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void bilSapSave(BusinessContext ctx) throws Exception {
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_zfst0310_list = ctx.getInputDataset("ds_zfst0310_list");
		// ���� �Ϸ� ���¿��� ���� �����͸� ���� �� ó��
		
		// ���� ���� ó�� �� SAP�� �����Ͽ� ���� ���� ���°� �Ϸ�� ������ �ǿ� ���� ���� �� ������� �����ϸ�
		// ���� ���� ������ �ֽ�ȭ �� �� ����ڿ��� �޼����� ����Ѵ�.
		
		// SAP �ݿ� �Ϸ� �Ŀ� �������� ���� ������ �ݿ� �� ��� �Ѵ�.
	}
}
