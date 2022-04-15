package com.helco.xf.cs01.ws;

import com.tobesoft.platform.data.Dataset;
import com.helco.xf.comm.Utillity;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

public class CS0108002D_ServiceImpl extends AbstractBusinessService implements CS0108002D_Service {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger;

	@Override
	public void saveTemplet(BusinessContext ctx) throws Exception {
		logger = ServiceManagerFactory.getLogger();
		
		Dataset ds_zcstplms_insert = ctx.getInputDataset("ds_zcstplms_insert");
		Dataset ds_zcstpldt_insert = ctx.getInputDataset("ds_zcstpldt_insert");
		Dataset ds_zcstplms_update = ctx.getInputDataset("ds_zcstplms_update");
		Dataset ds_zcstpldt_update = ctx.getInputDataset("ds_zcstpldt_update");
		
		String db_con = Utillity.getSapJndiBySysid(ctx.getInputVariable("G_SYSID"));
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		DatasetSqlRequest sqlMax = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108002D_S4");
		sqlMax.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlMax.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		DatasetSqlRequest sqlZcstplms = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108002D_I1");
		sqlZcstplms.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlZcstplms.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		DatasetSqlRequest sqlZcstpldt = SqlMapManagerUtility.makeSqlRequestForDataset("cs01:CS0108002D_I2");
		sqlZcstpldt.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlZcstpldt.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 마스터 디테일 입력 처리
		for(int i=0; i < ds_zcstplms_insert.getRowCount(); i++) {
			String NUM = selectInsertZcstplms(sqlMax, sqlZcstplms, executor);
			sqlZcstplms.addParamObject("TYPE", ds_zcstplms_insert.getColumnAsString(i, "TYPE"));
			sqlZcstplms.addParamObject("NAM", ds_zcstplms_insert.getColumnAsString(i, "NAM"));
			sqlZcstplms.addParamObject("RMK", ds_zcstplms_insert.getColumnAsString(i, "RMK"));
			executor.execute(sqlZcstplms);
			for(int j=0; j < ds_zcstpldt_insert.getRowCount(); j++) {
				sqlZcstpldt.addParamObject("NUM", NUM);
				sqlZcstpldt.addParamObject("GRP", ds_zcstpldt_insert.getColumnAsString(j, "GRP"));
				sqlZcstpldt.addParamObject("COD", ds_zcstpldt_insert.getColumnAsString(j, "COD"));
				sqlZcstpldt.addParamObject("VALUE", ds_zcstpldt_insert.getColumnAsString(j, "VALUE"));
				executor.execute(sqlZcstpldt);
			}
		}
		
		// 마스터 데이터 수정 처리
		for(int i=0; i < ds_zcstplms_update.getRowCount(); i++) {
			sqlZcstplms.addParamObject("NUM", ds_zcstplms_update.getColumnAsString(i, "NUM"));
			sqlZcstplms.addParamObject("TYPE", ds_zcstplms_update.getColumnAsString(i, "TYPE"));
			sqlZcstplms.addParamObject("NAM", ds_zcstplms_update.getColumnAsString(i, "NAM"));
			sqlZcstplms.addParamObject("RMK", ds_zcstplms_update.getColumnAsString(i, "RMK"));
			executor.execute(sqlZcstplms);
		}
		
		// 디테일 특성 수정 처리
		for(int i=0; i < ds_zcstpldt_update.getRowCount(); i++) {
			sqlZcstpldt.addParamObject("NUM", ds_zcstpldt_update.getColumnAsString(i, "NUM"));
			sqlZcstpldt.addParamObject("GRP", ds_zcstpldt_update.getColumnAsString(i, "GRP"));
			sqlZcstpldt.addParamObject("COD", ds_zcstpldt_update.getColumnAsString(i, "COD"));
			sqlZcstpldt.addParamObject("VALUE", ds_zcstpldt_update.getColumnAsString(i, "VALUE"));
			executor.execute(sqlZcstpldt);
		}
	}

	/**
	 * 템플릿 마스터 정보 맥스값 조회 및 저장
	 * @param ctx
	 * @throws Exception
	 */
	private synchronized String selectInsertZcstplms(DatasetSqlRequest sqlMax, 
			                                            DatasetSqlRequest sqlZcstplms, DatasetSqlExecutor executor) throws Exception {
		String NUM = "";
		// 맥스키를 조회한다.
		Dataset ds_max_num = (Dataset)executor.query(sqlMax).getResultObject();
		if( ds_max_num.getRowCount() > 0 ) {
			NUM = ds_max_num.getColumnAsString(0, "NUM");
		} else {
			throw new Exception("템플릿 마스터 키 값 생성 오류");
		}

		sqlZcstplms.addParamObject("NUM", NUM);
		executor.execute(sqlZcstplms);
		return NUM;
	}
}
