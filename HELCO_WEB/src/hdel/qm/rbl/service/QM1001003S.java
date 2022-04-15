package hdel.qm.rbl.service;

import hdel.qm.rbl.dao.QM1001003D;
import hdel.qm.rbl.domain.QM1001002;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class QM1001003S {

	Logger logger = Logger.getLogger(this.getClass());

	private QM1001003D QM1001003Dao;

	public void createDao(SqlSession sqlSession) throws Exception {
		QM1001003Dao = sqlSession.getMapper(QM1001003D.class);
	}

	public String save(QM1001002 master, List<QM1001002> testList, List<QM1001002> fileList, QM1001002 testinfo) throws Exception {

		try {

			QM1001003Dao.updateMaster(master);

			for (int i = 0; i < testList.size(); i++) {
				QM1001002 test = testList.get(i);
				if (!test.getReqseq().equals("")) {
					QM1001003Dao.insertTest(test);
				}
			}

			if (testList.size() > 0) {
				QM1001003Dao.insertTestInfo(testinfo);
				QM1001003Dao.updateTestInfo(testinfo);
			}

			/*
			 * if ( testinfo != null && testinfo.getOtestrev() != null &&
			 * !testinfo.getOtestrev().equals("") ) { // 계획 변경 사유 update
			 * QM1001003Dao.updateTestInfo(testinfo); }
			 */
			
			//QM1001003Dao.deleteFile(master);
			
			for (int i = 0; i < fileList.size(); i++) {
				QM1001002 file = fileList.get(i);
				if( "I".equals( file.getFlag())  ) {
					QM1001003Dao.insertFile(file);
				} else if( "D".equals( file.getFlag())  ) {					
					QM1001003Dao.deleteFile(file);
				}
			}			

		} catch (Exception e) {
			throw e;
		}
		return null;
	}

}
