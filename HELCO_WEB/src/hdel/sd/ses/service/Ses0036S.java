package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0036D;
import hdel.sd.ses.domain.Ses0036;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0036S extends SrmService {

	private final String ZPRGFLG_COST_REQUEST = "31";
	private final String ZAPFLG_COST_REQUEST = "10";
	private final String ZRQFLG_COST = "2"; // 1 : 견적요청

	private Ses0036D Ses0036Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0036Dao = sqlSession.getMapper(Ses0036D.class);
	}
	
	// 기술검토결과등록
	public void save(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		Dataset ds_list = paramVO.getDataset("ds_list");
		// INPUT DATASET GET 

		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0036 param = new Ses0036();						// 저장 파라메터

			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 

			
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt( paramVO.getVariable("G_MANDT") );
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getInt(ds_list, irow, "QTSER"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setCuser( paramVO.getVariable("G_USER_ID") );
				param.setZapflg( ZAPFLG_COST_REQUEST );
				param.setZrqflg( ZRQFLG_COST );
				param.setZprgflg( ZPRGFLG_COST_REQUEST );
				
				insertZSDT1057(param); 
				updateZSDT1046(param);
			}		 
		}  catch (Exception e) {
			throw e;
		}
	}   


	public void insertZSDT1057(Ses0036 param) {
		Ses0036Dao.insertZSDT1057(param);
	}  
	public void updateZSDT1046(Ses0036 param) {
		Ses0036Dao.updateZSDT1046(param);
	}

}
