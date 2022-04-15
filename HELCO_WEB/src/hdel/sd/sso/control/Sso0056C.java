package hdel.sd.sso.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.sso.service.Sso0056S;
import hdel.sd.com.service.DutyS;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

/*
 ******************************************************************************************
 * 기      능   : 계약변경 호기순번 변경
 * 작  성  자   :
 * 작성  일자   : 2016.03.07
 * 기능ID       : UF006
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 최초 코딩 박수근
 ******************************************************************************************
*/


@Controller
@RequestMapping("sso0056")
public class Sso0056C {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Sso0056S sso0056S;
	
	@Autowired
	private DutyS dutyService;

    //=========================================================================================
    //  함수기능  : 계약변경 호기 순번 기술사양서 적용 여부 조회
    //  파라미터  : 호기변경마스터(ds_mv_ZSDT0090)
    //              호기변경리스트(ds_mv_ZSDT0091)
    //  리턴값    :
    //  기능ID    : UF006
    //  개선내역  : 호기 순번 변경 신규
    //=========================================================================================
	@RequestMapping(value="hogiTechSendList")
	public View hogiTechSendListView(MipParameterVO paramVO, Model model) {
		Dataset ds_mv_ZSDT0090 = paramVO.getDataset("ds_mv_ZSDT0090");
		Dataset ds_mv_ZSDT0091 = paramVO.getDataset("ds_mv_ZSDT0091");
		Dataset ds_error = paramVO.getDataset("ds_error");

		MipResultVO resultVO = new MipResultVO();
		sso0056S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
		
		try {
			sso0056S.getHogiList(ds_mv_ZSDT0090, ds_mv_ZSDT0091, resultVO);
		} catch(Exception e) {
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		} finally {
			resultVO.addDataset(ds_error);
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}

    //=========================================================================================
    //  함수기능  : 계약변경 호기 순번 변경 저장 후 변경 호기 원가 산출 수행
    //  파라미터  : 호기변경마스터(ds_mv_ZSDT0090)
    //              호기변경리스트(ds_mv_ZSDT0091)
    //  리턴값    :
    //  기능ID    : UF006
    //  개선내역  : 호기 순번 변경 신규
    //=========================================================================================
	@RequestMapping(value="saveMoveHogiData")
	public View saveMoveHogiDataView(MipParameterVO paramVO, Model model) {
		Dataset ds_mv_ZSDT0090 = paramVO.getDataset("ds_mv_ZSDT0090");
		Dataset ds_mv_ZSDT0091 = paramVO.getDataset("ds_mv_ZSDT0091");
		Dataset ds_error = paramVO.getDataset("ds_error");
		String  erNam     = paramVO.getVariable("G_SAP_USER_NAME");

		MipResultVO resultVO = new MipResultVO();
		sso0056S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성

		try {
			sso0056S.saveMoveHogiData(ds_mv_ZSDT0090, ds_mv_ZSDT0091, resultVO, ds_error, erNam);
		} catch(Exception e) {
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			logger.error("error: "+e.getMessage());
		} finally {
			resultVO.addDataset(ds_error);
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}
}
