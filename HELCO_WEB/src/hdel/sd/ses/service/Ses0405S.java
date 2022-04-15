package hdel.sd.ses.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0405D;
import hdel.sd.ses.dao.Ses0407D;
import hdel.sd.ses.domain.Ses0405;
import hdel.sd.ses.domain.Ses0407;

@Service
public class Ses0405S extends SrmService {

	private Ses0405D Ses0405Dao;
	private Ses0407D Ses0407Dao;

	public void createDao(SqlSession sqlSession) {
		Ses0405Dao = sqlSession.getMapper(Ses0405D.class);
		Ses0407Dao = sqlSession.getMapper(Ses0407D.class);
	}

	public Dataset searchAnnualUnitCost(MipParameterVO paramVO) throws ParseException {
		Ses0405 param = new Ses0405();

		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMandt(paramVO.getVariable("G_MANDT"));
		param.setFr_bsdat(param.getBsdat());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(param.getBsdat()));
		calendar.add(Calendar.DATE, 300);
		param.setTo_bsdat(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));

		List<Ses0405> lstSes0405 = Ses0405Dao.searchAnnualUnitCost(param);

		Dataset dsList = paramVO.getDataset("ds_list");
		if(lstSes0405 != null) {
			for (Ses0405 ses0405 : lstSes0405) {
				if(ses0405.getQtnum().equals(""))
					continue;

				ses0405.setDcsdat();
				float f = TimeUnit.DAYS.convert(new SimpleDateFormat("yyyyMMdd").parse(ses0405.getDcsdat().getValue()).getTime() 
						- new SimpleDateFormat("yyyyMMdd").parse(param.getBsdat()).getTime(), TimeUnit.MILLISECONDS);
				ses0405.setDaysno((int) f);
				ses0405.setMonthno((int) Math.floor(ses0405.getDaysno().intValue() / 30) + ((ses0405.getDaysno().intValue() % 30) == 0 ? 0 : 1));
				
				calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(ses0405.getDcsdat().getValue()));
				calendar.add(Calendar.DATE, -90);
				ses0405.setDel90(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));

				f = TimeUnit.DAYS.convert(calendar.getTime().getTime() - Calendar.getInstance().getTime().getTime(), TimeUnit.MILLISECONDS);
				ses0405.setDel90Days((int) f);
			}
			
			DatasetUtil.moveListToDs(lstSes0405, dsList);
		}
		return dsList;
	}
	public void mergeIntoReport(Dataset dsReport, Dataset dsSes0405) {
		List<String> arrZ3 = new ArrayList<String>();
		List<String> arrQtnum = new ArrayList<String>();

		dsReport.deleteAll();
		for(int idx=0; idx < dsSes0405.getRowCount(); idx++) {
			String qtnum = dsSes0405.getColumnAsString(idx, "qtnum");
			if(arrQtnum.contains(qtnum))
				continue;

			int dsReportRowIdx = 0;

			String parz3 = dsSes0405.getColumnAsString(idx, "parz3");
			if(arrZ3.contains(parz3)) {
				for(int i=0; i < dsReport.getRowCount(); i++) {
					if(parz3.equals(dsReport.getColumnAsString(i, "parz3"))) {
						dsReportRowIdx = i;
						break;
					}
				}
			} else {
				arrZ3.add(parz3);
				dsReport.appendRow();
				dsReportRowIdx = dsReport.getRowCount()-1;
			}

			double sValue = 0, eValue = 0;
			double stotValue = 0, etotValue = 0;
			dsReport.setColumn(dsReportRowIdx, "dept", dsSes0405.getColumn(idx, "dept"));
			dsReport.setColumn(dsReportRowIdx, "dept_n", dsSes0405.getColumn(idx, "dept_n"));
			dsReport.setColumn(dsReportRowIdx, "vkbur", dsSes0405.getColumn(idx, "vkbur"));
			dsReport.setColumn(dsReportRowIdx, "vkgrp", dsSes0405.getColumn(idx, "vkgrp"));
			dsReport.setColumn(dsReportRowIdx, "parz3", dsSes0405.getColumn(idx, "parz3"));
			dsReport.setColumn(dsReportRowIdx, "parz3nm", dsSes0405.getColumn(idx, "parz3nm"));

			if(!qtnum.equals("")) {
				arrQtnum.add(qtnum);
				int month = NumberUtils.toInt(dsSes0405.getColumnAsString(idx, "monthno"), 0);
	
				String sColName = "s" + String.format("%02d", month);
				String eColName = "e" + String.format("%02d", month);
				
				
				sValue = NumberUtils.toInt(dsReport.getColumnAsString(dsReportRowIdx, sColName),0);
				if(NumberUtils.toInt(dsSes0405.getColumnAsString(idx, "dcsdat"),0) > 0) {
					sValue = sValue + 1;
					dsReport.setColumn(dsReportRowIdx, sColName, sValue);	//s##					
					stotValue = NumberUtils.toInt(dsReport.getColumnAsString(dsReportRowIdx, "stot"),0) + 1;
					dsReport.setColumn(dsReportRowIdx, "stot", stotValue);
					/*if(!"".equals(dsSes0405.getColumnAsString(idx, "pjtid").trim())) {
						stotValue = NumberUtils.toInt(dsReport.getColumnAsString(dsReportRowIdx, "stot"),0) + 1;
						dsReport.setColumn(dsReportRowIdx, "stot", stotValue);
					}*/
				}
				eValue = NumberUtils.toInt(dsReport.getColumnAsString(dsReportRowIdx, eColName),0);
				if(NumberUtils.toInt(dsSes0405.getColumnAsString(idx, "findat"),0) > 0) {
					eValue = eValue + 1;
					dsReport.setColumn(dsReportRowIdx, eColName, eValue);	//e##					
					etotValue = NumberUtils.toInt(dsReport.getColumnAsString(dsReportRowIdx, "etot"),0) + 1;
					dsReport.setColumn(dsReportRowIdx, "etot", etotValue);	
					/*if(!"".equals(dsSes0405.getColumnAsString(idx, "pjtid").trim())) {
						etotValue = NumberUtils.toInt(dsReport.getColumnAsString(dsReportRowIdx, "etot"),0) + 1;
						dsReport.setColumn(dsReportRowIdx, "etot", etotValue);
					}*/
				}
			}
		}
	}
	public Dataset searchAUCKunnr(MipParameterVO paramVO) throws ParseException {
		Ses0407 param = new Ses0407();

		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMandt(paramVO.getVariable("G_MANDT"));

		
		List<Ses0407> lstSes0407 = Ses0407Dao.searchAUCKunnr(param);

		Dataset dsList = paramVO.getDataset("ds_list");
		DatasetUtil.moveListToDs(lstSes0407, dsList);

		return dsList;
	}
	public void updateAUCKunnr(MipParameterVO paramVO) throws Exception {
		Dataset dsList = paramVO.getDataset("ds_list");
		Ses0407 param = new Ses0407();
		
		if(dsList == null)
				return;
		for(int i=0; i < dsList.getRowCount(); i++) {
			try {
				DatasetUtil.moveDsRowToObj(dsList, i, param);
				param.setMandt(paramVO.getVariable("G_MANDT"));
				param.setUdate(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));
				param.setUname(paramVO.getVariable("G_USER_ID"));
				Ses0407Dao.updateAUCKunnr(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}