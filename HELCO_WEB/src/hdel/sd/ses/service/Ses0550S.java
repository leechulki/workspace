package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0550D;
import hdel.sd.ses.domain.Ses0490;
import hdel.sd.ses.domain.Ses0490ParamVO;
import hdel.sd.ses.domain.Ses0550;
import hdel.sd.ses.domain.Ses0550ParamVO;
import tit.util.DatasetUtility;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0550S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());
	
	private Ses0550D Ses0550Dao;

	public void createDao(SqlSession sqlSession) {
		Ses0550Dao = sqlSession.getMapper(Ses0550D.class);
	}	
	
	public MipResultVO searchTemplate(MipParameterVO paramVO) {
		MipResultVO resultVO = new MipResultVO();
		
		// OUTPUT DATASET DECLARE  
				Dataset ds_list	 = paramVO.getDataset("ds_list");
				Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
				
				try {
					// vo생성
					Ses0550ParamVO param  = new Ses0550ParamVO();
					
					// 변수 세팅
					param.setMandt(paramVO.getVariable("G_MANDT"));
					param.setLang(paramVO.getVariable("G_LANG"));

					String matnr = "L-1000,S-1000,Y-1000,T-1000";
					String[] arrMatnr = matnr.split(",");
					
					ds_list.deleteAll();
					List<Ses0550> list = null;
					
					for(int i = 0; i < arrMatnr.length; i++) {
						param.setMatnr(arrMatnr[i]);
						
						// 서비스호출
						list = Ses0550Dao.selectTemplate(param);
						
						// DATASET 복사
						CallSAPHdel.moveListToDsSub(ds_list, Ses0550.class, list);
					}
					
					
					
		} catch (Exception e) {
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			
			ds_error.setId("ds_error");  		// ERROR 
			resultVO.addDataset(ds_error);  	// ERROR
		}
		
		ds_list.setId("ds_list");
		ds_error.setId("ds_error");
		
		resultVO.addDataset(ds_list);
		resultVO.addDataset(ds_error);

		//return 
		return resultVO;
	}
	
	public void save(MipParameterVO paramVO, MipResultVO resultVO) {
		Dataset ds_list = paramVO.getDataset("ds_list");
		Dataset ds_atnam_2 = paramVO.getDataset("ds_atnam_2");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try {
			// vo생성
			Ses0550 param = new Ses0550();
			
			if(ds_atnam_2.getRowCount()!=0)
			{ 
				for(int i = 0; i < ds_atnam_2.getRowCount(); i++) {
					param.setMandt(paramVO.getVariable("G_MANDT"));
					param.setClasscd(ds_atnam_2.getColumnAsString(i, "CLASSCD"));
					param.setPrh(ds_atnam_2.getColumnAsString(i, "PRH"));
			
					Ses0550Dao.insertZSDT1073(param);	
				}
			}
			for(int i = 0; i < ds_list.getRowCount(); i++) {
				if("".equals(ds_list.getColumnAsString(i, "EXATWRT")) || "X".equals(ds_list.getColumnAsString(i, "EXATWRT"))) {
					param.setMandt(paramVO.getVariable("G_MANDT"));
					param.setPrh(ds_list.getColumnAsString(i, "PRH"));
					param.setAtwrt(ds_list.getColumnAsString(i, "ATWRT"));
					param.setExatwrt(ds_list.getColumnAsString(i, "EXATWRT"));
				
					Ses0550Dao.insertZSDT1072(param);					
				}	
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
			ds_error.setId("ds_error");
			resultVO.addDataset(ds_error); 	// 오류결과내역
		}
		
	}
}
