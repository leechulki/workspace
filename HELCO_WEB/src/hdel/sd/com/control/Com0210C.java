package hdel.sd.com.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.com.domain.Com0210;
import hdel.sd.com.domain.Com0210ParamVO;
import hdel.sd.com.service.Com0210S;

import java.util.List;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility; 
import com.tobesoft.platform.data.Dataset;

import org.apache.log4j.Logger; 

/**
 * 수주계획번호 조회 (Com0210C) Control
 * @Comment 
 *     	1. View find ( 수주계획번호 조회 )
 * @since 1.0
 * 		History
 * 		1.0  2012.08.29  김재영  :  initial version 
 * @version 1.0
 */

@Controller
@RequestMapping("com0210")
public class Com0210C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0210S service;

	// 수주계획번호 조회
	@RequestMapping(value="find")
	public View Com0040FindView(MipParameterVO paramVO, Model model) {

		Dataset ds_cond = paramVO.getDataset("ds_cond_sonnr");  	// INPUT DATASET GET

		// OUTPUT DATASET DECLARE
		Dataset ds_list	 = paramVO.getDataset("ds_list_sonnr");	 // UI로 return할 out dataset
		Dataset ds_error = paramVO.getDataset("ds_error_sonnr"); // UI로 return할 오류메세지 dataset

		// INPUT PARAM INFO --------------------------------------------------------------------------------------------
		// sap client (개발 : 310 or 910)

		try {
			Com0210ParamVO param = new Com0210ParamVO();

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			param.setMandt	(paramVO.getVariable("G_MANDT"));  		  // SAP CLIENT
			param.setZpym  (DatasetUtility.getString(ds_cond,"ZPYM"));    // 수주년월
			param.setGsnam(DatasetUtility.getString(ds_cond,"GSNAM"));  // 공사명
			param.setZkunnr(DatasetUtility.getString(ds_cond,"ZKUNNR")); // 담당자ID

			List<Com0210> list = service.find(param);  	// 서비스CALL

			for (int i=0;i<list.size();i++)	 {	// 호출결과(list)를 데이타셋(dsList)에 복사
				ds_list.appendRow(); // 행추가
				ds_list.setColumn(i, "ZPYM"  , list.get(i).getZpym());   // 수주년월
				ds_list.setColumn(i, "SONNR", list.get(i).getSonnr());   // 수주계획번호
				ds_list.setColumn(i, "GSNAM", list.get(i).getGsnam()); // 공사명
			}

			MipResultVO resultVO = new MipResultVO();			// RFC 출력 dataset을 return
			resultVO.addDataset(ds_list);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}
}
