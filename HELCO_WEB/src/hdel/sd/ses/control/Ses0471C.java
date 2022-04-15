package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.scl.domain.ZSDS0014E;
import hdel.sd.scl.domain.ZSDS0015E;
import hdel.sd.ses.domain.Ses0401;
import hdel.sd.ses.domain.Ses0471;
import hdel.sd.ses.domain.Ses0471ParamVO;
import hdel.sd.ses.service.Ses0471S;
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
@RequestMapping("ses0471")
public class Ses0471C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0471S service;
	
	
	/**
	 * ��������û �� ��ȸ
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View Ses0471FindView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0471FindView START");
		
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
			
			Ses0471ParamVO param = new Ses0471ParamVO();
			param.setMANDT (DatasetUtility.getString(dsCond, "MANDT" ));	// SAP CLIENT
			param.setZRQSEQ(DatasetUtility.getString(dsCond, "ZRQSEQ"));	// ��û�Ϸù�ȣ
			param.setZATGBN(DatasetUtility.getString(dsCond, "ZATGBN"));	// ÷�α���
			param.setUUSER(paramVO.getVariable("G_USER_ID"));				// ó����
			
			dsHeader.deleteAll();
			List<Ses0471> list = service.selectListHeader(param);      // ����CALL
			CallSAPHdel.moveListToDs(dsHeader, Ses0471.class, list);  // DATASET ����
			logger.debug("@@@ dsHeader.getRowCount ===> " + dsHeader.getRowCount());
			
			resultVO.addDataset(dsHeader);
			
			// ÷������ ����Ʈ
			dsFile.deleteAll();
			List<Ses0471> listFile = service.selectListFile(param);    // ����CALL
			CallSAPHdel.moveListToDs(dsFile, Ses0471.class, listFile);  // DATASET ����
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
	 * ��������û �� ���
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="save")
	public View Ses0471SaveView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0471SaveView START");
		
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
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String ZRQFLG   = DatasetUtility.getString(dsHeader, 0, "ZRQFLG"  ); if (ZRQFLG  == null) ZRQFLG   = "";
			String ZRQDAT   = DatasetUtility.getString(dsHeader, 0, "ZRQDAT"  ); if (ZRQDAT  == null) ZRQDAT   = "";
			String ZRQSTAT  = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT" ); if (ZRQSTAT == null) ZRQSTAT  = "";
			String ZRSRLT   = DatasetUtility.getString(dsHeader, 0, "ZRSRLT"  ); if (ZRSRLT  == null) ZRSRLT   = "";
			String KUNNR_Z3 = DatasetUtility.getString(dsHeader, 0, "KUNNR_Z3"); if (KUNNR_Z3== null) KUNNR_Z3 = "";
			String QTSO_NO  = DatasetUtility.getString(dsHeader, 0, "QTSO_NO" ); if (QTSO_NO == null) QTSO_NO  = "";
			String QTSER    = DatasetUtility.getString(dsHeader, 0, "QTSER"   ); if (QTSER   == null) QTSER    = "";
			String FWRD     = DatasetUtility.getString(dsHeader, 0, "FWRD"    ); if (FWRD    == null) FWRD     = "";
			String FINDAT   = DatasetUtility.getString(dsHeader, 0, "FINDAT"  ); if (FINDAT  == null) FINDAT   = "";
			String OUTSRCDT   = DatasetUtility.getString(dsHeader, 0, "OUTSRCDT"  ); if (OUTSRCDT  == null) OUTSRCDT   = "";
			String OUTFINDT   = DatasetUtility.getString(dsHeader, 0, "OUTFINDT"  ); if (OUTFINDT  == null) OUTFINDT   = "";
			String OUTCANDT   = DatasetUtility.getString(dsHeader, 0, "OUTCANDT"  ); if (OUTCANDT  == null) OUTCANDT   = "";
			String F_BRPT    = DatasetUtility.getString(dsHeader, 0, "F_BRPT" );   if (F_BRPT   == null) F_BRPT    = "";
			String F_SRPT    = DatasetUtility.getString(dsHeader, 0, "F_SRPT" );   if (F_SRPT   == null) F_SRPT    = "";                         
			String J_LOPL    = DatasetUtility.getString(dsHeader, 0, "J_LOPL" );   if (J_LOPL   == null) J_LOPL    = "";
			String J_BYRPH   = DatasetUtility.getString(dsHeader, 0, "J_BYRPH" );  if (J_BYRPH  == null) J_BYRPH   = "";
			String J_STDYN   = DatasetUtility.getString(dsHeader,  0, "J_STDYN");  if (J_STDYN  == null) J_STDYN = "";
			String J_SUBDAT  = DatasetUtility.getString(dsHeader, 0, "J_SUBDAT" ); if (J_SUBDAT == null) J_SUBDAT  = "";
			String J_USE     = DatasetUtility.getString(dsHeader, 0, "J_USE" );    if (J_USE    == null) J_USE     = "";
			String J_TYPE    = DatasetUtility.getString(dsHeader, 0, "J_TYPE" );   if (J_TYPE   == null) J_TYPE    = "";
			String J_MODL    = DatasetUtility.getString(dsHeader, 0, "J_MODL" );   if (J_MODL   == null) J_MODL    = "";
			String J_GRDQ    = DatasetUtility.getString(dsHeader, 0, "J_GRDQ" );   if (J_GRDQ   == null) J_GRDQ    = "0";
			String J_EQSQ    = DatasetUtility.getString(dsHeader, 0, "J_EQSQ" );   if (J_EQSQ   == null) J_EQSQ    = "0";
			String J_SUVQ    = DatasetUtility.getString(dsHeader, 0, "J_SUVQ" );   if (J_SUVQ   == null) J_SUVQ    = "0";
			String J_TTBL    = DatasetUtility.getString(dsHeader, 0, "J_TTBL" );   if (J_TTBL   == null) J_TTBL    = "";
			String J_EXMT    = DatasetUtility.getString(dsHeader, 0, "J_EXMT" );   if (J_EXMT   == null) J_EXMT    = "";
			String J_EXMTDP  = DatasetUtility.getString(dsHeader, 0, "J_EXMTDP" ); if (J_EXMTDP == null) J_EXMTDP  = "0";
			String J_PLAL    = DatasetUtility.getString(dsHeader, 0, "J_PLAL" );   if (J_PLAL   == null) J_PLAL    = "";
			String J_EFST    = DatasetUtility.getString(dsHeader, 0, "J_EFST" );   if (J_EFST   == null) J_EFST    = "";
			String J_DWG     = DatasetUtility.getString(dsHeader, 0, "J_DWG" );    if (J_DWG    == null) J_DWG     = "";
			String ZRQCN01   = DatasetUtility.getString(dsHeader, 0, "ZRQCN01"  ); if (ZRQCN01  == null) ZRQCN01    = "";
			String ZRQCN02   = DatasetUtility.getString(dsHeader, 0, "ZRQCN02"  ); if (ZRQCN02  == null) ZRQCN02    = "";
			String ZRQCN03   = DatasetUtility.getString(dsHeader, 0, "ZRQCN03"  ); if (ZRQCN03  == null) ZRQCN03    = "";
			String ZRQCN04   = DatasetUtility.getString(dsHeader, 0, "ZRQCN04"  ); if (ZRQCN04  == null) ZRQCN04    = "";
			String ZRQCN05   = DatasetUtility.getString(dsHeader, 0, "ZRQCN05"  ); if (ZRQCN05  == null) ZRQCN05    = "";
			String ZRQCN06   = DatasetUtility.getString(dsHeader, 0, "ZRQCN06"  ); if (ZRQCN06  == null) ZRQCN06    = "";
			String ZRQCN07   = DatasetUtility.getString(dsHeader, 0, "ZRQCN07"  ); if (ZRQCN07  == null) ZRQCN07    = "";
			String ZRQCN08   = DatasetUtility.getString(dsHeader, 0, "ZRQCN08"  ); if (ZRQCN08  == null) ZRQCN08    = "";
			String ZRQCN09   = DatasetUtility.getString(dsHeader, 0, "ZRQCN09"  ); if (ZRQCN09  == null) ZRQCN09    = "";
			String ZRQCN10   = DatasetUtility.getString(dsHeader, 0, "ZRQCN10"  ); if (ZRQCN10  == null) ZRQCN10    = "";
			String UUSER    = uuser;
			String GVGCD    = gvbcd;
			
			Ses0471 param = new Ses0471();
			
			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setZRQSEQ  (ZRQSEQ  ); // ��û�Ϸù�ȣ
			param.setZRQFLG  (ZRQFLG  ); // ��û����
			param.setZRQDAT  (ZRQDAT  ); // ��û��
			param.setZRQSTAT (ZRQSTAT ); // ��û����(��û/����/�ݷ�/�Ϸ�)
			param.setZRSRLT  (ZRSRLT  ); // ������
			param.setKUNNR_Z3(KUNNR_Z3); // ������������
			param.setQTSO_NO (QTSO_NO ); // ����/���� ��ȣ
			param.setQTSER   (QTSER   ); // ��������
			param.setFWRD    (FWRD    ); // ȣ�� ��ȣ
			param.setFINDAT  (FINDAT  ); //
			param.setOUTSRCDT  (OUTSRCDT  ); //
			param.setOUTFINDT  (OUTFINDT  ); //
			param.setOUTCANDT  (OUTCANDT  ); //
			param.setF_BRPT  (F_BRPT   );
			param.setF_SRPT  (F_SRPT   );
			param.setJ_LOPL  (J_LOPL   );
			param.setJ_BYRPH (J_BYRPH  );
			param.setJ_STDYN(J_STDYN);
			param.setJ_SUBDAT(J_SUBDAT );
			param.setJ_USE   (J_USE    );
			param.setJ_TYPE  (J_TYPE   );
			param.setJ_MODL  (J_MODL   );
			param.setJ_GRDQ  (J_GRDQ   );
			param.setJ_EQSQ  (J_EQSQ   );
			param.setJ_SUVQ  (J_SUVQ   );
			param.setJ_TTBL  (J_TTBL   );
			param.setJ_EXMT  (J_EXMT   );
			param.setJ_EXMTDP(J_EXMTDP );
			param.setJ_PLAL  (J_PLAL   );
			param.setJ_EFST  (J_EFST   );
			param.setJ_DWG   (J_DWG    );
			param.setZRQCN01 (ZRQCN01 ); // ��û����
			param.setZRQCN02 (ZRQCN02 ); // ��û����
			param.setZRQCN03 (ZRQCN03 ); // ��û����
			param.setZRQCN04 (ZRQCN04 ); // ��û����
			param.setZRQCN05 (ZRQCN05 ); // ��û����
			param.setZRQCN06 (ZRQCN06 ); // ��û����
			param.setZRQCN07 (ZRQCN07 ); // ��û����
			param.setZRQCN08 (ZRQCN08 ); // ��û����
			param.setZRQCN09 (ZRQCN09 ); // ��û����
			param.setZRQCN10 (ZRQCN10 ); // ��û����
			param.setUUSER   (UUSER   ); // �Է���
			param.setGVGCD   (GVGCD   ); // �μ��ڵ�
			
			
			// ÷������ ����Ʈ ó��
			List<Ses0471> listFile = new ArrayList<Ses0471>();
			
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
				
				Ses0471 paramFile = new Ses0471();
				
				paramFile.setMANDT   (fMANDT   );
				paramFile.setZRQSEQ  (fZRQSEQ  );
				paramFile.setZATTSEQ (fZATTSEQ );
				paramFile.setZRQSTAT (fZRQSTAT );
				paramFile.setZATGBN  (fZATGBN  );
				paramFile.setZATTPATH(fZATTPATH);
				paramFile.setZATTFN  (fZATTFN  );
				paramFile.setFLAG    (fFLAG    );
				paramFile.setUUSER   (fUUSER   );
				
				listFile.add(paramFile);
			}
			
			// ����
			String strNewZrqSeq = service.save(param, listFile);
			
			dsHeader.setColumn(0, "ZRQSEQ", strNewZrqSeq);
			
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
	 * ��������û ����/�ݷ�
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveZrqstat")
	public View Ses0411SaveZrqstat(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0411SaveReceipt START");
		
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
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String ZRQSTAT  = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT" ); if (ZRQSTAT  == null) ZRQSTAT   = "";
			String KUNNR_Z3 = DatasetUtility.getString(dsHeader, 0, "KUNNR_Z3"); if (KUNNR_Z3== null) KUNNR_Z3 = "";
			String J_MODL = DatasetUtility.getString(dsHeader, 0, "J_MODL"); if (J_MODL== null) J_MODL = "";
			String ZRSRLT   = DatasetUtility.getString(dsHeader, 0, "ZRSRLT"  ); if (ZRSRLT  == null) ZRSRLT   = "";
			String FWRD     = DatasetUtility.getString(dsHeader, 0, "FWRD"    ); if (FWRD    == null) FWRD     = "";
			String J_STDYN  = DatasetUtility.getString(dsHeader, 0, "J_STDYN" ); if (J_STDYN    == null) J_STDYN = "";
			String UUSER    = uuser;
			
			Ses0471 param = new Ses0471();
			
			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setZRQSEQ  (ZRQSEQ  ); // ��û�Ϸù�ȣ
			param.setZRQSTAT (ZRQSTAT  ); // ó������
			param.setJ_MODL(J_MODL); // Model Name
			param.setKUNNR_Z3(KUNNR_Z3); // ������������
			param.setZRSRLT  (ZRSRLT  ); // ó�����
			param.setFWRD    (FWRD    ); // ó�����
			param.setJ_STDYN(J_STDYN); // �Է���
			param.setUUSER   (UUSER   ); // �Է���

			// ����
			service.saveZrqstat(param);
			
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
	public View Ses0471SaveApprovalView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0471SaveApprovalView START");
		
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
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String ZRQSTAT  = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT" ); if (ZRQSTAT == null) ZRQSTAT  = "";
			String ZRSRLT   = DatasetUtility.getString(dsHeader, 0, "ZRSRLT"  ); if (ZRSRLT  == null) ZRSRLT   = "";
			String UUSER    = uuser;
			
			Ses0471 param = new Ses0471();
			
			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setZRQSEQ  (ZRQSEQ  ); // ��û�Ϸù�ȣ
			param.setZRQSTAT (ZRQSTAT ); // ��û����(����)
			param.setZRSRLT  (ZRSRLT  ); // ó�����
			param.setUUSER   (UUSER   ); // �Է���
			
			// ÷������ ����Ʈ ó��
			List<Ses0471> listFile = new ArrayList<Ses0471>();
			
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
				
				Ses0471 paramFile = new Ses0471();
				
				paramFile.setMANDT   (fMANDT   );
				paramFile.setZRQSEQ  (fZRQSEQ  );
				paramFile.setZATTSEQ (fZATTSEQ );
				paramFile.setZRQSTAT (fZRQSTAT );
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

	@RequestMapping(value="findPrint")
	public View Ses0471FindPrintView(MipParameterVO paramVO, Model model) {

		Dataset dsPrint    = paramVO.getDataset("ds_print");					// UI�� return�� out dataset
		Dataset dsTemplate = paramVO.getDataset("ds_template");					// UI�� return�� out dataset
		Dataset dsError    = CallSAPHdel.makeErrorInfoCreateDs("ds_error");		// UI�� return�� �����޼��� dataset

		// RFC ��� dataset�� return
		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Ses0471ParamVO param = new Ses0471ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			param.setMANDT(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setZRQSEQ(paramVO.getVariable("P_ZRQSEQ"));
			param.setGUBUN (paramVO.getVariable("P_GUBUN"));

			// �������
			List<Ses0471> list = service.selectPrintHeader(param);   // ����CALL

			dsPrint.deleteAll();
			CallSAPHdel.moveListToDs(dsPrint, Ses0471.class, list);  // DATASET ����
			resultVO.addDataset(dsPrint);

			if ( list != null && list.size() > 0 )	{
				// �������
				if ("Q".equals(param.getGUBUN()))	{
					List<Ses0471> listTemplate = service.selectPrintTemplate(param);   // ����CALL
	
					dsTemplate.deleteAll();
					CallSAPHdel.moveListToDs(dsTemplate, Ses0471.class, listTemplate);  // DATASET ����
					resultVO.addDataset(dsTemplate);
				}else	{
					// RFC INPUT PARAM DECLARE 
					ZSDT0094[] list_ZSDT0094 = new ZSDT0094[]{};  // sap output list
					ZQMSEMSG[] listMsg  	 = new ZQMSEMSG[]{};  // sap �����޽��� return��

					String sVBELN = StringUtils.leftPad(list.get(0).getQTSO_NO(), 10, "0");
					
					// WFC INPUT SET
					Object obj[] = new Object[]{ 
							  sVBELN 					// ������Ʈ��ȣ
							, list.get(0).getHOGI()  	// ȣ��
							, list.get(0).getPOSNR()	//�ǸŹ���ǰ��
							, listMsg
							, list_ZSDT0094
					}; 
		
					// OUTPUT DATASET DECLARE
					Dataset ds_output_ZSDT0094 = null;		// sap�� ������� dataSet���� �������.

					// RFC CALL
					SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT") 	// ���Ǳ���
										, "hdel.sd.sso.domain.ZWEB_SD_HOGI_SPEC2"			
										, obj, false);	
		
					// RFC ��±���ü�� out dataset ���� (�ʿ��� Dataset�� ó��)
					ds_output_ZSDT0094 = CallSAP.isJCO() ? new Dataset() : ZSDT0094.getDataset();
		
					// o_etab --> ó������ ����
					listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
					if ( "4".equals(stub.getOutput("E_TYPE").toString()) ) {	// ����
						dsError = CallSAP.makeErrorInfo(listMsg);
					}else	{
						// RFC ȣ������ out dataset�� �ű��  
						CallSAP.moveObj2Ds(ds_output_ZSDT0094, stub.getOutput("S_ITAB"));
		
						dsTemplate.deleteAll();
		
						for (int i = 0; i < ds_output_ZSDT0094.getRowCount(); i++)	{
							dsTemplate.appendRow(); 	// ���߰�
		
							dsTemplate.setColumn(i, "MANDT"  , ds_output_ZSDT0094.getColumn(i, "MANDT") );
							dsTemplate.setColumn(i, "ATKLA"  , ds_output_ZSDT0094.getColumn(i, "ATKLA") );
							dsTemplate.setColumn(i, "PRH"    , ds_output_ZSDT0094.getColumn(i, "NAM_CHAR") );
							dsTemplate.setColumn(i, "PRD"    , ds_output_ZSDT0094.getColumn(i, "CHAR_VALUE") );
							dsTemplate.setColumn(i, "PRHNAME", ds_output_ZSDT0094.getColumn(i, "ATBEZ") );
						}

						resultVO.addDataset(dsTemplate);
					}
				}
			}
			
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value="outsourcing")
	public View Ses0471OutsourcingView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0411outsourcing START");
		
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
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String UUSER    = uuser;
			
			Ses0471 param = new Ses0471();
			
			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setZRQSEQ  (ZRQSEQ  ); // ��û�Ϸù�ȣ
			param.setUUSER   (UUSER   ); // �Է���

			// ����
			service.saveOutdate(param);
			
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

	@RequestMapping(value="outcancel")
	public View Ses0471OutcancelView(MipParameterVO paramVO, Model model) {
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI�� return�� out dataset
		
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset
		
		MipResultVO resultVO = new MipResultVO(); // ��� dataset�� return
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
		
		try {
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String UUSER    = uuser;
			
			Ses0471 param = new Ses0471();
			
			param.setMANDT(MANDT);
			param.setZRQSEQ(ZRQSEQ);
			param.setUUSER(UUSER);

			// ����
			service.saveOutcandt(param);
			
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

	@RequestMapping(value="outfinish")
	public View Ses0471OutfinishView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0411outsourcing START");
		
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
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String UUSER    = uuser;
			
			Ses0471 param = new Ses0471();
			
			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setZRQSEQ  (ZRQSEQ  ); // ��û�Ϸù�ȣ
			param.setUUSER   (UUSER   ); // �Է���

			// ����
			service.saveOutfinish(param);
			
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
	 * ��������û �� ���� 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="delete")
	public View Ses0471DeleteView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0471DeleteView START");		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UI�� return�� out dataset
		
		//SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
			
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
			
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset
			
		// DAO ����
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
				
		try {
			Ses0471 param = new Ses0471();
			
			param.setMANDT(DatasetUtility.getString(dsHeader, 0, "MANDT"   )); 
			if (param.getMANDT() == null) param.setMANDT("");
			param.setZRQSEQ(DatasetUtility.getString(dsHeader, 0, "ZRQSEQ" )); 
			if (param.getZRQSEQ() == null) param.setZRQSEQ("");
						
			// ����
			service.deleteHeader(param);
			service.deleteFile2(param);
						
			// define ��� VIEW
			MipResultVO resultVO = new MipResultVO(); // ��� dataset�� return
			resultVO.addDataset(dsHeader);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		return view;
	}
}
