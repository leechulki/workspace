
package com.helco.xf.wb01;

import com.tobesoft.platform.data.*;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tit.biz.BusinessContext;
import tit.biz.session.UserInfo;
import tit.service.core.logger.Logger;

public class WebBusinessContext
    implements BusinessContext
{

    @Override
	public Object getInputRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getOuputResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	private WebBusinessContext()
    {
        mCtx = null;
        mUserInfo = null;
        mOutputPage = null;
    }

    public WebBusinessContext(HttpServletRequest request, HttpServletResponse response, ServletContext ctx)
    {
        mCtx = null;
        mUserInfo = null;
        mOutputPage = null;
        mHttpRequest = request;
        mHttpResponse = response;
        mCtx = ctx;
    }

    public void addOutputDataset(Dataset ds)
    {
        mHttpRequest.setAttribute(ds.getId(), ds);
    }

    public void addOutputDataset(String name, Dataset ds)
    {
        mHttpRequest.setAttribute(name, ds);
    }

    public void addOutputVariable(String name, String str)
    {
        mHttpRequest.setAttribute(name, str);
    }

    public void addOutputVariable(String name, Object obj)
    {
        mHttpRequest.setAttribute(name, obj);
    }

    public String getActionName()
    {
        return mHttpRequest.getParameter("actionName");
    }

    public String getCharset()
    {
        return mCharset;
    }

    public String getCommand()
    {
        return mHttpRequest.getParameter("cmd");
    }

    public Dataset getInputDataset()
    {
        return null;
    }

    public Dataset getInputDataset(String dsName)
    {
        return null;
    }

    public DatasetList getInputDatasetList()
    {
        return null;
    }

    public String getInputVariable(String name)
    {
        return mHttpRequest.getParameter(name);
    }

    public VariableList getInputVariableList()
    {
        VariableList list = new VariableList();
        Map map = mHttpRequest.getParameterMap();
        Iterator ir = map.keySet().iterator();
        String key = null;
        String values[] = (String[])null;
        while(ir.hasNext()) 
        {
            key = (String)ir.next();
            values = mHttpRequest.getParameterValues(key);
            if(values.length > 1)
                list.addVariable(key, values);
            else
                list.addVariable(key, values[0]);
        }
        return list;
    }

    public Dataset getOutputDataset(String name)
    {
        return null;
    }

    public Dataset getOutputDataset()
    {
        return null;
    }

    public DatasetList getOutputDatasetList()
    {
        return null;
    }

    public OutputStream getOutputStream()
    {
        return null;
    }

    public VariableList getOutputVariableList()
    {
        return null;
    }

    public UserInfo getUserInfo()
    {
        return mUserInfo;
    }

    public void makeErrorResult(String msgCode, String msg)
    {
        mHttpRequest.setAttribute("MsgCode", msgCode);
        mHttpRequest.setAttribute("Msg", msg);
    }

    public void makeErrorResult(int msgCode, String msg)
    {
        mHttpRequest.setAttribute("MsgCode", (new StringBuffer(String.valueOf(msgCode))).toString());
        mHttpRequest.setAttribute("Msg", msg);
    }

    public void makeErrorResult(String msg)
    {
        mHttpRequest.setAttribute("Msg", msg);
    }

    public void makeErrorResult(Throwable e)
    {
        makeErrorResult(((String) (null)), e);
    }

    public void makeErrorResult(String msg, Throwable e)
    {
        e.printStackTrace();
        mHttpRequest.setAttribute("MsgCode", "-999999999");
        mHttpRequest.setAttribute("Msg", msg != null ? ((Object) (msg)) : ((Object) (e.getMessage())));
        if(e != null)
        {
            if(e.getCause() != null)
                e = e.getCause();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            mHttpRequest.setAttribute("ErrorTrace", sw.toString());
        }
    }

    public void makeSuccessResult(String msgCode, String msg)
    {
        mHttpRequest.setAttribute("MsgCode", msgCode);
        mHttpRequest.setAttribute("Msg", msg);
    }

    public void makeSuccessResult(String msg)
    {
        mHttpRequest.setAttribute("MsgCode", "0");
        mHttpRequest.setAttribute("Msg", msg);
    }

    public void sendData()
        throws Exception
    {
        if(getOutput() == null || getOutput().equals(""))
            return;
        mHttpResponse.setContentType("text/html;charset=" + mCharset != null ? mCharset : mHttpRequest.getCharacterEncoding());
        String page = getOutput();
        if(page.charAt(0) != '/')
            page = "/" + page;
        RequestDispatcher rd = mCtx.getRequestDispatcher(page);
        System.out.println("page >>>>>>>>>>> "+page);
        rd.forward(mHttpRequest, mHttpResponse);
    }

    /*public void sendData() throws Exception {
    	  if ( getOutput() == null || getOutput().equals("")) {
    	   return;
    	  }
    	  
    	  String page = getOutput();
    	  if ( page.charAt(0) != '/') {
    	   page = "/" + page;
    	  }
    	  System.out.println("page >>>>>>>>>>> "+page);
    	  mHttpResponse.sendRedirect( page ); 
    }*/
    
    public void setCharset(String newCharset)
    {
        mCharset = newCharset;
    }

    public String getOutput()
    {
        return mOutputPage;
    }

    public void setOutput(String output)
    {
        mOutputPage = output;
    }

    public void setUserInfo(UserInfo info)
    {
        mUserInfo = info;
    }

    public void debug(Logger logger1)
    {
    }
    
    public HttpServletRequest getReq(){
    	return mHttpRequest;
    }

    public HttpServletResponse getRes(){
    	return mHttpResponse;
    }

    protected HttpServletRequest mHttpRequest;
    protected HttpServletResponse mHttpResponse;
    protected ServletContext mCtx;
    private String mCharset;
    private UserInfo mUserInfo;
    private String mOutputPage;
}

