package hdel.sd.sbp.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.sbp.service.Sbp0170S;
import hdel.sd.sbp.domain.ZSDS0038;  		// ������Ʈ�� ���ݰ�������

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
	
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs01.ws.ZMMS006;
// import com.helco.xf.cs96.ws.ZCSS_TEST;   // �׽�Ʈ�� ����
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

@Controller
@RequestMapping("sbp0170")
public class Sbp0170C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0170S service;
	
	@RequestMapping(value="find")
	public View test(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0038[] 	list 	= new ZSDS0038[]{};  // ������Ʈ�� ���ݰ������ WFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI�� return�� out dataset
		Dataset 	dsError = null;				// UI�� return�� �����޼��� dataset
		
		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (���� : 310 or 910)
		System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	        +"\n");  
		System.out.print("\n@@@ dsCond.FR_VB	===>"+DatasetUtility.getString(dsCond,"FR_VB")	+"\n");
		System.out.print("\n@@@ dsCond.FR_VG	===>"+DatasetUtility.getString(dsCond,"FR_VG")	+"\n");
		System.out.print("\n@@@ dsCond.FR_YM	===>"+DatasetUtility.getString(dsCond,"FR_YM")	+"\n");
		System.out.print("\n@@@ dsCond.SMAN  	===>"+DatasetUtility.getString(dsCond,"SMAN")	+"\n");
		System.out.print("\n@@@ dsCond.SPART	===>"+DatasetUtility.getString(dsCond,"SPART")	+"\n");
	
		//--------------------------------------------------------------------------------------------
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "FR_VB" )  // �μ��ڵ� _FR
				, DatasetUtility.getString(dsCond, "FR_VG")  // ���ڵ�_FR    
				, DatasetUtility.getString(dsCond, "FR_YM")  // ���ֿ� 
				, DatasetUtility.getString(dsCond, "SMAN")   // �����       
				, DatasetUtility.getString(dsCond, "SPART" )  // ��ǰ��  
				, listMsg
				, list
		}; 
		 
		try { 

			System.out.print("\n@@@ ZWEB_SD_CHN_109 start" + "\n");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sbp.domain.ZWEB_SD_CHN_109", obj, false);	

			// RFC ��±���ü�� out dataset ����
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0038.getDataset();
			ds.setId("ds_list");  
			
			// RFC ȣ������ out dataset�� �ű��  
			CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			 
			System.out.print("\n@@@ ZWEB_SD_CHN_109 SUCCESS!!!" + "\n");
			
		} catch (CommRfcException e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_109 CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (Exception e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_109 Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	 
		
		// �����ͰǼ� INFO
		System.out.print("\n@@@ ds_list.getRowCount ===>" + ds.getRowCount() + "\n");
		
		// RFC ��� dataset�� return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds); 
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
	
	@RequestMapping(value="update")
	public View update(MipParameterVO paramVO, Model model) {
		
		
		// INPUT DATASET GET
		Dataset dsParam = paramVO.getDataset("ds_param");		
		
		// INPUT DATASET GET
		Dataset ds_list = paramVO.getDataset("ds_list");
		
		// �Է� �Ķ���� ó�� 
		ZSDS0038[] list =  (ZSDS0038[])moveDs2Obj2(ds_list, ZSDS0038.class,"");
		
		// RFC INPUT PARAM DECLARE 
		//ZSDS0038[] 	list 	= new ZSDS0038[]{};  // ������Ʈ�� ���ݰ������ WFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset 	ds 		= null;				// UI�� return�� out dataset
		Dataset 	dsError = null;				// UI�� return�� �����޼��� dataset
		
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsParam, "FR_YM")  // ���ֿ� 
				  , list
		}; 
		
		
//		for( int i = 0; i < ds_list.getRowCount(); i++ ) {
//			for( int c = 0; c < ds_list.getColumnCount(); c++) {
//	
//			}
//		}
		
		try { 

			System.out.print("\n@@@ ZWEB_SD_CHN_109_SAVE start" + "\n");
			
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sbp.domain.ZWEB_SD_CHN_109_SAVE", obj, false);	

			// RFC ��±���ü�� out dataset ����
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0038.getDataset();
			ds.setId("ds_result");  
			
			// RFC ȣ������ out dataset�� �ű��  
			//CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
			 
			System.out.print("\n@@@ ZWEB_SD_CHN_109_SAVE SUCCESS!!!" + "\n");
			
		} catch (CommRfcException e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_109_SAVE CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (Exception e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_109_SAVE Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	 
		
		// �����ͰǼ� INFO
		System.out.print("\n@@@ ds_result.getRowCount ===>" + ds.getRowCount() + "\n");
		
		// RFC ��� dataset�� return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds); 
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}	
	
	/**
	 * Dataset�� �÷��� �������� �־��� Class �� �ش� ��ü�� �����Ͽ� ���� Move �Ѵ�. 
	 * 
	 * @param ds �����ͼ� 
	 * @param cls ó���� ��ü 
	 * @param flag �÷��� �÷� : TMODE�� ��Ī�Ǵ� �̸� (���� ��� ���� �ʿ� ����. )
	 * @return 
	 */
	public static Object moveDs2Obj2(Dataset ds, Class cls, String flag) {
		return moveDs2Obj2(ds, cls, flag, null);
	}
	
	
	/**
	 * Dataset�� �÷��� �������� �־��� Class �� �ش� ��ü�� �����Ͽ� ���� Move �Ѵ�. 
	 * �Ѱ��� �����͸� ��ü�� �ű� �� Ds2ObjHelper.endRow()�� ȣ���Ͽ� ó�� ���Ḧ �˸���. 
	 * 
	 * @param ds �����ͼ� 
	 * @param cls ó���� ��ü 
	 * @param flag �÷��� �÷� : TMODE�� ��Ī�Ǵ� �̸� (���� ��� ���� �ʿ� ����. )
	 * @return 
	 */	
	public static Object moveDs2Obj2(Dataset ds, Class cls, String flag, Ds2ObjHelper helper) {
		if ( flag != null && !flag.equals("")) {
			for( int i = ds.getRowCount()-1; i >= 0; i--) {
				// Flag �÷� �ܴ̿� ���� 
				if ( DatasetUtility.getString(ds,i,flag, "").equals("") 
					|| DatasetUtility.getString(ds,i,flag, "").equals("0")) {
					ds.deleteRow(i);
				}
			}
		}
		
		// �ش� Type���� �迭 ���� 
		Object obj = Array.newInstance(cls, ds.getRowCount());
		Method[] mArr = cls.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			// Set �޼ҵ常 �������� 
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
				
				// ó�� ���� �˸�. 
				if ( helper != null ) {
					helper.endMoveRow(ds, i, tmpObj);
				}
			} catch( Exception e){
				e.printStackTrace();
			}
			
			// Array ���� ���� 
			Array.set(obj, i, tmpObj);
		}
		return obj;
	}	
}
