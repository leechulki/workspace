package hdel.sd.sso.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sso.domain.TLINE;
import hdel.sd.sso.domain.ZSDS0001;
import hdel.sd.sso.domain.ZSDS0002;
import hdel.sd.sso.domain.ZSDS0003;
import hdel.sd.sso.domain.ZSDS0004;
import hdel.sd.sso.domain.ZSDS0005;
import hdel.sd.sso.domain.ZSDS0006;
import hdel.sd.sso.domain.ZSDS0008;
import hdel.sd.sso.domain.ZSDS0020;
import hdel.sd.sso.domain.ZSDS0044;
import hdel.sd.sso.domain.ZSDS028;
import hdel.sd.sso.domain.ZSDS0056;
import hdel.sd.sso.domain.ZSDS0058;
import hdel.sd.sso.service.Sso0030S;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sso0030")
public class Sso0030C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sso0030S service;
	
	@RequestMapping(value="find")
	public View Sso0030Find(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0044[] 	list 	= new ZSDS0044[]{};  // 프로젝트별 수금관리목록 WFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset ds 		 = null;				// UI로 return할 out dataset
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "FR_SO")  
				, DatasetUtility.getString(dsCond, "FR_VB")    
				, DatasetUtility.getString(dsCond, "FR_VG") 
				, DatasetUtility.getString(dsCond, "FR_YMD")  
				, DatasetUtility.getString(dsCond, "SMAN")
				, DatasetUtility.getString(dsCond, "TO_SO")    
				, DatasetUtility.getString(dsCond, "TO_VB") 
				, DatasetUtility.getString(dsCond, "TO_VG")  
				, DatasetUtility.getString(dsCond, "TO_YMD")
				, listMsg
				, list
		}; 
		 
		try {

			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sso.domain.ZWEB_SD_CHN_103", obj, false);	

			// RFC 출력구조체로 out dataset 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0044.getDataset();
			ds.setId("ds_list");  

			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			if ( stub.getOutput("E_TYPE") == null ) {	// no_data
			} else if ( "4".equals(stub.getOutput("E_TYPE").toString()) ) {	// 실패
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
						, listMsg[0].getIDX().toString()
						, listMsg[0].getERRMSG().toString()
						, "Y"
						, "Y");
			} else {
				// RFC 호출결과를 out dataset에 옮기기
				CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
			}
			
		} catch (CommRfcException e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_103 CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (NullPointerException ne){
			System.out.print("\n@@@ ZWEB_SD_CHN_103 Exception ERROR!!!" + "\n");
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
					, "NO"
					, "NO_DATA_FOUND"
					, "Y"
					, "Y");
			ne.printStackTrace();
		} catch (Exception e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_103 Exception ERROR!!!" + "\n");
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
					, "NO"
					, "NO_DATA_FOUND"
					, "Y"
					, "Y");
			e.printStackTrace();
		}
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds);
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	// RFC호출하지 않고 바로 기안 URL 호출방식으로 변경하여 제거 2012.10.17
	/*
	@RequestMapping(value="process")
	public View Sso0030Process(MipParameterVO paramVO, Model model) {

		// INPUT DATASET GET
		Dataset ds_process = paramVO.getDataset("ds_process");

		// OUTPUT DATASET DECLARE
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		// 입력 파라메터 처리  Dataset의 정보를 T_ITAB에 처리하여 전송
		ZSDS0044[] list =  (ZSDS0044[])moveDs2Obj2(ds_process, ZSDS0044.class,"");
		
		// RFC INPUT PARAM DECLARE 
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				    paramVO.getVariable("cmd")  // CMD 
				  , listMsg
				  , list
		}; 

		for( int i = 0; i < ds_process.getRowCount(); i++ ) {
			for( int c = 0; c < ds_process.getColumnCount(); c++) {
				System.out.println(ds_process.getColumnID(c) + "======>"  +  DatasetUtility.getString(ds_process, i, ds_process.getColumnID(c)));
			}
		}

		try { 
	
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sso.domain.ZWEB_SD_CHN_103_CMD", obj, false);	

			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			if ( "4".equals(stub.getOutput("E_TYPE").toString()) ) {	// 실패
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
						, listMsg[0].getIDX().toString()
						, listMsg[0].getERRMSG().toString()
						, "Y"
						, "Y");
			}
		} catch (CommRfcException e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_103_CMD CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (Exception e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_103_CMD Exception ERROR!!!" + "\n");
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
					, "CE00001"
					, "CE00001"
					, "Y"
					, "Y");
			e.printStackTrace();
		}

		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds_error); 
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	*/

	@RequestMapping(value="print")
	public View print(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");

		// OUTPUT DATASET DECLARE
		Dataset dsBILLCON = paramVO.getDataset("dsBILLCON");
		Dataset dsBOTTINFO = paramVO.getDataset("dsBOTTINFO");
		Dataset dsCONCON = paramVO.getDataset("dsCONCON");
		Dataset dsCOSTINFO = paramVO.getDataset("dsCOSTINFO");
		Dataset dsCOSTINFO2 = paramVO.getDataset("dsCOSTINFO2");
		Dataset dsHEAD = paramVO.getDataset("dsHEAD");
		Dataset dsO_TAB = paramVO.getDataset("dsO_TAB");
		Dataset dsSOINFO = paramVO.getDataset("dsSOINFO");
		Dataset dsSOINFO1 = paramVO.getDataset("dsSOINFO1");
		Dataset dsSUBTO = paramVO.getDataset("dsSUBTO");
		Dataset dsSUBTO1 = paramVO.getDataset("dsSUBTO1");
		Dataset dsTEXT = paramVO.getDataset("dsTEXT");
		Dataset dsTOTAL = paramVO.getDataset("dsTOTAL");
		Dataset dsT_ADD_DATA = paramVO.getDataset("dsT_ADD_DATA");
		Dataset dsSPDRFTINFO = paramVO.getDataset("dsSPDRFTINFO");
		Dataset[] dsar = null;

		// RFC INPUT PARAM DECLARE 
		ZSDS0003[] listZSDS0003 = new ZSDS0003[]{};
		ZSDS0006[] listZSDS0006 = new ZSDS0006[]{};
		ZSDS0002[] listZSDS0002 = new ZSDS0002[]{};
		ZSDS0005[] listZSDS0005 = new ZSDS0005[]{};
		ZSDS0008[] listZSDS0008 = new ZSDS0008[]{};
		ZSDS0001[] listZSDS0001 = new ZSDS0001[]{};
		ZSDS028[] listZSDS028 = new ZSDS028[]{};
		ZSDS0004[] listZSDS0004_1 = new ZSDS0004[]{};
		ZSDS0004[] listZSDS0004_2 = new ZSDS0004[]{};
		ZSDS0020[] listZSDS0020_1 = new ZSDS0020[]{};
		ZSDS0020[] listZSDS0020_2 = new ZSDS0020[]{};
		TLINE[] listTLINE = new TLINE[]{};
		ZSDS0020[] listZSDS0020_3 = new ZSDS0020[]{};
		ZSDS0056[] listZSDS0056 = new ZSDS0056[]{};
		ZSDS0058[] listZSDS0058 = new ZSDS0058[]{};
		
		// RFC INPUT SET
		Object obj[] = new Object[]{
				  listZSDS0003
				, listZSDS0006
				, listZSDS0002
				, listZSDS0005
				, listZSDS0008
				, listZSDS0001
				, listZSDS028
				, listZSDS0004_1
				, listZSDS0004_2
				, listZSDS0058
				, listZSDS0020_1
				, listZSDS0020_2
				, listTLINE
				, listZSDS0020_3
				, listZSDS0056
				, DatasetUtility.getString(dsCond, "VBELN")
		};
		
		try {
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sso.domain.ZGW_SD_GET_SD_DATA1", obj, false);	

			// RFC 출력구조체로 out dataset 생성
			dsHEAD = CallSAP.isJCO() ? new Dataset() : ZSDS0001.getDataset();
			dsCONCON = CallSAP.isJCO() ? new Dataset() : ZSDS0002.getDataset();
			dsBILLCON = CallSAP.isJCO() ? new Dataset() : ZSDS0003.getDataset();
			dsar = CallSAP.isJCO() ? null : ZSDS0004.getDataset();
			dsSOINFO = dsar[0];		// ZSDS0004
			dsSOINFO1 = dsar[1];	// ZSDS0004
			dsCOSTINFO = CallSAP.isJCO() ? new Dataset() : ZSDS0005.getDataset();
			dsTEXT = CallSAP.isJCO() ? new Dataset() : TLINE.getDataset();
			dsBOTTINFO = CallSAP.isJCO() ? new Dataset() : ZSDS0006.getDataset();
			dsCOSTINFO2 = CallSAP.isJCO() ? new Dataset() : ZSDS0008.getDataset();
			dsO_TAB = CallSAP.isJCO() ? new Dataset() : ZSDS028.getDataset();
			dsar = CallSAP.isJCO() ? null : ZSDS0020.getDataset();
			dsSUBTO = dsar[0];		// ZSDS0020
			dsSUBTO1 = dsar[1];		// ZSDS0020
			dsTOTAL = dsar[2];		// ZSDS0020
			dsT_ADD_DATA = CallSAP.isJCO() ? new Dataset() : ZSDS0056.getDataset();
			dsSPDRFTINFO = CallSAP.isJCO() ? new Dataset() : ZSDS0058.getDataset();			
			
			dsHEAD.setId("dsHEAD");
			dsCONCON.setId("dsCONCON");
			dsBILLCON.setId("dsBILLCON");
			dsSOINFO.setId("dsSOINFO");
			dsSOINFO1.setId("dsSOINFO1");
			dsCOSTINFO.setId("dsCOSTINFO");
			dsTEXT.setId("dsTEXT");
			dsBOTTINFO.setId("dsBOTTINFO");
			dsCOSTINFO2.setId("dsCOSTINFO2");
			dsO_TAB.setId("dsO_TAB");
			dsSUBTO.setId("dsSUBTO");
			dsSUBTO1.setId("dsSUBTO1");
			dsTOTAL.setId("dsTOTAL");
			dsT_ADD_DATA.setId("dsT_ADD_DATA");
			dsSPDRFTINFO.setId("dsSPDRFTINFO");

			if ( !"4".equals(stub.getOutput("E_TYPE").toString()) ) {	//
				// RFC 호출결과를 out dataset에 옮기기
				CallSAP.moveObj2Ds(dsHEAD, stub.getOutput("HEAD"));
				CallSAP.moveObj2Ds(dsCONCON, stub.getOutput("CONCON"));
				CallSAP.moveObj2Ds(dsBILLCON, stub.getOutput("BILLCON"));
				CallSAP.moveObj2Ds(dsSOINFO, stub.getOutput("SOINFO"));
				CallSAP.moveObj2Ds(dsSOINFO1, stub.getOutput("SOINFO1"));
				CallSAP.moveObj2Ds(dsCOSTINFO, stub.getOutput("COSTINFO"));
				CallSAP.moveObj2Ds(dsTEXT, stub.getOutput("TEXT"));
				CallSAP.moveObj2Ds(dsBOTTINFO, stub.getOutput("BOTTINFO"));
				CallSAP.moveObj2Ds(dsCOSTINFO2, stub.getOutput("COSTINFO2"));
				CallSAP.moveObj2Ds(dsO_TAB, stub.getOutput("O_TAB"));
				CallSAP.moveObj2Ds(dsSUBTO, stub.getOutput("SUBTO"));
				CallSAP.moveObj2Ds(dsSUBTO1, stub.getOutput("SUBTO1"));
				CallSAP.moveObj2Ds(dsTOTAL, stub.getOutput("TOTAL"));
				CallSAP.moveObj2Ds(dsT_ADD_DATA, stub.getOutput("T_ADD_DATA"));
				CallSAP.moveObj2Ds(dsSPDRFTINFO, stub.getOutput("SPDRFTINFO"));
			}

		} catch (CommRfcException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsBILLCON);
		resultVO.addDataset(dsBOTTINFO);
		resultVO.addDataset(dsCONCON);
		resultVO.addDataset(dsCOSTINFO);
		resultVO.addDataset(dsCOSTINFO2);
		resultVO.addDataset(dsHEAD);
		resultVO.addDataset(dsO_TAB);
		resultVO.addDataset(dsSOINFO);
		resultVO.addDataset(dsSOINFO1);
		resultVO.addDataset(dsSUBTO);
		resultVO.addDataset(dsSUBTO1);
		resultVO.addDataset(dsTEXT);
		resultVO.addDataset(dsTOTAL);
		resultVO.addDataset(dsT_ADD_DATA);
		resultVO.addDataset(dsSPDRFTINFO);
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}

	/*
	@RequestMapping(value="update")
	public View update(MipParameterVO paramVO, Model model) {
		
		
		// INPUT DATASET GET
		Dataset dsParam = paramVO.getDataset("ds_param");		
		
		// INPUT DATASET GET
		Dataset ds_list = paramVO.getDataset("ds_list");
		
		// 입력 파라메터 처리 
		ZSDS0044[] list =  (ZSDS0044[])moveDs2Obj2(ds_list, ZSDS0044.class,"");
		
		// RFC INPUT PARAM DECLARE 
		//ZSDS0044[] 	list 	= new ZSDS0044[]{};  // 프로젝트별 수금관리목록 WFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI로 return할 out dataset
		Dataset 	dsError = null;				// UI로 return할 오류메세지 dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		
		
		System.out.println("start");  

	
		//--------------------------------------------------------------------------------------------
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsParam, "FR_YM")  // 수주월 
				  , list
		}; 
		
		
		for( int i = 0; i < ds_list.getRowCount(); i++ ) {
			for( int c = 0; c < ds_list.getColumnCount(); c++) {
	
						System.out.println(ds_list.getColumnID(c) + "======>"  +  DatasetUtility.getString(ds_list, i, ds_list.getColumnID(c)));
	
			}
		}
		
		System.out.println("end");  
		 
		try { 

			System.out.print("\n@@@ ZWEB_SD_CHN_103_SAVE start" + "\n");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sso.domain.ZWEB_SD_CHN_103_SAVE", obj, false);	

			// RFC 출력구조체로 out dataset 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0044.getDataset();
			ds.setId("ds_result");  
			
			// RFC 호출결과를 out dataset에 옮기기  
			//CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			 
			System.out.print("\n@@@ ZWEB_SD_CHN_103_SAVE SUCCESS!!!" + "\n");
			
		} catch (CommRfcException e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_103_SAVE CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (Exception e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_103_SAVE Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	 
		
		// 데이터건수 INFO
		System.out.print("\n@@@ ds_result.getRowCount ===>" + ds.getRowCount() + "\n");
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds); 
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	*/
	
	/**
	 * Dataset의 컬럼을 기준으로 주어진 Class 에 해당 객체를 생성하여 값을 Move 한다. 
	 * 
	 * @param ds 데이터셋 
	 * @param cls 처리할 객체 
	 * @param flag 플래그 컬럼 : TMODE와 대칭되는 이름 (같을 경우 정의 필요 없음. )
	 * @return 
	 */
	public static Object moveDs2Obj2(Dataset ds, Class cls, String flag) {
		return moveDs2Obj2(ds, cls, flag, null);
	}
	
	
	/**
	 * Dataset의 컬럼을 기준으로 주어진 Class 에 해당 객체를 생성하여 값을 Move 한다. 
	 * 한건의 데이터를 객체로 옮긴 후 Ds2ObjHelper.endRow()를 호출하여 처리 종료를 알린다. 
	 * 
	 * @param ds 데이터셋 
	 * @param cls 처리할 객체 
	 * @param flag 플래그 컬럼 : TMODE와 대칭되는 이름 (같을 경우 정의 필요 없음. )
	 * @return 
	 */	
	public static Object moveDs2Obj2(Dataset ds, Class cls, String flag, Ds2ObjHelper helper) {
		if ( flag != null && !flag.equals("")) {
			for( int i = ds.getRowCount()-1; i >= 0; i--) {
				// Flag 컬럼 이외는 삭제 
				if ( DatasetUtility.getString(ds,i,flag, "").equals("") 
					|| DatasetUtility.getString(ds,i,flag, "").equals("0")) {
					ds.deleteRow(i);
				}
			}
		}
		
		// 해당 Type으로 배열 생성 
		Object obj = Array.newInstance(cls, ds.getRowCount());
		Method[] mArr = cls.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			// Set 메소드만 가져오기 
			if ( mArr[i].getName().startsWith("set")) {
				mData.put(mArr[i].getName().substring(3), mArr[i]);
			}
		}
		Object tmpObj = null;
		Method m = null;
		Class setClass = null;
		BigDecimal b = null;
		for( int i = 0; i < ds.getRowCount(); i++ ) {
			try {
				tmpObj = cls.newInstance();
				
				for( int c = 0; c < ds.getColumnCount(); c++) {
					m = (Method) mData.get(ds.getColumnID(c));
					if ( m != null ) {
						setClass = m.getParameterTypes()[0];
						if ( setClass.getName().equals("int")){
							m.invoke(tmpObj, DatasetUtility.getInt(ds,i, ds.getColumnID(c)));
						} else if ( setClass.getName().equals("java.math.BigDecimal")){
							b = new BigDecimal(DatasetUtility.getDbl(ds, i, ds.getColumnID(c), 0));
							b = b.setScale(2, RoundingMode.DOWN);
							m.invoke(tmpObj, b);
						} else {	
							m.invoke(tmpObj, DatasetUtility.getString(ds, i, ds.getColumnID(c)));
						}
					}
				}
				
				if ( !flag.equals("TMODE")) {
					m = (Method) mData.get("TMODE");
					if ( m != null ) {
						setClass = m.getParameterTypes()[0];
						m.invoke(tmpObj, ds.getColumnAsString(i, flag));
					}
				}
				
				// 처리 종료 알림. 
				if ( helper != null ) {
					helper.endMoveRow(ds, i, tmpObj);
				}
			} catch( Exception e){
				e.printStackTrace();
			}
			
			// Array 정보 설정 
			Array.set(obj, i, tmpObj);
		}
		return obj;
	}	
}
