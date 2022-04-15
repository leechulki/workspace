package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0085;
import hdel.sd.ses.domain.Ses0085ParamVO;
import hdel.sd.ses.service.Ses0085S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0085")
public class Ses0085C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0085S service;

	@RequestMapping(value="find")
	public View Ses0082View(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");	// UI로 return할 out dataset
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		try {
			dsList = detailToDataset(dsList, dsCond, paramVO);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			ds_error.setId("ds_error");
		}	    
		MipResultVO resultVO = new MipResultVO();	// 출력 dataset을 return
		resultVO.addDataset(dsList);

		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
	

	private Dataset detailToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {

		Ses0085ParamVO param = new Ses0085ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		param.setMandt(paramVO.getVariable("G_MANDT"));   // SAP CLIENT
		param.setZdev(DatasetUtility.getString(dsCond,"ZDEV"));
		param.setZgnm(DatasetUtility.getString(dsCond,"ZGNM"));
		param.setGsnam(DatasetUtility.getString(dsCond,"GSNAM"));
		param.setAdr(DatasetUtility.getString(dsCond,"ADR"));
		param.setFr_zapdat(DatasetUtility.getString(dsCond, "FR_ZAPDAT"));		// 2017.01.17 판정일자 조회 조건 추가 by mj.Shin 		
		param.setTo_zapdat(DatasetUtility.getString(dsCond, "TO_ZAPDAT"));
		param.setSid(DatasetUtility.getString(dsCond, "SID"));

		List<Ses0085> list = service.searchDetail(param);  	// 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) { // 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MANDT"    , list.get(i).getMandt());
			dsList.setColumn(i, "QTNUM"    , list.get(i).getQtnum());
			dsList.setColumn(i, "QTSER"      , list.get(i).getQtser()); 
			dsList.setColumn(i, "ZRQSEQ"      , list.get(i).getZrqseq()); 			
			dsList.setColumn(i, "SPART"    , list.get(i).getSpart()); 
			dsList.setColumn(i, "ZKUNNR_NM"    , list.get(i).getZkunnr_nm()); 
			dsList.setColumn(i, "ZDEV"        , list.get(i).getZdev()); 
			dsList.setColumn(i, "ZGNM"      , list.get(i).getZgnm()); 
			dsList.setColumn(i, "GSNAM"      , list.get(i).getGsnam()); 
			dsList.setColumn(i, "ZCID"      , list.get(i).getZcid()); 
			dsList.setColumn(i, "ZPID"      , list.get(i).getZpid()); 
			dsList.setColumn(i, "ZTEL",       list.get(i).getZtel());
			dsList.setColumn(i, "ZADDR_ADR1", list.get(i).getZaddr_adr1());
			dsList.setColumn(i, "ZADDR_ADR2", list.get(i).getZaddr_adr2());
			dsList.setColumn(i, "ZAPFLG",     list.get(i).getZapflg());
			dsList.setColumn(i, "ZAPDAT",     list.get(i).getZapdat());
			dsList.setColumn(i, "ZRQCN",      list.get(i).getZrqcn());
			dsList.setColumn(i, "ZPRGFLG",    list.get(i).getZprgflg());
			dsList.setColumn(i, "MAXQTSER_ZPRGFLG",    list.get(i).getMaxqtser_zprgflg()); // 최종차수 진행상태 추가 2021.06.15.
			dsList.setColumn(i, "ZRTRSN"     , list.get(i).getZrtrsn());
			dsList.setColumn(i, "UUSER"     , list.get(i).getUuser());
			dsList.setColumn(i, "QTGBN"   , list.get(i).getQtgbn()); 
			dsList.setColumn(i, "SID"   , list.get(i).getSid()); 
		}
				
		return dsList;
	}
}
