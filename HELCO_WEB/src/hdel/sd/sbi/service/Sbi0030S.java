package hdel.sd.sbi.service;

import hdel.lib.service.SrmService;
import hdel.sd.sbi.dao.Sbi0030D;
import hdel.sd.sbi.domain.Sbi0030;
import hdel.sd.sbi.domain.Sbi0030ParamVO;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


@Service
public class Sbi0030S extends SrmService {

	private Sbi0030D dao;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Sbi0030D.class);
	}
	
	public List<Sbi0030> selectListHeader(Sbi0030ParamVO paramVO) {
		return dao.selectListHeader(paramVO);
	}
	
	public List<Sbi0030> selectListFile(Sbi0030ParamVO paramVO) {
		return dao.selectListFile(paramVO);
	}

	public String save(Sbi0030 param, List<Sbi0030> listFile) throws Exception {
		
		try {
			
			String INFNO  = param.getINFNO();
			
			if ( INFNO.equals("") ) {
				
				List<Sbi0030> list = dao.selectMaxInfNo(param);
				
				String strMaxSerial = list.get(0).getINFNO();
				String strNewSerial = String.valueOf(Integer.parseInt(strMaxSerial)+1);
			
				param.setINFNO(strNewSerial);
				
				dao.insertHeader(param);
				
			} else {
				dao.updateHeader(param);
			}
			
			INFNO = param.getINFNO();
			/*
			// 첨부파일 저장
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Sbi0030 paramFile = listFile.get(i);
				if ( paramFile.getINFNO().equals("") ) {
					paramFile.setINFNO(INFNO);
				}
			}
			
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Sbi0030 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("D") ) {
					dao.deleteFile(paramFile);
				}
			}
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Sbi0030 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("I") ) {
					dao.insertFile(paramFile);
				} else if ( paramFile.getFLAG().equals("U") ) {
					dao.updateFile(paramFile);
				}
			}*/
			
		} catch (Exception e) {
			throw e;
		}
		
		return param.getINFNO();
	}

}
