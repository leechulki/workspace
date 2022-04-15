package hdel.sd.ses.control;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.sap.domain.Lifnr;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT0161;
import hdel.lib.domain.ZSDT1084;
import hdel.lib.service.ZSDT0161S;
import hdel.lib.service.ZSDT0198S;
import hdel.lib.service.ZSDT1084S;
import hdel.sd.ses.domain.Ses0401;
import hdel.sd.ses.domain.Ses0401ParamVO;
import hdel.sd.ses.domain.ZSDT1079;
import hdel.sd.ses.service.Ses0401S;
import hdel.sd.sso.domain.ZSDT0094;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0401")
public class Ses0401C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0401S service;
	@Autowired
	private ZSDT0161S zsdt0161s;
	@Autowired
	private ZSDT0198S zsdt0198s;
	@Autowired
	private ZSDT1084S zsdt1084s;

	
	/**
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View Ses0401FindView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0401FindView START");
		
		// get Input Dataset
		Dataset dsCond		= paramVO.getDataset("ds_cond");
		Dataset dsHeader	= paramVO.getDataset("ds_header");
		Dataset dsFile		= paramVO.getDataset("ds_file");
		Dataset dsFile2  	= paramVO.getDataset("ds_file_2");
		Dataset ds1079		= paramVO.getDataset("ds_zsdt1079");
		Dataset ds1084		= paramVO.getDataset("ds_1084");
		
		Dataset dsError   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();
		
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			zsdt1084s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			Ses0401ParamVO param = new Ses0401ParamVO();
			param.setMANDT (DatasetUtility.getString(dsCond, "MANDT" ));	// SAP CLIENT
			param.setZRQSEQ(DatasetUtility.getString(dsCond, "ZRQSEQ"));	// ¿äÃ»ÀÏ·Ã¹øÈ£
			param.setZATGBN(DatasetUtility.getString(dsCond, "ZATGBN"));	// Ã·ºÎ±¸ºÐ
			param.setUUSER(paramVO.getVariable("G_USER_ID"));				// Ã³¸®ÀÚ
			
			dsHeader.deleteAll();
			List<Ses0401> list = service.selectListHeader(param);      // ¼­ºñ½ºCALL
			CallSAPHdel.moveListToDs(dsHeader, Ses0401.class, list);  // DATASET º¹»ç
			logger.debug("@@@ dsHeader.getRowCount ===> " + dsHeader.getRowCount());
			resultVO.addDataset(dsHeader);

			ds1079.deleteAll();
			List<ZSDT1079> list1079 = service.searchChangedCharPart(param.getMANDT(), param.getZRQSEQ());
//			CallSAPHdel.moveListToDs(ds1079, ZSDT1079.class, list1079);
			for (int i = 0; i < list1079.size(); i++) {

				ds1079.appendRow();
				ds1079.setColumn(i, "MANDT",	list1079.get(i).getMandt());
				ds1079.setColumn(i, "ZRQSEQ",	list1079.get(i).getZrqseq());
				ds1079.setColumn(i, "CHGCHRPT",	list1079.get(i).getChgchrpt()); 
			}

			if(ds1084 != null) {
				ds1084.deleteAll();
				ZSDT1084 zsdt1084 = zsdt1084s.select(param.getMANDT(), param.getZRQSEQ());
				if(zsdt1084 != null) {
					ds1084.appendRow();
					ds1084.setColumn(0, "zrqseq", zsdt1084.getZrqseq());
					ds1084.setColumn(0, "vbeln", zsdt1084.getVbeln().toString());
					ds1084.setColumn(0, "seq", zsdt1084.getSeq());
				}
				resultVO.addDataset(ds1084);
			}

			resultVO.addDataset(ds1079);

			dsFile.deleteAll();
			dsFile2.deleteAll();
			List<Ses0401> listFile = service.selectListFile(param);
			for (int i = 0; i < listFile.size(); i++) {
				String filegbn = listFile.get(i).getFILEGBN();
				if(filegbn.equals("S")) {
					dsFile.appendRow();
					//dsFile.setColumn(dsFile.getRowCount()-1, "MANDT", listFile.get(i).getZRQSEQ());
					dsFile.setColumn(dsFile.getRowCount()-1, "MANDT", listFile.get(i).getMANDT()); //2020.09.17 jss
					dsFile.setColumn(dsFile.getRowCount()-1, "ZRQSEQ", listFile.get(i).getZRQSEQ());
					dsFile.setColumn(dsFile.getRowCount()-1, "ZATTSEQ", listFile.get(i).getZATTSEQ());
					dsFile.setColumn(dsFile.getRowCount()-1, "FILEGBN", listFile.get(i).getFILEGBN());
					dsFile.setColumn(dsFile.getRowCount()-1, "ZATGBN", listFile.get(i).getZATGBN());
					dsFile.setColumn(dsFile.getRowCount()-1, "ZATTPATH", listFile.get(i).getZATTPATH());
					dsFile.setColumn(dsFile.getRowCount()-1, "ZATTFN", listFile.get(i).getZATTFN());
					dsFile.setColumn(dsFile.getRowCount()-1, "FLAG", listFile.get(i).getFLAG());
					dsFile.setColumn(dsFile.getRowCount()-1, "UUSER", listFile.get(i).getUUSER());
					
				}else if(filegbn.equals("O")) {
					dsFile2.appendRow();
					dsFile2.setColumn(dsFile2.getRowCount()-1, "MANDT", listFile.get(i).getMANDT());
					dsFile2.setColumn(dsFile2.getRowCount()-1, "ZRQSEQ", listFile.get(i).getZRQSEQ());
					dsFile2.setColumn(dsFile2.getRowCount()-1, "ZATTSEQ", listFile.get(i).getZATTSEQ());
					dsFile2.setColumn(dsFile2.getRowCount()-1, "FILEGBN", listFile.get(i).getFILEGBN());
					dsFile2.setColumn(dsFile2.getRowCount()-1, "ZATGBN", listFile.get(i).getZATGBN());
					dsFile2.setColumn(dsFile2.getRowCount()-1, "ZATTPATH", listFile.get(i).getZATTPATH());
					dsFile2.setColumn(dsFile2.getRowCount()-1, "ZATTFN", listFile.get(i).getZATTFN());
					dsFile2.setColumn(dsFile2.getRowCount()-1, "FLAG", listFile.get(i).getFLAG());
					dsFile2.setColumn(dsFile2.getRowCount()-1, "UUSER", listFile.get(i).getUUSER());
				}
			}
			resultVO.addDataset(dsFile);
			resultVO.addDataset(dsFile2);
			
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
	 * ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ã» ï¿½Ï°ï¿½ ï¿½ï¿½Ã» 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveAll")
	public View Ses0401SaveAllView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0401SaveView START");
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		Dataset dsFile    = paramVO.getDataset("ds_file");
		
		// get Variable
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		String gvbcd = paramVO.getVariable("G_SAP_USER_VGCD");
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAOï¿½ï¿½ï¿½ï¿½

		
		try {
			
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String ZRQFLG   = DatasetUtility.getString(dsHeader, 0, "ZRQFLG"  ); if (ZRQFLG  == null) ZRQFLG   = "";
			String ZRQDAT   = DatasetUtility.getString(dsHeader, 0, "ZRQDAT"  ); if (ZRQDAT  == null) ZRQDAT   = "";
			String ZRQCN    = DatasetUtility.getString(dsHeader, 0, "ZRQCN"   ); if (ZRQCN   == null) ZRQCN    = "";
			String ZRQSTAT  = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT" ); if (ZRQSTAT == null) ZRQSTAT  = "";
			String ZRSRLT   = DatasetUtility.getString(dsHeader, 0, "ZRSRLT"  ); if (ZRSRLT  == null) ZRSRLT   = "";
			String KUNNR_Z3 = DatasetUtility.getString(dsHeader, 0, "KUNNR_Z3"); if (KUNNR_Z3== null) KUNNR_Z3 = "";
			
			String QTSO_NO  = DatasetUtility.getString(dsHeader, 0, "QTSO_NO" ); if (QTSO_NO == null) QTSO_NO  = "";
			String QTSER    = DatasetUtility.getString(dsHeader, 0, "QTSER"   ); if (QTSER   == null) QTSER    = "";
			String QTSEQ    = DatasetUtility.getString(dsHeader, 0, "QTSEQ"   ); if (QTSEQ   == null) QTSEQ    = "";
			String HOGI     = DatasetUtility.getString(dsHeader, 0, "HOGI"    ); if (HOGI    == null) HOGI     = "";
			String FWRD     = DatasetUtility.getString(dsHeader, 0, "FWRD"    ); if (FWRD    == null) FWRD     = "";
			String FINDAT   = DatasetUtility.getString(dsHeader, 0, "FINDAT"  ); if (FINDAT  == null) FINDAT   = "";
			String CONFDAT   = DatasetUtility.getString(dsHeader, 0, "CONFDAT"  ); if (CONFDAT  == null) CONFDAT   = "";
			String CONFDOCU   = DatasetUtility.getString(dsHeader, 0, "CONFDOCU"  ); if (CONFDOCU  == null) CONFDOCU   = "";
			String OUTGBN    = DatasetUtility.getString(dsHeader, 0, "OUTGBN"  ); if (OUTGBN  == null) OUTGBN   = "";
			String OUTSRCDT   = DatasetUtility.getString(dsHeader, 0, "OUTSRCDT"  ); if (OUTSRCDT  == null) OUTSRCDT   = "";
			String OUTFINDT   = DatasetUtility.getString(dsHeader, 0, "OUTFINDT"  ); if (OUTFINDT  == null) OUTFINDT   = "";
			String OUTCANDT   = DatasetUtility.getString(dsHeader, 0, "OUTCANDT"  ); if (OUTCANDT  == null) OUTCANDT   = "";
			String OUTACTDT   = DatasetUtility.getString(dsHeader, 0, "OUTACTDT"  ); if (OUTACTDT  == null) OUTACTDT   = "";
			String OUTRETDT   = DatasetUtility.getString(dsHeader, 0, "OUTRETDT"  ); if (OUTRETDT  == null) OUTRETDT   = "";
			String OUTGRD   = DatasetUtility.getString(dsHeader, 0, "OUTGRD"  ); if (OUTGRD  == null) OUTGRD   = "";
			String OUTLOQ   = DatasetUtility.getString(dsHeader, 0, "OUTLOQ"  ); if (OUTLOQ  == null) OUTLOQ   = "";
			String OUTMAN   = DatasetUtility.getString(dsHeader, 0, "OUTMAN"  ); if (OUTMAN  == null) OUTMAN   = "";
			String LODAT    = DatasetUtility.getString(dsHeader, 0, "LODAT"   ); if (LODAT   == null) LODAT    = "";
			String CODAT    = DatasetUtility.getString(dsHeader, 0, "CODAT"   ); if (CODAT   == null) CODAT    = "";
			String LP_IS    = DatasetUtility.getString(dsHeader, 0, "LP_IS"   ); if (LP_IS   == null) LP_IS    = "";
			String LP_OLD   = DatasetUtility.getString(dsHeader, 0, "LP_OLD"   ); if (LP_OLD   == null) LP_OLD    = "";
			String NONSTD   = DatasetUtility.getString(dsHeader, 0, "NONSTD"   ); if (NONSTD   == null) NONSTD    = "";
			String LP_CHN   = DatasetUtility.getString(dsHeader, 0, "LP_CHN"   ); if (LP_CHN   == null) LP_CHN    = "";
			String SPDOCU   = DatasetUtility.getString(dsHeader, 0, "SPDOCU"  ); if (SPDOCU  == null) SPDOCU   = "";
			String APR_IS   = DatasetUtility.getString(dsHeader, 0, "APR_IS"  ); if (APR_IS  == null) APR_IS   = "";
			String DRW_IS   = DatasetUtility.getString(dsHeader, 0, "DRW_IS"  ); if (DRW_IS  == null) DRW_IS   = "";
			String GSNAM    = DatasetUtility.getString(dsHeader, 0, "GSNAM"   ); if (GSNAM   == null) GSNAM    = "";
			String UUSER    = uuser;
			String GVGCD    = gvbcd;
			
			Ses0401 param = new Ses0401();
			
			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setZRQSEQ  (ZRQSEQ  );
			param.setZRQFLG  (ZRQFLG  );
			param.setZRQDAT  (ZRQDAT  );
			param.setZRQCN   (ZRQCN   );
			param.setZRQSTAT (ZRQSTAT );
			param.setZRSRLT  (ZRSRLT  );
			param.setKUNNR_Z3(KUNNR_Z3);
			param.setQTSO_NO (QTSO_NO );
			param.setQTSER   (QTSER   );
			param.setQTSEQ   (QTSEQ   );
			param.setHOGI    (HOGI    );
			param.setFWRD    (FWRD    ); 
			param.setFINDAT  (FINDAT  );
			param.setCONFDAT  (CONFDAT  ); //
			param.setCONFDOCU (CONFDOCU  ); //
			param.setOUTGBN  (OUTGBN  ); //
			param.setOUTSRCDT  (OUTSRCDT  ); //
			param.setOUTFINDT  (OUTFINDT  ); //
			param.setOUTCANDT  (OUTCANDT  ); //
			param.setOUTACTDT  (OUTACTDT  ); //
			param.setOUTRETDT  (OUTRETDT  ); //
			param.setOUTGRD    (OUTGRD    ); //
			param.setOUTLOQ    (OUTLOQ    ); //
			param.setOUTMAN    (OUTMAN    ); //
			param.setLODAT   (LODAT   );
			param.setCODAT   (CODAT   );
			param.setLP_IS   (LP_IS   );
			param.setLP_CHN   (LP_CHN   );
			param.setLP_OLD   (LP_OLD   );
			param.setNONSTD   (NONSTD   );
			param.setSPDOCU  (SPDOCU  ); 
			param.setAPR_IS  (APR_IS  ); 
			param.setDRW_IS  (DRW_IS  );
			param.setGSNAM   (GSNAM   );
			param.setUUSER   (UUSER   );
			param.setGVGCD   (GVGCD   );

			List<Ses0401> list = service.selectQtseq(param);
			
			List<Ses0401> listFile = new ArrayList<Ses0401>();
			
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
				
				Ses0401 paramFile = new Ses0401();
				
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
			
			for ( int i = 0 ; i < list.size() ; i++ ) { 
				if ( ZRQSEQ.equals("") ) {
					param.setZRQSEQ  ("");   
				}
				param.setQTSEQ   ( list.get(i).getQTSEQ() );
				String strNewZrqSeq = service.save(param, listFile);
				dsHeader.setColumn(0, "ZRQSEQ", strNewZrqSeq);
			}
			
			
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
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="save")
	public View Ses0401SaveView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0401SaveView START");
		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		Dataset dsFile    = paramVO.getDataset("ds_file");
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			String strNewZrqSeq = service.save(configureHeaderParamToSave(dsHeader, paramVO), configureFileParamToSave(dsFile, paramVO));
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), strNewZrqSeq, ZSDT0161.cistat.SR.toString(), paramVO.getVariable("G_USER_ID"));
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
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="rewrite")
	public View Rewrite(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0401SaveView START");
		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		Dataset dsFile    = paramVO.getDataset("ds_file");
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			String strNewZrqSeq = service.save(configureHeaderParamToSave(dsHeader, paramVO), configureFileParamToSave(dsFile, paramVO));
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), strNewZrqSeq, ZSDT0161.cistat.SG.toString(), paramVO.getVariable("G_USER_ID"));
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
	
	private Ses0401 configureHeaderParamToSave(Dataset dsHeader, MipParameterVO paramVO) {
		Ses0401 param = new Ses0401();
		param.setMANDT(DatasetUtility.getString(dsHeader, 0, "MANDT"));
		param.setZRQSEQ(DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"));
		param.setZRQFLG(DatasetUtility.getString(dsHeader, 0, "ZRQFLG"));
		param.setZRQDAT(DatasetUtility.getString(dsHeader, 0, "ZRQDAT"));
		param.setZRQCN(DatasetUtility.getString(dsHeader, 0, "ZRQCN"));
		param.setZRQSTAT(DatasetUtility.getString(dsHeader, 0, "ZRQSTAT"));
		param.setZRSRLT(DatasetUtility.getString(dsHeader, 0, "ZRSRLT"));
		param.setKUNNR_Z3(DatasetUtility.getString(dsHeader, 0, "KUNNR_Z3"));
		param.setQTSO_NO(DatasetUtility.getString(dsHeader, 0, "QTSO_NO"));
		param.setQTSER(DatasetUtility.getString(dsHeader, 0, "QTSER"));
		param.setQTSEQ(DatasetUtility.getString(dsHeader, 0, "QTSEQ"));
		param.setHOGI(DatasetUtility.getString(dsHeader, 0, "HOGI"));
		param.setFWRD(DatasetUtility.getString(dsHeader, 0, "FWRD"));
		param.setFINDAT(DatasetUtility.getString(dsHeader, 0, "FINDAT"));
		param.setCONFDAT(DatasetUtility.getString(dsHeader, 0, "CONFDAT"));
		param.setCONFDOCU(DatasetUtility.getString(dsHeader, 0, "CONFDOCU"));
		param.setOUTGBN(DatasetUtility.getString(dsHeader, 0, "OUTGBN"));
		param.setOUTSRCDT(DatasetUtility.getString(dsHeader, 0, "OUTSRCDT"));
		param.setOUTFINDT(DatasetUtility.getString(dsHeader, 0, "OUTFINDT"));
		param.setOUTCANDT(DatasetUtility.getString(dsHeader, 0, "OUTCANDT"));
		param.setOUTACTDT(DatasetUtility.getString(dsHeader, 0, "OUTACTDT"));
		param.setOUTRETDT(DatasetUtility.getString(dsHeader, 0, "OUTRETDT"));
		param.setOUTGRD(DatasetUtility.getString(dsHeader, 0, "OUTGRD"));
		param.setOUTLOQ(DatasetUtility.getString(dsHeader, 0, "OUTLOQ"));
		param.setOUTMAN(DatasetUtility.getString(dsHeader, 0, "OUTMAN"));
		param.setLODAT(DatasetUtility.getString(dsHeader, 0, "LODAT"));
		param.setCODAT(DatasetUtility.getString(dsHeader, 0, "CODAT"));
		param.setSPDOCU(DatasetUtility.getString(dsHeader, 0, "SPDOCU"));
		param.setLP_IS(DatasetUtility.getString(dsHeader, 0, "LP_IS"));
		param.setLP_CHN(DatasetUtility.getString(dsHeader, 0, "LP_CHN"));
		param.setLP_OLD(DatasetUtility.getString(dsHeader, 0, "LP_OLD"));
		param.setNONSTD(DatasetUtility.getString(dsHeader, 0, "NONSTD"));
		param.setAPR_IS(DatasetUtility.getString(dsHeader, 0, "APR_IS"));
		param.setDRW_IS(DatasetUtility.getString(dsHeader, 0, "DRW_IS"));
		param.setGSNAM(DatasetUtility.getString(dsHeader, 0, "GSNAM"));
		param.setUUSER(paramVO.getVariable("G_USER_ID"));
		param.setGVGCD(paramVO.getVariable("G_SAP_USER_VGCD"));

		return param;
	}

	private List<Ses0401> configureFileParamToSave(Dataset dsFile, MipParameterVO paramVO) {
		List<Ses0401> listFile = new ArrayList<Ses0401>();
		
		for ( int i = 0 ; i < dsFile.getRowCount() ; i++ ) {
			Ses0401 paramFile = new Ses0401();
			paramFile.setMANDT(DatasetUtility.getString(dsFile, i, "MANDT"));
			paramFile.setZRQSEQ(DatasetUtility.getString(dsFile, i, "ZRQSEQ"));
			paramFile.setZATTSEQ(DatasetUtility.getString(dsFile, i, "ZATTSEQ"));
			paramFile.setFILEGBN(DatasetUtility.getString(dsFile, i, "FILEGBN"));
			paramFile.setZRQSTAT(DatasetUtility.getString(dsFile, i, "ZRQSTAT"));
			paramFile.setZATGBN(DatasetUtility.getString(dsFile, i, "ZATGBN"));
			paramFile.setZATTPATH(DatasetUtility.getString(dsFile, i, "ZATTPATH"));
			paramFile.setZATTFN(DatasetUtility.getString(dsFile, i, "ZATTFN"));
			paramFile.setFLAG(DatasetUtility.getString(dsFile, i, "FLAG"));
			paramFile.setUUSER(paramVO.getVariable("G_USER_ID"));
			
			listFile.add(paramFile);
		}

		return listFile;
	}

	/**
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveZrqstat")
	public View Ses0411SaveZrqstat(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0411SaveReceipt START");
		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		Dataset ds1079  = paramVO.getDataset("ds_zsdt1079");
		
		// get Variable
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		String cistat = paramVO.getVariable("cistat");
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		try {
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String ZRQSTAT  = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT" ); if (ZRQSTAT  == null) ZRQSTAT   = "";
			String CONFDAT  = DatasetUtility.getString(dsHeader, 0, "CONFDAT"  ); if (CONFDAT  == null) CONFDAT   = "";
			String CONFDOCU  = DatasetUtility.getString(dsHeader, 0, "CONFDOCU"  ); if (CONFDOCU  == null) CONFDOCU   = "";
			String KUNNR_Z3 = DatasetUtility.getString(dsHeader, 0, "KUNNR_Z3"); if (KUNNR_Z3== null) KUNNR_Z3 = "";
			String OUTGBN 	= DatasetUtility.getString(dsHeader, 0, "OUTGBN"); if (OUTGBN == null) OUTGBN = "";
			String ZRSRLT   = DatasetUtility.getString(dsHeader, 0, "ZRSRLT"  ); if (ZRSRLT  == null) ZRSRLT   = "";
			String FWRD     = DatasetUtility.getString(dsHeader, 0, "FWRD"    ); if (FWRD    == null) FWRD     = "";
			String UUSER    = uuser;
			
			Ses0401 param = new Ses0401();
			
			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setZRQSEQ  (ZRQSEQ  );
			param.setZRQSTAT (ZRQSTAT  );
			param.setKUNNR_Z3(KUNNR_Z3);
			param.setOUTGBN(OUTGBN);
			param.setZRSRLT  (ZRSRLT  );
			param.setFWRD    (FWRD    );
			param.setCONFDAT (CONFDAT  );
			param.setCONFDOCU (CONFDOCU );
			param.setUUSER   (UUSER   );
			param.setCADREQTYPE(DatasetUtility.getString(dsHeader, 0, "CADREQTYPE"));

			service.saveZrqstat(param);
			
			resultVO.addDataset(dsHeader);

			if(ds1079 != null) {
				List<ZSDT1079> ls1079 = new ArrayList<ZSDT1079>();
				for(int i=0; i<ds1079.getRowCount(); i++) {
					ZSDT1079 zsdt1079 = new ZSDT1079();
					zsdt1079.setMandt(param.getMANDT());
					zsdt1079.setZrqseq(param.getZRQSEQ());
					zsdt1079.setChgchrpt(DatasetUtility.getString(ds1079, i, "CHGCHRPT"));
					ls1079.add(zsdt1079);
				}
				//»ó¼¼³»¿ª ÀúÀå½Ã ¿À·ùÇ×¸ñ ÀúÀå Á¦¿Ü
				//service.deleteNInsert1079(MANDT, ZRQSEQ, ls1079);
			}
			if(cistat != null) {
				switch(ZSDT0161.cistat.valueOf(cistat)) {
					case TB:
					case TA:
					case EA:
						zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), ZRQSEQ, cistat, paramVO.getVariable("G_USER_ID"));
						break;
				}
			}
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
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveApproval")
	public View Ses0401SaveApprovalView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0401SaveApprovalView START");
		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		Dataset dsFile    = paramVO.getDataset("ds_file");
		
		// get Variable
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAOï¿½ï¿½ï¿½ï¿½
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String ZRQSTAT  = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT" ); if (ZRQSTAT == null) ZRQSTAT  = "";
			String CONFDAT  = DatasetUtility.getString(dsHeader, 0, "CONFDAT"  ); if (CONFDAT  == null) CONFDAT   = "";
			String CONFDOCU  = DatasetUtility.getString(dsHeader, 0, "CONFDOCU"  ); if (CONFDOCU  == null) CONFDOCU   = "";
			String ZRSRLT   = DatasetUtility.getString(dsHeader, 0, "ZRSRLT"  ); if (ZRSRLT  == null) ZRSRLT   = "";
			String UUSER    = uuser;
			
			Ses0401 param = new Ses0401();
			
			param.setMANDT   (MANDT   );
			param.setZRQSEQ  (ZRQSEQ  );
			param.setZRQSTAT (ZRQSTAT );
			param.setCONFDAT (CONFDAT  );
			param.setCONFDOCU (CONFDOCU);
			param.setZRSRLT  (ZRSRLT  );
			param.setUUSER   (UUSER   );
			
			List<Ses0401> listFile = new ArrayList<Ses0401>();
			
			for ( int i = 0 ; i < dsFile.getRowCount() ; i++ ) {
				String fMANDT    = DatasetUtility.getString(dsFile, i, "MANDT"   ); if (fMANDT    == null) fMANDT    = "";
				String fZRQSEQ   = DatasetUtility.getString(dsFile, i, "ZRQSEQ"  ); if (fZRQSEQ   == null) fZRQSEQ   = "";
				String fZATTSEQ  = DatasetUtility.getString(dsFile, i, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";
				String fZRQSTAT  = DatasetUtility.getString(dsFile, i, "ZRQSTAT" ); if (fZRQSTAT  == null) fZRQSTAT  = "";
				String fZATGBN   = DatasetUtility.getString(dsFile, i, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
				String fZATTPATH = DatasetUtility.getString(dsFile, i, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
				String fZATTFN   = DatasetUtility.getString(dsFile, i, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
				String fFLAG     = DatasetUtility.getString(dsFile, i, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
				String fFILEGBN  = DatasetUtility.getString(dsFile, i, "FILEGBN"    ); if (fFILEGBN     == null) fFILEGBN     = "";
				String fUUSER    = uuser;
				
				Ses0401 paramFile = new Ses0401();
				
				paramFile.setMANDT   (fMANDT   );
				paramFile.setZRQSEQ  (fZRQSEQ  );
				paramFile.setZATTSEQ (fZATTSEQ );
				paramFile.setZRQSTAT (fZRQSTAT );
				paramFile.setZATGBN  (fZATGBN  );
				paramFile.setZATTPATH(fZATTPATH);
				paramFile.setZATTFN  (fZATTFN  );
				paramFile.setFLAG    (fFLAG    );
				paramFile.setFILEGBN(fFILEGBN);
				paramFile.setUUSER   (fUUSER   );
				
				listFile.add(paramFile);
			}
			
			// ï¿½ï¿½ï¿½ï¿½
			service.saveApproval(param, listFile);
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), ZRQSEQ, ZSDT0161.cistat.TF.toString(), paramVO.getVariable("G_USER_ID"));
			
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
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="delete")
	public View Ses0401DeleteView(MipParameterVO paramVO, Model model) {
		
		logger.debug("@@@ Ses0401DeleteView START");		
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		
		// get Variable
			
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UIï¿½ï¿½ returnï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Þ¼ï¿½ï¿½ï¿½ dataset
			
		// DAO ï¿½ï¿½ï¿½ï¿½
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAOï¿½ï¿½ï¿½ï¿½
				
		try {
			Ses0401 param = new Ses0401();
			
			param.setMANDT(DatasetUtility.getString(dsHeader, 0, "MANDT"   )); 
			if (param.getMANDT() == null) param.setMANDT("");
			param.setZRQSEQ(DatasetUtility.getString(dsHeader, 0, "ZRQSEQ" )); 
			if (param.getZRQSEQ() == null) param.setZRQSEQ("");
						
			service.deleteHeader(param);
			service.deleteFile2(param);
						
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsHeader);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		return view;
	}
	@RequestMapping(value="findPrint")
	public View Ses0401FindPrintView(MipParameterVO paramVO, Model model) {

		Dataset dsPrint    = paramVO.getDataset("ds_print");					// UIï¿½ï¿½ returnï¿½ï¿½ out dataset
		Dataset dsTemplate = paramVO.getDataset("ds_template");					// UIï¿½ï¿½ returnï¿½ï¿½ out dataset
		Dataset dsError    = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Ses0401ParamVO param = new Ses0401ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAOï¿½ï¿½ï¿½ï¿½

			param.setMANDT(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setZRQSEQ(paramVO.getVariable("P_ZRQSEQ"));
			param.setGUBUN (paramVO.getVariable("P_GUBUN"));

			List<Ses0401> list = service.selectPrintHeader(param);   // ï¿½ï¿½ï¿½ï¿½CALL

			dsPrint.deleteAll();
			CallSAPHdel.moveListToDs(dsPrint, Ses0401.class, list);  // DATASET ï¿½ï¿½ï¿½ï¿½
			resultVO.addDataset(dsPrint);

			if ( list != null && list.size() > 0 )	{
				if ("Q".equals(param.getGUBUN()))	{
					List<Ses0401> listTemplate = service.selectPrintTemplate(param); 
	
					dsTemplate.deleteAll();
					CallSAPHdel.moveListToDs(dsTemplate, Ses0401.class, listTemplate);  // DATASET ï¿½ï¿½ï¿½ï¿½
					resultVO.addDataset(dsTemplate);
				}else	{
					// RFC INPUT PARAM DECLARE 
					ZSDT0094[] list_ZSDT0094 = new ZSDT0094[]{};  // sap output list
					ZQMSEMSG[] listMsg  	 = new ZQMSEMSG[]{};

					String sVBELN = StringUtils.leftPad(list.get(0).getQTSO_NO(), 10, "0");
					
					// WFC INPUT SET
					Object obj[] = new Object[]{ 
							  sVBELN
							, list.get(0).getHOGI()
							, list.get(0).getPOSNR()
							, listMsg
							, list_ZSDT0094
					}; 
		
					// OUTPUT DATASET DECLARE
					Dataset ds_output_ZSDT0094 = null;

					// RFC CALL
					SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT") 	// ï¿½ï¿½ï¿½Ç±ï¿½ï¿½ï¿½
										, "hdel.sd.sso.domain.ZWEB_SD_HOGI_SPEC2"			
										, obj, false);	
		
					ds_output_ZSDT0094 = CallSAP.isJCO() ? new Dataset() : ZSDT0094.getDataset();
		
					listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
					if ( "4".equals(stub.getOutput("E_TYPE").toString()) ) {	// ï¿½ï¿½ï¿½ï¿½
						dsError = CallSAP.makeErrorInfo(listMsg);
					}else	{
						CallSAP.moveObj2Ds(ds_output_ZSDT0094, stub.getOutput("S_ITAB"));
		
						dsTemplate.deleteAll();
		
						for (int i = 0; i < ds_output_ZSDT0094.getRowCount(); i++)	{
							dsTemplate.appendRow();
		
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
	public View Ses0401OutsourcingView(MipParameterVO paramVO, Model model) {
		ZSDT1084 zsdt1084;
		
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		Dataset dsFile2  = paramVO.getDataset("ds_file_2");
		
		String sysid = paramVO.getVariable("G_SYSID");
		String uuser = paramVO.getVariable("G_USER_ID");
		String outgbn = paramVO.getVariable("outgbn");
		String cistat = paramVO.getVariable("cistat");
		Lifnr lifnr = new Lifnr(paramVO.getVariable("lifnr"));
		
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		service.createDao(sqlSession.getSqlSessionBySysid(sysid));
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(sysid));
		zsdt0198s.createDao(sqlSession.getSqlSessionBySysid(sysid));
		
		try {
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String cadReqType = DatasetUtility.getString(dsHeader, 0, "CADREQTYPE"  ); if (cadReqType  == null) cadReqType   = "";
			
			zsdt1084 = service.searchLastValidChgContract(MANDT, dsHeader.getColumnAsString(0, "QTSO_NO"), dsHeader.getColumnAsString(0, "QTSER"));
			if(zsdt1084 != null) {
				zsdt1084.setMandt(MANDT);
				zsdt1084.setZrqseq(dsHeader.getColumnAsString(0, "ZRQSEQ"));
				
				zsdt1084s.createDao(sqlSession.getSqlSessionBySysid(sysid));
				zsdt1084s.merge(zsdt1084);
			}
			Ses0401 param = new Ses0401();
			
			param.setMANDT(MANDT);
			param.setZRQSEQ(ZRQSEQ);
			param.setOUTGBN(outgbn);
			param.setCADREQTYPE(cadReqType);
			param.setUUSER(uuser);

			service.saveOutdate(param);
			zsdt0161s.insertCistat(param.getMANDT(), ZRQSEQ, cistat, uuser);
			if(ZSDT0161.cistat.TR.toString().equals(cistat)) {
				zsdt0198s.insertWhenNotExists(param.getMANDT(), dsHeader.getColumnAsString(0, "QTSO_NO"), lifnr, uuser);
			}
			resultVO.addDataset(dsHeader);
			
			//µµ¸é¿ÜÁÖ Ã·ºÎÆÄÀÏ ÀúÀå
			List<Ses0401> listFile = new ArrayList<Ses0401>();
			
			for ( int i = 0 ; i < dsFile2.getRowCount() ; i++ ) {
				String fMANDT    = DatasetUtility.getString(dsFile2, i, "MANDT"   ); if (fMANDT    == null) fMANDT    = "";
				String fZRQSEQ   = DatasetUtility.getString(dsFile2, i, "ZRQSEQ"  ); if (fZRQSEQ   == null) fZRQSEQ   = "";
				String fZATTSEQ  = DatasetUtility.getString(dsFile2, i, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";
				String fZRQSTAT  = DatasetUtility.getString(dsFile2, i, "ZRQSTAT" ); if (fZRQSTAT  == null) fZRQSTAT  = "";
				String fZATGBN   = DatasetUtility.getString(dsFile2, i, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
				String fZATTPATH = DatasetUtility.getString(dsFile2, i, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
				String fZATTFN   = DatasetUtility.getString(dsFile2, i, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
				String fFLAG     = DatasetUtility.getString(dsFile2, i, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
				String fFILEGBN     = DatasetUtility.getString(dsFile2, i, "FILEGBN"    ); if (fFILEGBN     == null) fFILEGBN     = "";
				String fUUSER    = paramVO.getVariable("G_USER_ID");
				
				Ses0401 paramFile = new Ses0401();
				
				paramFile.setMANDT   (fMANDT   );
				paramFile.setZRQSEQ  (fZRQSEQ  );
				paramFile.setZATTSEQ (fZATTSEQ );
				paramFile.setZRQSTAT (fZRQSTAT );
				paramFile.setZATGBN  (fZATGBN  );
				paramFile.setZATTPATH(fZATTPATH);
				paramFile.setZATTFN  (fZATTFN  );
				paramFile.setFLAG    (fFLAG    );
				paramFile.setFILEGBN(fFILEGBN);
				paramFile.setUUSER   (fUUSER   );
				
				listFile.add(paramFile);
			}
			
			service.saveDsFile2(listFile);
			
			resultVO.addDataset(dsFile2);
					
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
	public View Ses0401OutcancelView(MipParameterVO paramVO, Model model) {
				
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		
		// get Variable
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		String outgbn = paramVO.getVariable("outgbn");
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String UUSER    = uuser;
			
			Ses0401 param = new Ses0401();
			
			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setZRQSEQ  (ZRQSEQ  );
			param.setOUTGBN(outgbn);
			param.setUUSER   (UUSER   );

			service.saveOutcandt(param);
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), ZRQSEQ, ZSDT0161.cistat.TC.toString(), paramVO.getVariable("G_USER_ID"));
			
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
	public View Ses0401OutfinishView(MipParameterVO paramVO, Model model) {
				
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		
		// get Variable
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String OUTGRD   = DatasetUtility.getString(dsHeader, 0, "OUTGRD"  ); if (OUTGRD  == null) OUTGRD   = "";
			String OUTLOQ   = DatasetUtility.getString(dsHeader, 0, "OUTLOQ"  ); if (OUTLOQ  == null) OUTLOQ   = "";
			String outman = DatasetUtility.getString(dsHeader, 0, "OUTMAN"  ); if (outman  == null) outman   = "";
			String UUSER    = uuser;
			
			Ses0401 param = new Ses0401();
			
			param.setMANDT(MANDT);
			param.setZRQSEQ(ZRQSEQ);
			param.setOUTGRD(OUTGRD);
			param.setOUTLOQ(OUTLOQ);
			param.setOUTMAN(outman);
			param.setUUSER(UUSER);

			// ï¿½ï¿½ï¿½ï¿½
			service.saveOutfinish(param);
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), ZRQSEQ, ZSDT0161.cistat.EF.toString(), paramVO.getVariable("G_USER_ID"));
			
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

	@RequestMapping(value="outaccept")
	public View Ses0401OutacceptView(MipParameterVO paramVO, Model model) {
				
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");	// UIï¿½ï¿½ returnï¿½ï¿½ out dataset
		
		// get Variable
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String outgrd   = DatasetUtility.getString(dsHeader, 0, "OUTGRD"  ); if (ZRQSEQ  == null) outgrd   = "";
			String outloq   = DatasetUtility.getString(dsHeader, 0, "OUTLOQ"  ); if (ZRQSEQ  == null) outloq   = "";
			String OUTMAN   = DatasetUtility.getString(dsHeader, 0, "OUTMAN"  ); if (OUTMAN  == null) OUTMAN   = "";
			String UUSER    = uuser;
			
			Ses0401 param = new Ses0401();
			
			param.setMANDT   (MANDT   ); // SAP CLIENT
			param.setZRQSEQ  (ZRQSEQ  );
			param.setOUTGRD(outgrd);
			param.setOUTLOQ(outloq);
			param.setOUTMAN   (OUTMAN   );
			param.setUUSER   (UUSER   );

			service.saveOutactdt(param);
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), ZRQSEQ, ZSDT0161.cistat.EA.toString(), paramVO.getVariable("G_USER_ID"));

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

	@RequestMapping(value="outreturn")
	public View Ses0401OutreturnView(MipParameterVO paramVO, Model model) {
				
		// get Input Dataset
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		
		// get Variable
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO(); 
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			String MANDT    = DatasetUtility.getString(dsHeader, 0, "MANDT"   ); if (MANDT   == null) MANDT    = "";
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String UUSER    = uuser;
			
			Ses0401 param = new Ses0401();
			
			param.setMANDT(MANDT); // SAP CLIENT
			param.setZRQSEQ(ZRQSEQ);
			param.setUUSER(UUSER);

			service.saveOutretdt(param);
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), ZRQSEQ, ZSDT0161.cistat.EB.toString(), paramVO.getVariable("G_USER_ID"));
			
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
	
	@RequestMapping(value="savez3all")
	public View Ses0401Savez3allView(MipParameterVO paramVO, Model model) {
				
		// get Input Dataset
		Dataset dsList  = paramVO.getDataset("ds_list");
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO(); 
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try { 
			for ( int i = 0; i < dsList.getRowCount() ; i++){
			
				String MANDT     = mandt;
				String ZRQSEQ    = DatasetUtility.getString(dsList, i, "ZRQSEQ"  );    if (ZRQSEQ     == null) ZRQSEQ    = "";
				String ZKUNNR_Z3 = DatasetUtility.getString(dsList, i, "ZKUNNR_Z3"  ); if (ZKUNNR_Z3  == null) ZKUNNR_Z3 = "";
				String UUSER     = uuser;
			
				Ses0401 param = new Ses0401();
			
				param.setMANDT     (MANDT   ); // SAP CLIENT
				param.setZRQSEQ    (ZRQSEQ  );
				param.setZKUNNR_Z3 (ZKUNNR_Z3  );
				param.setUUSER   (UUSER   );
			
				service.saveZ3all(param);			
				resultVO.addDataset(dsList);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}		
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value="confirmDrawing")
	public View confirmDrawing(MipParameterVO paramVO, Model model) {
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			dsHeader.setColumn(0, "CISTAT", ZSDT0161.cistat.TO.toString());
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), dsHeader.getColumnAsString(0, "ZRQSEQ"), dsHeader.getColumnAsString(0, "CISTAT"), paramVO.getVariable("G_USER_ID"));
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

	@RequestMapping(value="rejectDrawing")
	public View rejectDrawing(MipParameterVO paramVO, Model model) {
		Dataset dsHeader  = paramVO.getDataset("ds_header");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset ds1079	= paramVO.getDataset("ds_zsdt1079");
		Dataset dsFile2  = paramVO.getDataset("ds_file_2");
		
		MipResultVO resultVO = new MipResultVO();
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			dsHeader.setColumn(0, "CISTAT", ZSDT0161.cistat.TK.toString());
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), dsHeader.getColumnAsString(0, "ZRQSEQ"), dsHeader.getColumnAsString(0, "CISTAT"), paramVO.getVariable("G_USER_ID"));
			resultVO.addDataset(dsHeader);

			if(ds1079 != null) {
				List<ZSDT1079> ls1079 = new ArrayList<ZSDT1079>();
				for(int i=0; i<ds1079.getRowCount(); i++) {
					ZSDT1079 zsdt1079 = new ZSDT1079();
					zsdt1079.setMandt(paramVO.getVariable("G_MANDT"));
					zsdt1079.setZrqseq(dsHeader.getColumnAsString(0, "ZRQSEQ"));
					zsdt1079.setChgchrpt(DatasetUtility.getString(ds1079, i, "CHGCHRPT"));
					ls1079.add(zsdt1079);
				}
				
				service.deleteNInsert1079(paramVO.getVariable("G_MANDT"), dsHeader.getColumnAsString(0, "ZRQSEQ"), ls1079);
				service.UpdateErrortext(paramVO.getVariable("G_MANDT"), dsHeader.getColumnAsString(0, "ZRQSEQ"), dsHeader.getColumnAsString(0, "ERROR_TEXT"));
				
			}
			//µµ¸é¿ÜÁÖ Ã·ºÎÆÄÀÏ ÀúÀå(µµ¸é¹Ý·Á ½Ã)
			List<Ses0401> listFile = new ArrayList<Ses0401>();
			
			for ( int i = 0 ; i < dsFile2.getRowCount() ; i++ ) {
				String fMANDT    = DatasetUtility.getString(dsFile2, i, "MANDT"   ); if (fMANDT    == null) fMANDT    = "";
				String fZRQSEQ   = DatasetUtility.getString(dsFile2, i, "ZRQSEQ"  ); if (fZRQSEQ   == null) fZRQSEQ   = "";
				String fZATTSEQ  = DatasetUtility.getString(dsFile2, i, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";
				String fZRQSTAT  = DatasetUtility.getString(dsFile2, i, "ZRQSTAT" ); if (fZRQSTAT  == null) fZRQSTAT  = "";
				String fZATGBN   = DatasetUtility.getString(dsFile2, i, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
				String fZATTPATH = DatasetUtility.getString(dsFile2, i, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
				String fZATTFN   = DatasetUtility.getString(dsFile2, i, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
				String fFLAG     = DatasetUtility.getString(dsFile2, i, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
				String fFILEGBN     = DatasetUtility.getString(dsFile2, i, "FILEGBN"    ); if (fFILEGBN     == null) fFILEGBN     = "";
				String fUUSER    = paramVO.getVariable("G_USER_ID");
				
				Ses0401 paramFile = new Ses0401();
				
				paramFile.setMANDT   (fMANDT   );
				paramFile.setZRQSEQ  (fZRQSEQ  );
				paramFile.setZATTSEQ (fZATTSEQ );
				paramFile.setZRQSTAT (fZRQSTAT );
				paramFile.setZATGBN  (fZATGBN  );
				paramFile.setZATTPATH(fZATTPATH);
				paramFile.setZATTFN  (fZATTFN  );
				paramFile.setFLAG    (fFLAG    );
				paramFile.setFILEGBN(fFILEGBN);
				paramFile.setUUSER   (fUUSER   );
				
				listFile.add(paramFile);
			}
			
			service.saveDsFile2(listFile);
			
			resultVO.addDataset(dsFile2);
			
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	@RequestMapping(value="findUsercode")
	public View findUsercode(MipParameterVO paramVO, Model model) {
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_usercode");
		
		// OUTPUPT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_list_usercode");
		
		try {
			Ses0401ParamVO param = new Ses0401ParamVO();
			
			// DAO»ý¼º
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			String smanCd = DatasetUtility.getString(dsCond, "SMAN_CD");
			
			// parameter set
			param.setMANDT(paramVO.getVariable("G_MANDT")); // SAP Client
			param.setSMANCD(smanCd); // »ç¹ø
			
			// ¼­ºñ½º CALL
			List<Ses0401> list = service.findUsercode(param);
			
			// È£Ãâ°á°ú(list)¸¦ µ¥ÀÌÅÍ¼Â(dsList)¿¡ º¹»ç
			if (!list.isEmpty()) {
				dsList.appendRow();
				dsList.setColumn(0, "USERCODE", list.get(0).getUSERCODE());
			}
			
			// RFC Ãâ·Â datasetÀ» return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
}
