package com.helco.xf.fs02.ws;

import com.tobesoft.platform.data.Dataset;
import com.helco.xf.comm.Utillity;

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

public class FS0201002A_ServiceImpl extends AbstractBusinessService implements FS0201002A_Service {

	/**
	 * �������� ������ ����
	 * @param ctx
	 * @throws Exception
	 */
    private static final long serialVersionUID = 1L;
    static Logger logger;
	@Override
	public void escsMasterSave(BusinessContext ctx) throws Exception {
		Dataset ds_zfst0201_list = ctx.getInputDataset("ds_zfst0201_list");
		Dataset ds_zfst0201 = ctx.getInputDataset("ds_zfst0201");
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		String escsNumb = ds_cond.getColumnAsString(0, "ESCS_NUMB");
		
		String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		if("".equals(escsNumb)) {
	        // ����������ȣ ä��
			DatasetSqlRequest sqlKey = SqlMapManagerUtility.makeSqlRequestForDataset("fs02:FS0201002F_S1");
			sqlKey.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			Dataset ds_zfst0200 = (Dataset)executor.query(sqlKey).getResultObject();

			// ����������ȣ ������ �Է�
			DatasetSqlRequest sql_zfst0200 = SqlMapManagerUtility.makeSqlRequestForDataset("fs02:FS0201002A_I1");
			sql_zfst0200.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			sql_zfst0200.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
			sql_zfst0200.addParamObject("ds_zfst0200", ds_zfst0200);
			executor.execute(sql_zfst0200);

			// ����������ȣ ǰ��(�����׸�) ���
			DatasetSqlRequest sql_zfst0201 = SqlMapManagerUtility.makeSqlRequestForDataset("fs02:FS0201002A_I2");
			sql_zfst0201.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			sql_zfst0201.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

			// ä���� Ű ���� �Է��Ѵ�.
			for(int i=0; i<ds_zfst0201_list.getRowCount(); i++) {
				ds_zfst0201.deleteAll();
				int nRow = ds_zfst0201.appendRow();
				ds_zfst0201.setColumn(nRow, "ESCS_NUMB", ds_zfst0200.getColumn(0, "ESCS_NUMB"));
				ds_zfst0201.setColumn(nRow, "ESCS_NTIM", ds_zfst0200.getColumn(0, "ESCS_NTIM"));
				ds_zfst0201.setColumn(nRow, "ITEM_DIYN", ds_zfst0201_list.getColumn(i, "ITEM_DIYN"));
				ds_zfst0201.setColumn(nRow, "ESCS_GROP", ds_zfst0201_list.getColumn(i, "ESCS_GROP"));
				ds_zfst0201.setColumn(nRow, "ESCS_UPGR", ds_zfst0201_list.getColumn(i, "ESCS_UPGR"));
				ds_zfst0201.setColumn(nRow, "ESCS_ITEM", ds_zfst0201_list.getColumn(i, "ESCS_ITEM"));
				ds_zfst0201.setColumn(nRow, "EXTN_ESCS", ds_zfst0201_list.getColumn(i, "EXTN_ESCS"));
				ds_zfst0201.setColumn(nRow, "ESCE_NAME", ds_zfst0201_list.getColumn(i, "ESCE_NAME"));
				ds_zfst0201.setColumn(nRow, "ESIT_SEQN", ds_zfst0201_list.getColumn(i, "ESIT_SEQN"));
				sql_zfst0201.addParamObject("ds_zfst0201", ds_zfst0201);
				executor.execute(sql_zfst0201);
			}
			ctx.addOutputDataset(ds_zfst0200);	
		} else {
			// ����������ȣ ǰ��(�����׸�) ���
			DatasetSqlRequest sql_zfst0201 = SqlMapManagerUtility.makeSqlRequestForDataset("fs02:FS0201002A_I2");
			sql_zfst0201.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			sql_zfst0201.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

			// ä���� Ű ���� �Է��Ѵ�.
			for(int i=0; i<ds_zfst0201_list.getRowCount(); i++) {
				ds_zfst0201.deleteAll();
				int nRow = ds_zfst0201.appendRow();
				ds_zfst0201.setColumn(nRow, "ESCS_NUMB", ds_cond.getColumnAsString(0, "ESCS_NUMB"));
				ds_zfst0201.setColumn(nRow, "ESCS_NTIM", ds_cond.getColumnAsString(0, "ESCS_NTIM"));
				ds_zfst0201.setColumn(nRow, "ITEM_DIYN", ds_zfst0201_list.getColumn(i, "ITEM_DIYN"));
				ds_zfst0201.setColumn(nRow, "ESCS_GROP", ds_zfst0201_list.getColumn(i, "ESCS_GROP"));
				ds_zfst0201.setColumn(nRow, "ESCS_UPGR", ds_zfst0201_list.getColumn(i, "ESCS_UPGR"));
				ds_zfst0201.setColumn(nRow, "ESCS_ITEM", ds_zfst0201_list.getColumn(i, "ESCS_ITEM"));
				ds_zfst0201.setColumn(nRow, "EXTN_ESCS", ds_zfst0201_list.getColumn(i, "EXTN_ESCS"));
				ds_zfst0201.setColumn(nRow, "ESCE_NAME", ds_zfst0201_list.getColumn(i, "ESCE_NAME"));
				ds_zfst0201.setColumn(nRow, "ESIT_SEQN", ds_zfst0201_list.getColumn(i, "ESIT_SEQN"));
				sql_zfst0201.addParamObject("ds_zfst0201", ds_zfst0201);
				executor.execute(sql_zfst0201);
			}
		}
	}
}
