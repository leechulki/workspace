package hdel.sd.ses.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.domain.ZSDT1084;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0401D;
import hdel.sd.ses.dao.ZSDT1079D;
import hdel.sd.ses.domain.Ses0401;
import hdel.sd.ses.domain.Ses0401ParamVO;
import hdel.sd.ses.domain.ZSDT1079;


@Service
public class Ses0401S extends SrmService {

	private Ses0401D dao;
	private ZSDT1079D zsdt1079d;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Ses0401D.class);
		zsdt1079d = sqlSession.getMapper(hdel.sd.ses.dao.ZSDT1079D.class);
	}
	
	public List<Ses0401> selectListHeader(Ses0401ParamVO paramVO) {
		return dao.selectListHeader(paramVO);
	}
	
	public List<Ses0401> selectListFile(Ses0401ParamVO paramVO) {
		return dao.selectListFile(paramVO);
	}

	public List<ZSDT1079> searchChangedCharPart(String mandt, String zrqseq) {
		return zsdt1079d.select(mandt, zrqseq);
	}
	public List<Ses0401> selectPrintHeader(Ses0401ParamVO paramVO) {
		if ("Q".equals(paramVO.getGUBUN()))	{
			return dao.selectQtnumPrint(paramVO);
		}else	{
			return dao.selectProjectPrint(paramVO);
		}
	}

	public List<Ses0401> selectPrintTemplate(Ses0401ParamVO paramVO) {
			return dao.selectQtnumTemplate(paramVO);
	}

	public List<Ses0401> selectQtseq(Ses0401 param) {
			return dao.selectQtseq(param);
	}

	public String save(Ses0401 param, List<Ses0401> listFile) throws Exception {
		
		try {
			
			String ZRQSEQ  = param.getZRQSEQ();
			String ZRQSTAT = param.getZRQSTAT();
			
			List<Ses0401> list2 = dao.selectZkunnr_Z3(param);	
			if ( list2.size() > 0 ){
			  param.setKUNNR_Z3(list2.get(0).getKUNNR_Z3());
			}
			
			if ( ZRQSTAT.equals("R") ) {
				List<Ses0401> listStat = dao.selectStat(param);
				if ( listStat.size() > 0 ){
				  param.setZRQSTAT("Q");
				}
			}
			
			if ( ZRQSEQ.equals("") ) {
				
				List<Ses0401> list = dao.selectMaxZRqSeq(param);
				
				String strMaxSerial = list.get(0).getZRQSEQ();
				String strSerialGvgcd = strMaxSerial.substring(strMaxSerial.indexOf("-")+1, strMaxSerial.lastIndexOf("-"));

				if("".equals(strSerialGvgcd)) {
					strMaxSerial = strMaxSerial.substring(10, strMaxSerial.length());
				} else {
					strMaxSerial = strMaxSerial.substring(13, strMaxSerial.length());
				}
				
				String strNewSerial = StringUtils.leftPad((NumberUtils.toInt(strMaxSerial)+1)+"", 3, "0");
				
				String strNewZRQSEQ = "";
				strNewZRQSEQ += param.getZRQDAT() + "-";	// (8)+'-'
				strNewZRQSEQ += param.getGVGCD()  + "-";	// (3)+'-'
				strNewZRQSEQ += strNewSerial;				// (3)
				
				param.setZRQSEQ(strNewZRQSEQ);
				dao.insertHeader(param);
				
			} else {
				dao.updateHeader(param);
			}
			
			ZRQSEQ = param.getZRQSEQ();
			
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0401 paramFile = listFile.get(i);
				if ( paramFile.getZRQSEQ().equals("") ) {
					paramFile.setZRQSEQ(ZRQSEQ);
				}
			}
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0401 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("D") ) {
					dao.deleteFile(paramFile);
				}
			}
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0401 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("I") ) {
					dao.insertFile(paramFile);
				} else if ( paramFile.getFLAG().equals("U") ) {
					dao.updateFile(paramFile);
				}
			}

			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0401 paramFile = listFile.get(i);
				paramFile.setZRQSEQ("");
			}
		} catch (Exception e) {
			throw e;
		}
		
		return param.getZRQSEQ();
	}

	public void saveZrqstat(Ses0401 param) throws Exception {
		
		try {
			dao.updateZrqstat(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void deleteNInsert1079(String mandt, String zrqseq, List<ZSDT1079> ls1079) throws Exception {
		try {
			
			zsdt1079d.delete(mandt, zrqseq);

			if(ls1079.size() > 0) zsdt1079d.insert(ls1079);
		} catch (Exception e) {
			throw e;
		}
	}

	//¿ä±â
	public void saveApproval(Ses0401 param, List<Ses0401> listFile) throws Exception {
		
		try {
			
			String ZRQSEQ  = param.getZRQSEQ();
			
			dao.updateApproval(param);

			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0401 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("D") ) {
					dao.deleteFile(paramFile);
				}else if ( paramFile.getFLAG().equals("I") ) {
					dao.insertFile(paramFile);
				} else if ( paramFile.getFLAG().equals("U") ) {
					dao.updateFile(paramFile);
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void saveOutdate(Ses0401 param) throws Exception {
		
		try {
			dao.updateOutdate(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void saveOutcandt(Ses0401 param) throws Exception {
		
		try {
			dao.updateOutcandt(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void saveOutfinish(Ses0401 param) throws Exception {
		
		try {
			dao.updateOutfinish(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void saveOutactdt(Ses0401 param) throws Exception {
		
		try {
			dao.updateOutactdt(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void saveOutretdt(Ses0401 param) throws Exception {
		
		try {
			dao.updateOutretdt(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    public void deleteHeader(Ses0401 param) throws Exception {
		
		try {
			dao.deleteHeader(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    
    public void deleteFile2(Ses0401 param) throws Exception {
		
		try {
			dao.deleteFile(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    public void saveZ3all(Ses0401 param) throws Exception {
		
		try {
			dao.updateZ3all(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    
	public ZSDT1084 searchLastValidChgContract(String mandt, String qtnum, String qtser) {
		return dao.searchLastValidChgContract(mandt, qtnum, qtser);
	}
	
	public void UpdateErrortext(String mandt, String zrqseq, String error_text) throws Exception {
		try {
			dao.UpdateErrortext(mandt, zrqseq, error_text);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Ses0401> findUsercode(Ses0401ParamVO param) {
		return dao.findUsercode(param);
	}
	
public void saveDsFile2(List<Ses0401> listFile) throws Exception {
		
		try {
			
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0401 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("D") ) {
					dao.deleteFile(paramFile);
				}else if ( paramFile.getFLAG().equals("I") ) {
					dao.insertFile(paramFile);
				} else if ( paramFile.getFLAG().equals("U") ) {
					dao.updateFile(paramFile);
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	
}