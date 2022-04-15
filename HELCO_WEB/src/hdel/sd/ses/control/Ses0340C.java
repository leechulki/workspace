package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0340;
import hdel.sd.ses.domain.Ses0340ParamVO;
import hdel.sd.ses.service.Ses0340S;

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
@RequestMapping("ses0340")
public class Ses0340C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0340S service;
	
	@RequestMapping(value="find")
	public View Ses0340FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list");	// UI로 return할 out dataset  
		 
		try { 
			Ses0340ParamVO param = new Ses0340ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setZconnr(DatasetUtility.getString(dsCond,"ZCONNR"  ));

			//--------------------------------------------------------------------------------------------
			// INPUT PARAM INFO     		
//			System.out.print("\n@@@ paramVO.G_MANDT   ===>"+paramVO.getVariable("G_MANDT") 	       +"\n");  
//			System.out.print("\n@@@ dsCond.ZCONNR 	===>"+DatasetUtility.getString(dsCond,"ZCONNR")	+"\n");
			//--------------------------------------------------------------------------------------------
			
			List<Ses0340> list = service.selectList(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if      (dsList.getColumnID(iCol).equals("MANDT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("ZCONNR"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZconnr());
					else if (dsList.getColumnID(iCol).equals("ATNAM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getAtnam()); 
					else if (dsList.getColumnID(iCol).equals("ATBEZ"))               dsList.setColumn(iRow, iCol, list.get(iRow).getAtbez());
					else if (dsList.getColumnID(iCol).equals("ATWRT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getAtwrt());
					else if (dsList.getColumnID(iCol).equals("SPEC"))               dsList.setColumn(iRow, iCol, list.get(iRow).getSpec());  
					else if (dsList.getColumnID(iCol).equals("ZQTY"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZqty());
					else if (dsList.getColumnID(iCol).equals("ZUAM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZuam());
					else if (dsList.getColumnID(iCol).equals("ZAMT"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZamt()); 
					else if (dsList.getColumnID(iCol).equals("ZRMK"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrmk()); 
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

	@RequestMapping(value="findSpec")
	public View Ses0340FindSpecView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list");	// UI로 return할 out dataset  
		 
		try { 
			Ses0340ParamVO param = new Ses0340ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setZconnr(DatasetUtility.getString(dsCond,"ZCONNR"  ));

			//--------------------------------------------------------------------------------------------
			// INPUT PARAM INFO     		
//			System.out.print("\n@@@ paramVO.G_MANDT   ===>"+paramVO.getVariable("G_MANDT") 	       +"\n");  
//			System.out.print("\n@@@ dsCond.ZCONNR 	===>"+DatasetUtility.getString(dsCond,"ZCONNR")	+"\n");
			//--------------------------------------------------------------------------------------------
			
			List<Ses0340> list = service.selectSpec(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if      (dsList.getColumnID(iCol).equals("MANDT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("ZCONNR"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZconnr());
					else if (dsList.getColumnID(iCol).equals("ATNAM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getAtnam()); 
					else if (dsList.getColumnID(iCol).equals("ATBEZ"))               dsList.setColumn(iRow, iCol, list.get(iRow).getAtbez());
					else if (dsList.getColumnID(iCol).equals("ATWRT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getAtwrt());
					else if (dsList.getColumnID(iCol).equals("SPEC"))               dsList.setColumn(iRow, iCol, list.get(iRow).getSpec());  
					else if (dsList.getColumnID(iCol).equals("ZQTY"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZqty());
					else if (dsList.getColumnID(iCol).equals("ZUAM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZuam());
					else if (dsList.getColumnID(iCol).equals("ZAMT"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZamt()); 
					else if (dsList.getColumnID(iCol).equals("ZRMK"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrmk()); 
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

	@RequestMapping(value="check")
	public View Ses0340CheckView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_check");	// UI로 return할 out dataset  
		 
		try { 
			Ses0340ParamVO param = new Ses0340ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setZconnr(DatasetUtility.getString(dsCond,"ZCONNR"  ));

			//--------------------------------------------------------------------------------------------
			// INPUT PARAM INFO     		
//			System.out.print("\n@@@ paramVO.G_MANDT   ===>"+paramVO.getVariable("G_MANDT") 	       +"\n");  
//			System.out.print("\n@@@ dsCond.ZCONNR 	===>"+DatasetUtility.getString(dsCond,"ZCONNR")	+"\n");
			//--------------------------------------------------------------------------------------------
			
			List<Ses0340> list = service.selectCheck(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if (dsList.getColumnID(iCol).equals("ROWCNT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getRowcnt());
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
	
	@RequestMapping(value="save")
	public View Ses0340SaveView(MipParameterVO paramVO, Model model) { 
  
		// 오류정보 DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// 저장 호출
			service.saveZSDT1068(paramVO, model, session);

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
