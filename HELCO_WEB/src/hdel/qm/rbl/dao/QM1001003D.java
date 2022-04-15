package hdel.qm.rbl.dao;

import java.util.List;

import hdel.qm.rbl.domain.QM1001002;

public interface QM1001003D {

	public List<QM1001002> selectNReqSeq(QM1001002 param);

	public void updateMaster(QM1001002 param);

	public void insertTest(QM1001002 param);

	public void insertTestInfo(QM1001002 param);

	public void updateTestInfo(QM1001002 param);

	public void insertFile(QM1001002 param);
	
	public void deleteFile(QM1001002 param);
}
