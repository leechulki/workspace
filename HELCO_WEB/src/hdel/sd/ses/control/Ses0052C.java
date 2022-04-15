package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.scl.domain.ZSDS0014E;
import hdel.sd.scl.domain.ZSDS0015E;
import hdel.sd.ses.domain.Ses0052;
import hdel.sd.ses.domain.Ses0052ParamVO;
import hdel.sd.ses.service.Ses0052S;
import hdel.sd.sso.domain.ZSDT0094;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
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
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0052")
public class Ses0052C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0052S service;
	
	
	/**
	 * 주차설비 정보 조회 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View Ses0052FindView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0052FindView START");
		
		// get Input Dataset
		Dataset dsCond    = paramVO.getDataset("ds_cond");
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI로 return할 out dataset
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");	// SAP CLIENT
		
		// define Error Dataset
		Dataset dsError   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		// define 결과 VIEW
		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
		
		try {
			
			// DAO 생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			Ses0052ParamVO param = new Ses0052ParamVO();
			param.setMANDT (DatasetUtility.getString(dsCond, "MANDT" ));	// SAP CLIENT
			param.setQTNUM(DatasetUtility.getString(dsCond, "QTNUM"));	    // 견적번호
			param.setQTSER(DatasetUtility.getString(dsCond, "QTSER"));	    // 견적차수
			
			dsHeader.deleteAll();
			List<Ses0052> list = service.selectListHeader(param);      // 서비스CALL
			CallSAPHdel.moveListToDs(dsHeader, Ses0052.class, list);  // DATASET 복사
			logger.debug("@@@ dsHeader.getRowCount ===> " + dsHeader.getRowCount());
			
			resultVO.addDataset(dsHeader);
						
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	 /*
	 * 주차설비 상세 등록
	 * @param paramVO
	 * @param model
	 * @return
	 */
	 
	@RequestMapping(value="save")
	public View Ses0052SaveView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0052SaveView START");
		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI로 return할 out dataset
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		// define 결과 VIEW
		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
		
		// DAO 생성
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		try {
			//String MANDT   = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT  == null) MANDT  = "";
			String MANDT   = mandt;
			String QTNUM   = DatasetUtility.getString(dsHeader, 0, "QTNUM"   ); if (QTNUM  == null) QTNUM  = "";
			String QTSER   = DatasetUtility.getString(dsHeader, 0, "QTSER"   ); if (QTSER  == null) QTSER  = "0";
			String JTYPE1  = DatasetUtility.getString(dsHeader, 0, "JTYPE1"  ); if (JTYPE1 == null) JTYPE1 = "";
			String JTYPE2  = DatasetUtility.getString(dsHeader, 0, "JTYPE2"  ); if (JTYPE2 == null) JTYPE2 = "";
			String JTYPE3  = DatasetUtility.getString(dsHeader, 0, "JTYPE3"  ); if (JTYPE3 == null) JTYPE3 = "";
			String JTYPE4  = DatasetUtility.getString(dsHeader, 0, "JTYPE4"  ); if (JTYPE4 == null) JTYPE4 = "";
			String TEXT    = DatasetUtility.getString(dsHeader, 0, "TEXT"    ); if (TEXT   == null) TEXT = "";
			
			String UUSER    = uuser;
			
			Ses0052 param = new Ses0052();
			
			param.setMANDT   (MANDT  ); // SAP CLIENT
			param.setQTNUM   (QTNUM  );  
			param.setQTSER   (QTSER  );  
			param.setTEXT    (TEXT);
			param.setJTYPE1  (JTYPE1 ); 
			param.setJTYPE2  (JTYPE2 );
			param.setJTYPE3  (JTYPE3 ); 
			param.setJTYPE4  (JTYPE4 );			 
			param.setUUSER   (UUSER   ); // 입력자 
		
			service.saveHeader(param);
			resultVO.addDataset(dsHeader);
			
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}	
	/**
	 * 주차설비 정보 조회 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	/*
	@RequestMapping(value="selectQtn")
	public View Ses0052SelectQtn(MipParameterVO paramVO, Model model) {
				
		// get Input Dataset
		Dataset dsCond    = paramVO.getDataset("ds_header");
		int     Qtcnt;
		//Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI로 return할 out dataset
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");	// SAP CLIENT
		
		// define Error Dataset
		Dataset dsError   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		// define 결과 VIEW
		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
		
		try {
			
			// DAO 생성
			service.createDao(sqlSession.getSqlSession(mandt)); // DAO생성
			
			Ses0052ParamVO param = new Ses0052ParamVO();
			param.setMANDT (DatasetUtility.getString(dsCond, "MANDT" ));	// SAP CLIENT
			param.setQTNUM(DatasetUtility.getString(dsCond, "QTNUM"));	    // 견적번호
			param.setQTSER(DatasetUtility.getString(dsCond, "QTSER"));	    // 견적차수
			
			
			//List<Ses0052> list = service.selectListHeader(param);      // 서비스CALL
			Qtcnt = service.selectQtn(param);
			
			resultVO.addDataset(dsHeader);
						
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	*/
	/**
	 * 기술검토요청 상세 삭제 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value="delete")
	public View Ses0052DeleteView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0052DeleteView START");		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI로 return할 out dataset
		
		//SqlSession session = sqlSession.getSqlSession(paramVO.getVariable("G_MANDT"));// Session GET
			
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
			
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
			
		// DAO 생성
		service.createDao(sqlSession.getSqlSession(mandt)); // DAO생성
				
		try {
			Ses0052 param = new Ses0052();
			
			param.setMANDT(DatasetUtility.getString(dsHeader, 0, "MANDT"   )); 
			if (param.getMANDT() == null) param.setMANDT("");
			param.setZRQSEQ(DatasetUtility.getString(dsHeader, 0, "ZRQSEQ" )); 
			if (param.getZRQSEQ() == null) param.setZRQSEQ("");
						
			// 실행
			service.deleteHeader(param);
			service.deleteFile2(param);
						
			// define 결과 VIEW
			MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
			resultVO.addDataset(dsHeader);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		return view;
	}*/
}
