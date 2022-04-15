package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0520D;
import hdel.sd.ses.domain.Ses0520;
import hdel.sd.ses.domain.Ses0520ParamVO;
//import sun.org.mozilla.javascript.internal.GeneratedClassLoader;
import tit.util.DatasetUtility;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0520S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

	private Ses0520D Ses0520Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0520Dao = sqlSession.getMapper(Ses0520D.class);
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
			Ses0520ParamVO param  = new Ses0520ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			
			List<Ses0520> partCd = Ses0520Dao.selectPartCd(param);
			
			CallSAPHdel.moveListToDsSub(ds_part_cd, Ses0520.class, partCd);
			
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
			Ses0520ParamVO param  = new Ses0520ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setPart_cd(paramVO.getVariable("part_cd"));
			
			List<Ses0520> detailCd = Ses0520Dao.selectDetailCd(param);
			
			CallSAPHdel.moveListToDsSub(ds_detail_cd, Ses0520.class, detailCd);
			
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
			Ses0520ParamVO param  = new Ses0520ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setDetail_cd(paramVO.getVariable("detail_cd"));
			
			List<Ses0520> checkCd = Ses0520Dao.selectCheckId(param);
			
			CallSAPHdel.moveListToDsSub(ds_check_id, Ses0520.class, checkCd);
			
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
		
		Dataset ds_list = paramVO.getDataset("ds_list");
		
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try{
			Ses0520ParamVO param  = new Ses0520ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setZrqno(paramVO.getVariable("zrqno"));
						
			List<Ses0520> dsList = Ses0520Dao.selectDeviationListDetail(param);
			
			CallSAPHdel.moveListToDsSub(ds_list, Ses0520.class, dsList);
			
			ds_list.setColumn(0, "mandt", 		ds_list.getColumn(0, "mandt"));
			ds_list.setColumn(0, "zrqno", 		ds_list.getColumn(0, "zrqno"));
			ds_list.setColumn(0, "kunnr", 		ds_list.getColumn(0, "kunnr"));
			ds_list.setColumn(0, "kunnr_nm",	ds_list.getColumn(0, "kunnr_nm"));
			ds_list.setColumn(0, "part_cd", 	ds_list.getColumn(0, "part_cd"));
			ds_list.setColumn(0, "detail_cd", 	ds_list.getColumn(0, "detail_cd"));
			ds_list.setColumn(0, "check_id", 	ds_list.getColumn(0, "check_id"));
			ds_list.setColumn(0, "kunz2", 		ds_list.getColumn(0, "kunz2"));
			ds_list.setColumn(0, "kunz2_nm",	ds_list.getColumn(0, "kunz2_nm"));
			ds_list.setColumn(0, "zrevym", 		ds_list.getColumn(0, "zrevym"));
			ds_list.setColumn(0, "zrqdat",	 	ds_list.getColumn(0, "zrqdat"));
			ds_list.setColumn(0, "item_cd",	 	ds_list.getColumn(0, "item_cd"));
			ds_list.setColumn(0, "searchkey",	ds_list.getColumn(0, "searchkey"));
			ds_list.setColumn(0, "spec_text",	ds_list.getColumn(0, "spec_text"));
			ds_list.setColumn(0, "sman",		ds_list.getColumn(0, "sman"));
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_list);
		resultVO.addDataset(ds_error);
		return resultVO;
	}

	/*-----------------------------------------------------
	 *  시방서 세부 저장
	 ------------------------------------------------------*/
	public MipResultVO saveDeviationDetail(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_list = paramVO.getDataset("ds_list");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try{
			Ses0520ParamVO param  = new Ses0520ParamVO();
			
			int ds_list_count = ds_list.getRowCount();
			
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setErnam(paramVO.getVariable("G_USER_ID"));
			param.setAenam(paramVO.getVariable("G_USER_ID"));
			
			for(int i = 0 ; i < ds_list_count ; i++){
				param.setFlag(DatasetUtility.getString(ds_list, i, "flag"));
				param.setZrqno(	DatasetUtility.getString(ds_list, i,"zrqno"));
				param.setZseq(	DatasetUtility.getString(ds_list, i,"zseq"));
				param.setDestat(DatasetUtility.getString(ds_list, i,"destat"));
				param.setKunz2(	DatasetUtility.getString(ds_list, i, "kunz2"));
				param.setKunnr(	DatasetUtility.getString(ds_list, i, "kunnr"));
				param.setZrevym(DatasetUtility.getString(ds_list, i, "zrevym"));
				param.setZrqdat(DatasetUtility.getString(ds_list, i, "zrqdat"));
				param.setPart_cd(	DatasetUtility.getString(ds_list, i, "part_cd"));
				param.setDetail_cd(	DatasetUtility.getString(ds_list, i, "detail_cd"));
				param.setCheck_id(	DatasetUtility.getString(ds_list, i, "check_id"));
				param.setItem_cd(	DatasetUtility.getString(ds_list, i, "item_cd"));
				param.setSearchkey(	DatasetUtility.getString(ds_list, i, "searchkey"));
				param.setSpec_text(	DatasetUtility.getString(ds_list, i, "spec_text"));
				
				if(param.getFlag() == null) param.setFlag("") ;
				if(param.getZrqno() == null) param.setZrqno("") ;
				if(param.getZseq() == null) param.setZseq("") ;
				if(param.getDestat() == null) param.setDestat("");
				if(param.getKunz2() == null) param.setKunz2("") ;
				if(param.getKunnr() == null) param.setKunnr("") ;
				if(param.getZrevym() == null) param.setZrevym("") ;
				if(param.getZrqdat() == null) param.setZrqdat("") ;
				if(param.getPart_cd() == null) param.setPart_cd("") ;
				if(param.getDetail_cd() == null) param.setDetail_cd("") ;
				if(param.getCheck_id() == null) param.setCheck_id("") ;
				if(param.getItem_cd() == null) param.setItem_cd("") ;
				if(param.getSearchkey() == null) param.setSearchkey("") ;
				if(param.getSpec_text() == null) param.setSpec_text("") ;

				
//				System.out.println("ds_list_flag : " + param.getFlag() + " .");
//				System.out.println("ds_list_zrqno : " + param.getZrqno() + " .");
//				System.out.println("ds_list_zseq : " + param.getZseq() + " .");
//				System.out.println("ds_list_zrevym : " + param.getZrevym() + " .");
//				System.out.println("ds_list_kunnr : " + param.getKunnr() + " .");
//				System.out.println("ds_list_kunz2 : " + param.getKunz2() + " .");
//				System.out.println("ds_list_destat : " + param.getDestat() + " .");
				
				
				if(param.getFlag().equals("I")){
					// 기존 요청 사항에 항목품번을 추가하였을 경우
//					System.out.println("신규 품목 등록");
					Ses0520Dao.insertDeviationDetail(param);	// 요청 마스터 상세 저장하기(추가)
				} else if(param.getFlag().equals("U")){
					// 기존 요청 사항을 수정하였을 경우.
//					System.out.println("기존 품목 업데이트");
					Ses0520Dao.updateDeviationDetail(param);	// 요청 마스터 상세 저장하기(수정)
				}
			} 
			
			List<Ses0520> dsList = Ses0520Dao.selectDeviationListDetail(param);
			CallSAPHdel.moveListToDsSub(ds_list, Ses0520.class, dsList);

		}catch(Exception e){
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			ds_error.setId("ds_error");
			resultVO.addDataset(ds_error); 
		}
		
		ds_error.setId("ds_error");
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
			Ses0520ParamVO param  = new Ses0520ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setZrqno(paramVO.getVariable("zrqno"));
						
			List<Ses0520> dsHeader = Ses0520Dao.selectDeviationListHeader(param);
			
			CallSAPHdel.moveListToDsSub(ds_header, Ses0520.class, dsHeader);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_header);
		resultVO.addDataset(ds_error);
		return resultVO;
	}

	/*-----------------------------------------------------
	 *  시방서 검토 요청 헤더 저장
	 ------------------------------------------------------*/
	public MipResultVO insertDeviationHeader(MipParameterVO paramVO) {
		// TODO Auto-generated method stub
		MipResultVO resultVO = new MipResultVO();
		
		Dataset ds_header = paramVO.getDataset("ds_header");
			Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try{
			Ses0520ParamVO param  = new Ses0520ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			
			String userId = paramVO.getVariable("G_USER_ID");
			
			param.setErnam("H"+userId);
			param.setAenam("H"+userId);
			
			param.setDestat("SR");
			
			//신규 요청일련번호 생성
			List<Ses0520> nextZrqno = Ses0520Dao.selectMaxZrqno(param);
//			System.out.println("nextZrqno : " + nextZrqno.get(0).getZrqno().toString());
			param.setZrqno(nextZrqno.get(0).getZrqno().toString());
			
			param.setZrqdat(DatasetUtility.getString(ds_header, "zrqdat"));
			param.setZrevym(DatasetUtility.getString(ds_header, "zrevym"));
			param.setKunnr(DatasetUtility.getString(ds_header, "kunnr"));
			param.setKunz2(DatasetUtility.getString(ds_header, "sman"));
			
			
			if(param.getZrqdat() == null) param.setZrqdat("") ;
			if(param.getZrevym() == null) param.setZrevym("") ;
			if(param.getKunnr() == null) param.setKunnr("") ;
			if(param.getKunz2() == null) param.setKunz2("") ;
			if(param.getErnam() == null) param.setErnam("") ;
			if(param.getAenam() == null) param.setAenam("") ;
			
//			System.out.println("mandt : " + param.getMandt());
//			System.out.println("zrqno : " + param.getZrqno());
//			System.out.println("zrqdat : " + param.getZrqdat());
//			System.out.println("zrevym : " + param.getZrevym());
//			System.out.println("kunnr : " + param.getKunnr());
//			System.out.println("kunz2 : " + param.getKunz2());
//			System.out.println("ernam : " + param.getErnam());
//			System.out.println("aenam : " + param.getAenam());
			
			
			//신규일 경우 마스터와 상태에 최초 1번만 입력하기
			Ses0520Dao.insertDeviationHeader(param);	// 요청 마스터 해더 저장하기(추가)
			Ses0520Dao.insertDeviationState(param);		// 상태 저장하기(추가)
							
			List<Ses0520> dsHeader = Ses0520Dao.selectDeviationListHeader(param);
			ds_header.deleteAll();
			CallSAPHdel.moveListToDsSub(ds_header, Ses0520.class, dsHeader);
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_header);
		resultVO.addDataset(ds_error);
		return resultVO;
	}
}
