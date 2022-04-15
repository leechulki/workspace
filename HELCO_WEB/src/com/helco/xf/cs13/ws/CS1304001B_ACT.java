package com.helco.xf.cs13.ws;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.service.business.ServiceFactoryManager;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.ColumnInfo;

public class CS1304001B_ACT extends AbstractAction {
	
	/**
	 * 조회 
	 * @param ctx
	 * @throws Exception
	 */
	public void search(BusinessContext ctx) throws Exception {	
		
	}

	public void searchComboCst(BusinessContext ctx) throws Exception {	
		
	}
	
	/**
	 * 저장
	 * @param ctx
	 * @throws Exception
	 */
	public void save(BusinessContext ctx) throws Exception {	
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		Dataset ds_list = ctx.getInputDataset("ds_list");

		// 무상발주정보 select(zcst116)
		DatasetSqlRequest zcst116s = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_S3");
		zcst116s.addParamObject("ds_list", ds_list);

		// 유상계약정보 select(zcst126)
		DatasetSqlRequest zcst126s = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_S4");
		zcst126s.addParamObject("ds_list", ds_list);

		// 실패현장정보 select(zcst144)
		DatasetSqlRequest zcst144s = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_S5");
		zcst144s.addParamObject("ds_list", ds_list);

		// 마감정보 select(zeist104)
		DatasetSqlRequest zeist104s = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_S9");
		zeist104s.addParamObject("ds_list", ds_list);

		// 실패현장정보 시퀀스 select(zcst143)
		DatasetSqlRequest zcst143s = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_S6");
		zcst143s.addParamObject("ds_list", ds_list);

		// 실패현장정보 master insert(zcst143)
		DatasetSqlRequest zcst143i = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_I1");
		zcst143i.addParamObject("ds_list", ds_list);

		// 실패현장정보 detail insert(zcst144)
		DatasetSqlRequest zcst144i = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_I2");
		zcst144i.addParamObject("ds_list", ds_list);

		zcst116s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst144s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst144s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zeist104s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zeist104s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst143s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst143s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst143i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst143i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst144i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst144i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		DatasetSqlRequest zcst143u = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_U1");
		zcst143u.addParamObject("ds_list", ds_list);

		DatasetSqlRequest zcst143u2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_U6");
		zcst143u2.addParamObject("ds_list", ds_list);

		DatasetSqlRequest zcst144u = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_U2");
		zcst144u.addParamObject("ds_list", ds_list);

		DatasetSqlRequest zcst111u = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_U3");
		zcst111u.addParamObject("ds_list", ds_list);

		zcst143u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst143u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst143u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst143u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst144u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst144u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		// 교체 등록 프로젝트 확인 zcst172
		DatasetSqlRequest zcst172s = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_S15");
		zcst172s.addParamObject("ds_list", ds_list);

		zcst172s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst172s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));


		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		if(ds_list.getColumnAsString(0, "UPD").equals("X")) { //보수PM 발송상태에서 데이터 수정가능
			zcst143u2.setRowIndex(0);
			executor.execute(zcst143u2);
		} else if(ds_list.getColumnAsString(0, "PST").equals("A1")) { //최초 데이터 저장시
			// Insert
			for(int i=0; i<ds_list.getRowCount(); i++) {
				zcst116s.setRowIndex(i);
				Dataset dsRtn116 = (Dataset)executor.query(zcst116s).getResultObject();

				zcst126s.setRowIndex(i);
				Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();

				zcst144s.setRowIndex(i);
				Dataset dsRtn144 = (Dataset)executor.query(zcst144s).getResultObject();
				
				zeist104s.setRowIndex(i);
				Dataset dsRtn104 = (Dataset)executor.query(zeist104s).getResultObject();

				if(dsRtn116.getColumnAsInteger(0, "CNT") > 0) { //무상발주 진행중이면 실패 불가
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 무상발주 만료일보다 실패일자가 작습니다.\n실패일자 변경이나 만료일 변경후 실패등록 하시기 바랍니다.");
				}

				if(dsRtn126.getColumnAsInteger(0, "CNT") > 0) { //유상계약 진행중이면 실패 불가
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 유상계약 만료일보다 실패일자가 작습니다.\n실패일자 변경이나 중도해지후 실패등록 하시기 바랍니다.");
				} 

				if(dsRtn144.getColumnAsInteger(0, "CNT") > 0) { //현재 호기가 실패 진행 중이거나 상태가 실패일때 실패 불가
					throw new BizException(String.valueOf(i + 1) + " 번째 호기가 실패 진행중입니다. 재확인 하시기 바랍니다.");
				}
				
				zcst172s.setRowIndex(i);
				Dataset dsRtn172 = (Dataset)executor.query(zcst172s).getResultObject();
				
				if(dsRtn172.getRowCount() > 0) { //현재 호기가 교체 프로젝트 인 경우
					throw new BizException(String.valueOf(i + 1) + " 번째 호기는  " + dsRtn172.getColumnAsString(0, "RTHNO") +"  로 교체 진행 되었습니다. 저장 처리를 취소합니다.");
				}
/* 2011.10.26 LJH 최인식대리 요청으로 로직 제외
				if(dsRtn104.getColumnAsInteger(0, "ZCLOSE") >= Integer.parseInt(ds_list.getColumnAsString(0, "JHD"))) { //월마감 이전일자로 실패 불가
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 월마감이 완료되었습니다.\n실패일자를 변경후 실패등록 하시기 바랍니다.");
				}
*/
			}

			int k = 0;
			for(int i=0; i<ds_list.getRowCount(); i++) {
				if(k == 0) { //최초 한번만 생성
					zcst143s.setRowIndex(i);
					Dataset dsRtn143 = (Dataset)executor.query(zcst143s).getResultObject();

					zcst143i.addParamObject("G_SEQ", dsRtn143.getColumnAsString(0, "SEQ"));
					zcst144i.addParamObject("G_SEQ", dsRtn143.getColumnAsString(0, "SEQ"));

					zcst143i.setRowIndex(i);
					executor.execute(zcst143i);
					
					//최초 저장 후 리프레시 될때 저장 결과를 조회할 수 있도록 seq를 돌려준다. 
					Dataset dsOutput = new Dataset("ds_output");
					dsOutput.addColumn("SEQ", ColumnInfo.COLUMN_TYPE_STRING, 255);
					dsOutput.appendRow();
					dsOutput.setColumn(0, "SEQ", dsRtn143.getColumnAsString(0, "SEQ"));
					ctx.addOutputDataset(dsOutput);
				}

				zcst144i.setRowIndex(i);
				executor.execute(zcst144i);

				k++;
			}
		} else {
			// Update
			zcst143u.setRowIndex(0);
			zcst144u.setRowIndex(0);

			executor.execute(zcst143u);
			executor.execute(zcst144u);

			if(ds_list.getColumnAsString(0, "PST").equals("A6")) {
				for(int i=0; i<ds_list.getRowCount(); i++) {
					zcst111u.setRowIndex(i);
					executor.execute(zcst111u);
				}
			}
		}
	}

	/**
	 * 수정 
	 * @param ctx
	 * @throws Exception
	 */
	public void update(BusinessContext ctx) throws Exception {

	}

	/**
	 * 삭제 
	 * @param ctx
	 * @throws Exception
	 */
	public void delete(BusinessContext ctx) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		Dataset ds_list = ctx.getInputDataset("ds_list");

		DatasetSqlRequest zcst143d = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_D1");
		zcst143d.addParamObject("ds_list", ds_list);

		DatasetSqlRequest zcst144d = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1304001B_D2");
		zcst144d.addParamObject("ds_list", ds_list);

		zcst143d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst143d.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst144d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst144d.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// Delete
		zcst143d.setRowIndex(0);
		zcst144d.setRowIndex(0);

		executor.execute(zcst143d);
		executor.execute(zcst144d);
	}
}
