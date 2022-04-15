package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0240D;
import hdel.sd.ses.domain.Ses0020;
import hdel.sd.ses.domain.Ses0020ParamVO;
import hdel.sd.ses.domain.Ses0240;
import hdel.sd.ses.domain.Ses0240ParamVO;
import hdel.sd.smp.domain.Smp0070ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0240S extends SrmService {

	private Ses0240D Ses0240Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0240Dao = sqlSession.getMapper(Ses0240D.class);
	}
	
	public List<Ses0240> searchHeader(Ses0240ParamVO param) {
		return Ses0240Dao.selectListHeader(param);
	}

	public List<Ses0240> searchDetail(Ses0240ParamVO param) {
		return Ses0240Dao.selectListDetail(param);
	}
	
	public List<Ses0240> searchFile(Ses0240ParamVO param) {
		return Ses0240Dao.selectListFile(param);
	}
	
	public int findCountQtnum(Ses0240 param) {
		return Ses0240Dao.findCountQtnum(param);
	}
	
	public List<Ses0240> findMaxSeq(Ses0240 param) {
		return Ses0240Dao.findMaxSeq(param);
	}
	
	public List<Ses0240> findMaxDocReqNo(Ses0240 param) {
		return Ses0240Dao.findMaxDocReqNo(param);
	}

	// 도면의뢰Header insert
	public String insertHeader(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_header");  	// 수주 등록,수정,삭제 대상정보
		Ses0240 param = new Ses0240();						// 저장 파라메터
		
		try
		{  
			
			createDao(session);	 
			
			// 도면의뢰 Insert
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	               					    // SAP CLIENT					
				List<Ses0240> Headerseq= findMaxDocReqNo(param);									//도면의뢰번호 Max값 조회
				param.setDocreqno(Headerseq.get(0).getDocreqno());										//도면의뢰번호
				param.setZrqflg(DatasetUtility.getString(ds_list, irow, "ZRQFLG"));						//도면의뢰구분
				param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));						//요청일자
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));							//요청내용
				param.setZwriter(DatasetUtility.getString(ds_list, irow, "ZWRITER"));						//작성자
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));							//비고
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));						//견적번호
				param.setCdate(DatasetUtility.getString(ds_list, irow, "CDATE"));
				param.setCtime(DatasetUtility.getString(ds_list, irow, "CTIME"));
				param.setCuser(DatasetUtility.getString(ds_list, irow, "CUSER"));
				param.setUdate(DatasetUtility.getString(ds_list, irow, "UDATE"));
				param.setUtime(DatasetUtility.getString(ds_list, irow, "UTIME"));				
				param.setUuser(DatasetUtility.getString(ds_list, irow, "UUSER"));
				
				insertHeader(param); 	//Header Insert
				insertDetail(param);		//Detail insert
				
			} // end of for		 
			
		}  catch (Exception e) { 
		}
		
		return param.getDocreqno();
	}   


	// 견적Header(ZSDT1046) 변경  (신규도면의뢰시 디테일도 함께 저장)
	public void insertHeader(Ses0240 param) {
		Ses0240Dao.insertHeader(param);
	}  

	
	// Detail insert
	public void insertDetail(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list = paramVO.getDataset("ds_list");  	
		try
		{  
			Ses0240 param = new Ses0240();						
			createDao(session);	 
			
			//Detail Insert
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
				param.setDocreqno(DatasetUtility.getString(ds_list, irow, "DOCREQNO"));
				//param.setZseq(DatasetUtility.getString(ds_list, irow, "ZSEQ"));
				//param.setZseq("10");		//도면의뢰/ MN생성 신규입력
				param.setZrqflg(DatasetUtility.getString(ds_list, irow, "ZRQFLG"));
				param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setZwriter(DatasetUtility.getString(ds_list, irow, "ZWRITER"));
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
				param.setCdate(DatasetUtility.getString(ds_list, irow, "CDATE"));
				param.setCuser(DatasetUtility.getString(ds_list, irow, "CUSER"));
				param.setUdate(DatasetUtility.getString(ds_list, irow, "UDATE"));
				param.setUtime(DatasetUtility.getString(ds_list, irow, "UTIME"));				
				param.setUuser(DatasetUtility.getString(ds_list, irow, "UUSER"));	
				insertDetail(param); 				
			} // end of for
			
		}  catch (Exception e) { 
			//TransactionManager.rollback(); 
		}
	}   

	// 견적Header(ZSDT1046) 변경
	public void insertDetail(Ses0240 param) {
		
		Ses0240Dao.insertDetail(param);
	}  
	
	// 도면의뢰 진행단계
	public void addDetail(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list = paramVO.getDataset("ds_list");  	// 수주 등록,수정,삭제 대상정보
		try
		{  
			Ses0240 param = new Ses0240();						// 수주 저장 파라메터
			createDao(session);	 
			
			// 견적승인요청 접수 처리 START
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT
				param.setDocreqno(DatasetUtility.getString(ds_list, irow, "DOCREQNO"));
				List<Ses0240> seqlist = findMaxSeq(param);				
				param.setZseq(seqlist.get(0).getZseq());
				param.setZrqflg(DatasetUtility.getString(ds_list, irow, "ZRQFLG"));
				param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setZwriter(DatasetUtility.getString(ds_list, irow, "ZWRITER"));
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
				param.setCdate(DatasetUtility.getString(ds_list, irow, "CDATE"));
				param.setCuser(DatasetUtility.getString(ds_list, irow, "CUSER"));
				param.setUdate(DatasetUtility.getString(ds_list, irow, "UDATE"));
				param.setUtime(DatasetUtility.getString(ds_list, irow, "UTIME"));				
				param.setUuser(DatasetUtility.getString(ds_list, irow, "UUSER"));
//System.out.println( "setZrqcn > " + param.getZrqcn());
//System.out.println( "setZrmk > " + param.getZrmk());
//System.out.println( "setZwriter > " + param.getZwriter());
				addDetail(param); 				
			} // end of for
			
		}  catch (Exception e) { 
			//TransactionManager.rollback(); 
		}
	}   

	// 견적Header(ZSDT1046) 변경
	public void addDetail(Ses0240 param) {
		
		Ses0240Dao.addDetail(param);
	}  
	
	// Header update
	public void updateHeader(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_list");
 	
		try
		{  
			Ses0240 param = new Ses0240();						// 저장 파라메터
			createDao(session);	 			
			
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setDocreqno(DatasetUtility.getString(ds_list, irow, "DOCREQNO"));
				param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));
				param.setZrqflg(DatasetUtility.getString(ds_list, irow, "ZRQFLG"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setZwriter(DatasetUtility.getString(ds_list, irow, "ZWRITER"));
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getString(ds_list, irow, "QTSER"));	
				param.setUdate(DatasetUtility.getString(ds_list, irow, "UDATE"));
				param.setUtime(DatasetUtility.getString(ds_list, irow, "UTIME"));				
				param.setUuser(DatasetUtility.getString(ds_list, irow, "UUSER"));
				
				updateHeader(param); 
			} // end of for		 
		}  catch (Exception e) { 
		}
	}   
	
	// 도면의뢰Header update
	public void updateDetail(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list	= paramVO.getDataset("ds_list");  	
 	
		try
		{  
			Ses0240 param = new Ses0240();						// 저장 파라메터
			createDao(session);	 			
			
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				if (ds_list.getRowType(irow) == ds_list.ROWTYPE_UPDATE) {
					param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
					param.setDocreqno(DatasetUtility.getString(ds_list, irow, "DOCREQNO"));
					param.setZseq(DatasetUtility.getString(ds_list, irow, "ZSEQ"));
					param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));
					param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
					param.setZwriter(DatasetUtility.getString(ds_list, irow, "ZWRITER"));
					param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
					param.setUdate(DatasetUtility.getString(ds_list, irow, "UDATE"));
					param.setUtime(DatasetUtility.getString(ds_list, irow, "UTIME"));				
					param.setUuser(DatasetUtility.getString(ds_list, irow, "UUSER"));
					
					updateDetail(param); 
				}
			} // end of for		 
		}  catch (Exception e) { 
		}
	}   	
	
	// 도면의뢰 상태변경
	public void statusUpdate(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
			
			// INPUT DATASET GET 
			Dataset ds_header	= paramVO.getDataset("ds_header"); 
	 	
			try
			{  
				Ses0240 param = new Ses0240();						// 저장 파라메터
				createDao(session);	 			
				
				for( int irow = 0; irow < ds_header.getRowCount(); irow++ ) 
				{  
						param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
						param.setDocreqno(DatasetUtility.getString(ds_header, irow, "DOCREQNO"));						
						param.setZrqflg(DatasetUtility.getString(ds_header, irow, "ZRQFLG"));		
						param.setZrqcn(DatasetUtility.getString(ds_header, irow, "ZRQCN"));		
						param.setZrmk(DatasetUtility.getString(ds_header, irow, "ZRMK"));		
						param.setUuser(DatasetUtility.getString(ds_header, irow, "UUSER"));
						
						statusUpdate(param); 
				} // end of for		 
			}  catch (Exception e) { 
			}
			
		}   
	
	// Header/Detail Save
	public boolean save(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
			
			// INPUT DATASET GET 
			Dataset ds_header = paramVO.getDataset("ds_header");  	
			Dataset ds_detail = paramVO.getDataset("ds_detail");  	
			Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
			
			try
			{  
				
				Ses0240 param = new Ses0240();			
				
				createDao(session);	 
				
				//Header save
				for( int irow = 0; irow < ds_header.getRowCount(); irow++ ) 
				{  
					param.setMandt(paramVO.getVariable("G_MANDT"));	               
					param.setDocreqno(DatasetUtility.getString(ds_header, irow, "DOCREQNO"));		
					param.setZrqflg(DatasetUtility.getString(ds_header, irow, "ZRQFLG"));
					param.setZrqcn(DatasetUtility.getString(ds_header, irow, "ZRQCN"));
					param.setZrmk(DatasetUtility.getString(ds_header, irow, "ZRMK"));
					param.setQtnum(DatasetUtility.getString(ds_header, irow, "QTNUM"));	
					param.setQtser(DatasetUtility.getString(ds_header, irow, "QTSER"));	
					param.setUuser(DatasetUtility.getString(ds_header, irow, "UUSER"));
					
					
					String qtnum = DatasetUtility.getString(ds_header, irow, "QTNUM");
					String org_qtnum =DatasetUtility.getString( ds_header, irow, "ORG_QTNUM") ;
					
					
					//견적번호가 변경된 경우
					if (!qtnum.equals(org_qtnum)) {
						int cnt = findCountQtnum(param);	
						if (cnt > 0) 	return false;						
					} 

					if (ds_header.getRowType(irow) == ds_header.ROWTYPE_UPDATE) {
						updateHeader(param); 				
					} 

				}
				
				//Detail save
				for( int irow = 0; irow < ds_detail.getRowCount(); irow++ ) 
				{  
					param.setMandt(paramVO.getVariable("G_MANDT"));	               
					param.setDocreqno(DatasetUtility.getString(ds_detail, irow, "DOCREQNO"));	
					param.setZseq(DatasetUtility.getString(ds_detail, irow, "ZSEQ"));	
					param.setZrqcn(DatasetUtility.getString(ds_detail, irow, "ZRQCN"));
					param.setZwriter(DatasetUtility.getString(ds_detail, irow, "ZWRITER"));
					param.setZrmk(DatasetUtility.getString(ds_detail, irow, "ZRMK"));
					param.setUuser(DatasetUtility.getString(ds_detail, irow, "UUSER"));
					
					if (ds_detail.getRowType(irow) == ds_detail.ROWTYPE_UPDATE) {
						updateDetail(param); 				
					}
				}
				
			}  catch (Exception e) { 
				
			}
			
			return true;
		}   

	// 파일업로드
	public void fileSave(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET  	
		Dataset ds_file	= paramVO.getDataset("ds_file");  	
		Dataset ds_delFile = paramVO.getDataset("ds_delFile");
 	
		try
		{  
			Ses0240 param = new Ses0240();						// 저장 파라메터
			createDao(session);	 
			
			//저장
			for( int irow = 0; irow < ds_file.getRowCount(); irow++ ) 
			{
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setZwgbn(DatasetUtility.getString(ds_file, irow, "ZWGBN"));
				param.setDocreqno(DatasetUtility.getString(ds_file, irow, "DOCREQNO"));
				param.setZseq(DatasetUtility.getString(ds_file, irow, "ZSEQ"));
				param.setZattfn(DatasetUtility.getString(ds_file, irow, "ZATTFN"));
				param.setZattfn_or(DatasetUtility.getString(ds_file, irow, "ZATTFN_OR"));
				param.setZattpath(DatasetUtility.getString(ds_file, irow, "ZATTPATH"));
				param.setCuser(DatasetUtility.getString(ds_file, irow, "CUSER"));
				param.setUuser(DatasetUtility.getString(ds_file, irow, "UUSER"));
				
				// 파일정보 insert
				if(ds_file.getRowType(irow) == ds_file.ROWTYPE_INSERT) {
					fileUpload(param); 
				} 
			} // end of for		 
			
			//파일삭제
			for( int d = 0; d < ds_delFile.getRowCount(); d++ ) 
			{
				param.setMandt(paramVO.getVariable("G_MANDT"));	                // SAP CLIENT	
				param.setZwgbn(DatasetUtility.getString(ds_delFile, d, "ZWGBN"));
				param.setDocreqno(DatasetUtility.getString(ds_delFile, d, "DOCREQNO"));
				param.setZseq(DatasetUtility.getString(ds_delFile, d, "ZSEQ"));
				param.setZattseq(DatasetUtility.getString(ds_delFile, d, "ZATTSEQ"));
				
					fileDelete(param);	
			}
		}  catch (Exception e) { 
		}
	}   	
	
	// Header변경 
	public void updateHeader(Ses0240 param) {
		Ses0240Dao.updateHeader(param);
	}
	
	public void updateDetail(Ses0240 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0240Dao.updateDetail(param);		
	}
	
	public void statusUpdate(Ses0240 param) {
		Ses0240Dao.statusUpdate(param);
	}
	
	public void deleteHeader(Ses0240 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0240Dao.deleteHeader(param);		
	}
	
	public void deleteDetail(Ses0240 param) {
		//logger.info("@@@@@@@@@@ saveCopyNext service in -> dao @@@@@@@@@");
		Ses0240Dao.deleteDetail(param);		
	}
	
	// 파일삭제
	public void fileDelete(Ses0240 param) {
		Ses0240Dao.fileDelete(param);		//Insert
	}  		
	
	// 파일업로드
	public void fileUpload(Ses0240 param) {
		Ses0240Dao.fileUpload(param);		//Insert
	}  	

}
