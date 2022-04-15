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
		Dataset dsTemp   = paramVO.getDataset("ds_temp");	// UI�� return�� out dataset
		Dataset dsDetail = paramVO.getDataset("ds_detail");	// UI�� return�� out dataset

		// OUTPUT DATASET DECLARE  
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset
		
		try
		{
			Sso0062ParamVO param = new Sso0062ParamVO();

			param.setMANDT( DatasetUtility.getString(dsCond,"MANDT") );
			param.setMATNR( DatasetUtility.getString(dsCond,"MATNR") );	// �����ȣ

			serviceSso0062S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			Sso0062VO Vo = serviceSso0062S.selectZprdgbn(param);

			// �����ȣ�� ��ǰ�ڵ尡 �����ϴ� ��쿡�� ó��
			if ( Vo != null )	{

				dsDetail.deleteAll();
				dsDetail.appendRow();

				dsDetail.setColumn(0, "ZPRDGBN", Vo.getZPRDGBN());

				// RFC INPUT PARAM DECLARE 
				ZSDT0094[] list_ZSDT0094			= new ZSDT0094[]{};  // sap output list
				ZQMSEMSG[] listMsg  				= new ZQMSEMSG[]{};  // sap �����޽��� return��
	
				// WFC INPUT SET
				Object obj[] = new Object[]{ 
						  DatasetUtility.getString(dsCond,"VBELN")  // ������Ʈ��ȣ
						, DatasetUtility.getString(dsCond,"HOGI")  	// ȣ��
						, DatasetUtility.getString(dsCond,"POSNR")	//�ǸŹ���ǰ��
						, listMsg
						, list_ZSDT0094
				}; 
	
				// OUTPUT DATASET DECLARE
				Dataset ds_output_ZSDT0094 = null;		// sap�� ������� dataSet���� �������.
	
				// RFC CALL
				SapFunction stub = CallSAP.callSap(paramVO.getVariable("G_SYSID"), paramVO.getVariable("G_MANDT") 	// ���Ǳ���
									, "hdel.sd.sso.domain.ZWEB_SD_HOGI_SPEC2"			
									, obj, false);	
	
				// RFC ��±���ü�� out dataset ���� (�ʿ��� Dataset�� ó��)
				ds_output_ZSDT0094 = CallSAP.isJCO() ? new Dataset() : ZSDT0094.getDataset();
	
				// o_etab --> ó������ ����
				listMsg = (ZQMSEMSG[])stub.getOutput("O_ETAB");
				if ( "4".equals(stub.getOutput("E_TYPE").toString()) ) {	// ����
					ds_error = CallSAP.makeErrorInfo(listMsg);
				}else	{
					// RFC ȣ������ out dataset�� �ű��  
					CallSAP.moveObj2Ds(ds_output_ZSDT0094, stub.getOutput("S_ITAB"));
	
					dsTemp.deleteAll();
	
					for (int i = 0; i < ds_output_ZSDT0094.getRowCount(); i++)	{
						dsTemp.appendRow(); 	// ���߰�
	
						dsTemp.setColumn(i, "MANDT"  , ds_output_ZSDT0094.getColumn(i, "MANDT") );
						dsTemp.setColumn(i, "MCLASS" , ds_output_ZSDT0094.getColumn(i, "MATNR") );
						dsTemp.setColumn(i, "ATKLA"  , ds_output_ZSDT0094.getColumn(i, "ATKLA") );
						dsTemp.setColumn(i, "PRH"    , ds_output_ZSDT0094.getColumn(i, "NAM_CHAR") );
						dsTemp.setColumn(i, "PRD"    , ds_output_ZSDT0094.getColumn(i, "CHAR_VALUE") );
						dsTemp.setColumn(i, "PRHNAME", ds_output_ZSDT0094.getColumn(i, "ATBEZ") );
						dsTemp.setColumn(i, "CNT"    , ds_output_ZSDT0094.getColumn(i, "CNT") );

						// 2020 �귣��
						dsTemp.setColumn(i, "PCNCD"    , ds_output_ZSDT0094.getColumn(i, "PCNCD") );
						dsTemp.setColumn(i, "DISPTP"    , ds_output_ZSDT0094.getColumn(i, "DISPTP") );
						dsTemp.setColumn(i, "MODITP"    , ds_output_ZSDT0094.getColumn(i, "MODITP") );
					}
				}

			}else	{
				ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CW10025", "ǰ���� �����ȣ�� Ȯ���Ͻʽÿ�.", "Y", "Y");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "", "Y", "Y");   
		}

		ds_error.setId("ds_error");  		// ERROR

		MipResultVO resultVO = new MipResultVO(); 			// ��� dataset�� return
		resultVO.addDataset(dsTemp);
		resultVO.addDataset(dsDetail);	// ��ǰ����
		resultVO.addDataset(ds_error);  // ERROR
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	@RequestMapping(value="qtfind")
	public View Sso006QtFindView(MipParameterVO paramVO, Model model) {

		Dataset dsCond   = paramVO.getDataset("ds_cond");
		Dataset dsTemp   = paramVO.getDataset("ds_temp");	// UI�� return�� out dataset
		Dataset dsDetail = paramVO.getDataset("ds_detail");	// UI�� return�� out dataset

		// OUTPUT DATASET DECLARE  
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset
		
		try
		{
			Sso0062ParamVO param = new Sso0062ParamVO();

			param.setMANDT( DatasetUtility.getString(dsCond,"MANDT") );
			param.setQTNUM( DatasetUtility.getString(dsCond,"QTNUM") );
			param.setQTSER( DatasetUtility.getString(dsCond,"QTSER") );
			param.setQTSEQ( DatasetUtility.getString(dsCond,"QTSEQ") );
			param.setMATNR( DatasetUtility.getString(dsCond,"MATNR") );	// �����ȣ

			// ����Detail�� ��ǰ�������� ȹ��
			serviceSso0062S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			Sso0062VO Vo = serviceSso0062S.selectZprdgbn(param);

			// �����ȣ�� ��ǰ�ڵ尡 �����ϴ� ��쿡�� ó��
			if ( Vo != null )	{

				dsDetail.deleteAll();
				dsDetail.appendRow();

				dsDetail.setColumn(0, "MANDT",   DatasetUtility.getString(dsCond,"MANDT"));
				dsDetail.setColumn(0, "QTNUM",   DatasetUtility.getString(dsCond,"QTNUM"));
				dsDetail.setColumn(0, "QTSER",   DatasetUtility.getString(dsCond,"QTSER"));
				dsDetail.setColumn(0, "QTSEQ",   DatasetUtility.getString(dsCond,"QTSEQ"));
				dsDetail.setColumn(0, "ZPRDGBN", Vo.getZPRDGBN());

				// ���������� ��� Service call
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

				// ����Detail�� ��ǰ�������� ȹ��
				serviceSes0031S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

				List<Ses0031> list = serviceSes0031S.searchTemplate(paramV);

				dsTemp.deleteAll();

				for (int i = 0; i < list.size(); i++) {	// ȣ����(list)�� ����Ÿ��(dsSo)�� ����
					dsTemp.appendRow(); 	// ���߰�

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

		MipResultVO resultVO = new MipResultVO(); 			// ��� dataset�� return
		resultVO.addDataset(dsTemp);
		resultVO.addDataset(dsDetail);	// ��ǰ����
		resultVO.addDataset(ds_error);  // ERROR
		model.addAttribute("resultVO", resultVO);

		return view;
	}

	// 2013.02.28 �߰�
	@RequestMapping(value="qtfind_tech")
	public View Sso006QtFindTechView(MipParameterVO paramVO, Model model) {

		Dataset dsCond   = paramVO.getDataset("ds_cond");
		Dataset dsTemp   = paramVO.getDataset("ds_temp");	// UI�� return�� out dataset
		Dataset dsDetail = paramVO.getDataset("ds_detail");	// UI�� return�� out dataset

		// OUTPUT DATASET DECLARE  
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� error out dataset
		
		try
		{
			Sso0062ParamVO param = new Sso0062ParamVO();

			param.setMANDT( DatasetUtility.getString(dsCond,"MANDT") );
			param.setQTNUM( DatasetUtility.getString(dsCond,"QTNUM") );
			param.setQTSER( DatasetUtility.getString(dsCond,"QTSER") );
			param.setQTSEQ( DatasetUtility.getString(dsCond,"QTSEQ") );
			param.setMATNR( DatasetUtility.getString(dsCond,"MATNR") );	// �����ȣ

			// ����Detail�� ��ǰ�������� ȹ��
			serviceSso0062S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			Sso0062VO Vo = serviceSso0062S.selectZprdgbn(param);

			// �����ȣ�� ��ǰ�ڵ尡 �����ϴ� ��쿡�� ó��
			if ( Vo != null )	{

				dsDetail.deleteAll();
				dsDetail.appendRow();

				dsDetail.setColumn(0, "MANDT",   DatasetUtility.getString(dsCond,"MANDT"));
				dsDetail.setColumn(0, "QTNUM",   DatasetUtility.getString(dsCond,"QTNUM"));
				dsDetail.setColumn(0, "QTSER",   DatasetUtility.getString(dsCond,"QTSER"));
				dsDetail.setColumn(0, "QTSEQ",   DatasetUtility.getString(dsCond,"QTSEQ"));
				dsDetail.setColumn(0, "ZPRDGBN", Vo.getZPRDGBN());

				// ���������� ��� Service call
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

				// ����Detail�� ��ǰ�������� ȹ��
				serviceSes0031S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

				List<Ses0031> list = serviceSes0031S.searchTemplate(paramV);

				dsTemp.deleteAll();

				for (int i = 0; i < list.size(); i++) {	// ȣ����(list)�� ����Ÿ��(dsSo)�� ����
					dsTemp.appendRow(); 	// ���߰�

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

		MipResultVO resultVO = new MipResultVO(); 			// ��� dataset�� return
		resultVO.addDataset(dsTemp);
		resultVO.addDataset(dsDetail);	// ��ǰ����
		resultVO.addDataset(ds_error);  // ERROR
		model.addAttribute("resultVO", resultVO);

		return view;
	}
}
