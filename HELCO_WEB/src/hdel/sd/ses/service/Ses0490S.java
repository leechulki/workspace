package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0490D;
import hdel.sd.ses.domain.Ses0490;
import hdel.sd.ses.domain.Ses0490ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0490S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());
	
	private Ses0490D Ses0490Dao;

	public void createDao(SqlSession sqlSession) {
		Ses0490Dao = sqlSession.getMapper(Ses0490D.class);
	}	
	
	//=========================================================================================
    //  함수기능 	: 견적 사양 및 제외 대상 조회
    //  기능ID   	: UF013
    //  HISTORY : 2016.02.18 최초 코딩 hsi
    //=========================================================================================
	public MipResultVO searchTemplate(MipParameterVO paramVO) {
		MipResultVO resultVO = new MipResultVO();
		
		// OUTPUT DATASET DECLARE  
		Dataset ds_list	 = paramVO.getDataset("ds_list");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI로 return할 error out dataset
		
		try {
			// vo생성
			Ses0490ParamVO param  = new Ses0490ParamVO();
			
			// 변수 세팅
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setLang(paramVO.getVariable("G_LANG"));
			//
			String matnr = "L-1000,S-1000,Y-1000,T-1000";
			String[] arrMatnr = matnr.split(",");
			
			ds_list.deleteAll();
			List<Ses0490> list = null;
			for(int i = 0; i < arrMatnr.length; i++) {
				param.setMatnr(arrMatnr[i]);
				
				// 서비스호출
				list = Ses0490Dao.selectListTemplate(param);
				
				// DATASET 복사
				CallSAPHdel.moveListToDsSub(ds_list, Ses0490.class, list);
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
	
	//=========================================================================================
    //  함수기능 	: 견적 사양 제외 대상 저장
    //  기능ID   	: UF013
    //  HISTORY : 2016.02.18 최초 코딩 hsi
    //=========================================================================================
	public void save(MipParameterVO paramVO, MipResultVO resultVO) {
		Dataset ds_list = paramVO.getDataset("ds_list");
		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		try {
			// vo생성
			Ses0490 param = new Ses0490();
						
			for(int i = 0; i < ds_list.getRowCount(); i++) {
				if("U".equals(ds_list.getColumnAsString(i, "FLAG"))) {
					param.setMandt(ds_list.getColumnAsString(i, "MANDT"));
					param.setMatnr(ds_list.getColumnAsString(i, "MATNR"));
					param.setPrh(ds_list.getColumnAsString(i, "PRH"));
					param.setAgent(ds_list.getColumnAsString(i, "AGENT"));
					
					Ses0490Dao.mergeZSDT1090(param);
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
