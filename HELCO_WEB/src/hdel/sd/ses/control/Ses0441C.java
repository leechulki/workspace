package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.scl.domain.ZSDS0014E;
import hdel.sd.scl.domain.ZSDS0015E;
import hdel.sd.ses.domain.Ses0441;
import hdel.sd.ses.domain.Ses0441ParamVO;
import hdel.sd.ses.service.Ses0441S;
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
@RequestMapping("ses0441")
public class Ses0441C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0441S service;
	
	
	/**
	 * �� ��ȸ
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View Ses0441FindView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0441FindView START");
		
		// get Input Dataset
		Dataset dsCond    = paramVO.getDataset("ds_cond");
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI�� return�� out dataset
		Dataset dsFile    = paramVO.getDataset("ds_file");	// UI�� return�� out dataset
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");	// SAP CLIENT
		
		// define Error Dataset
		Dataset dsError   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset
		
		// define ��� VIEW
		MipResultVO resultVO = new MipResultVO(); // ��� dataset�� return
		
		try {
			
			// DAO ����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			Ses0441ParamVO param = new Ses0441ParamVO();
			param.setMANDT (DatasetUtility.getString(dsCond, "MANDT" ));	// SAP CLIENT
			param.setPSPID(DatasetUtility.getString(dsCond, "PSPID"));	// ������Ʈ
			param.setSEQ(DatasetUtility.getString(dsCond, "SEQ"));	// ��������
			param.setRQSER(DatasetUtility.getString(dsCond, "RQSER"));	// ��û���� 
			
			dsHeader.deleteAll();
			List<Ses0441> list = service.selectList(param);      // ����CALL
			CallSAPHdel.moveListToDs(dsHeader, Ses0441.class, list);  // DATASET ����
			
			resultVO.addDataset(dsHeader);
			
			// ÷������ ����Ʈ
			dsFile.deleteAll();
			List<Ses0441> listFile = service.selectListFile(param);    // ����CALL
			CallSAPHdel.moveListToDs(dsFile, Ses0441.class, listFile);  // DATASET ����
			logger.debug("@@@ dsFile.getRowCount ===> " + dsFile.getRowCount());
			
			resultVO.addDataset(dsFile);
			
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
	 * SAVE
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="save")
	public View Ses0441SaveView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0441SaveView START");
		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI�� return�� out dataset
		Dataset dsFile    = paramVO.getDataset("ds_file");	// UI�� return�� out dataset
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		String gvbcd = paramVO.getVariable("G_SAP_USER_VGCD");	// �μ��ڵ�
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset
		
		// define ��� VIEW
		MipResultVO resultVO = new MipResultVO(); // ��� dataset�� return
		
		// DAO ����
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
		
		try {
			String PSPID   = DatasetUtility.getString(dsHeader, 0, "PSPID"  ); if (PSPID   == null) PSPID    = "";
			String SEQ     = DatasetUtility.getString(dsHeader, 0, "SEQ"    ); if (SEQ     == null) SEQ      = "";
			String RQSER   = DatasetUtility.getString(dsHeader, 0, "RQSER"  ); if (RQSER   == null) RQSER    = "";
			String REQDAT  = DatasetUtility.getString(dsHeader, 0, "REQDAT" ); if (REQDAT  == null) REQDAT   = "";
			String FINDAT  = DatasetUtility.getString(dsHeader, 0, "FINDAT" ); if (FINDAT  == null) FINDAT   = "";
			String IS_LO   = DatasetUtility.getString(dsHeader, 0, "IS_LO"  ); if (IS_LO   == null) IS_LO    = "";
			String IS_DM   = DatasetUtility.getString(dsHeader, 0, "IS_DM"  ); if (IS_DM   == null) IS_DM    = "";

			String BIGO01  = DatasetUtility.getString(dsHeader, 0, "BIGO01" ); if (BIGO01  == null) BIGO01   = "";
			String BIGO02  = DatasetUtility.getString(dsHeader, 0, "BIGO02" ); if (BIGO02  == null) BIGO02   = "";
			String BIGO03  = DatasetUtility.getString(dsHeader, 0, "BIGO03" ); if (BIGO03  == null) BIGO03   = "";
			String BIGO04  = DatasetUtility.getString(dsHeader, 0, "BIGO04" ); if (BIGO04  == null) BIGO04   = "";
			String BIGO05  = DatasetUtility.getString(dsHeader, 0, "BIGO05" ); if (BIGO05  == null) BIGO05   = "";
			String BIGO06  = DatasetUtility.getString(dsHeader, 0, "BIGO06" ); if (BIGO06  == null) BIGO06   = "";
			String BIGO07  = DatasetUtility.getString(dsHeader, 0, "BIGO07" ); if (BIGO07  == null) BIGO07   = "";
			String BIGO08  = DatasetUtility.getString(dsHeader, 0, "BIGO08" ); if (BIGO08  == null) BIGO08   = "";
			String BIGO09  = DatasetUtility.getString(dsHeader, 0, "BIGO09" ); if (BIGO09  == null) BIGO09   = "";
			String BIGO10  = DatasetUtility.getString(dsHeader, 0, "BIGO10" ); if (BIGO10  == null) BIGO10   = "";
			String REPL01  = DatasetUtility.getString(dsHeader, 0, "REPL01" ); if (REPL01  == null) REPL01   = "";
			String REPL02  = DatasetUtility.getString(dsHeader, 0, "REPL02" ); if (REPL02  == null) REPL02   = "";
			String REPL03  = DatasetUtility.getString(dsHeader, 0, "REPL03" ); if (REPL03  == null) REPL03   = "";
			String REPL04  = DatasetUtility.getString(dsHeader, 0, "REPL04" ); if (REPL04  == null) REPL04   = "";
			String REPL05  = DatasetUtility.getString(dsHeader, 0, "REPL05" ); if (REPL05  == null) REPL05   = "";
			String REPL06  = DatasetUtility.getString(dsHeader, 0, "REPL06" ); if (REPL06  == null) REPL06   = "";
			String REPL07  = DatasetUtility.getString(dsHeader, 0, "REPL07" ); if (REPL07  == null) REPL07   = "";
			String REPL08  = DatasetUtility.getString(dsHeader, 0, "REPL08" ); if (REPL08  == null) REPL08   = "";
			String REPL09  = DatasetUtility.getString(dsHeader, 0, "REPL09" ); if (REPL09  == null) REPL09   = "";
			String REPL10  = DatasetUtility.getString(dsHeader, 0, "REPL10" ); if (REPL10  == null) REPL10   = "";
			
			
			Ses0441 param = new Ses0441();
			
			param.setMANDT   (mandt  ); // SAP CLIENT
			param.setPSPID   (PSPID  ); // ������Ʈ
			param.setSEQ     (SEQ    ); // ��������  
			param.setRQSER   (RQSER  ); // ��û����   
			param.setSTATUS  ("M"    ); // ����  '�ۼ�'  
			param.setREQDAT  (REQDAT ); // ��û�� 
			param.setFINDAT  (FINDAT ); // �Ϸ���
			param.setIS_LO   (IS_LO  ); // LO ����
			param.setIS_DM   (IS_DM  ); // ��Ÿ���� ����
			param.setBIGO01  (BIGO01 ); // Ư����� 
			param.setBIGO02  (BIGO02 ); // Ư�����
			param.setBIGO03  (BIGO03 ); // Ư�����
			param.setBIGO04  (BIGO04 ); // Ư�����
			param.setBIGO05  (BIGO05 ); // Ư�����
			param.setBIGO06  (BIGO06 ); // Ư�����
			param.setBIGO07  (BIGO07 ); // Ư�����
			param.setBIGO08  (BIGO08 ); // Ư�����
			param.setBIGO09  (BIGO09 ); // Ư�����
			param.setBIGO10  (BIGO10 ); // Ư�����
			param.setREPL01  (REPL01 ); // ȸ��
			param.setREPL02  (REPL02 ); // ȸ��
			param.setREPL03  (REPL03 ); // ȸ��
			param.setREPL04  (REPL04 ); // ȸ��
			param.setREPL05  (REPL05 ); // ȸ��
			param.setREPL06  (REPL06 ); // ȸ��
			param.setREPL07  (REPL07 ); // ȸ��
			param.setREPL08  (REPL08 ); // ȸ��
			param.setREPL09  (REPL09 ); // ȸ��
			param.setREPL10  (REPL10 ); // ȸ��
			param.setUUSER   (uuser   );
			
			
			// ÷������ ����Ʈ ó��
			List<Ses0441> listFile = new ArrayList<Ses0441>();
			
			for ( int i = 0 ; i < dsFile.getRowCount() ; i++ ) {
				String fMANDT    = DatasetUtility.getString(dsFile, i, "MANDT"   ); if (fMANDT    == null) fMANDT    = "";
				String fPSPID    = DatasetUtility.getString(dsFile, i, "PSPID"   ); if (fPSPID    == null) fPSPID    = "";
				String fSEQ      = DatasetUtility.getString(dsFile, i, "SEQ"     ); if (fSEQ      == null) fSEQ      = "";
				String fRQSER    = DatasetUtility.getString(dsFile, i, "RQSER"   ); if (fRQSER    == null) fRQSER    = "";
				String fZATTSEQ  = DatasetUtility.getString(dsFile, i, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";
				String fZATGBN   = DatasetUtility.getString(dsFile, i, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
				String fZATTPATH = DatasetUtility.getString(dsFile, i, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
				String fZATTFN   = DatasetUtility.getString(dsFile, i, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
				String fFLAG     = DatasetUtility.getString(dsFile, i, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
				String fUUSER    = uuser;
				
				Ses0441 paramFile = new Ses0441();
				
				paramFile.setMANDT   (fMANDT   );
				paramFile.setPSPID   (fPSPID   );
				paramFile.setSEQ     (fSEQ     );
				paramFile.setRQSER   (fRQSER     );
				paramFile.setZATTSEQ (fZATTSEQ  );
				paramFile.setSTATUS  ("R"  );
				paramFile.setZATGBN  (fZATGBN  );
				paramFile.setZATTPATH(fZATTPATH);
				paramFile.setZATTFN  (fZATTFN  );
				paramFile.setFLAG    (fFLAG    );
				paramFile.setUUSER   (fUUSER   );
				
				listFile.add(paramFile);
			}
			
			// ����
			String strNewRqseq = service.save(param, listFile);
						
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
	/**
	 * �����û ����/�ݷ�
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveRequest")
	public View Ses0411SaveZrqstat(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0411SaveRequest START");
		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI�� return�� out dataset
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset
		
		// define ��� VIEW
		MipResultVO resultVO = new MipResultVO(); // ��� dataset�� return
		
		// DAO ����
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
		
		try {
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String PSPID    = DatasetUtility.getString(dsHeader, 0, "PSPID"   ); if (PSPID   == null) PSPID    = "";
			String SEQ      = DatasetUtility.getString(dsHeader, 0, "SEQ"     ); if (SEQ      == null) SEQ       = "";
			String RQSER    = DatasetUtility.getString(dsHeader, 0, "RQSER"   ); if (RQSER   == null) RQSER      = "";
			String REQDAT   = DatasetUtility.getString(dsHeader, 0, "REQDAT"  ); if (REQDAT  == null) REQDAT     = "";
			String STATUS   = DatasetUtility.getString(dsHeader, 0, "STATUS"  ); if (STATUS   == null) STATUS    = "";
			String UUSER    = uuser;
			
			Ses0441 param = new Ses0441();
			
			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setPSPID   (PSPID   ); // ��������Ʈ
			param.setSEQ     (SEQ      ); // ����
			param.setRQSER   (RQSER      ); // ��û����
			param.setREQDAT  (REQDAT     ); // ��û��
			param.setSTATUS  ("R"  ); // ���� '��û'
			param.setUUSER   (UUSER   ); // �Է���

			// ����
			service.saveRequest(param);
			
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
	 * ��������û ����
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveApproval")
	public View Ses0441SaveApprovalView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0441SaveApprovalView START");
		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI�� return�� out dataset
		Dataset dsFile    = paramVO.getDataset("ds_file");	// UI�� return�� out dataset
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset
		
		// define ��� VIEW
		MipResultVO resultVO = new MipResultVO(); // ��� dataset�� return
		
		// DAO ����
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
		
		try {
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String PSPID    = DatasetUtility.getString(dsHeader, 0, "PSPID"   ); if (PSPID   == null) PSPID    = "";
			String SEQ      = DatasetUtility.getString(dsHeader, 0, "SEQ"     ); if (SEQ      == null) SEQ       = "";
			String RQSER    = DatasetUtility.getString(dsHeader, 0, "RQSER"   ); if (RQSER    == null) RQSER       = "";
			String STATUS   = DatasetUtility.getString(dsHeader, 0, "STATUS"  ); if (STATUS   == null) STATUS    = "";
			String UUSER    = uuser;
			
			Ses0441 param = new Ses0441();

			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setPSPID   (PSPID   ); // ������Ʈ
			param.setSEQ     (SEQ      ); // ����
			param.setRQSER   (RQSER    ); // ��û����
			param.setSTATUS  ("S"  ); // ó������  
			param.setUUSER   (UUSER   ); // �Է���
			
			// ÷������ ����Ʈ ó��
			List<Ses0441> listFile = new ArrayList<Ses0441>();
			
			for ( int i = 0 ; i < dsFile.getRowCount() ; i++ ) {
				String fMANDT    = DatasetUtility.getString(dsFile, i, "MANDT"   ); if (fMANDT    == null) fMANDT    = "";
				String fPSPID    = DatasetUtility.getString(dsFile, i, "PSPID"   ); if (fPSPID    == null) fPSPID    = "";
				String fSEQ      = DatasetUtility.getString(dsFile, i, "SEQ"     ); if (fSEQ      == null) fSEQ      = "";
				String fRQSER    = DatasetUtility.getString(dsFile, i, "RQSER"   ); if (fRQSER    == null) fRQSER      = "";
				String fZATTSEQ  = DatasetUtility.getString(dsFile, i, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";
				String fSTATUS   = DatasetUtility.getString(dsFile, i, "STATUS"  ); if (fSTATUS   == null) fSTATUS   = "";
				String fZATGBN   = DatasetUtility.getString(dsFile, i, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
				String fZATTPATH = DatasetUtility.getString(dsFile, i, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
				String fZATTFN   = DatasetUtility.getString(dsFile, i, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
				String fFLAG     = DatasetUtility.getString(dsFile, i, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
				String fUUSER    = uuser;
				
				Ses0441 paramFile = new Ses0441();
				
				paramFile.setMANDT   (fMANDT   );
				paramFile.setPSPID   (fPSPID   );
				paramFile.setSEQ     (fSEQ     );
				paramFile.setRQSER   (fRQSER   );
				paramFile.setZATTSEQ  (fZATTSEQ  );
				paramFile.setSTATUS  ("S"  );
				paramFile.setZATGBN  (fZATGBN  );
				paramFile.setZATTPATH(fZATTPATH);
				paramFile.setZATTFN  (fZATTFN  );
				paramFile.setFLAG    (fFLAG    );
				paramFile.setUUSER   (fUUSER   );
				
				
				listFile.add(paramFile);
			}
			
			// ����
			service.saveApproval(param, listFile);
			
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
