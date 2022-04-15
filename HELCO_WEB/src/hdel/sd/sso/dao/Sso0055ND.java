package hdel.sd.sso.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sap.domain.Vbeln;

import hdel.lib.domain.ZSDT0129;
import hdel.sd.sso.domain.Sso0055ParamVO;
import hdel.sd.sso.domain.Sso0055VO;
import hdel.sd.sso.domain.ZSDS0063;
import hdel.sd.sso.domain.ZSDT0090;
import hdel.sd.sso.domain.ZSDT0091;
import hdel.sd.sso.domain.ZSDT0092;
import hdel.sd.sso.domain.ZSDT0093;
import hdel.sd.sso.domain.ZSDT0094;
import hdel.sd.sso.domain.ZSDT1108;

/**
 * 수주변경
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
public interface Sso0055ND { 

	public List<ZSDS0063> SelectQtdat(ZSDS0063 param);	  		// 견적일 , 견적승인일 
	public List<ZSDS0063> SelectProject(ZSDS0063 param);	  	// 프로젝트 데이터
	public List<ZSDS0063> SelectProjectInput(ZSDS0063 param); 	// 프로젝트 데이터(신규생성)
	public List<ZSDT0090> SelectHeader(ZSDT0090 param);		  	// 계약변경 header
	public List<ZSDT0090> SelectHeaderInput(ZSDT0090 param);	// 계약변경 header(신규생성)
	public List<ZSDT0091> SelectItem(ZSDT0091 param);		  	// 계약변경 item
	public List<ZSDT0091> SelectItemInput(ZSDT0091 param);		// 계약변경 item(신규생성)
	public List<ZSDT0092> SelectBillO(ZSDT0092 param);		  	// 계약변경 billing original
	public List<ZSDT0092> SelectBillOInput(ZSDT0092 param);		// 계약변경 billing original(신규생성)
	public List<ZSDT0093> SelectBillC(ZSDT0093 param);		  	// 계약변경 billing change
	public List<ZSDT0093> SelectBillCInput(ZSDT0093 param);		// 계약변경 billing change(신규생성)
	public List<ZSDT0094> SelectSpecC(ZSDT0094 param);		  	// 계약변경 spec change
	public List<ZSDT0094> SelectSpecCInput(ZSDT0094 param);		// 계약변경 spec change(신규생성)

	public int mergeHeader(ZSDT0090 param);                    	// 계약변경 header save
	public int updateHeader(ZSDT0090 param);                    // 계약변경 header save
	public int insertItem(ZSDT0091 param);                    	// 계약변경 item save
	public int mergeItem(ZSDT0091 param);                    	// 계약변경 item save
	public int mergeBillO(ZSDT0092 param);                    	// 계약변경 billing original save
	public int mergeBillC(ZSDT0093 param);                    	// 계약변경 billing change save
	public int insertSpecC(ZSDT0094 param);                    	// 계약변경 spec chage save
	public int insertSpecCsub(ZSDT0094 param);                    	// 계약변경 spec chage save( (+) 버튼 클릭시)
	public int mergeSpecC(ZSDT0094 param);                    	// 계약변경 spec chage save
	public List<Sso0055VO> getRecad_da(Sso0055ParamVO param);  
	
	public List<ZSDT1108> selectJqpr(ZSDT1108 param);			// JQPR 조회
	public int mergeZsdt1108(ZSDT1108 param);					// JQPR 저장
	public int updateZqmt007(ZSDT1108 param); 
	
	public List<Sso0055VO> SelectSayang(Sso0055ParamVO param);		// 수주
	public List<Sso0055VO> SelectVbeln(Sso0055ParamVO param);		// 견적
	public List<Sso0055VO> SelectVbeln2(Sso0055ParamVO param);		// 견적
	public List<Sso0055VO> SelectMaxSeq(Sso0055ParamVO param);	  // 계약변경 차수
	public List<Sso0055VO> SelectSayangForPrint(Sso0055ParamVO param);
	public List<Sso0055VO> SelectMaxHogi(Sso0055VO param);		  	// 신규 호기 생성

	public void setCostState(Sso0055ParamVO param); // 원가의뢰
	public List<Sso0055VO> searchZcobt304(Sso0055ParamVO param);
	public List<Sso0055VO> searchZcobt304D(Sso0055ParamVO param);
	public List<Sso0055VO> searchZcobt304DPrev(Sso0055ParamVO param);
	public List<Sso0055VO> selectListBlockName(Sso0055ParamVO param);
	public List<Sso0055VO> selectListExchange(Sso0055ParamVO param); // 매매기준환율 조회 2013.02.20
	public List<Sso0055VO> searchZcobt204D(Sso0055ParamVO param);
	public List<Sso0055VO> selectListBlockNameD(Sso0055ParamVO param);
	
	// 삭제
	public int deleteZsdt0090(ZSDT0090 param); // 계약변경 header
	public int deleteZsdt0091(ZSDT0091 param); // 계약변경 item
	public int deleteZsdt0092(ZSDT0092 param); // 계약변경 billing original
	public int deleteZsdt0093(ZSDT0093 param); // 계약변경 billing change
	public int deleteZsdt0094(ZSDT0094 param); // 계약변경 spec
	public int deletePosnrZsdt0094(ZSDT0094 param); // 2020 브랜드 계약변경 호기 삭제 spec
	public int insertPosnrZsdt0094(ZSDT0094 param); // 2020 브랜드 계약변경 호기 삭제 spec
	
	public int deleteZsdt1108(ZSDT1108 param);	// JQPR 삭제
	
	public List<Sso0055VO> selectChkHeader(ZSDT0090 param);	  	// 계약변경 header (비고5 수정)
	
	public int insertZero0090(ZSDT0090 param);                   // 계약변경 0차수 생성 (HEADER)
	public int insertZero0091(ZSDT0091 param);                   // 계약변경 0차수 생성 (ITEM)
	public int insertZero0092(ZSDT0092 param);                   // 계약변경 0차수 생성 (BILLING ORIGINAL)
	public int insertZero0093(ZSDT0093 param);                   // 계약변경 0차수 생성 (BILLING CHANGE)
	public int insertZero0094(ZSDT0094 param);                   // 계약변경 0차수 생성 (SPEC)

	public String getPartnerZ3(@Param("mandt")String mandt, @Param("posid")String posid);
	public Sso0055VO SelectDocStatusGBSTK(@Param("mandt") String sMandt, @Param("pspid") String sPspid);
	public ZSDT0129 getExceptionalVbeln(@Param("mandt")String mandt, @Param("vbeln")Vbeln vbeln);
	
	public Double getZsdt0220Kbetr(@Param("mandt") String mandt, @Param("contrda")String contrda, @Param("vbeln")Vbeln vbeln);

	public String getZsdt0223Area(@Param("mandt")String mandt, @Param("contrda")String contrda, @Param("vbeln")Vbeln vbeln);
	
	public Double getAdtZa90(@Param("mandt") String mandt, @Param("vbeln")Vbeln vbeln);

	public String SelectHogi(@Param("mandt")String mandt, @Param("vbeln")String vbeln, 
							 @Param("pspid")String pspid, @Param("seq")String seq, @Param("posnr")String posnr);	//신규생성 호기 정보
	
	public List<Sso0055VO> SelectDateCommi(Sso0055ParamVO param);		// 대리점 수수료 기준일자	
	
	public String SelectKunnr(@Param("mandt")String mandt, @Param("vbeln")String vbeln); //영업사원 조회
	public String SelectFinl(ZSDT0090 param);

	public int SelectFksaf(@Param("mandt")String mandt, @Param("pspId")String pspId); 			//standard Table 청구대장 조회
	public int SelectZsdt0214(@Param("kunnr")String kunnr); 									//리모델링 대리점 현대협력사 카운트
	public int getMaxSeq(@Param("mandt")String i_mandt, @Param("vbeln")String i_vbeln);
	public int validation_pspid(@Param("mandt")String i_mandt, @Param("pspId")String i_pspId); 	//프로젝트 유효성 체크 	
	
	// 2020년 브랜드 적용
	public String SelectQtnum(ZSDT0090 param);
} 
