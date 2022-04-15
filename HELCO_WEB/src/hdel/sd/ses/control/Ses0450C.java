package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0450;
import hdel.sd.ses.domain.Ses0450ParamVO;
import hdel.sd.ses.service.Ses0450S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0450")
public class Ses0450C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0450S service;
	
	@RequestMapping(value="findListHeader")
	public View Ses0450FindListHeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_header");					// UI�� return�� out dataset
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset
		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Ses0450ParamVO param = new Ses0450ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrqtdat(DatasetUtility.getString(dsCond,"FR_QTDAT"));
			param.setToqtdat(DatasetUtility.getString(dsCond,"TO_QTDAT"));
			param.setFrdlvdat(DatasetUtility.getString(dsCond,"FR_DLVDAT"));
			param.setTodlvdat(DatasetUtility.getString(dsCond,"TO_DLVDAT"));
			param.setVkbur(DatasetUtility.getString(dsCond,"VKBUR"));
			param.setVkgrp(DatasetUtility.getString(dsCond,"VKGRP"));
			param.setZkunnr(DatasetUtility.getString(dsCond,"ZKUNNR"));
			param.setZrqstat(DatasetUtility.getString(dsCond,"ZRQSTAT"));
			param.setGubun(DatasetUtility.getString(dsCond,"GUBUN"));
			param.setQtso_no(DatasetUtility.getString(dsCond,"QTSO_NO"));
			param.setZrqid(DatasetUtility.getString(dsCond,"ZRQID"));
			param.setVbeln(DatasetUtility.getString(dsCond,"VBELN"));

			List<Ses0450> list = service.getListHeader(param);   			// ����CALL
			dsList.deleteAll();
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����
			for (int i = 0; i < list.size(); i++) {

				dsList.appendRow(); 	// ���߰�

				dsList.setColumn(i, "GUBUN",   list.get(i).getGubun());
				dsList.setColumn(i, "MANDT",   list.get(i).getMandt());
				dsList.setColumn(i, "QTSO_NO", list.get(i).getQtso_no());
				dsList.setColumn(i, "QTSER",   list.get(i).getQtser());
				dsList.setColumn(i, "QTSEQ",   list.get(i).getQtseq());
				dsList.setColumn(i, "HOGI",    list.get(i).getHogi());
				dsList.setColumn(i, "VKBUR",   list.get(i).getVkbur());
				dsList.setColumn(i, "VKGRP",   list.get(i).getVkgrp());
				dsList.setColumn(i, "ZKUNNR",  list.get(i).getZkunnr());
				dsList.setColumn(i, "ZKUNNR_NM", list.get(i).getZkunnr_nm());
				dsList.setColumn(i, "ZAGNT",    list.get(i).getZagnt());
				dsList.setColumn(i, "ZAGNT_NM", list.get(i).getZagnt_nm());
				dsList.setColumn(i, "KUNNR",    list.get(i).getKunnr());
				dsList.setColumn(i, "KUNNR_NM", list.get(i).getKunnr_nm());
				dsList.setColumn(i, "GSNAM",    list.get(i).getGsnam());
				dsList.setColumn(i, "SHANGH"  , list.get(i).getShangh()); 
				dsList.setColumn(i, "MATNR"   , list.get(i).getMatnr()); 
				dsList.setColumn(i, "SPEC"    , list.get(i).getSpec()); 
				dsList.setColumn(i, "GTYPE"   , list.get(i).getGtype()); 
				dsList.setColumn(i, "TYPE1"   , list.get(i).getType1()); 
				dsList.setColumn(i, "TYPE2"   , list.get(i).getType2()); 
				dsList.setColumn(i, "TYPE3"   , list.get(i).getType3()); 
				dsList.setColumn(i, "ZNUMBER",  list.get(i).getZnumber());
				dsList.setColumn(i, "ZUSE"    , list.get(i).getZuse());
				dsList.setColumn(i, "ZOPN"    , list.get(i).getZopn());
				dsList.setColumn(i, "ZLEN"    , list.get(i).getZlen());
				dsList.setColumn(i, "ZUAM"    , list.get(i).getZuam());
				dsList.setColumn(i, "ZCOST"   , list.get(i).getZcost());
				dsList.setColumn(i, "ZEAM"    , list.get(i).getZeam());
				dsList.setColumn(i, "SHANG"   , list.get(i).getShang());
				dsList.setColumn(i, "ZRMK"    , list.get(i).getZrmk());
				dsList.setColumn(i, "ZACAPA"  , list.get(i).getZacapa());
				dsList.setColumn(i, "POSNR"   , list.get(i).getPosnr());
				dsList.setColumn(i, "VBELN"   , list.get(i).getVbeln());
			}

			resultVO.addDataset(dsList);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		return view;
	}


	@RequestMapping(value="findListDetail")
	public View Ses0450FindListDetail(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_detail");	// UI�� return�� out dataset  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset

		MipResultVO resultVO = new MipResultVO();
		try
		{ 
			Ses0450ParamVO param = new Ses0450ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			//param.setGubun(DatasetUtility.getString(dsCond,"GUBUN"));
			param.setQtso_no(DatasetUtility.getString(dsCond,"QTSO_NO"));
			param.setQtser(DatasetUtility.getString(dsCond,"QTSER"));
			param.setQtseq(DatasetUtility.getString(dsCond,"QTSEQ"));
			param.setHogi(DatasetUtility.getString(dsCond,"HOGI"));

			
			List<Ses0450> list = service.getListDetail(param);  	// ����CALL
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) { 			// ȣ����(list)�� ����Ÿ��(dsList)�� ����

				dsList.appendRow(); 	// ���߰�
				dsList.setColumn(i, "MANDT"      , list.get(i).getMandt());
				dsList.setColumn(i, "QTSO_NO"    , list.get(i).getQtso_no());
				dsList.setColumn(i, "QTSER"      , list.get(i).getQtser());
				dsList.setColumn(i, "QTSEQ"      , list.get(i).getQtseq());
				dsList.setColumn(i, "HOGI"       , list.get(i).getHogi());
				dsList.setColumn(i, "ZRQSEQ"     , list.get(i).getZrqseq());
				dsList.setColumn(i, "DSNGBN"     , list.get(i).getDsngbn());
				dsList.setColumn(i, "ZRQDAT"     , list.get(i).getZrqdat());
				dsList.setColumn(i, "ZRQID"      , list.get(i).getZrqid());
				dsList.setColumn(i, "ZRQID_NM"   , list.get(i).getZrqid_nm());
				dsList.setColumn(i, "DLVDAT"     , list.get(i).getDlvdat());
				dsList.setColumn(i, "ZRQCN"      , list.get(i).getZrqcn());
				dsList.setColumn(i, "ZRSRLT"     , list.get(i).getZrsrlt());
				dsList.setColumn(i, "FINDAT"     , list.get(i).getFindat());
				dsList.setColumn(i, "KUNNR_DS"   , list.get(i).getKunnr_ds());
				dsList.setColumn(i, "KUNNR_DS_NM", list.get(i).getKunnr_ds_nm());
				dsList.setColumn(i, "EX_KUNNR"   , list.get(i).getEx_kunnr());
				dsList.setColumn(i, "EX_KUNNR_NM", list.get(i).getEx_kunnr_nm());
				dsList.setColumn(i, "ZRQSTAT"    , list.get(i).getZrqstat());
				dsList.setColumn(i, "RQ_LEVEL"   , list.get(i).getRq_level());
				dsList.setColumn(i, "ZRQSEQ_DEL", list.get(i).getZrqseq_del());
			}

			resultVO.addDataset(dsList);    
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}


	@RequestMapping(value="findRequestList")
	public View Ses0460FindRequestList(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");	// UI�� return�� out dataset  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset

		MipResultVO resultVO = new MipResultVO();
		try
		{ 
			Ses0450ParamVO param = new Ses0450ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrdlvdat(DatasetUtility.getString(dsCond,"FR_DLVDAT"));
			param.setTodlvdat(DatasetUtility.getString(dsCond,"TO_DLVDAT"));
			param.setFrvkbur(DatasetUtility.getString(dsCond,"FR_VKBUR"));
			param.setTovkbur(DatasetUtility.getString(dsCond,"TO_VKBUR"));
			param.setFrvkgrp(DatasetUtility.getString(dsCond,"FR_VKGRP"));
			param.setTovkgrp(DatasetUtility.getString(dsCond,"TO_VKGRP"));
			param.setZagnt(DatasetUtility.getString(dsCond,"ZAGNT"));
			param.setZkunnr(DatasetUtility.getString(dsCond,"ZKUNNR"));			// ��û��
			param.setKunnr_ds(DatasetUtility.getString(dsCond,"KUNNR_DS"));
			param.setZrqstat(DatasetUtility.getString(dsCond,"ZRQSTAT"));
			param.setQtso_no(DatasetUtility.getString(dsCond,"QTSO_NO"));
			param.setEmail_dept(DatasetUtility.getString(dsCond,"EMAIL_DEPT"));
			param.setZrqseq_del(DatasetUtility.getString(dsCond,"ZRQSEQ_DEL"));
			param.setDsngbn(DatasetUtility.getString(dsCond,"DSNGBN"));
			param.setProctp(DatasetUtility.getString(dsCond,"PROCTP"));
			
			if (param.getFrvkbur() == null)	{
				param.setFrvkbur("");
			}

			if (param.getTovkbur() == null)	{
				param.setTovkbur("");
			}

			if (param.getFrvkgrp() == null)	{
				param.setFrvkgrp("");
			}

			if (param.getTovkgrp() == null)	{
				param.setTovkgrp("");
			}
			
			if (param.getEmail_dept() == null)	{
				param.setEmail_dept("");
			}
			
			if (param.getZrqseq_del() == null)	{
				param.setZrqseq_del("");
			}

			List<Ses0450> list = service.getRequestList(param);  	// ����CALL
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) { 			// ȣ����(list)�� ����Ÿ��(dsList)�� ����

				dsList.appendRow(); 	// ���߰�
				dsList.setColumn(i, "MANDT"      , list.get(i).getMandt());
				dsList.setColumn(i, "GUBUN"      , list.get(i).getGubun());
				dsList.setColumn(i, "QTSO_NO"    , list.get(i).getQtso_no());
				dsList.setColumn(i, "QTSER"      , list.get(i).getQtser()); 
				dsList.setColumn(i, "QTSEQ"      , list.get(i).getQtseq()); 
				dsList.setColumn(i, "HOGI"       , list.get(i).getHogi()); 
				dsList.setColumn(i, "ZRQSEQ"     , list.get(i).getZrqseq());
				dsList.setColumn(i, "DSNGBN"     , list.get(i).getDsngbn());
				dsList.setColumn(i, "ZRQDAT"     , list.get(i).getZrqdat()); 
				dsList.setColumn(i, "DLVDAT"      , list.get(i).getDlvdat()); 
				dsList.setColumn(i, "ZRQCN"      , list.get(i).getZrqcn()); 
				dsList.setColumn(i, "ZRSRLT"     , list.get(i).getZrsrlt()); 
				dsList.setColumn(i, "FINDAT"     , list.get(i).getFindat());
				dsList.setColumn(i, "ZRQID"      , list.get(i).getZrqid());
				dsList.setColumn(i, "ZRQID_NM"   , list.get(i).getZrqid_nm());
				dsList.setColumn(i, "KUNNR_DS"    , list.get(i).getKunnr_ds());
				dsList.setColumn(i, "KUNNR_DS_NM" , list.get(i).getKunnr_ds_nm());
				dsList.setColumn(i, "KUNNR_DS_TEL", list.get(i).getKunnr_ds_tel());
				dsList.setColumn(i, "EX_KUNNR"    , list.get(i).getEx_kunnr());
				dsList.setColumn(i, "EX_KUNNR_NM" , list.get(i).getEx_kunnr_nm());
				dsList.setColumn(i, "EX_KUNNR_TEL", list.get(i).getEx_kunnr_tel());
				dsList.setColumn(i, "ZRQSTAT"     , list.get(i).getZrqstat());
				dsList.setColumn(i, "MATNR"   , list.get(i).getMatnr()); 
				dsList.setColumn(i, "SPEC"    , list.get(i).getSpec()); 
				dsList.setColumn(i, "GTYPE"   , list.get(i).getGtype()); 
				dsList.setColumn(i, "TYPE1"   , list.get(i).getType1()); 
				dsList.setColumn(i, "TYPE2"   , list.get(i).getType2()); 
				dsList.setColumn(i, "TYPE3"   , list.get(i).getType3()); 
				dsList.setColumn(i, "ZNUMBER" , list.get(i).getZnumber());
				dsList.setColumn(i, "ZUSE"    , list.get(i).getZuse());
				dsList.setColumn(i, "ZOPN"    , list.get(i).getZopn());
				dsList.setColumn(i, "ZLEN"    , list.get(i).getZlen());
				dsList.setColumn(i, "ZACAPA"  , list.get(i).getZacapa());
				dsList.setColumn(i, "VKBUR"   , list.get(i).getVkbur());
				dsList.setColumn(i, "VKGRP"   , list.get(i).getVkgrp());
				dsList.setColumn(i, "ZKUNNR"  , list.get(i).getZkunnr());
				dsList.setColumn(i, "ZKUNNR_NM",  list.get(i).getZkunnr_nm());
				dsList.setColumn(i, "ZKUNNR_TEL", list.get(i).getZkunnr_tel());
				dsList.setColumn(i, "ZAGNT",      list.get(i).getZagnt());
				dsList.setColumn(i, "ZAGNT_NM",   list.get(i).getZagnt_nm());
				dsList.setColumn(i, "POSNR",      list.get(i).getPosnr());
				dsList.setColumn(i, "GSNAM",      list.get(i).getGsnam());
				dsList.setColumn(i, "DFINDAT",      list.get(i).getDfindat());
				dsList.setColumn(i, "ZRQSEQ_DEL", list.get(i).getZrqseq_del());
				dsList.setColumn(i, "EMAIL_DEPT", list.get(i).getEmail_dept());
				dsList.setColumn(i,  "PROCTP", 	  list.get(i).getProctp());
				
			}

			resultVO.addDataset(dsList);    
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
}
