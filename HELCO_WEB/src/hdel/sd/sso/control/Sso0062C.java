package hdel.sd.sso.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0031;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.ses.service.Ses0031S;
import hdel.sd.sso.domain.Sso0062ParamVO;
import hdel.sd.sso.domain.Sso0062VO;
import hdel.sd.sso.domain.ZSDS0063;
import hdel.sd.sso.domain.ZSDT0090;
import hdel.sd.sso.domain.ZSDT0091;
import hdel.sd.sso.domain.ZSDT0092;
import hdel.sd.sso.domain.ZSDT0093;
import hdel.sd.sso.domain.ZSDT0094;
import hdel.sd.sso.service.Sso0062S;

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
@RequestMapping("sso0062")
public class Sso0062C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Ses0031S serviceSes0031S;
	
	@Autowired
	private Sso0062S serviceSso0062S;
	
	@RequestMapping(value="sofind")
	public View Sso0062SoFindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond   = paramVO.getDataset("ds_cond");
		Dataset dsTemp   = paramVO.getDataset("ds_temp");	// UI로 return할 out dataset
		Dataset dsDetail = paramVO.getDataset("ds_detail");	// UI로 return할 out dataset

		// OUTPUT DATASET DECLARE  
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		
		try
		{
			Sso0062ParamVO param = new Sso0062ParamVO();

			param.setMANDT( DatasetUtility.getString(dsCond,"MANDT") );
			param.setMATNR( DatasetUtility.getString(dsCond,"MATNR") );	// 자재번호

			serviceSso0062S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			Sso0062VO Vo = serviceSso0062S.selectZprdgbn(param);

			// 자재번호의 제품코드가 존재하는 경우에만 처리
			if ( Vo != null )	{

				dsDetail.deleteAll();
				dsDetail.appendRow();

				dsDetail.setColumn(0, "ZPRDGBN", Vo.getZPRDGBN());

				// RFC INPUT PARAM DECLARE 
				ZSDT0094[] list_ZSDT0094			= new ZSDT0094[]{};  // sap output list
				ZQMSEMSG[] listMsg  				= new ZQMSEMSG[]{};  // sap 에러메시지 return용
	
				// WFC INPUT SET
				Object obj[] = new Object[]{ 
						  DatasetUtility.getString(dsCond,"VBELN")  // 프로젝트번호
						, DatasetUtility.getString(dsCond,"HOGI")  	// 호기
						, DatasetUtility.getString(dsCond,"POSNR")	//판매문서품목
						, listMsg
						, list_ZSDT0094
				}; 
	
				// OUTPUT DATASET DECLARE
				Dataset ds_output_ZSDT0094 = null;		// sap의 결과셋을 dataSet으로 담기위함.
	
				// RFC CALL
				SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT") 	// 세션권한
									, "hdel.sd.sso.domain.ZWEB_SD_HOGI_SPEC2"			
									, obj, false);	
	
				// RFC 출력구조체로 out dataset 생성 (필요한 Dataset만 처리)
				ds_output_ZSDT0094 = CallSAP.isJCO() ? new Dataset() : ZSDT0094.getDataset();
	
				// o_etab --> 처리오류 내역
				listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
				if ( "4".equals(stub.getOutput("E_TYPE").toString()) ) {	// 실패
					ds_error = CallSAP.makeErrorInfo(listMsg);
				}else	{
					// RFC 호출결과를 out dataset에 옮기기  
					CallSAP.moveObj2Ds(ds_output_ZSDT0094, stub.getOutput("S_ITAB"));
	
					dsTemp.deleteAll();
	
					for (int i = 0; i < ds_output_ZSDT0094.getRowCount(); i++)	{
						dsTemp.appendRow(); 	// 행추가
	
						dsTemp.setColumn(i, "MANDT"  , ds_output_ZSDT0094.getColumn(i, "MANDT") );
						dsTemp.setColumn(i, "MCLASS" , ds_output_ZSDT0094.getColumn(i, "MATNR") );
						dsTemp.setColumn(i, "ATKLA"  , ds_output_ZSDT0094.getColumn(i, "ATKLA") );
						dsTemp.setColumn(i, "PRH"    , ds_output_ZSDT0094.getColumn(i, "NAM_CHAR") );
						dsTemp.setColumn(i, "PRD"    , ds_output_ZSDT0094.getColumn(i, "CHAR_VALUE") );
						dsTemp.setColumn(i, "PRHNAME", ds_output_ZSDT0094.getColumn(i, "ATBEZ") );
						dsTemp.setColumn(i, "CNT"    , ds_output_ZSDT0094.getColumn(i, "CNT") );

						// 2020 브랜드
						dsTemp.setColumn(i, "PCNCD"    , ds_output_ZSDT0094.getColumn(i, "PCNCD") );
						dsTemp.setColumn(i, "DISPTP"    , ds_output_ZSDT0094.getColumn(i, "DISPTP") );
						dsTemp.setColumn(i, "MODITP"    , ds_output_ZSDT0094.getColumn(i, "MODITP") );
					}
				}

			}else	{
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW10025", "품목의 자재번호를 확인하십시요.", "Y", "Y");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");   
		}

		ds_error.setId("ds_error");  		// ERROR

		MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
		resultVO.addDataset(dsTemp);
		resultVO.addDataset(dsDetail);	// 제품구분
		resultVO.addDataset(ds_error);  // ERROR
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	@RequestMapping(value="qtfind")
	public View Sso006QtFindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond   = paramVO.getDataset("ds_cond");
		Dataset dsTemp   = paramVO.getDataset("ds_temp");	// UI로 return할 out dataset
		Dataset dsDetail = paramVO.getDataset("ds_detail");	// UI로 return할 out dataset

		// OUTPUT DATASET DECLARE  
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		
		try
		{
			Sso0062ParamVO param = new Sso0062ParamVO();

			param.setMANDT( DatasetUtility.getString(dsCond,"MANDT") );
			param.setQTNUM( DatasetUtility.getString(dsCond,"QTNUM") );
			param.setQTSER( DatasetUtility.getString(dsCond,"QTSER") );
			param.setQTSEQ( DatasetUtility.getString(dsCond,"QTSEQ") );
			param.setMATNR( DatasetUtility.getString(dsCond,"MATNR") );	// 자재번호

			// 견적Detail의 제품구분정보 획득
			serviceSso0062S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			Sso0062VO Vo = serviceSso0062S.selectZprdgbn(param);

			// 자재번호의 제품코드가 존재하는 경우에만 처리
			if ( Vo != null )	{

				dsDetail.deleteAll();
				dsDetail.appendRow();

				dsDetail.setColumn(0, "MANDT",   DatasetUtility.getString(dsCond,"MANDT"));
				dsDetail.setColumn(0, "QTNUM",   DatasetUtility.getString(dsCond,"QTNUM"));
				dsDetail.setColumn(0, "QTSER",   DatasetUtility.getString(dsCond,"QTSER"));
				dsDetail.setColumn(0, "QTSEQ",   DatasetUtility.getString(dsCond,"QTSEQ"));
				dsDetail.setColumn(0, "ZPRDGBN", Vo.getZPRDGBN());

				// 견적정보의 사양 Service call
				Ses0031ParamVO paramV = new Ses0031ParamVO();

				paramV.setMandt(DatasetUtility.getString(dsCond,"MANDT"));	// SAP CLIENT
				paramV.setQtnum(DatasetUtility.getString(dsCond,"QTNUM"));
				paramV.setQtser(DatasetUtility.getString(dsCond,"QTSER"));
				paramV.setQtseq(DatasetUtility.getString(dsCond,"QTSEQ"));
				paramV.setZprdgbn(Vo.getZPRDGBN());

				if ("E".equals(DatasetUtility.getString(dsCond,"SPRAS")))
					paramV.setSpras("E");
				else
					paramV.setSpras("3");

				// 견적Detail의 제품구분정보 획득
				serviceSes0031S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

				List<Ses0031> list = serviceSes0031S.searchTemplate(paramV);

				dsTemp.deleteAll();

				for (int i = 0; i < list.size(); i++) {	// 호출결과(list)를 데이타셋(dsSo)에 복사
					dsTemp.appendRow(); 	// 행추가

					dsTemp.setColumn(i, "MCLASS" , list.get(i).getMclass());
					dsTemp.setColumn(i, "MANDT"  , list.get(i).getMandt());
					dsTemp.setColumn(i, "QTNUM"  , list.get(i).getQtnum());
					dsTemp.setColumn(i, "QTSER"  , list.get(i).getQtser());
					dsTemp.setColumn(i, "QTSEQ"  , list.get(i).getQtseq());
					dsTemp.setColumn(i, "PRSEQ"  , list.get(i).getPrseq());
					dsTemp.setColumn(i, "ATKLA"  , list.get(i).getAtkla());
					dsTemp.setColumn(i, "PRH"    , list.get(i).getPrh());
					dsTemp.setColumn(i, "PRD"    , list.get(i).getPrd());
					dsTemp.setColumn(i, "PRHNAME", list.get(i).getPrhname());
					dsTemp.setColumn(i, "ZRMK"   , list.get(i).getZrmk());
					dsTemp.setColumn(i, "CNT"    , list.get(i).getCnt());
				}

			}else	{
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");   
		}

		ds_error.setId("ds_error");  		// ERROR

		MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
		resultVO.addDataset(dsTemp);
		resultVO.addDataset(dsDetail);	// 제품구분
		resultVO.addDataset(ds_error);  // ERROR
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	// 2013.02.28 추가
	@RequestMapping(value="qtfind_tech")
	public View Sso006QtFindTechView(MipParameterVO paramVO, Model model) {

		Dataset dsCond   = paramVO.getDataset("ds_cond");
		Dataset dsTemp   = paramVO.getDataset("ds_temp");	// UI로 return할 out dataset
		Dataset dsDetail = paramVO.getDataset("ds_detail");	// UI로 return할 out dataset

		// OUTPUT DATASET DECLARE  
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		
		try
		{
			Sso0062ParamVO param = new Sso0062ParamVO();

			param.setMANDT( DatasetUtility.getString(dsCond,"MANDT") );
			param.setQTNUM( DatasetUtility.getString(dsCond,"QTNUM") );
			param.setQTSER( DatasetUtility.getString(dsCond,"QTSER") );
			param.setQTSEQ( DatasetUtility.getString(dsCond,"QTSEQ") );
			param.setMATNR( DatasetUtility.getString(dsCond,"MATNR") );	// 자재번호

			// 견적Detail의 제품구분정보 획득
			serviceSso0062S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

			Sso0062VO Vo = serviceSso0062S.selectZprdgbn(param);

			// 자재번호의 제품코드가 존재하는 경우에만 처리
			if ( Vo != null )	{

				dsDetail.deleteAll();
				dsDetail.appendRow();

				dsDetail.setColumn(0, "MANDT",   DatasetUtility.getString(dsCond,"MANDT"));
				dsDetail.setColumn(0, "QTNUM",   DatasetUtility.getString(dsCond,"QTNUM"));
				dsDetail.setColumn(0, "QTSER",   DatasetUtility.getString(dsCond,"QTSER"));
				dsDetail.setColumn(0, "QTSEQ",   DatasetUtility.getString(dsCond,"QTSEQ"));
				dsDetail.setColumn(0, "ZPRDGBN", Vo.getZPRDGBN());

				// 견적정보의 사양 Service call
				Ses0031ParamVO paramV = new Ses0031ParamVO();

				paramV.setMandt(DatasetUtility.getString(dsCond,"MANDT"));	// SAP CLIENT
				paramV.setQtnum(DatasetUtility.getString(dsCond,"QTNUM"));
				paramV.setQtser(DatasetUtility.getString(dsCond,"QTSER"));
				paramV.setQtseq(DatasetUtility.getString(dsCond,"QTSEQ"));
				paramV.setZprdgbn(Vo.getZPRDGBN());

				if ("E".equals(DatasetUtility.getString(dsCond,"SPRAS")))
					paramV.setSpras("E");
				else
					paramV.setSpras("3");

				// 견적Detail의 제품구분정보 획득
				serviceSes0031S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

				List<Ses0031> list = serviceSes0031S.searchTemplate(paramV);

				dsTemp.deleteAll();

				for (int i = 0; i < list.size(); i++) {	// 호출결과(list)를 데이타셋(dsSo)에 복사
					dsTemp.appendRow(); 	// 행추가

					dsTemp.setColumn(i, "MCLASS" , list.get(i).getMclass());
					dsTemp.setColumn(i, "MANDT"  , list.get(i).getMandt());
					dsTemp.setColumn(i, "QTNUM"  , list.get(i).getQtnum());
					dsTemp.setColumn(i, "QTSER"  , list.get(i).getQtser());
					dsTemp.setColumn(i, "QTSEQ"  , list.get(i).getQtseq());
					dsTemp.setColumn(i, "PRSEQ"  , list.get(i).getPrseq());
					dsTemp.setColumn(i, "ATKLA"  , list.get(i).getAtkla());
					dsTemp.setColumn(i, "PRH"    , list.get(i).getPrh());
					dsTemp.setColumn(i, "PRD"    , list.get(i).getPrd());
					dsTemp.setColumn(i, "PRHNAME", list.get(i).getPrhname());
					dsTemp.setColumn(i, "ZRMK"   , list.get(i).getZrmk());
					dsTemp.setColumn(i, "CNT"    , list.get(i).getCnt());
				}

			}else	{
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");   
		}

		ds_error.setId("ds_error");  		// ERROR

		MipResultVO resultVO = new MipResultVO(); 			// 출력 dataset을 return
		resultVO.addDataset(dsTemp);
		resultVO.addDataset(dsDetail);	// 제품구분
		resultVO.addDataset(ds_error);  // ERROR
		model.addAttribute("resultVO", resultVO);

		return view;
	}
}
