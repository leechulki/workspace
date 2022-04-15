package hdel.sd.sso.service;


import hdel.lib.domain.MipResultVO;
import hdel.lib.service.SrmService;
import hdel.sd.sso.dao.Sso0056D;
import hdel.sd.sso.domain.ZSDT0004;
import hdel.sd.sso.domain.ZSDT0090;
import hdel.sd.sso.domain.ZSDT0091;
import hdel.sd.sso.domain.ZSDTHOGIMV;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tobesoft.platform.data.Dataset;

/*
 ******************************************************************************************
 * ��      ��   : ��ຯ�� ȣ����� ���� ���� ��
 * ��  ��  ��   :
 * �ۼ�  ����   : 2016.03.07
 * ���ID       : UF006
 * ----------------------------------------------------------------------------------------
 * HISTORY      :  2016.02.12 ���� �ڵ� �ڼ���
 ******************************************************************************************
*/

@Service
public class Sso0056S extends SrmService {
	Logger logger = Logger.getLogger(this.getClass());

    private Sso0056D sso0056D;
	
	@Override

	public void createDao(SqlSession sqlSession) {
		sso0056D = sqlSession.getMapper(Sso0056D.class);
	}
	
    //=========================================================================================
    //  �Լ����  : ��ຯ�� ȣ�� ���� �����缭 ���� ���� ��ȸ
    //  �Ķ����  : ȣ�⺯�渶����(ds_mv_ZSDT0090)
    //              ȣ�⺯�渮��Ʈ(ds_mv_ZSDT0091)
    //  ���ϰ�    :
    //  ���ID    : UF006
    //  ��������  : ȣ�� ���� ���� �ű�
    //=========================================================================================
	public void getHogiList(Dataset ds_mv_ZSDT0090, Dataset ds_mv_ZSDT0091, MipResultVO resultVO) throws Exception {
		ZSDT0004 zSDT0004 = new ZSDT0004();
		zSDT0004.setMANDT(ds_mv_ZSDT0090.getColumnAsString(0, "MANDT"));
		zSDT0004.setZZPJT_ID(ds_mv_ZSDT0090.getColumnAsString(0, "PSPID"));
		List<ZSDT0004> zSDT0004List = sso0056D.selectZSDT0004List(zSDT0004);
		
		// DB ���� ���� üũ
		ZSDT0090 zSDT0090 = new ZSDT0090();
		zSDT0090.setMANDT(ds_mv_ZSDT0090.getColumnAsString(0, "MANDT"));
		zSDT0090.setPSPID(ds_mv_ZSDT0090.getColumnAsString(0, "PSPID"));
		zSDT0090.setSEQ(ds_mv_ZSDT0090.getColumnAsString(0, "SEQ"));
		String maxSeq = sso0056D.selectZSDT0090MaxSeq(zSDT0090);
		if( maxSeq == null ) {
			ds_mv_ZSDT0090.setColumn(0, "DYN", "N");
		} else {
			ds_mv_ZSDT0090.setColumn(0, "DYN", "Y");
		}

		for(int i=0; i < zSDT0004List.size(); i++ ) {
			ZSDT0004 zsdt0004Item = zSDT0004List.get(i);
            for(int j=0; j < ds_mv_ZSDT0091.getRowCount(); j++) {
            	String hogi = ds_mv_ZSDT0091.getColumnAsString(j, "HOGI");
            	if( zsdt0004Item.getHOGI().equals(hogi) ) {
        			// �����缭�� �߼۵� ȣ�Ⱑ �ִ� ��� �ش� ȣ��� ������ ó�����ϰ� ���°�('N')�� �־��ش�.
        			if( !zsdt0004Item.getTP_DATE3().equals("00000000")) {
                		ds_mv_ZSDT0091.setColumn(j, "BEXEC", "N");
                		// �����缭�� �Ѱ��̶� �����ϴ� ��� ���°� ó��
                		ds_mv_ZSDT0090.setColumn(0, "BEXEC", "N");
        			}
            		break;
            	}
            }
		}
		
		resultVO.addDataset(ds_mv_ZSDT0090);
		resultVO.addDataset(ds_mv_ZSDT0091);
	}

    //=========================================================================================
    //  �Լ����  : ��ຯ�� ȣ�� ���� ���� ���� �� ���� ȣ�� ���� ���� ����
    //  �Ķ����  : ȣ�⺯�渶����(ds_mv_ZSDT0090)
    //              ȣ�⺯�渮��Ʈ(ds_mv_ZSDT0091)
    //  ���ϰ�    :
    //  ���ID    : UF006
    //  ��������  : ȣ�� ���� ���� �ű�
    //=========================================================================================
	public void saveMoveHogiData(Dataset ds_mv_ZSDT0090, Dataset ds_mv_ZSDT0091, MipResultVO resultVO, Dataset ds_error, String erNam) throws Exception {
        int qCnt = 0;
        int seq = new Integer(ds_mv_ZSDT0090.getColumnAsString(0, "SEQ")).intValue();
		ZSDT0090 zSDT0090 = new ZSDT0090();
		zSDT0090.setMANDT(ds_mv_ZSDT0090.getColumnAsString(0, "MANDT"));
		zSDT0090.setPSPID(ds_mv_ZSDT0090.getColumnAsString(0, "PSPID"));
		zSDT0090.setSEQ(ds_mv_ZSDT0090.getColumnAsString(0, "SEQ"));
		
		String maxSeq = sso0056D.selectZSDT0090MaxSeq(zSDT0090);
		// ȣ�� �������� ���濩�� �÷� �� �������� ��ư�� �Ӽ����� ���� �����Ѵ�.
		// ������ ���ذ��� �����Ѵ�.
//        ZSDTHOGIMV aGoSDTHOGIMV = new ZSDTHOGIMV();
//        aGoSDTHOGIMV.setMANDT(ds_mv_ZSDT0090.getColumnAsString(0, "MANDT"));
//        aGoSDTHOGIMV.setPSPID(ds_mv_ZSDT0090.getColumnAsString(0, "PSPID"));
//        aGoSDTHOGIMV.setSEQ("1");
//        aGoSDTHOGIMV.setERNAM(erNam);
//        aGoSDTHOGIMV.setAGOSEQ("0");

        ZSDTHOGIMV zSDTHOGIMV = new ZSDTHOGIMV();
        zSDTHOGIMV.setMANDT(ds_mv_ZSDT0090.getColumnAsString(0, "MANDT"));
        zSDTHOGIMV.setPSPID(ds_mv_ZSDT0090.getColumnAsString(0, "PSPID"));
        zSDTHOGIMV.setSEQ(ds_mv_ZSDT0090.getColumnAsString(0, "SEQ"));
        zSDTHOGIMV.setAGOSEQ(new Integer((seq-1)).toString());
        zSDTHOGIMV.setERNAM(erNam);
        
//        if( maxSeq == null && seq == 1 ) {
        if( maxSeq == null ) {
        	// �������� 1�� �̷��� ���� ���� �����Ϳ��� �����Ѵ�.
        	qCnt = sso0056D.insertFirsrMvZSDT0090(zSDTHOGIMV);
        	logger.debug("insertFirsrMvZSDT0090 cnt:"+qCnt);
    		// POSNR : 000000 ����ó��
        	zSDTHOGIMV.setPOSNR("000000");
        	zSDTHOGIMV.setMVPOSNR("000000");
        	qCnt = sso0056D.insertFirstMvZSDT0092(zSDTHOGIMV);
        	logger.debug("insertFirstMvZSDT0092 cnt:"+qCnt);
        	
        	zSDTHOGIMV.setMVHOGI(" ");
        	qCnt = sso0056D.insertFirstMvZSDT0093(zSDTHOGIMV);
        	logger.debug("insertFirstMvZSDT0093 cnt:"+qCnt);
        	
        	// �⺻ ��翡 ���ؼ��� 0�� �̷��� ������ ����
        	int i094Cnt = sso0056D.selectFirstMvOriginZSDT0094Cnt(zSDTHOGIMV);
        	if( i094Cnt == 0 ) {
        		qCnt = sso0056D.insertFirstMvOriginZSDT0094(zSDTHOGIMV);
            	logger.debug("insertFirstMvOriginZSDT0094 cnt:"+qCnt);
        	}
        } else {
            // ������ �ش� ������ ���� ȣ��, û����ȹ, ��� ������ ���ؼ� ������ �����Ѵ�.
            // deleteZSDT0091
        	// deleteZSDT0092
        	// deleteZSDT0093
        	// deleteZSDT0094
        	sso0056D.deleteZSDT0091(zSDT0090);
        	sso0056D.deleteZSDT0092(zSDT0090);
        	sso0056D.deleteZSDT0093(zSDT0090);
        	sso0056D.deleteZSDT0094(zSDT0090);
        	
        	zSDTHOGIMV.setPOSNR("000000");
        	zSDTHOGIMV.setMVPOSNR("000000");
        	qCnt = sso0056D.insertFirstMvZSDT0092(zSDTHOGIMV);
        	logger.debug("insertFirstMvZSDT0092 cnt:"+qCnt);
        	zSDTHOGIMV.setMVHOGI(" ");
        	qCnt = sso0056D.insertFirstMvZSDT0093(zSDTHOGIMV);
        	logger.debug("insertFirstMvZSDT0093 cnt:"+qCnt);
        }

        /* 
        else if( maxSeq == null && seq > 1 ) {
        	// �̷µ����Ϳ��� ���ο� ������ ���� �̷� �����͸� �����Ѵ�.
        	qCnt = sso0056D.insertMvZSDT0090(zSDTHOGIMV);
        	logger.debug("insertMvZSDT0090 cnt:"+qCnt);
    		// POSNR : 000000 ����ó��
        	zSDTHOGIMV.setPOSNR("000000");
        	qCnt = sso0056D.insertMvZSDT0092(zSDTHOGIMV);

        	/* �ش� ���� �߻��ϸ� �ȵ� - Ȥ�� ���� �ڵ��� ��
        	int i094Cnt = sso0056D.selectMvOriginZSDT0094Cnt(zSDTHOGIMV);
        	if( i094Cnt == 0 ) {
                zSDTHOGIMV.setSEQ(new Integer((seq-1)).toString());
                zSDTHOGIMV.setAGOSEQ(new Integer((seq-2)).toString());
        		qCnt = sso0056D.insertMvOriginZSDT0094(zSDTHOGIMV);
            	logger.debug("insertMvOriginZSDT0094 cnt:"+qCnt);
        	}
        }
       	*/

        // ȣ�⺰ ������� ó��
		// ���� ������ Ű���� �����Ѵ�.
		for(int i=0; i < ds_mv_ZSDT0091.getRowCount(); i++ ) {
			logger.debug("ds_mv_ZSDT0091 FLAG:"+ds_mv_ZSDT0091.getColumnAsString(i, "FLAG"));
			logger.debug("ds_mv_ZSDT0091 POSNR:"+ds_mv_ZSDT0091.getColumnAsString(i, "POSNR"));
		    logger.debug("ds_mv_ZSDT0091 HOGI:"+ds_mv_ZSDT0091.getColumnAsString(i, "HOGI"));
		    logger.debug("ds_mv_ZSDT0091 MVPOSNR:"+ds_mv_ZSDT0091.getColumnAsString(i, "MVPOSNR"));
		    logger.debug("ds_mv_ZSDT0091 MVHOGI:"+ds_mv_ZSDT0091.getColumnAsString(i, "MVHOGI"));
		    
		    
		    // �̷°��� �������� �ʴ� ���
//		    aGoSDTHOGIMV.setPOSNR(ds_mv_ZSDT0091.getColumnAsString(i, "POSNR"));
//		    aGoSDTHOGIMV.setHOGI(ds_mv_ZSDT0091.getColumnAsString(i, "HOGI"));
//		    aGoSDTHOGIMV.setMVHOGI(ds_mv_ZSDT0091.getColumnAsString(i, "MVHOGI"));

		    // �̷°��� �����ϴ� ���
		    zSDTHOGIMV.setPOSNR(ds_mv_ZSDT0091.getColumnAsString(i, "POSNR"));
		    zSDTHOGIMV.setMVPOSNR(ds_mv_ZSDT0091.getColumnAsString(i, "MVPOSNR"));

		    String tmpHogi = ds_mv_ZSDT0091.getColumnAsString(i, "HOGI");
		    String tmpMvHogi = ds_mv_ZSDT0091.getColumnAsString(i, "MVHOGI");
		    if( tmpHogi == null ) {
		    	tmpHogi = " ";
		    } else {
		    	if( "".equals(tmpHogi) ) {
			    	tmpHogi = " ";
		    	}
		    }

		    if( tmpMvHogi == null ) {
		    	tmpMvHogi = " ";
		    } else {
		    	if( "".equals(tmpMvHogi) ) {
		    		tmpMvHogi = " ";
		    	}
		    }
		    
		    zSDTHOGIMV.setHOGI(tmpHogi);
		    zSDTHOGIMV.setMVHOGI(tmpMvHogi);

	        //if( maxSeq == null ) {
	        // if( maxSeq == null && seq == 1 ) {
	        	// ���� ���ְ�ȹ �ڷῡ�� 1���� �̷��� �����Ѵ�.
        	qCnt = sso0056D.insertFirstMvZSDT0091(zSDTHOGIMV);
        	logger.debug("insertFirstMvZSDT0091 cnt:"+qCnt);
        	qCnt = sso0056D.insertFirstMvZSDT0092(zSDTHOGIMV);
        	logger.debug("insertFirstMvZSDT0092 cnt:"+qCnt);
        	qCnt = sso0056D.insertFirstMvZSDT0093(zSDTHOGIMV);
        	logger.debug("insertFirstMvZSDT0093 cnt:"+qCnt);
        	qCnt = sso0056D.insertFirstMvChangeZSDT0094(zSDTHOGIMV);
        	logger.debug("insertFirstMvChangeZSDT0094 cnt:"+qCnt);
        	
		    //if( "U".equals(ds_mv_ZSDT0091.getColumnAsString(i, "FLAG")) ) {
			qCnt = sso0056D.updateZSDT0091(zSDTHOGIMV);
			
			//}

	        //} else {
			/*    
			    if( "U".equals(ds_mv_ZSDT0091.getColumnAsString(i, "FLAG")) 
			    	 && "Y".equals(ds_mv_ZSDT0091.getColumnAsString(i, "MVFLAG")) ) {
			    	zSDTHOGIMV.setTMPPOSNR("999999");
			    	zSDTHOGIMV.setTMPPOSNR("999999999");
			    	
			    	// ���� �� Ű ���� 9999 ������ ����
			    	qCnt = sso0056D.updateTmpZSDT0091(zSDTHOGIMV); 
			    	
		        	// ���� Ű������ ���� ȣ�� Ű ����
			    	qCnt = sso0056D.updateOrgMvZSDT0091(zSDTHOGIMV);
			    	
			    	// ���� Ű ���� ���� ȣ�� Ű ����
			    	qCnt = sso0056D.updateTmpMvZSDT0091(zSDTHOGIMV);
			    	
		        	// û����ȹ Ű ����
		        	qCnt = sso0056D.updateTmpZSDT0093(zSDTHOGIMV);
		        	
		        	qCnt = sso0056D.updateOrgMvZSDT0093(zSDTHOGIMV);
		        	
		        	qCnt = sso0056D.updateTmpMvZSDT0093(zSDTHOGIMV);
		        	
		        	
		        	// ��系�� Ű ����
		        	qCnt = sso0056D.updateTmpZSDT0094(zSDTHOGIMV);
		        	
		        	qCnt = sso0056D.updateOrgMvZSDT0094(zSDTHOGIMV);
		        	
		        	qCnt = sso0056D.updateTmpMvZSDT0094(zSDTHOGIMV);
		        	
		        	qCnt = sso0056D.updateZSDT0091(zSDTHOGIMV);		        	
			    }
	        }
	        */
	        
	        /*
	            else if( maxSeq == null && seq > 1 ) {
	        	// �̷� �����Ϳ��� ���ο� �̷� ������ �����Ѵ�.
	        	qCnt = sso0056D.insertMvZSDT0091(zSDTHOGIMV);
	        	logger.debug("insertFirstMvZSDT0091 cnt:"+qCnt);
	        	qCnt = sso0056D.insertMvZSDT0092(zSDTHOGIMV);
	        	logger.debug("insertFirstMvZSDT0092 cnt:"+qCnt);
	        	qCnt = sso0056D.insertMvZSDT0093(zSDTHOGIMV);
	        	logger.debug("insertFirstMvZSDT0093 cnt:"+qCnt);
	        	qCnt = sso0056D.insertMvChangeZSDT0094(zSDTHOGIMV);
	        	logger.debug("insertFirstMvChangeZSDT0094 cnt:"+qCnt);
		    }
		    */
		}

		String BIGO5 = "";  //���� ȣ�� <L01> 147522L01
        String firstHOgi = "";
        int iMvCnt = 0;
		List<ZSDT0091> chkHogiList = sso0056D.selectZSDT0091ChangeList(zSDT0090);
		for(int i=0; i< chkHogiList.size(); i++) {
			ZSDT0091 zSDT0091 = (ZSDT0091)chkHogiList.get(i);
	    	String tmpBigo = "";
	    	String tmpHogi = zSDT0091.getHOGI();
	    	if( " ".equals(tmpHogi) ) {
	    		tmpBigo = tmpHogi;
	    	} else {
	    		if( tmpHogi.length() > 6 ) {
		    		tmpBigo = tmpHogi.substring(6);
	    		} else {
		    		tmpBigo = tmpHogi;
	    		}
	    	}

	    	if( iMvCnt == 0 ) {
	    		firstHOgi = tmpBigo;
	    		BIGO5 = BIGO5 + tmpBigo;
	    	} else {
	    		BIGO5 = BIGO5 + ","+ tmpBigo;
	    	}
	    	iMvCnt = iMvCnt + 1;
		}
		
		// ���ó��
		if( iMvCnt > 9 ) {
			BIGO5 = "���� ȣ�� "+firstHOgi+ " �� "+(iMvCnt-1)+"��";
		} else {
			BIGO5 = "���� ȣ�� <"+ BIGO5 +">";
		}
		
		zSDTHOGIMV.setBIGO5(BIGO5);
		qCnt = sso0056D.updateZSDT0090(zSDTHOGIMV);
	}
}
