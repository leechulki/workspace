/**
 * �� �� �� : DBConstants.java
 * �� �� �� : jkkoo
 * �� �� �� : 2006-03-10
 * ��    �� : EJB���� ����� DataSource��l JNDI �� ���� ��� Ŭ���� 
 * ���泻�� :
 */
package com.helco.xf.cs12.evladm.dbutil;

public class DBConstants
{
   
   //2006.03.10 ���ڰ� : DB Connection ��Ī ����
   public   static   final String   HDCSPOOL       = "java:comp/env/udbtr1DS";
   public   static   final String   EVLADMPOOL     = "java:comp/env/udbtr2DS";
   public   static   final String   ELDB2ADMPOOL   = "java:comp/env/udbtr2DS";
   public   static   final String   HDCSREFPOOL    = "java:comp/env/udbHdcsDS";
   
   //2006.07.04 ����ȣ : etest JNDI(DB2ADMIN)
   public   static   final String   DB2ADMINPOOL   = "java:comp/env/udb2DS";
   
   public   static   final String   DB2_HED   = "jdbc:db2://203.242.37.24:5912/hed";
   
   public static final String CSPOOL = "java:comp/env/jdbc/cs";	//-> ������ �� 
   
   public   static   final int   MU_GONDSA                  = 11; // �������
   public   static   final int   MU_ILBAN                   = 12; // �����Ϲ�
   public   static   final int   MU_CHUGA                   = 13; // �����߰�
   public   static   final int   YU_GONDSA                  = 21; // �������
   public   static   final int   YU_ILBAN_SINGYU            = 31; // �����Ϲݽű�
   public   static   final int   YU_ILBAN_JAEGYEYAK         = 32; // �����Ϲ�����
   public   static   final int   YU_ILBAN_BYUNKYUNG_BEFORE  = 33; // �����Ϲݺ�����
   public   static   final int   YU_ILBAN_BYUNKYUNG_AFTER   = 34; // �����Ϲݺ�����
   public   static   final int   YU_ILBAN_TASA_HOISU        = 35; // Ÿ��ȸ��
   public   static   final int   YU_ILBAN_JASA_HOISU        = 36; // �ڻ�ȸ��
   public   static   final int   YU_ILBAN_JADONG_YEONJANG   = 37; // �ڵ�����
   public   static   final int   SILPAE_BOGO                = 51; // ���к���
   public   static   final int   ILSI_JUNGJI_SEUNGIN        = 52; // �Ͻ���������
   public   static   final int   ILSI_BOKGU_SEUNGIN         = 53; // �Ͻú�������
   public   static   final int   JEOMGUM_IGWAN_BEFORE       = 61; // �����̰���
   public   static   final int   JEOMGUM_IGWAN_AFTER        = 62; // �����̰���
   public   static   final int   JUNGDO_HAEJI               = 66; // �ߵ�����
   public   static   final int   INWON_SANGJU               = 71; // �ο�����
}
