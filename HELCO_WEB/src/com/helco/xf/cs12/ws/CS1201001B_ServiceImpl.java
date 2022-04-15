package com.helco.xf.cs12.ws;

import java.math.BigDecimal;

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
import com.helco.xf.cs12.evladm.dbutil.*;
import com.helco.xf.comm.Utillity;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.business.config.ConfigUtility;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.StringOperator;
import tit.util.TitUtility;

public class CS1201001B_ServiceImpl extends AbstractBusinessService implements CS1201001B_Service {
	static Logger logger;

	public Dataset searchComboCst(Dataset ds) throws Exception {
		String sql = "SELECT CS121_CST CST FROM SAPHEE.ZCST121 WHERE 1 = 1";
		
		// NULL�� �ƴ��� ���� Ȯ�� 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sql += " AND MANDT = #MANDT#";
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sql += " AND CS121_UPN = #UPN#";
		}

		// SqlRequest ���� 
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
		
		// �Ķ���� �����ϱ� 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sqlRequest.addParamObject("MANDT", DatasetUtility.getString(ds, "MANDT"));
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sqlRequest.addParamObject("UPN", DatasetUtility.getString(ds, "UPN"));
		}
		
		String mdt = DatasetUtility.getString(ds, "MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		// ��ȸ ó�� 
		SqlExecutor db = new DatasetSqlExecutor(getConnection(db_con));
		SqlResult result = db.query(sqlRequest);
		
		// ��ȸ ��� ��ü ã�ƿ��� 
		Dataset returnDs = (Dataset)result.getResultObject();
		
		// ����ڿ��� �� �ѱ�� 
		return returnDs;
	}

	public Dataset searchNamSpt(Dataset ds) throws Exception {
		String sql = "SELECT CS121_NAM NAM, CS121_SPT SPT FROM SAPHEE.ZCST121 WHERE 1 = 1";

		// NULL�� �ƴ��� ���� Ȯ�� 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sql += " AND MANDT = #MANDT#";
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sql += " AND CS121_UPN = #UPN#";
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "CST"))) {
			sql += " AND CS121_CST = #CST#";
		} else {
			sql += " AND CS121_CST = ''";
		}
		
		// SqlRequest ����
		SqlRequest sqlRequest = SqlMapManagerUtility.makeSelectSqlRequestForSql(sql);
		
		// �Ķ���� �����ϱ� 
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "MANDT"))) {
			sqlRequest.addParamObject("MANDT", DatasetUtility.getString(ds, "MANDT"));
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "UPN"))) {
			sqlRequest.addParamObject("UPN", DatasetUtility.getString(ds, "UPN"));
		}
		if (TitUtility.isNotNull(DatasetUtility.getString(ds, "CST"))) {
			sqlRequest.addParamObject("CST", DatasetUtility.getString(ds, "CST"));
		}

		String mdt = DatasetUtility.getString(ds, "MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		DatasetSqlRequest zcst121s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S63");//X
		
		zcst121s.addParamObject("ds_list", ds);
		zcst121s.addParamObject("G_MANDT", mdt);
		
		Dataset returnDs = (Dataset)executor.query(zcst121s).getResultObject();
		
		/*********************************************************************
		// ��ȸ ó�� 
		SqlExecutor db = new DatasetSqlExecutor(getConnection(db_con));
		SqlResult result = db.query(sqlRequest);
		
		// ��ȸ ��� ��ü ã�ƿ��� 
		Dataset returnDs = (Dataset)result.getResultObject();
		**********************************************************************/
		// ����ڿ��� �� �ѱ�� 
		return returnDs;
	}

	public void save(BusinessContext ctx, Dataset ds_gbn, Dataset ds_list, Dataset ds_lists) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		// ���� 12���� �ʰ� Ȯ�� ���� , ���� ���ѿ� ���� ���� 20170829
		// 2020.06.17 �������������� ���� �������� ���� �ּ�ó��.
		// 2020.06.29 ���ؼ� ���� ��û���� ���ֺ������� �������� �ʵ��� ����. Han JH
		String except_flag = ds_gbn.getColumnAsString(0, "EXCEPT");
		
		// ���� ����� �����׷� ����. 2020.06.17 Han JH
		String bf_bsu = ""; // ���� �������»� �����׷�
		String af_bsu = ""; // �� �������»� �����׷�
		
		if(TitUtility.isNotNull(ds_gbn.getColumnAsString(0, "BF_BSU"))) {
			bf_bsu = ds_gbn.getColumnAsString(0, "BF_BSU");
		}
		
		if(TitUtility.isNotNull(ds_gbn.getColumnAsString(0, "AF_BSU"))) {
			af_bsu = ds_gbn.getColumnAsString(0, "AF_BSU");
		}
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// �������庸�� Detail select(zcst146)
		DatasetSqlRequest zcst146s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S19");//X
		zcst146s.addParamObject("ds_list", ds_list);

		// �������庸�� Detail select(zcst142)
		DatasetSqlRequest zcst142s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S18");//X
		zcst142s.addParamObject("ds_list", ds_list);

		// ���󺸼�������� select(zcst126)
		DatasetSqlRequest zcst126s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S13");//X
		zcst126s.addParamObject("ds_list", ds_list);
		
		// ������ְ������ select(zcst116)
		DatasetSqlRequest zcst116s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S21");//X
		zcst116s.addParamObject("ds_list", ds_list);

		// ���󺸼�������� insert(zcst126)
		DatasetSqlRequest zcst126i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I1");//O
		zcst126i.addParamObject("ds_list", ds_list);

		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U7");//O
		zcst126u.addParamObject("ds_list", ds_list);
		
		// ������ȣ������ select(zmaster02)
		DatasetSqlRequest zmaster02s 
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S24");//X
		zmaster02s.addParamObject("ds_list", ds_list);

		// ���󺸼�������� select(zcst126)
		DatasetSqlRequest zcst126s2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S26");//X
		zcst126s2.addParamObject("ds_list", ds_list);
		
		// ���󺸼�������� select(zcst126)
		DatasetSqlRequest zcst126s3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S28");//X
		zcst126s3.addParamObject("ds_list", ds_list);
		
		// ������ü���� select(zcst172)
		DatasetSqlRequest zcst172s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S29");//X
		zcst172s.addParamObject("ds_list", ds_list);

		// ���󺸼�������� select(zcst126)_20121108 LHR
		DatasetSqlRequest zcst126s4
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S36");//X
		zcst126s4.addParamObject("ds_list", ds_list);
		
		// ����Ű� ����� select(zsdt0500)_20140903 YAR
		DatasetSqlRequest zsdt0500s1
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S50");//X
		zsdt0500s1.addParamObject("ds_list", ds_list);
				
		// �ش�⵵ �����ళ���� sum �� select(zcst126)_20141014 YAR
		DatasetSqlRequest zcst126s51
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S51");//X
		zcst126s51.addParamObject("ds_list", ds_list);
		
		// ���⵵ ���� ���س⵵ ���� ���� ���� Ȯ��_20170919 ���ϸ�
		DatasetSqlRequest zcst126s51c
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S51C");//X	
		zcst126s51c.addParamObject("ds_list", ds_list);
		zcst126s51c.addParamObject("G_MANDT", mdt);
		
		// ���⵵ ���� ���س⵵ ���� ����-12���� �ʰ� �Է� ���� ���� Ȯ�� select(zcst126)_20170919 ���ϸ�
		DatasetSqlRequest zcst126s51n
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S51N");//X
		zcst126s51n.addParamObject("ds_list", ds_list);
		zcst126s51n.addParamObject("G_MANDT", mdt);

		// �̰� ��� ���� ���� üũ select(zcst157)_201506 LHR 
		DatasetSqlRequest zcst157s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S55");//X
		zcst157s.addParamObject("ds_list", ds_list);
		
		// ���ϳ�� ��� ���� üũ select(zcst126)_20160202 YAR
		DatasetSqlRequest zcst126s5
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S58");//X
		zcst126s5.addParamObject("ds_list", ds_list);
		
		// ����� �ܰ� �������� select(zcst126)_201601012
		DatasetSqlRequest zcst126s6
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S60");//
		zcst126s6.addParamObject("ds_list", ds_list);
		
		// ����뿪 üũ  select(zcst121) 201601102
		DatasetSqlRequest zcst121s1
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S63");//X
		zcst121s1.addParamObject("ds_list", ds_list);
				
		zcst146s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst146s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst142s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst142s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zmaster02s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst172s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst172s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zsdt0500s1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zsdt0500s1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s51.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s51.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
				
		zcst157s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst157s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s5.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s5.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s6.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s6.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst121s1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		// ***********  SEG ǥ�شܰ� ��������   ***********  //
		CS1299001A_ServiceImpl getSegData =  new CS1299001A_ServiceImpl();
		//Dataset ds_hnoinfo = ds_list.
		getSegData.getStdPrice2(ctx,ds_list);
		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("I".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				ds_list.setColumn(i, "UMS", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS"))));
				ds_list.setColumn(i, "MMN", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "MMN"))));
				ds_list.setColumn(i, "JKH", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "JKH"))));
//				ds_list.setColumn(i, "RTO", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "RTO"))));

				String t_gkd = "";			
				String t_gnd = "";			
				t_gkd = CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "GKD"));
				t_gnd = CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "GND"));

				zcst126s2.setRowIndex(i);
				Dataset dsRtn126s = (Dataset)executor.query(zcst126s2).getResultObject();
				
				zmaster02s.setRowIndex(i);
				Dataset dsRtn02 = (Dataset)executor.query(zmaster02s).getResultObject();	

				if(t_gnd.trim().equals("D")) { //�����Ϲ� ����� ��츸 üũ(2010.08.23 ���νĴ븮 ��û)
					if(t_gkd.trim().equals("1") && dsRtn126s.getColumnAsString(0, "GKD").trim().equals("1")) { //��౸�� üũ
						if(Integer.parseInt(dsRtn02.getColumnAsString(0, "CNT")) == 0) { //���� �ܿ����� �� �ִ� ��� ���� ���� �߻�
							if(Integer.parseInt(dsRtn02.getColumnAsString(0, "MG_CNT")) > 0 || Integer.parseInt(dsRtn02.getColumnAsString(0, "MI_CNT")) > 0 ){
								throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� �����ܿ������� ���� �� �����Ͻñ� �ٶ��ϴ�. ����ó���� ����մϴ�.");
							}
							else{
								throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ������� �����Ͱ� �������� �ʽ��ϴ�. ����ó���� ����մϴ�.");
							}
						}
					}
				}
				//����Ű�� ������� ��� Exception YAR
				zsdt0500s1.setRowIndex(i);
				Dataset dsRtn500 = (Dataset)executor.query(zsdt0500s1).getResultObject();

				if(Integer.parseInt(dsRtn500.getColumnAsString(0, "CNT")) > 0) { //��� �Ű�� ������� ��� Exception
					throw new BizException("       ��� �Ű�� ����� �Դϴ�.  (��� �Ű���: " + Integer.parseInt(dsRtn500.getColumnAsString(0, "DAT")) +")" );
				}
				
/* *********************************************************************
 * 20170919 ���ϸ�    
 * ****************************************************************Start
 * 1. ���س⵵ �����⵵ ����    cs126_ugs < ���س⵵  && cs126_adt < ���س⵵  
 * 2. ���س⵵ ���� ���           cs126_umr = ���س⵵
 * 3. ���س⵵ �ߵ����� �̷�    cs126_cha <> ''
 *  �� 3������ ��� �����ϴ� ��� ���� ������ ��ŭ ��� ��� ����, �� ���� 12���� �ʰ� ����
 *  �ش����� �ʴ� ��� ������ �� ���� 12���� �ʰ� ���� ����
 *  ********************************************************************
 *  ������ ��� ������ȹ �缺�� ���� ���� ���� �� 
 *  �������� ��츸 �ű� ���� ���� ( �׽�Ʈ �� ��ü ���� 20170919 )
 * **********************************************************************/
				// ***********  �����ܰ� ��������   ***********  //
				zcst126s6.setRowIndex(i);
				Dataset dsRtn1266 = (Dataset)executor.query(zcst126s6).getResultObject();
				
				double p_amt = 0;   // ������� - ����������(����)
				double p_ambt = 0;  // ������� - ����������(���»�)
				double p_amtt = 0;  // ������� - ����������
				String p_knd = "";  // ������� - FM ����
				String p_hyn = "";  // ������� - HRTS
				//===========================����û���� �߰� 20200515 Han.JH==================================
				String p_acyn = "";  // ������� - ����û���� ����
				//======================================================================================
				
				if(dsRtn1266.getRowCount() > 0){
					p_amt  = dsRtn1266.getColumnAsDouble(0, "CS126_AMT");   // ������� - ����������(����)
					p_ambt = dsRtn1266.getColumnAsDouble(0, "CS126_AMBT");  // ������� - ����������(���»�)
					p_amtt = dsRtn1266.getColumnAsDouble(0, "CS126_AMTT");  // ������� - ����������
					p_knd = dsRtn1266.getColumnAsString(0, "CS126_KND");    // ������� - FM ����
					p_hyn = dsRtn1266.getColumnAsString(0, "CS126_HYN");    // ������� - HRTS
					//===========================����û���� �߰� 20200515 Han.JH==================================
					p_acyn = dsRtn1266.getColumnAsString(0, "CS126_ACYN");    // ������� - ����û���� ����
					//======================================================================================
				}
								
				double ds_amt  = ds_list.getColumnAsDouble(i, "AMT") ;       // ���� ���(����)
				double ds_ambt = ds_list.getColumnAsDouble(i, "AMBT") ;      // ���� ���(���»�)
				double ds_amtt = ds_list.getColumnAsDouble(i, "AMTT") ;      // ���� ���
				String ds_knd = ds_list.getColumnAsString(i, "KND") ;        // ���� ���
				String ds_hyn = ds_list.getColumnAsString(i, "HYN") ;        // ���� ���
				//===========================����û���� �߰� 20200515 Han.JH==================================
				String ds_acyn = ds_list.getColumnAsString(i, "ACYN") ;        // ���� ���(����û����)
				//======================================================================================
				String chk_amt = "";
				
				/* HRTS ���� üũ�� Ŭ���̾�Ʈ���� ���ŵ� ���� �������� ���Ѱ��� ����ġ�� ���� 20200608 Han J.H */
				if(ds_hyn.equals("1")) {
					ds_hyn = "Y";
				}
				
				//if (p_amt == ds_amt) chk_amt = "Y";
				// �� ����� ����������, FM ����, HRTS ���� ���� üũ
				if (p_amt == ds_amt && p_knd.endsWith(ds_knd) && p_hyn.endsWith(ds_hyn) && p_acyn.endsWith(ds_acyn)) chk_amt = "Y";
				
				zcst126s51c.setRowIndex(i);
				Dataset dsRtnChk = (Dataset)executor.query(zcst126s51c).getResultObject();	
				
				int chk = Integer.parseInt(dsRtnChk.getColumnAsString(0, "CNT"));
				int estno_len = ds_list.getColumnAsString(i, "SIR").length();
				
				// ���ؼ� ������ �ƴϸ� ���ֺ��� ������ �۵���Ŵ 20200629 Han JH
				if(!except_flag.equals("Y")) {
					
					//if(chk > 0 && estno_len == 0) {
					//if( chk > 0  && chk_amt.endsWith("Y")) {
					// ��������� ��ȸ���̰� ������� ��ȸ���� ��� ���ֺ����� ���� �����Ƿ� �Ʒ��� ���ֺ��� ������ �������� ����.
					if(!(bf_bsu.equals("H100")) && af_bsu.equals("H100")) {
						// ��ȸ�� ���ֺ��� ����
						//if(af_bsu.equals("H100")) { // �̰�(ZCST157) �������� ���ռ� ������ �̰��� �������»��� ���� ����Ȯ �� �� ���.
					} else {
						if( chk > 0 ) {
							
							int gno_seq = 0;
							// ���������� ��� ��� ���� üũ 
							if(estno_len > 0) {
								gno_seq = Integer.parseInt(ds_list.getColumnAsString(0, "LTS"));
							}
							
							//System.out.println("*****************************************");
							//System.out.println("gno_seq =  "+ gno_seq);
							//System.out.println("*****************************************");
							
							// ���� 2���� ����� �ش� ���� ����
							if ( estno_len > 0 && gno_seq == 1 || estno_len == 0)  {
								
								zcst126s51n.setRowIndex(i);
								Dataset dsRtnUmsChk = (Dataset)executor.query(zcst126s51n).getResultObject();	
								int ums_sum   = Integer.parseInt(dsRtnUmsChk.getColumnAsString(0, "SUM"));
								int ums_minus = Integer.parseInt(dsRtnUmsChk.getColumnAsString(0, "SUM_M"));
								int ums_new   = Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS")));
								
								int ums_limit = Math.abs(ums_sum + ums_minus) ;
								
								if(ums_sum + ums_minus < 0) { 
									if(ums_new > ums_limit){
										throw new BizException("       �Է��� �� �ִ� �������� �ʰ��Ͽ����ϴ�.      ���ɰ����� : " + ums_limit + " ���� " );
									}
								}
								
								ums_limit =  ums_new - (ums_sum + ums_minus) ;
								
								if(ums_sum + ums_minus + ums_new > 12 && chk_amt.endsWith("Y")) { 
									throw new BizException("       �ش�⵵ ���󰳿����� 12������ ���� �� �����ϴ�.      ���ɰ����� : " + ums_limit + " ���� " );
								}
							}	
						}					
						
						//================================================================================================================================================
						//===============================================40���� �̻��϶� �ش� ������ �����ؾ� �ϳ� �Ʒ��� ���� ������ IF������ ���� �ǹ̾��� �κ��̶� �ǴܵǾ� �߼�ó��. 20200612 Han JH========
						//  ���� 12���� �ʰ� Ȯ�� ���� , ���� ���ѿ� ���� ���� 20170829	  >> �ӽ�ó�� ���� ���� 20170919
						/*if(except_flag.equals("")) {	
						//�ش�⵵ ���󺸼������� 12���� �ʰ��� ��� Exception 20141014 YAR
						zcst126s51.setRowIndex(i);
						Dataset dsRtn501 = (Dataset)executor.query(zcst126s51).getResultObject();
						
						int ums_sum2 = Integer.parseInt(dsRtn501.getColumnAsString(0, "SUM"));
						int ums_new2  =  Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS")));
		                int ums_limit2 = 12 - ums_sum2;
		                
						if(ums_sum2 + ums_new2 > 12) { 
							throw new BizException("       �Է��� �� �ִ� �������� �ʰ��Ͽ����ϴ�.     �ش�⵵ ���󰳿����� 12������ ���� �� �����ϴ�.      ���ɰ����� : " + ums_limit2 + " ���� " );
						} 
					}*/
						//================================================================================================================================================
						
						// ��ȸ���̸� ���� ���������� ���� ������������ üũ�ϴ� ������ ��� �������� ����.
					}
					
				}

/* *********************************************************************
 * ****************************************************************End
 * ********************************************************************/		
				
			/*	***** >>>> ����ȭ ���� ���� ó�� ���� 
				//  ���� 12���� �ʰ� Ȯ�� ���� , ���� ���ѿ� ���� ���� 20170829	
			//	if(except_flag.equals("")) {	
					//�ش�⵵ ���󺸼������� 12���� �ʰ��� ��� Exception 20141014 YAR
					zcst126s51.setRowIndex(i);
					Dataset dsRtn501 = (Dataset)executor.query(zcst126s51).getResultObject();
					
					if(Integer.parseInt(dsRtn501.getColumnAsString(0, "SUM"))+ Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS"))) > 12) { 
						throw new BizException("       �Է��� �� �ִ� �������� �ʰ��Ͽ����ϴ�.     �ش�⵵ ���󰳿����� 12������ ���� �� �����ϴ�.");
					} 
			//	}		
			*/	
				zcst116s.setRowIndex(i);
				Dataset dsRtn116 = (Dataset)executor.query(zcst116s).getResultObject();

				if(Integer.parseInt(dsRtn116.getColumnAsString(0, "CNT")) > 0) { //������ֱⰣ �ߺ������� ������ Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ������ֿ� ���Ⱓ�� �ߺ��˴ϴ�. ���Ⱓ�� �ٽ� �ѹ� Ȯ���� �ֽʽÿ�.");
				}

				zcst146s.setRowIndex(i);
				Dataset dsRtn146 = (Dataset)executor.query(zcst146s).getResultObject();

				if(Integer.parseInt(dsRtn146.getColumnAsString(0, "CNT")) > 0) { //��������� ������ ������ Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ������� ������ �Դϴ�. �ٽ� �ѹ� Ȯ���� �ֽʽÿ�.");
				}

				zcst142s.setRowIndex(i);
				Dataset dsRtn142 = (Dataset)executor.query(zcst142s).getResultObject();

				if(Integer.parseInt(dsRtn142.getColumnAsString(0, "CNT")) > 0) { //���е���� ������ ������ Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ���е�� ������ �Դϴ�. �ٽ� �ѹ� Ȯ���� �ֽʽÿ�.");
				}

				zcst126s3.setRowIndex(i);
				Dataset dsRtn1263 = (Dataset)executor.query(zcst126s3).getResultObject();

				if(Integer.parseInt(dsRtn1263.getColumnAsString(0, "CNT")) > 0) { //���� ����Ǵ� �������ں��� �̷�����  ������ ������ Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� �̷������ �����մϴ�. ����ó���� ����մϴ�.");
				}
				
				zcst172s.setRowIndex(i);
				Dataset dsRtn172 = (Dataset)executor.query(zcst172s).getResultObject();

				if(Integer.parseInt(dsRtn172.getColumnAsString(0, "CNT")) > 0) { //������ü �����  ������ ������ Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ������ü ��ϵǾ����ϴ�. ����ó���� ����մϴ�.");
				}

				zcst121s1.setRowIndex(i);
				Dataset dsRtn121 = (Dataset)executor.query(zcst121s1).getResultObject();

				if(dsRtn121.getColumnAsString(0, "SLA_BSU").equals("Y") && !ds_list.getColumnAsString(i, "SLA").equals("Y")) { // �Ǹ�ó�� ���� ��ü�� ��� ����뿪 ��� ����� �ƴ� ��� ���
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ����뿪 ������� ��� �Ͻʽÿ�.");
				}
				
				
				if(ds_list.getColumnAsString(i, "KND").equals("")){
					ds_list.setColumn(i, "STDAMT", ds_list.getColumnAsDouble(i, "P10"));
					ds_list.setColumn(i, "PREAMT",0);
				} else {
					ds_list.setColumn(i, "STDAMT", ds_list.getColumnAsDouble(i, "F10"));
					ds_list.setColumn(i, "PREAMT",0);
				}

				// ***********  �����ܰ� ��������   ***********  //			
				if(dsRtn1266.getRowCount() > 0){
					ds_list.setColumn(i, "PREAMT", dsRtn1266.getColumnAsDouble(0, "CS126_AMT") + dsRtn1266.getColumnAsDouble(0, "CS126_AMBT"));
				}
				// ***********  �޼��� ���    ***********  //
				// conversion & recapture : FM/POG ǥ�شܰ� ��� �޼���
				// renewal                : FM ǥ�شܰ� /POG �����ܰ� ��� �޼���
				double t_std = 0 ;
				double t_amt = ds_list.getColumnAsDouble(i, "AMT") + ds_list.getColumnAsDouble(i, "AMBT") ;
				double t_trt = 0 ;

				if(t_gkd.trim().equals("2") && ds_list.getColumnAsString(i, "KND").equals("")){
					
					if( ds_list.getColumnAsDouble(i, "PREAMT") == 0) {
						t_std = ds_list.getColumnAsDouble(i, "STDAMT") ;	
					} else {
						t_std = ds_list.getColumnAsDouble(i, "PREAMT") ;	
					}
				} else {
					t_std = ds_list.getColumnAsDouble(i, "STDAMT") ;	
				}
				
				t_trt = (t_amt / t_std) * 100 ;
				
				ds_list.setColumn(i, "TRT", t_trt);
				// **********************************  //
				
				zcst126s.setRowIndex(i);
				Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();

				if(Integer.parseInt(dsRtn126.getColumnAsString(0, "CNT")) > 0) { //�ߺ��� ���Ⱓ�� ������ Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ���Ⱓ�� �ߺ��˴ϴ�. ���Ⱓ�� �ٽ� �ѹ� Ȯ���� �ֽʽÿ�.");
				} else {
					zcst126i.setRowIndex(i);
					executor.execute(zcst126i);
				}

				zcst126s4.setRowIndex(i);
				Dataset dsRtn1264 = (Dataset)executor.query(zcst126s4).getResultObject();

				if(t_gkd.trim().equals("2") && Integer.parseInt(dsRtn1264.getColumnAsString(0, "CNT")) > 0) { //����뿪 ���� �������� ���� ������ ��� Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ���� ����뿪 ���θ� Ȯ���Ͻʽÿ�.");
				}

				zcst157s.setRowIndex(i);
				Dataset dsRtn157s = (Dataset)executor.query(zcst157s).getResultObject();

				if(Integer.parseInt(dsRtn157s.getColumnAsString(0, "CNT")) > 0) { //�̰� ��� ���� ���� ��� ���� �Ұ� Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� �̰� ��� ���� ���Դϴ�.");
				}
				
				//���ϳ�� ��� �����ϴ� ��� Exception 2016.01.19 YAR
				zcst126s5.setRowIndex(i);
				Dataset dsRtn1265 = (Dataset)executor.query(zcst126s5).getResultObject();

				if(Integer.parseInt(dsRtn1265.getColumnAsString(0, "CNT")) > 0) { //���ϳ�� ��� �����ϴ� ��� Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ���� ��� ����� �����մϴ�.");
				}
			}

			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				ds_list.setColumn(i, "UMS", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS"))));
				ds_list.setColumn(i, "MMN", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "MMN"))));
				ds_list.setColumn(i, "JKH", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "JKH"))));
//				ds_list.setColumn(i, "RTO", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "RTO"))));

				zcst126u.setRowIndex(i);
				executor.execute(zcst126u);
			}
		}
		// �ο����� ��� (����) 201601 ���ϸ�
		CS1201001D_ServiceImpl sjsave;
		sjsave = new CS1201001D_ServiceImpl();
		sjsave.save(ctx,ds_lists);
	}
	
	public void delete(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U8");//X
		zcst126u.addParamObject("ds_list", ds_list);
		
		// �ο����ְ������ update(zcst127)
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U29");//X
		zcst127u.addParamObject("ds_list", ds_list);

		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst126u.setRowIndex(i);
				zcst127u.setRowIndex(i);
				executor.execute(zcst126u);
				executor.execute(zcst127u); // �ο����� ���� ó�� 
			}
		}
	}
	
	public void save3_bak(BusinessContext ctx, Dataset ds_gbn, Dataset ds_list, Dataset ds_cost) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		String kind = ds_gbn.getColumnAsString(0, "KIND");
//		String gbn  = ds_gbn.getColumnAsString(0, "GBN");
		String gbn  = "";
		String t_gno = "";
		double s_cost = ds_cost.getColumnAsDouble(0, "COST");
		double s_rat = ds_cost.getColumnAsDouble(0, "RAT");

		// �ο��������� select(zcst127)
		DatasetSqlRequest zcst127s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S14");
		zcst127s.addParamObject("ds_list", ds_list);

		// ȣ��master update(zcst111)
		DatasetSqlRequest zcst111u1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U1");
		zcst111u1.addParamObject("ds_list", ds_list);
		
		// ȣ��master update(zcst111)
		DatasetSqlRequest zcst111u2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U3");
		zcst111u2.addParamObject("ds_list", ds_list);
		
		// ���󺸼��������� select(zcst116)
		DatasetSqlRequest zcst116s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S4");
		zcst116s.addParamObject("ds_list", ds_list);
		
		// ���󺸼��������� select(zcst116)
		DatasetSqlRequest zcst116s2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S30");
		zcst116s2.addParamObject("ds_list", ds_list);

		// ���󺸼��������� update(zcst116)
		DatasetSqlRequest zcst116u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U2");
		zcst116u.addParamObject("ds_list", ds_list);
		
		// ���󺸼��������� update(zcst116)
		DatasetSqlRequest zcst116u2	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U6");
		zcst116u2.addParamObject("ds_list", ds_list);

		// ���󺸼�������� select(zcst126)
		DatasetSqlRequest zcst126s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S10");
		zcst126s.addParamObject("ds_list", ds_list);
		
		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U4");
		zcst126u1.addParamObject("ds_list", ds_list);

		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u2	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U5");
		zcst126u2.addParamObject("ds_list", ds_list);

		// ����ó ���� insert(zfit1031)
		DatasetSqlRequest zfit1031i4 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I4");
		zfit1031i4.addParamObject("ds_list", ds_list);

		// ������ȣ������ update(zmaster02)
		DatasetSqlRequest zmaster02u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U25");
		zmaster02u.addParamObject("ds_list", ds_list);

		// �̹��� �� ���� �������� update(zcst113)
		DatasetSqlRequest zcst113u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U27");
		zcst113u.addParamObject("ds_list", ds_list);

		// �����ȹ���� select(zcst131)
		DatasetSqlRequest zcst131s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S25");
		zcst131s.addParamObject("ds_list", ds_list);

		// ������ ���� insert(zcst128)
		DatasetSqlRequest zcst128i = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I6");
		zcst128i.addParamObject("ds_cost", ds_cost);

		zcst127s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

//		zfit1031i4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
//		zfit1031i4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst113u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst113u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst128i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));

		ComMethodVo comVo;
		ComMethodDao comDao;

		int z = 0;

		for(int i=0; i<ds_list.getRowCount(); i++) 
		{
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) 
			{
				gbn = ds_list.getColumnAsString(i, "GKD");

				ds_list.setColumn(i, "UMS", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS"))));
				ds_list.setColumn(i, "MMN", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "MMN"))));
				ds_list.setColumn(i, "JKH", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "JKH"))));
//				ds_list.setColumn(i, "RTO", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "RTO"))));
				
//				if(i == 0) { //���� �ѹ��� GNO �� �����Ѵ�. ==> USD �� Ʋ�� ��찡 �����Ƿ� row ���� �����Ѵ�.
//					zcst126s.setRowIndex(i);
//					Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();
					
//					zcst126u2.addParamObject("GNO", dsRtn126.getColumnAsString(0, "GNO"));

//					t_gno = "";
					t_gno = ds_list.getColumnAsString(i, "UPN") + 
					        "U" + 
					        ds_list.getColumnAsString(i, "UGS").substring(2,6) + 
					        "-" + 
					        ds_list.getColumnAsString(i, "CST");

					zcst126u2.addParamObject("GNO", t_gno);
//				}
				
				zcst127s.setRowIndex(i);
				Dataset dsRtn127 = (Dataset)executor.query(zcst127s).getResultObject();
				
				if(dsRtn127.getRowCount() > 0) { //�ο����� S/O ���� �����Ͱ� ������ ���� ���� �߻�
					throw new BizException("���� ������ �����Ϳ� �ش� �ϴ� �ο����� �������� S/O ��ȣ�� ���� �մϴ�. ����ó���� ����մϴ�.");
				}

				zcst116s2.setRowIndex(i);
				Dataset dsRtn1162 = (Dataset)executor.query(zcst116s2).getResultObject();

				if(Integer.parseInt(dsRtn1162.getColumnAsString(0, "CNT")) > 0) { //������ �ȵ� ������� ������ ������ Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ������� ����ó���� �Ϸ���� �ʾҽ��ϴ�. ������� ������ �ٽ� �ѹ� Ȯ���� �ֽʽÿ�.");
				}

				zcst126u2.setRowIndex(i);
//				zcst116u2.setRowIndex(i);
				zcst111u1.setRowIndex(i);
				zcst113u.setRowIndex(i);

				executor.execute(zcst126u2);
//				executor.execute(zcst116u2);
				executor.execute(zcst111u1);
				executor.execute(zcst113u);

				String hgb = "";
				hgb = ds_list.getColumnAsString(i, "HNO").substring(0,1);

				if(z == 0 && (hgb.equals("L") || hgb.equals("S") || hgb.equals("W"))) {
					ds_cost.setColumn(0, "GNO", t_gno); 
					zcst128i.setRowIndex(0);
					executor.execute(zcst128i);
				}

				try
				{
					zfit1031i4.addParamObject("GNO", t_gno);
					zfit1031i4.setRowIndex(i);
					executor.execute(zfit1031i4);
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}

				if(kind.equals("D"))  //�����Ϲ��� ���
				{
					if(gbn.equals("3")) { //��౸���� ��ຯ���� ���
						zcst126u1.setRowIndex(i);
						executor.execute(zcst126u1);
					}
					
//					zmaster02u.setRowIndex(i);
//					executor.execute(zmaster02u);
					
					zcst116s.setRowIndex(i);
					Dataset dsRtn116 = (Dataset)executor.query(zcst116s).getResultObject();
	
					if(dsRtn116.getRowCount() > 0) { //�����Ϲݺ����� �������� ��쿡�� ������ �����Ϲ� ���� ������ ��� ó��
						zcst116u.setRowIndex(i);
						zcst111u2.setRowIndex(i);
	
						executor.execute(zcst116u);
						executor.execute(zcst111u2);
					}
				}
				
				//�⼺�� ����
				comVo = new ComMethodVo();
				comDao = new ComMethodDao();
	
				if("1".equals(ds_list.getColumnAsString(i, "GKD")) || "5".equals(ds_list.getColumnAsString(i, "GKD")))
					if("C".equals(ds_list.getColumnAsString(i, "GND"))) {
						comVo.setDataId("21");//�������
					} else {
						comVo.setDataId("31");//�ڷ�id �ű� ,Ÿ��ű�
					}
				else if("2".equals(ds_list.getColumnAsString(i, "GKD")))
					comVo.setDataId("32");//����
				else if("3".equals(ds_list.getColumnAsString(i, "GKD")))
					comVo.setDataId("34");//��ຯ��(��)
				else if("4".equals(ds_list.getColumnAsString(i, "GKD"))){//����ȸ�� 
					if("3".equals(ds_list.getColumnAsString(i, "WIL"))){					
						comVo.setDataId("35");//Ÿ��ȸ�� 
					}	else{					
						comVo.setDataId("36");//�ڻ�ȸ�� 
					}
				}else if("2".equals(ds_list.getColumnAsString(i, "CHK_QUERY"))){//�ڵ�����
					comVo.setDataId("37");
				}else{
					throw new BizException("Data Id�� ���� ���� �ʾҽ��ϴ�");
				}
	
				if("3".equals(ds_list.getColumnAsString(i, "GKD")))   
					 comVo.setJobGubun("I/D");
				else
					 comVo.setJobGubun("I");
	
				comVo.setMandt(ctx.getInputVariable("G_MANDT"));
				comVo.setUpn(ds_list.getColumnAsString(i, "UPN"));
				comVo.setCst(ds_list.getColumnAsString(i, "CST"));
				comVo.setPjt(ds_list.getColumnAsString(i, "PJT"));
				comVo.setHno(ds_list.getColumnAsString(i, "HNO"));
				comVo.setSeq(ds_list.getColumnAsString(i, "SEQ"));
				comVo.setSla(ds_list.getColumnAsString(i, "SLA"));
				comVo.setUserId(ctx.getInputVariable("G_USER_ID"));
				comVo.setGno(t_gno);
	
				int rtnCode = comDao.SetBXRBXL(comVo);

				if(rtnCode == 0) {
					zcst131s.setRowIndex(i);
					Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();
					
					if(Integer.parseInt(dsRtn131.getColumnAsString(0, "CNT")) > 1) { //���� �����Ͱ� �ߺ� �߻������� ���� ���� �߻�
						throw new BizException("���� ������ �����Ϳ� �ش� �ϴ� ���� �����Ͱ� �ߺ��߻� �Ǿ����ϴ�. ����ó���� ����մϴ�.");
					}
				}

				z++;
			}
		}
	}
	
	public void cancel(BusinessContext ctx, Dataset ds_list, Dataset ds_zcst630) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		String t_gno = "";
		String t_gno_tmp = "";
		String chk = "";
		String c_ums = ""; // �������� ��� CRM���� ���� ����� ���� ��ġ���� ������ ���ϴ� ��찡 �־� �̸� �ذ��ϰ��� �߰�. 2021.10.23 HJH
		logger = ServiceManagerFactory.getLogger();

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
			
		// ��������  select(vbak)
		DatasetSqlRequest vbaks
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S22");//X
		vbaks.addParamObject("ds_list", ds_list);

		// ���������������� select(zcst166)
		DatasetSqlRequest zcst166s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S33");//X
		zcst166s.addParamObject("ds_list", ds_list);

		// ���󺸼��������  select(zcst126)
		DatasetSqlRequest zcst126s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S23");//X
		zcst126s.addParamObject("ds_list", ds_list);

		// ȣ�� master select(zcst111)
		DatasetSqlRequest zcst111s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S20");//X
		zcst111s.addParamObject("ds_list", ds_list);

		// �����ȹ���� select(zcst131)
		DatasetSqlRequest zcst131s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S12");//X
		zcst131s.addParamObject("ds_list", ds_list);

		// ���� �� �⼺�� ��ȹ ����ȭ üũ
		DatasetSqlRequest plan 
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S32");//X
		plan.addParamObject("ds_list", ds_list);

		// �������庸�� Detail select(zcst146)
		DatasetSqlRequest zcst146s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S19");//X
		zcst146s.addParamObject("ds_list", ds_list);
		
		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U9");//X
		zcst126u.addParamObject("ds_list", ds_list);
		
		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U20");//X
		zcst126u2.addParamObject("ds_list", ds_list);
		
		// �ο����ְ������ update(zcst127)
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U23");//X
		zcst127u.addParamObject("ds_list", ds_list);
		
		// �ο����ְ������ update(zcst127)
		DatasetSqlRequest zcst127u2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U24");//X
		zcst127u2.addParamObject("ds_list", ds_list);

		// ȣ�� master update(zcst111)
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U10");//X
		zcst111u.addParamObject("ds_list", ds_list);
		
		// ����������� log insert(zcst148)
		DatasetSqlRequest zcst148i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I3");//X
		zcst148i.addParamObject("ds_list", ds_list);

		// ���ο� ���� �ߵ����� üũ select(zcst126)
		DatasetSqlRequest zcst126s2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S34");//X
		zcst126s2.addParamObject("ds_list", ds_list);

		// ���⼺ ���ο��� üũ Detail select(zcst136)
		DatasetSqlRequest zcst136s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S39");//X
		zcst136s.addParamObject("ds_list", ds_list);

		//�ߵ����� ���� ���� zcst630
		DatasetSqlRequest cs630i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_I1");//X
		cs630i.addParamObject("ds_zcst630", ds_zcst630);
		//�ߵ����� ��� �ø��� �� ��������
		DatasetSqlRequest cs630u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U1");//X
		cs630u.addParamObject("ds_zcst630", ds_zcst630);
		//�ߵ����� ���Ű��� �Ϸ� �� ������� ������Ʈ
		DatasetSqlRequest cs630u3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U3");//X
		cs630u3.addParamObject("ds_zcst630", ds_zcst630);
		//�ߵ����� ���� ���� �� �ݼ� �� ���ۼ� �� ��������
		DatasetSqlRequest cs630u4
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U4");//X
		cs630u4.addParamObject("ds_zcst630", ds_zcst630);
		
		// ��� ���� �� ���� ���� ����� Seq select(zcst126)
		DatasetSqlRequest zcst126s3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_S9");//X
		zcst126s3.addParamObject("ds_list", ds_list);
		
		DatasetSqlRequest zcst126u3
		= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U35");//X
		zcst126u3.addParamObject("ds_list", ds_list);		
		
		vbaks.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		vbaks.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst166s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst166s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		plan.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		plan.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst146s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst146s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst148i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst148i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst136s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//�ߵ����� ���� ���� zcst630
		cs630i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//�ߵ����� ��� �ø��� �� ��������
		cs630u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//�ߵ����� ���Ű��� �Ϸ� �� ������� ������Ʈ
		cs630u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//�ߵ����� ���� ���� �� �ݼ� �� ���ۼ� �� ��������
		cs630u4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126s3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst126u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		ComMethodVo comVo;
		ComMethodDao comDao;
		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				t_gno = ds_list.getColumnAsString(i, "GNO");
				c_ums = ds_list.getColumnAsString(i, "UMS"); // ������
				
				vbaks.setRowIndex(i);
				Dataset dsRtnvbak = (Dataset)executor.query(vbaks).getResultObject();
                //=============����� �����ȣ���� �ݾ� üũ�� ���� ���� �߰�=============
				chk = "";
				if(t_gno_tmp == "" || !t_gno.equals(t_gno_tmp) ) {
					t_gno_tmp = t_gno;
					chk = "X";
				}
				//=============================================================
				
				zcst126s.setRowIndex(i);
				Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();
				
				zcst111s.setRowIndex(i);
				Dataset dsRtn111 = (Dataset)executor.query(zcst111s).getResultObject();

				zcst131s.setRowIndex(i);
				Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();

				zcst146s.setRowIndex(i);
				Dataset dsRtn146 = (Dataset)executor.query(zcst146s).getResultObject();

				zcst166s.setRowIndex(i);
				Dataset dsRtn166 = (Dataset)executor.query(zcst166s).getResultObject();
				
				zcst126s2.setRowIndex(i);
				Dataset dsRtn126_2 = (Dataset)executor.query(zcst126s2).getResultObject();

				zcst136s.setRowIndex(i);
				Dataset dsRtn136 = (Dataset)executor.query(zcst136s).getResultObject();

				if(dsRtn126_2.getColumnAsInteger(0, "CNT") > 0) { //�ߵ����� �Ұ�(2012.07.25 LJH)
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ���� �ش���� ����ó�� �Ǿ����ϴ�. �ߵ����� ó���� ���� �մϴ�.");
				}

				if(dsRtn166.getColumnAsInteger(0, "CNT") > 0 || dsRtn136.getColumnAsInteger(0, "CNT") > 0) { //�������⼺ ���� �Ϸ� �Ǵ� ���⼺ ���� ó�� �Ǿ����� �ߵ����� �Ұ�
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ���� ���⼺�� �����Ǿ����ϴ�. �ߵ����� ó���� ���� �մϴ�.");
				}

				if(Integer.parseInt(dsRtn146.getColumnAsString(0, "CNT")) > 0) { //��������� ������ ������ Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ������� ������ �Դϴ�. �ٽ� �ѹ� Ȯ���� �ֽʽÿ�.");
				}

				//if(!dsRtn126.getColumnAsString(0, "VBELN").trim().equals("") && dsRtnvbak.getColumnAsString(0, "SUJU").equals("N")) { //SO�� �ְ� VBAK�� ���ֱݾװ� ZCST126�� �ݾ��� �����ϸ� �ߵ����� ó�� ����
				if(!dsRtn126.getColumnAsString(0, "VBELN").trim().equals("") && dsRtnvbak.getColumnAsString(0, "SUJU").equals("N") && chk.equals("X")) { //SO�� �ְ� VBAK�� ���ֱݾװ� ZCST126�� �ݾ��� �����ϸ� �ߵ����� ó�� ����
					throw new BizException("���ֱݾ��� '" +  String.format("%,d",Integer.parseInt(dsRtnvbak.getColumnAsString(0, "CS126_AMT"))) + "'���� ���� ���� �� ������ �۾��Ͻñ� �ٶ��ϴ�. �ߵ����� ó���� ���� �մϴ�.");
				} else {
					if(dsRtn111.getColumnAsInteger(0, "CNT") > 0) { //��������Ʈ ȣ�⺰�� ���а� �Ѱ��̶� ������ �ߵ����� ó�� ����
						throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ���� �������Դϴ�. �ߵ����� ó���� ���� �մϴ�.");
					} else {
						if(dsRtn131.getRowCount() > 0 && dsRtn131.getColumnAsString(0, "GBN").equals("X")) { //����������Ʈ ȣ�⺰�� ����Ȯ���� �Ѱ��̶� ����Ǿ����� �ߵ����� ó�� ����
							throw new BizException(String.valueOf(i + 1) + " ��° ȣ���� ���� Ȯ���� �Ϸ�Ǿ����ϴ�. �ߵ����� ó���� ���� �մϴ�.");
						} else {							
							if("A1".equals(ds_list.getColumnAsString(i, "PST"))) {								
								//logger.debug("start Draft");							
							} else {							
							logger.debug("�ߵ����� ó��");																				
							zcst126u.setRowIndex(i);
							zcst111u.setRowIndex(i);
							zcst148i.setRowIndex(i);

							executor.execute(zcst126u);
							executor.execute(zcst111u);
							executor.execute(zcst148i);
							
							//====================������ �����ȣ�� ù��° ���̸鼭, �ο����� �������� �����Ѵٸ�=======================
							//====================�ش� �����ȣ ������ ������, �ο����� �������� �ߵ��������ڷ� ������==================
							//====================���������� ��� ������ True ������ ���ϵǾ� ���������� ������=======================
							//if(i == 0 && ds_list.getColumnAsString(i, "MAXSEQ") != "") {
							if(i == 0 && !(ds_list.getColumnAsString(i, "MAXSEQ").trim().equals(""))) {
								zcst127u.setRowIndex(i);
								executor.execute(zcst127u);
							}

							//�⼺�� ����
							comVo = new ComMethodVo();
							comDao = new ComMethodDao();

							comVo.setDataId("66"); //�ߵ�����
							comVo.setJobGubun("I");

							comVo.setMandt(CommonUtil.NullToBlank(ctx.getInputVariable("G_MANDT")));
							comVo.setUpn(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "UPN")));
							comVo.setCst(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "CST")));
							comVo.setPjt(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "PJT")));
							comVo.setHno(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "HNO")));
							comVo.setSeq(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "SEQ")));
							//====================������ ��� ù��° �����ȣ�� �ο������� �������� �����Ͽ�==========================
							//====================�ߵ����� ó���� �Ͽ��´� �κ������θ� �ߵ����� ó���� �Ǿ�==========================
							//====================SRM �ߵ����� �ҽ����� �����ȣ�� �ο����� �������� ���Ͽ�=========================
							//====================�⼺��ȹ �� �����ȹ�� �ߵ����� ó���ǵ��� ó���Ͽ�����============================
							//====================�������� ���� ���. �� �ο����ְ� ���� ��쿡�� ���ڰ��� �����Ƿ�=====================
							//====================comDao.SetBXRBXL�� �ο����� �ߵ����� ������ �������� �ʴ´�====================
//							if(i == 0) {
								comVo.setMaxSeq(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "MAXSEQ")));
								System.out.println(CommonUtil.NullToBlank(t_gno) + " : " + i + " : " + comVo.getMaxSeq());
//							} else {
//								comVo.setMaxSeq("");
//							}
							//======================================================================================
							comVo.setUserId(CommonUtil.NullToBlank(ctx.getInputVariable("G_USER_ID")));
							comVo.setGno(CommonUtil.NullToBlank(t_gno));
							
							int rtnCode = comDao.SetBXRBXL(comVo);

							plan.setRowIndex(i);
							Dataset dsRtnPlan = (Dataset)executor.query(plan).getResultObject();

							logger.debug("dsRtnPlan.getColumnAsInteger(0, 'CNT') ==> [" + dsRtnPlan.getColumnAsInteger(0, "CNT") + "]");

							/* 2012.06.11 LJH ��ݰ�ȹ ��û�� üũ���� ���� */
							if(dsRtnPlan.getColumnAsInteger(0, "CNT") > 0) { //���� �� �⼺��ȹ ��뿩�� üũ�� ����ȭ ���� �߻��� �ߵ����� ó�� ����
								logger.debug("���� �� �⼺��ȹ�� ������ �߻��Ͽ����ϴ�. �ߵ����� ó���� ���� �մϴ�.");
								throw new BizException("���� �� �⼺��ȹ�� ������ �߻��Ͽ����ϴ�. �ߵ����� ó���� ���� �մϴ�.");
							}

							logger.debug("zcst126u2 START!!");

							zcst126u2.setRowIndex(i);
							executor.execute(zcst126u2);
							//=======================�ο����� ���ֱⰣ�������� �ߵ����� -1�� �������ڷ� �����ϴ� �����̸�======================
							//=======================���������� ��� ù���� �����ȣ�� �����Ͽ����� �κ������� �������� ������ �߻��Ǿ�����===========
							//=======================���������� ��� ������ True ������ ���ϵǾ� ���������� ������==========================
							//=======================�ߵ������� ������ �ο����� �����ȹ �� �⼺��ȹ�� ó���ϰ� ����==========================
							//=======================�ش� ������ �����Ͽ� ���ֱⰣ�������� �ߵ����� -1�� Ȥ�� �̷��� �������ȣ�� ��� ���ֱⰣ �����Ϸ� ������=================
//							if(i == 0 && ds_list.getColumnAsString(i, "MAXSEQ") != "") {
							if(!(ds_list.getColumnAsString(i, "MAXSEQ").trim().equals(""))) {
								zcst127u2.setRowIndex(i);
								executor.execute(zcst127u2);
							}
							//================================================================================================================
							
							// SRM �ߵ����� �� CRM���� ������ ������ ��� �ĺ��� �ȵǾ� �̸� �����ϰ��� ���� �߰���. 2021.10.22 HJH
							if (c_ums.equals("0")) { // �������� 0. �� ��� ������ �̸�
								zcst126s3.setRowIndex(i);
								Dataset dsRtn126_3 = (Dataset)executor.query(zcst126s3).getResultObject();
//								System.out.println("test : " + dsRtn126_3.getColumnAsString(0, "C_SEQ"));
								// ���� ����� �������� ���Ͽ�
								if(dsRtn126_3.getColumnAsInteger(0, "CNT") > 0) {
									zcst126u3.addParamObject("C_SEQ", dsRtn126_3.getColumnAsString(0, "C_SEQ"));
									
									// ��������, �����Ͻ� �÷��� ���� ����ȭ ���ش�.
									zcst126u3.setRowIndex(i);
									executor.execute(zcst126u3);
								}
							}							
							
							}
						}
					}
				}
			}
			
		}

		if("I".equals(ds_zcst630.getColumnAsString(0, "FLAG"))) {
			executor.execute(cs630u4);
			executor.execute(cs630i);
		} else if("U".equals(ds_zcst630.getColumnAsString(0, "FLAG"))) {
			executor.execute(cs630u);
		} else {
			executor.execute(cs630u3);
		}
	}

	public void cancelJ(BusinessContext ctx, Dataset ds_list, Dataset ds_zcst630) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		String t_gno = "";
		String t_gno_tmp = "";
		String chk = "";
		logger = ServiceManagerFactory.getLogger();

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
			
		// ���������������� select(zcst166)
		DatasetSqlRequest zcst166s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S33");//X--
		zcst166s.addParamObject("ds_list", ds_list);

		// ���󺸼��������  select(zcst126)
		DatasetSqlRequest zcst126s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S23");//X--126���� ���ֹ�ȣ CHECK
		zcst126s.addParamObject("ds_list", ds_list);

		// ȣ�� master select(zcst111)
		DatasetSqlRequest zcst111s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S20");//X--����ȣ��CHECK
		zcst111s.addParamObject("ds_list", ds_list);

		// �����ȹ���� select(zcst131)
		DatasetSqlRequest zcst131s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S12");//X--�ߵ��������� ����Ȯ������ check
		zcst131s.addParamObject("ds_list", ds_list);

		// ���� �� �⼺�� ��ȹ ����ȭ üũ
		DatasetSqlRequest plan 
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S32");//X--�����ȹ(131)���� �⼺(136)������ �ִ��� Ȯ��
		plan.addParamObject("ds_list", ds_list);

		// �������庸�� Detail select(zcst146)
		DatasetSqlRequest zcst146s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S19");//X--�Ͻ����� ������Ʈ���� Ȯ��
		zcst146s.addParamObject("ds_list", ds_list);
		
		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U9");//X
		zcst126u.addParamObject("ds_list", ds_list);
		
		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U20");//X
		zcst126u2.addParamObject("ds_list", ds_list);
		
		// �ο����ְ������ update(zcst127)
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U23");//X
		zcst127u.addParamObject("ds_list", ds_list);
		
		// �ο����ְ������ update(zcst127)
		DatasetSqlRequest zcst127u2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U24");//X
		zcst127u2.addParamObject("ds_list", ds_list);

		// ȣ�� master update(zcst111)
		DatasetSqlRequest zcst111u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U10");//X
		zcst111u.addParamObject("ds_list", ds_list);
		
		// ����������� log insert(zcst148)
		DatasetSqlRequest zcst148i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I3");//X
		zcst148i.addParamObject("ds_list", ds_list);

		// ���ο� ���� �ߵ����� üũ select(zcst126)
		DatasetSqlRequest zcst126s2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S34");//X
		zcst126s2.addParamObject("ds_list", ds_list);

		// ���⼺ ���ο��� üũ Detail select(zcst136)
		DatasetSqlRequest zcst136s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S39");//X
		zcst136s.addParamObject("ds_list", ds_list);

		//�ߵ����� ���� ���� zcst630
		DatasetSqlRequest cs630i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_I1");//X
		cs630i.addParamObject("ds_zcst630", ds_zcst630);
		//�ߵ����� ��� �ø��� �� ��������
		DatasetSqlRequest cs630u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U1");//X
		cs630u.addParamObject("ds_zcst630", ds_zcst630);
		//�ߵ����� ���Ű��� �Ϸ� �� ������� ������Ʈ
		DatasetSqlRequest cs630u3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U3");//X
		cs630u3.addParamObject("ds_zcst630", ds_zcst630);
		//�ߵ����� ���� ���� �� �ݼ� �� ���ۼ� �� ��������
		DatasetSqlRequest cs630u4
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001H_U4");//X
		cs630u4.addParamObject("ds_zcst630", ds_zcst630);
		
		zcst166s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst166s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		plan.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		plan.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst146s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst146s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst148i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst148i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst136s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//�ߵ����� ���� ���� zcst630
		cs630i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//�ߵ����� ��� �ø��� �� ��������
		cs630u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//�ߵ����� ���Ű��� �Ϸ� �� ������� ������Ʈ
		cs630u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//�ߵ����� ���� ���� �� �ݼ� �� ���ۼ� �� ��������
		cs630u4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		cs630u4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		ComMethodVo comVo;
		ComMethodDao comDao;
		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				t_gno = ds_list.getColumnAsString(i, "GNO");
				
                //=============����� �����ȣ���� �ݾ� üũ�� ���� ���� �߰�=============
				chk = "";
				if(t_gno_tmp == "" || !t_gno.equals(t_gno_tmp) ) {
					t_gno_tmp = t_gno;
					chk = "X";
				}
				//=============================================================
				
				zcst126s.setRowIndex(i);
				Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();
				
				zcst111s.setRowIndex(i);
				Dataset dsRtn111 = (Dataset)executor.query(zcst111s).getResultObject();

				zcst131s.setRowIndex(i);
				Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();

				zcst146s.setRowIndex(i);
				Dataset dsRtn146 = (Dataset)executor.query(zcst146s).getResultObject();

				zcst166s.setRowIndex(i);
				Dataset dsRtn166 = (Dataset)executor.query(zcst166s).getResultObject();
				
				zcst126s2.setRowIndex(i);
				Dataset dsRtn126_2 = (Dataset)executor.query(zcst126s2).getResultObject();

				zcst136s.setRowIndex(i);
				Dataset dsRtn136 = (Dataset)executor.query(zcst136s).getResultObject();

				if(dsRtn126_2.getColumnAsInteger(0, "CNT") > 0) { //�ߵ����� �Ұ�(2012.07.25 LJH)
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ���� �ش���� ����ó�� �Ǿ����ϴ�. �ߵ����� ó���� ���� �մϴ�.");
				}

				if(dsRtn166.getColumnAsInteger(0, "CNT") > 0 || dsRtn136.getColumnAsInteger(0, "CNT") > 0) { //�������⼺ ���� �Ϸ� �Ǵ� ���⼺ ���� ó�� �Ǿ����� �ߵ����� �Ұ�
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ���� ���⼺�� �����Ǿ����ϴ�. �ߵ����� ó���� ���� �մϴ�.");
				}

				if(Integer.parseInt(dsRtn146.getColumnAsString(0, "CNT")) > 0) { //��������� ������ ������ Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ������� ������ �Դϴ�. �ٽ� �ѹ� Ȯ���� �ֽʽÿ�.");
				}

				//if(!dsRtn126.getColumnAsString(0, "VBELN").trim().equals("") && dsRtnvbak.getColumnAsString(0, "SUJU").equals("N")) { //SO�� �ְ� VBAK�� ���ֱݾװ� ZCST126�� �ݾ��� �����ϸ� �ߵ����� ó�� ����

//				if(dsRtn126.getColumnAsString(0, "VBELN").trim().equals("")) { //ZCST126���� ���ֹ�ȣ�� ���� ��� �������ó���� �Ѵ�.
//					throw new BizException("���ֹ�ȣ�� �����ϴ�. '�������' ó�� �ٶ��ϴ�.");
//				} else {
					if(dsRtn111.getColumnAsInteger(0, "CNT") > 0) { //��������Ʈ ȣ�⺰�� ���а� �Ѱ��̶� ������ �ߵ����� ó�� ����
						throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ���� �������Դϴ�. �ߵ����� ó���� ���� �մϴ�.");
					} else {
						if(dsRtn131.getRowCount() > 0 && dsRtn131.getColumnAsString(0, "GBN").equals("X")) { //����������Ʈ ȣ�⺰�� ����Ȯ���� �Ѱ��̶� ����Ǿ����� �ߵ����� ó�� ����
							throw new BizException(String.valueOf(i + 1) + " ��° ȣ���� ���� Ȯ���� �Ϸ�Ǿ����ϴ�. �ߵ����� ó���� ���� �մϴ�.");
						} else {							
							if("A1".equals(ds_list.getColumnAsString(i, "PST"))) {								
								//logger.debug("start Draft");							
							} else {							
								logger.debug("�ߵ����� ó��");																				
								zcst126u.setRowIndex(i);
								zcst111u.setRowIndex(i);
								zcst148i.setRowIndex(i);
	
								executor.execute(zcst126u);
								executor.execute(zcst111u);
								executor.execute(zcst148i);
								
								//====================������ �����ȣ�� ù��° ���̸鼭, �ο����� �������� �����Ѵٸ�=======================
								//====================�ش� �����ȣ ������ ������, �ο����� �������� �ߵ��������ڷ� ������==================
								//====================���������� ��� ������ True ������ ���ϵǾ� ���������� ������=======================
								//if(i == 0 && ds_list.getColumnAsString(i, "MAXSEQ") != "") {
								if(i == 0 && !(ds_list.getColumnAsString(i, "MAXSEQ").trim().equals(""))) {
									zcst127u.setRowIndex(i);
									executor.execute(zcst127u);
								}
	
								//�⼺�� ����
								comVo = new ComMethodVo();
								comDao = new ComMethodDao();
	
								comVo.setDataId("66"); //�ߵ�����
								comVo.setJobGubun("I");
	
								comVo.setMandt(CommonUtil.NullToBlank(ctx.getInputVariable("G_MANDT")));
								comVo.setUpn(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "UPN")));
								comVo.setCst(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "CST")));
								comVo.setPjt(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "PJT")));
								comVo.setHno(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "HNO")));
								comVo.setSeq(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "SEQ")));
								//====================������ ��� ù��° �����ȣ�� �ο������� �������� �����Ͽ�==========================
								//====================�ߵ����� ó���� �Ͽ��´� �κ������θ� �ߵ����� ó���� �Ǿ�==========================
								//====================SRM �ߵ����� �ҽ����� �����ȣ�� �ο����� �������� ���Ͽ�=========================
								//====================�⼺��ȹ �� �����ȹ�� �ߵ����� ó���ǵ��� ó���Ͽ�����============================
								//====================�������� ���� ���. �� �ο����ְ� ���� ��쿡�� ���ڰ��� �����Ƿ�=====================
								//====================comDao.SetBXRBXL�� �ο����� �ߵ����� ������ �������� �ʴ´�====================
	//							if(i == 0) {
									comVo.setMaxSeq(CommonUtil.NullToBlank(ds_list.getColumnAsString(i, "MAXSEQ")));
									System.out.println(CommonUtil.NullToBlank(t_gno) + " : " + i + " : " + comVo.getMaxSeq());
	//							} else {
	//								comVo.setMaxSeq("");
	//							}
								//======================================================================================
								comVo.setUserId(CommonUtil.NullToBlank(ctx.getInputVariable("G_USER_ID")));
								comVo.setGno(CommonUtil.NullToBlank(t_gno));
								
								int rtnCode = comDao.SetBXRBXL(comVo);
	
								plan.setRowIndex(i);
								Dataset dsRtnPlan = (Dataset)executor.query(plan).getResultObject();
	
								logger.debug("dsRtnPlan.getColumnAsInteger(0, 'CNT') ==> [" + dsRtnPlan.getColumnAsInteger(0, "CNT") + "]");
	
								/* 2012.06.11 LJH ��ݰ�ȹ ��û�� üũ���� ���� */
								if(dsRtnPlan.getColumnAsInteger(0, "CNT") > 0) { //���� �� �⼺��ȹ ��뿩�� üũ�� ����ȭ ���� �߻��� �ߵ����� ó�� ����
									logger.debug("���� �� �⼺��ȹ�� ������ �߻��Ͽ����ϴ�. �ߵ����� ó���� ���� �մϴ�.");
									throw new BizException("���� �� �⼺��ȹ�� ������ �߻��Ͽ����ϴ�. �ߵ����� ó���� ���� �մϴ�.");
								}
	
								logger.debug("zcst126u2 START!!");
	
								zcst126u2.setRowIndex(i);
								executor.execute(zcst126u2);
								//=======================�ο����� ���ֱⰣ�������� �ߵ����� -1�� �������ڷ� �����ϴ� �����̸�======================
								//=======================���������� ��� ù���� �����ȣ�� �����Ͽ����� �κ������� �������� ������ �߻��Ǿ�����===========
								//=======================���������� ��� ������ True ������ ���ϵǾ� ���������� ������==========================
								//=======================�ߵ������� ������ �ο����� �����ȹ �� �⼺��ȹ�� ó���ϰ� ����==========================
								//=======================�ش� ������ �����Ͽ� ���ֱⰣ�������� �ߵ����� -1�� Ȥ�� �̷��� �������ȣ�� ��� ���ֱⰣ �����Ϸ� ������=================
	//							if(i == 0 && ds_list.getColumnAsString(i, "MAXSEQ") != "") {
								if(!(ds_list.getColumnAsString(i, "MAXSEQ").trim().equals(""))) {
									zcst127u2.setRowIndex(i);
									executor.execute(zcst127u2);
								}
							}
						}
//					}
				}
			}
			
		}

		if("I".equals(ds_zcst630.getColumnAsString(0, "FLAG"))) {
			executor.execute(cs630u4);
			executor.execute(cs630i);
		} else if("U".equals(ds_zcst630.getColumnAsString(0, "FLAG"))) {
			executor.execute(cs630u);
		} else {
			executor.execute(cs630u3);
		}
	}

	public void seungin_cancel(BusinessContext ctx, Dataset ds_list) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		// ��������  select(vbak)//����ȣ�� �����Ǵ� ���ֹ�ȣ
		DatasetSqlRequest vbaks
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S31");//X
		vbaks.addParamObject("ds_list", ds_list);

		// �����ȹ���� select(zcst131)//
		DatasetSqlRequest zcst131s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S11");//X
		zcst131s.addParamObject("ds_list", ds_list);

		// ���������������� select(zcst166)
		DatasetSqlRequest zcst166s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S16");//X
		zcst166s.addParamObject("ds_list", ds_list);
		
		// ȣ�� master update(zcst111)
		DatasetSqlRequest zcst111u1
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U11");//X
		zcst111u1.addParamObject("ds_list", ds_list);

		DatasetSqlRequest zcst111u2
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U16");//X
		zcst111u2.addParamObject("ds_list", ds_list);

		DatasetSqlRequest zcst111u3
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U17");//X
		zcst111u3.addParamObject("ds_list", ds_list);
		
		DatasetSqlRequest zcst111u4
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U18");//X
		zcst111u4.addParamObject("ds_list", ds_list);
				
		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U12");//X
		zcst126u.addParamObject("ds_list", ds_list);

		// �ο����ְ������ update(zcst127)
		DatasetSqlRequest zcst127u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U13");//X
		zcst127u.addParamObject("ds_list", ds_list);
		
		// �����ȹ���� update(zcst131)
		DatasetSqlRequest zcst131u
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_D1");
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U14");//X
		zcst131u.addParamObject("ds_list", ds_list);
		
		//�ӽ� ��� 2008.10.28 LJH
		// �����ȹ���� update(zcst131)
		DatasetSqlRequest zcst131u2
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_D1");
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U19");//X
		zcst131u2.addParamObject("ds_list", ds_list);
		//�ӽ� ��� 2008.10.28 LJH

		// �⼺�����ް�ȹ���� update(zcst136)
		DatasetSqlRequest zcst136u
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_D2");
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U15");//X
		zcst136u.addParamObject("ds_list", ds_list);

		// �����ȹ���� update(zcst131)
		DatasetSqlRequest zcst131u3
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_D1");
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U21");//X
		zcst131u3.addParamObject("ds_list", ds_list);
		
		// �⼺�����ް�ȹ���� update(zcst136)
		DatasetSqlRequest zcst136u3
//			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_D2");
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U22");//X
		zcst136u3.addParamObject("ds_list", ds_list);
		
		// ����������� log insert(zcst148)
		DatasetSqlRequest zcst148i
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I5");//X
		zcst148i.addParamObject("ds_list", ds_list);
		
		// ������ȣ������ update(zmaster02)
		DatasetSqlRequest zmaster02u
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U26");//X
		zmaster02u.addParamObject("ds_list", ds_list);

		// ���ο� ���� ������� üũ select(zcst126)
		DatasetSqlRequest zcst126s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S35");//X
		zcst126s.addParamObject("ds_list", ds_list);

		// �� ������� ���� üũ select(zeist104)
		DatasetSqlRequest zeist104s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S37");//X
		zeist104s.addParamObject("ds_list", ds_list);

		// ���⼺ ���ο��� üũ Detail select(zcst136)
		DatasetSqlRequest zcst136s
			= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S39");//X
		zcst136s.addParamObject("ds_list", ds_list);

		vbaks.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		vbaks.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst166s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst166s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst111u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst111u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst111u4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst127u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst131u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		//�ӽ� ��� 2008.10.28 LJH
		zcst131u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		//�ӽ� ��� 2008.10.28 LJH

		zcst136u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst131u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst136u3.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136u3.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zcst148i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst148i.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		
		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zeist104s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zeist104s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst136s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst136s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		for(int i=0; i<ds_list.getRowCount(); i++) {
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
				zcst166s.setRowIndex(i);
				Dataset dsRtn166 = (Dataset)executor.query(zcst166s).getResultObject();

				zcst126s.setRowIndex(i);
				Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();

				zeist104s.setRowIndex(i);
				Dataset dsRtn104 = (Dataset)executor.query(zeist104s).getResultObject();

				zcst136s.setRowIndex(i);
				Dataset dsRtn136 = (Dataset)executor.query(zcst136s).getResultObject();

//				if(dsRtn126.getColumnAsInteger(0, "CNT") > 0) { //������� �Ұ�(2012.07.25 LJH)
//					throw new BizException(String.valueOf(i + 1) + " ��° ȣ���� �ش���� ����ó�� �Ǿ����ϴ�. ������� ó���� ���� �մϴ�.");
//				}
/* 2014.02.03 */

				if(dsRtn104.getColumnAsInteger(0, "CNT") > 0) { //���� �� ������� �Ϸ� ��, ���󰳽��� �� ������-�������� üũ
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� �� ��������� �Ϸ�Ǿ����ϴ�. ������� ó���� ���� �մϴ�.");
				}

				if(dsRtn166.getColumnAsInteger(0, "CNT") > 0 || dsRtn136.getColumnAsInteger(0, "CNT") > 0) { //�������⼺ ���� �Ϸ� ó�� �Ǿ����� ������� �Ұ�(�����޿� ���� �� �⼺�� - ó��)
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ���� ���⼺�� �����Ǿ����ϴ�. ������� ó���� ���� �մϴ�.");
				} else {
					vbaks.setRowIndex(i);
					Dataset dsRtnvbak = (Dataset)executor.query(vbaks).getResultObject();

					zcst131s.setRowIndex(i);
					Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();

					if(!CommonUtil.NullToBlank(dsRtnvbak.getColumnAsString(0, "GBN")).trim().equals("N")) { //SAP���� ���ֱݾ� 0������ ���� ��ó���� ������� ó�� ����
						throw new BizException("SAP���� ���ֱݾ� ������ �۾��Ͻñ� �ٶ��ϴ�. ������� ó���� ���� �մϴ�.");
					} else {
						//�ӽ� ��� 2008.10.28 LJH
						//dsRtn131.setColumn(0, "CS131_TIS", "");
						//�ӽ� ��� 2008.10.28 LJH
						if(!CommonUtil.NullToBlank(dsRtn131.getColumnAsString(0, "CS131_TIS")).trim().equals("")) { //����������Ʈ ȣ�⺰�� ����Ȯ���� �Ѱ��̶� ����Ǿ����� ������� ó�� ����
							throw new BizException(String.valueOf(i + 1) + " ��° ȣ���� ���� Ȯ���� �Ϸ�Ǿ����ϴ�. ������� ó���� ���� �մϴ�.");
						} else {
							zcst126u.setRowIndex(i);
							zcst127u.setRowIndex(i);
							zcst131u.setRowIndex(i);
							zcst136u.setRowIndex(i);
							zcst148i.setRowIndex(i);
							//zcst136u.setRowIndex(i);
							//�ӽ� ��� 2008.10.28 LJH
							zcst131u2.setRowIndex(i);
							//�ӽ� ��� 2008.10.28 LJH
							zcst131u3.setRowIndex(i);
							zcst136u3.setRowIndex(i);
	//						zmaster02u.setRowIndex(i);
							if("C".equals(ds_list.getColumnAsString(i, "GND").trim())) { //�������
								if(ds_list.getColumnAsInteger(i, "CS2") == 1) { //��������� 1�϶�
									zcst111u1.setRowIndex(i);
								} else if(ds_list.getColumnAsInteger(i, "CS2") > 1) { //��������� 1���� Ŭ��
									zcst111u2.setRowIndex(i);
								}
							} else if("D".equals(ds_list.getColumnAsString(i, "GND").trim())) { //�����Ϲ�
								if(ds_list.getColumnAsInteger(i, "IS2") == 1) { //��������� 1�϶�
									zcst111u3.setRowIndex(i);
								} else if(ds_list.getColumnAsInteger(i, "IS2") > 1) { //��������� 1���� Ŭ��
									zcst111u4.setRowIndex(i);
								}
							}
	
							executor.execute(zcst126u);
							executor.execute(zcst127u);
							executor.execute(zcst131u);
							executor.execute(zcst136u);
							executor.execute(zcst148i);
							//�ӽ� ��� 2008.10.28 LJH
							executor.execute(zcst131u2);
							//�ӽ� ��� 2008.10.28 LJH
							executor.execute(zcst131u3);
							executor.execute(zcst136u3);
	//						executor.execute(zmaster02u);
							if("C".equals(ds_list.getColumnAsString(i, "GND").trim())) { //�������
								if(ds_list.getColumnAsInteger(i, "CS2") == 1) { //��������� 1�϶�
									executor.execute(zcst111u1);
								} else if(ds_list.getColumnAsInteger(i, "CS2") > 1) { //��������� 1���� Ŭ��
									executor.execute(zcst111u2);
								}
							} else if("D".equals(ds_list.getColumnAsString(i, "GND").trim())) { //�����Ϲ�
								if(ds_list.getColumnAsInteger(i, "IS2") == 1) { //��������� 1�϶�
									executor.execute(zcst111u3);
								} else if(ds_list.getColumnAsInteger(i, "IS2") > 1) { //��������� 1���� Ŭ��
									executor.execute(zcst111u4);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void save3(BusinessContext ctx, Dataset ds_gbn, Dataset ds_list, Dataset ds_lists) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		String kind = ds_gbn.getColumnAsString(0, "KIND");
//		String gbn  = ds_gbn.getColumnAsString(0, "GBN");
		String gbn  = "";
		String t_gno = "";

		// �ο��������� select(zcst127)
		DatasetSqlRequest zcst127s	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S14");//X
		zcst127s.addParamObject("ds_list", ds_list);

		// ȣ��master update(zcst111)
		DatasetSqlRequest zcst111u1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U1");//X
		zcst111u1.addParamObject("ds_list", ds_list);
		
		// ȣ��master update(zcst111)
		DatasetSqlRequest zcst111u2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U3");//X
		zcst111u2.addParamObject("ds_list", ds_list);
		
		// ���󺸼��������� select(zcst116)
		DatasetSqlRequest zcst116s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S4");//X
		zcst116s.addParamObject("ds_list", ds_list);
		
		// ���󺸼��������� select(zcst116)
		DatasetSqlRequest zcst116s2 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S30");//X
		zcst116s2.addParamObject("ds_list", ds_list);

		// ���󺸼��������� update(zcst116)
		DatasetSqlRequest zcst116u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U2");//X
		zcst116u.addParamObject("ds_list", ds_list);
		
		// ���󺸼��������� update(zcst116)
		DatasetSqlRequest zcst116u2	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U6");//X
		zcst116u2.addParamObject("ds_list", ds_list);

		// ���󺸼�������� select(zcst126)
		DatasetSqlRequest zcst126s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S10");//X
		zcst126s.addParamObject("ds_list", ds_list);
		
		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U4");//X
		zcst126u1.addParamObject("ds_list", ds_list);

		// ���󺸼�������� update(zcst126)
		DatasetSqlRequest zcst126u2	= SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U5");//X
		zcst126u2.addParamObject("ds_list", ds_list);

		// ����ó ���� insert(zfit1031)
		DatasetSqlRequest zfit1031i4 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I4");//X
		zfit1031i4.addParamObject("ds_list", ds_list);

		// ������ȣ������ update(zmaster02)
		DatasetSqlRequest zmaster02u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U25");//X
		zmaster02u.addParamObject("ds_list", ds_list);

		// �̹��� �� ���� �������� update(zcst113) : ���ܰ�, �Ⱓ���ֿ���ݾ�(�����ݾ��� �������� ��(�ϴ���))
		DatasetSqlRequest zcst113u = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U27");//X
		zcst113u.addParamObject("ds_list", ds_list);

		// �����ȹ���� select(zcst131)
		DatasetSqlRequest zcst131s = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_S25");//X
		zcst131s.addParamObject("ds_list", ds_list);

		// ���⺸�� ������ update(zcst128)
		DatasetSqlRequest zcst128u1 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_U30");//X
		zcst128u1.addParamObject("ds_list", ds_list);
		zcst128u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		
		
		zcst127s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst127s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst111u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst111u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116s2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116s2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst116u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst116u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u1.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u1.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst126u2.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst126u2.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

//		zfit1031i4.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
//		zfit1031i4.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zmaster02u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zmaster02u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst113u.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst113u.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));

		zcst131s.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		zcst131s.addParamObject("G_USER_ID", ctx.getInputVariable("G_USER_ID"));
		

		ComMethodVo comVo;
		ComMethodDao comDao;

		int z = 0;

		for(int i=0; i<ds_list.getRowCount(); i++) 
		{
			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) 
			{
				gbn = ds_list.getColumnAsString(i, "GKD");

				ds_list.setColumn(i, "UMS", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "UMS"))));
				ds_list.setColumn(i, "MMN", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "MMN"))));
				ds_list.setColumn(i, "JKH", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "JKH"))));
//				ds_list.setColumn(i, "RTO", Integer.parseInt(CommonUtil.NullBlankToZero(ds_list.getColumnAsString(i, "RTO"))));
				
//				if(i == 0) { //���� �ѹ��� GNO �� �����Ѵ�. ==> USD �� Ʋ�� ��찡 �����Ƿ� row ���� �����Ѵ�.
//					zcst126s.setRowIndex(i);
//					Dataset dsRtn126 = (Dataset)executor.query(zcst126s).getResultObject();
					
//					zcst126u2.addParamObject("GNO", dsRtn126.getColumnAsString(0, "GNO"));

//					t_gno = "";
					t_gno = ds_list.getColumnAsString(i, "UPN") + 
					        "U" + 
					        ds_list.getColumnAsString(i, "UGS").substring(2,6) + 
					        "-" + 
					        ds_list.getColumnAsString(i, "CST");
					
					//zcst126u2.addParamObject("GNO", t_gno);
					ds_list.setColumn(i, "GNO", t_gno);
//				}
				
				zcst127s.setRowIndex(i);
				Dataset dsRtn127 = (Dataset)executor.query(zcst127s).getResultObject();
				
				if(dsRtn127.getRowCount() > 0) { //�ο����� S/O ���� �����Ͱ� ������ ���� ���� �߻�
					throw new BizException("���� ������ �����Ϳ� �ش� �ϴ� �ο����� �������� S/O ��ȣ�� ���� �մϴ�. ����ó���� ����մϴ�.");
				}

				zcst116s2.setRowIndex(i);
				Dataset dsRtn1162 = (Dataset)executor.query(zcst116s2).getResultObject();

				if(Integer.parseInt(dsRtn1162.getColumnAsString(0, "CNT")) > 0) { //������ �ȵ� ������� ������ ������ Exception
					throw new BizException(String.valueOf(i + 1) + " ��° ȣ��� ������� ����ó���� �Ϸ���� �ʾҽ��ϴ�. ������� ������ �ٽ� �ѹ� Ȯ���� �ֽʽÿ�.");
				}

				zcst126u2.setRowIndex(i);
//				zcst116u2.setRowIndex(i);
				zcst111u1.setRowIndex(i);
				zcst113u.setRowIndex(i);
				zcst128u1.setRowIndex(i);

				executor.execute(zcst126u2);
//				executor.execute(zcst116u2);
				executor.execute(zcst111u1);
				executor.execute(zcst113u);
				executor.execute(zcst128u1);

				try
				{
					zfit1031i4.addParamObject("GNO", t_gno);
					zfit1031i4.setRowIndex(i);
					executor.execute(zfit1031i4);
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}

				if(kind.equals("D"))  //�����Ϲ��� ���
				{
					if(gbn.equals("3")) { //��౸���� ��ຯ���� ���
						zcst126u1.setRowIndex(i);
						executor.execute(zcst126u1);
					}
					
//					zmaster02u.setRowIndex(i);
//					executor.execute(zmaster02u);
					
					zcst116s.setRowIndex(i);
					Dataset dsRtn116 = (Dataset)executor.query(zcst116s).getResultObject();
	
					if(dsRtn116.getRowCount() > 0) { //�����Ϲݺ����� �������� ��쿡�� ������ �����Ϲ� ���� ������ ��� ó��
						zcst116u.setRowIndex(i);
						zcst111u2.setRowIndex(i);
	
						executor.execute(zcst116u);
						executor.execute(zcst111u2);
					}
				}
				
				//�⼺�� ����
				comVo = new ComMethodVo();
				comDao = new ComMethodDao();
	
				if("1".equals(ds_list.getColumnAsString(i, "GKD")) || "5".equals(ds_list.getColumnAsString(i, "GKD")))
					if("C".equals(ds_list.getColumnAsString(i, "GND"))) {
						comVo.setDataId("21");//�������
					} else {
						comVo.setDataId("31");//�ڷ�id �ű� ,Ÿ��ű�
					}
				else if("2".equals(ds_list.getColumnAsString(i, "GKD")))
					comVo.setDataId("32");//����
				else if("3".equals(ds_list.getColumnAsString(i, "GKD")))
					comVo.setDataId("34");//��ຯ��(��)
				else if("4".equals(ds_list.getColumnAsString(i, "GKD"))){//����ȸ�� 
					if("3".equals(ds_list.getColumnAsString(i, "WIL"))){					
						comVo.setDataId("35");//Ÿ��ȸ�� 
					}	else{					
						comVo.setDataId("36");//�ڻ�ȸ�� 
					}
				}else if("2".equals(ds_list.getColumnAsString(i, "CHK_QUERY"))){//�ڵ�����
					comVo.setDataId("37");
				}else{
					throw new BizException("Data Id�� ���� ���� �ʾҽ��ϴ�");
				}
	
				if("3".equals(ds_list.getColumnAsString(i, "GKD")))   
					 comVo.setJobGubun("I/D");
				else
					 comVo.setJobGubun("I");
	
				comVo.setMandt(ctx.getInputVariable("G_MANDT"));
				comVo.setUpn(ds_list.getColumnAsString(i, "UPN"));
				comVo.setCst(ds_list.getColumnAsString(i, "CST"));
				comVo.setPjt(ds_list.getColumnAsString(i, "PJT"));
				comVo.setHno(ds_list.getColumnAsString(i, "HNO"));
				comVo.setSeq(ds_list.getColumnAsString(i, "SEQ"));
				comVo.setSla(ds_list.getColumnAsString(i, "SLA"));
				comVo.setUserId(ctx.getInputVariable("G_USER_ID"));
				comVo.setGno(t_gno);

				int rtnCode = comDao.SetBXRBXL(comVo);

				if(rtnCode == 0) {
					zcst131s.setRowIndex(i);
					Dataset dsRtn131 = (Dataset)executor.query(zcst131s).getResultObject();
					
					if(Integer.parseInt(dsRtn131.getColumnAsString(0, "CNT")) > 1) { //���� �����Ͱ� �ߺ� �߻������� ���� ���� �߻�
						throw new BizException("���� ������ �����Ϳ� �ش� �ϴ� ���� �����Ͱ� �ߺ��߻� �Ǿ����ϴ�. ����ó���� ����մϴ�.");
					}
				}

				z++;
			}
		}
		// �ο�����(����) ���� ó�� 201601
		CS1201001D_ServiceImpl sjsave2;
		sjsave2 = new CS1201001D_ServiceImpl();
		sjsave2.save2(ctx,ds_lists);
	}
	
	public void saveCost(BusinessContext ctx, Dataset ds_list, Dataset ds_cost) throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		String t_gno = "";

		// ������ ���� insert(zcst128)
		DatasetSqlRequest zcst128i = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1201001B_I6");//X
		zcst128i.addParamObject("ds_cost", ds_cost);
		zcst128i.addParamObject("G_MANDT", ctx.getInputVariable("G_MANDT"));
		for(int i=0; i<ds_list.getRowCount(); i++) 
		{
//			if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) 
//			{
				t_gno = ds_cost.getColumnAsString(i, "GNO");
				String hgb = "";
				hgb = ds_list.getColumnAsString(i, "HNO").substring(0,1);

				if((hgb.equals("L") || hgb.equals("S") || hgb.equals("W"))) {
					ds_cost.setColumn(0, "GNO", t_gno); 
					zcst128i.setRowIndex(0);
					executor.execute(zcst128i);
				}
//			}
		}
	}
	
}

