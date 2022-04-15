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
import hdel.sd.com.domain.Com0300;
import hdel.sd.com.domain.Com0300ParamVO;
import hdel.sd.com.service.Com0300S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("com0300")
public class Com0300C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0300S service;
	
	@RequestMapping(value="find")
	public View Com0300FindView(MipParameterVO paramVO, Model model) {
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// OUTPUT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_list");
		
		try {
			
			Com0300ParamVO param = new Com0300ParamVO();
			
			// DAO 생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// 파라메터 SET
			param.setMandt(paramVO.getVariable("G_MANDT")); // SAP CLIENT
			param.setDealer_cd(DatasetUtility.getString(dsCond, "DEALER_CD")); // 담당자코드
			param.setDealer_name(DatasetUtility.getString(dsCond, "DEALER_NAME")); // 담당자명
			
			// 서비스 CALL
			List<Com0300> list = service.find(param);
			
			// 호출결과(list)를 데이터셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++ ) {
				
				// 행추가
				dsList.appendRow();
				
				// 컬럼SET
				for (int iCol=0;iCol<dsList.getColumnCount();iCol++) {
					dsList.setColumn(iRow, "USERCODE_B", list.get(iRow).getUsercode_b()); // 사업자번호
					dsList.setColumn(iRow, "LIFNR", list.get(iRow).getLifnr()); // 대리점명
					dsList.setColumn(iRow, "DEALER_CD", list.get(iRow).getDealer_cd()); // 담당자코드
					dsList.setColumn(iRow, "DEALER_NAME", list.get(iRow).getDealer_name()); // 담당자명
					
				}
			}
			// 데이터건수 INFO
			logger.debug("\n@@@ ds_list.getRowCount ===>" + dsList.getRowCount() + "\n"); 
			
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
