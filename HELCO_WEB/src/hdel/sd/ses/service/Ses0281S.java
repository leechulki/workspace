package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0281D;
import hdel.sd.ses.domain.Ses0110;
import hdel.sd.ses.domain.Ses0281;
import hdel.sd.ses.domain.Ses0281ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0281S extends SrmService {

	private Ses0281D Ses0281Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0281Dao = sqlSession.getMapper(Ses0281D.class);
	}
	
	public List<Ses0281> searchFormHeader(Ses0281ParamVO param) {
		return Ses0281Dao.selectFormHeader(param);
	}

	public List<Ses0281> searchList(Ses0281ParamVO param) {
		return Ses0281Dao.selectList(param);
	}
	
	public List<Ses0281> searchListDetail(Ses0281ParamVO param) {
		System.out.print("\n@@@ Ses0281FindDtlView searchListDetail strat");
		return Ses0281Dao.selectListDetail(param);
	}

	// 견적원가의뢰
	public void updateZSDT1054(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list_save");  	
		Dataset ds_list_detail 		= paramVO.getDataset("ds_list_detail_save");  	
		
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0281 	param 		= new Ses0281();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// 원가의뢰요청 접수 처리 START
			//------------------------------------------------------------------------
			System.out.print("\n@@@ ds_list.getRowCount  ===>"+ds_list.getRowCount() 	+ "\n");
			
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getInt(ds_list, irow, "QTSER"));
				param.setZrqseq(DatasetUtility.getInt(ds_list, irow, "ZRQSEQ"));
				param.setZrqdat(DatasetUtility.getString(ds_list, irow, "ZRQDAT"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setZrqstat(DatasetUtility.getString(ds_list, irow, "ZRQSTAT"));
				param.setZrqrlt(DatasetUtility.getString(ds_list, irow, "ZRQRLT"));
				param.setUser_id(DatasetUtility.getString(ds_list, irow, "USER_ID"));

				updateZSDT1054(param); 
				
				System.out.print("\n@@@ ds_list.ZRQSTAT 	===>"+DatasetUtility.getInt(ds_list, irow, "ZRQSTAT")+"\n");

				if (DatasetUtility.getString(ds_list, irow, "ZRQSTAT").toString().equals("30")) {
					updateFlagConfirmZSDT1046(param);
				} else if (DatasetUtility.getString(ds_list, irow, "ZRQSTAT").toString().equals("40")) {
					updateFlagCancelZSDT1046(param);
				}
 
			} 
		 
			for( int irow = 0; irow < ds_list_detail.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list_detail, irow, "MANDT"));
				param.setQtnum(DatasetUtility.getString(ds_list_detail, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getInt(ds_list_detail, irow, "QTSER"));
				param.setQtseq(DatasetUtility.getInt(ds_list_detail, irow, "QTSEQ"));
				param.setZcostzseq(DatasetUtility.getString(ds_list_detail, irow, "ZCOSTZSEQ"));
				param.setZccd(DatasetUtility.getString(ds_list_detail, irow, "ZCCD"));
				param.setZcgrp(DatasetUtility.getString(ds_list_detail, irow, "ZCGRP"));
				param.setZcct(DatasetUtility.getString(ds_list_detail, irow, "ZCCT"));
				param.setZuam(DatasetUtility.getString(ds_list_detail, irow, "ZUAM"));
				param.setZcost(DatasetUtility.getString(ds_list_detail, irow, "ZCOST"));
				param.setZprcp(DatasetUtility.getString(ds_list_detail, irow, "ZPRCP"));
				param.setZprdi(DatasetUtility.getString(ds_list_detail, irow, "ZPRDI"));
				param.setZprds(DatasetUtility.getString(ds_list_detail, irow, "ZPRDS"));
				param.setUser_id(DatasetUtility.getString(ds_list_detail, irow, "USER_ID"));

				insertZSDT1049(param); 
				
			}

			System.out.print("\n@@@ End Service");
			
			
		}  catch (Exception e) { 
		}
	}   

	// 견적Header(ZSDT1054) 변경
	public void updateZSDT1054(Ses0281 param) {
		Ses0281Dao.updateZSDT1054(param);
	}  

	// 견적Header(ZSDT1054) 변경
	public void insertZSDT1049(Ses0281 param) {
		
		Ses0281Dao.insertZSDT1049(param);
	}  

	public void updateFlagConfirmZSDT1046(Ses0281 param) {
		Ses0281Dao.updateFlagConfirmZSDT1046(param);
	}
	
public void updateFlagCancelZSDT1046(Ses0281 param) {	
		Ses0281Dao.updateFlagCancelZSDT1046(param);
	} 
}
