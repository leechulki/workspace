package hdel.sd.com.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0330;
import hdel.sd.com.domain.Com0330ParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.com.service.Com0330S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("com0330")
public class Com0330C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0330S service;
	
	@RequestMapping(value="search")
	public View Ses0032HeaderView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list");// UI로 return할 out dataset  

		dsList = listToDataset(dsList, dsCond, paramVO);

		try {
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
		}

		return view;
	}

	private Dataset listToDataset(Dataset dsList, Dataset dsCond, MipParameterVO paramVO) {
		Com0330ParamVO param = new Com0330ParamVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		 
		String mandt = DatasetUtility.getString(dsCond,"MANDT");
		String frqtdat = DatasetUtility.getString(dsCond,"FR_QTDAT");
		String toqtdat = DatasetUtility.getString(dsCond,"TO_QTDAT");
		String zkunnr = DatasetUtility.getString(dsCond,"ZKUNNR");
		String sid = DatasetUtility.getString(dsCond,"SID");
		String dmstat = DatasetUtility.getString(dsCond,"DMSTAT");
		String zregn = DatasetUtility.getString(dsCond,"ZREGN");
		String bstdke = DatasetUtility.getString(dsCond,"BSTDK_E");
		String inspdt = DatasetUtility.getString(dsCond,"INSPDT");

		if (mandt == null) mandt = paramVO.getVariable("G_MANDT");
		if (frqtdat   == null) frqtdat = "";
		if (toqtdat   == null) toqtdat = "";
		if (zkunnr == null) zkunnr = "";
		if (sid == null) sid = "";
		if (dmstat == null) dmstat = "";
		if (zregn == null) zregn = "";
		if (bstdke == null) bstdke = "";
		if (inspdt == null) inspdt = "";
		
		param.setMandt(mandt);
		param.setZkunnr(zkunnr);
		param.setFrqtdat(frqtdat);
		param.setToqtdat(toqtdat);
		param.setSid(sid);
		param.setDmstat(dmstat);
		param.setZregn(zregn);
		param.setBstdke(bstdke);
		param.setInspdt(inspdt);

		List<Com0330> list = service.search(param);
		dsList.deleteAll();
		
		for (int i=0;i<list.size();i++) {
			dsList.appendRow();
			dsList.setColumn(i, "MANDT"  , list.get(i).getMANDT());
			dsList.setColumn(i, "SID"  , list.get(i).getSID());
			dsList.setColumn(i, "DMSTAT"  , list.get(i).getDMSTAT());
			dsList.setColumn(i, "KUNZ2"  , list.get(i).getKUNZ2());
			dsList.setColumn(i, "BSTDK_E"  , list.get(i).getBSTDK_E());
			dsList.setColumn(i, "WWMODT"  , list.get(i).getWWMODT());
			dsList.setColumn(i, "WWUSET"  , list.get(i).getWWUSET());
			dsList.setColumn(i, "WWFQ"  , list.get(i).getWWFQ());
			dsList.setColumn(i, "ZREGN"  , list.get(i).getZREGN());
			dsList.setColumn(i, "REGIO_E"  , list.get(i).getREGIO_E());
			dsList.setColumn(i, "ORT01_E"  , list.get(i).getORT01_E());
			dsList.setColumn(i, "STRAS"  , list.get(i).getSTRAS());
			dsList.setColumn(i, "INSPDT1"  , list.get(i).getINSPDT1());
			dsList.setColumn(i, "BDORD"  , list.get(i).getBDORD());
			dsList.setColumn(i, "BDADAT"  , list.get(i).getBDADAT());
			dsList.setColumn(i, "BDDDAT"  , list.get(i).getBDDDAT());
			dsList.setColumn(i, "WWPER"  , list.get(i).getWWPER());
			dsList.setColumn(i, "WWSPD"  , list.get(i).getWWSPD());
			dsList.setColumn(i, "REGION"  , list.get(i).getREGION());
		}
		return dsList;
	}
	
	
	
    @RequestMapping(value="CommonCode")
    public View Com0330CommonCode(MipParameterVO paramVO, Model model) {
        MipResultVO resultVO = new MipResultVO();
        Dataset     ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

        try {
        	service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
        	
        	Dataset ds_ara  = paramVO.getDataset("ds_ara");
        	Dataset ds_dmstat  = paramVO.getDataset("ds_dmstat");

        	Com0330ParamVO com0330ParamVO = new Com0330ParamVO();
        	com0330ParamVO.setMandt(paramVO.getVariable("G_MANDT"));

        	//지역
        	List<ComboVO> listAra = service.searchAra(com0330ParamVO);
        	ds_ara.deleteAll();
            for (int iRow=0;iRow<listAra.size();iRow++) {
            	ds_ara.appendRow();
            	ds_ara.setColumn(iRow, "CODE"        , listAra.get(iRow).getCode());  
            	ds_ara.setColumn(iRow, "CODE_NAME"    , listAra.get(iRow).getCodeName());
            }
            resultVO.addDataset(ds_ara);
            
        	//수요상태 
        	List<ComboVO> listDmstat = service.searchDmstat(com0330ParamVO);
        	ds_dmstat.deleteAll();
            for (int iRow=0;iRow<listDmstat.size();iRow++) {
            	ds_dmstat.appendRow();
            	ds_dmstat.setColumn(iRow, "CODE"        , listDmstat.get(iRow).getCode());
            	ds_dmstat.setColumn(iRow, "CODE_NAME"    , listDmstat.get(iRow).getCodeName());
            }
            resultVO.addDataset(ds_dmstat);

        } catch(Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
        	
        } finally {
			if( ds_error.getRowCount() > 0 ) {
				ds_error.setId("ds_error");
				resultVO.addDataset(ds_error);
			}			
			model.addAttribute("resultVO", resultVO);
        }
        return view;
    }
	
	
}
