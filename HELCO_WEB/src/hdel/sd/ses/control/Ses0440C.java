package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0440S;
import hdel.sd.ses.domain.Ses0440;
import hdel.sd.ses.domain.Ses0440ParamVO;

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
@RequestMapping("ses0440")
public class Ses0440C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0440S service;
	
	@RequestMapping(value="findListHeader")
	public View Ses0440FindListHeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");	// UI로 return할 out dataset  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Ses0440ParamVO param = new Ses0440ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrerdat(DatasetUtility.getString(dsCond,"FR_ERDAT"));
			param.setToerdat(DatasetUtility.getString(dsCond,"TO_ERDAT"));
			param.setZkunnr(DatasetUtility.getString(dsCond, "ZKUNNR"));
			param.setPspid(DatasetUtility.getString(dsCond, "PSPID"));
			param.setSeq(DatasetUtility.getString(dsCond, "SEQ"));
			param.setFrvkbur(DatasetUtility.getString(dsCond, "FR_VKBUR"));
			param.setFrvkgrp(DatasetUtility.getString(dsCond, "FR_VKGRP"));
			param.setTovkbur(DatasetUtility.getString(dsCond, "TO_VKBUR"));
			param.setTovkgrp(DatasetUtility.getString(dsCond, "TO_VKGRP"));
			param.setKunnr_z3(DatasetUtility.getString(dsCond, "KUNNR_Z3"));

			List<Ses0440> list = service.getListHeader(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int i = 0; i < list.size(); i++) {

				dsList.appendRow(); 	// 행추가
			    
				dsList.setColumn(i, "MANDT",   list.get(i).getMandt());
				dsList.setColumn(i, "PSPID",   list.get(i).getPspid());
				dsList.setColumn(i, "SEQ",     list.get(i).getSeq());
				dsList.setColumn(i, "STATUS",   list.get(i).getStatus());
				dsList.setColumn(i, "ERDAT",   list.get(i).getErdat());
				dsList.setColumn(i, "VKBUR",   list.get(i).getVkbur());
				dsList.setColumn(i, "VKBURT",   list.get(i).getVkburt());
				dsList.setColumn(i, "VKGRP",   list.get(i).getVkgrp());
				dsList.setColumn(i, "VKGRPT",   list.get(i).getVkgrpt());
				dsList.setColumn(i, "ZKUNNR",  list.get(i).getZkunnr());
				dsList.setColumn(i, "ZKUNNR_NM", list.get(i).getZkunnr_nm());
				dsList.setColumn(i, "BSTNK",   list.get(i).getBstnk());
				dsList.setColumn(i, "BIGO01",   list.get(i).getBigo01());
				dsList.setColumn(i, "REPL01",   list.get(i).getRepl01());
				dsList.setColumn(i, "FINDAT",   list.get(i).getFindat());
				dsList.setColumn(i, "REQDAT",   list.get(i).getReqdat());
				dsList.setColumn(i, "RQSER",   list.get(i).getRqser());
				dsList.setColumn(i, "IS_LO",   list.get(i).getIs_lo());
				dsList.setColumn(i, "IS_DM",   list.get(i).getIs_dm());
				dsList.setColumn(i, "KUNNR_Z3",   list.get(i).getKunnr_z3());
				dsList.setColumn(i, "KUNNR_Z3_NM",   list.get(i).getKunnr_z3_nm());
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
	public View Ses0440FindListDetail(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_detail");	// UI로 return할 out dataset  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
		try
		{ 
			Ses0440ParamVO param = new Ses0440ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setPspid(DatasetUtility.getString(dsCond, "PSPID"));
			param.setSeq(DatasetUtility.getString(dsCond, "SEQ"));

			
			List<Ses0440> list = service.getListDetail(param);  	// 서비스CALL
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "MANDT",   list.get(i).getMandt());
				dsList.setColumn(i, "PSPID",   list.get(i).getPspid());
				dsList.setColumn(i, "STATUS",   list.get(i).getStatus());
				dsList.setColumn(i, "ERDAT",   list.get(i).getErdat());
				dsList.setColumn(i, "VKBUR",   list.get(i).getVkbur());
				dsList.setColumn(i, "VKGRP",   list.get(i).getVkgrp());
				dsList.setColumn(i, "ZKUNNR",  list.get(i).getZkunnr());
				dsList.setColumn(i, "ZKUNNR_NM", list.get(i).getZkunnr_nm());
				dsList.setColumn(i, "BSTNK",   list.get(i).getBstnk());
				dsList.setColumn(i, "BIGO01",   list.get(i).getBigo01());
				dsList.setColumn(i, "REPL01",   list.get(i).getRepl01());
				dsList.setColumn(i, "FINDAT",   list.get(i).getFindat());
				dsList.setColumn(i, "REQDAT",   list.get(i).getReqdat());
				dsList.setColumn(i, "RQSER",   list.get(i).getRqser());
				dsList.setColumn(i, "IS_LO",   list.get(i).getIs_lo());
				dsList.setColumn(i, "IS_DM",   list.get(i).getIs_dm());
				dsList.setColumn(i, "KUNNR_Z3",   list.get(i).getKunnr_z3());
				dsList.setColumn(i, "KUNNR_Z3_NM",   list.get(i).getKunnr_z3_nm());
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
