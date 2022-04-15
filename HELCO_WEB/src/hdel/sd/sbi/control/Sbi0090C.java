package hdel.sd.sbi.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sbi.domain.SanyangPrd;
import hdel.sd.sbi.domain.SanyangPrh;
import hdel.sd.sbi.domain.ZSDT1141;
import hdel.sd.sbi.domain.ZSDT1142;
import hdel.sd.sbi.service.Sbi0090S;

/**
 * 브랜드 등록 Controller 클래스
 * 
 * @author  박수근
 * @since 2020.08.20
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.08.20         박수근          최초 생성
 * 
 * </pre>
 */


@Controller
@RequestMapping("sbi0090c")
public class Sbi0090C {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private Sbi0090S sbi0090S;

	/** 
	 * 브랜드등록 화면 공통코드/초기값 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="initSbi0090")
	public View initSbi0090(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		String gSysid  = paramVO.getVariable("G_SYSID");

		try {
			sbi0090S.setSqlSessionBySysid(gSysid);
			sbi0090S.initSbi0090(paramVO, resultVO);
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

	/** 
	 * 브랜드차수별 브랜드코드
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findAbrnd")
	public View findAbrnd(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		String gSysid  = paramVO.getVariable("G_SYSID");
		try {
			sbi0090S.setSqlSessionBySysid(gSysid);
			sbi0090S.findAbrnd(paramVO, resultVO);
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

	/** 
	 * 브랜드 영업특성 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findZsdt1141List")
	public View findZsdt1141List(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsSanyangPrh = paramVO.getDataset("ds_sanyangPrh");
		
		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<Map<String, String>> paramMapList = new ArrayList<Map<String, String>>();
			String brndcd  = dsCond.getColumnAsString(0, "BRNDCD");
			String[] brndcds = brndcd.split(",");
			
			paramMap.put("MANDT", gMandt);
			paramMap.put("BRNDSEQ", dsCond.getColumnAsString(0, "BRNDSEQ"));
			paramMap.put("ZPRDGBN", dsCond.getColumnAsString(0, "ZPRDGBN"));
			for(int i=0; i < brndcds.length; i++) {
				Map<String, String> brndcdMap = new HashMap<String, String>();
				brndcdMap.put("BRNDCD", brndcds[i].trim());
				paramMapList.add(brndcdMap);
			}
			paramMap.put("brndcdList", paramMapList);
			paramMap.put("ATKLA", dsCond.getColumnAsString(0, "ATKLA"));
			paramMap.put("PRH", dsCond.getColumnAsString(0, "PRH"));
			paramMap.put("PRHNAME", dsCond.getColumnAsString(0, "PRHNAME"));
			sbi0090S.setSqlSessionBySysid(gSysid);
			List<SanyangPrh> zSdt1141List = sbi0090S.findZsdt1141List(paramMap);
			DatasetUtil.moveListToDs(zSdt1141List, dsSanyangPrh);
			resultVO.addDataset(dsSanyangPrh); 
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

	/** 
	 * 브랜드 영업특성값 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findZsdt1142List")
	public View findZsdt1142List(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsSanyangPrd = paramVO.getDataset("ds_sanyangPrd");
		
		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<Map<String, String>> paramMapList = new ArrayList<Map<String, String>>();
			String brndcd  = dsCond.getColumnAsString(0, "BRNDCD");
			String[] brndcds = brndcd.split(",");
			
			paramMap.put("MANDT", gMandt);
			paramMap.put("BRNDSEQ", dsCond.getColumnAsString(0, "BRNDSEQ"));
			paramMap.put("ZPRDGBN", dsCond.getColumnAsString(0, "ZPRDGBN"));
			if( brndcds != null) {
				for(int i=0; i < brndcds.length; i++) {
					Map<String, String> brndcdMap = new HashMap<String, String>();
					brndcdMap.put("BRNDCD", brndcds[i].trim());
					paramMapList.add(brndcdMap);
				}
				paramMap.put("brndcdList", paramMapList);
			}
			paramMap.put("PRH", dsCond.getColumnAsString(0, "PRH"));
			
			sbi0090S.setSqlSessionBySysid(gSysid);
			List<SanyangPrd> zSdt1142List = sbi0090S.findZsdt1142List(paramMap);
			DatasetUtil.moveListToDs(zSdt1142List, dsSanyangPrd);
			resultVO.addDataset(dsSanyangPrd); 			
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
	
	/** 
	 * 브랜드 엑셀다운로드 영업특성값 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="excelZsdt1142List")
	public View excelZsdt1142List(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsExcelSanyangPrd = paramVO.getDataset("ds_excel_sanyangPrd");
		
		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<Map<String, String>> paramMapList = new ArrayList<Map<String, String>>();
			String brndcd  = dsCond.getColumnAsString(0, "BRNDCD");
			String[] brndcds = brndcd.split(",");
			
			paramMap.put("MANDT", gMandt);
			paramMap.put("BRNDSEQ", dsCond.getColumnAsString(0, "BRNDSEQ"));
			paramMap.put("ZPRDGBN", dsCond.getColumnAsString(0, "ZPRDGBN"));
			if( brndcds != null) {
				for(int i=0; i < brndcds.length; i++) {
					Map<String, String> brndcdMap = new HashMap<String, String>();
					brndcdMap.put("BRNDCD", brndcds[i].trim());
					paramMapList.add(brndcdMap);
				}
				paramMap.put("brndcdList", paramMapList);
			}
			paramMap.put("ATKLA", dsCond.getColumnAsString(0, "ATKLA"));
			paramMap.put("PRH", dsCond.getColumnAsString(0, "PRH"));

			sbi0090S.setSqlSessionBySysid(gSysid);
			List<SanyangPrd> zSdt1142List = sbi0090S.excelZsdt1142List(paramMap);
			DatasetUtil.moveListToDs(zSdt1142List, dsExcelSanyangPrd);
			resultVO.addDataset(dsExcelSanyangPrd); 			
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

	/** 
	 * 브랜드 영업특성, 특성값 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="saveSayang")
	public View saveSayang(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsZsdt1141 = paramVO.getDataset("ds_zsdt1141");
		Dataset dsZsdt1142 = paramVO.getDataset("ds_zsdt1142");

		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		String gUserId = paramVO.getVariable("G_USER_ID");
		try {
			// 영업특성 입력 생성
			List<ZSDT1141> deleteZsdt1141 = new ArrayList<ZSDT1141>();
			List<ZSDT1141> insertZsdt1141 = new ArrayList<ZSDT1141>();
			List<ZSDT1141> updateZsdt1141 = new ArrayList<ZSDT1141>();
			for(int i=0; i < dsZsdt1141.getRowCount(); i++) {
				String flag = dsZsdt1141.getColumnAsString(i, "FLAG");
				ZSDT1141 zsdt1141 = new ZSDT1141();
				zsdt1141.setMANDT(gMandt);
				zsdt1141.setBRNDSEQ(dsZsdt1141.getColumnAsString(i, "BRNDSEQ"));
				zsdt1141.setZPRDGBN(dsZsdt1141.getColumnAsString(i, "ZPRDGBN"));
				zsdt1141.setBRNDCD(dsZsdt1141.getColumnAsString(i, "BRNDCD"));
				zsdt1141.setPRH(dsZsdt1141.getColumnAsString(i, "PRH"));
				zsdt1141.setDISPTP(dsZsdt1141.getColumnAsString(i, "DISPTP"));
				zsdt1141.setMODITP(dsZsdt1141.getColumnAsString(i, "MODITP"));
				zsdt1141.setEGISSND(dsZsdt1141.getColumnAsString(i, "EGISSND"));
				zsdt1141.setDIPSDAT(dsZsdt1141.getColumnAsString(i, "DIPSDAT"));
				zsdt1141.setAENAM(gUserId);
				zsdt1141.setERNAM(gUserId);
				zsdt1141.setFLAG(flag);
				if("D".equals(flag)) {
					deleteZsdt1141.add(zsdt1141);
				} else if("I".equals(flag)) {
					insertZsdt1141.add(zsdt1141);
				} else if("U".equals(flag)) {
					updateZsdt1141.add(zsdt1141);
				}
			}

			// 영업특성값 입력 생성
			List<ZSDT1142> deleteZsdt1142 = new ArrayList<ZSDT1142>();
			List<ZSDT1142> insertZsdt1142 = new ArrayList<ZSDT1142>();
			List<ZSDT1142> updateZsdt1142 = new ArrayList<ZSDT1142>();
			for(int i=0; i < dsZsdt1142.getRowCount(); i++) {
				String flag = dsZsdt1142.getColumnAsString(i, "FLAG");
				ZSDT1142 zsdt1142 = new ZSDT1142();
				zsdt1142.setMANDT(gMandt);
				zsdt1142.setBRNDSEQ(dsZsdt1142.getColumnAsString(i, "BRNDSEQ"));
				zsdt1142.setZPRDGBN(dsZsdt1142.getColumnAsString(i, "ZPRDGBN"));
				zsdt1142.setBRNDCD(dsZsdt1142.getColumnAsString(i, "BRNDCD"));
				zsdt1142.setPRH(dsZsdt1142.getColumnAsString(i, "PRH"));
				zsdt1142.setPRD(dsZsdt1142.getColumnAsString(i, "PRD"));
				zsdt1142.setDISPTP(dsZsdt1142.getColumnAsString(i, "DISPTP"));
				zsdt1142.setDIPSDAT(dsZsdt1142.getColumnAsString(i, "DIPSDAT"));
				zsdt1142.setAENAM(gUserId);
				zsdt1142.setERNAM(gUserId);
				zsdt1142.setFLAG(flag);

				if("D".equals(flag)) {
					deleteZsdt1142.add(zsdt1142);
				} else if("I".equals(flag)) {
					insertZsdt1142.add(zsdt1142);
				} else if("U".equals(flag)) {
					updateZsdt1142.add(zsdt1142);
				}
			}

			sbi0090S.setSqlSessionBySysid(gSysid);
			logger.debug("deleteZsdt1141:"+deleteZsdt1141);
			logger.debug("insertZsdt1141:"+insertZsdt1141);
			logger.debug("updateZsdt1141:"+updateZsdt1141);
			logger.debug("deleteZsdt1142:"+deleteZsdt1142);
			logger.debug("insertZsdt1142:"+insertZsdt1142);
			logger.debug("updateZsdt1142:"+updateZsdt1142);

			// 영업사양 특성과 특성값을 저장한다.
			sbi0090S.saveSayang(deleteZsdt1141, insertZsdt1141, updateZsdt1141
					           ,deleteZsdt1142, insertZsdt1142, updateZsdt1142);
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
