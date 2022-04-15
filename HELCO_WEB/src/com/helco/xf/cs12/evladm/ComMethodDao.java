/**
 * 파 일 명 : ComMethodDao.java
 * 설    명 :
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-04-28 오후 2:26:34
 * 변경내역 :
 *
 * struts-Config :
 *
 */
package com.helco.xf.cs12.evladm;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.lang.*;

import tit.biz.AbstractBusinessService;

//import org.apache.log4j.Logger;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.dao.TBEBXRF1Dao;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWMF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWNF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBWUF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBXLF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBXRF1Vo;
import com.helco.xf.cs12.evladm.dao.FrwCrudDAO;
import com.helco.xf.cs12.evladm.dao.TBEBWEF1Dao;
import com.helco.xf.cs12.evladm.dao.TBEBWMF1Dao;
import com.helco.xf.cs12.evladm.dao.TBEBWNF1Dao;
import com.helco.xf.cs12.evladm.dao.TBEBWUF1Dao;
import com.helco.xf.cs12.evladm.dao.TBEBWZF1Dao;
import com.helco.xf.cs12.evladm.dao.TBEBXLF1Dao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

public class ComMethodDao extends AbstractBusinessService
{
//   Logger logger = Logger.getLogger(ComMethodDao.class);

   private int preMonthSeleAmt = 0;
   private double [] basGisungbi;
   private double [] basGisungbi_ja; //자회사용 아파트
   private double [] basGisungbi_jb; //자회사용 빌링

   private boolean incentiveFlag ;
   private int dataId = 0;
   private boolean is1Flag;
   private boolean is2Flag;
   private boolean is3Flag;
   private boolean is4Flag;

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-04-28 오후 2:26:34
	 * 설  명:
	 * 기  타:
	 */
	public int SetBXRBXL(Object obj) throws Exception
	{ 
		ComMethodVo vo = (ComMethodVo) obj;
		ComMethodVo vo2 = (ComMethodVo) obj;
		int dataId = Integer.parseInt(vo.getDataId());
		String jobGubun = vo.getJobGubun();
		String upn_gbn = vo.getUpn().substring(0,1); //J:주차, G:PSD, 그외:승강기(E/L, E/S, M/W)
		String sla_gbn = vo.getSla();

		TBEBWEF1Dao bweDao = new TBEBWEF1Dao();	//호기마스터(ZCST116)
		TBEBWMF1Dao bwmDao = new TBEBWMF1Dao();	//유상계약 정보(ZCST126)
		TBEBWUF1Dao bwuDao = new TBEBWUF1Dao();	//일시중지/보류(복구)정보(ZCST146)
		TBEBWNF1Dao bwnDao = new TBEBWNF1Dao();	//상주인원(ZCST127)
		TBEBWZF1Dao bwzDao = new TBEBWZF1Dao();
		TBEBWNF1Dao bwnDao2 = new TBEBWNF1Dao();//상주인원(ZCST127)

		ArrayList glist = new ArrayList();
		ArrayList mlist = new ArrayList();
		int rtnCode = 0;
//		double bpzGisung[] = { 53224, 47924, 47151, 44824 };
		/********************** 무상보수 지급기준 변경 2013.04.24 LJH **********************/
		double bpzGisung[] = { 53200, 48000, 0, 44800, 6000, 9000 };
		/********************************************************************************/
		setBasGisungbi(bpzGisung);
		
		/********************** 자회사 무상보수(EL) 지급기준 변경 2021.03.30 BGT **********************/
		double bpzGisung_ja[] = {     0, 39100, 34400, 37000, 34600, 36300, 34700, 36000, 34800, 35800, 34800, 35600, 34800, 35500, 34800, 35500, 
				                  34900, 35400, 34900, 35400, 34900, 35300, 34900, 35300, 34900, 35300, 34900, 35200, 34900, 35200, 34900, 35200, 
				                  34900, 35200, 34900, 35200, 34900, 35200, 34900, 35200, 34900, 35100, 34900, 35100, 34900, 35100, 34900, 35100, 
				                  34900, 35100, 34900, 35100, 34900, 35100, 34900, 35100, 34900, 35100, 34900, 35100 };
		setBasGisungbi_ja(bpzGisung_ja);
		double bpzGisung_jb[] = {     0, 39100, 34400, 37800, 35300, 37400, 35600, 37200, 35800, 37100, 36000, 37000, 36100, 36900, 36100, 36900, 
				                  36200, 36800, 36200, 36800, 36200, 36800, 36300, 36800, 36300, 36700, 36300, 36700, 36300, 36700, 36300, 36700, 
				                  36400, 36700, 36400, 36700, 36400, 36700, 36400, 36700, 36400, 36700, 36400, 36700, 36400, 36700, 36400, 36700, 
				                  36400, 36700, 36400, 36600, 36400, 36600, 36400, 36600, 36400, 36600, 36400, 36600  };
		setBasGisungbi_jb(bpzGisung_jb);
		/********************************************************************************/
		
		try {
			TBEBWEF1Vo bweVo = ComVo2BWE(vo);
			TBEBWMF1Vo bwmVo = ComVo2BWM(vo);	//ZCST126
			TBEBWUF1Vo bwuVo = ComVo2BWU(vo);
			TBEBWNF1Vo bwnVo = ComVo2BWN(vo);
			TBEBWNF1Vo bwnVo2 = ComVo2BWN2(vo2);

			switch (dataId) {
			case DBConstants.MU_GONDSA: // 무상공사
				bweVo = (TBEBWEF1Vo) bweDao.select(vo);
				//glist = getCalcGisungbi1(bweVo);
				if( bweVo.getCevwevkgrp().equals("H11") && "L".equals(bweVo.getCebwehno().substring(0, 1))){
					glist = getCalcGisungbi1_j(bweVo);  //자회사 무상공사 추가 
				} else {
					glist = getCalcGisungbi1(bweVo);
				}
				
				rtnCode = saveBxrForBweA(bweVo, glist);
				break;
			case DBConstants.MU_ILBAN: // 무상일반
				bweVo = (TBEBWEF1Vo) bweDao.select(vo);
				chkExistData1(bweVo);
				//glist = getCalcGisungbi1(bweVo);
				if( bweVo.getCevwevkgrp().equals("H11") && "L".equals(bweVo.getCebwehno().substring(0, 1)) ){
					glist = getCalcGisungbi1_j(bweVo);  //자회사 무상공사 추가 
				} else {
					glist = getCalcGisungbi1(bweVo);
				}
				
				rtnCode = saveBxrForBweB(bweVo, glist);
				break;
			/** **************************************************************************************** */
			/*******************************************************************
			 * 이번에 빠짐 ************************************** case
			 * DBConstants.MU_CHUGA : // 무상추가 bweVo = (TBEBWEF1Vo)
			 * bweDao.select(vo); chkExistData1(bweVo); glist =
			 * getCalcGisungbi1(bweVo); rtnCode = saveBxrForBweB(bweVo, glist);
			 * break;
			 ******************************************************************/
			/** **************************************************************************************** */
			case DBConstants.YU_GONDSA: // 유상공사
				setIsFlag("0001");
				bwmVo = (TBEBWMF1Vo) bwmDao.select(vo);
				chkExistData2(bwmVo);
				//glist = getCalcGisungbi2(bwmVo);
				glist = getCalcGisungbi3(bwmVo); //정률제, 유상일반과 동일하게 적용_201210 LHR
				rtnCode = saveBxrForBwmC(bwmVo, glist);
				//mlist = getCalcMaechul2(bwmVo);													// 유상공사 매출 계산시 만료일의 월 매출액을 항상 뭘 말일로 금액계산
				mlist = getCalcMaechul3(bwmVo);														// 2020.04.13 유상일반과 동일하게 계산하도록 함수호출을 변경- 김종
				rtnCode = saveBxlForBwmC(bwmVo, mlist);
				break;
			case DBConstants.YU_ILBAN_SINGYU: // 유상일반신규
				setIsFlag("1001");
				bwmVo = (TBEBWMF1Vo) bwmDao.select(vo);
				chkExistData3(bwmVo);
				glist = getCalcGisungbi3(bwmVo);
				rtnCode = saveBxrForBwmD(bwmVo, glist);
				mlist = getCalcMaechul3(bwmVo);
				rtnCode = saveBxlForBwmC(bwmVo, mlist);
				break;
			case DBConstants.YU_ILBAN_JAEGYEYAK: // 유상일반재계약
				setIsFlag("1111");
				bwmVo = (TBEBWMF1Vo) bwmDao.select(vo);
				glist = getCalcGisungbi3(bwmVo);
				rtnCode = saveBxrForBwmD(bwmVo, glist);
				mlist = getCalcMaechul3(bwmVo);
				rtnCode = saveBxlForBwmC(bwmVo, mlist);
				break;
			case DBConstants.YU_ILBAN_BYUNKYUNG_AFTER: // 유상일반계약변경
				setIsFlag("1111");
				bwmVo = (TBEBWMF1Vo) bwmDao.select(vo);
				glist = getCalcGisungbi3(bwmVo);
				glist = chkExistData6_1(glist, bwmVo);
				rtnCode = saveBxrForBwmD(bwmVo, glist);
				mlist = getCalcMaechul3(bwmVo);
				mlist = chkExistData6_2(mlist, bwmVo);
				rtnCode = saveBxlForBwmC(bwmVo, mlist);
				break;
			case DBConstants.YU_ILBAN_TASA_HOISU: // 타사회수(실패회수)
				setIsFlag("1011");
				bwmVo = (TBEBWMF1Vo) bwmDao.select(vo);
				bwmVo.setGubun("53");
				glist = getCalcGisungbi5(bwmVo);
				rtnCode = saveBxrForBwmC(bwmVo, glist);
				mlist = getCalcMaechul3(bwmVo);
				rtnCode = saveBxlForBwmC(bwmVo, mlist);
				break;
			case DBConstants.YU_ILBAN_JASA_HOISU: // 자사회수
				setIsFlag("1011");
				bwmVo = (TBEBWMF1Vo) bwmDao.select(vo);
				bwmVo.setGubun("54");
				glist = getCalcGisungbi5(bwmVo);
				rtnCode = saveBxrForBwmC(bwmVo, glist);
				mlist = getCalcMaechul3(bwmVo);
				rtnCode = saveBxlForBwmC(bwmVo, mlist);
				break;
			case DBConstants.YU_ILBAN_JADONG_YEONJANG: // 자동연장
				setIsFlag("1111");
				bwmVo = (TBEBWMF1Vo) bwmDao.select(vo);
				glist = getCalcGisungbi3(bwmVo);
				rtnCode = saveBxrForBwmC(bwmVo, glist);
				mlist = getCalcMaechul3(bwmVo);
				rtnCode = saveBxlForBwmC(bwmVo, mlist);
				break;
			case DBConstants.SILPAE_BOGO: // 실패보고 승인
				bwmVo = (TBEBWMF1Vo) bwmDao.select(vo);
				chkExistData4(bwmVo);
				if(!vo2.getMaxSeq().trim().equals("")) {
					bwnVo2 = (TBEBWNF1Vo) bwnDao2.select2(vo2);
					rtnCode = chkExistData7(bwnVo2);
				}
				break;
			case DBConstants.JUNGDO_HAEJI: // 중도해지
				bwmVo = (TBEBWMF1Vo) bwmDao.select(vo);
				rtnCode = chkExistData6(bwmVo);
				if(!vo2.getMaxSeq().trim().equals("")) {
					bwnVo2 = (TBEBWNF1Vo) bwnDao2.select2(vo2);
					rtnCode = chkExistData7(bwnVo2);
				}
				break;
			case DBConstants.ILSI_JUNGJI_SEUNGIN: // 일시중지승인
				//bwuVo = (TBEBWUF1Vo) bwuDao.select(vo);
				//chkExistData5(bwuVo);
				chkExistData5(vo);
				break;
			case DBConstants.ILSI_BOKGU_SEUNGIN: // 일시복구승인
				//bwuVo = (TBEBWUF1Vo) bwuDao.select(vo);
				//restoreExistData(bwuVo, vo);
				restoreExistData(vo);
				break;
			case DBConstants.JEOMGUM_IGWAN_BEFORE: // 점검이관전
				break;
			case DBConstants.JEOMGUM_IGWAN_AFTER: // 점검이관후
				break;
			case DBConstants.INWON_SANGJU: // 인원상주
				bwnVo = (TBEBWNF1Vo) bwnDao.select(vo);
				glist = getCalcGisungbi4(bwnVo);
				rtnCode = saveBxrForBwn(bwnVo, glist);
				mlist = getCalcMaechul4(bwnVo);
				rtnCode = saveBxlForBwn(bwnVo, mlist);
				break;
			}
		}
		catch (Exception e)
		{
         e.printStackTrace();
         throw e;
		}
		return 0;
	}

   /**
	 * 작성자: jkkoo 작성일: 2006-04-30 설 명: 무상공사/일반 에 대한 기성비 산출 기 타:
	 */
   public ArrayList getCalcGisungbi1(TBEBWEF1Vo bweVo) throws BizException
   {
      ArrayList list = new ArrayList();

      int gaewolsu = 0;
      int basGaewol = 0;
      String baljuStd = "";
      String baljuEtd = "";
      boolean mangunFlg = false;
      boolean defaultGisungbiFlg = true;	//기본기성FLAG (기본기성->3개월)
      boolean threeMonthFlg = false;
      String fdt = "";
      String tod = "";
      double calcDays = 0;
      double jdq = 0;
      double bda = 0;
      double jyq = 0;
      double bya = 0;
      double t_bya = 0;

      try
      {
         basGaewol = 0;
         gaewolsu = DateTime.monthsBetween(bweVo.getCebwebgt().substring(0,6)+"01", bweVo.getCebwebmt().substring(0,6)+DateTime.lastDayOfMonth(bweVo.getCebwebmt()).substring(6,8));
         baljuStd = bweVo.getCebwebgt();
         baljuEtd = bweVo.getCebwebmt();
         CommonUtil cmu = new CommonUtil();
         
         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();

            if(i==1)
               fdt = baljuStd;
            else
            {
               if(basGaewol < 3)
               {
            	  //무상 3개월 기본기성인 경우
                  defaultGisungbiFlg = true;
                  fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";
               }
               else
               {
            	  // 무상 3개월 초과
                  if(basGaewol == 3)
                  {
                	 //1일부터 시작하는 경우
                     if("01".equals(baljuStd.substring(6,8)))
                     {
                        fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";
                        defaultGisungbiFlg = false;
                     }
                     //일할 계산 필요한 경우
                     else
                     {
                        fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";
                        tod = DateTime.addDays(DateTime.addMonths(baljuStd, basGaewol), -1);
                        calcDays = DateTime.daysBetween(fdt, tod) + 1;
                        jdq = calcDays;
                        
                        if("J".equals(bweVo.getCebwehno().substring(0, 1))) { // 주차일 경우 PLT수 * 5000
                        	//bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * 6000,"7"));
                        	bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * getBasGisungbi(4),"7"));
                        } else {
                        	bda = getBasGisungbi(0);
                        }
                        
                        if("S".equals(bweVo.getCebwehno().substring(0, 1)) || "W".equals(bweVo.getCebwehno().substring(0, 1))) { // 무상 ES,MW일 경우 53200
                        	bda = getBasGisungbi(0);
                        }

                        bya = bda - t_bya;
                       	list.add(setBxrVoByGisungbi1(fdt, tod, jdq, bda, bya, 0));

                        if( DateTime.daysBetween( DateTime.addDays(DateTime.addMonths(baljuStd, basGaewol), -1), baljuEtd) > 0 )
                        {
                           fdt = DateTime.addDays(tod , 1);
                           tod = DateTime.lastDayOfMonth(fdt);
                           calcDays = DateTime.daysBetween(fdt, tod) + 1;
                           jdq = calcDays;
                           
                           if("J".equals(bweVo.getCebwehno().substring(0, 1))) { // 주차일 경우 PLT수 * 6000
                              // bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * 6000,"7"));
                        	   bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * getBasGisungbi(4),"7"));
                           } else {
                               /* 2012.09.18 LJH 정률제 처리 */
                               //bda = getBasGisungbi1(bweVo);
                               bda = getBasGisungbi1_new(bweVo);
                               /* 2012.09.18 LJH 정률제 처리 */
                           }
                           
                           if("S".equals(bweVo.getCebwehno().substring(0, 1)) || "W".equals(bweVo.getCebwehno().substring(0, 1))) { // 무상 ES,MW일 경우 53200
                        	   bda = getBasGisungbi(0);
                           }
                           
                           bya = Double.parseDouble(cmu.setFrm((((bda / 30) * jdq)),"7"));
                           t_bya = bya;
                       	   list.add(setBxrVoByGisungbi1(fdt, tod, jdq, bda, bya, 0));
                        }
                        defaultGisungbiFlg = false;
                        basGaewol++;
                        continue;
                     }//if("01".equals(baljuStd.substring(6,8)))
                  }//if(basGaewol == 3)
                  // basgaewo
                  else
                  {
                     fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";
                     defaultGisungbiFlg = false;
                     threeMonthFlg = true;
                  }//if(basGaewol == 3)
               }//if(basGaewol < 3)
            }//if(i==1)
            if(i == gaewolsu)
               tod = bweVo.getCebwebmt();
            else
               tod = DateTime.lastDayOfMonth(fdt);
            
            mangunFlg = chkMangun(fdt, tod);
            calcDays = DateTime.daysBetween(fdt, tod) + 1;
            
            if(mangunFlg)
            {
               jdq = 30;
               if(defaultGisungbiFlg) {
            	   if("J".equals(bweVo.getCebwehno().substring(0, 1))) { // 주차일 경우 PLT수 * 5000
            		   //bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * 6000,"7"));
            		   bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * getBasGisungbi(4),"7"));
            	   }		
            	   else {
                	   bda = getBasGisungbi(0);
                   }
            	   
            	   if("S".equals(bweVo.getCebwehno().substring(0, 1)) || "W".equals(bweVo.getCebwehno().substring(0, 1))) { //무상 ES,MW일 경우 53200
            		   bda = getBasGisungbi(0);
                   }
               } else {
            	   if("J".equals(bweVo.getCebwehno().substring(0, 1)) ) { // 주차 3개월 초과
            		// bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * 9000,"7"));
            		   bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * getBasGisungbi(5),"7"));
                   }
            	   else {
                       /* 2012.09.18 LJH 정률제 처리 */
                       //bda = getBasGisungbi1(bweVo);
                       bda = getBasGisungbi1_new(bweVo);
                       /* 2012.09.18 LJH 정률제 처리 */
                   }
            	   if("S".equals(bweVo.getCebwehno().substring(0, 1)) || "W".equals(bweVo.getCebwehno().substring(0, 1))) { // 무상 ES,MW일 경우 53200
            		   bda = getBasGisungbi(0);
                   }
               }
               bya = bda;
            }
            //mangunFlg = false 
            else
            {
               jdq = calcDays;
               if(defaultGisungbiFlg) {
            	   if("J".equals(bweVo.getCebwehno().substring(0, 1))) { // 주차일 경우 PLT수 * 5000
            		   // bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * 6000,"7"));
            		   bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * getBasGisungbi(4),"7"));
            	   } else {
                	   bda = getBasGisungbi(0);
                   }
            	   
            	   if("S".equals(bweVo.getCebwehno().substring(0, 1)) || "W".equals(bweVo.getCebwehno().substring(0, 1))) { // 무상 ES,MW일 경우 53200
            		   bda = getBasGisungbi(0);
                   }
               } else {
            	   if("J".equals(bweVo.getCebwehno().substring(0, 1))) { // 주차 3개월 초과
            		   bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * getBasGisungbi(5),"7"));
            		  // bda = Double.parseDouble(cmu.setFrm(Double.parseDouble(bweVo.getCebweplt()) * 9000,"7"));
                   }  
            	   else {
                       /* 2012.09.18 LJH 정률제 처리 */
                       //bda = getBasGisungbi1(bweVo);
                       bda = getBasGisungbi1_new(bweVo);
                       /* 2012.09.18 LJH 정률제 처리 */
                   }
            	   
            	   if("S".equals(bweVo.getCebwehno().substring(0, 1)) || "W".equals(bweVo.getCebwehno().substring(0, 1))) { // 무상 ES,MW일 경우 53200
            		   bda = getBasGisungbi(0);
                   }
               }
               if(i == gaewolsu) {
            	   bya = bda - t_bya;
               } else {
            	   bya = Double.parseDouble(cmu.setFrm((((bda / 30) * jdq)),"7"));
               }
               
               if(i == 1) {
            	   t_bya = bya;
               }
            }
            System.out.println("bda ======> "+bda);
            list.add(setBxrVoByGisungbi1(fdt, tod, jdq, bda, bya, 0));
            basGaewol++;
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("기성비 산출 오류가 있습니다");
      }
      return list;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-03
     * 설  명: 유상공사 에 대한 기성비 산출
     * 기  타:
     */
   public ArrayList getCalcGisungbi2(TBEBWMF1Vo bwmVo) throws BizException
   {
      ArrayList list = new ArrayList();

      int gaewolsu = 0;
      String baljuStd = "";
      int basGaewol = 0;
      int ums = 0;
      String fdt = "";
      String tod = "";
      int jdq = 0;
      double bda = 53224;
      double hda = 0;
      double dda = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double acda = 0;
      //======================================================================================
      
      int jyq = 0;
      double bya = 0;
      double hya = 0;
      double dya = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double acya = 0;
      //======================================================================================
      double t_bya = 0;
      double t_hya = 0;
      double t_dya = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double t_acya = 0;
      //======================================================================================
      int baseIncentive1 = 0;    // 인센티브 1 기준
      int t_baseIncentive1 = 0;
      double incentive2 = 0;
      double incentive3 = 0;
      double incentive4 = 0;
      double monthSaleAmt = 0;
      int calcDays = 0;
      boolean mangunFlg = false;

      try
      {
    	 CommonUtil cmu = new CommonUtil();
    	 ums = Integer.parseInt(bwmVo.getCebwmums());

         basGaewol = 0;
         baljuStd = bwmVo.getCebwmugs();
         gaewolsu = DateTime.monthsBetween(bwmVo.getCebwmugs().substring(0,6)+"01", bwmVo.getCebwmumr().substring(0,6)+DateTime.lastDayOfMonth(bwmVo.getCebwmumr()).substring(6,8));

         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();

            if(i==1)
               fdt = baljuStd;
            else
               fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tod = bwmVo.getCebwmumr();
            else
               tod = DateTime.lastDayOfMonth(fdt);

            /*2006.08.17 mhcho 기성비 산출시 tot => amt를 기준으로 수정 */
            //monthSaleAmt = Double.parseDouble(bwmVo.getCebwmtot());
            monthSaleAmt = Double.parseDouble(bwmVo.getCebwmamt());

            if(i == 1) {
                mangunFlg = chkMangun(fdt, tod);
                calcDays = DateTime.daysBetween(fdt, tod) + 1;

                if(mangunFlg)
                   jdq = 30;
                else
                   jdq = calcDays;
                
            	if(fdt.substring(6,8).equals("01")) {
                    bya = bda;
                    hya = hda;
                    dya = dda;
                    
                    acya = acda;
                    
                    baseIncentive1 = getBasInc1(Integer.parseInt(bwmVo.getCebwmamt()));            		
            	} else {
                    bya = StrictMath.round(((bda / 30) * jdq));
                    hya = StrictMath.round(((hda / 30) * jdq));
                    dya = StrictMath.round(((dda / 30) * jdq));
                    
                    acya = StrictMath.round(((acda / 30) * jdq));
                    
                    baseIncentive1 = StrictMath.round((getBasInc1(Integer.parseInt(bwmVo.getCebwmamt())) / 30) * jdq);            
                    t_bya = bya;         
                    t_hya = hya;         
                    t_dya = dya;
                    
                    t_acya = acya;
                    
                    t_baseIncentive1 = baseIncentive1;
            	}
            } else if(i == gaewolsu) {
            	mangunFlg = chkMangun(fdt, tod);
                calcDays = DateTime.daysBetween(fdt, tod) + 1;
                
                if(mangunFlg)
                    jdq = 30;
                 else
                    jdq = calcDays;

                if(bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
                	bya = bda;
                    hya = hda;
                    dya = dda;
                    
                    acya = acda;
                    
                    baseIncentive1 = getBasInc1(Integer.parseInt(bwmVo.getCebwmamt()));            		
                } else {
                	bya = StrictMath.round(((bda / 30) * jdq));
                    hya = StrictMath.round(((hda / 30) * jdq));
                    dya = StrictMath.round(((dda / 30) * jdq));
                    
                    acya = StrictMath.round(((acda / 30) * jdq));
                    
                    baseIncentive1 = StrictMath.round((getBasInc1(Integer.parseInt(bwmVo.getCebwmamt())) / 30) * jdq);  
                    bya = bda - t_bya;
                    hya = hda - t_hya;
                    dya = dda - t_dya;
                    
                    acya = acda - t_acya;
                    
                    baseIncentive1 = getBasInc1(Integer.parseInt(bwmVo.getCebwmamt())) - t_baseIncentive1;
                }
            } else {
            	jdq = 30;
            	bya = bda;
                hya = hda;
                dya = dda;
                
                acya = acda;
                
                baseIncentive1 = getBasInc1(Integer.parseInt(bwmVo.getCebwmamt()));            		
            }
            
            if(monthSaleAmt > 100000  && isIs4Flag())
            {
               if(mangunFlg)
                  incentive4 = StrictMath.round(monthSaleAmt * 0.05);
               else
                  incentive4 = StrictMath.round(((monthSaleAmt * 0.05)/ 30) * calcDays);
            }
            
           	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, baseIncentive1, incentive2, incentive3, incentive4, hda, hya, dda, dya, acda, acya));
            basGaewol++;
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("유상공사 기성비 산출 오류가 있습니다");
      }
      return list;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-10
     * 설  명: 유상일반에 대한 기성비 산출
     * 기  타: 신규/재계약
     *         getCalcGisungbi3_B  - 무상서비스 기간 포함 계산 없을경우
     *         getCalcGisungbi3_D  - 비율제계약.
     *         getCalcGisungbi3_E  - FM계약
     */
   public ArrayList getCalcGisungbi3(TBEBWMF1Vo bwmVo) throws BizException
   {
      ArrayList list = new ArrayList();

		String preMonth = "";

      int baseGisungbi = 0;      // 기본기성비 기준
      int baseIncentive1 = 0;    // 인센티브 1 기준
      int baseIncentive2 = 0;    // 인센티브 2 기준
      int baseIncentive3 = 0;    // 인센티브 3 기준
      int preMonthSaleAmt = 0;   // 전월매출단가
      double elevRate = 0;       // 인상률
      int diffSaleAmt = 0;       // 전월매출대비 차액

      try
      {
         preMonthSaleAmt = getPreMonthSaleAmt(bwmVo);
         
         if(preMonthSaleAmt == 0 && !bwmVo.getCebwmcst().trim().substring(1, 3).equals("01")) {
        	 preMonthSaleAmt = getPreMonthSaleAmt2(bwmVo);
         }
         /*2006.08.17 mhcho 기성비 산출시 tot => amt를 기준으로 수정 */
         //elevRate = 100 - ((preMonthSaleAmt / Double.parseDouble(bwmVo.getCebwmtot())) * 100);
         //diffSaleAmt = Integer.parseInt(bwmVo.getCebwmtot()) - preMonthSaleAmt  ;
         //elevRate = 100 - ((preMonthSaleAmt / Double.parseDouble(bwmVo.getCebwmamt())) * 100);
         //(인센티브2 계산공식) : 전월매출단가 - 금월매출단가 = 차액 , 차액 ÷ 전월매출단가
         //elevRate = StrictMath.round(((preMonthSaleAmt - Double.parseDouble(bwmVo.getCebwmamt())) / preMonthSaleAmt) * 100);
         //(인센티브2 계산공식) : 금월매출단가 - 전월매출단가 = 차액 , 차액 ÷ 전월매출단가
         //elevRate = StrictMath.round(((Double.parseDouble(bwmVo.getCebwmamt()) - preMonthSaleAmt) / preMonthSaleAmt) * 100);
         //elevRate = Math.floor(((Double.parseDouble(bwmVo.getCebwmamt()) - preMonthSaleAmt) / preMonthSaleAmt) * 100);
         elevRate = ((Double.parseDouble(bwmVo.getCebwmamt()) - preMonthSaleAmt) / preMonthSaleAmt) * 100;
         if(elevRate >= 0) {
        	 elevRate = Math.floor(elevRate);
         } else {
        	 elevRate = -(Math.floor(-elevRate));
         }
         if (preMonthSaleAmt == 0) elevRate = 0;
         diffSaleAmt = Integer.parseInt(bwmVo.getCebwmamt()) - preMonthSaleAmt;

         if("".equals(bwmVo.getCebwmknd().trim()) || "0".equals(bwmVo.getCebwmknd().trim()))       //2006.08.16 mhcho !"" ==> "" 로 수정.FM계약인 경우 'B'
         {
            if(!"Y".equals(bwmVo.getCebwmrgb()))
            {
               baseGisungbi = getBasGisungbi2(bwmVo);
               if(incentiveFlag)
               {
                  //baseIncentive1 = getBasInc1(Integer.parseInt(bwmVo.getCebwmtot()));
                  baseIncentive1 = getBasInc1(Integer.parseInt(bwmVo.getCebwmamt()));
                  baseIncentive2 = getBasInc2(elevRate);
               }
               list = getCalcGisungbi3_B(bwmVo, baseIncentive1, baseIncentive2, baseIncentive3, diffSaleAmt);
            }
            else
            {
               list = getCalcGisungbi3_D(bwmVo); //비율제 계약
            }
         }
         else
         {
            list = getCalcGisungbi3_E(bwmVo); //FM 계약
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("유상일반 기성비 산출 오류가 있습니다");
      }
      return list;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-13
     * 설  명: 인원상주에 대한 기성비 산출
     * 기  타:
     */
   public ArrayList getCalcGisungbi4(TBEBWNF1Vo bwnVo) throws BizException
   {
      ArrayList list = new ArrayList();

      int gaewolsu = 0;
      String fdt = "";
      String tod = "";
      int jdq = 0;
      int bda = 0;

      double baseGisungbi = 0;
      double bya = 0;
      double t_bya = 0;
      boolean mangunFlg = false;
      int monthSaleAmt = 0;
      int calcDays = 0;

      try
      {
         gaewolsu = DateTime.monthsBetween(bwnVo.getCebwnsfr().substring(0,6)+"01", bwnVo.getCebwnsto().substring(0,6)+DateTime.lastDayOfMonth(bwnVo.getCebwnsto()).substring(6,8));

         int basGaewol = 0;

         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();

            if(i==1)
               fdt = bwnVo.getCebwnsfr();
            else
               fdt = DateTime.addMonths(bwnVo.getCebwnsfr(), basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tod = bwnVo.getCebwnsto();
            else
               tod = DateTime.lastDayOfMonth(fdt);

            mangunFlg = chkMangun(fdt, tod);
            calcDays = DateTime.daysBetween(fdt, tod) + 1;

            //baseGisungbi = ((Integer.parseInt(bwnVo.getCebwnsqt()) * Integer.parseInt(bwnVo.getCebwnsut())) + Integer.parseInt(bwnVo.getCebwncqt())) * Double.parseDouble(bwnVo.getCebwnrto());
				//				shhwang 지급율 /100 으로 고침 0728
            	// 2008.05.09 LJH 수정(상주인원, 단가 없어서 제외)
//				baseGisungbi = ((Integer.parseInt(bwnVo.getCebwnsqt()) * Integer.parseInt(bwnVo.getCebwnsut())) + Integer.parseInt(bwnVo.getCebwncqt())) * Double.parseDouble(bwnVo.getCebwnrto())/100;
//				baseGisungbi = Math.round(((Integer.parseInt(bwnVo.getCebwnamt())) + (Integer.parseInt(bwnVo.getCebwncqt()))) * Double.parseDouble(bwnVo.getCebwnrto())/100);
				baseGisungbi = Math.round(Math.round(((Integer.parseInt(bwnVo.getCebwnamt())) + (Integer.parseInt(bwnVo.getCebwncqt()))) * Double.parseDouble(bwnVo.getCebwnrto())/100));

				//System.out.println(" baseGisungbi================>"+baseGisungbi);

            if(mangunFlg)
            {
               jdq = 30;
               bda = new Double(baseGisungbi).intValue();
               bya = bda;
            }
            else
            {
               jdq = calcDays;
               bda = new Double(baseGisungbi).intValue();
               bya = Math.round(new Double((bda / 30) * jdq).intValue());
               
               if(i == 1) {
               	  t_bya = bya;
	           }
	           if(i == gaewolsu && !bwnVo.getCebwnsto().equals(DateTime.lastDayOfMonth(bwnVo.getCebwnsto()))) {
	        	  bya = new Double(baseGisungbi).intValue() - t_bya;
	           }
            }
            
//            if(bwnVo.getSla().trim().equals("Y")) {
//            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, 0, 0, 0, 0, 0, 0));
//            } else {
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0));
//            }
            basGaewol++;
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("인원상주 기성비 산출 오류가 있습니다");
      }
      return list;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-10
     * 설  명: 유상일반에 대한 기성비 산출
     * 기  타: 회수
     *         getCalcGisungbi5_C1  - 타사/자사 회수 무상 2개월 미만(인센티브3은 판단)
     *         getCalcGisungbi5_C2  - 타사회수 무상 2개월 초과.
     *         getCalcGisungbi5_B - 유상일반(신규/재계약)과 같은 로직.
     */
   public ArrayList getCalcGisungbi5(TBEBWMF1Vo bwmVo) throws BizException
   {
      ArrayList list = new ArrayList();

      String preMonth = "";

      int baseGisungbi = 0;      // 기본기성비 기준
      int baseIncentive1 = 0;    // 인센티브 1 기준
      int baseIncentive2 = 0;    // 인센티브 2 기준
      int baseIncentive3 = 0;    // 인센티브 3 기준
      int preMonthSaleAmt = 0;   // 전월매출단가
      double elevRate = 0;       // 인상률
      int diffSaleAmt = 0;       // 전월매출대비 차액

      try
      {
         preMonthSaleAmt = getPreMonthSaleAmt(bwmVo);
         //elevRate = (Integer.parseInt(bwmVo.getCebwmtot()) / preMonthSaleAmt) * 100;
         //diffSaleAmt = Integer.parseInt(bwmVo.getCebwmtot()) - preMonthSaleAmt  ;
         if(preMonthSaleAmt == 0) {
        	 elevRate = 0;
         } else {
        	 //elevRate = StrictMath.round((Integer.parseInt(bwmVo.getCebwmamt()) / preMonthSaleAmt) * 100);
             elevRate = StrictMath.round(((Double.parseDouble(bwmVo.getCebwmamt()) - preMonthSaleAmt) / preMonthSaleAmt) * 100);
             if (preMonthSaleAmt == 0) elevRate = 0;
         }
         diffSaleAmt = Integer.parseInt(bwmVo.getCebwmamt()) - preMonthSaleAmt;
         

//         if(!"".equals(bwmVo.getCebwmknd()))
         if("".equals(bwmVo.getCebwmknd()))
         {
            if(!"Y".equals(bwmVo.getCebwmrgb()))
            {
               baseGisungbi = getBasGisungbi2(bwmVo);
               if(incentiveFlag)
               {
                  //baseIncentive1 = getBasInc1(Integer.parseInt(bwmVo.getCebwmtot()));
                  baseIncentive1 = getBasInc1(Integer.parseInt(bwmVo.getCebwmamt()));
                  baseIncentive2 = getBasInc2(elevRate);
                  baseIncentive3 = Integer.parseInt(bwmVo.getCebwmamt());
               }
               if("53".equals(bwmVo.getGubun())) // 타사회수
               {
                  if( Integer.parseInt(bwmVo.getCebwmmmn()) <= 2)
                     list = getCalcGisungbi5_C1(bwmVo, baseIncentive1, baseIncentive2, baseIncentive3, diffSaleAmt);
                  else
                     list = getCalcGisungbi5_C2(bwmVo, baseIncentive1, baseIncentive2, baseIncentive3, diffSaleAmt);
               }
               else if("54".equals(bwmVo.getGubun())) // 자사회수
               {
                  if("Y".equals(bwmVo.getCebwmiyn()) && "1".equals(bwmVo.getCebwmwil()) && Integer.parseInt(bwmVo.getCebwmmmn()) == 0)
                     list = getCalcGisungbi5_C1(bwmVo, baseIncentive1, baseIncentive2, baseIncentive3, diffSaleAmt);
                  else
                  {
                     setIs3Flag(false);
                     //20090123 LJH : 회수에 대한 인센티브3 지급은 안한다.(고객지원부 이세희씨 요청)
                     //list = getCalcGisungbi3_B(bwmVo, baseIncentive1, baseIncentive2, baseIncentive3, diffSaleAmt);
                     list = getCalcGisungbi3_B(bwmVo, baseIncentive1, baseIncentive2, 0, 0);
                  }
               }
            }
            else
            {
               list = getCalcGisungbi3_D(bwmVo); //비율제계약
            }
         }
         else
         {
            list = getCalcGisungbi3_E(bwmVo); //FM 계약
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("유상일반 기성비 산출 오류가 있습니다");
      }
      return list;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-10
     * 설  명: 유상일반에 대한 기성비 산출중 인센티브 1,2를 포함하여 산출
     * 기  타: 산출식 도표의 B에 해당
     *         신규/재계약/자동연장 에서 사용
     */
   public ArrayList getCalcGisungbi3_B(TBEBWMF1Vo bwmVo, double incAmt1, double incAmt2, double incAmt3, double diffAmt) throws BizException
   {
      ArrayList list = new ArrayList();

      int gaewolsu = 0;
      int srv_gaewolsu = 0;
      String baljuStd = "";
      String srv_tod = ""; // 무상개월수 만료일
      int mmn = Integer.parseInt(bwmVo.getCebwmmmn());

      int basGaewol = 0;
      String fdt = "";
      String tod = "";
      double jdq = 0;
      double bda = 0;
      double hda = 0;
      double dda = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double acda = 0; // 지급대상정보
      //======================================================================================
      
      double bya = 0;
      double hya = 0;
      double dya = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double acya = 0; // 지급요청정보
      //======================================================================================
      
      double t_bya = 0;
      double t_hya = 0;
      double t_dya = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double t_acya = 0;
      //======================================================================================
      
      boolean mangunFlg = false;
      double incentive1 = 0;
      double incentive2 = 0;
      double incentive3 = 0;
      double incentive4 = 0;
      double t_incentive1 = 0;
      double t_incentive2 = 0;
      double t_incentive3 = 0;
      double t_incentive4 = 0;
      int   monthSaleAmt = 0;
      int   calcDays = 0;

      try
      {
         // 무상서비스 기간의 처리
         basGaewol = 0;
         if(mmn > 0)
            srv_gaewolsu = DateTime.monthsBetween(bwmVo.getCebwmusd().substring(0,6)+"01" , DateTime.lastDayOfMonth(DateTime.addDays(DateTime.addMonths(bwmVo.getCebwmusd(), mmn), -1) ) );
         else
            srv_gaewolsu = 0;
         srv_tod = DateTime.addDays(DateTime.addMonths(bwmVo.getCebwmusd(), mmn), -1);

         for(int j=1;j <= srv_gaewolsu; j++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();
            if(j==1)
               fdt = bwmVo.getCebwmusd();
            else
               fdt = DateTime.addMonths(bwmVo.getCebwmusd(), basGaewol).substring(0,6) + "01";

            if(j == srv_gaewolsu)
               tod = srv_tod;
            else
               tod = DateTime.lastDayOfMonth(fdt);

            mangunFlg = chkMangun(fdt, tod);
            calcDays = DateTime.daysBetween(fdt, tod) + 1;

            if(mangunFlg)
               jdq = 30;
            else
               jdq = calcDays;
            
            if(bwmVo.getSla().trim().equals("Y")) {
            	//list.add(setBxrVoByGisungbi2(fdt,tod,jdq,0,0,0,0,0,0,0,0,0,0));
            } else {
            	//list.add(setBxrVoByGisungbi2(fdt,tod,jdq,0,0,0,0,0,0,0,0,0,0));
            }
            basGaewol++;
         }

         // 유상서비스(개시일)에 대한 처리
         basGaewol = 0;
         baljuStd = bwmVo.getCebwmugs();
         gaewolsu = DateTime.monthsBetween(baljuStd.substring(0,6)+"01", bwmVo.getCebwmumr().substring(0,6)+DateTime.lastDayOfMonth(bwmVo.getCebwmumr()).substring(6,8));

         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();
            if(i==1)
               fdt = baljuStd;
            else
               fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tod = bwmVo.getCebwmumr();
            else
               tod = DateTime.lastDayOfMonth(fdt);

            //monthSaleAmt = Integer.parseInt(bwmVo.getCebwmtot());
            monthSaleAmt = Integer.parseInt(bwmVo.getCebwmamt());
            if(i == 1) {
                mangunFlg = chkMangun(fdt, tod);
                calcDays = DateTime.daysBetween(fdt, tod) + 1;

                if(mangunFlg)
                   jdq = 30;
                else
                   jdq = calcDays;
                
            	if(fdt.substring(6,8).equals("01")) {
            		//bda = getBasGisungbi2(bwmVo);
            		bda = getBasGisungbi2_new(bwmVo); //정률제 적용_201210 LHR
            		hda = getBasGisungbi2_hrts(bwmVo); //HRTS 기성_201211 LHR
            		dda = getBasGisungbi2_dipbx(bwmVo); //DI-PBX 기성_201211 LHR
            		//===========================공기청정기 추가 20200515 Han.JH==================================
            		acda = getBasGisungbi2_aircleaner(bwmVo); //공기청정기 기성_202005 HJH
            		//======================================================================================
                    bya = bda;
                    hya = hda;
                    dya = dda;
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    acya = acda;
                    //======================================================================================
            	} else {
            		//bda = getBasGisungbi2(bwmVo);
            		bda = getBasGisungbi2_new(bwmVo); //정률제 적용_201210 LHR
            		hda = getBasGisungbi2_hrts(bwmVo); //HRTS 기성_201211 LHR
            		dda = getBasGisungbi2_dipbx(bwmVo); //DI-PBX 기성_201211 LHR
            		//===========================공기청정기 추가 20200515 Han.JH==================================
            		acda = getBasGisungbi2_aircleaner(bwmVo); //공기청정기 기성_202005 HJH
            		//======================================================================================
                    bya = StrictMath.round((bda / 30) * jdq);
                    hya = StrictMath.round((hda / 30) * jdq);
                    dya = StrictMath.round((dda / 30) * jdq);
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    acya = StrictMath.round((acda / 30) * jdq);
                    //======================================================================================
                    t_bya = bya;
                    t_hya = hya;
                    t_dya = dya;
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    t_acya = acya;
                    //======================================================================================
            	}
            } else if(i == gaewolsu) {
            	mangunFlg = chkMangun(fdt, tod);
                calcDays = DateTime.daysBetween(fdt, tod) + 1;
                
                if(mangunFlg)
                    jdq = 30;
                 else
                    jdq = calcDays;

                if(bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
            		//bda = getBasGisungbi2(bwmVo);
            		bda = getBasGisungbi2_new(bwmVo); //정률제 적용_201210 LHR
            		hda = getBasGisungbi2_hrts(bwmVo); //HRTS 기성_201211 LHR
            		dda = getBasGisungbi2_dipbx(bwmVo); //DI-PBX 기성_201211 LHR
            		//===========================공기청정기 추가 20200515 Han.JH==================================
            		acda = getBasGisungbi2_aircleaner(bwmVo); //공기청정기 기성_202005 HJH
            		//======================================================================================            		
                    bya = bda;
                    hya = hda;
                    dya = dda;
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    acya = acda;
                    //======================================================================================                    
                } else {
                	//bda = getBasGisungbi2(bwmVo);
            		bda = getBasGisungbi2_new(bwmVo); //정률제 적용_201210 LHR
            		hda = getBasGisungbi2_hrts(bwmVo); //HRTS 기성_201211 LHR
            		dda = getBasGisungbi2_dipbx(bwmVo); //DI-PBX 기성_201211 LHR
            		//===========================공기청정기 추가 20200515 Han.JH==================================
            		acda = getBasGisungbi2_aircleaner(bwmVo); //공기청정기 기성_202005 HJH
            		//======================================================================================            		
                    bya = StrictMath.round((bda / 30) * jdq);
                    hya = StrictMath.round((hda / 30) * jdq);
                    dya = StrictMath.round((dda / 30) * jdq);
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    acya = StrictMath.round((acda / 30) * jdq);
                    //======================================================================================                    
                    bya = bda - t_bya;
                    hya = hda - t_hya;
                    dya = dda - t_dya;
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    acya = acda - t_acya;
                    //======================================================================================
                }
            } else {
            	jdq = 30;
            	bya = bda;
                hya = hda;
                dya = dda;
                //===========================공기청정기 추가 20200515 Han.JH==================================
                acya = acda;
                //======================================================================================                
            }
            
            if(incentiveFlag)
            {
               mangunFlg = chkMangun(fdt, tod);
               calcDays = DateTime.daysBetween(fdt, tod) + 1;

               if(incAmt1 > 0 && isIs1Flag())
               {
                  if(mangunFlg)
                     incentive1 = incAmt1;
                  else
                     incentive1 = StrictMath.round((incAmt1 / 30) * calcDays);
                  
                  if(i == 1) {
                	  t_incentive1 = incentive1;
                  }
                  if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
                	  incentive1 = incAmt1 - t_incentive1;
                  }
               }
               if(incAmt2 != 0  && isIs2Flag())
               {
                  if(mangunFlg)
                     incentive2 = incAmt2;
                  else
                     incentive2 = StrictMath.round((incAmt2 / 30) * calcDays);
                  
                  if(i == 1) {
                	  t_incentive2 = incentive2;
                  }
                  if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
                	  incentive2 = incAmt2 - t_incentive2;
                  }
               }
               if(diffAmt > 0 && isIs3Flag() && mmn == 0)
               {
                  if (baljuStd.substring(6, 8).equals("01"))
                  {
                     if (i == 1)
                        incentive3 = diffAmt * 2;
                     else
                        incentive3 = 0;
                  }
                  else
                  {
                     if (i == 1)
                        incentive3 = StrictMath.round(Math.rint(((diffAmt * 2) / 30) * jdq));
                     else if (i == 2)
                        incentive3 = StrictMath.round(Math.rint((diffAmt * 2) - incentive3));
                     else
                        incentive3 = 0;
                  }
               }

               if(diffAmt > 0 && mmn == 0 && i == 1) { //매출발생 금액만큼을 첫달 incentive3 에 준다.
            	   incentive3 = StrictMath.round((incAmt3 / 30) * calcDays);
               } else if(diffAmt > 0 && !isIs3Flag() && mmn == 0 && i > 1) {
            	   incentive3 = 0;
               }

               if(monthSaleAmt >= 100000  && isIs4Flag())
               {
                  if(mangunFlg)
                     incentive4 = monthSaleAmt * 0.05;
                  else
                     incentive4 = StrictMath.round(((monthSaleAmt * 0.05)/ 30) * calcDays);
                  
                  if(i == 1) {
                	  t_incentive4 = incentive4;
                  }
                  if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
                	  incentive4 = (monthSaleAmt * 0.05) - t_incentive4;
                  }
               }
            }
            
            if(bwmVo.getSla().trim().equals("Y")) {
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            } else {
            	//list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, incentive1, incentive2, incentive3, incentive4));
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, 0, 0, 0, 0, hda, hya, dda, dya, acda, acya)); //정률제 적용, 인센티브 미지급_201210 LHR
            }
            basGaewol++;
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("유상일반(신규/재계약/자동연장) 기성비 산출 오류가 있습니다");
      }
      return list;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-10
     * 설  명: 유상일반에 대한 기성비 산출
     * 기  타: 타사/자사 회수 에서 사용
     *         무상기간이 존재 할때 기성비 및 인센티브 지급을 안한다(2개월까지는 지급 함)
     */
   public ArrayList getCalcGisungbi5_C2(TBEBWMF1Vo bwmVo, int incAmt1, int incAmt2, int incAmt3, int diffAmt) throws BizException
   {
      ArrayList list = new ArrayList();

      int gaewolsu = 0;
      int srv_gaewolsu = 0;
      String baljuStd = "";
      String srv_tod = ""; // 무상개월수 만료일
      int mmn = Integer.parseInt(bwmVo.getCebwmmmn());

      int basGaewol = 0;
      String startDt = "";
      String endDt = "";
      String fdt = "";
      String tod = "";
      int jdq = 0;
      int bda = 0;
      int hda = 0;
      int dda = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      int acda = 0;
      //======================================================================================
      boolean incentive3Yn = false;

      double bya = 0;
      double hya = 0;
      double dya = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double acya = 0;
      //======================================================================================
      double t_bya = 0;
      double t_hya = 0;
      double t_dya = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double t_acya = 0;
      //======================================================================================
      boolean mangunFlg = false;
      double incentive1 = 0;
      double t_incentive1 = 0;
      double incentive2 = 0;
      double incentive3 = 0;
      double incentive4 = 0;
      double t_incentive4 = 0;
      int monthSaleAmt = 0;
      int calcDays = 0;

      try
      {
         // 기성비지급 무상서비스 기간의 처리(계약일에서 2개월 이내)
         basGaewol = 0;
         incentive1 = 0;
         incentive2 = 0;
         incentive4 = 0;
         gaewolsu = DateTime.monthsBetween(bwmVo.getCebwmusd().substring(0,6)+"01" , DateTime.lastDayOfMonth(DateTime.addDays(DateTime.addMonths(bwmVo.getCebwmusd(), 2), -1) ) );
         endDt = DateTime.addDays(DateTime.addMonths(bwmVo.getCebwmusd(), 2), -1);

         for(int i=1;i <= gaewolsu ; i++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();
            if(i==1)
               fdt = bwmVo.getCebwmusd();
            else
               fdt = DateTime.addMonths(bwmVo.getCebwmusd(), basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tod = endDt;
            else
               tod = DateTime.lastDayOfMonth(fdt);

            mangunFlg = chkMangun(fdt, tod);
            calcDays = DateTime.daysBetween(fdt, tod) + 1;
            //monthSaleAmt = Integer.parseInt(bwmVo.getCebwmtot());
            monthSaleAmt = Integer.parseInt(bwmVo.getCebwmamt());

            if(mangunFlg)
            {
               jdq = 30;
               //bda = getBasGisungbi2(bwmVo);
               bda = new Double(getBasGisungbi2_new(bwmVo)).intValue(); // 정률제 적용_201210 LHR
               hda = new Double(getBasGisungbi2_hrts(bwmVo)).intValue(); //hrts_201211 LHR
               dda = new Double(getBasGisungbi2_dipbx(bwmVo)).intValue(); //di-pbx_201211 LHR
               //===========================공기청정기 추가 20200515 Han.JH==================================
	       		acda = new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue(); //공기청정기 기성_202005 HJH
	           //======================================================================================
               bya = bda;
               hya = hda;
               dya = dda;
               //===========================공기청정기 추가 20200515 Han.JH==================================
               acya = acda;
               //======================================================================================               
            }
            else
            {
               jdq = calcDays;
               //bda = getBasGisungbi2(bwmVo);
               bda = new Double(getBasGisungbi2_new(bwmVo)).intValue(); // 정률제 적용_201210 LHR
               hda = new Double(getBasGisungbi2_hrts(bwmVo)).intValue(); //hrts_201211 LHR
               dda = new Double(getBasGisungbi2_dipbx(bwmVo)).intValue(); //di-pbx_201211 LHR
               //===========================공기청정기 추가 20200515 Han.JH==================================
               acda = new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue(); //공기청정기 기성_202005 HJH
	           //======================================================================================               
               bya = StrictMath.round((bda / 30) * jdq);
               hya = StrictMath.round((hda / 30) * jdq);
               dya = StrictMath.round((dda / 30) * jdq);
               //===========================공기청정기 추가 20200515 Han.JH==================================
               acya = StrictMath.round((acda / 30) * jdq);
               //======================================================================================               
            }
            
            if(i == 1) {
            	t_bya = bya;
            	t_hya = hya;
            	t_dya = dya;
            	t_acya = acya;
	        }
	        if(i == gaewolsu && !endDt.equals(DateTime.lastDayOfMonth(endDt))) {
	        	bya = getBasGisungbi2_new(bwmVo) - t_bya; // 정률제 적용_201210 LHR
	        	hya = getBasGisungbi2_hrts(bwmVo) - t_hya; //hrts_201211 LHR
	        	dya = getBasGisungbi2_dipbx(bwmVo) - t_dya; //di-pbx_201211 LHR
	        	//===========================공기청정기 추가 20200515 Han.JH==================================
	        	acya = getBasGisungbi2_aircleaner(bwmVo) - t_acya; // //공기청정기 기성_202005 HJH
	        	//======================================================================================		
	        }
          
            if(incAmt1 > 0)
            {
               if(mangunFlg)
                  incentive1 = incAmt1;
               else
                  incentive1 = StrictMath.round((incAmt1 / 30) * calcDays);
               
               if(i == 1) {
               	  t_incentive1 = incentive1;
	           }
               if(i == gaewolsu && !endDt.equals(DateTime.lastDayOfMonth(endDt))) {
	           	  incentive1 = incAmt1 - t_incentive1;
	           }
            }
            if(monthSaleAmt > 100000)
            {
               if(mangunFlg)
                  incentive4 = StrictMath.round(monthSaleAmt * 0.05);
               else
                  incentive4 = StrictMath.round(((monthSaleAmt * 0.05) / 30) * calcDays);
               
               if(i == 1) {
               	  t_incentive4 = incentive4;
 	           }
               if(i == gaewolsu && !endDt.equals(DateTime.lastDayOfMonth(endDt))) {
 	           	  incentive4 = StrictMath.round(monthSaleAmt * 0.05) - t_incentive4;
 	           }
            }
            if(bwmVo.getSla().trim().equals("Y")) {
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            } else {
            	//list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, incentive1, incentive2, incentive3, incentive4));
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, 0, 0, 0, 0, hda, hya, dda, dya, acda, acya)); //정률제 적용, 인센티브 미지급_201210 LHR
            }
            basGaewol++;
         }

         // 기성비 미지급 무상서비스 기간의 처리(계약일에서 2개월 이후)
         basGaewol = 0;
         startDt = DateTime.addDays(endDt, 1);
         gaewolsu = DateTime.monthsBetween(startDt.substring(0,6)+"01" , DateTime.lastDayOfMonth(DateTime.addDays(DateTime.addMonths(startDt, mmn-2), -1) ) );
         endDt = DateTime.addDays(DateTime.addMonths(bwmVo.getCebwmusd(), mmn), -1);

         for(int i=1;i <= gaewolsu ; i++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();
            if(i==1)
               fdt = startDt;
            else
               fdt = DateTime.addMonths(startDt, basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tod = endDt;
            else
               tod = DateTime.lastDayOfMonth(fdt);

            mangunFlg = chkMangun(fdt, tod);
            calcDays = DateTime.daysBetween(fdt, tod) + 1;

            mangunFlg = chkMangun(fdt, tod);
            calcDays = DateTime.daysBetween(fdt, tod) + 1;

            if(mangunFlg)
               jdq = 30;
            else
               jdq = calcDays;

            if(bwmVo.getSla().trim().equals("Y")) {
            	list.add(setBxrVoByGisungbi2(fdt,tod,jdq,0,0,0,0,0,0,0,0,0,0,0,0));
            } else {
            	list.add(setBxrVoByGisungbi2(fdt,tod,jdq,0,0,0,0,0,0,0,0,0,0,0,0));
            }
            basGaewol++;
         }

         // 유상서비스 기간의 처리(유상보수개시일 부터)
         basGaewol = 0;
         baljuStd = bwmVo.getCebwmugs();
         gaewolsu = DateTime.monthsBetween(baljuStd.substring(0,6)+"01", bwmVo.getCebwmumr().substring(0,6)+DateTime.lastDayOfMonth(bwmVo.getCebwmumr()).substring(6,8));

         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();
            if(i==1)
               fdt = baljuStd;
            else
               fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tod = bwmVo.getCebwmumr();
            else
               tod = DateTime.lastDayOfMonth(fdt);

            mangunFlg = chkMangun(fdt, tod);
            calcDays = DateTime.daysBetween(fdt, tod) + 1;
            //monthSaleAmt = Integer.parseInt(bwmVo.getCebwmtot());
            monthSaleAmt = Integer.parseInt(bwmVo.getCebwmamt());

            if(mangunFlg)
            {
               jdq = 30;
               //bda = getBasGisungbi2(bwmVo);
               bda = new Double(getBasGisungbi2_new(bwmVo)).intValue(); // 정률제 적용_201210 LHR
               hda = new Double(getBasGisungbi2_hrts(bwmVo)).intValue(); //hrts_201211 LHR
               dda = new Double(getBasGisungbi2_dipbx(bwmVo)).intValue(); //di-pbx_201211 LHR
               //===========================공기청정기 추가 20200515 Han.JH==================================
               acda = new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue(); //공기청정기 기성_202005 HJH
	           //======================================================================================               
               bya = bda;
               hya = hda;
               dya = dda;
               //===========================공기청정기 추가 20200515 Han.JH==================================
               acya = acda;
               //======================================================================================
            }
            else
            {
               jdq = calcDays;
               //bda = getBasGisungbi2(bwmVo);
               bda = new Double(getBasGisungbi2_new(bwmVo)).intValue(); // 정률제 적용_201210 LHR
               hda = new Double(getBasGisungbi2_hrts(bwmVo)).intValue(); //hrts_201211 LHR
               dda = new Double(getBasGisungbi2_dipbx(bwmVo)).intValue(); //di-pbx_201211 LHR
               //===========================공기청정기 추가 20200515 Han.JH==================================
               acda = new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue(); //공기청정기 기성_202005 HJH
	           //======================================================================================               
               bya = StrictMath.round((bda / 30) * jdq);
               hya = StrictMath.round((hda / 30) * jdq);
               dya = StrictMath.round((dda / 30) * jdq);
               //===========================공기청정기 추가 20200515 Han.JH==================================
               acya = StrictMath.round((acda / 30) * jdq);
               //======================================================================================
            }
            
            if(i == 1) {
            	t_bya = bya;
            	t_hya = hya;
            	t_dya = dya;
            	//===========================공기청정기 추가 20200515 Han.JH==================================
            	t_acya = acya;
            	//======================================================================================
	        }
	        if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
	        	bya = getBasGisungbi2_new(bwmVo) - t_bya; // 정률제 적용_201210 LHR
	        	hya = getBasGisungbi2_hrts(bwmVo) - t_hya; //hrts_201211 LHR
	        	dya = getBasGisungbi2_dipbx(bwmVo) - t_dya; //di-pbx_201211 LHR
	        	//===========================공기청정기 추가 20200515 Han.JH==================================
	        	acya = getBasGisungbi2_aircleaner(bwmVo) - t_acya; //공기청정기 기성_202005 HJH
	        	//======================================================================================
	        }

            if(incentiveFlag)
            {
               if(incAmt1 > 0)
               {
                  if(mangunFlg)
                     incentive1 = incAmt1;
                  else
                     incentive1 = StrictMath.round((incAmt1 / 30) * calcDays);
                  
                  if(i == 1) {
                   	  t_incentive1 = incentive1;
    	          }
                  if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
    	           	  incentive1 = incAmt1 - t_incentive1;
    	          }
               }
               if(monthSaleAmt > 100000)
               {
                  if(mangunFlg)
                     incentive4 = StrictMath.round(monthSaleAmt * 0.05);
                  else
                     incentive4 = StrictMath.round(((monthSaleAmt * 0.05) / 30) * calcDays);
                  
                  if(i == 1) {
                   	  t_incentive4 = incentive4;
     	          }
                  if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
     	           	  incentive4 = StrictMath.round(monthSaleAmt * 0.05) - t_incentive4;
     	          }
               }
            }
            //20090123 LJH : 회수에 대한 인센티브3 지급은 안한다.(고객지원부 이세희씨 요청)
            //list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya,incentive1,incentive2,incentive3,incentive4));
            if(bwmVo.getSla().trim().equals("Y")) {
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            } else {
            	//list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, incentive1, incentive2, 0, incentive4, hda, hya, dda, dya));
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, 0, 0, 0, 0, hda, hya, dda, dya, acda, acya)); //정률제 적용, 인센티브 미지급_201210 LHR
            }
            basGaewol++;
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("유상일반(자타/타사 회수 무상2개월초과) 기성비 산출 오류가 있습니다");
      }
      return list;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-10
     * 설  명: 유상일반에 대한 기성비 산출
     * 기  타: 타사/자사 회수 에서 사용
     *         무상기간이 없을때 인센티브 3을 첫째달에 준다 (무상기간이 2개월 이하)
     */
   public ArrayList getCalcGisungbi5_C1(TBEBWMF1Vo bwmVo, int incAmt1, int incAmt2, int incAmt3, int diffAmt) throws BizException
   {
      ArrayList list = new ArrayList();

      int gaewolsu = 0;
      String baljuStd = "";
      int mmn = Integer.parseInt(bwmVo.getCebwmmmn());

      int basGaewol = 0;
      String fdt = "";
      String tod = "";
      int jdq = 0;
      int bda = 0;
      int hda = 0;
      int dda = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      int acda = 0;
      //======================================================================================
      double bya = 0;
      double hya = 0;
      double dya = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double acya = 0;
      //======================================================================================
      double t_bya = 0;
      double t_hya = 0;
      double t_dya = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double t_acya = 0;
      //======================================================================================      
      boolean mangunFlg = false;
      double incentive1 = 0;
      double t_incentive1 = 0;
      double incentive2 = 0;
      double incentive3 = 0;
      double incentive4 = 0;
      double t_incentive4 = 0;
      int   monthSaleAmt = 0;
      int   calcDays = 0;

      try
      {
         // 유상서비스 기간의 처리(계약개시일 부터)
         basGaewol = 0;
         baljuStd = bwmVo.getCebwmusd();
         gaewolsu = DateTime.monthsBetween(baljuStd.substring(0,6)+"01", bwmVo.getCebwmumr().substring(0,6)+DateTime.lastDayOfMonth(bwmVo.getCebwmumr()).substring(6,8));

         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();
            if(i==1)
               fdt = baljuStd;
            else
               fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tod = bwmVo.getCebwmumr();
            else
               tod = DateTime.lastDayOfMonth(fdt);

            mangunFlg = chkMangun(fdt, tod);
            calcDays = DateTime.daysBetween(fdt, tod) + 1;
            //monthSaleAmt = Integer.parseInt(bwmVo.getCebwmtot());
            monthSaleAmt = Integer.parseInt(bwmVo.getCebwmamt());

				if (mangunFlg)
				{
					jdq = 30;
					//bda = getBasGisungbi2(bwmVo);
					bda = new Double(getBasGisungbi2_new(bwmVo)).intValue(); //정률제 적용_201210 LHR
					hda = new Double(getBasGisungbi2_hrts(bwmVo)).intValue(); //hrts_201211 LHR
					dda = new Double(getBasGisungbi2_dipbx(bwmVo)).intValue(); //di-pbx_201211 LHR
					//===========================공기청정기 추가 20200515 Han.JH==================================
					acda = new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue();
					//======================================================================================
					bya = bda;
					hya = hda;
					dya = dda;
					//===========================공기청정기 추가 20200515 Han.JH==================================
					acya = acda;
					//======================================================================================
				}
				else
				{
					jdq = calcDays;
					//bda = getBasGisungbi2(bwmVo);
					bda = new Double(getBasGisungbi2_new(bwmVo)).intValue(); //정률제 적용_201210 LHR
					hda = new Double(getBasGisungbi2_hrts(bwmVo)).intValue(); //hrts_201211 LHR
					dda = new Double(getBasGisungbi2_dipbx(bwmVo)).intValue(); //di-pbx_201211 LHR
					//===========================공기청정기 추가 20200515 Han.JH==================================
					acda = new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue();
					//======================================================================================					
					bya = StrictMath.round((bda / 30) * jdq);
					hya = StrictMath.round((hda / 30) * jdq);
					dya = StrictMath.round((dda / 30) * jdq);
					//===========================공기청정기 추가 20200515 Han.JH==================================
					acya = StrictMath.round((acda / 30) * jdq);;
					//======================================================================================					
				}
				if(i == 1) {
					t_bya = bya;
					t_hya = hya;
					t_dya = dya;
					//===========================공기청정기 추가 20200515 Han.JH==================================
					t_acya = acya;
					//======================================================================================
	            }
	            if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
	            	bya = bda - t_bya;
	            	hya = hda - t_hya;
	            	dya = dda - t_dya;
	            	//===========================공기청정기 추가 20200515 Han.JH==================================
	            	acya = acda - t_acya;
	            	//======================================================================================
	            }
				if (incAmt1 > 0)
				{
					if (mangunFlg)
						incentive1 = incAmt1;
					else
						incentive1 = StrictMath.round((incAmt1 / 30) * calcDays);
					
					if(i == 1) {
						t_incentive1 = incentive1;
		            }
		            if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
		            	incentive1 = incAmt1 - t_incentive1;
		            }
				}
				if (mmn == 0)
				{
					if (baljuStd.substring(6, 8).equals("01"))
					{
						if (i == 1)
							incentive3 = monthSaleAmt;
						else
							incentive3 = 0;
					}
					else
					{
						if (i == 1)
							incentive3 = StrictMath.round((monthSaleAmt / 30) * jdq);
						else if (i == 2)
							incentive3 = monthSaleAmt - incentive3;
						else
							incentive3 = 0;
					}
				}
				if (monthSaleAmt > 100000)
				{
					if (mangunFlg)
						incentive4 = StrictMath.round(monthSaleAmt * 0.05);
					else
						incentive4 = StrictMath.round(((monthSaleAmt * 0.05) / 30) * calcDays);
					
					if(i == 1) {
						t_incentive4 = incentive4;
		            }
		            if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
		            	incentive4 = StrictMath.round(monthSaleAmt * 0.05) - t_incentive4;
		            }
				}
				//20090123 LJH : 회수에 대한 인센티브3 지급은 안한다.(고객지원부 이세희씨 요청)
				//list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, incentive1, incentive2, incentive3, incentive4));
				if(bwmVo.getSla().trim().equals("Y")) {
					list.add(setBxrVoByGisungbi2(fdt, tod, jdq, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
	            } else {
	            	//list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, incentive1, incentive2, 0, incentive4));
	            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, 0, 0, 0, 0, hda, hya, dda, dya, acda, acya)); //정률제 적용, 인센티브 미지급_201210 LHR
	            }
				basGaewol++;
         }

      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("유상일반(B타입) 기성비 산출 오류가 있습니다");
      }
      return list;
   }

   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-11
     * 설  명: 유상일반에 대한 기성비 산출중 비율제계약일경우 산출
     * 기  타: 산출식 도표의 D에 해당
     */
   public ArrayList getCalcGisungbi3_D(TBEBWMF1Vo bwmVo) throws BizException
   {
      ArrayList list = new ArrayList();

      int gaewolsu = 0;
      String fdt = "";
      String tod = "";
      int jdq = 0;
      int bda = 0;
      int hda = 0;
      int dda = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      int acda = 0;
      //======================================================================================
      int t_bda = 0;
      int t_hda = 0;
      int t_dda = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      int t_acda = 0;
      //======================================================================================

      double bya = 0;
      boolean mangunFlg = false;
      int      monthSaleAmt = 0;
      int      calcDays = 0;

      try
      {
         gaewolsu = DateTime.monthsBetween(bwmVo.getCebwmugs().substring(0,6)+"01", bwmVo.getCebwmumr().substring(0,6)+DateTime.lastDayOfMonth(bwmVo.getCebwmumr()).substring(6,8));

         int basGaewol = 0;

         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();

            if(i==1)
               fdt = bwmVo.getCebwmugs();
            else
               fdt = DateTime.addMonths(bwmVo.getCebwmugs(), basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tod = bwmVo.getCebwmumr();
            else
               tod = DateTime.lastDayOfMonth(fdt);

            mangunFlg = chkMangun(fdt, tod);
            calcDays = DateTime.daysBetween(fdt, tod) + 1;
            //monthSaleAmt = Integer.parseInt(bwmVo.getCebwmtot());
            monthSaleAmt = Integer.parseInt(bwmVo.getCebwmamt());
            if(mangunFlg)
            {
               jdq = 30;
//               bda = new Double(StrictMath.round(monthSaleAmt * 0.6)).intValue();
               bda = new Double(StrictMath.round(monthSaleAmt * (Double.parseDouble(bwmVo.getCebwmrto()) / 100))).intValue();
               hda = new Double(getBasGisungbi2_hrts(bwmVo)).intValue(); //hrts_201211 LHR
               dda = new Double(getBasGisungbi2_dipbx(bwmVo)).intValue(); //di-pbx_201211 LHR
               //===========================공기청정기 추가 20200515 Han.JH==================================
               acda = new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue();
               //======================================================================================
            }
            else
            {
               jdq = calcDays;
//               bda = new Double(StrictMath.round((monthSaleAmt * 0.6 / 30) * jdq)).intValue();
               bda = new Double(StrictMath.round((monthSaleAmt * (Double.parseDouble(bwmVo.getCebwmrto()) / 100) / 30) * jdq)).intValue();
               hda = new Double(StrictMath.round((getBasGisungbi2_hrts(bwmVo)/ 30) * jdq)).intValue(); //hrts_201211 LHR
               dda = new Double(StrictMath.round((getBasGisungbi2_dipbx(bwmVo)/ 30) * jdq)).intValue(); //di-pbx_201211 LHR
               //===========================공기청정기 추가 20200515 Han.JH==================================
               acda = new Double(StrictMath.round((getBasGisungbi2_aircleaner(bwmVo)/ 30) * jdq)).intValue();
               //======================================================================================
            }
            
            if(i == 1) {
          	  t_bda = bda;
          	  t_hda = hda;
          	  t_dda = dda;
          	  //===========================공기청정기 추가 20200515 Han.JH==================================
          	  t_acda = acda;
          	  //======================================================================================
            }
            if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
//            	bda = (new Double(StrictMath.round(monthSaleAmt * 0.6)).intValue()) - t_bda;
            	bda = (new Double(StrictMath.round(monthSaleAmt * (Double.parseDouble(bwmVo.getCebwmrto()) / 100))).intValue()) - t_bda;
                hda = (new Double(getBasGisungbi2_hrts(bwmVo)).intValue()) - t_hda; //hrts_201211 LHR
                dda = (new Double(getBasGisungbi2_dipbx(bwmVo)).intValue()) - t_dda; //di-pbx_201211 LHR
                //===========================공기청정기 추가 20200515 Han.JH==================================
                acda = (new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue()) - t_acda;
                //======================================================================================
            }
            
//            list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya,0,0,0,0));
            if(bwmVo.getSla().trim().equals("Y")) {
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
            } else {
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bda, 0, 0, 0, 0, hda, hda, dda, dda, acda, 0));
            }
            basGaewol++;
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("유상일반(비율제) 기성비 산출 오류가 있습니다");
      }
      return list;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-11
     * 설  명: 유상일반에 대한 기성비 산출중 FM계약일경우 산출
     * 기  타: 산출식 도표의 E에 해당
     */
   public ArrayList getCalcGisungbi3_E(TBEBWMF1Vo bwmVo) throws BizException
   {
      ArrayList list = new ArrayList();

      int gaewolsu = 0;
      String fdt = "";
      String tod = "";
      int jdq = 0;
      int bda = 0;
      int hda = 0;
      int dda = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      int acda = 0;
      //======================================================================================
      int t_bda = 0;
      int t_hda = 0;
      int t_dda = 0;
      //===========================공기청정기 추가 20200515 Han.JH==================================
      int t_acda = 0;
      //======================================================================================
      double bya = 0;
      double t_bya = 0;
      double   incentive3 = 0;
      boolean mangunFlg = false;
      int      monthSaleAmt = 0;
      int      calcDays = 0;

      try
      {
         gaewolsu = DateTime.monthsBetween(bwmVo.getCebwmugs().substring(0,6)+"01", bwmVo.getCebwmumr().substring(0,6)+DateTime.lastDayOfMonth(bwmVo.getCebwmumr()).substring(6,8));

         int basGaewol = 0;

         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();

            if(i==1)
               fdt = bwmVo.getCebwmugs();
            else
               fdt = DateTime.addMonths(bwmVo.getCebwmugs(), basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tod = bwmVo.getCebwmumr();
            else
               tod = DateTime.lastDayOfMonth(fdt);

            mangunFlg      = chkMangun(fdt, tod);
            calcDays       = DateTime.daysBetween(fdt, tod) + 1;
            //monthSaleAmt   = Integer.parseInt(bwmVo.getCebwmtot());
            monthSaleAmt   = Integer.parseInt(bwmVo.getCebwmamt());

            /* 주차 FM 기성율 57%로 변경 2010.10.04 고객지원부 이세희 요청 */
            /* ES, MW FM 기성율 60%로 변경 2011.09.05 고객지원부 이세희 요청 */
            /* ES, MW FM 기성율 53%로 변경 2013.09.02 고객지원부 이세희 요청 */
            if(mangunFlg)
            {
               jdq = 30;
               if(bwmVo.getCebwmhno().substring(0,1).trim().equals("J")) {
            	   bda = new Double(StrictMath.round(monthSaleAmt * 0.60)).intValue();
            	   hda = new Double(getBasGisungbi2_hrts(bwmVo)).intValue(); //hrts_201211 LHR
            	   dda = new Double(getBasGisungbi2_dipbx(bwmVo)).intValue(); //di-pbx_201211 LHR
            	   //===========================공기청정기 추가 20200515 Han.JH==================================
            	   acda = new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue();
            	   //======================================================================================
/* 2013.09.02
               } else if(bwmVo.getCebwmhno().substring(0,1).trim().equals("S") || bwmVo.getCebwmhno().substring(0,1).trim().equals("W")) {
            	   bda = new Double(StrictMath.round(monthSaleAmt * 0.60)).intValue();
            	   hda = new Double(getBasGisungbi2_hrts(bwmVo)).intValue(); //hrts_201211 LHR
            	   dda = new Double(getBasGisungbi2_dipbx(bwmVo)).intValue(); //di-pbx_201211 LHR
*/
               } else {
            	   bda = new Double(StrictMath.round(monthSaleAmt * 0.53)).intValue();
            	   hda = new Double(getBasGisungbi2_hrts(bwmVo)).intValue(); //hrts_201211 LHR
            	   dda = new Double(getBasGisungbi2_dipbx(bwmVo)).intValue(); //di-pbx_201211 LHR
            	   //===========================공기청정기 추가 20200515 Han.JH==================================
            	   acda = new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue();
            	   //======================================================================================
               }
            }
            else
            {
               jdq = calcDays;
               if(bwmVo.getCebwmhno().substring(0,1).trim().equals("J")) {
            	   bda = new Double(StrictMath.round((monthSaleAmt * 0.60 / 30) * jdq)).intValue();
            	   hda = StrictMath.round((new Double(getBasGisungbi2_hrts(bwmVo)).intValue() / 30) * jdq); //hrts_201211 LHR
            	   dda = StrictMath.round((new Double(getBasGisungbi2_dipbx(bwmVo)).intValue() / 30) * jdq); //di-pbx_201211 LHR
            	   //===========================공기청정기 추가 20200515 Han.JH==================================
            	   acda = StrictMath.round((new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue() / 30) * jdq);
            	   //======================================================================================
/* 2013.09.02
               } else if(bwmVo.getCebwmhno().substring(0,1).trim().equals("S") || bwmVo.getCebwmhno().substring(0,1).trim().equals("W")) {
            	   bda = new Double(StrictMath.round(monthSaleAmt * 0.60)).intValue();
            	   hda = StrictMath.round((new Double(getBasGisungbi2_hrts(bwmVo)).intValue() / 30) * jdq); //hrts_201211 LHR
            	   dda = StrictMath.round((new Double(getBasGisungbi2_dipbx(bwmVo)).intValue() / 30) * jdq); //di-pbx_201211 LHR
*/
               } else {
            	   bda = new Double(StrictMath.round((monthSaleAmt * 0.53 / 30) * jdq)).intValue();
            	   hda = StrictMath.round((new Double(getBasGisungbi2_hrts(bwmVo)).intValue() / 30) * jdq); //hrts_201211 LHR
            	   dda = StrictMath.round((new Double(getBasGisungbi2_dipbx(bwmVo)).intValue() / 30) * jdq); //di-pbx_201211 LHR
            	   //===========================공기청정기 추가 20200515 Han.JH==================================
            	   acda = StrictMath.round((new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue() / 30) * jdq);
            	   //======================================================================================
               }
            }

            if(i == 1) {
          	   t_bda = bda;
          	   t_hda = hda;
          	   t_dda = dda;
          	   //===========================공기청정기 추가 20200515 Han.JH==================================
          	   t_acda = acda;
          	   //======================================================================================
            }
            if(i == gaewolsu && !bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
               if(bwmVo.getCebwmhno().substring(0,1).trim().equals("J")) {
            	   bda = (new Double(StrictMath.round(monthSaleAmt * 0.60)).intValue()) - t_bda;
            	   hda = (new Double(getBasGisungbi2_hrts(bwmVo)).intValue()) - t_hda; //hrts_201211 LHR
            	   dda = (new Double(getBasGisungbi2_dipbx(bwmVo)).intValue()) - t_dda; //di-pbx_201211 LHR
            	   //===========================공기청정기 추가 20200515 Han.JH==================================
            	   acda = (new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue()) - t_acda;
            	   //======================================================================================
/* 2013.09.02
               } else if(bwmVo.getCebwmhno().substring(0,1).trim().equals("S") || bwmVo.getCebwmhno().substring(0,1).trim().equals("W")) {
            	   bda = new Double(StrictMath.round(monthSaleAmt * 0.60)).intValue();
            	   hda = (new Double(getBasGisungbi2_hrts(bwmVo)).intValue()) - t_hda; //hrts_201211 LHR
            	   dda = (new Double(getBasGisungbi2_dipbx(bwmVo)).intValue()) - t_dda; //di-pbx_201211 LHR
*/
               } else {
            	   bda = (new Double(StrictMath.round(monthSaleAmt * 0.53)).intValue()) - t_bda;
            	   hda = (new Double(getBasGisungbi2_hrts(bwmVo)).intValue()) - t_hda; //hrts_201211 LHR
            	   dda = (new Double(getBasGisungbi2_dipbx(bwmVo)).intValue()) - t_dda; //di-pbx_201211 LHR
            	   //===========================공기청정기 추가 20200515 Han.JH==================================
            	   acda = (new Double(getBasGisungbi2_aircleaner(bwmVo)).intValue()) - t_acda;
            	   //======================================================================================
               }
            }

            if(bwmVo.getCebwmugs().substring(6,8).equals("01"))
            {
               if(i == 1)
                  incentive3 = monthSaleAmt;
               else
                  incentive3 = 0;
            }
            else
            {
               if(i == 1)
                  incentive3 = StrictMath.round((monthSaleAmt / 30) * jdq);
               else if(i == 2)
                  incentive3 = monthSaleAmt - incentive3;
               else
                  incentive3 = 0;
            }

//            list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bya, 0, 0, incentive3, 0));
//            list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bda, 0, 0, incentive3, 0)); //2008.09.10
            if(bwmVo.getSla().trim().equals("Y")) {
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)); //2008.09.10 김재순GJ FM 인센티브3 0으로 초기화
            } else {
            	list.add(setBxrVoByGisungbi2(fdt, tod, jdq, bda, bda, 0, 0, 0, 0, hda, hda, dda, dda, acda, 0)); //2008.09.10 김재순GJ FM 인센티브3 0으로 초기화
            }
            basGaewol++;
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("유상일반(FM계약) 기성비 산출 오류가 있습니다");
      }
      return list;
   }

   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-08
     * 설  명: 유상공사 에 대한 매출액 산출
     * 기  타:
     */
   public ArrayList getCalcMaechul2(TBEBWMF1Vo bwmVo) throws BizException
   {
      ArrayList list = new ArrayList();

      int gaewolsu = 0;
      String fdt = "";
      String tdt = "";
      int jdq = 0;
      double cdq = 0;
      double amd = 0;
      double hmd = 0;
      double dmd = 0;
      
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double acmd = 0;
      //======================================================================================
      
      double vad = 0;
      double tod = 0;
      boolean mangunFlg = false;
      int   calcDays = 0;
      int bjg = 0;
      
      try
      {
    	 int basGaewol = 0;
    	 //bjg = Integer.parseInt(bwmVo.getCebwmbjg());
    	 bjg = 1;
         if(bjg == 4) {
         	 bjg = 6;
         } else if(bjg == 5) {
         	 bjg = 12;
         } else {
         	 bjg = bjg;
         }
    	 gaewolsu = DateTime.monthsBetween(bwmVo.getCebwmugs().substring(0,6)+"01", bwmVo.getCebwmumr().substring(0,6)+DateTime.lastDayOfMonth(bwmVo.getCebwmumr()).substring(6,8));
    	 gaewolsu = gaewolsu / bjg;
         basGaewol = bjg;
         
         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXLF1Vo rtnVo = new TBEBXLF1Vo();
            if(i==1)
               fdt = bwmVo.getCebwmusd();
            else
               fdt = DateTime.addMonths(bwmVo.getCebwmusd(), basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tdt = bwmVo.getCebwmumr();
            else
               tdt = DateTime.lastDayOfMonth(fdt);

            mangunFlg = chkMangun(fdt, tdt);
            calcDays = DateTime.daysBetween(fdt, tdt) + 1;
            if(mangunFlg)
               jdq = 30;
            else
               jdq = calcDays;
         }
         String gubun = "";
         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXLF1Vo rtnVo = new TBEBXLF1Vo();
            if(i == 1)
            {
               gubun = "1";
               fdt = bwmVo.getCebwmugs();
               tdt = DateTime.lastDayOfMonth(bwmVo.getCebwmugs());
               mangunFlg = chkMangun(fdt, tdt);
               calcDays = DateTime.daysBetween(fdt, tdt) + 1;
               if(mangunFlg)
            	   jdq = 30 * bjg;
	               if(bjg == 12) {
	            	   jdq = 365;
	               }
               else
                   jdq = calcDays;
               
               if(fdt.substring(6,8).equals("01"))
               {
                  amd = Double.parseDouble(bwmVo.getCebwmamt());
                  hmd = Double.parseDouble(bwmVo.getCebwmhmt());
                  dmd = Double.parseDouble(bwmVo.getCebwmdmt());
                  vad = Double.parseDouble(bwmVo.getCebwmvat());
                  tod = Double.parseDouble(bwmVo.getCebwmtot());
                  
                //===========================공기청정기 추가 20200515 Han.JH==================================
                  acmd = Double.parseDouble(bwmVo.getCebwmacmt());
                //======================================================================================  
               }
               else
               {
				  //만료월(tdt)의 총 일수 by ohb 2006.08.28
				  cdq = Integer.parseInt(DateTime.lastDayOfMonth(tdt).substring(6,8));
/*
				  cdq = 30 * bjg;
				  if(bjg == 12) {
					  cdq = 365;
                  }
*/
				  //단미만은 반올림
				  amd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmamt()) / cdq ) * jdq);
				  hmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmhmt()) / cdq ) * jdq);
				  dmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmdmt()) / cdq ) * jdq);
				  vad = StrictMath.round((Double.parseDouble(bwmVo.getCebwmvat()) / cdq ) * jdq);
				  tod = StrictMath.round((Double.parseDouble(bwmVo.getCebwmtot()) / cdq ) * jdq);
				  
				  //===========================공기청정기 추가 20200515 Han.JH==================================				  
				  acmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmacmt()) / cdq ) * jdq);
				  //======================================================================================
               }
               //basGaewol++;
            }
            else if(i == gaewolsu)
            {
//               fdt = bwmVo.getCebwmumr().substring(0,6)+"01";
               fdt = DateTime.addMonths(bwmVo.getCebwmugs(), basGaewol).substring(0,6) + "01";
//               tdt = bwmVo.getCebwmumr();
               tdt = DateTime.lastDayOfMonth(DateTime.addMonths(fdt, bjg - 1));
              
               mangunFlg = chkMangun(fdt, tdt);
               calcDays = DateTime.daysBetween(fdt, tdt) + 1;
               if(mangunFlg)
            	   jdq = 30 * bjg;
	               if(bjg == 12) {
	            	   jdq = 365;
	               }
               else
                   jdq = calcDays;
               if(bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr())))
               {
                  amd = Double.parseDouble(bwmVo.getCebwmamt());
                  hmd = Double.parseDouble(bwmVo.getCebwmhmt());
                  dmd = Double.parseDouble(bwmVo.getCebwmdmt());
                  vad = Double.parseDouble(bwmVo.getCebwmvat());
                  tod = Double.parseDouble(bwmVo.getCebwmtot());
                  //===========================공기청정기 추가 20200515 Han.JH==================================
                  acmd = Double.parseDouble(bwmVo.getCebwmacmt());
                  //======================================================================================
               }
               else
               {
			      //만료월(tdt)의 총 일수 by ohb 2006.08.28
				  cdq = Integer.parseInt(DateTime.lastDayOfMonth(tdt).substring(6,8));
/*
				  cdq = 30 * bjg;
 				  if(bjg == 12) {
 					  cdq = 365;
                  }
*/
				  //단미만은 반올림
                  amd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmamt()) / cdq ) * jdq);
				  hmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmhmt()) / cdq ) * jdq);
				  dmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmdmt()) / cdq ) * jdq);
                  vad = StrictMath.round((Double.parseDouble(bwmVo.getCebwmvat()) / cdq ) * jdq);
                  tod = StrictMath.round((Double.parseDouble(bwmVo.getCebwmtot()) / cdq ) * jdq);
                  
                  //===========================공기청정기 추가 20200515 Han.JH==================================                  
                  acmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmacmt()) / cdq ) * jdq);
                  //======================================================================================
               }
               //basGaewol++;
            }
            else
            {
               jdq = 30 * bjg;
               if(bjg == 12) {
            	   jdq = 365;
               }
//               fdt = DateTime.addMonths(bwmVo.getCebwmugs(), basGaewol++).substring(0,6) + "01";
               fdt = DateTime.addMonths(bwmVo.getCebwmugs(), basGaewol).substring(0,6) + "01";
               tdt = DateTime.lastDayOfMonth(fdt);
               amd = Double.parseDouble(bwmVo.getCebwmamt());
               hmd = Double.parseDouble(bwmVo.getCebwmhmt());
               dmd = Double.parseDouble(bwmVo.getCebwmdmt());
               vad = Double.parseDouble(bwmVo.getCebwmvat());
               tod = Double.parseDouble(bwmVo.getCebwmtot());
               
               //===========================공기청정기 추가 20200515 Han.JH==================================
               acmd = Double.parseDouble(bwmVo.getCebwmacmt());
               //======================================================================================
            }
            /****2006.07.29 mhcho 추가 cebxlydt를 구하기 위해****************/
            String tis  = bwmVo.getCebwmtis();
            String ydt  = "";

            if(Integer.parseInt(fdt) <= Integer.parseInt(CommonUtil.getToday2())){
               if(tis.equals("99")) ydt   = CommonUtil.getToday2().substring(0,6)+CommonUtil.getMonthPerDay(CommonUtil.getToday2().substring(0,6).substring(0,6));
               else ydt = CommonUtil.getToday2().substring(0,6)+tis;
            }else{
               if(tis.equals("99")) ydt   = fdt.substring(0,6)+CommonUtil.getMonthPerDay(fdt.substring(0,6));
               else ydt = fdt.substring(0,6)+tis;
            }
            /***************************************************************/
            list.add(setBxlVoByMaechul(fdt, tdt, jdq, amd, vad, tod, gubun, ydt, hmd, dmd, acmd));
            
            if(i > 1) {
            	basGaewol = basGaewol + bjg;
            }
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("유상공사 매출액 산출 오류가 있습니다");
      }
      return list;
   }

   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-10
     * 설  명: 유상일반계약에 대한 매출액 산출
     * 기  타: 신규/재계약/타사회수/자사회수/자동연장
     *         2006-06-05 무상서비스 기간에 별도 처리 블럭 추가
     */
   public ArrayList getCalcMaechul3(TBEBWMF1Vo bwmVo) throws BizException
   {
      ArrayList list = new ArrayList();

      int mmn = Integer.parseInt(bwmVo.getCebwmmmn()); // 무상서비스 개월수
      String srv_tod = ""; // 무상개월수 만료일

      int gaewolsu = 0;
      int srv_gaewolsu = 0;
      int basGaewol = 0;

      int ums = 0;
      String fdt = "";
      String tdt = "";
      String baljuStd = "";
	  int jdq = 0;
	  double cdq = 0;
      double amd = 0;
      double vad = 0;
      double tod = 0;
      double t_amd = 0;
      double t_vad = 0;
      double t_tod = 0;
      double hmd = 0;
      double t_hmd = 0;
      double dmd = 0;
      double t_dmd = 0;
      
      //===========================공기청정기 추가 20200515 Han.JH==================================
      double acmd = 0;
      double t_acmd = 0;
      //======================================================================================
      
      boolean mangunFlg = false;
      int   calcDays = 0;
      int bjg = 0;

      try
      {
         // 1. 무상서비스 기간에 대한 처리 (매출액을 0 으로 생성 한다.)
         if(mmn > 0)
            srv_gaewolsu = DateTime.monthsBetween(bwmVo.getCebwmusd().substring(0,6)+"01" , DateTime.lastDayOfMonth(DateTime.addDays(DateTime.addMonths(bwmVo.getCebwmusd(), mmn), -1) ) );
         else
            srv_gaewolsu = 0;
         srv_tod = DateTime.addDays(DateTime.addMonths(bwmVo.getCebwmusd(), mmn), -1);

         basGaewol = 0;

         for(int j=1;j <= srv_gaewolsu; j++)
         {
            TBEBXLF1Vo rtnVo = new TBEBXLF1Vo();
            if(j==1)
               fdt = bwmVo.getCebwmusd();
            else
               fdt = DateTime.addMonths(bwmVo.getCebwmusd(), basGaewol).substring(0,6) + "01";

            if(j == srv_gaewolsu)
               tdt = srv_tod;
            else
               tdt = DateTime.lastDayOfMonth(fdt);

            mangunFlg = chkMangun(fdt, tdt);
            calcDays = DateTime.daysBetween(fdt, tdt) + 1;
            if(mangunFlg)
               jdq = 30;
            else
               jdq = calcDays;
            /****2006.07.29 mhcho 추가 cebxlydt를 구하기 위해****************/
            String tis  = bwmVo.getCebwmtis();
            String ydt  = "";

            if(Integer.parseInt(fdt) <= Integer.parseInt(CommonUtil.getToday2())){
               if(tis.equals("99")) ydt   = CommonUtil.getToday2().substring(0,6)+CommonUtil.getMonthPerDay(CommonUtil.getToday2().substring(0,6).substring(0,6));
               else ydt = CommonUtil.getToday2().substring(0,6)+tis;
            }else{
               if(tis.equals("99")) ydt   = fdt.substring(0,6)+CommonUtil.getMonthPerDay(fdt.substring(0,6));
               else ydt = fdt.substring(0,6)+tis;
            }
            /***************************************************************/

            //2014.06.23 LJH 무상기간 데이터 생성 제외
            //list.add(setBxlVoByMaechul(fdt, tdt, jdq, 0, 0, 0, "", ydt,0,0)); //20121106_lhr
            basGaewol++;
         }

         // 2. 유상계약(개시일) 기간에 대한 처리
         basGaewol = 0;
         //bjg = Integer.parseInt(bwmVo.getCebwmbjg());
         bjg = 1;
         if(bjg == 4) {
        	 bjg = 6;
         } else if(bjg == 5) {
        	 bjg = 12;
         } else {
        	 bjg = bjg;
         }
         String gubun = "";
         baljuStd = bwmVo.getCebwmugs();
         gaewolsu = DateTime.monthsBetween(baljuStd.substring(0,6)+"01", bwmVo.getCebwmumr().substring(0,6)+DateTime.lastDayOfMonth(bwmVo.getCebwmumr()).substring(6,8));
         gaewolsu = gaewolsu / bjg;
         basGaewol = bjg;
         
         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXLF1Vo rtnVo = new TBEBXLF1Vo();
            if(i == 1)
            {
               fdt = baljuStd;
               gubun = "1";
            }
            else
               fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";

			if (i == gaewolsu)
				tdt = bwmVo.getCebwmumr();
			else
				tdt = DateTime.lastDayOfMonth(DateTime.addMonths(fdt, bjg - 1));
            
            if(i == 1) {
                mangunFlg = chkMangun(fdt, tdt);
                calcDays = DateTime.daysBetween(fdt, tdt) + 1;

                if(mangunFlg) {
	                jdq = 30 * bjg;
	                if(bjg == 12) {
	             	   jdq = 365;
	                }
                } else {
                	jdq = calcDays;
                }
                
            	if(fdt.substring(6,8).equals("01")) {
                    amd = Double.parseDouble(bwmVo.getCebwmamt());
                    hmd = Double.parseDouble(bwmVo.getCebwmhmt());
                    dmd = Double.parseDouble(bwmVo.getCebwmdmt());
                    vad = Double.parseDouble(bwmVo.getCebwmvat());
                    tod = Double.parseDouble(bwmVo.getCebwmtot());
                    
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    acmd = Double.parseDouble(bwmVo.getCebwmacmt());
                    //======================================================================================
            	} else {
            		cdq = Integer.parseInt(DateTime.lastDayOfMonth(tdt).substring(6,8));
/*
            		cdq = 30 * bjg;
            		if(bjg == 12) {
         			   cdq = 365;
                    }
*/
                    amd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmamt()) / cdq ) * jdq);
                    hmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmhmt()) / cdq ) * jdq);
                    dmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmdmt()) / cdq ) * jdq);
                    vad = StrictMath.round((Double.parseDouble(bwmVo.getCebwmvat()) / cdq ) * jdq);
                    tod = StrictMath.round((Double.parseDouble(bwmVo.getCebwmtot()) / cdq ) * jdq);
                    
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    acmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmacmt()) / cdq ) * jdq);
                    //======================================================================================                    
                    
                    t_amd = amd;
                    t_hmd = hmd;
                    t_dmd = dmd;
                    t_vad = vad;
                    t_tod = tod;
                    
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    t_acmd = acmd;
                    //======================================================================================
            	}
            } else if(i == gaewolsu) {
            	mangunFlg = chkMangun(fdt, tdt);
                calcDays = DateTime.daysBetween(fdt, tdt) + 1;
                
                if(mangunFlg) {
                	jdq = 30 * bjg;
	                if(bjg == 12) {
	             	   jdq = 365;
	                }
                } else {
                    jdq = calcDays;
                }

                if(bwmVo.getCebwmumr().equals(DateTime.lastDayOfMonth(bwmVo.getCebwmumr()))) {
                	amd = Double.parseDouble(bwmVo.getCebwmamt());
                    hmd = Double.parseDouble(bwmVo.getCebwmhmt());
                    dmd = Double.parseDouble(bwmVo.getCebwmdmt());
                    vad = Double.parseDouble(bwmVo.getCebwmvat());
                    tod = Double.parseDouble(bwmVo.getCebwmtot());
                    
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    acmd = Double.parseDouble(bwmVo.getCebwmacmt());
                    //======================================================================================
                } else {
                	cdq = Integer.parseInt(DateTime.lastDayOfMonth(tdt).substring(6,8));
/*
                	cdq = 30 * bjg;
            		if(bjg == 12) {
         			   cdq = 365;
                    }
*/
                    amd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmamt()) / cdq ) * jdq);
                    hmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmhmt()) / cdq ) * jdq);
                    dmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmdmt()) / cdq ) * jdq);
                    vad = StrictMath.round((Double.parseDouble(bwmVo.getCebwmvat()) / cdq ) * jdq);
                    tod = StrictMath.round((Double.parseDouble(bwmVo.getCebwmtot()) / cdq ) * jdq);
                    
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    acmd = StrictMath.round((Double.parseDouble(bwmVo.getCebwmacmt()) / cdq ) * jdq);
                    //======================================================================================
                    
                    amd = Double.parseDouble(bwmVo.getCebwmamt()) - t_amd;
                    hmd = Double.parseDouble(bwmVo.getCebwmhmt()) - t_hmd;
                    dmd = Double.parseDouble(bwmVo.getCebwmdmt()) - t_dmd;
                    vad = Double.parseDouble(bwmVo.getCebwmvat()) - t_vad;
                    tod = Double.parseDouble(bwmVo.getCebwmtot()) - t_tod;
                    
                    //===========================공기청정기 추가 20200515 Han.JH==================================
                    acmd = Double.parseDouble(bwmVo.getCebwmacmt()) - t_acmd;
                    //======================================================================================
                }
            } else {
//            	jdq = 30;
            	jdq = 30 * bjg;
                if(bjg == 12) {
             	   jdq = 365;
                }
            	amd = Double.parseDouble(bwmVo.getCebwmamt());
                hmd = Double.parseDouble(bwmVo.getCebwmhmt());
                dmd = Double.parseDouble(bwmVo.getCebwmdmt());
                vad = Double.parseDouble(bwmVo.getCebwmvat());
                tod = Double.parseDouble(bwmVo.getCebwmtot());
                
                //===========================공기청정기 추가 20200515 Han.JH==================================
                acmd = Double.parseDouble(bwmVo.getCebwmacmt());
                //======================================================================================
            }
            
            /****2006.07.29 mhcho 추가 cebxlydt를 구하기 위해****************/
            String tis  = bwmVo.getCebwmtis();
            String ydt  = "";

            if(Integer.parseInt(fdt) <= Integer.parseInt(CommonUtil.getToday2())){
               if(tis.equals("99")) ydt   = CommonUtil.getToday2().substring(0,6)+CommonUtil.getMonthPerDay(CommonUtil.getToday2().substring(0,6).substring(0,6));
               else ydt = CommonUtil.getToday2().substring(0,6)+tis;
            }else{
               if(tis.equals("99")) ydt   = fdt.substring(0,6)+CommonUtil.getMonthPerDay(fdt.substring(0,6));
               else ydt = fdt.substring(0,6)+tis;
            }
            /***************************************************************/
            list.add(setBxlVoByMaechul(fdt, tdt, jdq, amd, vad, tod, gubun, ydt, hmd, dmd, acmd)); //20121106_lhr
//            basGaewol++;
            if(i > 1) {
            	basGaewol = basGaewol + bjg;
            }
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("유상일반 매출액 산출 오류가 있습니다");
      }
      return list;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-13
     * 설  명: 인원상주 에 대한 매출액 산출
     * 기  타:
     */
   public ArrayList getCalcMaechul4(TBEBWNF1Vo bwnVo) throws BizException
   {
      ArrayList list = new ArrayList();

      int      gaewolsu = 0;
      double   baseMaechul = 0;
      boolean  mangunFlg = false;
      int      calcDays = 0;

      String fdt = "";
      String tdt = "";
      int jdq = 0;
	  int cdq = 0;
      int amd = 0;
      int t_amd = 0;
      int calcAmd = 0;
      int vad = 0;
      int t_vad = 0;
      int calcVad = 0;
      int tod = 0;
      int t_tod = 0;
      int calcTod = 0;
      int bjg = 0;

      try
      {
         //bjg = Integer.parseInt(bwnVo.getCebwnbjg());
         bjg = 1;
         if(bjg == 4) {
        	 bjg = 6;
         } else if(bjg == 5) {
        	 bjg = 12;
         } else {
        	 bjg = bjg;
         }
    	 gaewolsu = DateTime.monthsBetween(bwnVo.getCebwnsfr().substring(0,6)+"01", bwnVo.getCebwnsto().substring(0,6)+DateTime.lastDayOfMonth(bwnVo.getCebwnsto()).substring(6,8));
    	 gaewolsu = gaewolsu / bjg;
    	 
         int basGaewol = 0;
         basGaewol = bjg;
         String gubun  = "";
         for(int i=1;i <= gaewolsu; i++)
         {
            TBEBXLF1Vo rtnVo = new TBEBXLF1Vo();

            if(i==1)
            {
               fdt = bwnVo.getCebwnsfr();
               gubun = "1";
            }
            else
               fdt = DateTime.addMonths(bwnVo.getCebwnsfr(), basGaewol).substring(0,6) + "01";

            if(i == gaewolsu)
               tdt = bwnVo.getCebwnsto();
            else
//               tdt = DateTime.lastDayOfMonth(fdt);
               tdt = DateTime.lastDayOfMonth(DateTime.addMonths(fdt, bjg - 1));

            mangunFlg = chkMangun(fdt, tdt);
            calcDays = DateTime.daysBetween(fdt, tdt) + 1;
            calcAmd = Integer.parseInt(bwnVo.getCebwnamt()) + Integer.parseInt(bwnVo.getCebwncqt());
            calcVad = Integer.parseInt(bwnVo.getCebwnvat());
            calcTod = Integer.parseInt(bwnVo.getCebwntot());
            if(mangunFlg)
            {
//               jdq = 30;
               jdq = 30 * bjg;
               if(bjg == 12) {
            	   jdq = 365;
               }
               amd = calcAmd;
               vad = calcVad;
               tod = calcTod;
            }
            else
            {
			   //만료월(tdt)의 총 일수 by ohb 2006.08.28
            	// 2021.08.12 일자 산출 시 일자가 아닌 YYYYMMDD 형태로 도출되어 인원상주 첫달 매출계산 0으로 오생성 되던 문제 처리. Han J.H
    		   cdq = Integer.parseInt(DateTime.lastDayOfMonth(tdt).substring(6,8));
/*
    		   cdq = 30 * bjg;
    		   if(bjg == 12) {
    			   cdq = 365;
               }
*/
    		   jdq = calcDays;
//               amd = Math.round(new Double((calcAmd / cdq) * jdq).intValue());
//               vad = Math.round(new Double((calcVad / cdq) * jdq).intValue());
//               tod = Math.round(new Double((calcTod / cdq) * jdq).intValue());
//               amd = Math.round(new Double((calcAmd / 30) * jdq).intValue());
//               vad = Math.round(new Double((calcVad / 30) * jdq).intValue());
//               tod = Math.round(new Double((calcTod / 30) * jdq).intValue());
               amd = Math.round(new Double((calcAmd / cdq) * jdq).intValue());
               vad = Math.round(new Double((calcVad / cdq) * jdq).intValue());
               tod = Math.round(new Double((calcTod / cdq) * jdq).intValue());
               
               if(i == 1) {
                  t_amd = amd;
                  t_vad = vad;
                  t_tod = tod;
 	           }
 	           if(i == gaewolsu && !bwnVo.getCebwnsto().equals(DateTime.lastDayOfMonth(bwnVo.getCebwnsto()))) {
 	        	  amd = calcAmd - t_amd;
 	        	  vad = calcVad - t_vad;
 	        	  tod = calcTod - t_tod;
 	           }
            }
            /****2006.07.29 mhcho 추가 cebxlydt를 구하기 위해****************/
            String tis  = bwnVo.getImsitis();
            String ydt  = "";

            if(Integer.parseInt(fdt) <= Integer.parseInt(CommonUtil.getToday2())){
               if(tis.equals("99")) ydt   = CommonUtil.getToday2().substring(0,6)+CommonUtil.getMonthPerDay(CommonUtil.getToday2().substring(0,6).substring(0,6));
               else ydt = CommonUtil.getToday2().substring(0,6)+tis;
            }else{
               if(tis.equals("99")) ydt   = fdt.substring(0,6)+CommonUtil.getMonthPerDay(fdt.substring(0,6));
               else ydt = fdt.substring(0,6)+tis;
            }
			/***************************************************************/
            list.add(setBxlVoByMaechul(fdt, tdt, jdq, amd, vad, tod, gubun, ydt, 0, 0, 0));
//            basGaewol++;
            if(i > 1) {
            	basGaewol = basGaewol + bjg;
            }
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         throw new BizException("인원상주 매출액 산출 오류가 있습니다");
      }
      return list;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-30
     * 설  명: 무상발주에 대한 기성비 자료 세팅
     * 기  타: 인센티브가 포함 안됨.
     */
   public TBEBXRF1Vo setBxrVoByGisungbi1(String fdt, String tod, double jdq, double bda, double bya, double is4) throws BizException
   {
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      vo.setCebxrfdt(fdt);
      vo.setCebxrtod(tod);
      vo.setCebxrjdq(new Double(jdq).toString());
      vo.setCebxrbda(new Double(bda).toString());
      vo.setCebxrjyq(new Double(jdq).toString());

	  //2006.09.13 bya, bam 값은 bda 값으로 넣는다. 다른 경우는 없음 by ohb
//	  vo.setCebxrbya(vo.getCebxrbda());
//	  vo.setCebxrbam(vo.getCebxrbda());

      //2008.05.10 LJH 수정(신두식 이사님 요청사항)
      vo.setCebxrbya(new Double(bya).toString());
	  vo.setCebxrbam(new Double(bya).toString());

	  vo.setCebxris4(new Double(is4).toString());
      return vo;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-30
     * 설  명: 유발주에 대한 기성비 자료 세팅
     * 기  타: 인센티브 1,2,3,4가 포함 됨.
     */
   public TBEBXRF1Vo setBxrVoByGisungbi2(String fdt, String tod, double jdq, double bda, double bya, double is1,double is2,double is3,double is4, double hda, double hya, double dda, double dya, double acda, double acya) throws BizException
   {
      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      vo.setCebxrfdt(fdt);
      vo.setCebxrtod(tod);
      vo.setCebxrjdq(new Double(jdq).toString());
      vo.setCebxrbda(new Double(bda).toString());
      vo.setCebxrhda(new Double(hda).toString());
      vo.setCebxrdda(new Double(dda).toString());
      vo.setCebxrjyq(new Double(jdq).toString());
      //===========================공기청정기 추가 20200515 Han.JH==================================
      vo.setCebxracda(new Double(acda).toString());
      //======================================================================================

      //2006.09.13 bya, bam 값은 bda 값으로 넣는다. 다른 경우는 없음 by ohb
//      vo.setCebxrbya(vo.getCebxrbda());
//	  vo.setCebxrbam(vo.getCebxrbda());
      vo.setCebxrbya(new Double(bya).toString());
      vo.setCebxrhya(new Double(hya).toString());
      vo.setCebxrdya(new Double(dya).toString());
      //===========================공기청정기 추가 20200515 Han.JH==================================
      vo.setCebxracya(new Double(acya).toString());
      //======================================================================================      
	  vo.setCebxrbam(new Double(bya).toString());
	  vo.setCebxrham(new Double(hya).toString());
	  vo.setCebxrdam(new Double(dya).toString());
      //===========================공기청정기 추가 20200515 Han.JH==================================
	  vo.setCebxracam(new Double(acya).toString());
      //======================================================================================	  
      vo.setCebxris1(new Double(is1).toString());
      vo.setCebxris2(new Double(is2).toString());
      vo.setCebxris3(new Double(is3).toString());
      vo.setCebxris4(new Double(is4).toString());

      return vo;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-08
     * 설  명:
     * 기  타:
     */
   public TBEBXLF1Vo setBxlVoByMaechul(String fdt, String tdt, int jdq, double amd, double vad, double tod, String gubun,String ydt, double hmd, double dmd, double acmd) throws BizException
   {
      TBEBXLF1Vo vo = new TBEBXLF1Vo();
      vo.setCebxlfdt(fdt);
      vo.setCebxltdt(tdt);
      vo.setCebxljdq(new Integer(jdq).toString());
      vo.setCebxlamd(new Double(amd).toString());
      vo.setCebxlhmd(new Double(hmd).toString());
      vo.setCebxldmd(new Double(dmd).toString());
      vo.setCebxlvad(new Double(vad).toString());
      vo.setCebxltod(new Double(tod).toString());
//      vo.setCebxlamt(vo.getCebxlamd());
//      vo.setCebxlvat(vo.getCebxlvad());
//      vo.setCebxltot(vo.getCebxltod());
      vo.setCebxlamt(new Double(amd).toString());
      vo.setCebxlhmt(new Double(hmd).toString());
      vo.setCebxldmt(new Double(dmd).toString());
      vo.setCebxlvat(new Double(vad).toString());
      vo.setCebxltot(new Double(tod).toString());
      vo.setGubun(gubun);
      vo.setCebxlydt(ydt);
      
      //===========================공기청정기 추가 20200515 Han.JH==================================
      vo.setCebxlacmd(new Double(acmd).toString());
      vo.setCebxlacmt(new Double(acmd).toString());
      //======================================================================================

      return vo;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-30
     * 설  명:
     * 기  타:
     */
   public int saveBxrForBweA(TBEBWEF1Vo bweVo, ArrayList list) throws BizException, Exception
   {
      int rtnCode = -1;
      ArrayList alist = new ArrayList();
      TBEBXRF1Vo vo ;
      try
      {
         for(int i=0; i<list.size(); i++)
         {
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            vo = bweVo2BXR(vo, bweVo);
            alist.add(vo);
         }
         TBEBXRF1Dao dao = new TBEBXRF1Dao();
         dao.insert(alist, "ComMethodForAB");
      }
      catch(BizException be)
      {
         rtnCode = -1;
         throw be;
      }
      catch(Exception e)
      {
         rtnCode = -1;
         throw e;
      }
      finally
      {
         rtnCode = 1;
      }
      return rtnCode;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-30
     * 설  명:
     * 기  타:
     */
   public int saveBxrForBweB(TBEBWEF1Vo bweVo, ArrayList list) throws BizException, Exception
   {
      int rtnCode = -1;
      ArrayList alist = new ArrayList();
      TBEBXRF1Vo vo ;
      try
      {
         for(int i=0; i<list.size(); i++)
         {
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            vo = bweVo2BXR(vo, bweVo);
            alist.add(vo);
         }
         TBEBXRF1Dao dao = new TBEBXRF1Dao();
         dao.insert(alist, "ComMethodForAB");
      }
      catch(BizException be)
      {
         rtnCode = -1;
         throw be;
      }
      catch(Exception e)
      {
         rtnCode = -1;
         throw e;
      }
      finally
      {
         rtnCode = 1;
      }
      return rtnCode;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-04
     * 설  명:
     * 기  타:
     */
   public int saveBxrForBwmC(TBEBWMF1Vo bwmVo, ArrayList list) throws BizException, Exception
   {
      int rtnCode = -1;
      ArrayList alist = new ArrayList();
      TBEBXRF1Vo vo ;
      try
      {
         for(int i=0; i<list.size(); i++)
         {
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            vo = bwmVo2BXR(vo, bwmVo);
            alist.add(vo);
         }
         TBEBXRF1Dao dao = new TBEBXRF1Dao();
         dao.insert(alist, "ComMethodForC");
      }
      catch(BizException be)
      {
         rtnCode = -1;
         throw be;
      }
      catch(Exception e)
      {
         rtnCode = -1;
         throw e;
      }
      finally
      {
         rtnCode = 1;
      }
      return rtnCode;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-04
     * 설  명:
     * 기  타:
     */
   public int saveBxrForBwmD(TBEBWMF1Vo bwmVo, ArrayList list) throws BizException, Exception
   {
      int rtnCode = -1;
      ArrayList alist = new ArrayList();
      TBEBXRF1Vo vo ;
      try
      {
         for(int i=0; i<list.size(); i++)
         {
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            vo = bwmVo2BXR(vo, bwmVo);
            alist.add(vo);
         }
         TBEBXRF1Dao dao = new TBEBXRF1Dao();
         dao.insert(alist, "ComMethodForD");
      }
      catch(BizException be)
      {
         rtnCode = -1;
         throw be;
      }
      catch(Exception e)
      {
         rtnCode = -1;
         throw e;
      }
      finally
      {
         rtnCode = 1;
      }
      return rtnCode;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-04
     * 설  명:
     * 기  타:
     */
   public int saveBxlForBwmC(TBEBWMF1Vo bwmVo, ArrayList list) throws BizException, Exception
   {
      int rtnCode = -1;
      ArrayList alist = new ArrayList();
      TBEBXLF1Vo bxlVo ;
      try
      {
         double amd = 0;
         double vad = 0;
         double tod = 0;

         double basAmd = 0;
         double basVad = 0;
         double basTod = 0;

         for(int i=0; i<list.size(); i++)
         {
            bxlVo = new TBEBXLF1Vo();
            bxlVo = (TBEBXLF1Vo) list.get(i);

            // 2006.08.29 주석처리(불필요) by ohhb
            /*
            if("1".equals(bxlVo.getGubun()) && !"01".equals(bxlVo.getCebxlfdt().substring(6,8)) )
            {
               amd = Double.parseDouble(bxlVo.getCebxlamd());
               vad = Double.parseDouble(bxlVo.getCebxlvad());
               tod = Double.parseDouble(bxlVo.getCebxltod());
            }

            if(chkMangun(bxlVo.getCebxlfdt(), bxlVo.getCebxltdt()))
            {
               basAmd = Double.parseDouble(bxlVo.getCebxlamd());
               basVad = Double.parseDouble(bxlVo.getCebxlvad());
               basTod = Double.parseDouble(bxlVo.getCebxltod());
            }

            if(i == list.size()-1 && !chkMangun(bxlVo.getCebxlfdt(), bxlVo.getCebxltdt()))
            {
               System.out.println("amd : " + amd);
               System.out.println("vad : " + vad);
               System.out.println("tod : " + tod);
               System.out.println("basAmd : " + basAmd);
               System.out.println("basVad : " + basVad);
               System.out.println("basVad : " + basVad);
               bxlVo.setCebxlamd(new Double(basAmd - amd).toString());
               bxlVo.setCebxlvad(new Double(basVad - vad).toString());
               bxlVo.setCebxltod(new Double(basTod - tod).toString());
            }
            */

            bxlVo.setCebxlyym(bxlVo.getCebxlydt().substring(0,6));

            bxlVo = bwmVo2BXL(bxlVo, bwmVo);

            alist.add(bxlVo);
         }
         TBEBXLF1Dao dao = new TBEBXLF1Dao();
         dao.insert(alist, "ComMethodForC");
      }
      catch(BizException be)
      {
         rtnCode = -1;
         throw be;
      }
      catch(Exception e)
      {
         rtnCode = -1;
         throw e;
      }
      finally
      {
         rtnCode = 1;
      }
      return rtnCode;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-04
     * 설  명:
     * 기  타:
     */
   public int saveBxlForBwn(TBEBWNF1Vo bwnVo, ArrayList list) throws BizException, Exception
   {
      int rtnCode = -1;
      ArrayList alist = new ArrayList();
      TBEBXLF1Vo bxlVo ;
      try
      {
         for(int i=0; i<list.size(); i++)
         {
            bxlVo = new TBEBXLF1Vo();
            bxlVo = (TBEBXLF1Vo) list.get(i);

            bxlVo = bwnVo2BXL(bxlVo, bwnVo);
            alist.add(bxlVo);
         }
         TBEBXLF1Dao dao = new TBEBXLF1Dao();
         dao.insert(alist, "ComMethodForETC");
      }
      catch(BizException be)
      {
         rtnCode = -1;
         throw be;
      }
      catch(Exception e)
      {
         rtnCode = -1;
         throw e;
      }
      finally
      {
         rtnCode = 1;
      }
      return rtnCode;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-13
     * 설  명:
     * 기  타:
     */
   public int saveBxrForBwn(TBEBWNF1Vo bwnVo, ArrayList list) throws BizException, Exception
   {
      int rtnCode = -1;
      ArrayList alist = new ArrayList();
      TBEBXRF1Vo vo ;
      try
      {
         for(int i=0; i<list.size(); i++)
         {
            vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) list.get(i);
            /*2006.07.31 mhcho 인원상주시 발주종류 'E' 추가*/
            bwnVo.setCebwngnd("E");
            vo = bwnVo2BXR(vo, bwnVo);
            alist.add(vo);
         }
         TBEBXRF1Dao dao = new TBEBXRF1Dao();
         dao.insert(alist, "ComMethodForETC");
      }
      catch(BizException be)
      {
         rtnCode = -1;
         throw be;
      }
      catch(Exception e)
      {
         rtnCode = -1;
         throw e;
      }
      finally
      {
         rtnCode = 1;
      }
      return rtnCode;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-30
     * 설  명: 무상일반 발주시에 공사발주내용이 있다면 이를 취소처리 또는 잔여기간에 대한 재 생성 하는 메소드
     * 기  타:
     */
   public int chkExistData1(TBEBWEF1Vo bweVo) throws BizException, Exception
   {
      int rtnCode = -1;

      TBEBXRF1Vo vo = new TBEBXRF1Vo();

      String bwebgt  = bweVo.getCebwebgt();
      vo.setMandt(bweVo.getMandt());
      vo.setCebxrpjt(bweVo.getCebwepjt());
      vo.setCebxrhno(bweVo.getCebwehno());
      vo.setCebxrseq(bweVo.getCebweseq());
      vo.setCebxrgnd("A");
      vo.setCebxrjym(bwebgt.substring(0,6));
      vo.setCebxrupn(bweVo.getCebwepjt());
      vo.setCebxrcst("");
      vo.setCebxrbdgbn(bweVo.getCebwebdgbn());

      //  무상공사가 진행중이면 기성비 취소처리하고 잔여일수 여부에 따라 재 생성 하여줌.
      TBEBXRF1Dao dao = new TBEBXRF1Dao();
      ArrayList paramList = new ArrayList();
      paramList.add(vo);
      ArrayList alist = (ArrayList) dao.select(paramList, "ComMethod");
      if(alist.size() > 0)
      {
         TBEBXRF1Vo reVo = new TBEBXRF1Vo();
         reVo = (TBEBXRF1Vo) alist.get(0);
         dao.update(alist, "ComMethodForCancelForA");

         if(!bwebgt.substring(6,8).equals("01"))
         {
            int jdq = DateTime.daysBetween(bwebgt.substring(0,6)+"01", bwebgt);
            reVo.setMandt(reVo.getMandt());
            reVo.setCebxrfdt(bwebgt.substring(0,6)+"01");
            reVo.setCebxrtod(DateTime.addDays(bwebgt, -1));
            reVo.setCebxrbya(new Integer((Integer.parseInt(reVo.getCebxrbda()) / 30 ) * jdq).toString());
            reVo.setCebxrjdq(new Integer(jdq).toString());
            reVo.setCebxrjyq(reVo.getCebxrjdq());
            reVo.setCebxridq(reVo.getCebxrjdq());
            reVo.setCebxrbam(reVo.getCebxrbya());
            alist = new ArrayList();
            alist.add(reVo);
            dao.insert(alist,"ComMethodForAB");
         }
      }

      //  유상공사가 진행중이면 기성비 취소처리하고 잔여일수 여부에 따라 재 생성 하여줌.
      vo.setCebxrgnd("C");
      paramList.add(vo);
      alist = (ArrayList) dao.select(paramList, "ComMethod");
      if(alist.size() > 0)
      {
         TBEBXRF1Vo reVo = new TBEBXRF1Vo();
         reVo = (TBEBXRF1Vo) alist.get(0);
         dao.update(alist, "ComMethodForCancelForC");

         if(!bwebgt.substring(6,8).equals("01"))
         {
            int jdq = DateTime.daysBetween(bwebgt.substring(0,6)+"01", bwebgt);
            reVo.setCebxrfdt(bwebgt.substring(0,6)+"01");
            reVo.setCebxrtod(DateTime.addDays(bwebgt, -1));
            reVo.setCebxrbya(new Integer((Integer.parseInt(reVo.getCebxrbda()) / 30 ) * jdq).toString());
            reVo.setCebxrjdq(new Integer(jdq).toString());
            reVo.setCebxrjyq(reVo.getCebxrjdq());
            reVo.setCebxridq(reVo.getCebxrjdq());
            reVo.setCebxrbam(reVo.getCebxrbya());
            reVo.setMandt(reVo.getMandt());
            alist = new ArrayList();
            alist.add(reVo);
            dao.insert(alist,"ComMethodForC");
         }
      }

      // 유상공사가 진행중이면 매출액 취소처리하고 잔여일수 여부에 따라 재 생성 하여줌.
      TBEBXLF1Dao bxlDao = new TBEBXLF1Dao();
      TBEBXLF1Vo bxlVo = new TBEBXLF1Vo();
      bxlVo.setCebxlupn(""); // 무상발주정보에는 UPN와 CST가 존재하지 않음 .  2006.05.23
      bxlVo.setCebxlcst(""); // 무상발주정보에는 UPN와 CST가 존재하지 않음 .  2006.05.23
      bxlVo.setCebxlpjt(bweVo.getCebwepjt());
      bxlVo.setCebxlhno(bweVo.getCebwehno());
      bxlVo.setCebxlseq("");
      bxlVo.setCebxlmym(bwebgt.substring(0,6));
      bxlVo.setCebxlgnd("C");
      bxlVo.setMandt(bweVo.getMandt());
      paramList = new ArrayList();
      paramList.add(bxlVo);
      alist = (ArrayList) bxlDao.select(paramList, "ComMethod");

      if(alist.size() > 0)
      {
         TBEBXLF1Vo reVo = new TBEBXLF1Vo();
         reVo = (TBEBXLF1Vo) alist.get(0);
         bxlDao.update(alist, "ComMethodForCancelForC");

         if(!bwebgt.substring(6,8).equals("01"))
         {
            int jdq = DateTime.daysBetween(bwebgt.substring(0,6)+"01", bwebgt);
            reVo.setCebxlfdt(bwebgt.substring(0,6)+"01");
            reVo.setCebxltdt(DateTime.addDays(bwebgt, -1));

            //2006.09.16 매출계획은 일자계산 시 해당월의 총 월수가 분모가 되어야 함
			int cdq = Integer.parseInt(DateTime.lastDayOfMonth(reVo.getCebxltdt().substring(0,6)).substring(6,8));

            reVo.setCebxlamd(new Integer((Integer.parseInt(reVo.getCebxlamd()) / cdq ) * jdq).toString());
            reVo.setCebxlvad(new Integer((Integer.parseInt(reVo.getCebxlvad()) / cdq ) * jdq).toString());
            reVo.setCebxltod(new Integer((Integer.parseInt(reVo.getCebxltod()) / cdq ) * jdq).toString());
            reVo.setCebxljdq(new Integer(jdq).toString());

            alist = new ArrayList();
            alist.add(reVo);
            bxlDao.insert(alist,"ComMethodForC");
         }
      }
      return rtnCode;
   }

   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-03
     * 설  명: 유상공사 발주시에 무상공사발주내용이 있다면 이를 취소처리 또는 잔여기간에 대해 재 생성 하는 메소드
     * 기  타:
     */
   public int chkExistData2(TBEBWMF1Vo bwmVo) throws BizException, Exception
   {
      int rtnCode = -1;

      TBEBXRF1Vo vo = new TBEBXRF1Vo();

      String bwmusd  = bwmVo.getCebwmusd();
      String bwmums  = bwmVo.getCebwmums();
      String bwmumr  = bwmVo.getCebwmumr();

      double calcDays = 0;
      double jdq = 0;
      double bya = 0;
      double t_bya = 0;

      String  tod = "";
      String  fdt = "";

      vo.setMandt(bwmVo.getMandt());
      vo.setCebxrpjt(bwmVo.getCebwmpjt());
//      vo.setCebxrhgb(bwmVo.getCebwmhgb());
      vo.setCebxrhno(bwmVo.getCebwmhno());
      vo.setCebxrseq(bwmVo.getCebwmseq());
      vo.setCebxrgnd("A");
      vo.setCebxrjym(bwmusd.substring(0,6));
      vo.setCebxrupn(bwmVo.getCebwmupn());
      vo.setCebxrcst(bwmVo.getCebwmcst());
      vo.setCebxrbdgbn(bwmVo.getCebwmbdgbn());

      //  무상공사가 진행중이면 취소처리하고 잔여일수 여부에 따라 재 생성 하여줌.
      TBEBXRF1Dao dao = new TBEBXRF1Dao();
      ArrayList paramList = new ArrayList();
      paramList.add(vo);
      ArrayList alist = (ArrayList) dao.select(paramList, "ComMethod");
      dao.update(alist, "ComMethodForCancelForA");

      int arrSize = 0;
      if("01".equals(bwmusd.substring(6,8)))
         arrSize = alist.size() + 1;
      else
         arrSize = alist.size();
      for(int i=1; i<=alist.size();i++)
      {
         TBEBXRF1Vo newVo = new TBEBXRF1Vo();
         newVo = (TBEBXRF1Vo) alist.get(i-1);
         if(i==1)
         {
            if(!"01".equals(bwmusd.substring(6,8)))  // 날짜가 잘렸을경우 잔여일수에 대한 처리.
            {
               ArrayList saveList = new ArrayList();

               String startDt = ((TBEBXRF1Vo) alist.get(0)).getCebxrfdt();
               String endDt = DateTime.addDays(bwmusd, -1);
               double bda = Double.parseDouble(((TBEBXRF1Vo) alist.get(0)).getCebxrbda());
               calcDays = DateTime.daysBetween(startDt, endDt) + 1;
               jdq = calcDays;
               bya = (bda / 30) * jdq;
               TBEBXRF1Vo tmpVo1 = newVo;
               tmpVo1.setCebxrfdt(startDt);
               tmpVo1.setCebxrtod(endDt);
               tmpVo1.setCebxrjdq(new Double(jdq).toString());
               tmpVo1.setCebxrbya(new Double(bya).toString());
               tmpVo1.setCebxrjym(startDt.substring(0,6));
               /*2006.08.02 mhcho 수정*/
               //tmpVo1.setCebxrgym(startDt.substring(0,6));
               if(Integer.parseInt(CommonUtil.getToday2()) >= Integer.parseInt(startDt)){
                  tmpVo1.setCebxrgym(CommonUtil.getToday2().substring(0,6));
               }else{
                  tmpVo1.setCebxrgym(startDt.substring(0,6));
               }
               /*2006.08.02 mhcho 수정*/

               saveList.add(tmpVo1);
               dao.insert(saveList,"ComMethodForAB");

               startDt = DateTime.addMonths(bwmusd,Integer.parseInt(bwmums)) ;
               endDt = DateTime.lastDayOfMonth(startDt);
               bda = Double.parseDouble(((TBEBXRF1Vo) alist.get(0)).getCebxrbda());
               calcDays = DateTime.daysBetween(startDt, endDt) + 1;
               jdq = calcDays;
               bya = (bda / 30) * jdq;

               TBEBXRF1Vo tmpVo2 = newVo;
               tmpVo2.setCebxrfdt(startDt);
               tmpVo2.setCebxrtod(endDt);
               tmpVo2.setCebxrjdq(new Double(jdq).toString());
               tmpVo2.setCebxrbya(new Double(bya).toString());
               tmpVo2.setCebxrjym(startDt.substring(0,6));

               /*2006.08.02 mhcho 수정*/
               //tmpVo2.setCebxrgym(startDt.substring(0,6));
               if(Integer.parseInt(CommonUtil.getToday2()) >= Integer.parseInt(startDt)){
                  tmpVo2.setCebxrgym(CommonUtil.getToday2().substring(0,6));
               }else{
                  tmpVo2.setCebxrgym(startDt.substring(0,6));
               }
               /*2006.08.02 mhcho 수정*/
               saveList = new ArrayList();
               saveList.add(tmpVo2);
               dao.insert(saveList,"ComMethodForAB");
            }
            else
            {
               String startDt = DateTime.addMonths(newVo.getCebxrfdt() , Integer.parseInt(bwmums));
               String endDt = DateTime.lastDayOfMonth(startDt);
               newVo.setCebxrfdt(startDt);
               newVo.setCebxrtod(endDt);
               newVo.setCebxrjym(startDt.substring(0,6));

               /*2006.08.02 mhcho 수정*/
               //newVo.setCebxrgym(startDt.substring(0,6));
               if(Integer.parseInt(CommonUtil.getToday2()) >= Integer.parseInt(startDt)){
                  newVo.setCebxrgym(CommonUtil.getToday2().substring(0,6));
               }else{
                  newVo.setCebxrgym(startDt.substring(0,6));
               }
               /*2006.08.02 mhcho 수정*/
               ArrayList saveList = new ArrayList();
               saveList.add(newVo);
               dao.insert(saveList,"ComMethodForAB");
            }

         }
         else
         {
            String startDt = DateTime.addMonths(newVo.getCebxrfdt() , Integer.parseInt(bwmums));
            String endDt = "";
            if(i==alist.size())
               endDt = DateTime.addMonths(newVo.getCebxrtod() , Integer.parseInt(bwmums));
            else
               endDt = DateTime.lastDayOfMonth(startDt);

            newVo.setCebxrfdt(startDt);
            newVo.setCebxrtod(endDt);
            newVo.setCebxrjym(startDt.substring(0,6));
            /*2006.08.02 mhcho 수정*/
            //newVo.setCebxrgym(startDt.substring(0,6));
            if(Integer.parseInt(CommonUtil.getToday2()) >= Integer.parseInt(startDt)){
               newVo.setCebxrgym(CommonUtil.getToday2().substring(0,6));
            }else{
               newVo.setCebxrgym(startDt.substring(0,6));
            }
            /*2006.08.02 mhcho 수정*/
            ArrayList saveList = new ArrayList();
            saveList.add(newVo);
            dao.insert(saveList,"ComMethodForAB");
         }
      }

      return rtnCode;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-04
     * 설  명: 유상일반 발주시에 모든발주내용을 취소처리 하는 메소드
     * 기  타:
     */
   public int chkExistData3(TBEBWMF1Vo bwmVo) throws BizException, Exception
   {
      int rtnCode = -1;

      TBEBXRF1Vo bxrvo = new TBEBXRF1Vo();
      TBEBXLF1Vo bxlVo = new TBEBXLF1Vo();

      String bwmusd  = bwmVo.getCebwmusd();
      String bwmums  = bwmVo.getCebwmums();
      String bwmumr  = bwmVo.getCebwmumr();
      bxrvo.setMandt(bwmVo.getMandt());
      bxrvo.setCebxrpjt(bwmVo.getCebwmpjt());
      bxrvo.setCebxrhno(bwmVo.getCebwmhno());
      bxrvo.setCebxrseq("");
      bxrvo.setCebxrgnd("A");
      bxrvo.setCebxrjym(bwmusd.substring(0,6));
      bxrvo.setCebxrupn(bwmVo.getCebwmupn());
      bxrvo.setCebxrcst(bwmVo.getCebwmcst());
      
      //  무상공사가 진행중이면 취소처리하고 잔여일수 여부에 따라 재 생성 하여줌.
      TBEBXRF1Dao dao = new TBEBXRF1Dao();
      ArrayList paramList = new ArrayList();
      paramList.add(bxrvo);
      ArrayList alist = (ArrayList) dao.select(paramList, "ComMethod");
      if(alist.size() > 0)
      {
         // logger.info("중지대상 무상공사 개월 : " + alist.size());
         TBEBXRF1Vo reVo = new TBEBXRF1Vo();
         reVo = (TBEBXRF1Vo) alist.get(0);
         dao.update(alist, "ComMethodForCancelForA");

         if(!bwmusd.substring(6,8).equals("01"))
         {
            int jdq = DateTime.daysBetween(bwmusd.substring(0,6)+"01", bwmusd);
            reVo.setCebxrfdt(bwmusd.substring(0,6)+"01");
            reVo.setCebxrtod(DateTime.addDays(bwmusd, -1));
            reVo.setCebxrbya(new Integer((Integer.parseInt(reVo.getCebxrbda()) / 30 ) * jdq).toString());
            reVo.setCebxrjdq(new Integer(jdq).toString());
            reVo.setCebxrjyq(reVo.getCebxrjdq());
            reVo.setCebxridq(reVo.getCebxrjdq());
            reVo.setCebxrbam(reVo.getCebxrbya());
            alist = new ArrayList();
            alist.add(reVo);
            dao.insert(alist,"ComMethodForAB");
         }
      }

      //  유상공사가 진행중이면 기성비 취소처리하고 잔여일수 여부에 따라 재 생성 하여줌.
      bxrvo.setCebxrgnd("C");
      paramList = new ArrayList();
      paramList.add(bxrvo);
      alist = (ArrayList) dao.select(paramList, "ComMethod");
      if(alist.size() > 0)
      {
         // logger.info("중지대상 유상공사 기성비계획 개월 : " + alist.size());
         TBEBXRF1Vo reVo = new TBEBXRF1Vo();
         reVo = (TBEBXRF1Vo) alist.get(0);
         dao.update(alist, "ComMethodForCancelForC");

         if(!bwmusd.substring(6,8).equals("01"))
         {
            int jdq = DateTime.daysBetween(bwmusd.substring(0,6)+"01", bwmusd);
            reVo.setCebxrfdt(bwmusd.substring(0,6)+"01");
            reVo.setCebxrtod(DateTime.addDays(bwmusd, -1));
            reVo.setCebxrbya(new Integer((Integer.parseInt(reVo.getCebxrbda()) / 30 ) * jdq).toString());
            reVo.setCebxrjdq(new Integer(jdq).toString());
            reVo.setCebxrjyq(reVo.getCebxrjdq());
            reVo.setCebxridq(reVo.getCebxrjdq());
            reVo.setCebxrbam(reVo.getCebxrbya());
            alist = new ArrayList();
            alist.add(reVo);
            dao.insert(alist,"ComMethodForC");
         }
      }

      // 유상공사가 진행중이면 매출액 취소처리하고 잔여일수 여부에 따라 재 생성 하여줌.
      TBEBXLF1Dao bxlDao = new TBEBXLF1Dao();

      bxlVo.setMandt(bwmVo.getMandt());
      bxlVo.setCebxlupn(bwmVo.getCebwmupn());
      bxlVo.setCebxlcst(bwmVo.getCebwmcst());
      bxlVo.setCebxlpjt(bwmVo.getCebwmpjt());
      bxlVo.setCebxlhno(bwmVo.getCebwmhno());
      bxlVo.setCebxlseq("");
      bxlVo.setCebxlmym(bwmusd.substring(0,6));
      bxlVo.setCebxlgnd("C");
      bxlVo.setUserId(bwmVo.getUserId());
      paramList = new ArrayList();
      paramList.add(bxlVo);
      alist = (ArrayList) bxlDao.select(paramList, "ComMethod");

      if(alist.size() > 0)
      {
         // logger.info("중지대상 유상공사 매출계획취소 개월 : " + alist.size());

         TBEBXLF1Vo reVo = new TBEBXLF1Vo();
         reVo = (TBEBXLF1Vo) alist.get(0);
         bxlDao.update(alist, "ComMethodForCancelForC");

         if(!bwmusd.substring(6,8).equals("01"))
         {
            int jdq = DateTime.daysBetween(bwmusd.substring(0,6)+"01", bwmusd);
            reVo.setCebxlfdt(bwmusd.substring(0,6)+"01");
            reVo.setCebxltdt(DateTime.addDays(bwmusd, -1));

            reVo.setCebxlamd(new Integer((Integer.parseInt(reVo.getCebxlamd()) / 30 ) * jdq).toString());
            reVo.setCebxlvad(new Integer((Integer.parseInt(reVo.getCebxlvad()) / 30 ) * jdq).toString());
            reVo.setCebxltod(new Integer((Integer.parseInt(reVo.getCebxltod()) / 30 ) * jdq).toString());
            reVo.setCebxljdq(new Integer(jdq).toString());

            alist = new ArrayList();
            alist.add(reVo);
            bxlDao.insert(alist,"ComMethodForC");
         }
      }
      return rtnCode;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-03
     * 설  명: 실패보고 승인시에 승인일자 다음월 부터의 기성비 계획을 조회 한 후 취소 처리
     * 기  타:
     */
   public int chkExistData4(TBEBWMF1Vo bwmVo) throws BizException, Exception
   {
	  int rtnCode = -1;

	  TBEBXRF1Vo bxrvo = new TBEBXRF1Vo();
	  TBEBXLF1Vo bxlVo = new TBEBXLF1Vo();

	  // 기성비 계획 취소처리
	  String bwmuhj = DateTime.addDays(bwmVo.getCebwmuhj(), 1);
	  String bwmums = bwmVo.getCebwmums();
	  String bwmumr = bwmVo.getCebwmumr();
	  bxrvo.setMandt(bwmVo.getMandt());
	  bxrvo.setCebxrpjt(bwmVo.getCebwmpjt());
	  bxrvo.setCebxrhno(bwmVo.getCebwmhno());
	  bxrvo.setCebxrseq(bwmVo.getCebwmseq());
	  bxrvo.setCebxrgnd(bwmVo.getCebwmgnd());
	  bxrvo.setCebxrjym(bwmuhj.substring(0,6));
	  bxrvo.setCebxrupn(bwmVo.getCebwmupn());
      bxrvo.setCebxrcst(bwmVo.getCebwmcst());

	  //  승인일자에 해당하는 일자부터 취소 처리 함.
	  TBEBXRF1Dao dao = new TBEBXRF1Dao();
	  ArrayList paramList = new ArrayList();
	  paramList.add(bxrvo);
	  ArrayList alist = (ArrayList) dao.select(paramList, "ComMethod_silpae");

	  ArrayList reBuildList = new ArrayList();
	  if(alist.size() > 0)
	  {
		 // logger.info("실패보고 승인 기성비 중지 개월 : " + alist.size());
		 TBEBXRF1Vo reVo = new TBEBXRF1Vo();
		 reVo = (TBEBXRF1Vo) alist.get(0);
		 dao.update(alist, "ComMethodForCancelForETC1");

		 //실패일자가 "01" 이 아닐경우 기성비를 일자로 분할하여 정산하는 로직
		 if(!bwmuhj.substring(6,8).equals("01"))
		 {
			int jdq = DateTime.daysBetween(bwmuhj.substring(0,6)+"01", bwmuhj);
			reVo.setCebxrfdt(bwmuhj.substring(0,6)+"01");
			reVo.setCebxrtod(DateTime.addDays(bwmuhj, -1));
			reVo.setCebxrbya(new Integer((Integer.parseInt(reVo.getCebxrbda()) / 30 ) * jdq).toString());
			reVo.setCebxrjdq(new Integer(jdq).toString());
			reVo.setCebxrjyq(reVo.getCebxrjdq());
			reVo.setCebxridq(reVo.getCebxrjdq());
			reVo.setCebxrbam(reVo.getCebxrbya());
			alist = new ArrayList();
			alist.add(reVo);
			dao.insert(alist, "ComMethodForD");
		 }
	  }
	  // 매출액 취소처리
	  TBEBXLF1Dao bxlDao = new TBEBXLF1Dao();

	  bxlVo.setMandt(bwmVo.getMandt());
	  bxlVo.setCebxlupn(bwmVo.getCebwmupn());
	  bxlVo.setCebxlcst(bwmVo.getCebwmcst());
	  bxlVo.setCebxlpjt(bwmVo.getCebwmpjt());
	  bxlVo.setCebxlhno(bwmVo.getCebwmhno());
	  //2006.09.09 순번은 반드시 가져와야 함 by ohb
	  bxlVo.setCebxlseq(bwmVo.getCebwmseq());
	  //2006.09.09 매출예정년월에 유상보수해지일의 년월 세팅 by ohb
	  bxlVo.setCebxlmym(bwmuhj.substring(0,6));
	  bxlVo.setCebxlgnd(bwmVo.getCebwmgnd());
	  bxlVo.setUserId(bwmVo.getUserId());
	  
	  // 승인일자에 해당하는 일자부터 취소 처리 함.
	  paramList = new ArrayList();
	  paramList.add(bxlVo);
	  alist = (ArrayList) bxlDao.select(paramList, "ComMethod_silpae");

	  if(alist.size() > 0)
	  {
		 // logger.info("실패보고 승인 매출계획취소 개월 : " + alist.size());
		 TBEBXLF1Vo reVo = new TBEBXLF1Vo();
		 reVo = (TBEBXLF1Vo) alist.get(0);
		 bxlDao.update(alist, "ComMethodForCancelForETC1");

		//2006.09.16 실패일자가 "01" 이 아닐경우 매출액을 일자로 분할하여 정산하는 로직 추가 by ohb
		if(!bwmuhj.substring(6,8).equals("01"))
		{
		   int jdq = DateTime.daysBetween(bwmuhj.substring(0,6)+"01", bwmuhj);
		   reVo.setCebxlfdt(bwmuhj.substring(0,6)+"01");
		   reVo.setCebxltdt(DateTime.addDays(bwmuhj, -1));

//		   int cdq = Integer.parseInt(DateTime.lastDayOfMonth(bwmuhj.substring(0,6)).substring(6,8));
		   int cdq = Integer.parseInt(DateTime.lastDayOfMonth(bwmuhj).substring(6,8));

		   reVo.setCebxlamd(new Integer((Integer.parseInt(reVo.getCebxlamd()) / cdq ) * jdq).toString());
		   reVo.setCebxlvad(new Integer((Integer.parseInt(reVo.getCebxlvad()) / cdq ) * jdq).toString());
		   reVo.setCebxltod(new Integer((Integer.parseInt(reVo.getCebxltod()) / cdq ) * jdq).toString());
		   reVo.setCebxljdq(new Integer(jdq).toString());

		   alist = new ArrayList();
		   alist.add(reVo);
		   bxlDao.insert(alist, "ComMethodForC");
		}
	  }
	  return rtnCode;
   }

   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-03
     * 설  명: 일시중지 승인시에 승인일자 다음월 부터의 기성비 계획을 조회 한 후 취소 처리
     * 기  타:
     */
   public int chkExistData5(Object condVO) throws BizException, Exception
   {
      int rtnCode = -1;

      ComMethodVo vo = (ComMethodVo) condVO;
      TBEBXRF1Vo bxrvo = new TBEBXRF1Vo();
      TBEBXLF1Vo bxlVo = new TBEBXLF1Vo();

      // 기성비 계획 취소처리
      String bwujhd  = vo.getJhd();
      bxrvo.setMandt(vo.getMandt());
      bxrvo.setCebxrpjt(vo.getPjt());
      bxrvo.setCebxrhno(vo.getHno());
      bxrvo.setCebxrgnd(vo.getGnd());
      bxrvo.setCebxrseq(vo.getSeq());
      bxrvo.setCebxrjym(bwujhd.substring(0,6));
      bxrvo.setCebxrupn(vo.getUpn());
      bxrvo.setCebxrcst(vo.getCst());

      //  승인일자에 해당하는 일자부터 취소 처리 함.
      TBEBXRF1Dao dao = new TBEBXRF1Dao();
      ArrayList paramList = new ArrayList();
      paramList.add(bxrvo);
      ArrayList alist = (ArrayList) dao.select(paramList, "ComMethod_stop");

      ArrayList reBuildList = new ArrayList();
      if(alist.size() > 0)
      {
         // logger.info("일시중지 승인 기성비 중지 개월 : " + alist.size());
         TBEBXRF1Vo reVo = new TBEBXRF1Vo();
         dao.update(alist, "ComMethodForCancelForETC2");
      }
      // 매출액 취소처리
      TBEBXLF1Dao bxlDao = new TBEBXLF1Dao();

      bxlVo.setMandt(vo.getMandt());
      bxlVo.setCebxlupn(vo.getUpn());
      bxlVo.setCebxlcst(vo.getCst());
      bxlVo.setCebxlpjt(vo.getPjt());
      bxlVo.setCebxlhno(vo.getHno());
      bxlVo.setCebxlgnd(vo.getGnd());
      bxlVo.setCebxlseq(vo.getSeq());
      bxlVo.setCebxlmym(bwujhd.substring(0,6));
      bxlVo.setCebxlcsj(vo.getUserId());
      // 승인일자에 해당하는 일자부터 취소 처리 함.
      paramList = new ArrayList();
      paramList.add(bxlVo);
      alist = (ArrayList) bxlDao.select(paramList, "ComMethod_stop");

      if(alist.size() > 0)
      {
         // logger.info("일시중지 승인 매출계획취소 개월 : " + alist.size());

         TBEBXLF1Vo reVo = new TBEBXLF1Vo();
         reVo = (TBEBXLF1Vo) alist.get(0);
         bxlDao.update(alist, "ComMethodForCancelForETC2");

      }
      return rtnCode;
   }

   /**
    * 작성자: jhlee
    * 작성일: 2008-06-20
    * 설  명: 중도해지시에 승인일자 다음월 부터의 기성비 계획을 조회 한 후 취소 처리
    * 기  타:
    */
  public int chkExistData6(TBEBWMF1Vo bwmVo) throws BizException, Exception
  {
	  int rtnCode = -1;

	  TBEBXRF1Vo bxrvo = new TBEBXRF1Vo();
	  TBEBXLF1Vo bxlVo = new TBEBXLF1Vo();

	  // 기성비 계획 취소처리
	  String bwmuhj = bwmVo.getCebwmuhj();
	  String bwmums = bwmVo.getCebwmums();
	  String bwmumr = bwmVo.getCebwmumr();
	  bxrvo.setMandt(bwmVo.getMandt());
	  bxrvo.setCebxrpjt(bwmVo.getCebwmpjt());
	  bxrvo.setCebxrhno(bwmVo.getCebwmhno());
	  bxrvo.setCebxrseq(bwmVo.getCebwmseq());
	  bxrvo.setCebxrgnd(bwmVo.getCebwmgnd());
	  bxrvo.setCebxrjym(bwmuhj.substring(0,6));
	  bxrvo.setCebxrupn(bwmVo.getCebwmupn());
      bxrvo.setCebxrcst(bwmVo.getCebwmcst());
	  bxrvo.setGno(bwmVo.getGno());

	  //  승인일자에 해당하는 일자부터 취소 처리 함.
	  TBEBXRF1Dao dao = new TBEBXRF1Dao();
	  ArrayList paramList = new ArrayList();
	  paramList.add(bxrvo);
	  ArrayList alist = (ArrayList) dao.select(paramList, "ComMethod");

	  ArrayList reBuildList = new ArrayList();
	  if(alist.size() > 0)
	  {
		 // logger.info("중도해지 승인 기성비 중지 개월 : " + alist.size());
		 TBEBXRF1Vo reVo = new TBEBXRF1Vo();
		 reVo = (TBEBXRF1Vo) alist.get(0);
		 dao.update(alist, "ComMethodForCancelForETC4");

		 //중도해지일자가 "01" 이 아닐경우 기성비를 일자로 분할하여 정산하는 로직
//		 if(!bwmuhj.substring(6,8).equals("01"))
		 if(!bwmuhj.substring(6,8).equals("01") && !bwmVo.getCebwmugs().equals(bwmVo.getCebwmuhj()))
		 {
			int jdq = DateTime.daysBetween(bwmuhj.substring(0,6)+"01", bwmuhj);
			reVo.setCebxrfdt(bwmuhj.substring(0,6)+"01");
			reVo.setCebxrtod(DateTime.addDays(bwmuhj, -1));
			reVo.setCebxrbya(new Integer((Integer.parseInt(reVo.getCebxrbda()) / 30 ) * jdq).toString());
			reVo.setCebxrjdq(new Integer(jdq).toString());
			reVo.setCebxrjyq(reVo.getCebxrjdq());
			reVo.setCebxridq(reVo.getCebxrjdq());
			reVo.setCebxrbam(reVo.getCebxrbya());
			reVo.setGno(bwmVo.getGno());
			alist = new ArrayList();
			alist.add(reVo);
			dao.insert(alist, "ComMethodForD");
		 }
	  }
	  // 매출액 취소처리
	  TBEBXLF1Dao bxlDao = new TBEBXLF1Dao();

	  bxlVo.setMandt(bwmVo.getMandt());
	  bxlVo.setCebxlupn(bwmVo.getCebwmupn());
	  bxlVo.setCebxlcst(bwmVo.getCebwmcst());
	  bxlVo.setCebxlpjt(bwmVo.getCebwmpjt());
	  bxlVo.setCebxlhno(bwmVo.getCebwmhno());
	  //2006.09.09 순번은 반드시 가져와야 함 by ohb
	  bxlVo.setCebxlseq(bwmVo.getCebwmseq());
	  //2006.09.09 매출예정년월에 유상보수해지일의 년월 세팅 by ohb
	  bxlVo.setCebxlmym(bwmuhj.substring(0,6));
	  bxlVo.setCebxlgnd(bwmVo.getCebwmgnd());
	  bxlVo.setUserId(bwmVo.getUserId());
	  bxlVo.setGno(bwmVo.getGno());
	  
	  // 승인일자에 해당하는 일자부터 취소 처리 함.
	  paramList = new ArrayList();
	  paramList.add(bxlVo);
	  alist = (ArrayList) bxlDao.select(paramList, "ComMethod");

	  if(alist.size() > 0)
	  {
		 // logger.info("중도해지 승인 매출계획취소 개월 : " + alist.size());
		 TBEBXLF1Vo reVo = new TBEBXLF1Vo();
		 reVo = (TBEBXLF1Vo) alist.get(0);
		 bxlDao.update(alist, "ComMethodForCancelForETC4");

		//2006.09.16 중도해지일자가 "01" 이 아닐경우 매출액을 일자로 분할하여 정산하는 로직 추가 by ohb
//		if(!bwmuhj.substring(6,8).equals("01"))
		if(!bwmuhj.substring(6,8).equals("01") && !bwmVo.getCebwmugs().equals(bwmVo.getCebwmuhj()))
		{
		   int jdq = DateTime.daysBetween(bwmuhj.substring(0,6)+"01", bwmuhj);
		   reVo.setCebxlfdt(bwmuhj.substring(0,6)+"01");
		   reVo.setCebxltdt(DateTime.addDays(bwmuhj, -1));

//		   int cdq = Integer.parseInt(DateTime.lastDayOfMonth(bwmuhj.substring(0,6)).substring(6,8));
		   int cdq = Integer.parseInt(DateTime.lastDayOfMonth(bwmuhj).substring(6,8));

		   reVo.setCebxlamd(new Integer((Integer.parseInt(reVo.getCebxlamd()) / cdq ) * jdq).toString());
		   reVo.setCebxlvad(new Integer((Integer.parseInt(reVo.getCebxlvad()) / cdq ) * jdq).toString());
		   reVo.setCebxltod(new Integer((Integer.parseInt(reVo.getCebxltod()) / cdq ) * jdq).toString());
		   reVo.setCebxljdq(new Integer(jdq).toString());
		   reVo.setGno(bwmVo.getGno());

		   alist = new ArrayList();
		   alist.add(reVo);
		   bxlDao.insert(alist, "ComMethodForC");
		}
	  }
	  return rtnCode;
  }
  
  /**
   * 작성자: jhlee
   * 작성일: 2008-11-11
   * 설  명: 중도해지시에 승인일자 다음월 부터의 기성비 계획을 조회 한 후 취소 처리(인원상주)
   * 기  타:
   */
  public int chkExistData7(TBEBWNF1Vo bwnVo2) throws BizException, Exception
  {
	  int rtnCode = -1;

	  TBEBXRF1Vo bxrvo = new TBEBXRF1Vo();
	  TBEBXLF1Vo bxlVo = new TBEBXLF1Vo();

	  // 기성비 계획 취소처리
	  String bwnuhj = bwnVo2.getCebwnuhj();
	  String bwnsyo = bwnVo2.getCebwnsyo();
	  String bwnsto = bwnVo2.getCebwnsto();
	  bxrvo.setMandt(bwnVo2.getMandt());
	  bxrvo.setCebxrpjt(bwnVo2.getCebwnupn());
	  bxrvo.setCebxrhno("Z99");
	  bxrvo.setCebxrseq(bwnVo2.getCebwnseq());
	  bxrvo.setCebxrgnd("E");
	  bxrvo.setCebxrjym(bwnuhj.substring(0,6));
	  bxrvo.setCebxrupn(bwnVo2.getCebwnupn());
      bxrvo.setCebxrcst(bwnVo2.getCebwncst());
	  bxrvo.setGno(bwnVo2.getGno());

	  //  승인일자에 해당하는 일자부터 취소 처리 함.
	  TBEBXRF1Dao dao = new TBEBXRF1Dao();
	  ArrayList paramList = new ArrayList();
	  paramList.add(bxrvo);
	  ArrayList alist = (ArrayList) dao.select(paramList, "ComMethod");

	  ArrayList reBuildList = new ArrayList();
	  if(alist.size() > 0)
	  {
		 // logger.info("중도해지 승인 기성비 중지 개월 : " + alist.size());
		 TBEBXRF1Vo reVo = new TBEBXRF1Vo();
		 reVo = (TBEBXRF1Vo) alist.get(0);
		 dao.update(alist, "ComMethodForCancelForETC4");

		 //중도해지일자가 "01" 이 아닐경우 기성비를 일자로 분할하여 정산하는 로직
//		 if(!bwnuhj.substring(6,8).equals("01"))
		 if(!bwnuhj.substring(6,8).equals("01") && !bwnVo2.getCebwnsfr().equals(bwnVo2.getCebwnsto()))
		 {
			int jdq = DateTime.daysBetween(bwnuhj.substring(0,6)+"01", bwnuhj);
			reVo.setCebxrfdt(bwnuhj.substring(0,6)+"01");
			reVo.setCebxrtod(DateTime.addDays(bwnuhj, -1));
			reVo.setCebxrbya(new Integer((Integer.parseInt(reVo.getCebxrbda()) / 30 ) * jdq).toString());
			reVo.setCebxrjdq(new Integer(jdq).toString());
			reVo.setCebxrjyq(reVo.getCebxrjdq());
			reVo.setCebxridq(reVo.getCebxrjdq());
			reVo.setCebxrbam(reVo.getCebxrbya());
			reVo.setGno(reVo.getGno());
			alist = new ArrayList();
			alist.add(reVo);
			dao.insert(alist, "ComMethodForD");
		 }
	  }
	  // 매출액 취소처리
	  TBEBXLF1Dao bxlDao = new TBEBXLF1Dao();

	  bxlVo.setMandt(bwnVo2.getMandt());
	  bxlVo.setCebxlupn(bwnVo2.getCebwnupn());
	  bxlVo.setCebxlcst(bwnVo2.getCebwncst());
	  bxlVo.setCebxlpjt(bwnVo2.getCebwnupn());
	  bxlVo.setCebxlhno("Z99");
	  //2006.09.09 순번은 반드시 가져와야 함 by ohb
	  bxlVo.setCebxlseq(bwnVo2.getCebwnseq());
	  //2006.09.09 매출예정년월에 유상보수해지일의 년월 세팅 by ohb
	  bxlVo.setCebxlmym(bwnuhj.substring(0,6));
	  bxlVo.setCebxlgnd("E");
	  bxlVo.setUserId(bwnVo2.getCebwnjyj());
	  bxlVo.setGno(bwnVo2.getGno());
	  
	  // 승인일자에 해당하는 일자부터 취소 처리 함.
	  paramList = new ArrayList();
	  paramList.add(bxlVo);
	  alist = (ArrayList) bxlDao.select(paramList, "ComMethod");

	  if(alist.size() > 0)
	  {
		 // logger.info("중도해지 승인 매출계획취소 개월 : " + alist.size());
		 TBEBXLF1Vo reVo = new TBEBXLF1Vo();
		 reVo = (TBEBXLF1Vo) alist.get(0);
		 bxlDao.update(alist, "ComMethodForCancelForETC4");

		//2006.09.16 중도해지일자가 "01" 이 아닐경우 매출액을 일자로 분할하여 정산하는 로직 추가 by ohb
//		if(!bwnuhj.substring(6,8).equals("01"))
		if(!bwnuhj.substring(6,8).equals("01") && !bwnVo2.getCebwnsfr().equals(bwnVo2.getCebwnsto()))
		{
		   int jdq = DateTime.daysBetween(bwnuhj.substring(0,6)+"01", bwnuhj);
		   reVo.setCebxlfdt(bwnuhj.substring(0,6)+"01");
		   reVo.setCebxltdt(DateTime.addDays(bwnuhj, -1));

//		   int cdq = Integer.parseInt(DateTime.lastDayOfMonth(bwmuhj.substring(0,6)).substring(6,8));
		   int cdq = Integer.parseInt(DateTime.lastDayOfMonth(bwnuhj).substring(6,8));

		   reVo.setCebxlamd(new Integer((Integer.parseInt(reVo.getCebxlamd()) / cdq ) * jdq).toString());
		   reVo.setCebxlvad(new Integer((Integer.parseInt(reVo.getCebxlvad()) / cdq ) * jdq).toString());
		   reVo.setCebxltod(new Integer((Integer.parseInt(reVo.getCebxltod()) / cdq ) * jdq).toString());
		   reVo.setCebxljdq(new Integer(jdq).toString());
		   reVo.setGno(reVo.getGno());

		   alist = new ArrayList();
		   alist.add(reVo);
		   bxlDao.insert(alist, "ComMethodForC");
		}
	  }
	  return rtnCode;
  }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-17
     * 설  명: 유상일반 계약 변경시 이전 계약 내용을 조회 후 기 계산된 신규 계약 내용과 비교.
     * 기  타: 기성비 차액에 대한 부분만 처리.
     */
   public ArrayList chkExistData6_1(ArrayList glist, TBEBWMF1Vo bwmVo) throws BizException, Exception
   {
      //TBEBXLF1Vo bxlVo = new TBEBXLF1Vo();

      TBEBXRF1Vo vo = new TBEBXRF1Vo();
      vo.setMandt(bwmVo.getMandt());
      vo.setCebxrpjt(bwmVo.getCebwmpjt());
      vo.setCebxrhno(bwmVo.getCebwmhno());
      vo.setCebxrgnd(bwmVo.getCebwmgnd());
      vo.setCebxrjym(bwmVo.getCebwmusd().substring(0,6));
      vo.setCebxrupn(bwmVo.getCebwmupn());
      vo.setCebxrcst(bwmVo.getCebwmcst());
      
      TBEBXRF1Dao dao = new TBEBXRF1Dao();

      int seq = Integer.parseInt(bwmVo.getCebwmseq()) -1;
      if(seq < 10)
         vo.setCebxrseq("0"+seq);
      else
         vo.setCebxrseq(new Integer(seq).toString());

      vo.setCebxrjym(DateTime.getFormatString("yyyyMMdd").substring(0,6));

      ArrayList paramList = new ArrayList();
      paramList.add(vo);
      ArrayList preList = (ArrayList) dao.select(paramList, "ComMethodPre");

      // 기성비 계획 취소처리
      TBEBXRF1Vo bxrvo = new TBEBXRF1Vo();
      String bwmusd  = bwmVo.getCebwmusd();
      bxrvo.setMandt(bwmVo.getMandt());
      bxrvo.setCebxrpjt(bwmVo.getCebwmpjt());
      bxrvo.setCebxrhno(bwmVo.getCebwmhno());
	  if (seq < 10)
		  bxrvo.setCebxrseq("0" + seq);
	  else
		  bxrvo.setCebxrseq(new Integer(seq).toString());
      bxrvo.setCebxrgnd(bwmVo.getCebwmgnd());
      bxrvo.setCebxrjym(DateTime.getFormatString("yyyyMMdd").substring(0,6));
      bxrvo.setCebxrupn(bwmVo.getCebwmupn());
      bxrvo.setCebxrcst(bwmVo.getCebwmcst());

      // 승인일자에 해당하는 일자부터 취소 처리 함.
      paramList = new ArrayList();
      paramList.add(bxrvo);
      ArrayList selectedList = (ArrayList) dao.select(paramList, "ComMethod");

      if(selectedList.size() > 0)
      {
         // logger.info("기성비 취소(계약변경) 개월 : " + selectedList.size());
		   TBEBXRF1Vo reVo = new TBEBXRF1Vo();
		   reVo = (TBEBXRF1Vo) selectedList.get(0);

         dao.update(selectedList, "ComMethodForCancelForChg");

		//2006.09.18 계약변경 계약시작일자가 "01" 이 아닐경우 기성비를 일자로 분할하여 정산하는 로직 by ohb
		if(!bwmusd.substring(6,8).equals("01"))
		{
		   int jdq = DateTime.daysBetween(bwmusd.substring(0,6)+"01", bwmusd);
		   reVo.setCebxrfdt(bwmusd.substring(0,6)+"01");
		   reVo.setCebxrtod(DateTime.addDays(bwmusd, -1));
		   reVo.setCebxrbya(new Integer((Integer.parseInt(reVo.getCebxrbda()) / 30 ) * jdq).toString());

		   reVo.setCebxris1(new Integer((Integer.parseInt(reVo.getCebxris1()) / 30 ) * jdq).toString());
		   reVo.setCebxris2(new Integer((Integer.parseInt(reVo.getCebxris2()) / 30 ) * jdq).toString());
		   reVo.setCebxris3(new Integer((Integer.parseInt(reVo.getCebxris3()) / 30 ) * jdq).toString());
		   reVo.setCebxris4(new Integer((Integer.parseInt(reVo.getCebxris4()) / 30 ) * jdq).toString());

		   reVo.setCebxrjdq(new Integer(jdq).toString());
		   reVo.setCebxrjyq(reVo.getCebxrjdq());
		   reVo.setCebxridq(reVo.getCebxrjdq());
		   reVo.setCebxrbam(reVo.getCebxrbya());

		   reVo.setCebxriy1(reVo.getCebxris1());
		   reVo.setCebxriy2(reVo.getCebxris2());
		   reVo.setCebxriy3(reVo.getCebxris3());
		   reVo.setCebxriy4(reVo.getCebxris4());

		   selectedList = new ArrayList();
		   selectedList.add(reVo);
		   dao.insert(selectedList,"ComMethodForD");
		}
      }

      TBEBXRF1Vo newBxrVo;
      TBEBXRF1Vo diffBxrVo;
      ArrayList newList = new ArrayList();

      String jymCurr = "";
      String jymNext = "";

	  //2006.09.18 계약변경 기성계획 DATA 가 차액 처리되기전 받아 놓음(일자계산을 위하여)
	  TBEBXRF1Vo reVo2 = new TBEBXRF1Vo();
	  reVo2 = (TBEBXRF1Vo) glist.get(0);

      for(int i=0;i<glist.size();i++)
      {
         newBxrVo = new TBEBXRF1Vo();
         newBxrVo = (TBEBXRF1Vo)glist.get(i);
         newBxrVo = bwmVo2BXR(newBxrVo, bwmVo);

         jymCurr = newBxrVo.getCebxrjym();
         TBEBXRF1Vo chkBxrVo = new TBEBXRF1Vo();
         if(i < glist.size() -1)
         {
            chkBxrVo = new TBEBXRF1Vo();
            chkBxrVo = (TBEBXRF1Vo)glist.get(i+1);
            chkBxrVo = bwmVo2BXR(chkBxrVo, bwmVo);
            jymNext = chkBxrVo.getCebxrjym();
         }
         jymCurr = newBxrVo.getCebxrjym();

         if(!jymCurr.equals(jymNext))
         {
            //System.out.println("newBxrVo.getCebxrfdt : " + newBxrVo.getCebxrfdt() + "   ,  getCebxrtdt :" +  newBxrVo.getCebxrtod() );
			//재계약에 의해 생성된 기성비 계획중 중복부분을 소급
            for(int j=0; j<preList.size();j++)
            {
               if(newBxrVo.getCebxrjym().equals(((TBEBXRF1Vo) preList.get(j)).getCebxrjym()))
               {
                  diffBxrVo = new TBEBXRF1Vo();
                  diffBxrVo = (TBEBXRF1Vo) preList.get(j);

                  newBxrVo.setCebxrbya(new Double(Double.parseDouble(newBxrVo.getCebxrbya()) - Double.parseDouble(diffBxrVo.getCebxrbya())).toString());
                  newBxrVo.setCebxris1(new Double(Double.parseDouble(newBxrVo.getCebxris1()) - Double.parseDouble(diffBxrVo.getCebxris1())).toString());
                  newBxrVo.setCebxris2(new Double(Double.parseDouble(newBxrVo.getCebxris2()) - Double.parseDouble(diffBxrVo.getCebxris2())).toString());
                  newBxrVo.setCebxris3(new Double(Double.parseDouble(newBxrVo.getCebxris3()) - Double.parseDouble(diffBxrVo.getCebxris3())).toString());
                  newBxrVo.setCebxris4(new Double(Double.parseDouble(newBxrVo.getCebxris4()) - Double.parseDouble(diffBxrVo.getCebxris4())).toString());
				  //2006.09.12 by ohb
				  newBxrVo.setCebxrgym(newBxrVo.getCebxrjym());
                  break;
               }
            }
         }

		 //2006.09.18 계약변경 계약시작일자가 "01" 이 아닐경우 기성비를 일자로 분할하여 정산하는 로직 by ohb
		 if(jymCurr.equals(bwmusd.substring(0,6)) && !bwmusd.substring(6,8).equals("01"))
		 {
			int jdq = DateTime.daysBetween(DateTime.addDays(bwmusd, -1), DateTime.lastDayOfMonth(bwmusd.substring(0,6)));

		    reVo2.setCebxrfdt(bwmusd);
		    reVo2.setCebxrtod(DateTime.lastDayOfMonth(bwmusd.substring(0,6)));
		    reVo2.setCebxrbya(new Integer((Integer.parseInt(reVo2.getCebxrbda()) / 30 ) * jdq).toString());

			 reVo2.setCebxris1(new Integer((Integer.parseInt(reVo2.getCebxris1()) / 30 ) * jdq).toString());
			 reVo2.setCebxris2(new Integer((Integer.parseInt(reVo2.getCebxris2()) / 30 ) * jdq).toString());
			 reVo2.setCebxris3(new Integer((Integer.parseInt(reVo2.getCebxris3()) / 30 ) * jdq).toString());
			 reVo2.setCebxris4(new Integer((Integer.parseInt(reVo2.getCebxris4()) / 30 ) * jdq).toString());

		    reVo2.setCebxrjdq(new Integer(jdq).toString());
		    reVo2.setCebxrjyq(reVo2.getCebxrjdq());
		    reVo2.setCebxridq(reVo2.getCebxrjdq());
		    reVo2.setCebxrbam(reVo2.getCebxrbya());

			 reVo2.setCebxriy1(reVo2.getCebxris1());
			 reVo2.setCebxriy2(reVo2.getCebxris2());
			 reVo2.setCebxriy3(reVo2.getCebxris3());
			 reVo2.setCebxriy4(reVo2.getCebxris4());

		    selectedList = new ArrayList();
		    selectedList.add(reVo2);
		    dao.insert(selectedList,"ComMethodForD");
		 }
         /********************************************************************************************/

         newList.add(newBxrVo);
      }

      return newList;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-06-05
     * 설  명: 유상일반 계약 변경시 이전 계약 내용을 조회 후 기 계산된 신규 계약 내용과 비교.
     *            이전 매출계획 취소처리.
     * 기  타: 매출액 차액에 대한 부분만 처리.
     *         매출액의 차액계산은 기존 매출액목록을 가져와서 사용해야 하므로 기성비와 분리하여 계산.
     */
   public ArrayList chkExistData6_2(ArrayList mlist, TBEBWMF1Vo bwmVo) throws BizException, Exception
   {
	  TBEBXLF1Vo vo = new TBEBXLF1Vo();

	  vo.setMandt(bwmVo.getMandt());
	  vo.setCebxlupn(bwmVo.getCebwmupn());
	  vo.setCebxlcst(bwmVo.getCebwmcst());
	  vo.setCebxlpjt(bwmVo.getCebwmpjt());
	  vo.setCebxlhno(bwmVo.getCebwmhno());
	  vo.setCebxlgnd(bwmVo.getCebwmgnd());
	  vo.setCebxlmym(bwmVo.getCebwmusd().substring(0,6));

	  TBEBXLF1Dao dao = new TBEBXLF1Dao();

	  // 이전 차수(SEQ)의 매출계획 정보 가져오기
		int seq = Integer.parseInt(bwmVo.getCebwmseq()) - 1;
		if (seq < 10)
			vo.setCebxlseq("0" + seq);
		else
			vo.setCebxlseq(new Integer(seq).toString());

		vo.setCebxlmym(DateTime.getFormatString("yyyyMMdd").substring(0, 6));
		ArrayList paramList = new ArrayList();
		paramList.add(vo);

		ArrayList preList = (ArrayList) dao.select(paramList, "ComMethodPre");

	  // 매출 계획 취소처리

	   TBEBXLF1Dao bxlDao = new TBEBXLF1Dao();
	  TBEBXLF1Vo bxlvo = new TBEBXLF1Vo();
	  String bwmusd  = bwmVo.getCebwmusd(); //변경 매출계약  유상보수시작일

	  bxlvo.setMandt(bwmVo.getMandt());
	  bxlvo.setCebxlupn(bwmVo.getCebwmupn());
	  bxlvo.setCebxlcst(bwmVo.getCebwmcst());
	  bxlvo.setCebxlpjt(bwmVo.getCebwmpjt());
	  bxlvo.setCebxlhno(bwmVo.getCebwmhno());
	  if (seq < 10)
		  bxlvo.setCebxlseq("0" + seq);
	  else
		  bxlvo.setCebxlseq(new Integer(seq).toString());
	  //2006.09.12 주석처리, 앞에서 잘 계산된 값을 공백으로 초기화시킬 필요 없음
	  //bxlvo.setCebxlseq("");
	  bxlvo.setCebxlgnd(bwmVo.getCebwmgnd());
	  bxlvo.setCebxlmym(DateTime.getFormatString("yyyyMMdd").substring(0,6));

	  //승인일자에 해당하는 일자부터 취소 처리 함.
	  paramList = new ArrayList();
	  paramList.add(bxlvo);
	  ArrayList selectedList = (ArrayList) dao.select(paramList, "ComMethod");

	  if(selectedList.size() > 0)
	  {
		 // logger.info("매출계획 취소(계약변경) 개월 : " + selectedList.size());
		 dao.update(selectedList, "ComMethodForCancelForCHG");
	  }

	  //일자계산일 경우 해당월에서 계약일자 전까지 계약변경전 매출계획 넣는 로직  여기에 필요
	  //2006.09.16 계약변경 유상상보수계약시작일이 "01" 이 아닐경우 매출액을 일자로 분할하여 정산하는 로직 추가 by ohb
	  /*****************************************************************************************/
	  TBEBXLF1Vo reVo = new TBEBXLF1Vo();
	   reVo = (TBEBXLF1Vo) selectedList.get(0);

	   if(!bwmusd.substring(6,8).equals("01"))
	   {
		  int jdq = DateTime.daysBetween(bwmusd.substring(0,6)+"01", bwmusd);
		  reVo.setCebxlfdt(bwmusd.substring(0,6)+"01");
		  reVo.setCebxltdt(DateTime.addDays(bwmusd, -1));

		  int cdq = Integer.parseInt(DateTime.lastDayOfMonth(bwmusd.substring(0,6)).substring(6,8));

		  reVo.setCebxlamd(new Integer((Integer.parseInt(reVo.getCebxlamd()) / cdq ) * jdq).toString());
		  reVo.setCebxlvad(new Integer((Integer.parseInt(reVo.getCebxlvad()) / cdq ) * jdq).toString());
		  reVo.setCebxltod(new Integer((Integer.parseInt(reVo.getCebxltod()) / cdq ) * jdq).toString());
		  reVo.setCebxljdq(new Integer(jdq).toString());

		  selectedList = new ArrayList();
		  selectedList.add(reVo);
		  bxlDao.insert(selectedList,"ComMethodForC");
	   }
	   /*****************************************************************************************/

	  TBEBXLF1Vo newBxlVo;
	  TBEBXLF1Vo diffBxlVo;
	  ArrayList newList = new ArrayList();

	  String mymCurr = "";
	  String mymNext = "";

	  //2006.09.16 계약변경 매출계획 DATA 가 차액 처리되기전 받아 놓음(일자계산을 위하여)
	   reVo = (TBEBXLF1Vo) mlist.get(0);

	  for(int i=0;i<mlist.size();i++) //재계약후에 새로 생성된 매출계획을 돌리면서
	  {
		 newBxlVo = new TBEBXLF1Vo();
		 newBxlVo = (TBEBXLF1Vo)mlist.get(i);
		 newBxlVo = bwmVo2BXL(newBxlVo, bwmVo);

		 mymCurr = newBxlVo.getCebxlmym();
		 TBEBXLF1Vo chkBxlVo = new TBEBXLF1Vo();
		 if(i < mlist.size() -1) //같은 매출예정월이 존재할 경우 마지막 월에 적용하기 위함
		 {
			chkBxlVo = new TBEBXLF1Vo();
			chkBxlVo = (TBEBXLF1Vo)mlist.get(i+1);
			chkBxlVo = bwmVo2BXL(chkBxlVo, bwmVo);
			mymNext = chkBxlVo.getCebxlmym();
		 }
		 mymCurr = newBxlVo.getCebxlmym();

		 if(!mymCurr.equals(mymNext))
		 {
			for(int j=0; j<preList.size();j++) //재계약전에 생성됐던 매출계획(승인월을 기준으로 이전까지만)을 돌리면서 비교
			{
			   if(mymCurr.equals(((TBEBXLF1Vo) preList.get(j)).getCebxlmym()))
			   {
				  diffBxlVo = new TBEBXLF1Vo();
				  diffBxlVo = (TBEBXLF1Vo) preList.get(j);
				  newBxlVo = bwmVo2BXL(newBxlVo, bwmVo);

				  newBxlVo.setCebxlamd(new Double(Double.parseDouble(newBxlVo.getCebxlamd()) - Double.parseDouble(diffBxlVo.getCebxlamd())).toString());
				  newBxlVo.setCebxlvad(new Double(Double.parseDouble(newBxlVo.getCebxlvad()) - Double.parseDouble(diffBxlVo.getCebxlvad())).toString());
				  newBxlVo.setCebxltod(new Double(Double.parseDouble(newBxlVo.getCebxltod()) - Double.parseDouble(diffBxlVo.getCebxltod())).toString());

				  newBxlVo.setCebxlamt(newBxlVo.getCebxlamd());
				  newBxlVo.setCebxlvat(newBxlVo.getCebxlvad());
				  newBxlVo.setCebxltot(newBxlVo.getCebxltod());

				  //2006.09.11 추가 by ohb
				  newBxlVo.setCebxljgb("2");
			   } //End of if(newBxlVo.getCebxlmym().equals(((TBEBXLF1Vo) preList.get(j)).getCebxlmym()))
			} // End of for(int j=0; j<preList.size();j++)
		 } //End of if(!mymCurr.equals(mymNext))


		 //일자계산일 경우 해당월에서 계약일자 후 부터 해당월 말일까지 매출계획 넣는 로직  여기에 필요
		 //2006.09.16 계약변경 유상상보수계약시작일이 "01" 이 아닐경우 매출액을 일자로 분할하여 정산하는 로직 추가 by ohb

		 //TBEBXLF1Vo reVo = new TBEBXLF1Vo();
		 // reVo = (TBEBXLF1Vo) mlist.get(0);
		 /*****************************************************************************************/
		 if(mymCurr.equals(bwmusd.substring(0,6)) && !bwmusd.substring(6,8).equals("01"))
		 {
			int jdq = DateTime.daysBetween(DateTime.addDays(bwmusd, -1), DateTime.lastDayOfMonth(bwmusd.substring(0,6)));

			reVo.setCebxlfdt(bwmusd);
			reVo.setCebxltdt(DateTime.lastDayOfMonth(bwmusd.substring(0,6)));

			int cdq = Integer.parseInt(DateTime.lastDayOfMonth(bwmusd.substring(0,6)).substring(6,8));

			reVo.setCebxlamd(new Integer((Integer.parseInt(reVo.getCebxlamd()) / cdq ) * jdq).toString());
			reVo.setCebxlvad(new Integer((Integer.parseInt(reVo.getCebxlvad()) / cdq ) * jdq).toString());
			reVo.setCebxltod(new Integer((Integer.parseInt(reVo.getCebxltod()) / cdq ) * jdq).toString());
			reVo.setCebxljdq(new Integer(jdq).toString());

			selectedList = new ArrayList();
			selectedList.add(reVo);
			bxlDao.insert(selectedList,"ComMethodForC");
		 }
		 /*****************************************************************************************/

		 newList.add(newBxlVo);
	  }
	  return newList;
   }

   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-03
     * 설  명: 실패보고 승인시에 승인일자 다음월 부터의 기성비 계획을 조회 한 후 취소 처리
     * 기  타:
     */
   public int restoreExistData(Object condVO) throws BizException, Exception
   {
      int rtnCode = -1;

      ComMethodVo comvo = (ComMethodVo) condVO;
      TBEBXRF1Vo bxrvo = new TBEBXRF1Vo();
      TBEBXLF1Vo bxlVo = new TBEBXLF1Vo();

      // 기성비 계획 취소처리
      String bwujhd  = comvo.getStopDate(); //ZCST146 - CS146_SDT : 보류/중지 등록일자
      String yym  = comvo.getYym();         //매출예정일자(화면에서 입력)
      bxrvo.setMandt(comvo.getMandt());
      bxrvo.setCebxrpjt(comvo.getPjt());
      bxrvo.setCebxrhno(comvo.getHno());
      bxrvo.setCebxrseq(comvo.getSeq());
      bxrvo.setCebxrgnd(comvo.getGnd());
      bxrvo.setCebxrjym(comvo.getJhd().substring(0,6));
      bxrvo.setCebxrupn(comvo.getUpn());
      bxrvo.setCebxrcst(comvo.getCst());

      // 복구일자에 해당하는 일자부터 원복 처리 함.
      TBEBXRF1Dao dao = new TBEBXRF1Dao();
      ArrayList paramList = new ArrayList();
      paramList.add(bxrvo);
      ArrayList alist = (ArrayList) dao.select(paramList, "ComMethodForRestore_stop");

      if(alist.size() > 0)
      {
         ArrayList reBuildList = new ArrayList();
         for(int i=0;i < alist.size();i++)
         {
            TBEBXRF1Vo vo = new TBEBXRF1Vo();
            vo = (TBEBXRF1Vo) alist.get(i);
/*
            if(Integer.parseInt(vo.getCebxrgym()) < Integer.parseInt(bwujhd.substring(0,6)))
            {
               vo.setCebxrgym(bwujhd.substring(0,6));
            }
*/
            if(Integer.parseInt(vo.getCebxrgym()) < Integer.parseInt(yym.substring(0,6)))
            {
            	vo.setCebxrgym(yym.substring(0,6));
            }
            
            reBuildList.add(vo);
         }
         // logger.info("일시중지 복구 기성비 중지 개월 : " + reBuildList.size() );
         // logger.info("일시중지 복구 개시일 : " + bwujhd );
         dao.update(reBuildList, "ComMethodForCancelForETC3");
      }
      // 매출액 복구처리 - 유상계약일 경우에만 해당 됨.
//		if ("C".equals(bwuVo.getCebwugnd()) || "D".equals(bwuVo.getCebwugnd()))
//		{
			TBEBXLF1Dao bxlDao = new TBEBXLF1Dao();
			
			bxlVo.setMandt(comvo.getMandt());
			bxlVo.setCebxlupn(comvo.getUpn());
			bxlVo.setCebxlcst(comvo.getCst());
			bxlVo.setCebxlpjt(comvo.getPjt());
			bxlVo.setCebxlhno(comvo.getHno());
			bxlVo.setCebxlseq(comvo.getSeq());
			bxlVo.setCebxlgnd(comvo.getGnd());
			bxlVo.setCebxlmym(comvo.getJhd().substring(0, 6));
			bxlVo.setCebxlcsj(comvo.getUserId());
			
			paramList = new ArrayList();
			paramList.add(bxlVo);
			alist = (ArrayList) bxlDao.select(paramList, "ComMethodForRestore_stop");
			if (alist.size() > 0)
			{
				ArrayList reBuildList = new ArrayList();
				for (int i = 0; i < alist.size(); i++)
				{
					TBEBXLF1Vo vo = new TBEBXLF1Vo();
					vo = (TBEBXLF1Vo) alist.get(i);
/*
					if (Integer.parseInt(vo.getCebxlyym()) < Integer.parseInt(bwujhd.substring(0, 6)))
					{
						vo.setCebxlyym(bwujhd.substring(0, 6));
					}
*/
					if (Integer.parseInt(vo.getCebxlyym()) < Integer.parseInt(yym.substring(0,6)))
					{
						vo.setCebxlyym(yym.substring(0,6));
					}
					
					reBuildList.add(vo);
				}
				// logger.info("일시중지 복구 매출계획 중지 개월 : " + reBuildList.size());
				bxlDao.update(reBuildList, "ComMethodForCancelForETC3");
			}
//		}

		return rtnCode;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-11
     * 설  명: 입력된 날짜를 기준으로 만근 여부 체크 .
     * 기  타:
     */
   public boolean chkMangun(String fdt, String tod) throws BizException, Exception
   {
      boolean returnVal = false;

      if(fdt.substring(6,8).equals("01") && DateTime.lastDayOfMonth(tod).equals(tod))
         returnVal = true;
      else
         returnVal = false;

      return returnVal;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-30
     * 설  명:
     * 기  타: basGisungbi[53224, 47924, 47151, 44824]
     */
   public double getBasGisungbi1(TBEBWEF1Vo bweVo) throws BizException
   {
      double tmpGisungbi = 0;

      if("Y".equals(bweVo.getCebwehty()))
      {
         tmpGisungbi = getBasGisungbi(0);  // 53224
      }
      else if("9133".equals(bweVo.getCebwebsu())) // 중부-시대산업은 지방 기성 적용(예외 케이스) 2010.12.01 고객지원부 김재순GJ 요청
      {
         if("1".equals(bweVo.getCebweagb()))
            tmpGisungbi = getBasGisungbi(2); // 47151;
         else
            tmpGisungbi = getBasGisungbi(0); // 53224;
      }
      else if(!("E5".equals(bweVo.getCebweara()) || "G8".equals(bweVo.getCebweara()) || "G9".equals(bweVo.getCebweara())))
      {
         if("1".equals(bweVo.getCebweagb()))
            tmpGisungbi = getBasGisungbi(2); // 47151;
         else
            tmpGisungbi = getBasGisungbi(0); // 53224;
      }
      else if(!"1".equals(bweVo.getCebweagb()))
      {
         tmpGisungbi = getBasGisungbi(0); // 53224;
      }
      else if("A".equals(bweVo.getCebweabg()))
      {
         tmpGisungbi = getBasGisungbi(3);  //44824;
      }
      else
      {
         tmpGisungbi = getBasGisungbi(1); // 47924;
      }
      return tmpGisungbi;
   }
   /**
    * 작성자: LHR
    * 작성일: 2012-09-21
    * 설  명: 정률제 적용
    ***** 기  타: basGisungbi[53224, 47924, 47151, 44824]
    * 기  타: basGisungbi[53200, 48000, 0, 44800] ==> 2013.04.24 LJH
    */
  public double getBasGisungbi1_new(TBEBWEF1Vo bweVo) throws BizException
  {
     double tmpGisungbi = 0;

     if("A".equals(bweVo.getCebweabg()))
     {
        tmpGisungbi = getBasGisungbi(3);  //44800;
     }
     else
     {
        tmpGisungbi = getBasGisungbi(1); // 48000;
     }
     return tmpGisungbi;
  }

  /**
   * 작성자: LHR
   * 작성일: 2012-10-15
   * 설  명: 정률제 적용(유상 / 비율제적용을 하지 않는 계약대상의 기성비 산출)
   * 기  타:
   */
 public double getBasGisungbi2_new(TBEBWMF1Vo bwmVo) throws BizException
 {
    double   tmpGisungbi    = 0;
    int      monthSaleAmt   = Integer.parseInt(bwmVo.getCebwmamt());

	if("A".equals(bwmVo.getCebwmabg())) 
    {
        // 2015.02.20 LJH 기성비 인상(2015년도 2월부터 적용)
//		tmpGisungbi = StrictMath.round(monthSaleAmt * 0.70); //아파트, 매출의 70%		
		tmpGisungbi = StrictMath.round(monthSaleAmt * 0.742); //아파트, 매출의 74.2%		
        incentiveFlag = false;
    }
    else
    {
    	// 2015.02.20 LJH 기성비 인상(2015년도 2월부터 적용)
//    	tmpGisungbi = StrictMath.round(monthSaleAmt * 0.60); //빌딩, 매출의 60%
    	tmpGisungbi = StrictMath.round(monthSaleAmt * 0.642); //빌딩, 매출의 64.2%
        incentiveFlag = false;
    }
    return tmpGisungbi;   
 }
 /**
  * 작성자: LHR
  * 작성일: 2012-11-09
  * 설  명: HRTS 기성비 산출
  * 기  타:
  */
public double getBasGisungbi2_hrts(TBEBWMF1Vo bwmVo) throws BizException
{
   double   tmpGisungbi    = 0;
   int      monthSaleHmt   = Integer.parseInt(bwmVo.getCebwmhmt());

	tmpGisungbi = StrictMath.round(monthSaleHmt * 0.00);		
	incentiveFlag = false;

   return tmpGisungbi;   
}
/**
 * 작성자: LHR
 * 작성일: 2012-11-09
 * 설  명: DI-PBX 기성비 산출
 * 기  타:
 */
public double getBasGisungbi2_dipbx(TBEBWMF1Vo bwmVo) throws BizException
{
  double   tmpGisungbi    = 0;
  int      monthSaleDmt   = Integer.parseInt(bwmVo.getCebwmdmt());

	tmpGisungbi = StrictMath.round(monthSaleDmt * 0.00);		
	incentiveFlag = false;

  return tmpGisungbi;   
}
/**
 * 작성자: HJH
 * 작성일: 2020-05-21
 * 설  명: 공기청정기 기성비 산출
 * 기  타:
 */
public double getBasGisungbi2_aircleaner(TBEBWMF1Vo bwmVo) throws BizException
{
  double   tmpGisungbi    = 0;
  int      monthSaleAcmt   = Integer.parseInt(bwmVo.getCebwmacmt());

	tmpGisungbi = StrictMath.round(monthSaleAcmt * 0.00);		
	incentiveFlag = false;

  return tmpGisungbi;   
}
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-30
     * 설  명:
     * 기  타:
     */
   public int getBasGisungbi2(TBEBWMF1Vo bwmVo) throws BizException
   {
      double   tmpGisungbi    = 0;
      //int      monthSaleAmt   = Integer.parseInt(bwmVo.getCebwmtot());
      int      monthSaleAmt   = Integer.parseInt(bwmVo.getCebwmamt());
      double   calcGisungbi   = monthSaleAmt * 0.9;

      if("Y".equals(bwmVo.getCebwmhty())) //고속기종일 경우
      {
         tmpGisungbi = 53224;
         setIncentiveFlag(true);
      }
      else if("9133".equals(bwmVo.getCebwmbsu()))  // 중부-시대산업은 지방 기성 적용(예외 케이스) 2010.12.01 고객지원부 김재순GJ 요청
      {
         if("1".equals(bwmVo.getCebwmagb()))
         {
            if(monthSaleAmt < 52390)
            {
               tmpGisungbi = calcGisungbi;
               setIncentiveFlag(false);
            }
            else
            {
               //tmpGisungbi = 53224;
               tmpGisungbi = 47151; // 20080715 LJH 지방이면서 공급가액이 52390보다 크면 기성비는 47151원 이다.(김재순 GJ 요청)
               setIncentiveFlag(true);
            }
         }
         else
         {
            if(monthSaleAmt < 59138)
            {
               tmpGisungbi = calcGisungbi;
               setIncentiveFlag(false);
            }
            else
            {
               tmpGisungbi = 53224;
               setIncentiveFlag(true);
            }
         }
      }
      else if(!("E5".equals(bwmVo.getCebwmara()) || "G8".equals(bwmVo.getCebwmara()) || "G9".equals(bwmVo.getCebwmara()))) //서울,인천,중부가 아닐 경우
      {
         if("1".equals(bwmVo.getCebwmagb()))
         {
            if(monthSaleAmt < 52390)
            {
               tmpGisungbi = calcGisungbi;
               setIncentiveFlag(false);
            }
            else
            {
               //tmpGisungbi = 53224;
               tmpGisungbi = 47151; // 20080715 LJH 지방이면서 공급가액이 52390보다 크면 기성비는 47151원 이다.(김재순 GJ 요청)
               setIncentiveFlag(true);
            }
         }
         else
         {
            if(monthSaleAmt < 59138)
            {
               tmpGisungbi = calcGisungbi;
               setIncentiveFlag(false);
            }
            else
            {
               tmpGisungbi = 53224;
               setIncentiveFlag(true);
            }
         }
      }
      else if("E5".equals(bwmVo.getCebwmara()) || "G8".equals(bwmVo.getCebwmara()) || "G9".equals(bwmVo.getCebwmara())) //서울,인천,중부일 경우
      {
    	  if(!"1".equals(bwmVo.getCebwmagb()))
          {
             if(monthSaleAmt < 59138)
             {
                tmpGisungbi = calcGisungbi;
                incentiveFlag = false;
             }
             else
             {
                tmpGisungbi = 53224;
                incentiveFlag = true;
             }
          }
          else
          {
        	 if("A".equals(bwmVo.getCebwmabg())) {
	        	 if(monthSaleAmt < 49804)
	             {
	                tmpGisungbi = calcGisungbi;
	                incentiveFlag = false;
	             }
	             else
	             {
	                if(monthSaleAmt < 100000)       /*2006.08.15 mhcho 기성비산출서울시내아파트 10만원 미만 수정.*/
	                {
	                   tmpGisungbi = 44824;
	                   incentiveFlag = true;
	                }
	                else
	                {
	                   tmpGisungbi = 47924;
	                   incentiveFlag = true;
	                }
	             }
        	 } else {
        		 if(monthSaleAmt < 53248)
                 {
                    tmpGisungbi = calcGisungbi;
                    incentiveFlag = false;
                 }
                 else
                 {
                    tmpGisungbi = 47924;
                    incentiveFlag = true;
                 }  
        	 }
          }
      }
      else
      {
         tmpGisungbi = 47924;
         incentiveFlag = true;
      }
      return new Double(tmpGisungbi).intValue();
   }

   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-10
     * 설  명: 인센티브-1 기준 생성
     * 기  타:
     */
   public int getBasInc1(int unitAmt) throws BizException
   {
      int bastInc1 = 0;

      if(unitAmt >= 100000)
         bastInc1 = 4000;
      else if (unitAmt >= 90000)
         bastInc1 = 3500;
      else if (unitAmt >= 85000)
         bastInc1 = 3000;
      else if (unitAmt >= 80000)
         bastInc1 = 2500;
      else if (unitAmt >= 75000)
         bastInc1 = 2000;
      else if (unitAmt >= 70000)
         bastInc1 = 1500;
      else if (unitAmt >= 60000)
         bastInc1 = 1000;
      else
         bastInc1 = 0;

      return bastInc1;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-10
     * 설  명: 인센티브-2 기준생성
     * 기  타:
     */
   public int getBasInc2(double elevRate) throws BizException
   {

      int bastInc2 = 0;

		if (elevRate >= 25)
			bastInc2 = 2000;
		else if (elevRate >= 20)
			bastInc2 = 1400;
		else if (elevRate >= 15)
			bastInc2 = 1200;
		else if (elevRate >= 10)
			bastInc2 = 1000;
		else if (elevRate >= 5)
			bastInc2 = 800;
		else if (elevRate >= 3)
			bastInc2 = 700;
		else if (elevRate >= 0)
			bastInc2 = 0;
        else if (elevRate <= -25)
            bastInc2 = -2000;
        else if (elevRate <= -20)
            bastInc2 = -1400;
        else if (elevRate <= -15)
            bastInc2 = -1200;
        else if (elevRate <= -10)
            bastInc2 = -1000;
        else if (elevRate <= -5)
            bastInc2 = -800;
        else if (elevRate <= -3)
			bastInc2 = -700;
		else if (elevRate < 0)
			bastInc2 = 0;
        else
            bastInc2 = 0;

      return bastInc2;
   }

   /**
   * 작성자: jkkoo
   * 작성일: 2006-05-10
   * 설  명: 유상발주정보 테이블에서 전월 매출액 정보 가져오기
   * 기  타:
   */
	public int getPreMonthSaleAmt(TBEBWMF1Vo vo) throws Exception
	{
		int preMonthSaleAmt = 0;
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      Connection                 conn     = null;

//      System.out.println(" 시퀀스 : " + vo.getCebwmseq());

      sqlBuff.append("  SELECT                              \n");
      sqlBuff.append("     VALUE(MAX(A.CS126_AMT),0) CS126_AMT \n");
      sqlBuff.append("  FROM                                \n");
      sqlBuff.append("     SAPHEE.ZCST126 A,                \n");
      sqlBuff.append("     (  SELECT                        \n");
      sqlBuff.append("           MANDT,                     \n");
      sqlBuff.append("           CS126_UPN,                 \n");
      sqlBuff.append("           CS126_CST,                 \n");
      sqlBuff.append("           CS126_PJT,                 \n");
      sqlBuff.append("           CS126_HNO,                 \n");
      sqlBuff.append("           MAX(CS126_SEQ) CS126_SEQ   \n");
      sqlBuff.append("        FROM                          \n");
      sqlBuff.append("           SAPHEE.ZCST126             \n");
      sqlBuff.append("        WHERE MANDT     = ?           \n");
      sqlBuff.append("        AND   CS126_UPN = ?           \n");
      sqlBuff.append("        AND   CS126_CST = ?           \n");
      sqlBuff.append("        AND   CS126_PJT = ?           \n");
      sqlBuff.append("        AND   CS126_HNO = ?           \n");
      sqlBuff.append("        AND   CS126_SEQ < ?           \n");
      sqlBuff.append("        AND   CS126_DDT = ''          \n");
      sqlBuff.append("        GROUP BY                      \n");
      sqlBuff.append("           MANDT,                     \n");
      sqlBuff.append("           CS126_UPN,                 \n");
      sqlBuff.append("           CS126_CST,                 \n");
      sqlBuff.append("           CS126_PJT,                 \n");
      sqlBuff.append("           CS126_HNO                  \n");
      sqlBuff.append("     ) B                              \n");
      sqlBuff.append("  WHERE 1=1                           \n");
      sqlBuff.append("  AND   A.MANDT     = ?               \n");
      sqlBuff.append("  AND   A.CS126_UPN = ?               \n");
      sqlBuff.append("  AND   A.CS126_CST = ?               \n");
      sqlBuff.append("  AND   A.CS126_PJT = ?               \n");
      sqlBuff.append("  AND   A.CS126_HNO = ?               \n");
      sqlBuff.append("  AND   A.MANDT = B.MANDT             \n");
      sqlBuff.append("  AND   A.CS126_UPN = B.CS126_UPN     \n");
      sqlBuff.append("  AND   A.CS126_CST = B.CS126_CST     \n");
      sqlBuff.append("  AND   A.CS126_PJT = B.CS126_PJT     \n");
      sqlBuff.append("  AND   A.CS126_HNO = B.CS126_HNO     \n");
      sqlBuff.append("  AND   A.CS126_SEQ = B.CS126_SEQ     \n");

      try
      {
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
         conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         pstmt.setString(1, vo.getMandt());
         pstmt.setString(2, vo.getCebwmupn());
         pstmt.setString(3, vo.getCebwmcst());
         pstmt.setString(4, vo.getCebwmpjt());
         pstmt.setString(5, vo.getCebwmhno());
         pstmt.setString(6, vo.getCebwmseq());
         pstmt.setString(7, vo.getMandt());
         pstmt.setString(8, vo.getCebwmupn());
         pstmt.setString(9, vo.getCebwmcst());
         pstmt.setString(10, vo.getCebwmpjt());
         pstmt.setString(11, vo.getCebwmhno());

        // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
//          2006.08.17 mhcho 기성비 산출시 tot = > amt기준으로 수정
            //preMonthSaleAmt = rs.getInt("CEBWMTOT");
            preMonthSaleAmt = rs.getInt("CS126_AMT");
         }
         else
            preMonthSaleAmt = 0;
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         try
         {
            DBUtil.close(rs, pstmt, null);
         }
         catch (Exception e)
         {
         }
      }
		return preMonthSaleAmt;
	}

   /**
   * 작성자: jhlee
   * 작성일: 2008-12-22
   * 설  명: 유상발주정보 테이블에서 전월 매출액 정보 가져오기(거래선이 변경된 경우)
   * 기  타:
   */
	public int getPreMonthSaleAmt2(TBEBWMF1Vo vo) throws Exception
	{
		int preMonthSaleAmt = 0;
      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      Connection                 conn     = null;

//	      System.out.println(" 시퀀스 : " + vo.getCebwmseq());

      sqlBuff.append("  SELECT                              \n");
      sqlBuff.append("     VALUE(MAX(A.CS126_AMT),0) CS126_AMT \n");
      sqlBuff.append("  FROM                                \n");
      sqlBuff.append("     SAPHEE.ZCST126 A,                \n");
      sqlBuff.append("     (  SELECT                        \n");
      sqlBuff.append("           MANDT,                     \n");
      sqlBuff.append("           CS126_UPN,                 \n");
      sqlBuff.append("           CS126_CST,                 \n");
      sqlBuff.append("           CS126_PJT,                 \n");
      sqlBuff.append("           CS126_HNO,                 \n");
      sqlBuff.append("           MAX(CS126_SEQ) CS126_SEQ   \n");
      sqlBuff.append("        FROM                          \n");
      sqlBuff.append("           SAPHEE.ZCST126             \n");
      sqlBuff.append("        WHERE MANDT     = ?           \n");
      sqlBuff.append("        AND   CS126_UPN = ?           \n");
      sqlBuff.append("        AND   CS126_CST = ?           \n");
      sqlBuff.append("        AND   CS126_PJT = ?           \n");
      sqlBuff.append("        AND   CS126_HNO = ?           \n");
      sqlBuff.append("        AND   CS126_PST = 'A6'        \n");
      sqlBuff.append("        AND   CS126_DDT = ''          \n");
      sqlBuff.append("        AND   CS126_GNO > ''          \n");
      sqlBuff.append("        GROUP BY                      \n");
      sqlBuff.append("           MANDT,                     \n");
      sqlBuff.append("           CS126_UPN,                 \n");
      sqlBuff.append("           CS126_CST,                 \n");
      sqlBuff.append("           CS126_PJT,                 \n");
      sqlBuff.append("           CS126_HNO                  \n");
      sqlBuff.append("     ) B                              \n");
      sqlBuff.append("  WHERE 1=1                           \n");
      sqlBuff.append("  AND   A.MANDT     = ?               \n");
      sqlBuff.append("  AND   A.CS126_UPN = ?               \n");
      sqlBuff.append("  AND   A.CS126_CST = ?               \n");
      sqlBuff.append("  AND   A.CS126_PJT = ?               \n");
      sqlBuff.append("  AND   A.CS126_HNO = ?               \n");
      sqlBuff.append("  AND   A.MANDT = B.MANDT             \n");
      sqlBuff.append("  AND   A.CS126_UPN = B.CS126_UPN     \n");
      sqlBuff.append("  AND   A.CS126_CST = B.CS126_CST     \n");
      sqlBuff.append("  AND   A.CS126_PJT = B.CS126_PJT     \n");
      sqlBuff.append("  AND   A.CS126_HNO = B.CS126_HNO     \n");
      sqlBuff.append("  AND   A.CS126_SEQ = B.CS126_SEQ     \n");

      try
      {
    	 String db_con = Utillity.getSapJndi(vo.getMandt());
         conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());
         
         String idx = vo.getCebwmcst().trim().substring(1, 3);
         if(Integer.parseInt(idx) > 1) {
        	 idx = "0" + (Integer.parseInt(idx) - 1);
        	 idx = vo.getCebwmcst().trim().substring(0, 1) + idx;
         }

         pstmt.setString(1, vo.getMandt());
         pstmt.setString(2, vo.getCebwmupn());
         pstmt.setString(3, idx);
         pstmt.setString(4, vo.getCebwmpjt());
         pstmt.setString(5, vo.getCebwmhno());
         pstmt.setString(6, vo.getMandt());
         pstmt.setString(7, vo.getCebwmupn());
         pstmt.setString(8, idx);
         pstmt.setString(9, vo.getCebwmpjt());
         pstmt.setString(10, vo.getCebwmhno());

        // logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
//	          2006.08.17 mhcho 기성비 산출시 tot = > amt기준으로 수정
            //preMonthSaleAmt = rs.getInt("CEBWMTOT");
            preMonthSaleAmt = rs.getInt("CS126_AMT");
         }
         else
            preMonthSaleAmt = 0;
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         try
         {
            DBUtil.close(rs, pstmt, null);
         }
         catch (Exception e)
         {
         }
      }
		return preMonthSaleAmt;
	}

   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-28 오후 2:26:34
     * 설  명:
     * 기  타:
     */
	public TBEBXRF1Vo bweVo2BXR(TBEBXRF1Vo bxrVo, TBEBWEF1Vo bweVo) throws Exception
	{
		try
		{
			String vengbu = getVenGbu(bweVo.getMandt(), bweVo.getCebwepjt(), bweVo.getCebwehno());

			bxrVo.setMandt(bweVo.getMandt());
			bxrVo.setCebxrpjt(bweVo.getCebwepjt());
			bxrVo.setCebxrhno(bweVo.getCebwehno());
			bxrVo.setCebxrseq(bweVo.getCebweseq());
			bxrVo.setCebxrgnd(bweVo.getCebwegnd());
			bxrVo.setCebxrjym(bxrVo.getCebxrfdt().substring(0,6));
			bxrVo.setCebxrmno(bweVo.getCebwemno());
			bxrVo.setCebxrhty(bweVo.getCebwehty());
			bxrVo.setCebxrara(bweVo.getCebweara());
			bxrVo.setCebxrbsu(bweVo.getCebwebsu());
			bxrVo.setCebxrgbu(vengbu);
			bxrVo.setCebxragb(bweVo.getCebweagb());
			bxrVo.setCebxrabg(bweVo.getCebweabg());
			bxrVo.setCebxrjsd(DateTime.getDateString("yyyyMMdd"));
			bxrVo.setCebxrjyd(DateTime.getDateString("yyyyMMdd"));
			bxrVo.setCebxridq(bxrVo.getCebxrjdq());
			bxrVo.setCebxrbam(bxrVo.getCebxrbya());
			bxrVo.setCebxrplt(bweVo.getCebweplt());
			bxrVo.setGno(bweVo.getGno());
			bxrVo.setCebxrbdgbn(bweVo.getCebwebdgbn());

			if ("1".equals(vengbu))
			{	if (bweVo.getCevwevkgrp().equals("H11") && "L".equals(bweVo.getCebwehno().substring(0, 1)))
				{  //자회사 무상발주 일경우  기성데이터를 협력으로 기성비 지급 가능토록 입력함
					bxrVo.setCebxrggb("");
					bxrVo.setCebxrpst("A1");
					bxrVo.setCebxrmgb("");
					bxrVo.setCebxrmsa("");
					bxrVo.setCebxrmdt("");
					bxrVo.setCebxrgbu("3");
				}
				else 
				{
					bxrVo.setCebxrggb("9");
					bxrVo.setCebxrpst("A1");
					bxrVo.setCebxrmgb("Y");
					bxrVo.setCebxrmsa("09");
					bxrVo.setCebxrmdt(DateTime.getDateString("yyyyMMdd"));								
				}
			}
			else if ("3".equals(vengbu))
			{
				bxrVo.setCebxrggb("");
				bxrVo.setCebxrpst("A1");
				bxrVo.setCebxrmgb("");
				bxrVo.setCebxrmsa("");
				bxrVo.setCebxrmdt("");
			}
			else
			{
				bxrVo.setCebxrggb("");
				bxrVo.setCebxrpst("");
				bxrVo.setCebxrmgb("");
				bxrVo.setCebxrmsa("");
				bxrVo.setCebxrmdt("");
			}

			String str_gym = "";

			/* 2010.12.07 LJH IFRS 관련 기성지급월 생성 변경 처리(생성일자가 20일이후일 경우 지급월을 차월로 생성)*/
			/* 2011.03.29 LJH IFRS 관련 기성지급월 생성 변경 처리(생성일자가 22일이후일 경우 지급월을 차월로 생성)*/
			/* 2011.06.07 LJH IFRS 관련 기성지급월 생성 변경 처리(최초 생성과 동일하게 처리)*/
	        if(Integer.parseInt(bxrVo.getCebxrfdt()) <= Integer.parseInt(CommonUtil.getToday2())){
	        	//if(Integer.parseInt(CommonUtil.getToday2().substring(6,8)) < 22) {
	        		str_gym = CommonUtil.getToday2().substring(0,6);
	        	//} else {
	        		//str_gym = DateTime.addMonths(CommonUtil.getToday2(),1).substring(0,6);
	        	//}
	        } else {
	        	str_gym = bxrVo.getCebxrjym();
	        }

	        bxrVo.setCebxrgym(str_gym);
		}
		catch (Exception e)
		{
			throw e;
		}
		return bxrVo;
	}
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-04
     * 설  명:
     * 기  타:
     */
   public TBEBXRF1Vo bwmVo2BXR(TBEBXRF1Vo bxrVo, TBEBWMF1Vo bwmVo) throws Exception
   {
      try
      {
         String  vengbu = getVenGbu(bwmVo.getMandt(), bwmVo.getCebwmpjt(), bwmVo.getCebwmhno());
         double  bwmtot = Double.parseDouble(bwmVo.getCebwmtot());

         bxrVo.setMandt(bwmVo.getMandt());
         bxrVo.setCebxrpjt(bwmVo.getCebwmpjt());
         bxrVo.setCebxrhno(bwmVo.getCebwmhno());
         bxrVo.setCebxrseq(bwmVo.getCebwmseq());
         bxrVo.setCebxrgnd(bwmVo.getCebwmgnd());
         bxrVo.setCebxrjym(bxrVo.getCebxrfdt().substring(0,6));
         bxrVo.setCebxrupn(bwmVo.getCebwmupn());
         bxrVo.setCebxrcst(bwmVo.getCebwmcst());
         bxrVo.setCebxrhty(bwmVo.getCebwmhty());
         bxrVo.setCebxrara(bwmVo.getCebwmara());
         bxrVo.setCebxrbsu(bwmVo.getCebwmbsu());
         bxrVo.setCebxrgbu(vengbu);
         bxrVo.setCebxragb(bwmVo.getCebwmagb());
         bxrVo.setCebxrabg(bwmVo.getCebwmabg());
         bxrVo.setCebxrrgb(bwmVo.getCebwmrgb());

         bxrVo.setCebxrjdj(bwmVo.getCebwmrto());
         //bxrVo.setCebxrcpr(bwmVo.getCebwmtot());
         bxrVo.setCebxrcpr(bwmVo.getCebwmamt());
         /*2006.08.17 mhcho 전회차 매출단가 추가*/
         if(bwmVo.getCebwmseq().equals("01")) bxrVo.setCebxrbpd("0");
         else bxrVo.setCebxrbpd(getPreBpd(bwmVo));

         bxrVo.setCebxrjsd(DateTime.getDateString("yyyyMMdd"));
         bxrVo.setCebxrjyc(bwmVo.getCebwmrto());

         bxrVo.setCebxrjyd(DateTime.getDateString("yyyyMMdd"));
         bxrVo.setCebxridq(bxrVo.getCebxrjdq());
         bxrVo.setCebxridj(bwmVo.getCebwmrto());
         bxrVo.setCebxrbam(bxrVo.getCebxrbya());
         bxrVo.setCebxrjyj(bwmVo.getUserId());
         bxrVo.setCebxrplt(bwmVo.getCebwmplt());
         bxrVo.setGno(bwmVo.getGno());

         bxrVo.setCebxrbdgbn(bwmVo.getCebwmbdgbn());		//분담이행방식

         if ("1".equals(vengbu)){
            bxrVo.setCebxrggb("9");
            bxrVo.setCebxrpst("A1");
         }
         else if ("3".equals(vengbu)){
        	if (bxrVo.getCebxrbdgbn().equals("X") || bxrVo.getCebxrbdgbn().equals("1")){
                bxrVo.setCebxrggb("9");
                bxrVo.setCebxrpst("A1");
        	} else {
	            bxrVo.setCebxrggb("");
	            bxrVo.setCebxrpst("A1");
        	}
         }
         else{
            bxrVo.setCebxrggb("");
            bxrVo.setCebxrpst("");
         }
         
         String str_gym = "";
         
         if(Integer.parseInt(bxrVo.getCebxrfdt()) <= Integer.parseInt(CommonUtil.getToday2())){
        	 str_gym = CommonUtil.getToday2().substring(0,6);
         }else{
        	 str_gym = bxrVo.getCebxrjym();
         }
         
         bxrVo.setCebxrgym(str_gym);

         if("1".equals(vengbu))
         {
            bxrVo.setCebxrmgb("Y");
            bxrVo.setCebxrmsa("09");
            bxrVo.setCebxrmdt(DateTime.getDateString("yyyyMMdd"));
         }
         else
         {
        	 if (bxrVo.getCebxrbdgbn().equals("X") || bxrVo.getCebxrbdgbn().equals("1")){
                 bxrVo.setCebxrmgb("Y");
                 bxrVo.setCebxrmsa("09");
                 bxrVo.setCebxrmdt(DateTime.getDateString("yyyyMMdd"));
        	 } else {
	            bxrVo.setCebxrmgb("");
	            bxrVo.setCebxrmsa("");
	            bxrVo.setCebxrmdt("");
        	 }
         }
      }
      catch (Exception e)
      {
         throw e;
      }
      return bxrVo;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-04
     * 설  명:
     * 기  타:
     */
   public TBEBXRF1Vo bwnVo2BXR(TBEBXRF1Vo bxrVo, TBEBWNF1Vo bwnVo) throws Exception
   {
      try
      {
         String   vengbu = getVenGbuBwn(bwnVo.getMandt(), bwnVo.getCebwnbsu());

//    	 String vengbu = "";
         bxrVo.setMandt(bwnVo.getMandt());
         bxrVo.setCebxrpjt(bwnVo.getCebwnupn());
         bxrVo.setCebxrhno("Z99");
         bxrVo.setCebxrseq(bwnVo.getCebwnseq());
         bxrVo.setCebxrjym(bxrVo.getCebxrfdt().substring(0,6));
         bxrVo.setCebxrgnd(bwnVo.getCebwngnd());
         bxrVo.setCebxrupn(bwnVo.getCebwnupn());
         bxrVo.setCebxrcst(bwnVo.getCebwncst());
         bxrVo.setCebxrbsu(bwnVo.getCebwnbsu());
         bxrVo.setCebxrgbu(vengbu);
         bxrVo.setCebxrjsd(DateTime.getDateString("yyyyMMdd"));
         bxrVo.setCebxrjyd(DateTime.getDateString("yyyyMMdd"));
         bxrVo.setCebxridq(bxrVo.getCebxrjdq());
         bxrVo.setCebxrbam(bxrVo.getCebxrbya());
         bxrVo.setCebxrara(bwnVo.getCebwnara());
         bxrVo.setCebxriis(bwnVo.getCebwnisr());
         bxrVo.setCebxrjdj(bwnVo.getCebwnjdj());
         bxrVo.setCebxrjyj(bwnVo.getCebwnjyj());
         bxrVo.setGno(bwnVo.getGno());
         bxrVo.setCebxrbdgbn(bwnVo.getCebwnbdgbn());	// 분담이행방식

         if ("1".equals(vengbu)){
            bxrVo.setCebxrggb("9");
            bxrVo.setCebxrpst("A1");
         }
         else if ("3".equals(vengbu)){
        	 if (bxrVo.getCebxrbdgbn().equals("X") || bxrVo.getCebxrbdgbn().equals("1")){
                 bxrVo.setCebxrggb("9");
                 bxrVo.setCebxrpst("A1");
        	 } else {
                 bxrVo.setCebxrggb("");
                 bxrVo.setCebxrpst("A1");
        	 }
         }
         else{
            bxrVo.setCebxrggb("");
            bxrVo.setCebxrpst("");
         }
         
         String str_gym = "";
         
         if(Integer.parseInt(bxrVo.getCebxrfdt()) <= Integer.parseInt(CommonUtil.getToday2())){
        	 str_gym = CommonUtil.getToday2().substring(0,6);
         }else{
        	 str_gym = bxrVo.getCebxrjym();
         }
         
         bxrVo.setCebxrgym(str_gym);
         
         if("1".equals(vengbu))
         {
            bxrVo.setCebxrmgb("Y");
            bxrVo.setCebxrmsa("09");
            bxrVo.setCebxrmdt(DateTime.getDateString("yyyyMMdd"));
         }
         else
         {
        	 if (bxrVo.getCebxrbdgbn().equals("X") || bxrVo.getCebxrbdgbn().equals("1")){
                 bxrVo.setCebxrmgb("Y");
                 bxrVo.setCebxrmsa("09");
                 bxrVo.setCebxrmdt(DateTime.getDateString("yyyyMMdd"));
        	 } else {
                 bxrVo.setCebxrmgb("");
                 bxrVo.setCebxrmsa("");
                 bxrVo.setCebxrmdt("");
        		 
        	 }
         }

      }
      catch (Exception e)
      {
         throw e;
      }
      return bxrVo;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-04
     * 설  명:
     * 기  타:
     */
   public TBEBXLF1Vo bwmVo2BXL(TBEBXLF1Vo bxlVo, TBEBWMF1Vo bwmVo) throws Exception
   {
      try
      {
         String   vengbu = getVenGbu(bwmVo.getMandt(), bwmVo.getCebwmpjt(), bwmVo.getCebwmhno());
         double  bwmtot = Double.parseDouble(bwmVo.getCebwmtot());

         bxlVo.setMandt(bwmVo.getMandt());
         bxlVo.setCebxlupn(bwmVo.getCebwmupn());
         bxlVo.setCebxlcst(bwmVo.getCebwmcst());
         bxlVo.setCebxlpjt(bwmVo.getCebwmpjt());
         bxlVo.setCebxlhno(bwmVo.getCebwmhno());
         bxlVo.setCebxlseq(bwmVo.getCebwmseq());
         bxlVo.setCebxlgnd(bwmVo.getCebwmgnd());
         bxlVo.setCebxlmym(bxlVo.getCebxlfdt().substring(0,6));

         bxlVo.setCebxltyp(bwmVo.getCebwmtyp());
         bxlVo.setCebxlara(bwmVo.getCebwmara());
         bxlVo.setCebxlbsu(bwmVo.getCebwmbsu());
         bxlVo.setCebxlgbu(vengbu);
         bxlVo.setCebxlagb(bwmVo.getCebwmagb());
         bxlVo.setCebxlabg(bwmVo.getCebwmabg());
//         bxlVo.setCebxlydt(DateTime.getDateString("yyyyMMdd"));
//         bxlVo.setCebxlydt(bwmVo.getCebwmydt());


         bxlVo.setCebxlbgb(bwmVo.getCebwmbgb());
         bxlVo.setCebxlbhb(bwmVo.getCebwmgbn());
         bxlVo.setCebxlsju(bwmVo.getCebwmsju());
         bxlVo.setCebxlzer(bwmVo.getCebwmzer());
         bxlVo.setGno(bwmVo.getGno());

         bxlVo.setCebxlbdgbn(bwmVo.getCebwmbdgbn());	
         /*
         bxlVo.setCebxlamd(bwmVo.getCebwmamt());
         bxlVo.setCebxlvad(bwmVo.getCebwmvat());
         bxlVo.setCebxltod(bwmVo.getCebwmtot());
         bxlVo.setCebxlamt(bwmVo.getCebwmamt());
         bxlVo.setCebxlvat(bwmVo.getCebwmvat());
         bxlVo.setCebxltot(bwmVo.getCebwmtot());
         */

         if("1".equals(vengbu)) //직영일때는 미사용
         {
            bxlVo.setCebxlmyb("");
            bxlVo.setCebxlmsa("09");
            bxlVo.setCebxlmdt(DateTime.getDateString("yyyyMMdd"));
         }
         else
         {
        	if (bxlVo.getCebxlbdgbn().equals("X") || bxlVo.getCebxlbdgbn().equals("1")){
                bxlVo.setCebxlmyb("");
                bxlVo.setCebxlmsa("09");
                bxlVo.setCebxlmdt(DateTime.getDateString("yyyyMMdd"));
        	} else {
                bxlVo.setCebxlmyb("");
                bxlVo.setCebxlmsa("");
                bxlVo.setCebxlmdt("");
        	}
         }
         bxlVo.setCebxlbgd("1");
         bxlVo.setCebxljgb("1");
         bxlVo.setCebxlup1(bwmVo.getCebwmupn());
         bxlVo.setCebxlcs1(bwmVo.getCebwmcst());
         //2006.09.12 by ohb
		 bxlVo.setCebxlyym(bxlVo.getCebxlydt().substring(0,6));

         //bxlVo.setCebxlyym(bxlVo.getCebxlmym());
         //bxrVo.setCebxlcsj(ui.getUserid());
		 // 분담이행방식 변서 설정
         bxlVo.setCebxlbdgbn(bwmVo.getCebwmbdgbn());

		 
      }
      catch (Exception e)
      {
         throw e;
      }
      return bxlVo;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-04
     * 설  명:
     * 기  타:
     */
   public TBEBXLF1Vo bwnVo2BXL(TBEBXLF1Vo bxlVo, TBEBWNF1Vo bwnVo) throws Exception
   {
      try
      {
         String   vengbu = getVenGbuBwn(bwnVo.getMandt(), bwnVo.getCebwnbsu());
//         String   vengbu = "";
         double  bwmtot = Double.parseDouble(bwnVo.getCebwntot());

         bxlVo.setMandt(bwnVo.getMandt());
         bxlVo.setCebxlupn(bwnVo.getCebwnupn());
         bxlVo.setCebxlcst(bwnVo.getCebwncst());
         bxlVo.setCebxlpjt(bwnVo.getCebwnupn());
         bxlVo.setCebxlhno("Z99");
         bxlVo.setCebxlseq(bwnVo.getCebwnseq());
         bxlVo.setCebxlgnd("E");

         bxlVo.setCebxlmym(bxlVo.getCebxlfdt().substring(0,6));

         bxlVo.setCebxlara(bwnVo.getCebwnara());
         bxlVo.setCebxlbsu(bwnVo.getCebwnbsu());
         bxlVo.setCebxlgbu(vengbu);
         bxlVo.setGno(bwnVo.getGno());

         /* 2006.09.29 주석처리 앞 단에서 세팅 됐음 by ohb */
         //bxlVo.setCebxlydt(DateTime.getDateString("yyyyMMdd"));

         /* 2006.09.29 주석처리 똑 같은 값 두번 세팅 됐음 by ohb*/
         //bxlVo.setCebxlamt(bxlVo.getCebxlamd());
         //bxlVo.setCebxlvat(bxlVo.getCebxlvad());
         //bxlVo.setCebxltot(bxlVo.getCebxltod());

         bxlVo.setCebxlbgd("1");
         bxlVo.setCebxljgb("1");
         bxlVo.setCebxlup1(bwnVo.getCebwnupn());
         bxlVo.setCebxlcs1(bwnVo.getCebwncst());
         //2006.09.20 by ohb
         bxlVo.setCebxlyym(bxlVo.getCebxlydt().substring(0,6));
         bxlVo.setCebxlcsj("");
         bxlVo.setCebxlisr(bwnVo.getCebwnisr());
		 // 분담이행방식 변서 설정
         bxlVo.setCebxlbdgbn(bwnVo.getCebwnbdgbn());

      }
      catch (Exception e)
      {
         throw e;
      }
      return bxlVo;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-28 오후 2:26:34
     * 설  명:
     * 기  타:
     */
   public TBEBWEF1Vo ComVo2BWE(ComMethodVo vo) throws Exception
   {
      TBEBWEF1Vo bweVo = new TBEBWEF1Vo();
      try
      {
         bweVo.setMandt(vo.getMandt());
         bweVo.setCebwepjt(vo.getPjt());
         bweVo.setCebwehno(vo.getHno());
         bweVo.setCebweseq(vo.getSeq());
         bweVo.setJobType(Integer.parseInt(vo.getDataId()));
         bweVo.setGubun(vo.getJobGubun());
         bweVo.setFirstUserId(vo.getFirstUserId());
         bweVo.setSla(vo.getSla());
      }
      catch(Exception e)
      {
         throw e;
      }

      return bweVo;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-28 오후 2:26:34
     * 설  명:
     * 기  타:
     */
   public TBEBWMF1Vo ComVo2BWM(ComMethodVo vo) throws Exception
   {
      TBEBWMF1Vo bwmVo = new TBEBWMF1Vo();
      try
      {
         bwmVo.setMandt(vo.getMandt());
         bwmVo.setCebwmupn(vo.getUpn());
         bwmVo.setCebwmcst(vo.getCst());
         bwmVo.setCebwmpjt(vo.getPjt());
         bwmVo.setCebwmhno(vo.getHno());
         bwmVo.setCebwmseq(vo.getSeq());
         bwmVo.setJobType(Integer.parseInt(vo.getDataId()));
         bwmVo.setGubun(vo.getJobGubun());
         bwmVo.setFirstUserId(vo.getFirstUserId());
         bwmVo.setSla(vo.getSla());
      }
      catch(Exception e)
      {
         throw e;
      }

      return bwmVo;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-04-28 오후 2:26:34
     * 설  명:
     * 기  타:
     */
   public TBEBWUF1Vo ComVo2BWU(ComMethodVo vo) throws Exception
   {
      TBEBWUF1Vo bwuVo = new TBEBWUF1Vo();
      try
      {
         bwuVo.setCebwugbn(vo.getGbn());
         bwuVo.setCebwuwym(vo.getWym());
         bwuVo.setCebwuisr(vo.getIsr());

         bwuVo.setMandt(vo.getMandt());
         bwuVo.setCebwuupn(vo.getUpn());
         bwuVo.setCebwucst(vo.getCst());
         bwuVo.setCebwupjt(vo.getPjt());
         bwuVo.setCebwuhno(vo.getHno());
         bwuVo.setCebwuseq(vo.getSeq());
         bwuVo.setJobType(Integer.parseInt(vo.getDataId()));
         bwuVo.setGubun(vo.getJobGubun());
         bwuVo.setFirstUserId(vo.getFirstUserId());
         bwuVo.setSla(vo.getSla());
      }
      catch(Exception e)
      {
         throw e;
      }

      return bwuVo;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-13
     * 설  명:
     * 기  타:
     */
   public TBEBWNF1Vo ComVo2BWN(ComMethodVo vo) throws Exception
   {
      TBEBWNF1Vo bwnVo = new TBEBWNF1Vo();
      try
      {
    	 bwnVo.setMandt(vo.getMandt());
    	 bwnVo.setCebwnupn(vo.getUpn());
         bwnVo.setCebwncst(vo.getCst());
         bwnVo.setCebwnseq(vo.getSeq());
         bwnVo.setCebwnisr(vo.getIsr());
         bwnVo.setJobType(Integer.parseInt(vo.getDataId()));
         bwnVo.setGubun(vo.getJobGubun());
         bwnVo.setFirstUserId(vo.getFirstUserId());
         bwnVo.setSla(vo.getSla());
      }
      catch(Exception e)
      {
         throw e;
      }

      return bwnVo;
   }
   public TBEBWNF1Vo ComVo2BWN2(ComMethodVo vo) throws Exception
   {
      TBEBWNF1Vo bwnVo2 = new TBEBWNF1Vo();
      try
      {
    	 bwnVo2.setMandt(vo.getMandt());
    	 bwnVo2.setCebwnupn(vo.getUpn());
         bwnVo2.setCebwncst(vo.getCst());
         bwnVo2.setCebwnseq(vo.getMaxSeq());
         bwnVo2.setMaxSeq(vo.getMaxSeq());
//         bwnVo2.setCebwnisr(vo.getIsr());
         bwnVo2.setJobType(Integer.parseInt(vo.getDataId()));
         bwnVo2.setGubun(vo.getJobGubun());
         bwnVo2.setFirstUserId(vo.getFirstUserId());
         bwnVo2.setSla(vo.getSla());
      }
      catch(Exception e)
      {
         throw e;
      }

      return bwnVo2;
   }
   /**
     * 작성자: jkkoo
     * 작성일: 2006-05-01
     * 설  명:
     * 기  타:
     */
   public String getVenGbu(String mandt, String pjt, String hno) throws Exception
   {
      String bsuGbu = "";

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      Connection                 conn     = null;

      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     GBU                        \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     SAPHEE.ZCST111             \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   MANDT = ?               \n");
      sqlBuff.append("  AND   PJT   = ?               \n");
      sqlBuff.append("  AND   HNO   = ?               \n");

      try
      {
     	 String db_con = Utillity.getSapJndi(mandt);
         conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         pstmt.setString(1, mandt);
         pstmt.setString(2, pjt);
         pstmt.setString(3, hno);
       //  logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());

         rs = pstmt.executeQuery();
         if (rs.next())
         {
            bsuGbu = rs.getString("GBU");
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         try
         {
            DBUtil.close(rs, pstmt, null);
         }
         catch (Exception e)
         {
         }
      }
      return bsuGbu;
   }

   public String getVenGbuBwn(String mandt, String lgort) throws Exception
   {
      String bsuGbu = "";

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      Connection                 conn     = null;

      sqlBuff.append(" SELECT                             \n");
      sqlBuff.append("        WB010A.BSU_GB GBU           \n");
      sqlBuff.append("   FROM                             \n");
      sqlBuff.append("        SAPHEE.ZMMT005 MM005A,      \n");
      sqlBuff.append("        SAPHEE.ZWBT010 WB010A       \n");
      sqlBuff.append("  WHERE                             \n");
      sqlBuff.append("        MM005A.MANDT = ?            \n");
      sqlBuff.append("    AND MM005A.MANDT = WB010A.MANDT \n");
      sqlBuff.append("    AND MM005A.LIFNR = WB010A.LIFNR \n");
      sqlBuff.append("    AND MM005A.LGORT = ?            \n");

      try
      {
     	 String db_con = Utillity.getSapJndi(mandt);
         conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         pstmt.setString(1, mandt);
         pstmt.setString(2, lgort);
       //  logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());

         rs = pstmt.executeQuery();
         if (rs.next())
         {
            bsuGbu = rs.getString("GBU");
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         try
         {
            DBUtil.close(rs, pstmt, null);
         }
         catch (Exception e)
         {
         }
      }
      return bsuGbu;
   }
   
   /**
    * 작성자: jkkoo
    * 작성일: 2006-05-11 오후 12:57:45
    * 설  명: boolean
    * 기  타:
    */
   public void setIsFlag(String str)
   {
      if(str.charAt(0) == '1')
         setIs1Flag(true);
      else
         setIs1Flag(false);

      if(str.charAt(1) == '1')
         setIs2Flag(true);
      else
         setIs2Flag(false);

      if(str.charAt(2) == '1')
         setIs3Flag(true);
      else
         setIs3Flag(false);

      if(str.charAt(3) == '1')
         setIs4Flag(true);
      else
         setIs4Flag(false);
   }

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-11 오후 12:57:45
	 * 설  명: boolean
	 * 기  타:
	 */
	public boolean isIncentiveFlag()
	{
		return incentiveFlag;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-11 오후 12:57:46
	 * 설  명: void
	 * 기  타:
	 */
	public void setIncentiveFlag(boolean b)
	{
		incentiveFlag = b;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-16 오후 1:51:04
	 * 설  명: boolean
	 * 기  타:
	 */
	public boolean isIs1Flag()
	{
		return is1Flag;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-16 오후 1:51:05
	 * 설  명: boolean
	 * 기  타:
	 */
	public boolean isIs2Flag()
	{
		return is2Flag;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-16 오후 1:51:05
	 * 설  명: boolean
	 * 기  타:
	 */
	public boolean isIs3Flag()
	{
		return is3Flag;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-16 오후 1:51:05
	 * 설  명: boolean
	 * 기  타:
	 */
	public boolean isIs4Flag()
	{
		return is4Flag;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-16 오후 1:51:06
	 * 설  명: void
	 * 기  타:
	 */
	public void setIs1Flag(boolean b)
	{
		is1Flag = b;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-16 오후 1:51:06
	 * 설  명: void
	 * 기  타:
	 */
	public void setIs2Flag(boolean b)
	{
		is2Flag = b;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-16 오후 1:51:07
	 * 설  명: void
	 * 기  타:
	 */
	public void setIs3Flag(boolean b)
	{
		is3Flag = b;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-16 오후 1:51:07
	 * 설  명: void
	 * 기  타:
	 */
	public void setIs4Flag(boolean b)
	{
		is4Flag = b;
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-23 오후 1:46:07
	 * 설  명: int[]
	 * 기  타:
	 */
	public double getBasGisungbi(int i)
	{
		return basGisungbi[i];
	}

	/**
	 * 작성자: jkkoo
	 * 작성일: 2006-05-23 오후 1:46:08
	 * 설  명: void
	 * 기  타:
	 */
	public void setBasGisungbi(double[] is)
	{
		basGisungbi = is;
	}

	
	
	public double getBasGisungbi_ja(int i)
	{
		return basGisungbi_ja[i];
	}

	public void setBasGisungbi_ja(double[] is)
	{
		basGisungbi_ja = is;
	}	
	
	
	public double getBasGisungbi_jb(int i)
	{
		return basGisungbi_jb[i];
	}

	public void setBasGisungbi_jb(double[] is)
	{
		basGisungbi_jb = is;
	}	
		
	
	
	
   /**
     * 작성자: mhcho
     * 작성일: 2006-08-17
     * 설  명: 유상 승인시 전회차 매출단가
     * 기  타:
     */
   public String getPreBpd(TBEBWMF1Vo bwmVo) throws Exception
   {
      String preBpd = "0";

      StringBuffer               sqlBuff  = new StringBuffer();
      LoggablePreparedStatement  pstmt    = null;
      ResultSet                  rs       = null;
      Connection                 conn     = null;

      sqlBuff.append("  SELECT                        \n");
      sqlBuff.append("     CS126_AMT                  \n");
      sqlBuff.append("  FROM                          \n");
      sqlBuff.append("     SAPHEE.ZCST126             \n");
      sqlBuff.append("  WHERE    1=1                  \n");
      sqlBuff.append("  AND   MANDT     = ?           \n");
      sqlBuff.append("  AND   CS126_UPN = ?           \n");
      sqlBuff.append("  AND   CS126_CST = ?           \n");
      sqlBuff.append("  AND   CS126_PJT = ?           \n");
      sqlBuff.append("  AND   CS126_HNO = ?           \n");
      sqlBuff.append("  AND   CS126_SEQ = SUBSTR(DIGITS(CAST('"+bwmVo.getCebwmseq()+"' AS INTEGER)-1),9,2) \n");

      try
      {
     	 String db_con = Utillity.getSapJndi(bwmVo.getMandt());
         conn  = getConnection(db_con);
         pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

         pstmt.setString(1, bwmVo.getMandt());
         pstmt.setString(2, bwmVo.getCebwmupn());
         pstmt.setString(3, bwmVo.getCebwmcst());
         pstmt.setString(4, bwmVo.getCebwmpjt());
         pstmt.setString(5, bwmVo.getCebwmhno());

       //  logger.debug("\n" + ((LoggablePreparedStatement) pstmt).getQueryString());
         rs = pstmt.executeQuery();
         if (rs.next())
         {
            preBpd = String.valueOf(rs.getBigDecimal("CS126_AMT").doubleValue()).toString();
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
      finally
      {
         try
         {
            DBUtil.close(rs, pstmt, null);
         }
         catch (Exception e)
         {
         }
      }
      return preBpd;
   }
   
   
   /**
	 * 작성자: bgt 작성일: 2021-03-30 설 명: 자회사 무상공사/일반 에 대한 기성비 산출 기 타:
	 */
  public ArrayList getCalcGisungbi1_j(TBEBWEF1Vo bweVo) throws BizException
  {
     ArrayList list = new ArrayList();

     int gaewolsu = 0;
     int gaewolsusum = 0;
     //int gaewolsugap = 0;
     int basGaewol = 0;
     int sumGaewol = 0;
     String baljuStd = "";
     String baljuEtd = "";
     boolean mangunFlg = false;
     boolean defaultGisungbiFlg = true;	//기본기성FLAG (기본기성->3개월)
     //boolean threeMonthFlg = false;
     String fdt = "";
     String tod = "";
     double calcDays = 0;
     double jdq = 0;
     double bda = 0;
     double jyq = 0;
     double bya = 0;
     double t_bya = 0;

     try
     {
        basGaewol = 0;
        gaewolsu = DateTime.monthsBetween(bweVo.getCebwebgt().substring(0,6)+"01", bweVo.getCebwebmt().substring(0,6)+DateTime.lastDayOfMonth(bweVo.getCebwebmt()).substring(6,8));
        
        gaewolsusum = Integer.parseInt(bweVo.getCevwezmusosum()); // 무상공사 + 무상일반
        //gaewolsugap = Integer.parseInt(bweVo.getCevwezmusogap()); // 무상공사 + 무상일반 - 무상공사사용 - 무상일반사용
       
        sumGaewol = gaewolsusum - 1;
        
        baljuStd = bweVo.getCebwebgt();
        baljuEtd = bweVo.getCebwebmt();
        CommonUtil cmu = new CommonUtil();
        
        for(int i=1;i <= gaewolsu; i++)
        {
           TBEBXRF1Vo rtnVo = new TBEBXRF1Vo();

           if(i==1)
              fdt = baljuStd;
           else
           {
              if(basGaewol < 3)
              {
           	  //무상 3개월 기본기성인 경우
                 //defaultGisungbiFlg = true;
                 fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";
              }
              else
              {
           	  // 무상 3개월 초과
                 if(basGaewol == 3)
                 {
               	 //1일부터 시작하는 경우
                    if("01".equals(baljuStd.substring(6,8)))
                    {
                       fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";
                       //defaultGisungbiFlg = false;
                    }
                    //일할 계산 필요한 경우
                    else
                    {
                       fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";
                       tod = DateTime.addDays(DateTime.addMonths(baljuStd, basGaewol), -1);
                       calcDays = DateTime.daysBetween(fdt, tod) + 1;
                       jdq = calcDays;
                       
                       //bda = getBasGisungbi(0);
                       if("A".equals(bweVo.getCebweabg())) {  // ==>  아파트 or 빌링으로 구분 해야함
                     	  bda = getBasGisungbi_ja(sumGaewol);
                       } else {           	
                           bda = getBasGisungbi_jb(sumGaewol);
                       }

                       bya = bda - t_bya;
                       list.add(setBxrVoByGisungbi1(fdt, tod, jdq, bda, bya, 0));

                       if( DateTime.daysBetween( DateTime.addDays(DateTime.addMonths(baljuStd, basGaewol), -1), baljuEtd) > 0 )
                       {
                          fdt = DateTime.addDays(tod , 1);
                          tod = DateTime.lastDayOfMonth(fdt);
                          calcDays = DateTime.daysBetween(fdt, tod) + 1;
                          jdq = calcDays;
                          
                          /* 2012.09.18 LJH 정률제 처리 */
                          //bda = getBasGisungbi1_new(bweVo);
                          
                          if("A".equals(bweVo.getCebweabg())) {  // ==>  아파트 or 빌링으로 구분 해야함
                        	  bda = getBasGisungbi_ja(sumGaewol);
                          } else {           	
                              bda = getBasGisungbi_jb(sumGaewol);
                          }
                          
                          bya = Double.parseDouble(cmu.setFrm((((bda / 30) * jdq)),"7"));
                          t_bya = bya;
                      	  list.add(setBxrVoByGisungbi1(fdt, tod, jdq, bda, bya, 0));
                       }
                       //defaultGisungbiFlg = false;
                       basGaewol++;
                       continue;
                    }
                 }

                 else
                 {
                    fdt = DateTime.addMonths(baljuStd, basGaewol).substring(0,6) + "01";
                    //defaultGisungbiFlg = false;
                 }
              }
              
              
           }
           
           if(i == gaewolsu)
              tod = bweVo.getCebwebmt();
           else
              tod = DateTime.lastDayOfMonth(fdt);
           
           mangunFlg = chkMangun(fdt, tod);
           calcDays = DateTime.daysBetween(fdt, tod) + 1;
           
           if(mangunFlg)
           {
              jdq = 30;
              if("A".equals(bweVo.getCebweabg())) {  // ==>  아파트 or 빌링으로 구분 해야함
               	   bda = getBasGisungbi_ja(sumGaewol);
              } else {           	
                   bda = getBasGisungbi_jb(sumGaewol);
              }
              bya = bda;
           }
           //mangunFlg = false 
           else
           {
              jdq = calcDays;
              if("A".equals(bweVo.getCebweabg())) {  // ==>  아파트 or 빌링으로 구분 해야함
            	  bda = getBasGisungbi_ja(sumGaewol);
              } else {
                   bda = getBasGisungbi_jb(sumGaewol);           	   
              }
              if(i == gaewolsu) {
           	   bya = bda - t_bya;
              } else {
           	   bya = Double.parseDouble(cmu.setFrm((((bda / 30) * jdq)),"7"));
              }
              
              if(i == 1) {
           	   t_bya = bya;
              }
           }
           
           System.out.println("bda ======> "+bda);
           list.add(setBxrVoByGisungbi1(fdt, tod, jdq, bda, bya, 0));
           basGaewol++;
        }  // for 문 끝
        
        
        
     }
     catch(Exception e)
     {
        e.printStackTrace();
        throw new BizException("기성비 산출 오류가 있습니다");
     }
     return list;
  }
   
   
   
   
}