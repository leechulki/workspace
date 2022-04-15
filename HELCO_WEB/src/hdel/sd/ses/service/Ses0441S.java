package hdel.sd.ses.service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0441D;
import hdel.sd.ses.domain.Ses0401;
import hdel.sd.ses.domain.Ses0441;
import hdel.sd.ses.domain.Ses0441ParamVO;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


@Service
public class Ses0441S extends SrmService {

	private Ses0441D dao;
	
	public void createDao(SqlSession sqlSession) {
		dao = sqlSession.getMapper(Ses0441D.class);
	}
	
	public List<Ses0441> selectList(Ses0441ParamVO paramVO) {

		String RQSER  = paramVO.getRQSER();
					
		if ( RQSER.equals("") ) {
			return dao.selectListMax(paramVO);
		} else {
			return dao.selectList(paramVO);
		}
		
	}
	
	public List<Ses0441> selectListFile(Ses0441ParamVO paramVO) {

		String RQSER  = paramVO.getRQSER();
					
		if ( RQSER.equals("") ) {
			return dao.selectListMaxFile(paramVO);
		} else {
			return dao.selectListFile(paramVO);
		}
	}

	public String save(Ses0441 param, List<Ses0441> listFile) throws Exception {
		
		try {
			
			String RQSER  = param.getRQSER();
			String strMaxRqser;
			String strNewRqser;
						
			if ( RQSER.equals("") ) {				
				List<Ses0441> list = dao.selectMaxRqser(param);
				
				strMaxRqser = list.get(0).getRQSER();

				strNewRqser = Integer.toString(Integer.parseInt(strMaxRqser)+1);
				
				param.setRQSER(strNewRqser);
				
				dao.insertHeader(param);
			} else {
				dao.updateHeader(param);
			}
			
			// 梅何颇老 历厘
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0441 paramFile = listFile.get(i);
				if ( paramFile.getPSPID().equals("") ) {
					//paramFile.setPSPID(PSPID);
				}
			}
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0441 paramFile = listFile.get(i);
				if ( paramFile.getFLAG().equals("D") ) {
					dao.deleteFile(paramFile);
				}
			}
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0441 paramFile = listFile.get(i);
				if ( RQSER.equals("") ) { 
					List<Ses0441> list = dao.selectMaxRqser(param);
					
					strMaxRqser = list.get(0).getRQSER();
					
					paramFile.setRQSER(strMaxRqser);
				}
				if ( paramFile.getFLAG().equals("I") ) {
					dao.insertFile(paramFile);
				} else if ( paramFile.getFLAG().equals("U") ) {
					dao.updateFile(paramFile);
				}
			}

		} catch (Exception e) {
			throw e;
		}
		
		return param.getPSPID();
	}

	public void saveRequest(Ses0441 param) throws Exception {
		
		try {
			dao.updateRequest(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}

	public void saveApproval(Ses0441 param, List<Ses0441> listFile) throws Exception {
		
		try {
			
			dao.updateApproval(param);
			
			// 梅何颇老 历厘
			for ( int i = 0 ; i < listFile.size() ; i++ ) {
				Ses0441 paramFile = listFile.get(i);
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
	public void saveOutdate(Ses0441 param) throws Exception {
		
		try {
			dao.updateOutdate(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
	public void saveOutfinish(Ses0441 param) throws Exception {
		
		try {
			dao.updateOutfinish(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    public void deleteHeader(Ses0441 param) throws Exception {
		
		try {
			dao.deleteHeader(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}
    
    public void deleteFile2(Ses0441 param) throws Exception {
		
		try {
			dao.deleteFile(param);
		} catch (Exception e) {
			throw e;
		}
		
		return;
	}

}
