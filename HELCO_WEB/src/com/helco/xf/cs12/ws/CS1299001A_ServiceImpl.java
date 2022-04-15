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
	 *************** 정기보수 견적 상세 CS1240001B
	 **계약예정일 변경 시 호기별 SEG 및 표준단가 산출
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
		// 공사 임율
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
	 *************** 표준단가표 화면 CS1299001A
	 **기준일에 따른 호기별 SEG 및 표준단가 산출
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
		// 공사 임율
		DatasetSqlRequest zcst600 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S6");
		zcst600.addParamObject("G_MANDT", mdt);

		zcst600.setRowIndex(0);
		Dataset ds600 = (Dataset)executor.query(zcst600).getResultObject();
		
		ds_list = (Dataset)executor.query(dr_hnoinfo).getResultObject();
		// 표준보수료 STDPRICE	
		String pgmID= ds_cond.getColumnAsString(0, "PGMID");
		// 표준보수료 및 seg	
		getPrice(ds_list,ds510,ds600,pgmID);
		// sto 구분
		getSTO(ds_list);

		ctx.addOutputDataset(ds_list);
		
	}
	
	/*****************************************
	 *************** getPrice
	 **FM / POG 호기별 SEG 및 표준단가 구하기
	 * @param pgmID 
	 * @param rat_600 
	 *****************************************/
	public Dataset getPrice(Dataset ds_hnoinfo, Dataset ds510, Dataset ds600, String pgmID) {
		
		// ==============2018.06.26 추가 사항 시작 =====================
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
		// ===============2018.06.26 추가 사항 종료 	===================	
		
		for(int i=0; i<ds_hnoinfo.getRowCount(); i++) {
			String pjthno = "";
			String hno = "";   		// 호기 구분
			String vkgrp = "";   		// 호기 구분
			int zspec3 = 0;   		// 속도
			int zspec7 = 0;    		// 층
			String zspec2 = "";		// 사양
			String zspec12 = "";	// 전기기종		
			String spd = "";   		// 속도 구간
			int cnt = 0;	   		// 총 대수
			int age = 0;			// 연령	
			String abg = "";		// O 빌딩구분
			String abg_c = "";		// N 빌딩구분 CODE
			String abg_k = "";		// N 빌딩구분 CODE NAME	
			String srg   = "";		// ROPING 구분 CODE NAME
			String srg_c = "";		// ROPING 구분 CODE	
			String seg = "";		// SEG CODE NAME
			String seg_c = "";		// SEG CODE
			
			pjthno  = ds_hnoinfo.getColumnAsString(i, "PJT") + ds_hnoinfo.getColumnAsString(i, "HNO");
			hno     = ds_hnoinfo.getColumnAsString(i, "HNO").substring(0, 1);		
			zspec2  = ds_hnoinfo.getColumnAsString(i, "ZSPEC2");	// 사양	
			zspec12 = ds_hnoinfo.getColumnAsString(i, "ZSPEC12");	// 전기기종
			abg     = ds_hnoinfo.getColumnAsString(i, "ABG");		// O 빌딩구분
  			cnt     = ds_hnoinfo.getColumnAsInteger(i, "CNT");		// 총 대수		
			age     = ds_hnoinfo.getColumnAsInteger(i, "AGEK");	// 연령		
			vkgrp   = ds_hnoinfo.getColumnAsString(i, "VKGRP");

			
			if("".equals(abg)){
				//throw new BizException(pjthno + " 의 빌딩구분값이 없습니다. 호기기본정보 정정에서 수정하십시오.");
  			} 			
  			// 속도 값 없는 경우 0으로 보정
  			if("".equals(ds_hnoinfo.getColumnAsString(i, "ZSPEC3"))){
  				zspec3 = 0;
  			} else {
  				zspec3 = ds_hnoinfo.getColumnAsInteger(i, "ZSPEC3"); 
  			}
  			// 층수 값 없는 경우 0으로 보정
  			if("".equals(ds_hnoinfo.getColumnAsString(i, "ZSPEC7"))){
  				zspec7 = 0;
  			} else {
  				zspec7 = ds_hnoinfo.getColumnAsInteger(i, "ZSPEC7");
  			}
			// 연령 보정 
			if(age > 20) { 
				age = 20;
			} else if(age < 1) { 
				age = 1;
			}		
		
			// ROPING 
  			ArrayList srgList = getSRG(zspec12, abg , zspec3);  			
  			srg   = srgList.get(0).toString() ;
  			srg_c = srgList.get(1).toString() ;

			/*SEG 구분 : 빌딩구분*/
  			ArrayList segList = getSEG(zspec3, hno, zspec2, cnt, abg, zspec12);
  			seg   = segList.get(0).toString() ;
  			seg_c = segList.get(1).toString() ;
						
  			/*SEG 구분 END*/	
			ds_hnoinfo.setColumn(i, "SRG"	, srg);
			ds_hnoinfo.setColumn(i, "SEG"   , seg);
			ds_hnoinfo.setColumn(i, "SEG_CD", seg_c);
			
			double CS600_RAT = ds600.getColumnAsDouble(0, "CS600_RAT");
			double CS604_US  = ds600.getColumnAsDouble(0, "CS604_US");  // 정기보수 간접비
			double J_RAT = ds600.getColumnAsDouble(0, "J_RAT"); //직영노무비 배부경비
			double H_RAT = ds600.getColumnAsDouble(0, "H_RAT"); //협력노무비 배부경비
			double K_RAT = 0 ; 	 //간접경비
			K_RAT = J_RAT / 100; // 표준단가 직접경비는 직영 기준으로
				
			// SEG 연령 계약구분 별 표준 항목 가져오기 ZCST510 511
  			// 직영노무비 지역별 요율 적용 ZCST512
			//	getIDX(seg_c, age, knd, ds510, vkgrp);		

			int idx510f = 0;
			int idx510p = 0;
	
			double std1,std2,std3,std4,std5,std6,std7;
			double item1,item2,item3,item4,item5,item6,item7,item8,item9;
			double amt510,ext,fee1,ext1,fee2,ext2,num;
			double rat_512 = 0.0;
			double f1,f2,f3,f4,f5,f6,f7,f8,f9,f10;	// FM 표준단가(F10) 항목
			double p1,p2,p3,p4,p5,p6,p7,p8,p9,p10;	// POG 표준단가(P10) 항목

			/********************************************************/
			/**************** FM  표준단가 계산  ************************/	
			//====== 2018.06.26 ======
			//idx510f = getIDX(seg_c, age, "A", ds510, vkgrp);
			idx510f = getIDX(seg_c, age, "A", map, vkgrp); //ds510, vkgrp);
			//========================	
			
			//System.out.println(" idx510f   >>"   + idx510f );
			std3 = ds510.getColumnAsInteger(idx510f, "STD3");	 //SEG 별 기준층
			std4 = ds510.getColumnAsInteger(idx510f, "STD4");
			item1 = ds510.getColumnAsInteger(idx510f, "ITEM1");	// SEG별 노무비
			item2 = ds510.getColumnAsInteger(idx510f, "ITEM2");	
			item3 = ds510.getColumnAsInteger(idx510f, "ITEM3");	
			item4 = ds510.getColumnAsInteger(idx510f, "ITEM4");	// 직접비율
			item5 = ds510.getColumnAsInteger(idx510f, "ITEM5");
			item6 = ds510.getColumnAsInteger(idx510f, "ITEM6");
			item7 = ds510.getColumnAsInteger(idx510f, "ITEM7");	
			item8 = ds510.getColumnAsInteger(idx510f, "ITEM8");	// 간접비율
			item9 = ds510.getColumnAsInteger(idx510f, "ITEM9");	// 영업이익비율
			amt510 = ds510.getColumnAsInteger(idx510f, "AMT");	// 자재비
			ext = ds510.getColumnAsInteger(idx510f, "EXT");	// 자재비 할증
			fee1 = ds510.getColumnAsInteger(idx510f, "FEE1");	// 정기검사비
			ext1 = ds510.getColumnAsInteger(idx510f, "EXT1");	// 정기검사 할증
			fee2 = ds510.getColumnAsInteger(idx510f, "FEE2");	// 정밀검사비
			ext2 = ds510.getColumnAsInteger(idx510f, "EXT2");	// 정밀검사 할증
			num = ds510.getColumnAsInteger(idx510f, "NUM");	// 정밀검사 횟수
			ext2 = ds510.getColumnAsInteger(idx510f, "EXT2");	// 정밀검사 할증
			rat_512 = ds510.getColumnAsDouble(idx510f, "CS512_RAT");	
  					
			// F1 - 점검노무비
			// F2 - 노무비 층할증
			// F3 - 수리인건비
			// F4 - 직접경비
			// F5 - 검사비
			// F6 - 소모품/자재비
			// F7 - 자재비 층할증
			// F8 - 간접비
			// F9 - 영업이익
			// F10 - 표준단가
			//pog 만 지역별,seg별 요율 적용
			//FM 은 자재나 인건비 투입비용이 전국 동일해서 지역별 요율을 alwjrdyd
			//f1 = item1 * rat_512;
			// D/W 추가 요율 적용, 계약상태 구분없이
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
			//f4 = f1 + f2 + f3; // 직접노무비
			// 2019.04.08 전현정 DL 요청. 직접경비의 경우 점검노무비 + 노무비총할증을 더하여 직접경비를 곱해야 함.
			f4 = f1 + f2; // 직접노무비
			f4 = f4 * K_RAT; // 직접경비
			
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
			/**************** POG 표준단가 계산  ************************/
			/****************                ************************/
			// ==========2018.06.26==============
			//idx510p = getIDX(seg_c, age, "", ds510, vkgrp);	
			idx510p = getIDX(seg_c, age, "", map, vkgrp); 
			// ==================================
			
			
			//System.out.println(" idx510p   >>"   + idx510p );			
			std3 = ds510.getColumnAsInteger(idx510p, "STD3");	 //SEG 별 기준층
			std4 = ds510.getColumnAsInteger(idx510p, "STD4");
			item1 = ds510.getColumnAsInteger(idx510p, "ITEM1");	// SEG별 노무비
			item2 = ds510.getColumnAsInteger(idx510p, "ITEM2");	
			item3 = ds510.getColumnAsInteger(idx510p, "ITEM3");	
			item4 = ds510.getColumnAsInteger(idx510p, "ITEM4");	// 직접비율
			item5 = ds510.getColumnAsInteger(idx510p, "ITEM5");
			item6 = ds510.getColumnAsInteger(idx510p, "ITEM6");
			item7 = ds510.getColumnAsInteger(idx510p, "ITEM7");	
			item8 = ds510.getColumnAsInteger(idx510p, "ITEM8");	// 간접비율
			item9 = ds510.getColumnAsInteger(idx510p, "ITEM9");	// 영업이익비율
			amt510 = ds510.getColumnAsInteger(idx510p, "AMT");	// 자재비
			ext = ds510.getColumnAsInteger(idx510p, "EXT");	// 자재비 할증
			fee1 = ds510.getColumnAsInteger(idx510p, "FEE1");	// 정기검사비
			ext1 = ds510.getColumnAsInteger(idx510p, "EXT1");	// 정기검사 할증
			fee2 = ds510.getColumnAsInteger(idx510p, "FEE2");	// 정밀검사비
			ext2 = ds510.getColumnAsInteger(idx510p, "EXT2");	// 정밀검사 할증
			num = ds510.getColumnAsInteger(idx510p, "NUM");	// 정밀검사 횟수
			ext2 = ds510.getColumnAsInteger(idx510p, "EXT2");	// 정밀검사 할증
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

			p3 = 0; // pog 수리인건비 제외
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
	 *************** 정기보수 수주시행율 조회 CS1239001A
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
			throw new NullPointerException("pgmID 값이 없습니다. 정보기술부로 문의 하십시오.");
		}

/******************************************************
		pgmID 구분
		1.ESTIMATE : 견적
		2.ContractRat : 계약 등록, 승인 전 CS126_PST = 'A5'
		3.승인 완료 대상
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
	 *************** 정기보수 수주시행율 계산하기
	 * @param pgmID 
	 *********************************************/	
	public Dataset calSujuRat(BusinessContext ctx, Dataset ds_list, String pgmID) throws Exception {
		
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		DatasetSqlRequest dr_hnoinfo ;
		DatasetSqlRequest dr_firstContract ;
		
		//=======================2018.07.03 추가 시작=======================
		HashMap<String, Integer> keyMap = new HashMap<String, Integer>();
		//=======================2018.07.03 추가 종료=======================		

/******************************************************
		pgmID 구분
		1.ESTIMATE : 견적
		2.ContractRat : 계약 등록, 승인 전 CS126_PST = 'A5'
		3.승인 완료 대상
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

		// SEG / 연령 별 표준단가표
		// ZCST510 , ZCST511
		DatasetSqlRequest zcst510 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1299001A_S3");
		zcst510.addParamObject("G_MANDT", mdt);

		zcst510.setRowIndex(0);
		Dataset ds510 = (Dataset)executor.query(zcst510).getResultObject();
		
		// hep 점검 주기 별 요율
		DatasetSqlRequest zcst506 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S4");
		zcst506.addParamObject("G_MANDT", mdt);

		zcst506.setRowIndex(0);
		Dataset ds506 = (Dataset)executor.query(zcst506).getResultObject();
		
		// 속도별 요율
		DatasetSqlRequest zcst502 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S5");
		zcst502.addParamObject("G_MANDT", mdt);

		zcst502.setRowIndex(0);
		Dataset ds502 = (Dataset)executor.query(zcst502).getResultObject();
		
		// roping별 공수
		DatasetSqlRequest zcst503 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S6");
		zcst503.addParamObject("G_MANDT", mdt);

		zcst503.setRowIndex(0);
		Dataset ds503 = (Dataset)executor.query(zcst503).getResultObject();
		
		// 팀별 임율 및 경비 비율 ZCST500 ZCST600 ZCST604(간접비율)
		DatasetSqlRequest zcst500 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S7");
		zcst500.addParamObject("G_MANDT", mdt);

		zcst500.setRowIndex(0);
		Dataset ds500 = (Dataset)executor.query(zcst500).getResultObject();
		
		//  ZCST600(공사 임율)
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

		//===========================공기청정기 추가 20200515 Han.JH=========================================
		//  ZCST608
		DatasetSqlRequest zcst608 = SqlMapManagerUtility.makeSqlRequestForDataset("cs12:CS1239001A_S13");
		zcst608.addParamObject("G_MANDT", mdt);

		zcst608.setRowIndex(0);
		Dataset ds608 = (Dataset)executor.query(zcst608).getResultObject(); 
		//==============================================================================================
		
		//=======================2018.07.03 추가 시작=======================
		for(int j = 0; j < ds510.getRowCount(); j++) {
			String seg_510 = DatasetUtility.getString(ds510, j, "SEG");  //ds510.getString(j, "SEG");
			String gbu_510 = DatasetUtility.getString(ds510, j, "GBU");  //ds510.getString(j, "GBU");
			int age_510 = DatasetUtility.getInt(ds510, j, "AGE");  //ds510.getInt(j, "AGE");
			String vkg_510 = DatasetUtility.getString(ds510, j, "VKGRP");  //ds510.getString(j, "VKGRP");
			
			String key = seg_510+gbu_510+age_510+vkg_510;
			keyMap.put(key, j);
		}
		//=======================2018.07.03 추가 종료=======================		
				
		for(int i=0; i<ds_list.getRowCount(); i++) {
					
			dr_hnoinfo.setRowIndex(i);
			Dataset ds_hnoinfo = (Dataset)executor.query(dr_hnoinfo).getResultObject();
			
			double g_wonga = 0;
			double g_wonga2 = 0;
			double g_direct = 0; // 직접비
			double g_direct2 = 0; // 직접비
			double g_indirect = 0; // 간접비
			double g_rat_us_604 = 0; // 간접비율
			
			double g_nmt = 0;	// 노무비
			double g_jmt = 0;	// 자재비
			double g_jmt2 = 0;	// 자재비
			double g_cmt = 0;   // 검사 수수료
			double g_hmt = 0;   // hrts
			double g_dmt = 0;   // di-pbx
			double g_acmt = 0;   // 공기청정기
			double g_sjt = 0;   // 상주원가

			double g_kmt = 0;   // 직접 경비
			double g_rat = 0;
			double g_rat2 = 0;
			double g_smt = 0;   // 상주 계약금액
			double g_ttamt = 0; // 계약 총 금액
			double g_inc = 0; // 인센티브
			//double cs608_inc = 0; // 공기청정기 인센티브 기준금액
			double air_tInc = 0; // 호기별 공기청정기 인센티브 합계금액
			
			String firstH = ""; // HRST 최초 계약
			String firstD = "";
			String firstAC = "";// 공기청정기 최초 계약
			String g_seg = "";
			
			
			for(int h=0; h<ds_hnoinfo.getRowCount(); h++) { 
				/************최초계약 여부 확인 START ************/
				dr_firstContract.addParamObject("ds_list", ds_hnoinfo);
				dr_firstContract.addParamObject("G_MANDT", mdt);
				dr_firstContract.setRowIndex(h);				
				Dataset ds_firstContract = (Dataset)executor.query(dr_firstContract).getResultObject();
				firstH = "Y";
				firstD = "Y";

				String gkd    = ds_hnoinfo.getColumnAsString(h, "GKD");
				// 실패회수 4로 통일
				if (gkd.equals("5") ) {
					gkd = "4";	
				}
				// 종전 hrts 계약 이력이 존재하나, 실패 회수인 경우 최초 계약 여부 
				if(ds_firstContract.getColumnAsString(0, "HYN").equals("Y") && !gkd.equals("4")){
					firstH = "";
				}
				// 종전 dipbx 계약 이력이 존재하나, 실패 회수인 경우 최초 계약 여부 
				if(ds_firstContract.getColumnAsString(0, "DYN").equals("Y")){
					firstD = "";
				}
				// 종전 공기청정기 계약 이력이 존재하나, 실패 회수인 경우 최초 계약 여부 
				if(ds_firstContract.getColumnAsString(0, "ACYN").equals("Y")){
					firstAC = "";
				}				
				/************최초계약 여부 확인 END************/
				
				double h_nmt = 0;	// 총노무비 h_nmt1 + h_nmt2 + h_nmt3 + h_emt
				double h_nmt1 = 0; // 직영 노무비 1( 표준 임율 )
				double h_nmt2 = 0; // 직영 노무비 2( 표준 임율 )
				double h_nmt3 = 0; // 직영 노무비3( 표준 임율 )	
				double h_emt = 0;	// 외주비 ( 협력사 기성 )
				double h_jmt = 0;	// 자재비
				double h_jmt2 = 0;	// 자재비 ( 추가 자재 , 투입예상 자재 )
				double h_cmt = 0;   // 검사 수수료
				double h_hmt = 0;   //
				double h_hmt1 = 0;   //
				double h_hmt2 = 0;   //
				double h_acmt = 0;    // 공기청정기료
				double h_acmt1 = 0;   // 
				double h_acmt2 = 0;   // 				
				double h_dmt = 0;   //
				double h_dmt1 = 0;   //
				double h_dmt2 = 0;   //
				double h_kmt = 0;   // 직접 경비 h_kmt1 + h_kmt2
				double h_kmt1 = 0;   // 직영 경비
				double h_kmt2 = 0;   // 협력사 경비
				double h_ttamt = 0; // 총수주 ( 실계약기준 )
				
				/*호기정보*/
				String hno    = ds_hnoinfo.getColumnAsString(h, "HNO").substring(0, 1); // 호기 구분
				String vkgrp  = ds_hnoinfo.getColumnAsString(h, "VKGRP");
				String abg    = ds_hnoinfo.getColumnAsString(h, "ABG");
				String bld    = ds_hnoinfo.getColumnAsString(h, "BLD");
				String gyn    = ds_hnoinfo.getColumnAsString(h, "GYN"); // 감시반여부
				int    zspec3 = 0;    		// 속도
				int    zspec7 = 0;    		// 층
				String zspec2 = ds_hnoinfo.getColumnAsString(h, "ZSPEC2");;		// 사양
				String zspec12 = ds_hnoinfo.getColumnAsString(h, "ZSPEC12");;	// 전기기종	
				int cnt = 0;	   		// 총 대수
				int age = 0;			// 설치연령 (인수일기준)	
				/*계약정보*/
				String gbu = ds_hnoinfo.getColumnAsString(h, "GBU"); // 직영 구분
				String knd = ds_hnoinfo.getColumnAsString(h, "KND"); // FM POG
				double amt = ds_hnoinfo.getColumnAsDouble(h, "AMT"); // 계약     단가
				double hmt = ds_hnoinfo.getColumnAsDouble(h, "HMT"); // HRTS  단가
				double acmt = ds_hnoinfo.getColumnAsDouble(h, "ACMT"); // 공기청정기  단가
				double dmt = ds_hnoinfo.getColumnAsDouble(h, "DMT"); // DIPBX 단가
				int    ums = ds_hnoinfo.getColumnAsInteger(h, "UMS"); // 실계약기간
				String hep = ds_hnoinfo.getColumnAsString(h, "HEP"); // 점검주기
				String hfr = ds_hnoinfo.getColumnAsString(h, "HFR"); // HRTS 무상서비스 여부
				String hwr = ds_hnoinfo.getColumnAsString(h, "HWR"); // 유무선여부
				String dnd = ds_hnoinfo.getColumnAsString(h, "DND"); // 비통 TYPE
				String acnd = ds_hnoinfo.getColumnAsString(h, "ACND"); // 공기청정기 TYPE
	  			
				
				String zyear = ds_hnoinfo.getColumnAsString(h, "USD").substring(0, 4); // 계약개시일 , 기준년월
				int chkZYear = Integer.parseInt(zyear);
	  			// 기행율 산출 기준 정보, 2014년 이전이 없음. 보정할 것
				if(chkZYear <= 2013) {
					zyear = "2014";
				}
				
				String srg   = "";		// ROPING 구분 CODE NAME
				String srg_c = "";		// ROPING 구분 CODE	
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
				
				// 층수 값 없는 경우 0으로 보정
	  			if("".equals(ds_hnoinfo.getColumnAsString(h, "ZSPEC7"))){
	  				zspec7 = 0;
	  			} else {
	  				zspec7 = ds_hnoinfo.getColumnAsInteger(h, "ZSPEC7");
	  			}
	  			// 속도 값 없는 경우 0으로 보정
	  			if("".equals(ds_hnoinfo.getColumnAsString(h, "ZSPEC3"))){
	  				zspec3 = 0;
	  			} else {
	  				zspec3 = ds_hnoinfo.getColumnAsInteger(h, "ZSPEC3"); 
	  			}
	  			
	  			// 점검주기별 요율 가져오기
	  			rat_506 = getHEPRAT(hep, zyear, ds506);
	  			// 속도별 요율 가져오기
	  			//rat_502 = getSPDRAT(hno, zspec3, zyear, ds502);
	  	
	  			// SEG 가져오기
	  			ArrayList segList = getSEG(zspec3,hno, zspec2, cnt, abg, zspec12);			
	  			seg   = segList.get(0).toString() ;
	  			seg_c = segList.get(1).toString() ;
                
	  			// ROPING 가져오기
	  			ArrayList srgList = getSRG(zspec12, abg , zspec3);  			
	  			srg   = srgList.get(0).toString() ;
	  			srg_c = srgList.get(1).toString() ;

	  			// 임율 정보 가져오기 500 600 604
	  			ArrayList wageList = getWAGE(vkgrp, zyear, ds500);	
	  			m_amt_500 = (Double) wageList.get(0) ;
	  			j_rat_500 = (Double) wageList.get(1) ;
	  			h_rat_500 = (Double) wageList.get(2) ;
	  			rat_600   = (Double) wageList.get(3) ;
	  			g_rat_us_604   = (Double) wageList.get(4) ;

	  			// SEG 연령 계약구분 별 표준 항목 가져오기 ZCST510 511
	  			// 직영노무비 지역별 요율 적용 ZCST512		
	  			//System.out.println("knd  >> "+knd); 
//	  			int idx510 = getIDX(seg_c, age, knd, ds510, vkgrp);
	  			
	  			//=======================2018.07.03 추가 시작=======================
	  			int idx510 = getIDX(seg_c, age, knd, keyMap, vkgrp);
	  			//=======================2018.07.03 추가 종료=======================	  			

	  			double std1,std2,std3,std4,std5,std6,std7;
	  			double item1,item2,item3,item4,item5,item6,item7,item8,item9;
				double amt510,ext,fee1,ext1,fee2,ext2,num;
				double rat_512 = 1.0;

				std3 = ds510.getColumnAsDouble(idx510, "STD3");	 //SEG 별 기준층
				std4 = ds510.getColumnAsDouble(idx510, "STD4"); // 점검시간
				std5 = ds510.getColumnAsDouble(idx510, "STD5"); // 이동시간
				std6 = ds510.getColumnAsDouble(idx510, "STD6"); // 고장처리 시간
				

				
				item2 = ds510.getColumnAsInteger(idx510, "ITEM2");	
				item3 = ds510.getColumnAsInteger(idx510, "ITEM3");	
				item4 = ds510.getColumnAsInteger(idx510, "ITEM4");	// 직접비율
				item5 = ds510.getColumnAsInteger(idx510, "ITEM5");
				item6 = ds510.getColumnAsInteger(idx510, "ITEM6");
				item7 = ds510.getColumnAsInteger(idx510, "ITEM7");	
				item8 = ds510.getColumnAsInteger(idx510, "ITEM8");	// 간접비율
				item9 = ds510.getColumnAsInteger(idx510, "ITEM9");	// 영업이익비율
				amt510 = ds510.getColumnAsInteger(idx510, "AMT");	// 자재비
				ext = ds510.getColumnAsInteger(idx510, "EXT");	// 자재비 할증
				fee1 = ds510.getColumnAsInteger(idx510, "FEE1");	// 정기검사비
				ext1 = ds510.getColumnAsInteger(idx510, "EXT1");	// 정기검사 할증
				fee2 = ds510.getColumnAsInteger(idx510, "FEE2");	// 정밀검사비
				ext2 = ds510.getColumnAsInteger(idx510, "EXT2");	// 정밀검사 할증
				num = ds510.getColumnAsInteger(idx510, "NUM");	// 정밀검사 횟수
				ext2 = ds510.getColumnAsInteger(idx510, "EXT2");	// 정밀검사 할증
				//rat_512 = ds510.getColumnAsDouble(idx510, "CS512_RAT");	
		
				
				// 점검주기에 따라 점검시간 차감 //
				if("02".equals(hep)) {
					std4 = std4 * 0.5 ;
				} else if("03".equals(hep)) {
					std4 = std4 * 0.3 ;
				}
				// 총 점검시간 = 점검시간 + 이동시간 + 고장처리 시간
				std7 = std4 + std5 + std6 ;
				double std7R = 1.045 ; // 인상율
				double item1a = 0 ;
				
				if(!"".equals(knd)) { 
					// FM 의 경우 인상율 적용 
					item1a = item2 * (1 + std7R +  Math.pow(std7R,2) +  Math.pow(std7R,3)+  Math.pow(std7R,4)) / 5;
					item1a = item1a * (std7 / 60) / 100 ;
				} else { // pog 의 경우
					item1a = item2 * (std7 / 60) / 100 ;
				}
				// 점검노무비 산출  백원단위 올림 
				item1a = StrictMath.round(item1a)*100;              // 점검주기별 노무비
				item1 = ds510.getColumnAsInteger(idx510, "ITEM1");	// SEG별 점검 노무비

				item1 = item1a ;
				
				h_nmt1 = item1 * ums;

				h_nmt1 = h_nmt1 * rat_506;
				h_nmt2 = item2;
				if(!"ES".equals(seg_c)){
					if(std3 > zspec7){
						h_nmt2 = 0;	
					} else if(std3 <= zspec7){
						//h_nmt2 = (((zspec7-std3) * h_nmt2 * std4) / std3) / 60;
						// 표준보수료 노무비 - 층별 할증 포함
						// 정기보수 시행율 산출 시 -  층별 할증 제외처리 20170801
						h_nmt2 = 0;	
					}
				} else {
					h_nmt2 = 0;
				}
				h_nmt2 = h_nmt2 * ums ;
				
	  			//로핑별 공수
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
				
				// 직영 노무비 개월수 /속도별 mh/ 임율 / 팀별 노무 단가 / 점검주기에 따른 비율					
				int flag_knd = 0 ;
				if(!"".equals(knd)) { 
					flag_knd = 1; 
				} else { // pog 의 경우만 지역별,seg별 요율 적용
					h_nmt1 = h_nmt1 * rat_512;
				}				
				// D/W 추가 요율 적용, 계약상태 구분없이
				double rat_add = 0.8;
				if(zspec12.matches(".*D/W.*")){
					h_nmt1 = h_nmt1 * rat_add ;
				}
				
				h_nmt2 = h_nmt2 * flag_knd ;
				h_nmt3 = h_nmt3 * flag_knd ;	
				
				if("3".equals(gbu)) { h_nmt1 = 0; h_nmt2 = 0; h_nmt3 = 0;}
		
				h_nmt = h_nmt1 + h_nmt2+ h_nmt3 ;
				
				// 외주비 기성
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
				h_jmt = h_jmt * flag_knd ; // pog 협력사의 경우 재료비 미포함 , 표준단가에는 포함되나 시행율산출 시 제외 할 것 최인식 과장 요청 20160615

				// 추가 자재 or 투입예상 자재 <<  견적시에만 산출
				h_jmt2 = 0 ;
			
				//검사 수수료
				h_cmt = 0 ;
				if(zspec7 <= 6){                                                       	
					h_cmt = fee1 / 12 + (num * fee2) / 60;                                        	
				} else {	                                                            	
					h_cmt = (fee1 +(zspec7 - 6) * ext1) / 12 + (num * fee2 + (zspec7 - 6) * ext2) / 60 ;  	
				}    
		
				h_cmt = h_cmt * ums * flag_knd ;

				/******** HRTS 원가 계산 *****************************************************************/
				if(!"".equals(hwr)){
		  			ArrayList hrtsList = getHRTS(hwr, gyn, zyear, ds601);
		  			
					double CS601_JAMT = (Double) hrtsList.get(0);
					double CS601_TAMT = (Double) hrtsList.get(1);			
					
					//hrts L 호기 무선의 경우/ 설치비 + 자재비 + 통신료 + 가입비 18000
					if("L".equals(hno) && "B".equals(hwr)){
						//h_hmt1 = 100000 + CS601_JAMT + ( CS601_TAMT * ums ) + 18000 ;
						h_hmt1 = 37000 + CS601_JAMT + ( CS601_TAMT * ums ) ;

						// 최초 계약이 아닌 경우 통신료만 계산
						if(firstH.equals("")) {
							h_hmt1 = CS601_TAMT * ums ;
						}
					}
					if("L".equals(hno) && "A".equals(hwr) && "Y".equals(gyn) && h==0){
						h_hmt2= CS601_TAMT * ums ;
						// 최초 계약이 아닌 경우 통신료만 계산
						if(firstH.equals("")) {
							h_hmt2 = CS601_TAMT * ums ;
						}
					} else if("L".equals(hno) && "A".equals(hwr) && "N".equals(gyn) && h==0){
						h_hmt2= CS601_TAMT * ums + CS601_JAMT;
						// 최초 계약이 아닌 경우 통신료만 계산
						if(firstH.equals("")) {
							h_hmt2 = CS601_TAMT * ums ;
						}
					}
					// 무상서비스 인 경우 
					// 모두 무선으로 진행되므로 호기별로 재료비 설치비 적용
					// 재료비 3000 설치비 20020
					if("Y".equals(hfr)){
						h_hmt1 = 0 ;
						h_hmt2 = 0 ;
						h_hmt1 = 3000 + 20020;
					}	
				}

				/******** 공기청정기 원가 계산 시작 *****************************************************************/
				if(!"".equals(acnd)){
					ArrayList airPurifierList = getAirPurifier(acnd, zyear, ds608);
					
					double cs608_jamt = (Double) airPurifierList.get(0);  // 재료비
					double cs608_famt = (Double) airPurifierList.get(1);  // 필터비
					double cs608_samt = (Double) airPurifierList.get(2);  // 설치비
					double cs608_inc = (Double) airPurifierList.get(3);   // 인센티브
					int fCnt = 0; // 계약기간별 필터 갯수
					
					if("A".equals(acnd)){ // 기본형 공기청정기
						fCnt = getFilterCount(ums); // 계약기간에 해당하는 필터 갯수를 구한다.
						h_acmt1 = cs608_jamt + (cs608_famt * fCnt) + cs608_samt + cs608_inc; // 재료비 + 필터비 + 설치비 + 인센티브
						h_acmt2 = 0;
						
						air_tInc += cs608_inc; // 호기별 공기청정기 인센티브 합산금액을 구한다.
					}					
				} else {
					
					h_acmt1 = 0;
					h_acmt2 = 0;
				}
				/******** 공기청정기 원가 계산 종료************************************************************/
				
				/******** DIPBX 원가 계산 *****************************************************************/
				//  비통 통신료 수주의 경우 , dnd 초기화, dmt 비통 수주금액 수주 합 계산 시 제외 처리
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
					
					// 비통 교환기
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
						// 최초 계약이 아닌 경우 통신료만 계산
						if(firstD.equals("")){
							h_dmt1 = tamt602 * ums ;
							h_dmt2 = 0;
						}
					} else if("B".equals(dnd)){
						h_dmt1 = 	lp602 + ( cp602 * ( 1 + acd) ) + key602  + ( mh602 * rat_600 ) ;
						// 최초 계약이 아닌 경우 통신료만 계산
						if(firstD.equals("")){
							h_dmt1 = 0;
						}
						if(h==0) {
							h_dmt1 = h_dmt1 + tamt602 * ums ;
							h_dmt2 = 8 * rat_600 + amt507 ;
							// 최초 계약이 아닌 경우 통신료만 계산
							if(firstD.equals("")){
								h_dmt1 = tamt602 * ums ;
								h_dmt2 = 0;
							}
							
						}
					} else if("C".equals(dnd)){
						h_dmt1 =  damt602 + lp602 + ( cp602 * ( 2 + acd) ) + ( mh602 * rat_600 )  + ( tamt602 * ums ) + jamt_o_602;
						// 최초 계약이 아닌 경우 통신료만 계산
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
			
			// 추가   -----------(ds_lsit 의 분담이행 여부 값	)----//	
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
			
			// 2. 인원 상주 		
			calSangjuRat(ctx,ds_list,i,ds500,pgmID);
			g_sjt = ds_list.getColumnAsDouble(i, "G_SJT");
			g_smt = ds_list.getColumnAsDouble(i, "G_TTAMT_S");
			
			// 3. 추가 자재 
			if("ESTIMATE".equals(pgmID)) {
				calAddMatnr(ctx,ds_list,i);
				g_jmt2 = ds_list.getColumnAsDouble(i, "G_JMT2");
			} else {
				g_jmt2 = g_jmt;	
			}
			
			// 4.원가 계산		
			// 직접비 = 노무비 + 재료비 + 경비  + 검사비 + hrts + dipbx + 상주비 + 공기청정기
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

			// 5.FM 인센티브
			calFmIncentive(ctx,ds_list,i,pgmID,g_rat_us_604);
			
			// 공기청정기 인센티브
//			calAirFurifierInc(ctx,ds_list,i,pgmID,cs608_inc,ds510,ds600,ds_hnoinfo);
			
			// 공기청정기 인센티브
			calAirFurifierInc(ctx,ds_list,i,pgmID,air_tInc);
		}
		return ds_list;
	
	}
	
	public int getFilterCount(int ums) {
		int cnt = 0; // 계약기간별 필터 갯수
		
		if(ums > 0) {
			if(ums > 0 && ums < 7) { // 계약기간이 6개월 이내이면 필터교체 없음
				cnt = 0;
			} else if(ums > 6 && ums < 13) { // 계약기간이 6개월 초과 12개월 이내이면 필터교체 1회
				cnt = 1;
			} else if(ums > 12 && ums < 19) { // 계약기간이 12개월 초과 18개월 이내이면 필터교체 2회
				cnt = 2;
			} else if(ums > 18 && ums < 25) { // 계약기간이 18개월 초과 24개월 이내이면 필터교체 3회
				cnt = 3;
			} else if(ums > 24 && ums < 31) { // 계약기간이 24개월 초과 30개월 이내이면 필터교체 4회
				cnt = 4;
			} else if(ums > 30 && ums < 37) { // 계약기간이 30개월 초과 36개월 이내이면 필터교체 5회
				cnt = 5;
			} else if(ums > 36 && ums < 43) { // 계약기간이 36개월 초과 42개월 이내이면 필터교체 6회
				cnt = 6;
			} else if(ums > 42 && ums < 49) { // 계약기간이 42개월 초과 48개월 이내이면 필터교체 7회
				cnt = 7;
			} else if(ums > 48 && ums < 55) { // 계약기간이 48개월 초과 54개월 이내이면 필터교체 8회
				cnt = 8;
			} else if(ums > 54 && ums < 61) { // 계약기간이 54개월 초과 60개월 이내이면 필터교체 9회
				cnt = 9;
			}
		}
		
		return cnt;
	}

	public Dataset calAirFurifierInc(BusinessContext ctx, Dataset ds_list, int i, String pgmID, double air_tInc) {
		/******************************************************
		pgmID 구분
		1.ESTIMATE : 견적
		2.ContractRat : 계약 등록, 승인 전 CS126_PST = 'A5'
		3.승인 완료 대상
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
		
		double stndrdTotFee = 0; // 표준보수료 합계
		double curTotAmt = 0; // 현재단가 합계
		double preTotAmt = 0; // 이전단가 합계
		
		double airIncentive = 0; // 공기청정기 인센티브
		
		String gkd = ds_list.getColumnAsString(i, "GKD");// 계약유형
		
		// 호기별 이전단가,현재단가,표준보수료 합산 금액을 구한다
		for(int h=0; h<ds_hnoinfo.getRowCount(); h++) {
			// 표준보수료 구한다.
			getPrice(ds_hnoinfo,ds510,ds600,pgmID);
			
			String knd = ds_hnoinfo.getColumnAsString(h, "KND"); // FM POG
			
			if(!"".equals(knd)) {
				// FM계약
				stndrdTotFee += ds_hnoinfo.getColumnAsDouble(h, "F10"); // FM 표준보수료
			} else {
				// POG계약
				stndrdTotFee += ds_hnoinfo.getColumnAsDouble(h, "P10"); // POG 표준보수료
			}
			
			preTotAmt += ds_hnoinfo.getColumnAsDouble(h, "PRE_AMT"); // 이전단가
			curTotAmt += ds_hnoinfo.getColumnAsDouble(h, "AMT_TOT"); // 현재단가
		}
		
		// 공기청정기 인센티브의 경우
		// 계약유형이 전환계약이면서, 기준보수료 이상 일떄 인센티브 제공
		if(gkd.equals("1")) {
			// 현재단가 총금액이 표준보수료를 충족하면 인센티브 지급
			if(curTotAmt >= stndrdTotFee) {
				airIncentive = cs608_inc;
				ds_list.setColumn(i, "G_INC"   , ds_list.getColumnAsDouble(i, "G_INC") + airIncentive);
			}
			
		} else if(gkd.equals("2")) {
			// 계약유형이 갱신계약이면서
			// 종전보수료 보다 이상의 금액 일 때 인센티브 제공			
			// 이전단가가 0 이상이고 그리고 현재단가가 이전단가 이상일때 인센티브 지급
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
			
			if("Y".equals(amg)) { // 추가자재
				t_jmt2 = amg_to2 + t_jmt1;
				
			} else { //  투입예상 자재
				t_jmt2 = amg_to2;
			}
		} else {
			t_jmt2 = t_jmt1;
		}
		
		ds_list.setColumn(i, "G_JMT2"   , t_jmt2);
		return ds_list;
	}


	/*********************************************
	 *************** 인원상주 수주시행율 계산하기
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
		String zyear; // 계약개시일 , 기준년월		 
			
		double tot  = 0;  // 공급가액
		String tgb ;
		String tmp ;
		double hur = 0 ;
		double sjt = 0 ; // 상주 원가
		double sjt_t = 0 ; // 업체별 상주 원가
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
				zyear = ds_sangjuList.getColumnAsString(s, "SFR").substring(0, 4); // 계약개시일 , 기준년월		 
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
			
		if(gbn.equals("02")) { // 미발주
			if(detail.equals("J")) {
				sql = "cs12:CS1209001A_S2";
			} else if(detail.equals("B")) {
				sql = "cs12:CS1209001A_S3";
			} else if(detail.equals("F")) {
				sql = "cs12:CS1209001A_S4";
			} else {
				sql = "cs12:CS1209001A_S5";
			}		
		} else if(gbn.equals("03")) { // 무상
			if(detail.equals("D")) {
				sql = "cs12:CS1209001A_S6";
			} else if(detail.equals("C")) {
				sql = "cs12:CS1209001A_S7";
			} else {
				sql = "cs12:CS1209001A_S8";
			}	
		} else if(gbn.equals("04")) { // 유상
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

		if(gubun1.equals("A") || gubun1.equals("C") ||gubun1.equals("D") ) { //전환,회수,갱신
			sql_h = "cs12:CS1238001A_S1";
			sql_d = "cs12:CS1238001A_S2";		
		} else if(gubun1.equals("B") || gubun1.equals("H")|| gubun1.equals("I") ) { //실패
			sql_h ="cs12:CS1238001A_S3";
			sql_d ="cs12:CS1238001A_S4";
		} else if(gubun1.equals("E")) { //인수
			sql_h = "cs12:CS1238001A_S5";
			sql_d = "cs12:CS1238001A_S6";	
			sql_3 = "cs12:CS1238001A_S13";
		} else if(gubun1.equals("F")) { //무상만료
			sql_h = "cs12:CS1238001A_S7";
			sql_d = "cs12:CS1238001A_S8";		
		} else if(gubun1.equals("G")) { //유상만료
			sql_h = "cs12:CS1238001A_S9";
			sql_d = "cs12:CS1238001A_S10";	
		} else if(gubun1.equals("J")) { //기종교체
			sql_h = "cs12:CS1238001A_S11";
			sql_d = "cs12:CS1238001A_S12";
		}
		
		if(sql_h.length()==0 || sql_d.length()== 0 ) {
			throw new BizException(" 처리 실패 ");	
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
	 ********************** SEG 구하기
	 **속도       ZSPEC3 
	 **호기번호 HNO
	 **사양        ZSPEC2 
	 **현장 총대수 CNT (ZMASTER02 생성 호기 기준) 
	 **빌딩구분     ABG
	*********************************/
	private ArrayList<String> getSEG(int zspec3, String hno, String zspec2, int cnt, String abg, String zspec12 ){
		ArrayList segList = new ArrayList();
		String seg = "";
		String seg_c = "";
		String spd = "";
		String abg_c = "";
		String abg_k = "";

		/*SEG 구분 : 빌딩구분*/
		if("A".equals(abg)) {
			abg_c = "A";
			abg_k = "아파트";		
		} else {
			abg_c = "O";
			abg_k = "오피스";	
		}
		
		if(zspec3 < 120) { spd = "A"; }
		else if(zspec3 < 150) { spd = "B"; }
		else if(zspec3 < 180) { spd = "C"; }
		else if(zspec3 < 300) { spd = "D"; }
		else if(zspec3 < 480) { spd = "E"; }
		else if(zspec3 < 600) { spd = "F"; }
		else {spd = "G";}
		/*SEG 구분 START : ES/F(화물용)/아파트/오피스*/			
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
			seg = "아파트F"; seg_c = "AF";
		}
		segList.add(seg) ;
		segList.add(seg_c) ;
		
		return segList ;
	}
	
	private int getIDX(String seg_c, int age, String knd, Dataset ds510, String vkgrp) {
		int idx = 0 ;

		// 연령 보정 
		if(age > 20) { 
			age = 20;
		} else if(age < 1) { 
			age = 1;
		}
		// 계약구분값  보정
		// 정기보수 계약 구분 값은 pog 는  ''
		if (knd.length()== 0) { 
			knd = "B" ; 
		} else {
			knd = "A" ; 
		}
		//SEG/연령 별 표준단가표
		String seg_510 = "";	// SEG 구분
		String gbu_510 = "";	// FM(A)/POG(B) 구분	
		int    age_510 = 0;		// 연령 구분 (1~20)
		String vkg_510 = "";	// 
		
		for(int j=0; j <ds510.getRowCount(); j++) {
			seg_510 = ds510.getColumnAsString(j, "SEG");
			gbu_510 = ds510.getColumnAsString(j, "GBU");
			age_510 = ds510.getColumnAsInteger(j, "AGE");
			vkg_510 = ds510.getColumnAsString(j, "VKGRP");

			if( seg_c.equals(seg_510)  ) {
				// FM 표준단가 산출
				if( gbu_510.equals(knd) && age_510==age && vkg_510.equals(vkgrp)){
					idx = j ; break;
				}
			}
		}	
		
		return idx ;
	}

	// ================2018.06.26 추가 =======================
	private int getIDX(String seg_c, int age, String knd, HashMap<String, Integer> ds510, String vkgrp) {
		int idx = 0 ;

		// 연령 보정 
		if(age > 20) { 
			age = 20;
		} else if(age < 1) { 
			age = 1;
		}
		// 계약구분값  보정
		// 정기보수 계약 구분 값은 pog 는  ''
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
	 *************** SRG ROPING 구하기
	 **빌딩구분 ABG
	 **전기기종 ZSPEC12
	 **속도       ZSPEC3 
	*********************************/
	private ArrayList<String> getSRG(String zspec12, String abg , int zspec3){
		ArrayList srgList = new ArrayList();
		String srg = "" ;
		String srg_c = "" ;
		
		// ROPING 기본값
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
		// ROPING 4:1 , 아파트/고속, 빌링/고속
		// 고속 기종 체크하기 , 아파트 제외 할 것 20160622
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
	 *************** STO 구분
	 **List 받아 해당 함수에서 대수 계산 후 STO 구분
	*************************************/
	public void getListSTO(BusinessContext ctx, Dataset ds_list) throws Exception {
		
		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);
		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));
		
		//호기대수 CNT (PJT, HNO)
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
	 ************************* STO 구분  *******
	 **********************(STO/아파트/오피스)
	 ** 속도           ZSPEC3 
	 ** 호기번호       HNO
	 ** 현장 총대수    CNT (ZMASTER02 생성 호기 기준) 
	 ** 빌딩구분       ABG
	 ****************************************
	*****************************************/	
	public Dataset getSTO(Dataset ds_hnoinfo){

		String sto = "";   		// STO 구분
		String pjt = "";   		// 프로젝트
		String hno = "";   		// 호기 
		int zspec3 = 0;   		// 속도
		int cnt_l = 0;	   		// 총 대수
		int cnt_s = 0;			// s,w 대수
		String abg = "";		// 빌딩구분
		
		for(int j=0; j<ds_hnoinfo.getRowCount(); j++) {
			pjt  	= ds_hnoinfo.getColumnAsString(j, "PJT"); 				   // 프로젝트
			hno     = ds_hnoinfo.getColumnAsString(j, "HNO").substring(0, 1);  // 호기
			abg     = ds_hnoinfo.getColumnAsString(j, "ABG");			       // 빌딩구분
			cnt_l    = ds_hnoinfo.getColumnAsInteger(j, "CNT_L");			  // 총 대수
			cnt_s   = ds_hnoinfo.getColumnAsInteger(j, "CNT_S");		     // 총 대수
		
			// 속도(zspec3) 없는 경우 0 으로 보정
			if("".equals(ds_hnoinfo.getColumnAsString(j, "ZSPEC3"))){
					zspec3 = 0;
			} else {
					zspec3 = ds_hnoinfo.getColumnAsInteger(j, "ZSPEC3"); 
			}
			// 속도(zspec3) 없는 경우 0 으로 보정
			if("".equals(ds_hnoinfo.getColumnAsString(j, "CNT_S"))){
					cnt_s = 0;
			} else {
					cnt_s = ds_hnoinfo.getColumnAsInteger(j, "CNT_S"); 
			}
			/*
			 * * * * * *  STO 구분 기준 * * * * * *
			 * 1) S/W 호기인 경우  			  "오피스"
			 *    S/W가 포함된 L호기인 경우         "오피스"
			 * 2) 속도 120 이하 & 총대수 2대 이하  "STO"
			 * 3) 건물구분 아파트	   	      "아파트"
			 * 	   그 외 			  		  "오피스"
			 * * * * * * * * * * * * * * * * * *
			 */
			
			if(hno.equals("S") || hno.equals("W") || cnt_s > 0)	{ 
					sto = "오피스"; 
			} else if(hno.equals("Z")){ // STO 구분 필요없는 DATA
				sto = ""; 
			} else{
					if(zspec3 <= 120 && cnt_l <= 2){ 
						sto = "STO"; 
					}
					else if ("A".equals(abg)) {		
						sto = "아파트"; 
					}
					else { 
						sto = "오피스"; 
					}
			}
		/*
			System.out.println("***** STO 시작 *****");
			System.out.println("sto  "+ sto);
			System.out.println("zspec3  "+ zspec3);
			System.out.println("hno  "+ hno);
			System.out.println("cnt  "+ cnt_l);
			System.out.println("abg  "+ abg);
			System.out.println("***** STO 끝 *****");
		*/	
			
			ds_hnoinfo.setColumn(j, "STO" , sto);
		}
		return ds_hnoinfo;
		
	}
	
	public void getListSTO2(BusinessContext ctx, Dataset ds_list) throws Exception {
		for(int i=0; i<ds_list.getRowCount(); i++) {	

			/*호기정보*/
			String hno    = ds_list.getColumnAsString(i, "HNO").substring(0, 1); // 호기 구분
			String abg    = ds_list.getColumnAsString(i, "ABG");
			int    zspec3 = 0;    		// 속도
			String zspec2 = ds_list.getColumnAsString(i, "ZSPEC2"); // 사양
			int cnt = ds_list.getColumnAsInteger(i, "CNT");	   		// 총 대수
	
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
	 ********************** STO 구하기
	 **속도       ZSPEC3 
	 **호기번호 HNO
	 **사양        ZSPEC2 
	 **현장 총대수 CNT (ZMASTER02 생성 호기 기준) 
	 **빌딩구분     ABG
	*********************************/
	private ArrayList<String> getSTO2(int zspec3, String hno, String zspec2, int cnt, String abg ){
		ArrayList stoList = new ArrayList();
		String seg = "";
		String seg_c = "";
		String spd = "";
		String abg_c = "";
		String abg_k = "";
		String sto = "";
		
		if(hno.equals("S") || hno.equals("W"))	{ sto = "오피스"; }
		else{
				if(zspec3 <= 120 && cnt <= 2)	{ sto = "STO"; }
				else if ("A".equals(abg)) {		sto = "아파트"; }
				else { sto = "오피스"; }
		}
		
		stoList.add(sto) ;
		return stoList ;
	}

	// 공기청정기 기준정보 항목 값 구하기
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