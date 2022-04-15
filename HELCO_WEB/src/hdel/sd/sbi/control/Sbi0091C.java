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
import hdel.sd.sbi.domain.ZSDT1139;
import hdel.sd.sbi.service.Sbi0091S;

/**
 * 브랜드차수 등록 Controller 클래스
 * 
 * @author  박수근
 * @since 2020.09.07
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.09.07         박수근          최초 생성
 * 
 * </pre>
 */

@Controller
@RequestMapping("sbi0091c")
public class Sbi0091C {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private Sbi0091S sbi0091S;
	
	/** 
	 * 브랜드 차수 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findZsdt1139List")
	public View findZsdt1139List(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsZsdt1139 = paramVO.getDataset("ds_zsdt1139");

		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		String brndseq = paramVO.getVariable("BRNDSEQ");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("MANDT", gMandt);
			paramMap.put("BRNDSEQ", brndseq);
			sbi0091S.setSqlSessionBySysid(gSysid);
			List<ZSDT1139> zSdt1139List = sbi0091S.findZsdt1139List(paramMap);
			
			DatasetUtil.moveListToDs(zSdt1139List, dsZsdt1139);
			resultVO.addDataset(dsZsdt1139); 
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
	 * 브랜드 차수 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="saveZsdt1139")
	public View saveZsdt1139(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsZsdt1139 = paramVO.getDataset("ds_zsdt1139");

		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		String gUserId = paramVO.getVariable("G_USER_ID");
		try {
			List<ZSDT1139> deleteZsdt1139 = new ArrayList<ZSDT1139>();
			List<ZSDT1139> insertZsdt1139 = new ArrayList<ZSDT1139>();
			List<ZSDT1139> updateZsdt1139 = new ArrayList<ZSDT1139>();
			for(int i=0; i < dsZsdt1139.getRowCount(); i++) {
				ZSDT1139 zsdt1139 = new ZSDT1139();
				zsdt1139.setMANDT(gMandt);
				zsdt1139.setBRNDSEQ(dsZsdt1139.getColumnAsString(i, "BRNDSEQ"));
				zsdt1139.setNOTE(dsZsdt1139.getColumnAsString(i, "NOTE"));
				zsdt1139.setAENAM(gUserId);
				zsdt1139.setERNAM(gUserId);
				
				String flag = dsZsdt1139.getColumnAsString(i, "FLAG");
				if("D".equals(flag)) {
					deleteZsdt1139.add(zsdt1139);
				} else if("I".equals(flag)) {
					insertZsdt1139.add(zsdt1139);
				} else if("U".equals(flag)) {
					updateZsdt1139.add(zsdt1139);
				}
			}
			sbi0091S.setSqlSessionBySysid(gSysid);
			sbi0091S.saveZsdt1139(deleteZsdt1139, insertZsdt1139, updateZsdt1139);
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
