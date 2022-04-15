package hdel.sd.ses.control;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0610;
import hdel.sd.ses.domain.Ses0610ParamVO;
import hdel.sd.ses.service.Ses0610S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0610")
public class Ses0610C {
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0610S service;
	
	@RequestMapping(value="find")
	public View Ses0610FindView(MipParameterVO paramVO, Model model) {
		Logger logger = Logger.getLogger(this.getClass());
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		// OUTPUT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_list");
		
		try {
			Ses0610ParamVO param = new Ses0610ParamVO();
			
			// DAO create
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// parameter set
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setSid(DatasetUtility.getString(dsCond, "SID"));
			param.setBstdk_e(DatasetUtility.getString(dsCond, "BSTDK_E"));
			param.setWwfq(DatasetUtility.getString(dsCond, "WWFQ"));
			param.setZregn(DatasetUtility.getString(dsCond, "ZREGN"));
			param.setZkunnr(DatasetUtility.getString(dsCond, "ZKUNNR"));
			param.setFrInspdt("");
			param.setToInspdt("");
			param.setBasedate("");
			
			// service CALL
			List<Ses0610> findList = service.find(param);
			
			// 호출결과를 데이터셋에 복사
			for (int iRow=0 ; iRow<findList.size(); iRow++) {
				dsList.appendRow();
				
				for (int iCol=0 ; iCol<dsList.getColumnCount() ; iCol++) {
					dsList.setColumn(iRow, "SID", findList.get(iRow).getSid());
					dsList.setColumn(iRow, "BSTDK_E", findList.get(iRow).getBstdk_e());
					dsList.setColumn(iRow, "WWFQ", findList.get(iRow).getWwfq());
					dsList.setColumn(iRow, "ZREGN", findList.get(iRow).getZregn());
					dsList.setColumn(iRow, "ORT01_E", findList.get(iRow).getOrt01_e());
					dsList.setColumn(iRow, "STRAS", findList.get(iRow).getStras());
					dsList.setColumn(iRow, "INSPDT1", findList.get(iRow).getInspdt1());
					dsList.setColumn(iRow, "INSPDT2", findList.get(iRow).getInspdt2());
					dsList.setColumn(iRow, "INSPDT3", findList.get(iRow).getInspdt3());
					dsList.setColumn(iRow, "INSPDT4", findList.get(iRow).getInspdt4());
					dsList.setColumn(iRow, "INSPDTN", findList.get(iRow).getInspdtn());
					dsList.setColumn(iRow, "KUNZ2", findList.get(iRow).getKunz2());
					dsList.setColumn(iRow, "KUNZ2NAME", findList.get(iRow).getKunz2Name());
					dsList.setColumn(iRow, "BDADAT1", findList.get(iRow).getBdadat1());
					dsList.setColumn(iRow, "BDADAT2", findList.get(iRow).getBdadat2());
					dsList.setColumn(iRow, "BDDDAT", findList.get(iRow).getBdddat());
					dsList.setColumn(iRow, "MGNM", findList.get(iRow).getMgnm());
					dsList.setColumn(iRow, "MGTEL", findList.get(iRow).getMgtel());
					dsList.setColumn(iRow, "SFPRTCO", findList.get(iRow).getSfprtco());
					dsList.setColumn(iRow, "SBDCM", findList.get(iRow).getSbdcm());
					dsList.setColumn(iRow, "DMSTAT", findList.get(iRow).getDmstat());
					if(findList.get(iRow).getFilecnt() > 0){
						dsList.setColumn(iRow, "FILECNT", "Y");	
					}else dsList.setColumn(iRow, "FILECNT", "");
				}
			}
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	@RequestMapping(value="merge")
	public View updateZsdt1154(MipParameterVO paramVO, Model model) {
		List<Ses0610> listSes0610 = new ArrayList<Ses0610>();
		Dataset ds_save_list = paramVO.getDataset("ds_save_list");
		
		try {
			for (int i=0 ; i<ds_save_list.getRowCount() ; i++) {
				Ses0610 ses0610 = new Ses0610();
				DatasetUtil.moveDsRowToObj(ds_save_list, i, ses0610);
				ses0610.setMandt(paramVO.getVariable("G_MANDT"));
				ses0610.setSid(ds_save_list.getColumnAsString(i, "SID"));
				ses0610.setKunz2(ds_save_list.getColumnAsString(i, "KUNZ2"));
				ses0610.setBdadat1(ds_save_list.getColumnAsString(i, "BDADAT1"));
				ses0610.setBdadat2(ds_save_list.getColumnAsString(i, "BDADAT2"));
				ses0610.setBdddat(ds_save_list.getColumnAsString(i, "BDDDAT"));
				ses0610.setMgnm(ds_save_list.getColumnAsString(i, "MGNM"));
				ses0610.setMgtel(ds_save_list.getColumnAsString(i, "MGTEL"));
				ses0610.setSfprtco(ds_save_list.getColumnAsString(i, "SFPRTCO"));
				ses0610.setSbdcm(ds_save_list.getColumnAsString(i, "SBDCM"));
				ses0610.setDmstat(ds_save_list.getColumnAsString(i, "DMSTAT"));
				ses0610.getTstmp().setTimeStamp(paramVO.getVariable("G_USER_ID"));
				listSes0610.add(ses0610);
			}
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			service.merge(listSes0610);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value="matchkunz2")
	public View matchkunz2(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_kunz2");
		
		// OUTPUT DATASET DECLARE
		Dataset dsList = paramVO.getDataset("ds_list_kunz2");
		
		try {
			Ses0610ParamVO param = new Ses0610ParamVO();
			
			// DAO create
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// parameter set
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setZkunnr(DatasetUtility.getString(dsCond, "ZKUNNR"));
			
			// service CALL
			List<Ses0610> findList = service.findzsdt0222(param);
			
			dsList.appendRow();
			if (findList.isEmpty()) {
				dsList.setColumn(0, "ISKUNZ2", "N");
			} else {
				dsList.setColumn(0, "ISKUNZ2", "Y");
			}
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
