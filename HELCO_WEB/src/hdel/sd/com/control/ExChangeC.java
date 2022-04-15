package hdel.sd.com.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;

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

import hdel.sd.com.service.ExchangeS;

@Controller
@RequestMapping("exchange")

public class ExChangeC {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private ExchangeS ExchangeS;
	
	@RequestMapping(value = "getCostRegiExRate")
	public View getExchangeRateView(MipParameterVO paramVO, Model model) {
		
		Dataset dsExchange 	= paramVO.getDataset("ds_exchange");				// UI로 return할 out dataset(목록 정보)		
		Dataset dsError 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));		// Session GET
		
		ExchangeS.createDao(session);		//DAO 생성
		
		try { 
			dsExchange = ExchangeS.searchCostRegiExRate(paramVO);
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
}
