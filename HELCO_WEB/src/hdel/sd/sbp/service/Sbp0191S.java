package hdel.sd.sbp.service;


import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.sbp.dao.Sbp0191D;
import hdel.sd.sbp.domain.Sbp0191;
import hdel.sd.sbp.domain.Sbp0191ParamVO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Sbp0191S extends SrmService {
	 
	private Sbp0191D sbp0191D;

	@Override
	public void createDao(SqlSession sqlSession) {
		sbp0191D = sqlSession.getMapper(Sbp0191D.class);
	}
	
	public List<Sbp0191> selectListDetail(Sbp0191ParamVO param) {
		return sbp0191D.selectListDetail(param);
	}
	
	public int insertZSDT1006(Sbp0191 param) {
		return sbp0191D.insertZSDT1006(param);
	}
	
	public int deleteZSDT1006(Sbp0191 param) {
		return sbp0191D.deleteZSDT1006(param);
	}
	
	public int insertZSDT1007(Sbp0191 param) {
		return sbp0191D.insertZSDT1007(param);
	}
	
	public int deleteZSDT1007(Sbp0191 param) {
		return sbp0191D.deleteZSDT1007(param);
	}
	
	public int insertZSDT1008(Sbp0191 param) {
		return sbp0191D.insertZSDT1008(param);
	}
	
	public int deleteZSDT1008(Sbp0191 param) {
		return sbp0191D.deleteZSDT1008(param);
	}

	public void save(MipParameterVO paramVO, Model model, SqlSession session) {
		Dataset dsCondDetail = paramVO.getDatasetList().getDataset("ds_cond_detail");
		Dataset dsYearm = paramVO.getDatasetList().getDataset("ds_yearm");
		Dataset dsListDetail = paramVO.getDatasetList().getDataset("ds_list_detail");
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String userId = paramVO.getVariable("G_USER_ID");		// WEB USER ID
		String pspid = DatasetUtility.getString(dsCondDetail,"PSPID");

		try {
			Sbp0191 param = new Sbp0191();				// 매출/청구/수금 저장 파라메터
			String gbn = null;							// 매출/청구/수금 구분	(1:매출, 2:청구, 3:수금)
			BigDecimal amt = null;						// 매출/청구/수금 금액
			BigDecimal zero = new BigDecimal("0.00");	// 0.00
			
			// DAO생성
			createDao(session);

			param.setMANDT(mandt);
			param.setPSPID(pspid);
			
			// 매출삭제 서비스CALL  (사업계획번호 한건당 삭제)
			deleteZSDT1006(param);
			
			// 청구삭제 서비스CALL  (사업계획번호 한건당 삭제)
			deleteZSDT1007(param);
			
			// 수금삭제 서비스CALL  (사업계획번호 한건당 삭제)
			deleteZSDT1008(param);  
			
			// 매출/청구/수금등록 서비스CALL
			detailSave( dsListDetail												// 매출/청구/수금 예상금액
								, dsYearm											// 매출/청구/수금 예상년월
								, mandt												// CLIENT
								, userId 											// WEB USER_ID
								, pspid 											// 사업계획번호
								, DatasetUtility.getString(dsCondDetail,"ZMPFLG") 	// 이동계획반영여부
								, DatasetUtility.getString(dsCondDetail,"WAERK")	// 통화
								);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	// 매출/청구/수금 등록
	public void detailSave(	 Dataset dsListDetail
								,Dataset dsYearm	// 예상년월 18개월
								,String mandt		// CLIENT
								,String userId 		// WEB USER_ID
								,String pspid 		// 수주계획번호
								,String zmpflg 		// 이동계획반영여부
								,String waerk 		// 통화
								)
	{ 

		Sbp0191 param = new Sbp0191();				// 매출/청구/수금 저장 파라메터
		String gbn = null;							// 매출/청구/수금 구분	(1:매출, 2:청구, 3:수금)
		BigDecimal amt = null;						// 매출/청구/수금 금액
		BigDecimal zero = new BigDecimal("0.00");	// 0.00
		String colId = null;
		String yearm = null;
		
		for (int iRow1=0;iRow1<dsListDetail.getRowCount(); iRow1++ ) 
		{
			gbn = dsListDetail.getColumnAsString(iRow1, "GBN"); 	// 매출/청구/수금 구분
			
			for (int iCol1=0;iCol1<dsListDetail.getColumnCount(); iCol1++ ) 
			{
				colId = dsListDetail.getColumnID(iCol1);
				
				if (!("MANDT".equals(colId)) && !("GBN".equals(colId)) && !("GBN_NM".equals(colId)))
				{
					// 금액항목에 값이 있는 경우에만 실행
					if (StringUtils.isNotBlank(dsListDetail.getColumnAsString(iRow1, iCol1)))
					{ 
						// 예상금액
						amt = new BigDecimal(dsListDetail.getColumnAsDouble(iRow1, iCol1));  
						
						// 금액 > 0 인 경우만 등록
						if( amt.compareTo(zero) == 1 )  // compareTo :::: -1은 작다, 0은 같다, 1은 크다 
						{  	
							if ( colId.endsWith("00")) yearm = dsYearm.getColumnAsString( 0, "YEARM");
							if ( colId.endsWith("01")) yearm = dsYearm.getColumnAsString( 1, "YEARM");
							if ( colId.endsWith("02")) yearm = dsYearm.getColumnAsString( 2, "YEARM");
							if ( colId.endsWith("03")) yearm = dsYearm.getColumnAsString( 3, "YEARM");
							if ( colId.endsWith("04")) yearm = dsYearm.getColumnAsString( 4, "YEARM");
							if ( colId.endsWith("05")) yearm = dsYearm.getColumnAsString( 5, "YEARM");
							if ( colId.endsWith("06")) yearm = dsYearm.getColumnAsString( 6, "YEARM");
							if ( colId.endsWith("07")) yearm = dsYearm.getColumnAsString( 7, "YEARM");
							if ( colId.endsWith("08")) yearm = dsYearm.getColumnAsString( 8, "YEARM");
							if ( colId.endsWith("09")) yearm = dsYearm.getColumnAsString( 9, "YEARM");
							if ( colId.endsWith("10")) yearm = dsYearm.getColumnAsString(10, "YEARM");
							if ( colId.endsWith("11")) yearm = dsYearm.getColumnAsString(11, "YEARM");
							if ( colId.endsWith("12")) yearm = dsYearm.getColumnAsString(12, "YEARM");
							if ( colId.endsWith("13")) yearm = dsYearm.getColumnAsString(13, "YEARM");
							if ( colId.endsWith("14")) yearm = dsYearm.getColumnAsString(14, "YEARM");
							if ( colId.endsWith("15")) yearm = dsYearm.getColumnAsString(15, "YEARM");
							if ( colId.endsWith("16")) yearm = dsYearm.getColumnAsString(16, "YEARM");
							if ( colId.endsWith("17")) yearm = dsYearm.getColumnAsString(17, "YEARM");
							if ( colId.endsWith("18")) yearm = dsYearm.getColumnAsString(18, "YEARM");
							if ( colId.endsWith("19")) yearm = dsYearm.getColumnAsString(19, "YEARM");
							if ( colId.endsWith("20")) yearm = dsYearm.getColumnAsString(20, "YEARM");
							if ( colId.endsWith("21")) yearm = dsYearm.getColumnAsString(21, "YEARM");
							if ( colId.endsWith("22")) yearm = dsYearm.getColumnAsString(22, "YEARM");
							if ( colId.endsWith("23")) yearm = dsYearm.getColumnAsString(23, "YEARM");
							if ( colId.endsWith("24")) yearm = dsYearm.getColumnAsString(24, "YEARM");
							if ( colId.endsWith("25")) yearm = dsYearm.getColumnAsString(25, "YEARM");
							if ( colId.endsWith("26")) yearm = dsYearm.getColumnAsString(26, "YEARM");
							if ( colId.endsWith("27")) yearm = dsYearm.getColumnAsString(27, "YEARM");
							if ( colId.endsWith("28")) yearm = dsYearm.getColumnAsString(28, "YEARM");
							if ( colId.endsWith("29")) yearm = dsYearm.getColumnAsString(29, "YEARM");
							
							param.setMANDT(mandt);  		// SAP CLIENT
							param.setUSER_ID(userId);  	// WEB USER_ID
							param.setPSPID(pspid);		// 사업계획번호 
							param.setZMPFLG(zmpflg);	// 이동계획반영여부
							param.setWAERK(waerk);		// 통화
							param.setZSAYM(yearm);		// 예상년월
							
							// 등록서비스 CALL
							if 		 ("1".equals(gbn)) { 
								param.setZSAYM(yearm);	// 예상년월
								param.setNETWR_SA(amt);	// 예상금액
								insertZSDT1006(param);  // 매출등록 서비스CALL
							}else if ("2".equals(gbn)) { 
								param.setZRQYM(yearm);	// 예상년월
								param.setNETWR_RQ(amt);	// 예상금액
								insertZSDT1007(param);  // 청구등록 서비스CALL
							}else if ("3".equals(gbn)) { 
								param.setBYSYM(yearm);	// 예상년월
								param.setCOLBI(amt);	// 예상금액
								insertZSDT1008(param);  // 수금등록 서비스CALL
							}
						}  	// end of if( amt.compareTo(zero) == 1 ) 
					}		// end of if (StringUtils.isNotBlank(ds_list_detail.getColumnAsString(iRow1, iCol1)))
				}			// 
			}  				// end of for (int iCol1=2;iCol1<=ds_list_detail.getColumnCount(); iCol1++ ) 
		}					// end of for (int iRow1=0;iRow1<=ds_list_detail.getRowCount(); iRow1++ ) 
	} 
	
}
