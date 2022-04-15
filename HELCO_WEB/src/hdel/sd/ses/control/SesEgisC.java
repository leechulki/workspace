package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.service.Ses0031NS;
import hdel.sd.ses.service.SesEgisS;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("sesEgis")
public class SesEgisC {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;

	@Autowired
	private Ses0031NS service;

	@Autowired
	private SesEgisS egisService;

	/**
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "searchCost")
	public View Ses0031CostView(MipParameterVO paramVO, Model model) {
		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			// DAO����
			service.createDao(session);
			// ����ȣ��
			resultVO = service.searchCost(paramVO, session);
		} catch (Exception e) {
			logger.error("@@@ Ses0031N searchCost Exception ERROR!!!");
		}

		model.addAttribute("resultVO", resultVO);
		return view;
	} 

//	'-. E-GIS���� "HQ PO�߼�" ��ư Ŭ�� �� SRM�� �������¸� ����
//	-. ������ ������Ʈ ��ȣ��  I/F
//	-. E-GIS���� SRM�� Controller�� ȣ��, URL�� ����, I/F ���� �߰�
//	���� ���� ���̺��� ZSDT1046 table (ZPRGFLG = ��61�� , UDATE, UTIME, UUSER) �� zsdt1091 table(PSPID_CO)�� �������ּ���.
//	Service, domain, dao�� �и��Ͽ� �ۼ� �ٶ��ϴ�.

	@RequestMapping(value = "updateHQPOState")
	public View updateHQPOState(MipParameterVO paramVO, Model model) {

		Dataset dsHeader = paramVO.getDataset("ds_header");
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			dsHeader = egisService.updateHQPOState(paramVO, model, session);
			MipResultVO resultVO = new MipResultVO(); // ��� dataset�� return

			resultVO.addDataset(dsHeader);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

/*
	
	*/
	
	@RequestMapping(value = "updateTradeAmt")
	public View updateTradeAmt(MipParameterVO paramVO, Model model) {

		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			resultVO = egisService.updateTradeAmt(paramVO, session);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
/*	
	Nego�ݾ� update�� ���� �������̽����Ǽ� �Դϴ�.
	ȣ�� ���񽺴� ��sesEgis��/��updateNegoPrice��  �۾� �ٶ��ϴ�.
	F_AMTTP = 1 (����), 2 = (������) 
	������ �߰蹫���� ��� e-GIS���� SRM���� I/F�˴ϴ�.  
	ZUAM, ZUAMS, ZCOST, �������� ���� �ʵ带 �����Ͻñ� �ٶ��ϴ�. (�߰蹫�� ��updateTradeAmt���� �����Ͻñ� �ٶ��ϴ�.)
	�������� ZEAM �ʵ带 �����Ͻñ� �ٶ��ϴ�.
	
*/	@RequestMapping(value = "updateNegoPrice")
	public View updateNegoPrice(MipParameterVO paramVO, Model model) {

		// ��� VIEW
		MipResultVO resultVO = new MipResultVO();
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));// Session GET

		try {
			resultVO = egisService.updateNegoPrice(paramVO, session);

			model.addAttribute("resultVO", resultVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}	
	
}