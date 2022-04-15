package hdel.sd.com.control;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0320;
import hdel.sd.com.domain.Com0320ParamVO;
import hdel.sd.com.service.Com0320S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("com0320")
public class Com0320C {
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0320S service;
	
	@RequestMapping(value="find")
	public View Com0320FindView(MipParameterVO paramVO, Model model) {
		Logger logger = Logger.getLogger(this.getClass());
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// OUTPUT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_list");
		
		try {
			Com0320ParamVO param = new Com0320ParamVO();
			
			// DAO create
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// parameter set
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setLandCode(DatasetUtility.getString(dsCond, "LANDCODE"));
			param.setLandName(DatasetUtility.getString(dsCond, "LANDNAME"));
			
			// service CALL
			List<Com0320> findList = service.find(param);
			
			// 호출결과를 데이터셋에 복사
			for (int iRow=0 ; iRow<findList.size(); iRow++) {
				dsList.appendRow();
				
				for (int iCol=0; iCol<dsList.getColumnCount() ; iCol++) {
					dsList.setColumn(iRow, "LANDCODE", findList.get(iRow).getLandCode());
					dsList.setColumn(iRow, "LANDNAME", findList.get(iRow).getLandName());
				}
			}
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
}
