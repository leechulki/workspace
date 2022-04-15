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
 * ��      ��   : ��ຯ�� ȣ����� ����
 * ��  ��  ��   :
 * �ۼ�  ����   : 2016.03.07
 * ���ID       : UF006
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 ���� �ڵ� �ڼ���
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
    //  �Լ����  : ��ຯ�� ȣ�� ���� �����缭 ���� ���� ��ȸ
    //  �Ķ����  : ȣ�⺯�渶����(ds_mv_ZSDT0090)
    //              ȣ�⺯�渮��Ʈ(ds_mv_ZSDT0091)
    //  ���ϰ�    :
    //  ���ID    : UF006
    //  ��������  : ȣ�� ���� ���� �ű�
    //=========================================================================================
	@RequestMapping(value="hogiTechSendList")
	public View hogiTechSendListView(MipParameterVO paramVO, Model model) {
		Dataset ds_mv_ZSDT0090 = paramVO.getDataset("ds_mv_ZSDT0090");
		Dataset ds_mv_ZSDT0091 = paramVO.getDataset("ds_mv_ZSDT0091");
		Dataset ds_error = paramVO.getDataset("ds_error");

		MipResultVO resultVO = new MipResultVO();
		sso0056S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
		
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
    //  �Լ����  : ��ຯ�� ȣ�� ���� ���� ���� �� ���� ȣ�� ���� ���� ����
    //  �Ķ����  : ȣ�⺯�渶����(ds_mv_ZSDT0090)
    //              ȣ�⺯�渮��Ʈ(ds_mv_ZSDT0091)
    //  ���ϰ�    :
    //  ���ID    : UF006
    //  ��������  : ȣ�� ���� ���� �ű�
    //=========================================================================================
	@RequestMapping(value="saveMoveHogiData")
	public View saveMoveHogiDataView(MipParameterVO paramVO, Model model) {
		Dataset ds_mv_ZSDT0090 = paramVO.getDataset("ds_mv_ZSDT0090");
		Dataset ds_mv_ZSDT0091 = paramVO.getDataset("ds_mv_ZSDT0091");
		Dataset ds_error = paramVO.getDataset("ds_error");
		String  erNam     = paramVO.getVariable("G_SAP_USER_NAME");

		MipResultVO resultVO = new MipResultVO();
		sso0056S.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

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
