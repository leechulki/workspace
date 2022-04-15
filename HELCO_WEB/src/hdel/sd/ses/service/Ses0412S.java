package hdel.sd.ses.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0412D;
import hdel.sd.ses.dao.Ses0414D;
import hdel.sd.ses.domain.Ses0412;
import hdel.sd.ses.domain.Ses0412ParamVO;
import hdel.sd.ses.domain.Ses0414;
import hdel.sd.ses.domain.ZPST0011;


@Service
public class Ses0412S extends SrmService {

	private Ses0412D dao;
	private Ses0414D ses0414d;
	
	public void createDao(SqlSession sqlSession) { 
		dao = sqlSession.getMapper(Ses0412D.class);
		ses0414d = sqlSession.getMapper(hdel.sd.ses.dao.Ses0414D.class);
	}
	
	public List<Ses0412> selectListHeader(Ses0412ParamVO paramVO) {
		return dao.selectListHeader(paramVO);
	}
	
	public List<Ses0412> selectListFile(Ses0412ParamVO paramVO) {
		return dao.selectListFile(paramVO);
	}

	public List<Ses0412> selectPrintHeader(Ses0412ParamVO paramVO) {
		if ("Q".equals(paramVO.getGUBUN()))	{
			return dao.selectQtnumPrint(paramVO);
		}else	{
			return dao.selectProjectPrint(paramVO);
		}
	}

	public List<Ses0412> selectPrintTemplate(Ses0412ParamVO paramVO) {
			return dao.selectQtnumTemplate(paramVO);
	}

	public String save(Ses0412 param, List<Ses0412> listFile) throws Exception {
		
		try {
			
			String ZRQSEQ  = param.getZRQSEQ();
			
			//이전 차수 기영담당자 존재시, 매칭 작업 2013.08.06
			List<Ses0412> list2 = dao.selectZkunnr_Z3(param);	
			if ( list2.size() > 0 ){
			  param.setKUNNR_Z3(list2.get(0).getKUNNR_Z3());
			}
			
			if ( ZRQSEQ.equals("") ) {
				
				List<Ses0412> list = dao.selectMaxZRqSeq(param);
				
				String strMaxSerial = list.get(0).getZRQSEQ();
				strMaxSerial = strMaxSerial.substring(15, strMaxSerial.length());
				String strNewSerial = StringUtils.leftPad((Integer.parseInt(strMaxSerial)+1)+"", 3, "0");
				
				String strNewZRQSEQ = "";				
				strNewZRQSEQ += "X-";                       // 현장지원 구분자 
				strNewZRQSEQ += param.getZRQDAT() + "-";	// 일자   (8)+'-'
				if ( param.getGVGCD().equals("") ){
					strNewZRQSEQ += "XXX-";	// 부서   (3)+'-'
				} else {
					strNewZRQSEQ += param.getGVGCD()  + "-";	// 부서   (3)+'-'
				}
				strNewZRQSEQ += strNewSerial;				// SERIAL (3)
				
				param.setZRQSEQ(strNewZRQSEQ);
				dao.insertHeader(param);
				//dao.insertHeaderPs(param);  zpsdt1113 인서트 로직 제외 요청 20160310
				
			} else {
				dao.updateHeader(param);
			}
			
			ZRQSEQ = param.getZRQSEQ();
			
			// 첨부파일 저장
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0412 paramFile = listFile.get(i);
				if ( paramFile.getZRQSEQ().equals("") ) {
					paramFile.setZRQSEQ(ZRQSEQ);
				}
			}
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0412 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("D") ) {
					dao.deleteFile(paramFile);
				}
			}
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0412 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("I") ) {
					dao.insertFile(paramFile);
				} else if ( paramFile.getFLAG().equals("U") ) {
					dao.updateFile(paramFile);
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return param.getZRQSEQ();
	}

	public void saveZrqstat(Ses0412 param) throws Exception {
		
		try {
			dao.updateZrqstat(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}

	public void saveApproval(Ses0412 param, List<Ses0412> listFile) throws Exception {
		
		try {
			
			String ZRQSEQ  = param.getZRQSEQ();
			
			dao.updateApproval(param);
			
			// 첨부파일 저장
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0412 paramFile = listFile.get(i);
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
	public void saveOutdate(Ses0412 param) throws Exception {
		
		try {
			dao.updateOutdate(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void saveOutfinish(Ses0412 param) throws Exception {
		
		try {
			dao.updateOutfinish(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    public void deleteHeader(Ses0412 param) throws Exception {
		
		try {
			dao.deleteHeader(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    
    public void deleteFile2(Ses0412 param) throws Exception {
    			
		try {
			dao.deleteFile(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}

    public List<Ses0414> selectRepPm(String mandt, String temno, String vkgrp) {
		return ses0414d.selectRepPm(mandt, temno, vkgrp);
    }

    public List<Ses0414> searchPsPm(String mandt, String temno) {
    	return ses0414d.searchPsPm(mandt, temno);
    }

    public List<Ses0414> searchRepPmByTeam(String mandt, String vkgrp) {
    	return ses0414d.searchRepPmByTeam(mandt, vkgrp);
    }
}
