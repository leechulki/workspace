package hdel.qm.sag.dao;

import hdel.lib.domain.MipParameterVO;
import hdel.qm.sag.domain.QM0901000;
import hdel.qm.sag.domain.QM0901000ParamVO; 

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;


public interface QM0901000D {  
		
	// 초도품 검사 진행 관리 조회
	public List<QM0901000> selectList(QM0901000ParamVO param);
	public List<QM0901000> selectfileList(QM0901000 param);
	
	public List<QM0901000> selectListDetail(QM0901000 param);	
	public List<QM0901000> selectMaxZRqSeq(QM0901000 param);
	public List<QM0901000> selectMaxZattSeq(QM0901000 param);
	
		
	public void insertHeader(QM0901000 param);	
	public void updateHeader(QM0901000 param);
	public void updateRequestHeader(QM0901000 param);
	public void updateRegisterHeader(QM0901000 param);
	public void updateResultHeader(QM0901000 param);
	public void updateConfirmHeader(QM0901000 param);
	public void updateRejectHeader(QM0901000 param);
	
	public void deleteFile(QM0901000 param);
	public void insertFile(QM0901000 param);
	public void updateFile(QM0901000 param);
	
	public void deleteHeader(QM0901000 param);
	public void deleteFile2(QM0901000 param);
}
