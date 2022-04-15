package hdel.sd.sso.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.dao.ZSDT0004D;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.domain.ZSDT0004;
import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0058D;
import hdel.sd.sso.domain.Sso0058;

@Service
public class Sso0058S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

    private ZSDT0004D zsdt0004d;
    private Sso0058D sso0058d;
    
	
	@Override

	public void createDao(SqlSession sqlSession) {
		zsdt0004d = sqlSession.getMapper(ZSDT0004D.class);
		sso0058d = sqlSession.getMapper(hdel.sd.sso.dao.Sso0058D.class);
	}
	
	public void getHogiStatus(MipParameterVO paramVO, MipResultVO resultVO) throws Exception {

		String mandt	= paramVO.getVariable("G_MANDT");
		String pVbeln	= paramVO.getVariable("p_vbeln");
		String pSeq		= paramVO.getVariable("p_seq");
		if(Integer.parseInt(pSeq) == 0)
			throw new Exception("변경 불가능 차수(0).");

		Dataset dsHogiInfo = paramVO.getDataset("ds_hogi_info");
		Sso0058 sso0058 = new Sso0058(mandt, pVbeln, Integer.valueOf(pSeq));
		sso0058 = sso0058d.selectZSDT0090FINL(sso0058);
		if(sso0058 == null)
			throw new Exception("저장 후 호기변경 가능.");
		if(sso0058.getRecad_da().equals("00000000"))
			throw new Exception("수주승인 후 호기변경 가능.");
		if(!sso0058.getFinl().equals(""))
			throw new Exception("원가의뢰전 상태에서만 호기변경 가능.");

		ZSDT0004 zsdt0004 = new ZSDT0004();
		zsdt0004.setMandt(mandt);
		zsdt0004.setVbeln(pVbeln);
		List<ZSDT0004> lstZsdt0004 = zsdt0004d.selectByVbeln(zsdt0004);

		dsHogiInfo.deleteAll();
		for(ZSDT0004 iterator : lstZsdt0004)  {
			dsHogiInfo.appendRow();
			dsHogiInfo.setColumn(dsHogiInfo.getRowCount()-1, "hogi", iterator.getHogi());
			dsHogiInfo.setColumn(dsHogiInfo.getRowCount()-1, "tp_date2", iterator.getTp_date2());
			dsHogiInfo.setColumn(dsHogiInfo.getRowCount()-1, "tp_date3", iterator.getTp_date3());
		}

		resultVO.addDataset(dsHogiInfo);
	}
	public void saveChangedHogi(MipParameterVO paramVO, MipResultVO resultVO) throws Exception {
		Dataset dsChgItem = paramVO.getDataset("ds_chgItem");
        Integer seq = new Integer(paramVO.getVariable("p_seq")).intValue();

		if(dsChgItem == null || dsChgItem.getRowCount() == 0)
			return;

		List<Sso0058> lstSso0058 = new ArrayList<Sso0058>();
		for(int i=0; i < dsChgItem.getRowCount(); i++) {
			Sso0058 sso0058 = new Sso0058();
			DatasetUtil.moveDsRowToObj(dsChgItem, i, sso0058);
			sso0058.setMandt(paramVO.getVariable("G_MANDT"));
			sso0058.setSeq(seq);
			sso0058.getTstmp().setTimeStamp(paramVO.getVariable("G_USER_ID"));
			lstSso0058.add(sso0058);
		}
		Map<String, Object> mapSso0058 = new HashMap<String, Object>();
		mapSso0058.put("swapHogi", lstSso0058);
		mapSso0058.put("entity", lstSso0058.get(0));
		sso0058d.swapHogi91(mapSso0058);
		sso0058d.swapHogi93(mapSso0058);
		sso0058d.swapHogi94(mapSso0058);
	}
}