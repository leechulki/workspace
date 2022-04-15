package hdel.sd.com.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.com.dao.ComboD;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;

@Service
public class ComboS extends SrmService {

	private ComboD commonD;
	private hdel.sd.sch.dao.Sch0060D sch0060d;

	@Override
	public void createDao(SqlSession sqlSession) {
		commonD = sqlSession.getMapper(ComboD.class);
		sch0060d = sqlSession.getMapper(hdel.sd.sch.dao.Sch0060D.class);
	}
	
	public List<ComboVO> selectXml(ComboParamVO param) {
		return commonD.selectXml(param);
	}
	
	public List<ComboVO> selectClass(ComboParamVO param) {
		return commonD.selectClass(param);
	}
	
	public List<ComboVO> selectVkburByDc(ComboParamVO param) {
		return commonD.selectVkburByDc(param);
	}
	public List<ComboVO> selectVkgrpByDc(ComboParamVO param) {
		return commonD.selectVkgrpByDc(param);
	}

	public List<ComboVO> selectVkbur(ComboParamVO param) {
		return commonD.selectVkbur(param);
	}
	
	public List<ComboVO> selectVkgrpAll(ComboParamVO param) {
		return commonD.selectVkgrpAll(param);
	}
	
	public List<ComboVO> selectVkgrp(ComboParamVO param) {
		return commonD.selectVkgrp(param);
	}
	
	public List<ComboVO> selectSman(ComboParamVO param) {
		return commonD.selectSman(param);
	}
	
	public List<ComboVO> selectBuyr(ComboParamVO param) {
		return commonD.selectBuyr(param);
	}
	
	public List<ComboVO> selectAuart(ComboParamVO param) {
		return commonD.selectAuart(param);
	}
	
	// 2012.05.30 : GRY : 추가 : 공통코드목록조회 (코드, 코드명(코드+' '+코드명))
	public List<ComboVO> selectListConcatDD07T(ComboParamVO param) {
		return commonD.selectListConcatDD07T(param);
	}
	
	//2012.05.30 : GRY : 추가 : 공통코드목록조회 (코드, 코드명)
	public List<ComboVO> selectListDD07T(ComboParamVO param) {
		return commonD.selectListDD07T(param);
	}
	
	// 2012.06.26 : 이지훈 : 추가
	public List<ComboVO> selectLand(ComboParamVO param) {
		return commonD.selectLand(param);
	}
	
	// 2012.06.28 : 이지훈 : 추가
	public List<ComboVO> selectGtype(ComboParamVO param) {
		return commonD.selectGtype(param);
	}
	public List<ComboVO> selectGtypeConcat(ComboParamVO param) {
		return commonD.selectGtypeConcat(param);
	}
	public List<ComboVO> selectRtype(ComboParamVO param) {
		return commonD.selectRtype(param);
	} 
	
	// 2012.07.23 : 이지훈 : 추가
	public List<ComboVO> selectJAACTT(ComboParamVO param) {
		return commonD.selectJAACTT(param);
	}
	
	// 2012.07.23 : 이지훈 : 추가
	public List<ComboVO> selectDD07V(ComboParamVO param) {
		return commonD.selectDD07V(param);
	} 

	// 2012.07.31 : GRY : 추가 : 지급조건 조회
	public List<ComboVO> selectListZterm(ComboParamVO param) {
		return commonD.selectListZterm(param);
	}
	
	// 2012.07.31 : GRY : 추가 : 인도조건 조회
	public List<ComboVO> selectListInco(ComboParamVO param) {
		return commonD.selectListInco(param);
	}
	
	// 2012.07.31 : GRY : 추가 : 종별구분 조회
	public List<ComboVO> selectListMlbez(ComboParamVO param) {
		return commonD.selectListMlbez(param);
	}
	
	// 2012.08.02 : GRY : 추가 : 오더유형코드, 오더유형명 조회
	public List<ComboVO> selectAuartNm(ComboParamVO param) {
		return commonD.selectAuartNm(param);
	}
	
	// 2012.08.02 : GRY : 추가 : 보수협력사 조회(지역)
	public List<ComboVO> selectBosuArea(ComboParamVO param) {
		return commonD.selectBosuArea(param);
	}
	
	public List<ComboVO> selectBosuAreaDetail(ComboParamVO param) {
		return commonD.selectBosuAreaDetail(param);
	}
	
	// 2012.08.02 : GRY : 추가 : 보수협력사 조회(PM)
	public List<ComboVO> selectBosuPm(ComboParamVO param) {
		return commonD.selectBosuPm(param);
	}
	
	public List<ComboVO> selectBosuPmDetail(ComboParamVO param) {
		return commonD.selectBosuPmDetail(param);
	}
	
	// 2012.08.02 : GRY : 추가 : 보수협력사 조회(팀)
	public List<ComboVO> selectBosuTeam(ComboParamVO param) {
		return commonD.selectBosuTeam(param);
	}
	
	public List<ComboVO> selectBosuTeamDetail(ComboParamVO param) {
		return commonD.selectBosuTeamDetail(param);
	}

	public List<hdel.sd.sch.domain.ComboVO> selectTemno(String mandt) {
		hdel.sd.sch.domain.ComboParamVO param = new hdel.sd.sch.domain.ComboParamVO();
		param.setMANDT(mandt);
		return sch0060d.selectTEMNO(param);
	}
}