//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tit.service.web;

import tit.base.ServiceManagerFactory;
import tit.base.ServiceName;
import tit.beans.ServiceNameEditor;
import tit.biz.session.SessionManager;
import tit.biz.session.UserInfo;
import tit.service.core.logger.Logger;
import tit.service.miplatform.MiplatformBusinessContext;
import tit.util.StringOperator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FtpFileManagerServlet extends HttpServlet {
    private final Logger logger = ServiceManagerFactory.getLogger();
    protected boolean isSessionCheck = false;
    protected ServiceName sessionServiceName = null;

    public FtpFileManagerServlet() {
    }

    public void init(ServletConfig var1) {
        String var4 = var1.getInitParameter("SESSION_SERVICE");
        if (var4 != null && !var4.trim().equals("")) {
            try {
                ServiceNameEditor var5 = new ServiceNameEditor();
                var5.setAsText(var4);
                this.sessionServiceName = (ServiceName) var5.getValue();
            } catch (Exception var6) {
                this.sessionServiceName = null;
            }
        }

        if (var1.getInitParameter("SESSION_CHECK") != null && var1.getInitParameter("SESSION_CHECK").equalsIgnoreCase("true")) {
            this.isSessionCheck = true;
        }

    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map result;
        if (this.isSessionCheck && this.sessionServiceName != null) {
            SessionManager sessionManager = (SessionManager) ServiceManagerFactory.getService(this.sessionServiceName);
            if (sessionManager == null) {
                try {
                    response.setStatus(400);
                    response.getWriter().println("Invalid request session : session service is empty.");
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                return;
            }

            UserInfo userInfo = (UserInfo) sessionManager.findUserInfo(request);
            if (userInfo == null) {
                result = sessionManager.getSessionMap();
                boolean exists = false;
                String sID = request.getParameter("sID");
                if (result != null && sID != null && !sID.equals("")) {
                    Iterator iterator = result.keySet().iterator();

                    while (iterator.hasNext()) {
                        String next = (String) iterator.next();
                        if (next.startsWith(sID)) {
                            exists = true;
                            break;
                        }
                    }
                }

                if (!exists) {
                    try {
                        response.setStatus(400);
                        response.getWriter().println("Invalid request session.");
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                    return;
                }
            }
        }

        String mode = request.getParameter("mode");
        if (mode == null) {
            mode = "";
        }

        FtpFileManagerService fileManager = FtpFileManagerService.getInstance();

        try {
            if (mode.equals("download")) {
                String name = request.getParameter("name");
                String absPath = request.getParameter("path");
                if ((absPath == null || "".equals(absPath)) || (name == null || "".equals(name))) {
                    sendError(response, "E|Invalid Parameter [absPath or name is null]");
                    return;
                }

                InputStream is;
                if (request.getRequestURL().indexOf("localhost") > -1) {
                    is = getFileStreamForLocalhost();
                } else {
                    is = fileManager.getFileStream(absPath);
                }
                if (is == null) {
                    sendError(response, "E|File Not Found.");
                    return;
                }

                BufferedInputStream bufferedInputStream = null;
                ServletOutputStream servletOutputStream = null;

                String _result;

                try {
                    bufferedInputStream = new BufferedInputStream(is);
                    servletOutputStream = response.getOutputStream();
                    byte[] buffer = new byte[10240];

                    int offset;
                    while ((offset = bufferedInputStream.read(buffer)) != -1) {
                        servletOutputStream.write(buffer, 0, offset);
                    }

                    servletOutputStream.flush();

                    _result = "S|S";
                } catch (Exception e) {
                    e.printStackTrace();
                    _result = "E|" + e.getMessage();
                    sendError(response, _result);
                    return;
                } finally {
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }

                    if (servletOutputStream != null) {
                        servletOutputStream.close();
                    }
                }

                try {
                    _result = URLEncoder.encode(_result, "utf-8");
                    _result = StringOperator.replaceString(_result, "+", " ");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    _result = "E|" + e.getMessage();
                    sendError(response, _result);
                    return;
                }

                Cookie cookie = new Cookie(name, this.kscToAsc(_result));
                response.setContentType("text/html");
                response.addCookie(cookie);

            } else if (mode.equals("list")) {
                String cate = request.getParameter("cate");
                String path = request.getParameter("path");
                if ((cate == null || "".equals(cate)) || (path == null || "".equals(path))) {
                    sendError(response, "E|Invalid Parameter [cate or path is null]");
                    return;
                }
                ArrayList<HashMap<String, String>> ret;
                if (request.getRequestURL().indexOf("localhost") > -1) {
                    ret = makeSampleDataForLocalhost(cate);
                } else {
                    ret = fileManager.getFileInfoList(path);
                }
                MiplatformBusinessContext context = new MiplatformBusinessContext(response);
                context.addOutputVariable("_CATE_LIST", getValues(ret, "CATE"));
                context.addOutputVariable("_FNAME_LIST", getValues(ret, "FNAME"));
                context.addOutputVariable("_FPATH_LIST", getValues(ret, "FPATH"));
                context.addOutputVariable("_UDATE_LIST", getValues(ret, "UDATE"));
                context.makeSuccessResult("OK");
                context.sendData();
            } else {
                sendError(response,"E|Parameter[mode] is not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendError(response, e.getMessage());
        }
    }

    private void sendError(HttpServletResponse response, String msg) {
        Cookie cookie = new Cookie("MSG", msg);
        response.setContentType("text/html");
        response.addCookie(cookie);
        try {
            response.sendError(500, this.kscToAsc(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getValues(ArrayList<HashMap<String, String>> data, String key) {
        StringBuilder result = new StringBuilder();
        if (data == null || data.size() == 0) {
            return "";
        }
        for (int i = 0; i < data.size(); i++) {
            if (i > 0) {
                result.append(",");
            }
            result.append(data.get(i).get(key));
        }
        return result.toString();
    }

    private ArrayList<HashMap<String, String>> makeSampleDataForLocalhost(String cate) {
        ArrayList<HashMap<String, String>> ret = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 3; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            if ("D".equals(cate)) {
                item.put("CATE", "D");
                if (i == 0 || i == 2) {
                    item.put("FNAME", "image" + i + ".png");
                    item.put("FPATH", "/interface/specify/167811");
                }
                item.put("UDATE", "2020-03-08 11:37:0" + i);
            } else if ("R".equals(cate)) {
                item.put("CATE", "R");
                if (i == 0 || i == 2) {
                    item.put("FNAME", "image" + i + ".png");
                    item.put("FPATH", "/interface/rendering/167811");
                }
                item.put("UDATE", "2020-03-08 11:37:0" + i);
            }
            ret.add(item);
        }
        return ret;
    }

    private InputStream getFileStreamForLocalhost() {
        String root = ServiceManagerFactory.getProperty("WORK_DIR");
        String path = root + "WebContent/upload/PS/001.png";
        File f = new File(path);
        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String kscToAsc(String var1) {
        if (var1 == null) {
            var1 = "";
        }

        return var1;
    }

    private String ascToKsc(String var1) {
        try {
            var1 = new String(var1.getBytes("8859_1"), "utf-8");
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        return var1;
    }
}
