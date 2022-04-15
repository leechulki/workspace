package hdel.sd.sso.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.ZSDT0094D;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.ZSDT0094;
import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0059D;
import hdel.sd.sso.domain.Sso0059;

@Service
public class Sso0059S extends SrmService {

	private Sso0059D sso0059Dao;
	private ZSDT0094D zsdt0094Dao;

	public void createDao(SqlSession sqlSession) {
		sso0059Dao = sqlSession.getMapper(Sso0059D.class);
		zsdt0094Dao = sqlSession.getMapper(hdel.lib.dao.ZSDT0094D.class);
	}

	public Dataset searchItemList(MipParameterVO paramVO) throws Exception {
		Sso0059 param = new Sso0059();

		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMandt(paramVO.getVariable("G_MANDT"));

		List<Sso0059> hogiList = sso0059Dao.searchItemList(param);
		Dataset dsList = paramVO.getDataset("ds_item");
		DatasetUtil.moveListToDs(hogiList, dsList);

		return dsList;
	}

	public Dataset searchItemChar(MipParameterVO paramVO) throws Exception {
		Sso0059 param = new Sso0059();

		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setItemno(paramVO.getVariable("item"));

		List<Sso0059> z59List = sso0059Dao.searchCharByItem(param);
		Dataset dsChar = paramVO.getDataset("ds_char");
		DatasetUtil.moveListToDs(z59List, dsChar);

		return dsChar;
	}
}