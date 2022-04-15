package hdel.sd.ses.control;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZBCS_TIMESTAMP;
import hdel.lib.domain.ZSDT0161;
import hdel.lib.domain.ZSDT1081;
import hdel.lib.service.ZSDT0161S;
import hdel.lib.service.ZSDT1081S;
import hdel.sd.ses.domain.Ses0412;
import hdel.sd.ses.domain.Ses0412ParamVO;
import hdel.sd.ses.domain.Ses0414;
import hdel.sd.ses.service.Ses0412S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0412")
public class Ses0412C {
	private enum queryType {
		I, U, D
	};

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Ses0412S service;
	@Autowired
	private ZSDT0161S zsdt0161s;

	@Autowired
	private ZSDT1081S zsdt1081Service;

	/**
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "find")
	public View Ses0412FindView(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Ses0412FindView START");

		// get Input Dataset
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsFile = paramVO.getDataset("ds_file");

		String mandt = paramVO.getVariable("G_MANDT");

		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		MipResultVO resultVO = new MipResultVO();

		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			Ses0412ParamVO param = new Ses0412ParamVO();
			param.setMANDT(DatasetUtility.getString(dsCond, "MANDT"));
			param.setZRQSEQ(DatasetUtility.getString(dsCond, "ZRQSEQ"));
			param.setZATGBN(DatasetUtility.getString(dsCond, "ZATGBN"));
			param.setUUSER(paramVO.getVariable("G_USER_ID"));

			dsHeader.deleteAll();
			List<Ses0412> list = service.selectListHeader(param);
			CallSAPHdel.moveListToDs(dsHeader, Ses0412.class, list);

			resultVO.addDataset(dsHeader);

			dsFile.deleteAll();
			List<Ses0412> listFile = service.selectListFile(param);
			CallSAPHdel.moveListToDs(dsFile, Ses0412.class, listFile);

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
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save")
	public View Ses0412SaveView(MipParameterVO paramVO, Model model) {
		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsFile = paramVO.getDataset("ds_file");
		String mandt = paramVO.getVariable("G_MANDT");
		String uuser = paramVO.getVariable("G_USER_ID");
		String gvbcd = paramVO.getVariable("G_SAP_USER_VGCD");
		if (gvbcd.equals(""))
			gvbcd = "XXX";

		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		try {
			String MANDT = DatasetUtility.getString(dsHeader, 0, "MANDT");
			if (MANDT == null)
				MANDT = "";
			String ZRQSEQ = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ");
			if (ZRQSEQ == null)
				ZRQSEQ = "";
			String ZRQFLG = DatasetUtility.getString(dsHeader, 0, "ZRQFLG");
			if (ZRQFLG == null)
				ZRQFLG = "";
			String ZRQDAT = DatasetUtility.getString(dsHeader, 0, "ZRQDAT");
			if (ZRQDAT == null)
				ZRQDAT = "";
			String ZRQCN = DatasetUtility.getString(dsHeader, 0, "ZRQCN");
			if (ZRQCN == null)
				ZRQCN = "";
			String ZRQCN2 = DatasetUtility.getString(dsHeader, 0, "ZRQCN2");
			if (ZRQCN2 == null)
				ZRQCN2 = "";
			String ZRQCN3 = DatasetUtility.getString(dsHeader, 0, "ZRQCN3");
			if (ZRQCN3 == null)
				ZRQCN3 = "";
			String ZRQCN4 = DatasetUtility.getString(dsHeader, 0, "ZRQCN4");
			if (ZRQCN4 == null)
				ZRQCN4 = "";
			String ZRQCN5 = DatasetUtility.getString(dsHeader, 0, "ZRQCN5");
			if (ZRQCN5 == null)
				ZRQCN5 = "";
			String ZRQCN6 = DatasetUtility.getString(dsHeader, 0, "ZRQCN6");
			if (ZRQCN6 == null)
				ZRQCN6 = "";
			String ZRQCN7 = DatasetUtility.getString(dsHeader, 0, "ZRQCN7");
			if (ZRQCN7 == null)
				ZRQCN7 = "";
			String ZRQCN8 = DatasetUtility.getString(dsHeader, 0, "ZRQCN8");
			if (ZRQCN8 == null)
				ZRQCN8 = "";
			String ZRQCN9 = DatasetUtility.getString(dsHeader, 0, "ZRQCN9");
			if (ZRQCN9 == null)
				ZRQCN9 = "";
			String ZRQCN10 = DatasetUtility.getString(dsHeader, 0, "ZRQCN10");
			if (ZRQCN10 == null)
				ZRQCN10 = "";
			String ZRQSTAT = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT");
			if (ZRQSTAT == null)
				ZRQSTAT = "";
			String ZRSRLT = DatasetUtility.getString(dsHeader, 0, "ZRSRLT");
			if (ZRSRLT == null)
				ZRSRLT = "";
			String KUNNR_Z2 = DatasetUtility.getString(dsHeader, 0, "KUNNR_Z2");
			if (KUNNR_Z2 == null)
				KUNNR_Z2 = "";
			String KUNNR_Z3 = DatasetUtility.getString(dsHeader, 0, "KUNNR_Z3");
			if (KUNNR_Z3 == null)
				KUNNR_Z3 = "";
			String QTSO_NO = DatasetUtility.getString(dsHeader, 0, "QTSO_NO");
			if (QTSO_NO == null)
				QTSO_NO = "";
			String QTSER = DatasetUtility.getString(dsHeader, 0, "QTSER");
			if (QTSER == null)
				QTSER = "";
			String QTSEQ = DatasetUtility.getString(dsHeader, 0, "QTSEQ");
			if (QTSEQ == null)
				QTSEQ = "";
			String FINDAT = DatasetUtility.getString(dsHeader, 0, "FINDAT");
			if (FINDAT == null)
				FINDAT = "";
			String REQDAT = DatasetUtility.getString(dsHeader, 0, "REQDAT");
			if (REQDAT == null)
				REQDAT = "";
			String ZBDTYP = DatasetUtility.getString(dsHeader, 0, "ZBDTYP");
			if (ZBDTYP == null)
				ZBDTYP = "";
			String VKGRP = DatasetUtility.getString(dsHeader, 0, "VKGRP");
			if (VKGRP == null)
				VKGRP = "";
			String ZPID = DatasetUtility.getString(dsHeader, 0, "ZPID");
			if (ZPID == null)
				ZPID = "";
			String ZTEL = DatasetUtility.getString(dsHeader, 0, "ZTEL");
			if (ZTEL == null)
				ZTEL = "";
			String SPDOCU = DatasetUtility.getString(dsHeader, 0, "SPDOCU");
			if (SPDOCU == null)
				SPDOCU = "";
			String DRW_IS = DatasetUtility.getString(dsHeader, 0, "DRW_IS");
			if (DRW_IS == null)
				DRW_IS = "";
			String CODAT = DatasetUtility.getString(dsHeader, 0, "CODAT");
			if (CODAT == null)
				CODAT = "";
			String UUSER = uuser;
			String GVGCD = gvbcd;

			Ses0412 param = new Ses0412();

			param.setMANDT(MANDT);
			param.setZRQSEQ(ZRQSEQ);
			param.setZRQFLG(ZRQFLG);
			param.setZRQDAT(ZRQDAT); 
			param.setZRQCN(ZRQCN); 
			param.setZRQCN2(ZRQCN2);
			param.setZRQCN3(ZRQCN3);
			param.setZRQCN4(ZRQCN4);
			param.setZRQCN5(ZRQCN5);
			param.setZRQCN6(ZRQCN6);
			param.setZRQCN7(ZRQCN7);
			param.setZRQCN8(ZRQCN8);
			param.setZRQCN9(ZRQCN9);
			param.setZRQCN10(ZRQCN10);
			param.setZRQSTAT(ZRQSTAT);
			param.setZRSRLT(ZRSRLT);
			param.setKUNNR_Z2(KUNNR_Z2);
			param.setKUNNR_Z3(KUNNR_Z3);
			param.setQTSO_NO(QTSO_NO);
			param.setQTSER(QTSER);
			param.setQTSEQ(QTSEQ);
			param.setFINDAT(FINDAT);
			param.setREQDAT(REQDAT);
			param.setZBDTYP(ZBDTYP);
			param.setZBDTYP(VKGRP); 
			param.setUUSER(UUSER);
			param.setGVGCD(GVGCD);
			param.setZPID(ZPID);
			param.setZTEL(ZTEL);
			param.setSPDOCU(SPDOCU);
			param.setDRW_IS(DRW_IS);
			param.setCODAT(CODAT);

			List<Ses0412> listFile = new ArrayList<Ses0412>();

			for (int i = 0; i < dsFile.getRowCount(); i++) {
				String fMANDT = DatasetUtility.getString(dsFile, i, "MANDT");
				if (fMANDT == null)
					fMANDT = "";
				String fZRQSEQ = DatasetUtility.getString(dsFile, i, "ZRQSEQ");
				if (fZRQSEQ == null)
					fZRQSEQ = "";
				String fZATTSEQ = DatasetUtility.getString(dsFile, i, "ZATTSEQ");
				if (fZATTSEQ == null)
					fZATTSEQ = "";
				String fZRQSTAT = DatasetUtility.getString(dsFile, i, "ZRQSTAT");
				if (fZRQSTAT == null)
					fZRQSTAT = "";
				String fZATGBN = DatasetUtility.getString(dsFile, i, "ZATGBN");
				if (fZATGBN == null)
					fZATGBN = "";
				String fZATTPATH = DatasetUtility.getString(dsFile, i, "ZATTPATH");
				if (fZATTPATH == null)
					fZATTPATH = "";
				String fZATTFN = DatasetUtility.getString(dsFile, i, "ZATTFN");
				if (fZATTFN == null)
					fZATTFN = "";
				String fFLAG = DatasetUtility.getString(dsFile, i, "FLAG");
				if (fFLAG == null)
					fFLAG = "";
				String fFILEGBN = DatasetUtility.getString(dsFile, i, "FILEGBN");
				if (fFILEGBN == null)
					fFILEGBN = "";
				String fUUSER = uuser;

				Ses0412 paramFile = new Ses0412();

				paramFile.setMANDT(fMANDT);
				paramFile.setZRQSEQ(fZRQSEQ);
				paramFile.setZATTSEQ(fZATTSEQ);
				paramFile.setZRQSTAT(fZRQSTAT);
				paramFile.setZATGBN(fZATGBN);
				paramFile.setZATTPATH(fZATTPATH);
				paramFile.setZATTFN(fZATTFN);
				paramFile.setFLAG(fFLAG);
				paramFile.setFILEGBN(fFILEGBN);
				paramFile.setUUSER(fUUSER);

				listFile.add(paramFile);
			}

			String strNewZrqSeq = service.save(param, listFile);
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), strNewZrqSeq, ZSDT0161.cistat.SR.toString(), paramVO.getVariable("G_USER_ID"));
			dsHeader.setColumn(0, "ZRQSEQ", strNewZrqSeq);

			resultVO.addDataset(dsHeader);
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
	 * 
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveZrqstat")
	public View Ses0411SaveZrqstat(MipParameterVO paramVO, Model model) {

		logger.debug("@@@ Ses0411SaveReceipt START");

		Dataset dsHeader = paramVO.getDataset("ds_header");

		String mandt = paramVO.getVariable("G_MANDT");
		String uuser = paramVO.getVariable("G_USER_ID");
		String cistat = paramVO.getVariable("cistat");

		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		zsdt0161s.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		try {
			String MANDT = DatasetUtility.getString(dsHeader, 0, "MANDT");
			if (MANDT == null)
				MANDT = "";
			String ZRQSEQ = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ");
			if (ZRQSEQ == null)
				ZRQSEQ = "";
			String ZRQSTAT = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT");
			if (ZRQSTAT == null)
				ZRQSTAT = "";
			String KUNNR_Z3 = DatasetUtility.getString(dsHeader, 0, "KUNNR_Z3");
			if (KUNNR_Z3 == null)
				KUNNR_Z3 = "";
			String ZRSRLT = DatasetUtility.getString(dsHeader, 0, "ZRSRLT");
			if (ZRSRLT == null)
				ZRSRLT = "";
			String UUSER = uuser;

			Ses0412 param = new Ses0412();

			param.setMANDT(MANDT);
			param.setZRQSEQ(ZRQSEQ);
			param.setZRQSTAT(ZRQSTAT);
			param.setKUNNR_Z3(KUNNR_Z3);
			param.setZRSRLT(ZRSRLT);
			param.setUUSER(UUSER);

			service.saveZrqstat(param);

			if(cistat != null) {
				switch(ZSDT0161.cistat.valueOf(cistat)) {
					case TB:
					case TA:
						zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), ZRQSEQ, cistat, paramVO.getVariable("G_USER_ID"));
						break;
				}
			}

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

	/**
	 *
	 * 
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveApproval")
	public View Ses0412SaveApprovalView(MipParameterVO paramVO, Model model) {
		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsFile = paramVO.getDataset("ds_file");
		String mandt = paramVO.getVariable("G_MANDT");
		String uuser = paramVO.getVariable("G_USER_ID");

		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		try {
			String MANDT = DatasetUtility.getString(dsHeader, 0, "MANDT");
			if (MANDT == null)
				MANDT = "";
			String ZRQSEQ = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ");
			if (ZRQSEQ == null)
				ZRQSEQ = "";
			String ZRQSTAT = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT");
			if (ZRQSTAT == null)
				ZRQSTAT = "";
			String ZRSRLT = DatasetUtility.getString(dsHeader, 0, "ZRSRLT");
			if (ZRSRLT == null)
				ZRSRLT = "";
			String UUSER = uuser;

			Ses0412 param = new Ses0412();

			param.setMANDT(MANDT);
			param.setZRQSEQ(ZRQSEQ);
			param.setZRQSTAT(ZRQSTAT);
			param.setZRSRLT(ZRSRLT);
			param.setUUSER(UUSER);

			List<Ses0412> listFile = new ArrayList<Ses0412>();

			for (int i = 0; i < dsFile.getRowCount(); i++) {
				String fMANDT = DatasetUtility.getString(dsFile, i, "MANDT");
				if (fMANDT == null)
					fMANDT = "";
				String fZRQSEQ = DatasetUtility.getString(dsFile, i, "ZRQSEQ");
				if (fZRQSEQ == null)
					fZRQSEQ = "";
				String fZATTSEQ = DatasetUtility.getString(dsFile, i, "ZATTSEQ");
				if (fZATTSEQ == null)
					fZATTSEQ = "";
				String fZRQSTAT = DatasetUtility.getString(dsFile, i, "ZRQSTAT");
				if (fZRQSTAT == null)
					fZRQSTAT = "";
				String fZATGBN = DatasetUtility.getString(dsFile, i, "ZATGBN");
				if (fZATGBN == null)
					fZATGBN = "";
				String fZATTPATH = DatasetUtility.getString(dsFile, i, "ZATTPATH");
				if (fZATTPATH == null)
					fZATTPATH = "";
				String fZATTFN = DatasetUtility.getString(dsFile, i, "ZATTFN");
				if (fZATTFN == null)
					fZATTFN = "";
				String fFLAG = DatasetUtility.getString(dsFile, i, "FLAG");
				if (fFLAG == null)
					fFLAG = "";
				String fFILEGBN = DatasetUtility.getString(dsFile, i, "FILEGBN");
				if (fFILEGBN == null)
					fFILEGBN = "";

				String fUUSER = uuser;

				Ses0412 paramFile = new Ses0412();

				paramFile.setMANDT(fMANDT);
				paramFile.setZRQSEQ(fZRQSEQ);
				paramFile.setZATTSEQ(fZATTSEQ);
				paramFile.setZRQSTAT(fZRQSTAT);
				paramFile.setZATGBN(fZATGBN);
				paramFile.setZATTPATH(fZATTPATH);
				paramFile.setZATTFN(fZATTFN);
				paramFile.setFLAG(fFLAG);
				paramFile.setFILEGBN(fFILEGBN);
				paramFile.setUUSER(fUUSER);

				listFile.add(paramFile);
			}

			service.saveApproval(param, listFile);
			zsdt0161s.insertCistat(paramVO.getVariable("G_MANDT"), ZRQSEQ, ZSDT0161.cistat.TF.toString(), paramVO.getVariable("G_USER_ID"));

			resultVO.addDataset(dsHeader);
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
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectRepPm")
	public View selectRepPm(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		try {
			List<Ses0414> pmList = service.selectRepPm(paramVO.getVariable("G_MANDT"), DatasetUtility.getString(dsCond, "TEMNO"), DatasetUtility.getString(dsCond, "VKGRP"));

			dsList.deleteAll();
			CallSAPHdel.moveListToDs(dsList, Ses0414.class, pmList);
			resultVO.addDataset(dsList);

		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);

		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value = "searchPsPm")
	public View searchPsPm(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		try {
			List<Ses0414> pmList = service.searchPsPm(paramVO.getVariable("G_MANDT"), DatasetUtility.getString(dsCond, "TEMNO"));

			dsList.deleteAll();
			CallSAPHdel.moveListToDs(dsList, Ses0414.class, pmList);
			if(dsList.getRowCount() > 0)
				dsList.setColumn(0, "TEMNONM", pmList.get(0).getTEMNONM());
			resultVO.addDataset(dsList);

		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);

		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value = "searchRepPmByTeam")
	public View searchRepPmByTeam(MipParameterVO paramVO, Model model) {
		Dataset dsList = paramVO.getDataset("ds_list");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		try {
			List<Ses0414> pmList = service.searchRepPmByTeam(paramVO.getVariable("G_MANDT"), paramVO.getVariable("VKGRP"));

			dsList.deleteAll();
			CallSAPHdel.moveListToDs(dsList, Ses0414.class, pmList);
			if(dsList.getRowCount() > 0)
				dsList.setColumn(0, "TEMNONM", pmList.get(0).getTEMNONM());
			resultVO.addDataset(dsList);

		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);

		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value = "saveRepPm")
	public View saveRepPm(MipParameterVO paramVO, Model model) {
		Dataset dsList = paramVO.getDataset("ds_list");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		zsdt1081Service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

		try {
			ZBCS_TIMESTAMP tstmp = new ZBCS_TIMESTAMP();
			List<ZSDT1081> ls1081Delete = new ArrayList<ZSDT1081>();
			List<ZSDT1081> ls1081Insert = new ArrayList<ZSDT1081>();
			List<ZSDT1081> ls1081Update = new ArrayList<ZSDT1081>();
			for (int i = 0; i < dsList.getRowCount(); i++) {
				ZSDT1081 zsdt1081 = new ZSDT1081();
				zsdt1081.setMANDT(paramVO.getVariable("G_MANDT"));
				zsdt1081.setTEMNO(DatasetUtility.getString(dsList, i, "TEMNO"));
				zsdt1081.setVKGRP(DatasetUtility.getString(dsList, i, "VKGRP"));
				zsdt1081.getTstmp().setTimeStamp(/*DatasetUtility.getString(dsList, i, "CHGSTS").equals("I"),*/
						paramVO.getVariable("G_USER_ID"));
				;
				switch (queryType.valueOf(DatasetUtility.getString(dsList, i, "CHGSTS"))) {
				case I:
					ls1081Insert.add(zsdt1081);
					break;
				case U:
					ls1081Update.add(zsdt1081);
					break;
				case D:
					ls1081Delete.add(zsdt1081);
					break;
				default:
				}
			}

			zsdt1081Service.delete(ls1081Delete);
			for(ZSDT1081 zsdt1081 : ls1081Update) {
				zsdt1081Service.update(zsdt1081);
			}
			zsdt1081Service.insert(ls1081Insert);
			resultVO.addDataset(dsList);

		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);

		model.addAttribute("resultVO", resultVO);
		return view;
	}
}
