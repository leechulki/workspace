package hdel.sd.sso.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.sso.service.Sso0210S;
import hdel.sd.sso.domain.Sso0210;
import hdel.sd.sso.domain.Sso0210ParamVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sso0210")
public class Sso0210C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sso0210S service;
	
	@RequestMapping(value="findList")
	public View sso0210FindListView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList = paramVO.getDataset("ds_list");					// UI�� return�� out dataset
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset
		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			Sso0210ParamVO param = new Sso0210ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setFrzrqdat(DatasetUtility.getString(dsCond,"FR_ZRQDAT"));
			param.setTozrqdat(DatasetUtility.getString(dsCond,"TO_ZRQDAT"));
			param.setSman(DatasetUtility.getString(dsCond,"ZKUNNR_Z3"));

			List<Sso0210> list = service.getListHeader(param);   			// ����CALL
			dsList.deleteAll();
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����
			for (int i = 0; i < list.size(); i++) {

				dsList.appendRow(); 	// ���߰�

				dsList.setColumn(i, "MANDT",   list.get(i).getMandt());
				dsList.setColumn(i, "PSPID",   list.get(i).getPspid());
				dsList.setColumn(i, "SEQ",     list.get(i).getSeq());
				dsList.setColumn(i, "HOGI",   list.get(i).getHogi());;
				dsList.setColumn(i, "BSTNK",   list.get(i).getBstnk());
				dsList.setColumn(i, "CHDAT",   list.get(i).getChdat());
				dsList.setColumn(i, "ZZSHIP1",   list.get(i).getZzship1());
				dsList.setColumn(i, "ZZSHIP2",   list.get(i).getZzship2());
				dsList.setColumn(i, "ZZSHIP3",   list.get(i).getZzship3());
				dsList.setColumn(i, "KUNNR_Z2",   list.get(i).getKunnr_z2());
				dsList.setColumn(i, "KUNNR_Z3",   list.get(i).getKunnr_z3());
				dsList.setColumn(i, "KUNNR_PM",   list.get(i).getKunnr_pm());				
				dsList.setColumn(i, "PHONE_Z2",   list.get(i).getPhone_z2());
				dsList.setColumn(i, "PHONE_Z3",   list.get(i).getPhone_z3());
				dsList.setColumn(i, "PDMMPNO",   list.get(i).getPdmmpno());
				dsList.setColumn(i, "PDMEPNO",   list.get(i).getPdmepno());				
			}

			resultVO.addDataset(dsList);
		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		return view;
	}

	@RequestMapping(value="findDetail")
	public View sso0210FindDetailView(MipParameterVO paramVO, Model model) {

		Dataset dsList2 = paramVO.getDataset("ds_list2");					// UI�� return�� out dataset
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");	// UI�� return�� �����޼��� dataset

		// INPUT DATASET GET
		String i_pspId	= paramVO.getVariable("pspId");	// ������Ʈ��ȣ
		String i_seq	= paramVO.getVariable("seq"); 	// ����
		String i_hogi	= paramVO.getVariable("hogi"); 	// ����
		String i_seqpre = "";
		
		MipResultVO resultVO = new MipResultVO();

		try
		{ 
			//CW00001=[${}] �׸� ���� �����Ǿ����ϴ�.\nȮ���Ͽ� �ֽʽÿ�.
			//CW00002=�ʼ� �Է��׸��Դϴ�.\nȮ���Ͽ� �ֽʽÿ�.
			if ( "".equals( i_pspId ) || i_pspId == null )
			{
				throw new BizException("CW00001,������Ʈ��ȣ");
			}
			if ( "".equals( i_seq ) || i_seq == null )
			{
				throw new BizException("CW00001,����");
			}
			if ( i_seq == "1" ) {
				i_seqpre = "1";
			} else {
				i_seqpre = String.valueOf(Integer.parseInt(i_seq) - 1);
			}
			Sso0210ParamVO param = new Sso0210ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
			param.setPspid(i_pspId); 										// ������Ʈ��ȣ
			param.setHogi(i_hogi);
			param.setSeq(i_seq);											// ����
			param.setSeqpre(i_seqpre);

			List<Sso0210> list = service.getListDetail(param);   			// ����CALL
			dsList2.deleteAll();
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����
			for (int i = 0; i < list.size(); i++) {

				dsList2.appendRow(); 	// ���߰�

				dsList2.setColumn(i, "MANDT",   list.get(i).getMandt());
				dsList2.setColumn(i, "PSPID",   list.get(i).getPspid());
				
				dsList2.setColumn(i, "SEQ",     list.get(i).getSeq());
				dsList2.setColumn(i, "HOGI",    list.get(i).getHogi());
				dsList2.setColumn(i, "NAM_CHAR",list.get(i).getNam_char());
				dsList2.setColumn(i, "ATBEZ",   list.get(i).getAtbez());
				dsList2.setColumn(i, "ATWTB_P",   list.get(i).getAtwtb_p());
				dsList2.setColumn(i, "ATWTB_T",   list.get(i).getAtwtb_t());				
			}

			resultVO.addDataset(dsList2);
 		} catch (Exception e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);
		return view;
	}
}
