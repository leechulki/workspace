package hdel.sd.ses.service;

import java.util.HashMap;
import java.util.List;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.lib.service.ZSDT0167S;
import hdel.sd.com.domain.AutoNumberParamVO;
import hdel.sd.com.domain.AutoNumberVO;
import hdel.sd.com.service.AutoQtNumberS;
import hdel.sd.ses.dao.Ses0030ED;
import hdel.sd.ses.domain.Ses0030;
import hdel.sd.ses.domain.Ses0030ParamVO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Ses0030ES extends SrmService {

	@Autowired
	private AutoQtNumberS Autoservice;	// 견적번호채번 서비스 
	
	private Ses0030ED Ses0030EDao;

	public void createDao(SqlSession sqlSession) {
		Ses0030EDao = sqlSession.getMapper(Ses0030ED.class);
	}

	public List<Ses0030> searchEgisHeader(Ses0030ParamVO param) {
		return Ses0030EDao.selectEgisListHeader(param);
	}

	// 해외모의견적 등록 saveEgisEstimate
	public void saveEgisEstimate(MipParameterVO paramVO, Ses0030ParamVO param, SqlSession session) throws Exception {
		String smlqtnum = null;
		String smlqtser = null;

		// 모의견적번호
	    HashMap<String, Object> smlMap = (HashMap<String, Object>) Ses0030EDao.selectQtnumZsdt1164(param);
        if(smlMap == null) {
            AutoNumberParamVO AutoNumberParam = new AutoNumberParamVO();
			Autoservice.createDao(session);
			AutoNumberParam.setMandt(param.getMandt());
			AutoNumberParam.setDeptFlag(param.getAuart());	// 기종
			AutoNumberParam.setSoQtFlag("2");		// 채번구분(0:사업계획, 1:수주계획 , 2:견적)

			List<AutoNumberVO> listVO = Autoservice.AutoQtNumber(AutoNumberParam);// 견적번호 채번 서비스 호출
			smlqtnum = listVO.get(0).getNumber().toString();
			smlqtser = "1";
			
			// 견적번호를 생성 후 입력
			param.setSmlqtnum(smlqtnum);
			// 최초생성한 견적번호는 1차로 정의한다.
			param.setSmlqtser("1");

	        ZSDT0167S zsdt0167s = new ZSDT0167S();
			zsdt0167s.createDao(session);
			// 0 -> "", 1 -> X 값으로 반영된다. - 해외모의견적도 가견적으로 바라본다. - 특이한 점은 원본 데이터 견적서가 존재한다는 것이 특이사항임
			zsdt0167s.insert(param.getMandt(), param.getSmlqtnum(), "1", paramVO.getVariable("G_USER_ID"));
	    } else {
	    	smlqtnum = (String)smlMap.get("SML_QTNUM");
	    	smlqtser = (String)smlMap.get("SML_QTSER");
		    param.setSmlqtnum(smlqtnum);
		    param.setSmlqtser(smlqtser);
        }
		
        Ses0030EDao.insertSmlZsdt1046(param);
		Ses0030EDao.insertSmlZsdt1091(param);
		Ses0030EDao.insertSmlZsdt1047(param);
		Ses0030EDao.insertSmlZsdt1093(param);
	    Ses0030EDao.insertSmlZsdt1048(param);
		Ses0030EDao.insertSmlZcobt309(param);
		Ses0030EDao.insertSmlZcobt302(param);
		Ses0030EDao.insertSmlZcobt202(param);
		Ses0030EDao.insertSmlZsdt1054D(param);
		Ses0030EDao.insertSmlZsdt1050(param);
		//Ses0030EDao.insertSmlZsdt0713(param);  // 견적번호 J(주차기),G(PSD),F(물류) COST
		//Ses0030EDao.insertSmlZsdt0712(param);  // 견적번호 J(주차기),G(PSD),F(물류) COST
		Ses0030EDao.insertSmlZsdt0711(param);
		Ses0030EDao.insertSmlZsdt1054H(param);
		Ses0030EDao.insertSmlZSDT1164(param);

        /*
		// 데이터를 삭제 처리
		Ses0030EDao.deleteSmlZsdt1054H(param);
		Ses0030EDao.deleteSmlZsdt0711(param);
		Ses0030EDao.deleteSmlZsdt0712(param);
		Ses0030EDao.deleteSmlZsdt0713(param);
		Ses0030EDao.deleteSmlZsdt1050(param);
		Ses0030EDao.deleteSmlZsdt1054D(param);
		Ses0030EDao.deleteSmlZcobt202(param);
		Ses0030EDao.deleteSmlZcobt302(param);
		Ses0030EDao.deleteSmlZcobt309(param);
		Ses0030EDao.deleteSmlZsdt1048(param);
		Ses0030EDao.deleteSmlZsdt1093(param);
		Ses0030EDao.deleteSmlZsdt1047(param);
		Ses0030EDao.deleteSmlZsdt1091(param);
		Ses0030EDao.deleteSmlZsdt1046(param);
		Ses0030EDao.deleteSmlZsdt1164(param);
		*/
	}

	public int deleteZsdt0711(String mandt, String qtnum, String qtser) {
		Ses0030ParamVO param = new Ses0030ParamVO();
		param.setMandt(mandt);
		param.setSmlqtnum(qtnum);
		param.setSmlqtser(qtser);
		return Ses0030EDao.deleteSmlZsdt0711(param);
	}
	
	// 모의견적마스터 생성
	public int insertZSDT1164(String mandt, String qtnum, String qtser, String smlqtnum, String smlqtser, String cuser) {
		Ses0030ParamVO param = new Ses0030ParamVO();
		param.setMandt(mandt);
		param.setQtnum(qtnum);
		param.setQtser(qtser);
		param.setSmlqtnum(smlqtnum);
		param.setSmlqtser(smlqtser);
		param.setCuser(cuser);
		
		
		
		return Ses0030EDao.insertSmlZSDT1164(param);
	}
	
	// 모의견적마스터 삭제 deleteSmlZsdt1164
	public int deleteSmlZsdt1164(String mandt, String qtnum, String qtser, String smlqtnum, String smlqtser) {
		Ses0030ParamVO param = new Ses0030ParamVO();
		param.setMandt(mandt);
		param.setQtnum(qtnum);
		param.setQtser(qtser);
		param.setSmlqtnum(smlqtnum);
		param.setSmlqtser(smlqtser);
		return Ses0030EDao.deleteSmlZsdt1164(param);
	}
}
