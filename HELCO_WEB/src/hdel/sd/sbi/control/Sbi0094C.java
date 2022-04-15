package hdel.sd.sbi.control;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sbi.domain.ZSDT1141;
import hdel.sd.sbi.domain.ZSDT1142;
import hdel.sd.sbi.service.Sbi0094S;

/**
 * 브랜드 영업사양특성, 특성값 배치 등록 Controller 클래스
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
@RequestMapping("sbi0094c")
public class Sbi0094C {

	// 로그선언
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

	@Autowired
	private Sbi0094S sbi0094S;

	/** 
	 * 브랜드 영업특성, 특성값 저장
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="saveBatchSayang")
	public View saveBatchSayang(MipParameterVO paramVO, Model model) {
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
			List<ZSDT1141> iuZsdt1141 = new ArrayList<ZSDT1141>();
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
					iuZsdt1141.add(zsdt1141);
				} else if("U".equals(flag)) {
					iuZsdt1141.add(zsdt1141);
				}
			}

			// 영업특성값 입력 생성
			List<ZSDT1142> deleteZsdt1142 = new ArrayList<ZSDT1142>();
			List<ZSDT1142> iuZsdt1142 = new ArrayList<ZSDT1142>();
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
					iuZsdt1142.add(zsdt1142);
				} else if("U".equals(flag)) {
					iuZsdt1142.add(zsdt1142);
				}
			}

			sbi0094S.setSqlSessionBySysid(gSysid);
			logger.debug("deleteZsdt1141:"+deleteZsdt1141);
			logger.debug("iuZsdt1141:"+iuZsdt1141);
			logger.debug("deleteZsdt1142:"+deleteZsdt1142);
			logger.debug("iuZsdt1142:"+iuZsdt1142);
			// 영업사양 특성과 특성값을 저장한다.
			sbi0094S.saveBatchSayang(deleteZsdt1141, iuZsdt1141
					                ,deleteZsdt1142, iuZsdt1142);

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
