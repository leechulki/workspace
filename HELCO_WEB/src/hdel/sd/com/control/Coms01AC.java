package hdel.sd.com.control;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;

import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.com.domain.Coms01A;
import hdel.sd.com.service.Coms01AS;
import hdel.sd.sbi.domain.ZSDT1143;
import hdel.sd.com.domain.BrndSayang;

@Controller
@RequestMapping("coms01a")
public class Coms01AC {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Coms01AS service;
	
	@RequestMapping(value="search")
	public View search(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDataset("ds_cond");  
		Dataset dsList = paramVO.getDataset("ds_list");  
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Coms01A coms01a = new Coms01A();
			String qtnum = dsCond.getColumnAsString(0, "QTNUM");
			coms01a.setMANDT(paramVO.getVariable("G_MANDT"));
			coms01a.setGTYPE(dsCond.getColumnAsString(0, "GTYPE"));
			if(qtnum == null) {
				coms01a.setQTNUM("");
			} else {
				coms01a.setQTNUM(dsCond.getColumnAsString(0, "QTNUM"));
			}
			coms01a.setZPRDGBN(dsCond.getColumnAsString(0, "ZPRDGBN"));
			coms01a.setBRNDCD(dsCond.getColumnAsString(0, "BRNDCD"));
			
			List<Coms01A> list = service.selectBrandGtypeList(coms01a);
			DatasetUtil.moveListToDs(list, dsList);

			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}	    
		return view;
	}

	@RequestMapping(value="brndFlag")
	public View brndFlag(MipParameterVO paramVO, Model model) {
		String mandt = paramVO.getVariable("G_SYSID");
		String qtnum = paramVO.getVariable("G_QTNUM");
		Dataset ds_brnd_flag = paramVO.getDataset("ds_brnd_flag");
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(mandt));
			Coms01A coms01a = new Coms01A();
			coms01a.setMANDT(paramVO.getVariable("G_MANDT"));
			if(qtnum == null) {
				coms01a.setQTNUM("");
			} else {
				coms01a.setQTNUM(qtnum);
			}
			
			String brandFlag = service.selectBrandFlag(coms01a);
			ds_brnd_flag.deleteAll();
			int nRow = ds_brnd_flag.appendRow();
			ds_brnd_flag.setColumn(nRow, "F_BRND_FLAG", brandFlag);
			
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_brnd_flag); 
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	// 2020 브랜드 영업특성코드 콤보 조회
	@RequestMapping(value="comboAtwrt")
	public View comboAtwrt(MipParameterVO paramVO, Model model) {
		Dataset ds_list =paramVO.getDataset("ds_list_atwrt");

		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		ComboParamVO param = new ComboParamVO();
		param.setMandt(paramVO.getVariable("G_MANDT"));				// 채널  
		param.setLang(paramVO.getVariable("G_LANG"));
		param.setVkbur(paramVO.getVariable("G_VKBUR"));
		
		// 서비스호출
		List<ComboVO> listCombo = service.selectAtwrt(param); 	// 오더유형코드, 오더유형명 조회

		// 호출결과(listCombo)를 데이타셋(ds_list)에 복사 
		ds_list.deleteAll(); 
		for (int iRow=0;iRow<listCombo.size();iRow++)
		{    
			ds_list.appendRow();   
			ds_list.setColumn(iRow, "CODE"		, listCombo.get(iRow).getCode()); 		// 코드
			ds_list.setColumn(iRow, "CODE_NAME"	, listCombo.get(iRow).getCodeName());	// 코드명 
		} 
		
		// dataset을 return
		MipResultVO resultVO = new MipResultVO();
		ds_list.setId		(ds_list.getId());  
		resultVO.addDataset	(ds_list);
		model.addAttribute("resultVO", resultVO);  
		return view;
	}

	// 브랜드별 영업특성값 조회
	@RequestMapping(value="sayangPrdList")
	public View sayangPrdList(MipParameterVO paramVO, Model model) {
		Dataset dsCond =paramVO.getDataset("ds_cond");
		Dataset dsSayangList = new Dataset("ds_brnd_prd");
		dsSayangList.addColumn("BRNDCD",(short)1,60);
		dsSayangList.addColumn("PRH",(short)1,60);
		dsSayangList.addColumn("PRD",(short)1,256);
		dsSayangList.addColumn("KEYIN",(short)1,1);
		
        String mandt = paramVO.getVariable("G_MANDT");
        String brndseq = dsCond.getColumnAsString(0, "BRNDSEQ");
        String zprdgbn = dsCond.getColumnAsString(0, "ZPRDGBN");
        String brndcd  = dsCond.getColumnAsString(0, "BRNDCD");

        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("MANDT", mandt);
        paraMap.put("BRNDSEQ", brndseq);
        paraMap.put("ZPRDGBN", zprdgbn);
        paraMap.put("BRNDCD", brndcd);

        service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		List<BrndSayang> sayanglist = service.sayangPrdList(paraMap);
		for (int iRow=0;iRow<sayanglist.size();iRow++)
		{    
			dsSayangList.appendRow();
			dsSayangList.setColumn(iRow, "BRNDCD", sayanglist.get(iRow).getBRNDCD());
			dsSayangList.setColumn(iRow, "PRH", sayanglist.get(iRow).getPRH());
			dsSayangList.setColumn(iRow, "PRD", sayanglist.get(iRow).getPRD());
			dsSayangList.setColumn(iRow, "KEYIN", sayanglist.get(iRow).getKEYIN());
		} 
		
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset	(dsSayangList);
		model.addAttribute("resultVO", resultVO);  
		return view;
	}

	/** 
	 * 소그룹 타이틀 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findGroupTitList")
	public View findGroupTitList(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset  dsCond  = paramVO.getDataset("ds_cond");
		Dataset  dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset  dsZsdt1143 = paramVO.getDataset("ds_zsdt1143");
		String gMandt  = paramVO.getVariable("G_MANDT");
		
		try {
			// 브랜드 차수 적용 내역 조회
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("MANDT", gMandt);
			paramMap.put("ZPRDGBN", dsCond.getColumnAsString(0, "ZPRDGBN"));

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			List<ZSDT1143> zSdt1143List = service.selectGroupTitList(paramMap);

			// ZSDT1143List를 데이터셋으로 변경한다.
			DatasetUtil.moveListToDs(zSdt1143List, dsZsdt1143);
			resultVO.addDataset(dsZsdt1143); 
		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error"); 
			resultVO.addDataset(dsError);
		} finally {
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}	
	
}
