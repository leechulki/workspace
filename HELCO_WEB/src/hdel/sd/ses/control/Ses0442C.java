package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0442S;
import hdel.sd.ses.domain.Ses0442;
import hdel.sd.ses.domain.Ses0442ParamVO;

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
@RequestMapping("ses0442")
public class Ses0442C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0442S service;
	
	@RequestMapping(value="find")
	public View Ses0442FindListHeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");	// UI로 return할 out dataset  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Ses0442ParamVO param = new Ses0442ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setPt_num(DatasetUtility.getString(dsCond,"PT_NUM"));
			param.setGl_num(DatasetUtility.getString(dsCond,"GL_NUM") + "%");
			
			String gubun = DatasetUtility.getString(dsCond, "ZGUBUN");
			if ( gubun.equals("A") ) {
				param.setZgubun("14");	 //상해
			} else {
				param.setZgubun("15");   //브라질
			}
			//param.setZgubun(DatasetUtility.getString(dsCond, "ZGUBUN"));
			param.setZuse(DatasetUtility.getString(dsCond, "ZUSE"));
			System.out.print(param.getGl_num());
			List<Ses0442> list = service.getListHeader(param);   			// 서비스CALL
			dsList.deleteAll();
			
			float inrate = 0;
			inrate = service.selectinexrate(param);
			System.out.print(inrate);
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int i = 0; i < list.size(); i++) {

				dsList.appendRow(); 	// 행추가
			    
				dsList.setColumn(i, "MANDT",   list.get(i).getMandt());
				dsList.setColumn(i, "PT_NUM",  list.get(i).getPt_num());
				dsList.setColumn(i, "GL_NUM",  list.get(i).getGl_num());
				dsList.setColumn(i, "PT_NAME", list.get(i).getPt_name());
				dsList.setColumn(i, "ZSPEC",   list.get(i).getZspec());
				dsList.setColumn(i, "ZSIZE",   list.get(i).getZsize());
				dsList.setColumn(i, "AMOUNT",  list.get(i).getAmount());
				dsList.setColumn(i, "INRATE",  inrate);
				dsList.setColumn(i, "WAERS",   list.get(i).getWaers());				
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
/*
	@RequestMapping(value="findListDetail")
	public View Ses0442FindListDetail(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_detail");	// UI로 return할 out dataset  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
		try
		{ 
			Ses0442ParamVO param = new Ses0442ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setPspid(DatasetUtility.getString(dsCond, "PSPID"));
			param.setSeq(DatasetUtility.getString(dsCond, "SEQ"));

			
			List<Ses0442> list = service.getListDetail(param);  	// 서비스CALL
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
	*/

}
