package com.helco.xf.ps02.ws;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.DatasetHelper;
import com.helco.xf.comm.RegExp;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.ps01.ws.TLINE;
import com.helco.xf.ps01.ws.ZSDS0014;
import com.helco.xf.ps01.ws.ZSDS0015;
import com.tobesoft.platform.data.Dataset;

import tit.biz.AbstractAction;
import tit.biz.BusinessContext;
import tit.util.DatasetUtility;
import tit.util.StringOperator;

public class PS0201001G_ACT extends AbstractAction 
{
	private static final long serialVersionUID = 5661911687794518235L;

	/**
	 * query : 특이사항 가져오기
	 * @param ctx
	 * @throws Exception
	 */
	public void query(BusinessContext ctx) throws Exception 
	{
		Dataset dsCond = ctx.getInputDataset("ds_cond");
		SapFunction stub = null;
		Dataset dsList = null;
		Dataset dsList2 = new Dataset();

		TLINE[] list = new TLINE[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "PSPID","")
				, listMsg
				, list
		};

		// SAP Call
		stub = CallSAP.callSap(ctx.getInputVariable("G_MANDT"),"com.helco.xf.ps01.ws.ZWEB_PS_GET_0009", obj);
		// Dataset make
		dsList = CallSAP.isJCO() ? new Dataset() : TLINE.getDataset();

		// ZQMS028[] -> dsList
		CallSAP.moveObj2Ds(dsList, stub.getOutput("O_TEXT"));

		String textOrg = "";
		String text = "";
		for(int i = 0; i<dsList.getRowCount(); i++)
		{	
			//특이사항에서  금액 부분을 삭제하기위해
			textOrg = RegExp.replaceAll(
					dsList.getColumnAsString(i, "TDLINE"),
					"\\x5c[0-9\\s\\-\\+\\,\\.]+",
					"*");
			// <br>을 line skip으로 변환
			text += RegExp.replaceAll(textOrg,"<(B|b)(R|r)(\\/)?>","\n\n")+"\n\n";
		}
//		dsList.setColumn(0,"TEXT",RegExp.replaceAll(textOrg,"<(B|b)(R|r)(\\/)?>","\n\n"));

		dsList2.setId("ds_head");
		CallSAP.makeDsCol(dsList2, "TEXT", 1);
		dsList2.appendRow();
		dsList2.setColumn(0, "TEXT", text);

		ctx.addOutputDataset(dsList2);
	}

	class MyDatasetHelper implements DatasetHelper 
	{
		public Object getDsValue(String dsColName, Object colValue,	Object orgObj) 
		{
			if ( colValue == null ) 
			{
				return null;
			}

			if ( dsColName.equals("EXPORT") ) 
			{
				return ((String) colValue).equals("0") ? "1" : "0";
			} 
			else if ( dsColName.equals("INSGB")) 
			{
				return ((String) colValue).equals("X") ? "1" : "0";
			}

			return colValue;
		}
	}
}
