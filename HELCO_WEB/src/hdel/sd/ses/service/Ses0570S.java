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
import hdel.sd.ses.dao.Ses0570D;
import hdel.sd.ses.dao.Ses0407D;
import hdel.sd.ses.domain.Ses0570;
import hdel.sd.ses.domain.Ses0407;

@Service
public class Ses0570S extends SrmService {

	private Ses0570D Ses0570Dao;
	private Ses0407D Ses0407Dao;

	public void createDao(SqlSession sqlSession) {
		Ses0570Dao = sqlSession.getMapper(Ses0570D.class);
		Ses0407Dao = sqlSession.getMapper(Ses0407D.class);
	}

	public Dataset findRequestSchedule(MipParameterVO paramVO) throws ParseException {
		Ses0570 param = new Ses0570();
		Dataset dsCond = paramVO.getDataset("ds_cond");
		DatasetUtil.moveDsRowToObj(dsCond, 0, param);
		param.setMandt(paramVO.getVariable("G_MANDT"));

		Dataset dsList = paramVO.getDataset("ds_list");
		List<Ses0570> lstSes0570 = Ses0570Dao.findRequestSchedule(param);

		if (lstSes0570 != null) {
			for (Ses0570 ses0570 : lstSes0570) {
				if (ses0570.getFindat().equals(""))
					continue;

				long daysno = new SimpleDateFormat("yyyyMMdd").parse(ses0570.getFindat()).getTime()
						- new SimpleDateFormat("yyyyMMdd").parse(ses0570.getZrqdat()).getTime();
				long f = daysno / (24 * 60 * 60 * 1000);
				ses0570.setDaysno((int) f);

				if (ses0570.getDaysno() <= 10) {
					ses0570.setRange((int) 1);
				} else if (ses0570.getDaysno() >= 11 && ses0570.getDaysno() <= 14) {
					ses0570.setRange((int) 2);
				} else if (ses0570.getDaysno() >= 15 && ses0570.getDaysno() <= 21) {
					ses0570.setRange((int) 3);
				} else if (ses0570.getDaysno() > 21) {
					ses0570.setRange((int) 4);
				}
			}

			DatasetUtil.moveListToDs(lstSes0570, dsList);
		}

		return dsList;

	}

	public void mergeIntoReport(Dataset dsReport, Dataset dsSes0570) {
		List<String> arrZ3 = new ArrayList<String>();
		int totalzrqdat = 0;
		dsReport.deleteAll();

		for (int idx = 0; idx < dsSes0570.getRowCount(); idx++) {

			int dsReportRowIdx = 0;

			String kunnr_z3 = dsSes0570.getColumnAsString(idx, "kunnr_z3");
			if (arrZ3.contains(kunnr_z3)) {
				for (int i = 0; i < dsReport.getRowCount(); i++) {
					if (kunnr_z3.equals(dsReport.getColumnAsString(i, "kunnr_z3"))) {
						dsReportRowIdx = i;
						totalzrqdat++;
						break;
					}
				}
			} else {
				arrZ3.add(kunnr_z3);
				dsReport.appendRow();
				dsReportRowIdx = dsReport.getRowCount() - 1;
				totalzrqdat = 0;
				totalzrqdat++;
			}

			dsReport.setColumn(dsReportRowIdx, "kunnr_z3", dsSes0570.getColumn(idx, "kunnr_z3"));
			dsReport.setColumn(dsReportRowIdx, "kunnr_z3_nm", dsSes0570.getColumn(idx, "kunnr_z3_nm"));
			dsReport.setColumn(dsReportRowIdx, "totalzrqdat", totalzrqdat);
			dsReport.setColumn(dsReportRowIdx, "part_code", dsSes0570.getColumn(idx, "part_code"));

			double sValue = 0;
			double stotValue = 0;

			int range = NumberUtils.toInt(dsSes0570.getColumnAsString(idx, "range"), 0);
			String sColName = "s" + String.format("%02d", range);

			sValue = NumberUtils.toInt(dsReport.getColumnAsString(dsReportRowIdx, sColName), 0);
			sValue = sValue + 1;
			dsReport.setColumn(dsReportRowIdx, sColName, sValue); // s##

		}

	}

}