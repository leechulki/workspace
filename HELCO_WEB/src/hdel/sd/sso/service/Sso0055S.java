package hdel.sd.sso.service;

/**
 * ���ֺ���
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0031D;
import hdel.sd.ses.domain.Ses0031ParamVO;
import hdel.sd.smp.control.SmpComC;
import hdel.sd.sso.dao.Sso0055D;
import hdel.sd.sso.domain.Sso0055ParamVO;
import hdel.sd.sso.domain.Sso0055VO;
import hdel.sd.sso.domain.ZSDS0063;
import hdel.sd.sso.domain.ZSDT0090;
import hdel.sd.sso.domain.ZSDT0091;
import hdel.sd.sso.domain.ZSDT0092;
import hdel.sd.sso.domain.ZSDT0093;
import hdel.sd.sso.domain.ZSDT0094;

import java.util.List;
import java.math.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Sso0055S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Sso0055D dao;
	private Ses0031D Ses0031Dao;
	
	@Override

	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Sso0055D.class);
	}
	
	public void createSes0031Dao(SqlSession sqlSession) {
		Ses0031Dao = sqlSession.getMapper(Ses0031D.class);
	}
	
	// ���� �������� ��ȸ	
	public List<Sso0055VO> selectMaxSeq(Sso0055ParamVO param) {
		
		return dao.SelectMaxSeq(param);
			
	}
	// ������Ʈ ������ ��ȸ
	public List<ZSDS0063> searchProject(ZSDS0063 param) {
		
		return dao.SelectProject(param);
			
	}

	// ������Ʈ ������ ��ȸ
	public List<ZSDS0063> searchQtdat(ZSDS0063 param) {
		
		return dao.SelectQtdat(param);
			
	}
	
	// ��ຯ�� header ������ ��ȸ
	public List<ZSDT0090> searchHeader(ZSDT0090 param) {
		
		return dao.SelectHeader(param);
			
	}
	// ��ຯ��item ������ ��ȸ
	public List<ZSDT0091> searchItem(ZSDT0091 param) {
		
		return dao.SelectItem(param);
			
	}
	// ��ຯ�� billing ���� ������ ��ȸ
	public List<ZSDT0092> searchBillO(ZSDT0092 param) {
		
		return dao.SelectBillO(param);
			
	}
	// ��ຯ�� billing ���� ������ ��ȸ
	public List<ZSDT0093> searchBillC(ZSDT0093 param) {
		
		return dao.SelectBillC(param);
			
	}
	// ��ຯ��  ������ ������ ��ȸ
	public List<ZSDT0094> searchSpecC(ZSDT0094 param) {
		
		return dao.SelectSpecC(param);
			
	}
	

	public Dataset saveData(MipParameterVO paramVO, Model model, SqlSession session) throws Exception {

		Dataset ds_ZSDS0063	= paramVO.getDataset("ds_output_ZSDS0063");				// ZSDS0063 			// sap output list
		Dataset ds_ZSDT0090  = paramVO.getDataset("ds_output_ZSDT0090");
		Dataset ds_ZSDT0091  = paramVO.getDataset("ds_output_ZSDT0091");
		Dataset ds_ZSDT0092  = paramVO.getDataset("ds_output_ZSDT0092");
		Dataset ds_ZSDT0093  = paramVO.getDataset("ds_output_ZSDT0093");
		Dataset ds_ZSDT0094  = paramVO.getDataset("ds_output_ZSDT0094");
/*
		ZSDT0090[] ds0090	= (ZSDT0090[])SmpComC.moveDs2Obj(ds_ZSDT0090, ZSDT0090.class, "");  			// sap output list
		ZSDT0091[] ds0091	= (ZSDT0091[])SmpComC.moveDs2Obj(ds_ZSDT0091, ZSDT0091.class, "");  			// sap output list
		ZSDT0092[] ds0092	= (ZSDT0092[])SmpComC.moveDs2Obj(ds_ZSDT0092, ZSDT0092.class, "");  			// sap output list
		ZSDT0093[] ds0093	= (ZSDT0093[])SmpComC.moveDs2Obj(ds_ZSDT0093, ZSDT0093.class, "");  			// sap output list
		ZSDT0094[] ds0094	= (ZSDT0094[])SmpComC.moveDs2Obj(ds_ZSDT0094, ZSDT0094.class, "");  			// sap output list
  */      
		ZSDT0090 param90 = new ZSDT0090();
		ZSDT0091 param91 = new ZSDT0091();
		ZSDT0092 param92 = new ZSDT0092();
		ZSDT0093 param93 = new ZSDT0093();
		ZSDT0094 param94 = new ZSDT0094();
		
		try{	
			createDao(session);  // DAO����
			
			String MANDT         = "";		String VBELN         = "";		String PSPID         = "";
			String SEQ           = "";		String ZQNTY         = "";		Integer NETWR         = 0;
			Integer WAVWR        = 0;		Integer COMMI        = 0;		String VDATU         = "";
			String WAERK         = "";		Integer CHNETWR      = 0;		Integer CHWAVWR       = 0;
			Integer CHCOMMI     = 0;		String CHVDATU       = "";		String CHWAERK       = "";
			String FINL          = "";		String BIGO1         = "";		String BIGO2         = "";
			String BIGO3         = "";		String BIGO4         = "";		String BIGO5         = "";
			String CHRES         = "";		String REDEP         = "";		String CHRES_A       = "";
			String REDEP_A       = "";		String CHRES_B       = "";		String REDEP_B       = "";
			String CHRES_C       = "";		String REDEP_C       = "";		String CHRES_D       = "";
			String REDEP_D       = "";		String CHRES_E       = "";		String REDEP_E       = "";
			String ERDAT         = "";		String ERNAM         = "";
		
			String POSNR         = "";		String HOGI          = "";		String AUART         = "";
			String KZWI4         = "";		String EDATU         = "";		String REPMO         = "";
			String GUAMO         = "";		String WORMO         = "";		String CHEDATU       = "";
			String CHREPMO       = "";		String CHGUAMO       = "";		String CHWORMO       = "";
			String CHCK          = "";		String HOGI_CANC     = "";		String SPEC_STAT     = "";
			String MATNR         = "";		String CLASS         = "";		String ARKTX         = "";
		
			String FKDAT         = "";		String MLBEZ         = "";		String ZTERM         = "";
			String FKSAF         = "";		String FPLTR         = "";		String MSTXT         = "";
			String FAKWR         = "";
		
			String CHFKDAT       = "";		String CHMLBEZ       = "";		String CHZTERM       = "";
			String CHFKSAF       = "";		String CHMSTXT       = "";		String CHFAKWR       = "";
		
			String NAM_CHAR      = "";		String ATBEZ         = "";		String CHAR_VALUE    = "";
			String CHAR_VALUEMD  = "";		String ATWTB         = "";		String ATKLA         = "";
			String CNT           = "";		String CNGBN         = "";
			
			for (int i = 0 ; i < ds_ZSDT0090.getRowCount(); i++){
				MANDT            = DatasetUtility.getString(ds_ZSDT0090, i, "MANDT");
				VBELN            = DatasetUtility.getString(ds_ZSDT0090, i, "VBELN"  );
				PSPID            = DatasetUtility.getString(ds_ZSDT0090, i, "PSPID"  );
				SEQ              = DatasetUtility.getString(ds_ZSDT0090, i, "SEQ"    );
				ZQNTY            = DatasetUtility.getString(ds_ZSDT0090, i, "ZQNTY"  );
				NETWR            = DatasetUtility.getInt(ds_ZSDT0090, i, "NETWR"  );
				WAVWR            = DatasetUtility.getInt(ds_ZSDT0090, i, "WAVWR"  );
				COMMI            = DatasetUtility.getInt(ds_ZSDT0090, i, "COMMI"  );
				VDATU            = DatasetUtility.getString(ds_ZSDT0090, i, "VDATU"  );
				WAERK            = DatasetUtility.getString(ds_ZSDT0090, i, "WAERK"  );
				CHNETWR          = DatasetUtility.getInt(ds_ZSDT0090, i, "CHNETWR");
				CHWAVWR          = DatasetUtility.getInt(ds_ZSDT0090, i, "CHWAVWR");
				CHCOMMI          = DatasetUtility.getInt(ds_ZSDT0090, i, "CHCOMMI");
				CHVDATU          = DatasetUtility.getString(ds_ZSDT0090, i, "CHVDATU");
				CHWAERK          = DatasetUtility.getString(ds_ZSDT0090, i, "CHWAERK");
				FINL             = DatasetUtility.getString(ds_ZSDT0090, i, "FINL"   );
				BIGO1            = DatasetUtility.getString(ds_ZSDT0090, i, "BIGO1"  );
				BIGO2            = DatasetUtility.getString(ds_ZSDT0090, i, "BIGO2"  );
				BIGO3            = DatasetUtility.getString(ds_ZSDT0090, i, "BIGO3"  );
				BIGO4            = DatasetUtility.getString(ds_ZSDT0090, i, "BIGO4"  );
				BIGO5            = DatasetUtility.getString(ds_ZSDT0090, i, "BIGO5"  );
				CHRES            = DatasetUtility.getString(ds_ZSDT0090, i, "CHRES"  );
				REDEP            = DatasetUtility.getString(ds_ZSDT0090, i, "REDEP"  );
				CHRES_A          = DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_A");
				REDEP_A          = DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_A");
				CHRES_B          = DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_B");
				REDEP_B          = DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_B");
				CHRES_C          = DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_C");
				REDEP_C          = DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_C");
				CHRES_D          = DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_D");
				REDEP_D          = DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_D");
				CHRES_E          = DatasetUtility.getString(ds_ZSDT0090, i, "CHRES_E");
				REDEP_E          = DatasetUtility.getString(ds_ZSDT0090, i, "REDEP_E");
				ERDAT            = DatasetUtility.getString(ds_ZSDT0090, i, "ERDAT"  );
				ERNAM            = DatasetUtility.getString(ds_ZSDT0090, i, "ERNAM"  );	
	
				if (MANDT    ==  null ) MANDT   = paramVO.getVariable("G_MANDT");
				if ( VBELN   ==  null ) VBELN   = "";
				if ( PSPID   ==  null ) PSPID   = "";
				if ( SEQ     ==  null ) SEQ     = "0";
				if ( ZQNTY   ==  null ) ZQNTY   = "0";
				if ( NETWR   ==  null ) NETWR   = 0;
				if ( WAVWR   ==  null ) WAVWR   = 0;
				if ( COMMI   ==  null ) COMMI   = 0;
				if ( VDATU   ==  null ) VDATU   = "";
				if ( WAERK   ==  null ) WAERK   = "";
				if ( CHNETWR ==  null ) CHNETWR = 0;
				if ( CHWAVWR ==  null ) CHWAVWR = 0;
				if ( CHCOMMI ==  null ) CHCOMMI = 0;
				if ( CHVDATU ==  null ) CHVDATU = "";
				if ( CHWAERK ==  null ) CHWAERK = "";
				if ( FINL    ==  null ) FINL    = "";
				if ( BIGO1   ==  null ) BIGO1   = "";
				if ( BIGO2   ==  null ) BIGO2   = "";
				if ( BIGO3   ==  null ) BIGO3   = "";
				if ( BIGO4   ==  null ) BIGO4   = "";
				if ( BIGO5   ==  null ) BIGO5   = "";
				if ( CHRES   ==  null ) CHRES   = "";
				if ( REDEP   ==  null ) REDEP   = "";
				if ( CHRES_A ==  null ) CHRES_A = "";
				if ( REDEP_A ==  null ) REDEP_A = "";
				if ( CHRES_B ==  null ) CHRES_B = "";
				if ( REDEP_B ==  null ) REDEP_B = "";
				if ( CHRES_C ==  null ) CHRES_C = "";
				if ( REDEP_C ==  null ) REDEP_C = "";
				if ( CHRES_D ==  null ) CHRES_D = "";
				if ( REDEP_D ==  null ) REDEP_D = "";
				if ( CHRES_E ==  null ) CHRES_E = "";
				if ( REDEP_E ==  null ) REDEP_E = "";
				if ( ERDAT   ==  null ) ERDAT   = "";
				if ( ERNAM   ==  null ) ERNAM   = "";		
				
				param90.setMANDT  (MANDT   );		
				param90.setVBELN  (VBELN   );
				param90.setPSPID  (PSPID   );
				param90.setSEQ    (SEQ     );
				param90.setZQNTY  (ZQNTY   );
				param90.setNETWR  (BigDecimal.valueOf(NETWR) );
				param90.setWAVWR  (BigDecimal.valueOf(WAVWR) );
				param90.setCOMMI  (BigDecimal.valueOf(COMMI) );
				//param90.setWAVWR  (BigDecimal.valueOf(Integer.parseInt(WAVWR)) );
				//param90.setCOMMI  (BigDecimal.valueOf(Integer.parseInt(COMMI))   );
				param90.setVDATU  (VDATU   );
				param90.setWAERK  (WAERK   );
				param90.setCHNETWR(BigDecimal.valueOf(CHNETWR) );
				param90.setCHWAVWR(BigDecimal.valueOf(CHWAVWR) );
				param90.setCHCOMMI(BigDecimal.valueOf(CHCOMMI) );
				param90.setCHVDATU(CHVDATU );
				param90.setCHWAERK(CHWAERK );
				param90.setFINL   (FINL    );
				param90.setBIGO1  (BIGO1   );
				param90.setBIGO2  (BIGO2   );
				param90.setBIGO3  (BIGO3   );
				param90.setBIGO4  (BIGO4   );
				param90.setBIGO5  (BIGO5   );
				param90.setCHRES  (CHRES   );
				param90.setREDEP  (REDEP   );
				param90.setCHRES_A(CHRES_A );
				param90.setREDEP_A(REDEP_A );
				param90.setCHRES_B(CHRES_B );
				param90.setREDEP_B(REDEP_B );
				param90.setCHRES_C(CHRES_C );
				param90.setREDEP_C(REDEP_C );
				param90.setCHRES_D(CHRES_D );
				param90.setREDEP_D(REDEP_D );
				param90.setCHRES_E(CHRES_E );
				param90.setREDEP_E(REDEP_E );
				param90.setERDAT  (ERDAT   );
				param90.setERNAM  (ERNAM   );
			}
			dao.saveHeader(param90);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ds_ZSDS0063;
	}
	
	// ��ȸ
	public List<Sso0055VO> find(Sso0055ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		
		return dao.SelectSayang(paramV);
		
	}

	// ��ȸ
	public List<Sso0055VO> findVbeln(Sso0055ParamVO paramV) {
		logger.info("@@@@@@@@@@ find service in -> dao @@@@@@@@@");
		
		return dao.SelectVbeln(paramV);
		
	}
	// ��ȸ
	public List<Sso0055VO> findVbeln2(Sso0055ParamVO paramV) {
		
		return dao.SelectVbeln2(paramV);
		
	}
	
	// ��ȸ
	public List<Sso0055VO> findSayangForPrint(Sso0055ParamVO paramV) {
		logger.info("@@@@@@@@@@ findSayangForPrint service in -> dao @@@@@@@@@");
		return dao.SelectSayangForPrint(paramV);
	}
	
	public List<Sso0055VO> selectRecad_da(Sso0055ParamVO paramV) {
		return dao.getRecad_da(paramV);
	}
	
	public Dataset deleteZsdt1054(MipParameterVO paramVO, Model model, SqlSession session) {

		Dataset dsZsdt1054 = paramVO.getDataset("ds_output_char");

		try {
			createSes0031Dao(session);  // DAO����
			
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

			//deleteZsdt1054H(param); // 2013.03.20 ����
			//deleteZsdt1054D(param); // 2013.03.20 ����

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
			
			Sso0055ParamVO param = new Sso0055ParamVO();
			
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
	
	public List<Sso0055VO> searchZcobt304(Sso0055ParamVO param) {
		List<Sso0055VO> list = dao.searchZcobt304D(param); //zcobt304D üũ�� ���� 
		
		if ( list.size() > 0 ) {
			//return dao.searchZcobt304D(param);
			return list;
		} else {
			return dao.searchZcobt304(param);
		}
		
	}

	public List<Sso0055VO> searchBlockName(Sso0055ParamVO param) {
		List<Sso0055VO> list2 = dao.searchZcobt204D(param); //zcobt204D üũ�� ����
		
		if ( list2.size() > 0 ) {
			return dao.selectListBlockNameD(param);
		} else {
			return dao.selectListBlockName(param);
		}
	}
	
	/**
	 * �Ÿű���ȯ�� ��ȸ 2013.02.20
	 * @param param
	 * @return
	 */
	public List<Sso0055VO> searchExchange(Sso0055ParamVO param) {
		return dao.selectListExchange(param);
	}
	public List<Sso0055VO> checkZcobt204D(Sso0055ParamVO param) {
		return dao.searchZcobt204D(param);
	}
	public List<Sso0055VO> searchBlockNameD(Sso0055ParamVO param) {
		return dao.selectListBlockNameD(param);
	}

	
	
}
