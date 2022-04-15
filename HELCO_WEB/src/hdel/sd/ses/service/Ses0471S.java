package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0471D;
import hdel.sd.ses.domain.Ses0401;
import hdel.sd.ses.domain.Ses0471;
import hdel.sd.ses.domain.Ses0471ParamVO;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


@Service
public class Ses0471S extends SrmService {

	private Ses0471D dao;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Ses0471D.class);
	}
	
	public List<Ses0471> selectListHeader(Ses0471ParamVO paramVO) {
		return dao.selectListHeader(paramVO);
	}
	
	public List<Ses0471> selectListFile(Ses0471ParamVO paramVO) {
		return dao.selectListFile(paramVO);
	}

	public List<Ses0471> selectPrintHeader(Ses0471ParamVO paramVO) {
		if ("Q".equals(paramVO.getGUBUN()))	{
			return dao.selectQtnumPrint(paramVO);
		}else	{
			return dao.selectProjectPrint(paramVO);
		}
	}

	public List<Ses0471> selectPrintTemplate(Ses0471ParamVO paramVO) {
			return dao.selectQtnumTemplate(paramVO);
	}

	public List<Ses0471> selectQtseq(Ses0471 param) {
			return dao.selectQtseq(param);
	}

	public String save(Ses0471 param, List<Ses0471> listFile) throws Exception {
		
		try {
			
			String ZRQSEQ  = param.getZRQSEQ();
			
			//이전 차수 기영담당자 존재시, 매칭 작업 2013.08.06
			List<Ses0471> list2 = dao.selectZkunnr_Z3(param);	
			if ( list2.size() > 0 ){
			  param.setKUNNR_Z3(list2.get(0).getKUNNR_Z3());
			}
			
			if ( ZRQSEQ.equals("") ) {
				
				List<Ses0471> list = dao.selectMaxZRqSeq(param);
				
				String strMaxSerial = list.get(0).getZRQSEQ();
				strMaxSerial = strMaxSerial.substring(13, strMaxSerial.length());
				String strNewSerial = StringUtils.leftPad((Integer.parseInt(strMaxSerial)+1)+"", 3, "0");
				
				String strNewZRQSEQ = "";
				strNewZRQSEQ += param.getZRQDAT() + "-";	// 일자   (8)+'-'
				strNewZRQSEQ += param.getGVGCD()  + "-";	// 부서   (3)+'-'
				strNewZRQSEQ += strNewSerial;				// SERIAL (3)
				
				param.setZRQSEQ(strNewZRQSEQ);
				dao.insertHeader(param);
				
			} else {
				dao.updateHeader(param);
			}
			
			ZRQSEQ = param.getZRQSEQ();
			
			// 첨부파일 저장
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0471 paramFile = listFile.get(i);
				if ( paramFile.getZRQSEQ().equals("") ) {
					paramFile.setZRQSEQ(ZRQSEQ);
				}
			}
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0471 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("D") ) {
					dao.deleteFile(paramFile);
				}
			}
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0471 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("I") ) {
					dao.insertFile(paramFile);
				} else if ( paramFile.getFLAG().equals("U") ) {
					dao.updateFile(paramFile);
				}
			}

			// 첨부파일 저장 후 일괄 도면 요청을 위해 listFile의 ZRQSEQ 의 값을 "" 로 바꿔준다.
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0471 paramFile = listFile.get(i);
				paramFile.setZRQSEQ("");
			}
		} catch (Exception e) {
			throw e;
		}
		
		return param.getZRQSEQ();
	}

	public void saveZrqstat(Ses0471 param) throws Exception {
		
		try {
			dao.updateZrqstat(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}

	public void saveApproval(Ses0471 param, List<Ses0471> listFile) throws Exception {
		
		try {
			
			String ZRQSEQ  = param.getZRQSEQ();
			
			dao.updateApproval(param);
			
			// 첨부파일 저장
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0471 paramFile = listFile.get(i);
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
	public void saveOutdate(Ses0471 param) throws Exception {
		
		try {
			dao.updateOutdate(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void saveOutcandt(Ses0471 param) throws Exception {
		
		try {
			dao.updateOutcandt(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void saveOutfinish(Ses0471 param) throws Exception {
		
		try {
			dao.updateOutfinish(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    public void deleteHeader(Ses0471 param) throws Exception {
		
		try {
			dao.deleteHeader(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    
    public void deleteFile2(Ses0471 param) throws Exception {
		
		try {
			dao.deleteFile(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}

}
