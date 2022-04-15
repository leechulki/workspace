package hdel.sd.sbi.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.domain.OrderItem;
import com.sap.domain.OrderNo;
import com.sap.domain.Spras;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.ZSDTDUTYEXCPTD;
import hdel.lib.service.SrmService;
import hdel.lib.service.ZSDTDUTYEXCPTDS;
import hdel.sd.sbi.dao.Sbi0050D;
import hdel.sd.sbi.domain.Sbi0050;

@Service
public class Sbi0050S extends SrmService {
	@Autowired
	private ZSDTDUTYEXCPTDS dutyExcptService;

	Sbi0050D sbi0050d;

	public void createDao(SqlSession sqlSession) {
		dutyExcptService = new ZSDTDUTYEXCPTDS();
		dutyExcptService.createDao(sqlSession);

		sbi0050d = sqlSession.getMapper(hdel.sd.sbi.dao.Sbi0050D.class);
	}

	public void modifyDutyException(MipParameterVO paramVO) {
		ZSDTDUTYEXCPTD param;

		Dataset dsList = paramVO.getDataset("ds_list");
		Integer ordseq = new Integer(0);
		String blockno = "";

		List<ZSDTDUTYEXCPTD> lstDutyExcpt = new ArrayList<ZSDTDUTYEXCPTD>();
		List<ZSDTDUTYEXCPTD> lstDutyExcptDel = new ArrayList<ZSDTDUTYEXCPTD>();
		for(int i=0; i < dsList.getRowCount(); i++) {
			param = new ZSDTDUTYEXCPTD();
			param.setMandt(paramVO.getVariable("G_MANDT"));
			param.setOrdno(new OrderNo(dsList.getColumnAsString(i, "ordno")));
			ordseq = dsList.getColumnAsInteger(i, "ordseq");
			param.setOrdseq(ordseq == null ? 0 : ordseq);
			param.setOrditem(new OrderItem(dsList.getColumnAsString(i, "orditem")));
			blockno = dsList.getColumnAsString(i, "blockno");
			param.setBlockno(blockno == null ? "" : blockno);
			param.getTstmp().setTimeStamp(paramVO.getVariable("G_USER_ID"));
			if(BooleanUtils.toBoolean(NumberUtils.toInt(dsList.getColumnAsString(i, "delflag")))) {
				lstDutyExcptDel.add(param);
			} else {
				lstDutyExcpt.add(param);
			}
		}

		dutyExcptService.mergeDutyExcption(lstDutyExcpt);
		dutyExcptService.deleteDutyExcption(lstDutyExcptDel);
	}

	public OrderItem searchOrderItem(MipParameterVO paramVO) {
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Sbi0050 param = new Sbi0050();
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setOrderno(new OrderNo(dsCond.getColumnAsString(0, "ordno")));
		param.setOrderseq(dsCond.getColumnAsInteger(0, "ordseq"));
		param.setOrderitemnm(paramVO.getVariable("orditemnm"));
		OrderItem ordItem = new OrderItem();
		if(NumberUtils.isNumber(param.getOrderitemnm()))
			ordItem = sbi0050d.searchQuotOrderItem(param);
		else
			ordItem = sbi0050d.searchProjOrderItem(param);
		return ordItem == null ? new OrderItem() : ordItem;
	}
	public String searchBlockName(MipParameterVO paramVO) {
		return sbi0050d.searchBlockName(paramVO.getVariable("G_MANDT"), paramVO.getVariable("blockno"), new Spras(paramVO.getVariable("G_LANG")));
	}
}