package hdel.sd.sbp.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sbp.domain.Sbp0191;
import hdel.sd.sbp.domain.Sbp0191ParamVO;
import hdel.sd.sbp.service.Sbp0191S;

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
@RequestMapping("sbp0191")
public class Sbp0191C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Sbp0191S service;
	
	@RequestMapping(value="find_detail")
	public View Sbp0191FindDetailView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCondDetail = paramVO.getDatasetList().getDataset("ds_cond_detail");
		Dataset dsYearm = paramVO.getDatasetList().getDataset("ds_yearm");

		// OUTPUT DATASET DECLARE
		Dataset dsListDetail = paramVO.getDatasetList().getDataset("ds_list_detail");
		
		try {
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			Sbp0191ParamVO param = new Sbp0191ParamVO();

			// ���ְ�ȹ��ȣ
			String pspid = DatasetUtility.getString(dsCondDetail,"PSPID");

			// �Ķ���� SET
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  			// SAP CLIENT 
			param.setPSPID		(pspid);									// �����ȹ��ȣ
			param.setM00		(dsYearm.getColumnAsString( 0, "YEARM"));	// ��ȹ��� + 00��
			param.setM01		(dsYearm.getColumnAsString( 1, "YEARM"));	// ��ȹ��� + 01��
			param.setM02		(dsYearm.getColumnAsString( 2, "YEARM"));	// ��ȹ��� + 02��
			param.setM03		(dsYearm.getColumnAsString( 3, "YEARM"));	// ��ȹ��� + 03��
			param.setM04		(dsYearm.getColumnAsString( 4, "YEARM"));	// ��ȹ��� + 04��
			param.setM05		(dsYearm.getColumnAsString( 5, "YEARM"));	// ��ȹ��� + 05��
			param.setM06		(dsYearm.getColumnAsString( 6, "YEARM"));	// ��ȹ��� + 06��
			param.setM07		(dsYearm.getColumnAsString( 7, "YEARM"));	// ��ȹ��� + 07��
			param.setM08		(dsYearm.getColumnAsString( 8, "YEARM"));	// ��ȹ��� + 08��
			param.setM09		(dsYearm.getColumnAsString( 9, "YEARM"));	// ��ȹ��� + 09��
			param.setM10		(dsYearm.getColumnAsString(10, "YEARM"));	// ��ȹ��� + 10��
			param.setM11		(dsYearm.getColumnAsString(11, "YEARM"));	// ��ȹ��� + 11��
			param.setM12		(dsYearm.getColumnAsString(12, "YEARM"));	// ��ȹ��� + 12��
			param.setM13		(dsYearm.getColumnAsString(13, "YEARM"));	// ��ȹ��� + 13��
			param.setM14		(dsYearm.getColumnAsString(14, "YEARM"));	// ��ȹ��� + 14��
			param.setM15		(dsYearm.getColumnAsString(15, "YEARM"));	// ��ȹ��� + 15��
			param.setM16		(dsYearm.getColumnAsString(16, "YEARM"));	// ��ȹ��� + 16��
			param.setM17		(dsYearm.getColumnAsString(17, "YEARM"));	// ��ȹ��� + 17��
			param.setM18		(dsYearm.getColumnAsString(18, "YEARM"));	// ��ȹ��� + 18��
			param.setM19		(dsYearm.getColumnAsString(19, "YEARM"));	// ��ȹ��� + 19��
			param.setM20		(dsYearm.getColumnAsString(20, "YEARM"));	// ��ȹ��� + 20��
			param.setM21		(dsYearm.getColumnAsString(21, "YEARM"));	// ��ȹ��� + 21��
			param.setM22		(dsYearm.getColumnAsString(22, "YEARM"));	// ��ȹ��� + 22��
			param.setM23		(dsYearm.getColumnAsString(23, "YEARM"));	// ��ȹ��� + 23��
			param.setM24		(dsYearm.getColumnAsString(24, "YEARM"));	// ��ȹ��� + 24��
			param.setM25		(dsYearm.getColumnAsString(25, "YEARM"));	// ��ȹ��� + 25��
			param.setM26		(dsYearm.getColumnAsString(26, "YEARM"));	// ��ȹ��� + 26��
			param.setM27		(dsYearm.getColumnAsString(27, "YEARM"));	// ��ȹ��� + 27��
			param.setM28		(dsYearm.getColumnAsString(28, "YEARM"));	// ��ȹ��� + 28��
			param.setM29		(dsYearm.getColumnAsString(29, "YEARM"));	// ��ȹ��� + 29��
			
			List<Sbp0191> listSbp0191 = service.selectListDetail(param);

			CallSAPHdel.moveListToDs(dsListDetail, Sbp0191.class, listSbp0191);
			dsListDetail.setId("ds_list_detail");
			
			// dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsListDetail);
			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}

	@RequestMapping(value="save")
	public View Sbp0010SaveView(MipParameterVO paramVO, Model model) {
		// �������  DATASET GET
		Dataset dsResult = paramVO.getDataset("ds_result");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		try {
			// ���� ȣ��
			service.save(paramVO, model, session);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		MipResultVO resultVO = new MipResultVO();
		dsResult.setId("ds_result");
		resultVO.addDataset(dsResult);  	// ���INFO(�����ȹ��ȣ) 
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	
}
