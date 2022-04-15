package hdel.sd.sso.dao;

import java.util.List;

import hdel.sd.sso.domain.ZSDT0004;
import hdel.sd.sso.domain.ZSDT0090;
import hdel.sd.sso.domain.ZSDT0091;
import hdel.sd.sso.domain.ZSDTHOGIMV;

/*
 ******************************************************************************************
 * 기      능   : 계약 호기순번 변경 DAO(Sso0056.xml)
 * 작  성  자   :
 * 작성  일자   : 2016.03.07
 * 기능ID       : UF006
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 최초 코딩 박수근
 ******************************************************************************************
*/

public interface Sso0056D { 

	// 기술사양 이력생성 순번 조회
	public int selectZSDT0090Cnt(ZSDT0090 zSDT0090);
	
	// max seq 조회
	public String selectZSDT0090MaxSeq(ZSDT0090 zSDT0090);
	
	// 변경된 X 호기 정보 조회
	public List<ZSDT0091> selectZSDT0091ChangeList(ZSDT0090 zSDT0090);
	
	// 기술사양 전송 리스트 조회
	public List<ZSDT0004> selectZSDT0004List(ZSDT0004 zSDT0004);
	
	// 최초 계약변경 헤더 자료를 SAP 수주 정보에서 조회하여 저장
	public int insertFirsrMvZSDT0090(ZSDTHOGIMV zSDTHOGIMV);
	
	// 최초 계약변경 호기 마스터 자료를 SAP 수주 정보에서 조회하여 저장
	public int insertFirstMvZSDT0091(ZSDTHOGIMV zSDTHOGIMV);
	
	// 최초 계약변경 호기별 청구계획 정보 SAP 수주 정보에서 조회하여 저장
	public int insertFirstMvZSDT0092(ZSDTHOGIMV zSDTHOGIMV);
	
	// 최초 계약변경 호기별 청구계획 변경정보 SAP 수주 정보에서 조회하여 저장
	public int insertFirstMvZSDT0093(ZSDTHOGIMV zSDTHOGIMV);

	// 최초 계약변경 호기별 사양서 내역 저장 건수 조회
    public int selectFirstMvOriginZSDT0094Cnt(ZSDTHOGIMV zSDTHOGIMV);
    
	// 최초 계약변경 호기별 사양서 내역 SAP 수주 정보에서 조회 하여 저장
    public int insertFirstMvOriginZSDT0094(ZSDTHOGIMV zSDTHOGIMV);
    
	// 최초 계약변경 호기별 사양서 변경 내역 SAP 수주 정보에서 조회 하여 저장
    public int insertFirstMvChangeZSDT0094(ZSDTHOGIMV zSDTHOGIMV);

	// 계획변경 계약변경 마스터에서 이전 차수를 신규차수로 변경해서 저장
    public int insertMvZSDT0090(ZSDTHOGIMV zSDTHOGIMV);

	// 계획변경 계약변경 호기 마스터에서 이전 차수를 신규차수로 변경해서 저장
    public int insertMvZSDT0091(ZSDTHOGIMV zSDTHOGIMV);
    
	// 계획변경 계약변경 호기별 청구계획 이전 차수를 신규차수로 변경해서 저장
    public int insertMvZSDT0092(ZSDTHOGIMV zSDTHOGIMV);
    
	// 계획변경 계약변경 호기별 이전 청구계획 이전 차수를 신규차수로 변경해서 저장
    public int insertMvZSDT0093(ZSDTHOGIMV zSDTHOGIMV);
    
	// 계획변경 계약변경 호기별 이전 사양내역 건수 조회(호기별 변경 호기 정보 반영)
    public int selectMvOriginZSDT0094Cnt(ZSDTHOGIMV zSDTHOGIMV);

	// 계획변경 계약변경 호기별 이전 사양내역에서 신규 이전 사양내역 저장(호기별 변경 호기 정보 반영)
    public int insertMvOriginZSDT0094(ZSDTHOGIMV zSDTHOGIMV);

    // 계획변경 계약변경 호기별 이전 사양내역에서 신규 사양내역 저장(호기별 변경 호기 정보 반영)
    public int insertMvChangeZSDT0094(ZSDTHOGIMV zSDTHOGIMV);
    
    // 호기별 변경 비고 정보 수정
	public int updateZSDT0090(ZSDTHOGIMV zSDTHOGIMV);

	// 호기별 변경여부 컬럼 X로 수정
	public int updateZSDT0091(ZSDTHOGIMV zSDTHOGIMV);

	// 호기별 변경된 호기번호 수정
	public int updateTmpZSDT0091(ZSDTHOGIMV zSDTHOGIMV);

	public int updateOrgMvZSDT0091(ZSDTHOGIMV zSDTHOGIMV);

	public int updateTmpMvZSDT0091(ZSDTHOGIMV zSDTHOGIMV);


	// 호기별 변경된 청구계획 호기번호 수정
	public int updateTmpZSDT0093(ZSDTHOGIMV zSDTHOGIMV);

	public int updateOrgMvZSDT0093(ZSDTHOGIMV zSDTHOGIMV);

	public int updateTmpMvZSDT0093(ZSDTHOGIMV zSDTHOGIMV);


	// 호기별 변경된 사양내역 수정
	public int updateTmpZSDT0094(ZSDTHOGIMV zSDTHOGIMV);

	public int updateOrgMvZSDT0094(ZSDTHOGIMV zSDTHOGIMV);

	public int updateTmpMvZSDT0094(ZSDTHOGIMV zSDTHOGIMV);
	
	// 저장되어 있는 데이터 삭제
	public int deleteZSDT0091(ZSDT0090 zSDT0090);
	public int deleteZSDT0092(ZSDT0090 zSDT0090);
	public int deleteZSDT0093(ZSDT0090 zSDT0090);
	public int deleteZSDT0094(ZSDT0090 zSDT0090);
	
	
}
