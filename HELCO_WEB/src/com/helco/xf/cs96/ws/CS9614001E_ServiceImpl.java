package com.helco.xf.cs96.ws;

import java.io.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;

import javax.servlet.http.HttpSession;

import com.tobesoft.platform.*;
import com.tobesoft.platform.data.*;

import com.helco.xf.cs12.evladm.BizException;
import com.helco.xf.cs12.evladm.ComMethodDao;
import com.helco.xf.cs12.evladm.dbutil.CommonUtil;
import com.helco.xf.cs12.evladm.dbutil.DBConstants;
import com.helco.xf.cs12.evladm.dbutil.DBUtil;
import com.helco.xf.cs12.evladm.dbutil.DateTime;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;
import com.helco.xf.cs12.evladm.vo.ComMethodVo;
import com.helco.xf.cs12.evladm.vo.TBEBWEF1Vo;
import com.helco.xf.cs12.evladm.dbutil.*;
import com.helco.xf.comm.Utillity;

import tit.base.ServiceManagerFactory;
import tit.biz.AbstractBusinessService;
import tit.biz.BusinessContext;
import tit.service.business.config.ConfigUtility;
import tit.service.core.logger.Logger;
import tit.service.sqlmap.SqlExecutor;
import tit.service.sqlmap.SqlMapManagerUtility;
import tit.service.sqlmap.SqlRequest;
import tit.service.sqlmap.SqlResult;
import tit.service.sqlmap.dataset.DatasetSqlExecutor;
import tit.service.sqlmap.dataset.DatasetSqlRequest;
import tit.util.DatasetUtility;
import tit.util.StringOperator;
import tit.util.TitUtility;

public class CS9614001E_ServiceImpl extends AbstractBusinessService implements CS9614001E_Service {
	static Logger logger;

	@SuppressWarnings("deprecation")
	public Dataset selectList(BusinessContext ctx, Dataset ds_cond) throws Exception {
		String default_charset = "euc-kr";
		String outDSnm = "output";
		int ColSize = 255;

		Dataset RDS = new Dataset(outDSnm, default_charset);

		String mdt = ctx.getInputVariable("G_MANDT");
		String db_con = Utillity.getSapJndi(mdt);

		DatasetSqlExecutor executor = new DatasetSqlExecutor(getConnection(db_con));

		DatasetSqlRequest zmmt138s = SqlMapManagerUtility.makeSqlRequestForDataset("cs96:CS9614001E_S1");
		zmmt138s.addParamObject("ds_cond", ds_cond);
		zmmt138s.addParamObject("G_MANDT", mdt);

		zmmt138s.setRowIndex(0);
		Dataset dsList = (Dataset)executor.query(zmmt138s).getResultObject();

		RDS.addColumn("IMG", ColumnInfo.CY_COLINFO_BLOB, ColSize);
		RDS.addStringColumn("SZE");
		RDS.addStringColumn("SPC");
		RDS.addStringColumn("MEINS");
		RDS.addDecimalColumn("COST");
		RDS.addDecimalColumn("AMT");
		RDS.addStringColumn("URL");
//		RDS.addColumn("IMG_FILE", ColumnInfo.CY_COLINFO_STRING, ColSize);
//		RDS.addColumn("IMG_SIZE", ColumnInfo.CY_COLINFO_INT, ColSize);

		for(int i=0; i<dsList.getRowCount(); i++) {
			String urlStr;
//			urlStr = "http://localhost:8088/HELCO_WEB/ui/HELCO_WEB/IMG/";
//			urlStr = "http://srm.hdel.co.kr/ui/HELCO_WEB/IMG/";
//			urlStr = "/srm/HELCO_WEB/HELCO_WEB.war/ui/HELCO_WEB/IMG/";
//			urlStr = "/ui/HELCO_WEB/IMG/";
			urlStr = "http://127.0.0.1/ui/HELCO_WEB/IMG/";

			InputStream resultInStream = null;
			RDS.appendRow();

			try {
				RDS.setColumn(i, "SZE", dsList.getColumnAsString(i, "SZE"));
				RDS.setColumn(i, "SPC", dsList.getColumnAsString(i, "SPC"));
				RDS.setColumn(i, "MEINS", dsList.getColumnAsString(i, "MEINS"));
				RDS.setColumn(i, "COST", dsList.getColumnAsCurrency(i, "COST"));
				RDS.setColumn(i, "AMT", dsList.getColumnAsCurrency(i, "AMT"));

				if(dsList.getColumnAsString(i, "IMG").trim().length() > 0) {
					URL url = new URL(urlStr + dsList.getColumnAsString(i, "IMG") + ".jpg");

					try {
						resultInStream = url.openStream();
					} catch (Exception es) {
						System.out.println("Exception(es) =====> " + es);
					}

					byte[] buffer = new byte[4096];
					int bytes_read;
					ByteArrayOutputStream ImageData = new ByteArrayOutputStream();

					while((bytes_read=resultInStream.read(buffer)) != -1) {
					    ImageData.write(buffer,0,bytes_read);
					}

					RDS.setColumn(i, "IMG", ImageData.toByteArray());
					RDS.setColumn(i, "URL", url.getPath());

				    System.out.println("getPath() : [" + url.getPath() + "]"); //경로 + 파일명
				} else {
					RDS.setColumn(i, "IMG", "");
					RDS.setColumn(i, "URL", "");

					System.out.println("getPath() : [No Image!!!]"); //경로 + 파일명
				}
//				RDS.setColumn(i, "IMG_FILE", urlStr);
//				RDS.setColumn(i, "IMG_SIZE", ImageData.toByteArray().length);

				resultInStream.close();
		 	} catch (Exception e) {
		 		System.out.println("Exception(e) =====> " + e);
		 	} finally {
				try {
					resultInStream.close();  
				} catch (Exception e) {
				}
			}
		}

		return RDS;
	}
}
