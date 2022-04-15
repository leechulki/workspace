package hdel.sd.com.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0150;
import hdel.sd.com.domain.Com0150ParamVO;
import hdel.sd.com.service.Com0150S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("com0150")
public class Com0150C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0150S service;
	
	@RequestMapping(value="find")
	public View Com0150FindView(MipParameterVO paramVO, Model model) {
		
		Dataset dsCond        = paramVO.getDataset("ds_cond_atnam"); // INPUT DATASET GET
		Dataset ds_list_atnam = paramVO.getDataset("ds_list_atnam");	// OUTPUT DATASET DECLARE UI로 return할 out dataset

		try {
			Com0150ParamVO param = new Com0150ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));	// DAO생성

/*			System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	        +"\n");  
			System.out.print("\n@@@ dsCond.KLART	===>"+DatasetUtility.getString(dsCond,"KLART")	+"\n");
			System.out.print("\n@@@ dsCond.CLASS1		===>"+DatasetUtility.getString(dsCond,"CLASS1")	+"\n");
			System.out.print("\n@@@ dsCond.ATKLA	===>"+DatasetUtility.getString(dsCond,"ATKLA")	+"\n");
			System.out.print("\n@@@ dsCond.ATNAM	===>"+DatasetUtility.getString(dsCond,"ATNAM")	+"\n");
			System.out.print("\n@@@ dsCond.WHERE	===>"+DatasetUtility.getString(dsCond,"WHERE")	+"\n");*/

			String lang = paramVO.getVariable("G_LANG");
			String class1 = DatasetUtility.getString(dsCond,"CLASS1");
			String spras = "3";

			if (lang.equals("ko") || class1.equals("SHN_01") || class1.equals("SHN_02"))
					spras = "3";
			else   spras = "E";
			
			// 2020 브랜드
			String brndcd = DatasetUtility.getString(dsCond,"BRNDCD");
			String brndseq = DatasetUtility.getString(dsCond,"BRNDSEQ");
			
			if( brndcd == null) {
				brndcd = "NOBRND";
			}
			
			if( brndseq == null) {
				brndseq = "000";
			}

			param.setMandt(paramVO.getVariable("G_MANDT"));  		// SAP CLIENT
			param.setKlart(DatasetUtility.getString(dsCond,"KLART"));		// 협력업체명 
			param.setClass1(class1);	//
			param.setBrndcd(brndcd);
			param.setBrndseq(brndseq);
			param.setAtkla(DatasetUtility.getString(dsCond,"ATKLA"));		// 
			param.setAtnam(DatasetUtility.getString(dsCond,"ATNAM"));	// 
			param.setWhere(DatasetUtility.getString(dsCond,"WHERE"));
			param.setSpras(spras);
			
			List<Com0150> list = service.find(param); // 서비스CALL

			ds_list_atnam.deleteAll();
			
			for (int i=0; i<list.size(); i++) { // 호출결과(list)를 데이타셋(ds_list_atnam)에 복사
				ds_list_atnam.appendRow(); // 행추가
				ds_list_atnam.setColumn(i, "CLASS1" , list.get(i).getClass1());
				ds_list_atnam.setColumn(i, "ATKLA"  , list.get(i).getAtkla());
				ds_list_atnam.setColumn(i, "ATNAM", list.get(i).getAtnam());
				ds_list_atnam.setColumn(i, "ATBEZ" , list.get(i).getAtbez());
				ds_list_atnam.setColumn(i, "ATINN" , list.get(i).getAtinn());
				ds_list_atnam.setColumn(i, "ATZHL" , list.get(i).getAtzhl());
				ds_list_atnam.setColumn(i, "ATWRT", list.get(i).getAtwrt());
				ds_list_atnam.setColumn(i, "ATWTB", list.get(i).getAtwtb());
				ds_list_atnam.setColumn(i, "ATSON", list.get(i).getAtson());
			}

			MipResultVO resultVO = new MipResultVO();	// RFC 출력 dataset을 return
			resultVO.addDataset(ds_list_atnam); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return view;
	}
}
