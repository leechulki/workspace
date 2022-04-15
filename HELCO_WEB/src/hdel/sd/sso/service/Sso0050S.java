package hdel.sd.sso.service;

/**
 * 수주변경
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
 */
import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0031D;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.sso.dao.Sso0050D;
import hdel.sd.sso.domain.Sso0050ParamVO;
import hdel.sd.sso.domain.Sso0050VO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Sso0050S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sso0050D dao;
	private Ses0031D Ses0031Dao;
	
	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub		
		dao = sqlSession.getMapper(Sso0050D.class);
		
	} 
	
	public void createSes0031Dao(SqlSession sqlSession) {
		Ses0031Dao = sqlSession.getMapper(Ses0031D.class);
	}
	
	// 조회
	public List<Sso0050VO> find(Sso0050ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		
		return dao.SelectSayang(paramV);
		
	}
	
	// 조회
	public List<Sso0050VO> findVbeln(Sso0050ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		
		return dao.SelectVbeln(paramV);
		
	}
	
	// 조회
	public List<Sso0050VO> findSayangForPrint(Sso0050ParamVO paramV) {
		logger.info("@@@@@@@@@@ findSayangForPrint service in -> dao @@@@@@@@@");
		return dao.SelectSayangForPrint(paramV);
	}
	
	public Dataset deleteZsdt1054(MipParameterVO paramVO, Model model, SqlSession session) {

		Dataset dsZsdt1054 = paramVO.getDataset("ds_output_char");

		try {
			createSes0031Dao(session);  // DAO생성
			
			String mandt = paramVO.getVariable("G_MANDT");
			String pspid = DatasetUtility.getString(dsZsdt1054, 0, "PSPID");
			String posid = DatasetUtility.getString(dsZsdt1054, 0, "POSID");

			if (pspid == null) pspid = "";
			if (posid == null) posid = "0";
			//System.out.print("\n@@@ MANDT ===>"+ mandt +"\n");
			//System.out.print("\n@@@ PSPID ===>"+ pspid +"\n");
			//System.out.print("\n@@@ POSID   ===>"+ posid   +"\n");

			Ses0031ParamVO param = new Ses0031ParamVO();

			param.setMandt(mandt );	 // SAP CLIENT
			param.setPspid(pspid);
			param.setPosid(posid);

			//deleteZsdt1054H(param); // 2013.03.20 제거
			//deleteZsdt1054D(param); // 2013.03.20 제거

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsZsdt1054;
	}
	
	public void setCostState(MipParameterVO paramVO) {
		Dataset dsZsdt0090 = paramVO.getDataset("ds_cond");
		
		try {
			
			String mandt = paramVO.getVariable("G_MANDT");
			String pspid = DatasetUtility.getString(dsZsdt0090, 0, "PSPID");
			String seq   = DatasetUtility.getString(dsZsdt0090, 0, "SEQ"  );
			String finl  = DatasetUtility.getString(dsZsdt0090, 0, "FINL" );
			
			if (mandt == null) mandt = "";
			if (pspid == null) pspid = "";
			if (seq   == null) seq   = "0";
			if (finl  == null) finl  = "";
			
			//System.out.print("\n@@@ MANDT ===>"+ mandt +"\n");
			//System.out.print("\n@@@ PSPID ===>"+ pspid +"\n");
			//System.out.print("\n@@@ POSID ===>"+ posid +"\n");
			//System.out.print("\n@@@ FINL  ===>"+ finl  +"\n");
			
			Sso0050ParamVO param = new Sso0050ParamVO();
			
			param.setMANDT(mandt );	 // SAP CLIENT
			param.setPSPID(pspid);
			param.setSEQ(seq);
			param.setFINL(finl);
			
			dao.setCostState(param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int deleteZsdt1054H(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1054H(param);
	}

	public int deleteZsdt1054D(Ses0031ParamVO param) {
		return Ses0031Dao.deleteZsdt1054D(param);
	}
	
	public List<Sso0050VO> searchZcobt304(Sso0050ParamVO param) {
		List<Sso0050VO> list = dao.searchZcobt304D(param); //zcobt304D 체크후 리턴 
		
		if ( list.size() > 0 ) {
			//return dao.searchZcobt304D(param);
			return list;
		} else {
			return dao.searchZcobt304(param);
		}
		
	}

	public List<Sso0050VO> searchBlockName(Sso0050ParamVO param) {
		List<Sso0050VO> list2 = dao.searchZcobt204D(param); //zcobt204D 체크후 리턴
		
		if ( list2.size() > 0 ) {
			return dao.selectListBlockNameD(param);
		} else {
			return dao.selectListBlockName(param);
		}
	}
	
	/**
	 * 2013.02.20 - 매매기준환율 조회
	 * 2018.03.06 - 환율조회 공통 모듈화 (com.ExchangeS.class)

	public List<Sso0050VO> searchExchange(Sso0050ParamVO param) {
		return dao.selectListExchange(param);
	}
	*/
	public List<Sso0050VO> checkZcobt204D(Sso0050ParamVO param) {
		return dao.searchZcobt204D(param);
	}
	public List<Sso0050VO> searchBlockNameD(Sso0050ParamVO param) {
		return dao.selectListBlockNameD(param);
	}
	
}
