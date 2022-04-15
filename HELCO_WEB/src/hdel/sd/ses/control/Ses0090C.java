package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0090;
import hdel.sd.ses.domain.Ses0090ParamVO;
import hdel.sd.ses.service.Ses0090S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0090")
public class Ses0090C {

	private final String ZRQFLG_1 = "1";	// 견적요청 - 국내
	private final String ZRQFLG_3 = "3";	// 견적원가 요청 - 해외대리점

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0090S service;
	
	@RequestMapping(value="header")
	public View Ses0090FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_header");	// UI로 return할 out dataset  
		 
		try { 
			Ses0090ParamVO param = new Ses0090ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrqtdat   (DatasetUtility.getString(dsCond,"FR_QTDAT"  ));
			param.setToqtdat  (DatasetUtility.getString(dsCond,"TO_QTDAT"  ));
			param.setFrzestdat (DatasetUtility.getString(dsCond,"FR_ZESTDAT"));
			param.setTozestdat(DatasetUtility.getString(dsCond,"TO_ZESTDAT"));
			param.setVkbur    (DatasetUtility.getString(dsCond,"VKBUR"       ));
			param.setVkgrp    (DatasetUtility.getString(dsCond,"VKGRP"       ));
			param.setZapflg  (DatasetUtility.getString(dsCond,"ZAPFLG"    ));
			param.setZkunnr  (DatasetUtility.getString(dsCond,"SMAN"        ));
			param.setZrqflg(ZRQFLG_1);
	
			List<Ses0090> list = service.searchHeader(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if       (dsList.getColumnID(iCol).equals("CHECK"))             dsList.setColumn(iRow, iCol, list.get(iRow).getCheck());
					else if (dsList.getColumnID(iCol).equals("MANDT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("QTNUM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtnum());
					else if (dsList.getColumnID(iCol).equals("QTSER"))               dsList.setColumn(iRow, iCol, list.get(iRow).getQtser()); 
					else if (dsList.getColumnID(iCol).equals("ZRQSEQ"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqseq()); 
					else if (dsList.getColumnID(iCol).equals("ZRQDAT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqdat()); 
					else if (dsList.getColumnID(iCol).equals("ZAPFLG"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZapflg());
					else if (dsList.getColumnID(iCol).equals("ZAPDAT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZapdat());
					else if (dsList.getColumnID(iCol).equals("ZRQCN"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqcn());
					else if (dsList.getColumnID(iCol).equals("ZRTRSN"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrtrsn());
					else if (dsList.getColumnID(iCol).equals("QTDAT"))              dsList.setColumn(iRow, iCol, list.get(iRow).getQtdat()); 
					else if (dsList.getColumnID(iCol).equals("SPART"))               dsList.setColumn(iRow, iCol, list.get(iRow).getSpart()); 
					else if (dsList.getColumnID(iCol).equals("QTGBN"))              dsList.setColumn(iRow, iCol, list.get(iRow).getQtgbn()); 
					else if (dsList.getColumnID(iCol).equals("ZBDTYP"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZbdtyp()); 
					else if (dsList.getColumnID(iCol).equals("ZPRGFLG"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZprgflg()); 
					else if (dsList.getColumnID(iCol).equals("VKBUR"))              dsList.setColumn(iRow, iCol, list.get(iRow).getVkbur()); 
					else if (dsList.getColumnID(iCol).equals("VKGRP"))               dsList.setColumn(iRow, iCol, list.get(iRow).getVkgrp()); 
					else if (dsList.getColumnID(iCol).equals("ZKUNNR"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZkunnr()); 
					else if (dsList.getColumnID(iCol).equals("ZAGNT"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZagnt()); 
					else if (dsList.getColumnID(iCol).equals("KUNNR"))              dsList.setColumn(iRow, iCol, list.get(iRow).getKunnr()); 
					else if (dsList.getColumnID(iCol).equals("ZCST"))                 dsList.setColumn(iRow, iCol, list.get(iRow).getZcst()); 
					else if (dsList.getColumnID(iCol).equals("ZGNM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZgnm()); 
					else if (dsList.getColumnID(iCol).equals("ZDEV"))                dsList.setColumn(iRow, iCol, list.get(iRow).getZdev()); 
					else if (dsList.getColumnID(iCol).equals("GSNAM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getGsnam()); 
					else if (dsList.getColumnID(iCol).equals("ZREGN"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZregn()); 
					else if (dsList.getColumnID(iCol).equals("ZPID"))                 dsList.setColumn(iRow, iCol, list.get(iRow).getZpid()); 
					else if (dsList.getColumnID(iCol).equals("ZTEL"))                 dsList.setColumn(iRow, iCol, list.get(iRow).getZtel()); 
					else if (dsList.getColumnID(iCol).equals("ZADDR_ZPOST"))     dsList.setColumn(iRow, iCol, list.get(iRow).getZaddr_zpost()); 
					else if (dsList.getColumnID(iCol).equals("ZADDR_ADR1"))      dsList.setColumn(iRow, iCol, list.get(iRow).getZaddr_adr1()); 
					else if (dsList.getColumnID(iCol).equals("ZADDR_ADR2"))      dsList.setColumn(iRow, iCol, list.get(iRow).getZaddr_adr2()); 
					else if (dsList.getColumnID(iCol).equals("SOABLE"))             dsList.setColumn(iRow, iCol, list.get(iRow).getSoable()); 
					else if (dsList.getColumnID(iCol).equals("ZESTDAT"))           dsList.setColumn(iRow, iCol, list.get(iRow).getZestdat()); 
					else if (dsList.getColumnID(iCol).equals("DELDAT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getDeldat()); 
					else if (dsList.getColumnID(iCol).equals("ZDVMTS"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZdvmts()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_ZNUMBER")) dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_znumber()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_ZCOST"))     dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_zcost()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_ZEAM"))      dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_zeam()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_SHANG"))    dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_shang()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_PPRT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_pprt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_PPCT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_ppct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_PPCD"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_ppcd()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_1STRT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_1strt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_1STCT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_1stct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_1STCD"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_1stcd()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_2STRT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_2strt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_2STCT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_2stct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_2STCD"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_2stcd()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_RMRT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_rmrt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_RMCT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_rmct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_RMCD"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_rmcd()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK1"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk1()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK2"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk2()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK3"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk3()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK4"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk4()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK5"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk5()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK6"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk6()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK7"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk7()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK8"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk8()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK9"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk9()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK10"))          dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk10());
					else if (dsList.getColumnID(iCol).equals("WAERK"))          dsList.setColumn(iRow, iCol, list.get(iRow).getWaerk());
					else if (dsList.getColumnID(iCol).equals("VBELN"))          dsList.setColumn(iRow, iCol, list.get(iRow).getVbeln());
					else if (dsList.getColumnID(iCol).equals("INCO"))          dsList.setColumn(iRow, iCol, list.get(iRow).getInco());
					else if (dsList.getColumnID(iCol).equals("INCO2"))          dsList.setColumn(iRow, iCol, list.get(iRow).getInco2());
				}
			} 
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			
//			System.out.println("============== ds_header.getRowCount() " + dsList.getRowCount());
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	@RequestMapping(value="detail")
	public View Ses0090DtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_detail");	// UI로 return할 out dataset  
		 
		try { 
			Ses0090ParamVO param = new Ses0090ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			String qtnum = DatasetUtility.getString(dsCond, "QTNUM");
			String qtser   = DatasetUtility.getString(dsCond, "QTSER");

			if (qtnum == null) qtnum = "";
			if (qtser   == null) qtser   = "0";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setQtnum(qtnum);
			param.setQtser  (qtser  );

			//--------------------------------------------------------------------------------------------
			// INPUT PARAM INFO     		
//			System.out.print("\n@@@ paramVO.G_MANDT===>"+paramVO.getVariable("G_MANDT") 	     +"\n");  
//			System.out.print("\n@@@ dsCond.QTNUM 	 ===>"+DatasetUtility.getString(dsCond,"QTNUM")+"\n");
//			System.out.print("\n@@@ dsCond.QTSER	     ===>"+DatasetUtility.getString(dsCond,"QTSER")	+"\n");
			//--------------------------------------------------------------------------------------------
			
			List<Ses0090> list = service.searchDetail(param);  	// 서비스CALL
			dsList.deleteAll();
			
			for (int i=0;i<list.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "MANDT"    , list.get(i).getMandt());
				dsList.setColumn(i, "QTNUM"    , list.get(i).getQtnum());
				dsList.setColumn(i, "QTSER"      , list.get(i).getQtser()); 
				dsList.setColumn(i, "QTSEQ"      , list.get(i).getQtseq()); 
				dsList.setColumn(i, "SHANGH"   , list.get(i).getShangh()); 
				dsList.setColumn(i, "MATNR"    , list.get(i).getMatnr()); 
				dsList.setColumn(i, "SPEC"        , list.get(i).getSpec()); 
				dsList.setColumn(i, "GTYPE"      , list.get(i).getGtype()); 
				dsList.setColumn(i, "TYPE1"      , list.get(i).getType1()); 
				dsList.setColumn(i, "TYPE2"      , list.get(i).getType2()); 
				dsList.setColumn(i, "TYPE3"      , list.get(i).getType3()); 
				dsList.setColumn(i, "ZNUMBER", list.get(i).getZnumber());
				dsList.setColumn(i, "ZACAPA",  list.get(i).getZacapa());
				dsList.setColumn(i, "ZUSE"       , list.get(i).getZuse());
				dsList.setColumn(i, "ZOPN"      , list.get(i).getZopn());
				dsList.setColumn(i, "ZLEN"       , list.get(i).getZlen());
				dsList.setColumn(i, "ZUAM"     , list.get(i).getZuam());
				dsList.setColumn(i, "ZCOST"     , list.get(i).getZcost());
				dsList.setColumn(i, "ZEAM"      , list.get(i).getZeam());
				dsList.setColumn(i, "SHANG"    , list.get(i).getShang());
				dsList.setColumn(i, "ZRMK"      , list.get(i).getZrmk());
			}
			MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
			resultVO.addDataset(dsList); 

//			System.out.println("============== ds_detail.getRowCount() " + dsList.getRowCount());
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	/*------------------------------------------------------------
	 *  견적승인
	 ------------------------------------------------------------*/
	@RequestMapping(value="approval")
	public View Ses0090approvalView(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.approval(paramVO, model, session);

			} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
//		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}

	/*------------------------------------------------------------
	 *  견적반려
	 ------------------------------------------------------------*/
	@RequestMapping(value="reject")
	public View Ses0090rejectView(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		
		// DAO Create
		service.createDao(session);		
		
		try {
			
			// 저장 호출
			service.reject(paramVO, model, session);

		} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
//		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}

	/* 해외 대리점에서 처리하는 Method */

	@RequestMapping(value="findAgentHeader")
	public View Ses0090EFindAgentHeader(MipParameterVO paramVO, Model model) {

		Dataset dsCond   = paramVO.getDataset("ds_cond");
		Dataset dsList   = paramVO.getDataset("ds_header");	// UI로 return할 out dataset
		Dataset ds_kunnr = paramVO.getDataset("ds_kunnr");	// UI로 return할 out dataset

		// 오류정보 DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		 
		try { 
			Ses0090ParamVO param = new Ses0090ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrqtdat(DatasetUtility.getString(dsCond,"FR_QTDAT"));
			param.setToqtdat(DatasetUtility.getString(dsCond,"TO_QTDAT"));
			param.setVkbur(DatasetUtility.getString(dsCond,"VKBUR"));
			param.setVkgrp(DatasetUtility.getString(dsCond,"VKGRP"));
			param.setZapflg(DatasetUtility.getString(dsCond,"ZAPFLG"));
			param.setKunnr(DatasetUtility.getString(dsCond,"KUNNR"));
			param.setZrqflg(ZRQFLG_3);

			// 고객정보 조회 (출력용)
			List<Ses0090> listKunnr = service.searchKunnr(param);   				// 서비스CALL
			ds_kunnr.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow = 0; iRow < listKunnr.size(); iRow++) {

				ds_kunnr.appendRow(); 	// 행추가

				for (int iCol = 0; iCol < ds_kunnr.getColumnCount(); iCol++)	{
					if      (ds_kunnr.getColumnID(iCol).equals("KUNNR"))	ds_kunnr.setColumn(iRow, iCol, listKunnr.get(iRow).getKunnr());
					else if (ds_kunnr.getColumnID(iCol).equals("LAND1"))    ds_kunnr.setColumn(iRow, iCol, listKunnr.get(iRow).getLand1());
					else if (ds_kunnr.getColumnID(iCol).equals("NAME1"))    ds_kunnr.setColumn(iRow, iCol, listKunnr.get(iRow).getName1());
					else if (ds_kunnr.getColumnID(iCol).equals("TELF1"))    ds_kunnr.setColumn(iRow, iCol, listKunnr.get(iRow).getTelf1());
					else if (ds_kunnr.getColumnID(iCol).equals("TELFX"))    ds_kunnr.setColumn(iRow, iCol, listKunnr.get(iRow).getTelfx());
				}
			}

			// 견적가승인요청 헤더정보 조회
			List<Ses0090> list = service.searchAgentHeader(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if      (dsList.getColumnID(iCol).equals("CHECK"))             dsList.setColumn(iRow, iCol, list.get(iRow).getCheck());
					else if (dsList.getColumnID(iCol).equals("MANDT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("QTNUM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtnum());
					else if (dsList.getColumnID(iCol).equals("QTSER"))               dsList.setColumn(iRow, iCol, list.get(iRow).getQtser()); 
					else if (dsList.getColumnID(iCol).equals("ZRQSEQ"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqseq()); 
					else if (dsList.getColumnID(iCol).equals("ZRQDAT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqdat()); 
					else if (dsList.getColumnID(iCol).equals("ZAPFLG"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZapflg());
					else if (dsList.getColumnID(iCol).equals("ZAPDAT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZapdat());
					else if (dsList.getColumnID(iCol).equals("ZRQCN"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqcn());
					else if (dsList.getColumnID(iCol).equals("ZRTRSN"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrtrsn());
					else if (dsList.getColumnID(iCol).equals("QTDAT"))              dsList.setColumn(iRow, iCol, list.get(iRow).getQtdat()); 
					else if (dsList.getColumnID(iCol).equals("SPART"))               dsList.setColumn(iRow, iCol, list.get(iRow).getSpart()); 
					else if (dsList.getColumnID(iCol).equals("QTGBN"))              dsList.setColumn(iRow, iCol, list.get(iRow).getQtgbn()); 
					else if (dsList.getColumnID(iCol).equals("ZBDTYP"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZbdtyp()); 
					else if (dsList.getColumnID(iCol).equals("ZPRGFLG"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZprgflg()); 
					else if (dsList.getColumnID(iCol).equals("VKBUR"))              dsList.setColumn(iRow, iCol, list.get(iRow).getVkbur()); 
					else if (dsList.getColumnID(iCol).equals("VKGRP"))               dsList.setColumn(iRow, iCol, list.get(iRow).getVkgrp()); 
					else if (dsList.getColumnID(iCol).equals("ZKUNNR"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZkunnr()); 
					else if (dsList.getColumnID(iCol).equals("ZAGNT"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZagnt()); 
					else if (dsList.getColumnID(iCol).equals("KUNNR"))              dsList.setColumn(iRow, iCol, list.get(iRow).getKunnr()); 
					else if (dsList.getColumnID(iCol).equals("ZCST"))                 dsList.setColumn(iRow, iCol, list.get(iRow).getZcst()); 
					else if (dsList.getColumnID(iCol).equals("ZGNM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZgnm()); 
					else if (dsList.getColumnID(iCol).equals("ZDEV"))                dsList.setColumn(iRow, iCol, list.get(iRow).getZdev()); 
					else if (dsList.getColumnID(iCol).equals("GSNAM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getGsnam()); 
					else if (dsList.getColumnID(iCol).equals("ZREGN"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZregn()); 
					else if (dsList.getColumnID(iCol).equals("ZPID"))                 dsList.setColumn(iRow, iCol, list.get(iRow).getZpid()); 
					else if (dsList.getColumnID(iCol).equals("ZTEL"))                 dsList.setColumn(iRow, iCol, list.get(iRow).getZtel()); 
					else if (dsList.getColumnID(iCol).equals("ZADDR_ZPOST"))     dsList.setColumn(iRow, iCol, list.get(iRow).getZaddr_zpost()); 
					else if (dsList.getColumnID(iCol).equals("ZADDR_ADR1"))      dsList.setColumn(iRow, iCol, list.get(iRow).getZaddr_adr1()); 
					else if (dsList.getColumnID(iCol).equals("ZADDR_ADR2"))      dsList.setColumn(iRow, iCol, list.get(iRow).getZaddr_adr2()); 
					else if (dsList.getColumnID(iCol).equals("SOABLE"))             dsList.setColumn(iRow, iCol, list.get(iRow).getSoable()); 
					else if (dsList.getColumnID(iCol).equals("ZESTDAT"))           dsList.setColumn(iRow, iCol, list.get(iRow).getZestdat()); 
					else if (dsList.getColumnID(iCol).equals("DELDAT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getDeldat()); 
					else if (dsList.getColumnID(iCol).equals("ZDVMTS"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZdvmts()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_ZNUMBER")) dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_znumber()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_ZCOST"))     dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_zcost()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_ZEAM"))      dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_zeam()); 
					else if (dsList.getColumnID(iCol).equals("ZSUM_SHANG"))    dsList.setColumn(iRow, iCol, list.get(iRow).getZsum_shang()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_PPRT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_pprt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_PPCT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_ppct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_PPCD"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_ppcd()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_1STRT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_1strt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_1STCT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_1stct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_1STCD"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_1stcd()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_2STRT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_2strt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_2STCT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_2stct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_2STCD"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_2stcd()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_RMRT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_rmrt()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_RMCT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_rmct()); 
					else if (dsList.getColumnID(iCol).equals("ZICIF_RMCD"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZicif_rmcd()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK1"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk1()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK2"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk2()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK3"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk3()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK4"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk4()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK5"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk5()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK6"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk6()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK7"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk7()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK8"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk8()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK9"))            dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk9()); 
					else if (dsList.getColumnID(iCol).equals("SPRMK10"))          dsList.setColumn(iRow, iCol, list.get(iRow).getSprmk10());
					else if (dsList.getColumnID(iCol).equals("WAERK"))            dsList.setColumn(iRow, iCol, list.get(iRow).getWaerk());
					else if (dsList.getColumnID(iCol).equals("VBELN"))            dsList.setColumn(iRow, iCol, list.get(iRow).getVbeln());
					else if (dsList.getColumnID(iCol).equals("INCO"))             dsList.setColumn(iRow, iCol, list.get(iRow).getInco());
					else if (dsList.getColumnID(iCol).equals("INCO2"))          dsList.setColumn(iRow, iCol, list.get(iRow).getInco2());
				}
			} 
		} catch (Exception e) { 
			e.printStackTrace();  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds_kunnr);
		resultVO.addDataset(dsList);

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);	
		return view;
	}

	@RequestMapping(value="findAgentDetail")
	public View Ses0090EFindAgentDetail(MipParameterVO paramVO, Model model) {

		Dataset dsCond  = paramVO.getDataset("ds_cond");
		Dataset dsList  = paramVO.getDataset("ds_detail");	// UI로 return할 out dataset
		Dataset ds_spec = paramVO.getDataset("ds_spec");	// 사양상제정보 조회 출력용

		// 오류정보 DATASET GET
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		 
		try { 
			Ses0090ParamVO param = new Ses0090ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			String qtnum = DatasetUtility.getString(dsCond, "QTNUM");
			String qtser = DatasetUtility.getString(dsCond, "QTSER");
			String spras = "3";

			if (qtnum == null) qtnum = "";
			if (qtser == null) qtser = "1";

			if (!"ko".equals( paramVO.getVariable("G_LANG") ) )	{
				spras = "E";
			}
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setQtnum(qtnum);
			param.setQtser(qtser);
			param.setSpras(spras);	// SPEC조회 시 활용

			// SPEC 조회
			List<Ses0090> listSpec = service.searchSpecList(param);  	// 서비스CALL
			ds_spec.deleteAll();
			
			for (int i=0; i < listSpec.size(); i++) 	{
				ds_spec.appendRow(); 	// 행추가
				ds_spec.setColumn(i, "MANDT",   listSpec.get(i).getMandt());
				ds_spec.setColumn(i, "QTNUM",   listSpec.get(i).getQtnum());
				ds_spec.setColumn(i, "QTSER",   listSpec.get(i).getQtser());
				ds_spec.setColumn(i, "QTSEQ",   listSpec.get(i).getQtseq());
				ds_spec.setColumn(i, "PRSEQ",   listSpec.get(i).getPrseq());
				ds_spec.setColumn(i, "ATKLA",   listSpec.get(i).getAtkla());
				ds_spec.setColumn(i, "PRH",     listSpec.get(i).getPrh());
				ds_spec.setColumn(i, "PRHNAME", listSpec.get(i).getPrhname());
				ds_spec.setColumn(i, "PRD",     listSpec.get(i).getPrd());
				ds_spec.setColumn(i, "PRDNAME", listSpec.get(i).getPrdname());
				ds_spec.setColumn(i, "GBN",     "");
			}

			// 상세정보 조회
			List<Ses0090> list = service.searchAgentDetail(param);  	// 서비스CALL
			dsList.deleteAll();
			
			for (int i=0;i<list.size();i++) 	{ 			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가

				dsList.setColumn(i, "MANDT"    , list.get(i).getMandt());
				dsList.setColumn(i, "QTNUM"    , list.get(i).getQtnum());
				dsList.setColumn(i, "QTSER"      , list.get(i).getQtser()); 
				dsList.setColumn(i, "QTSEQ"      , list.get(i).getQtseq()); 
				dsList.setColumn(i, "SHANGH"   , list.get(i).getShangh()); 
				dsList.setColumn(i, "MATNR"    , list.get(i).getMatnr());
				dsList.setColumn(i, "ZPRDGBN"    , list.get(i).getZprdgbn()); 
				dsList.setColumn(i, "SPEC"        , list.get(i).getSpec()); 
				dsList.setColumn(i, "GTYPE"      , list.get(i).getGtype()); 
				dsList.setColumn(i, "TYPE1"      , list.get(i).getType1()); 
				dsList.setColumn(i, "TYPE2"      , list.get(i).getType2()); 
				dsList.setColumn(i, "TYPE3"      , list.get(i).getType3()); 
				dsList.setColumn(i, "ZNUMBER", list.get(i).getZnumber());
				dsList.setColumn(i, "ZACAPA",  list.get(i).getZacapa());
				dsList.setColumn(i, "ZUSE"       , list.get(i).getZuse());
				dsList.setColumn(i, "ZOPN"      , list.get(i).getZopn());
				dsList.setColumn(i, "ZLEN"       , list.get(i).getZlen());
				dsList.setColumn(i, "ZUAM"     , list.get(i).getZuam());
				dsList.setColumn(i, "ZCOST"     , list.get(i).getZcost());
				dsList.setColumn(i, "ZEAM"      , list.get(i).getZeam());
				dsList.setColumn(i, "SHANG"    , list.get(i).getShang());
				dsList.setColumn(i, "ZRMK"      , list.get(i).getZrmk());
				dsList.setColumn(i, "OPER"      , list.get(i).getOper());
			}
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}	    
		MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
		resultVO.addDataset(dsList);
		resultVO.addDataset(ds_spec);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);     
		return view;
	}

	/*------------------------------------------------------------
	 *  견적원가 승인
	 ------------------------------------------------------------*/
	@RequestMapping(value="agentApproval")
	public View Ses0090EAgentApproval(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.agentApproval(paramVO);

			} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
//		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}

	/*------------------------------------------------------------
	 *  견적원가 반려
	 ------------------------------------------------------------*/
	@RequestMapping(value="agentReject")
	public View Ses0090EAgentReject(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		
		// DAO Create
		service.createDao(session);		
		
		try {
			
			// 저장 호출
			service.agentReject(paramVO);

		} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
//		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}

}
