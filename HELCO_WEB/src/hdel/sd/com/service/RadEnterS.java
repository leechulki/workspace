package hdel.sd.com.service;

import org.springframework.stereotype.Service;

import hdel.lib.exception.BizException;

import org.apache.log4j.Logger;

/**
 * ���𵨸��� ���Ա� ���嵵 ���� Ŭ����
 * 
 * @author  �ڼ���
 * @since 2021.01.26
 * @version 1.0
 * @see 
 * <pre>
 *  == �����̷�(Modification Information) ==
 *   
 *          ������          ������           ��������
 *  ----------------    ------------    ---------------------------
 *   2021.01.26         �ڼ���          ���� ����
 * 
 * </pre>
 */

@Service
public class RadEnterS {

	// �α׼���
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * Base Plate ���� ����, ���� ���
	 * Base Plate ���� ���� ����: ǥ�ñ⺰ ���� �����Ͱ� �����ؾ� �Ѵ�.
	 * Ȧ��ư(HPB, HIP) �������� ������ ����
	 * Ȧ���ϴ� �𵨺��� ����
	 * �ҹ潺��ġ�� ��� �� ����
	 * HPI�� �𵨺��� ����
	 * return Basee Plate ����� ����,����
	 * * @throws SQLException 
	 */
    public String[] getCalBasePlate(String suvWith, String suvHeight, String prdWith, String prdHeight
    		                        ,String ELR_E_IDTP, String ELC_E_BP_TYPE) {
        logger.debug("input suvWith:"+suvWith+", suvHeight:"+suvHeight+", prdWith:"+prdWith+", prdHeight:"+prdHeight);
    	// 0: ����, 1: ����
    	String[] rslt = new String[2];
        float calWith = new Float(suvWith).floatValue();
        float calHeight = new Float(suvHeight).floatValue();
        int[] chiPlate = getchoiceBasePlateAddValue(ELR_E_IDTP, ELC_E_BP_TYPE);
        int baseAddWith   = chiPlate[0]; // ���� bpase add ���� 
        int baseAddHeight = chiPlate[1]; // ���� bpase add ����
        int prdAddWith    = chiPlate[2]; // ��ǰ bpase add ����
        int prdAddHeight  = chiPlate[3]; // ��ǰ bpase add ����

        // ���𵨸� ����ġ ����, ���� ���̽� �÷���Ʈ �ݿø��� ����
        if("P".equals(ELC_E_BP_TYPE)) {
            calWith   = getPlateRoundCalWith(calWith, prdAddWith);
            calHeight = getPlateRoundCalHeight(calHeight, prdAddHeight);
        } else {
            calWith   = getPlateRoundCalWith(calWith, baseAddWith);
            calHeight = getPlateRoundCalHeight(calHeight, baseAddHeight);
        }
        
        int tmp = 0;
        // ���� ������ ����
        if(calWith%1 == 0) {
        	tmp = new Float(calWith).intValue();
        	rslt[0] = new Integer(tmp).toString(); 
        } else {
        	rslt[0] = new Float(calWith).toString();
        }
        
        // ���� ������ ����
        if(calHeight%1 == 0) {
        	tmp = new Float(calHeight).intValue();
        	rslt[1] = new Integer(tmp).toString(); 
        } else {
        	rslt[1] = new Float(calHeight).toString();
        }

        logger.debug("out:"+rslt);
        
        return rslt;
    }

	/**
	 * 
	 * Base Plate ���� ����, ���� ���
	 * Base Plate ���� ���� ����: ǥ�ñ⺰ ���� �����Ͱ� �����ؾ� �Ѵ�.
	 * Ȧ��ư(HPB, HIP) �������� ������ ����
	 * Ȧ���ϴ� �𵨺��� ����
	 * �ҹ潺��ġ�� ��� �� ����
	 * HPI�� �𵨺��� ����
	 * return Basee Plate ����� ����,����
	 * * @throws SQLException 
	 */
    public String[] getCalBasePlateAdd(String suvWith, String suvHeight, String prdWith, String prdHeight
    		                        ,String ELR_E_IDTP, String ELC_E_BP_TYPE) {
        logger.debug("input suvWith:"+suvWith+", suvHeight:"+suvHeight+", prdWith:"+prdWith+", prdHeight:"+prdHeight);
    	// 0: ����, 1: ����
    	String[] rslt = new String[2];
        float calWith = new Float(suvWith).floatValue();
        float calHeight = new Float(suvHeight).floatValue();
        float fPrdWith = new Float(prdWith).floatValue();
        float fPrdHeight = new Float(prdHeight).floatValue();
        
        int[] chiPlate = getchoiceBasePlateAddValue(ELR_E_IDTP, ELC_E_BP_TYPE);
        int baseAddWith   = chiPlate[0]; // ���� bpase add ���� 
        int baseAddHeight = chiPlate[1]; // ���� bpase add ����
        int prdAddWith    = chiPlate[2]; // ��ǰ bpase add ����
        int prdAddHeight  = chiPlate[3]; // ��ǰ bpase add ����

        // ���𵨸� ����ġ ����, ���� ���̽� �÷���Ʈ �ݿø��� ����
        calWith   = getPlateRoundCalWith(calWith, baseAddWith);
        calHeight = getPlateRoundCalHeight(calHeight, baseAddHeight);

        // ��ǰ ���̽��÷���Ʈ �߰��� ����� ����, ����
        fPrdWith = fPrdWith + prdAddWith;
        fPrdHeight = fPrdHeight + prdAddHeight;
        
        // ���̽��÷���Ʈ ���� ����, ��ǰ ���̽��÷���Ʈ ���� ���� ��
        if(fPrdWith > calWith) {
        	calWith = fPrdWith;
        }
        
        // ���̽��÷���Ʈ ���� ����, ��ǰ ���̽��÷���Ʈ ���� ���� ��
        if(fPrdHeight > calHeight) {
        	calHeight = fPrdHeight;
        }
        
        int tmp = 0;
        // ���� ������ ����
        if(calWith%1 == 0) {
        	tmp = new Float(calWith).intValue();
        	rslt[0] = new Integer(tmp).toString(); 
        } else {
        	rslt[0] = new Float(calWith).toString();
        }
        
        // ���� ������ ����
        if(calHeight%1 == 0) {
        	tmp = new Float(calHeight).intValue();
        	rslt[1] = new Integer(tmp).toString(); 
        } else {
        	rslt[1] = new Float(calHeight).toString();
        }
        
        return rslt;
    }
    
	/**
	 * 
	 * ��ŷŰ ���� Base Plate ���� ����, ���� ���
	 * ��ŷŰ ���� Base Plate ���� ���� ����: ǥ�ñ⺰ ���� �����Ͱ� �����ؾ� �Ѵ�.
	 * Ȧ��ư(HPB, HIP) �������� ������ ����
	 * Ȧ���ϴ� �𵨺��� ����
	 * �ҹ潺��ġ�� ��� �� ����
	 * HPI�� �𵨺��� ����
	 * return Basee Plate ����� ����,����
	 * * @throws SQLException 
	 */
    public String[] getCalPksBasePlate(String suvWith, String suvHeight, String suvF, String pksA, String pksB, String pksF, String prdWith, String prdHeight
    		                        ,String ELR_E_IDTP, String ELC_E_BP_TYPE) {
        logger.debug("input suvWith:"+suvWith+", suvHeight:"+suvHeight+", prdWith:"+prdWith+", prdHeight:"+prdHeight+", suvF:"+suvF+", pksA:"+pksA+", pksF:"+pksF);
        // 0: ����, 1: ����
    	String[] rslt = new String[2];
        float calWith = new Float(suvWith).floatValue();
        float calHeight = new Float(suvHeight).floatValue();
        float fSuvF =  new Float(suvF).floatValue();  // ��ǰ F��
        
        // ��ŷ Ű ������
        float fPksA = new Float(pksA).floatValue();
        float fPksB = new Float(pksB).floatValue();
        float fPksF = new Float(pksF).floatValue();

        int[] chiPlate = getchoiceBasePlateAddValue(ELR_E_IDTP, ELC_E_BP_TYPE);
        int baseAddWith   = chiPlate[0]; // ���� bpase add ���� 
        int baseAddHeight = chiPlate[1]; // ���� bpase add ����
        int prdAddWith    = chiPlate[2]; // ��ǰ bpase add ����
        int prdAddHeight  = chiPlate[3]; // ��ǰ bpase add ����
        
        // ��ŷŰ ����� ���ΰ�
        if(fPksA > calWith) {
        	calWith = fPksA;
        }
        
        //��ŷ Ű �� �Ʒ� ��ġ�� ���� ���ΰ� ���� ���� �����Ѵ�.
        if(fSuvF >= fPksF) {
        	// ��ŷŰ�� �Ʒ��� �ִ� ������ �����Ѵ�.
            calHeight = (fSuvF + calHeight) - fPksF;
        } else {
        	// ��ŷŰ�� ���� �ִ� ���� �����Ѵ�.
            calHeight = (fPksF + fPksB) - fSuvF;
        }

        if("P".equals(ELC_E_BP_TYPE)) {
            // ��ŷŰ�� ����� ���� ����, ���� ������� ���̽� �÷���Ʈ �ݿø� ����        
            calWith   = getPlateRoundCalWith(calWith, prdAddWith);
            calHeight = getPlateRoundCalHeight(calHeight, prdAddHeight);
        } else {
            // ��ŷŰ�� ����� ���� ����, ���� ������� ���̽� �÷���Ʈ �ݿø� ����        
            calWith   = getPlateRoundCalWith(calWith, baseAddWith);
            calHeight = getPlateRoundCalHeight(calHeight, baseAddHeight);
        }

        int tmp = 0;
        // ���� ������ ����
        if(calWith%1 == 0) {
        	tmp = new Float(calWith).intValue();
        	rslt[0] = new Integer(tmp).toString(); 
        } else {
        	rslt[0] = new Float(calWith).toString();
        }
        
        // ���� ������ ����
        if(calHeight%1 == 0) {
        	tmp = new Float(calHeight).intValue();
        	rslt[1] = new Integer(tmp).toString(); 
        } else {
        	rslt[1] = new Float(calHeight).toString();
        }
        logger.debug("out:"+rslt);
        return rslt;
    }

	/**
	 * 
	 * ��ŷŰ ���� Base Plate ���� ����, ���� ���
	 * ��ŷŰ ���� Base Plate ���� ���� ����: ǥ�ñ⺰ ���� �����Ͱ� �����ؾ� �Ѵ�.
	 * Ȧ��ư(HPB, HIP) �������� ������ ����
	 * Ȧ���ϴ� �𵨺��� ����
	 * �ҹ潺��ġ�� ��� �� ����
	 * HPI�� �𵨺��� ����
	 * return Basee Plate ����� ����,����
	 * * @throws SQLException 
	 */
    public String[] getCalPksBasePlateAdd(String suvWith, String suvHeight, String suvF, String pksA, String pksB, String pksF, String prdWith, String prdHeight
    		                        ,String ELR_E_IDTP, String ELC_E_BP_TYPE) {
        logger.debug("input suvWith:"+suvWith+", suvHeight:"+suvHeight+", prdWith:"+prdWith+", prdHeight:"+prdHeight+", suvF:"+suvF+", pksA:"+pksA+", pksF:"+pksF);
        // 0: ����, 1: ����
    	String[] rslt = new String[2];
        float calWith = new Float(suvWith).floatValue();
        float calHeight = new Float(suvHeight).floatValue();
        float fSuvF =  new Float(suvF).floatValue();  // ��ǰ F��
        float fPrdWith = new Float(prdWith).floatValue();
        float fPrdHeight = new Float(prdHeight).floatValue();
        
        // ��ŷ Ű ������
        float fPksA = new Float(pksA).floatValue();
        float fPksB = new Float(pksB).floatValue();
        float fPksF = new Float(pksF).floatValue();

        int[] chiPlate = getchoiceBasePlateAddValue(ELR_E_IDTP, ELC_E_BP_TYPE);
        int baseAddWith   = chiPlate[0]; // ���� bpase add ���� 
        int baseAddHeight = chiPlate[1]; // ���� bpase add ����
        int prdAddWith    = chiPlate[2]; // ��ǰ bpase add ����
        int prdAddHeight  = chiPlate[3]; // ��ǰ bpase add ����
        
        // ��ŷŰ ����� ���ΰ�
        if(fPksA > calWith) {
        	calWith = fPksA;
        }
        
        //��ŷ Ű �� �Ʒ� ��ġ�� ���� ���ΰ� ���� ���� �����Ѵ�.
        if(fSuvF >= fPksF) {
        	// ��ŷŰ�� �Ʒ��� �ִ� ������ �����Ѵ�.
            calHeight = (fSuvF + calHeight) - fPksF;
        } else {
        	// ��ŷŰ�� ���� �ִ� ���� �����Ѵ�.
            calHeight = (fPksF + fPksB) - fSuvF;
        }
        
        // ��ŷŰ�� ����� ���� ����, ���� ������� ���̽� �÷���Ʈ �ݿø� ����        
        calWith   = getPlateRoundCalWith(calWith, baseAddWith);
        calHeight = getPlateRoundCalHeight(calHeight, baseAddHeight);
        
        // ��ǰ ���̽��÷���Ʈ �߰��� ����� ����, ����
        fPrdWith = fPrdWith + prdAddWith;
        fPrdHeight = fPrdHeight + prdAddHeight;
        
        // ���̽��÷���Ʈ ���� ����, ��ǰ ���̽��÷���Ʈ ���� ���� ��
        if(fPrdWith > calWith) {
        	calWith = fPrdWith;
        }
        
        // ���̽��÷���Ʈ ���� ����, ��ǰ ���̽��÷���Ʈ ���� ���� ��
        if(fPrdHeight > calHeight) {
        	calHeight = fPrdHeight;
        }
        
        int tmp = 0;
        // ���� ������ ����
        if(calWith%1 == 0) {
        	tmp = new Float(calWith).intValue();
        	rslt[0] = new Integer(tmp).toString(); 
        } else {
        	rslt[0] = new Float(calWith).toString();
        }
        
        // ���� ������ ����
        if(calHeight%1 == 0) {
        	tmp = new Float(calHeight).intValue();
        	rslt[1] = new Integer(tmp).toString(); 
        } else {
        	rslt[1] = new Float(calHeight).toString();
        }
        
        return rslt;
    }
    
	/**
	 * 
	 * ����� Ȧ��ư ���� ���
	 * ������: EL_AUSE : HC(���),  EH(���,���), OH(����,���), BH(����,���), NH(����,���), HF(��ȭ,���)
	 * ���� = F + B/2
	 * return ����ο� ���� Ȧ��ư ����
	 * * @throws SQLException 
	 */
    public String getNudeCalHeight(String suvF, String suvB) {
        logger.debug("input suvF:"+suvF+", suvB:"+suvB);
    	float calHeight = 0;
    	String sCalHeight = "";
    	calHeight =  (new Float(suvF).floatValue()) + (new Float(suvB).floatValue())/2;
    	// ���� ������ ����
    	int tmp = 0;
        if(calHeight%1 == 0) {
        	tmp = new Float(calHeight).intValue();
        	sCalHeight = new Integer(tmp).toString(); 
        } else {
        	sCalHeight = new Float(calHeight).toString();
        }
        logger.debug("out sCalHeight:"+sCalHeight);
    	return sCalHeight;
    }

	/**
     * PLATE ���� Ÿ��: H	������ ���̽� �÷���Ʈ, V	������ ���̽� �÷���Ʈ, P	�÷���Ʈ ������ - ��ǰ������� ��ü, N	�÷���Ʈ ������
	 * ǥ�ñ� ���к� PLATE ADD ����, ���ο� ��ǰ ADD ����, ���� ���� �����Ͽ� �����Ѵ�.
	 * @param floate with
	 * @return chiPlate[0]: plate add ����, chiPlate[1]: plate add ����, chiPlate[2]: ��ǰ add ����, chiPlate[3]: ��ǰ add ����
	 */
    private int[] getchoiceBasePlateAddValue(String ELR_E_IDTP, String ELC_E_BP_TYPE) {
        logger.debug("input ELR_E_IDTP:"+ELR_E_IDTP+", ELC_E_BP_TYPE:"+ELC_E_BP_TYPE);
    	int[] chiPlate = new int[4];
    	/*
    	PLATE ���� Ÿ��                        : �÷���Ʈ �߰� ����: �÷���Ʈ �߰� ����: ��ǰ�߰� ����: ��ǰ�߰� ����
    	������ Ȧ��ư  HPB,HIP,�ҹ潺��ġ,HPI    10                  80                  10             60
    	������ Ȧ����                            20                  80                  10             60
    	������ HPI                               20                  80                  20             80
    	*/
        int[] type1 = { 10, 80, 10, 60};
        int[] type2 = { 20, 80, 10, 60};
        //int[] type3 = { 20, 80, 20, 80};
        int[] type3 = { 80, 20, 80, 20};
        // ELR_E_IDTP: ǥ�ñ� ���� HPB(�����ư), HIP(������ġǥ�ñ�), HPI(������ġǥ�ñ�), PKS	PARKING S/W, HLT	Ȧ����, FSW	�ҹ潺��ġ
        // ELC_E_BP_TYPE PLATE ���� Ÿ��: H	������ ���̽� �÷���Ʈ, V	������ ���̽� �÷���Ʈ, P	�÷���Ʈ ������ - ��ǰ������� ��ü, N	�÷���Ʈ ������
        if( "V".equals(ELC_E_BP_TYPE)) {
            if("HPB".equals(ELR_E_IDTP) || "HIP".equals(ELR_E_IDTP) || "PKS".equals(ELR_E_IDTP) || "HPI".equals(ELR_E_IDTP) || "HPBCIRCLE".equals(ELR_E_IDTP) || "FSW".equals(ELR_E_IDTP)) {
                logger.debug("out type1:"+type1);
            	return type1; 
            } else if("HPB".equals(ELR_E_IDTP)) {
                logger.debug("out type2:"+type1);
            	return type2;
            } else {
	            throw new BizException("��ǰ�԰� ���ص����͸� Ȯ���Ͻʽÿ�. ������� ����ڿ� �����Ͻʽÿ�.");
            }
        } else if( "H".equals(ELC_E_BP_TYPE)) {
        	if("HPI".equals(ELR_E_IDTP)) {
                logger.debug("out type3:"+type1);
            	return type3;
        	} else {
	            throw new BizException("��ǰ�԰� ���ص����͸� Ȯ���Ͻʽÿ�. ������� ����ڿ� �����Ͻʽÿ�.");
        	}
        }
    	return chiPlate;
    }
    
	/**
	 * 
	 * ���̽� ������Ʈ �ݿø� ����� with
	 * @param floate with
	 * @return floate with
	 */
    private float getPlateRoundCalWith(float with, int plateWith) {
    	float calWith = 0L;
    	String sWith = new Float(with).toString();
        String tmp1 = "";
        String tmp2 = "";
        int    index = sWith.lastIndexOf("."); 
    	if(with > 10) {
    		tmp1 = sWith.substring(index-1, index);
    		tmp2 = sWith.substring(0, index-1)+"0"+sWith.substring(index);
    		if("1".equals(tmp1)||"0".equals(tmp1)) {
    			tmp1 = "0";
    		} else if("2".equals(tmp1) || "3".equals(tmp1) || "4".equals(tmp1)|| "5".equals(tmp1)|| "6".equals(tmp1)) {
    			tmp1 = "5";
    		} else if("7".equals(tmp1) || "8".equals(tmp1) || "9".equals(tmp1)) {
    			tmp1 = "10";
    	    }
			calWith = new Float(tmp2).floatValue() + new Float(tmp1).floatValue() + plateWith;
        	return calWith;
    	} else {
    		return with + plateWith;
    	}
    }

	/**
	 * 
	 * ���̽� ������Ʈ �ݿø� ����� height
	 * @param String btnf, String btnb, String pswf
	 * @return floate height
	 */
    private float getPlateRoundCalHeight(float height, int plateHeight) {
    	float calHeight = 0L;
    	String sHeight = new Float(height).toString();
        String tmp1 = "";
        String tmp2 = "";
        int    index = sHeight.lastIndexOf("."); 
    	if(height > 10) {
    		tmp1 = sHeight.substring(index-1, index);
    		tmp2 = sHeight.substring(0, index-1)+"0"+sHeight.substring(index);
    		if("0".equals(tmp1)) {
    			tmp1 = "0";
    		} else {
    			tmp1 = "10";
    		}
			calHeight = new Float(tmp2).floatValue() + new Float(tmp1).floatValue() + plateHeight;
        	return calHeight;
    	} else {
    		return height + plateHeight;
    	}
    }

}
