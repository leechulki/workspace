package hdel.sd.com.control;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0290;
import hdel.sd.com.domain.Com0290ParamVO;
import hdel.sd.com.service.Com0290S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("com0290")
public class Com0290C {
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0290S service;
	
	@RequestMapping(value="find")
	public View Com0290FindView(MipParameterVO paramVO, Model model) {
		Logger logger = Logger.getLogger(this.getClass());
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// OUTPUT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_list");
		
		try {
			
			Com0290ParamVO param = new Com0290ParamVO();
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // global sysid를 가져와 sqlsession 넘김
			
			String vkbur = DatasetUtility.getString(dsCond, "VKBUR");
			String vkgrp = DatasetUtility.getString(dsCond, "VKGRP");
			String kunz2 = DatasetUtility.getString(dsCond, "KUNZ2");
			String fr_perdt = DatasetUtility.getString(dsCond, "FR_PERDT");
			String to_perdt = DatasetUtility.getString(dsCond, "TO_PERDT");
			String perno = DatasetUtility.getString(dsCond, "PERNO");
			String manag = DatasetUtility.getString(dsCond, "MANAG");
			String postn = DatasetUtility.getString(dsCond, "POSTN");
			String fr_chakd = DatasetUtility.getString(dsCond, "FR_CHAKD");
			String to_chakd = DatasetUtility.getString(dsCond, "TO_CHAKD");
			
			// 파라메터 SET
			param.setMandt(paramVO.getVariable("G_MANDT")); // SAP Client
			param.setVkbur(vkbur); // 사업장
			param.setVkgrp(vkgrp); // 영업 그룹
			param.setKunz2(kunz2); // 담당자
			param.setFr_perdt(fr_perdt); // 허가신고일 1
			param.setTo_perdt(to_perdt); // 허가신고일 2
			param.setPerno(perno); // 허가번호
			param.setManag(manag); // 관리기관
			param.setPostn(postn); // 대지위치
			param.setFr_chakd(fr_chakd); // 착공예정일 1
			param.setTo_chakd(to_chakd); // 착공예정일 2
			
			// 서비스 CALL
			List<Com0290> list = service.find(param);
			
			// 호출결과(list)를 데이터셋(dsList)에 복사
			for (int iRow = 0; iRow < list.size(); iRow++) {
				// 행추가
				dsList.appendRow();
				
				// 컬럼SET
				for (int iCol = 0; iCol < dsList.getColumnCount(); iCol++) {
					dsList.setColumn(iRow, "PERNO", list.get(iRow).getPerno());
					dsList.setColumn(iRow, "PERDT", list.get(iRow).getPerdt());
					dsList.setColumn(iRow, "MANAG", list.get(iRow).getManag());
					dsList.setColumn(iRow, "POSTN", list.get(iRow).getPostn());
					dsList.setColumn(iRow, "BNAME", list.get(iRow).getBname());
					dsList.setColumn(iRow, "ZUSE", list.get(iRow).getZuse());
				}
			}
			
			// 데이터건수 INFO
			logger.info("@@@@ ds_list.getRowCount ===>" + dsList.getRowCount());
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}

}
