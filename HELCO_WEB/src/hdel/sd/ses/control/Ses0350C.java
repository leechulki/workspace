package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0350;
import hdel.sd.ses.domain.Ses0350ParamVO;
import hdel.sd.ses.service.Ses0350S;

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
@RequestMapping("ses0350")
public class Ses0350C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0350S service;
	
	@RequestMapping(value="header")
	public View Ses0350HeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_header");	// UI�� return�� out dataset  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset

		MipResultVO resultVO = new MipResultVO(); 	// ��� dataset�� return

		try {
			dsList = headerToDataset(dsList, dsCond, paramVO);

			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		return view;
	}
	
	private Dataset headerToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) throws Exception	{

		try	{
			Ses0350ParamVO param = new Ses0350ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			String mandt   = DatasetUtility.getString(dsCond,"MANDT");
			String frcdate = DatasetUtility.getString(dsCond,"FR_CDATE");
			String tocdate = DatasetUtility.getString(dsCond,"TO_CDATE");
			String vkbur   = DatasetUtility.getString(dsCond,"VKBUR");
			String vkgrp   = DatasetUtility.getString(dsCond,"VKGRP");
			String zkunnr  = DatasetUtility.getString(dsCond,"SMAN");
			String qtnum   = DatasetUtility.getString(dsCond,"QTNUM");
			String qtser   = DatasetUtility.getString(dsCond,"QTSER");
			String pspid   = DatasetUtility.getString(dsCond,"PSPID");
			
			if (mandt == null)   mandt   = "";
			if (frcdate == null) frcdate = "";
			if (tocdate == null) tocdate = "";
			if (vkbur == null)   vkbur   = "";
			if (vkgrp == null)   vkgrp   = "";
			if (zkunnr == null)  zkunnr  = "";
			if (qtnum == null)   qtnum   = "";
			if (qtser == null)   qtser   = "";
			if (pspid == null)   pspid = "";
			
			String vbeln = "";
			vbeln = pspid;
			
			param.setMandt   (mandt);   // SAP CLIENT
			param.setFrcdate (frcdate);
			param.setTocdate (tocdate);
			param.setVkbur   (vkbur);
			param.setVkgrp   (vkgrp);
			param.setZkunnr  (zkunnr);
			param.setQtnum   (qtnum);
			param.setQtser   (qtser);
			param.setVbeln	 (vbeln);
			
			List<Ses0350> list = service.searchHeader(param); // ����CALL
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) 	{	// ȣ����(list)�� ����Ÿ��(dsList)�� ����
				dsList.appendRow(); 	// ���߰�
				dsList.setColumn(i, "MANDT"         , list.get(i).getMandt());
				dsList.setColumn(i, "QTNUM"         , list.get(i).getQtnum());		// ������ȣ
				dsList.setColumn(i, "QTSER"         , list.get(i).getQtser()); 	// ����
				dsList.setColumn(i, "QTDAT"         , list.get(i).getQtdat()); 	// ��������
				dsList.setColumn(i, "VKBUR"         , list.get(i).getVkbur());		// �μ�
				dsList.setColumn(i, "VKGRP"         , list.get(i).getVkgrp());		// ��
				dsList.setColumn(i, "ZKUNNR"        , list.get(i).getZkunnr());		// �����
				dsList.setColumn(i, "ZKUNNR_NM"     , list.get(i).getZkunnr_nm());		// ����ڸ�
				dsList.setColumn(i, "CDATE"         , list.get(i).getCdate());		// ��û��
				dsList.setColumn(i, "VBELN"         , list.get(i).getVbeln());		// ������Ʈ��ȣ
				dsList.setColumn(i, "GSNAM"         , list.get(i).getGsnam());		// �����
				dsList.setColumn(i, "WAERK"         , list.get(i).getWaerk());		// ��ȭ
				dsList.setColumn(i, "ZPRGFLG"       , list.get(i).getZprgflg());	// �����������
				dsList.setColumn(i, "SEQ"           , list.get(i).getSeq());		// ���ó������
				dsList.setColumn(i, "FINL"          , list.get(i).getFinl());		// ���ó������
				dsList.setColumn(i, "BIGO"          , list.get(i).getBigo());		// ����,��� ��������
				dsList.setColumn(i, "AUART"         , list.get(i).getAuart());		//
				dsList.setColumn(i, "ZSUM_ZCOST"         , list.get(i).getZsum_zcost());		//
			}
			
			return dsList;
		}catch (Exception e){
			throw e;
		}
	}
	
	@RequestMapping(value="getZuam")
	public View Ses0350ListrView(MipParameterVO paramVO, Model model) {

		Dataset ds_list = paramVO.getDataset("ds_list");
		//Dataset dsList = paramVO.getDataset("ds_header");	// UI�� return�� out dataset  
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset

		MipResultVO resultVO = new MipResultVO(); 	// ��� dataset�� return
		
		try {
			Ses0350ParamVO param = new Ses0350ParamVO();
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			String mandt      = paramVO.getVariable("G_MANDT");
			String qtnum      = DatasetUtility.getString(ds_list,"QTNUM");
			String qtser      = DatasetUtility.getString(ds_list,"QTSER");
			String pspid      = DatasetUtility.getString(ds_list,"PSPID");
			String seq        = DatasetUtility.getString(ds_list,"SEQ");

			param.setMandt(mandt);
			param.setQtnum(qtnum);
			param.setQtser(qtser);
			param.setPspid(pspid);
			param.setSeq(seq);
			
			Ses0350 zuamVO;
			String f_flag = paramVO.getVariable("f_flag");
			if ("P".equals(f_flag)){ 
				zuamVO = service.getWavwr(param); // ����CALL
			} else{
				zuamVO = service.getZuam(param); // ����CALL
			}
			ds_list.deleteAll();

			if (zuamVO != null) 	{
				ds_list.appendRow(); 	// ���߰�
				ds_list.setColumn(0, "MANDT"  ,   zuamVO.getMandt());
				ds_list.setColumn(0, "QTNUM"  ,   zuamVO.getQtnum());
				ds_list.setColumn(0, "QTSER"  ,   zuamVO.getQtser());
				//ds_list.setColumn(0, "PSPID"  ,   zuamVO.getPspid());
				ds_list.setColumn(0, "ZSUM_ZCOST" ,   zuamVO.getZsum_zcost());
			}
			resultVO.addDataset(ds_list);

			model.addAttribute("resultVO", resultVO);
			
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}	    
		//ds_error.setId("ds_error");
		//resultVO.addDataset(ds_error);
		return view;
	}

}
