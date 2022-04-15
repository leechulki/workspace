package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0340D;
import hdel.sd.ses.domain.Ses0340;
import hdel.sd.ses.domain.Ses0340ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0340S extends SrmService {

	private Ses0340D Ses0340Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0340Dao = sqlSession.getMapper(Ses0340D.class);
	}
	
	public List<Ses0340> selectCheck(Ses0340ParamVO param) {
		return Ses0340Dao.selectCheck(param);
	}
	
	public List<Ses0340> selectList(Ses0340ParamVO param) {
		return Ses0340Dao.selectList(param);
	}

	public List<Ses0340> selectSpec(Ses0340ParamVO param) {
		return Ses0340Dao.selectSpec(param);
	}

	// 원가의뢰요청 접수
	public void saveZSDT1068(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		
		// INPUT DATASET GET 
		Dataset ds_list 		= paramVO.getDataset("ds_list_save");
 	
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0340 	param 		= new Ses0340();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			//------------------------------------------------------------------------
			// 원가의뢰요청 접수 처리 START
			//------------------------------------------------------------------------
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setZconnr(DatasetUtility.getString(ds_list, irow, "ZCONNR"));
				param.setAtnam(DatasetUtility.getString(ds_list, irow, "ATNAM"));
				param.setAtbez(DatasetUtility.getString(ds_list, irow, "ATBEZ"));
				param.setAtwrt(DatasetUtility.getString(ds_list, irow, "ATWRT"));
				param.setSpec(DatasetUtility.getString(ds_list, irow, "SPEC"));
				param.setZqty(DatasetUtility.getString(ds_list, irow, "ZQTY"));
				param.setZuam(DatasetUtility.getString(ds_list, irow, "ZUAM"));
				param.setZamt(DatasetUtility.getString(ds_list, irow, "ZAMT"));
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
				param.setUserid(paramVO.getVariable("G_USER_ID"));

				System.out.print("\n@@@ ds_list_save.MANDT  ===>"+DatasetUtility.getString(ds_list, irow, "MANDT") + "\n");
				System.out.print("\n@@@ ds_list_save.ZCONNR  ===>"+DatasetUtility.getString(ds_list, irow, "ZCONNR") + "\n");
				System.out.print("\n@@@ ds_list_save.ATNAM  ===>"+DatasetUtility.getString(ds_list, irow, "ATNAM") + "\n");
				System.out.print("\n@@@ ds_list_save.ATBEZ  ===>"+DatasetUtility.getString(ds_list, irow, "ATBEZ") + "\n");
				System.out.print("\n@@@ ds_list_save.ATWRT  ===>"+DatasetUtility.getString(ds_list, irow, "ATWRT") + "\n");
				System.out.print("\n@@@ ds_list_save.SPEC  ===>"+DatasetUtility.getString(ds_list, irow, "SPEC") + "\n");
				System.out.print("\n@@@ ds_list_save.ZQTY  ===>"+DatasetUtility.getString(ds_list, irow, "ZQTY") + "\n");
				System.out.print("\n@@@ ds_list_save.ZUAM  ===>"+DatasetUtility.getString(ds_list, irow, "ZUAM") + "\n");
				System.out.print("\n@@@ ds_list_save.ZAMT  ===>"+DatasetUtility.getString(ds_list, irow, "ZAMT") + "\n");
				System.out.print("\n@@@ ds_list_save.ZRMK  ===>"+DatasetUtility.getString(ds_list, irow, "ZRMK") + "\n");
				System.out.print("\n@@@ ds_list_save.G_USER_ID  ===>"+paramVO.getVariable("G_USER_ID") + "\n");
				
				
				
				saveZSDT1068(param); 
				
			} // end of for
		 
		}  catch (Exception e) { 
		}
	}   

	// 견적Header(ZSDT1054) 변경
	public void saveZSDT1068(Ses0340 param) {
		Ses0340Dao.saveZSDT1068(param);
	}  
}
