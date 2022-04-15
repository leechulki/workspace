package com.helco.xf.cs06.ws;

import com.tobesoft.platform.data.Dataset;

import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;
import com.helco.xf.comm.Utillity;

import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.TitUtility;

public class CS0609001A_ServiceImpl extends AbstractBusinessService implements
		CS0609001A_Service {

/*	public void save(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		String t_gno = "";
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_U1");
		zcst111u.addParamObject("ds_list", ds_list);

		// 무상보수 발주정보 update(zcst116)
		DatasetSqlRequest zcst116u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_U2");
		zcst116u.addParamObject("ds_list", ds_list);

		// 마스터호기테이블 update(zmaster02)
		DatasetSqlRequest zmaster02u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_U5");
		zmaster02u.addParamObject("ds_list", ds_list);

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		ComMethodVo comVo;
		ComMethodDao comDao;

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				t_gno = ds_list.getColumnAsString(i, "PJT") + 
				        ds_list.getColumnAsString(i, "HNO") + 
				        "-" + "R";

				zcst116u.addParamObject("GNO", t_gno);
		
				zcst116u.setRowIndex(i);
				zcst111u.setRowIndex(i);
				zmaster02u.setRowIndex(i);

				executor.execute(zcst116u);
				executor.execute(zcst111u);
				executor.execute(zmaster02u);

				comVo = new ComMethodVo();
				comDao = new ComMethodDao();

	            if("A".equals(ds_list.getColumnAsString(i, "GND"))) //무상공사
	            	comVo.setDataId("11");
	            else if("B".equals(ds_list.getColumnAsString(i, "GND"))) //무상일반
	            	comVo.setDataId("12");
	            else
	            	throw new BizException("Data Id가 정의 되지 않았습니다");

	            comVo.setMandt(ctx.getInputVariable("G_MANDT"));
	            comVo.setJobGubun("I");
	            comVo.setUpn(ds_list.getColumnAsString(i, "PJT"));
	            comVo.setCst("Z99");
	            comVo.setPjt(ds_list.getColumnAsString(i, "PJT"));
	            comVo.setHno(ds_list.getColumnAsString(i, "HNO"));
	            comVo.setSeq(ds_list.getColumnAsString(i, "SEQ"));
	            comVo.setFirstUserId("");
	            comVo.setGno(t_gno);
	            
	            int rtnCode = comDao.SetBXRBXL(comVo);
			}
		}
	}

	public void sendback(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// 호기master update(zcst111)
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_U3");
		zcst111u.addParamObject("ds_list", ds_list);

		// 무상보수 발주정보 update(zcst116)
		DatasetSqlRequest zcst116u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs11:CS1104001A_U4");
		zcst116u.addParamObject("ds_list", ds_list);

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst116u.setRowIndex(i);
				zcst111u.setRowIndex(i);

				executor.execute(zcst116u);
				executor.execute(zcst111u);
			}
		}
	}*/
}
