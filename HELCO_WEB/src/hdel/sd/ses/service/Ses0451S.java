package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0451D;
import hdel.sd.ses.domain.Ses0451;
import hdel.sd.ses.domain.Ses0451ParamVO;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class Ses0451S extends SrmService {

	private Ses0451D Ses0451Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0451Dao = sqlSession.getMapper(Ses0451D.class);
	}
	
	public List<Ses0451> getListHeader(Ses0451ParamVO param) {
		return Ses0451Dao.selectListHeader(param);
	}
	
	public List<Ses0451> getListTemplateQtnum(Ses0451ParamVO param) {
		return Ses0451Dao.selectListTemplateQtnum(param);
	}
	
	public List<Ses0451> getListTemplateSd120(Ses0451ParamVO param) {
		return Ses0451Dao.selectListTemplateSd120(param);
	}
	
	public List<Ses0451> getListFile(Ses0451ParamVO paramVO) {		
		return Ses0451Dao.selectListFile(paramVO);
	}	
	
	public List<Ses0451> getListEmail(Ses0451ParamVO param) {
		return Ses0451Dao.selectListEmail(param);
	}
	
	public String save(Ses0451 param, List<Ses0451> listFile) throws Exception {
		
		try {
			
			String ZRQSEQ  = param.getZrqseq();
			
			if ( ZRQSEQ.equals("") ) {
				
				List<Ses0451> list = Ses0451Dao.selectMaxZRqSeq(param);
				
				String strMaxSerial = list.get(0).getZrqseq();
				strMaxSerial = strMaxSerial.substring(13, strMaxSerial.length());
				String strNewSerial = StringUtils.leftPad((Integer.parseInt(strMaxSerial)+1)+"", 3, "0");
				
				String strNewZRQSEQ = "";
				strNewZRQSEQ += param.getZrqdat() + "-";	// 老磊   (8)+'-'
				strNewZRQSEQ += param.getGvgcd()  + "-";	// 何辑   (3)+'-'
				strNewZRQSEQ += strNewSerial;				// SERIAL (3)
				
				param.setZrqseq(strNewZRQSEQ);			
				Ses0451Dao.insertHeader(param);
				
			} else {			
				Ses0451Dao.updateHeader(param);
			}
			
			ZRQSEQ = param.getZrqseq();
			
			// 梅何颇老 历厘
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0451 paramFile = listFile.get(i);
				if ( paramFile.getZrqseq().equals("") ) {					
					paramFile.setZrqseq(ZRQSEQ);					
				}
			}		
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0451 paramFile = listFile.get(i);
				if ( paramFile.getFlag().equals("D") ) {				
					Ses0451Dao.deleteFile(paramFile); 
				}else if ( paramFile.getFlag().equals("I") ) {					
					Ses0451Dao.insertFile(paramFile);
				} else if ( paramFile.getFlag().equals("U") ) {					
					Ses0451Dao.updateFile(paramFile);
				}
			}			
			
		} catch (Exception e) {
			throw e;
		}
		
		return param.getZrqseq();
	}

	public void saveZrqstat(Ses0451 param) throws Exception {
		try {
			Ses0451Dao.updateZrqstat(param);
		} catch (Exception e) {
			throw e;
		}
	}

	
	public void saveApproval(Ses0451 param, List<Ses0451> listFile) throws Exception {
		
		try {
			
			Ses0451Dao.updateApproval(param);		
			
			// 梅何颇老 历厘
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0451 paramFile = listFile.get(i);
				if ( paramFile.getFlag().equals("D") ) {
					Ses0451Dao.deleteFile(paramFile);
				}else if ( paramFile.getFlag().equals("I") ) {
					Ses0451Dao.insertFile(paramFile);
				} else if ( paramFile.getFlag().equals("U") ) {
					Ses0451Dao.updateFile(paramFile);
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}	
	

}
