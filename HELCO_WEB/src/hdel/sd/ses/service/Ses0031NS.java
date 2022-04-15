package hdel.sd.ses.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.CommRfcException;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.lib.exception.RequireException;
import hdel.lib.service.MailService;
import hdel.lib.service.SrmService;
import hdel.lib.service.ZSDT0167S;
import hdel.sd.com.dao.Com0150D;
import hdel.sd.com.dao.ComboD;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.domain.Com0150;
import hdel.sd.com.domain.Com0150ParamVO;
import hdel.sd.com.domain.ComboParamVO;
import hdel.sd.com.domain.ComboVO;
import hdel.sd.com.domain.DutyObj;
import hdel.sd.com.service.AutoQtNumberS;
import hdel.sd.com.service.DutyNS;
import hdel.sd.ses.dao.Ses0030D;
import hdel.sd.ses.dao.Ses0031ND;
import hdel.sd.ses.dao.Ses0032D;
import hdel.sd.ses.domain.Ses0030;
import hdel.sd.ses.domain.Ses0030ParamVO;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.domain.Ses0032;
import hdel.sd.ses.domain.Ses0032ParamVO;
import hdel.sd.ses.domain.Ses0355;
import hdel.sd.ses.domain.Ses0355ParamVO;
import hdel.sd.sso.domain.Sso0050ParamVO;
import hdel.sd.sso.domain.Sso0050VO;
import hdel.sd.sso.domain.ZCOBT302;
import hdel.sd.sso.service.Sso0050S;
import tit.service.sapjco3.Rfc;
import tit.service.sapjco3.RfcException;
import tit.util.DatasetUtility;
import hdel.sd.com.domain.ExchangeVO;
import hdel.sd.com.service.ExchangeS;

@Service
public class Ses0031NS extends SrmService {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AutoQtNumberS Autoservice;	// 견적번호채번 서비스 

	@Autowired
	private Ses0355S service0355;

	private Ses0031ND Ses0031Dao;

	@Autowired
	private Ses0030S service0030;

	@Autowired
	private Sso0050S service0050S;

	private ComboD commonD;
	
	private Ses0032D Ses0032Dao;
	
	private Ses0030D Ses0030Dao;
	
	private Com0150D Com0150Dao;
	
//	@Autowired
//	private DutyS dutyService;

	@Autowired
	private DutyNS dutyNService;
	
	@Autowired
	private Ses0031NJS ses0031NJS;
	
	@Autowired
	private ExchangeS ExchangeS;	
	
	// 해외모의견적 서비스빈 21.06.04
	@Autowired
	private Ses0030ES ses0030ES; 
	
	public void createDao(SqlSession sqlSession) {
		Ses0031Dao = sqlSession.getMapper(Ses0031ND.class);
	}

	//=========================================================================================
    //  함수기능 	: 최초 화면 오픈시 코드 및 조회쿼리
    //  기능ID   	: UF001
    //  개선내역 	: 각자 가져오던 코드 및 조회 쿼리 하나의 펑션으로 합쳐서 한번에 처리
    //  HISTORY : 2016.02.15 최초 코딩 hsi
    //=========================================================================================
	public MipResultVO searchInit(MipParameterVO paramVO, SqlSession sessionVO) {
		MipResultVO resultVO = new MipResultVO();
		
		// OUTPUT DATASET DECLARE  
		Dataset ds_list_inco1 	= paramVO.getDataset("ds_list_inco1");
		Dataset ds_class 		= paramVO.getDataset("ds_class");
		Dataset ds_cond 		= paramVO.getDataset("ds_cond");
		Dataset ds_header 		= paramVO.getDataset("ds_header");
		Dataset ds_acapa 		= paramVO.getDataset("ds_acapa");
		Dataset ds_error		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		
		String mandt 			= paramVO.getVariable("G_MANDT");				// IN_CLIENT
		String sonnr			= DatasetUtility.getString(ds_header, 0, "SONNR");
		String lang 			= paramVO.getVariable("G_LANG");
		
		try {
			// dao 생성
			commonD = sessionVO.getMapper(ComboD.class);
			Ses0032Dao = sessionVO.getMapper(Ses0032D.class);
			Ses0030Dao = sessionVO.getMapper(Ses0030D.class);
			
			// vo 선언
			ComboParamVO comboParam = new ComboParamVO();
			Ses0031ParamVO ses0031ParamVo = new Ses0031ParamVO();
			Ses0032ParamVO ses0032ParamVo = new Ses0032ParamVO();
			Ses0030ParamVO ses0030ParamVo = new Ses0030ParamVO();
			
			// 변수 세팅
			comboParam.setMandt(mandt);  
			ses0031ParamVo.setMandt(mandt);
			ses0031ParamVo.setAtinn(DatasetUtility.getString(ds_acapa, 0, "ATINN"));
			ses0031ParamVo.setSpras(DatasetUtility.getString(ds_acapa, 0, "SPRAS"));
			ses0031ParamVo.setQtnum(DatasetUtility.getString(ds_cond,"QTNUM"));
			ses0031ParamVo.setQtser(DatasetUtility.getString(ds_cond,"QTSER"));
			ses0032ParamVo.setLang(lang);
			ses0032ParamVo.setMandt(mandt);
			ses0032ParamVo.setZpym(DatasetUtility.getString(ds_cond,"ZPYM"));
			ses0032ParamVo.setZkunnr(DatasetUtility.getString(ds_cond,"ZKUNNR"));
			ses0032ParamVo.setSpart(DatasetUtility.getString(ds_cond,"SPART"));
			ses0032ParamVo.setZagnt(DatasetUtility.getString(ds_cond,"ZAGNT"));
			ses0032ParamVo.setKunnr(DatasetUtility.getString(ds_cond,"KUNNR"));
			ses0032ParamVo.setSonnr(sonnr);
			ses0030ParamVo.setLang(lang);
			ses0030ParamVo.setMandt(mandt);
			ses0030ParamVo.setFrqtdat(DatasetUtility.getString(ds_cond,"FR_QTDAT"));
			ses0030ParamVo.setToqtdat(DatasetUtility.getString(ds_cond,"TO_QTDAT"));
			ses0030ParamVo.setFrzestdat (DatasetUtility.getString(ds_cond,"FR_ZESTDAT"));
			ses0030ParamVo.setTozestdat(DatasetUtility.getString(ds_cond,"TO_ZESTDAT"));
			ses0030ParamVo.setVkbur(DatasetUtility.getString(ds_cond,"VKBUR"));
			ses0030ParamVo.setVkgrp(DatasetUtility.getString(ds_cond,"VKGRP"));
			ses0030ParamVo.setZprgflg(DatasetUtility.getString(ds_cond,"ZPRGFLG"));
			ses0030ParamVo.setZkunnr(DatasetUtility.getString(ds_cond,"SMAN"));
			ses0030ParamVo.setAuart(DatasetUtility.getString(ds_cond,"AGNTGB"));
			ses0030ParamVo.setQtnum(DatasetUtility.getString(ds_cond,"QTNUM"));
			ses0030ParamVo.setQtser(DatasetUtility.getString(ds_cond,"QTSER"));
			ses0030ParamVo.setGno(DatasetUtility.getString(ds_cond,"GNO"));
			ses0030ParamVo.setDarijumzkunnr(DatasetUtility.getString(ds_cond,"DARIJUMUSER"));
			if ("B1".equals(DatasetUtility.getString(ds_cond,"ARA")))ses0030ParamVo.setZregn("E5");	
			else ses0030ParamVo.setZregn(DatasetUtility.getString(ds_cond,"ARA"));
			ses0030ParamVo.setTquot(paramVO.getVariable("TQUOT"));
			// 모의견적여부 추가 2021.06.08
			ses0030ParamVo.setSmlysno(paramVO.getVariable("SMLYSNO"));

			// 서비스호출
			List<Ses0032> ses0032List = null;
			List<Ses0030> ses0030List = null;
			
			List<ComboVO> listCombo = commonD.selectListInco(comboParam); 					// 인도조건
			List<Ses0031> ses0031List = Ses0031Dao.selectListClass(ses0031ParamVo); 		// 제품구분
			List<Ses0031> ses0031List2 = Ses0031Dao.selectListAcapa(ses0031ParamVo); 		// 용량
			
			if(!"".equals(sonnr) && sonnr != null) ses0032List = Ses0032Dao.selectList(ses0032ParamVo);			// 헤더(so)
			else ses0030List = Ses0030Dao.selectListHeader(ses0030ParamVo); 				// 헤더
			
			// DATASET 복사
			ds_header.deleteAll();
			
			CallSAPHdel.moveListToDsSub(ds_list_inco1, ComboVO.class, listCombo);
			CallSAPHdel.moveListToDsSub(ds_class, Ses0031.class, ses0031List);
			CallSAPHdel.moveListToDsSub(ds_acapa, Ses0031.class, ses0031List2);
			
			if(!"".equals(sonnr) && sonnr != null) CallSAPHdel.moveListToDsSub(ds_header, Ses0032.class, ses0032List); 
			else CallSAPHdel.moveListToDsSub(ds_header, Ses0030.class, ses0030List); 
						
		} catch (Exception e) {
			e.printStackTrace(); 
			
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			
			ds_error.setId("ds_error");  		// ERROR 
			resultVO.addDataset(ds_error);  	// ERROR
			
			return resultVO;
		}
					
		ds_list_inco1.setId("ds_list_inco1");
		ds_class.setId("ds_class");
		ds_header.setId("ds_header");
		ds_acapa.setId("ds_acapa");
		ds_error.setId("ds_error");
		
		resultVO.addDataset(ds_list_inco1);
		resultVO.addDataset(ds_class);
		resultVO.addDataset(ds_header);
		resultVO.addDataset(ds_acapa);
		resultVO.addDataset(ds_error);
		
		return resultVO;
	}
	
	//=========================================================================================
    //  함수기능 	: 견적 사양 조회
    //  기능ID   	: UF001
    //  개선내역 	: 견적번호/차수/mo별로 가져오던 조회 쿼리를 견적번호의 차수별로 한번에 조회
    //  HISTORY : 2016.02.15 최초 코딩 hsi
    //=========================================================================================
	public MipResultVO searchTemplate(MipParameterVO paramVO, SqlSession sessionVO) {
		MipResultVO resultVO = new MipResultVO();
		
		// OUTPUT DATASET DECLARE  
		Dataset ds_template = paramVO.getDataset("ds_template");
		Dataset ds_detail	= paramVO.getDataset("ds_detail");
		Dataset ds_cond_atnam	= null;
		Dataset ds_zregn2		= null;
		Dataset ds_zregn3		= null;
		Dataset ds_t302 		= paramVO.getDataset("ds_t302");
		Dataset ds_error		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		
		// 견적상세(해외)에서는 사용하지 않는 로직은 배제
		if(!"EN".equals(paramVO.getVariable("ATNAM2"))) {
			ds_cond_atnam 		= paramVO.getDataset("ds_cond_atnam");
			ds_zregn2 			= paramVO.getDataset("ds_zregn2");
			ds_zregn3 			= paramVO.getDataset("ds_zregn3");			
		}
		
		String lang 			= paramVO.getVariable("G_LANG");
		String mandt			= DatasetUtility.getString(ds_detail, 0, "MANDT");
		String qtnum			= DatasetUtility.getString(ds_detail, 0, "QTNUM");
		String qtser			= DatasetUtility.getString(ds_detail, 0, "QTSER");
		String atnam2			= paramVO.getVariable("ATNAM2");
		
		int j = 0;
		
		try {
			// dao 생성
			commonD = sessionVO.getMapper(ComboD.class);
			Com0150Dao = sessionVO.getMapper(Com0150D.class);
						
			// vo생성
			Ses0031ParamVO param  = new Ses0031ParamVO();
			Com0150ParamVO com0150ParamVo = new Com0150ParamVO();
			Com0150ParamVO com0150ParamVo2 = new Com0150ParamVO();
			
			// 변수 세팅
			if(mandt == null) param.setMandt("");  	else param.setMandt(mandt);
			if(qtnum == null) param.setQtnum("");  	else param.setQtnum(qtnum);
			if(qtser == null) param.setQtser("0");  else param.setQtser(qtser);
			if(lang == null) param.setLang("");   	else param.setLang(lang);

			if(!"EN".equals(atnam2)) {
				String klart = DatasetUtility.getString(ds_cond_atnam,"KLART");
				String class1 = DatasetUtility.getString(ds_cond_atnam,"CLASS1");
				String atkla = DatasetUtility.getString(ds_cond_atnam,"ATKLA");
				String atnam = DatasetUtility.getString(ds_cond_atnam,"ATNAM");
				String where = DatasetUtility.getString(ds_cond_atnam,"WHERE");
				
				if(mandt == null) com0150ParamVo.setMandt("");  	else com0150ParamVo.setMandt(mandt);
				if(klart == null) com0150ParamVo.setKlart("");  	else com0150ParamVo.setKlart(klart);
				if(class1 == null) com0150ParamVo.setClass1("");  	else com0150ParamVo.setClass1(class1);
				if(atkla == null) com0150ParamVo.setAtkla("");  	else com0150ParamVo.setAtkla(atkla);
				if(atnam == null) com0150ParamVo.setAtnam("");  	else com0150ParamVo.setAtnam(atnam);
				if(where == null) com0150ParamVo.setWhere("");  	else com0150ParamVo.setWhere(where);
				if ("ko".equals(lang) || "SHN_01".equals(class1) || "SHN_02".equals(class1)) com0150ParamVo.setSpras("3");
				else com0150ParamVo.setSpras("E");
				
				if(mandt == null) com0150ParamVo2.setMandt("");  	else com0150ParamVo2.setMandt(mandt);
				if(klart == null) com0150ParamVo2.setKlart("");  	else com0150ParamVo2.setKlart(klart);
				if(class1 == null) com0150ParamVo2.setClass1("");  	else com0150ParamVo2.setClass1(class1);
				if(atkla == null) com0150ParamVo2.setAtkla("");  	else com0150ParamVo2.setAtkla(atkla);
				if(atnam == null) com0150ParamVo2.setAtnam("");  	else com0150ParamVo2.setAtnam(atnam2);
				if(where == null) com0150ParamVo2.setWhere("");  	else com0150ParamVo2.setWhere(where);
				if ("ko".equals(lang) || "SHN_01".equals(class1) || "SHN_02".equals(class1)) com0150ParamVo2.setSpras("3");
				else com0150ParamVo2.setSpras("E");
			}

			
			List<Com0150> com0150List = null;
			List<Com0150> com0150List2 = null;
			
			List<Ses0031> list = Ses0031Dao.selectListTemplate(param);			// 견적사양조회
			List<Ses0031> ses0031List = Ses0031Dao.selectListZcobt302(param);	// 원가
			if(!"EN".equals(atnam2)) {
				com0150List = Com0150Dao.selectList(com0150ParamVo); 			// 지역
				com0150List2 = Com0150Dao.selectList(com0150ParamVo2); 		// 광역
			}
			
			// DATASET 초기화
			ds_template.deleteAll();
			ds_t302.deleteAll();
			
			// DATASET 복사
			CallSAPHdel.moveListToDsSub(ds_t302, Ses0031.class, ses0031List);
			if(!"EN".equals(atnam2)) {
				ds_zregn2.deleteAll();
				ds_zregn3.deleteAll();
				CallSAPHdel.moveListToDsSub(ds_zregn2, Com0150.class, com0150List);
				CallSAPHdel.moveListToDsSub(ds_zregn3, Com0150.class, com0150List2);
			}
			
			// 견적사양값이 없다면 다른 조회쿼리가 시행됨
			if (list.size() == 0 || param.getQtnum().length() == 0) {
				for (int a = 0; a < ds_detail.getRowCount(); a++) {
					
					if(DatasetUtility.getString(ds_detail, a, "ATNAM" ) == null) param.setPrh("");
					else param.setPrh(DatasetUtility.getString(ds_detail, a, "ATNAM" ));
					if(DatasetUtility.getString(ds_detail, a, "MCLASS" ) == null) {
						if(DatasetUtility.getString(ds_detail, a, "ZPRDGBN" ) == null) param.setZprdgbn("");
						else param.setZprdgbn(DatasetUtility.getString(ds_detail, a, "ZPRDGBN" ));
					} else param.setZprdgbn(DatasetUtility.getString(ds_detail, a, "MCLASS" ));
					
					
					if("en".equals(lang)) param.setSpras("E");
					else param.setSpras("3");
					
					if ("SHN_01".equals(param.getMclass()) || "SHN_02".equals(param.getMclass())) param.setSpras("3");
					
					list = Ses0031Dao.selectListKsml(param);
					
					for(int i = 0; i < list.size(); i++) {
						j = ds_template.appendRow(); 	// 행추가
						ds_template.setColumn(j, "FLAG"       , list.get(i).getFlag());
						ds_template.setColumn(j, "MANDT"   , mandt);
						ds_template.setColumn(j, "QTNUM"   , DatasetUtility.getString(ds_detail, a, "QTSNUM"));
						ds_template.setColumn(j, "QTSER"     , DatasetUtility.getString(ds_detail, a, "QTSER"));
						ds_template.setColumn(j, "QTSEQ"     , DatasetUtility.getString(ds_detail, a, "QTSEQ"));
						ds_template.setColumn(j, "PRSEQ"     , list.get(i).getPosnr());
						ds_template.setColumn(j, "MCLASS"   , list.get(i).getMclass());
						ds_template.setColumn(j, "ATKLA"     , list.get(i).getAtkla());
						ds_template.setColumn(j, "PRH"        , list.get(i).getPrh());
						ds_template.setColumn(j, "PRD"        , list.get(i).getPrd());
						ds_template.setColumn(j, "ATFOR"     , list.get(i).getAtfor());
						ds_template.setColumn(j, "PRHNAME", list.get(i).getPrhname());
						ds_template.setColumn(j, "CNT"       , list.get(i).getCnt());
						ds_template.setColumn(j, "PCNCD"    , list.get(i).getPcncd());
						j++;
					}
				}
				
			} else {
				// DATASET 복사
				CallSAPHdel.moveListToDsSub(ds_template, Ses0031.class, list);
			}

		} catch (Exception e) {
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			
			ds_error.setId("ds_error");  		// ERROR 
			resultVO.addDataset(ds_error);  	// ERROR
			
			return resultVO;
		}
					
		ds_template.setId("ds_template");
		ds_t302.setId("ds_t302");
		ds_error.setId("ds_error");
		if(!"EN".equals(atnam2)) {
			ds_zregn2.setId("ds_zregn2");
			ds_zregn3.setId("ds_zregn3");
		}
		
		resultVO.addDataset(ds_template);
		resultVO.addDataset(ds_t302);
		resultVO.addDataset(ds_error);
		if(!"EN".equals(atnam2)) {
			resultVO.addDataset(ds_zregn2);
			resultVO.addDataset(ds_zregn3);
		}
		
		return resultVO;
	}
	
	//=========================================================================================
    //  함수기능 	: 견적 사양 조회(신규)
    //  기능ID 	: 
    //  개선내역 	: mo 최초 생성시 가져오는 견적사양
    //  HISTORY : 2016.02.15 최초 코딩 hsi
    //=========================================================================================
	public MipResultVO searchAddTemplate(MipParameterVO paramVO) {

		MipResultVO resultVO = new MipResultVO();
		
		// OUTPUT DATASET DECLARE  
		Dataset ds_error		= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		Dataset ds_template = paramVO.getDataset("ds_template");
		Dataset ds_detail	= paramVO.getDataset("ds_detail");
		
		String lang 			= paramVO.getVariable("G_LANG");
		String mandt			= DatasetUtility.getString(ds_detail, 0, "MANDT");
		String qtnum			= DatasetUtility.getString(ds_detail, 0, "QTNUM");
		String qtser			= DatasetUtility.getString(ds_detail, 0, "QTSER");
		String qtseq			= DatasetUtility.getString(ds_detail, 0, "QTSEQ");
		String mclass			= DatasetUtility.getString(ds_detail, 0, "MCLASS");
		
		// 2020 브랜드 입력
		String brndcd           = DatasetUtility.getString(ds_detail, 0, "BRNDCD");
		String brndseq          = DatasetUtility.getString(ds_detail, 0, "BRNDSEQ");
		
		int j = 0;
		
		try {
			// vo생성
			Ses0031ParamVO param  = new Ses0031ParamVO();
			
			// 변수 세팅
			if(mandt == null) param.setMandt("");  	else param.setMandt(mandt);
			if(qtnum == null) param.setQtnum("");  	else param.setQtnum(qtnum);
			if(qtser == null) param.setQtser("0");  else param.setQtser(qtser);
			if(qtseq == null) param.setQtseq("0");  else param.setQtseq(qtseq);
			if(mclass == null) param.setMclass(""); else param.setMclass(mclass);
			if(lang == null) param.setLang("");   	else param.setLang(lang);

			// 2020 브랜드 입력
			if(brndcd == null) param.setBrndcd("NOBRND");  else if( "".equals(brndcd)) param.setBrndcd("NOBRND"); else param.setBrndcd(brndcd);
			if(brndseq == null) param.setBrndseq("000");  else if( "".equals(brndseq)) param.setBrndseq("000"); else param.setBrndseq(brndseq);
			
			
			List<Ses0031> list = Ses0031Dao.selectListAddTemplate(param);
			// DATASET 복사
			CallSAPHdel.moveListToDsSub(ds_template, Ses0031.class, list);
			
			/*
			if (list.size() == 0 || param.getQtnum().length() == 0) {
				for (int a = 0; a < ds_detail.getRowCount(); a++) {
					
					if(DatasetUtility.getString(ds_detail, a, "ATNAM" ) == null) param.setPrh("");
					else param.setPrh(DatasetUtility.getString(ds_detail, a, "ATNAM" ));
					if(DatasetUtility.getString(ds_detail, a, "MCLASS" ) == null) {
						if(DatasetUtility.getString(ds_detail, a, "ZPRDGBN" ) == null) param.setZprdgbn("");
						else param.setZprdgbn(DatasetUtility.getString(ds_detail, a, "ZPRDGBN" ));
					} else param.setZprdgbn(DatasetUtility.getString(ds_detail, a, "MCLASS" ));
					
					
					if("en".equals(lang)) param.setSpras("E");
					else param.setSpras("3");
					
					if ("SHN_01".equals(param.getMclass()) || "SHN_02".equals(param.getMclass())) param.setSpras("3");
					
					list = Ses0031Dao.selectListKsml(param);
					
					for(int i = 0; i < list.size(); i++) {
						j = ds_template.appendRow(); 	// 행추가
						ds_template.setColumn(j, "FLAG"       , list.get(i).getFlag());
						ds_template.setColumn(j, "MANDT"   , mandt);
						ds_template.setColumn(j, "QTNUM"   , DatasetUtility.getString(ds_detail, a, "QTSNUM"));
						ds_template.setColumn(j, "QTSER"     , DatasetUtility.getString(ds_detail, a, "QTSER"));
						ds_template.setColumn(j, "QTSEQ"     , DatasetUtility.getString(ds_detail, a, "QTSEQ"));
						ds_template.setColumn(j, "PRSEQ"     , list.get(i).getPosnr());
						ds_template.setColumn(j, "MCLASS"   , list.get(i).getMclass());
						ds_template.setColumn(j, "ATKLA"     , list.get(i).getAtkla());
						ds_template.setColumn(j, "PRH"        , list.get(i).getPrh());
						ds_template.setColumn(j, "PRD"        , list.get(i).getPrd());
						ds_template.setColumn(j, "ATFOR"     , list.get(i).getAtfor());
						ds_template.setColumn(j, "PRHNAME", list.get(i).getPrhname());
						ds_template.setColumn(j, "CNT"       , list.get(i).getCnt());
						ds_template.setColumn(j, "PCNCD"    , list.get(i).getPcncd());
						j++;
					}
				}
			} else {
				// DATASET 복사
				CallSAPHdel.moveListToDsSub(ds_template, Ses0031.class, list);
			}
				*/
		} catch (Exception e) {
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			
			ds_error.setId("ds_error");  		// ERROR 
			resultVO.addDataset(ds_error);  	// ERROR
			
			return resultVO;
		}
					
		ds_template.setId("ds_template");
		ds_error.setId("ds_error");
					
		resultVO.addDataset(ds_template);
		resultVO.addDataset(ds_error);
		
		return resultVO;
	}
	
	//=========================================================================================
	//  함수기능 	: 견적사양 복사를 위한 조회
	//  기능ID 	: UF005
	//  HISTORY : 2016.02.15 최초 코딩 hsi
	//=========================================================================================	
	public MipResultVO searchTemplatePop(MipParameterVO paramVO) {
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_resList	= paramVO.getDataset("ds_resList");
		Dataset ds_error	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		try {
			// vo생성
			Ses0031ParamVO param  = new Ses0031ParamVO();

			// 변수 세팅
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setQtnum(paramVO.getVariable("qtnum"));
			param.setQtser(paramVO.getVariable("qtser"));
			param.setQtseq(paramVO.getVariable("qtseq"));
			if("en".equals(paramVO.getVariable("G_LANG"))) param.setSpras("E");
			else param.setSpras("3");
			
			// 서비스호출
			List<Ses0031> list = Ses0031Dao.searchTemplatePop(param);
	
			// DATASET 복사
			CallSAPHdel.moveListToDsSub(ds_resList, Ses0031.class, list);
		} catch (Exception e) {
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			
			ds_error.setId("ds_error");  		// ERROR 
			resultVO.addDataset(ds_error);  	// ERROR
			
			return resultVO;
		}
		
		ds_resList.setId("ds_resList");
		
		resultVO.addDataset(ds_resList);
		
		return resultVO;
	}

	//=========================================================================================
	//  함수기능 	: 원가산출
	//  기능ID 	: UF007
	//  개선내역 	: 종속성 체크 로직 변경 sap 호출방식 및 리턴 처리방식 변경
	//  HISTORY : 2016.02.15 최초 코딩 hsi
	//=========================================================================================
	public MipResultVO searchCost(MipParameterVO paramVO, SqlSession session) throws RfcException, Exception {
		MipResultVO resultVO = new MipResultVO();
		
		Rfc rfc = null;

		Dataset dsCost     = paramVO.getDataset("ds_cost");
		Dataset dsChar     = paramVO.getDataset("ds_char");		// 화면상의 ds_template

		Dataset dsHeader   = paramVO.getDataset("ds_header");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		Dataset ds_log = new Dataset("ds_log");                  // ds_log 추가 20150423 김선호 
		ds_log.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);  
		ds_log.addColumn("LOGMSG", ColumnInfo.COLTYPE_STRING, 256);
				
		Dataset ds_300    = null; // UI로 return할 out dataset
		Dataset ds_202    = null; // UI로 return할 out dataset
		Dataset ds_302    = null; // UI로 return할 out dataset
		Dataset ds_204    = null; // UI로 return할 out dataset
		Dataset ds_304    = null; // UI로 return할 out dataset
		Dataset ds_check = null;
		
		Dataset ds_egis_price = null;   //E-GIS로 return할 out dataset
		// ---------------------------------------------------------------
		//	1. 제한 조건 체크
		// ---------------------------------------------------------------
		resultVO = restrictionCondi(paramVO, session);
		Dataset ds_log_rslt = resultVO.getDatasetList().getDataset("ds_log");
		Dataset ds_error_rslt = resultVO.getDatasetList().getDataset("ds_error");
		
		if ( ds_log_rslt != null && ds_log_rslt.getRowCount() > 0 ) {
			resultVO.addDataset(ds_log_rslt);
			return resultVO;
		}else if ( ds_error_rslt != null && ds_error_rslt.getRowCount() > 0 ) {
			resultVO.addDataset(ds_error_rslt);
			return resultVO;
		}
		
		try
		{
			// ---------------------------------------------------------------
			//	2. 원가 계산 
			// ---------------------------------------------------------------			
			// JCO 방식 SAP 호출 방식
			boolean grpCall = "TRUE".equals(paramVO.getVariable("grpCall"));
			rfc = ses0031NJS.callProjectDataSet(paramVO.getVariable("G_SYSID"), DatasetUtility.getString(dsCost, "DIV"), DatasetUtility.getString(dsCost, "GBN")
                    , DatasetUtility.getString(dsCost, "GJAHR"), DatasetUtility.getString(dsCost, "POPER")
                    , 0, "", paramVO.getVariable("ONE"), grpCall, dsChar, ds_check, null, null, null, null, null, null, null, null, null);
			
			ds_300 = rfc.getDatasetFromJcoTable("T_T300");
			ds_202 = rfc.getDatasetFromJcoTable("T_T202");
			ds_302 = rfc.getDatasetFromJcoTable("T_T302");
			ds_204 = rfc.getDatasetFromJcoTable("T_T204");
			ds_304 = rfc.getDatasetFromJcoTable("T_T304");
			ds_check = rfc.getDatasetFromJcoTable("T_CHEK");
			
			ds_202.setId("ds_t202");
			ds_204.setId("ds_t204");
			ds_300.setId("ds_t300");
			ds_302.setId("ds_t302");
			ds_304.setId("ds_t304");
			ds_check.setId("ds_check");
			
			if("4".equals(rfc.getDatasetFromJcoTable("E_TYPE")) ) {
				ds_error = rfc.getDatasetFromJcoTable("O_ETAB"); // 오류결과
				resultVO.addDataset(ds_error); 	// 오류결과내역
			}
			
			
			// 견적의 경우에만 처리 2012.12.18
			if ( DatasetUtility.getString(dsCost, "DIV").equals("3") )	{
				// service.saveCostUpdate(dsHeader, dsExchange, ds_302, paramVO, session);	// 환율기준정보 변경으로 제거 2013.02.20 &&&&&
				// 2013.03.13 원가산출(재산출) 시 부분원가 동일 여부 확인
	//						boolean bCheck = false;
	//						for (int i = 0; i < ds_check.getRowCount(); i++)		{
	//							if ( !"X".equals(DatasetUtility.getString(ds_check, i, "STATE")) )	{
	//								bCheck = true;	// 부분원가가 존재하는 경우
	//								break;
	//							}
	//						}
	
				//2013.03.20 부분원가 처리 여부 확인, 부분원가 처리대상만 존재하도록 처리
				boolean bCheck   = false;
				String  keyQtseq = "";
				String  tmpQtseq = "";
				//String  tmpState = "";
				//HashMap tmpMap = new HashMap();
				
				int iRow = 0;
				
				Dataset tmpCheck = new Dataset("tmpCheck");
				tmpCheck.addColumn("KEY",   ColumnInfo.COLTYPE_STRING, 10);
				
				for (int i = 0; i < ds_check.getRowCount(); i++)	{
					keyQtseq = DatasetUtility.getString(ds_check, i, "QTSEQ");
					if ( i == 0)	{
						//tmpMap.put(keyQtseq, keyQtseq);
						iRow = tmpCheck.appendRow();
						tmpCheck.setColumn(iRow, "KEY", keyQtseq);
					}else	{
						//tmpQtseq = (String) tmpMap.get(keyQtseq);
						tmpQtseq = keyQtseq;
						if (tmpQtseq == null || "".equals(tmpQtseq) )	{
							//tmpMap.put(keyQtseq, keyQtseq);
							iRow = tmpCheck.appendRow();
							tmpCheck.setColumn(iRow, "KEY", keyQtseq);
						}
					}
				}
	
				for (int ii = 0; ii < tmpCheck.getRowCount(); ii++ )	{
					tmpQtseq = DatasetUtility.getString(tmpCheck, ii, "KEY");
					bCheck = false;
	
					for (int i = 0; i < ds_check.getRowCount(); i++)	{
						if (tmpQtseq.equals(DatasetUtility.getString(ds_check, i, "QTSEQ")) )	{
							if ( !"X".equals(DatasetUtility.getString(ds_check, i, "STATE")) )		{
								bCheck = true;	// 부분원가가 존재하는 경우
								break;
							}
						}
					}
	
					if (!bCheck)	{	// 부분원가 재 처리 불필요건
						for (int i = 0; i < ds_check.getRowCount(); i++)	{
							if (tmpQtseq.equals(DatasetUtility.getString(ds_check, i, "QTSEQ")) )	{
								ds_check.deleteRow(i);
							}
						}
					}
				}

				resultVO = this.saveCostUpdate(dsHeader, ds_302, paramVO, session);
			}
	
			resultVO.addDataset(ds_check);
			
			//this.getSapJcoCostDataset(dsCost, ds_error, paramVO, resultVO, session);
		} catch (CommRfcException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (RuntimeException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), e.getMessage(), "Y", "Y"); // 환율의 경우에는 메시지 코드 임
		}catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		
		resultVO.addDataset(ds_202);
		resultVO.addDataset(ds_302);
		resultVO.addDataset(ds_log);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		
		return resultVO;
	}

	//=========================================================================================
	//  함수기능 	: 제한조건 체크 
	//  기능ID 	: UF008
	//  HISTORY : 2017.06.26 mj.Shin 
	//=========================================================================================	
	public MipResultVO restrictionCondi(MipParameterVO paramVO, SqlSession session) throws RfcException, Exception {
		MipResultVO resultVO = new MipResultVO();
		
		// INPUT DATASET GET
		Dataset dsChar     = paramVO.getDataset("ds_char");		// 화면상의 ds_template
		Dataset dsOrder = paramVO.getDataset("ds_order");		// 화면상의 ds_template
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		Dataset ds_log = new Dataset("ds_log");                  // ds_log 추가 20150423 김선호 
		ds_log.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);  
		ds_log.addColumn("LOGMSG", ColumnInfo.COLTYPE_STRING, 256);
		
		String item  = "";
		String[] arrChkQtseq = paramVO.getVariable("F_CHK_QTSEQ").trim().split(",");

		boolean found = false;
		Dataset dsQtseq = new Dataset();
		dsQtseq.addColumn("QTSEQ", ColumnInfo.COLTYPE_CHAR, 256);
		for(int i=0; i < dsChar.getRowCount(); i++) {
			found = false;
			for(int j=0; j < dsQtseq.getRowCount(); j++) {
				found = dsChar.getColumnAsString(i, "QTSEQ").equals(dsQtseq.getColumnAsString(j, "QTSEQ"));
				if(found) {
					break;
				}
			}
			if(!found) {
				dsQtseq.appendRow();
				dsQtseq.setColumn(dsQtseq.getRowCount()-1, "QTSEQ", dsChar.getColumn(i, "QTSEQ"));
			}
		}

		try {
			// 2013.01.02 원가산출의뢰전 사양 필수사양 확인
			dutyNService.createDao(session);

			String mandt = paramVO.getVariable("G_MANDT");				// MANDT
			String lang  = paramVO.getVariable("G_LANG");				// LANG
			String flag  = "Q"; 										// Q-견적, P-계약
			boolean bBool = false;
			
			DutyObj dutyObj = new DutyObj();
			
			for(int i=0; i < dsQtseq.getRowCount(); i++) {
				item = dsQtseq.getColumnAsString(i, "QTSEQ");
				
				
				
				if(!"".equals(arrChkQtseq[0])) {
					for(int j = 0; j < arrChkQtseq.length; j++) {
						
						if(item.equals(arrChkQtseq[j])) {
							dsOrder.setColumn(0, "orditem", arrChkQtseq[j]);
							bBool = dutyNService.genSpecCheck(mandt, flag, item, dsChar, ds_log, lang, dutyObj, dsOrder);
						}
					}
				} else {
					bBool = dutyNService.genSpecCheck(mandt, flag, item, dsChar, ds_log, lang, dutyObj, dsOrder);
				}
	
				if ( ds_log.getRowCount() > 0 ) {   // 종속성 체크후 log 있으면 return
					resultVO.addDataset(ds_log); 	// log 내역 
					
					return resultVO;
				}
			}
					
		} catch (Exception e) {
			ds_error.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);
			ds_error.addColumn("ERRMSG", ColumnInfo.COLTYPE_STRING, 256);
			ds_error.appendRow();
			ds_error.setColumn(0, "IDX", "9999");
			ds_error.setColumn(0, "ERRMSG", "[필수 입력 에러] 호기번호:" + item + ", " + e.getMessage());
			
			resultVO.addDataset(ds_error); 	// 오류결과내역
		}
		
		return resultVO;
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

	public List<Ses0031> searchSpec(Ses0031ParamVO param) {
		return Ses0031Dao.selectListSpec(param);
	}

	public List<Ses0031> searchZcobt302(Ses0031ParamVO param) {
		return Ses0031Dao.selectListZcobt302(param);
	}

	public List<Ses0031> searchZcobt309(Ses0031ParamVO param) {
		return Ses0031Dao.selectListZsdt1054D(param);
	}
	
	public List<Ses0031> searchBlockName(Ses0031ParamVO param) {
		return Ses0031Dao.selectListBlockName(param);
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

		// 2020 브랜드
		// 입력된 호기의 브랜드가 변경된 호기 데이터셋
		Dataset dsBrndDel = paramVO.getDataset("ds_brnd_del");
		
		// 견적번호 전역변수 사용
		String gQtnum = "";
		String gQtser = "";

		try {
			createDao(session);

			String flag              = "";
			String qtnum           = "";
			String qtser             = "";
			String qtdat             = "";
			String egisFlag          = "";

			Ses0031ParamVO param = new Ses0031ParamVO();
			
			for (int i=0;i< dsHeader.getRowCount(); i++) {

				flag = DatasetUtility.getString(dsHeader, i, "FLAG");

				if ( flag != null) {
					qtnum = DatasetUtility.getString(dsHeader, i, "QTNUM") == null ? "" : DatasetUtility.getString(dsHeader, i, "QTNUM");
					qtser = DatasetUtility.getString(dsHeader, i, "QTSER") == null ? "0" : DatasetUtility.getString(dsHeader, i, "QTSER");
					qtdat = DatasetUtility.getString(dsHeader, i, "QTDAT") == null ? "" : DatasetUtility.getString(dsHeader, i, "QTDAT");
					egisFlag = DatasetUtility.getString(dsHeader, i, "EGIS_FLAG") == null ? "" : DatasetUtility.getString(dsHeader, i, "EGIS_FLAG");

					String qtgbn = DatasetUtility.getString(dsHeader, i, "QTGBN") == null ? "" : DatasetUtility.getString(dsHeader, i, "QTGBN");
					String auart = DatasetUtility.getString(dsHeader, i, "AUART") == null ? "" : DatasetUtility.getString(dsHeader, i, "AUART");
					String sid = DatasetUtility.getString(dsHeader, i, "SID") == null ? "" : DatasetUtility.getString(dsHeader, i, "SID");
					
					if (qtgbn.equals("")) qtgbn = "A";	// 2013.02.05 변경(A : 실행가 -> Default)

					if (qtnum.equals(""))	{	// 2013.02.05 변경
						AutoNumberParamVO AutoNumberParam = new AutoNumberParamVO();

						Autoservice.createDao(session);

						AutoNumberParam.setMandt(paramVO.getVariable("G_MANDT")); 	// SAP CLIENT
						AutoNumberParam.setDeptFlag(auart);	// 기종
						AutoNumberParam.setSoQtFlag("2");		// 채번구분(0:사업계획, 1:수주계획 , 2:견적)

						List<AutoNumberVO> listVO = Autoservice.AutoQtNumber(AutoNumberParam);// 견적번호 채번 서비스 호출
						qtnum = listVO.get(0).getNumber().toString();
					}

					param.setMandt(paramVO.getVariable("G_MANDT"));
					param.setQtnum(qtnum);
				
					if (qtser.equals("0")) {
						List<Ses0031> list = searchQtser(param);	// 서비스CALL
						qtser = list.get(0).getQtser().toString();
					}

					// 견적번호 전역변수 입력 
					gQtnum = qtnum;
					gQtser = qtser;

					param.setQtser(qtser);
					param.setQtdat(qtdat);
					param.setSpart(DatasetUtility.getString(dsHeader, i, "SPART") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPART"));
					param.setQtgbn(qtgbn);
					param.setZbdtyp(DatasetUtility.getString(dsHeader, i, "ZBDTYP") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZBDTYP"));
					param.setZprgflg(DatasetUtility.getString(dsHeader, i, "ZPRGFLG") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZPRGFLG"));
					param.setVkbur(DatasetUtility.getString(dsHeader, i, "VKBUR") == null ? "" : DatasetUtility.getString(dsHeader, i, "VKBUR"));
					param.setVkgrp(DatasetUtility.getString(dsHeader, i, "VKGRP") == null ? "" : DatasetUtility.getString(dsHeader, i, "VKGRP"));
					param.setZkunnr(DatasetUtility.getString(dsHeader, i, "ZKUNNR") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZKUNNR"));
					param.setZagnt(DatasetUtility.getString(dsHeader, i, "ZAGNT") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZAGNT"));
					param.setKunnr(DatasetUtility.getString(dsHeader, i, "KUNNR") == null ? "" : DatasetUtility.getString(dsHeader, i, "KUNNR"));
					param.setZcst(DatasetUtility.getString(dsHeader, i, "ZCST") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZCST"));
					param.setZgnm(DatasetUtility.getString(dsHeader, i, "ZGNM") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZGNM"));
					param.setZdev(DatasetUtility.getString(dsHeader, i, "ZDEV") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZDEV"));
					param.setGsnam(DatasetUtility.getString(dsHeader, i, "GSNAM") == null ? "" : DatasetUtility.getString(dsHeader, i, "GSNAM"));
					param.setZregn(DatasetUtility.getString(dsHeader, i, "ZREGN") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZREGN"));
					param.setZpid(DatasetUtility.getString(dsHeader, i, "ZPID") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZPID"));
					param.setZtel(DatasetUtility.getString(dsHeader, i, "ZTEL") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZTEL"));
					param.setZaddr_zpost(DatasetUtility.getString(dsHeader, i, "ZADDR_ZPOST") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZADDR_ZPOST"));
					param.setZaddr_adr1(DatasetUtility.getString(dsHeader, i, "ZADDR_ADR1") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZADDR_ADR1"));
					param.setZaddr_adr2(DatasetUtility.getString(dsHeader, i, "ZADDR_ADR2") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZADDR_ADR2"));
					param.setSoable(DatasetUtility.getString(dsHeader, i, "SOABLE") == null ? "" : DatasetUtility.getString(dsHeader, i, "SOABLE"));
					param.setZestdat(DatasetUtility.getString(dsHeader, i, "ZESTDAT") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZESTDAT"));
					param.setDeldat(DatasetUtility.getString(dsHeader, i, "DELDAT") == null ? "" : DatasetUtility.getString(dsHeader, i, "DELDAT"));
					param.setZdvmts(DatasetUtility.getString(dsHeader, i, "ZDVMTS") == null ? "0" : DatasetUtility.getString(dsHeader, i, "ZDVMTS"));
					param.setZsum_znumber(DatasetUtility.getString(dsHeader, i, "ZSUM_ZNUMBER") == null ? "0" : DatasetUtility.getString(dsHeader, i, "ZSUM_ZNUMBER"));
					param.setZsum_zcost(DatasetUtility.getString(dsHeader, i, "ZSUM_ZCOST") == null ? "0.00" : DatasetUtility.getString(dsHeader, i, "ZSUM_ZCOST"));
					param.setZsum_zeam(DatasetUtility.getString(dsHeader, i, "ZSUM_ZEAM") == null ? "0.00" : DatasetUtility.getString(dsHeader, i, "ZSUM_ZEAM"));
					param.setZsum_shang(DatasetUtility.getString(dsHeader, i, "ZSUM_SHANG") == null ? "0" : DatasetUtility.getString(dsHeader, i, "ZSUM_SHANG"));
					param.setInco(DatasetUtility.getString(dsHeader, i, "INCO") == null ? "" : DatasetUtility.getString(dsHeader, i, "INCO"));
					param.setZicif_pprt(DatasetUtility.getString(dsHeader, i, "ZICIF_PPRT") == null ? "0" : DatasetUtility.getString(dsHeader, i, "ZICIF_PPRT"));
					param.setZicif_ppct(DatasetUtility.getString(dsHeader, i, "ZICIF_PPCT") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZICIF_PPCT"));
					param.setZicif_ppcd(DatasetUtility.getString(dsHeader, i, "ZICIF_PPCD") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZICIF_PPCD"));
					param.setZicif_1strt(DatasetUtility.getString(dsHeader, i, "ZICIF_1STRT") == null ? "0" : DatasetUtility.getString(dsHeader, i, "ZICIF_1STRT"));
					param.setZicif_1stct(DatasetUtility.getString(dsHeader, i, "ZICIF_1STCT") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZICIF_1STCT"));
					param.setZicif_1stcd(DatasetUtility.getString(dsHeader, i, "ZICIF_1STCD") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZICIF_1STCD"));
					param.setZicif_2strt(DatasetUtility.getString(dsHeader, i, "ZICIF_2STRT") == null ? "0" : DatasetUtility.getString(dsHeader, i, "ZICIF_2STRT"));
					param.setZicif_2stct(DatasetUtility.getString(dsHeader, i, "ZICIF_2STCT") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZICIF_2STCT"));
					param.setZicif_2stcd(DatasetUtility.getString(dsHeader, i, "ZICIF_2STCD") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZICIF_2STCD"));
					param.setZicif_rmrt(DatasetUtility.getString(dsHeader, i, "ZICIF_RMRT") == null ? "0" : DatasetUtility.getString(dsHeader, i, "ZICIF_RMRT"));
					param.setZicif_rmct(DatasetUtility.getString(dsHeader, i, "ZICIF_RMCT") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZICIF_RMCT"));
					param.setZicif_rmcd(DatasetUtility.getString(dsHeader, i, "ZICIF_RMCD") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZICIF_RMCD"));
					param.setSprmk1(DatasetUtility.getString(dsHeader, i, "SPRMK1") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPRMK1"));
					param.setSprmk2(DatasetUtility.getString(dsHeader, i, "SPRMK2") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPRMK2"));
					param.setSprmk3(DatasetUtility.getString(dsHeader, i, "SPRMK3") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPRMK3"));
					param.setSprmk4(DatasetUtility.getString(dsHeader, i, "SPRMK4") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPRMK4"));
					param.setSprmk5(DatasetUtility.getString(dsHeader, i, "SPRMK5") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPRMK5"));
					param.setSprmk6(DatasetUtility.getString(dsHeader, i, "SPRMK6") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPRMK6"));
					param.setSprmk7(DatasetUtility.getString(dsHeader, i, "SPRMK7") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPRMK7"));
					param.setSprmk8(DatasetUtility.getString(dsHeader, i, "SPRMK8") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPRMK8"));
					param.setSprmk9(DatasetUtility.getString(dsHeader, i, "SPRMK9") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPRMK9"));
					param.setSprmk10(DatasetUtility.getString(dsHeader, i, "SPRMK10") == null ? "" : DatasetUtility.getString(dsHeader, i, "SPRMK10"));
					param.setZsoflg(DatasetUtility.getString(dsHeader, i, "ZSOFLG") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZSOFLG"));
					param.setSonnr(DatasetUtility.getString(dsHeader, i, "SONNR") == null ? "" : DatasetUtility.getString(dsHeader, i, "SONNR"));
					param.setVbeln(DatasetUtility.getString(dsHeader, i, "VBELN") == null ? "" : DatasetUtility.getString(dsHeader, i, "VBELN"));
					param.setWaerk(DatasetUtility.getString(dsHeader, i, "WAERK") == null ? "" : DatasetUtility.getString(dsHeader, i, "WAERK"));
					param.setAuart(auart);
					param.setCo_ddvrtq(DatasetUtility.getString(dsHeader, i, "CO_DDVRTQ") == null ? "" : DatasetUtility.getString(dsHeader, i, "CO_DDVRTQ"));
					param.setCo_dephtq(DatasetUtility.getString(dsHeader, i, "CO_DEPHTQ") == null ? "" : DatasetUtility.getString(dsHeader, i, "CO_DEPHTQ"));
					param.setCo_dssq(DatasetUtility.getString(dsHeader, i, "CO_DSSQ") == null ? "" : DatasetUtility.getString(dsHeader, i, "CO_DSSQ"));
					param.setCo_bclcdtq(DatasetUtility.getString(dsHeader, i, "CO_BCLCDTQ") == null ? "" : DatasetUtility.getString(dsHeader, i, "CO_BCLCDTQ"));
					param.setCo_chpitq(DatasetUtility.getString(dsHeader, i, "CO_CHPITQ") == null ? "" : DatasetUtility.getString(dsHeader, i, "CO_CHPITQ"));
					param.setCo_aspc(DatasetUtility.getString(dsHeader, i, "CO_ASPC") == null ? "" : DatasetUtility.getString(dsHeader, i, "CO_ASPC"));
					param.setCo_dsv1(DatasetUtility.getString(dsHeader, i, "CO_DSV1") == null ? "" : DatasetUtility.getString(dsHeader, i, "CO_DSV1"));
					param.setCo_dsv1tq(DatasetUtility.getString(dsHeader, i, "CO_DSV1TQ") == null ? "" : DatasetUtility.getString(dsHeader, i, "CO_DSV1TQ"));
					param.setCo_dsv2(DatasetUtility.getString(dsHeader, i, "CO_DSV2") == null ? "" : DatasetUtility.getString(dsHeader, i, "CO_DSV2"));
					param.setCo_dsv2tq(DatasetUtility.getString(dsHeader, i, "CO_DSV2TQ") == null ? "" : DatasetUtility.getString(dsHeader, i, "CO_DSV2TQ"));
					param.setCuser(paramVO.getVariable("G_SAP_USER_PM_B") == null ? "" : paramVO.getVariable("G_SAP_USER_PM_B"));
					param.setUuser(param.getCuser());
					param.setZlzone(DatasetUtility.getString(dsHeader, i, "ZLZONE") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZLZONE"));
					param.setRegion(DatasetUtility.getString(dsHeader, i, "REGION") == null ? "" : DatasetUtility.getString(dsHeader, i, "REGION"));
					param.setZagntflg(DatasetUtility.getString(dsHeader, i, "ZAGNTFLG") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZAGNTFLG"));
					param.setInco2(DatasetUtility.getString(dsHeader, i, "INCO2") == null ? "" : DatasetUtility.getString(dsHeader, i, "INCO2"));
					param.setFa_byrgbn(DatasetUtility.getString(dsHeader, i, "FA_BYRGBN") == null ? "" : DatasetUtility.getString(dsHeader, i, "FA_BYRGBN"));
					param.setZdeli(DatasetUtility.getString(dsHeader, i, "ZDELI") == null ? "" : DatasetUtility.getString(dsHeader, i, "ZDELI"));
					param.setJgbn(DatasetUtility.getString(dsHeader, i, "JGBN") == null ? "" : DatasetUtility.getString(dsHeader, i, "JGBN"));
					param.setAqtnum(DatasetUtility.getString(dsHeader, i, "AQTNUM") == null ? "" : DatasetUtility.getString(dsHeader, i, "AQTNUM"));
					param.setOppid(DatasetUtility.getString(dsHeader, i, "OPPID") == null ? "" : DatasetUtility.getString(dsHeader, i, "OPPID"));
					param.setAutolp(DatasetUtility.getString(dsHeader, i, "AUTOLP") == null ? "" : DatasetUtility.getString(dsHeader, i, "AUTOLP"));
					param.setUch_duty(DatasetUtility.getString(dsHeader, i, "UCH_DUTY") == null ? "" : DatasetUtility.getString(dsHeader, i, "UCH_DUTY"));
					param.setFrcmfcdat(DatasetUtility.getString(dsHeader, i, "FRCMFCDAT") == null ? "" : DatasetUtility.getString(dsHeader, i, "FRCMFCDAT"));
					//E-GIS 수신시 EGIS_FLAG='X'이면 저장  20190329 BY LYK
					param.setEgisFlag(egisFlag);
					param.setEgisEstNo(DatasetUtility.getString(dsHeader, i, "EGIS_EST_NO") == null ? "" : DatasetUtility.getString(dsHeader, i, "EGIS_EST_NO"));
					param.setEgisEstSeq(DatasetUtility.getString(dsHeader, i, "EGIS_EST_SEQ") == null ? "0" : DatasetUtility.getString(dsHeader, i, "EGIS_EST_SEQ"));
					param.setPspidCo(DatasetUtility.getString(dsHeader, i, "PSPID_CO") == null ? "" : DatasetUtility.getString(dsHeader, i, "PSPID_CO"));
					param.setDestport(DatasetUtility.getString(dsHeader, i, "DESTPORT") == null ? "" : DatasetUtility.getString(dsHeader, i, "DESTPORT"));
					param.setDeptport(DatasetUtility.getString(dsHeader, i, "DEPTPORT") == null ? "" : DatasetUtility.getString(dsHeader, i, "DEPTPORT"));
					
					// 계약형태 추가 20200710 by 김은하
					param.setLifnrchk(DatasetUtility.getString(dsHeader, i, "LIFNRCHK") == null ? "" : DatasetUtility.getString(dsHeader, i, "LIFNRCHK"));
					
					param.setAchdt(DatasetUtility.getString(dsHeader, i, "ACHDT") == null ? "" : DatasetUtility.getString(dsHeader, i, "ACHDT"));
					
					ZSDT0167S zsdt0167s = new ZSDT0167S();
					zsdt0167s.createDao(session);

					if(flag.equals("I") || flag.equals("U")) {
						zsdt0167s.insert(paramVO.getVariable("G_MANDT"), qtnum, paramVO.getVariable("TQUOT"), paramVO.getVariable("G_USER_ID"));
						mergeHeader(param);
						mergeZsdt1091(param);    /* 해상운임비 - 견적번호 신규생성및 업데이트 20190226 by lyk*/	
						//2020.12 jss
						if("ZN01".equals(auart)|| "ZN02".equals(auart)){
							updateZsdt1154(paramVO.getVariable("G_MANDT"), sid);
						}
					}
					else if(flag.equals("D")) {
						deleteHeader(param);
						deleteZsdt1091(param);  /* 해상운임비 - 견적번호 삭제 20190226 by lyk*/
						zsdt0167s.delete(paramVO.getVariable("G_MANDT"), qtnum);
					}
					else if(flag.equals("A")) {   
						addHeader(param);
						/* 해상운임비 - 견적번호- 견적차수+1  20190226 by lyk*/
						addZsdt1091(param);
					}
					
					// 2020 브랜드 
					// 브랜드 변경 시 기종 브랜드 영업사양 데이터 삭제 처리
					if(dsBrndDel != null) {
						if(dsBrndDel.getRowCount()>0) {
							deleteBrndTemplate(paramVO, model, session, param, dsBrndDel);
						}
					}
					saveTemplate(paramVO, model, session, param, dsTemplate, gQtnum,  gQtser);
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
//				saveAddinkey(param);

				dsHeader.setColumn(i, "MANDT", paramVO.getVariable("G_MANDT"));
				dsHeader.setColumn(i, "QTNUM", qtnum);
				dsHeader.setColumn(i, "QTSER"  , qtser  );
			}   //end for
			
			
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
	
			mandt   = paramVO.getVariable("G_MANDT");
			qtnum   = DatasetUtility.getString(dsHeader, 0, "QTNUM");
			qtser   = DatasetUtility.getString(dsHeader, 0, "QTSER");
			zprgflg = "20";		// 견적생성
			qtser_flag = paramVO.getVariable("qtser_flag");
	
			param.setMandt(mandt);
			param.setQtnum(qtnum);
			param.setQtser(qtser);
			param.setZprgflg(zprgflg);
			param.setCuser(paramVO.getVariable("G_USER_ID"));
			param.setUuser(paramVO.getVariable("G_USER_ID"));

			// 원가삭제용 (삭제 감소 시)
			paramCost.setMandt(mandt);
			paramCost.setQtnum(qtnum);
			paramCost.setQtser(qtser);

			// 모의견적 파라미터 처리
			String smlysno = paramVO.getVariable("SMLYSNO");
			String smlqtnum = paramVO.getVariable("SMLQTNUM");
			String smlqtser = paramVO.getVariable("SMLQTSER");
			if(smlysno == null) {
				smlysno = "N";
			} else {
				if("".equals(smlysno)) {
					smlysno = "N";
				}
			}

			// 모의견적 추가, 삭제인 경우 만 처리
			ses0030ES.createDao(session);
			
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
				/* 해상운임비 - 견적번호- 견적차수+1  20190226 by lyk*/				
				addZsdt1091(param);
				addQtserZsdt1047(param);
				addZsdt1093(param);
				addQtserZsdt1048(param);
				
				//수주계획번호의 상태 변경
				param.setSorlt("20");	// 수주결과
				param.setQtser(listQtser.get(0).getQtser());	// 증가된 차수의 수주계획번호 상태 변경.
				updateSorlt(param);
				
				// 재 조회 처리를 위한 QTSER 처리
				dsHeader.setColumn(0, "QTSER", listQtser.get(0).getQtser());
				
				// 모의견적 추가, 삭제인 경우 만 처리
				if("Y".equals(smlysno)) {
					ses0030ES.insertZSDT1164(mandt, smlqtnum, smlqtser, qtnum, param.getMax_qtser(), paramVO.getVariable("G_USER_ID"));
				}
				
			}else if ("D".equals(qtser_flag))	{
				// 정보가 존재하지 않는 경우
				
				/*//---< 2019.08.21 자동도면 견적 삭제 Check 제외 처리 by mj.Shin Start 
				if (!validToDelete(mandt, qtnum) 
						&& !paramVO.getVariable("G_USER_ID").equals("2015935")) {	//20170811 영업과 협의할때까지 영업기술 성윤권 차장만 삭제처리 가능하도록 임시처리 by any2me
					throw new BizException("자동도면 견적은 삭제 불가능합니다.");
				}
				  //---> 2019.08.21 자동도면 견적 삭제 Check 제외 처리 by mj.Shin End 
				*/
				
				//---< 2017.03.16 견적 승인 요청 존재 여부 Check by mj.Shin Start   
				/*
				String zrqflg = "1";					//견적승인요청(국내대리점)
				List<Ses0031> listRstApp = searchRqstAppr(mandt, qtnum, qtser, zrqflg);
				if (listRstApp != null && listRstApp.size() >= 0)		{
					throw new BizException("CW10226");					
				}
				*/
				//---> 2017.03.16 견적 승인 요청 존재 여부 Check by mj.Shin End 
				
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
				/* 해상운임비 - 견적번호 삭제 20190226 by lyk*/
				deleteZsdt1091(param);   					
				deleteQtserZsdt1093(param);   					
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
				
				// 모의견적 추가, 삭제인 경우 만 처리
				if("Y".equals(smlysno)) {
					ses0030ES.deleteZsdt0711(mandt,smlqtnum, smlqtser);
					ses0030ES.deleteSmlZsdt1164(mandt, smlqtnum, smlqtser, qtnum, qtser);
				}
			}

			return dsHeader;
		}catch(Exception e)	{
			throw e;
		}
	}

	public Boolean validToDelete(String manst, String qtnum) {
		String autolp = Ses0030Dao.isAutolpOnMaxSeq(manst, qtnum);
		return autolp.equals("Y") ? false : true;
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

	public int updateZsdt1154(String sMandt, String sSid) {
		return Ses0031Dao.updateZsdt1154(sMandt, sSid);
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
			
			//상해단가 추가  - 신미정 2014.05.27 정동명CJ 요청			
			String zuams     = "";
			String zurate     = "";
			//E-GIS 송수신 저장 ZSDT1093  20190329 BY LYK
			String egisEstNo    = "";
			String egisEstSeq    = "";
			String egisEstSerno    = "";
			String egisSpecType    = "";

			// 브랜드 입력화
			String brndcd    = "";
			String brndseq    = "";
			
			// 프로젝트원가 추가 2021.07.06
			String zuamp  = "";
			String zcostp = "";
			
			//승강기번호 추가 : 2021.09 jss
			String sid = "";
			
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
					
					// 브랜드입력화
					brndcd    = DatasetUtility.getString(dsDetail, i, "BRNDCD"     );					
					brndseq    = DatasetUtility.getString(dsDetail, i, "BRNDSEQ"     );			
					
					// 프로젝트원가 추가 2021.07.06
					zuamp    = DatasetUtility.getString(dsDetail, i, "ZUAMP"     );					
					zcostp    = DatasetUtility.getString(dsDetail, i, "ZCOSTP"     );		

					//승강기번호 추가 : 2021.09 jss
					sid = DatasetUtility.getString(dsDetail, i, "SID");
					
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
					if (egisSpecType  	== null) egisSpecType     = "";

					// 브랜드 입력화
					if (brndcd  	== null) brndcd     = "";
					if (brndseq  	== null) {
						brndseq     = "000";
					} else {
						if("".equals(brndseq) || "0".equals(brndseq)) {
							brndseq     = "000";
						}
					}

					// 프로젝트원가 추가 2021.07.06
					if(zuamp == null) zuamp = "0";
					if(zcostp == null) zcostp = "0";
					
					//승강기번호 추가 : 2021.09 jss
					if(sid == null) sid = "";
					
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

					// 브랜드 입력화
					param.setBrndcd(brndcd);
					param.setBrndseq(brndseq);
					
					// 프로젝트원가 추가 2021.07.06
					param.setZuamp(zuamp);
					param.setZcostp(zcostp);
					
					if (flag.equals("I") || flag.equals("U")) {
						mergeDetail(param);
						mergeZsdt1093(param);
						if (flag.equals("I")) {
							param.setSorlt("20");	// 수주결과
							updateSorlt(param);
						}
						//2021.09 jss : 수요정보 상태변경 (견적등록)
						if(!"".equals(sonnr) && !"".equals(sid)){		
							Ses0031Dao.updateZsdt1154Detail(param);
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
						{	
							deleteZsdt0713(param);
							deleteZsdt0712(param);
						}
					}else if (flag.equals("A")) {
						param.setSorlt("20");	// 수주결과
						updateSorlt(param);
						addDetail(param);
						addZsdt1093(param);
					}
					
					if("I".equals(flag) || "U".equals(flag) || "A".equals(flag)) {
						Ses0031Dao.updateZprgflg(param);
					}
					// updateHeader(param); 최종 처리하는 부분으로 변경 2013.03.19
				}
			}

			updateHeader(param);	// 2013.03.19
			mergeZsdt1091(param);    /* 해상운임비 - 견적번호 신규생성및 업데이트 20190226 by lyk*/	
			//SS COST header update 2014.06.28 ksk
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
	
	public int updateDetailEgis(Ses0031ParamVO param) {
		return Ses0031Dao.updateDetailEgis(param);
	}	

	// 2020 브랜드
	// 호기별 브래드 변경시 영업사양 특성 데이터를 삭제 처리한다.
	public void deleteBrndTemplate(MipParameterVO paramVO, Model model, SqlSession session, Ses0031ParamVO param, Dataset dsBrndDel) throws Exception{

		try {
			createDao(session);  // DAO생성

			String mandt = "";
			String qtnum = "";
			String qtser   = "";
			String qtseq  = "";
			String prseq  = "";
			
			Ses0031ParamVO input = new  Ses0031ParamVO();
			for (int i=0;i< dsBrndDel.getRowCount(); i++) {
				mandt = DatasetUtility.getString(dsBrndDel, i, "MANDT" );
				qtnum = DatasetUtility.getString(dsBrndDel, i, "QTNUM");
				qtser   = DatasetUtility.getString(dsBrndDel, i, "QTSER"  );
				qtseq  = DatasetUtility.getString(dsBrndDel, i, "QTSEQ"  );
				input.setMandt(mandt );
				input.setQtnum(qtnum);
				input.setQtser(qtser);
				input.setQtseq(qtseq);
				// 등록된 브랜드 영업사양특성 삭제 처리
				if(qtnum != null && qtser != null && qtseq != null) {
					deleteTemplate(input);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Dataset saveTemplate(MipParameterVO paramVO, Model model, SqlSession session, Ses0031ParamVO param, Dataset dsTemplate, String gQtnum, String gQtser) throws Exception{

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
			// 2020 브랜드 
			// 변경여부 체크하여 변경상태 값을 처리한다.
			HashMap<String, Integer> zprgMap = new HashMap<String, Integer>();

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

					if(flag.equals("I") || flag.equals("U")) {
						mergeTemplate(param);
					}
					else if (flag.equals("D")) {
						deleteTemplate(param);	// MO삭제 시 동시 처리되어 향후 불필요 2013.01.14
					}
					else if (flag.equals("A")) {
						addTemplate(param); 
						break;
					}
					
					if("I".equals(flag) || "U".equals(flag) || "A".equals(flag)) {
						// 변경여부 체크를 위한 해쉬맵 정보 처리
						zprgMap.put(qtseq, i);
//						if(prh.equals("CO_ELQTY") || prh.equals("CO_LAND1")) {
						if(prh.equals("CO_ELQTY")) {
							Ses0031Dao.updateCoElqty(param);
						}
					}
				}
			}

			
			// 2020 브랜드
			// 견적번호, 견적차수 전역변수를 사용해서 업데이트 처리를 수행한다.
			Ses0031ParamVO input = new  Ses0031ParamVO();
			Iterator<String> keys = zprgMap.keySet().iterator();
			while( keys.hasNext() ) {
				String key = keys.next();
				input.setMandt(mandt );
				input.setQtnum(gQtnum);
				input.setQtser(gQtser);
				input.setQtseq(key);
				Ses0031Dao.updateZprgflg(input);
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
	public MipResultVO saveCostUpdate(Dataset dsHeader, Dataset dsZcobt302, MipParameterVO paramVO, SqlSession session) throws RuntimeException, Exception{
		MipResultVO resultVO = new MipResultVO();
			
		String mandt = paramVO.getVariable("G_MANDT");
		String uuser = paramVO.getVariable("G_USER_NAME");

		Ses0031ParamVO paramH = new Ses0031ParamVO();
		
		ExchangeS.createDao(session);					// DAO생성
		
		try {
			// 견적서 및 견적상세의 단가, 원가, 시행율 초기화 처리
			paramH.setMandt(paramVO.getVariable("G_MANDT"));
			paramH.setQtnum(DatasetUtility.getString(dsHeader, 0, "QTNUM"));
			paramH.setQtser(DatasetUtility.getString(dsHeader, 0, "QTSER"));
			if(DatasetUtility.getString(dsZcobt302, 0, "VBELN") == null) paramH.setQtseq("0");		
			else paramH.setQtseq(DatasetUtility.getString(dsZcobt302, 0, "QTSEQ"));
			paramH.setZuam("0");
			paramH.setZcost("0");
			paramH.setShang("0");
			// 프로젝트 원가 초기화 처리 21.07.01
			paramH.setZuamp("0");
			paramH.setZcostp("0");

			if(DatasetUtility.getString(dsHeader, 0, "PSPID_CO") == null) paramH.setPspidCo("");		
			else paramH.setPspidCo(DatasetUtility.getString(dsHeader, 0, "PSPID_CO"));
			if(DatasetUtility.getString(dsHeader, 0, "DEPTPORT") == null) paramH.setDeptport("");		
			else paramH.setDeptport(DatasetUtility.getString(dsHeader, 0, "DEPTPORT"));
			if(DatasetUtility.getString(dsHeader, 0, "DESTPORT") == null) paramH.setDestport("");		
			else paramH.setDestport(DatasetUtility.getString(dsHeader, 0, "DESTPORT"));
			if(DatasetUtility.getString(dsHeader, 0, "EGIS_FLAG") == null) paramH.setEgisFlag("");		
			else paramH.setEgisFlag(DatasetUtility.getString(dsHeader, 0, "EGIS_FLAG"));
			if(DatasetUtility.getString(dsHeader, 0, "EGIS_EST_NO") == null) paramH.setEgisEstNo("");		
			else paramH.setEgisEstNo(DatasetUtility.getString(dsHeader, 0, "EGIS_EST_NO"));
			if(DatasetUtility.getString(dsHeader, 0, "EGIS_EST_SEQ") == null) paramH.setEgisEstSeq("0");		
			else paramH.setEgisEstSeq(DatasetUtility.getString(dsHeader, 0, "EGIS_EST_SEQ"));

			if(!"Y".equals(paramVO.getVariable("ONE"))) {
				updateInitCostZSDT1046(paramH);
				updateInitCostZSDT1047(paramH);		// SP는 단가 입력된 정보 유지 필요.(MATNR != "Y-1000")
			}
			
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

			String waerk = DatasetUtility.getString(dsHeader,   0, "WAERK");

			String eur   = exchangeVO.getKrweur();
			String jpy   = exchangeVO.getKrwjpy();
			String usd   = exchangeVO.getKrwusd();
	
			if (waerk == null) waerk = "KRW";
			if (eur   == null) eur   = "0";
			if (jpy   == null) jpy   = "0";
			if (usd   == null) usd   = "0";
			
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
								// 2013.03.20 부?원가 존재여부 확인(MO별로 확인)
								sum += DatasetUtility.getDbl(dsZcobt302, i, dsZcobt302.getColumnID(c), 0) * 100;
							}
						}
					}
				}

				// RRC처리 후 및 ZCOBT302에 처리된 원가는 원화롤 저장되어 있음. (단위 항목에서 *100 처리) 2013.03.08
				// sum *= 100;

// -----< 2018.03.30 환율 계산  by mj.Shin 		Start

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
				
				double ratio = 1;
				BigDecimal Mratio = new BigDecimal(0);
				
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
									// 2013.03.20 부?원가 존재여부 확인(MO별로 확인)
									sum += DatasetUtility.getDbl(dsZcobt302, i, dsZcobt302.getColumnID(c), 0) * 100;
								}
							}
						}
					}

					// RRC처리 후 및 ZCOBT302에 처리된 원가는 원화롤 저장되어 있음. (단위 항목에서 *100 처리) 2013.03.08
					// sum *= 100;				
					String waerk = DatasetUtility.getString(dsHeader, 0, "WAERK");
					String auart = DatasetUtility.getString(dsHeader, 0, "AUART");
					String exchangeR = null;
					
					if (waerk != null && !"KRW".equals(waerk) && "ZE".equals( auart.substring(0, 2))) {
							exchangeR = ExchangeS.getExchangeRate(mandt, "Q", DateTime.getDateString(), waerk, "KRW");		
							if (Double.parseDouble(exchangeR) > 0 ) {
								sum = sum / Double.parseDouble(exchangeR);
							}
							else {
								String error = "CE10007";
								throw new RequireException(error);
							}
					}

					zuam = new BigDecimal(sum);
					zuam = zuam.setScale(2, RoundingMode.DOWN);
					
					//프로젝트원가 산출 로직 추가 21.07.01
					String[]  zuampFildId = {
							 "PRDM01"
							,"PRDM02"
							,"PRDM03"
							,"PRDM04"
							,"PRDM05"
							,"PRDL01"
							,"PRDL02"
							,"PRDL03"
							,"PRDL04"
							,"PRDL05"
							,"PRDE01"
							,"PRDE02"
							,"PRDE03"
							,"PRDE04"
							,"PRDE05"
							,"PRDE06"
							,"PRDE07"
							,"PRDE08"
							,"PRDE09"
							,"PRDE10"
							,"PRDE11"
							,"PRDE12"
							,"PRDE13"
							,"PRDE14"
							,"PRDE15"
							,"PRDI01"
							,"PRDI04"
							,"EQMM01"
							,"EQMM02"
							,"EQMM03"
							,"EQMM04"
							,"EQMM05"
							,"EQML01"
							,"EQML02"
							,"EQML03"
							,"EQML04"
							,"EQML05"
							,"EQME01"
							,"EQME02"
							,"EQME03"
							,"EQME04"
							,"EQME05"
							,"EQME06"
							,"EQME07"
							,"EQME08"
							,"EQME09"
							,"EQME10"
							,"EQME11"
							,"EQME12"
							,"EQME13"
							,"EQME14"
							,"EQME15"
							,"EQME51"
							,"EQME52"
							,"EQME53"
							,"EQME54"
							,"EQME55"
							,"EQME56"
							,"EQME57"
							,"EQME58"
							,"EQME59"
							,"EQME60"
							,"EQME61"
							,"EQME62"
							,"EQME63"
							,"EQME64"
							,"EQME65"
							,"EQME66"
							,"EQME67"
							,"EQME68"
							,"EQME69"
							,"EQME70"
							,"EQME71"
							,"EQME72"
							,"EQME73"
							,"EQME74"
							,"EQME75"
							,"EQME76"
							,"EQME77"
							,"EQME78"
							,"EQME79"
							,"EQME80"
							,"EQME81"
							,"EQME82"
							,"EQME83"
							,"EQME84"
							,"EQME85"
							,"EQME86"
							,"EQME87"
							,"EQME88"
							,"EQME89"
							,"EQME90"
							,"EQME91"
							,"EQME92"
							,"EQME93"
							,"EQME94"
							,"EQME95"
							,"EQME96"
							,"EQME97"
							,"EQME98"
							,"EQME99"
							,"EQME10"
							,"EQME10"
							,"EQME10"
							,"EQME10"
							,"EQME10"
							,"EQME10"
							,"EQME10"
							,"EQME10"
							,"EQME10"
							,"EQME10"
							,"EQME11"
							,"EQME11"
							,"EQME11"
							,"EQME11"
							,"EQME11"
							,"EQME11"
							,"EQME11"
							,"EQME11"
							,"EQME11"
							,"EQME11"
							,"EQME12"
							,"EQMI01"
							,"EQMI03"
							,"EQMO01"
							,"EQMO02"
							,"EQMO03"
							,"EQMO04"
							,"EQMO05"
							,"EQMO06"
							,"EQMO07"
							,"EQMO08"
							,"EQMO09"
							,"EQMO10"
							,"EQMO11"
							,"EQMO12"
							,"EQMO13"
							,"EQMO14"
							,"EQMO15"
							,"EQMO16"
							,"EQMO17"
							,"EQMO18"
							,"EQMO19"
							,"EQMO20"
							,"EQMO21"
							,"EQMO22"
							,"EQMO23"
							,"EQMO24"
							,"EQMO25"
							,"EQMO26"
							,"EQMO27"
							,"EQMO28"
							,"EQMO29"
							,"EQMO30"
							,"EQMO31"
							,"EQMO32"
							,"EQMO33"
							,"EQMO34"
							,"EQMO35"
							,"EQMO36"
							,"EQMO37"
							,"EQMO38"
							,"EQMO39"
							,"EQMO40"
							,"CTSM05"
							,"CTSM06"
							,"CTSM07"
							,"CTSM08"
							,"CTSM09"
							,"CTSM10"
							,"CTSM11"
							,"CTSM12"
							,"CTSM13"
							,"CTSM14"
							,"CTSM15"
							,"CTSM16"
							,"CTSM17"
							,"CTSM18"
							,"CTSL01"
							,"CTSL02"
							,"CTSL03"
							,"CTSL04"
							,"CTSL05"
							,"CTSE01"
							,"CTSE02"
							,"CTSE03"
							,"CTSE04"
							,"CTSE05"
							,"CTSI03"
							,"ADDT01"
							,"FDSP01"
							,"FDSP02"
							,"FDSP03"
							,"FDSP04"
							,"FDSP05"
							,"FDSP06"
							,"FDSP07"
							,"FDSP08"
							,"FDSP09"
							,"FDSP10"
							,"FDSP11"
					};
					double zuampSum = 0;
					BigDecimal zuamp = new BigDecimal(0);
					for( int zi = 0; zi < dsZcobt302.getRowCount(); zi++ ) 	{
						for(int zj=0; zj < zuampFildId.length; zj++) {
							zuampSum += DatasetUtility.getDbl(dsZcobt302, zi, zuampFildId[zj], 0) * 100;
						}
					}

					// 프로젝트원가 환율처리
					if (waerk != null && !"KRW".equals(waerk) && "ZE".equals( auart.substring(0, 2))) {
						exchangeR = ExchangeS.getExchangeRate(mandt, "Q", DateTime.getDateString(), waerk, "KRW");		
						if (Double.parseDouble(exchangeR) > 0 ) {
							zuampSum = zuampSum / Double.parseDouble(exchangeR);
						}
						else {
							String error = "CE10007";
							throw new RequireException(error);
						}
				    }

					zuamp = new BigDecimal(zuampSum);
					// 2자리 반올림 처리
					zuamp = zuamp.setScale(2, RoundingMode.UP);

					//E-GIS 요청일때 원가에 마진율적용 20190402 by lyk
					if("X".equals(DatasetUtility.getString(dsHeader, 0, "EGIS_FLAG"))) {						
						Ses0031ParamVO ratioVO = new Ses0031ParamVO();
						ratioVO = Ses0031Dao.selectRatio(paramH);
						if(ratioVO != null){
							ratio = Double.parseDouble(ratioVO.getRatio());
							ratio = ((ratio/100) +1);   //마진율 
						} else {
							ratio = 1;   				//마진율
						}
					}
					//E-GIS 요청일때 단가에 마진율적용 20190402 by lyk			

					Mratio = new BigDecimal(ratio);
					Mratio = Mratio.setScale(4, RoundingMode.UP);

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
					param.setRatio(Mratio.toString());
					// 표준원가 처리 추가 21.07.01
					param.setZuamp(zuamp.toString());

					if("Y".equals(paramVO.getVariable("ONE"))) {
						Dataset dsCostOne = new Dataset("dsCostOne");                  // ds_log 추가 20150423 김선호 
						dsCostOne.addColumn("COST", ColumnInfo.COLTYPE_STRING, 256);  
						dsCostOne.addColumn("COSTP", ColumnInfo.COLTYPE_STRING, 256);  
						dsCostOne.setId("ds_costOne");
						
						dsCostOne.insertRow(0);
						dsCostOne.setColumn(0, "COST", zuam.toString());
						dsCostOne.setColumn(0, "COSTP", zuamp.toString());
						resultVO.addDataset(dsCostOne);
					}

					if("X".equals(DatasetUtility.getString(dsHeader, 0, "EGIS_FLAG"))) {
						if(!"Y".equals(paramVO.getVariable("ONE"))) {
//							/* 원가 + 적하보험료 (20190603 BY LYK) */							
//							BigDecimal eIns = ses0031NJS.callCostIns(paramVO, param);
//							BigDecimal cost= new BigDecimal(param.getZuam());
//							cost = cost.add(eIns);
//							param.setZuam(cost.toString());
//							/* 원가 + 적하보험료 (20190603 BY LYK) */
							updateDetailEgis(param);
						}
					} else {
						if(!"Y".equals(paramVO.getVariable("ONE"))) {
							updateDetail(param);						
						}
					}
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
			//E-GIS 요청일때 단가에 마진율적용 20190403 by lyk
			if("X".equals(DatasetUtility.getString(dsHeader, 0, "EGIS_FLAG"))) {						
				Dataset dsEgisPrice = new Dataset("ds_egis_price");
				dsEgisPrice.addColumn("QTNUM", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisPrice.addColumn("QTSEQ", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisPrice.addColumn("QTSER", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisPrice.addColumn("ZUAM", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisPrice.addColumn("ZCOST", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisPrice.addColumn("ZEAM", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisPrice.setId("ds_egis_price");
				
				Dataset dsEgisBlock = new Dataset("ds_egis_block");
				dsEgisBlock.addColumn("MANDT", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.addColumn("QTNUM", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.addColumn("QTSEQ", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.addColumn("QTSER", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.addColumn("BLSEQ", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.addColumn("BDMNG", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.addColumn("SALES", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.addColumn("MESSG", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.addColumn("ZCTOTAL", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.addColumn("BLOCKNAME", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.addColumn("MAKTX", ColumnInfo.COLTYPE_STRING, 256);  
				dsEgisBlock.setId("ds_egis_block");
				
				List<Ses0031> list = this.searchBlockName(paramH);
				for (int i = 0; i < list.size(); i++) {

					int rows = dsEgisBlock.appendRow();
					dsEgisBlock.setColumn(rows, "MANDT", 		list.get(i).getMandt());
					dsEgisBlock.setColumn(rows, "QTNUM", 		list.get(i).getQtnum());
					dsEgisBlock.setColumn(rows, "QTSEQ", 		list.get(i).getQtseq());
					dsEgisBlock.setColumn(rows, "QTSER", 		list.get(i).getQtser());
					dsEgisBlock.setColumn(rows, "BLSEQ", 		list.get(i).getBlseq());
					dsEgisBlock.setColumn(rows, "BDMNG", 		list.get(i).getBdmng());
					dsEgisBlock.setColumn(rows, "SALES", 		list.get(i).getSales());
					dsEgisBlock.setColumn(rows, "MESSG", 		list.get(i).getMessg());
					dsEgisBlock.setColumn(rows, "ZCTOTAL", 	list.get(i).getZctotal());
					dsEgisBlock.setColumn(rows, "BLOCKNAME", 	list.get(i).getBlockname());
					dsEgisBlock.setColumn(rows, "MAKTX", 		list.get(i).getMaktx());
				}

				resultVO.addDataset(dsEgisBlock);

				List<Ses0030> egisPrice = Ses0031Dao.selectEgisPrice(paramH);
				for (int i = 0; i < egisPrice.size(); i++) {
					int rows = dsEgisPrice.appendRow();
					dsEgisPrice.setColumn(rows, "QTNUM", egisPrice.get(i).getQtnum());
					dsEgisPrice.setColumn(rows, "QTSEQ", egisPrice.get(i).getQtseq());
					dsEgisPrice.setColumn(rows, "QTSER", egisPrice.get(i).getQtser());
					dsEgisPrice.setColumn(rows, "ZUAM",  egisPrice.get(i).getZuam());
					dsEgisPrice.setColumn(rows, "ZCOST", egisPrice.get(i).getZcost());
					dsEgisPrice.setColumn(rows, "ZEAM",  egisPrice.get(i).getZeam());
				}
				
				resultVO.addDataset(dsEgisPrice);				
			}
			//E-GIS 요청일때 단가에 마진율적용 20190403 by lyk
			
			// X의 경우 동일, 이외에는 상이함
			// 최종 처리후 삭제
			//if (bCheck)	{
			//	deleteZsdt1054(paramVO, session);
			//}
		} catch( Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return resultVO;
	}

	// 2012.12.24 Control에서 Service로 이동
/*	public void saveCostUpdate(Dataset dsHeader, Dataset dsExchange, Dataset dsZcobt302, MipParameterVO paramVO, SqlSession session) throws Exception{
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
*/
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

			if (waerk != null && !"KRW".equals(waerk) && auart != null && "ZE".equals(auart.substring(0, 2)) ) {
				exchangeR = ExchangeS.getExchangeRate(mandt, f_flag, DateTime.getDateString(), waerk, "KRW");		
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
										
					if ( Double.parseDouble(exchangeR) > 0 && auart != null && "ZE".equals(auart.substring(0, 2)) )	{
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

	public Ses0031 searchNewExchange(String sMandt, String sDate) {
		return Ses0031Dao.selectNewExchange(sMandt, sDate);
	}

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
	
	public int updateQtvadt(Ses0031ParamVO param) {
		return Ses0031Dao.updateQtvadt(param);
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
	
	public List<Ses0031> searchAcapa(Ses0031ParamVO param) {
		return Ses0031Dao.selectListAcapa(param);
	}
	
	public List<Ses0031> searchClass(Ses0031ParamVO param) {
		return Ses0031Dao.selectListClass(param);
	}
	
	public List<Ses0031> searchRqstAppr(String sMandt, String sQtnum, String sQtser, String sZrqflg) {
		return Ses0031Dao.selectListRqsAppr(sMandt, sQtnum, sQtser, sZrqflg);
	}
	
//	public void saveAddinkey(Ses0031ParamVO param) throws Exception{
//		
//		String lhCrudFlag = "";
//		try {
//		
//			lhCrudFlag = getLhCrudFlag(param.getMandt(), param.getQtnum(), param.getQtser()); 	
//			
//			/* 해상운임비 특성코드값 합계관리 ZSDT1091 BY LYK 20190222*/
//			if ("X".equals(lhCrudFlag))			{	// Create_ZSDT1091
//				Ses0031Dao.updateZsdt1091(param);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//		
//	}			
	
	public int updateZsdt1091(Ses0031ParamVO param) {
		return 	Ses0031Dao.updateZsdt1091(param);
	}
	
	public int updateZsdt1091Lh(Ses0031ParamVO param) {
		return 	Ses0031Dao.updateZsdt1091Lh(param);
	}

//	public String getLhCrudFlag(String mandt, String qtnum, String qtser) {
//		return Ses0031Dao.selectLhCrudFlag(mandt, qtnum, qtser);
//	}
	
	public int deleteZsdt1091(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1091(param);
	}	

	public int deleteZsdt1093(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1093(param);
	}

	public int deleteQtserZsdt1093(Ses0031ParamVO param) {
		return Ses0031Dao.deleteQtserZsdt1093(param);
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

	public Dataset saveLog(MipParameterVO paramVO, Model model, SqlSession session) throws Exception {

		Dataset dsCallLog   = paramVO.getDataset("ds_call_log");

		try {
			
			createDao(session);

			Ses0031ParamVO param = new Ses0031ParamVO();
			
			for (int i=0;i< dsCallLog.getRowCount(); i++) {
				
				param.setMandt(paramVO.getVariable("G_MANDT"));
				param.setQtnum(DatasetUtility.getString(dsCallLog, i, "QTNUM") == null ? "" : DatasetUtility.getString(dsCallLog, i, "QTNUM"));
				param.setQtser(DatasetUtility.getString(dsCallLog, i, "QTSER") == null ? "" : DatasetUtility.getString(dsCallLog, i, "QTSER"));
				param.setFpath(DatasetUtility.getString(dsCallLog, i, "FPATH") == null ? "" : DatasetUtility.getString(dsCallLog, i, "FPATH"));
				param.setFname(DatasetUtility.getString(dsCallLog, i, "FNAME") == null ? "" : DatasetUtility.getString(dsCallLog, i, "FNAME"));
				param.setCuser(paramVO.getVariable("G_USER_ID"));
				
				Ses0031Dao.InsertLog(param);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return dsCallLog;
	}
	

	//=========================================================================================
	//  함수기능 	: 제한조건 체크 
	//  개선내역 	: 제한조건 제외하고 제한조건 실행
	//  HISTORY : 2020.07.09 박수근
	//=========================================================================================	
	public MipResultVO oneRestrictionCondi(MipParameterVO paramVO, SqlSession session) throws Exception {
		MipResultVO resultVO = new MipResultVO();

		// INPUT DATASET GET
		Dataset dsChar     = paramVO.getDataset("ds_char");		// 화면상의 ds_template
		Dataset dsOrder = paramVO.getDataset("ds_order");		// 화면상의 ds_template
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		Dataset ds_log = new Dataset("ds_log");                  // ds_log 추가 20150423 김선호 
		ds_log.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);  
		ds_log.addColumn("LOGMSG", ColumnInfo.COLTYPE_STRING, 256);
		// 2020 브랜드
		ds_log.addColumn("PRCSYS", ColumnInfo.COLTYPE_STRING, 256);
		ds_log.addColumn("HOGI", ColumnInfo.COLTYPE_STRING, 256);
		ds_log.addColumn("ATKLA", ColumnInfo.COLTYPE_STRING, 256);
		ds_log.addColumn("PRHNAME", ColumnInfo.COLTYPE_STRING, 256);
		String item = paramVO.getVariable("F_CHK_QTSEQ");

		try {
			// 2013.01.02 원가산출의뢰전 사양 필수사양 확인
			dutyNService.createDao(session);
			String mandt = paramVO.getVariable("G_MANDT");				// MANDT
			String lang  = paramVO.getVariable("G_LANG");				// LANG
			String flag  = "Q"; 										// Q-견적, P-계약
			DutyObj dutyObj = new DutyObj();
			dutyNService.genSpecCheck(mandt, flag, item, dsChar, ds_log, lang, dutyObj, dsOrder);

			if ( ds_log.getRowCount() > 0 ) {   // 종속성 체크후 log 있으면 return
				resultVO.addDataset(ds_log); 	// log 내역 
				return resultVO;
			}
		} catch (Exception e) {
			ds_error.addColumn("IDX", ColumnInfo.COLTYPE_STRING, 256);
			ds_error.addColumn("ERRMSG", ColumnInfo.COLTYPE_STRING, 256);
			ds_error.appendRow();
			ds_error.setColumn(0, "IDX", "9999");
			ds_error.setColumn(0, "ERRMSG", "[필수 입력 에러] 호기번호:" + item + ", " + e.getMessage());
			resultVO.addDataset(ds_error);
		}
		return resultVO;
	}
	
	//=========================================================================================
	//  함수기능 	: 제한조건 제외 원가산출
	//  개선내역 	: 제한조건 제외하고 원가산출 수행
	//  HISTORY : 2020.07.09 박수근
	//=========================================================================================
	public MipResultVO restrictionCondisearchCost(MipParameterVO paramVO, SqlSession session) throws RfcException, Exception {
		MipResultVO resultVO = new MipResultVO();
		Rfc rfc = null;
		Dataset dsCost     = paramVO.getDataset("ds_cost");
		Dataset dsChar     = paramVO.getDataset("ds_char");		// 화면상의 ds_template
		Dataset dsHeader   = paramVO.getDataset("ds_header");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		Dataset ds_300    = null; // UI로 return할 out dataset
		Dataset ds_202    = null; // UI로 return할 out dataset
		Dataset ds_302    = null; // UI로 return할 out dataset
		Dataset ds_204    = null; // UI로 return할 out dataset
		Dataset ds_304    = null; // UI로 return할 out dataset
		Dataset ds_check = null;
		
		try
		{
			// ---------------------------------------------------------------
			//	2. 원가 계산 
			// ---------------------------------------------------------------			
			// JCO 방식 SAP 호출 방식
			boolean grpCall = "TRUE".equals(paramVO.getVariable("grpCall"));
			try {
				rfc = ses0031NJS.callProjectDataSet(paramVO.getVariable("G_SYSID"), DatasetUtility.getString(dsCost, "DIV"), DatasetUtility.getString(dsCost, "GBN")
	                    , DatasetUtility.getString(dsCost, "GJAHR"), DatasetUtility.getString(dsCost, "POPER")
	                    , 0, "", paramVO.getVariable("ONE"), grpCall, dsChar, ds_check, null, null, null, null, null, null, null, null, null);

				ds_300 = rfc.getDatasetFromJcoTable("T_T300");
				ds_202 = rfc.getDatasetFromJcoTable("T_T202");
				ds_302 = rfc.getDatasetFromJcoTable("T_T302");
				ds_204 = rfc.getDatasetFromJcoTable("T_T204");
				ds_304 = rfc.getDatasetFromJcoTable("T_T304");
				ds_check = rfc.getDatasetFromJcoTable("T_CHEK");

				ds_202.setId("ds_t202");
				ds_204.setId("ds_t204");
				ds_300.setId("ds_t300");
				ds_302.setId("ds_t302");
				ds_304.setId("ds_t304");
				ds_check.setId("ds_check");
				
				if("4".equals(rfc.getDatasetFromJcoTable("E_TYPE")) ) {
					ds_error = rfc.getDatasetFromJcoTable("O_ETAB"); // 오류결과
					resultVO.addDataset(ds_error); 	// 오류결과내역
				}
			} catch (Exception e) {
				e.printStackTrace();
				ds_error.setId("ds_error");
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CI30001", "원가산출 기능이 정상 작동하지 않습니다.  CE팀 확인 바랍니다.", "Y", "Y");
				resultVO.addDataset(ds_error);
				return resultVO;
			}
			
			// 견적의 경우에만 처리 2012.12.18
			if ( DatasetUtility.getString(dsCost, "DIV").equals("3") )	{
				// service.saveCostUpdate(dsHeader, dsExchange, ds_302, paramVO, session);	// 환율기준정보 변경으로 제거 2013.02.20 &&&&&
				// 2013.03.13 원가산출(재산출) 시 부분원가 동일 여부 확인
	//						boolean bCheck = false;
	//						for (int i = 0; i < ds_check.getRowCount(); i++)		{
	//							if ( !"X".equals(DatasetUtility.getString(ds_check, i, "STATE")) )	{
	//								bCheck = true;	// 부분원가가 존재하는 경우
	//								break;
	//							}
	//						}
	
				//2013.03.20 부분원가 처리 여부 확인, 부분원가 처리대상만 존재하도록 처리
				boolean bCheck   = false;
				String  keyQtseq = "";
				String  tmpQtseq = "";
				//String  tmpState = "";
				//HashMap tmpMap = new HashMap();
				
				int iRow = 0;
				
				Dataset tmpCheck = new Dataset("tmpCheck");
				tmpCheck.addColumn("KEY",   ColumnInfo.COLTYPE_STRING, 10);
				
				for (int i = 0; i < ds_check.getRowCount(); i++)	{
					keyQtseq = DatasetUtility.getString(ds_check, i, "QTSEQ");
					if ( i == 0)	{
						//tmpMap.put(keyQtseq, keyQtseq);
						iRow = tmpCheck.appendRow();
						tmpCheck.setColumn(iRow, "KEY", keyQtseq);
					}else	{
						//tmpQtseq = (String) tmpMap.get(keyQtseq);
						tmpQtseq = keyQtseq;
						if (tmpQtseq == null || "".equals(tmpQtseq) )	{
							//tmpMap.put(keyQtseq, keyQtseq);
							iRow = tmpCheck.appendRow();
							tmpCheck.setColumn(iRow, "KEY", keyQtseq);
						}
					}
				}
	
				for (int ii = 0; ii < tmpCheck.getRowCount(); ii++ )	{
					tmpQtseq = DatasetUtility.getString(tmpCheck, ii, "KEY");
					bCheck = false;
	
					for (int i = 0; i < ds_check.getRowCount(); i++)	{
						if (tmpQtseq.equals(DatasetUtility.getString(ds_check, i, "QTSEQ")) )	{
							if ( !"X".equals(DatasetUtility.getString(ds_check, i, "STATE")) )		{
								bCheck = true;	// 부분원가가 존재하는 경우
								break;
							}
						}
					}
	
					if (!bCheck)	{	// 부분원가 재 처리 불필요건
						for (int i = 0; i < ds_check.getRowCount(); i++)	{
							if (tmpQtseq.equals(DatasetUtility.getString(ds_check, i, "QTSEQ")) )	{
								ds_check.deleteRow(i);
							}
						}
					}
				}
				
				resultVO = this.saveCostUpdate(dsHeader, ds_302, paramVO, session);
			}
	
			resultVO.addDataset(ds_check);
			
			//this.getSapJcoCostDataset(dsCost, ds_error, paramVO, resultVO, session);
		} catch (CommRfcException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} catch (RuntimeException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), e.getMessage(), "Y", "Y"); // 환율의 경우에는 메시지 코드 임
		}catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "CE00001", "Y", "Y");
		}
		
		resultVO.addDataset(ds_202);
		resultVO.addDataset(ds_302);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		
		return resultVO;
	}
	

	public Dataset saveQtVadt(MipParameterVO paramVO, Model model, SqlSession session) throws Exception	{
		Dataset dsHeader         = paramVO.getDataset("ds_header");
		Ses0031ParamVO param     = new Ses0031ParamVO();

		try
		{
			createDao(session);  // DAO생성
			service0030.createDao(session);
			
			String mandt      = "";
			String qtnum      = "";
			String qtser      = "";
	
			mandt   = paramVO.getVariable("G_MANDT");
			qtnum   = DatasetUtility.getString(dsHeader, 0, "QTNUM");
			qtser   = DatasetUtility.getString(dsHeader, 0, "QTSER");
	
			param.setMandt(mandt);
			param.setQtnum(qtnum);
			param.setQtser(qtser);
			param.setCuser(paramVO.getVariable("G_USER_ID"));
			param.setUuser(paramVO.getVariable("G_USER_ID"));
			
			updateQtvadt(param);

			return dsHeader;
		}catch(Exception e)	{
			throw e;
		}
	}
	
	
}

