package hdel.sd.com.domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.ibatis.session.SqlSession;

public class ThreadLog {
	private static ThreadLocal<ThreadLogMeta> threadLog = new ThreadLocal<ThreadLogMeta>();
	private static String threadName = null;
	private static String filePath = "/srm/HELCO_WEB/HELCO_WEB.war/test/if_test_egis";
	//private static String filePath = "/srm/HELCO_WEB/HELCO_WEB.war/if_test_egis";
	//private static String filePath = "C:\\devWork\\workspace\\HELCO_WEB";
	private static String checkQtnum = "test";
	private static String checkEQtnum = "test";
	private static Calendar cal = Calendar.getInstance();
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");

	public static void threadLogInit(String methdName, String gSysId, SqlSession session, String check1, String check2) {
    	threadName = Thread.currentThread().getName();
    	//System.out.println("threadLogInit:"+threadName);
		String msg = "";
        String sToday = formatter.format(cal.getTime()); //for log-time
        msg = "======= "+ methdName + "start =======\n";
		msg = msg + "["+sToday+"]: start sysid:"+gSysId+", session:"+session.hashCode()+"\n";
		checkQtnum = check1;
		checkEQtnum = check2;

		ThreadLogMeta logMeta = new ThreadLogMeta(); 
        logMeta.setLogMsg(msg);
        threadLog.set(logMeta);
	}

	public static void threadLogInit(String methdName, String gSysId, SqlSession session, String check1) {
    	threadName = Thread.currentThread().getName();
		String msg = "";
        String sToday = formatter.format(cal.getTime()); //for log-time
        msg = "======= "+ methdName + "start =======\n";
        msg = msg + "["+sToday+"]: start sysid:"+gSysId+", session:"+session.hashCode()+"\n";
		checkQtnum = check1;

		ThreadLogMeta logMeta = new ThreadLogMeta(); 
        logMeta.setLogMsg(msg);
        threadLog.set(logMeta);
	}
	
    public static void close(String methdName) {
    	ThreadLogMeta logMeta = threadLog.get();
		String msg = "";
        String sToday = formatter.format(cal.getTime()); //for log-time
        msg = "["+sToday+"]: end\n";
        msg = "======= "+ methdName + "end =======";
        logMeta.setLogMsg(msg);
//        if(logMeta != null) {
   		if(logMeta.getQtnum() != null ) {
   			if("E200015602".equals(logMeta.getQtnum())) {
   			    writelog(logMeta);
   			}
    	}

   		threadLog.remove();
    }

    public static void setLog(String methodName, String errMsg) {
    	ThreadLogMeta logMeta = threadLog.get();
		String msg = "";
        String sToday = formatter.format(cal.getTime()); //for log-time
		msg = methodName+"["+sToday+"]: errMsg:"+errMsg+"\n";
        logMeta.setLogMsg(msg);
    }
    
    public static void setLog(String methodName, String qtnum, String qtser, String eQtnum, int index, SqlSession session, String logMsg) {
    	ThreadLogMeta logMeta = threadLog.get();
    	logMeta.setQtnum(qtnum);
    	logMeta.setEqtnum(eQtnum);
//		if(logMeta.getQtnum().equals(checkQtnum) || logMeta.getEqtnum().equals(checkEQtnum) ) {
		if(qtnum != null ) {
			if("E200015602".equals(qtnum)) {
				String msg = "";
		        String sToday = formatter.format(cal.getTime()); //for log-time
				msg = methodName+"["+sToday+"]: session:"+session.hashCode()+", qtnum:"+qtnum+", qtser:"+qtser+", eqtnum:"+eQtnum+", logMsg:"+logMsg+"\n";
		        logMeta.setLogMsg(msg);
			}
		}
    }

    public static void setLog(String methodName, String qtnum, String qtser, String eQtnum, int index, String logMsg) {
    	ThreadLogMeta logMeta = threadLog.get();
    	logMeta.setQtnum(qtnum);
    	logMeta.setEqtnum(eQtnum);
		if(qtnum != null ) {
			if("E200015602".equals(qtnum)) {
				String msg = "";
		        String sToday = formatter.format(cal.getTime()); //for log-time
				msg = methodName+"["+sToday+"]: session:n/a, qtnum:"+qtnum+", qtser:"+qtser+", eqtnum:"+eQtnum+", logMsg:"+logMsg+"\n";
		        logMeta.setLogMsg(msg);
			}
		}

//    	if(logMeta.getQtnum().equals(checkQtnum) || logMeta.getEqtnum().equals(checkEQtnum) ) {
//		}
    }
    
    private static synchronized void writelog(ThreadLogMeta logMeta)
    {
        String sToday = formatter.format(cal.getTime()); //for log-time
        String sFileName = filePath+ File.separator + checkQtnum+"_"+sToday +".log";
    	//System.out.println("threadLog sFileName:"+sFileName);
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sFileName, true));
            bw.write(logMeta.getLogMsg().toString());
            bw.newLine();
            bw.close();
        }
        catch(IOException ie)
        {
            ie.printStackTrace();
        }
    }
}

