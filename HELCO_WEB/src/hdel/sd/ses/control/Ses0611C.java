package hdel.sd.ses.control;

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
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0611;
import hdel.sd.ses.domain.Ses0611ParamVO;
import hdel.sd.ses.service.Ses0611S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0611")
public class Ses0611C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0611S service;

	
	/**
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View Ses0611FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond		= paramVO.getDataset("ds_cond");
		Dataset dsFile		= paramVO.getDataset("ds_file");
		Dataset dsError   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();
		
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Ses0611ParamVO param = new Ses0611ParamVO();
			param.setMANDT (DatasetUtility.getString(dsCond, "MANDT"));	
			param.setSID(DatasetUtility.getString(dsCond, "SID"));
			
			List<Ses0611> listFile = service.selectListFile(param);
			dsFile.deleteAll();
			CallSAPHdel.moveListToDs(dsFile, Ses0611.class, listFile);
			
			resultVO.addDataset(dsFile);
			
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
	 	
		return view;
	}


	/**
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="save")
	public View Ses0611SaveView(MipParameterVO paramVO, Model model) {
		Dataset dsFile    = paramVO.getDataset("ds_file");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			service.save(configureFileParamToSave(dsFile, paramVO));
			resultVO.addDataset(dsFile);
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}


	private List<Ses0611> configureFileParamToSave(Dataset dsFile, MipParameterVO paramVO) {
		List<Ses0611> listFile = new ArrayList<Ses0611>();
		for ( int i = 0 ; i < dsFile.getRowCount() ; i++ ) {
			Ses0611 paramFile = new Ses0611();
			paramFile.setMANDT(DatasetUtility.getString(dsFile, i, "MANDT"));
			paramFile.setSID(DatasetUtility.getString(dsFile, i, "SID"));
			paramFile.setATTSEQ(DatasetUtility.getString(dsFile, i, "ATTSEQ"));
			paramFile.setATTPATH(DatasetUtility.getString(dsFile, i, "ATTPATH"));
			paramFile.setATTFN(DatasetUtility.getString(dsFile, i, "ATTFN"));
			paramFile.setFLAG(DatasetUtility.getString(dsFile, i, "FLAG"));
			paramFile.setUUSER(paramVO.getVariable("G_USER_ID"));
			listFile.add(paramFile);
		}
		return listFile;
	}
}