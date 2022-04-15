/**
 * 파 일 명 : DBConstants.java
 * 작 성 자 : jkkoo
 * 작 성 일 : 2006-03-10
 * 설    명 : EJB에서 사용할 DataSource등릐 JNDI 에 대한 상수 클래스 
 * 변경내역 :
 */
package com.helco.xf.cs12.evladm.dbutil;

public class DBConstants
{
   
   //2006.03.10 구자경 : DB Connection 명칭 설정
   public   static   final String   HDCSPOOL       = "java:comp/env/udbtr1DS";
   public   static   final String   EVLADMPOOL     = "java:comp/env/udbtr2DS";
   public   static   final String   ELDB2ADMPOOL   = "java:comp/env/udbtr2DS";
   public   static   final String   HDCSREFPOOL    = "java:comp/env/udbHdcsDS";
   
   //2006.07.04 조문호 : etest JNDI(DB2ADMIN)
   public   static   final String   DB2ADMINPOOL   = "java:comp/env/udb2DS";
   
   public   static   final String   DB2_HED   = "jdbc:db2://203.242.37.24:5912/hed";
   
   public static final String CSPOOL = "java:comp/env/jdbc/cs";	//-> 수정된 것 
   
   public   static   final int   MU_GONDSA                  = 11; // 무상공사
   public   static   final int   MU_ILBAN                   = 12; // 무상일반
   public   static   final int   MU_CHUGA                   = 13; // 무상추가
   public   static   final int   YU_GONDSA                  = 21; // 유상공사
   public   static   final int   YU_ILBAN_SINGYU            = 31; // 유상일반신규
   public   static   final int   YU_ILBAN_JAEGYEYAK         = 32; // 유상일반재계약
   public   static   final int   YU_ILBAN_BYUNKYUNG_BEFORE  = 33; // 유상일반변경전
   public   static   final int   YU_ILBAN_BYUNKYUNG_AFTER   = 34; // 유상일반변경후
   public   static   final int   YU_ILBAN_TASA_HOISU        = 35; // 타사회수
   public   static   final int   YU_ILBAN_JASA_HOISU        = 36; // 자사회수
   public   static   final int   YU_ILBAN_JADONG_YEONJANG   = 37; // 자동연장
   public   static   final int   SILPAE_BOGO                = 51; // 실패보고
   public   static   final int   ILSI_JUNGJI_SEUNGIN        = 52; // 일시중지승인
   public   static   final int   ILSI_BOKGU_SEUNGIN         = 53; // 일시복구승인
   public   static   final int   JEOMGUM_IGWAN_BEFORE       = 61; // 점검이관전
   public   static   final int   JEOMGUM_IGWAN_AFTER        = 62; // 점검이관후
   public   static   final int   JUNGDO_HAEJI               = 66; // 중도해지
   public   static   final int   INWON_SANGJU               = 71; // 인원상주
}
