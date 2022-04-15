package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0360;
import hdel.sd.ses.domain.Ses0360ParamVO;
import hdel.sd.ses.service.Ses0360S;

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
@RequestMapping("ses0360")
public class Ses0360C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0360S service;
	
	/*------------------------------------------------------------
	 *  물류견적원가 조회
	 ------------------------------------------------------------*/
	@RequestMapping(value="find")
	public View Ses0240Find1View(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_key");
		Dataset ds_list	= paramVO.getDataset("ds_excel");
		Dataset ds_file	= paramVO.getDataset("ds_file");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		
		try { 
			Ses0360ParamVO param = new Ses0360ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			
			String qtnum   = DatasetUtility.getString(dsCond, "QTNUM");
			String qtser   = DatasetUtility.getString(dsCond, "QTSER");
			String qtseq   = DatasetUtility.getString(dsCond, "QTSEQ");
			String lang    = paramVO.getVariable("G_LANG");
			
			if (lang == null || lang == "" ) lang = "ko";

			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setQtnum(qtnum);
			param.setQtser(qtser);
			param.setQtseq(qtseq);
			param.setLang(lang); // 'en' OR 'ko' => 'E', '3'
			
			List<Ses0360> list = service.searchList(param);   			// 서비스CALL
			ds_list.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {
				ds_list.appendRow(); 	// 행추가
				ds_list.setColumn(iRow, "MANDT"		, list.get(iRow).getMandt()		);
				ds_list.setColumn(iRow, "QTNUM"		, list.get(iRow).getQtnum()		);
				ds_list.setColumn(iRow, "QTSER"		, list.get(iRow).getQtser()		);
				ds_list.setColumn(iRow, "QTSEQ"		, list.get(iRow).getQtseq()		);
				ds_list.setColumn(iRow, "COSTZSEQ"	, list.get(iRow).getCostzseq()	);
				ds_list.setColumn(iRow, "CTEXT1"	, list.get(iRow).getCtext1()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "CTEXT2"	, list.get(iRow).getCtext2()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "DTEXT1"	, list.get(iRow).getDtext1()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "DTEXT2"	, list.get(iRow).getDtext2()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "DTEXT3"	, list.get(iRow).getDtext3()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "DTEXT4"	, list.get(iRow).getDtext4()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "SETTING"	, list.get(iRow).getSetting()	);
				ds_list.setColumn(iRow, "QNTY"		, list.get(iRow).getQnty()		);
				ds_list.setColumn(iRow, "ZUAM"		, list.get(iRow).getZuam()		);
				ds_list.setColumn(iRow, "ZCOST"		, list.get(iRow).getZcost()		);
				ds_list.setColumn(iRow, "E_VALUE"	, list.get(iRow).getE_value()	);
				ds_list.setColumn(iRow, "O_RATE"	, list.get(iRow).getO_rate()	);
				ds_list.setColumn(iRow, "ZRMK"		, list.get(iRow).getZrmk()		);
			}
			
			// 2013.02.21 첨부파일 조회 기능 추가
			ds_file.deleteAll();
			List<Ses0360> listFile = service.selectListFile(param);
			for ( int iRow = 0 ; iRow < listFile.size() ; iRow++ ) {
				ds_file.appendRow(); 	// 행추가
				ds_file.setColumn(iRow, "MANDT"		, listFile.get(iRow).getMandt()		);
				ds_file.setColumn(iRow, "QTNUM"		, listFile.get(iRow).getQtnum()		);
				ds_file.setColumn(iRow, "QTSER"		, listFile.get(iRow).getQtser()		);
				ds_file.setColumn(iRow, "QTSEQ"		, listFile.get(iRow).getQtseq()		);
				ds_file.setColumn(iRow, "ZATTSEQ"	, listFile.get(iRow).getZattseq()	);
				ds_file.setColumn(iRow, "ZATGBN"	, listFile.get(iRow).getZatgbn()	);
				ds_file.setColumn(iRow, "ZATTPATH"	, listFile.get(iRow).getZattpath()	);
				ds_file.setColumn(iRow, "ZATTFN"	, listFile.get(iRow).getZattfn()	);
			}
			
			ds_list.setId("ds_excel");
			resultVO.addDataset(ds_list);
			
			ds_file.setId("ds_file");
			resultVO.addDataset(ds_file);
			
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		return view;
	}
	
	/*------------------------------------------------------------
	 *  물류수주원가 조회
	 ------------------------------------------------------------*/
	@RequestMapping(value="find2")
	public View Ses0240Find2View(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_key");
		Dataset ds_list	= paramVO.getDataset("ds_excel2");	
		Dataset ds_file	= paramVO.getDataset("ds_file");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset
		
		// RFC 출력 dataset을 return
		MipResultVO resultVO = new MipResultVO();
		
		try { 
			Ses0360ParamVO param = new Ses0360ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			
			String pspid   = DatasetUtility.getString(dsCond, "PSPID");
			String lang    = paramVO.getVariable("G_LANG");
			
			if (lang == null || lang == "" ) lang = "ko";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setPspid(pspid);
			param.setLang(lang);  // 'en' OR 'ko'
			
			List<Ses0360> list = service.searchList2(param);   			// 서비스CALL
			ds_list.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {
				ds_list.appendRow(); 	// 행추가
				ds_list.setColumn(iRow, "MANDT"		, list.get(iRow).getMandt()		);
				ds_list.setColumn(iRow, "PSPID"		, list.get(iRow).getPspid()		);
				ds_list.setColumn(iRow, "POSID"		, list.get(iRow).getPosid()		);
				ds_list.setColumn(iRow, "COSTZSEQ"	, list.get(iRow).getCostzseq()	);
				ds_list.setColumn(iRow, "CTEXT1"	, list.get(iRow).getCtext1()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "CTEXT2"	, list.get(iRow).getCtext2()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "DTEXT1"	, list.get(iRow).getDtext1()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "DTEXT2"	, list.get(iRow).getDtext2()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "DTEXT3"	, list.get(iRow).getDtext3()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "DTEXT4"	, list.get(iRow).getDtext4()	); // 2013.02.21 Import,Export 양식 통일
				ds_list.setColumn(iRow, "SETTING"	, list.get(iRow).getSetting()	);
				ds_list.setColumn(iRow, "QNTY"		, list.get(iRow).getQnty()		);
				ds_list.setColumn(iRow, "ZUAM"		, list.get(iRow).getZuam()		);
				ds_list.setColumn(iRow, "ZCOST"		, list.get(iRow).getZcost()		);
				ds_list.setColumn(iRow, "ZRMK"		, list.get(iRow).getZrmk()		);
			}
			
			// 2013.02.21 첨부파일 조회 기능 추가
			ds_file.deleteAll();
			List<Ses0360> listFile = service.selectListFile2(param);
			for ( int iRow = 0 ; iRow < listFile.size() ; iRow++ ) {
				ds_file.appendRow(); 	// 행추가
				ds_file.setColumn(iRow, "MANDT"		, listFile.get(iRow).getMandt()		);
				ds_file.setColumn(iRow, "PSPID"		, listFile.get(iRow).getPspid()		);
				ds_file.setColumn(iRow, "POSID"		, listFile.get(iRow).getPosid()		);
				ds_file.setColumn(iRow, "ZATTSEQ"	, listFile.get(iRow).getZattseq()	);
				ds_file.setColumn(iRow, "ZATGBN"	, listFile.get(iRow).getZatgbn()	);
				ds_file.setColumn(iRow, "ZATTPATH"	, listFile.get(iRow).getZattpath()	);
				ds_file.setColumn(iRow, "ZATTFN"	, listFile.get(iRow).getZattfn()	);
			}
			
			ds_list.setId("ds_excel2");
			resultVO.addDataset(ds_list);
			
			ds_file.setId("ds_file");
			resultVO.addDataset(ds_file);
			
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}	    
		
		dsError.setId("ds_error");
		resultVO.addDataset(dsError);
		
		return view;
	}
	
	/*------------------------------------------------------------
	 * 물류견적원가 저장
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Ses0360Save(MipParameterVO paramVO, Model model) {

		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		
		// DAO Create
		service.createDao(session);		

		try {
			service.saveExcel(paramVO, model, session);

		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}	    
		
		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error); 
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}

	/*------------------------------------------------------------
	 *  견적수주원가 저장
	 ------------------------------------------------------------*/
	@RequestMapping(value="save2")
	public View Ses0360Save2(MipParameterVO paramVO, Model model) {

		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		
		// DAO Create
		service.createDao(session);		

		try {
			service.saveExcel2(paramVO, model, session);

		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
		}	    
		
		MipResultVO resultVO = new MipResultVO(); 	// 출력 dataset을 return
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error); 
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
}
