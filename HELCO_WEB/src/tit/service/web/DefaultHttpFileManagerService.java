//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tit.service.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tit.base.ServiceBase;
import tit.base.ServiceManagerFactory;
import tit.service.miplatform.MiplatformBusinessContext;
import tit.util.CsvArrayList;
import tit.util.StringOperator;
import tit.util.TitUtility;

public class DefaultHttpFileManagerService extends ServiceBase implements DefaultHttpFileManagerServiceMBean, HttpFileManager {
    protected String mRootDir = null;
    protected String mWebRootDir = null;
    protected Properties mSubDirInfo = null;
    protected boolean mAppendTime = true;
    private static int BUFFER_SIZE = 10240;
    protected String mSystemEncoding = null;
    protected boolean mSupportDynamicDir = false;
    protected String mFileParamName = "FileParam";
    protected boolean mUseURIEncoding = false;
    protected String[] mUnapprovedFileExtension = null;
    protected String[] mApprovedFileExtension = null;

    public DefaultHttpFileManagerService() {
    }

    public String getFileRootDir() {
        return this.mRootDir;
    }

    public Properties getSubDirList() {
        return this.mSubDirInfo;
    }

    public boolean isAppendTime() {
        return this.mAppendTime;
    }

    public void setAppendTime(boolean var1) {
        this.mAppendTime = var1;
    }

    public void setFileRootDir(String var1) {
        this.mRootDir = var1;
        this.mWebRootDir = var1;
        String var2 = ServiceManagerFactory.getProperty("WORK_DIR");
        if (var2 == null) {
            var2 = "";
        }

        if (!this.mRootDir.startsWith("/") && this.mRootDir.indexOf(":") == -1) {
            this.mRootDir = var2 + this.mRootDir;
        }

    }

    public void setSubDirList(Properties var1) {
        this.mSubDirInfo = var1;
    }

    public String getFileParamName() {
        return this.mFileParamName;
    }

    public void setFileParamName(String var1) {
        this.mFileParamName = var1;
    }

    public void delete(HttpServletRequest var1, HttpServletResponse var2) throws Exception {
        String var3 = "";
        boolean var4 = false;

        try {
            Map var5 = this.getRequestParameterValue(var1);
            String var6 = (String)var5.get("fileDir");
            if (var6 == null || var6.equals("")) {
                var6 = (String)var5.get("subDir");
            }

            String var7 = this.getSaveDirPath(var6, false);
            if (!var7.substring(var7.length() - 1).equals("/")) {
                var7 = var7 + "/";
            }

            String var8 = (String)var5.get("fileName");
            this.invalidFileName(var8);
            File var9 = new File(var7, var8);
            if (var9.exists()) {
                var9.delete();
                var3 = "OK";
                var4 = true;
            } else {
                var3 = var8 + " is not found.";
                var4 = false;
            }
        } catch (Exception var14) {
            var3 = var14.getMessage();
        } finally {
            ;
        }

        MiplatformBusinessContext var16 = new MiplatformBusinessContext(var2);
        var16.addOutputVariable("_FILE_PROC_RTN", var4 ? "1" : "0");
        var16.addOutputVariable("_FILE_PROC_MSG", var3);
        var16.makeSuccessResult(var3);
        var16.sendData();
    }

    public boolean delete(String var1, String var2) throws Exception {
        boolean var3 = false;

        try {
            String var4 = this.getSaveDirPath(var1, false);
            if (!var4.substring(var4.length() - 1).equals("/")) {
                var4 = var4 + "/";
            }

            this.invalidFileName(var2);
            File var6 = new File(var4, var2);
            if (var6.exists()) {
                var6.delete();
                var3 = true;
            }
        } catch (Exception var7) {
            var3 = false;
        }

        return var3;
    }

    public boolean delete(String var1, String[] var2) throws Exception {
        boolean var3 = false;

        try {
            String var4 = this.getSaveDirPath(var1, false);
            if (!var4.substring(var4.length() - 1).equals("/")) {
                var4 = var4 + "/";
            }

            String var5 = "";

            for(int var6 = 0; var6 < var2.length; ++var6) {
                var5 = var2[var6];
                this.invalidFileName(var5);
                File var7 = new File(var4, var5);
                if (var7.exists()) {
                    var7.delete();
                    var3 = true;
                }
            }
        } catch (Exception var8) {
            var3 = false;
        }

        return var3;
    }

    public void download(HttpServletRequest var1, HttpServletResponse var2) throws Exception {
        BufferedInputStream var3 = null;
        ServletOutputStream var4 = null;
        String var5 = "";
        boolean var6 = true;

        try {
            String[] var7 = null;
            Map var8 = this.getRequestParameterValue(var1);
            String var9 = (String)var8.get(this.mFileParamName);
            if (var9 != null && !var9.trim().equals("")) {
                CsvArrayList var10 = new CsvArrayList();
                var10.split(var9, "|");
                var7 = var10.toStringArray();
            }

            if (var7 != null && var7.length >= 2) {
                var6 = false;
            } else {
                var7 = this.getRequestValue(var1);
                if (var7 == null || var7.length < 2) {
                    var5 = "E|Request information is invalid.";
                    Cookie var24 = new Cookie(this.mFileParamName, this.kscToAsc(var5));
                    var2.setContentType("text/html");
                    var2.addCookie(var24);
                    return;
                }
            }

            String var25 = this.getSaveDirPath(var7[0], false);
            String var11 = var7[1];
            this.invalidFileName(var11);
            File var12 = new File(var25, var11);
            if (var12.exists()) {
                var3 = new BufferedInputStream(new FileInputStream(var12));
                var4 = var2.getOutputStream();
                byte[] var13 = new byte[BUFFER_SIZE];
                boolean var14 = false;

                int var26;
                while((var26 = var3.read(var13)) != -1) {
                    var4.write(var13, 0, var26);
                }

                var4.flush();
                if (var7.length == 3 && var7[2] != null && var7[2].equals("Y")) {
                    var12.delete();
                }

                var5 = "S|S";
            } else {
                var5 = "E|File Not Found.";
            }
        } catch (Exception var21) {
            var5 = "E|" + var21.getMessage();
        } finally {
            if (var3 != null) {
                var3.close();
            }

            if (var4 != null) {
                var4.close();
            }

        }

        try {
            var5 = URLEncoder.encode(var5, this.mSystemEncoding == null ? "utf-8" : this.mSystemEncoding);
            var5 = StringOperator.replaceString(var5, "+", " ");
        } catch (UnsupportedEncodingException var20) {
        }

        Cookie var23 = new Cookie(this.mFileParamName, this.kscToAsc(var5));
        var2.setContentType("text/html");
        var2.addCookie(var23);
        if (!var6) {
            if (var5.startsWith("E")) {
                var2.sendError(500, this.kscToAsc(var5));
            } else {
                var2.getWriter().println(this.kscToAsc(var5));
            }
        }

    }

    public void downloadWeb(HttpServletRequest var1, HttpServletResponse var2) throws Exception {
        try {
            try {
                Map var3 = this.getRequestParameterValue(var1);
                String var4 = (String)var3.get("fileFullNm");
                String var5 = (String)var3.get("fileDir");
                if (var5 == null || var5.equals("")) {
                    var5 = (String)var3.get("subDir");
                }

                String var6 = this.getSaveDirPath(var5, false);
                String var7 = (String)var3.get("fileName");
                File var8 = null;
                String var9 = URLEncoder.encode(var7, "utf-8");
                if (var4 != null && !var4.equals("")) {
                    var9 = var4.substring(var4.lastIndexOf("/") + 1);
                    this.invalidFileName(var4);
                    var8 = new File(var4);
                } else {
                    this.invalidFileName(var7);
                    var8 = new File(var6, var7);
                }

                String var10 = var1.getParameter("downloadFileNm");
                if (var10 != null && !var10.equals("")) {
                    var10 = (String)var3.get("downloadFileNm");
                    var9 = URLEncoder.encode(var10, "utf-8");
                }

                byte[] var11 = new byte[4096];
                var2.setCharacterEncoding(this.mSystemEncoding == null ? var1.getCharacterEncoding() : this.mSystemEncoding);
                String var12 = var1.getHeader("User-Agent");
                var2.setContentType("application/octet-stream;" + (this.mSystemEncoding == null ? "" : "charset=" + this.mSystemEncoding));
                var2.setHeader("Accept-Ranges", "bytes");
                if (var9 != null) {
                    var9 = var9.replace('+', ' ');
                }

                if (var12.indexOf("MSIE 5.5") > -1) {
                    var2.setHeader("Content-Disposition", "filename=" + var9);
                } else {
                    var2.setHeader("Content-Disposition", "attachment;filename=" + var9);
                }

                var2.setHeader("Pragma", "no-cache");
                var2.setHeader("Expires", "0");
                if (var8.isFile()) {
                    BufferedInputStream var13 = new BufferedInputStream(new FileInputStream(var8));
                    BufferedOutputStream var14 = new BufferedOutputStream(var2.getOutputStream());
                    boolean var15 = false;

                    try {
                        int var33;
                        try {
                            while((var33 = var13.read(var11)) != -1) {
                                var14.write(var11, 0, var33);
                            }
                        } catch (Exception var29) {
                        }
                    } finally {
                        if (var14 != null) {
                            var14.close();
                        }

                        if (var13 != null) {
                            var13.close();
                        }

                    }
                } else {
                    this.getLogger().debug("File not found[" + var8.getName() + "]");
                }
            } catch (Exception var31) {
            }

        } finally {
            ;
        }
    }

    public void upload(HttpServletRequest var1, HttpServletResponse var2) throws Exception {
        BufferedInputStream var3 = null;
        FileOutputStream var4 = null;
        String var5 = "";
        boolean var6 = true;

        try {
            String[] var7 = null;
            Map var8 = this.getRequestParameterValue(var1);
            String var9 = (String)var8.get(this.mFileParamName);
            if (var9 != null && !var9.trim().equals("")) {
                CsvArrayList var10 = new CsvArrayList();
                var10.split(var9, "|");
                var7 = var10.toStringArray();
            }

            if (var7 != null && var7.length >= 3) {
                var6 = false;
            } else {
                var7 = this.getRequestValue(var1);
                if (var7 == null || var7.length < 3) {
                    var5 = "E|Request information is invalid.";
                    Cookie var32 = new Cookie(this.mFileParamName, this.kscToAsc(var5));
                    var2.setContentType("text/html");
                    var2.addCookie(var32);
                    return;
                }
            }

            String var33 = var7[0];
            String var11 = this.getSaveDirPath(var7[1], true);
            String var12 = var7[2];
            String var13 = null;
            boolean var14 = true;
            if (var7.length == 4) {
                var13 = var7[3];
            }

            if (!var11.substring(var11.length() - 1).equals("/")) {
                var11 = var11 + "/";
            }

            String var15 = "";
            if (var7.length == 5) {
                if (var7[4] != null && var7[4].equalsIgnoreCase("N")) {
                    var14 = false;
                }

                if (var7[4] != null && var7[4].equalsIgnoreCase("F")) {
                    var14 = false;
                    var15 = "F";
                }
            }

            long var16 = 0L;
            if (var33.equals("D") || var33.equals("A")) {
                var12 = this.getSaveFileName(var7[2]);
            }

            this.invalidFileName(var12);
            String[] var18 = new String[]{"\\", "/", ":", "*", "?", "\"", "'", "<", ">", "|"};
            String[] var19 = var18;
            int var20 = var18.length;

            for(int var21 = 0; var21 < var20; ++var21) {
                String var22 = var19[var21];
                var12 = StringOperator.replaceString(var12, var22, "_");
            }

            File var34;
            if (var33.equals("N")) {
                var34 = new File(var11, var12);
                if (var34.exists()) {
                    if (var14) {
                        var5 = "E|" + var12 + " is already exists.";
                    } else {
                        var5 = "E|File is already exists.";
                    }

                    if (var6) {
                        Cookie var36 = new Cookie(this.mFileParamName, this.kscToAsc(var5));
                        var2.setContentType("text/html");
                        var2.addCookie(var36);
                    } else {
                        var2.sendError(500, this.kscToAsc(var5));
                    }

                    return;
                }
            } else if (var33.equals("A")) {
                var34 = new File(var11, var12);
                if (var34.exists()) {
                    var34.delete();
                }
            }

            if (var13 != null && !var13.trim().equals("")) {
                this.invalidFileName(var13);
                var34 = new File(var11, var13);
                if (var34.exists()) {
                    var34.delete();
                }
            }

            var3 = new BufferedInputStream(var1.getInputStream());
            var4 = new FileOutputStream(var11 + var12);
            byte[] var35 = new byte[BUFFER_SIZE];

            while(true) {
                var20 = var3.read(var35);
                if (var20 <= 0) {
                    var4.flush();

                    try {
                        if (var15.equals("F")) {
                            var5 = URLEncoder.encode("S||" + var12 + "|" + 0, this.mSystemEncoding == null ? "utf-8" : this.mSystemEncoding);
                        } else if (var14) {
                            var5 = URLEncoder.encode("S|" + var11 + "|" + var12 + "|" + var16, this.mSystemEncoding == null ? "utf-8" : this.mSystemEncoding);
                        } else {
                            var5 = URLEncoder.encode("S|ok", this.mSystemEncoding == null ? "utf-8" : this.mSystemEncoding);
                        }

                        var5 = StringOperator.replaceString(var5, "+", " ");
                    } catch (UnsupportedEncodingException var28) {
                        if (var15.equals("F")) {
                            var5 = "S||" + var12 + "|" + 0;
                        } else if (var14) {
                            var5 = "S|" + var11 + "|" + var12 + "|" + var16;
                        } else {
                            var5 = "S|ok";
                        }
                    }
                    break;
                }

                var16 += (long)var20;
                var4.write(var35, 0, var20);
            }
        } catch (Exception var29) {
            var29.printStackTrace();
            var5 = "E|" + var29.getMessage();
        } finally {
            if (var3 != null) {
                var3.close();
            }

            if (var4 != null) {
                var4.close();
            }

        }

        Cookie var31 = new Cookie(this.mFileParamName, this.kscToAsc(var5));
        var2.setContentType("text/html");
        var2.addCookie(var31);
        if (!var6) {
            if (var5.startsWith("E")) {
                var2.sendError(500, this.kscToAsc(var5));
            } else {
                var2.getWriter().println(this.kscToAsc(var5));
            }
        }

    }

    public void startService() throws Exception {
        if (this.mRootDir != null && !this.mRootDir.equals("")) {
            if (this.mSystemEncoding == null) {
                String var1 = System.getProperty("file.encoding");
                if (var1 != null && !var1.equals("")) {
                    try {
                        (new String("")).getBytes(var1);
                    } catch (UnsupportedEncodingException var3) {
                    }
                }
            }

        } else {
            throw new IllegalArgumentException(this.getMessageRecordFactory().findEmbedMessage("COMSVC0001", "File Root Directory"));
        }
    }

    protected String[] getRequestValue(HttpServletRequest var1) {
        Cookie[] var2 = var1.getCookies();
        String var3 = "";
        if (var2 != null) {
            for(int var4 = 0; var4 < var2.length; ++var4) {
                if (var2[var4].getName().equals(this.mFileParamName)) {
                    try {
                        var3 = URLDecoder.decode(var2[var4].getValue(), this.mSystemEncoding == null ? "utf-8" : this.mSystemEncoding);
                    } catch (UnsupportedEncodingException var8) {
                        try {
                            var3 = URLDecoder.decode(var2[var4].getValue(), "euc-kr");
                        } catch (UnsupportedEncodingException var7) {
                            var3 = var2[var4].getValue();
                        }
                    }
                    break;
                }
            }
        }

        CsvArrayList var9 = new CsvArrayList();
        var9.split(var3, "|");
        String[] var5 = var9.toStringArray();
        return var5;
    }

    protected Map getRequestParameterValue(HttpServletRequest var1) {
        String var2 = var1.getCharacterEncoding();
        boolean var3 = var1.getMethod() != null && var1.getMethod().equalsIgnoreCase("GET") && this.mUseURIEncoding;
        HashMap var4 = new HashMap();
        Enumeration var5 = var1.getParameterNames();

        while(var5.hasMoreElements()) {
            String var6 = (String)var5.nextElement();
            String var7 = var3 ? var1.getParameter(var6) : this.ascToKsc(var1.getParameter(var6), var2);
            var4.put(var6, var7);
        }

        return var4;
    }

    protected String getSaveFileName(String var1) {
        String var2 = "";
        String var3 = var1;
        int var4 = var1.lastIndexOf(".");
        if (var4 != -1) {
            var2 = var1.substring(var4);
            var3 = var1.substring(0, var4);
        }

        if (this.isAppendTime()) {
            var3 = var3 + "_" + TitUtility.getDateTimeString("yyyyMMddHHmmssSSS");
        }

        return var3 + var2;
    }

    protected String getSaveDirPath(String var1, boolean var2) {
        String var3 = this.getRealDirPath(var1);
        if (var2) {
            File var4 = new File(var3);
            if (!var4.exists()) {
                var4.mkdirs();
            }
        }

        return var3;
    }

    public String getRealDirPath(String var1) {
        String var2 = this.mRootDir;
        if (TitUtility.isNotNull(var1)) {
            if (var1.indexOf("../") >= 0 || var1.indexOf("..\\") >= 0) {
                throw new IllegalArgumentException("invalid file directory path.");
            }

            if (this.mSubDirInfo != null) {
                var2 = this.mSubDirInfo.getProperty(var1);
                if (TitUtility.isNull(var2)) {
                    if (this.mSupportDynamicDir) {
                        var2 = this.mRootDir + var1;
                    } else {
                        var2 = this.mRootDir;
                    }
                } else if (!var2.startsWith("/") && var2.indexOf(":") == -1) {
                    var2 = this.mRootDir + var2;
                }
            }
        }

        return var2;
    }

    private void invalidFileName(String var1) {
        FileInfo.invalidFileName(var1, this.mUnapprovedFileExtension);
        FileInfo.validFileExtension(var1, this.mApprovedFileExtension);
    }

    private String kscToAsc(String var1) {
        if (var1 == null) {
            var1 = "";
        }

        return var1;
    }

    private String ascToKsc(String var1, String var2) {
        var2 = "8859_1";

        try {
            if (var1 != null && this.mSystemEncoding != null) {
                var1 = new String(var1.getBytes(var2), this.mSystemEncoding);
            }
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        return var1;
    }

    public Map getSaveDirList() {
        HashMap var1 = new HashMap();
        var1.put("_root", this.mWebRootDir);
        Properties var2 = this.getSubDirList();
        Iterator var3 = var2.keySet().iterator();
        String var4 = null;

        while(var3.hasNext()) {
            var4 = (String)var3.next();
            var1.put(var4, var2.get(var4));
        }

        return var1;
    }

    public boolean copy(String var1, String var2, String var3, String var4, boolean var5) throws Exception {
        boolean var6 = false;

        try {
            this.invalidFileName(var3);
            this.invalidFileName(var4);
            File var7 = new File(this.getSaveDirPath(var1, false), var3);
            File var8 = new File(this.getSaveDirPath(var2, false));
            File var9 = new File(this.getSaveDirPath(var2, false), var4);
            if (!var8.exists()) {
                var8.mkdirs();
            }

            if (var7.exists()) {
                FileInputStream var10 = null;
                FileOutputStream var11 = null;

                try {
                    var10 = new FileInputStream(var7);
                    var11 = new FileOutputStream(var9);
                    byte[] var12 = new byte[BUFFER_SIZE];

                    while(true) {
                        int var13 = var10.read(var12);
                        if (var13 <= 0) {
                            var11.flush();
                            break;
                        }

                        var11.write(var12, 0, var13);
                    }
                } catch (IOException var19) {
                    var19.printStackTrace();
                    throw var19;
                } finally {
                    if (var10 != null) {
                        var10.close();
                    }

                    if (var11 != null) {
                        var11.close();
                    }

                }

                if (var5) {
                    var7.delete();
                }

                var6 = true;
                return var6;
            } else {
                throw new IllegalArgumentException(var3 + " is not found.");
            }
        } catch (Exception var21) {
            throw var21;
        }
    }

    public void copy(HttpServletRequest var1, HttpServletResponse var2) throws Exception {
        String var3 = "";
        boolean var4 = false;

        try {
            Map var5 = this.getRequestParameterValue(var1);
            String var6 = (String)var5.get("srcFileDir");
            String var7 = (String)var5.get("targetFileDir");
            String var8 = (String)var5.get("srcFileName");
            String var9 = (String)var5.get("targetFileName");
            String var10 = (String)var5.get("isDelete");
            var4 = this.copy(var6, var7, var8, var9, var10 != null && var10.equals("Y"));
        } catch (IllegalArgumentException var11) {
            var4 = false;
            var3 = var11.getMessage();
        } catch (Exception var12) {
            var4 = false;
            var3 = var12.getMessage();
        }

        MiplatformBusinessContext var13 = new MiplatformBusinessContext(var2);
        var13.addOutputVariable("_FILE_PROC_RTN", var4 ? "1" : "0");
        var13.addOutputVariable("_FILE_PROC_MSG", var3);
        var13.makeSuccessResult(var3);
        var13.sendData();
    }

    public void setSystemEncoding(String var1) {
        this.mSystemEncoding = var1;
    }

    public String getSystemEncoding() {
        return this.mSystemEncoding;
    }

    public boolean isSupportDynamicDir() {
        return this.mSupportDynamicDir;
    }

    public void setSupportDynamicDir(boolean var1) {
        this.mSupportDynamicDir = var1;
    }

    public void setUseURIEncoding(boolean var1) {
        this.mUseURIEncoding = var1;
    }

    public boolean getUseURIEncoding() {
        return this.mUseURIEncoding;
    }

    public String[] getUnapprovedFileExtension() {
        return this.mUnapprovedFileExtension;
    }

    public void setUnapprovedFileExtension(String[] var1) {
        this.mUnapprovedFileExtension = var1;
    }

    public String[] getApprovedFileExtension() {
        return this.mApprovedFileExtension;
    }

    public void setApprovedFileExtension(String[] var1) {
        this.mApprovedFileExtension = var1;
    }

    public String[] getAvailable() {
        return this.mApprovedFileExtension;
    }

    public void setAvailable(String[] var1) {
        this.mApprovedFileExtension = var1;
    }
}
