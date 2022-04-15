package hdel.sd.ses.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
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
import hdel.sd.ses.domain.Ses0420;
import hdel.sd.ses.domain.Ses0420ParamVO;
import hdel.sd.ses.service.Ses0420S;
import hdel.sd.smp.control.SmpComC;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0420")
public class Ses0420C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0420S service;
	
	@RequestMapping(value="findList")
	public View Ses0420FindListView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");					// UI out dataset
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI return dataset

		// RFC  return
		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Ses0420ParamVO param = new Ses0420ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO

			String lang = paramVO.getVariable("G_LANG");
			String spras = "3";

			if (lang.equals("ko"))
				spras = "3";
			else
				spras = "E";				
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrzrqdat(DatasetUtility.getString(dsCond,"FR_ZRQDAT"));
			param.setTozrqdat(DatasetUtility.getString(dsCond,"TO_ZRQDAT"));
			param.setFrdeldat(DatasetUtility.getString(dsCond,"FR_DELDAT"));
			param.setTodeldat(DatasetUtility.getString(dsCond,"TO_DELDAT"));
			param.setVkbur(DatasetUtility.getString(dsCond,"VKBUR"));
			param.setVkgrp(DatasetUtility.getString(dsCond,"VKGRP"));
			param.setZkunnr(DatasetUtility.getString(dsCond,"ZKUNNR"));
			param.setZkunnr_z3(DatasetUtility.getString(dsCond,"ZKUNNR_Z3"));
			param.setGubun(DatasetUtility.getString(dsCond,"GUBUN"));
			param.setQtso_no(DatasetUtility.getString(dsCond,"QTSO_NO"));
			param.setYcont(DatasetUtility.getString(dsCond,"YCONT"));
			
			param.setFrildat(DatasetUtility.getString(dsCond, "FR_ILDAT"));
			param.setToildat(DatasetUtility.getString(dsCond, "TO_ILDAT"));
			
			param.setFrfrcmfcdat(DatasetUtility.getString(dsCond, "FR_FRCMFCDAT"));
			param.setTofrcmfcdat(DatasetUtility.getString(dsCond, "TO_FRCMFCDAT"));
			
			param.setPart(DatasetUtility.getString(dsCond,"PART"));
			param.setSpras(spras);
			
			param.setD_zqntytot(DatasetUtility.getInt(dsCond,"D_ZQNTYTOT"));
			
			List<Ses0420> list = service.getListHeader(param);   			// CALL
			dsList.deleteAll();

			Calendar calendar = Calendar.getInstance();		
			
			for (int i = 0; i < list.size(); i++) {
				
				if(param.getD_zqntytot() <= Integer.parseInt(list.get(i).getZqntytot())) {
					dsList.appendRow(); 
				}else continue;
											
				dsList.setColumn(dsList.getRowCount()-1, "MANDT",   list.get(i).getMandt());
				dsList.setColumn(dsList.getRowCount()-1, "ZRQSEQ", list.get(i).getZrqseq());
				dsList.setColumn(dsList.getRowCount()-1, "KUNNR_Z3_NM", list.get(i).getKunnr_z3_nm());
				dsList.setColumn(dsList.getRowCount()-1, "QTSO_NO", list.get(i).getQtso_no());
				dsList.setColumn(dsList.getRowCount()-1, "QTSER",   list.get(i).getQtser());
				dsList.setColumn(dsList.getRowCount()-1, "QTSEQ",   list.get(i).getQtseq());
				dsList.setColumn(dsList.getRowCount()-1, "HOGI",   list.get(i).getHogi());
				dsList.setColumn(dsList.getRowCount()-1, "QTDAT",   list.get(i).getQtdat());
				dsList.setColumn(dsList.getRowCount()-1, "RECAD_DA",   list.get(i).getRecad_da());

				try {
					
					calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(list.get(i).getDeldat()));
					
				} catch(ParseException pe) {
					
				}
				
				calendar.add(Calendar.DATE, -90);
				dsList.setColumn(dsList.getRowCount()-1, "DEL90", new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));

				float f = TimeUnit.DAYS.convert(calendar.getTime().getTime() - Calendar.getInstance().getTime().getTime(), TimeUnit.MILLISECONDS);
				dsList.setColumn(dsList.getRowCount()-1, "DEL90DAYS", (int) f);

				dsList.setColumn(dsList.getRowCount()-1, "DELDAT",   list.get(i).getDeldat());
				dsList.setColumn(dsList.getRowCount()-1, "LODAT",   list.get(i).getLodat());
				dsList.setColumn(dsList.getRowCount()-1, "FINDAT",   list.get(i).getFindat());
				dsList.setColumn(dsList.getRowCount()-1, "ZRQDAT",   list.get(i).getZrqdat());
				dsList.setColumn(dsList.getRowCount()-1, "VKBUR",   list.get(i).getVkbur());
				dsList.setColumn(dsList.getRowCount()-1, "VKGRP",   list.get(i).getVkgrp());
				dsList.setColumn(dsList.getRowCount()-1, "ZKUNNR",  list.get(i).getZkunnr());
				dsList.setColumn(dsList.getRowCount()-1, "ZKUNNR_NM", list.get(i).getZkunnr_nm());
				dsList.setColumn(dsList.getRowCount()-1, "KUNNR",    list.get(i).getKunnr());
				dsList.setColumn(dsList.getRowCount()-1, "KUNNR_NM", list.get(i).getKunnr_nm());
				dsList.setColumn(dsList.getRowCount()-1, "GSNAM",    list.get(i).getGsnam()); 
				dsList.setColumn(dsList.getRowCount()-1, "SPEC", 	list.get(i).getSpec()); 
				dsList.setColumn(dsList.getRowCount()-1, "ZNUMBER",  list.get(i).getZnumber());;				
				dsList.setColumn(dsList.getRowCount()-1, "LP_IS",  list.get(i).getLp_is());;					
				dsList.setColumn(dsList.getRowCount()-1, "LP_CHN",  list.get(i).getLp_chn());;				
				dsList.setColumn(dsList.getRowCount()-1, "LP_OLD",  list.get(i).getLp_old());;				
				dsList.setColumn(dsList.getRowCount()-1, "NONSTD",  list.get(i).getNonstd());;						
				dsList.setColumn(dsList.getRowCount()-1, "APR_IS",  list.get(i).getApr_is());;		
				dsList.setColumn(dsList.getRowCount()-1, "CHAI",  list.get(i).getChai());;				
				dsList.setColumn(dsList.getRowCount()-1, "ZRQSTAT",  list.get(i).getZrqstat());;				
				dsList.setColumn(dsList.getRowCount()-1, "ZZPJT_ID",  list.get(i).getZzpjt_id());;				
				dsList.setColumn(dsList.getRowCount()-1, "CONFDAT",  list.get(i).getConfdat());;				
				dsList.setColumn(dsList.getRowCount()-1, "CADCONFDT",  list.get(i).getCadconfdt());;				
				dsList.setColumn(dsList.getRowCount()-1, "CONFCHAI",  list.get(i).getConfchai());;				
				dsList.setColumn(dsList.getRowCount()-1, "CONFDCHAI",  list.get(i).getConfdchai());;			
				dsList.setColumn(dsList.getRowCount()-1, "TP_DATE4",  list.get(i).getTp_date4());;			
				dsList.setColumn(dsList.getRowCount()-1, "TP_DATE2",  list.get(i).getTp_date2());;			
				dsList.setColumn(dsList.getRowCount()-1, "CONTR_DA",  list.get(i).getContr_da());;			
				dsList.setColumn(dsList.getRowCount()-1, "ADMITNO",  list.get(i).getAdmitno());;
				dsList.setColumn(dsList.getRowCount()-1, "KUNNR_Z2_NM", list.get(i).getKunnr_z2_nm());		
				dsList.setColumn(dsList.getRowCount()-1, "CHAI_LC",  list.get(i).getChai_lc());;				
				dsList.setColumn(dsList.getRowCount()-1, "CHAI_RC",  list.get(i).getChai_rc());;				
				dsList.setColumn(dsList.getRowCount()-1, "CHAI_PC",  list.get(i).getChai_pc());;				
				dsList.setColumn(dsList.getRowCount()-1, "CHAI_DAN",  list.get(i).getChai_dan());;				
				dsList.setColumn(dsList.getRowCount()-1, "VKBURT",  list.get(i).getVkburt());;					
				dsList.setColumn(dsList.getRowCount()-1, "YCONT",  list.get(i).getYcont());;					
				dsList.setColumn(dsList.getRowCount()-1, "LHSITE",  list.get(i).getLhsite());;
				dsList.setColumn(dsList.getRowCount()-1, "BEZEI",  list.get(i).getBezei());
				dsList.setColumn(dsList.getRowCount()-1, "ZZCHAKD",  list.get(i).getZzchakd());
				dsList.setColumn(dsList.getRowCount()-1, "SPECDUE",  list.get(i).getSpecdue());
				/*if (list.get(i).getLfindat() == null ) {
					dsList.setColumn(i, "LFINDAT",  "");
				} else {
					dsList.setColumn(i, "LFINDAT",  list.get(i).getLfindat());
				}*/
				
				dsList.setColumn(dsList.getRowCount()-1, "ZQNTYTOT", list.get(i).getZqntytot());
				dsList.setColumn(dsList.getRowCount()-1, "BFSHIPDAYS", list.get(i).getBfshipdays());
				
				/*if (list.get(i).getAppdodat() == null ) {
					dsList.setColumn(i, "APPDODAT",  "");
				} else {
					dsList.setColumn(i, "APPDODAT",  list.get(i).getAppdodat());
				}
				if (list.get(i).getAppdwdat() == null ) {
					dsList.setColumn(i, "APPDWDAT",  "");
				} else {
					dsList.setColumn(i, "APPDWDAT",  list.get(i).getAppdwdat());
				}*/
				
				if (list.get(i).getIldat() == null ) {
					dsList.setColumn(dsList.getRowCount()-1, "ILDAT",  "");
				} else {
					dsList.setColumn(dsList.getRowCount()-1, "ILDAT",  list.get(i).getIldat());
				}						
				
				dsList.setColumn(dsList.getRowCount()-1, "SPCODAYS", list.get(i).getSpcodays());
				
				if (list.get(i).getFrcmfcdat() == null ) {
					dsList.setColumn(dsList.getRowCount()-1, "FRCMFCDAT",  "");
				} else {
					dsList.setColumn(dsList.getRowCount()-1, "FRCMFCDAT",  list.get(i).getFrcmfcdat());
				}				
				
				dsList.setColumn(dsList.getRowCount()-1, "ZDESC", list.get(i).getZdesc());
				dsList.setColumn(dsList.getRowCount()-1, "APPROVAL", list.get(i).getApproval());
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
	
	@RequestMapping(value="save")
	public View Ses0420SaveListView(MipParameterVO paramVO, Model model) {

		//Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");					// UI out dataset		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI return dataset
		SqlSession session   = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));	// Session GET

		// RFC  return
		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Ses0420ParamVO param = new Ses0420ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO
			service.saveAddData(paramVO, model, session);
			
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
