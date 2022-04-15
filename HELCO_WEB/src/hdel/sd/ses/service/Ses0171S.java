package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0171D;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.domain.Ses0171;
import hdel.sd.ses.domain.Ses0171ParamVO;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.helco.xf.comm.DatasetUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.Dataset;

import hdel.sd.com.domain.ExchangeVO;
import hdel.sd.com.service.ExchangeS;

@Service
public class Ses0171S extends SrmService {

	private Ses0171D Ses0171Dao;
	
	@Autowired
	private ExchangeS ExchangeS;
	
	public void createDao(SqlSession sqlSession) {
		Ses0171Dao = sqlSession.getMapper(Ses0171D.class);
	}

	public List<Ses0171> searchList(Ses0171ParamVO param) {
		return Ses0171Dao.selectList(param);
	}
	
	public List<Ses0171> searchListDetail(Ses0171ParamVO param) {
		return Ses0171Dao.selectListDetail(param);
	}

	public List<Ses0171> selectSumCostZSDT1047(Ses0171ParamVO param) {
		return Ses0171Dao.selectSumCostZSDT1047(param);
	}

	// 견적원가등록
	public void saveZSDT1050(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		Dataset ds_detail = paramVO.getDataset("ds_detail");		// INPUT DATASET GET 
		Dataset ds_list   = paramVO.getDataset("ds_list");

		try {
			Ses0171 param = new Ses0171();	// 저장 파라메터
			createDao(session);	 			// DAO 생성

			String flag = "";

			for( int i = 0; i < ds_detail.getRowCount(); i++ ) {
				param.setFlag(DatasetUtility.getString(ds_detail, i, "FLAG"));
				param.setMandt(DatasetUtility.getString(ds_detail, i, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_detail, i, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_detail, i, "QTSER"));
				param.setQtseq(DatasetUtility.getString(ds_detail, i, "QTSEQ"));
				param.setZcostzseq(DatasetUtility.getString(ds_detail, i, "ZCOSTZSEQ"));
				param.setZccd(DatasetUtility.getString(ds_detail, i, "ZCCD"));
				param.setZcgrp(DatasetUtility.getString(ds_detail, i, "ZCGRP"));
				param.setZcct(DatasetUtility.getString(ds_detail, i, "ZCCT"));
				param.setZuam(DatasetUtility.getString(ds_detail, i, "ZUAM"));
				param.setZuams(DatasetUtility.getString(ds_detail, i, "ZUAMS"));
				param.setZurate(DatasetUtility.getString(ds_detail, i, "ZURATE"));
				param.setZcost(DatasetUtility.getString(ds_detail, i, "ZCOST"));
				param.setZprcp(DatasetUtility.getString(ds_detail, i, "ZPRCP"));
				param.setZprdi(DatasetUtility.getString(ds_detail, i, "ZPRDI"));
				param.setZprds(DatasetUtility.getString(ds_detail, i, "ZPRDS"));
				param.setWaerk(DatasetUtility.getString(ds_detail, i, "WAERK"));
				param.setUser_id(paramVO.getVariable("G_USER_ID"));
				
				flag = DatasetUtility.getString(ds_detail, i, "FLAG");

				if (flag.equals("D")) {
					deleteZSDT1050(param); // updateCostZSDT1047(param); // 견적Detail 수기원가 합계 업데이트
				} else if (flag.equals("U") || flag.equals("I")){
					insertZSDT1050(param); //	updateCostZSDT1047(param);	 // 견적Detail 수기원가 합계 업데이트
				}
			}

			// ZSDT1047처리용
			Ses0171 param1 = new Ses0171();	// 저장 파라메터
			boolean bCheck = false;
			String  sQtnum = "";
			String  sQtser = "";

			if (!"part".equals(paramVO.getVariable("fa_part")))		{	// 해당 영업본부에서 직접 수기 입력 시 처리
				for( int i = 0; i < ds_list.getRowCount(); i++ ) {

					flag = DatasetUtility.getString(ds_list, i, "FLAG");
	
					if ("U".equals(flag)) {
					
						param1.setFlag(DatasetUtility.getString(ds_list, i, "FLAG"));
						param1.setMandt(DatasetUtility.getString(ds_list, i, "MANDT"));
						param1.setQtnum(DatasetUtility.getString(ds_list, i, "QTNUM"));
						sQtnum = DatasetUtility.getString(ds_list, i, "QTNUM");

						param1.setQtser(DatasetUtility.getString(ds_list, i, "QTSER"));
						sQtser = DatasetUtility.getString(ds_list, i, "QTSER");

						param1.setQtseq(DatasetUtility.getString(ds_list, i, "QTSEQ"));
						param1.setZuam(DatasetUtility.getString(ds_list, i, "ZUAM"));
						
						//상해단가 추가  - 신미정 2014.05.27 정동명CJ 요청		
						param1.setZuams(DatasetUtility.getString(ds_list,  i, "ZUAMS"));
						param1.setZurate(DatasetUtility.getString(ds_list,  i, "ZURATE"));
						param1.setZeam(DatasetUtility.getString(ds_list, i, "ZEAM"));
						param1.setZcost(DatasetUtility.getString(ds_list, i, "ZCOST"));
						param1.setShang(DatasetUtility.getString(ds_list, i, "SHANG"));
						param1.setUser_id(paramVO.getVariable("G_USER_ID"));
	
						updateCostZSDT1047(param1);	 // 견적Detail 수기원가 합계 업데이트
						bCheck = true;
					}
				}
				
				if ( bCheck )		{
					// 시행울 계산 및 파싱
					Ses0171 paramH = new Ses0171();	// 저장 파라메터
					paramH.setMandt(paramVO.getVariable("G_MANDT"));
					paramH.setQtnum(sQtnum);
					paramH.setQtser(sQtser);
					paramH.setUser_id(paramVO.getVariable("G_USER_ID"));

					updateCostZSDT1046(paramH);	 // 견적 수기원가 합계 업데이트
				}
			}
			System.out.print("\n@@@ End Service");

		}  catch (Exception e) {
			throw e;
		}
	}  
	
	// 견적원가의뢰승인
	public void confirmFlag(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		Dataset ds_list = paramVO.getDataset("ds_save"); // INPUT DATASET GET

		try {
			Ses0171 	param = new Ses0171(); // 저장 파라메터
			createDao(session);	 				// DAO 생성
			
			for( int i = 0; i < ds_list.getRowCount(); i++ ) 	{ // 원가의뢰요청 접수 처리 START

				param.setMandt(DatasetUtility.getString(ds_list, i, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, i, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, i, "QTSER"));
				param.setZrqseq(DatasetUtility.getString(ds_list, i, "ZRQSEQ"));
				param.setZrqdat(DatasetUtility.getString(ds_list, i, "ZRQDAT"));
				param.setZrqcn(DatasetUtility.getString(ds_list, i, "ZRQCN"));
				param.setZrqstat(DatasetUtility.getString(ds_list, i, "ZRQSTAT"));
				param.setZrqrlt(DatasetUtility.getString(ds_list, i, "ZRQRLT"));
				param.setUser_id(DatasetUtility.getString(ds_list, i, "USER_ID"));

				updateZSDT1054(param);					//견적원가의뢰승인
				updateFlagConfirmZSDT1046(param);		//견적HEADER Update
				updateCostZSDT1047(param);				//견적Detail 수기원가 합계 업데이트
				
				System.out.print("\n@@@ ds_list.COUNT 	===>"+DatasetUtility.getInt(ds_list, i, "ZRQSTAT")+"\n");
			} 
			System.out.print("\n@@@ End Service");

		}  catch (Exception e) { 
		}
	}
/*	
	public Dataset searchExchangeRate(MipParameterVO paramVO) throws RuntimeException, Exception {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsExchange = paramVO.getDataset("ds_exchange");
		
		try {		
			
			String mandt = paramVO.getVariable("G_MANDT");	                // SAP CLIENT
			String qtnum = DatasetUtility.getString(dsCond,"QTNUM");
			String qtser = DatasetUtility.getString(dsCond,"QTSER");
			String pspid = DatasetUtility.getString(dsCond,"PSPID");
			String seq = DatasetUtility.getString(dsCond,"SEQ");
			String kurst = DatasetUtility.getString(dsCond,"KURST");
            
			Ses0171 CurBaseinfo = null;
			if ("Q".equals( kurst ) ) {
				CurBaseinfo = Ses0171Dao.selectCurrencyQt(mandt, qtnum, qtser);		// 견적일자, 견적통화 쿼리
			} else {
				CurBaseinfo = Ses0171Dao.selectCurrencySo(mandt, pspid, seq);		// 수주일자, 수주통화 쿼리
			}
			
			String waerkCon = CurBaseinfo.getWaerk();
			String auart = CurBaseinfo.getAuart();
			String gdatu = CurBaseinfo.getGdatu();
			String exchangeBas = null;				//기준통화 
			String exchangeCon = null;				//계약통화

			String waerkBas = Ses0171Dao.searchWaerkBase(mandt, gdatu); 
			
			if (waerkBas != waerkCon && "ZT".equals( auart.substring(0, 2))) {
				exchangeBas = ExchangeS.getExchangeRate(mandt, kurst, gdatu, waerkBas, "KRW");		// 기준통화 -> 원화 
				exchangeCon = ExchangeS.getExchangeRate(mandt, kurst, gdatu, waerkCon, "KRW");		// 계약통화 -> 원화 		
			}
			
			dsExchange.deleteAll();
			dsExchange.appendRow();
			dsExchange.setColumn(0, "MANDT", mandt);
			dsExchange.setColumn(0, "KURST", kurst);
			dsExchange.setColumn(0, "FCURR", waerkBas);
			dsExchange.setColumn(0, "TCURR", waerkCon);
			dsExchange.setColumn(0, "GDATU", gdatu);
			if (Double.parseDouble(exchangeBas) > 0 && Double.parseDouble(exchangeCon) > 0) {

				BigDecimal exchangR_dec = new BigDecimal(Double.parseDouble(exchangeBas) / Double.parseDouble(exchangeCon));				
				dsExchange.setColumn(0, "UKURS", exchangR_dec.setScale(4,BigDecimal.ROUND_UP).toString());
			}		
			
		} catch( Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return dsExchange;
	}	
*/
	public void updateZSDT1054(Ses0171 param) { // 견적Header(ZSDT1054) 변경
		Ses0171Dao.updateZSDT1054(param);
	}  

	public void insertZSDT1050(Ses0171 param) {	// 견적Header(ZSDT1054) 변경
		Ses0171Dao.insertZSDT1050(param);
	}  

	public void deleteZSDT1050(Ses0171 param) {
		Ses0171Dao.deleteZSDT1050(param);
	} 

	public void updateFlagConfirmZSDT1046(Ses0171 param) {
		Ses0171Dao.updateFlagConfirmZSDT1046(param);
	}

	public void updateCostZSDT1047(Ses0171 param) {	
		Ses0171Dao.updateCostZSDT1047(param);
	}

	public void updateCostZSDT1046(Ses0171 param) {	
		Ses0171Dao.updateCostZSDT1046(param);
	}
}
