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
	 * 수주 SAP 연동 후 이력 데이터 생성
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
		// 오더유형이 ZC01 인 경우에는 수주 마스터 정보를 저장한 후에 WBS를 생성한다.
		
		// 오더유형이 ZR01, ZR02, ZR03인 경우에는 WBS 정보를 생성한 후에 수주 마스터 정보를 생성한다.
		

        // SAP에서 전달된 값을 물류서비스 수주 테이블에 정보를 반영한 후 문서 상태 정보를 변경한다.
		// zfst0304 수주 마스터 문서번호, 프로젝트 번호 -> 
		// zfst0307 수주 품목의 WBS 문서 번호 정보
		// 결재 상태 정보를 변경한다.
		DatasetSqlRequest sql_zfst0304 = SqlMapManagerUtility.makeSqlRequestForDataset("fs03:FS0301003A_U1");
		sql_zfst0304.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sql_zfst0304.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		// 승인 상태 변경 - 수수 승인 완료 250
		ds_zfst0304.setColumn(0, "OCNT_STAT", "250");
		sql_zfst0304.addParamObject("ds_zfst0304", ds_zfst0304);
		executor.execute(sql_zfst0304);

		DatasetSqlRequest sql_zfst0307 = SqlMapManagerUtility.makeSqlRequestForDataset("fs03:FS0301003A_U4");
		sql_zfst0307.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sql_zfst0307.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		sql_zfst0307.addParamObject("sql_zfst0307", sql_zfst0307);
		executor.execute(sql_zfst0307);
		
		// 결재상태 변경
		/*
		DatasetSqlRequest sqlM_zfst0200 = SqlMapManagerUtility.makeSqlRequestForDataset("fs01:FS_COM0701_M2");
		sqlM_zfst0200.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlM_zfst0200.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		sqlM_zfst0200.addParamObject("ds_doc_appr", ds_doc_appr);
		executor.execute(sqlM_zfst0200);
*/
		
		// 문서 상태 변경
		DatasetSqlRequest sqlU_zfst0200 = SqlMapManagerUtility.makeSqlRequestForDataset("fs01:FS_COM0701_U1");
		sqlU_zfst0200.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		sqlU_zfst0200.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		sqlU_zfst0200.addParamObject("ds_doc_appr", ds_doc_appr);
		executor.execute(sqlU_zfst0200);

		// 최종 완료된 데이터로 백업 데이터를 생성한다.
		// ZFST0305 이력 생성, ZFST0308 이력 생성한다. -> 혹시 모르니 삭제 후 저장 로직으로 구현한다.
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
	 * 수주 빌링 데이터 연동
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void bilSapSave(BusinessContext ctx) throws Exception {
		Dataset ds_cond = ctx.getInputDataset("ds_cond");
		Dataset ds_zfst0310_list = ctx.getInputDataset("ds_zfst0310_list");
		// 결재 완료 상태에서 빌링 데이터만 수정 시 처리
		
		// 빌링 정보 처리 시 SAP에 연동하여 만약 빌링 상태가 완료된 데이터 건에 대한 삭제 및 변경건이 존재하면
		// 현재 빌링 정보를 최신화 한 후 사용자에게 메세지를 출력한다.
		
		// SAP 반영 완료 후에 물류서비스 빌링 정보에 반영 해 줘야 한다.
	}
}
