package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0171;
import hdel.sd.ses.domain.Ses0171ParamVO;
import hdel.sd.ses.service.Ses0171S;

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
@RequestMapping("ses0171")
public class Ses0171C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0171S service;
	
	@RequestMapping(value="find")
	public View Ses0171FindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond   = paramVO.getDataset("ds_cond");
		Dataset dsList   = paramVO.getDataset("ds_list");	// UI로 return할 out dataset
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		MipResultVO resultVO = new MipResultVO();			// RFC 출력 dataset을 return

		try { 
			Ses0171ParamVO param = new Ses0171ParamVO();
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setQtnum(DatasetUtility.getString(dsCond,"QTNUM"));
			param.setQtser(DatasetUtility.getString(dsCond,"QTSER"));
			param.setQtseq(DatasetUtility.getString(dsCond,"QTSEQ"));

			List<Ses0171> list = service.searchList(param);   			// 서비스CALL
			dsList.deleteAll();
			
			for (int iRow = 0; iRow < list.size(); iRow++) 	{			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if       (dsList.getColumnID(iCol).equals("MANDT"))             dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("QTNUM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtnum());
					else if (dsList.getColumnID(iCol).equals("QTSER"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtser());
					else if (dsList.getColumnID(iCol).equals("QTSEQ"))               dsList.setColumn(iRow, iCol, list.get(iRow).getQtseq()); 
					else if (dsList.getColumnID(iCol).equals("SHANGH"))               dsList.setColumn(iRow, iCol, list.get(iRow).getShangh()); 
					else if (dsList.getColumnID(iCol).equals("MATNR"))               dsList.setColumn(iRow, iCol, list.get(iRow).getMatnr()); 
					else if (dsList.getColumnID(iCol).equals("SPEC"))               dsList.setColumn(iRow, iCol, list.get(iRow).getSpec()); 
					else if (dsList.getColumnID(iCol).equals("GTYPE"))               dsList.setColumn(iRow, iCol, list.get(iRow).getGtype());  
					else if (dsList.getColumnID(iCol).equals("TYPE1"))             dsList.setColumn(iRow, iCol, list.get(iRow).getType1());
					else if (dsList.getColumnID(iCol).equals("TYPE2"))             dsList.setColumn(iRow, iCol, list.get(iRow).getType2());
					else if (dsList.getColumnID(iCol).equals("TYPE3"))               dsList.setColumn(iRow, iCol, list.get(iRow).getType3()); 
					else if (dsList.getColumnID(iCol).equals("ZNUMBER"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZnumber()); 
					else if (dsList.getColumnID(iCol).equals("ZUSE"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZuse()); 
					else if (dsList.getColumnID(iCol).equals("ZOPN"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZopn()); 
					else if (dsList.getColumnID(iCol).equals("ZLEN"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZlen());
					else if (dsList.getColumnID(iCol).equals("ZUAM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZuam());
					else if (dsList.getColumnID(iCol).equals("ZUAMS"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZuams());
					else if (dsList.getColumnID(iCol).equals("ZURATE"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZurate());				
					else if (dsList.getColumnID(iCol).equals("ZCOST"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZcost()); 
					else if (dsList.getColumnID(iCol).equals("ZCOSTM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZcostm());
					else if (dsList.getColumnID(iCol).equals("ZEAM"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZeam()); 
					else if (dsList.getColumnID(iCol).equals("SHANG"))               dsList.setColumn(iRow, iCol, list.get(iRow).getShang()); 
					else if (dsList.getColumnID(iCol).equals("ZRMK"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZrmk());
					else if (dsList.getColumnID(iCol).equals("VKBUR"))               dsList.setColumn(iRow, iCol, list.get(iRow).getVkbur());
					else if (dsList.getColumnID(iCol).equals("VKGRP"))               dsList.setColumn(iRow, iCol, list.get(iRow).getVkgrp());
					else if (dsList.getColumnID(iCol).equals("AUART"))               dsList.setColumn(iRow, iCol, list.get(iRow).getAuart());
					else if (dsList.getColumnID(iCol).equals("ZPRGFLG"))               dsList.setColumn(iRow, iCol, list.get(iRow).getZprgflg());
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");
		}	    
		resultVO.addDataset(dsList);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value="query_list_detail")
	public View Ses0171FindDtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond_detail");
		Dataset dsList = paramVO.getDataset("ds_detail");	// UI로 return할 out dataset
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		MipResultVO resultVO = new MipResultVO();			// RFC 출력 dataset을 return

		try { 
			Ses0171ParamVO param = new Ses0171ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setQtnum(DatasetUtility.getString(dsCond,"QTNUM"));
			param.setQtser(DatasetUtility.getString(dsCond,"QTSER"));
			param.setQtseq(DatasetUtility.getString(dsCond,"QTSEQ"));
			param.setUkurs(DatasetUtility.getString(dsCond,"UKURS"));
			param.setFcurr(DatasetUtility.getString(dsCond, "FCURR"));
			
			List<Ses0171> list = service.searchListDetail(param); // 서비스CALL
			dsList.deleteAll();
			
			for (int iRow = 0; iRow < list.size(); iRow++) 	{			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if       (dsList.getColumnID(iCol).equals("MANDT"))            dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("QTNUM"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtnum());
					else if (dsList.getColumnID(iCol).equals("QTSER"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtser());
					else if (dsList.getColumnID(iCol).equals("QTSEQ"))             dsList.setColumn(iRow, iCol, list.get(iRow).getQtseq()); 
					else if (dsList.getColumnID(iCol).equals("ZCOSTZSEQ"))         dsList.setColumn(iRow, iCol, list.get(iRow).getZcostzseq()); 
					else if (dsList.getColumnID(iCol).equals("ZCCD"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZccd()); 
					else if (dsList.getColumnID(iCol).equals("ZCGRP"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZcgrp()); 
					else if (dsList.getColumnID(iCol).equals("ZCCT"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZcct());  
					else if (dsList.getColumnID(iCol).equals("ZUAM"))              dsList.setColumn(iRow, iCol, list.get(iRow).getZuam());
					else if (dsList.getColumnID(iCol).equals("ZUAMS"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZuams());
					else if (dsList.getColumnID(iCol).equals("ZURATE"))            dsList.setColumn(iRow, iCol, list.get(iRow).getZurate());				
					else if (dsList.getColumnID(iCol).equals("ZCOST"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZcost()); 
					else if (dsList.getColumnID(iCol).equals("ZPRCP"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZprcp()); 
					else if (dsList.getColumnID(iCol).equals("ZPRDI"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZprdi());
					else if (dsList.getColumnID(iCol).equals("ZPRDS"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZprds());
					else if (dsList.getColumnID(iCol).equals("ZUAMC"))             dsList.setColumn(iRow, iCol, list.get(iRow).getZuamc());					
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");
		}	    
		resultVO.addDataset(dsList); 
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	
	/*------------------------------------------------------------
	 *  견적원가의뢰 등록
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Ses0171SaveZSDT1050View(MipParameterVO paramVO, Model model) { 
  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");		// 오류정보 DATASET GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));		// Session GET

		service.createDao(session);				// DAO Create
		
		try {
			service.saveZSDT1050(paramVO, model, session);	// 저장 호출

		} catch (Exception e ) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO(); 
		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   
		return view;
	}
	
	/*------------------------------------------------------------
	 *  견적master, detail, 승인 update
	 ------------------------------------------------------------*/
	@RequestMapping(value="updateFlag")
	public View updateFlagView(MipParameterVO paramVO, Model model) { 
  
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");		// 오류정보 DATASET GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));		// Session GET
		service.createDao(session);				// DAO Create
		
		try {
			service.confirmFlag(paramVO, model, session);			// 저장 호출

			} catch (Exception e ) {
			
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO(); 
//		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// 오류INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;
	}
/*	
	@RequestMapping(value = "getExchangeRate")
	public View getExchangeRateView(MipParameterVO paramVO, Model model) {
		
		Dataset dsExchange 	= paramVO.getDataset("ds_exchange");				// UI로 return할 out dataset(목록 정보)		
		Dataset dsError 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));		// Session GET
		service.createDao(session);			// DAO Create
		
		ExchangeS.createDao(session);		//DAO 생성
		
		
		try { 
			dsExchange = service.searchExchangeRate(paramVO);
		} catch (Exception e) { 
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
		}
		
		MipResultVO resultVO = new MipResultVO();
		
		dsExchange.setId	("ds_exchange");
		dsError.setId		("ds_error");
		
		resultVO.addDataset(dsExchange);
		resultVO.addDataset(dsError);
		model.addAttribute("resultVO", resultVO);
		return view;
	}	
*/
}
