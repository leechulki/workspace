package hdel.qm.rbl.dao;

import java.util.List;

import hdel.qm.rbl.domain.QM1001002;

public interface QM1001002D {

	public List<QM1001002> selectNReqSeq(QM1001002 param);

	public void insertMaster(QM1001002 param);

	public void insertTest(QM1001002 param);
	
	public void deleteTest(QM1001002 param);

	public void insertRnd(QM1001002 param);
	
	public void deleteRnd(QM1001002 param);

	public void insertTestInfo(QM1001002 param);
	
	public void deleteTestInfo(QM1001002 param);

	public void insertFile(QM1001002 param);
	
	public void deleteFile(QM1001002 param);

}
