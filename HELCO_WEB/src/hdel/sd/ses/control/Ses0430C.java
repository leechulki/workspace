package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0430S;
import hdel.sd.ses.domain.Ses0430;
import hdel.sd.ses.domain.Ses0430ParamVO;

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
@RequestMapping("ses0430")
public class Ses0430C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0430S service;
	
	@RequestMapping(value="findList")
	public View Ses0430FindListView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");					// UI로 return할 out dataset
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Ses0430ParamVO param = new Ses0430ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrzrqdat(DatasetUtility.getString(dsCond,"FR_ZRQDAT"));
			param.setTozrqdat(DatasetUtility.getString(dsCond,"TO_ZRQDAT"));
			param.setVkbur(DatasetUtility.getString(dsCond,"VKBUR"));
			param.setVkgrp(DatasetUtility.getString(dsCond,"VKGRP"));
			param.setZkunnr(DatasetUtility.getString(dsCond,"ZKUNNR"));
			param.setGubun(DatasetUtility.getString(dsCond,"GUBUN"));
			param.setQtso_no(DatasetUtility.getString(dsCond,"QTSO_NO"));

			List<Ses0430> list = service.getListHeader(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int i = 0; i < list.size(); i++) {

				dsList.appendRow(); 	// 행추가

				dsList.setColumn(i, "MANDT",   list.get(i).getMandt());
				dsList.setColumn(i, "ZZPJT_ID",   list.get(i).getZzpjt_id());
				dsList.setColumn(i, "ZRQSEQ", list.get(i).getZrqseq());
				dsList.setColumn(i, "KUNNR_Z3_NM", list.get(i).getKunnr_z3_nm());
				dsList.setColumn(i, "QTSO_NO", list.get(i).getQtso_no());
				dsList.setColumn(i, "QTSER",   list.get(i).getQtser());
				dsList.setColumn(i, "QTSEQ",   list.get(i).getQtseq());
				dsList.setColumn(i, "HOGI",   list.get(i).getHogi());
				dsList.setColumn(i, "QTDAT",   list.get(i).getQtdat());
				dsList.setColumn(i, "RECAD_DA",   list.get(i).getRecad_da());
				dsList.setColumn(i, "DELDAT",   list.get(i).getDeldat());
				dsList.setColumn(i, "DELGBN",   list.get(i).getDelgbn());
				dsList.setColumn(i, "LODAT",   list.get(i).getLodat());
				dsList.setColumn(i, "FINDAT",   list.get(i).getFindat());
				dsList.setColumn(i, "ZRQDAT",   list.get(i).getZrqdat());
				dsList.setColumn(i, "VKBUR",   list.get(i).getVkbur());
				dsList.setColumn(i, "VKGRP",   list.get(i).getVkgrp());
				dsList.setColumn(i, "ZKUNNR",  list.get(i).getZkunnr());
				dsList.setColumn(i, "ZKUNNR_NM", list.get(i).getZkunnr_nm());
				dsList.setColumn(i, "KUNNR",    list.get(i).getKunnr());
				dsList.setColumn(i, "KUNNR_NM", list.get(i).getKunnr_nm());
				dsList.setColumn(i, "GSNAM",    list.get(i).getGsnam()); 
				dsList.setColumn(i, "SPEC", 	list.get(i).getSpec()); 
				dsList.setColumn(i, "ZNUMBER",  list.get(i).getZnumber());;				
				dsList.setColumn(i, "LP_IS",  list.get(i).getLp_is());;				
				dsList.setColumn(i, "CHAI",  list.get(i).getChai());;
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
