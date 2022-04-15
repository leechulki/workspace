package hdel.sd.sbi.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sbi.domain.Sbi0030;
import hdel.sd.sbi.domain.Sbi0030ParamVO;
import hdel.sd.sbi.service.Sbi0030S;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sbi0030")
public class Sbi0030C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbi0030S service;
	
	
	/**
	 * 영업수요정보 조회
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View Sbi0030FindView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbi0030FindView START");
		
		// get Input Dataset
		Dataset dsCond    = paramVO.getDataset("ds_cond");
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI로 return할 out dataset
		Dataset dsFile    = paramVO.getDataset("ds_file");	// UI로 return할 out dataset
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");	// SAP CLIENT
		
		// define Error Dataset
		Dataset dsError   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		// define 결과 VIEW
		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
		
		try {
			
			// DAO 생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			Sbi0030ParamVO param = new Sbi0030ParamVO();
			param.setMANDT (DatasetUtility.getString(dsCond, "MANDT" ));	// SAP CLIENT
			param.setINFNO(DatasetUtility.getString(dsCond, "INFNO"));	// 요청일련번호
			param.setUUSER(paramVO.getVariable("G_USER_ID"));				// 처리자
			
			dsHeader.deleteAll();
			List<Sbi0030> list = service.selectListHeader(param);      // 서비스CALL
			CallSAPHdel.moveListToDs(dsHeader, Sbi0030.class, list);  // DATASET 복사
			logger.debug("@@@ dsHeader.getRowCount ===> " + dsHeader.getRowCount());
			
			resultVO.addDataset(dsHeader);
			/*
			// 첨부파일 리스트
			dsFile.deleteAll();
			List<Sbi0030> listFile = service.selectListFile(param);    // 서비스CALL
			CallSAPHdel.moveListToDs(dsFile, Sbi0030.class, listFile);  // DATASET 복사
			logger.debug("@@@ dsFile.getRowCount ===> " + dsFile.getRowCount());
			
			resultVO.addDataset(dsFile);  */
			
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	/**
	 * 영업수요정보 입력
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="save")
	public View Sbi0030SaveView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Sbi0030SaveView START");
		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI로 return할 out dataset
		Dataset dsFile    = paramVO.getDataset("ds_file");	// UI로 return할 out dataset
		
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
			String MANDT   = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String INFNO   = DatasetUtility.getString(dsHeader, 0, "INFNO"  ); if (INFNO  == null) INFNO   = "";
			String INFDT   = DatasetUtility.getString(dsHeader, 0, "INFDT"  ); if (INFDT  == null) INFDT   = "";
			String INFNM   = DatasetUtility.getString(dsHeader, 0, "INFNM"  ); if (INFNM  == null) INFNM   = "";
			String DPT     = DatasetUtility.getString(dsHeader, 0, "DPT"  ); if (DPT  == null) DPT   = "";
			String VKBURT   = DatasetUtility.getString(dsHeader, 0, "VKBURT"  ); if (VKBURT  == null) VKBURT   = "";
			String VKGRPT   = DatasetUtility.getString(dsHeader, 0, "VKGRPT"  ); if (VKGRPT  == null) VKGRPT   = "";
			String PHONE   = DatasetUtility.getString(dsHeader, 0, "PHONE"  ); if (PHONE  == null) PHONE   = "";
			String CELL    = DatasetUtility.getString(dsHeader, 0, "CELL"  ); if (CELL  == null) CELL   = "";
			String FAX     = DatasetUtility.getString(dsHeader, 0, "FAX"  ); if (FAX  == null) FAX   = "";
			String EMAIL   = DatasetUtility.getString(dsHeader, 0, "EMAIL"  ); if (EMAIL  == null) EMAIL   = "";
			String BSTNK   = DatasetUtility.getString(dsHeader, 0, "BSTNK"  ); if (BSTNK  == null) BSTNK   = "";
			String COMPA   = DatasetUtility.getString(dsHeader, 0, "COMPA"  ); if (COMPA  == null) COMPA   = "";
			String POST    = DatasetUtility.getString(dsHeader, 0, "POST"  ); if (POST  == null) POST   = "";
			String ADDR    = DatasetUtility.getString(dsHeader, 0, "ADDR"  ); if (ADDR  == null) ADDR   = "";
			String CSTNM   = DatasetUtility.getString(dsHeader, 0, "CSTNM"  ); if (CSTNM  == null) CSTNM   = "";
			String CSTPH   = DatasetUtility.getString(dsHeader, 0, "CSTPH"  ); if (CSTPH  == null) CSTPH   = "";
			String CSTCL   = DatasetUtility.getString(dsHeader, 0, "CSTCL"  ); if (CSTCL  == null) CSTCL   = "";
			String PROD    = DatasetUtility.getString(dsHeader, 0, "PROD"  ); if (PROD  == null) PROD   = "";
			String ZQNTY   = DatasetUtility.getString(dsHeader, 0, "ZQNTY"  ); if (ZQNTY  == null) ZQNTY   = "";
			String BIGO    = DatasetUtility.getString(dsHeader, 0, "BIGO"  ); if (BIGO  == null) BIGO   = "";
			String STATUS  = DatasetUtility.getString(dsHeader, 0, "STATUS"  ); if (STATUS  == null) STATUS   = "";
			String ZKUNNR  = DatasetUtility.getString(dsHeader, 0, "ZKUNNR"  ); if (ZKUNNR  == null) ZKUNNR   = "";
			String ZKUNNRT = DatasetUtility.getString(dsHeader, 0, "ZKUNNRT" ); if (ZKUNNRT == null) ZKUNNRT  = "";
			String UUSER   = uuser;
			
			Sbi0030 param = new Sbi0030();
			
			param.setMANDT  (MANDT   ); // SAP CLIENT
			param.setINFNO  (INFNO   ); // 영업정보 번호
			param.setINFDT  (INFDT   );
			param.setINFNM  (INFNM   );
			param.setDPT    (DPT   );
			param.setVKBURT (VKBURT   );
			param.setVKGRPT (VKGRPT   );
			param.setPHONE  (PHONE   );
			param.setCELL   (CELL   );
			param.setFAX    (FAX   );
			param.setEMAIL  (EMAIL   );
			param.setBSTNK  (BSTNK   );
			param.setCOMPA  (COMPA   );
			param.setADDR   (ADDR   );
			param.setPOST   (POST   );
			param.setCSTNM  (CSTNM   );
			param.setCSTPH  (CSTPH   );
			param.setCSTCL  (CSTCL   );
			param.setPROD   (PROD   );
			param.setZQNTY  (ZQNTY   );
			param.setBIGO   (BIGO   );
			param.setSTATUS  (STATUS   );
			param.setZKUNNR  (ZKUNNR   );
			param.setZKUNNRT  (ZKUNNRT  );
			param.setUUSER   (UUSER   ); // 입력자
			
			
			// 첨부파일 리스트 처리
			List<Sbi0030> listFile = new ArrayList<Sbi0030>();
			/*
			for ( int i = 0 ; i < dsFile.getRowCount() ; i++ ) {
				String fMANDT    = DatasetUtility.getString(dsFile, i, "MANDT"   ); if (fMANDT    == null) fMANDT    = "";
				String fZRQSEQ   = DatasetUtility.getString(dsFile, i, "ZRQSEQ"  ); if (fZRQSEQ   == null) fZRQSEQ   = "";
				String fZATTSEQ  = DatasetUtility.getString(dsFile, i, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";
				String fZRQSTAT  = DatasetUtility.getString(dsFile, i, "ZRQSTAT" ); if (fZRQSTAT  == null) fZRQSTAT  = "";
				String fZATGBN   = DatasetUtility.getString(dsFile, i, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
				String fZATTPATH = DatasetUtility.getString(dsFile, i, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
				String fZATTFN   = DatasetUtility.getString(dsFile, i, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
				String fFLAG     = DatasetUtility.getString(dsFile, i, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
				String fUUSER    = uuser;
				
				Sbi0030 paramFile = new Sbi0030();
				
				paramFile.setMANDT   (fMANDT   );
				paramFile.setUUSER   (fUUSER   );
				
				listFile.add(paramFile);
			}
			*/
			// 실행
			String strNewInfNo = service.save(param, listFile);
			
			dsHeader.setColumn(0, "INFNO", strNewInfNo);
			
			resultVO.addDataset(dsHeader);
			resultVO.addDataset(dsFile);  
			
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}

}
