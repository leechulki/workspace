package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0082D;
import hdel.sd.ses.domain.Ses0082;
import hdel.sd.ses.domain.Ses0082ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0082S extends SrmService {

	private final String ZPRGFLG_23 = "23";
	private final String ZPRGFLG_24 = "24";
	private final String ZPRGFLG_25 = "25";
	private final String ZPRGFLG_26 = "26";

	private final String ZRQFLG_1   = "1";		// 국내 견적승인요청
	
	private Ses0082D Ses0082Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0082Dao = sqlSession.getMapper(Ses0082D.class);
	}

	public List<Ses0082> searchDetail(Ses0082ParamVO param) {
		return Ses0082Dao.selectListDetail(param);
	}
	
	public void save(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		Dataset ds_list = paramVO.getDataset("ds_list3");
		// INPUT DATASET GET 
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0082 param = new Ses0082();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 

			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt( paramVO.getVariable("G_MANDT") );
				param.setZrqseq(DatasetUtility.getString(ds_list, irow, "ZRQSEQ"));
				param.setQtnum(DatasetUtility.getString(ds_list, irow,  "QTNUM"));
				param.setQtser(DatasetUtility.getInt(ds_list, irow,     "QTSER"));
				param.setZapflg(DatasetUtility.getString(ds_list, irow, "ZAPFLG"));
				param.setZrtrsn(DatasetUtility.getString(ds_list, irow, "ZRTRSN"));
				param.setUsergbn(DatasetUtility.getString(ds_list, irow, "USERGBN"));
				param.setZrqflg(ZRQFLG_1);
				
				if ("20".equals( param.getZapflg() ))	{		// 담당자 접수
					param.setZprgflg(ZPRGFLG_23);
				}else if ("30".equals( param.getZapflg() ))	{	// 팀장 승인
					param.setZprgflg(ZPRGFLG_25);
				}else if ("40".equals( param.getZapflg() ))	{	// 반려
					if ("30".equals( param.getUsergbn() ) )	{	// 담당반려
						param.setZprgflg(ZPRGFLG_24);
					}else {
						param.setZprgflg(ZPRGFLG_26);			// 팀장 및 관리자 반려
					}
				}
				
				param.setUuser( paramVO.getVariable("G_USER_ID") );
 
				updateZSDT1057(param);
				updateZSDT1046(param);
			}
			
		 
		}  catch (Exception e) { 
		}
	}

	public void updateZSDT1057(Ses0082 param) {
		Ses0082Dao.updateZSDT1057(param);
	}

	public void updateZSDT1046(Ses0082 param) {
		Ses0082Dao.updateZSDT1046(param);
	} 
}
