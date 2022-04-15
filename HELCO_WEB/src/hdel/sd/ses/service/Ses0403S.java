package hdel.sd.ses.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0403D;
import hdel.sd.ses.domain.Ses0403;
import hdel.sd.ses.domain.Ses0403ParamVO;

@Service
public class Ses0403S extends SrmService {

	private Ses0403D Ses0403Dao;

	public void createDao(SqlSession sqlSession) {
		Ses0403Dao = sqlSession.getMapper(Ses0403D.class);
	}

	public List<Ses0403> getListDetail(Ses0403ParamVO param) {
		return Ses0403Dao.selectListDetail(param);
	}

	public List<Ses0403> existing(Ses0403 ses0403Entity) {
		return Ses0403Dao.existing(ses0403Entity);
	}

	public List<Ses0403> getCadVendorAbbr(Ses0403 ses0403Entity) {
		return Ses0403Dao.getCadVendorAbbr(ses0403Entity);
	}

	public void save(Ses0403 ses0403Entity) throws Exception {

		try {

			List<Ses0403> ses0403List;
			ses0403List = existing(ses0403Entity);
			if (ses0403List.size() > 0) {
				Ses0403Dao.updateEntity(ses0403Entity);

			} else {
				Ses0403Dao.insertEntity(ses0403Entity);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public void setSeparate(Ses0403 ses0403Entity, String Flag) throws Exception {
		try {

			if("U".equals(Flag)) {
				List<Ses0403> ses0403SprtList = sprtExisting(ses0403Entity);
				
				if(ses0403SprtList.size() > 0) {
					Ses0403Dao.updateSeparate(ses0403Entity);
				} else {
					Ses0403Dao.insertSeparate(ses0403Entity);
				}
			} else if("D".equals(Flag)) {
				Ses0403Dao.deleteSeparate(ses0403Entity);
			}
			
		} catch (Exception e) {
			throw e;
		}
	}

	private List<Ses0403> sprtExisting(Ses0403 ses0403) {
		return Ses0403Dao.sprtExisting(ses0403);
	}

	public Map<String, Object> selectSprtCs(Ses0403 param) {
		return Ses0403Dao.selectSprtCs(param);
	}

	public Map<String, Object> selectLifnr(Ses0403 param) {
		return Ses0403Dao.selectLifnr(param);
	}

	public Map<String, Object> selectZsdt0198(Ses0403 param) {
		return Ses0403Dao.selectZsdt0198(param);
	}
}