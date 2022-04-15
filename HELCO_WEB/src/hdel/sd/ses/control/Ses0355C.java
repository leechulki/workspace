package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0355;
import hdel.sd.ses.domain.Ses0355ParamVO;
import hdel.sd.ses.service.Ses0355S;

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
@RequestMapping("ses0355")
public class Ses0355C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Ses0355S service;

	@RequestMapping(value="findContItem")
	public View Ses0355HeaderContItemView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");	// UI로 return할 out dataset  

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset

		MipResultVO resultVO = new MipResultVO();			// RFC 출력 dataset을 return

		try { 
			Ses0355ParamVO param = new Ses0355ParamVO();
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setPspid(DatasetUtility.getString(dsCond,"PSPID"));
			param.setHogi(DatasetUtility.getString(dsCond,"HOGI"));
			param.setSeq(DatasetUtility.getString(dsCond,"SEQ"));
			
			String sPart = paramVO.getVariable("strPart");

			List<Ses0355> list = service.getContItem(param);   		// 서비스CALL
			// 2013.01.23 계약변경 해외 상해 변경원가 처리 시 자료가 없는 경우 STD Table에서 재조회 후 처리
			if (list == null || list.size() == 0)	{
				if (!"part".equals(sPart))	{		// 설계부에서 호출된 경우가 아닌경우에만 처리
					list = service.getContItemStd(param);
				}
			}

			dsList.deleteAll();
			
			for (int iRow = 0; iRow < list.size(); iRow++) 	{			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if      (dsList.getColumnID(iCol).equals("MANDT"))   dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("PSPID"))   dsList.setColumn(iRow, iCol, list.get(iRow).getPspid());
					else if (dsList.getColumnID(iCol).equals("HOGI"))    dsList.setColumn(iRow, iCol, list.get(iRow).getHogi());
					else if (dsList.getColumnID(iCol).equals("SEQ"))     dsList.setColumn(iRow, iCol, list.get(iRow).getSeq()); 
					else if (dsList.getColumnID(iCol).equals("NETWR"))   dsList.setColumn(iRow, iCol, list.get(iRow).getNetwr()); 
					else if (dsList.getColumnID(iCol).equals("WAVWR"))   dsList.setColumn(iRow, iCol, list.get(iRow).getWavwr()); 
					else if (dsList.getColumnID(iCol).equals("CHWAVWR"))   dsList.setColumn(iRow, iCol, list.get(iRow).getChwavwr());
					else if (dsList.getColumnID(iCol).equals("EDATU"))   dsList.setColumn(iRow, iCol, list.get(iRow).getEdatu()); 
					else if (dsList.getColumnID(iCol).equals("WAERK"))   dsList.setColumn(iRow, iCol, list.get(iRow).getWaerk());  
					else if (dsList.getColumnID(iCol).equals("MATNR"))   dsList.setColumn(iRow, iCol, list.get(iRow).getMatnr());
					else if (dsList.getColumnID(iCol).equals("CLSS"))    dsList.setColumn(iRow, iCol, list.get(iRow).getClss());
					else if (dsList.getColumnID(iCol).equals("BSTNK"))   dsList.setColumn(iRow, iCol, list.get(iRow).getBstnk()); 
					else if (dsList.getColumnID(iCol).equals("FINL"))    dsList.setColumn(iRow, iCol, list.get(iRow).getFinl());
					else if (dsList.getColumnID(iCol).equals("AUART"))    dsList.setColumn(iRow, iCol, list.get(iRow).getAuart());
					else if (dsList.getColumnID(iCol).equals("SPART"))    dsList.setColumn(iRow, iCol, list.get(iRow).getSpart());
					else if (dsList.getColumnID(iCol).equals("VKBUR"))    dsList.setColumn(iRow, iCol, list.get(iRow).getVkbur());
					else if (dsList.getColumnID(iCol).equals("VKGRP"))    dsList.setColumn(iRow, iCol, list.get(iRow).getVkgrp());
					else if (dsList.getColumnID(iCol).equals("CONTR_DA"))    dsList.setColumn(iRow, iCol, list.get(iRow).getContr_da());
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
	public View Ses0355FindDtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond_detail");
		Dataset dsList = paramVO.getDataset("ds_detail");	// UI로 return할 out dataset
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI로 return할 오류메세지 dataset

		MipResultVO resultVO = new MipResultVO();			// RFC 출력 dataset을 return

		try { 
			Ses0355ParamVO param = new Ses0355ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setPspid(DatasetUtility.getString(dsCond,"PSPID"));
			param.setHogi(DatasetUtility.getString(dsCond,"HOGI"));
			param.setSeq(DatasetUtility.getString(dsCond,"SEQ"));
			param.setUkurs(DatasetUtility.getString(dsCond,"UKURS"));			

			List<Ses0355> list = service.getListDetail(param); // 서비스CALL
			dsList.deleteAll();
			
			for (int iRow = 0; iRow < list.size(); iRow++) 	{			// 호출결과(list)를 데이타셋(dsList)에 복사

				dsList.appendRow(); 	// 행추가

				for (int iCol=0;iCol<dsList.getColumnCount();iCol++)	{
					if      (dsList.getColumnID(iCol).equals("MANDT"))       dsList.setColumn(iRow, iCol, list.get(iRow).getMandt());
					else if (dsList.getColumnID(iCol).equals("PSPID"))       dsList.setColumn(iRow, iCol, list.get(iRow).getPspid());
					else if (dsList.getColumnID(iCol).equals("HOGI"))        dsList.setColumn(iRow, iCol, list.get(iRow).getHogi());
					else if (dsList.getColumnID(iCol).equals("SEQ"))         dsList.setColumn(iRow, iCol, list.get(iRow).getSeq()); 
					else if (dsList.getColumnID(iCol).equals("ZCOSTZSEQ"))   dsList.setColumn(iRow, iCol, list.get(iRow).getZcostzseq()); 
					else if (dsList.getColumnID(iCol).equals("ZCCD"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZccd()); 
					else if (dsList.getColumnID(iCol).equals("ZCGRP"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZcgrp()); 
					else if (dsList.getColumnID(iCol).equals("ZCCT"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZcct());  
					else if (dsList.getColumnID(iCol).equals("ZUAM"))        dsList.setColumn(iRow, iCol, list.get(iRow).getZuam()); 
					else if (dsList.getColumnID(iCol).equals("ZUAMC"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZuamc());
					else if (dsList.getColumnID(iCol).equals("ZCOST"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZcost()); 
					else if (dsList.getColumnID(iCol).equals("ZPRCP"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZprcp()); 
					else if (dsList.getColumnID(iCol).equals("ZPRDI"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZprdi());
					else if (dsList.getColumnID(iCol).equals("ZPRDS"))       dsList.setColumn(iRow, iCol, list.get(iRow).getZprds());
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

	@RequestMapping(value="save")
	public View Ses0355Save(MipParameterVO paramVO, Model model) { 
  
		Dataset ds_error   = CallSAPHdel.makeErrorInfoCreateDs("ds_error");		// 오류정보 DATASET GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));		// Session GET

		service.createDao(session);				// DAO Create
		
		try {
			service.savePartCost(paramVO, model, session);	// 저장 호출

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
}
