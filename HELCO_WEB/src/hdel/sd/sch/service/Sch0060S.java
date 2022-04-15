package hdel.sd.sch.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.sch.dao.Sch0060D;
import hdel.sd.sch.domain.ComboParamVO;
import hdel.sd.sch.domain.ComboVO;
import hdel.sd.sch.domain.Sch0060;
import hdel.sd.sch.domain.Sch0060ParamVO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

@Service
public class Sch0060S extends SrmService {

	private Sch0060D sch0060D;

	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		sch0060D = sqlSession.getMapper(Sch0060D.class);
	}

	// ���1, ���2, ���3, ���4
	public List<ComboVO> selectZSPEC(ComboParamVO param) {
		return sch0060D.selectZSPEC(param);
	}

	// ���س��
	public List<ComboVO> selectCDATE(ComboParamVO param) {
		return sch0060D.selectCDATE(param);
	}

	// ����2, ����3
	public List<ComboVO> selectGUBUN(ComboParamVO param) {
		return sch0060D.selectGUBUN(param);
	}

	// �ſ���
	public List<ComboVO> selectJUDGE(ComboParamVO param) {
		return sch0060D.selectJUDGE(param);
	}

	// ��ġ1
	public List<ComboVO> selectZZACTSS(ComboParamVO param) {
		return sch0060D.selectZZACTSS(param);
	}

	// ��ġ2
	public List<ComboVO> selectTEMNO(ComboParamVO param) {
		return sch0060D.selectTEMNO(param);
	}

	// ����1, ��������, ������������
	public List<ComboVO> selectGUBUNC(ComboParamVO param) {
		return sch0060D.selectGUBUNC(param);
	}

	// �ǹ��뵵
	public List<ComboVO> selectWWBLD(ComboParamVO param) {
		return sch0060D.selectWWBLD(param);
	}

	// �������¾�ü, ��ġ���¾�ü
	public List<ComboVO> selectKUNZ1(ComboParamVO param) {
		return sch0060D.selectKUNZ1(param);
	}

	// ȣ��
	public List<Sch0060> selectHOKI(Sch0060ParamVO param) {
		return sch0060D.selectHOKI(param);
	}

	// ������Ʈ
	public List<Sch0060> selectPROJECT(Sch0060ParamVO param) {
		return sch0060D.selectPROJECT(param);
	}

	// û��
	public List<Sch0060> selectCHEONG(Sch0060ParamVO param) {
		return sch0060D.selectCHEONG(param);
	}

	// ����
	public List<Sch0060> selectSUKEUM(Sch0060ParamVO param) {
		return sch0060D.selectSUKEUM(param);
	}

	public void mergeZSDT1010(MipParameterVO paramVO, Model model, SqlSession session) {
		Dataset dsListSave = paramVO.getDatasetList().getDataset("ds_list_save");

		try {
			// DAO����
			createDao(session);

			Sch0060 param = new Sch0060();

			for (int nRow = 0; nRow < dsListSave.getRowCount(); nRow++) {
				param = paramSet(param, "C", paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_USER_ID"), nRow, dsListSave);

				sch0060D.mergeZSDT1010(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mergeZSDT1011(MipParameterVO paramVO, Model model, SqlSession session) {
		Dataset dsListSave = paramVO.getDatasetList().getDataset("ds_list_save");

		try {
			// DAO����
			createDao(session);

			Sch0060 param = new Sch0060();

			for (int nRow = 0; nRow < dsListSave.getRowCount(); nRow++) {
				param = paramSet(param, "D", paramVO.getVariable("G_MANDT"), paramVO.getVariable("G_USER_ID"), nRow, dsListSave);

				sch0060D.mergeZSDT1011(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Sch0060 paramSet(Sch0060 param, String gubun, String mandt, String user, int nRow, Dataset dsListSave) {
		param.setMANDT(mandt);
		param.setVBELN(dsListSave.getColumnAsString(nRow,"VBELN"));
		param.setPSPID(dsListSave.getColumnAsString(nRow,"PSPID"));
		param.setSPART(dsListSave.getColumnAsString(nRow,"SPART"));
		param.setAUART(dsListSave.getColumnAsString(nRow,"AUART"));
		param.setVKBUR(dsListSave.getColumnAsString(nRow,"VKBUR"));
		param.setVKGRP(dsListSave.getColumnAsString(nRow,"VKGRP"));
		param.setWAERK(dsListSave.getColumnAsString(nRow,"WAERK"));
		param.setDATE(DateTime.getDateString());
		param.setTIME(DateTime.getShortTimeString());
		param.setUSER(user);

		if (gubun.equals("C")) {
			param.setZRQYM(dsListSave.getColumnAsString(nRow,"ZRQYM"));
			param.setNETWR_RQ(new BigDecimal(dsListSave.getColumnAsString(nRow,"NETWR_RQ")));
		} else if (gubun.equals("D")) {
			param.setBYSYM(dsListSave.getColumnAsString(nRow,"BYSYM"));
			param.setCOLBI(new BigDecimal(dsListSave.getColumnAsString(nRow,"COLBI")));
		}

		return param;
	}
}
