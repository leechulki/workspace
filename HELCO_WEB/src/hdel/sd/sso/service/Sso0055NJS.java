package hdel.sd.sso.service;

import hdel.lib.service.SapJCoService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import tit.service.sapjco3.Rfc;
import tit.service.sapjco3.RfcException;
import tit.service.sapjco3.RfcFactory;

import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.Dataset;

/*
 ******************************************************************************************
 * ��      ��   : ��ຯ�� rfc ȣ���
 * ��  ��  ��  : hsi
 * �ۼ�  ���� : 2016.02.29
 * ���ID  : UF019
 * ----------------------------------------------------------------------------------------
 * HISTORY : 2016.02.25 ���� �ڵ� hsi
 ******************************************************************************************
*/

@Service
public class Sso0055NJS extends SapJCoService {

	Logger logger = Logger.getLogger(this.getClass());
    
	//=========================================================================================
    //  SAP RFC ID: ZWEB_SD_CHN_SO
    //  �Լ����  : 
    //  ���ϰ�    : Rfc rfc
    //  ���ID : UF019
    //  �������� : JCO RFC ȣ�� ����
    //=========================================================================================
    public Rfc callZwebSdChnSo(String sysid, String pspId, String cmd, String seq, String rfcName, Dataset ds_output_ZSDS0063) throws RfcException {

        RfcFactory rfcFactory = getRfcFactory(sysid);
        Rfc rfc = rfcFactory.createRfc(rfcName);

        // RFC  �Լ��� �Է°��� �����Ѵ�.
        // �Է� ���� ��� 1 : ImportParameter�� key = value �� �������� �ʵ� �� ����
        rfc.setImportValue("I_CMD", cmd);
        rfc.setImportValue("I_FR_SO", pspId);
        rfc.setImportValue("I_SEQ", seq);
        
        rfc.setJcoTableFromDataset(ds_output_ZSDS0063, "T_ITAB");
        rfc.execute();
       
        return rfc;
    }
    
    //=========================================================================================
    //  SAP RFC ID: ZWEB_CO4_FIND_COST
    //  �Լ���� : �������� 
    //  ���ϰ�    : Rfc rfc
    //  ���ID : UF008
    //  �������� : JCO RFC ȣ�� ����
    //=========================================================================================
    public Rfc callZwebCo4FindCost(String sysid, String div, String gbn, String gjahr, String poper, String seq, String zdate, String chk, Dataset dsChar
    							 , Dataset dsCheck, Dataset ds200, Dataset ds202, Dataset ds204, Dataset ds206, Dataset ds300
    							 , Dataset ds302, Dataset ds304, Dataset ds306, Dataset dsMsg) throws RfcException {
    	
        String rfcName = "ZWEB_CO4_FIND_COST";
        RfcFactory rfcFactory = getRfcFactory(sysid);
        Rfc rfc = rfcFactory.createRfc(rfcName);

        // RFC  �Լ��� �Է°��� �����Ѵ�.
        // �Է� ���� ��� 1 : ImportParameter�� key = value �� �������� �ʵ� �� ����
        rfc.setImportValue("I_DIV", div);
        rfc.setImportValue("I_GBN", gbn);
        rfc.setImportValue("I_GJAHR", gjahr);
        rfc.setImportValue("I_POPER", poper);
        rfc.setImportValue("I_SEQ", seq);
        rfc.setImportValue("I_ZDATE", zdate);
        //rfc.setImportValue("E_TYPE", "");
        
        Dataset dsChar2 = new Dataset();
		dsChar2.setId("dsChar2");
		
        // sap�� ds Type�� �� �� ���⿡ �̰����� ���� ds�� ���� �� sap���� �ΰ�
        for(int i = 0; i < rfc.getDatasetFromJcoTable("T_CHAR").getColumnCount(); i++) {
        	// jco���� DECIMAL�� ������ ���Ͽ� DECIMAL�� ��� ������ int�� ����
        	if(rfc.getDatasetFromJcoTable("T_CHAR").getColumnInfo(i).getColumnType() == (short) 4) {
        		CallSAP.makeDsCol(dsChar2, rfc.getDatasetFromJcoTable("T_CHAR").getColumnId(i), 
		                   rfc.getDatasetFromJcoTable("T_CHAR").getColumnInfo(i).getColumnSize(), 
		                   (short) 2);
        	} else {
        		CallSAP.makeDsCol(dsChar2, rfc.getDatasetFromJcoTable("T_CHAR").getColumnId(i), 
		                   rfc.getDatasetFromJcoTable("T_CHAR").getColumnInfo(i).getColumnSize(), 
		                   rfc.getDatasetFromJcoTable("T_CHAR").getColumnInfo(i).getColumnType());
        	}
        }
        
        // ��ຯ���� ���������� ui���� �ѰǾ� �Ѿ���Ƿ� 0��°�͸� ó���Ѵ�.
        if("ONE".equals(chk)) {
        	for(int i = 0; i < dsChar.getRowCount(); i++) {
        		i = dsChar2.insertRow(i);
        		for(int j = 0; j < dsChar.getColumnCount(); j++) {
        			dsChar2.setColumn(i, dsChar.getColumnId(j), dsChar.getColumn(i, dsChar.getColumnId(j)));
        		}        		
        	}
        	/*
        	dsChar2.insertRow(0);
    		for(int j = 0; j < dsChar.getColumnCount(); j++) {
    			dsChar2.setColumn(0, dsChar.getColumnId(j), dsChar.getColumn(0, dsChar.getColumnId(j)));
    		}
    		*/
        } else if("ALL".equals(chk)) {
        	dsChar2.insertRow(0);
    		for(int j = 0; j < dsChar.getColumnCount(); j++) {
    			dsChar2.setColumn(0, dsChar.getColumnId(j), dsChar.getColumn(0, dsChar.getColumnId(j)));
    		}
        }
		
        
        rfc.setJcoTableFromDataset(dsChar2, "T_CHAR");
        rfc.setJcoTableFromDataset(dsCheck, "T_CHEK");
        rfc.setJcoTableFromDataset(ds200, "T_T200");
        rfc.setJcoTableFromDataset(ds202, "T_T202");
        rfc.setJcoTableFromDataset(ds204, "T_T204");
        rfc.setJcoTableFromDataset(ds206, "T_T206");
        rfc.setJcoTableFromDataset(ds300, "T_T300");
        rfc.setJcoTableFromDataset(ds302, "T_T302");
        rfc.setJcoTableFromDataset(ds304, "T_T304");
        rfc.setJcoTableFromDataset(ds306, "T_T306");
        
        // sap rfc ����
        rfc.execute();
        
        // sap rfc ������ ���� ����� ���ؼ� �ش� Sap RFC�� ȣ���� ���� ������ �����Ѵ�.
        return rfc;
    }
    
}
