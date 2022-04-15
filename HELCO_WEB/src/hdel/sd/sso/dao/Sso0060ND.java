package hdel.sd.sso.dao;

import hdel.sd.sso.domain.Sso0060;
import hdel.sd.sso.domain.Sso0060ParamVO;
import hdel.sd.sso.domain.ZSDT1100;
import hdel.sd.sso.domain.ZSDT1101;
import hdel.sd.sso.domain.ZSDT1102;
import hdel.sd.sso.domain.ZSDT1103;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * 수주생성(Sso0060D) DAO
 * @Comment
 *        - List selectListZclose      ( 마감여부 조회 )
 *
 * @since 1.0
 *         History
 *         1.0  2016.0.25 박수근 개선
 * @version 1.0
 */


public interface Sso0060ND {


    // 프로젝트번호로 견적번호 조회
    public List<Sso0060> selectListQtnum(Sso0060ParamVO param); 

    // 견적번호로 입찰성공 견적차수 및 프로젝트번호 조회
    public List<Sso0060> selectListQtser(Sso0060ParamVO param); 

    // 견적번호로 견적상세정보 조회
    public List<Sso0060> selectListQtnumInfo(Sso0060ParamVO param);   

    // 견적번호로 견적품목상세정보 조회
    public List<Sso0060> selectListQtnumItemInfo(Sso0060ParamVO param); 

    // 판매처정보 조회
    public List<Sso0060> selectListKunnrRg(Sso0060ParamVO param); 

    // 부서명,팀명 조회
    public List<Sso0060> selectListVbVg(Sso0060ParamVO paramV);  

    // 납품처목록 조회
    public List<Sso0060> selectListKunnrWe(Sso0060ParamVO param); 

    // 대리점정보 조회
    public List<Sso0060> selectListKunnrZ1(Sso0060ParamVO param); 

    // 담당자정보 조회
    public List<Sso0060> selectListKunnrZ2(Sso0060ParamVO param); 

    // 기술영업담당자정보 조회
    public List<Sso0060> selectListKunnrZ3(Sso0060ParamVO param); 
    
    // 관리담당자 조회
    public List<Sso0060> selectListManager(Sso0060ParamVO param);

    // 견적진행상태 변경
    public void updateZsdt1046Zprgflg(Sso0060 param); 

    // 수주계획상태 변경
    public void updateZsdt1001Sorlt(Sso0060 param); 

    //public Sso0060 selectExchangeRate(Sso0060ParamVO param); 
    
    //=========================================================================================
    //  기능ID    : UF014
    //  개선내역  : 수주 사용자 입력 data 조회(수주생성임시저장)
    //=========================================================================================
    
    // 임시 견적서 HEADER 건수 조회
    public int selectTmpCount(Sso0060ParamVO param);

    // 임시 견적서 HEADER 조회
    public List<ZSDT1100> selectTmpQtHeader(Sso0060ParamVO param);

    // 임시 견적서 HEADER 입력/수정
    public int mergeTmpQtHeader(ZSDT1100 zSDT1100);

    // 임시 견적서 HEADER 저장
    public int insertTmpQtHeader(ZSDT1100 zSDT1100);

    // 임시 견적서 HEADER 수정
    public int updateTmpQtHeader(ZSDT1100 zSDT1100);
    
    // 임시 견적서 HEADER 삭제
    public int deleteTmpQtHeader(ZSDT1100 zSDT1100);
    
    // 임시 견적서 ITEM 조회
    public List<ZSDT1101> selectTmpQtItem(Sso0060ParamVO param);

    // 임시 견적서 ITEM 입력
    public int insertTmpQtItem(ZSDT1101 zSDT1101);
    
    // 임시 견적서 ITEM 수정
    public int updateTmpQtItem(ZSDT1101 zSDT1101);

    // 임시 견적서 ITEM All 삭제
    public int deleteAllTmpQtItem(ZSDT1101 zSDT1101);

    // 임시 견적서 ITEM 삭제
    public int deleteTmpQtItem(ZSDT1101 zSDT1101);

    // 임시 견적서 청구계획 조회
    public List<ZSDT1102> selectTmpQtBiliingPlan(Sso0060ParamVO param);
    
    // 임시 견적서 청구계획 삭제
    public int deleteTmpQtBiliing(Sso0060ParamVO param);

    // 임시 견적서 청구계획 맥스 시퀀스 조회
    public int deleteTmpQtBiliingMaxSeq(Sso0060ParamVO param);
    
    // 임시 견적서 청구계획 입력
    public int insertTmpQtBiliingPlan(ZSDT1102 zSDT1102);

    // 임시 견적서 청구계획 수정
    public int updateTmpQtBiliingPlan(ZSDT1102 zSDT1102);
    
    // 임시 견적서 청구계획 삭제
    public int deleteTmpQtBiliingPlan(ZSDT1102 zSDT1102);
    
    // 임시 견적서 텍스트 조회
    public List<ZSDT1103> selectTmpQtText(Sso0060ParamVO param);

    // 임시 견적서 청구계획 맥스 시퀀스 조회
    public int selectTmpQtTxtMaxSeq(Sso0060ParamVO param);
    
    // 임시 견적서 청구계획 맥스 시퀀스 조회
    public int deleteTmpQtMsTxtSer(Sso0060ParamVO param);
    
    // 임시 견적서 텍스트 입력
    public int insertTmpQtText(ZSDT1103 zSDT1103);

    // 임시 견적서 텍스트 수정
    public int updateTmpQtText(ZSDT1103 zSDT1103);
    
    // 임시 견적서 텍스트 삭제
    public int deleteTmpQtText(ZSDT1103 zSDT1103);
    
    //=========================================================================================
    //  기능ID    : UF014
    //  개선내역  : 수주 사용자 입력 data 조회(수주생성임시저장)
    //=========================================================================================
    
    public Sso0060 getVbeln(Sso0060ParamVO param);

	public List<Sso0060> selectSopind(Sso0060ParamVO param);
}
