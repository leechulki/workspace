package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0451;
import hdel.sd.ses.domain.Ses0451ParamVO;
import hdel.sd.ses.service.Ses0451S;
import hdel.sd.sso.domain.ZSDT0094;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.SapFunction;
import com.helco.xf.comm.ZQMSEMSG;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0451")
public class Ses0451C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0451S service;
	
	/**
	 * ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ç·Ú¿ï¿½Ã» ï¿½ï¿½ ï¿½ï¿½È¸
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View Ses0451FindView(MipParameterVO paramVO, Model model) {
		
		// get Input Dataset
		Dataset dsCond          = paramVO.getDataset("ds_cond");
		Dataset dsHeader        = paramVO.getDataset("ds_header");			// UIï¿½ï¿½ returnï¿½ï¿½ out dataset
		Dataset dsTemplate      = paramVO.getDataset("ds_template");		// UIï¿½ï¿½ returnï¿½ï¿½ out dataset
		Dataset dsTemplateSd120 = paramVO.getDataset("ds_templateSd120");	// UIï¿½ï¿½ returnï¿½ï¿½ out dataset
		Dataset dsFile    = paramVO.getDataset("ds_file");					// UIï¿½ï¿½ returnï¿½ï¿½ out dataset		
		Dataset dsEmail    = paramVO.getDataset("ds_email");					// UIï¿½ï¿½ returnï¿½ï¿½ out dataset		
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UIï¿½ï¿½ returnï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Þ¼ï¿½ï¿½ï¿½ dataset
		
		// get Variable
		String mandt   = paramVO.getVariable("G_MANDT");	// SAP CLIENT
		String zrqseq  = DatasetUtility.getString(dsCond, "ZRQSEQ");
		String qtso_no = DatasetUtility.getString(dsCond, "QTSO_NO");
		String qtser   = DatasetUtility.getString(dsCond, "QTSER");
		String qtseq   = DatasetUtility.getString(dsCond, "QTSEQ");
		String hogi    = DatasetUtility.getString(dsCond, "HOGI");
		String uuser   = paramVO.getVariable("G_USER_ID");
		String spras   = paramVO.getVariable("G_LANG").equals("ko") ? "3" : "E";
		String gubun   = DatasetUtility.getString(dsCond, "GUBUN");
		String matnr   = DatasetUtility.getString(dsCond, "MATNR");
		String posnr   = DatasetUtility.getString(dsCond, "POSNR");
		String email_dept   = DatasetUtility.getString(dsEmail, "EMAIL_DEPT");
		
		if (mandt   == null) mandt   = "";
		if (zrqseq  == null) zrqseq  = "";
		if (qtso_no == null) qtso_no = "";
		if (qtser   == null) qtser   = "";
		if (qtseq   == null) qtseq   = "";
		if (hogi    == null) hogi    = "";
		if (uuser   == null) uuser   = "";
		if (matnr   == null) matnr   = "";
		if (posnr   == null) posnr   = "";
		if (email_dept   == null) email_dept   = "";
		
		if ( "".equals(hogi) ) {
			gubun = "Q";
		} else {
			gubun = "P";
		}

		MipResultVO resultVO = new MipResultVO();
		
		try {
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			Ses0451ParamVO param = new Ses0451ParamVO();
			
			param.setMandt  (mandt);
			param.setZrqseq (zrqseq);
			param.setQtso_no(qtso_no);
			param.setQtser  (qtser);
			param.setQtseq  (qtseq);
			param.setHogi   (hogi);
			param.setUuser  (uuser);
			param.setSpras  (spras);
			param.setGubun  (gubun);
			param.setMatnr  (matnr);
			param.setPosnr  (posnr);
			

			// [1/3]
			if ( !"".equals(zrqseq) ) {
				dsHeader.deleteAll();
				List<Ses0451> listHeader = service.getListHeader(param);      // ï¿½ï¿½ï¿½ï¿½CALL
				for ( int i = 0 ; i < listHeader.size() ; i++ ) {
					String MANDT      = listHeader.get(i).getMandt();      if ( MANDT      == null ) MANDT      = "";
					String ZRQSEQ     = listHeader.get(i).getZrqseq();     if ( ZRQSEQ     == null ) ZRQSEQ     = "";
					String ZRQDAT     = listHeader.get(i).getZrqdat();     if ( ZRQDAT     == null ) ZRQDAT     = "";
					String DSNGBN     = listHeader.get(i).getDsngbn();     if ( DSNGBN     == null ) DSNGBN     = "";
					String ZRQID      = listHeader.get(i).getZrqid();      if ( ZRQID      == null ) ZRQID      = "";
					String ZRQNM      = listHeader.get(i).getZrqnm();      if ( ZRQNM      == null ) ZRQNM      = "";
					String ZRQ_DEPTNM = listHeader.get(i).getZrq_deptnm(); if ( ZRQ_DEPTNM == null ) ZRQ_DEPTNM = "";
					String ZRQTEL     = listHeader.get(i).getZrqtel();     if ( ZRQTEL     == null ) ZRQTEL     = "";
					String ZRQCEL     = listHeader.get(i).getZrqcel();     if ( ZRQCEL     == null ) ZRQCEL     = "";
					String ZRQCN      = listHeader.get(i).getZrqcn();      if ( ZRQCN      == null ) ZRQCN      = "";
					String ZRSRLT     = listHeader.get(i).getZrsrlt();     if ( ZRSRLT     == null ) ZRSRLT     = "";
					String ZRQSTAT    = listHeader.get(i).getZrqstat();    if ( ZRQSTAT    == null ) ZRQSTAT    = "";
					String KUNNR_DS   = listHeader.get(i).getKunnr_ds();   if ( KUNNR_DS   == null ) KUNNR_DS   = "";
					String KUNNR_DSNM = listHeader.get(i).getKunnr_dsnm(); if ( KUNNR_DSNM == null ) KUNNR_DSNM = "";
					String QTSO_NO    = listHeader.get(i).getQtso_no();    if ( QTSO_NO    == null ) QTSO_NO    = "";
					String QTSER      = listHeader.get(i).getQtser();      if ( QTSER      == null ) QTSER      = "";
					String QTSEQ      = listHeader.get(i).getQtseq();      if ( QTSEQ      == null ) QTSEQ      = "";
					String HOGI       = listHeader.get(i).getHogi();       if ( HOGI       == null ) HOGI       = "";
					String DLVDAT     = listHeader.get(i).getDlvdat();     if ( DLVDAT     == null ) DLVDAT     = "";
					String FINDAT     = listHeader.get(i).getFindat();     if ( FINDAT     == null ) FINDAT     = "";
					String METR_2ND   = listHeader.get(i).getMetr_2nd();   if ( METR_2ND   == null ) METR_2ND   = "";
					String ATYP_2ND   = listHeader.get(i).getAtyp_2nd();   if ( ATYP_2ND   == null ) ATYP_2ND   = "";
					String AMAN_2ND   = listHeader.get(i).getAman_2nd();   if ( AMAN_2ND   == null ) AMAN_2ND   = "";
					String AQTY_2ND   = listHeader.get(i).getAqty_2nd();   if ( AQTY_2ND   == null ) AQTY_2ND   = "";
					String ASPD_2ND   = listHeader.get(i).getAspd_2nd();   if ( ASPD_2ND   == null ) ASPD_2ND   = "";
					String AFLR_2ND   = listHeader.get(i).getAflr_2nd();   if ( AFLR_2ND   == null ) AFLR_2ND   = "";
					String AUSE_2ND   = listHeader.get(i).getAuse_2nd();   if ( AUSE_2ND   == null ) AUSE_2ND   = "";
					String ADOOR_2ND  = listHeader.get(i).getAdoor_2nd();  if ( ADOOR_2ND  == null ) ADOOR_2ND  = "";
					String CEIL_2ND   = listHeader.get(i).getCeil_2nd();   if ( CEIL_2ND   == null ) CEIL_2ND   = "";
					String WALL_2ND   = listHeader.get(i).getWall_2nd();   if ( WALL_2ND   == null ) WALL_2ND   = "";
					String FLOR_2ND   = listHeader.get(i).getFlor_2nd();   if ( FLOR_2ND   == null ) FLOR_2ND   = "";
					String HDRL_2ND   = listHeader.get(i).getHdrl_2nd();   if ( HDRL_2ND   == null ) HDRL_2ND   = "";
					String OPER_2ND   = listHeader.get(i).getOper_2nd();   if ( OPER_2ND   == null ) OPER_2ND   = "";
					String DOOR_2ND   = listHeader.get(i).getDoor_2nd();   if ( DOOR_2ND   == null ) DOOR_2ND   = "";
					String POID_2ND   = listHeader.get(i).getPoid_2nd();   if ( POID_2ND   == null ) POID_2ND   = "";
					String HATC_2ND   = listHeader.get(i).getHatc_2nd();   if ( HATC_2ND   == null ) HATC_2ND   = "";
					String JAMB_2ND   = listHeader.get(i).getJamb_2nd();   if ( JAMB_2ND   == null ) JAMB_2ND   = "";
					String HBTN_2ND   = listHeader.get(i).getHbtn_2nd();   if ( HBTN_2ND   == null ) HBTN_2ND   = "";
					String HLTN_2ND   = listHeader.get(i).getHltn_2nd();   if ( HLTN_2ND   == null ) HLTN_2ND   = "";
					String EXPO_2ND   = listHeader.get(i).getExpo_2nd();   if ( EXPO_2ND   == null ) EXPO_2ND   = "";
					String METR_3ND   = listHeader.get(i).getMetr_3nd();   if ( METR_3ND   == null ) METR_3ND   = "";
					String ATYP_3ND   = listHeader.get(i).getAtyp_3nd();   if ( ATYP_3ND   == null ) ATYP_3ND   = "";
					String AMAN_3ND   = listHeader.get(i).getAman_3nd();   if ( AMAN_3ND   == null ) AMAN_3ND   = "";
					String AQTY_3ND   = listHeader.get(i).getAqty_3nd();   if ( AQTY_3ND   == null ) AQTY_3ND   = "";
					String ASPD_3ND   = listHeader.get(i).getAspd_3nd();   if ( ASPD_3ND   == null ) ASPD_3ND   = "";
					String AFLR_3ND   = listHeader.get(i).getAflr_3nd();   if ( AFLR_3ND   == null ) AFLR_3ND   = "";
					String AUSE_3ND   = listHeader.get(i).getAuse_3nd();   if ( AUSE_3ND   == null ) AUSE_3ND   = "";
					String ADOOR_3ND  = listHeader.get(i).getAdoor_3nd();  if ( ADOOR_3ND  == null ) ADOOR_3ND  = "";
					String CEIL_3ND   = listHeader.get(i).getCeil_3nd();   if ( CEIL_3ND   == null ) CEIL_3ND   = "";
					String WALL_3ND   = listHeader.get(i).getWall_3nd();   if ( WALL_3ND   == null ) WALL_3ND   = "";
					String FLOR_3ND   = listHeader.get(i).getFlor_3nd();   if ( FLOR_3ND   == null ) FLOR_3ND   = "";
					String HDRL_3ND   = listHeader.get(i).getHdrl_3nd();   if ( HDRL_3ND   == null ) HDRL_3ND   = "";
					String OPER_3ND   = listHeader.get(i).getOper_3nd();   if ( OPER_3ND   == null ) OPER_3ND   = "";
					String DOOR_3ND   = listHeader.get(i).getDoor_3nd();   if ( DOOR_3ND   == null ) DOOR_3ND   = "";
					String POID_3ND   = listHeader.get(i).getPoid_3nd();   if ( POID_3ND   == null ) POID_3ND   = "";
					String HATC_3ND   = listHeader.get(i).getHatc_3nd();   if ( HATC_3ND   == null ) HATC_3ND   = "";
					String JAMB_3ND   = listHeader.get(i).getJamb_3nd();   if ( JAMB_3ND   == null ) JAMB_3ND   = "";
					String HBTN_3ND   = listHeader.get(i).getHbtn_3nd();   if ( HBTN_3ND   == null ) HBTN_3ND   = "";
					String HLTN_3ND   = listHeader.get(i).getHltn_3nd();   if ( HLTN_3ND   == null ) HLTN_3ND   = "";
					String EXPO_3ND   = listHeader.get(i).getExpo_3nd();   if ( EXPO_3ND   == null ) EXPO_3ND   = "";
					String NOREST     = listHeader.get(i).getNorest();     if ( NOREST     == null ) NOREST     = "";
					String REST01     = listHeader.get(i).getRest01();     if ( REST01     == null ) REST01     = "";
					String REST02     = listHeader.get(i).getRest02();     if ( REST02     == null ) REST02     = "";
					String REST03     = listHeader.get(i).getRest03();     if ( REST03     == null ) REST03     = "";
					String DELV01     = listHeader.get(i).getDelv01();     if ( DELV01     == null ) DELV01     = "";
					String DELV02     = listHeader.get(i).getDelv02();     if ( DELV02     == null ) DELV02     = "";
					String DELV03     = listHeader.get(i).getDelv03();     if ( DELV03     == null ) DELV03     = "";
					String ADDTUSE01  = listHeader.get(i).getAddtuse01();  if ( ADDTUSE01  == null ) ADDTUSE01  = "";  //2013.06.03
					String ADDTUSE02  = listHeader.get(i).getAddtuse02();  if ( ADDTUSE02  == null ) ADDTUSE02  = "";					
					String EX_KUNNR   = listHeader.get(i).getEx_kunnr();   if ( EX_KUNNR   == null ) EX_KUNNR   = "";
					String EX_KUNNRNM = listHeader.get(i).getEx_kunnrnm(); if ( EX_KUNNRNM == null ) EX_KUNNRNM = "";
					String INTER_AMT  = listHeader.get(i).getInter_amt();  if ( INTER_AMT  == null ) INTER_AMT  = "";
					String RQ_SA      = listHeader.get(i).getRq_sa();      if ( RQ_SA      == null ) RQ_SA      = "";
					String RQ_SA_NM   = listHeader.get(i).getRq_sa_nm();   if ( RQ_SA_NM   == null ) RQ_SA_NM   = "";
					String RQ_TB      = listHeader.get(i).getRq_tb();      if ( RQ_TB      == null ) RQ_TB      = "";
					String RQ_TB_NM   = listHeader.get(i).getRq_tb_nm();   if ( RQ_TB_NM   == null ) RQ_TB_NM   = "";
					String RQ_DB      = listHeader.get(i).getRq_db();      if ( RQ_DB      == null ) RQ_DB      = "";
					String RQ_DB_NM   = listHeader.get(i).getRq_db_nm();   if ( RQ_DB_NM   == null ) RQ_DB_NM   = "";
					String DS_TB      = listHeader.get(i).getDs_tb();      if ( DS_TB      == null ) DS_TB      = "";
					String DS_TB_NM   = listHeader.get(i).getDs_tb_nm();   if ( DS_TB_NM   == null ) DS_TB_NM   = "";
					String DS_DB      = listHeader.get(i).getDs_db();      if ( DS_DB      == null ) DS_DB      = "";
					String DS_DB_NM   = listHeader.get(i).getDs_db_nm();   if ( DS_DB_NM   == null ) DS_DB_NM   = "";
					String CDATE      = listHeader.get(i).getCdate();      if ( CDATE      == null ) CDATE      = "";
					String CTIME      = listHeader.get(i).getCtime();      if ( CTIME      == null ) CTIME      = "";
					String CUSER      = listHeader.get(i).getCuser();      if ( CUSER      == null ) CUSER      = "";
					String UDATE      = listHeader.get(i).getUdate();      if ( UDATE      == null ) UDATE      = "";
					String UTIME      = listHeader.get(i).getUtime();      if ( UTIME      == null ) UTIME      = "";
					String UUSER      = listHeader.get(i).getUuser();      if ( UUSER      == null ) UUSER      = "";
					String VBELN      = listHeader.get(i).getVbeln();      if ( VBELN      == null ) VBELN      = "";
					String GSNAM      = listHeader.get(i).getGsnam();      if ( GSNAM      == null ) GSNAM      = "";
					String GTYPE      = listHeader.get(i).getGtype();      if ( GTYPE      == null ) GTYPE      = "";
					String ZNUMBER    = listHeader.get(i).getZnumber();    if ( ZNUMBER    == null ) ZNUMBER    = "";
					String ZACAPA     = listHeader.get(i).getZacapa();     if ( ZACAPA     == null ) ZACAPA     = "";
					String ZUSE       = listHeader.get(i).getZuse();       if ( ZUSE       == null ) ZUSE       = "";
					String ZOPN       = listHeader.get(i).getZopn();       if ( ZOPN       == null ) ZOPN       = "";
					String ZLEN       = listHeader.get(i).getZlen();       if ( ZLEN       == null ) ZLEN       = "";
					String TYPE1      = listHeader.get(i).getType1();      if ( TYPE1      == null ) TYPE1      = "";
					String TYPE2      = listHeader.get(i).getType2();      if ( TYPE2      == null ) TYPE2      = "";
					String TYPE3      = listHeader.get(i).getType3();      if ( TYPE3      == null ) TYPE3      = "";
					String DFINDAT    = listHeader.get(i).getDfindat();    if (DFINDAT  == null)     DFINDAT   = "";
					String email    = listHeader.get(i).getEmail();    if (email  == null)     email   = "";
					String ZRQSEQ_DEL = listHeader.get(i).getEmail_dept(); if ( ZRQSEQ_DEL     == null ) ZRQSEQ_DEL     = "";
					String EMAIL_DEPT = listHeader.get(i).getEmail_dept(); if ( EMAIL_DEPT     == null ) EMAIL_DEPT     = "";
					String LIFNR = listHeader.get(i).getLifnr(); if ( LIFNR     == null ) LIFNR     = "";
					String LIFNRNM = listHeader.get(i).getLifnrnm(); if ( LIFNRNM     == null ) LIFNRNM     = "";
					
					if ( "".equals(KUNNR_DSNM) ) KUNNR_DSNM = KUNNR_DS;
					if ( "".equals(RQ_SA_NM) ) RQ_SA_NM = RQ_SA;
					if ( "".equals(RQ_TB_NM) ) RQ_TB_NM = RQ_TB;
					if ( "".equals(RQ_DB_NM) ) RQ_DB_NM = RQ_DB;
					if ( "".equals(DS_TB_NM) ) DS_TB_NM = DS_TB;
					if ( "".equals(DS_DB_NM) ) DS_DB_NM = DS_DB;
					
					dsHeader.appendRow();
					dsHeader.setColumn(i, "MANDT"     , MANDT);
					dsHeader.setColumn(i, "ZRQSEQ"    , ZRQSEQ);
					dsHeader.setColumn(i, "ZRQDAT"    , ZRQDAT);
					dsHeader.setColumn(i, "DSNGBN"    , DSNGBN);
					dsHeader.setColumn(i, "ZRQID"     , ZRQID);
					dsHeader.setColumn(i, "ZRQNM"     , ZRQNM);
					dsHeader.setColumn(i, "ZRQ_DEPTNM", ZRQ_DEPTNM);
					dsHeader.setColumn(i, "ZRQTEL"    , ZRQTEL);
					dsHeader.setColumn(i, "ZRQCEL"    , ZRQCEL);
					dsHeader.setColumn(i, "ZRQCN"     , ZRQCN);
					dsHeader.setColumn(i, "ZRSRLT"    , ZRSRLT);
					dsHeader.setColumn(i, "ZRQSTAT"   , ZRQSTAT);
					dsHeader.setColumn(i, "KUNNR_DS"  , KUNNR_DS);
					dsHeader.setColumn(i, "KUNNR_DSNM", KUNNR_DSNM);
					dsHeader.setColumn(i, "QTSO_NO"   , QTSO_NO);
					dsHeader.setColumn(i, "QTSER"     , QTSER);
					dsHeader.setColumn(i, "QTSEQ"     , QTSEQ);
					dsHeader.setColumn(i, "HOGI"      , HOGI);
					dsHeader.setColumn(i, "DLVDAT"    , DLVDAT);
					dsHeader.setColumn(i, "FINDAT"    , FINDAT);
					dsHeader.setColumn(i, "METR_2ND"  , METR_2ND);
					dsHeader.setColumn(i, "ATYP_2ND"  , ATYP_2ND);
					dsHeader.setColumn(i, "AMAN_2ND"  , AMAN_2ND);
					dsHeader.setColumn(i, "AQTY_2ND"  , AQTY_2ND);
					dsHeader.setColumn(i, "ASPD_2ND"  , ASPD_2ND);
					dsHeader.setColumn(i, "AFLR_2ND"  , AFLR_2ND);
					dsHeader.setColumn(i, "AUSE_2ND"  , AUSE_2ND);
					dsHeader.setColumn(i, "ADOOR_2ND" , ADOOR_2ND);
					dsHeader.setColumn(i, "CEIL_2ND"  , CEIL_2ND);
					dsHeader.setColumn(i, "WALL_2ND"  , WALL_2ND);
					dsHeader.setColumn(i, "FLOR_2ND"  , FLOR_2ND);
					dsHeader.setColumn(i, "HDRL_2ND"  , HDRL_2ND);
					dsHeader.setColumn(i, "OPER_2ND"  , OPER_2ND);
					dsHeader.setColumn(i, "DOOR_2ND"  , DOOR_2ND);
					dsHeader.setColumn(i, "POID_2ND"  , POID_2ND);
					dsHeader.setColumn(i, "HATC_2ND"  , HATC_2ND);
					dsHeader.setColumn(i, "JAMB_2ND"  , JAMB_2ND);
					dsHeader.setColumn(i, "HBTN_2ND"  , HBTN_2ND);
					dsHeader.setColumn(i, "HLTN_2ND"  , HLTN_2ND);
					dsHeader.setColumn(i, "EXPO_2ND"  , EXPO_2ND);
					dsHeader.setColumn(i, "METR_3ND"  , METR_3ND);
					dsHeader.setColumn(i, "ATYP_3ND"  , ATYP_3ND);
					dsHeader.setColumn(i, "AMAN_3ND"  , AMAN_3ND);
					dsHeader.setColumn(i, "AQTY_3ND"  , AQTY_3ND);
					dsHeader.setColumn(i, "ASPD_3ND"  , ASPD_3ND);
					dsHeader.setColumn(i, "AFLR_3ND"  , AFLR_3ND);
					dsHeader.setColumn(i, "AUSE_3ND"  , AUSE_3ND);
					dsHeader.setColumn(i, "ADOOR_3ND" , ADOOR_3ND);
					dsHeader.setColumn(i, "CEIL_3ND"  , CEIL_3ND);
					dsHeader.setColumn(i, "WALL_3ND"  , WALL_3ND);
					dsHeader.setColumn(i, "FLOR_3ND"  , FLOR_3ND);
					dsHeader.setColumn(i, "HDRL_3ND"  , HDRL_3ND);
					dsHeader.setColumn(i, "OPER_3ND"  , OPER_3ND);
					dsHeader.setColumn(i, "DOOR_3ND"  , DOOR_3ND);
					dsHeader.setColumn(i, "POID_3ND"  , POID_3ND);
					dsHeader.setColumn(i, "HATC_3ND"  , HATC_3ND);
					dsHeader.setColumn(i, "JAMB_3ND"  , JAMB_3ND);
					dsHeader.setColumn(i, "HBTN_3ND"  , HBTN_3ND);
					dsHeader.setColumn(i, "HLTN_3ND"  , HLTN_3ND);
					dsHeader.setColumn(i, "EXPO_3ND"  , EXPO_3ND);
					dsHeader.setColumn(i, "NOREST"    , NOREST.equals("Y") ? "1" : "0");
					dsHeader.setColumn(i, "REST01"    , REST01.equals("Y") ? "1" : "0");
					dsHeader.setColumn(i, "REST02"    , REST02.equals("Y") ? "1" : "0");
					dsHeader.setColumn(i, "REST03"    , REST03.equals("Y") ? "1" : "0");
					dsHeader.setColumn(i, "DELV01"    , DELV01.equals("Y") ? "1" : "0");
					dsHeader.setColumn(i, "DELV02"    , DELV02.equals("Y") ? "1" : "0");
					dsHeader.setColumn(i, "DELV03"    , DELV03.equals("Y") ? "1" : "0");
					dsHeader.setColumn(i, "ADDTUSE01" , ADDTUSE01.equals("Y") ? "1" : "0");
					dsHeader.setColumn(i, "ADDTUSE02" , ADDTUSE02.equals("Y") ? "1" : "0");					
					dsHeader.setColumn(i, "EX_KUNNR"  , EX_KUNNR);
					dsHeader.setColumn(i, "EX_KUNNRNM", EX_KUNNRNM);
					dsHeader.setColumn(i, "INTER_AMT" , INTER_AMT);
					dsHeader.setColumn(i, "RQ_SA"     , RQ_SA);
					dsHeader.setColumn(i, "RQ_SA_NM"  , RQ_SA_NM);
					dsHeader.setColumn(i, "RQ_TB"     , RQ_TB);
					dsHeader.setColumn(i, "RQ_TB_NM"  , RQ_TB_NM);
					dsHeader.setColumn(i, "RQ_DB"     , RQ_DB);
					dsHeader.setColumn(i, "RQ_DB_NM"  , RQ_DB_NM);
					dsHeader.setColumn(i, "DS_TB"     , DS_TB);
					dsHeader.setColumn(i, "DS_TB_NM"  , DS_TB_NM);
					dsHeader.setColumn(i, "DS_DB"     , DS_DB);
					dsHeader.setColumn(i, "DS_DB_NM"  , DS_DB_NM);
					dsHeader.setColumn(i, "CDATE"     , CDATE);
					dsHeader.setColumn(i, "CTIME"     , CTIME);
					dsHeader.setColumn(i, "CUSER"     , CUSER);
					dsHeader.setColumn(i, "UDATE"     , UDATE);
					dsHeader.setColumn(i, "UTIME"     , UTIME);
					dsHeader.setColumn(i, "UUSER"     , UUSER);
					dsHeader.setColumn(i, "VBELN"     , VBELN);
					dsHeader.setColumn(i, "GSNAM"     , GSNAM);
					dsHeader.setColumn(i, "GTYPE"     , GTYPE);
					dsHeader.setColumn(i, "ZNUMBER"   , ZNUMBER);
					dsHeader.setColumn(i, "ZACAPA"    , ZACAPA);
					dsHeader.setColumn(i, "ZUSE"      , ZUSE);
					dsHeader.setColumn(i, "ZOPN"      , ZOPN);
					dsHeader.setColumn(i, "ZLEN"      , ZLEN);
					dsHeader.setColumn(i, "TYPE1"     , TYPE1);
					dsHeader.setColumn(i, "TYPE2"     , TYPE2);
					dsHeader.setColumn(i, "TYPE3"     , TYPE3);
					dsHeader.setColumn(i, "GSNAM"     , GSNAM);
					dsHeader.setColumn(i, "DFINDAT"   , DFINDAT);
					dsHeader.setColumn(i, "EMAIL"   , email);
					dsHeader.setColumn(i, "ZRQSEQ_DEL"    , ZRQSEQ_DEL);
					dsHeader.setColumn(i, "EMAIL_DEPT"    , EMAIL_DEPT);
					dsHeader.setColumn(i, "LIFNR", LIFNR);
					dsHeader.setColumn(i, "LIFNRNM", LIFNRNM);

					if ( dsHeader.getColumnAsString(i, "HOGI") == null || "".equals(dsHeader.getColumnAsString(i, "HOGI").trim()))	{
						param.setGubun("Q");
					}else	{
						param.setGubun("P");
					}

					param.setQtso_no(dsHeader.getColumnAsString(i, "QTSO_NO"));
					param.setQtser  (dsHeader.getColumnAsString(i, "QTSER")  );
					param.setQtseq  (dsHeader.getColumnAsString(i, "QTSEQ")  );
					param.setHogi   (dsHeader.getColumnAsString(i, "HOGI")   );

				}
				resultVO.addDataset(dsHeader);
				
				dsFile.deleteAll();
				List<Ses0451> listFile = service.getListFile(param);
				
				for (int i = 0; i < listFile.size(); i++)	{
					dsFile.appendRow();
					dsFile.setColumn(i, "MANDT"  , listFile.get(i).getMandt());
					dsFile.setColumn(i, "ZRQSEQ"  , listFile.get(i).getZrqseq());
					dsFile.setColumn(i, "ZATTSEQ"  , listFile.get(i).getZattseq());
					dsFile.setColumn(i, "ZATGBN"  , listFile.get(i).getZatgbn());
					dsFile.setColumn(i, "ZATTPATH"  , listFile.get(i).getZattpath());
					dsFile.setColumn(i, "ZATTFN"  , listFile.get(i).getZattfn());		
				}	
				
				
				resultVO.addDataset(dsFile);			
				
				dsEmail.deleteAll();
				param.setEmail_dept(listHeader.get(0).getEmail_dept());
				List<Ses0451> listEmail = service.getListEmail(param);
				
				for (int i = 0; i < listEmail.size(); i++)	{				
					String EMAIL = listEmail.get(i).getEmail();    if (EMAIL  == null)     EMAIL   = "";
					dsEmail.appendRow();
					dsEmail.setColumn(i, "EMAIL"  , EMAIL);
				}
				
				resultVO.addDataset(dsEmail);
			}
			
			// [2/3]
			dsTemplateSd120.deleteAll();
			List<Ses0451> listSd120 = service.getListTemplateSd120(param);
			for (int i = 0; i < listSd120.size(); i++)	{
				dsTemplateSd120.appendRow();
				dsTemplateSd120.setColumn(i, "MANDT" , listSd120.get(i).getMandt());
				dsTemplateSd120.setColumn(i, "MATNR" , listSd120.get(i).getMatnr());
				dsTemplateSd120.setColumn(i, "PRH"   , listSd120.get(i).getPrh());
				dsTemplateSd120.setColumn(i, "SCOLOR", listSd120.get(i).getScolor());
			}
			resultVO.addDataset(dsTemplateSd120);
			
			//2020.08.25 jss 
			boolean proGbn = true;
			if ( param.getQtso_no() == null  ||  "".equals(param.getQtso_no().trim())) {
				proGbn = false;
			}
			if ( param.getQtser() == null  ||  "".equals(param.getQtser().trim())) {
				proGbn = false;
			}
			if ( param.getQtseq() == null  ||  "".equals(param.getQtseq().trim())) {
				proGbn = false;
			}
			
			
			// [3/3]
			if ( "Q".equals(param.getGubun()) ) {
				
				if(proGbn){ //2020.08.25 jss : Á¶°ÇÃ¼Å©Ãß°¡
					dsTemplate.deleteAll();
					List<Ses0451> listTemplate = service.getListTemplateQtnum(param);
					for (int i = 0; i < listTemplate.size(); i++) {
						dsTemplate.appendRow();
						dsTemplate.setColumn(i, "MANDT"  , listTemplate.get(i).getMandt());
						dsTemplate.setColumn(i, "QTSO_NO", listTemplate.get(i).getQtso_no());
						dsTemplate.setColumn(i, "QTSER"  , listTemplate.get(i).getQtser());
						dsTemplate.setColumn(i, "QTSEQ"  , listTemplate.get(i).getQtseq());
						dsTemplate.setColumn(i, "HOGI"   , param.getHogi());
						dsTemplate.setColumn(i, "PRH"    , listTemplate.get(i).getPrh());
						dsTemplate.setColumn(i, "PRD"    , listTemplate.get(i).getPrd());
						dsTemplate.setColumn(i, "MCLASS" , listTemplate.get(i).getMclass());
						dsTemplate.setColumn(i, "ATKLA"  , listTemplate.get(i).getAtkla());
						dsTemplate.setColumn(i, "PRHNAME", listTemplate.get(i).getPrhname());
					}
					resultVO.addDataset(dsTemplate);
				}
				
			} else {
				
				if ( !"".equals(matnr) ) {
					// RFC INPUT PARAM DECLARE 
					ZSDT0094[] list_ZSDT0094			= new ZSDT0094[]{};  // sap output list
					ZQMSEMSG[] listMsg  				= new ZQMSEMSG[]{};
					
					// WFC INPUT SET
					Object obj[] = new Object[]{ 
							qtso_no
							, hogi
							, posnr
							, listMsg
							, list_ZSDT0094
					};
					
					// OUTPUT DATASET DECLARE
					Dataset ds_output_ZSDT0094 = null;
					
					// RFC CALL
					SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT")
										, "hdel.sd.sso.domain.ZWEB_SD_HOGI_SPEC2"
										, obj, false);
					
					ds_output_ZSDT0094 = CallSAP.isJCO() ? new Dataset() : ZSDT0094.getDataset();
					
					listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
					if ( "4".equals(stub.getOutput("E_TYPE").toString()) ) {
						dsError = CallSAP.makeErrorInfo(listMsg);
					}else	{
						CallSAP.moveObj2Ds(ds_output_ZSDT0094, stub.getOutput("S_ITAB"));
						
						dsTemplate.deleteAll();
						for (int i = 0; i < ds_output_ZSDT0094.getRowCount(); i++)	{
							dsTemplate.appendRow();
							dsTemplate.setColumn(i, "MANDT"  , ds_output_ZSDT0094.getColumn(i, "MANDT"));
							dsTemplate.setColumn(i, "MCLASS" , ds_output_ZSDT0094.getColumn(i, "MATNR"));
							dsTemplate.setColumn(i, "ATKLA"  , ds_output_ZSDT0094.getColumn(i, "ATKLA"));
							dsTemplate.setColumn(i, "PRH"    , ds_output_ZSDT0094.getColumn(i, "NAM_CHAR"));
							dsTemplate.setColumn(i, "PRD"    , ds_output_ZSDT0094.getColumn(i, "CHAR_VALUE"));
							dsTemplate.setColumn(i, "PRHNAME", ds_output_ZSDT0094.getColumn(i, "ATBEZ"));
							dsTemplate.setColumn(i, "QTSO_NO", param.getQtso_no());
							dsTemplate.setColumn(i, "HOGI"   , param.getHogi());
						}
						resultVO.addDataset(dsTemplate);
					}
				}else {
					dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CW10025", "Ç°¸ñÀÇ ÀÚÀç¹øÈ£¸¦ È®ÀÎÇÏ½Ê½Ã¿ä.", "Y", "Y");
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	/**
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="save")
	public View Ses0451SaveView(MipParameterVO paramVO, Model model) {
		
		// get Input Dataset
		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsFile    = paramVO.getDataset("ds_file");
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		String gvbcd = paramVO.getVariable("G_SAP_USER_VGCD");	//ï¿½Î¼ï¿½ï¿½Úµï¿½
		String vkbur = paramVO.getVariable("G_SAP_USER_VBCD");
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		
		// DAO 
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			String MANDT     = DatasetUtility.getString(dsHeader, 0, "MANDT"    ); if (MANDT     == null) MANDT     = "";
			String ZRQSEQ    = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"   ); if (ZRQSEQ    == null) ZRQSEQ    = "";
			String ZRQDAT    = DatasetUtility.getString(dsHeader, 0, "ZRQDAT"   ); if (ZRQDAT    == null) ZRQDAT    = "";
			String DSNGBN    = DatasetUtility.getString(dsHeader, 0, "DSNGBN"   ); if (DSNGBN    == null) DSNGBN    = "";
			String ZRQID     = DatasetUtility.getString(dsHeader, 0, "ZRQID"    ); if (ZRQID     == null) ZRQID     = "";
			String ZRQTEL    = DatasetUtility.getString(dsHeader, 0, "ZRQTEL"   ); if (ZRQTEL    == null) ZRQTEL    = "";
			String ZRQCEL    = DatasetUtility.getString(dsHeader, 0, "ZRQCEL"   ); if (ZRQCEL    == null) ZRQCEL    = "";
			String ZRQCN     = DatasetUtility.getString(dsHeader, 0, "ZRQCN"    ); if (ZRQCN     == null) ZRQCN     = "";
			String ZRSRLT    = DatasetUtility.getString(dsHeader, 0, "ZRSRLT"   ); if (ZRSRLT    == null) ZRSRLT    = "";
			String ZRQSTAT   = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT"  ); if (ZRQSTAT   == null) ZRQSTAT   = "";
			String KUNNR_DS  = DatasetUtility.getString(dsHeader, 0, "KUNNR_DS" ); if (KUNNR_DS  == null) KUNNR_DS  = "";
			String QTSO_NO   = DatasetUtility.getString(dsHeader, 0, "QTSO_NO"  ); if (QTSO_NO   == null) QTSO_NO   = "";
			String QTSER     = DatasetUtility.getString(dsHeader, 0, "QTSER"    ); if (QTSER     == null) QTSER     = "0";
			String QTSEQ     = DatasetUtility.getString(dsHeader, 0, "QTSEQ"    ); if (QTSEQ     == null) QTSEQ     = "0";
			String HOGI      = DatasetUtility.getString(dsHeader, 0, "HOGI"     ); if (HOGI      == null) HOGI      = "";
			String DLVDAT    = DatasetUtility.getString(dsHeader, 0, "DLVDAT"   ); if (DLVDAT    == null) DLVDAT    = "";
			String FINDAT    = DatasetUtility.getString(dsHeader, 0, "FINDAT"   ); if (FINDAT    == null) FINDAT    = "";
			String METR_2ND  = DatasetUtility.getString(dsHeader, 0, "METR_2ND" ); if (METR_2ND  == null) METR_2ND  = "";
			String ATYP_2ND  = DatasetUtility.getString(dsHeader, 0, "ATYP_2ND" ); if (ATYP_2ND  == null) ATYP_2ND  = "";
			String AMAN_2ND  = DatasetUtility.getString(dsHeader, 0, "AMAN_2ND" ); if (AMAN_2ND  == null) AMAN_2ND  = "";
			String AQTY_2ND  = DatasetUtility.getString(dsHeader, 0, "AQTY_2ND" ); if (AQTY_2ND  == null) AQTY_2ND  = "";
			String ASPD_2ND  = DatasetUtility.getString(dsHeader, 0, "ASPD_2ND" ); if (ASPD_2ND  == null) ASPD_2ND  = "";
			String AFLR_2ND  = DatasetUtility.getString(dsHeader, 0, "AFLR_2ND" ); if (AFLR_2ND  == null) AFLR_2ND  = "";
			String AUSE_2ND  = DatasetUtility.getString(dsHeader, 0, "AUSE_2ND" ); if (AUSE_2ND  == null) AUSE_2ND  = "";
			String ADOOR_2ND = DatasetUtility.getString(dsHeader, 0, "ADOOR_2ND"); if (ADOOR_2ND == null) ADOOR_2ND = "";
			String CEIL_2ND  = DatasetUtility.getString(dsHeader, 0, "CEIL_2ND" ); if (CEIL_2ND  == null) CEIL_2ND  = "";
			String WALL_2ND  = DatasetUtility.getString(dsHeader, 0, "WALL_2ND" ); if (WALL_2ND  == null) WALL_2ND  = "";
			String FLOR_2ND  = DatasetUtility.getString(dsHeader, 0, "FLOR_2ND" ); if (FLOR_2ND  == null) FLOR_2ND  = "";
			String HDRL_2ND  = DatasetUtility.getString(dsHeader, 0, "HDRL_2ND" ); if (HDRL_2ND  == null) HDRL_2ND  = "";
			String OPER_2ND  = DatasetUtility.getString(dsHeader, 0, "OPER_2ND" ); if (OPER_2ND  == null) OPER_2ND  = "";
			String DOOR_2ND  = DatasetUtility.getString(dsHeader, 0, "DOOR_2ND" ); if (DOOR_2ND  == null) DOOR_2ND  = "";
			String POID_2ND  = DatasetUtility.getString(dsHeader, 0, "POID_2ND" ); if (POID_2ND  == null) POID_2ND  = "";
			String HATC_2ND  = DatasetUtility.getString(dsHeader, 0, "HATC_2ND" ); if (HATC_2ND  == null) HATC_2ND  = "";
			String JAMB_2ND  = DatasetUtility.getString(dsHeader, 0, "JAMB_2ND" ); if (JAMB_2ND  == null) JAMB_2ND  = "";
			String HBTN_2ND  = DatasetUtility.getString(dsHeader, 0, "HBTN_2ND" ); if (HBTN_2ND  == null) HBTN_2ND  = "";
			String HLTN_2ND  = DatasetUtility.getString(dsHeader, 0, "HLTN_2ND" ); if (HLTN_2ND  == null) HLTN_2ND  = "";
			String EXPO_2ND  = DatasetUtility.getString(dsHeader, 0, "EXPO_2ND" ); if (EXPO_2ND  == null) EXPO_2ND  = "";
			String METR_3ND  = DatasetUtility.getString(dsHeader, 0, "METR_3ND" ); if (METR_3ND  == null) METR_3ND  = "";
			String ATYP_3ND  = DatasetUtility.getString(dsHeader, 0, "ATYP_3ND" ); if (ATYP_3ND  == null) ATYP_3ND  = "";
			String AMAN_3ND  = DatasetUtility.getString(dsHeader, 0, "AMAN_3ND" ); if (AMAN_3ND  == null) AMAN_3ND  = "";
			String AQTY_3ND  = DatasetUtility.getString(dsHeader, 0, "AQTY_3ND" ); if (AQTY_3ND  == null) AQTY_3ND  = "";
			String ASPD_3ND  = DatasetUtility.getString(dsHeader, 0, "ASPD_3ND" ); if (ASPD_3ND  == null) ASPD_3ND  = "";
			String AFLR_3ND  = DatasetUtility.getString(dsHeader, 0, "AFLR_3ND" ); if (AFLR_3ND  == null) AFLR_3ND  = "";
			String AUSE_3ND  = DatasetUtility.getString(dsHeader, 0, "AUSE_3ND" ); if (AUSE_3ND  == null) AUSE_3ND  = "";
			String ADOOR_3ND = DatasetUtility.getString(dsHeader, 0, "ADOOR_3ND"); if (ADOOR_3ND == null) ADOOR_3ND = "";
			String CEIL_3ND  = DatasetUtility.getString(dsHeader, 0, "CEIL_3ND" ); if (CEIL_3ND  == null) CEIL_3ND  = "";
			String WALL_3ND  = DatasetUtility.getString(dsHeader, 0, "WALL_3ND" ); if (WALL_3ND  == null) WALL_3ND  = "";
			String FLOR_3ND  = DatasetUtility.getString(dsHeader, 0, "FLOR_3ND" ); if (FLOR_3ND  == null) FLOR_3ND  = "";
			String HDRL_3ND  = DatasetUtility.getString(dsHeader, 0, "HDRL_3ND" ); if (HDRL_3ND  == null) HDRL_3ND  = "";
			String OPER_3ND  = DatasetUtility.getString(dsHeader, 0, "OPER_3ND" ); if (OPER_3ND  == null) OPER_3ND  = "";
			String DOOR_3ND  = DatasetUtility.getString(dsHeader, 0, "DOOR_3ND" ); if (DOOR_3ND  == null) DOOR_3ND  = "";
			String POID_3ND  = DatasetUtility.getString(dsHeader, 0, "POID_3ND" ); if (POID_3ND  == null) POID_3ND  = "";
			String HATC_3ND  = DatasetUtility.getString(dsHeader, 0, "HATC_3ND" ); if (HATC_3ND  == null) HATC_3ND  = "";
			String JAMB_3ND  = DatasetUtility.getString(dsHeader, 0, "JAMB_3ND" ); if (JAMB_3ND  == null) JAMB_3ND  = "";
			String HBTN_3ND  = DatasetUtility.getString(dsHeader, 0, "HBTN_3ND" ); if (HBTN_3ND  == null) HBTN_3ND  = "";
			String HLTN_3ND  = DatasetUtility.getString(dsHeader, 0, "HLTN_3ND" ); if (HLTN_3ND  == null) HLTN_3ND  = "";
			String EXPO_3ND  = DatasetUtility.getString(dsHeader, 0, "EXPO_3ND" ); if (EXPO_3ND  == null) EXPO_3ND  = "";
			String NOREST    = DatasetUtility.getString(dsHeader, 0, "NOREST"   ); if (NOREST    == null) NOREST    = "";
			String REST01    = DatasetUtility.getString(dsHeader, 0, "REST01"   ); if (REST01    == null) REST01    = "";
			String REST02    = DatasetUtility.getString(dsHeader, 0, "REST02"   ); if (REST02    == null) REST02    = "";
			String REST03    = DatasetUtility.getString(dsHeader, 0, "REST03"   ); if (REST03    == null) REST03    = "";
			String DELV01    = DatasetUtility.getString(dsHeader, 0, "DELV01"   ); if (DELV01    == null) DELV01    = "";
			String DELV02    = DatasetUtility.getString(dsHeader, 0, "DELV02"   ); if (DELV02    == null) DELV02    = "";
			String DELV03    = DatasetUtility.getString(dsHeader, 0, "DELV03"   ); if (DELV03    == null) DELV03    = "";
			String ADDTUSE01 = DatasetUtility.getString(dsHeader, 0, "ADDTUSE01"   ); if (ADDTUSE01    == null) ADDTUSE01    = "";
			String ADDTUSE02 = DatasetUtility.getString(dsHeader, 0, "ADDTUSE02"   ); if (ADDTUSE02    == null) ADDTUSE02    = "";			
			String EX_KUNNR  = DatasetUtility.getString(dsHeader, 0, "EX_KUNNR" ); if (EX_KUNNR  == null) EX_KUNNR  = "";
			String INTER_AMT = DatasetUtility.getString(dsHeader, 0, "INTER_AMT"); if (INTER_AMT == null) INTER_AMT = "";
			String RQ_SA     = DatasetUtility.getString(dsHeader, 0, "RQ_SA"    ); if (RQ_SA     == null) RQ_SA     = "";
			String RQ_TB     = DatasetUtility.getString(dsHeader, 0, "RQ_TB"    ); if (RQ_TB     == null) RQ_TB     = "";
			String RQ_DB     = DatasetUtility.getString(dsHeader, 0, "RQ_DB"    ); if (RQ_DB     == null) RQ_DB     = "";
			String DS_TB     = DatasetUtility.getString(dsHeader, 0, "DS_TB"    ); if (DS_TB     == null) DS_TB     = "";
			String DS_DB     = DatasetUtility.getString(dsHeader, 0, "DS_DB"    ); if (DS_DB     == null) DS_DB     = "";
			String UUSER     = uuser;
			String GVGCD     = gvbcd == null || gvbcd.equals("") ? "***" : gvbcd;
			String GSNAM     = DatasetUtility.getString(dsHeader, 0, "GSNAM"    ); if (GSNAM     == null) GSNAM     = "";  //ksk
			String VKBUR     = vkbur;																																											 //ksk
			String ZRQSEQ_DEL= DatasetUtility.getString(dsHeader, 0, "ZRQSEQ_DEL"    ); if (ZRQSEQ_DEL     == null) ZRQSEQ_DEL     = "";
			String EMAIL_DEPT    = DatasetUtility.getString(dsHeader, 0, "EMAIL_DEPT"   ); if (EMAIL_DEPT    == null) EMAIL_DEPT    = "";
			String LIFNR 	= DatasetUtility.getString(dsHeader, 0, "LIFNR"   ); if (LIFNR    == null) LIFNR    = "";
			
			NOREST = NOREST.equals("1") ? "Y" : "";
			REST01 = REST01.equals("1") ? "Y" : "";
			REST02 = REST02.equals("1") ? "Y" : "";
			REST03 = REST03.equals("1") ? "Y" : "";
			DELV01 = DELV01.equals("1") ? "Y" : "";
			DELV02 = DELV02.equals("1") ? "Y" : "";
			DELV03 = DELV03.equals("1") ? "Y" : "";
			ADDTUSE01 = ADDTUSE01.equals("1") ? "Y" : "";
			ADDTUSE02 = ADDTUSE02.equals("1") ? "Y" : "";			
			
			Ses0451 param = new Ses0451();
			
			param.setMandt    (MANDT   ); // SAP CLIENT
			param.setZrqseq   (ZRQSEQ  );
			param.setZrqdat   (ZRQDAT  );
			param.setDsngbn   (DSNGBN  ); // S : Ç¥ï¿½ï¿½  D: ï¿½ï¿½Ç¥ï¿½ï¿½  I: ï¿½ï¿½ï¿½×¸ï¿½ï¿½ï¿½
			param.setZrqid    (ZRQID   );
			param.setZrqtel   (ZRQTEL  );
			param.setZrqcel   (ZRQCEL  );
			param.setZrqcn    (ZRQCN   );
			param.setZrsrlt   (ZRSRLT  );
			param.setZrqstat  (ZRQSTAT );
			param.setKunnr_ds (KUNNR_DS);
			param.setQtso_no  (QTSO_NO );
			param.setQtser    (QTSER   );
			param.setQtseq    (QTSEQ   );
			param.setHogi     (HOGI    );
			param.setDlvdat   (DLVDAT  );
			param.setFindat   (FINDAT  );
			param.setMetr_2nd(METR_2ND);
			param.setAtyp_2nd(ATYP_2ND);
			param.setAman_2nd(AMAN_2ND);
			param.setAqty_2nd(AQTY_2ND);
			param.setAspd_2nd(ASPD_2ND);
			param.setAflr_2nd(AFLR_2ND);
			param.setAuse_2nd(AUSE_2ND);
			param.setAdoor_2nd(ADOOR_2ND);
			param.setCeil_2nd (CEIL_2ND);
			param.setWall_2nd (WALL_2ND);
			param.setFlor_2nd (FLOR_2ND);
			param.setHdrl_2nd (HDRL_2ND);
			param.setOper_2nd (OPER_2ND);
			param.setDoor_2nd (DOOR_2ND);
			param.setPoid_2nd (POID_2ND);
			param.setHatc_2nd (HATC_2ND);
			param.setJamb_2nd (JAMB_2ND);
			param.setHbtn_2nd (HBTN_2ND);
			param.setHltn_2nd (HLTN_2ND);
			param.setExpo_2nd (EXPO_2ND);
			param.setMetr_3nd(METR_3ND);
			param.setAtyp_3nd(ATYP_3ND);
			param.setAman_3nd(AMAN_3ND);
			param.setAqty_3nd(AQTY_3ND);
			param.setAspd_3nd(ASPD_3ND);
			param.setAflr_3nd(AFLR_3ND);
			param.setAuse_3nd(AUSE_3ND);
			param.setAdoor_3nd(ADOOR_3ND);
			param.setCeil_3nd (CEIL_3ND);
			param.setWall_3nd (WALL_3ND);
			param.setFlor_3nd (FLOR_3ND);
			param.setHdrl_3nd (HDRL_3ND);
			param.setOper_3nd (OPER_3ND);
			param.setDoor_3nd (DOOR_3ND);
			param.setPoid_3nd (POID_3ND);
			param.setHatc_3nd (HATC_3ND);
			param.setJamb_3nd (JAMB_3ND);
			param.setHbtn_3nd (HBTN_3ND);
			param.setHltn_3nd (HLTN_3ND);
			param.setExpo_3nd (EXPO_3ND);
			param.setNorest   (NOREST);  
			param.setRest01   (REST01);  
			param.setRest02   (REST02);  
			param.setRest03   (REST03);  
			param.setDelv01   (DELV01);  
			param.setDelv02   (DELV02);  
			param.setDelv03   (DELV03);  
			param.setAddtuse01   (ADDTUSE01);
			param.setAddtuse02   (ADDTUSE02);			
			param.setEx_kunnr (EX_KUNNR);
			param.setInter_amt(INTER_AMT);
			param.setRq_sa    (RQ_SA);    
			param.setRq_tb    (RQ_TB);    
			param.setRq_db    (RQ_DB);    
			param.setDs_tb    (DS_TB);    
			param.setDs_db    (DS_DB);    
			param.setUuser    (UUSER);
			param.setGvgcd    (GVGCD);
			param.setGsnam    (GSNAM);    //ksk      
			param.setVkbur    (VKBUR);    //ksk
			param.setZrqseq_del(ZRQSEQ_DEL);
			param.setEmail_dept(EMAIL_DEPT);  
			param.setLifnr(LIFNR);
			
			List<Ses0451> listFile = new ArrayList<Ses0451>();
			
			for ( int i = 0 ; i < dsFile.getRowCount() ; i++ ) {
				String fMANDT    = DatasetUtility.getString(dsFile, i, "MANDT"   ); if (fMANDT    == null) fMANDT    = "";
				String fZRQSEQ   = DatasetUtility.getString(dsFile, i, "ZRQSEQ"  ); if (fZRQSEQ   == null) fZRQSEQ   = "";
				String fZATTSEQ  = DatasetUtility.getString(dsFile, i, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";
				String fZRQSTAT  = DatasetUtility.getString(dsFile, i, "ZRQSTAT" ); if (fZRQSTAT  == null) fZRQSTAT  = "";
				String fZATGBN   = DatasetUtility.getString(dsFile, i, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
				String fZATTPATH = DatasetUtility.getString(dsFile, i, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
				String fZATTFN   = DatasetUtility.getString(dsFile, i, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
				String fFLAG     = DatasetUtility.getString(dsFile, i, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
				String fUUSER    = uuser;
				
				Ses0451 paramFile = new Ses0451();
				
				paramFile.setMandt(fMANDT)  ;
				paramFile.setZrqseq(fZRQSEQ)  ;
				paramFile.setZattseq(fZATTSEQ) ;
				paramFile.setZrqstat(fZRQSTAT) ;
				paramFile.setZatgbn(fZATGBN)  ;
				paramFile.setZattpath(fZATTPATH);
				paramFile.setZattfn(fZATTFN)  ;
				paramFile.setFlag(fFLAG)    ;
				paramFile.setUuser(fUUSER)  ;
				
				listFile.add(paramFile);
			}	
				
			// ï¿½ï¿½ï¿½ï¿½
			String strNewZrqSeq = service.save(param, listFile);
			
			dsHeader.setColumn(0, "ZRQSEQ", strNewZrqSeq);
			
			resultVO.addDataset(dsHeader);
			resultVO.addDataset(dsFile);			
			
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	
	/**
	 * ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ç·Ú¿ï¿½Ã» ï¿½ï¿½ï¿½ï¿½/ï¿½Ý·ï¿½
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveZrqstat")
	public View Ses0451SaveZrqstat(MipParameterVO paramVO, Model model) {
		
		// get Input Dataset
		Dataset dsHeader = paramVO.getDataset("ds_header");
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String ZRQSTAT  = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT" ); if (ZRQSTAT == null) ZRQSTAT  = "";
			String RQ_SA    = DatasetUtility.getString(dsHeader, 0, "RQ_SA" );   if (RQ_SA   == null) RQ_SA    = "";
			String RQ_TB    = DatasetUtility.getString(dsHeader, 0, "RQ_TB" );   if (RQ_TB   == null) RQ_TB    = "";
			String RQ_DB    = DatasetUtility.getString(dsHeader, 0, "RQ_DB" );   if (RQ_DB   == null) RQ_DB    = "";
			String DS_TB    = DatasetUtility.getString(dsHeader, 0, "DS_TB" );   if (DS_TB   == null) DS_TB    = "";
			String DS_DB    = DatasetUtility.getString(dsHeader, 0, "DS_DB" );   if (DS_DB   == null) DS_DB    = "";
			String KUNNR_DS = DatasetUtility.getString(dsHeader, 0, "KUNNR_DS"); if (KUNNR_DS== null) KUNNR_DS = "";
			String ZRSRLT   = DatasetUtility.getString(dsHeader, 0, "ZRSRLT"  ); if (ZRSRLT  == null) ZRSRLT   = "";
			String DSNGBN   = DatasetUtility.getString(dsHeader, 0, "DSNGBN"  ); if (DSNGBN  == null) DSNGBN   = "";
			String FINDAT   = DatasetUtility.getString(dsHeader, 0, "FINDAT"  ); if (FINDAT  == null) FINDAT   = "";
			String DFINDAT  = DatasetUtility.getString(dsHeader, 0, "DFINDAT"  ); if (DFINDAT  == null) DFINDAT   = "";
			String LIFNR  = DatasetUtility.getString(dsHeader, 0, "LIFNR"  ); if (LIFNR  == null) DFINDAT   = "";
			
			Ses0451 param = new Ses0451();
			
			param.setMandt   (mandt   ); // SAP CLIENT
			param.setZrqseq  (ZRQSEQ  );
			param.setZrqstat (ZRQSTAT );
			param.setRq_sa   (RQ_SA   );
			param.setRq_tb   (RQ_TB   );
			param.setRq_db   (RQ_DB   );
			param.setDs_tb   (DS_TB   );
			param.setDs_db   (DS_DB   );
			param.setKunnr_ds(KUNNR_DS);
			param.setZrsrlt  (ZRSRLT  );
			param.setUuser   (uuser   );
			param.setDsngbn  (DSNGBN  );
			param.setFindat  (FINDAT  );
			param.setDfindat (DFINDAT );
			param.setLifnr   (LIFNR   );
			// ï¿½ï¿½ï¿½ï¿½
			service.saveZrqstat(param);
			
			resultVO.addDataset(dsHeader);
			
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}
	
	
	/**
	 * ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ç·Ú¿ï¿½Ã» ï¿½Ï·ï¿½Ã³ï¿½ï¿½
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveApproval")
	public View Ses0451SaveApproval(MipParameterVO paramVO, Model model) {

		// get Input Dataset
		Dataset dsHeader = paramVO.getDataset("ds_header");
		Dataset dsFile    = paramVO.getDataset("ds_file");			
		
		// get Variable
		String mandt = paramVO.getVariable("G_MANDT");			// SAP CLIENT
		String uuser = paramVO.getVariable("G_USER_ID");		// G_USER_NAME
		
		// define Error Dataset
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
		
		try {
			String ZRQSEQ   = DatasetUtility.getString(dsHeader, 0, "ZRQSEQ"  ); if (ZRQSEQ  == null) ZRQSEQ   = "";
			String ZRQSTAT  = DatasetUtility.getString(dsHeader, 0, "ZRQSTAT" ); if (ZRQSTAT == null) ZRQSTAT  = "";
			String RQ_SA    = DatasetUtility.getString(dsHeader, 0, "RQ_SA" );   if (RQ_SA   == null) RQ_SA    = "";
			String RQ_TB    = DatasetUtility.getString(dsHeader, 0, "RQ_TB" );   if (RQ_TB   == null) RQ_TB    = "";
			String RQ_DB    = DatasetUtility.getString(dsHeader, 0, "RQ_DB" );   if (RQ_DB   == null) RQ_DB    = "";
			String DS_TB    = DatasetUtility.getString(dsHeader, 0, "DS_TB" );   if (DS_TB   == null) DS_TB    = "";
			String DS_DB    = DatasetUtility.getString(dsHeader, 0, "DS_DB" );   if (DS_DB   == null) DS_DB    = "";
			String KUNNR_DS = DatasetUtility.getString(dsHeader, 0, "KUNNR_DS"); if (KUNNR_DS== null) KUNNR_DS = "";
			String ZRSRLT   = DatasetUtility.getString(dsHeader, 0, "ZRSRLT"  ); if (ZRSRLT  == null) ZRSRLT   = "";
			String FINDAT   = DatasetUtility.getString(dsHeader, 0, "FINDAT"  ); if (FINDAT  == null) FINDAT   = "";
			String DFINDAT  = DatasetUtility.getString(dsHeader, 0, "DFINDAT"  ); if (DFINDAT  == null) DFINDAT   = "";
			
			Ses0451 param = new Ses0451();
			
			param.setMandt   (mandt   ); // SAP CLIENT
			param.setZrqseq  (ZRQSEQ  );
			param.setZrqstat (ZRQSTAT );
			param.setRq_sa   (RQ_SA   );
			param.setRq_tb   (RQ_TB   );
			param.setRq_db   (RQ_DB   );
			param.setDs_tb   (DS_TB   );
			param.setDs_db   (DS_DB   );
			param.setKunnr_ds(KUNNR_DS);
			param.setZrsrlt  (ZRSRLT  );
			param.setFindat  (FINDAT  ); 
			param.setUuser   (uuser   );
			param.setDfindat (DFINDAT  );

			List<Ses0451> listFile = new ArrayList<Ses0451>();
			
			for ( int i = 0 ; i < dsFile.getRowCount() ; i++ ) {
				String fMANDT    = DatasetUtility.getString(dsFile, i, "MANDT"   ); if (fMANDT    == null) fMANDT    = "";
				String fZRQSEQ   = DatasetUtility.getString(dsFile, i, "ZRQSEQ"  ); if (fZRQSEQ   == null) fZRQSEQ   = "";
				String fZATTSEQ  = DatasetUtility.getString(dsFile, i, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";
				String fZRQSTAT  = DatasetUtility.getString(dsFile, i, "ZRQSTAT" ); if (fZRQSTAT  == null) fZRQSTAT  = "";
				String fZATGBN   = DatasetUtility.getString(dsFile, i, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
				String fZATTPATH = DatasetUtility.getString(dsFile, i, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
				String fZATTFN   = DatasetUtility.getString(dsFile, i, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
				String fFLAG     = DatasetUtility.getString(dsFile, i, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
				String fUUSER    = uuser;
				
				Ses0451 paramFile = new Ses0451();
				
				paramFile.setMandt(fMANDT)  ;
				paramFile.setZrqseq(fZRQSEQ)  ;
				paramFile.setZattseq(fZATTSEQ) ;
				paramFile.setZrqstat(fZRQSTAT) ;
				paramFile.setZatgbn(fZATGBN)  ;
				paramFile.setZattpath(fZATTPATH);
				paramFile.setZattfn(fZATTFN)  ;
				paramFile.setFlag(fFLAG)    ;
				paramFile.setUuser(fUUSER)  ;
				
				listFile.add(paramFile);
			}
			
			// ï¿½ï¿½ï¿½ï¿½
			service.saveApproval(param, listFile);
			
			resultVO.addDataset(dsHeader);
			resultVO.addDataset(dsFile);			
			
		} catch(Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		model.addAttribute("resultVO", resultVO);
		return view;
	}	
}
