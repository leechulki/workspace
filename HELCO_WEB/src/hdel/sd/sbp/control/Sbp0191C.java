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
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			Sbp0191ParamVO param = new Sbp0191ParamVO();

			// 수주계획번호
			String pspid = DatasetUtility.getString(dsCondDetail,"PSPID");

			// 파라메터 SET
			param.setMANDT		(paramVO.getVariable("G_MANDT"));  			// SAP CLIENT 
			param.setPSPID		(pspid);									// 사업계획번호
			param.setM00		(dsYearm.getColumnAsString( 0, "YEARM"));	// 계획년월 + 00월
			param.setM01		(dsYearm.getColumnAsString( 1, "YEARM"));	// 계획년월 + 01월
			param.setM02		(dsYearm.getColumnAsString( 2, "YEARM"));	// 계획년월 + 02월
			param.setM03		(dsYearm.getColumnAsString( 3, "YEARM"));	// 계획년월 + 03월
			param.setM04		(dsYearm.getColumnAsString( 4, "YEARM"));	// 계획년월 + 04월
			param.setM05		(dsYearm.getColumnAsString( 5, "YEARM"));	// 계획년월 + 05월
			param.setM06		(dsYearm.getColumnAsString( 6, "YEARM"));	// 계획년월 + 06월
			param.setM07		(dsYearm.getColumnAsString( 7, "YEARM"));	// 계획년월 + 07월
			param.setM08		(dsYearm.getColumnAsString( 8, "YEARM"));	// 계획년월 + 08월
			param.setM09		(dsYearm.getColumnAsString( 9, "YEARM"));	// 계획년월 + 09월
			param.setM10		(dsYearm.getColumnAsString(10, "YEARM"));	// 계획년월 + 10월
			param.setM11		(dsYearm.getColumnAsString(11, "YEARM"));	// 계획년월 + 11월
			param.setM12		(dsYearm.getColumnAsString(12, "YEARM"));	// 계획년월 + 12월
			param.setM13		(dsYearm.getColumnAsString(13, "YEARM"));	// 계획년월 + 13월
			param.setM14		(dsYearm.getColumnAsString(14, "YEARM"));	// 계획년월 + 14월
			param.setM15		(dsYearm.getColumnAsString(15, "YEARM"));	// 계획년월 + 15월
			param.setM16		(dsYearm.getColumnAsString(16, "YEARM"));	// 계획년월 + 16월
			param.setM17		(dsYearm.getColumnAsString(17, "YEARM"));	// 계획년월 + 17월
			param.setM18		(dsYearm.getColumnAsString(18, "YEARM"));	// 계획년월 + 18월
			param.setM19		(dsYearm.getColumnAsString(19, "YEARM"));	// 계획년월 + 19월
			param.setM20		(dsYearm.getColumnAsString(20, "YEARM"));	// 계획년월 + 20월
			param.setM21		(dsYearm.getColumnAsString(21, "YEARM"));	// 계획년월 + 21월
			param.setM22		(dsYearm.getColumnAsString(22, "YEARM"));	// 계획년월 + 22월
			param.setM23		(dsYearm.getColumnAsString(23, "YEARM"));	// 계획년월 + 23월
			param.setM24		(dsYearm.getColumnAsString(24, "YEARM"));	// 계획년월 + 24월
			param.setM25		(dsYearm.getColumnAsString(25, "YEARM"));	// 계획년월 + 25월
			param.setM26		(dsYearm.getColumnAsString(26, "YEARM"));	// 계획년월 + 26월
			param.setM27		(dsYearm.getColumnAsString(27, "YEARM"));	// 계획년월 + 27월
			param.setM28		(dsYearm.getColumnAsString(28, "YEARM"));	// 계획년월 + 28월
			param.setM29		(dsYearm.getColumnAsString(29, "YEARM"));	// 계획년월 + 29월
			
			List<Sbp0191> listSbp0191 = service.selectListDetail(param);

			CallSAPHdel.moveListToDs(dsListDetail, Sbp0191.class, listSbp0191);
			dsListDetail.setId("ds_list_detail");
			
			// dataset을 return
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
		// 결과정보  DATASET GET
		Dataset dsResult = paramVO.getDataset("ds_result");
		
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		try {
			// 저장 호출
			service.save(paramVO, model, session);
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		MipResultVO resultVO = new MipResultVO();
		dsResult.setId("ds_result");
		resultVO.addDataset(dsResult);  	// 결과INFO(사업계획번호) 
		model.addAttribute("resultVO", resultVO);

		return view;
	}
	
}
