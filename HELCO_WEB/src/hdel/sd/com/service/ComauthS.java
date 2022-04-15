package hdel.sd.com.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.com.dao.ComauthD;
import hdel.sd.com.domain.Comauth;


@Service
public class ComauthS extends SrmService {

	private ComauthD comauthDao;

	@Override
	public void createDao(SqlSession sqlSession) {
		// TODO Auto-generated method stub
		comauthDao = sqlSession.getMapper(ComauthD.class);
	}
	
	public Dataset searchspcialAuth(MipParameterVO paramVO) {
		Comauth param = new Comauth();

		String i_mandt		= paramVO.getVariable("G_MANDT");
		String i_userId  	= paramVO.getVariable("G_USER_ID");						// ªÁøÎ¿ÂID
		String i_pgmId  	= paramVO.getVariable("pgmId");							// Program ID 
		
		param.setMandt(i_mandt);
		param.setUserid(i_userId);
		param.setPgmid(i_pgmId);
		
		List<Comauth> AuthList = comauthDao.searchspcialAuth(param);
		Dataset dsList = paramVO.getDataset("ds_list");
		
		DatasetUtil.moveListToDs(AuthList, dsList);
		
		return dsList;
	}	

}
