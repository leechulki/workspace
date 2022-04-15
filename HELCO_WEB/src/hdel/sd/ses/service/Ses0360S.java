package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0360D;
import hdel.sd.ses.domain.Ses0360;
import hdel.sd.ses.domain.Ses0360ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0360S extends SrmService {

	private Ses0360D Ses0360Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0360Dao = sqlSession.getMapper(Ses0360D.class);
	}
	
	public List<Ses0360> searchList(Ses0360ParamVO param) {
		return Ses0360Dao.selectList(param);
	}
	
	public List<Ses0360> searchList2(Ses0360ParamVO param) {
		return Ses0360Dao.selectList2(param);
	}
	
	public void insertExcel(Ses0360 param) {
		
		Ses0360Dao.insertExcel(param);
	}  
	public void insertExcel2(Ses0360 param) {
		
		Ses0360Dao.insertExcel2(param);
	}
	
	public void deleteExcel(Ses0360 param) {
		
		Ses0360Dao.deleteExcel(param);
	} 
	
	public void deleteExcel2(Ses0360 param) {
		
		Ses0360Dao.deleteExcel2(param);
	} 

	public void saveExcel(MipParameterVO paramVO, Model model, SqlSession session) throws Exception {
		
		Dataset ds_key	= paramVO.getDataset("ds_key"); 
		Dataset ds_excel	= paramVO.getDataset("ds_excel"); 
		Dataset ds_file		= paramVO.getDataset("ds_file");
		
		try {
				Ses0360 param = new Ses0360();
				createDao(session);	

				//기존 물류견적원가 삭제
				param.setMandt(paramVO.getVariable("G_MANDT"));	     
				param.setQtnum(DatasetUtility.getString(ds_key, 0, "QTNUM"));		
				param.setQtser(DatasetUtility.getString(ds_key, 0, "QTSER"));		
				param.setQtseq(DatasetUtility.getString(ds_key, 0, "QTSEQ"));		
				param.setCuser(DatasetUtility.getString(ds_key, 0, "CUSER"));
				param.setUuser(DatasetUtility.getString(ds_key, 0, "UUSER"));
				
				deleteExcel(param);
				
				//물류견적원가 insert
				for(int i=0; i<ds_excel.getRowCount(); i++) {
					
					param.setCostzseq(DatasetUtility.getString(ds_excel, i, "COSTZSEQ"));	
					param.setSetting(DatasetUtility.getString(ds_excel, i, "SETTING"));							
					param.setQnty(DatasetUtility.getString(ds_excel, i, "QNTY"));						
					param.setZuam(DatasetUtility.getString(ds_excel, i, "ZUAM"));
					param.setZcost(DatasetUtility.getString(ds_excel, i, "ZCOST"));
					param.setE_value(DatasetUtility.getString(ds_excel, i, "E_VALUE"));
					param.setO_rate(DatasetUtility.getString(ds_excel, i, "O_RATE"));
					param.setZrmk(DatasetUtility.getString(ds_excel, i, "ZRMK"));
									
					insertExcel(param);
				}
				
				// 2013.02.21 첨부파일 저장 기능 추가
				for ( int i = 0 ; i < ds_file.getRowCount() ; i++ ) {
					String fMANDT    = DatasetUtility.getString(ds_file, i, "MANDT"   ); if (fMANDT    == null) fMANDT    = "";
					String fQTNUM    = DatasetUtility.getString(ds_file, i, "QTNUM"   ); if (fQTNUM    == null) fQTNUM    = "";
					String fQTSER    = DatasetUtility.getString(ds_file, i, "QTSER"   ); if (fQTSER    == null) fQTSER    = "";
					String fQTSEQ    = DatasetUtility.getString(ds_file, i, "QTSEQ"   ); if (fQTSEQ    == null) fQTSEQ    = "";
					String fZATTSEQ  = DatasetUtility.getString(ds_file, i, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";
					String fZATGBN   = DatasetUtility.getString(ds_file, i, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
					String fZATTPATH = DatasetUtility.getString(ds_file, i, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
					String fZATTFN   = DatasetUtility.getString(ds_file, i, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
					String fFLAG     = DatasetUtility.getString(ds_file, i, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
					String fUUSER    = DatasetUtility.getString(ds_key, 0, "UUSER");
					
					Ses0360 paramFile = new Ses0360();
					
					paramFile.setMandt   (fMANDT   );
					paramFile.setQtnum   (fQTNUM   );
					paramFile.setQtser   (fQTSER   );
					paramFile.setQtseq   (fQTSEQ   );
					paramFile.setZattseq (fZATTSEQ );
					paramFile.setZatgbn  (fZATGBN  );
					paramFile.setZattpath(fZATTPATH);
					paramFile.setZattfn  (fZATTFN  );
					paramFile.setUuser   (fUUSER   );
					
					if ( fFLAG.equals("D") ) {
						Ses0360Dao.deleteFile(paramFile);
					} else if ( fFLAG.equals("I") ) {
						Ses0360Dao.insertFile(paramFile);
					} else if ( fFLAG.equals("U") ) {
						Ses0360Dao.updateFile(paramFile);
					}
				}
				
		} catch(Exception e) {
			throw e;
		}		
	}
	
	public void saveExcel2(MipParameterVO paramVO, Model model, SqlSession session) throws Exception {
		
		Dataset ds_key	= paramVO.getDataset("ds_key"); 
		Dataset ds_excel	= paramVO.getDataset("ds_excel2");
		Dataset ds_file		= paramVO.getDataset("ds_file");
		
		try {
				Ses0360 param = new Ses0360();
				createDao(session);
				
				//기존 물류수주원가 삭제
				param.setMandt(paramVO.getVariable("G_MANDT"));	     
				param.setPspid(DatasetUtility.getString(ds_key, 0, "PSPID"));	
				param.setPosid(DatasetUtility.getString(ds_key, 0, "POSID"));		
				param.setCuser(DatasetUtility.getString(ds_key, 0, "CUSER"));
				param.setUuser(DatasetUtility.getString(ds_key, 0, "UUSER"));
				
				deleteExcel2(param);
				
				//물류수주원가 insert
				for(int i=0; i<ds_excel.getRowCount(); i++) {
					
					param.setCostzseq(DatasetUtility.getString(ds_excel, i, "COSTZSEQ"));	
					param.setSetting(DatasetUtility.getString(ds_excel, i, "SETTING"));							
					param.setQnty(DatasetUtility.getString(ds_excel, i, "QNTY"));						
					param.setZuam(DatasetUtility.getString(ds_excel, i, "ZUAM"));
					param.setZcost(DatasetUtility.getString(ds_excel, i, "ZCOST"));
					param.setZrmk(DatasetUtility.getString(ds_excel, i, "ZRMK"));
									
					insertExcel2(param);
				}
				
				// 2013.02.21 첨부파일 저장 기능 추가
				for ( int i = 0 ; i < ds_file.getRowCount() ; i++ ) {
					String fMANDT    = DatasetUtility.getString(ds_file, i, "MANDT"   ); if (fMANDT    == null) fMANDT    = "";
					String fPSPID    = DatasetUtility.getString(ds_file, i, "PSPID"   ); if (fPSPID    == null) fPSPID    = "";
					//String fPOSID    = DatasetUtility.getString(ds_file, i, "POSID"   ); if (fPOSID    == null) fPOSID    = "";
					String fZATTSEQ  = DatasetUtility.getString(ds_file, i, "ZATTSEQ" ); if (fZATTSEQ  == null) fZATTSEQ  = "";
					String fZATGBN   = DatasetUtility.getString(ds_file, i, "ZATGBN"  ); if (fZATGBN   == null) fZATGBN   = "";
					String fZATTPATH = DatasetUtility.getString(ds_file, i, "ZATTPATH"); if (fZATTPATH == null) fZATTPATH = "";
					String fZATTFN   = DatasetUtility.getString(ds_file, i, "ZATTFN"  ); if (fZATTFN   == null) fZATTFN   = "";
					String fFLAG     = DatasetUtility.getString(ds_file, i, "FLAG"    ); if (fFLAG     == null) fFLAG     = "";
					String fUUSER    = DatasetUtility.getString(ds_key, 0, "UUSER");
					
					Ses0360 paramFile = new Ses0360();
					
					paramFile.setMandt   (fMANDT   );
					paramFile.setPspid   (fPSPID   );
					//paramFile.setPosid   (fPOSID   );
					paramFile.setZattseq (fZATTSEQ );
					paramFile.setZatgbn  (fZATGBN  );
					paramFile.setZattpath(fZATTPATH);
					paramFile.setZattfn  (fZATTFN  );
					paramFile.setUuser   (fUUSER   );
					
					if ( fFLAG.equals("D") ) {
						Ses0360Dao.deleteFile2(paramFile);
					} else if ( fFLAG.equals("I") ) {
						Ses0360Dao.insertFile2(paramFile);
					} else if ( fFLAG.equals("U") ) {
						Ses0360Dao.updateFile2(paramFile);
					}
				}
		} catch(Exception e) {
			throw e;
		}		
	}
	
	/**
	 * 2013.02.21 첨부파일 조회 기능 추가 - 견적원가등록
	 * @param param
	 * @return
	 */
	public List<Ses0360> selectListFile(Ses0360ParamVO param) {
		return Ses0360Dao.selectListFile(param);
	}
	
	/**
	 * 2013.02.21 첨부파일 조회 기능 추가 - 수주원가등록
	 * @param param
	 * @return
	 */
	public List<Ses0360> selectListFile2(Ses0360ParamVO param) {
		return Ses0360Dao.selectListFile2(param);
	}
}
