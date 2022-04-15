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
 * �����ȣ ��ȸ (Com0180C) Control
 * @Comment 
 *     	1. View find ( �����ȣ ��ȸ )
 * @since 1.0
 * 		History
 * 		1.0  2012.08.01  ���翵  :  initial version 
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
	
	// �����ȣ ��ȸ
	@RequestMapping(value="find")
	public View Com0040FindView(MipParameterVO paramVO, Model model) {

		Dataset ds_cond = paramVO.getDataset("ds_cond_zzpjt");  	// INPUT DATASET GET

		// OUTPUT DATASET DECLARE
		Dataset ds_list	 = paramVO.getDataset("ds_list_zzpjt");	 // UI�� return�� out dataset
		Dataset ds_error = paramVO.getDataset("ds_error_zzpjt"); // UI�� return�� �����޼��� dataset

		// INPUT PARAM INFO --------------------------------------------------------------------------------------------
		// sap client (���� : 310 or 910)
		try {
			Com0180ParamVO param = new Com0180ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			param.setMandt	(paramVO.getVariable("G_MANDT"));  			// SAP CLIENT
			param.setZzpjt_id(DatasetUtility.getString(ds_cond,"ZZPJT_ID")); // �����ȣ
			param.setBstnk   (DatasetUtility.getString(ds_cond,"BSTNK"  ));  // �����
			param.setErdat_fr(DatasetUtility.getString(ds_cond,"ERDAT_FR"  ));
			param.setErdat_to(DatasetUtility.getString(ds_cond,"ERDAT_TO"  ));

			List<Com0180> list = service.find(param);  	// ����CALL

			for (int i=0;i<list.size();i++)	 {	// ȣ����(list)�� ����Ÿ��(dsList)�� ����
				ds_list.appendRow(); // ���߰�
				ds_list.setColumn(i, "ZZPJT_ID", list.get(i).getZzpjt_id());	// �����ȣ
				ds_list.setColumn(i, "BSTNK"   , list.get(i).getBstnk()   );	// �����
			}
			
			MipResultVO resultVO = new MipResultVO();			// RFC ��� dataset�� return
			resultVO.addDataset(ds_list);
			model.addAttribute("resultVO", resultVO);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}
}
