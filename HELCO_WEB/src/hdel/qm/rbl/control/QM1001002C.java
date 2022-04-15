package hdel.qm.rbl.control;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.qm.rbl.domain.QM1001002;
import hdel.qm.rbl.service.QM1001002S;

@Controller
@RequestMapping("qm1001002")
public class QM1001002C {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	private SqlSessionTemplate sqlSessionTemplate;
	private SqlSessionFactory sqlSessionFactory;

	@Autowired
	private QM1001002S service;

	public void setSqlSessionBySysid(String gSysid) {
		this.sqlSessionTemplate = (SqlSessionTemplate) sqlSession.getSqlSessionBySysid(gSysid);
		sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();
	}

	@RequestMapping(value = "save")
	public View QM1001002SaveView(MipParameterVO paramVO, Model model) {

		// get Input Dataset
		Dataset ds_master = paramVO.getDataset("ds_master");
		Dataset ds_test = paramVO.getDataset("ds_test");
		Dataset ds_file = paramVO.getDataset("ds_file");
		Dataset ds_rnd = paramVO.getDataset("ds_rnd");

		String uuser = paramVO.getVariable("G_USER_ID"); // G_USER_NAME
		
		String mandt = paramVO.getVariable("G_MANDT");

		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO();

		setSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		SqlSession session = sqlSessionFactory.openSession(false);
		
		String resultReqseq = "";

		try {

			session.getConnection().setAutoCommit(false);

			service.createDao(session);

			QM1001002 masterObj = new QM1001002();

			DatasetUtil.moveDsRowToObj(ds_master, 0, masterObj);
			masterObj.setCreid(uuser);
			masterObj.setModid(uuser);
			masterObj.setMandt(mandt);

			List<QM1001002> testList = new ArrayList<QM1001002>();
			List<QM1001002> fileList = new ArrayList<QM1001002>();
			List<QM1001002> rndList = new ArrayList<QM1001002>();

			for (int i = 0; i < ds_test.getRowCount(); i++) {
				QM1001002 testObj = new QM1001002();
				DatasetUtil.moveDsRowToObj(ds_test, i, testObj);
				testObj.setCreid(uuser);
				testObj.setModid(uuser);
				testObj.setMandt(mandt);
				testList.add(testObj);
			}

			for (int j = 0; j < ds_file.getRowCount(); j++) {
				QM1001002 fileObj = new QM1001002();
				DatasetUtil.moveDsRowToObj(ds_file, j, fileObj);
				fileObj.setCreid(uuser);
				fileObj.setModid(uuser);
				fileObj.setMandt(mandt);
				fileList.add(fileObj);
			}

			if (ds_rnd != null) {
				for (int i = 0; i < ds_rnd.getRowCount(); i++) {
					QM1001002 rndObj = new QM1001002();
					DatasetUtil.moveDsRowToObj(ds_rnd, i, rndObj);
					rndObj.setCreid(uuser);
					rndObj.setModid(uuser);
					rndObj.setMandt(mandt);
					rndList.add(rndObj);
				}
			}

			QM1001002 testinfoObj = new QM1001002();

			// DatasetUtil.moveDsRowToObj(ds_testinfo, 0, testinfoObj);
			testinfoObj.setCreid(uuser);
			testinfoObj.setModid(uuser);
			testinfoObj.setMandt(mandt);

			resultReqseq = service.save(masterObj, testList, fileList, testinfoObj, rndList);

		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			logger.debug("@@@ QM1001002SaveView Exception => " + e.getMessage());
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			session.commit();
			session.close();
		}

		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		resultVO.addVariable("G_REQSEQ", resultReqseq);

		model.addAttribute("resultVO", resultVO);
		return view;
	}
}
