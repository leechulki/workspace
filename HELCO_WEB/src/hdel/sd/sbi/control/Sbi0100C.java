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
import hdel.sd.sbi.service.Sbi0100S;

/**
 * 브랜드 조회 Controller 클래스
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
@RequestMapping("sbi0100c")
public class Sbi0100C {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private Sbi0100S sbi0100S;

	/** 
	 * 브랜드 조회 화면 공통코드/초기값 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="initSbi0100")
	public View initSbi0100(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		String gSysid  = paramVO.getVariable("G_SYSID");

		try {
			sbi0100S.setSqlSessionBySysid(gSysid);
			sbi0100S.initSbi0100(paramVO, resultVO);
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
	 * 브랜드 영업사양 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findSayangList")
	public View findSayangList(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsSanyang = paramVO.getDataset("ds_sanyang");
		
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
			paramMap.put("PRHNAME", dsCond.getColumnAsString(0, "PRHNAME"));

			sbi0100S.setSqlSessionBySysid(gSysid);
			List<SanyangPrd> zSdt1142List = sbi0100S.findSayangList(paramMap);
			DatasetUtil.moveListToDs(zSdt1142List, dsSanyang);
			resultVO.addDataset(dsSanyang); 			
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
