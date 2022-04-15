package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.ZLang;
import hdel.sd.ses.domain.Ses0020;
import hdel.sd.ses.domain.Ses0020ParamVO;

import hdel.sd.ses.service.Ses0020S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

import hdel.sd.com.domain.ZLang;

@Controller
@RequestMapping("ses0020")
public class Ses0020C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0020S service;
	
	@RequestMapping(value="findTemp")
	public View Ses0020TempFindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_temp");	// UI로 return할 out dataset  

		dsList = templateToDataset(paramVO.getVariable("G_SYSID"), dsList, dsCond);

		try { 
			MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	@RequestMapping(value="find")
	public View Ses0020FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_temp");	// UI로 return할 out dataset  

		dsList = listToDataset(paramVO.getVariable("G_SYSID"), dsList, dsCond);

		try {
			MipResultVO resultVO = new MipResultVO();	// 출력 dataset을 return
			resultVO.addDataset(dsList); 
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}	
	
	@RequestMapping(value="save")
	public View Ses0020HeaderSave(MipParameterVO paramVO, Model model) {

		Dataset dsSave    = paramVO.getDataset("ds_save" );
		Dataset dsHeader = paramVO.getDataset("ds_head");

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET
		
		try {
			dsHeader = service.save(paramVO, model, session);
			MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
			resultVO.addDataset(dsSave);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	private Dataset listToDataset(String sysid, Dataset dsList, Dataset dsCond) {
		Ses0020ParamVO param = new Ses0020ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(sysid)); // DAO생성

		//param.setMandt(paramVO.getVariable("G_MANDT"));	
		String mandt = DatasetUtility.getString(dsCond,"MANDT" );
		String ztplno = DatasetUtility.getString(dsCond,"ZTPLNO" );
		String zprdgbn = DatasetUtility.getString(dsCond,"ZPRDGBN");
		String ztplgbn = DatasetUtility.getString(dsCond,"ZTPLGBN");
		String zrmk = DatasetUtility.getString(dsCond,"ZRMK");
		String spras = ZLang.convSapLang(DatasetUtility.getString(dsCond,"SPRAS"));
		// 2020 브랜드
		String brndcd = DatasetUtility.getString(dsCond,"BRNDCD");
		

		if (mandt  == null) mandt  = "";
		if (ztplgbn == null) ztplgbn = "";
		if (zrmk    == null) zrmk    = "";
		if (zprdgbn  == null) zprdgbn  = "";
		if (spras  == null) spras  = "3";

		// 2020 브랜드
		if(brndcd == null) {
			brndcd = "NOBRND";
		} else {
			if("".equals(brndcd)) {
				brndcd = "NOBRND";
		    }
		}
		
//		param.setZtplgbn(DatasetUtility.getString(dsCond, 0, "ZTPLGBN"));
//		param.setZrmk(DatasetUtility.getString(dsCond, 0, "ZRMK"));

		param.setMandt(mandt);
		param.setZprdgbn(zprdgbn);
		param.setZtplgbn(ztplgbn);
		param.setZrmk(zrmk);
		param.setSpras(spras);
		// 2020 브랜드
		param.setBrndcd(brndcd);
		
		if (ztplno == null) {
			List<Ses0020> minList = service.searchMinTemplate(param);
			ztplno = minList.get(0).getZtplno().toString();
		}

		param.setZtplno(ztplno);
		
		List<Ses0020> list = service.find(param); // 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MCLASS"  , list.get(i).getMclass());
			dsList.setColumn(i, "ATKLA"    , list.get(i).getAtkla());
			dsList.setColumn(i, "PRH"       , list.get(i).getPrh());
			dsList.setColumn(i, "PRD"       , list.get(i).getPrd());
			dsList.setColumn(i, "PRHNAME", list.get(i).getPrhname());
			dsList.setColumn(i, "ZTPLSEQ"  , list.get(i).getZtplseq());
			dsList.setColumn(i, "CNT"  , list.get(i).getCnt());
			dsList.setColumn(i, "ATFOR", list.get(i).getAtfor()); // 2013.03.07 추가
			
			// 2020 브랜드
			dsList.setColumn(i, "PCNCD"  , list.get(i).getPcncd());
			dsList.setColumn(i, "BRNDCD"  , list.get(i).getBrndcd());
			dsList.setColumn(i, "BRNDSEQ"  , list.get(i).getBrndseq());
			dsList.setColumn(i, "DISPTP"  , list.get(i).getDisptp());
			dsList.setColumn(i, "MODITP"  , list.get(i).getModitp());
		}

		return dsList;
	}	

	private Dataset templateToDataset(String sysid, Dataset dsList, Dataset dsCond) {

		Ses0020ParamVO param = new Ses0020ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(sysid)); // DAO생성

		param.setMandt(DatasetUtility.getString(dsCond,"MANDT"));
		param.setZprdgbn(DatasetUtility.getString(dsCond,"ZPRDGBN"));
		param.setSpras(ZLang.convSapLang(DatasetUtility.getString(dsCond,"SPRAS")));
		
		// 2020 브랜드
		if(DatasetUtility.getString(dsCond,"BRNDCD") == null) {
			param.setBrndcd("NOBRND");
		} else {
			if("".equals(DatasetUtility.getString(dsCond,"BRNDCD"))) {
			    param.setBrndcd("NOBRND");
		    } else {
		    	param.setBrndcd(DatasetUtility.getString(dsCond,"BRNDCD"));
		    }
		}

		List<Ses0020> list = service.findTemp(param);	// 서비스CALL
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {	// 호출결과(list)를 데이타셋(dsList)에 복사

			dsList.appendRow(); 	// 행추가
			dsList.setColumn(i, "MCLASS"  , list.get(i).getMclass());
			dsList.setColumn(i, "ATKLA"    , list.get(i).getAtkla());
			dsList.setColumn(i, "PRH"       , list.get(i).getPrh());
			dsList.setColumn(i, "PRD"       , list.get(i).getPrd());
			dsList.setColumn(i, "FLAG"      , list.get(i).getFlag());
			dsList.setColumn(i, "PRHNAME", list.get(i).getPrhname());
			dsList.setColumn(i, "ATFOR",   list.get(i).getAtfor());
			
			dsList.setColumn(i, "CNT"  , list.get(i).getCnt());
			dsList.setColumn(i, "ATFOR", list.get(i).getAtfor()); // 2013.03.07 추가
			
			// 2020 브랜드
			dsList.setColumn(i, "PCNCD"  , list.get(i).getPcncd());
			dsList.setColumn(i, "BRNDCD"  , list.get(i).getBrndcd());
			dsList.setColumn(i, "BRNDSEQ"  , list.get(i).getBrndseq());
			dsList.setColumn(i, "DISPTP"  , list.get(i).getDisptp());
			dsList.setColumn(i, "MODITP"  , list.get(i).getModitp());
		}
				
		return dsList;
	}
}
