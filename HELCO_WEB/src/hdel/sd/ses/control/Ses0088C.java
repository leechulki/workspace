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
import hdel.sd.ses.domain.Ses0088;
import hdel.sd.ses.domain.Ses0088ParamVO;
import hdel.sd.ses.service.Ses0088S;
import tit.util.DatasetUtility;

@Controller
@RequestMapping("ses0088")
public class Ses0088C {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0088S service;

	
	/**
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View Ses0401FindView(MipParameterVO paramVO, Model model) {
		
		// get Input Dataset
		Dataset dsCond		= paramVO.getDataset("ds_cond");
		Dataset dsFile		= paramVO.getDataset("ds_file");
		Dataset dsError   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();
		
		try {
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			Ses0088ParamVO param = new Ses0088ParamVO();
			
			param.setMANDT (DatasetUtility.getString(dsCond, "MANDT"));	
			param.setQTNUM(DatasetUtility.getString(dsCond, "QTNUM"));
			param.setQTSER(DatasetUtility.getString(dsCond, "QTSER"));
			param.setFILEGBN(DatasetUtility.getString(dsCond, "FILEGBN"));
			
			List<Ses0088> listFile = service.selectListFile(param);
			
			dsFile.deleteAll();
			for (int i = 0; i < listFile.size(); i++) {
				dsFile.appendRow();
				dsFile.setColumn(dsFile.getRowCount()-1, "MANDT", listFile.get(i).getMANDT());
				dsFile.setColumn(dsFile.getRowCount()-1, "QTNUM", listFile.get(i).getQTNUM());
				dsFile.setColumn(dsFile.getRowCount()-1, "QTSER", listFile.get(i).getQTSER());
				dsFile.setColumn(dsFile.getRowCount()-1, "ATTSEQ", listFile.get(i).getATTSEQ());
				dsFile.setColumn(dsFile.getRowCount()-1, "FILEGBN", listFile.get(i).getFILEGBN());
				dsFile.setColumn(dsFile.getRowCount()-1, "ATTPATH", listFile.get(i).getATTPATH());
				dsFile.setColumn(dsFile.getRowCount()-1, "ATTFN", listFile.get(i).getATTFN());
				dsFile.setColumn(dsFile.getRowCount()-1, "FLAG", listFile.get(i).getFLAG());
				dsFile.setColumn(dsFile.getRowCount()-1, "UUSER", listFile.get(i).getUUSER());
			}
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
	public View Ses0401SaveView(MipParameterVO paramVO, Model model) {
		
		// get Input Dataset
		Dataset dsFile    = paramVO.getDataset("ds_file");
		
		// define Error Dataset
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


	private List<Ses0088> configureFileParamToSave(Dataset dsFile, MipParameterVO paramVO) {
		List<Ses0088> listFile = new ArrayList<Ses0088>();
		
		for ( int i = 0 ; i < dsFile.getRowCount() ; i++ ) {
			Ses0088 paramFile = new Ses0088();
			paramFile.setMANDT(DatasetUtility.getString(dsFile, i, "MANDT"));
			paramFile.setQTNUM(DatasetUtility.getString(dsFile, i, "QTNUM"));
			paramFile.setQTSER(DatasetUtility.getString(dsFile, i, "QTSER"));
			paramFile.setATTSEQ(DatasetUtility.getString(dsFile, i, "ATTSEQ"));
			paramFile.setFILEGBN(DatasetUtility.getString(dsFile, i, "FILEGBN"));
			paramFile.setATTPATH(DatasetUtility.getString(dsFile, i, "ATTPATH"));
			paramFile.setATTFN(DatasetUtility.getString(dsFile, i, "ATTFN"));
			paramFile.setFLAG(DatasetUtility.getString(dsFile, i, "FLAG"));
			paramFile.setUUSER(paramVO.getVariable("G_USER_ID"));
			listFile.add(paramFile);
		}
		return listFile;
	}
	
}
