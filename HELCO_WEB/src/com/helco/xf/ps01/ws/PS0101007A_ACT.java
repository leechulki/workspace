package com.helco.xf.ps01.ws;

import java.io.UnsupportedEncodingException;

import antlr.StringUtils;

import com.ctc.wstx.util.StringUtil;
import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.ps01.ws.ZSDS0016;
import com.helco.xf.ps01.ws.ZSDS0017;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.Variant;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class PS0101007A_ACT extends AbstractAction {

	/**
	 * 변환
	 * @param ctx
	 * @throws Exception
	 */
	public void logic(BusinessContext ctx) throws Exception {
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		Dataset dsList = ctx.getInputDataset("ds_listF");
		
		String sPspid = "";
		String sPosid = "";
		String sStdText = "";
		String sResultText = "";
		
		// 1차 :기준문자열 산출.
		for(int i = 0; i<dsList.getRowCount(); i++)
		{	
			sPspid = dsList.getColumn(i, "PSPID").toString();
			sPosid = dsList.getColumn(i, "POSID").toString();
			sStdText = dsList.getColumn(i, "POSID_DONG").toString();
			
			//공백인 경우
			if(sStdText.equals("") || sStdText.equals("X") || sStdText == null)
			{
				sStdText = dsList.getColumn(i, "POSID_OLD").toString();
				
				if(sStdText.equals("") || sStdText.equals("X") || sStdText == null)
				{
					sResultText = org.apache.commons.lang.StringUtils.right(sPosid, 3); // ex.L03, L04 ...
				}
				else
				{
					sResultText = byte10Down(sStdText);
				}
			}
			else
			{
				sResultText = byte10Down(sStdText);
			}
			
			dsList.setColumn(i, "TEMP_POSID", sPspid+sResultText);
			dsList.setColumn(i, "TEMP", sResultText);
		}
		
		
		String sTempCode = "";
		String sPspid2 = "";
		String sPosid2 = "";
		String sTempCode2 = "";
		String sFlg = "";
		
		// 2차 : 최종 문자열 = 프로젝트번호 + 기준문자열 동일코드 있는 것 추출.
		for(int i = 0; i<dsList.getRowCount(); i++)
		{	
			//sPspid = dsList.getColumn(i, "PSPID").toString();
			//sPosid = dsList.getColumn(i, "POSID").toString();
			sTempCode = dsList.getColumn(i, "TEMP_POSID").toString();
			sFlg = dsList.getColumn(i, "FLG").toString();
			
			if(!sFlg.equals("X"))
			{
				for(int j = i+1; j<dsList.getRowCount(); j++)
				{
					//sPspid2 = dsList.getColumn(j, "PSPID").toString();
					//sPosid2 = dsList.getColumn(j, "POSID").toString();
					sTempCode2 = dsList.getColumn(j, "TEMP_POSID").toString();
					
					if(sTempCode.equals(sTempCode2))
					{
						dsList.setColumn(i, "FLG", "X");
						dsList.setColumn(j, "FLG", "X");
					}
				}
			}
			
		}
		
		
		String sText = "";
		String sReplaceText = "";
		// 3차 : 최종 문자열 = 프로젝트번호 + 기준문자열 동일코드 있는 것 추출.
		for(int i = 0; i<dsList.getRowCount(); i++)
		{	
			sFlg = dsList.getColumn(i, "FLG").toString();
			sText = dsList.getColumn(i, "TEMP").toString();
			sPspid = dsList.getColumn(i, "PSPID").toString();
			sReplaceText = sText;
			
			if(sFlg.equals("X"))
			{
				// 1. 기준 문자열에 글자 제거 
				if(sText.indexOf("강의동") < 0 && sText.indexOf("공동") < 0 && sText.indexOf("관리동") < 0 && sText.indexOf("교육동") < 0 && sText.indexOf("기숙사동") < 0 
				   && sText.indexOf("동앞") < 0 && sText.indexOf("동옆") < 0 && sText.indexOf("물류동") < 0 && sText.indexOf("병동") < 0 && sText.indexOf("병원동") < 0
				   && sText.indexOf("복리동") < 0 && sText.indexOf("사무동") < 0 && sText.indexOf("숙소동") < 0 && sText.indexOf("업무동") < 0
				   && sText.indexOf("외부동") < 0 && sText.indexOf("운동") < 0 && sText.indexOf("주차장동") < 0 && sText.indexOf("증축동") < 0)
				  {
					sReplaceText = sText.replaceAll("동", "");
				  }
				
				// 2. 문자열에서 '아파트 ' 또는 ' 아파트'는 없앰.
				if(sReplaceText.contains(" 아파트"))
				{
					sReplaceText = sReplaceText.replaceAll(" 아파트", "");
				}
				
				if(sReplaceText.contains("아파트 "))
				{
					sReplaceText = sReplaceText.replaceAll("아파트 ","");
				}
				

				// 3. 기준 문자열을 10Byte이내로 줄임
				sReplaceText = byte07Down(sReplaceText);
				
				//dsList.setColumn(i, "TEMP_POSID", sPspid+sReplaceText);
				dsList.setColumn(i, "TEMP", sReplaceText);
			}
			
			
			
		}
		
		
		int nSeq = 1;
		String sChgFlg = "X";
		String sPrev = "";
		String sNext = "";
		String seq = "";
		String initSeq = "";
		
		// 3차 : 최종 문자열 = 프로젝트번호 + 기준문자열 동일코드 있는 것 추출.
		for(int i = 0; i<dsList.getRowCount(); i++)
		{	
			sFlg = dsList.getColumn(i, "FLG").toString();
			sPrev = dsList.getColumn(i, "TEMP_POSID").toString();
			sReplaceText = sPrev;
			initSeq = dsList.getColumn(i, "SEQ").toString();

			if(sFlg.equals("X") && (initSeq.equals("") || initSeq == null)) 
			{
				dsList.setColumn(i, "SEQ", 1);
			}
			
			sNext = "";
			for(int j=i+1;j<dsList.getRowCount();j++)
			{
				seq = dsList.getColumn(j, "SEQ").toString();
				if(sFlg.equals("X") &&  (seq.equals("") || seq == null))
				{
					sNext = dsList.getColumn(j, "TEMP_POSID").toString();
						
					if(sPrev.equals(sNext))
					{
						nSeq++;
						dsList.setColumn(j, "SEQ", nSeq);
					}
					
				}
			}
			
			nSeq = 1;
		}
		
		String sTemp = "";
		String sTempR = "";
		String sResult = "";
		// 5. 시퀀스 최종 완성 .. 시퀀스 추가(-1,-2,-3, ...)
		for(int i=0;i<dsList.getRowCount();i++)
		{
			sTemp = dsList.getColumn(i, "TEMP").toString();
			seq = dsList.getColumn(i, "SEQ").toString();
			sFlg = dsList.getColumn(i, "FLG").toString();
			sResult = "";
			if(sFlg.equals("X") && (!seq.equals("") && seq != null))
			{
				sTempR = org.apache.commons.lang.StringUtils.right(sTemp, 1);
				if(sTempR.equals("#"))
				{
					sTemp = sTemp + seq;
				}
				else
				{
					sTemp = sTemp + '-' + seq;
				}
				
				dsList.setColumn(i, "TEMP", sTemp);
				dsList.setColumn(i, "UKEY", sTemp);
			}
			else
			{
				dsList.setColumn(i, "UKEY", sTemp);
			}
			
			sResult = org.apache.commons.lang.StringUtils.right(sTemp, 1);
			// 마지막 자리가 (, #, -, / 이면 제거
			if(sResult.equals(",") || sResult.equals("#") || sResult.equals("-") || sResult.equals("/"))
			{
				sTemp = sTemp.substring(0,sTemp.length()-1);
				dsList.setColumn(i, "TEMP", sTemp);
				dsList.setColumn(i, "UKEY", sTemp);
			}
		}


		dsList.setId("ds_list");
	

		ctx.addOutputDataset(dsList);
	}
	
	// 문자열을 10바이트 이하로 만들어주는 펑션
	public String byte10Down(String str)
	{
		String charset = "euc-kr";
		int len = 0;
		int j = 0;
		try {
			len = str.getBytes(charset).length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int len2 = len;
		String resultStr = str;
		
		if(len >= 10)
		{
			for(int i=str.length()-1;i>=0;i--)
			{
				resultStr = resultStr.substring(0,str.length()-j);
				try {
					len2 = resultStr.getBytes(charset).length;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				j++;
				
				if(len2 <= 10)	i= (-1);
			}
		}
		
		return resultStr;
	}
	
	// 문자열을 13바이트 이하로 만들어 주는 펑션.
	public String byte07Down(String str)
	{
		String charset = "euc-kr";
		int len = 0;
		int j = 0;
		try {
			len = str.getBytes(charset).length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int len2 = len;
		String resultStr = str;
		
		if(len >= 7)
		{
			for(int i=str.length()-1;i>=0;i--)
			{
				resultStr = resultStr.substring(0,str.length()-j);
				try {
					len2 = resultStr.getBytes(charset).length;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				j++;
				
				if(len2 <= 7)	i= (-1);
			}
		}
		
		return resultStr;
	}
}