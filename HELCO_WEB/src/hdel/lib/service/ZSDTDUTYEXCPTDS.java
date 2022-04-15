package hdel.lib.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.sap.domain.OrderItem;
import com.sap.domain.OrderNo;
import com.sap.domain.Spras;

import hdel.lib.dao.ZSDTDUTYEXCPTDD;
import hdel.lib.domain.ZSDTDUTYEXCPT;
import hdel.lib.domain.ZSDTDUTYEXCPTD;

@Service
public class ZSDTDUTYEXCPTDS extends SrmService {

	private hdel.lib.dao.ZSDTDUTYEXCPTD dutyExcptDao;
	private ZSDTDUTYEXCPTDD dutyExcptDDao;

	public void createDao(SqlSession sqlSession) {
		dutyExcptDDao = sqlSession.getMapper(ZSDTDUTYEXCPTDD.class);
		dutyExcptDao = sqlSession.getMapper(hdel.lib.dao.ZSDTDUTYEXCPTD.class);
	}

	public List<ZSDTDUTYEXCPTD> selectDutyExcption(String mandt, OrderNo ordNo, Integer ordSeq, OrderItem ordItem, Spras spras) {
		return dutyExcptDDao.select(mandt, ordNo, ordSeq, ordItem, spras);
	}
	public List<ZSDTDUTYEXCPTD> selectDutyExcptionAll(String mandt, OrderNo ordNo, Integer ordSeq, OrderItem ordItem, Spras spras) {
		return dutyExcptDDao.selectAll(mandt, ordNo, ordSeq, ordItem, spras);
	}
	public void deleteDutyExcption(List<ZSDTDUTYEXCPTD> lstDutyExcptD) {
		for(ZSDTDUTYEXCPTD dutyExcptD : lstDutyExcptD) {
			dutyExcptDDao.delete(dutyExcptD);
		}
		if(!lstDutyExcptD.isEmpty()) {
			ZSDTDUTYEXCPTD dutyExcptD = lstDutyExcptD.get(0);
			List<ZSDTDUTYEXCPTD> lstChekExist = selectDutyExcption(dutyExcptD.getMandt(), dutyExcptD.getOrdno(), dutyExcptD.getOrdseq(), new OrderItem(), dutyExcptD.getSpras());
			if(lstChekExist.isEmpty()) {
				dutyExcptDao.delete(dutyExcptD.getMandt(), dutyExcptD.getOrdno(), dutyExcptD.getOrdseq());
			}
		}
	}
	public void mergeDutyExcption(List<ZSDTDUTYEXCPTD> lstDutyExcptD) {
		if(!lstDutyExcptD.isEmpty()) {
			ZSDTDUTYEXCPTD dutyExcptD = lstDutyExcptD.get(0);
			List<ZSDTDUTYEXCPT> lstCheckExists = dutyExcptDao.select(dutyExcptD.getMandt(), dutyExcptD.getOrdno(), dutyExcptD.getOrdseq());
			if(lstCheckExists.isEmpty()) {
				ZSDTDUTYEXCPT dutyExcpt = new ZSDTDUTYEXCPT();
				dutyExcpt.setMandt(dutyExcptD.getMandt());
				dutyExcpt.setOrdno(dutyExcptD.getOrdno());
				dutyExcpt.setOrdseq(dutyExcptD.getOrdseq());
				dutyExcpt.getTstmp().setTimeStamp(dutyExcptD.getTstmp().getAenam());
				dutyExcptDao.merge(dutyExcpt);
			}
		}
		for(ZSDTDUTYEXCPTD dutyExcptD : lstDutyExcptD) {
			dutyExcptDDao.merge(dutyExcptD);
		}
	}
	public List<String> getExceptionalBlockNoList(String mandt, OrderNo ordNo, Integer ordSeq, OrderItem ordItem) {
		return dutyExcptDDao.getExceptionalBlockNoList(mandt, ordNo, ordSeq, ordItem);
	}
}