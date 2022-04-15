package hdel.srm.sample.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.srm.sample.domain.Sample;
import hdel.srm.sample.domain.SampleParamVO;
import hdel.srm.sample.service.SampleService;

import java.io.IOException;
	
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.helco.xf.comm.ZQMSEMSG;
import com.helco.xf.cs12.batch.BatchConstants;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.vo.TBEBWZF1Vo;
import com.helco.xf.cs12.evladm.vo.TBEBXRF1Vo;
import com.helco.xf.cs96.ws.ZCSS_TEST;
import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.VariableList;

/**
 * 샘플 Control
 * <br>
 * 샘플 예제로 구현된 Controller 클래스
 * 
 * @since 1.0
 * <p>
 * History<br>
 * 1.0  2007.01.01  jissfa    initial version
 * 
 * @version 1.0
 * 
 * @author <a href="mailto:jissfa@naver.com">jissfa</a>
 */
@Controller
@RequestMapping("sample")
public class SampleControl {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private SampleService service;
	
	/**
	 * 샘플 조회
	 * @param paramVO
	 * @param model
	 * @return
	 */
	public View message(MipParameterVO paramVO, Model model) {
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try {
			service.message();
		} catch (BizException e) {
			// 서비스에서 로직처리되어 리턴된 메시지 처리
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		} catch (Exception e) {
			// 서비스에서 발생된 예외 메시지 처리
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO();
		
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;
	}
	
	@RequestMapping(value="find")
	public View test(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// 입력 파라메터 처리 
		ZCSS_TEST[] list = new ZCSS_TEST[]{};
		ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
		Dataset ds = null;
		Dataset dsError = null;

		Object obj[] = new Object[]{
				DatasetUtility.getString(dsCond, "HST")
				, listMsg
				, list
		};
/*
		try {
			SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_MANDT"), "com.helco.xf.cs96.ws.ZWEB_CS_TEST", obj);
			
			// 출력 Dataset 생성 
			ds = CallSAP.isJCO() ? new Dataset() : ZCSS_TEST.getDataset();
			ds.setId("ds_list");
			
			// 목록 정보 옮기기 
			CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
			dsError = CallSAP.makeErrorInfo(listMsg);
		} catch (CommRfcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
*/			
//		service.createDao(sqlSession.getSqlSession(SrmSqlSession.PRD));
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		SampleParamVO param = new SampleParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setLang(paramVO.getVariable("G_LANG"));
		param.setCode1("MW_BMP");
		
		java.util.List listD = service.find(param);
		System.out.println(listD);

		ds = ZCSS_TEST.getDataset();
		ds.setId("ds_list");
//		ds.addColumn("PJT",(short)1,6);
//		ds.addColumn("HNO",(short)1,3);
//		ds.insertRow(0);
//		ds.setColumn(0, "MANDT", 1);
//		ds.setColumn(0, "LANG", 2);
//		ds.setColumn(0, "PJT", 1);
//		ds.setColumn(0, "HNO", 2);

		// 목록 정보 옮기기 
//		CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));

		System.out.println("==========listD.size() = " + listD.size());
		
//		System.out.println("==========listD.get(0) = " + (String)listD.get(0).getClass().getField("aa") );
//		System.out.println("==========listD.getClass().getDeclaredFields() = " + listD.getClass().getField("MANDT") );
		
		System.out.println("==========ds.getRowCount() = " + ds.getRowCount());
		System.out.println("==========ds.getId() = " + ds.getId());
		
		MipResultVO resultVO = new MipResultVO();

		for (int i=0; i<listD.size(); i++) {
//			listD.get(i).getClass().getField("MANDT");
//			resultVO = (MipResultVO)listD.get(i);
			
//			CallSAP.moveObj2Ds(ds, listD.get(i));
			System.out.println("==========ds.getColumn(0, PJT) = " +ds.getColumn(0, "PJT"));
			System.out.println("==========ds.getColumn(0, MANDT) = " +ds.getColumn(0, "MANDT"));
		}
		
		resultVO.addDataset(ds);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
//	
//	public void test1(HttpServletRequest req, HttpServletResponse res) {
//		try {
//			PlatformRequest pReq = new PlatformRequest(req);
//			
//			pReq.receiveData();
//			
//			VariableList vl = pReq.getVariableList();
//			com.tobesoft.platform.data.DatasetList dl = pReq.getDatasetList();
//			
//			com.tobesoft.platform.data.Dataset dsCond = dl.getDataset("ds_cond");
//			
//			// 입력 파라메터 처리 
//			ZCSS_TEST[] list = new ZCSS_TEST[]{};
//			ZQMSEMSG[] listMsg = new ZQMSEMSG[]{};
//			Dataset dsError = null;
//
//			Object obj[] = new Object[]{
//					DatasetUtility.getString(dsCond, "HST")
//					, listMsg
//					, list
//			};
//
//			SapFunction stub = CallSAP.callSap(vl.getValueAsString("G_MANDT"), "com.helco.xf.cs96.ws.ZWEB_CS_TEST", obj);
//			// 출력 Dataset 생성 
//			Dataset ds = CallSAP.isJCO() ? new Dataset() : ZCSS_TEST.getDataset();
//			ds.setId("ds_list");
//			
//			// 목록 정보 옮기기 
//			CallSAP.moveObj2Ds(ds, stub.getOutput("O_TAB"));
//			listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
//			dsError = CallSAP.makeErrorInfo(listMsg);
//			
//			PlatformResponse pRes = new PlatformResponse(res, PlatformResponse.XML, "utf-8");
//			com.tobesoft.platform.data.DatasetList dlo = new com.tobesoft.platform.data.DatasetList();
//			
//			dlo.addDataset(ds);
//			
//			pRes.sendData(vl, dlo);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
