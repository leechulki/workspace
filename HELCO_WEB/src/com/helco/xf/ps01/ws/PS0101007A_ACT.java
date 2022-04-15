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
	 * ��ȯ
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
		
		// 1�� :���ع��ڿ� ����.
		for(int i = 0; i<dsList.getRowCount(); i++)
		{	
			sPspid = dsList.getColumn(i, "PSPID").toString();
			sPosid = dsList.getColumn(i, "POSID").toString();
			sStdText = dsList.getColumn(i, "POSID_DONG").toString();
			
			//������ ���
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
		
		// 2�� : ���� ���ڿ� = ������Ʈ��ȣ + ���ع��ڿ� �����ڵ� �ִ� �� ����.
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
		// 3�� : ���� ���ڿ� = ������Ʈ��ȣ + ���ع��ڿ� �����ڵ� �ִ� �� ����.
		for(int i = 0; i<dsList.getRowCount(); i++)
		{	
			sFlg = dsList.getColumn(i, "FLG").toString();
			sText = dsList.getColumn(i, "TEMP").toString();
			sPspid = dsList.getColumn(i, "PSPID").toString();
			sReplaceText = sText;
			
			if(sFlg.equals("X"))
			{
				// 1. ���� ���ڿ��� ���� ���� 
				if(sText.indexOf("���ǵ�") < 0 && sText.indexOf("����") < 0 && sText.indexOf("������") < 0 && sText.indexOf("������") < 0 && sText.indexOf("����絿") < 0 
				   && sText.indexOf("����") < 0 && sText.indexOf("����") < 0 && sText.indexOf("������") < 0 && sText.indexOf("����") < 0 && sText.indexOf("������") < 0
				   && sText.indexOf("������") < 0 && sText.indexOf("�繫��") < 0 && sText.indexOf("���ҵ�") < 0 && sText.indexOf("������") < 0
				   && sText.indexOf("�ܺε�") < 0 && sText.indexOf("�") < 0 && sText.indexOf("�����嵿") < 0 && sText.indexOf("���ൿ") < 0)
				  {
					sReplaceText = sText.replaceAll("��", "");
				  }
				
				// 2. ���ڿ����� '����Ʈ ' �Ǵ� ' ����Ʈ'�� ����.
				if(sReplaceText.contains(" ����Ʈ"))
				{
					sReplaceText = sReplaceText.replaceAll(" ����Ʈ", "");
				}
				
				if(sReplaceText.contains("����Ʈ "))
				{
					sReplaceText = sReplaceText.replaceAll("����Ʈ ","");
				}
				

				// 3. ���� ���ڿ��� 10Byte�̳��� ����
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
		
		// 3�� : ���� ���ڿ� = ������Ʈ��ȣ + ���ع��ڿ� �����ڵ� �ִ� �� ����.
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
		// 5. ������ ���� �ϼ� .. ������ �߰�(-1,-2,-3, ...)
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
			// ������ �ڸ��� (, #, -, / �̸� ����
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
	
	// ���ڿ��� 10����Ʈ ���Ϸ� ������ִ� ���
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
	
	// ���ڿ��� 13����Ʈ ���Ϸ� ����� �ִ� ���.
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