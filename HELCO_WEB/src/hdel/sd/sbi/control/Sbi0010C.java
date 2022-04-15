package hdel.sd.sbi.control;


import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.Ds2ObjHelper;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.ksign.KsignSPinCrypto;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sbi.domain.Sbi0010;
import hdel.sd.sbi.domain.Sbi0010ParamVO;
import hdel.sd.sbi.domain.ZSDS0045;
import hdel.sd.sbi.service.Sbi0010S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("Sbi0010")
public class Sbi0010C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbi0010S service;

	@RequestMapping(value="find")
	public View Sbi0010Test(MipParameterVO paramVO, Model model) {
		KsignSPinCrypto crypt = new KsignSPinCrypto(paramVO.getVariable("G_SYSID"));

		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// RFC INPUT PARAM DECLARE 
		ZSDS0045[] 	list 	= new ZSDS0045[]{};  // 프로젝트별 수금관리목록 WFC output list
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset ds 		 = null;				// UI로 return할 out dataset
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		for(int i=0; i < list.length; i++) {
			list[i].setSTCD1(crypt.encJumin(list[i].getSTCD1()));
		}
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				  DatasetUtility.getString(dsCond, "SMSG")
				, DatasetUtility.getString(dsCond, "CMD")  	  	  // cmd 
				, DatasetUtility.getString(dsCond, "KTOKD" )  	  // 고객유형 
				, DatasetUtility.getString(dsCond, "KUNNR")  	  // 고객번호
				, DatasetUtility.getString(dsCond, "NAME1")  	  // 고객명
				, crypt.encJumin(DatasetUtility.getString(dsCond, "STCD1"))  	  // 주민번호
				, DatasetUtility.getString(dsCond, "STCD2")  	  // 사업자번호
				, listMsg
				, list
		};
		
		try { 
			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sbi.domain.ZWEB_SD_CHN_XD01", obj, false);	

			// RFC 출력구조체로 out dataset 생성
			ds = CallSAP.isJCO() ? new Dataset() : ZSDS0045.getDataset();
			ds.setId("ds_list");
			
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			if ( "4".equals(stub.getOutput("E_TYPE").toString()) ) {	// 실패
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
						, listMsg[0].getIDX().toString()
						, listMsg[0].getERRMSG().toString()
						, "Y"
						, "Y");
			} else {
				// RFC 호출결과를 out dataset에 옮기기
				CallSAP.moveObj2Ds(ds, stub.getOutput("T_ITAB"));
				for (int i = 0; i < ds.getRowCount(); i++) {
					ds.setColumn(i, "STCD1", crypt.decJumin(ds.getColumnAsString(i, "STCD1")));
				}			
			}

		} catch (CommRfcException e) { 
			e.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace();
		}

		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds);
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
	
	@RequestMapping(value="save")
	public View Sbi0010Save(MipParameterVO paramVO, Model model) {
		KsignSPinCrypto crypt = new KsignSPinCrypto(paramVO.getVariable("G_SYSID"));
		// INPUT DATASET GET
		Dataset ds_list = paramVO.getDataset("ds_list");		//저장 정보
		String  sCmd    = paramVO.getVariable("cmd");			// 처리정보

		// 입력 파라메터 처리 
		ZSDS0045[] list =  (ZSDS0045[])moveDs2Obj2(ds_list, ZSDS0045.class,"");
		
		// RFC INPUT PARAM DECLARE 
		ZQMSEMSG[] 	listMsg = new ZQMSEMSG[]{};
		
		// OUTPUT DATASET DECLARE
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		//--------------------------------------------------------------------------------------------
		// INPUT PARAM INFO     		
		// sap client (개발 : 310 or 910)
		System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	        +"\n"); 
		System.out.print("\n@@@ dsCond.SMSG  	===>"+DatasetUtility.getString(ds_list,"SMSG")	+"\n");
		System.out.print("\n@@@ dsCond.KTOKD	===>"+DatasetUtility.getString(ds_list,"KTOKD")	+"\n");
		System.out.print("\n@@@ dsCond.CMD		===>"+sCmd	+"\n");
		System.out.print("\n@@@ dsCond.KUNNR	===>"+DatasetUtility.getString(ds_list,"KUNNR")	+"\n");
		System.out.print("\n@@@ dsCond.NAME1	===>"+DatasetUtility.getString(ds_list,"NAME1")	+"\n");
		System.out.print("\n@@@ dsCond.STCD1	===>"+DatasetUtility.getString(ds_list,"STCD1")	+"\n");
		System.out.print("\n@@@ dsCond.STCD2	===>"+DatasetUtility.getString(ds_list,"STCD2")	+"\n");
		//--------------------------------------------------------------------------------------------
		
		// WFC INPUT SET
		Object obj[] = new Object[]{
				    DatasetUtility.getString(ds_list, "SMSG")
				  , sCmd									  		  // cmd  
				  , DatasetUtility.getString(ds_list, "KTOKD")  	  // 수주월
				  , DatasetUtility.getString(ds_list, "KUNNR")  	  // 고객번호
				  , DatasetUtility.getString(ds_list, "NAME1")  	  // 고객명
				  , crypt.encJumin(DatasetUtility.getString(ds_list, "STCD1"))  	  // 주민번호
				  , DatasetUtility.getString(ds_list, "STCD2")  	  // 사업자번호
				  , listMsg
				  , list
		}; 

		try { 

			// RFC CALL
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT"), "hdel.sd.sbi.domain.ZWEB_SD_CHN_XD01", obj, false);

			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			if ( "4".equals(stub.getOutput("E_TYPE").toString()) ) {	// 실패
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error
						, listMsg[0].getIDX().toString()
						, listMsg[0].getERRMSG().toString()
						, "Y"
						, "Y");
			}
			
		} catch (CommRfcException e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_XD01 CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (Exception e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_XD01 Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	 

		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}	
	
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
	
	@RequestMapping(value="findregio")
	public View Sbi0010findregio(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset ds_list = paramVO.getDataset("ds_list");
		//Dataset  ds_regio  = new Dataset();
		List<Sbi0010>     list     = null;                    // 결과리스트SET
        Sbi0010ParamVO    param    = new Sbi0010ParamVO();    // 조건
        String mandt = paramVO.getVariable("G_MANDT");
        String land  = DatasetUtility.getString(ds_list, "LAND1");
        
        param.setMANDT(mandt);                                // 조건_SAP CLIENT
        param.setLAND(land);                                // 견적번호
     // DAO 생성
     	service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
        
        
		// OUTPUT DATASET DECLARE
		//List<Sbi0010>  ds_regio 		 = null;				// UI로 return할 out dataset
     	Dataset ds_regio = new Dataset();
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		try { 
			list = service.selectRegio(param);	
			ds_regio.setId("ds_regio");
			ds_regio.addColumn("CODE", ColumnInfo.COLTYPE_STRING, 256);
			ds_regio.addColumn("CODE_NAME", ColumnInfo.COLTYPE_STRING, 256);
			
			if ( list.size()> 0 ){
				for( int j = 0 ; j < list.size() ; j++){
				      ds_regio.appendRow();
		              ds_regio.setColumn(j, "CODE"        , list.get(j).getCode());         // 코드
		              ds_regio.setColumn(j, "CODE_NAME"   , list.get(j).getCode_name());    // 코드명
		            }				
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}

		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds_regio);
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
}
