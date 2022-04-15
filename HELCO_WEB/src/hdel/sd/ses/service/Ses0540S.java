package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0540D;
import hdel.sd.ses.domain.Ses0540;
import hdel.sd.ses.domain.Ses0540ParamVO;
import tit.util.DatasetUtility;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0540S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Ses0540D Ses0540Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0540Dao = sqlSession.getMapper(Ses0540D.class);
	}	

	/*-----------------------------------------------------
	 *  파트 코드 조회
	 ------------------------------------------------------*/
	public MipResultVO searchPartCd(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_part_cd = paramVO.getDataset("ds_part_cd");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
				
		try{
			Ses0540ParamVO param  = new Ses0540ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			
			List<Ses0540> partCd = Ses0540Dao.selectPartCd(param);
			
			CallSAPHdel.moveListToDsSub(ds_part_cd, Ses0540.class, partCd);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		
		resultVO.addDataset(ds_part_cd);
		resultVO.addDataset(ds_error);
		return resultVO;
	}
	
	/*-----------------------------------------------------
	 *  세부파트 코드 조회
	 ------------------------------------------------------*/
	public MipResultVO searchDetailCd(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_detail_cd = paramVO.getDataset("ds_detail_cd");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
				
		try{
			Ses0540ParamVO param  = new Ses0540ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setPart_cd(paramVO.getVariable("part_cd"));
			
			List<Ses0540> detailCd = Ses0540Dao.selectDetailCd(param);
			
			CallSAPHdel.moveListToDsSub(ds_detail_cd, Ses0540.class, detailCd);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		
		resultVO.addDataset(ds_detail_cd);
		resultVO.addDataset(ds_error);
		return resultVO;
	}
	
	/*-----------------------------------------------------
	 *  검토항목 코드 조회
	 ------------------------------------------------------*/
	public MipResultVO searchCheckId(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_check_id = paramVO.getDataset("ds_check_id");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
				
		try{
			Ses0540ParamVO param  = new Ses0540ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setDetail_cd(paramVO.getVariable("detail_cd"));
			
			List<Ses0540> checkCd = Ses0540Dao.selectCheckId(param);
			
			CallSAPHdel.moveListToDsSub(ds_check_id, Ses0540.class, checkCd);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		
		resultVO.addDataset(ds_check_id);
		resultVO.addDataset(ds_error);
		return resultVO;
	}

	/*-----------------------------------------------------
	 *  시방서 세부 항목 조회
	 ------------------------------------------------------*/
	public MipResultVO selectDeviationListDetail(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_header = paramVO.getDataset("ds_header");
		Dataset ds_list = paramVO.getDataset("ds_list");
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try{
			Ses0540ParamVO param  = new Ses0540ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setZrqno(paramVO.getVariable("zrqno"));
						
			List<Ses0540> dsHeader = Ses0540Dao.selectDeviationListHeader(param);
			List<Ses0540> dsList = Ses0540Dao.selectDeviationListDetail(param);
			
			CallSAPHdel.moveListToDsSub(ds_header, Ses0540.class, dsHeader);
			CallSAPHdel.moveListToDsSub(ds_list, Ses0540.class, dsList);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_header);
		resultVO.addDataset(ds_list);
		resultVO.addDataset(ds_error);
		return resultVO;
	}

	

	/*-----------------------------------------------------
	 *  시방서 헤더 조회
	 ------------------------------------------------------*/
	public MipResultVO selectDeviationListHeader(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_header = paramVO.getDataset("ds_header");
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try{
			Ses0540ParamVO param  = new Ses0540ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setZrqno(paramVO.getVariable("zrqno"));
						
			List<Ses0540> dsHeader = Ses0540Dao.selectDeviationListHeader(param);
			
			CallSAPHdel.moveListToDsSub(ds_header, Ses0540.class, dsHeader);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_header);
		resultVO.addDataset(ds_error);
		return resultVO;
	}

	
	/*-----------------------------------------------------
	 *  시방서 검토 요청 접수
	 ------------------------------------------------------*/
	public MipResultVO insertDeviationDetailListReceipt(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_header = paramVO.getDataset("ds_header");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try{
			Ses0540ParamVO param  = new Ses0540ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			String userId = paramVO.getVariable("G_USER_ID");
			
			param.setErnam("H"+userId);
			param.setAenam("H"+userId);
			param.setKunz3("H"+userId);
			param.setZrqno(paramVO.getVariable("zrqno"));
			param.setDestat("TA");
			
			if(param.getKunz3() == null) param.setKunz3("") ;
			if(param.getErnam() == null) param.setErnam("") ;
			if(param.getAenam() == null) param.setAenam("") ;
			
			System.out.println("mandt : " + param.getMandt());
			System.out.println("zrqno : " + param.getZrqno());
			System.out.println("kunz3 : " + param.getKunz3());
			System.out.println("ernam : " + param.getErnam());
			System.out.println("aenam : " + param.getAenam());
			
			
			//신규일 경우 마스터와 상태에 최초 1번만 입력하기
			Ses0540Dao.insertDeviationReceipt(param);	// 요청 마스터 해더 저장하기(추가)
			Ses0540Dao.updateDeviationDestat(param);		// 상태 저장하기(추가)
							
			List<Ses0540> dsHeader = Ses0540Dao.selectDeviationListHeader(param);
			ds_header.deleteAll();
			CallSAPHdel.moveListToDsSub(ds_header, Ses0540.class, dsHeader);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_header);
		resultVO.addDataset(ds_error);
		return resultVO;
	}
	
	/*-----------------------------------------------------
	 *  시방서 검토 요청 반송
	 ------------------------------------------------------*/
	public MipResultVO saveDeviationDetailListSendback(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_header = paramVO.getDataset("ds_header");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try{
			Ses0540ParamVO param  = new Ses0540ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			
			String userId = paramVO.getVariable("G_USER_ID");
			
			param.setErnam("H"+userId);
			param.setAenam("H"+userId);
			param.setZrqno(paramVO.getVariable("zrqno"));
			param.setDestat("TB");
			
			if(param.getErnam() == null) param.setErnam("") ;
			if(param.getAenam() == null) param.setAenam("") ;
			
			System.out.println("mandt : " + param.getMandt());
			System.out.println("zrqno : " + param.getZrqno());
			System.out.println("ernam : " + param.getErnam());
			System.out.println("aenam : " + param.getAenam());
			
			
			Ses0540Dao.updateDeviationDestat(param);
			Ses0540Dao.updateDeviationHeader(param);
							
			List<Ses0540> dsHeader = Ses0540Dao.selectDeviationListHeader(param);
			ds_header.deleteAll();
			CallSAPHdel.moveListToDsSub(ds_header, Ses0540.class, dsHeader);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_header);
		resultVO.addDataset(ds_error);
		return resultVO;
	}

	/*-----------------------------------------------------
	 *  시방서 검토 내용 저장
	 ------------------------------------------------------*/
	public MipResultVO saveDeviationDetailListReview(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_header = paramVO.getDataset("ds_header");
		Dataset ds_list = paramVO.getDataset("ds_list");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try{
			Ses0540ParamVO param  = new Ses0540ParamVO();
			
			int ds_list_count = ds_list.getRowCount();
			
			param.setMandt(paramVO.getVariable("G_MANDT"));
			String userId = paramVO.getVariable("G_USER_ID");
			
			param.setErnam("H"+userId);
			param.setAenam("H"+userId);
			param.setZrqno(paramVO.getVariable("zrqno"));
			
			if(param.getZrqno() == null) param.setZrqno("") ;
			if(param.getErnam() == null) param.setErnam("") ;
			if(param.getAenam() == null) param.setAenam("") ;
			
			for(int i = 0 ; i < ds_list_count ; i++){
				param.setFlag(DatasetUtility.getString(ds_list, i, "flag"));
				param.setZseq(DatasetUtility.getString(ds_list, i, "zseq"));
				param.setHdel_text(	DatasetUtility.getString(ds_list, i, "hdel_text"));
				param.setAppend_text(	DatasetUtility.getString(ds_list, i, "append_text"));
				
				if(param.getFlag() == null) param.setFlag("") ;
				if(param.getZseq() == null) param.setZseq("") ;
				if(param.getHdel_text() == null) param.setHdel_text("") ;
				if(param.getAppend_text() == null) param.setAppend_text("") ;		
				
				if(param.getFlag().equals("U")){
					Ses0540Dao.updateDeviationReviewDetail(param);
				}
			} 
			
			
			
							
			List<Ses0540> dsHeader = Ses0540Dao.selectDeviationListHeader(param);
			List<Ses0540> dsList = Ses0540Dao.selectDeviationListDetail(param);
			ds_header.deleteAll();
			ds_list.deleteAll();
			CallSAPHdel.moveListToDsSub(ds_header, Ses0540.class, dsHeader);
			CallSAPHdel.moveListToDsSub(ds_list, Ses0540.class, dsList);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_header);
		resultVO.addDataset(ds_list);
		resultVO.addDataset(ds_error);
		return resultVO;
	}

	public MipResultVO saveDeviationDetailListFinish(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_header = paramVO.getDataset("ds_header");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try{
			Ses0540ParamVO param  = new Ses0540ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			
			String userId = paramVO.getVariable("G_USER_ID");
			
			param.setErnam("H"+userId);
			param.setAenam("H"+userId);
			param.setZrqno(paramVO.getVariable("zrqno"));
			param.setDestat("TF");
			
			if(param.getErnam() == null) param.setErnam("") ;
			if(param.getAenam() == null) param.setAenam("") ;
			
			System.out.println("mandt : " + param.getMandt());
			System.out.println("zrqno : " + param.getZrqno());
			System.out.println("ernam : " + param.getErnam());
			System.out.println("aenam : " + param.getAenam());
			
			Ses0540Dao.updateDeviationDestat(param);
			Ses0540Dao.updateDeviationHeader(param);
			
							
			List<Ses0540> dsHeader = Ses0540Dao.selectDeviationListHeader(param);
			ds_header.deleteAll();
			CallSAPHdel.moveListToDsSub(ds_header, Ses0540.class, dsHeader);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_header);
		resultVO.addDataset(ds_error);
		return resultVO;
	}
}
