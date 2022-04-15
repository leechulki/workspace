package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0035D;
import hdel.sd.ses.domain.Ses0035;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0035S extends SrmService {

	private final String ZAPFLG_INIT = "10";	// ��û

	private final String ZPRGFLG_21 = "21";		// �����븮�� ������û
	private final String ZPRGFLG_34 = "34";		// �ؿܺ��� ������������ ��û

	private Ses0035D Ses0035Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0035Dao = sqlSession.getMapper(Ses0035D.class);
	}
	
	// ������������
	public void save(MipParameterVO paramVO) throws Exception{
		Dataset ds_list = paramVO.getDataset("ds_list");
		// INPUT DATASET GET 
		
		try
		{
			//------------------------------------------------------------------------
			// ���� ����
			//------------------------------------------------------------------------
			Ses0035 	param 		= new Ses0035();						// ���� �Ķ����
			
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(paramVO.getVariable("G_MANDT"));

				param.setQtnum(DatasetUtility.getString(ds_list, irow, "QTNUM"));
				param.setQtser(DatasetUtility.getInt(ds_list, irow,    "QTSER"));
				param.setZrqcn(DatasetUtility.getString(ds_list, irow, "ZRQCN"));
				param.setCuser(paramVO.getVariable("G_USER_ID"));
				param.setZrqflg( DatasetUtility.getString(ds_list, irow, "ZRQFLG") );
				param.setZapflg( ZAPFLG_INIT );
				
				if ( "1".equals( param.getZrqflg() ) )			{	// �����븮�� ������û�� ���
					param.setZprgflg( ZPRGFLG_21 );
				}else if ( "3".equals( param.getZrqflg() ) )	{	// �ؿܺ��� ������ ���� ��û
					param.setZprgflg( ZPRGFLG_34 );
				}

				insertZSDT1057(param); 
				updateZSDT1046(param);
			}
			
		 
		}  catch (Exception e) {
			throw e;
		}
	}   

	// ��������û/���(ZSDT1058) ����
	public void insertZSDT1057(Ses0035 param) {
		Ses0035Dao.insertZSDT1057(param);
	}  
	public void updateZSDT1046(Ses0035 param) {
		Ses0035Dao.updateZSDT1046(param);
	} 

}
