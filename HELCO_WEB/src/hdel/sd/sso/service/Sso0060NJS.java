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
 * ��      ��   : ������� ���۴�� ��ȸ(sso0020)
 * ��  ��  ��   :
 * �ۼ�  ����   : 2016.02.12
 * ���ID       : UF001
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 ���� �ڵ� �ڼ���
 ******************************************************************************************
*/

@Service
public class Sso0060NJS extends SapJCoService {

	Logger logger = Logger.getLogger(this.getClass());

    //=========================================================================================
    //  SAP RFC ID: ZWEB_CS_GET_MISU
    //  �Լ����  : �ش� RFC �Լ� ������ ���� �� ��¥ �Ǵ� Ư�� �÷��� ���� ������ ���� ������ �ʿ��� ��� ����
    //  �Ķ����  : ZSDS0060T[] list_txt, ZSDS0062[] list_bill, String cmd, String vbeln, String qtnum, 
    //              String stser, ZQMSEMSG[] listMsg, ZSDS0061[] list_item, ZSDS0060[] list_head
    //  ���ϰ�    : Rfc rfc
    //  ���ID    : UF014, UF015
    //  ��������  : JCO RFC ȣ�� ����
    //=========================================================================================
    public Rfc callProjectDataSet(String sysid, String cmd, String vbeln, String qtnum, String stser, String rfcName, 
    		Dataset ds_dtl, Dataset ds_list_item, Dataset ds_list_bill, Dataset ds_list_txt) throws RfcException {
    	
        RfcFactory rfcFactory = getRfcFactory(sysid);
        Rfc        rfc        = rfcFactory.createRfc(rfcName);

        // �ش� ������ �¿� ���� �� ���� ó�� 
        
        logger.debug("cmd :"+cmd);
        logger.debug("vbeln :"+vbeln);
        logger.debug("qtnum :"+qtnum);
        logger.debug("stser :"+stser);

        // RFC  �Լ��� �Է°��� �����Ѵ�.
        // �Է� ���� ��� 1 : ImportParameter�� key = value �� �������� �ʵ� �� ����
        // DECIMAL-> type:2
        rfc.setImportValue("I_CMD"  , cmd);
        rfc.setImportValue("I_PSPID", vbeln);
        rfc.setImportValue("I_QTNUM", qtnum);
        rfc.setImportValue("I_QTSER", stser);
        
	     // �����÷��� ������ Ÿ��
	     // ColumnInfo.COLTYPE_INT:2
	     // ColumnInfo.COLTYPE_DECIMAL:4
	     // ColumnInfo.COLTYPE_STRING:1
	     // ColumnInfo.COLTYPE_DATE:8
	     // ColumnInfo.COLTYPE_CURRENCY:5
	     // ColumnInfo.COLTYPE_BLOB:9
        Dataset ds_list_item2 = new Dataset();
        if( ds_list_item != null ) {
            ds_list_item2.setId("ds_list_item2");
	        // sap�� ds Type�� �� �� ���⿡ �̰����� ���� ds�� ���� �� sap���� �ΰ�
	        for(int i = 0; i < rfc.getDatasetFromJcoTable("P_ITAB").getColumnCount(); i++) {
	        	// jco���� DECIMAL�� ������ ���Ͽ� DECIMAL�� ��� ������ String�� ����
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
            // sap�� ds Type�� �� �� ���⿡ �̰����� ���� ds�� ���� �� sap���� �ΰ�
            for(int i = 0; i < rfc.getDatasetFromJcoTable("B_ITAB").getColumnCount(); i++) {
            	// jco���� DECIMAL�� ������ ���Ͽ� DECIMAL�� ��� ������ String�� ����
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
        
        // sap rfc ����
        rfc.execute();

        return rfc;
    }
}

