package hdel.sd.sbi.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import hdel.sd.sbi.domain.ZSDT1143;
import hdel.sd.sbi.service.Sbi0093S;

/**
 * 특성표출 그룹 Controller 클래스
 * 
 * @author  박수근
 * @since 2020.09.01
 * @version 1.0
 * @see 
 * <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *   2020.09.01         박수근          최초 생성
 * 
 * </pre>
 */

@Controller
@RequestMapping("sbi0093c")
public class Sbi0093C {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private Sbi0093S sbi0093S;
	
	/** 
	 * 특성그룹 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findZsdt1143List")
	public View findZsdt1143List(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset  dsCond  = paramVO.getDataset("ds_cond");
		Dataset  dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset  dsZsdt1143 = paramVO.getDataset("ds_zsdt1143");
		
		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		
		try {
			// 브랜드 차수 적용 내역 조회
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("MANDT", gMandt);
			paramMap.put("ZPRDGBN", dsCond.getColumnAsString(0, "ZPRDGBN"));
			paramMap.put("GRPPRH", dsCond.getColumnAsString(0, "GRPPRH"));

			sbi0093S.setSqlSessionBySysid(gSysid);
			List<ZSDT1143> zSdt1143List = sbi0093S.selectZsdt1143List(paramMap);

			// ZSDT1143List를 데이터셋으로 변경한다.
			DatasetUtil.moveListToDs(zSdt1143List, dsZsdt1143);
			logger.debug("dsZsdt1143:"+dsZsdt1143);

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
	
	/** 
	 * 브랜드 소그룹 타이틀 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="saveZsdt1143")
	public View saveZsdt1143(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsZsdt1143 = paramVO.getDataset("ds_zsdt1143");

		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		String gUserId = paramVO.getVariable("G_USER_ID");
		try {
			List<ZSDT1143> deleteZsdt1143 = new ArrayList<ZSDT1143>();
			List<ZSDT1143> insertZsdt1143 = new ArrayList<ZSDT1143>();
			List<ZSDT1143> updateZsdt1143 = new ArrayList<ZSDT1143>();
			for(int i=0; i < dsZsdt1143.getRowCount(); i++) {
				String flag = dsZsdt1143.getColumnAsString(i, "FLAG");
				ZSDT1143 zsdt1143 = new ZSDT1143();
				zsdt1143.setMANDT(gMandt);
				zsdt1143.setZPRDGBN(dsZsdt1143.getColumnAsString(i, "ZPRDGBN"));
				zsdt1143.setGRPPRH(dsZsdt1143.getColumnAsString(i, "GRPPRH"));
				zsdt1143.setGRPDESC(dsZsdt1143.getColumnAsString(i, "GRPDESC"));
				zsdt1143.setGRPEDESC(dsZsdt1143.getColumnAsString(i, "GRPEDESC"));
				zsdt1143.setAENAM(gUserId);
				zsdt1143.setERNAM(gUserId);
				zsdt1143.setFLAG(flag);
				if("D".equals(flag)) {
					deleteZsdt1143.add(zsdt1143);
				} else if("I".equals(flag)) {
					insertZsdt1143.add(zsdt1143);
				} else if("U".equals(flag)) {
					updateZsdt1143.add(zsdt1143);
				}
			}
			sbi0093S.setSqlSessionBySysid(gSysid);
			sbi0093S.saveZsdt1143(deleteZsdt1143, insertZsdt1143, updateZsdt1143);
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
