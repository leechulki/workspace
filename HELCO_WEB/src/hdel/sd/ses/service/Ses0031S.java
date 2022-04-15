package hdel.sd.ses.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.helco.xf.comm.FSPPopClientHelper;
import com.helco.xf.comm.JSONUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.sz.ui.data.DataSet;
import com.sz.ui.data.DataSetList;
import com.sz.ui.data.DataTypes;
import com.sz.ui.data.PlatformData;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.exception.BizException;
import hdel.lib.service.MailService;
import hdel.lib.service.SrmService;
import hdel.lib.service.ZSDT0167S;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoQtNumberS;
import hdel.sd.com.service.ExchangeS;
import hdel.sd.ses.dao.Ses0031D;
import hdel.sd.ses.domain.Ses0030;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.domain.Ses0355;
import hdel.sd.ses.domain.Ses0355ParamVO;
import hdel.sd.sso.domain.ZCOBT302;
import hdel.sd.sso.service.Sso0050S;
import tit.util.DatasetUtility;

@Service
public class Ses0031S extends SrmService {

	@Autowired
	private AutoQtNumberS Autoservice;	// 견적번호채번 서비스 

	@Autowired
	private Ses0355S service0355;

	private Ses0031D Ses0031Dao;

	@Autowired
	private Ses0030S service0030;

	@Autowired
	private Sso0050S service0050S;

	@Autowired
	private ExchangeS ExchangeS;
	
//	Ses0031ParamVO param = new Ses0031ParamVO();
	
	public void createDao(SqlSession sqlSession) {
		Ses0031Dao = sqlSession.getMapper(Ses0031D.class);
	}

	public List<Ses0031> searchGtype(Ses0031ParamVO param) {
		return Ses0031Dao.selectListGtype(param);
	}

	public List<Ses0031> searchSO(Ses0031ParamVO param) {
		return Ses0031Dao.selectListSO(param);
	}

	public List<Ses0031> searchQtser(Ses0031ParamVO param) {
		return Ses0031Dao.selectListQtser(param);
	}

	public List<Ses0031> searchTemplate(Ses0031ParamVO param) {
		return Ses0031Dao.selectListTemplate(param);
	}

	public List<Ses0031> searchKsml(Ses0031ParamVO param) {
		return Ses0031Dao.selectListKsml(param);
	}

	public List<Ses0031> searchSpec(Ses0031ParamVO param) {
		return Ses0031Dao.selectListSpec(param);
	}

	public List<Ses0031> searchZcobt302(Ses0031ParamVO param) {
		return Ses0031Dao.selectListZcobt302(param);
	}

	public List<Ses0031> searchZcobt309(Ses0031ParamVO param) {
		//return Ses0031Dao.selectListZcobt309(param);
		return Ses0031Dao.selectListZsdt1054D(param);
	}
	
	public List<Ses0031> searchBlockName(Ses0031ParamVO param) {
		return Ses0031Dao.selectListBlockName(param);
	}

	public List<Ses0031> searchClass(Ses0031ParamVO param) {
		return Ses0031Dao.selectListClass(param);
	}

	public List<Ses0031> searchAcapa(Ses0031ParamVO param) {
		return Ses0031Dao.selectListAcapa(param);
	}

/* -----< 2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start
	public List<Ses0031> searchExchange(Ses0031ParamVO param) {
		return Ses0031Dao.selectListExchange(param);
	}

	public Ses0031 searchNewExchange(Ses0031ParamVO param, SqlSession session, String G_LANG) throws Exception{
		try	{
			Ses0031 exchangeVO = null;
			if ("Q".equals(param.getF_flag()))	{	// 견적에서 호출되는 경우
				createDao(session); // DAO생성
				exchangeVO = Ses0031Dao.selectNewExchange(param.getMandt(), param.getSdate());
			}else	{
				service0050S.createDao(session);
				Sso0050ParamVO param0050 = new Sso0050ParamVO();
				
				param0050.setMANDT(param.getMandt());
				param0050.setTCURRDATE(param.getSdate());
				
				List<Sso0050VO> list = service0050S.searchExchange(param0050);
				
				for(int i = 0; i < list.size(); i++)	{
					exchangeVO = new Ses0031();

					exchangeVO.setMandt(list.get(i).getMANDT());
					exchangeVO.setKrweur(list.get(i).getKRWEUR());
					exchangeVO.setKrwusd(list.get(i).getKRWUSD());
					exchangeVO.setKrwjpy(list.get(i).getKRWJPY());
				}
			}
			
			if (exchangeVO == null)	{
				String error = "";
				if (!"ko".equals(G_LANG))	{
					error = "CE10007";
				}else	{
					error = "CE10007";
				}
				throw new RequireException(error);
			}
	
			return exchangeVO;
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
 */ //-----> 2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End

	public int getSonnrCount(String sMandt, String sQtnum, String sSonnr) {
		return Ses0031Dao.selectSonnrCount(sMandt, sQtnum, sSonnr);
	}

	public Dataset saveHeader(MipParameterVO paramVO, Model model, SqlSession session) throws Exception {

		Dataset dsHeader   = paramVO.getDataset("ds_header");
		Dataset dsDetail   = paramVO.getDataset("ds_detail");
		Dataset dsTemplate = paramVO.getDataset("ds_template");

		try {
			createDao(session);  // DAO생성
		
			String flag              = "";
			String mandt           = "";
			String qtnum           = "";
			String qtser             = "";
			String qtgbn            = "";
			String auart              = "";
			String egisFlag          = "";

			Ses0031ParamVO param = new Ses0031ParamVO();
			
			for (int i=0;i< dsHeader.getRowCount(); i++) {

				flag = DatasetUtility.getString(dsHeader, i, "FLAG");

				if ( flag != null) {			
					
					mandt  = DatasetUtility.getString(dsHeader, i, "MANDT") == null ? paramVO.getVariable("G_MANDT") : DatasetUtility.getString(dsHeader, i, "MANDT");
					qtnum  = DatasetUtility.getString(dsHeader, i, "QTNUM") == null ? "" : DatasetUtility.getString(dsHeader, i, "QTNUM");
					auart  = DatasetUtility.getString(dsHeader, i, "AUART") == null ? "" : DatasetUtility.getString(dsHeader, i, "AUART");
					qtser  = DatasetUtility.getString(dsHeader, i, "QTSER") == null ? "0" : DatasetUtility.getString(dsHeader, i, "QTSER");
					qtgbn  = DatasetUtility.getString(dsHeader, i, "QTGBN") == null ? "" : DatasetUtility.getString(dsHeader, i, "QTGBN");
					egisFlag = DatasetUtility.getString(dsHeader, i, "EGIS_FLAG") == null ? "" : DatasetUtility.getString(dsHeader, i, "EGIS_FLAG");
					
					if ("".equals(qtnum) || qtnum == "")	{	// 2013.02.05 변경
						AutoNumberParamVO AutoNumberParam = new AutoNumberParamVO();

						Autoservice.createDao(session);

						AutoNumberParam.setMandt	 (mandt); 	// SAP CLIENT
						AutoNumberParam.setDeptFlag(auart);	// 기종
						AutoNumberParam.setSoQtFlag("2");		// 채번구분(0:사업계획, 1:수주계획 , 2:견적)

						List<AutoNumberVO> listVO = Autoservice.AutoQtNumber(AutoNumberParam);// 견적번호 채번 서비스 호출
						qtnum = listVO.get(0).getNumber().toString();
					}

					param.setMandt(mandt);
					param.setQtnum(qtnum);
					param.setAuart(auart);

					if (qtser.equals("0")) {
						List<Ses0031> list = searchQtser(param);	// 서비스CALL
						qtser = list.get(0).getQtser().toString();
					}
					
					param.setQtser(qtser);				
					param.setQtdat(DatasetUtility.getString(dsHeader, i, "QTDAT") == null ? "" 		: DatasetUtility.getString(dsHeader, i, "QTDAT"));                                  					
					param.setSpart(DatasetUtility.getString(dsHeader, i, "SPART")  == null ? "" 	: DatasetUtility.getString(dsHeader, i, "SPART"));                                  					
					param.setQtgbn		 ( "".equals(qtgbn) ?  "A" : DatasetUtility.getString(dsHeader, i, "QTGBN"));                       
					param.setZbdtyp		 (DatasetUtility.getString(dsHeader, i, "ZBDTYP")  == null ? "" 	: DatasetUtility.getString(dsHeader, i, "ZBDTYP"));                                  					
					param.setZprgflg	 (DatasetUtility.getString(dsHeader, i, "ZPRGFLG") == null ? "" 	: DatasetUtility.getString(dsHeader, i, "ZPRGFLG"));                                					
					param.setVkbur		 (DatasetUtility.getString(dsHeader, i, "VKBUR") == null ? "" 		: DatasetUtility.getString(dsHeader, i, "VKBUR"));                                   					
					param.setVkgrp		 (DatasetUtility.getString(dsHeader, i, "VKGRP") == null ? "" 		: DatasetUtility.getString(dsHeader, i, "VKGRP"));                                   					
					param.setZkunnr		 (DatasetUtility.getString(dsHeader, i, "ZKUNNR") == null ?  "" 	: DatasetUtility.getString(dsHeader, i, "ZKUNNR"));                                   				
					param.setZagnt		 (DatasetUtility.getString(dsHeader, i, "ZAGNT") == null ? "" 		: DatasetUtility.getString(dsHeader, i, "ZAGNT"));                                   					
					param.setKunnr		 (DatasetUtility.getString(dsHeader, i, "KUNNR") == null ? "" 		: DatasetUtility.getString(dsHeader, i, "KUNNR"));  					                                                                  					
					param.setZcst        (DatasetUtility.getString(dsHeader, i, "ZCST")        == null       ? "":    DatasetUtility.getString(dsHeader, i, "ZCST"));                                   					  
					param.setZgnm        (DatasetUtility.getString(dsHeader, i, "ZGNM")        == null    ? "":       DatasetUtility.getString(dsHeader, i, "ZGNM"));                                     					
					param.setZdev        (DatasetUtility.getString(dsHeader, i, "ZDEV")        == null     ? "":      DatasetUtility.getString(dsHeader, i, "ZDEV"));                                    					  
					param.setGsnam       (DatasetUtility.getString(dsHeader, i, "GSNAM")       == null    ? "":       DatasetUtility.getString(dsHeader, i, "GSNAM"));                                     				  	
					param.setZregn       (DatasetUtility.getString(dsHeader, i, "ZREGN")       == null     ? "":      DatasetUtility.getString(dsHeader, i, "ZREGN"));                                    					
					param.setZpid        (DatasetUtility.getString(dsHeader, i, "ZPID")        == null     ?  "":     DatasetUtility.getString(dsHeader, i, "ZPID"));                                    					  
					param.setZtel        (DatasetUtility.getString(dsHeader, i, "ZTEL")        == null      ? "":     DatasetUtility.getString(dsHeader, i, "ZTEL"));                                   					  
					param.setZaddr_zpost (DatasetUtility.getString(dsHeader, i, "ZADDR_ZPOST") == null   ? "":        DatasetUtility.getString(dsHeader, i, "ZADDR_ZPOST"));                               				  	
					param.setZaddr_adr1  (DatasetUtility.getString(dsHeader, i, "ZADDR_ADR1")  == null   ? "":        DatasetUtility.getString(dsHeader, i, "ZADDR_ADR1"));                                				  	
					param.setZaddr_adr2  (DatasetUtility.getString(dsHeader, i, "ZADDR_ADR2")  == null   ? "":        DatasetUtility.getString(dsHeader, i, "ZADDR_ADR2"));                                				  	
					param.setSoable      (DatasetUtility.getString(dsHeader, i, "SOABLE")      == null     ? "":      DatasetUtility.getString(dsHeader, i, "SOABLE"));                                  					  
					param.setZestdat     (DatasetUtility.getString(dsHeader, i, "ZESTDAT")     == null    ? "":       DatasetUtility.getString(dsHeader, i, "ZESTDAT"));                                 					  
					param.setDeldat      (DatasetUtility.getString(dsHeader, i, "DELDAT")      == null    ? "":       DatasetUtility.getString(dsHeader, i, "DELDAT"));                                  					  
					param.setZdvmts      (DatasetUtility.getString(dsHeader, i, "ZDVMTS")      == null   ? "0":       DatasetUtility.getString(dsHeader, i, "ZDVMTS"));                                   					
					param.setZsum_znumber(DatasetUtility.getString(dsHeader, i, "ZSUM_ZNUMBER")== null? "0":          DatasetUtility.getString(dsHeader, i, "ZSUM_ZNUMBER"));                                 					
					param.setZsum_zcost  (DatasetUtility.getString(dsHeader, i, "ZSUM_ZCOST")  == null  ? "0.00":     DatasetUtility.getString(dsHeader, i, "ZSUM_ZCOST"));                                				  	
					param.setZsum_zeam   (DatasetUtility.getString(dsHeader, i, "ZSUM_ZEAM")   == null  ? "0.00":     DatasetUtility.getString(dsHeader, i, "ZSUM_ZEAM"));                                  					
					param.setZsum_shang  (DatasetUtility.getString(dsHeader, i, "ZSUM_SHANG")  == null  ? "0":        DatasetUtility.getString(dsHeader, i, "ZSUM_SHANG"));                                  			  		
					param.setInco        (DatasetUtility.getString(dsHeader, i, "INCO")        == null     ? "":      DatasetUtility.getString(dsHeader, i, "INCO"));                                    					  
					param.setZicif_pprt  (DatasetUtility.getString(dsHeader, i, "ZICIF_PPRT")  == null      ? "0":    DatasetUtility.getString(dsHeader, i, "ZICIF_PPRT"));                              					  
					param.setZicif_ppct  (DatasetUtility.getString(dsHeader, i, "ZICIF_PPCT")  == null     ? "":      DatasetUtility.getString(dsHeader, i, "ZICIF_PPCT"));                              					  
					param.setZicif_ppcd  (DatasetUtility.getString(dsHeader, i, "ZICIF_PPCD")  == null     ? "":      DatasetUtility.getString(dsHeader, i, "ZICIF_PPCD"));                               					
					param.setZicif_1strt (DatasetUtility.getString(dsHeader, i, "ZICIF_1STRT") == null      ? "0":    DatasetUtility.getString(dsHeader, i, "ZICIF_1STRT"));                            					  
					param.setZicif_1stct (DatasetUtility.getString(dsHeader, i, "ZICIF_1STCT") == null      ? "":     DatasetUtility.getString(dsHeader, i, "ZICIF_1STCT"));                            					  
					param.setZicif_1stcd (DatasetUtility.getString(dsHeader, i, "ZICIF_1STCD") == null     ? "":      DatasetUtility.getString(dsHeader, i, "ZICIF_1STCD"));                             					  
					param.setZicif_2strt (DatasetUtility.getString(dsHeader, i, "ZICIF_2STRT") == null      ? "0":    DatasetUtility.getString(dsHeader, i, "ZICIF_2STRT"));                            					  
					param.setZicif_2stct (DatasetUtility.getString(dsHeader, i, "ZICIF_2STCT") == null      ? "":     DatasetUtility.getString(dsHeader, i, "ZICIF_2STCT"));                            					  
					param.setZicif_2stcd (DatasetUtility.getString(dsHeader, i, "ZICIF_2STCD") == null     ? "":      DatasetUtility.getString(dsHeader, i, "ZICIF_2STCD"));                             					  
					param.setZicif_rmrt  (DatasetUtility.getString(dsHeader, i, "ZICIF_RMRT")  == null     ? "0":     DatasetUtility.getString(dsHeader, i, "ZICIF_RMRT"));                              					  
					param.setZicif_rmct  (DatasetUtility.getString(dsHeader, i, "ZICIF_RMCT")  == null     ? "":      DatasetUtility.getString(dsHeader, i, "ZICIF_RMCT"));                              					  
					param.setZicif_rmcd  (DatasetUtility.getString(dsHeader, i, "ZICIF_RMCD")  == null    ? "":       DatasetUtility.getString(dsHeader, i, "ZICIF_RMCD"));                               					
					param.setSprmk1      (DatasetUtility.getString(dsHeader, i, "SPRMK1")      == null   ? "":        DatasetUtility.getString(dsHeader, i, "SPRMK1"));                                    				  	
					param.setSprmk2      (DatasetUtility.getString(dsHeader, i, "SPRMK2")      == null   ? "":        DatasetUtility.getString(dsHeader, i, "SPRMK2"));                                    				  	
					param.setSprmk3      (DatasetUtility.getString(dsHeader, i, "SPRMK3")      == null   ? "":        DatasetUtility.getString(dsHeader, i, "SPRMK3"));                                    				  	
					param.setSprmk4      (DatasetUtility.getString(dsHeader, i, "SPRMK4")      == null   ? "":        DatasetUtility.getString(dsHeader, i, "SPRMK4"));                                    				  	
					param.setSprmk5      (DatasetUtility.getString(dsHeader, i, "SPRMK5")      == null   ? "":        DatasetUtility.getString(dsHeader, i, "SPRMK5"));                                    				  	
					param.setSprmk6      (DatasetUtility.getString(dsHeader, i, "SPRMK6")      == null   ? "":        DatasetUtility.getString(dsHeader, i, "SPRMK6"));                                    				  	
					param.setSprmk7      (DatasetUtility.getString(dsHeader, i, "SPRMK7")      == null   ? "":        DatasetUtility.getString(dsHeader, i, "SPRMK7"));                                    				  	
					param.setSprmk8      (DatasetUtility.getString(dsHeader, i, "SPRMK8")      == null   ? "":        DatasetUtility.getString(dsHeader, i, "SPRMK8"));                                    				  	
					param.setSprmk9      (DatasetUtility.getString(dsHeader, i, "SPRMK9")      == null   ? "":        DatasetUtility.getString(dsHeader, i, "SPRMK9"));                                    				  	
					param.setSprmk10     (DatasetUtility.getString(dsHeader, i, "SPRMK10")     == null  ? "":         DatasetUtility.getString(dsHeader, i, "SPRMK10"));                                   				  	
					param.setZsoflg      (DatasetUtility.getString(dsHeader, i, "ZSOFLG")      == null     ? "":      DatasetUtility.getString(dsHeader, i, "ZSOFLG"));                                  					  
					param.setSonnr       (DatasetUtility.getString(dsHeader, i, "SONNR")       == null    ? "":       DatasetUtility.getString(dsHeader, i, "SONNR"));                                    					
					param.setVbeln       (DatasetUtility.getString(dsHeader, i, "VBELN")       == null    ? "":       DatasetUtility.getString(dsHeader, i, "VBELN"));                                   					  
					param.setWaerk       (DatasetUtility.getString(dsHeader, i, "WAERK")       == null    ? "":       DatasetUtility.getString(dsHeader, i, "WAERK"));                                                
					param.setAuart       (auart);                                 					
					param.setCo_ddvrtq   (DatasetUtility.getString(dsHeader, i, "CO_DDVRTQ")   == null   ? "":        DatasetUtility.getString(dsHeader, i, "CO_DDVRTQ"));                                					
					param.setCo_dephtq   (DatasetUtility.getString(dsHeader, i, "CO_DEPHTQ")   == null   ? "":        DatasetUtility.getString(dsHeader, i, "CO_DEPHTQ"));                                 				  	
					param.setCo_dssq     (DatasetUtility.getString(dsHeader, i, "CO_DSSQ")     == null    ? "":       DatasetUtility.getString(dsHeader, i, "CO_DSSQ"));                                  					
					param.setCo_bclcdtq  (DatasetUtility.getString(dsHeader, i, "CO_BCLCDTQ")  == null    ? "":       DatasetUtility.getString(dsHeader, i, "CO_BCLCDTQ"));                               					
					param.setCo_chpitq   (DatasetUtility.getString(dsHeader, i, "CO_CHPITQ")   == null    ? "":       DatasetUtility.getString(dsHeader, i, "CO_CHPITQ"));                                					
					param.setCo_aspc     (DatasetUtility.getString(dsHeader, i, "CO_ASPC")     == null    ? "":       DatasetUtility.getString(dsHeader, i, "CO_ASPC"));                                  					
					param.setCo_dsv1     (DatasetUtility.getString(dsHeader, i, "CO_DSV1")     == null    ? "":       DatasetUtility.getString(dsHeader, i, "CO_DSV1"));                                  					
					param.setCo_dsv1tq   (DatasetUtility.getString(dsHeader, i, "CO_DSV1TQ")   == null   ? "":        DatasetUtility.getString(dsHeader, i, "CO_DSV1TQ"));                                					
					param.setCo_dsv2     (DatasetUtility.getString(dsHeader, i, "CO_DSV2")     == null    ? "":       DatasetUtility.getString(dsHeader, i, "CO_DSV2"));                                  					
					param.setCo_dsv2tq   (DatasetUtility.getString(dsHeader, i, "CO_DSV2TQ")   == null   ? "":        DatasetUtility.getString(dsHeader, i, "CO_DSV2TQ"));                                					
					param.setCuser       (paramVO.getVariable("G_SAP_USER_PM_B")       			 == null ?  "":		paramVO.getVariable("G_SAP_USER_PM_B"));   
					param.setUuser(param.getCuser());
					param.setZlzone      (DatasetUtility.getString(dsHeader, i, "ZLZONE")    == null ?  "":		DatasetUtility.getString(dsHeader, i, "ZLZONE"));                                  					  
					param.setRegion      (DatasetUtility.getString(dsHeader, i, "REGION")    == null ?  "":		DatasetUtility.getString(dsHeader, i, "REGION"));                                  					  
					param.setZagntflg    (DatasetUtility.getString(dsHeader, i, "ZAGNTFLG")  == null ?  "":		DatasetUtility.getString(dsHeader, i, "ZAGNTFLG"));                                					  
					param.setInco2       (DatasetUtility.getString(dsHeader, i, "INCO2")     == null ?  "":		DatasetUtility.getString(dsHeader, i, "INCO2"));                                   					  
					param.setFa_byrgbn   (DatasetUtility.getString(dsHeader, i, "FA_BYRGBN") == null ? 	"":		DatasetUtility.getString(dsHeader, i, "FA_BYRGBN"));                                 				  	
					param.setZdeli       (DatasetUtility.getString(dsHeader, i, "ZDELI")     == null ?  "":		DatasetUtility.getString(dsHeader, i, "ZDELI"));                                  					  
					param.setJgbn        (DatasetUtility.getString(dsHeader, i, "JGBN")      == null ?  "":		DatasetUtility.getString(dsHeader, i, "JGBN"));                                    					  
					param.setAqtnum      (DatasetUtility.getString(dsHeader, i, "AQTNUM")    == null ?  "":		DatasetUtility.getString(dsHeader, i, "AQTNUM"));                                					    
					param.setOppid       (DatasetUtility.getString(dsHeader, i, "OPPID")     == null ?  "":		DatasetUtility.getString(dsHeader, i, "OPPID"));                                  					  
					param.setAutolp      (DatasetUtility.getString(dsHeader, i, "AUTOLP")    == null ?  "":		DatasetUtility.getString(dsHeader, i, "AUTOLP"));    					
					//E-GIS 수신시 EGIS_FLAG='X'이면 저장  20190329 BY LYK	
					param.setEgisFlag(egisFlag);
					param.setEgisEstNo(DatasetUtility.getString(dsHeader, i, "EGIS_EST_NO") == null ? "" : DatasetUtility.getString(dsHeader, i, "EGIS_EST_NO"));
					param.setEgisEstSeq(DatasetUtility.getString(dsHeader, i, "EGIS_EST_SEQ") == null ? "0" : DatasetUtility.getString(dsHeader, i, "EGIS_EST_SEQ"));
				
					ZSDT0167S zsdt0167s = new ZSDT0167S();
					zsdt0167s.createDao(session);

					if(flag.equals("I") || flag.equals("U")) {
						zsdt0167s.insert(paramVO.getVariable("G_MANDT"), qtnum, paramVO.getVariable("TQUOT"), paramVO.getVariable("G_USER_ID"));
						mergeHeader(param);
						mergeZsdt1091(param);    /* 해상운임비 - 견적번호 신규생성및 업데이트 20190226 by lyk*/	
					}
					else if(flag.equals("D")) {
						deleteHeader(param);
						deleteZsdt1091(param);  /* 해상운임비 - 견적번호 삭제 20190226 by lyk*/
						zsdt0167s.delete(paramVO.getVariable("G_MANDT"), qtnum);
					}
					else if (flag.equals("A"))	{
						addHeader(param);
						 /* 해상운임비 - 견적번호- 견적차수+1  20190226 by lyk*/
						addZsdt1091(param);
					}
					saveTemplate(paramVO, model, session, param, dsTemplate);
					saveDetail(paramVO, model, session, param, dsDetail);

//					if(flag.equals("I") || flag.equals("U")) {    /* 신규견적번호 INSERT*/
//						mergeZsdt1091(param);    /* 해상운임비 - 견적번호 신규생성및 업데이트 20190226 by lyk*/	
//					}
//					else if(flag.equals("D")) {
//						deleteZsdt1091(param);  /* 해상운임비 - 견적번호 삭제 20190226 by lyk*/
//					}
//					else if(flag.equals("A")) {   
//						 /* 해상운임비 - 견적번호- 견적차수+1  20190226 by lyk*/
//						addZsdt1091(param);
//					}
				}  //end if
				
				updateZsdt1091Lh(param);

				dsHeader.setColumn(i, "MANDT", paramVO.getVariable("G_MANDT"));
				dsHeader.setColumn(i, "QTNUM", qtnum);
				dsHeader.setColumn(i, "QTSER"  , qtser  );			
			}

//			for (int i=0;i< dsHeader.getRowCount(); i++) { 	// parameter를 데이타셋(dsHeader)에 복사
//				dsHeader.setColumn(i, "MANDT", mandt);
//				dsHeader.setColumn(i, "QTNUM", qtnum);
//				dsHeader.setColumn(i, "QTSER"  , qtser  );
//			}

//			saveTemplate(paramVO, model, session, param, dsTemplate);
//			saveDetail(paramVO, model, session, param, dsDetail);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return dsHeader;
	}

	// 2012.12.31 견적차수 증가/감소 처리 분리
	public Dataset saveQtser(MipParameterVO paramVO, Model model, SqlSession session) throws Exception	{

		Dataset dsHeader         = paramVO.getDataset("ds_header");
		Dataset dsDetail         = paramVO.getDataset("ds_detail");
		Ses0031ParamVO param     = new Ses0031ParamVO();
		Ses0031ParamVO paramCost = new Ses0031ParamVO();

		try
		{
			createDao(session);  // DAO생성
			service0030.createDao(session);
		
			String qtser_flag = "";
			String mandt      = "";
			String qtnum      = "";
			String qtser      = "";
			String zprgflg    = "";
			
			String egisFlag          = "";
			String egisEstNo          = "";
			String egisEstSeq          = "";

			mandt   = paramVO.getVariable("G_MANDT");
			qtnum   = DatasetUtility.getString(dsHeader, 0, "QTNUM");
			qtser   = DatasetUtility.getString(dsHeader, 0, "QTSER");
			zprgflg = "20";		// 견적생성
			qtser_flag = paramVO.getVariable("qtser_flag");
	
			egisFlag = DatasetUtility.getString(dsHeader, 0, "EGIS_FLAG") == null ? "" : DatasetUtility.getString(dsHeader, 0, "EGIS_FLAG");
			egisEstNo = DatasetUtility.getString(dsHeader, 0, "EGIS_EST_NO") == null ? "" : DatasetUtility.getString(dsHeader, 0, "EGIS_EST_NO");
			egisEstSeq	= DatasetUtility.getString(dsHeader, 0, "EGIS_EST_SEQ") == null ? "" : DatasetUtility.getString(dsHeader, 0, "EGIS_EST_SEQ");		

			param.setMandt(mandt);
			param.setQtnum(qtnum);
			param.setQtser(qtser);
			param.setZprgflg(zprgflg);
			param.setCuser(paramVO.getVariable("G_USER_ID"));
			param.setUuser(paramVO.getVariable("G_USER_ID"));
			//E-GIS 수신시 EGIS_FLAG='X'이면 저장  20190329 BY LYK	
			param.setEgisFlag(egisFlag);
			param.setEgisEstNo(egisEstNo);
			param.setEgisEstSeq(egisEstSeq);

			// 원가삭제용 (삭제 감소 시)
			paramCost.setMandt(mandt);
			paramCost.setQtnum(qtnum);
			paramCost.setQtser(qtser);

			// 해당 견적건의 최종 차수 확인
			if ("A".equals(qtser_flag))		{
				List<Ses0030> list = service0030.getMaxQtnumInfo(mandt, qtnum, qtser);
				// 정보가 존재하지 않는 경우
				if (list == null || list.size() <= 0)	{
					throw new BizException("CW10065");
				}
	
				if(list.size() > 0)	{
					// 최종 차수의 진행상태가 수주생성의 경우 생성불가
					if("90".equals(list.get(0).getZprgflg()))	{
						throw new BizException("CW10225");	// 차수증가불가
					}
				}
				//최종차수 정보 획득
				List<Ses0031> listQtser = searchQtser(param);
				if (listQtser == null || listQtser.size() <= 0)		{
					throw new BizException("CW10065");
				}
				param.setMax_qtser(listQtser.get(0).getQtser());
				//견적추가등록
				addQtserZsdt1046(param);
				/* 해상운임비 - 견적번호- 견적차수+1  20190325 by lyk*/				
				addZsdt1091(param);
				addQtserZsdt1047(param);
				addQtserZsdt1048(param);
				
				//수주계획번호의 상태 변경
				param.setSorlt("20");	// 수주결과
				param.setQtser(listQtser.get(0).getQtser());	// 증가된 차수의 수주계획번호 상태 변경.
				updateSorlt(param);
				
				// 재 조회 처리를 위한 QTSER 처리
				dsHeader.setColumn(0, "QTSER", listQtser.get(0).getQtser());
			}else if ("D".equals(qtser_flag))	{
			
				// zcobt309, zcobt302, zcobt202, zsdt1054h, zsdt1054d, zsdt1050 --> 삭제필요
				// 원가정보 삭제
				deleteZcobt309(paramCost);
				deleteZcobt302(paramCost);
				deleteZcobt202(paramCost);
				deleteZsdt1054H(paramCost);
				deleteZsdt1054D(paramCost);
				deleteZsdt1050(paramCost);

				// 견적정보 삭제
				deleteQtserZsdt1048(param);
				deleteQtserZsdt1047(param);
				/* 해상운임비 - 견적번호 삭제 20190325 by lyk*/
				deleteZsdt1091(param);   					
				deleteHeader(param);

				// 수주계획 정보를 체크(견적에 등록된 정보가 존재하지 않는 경우 수주계획을 등록으로 상태 변경);
				String sSonnr;
				int    iSonnrCnt;
				for (int i = 0; i < dsDetail.getRowCount(); i++)	{
					sSonnr    = "";
					iSonnrCnt = 0;
					sSonnr = DatasetUtility.getString(dsDetail, i, "SONNR").trim();
					if (sSonnr != null && !"".equals(sSonnr))	{
						iSonnrCnt = getSonnrCount(mandt, qtnum, sSonnr);
						//iSonnrCnt = Integer.parseInt((String) map.get("SONNR_CNT")); 
						if (iSonnrCnt == 0)	{	// 견적에 등록된 수주계획이 없는경우 (동일 견적번호에서만 조회, 단납기 제외)
							updateQtserZsdt1001(mandt, sSonnr, "10");	// 등록의로 변경
						}
					}
				}
			}

			return dsHeader;
		}catch(Exception e)	{
			throw e;
		}
	}

	public int mergeHeader(Ses0031ParamVO param) {
		return Ses0031Dao.mergeHeader(param);
	}
	
	public int deleteHeader(Ses0031ParamVO param) {
		return Ses0031Dao.deleteHeader(param);
	}
	
	public int addHeader(Ses0031ParamVO param) {
		return Ses0031Dao.addHeader(param);
	}

	public int addQtserZsdt1046(Ses0031ParamVO param) {
		return Ses0031Dao.insertQtserZsdt1046(param);
	}

	public int updateHeader(Ses0031ParamVO param) {
		return Ses0031Dao.updateHeader(param);
	}

	// 2012.12.17 원가산출 후 견적상세 단가, 원가, 시행율 초기화 처리
	public int updateInitCostZSDT1047(Ses0031ParamVO param) {
		return Ses0031Dao.updateInitCostZSDT1047(param);
	}

	// 2012.12.124 원가산출 후 견적 단가, 원가, 시행율 초기화 처리
	public int updateInitCostZSDT1046(Ses0031ParamVO param) {
		return Ses0031Dao.updateInitCostZSDT1046(param);
	}


	public Dataset saveDetail(MipParameterVO paramVO, Model model, SqlSession session, Ses0031ParamVO param, Dataset dsDetail) throws Exception{

//		Dataset dsDetail = paramVO.getDataset("ds_detail");

		try {
			createDao(session);  // DAO생성

			String flag        = "";
			String mandt    = "";
			String qtnum    = "";
			String qtser      = "";
			String qtseq     = "";
			String shangh   = "";
			String zsarea    = "";
			String matnr     = "";
			String zprdgbn  = "";
			String spec      = "";
			String gtype     = "";
			String type1     = "";
			String type2     = "";
			String type3     = "";
			String znumber = "";
			String zacapa    = "";
			String zuse      = "";
			String zopn      = "";
			String zlen       = "";
			String zoutc     = "";
			String zuam     = "";
			String zcost     = "";
			String zcostm   = "";
			String zeam     = "";
			String shang    = "";
			String zrmk      = "";
			String sonnr     = "";
			String userId    = "";
			//E-GIS 송수신 저장 ZSDT1093  20190329 BY LYK
			String egisEstNo    = "";
			String egisEstSeq    = "";
			String egisEstSerno    = "";
			String egisSpecType    = "";
			
			//상해단가 추가  - 신미정 2014.05.27 정동명CJ 요청			
			String zuams     = "";
			String zurate     = "";
			
//			System.out.println("============== dsDetail.getRowCount() = " + dsDetail.getRowCount());

			for (int i=0;i< dsDetail.getRowCount(); i++) {

				flag = DatasetUtility.getString(dsDetail, i, "FLAG");
				
				if ( flag != null) {
					mandt   = DatasetUtility.getString(dsDetail, i, "MANDT"  );
					qtnum   = DatasetUtility.getString(dsDetail, i, "QTNUM"  );
					qtser     = DatasetUtility.getString(dsDetail, i, "QTSER"    );
					qtseq    = DatasetUtility.getString(dsDetail, i, "QTSEQ"    );
					shangh  = DatasetUtility.getString(dsDetail, i, "SHANGH" );
					zsarea   = DatasetUtility.getString(dsDetail, i, "ZSAREA"   );
					matnr    = DatasetUtility.getString(dsDetail, i, "MATNR"   );
					zprdgbn = DatasetUtility.getString(dsDetail, i, "ZPRDGBN");
					spec      = DatasetUtility.getString(dsDetail, i, "SPEC"      );
					gtype     = DatasetUtility.getString(dsDetail, i, "GTYPE"    );
					type1     = DatasetUtility.getString(dsDetail, i, "TYPE1"     );
					type2     = DatasetUtility.getString(dsDetail, i, "TYPE2"     );
					type3     = DatasetUtility.getString(dsDetail, i, "TYPE3"     );
					znumber = DatasetUtility.getString(dsDetail, i, "ZNUMBER");
					zacapa   = DatasetUtility.getString(dsDetail, i, "ZACAPA"   );
					zuse      = DatasetUtility.getString(dsDetail, i, "ZUSE"      );
					zopn     = DatasetUtility.getString(dsDetail, i, "ZOPN"      );
					zlen      = DatasetUtility.getString(dsDetail, i, "ZLEN"       );
					zoutc     = DatasetUtility.getString(dsDetail, i, "ZOUTC"    );
					zuam     = DatasetUtility.getString(dsDetail, i, "ZUAM"     );
					zcost     = DatasetUtility.getString(dsDetail, i, "ZCOST"     );
					zcostm   = DatasetUtility.getString(dsDetail, i, "ZCOSTM"  );
					zeam     = DatasetUtility.getString(dsDetail, i, "ZEAM"     );
					shang    = DatasetUtility.getString(dsDetail, i, "SHANG"   );
					zrmk     = DatasetUtility.getString(dsDetail, i, "ZRMK"     );
					sonnr    = DatasetUtility.getString(dsDetail, i, "SONNR"   );
					userId   = paramVO.getVariable("G_USER_ID");
					
					//상해단가 추가  - 신미정 2014.05.27 정동명CJ 요청					
					zuams     = DatasetUtility.getString(dsDetail, i, "ZUAMS"     );
					zurate    = DatasetUtility.getString(dsDetail, i, "ZURATE"     );					

					//E-GIS 송수신 저장 ZSDT1093  20190329 BY LYK
					egisEstNo    = DatasetUtility.getString(dsDetail, i, "EGIS_EST_NO"     );					
					egisEstSeq    = DatasetUtility.getString(dsDetail, i, "EGIS_EST_SEQ"     );					
					egisEstSerno    = DatasetUtility.getString(dsDetail, i, "EGIS_EST_SERNO"     );					
					egisSpecType    = DatasetUtility.getString(dsDetail, i, "EGIS_SPEC_TYPE"     );					
					
					
					if (mandt   == null) mandt     = paramVO.getVariable("G_MANDT");
					if (qtnum   == null) qtnum     = "";
					if (qtser     == null) qtser      = "0";
					if (qtseq     == null) qtseq     = "0";
					if (shangh  == null) shangh   = "";
					if (zsarea    == null) zsarea    = "";
					if (matnr    == null) matnr     = "";
					if (zprdgbn == null) zprdgbn  = "";
					if (spec      == null) spec      = "";
					if (gtype     == null) gtype     = "";
					if (type1     == null) type1     = "";
					if (type2     == null) type2     = "";
					if (type3     == null) type3     = "";
					if (znumber == null) znumber = "0";
					if (zacapa   == null) zacapa    = "";
					if (zuse      == null) zuse      = "";
					if (zopn     == null) zopn      = "";
					if (zlen      == null) zlen       = "";
					if (zoutc     == null) zoutc     = "";
					if (zuam     == null) zuam     = "0.00";
					if (zcost     == null) zcost      = "0.00";
					if (zcostm   == null) zcostm    = "0.00";
					if (zeam     == null) zeam      = "0.00";
					if (shang    == null) shang     = "0";
					if (zrmk      == null) zrmk      = "";
					if (sonnr     == null) sonnr     = "";
					if (userId    == null) userId     = "";
					
					//상해단가 추가  - 신미정 2014.05.27 정동명CJ 요청 
					if (zuams     == null) zuams     = "0.00";
					if (zurate     == null) zurate     = "0.00";
					
					if (qtnum   == "")   qtnum = param.getQtnum();
					if (qtser.equals("0")) qtser   = param.getQtser();
					//E-GIS 송수신 저장 ZSDT1093  20190329 BY LYK
					if (egisEstNo   	== null) egisEstNo     = "";
					if (egisEstSeq  	== null) egisEstSeq      = "0";
					if (egisEstSerno  	== null) egisEstSerno     = "0";
					if (egisSpecType   	== null) egisSpecType     = "";

					//for( int irow = 0; irow < dsDetail.getRowCount(); irow++ )  {
					//	for( int icol = 0; icol < dsDetail.getColumnCount(); icol++) 	{
//							System.out.println("@@@ dsDetail["+irow+"Record : " + dsDetail.getColumnID(icol)+"] ===>"  + DatasetUtility.getString(dsDetail, irow, dsDetail.getColumnID(icol)) + "\n");
					//	}
					//}

					param.setMandt   (mandt  );	 // SAP CLIENT
					param.setQtnum   (qtnum  );
					param.setQtser     (qtser   );
					param.setQtseq    (qtseq   );
					param.setShangh  (shangh );
					param.setZsarea   (zsarea  );
					param.setMatnr    (matnr   );
					param.setZprdgbn(zprdgbn);
					param.setSpec      (spec    );
					param.setGtype    (gtype   );
					param.setType1    (type1   );
					param.setType2    (type2   );
					param.setType3    (type3   );
					param.setZnumber(znumber);
					param.setZacapa   (zacapa );
					param.setZuse      (zuse    );
					param.setZopn     (zopn    );
					param.setZlen      (zlen     );
					param.setZoutc    (zoutc    );
					param.setZuam     (zuam   );
					param.setZcost     (zcost    );
					param.setZcostm   (zcostm  );
					param.setZeam     (zeam   );
					param.setShang    (shang  );
					param.setZrmk     (zrmk    );
					param.setSonnr    (sonnr   );
					param.setCuser    (userId  );
					param.setUuser    (userId  );
					
					//상해단가 추가  - 신미정 2014.05.27 정동명CJ 요청
					param.setZuams(zuams);
					param.setZurate(zurate);

					//E-GIS 송수신 저장 ZSDT1093  20190329 BY LYK
					param.setEgisEstNo(egisEstNo);
					param.setEgisEstSeq(egisEstSeq);
					param.setEgisEstSerno(egisEstSerno);
					param.setEgisSpecType(egisSpecType);
					
					if (flag.equals("I") || flag.equals("U")) {
						mergeDetail(param);
						mergeZsdt1093(param);

						if (flag.equals("I")) {
							param.setSorlt("20");	// 수주결과
							updateSorlt(param);
						}
					}else if (flag.equals("D"))	{ 
						
						// CONQTY20,CONQTY40 을 0으로 업데이트
						if(dsDetail.getRowCount() == 1) {
							updateZsdt1091(param);
						}
						deleteDetail(param);
						deleteZsdt1093(param);
						
						// 수주계획 정보를 체크(견적에 등록된 정보가 존재하지 않는 경우 수주계획을 등록으로 상태 변경); 2013.01.16
						int    iSonnrCnt = 0;

						if (sonnr != null && !"".equals(sonnr))		{
							iSonnrCnt = getSonnrCount(mandt, qtnum, sonnr);
 
							if (iSonnrCnt == 0)	{	// 견적에 등록된 수주계획이 없는경우 (동일 견적번호에서만 조회, 단납기 제외)
								updateQtserZsdt1001(mandt, sonnr, "10");	// 등록의로 변경
							}
						}

						deleteTemplate(param);		// 2013.01.14 MO정보 삭제 시 사양정보 삭제 처리

						// zcobt309, zcobt302, zcobt202, zsdt1054d, zsdt1050 --> 삭제필요 (zsdt1054h 제외)
						// 2013.01.15 원가정보 제거 처리
						deleteZcobt309(param);
						deleteZcobt302(param);
						deleteZcobt202(param);
						deleteZsdt1054D(param);
						deleteZsdt1050(param);
						
						//SS COST 이력 삭제   2014.06.28 ksk
						//ZSDT0712, ZSDT0713
						if ( qtnum.substring(0, 1).equals("J") ||
						     qtnum.substring(0, 1).equals("G") ||
						     qtnum.substring(0, 1).equals("F") ) 
						{	deleteZsdt0713(param);
							deleteZsdt0712(param);
						}
					}else if (flag.equals("A")) {
						param.setSorlt("20");	// 수주결과
						updateSorlt(param);
						addDetail(param);
						addZsdt1093(param);
					}
					// updateHeader(param); 최종 처리하는 부분으로 변경 2013.03.19
				}
			}
			updateHeader(param);	// 2013.03.19
			//SS COST header update 2014.06.28 ksk
			mergeZsdt1091(param);    /* 해상운임비 - 견적번호 신규생성및 업데이트 20190226 by lyk*/	
			updateZsdt0711(param);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return dsDetail;
	}
	
	public int mergeDetail(Ses0031ParamVO param) {
		return Ses0031Dao.mergeDetail(param);
	}
	
	public int deleteDetail(Ses0031ParamVO param) {
		return Ses0031Dao.deleteDetail(param);
	}

	public int deleteQtserZsdt1047(Ses0031ParamVO param) {
		return Ses0031Dao.deleteQtserZsdt1047(param);
	}


	public int addDetail(Ses0031ParamVO param) {
		return Ses0031Dao.addDetail(param);
	}

	public int addQtserZsdt1047(Ses0031ParamVO param) {
		return Ses0031Dao.insertQtserZsdt1047(param);
	}

	public int updateDetail(Ses0031ParamVO param) {
		return Ses0031Dao.updateDetail(param);
	}

	public Dataset saveTemplate(MipParameterVO paramVO, Model model, SqlSession session, Ses0031ParamVO param, Dataset dsTemplate) throws Exception{

//		Dataset dsTemplate = paramVO.getDataset("ds_template");

		try {
			createDao(session);  // DAO생성

			String flag    = "";
			String mandt = "";
			String qtnum = "";
			String qtser   = "";
			String qtseq  = "";
			String prseq  = "";
			String prh     = "";
			String prd     = "";
			String etch    = "";
			String etcm   = "";
			String zrmk   = "";
			String userId = "";

			for (int i=0;i< dsTemplate.getRowCount(); i++) {

				flag = DatasetUtility.getString(dsTemplate, i, "FLAG");

				if ( flag != null) {
					mandt = DatasetUtility.getString(dsTemplate, i, "MANDT" );
					qtnum = DatasetUtility.getString(dsTemplate, i, "QTNUM");
					qtser   = DatasetUtility.getString(dsTemplate, i, "QTSER"  );
					qtseq  = DatasetUtility.getString(dsTemplate, i, "QTSEQ"  );
					prseq  = DatasetUtility.getString(dsTemplate, i, "PRSEQ"  );
					prh     = DatasetUtility.getString(dsTemplate, i, "PRH"     );
					prd     = DatasetUtility.getString(dsTemplate, i, "PRD"     );
					etch    = DatasetUtility.getString(dsTemplate, i, "ETCH"    );
					etcm    = DatasetUtility.getString(dsTemplate, i, "ETCM"   );
					zrmk    = DatasetUtility.getString(dsTemplate, i, "ZRMK"  );
					userId  = paramVO.getVariable("G_USER_ID");

					if (mandt == null) mandt = paramVO.getVariable("G_MANDT");
					if (qtnum == null) qtnum = "";
					if (qtser   == null) qtser   = "0";
					if (qtseq  == null) qtseq   = "0";
					if (prseq  == null) prseq   = "0";
					if (prh     == null) prh     = "";
					if (prd     == null) prd     = "";
					if (etch    == null) etch    = "";
					if (etcm    == null) etcm   = "0";
					if (zrmk    == null) zrmk   = "";
					if (userId  == null) userId = "";

					if (qtnum   == "")   qtnum = param.getQtnum();
					if (qtser.equals("0")) qtser   = param.getQtser();
					
/*					System.out.print("\n@@@ MANDT ===>"+ mandt +"\n");
					System.out.print("\n@@@ QTNUM ===>"+ qtnum +"\n");
					System.out.print("\n@@@ QTSER   ===>"+ qtser   +"\n");
					System.out.print("\n@@@ QTSEQ   ===>"+ qtseq  +"\n");
					System.out.print("\n@@@ PRSEQ   ===>"+ prseq  +"\n");
					System.out.print("\n@@@ PRH      ===>"+ prh     +"\n");
					System.out.print("\n@@@ PRD      ===>"+ prd     +"\n");
					System.out.print("\n@@@ ETCH     ===>"+ etch    +"\n");
					System.out.print("\n@@@ ETCM    ===>"+ etcm    +"\n");
					System.out.print("\n@@@ ZRMK    ===>"+ zrmk   +"\n");
					System.out.print("\n@@@ USERID  ===>"+ userId  +"\n");*/

					param.setMandt(mandt );	 // SAP CLIENT
					param.setQtnum(qtnum);
					param.setQtser  (qtser  );
					param.setQtseq  (qtseq );
					param.setPrseq  (prseq );
					param.setPrh     (prh   );
					param.setPrd     (prd   );
					param.setEtch    (etch  );
					param.setEtcm   (etcm  );
					param.setZrmk   (zrmk );
					
					param.setCuser  (userId);
					param.setUuser  (userId);

					if       (flag.equals("I") || flag.equals("U")) mergeTemplate(param);
					else if (flag.equals("D"))                      deleteTemplate(param);	// MO삭제 시 동시 처리되어 향후 불필요 2013.01.14
					else if (flag.equals("A")) {
						addTemplate(param); break;
					}
					
					if("I".equals(flag) || "U".equals(flag) || "A".equals(flag)) {
//						if(prh.equals("CO_ELQTY") || prh.equals("CO_LAND1")) {
						if(prh.equals("CO_ELQTY")) {
							Ses0031Dao.updateCoElqty(param);
							
						}
					}
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return dsTemplate;
	}

	public int mergeTemplate(Ses0031ParamVO param) {
		return Ses0031Dao.mergeTemplate(param);
	}

	public int deleteTemplate(Ses0031ParamVO param) {
		return Ses0031Dao.deleteTemplate(param);
	}

	public int deleteQtserZsdt1048(Ses0031ParamVO param) {
		return Ses0031Dao.deleteQtserZsdt1048(param);
	}


	public int addTemplate(Ses0031ParamVO param) {
		return Ses0031Dao.addTemplate(param);
	}

	public int addQtserZsdt1048(Ses0031ParamVO param) {
		return Ses0031Dao.insertQtserZsdt1048(param);
	}

	public Dataset saveZsdt1054(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{

		Dataset dsZsdt1054 = paramVO.getDataset("ds_check");

		try {
			createDao(session);  // DAO생성

			String flag   = "";
			String mandt  = "";
			String qtnum  = "";
			String qtser  = "";
			String qtseq  = "";
			String pspid  = "";
			String posid  = "";
			String atype  = "";
			String gubun  = "";
			String seria  = "0";
			String blseq  = "";
			String blnum  = "";
			String blnam  = "";
			String zitem  = "";
			String zchar  = "";
			String zquty  = "";
			String zamnt  = "";
			String ztotl  = "";
			String bigo   = "";
			String tex1   = "";
			String tex2   = "";
			String userId = "";
			String state  = "";
			String addt01 = "";
			String messg  = "";
			String zuam   = "0";
			// 견적 또는 계약변경 구분
			String f_flag = paramVO.getVariable("f_flag");
			String f_save = paramVO.getVariable("f_save");

			for (int i=0;i< dsZsdt1054.getRowCount(); i++) {

				flag = DatasetUtility.getString(dsZsdt1054, i, "FLAG");

				if ( flag != null) {

					mandt  = DatasetUtility.getString(dsZsdt1054, i, "MANDT" );
					qtnum  = DatasetUtility.getString(dsZsdt1054, i, "QTNUM");
					qtser  = DatasetUtility.getString(dsZsdt1054, i, "QTSER"  );
					qtseq  = DatasetUtility.getString(dsZsdt1054, i, "QTSEQ"  );
					pspid  = DatasetUtility.getString(dsZsdt1054, i, "PSPID"  );
					posid  = DatasetUtility.getString(dsZsdt1054, i, "POSID"  );
					atype  = DatasetUtility.getString(dsZsdt1054, i, "ATYPE"  );
					gubun  = DatasetUtility.getString(dsZsdt1054, i, "GUBUN");
					seria  = DatasetUtility.getString(dsZsdt1054, i, "SERIA"  );
					blseq  = DatasetUtility.getString(dsZsdt1054, i, "BLSEQ"  );
					blnum  = DatasetUtility.getString(dsZsdt1054, i, "BLNUM");
					blnam  = DatasetUtility.getString(dsZsdt1054, i, "BLNAM");
					zitem  = DatasetUtility.getString(dsZsdt1054, i, "ZITEM"  );
					zchar  = DatasetUtility.getString(dsZsdt1054, i, "ZCHAR" );
					zquty  = DatasetUtility.getString(dsZsdt1054, i, "ZQUTY" );
					zamnt  = DatasetUtility.getString(dsZsdt1054, i, "ZAMNT");
					ztotl  = DatasetUtility.getString(dsZsdt1054, i, "ZTOTL"  );
					bigo   = DatasetUtility.getString(dsZsdt1054, i, "BIGO"   );
					tex1   = DatasetUtility.getString(dsZsdt1054, i, "TEX1"   );
					tex2   = DatasetUtility.getString(dsZsdt1054, i, "TEX2"   );
					state  = DatasetUtility.getString(dsZsdt1054, i, "STATE"  );
					messg  = DatasetUtility.getString(dsZsdt1054, i, "MESSG"  );
					addt01 = DatasetUtility.getString(dsZsdt1054, i, "ADDT01");
					userId = paramVO.getVariable("G_USER_ID");
					
					if (mandt == null) mandt = paramVO.getVariable("G_MANDT");
					if (qtnum == null) qtnum = "";
					if (qtser == null) qtser   = "0";
					if (qtseq == null) qtseq   = "0";
					if (pspid == null) pspid  = "";
					if (posid == null) posid  = "";
					if (atype == null) atype   = "";
					if (gubun == null) gubun ="";
					if (seria == null) seria   = "0";
					if (blseq == null) blseq  = "";
					if (blnum == null) blnum = "";
					if (blnam == null) blnam = "";
					if (zitem == null) zitem  = "";
					if (zchar == null) zchar  = "";
					if (zquty == null) zquty  = "0";
					if (zamnt == null) zamnt = "0";
					if (ztotl == null) ztotl    = "0";
					if (bigo  == null) bigo   = "";
					if (tex1  == null) tex1    = "";
					if (tex2  == null) tex2    = "";
					if (state  == null) state  = "";
					if (userId == null) userId = "";
					if (messg == null)  messg = "";
					if (f_flag == null) f_flag = "Q";	// 견적으로 처리 (Q : 견적, P :계약변경)

					Ses0031ParamVO param = new Ses0031ParamVO();

					param.setMandt(mandt);	 // SAP CLIENT
					param.setQtnum(qtnum);
					param.setQtser(qtser);
					param.setQtseq(qtseq);
					param.setPspid(pspid);
					param.setPosid(posid);
					param.setAtype(atype);
					param.setGubun(gubun);
					param.setSeria(seria);
					param.setBlseq(blseq);
					param.setBlnum(blnum);
					param.setBlnam(blnam);
					param.setZitem(zitem);
					param.setZchar(zchar);
					param.setZquty(zquty);
					param.setZamnt(zamnt);
					param.setZtotl(ztotl);
					param.setBigo(bigo);
					param.setTex1(tex1);
					param.setTex2(tex2);
					param.setCuser(userId);
					param.setUuser(userId);
					param.setAddt01(addt01);
					param.setMessg(messg);

					if (flag.equals("I") || flag.equals("U")) {

						if (state.length() > 0) param.setState("X");
						else                    param.setState("");

						updateZcobt309(param);
						
						if ("Q".equals(f_flag))			{	// 견적
							updateZcobt202(param);

							// 저장시에는 반영되면 안됨 2012.12.15
							//if (addt01 != null) updateZcobt302(param);
						}else	if ("P".equals(f_flag))	{	// 계약변경
							updateZcobt204(param);
						}

						param.setFlag(state);
						updateZsdt1054H(param);
						updateZsdt1054D(param);
					} else if (flag.equals("D")) {
						param.setState("");
						updateZcobt309(param);

						if ("Q".equals(f_flag))			{	// 견적
							updateZcobt202(param);

							// 저장시에는 반영되면 안됨 2012.12.15
							//if (addt01 != null) updateZcobt302(param);	
						}else	if ("P".equals(f_flag))	{	// 계약변경
							updateZcobt204(param);
						}

						if (f_save != null && "D".equals(f_save))	{
							deleteZsdt1054H(param); //2012.12.28 부분원가 삭제시만 처리
						}

						deleteZsdt1054D(param);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dsZsdt1054;
	}

	// 환율기준정보 변경으로 추가 2013.02.20 &&&&&
	public void saveCostUpdate(Dataset dsHeader, Dataset dsZcobt302, MipParameterVO paramVO, SqlSession session) throws RuntimeException, Exception{
		String mandt = paramVO.getVariable("G_MANDT");
		String uuser = paramVO.getVariable("G_USER_NAME");

		Ses0031ParamVO paramH = new Ses0031ParamVO();
		
		ExchangeS.createDao(session);		//DAO 생성 

		try {
			// 견적서 및 견적상세의 단가, 원가, 시행율 초기화 처리
			paramH.setMandt(paramVO.getVariable("G_MANDT"));
			paramH.setQtnum(DatasetUtility.getString(dsHeader, 0, "QTNUM"));
			paramH.setQtser(DatasetUtility.getString(dsHeader, 0, "QTSER"));
			paramH.setZuam("0");
			paramH.setZcost("0");
			paramH.setShang("0");

			// 원가 초기화 처리
			updateInitCostZSDT1046(paramH);
			updateInitCostZSDT1047(paramH);		// SP는 단가 입력된 정보 유지 필요.(MATNR != "Y-1000")

			Method[] mArr  = ZCOBT302.class.getMethods();
			HashMap  mData = new HashMap();

			for ( int i = 0; i < mArr.length; i++) 	{
				// Set 메소드만 가져오기
				if ( mArr[i].getName().startsWith("set")) {
					mData.put(mArr[i].getName().substring(3), mArr[i]);
				}
			}

/*-----< 2018.03.30 환율 계산 로직 주석 처리 by mj.Shin 		Start			
			// 환율정보 조회
			Ses0031 exchangeVO = null;
			exchangeVO = searchNewExchange(paramVO.getVariable("G_MANDT"), DateTime.getDateString());

			if (exchangeVO == null)		{
				String error = "";
				if (!"ko".equals(paramVO.getVariable("G_LANG")))	{
					error = "CE10007";
				}else	{
					error = "CE10007";
				}
				throw new RequireException(error);
			}

			Method m = null;
			Class setClass = null;
			double sum   = 0;
			BigDecimal zuam = new BigDecimal(0);

			String waerk = DatasetUtility.getString(dsHeader,   0, "WAERK");

			String eur   = exchangeVO.getKrweur();
			String jpy   = exchangeVO.getKrwjpy();
			String usd   = exchangeVO.getKrwusd();
	
			if (waerk == null) waerk = "KRW";
			if (eur   == null) eur   = "0";
			if (jpy   == null) jpy   = "0";
			if (usd   == null) usd   = "0";
	
			for( int i = 0; i < dsZcobt302.getRowCount(); i++ ) 	{
				Ses0031ParamVO param = new Ses0031ParamVO();

				zuam = new BigDecimal(0);
				sum  = 0;

				for( int c = 0; c < dsZcobt302.getColumnCount(); c++) {
					m = (Method) mData.get(dsZcobt302.getColumnID(c));

					if ( m != null ) {
						setClass = m.getParameterTypes()[0];

						if ( setClass.getName().equals("java.math.BigDecimal")){
							if (   "EQMA01".equals(dsZcobt302.getColumnID(c)) 
								|| "EQMA02".equals(dsZcobt302.getColumnID(c)) 
								|| "PRDA01".equals(dsZcobt302.getColumnID(c)) 
								|| "CTSA01".equals(dsZcobt302.getColumnID(c))) {
								continue;
							} else {
								// 2013.03.20 존재여부 확인(MO별로 확인)
								sum += DatasetUtility.getDbl(dsZcobt302, i, dsZcobt302.getColumnID(c), 0) * 100;
							}
						}
					}
				}

				// RRC처리 후 및 ZCOBT302에 처리된 원가는 원화롤 저장되어 있음. (단위 항목에서 *100 처리) 2013.03.08
				// sum *= 100;

				// AUART가 ZE로 시작하는 경우에만 처리 2013.02.20
				String auart = DatasetUtility.getString(dsHeader, 0, "AUART");
				if (auart != null && "ZE".equals( auart.substring(0, 2)) ) 	{
					if ("EUR".equals(waerk))	{
						String value = exchangeVO.getKrweur();
						if (value == null || "".equals(value.trim()))		{
							String error = "CE10007";
							throw new RequireException(error);
						}else	{
							if (Double.parseDouble(value) <= 0)	{
								String error = "CE10007";
								throw new RequireException(error);
							}							
						}
						sum = sum / Double.parseDouble(eur);
					}else if ("JPY".equals(waerk))	{
						String value = exchangeVO.getKrwjpy();
						if (value == null || "".equals(value.trim()))		{
							String error = "CE10007";
							throw new RequireException(error);
						}else	{
							if (Double.parseDouble(value) <= 0)	{
								String error = "CE10007";
								throw new RequireException(error);
							}							
						}
						sum = sum / Double.parseDouble(jpy);
					}else if ("USD".equals(waerk))	{
						String value = exchangeVO.getKrwusd();
						if (value == null || "".equals(value.trim()))		{
							String error = "CE10007";
							throw new RequireException(error);
						}else	{
							if (Double.parseDouble(value) <= 0)	{
								String error = "CE10007";
								throw new RequireException(error);
							}							
						}
						sum = sum / Double.parseDouble(usd);
					}
				}
	-----> 2018.03.30 환율 계산 로직 주석 처리 by mj.Shin 		End 		*/
			
			Method m = null;
			Class setClass = null;
			double sum   = 0;
			BigDecimal zuam = new BigDecimal(0);
	
			for( int i = 0; i < dsZcobt302.getRowCount(); i++ ) 	{
				Ses0031ParamVO param = new Ses0031ParamVO();

				zuam = new BigDecimal(0);
				sum  = 0;

				for( int c = 0; c < dsZcobt302.getColumnCount(); c++) {
					m = (Method) mData.get(dsZcobt302.getColumnID(c));

					if ( m != null ) {
						setClass = m.getParameterTypes()[0];

						if ( setClass.getName().equals("java.math.BigDecimal")){
							if (   "EQMA01".equals(dsZcobt302.getColumnID(c)) 
								|| "EQMA02".equals(dsZcobt302.getColumnID(c)) 
								|| "PRDA01".equals(dsZcobt302.getColumnID(c)) 
								|| "CTSA01".equals(dsZcobt302.getColumnID(c))) {
								continue;
							} else {
								// 2013.03.20 존재여부 확인(MO별로 확인)
								sum += DatasetUtility.getDbl(dsZcobt302, i, dsZcobt302.getColumnID(c), 0) * 100;
							}
						}
					}
				}

				// RRC처리 후 및 ZCOBT302에 처리된 원가는 원화롤 저장되어 있음. (단위 항목에서 *100 처리) 2013.03.08
				// sum *= 100;

				// AUART가 ZE로 시작하는 경우에만 처리 2013.02.20
				String waerk = DatasetUtility.getString(dsHeader, 0, "WAERK");
				String auart = DatasetUtility.getString(dsHeader, 0, "AUART");
				String exchangeR = null;
				
				if (waerk != null && !"KRW".equals(waerk) && auart != null && "ZE".equals( auart.substring(0, 2)) ) {
					exchangeR = ExchangeS.getExchangeRate(mandt, "Q", DateTime.getDateString(), waerk, "KRW");		
				}

				if (Double.parseDouble(exchangeR) > 0 ) {
					sum = sum / Double.parseDouble(exchangeR);
				}
				
				zuam = new BigDecimal(sum);
				zuam = zuam.setScale(2, RoundingMode.DOWN);

				String qtnum = DatasetUtility.getString(dsZcobt302, i, "QTNUM");
				String qtser = DatasetUtility.getString(dsZcobt302, i, "QTSER");
				String qtseq = DatasetUtility.getString(dsZcobt302, i, "QTSEQ");

				if (mandt == null) mandt = "";
				if (qtnum == null) qtnum = "";
				if (qtser == null) qtser = "0";
				if (qtseq == null) qtseq = "0";

				param.setMandt(mandt);
				param.setCuser(uuser);
				param.setUuser(uuser);
				param.setQtnum(qtnum);
				param.setQtser(qtser);
				param.setQtseq(qtseq);
				param.setZuam(zuam.toString());

				// 견적상세 Update
				updateDetail(param);
			}
			// Header 처리
			String zprgflg = "32"; // 32 원가산출

			paramH.setCuser(uuser);
			paramH.setUuser(uuser);
			paramH.setZprgflg(zprgflg);
			// 원가산출 일자를 견적일자로 변경 2013.01.07
			paramH.setQtdat(DateTime.getDateString());

			updateHeader(paramH);
			mergeZsdt1091(paramH);    /* 해상운임비 - 견적번호 신규생성및 업데이트 20190226 by lyk*/	
			
			// X의 경우 동일, 이외에는 상이함
			// 최종 처리후 삭제
			//if (bCheck)	{
			//	deleteZsdt1054(paramVO, session);
			//}
		} catch( Exception e){
			e.printStackTrace();
			throw e;
		}
		return;
	}

/*-----< 2018.03.30 호출되는 곳 없음 saveCostUpdate 별도 생성 됨(dsExchang 제외) by mj.Shin 		Start	
	// 2012.12.24 Control에서 Service로 이동
	public void saveCostUpdate(Dataset dsHeader, Dataset dsExchange, Dataset dsZcobt302, MipParameterVO paramVO, SqlSession session) throws Exception{
		String mandt = paramVO.getVariable("G_MANDT");
		String uuser = paramVO.getVariable("G_USER_NAME");

		Ses0031ParamVO paramH = new Ses0031ParamVO();

		try {
			// 견적서 및 견적상세의 단가, 원가, 시행율 초기화 처리
			paramH.setMandt(paramVO.getVariable("G_MANDT"));
			paramH.setQtnum(DatasetUtility.getString(dsHeader, 0, "QTNUM"));
			paramH.setQtser(DatasetUtility.getString(dsHeader, 0, "QTSER"));
			paramH.setZuam("0");
			paramH.setZcost("0");
			paramH.setShang("0");

			// 원가 초기화 처리
			updateInitCostZSDT1046(paramH);
			updateInitCostZSDT1047(paramH);		// SP는 단가 입력된 정보 유지 필요.(MATNR != "Y-1000")

			Method[] mArr  = ZCOBT302.class.getMethods();
			HashMap  mData = new HashMap();

			for ( int i = 0; i < mArr.length; i++) 	{
				// Set 메소드만 가져오기
				if ( mArr[i].getName().startsWith("set")) {
					mData.put(mArr[i].getName().substring(3), mArr[i]);
				}
			}
	
			Method m = null;
			Class setClass = null;
			double sum = 0;
			BigDecimal b    = null;
			BigDecimal zuam = new BigDecimal(0);			

			String waerk = DatasetUtility.getString(dsHeader,   0, "WAERK");

			String eur   = DatasetUtility.getString(dsExchange, 0, "KRWEUR");
			String jpy   = DatasetUtility.getString(dsExchange, 0, "KRWJPY");
			String usd   = DatasetUtility.getString(dsExchange, 0, "KRWUSD");
	
			if (waerk == null) waerk = "KRW";
			if (eur   == null) eur   = "0";
			if (jpy   == null) jpy   = "0";
			if (usd   == null) usd   = "0";
	
			for( int i = 0; i < dsZcobt302.getRowCount(); i++ ) 	{
				Ses0031ParamVO param = new Ses0031ParamVO();

				zuam = new BigDecimal(0);
				sum  = 0;

				for( int c = 0; c < dsZcobt302.getColumnCount(); c++) {
					m = (Method) mData.get(dsZcobt302.getColumnID(c));

					if ( m != null ) {
						setClass = m.getParameterTypes()[0];

						if ( setClass.getName().equals("java.math.BigDecimal")){
							if (   "EQMA01".equals(dsZcobt302.getColumnID(c)) 
								|| "EQMA02".equals(dsZcobt302.getColumnID(c)) 
								|| "PRDA01".equals(dsZcobt302.getColumnID(c)) 
								|| "CTSA01".equals(dsZcobt302.getColumnID(c))) {
								continue;
							} else {
								// b = new BigDecimal(DatasetUtility.getDbl(dsZcobt302, i, dsZcobt302.getColumnID(c), 0));
								// sum += b.doubleValue();
								sum += DatasetUtility.getDbl(dsZcobt302, i, dsZcobt302.getColumnID(c), 0) * 100;
							}
						}
					}
				}

				// RRC처리 후 및 ZCOBT302에 처리된 원가는 원화롤 저장되어 있음.
				//sum *= 100;
				// if (!"JPY".equals(waerk)) sum *= 100;

				if      ("EUR".equals(waerk)) sum = sum / Double.parseDouble(eur);
				else if ("JPY".equals(waerk)) sum = sum / Double.parseDouble(jpy);
				else if ("USD".equals(waerk)) sum = sum / Double.parseDouble(usd);

				zuam = new BigDecimal(sum);
				zuam = zuam.setScale(2, RoundingMode.DOWN);

				String qtnum = DatasetUtility.getString(dsZcobt302, i, "QTNUM");
				String qtser = DatasetUtility.getString(dsZcobt302, i, "QTSER");
				String qtseq = DatasetUtility.getString(dsZcobt302, i, "QTSEQ");

				if (mandt == null) mandt = "";
				if (qtnum == null) qtnum = "";
				if (qtser == null) qtser = "0";
				if (qtseq == null) qtseq = "0";

				param.setMandt(mandt);
				param.setCuser(uuser);
				param.setUuser(uuser);
				param.setQtnum(qtnum);
				param.setQtser(qtser);
				param.setQtseq(qtseq);
				param.setZuam(zuam.toString());

				// 견적상세 Update
				updateDetail(param);
			}
			// Header 처리
			String zprgflg = "32"; // 32 원가산출

			paramH.setCuser(uuser);
			paramH.setUuser(uuser);
			paramH.setZprgflg(zprgflg);
			// 원가산출 일자를 견적일자로 변경 2013.01.07
			paramH.setQtdat(DateTime.getDateString());

			updateHeader(paramH);
			
			// 최종 처리후 삭제
			deleteZsdt1054(paramVO, session);
		} catch( Exception e){
			e.printStackTrace();
			throw e;
		}
		return;
	}
  -----< 2018.03.30 호출되는 곳 없음 saveCostUpdate 별도 생성 됨(dsExchang 제외) by mj.Shin 		End  */

	public Dataset deleteZsdt1054(MipParameterVO paramVO, SqlSession session) {

		Dataset dsZsdt1054 = paramVO.getDataset("ds_char");

		try {
			createDao(session);  // DAO생성

			String mandt = paramVO.getVariable("G_MANDT");
			String qtnum = DatasetUtility.getString(dsZsdt1054, 0, "QTNUM");
			String qtser = DatasetUtility.getString(dsZsdt1054, 0, "QTSER"  );

			if (qtnum == null) qtnum = "";
			if (qtser == null) qtser = "0";

/*			System.out.print("\n@@@ MANDT ===>"+ mandt +"\n");
			System.out.print("\n@@@ QTNUM ===>"+ qtnum +"\n");
			System.out.print("\n@@@ QTSER   ===>"+ qtser   +"\n"); */

			Ses0031ParamVO param = new Ses0031ParamVO();

			param.setMandt(mandt );	 // SAP CLIENT
			param.setQtnum(qtnum);
			param.setQtser(qtser);

			deleteZsdt1054H(param);
			deleteZsdt1054D(param);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsZsdt1054;
	}
	
	public Dataset saveZuam(MipParameterVO paramVO, Model model, SqlSession session) throws RuntimeException, Exception{
		
		ExchangeS.createDao(session);		//DAO 생성
		
		// 2013.02.13 반영전 부분원가가 저장되도록 변경 
		Dataset dsCheck      = paramVO.getDataset("ds_check");	// 부분원가
		Dataset dsApplyCheck = paramVO.getDataset("ds_apply");	// 반영원가
		Dataset dsExchange   = paramVO.getDataset("ds_exchange");


		try {
			createDao(session);  // DAO생성

			String flag   = "";
			String mandt  = "";
			String qtnum  = "";
			String qtser  = "";
			String qtseq  = "";
			String pspid  = "";
			String posid  = "";
			String atype  = "";
			String gubun  = "";
			String seria  = "0";
			String blseq  = "";
			String blnum  = "";
			String blnam  = "";
			String zitem  = "";
			String zchar  = "";
			String zquty  = "";
			String zamnt  = "";
			String ztotl  = "";
			String bigo   = "";
			String tex1   = "";
			String tex2   = "";
			String userId = "";
			String state  = "";
			String addt01 = "";
			String messg  = "";
			String zuam   = "0";
			String zuams   = "0";
			String zurate   = "0";
			String exchangeR = "0";
			
			// 견적 또는 계약변경 구분
			String f_flag = paramVO.getVariable("f_flag");
			String f_save = paramVO.getVariable("f_save");

			String waerk = DatasetUtility.getString(dsExchange, 0, "FCURR");	// 소스 통화 단위
			String auart = DatasetUtility.getString(dsExchange, 0, "AUART");
			String Basedt = DatasetUtility.getString(dsExchange, 0, "BASEDT");
			String Kurst = DatasetUtility.getString(dsExchange, 0, "KURST");
			
			if (waerk != null && !"KRW".equals(waerk)) {
				exchangeR = ExchangeS.getExchangeRate(DatasetUtility.getString(dsExchange, 0, "MANDT"), Kurst, Basedt, waerk, "KRW");		
			}
			
			for (int i=0;i< dsCheck.getRowCount(); i++) {

				flag = DatasetUtility.getString(dsCheck, i, "FLAG");

				if ( flag != null) {

					mandt  = DatasetUtility.getString(dsCheck, i, "MANDT" );
					qtnum  = DatasetUtility.getString(dsCheck, i, "QTNUM");
					qtser  = DatasetUtility.getString(dsCheck, i, "QTSER"  );
					qtseq  = DatasetUtility.getString(dsCheck, i, "QTSEQ"  );
					pspid  = DatasetUtility.getString(dsCheck, i, "PSPID"  );
					posid  = DatasetUtility.getString(dsCheck, i, "POSID"  );
					atype  = DatasetUtility.getString(dsCheck, i, "ATYPE"  );
					gubun  = DatasetUtility.getString(dsCheck, i, "GUBUN");
					seria  = DatasetUtility.getString(dsCheck, i, "SERIA"  );
					blseq  = DatasetUtility.getString(dsCheck, i, "BLSEQ"  );
					blnum  = DatasetUtility.getString(dsCheck, i, "BLNUM");
					blnam  = DatasetUtility.getString(dsCheck, i, "BLNAM");
					zitem  = DatasetUtility.getString(dsCheck, i, "ZITEM"  );
					zchar  = DatasetUtility.getString(dsCheck, i, "ZCHAR" );
					zquty  = DatasetUtility.getString(dsCheck, i, "ZQUTY" );
					zamnt  = DatasetUtility.getString(dsCheck, i, "ZAMNT");
					ztotl  = DatasetUtility.getString(dsCheck, i, "ZTOTL"  );
					bigo   = DatasetUtility.getString(dsCheck, i, "BIGO"   );
					tex1   = DatasetUtility.getString(dsCheck, i, "TEX1"   );
					tex2   = DatasetUtility.getString(dsCheck, i, "TEX2"   );
					state  = DatasetUtility.getString(dsCheck, i, "STATE"  );
					addt01 = DatasetUtility.getString(dsCheck, i, "ADDT01");
					messg  = DatasetUtility.getString(dsCheck, i, "MESSG"  );
					userId = paramVO.getVariable("G_USER_ID");
					
					if (mandt == null) mandt = paramVO.getVariable("G_MANDT");
					if (qtnum == null) qtnum = "";
					if (qtser == null) qtser   = "0";
					if (qtseq == null) qtseq   = "0";
					if (pspid == null) pspid  = "";
					if (posid == null) posid  = "";
					if (atype == null) atype   = "";
					if (gubun == null) gubun ="";
					if (seria == null) seria   = "0";
					if (blseq == null) blseq  = "";
					if (blnum == null) blnum = "";
					if (blnam == null) blnam = "";
					if (zitem == null) zitem  = "";
					if (zchar == null) zchar  = "";
					if (zquty == null) zquty  = "0";
					if (zamnt == null) zamnt = "0";
					if (ztotl == null) ztotl    = "0";
					if (bigo  == null) bigo   = "";
					if (tex1  == null) tex1    = "";
					if (tex2  == null) tex2    = "";
					if (state  == null) state  = "";
					if (messg == null)  messg = "";
					if (userId == null) userId = "";
					if (f_flag == null) f_flag = "Q";	// 견적으로 처리 (Q : 견적, P :계약변경)

					Ses0031ParamVO param = new Ses0031ParamVO();

					param.setMandt(mandt);	 // SAP CLIENT
					param.setQtnum(qtnum);
					param.setQtser(qtser);
					param.setQtseq(qtseq);
					param.setPspid(pspid);
					param.setPosid(posid);
					param.setAtype(atype);
					param.setGubun(gubun);
					param.setSeria(seria);
					param.setBlseq(blseq);
					param.setBlnum(blnum);
					param.setBlnam(blnam);
					param.setZitem(zitem);
					param.setZchar(zchar);
					param.setZquty(zquty);
					param.setZamnt(zamnt);
					param.setZtotl(ztotl);
					param.setBigo(bigo);
					param.setTex1(tex1);
					param.setTex2(tex2);
					param.setCuser(userId);
					param.setUuser(userId);
					param.setAddt01(addt01);
					param.setMessg(messg);

					if (flag.equals("I") || flag.equals("U")) {

						if (state.length() > 0) param.setState("X");
						else                    param.setState("");

						updateZcobt309(param);
						
						if ("Q".equals(f_flag))			{	// 견적
							updateZcobt202(param);

							// 저장시에는 반영되면 안됨 2012.12.15
							//if (addt01 != null) updateZcobt302(param);
						}else	if ("P".equals(f_flag))	{	// 계약변경
							updateZcobt204(param);
						}

						param.setFlag(state);
						updateZsdt1054H(param);
						updateZsdt1054D(param);
					} else if (flag.equals("D")) {
						param.setState("");
						updateZcobt309(param);

						if ("Q".equals(f_flag))			{	// 견적
							updateZcobt202(param);

							// 저장시에는 반영되면 안됨 2012.12.15
							//if (addt01 != null) updateZcobt302(param);	
						}else	if ("P".equals(f_flag))	{	// 계약변경
							updateZcobt204(param);
						}

						if (f_save != null && "D".equals(f_save))	{
							deleteZsdt1054H(param); //2012.12.28 부분원가 삭제시만 처리
						}

						deleteZsdt1054D(param);
					}
				}
			}

			// 부분원가 반영 후 원가적용 처리
			mandt  = "";
			qtnum  = "";
			qtser  = "";
			qtseq  = "";
			pspid  = "";
			posid  = "";
			addt01 = "";
			userId = "";
			zuam   = "0";
			f_flag = "Q";	// 견적 및 계약변경 구분

			String seq    = "";
			double sum    = 0;

			f_flag = paramVO.getVariable("f_flag");

			List<Ses0031> list;
/* -----< 2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start
			String waerk = DatasetUtility.getString(dsExchange, 0, "WAERK");	// 화폐단위
			String auart = DatasetUtility.getString(dsExchange, 0, "AUART");

			// 2013.02.20 제품군반영
			// 처리전에 재조회하여 확인
			Ses0031ParamVO paramExchangeVO = new Ses0031ParamVO();
			paramExchangeVO.setF_flag(f_flag);
			paramExchangeVO.setMandt(DatasetUtility.getString(dsExchange, 0, "MANDT"));
			paramExchangeVO.setSdate(DatasetUtility.getString(dsExchange, 0, "SDATE"));

			// String eur   = DatasetUtility.getString(dsExchange, 0, "KRWEUR");
			// String jpy   = DatasetUtility.getString(dsExchange, 0, "KRWJPY");
			// String usd   = DatasetUtility.getString(dsExchange, 0, "KRWUSD");

			String eur = "";
			String jpy = "";
			String usd = "";

			Ses0031 exchangeVO = searchNewExchange(paramExchangeVO, session, paramVO.getVariable("G_LANG"));

			if (exchangeVO != null)	{
				eur = exchangeVO.getKrweur();
				jpy = exchangeVO.getKrwjpy();
				usd = exchangeVO.getKrwusd();
			}

 -----> 2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End */
			
			for (int i = 0; i < dsApplyCheck.getRowCount(); i++) {

				addt01 = DatasetUtility.getString(dsApplyCheck, i, "ADDT01");

				if ( addt01 != null) {

					mandt  = DatasetUtility.getString(dsApplyCheck, i, "MANDT");
					qtnum  = DatasetUtility.getString(dsApplyCheck, i, "QTNUM");
					qtser  = DatasetUtility.getString(dsApplyCheck, i, "QTSER");
					qtseq  = DatasetUtility.getString(dsApplyCheck, i, "QTSEQ");
					pspid  = DatasetUtility.getString(dsApplyCheck, i, "PSPID");
					posid  = DatasetUtility.getString(dsApplyCheck, i, "POSID");
					seq    = DatasetUtility.getString(dsApplyCheck, i, "SEQ");
					userId = paramVO.getVariable("G_USER_ID");
					
					if (mandt  == null) mandt  = paramVO.getVariable("G_MANDT");
					if (qtnum  == null) qtnum  = "";
					if (qtser  == null) qtser  = "0";
					if (qtseq  == null) qtseq  = "0";
					if (pspid  == null) pspid  = "";
					if (posid  == null) posid  = "";
					if (seq    == null) seq    = "0";
					if (userId == null) userId = "";
					if (addt01 == null) addt01 = "0";
					

					Ses0031ParamVO param = new Ses0031ParamVO();

					param.setMandt(mandt );	 // SAP CLIENT
					param.setQtnum(qtnum);
					param.setQtser(qtser);
					param.setQtseq(qtseq);
					param.setPspid(pspid);
					param.setPosid(posid);
					param.setSeq(seq);
					param.setCuser(userId);
					param.setUuser(userId);
					param.setAddt01(addt01);
					//param.setZprgflg("42"); // 원가팀완료

					// 구분이 '1'(모델)의 경우는 ZCOBT302, ZCOBT304에 원가 정보가 존재하지 않음.
					if ("Q".equals(f_flag))		{	// 견적원가
						list = searchZuam(param);
					}else	{
						list = searchZcobt304(param);	// 계약변경 원가
					}

					if (list.size() > 0)
						zuam = list.get(0).getZuam().toString();
					else
						zuam = "0";

					sum  = Double.parseDouble(zuam) + Double.parseDouble(addt01);
					zuam = Double.toString(sum);

					if ( Double.parseDouble(exchangeR) > 0 && auart != null && "ZE".equals(auart.substring(0, 2)))	{
						sum = Double.parseDouble(zuam)  / Double.parseDouble(exchangeR);
						zuam = Double.toString(sum);
					}

/* -----< 2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start					
					// 2013.02.20 AUART가 ZE로 시작하는 경우만 환율 적용
					if ( auart != null && "ZE".equals(auart.substring(0, 2)) )	{

						if ("EUR".equals(waerk))  {
							String value = eur;
							if (value == null || "".equals(value.trim()))		{
								String error = "CE10007";
								throw new RequireException(error);
							}else	{
								if (Double.parseDouble(value) <= 0)	{
									String error = "CE10007";
									throw new RequireException(error);
								}							
							}

							sum = Double.parseDouble(zuam)  / Double.parseDouble(eur);
							zuam = Double.toString(sum);
						}
						if ("JPY".equals(waerk))  {
							String value = jpy;
							if (value == null || "".equals(value.trim()))		{
								String error = "CE10007";
								throw new RequireException(error);
							}else	{
								if (Double.parseDouble(value) <= 0)	{
									String error = "CE10007";
									throw new RequireException(error);
								}							
							}

							sum = Double.parseDouble(zuam)  / Double.parseDouble(jpy);
							zuam = Double.toString(sum);
						}
						if ("USD".equals(waerk))  {
							String value = usd;
							if (value == null || "".equals(value.trim()))		{
								String error = "CE10007";
								throw new RequireException(error);
							}else	{
								if (Double.parseDouble(value) <= 0)	{
									String error = "CE10007";
									throw new RequireException(error);
								}							
							}

							sum = Double.parseDouble(zuam)  / Double.parseDouble(usd);
							zuam = Double.toString(sum);
						}
					}
					// 2013.02.20 AUART가 ZE로 시작하는 경우만 환율 적용
 -----> 2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End */
					
					param.setZuam(zuam);
					if ("Q".equals(f_flag))		{ // 견적
						updateZcobt302(param);	// 부분원가 정보 저장 2012.12.15
						updateDetail(param);
					}else	{	// 계약변경
						updateZcobt304(param);
						updateZcobt304D(param);

						if ("KRW".equals(waerk) || "JPY".equals(waerk))		{
							double dZuam = 0;
							dZuam = Double.parseDouble(zuam) / 100.0;
							zuam  = dZuam + "";

							param.setZuam(zuam);
						}
						updateZsdt0091(param);
					}
				}
			}

			// 견적헤더 처리
			Ses0031ParamVO paramH = new Ses0031ParamVO();

			paramH.setMandt(mandt );	 // SAP CLIENT
			paramH.setCuser(userId);
			paramH.setUuser(userId);

			if ("Q".equals(f_flag))	{
				paramH.setQtnum(qtnum);
				paramH.setQtser(qtser);
				paramH.setQtseq(qtseq);
				paramH.setZprgflg("42"); // 원가팀완료

				updateHeader(paramH);
				mergeZsdt1091(paramH);    /* 해상운임비 - 견적번호 신규생성및 업데이트 20190226 by lyk*/
			}else	{
				paramH.setPspid(pspid);
				paramH.setSeq(seq);
				paramH.setFinl("S");		// 원가계산완료(계약변경)

				Ses0355ParamVO param0355 = new Ses0355ParamVO();
				param0355.setMandt(mandt);
				param0355.setPspid(pspid);
				param0355.setSeq(seq);
				
				service0355.createDao(session);
				
				List<Ses0355> list0355 = service0355.getContInfo(param0355);
				// 국내 대리점의 경우 수수료 계산 포함하여 처리
				if (list0355 != null && list0355.size() > 0)	{
					String sZuam = "0";
					String strAuart = list0355.get(0).getAuart().substring(2);

					BigDecimal bRate;
					if ("02".equals( strAuart ) )	{
						// 시행율
						Double dRate = 0.0;
						dRate = Double.parseDouble(list0355.get(0).getChwavwr()) / Double.parseDouble(list0355.get(0).getChnetwr()) * 100.0;

						if (dRate > 999)	{
							bRate = new BigDecimal("999");
						}else	{
							bRate = new BigDecimal(dRate + "");
							bRate = bRate.setScale(1, BigDecimal.ROUND_HALF_UP);
						}
						// 수수료 계산 함수 호출
						String sFee = calAgentFee(bRate + "", list0355.get(0).getContr_da(), list0355.get(0).getSpart());

						// 수수료가 포함된 변경원가
						sZuam = (  Double.parseDouble(list0355.get(0).getChwavwr()) + 
								  (Double.parseDouble(list0355.get(0).getChwavwr()) / 100.0 * Double.parseDouble(sFee))
								) + "";
					}else	{
						sZuam = list0355.get(0).getChwavwr();
					}
					// 화폐단위에 따른 계산

					if ("KRW".equals(list0355.get(0).getChwaerk()) || "JPY".equals(list0355.get(0).getChwaerk()))	{
						double dZuam = 0;
						dZuam = Double.parseDouble(sZuam) / 100.0;
						sZuam  = dZuam + "";
					}
					paramH.setZuam(sZuam);

					updateZsdt0090(paramH);
				}
			}
		} catch (RuntimeException ex) { 
			ex.printStackTrace();
			throw ex;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dsCheck;
	}

	public String calAgentFee(String sRate, String sContrDt, String sSpart) throws Exception	{

		try	{
			String rtnFee = "0";
			String sFee   = "0";
			int iRate     = 0;
			int iContrDt  = Integer.parseInt(sContrDt);
	
			iRate = (int) (Double.parseDouble(sRate) * 10);
	
			if(!"20".equals(sSpart))	{	// 승강기
				// 가격결정일이 200707월 이전일 경우
				if (iContrDt < 20070701 )		{
					if (iRate <= 940)	{
						sFee = "70";
					}else if ( iRate > 940 && iRate <= 980 ) {
						sFee = "60";
					} else if ( iRate > 980 && iRate < 1000 ) {
						sFee = "50";
					} else if ( iRate == 1000 ) {
						sFee = "40";
					} else if ( iRate > 1000 && iRate <= 1050 ) {
						sFee = "35";
					} else if ( iRate > 1050 && iRate <= 1070 ) {
						sFee = "25";
					} else if ( iRate > 1070 && iRate <= 1100 ) {
						sFee = "20";
					} else if ( iRate > 1100 && iRate <= 1120 ) {
						sFee = "5";
					} else {
						sFee = "0";
					}
				}		// 2008.10.27 계약분부터 적용
				else if ( iContrDt >= 20070701 && iContrDt < 20081027 )
				{
					if ( iRate <= 940 ) {
						sFee = "70";
					} else if ( iRate > 940 && iRate <= 980 ) {
						sFee = "60";
					} else if ( iRate > 980 && iRate < 1000 ) {
						sFee = "50";
					} else if ( iRate == 1000 ) {
						sFee = "40";
					} else if ( iRate > 1000 && iRate <= 1050 ) {
						sFee = "35";
					} else if ( iRate > 1050 && iRate <= 1070 ) {
						sFee = "25";
					} else if ( iRate > 1070 && iRate <= 1100 ) {
						sFee = "0";
					} else {
						sFee = "0";
					}
				}		// 2008.11월 계약분부터 적용
				else if ( iContrDt >= 20081027 && iContrDt < 20100421 )
				{
					if ( iRate <= 940 ) {
						sFee = "70";
					} else if ( iRate > 940 && iRate <= 980 ) {
						sFee = "60";
					} else if ( iRate > 980 && iRate <= 1000 ) {
						sFee = "50";
					} else if ( iRate > 1000 && iRate <= 1020 ) {
						sFee = "40";
					} else if ( iRate > 1020 && iRate <= 1050 ) {
						sFee = "35";
					} else if ( iRate > 1050 && iRate <= 1070 ) {
						sFee = "25";
					} else if ( iRate > 1070 && iRate <= 1100 ) {
						sFee = "20";
					} else if ( iRate > 1100 && iRate <= 1120 ) {
						sFee = "5";
					} else {
						sFee = "0";
					}
				}		// 2010.4월21일 계약분부터 적용
				else if ( iContrDt >= 20100421 && iContrDt < 20110531 )
				{
					if ( iRate <= 940 ) {
						sFee = "70";
					} else if ( iRate > 940 && iRate <= 980 ) {
						sFee = "60";
					} else if ( iRate > 980 && iRate <= 1000 ) {
						sFee = "50";
					} else if ( iRate > 1000 && iRate <= 1020 ) {
						sFee = "40";
					} else if ( iRate > 1020 && iRate <= 1050 ) {
						sFee = "35";
					} else if ( iRate > 1050 && iRate <= 1070 ) {
						sFee = "25";
					} else if ( iRate > 1070 && iRate <= 1150 ) {
						sFee = "20";
					} else {
						sFee = "0";
					}
				}		// 2011.5월31일 계약분부터 적용
				else if ( iContrDt >= 20110531 && iContrDt < 20120101 )
				{
					if ( iRate <= 840 ) {
						sFee = "70";
					} else if ( iRate > 840 && iRate <= 880 ) {
						sFee = "60";
					} else if ( iRate > 880 && iRate <= 900 ) {
						sFee = "50";
					} else if ( iRate > 900 && iRate <= 920 ) {
						sFee = "40";
					} else if ( iRate > 920 && iRate <= 950 ) {
						sFee = "35";
					} else if ( iRate > 950 && iRate <= 970 ) {
						sFee = "25";
					} else if ( iRate > 970 && iRate <= 1050 ) {
						sFee = "20";
					} else {
						sFee = "0";
					}
				}		// 2012.1월1일 계약분부터 적용
				else if ( iContrDt >= 20120101 )
				{
					if ( iRate <= 840 ) {
						sFee = "70";
					} else if ( iRate > 840 && iRate <= 880 ) {
						sFee = "60";
					} else if ( iRate > 880 && iRate <= 900 ) {
						sFee = "50";
					} else if ( iRate > 900 && iRate <= 920 ) {
						sFee = "40";
					} else if ( iRate > 920 && iRate <= 950 ) {
						sFee = "35";
					} else if ( iRate > 950 && iRate <= 970 ) {
						sFee = "25";
					} else if ( iRate > 970 && iRate <= 1000 ) {
						sFee = "20";
					} else {
						sFee = "0";
					}
				}
				
			}
			else
			{
				// 가격결정일이 200707월 이전일 경우
				if ( iContrDt < 20070701 )
				{
					if ( iRate <= 940 ) {
						sFee = "70";
					} else if ( iRate > 940 && iRate <= 980 ) {
						sFee = "60";
					} else if ( iRate > 980 && iRate < 1000 ) {
						sFee = "50";
					} else if ( iRate == 1000 ) {
						sFee = "40";
					} else if ( iRate > 1000 && iRate <= 1050 ) {
						sFee = "30";
					} else {
						sFee = "0";
					}
				}
				// 2008.10.27 계약분부터 적용
				else if ( iContrDt >= 20070701 && iContrDt < 20081027 )
				{
					if ( iRate <= 940 ) {
						sFee = "70";
					} else if ( iRate > 940 && iRate <= 980 ) {
						sFee = "60";
					} else if ( iRate > 980 && iRate < 1000 ) {
						sFee = "50";
					} else if ( iRate == 1000 ) {
						sFee = "40";
					} else if ( iRate > 1000 && iRate <= 1050 ) {
						sFee = "35";
					} else if ( iRate > 1050 && iRate <= 1070 ) {
						sFee = "25";
					} else if ( iRate > 1070 && iRate <= 1100 ) {
						sFee = "0";
					} else {
						sFee = "0";
					}
				}
				// 2008.11월 계약분부터 적용
				else if ( iContrDt >= 20081027 && iContrDt < 20100421 )
				{
					if ( iRate <= 940 ) {
						sFee = "70";
					} else if ( iRate > 940 && iRate <= 980 ) {
						sFee = "60";
					} else if ( iRate > 980 && iRate <= 1000 ) {
						sFee = "50";
					} else if ( iRate > 1000 && iRate <= 1020 ) {
						sFee = "40";
					} else if ( iRate > 1020 && iRate <= 1050 ) {
						sFee = "35";
					} else if ( iRate > 1050 && iRate <= 1070 ) {
						sFee = "25";
					} else if ( iRate > 1070 && iRate <= 1100 ) {
						sFee = "20";
					} else if ( iRate > 1100 && iRate <= 1120 ) {
						sFee = "5";
					} else {
						sFee = "0";
					}
				}
				// 2010.4월21일 계약분부터 적용
				else if ( iContrDt >= 20100421 && iContrDt < 20110529 )
				{
					if ( iRate <= 940 ) {
						sFee = "70";
					} else if ( iRate > 940 && iRate <= 980 ) {
						sFee = "60";
					} else if ( iRate > 980 && iRate <= 1000 ) {
						sFee = "50";
					} else if ( iRate > 1000 && iRate <= 1020 ) {
						sFee = "40";
					} else if ( iRate > 1020 && iRate <= 1050 ) {
						sFee = "35";
					} else if ( iRate > 1050 && iRate <= 1070 ) {
						sFee = "25";
					} else if ( iRate > 1070 && iRate <= 1150 ) {
						sFee = "20";
					} else {
						sFee = "0";
					}
				}
				// 2011.5월29일 계약분부터 적용
				else
				{
					if ( iRate <= 840 ) {
						sFee = "70";
					} else if ( iRate > 840 && iRate <= 880 ) {
						sFee = "60";
					} else if ( iRate > 880 && iRate <= 900 ) {
						sFee = "50";
					} else if ( iRate > 900 && iRate <= 920 ) {
						sFee = "40";
					} else if ( iRate > 920 && iRate <= 950 ) {
						sFee = "35";
					} else if ( iRate > 950 && iRate <= 970 ) {
						sFee = "25";
					} else if ( iRate > 970 && iRate <= 1050 ) {
						sFee = "20";
					} else {
						sFee = "0";
					}
				}
			}

			Double dFee = Double.parseDouble(sFee) / 10.0;
			BigDecimal rtnRate = new BigDecimal(dFee + "");
			rtnFee = rtnRate.setScale(1, BigDecimal.ROUND_HALF_UP) + "";
			return rtnFee;
		}catch(Exception e)	{
			e.printStackTrace();
			throw e;
		}
	}

	public int updateQtserZsdt1001(String sMandt, String sSonnr, String sSorlt) {
		return Ses0031Dao.updateQtserZsdt1001(sMandt, sSonnr, sSorlt);
	}

	public int updateZsdt1054H(Ses0031ParamVO param) {
		return Ses0031Dao.mergeZsdt1054H(param);
	}

	public int updateZsdt1054D(Ses0031ParamVO param) {
		return Ses0031Dao.mergeZsdt1054D(param);
	}

	public int deleteZsdt1054H(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1054H(param);
	}

	public int deleteZsdt1054D(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1054D(param);
	}

	public int deleteZsdt1050(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1050(param);
	}

	public int updateZcobt202(Ses0031ParamVO param) {
		return Ses0031Dao.updateZcobt202(param);
	}

	public int deleteZcobt202(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZcobt202(param);
	}

	public int updateZcobt204(Ses0031ParamVO param) {
		return Ses0031Dao.updateZcobt204(param);
	}

	public int updateZcobt302(Ses0031ParamVO param) {
		return Ses0031Dao.updateZcobt302(param);
	}

	public int deleteZcobt302(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZcobt302(param);
	}

	public int updateZcobt304(Ses0031ParamVO param) {
		return Ses0031Dao.updateZcobt304(param);
	}

	public int updateZsdt0091(Ses0031ParamVO param) {
		return Ses0031Dao.updateZsdt0091(param);
	}

	public int updateZsdt0090(Ses0031ParamVO param) {
		return Ses0031Dao.updateZsdt0090(param);
	}


	public int updateZcobt309(Ses0031ParamVO param) {
		return Ses0031Dao.updateZcobt309(param);
	}

	public int deleteZcobt309(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZcobt309(param);
	}

	public List<Ses0031> searchZuam(Ses0031ParamVO param) {
		return Ses0031Dao.selectListZuam(param);
	}

	public List<Ses0031> searchZcobt304(Ses0031ParamVO param) {
		return Ses0031Dao.selectZcobt304(param);
	}

	/* 2018.03.30 - 환율조회 공통 모듈화 (com.ExchangeS.getExchangeRate)	
	public Ses0031 searchNewExchange(String sMandt, String sDate) {
		return Ses0031Dao.selectNewExchange(sMandt, sDate);
	}
	*/

	public Ses0031 searchKunnr(Ses0031ParamVO param) {
		return Ses0031Dao.selectKunnr(param.getMandt(), param.getKunnr());
	}	

	public int updateSorlt(Ses0031ParamVO param) {
		return Ses0031Dao.updateSorlt(param);
	}
	
	public int deleteZsdt0713(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt0713(param);
	}
	
	public int deleteZsdt0712(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt0712(param);
	}
	
	public int updateZsdt0711(Ses0031ParamVO param) {
		return Ses0031Dao.updateZsdt0711(param);
	}
	
	public int updateZcobt304D(Ses0031ParamVO param) {
		return Ses0031Dao.updateZcobt304D(param);
	}
	
	public Dataset updateHeaderFlag(MipParameterVO paramVO, Model model, SqlSession session) {

		Dataset dsHeader = paramVO.getDataset("ds_header");

		try {
			createDao(session);  // DAO생성

			String mandt = "";
			String qtnum = "";
			String qtser   = "";
			String zprgflg = "";
			String userId  = "";

			List<Ses0031> list;
			
			for (int i=0; i<dsHeader.getRowCount(); i++) {

				mandt = DatasetUtility.getString(dsHeader, i, "MANDT" );
				qtnum = DatasetUtility.getString(dsHeader, i, "QTNUM");
				qtser   = DatasetUtility.getString(dsHeader, i, "QTSER"  );
				zprgflg = DatasetUtility.getString(dsHeader, i, "ZPRGFLG");
				userId = paramVO.getVariable("G_USER_ID");

				if (mandt == null) mandt  = paramVO.getVariable("G_MANDT");
				if (qtnum == null) qtnum  = "";
				if (qtser   == null) qtser    = "0";
				if (zprgflg == null) zprgflg = "";
				if (userId  == null) userId  = "";

/*				System.out.print("\n@@@ MANDT ===>"+ mandt +"\n");
				System.out.print("\n@@@ QTNUM ===>"+ qtnum +"\n");
				System.out.print("\n@@@ QTSER   ===>"+ qtser   +"\n");
				System.out.print("\n@@@ ZPRGFLG ===>"+ zprgflg +"\n");
				System.out.print("\n@@@ USERID  ===>"+ userId +"\n"); */

				Ses0031ParamVO param = new Ses0031ParamVO();

				param.setMandt(mandt);	 // SAP CLIENT
				param.setQtnum(qtnum);
				param.setQtser(qtser);
				param.setZprgflg(zprgflg);
				param.setUuser(userId);

				updateHeaderFlag(param);

				if (zprgflg.equals("61") || zprgflg.equals("62")) {
					param.setSorlt(zprgflg);	// 수주결과
					updateSorlt(param);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsHeader;
	}

	public int updateHeaderFlag(Ses0031ParamVO param) {
		return Ses0031Dao.updateHeaderFlag(param);
	}

	public int updateDwgFlag(Ses0031ParamVO param) {
		return Ses0031Dao.updateDwgFlag(param);
	}

	//sendmail header
	public void sendMailheader(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		Dataset ds_email = paramVO.getDataset("ds_email");  	// INPUT DATASET GET 

		String sFrom = null;
		String sTo = null;
		String sHead = null;
		String sBody = null;
		
		try  {
			createDao(session);

			// email정보 셋팅/ 전송
			for( int irow = 0; irow < ds_email.getRowCount(); irow++ ) {

				sFrom = DatasetUtility.getString(ds_email, irow, "FROM");
				sTo = DatasetUtility.getString(ds_email, irow, "TO");
				sHead = DatasetUtility.getString(ds_email, irow, "TITLE");
				sBody = DatasetUtility.getString(ds_email, irow, "CONTENT");
				
				MailService ms = new MailService(); 
				ms.send(sFrom, sTo, sHead, sBody);

			} // end of for
			
					 
		}  catch (Exception e) { 
		}
	}

	public int updateZsdt0090Status(Ses0031ParamVO param) {
		return Ses0031Dao.updateZsdt0090Status(param);
	}

	// 2013.04.25 부분원가요청 반려
	public void saveZuamReject(MipParameterVO paramVO, Model model, SqlSession session) throws Exception	{
		Dataset dsReject = paramVO.getDataset("ds_reject");

		try {
			createDao(session);  // DAO생성

			String f_flag = paramVO.getVariable("f_flag");

			for (int i = 0; i < dsReject.getRowCount(); i++) {

				Ses0031ParamVO param = new Ses0031ParamVO();

				param.setMandt(paramVO.getVariable("G_MANDT"));	 // SAP CLIENT

				param.setQtnum(DatasetUtility.getString(dsReject, i, "QTNUM"));
				param.setQtser(DatasetUtility.getString(dsReject, i, "QTSER"));
				param.setPspid(DatasetUtility.getString(dsReject, i, "PSPID"));
				param.setSeq(DatasetUtility.getString(dsReject, i, "SEQ"));
				param.setZprgflg(DatasetUtility.getString(dsReject, i, "ZPRGFLG"));
				param.setFinl(DatasetUtility.getString(dsReject, i, "FINL"));
				param.setUuser(paramVO.getVariable("G_USER_ID"));
				
				// 부분원가정보 제거
				deleteZcobt309(param);
				deleteZsdt1054H(param);
				deleteZsdt1054D(param);
				if ("Q".equals(f_flag))			{	// 견적
					updateHeaderFlag(param);
				}else if ("P".equals(f_flag))	{	// 계약변경
					updateZsdt0090Status(param);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int updateZsdt1091(Ses0031ParamVO param) {
		return 	Ses0031Dao.updateZsdt1091(param);
	}

	public int updateZsdt1091Lh(Ses0031ParamVO param) {
		return 	Ses0031Dao.updateZsdt1091Lh(param);
	}
	
	public int deleteZsdt1091(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1091(param);
	}	
	
	public int deleteZsdt1093(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1093(param);
	}	

	/* 해상운임비 특성코드값 합계관리 ZSDT1091 BY LYK 20190222*/
	public int mergeZsdt1091(Ses0031ParamVO param) {
		return Ses0031Dao.mergeZsdt1091(param);
	}

	/* 해상운임비 특성코드값 합계관리 ZSDT1093 BY LYK 20190222*/
	public int mergeZsdt1093(Ses0031ParamVO param) {
		return Ses0031Dao.mergeZsdt1093(param);
	}

	public int addZsdt1091(Ses0031ParamVO param) {
		return Ses0031Dao.addZsdt1091(param);
	}
	
	public int addZsdt1093(Ses0031ParamVO param) {
		return Ses0031Dao.addZsdt1093(param);
	}

	//EGIS_FLAG 조회
	public Ses0031ParamVO selectEgisFlag(Ses0031ParamVO param) {
		return Ses0031Dao.selectEgisFlag(param);
	}

//	public List<Ses0031> searchZuam1(Ses0031ParamVO param) {
//		return Ses0031Dao.selectListZuam1(param);
//	}

	public int updateDetailEgis(Ses0031ParamVO param) {
		return Ses0031Dao.updateDetailEgis(param);
	}

	public Dataset saveZuamEgis(MipParameterVO paramVO, Model model, SqlSession session) throws RuntimeException, Exception{
		
		ExchangeS.createDao(session);		//DAO 생성
		
		// 2013.02.13 반영전 부분원가가 저장되도록 변경 
		Dataset dsCheck      = paramVO.getDataset("ds_check");		// 부분원가
		Dataset dsApplyCheck = paramVO.getDataset("ds_apply");		// 반영원가
		Dataset dsExchange   = paramVO.getDataset("ds_exchange");


		try {
			createDao(session);  // DAO생성

			String flag   = "";
			String mandt  = "";
			String qtnum  = "";
			String qtser  = "";
			String qtseq  = "";
			String pspid  = "";
			String posid  = "";
			String atype  = "";
			String gubun  = "";
			String seria  = "0";
			String blseq  = "";
			String blnum  = "";
			String blnam  = "";
			String zitem  = "";
			String zchar  = "";
			String zquty  = "";
			String zamnt  = "";
			String ztotl  = "";
			String bigo   = "";
			String tex1   = "";
			String tex2   = "";
			String userId = "";
			String state  = "";
			String addt01 = "";
			String messg  = "";
			String zuam   = "0";
			String zuams   = "0";
			String zurate   = "0";
			String exchangeR = "0";
			String znumber = "0";
			
			String rtnCode ="";
			// 견적 또는 계약변경 구분
			String f_flag = paramVO.getVariable("f_flag");
			String f_save = paramVO.getVariable("f_save");

			String waerk = DatasetUtility.getString(dsExchange, 0, "FCURR");	// 소스 통화 단위
			String auart = DatasetUtility.getString(dsExchange, 0, "AUART");
			String Basedt = DatasetUtility.getString(dsExchange, 0, "BASEDT");   //견적일자
			String Kurst = DatasetUtility.getString(dsExchange, 0, "KURST");
			
			if (waerk != null && !"KRW".equals(waerk)) {
				exchangeR = ExchangeS.getExchangeRate(DatasetUtility.getString(dsExchange, 0, "MANDT"), Kurst, Basedt, waerk, "KRW");		
			}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			String seq1    = "";
			String ratio = "1";
			String Zeam = "0";
			double sum1 = 0;

			PlatformData pd = new PlatformData();			
			pd.setDataSetList(new DataSetList());
			//pd.setVariableList(vl);
			DataSet ds_egis_price = new DataSet("ds_egis_price");
			ds_egis_price.addColumn("QTNUM", DataTypes.STRING);
			ds_egis_price.addColumn("QTSEQ", DataTypes.INT);
			ds_egis_price.addColumn("QTSER", DataTypes.INT);
			ds_egis_price.addColumn("ZUAM", DataTypes.STRING);
			ds_egis_price.addColumn("ZEAM", DataTypes.STRING);

			DataSet ds_egis_block = new DataSet("ds_egis_block");
			ds_egis_block.addColumn("MANDT", DataTypes.STRING);
			ds_egis_block.addColumn("QTNUM", DataTypes.STRING);
			ds_egis_block.addColumn("QTSEQ", DataTypes.INT);
			ds_egis_block.addColumn("QTSER", DataTypes.INT);
			ds_egis_block.addColumn("BLSEQ", DataTypes.STRING);
			ds_egis_block.addColumn("BDMNG", DataTypes.STRING);
			ds_egis_block.addColumn("SALES", DataTypes.STRING);
			ds_egis_block.addColumn("MESSG", DataTypes.STRING);					
			ds_egis_block.addColumn("ZCTOTAL", DataTypes.STRING);
			ds_egis_block.addColumn("BLOCKNAME", DataTypes.STRING);
			ds_egis_block.addColumn("MAKTX", DataTypes.STRING);
			
			List<Ses0031> list;
			
			if(dsApplyCheck.getRowCount() == 0) throw new BizException("E-GIS 전송 데이타가 존재하지 않습니다.");				

			for (int i = 0; i < dsApplyCheck.getRowCount(); i++) {

				addt01 = DatasetUtility.getString(dsApplyCheck, i, "ADDT01");

				if ( addt01 != null) {

					mandt  = DatasetUtility.getString(dsApplyCheck, i, "MANDT");
					qtnum  = DatasetUtility.getString(dsApplyCheck, i, "QTNUM");
					qtser  = DatasetUtility.getString(dsApplyCheck, i, "QTSER");
					qtseq  = DatasetUtility.getString(dsApplyCheck, i, "QTSEQ");
					pspid  = DatasetUtility.getString(dsApplyCheck, i, "PSPID");
					posid  = DatasetUtility.getString(dsApplyCheck, i, "POSID");
					seq1    = DatasetUtility.getString(dsApplyCheck, i, "SEQ");
					userId = paramVO.getVariable("G_USER_ID");
					
					if (mandt  == null) mandt  = paramVO.getVariable("G_MANDT");
					if (qtnum  == null) qtnum  = "";
					if (qtser  == null) qtser  = "0";
					if (qtseq  == null) qtseq  = "0";
					if (pspid  == null) pspid  = "";
					if (posid  == null) posid  = "";
					if (seq1    == null) seq1   = "0";
					if (userId == null) userId = "";
					if (addt01 == null) addt01 = "0";				

					Ses0031ParamVO param = new Ses0031ParamVO();

					param.setMandt(mandt );	 // SAP CLIENT
					param.setQtnum(qtnum);
					param.setQtser(qtser);
					param.setQtseq(qtseq);
					param.setPspid(pspid);
					param.setPosid(posid);
					param.setSeq(seq1);
					param.setCuser(userId);
					param.setUuser(userId);
					param.setAddt01(addt01);
					//param.setZprgflg("42"); // 원가팀완료

					// 구분이 '1'(모델)의 경우는 ZCOBT302, ZCOBT304에 원가 정보가 존재하지 않음.
					list = searchZuam(param);

					if (list.size() > 0) {
						zuam = list.get(0).getZuam().toString();
					}	
					else {
						zuam = "0";
					}	
					
					Ses0031ParamVO ratioVO = new Ses0031ParamVO();
					ratioVO = Ses0031Dao.selectRatio(param);
					if(ratioVO != null) {
						ratio = ratioVO.getRatio();
					} else {
						ratio = "1";   				//마진율
					}
					param.setRatio(ratio);
					
					sum1  = (Double.parseDouble(zuam) + Double.parseDouble(addt01));
					zuam = Double.toString(sum1);
					
					if ( Double.parseDouble(exchangeR) > 0 && auart != null && "ZE".equals(auart.substring(0, 2)))	{
						sum1 = Double.parseDouble(zuam) / Double.parseDouble(exchangeR);
						zuam = Double.toString(sum1);
					}
					
					param.setZuam(zuam);
					
					Ses0031ParamVO egisDataVO = new Ses0031ParamVO();
					egisDataVO = Ses0031Dao.selectEgisData(param);
					if(egisDataVO != null) {
						Zeam = egisDataVO.getZeam();
					} 
					
					int nRow = ds_egis_price.newRow();
					ds_egis_price.set(nRow,"QTNUM",qtnum);
					ds_egis_price.set(nRow,"QTSEQ",qtseq);
					ds_egis_price.set(nRow,"QTSER",qtser);
					ds_egis_price.set(nRow,"ZUAM" ,zuam);
					ds_egis_price.set(nRow,"ZEAM" ,Zeam);				
				}
			}			
			pd.getDataSetList().add(ds_egis_price);
			pd.getDataSetList().add(ds_egis_block);
			
			try {
				String sysid = paramVO.getVariable("G_SYSID");
				/** egis 운영 or 개발 판단 임시 조치 **/
				String f_gsnam = paramVO.getVariable("f_gsnam");
				sysid = f_gsnam.startsWith("E-GIS TEST-") ? "HED" : sysid;
				sysid = f_gsnam.startsWith("E-GIS]") ? "HEP" : sysid;
				/** egis 운영 or 개발 판단 임시 조치 **/
				FSPPopClientHelper fspPop = new FSPPopClientHelper("SetSRMCost", JSONUtil.makeToJSON(pd).toJSONString(), sysid);
				rtnCode = fspPop.getError();
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				throw new BizException("e-GIS 인터페이스 중 오류가 발생하였습니다.  재시도 바랍니다.");
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizException("e-GIS 인터페이스 중 오류가 발생하였습니다.  재시도 바랍니다.");
			}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			if(!"S".equals(rtnCode)) {
				throw new BizException("e-GIS 인터페이스 중 오류가 발생하였습니다.  재시도 바랍니다.");
			}
			
			for (int i=0;i< dsCheck.getRowCount(); i++) {
				
				flag = DatasetUtility.getString(dsCheck, i, "FLAG");	
				if ( flag != null) {

					mandt  = DatasetUtility.getString(dsCheck, i, "MANDT" );
					qtnum  = DatasetUtility.getString(dsCheck, i, "QTNUM");
					qtser  = DatasetUtility.getString(dsCheck, i, "QTSER"  );
					qtseq  = DatasetUtility.getString(dsCheck, i, "QTSEQ"  );
					pspid  = DatasetUtility.getString(dsCheck, i, "PSPID"  );
					posid  = DatasetUtility.getString(dsCheck, i, "POSID"  );
					atype  = DatasetUtility.getString(dsCheck, i, "ATYPE"  );
					gubun  = DatasetUtility.getString(dsCheck, i, "GUBUN");
					seria  = DatasetUtility.getString(dsCheck, i, "SERIA"  );
					blseq  = DatasetUtility.getString(dsCheck, i, "BLSEQ"  );
					blnum  = DatasetUtility.getString(dsCheck, i, "BLNUM");
					blnam  = DatasetUtility.getString(dsCheck, i, "BLNAM");
					zitem  = DatasetUtility.getString(dsCheck, i, "ZITEM"  );
					zchar  = DatasetUtility.getString(dsCheck, i, "ZCHAR" );
					zquty  = DatasetUtility.getString(dsCheck, i, "ZQUTY" );
					zamnt  = DatasetUtility.getString(dsCheck, i, "ZAMNT");
					ztotl  = DatasetUtility.getString(dsCheck, i, "ZTOTL"  );
					bigo   = DatasetUtility.getString(dsCheck, i, "BIGO"   );
					tex1   = DatasetUtility.getString(dsCheck, i, "TEX1"   );
					tex2   = DatasetUtility.getString(dsCheck, i, "TEX2"   );
					state  = DatasetUtility.getString(dsCheck, i, "STATE"  );
					addt01 = DatasetUtility.getString(dsCheck, i, "ADDT01");
					messg  = DatasetUtility.getString(dsCheck, i, "MESSG"  );
					userId = paramVO.getVariable("G_USER_ID");
					
					if (mandt == null) mandt = paramVO.getVariable("G_MANDT");
					if (qtnum == null) qtnum = "";
					if (qtser == null) qtser   = "0";
					if (qtseq == null) qtseq   = "0";
					if (pspid == null) pspid  = "";
					if (posid == null) posid  = "";
					if (atype == null) atype   = "";
					if (gubun == null) gubun ="";
					if (seria == null) seria   = "0";
					if (blseq == null) blseq  = "";
					if (blnum == null) blnum = "";
					if (blnam == null) blnam = "";
					if (zitem == null) zitem  = "";
					if (zchar == null) zchar  = "";
					if (zquty == null) zquty  = "0";
					if (zamnt == null) zamnt = "0";
					if (ztotl == null) ztotl    = "0";
					if (bigo  == null) bigo   = "";
					if (tex1  == null) tex1    = "";
					if (tex2  == null) tex2    = "";
					if (state  == null) state  = "";
					if (messg == null)  messg = "";
					if (userId == null) userId = "";
					if (f_flag == null) f_flag = "Q";	// 견적으로 처리 (Q : 견적, P :계약변경)

					Ses0031ParamVO param = new Ses0031ParamVO();

					param.setMandt(mandt);	 // SAP CLIENT
					param.setQtnum(qtnum);
					param.setQtser(qtser);
					param.setQtseq(qtseq);
					param.setPspid(pspid);
					param.setPosid(posid);
					param.setAtype(atype);
					param.setGubun(gubun);
					param.setSeria(seria);
					param.setBlseq(blseq);
					param.setBlnum(blnum);
					param.setBlnam(blnam);
					param.setZitem(zitem);
					param.setZchar(zchar);
					param.setZquty(zquty);
					param.setZamnt(zamnt);
					param.setZtotl(ztotl);
					param.setBigo(bigo);
					param.setTex1(tex1);
					param.setTex2(tex2);
					param.setCuser(userId);
					param.setUuser(userId);
					param.setAddt01(addt01);
					param.setMessg(messg);

					if (flag.equals("I") || flag.equals("U")) {

						if (state.length() > 0) param.setState("X");
						else                    param.setState("");

						updateZcobt309(param);
						
						if ("Q".equals(f_flag))			{	// 견적
							updateZcobt202(param);

							// 저장시에는 반영되면 안됨 2012.12.15
							//if (addt01 != null) updateZcobt302(param);
						}else	if ("P".equals(f_flag))	{	// 계약변경
							updateZcobt204(param);
						}

						param.setFlag(state);
						updateZsdt1054H(param);
						updateZsdt1054D(param);
					} else if (flag.equals("D")) {
						param.setState("");
						updateZcobt309(param);

						if ("Q".equals(f_flag))			{	// 견적
							updateZcobt202(param);

							// 저장시에는 반영되면 안됨 2012.12.15
							//if (addt01 != null) updateZcobt302(param);	
						}else	if ("P".equals(f_flag))	{	// 계약변경
							updateZcobt204(param);
						}

						if (f_save != null && "D".equals(f_save))	{
							deleteZsdt1054H(param); //2012.12.28 부분원가 삭제시만 처리
						}

						deleteZsdt1054D(param);
					}
				}
			}

			// 부분원가 반영 후 원가적용 처리
			mandt  = "";
			qtnum  = "";
			qtser  = "";
			qtseq  = "";
			pspid  = "";
			posid  = "";
			addt01 = "";
			userId = "";
			zuam   = "0";
			f_flag = "Q";	// 견적 및 계약변경 구분

			String seq    = "";
			double sum    = 0;

			f_flag = paramVO.getVariable("f_flag");

/* -----< 2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start
 -----> 2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End */
			
			for (int i = 0; i < dsApplyCheck.getRowCount(); i++) {

				addt01 = DatasetUtility.getString(dsApplyCheck, i, "ADDT01");

				if ( addt01 != null) {

					mandt  = DatasetUtility.getString(dsApplyCheck, i, "MANDT");
					qtnum  = DatasetUtility.getString(dsApplyCheck, i, "QTNUM");
					qtser  = DatasetUtility.getString(dsApplyCheck, i, "QTSER");
					qtseq  = DatasetUtility.getString(dsApplyCheck, i, "QTSEQ");
					pspid  = DatasetUtility.getString(dsApplyCheck, i, "PSPID");
					posid  = DatasetUtility.getString(dsApplyCheck, i, "POSID");
					seq    = DatasetUtility.getString(dsApplyCheck, i, "SEQ");
					userId = paramVO.getVariable("G_USER_ID");
					
					if (mandt  == null) mandt  = paramVO.getVariable("G_MANDT");
					if (qtnum  == null) qtnum  = "";
					if (qtser  == null) qtser  = "0";
					if (qtseq  == null) qtseq  = "0";
					if (pspid  == null) pspid  = "";
					if (posid  == null) posid  = "";
					if (seq    == null) seq    = "0";
					if (userId == null) userId = "";
					if (addt01 == null) addt01 = "0";
					

					Ses0031ParamVO param = new Ses0031ParamVO();

					param.setMandt(mandt );	 // SAP CLIENT
					param.setQtnum(qtnum);
					param.setQtser(qtser);
					param.setQtseq(qtseq);
					param.setPspid(pspid);
					param.setPosid(posid);
					param.setSeq(seq);
					param.setCuser(userId);
					param.setUuser(userId);
					param.setAddt01(addt01);
					//param.setZprgflg("42"); // 원가팀완료
					param.setRatio(ratio);

					// 구분이 '1'(모델)의 경우는 ZCOBT302, ZCOBT304에 원가 정보가 존재하지 않음.
					if ("Q".equals(f_flag))		{	// 견적원가
						list = searchZuam(param);
					}else	{
						list = searchZcobt304(param);	// 계약변경 원가
					}

					if (list.size() > 0)
						zuam = list.get(0).getZuam().toString();
					else
						zuam = "0";

					sum  = (Double.parseDouble(zuam) + Double.parseDouble(addt01));
					zuam = Double.toString(sum);

					if ( Double.parseDouble(exchangeR) > 0 && auart != null && "ZE".equals(auart.substring(0, 2)))	{
						sum = Double.parseDouble(zuam)  / Double.parseDouble(exchangeR);
						zuam = Double.toString(sum);
					}
					
/* -----< 2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		Start					
					// 2013.02.20 AUART가 ZE로 시작하는 경우만 환율 적용
					// 2013.02.20 AUART가 ZE로 시작하는 경우만 환율 적용
 -----> 2018.03.30 환율 관리 공통모듈화 ExchangeS.searchExchangeRate 처리에 따른 주석 by mj.Shin 		End */
					
					param.setZuam(zuam);
					
					if ("Q".equals(f_flag))		{ // 견적
						updateZcobt302(param);	// 부분원가 정보 저장 2012.12.15
						updateDetailEgis(param);
					}else	{	// 계약변경
						updateZcobt304(param);
						updateZcobt304D(param);

						if ("KRW".equals(waerk) || "JPY".equals(waerk))		{
							double dZuam = 0;
							dZuam = Double.parseDouble(zuam) / 100.0;
							zuam  = dZuam + "";

							param.setZuam(zuam);
						}
						updateZsdt0091(param);
					}
				}
			}

			// 견적헤더 처리
			Ses0031ParamVO paramH = new Ses0031ParamVO();

			paramH.setMandt(mandt );	 // SAP CLIENT
			paramH.setCuser(userId);
			paramH.setUuser(userId);

			if ("Q".equals(f_flag))	{
				paramH.setQtnum(qtnum);
				paramH.setQtser(qtser);
				paramH.setQtseq(qtseq);
				paramH.setZprgflg("42"); // 원가팀완료

				updateHeader(paramH);
//					mergeZsdt1091(paramH);    /* 해상운임비 - 견적번호 신규생성및 업데이트 20190226 by lyk*/
			} else	{
				paramH.setPspid(pspid);
				paramH.setSeq(seq);
				paramH.setFinl("S");		// 원가계산완료(계약변경)

				Ses0355ParamVO param0355 = new Ses0355ParamVO();
				param0355.setMandt(mandt);
				param0355.setPspid(pspid);
				param0355.setSeq(seq);
				
				service0355.createDao(session);
				
				List<Ses0355> list0355 = service0355.getContInfo(param0355);
				// 국내 대리점의 경우 수수료 계산 포함하여 처리
				if (list0355 != null && list0355.size() > 0)	{
					String sZuam = "0";
					String strAuart = list0355.get(0).getAuart().substring(2);

					BigDecimal bRate;
					if ("02".equals( strAuart ) )	{
						// 시행율
						Double dRate = 0.0;
						dRate = Double.parseDouble(list0355.get(0).getChwavwr()) / Double.parseDouble(list0355.get(0).getChnetwr()) * 100.0;

						if (dRate > 999)	{
							bRate = new BigDecimal("999");
						}else	{
							bRate = new BigDecimal(dRate + "");
							bRate = bRate.setScale(1, BigDecimal.ROUND_HALF_UP);
						}
						// 수수료 계산 함수 호출
						String sFee = calAgentFee(bRate + "", list0355.get(0).getContr_da(), list0355.get(0).getSpart());

						// 수수료가 포함된 변경원가
						sZuam = (  Double.parseDouble(list0355.get(0).getChwavwr()) + 
								  (Double.parseDouble(list0355.get(0).getChwavwr()) / 100.0 * Double.parseDouble(sFee))
								) + "";
					}else	{
						sZuam = list0355.get(0).getChwavwr();
					}
					// 화폐단위에 따른 계산

					if ("KRW".equals(list0355.get(0).getChwaerk()) || "JPY".equals(list0355.get(0).getChwaerk()))	{
						double dZuam = 0;
						dZuam = Double.parseDouble(sZuam) / 100.0;
						sZuam  = dZuam + "";
					}
					paramH.setZuam(sZuam);

					updateZsdt0090(paramH);
				}
			}				
		} catch (RuntimeException ex) { 
			ex.printStackTrace();
			throw ex;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dsCheck;
	}

	public List<Map<String, Object>> searchZsdt1072(Ses0031ParamVO param) {
		return Ses0031Dao.selectZsdt1072(param);
	}

	public List<Map<String, Object>> searchZsdt1073(Ses0031ParamVO param) {
		return Ses0031Dao.selectZsdt1073(param);
	}
}
