package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.service.Ses0031S;
import hdel.sd.sso.domain.ZCOBS001;
import hdel.sd.sso.domain.ZCOBT200;
import hdel.sd.sso.domain.ZCOBT202;
import hdel.sd.sso.domain.ZCOBT300;
import hdel.sd.sso.domain.ZCOBT302;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.SapFunction;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0260")
public class Ses0260C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0031S service;

	Ses0031ParamVO param = new Ses0031ParamVO();

	@RequestMapping(value="searchCost")
	public View Ses0031CostView(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset dsCost = paramVO.getDataset("ds_cost");
		Dataset dsChar = paramVO.getDataset("ds_char");
		Dataset dsT202 = paramVO.getDataset("ds_t202");
		Dataset dsT302 = paramVO.getDataset("ds_t302");

		// RFC INPUT PARAM DECLARE
		ZCOBS001[] 	 list001 = (ZCOBS001[])CallSAPHdel.moveDs2Obj2(dsChar , ZCOBS001.class, ""); // RFC input dataset을 CLASS(ZCOBS001) 에 옮기기

		ZCOBT202[] 	 list202 = new ZCOBT202[]{};
		ZCOBT302[] 	 list302 = new ZCOBT302[]{};
		ZCOBT200[] 	 list200 = new ZCOBT200[]{};
		ZCOBT300[] 	 list300 = new ZCOBT300[]{};

		// OUTPUT DATASET DECLARE
		Dataset 	ds_202 = null; // UI로 return할 out dataset
		Dataset 	ds_302 = null; // UI로 return할 out dataset

//		System.out.print("\n@@@ dsCost.getRowCount	===>"+dsCost.getRowCount() + "\n");
//		System.out.print("\n@@@ dsChar.getRowCount	===>"+dsChar.getRowCount() + "\n");

		// INPUT PARAM INFO --------------------------------------------------------------------------------------------
//		System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	+"\n");
//		System.out.print("\n@@@ paramVO.G_USER_ID ===>"+paramVO.getVariable("G_USER_ID") 	+"\n");

/*		for( int irow = 0; irow < dsCost.getRowCount(); irow++ )  {
			for( int icol = 0; icol < dsCost.getColumnCount(); icol++) 	{
				System.out.println("@@@ dsCost["+irow+"Record : " + dsCost.getColumnID(icol)+"] ===>"  + DatasetUtility.getString(dsCost, irow, dsCost.getColumnID(icol)) + "\n");
			}
		}
		for( int irow = 0; irow < dsChar.getRowCount(); irow++ )  {
			for( int icol = 0; icol < dsChar.getColumnCount(); icol++) 	{
				System.out.println("@@@ dsChar["+irow+"Record : " + dsChar.getColumnID(icol)+"] ===>"  + DatasetUtility.getString(dsChar, irow, dsChar.getColumnID(icol)) + "\n");
			}
		}*/
		
		Object obj[] = new Object[]{		// RFC INPUT SET
				  DatasetUtility.getString(dsCost, "AUART" )
				, DatasetUtility.getString(dsCost, "GBN"    )
				, DatasetUtility.getString(dsCost, "GJAHR"  )
				, DatasetUtility.getString(dsCost, "ZONE"  )
				, DatasetUtility.getString(dsCost, "MATNR" )
				, DatasetUtility.getString(dsCost, "POPER"  )
				, DatasetUtility.getString(dsCost, "QTNUM")
				, DatasetUtility.getString(dsCost, "QTSEQ"  )
				, DatasetUtility.getString(dsCost, "QTSER"  )
				, DatasetUtility.getString(dsCost, "REGIO"  )
				, DatasetUtility.getString(dsCost, "VKBUR"  )
				, DatasetUtility.getString(dsCost, "VKGRP"  )
				, list001
				, list200
				, list202
				, list300
				, list302
		};
		 
		try {

			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sso.domain.ZWEB_CO4_FIND_COST", obj, false);	// RFC CALL
			
			ds_202 = CallSAP.isJCO() ? new Dataset() : ZCOBT202.getDataset();	// RFC 출력구조체로 out dataset(ds) 생성
			ds_202.setId("ds_t202");

			ds_302 = CallSAP.isJCO() ? new Dataset() : ZCOBT302.getDataset();
			ds_302.setId("ds_t302");

			if( "4".equals(stub.getOutput("E_TYPE")) ) {
//				System.out.print("\n@@@ 오류!" + "\n");
			}
			
			CallSAP.moveObj2Ds(ds_202, stub.getOutput("T_T202"));		// RFC 호출결과(T_T202,T_T302)를 out dataset(ds)에 옮기기
			CallSAP.moveObj2Ds(ds_302, stub.getOutput("T_T302"));

//			System.out.print("\n@@@ ZWEB_CO4_FIND_COST SUCCESS!!!" + "\n");
			
		} catch (CommRfcException e) { 
//			System.out.print("\n@@@ ZWEB_CO4_FIND_COST CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (Exception e) { 
//			System.out.print("\n@@@ ZWEB_CO4_FIND_COST Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}
//		System.out.print("\n@@@ ds_202:" + ds_202.getRowCount() + ",  ds_302:" + ds_302.getRowCount());

		MipResultVO resultVO = new MipResultVO();		// RFC 출력 dataset을 return
		resultVO.addDataset(ds_202);
		resultVO.addDataset(ds_302);
//		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
}