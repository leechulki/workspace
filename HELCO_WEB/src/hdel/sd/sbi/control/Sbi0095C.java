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
import hdel.sd.sbi.domain.ZSDT1144;
import hdel.sd.sbi.service.Sbi0095S;

/**
 * 브랜드-기종 등록 Controller 클래스
 * 
 * @author  박수근
 * @since 2020.09.25
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.09.25         박수근          최초 생성
 * 
 * </pre>
 */

@Controller
@RequestMapping("sbi0095c")
public class Sbi0095C {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private Sbi0095S sbi0095S;
	
	/** 
	 * 브랜드-기종 초기값 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="initSbi0095")
	public View initSbi0095(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		String gSysid  = paramVO.getVariable("G_SYSID");

		try {
			sbi0095S.setSqlSessionBySysid(gSysid);
			sbi0095S.initSbi0095(paramVO, resultVO);
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
	 * 브랜드-기종 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findZsdt1144List")
	public View findZsdt1144List(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsZsdt1144 = paramVO.getDataset("ds_zsdt1144");

		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("MANDT", gMandt);
			sbi0095S.setSqlSessionBySysid(gSysid);
			List<ZSDT1144> zSdt1144List = sbi0095S.findZsdt1144List(paramMap);
			DatasetUtil.moveListToDs(zSdt1144List, dsZsdt1144);
			resultVO.addDataset(dsZsdt1144); 
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
	 * 브랜드-기종 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="saveZsdt1144")
	public View saveZsdt1144(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsZsdt1144 = paramVO.getDataset("ds_zsdt1144");

		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		String gUserId = paramVO.getVariable("G_USER_ID");
		try {
			List<ZSDT1144> deleteZsdt1144 = new ArrayList<ZSDT1144>();
			List<ZSDT1144> insertZsdt1144 = new ArrayList<ZSDT1144>();
			List<ZSDT1144> updateZsdt1144 = new ArrayList<ZSDT1144>();
			for(int i=0; i < dsZsdt1144.getRowCount(); i++) {
				ZSDT1144 zsdt1144 = new ZSDT1144();
				zsdt1144.setMANDT(gMandt);
				zsdt1144.setSEQ(dsZsdt1144.getColumnAsString(i, "SEQ"));
				zsdt1144.setBRNDCD(dsZsdt1144.getColumnAsString(i, "BRNDCD"));
				zsdt1144.setGTYPE(dsZsdt1144.getColumnAsString(i, "GTYPE"));
				zsdt1144.setAENAM(gUserId);
				zsdt1144.setERNAM(gUserId);

				String flag = dsZsdt1144.getColumnAsString(i, "FLAG");
				if("D".equals(flag)) {
					deleteZsdt1144.add(zsdt1144);
				} else if("I".equals(flag)) {
					insertZsdt1144.add(zsdt1144);
				} else if("U".equals(flag)) {
					updateZsdt1144.add(zsdt1144);
				}
			}
			sbi0095S.setSqlSessionBySysid(gSysid);
			sbi0095S.saveZsdt1144(deleteZsdt1144, insertZsdt1144, updateZsdt1144);
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
