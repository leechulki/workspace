package hdel.sd.com.control;

import java.util.HashMap;
import java.util.Iterator;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.RequireException;
import hdel.sd.com.service.DutyS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("duty")
public class DutyC {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private DutyS service;
	
	@RequestMapping(value="genDuty")
	public View genDuty(MipParameterVO paramVO, Model model) {
		Dataset dsCond      = paramVO.getDatasetList().getDataset("ds_cond");
		Dataset dsTemplate1 = paramVO.getDatasetList().getDataset("ds_template1");
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		HashMap mapInput = new HashMap();

		for (int i=0; i<dsTemplate1.getRowCount(); i++) {
			String value = dsTemplate1.getColumnAsString(i, "PRD");
			if (null != value && ! "".equals(value)) {
				mapInput.put(dsTemplate1.getColumnAsString(i, "PRH"), value);
			}
		}

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset
		try
		{
			String gtype = "";
			String sGubun = "";

			sGubun = (String) mapInput.get("EL_ATYP");
			if ("WBSS".equals(sGubun)) {
			  sGubun = "SSVF";
			}
			
			if (sGubun != null && !"".equals(sGubun))	{
				gtype = (String) mapInput.get("EL_ATYP");

				if ("WBSS".equals(gtype)) {
				  gtype = "SSVF";
				}

			}else {
				// �����÷�����
				sGubun = (String) mapInput.get("ES_ATYP");
				if (sGubun != null && !"".equals(sGubun))	{
					gtype = "MLBT";	// �����÷����ʹ� Default�� ó��
				}else {
					// ������ũ
					sGubun = (String) mapInput.get("MW_ATYP");
					if (sGubun != null && !"".equals(sGubun))	{
						gtype = "PMBTL";	// ������ũ�� Default�� ó��
					}
				}
			}

			if (null == gtype || "".equals(gtype))	 {
				if (!"ko".equals(paramVO.getVariable("G_LANG")))		{
					throw new RequireException("TYPE");
				}else	{
					throw new RequireException("����");
				}
			}
			
			mapInput = service.genSpec(paramVO.getVariable("G_MANDT"), gtype, dsCond.getColumnAsString(0, "BLOCKGBN"), mapInput, paramVO.getVariable("G_LANG"));
		} catch (RuntimeException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		String prh  = "";
		String prhname  = "";
		String prd  = "";
		String atkla = "";
		
		for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
			prh  = dsTemplate1.getColumnAsString(i, "PRH");
			prhname  = dsTemplate1.getColumnAsString(i, "PRHNAME");
			prd  = (String) mapInput.get(dsTemplate1.getColumnAsString(i, "PRH"));
			atkla = dsTemplate1.getColumnAsString(i, "ATKLA");

			if (prh  == null) prh = "";
			if (atkla == null) atkla = "";
				
			if (null != prd && ! "".equals(prd)) {
				dsTemplate1.setColumn(i, "PRH"   , prh );
				dsTemplate1.setColumn(i, "PRHNAME"   , prhname );
				dsTemplate1.setColumn(i, "PRD"   , prd );
				dsTemplate1.setColumn(i, "ATKLA", atkla);
			}
		}

		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsTemplate1);
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}

	@RequestMapping(value="stdGenDuty")
	public View stdGenDuty(MipParameterVO paramVO, Model model) {

		Dataset dsTemplate1 = paramVO.getDatasetList().getDataset("ds_template1");
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		HashMap mapInput = new HashMap();

		// ORG ������� �ӽ� ����
		for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
			String value = dsTemplate1.getColumnAsString(i, "PRD");
			if (null != value && ! "".equals(value)) {
				mapInput.put(dsTemplate1.getColumnAsString(i, "PRH"), value);
			}
		}

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset
		try
		{
			// ����(ǥ�����ø� ó�� ����)
			String sGtype    = (String) paramVO.getVariable("strGtype");
			String sBlockGbn = "00";
			String gtype     = "";

			String sGubun = (String) mapInput.get("EL_ATYP");
			if (sGubun != null && !"".equals(sGubun))	{
				gtype = (String) mapInput.get("EL_ATYP");

			}else {
				// �����÷�����
				sGubun = (String) mapInput.get("ES_ATYP");
				if (sGubun != null && !"".equals(sGubun))	{
					gtype = "MLBT";	// �����÷����ʹ� Default�� ó��
				}else {
					// ������ũ
					sGubun = (String) mapInput.get("MW_ATYP");
					if (sGubun != null && !"".equals(sGubun))	{
						gtype = "PMBTL";	// ������ũ�� Default�� ó��
					}
				}
			}

			if (null == gtype || "".equals(gtype))	 {
				if (!"ko".equals(paramVO.getVariable("G_LANG")))		{
					throw new RequireException("TYPE");
				}else	{
					throw new RequireException("����");
				}
			}

			mapInput = service.stdGenSpec(paramVO.getVariable("G_MANDT"), sGtype, sBlockGbn, mapInput, paramVO.getVariable("G_LANG"));
		} catch (RuntimeException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		// ��ǰ���п� �ص��ϴ� ������ �������� �ʴ� ��� �� Template ���� return.
		if (mapInput != null)	{
			String prh  = "";
			String prhname  = "";
			String prd  = "";
			String atkla = "";
			
			for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
				prh      = dsTemplate1.getColumnAsString(i, "PRH");
				prhname  = dsTemplate1.getColumnAsString(i, "PRHNAME");
				prd      = (String) mapInput.get(dsTemplate1.getColumnAsString(i, "PRH"));
				atkla    = dsTemplate1.getColumnAsString(i, "ATKLA");
	
				if (prh   == null) prh   = "";
				if (atkla == null) atkla = "";
					
				if (null != prd && ! "".equals(prd)) {
					dsTemplate1.setColumn(i, "PRH",     prh );
					dsTemplate1.setColumn(i, "PRHNAME", prhname );
					dsTemplate1.setColumn(i, "PRD",     prd );
					dsTemplate1.setColumn(i, "ATKLA",   atkla);
				}
			}
		}

		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsTemplate1);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}

	@RequestMapping(value="dependDuty")
	public View dependDuty(MipParameterVO paramVO, Model model) {

		Dataset dsTemplate1 = paramVO.getDatasetList().getDataset("ds_template1");
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		HashMap mapInput = new HashMap();

		// ORG ������� �ӽ� ����
		for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
			String value = dsTemplate1.getColumnAsString(i, "PRD");
			if (null != value && ! "".equals(value)) {
				mapInput.put(dsTemplate1.getColumnAsString(i, "PRH"), value);
			}
		}

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset
		try
		{
			// ����(ǥ�����ø� ó�� ����)
			String sGtype    = (String) paramVO.getVariable("strGtype");
			String sBlockGbn = "00";
			String gtype     = "";

			String sGubun = (String) mapInput.get("EL_ATYP");
			if (sGubun != null && !"".equals(sGubun))	{
				gtype = (String) mapInput.get("EL_ATYP");

			}else {
				// �����÷�����
				sGubun = (String) mapInput.get("ES_ATYP");
				if (sGubun != null && !"".equals(sGubun))	{
					gtype = "MLBT";	// �����÷����ʹ� Default�� ó��
				}else {
					// ������ũ
					sGubun = (String) mapInput.get("MW_ATYP");
					if (sGubun != null && !"".equals(sGubun))	{
						gtype = "PMBTL";	// ������ũ�� Default�� ó��
					} else {
						sGubun = (String) mapInput.get("AP_ATYP");
						if (sGubun != null && !"".equals(sGubun))	{
							gtype = "ELV";	// ������ Default�� ó��
						}						
					}
				}
			}

			if (null == gtype || "".equals(gtype))	 {
				if (!"ko".equals(paramVO.getVariable("G_LANG")))		{
					throw new RequireException("TYPE");
				}else	{
					throw new RequireException("����");
				}
			}

			mapInput = service.dependSpec(paramVO.getVariable("G_MANDT"), sGtype, sBlockGbn, mapInput, paramVO.getVariable("G_LANG"));
		} catch (RuntimeException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		// ��ǰ���п� �ص��ϴ� ������ �������� �ʴ� ��� �� Template ���� return.
		if (mapInput != null)	{
			String prh  = "";
			String prhname  = "";
			String prd  = "";
			String atkla = "";
			
			for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
				prh      = dsTemplate1.getColumnAsString(i, "PRH");
				prhname  = dsTemplate1.getColumnAsString(i, "PRHNAME");
				prd      = (String) mapInput.get(dsTemplate1.getColumnAsString(i, "PRH"));
				atkla    = dsTemplate1.getColumnAsString(i, "ATKLA");
	
				if (prh   == null) prh   = "";
				if (atkla == null) atkla = "";
					
				if (null != prd && ! "".equals(prd)) {
					dsTemplate1.setColumn(i, "PRH",     prh );
					dsTemplate1.setColumn(i, "PRHNAME", prhname );
					dsTemplate1.setColumn(i, "PRD",     prd );
					dsTemplate1.setColumn(i, "ATKLA",   atkla);
				}
			}
		}

		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsTemplate1);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
}
