package hdel.sd.com.control;


import hdel.lib.control.SrmView;

import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO; 
import hdel.sd.com.domain.Com0180;
import hdel.sd.com.domain.Com0180ParamVO;
import hdel.sd.com.service.Com0180S;

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
 * 공사번호 조회 (Com0180C) Control
 * @Comment 
 *     	1. View find ( 공사번호 조회 )
 * @since 1.0
 * 		History
 * 		1.0  2012.08.01  김재영  :  initial version 
 * @version 1.0
 */

@Controller
@RequestMapping("com0180")
public class Com0180C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0180S service;
	
	// 공사번호 조회
	@RequestMapping(value="find")
	public View Com0040FindView(MipParameterVO paramVO, Model model) {

		Dataset ds_cond = paramVO.getDataset("ds_cond_zzpjt");  	// INPUT DATASET GET

		// OUTPUT DATASET DECLARE
		Dataset ds_list	 = paramVO.getDataset("ds_list_zzpjt");	 // UI로 return할 out dataset
		Dataset ds_error = paramVO.getDataset("ds_error_zzpjt"); // UI로 return할 오류메세지 dataset

		// INPUT PARAM INFO --------------------------------------------------------------------------------------------
		// sap client (개발 : 310 or 910)
		try {
			Com0180ParamVO param = new Com0180ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			param.setMandt	(paramVO.getVariable("G_MANDT"));  			// SAP CLIENT
			param.setZzpjt_id(DatasetUtility.getString(ds_cond,"ZZPJT_ID")); // 공사번호
			param.setBstnk   (DatasetUtility.getString(ds_cond,"BSTNK"  ));  // 공사명
			param.setErdat_fr(DatasetUtility.getString(ds_cond,"ERDAT_FR"  ));
			param.setErdat_to(DatasetUtility.getString(ds_cond,"ERDAT_TO"  ));

			List<Com0180> list = service.find(param);  	// 서비스CALL

			for (int i=0;i<list.size();i++)	 {	// 호출결과(list)를 데이타셋(dsList)에 복사
				ds_list.appendRow(); // 행추가
				ds_list.setColumn(i, "ZZPJT_ID", list.get(i).getZzpjt_id());	// 공사번호
				ds_list.setColumn(i, "BSTNK"   , list.get(i).getBstnk()   );	// 공사명
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
