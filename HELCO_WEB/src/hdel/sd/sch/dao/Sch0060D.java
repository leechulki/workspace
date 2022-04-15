package hdel.sd.sch.dao;

import hdel.sd.sch.domain.ComboParamVO;
import hdel.sd.sch.domain.ComboVO;
import hdel.sd.sch.domain.Sch0060;
import hdel.sd.sch.domain.Sch0060ParamVO;

import java.util.List;

public interface Sch0060D {

	// 사양1, 사양2, 사양3, 사양4
	public List<ComboVO> selectZSPEC(ComboParamVO param);

	// 기준년월
	public List<ComboVO> selectCDATE(ComboParamVO param);

	// 구분2, 구분3
	public List<ComboVO> selectGUBUN(ComboParamVO param);

	// 신용등급
	public List<ComboVO> selectJUDGE(ComboParamVO param);

	// 설치1
	public List<ComboVO> selectZZACTSS(ComboParamVO param);

	// 설치2
	public List<ComboVO> selectTEMNO(ComboParamVO param);

	// 구분1, 수주유형, 납기경과개월수
	public List<ComboVO> selectGUBUNC(ComboParamVO param);

	// 건물용도
	public List<ComboVO> selectWWBLD(ComboParamVO param);

	// 수주협력업체, 설치협력업체
	public List<ComboVO> selectKUNZ1(ComboParamVO param);

	// 호기
	public List<Sch0060> selectHOKI(Sch0060ParamVO param);

	// 프로젝트
	public List<Sch0060> selectPROJECT(Sch0060ParamVO param);

	// 청구
	public List<Sch0060> selectCHEONG(Sch0060ParamVO param);

	// 수금
	public List<Sch0060> selectSUKEUM(Sch0060ParamVO param);

	public void mergeZSDT1010(Sch0060 param);

	public void mergeZSDT1011(Sch0060 param);

}
