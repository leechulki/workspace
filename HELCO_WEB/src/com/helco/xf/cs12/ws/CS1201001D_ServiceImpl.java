package com.helco.xf.cs12.ws;

import com.tobesoft.platform.data.Dataset;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;

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

public class CS1201001D_ServiceImpl extends AbstractBusinessService implements
		CS1201001D_Service {

	public void save(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// �ο��������� insert(zcst127) 
		DatasetSqlRequest zcst127i1
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001D_I1");
		zcst127i1.addParamObject("ds_list", ds_list);
		
		DatasetSqlRequest zcst127i2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001D_I2");
		zcst127i2.addParamObject("ds_list", ds_list);
		
		// �����ȹ���� delete(zcst131)
		DatasetSqlRequest zcst131d
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001D_D1");
		zcst131d.addParamObject("ds_list", ds_list);

		// �⼺���ȹ���� delete(zcst136)
		DatasetSqlRequest zcst136d
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001D_D2");
		zcst136d.addParamObject("ds_list", ds_list);

		// �ο��������� update(zcst127)
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001D_U1");
		zcst127u.addParamObject("ds_list", ds_list);

		zcst127i1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127i1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127i2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127i2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst131d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131d.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst136d.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136d.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("I".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				if(Integer.parseInt(ds_list.getColumnAsString(i, "G_CNT")) > 0) {
					zcst127i1.setRowIndex(i);
					executor.execute(zcst127i1);
				} else {
					zcst127i2.setRowIndex(i);
					executor.execute(zcst127i2);
				}
			} else if("D".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst131d.setRowIndex(i);
				zcst136d.setRowIndex(i);
				zcst127u.setRowIndex(i);

				executor.execute(zcst131d);
				executor.execute(zcst136d);
				executor.execute(zcst127u);
			}
		}
	}
	
	public void save2(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		String t_gno = "";

		// ���������� select(zcst126)
		DatasetSqlRequest zcst126s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S15");
		zcst126s.addParamObject("ds_list", ds_list);
		
		// ���������� select(zcst126)
		DatasetSqlRequest zcst126s2	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S17");
		zcst126s2.addParamObject("ds_list", ds_list);

		// �ο��������� update(zcst127)
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001D_U2");
		zcst127u.addParamObject("ds_list", ds_list);

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		ComMethodVo comVo;
		ComMethodDao comDao;

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
//				if(i == 0) { //���� �ѹ��� GNO �� �����Ѵ�. ==> SFR �� Ʋ�� ��찡 �����Ƿ� row ���� �����Ѵ�.
//					zcst126s.setRowIndex(i);
//					Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();
					
//					zcst126u2.addParamObject("GNO", dsRtn126.getColumnAsString(0, "GNO"));

				t_gno = "";	
				t_gno = ds_list.getColumnAsString(i, "UPN") + 
					        "U" + 
					        ds_list.getColumnAsString(i, "SFR").substring(2,6) + 
					        "-" + 
					        ds_list.getColumnAsString(i, "CST");

					zcst127u.addParamObject("GNO", t_gno);
//				}
				
				zcst126s2.setRowIndex(i);
				Dataset dsRtn1262 = (Dataset)executor.query(zcst126s2).getResultObject();
				
				if(dsRtn1262.getRowCount() == 0) { //������ �����Ŀ��� �ο����ְ� ���� �ǵ���
					throw new BizException("���� �ش� ������ ����ó���� �Ϸ���� �ʾҽ��ϴ�. ����ó���� ����մϴ�.");
				}
					
				zcst126s.setRowIndex(i);
				Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();
				
				if(dsRtn126.getRowCount() > 0) { //������ S/O ���� �����Ͱ� ������ ���� ���� �߻�
					throw new BizException("���� �ο����� �����Ϳ� �ش� �ϴ� ������ �������� S/O ��ȣ�� ���� �մϴ�. ����ó���� ����մϴ�.");
				}

				zcst127u.setRowIndex(i);
				executor.execute(zcst127u);

				comVo = new ComMethodVo();
				comDao = new ComMethodDao();
	
            	comVo.setDataId("71");
	            comVo.setJobGubun("I");
	            comVo.setMandt(ctx.getInputVariable("G_MANDT"));
	            comVo.setUpn(ds_list.getColumnAsString(i, "UPN"));
	            comVo.setCst(ds_list.getColumnAsString(i, "CST"));
	            comVo.setPjt(ds_list.getColumnAsString(i, "UPN"));
	            comVo.setHno("Z99");
	            comVo.setSeq(ds_list.getColumnAsString(i, "SEQ"));
				comVo.setIsr(ds_list.getColumnAsString(i, "ISR")); 
				comVo.setRto(ds_list.getColumnAsString(i, "RTO"));
	            comVo.setUserId(ctx.getInputVariable("G_USER_ID"));
	            comVo.setGno(t_gno);

	            int rtnCode = comDao.SetBXRBXL(comVo);
			}
		}
	}
}
