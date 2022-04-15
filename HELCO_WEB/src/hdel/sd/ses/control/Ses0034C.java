package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0034;
import hdel.sd.ses.domain.Ses0034ParamVO;
import hdel.sd.ses.service.Ses0034S;

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
@RequestMapping("ses0034")
public class Ses0034C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0034S service;
	
	@RequestMapping(value="find")
	public View Ses0010FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");	// UI로 return할 out dataset
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 오류메세지 dataset

		MipResultVO resultVO = new MipResultVO(); // 출력 dataset을 return
		try { 

			dsList = listToDataset(dsList, dsCond, paramVO);

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

	private Dataset listToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) throws Exception{
		
		Ses0034ParamVO param = new Ses0034ParamVO();
		try
		{
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
	
			param.setMandt(DatasetUtility.getString(dsCond, "MANDT" ));	// SAP CLIENT
			param.setSearch(DatasetUtility.getString(dsCond,"SEARCH"));	// 
	
			List<Ses0034> list = service.searchDetail(param);  // 서비스CALL
			dsList.deleteAll();
			
			for (int i = 0; i < list.size(); i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사
	
				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "MANDT"   , list.get(i).getMandt());
				dsList.setColumn(i, "ZIPCODE"   , list.get(i).getZipcode()); 
				dsList.setColumn(i, "ADDR" , list.get(i).getAddr()); 
				
			}
				
			return dsList;
		}catch(Exception e){
			throw e;
		}
	}
}
