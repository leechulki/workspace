package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0270;
import hdel.sd.ses.domain.Ses0270ParamVO;
import hdel.sd.ses.service.Ses0270S;

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
@RequestMapping("ses0270")
public class Ses0270C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0270S service;
	
	@RequestMapping(value="find")
	public View Ses0270FindView(MipParameterVO paramVO, Model model) {

//		System.out.print("\n@@@ Ses0270FindView");
		
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list");	// UI로 return할 out dataset  
		 
		try { 
			Ses0270ParamVO param = new Ses0270ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setQtnum   (DatasetUtility.getString(dsCond,"QTNUM"  ));
			param.setQtser  (DatasetUtility.getInt(dsCond,"QTSER"  ));
			param.setZrqseq (DatasetUtility.getInt(dsCond,"ZRQSEQ"));

			//--------------------------------------------------------------------------------------------
			// INPUT PARAM INFO     		
//			System.out.print("\n@@@ paramVO.G_MANDT   ===>"+paramVO.getVariable("G_MANDT") 	       +"\n");  
//			System.out.print("\n@@@ dsCond.QTNUM 	===>"+DatasetUtility.getString(dsCond,"QTNUM")	+"\n");
//			System.out.print("\n@@@ dsCond.QTSER	===>"+DatasetUtility.getString(dsCond,"QTSER")	+"\n");
//			System.out.print("\n@@@ dsCond.ZRQSEQ	===>"+DatasetUtility.getString(dsCond,"ZRQSEQ")+"\n");
			//--------------------------------------------------------------------------------------------
			
			List<Ses0270> list = service.searchList(param);   			// 서비스CALL
			dsList.deleteAll();
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if       (dsList.getColumnID(iCol).equals("MANDT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("QTNUM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtnum());
					else if (dsList.getColumnID(iCol).equals("QTSER"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtser());
					else if (dsList.getColumnID(iCol).equals("ZRQSEQ"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqseq()); 
					else if (dsList.getColumnID(iCol).equals("ZRQDAT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqdat()); 
					else if (dsList.getColumnID(iCol).equals("ZRQCN"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqcn()); 
					else if (dsList.getColumnID(iCol).equals("ZRQSTAT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqstat()); 
					else if (dsList.getColumnID(iCol).equals("ZRQRLT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrqrlt());  
				}
			} 
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			
//			System.out.println("============== ds_list.getRowCount() " + dsList.getRowCount());
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	@RequestMapping(value="query_list_detail")
	public View Ses0270FindDtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_list_detail");	// UI로 return할 out dataset  
		 
		try { 
			Ses0270ParamVO param = new Ses0270ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setQtnum   (DatasetUtility.getString(dsCond,"QTNUM"  ));
			param.setQtser  (DatasetUtility.getInt(dsCond,"QTSER"  ));
			param.setZrqseq (DatasetUtility.getInt(dsCond,"ZRQSEQ"));

			//--------------------------------------------------------------------------------------------
			// INPUT PARAM INFO     		
//			System.out.print("\n@@@ paramVO.G_MANDT   ===>"+paramVO.getVariable("G_MANDT") 	       +"\n");  
//			System.out.print("\n@@@ dsCond.QTNUM 	===>"+DatasetUtility.getString(dsCond,"QTNUM")	+"\n");
//			System.out.print("\n@@@ dsCond.QTSER	===>"+DatasetUtility.getString(dsCond,"QTSER")	+"\n");
//			System.out.print("\n@@@ dsCond.ZRQSEQ	===>"+DatasetUtility.getString(dsCond,"ZRQSEQ")+"\n");
			//--------------------------------------------------------------------------------------------
//			System.out.print("\n@@@ Ses0270FindDtlView    " + param.getZrqseq());
			
			List<Ses0270> list = service.searchListDetail(param);   			// 서비스CALL
			
			dsList.deleteAll();
			
//			System.out.print("\n@@@ Ses0270FindDtlView ZZZZZZZZZZ");
			
			// 호출결과(list)를 데이타셋(dsList)에 복사
			for (int iRow=0;iRow<list.size();iRow++) {

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if       (dsList.getColumnID(iCol).equals("MANDT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("QTNUM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtnum());
					else if (dsList.getColumnID(iCol).equals("QTSER"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtser());
					else if (dsList.getColumnID(iCol).equals("QTSEQ"))               dsList.setColumn(iRow, iCol, list.get(iRow).getQtseq()); 
					else if (dsList.getColumnID(iCol).equals("ZCOSTZSEQ"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZcostzseq()); 
					else if (dsList.getColumnID(iCol).equals("ZCCD"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZccd()); 
					else if (dsList.getColumnID(iCol).equals("ZCGRP"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZcgrp()); 
					else if (dsList.getColumnID(iCol).equals("ZCCT"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZcct());  
					else if (dsList.getColumnID(iCol).equals("ZUAM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZuam()); 
					else if (dsList.getColumnID(iCol).equals("ZCOST"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZcost()); 
					else if (dsList.getColumnID(iCol).equals("ZPRCP"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZprcp()); 
					else if (dsList.getColumnID(iCol).equals("ZPRDI"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZprdi());
					else if (dsList.getColumnID(iCol).equals("ZPRDS"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZprds());
				}
			} 
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsList); 
			
//			System.out.println("============== ds_list_detail.getRowCount() " + dsList.getRowCount());
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
}
