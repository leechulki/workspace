package com.helco.xf.cs12.ws;

import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.Variant;
import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.Utillity;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.batch.TransForBSC;
import com.helco.xf.cs12.batch.TransForBSG;
import com.helco.xf.cs12.batch.TransForBWA;
import com.helco.xf.cs12.batch.TransForBWE;
import com.helco.xf.cs12.batch.TransForBWM;
import com.helco.xf.cs12.batch.TransForBWZ;
import com.helco.xf.cs12.batch.TransForBXL;
import com.helco.xf.cs12.batch.TransForBXR;
import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWMF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs34.ws.ZCSS010;
import com.helco.xf.cs34.ws.ZCSS011;
import com.helco.xf.cs41.ws.ZCSS012;
import com.helco.xf.wb01.batch.FileFilter;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.TitUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;


public class CS1299001A_ServiceImpl extends AbstractBusinessService implements
		CS1299001A_Service {
	static Logger logger;
	private static final long serialVersionUID = 1L;

	/*****************************************
	 *************** ���⺸�� ���� �� CS1240001B
	 **��࿹���� ���� �� ȣ�⺰ SEG �� ǥ�شܰ� ����
	 *****************************************/
	public void getStdPrice2(BusinessContext ctx, Dataset ds_list) throws Exception {
		
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		DatasetSqlRequest dr_hnoinfo = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S4");
		dr_hnoinfo.addParamObject("ds_list", ds_list);
		dr_hnoinfo.addParamObject("G_MANDT", mdt);
		
		DatasetSqlRequest zcst510 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S3");
		zcst510.addParamObject("G_MANDT", mdt);

		zcst510.setRowIndex(0);
		Dataset ds510 = (Dataset)executor.query(zcst510).getResultObject();
		// ���� ����
		DatasetSqlRequest zcst600 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S6");
		zcst600.addParamObject("G_MANDT", mdt);

		zcst600.setRowIndex(0);
		Dataset ds600 = (Dataset)executor.query(zcst600).getResultObject();
		

		//String pgmID= ds_list.getColumnAsString(0, "PGMID");
		String pgmID = "ESTIMATE";
		
		for(int i=0; i<ds_list.getRowCount(); i++) {
			
			dr_hnoinfo.setRowIndex(i);
			Dataset ds_hnoinfo = (Dataset)executor.query(dr_hnoinfo).getResultObject();
			if(ds_hnoinfo.getRowCount() > 0) {
				
				getPrice(ds_hnoinfo,ds510,ds600,pgmID);
				
				ds_list.setColumn(i, "P10"   , ds_hnoinfo.getColumnAsDouble(0, "P10") );
				ds_list.setColumn(i, "F10"   , ds_hnoinfo.getColumnAsDouble(0, "F10") );
				ds_list.setColumn(i, "SEG"   , ds_hnoinfo.getColumnAsString(0, "SEG") );
				ds_list.setColumn(i, "CNT_HRTS"   , ds_hnoinfo.getColumnAsString(0, "CNT_HRTS") );
			}
		}
		ctx.addOutputDataset(ds_list);
	}

	/*****************************************
	 *************** ǥ�شܰ�ǥ ȭ�� CS1299001A
	 **�����Ͽ� ���� ȣ�⺰ SEG �� ǥ�شܰ� ����
	 *****************************************/
	public void getStdPrice(BusinessContext ctx, Dataset ds_list, Dataset ds_cond) throws Exception {
		
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		DatasetSqlRequest dr_hnoinfo = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S2");
		dr_hnoinfo.addParamObject("ds_cond", ds_cond);
		dr_hnoinfo.addParamObject("G_MANDT", mdt);
		
		DatasetSqlRequest zcst510 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S3");
		zcst510.addParamObject("G_MANDT", mdt);

		zcst510.setRowIndex(0);
		Dataset ds510 = (Dataset)executor.query(zcst510).getResultObject();
		// ���� ����
		DatasetSqlRequest zcst600 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S6");
		zcst600.addParamObject("G_MANDT", mdt);

		zcst600.setRowIndex(0);
		Dataset ds600 = (Dataset)executor.query(zcst600).getResultObject();
		
		ds_list = (Dataset)executor.query(dr_hnoinfo).getResultObject();
		// ǥ�غ����� STDPRICE	
		String pgmID= ds_cond.getColumnAsString(0, "PGMID");
		// ǥ�غ����� �� seg	
		getPrice(ds_list,ds510,ds600,pgmID);
		// sto ����
		getSTO(ds_list);

		ctx.addOutputDataset(ds_list);
		
	}
	
	/*****************************************
	 *************** getPrice
	 **FM / POG ȣ�⺰ SEG �� ǥ�شܰ� ���ϱ�
	 * @param pgmID 
	 * @param rat_600 
	 *****************************************/
	public Dataset getPrice(Dataset ds_hnoinfo, Dataset ds510, Dataset ds600, String pgmID) {
		
		// ==============2018.06.26 �߰� ���� ���� =====================
		// HashMap
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(int j = 0; j < ds510.getRowCount(); j++) {
			String seg_510 = DatasetUtility.getString(ds510, j, "SEG");  //ds510.getString(j, "SEG");
			String gbu_510 = DatasetUtility.getString(ds510, j, "GBU");  //ds510.getString(j, "GBU");
			int age_510 = DatasetUtility.getInt(ds510, j, "AGE");  //ds510.getInt(j, "AGE");
			String vkg_510 = DatasetUtility.getString(ds510, j, "VKGRP");  //ds510.getString(j, "VKGRP");
			
			String key = seg_510+gbu_510+age_510+vkg_510;
			map.put(key, j);
		}
		// ===============2018.06.26 �߰� ���� ���� 	===================	
		
		for(int i=0; i<ds_hnoinfo.getRowCount(); i++) {
			String pjthno = "";
			String hno = "";   		// ȣ�� ����
			String vkgrp = "";   		// ȣ�� ����
			int zspec3 = 0;   		// �ӵ�
			int zspec7 = 0;    		// ��
			String zspec2 = "";		// ���
			String zspec12 = "";	// �������		
			String spd = "";   		// �ӵ� ����
			int cnt = 0;	   		// �� ���
			int age = 0;			// ����	
			String abg = "";		// O ��������
			String abg_c = "";		// N �������� CODE
			String abg_k = "";		// N �������� CODE NAME	
			String srg   = "";		// ROPING ���� CODE NAME
			String srg_c = "";		// ROPING ���� CODE	
			String seg = "";		// SEG CODE NAME
			String seg_c = "";		// SEG CODE
			
			pjthno  = ds_hnoinfo.getColumnAsString(i, "PJT") + ds_hnoinfo.getColumnAsString(i, "HNO");
			hno     = ds_hnoinfo.getColumnAsString(i, "HNO").substring(0, 1);		
			zspec2  = ds_hnoinfo.getColumnAsString(i, "ZSPEC2");	// ���	
			zspec12 = ds_hnoinfo.getColumnAsString(i, "ZSPEC12");	// �������
			abg     = ds_hnoinfo.getColumnAsString(i, "ABG");		// O ��������
  			cnt     = ds_hnoinfo.getColumnAsInteger(i, "CNT");		// �� ���		
			age     = ds_hnoinfo.getColumnAsInteger(i, "AGEK");	// ����		
			vkgrp   = ds_hnoinfo.getColumnAsString(i, "VKGRP");

			
			if("".equals(abg)){
				//throw new BizException(pjthno + " �� �������а��� �����ϴ�. ȣ��⺻���� �������� �����Ͻʽÿ�.");
  			} 			
  			// �ӵ� �� ���� ��� 0���� ����
  			if("".equals(ds_hnoinfo.getColumnAsString(i, "ZSPEC3"))){
  				zspec3 = 0;
  			} else {
  				zspec3 = ds_hnoinfo.getColumnAsInteger(i, "ZSPEC3"); 
  			}
  			// ���� �� ���� ��� 0���� ����
  			if("".equals(ds_hnoinfo.getColumnAsString(i, "ZSPEC7"))){
  				zspec7 = 0;
  			} else {
  				zspec7 = ds_hnoinfo.getColumnAsInteger(i, "ZSPEC7");
  			}
			// ���� ���� 
			if(age > 20) { 
				age = 20;
			} else if(age < 1) { 
				age = 1;
			}		
		
			// ROPING 
  			ArrayList srgList = getSRG(zspec12, abg , zspec3);  			
  			srg   = srgList.get(0).toString() ;
  			srg_c = srgList.get(1).toString() ;

			/*SEG ���� : ��������*/
  			ArrayList segList = getSEG(zspec3, hno, zspec2, cnt, abg, zspec12);
  			seg   = segList.get(0).toString() ;
  			seg_c = segList.get(1).toString() ;
						
  			/*SEG ���� END*/	
			ds_hnoinfo.setColumn(i, "SRG"	, srg);
			ds_hnoinfo.setColumn(i, "SEG"   , seg);
			ds_hnoinfo.setColumn(i, "SEG_CD", seg_c);
			
			double CS600_RAT = ds600.getColumnAsDouble(0, "CS600_RAT");
			double CS604_US  = ds600.getColumnAsDouble(0, "CS604_US");  // ���⺸�� ������
			double J_RAT = ds600.getColumnAsDouble(0, "J_RAT"); //�����빫�� ��ΰ��
			double H_RAT = ds600.getColumnAsDouble(0, "H_RAT"); //���³빫�� ��ΰ��
			double K_RAT = 0 ; 	 //�������
			K_RAT = J_RAT / 100; // ǥ�شܰ� �������� ���� ��������
				
			// SEG ���� ��౸�� �� ǥ�� �׸� �������� ZCST510 511
  			// �����빫�� ������ ���� ���� ZCST512
			//	getIDX(seg_c, age, knd, ds510, vkgrp);		

			int idx510f = 0;
			int idx510p = 0;
	
			double std1,std2,std3,std4,std5,std6,std7;
			double item1,item2,item3,item4,item5,item6,item7,item8,item9;
			double amt510,ext,fee1,ext1,fee2,ext2,num;
			double rat_512 = 0.0;
			double f1,f2,f3,f4,f5,f6,f7,f8,f9,f10;	// FM ǥ�شܰ�(F10) �׸�
			double p1,p2,p3,p4,p5,p6,p7,p8,p9,p10;	// POG ǥ�شܰ�(P10) �׸�

			/********************************************************/
			/**************** FM  ǥ�شܰ� ���  ************************/	
			//====== 2018.06.26 ======
			//idx510f = getIDX(seg_c, age, "A", ds510, vkgrp);
			idx510f = getIDX(seg_c, age, "A", map, vkgrp); //ds510, vkgrp);
			//========================	
			
			//System.out.println(" idx510f   >>"   + idx510f );
			std3 = ds510.getColumnAsInteger(idx510f, "STD3");	 //SEG �� ������
			std4 = ds510.getColumnAsInteger(idx510f, "STD4");
			item1 = ds510.getColumnAsInteger(idx510f, "ITEM1");	// SEG�� �빫��
			item2 = ds510.getColumnAsInteger(idx510f, "ITEM2");	
			item3 = ds510.getColumnAsInteger(idx510f, "ITEM3");	
			item4 = ds510.getColumnAsInteger(idx510f, "ITEM4");	// ��������
			item5 = ds510.getColumnAsInteger(idx510f, "ITEM5");
			item6 = ds510.getColumnAsInteger(idx510f, "ITEM6");
			item7 = ds510.getColumnAsInteger(idx510f, "ITEM7");	
			item8 = ds510.getColumnAsInteger(idx510f, "ITEM8");	// ��������
			item9 = ds510.getColumnAsInteger(idx510f, "ITEM9");	// �������ͺ���
			amt510 = ds510.getColumnAsInteger(idx510f, "AMT");	// �����
			ext = ds510.getColumnAsInteger(idx510f, "EXT");	// ����� ����
			fee1 = ds510.getColumnAsInteger(idx510f, "FEE1");	// ����˻��
			ext1 = ds510.getColumnAsInteger(idx510f, "EXT1");	// ����˻� ����
			fee2 = ds510.getColumnAsInteger(idx510f, "FEE2");	// ���а˻��
			ext2 = ds510.getColumnAsInteger(idx510f, "EXT2");	// ���а˻� ����
			num = ds510.getColumnAsInteger(idx510f, "NUM");	// ���а˻� Ƚ��
			ext2 = ds510.getColumnAsInteger(idx510f, "EXT2");	// ���а˻� ����
			rat_512 = ds510.getColumnAsDouble(idx510f, "CS512_RAT");	
  					
			// F1 - ���˳빫��
			// F2 - �빫�� ������
			// F3 - �����ΰǺ�
			// F4 - �������
			// F5 - �˻��
			// F6 - �Ҹ�ǰ/�����
			// F7 - ����� ������
			// F8 - ������
			// F9 - ��������
			// F10 - ǥ�شܰ�
			//pog �� ������,seg�� ���� ����
			//FM �� ���糪 �ΰǺ� ���Ժ���� ���� �����ؼ� ������ ������ alwjrdyd
			//f1 = item1 * rat_512;
			// D/W �߰� ���� ����, ������ ���о���
			double rat_add = 0.8;
			f1 = item1;
			if(zspec12.matches(".*D/W.*")){
				f1 = f1 * rat_add ;
			}
			
			f2 = item2;
			
			if(!"ES".equals(seg_c)){
				if(std3>zspec7){
					f2 = 0;	
				} else if(std3<=zspec7){
					f2 = (((zspec7-std3)*f2*std4)/std3)/60;
				}
			} else {
				f2=0;
			}
			f3 = 0;
			if(!"ES".equals(seg_c)){
				if((std3+1) > zspec7){
					if("A".equals(srg_c)){
						f3 = new Double(((8*4*CS600_RAT)/60)*1.5);
					} else if("B".equals(srg_c)){
						f3 = new Double(((10*5*CS600_RAT)/60)*1.5);
					} else {
						f3 = new Double(((11*6*CS600_RAT)/60)*1.5);
					}									
				} else if((std3+1)<=zspec7){
					if("A".equals(srg_c)){
						f3=new Double((((8 + (zspec7-std3)*0.17)*4*CS600_RAT)/60)*1.5);
					} else if("B".equals(srg_c)){
						f3= new Double((((10 + (zspec7-std3)*0.17)*5*CS600_RAT)/60)*1.5);
					} else {
						f3= new Double((((11 + (zspec7-std3)*0.17)*6*CS600_RAT)/60)*1.5);
					}
				}
			}
			//f4 = f1 + f2 + f3; // �����빫��
			// 2019.04.08 ������ DL ��û. ��������� ��� ���˳빫�� + �빫���������� ���Ͽ� ������� ���ؾ� ��.
			f4 = f1 + f2; // �����빫��
			f4 = f4 * K_RAT; // �������
			
			f5 = 0;
			if(zspec7 <= 6){
				f5 = fee1/12 + (num*fee2)/60;
			} else {	
				f5 = (fee1 +(zspec7-6)*ext1)/12 + (num*fee2 + (zspec7-6)*ext2)/60 ;
			}

			f6 = amt510;
			
			f7 = 0;
			if(std3 <= zspec7) {
				f7 = ((zspec7-std3)*ext*30)/60;
			}
			f8 = f1+f2+f3+f4+f5+f6+f7;
			f8 = new Double((f8)*CS604_US)/100/100;
			f8 = StrictMath.round(f8)*100;
			
			f9 = ((f1+f2+f3+f4+f5+f6+f7+f8)*item9)/100/100;
			f9 = StrictMath.round(f9)*100;

			f10 = f1+f2+f3+f4+f5+f6+f7+f8+f9;	
			f10 = new Double(f10/100);
			f10 = StrictMath.round(f10)*100;	

			ds_hnoinfo.setColumn(i, "F1"   , f1);
			ds_hnoinfo.setColumn(i, "F2"   , f2);
			ds_hnoinfo.setColumn(i, "F3"   , f3);
			ds_hnoinfo.setColumn(i, "F4"   , f4);
			ds_hnoinfo.setColumn(i, "F5"   , f5);
			ds_hnoinfo.setColumn(i, "F6"   , f6);
			ds_hnoinfo.setColumn(i, "F7"   , f7);
			ds_hnoinfo.setColumn(i, "F8"   , f8);
			ds_hnoinfo.setColumn(i, "F9"   , f9);
			ds_hnoinfo.setColumn(i, "F10"   , f10);
			ds_hnoinfo.setColumn(i, "STD3"   , std3);

			/********************************************************/
			/**************** POG ǥ�شܰ� ���  ************************/
			/****************                ************************/
			// ==========2018.06.26==============
			//idx510p = getIDX(seg_c, age, "", ds510, vkgrp);	
			idx510p = getIDX(seg_c, age, "", map, vkgrp); 
			// ==================================
			
			
			//System.out.println(" idx510p   >>"   + idx510p );			
			std3 = ds510.getColumnAsInteger(idx510p, "STD3");	 //SEG �� ������
			std4 = ds510.getColumnAsInteger(idx510p, "STD4");
			item1 = ds510.getColumnAsInteger(idx510p, "ITEM1");	// SEG�� �빫��
			item2 = ds510.getColumnAsInteger(idx510p, "ITEM2");	
			item3 = ds510.getColumnAsInteger(idx510p, "ITEM3");	
			item4 = ds510.getColumnAsInteger(idx510p, "ITEM4");	// ��������
			item5 = ds510.getColumnAsInteger(idx510p, "ITEM5");
			item6 = ds510.getColumnAsInteger(idx510p, "ITEM6");
			item7 = ds510.getColumnAsInteger(idx510p, "ITEM7");	
			item8 = ds510.getColumnAsInteger(idx510p, "ITEM8");	// ��������
			item9 = ds510.getColumnAsInteger(idx510p, "ITEM9");	// �������ͺ���
			amt510 = ds510.getColumnAsInteger(idx510p, "AMT");	// �����
			ext = ds510.getColumnAsInteger(idx510p, "EXT");	// ����� ����
			fee1 = ds510.getColumnAsInteger(idx510p, "FEE1");	// ����˻��
			ext1 = ds510.getColumnAsInteger(idx510p, "EXT1");	// ����˻� ����
			fee2 = ds510.getColumnAsInteger(idx510p, "FEE2");	// ���а˻��
			ext2 = ds510.getColumnAsInteger(idx510p, "EXT2");	// ���а˻� ����
			num = ds510.getColumnAsInteger(idx510p, "NUM");	// ���а˻� Ƚ��
			ext2 = ds510.getColumnAsInteger(idx510p, "EXT2");	// ���а˻� ����
			rat_512 = ds510.getColumnAsDouble(idx510p, "CS512_RAT");	
			
			p1 = item1 * rat_512 ;
			if(zspec12.matches(".*D/W.*")){
				p1 = p1 * rat_add ;
			}
			
			p2 = item2;

			if(!"ES".equals(seg_c)){
				if(std3>zspec7){
					p2 = 0;	
				} else if(std3<=zspec7){
					p2 = (((zspec7-std3)*p2*std4)/std3)/60;
				}
			} else {
				p2=0;
			}

			p3 = 0; // pog �����ΰǺ� ����
			p4 = p1 + p2 + p3;
			p4 = p4 * K_RAT ;
			
			p5 = 0;
				
			p6 = amt510;
			
			p7 = 0;
			if("F".equals(seg_c)){
				if(std3 <= zspec7) {
					p7 = ((zspec7-std3)*ext*30)/60;
				}
			}
			
			p8 = p1+p2+p3+p4+p5+p6+p7;
			p8 = new Double((p8)*CS604_US)/100/100;
			p8 = StrictMath.round(p8)*100;
			
			p9 = ((p1+p2+p3+p4+p5+p6+p7+p8)*item9)/100/100;
			p9 = StrictMath.round(p9)*100;
			
			p10 = p1+p2+p3+p4+p5+p6+p7+p8+p9;	
			p10 = new Double(p10)/100;
			p10 = StrictMath.round(p10)*100;	

			ds_hnoinfo.setColumn(i, "P1"   , p1);
			ds_hnoinfo.setColumn(i, "P2"   , p2);
			ds_hnoinfo.setColumn(i, "P3"   , p3);
			ds_hnoinfo.setColumn(i, "P4"   , p4);
			ds_hnoinfo.setColumn(i, "P5"   , p5);
			ds_hnoinfo.setColumn(i, "P6"   , p6);
			ds_hnoinfo.setColumn(i, "P7"   , p7);
			ds_hnoinfo.setColumn(i, "P8"   , p8);
			ds_hnoinfo.setColumn(i, "P9"   , p9);
			ds_hnoinfo.setColumn(i, "P10"   , p10);
			ds_hnoinfo.setColumn(i, "STD3"   , std3);
			ds_hnoinfo.setColumn(i, "CS512_RAT"   , rat_512);
	
		}	
		return ds_hnoinfo;
	}


	/*********************************************
	 *************** ���⺸�� ���ֽ����� ��ȸ CS1239001A
	 **
	 *********************************************/
	public void getSujuRat(BusinessContext ctx, Dataset ds_list, Dataset ds_cond) throws Exception {
		
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		String pgmID = "";
		pgmID = ds_cond.getColumnAsString(0, "PGMID");
		DatasetSqlRequest dr_gnolist;
		//System.out.println("pgmID    >>>>> " + pgmID);
		
		if(pgmID.length() == 0){
			throw new NullPointerException("pgmID ���� �����ϴ�. ��������η� ���� �Ͻʽÿ�.");
		}

/******************************************************
		pgmID ����
		1.ESTIMATE : ����
		2.ContractRat : ��� ���, ���� �� CS126_PST = 'A5'
		3.���� �Ϸ� ���
*******************************************************/			
		if("ESTIMATE".equals(pgmID)) {
			dr_gnolist = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1241001A_S1");
		} else if("ContractRat".equals(pgmID)) {
			dr_gnolist = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S1S");
		} else {
			dr_gnolist = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S1");
		}

		dr_gnolist.addParamObject("ds_cond", ds_cond);
		dr_gnolist.addParamObject("G_MANDT", mdt);
		
		ds_list = (Dataset)executor.query(dr_gnolist).getResultObject();
		
		if(ds_list.getRowCount() > 0){
	
			calSujuRat(ctx,ds_list,pgmID);
	
		}
		ctx.addOutputDataset(ds_list);
	}

	/*********************************************
	 *************** ���⺸�� ���ֽ����� ����ϱ�
	 * @param pgmID 
	 *********************************************/	
	public Dataset calSujuRat(BusinessContext ctx, Dataset ds_list, String pgmID) throws Exception {
		
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		DatasetSqlRequest dr_hnoinfo ;
		DatasetSqlRequest dr_firstContract ;
		
		//=======================2018.07.03 �߰� ����=======================
		HashMap<String, Integer> keyMap = new HashMap<String, Integer>();
		//=======================2018.07.03 �߰� ����=======================		

/******************************************************
		pgmID ����
		1.ESTIMATE : ����
		2.ContractRat : ��� ���, ���� �� CS126_PST = 'A5'
		3.���� �Ϸ� ���
*******************************************************/	
		if("ESTIMATE".equals(pgmID)) {
			dr_hnoinfo = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1241001A_S8");
		} else if("ContractRat".equals(pgmID)) {
			dr_hnoinfo = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S2S");
		} else {
			dr_hnoinfo = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S2");
		}
		
		dr_firstContract = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S12");
		
		dr_hnoinfo.addParamObject("ds_list", ds_list);
		dr_hnoinfo.addParamObject("G_MANDT", mdt);

		// SEG / ���� �� ǥ�شܰ�ǥ
		// ZCST510 , ZCST511
		DatasetSqlRequest zcst510 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S3");
		zcst510.addParamObject("G_MANDT", mdt);

		zcst510.setRowIndex(0);
		Dataset ds510 = (Dataset)executor.query(zcst510).getResultObject();
		
		// hep ���� �ֱ� �� ����
		DatasetSqlRequest zcst506 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S4");
		zcst506.addParamObject("G_MANDT", mdt);

		zcst506.setRowIndex(0);
		Dataset ds506 = (Dataset)executor.query(zcst506).getResultObject();
		
		// �ӵ��� ����
		DatasetSqlRequest zcst502 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S5");
		zcst502.addParamObject("G_MANDT", mdt);

		zcst502.setRowIndex(0);
		Dataset ds502 = (Dataset)executor.query(zcst502).getResultObject();
		
		// roping�� ����
		DatasetSqlRequest zcst503 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S6");
		zcst503.addParamObject("G_MANDT", mdt);

		zcst503.setRowIndex(0);
		Dataset ds503 = (Dataset)executor.query(zcst503).getResultObject();
		
		// ���� ���� �� ��� ���� ZCST500 ZCST600 ZCST604(��������)
		DatasetSqlRequest zcst500 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S7");
		zcst500.addParamObject("G_MANDT", mdt);

		zcst500.setRowIndex(0);
		Dataset ds500 = (Dataset)executor.query(zcst500).getResultObject();
		
		//  ZCST600(���� ����)
		DatasetSqlRequest zcst600 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S6");
		zcst600.addParamObject("G_MANDT", mdt);

		zcst600.setRowIndex(0);
		Dataset ds600 = (Dataset)executor.query(zcst600).getResultObject();		
		
		//  ZCST601 
		DatasetSqlRequest zcst601 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S8");
		zcst601.addParamObject("G_MANDT", mdt);

		zcst601.setRowIndex(0);
		Dataset ds601 = (Dataset)executor.query(zcst601).getResultObject();
		
		//  ZCST602
		DatasetSqlRequest zcst602 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S9");
		zcst602.addParamObject("G_MANDT", mdt);

		zcst602.setRowIndex(0);
		Dataset ds602 = (Dataset)executor.query(zcst602).getResultObject();
	
		//  ZCST507
		DatasetSqlRequest zcst507 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S10");
		zcst507.addParamObject("G_MANDT", mdt);

		zcst507.setRowIndex(0);
		Dataset ds507 = (Dataset)executor.query(zcst507).getResultObject();

		//===========================����û���� �߰� 20200515 Han.JH=========================================
		//  ZCST608
		DatasetSqlRequest zcst608 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S13");
		zcst608.addParamObject("G_MANDT", mdt);

		zcst608.setRowIndex(0);
		Dataset ds608 = (Dataset)executor.query(zcst608).getResultObject(); 
		//==============================================================================================
		
		//=======================2018.07.03 �߰� ����=======================
		for(int j = 0; j < ds510.getRowCount(); j++) {
			String seg_510 = DatasetUtility.getString(ds510, j, "SEG");  //ds510.getString(j, "SEG");
			String gbu_510 = DatasetUtility.getString(ds510, j, "GBU");  //ds510.getString(j, "GBU");
			int age_510 = DatasetUtility.getInt(ds510, j, "AGE");  //ds510.getInt(j, "AGE");
			String vkg_510 = DatasetUtility.getString(ds510, j, "VKGRP");  //ds510.getString(j, "VKGRP");
			
			String key = seg_510+gbu_510+age_510+vkg_510;
			keyMap.put(key, j);
		}
		//=======================2018.07.03 �߰� ����=======================		
				
		for(int i=0; i<ds_list.getRowCount(); i++) {
					
			dr_hnoinfo.setRowIndex(i);
			Dataset ds_hnoinfo = (Dataset)executor.query(dr_hnoinfo).getResultObject();
			
			double g_wonga = 0;
			double g_wonga2 = 0;
			double g_direct = 0; // ������
			double g_direct2 = 0; // ������
			double g_indirect = 0; // ������
			double g_rat_us_604 = 0; // ��������
			
			double g_nmt = 0;	// �빫��
			double g_jmt = 0;	// �����
			double g_jmt2 = 0;	// �����
			double g_cmt = 0;   // �˻� ������
			double g_hmt = 0;   // hrts
			double g_dmt = 0;   // di-pbx
			double g_acmt = 0;   // ����û����
			double g_sjt = 0;   // ���ֿ���

			double g_kmt = 0;   // ���� ���
			double g_rat = 0;
			double g_rat2 = 0;
			double g_smt = 0;   // ���� ���ݾ�
			double g_ttamt = 0; // ��� �� �ݾ�
			double g_inc = 0; // �μ�Ƽ��
			//double cs608_inc = 0; // ����û���� �μ�Ƽ�� ���رݾ�
			double air_tInc = 0; // ȣ�⺰ ����û���� �μ�Ƽ�� �հ�ݾ�
			
			String firstH = ""; // HRST ���� ���
			String firstD = "";
			String firstAC = "";// ����û���� ���� ���
			String g_seg = "";
			
			
			for(int h=0; h<ds_hnoinfo.getRowCount(); h++) { 
				/************���ʰ�� ���� Ȯ�� START ************/
				dr_firstContract.addParamObject("ds_list", ds_hnoinfo);
				dr_firstContract.addParamObject("G_MANDT", mdt);
				dr_firstContract.setRowIndex(h);				
				Dataset ds_firstContract = (Dataset)executor.query(dr_firstContract).getResultObject();
				firstH = "Y";
				firstD = "Y";

				String gkd    = ds_hnoinfo.getColumnAsString(h, "GKD");
				// ����ȸ�� 4�� ����
				if (gkd.equals("5") ) {
					gkd = "4";	
				}
				// ���� hrts ��� �̷��� �����ϳ�, ���� ȸ���� ��� ���� ��� ���� 
				if(ds_firstContract.getColumnAsString(0, "HYN").equals("Y") && !gkd.equals("4")){
					firstH = "";
				}
				// ���� dipbx ��� �̷��� �����ϳ�, ���� ȸ���� ��� ���� ��� ���� 
				if(ds_firstContract.getColumnAsString(0, "DYN").equals("Y")){
					firstD = "";
				}
				// ���� ����û���� ��� �̷��� �����ϳ�, ���� ȸ���� ��� ���� ��� ���� 
				if(ds_firstContract.getColumnAsString(0, "ACYN").equals("Y")){
					firstAC = "";
				}				
				/************���ʰ�� ���� Ȯ�� END************/
				
				double h_nmt = 0;	// �ѳ빫�� h_nmt1 + h_nmt2 + h_nmt3 + h_emt
				double h_nmt1 = 0; // ���� �빫�� 1( ǥ�� ���� )
				double h_nmt2 = 0; // ���� �빫�� 2( ǥ�� ���� )
				double h_nmt3 = 0; // ���� �빫��3( ǥ�� ���� )	
				double h_emt = 0;	// ���ֺ� ( ���»� �⼺ )
				double h_jmt = 0;	// �����
				double h_jmt2 = 0;	// ����� ( �߰� ���� , ���Կ��� ���� )
				double h_cmt = 0;   // �˻� ������
				double h_hmt = 0;   //
				double h_hmt1 = 0;   //
				double h_hmt2 = 0;   //
				double h_acmt = 0;    // ����û�����
				double h_acmt1 = 0;   // 
				double h_acmt2 = 0;   // 				
				double h_dmt = 0;   //
				double h_dmt1 = 0;   //
				double h_dmt2 = 0;   //
				double h_kmt = 0;   // ���� ��� h_kmt1 + h_kmt2
				double h_kmt1 = 0;   // ���� ���
				double h_kmt2 = 0;   // ���»� ���
				double h_ttamt = 0; // �Ѽ��� ( �ǰ����� )
				
				/*ȣ������*/
				String hno    = ds_hnoinfo.getColumnAsString(h, "HNO").substring(0, 1); // ȣ�� ����
				String vkgrp  = ds_hnoinfo.getColumnAsString(h, "VKGRP");
				String abg    = ds_hnoinfo.getColumnAsString(h, "ABG");
				String bld    = ds_hnoinfo.getColumnAsString(h, "BLD");
				String gyn    = ds_hnoinfo.getColumnAsString(h, "GYN"); // ���ùݿ���
				int    zspec3 = 0;    		// �ӵ�
				int    zspec7 = 0;    		// ��
				String zspec2 = ds_hnoinfo.getColumnAsString(h, "ZSPEC2");;		// ���
				String zspec12 = ds_hnoinfo.getColumnAsString(h, "ZSPEC12");;	// �������	
				int cnt = 0;	   		// �� ���
				int age = 0;			// ��ġ���� (�μ��ϱ���)	
				/*�������*/
				String gbu = ds_hnoinfo.getColumnAsString(h, "GBU"); // ���� ����
				String knd = ds_hnoinfo.getColumnAsString(h, "KND"); // FM POG
				double amt = ds_hnoinfo.getColumnAsDouble(h, "AMT"); // ���     �ܰ�
				double hmt = ds_hnoinfo.getColumnAsDouble(h, "HMT"); // HRTS  �ܰ�
				double acmt = ds_hnoinfo.getColumnAsDouble(h, "ACMT"); // ����û����  �ܰ�
				double dmt = ds_hnoinfo.getColumnAsDouble(h, "DMT"); // DIPBX �ܰ�
				int    ums = ds_hnoinfo.getColumnAsInteger(h, "UMS"); // �ǰ��Ⱓ
				String hep = ds_hnoinfo.getColumnAsString(h, "HEP"); // �����ֱ�
				String hfr = ds_hnoinfo.getColumnAsString(h, "HFR"); // HRTS ���󼭺� ����
				String hwr = ds_hnoinfo.getColumnAsString(h, "HWR"); // ����������
				String dnd = ds_hnoinfo.getColumnAsString(h, "DND"); // ���� TYPE
				String acnd = ds_hnoinfo.getColumnAsString(h, "ACND"); // ����û���� TYPE
	  			
				
				String zyear = ds_hnoinfo.getColumnAsString(h, "USD").substring(0, 4); // ��ళ���� , ���س��
				int chkZYear = Integer.parseInt(zyear);
	  			// ������ ���� ���� ����, 2014�� ������ ����. ������ ��
				if(chkZYear <= 2013) {
					zyear = "2014";
				}
				
				String srg   = "";		// ROPING ���� CODE NAME
				String srg_c = "";		// ROPING ���� CODE	
				String seg = "";		// SEG CODE NAME
				String seg_c = "";		// SEG CODE				
				
				cnt     = ds_hnoinfo.getColumnAsInteger(h, "CNT");
				age     = ds_hnoinfo.getColumnAsInteger(h, "AGEK");			
				
				//double rat_502 = 0;
				double rat_506 = 0;
				double rat_600 = 0;
				double m_amt_500 = 0;
				double j_rat_500 = 0;
				double h_rat_500 = 0;
				double tamt      = 0;
				
				// ���� �� ���� ��� 0���� ����
	  			if("".equals(ds_hnoinfo.getColumnAsString(h, "ZSPEC7"))){
	  				zspec7 = 0;
	  			} else {
	  				zspec7 = ds_hnoinfo.getColumnAsInteger(h, "ZSPEC7");
	  			}
	  			// �ӵ� �� ���� ��� 0���� ����
	  			if("".equals(ds_hnoinfo.getColumnAsString(h, "ZSPEC3"))){
	  				zspec3 = 0;
	  			} else {
	  				zspec3 = ds_hnoinfo.getColumnAsInteger(h, "ZSPEC3"); 
	  			}
	  			
	  			// �����ֱ⺰ ���� ��������
	  			rat_506 = getHEPRAT(hep, zyear, ds506);
	  			// �ӵ��� ���� ��������
	  			//rat_502 = getSPDRAT(hno, zspec3, zyear, ds502);
	  	
	  			// SEG ��������
	  			ArrayList segList = getSEG(zspec3,hno, zspec2, cnt, abg, zspec12);			
	  			seg   = segList.get(0).toString() ;
	  			seg_c = segList.get(1).toString() ;
                
	  			// ROPING ��������
	  			ArrayList srgList = getSRG(zspec12, abg , zspec3);  			
	  			srg   = srgList.get(0).toString() ;
	  			srg_c = srgList.get(1).toString() ;

	  			// ���� ���� �������� 500 600 604
	  			ArrayList wageList = getWAGE(vkgrp, zyear, ds500);	
	  			m_amt_500 = (Double) wageList.get(0) ;
	  			j_rat_500 = (Double) wageList.get(1) ;
	  			h_rat_500 = (Double) wageList.get(2) ;
	  			rat_600   = (Double) wageList.get(3) ;
	  			g_rat_us_604   = (Double) wageList.get(4) ;

	  			// SEG ���� ��౸�� �� ǥ�� �׸� �������� ZCST510 511
	  			// �����빫�� ������ ���� ���� ZCST512		
	  			//System.out.println("knd  >> "+knd); 
//	  			int idx510 = getIDX(seg_c, age, knd, ds510, vkgrp);
	  			
	  			//=======================2018.07.03 �߰� ����=======================
	  			int idx510 = getIDX(seg_c, age, knd, keyMap, vkgrp);
	  			//=======================2018.07.03 �߰� ����=======================	  			

	  			double std1,std2,std3,std4,std5,std6,std7;
	  			double item1,item2,item3,item4,item5,item6,item7,item8,item9;
				double amt510,ext,fee1,ext1,fee2,ext2,num;
				double rat_512 = 1.0;

				std3 = ds510.getColumnAsDouble(idx510, "STD3");	 //SEG �� ������
				std4 = ds510.getColumnAsDouble(idx510, "STD4"); // ���˽ð�
				std5 = ds510.getColumnAsDouble(idx510, "STD5"); // �̵��ð�
				std6 = ds510.getColumnAsDouble(idx510, "STD6"); // ����ó�� �ð�
				

				
				item2 = ds510.getColumnAsInteger(idx510, "ITEM2");	
				item3 = ds510.getColumnAsInteger(idx510, "ITEM3");	
				item4 = ds510.getColumnAsInteger(idx510, "ITEM4");	// ��������
				item5 = ds510.getColumnAsInteger(idx510, "ITEM5");
				item6 = ds510.getColumnAsInteger(idx510, "ITEM6");
				item7 = ds510.getColumnAsInteger(idx510, "ITEM7");	
				item8 = ds510.getColumnAsInteger(idx510, "ITEM8");	// ��������
				item9 = ds510.getColumnAsInteger(idx510, "ITEM9");	// �������ͺ���
				amt510 = ds510.getColumnAsInteger(idx510, "AMT");	// �����
				ext = ds510.getColumnAsInteger(idx510, "EXT");	// ����� ����
				fee1 = ds510.getColumnAsInteger(idx510, "FEE1");	// ����˻��
				ext1 = ds510.getColumnAsInteger(idx510, "EXT1");	// ����˻� ����
				fee2 = ds510.getColumnAsInteger(idx510, "FEE2");	// ���а˻��
				ext2 = ds510.getColumnAsInteger(idx510, "EXT2");	// ���а˻� ����
				num = ds510.getColumnAsInteger(idx510, "NUM");	// ���а˻� Ƚ��
				ext2 = ds510.getColumnAsInteger(idx510, "EXT2");	// ���а˻� ����
				//rat_512 = ds510.getColumnAsDouble(idx510, "CS512_RAT");	
		
				
				// �����ֱ⿡ ���� ���˽ð� ���� //
				if("02".equals(hep)) {
					std4 = std4 * 0.5 ;
				} else if("03".equals(hep)) {
					std4 = std4 * 0.3 ;
				}
				// �� ���˽ð� = ���˽ð� + �̵��ð� + ����ó�� �ð�
				std7 = std4 + std5 + std6 ;
				double std7R = 1.045 ; // �λ���
				double item1a = 0 ;
				
				if(!"".equals(knd)) { 
					// FM �� ��� �λ��� ���� 
					item1a = item2 * (1 + std7R +  Math.pow(std7R,2) +  Math.pow(std7R,3)+  Math.pow(std7R,4)) / 5;
					item1a = item1a * (std7 / 60) / 100 ;
				} else { // pog �� ���
					item1a = item2 * (std7 / 60) / 100 ;
				}
				// ���˳빫�� ����  ������� �ø� 
				item1a = StrictMath.round(item1a)*100;              // �����ֱ⺰ �빫��
				item1 = ds510.getColumnAsInteger(idx510, "ITEM1");	// SEG�� ���� �빫��

				item1 = item1a ;
				
				h_nmt1 = item1 * ums;

				h_nmt1 = h_nmt1 * rat_506;
				h_nmt2 = item2;
				if(!"ES".equals(seg_c)){
					if(std3 > zspec7){
						h_nmt2 = 0;	
					} else if(std3 <= zspec7){
						//h_nmt2 = (((zspec7-std3) * h_nmt2 * std4) / std3) / 60;
						// ǥ�غ����� �빫�� - ���� ���� ����
						// ���⺸�� ������ ���� �� -  ���� ���� ����ó�� 20170801
						h_nmt2 = 0;	
					}
				} else {
					h_nmt2 = 0;
				}
				h_nmt2 = h_nmt2 * ums ;
				
	  			//���κ� ����
	  			ArrayList srcList = getROPING(srg_c, zyear, ds503);
	  			
	  			int   time_503 = (Integer) srcList.get(0) ;
	  			int    man_503 = (Integer) srcList.get(1) ;
	  			double rat_503 = (Double) srcList.get(2) ;				
	  			
				h_nmt3 = 0;
				if(!"ES".equals(seg_c)){
					if((std3+1) > zspec7){
						h_nmt3 = new Double(((time_503 * man_503 * rat_600) / 60) * 1.5);
					} else if(( std3 + 1 )<= zspec7){
						h_nmt3 = new Double((((time_503 + (zspec7-std3) * rat_503) * man_503 * rat_600) / 60) * 1.5);
					}
				}

				h_nmt3 = h_nmt3 * ums ;
				
				// ���� �빫�� ������ /�ӵ��� mh/ ���� / ���� �빫 �ܰ� / �����ֱ⿡ ���� ����					
				int flag_knd = 0 ;
				if(!"".equals(knd)) { 
					flag_knd = 1; 
				} else { // pog �� ��츸 ������,seg�� ���� ����
					h_nmt1 = h_nmt1 * rat_512;
				}				
				// D/W �߰� ���� ����, ������ ���о���
				double rat_add = 0.8;
				if(zspec12.matches(".*D/W.*")){
					h_nmt1 = h_nmt1 * rat_add ;
				}
				
				h_nmt2 = h_nmt2 * flag_knd ;
				h_nmt3 = h_nmt3 * flag_knd ;	
				
				if("3".equals(gbu)) { h_nmt1 = 0; h_nmt2 = 0; h_nmt3 = 0;}
		
				h_nmt = h_nmt1 + h_nmt2+ h_nmt3 ;
				
				// ���ֺ� �⼺
				if(!"1".equals(gbu)) {
					if(!"".equals(knd)){
						if("L".equals(hno)){
							h_emt = amt * 0.53 * ums ;
						} else {
							h_emt = amt * 0.53 * ums ;	
						}				
					} else {
						if("L".equals(hno)){
							if("A".equals(abg)) {
								h_emt = amt * 0.752 * ums ;	
							} else {
								h_emt = amt * 0.652* ums  ;	
							}
						} else {
							h_emt = amt * 0.652 * ums ;	
						}				
					} 
				}

				h_jmt = amt510 * ums ;                                     
				if(!"".equals(knd) || ("".equals(knd) && "F".equals(seg_c))) { 
					if(std3 <= zspec7) {
						h_jmt = h_jmt + (((zspec7 - std3) * ext * 30) / 60 ) * ums ;
					}
				} 
				h_jmt = h_jmt * flag_knd ; // pog ���»��� ��� ���� ������ , ǥ�شܰ����� ���Եǳ� ���������� �� ���� �� �� ���ν� ���� ��û 20160615

				// �߰� ���� or ���Կ��� ���� <<  �����ÿ��� ����
				h_jmt2 = 0 ;
			
				//�˻� ������
				h_cmt = 0 ;
				if(zspec7 <= 6){                                                       	
					h_cmt = fee1 / 12 + (num * fee2) / 60;                                        	
				} else {	                                                            	
					h_cmt = (fee1 +(zspec7 - 6) * ext1) / 12 + (num * fee2 + (zspec7 - 6) * ext2) / 60 ;  	
				}    
		
				h_cmt = h_cmt * ums * flag_knd ;

				/******** HRTS ���� ��� *****************************************************************/
				if(!"".equals(hwr)){
		  			ArrayList hrtsList = getHRTS(hwr, gyn, zyear, ds601);
		  			
					double CS601_JAMT = (Double) hrtsList.get(0);
					double CS601_TAMT = (Double) hrtsList.get(1);			
					
					//hrts L ȣ�� ������ ���/ ��ġ�� + ����� + ��ŷ� + ���Ժ� 18000
					if("L".equals(hno) && "B".equals(hwr)){
						//h_hmt1 = 100000 + CS601_JAMT + ( CS601_TAMT * ums ) + 18000 ;
						h_hmt1 = 37000 + CS601_JAMT + ( CS601_TAMT * ums ) ;

						// ���� ����� �ƴ� ��� ��ŷḸ ���
						if(firstH.equals("")) {
							h_hmt1 = CS601_TAMT * ums ;
						}
					}
					if("L".equals(hno) && "A".equals(hwr) && "Y".equals(gyn) && h==0){
						h_hmt2= CS601_TAMT * ums ;
						// ���� ����� �ƴ� ��� ��ŷḸ ���
						if(firstH.equals("")) {
							h_hmt2 = CS601_TAMT * ums ;
						}
					} else if("L".equals(hno) && "A".equals(hwr) && "N".equals(gyn) && h==0){
						h_hmt2= CS601_TAMT * ums + CS601_JAMT;
						// ���� ����� �ƴ� ��� ��ŷḸ ���
						if(firstH.equals("")) {
							h_hmt2 = CS601_TAMT * ums ;
						}
					}
					// ���󼭺� �� ��� 
					// ��� �������� ����ǹǷ� ȣ�⺰�� ���� ��ġ�� ����
					// ���� 3000 ��ġ�� 20020
					if("Y".equals(hfr)){
						h_hmt1 = 0 ;
						h_hmt2 = 0 ;
						h_hmt1 = 3000 + 20020;
					}	
				}

				/******** ����û���� ���� ��� ���� *****************************************************************/
				if(!"".equals(acnd)){
					ArrayList airPurifierList = getAirPurifier(acnd, zyear, ds608);
					
					double cs608_jamt = (Double) airPurifierList.get(0);  // ����
					double cs608_famt = (Double) airPurifierList.get(1);  // ���ͺ�
					double cs608_samt = (Double) airPurifierList.get(2);  // ��ġ��
					double cs608_inc = (Double) airPurifierList.get(3);   // �μ�Ƽ��
					int fCnt = 0; // ���Ⱓ�� ���� ����
					
					if("A".equals(acnd)){ // �⺻�� ����û����
						fCnt = getFilterCount(ums); // ���Ⱓ�� �ش��ϴ� ���� ������ ���Ѵ�.
						h_acmt1 = cs608_jamt + (cs608_famt * fCnt) + cs608_samt + cs608_inc; // ���� + ���ͺ� + ��ġ�� + �μ�Ƽ��
						h_acmt2 = 0;
						
						air_tInc += cs608_inc; // ȣ�⺰ ����û���� �μ�Ƽ�� �ջ�ݾ��� ���Ѵ�.
					}					
				} else {
					
					h_acmt1 = 0;
					h_acmt2 = 0;
				}
				/******** ����û���� ���� ��� ����************************************************************/
				
				/******** DIPBX ���� ��� *****************************************************************/
				//  ���� ��ŷ� ������ ��� , dnd �ʱ�ȭ, dmt ���� ���ֱݾ� ���� �� ��� �� ���� ó��
				if("Z".equals(dnd)){
					dnd = "";
					dmt = 0 ;
				}
				if(!"".equals(dnd)){
					
		  			ArrayList dipbxList = getDIPBX(dnd, zyear, ds602);
					
					double damt602 = (Double) dipbxList.get(0);
					double lp602   = (Double) dipbxList.get(1);
					double cp602   = (Double) dipbxList.get(2);
					double key602  = (Double) dipbxList.get(3);
					double jamt_o_602 = (Double) dipbxList.get(4);
					double mh602      = (Double) dipbxList.get(5);
					double tamt602    = (Double) dipbxList.get(7);
					
					String gubun507 = ds_hnoinfo.getColumnAsString(h, "CS507_GUBUN");
					
					// ���� ��ȯ��
					double amt507     = getEXCHG(gubun507, zyear, ds507);
					
					String acd_n = ds_hnoinfo.getColumnAsString(h, "ACD_N");
	
					int acd = 0;
					if("EN81".equals(acd_n)){
						acd = 2;
					}
					
					//dipbx
					h_dmt1 = 0;
					if("A".equals(dnd)){
						h_dmt1 = damt602 + lp602 + ( cp602 * ( 1 + acd) ) + ( mh602 * rat_600 ) + ( tamt602 * ums );
						// ���� ����� �ƴ� ��� ��ŷḸ ���
						if(firstD.equals("")){
							h_dmt1 = tamt602 * ums ;
							h_dmt2 = 0;
						}
					} else if("B".equals(dnd)){
						h_dmt1 = 	lp602 + ( cp602 * ( 1 + acd) ) + key602  + ( mh602 * rat_600 ) ;
						// ���� ����� �ƴ� ��� ��ŷḸ ���
						if(firstD.equals("")){
							h_dmt1 = 0;
						}
						if(h==0) {
							h_dmt1 = h_dmt1 + tamt602 * ums ;
							h_dmt2 = 8 * rat_600 + amt507 ;
							// ���� ����� �ƴ� ��� ��ŷḸ ���
							if(firstD.equals("")){
								h_dmt1 = tamt602 * ums ;
								h_dmt2 = 0;
							}
							
						}
					} else if("C".equals(dnd)){
						h_dmt1 =  damt602 + lp602 + ( cp602 * ( 2 + acd) ) + ( mh602 * rat_600 )  + ( tamt602 * ums ) + jamt_o_602;
						// ���� ����� �ƴ� ��� ��ŷḸ ���
						if(firstD.equals("")){
							h_dmt1 = tamt602 * ums ;
							h_dmt2 = 0;
						}
					}

					
				} else {
					
					h_dmt1 = 0;
				}	
				if("1".equals(gbu)){
					h_kmt1 = h_nmt1 * j_rat_500 / 100;
					
				} else {
					h_kmt2 = h_emt * h_rat_500 / 100;
				}		
		
				h_hmt = h_hmt1 +  h_hmt2 ;
				h_dmt = h_dmt1 +  h_dmt2 ;
				h_acmt = h_acmt1 +  h_acmt2 ;
				h_kmt = h_kmt1 + h_kmt2 ;
				h_nmt = h_nmt + h_emt ;
				
				tamt = ( amt + hmt + dmt + acmt ) * ums ;
				h_ttamt = tamt;

				g_nmt       += h_nmt ;          
				g_jmt       += h_jmt ;           
				g_jmt2      += h_jmt2 ;          
				g_kmt       += h_kmt ;          
				g_hmt       += h_hmt ;          
				g_dmt       += h_dmt ;
				g_acmt       += h_acmt ;
				g_cmt       += h_cmt ;              
				g_ttamt     += h_ttamt ;             
				g_seg     = seg ;
				
			}
			
			// �߰�   -----------(ds_lsit �� �д����� ���� ��	)----//	
			String g_bdgbn = "";	
			g_bdgbn = ds_list.getColumnAsString(i, "BDGBN");
			//if("1".equals(g_bdgbn)) {
			if("1".equals(g_bdgbn) && !"ESTIMATE".equals(pgmID) && !"ContractRat".equals(pgmID) ) {
				g_nmt = 0;
				g_kmt = 0;
			} 
			//===============================================//			
			
			

			
			ds_list.setColumn(i, "G_NMT"   , g_nmt);
			ds_list.setColumn(i, "G_JMT"   , g_jmt);
			ds_list.setColumn(i, "G_JMT2"   , g_jmt2);
			ds_list.setColumn(i, "G_KMT"   , g_kmt);
			ds_list.setColumn(i, "G_HMT"   , g_hmt);
			ds_list.setColumn(i, "G_DMT"   , g_dmt);
			ds_list.setColumn(i, "G_ACMT"   , g_acmt);
			ds_list.setColumn(i, "G_CMT"   , g_cmt);
			ds_list.setColumn(i, "G_GMT"   , g_indirect);
			ds_list.setColumn(i, "G_GMT2"   , g_indirect);
			ds_list.setColumn(i, "G_COS"   , g_wonga);
			ds_list.setColumn(i, "G_COS2"  , g_wonga2);
			ds_list.setColumn(i, "G_RAT"   , g_rat);
			ds_list.setColumn(i, "G_RAT2"   , g_rat2);
			ds_list.setColumn(i, "G_TTAMT" , g_ttamt);
			ds_list.setColumn(i, "G_TTAMT_S" , g_smt);
			ds_list.setColumn(i, "G_INC"   , g_inc);
			ds_list.setColumn(i, "G_PER"   , g_inc);
			
			// 2. �ο� ���� 		
			calSangjuRat(ctx,ds_list,i,ds500,pgmID);
			g_sjt = ds_list.getColumnAsDouble(i, "G_SJT");
			g_smt = ds_list.getColumnAsDouble(i, "G_TTAMT_S");
			
			// 3. �߰� ���� 
			if("ESTIMATE".equals(pgmID)) {
				calAddMatnr(ctx,ds_list,i);
				g_jmt2 = ds_list.getColumnAsDouble(i, "G_JMT2");
			} else {
				g_jmt2 = g_jmt;	
			}
			
			// 4.���� ���		
			// ������ = �빫�� + ���� + ���  + �˻�� + hrts + dipbx + ���ֺ� + ����û����
			g_direct = g_nmt + g_jmt  + g_kmt + g_cmt + g_hmt + g_dmt + g_sjt + g_acmt ;
			g_direct2 = g_nmt + g_jmt2  + g_kmt + g_cmt + g_hmt + g_dmt + g_sjt + g_acmt ;
			g_indirect = g_direct  * g_rat_us_604 / 100 ;
			g_wonga = g_direct + g_indirect ;
			g_wonga2 = g_direct2 ;
			g_rat = g_wonga / (g_ttamt+g_smt) * 100 ;
			
			ds_list.setColumn(i, "G_NMT"   , g_nmt);
			ds_list.setColumn(i, "G_JMT"   , g_jmt);
			ds_list.setColumn(i, "G_JMT2"  , g_jmt2);
			ds_list.setColumn(i, "G_KMT"   , g_kmt);
			ds_list.setColumn(i, "G_HMT"   , g_hmt);
			ds_list.setColumn(i, "G_DMT"   , g_dmt);
			ds_list.setColumn(i, "G_CMT"   , g_cmt);
			ds_list.setColumn(i, "G_GMT"   , g_indirect);
			ds_list.setColumn(i, "G_SJT"   , g_sjt);
			ds_list.setColumn(i, "G_COS"   , g_wonga);
			ds_list.setColumn(i, "G_COS2"  , g_wonga2);
			ds_list.setColumn(i, "G_RAT"   , g_rat);
			ds_list.setColumn(i, "G_TTAMT" , g_ttamt);
			ds_list.setColumn(i, "G_TTAMT_S" , g_smt);
			ds_list.setColumn(i, "G_INC"   , g_inc);
			ds_list.setColumn(i, "SEG"   , g_seg);
			ds_list.setColumn(i, "G_ACMT"   , g_acmt);

			// 5.FM �μ�Ƽ��
			calFmIncentive(ctx,ds_list,i,pgmID,g_rat_us_604);
			
			// ����û���� �μ�Ƽ��
//			calAirFurifierInc(ctx,ds_list,i,pgmID,cs608_inc,ds510,ds600,ds_hnoinfo);
			
			// ����û���� �μ�Ƽ��
			calAirFurifierInc(ctx,ds_list,i,pgmID,air_tInc);
		}
		return ds_list;
	
	}
	
	public int getFilterCount(int ums) {
		int cnt = 0; // ���Ⱓ�� ���� ����
		
		if(ums > 0) {
			if(ums > 0 && ums < 7) { // ���Ⱓ�� 6���� �̳��̸� ���ͱ�ü ����
				cnt = 0;
			} else if(ums > 6 && ums < 13) { // ���Ⱓ�� 6���� �ʰ� 12���� �̳��̸� ���ͱ�ü 1ȸ
				cnt = 1;
			} else if(ums > 12 && ums < 19) { // ���Ⱓ�� 12���� �ʰ� 18���� �̳��̸� ���ͱ�ü 2ȸ
				cnt = 2;
			} else if(ums > 18 && ums < 25) { // ���Ⱓ�� 18���� �ʰ� 24���� �̳��̸� ���ͱ�ü 3ȸ
				cnt = 3;
			} else if(ums > 24 && ums < 31) { // ���Ⱓ�� 24���� �ʰ� 30���� �̳��̸� ���ͱ�ü 4ȸ
				cnt = 4;
			} else if(ums > 30 && ums < 37) { // ���Ⱓ�� 30���� �ʰ� 36���� �̳��̸� ���ͱ�ü 5ȸ
				cnt = 5;
			} else if(ums > 36 && ums < 43) { // ���Ⱓ�� 36���� �ʰ� 42���� �̳��̸� ���ͱ�ü 6ȸ
				cnt = 6;
			} else if(ums > 42 && ums < 49) { // ���Ⱓ�� 42���� �ʰ� 48���� �̳��̸� ���ͱ�ü 7ȸ
				cnt = 7;
			} else if(ums > 48 && ums < 55) { // ���Ⱓ�� 48���� �ʰ� 54���� �̳��̸� ���ͱ�ü 8ȸ
				cnt = 8;
			} else if(ums > 54 && ums < 61) { // ���Ⱓ�� 54���� �ʰ� 60���� �̳��̸� ���ͱ�ü 9ȸ
				cnt = 9;
			}
		}
		
		return cnt;
	}

	public Dataset calAirFurifierInc(BusinessContext ctx, Dataset ds_list, int i, String pgmID, double air_tInc) {
		/******************************************************
		pgmID ����
		1.ESTIMATE : ����
		2.ContractRat : ��� ���, ���� �� CS126_PST = 'A5'
		3.���� �Ϸ� ���
		*******************************************************/
		String acyn = "";
		if("ESTIMATE".equals(pgmID)) {
			acyn = ds_list.getColumnAsString(i, "ACYN");
		} else if("ContractRat".equals(pgmID)) {
			acyn = ds_list.getColumnAsString(i, "CS126_ACYN");
		} else {
			acyn = ds_list.getColumnAsString(i, "CS126_ACYN");
		}
		
		if(!"".equals(acyn)) {
			ds_list.setColumn(i, "G_INC"   , ds_list.getColumnAsDouble(i, "G_INC") + air_tInc);
		}
		
		return ds_list;
	}

	public Dataset calAirFurifierInc2(BusinessContext ctx, Dataset ds_list, int i, String pgmID, double cs608_inc, Dataset ds510, Dataset ds600, Dataset ds_hnoinfo) throws Exception {
		
		double stndrdTotFee = 0; // ǥ�غ����� �հ�
		double curTotAmt = 0; // ����ܰ� �հ�
		double preTotAmt = 0; // �����ܰ� �հ�
		
		double airIncentive = 0; // ����û���� �μ�Ƽ��
		
		String gkd = ds_list.getColumnAsString(i, "GKD");// �������
		
		// ȣ�⺰ �����ܰ�,����ܰ�,ǥ�غ����� �ջ� �ݾ��� ���Ѵ�
		for(int h=0; h<ds_hnoinfo.getRowCount(); h++) {
			// ǥ�غ����� ���Ѵ�.
			getPrice(ds_hnoinfo,ds510,ds600,pgmID);
			
			String knd = ds_hnoinfo.getColumnAsString(h, "KND"); // FM POG
			
			if(!"".equals(knd)) {
				// FM���
				stndrdTotFee += ds_hnoinfo.getColumnAsDouble(h, "F10"); // FM ǥ�غ�����
			} else {
				// POG���
				stndrdTotFee += ds_hnoinfo.getColumnAsDouble(h, "P10"); // POG ǥ�غ�����
			}
			
			preTotAmt += ds_hnoinfo.getColumnAsDouble(h, "PRE_AMT"); // �����ܰ�
			curTotAmt += ds_hnoinfo.getColumnAsDouble(h, "AMT_TOT"); // ����ܰ�
		}
		
		// ����û���� �μ�Ƽ���� ���
		// ��������� ��ȯ����̸鼭, ���غ����� �̻� �ϋ� �μ�Ƽ�� ����
		if(gkd.equals("1")) {
			// ����ܰ� �ѱݾ��� ǥ�غ����Ḧ �����ϸ� �μ�Ƽ�� ����
			if(curTotAmt >= stndrdTotFee) {
				airIncentive = cs608_inc;
				ds_list.setColumn(i, "G_INC"   , ds_list.getColumnAsDouble(i, "G_INC") + airIncentive);
			}
			
		} else if(gkd.equals("2")) {
			// ��������� ���Ű���̸鼭
			// ���������� ���� �̻��� �ݾ� �� �� �μ�Ƽ�� ����			
			// �����ܰ��� 0 �̻��̰� �׸��� ����ܰ��� �����ܰ� �̻��϶� �μ�Ƽ�� ����
			if(preTotAmt > 0 && preTotAmt < curTotAmt) {
				airIncentive = cs608_inc;
				ds_list.setColumn(i, "G_INC"   , ds_list.getColumnAsDouble(i, "G_INC") + airIncentive);
			}
		}
		
		return ds_list;
	}

	public Dataset calFmIncentive(BusinessContext ctx, Dataset ds_list, int i, String pgmID,double p_rat_us ) {
		double t_rat = ds_list.getColumnAsDouble(i, "G_RAT");
		double t_rat2 = 0.0;
		double t_inc = 0.0;
		double t_rat_us = p_rat_us;
		double t_ttamt   = ds_list.getColumnAsDouble(i, "G_TTAMT");
		double t_ttamt_s = ds_list.getColumnAsDouble(i, "G_TTAMT_S");
		int    t_ums = 0;
  
		String b_rat_n = "";
		double b_rat = 0.0;
		if(t_rat <= 50.0) { b_rat_n = "0.0"; b_rat = 1.5;}
		else if(t_rat <= 60.0 ) { b_rat_n = "50.1"; b_rat = 1.3;}
		else if(t_rat <= 70.0) { b_rat_n = "60.1"; b_rat = 1.2;}
		else if(t_rat <= 75.0) { b_rat_n = "70.1"; b_rat = 1.1;}
		else if(t_rat <= 80.0) { b_rat_n = "75.1"; b_rat = 1.0;}
		else if(t_rat <= 85.5) { b_rat_n = "80.1"; b_rat = 0.9;}
		else {b_rat_n = "85.1" ;b_rat = 0.8;}
		
		double t_incentive = 0;
		String fmflag = "";
		double t_amt = 0 ;
		if("ESTIMATE".equals(pgmID)) {
			fmflag = ds_list.getColumnAsString(i, "CPD");
			t_amt = ds_list.getColumnAsDouble(i, "FMT");
			t_ums      = ds_list.getColumnAsInteger(i, "UMS");
		} else {
			fmflag = ds_list.getColumnAsString(i, "CS126_KND");
			t_amt = ds_list.getColumnAsDouble(i, "CS126_AMT") ; 
			t_ums      = ds_list.getColumnAsInteger(i, "UMS_T");
		}
		
		if("3".equals(ds_list.getColumnAsString(i, "GBU")) && "FM".equals(fmflag)) {
			t_incentive = ((t_amt * b_rat) / 60) * t_ums ;
			t_inc       = b_rat ;
		} else {
			t_incentive = 0 ;
			t_inc       = 0 ;
		}

		double t_wanga2 = ds_list.getColumnAsDouble(i, "G_COS2");
		double t_gmt2 = 0.0;

		t_gmt2 = ( t_wanga2 + t_incentive ) * t_rat_us / 100 ;
		t_wanga2 = t_gmt2 + t_wanga2 + t_incentive  ;
		t_rat2 = (t_wanga2 / (t_ttamt+t_ttamt_s)) * 100 ;
			
		ds_list.setColumn(i, "G_INC"   , t_incentive);
		ds_list.setColumn(i, "G_INCENTIVE"   , t_inc);
		ds_list.setColumn(i, "G_COS2"   , t_wanga2);
		ds_list.setColumn(i, "G_GMT2"   , t_gmt2);
		ds_list.setColumn(i, "G_RAT2"   , t_rat2);
		ds_list.setColumn(i, "G_PER"   , t_rat - t_rat2);
		
		return ds_list;
	}

	public Dataset calAddMatnr(BusinessContext ctx, Dataset ds_list, int i) throws Exception {

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
				
		DatasetSqlRequest dr_matnrList = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1241001A_S10");
	
		dr_matnrList.addParamObject("ds_list", ds_list);
		dr_matnrList.addParamObject("G_MANDT", mdt);

		Dataset ds_matnrList = (Dataset)executor.query(dr_matnrList).getResultObject();
		
		String amg = "N";
		double amg_to2 = 0;
		double t_jmt1 = 0;
		double t_jmt2 = 0;
		t_jmt1 = ds_list.getColumnAsDouble(i, "G_JMT");
		t_jmt2 = ds_list.getColumnAsDouble(i, "G_JMT2");
		
		if(ds_matnrList.getRowCount() > 0){

			amg = ds_matnrList.getColumnAsString(0, "AMG");
			amg_to2 = ds_matnrList.getColumnAsDouble(0, "AMG_TO2");
			
			if("Y".equals(amg)) { // �߰�����
				t_jmt2 = amg_to2 + t_jmt1;
				
			} else { //  ���Կ��� ����
				t_jmt2 = amg_to2;
			}
		} else {
			t_jmt2 = t_jmt1;
		}
		
		ds_list.setColumn(i, "G_JMT2"   , t_jmt2);
		return ds_list;
	}


	/*********************************************
	 *************** �ο����� ���ֽ����� ����ϱ�
	 * @param ds500 
	 * @param pgmID 
	 * @param i 
	 *********************************************/	
	public Dataset calSangjuRat(BusinessContext ctx, Dataset ds_list, int i, Dataset ds500, String pgmID) throws Exception {
		
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		
		DatasetSqlRequest dr_sangjuList ;
		if("ESTIMATE".equals(pgmID)) {
			dr_sangjuList = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1241001A_S9");
			dr_sangjuList.addParamObject("ds_list", ds_list);
			//dr_sangjuList.addParamObject("TEM", ds_list.getColumnAsString(i, "TEM"));
		} else if("ContractRat".equals(pgmID)) {
			dr_sangjuList = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S11S");
			dr_sangjuList.addParamObject("ds_list", ds_list);
		} else {
			dr_sangjuList = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S11");
			dr_sangjuList.addParamObject("CS126_GNO", ds_list.getColumnAsString(i, "CS126_GNO"));
		}
			
		dr_sangjuList.addParamObject("G_MANDT", mdt);

		Dataset ds_sangjuList = (Dataset)executor.query(dr_sangjuList).getResultObject();

		String vkgrp;		
		String zyear; // ��ళ���� , ���س��		 
			
		double tot  = 0;  // ���ް���
		String tgb ;
		String tmp ;
		double hur = 0 ;
		double sjt = 0 ; // ���� ����
		double sjt_t = 0 ; // ��ü�� ���� ����
		double smt = 0 ;
		double smt_t = 0 ;
		double rto = 0 ;
		int ums = 0;

		double m_amt_500 = 0   ;
		double rat_us_604 = 0    ;
		double tmp_amt = 2700000;
		int wday = 20;
		if(ds_sangjuList.getRowCount() > 0) {
			for(int s=0; s<ds_sangjuList.getRowCount(); s++) {	                                                                                                 
				vkgrp  = ds_sangjuList.getColumnAsString(s, "VKGRP");	
				zyear = ds_sangjuList.getColumnAsString(s, "SFR").substring(0, 4); // ��ళ���� , ���س��		 
				ums = ds_sangjuList.getColumnAsInteger(s, "UMS");

				tgb = ds_sangjuList.getColumnAsString(s, "TGB");
				tmp = ds_sangjuList.getColumnAsString(s, "TMP");
				hur = ds_sangjuList.getColumnAsDouble(s, "HUR") ; 

				tot = ds_sangjuList.getColumnAsDouble(s, "TOT") ; 

				rto = ds_sangjuList.getColumnAsDouble(s, "RTO") ; 
				smt_t = tot * ums ; 
				
				ArrayList wageList = getWAGE(vkgrp, zyear, ds500);	
				 m_amt_500 = (Double) wageList.get(0) ;
				 rat_us_604   = (Double) wageList.get(4) ;

				if("1".equals(tgb)) {
					if("N".equals(tmp)){
						sjt_t = hur * m_amt_500 * wday;
						sjt_t = sjt_t * ums;
					} else {
						sjt_t = tmp_amt;
						sjt_t = sjt_t * ums;
					}
				
				} else if("3".equals(tgb)) {
					sjt_t = ( tot * rto ) / 100 ;
					sjt_t = sjt_t * ums;
				
				} else {
					sjt_t = 0;
				}
				sjt += sjt_t ;
				smt += smt_t ;	
			}
		}

		ds_list.setColumn(i, "G_SJT", sjt);
		ds_list.setColumn(i, "G_TTAMT_S", smt);
			
		return ds_list;
	
	}
	
	public void getListSeg(BusinessContext ctx, Dataset ds_list) throws Exception {

		for(int i=0; i<ds_list.getRowCount(); i++) {	
		}
		getSTO(ds_list);
		ctx.addOutputDataset(ds_list);
	}	
	public void queryCS1209(BusinessContext ctx, Dataset ds_cond)throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		String gbn = "";
		String detail = "";
		String sql = "";
		
		gbn    = ds_cond.getColumnAsString(0, "GBN");
		detail = ds_cond.getColumnAsString(0, "DETAIL");
			
		if(gbn.equals("02")) { // �̹���
			if(detail.equals("J")) {
				sql = "cs12:CS1209001A_S2";
			} else if(detail.equals("B")) {
				sql = "cs12:CS1209001A_S3";
			} else if(detail.equals("F")) {
				sql = "cs12:CS1209001A_S4";
			} else {
				sql = "cs12:CS1209001A_S5";
			}		
		} else if(gbn.equals("03")) { // ����
			if(detail.equals("D")) {
				sql = "cs12:CS1209001A_S6";
			} else if(detail.equals("C")) {
				sql = "cs12:CS1209001A_S7";
			} else {
				sql = "cs12:CS1209001A_S8";
			}	
		} else if(gbn.equals("04")) { // ����
			if(detail.equals("H")) {
				sql = "cs12:CS1209001A_S9";
			} else if(detail.equals("G")) {
				sql = "cs12:CS1209001A_S10";
			} else {
				sql = "cs12:CS1209001A_S11";
			}
		} else {
			sql = "cs12:CS1209001A_S12";
		}
		
		DatasetSqlRequest dr_list = SqlMapManagerUtility.makeSqlRequestForDataset(sql);
		dr_list.addParamObject("G_MANDT", mdt);
		dr_list.addParamObject("ds_cond", ds_cond);
		
		dr_list.setRowIndex(0);
		Dataset ds_list = (Dataset)executor.query(dr_list).getResultObject();
		
		getSTO(ds_list);
		ctx.addOutputDataset(ds_list);
	}
	public void queryCS1238(BusinessContext ctx, Dataset ds_cond, Dataset ds_list, Dataset ds_list2, Dataset ds_list3) throws Exception {

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		String gubun1 = ds_cond.getColumnAsString(0, "GBN1");
		String sql_h = "" ;
		String sql_d = "" ;
		String sql_3 = "" ;

		if(gubun1.equals("A") || gubun1.equals("C") ||gubun1.equals("D") ) { //��ȯ,ȸ��,����
			sql_h = "cs12:CS1238001A_S1";
			sql_d = "cs12:CS1238001A_S2";		
		} else if(gubun1.equals("B") || gubun1.equals("H")|| gubun1.equals("I") ) { //����
			sql_h ="cs12:CS1238001A_S3";
			sql_d ="cs12:CS1238001A_S4";
		} else if(gubun1.equals("E")) { //�μ�
			sql_h = "cs12:CS1238001A_S5";
			sql_d = "cs12:CS1238001A_S6";	
			sql_3 = "cs12:CS1238001A_S13";
		} else if(gubun1.equals("F")) { //���󸸷�
			sql_h = "cs12:CS1238001A_S7";
			sql_d = "cs12:CS1238001A_S8";		
		} else if(gubun1.equals("G")) { //���󸸷�
			sql_h = "cs12:CS1238001A_S9";
			sql_d = "cs12:CS1238001A_S10";	
		} else if(gubun1.equals("J")) { //������ü
			sql_h = "cs12:CS1238001A_S11";
			sql_d = "cs12:CS1238001A_S12";
		}
		
		if(sql_h.length()==0 || sql_d.length()== 0 ) {
			throw new BizException(" ó�� ���� ");	
		}
		
		DatasetSqlRequest dr_sum = SqlMapManagerUtility.makeSqlRequestForDataset(sql_h);
		dr_sum.addParamObject("G_MANDT", mdt);
		dr_sum.addParamObject("ds_cond", ds_cond);

		DatasetSqlRequest dr_list = SqlMapManagerUtility.makeSqlRequestForDataset(sql_d);
		dr_list.addParamObject("G_MANDT", mdt);
		dr_list.addParamObject("ds_cond", ds_cond);
		
		if(sql_3.length()> 0  ) {
			DatasetSqlRequest dr_list3 = SqlMapManagerUtility.makeSqlRequestForDataset(sql_3);
			dr_list3.addParamObject("G_MANDT", mdt);
			dr_list3.addParamObject("ds_cond", ds_cond);
			
			ds_list3 = (Dataset) executor.query(dr_list3).getResultObject();
		}

		ds_list = (Dataset)executor.query(dr_sum).getResultObject();
		ds_list2 = (Dataset) executor.query(dr_list).getResultObject();
		
		getSTO(ds_list2);
		ctx.addOutputDataset(ds_list);
		ctx.addOutputDataset(ds_list2);
		ctx.addOutputDataset(ds_list3);
	}

	public void queryCS4101(BusinessContext ctx, Dataset ds_cond)throws Exception {
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		String sql = "cs41:CS4101001B_S10";
		
		DatasetSqlRequest dr_list = SqlMapManagerUtility.makeSqlRequestForDataset(sql);
		dr_list.addParamObject("G_MANDT", mdt);
		dr_list.addParamObject("ds_cond", ds_cond);
		
		dr_list.setRowIndex(0);
		Dataset ds_list = (Dataset)executor.query(dr_list).getResultObject();
		
		getSTO(ds_list);
		ctx.addOutputDataset(ds_list);
	}	
	private double getEXCHG(String gubun507, String zyear, Dataset ds507) {
		String t_gubun = "";	
		String t_zyear = "";	
		Double t_amt = 0.0;
		
		for(int j=0; j <ds507.getRowCount(); j++) {
			t_gubun   = ds507.getColumnAsString(j, "CS602_TYP");
			t_zyear = ds507.getColumnAsString(j, "CS602_YEAR");
			if( gubun507.equals(t_gubun) && zyear.equals(t_zyear) ) {
				t_amt = ds507.getColumnAsDouble(j, "AMT");
			}
		}
		return t_amt;
	}


	private ArrayList getDIPBX(String dnd, String zyear, Dataset ds602) {
		ArrayList t_dipbxList = new ArrayList();
		String t_dnd = "";	
		String t_zyear = "";	

		for(int j=0; j <ds602.getRowCount(); j++) {
			t_dnd   = ds602.getColumnAsString(j, "CS602_TYP");
			t_zyear = ds602.getColumnAsString(j, "CS602_YEAR");
			if( dnd.equals(t_dnd) && zyear.equals(t_zyear) ) {
				t_dipbxList.add(ds602.getColumnAsDouble(j, "CS602_DAMT"));
				t_dipbxList.add(ds602.getColumnAsDouble(j, "CS602_LP"));
				t_dipbxList.add(ds602.getColumnAsDouble(j, "CS602_CP"));
				t_dipbxList.add(ds602.getColumnAsDouble(j, "CS602_KEY"));
				t_dipbxList.add(ds602.getColumnAsDouble(j, "CS602_JAMT_O"));
				t_dipbxList.add(ds602.getColumnAsDouble(j, "CS602_MH"));
				t_dipbxList.add(ds602.getColumnAsDouble(j, "CS602_JAMT"));
				t_dipbxList.add(ds602.getColumnAsDouble(j, "CS602_TAMT"));
			}
		}
		
		return t_dipbxList;
	}


	private ArrayList getHRTS(String hwr, String gyn, String zyear,	Dataset ds601) {
		ArrayList t_hrtsList = new ArrayList();
		String t_hwr = "";	
		String t_gyn = "";	
		String t_zyear = "";	
		
		for(int j=0; j <ds601.getRowCount(); j++) {
			t_hwr = ds601.getColumnAsString(j, "CS601_TYP");
			t_gyn = ds601.getColumnAsString(j, "CS601_CRT");
			t_zyear   = ds601.getColumnAsString(j, "CS601_YEAR");
			if( hwr.equals(t_hwr) && gyn.equals(t_gyn) && zyear.equals(t_zyear) ) {
				t_hrtsList.add(ds601.getColumnAsDouble(j, "CS601_JAMT"));
				t_hrtsList.add(ds601.getColumnAsDouble(j, "CS601_TAMT"));
			}
		}	
		return t_hrtsList;
	}


	private ArrayList getWAGE(String vkgrp, String zyear, Dataset ds500) {
		ArrayList t_wageList = new ArrayList();
		String t_vkgrp = "";	
		String t_zyear = "";	
		
		for(int j=0; j <ds500.getRowCount(); j++) {
			t_vkgrp = ds500.getColumnAsString(j, "VKGRP");
			t_zyear   = ds500.getColumnAsString(j, "ZYEAR");
			if( vkgrp.equals(t_vkgrp) && zyear.equals(t_zyear) ) {
				t_wageList.add(ds500.getColumnAsDouble(j, "M_AMT"));
				t_wageList.add(ds500.getColumnAsDouble(j, "J_RAT"));
				t_wageList.add(ds500.getColumnAsDouble(j, "H_RAT"));
				t_wageList.add(ds500.getColumnAsDouble(j, "CS600_RAT"));
				t_wageList.add(ds500.getColumnAsDouble(j, "CS604_US"));
			}
		}	
		return t_wageList;
	}

	private ArrayList getROPING(String srg_c, String zyear, Dataset ds503) {
		ArrayList t_srgList = new ArrayList();
		String srg_503 = "";	
		String yea_503 = "";	
		
		for(int j=0; j <ds503.getRowCount(); j++) {
			srg_503 = ds503.getColumnAsString(j, "GUBUN");
			yea_503   = ds503.getColumnAsString(j, "ZYEAR");
			if( srg_c.equals(srg_503) && zyear.equals(yea_503) ) {
				t_srgList.add(ds503.getColumnAsInteger(j, "TIME"));
				t_srgList.add(ds503.getColumnAsInteger(j, "MAN"));
				t_srgList.add(ds503.getColumnAsDouble(j, "RAT"));
			}
		}	
		return t_srgList;
	}

	private double getSPDRAT(String hno, int zspec3, String zyear, Dataset ds502) {
		double t_rat502 = 0 ;
		String t_spd = "A";
		
		if("L".equals(hno)){
			t_spd = "60" ; 
		} else {
			if(zspec3 < 90) { t_spd = "60";}
			else if(zspec3 < 120) { t_spd = "90";}
			else if(zspec3 < 180) { t_spd = "120";}
			else { t_spd = "180"; }	
		}
		
		String spd_502 = "";	
		String yea_502 = "";	
	
		for(int j=0; j <ds502.getRowCount(); j++) {
			spd_502 = ds502.getColumnAsString(j, "GUBUN");
			yea_502   = ds502.getColumnAsString(j, "ZYEAR");
			if( t_spd.equals(spd_502) && zyear.equals(yea_502) ) {
				t_rat502 = ds502.getColumnAsDouble(j, "RAT");
			}
		}

		return t_rat502;
	}

	private double getHEPRAT(String hep, String zyear,Dataset ds506) {
		double t_rat506 = 0 ;
		String t_hep = "A";
		if("02".equals(hep)) {
			t_hep = "B" ;
		} else if("03".equals(hep)) {
				t_hep = "C" ;
		} 	

		String hep_506 = "";	
		String yea_506 = "";	
	
		for(int j=0; j <ds506.getRowCount(); j++) {
			hep_506 = ds506.getColumnAsString(j, "GUBUN");
			yea_506   = ds506.getColumnAsString(j, "ZYEAR");
			if( t_hep.equals(hep_506) && zyear.equals(yea_506) ) {
				t_rat506 = ds506.getColumnAsDouble(j, "RAT");
			}
		}		
		
		return t_rat506;
	}
	/********************************
	 ********************** SEG ���ϱ�
	 **�ӵ�       ZSPEC3 
	 **ȣ���ȣ HNO
	 **���        ZSPEC2 
	 **���� �Ѵ�� CNT (ZMASTER02 ���� ȣ�� ����) 
	 **��������     ABG
	*********************************/
	private ArrayList<String> getSEG(int zspec3, String hno, String zspec2, int cnt, String abg, String zspec12 ){
		ArrayList segList = new ArrayList();
		String seg = "";
		String seg_c = "";
		String spd = "";
		String abg_c = "";
		String abg_k = "";

		/*SEG ���� : ��������*/
		if("A".equals(abg)) {
			abg_c = "A";
			abg_k = "����Ʈ";		
		} else {
			abg_c = "O";
			abg_k = "���ǽ�";	
		}
		
		if(zspec3 < 120) { spd = "A"; }
		else if(zspec3 < 150) { spd = "B"; }
		else if(zspec3 < 180) { spd = "C"; }
		else if(zspec3 < 300) { spd = "D"; }
		else if(zspec3 < 480) { spd = "E"; }
		else if(zspec3 < 600) { spd = "F"; }
		else {spd = "G";}
		/*SEG ���� START : ES/F(ȭ����)/����Ʈ/���ǽ�*/			
		if(!hno.equals("L")){
			seg = "ES"; seg_c = "ES";
		} else {
			if(zspec2.matches(".*F.*")){
				seg = "F"; seg_c = "F";
				if(zspec12.matches(".*D/W.*")){
					seg = "STO"; seg_c = "SA";
				}
			} else {
				if(cnt < 3 && zspec3 <= 120){
					seg = "STO"; seg_c = "SA";
				} else {
					seg = abg_k + spd; seg_c = abg_c + spd;
				}	
			}
		}
		if(seg_c.equals("AG")){
			seg = "����ƮF"; seg_c = "AF";
		}
		segList.add(seg) ;
		segList.add(seg_c) ;
		
		return segList ;
	}
	
	private int getIDX(String seg_c, int age, String knd, Dataset ds510, String vkgrp) {
		int idx = 0 ;

		// ���� ���� 
		if(age > 20) { 
			age = 20;
		} else if(age < 1) { 
			age = 1;
		}
		// ��౸�а�  ����
		// ���⺸�� ��� ���� ���� pog ��  ''
		if (knd.length()== 0) { 
			knd = "B" ; 
		} else {
			knd = "A" ; 
		}
		//SEG/���� �� ǥ�شܰ�ǥ
		String seg_510 = "";	// SEG ����
		String gbu_510 = "";	// FM(A)/POG(B) ����	
		int    age_510 = 0;		// ���� ���� (1~20)
		String vkg_510 = "";	// 
		
		for(int j=0; j <ds510.getRowCount(); j++) {
			seg_510 = ds510.getColumnAsString(j, "SEG");
			gbu_510 = ds510.getColumnAsString(j, "GBU");
			age_510 = ds510.getColumnAsInteger(j, "AGE");
			vkg_510 = ds510.getColumnAsString(j, "VKGRP");

			if( seg_c.equals(seg_510)  ) {
				// FM ǥ�شܰ� ����
				if( gbu_510.equals(knd) && age_510==age && vkg_510.equals(vkgrp)){
					idx = j ; break;
				}
			}
		}	
		
		return idx ;
	}

	// ================2018.06.26 �߰� =======================
	private int getIDX(String seg_c, int age, String knd, HashMap<String, Integer> ds510, String vkgrp) {
		int idx = 0 ;

		// ���� ���� 
		if(age > 20) { 
			age = 20;
		} else if(age < 1) { 
			age = 1;
		}
		// ��౸�а�  ����
		// ���⺸�� ��� ���� ���� pog ��  ''
		if (knd.length()== 0) { 
			knd = "B" ; 
		} else {
			knd = "A" ; 
		}
		if( seg_c == null ) {
			seg_c = "";
		}
		if( vkgrp == null ) {
			vkgrp = "";
		}
		
		Integer i =  ds510.get(seg_c+knd+age+vkgrp);
		return i == null ? 0 : i;
	}	
	// ======================================================
	
	
	
	
	/********************************
	 *************** SRG ROPING ���ϱ�
	 **�������� ABG
	 **������� ZSPEC12
	 **�ӵ�       ZSPEC3 
	*********************************/
	private ArrayList<String> getSRG(String zspec12, String abg , int zspec3){
		ArrayList srgList = new ArrayList();
		String srg = "" ;
		String srg_c = "" ;
		
		// ROPING �⺻��
		srg_c = "A"; srg = "1:1";
		// ROPING 2:1 
		if(!"".equals(zspec12)) {
			if(zspec12.startsWith("LX")) {
				srg_c = "B"; srg = "2:1";
			}
			else if(zspec12.startsWith("SS")) {
				srg_c = "B"; srg = "2:1";
			}
			else if(zspec12.startsWith("WBLX")) {
				srg_c = "B"; srg = "2:1";
			}
			else if(zspec12.startsWith("WBSS")) {
				srg_c = "B"; srg = "2:1";
			}
		}
		// ROPING 4:1 , ����Ʈ/���, ����/���
		// ��� ���� üũ�ϱ� , ����Ʈ ���� �� �� 20160622
		if("B".equals(abg) && zspec3 > 150) {	
			srg_c = "C"; srg = "4:1";
		}
		//System.out.println( "srg_c  >>  " + srg_c   );
		//System.out.println( "srg  >>  " + srg   );
		//System.out.println( "zspec12  >>  " + zspec12   );
		//System.out.println( "abg  >>  " + abg   );
		//System.out.println( "zspec3  >>  " + zspec3   );

		srgList.add(srg) ;
		srgList.add(srg_c) ;
		
		return srgList ;
	}
	
	/************************************
	 *************** STO ����
	 **List �޾� �ش� �Լ����� ��� ��� �� STO ����
	*************************************/
	public void getListSTO(BusinessContext ctx, Dataset ds_list) throws Exception {
		
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		//ȣ���� CNT (PJT, HNO)
		DatasetSqlRequest dr_hnoinfo = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S5");
		dr_hnoinfo.addParamObject("ds_list", ds_list);
		dr_hnoinfo.addParamObject("G_MANDT", mdt);

		for(int i=0; i<ds_list.getRowCount(); i++) {
			
			dr_hnoinfo.setRowIndex(i);
			Dataset ds_hnoinfo = (Dataset)executor.query(dr_hnoinfo).getResultObject();

			getSTO(ds_hnoinfo);
			ds_list.setColumn(i, "STO"   , ds_hnoinfo.getColumnAsString(0, "STO") );
		}
		
		ctx.addOutputDataset(ds_list);
	}
	/*****************************************
	 ************************* STO ����  *******
	 **********************(STO/����Ʈ/���ǽ�)
	 ** �ӵ�           ZSPEC3 
	 ** ȣ���ȣ       HNO
	 ** ���� �Ѵ��    CNT (ZMASTER02 ���� ȣ�� ����) 
	 ** ��������       ABG
	 ****************************************
	*****************************************/	
	public Dataset getSTO(Dataset ds_hnoinfo){

		String sto = "";   		// STO ����
		String pjt = "";   		// ������Ʈ
		String hno = "";   		// ȣ�� 
		int zspec3 = 0;   		// �ӵ�
		int cnt_l = 0;	   		// �� ���
		int cnt_s = 0;			// s,w ���
		String abg = "";		// ��������
		
		for(int j=0; j<ds_hnoinfo.getRowCount(); j++) {
			pjt  	= ds_hnoinfo.getColumnAsString(j, "PJT"); 				   // ������Ʈ
			hno     = ds_hnoinfo.getColumnAsString(j, "HNO").substring(0, 1);  // ȣ��
			abg     = ds_hnoinfo.getColumnAsString(j, "ABG");			       // ��������
			cnt_l    = ds_hnoinfo.getColumnAsInteger(j, "CNT_L");			  // �� ���
			cnt_s   = ds_hnoinfo.getColumnAsInteger(j, "CNT_S");		     // �� ���
		
			// �ӵ�(zspec3) ���� ��� 0 ���� ����
			if("".equals(ds_hnoinfo.getColumnAsString(j, "ZSPEC3"))){
					zspec3 = 0;
			} else {
					zspec3 = ds_hnoinfo.getColumnAsInteger(j, "ZSPEC3"); 
			}
			// �ӵ�(zspec3) ���� ��� 0 ���� ����
			if("".equals(ds_hnoinfo.getColumnAsString(j, "CNT_S"))){
					cnt_s = 0;
			} else {
					cnt_s = ds_hnoinfo.getColumnAsInteger(j, "CNT_S"); 
			}
			/*
			 * * * * * *  STO ���� ���� * * * * * *
			 * 1) S/W ȣ���� ���  			  "���ǽ�"
			 *    S/W�� ���Ե� Lȣ���� ���         "���ǽ�"
			 * 2) �ӵ� 120 ���� & �Ѵ�� 2�� ����  "STO"
			 * 3) �ǹ����� ����Ʈ	   	      "����Ʈ"
			 * 	   �� �� 			  		  "���ǽ�"
			 * * * * * * * * * * * * * * * * * *
			 */
			
			if(hno.equals("S") || hno.equals("W") || cnt_s > 0)	{ 
					sto = "���ǽ�"; 
			} else if(hno.equals("Z")){ // STO ���� �ʿ���� DATA
				sto = ""; 
			} else{
					if(zspec3 <= 120 && cnt_l <= 2){ 
						sto = "STO"; 
					}
					else if ("A".equals(abg)) {		
						sto = "����Ʈ"; 
					}
					else { 
						sto = "���ǽ�"; 
					}
			}
		/*
			System.out.println("***** STO ���� *****");
			System.out.println("sto  "+ sto);
			System.out.println("zspec3  "+ zspec3);
			System.out.println("hno  "+ hno);
			System.out.println("cnt  "+ cnt_l);
			System.out.println("abg  "+ abg);
			System.out.println("***** STO �� *****");
		*/	
			
			ds_hnoinfo.setColumn(j, "STO" , sto);
		}
		return ds_hnoinfo;
		
	}
	
	public void getListSTO2(BusinessContext ctx, Dataset ds_list) throws Exception {
		for(int i=0; i<ds_list.getRowCount(); i++) {	

			/*ȣ������*/
			String hno    = ds_list.getColumnAsString(i, "HNO").substring(0, 1); // ȣ�� ����
			String abg    = ds_list.getColumnAsString(i, "ABG");
			int    zspec3 = 0;    		// �ӵ�
			String zspec2 = ds_list.getColumnAsString(i, "ZSPEC2"); // ���
			int cnt = ds_list.getColumnAsInteger(i, "CNT");	   		// �� ���
	
			String sto = "";
			ArrayList stoList = getSTO2(zspec3, hno, zspec2, cnt, abg);	
			sto   = stoList.get(0).toString() ;
			ds_list.setColumn(i, "STO"   , sto);
			
			System.out.println("sto  "+sto);
			System.out.println("zspec3  "+zspec3);
			System.out.println("hno  "+hno);
			System.out.println("zspec2  "+zspec2);
			System.out.println("cnt  "+cnt);
			System.out.println("abg  "+abg);
		
		}
		ctx.addOutputDataset(ds_list);
	}	
	/********************************
	 ********************** STO ���ϱ�
	 **�ӵ�       ZSPEC3 
	 **ȣ���ȣ HNO
	 **���        ZSPEC2 
	 **���� �Ѵ�� CNT (ZMASTER02 ���� ȣ�� ����) 
	 **��������     ABG
	*********************************/
	private ArrayList<String> getSTO2(int zspec3, String hno, String zspec2, int cnt, String abg ){
		ArrayList stoList = new ArrayList();
		String seg = "";
		String seg_c = "";
		String spd = "";
		String abg_c = "";
		String abg_k = "";
		String sto = "";
		
		if(hno.equals("S") || hno.equals("W"))	{ sto = "���ǽ�"; }
		else{
				if(zspec3 <= 120 && cnt <= 2)	{ sto = "STO"; }
				else if ("A".equals(abg)) {		sto = "����Ʈ"; }
				else { sto = "���ǽ�"; }
		}
		
		stoList.add(sto) ;
		return stoList ;
	}

	// ����û���� �������� �׸� �� ���ϱ�
	private ArrayList getAirPurifier(String acnd, String zyear, Dataset ds608) {
		ArrayList t_airPurifierList = new ArrayList();
		String t_acnd = "";	
		String t_zyear = "";	

		for(int j=0; j <ds608.getRowCount(); j++) {
			t_acnd   = ds608.getColumnAsString(j, "CS608_TYP");
			t_zyear = ds608.getColumnAsString(j, "CS608_YEAR");
			if( acnd.equals(t_acnd) && zyear.equals(t_zyear) ) {
				t_airPurifierList.add(ds608.getColumnAsDouble(j, "CS608_JAMT"));
				t_airPurifierList.add(ds608.getColumnAsDouble(j, "CS608_FAMT"));
				t_airPurifierList.add(ds608.getColumnAsDouble(j, "CS608_SAMT"));
				t_airPurifierList.add(ds608.getColumnAsDouble(j, "CS608_INC"));
//				t_airPurifierList.add(ds608.getColumnAsDouble(j, "CS608_INCAMT"));				
			}
		}
		
		return t_airPurifierList;
	}
	
}