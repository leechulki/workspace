package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0560D;
import hdel.sd.ses.domain.Ses0550;
import hdel.sd.ses.domain.Ses0560;
import hdel.sd.ses.domain.Ses0560ParamVO;
import tit.util.DatasetUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0560S extends SrmService {

	private Ses0560D Ses0560Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0560Dao = sqlSession.getMapper(Ses0560D.class);
	}
	
	public List<Ses0560> searchHeader(Ses0560ParamVO param) {
		return Ses0560Dao.selectListHeader(param);
	}


public void save(MipParameterVO paramVO, MipResultVO resultVO) {
	Dataset ds_cond = paramVO.getDataset("ds_cond");
	Dataset ds_ch_zkunnr = paramVO.getDataset("ds_ch_zkunnr");
	Dataset ds_header = paramVO.getDataset("ds_header");
	Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
	
	try {
		// vo생성

		if(ds_header == null || ds_header.getRowCount() == 0)
            return;

		List<Ses0560ParamVO> listSes0560ParamVO = new ArrayList<Ses0560ParamVO>();
		for(int i=0; i < ds_header.getRowCount(); i++) {

			Ses0560ParamVO param = new Ses0560ParamVO();
				
				
				param.setMandt(paramVO.getVariable("G_MANDT"));
				param.setCh_vkbur(DatasetUtility.getString(ds_ch_zkunnr,"CH_VKBUR"  )); 
				param.setCh_vkgrp(DatasetUtility.getString(ds_ch_zkunnr,"CH_VKGRP"  ));
				param.setCh_zkunnr(DatasetUtility.getString(ds_ch_zkunnr,"CH_ZKUNNR"  ));
				param.setCh_zkunnr_nm(DatasetUtility.getString(ds_ch_zkunnr,"CH_ZKUNNR_NM"  ));
				param.setQtnum(ds_header.getColumnAsString(i, "QTNUM"));

				listSes0560ParamVO.add(param);
		}

		Map<String, Object> mapSes0560 = new HashMap<String, Object>();
		mapSes0560.put("changeZkunnr", listSes0560ParamVO);
		mapSes0560.put("entity", listSes0560ParamVO.get(0));
		Ses0560Dao.updateZSDT1046(mapSes0560);

	} catch (Exception e) {
		e.printStackTrace(); 
		ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error); 	// 오류결과내역
	}
	
}

}