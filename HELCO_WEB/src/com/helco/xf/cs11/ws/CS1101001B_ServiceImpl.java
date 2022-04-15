package com.helco.xf.cs11.ws;

import java.sql.Connection;
import java.sql.ResultSet;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

public class CS1101001B_ServiceImpl extends AbstractBusinessService implements CS1101001B_Service {

	private static final long serialVersionUID = 1L;
	static Logger logger;

	/**
	 * 저장
	 */
	public void save(BusinessContext ctx, Dataset ds_list) throws Exception {

		Dataset dsError = new Dataset("ds_error");
		dsError.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 255);
		dsError.addColumn("ERRMSG", ColumnInfo.COLTYPE_STRING, 255);

		int i = 0;
		try {
			String mdt = ctx.getInputVariable("G_MANDT");
			String db_con = Utillity.getSapJndi(mdt);
			logger = ServiceManagerFactory.getLogger();
			DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

			// 무상보수발주정보 select(zcst116)
			logger.debug("무상보수발주정보 select(zcst116)");
			DatasetSqlRequest zcst116s1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_S6");
			zcst116s1.addParamObject("ds_list", ds_list);
			zcst116s1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			zcst116s1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

			// 무상보수발주정보 select(zcst116)
			logger.debug("무상보수발주정보 select(zcst116)");
			DatasetSqlRequest zcst116s2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_S7");
			zcst116s2.addParamObject("ds_list", ds_list);
			zcst116s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			zcst116s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

			// 무상보수발주정보 insert(zcst116)
			logger.debug("무상보수발주정보 insert(zcst116)");
			DatasetSqlRequest zcst116i = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_I1");
			zcst116i.addParamObject("ds_list", ds_list);
			zcst116i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			zcst116i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

			// 무상보수발주정보 update(zcst116)
			logger.debug("무상보수발주정보 update(zcst116)");
			DatasetSqlRequest zcst116u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_U3");
			zcst116u.addParamObject("ds_list", ds_list);
			zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
			zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

			int successCount = 0; // 처리갯수
			String seq = "01";
			for (i = 0; i < ds_list.getRowCount(); i++) {

				if ("I".equalsIgnoreCase(ds_list.getColumnAsString(i, "FLAG"))) {

					zcst116i.setRowIndex(i);
					executor.execute(zcst116i);

					zcst116u.setRowIndex(i);
					executor.execute(zcst116u);

					successCount++;

				} else if ("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
					zcst116u.setRowIndex(i);
					executor.execute(zcst116u);
				}

				if ("I".equals(ds_list.getColumnAsString(i, "FLAG")) || "U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
					zcst116s1.setRowIndex(i);
					Dataset dsRtn116a = (Dataset) executor.query(zcst116s1).getResultObject();

					if (dsRtn116a.getColumnAsInteger(0, "CNT") > 0) { 
						throw new BizException("SAP와 WEB의 무상발주개월수가 다릅니다. 저장처리를 취소합니다.");
					}

					zcst116s2.setRowIndex(i);
					Dataset dsRtn116b = (Dataset) executor.query(zcst116s2).getResultObject();

					if (dsRtn116b.getColumnAsInteger(0, "CNT") > 1) { 
						throw new BizException("동일한 호기가 중복생성되었습니다. 저장처리를 취소합니다.");
					}

					successCount++;
				}

			}

			if (successCount == 0) {
				throw new BizException("저장된 내역이 없습니다.");
			}

		} catch (BizException e) {
			dsError.appendRow();
			dsError.setColumn(0, "IDX", i);
			dsError.setColumn(0, "ERRMSG", "저장되지 않았습니다.");
			logger.debug(e.getMessage());
			throw e;
		} finally {
			ctx.addOutputDataset(dsError);
			ctx.addOutputDataset(ds_list);
		}

		// return ds_list;

	}

	/**
	 * 기안제출
	 */
	public void draft(BusinessContext ctx, Dataset ds_list) throws Exception {

		Dataset dsError = new Dataset("ds_error");
		dsError.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 255);
		dsError.addColumn("ERRMSG", ColumnInfo.COLTYPE_STRING, 255);

		Dataset dsDocRtn = null;

		int i = 0;
		try {
			String mdt = ctx.getInputVariable("G_MANDT");
			String db_con = Utillity.getSapJndi(mdt);
			logger = ServiceManagerFactory.getLogger();
			DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

			// 무상보수발주정보 update(zcst116)
			logger.debug("무상보수발주정보 기안정보 update(zcst116)");
			DatasetSqlRequest zcst116u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_U4");
			zcst116u.addParamObject("ds_list",   ds_list);
			zcst116u.addParamObject("G_MANDT",   ctx.getInputVariable("G_MANDT"));
			zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

			for ( i = 0; i < ds_list.getRowCount(); i++) {
				// 협력사 작성 문건에 대해서 기안 결재 진행 시 여러 호기 중 한호기만 선택하여도 해당 프로젝트 건은 모두
				// 기안진행되도록 수정요청
//				 if( "I".equalsIgnoreCase(ds_list.getColumnAsString(i, "FLAG")) ) {
					 zcst116u.setRowIndex(i);
					 executor.execute(zcst116u);
//				 }
			}

		} catch (Exception e) {
			dsError.appendRow();
			dsError.setColumn(0, "IDX", i);
			dsError.setColumn(0, "ERRMSG", "저장되지 않았습니다.");
			logger.debug(e.getMessage());
			throw e;
		} finally {
			ctx.addOutputDataset(dsError);
			ctx.addOutputDataset(dsDocRtn);
		}

	}

	/**
	 * 결재
	 */
	public void saveDecide(BusinessContext ctx, Dataset ds_list, Dataset ds_decide) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		String t_gno = "";

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_U6");
		zcst111u.addParamObject("ds_list", ds_list);
		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 무상보수 발주정보 update(zcst116)
		DatasetSqlRequest zcst116u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_U5");
		zcst116u.addParamObject("ds_list", ds_list);
		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 마스터호기테이블 update(zmaster02)
		DatasetSqlRequest zmaster02u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_U2");
		zmaster02u.addParamObject("ds_list", ds_list);
		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zmaster02u.addParamObject("HTY", "N");

		// 결재정보 관리 - 차순별 결재정보 update(zwbap02)
		DatasetSqlRequest zwbap02u = SqlMapManagerUtility.makeSqlRequestForDataset("cs04:CS0401002A_U2");
		zwbap02u.addParamObject("ds_list_0", ds_decide);
		zwbap02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zwbap02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 현장지원 결재 정보 관리 update(zwbap01)
		DatasetSqlRequest zwbap01u = SqlMapManagerUtility.makeSqlRequestForDataset("cs04:CS0401002A_U3");
		zwbap01u.addParamObject("ds_list_0", ds_decide);
		zwbap01u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zwbap01u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		// 결재 정보 
		DatasetSqlRequest zwbap01s = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_S12");
		zwbap01s.addParamObject("ds_list_0", ds_decide);
		zwbap01s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zwbap01s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		ComMethodVo comVo;
		ComMethodDao comDao;

		Dataset dsRtnRD = (Dataset)executor.query(zwbap01s).getResultObject();
		
		if(dsRtnRD.getRowCount() > 0) { //무상일반보수를 진행중일 경우에는 기존의 무상일반 관련 정보를 취소 처리
			System.out.println("최종결재 처리");
			for ( int i = 0; i < ds_list.getRowCount(); i++ ) {
				// 수신진행일 경우 이후처리.
				if ( "4".equals(ds_list.getColumnAsString(i, "APSTAT")) ) {
					t_gno = ds_list.getColumnAsString(i, "PJT") + ds_list.getColumnAsString(i, "HNO") + "-" + "R";

					zcst116u.addParamObject("GNO", t_gno);
					zcst116u.setRowIndex(i);

					zcst111u.setRowIndex(i);
					zmaster02u.setRowIndex(i);

					executor.execute(zcst116u);
					executor.execute(zcst111u);
					
					executor.execute(zmaster02u);

					// 기성내역처리
					comVo  = new ComMethodVo();
					comDao = new ComMethodDao();

					String t_seq = getSeq(ctx.getInputVariable("G_MANDT"), ds_list.getColumnAsString(i, "PJT"), ds_list.getColumnAsString(i, "HNO"), ds_list.getColumnAsString(i, "GND"));

					if ("A".equals(ds_list.getColumnAsString(i, "GND"))) // 무상공사
						comVo.setDataId("11");
					else if ("B".equals(ds_list.getColumnAsString(i, "GND"))) // 무상일반
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

					// int rtnCode = comDao.SetBXRBXL(comVo);
					comDao.SetBXRBXL(comVo);
				}
			}

		}
		
		// 결재상태업데이트
		for( int i = 0; i < ds_decide.getRowCount(); i++ ) {
			zwbap02u.setRowIndex(i);
			zwbap01u.setRowIndex(i);

			executor.execute(zwbap02u);
			executor.execute(zwbap01u);
		}

	}

	/**
	 * 기안과 결재 동시처리
	 */
	public void saveDraftAndDecide(BusinessContext ctx, Dataset ds_list, Dataset ds_decide) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		String t_gno = "";
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_U6");
		zcst111u.addParamObject("ds_list", ds_list);
		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		// 무상보수 발주정보 update(zcst116)
		DatasetSqlRequest zcst116u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_U5");
		zcst116u.addParamObject("ds_list", ds_list);
		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		// 마스터호기테이블 update(zmaster02)
		DatasetSqlRequest zmaster02u = SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1101001B_U2");
		zmaster02u.addParamObject("ds_list", ds_list);
		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		zmaster02u.addParamObject("HTY", "N");
		
		// 결재정보 관리 - 차순별 결재정보 update(zwbap02)
		DatasetSqlRequest zwbap02u = SqlMapManagerUtility.makeSqlRequestForDataset("cs04:CS0401002A_U2");
		zwbap02u.addParamObject("ds_list_0", ds_decide);
		zwbap02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zwbap02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		// 현장지원 결재 정보 관리 update(zwbap01)
		DatasetSqlRequest zwbap01u = SqlMapManagerUtility.makeSqlRequestForDataset("cs04:CS0401002A_U3");
		zwbap01u.addParamObject("ds_list_0", ds_decide);
		zwbap01u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zwbap01u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		
		ComMethodVo comVo;
		ComMethodDao comDao;
		
		for ( int i = 0; i < ds_list.getRowCount(); i++ ) {
			// 수신진행일 경우 이후처리.
			t_gno = ds_list.getColumnAsString(i, "PJT") + ds_list.getColumnAsString(i, "HNO") + "-" + "R";
			
			zcst116u.addParamObject("GNO", t_gno);
			zcst116u.setRowIndex(i);
			
			zcst111u.setRowIndex(i);
			zmaster02u.setRowIndex(i);
			
			executor.execute(zcst116u);
			executor.execute(zcst111u);
			executor.execute(zmaster02u);
			
			// 기성내역처리
			comVo  = new ComMethodVo();
			comDao = new ComMethodDao();
			
			String t_seq = getSeq( ctx.getInputVariable("G_MANDT"), ds_list.getColumnAsString(i, "PJT"), ds_list.getColumnAsString(i, "HNO"), ds_list.getColumnAsString(i, "GND") );
			
			if( "A".equals(ds_list.getColumnAsString(i, "GND")) )		  { // 무상공사
				comVo.setDataId("11");
			} else if ( "B".equals(ds_list.getColumnAsString(i, "GND")) ) { // 무상일반
				comVo.setDataId("12");
			} else {
				throw new BizException("Data Id가 정의 되지 않았습니다");
			}
			
			comVo.setMandt(ctx.getInputVariable("G_MANDT"));
			comVo.setJobGubun("I");
			comVo.setUpn(ds_list.getColumnAsString(i, "PJT"));
			comVo.setCst("Z99");
			comVo.setPjt(ds_list.getColumnAsString(i, "PJT"));
			comVo.setHno(ds_list.getColumnAsString(i, "HNO"));
			comVo.setSeq(t_seq);
			comVo.setFirstUserId("");
			comVo.setGno(t_gno);
			
			// int rtnCode = comDao.SetBXRBXL(comVo);
			comDao.SetBXRBXL(comVo);
		}
		
		// 결재상태업데이트
		for( int i = 0; i < ds_decide.getRowCount(); i++ ) {
			zwbap02u.setRowIndex(i);
			zwbap01u.setRowIndex(i);
			
			executor.execute(zwbap02u);
			executor.execute(zwbap01u);
		}
		
	}
	
	/**
	 * 호기별 최대시퀀스 구하기
	 * @param mandt
	 * @param pjt
	 * @param hno
	 * @param gnd
	 * @return
	 * @throws Exception
	 */
	public String getSeq(String mandt, String pjt, String hno, String gnd) throws Exception {
		String seq = "";
		
		StringBuffer sqlBuff = new StringBuffer();
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		sqlBuff.append("  SELECT                        \n");
		sqlBuff.append("        MAX(CS116_SEQ) SEQ      \n");
		sqlBuff.append("  FROM                          \n");
		sqlBuff.append("        SAPHEE.ZCST116          \n");
		sqlBuff.append("  WHERE    1=1                  \n");
		sqlBuff.append("  AND   MANDT = ?               \n");
		sqlBuff.append("  AND   CS116_PJT = ?           \n");
		sqlBuff.append("  AND   CS116_HNO = ?           \n");
		sqlBuff.append("  AND   CS116_GND = ?           \n");
		
		try {
			String db_con = Utillity.getSapJndi(mandt);
			conn = getConnection(db_con);
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
			
			pstmt.setString(1, mandt);
			pstmt.setString(2, pjt);
			pstmt.setString(3, hno);
			pstmt.setString(4, gnd);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				seq = rs.getString("SEQ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				DBUtil.close(rs, pstmt, null);
			} catch (Exception e) {
			}
		}
		return seq;
	}
	
	/**
	 * 결재문서번호별 진행상태
	 * @param mandt
	 * @param docno
	 * @return
	 * @throws Exception
	 */
	public String checkApstat(String mandt, String docno) throws Exception {
		String apstat = "";

		StringBuffer sqlBuff = new StringBuffer();
		LoggablePreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;

		sqlBuff.append("  SELECT                        \n");
		sqlBuff.append("        APSTAT      		    \n");
		sqlBuff.append("  FROM                          \n");
		sqlBuff.append("        SAPHEE.ZWBAP01          \n");
		sqlBuff.append("  WHERE    1=1                  \n");
		sqlBuff.append("  AND   MANDT = ?               \n");
		sqlBuff.append("  AND   DOCNO = ?               \n");

		try {
			String db_con = Utillity.getSapJndi(mandt);
			conn = getConnection(db_con);
			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

			pstmt.setString(1, mandt);
			pstmt.setString(2, docno);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				apstat = rs.getString("APSTAT");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				DBUtil.close(rs, pstmt, null);
			} catch (Exception e) {
			}
		}
		return apstat;
	}

	/**
	 * 기안과 결재 동시처리
	 */
	public void balDelete(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		DatasetSqlRequest zcst126s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_S4");
		zcst126s.addParamObject("ds_list", ds_list);
		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		DatasetSqlRequest zcst143s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_S5");
		zcst143s.addParamObject("ds_list", ds_list);
		zcst143s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
	
		DatasetSqlRequest zcst136s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_S6");
		zcst136s.addParamObject("ds_list", ds_list);
		zcst136s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
	
		DatasetSqlRequest zcst116s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_S7");
		zcst116s.addParamObject("ds_list", ds_list);
		zcst116s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		DatasetSqlRequest zcst116s1	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_S8");
		zcst116s1.addParamObject("ds_list", ds_list);
		zcst116s1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		DatasetSqlRequest zcst623s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_S9");
		zcst623s.addParamObject("ds_list", ds_list);
		zcst623s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));


		DatasetSqlRequest zwbap01d	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_D2");
		zwbap01d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		DatasetSqlRequest zwbap02d	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_D3");
		zwbap02d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		DatasetSqlRequest zcst623d	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_D4");
		zcst623d.addParamObject("ds_list", ds_list);
		zcst623d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		DatasetSqlRequest zcst136d	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_D5");
		zcst136d.addParamObject("ds_list", ds_list);
		zcst136d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		DatasetSqlRequest zcst116d	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_D6");
		zcst116d.addParamObject("ds_list", ds_list);
		zcst116d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		DatasetSqlRequest zmaster02u	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_U8");
		zmaster02u.addParamObject("ds_list", ds_list);
		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		DatasetSqlRequest zcst111u	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_U9");
		zcst111u.addParamObject("ds_list", ds_list);
		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		//협력사 무상발주 입력분 삭제 추가
		DatasetSqlRequest zcst649	= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_D7");
		zcst649.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		String docno = "";
		for(int i=0; i<ds_list.getRowCount(); i++) 
		{
			zcst126s.setRowIndex(i);
			Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();	
			
			if(dsRtn126.getRowCount() > 0) { //유상 등록 체크
				throw new BizException("현재 유상계약 데이터가 존재합니다. 삭제처리를 취소합니다.");
			}
			
			zcst143s.setRowIndex(i);
			Dataset dsRtn143 = (Dataset)executor.query(zcst143s).getResultObject();	
			
			if(dsRtn143.getRowCount() > 0) { //실패 등록 체크
				throw new BizException("실패 등록된 데이타입니다. 삭제처리를 취소합니다.");
			}
			
			zcst116s.setRowIndex(i);
			Dataset dsRtn116 = (Dataset)executor.query(zcst116s).getResultObject();	
			
			if(dsRtn116.getRowCount() > 0) { //
				throw new BizException(dsRtn116.getColumnAsString(0, "BGT") + " 개시 발주 데이타가 존재합니다. 삭제처리를 취소합니다.");
			}	
			
			if(ds_list.getColumnAsString(i, "PST").equals("A6")){
				
				zcst136s.setRowIndex(i);
				Dataset dsRtn136 = (Dataset)executor.query(zcst136s).getResultObject();	
				
				if(dsRtn136.getRowCount() > 0) { //기성 지급 여부 체크
					throw new BizException("기성 지급이 완료되었습니다. 삭제처리를 취소합니다.");
				}								
			}
			
			docno = "";
			zcst623s.setRowIndex(i);
			Dataset dsRtn623 = (Dataset)executor.query(zcst623s).getResultObject();	
			
			if(dsRtn623.getRowCount() == 1) { // 중도해지 결재문서 삭제
				docno = dsRtn623.getColumnAsString(0, "NUM");
				zwbap01d.addParamObject("DOCNO", docno);
				zwbap02d.addParamObject("DOCNO", docno);
				
				zwbap01d.setRowIndex(0);
				zwbap02d.setRowIndex(0);

				executor.execute(zwbap01d);
				executor.execute(zwbap02d);
				
			}
			
			docno = "";
			zcst116s1.setRowIndex(i);
			Dataset dsRtn116_2 = (Dataset)executor.query(zcst116s1).getResultObject();	
			
			if(dsRtn116_2.getRowCount() == 1) { // 무상청구 결재문서 삭제
				docno = dsRtn116_2.getColumnAsString(0, "DNO");
				zwbap01d.addParamObject("DOCNO", docno);
				zwbap02d.addParamObject("DOCNO", docno);
				
				zwbap01d.setRowIndex(0);
				zwbap02d.setRowIndex(0);

				executor.execute(zwbap01d);
				executor.execute(zwbap02d);
				
				//협력사 무상발주 입력분 삭제 추가
				zcst649.addParamObject("DOCNO", docno);
				zcst649.setRowIndex(0);
				executor.execute(zcst649);
				
			}	
			// 중도해지 이력 삭제
			zcst623d.setRowIndex(i);
			executor.execute(zcst623d);
			// 기성계획 삭제
			zcst136d.setRowIndex(i);
			executor.execute(zcst136d);
			// 발주이력 삭제
			zcst116d.setRowIndex(i);
			executor.execute(zcst116d);
			// 사용개월수 복원
			zmaster02u.setRowIndex(i);
			executor.execute(zmaster02u);
			// 서비스 마스터 복원
			zcst111u.setRowIndex(i);
			executor.execute(zcst111u);
			
		}	
	}
}
