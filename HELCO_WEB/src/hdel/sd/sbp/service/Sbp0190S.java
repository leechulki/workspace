package hdel.sd.sbp.service;


import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoSoNumberS;
import hdel.sd.sbp.dao.Sbp0190D;
import hdel.sd.sbp.domain.ComboParamVO;
import hdel.sd.sbp.domain.ComboVO;
import hdel.sd.sbp.domain.Sbp0190;
import hdel.sd.sbp.domain.Sbp0190ParamVO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

@Service
public class Sbp0190S extends SrmService {

	@Autowired
	private AutoSoNumberS autoSoService;
	
	private Sbp0190D sbp0190D;

	@Override
	public void createDao(SqlSession sqlSession) {
		sbp0190D = sqlSession.getMapper(Sbp0190D.class);
	}

	// 기종
	public List<ComboVO> selectGtype() {
		return sbp0190D.selectGtype();
	}

	// 설치지역
	public List<ComboVO> selectREGION(ComboParamVO param) {
		return sbp0190D.selectREGION(param);
	}

	// 수주가능성
	public List<ComboVO> selectDD07T(ComboParamVO param) {
		return sbp0190D.selectDD07T(param);
	}
	
	public List<Sbp0190> selectZSDT1005(Sbp0190ParamVO param) {
		return sbp0190D.selectZSDT1005(param);
	}
	
	public void save(MipParameterVO paramVO, Model model, SqlSession session) {
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");

		Sbp0190 param = new Sbp0190();
		AutoNumberParamVO sonnrParam = new AutoNumberParamVO();
		
		try {
			// DAO생성
			createDao(session);
			autoSoService.createDao(session);
			
			for (int i = 0; i < dsList.getRowCount(); i++) {
				if (DatasetUtility.getString(dsList, i, "FLAG") != null
						&& (DatasetUtility.getString(dsList, i, "FLAG").equals("U")
						|| DatasetUtility.getString(dsList, i, "FLAG").equals("I"))) {
					if (DatasetUtility.getInt(dsList, i, "ZMPFLG") == 1) {
						param.setZMPFLG("X");
					} else {
						if (DatasetUtility.getString(dsList, i, "ZMPFLG").equals("X")) {
							param.setZMPFLG("X");
						} else {
							param.setZMPFLG(" ");
						}
					}
					param.setMANDT(DatasetUtility.getString(dsList, i, "MANDT"));
					if (DatasetUtility.getString(dsList, i, "SONNR").equals("자동입력")) {
						sonnrParam.setDeptFlag(DatasetUtility.getString(dsList, i, "AUART"));
						sonnrParam.setSoQtFlag("1");
						sonnrParam.setMandt(paramVO.getVariable("G_MANDT"));
						List<AutoNumberVO> sonnrList = autoSoService.AutoSoNumber(sonnrParam);
						
						param.setSONNR(sonnrList.get(0).getNumber());
						param.setUDATE("00000000");
						param.setUTIME("000000");
						param.setUUSER(" ");
						param.setDDATE("00000000");
						param.setDTIME("000000");
						param.setDUSER(" ");
					} else {
						param.setSONNR(DatasetUtility.getString(dsList, i, "SONNR"));
						param.setUDATE(DateTime.getDateString());
						param.setUTIME(DateTime.getShortTimeString());
						param.setUUSER(paramVO.getVariable("G_USER_ID"));
					}
					param.setZPYM(DatasetUtility.getString(dsList, i, "ZPYM"));
					param.setSPART(DatasetUtility.getString(dsList, i, "SPART"));
					param.setAUART(DatasetUtility.getString(dsList, i, "AUART"));
					param.setMATNR(DatasetUtility.getString(dsList, i, "MATNR"));
					param.setVKBUR(DatasetUtility.getString(dsList, i, "VKBUR"));
					param.setVKGRP(DatasetUtility.getString(dsList, i, "VKGRP"));
					param.setZKUNNR(DatasetUtility.getString(dsList, i, "ZKUNNR"));
					param.setGTYPE(DatasetUtility.getString(dsList, i, "GTYPE"));
					param.setZNUMBER(new BigDecimal(DatasetUtility.getDouble(dsList, i, "ZNUMBER")));
					param.setKUNNR(DatasetUtility.getString(dsList, i, "KUNNR"));
					param.setGSNAM(DatasetUtility.getString(dsList, i, "GSNAM"));
					param.setREGION(DatasetUtility.getString(dsList, i, "REGION"));
					if (DatasetUtility.getInt(dsList, i, "ZDELI") == 1) {
						param.setZDELI("X");
					} else {
						param.setZDELI(" ");
					}
					param.setSOFOCA(new BigDecimal(DatasetUtility.getDouble(dsList, i, "SOFOCA")));
					param.setZFORE(new BigDecimal(DatasetUtility.getDouble(dsList, i, "ZFORE")));
					param.setWAERK(DatasetUtility.getString(dsList, i, "WAERK"));
					param.setPSPIDSV(DatasetUtility.getString(dsList, i, "PSPIDSV"));
					param.setZHOGISV(DatasetUtility.getString(dsList, i, "ZHOGISV"));
					param.setZRMK(DatasetUtility.getString(dsList, i, "ZRMK"));
					param.setSOABLE(DatasetUtility.getString(dsList, i, "SOABLE"));
					param.setSORLT(DatasetUtility.getString(dsList, i, "SORLT"));
					param.setSOPRICE(new BigDecimal(DatasetUtility.getDouble(dsList, i, "SOPRICE")));
					param.setSHANG(new BigDecimal(DatasetUtility.getDouble(dsList, i, "SHANG")));
					param.setZBPNNR(DatasetUtility.getString(dsList, i, "ZBPNNR"));
					param.setZAGNT(DatasetUtility.getString(dsList, i, "ZAGNT"));
					param.setCDATE(DateTime.getDateString());
					param.setCTIME(DateTime.getShortTimeString());
					param.setCUSER(paramVO.getVariable("G_USER_ID"));

					sbp0190D.mergeZSDT1005(param);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(MipParameterVO paramVO, Model model, SqlSession session) {
		// INPUT DATASET GET
		Dataset dsList = paramVO.getDatasetList().getDataset("ds_list");

		try {
			// DAO생성
			createDao(session);
			
			Sbp0190 param = new Sbp0190();
			
			for (int i = 0; i < dsList.getRowCount(); i++) {
				if (DatasetUtility.getString(dsList, i, "FLAG") != null
						&& DatasetUtility.getString(dsList, i, "FLAG").equals("D")) {
					param.setMANDT(paramVO.getVariable("G_MANDT"));
					param.setDDATE(DateTime.getDateString());
					param.setDTIME(DateTime.getShortTimeString());
					param.setDUSER(paramVO.getVariable("G_USER_ID"));
					param.setSONNR(DatasetUtility.getString(dsList, i, "SONNR"));

					sbp0190D.deleteZSDT1005(param);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
