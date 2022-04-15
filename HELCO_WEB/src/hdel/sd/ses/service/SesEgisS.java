package hdel.sd.ses.service;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.com.domain.ExchangeVO;
import hdel.sd.com.service.ExchangeS;
import hdel.sd.ses.dao.SesEgisD;
import hdel.sd.ses.domain.SesEgis;
import hdel.sd.ses.domain.SesEgisParamVO;
import tit.util.DatasetUtility;
import webservice.stub.sap.SAPStub;
import webservice.stub.sap.ZWEB_CO4_COST_INSStub;

@Service
public class SesEgisS extends SrmService {

	@Autowired
	private ExchangeS ExchangeS;

	Logger logger = Logger.getLogger(this.getClass());
	
	private SesEgisD SesEgisDao;

	public void createDao(SqlSession sqlSession) {
		SesEgisDao = sqlSession.getMapper(SesEgisD.class);
	}

	public Dataset updateHQPOState(MipParameterVO paramVO, Model model, SqlSession session) {

		Dataset dsHeader = paramVO.getDataset("ds_header");

		try {
			createDao(session);  // DAO생성

			String mandt = "";
			String qtnum = "";
			String qtser   = "";
			String userId  = "";
			String pspid_co  = "";

			for (int i=0; i<dsHeader.getRowCount(); i++) {

				mandt 	= DatasetUtility.getString(dsHeader, i, "MANDT" );
				qtnum 	= DatasetUtility.getString(dsHeader, i, "QTNUM");
				qtser   = DatasetUtility.getString(dsHeader, i, "QTSER"  );
//				pspid_co= DatasetUtility.getString(dsHeader, i, "PSPID_CO"  );
				pspid_co= DatasetUtility.getString(dsHeader, i, "QTGBN");
				userId 	= paramVO.getVariable("G_USER_ID");

				if (mandt == null) mandt  = paramVO.getVariable("G_MANDT");
				if (qtnum == null) qtnum  = "";
				if (qtser   == null) qtser    = "0";
				if (userId  == null) userId  = "";
				if (pspid_co  == null) pspid_co  = "";

				SesEgisParamVO param = new SesEgisParamVO();
				param.setMandt(mandt);
				param.setQtnum(qtnum);
				param.setQtser(qtser);
				param.setPspid_co(pspid_co);
				param.setUuser(userId);

				updateHQPO1046(param);
				updateHQPO1091(param);

				dsHeader.setColumn(i, "MANDT", paramVO.getVariable("G_MANDT"));
				dsHeader.setColumn(i, "QTNUM", qtnum);
				dsHeader.setColumn(i, "QTSER", qtser);
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dsHeader;
	}

	public int updateHQPO1046(SesEgisParamVO param) {
		return SesEgisDao.updateHQPO1046(param);
	}

	public int updateHQPO1091(SesEgisParamVO param) {
		return SesEgisDao.updateHQPO1091(param);
	}
	
	public MipResultVO updateTradeAmt(MipParameterVO paramVO, SqlSession session) throws RuntimeException, Exception{
		MipResultVO resultVO = new MipResultVO();
		Dataset dsEgisPrice = new Dataset("ds_egis_price");
		dsEgisPrice.addColumn("QTNUM", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.addColumn("QTSEQ", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.addColumn("QTSER", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.addColumn("ZUAM", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.addColumn("ZCOST", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.addColumn("ZEAM", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.setId("ds_egis_price");
			
		String mandt = paramVO.getVariable("G_MANDT");
		String uuser = paramVO.getVariable("G_USER_ID");
		String cuser = paramVO.getVariable("G_USER_ID");
		String srm_estmt_no = "";
		String srm_estmt_seq   = "";
		String srm_estmt_serial_no  = "";
		String scm_estmt_unit_price  = "";
		String scm_estmt_amt  = "";
		String currency  = "";

		ExchangeVO exchangeR = null;
		
		Dataset dsCostIf = paramVO.getDataset("ds_cost_if");

		try {
			createDao(session);  // DAO생성

			srm_estmt_no 			= DatasetUtility.getString(dsCostIf, 0, "SRM_ESTMT_NO");
			srm_estmt_seq			= DatasetUtility.getString(dsCostIf, 0, "SRM_ESTMT_SEQ");
			srm_estmt_serial_no		= DatasetUtility.getString(dsCostIf, 0, "SRM_ESTMT_SERIAL_NO");
			scm_estmt_unit_price	= DatasetUtility.getString(dsCostIf, 0, "SCM_ESTMT_UNIT_PRICE");
			scm_estmt_amt			= DatasetUtility.getString(dsCostIf, 0, "SCM_ESTMT_AMT");
			currency				= DatasetUtility.getString(dsCostIf, 0, "CURRENCY");

			if (srm_estmt_no == null) 			srm_estmt_no  = "";
			if (srm_estmt_seq   == null) 		srm_estmt_seq    = "0";
			if (srm_estmt_serial_no  == null) 	srm_estmt_serial_no  = "";
			if (scm_estmt_unit_price  == null) 	scm_estmt_unit_price  = "0";
			if (scm_estmt_amt  == null) 		scm_estmt_amt  = "0";
			if (currency  == null) 				currency  = "";

			SesEgisParamVO param = new SesEgisParamVO();
			param.setMandt(mandt);
			param.setQtnum(srm_estmt_no);
			param.setQtser(srm_estmt_seq);
			param.setQtseq(srm_estmt_serial_no);
			param.setSrm_estmt_no(srm_estmt_no);
			param.setSrm_estmt_seq(srm_estmt_seq);
			param.setSrm_serial_no(srm_estmt_serial_no);
			param.setScm_unit_price(scm_estmt_unit_price);
			param.setScm_estmt_amt(scm_estmt_amt);
			param.setCurrency(currency);
			param.setUuser(uuser);
			param.setCuser(cuser);			
			
			SesEgisParamVO ratioVO = SesEgisDao.selectTradeRatio(param);
			if(ratioVO != null) {
				param.setRatio(ratioVO.getRatio());
			}else {
				param.setRatio("0");
			}

			SesEgisParamVO urateVO = SesEgisDao.selectUrate(param);
			if(urateVO != null) {
				param.setUrate(urateVO.getUrate());
			}else {
				param.setUrate("0");
			}

			List<SesEgis> qtdata = SesEgisDao.selectQtdata(param);
			if(qtdata.size() > 0) {
				ExchangeVO ex = new ExchangeVO();
				ex.setMandt(mandt);
				ex.setBasedt(qtdata.get(0).getQtdat());
				ex.setFcurr(currency);
				ex.setTcurr("KRW");
				ex.setKurst("Q");
				
				exchangeR = SesEgisDao.selectQtExchange(ex);		
				String exchangeBas = exchangeR.getUkurs();
				
				ex.setFcurr(qtdata.get(0).getWaerk());		//견적 통화단위 
				ex.setTcurr("KRW");
				exchangeR = SesEgisDao.selectQtExchange(ex);		
				String exchangeCon = exchangeR.getUkurs();
				
				if (Double.parseDouble(exchangeBas) > 0 && Double.parseDouble(exchangeCon) > 0) {
					
					BigDecimal exchangeBas_dec = new BigDecimal(exchangeBas);
					BigDecimal exchangeCon_dec = new BigDecimal(exchangeCon);
					BigDecimal exchangeR_dec = exchangeCon_dec.divide(exchangeBas_dec, 4, BigDecimal.ROUND_HALF_UP);
					param.setUkurs(exchangeR_dec.toString());
				} else {
					param.setUkurs("1");
				}
				
			} else {
				//정보가조회되지 않습니다.
				throw new BizException("CW10065");
			}
			
			int rtn =0;
			int rtn1 =0;
			int rtn2 =0;

			Double znumber = Double.parseDouble(param.getScm_estmt_amt()) / Double.parseDouble(param.getScm_unit_price());
			BigDecimal unitPrice = plusCargoInsuranceCost(paramVO, param.getScm_unit_price(), param.getQtnum(), param.getQtser());
			BigDecimal amt = new BigDecimal(unitPrice.toString()).multiply(new BigDecimal(znumber));
			param.setScm_unit_price(unitPrice.toString());
			param.setScm_estmt_amt(amt.toString());

			rtn = SesEgisDao.updateTradeZSDT1050(param);
			rtn1 = SesEgisDao.updateTradeZSDT1047(param);
			rtn2 = SesEgisDao.updateTradeZSDT1046(param);			
			
			if(rtn > 0 && rtn1 > 0 && rtn2 > 0 ) {
				List<SesEgis> egisPrice = SesEgisDao.selectEgisPrice(param);
				for (int i = 0; i < egisPrice.size(); i++) {
					int rows = dsEgisPrice.appendRow();
					dsEgisPrice.setColumn(rows, "QTNUM", egisPrice.get(i).getQtnum());
					dsEgisPrice.setColumn(rows, "QTSEQ", egisPrice.get(i).getQtseq());
					dsEgisPrice.setColumn(rows, "QTSER", egisPrice.get(i).getQtser());
					dsEgisPrice.setColumn(rows, "ZUAM",  egisPrice.get(i).getZuam());
					dsEgisPrice.setColumn(rows, "ZEAM",  egisPrice.get(i).getZeam());
				}			
			} else {
				int rows = dsEgisPrice.appendRow();
				dsEgisPrice.setColumn(rows, "QTNUM", srm_estmt_no);
				dsEgisPrice.setColumn(rows, "QTSEQ", srm_estmt_seq);
				dsEgisPrice.setColumn(rows, "QTSER", srm_estmt_serial_no);
				dsEgisPrice.setColumn(rows, "ZUAM",  "0");
				dsEgisPrice.setColumn(rows, "ZEAM",  "0");				
			}
			resultVO.addDataset(dsEgisPrice);				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultVO;
	}	
	
	public MipResultVO updateNegoPrice(MipParameterVO paramVO, SqlSession session) throws RuntimeException, Exception{
		
		MipResultVO resultVO = new MipResultVO();
		Dataset dsEgisPrice = new Dataset("ds_egis_price");
		dsEgisPrice.addColumn("QTNUM", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.addColumn("QTSEQ", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.addColumn("QTSER", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.addColumn("ZUAM", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.addColumn("ZCOST", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.addColumn("TSP", ColumnInfo.COLTYPE_STRING, 256);
		dsEgisPrice.addColumn("CURRENCY", ColumnInfo.COLTYPE_STRING, 256);  
		dsEgisPrice.setId("ds_egis_price");

		Dataset dsError = new Dataset("ds_error");
		dsError.addColumn("ERRCODE", ColumnInfo.COLTYPE_STRING, 256);  
		dsError.addColumn("ERRMSG", ColumnInfo.COLTYPE_STRING, 256);  
		dsError.setId("ds_error");

		String mandt = paramVO.getVariable("G_MANDT");
		String uuser = paramVO.getVariable("G_USER_ID");
		String cuser = paramVO.getVariable("G_USER_ID");
		String f_amttp = paramVO.getVariable("F_AMTTP");   //1 : 원가 , 2 : 견적가

		String qtnum 		  = "";
		String qtser   		  = "";
		String qtseq  		  = "";
		String unit_price 	  = "";
		String estmt_amt      = "";
		String currency  	  = "";

		BigDecimal zuam 	  = new BigDecimal("0.0");	// 단가환산				 
		BigDecimal indRate 	  = new BigDecimal("0.0");	// 간접비율	
		BigDecimal zuams   	  = new BigDecimal("0.0");	// 단가환산
		BigDecimal incIndZuams = new BigDecimal("0.0");	// 간접비 포함 단가
		BigDecimal zcost 	  = new BigDecimal("0.0");	// 원가 
		BigDecimal urate	  = new BigDecimal("0.0"); 	// 간접비 계산용 변수
		
		ExchangeVO exchangeR = null;
		
		Dataset dsCond = paramVO.getDataset("ds_cond");
		
		try {
			createDao(session);  // DAO생성

			qtnum 		= DatasetUtility.getString(dsCond, 0, "QTNUM");
			qtser		= DatasetUtility.getString(dsCond, 0, "QTSER");
			qtseq		= DatasetUtility.getString(dsCond, 0, "QTSEQ");
			unit_price	= DatasetUtility.getString(dsCond, 0, "ZUAM");
			estmt_amt	= DatasetUtility.getString(dsCond, 0, "ZAMT");
			currency	= DatasetUtility.getString(dsCond, 0, "CURRENCY");

			if (qtnum == null) 			qtnum   = "";
			if (qtser   == null) 		qtser   = "0";
			if (qtseq  == null) 		qtseq  	= "";
			if (unit_price == null) 	unit_price  = "0";
			if (estmt_amt  == null) 	estmt_amt  = "0";
			if (currency  == null) 		currency  = "";
			
			SesEgisParamVO param = new SesEgisParamVO();
			param.setMandt(mandt);
			param.setQtnum(qtnum);
			param.setQtser(qtser);
			param.setQtseq(qtseq);
			param.setCurrency(currency);
			param.setUuser(uuser);
			
			SesEgisParamVO urateVO = SesEgisDao.selectUrate(param);
			if(urateVO != null) {
				param.setUrate(urateVO.getUrate());
			}else {
				param.setUrate("0");
			}

			List<SesEgis> qtdata = SesEgisDao.selectQtdata(param);

			if(qtdata.size() > 0) {
				param.setCuser(cuser);			
				ExchangeVO ex = new ExchangeVO();
				ex.setBasedt(qtdata.get(0).getQtdat());
				ex.setFcurr(currency);
				ex.setMandt(mandt);
				ex.setTcurr("KRW");
				ex.setKurst("Q");
				
				exchangeR = SesEgisDao.selectQtExchange(ex);		
				String exchangeBas = exchangeR.getUkurs();
				
				ex.setFcurr(qtdata.get(0).getWaerk());		//견적 통화단위 
				ex.setTcurr("KRW");
				exchangeR = SesEgisDao.selectQtExchange(ex);		
				String exchangeCon = exchangeR.getUkurs();
				
				if (Double.parseDouble(exchangeBas) > 0 && Double.parseDouble(exchangeCon) > 0) {
					
					BigDecimal exchangeBas_dec = new BigDecimal(exchangeBas);
					BigDecimal exchangeCon_dec = new BigDecimal(exchangeCon);
					BigDecimal exchangeR_dec = exchangeCon_dec.divide(exchangeBas_dec, 4, BigDecimal.ROUND_HALF_UP);
					param.setUkurs(exchangeR_dec.toString());
				} else {
					param.setUkurs("1");
				}
			} else {
				//정보가조회되지 않습니다.
				throw new BizException("CW10065");
			}
			
			int rtn  = 0;
			int rtn1 = 0;
			int rtn2 = 0;			
			/*
			 	구분자: 1 원가 ,2 견적가
				1원가   : 
						ZSDT1050  UPDATE 
				        ZSDT1047  UPDATE  
				        ZSDT1046  UPDATE
				        ZUAM 		==> ZUAM
				        ZAMT * 간접비  ==> ZCOST 
				        ZEAM 갱신안함
				        ZUAM,ZCOST,SHANG 갱신
				2견적가 :           
				        ZSDT1047  UPDATE
				        ZSDT1046 UPDATE
				        ZUAM ==> ZUAM
				        ZAMT ==> ZEAM
				        ZEAM,ZUAM,ZCOST,SHANG 갱신
			*/
			
			Double 	   znumber    = Double.parseDouble(estmt_amt) / Double.parseDouble(unit_price);
			if("1".equals(f_amttp)) {   		//원가
				
				indRate 	  = new BigDecimal(urateVO.getUrate().toString());							// 간접비율	
				zuams   	  = new BigDecimal(unit_price.toString()).divide(new BigDecimal(param.getUkurs()), 2, BigDecimal.ROUND_HALF_UP);	// 단가환산
				incIndZuams   = zuams.multiply(indRate).setScale(2, BigDecimal.ROUND_HALF_UP);
				
				zuam  	  	  = plusCargoInsuranceCost(paramVO, incIndZuams.toString(), qtnum, qtser);		// 단가 = 간접비 + 원가 + 보험료		
				zcost 	  	  = new BigDecimal(zuam.toString()).multiply(new BigDecimal(znumber));
				
				urate	  	  = indRate.subtract(new BigDecimal(1)).multiply(new BigDecimal(100));

				param.setZuam(zuam.setScale(2, BigDecimal.ROUND_HALF_UP).toString());   		 
				param.setZcost(zcost.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				param.setZuams(zuams.toString());
				param.setUrate(urate.toString());
				
//				System.out.print("\n@@@ updateNegoPrice service incIndZuams :" + incIndZuams.toString() + ",  zuam :" + zuam.toString());
			
			} else if("2".equals(f_amttp)) {   		//견적가 
				
				param.setZuam(unit_price);
				param.setZamt(estmt_amt);
			}
			
			if("1".equals(f_amttp)) {   		//원가	
				
				rtn = SesEgisDao.updateZSDT1050(param);			
				rtn1 = SesEgisDao.updateCostZSDT1047(param);
				rtn2 = SesEgisDao.updateCostZSDT1046(param);

				BigDecimal marginRate, TSPAmt;
				SesEgisParamVO ratioVO = SesEgisDao.selectTradeRatio(param);
				if(ratioVO != null) {
					marginRate = new BigDecimal(ratioVO.getRatio());
				}else {
					marginRate = new BigDecimal(1);
				}
				
				if(rtn > 0 && rtn1 > 0 && rtn2 > 0 ) {
			
					List<SesEgis> egisPrice = SesEgisDao.selectEgisNegoPrice(param);
					for (int i = 0; i < egisPrice.size(); i++) {
						
						int rows = dsEgisPrice.appendRow();
						
						BigDecimal zcostAmt = new BigDecimal(egisPrice.get(i).getZcost());
						TSPAmt   = zcostAmt.multiply(marginRate);
								
						dsEgisPrice.setColumn(rows, "QTNUM", egisPrice.get(i).getQtnum());
						dsEgisPrice.setColumn(rows, "QTSEQ", egisPrice.get(i).getQtseq());
						dsEgisPrice.setColumn(rows, "QTSER", egisPrice.get(i).getQtser());
						dsEgisPrice.setColumn(rows, "ZUAM",  egisPrice.get(i).getZuam());
						dsEgisPrice.setColumn(rows, "ZCOST",  egisPrice.get(i).getZcost());
						dsEgisPrice.setColumn(rows, "TSP",  TSPAmt.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
						dsEgisPrice.setColumn(rows, "CURRENCY",  egisPrice.get(i).getWaerk());						
					}			
				} else {
					
					TSPAmt   = new BigDecimal(estmt_amt.toString()).multiply(marginRate);
					
					int rows = dsEgisPrice.appendRow();
					dsEgisPrice.setColumn(rows, "QTNUM", qtnum);
					dsEgisPrice.setColumn(rows, "QTSEQ", qtseq);
					dsEgisPrice.setColumn(rows, "QTSER", qtser);
					dsEgisPrice.setColumn(rows, "ZUAM",  zuam.toString());
					dsEgisPrice.setColumn(rows, "ZCOST", estmt_amt.toString());				
					dsEgisPrice.setColumn(rows, "TSP",  TSPAmt.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					dsEgisPrice.setColumn(rows, "CURRENCY",  currency);				
				}
				
			} else if("2".equals(f_amttp)) {   		//견적가이면
				
				rtn1 = SesEgisDao.updateZeamZSDT1047(param);
				rtn2 = SesEgisDao.updateCostZSDT1046(param);							
				
			} else {
				dsError.setColumn(0, "ERRCODE", "F");
				dsError.setColumn(0, "ERRMSG", "F_AMTTP : "+f_amttp);
			}

			dsError.appendRow();
			dsError.setColumn(0, "ERRCODE", "S");
			dsError.setColumn(0, "ERRMSG", "Success");
//			resultVO.addDataset(dsError);
		} catch (Exception e) {
			e.printStackTrace();
			dsError.appendRow();
			dsError.setColumn(0, "ERRCODE", "F");
			dsError.setColumn(0, "ERRMSG", e.getMessage());
		}
		
		resultVO.addDataset(dsEgisPrice);				
		resultVO.addDataset(dsError);
		
		return resultVO;
	}	

    public BigDecimal plusCargoInsuranceCost(MipParameterVO mipParam, String basicCost, String qtnum, String qtser) {
		BigDecimal totCost= new BigDecimal(basicCost);
		BigDecimal insCost = new BigDecimal(0);

		ZWEB_CO4_COST_INSStub stub 
    		= (ZWEB_CO4_COST_INSStub) new SAPStub(mipParam.getVariable("G_SYSID"), mipParam.getVariable("G_MANDT"), mipParam.getVariable("G_LANG"))
    			.create(ZWEB_CO4_COST_INSStub.class);

    	ZWEB_CO4_COST_INSStub.ZWEB_CO4_COST_INS wsParam;
		wsParam = new ZWEB_CO4_COST_INSStub.ZWEB_CO4_COST_INS();
		wsParam.setI_TSP(ZWEB_CO4_COST_INSStub.Curr152.Factory.fromString(basicCost, ""));
		wsParam.setI_QTNUM(ZWEB_CO4_COST_INSStub.Char10.Factory.fromString(qtnum, ""));
		wsParam.setI_QTSER(new org.apache.axis2.databinding.types.UnsignedByte(qtser));

		ZWEB_CO4_COST_INSStub.ZWEB_CO4_COST_INSResponse response;
		try {
			response = stub.zWEB_CO4_COST_INS(wsParam);
			if(response.getE_TYPE() == 0)
				insCost = new BigDecimal(response.getE_INS().toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		System.out.print("\n@@@ plusCargoInsuranceCost service totCost :" + totCost.toString() + ",  insCost :" + insCost.toString());
		
		totCost = totCost.add(insCost);
		return totCost;
    }
}
