package hdel.qm.rbl.service;

import hdel.qm.rbl.dao.QM1001002D;
import hdel.qm.rbl.domain.QM1001002;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class QM1001002S {

	Logger logger = Logger.getLogger(this.getClass());

	private QM1001002D QM1001002Dao;

	public void createDao(SqlSession sqlSession) throws Exception {
		QM1001002Dao = sqlSession.getMapper(QM1001002D.class);
	}

	public String save(QM1001002 master, List<QM1001002> testList, List<QM1001002> fileList, QM1001002 testinfo,
			List<QM1001002> rndList) throws Exception {
		
		String nreqseq = master.getReqseq();

		try {
			
			if (nreqseq == null || "".equals(nreqseq)) {
				List<QM1001002> maxlist = QM1001002Dao.selectNReqSeq(master);
				nreqseq = maxlist.get(0).getNreqseq();
				master.setReqseq(nreqseq);
			}

			QM1001002Dao.insertMaster(master);
			
			// 임시저장 DATA 삭제
			QM1001002Dao.deleteTest(master);
			//QM1001002Dao.deleteFile(master);
			QM1001002Dao.deleteRnd(master);
			QM1001002Dao.deleteTestInfo(master);

			for (int i = 0; i < testList.size(); i++) {
				QM1001002 test = testList.get(i);
				test.setReqseq(nreqseq);
				QM1001002Dao.insertTest(test);
			}

			for (int i = 0; i < fileList.size(); i++) {
				QM1001002 file = fileList.get(i);
				file.setReqseq(nreqseq);
				
				if( "I".equals( file.getFlag())  ) {
					QM1001002Dao.insertFile(file);
				} else if( "D".equals( file.getFlag())  ) {					
					QM1001002Dao.deleteFile(file);
				}
			}

			if (rndList != null) {
				for (int i = 0; i < rndList.size(); i++) {
					QM1001002 rnd = rndList.get(i);
					rnd.setReqseq(nreqseq);
					QM1001002Dao.insertRnd(rnd);
				}
			}

			testinfo.setReqseq(nreqseq);
			QM1001002Dao.insertTestInfo(testinfo);

		} catch (Exception e) {
			throw e;
		}
		return nreqseq;
	}

}
