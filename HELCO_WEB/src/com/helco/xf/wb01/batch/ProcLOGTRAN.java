/**
 * 파 일 명 : ProcMSGTRAN.java
 * 설    명 : 기간 만료 쪽지 삭제 batch 처리
 *            service-helcoWeb.xml에 스케줄잡이 등록되어 매일 새벽에 실행 됨.
 *            로그파일 위치는 SystemOut.log파일에 저장됨.
 *            예) /usr/WAS/AppServer/profiles/AppSrv01/logs/server1/SystemOut.log
 * 작 성 자 : HSS
 * 작 성 일 : 2009-04-23 17:09
 * 변경내역 :
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
	  System.out.println("사용자 로그 자동 삭제 BATCH 프로세스 START : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
	  System.out.println("==============================================================================\n");
  
      File fDir = new File(PATH);
      File wDir = new File(PATH1);

      FileFilter mfilter = new FileFilter("*"+dayOfMonth+"*.log");
      FileFilter dfilter = new FileFilter("*"+dayOfPrev+"*.log");
      FileFilter wfilter = new FileFilter("*"+dayOfWeek+"*.log");

      File[] mfiles = fDir.listFiles(mfilter); //monthly log
      File[] dfiles = fDir.listFiles(dfilter); //yesterday log
      File[] wfiles = wDir.listFiles(wfilter); //weekly log
     
      // old log 파일 삭제
      System.out.println("==============================================================================");
      System.out.println("2달전 로그 삭제 : " + mfiles.length + " 개 ");
      System.out.println("==============================================================================\n");

      for (int i=0; i< mfiles.length; i++)
      {
    	  mfiles[i].delete();
      }
       
      // 전일 log 파일 weeklyLogs 로 복사
      System.out.println("==============================================================================");
      System.out.println("전일로그  복사 : " + dfiles.length + " 개 ");
      System.out.println("==============================================================================\n");

      for (int i=0; i< dfiles.length; i++)
      {
      	CommonUtil.filecopy(PATH+"/"+dfiles[i].getName(), PATH1+"/"+dfiles[i].getName());
      }

      // weekly log 중 1주이상된 log 삭제
      System.out.println("==============================================================================");
      System.out.println("weekly 로그  삭제 : " + wfiles.length + " 개 ");
      System.out.println("==============================================================================\n");

      for (int i=0; i< wfiles.length; i++)
      {
      	wfiles[i].delete();
      }
      
      System.out.println("==============================================================================");
      System.out.println("사용자 로그 자동 삭제 BATCH 프로세스 END : " + CommonUtil.getToday() + "  " + CommonUtil.getCurrentTimeHHMMSS());
      System.out.println("==============================================================================\n");
   } 
}
