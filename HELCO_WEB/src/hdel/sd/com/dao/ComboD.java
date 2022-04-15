package hdel.sd.com.dao;

import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;

import java.util.List;

public interface ComboD {

	public List<ComboVO> selectXml(ComboParamVO param);
	
	public List<ComboVO> selectClass(ComboParamVO param);

	public List<ComboVO> selectVkburByDc(ComboParamVO param);
	public List<ComboVO> selectVkgrpByDc(ComboParamVO param);

	public List<ComboVO> selectVkbur(ComboParamVO param);
	
	public List<ComboVO> selectVkgrpAll(ComboParamVO param);
	
	public List<ComboVO> selectVkgrp(ComboParamVO param);
	
	public List<ComboVO> selectSman(ComboParamVO param);
	
	public List<ComboVO> selectBuyr(ComboParamVO param);
	
	public List<ComboVO> selectAuart(ComboParamVO param);

	// 2012.05.30 : GRY : 추가 : 공통코드목록조회 (코드, 코드명)
	public List<ComboVO> selectListDD07T(ComboParamVO param);
	
	// 2012.05.30 : GRY : 추가 : 공통코드목록조회 (코드, 코드명(코드+' '+코드명))
	public List<ComboVO> selectListConcatDD07T(ComboParamVO param);
	
	//2012.06.26 : 이지훈 : 추가
	public List<ComboVO> selectLand(ComboParamVO param);
	
	//2012.06.28 : 이지훈 : 추가
	public List<ComboVO> selectGtype(ComboParamVO param);
	public List<ComboVO> selectGtypeConcat(ComboParamVO param);
	public List<ComboVO> selectRtype(ComboParamVO param);
	
	//2012.07.23 : 이지훈 : 추가
	public List<ComboVO> selectJAACTT(ComboParamVO param);
	
	//2012.07.23 : 이지훈 : 추가
	public List<ComboVO> selectDD07V(ComboParamVO param);
	
	// 2012.07.31 : GRY : 추가 : 지급조건 조회
	public List<ComboVO> selectListZterm(ComboParamVO param);
	
	// 2012.07.31 : GRY : 추가 : 인도조건 조회
	public List<ComboVO> selectListInco(ComboParamVO param);
	
	// 2012.07.31 : GRY : 추가 : 종별구분 조회
	public List<ComboVO> selectListMlbez(ComboParamVO param);
	
	// 2012.08.02 : GRY : 추가 : 오더유형코드, 오더유형명 조회
	public List<ComboVO> selectAuartNm(ComboParamVO param);
	
	// 2012.09.06 : GRY : 추가 : 보수협력사 조회(지역)
	public List<ComboVO> selectBosuArea(ComboParamVO param);
	
	public List<ComboVO> selectBosuAreaDetail(ComboParamVO param);
	
	// 2012.09.06 : GRY : 추가 : 보수협력사 조회(PM)
	public List<ComboVO> selectBosuPm(ComboParamVO param);
	
	public List<ComboVO> selectBosuPmDetail(ComboParamVO param);
	
	// 2012.09.06 : GRY : 추가 : 보수협력사 조회(팀)
	public List<ComboVO> selectBosuTeam(ComboParamVO param);
	
	public List<ComboVO> selectBosuTeamDetail(ComboParamVO param);
}
