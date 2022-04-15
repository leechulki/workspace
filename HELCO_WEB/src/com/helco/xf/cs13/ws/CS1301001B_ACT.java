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

public class CS1301001B_ACT extends AbstractAction {
	
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
		
		String str = getUserMessageFactory().findEmbedMessage("SSTRX0001", "test");
		System.out.println("message >>>>>>>>>>>>> "+str);
		
		Dataset ds_master = ctx.getInputDataset("ds_master");
		Dataset ds_detail = ctx.getInputDataset("ds_detail");		
		String db_con = Utillity.getSapJndi(ds_master.getColumnAsString(0, "MANDT"));
		
		// 매출계획정보 select(zcst131)
		DatasetSqlRequest zcst131s2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_S21");
		zcst131s2.addParamObject("ds_detail", ds_detail);
		
		// 보수영업마감정보 select(zcst166)
		DatasetSqlRequest zcst166s2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_S20");
		zcst166s2.addParamObject("ds_detail", ds_detail);
		
		zcst131s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst166s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst166s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		String t_gno = "";
		
		if(ds_master.getColumnAsString(0, "CS141_SEQ")==null||ds_master.getColumnAsString(0, "CS141_SEQ").equals("")){
			//	Insert
			SqlRequest sqlRequest 
				= SqlMapManagerUtility.makeSqlRequest("cs13:CS1301001B_S13");
		
			sqlRequest.addParamObject("CS141_UPN", ds_master.getColumnAsString(0, "CS141_UPN"));
			sqlRequest.addParamObject("CS141_CST", ds_master.getColumnAsString(0, "CS141_CST"));
			sqlRequest.addParamObject("MANDT",     ds_master.getColumnAsString(0, "MANDT"));
				
			Dataset dsRtn = (Dataset)executor.query(sqlRequest).getResultObject();
			int maxSeq = Integer.parseInt(dsRtn.getColumnAsString(0, "SEQ"));	
			maxSeq = maxSeq + 1;
			String maxs = maxSeq+"";
			if(maxs.length()==1){ 
				maxs = "0" + maxs;
			} else {
				
			}
			ds_master.setColumn(0, "CS141_SEQ", maxs);			

			for(int i=0; i<ds_detail.getRowCount(); i++){
				zcst131s2.setRowIndex(i);
				Dataset dsRtn1312 = (Dataset)executor.query(zcst131s2).getResultObject();
				
				zcst166s2.setRowIndex(i);
				Dataset dsRtn1662 = (Dataset)executor.query(zcst166s2).getResultObject();

				if(dsRtn1312.getRowCount() > 0 && dsRtn1312.getColumnAsString(0, "GBN").equals("X")) { //통합프로젝트 호기별로 매출확정이 한건이라도 발행되었으면 실패처리 중지
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 매출 확정이 완료되었습니다. 실패처리를 중지 합니다.");
				} else if(dsRtn1662.getColumnAsInteger(0, "CNT") > 0) { //보수월기성 마감 완료 처리 되었으면 실패 불가
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 월기성이 마감되었습니다. 실패처리를 중지 합니다.");
				}
			}

			// master dataset insert
			sqlRequest 
				= SqlMapManagerUtility.makeSqlRequest("cs13:CS1301001B_I1");
			sqlRequest.addParamObject("ds_master", ds_master);
			executor.execute(sqlRequest);
			
			// detail dataset insert
			DatasetSqlRequest dsReq
				= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_I2");
			for(int i=0; i<ds_detail.getRowCount(); i++){
				ds_detail.setColumn(i, "CS142_SEQ", ds_master.getColumn(0, "CS141_SEQ"));
			}			
			
			dsReq.addParamObject("ds_detail", ds_detail);
			
			for(int i=0; i<ds_detail.getRowCount(); i++){
				dsReq.setRowIndex(i);	
				executor.execute(dsReq);
			}
						
		} else {
			//	Update
			SqlRequest sqlRequest 
				= SqlMapManagerUtility.makeSqlRequest("cs13:CS1301001B_U1");
			sqlRequest.addParamObject("ds_master", ds_master);
			executor.execute(sqlRequest);
			
			DatasetSqlRequest dsReq = null;
			
			// 매출계획정보 select(zcst131)
			DatasetSqlRequest zcst131s
				= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_S21");
			zcst131s.addParamObject("ds_detail", ds_detail);

			DatasetSqlRequest zcst111u
				= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_U3");
			zcst111u.addParamObject("ds_detail", ds_detail);
			
			DatasetSqlRequest zcst126u
				= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_U4");
			// zcst126u.addParamObject("ds_detail", ds_detail);
			
			DatasetSqlRequest zcst126u2
				= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_U7");
			// zcst126u2.addParamObject("ds_detail", ds_detail);
			
			DatasetSqlRequest sqlRequest1 
				= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_S16");
			sqlRequest1.addParamObject("ds_detail", ds_detail);
			
			// 유상보수계약정보 select(zcst126)
			DatasetSqlRequest zcst126s
				= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_S18");
			// zcst126s.addParamObject("ds_detail", ds_detail);
			
			// 인원상주계약정보 update(zcst127)
			DatasetSqlRequest zcst127u
				= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_U8");
			//zcst127u.addParamObject("ds_detail", ds_detail);
			
			// 인원상주계약정보 update(zcst127)
			DatasetSqlRequest zcst127u2
				= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_U9");
			//zcst127u2.addParamObject("ds_detail", ds_detail);
			
			// 보수영업마감정보 select(zcst166)
			DatasetSqlRequest zcst166s
				= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_S20");
			zcst166s.addParamObject("ds_detail", ds_detail);
			
			zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
			zcst127u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
			
			zcst166s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			zcst166s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
			
			String umd = "";
			String cs142Gnd = "";
			String cs142Jhd = "";
			ComMethodVo comVo;
			ComMethodDao comDao;
			
			for(int i=0; i<ds_detail.getRowCount(); i++){ 
				zcst131s.setRowIndex(i);
				Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();
				
				zcst166s.setRowIndex(i);
				Dataset dsRtn166 = (Dataset)executor.query(zcst166s).getResultObject();

				if(dsRtn131.getRowCount() > 0 && dsRtn131.getColumnAsString(0, "GBN").equals("X")) { //통합프로젝트 호기별로 매출확정이 한건이라도 발행되었으면 실패처리 중지
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 매출 확정이 완료되었습니다. 실패처리를 중지 합니다.");
				} else if(dsRtn166.getColumnAsInteger(0, "CNT") > 0) { //보수월기성 마감 완료 처리 되었으면 실패 불가
					throw new BizException(String.valueOf(i + 1) + " 번째 호기의 월기성이 마감되었습니다. 실패처리를 중지 합니다.");
				} else {
					if(ds_detail.getColumnAsString(i, "ISEXIST").equals("Y")){
						//System.out.println("ISEXIST >> Y");
						if(ds_master.getColumnAsString(0, "CS141_PST").equals("A2")){
							dsReq = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_U6");
						} else {						
							dsReq = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_U2");
						}
						
					} else {					
						dsReq = SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_I2");
					}	
					dsReq.addParamObject("ds_detail", ds_detail);
					dsReq.setRowIndex(i);
					executor.execute(dsReq); 
									
					// 고객지원부담당자의 승인 작업인 경우				
					if(ds_master.getColumnAsString(0, "CS141_PST").equals("A6") 
							|| ds_master.getColumnAsString(0, "CS141_PST")=="A6"){
					
						//  계약만기일(cs111_umd)  > 실패일자(cs142_jhd) 의 경우
						sqlRequest1.setRowIndex(i);
						Dataset dsRtn = (Dataset)executor.query(sqlRequest1).getResultObject();
						
						umd = dsRtn.getColumnAsString(0, "UMD");
						cs142Gnd = ds_detail.getColumnAsString(i, "CS142_GND");
						cs142Jhd = ds_detail.getColumnAsString(i, "CS142_JHD");
						
//						System.out.println("UMD ========================== "+umd);					
//						System.out.println("CS142_JHD ========================== "+cs142Jhd);
//						System.out.println(Integer.parseInt(umd) +"||"+ Integer.parseInt(cs142Jhd));
//						System.out.println("CS142_GND ========================== "+cs142Gnd);

						// a.5 호기 마스터(zcst111) update
						zcst111u.setRowIndex(i);
						executor.execute(zcst111u);
						
						// a.6 유상보수계약정보(zcst126) update	
						if(Integer.parseInt(umd) > Integer.parseInt(cs142Jhd)){
							zcst126s.addParamObject("ds_detail", ds_detail);
							zcst126s.setRowIndex(i);
							Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();

							if(dsRtn126.getColumnAsInteger(0, "CNT") > 1) {
								zcst126u.addParamObject("ds_detail", ds_detail);
								zcst126u.setRowIndex(i);					
								executor.execute(zcst126u);					    		

					    		zcst126u2.addParamObject("ds_detail", ds_detail);
								zcst126u2.setRowIndex(i);					
								executor.execute(zcst126u2);
						    } else {
								zcst126u.addParamObject("ds_detail", ds_detail);
								zcst126u.setRowIndex(i);				
								executor.execute(zcst126u);				    	
						    }
							
							zcst127u.addParamObject("ds_detail", ds_detail);
							if(i == 0) {
								zcst127u.setRowIndex(i);
								executor.execute(zcst127u);
							}
						}

						if(cs142Gnd.equals("C") || cs142Gnd.equals("D")){

							comVo = new ComMethodVo();
							comDao = new ComMethodDao();

							// a.3 매출계획 정보(zcst131) - Update 
							// a.4 기성비(계획) 정보(zcst136) - Update 및 Insert
							if(Integer.parseInt(umd) > Integer.parseInt(cs142Jhd)){
								//--TODO (module 추가분)
//								System.out.println("a.3, a.4");

								t_gno = "";
								t_gno = ds_detail.getColumnAsString(i, "CS142_UPN") + 
								        "U" + 
								        ds_detail.getColumnAsString(i, "CS142_USD").substring(2,6) + 
								        "-" + 
								        ds_detail.getColumnAsString(i, "CS142_CST");

								//기성비 연동
								comVo.setDataId("51");			
								comVo.setJobGubun("D");

								comVo.setMandt(ctx.getInputVariable("G_MANDT"));
								comVo.setUpn(ds_detail.getColumnAsString(i, "CS142_UPN"));
								comVo.setCst(ds_detail.getColumnAsString(i, "CS142_CST"));
								comVo.setPjt(ds_detail.getColumnAsString(i, "CS142_PJT"));
								comVo.setHno(ds_detail.getColumnAsString(i, "CS142_HNO"));
								comVo.setSeq(ds_detail.getColumnAsString(i, "CS142_GSQ"));
								if(i == 0) {
									comVo.setMaxSeq(CommonUtil.NullToBlank(ds_detail.getColumnAsString(i, "MAXSEQ")));
								} else {
									comVo.setMaxSeq("");
								}
								comVo.setUserId(ctx.getInputVariable("G_USER_ID"));
//								comVo.setGno("");
								comVo.setGno(t_gno);

								int rtnCode = comDao.SetBXRBXL(comVo);
								
								zcst127u2.addParamObject("ds_detail", ds_detail);
								if(i == 0) {
									zcst127u2.setRowIndex(i);
									executor.execute(zcst127u2);
								}
							} else if(Integer.parseInt(umd) == Integer.parseInt(cs142Jhd)) {
								// 기본기성비는 일수를 적용하여 정산을 실시
//								System.out.println("==");
							}
						}
					}
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
		
		//	input dataset
		Dataset ds_master = ctx.getInputDataset("ds_master");
		Dataset ds_detail = ctx.getInputDataset("ds_detail");
		String db_con = Utillity.getSapJndi(ds_master.getColumnAsString(0, "MANDT"));
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
				
		//	Detail(zcst142) 삭제 
		DatasetSqlRequest dsReq
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs13:CS1301001B_D2");
		dsReq.addParamObject("ds_detail", ds_detail);
		
		for(int i=0; i<ds_detail.getRowCount(); i++){
			dsReq.setRowIndex(i);
			executor.execute(dsReq);
		}
			
		//	zcst142 남은 건수 조회
		SqlRequest sqlRequest 
			= SqlMapManagerUtility.makeSqlRequest("cs13:CS1301001B_S14");
		sqlRequest.addParamObject("ds_detail", ds_detail);
	
		Dataset dsRtn = (Dataset)executor.query(sqlRequest).getResultObject();
		int cnt = Integer.parseInt(dsRtn.getColumnAsString(0, "CNT"));
		
		//	zcst142 남은건수가 0일 경우만 zcst141 테이블 삭제 
		if(cnt==0){			
			sqlRequest 
				= SqlMapManagerUtility.makeSqlRequest("cs13:CS1301001B_D1");
			sqlRequest.addParamObject("ds_master", ds_master);
			executor.execute(sqlRequest);
			
		}		
		
	}
}
