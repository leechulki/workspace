/**
 * �� �� �� : ProcMSGTRAN.java
 * ��    �� : �Ⱓ ���� ���� ���� batch ó��
 *            service-helcoWeb.xml�� ���������� ��ϵǾ� ���� ������ ���� ��.
 *            �α����� ��ġ�� SystemOut.log���Ͽ� �����.
 *            ��) /usr/WAS/AppServer/profiles/AppSrv01/logs/server1/SystemOut.log
 * �� �� �� : HSS
 * �� �� �� : 2009-04-23 17:09
 * ���泻�� :
 *
 * struts-Config : 
 * 
 * 
 */
package com.helco.xf.wb01.batch;

import java.io.File;

import tit.biz.AbstractBusinessService;
import tit.service.scheduler.ScheduleTask;

import com.helco.xf.cs12.evladm.dbutil.CommonUtil;


public class ProcLOGTRAN extends AbstractBusinessService implements ScheduleTask
{
   private static String PATH = "/usr/WAS/AppServer/profiles/AppSrv01/installedApps/elswebCell01/HELCO_WEB.ear/logs";
   private static String PATH1 = "/usr/WAS/AppServer/profiles/AppSrv01/installedApps/elswebCell01/HELCO_WEB.ear/weeklyLogs";
   //private static String PATH = "D:/dev/work_src/HELCO_WEB/logs";
   //private static String PATH1 = "D:/dev/work_src/HELCO_WEB/weeklyLogs";
   
   public void run() throws Exception {
	  String dayOfMonth = CommonUtil.addMonthDate(-1);
	  String dayOfWeek = CommonUtil.addWeeks(-1);
	  String dayOfPrev = CommonUtil.getPrevDate(1);

	  System.out.println("==============================================================================");
	  System.out.println("����� �α� �ڵ� ���� BATCH ���μ��� START : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
	  System.out.println("==============================================================================\n");
  
      File fDir = new File(PATH);
      File wDir = new File(PATH1);

      FileFilter mfilter = new FileFilter("*"+dayOfMonth+"*.log");
      FileFilter dfilter = new FileFilter("*"+dayOfPrev+"*.log");
      FileFilter wfilter = new FileFilter("*"+dayOfWeek+"*.log");

      File[] mfiles = fDir.listFiles(mfilter); //monthly log
      File[] dfiles = fDir.listFiles(dfilter); //yesterday log
      File[] wfiles = wDir.listFiles(wfilter); //weekly log
     
      // old log ���� ����
      System.out.println("==============================================================================");
      System.out.println("2���� �α� ���� : " + mfiles.length + " �� ");
      System.out.println("==============================================================================\n");

      for (int i=0; i< mfiles.length; i++)
      {
    	  mfiles[i].delete();
      }
       
      // ���� log ���� weeklyLogs �� ����
      System.out.println("==============================================================================");
      System.out.println("���Ϸα�  ���� : " + dfiles.length + " �� ");
      System.out.println("==============================================================================\n");

      for (int i=0; i< dfiles.length; i++)
      {
      	CommonUtil.filecopy(PATH+"/"+dfiles[i].getName(), PATH1+"/"+dfiles[i].getName());
      }

      // weekly log �� 1���̻�� log ����
      System.out.println("==============================================================================");
      System.out.println("weekly �α�  ���� : " + wfiles.length + " �� ");
      System.out.println("==============================================================================\n");

      for (int i=0; i< wfiles.length; i++)
      {
      	wfiles[i].delete();
      }
      
      System.out.println("==============================================================================");
      System.out.println("����� �α� �ڵ� ���� BATCH ���μ��� END : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
      System.out.println("==============================================================================\n");
   } 
}
