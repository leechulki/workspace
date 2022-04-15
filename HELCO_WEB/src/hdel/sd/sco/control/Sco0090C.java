package hdel.sd.sco.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CommRfcException;
import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import tit.util.DatasetUtility;
import webservice.stub.eai.EAIStub;
import webservice.stub.eai.ZWEB_SD_CHN_113Stub;
import webservice.stub.eai.ZWEB_SD_CHN_113Stub.TABLE_OF_ZPSSEMSG;
import webservice.stub.eai.ZWEB_SD_CHN_113Stub.TABLE_OF_ZSDS0028;

@Controller
@RequestMapping("sco0090")
public class Sco0090C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@RequestMapping(value="find")
	public View Sco0090FindView(MipParameterVO paramVO, Model model) {
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");

		Dataset ds = null;
		Dataset dsError = null;
		
		dsList.deleteAll();
		try { 
    		ZWEB_SD_CHN_113Stub stub = (ZWEB_SD_CHN_113Stub) new EAIStub(
    				paramVO.getVariable("G_SYSID"),
    				paramVO.getVariable("G_MANDT"),
    				paramVO.getVariable("G_LANG")).create(ZWEB_SD_CHN_113Stub.class);
    		ZWEB_SD_CHN_113Stub.ZWEB_SD_CHN_113 param;
    		param = new ZWEB_SD_CHN_113Stub.ZWEB_SD_CHN_113();
    		param.setI_FR_YM(ZWEB_SD_CHN_113Stub.Char8.Factory.fromString(DatasetUtility.getString(dsCond, "FR_YM"), ""));
    		param.setI_TO_YM(ZWEB_SD_CHN_113Stub.Char8.Factory.fromString(DatasetUtility.getString(dsCond, "TO_YM"), ""));
    		param.setI_FR_SO(ZWEB_SD_CHN_113Stub.Char10.Factory.fromString(DatasetUtility.getString(dsCond, "FR_SO"), ""));
    		param.setI_TO_SO(ZWEB_SD_CHN_113Stub.Char10.Factory.fromString(DatasetUtility.getString(dsCond, "TO_SO"), ""));
    		param.setI_FR_VB(ZWEB_SD_CHN_113Stub.Char4.Factory.fromString(DatasetUtility.getString(dsCond, "FR_VB"), ""));
    		param.setI_TO_VB(ZWEB_SD_CHN_113Stub.Char4.Factory.fromString(DatasetUtility.getString(dsCond, "TO_VB"), ""));
    		param.setI_FR_VG(ZWEB_SD_CHN_113Stub.Char3.Factory.fromString(DatasetUtility.getString(dsCond, "FR_VG"), ""));
    		param.setI_TO_VG(ZWEB_SD_CHN_113Stub.Char3.Factory.fromString(DatasetUtility.getString(dsCond, "TO_VG"), ""));
    		param.setI_SMAN(ZWEB_SD_CHN_113Stub.Char10.Factory.fromString(DatasetUtility.getString(dsCond, "SMAN"), ""));
    		param.setI_BUYR(ZWEB_SD_CHN_113Stub.Char10.Factory.fromString(DatasetUtility.getString(dsCond, "BUYR"), ""));
    		param.setI_BSTNK(ZWEB_SD_CHN_113Stub.Char30.Factory.fromString(DatasetUtility.getString(dsCond, "BSTNK"), ""));
    		param.setI_AUART(ZWEB_SD_CHN_113Stub.Char4.Factory.fromString(DatasetUtility.getString(dsCond, "AUART"), ""));
    		param.setI_SPART(ZWEB_SD_CHN_113Stub.Char2.Factory.fromString(DatasetUtility.getString(dsCond, "SPART"), ""));
    		param.setI_STADA(ZWEB_SD_CHN_113Stub.Char8.Factory.fromString(DatasetUtility.getString(dsCond, "STADA"), ""));
    		param.setI_GUBUN(ZWEB_SD_CHN_113Stub.Char1.Factory.fromString(DatasetUtility.getString(dsCond, "GUBUN"), ""));

    		TABLE_OF_ZPSSEMSG table_of_zpssmesg = new TABLE_OF_ZPSSEMSG();
    		TABLE_OF_ZSDS0028 table_of_zsds0028 = new TABLE_OF_ZSDS0028();
    		param.setT_ITAB(table_of_zsds0028);
    		param.setO_ETAB(table_of_zpssmesg);

    		ZWEB_SD_CHN_113Stub.ZWEB_SD_CHN_113Response response;
    		response = stub.zWEB_SD_CHN_113(param);

			ZWEB_SD_CHN_113Stub.ZSDS0028 arrZsds0028[] = response.getT_ITAB().getItem();
			if(arrZsds0028 != null) {
				for(ZWEB_SD_CHN_113Stub.ZSDS0028 zsds0028 : arrZsds0028) {
					dsList.appendRow();
					DatasetUtil.moveObjToDsRow(zsds0028, dsList, dsList.getRowCount()-1);
				}
			}

		} catch (CommRfcException e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_113 CommRfcException ERROR!!!" + "\n");
			e.printStackTrace();
		} catch (Exception e) { 
			System.out.print("\n@@@ ZWEB_SD_CHN_113 Exception ERROR!!!" + "\n");
			e.printStackTrace();
		}	 
		
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsList); 
		model.addAttribute("resultVO", resultVO);  
		
		return view;
	}
}