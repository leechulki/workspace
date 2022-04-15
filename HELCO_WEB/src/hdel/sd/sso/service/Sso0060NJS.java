package hdel.sd.sso.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import hdel.lib.service.SapJCoService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.Dataset;

import tit.service.sapjco3.Rfc;
import tit.service.sapjco3.RfcException;
import tit.service.sapjco3.RfcFactory;
import tit.service.sapjco3.ValueHelper;

/*
 ******************************************************************************************
 * 기      능   : 영업사양 전송대상 조회(sso0020)
 * 작  성  자   :
 * 작성  일자   : 2016.02.12
 * 기능ID       : UF001
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 최초 코딩 박수근
 ******************************************************************************************
*/

@Service
public class Sso0060NJS extends SapJCoService {

	Logger logger = Logger.getLogger(this.getClass());

    //=========================================================================================
    //  SAP RFC ID: ZWEB_CS_GET_MISU
    //  함수기능  : 해당 RFC 함수 데이터 수신 시 날짜 또는 특정 컬럼에 대한 데이터 포맷 변경이 필요한 경우 선언
    //  파라미터  : ZSDS0060T[] list_txt, ZSDS0062[] list_bill, String cmd, String vbeln, String qtnum, 
    //              String stser, ZQMSEMSG[] listMsg, ZSDS0061[] list_item, ZSDS0060[] list_head
    //  리턴값    : Rfc rfc
    //  기능ID    : UF014, UF015
    //  개선내역  : JCO RFC 호출 로직
    //=========================================================================================
    public Rfc callProjectDataSet(String sysid, String cmd, String vbeln, String qtnum, String stser, String rfcName, 
    		Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt) throws RfcException {
    	
        RfcFactory rfcFactory = getRfcFactory(sysid);
        Rfc        rfc        = rfcFactory.createRfc(rfcName);

        // 해당 데이터 셋에 대한 값 변경 처리 
        
        logger.debug("cmd :"+cmd);
        logger.debug("vbeln :"+vbeln);
        logger.debug("qtnum :"+qtnum);
        logger.debug("stser :"+stser);

        // RFC  함수의 입력값을 설정한다.
        // 입력 설정 방법 1 : ImportParameter의 key = value 로 개별적일 필드 값 설정
        // DECIMAL-> type:2
        rfc.setImportValue("I_CMD"  , cmd);
        rfc.setImportValue("I_PSPID", vbeln);
        rfc.setImportValue("I_QTNUM", qtnum);
        rfc.setImportValue("I_QTSER", stser);
        
	     // 마이플랫폼 데이터 타입
	     // ColumnInfo.COLTYPE_INT:2
	     // ColumnInfo.COLTYPE_DECIMAL:4
	     // ColumnInfo.COLTYPE_STRING:1
	     // ColumnInfo.COLTYPE_DATE:8
	     // ColumnInfo.COLTYPE_CURRENCY:5
	     // ColumnInfo.COLTYPE_BLOB:9
        Dataset ds_list_item2 = new Dataset();
        if( ds_list_item != null ) {
            ds_list_item2.setId("ds_list_item2");
	        // sap의 ds Type을 알 수 없기에 이곳에서 새로 ds를 생성 후 sap으로 인계
	        for(int i = 0; i < rfc.getDatasetFromJcoTable("P_ITAB").getColumnCount(); i++) {
	        	// jco에서 DECIMAL을 인지를 못하여 DECIMAL일 경우 강제로 String로 변경
	        	if(rfc.getDatasetFromJcoTable("P_ITAB").getColumnInfo(i).getColumnType() == (short) 4 || rfc.getDatasetFromJcoTable("P_ITAB").getColumnInfo(i).getColumnType() == (short) 2) {
	        		CallSAP.makeDsCol(ds_list_item2, rfc.getDatasetFromJcoTable("P_ITAB").getColumnId(i), 
			                   rfc.getDatasetFromJcoTable("P_ITAB").getColumnInfo(i).getColumnSize(), 
			                   (short) 1);
	        	} else {
	        		CallSAP.makeDsCol(ds_list_item2, rfc.getDatasetFromJcoTable("P_ITAB").getColumnId(i), 
			                   rfc.getDatasetFromJcoTable("P_ITAB").getColumnInfo(i).getColumnSize(), 
			                   rfc.getDatasetFromJcoTable("P_ITAB").getColumnInfo(i).getColumnType());
	        	}
	        }
	        
			for(int i=0; i < ds_list_item.getRowCount(); i++) {
				ds_list_item2.insertRow(i);
				for(int j = 0; j < ds_list_item.getColumnCount(); j++) {
					ds_list_item2.setColumn(i, ds_list_item.getColumnId(j), ds_list_item.getColumn(i, ds_list_item.getColumnId(j)));
				}
			}
        }
        
        Dataset ds_list_bill2 = new Dataset();
        if(  ds_list_bill != null ) {
            ds_list_bill2.setId("ds_list_bill2");
            // sap의 ds Type을 알 수 없기에 이곳에서 새로 ds를 생성 후 sap으로 인계
            for(int i = 0; i < rfc.getDatasetFromJcoTable("B_ITAB").getColumnCount(); i++) {
            	// jco에서 DECIMAL을 인지를 못하여 DECIMAL일 경우 강제로 String로 변경
            	if(rfc.getDatasetFromJcoTable("B_ITAB").getColumnInfo(i).getColumnType() == (short) 4) {
            		CallSAP.makeDsCol(ds_list_bill2, rfc.getDatasetFromJcoTable("B_ITAB").getColumnId(i), 
    		                   rfc.getDatasetFromJcoTable("B_ITAB").getColumnInfo(i).getColumnSize(), 
    		                   (short) 1);
            	} else {
            		CallSAP.makeDsCol(ds_list_bill2, rfc.getDatasetFromJcoTable("B_ITAB").getColumnId(i), 
    		                   rfc.getDatasetFromJcoTable("B_ITAB").getColumnInfo(i).getColumnSize(), 
    		                   rfc.getDatasetFromJcoTable("B_ITAB").getColumnInfo(i).getColumnType());
            	}
            }

    		for(int i=0; i < ds_list_bill.getRowCount(); i++) {
    			ds_list_bill2.insertRow(i);
    			for(int j = 0; j < ds_list_bill.getColumnCount(); j++) {
    				ds_list_bill2.setColumn(i, ds_list_bill.getColumnId(j), ds_list_bill.getColumn(i, ds_list_bill.getColumnId(j)));
    			}
    		}
        }
        
        rfc.setJcoTableFromDataset(ds_dtl, "T_ITAB");
        rfc.setJcoTableFromDataset(ds_list_item2, "P_ITAB");
        rfc.setJcoTableFromDataset(ds_list_bill2, "B_ITAB");
        rfc.setJcoTableFromDataset(ds_list_txt, "BIGO");

        logger.debug("ds_dtl :"+ds_dtl);
        logger.debug("ds_list_item2 :"+ds_list_item2);
        logger.debug("ds_list_bill2 :"+ds_list_bill2);
        logger.debug("ds_list_txt :"+ds_list_txt);
        
        // sap rfc 수행
        rfc.execute();

        return rfc;
    }
}

