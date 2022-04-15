package hdel.sd.ses.service;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.helco.xf.comm.CallSAP;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SapJCoService;
import tit.service.sapjco3.Rfc;
import tit.service.sapjco3.RfcException;
import tit.service.sapjco3.RfcFactory;
import webservice.stub.sap.SAPStub;
import webservice.stub.sap.ZWEB_CO4_COST_INSStub;
import hdel.sd.ses.domain.Ses0031ParamVO;

/*
 ******************************************************************************************
 * ��      ��   : ������ ��������(ses0031)
 * ��  ��  ��  : hsi
 * �ۼ�  ���� : 2016.02.25
 * ���ID  : UF008
 * ----------------------------------------------------------------------------------------
 * HISTORY : 2016.02.25 ���� �ڵ� hsi
 ******************************************************************************************
*/

@Service
public class Ses0031NJS extends SapJCoService {

	Logger logger = Logger.getLogger(this.getClass());

    //=========================================================================================
    //  SAP RFC ID: ZWEB_CO4_FIND_COST
    //  �Լ����  : 
    //  ���ϰ�    : Rfc rfc
    //  ���ID : UF008
    //  �������� : JCO RFC ȣ�� ����
    //=========================================================================================
    public Rfc callProjectDataSet(String sysid, String div, String gbn, String gjahr, String poper, int seq, String zdate, String chk, boolean grpCall, Dataset dsChar
    							 , Dataset dsCheck, Dataset ds200, Dataset ds202, Dataset ds204, Dataset ds206, Dataset ds300
    							 , Dataset ds302, Dataset ds304, Dataset ds306, Dataset dsMsg) throws RfcException {
    	
        String rfcName;
        if(grpCall == true)
        	rfcName = "ZWEB_CO4_FIND_COST_PP";
        else
        	rfcName = "ZWEB_CO4_FIND_COST";

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

        if("Y".equals(chk)) {
        	
        	for(int i = 0; i < dsChar.getRowCount(); i++) {
        		i = dsChar2.insertRow(i);
        		for(int j = 0; j < dsChar.getColumnCount(); j++) {
        			dsChar2.setColumn(i, dsChar.getColumnId(j), dsChar.getColumn(i, dsChar.getColumnId(j)));
        		}        		
        	}
        } else {
        	boolean found = false;
        	//dsChar2.insertRow(0);
			for(int i=0; i < dsChar.getRowCount(); i++) {
				found = false;
				for(int j=0; j < dsChar2.getRowCount(); j++) {
					found = dsChar.getColumnAsString(i, "QTSEQ").equals(dsChar2.getColumnAsString(j, "QTSEQ"));
					if(found) {
						break;
					}
				}
				if(!found) {
					int idx = dsChar2.appendRow();
					for(int j = 0; j < dsChar.getColumnCount(); j++) {
						dsChar2.setColumn(idx, dsChar.getColumnId(j), dsChar.getColumn(i, dsChar.getColumnId(j)));
					}
				}
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
    
    /**
     * ���Ϻ���� RFC
     * @param mipParam
     * @param sesParam
     * @return
     */
//    public BigDecimal callCostIns(MipParameterVO mipParam, Ses0031ParamVO sesParam) {
//		BigDecimal eIns = new BigDecimal(0);
//
//		ZWEB_CO4_COST_INSStub stub 
//    		= (ZWEB_CO4_COST_INSStub) new SAPStub(mipParam.getVariable("G_SYSID"), mipParam.getVariable("G_MANDT"), mipParam.getVariable("G_LANG"))
//    			.create(ZWEB_CO4_COST_INSStub.class);
//
//    	ZWEB_CO4_COST_INSStub.ZWEB_CO4_COST_INS wsParam;
//		wsParam = new ZWEB_CO4_COST_INSStub.ZWEB_CO4_COST_INS();
//		wsParam.setI_TSP(ZWEB_CO4_COST_INSStub.Curr152.Factory.fromString(sesParam.getZuam(), ""));
//		wsParam.setI_QTNUM(ZWEB_CO4_COST_INSStub.Char10.Factory.fromString(sesParam.getQtnum(), ""));
//		wsParam.setI_QTSER(new org.apache.axis2.databinding.types.UnsignedByte(sesParam.getQtser()));
//
//		ZWEB_CO4_COST_INSStub.ZWEB_CO4_COST_INSResponse response;
//		try {
//			response = stub.zWEB_CO4_COST_INS(wsParam);
//			if(response.getE_TYPE() == 0)
//				eIns = new BigDecimal(response.getE_INS().toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		
//		return eIns;
//    }
}
