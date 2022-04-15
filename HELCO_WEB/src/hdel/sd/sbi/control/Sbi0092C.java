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
import hdel.sd.sbi.domain.ZSDT1140;
import hdel.sd.sbi.service.Sbi0092S;

/**
 * 브랜드 적용일자 등록 Controller 클래스
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
@RequestMapping("sbi0092c")
public class Sbi0092C {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());
	
	private final String LANG_KO = "ko";
	private final String LANG_EN = "en";
	private final String SPRAS_KO = "3";
	private final String SPRAS_EN = "E";
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private Sbi0092S sbi0092S;

	
	/** 
	 * 브랜드 적용일자 초기값 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="initSbi0092")
	public View initSbi0092(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		String gSysid  = paramVO.getVariable("G_SYSID");

		try {
			sbi0092S.setSqlSessionBySysid(gSysid);
			sbi0092S.initSbi0092(paramVO, resultVO);
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
	 * 브랜드 적용일자 조회
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findZsdt1140List")
	public View findZsdt1140List(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsZsdt1140 = paramVO.getDataset("ds_zsdt1140");

		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		String brndseq = paramVO.getVariable("BRNDSEQ");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("MANDT", gMandt);
			paramMap.put("BRNDSEQ", brndseq);
			sbi0092S.setSqlSessionBySysid(gSysid);
			List<ZSDT1140> zSdt1140List = sbi0092S.findZsdt1140List(paramMap);
			
			DatasetUtil.moveListToDs(zSdt1140List, dsZsdt1140);
			resultVO.addDataset(dsZsdt1140); 
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
	 * 브랜드 적용일자 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="saveZsdt1140")
	public View saveZsdt1140(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsZsdt1140 = paramVO.getDataset("ds_zsdt1140");

		String gSysid  = paramVO.getVariable("G_SYSID");
		String gMandt  = paramVO.getVariable("G_MANDT");
		String gUserId = paramVO.getVariable("G_USER_ID");
		String gspras;
		try {
			List<ZSDT1140> deleteZsdt1140 = new ArrayList<ZSDT1140>();
			List<ZSDT1140> insertZsdt1140 = new ArrayList<ZSDT1140>();
			List<ZSDT1140> updateZsdt1140 = new ArrayList<ZSDT1140>();
			
			// 2020.12.15 영문코드정보 반영
			if ("KO".equals( paramVO.getVariable("G_LANG").toUpperCase() ) )	{
				gspras = SPRAS_KO;
			}else	{
				gspras = SPRAS_EN;
			}
			
			for(int i=0; i < dsZsdt1140.getRowCount(); i++) {
				String flag = dsZsdt1140.getColumnAsString(i, "FLAG");
				ZSDT1140 zsdt1140 = new ZSDT1140();
				zsdt1140.setMANDT(gMandt);
				zsdt1140.setBRNDSEQ(dsZsdt1140.getColumnAsString(i, "BRNDSEQ"));
				zsdt1140.setZPRDGBN(dsZsdt1140.getColumnAsString(i, "ZPRDGBN"));
				zsdt1140.setBRNDCD(dsZsdt1140.getColumnAsString(i, "BRNDCD"));
				zsdt1140.setFRDAT(dsZsdt1140.getColumnAsString(i, "FRDAT"));
				zsdt1140.setTODAT(dsZsdt1140.getColumnAsString(i, "TODAT"));
				zsdt1140.setSNDSYS(dsZsdt1140.getColumnAsString(i, "SNDSYS"));
				zsdt1140.setAENAM(gUserId);
				zsdt1140.setERNAM(gUserId);
				zsdt1140.setFLAG(flag);
				zsdt1140.setSPRAS(gspras);
				if("D".equals(flag)) {
					deleteZsdt1140.add(zsdt1140);
				} else if("I".equals(flag)) {
					insertZsdt1140.add(zsdt1140);
				} else if("U".equals(flag)) {
					updateZsdt1140.add(zsdt1140);
				}
			}
			sbi0092S.setSqlSessionBySysid(gSysid);
			sbi0092S.saveZsdt1140(deleteZsdt1140, insertZsdt1140, updateZsdt1140);
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
