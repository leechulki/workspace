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

	// 2012.05.30 : GRY : �߰� : �����ڵ�����ȸ (�ڵ�, �ڵ��)
	public List<ComboVO> selectListDD07T(ComboParamVO param);
	
	// 2012.05.30 : GRY : �߰� : �����ڵ�����ȸ (�ڵ�, �ڵ��(�ڵ�+' '+�ڵ��))
	public List<ComboVO> selectListConcatDD07T(ComboParamVO param);
	
	//2012.06.26 : ������ : �߰�
	public List<ComboVO> selectLand(ComboParamVO param);
	
	//2012.06.28 : ������ : �߰�
	public List<ComboVO> selectGtype(ComboParamVO param);
	public List<ComboVO> selectGtypeConcat(ComboParamVO param);
	public List<ComboVO> selectRtype(ComboParamVO param);
	
	//2012.07.23 : ������ : �߰�
	public List<ComboVO> selectJAACTT(ComboParamVO param);
	
	//2012.07.23 : ������ : �߰�
	public List<ComboVO> selectDD07V(ComboParamVO param);
	
	// 2012.07.31 : GRY : �߰� : �������� ��ȸ
	public List<ComboVO> selectListZterm(ComboParamVO param);
	
	// 2012.07.31 : GRY : �߰� : �ε����� ��ȸ
	public List<ComboVO> selectListInco(ComboParamVO param);
	
	// 2012.07.31 : GRY : �߰� : �������� ��ȸ
	public List<ComboVO> selectListMlbez(ComboParamVO param);
	
	// 2012.08.02 : GRY : �߰� : ���������ڵ�, ���������� ��ȸ
	public List<ComboVO> selectAuartNm(ComboParamVO param);
	
	// 2012.09.06 : GRY : �߰� : �������»� ��ȸ(����)
	public List<ComboVO> selectBosuArea(ComboParamVO param);
	
	public List<ComboVO> selectBosuAreaDetail(ComboParamVO param);
	
	// 2012.09.06 : GRY : �߰� : �������»� ��ȸ(PM)
	public List<ComboVO> selectBosuPm(ComboParamVO param);
	
	public List<ComboVO> selectBosuPmDetail(ComboParamVO param);
	
	// 2012.09.06 : GRY : �߰� : �������»� ��ȸ(��)
	public List<ComboVO> selectBosuTeam(ComboParamVO param);
	
	public List<ComboVO> selectBosuTeamDetail(ComboParamVO param);
}
