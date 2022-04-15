package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.ses.domain.Ses0240;
import hdel.sd.ses.domain.Ses0240ParamVO;
import hdel.sd.ses.service.Ses0240S;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.biz.BusinessException;
import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0240")
public class Ses0240C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0240S service;
	
	@RequestMapping(value="header")
	public View Ses0240FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_header");	// UI로 return할 out dataset  
		 
		try { 
			Ses0240ParamVO param = new Ses0240ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			String zrqdatfr   = DatasetUtility.getString(dsCond, "ZRQDATFR");
			String zrqdatto   = DatasetUtility.getString(dsCond, "ZRQDATTO");
			String zrqcn   = DatasetUtility.getString(dsCond, "ZRQCN");
			String qtnum   = DatasetUtility.getString(dsCond, "QTNUM");
			String zrqflg   = DatasetUtility.getString(dsCond, "ZRQFLG");
			String docreqno   = DatasetUtility.getString(dsCond, "DOCREQNO");
			String cuser   = DatasetUtility.getString(dsCond, "CUSER");

			param.setZrqdatfr(zrqdatfr);
			param.setZrqdatto(zrqdatto);
			param.setZrqcn(zrqcn);
			param.setQtnum(qtnum);
			param.setZrqflg(zrqflg);
			param.setDocreqno(docreqno);
			param.setCuser(cuser);
			//--------------------------------------------------------------------------------------------
			
			List<Ses0240> list = service.searchHeader(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if (dsList.getColumnID(iCol).equals("MANDT"))             		dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("DOCREQNO"))          dsList.setColumn(iRow, iCol, list.get(iRow).getDocreqno());
					else if (dsList.getColumnID(iCol).equals("ZSEQ"))       		   dsList.setColumn(iRow, iCol, list.get(iRow).getZseq());
					else if (dsList.getColumnID(iCol).equals("ZRQFLG"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZrqflg()); 
					else if (dsList.getColumnID(iCol).equals("ZRQDAT"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZrqdat()); 
					else if (dsList.getColumnID(iCol).equals("ZRQCN"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZrqcn()); 
					else if (dsList.getColumnID(iCol).equals("ZWRITER"))           dsList.setColumn(iRow, iCol, list.get(iRow).getZwriter()); 
					else if (dsList.getColumnID(iCol).equals("ZRMK"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrmk()); 
					else if (dsList.getColumnID(iCol).equals("QTNUM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtnum()); 
					else if (dsList.getColumnID(iCol).equals("ORG_QTNUM"))       dsList.setColumn(iRow, iCol, list.get(iRow).getOrg_qtnum()); 
					else if (dsList.getColumnID(iCol).equals("GSNAM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getGsnam());
					else if (dsList.getColumnID(iCol).equals("QTSER"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtser()); 
					else if (dsList.getColumnID(iCol).equals("CDATE"))             dsList.setColumn(iRow, iCol, list.get(iRow).getCdate()); 
					else if (dsList.getColumnID(iCol).equals("CTIME"))             dsList.setColumn(iRow, iCol, list.get(iRow).getCtime()); 
					else if (dsList.getColumnID(iCol).equals("CUSER"))             dsList.setColumn(iRow, iCol, list.get(iRow).getCuser()); 
					else if (dsList.getColumnID(iCol).equals("UDATE"))             dsList.setColumn(iRow, iCol, list.get(iRow).getUdate()); 
					else if (dsList.getColumnID(iCol).equals("UTIME"))             dsList.setColumn(iRow, iCol, list.get(iRow).getUtime()); 
					else if (dsList.getColumnID(iCol).equals("UUSER"))             dsList.setColumn(iRow, iCol, list.get(iRow).getUuser()); 

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
	public View Ses0240DtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_detail");	// UI로 return할 out dataset  
		 
		try { 
			Ses0240ParamVO param = new Ses0240ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			String mandt = DatasetUtility.getString(dsCond, "MANDT");
			String docreqno   = DatasetUtility.getString(dsCond, "DOCREQNO");
			
			//if (mandt == null) mandt = "";
			if (docreqno   == null) docreqno   = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setDocreqno(docreqno);
			
			List<Ses0240> list = service.searchDetail(param);  	// 서비스CALL
			dsList.deleteAll();
			
			for (int i=0;i<list.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "MANDT"    		, list.get(i).getMandt());
				dsList.setColumn(i, "DOCREQNO"    	, list.get(i).getDocreqno());
				dsList.setColumn(i, "ZSEQ"     	 	, list.get(i).getZseq()); 
				dsList.setColumn(i, "ZRQFLG"      	, list.get(i).getZrqflg()); 
				dsList.setColumn(i, "ZRQDAT"   		, list.get(i).getZrqdat()); 
				dsList.setColumn(i, "ZRQCN"    		, list.get(i).getZrqcn()); 
				dsList.setColumn(i, "ZWRITER"       , list.get(i).getZwriter()); 
				dsList.setColumn(i, "ZRMK"      	, list.get(i).getZrmk()); 
				dsList.setColumn(i, "CDATE"      	, list.get(i).getCdate()); 
				dsList.setColumn(i, "CTIME"      	, list.get(i).getCtime()); 
				dsList.setColumn(i, "CUSER"      	, list.get(i).getCuser()); 
				dsList.setColumn(i, "UDATE"			, list.get(i).getUdate());
				dsList.setColumn(i, "UTIME"       	, list.get(i).getUtime());
				dsList.setColumn(i, "UUSER"      	, list.get(i).getUuser());
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
	
	@RequestMapping(value="fileview")
	public View Ses0240fileView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsDetail	= paramVO.getDataset("ds_detail");	
		Dataset dsList	= paramVO.getDataset("dsContReqAppend");	// UI로 return할 out dataset  
		 
		try { 
			Ses0240ParamVO param = new Ses0240ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			String mandt = DatasetUtility.getString(dsCond, "MANDT");
			String zwgbn   = DatasetUtility.getString(dsCond, "ZWGBN");
			String docreqno   = DatasetUtility.getString(dsCond, "DOCREQNO");
			String zseq   = DatasetUtility.getString(dsCond, "ZSEQ");
			
			//if (mandt == null) mandt = "";
			if (docreqno   == null) docreqno   = "";
			if (zseq   == null) zseq   = "";
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	
			param.setZwgbn(zwgbn);
			param.setDocreqno(docreqno);
			param.setZseq(zseq);
			
			//상세정보 조회
			List<Ses0240> d_list = service.searchDetail(param);  	

			for (int i=0;i<d_list.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsDetail.appendRow(); 	// 행추가
				dsDetail.setColumn(i, "MANDT"    		, d_list.get(i).getMandt());
				dsDetail.setColumn(i, "DOCREQNO"    	, d_list.get(i).getDocreqno());
				dsDetail.setColumn(i, "ZSEQ"     	 		, d_list.get(i).getZseq()); 
				dsDetail.setColumn(i, "ZRQFLG"      		, d_list.get(i).getZrqflg()); 
				dsDetail.setColumn(i, "ZRQDAT"   		, d_list.get(i).getZrqdat()); 
				dsDetail.setColumn(i, "ZRQCN"    		, d_list.get(i).getZrqcn()); 
				dsDetail.setColumn(i, "ZWRITER"          , d_list.get(i).getZwriter()); 
				dsDetail.setColumn(i, "ZRMK"      		, d_list.get(i).getZrmk()); 
				dsDetail.setColumn(i, "CDATE"      		, d_list.get(i).getCdate()); 
				dsDetail.setColumn(i, "CTIME"      		, d_list.get(i).getCtime()); 
				dsDetail.setColumn(i, "CUSER"      		, d_list.get(i).getCuser()); 
				dsDetail.setColumn(i, "UDATE"				, d_list.get(i).getUdate());
				dsDetail.setColumn(i, "UTIME"       		, d_list.get(i).getUtime());
				dsDetail.setColumn(i, "UUSER"      		, d_list.get(i).getUuser());
			}
			
			
			//파일정보 조회
			List<Ses0240> list = service.searchFile(param);  	
			dsList.deleteAll();
			
			for (int i=0;i<list.size();i++) { 			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가
				dsList.setColumn(i, "MANDT"    		, list.get(i).getMandt());
				dsList.setColumn(i, "ZWGBN"       	, list.get(i).getZwgbn());
				dsList.setColumn(i, "DOCREQNO"    	, list.get(i).getDocreqno());
				dsList.setColumn(i, "ZSEQ"     	 	, list.get(i).getZseq()); 
				dsList.setColumn(i, "ZATTSEQ"     	, list.get(i).getZattseq());
				dsList.setColumn(i, "ZATTPATH"     	, list.get(i).getZattpath());
				dsList.setColumn(i, "ZATTFN"     	 	, list.get(i).getZattfn());
				dsList.setColumn(i, "ZATTFN_OR"    	, list.get(i).getZattfn_or());
			}

			MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
			dsDetail.setId("ds_detail"); 
			dsList.setId("dsContReqAppend");
			resultVO.addDataset(dsDetail);
			resultVO.addDataset(dsList);
			model.addAttribute("resultVO", resultVO);   

//			System.out.println("============== fileview.getRowCount() " + dsList.getRowCount());
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}	

	/*------------------------------------------------------------
	 *  도면의뢰 등록(Header)
	 ------------------------------------------------------------*/
	@RequestMapping(value="insertHeader")
	public View Ses0240insertHeader(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_cond 	= paramVO.getDataset("ds_cond");	
		Dataset ds_header 	= paramVO.getDataset("ds_header");	
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		MipResultVO resultVO = new MipResultVO(); 
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			
			Ses0240ParamVO param = new Ses0240ParamVO();
			Ses0240 param2 = new Ses0240();
			
			param.setMandt(paramVO.getVariable("G_MANDT"));	    
			
			param2.setMandt(paramVO.getVariable("G_MANDT"));
			param2.setQtnum(ds_cond.getColumnAsString(0, "QTNUM"));
			
			//견적번호 중복체크
			int cnt = service.findCountQtnum(param2);
			
			if (cnt > 0) {
				ds_error.insertRow(0);
				ds_error.setColumn(0, "ERROR", "ERROR");
				ds_error.setColumn(0, "ERRMSG", "이미 도면접수 중인 견적번호입니다.");
				
				ds_error.setId("ds_error");   
				resultVO.addDataset(ds_error);  	
				model.addAttribute("resultVO", resultVO);   
				
				 throw new BizException("이미 도면접수 중인 견적번호입니다.");
			} else {
			
				// 저장 호출
				String docreqno = service.insertHeader(paramVO, model, session);
	
				param.setDocreqno(docreqno);
				
				List<Ses0240> list = service.searchHeader(param);   			// 서비스CALL
				ds_header.deleteAll();
				
				// 호출결과(list)를 데이타셋(dsList)에 복사
				for (int iRow=0;iRow<list.size();iRow++) {
	
					ds_header.appendRow(); 	// 행추가
	
					for (int iCol=0;iCol<ds_header.getColumnCount();iCol++)	{
						if (ds_header.getColumnID(iCol).equals("MANDT"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getMandt());
						else if (ds_header.getColumnID(iCol).equals("DOCREQNO"))          ds_header.setColumn(iRow, iCol, list.get(iRow).getDocreqno());
						else if (ds_header.getColumnID(iCol).equals("ZSEQ"))       		   ds_header.setColumn(iRow, iCol, list.get(iRow).getZseq());
						else if (ds_header.getColumnID(iCol).equals("ZRQFLG"))            ds_header.setColumn(iRow, iCol, list.get(iRow).getZrqflg()); 
						else if (ds_header.getColumnID(iCol).equals("ZRQDAT"))            ds_header.setColumn(iRow, iCol, list.get(iRow).getZrqdat()); 
						else if (ds_header.getColumnID(iCol).equals("ZRQCN"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getZrqcn()); 
						else if (ds_header.getColumnID(iCol).equals("ZWRITER"))           ds_header.setColumn(iRow, iCol, list.get(iRow).getZwriter()); 
						else if (ds_header.getColumnID(iCol).equals("ZRMK"))              ds_header.setColumn(iRow, iCol, list.get(iRow).getZrmk()); 
						else if (ds_header.getColumnID(iCol).equals("QTNUM"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getQtnum()); 
						else if (ds_header.getColumnID(iCol).equals("GSNAM"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getGsnam());
						else if (ds_header.getColumnID(iCol).equals("QTSER"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getQtser()); 
						else if (ds_header.getColumnID(iCol).equals("CDATE"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getCdate()); 
						else if (ds_header.getColumnID(iCol).equals("CTIME"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getCtime()); 
						else if (ds_header.getColumnID(iCol).equals("CUSER"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getCuser()); 
						else if (ds_header.getColumnID(iCol).equals("UDATE"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getUdate()); 
						else if (ds_header.getColumnID(iCol).equals("UTIME"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getUtime()); 
						else if (ds_header.getColumnID(iCol).equals("UUSER"))             ds_header.setColumn(iRow, iCol, list.get(iRow).getUuser()); 
	
					}
				} 
				
				ds_error.setId("ds_error");   
				ds_header.setId("ds_header");  
				resultVO.addDataset(ds_header); 
				resultVO.addDataset(ds_error);  	// 오류INFO 
			}
		} catch (Exception e ) {
			
			e.printStackTrace();
			// 호출결과  
			//ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		model.addAttribute("resultVO", resultVO);   

		return view;

	}

	/*------------------------------------------------------------
	 *  도면의뢰 등록(Detail)
	 ------------------------------------------------------------*/
	@RequestMapping(value="insertDetail")
	public View Ses0240insertDetail(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		
		// DAO Create
		service.createDao(session);		
		
		try {
			
			// 저장 호출
			service.insertDetail(paramVO, model, session);

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
	 *  도면의뢰 상세추가(addDetail)
	 ------------------------------------------------------------*/
	@RequestMapping(value="addDetail")
	public View Ses0240addDetail(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));
		
		// DAO Create
		service.createDao(session);		
		
		try {
			
			// 저장 호출
			service.addDetail(paramVO, model, session);

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
	 *  header수정(updateHeader)
	 ------------------------------------------------------------*/
	@RequestMapping(value="updateHeader")
	public View Ses0240updateHeader(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.updateHeader(paramVO, model, session);

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
	 *  detail수정(updateDetail)
	 ------------------------------------------------------------*/
	@RequestMapping(value="updateDetail")
	public View Ses0240updateDetail(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.updateDetail(paramVO, model, session);

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
	 *  진행상태 변경
	 ------------------------------------------------------------*/
	@RequestMapping(value="statusUpdate")
	public View Ses0240statusUpdate(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.statusUpdate(paramVO, model, session);

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
	 *  상세 및 파일 저장(fileSave)
	 ------------------------------------------------------------*/
	@RequestMapping(value="fileSave")
	public View Ses0240fileSave(MipParameterVO paramVO, Model model) { 
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		

		Dataset ds_file = paramVO.getDataset("ds_file");
		Dataset ds_delfile = paramVO.getDataset("ds_delFile");
		
		try {			
				// 파일 저장 호출
				service.fileSave(paramVO, model, session);
					
				} catch (Exception e ) {
				
				e.printStackTrace();
				// 호출결과  
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
		
		ds_error.setId("ds_error");   
		ds_file.setId("dsContReqAppend");
		resultVO.addDataset(ds_file);		
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}		

	/*------------------------------------------------------------
	 *  Header/Detail 저장(save)
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Ses0240Save(MipParameterVO paramVO, Model model) { 

		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
			try {			
				//저장 호출
				boolean save_rtn = service.save(paramVO, model, session);
					
				if (save_rtn == false) {					
					ds_error.insertRow(0);
					ds_error.setColumn(0, "ERRCODE", "ERROR");
					ds_error.setColumn(0, "ERRMSG", "이미 도면접수 중인 견적번호입니다.");
					
					ds_error.setId("ds_error");   
					
					MipResultVO resultVO = new MipResultVO(); 
					resultVO.addDataset(ds_error);  	
					model.addAttribute("resultVO", resultVO);   
					
					return view;
				}
			} catch (Exception e ) {
				
				e.printStackTrace();
				// 호출결과  
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");			
			}
		
			MipResultVO resultVO = new MipResultVO(); 
			
			resultVO.addDataset(ds_error);  	// 오류INFO 
			model.addAttribute("resultVO", resultVO);   

			return view;

	}
}
