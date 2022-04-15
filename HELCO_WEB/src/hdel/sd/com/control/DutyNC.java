package hdel.sd.com.control;

import java.util.HashMap;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.RequireException;
import hdel.sd.com.domain.DutyObj;
import hdel.sd.com.service.DutyNS;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("dutyN")
public class DutyNC {

    @Autowired
    private SrmView view;

    @Autowired
    private SrmSqlSession sqlSession;

    @Autowired
    private DutyNS service;

    private Logger logger = Logger.getLogger(this.getClass());

    //=========================================================================================
    //  �Լ���� : ������-Duty Call(���� MO�� ó�� ����)
    //  �Ķ���� : ds_cond, ds_template1(��缭���� �׸��� ȭ�鰪)
    //  ���ϰ�   : dsTemplate1(��缭 �Է�üũ ���� üũ �� �⺻�������� ����� ��缭����)
    //            ,ds_error(��缭 ���� �� �ʼ����� ���� ��� �����޽��� ������)
    //  ���ID   : UF003
    //  �������� : ��� �������� �� ��� üũ ������ ó�� ���� ����
    //  HISTORY  : 2016.01.28 ���� �ڵ� �ڼ���
    //=========================================================================================
    @RequestMapping(value="genSpecDutySingleMo")
    public View genSpecDutySingleMo(MipParameterVO paramVO, Model model) {
        // ������ �� ȭ�鿡�� ���޵Ǵ� �Ķ����
        Dataset dsCond      = paramVO.getDatasetList().getDataset("ds_cond");
        Dataset dsTemplate1 = paramVO.getDatasetList().getDataset("ds_template1");

        // DB ���� ����, G_MANDT ���� ���� ����, ǰ��, � DB�� ���ӵȴ�.
        service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

        // ������ ��系�� N�� 1�� ������ ������ 1�� N���������� ��ȯ�Ͽ� HashMap�� �Է��Ѵ�.
        // SAPHEE.CABN ATNAM  -> SAPHEE.ZSDT1048 PRH
        HashMap<String, String> mapInput = new HashMap<String, String>();
        for (int i=0; i<dsTemplate1.getRowCount(); i++) {
            String value = dsTemplate1.getColumnAsString(i, "PRD");
            if (null != value && ! "".equals(value)) {
                mapInput.put(dsTemplate1.getColumnAsString(i, "PRH"), value);
            }
        }

        // ��� üũ �� �߻��� ���� �޽��� ������ 
        Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
        try
        {
            String gtype = "";
            String xx_atyp = "";

            xx_atyp = (String) mapInput.get("EL_ATYP");
            if ("WBSS".equals(xx_atyp)) {
                xx_atyp = "SSVF";
            }
            
            if ("GTLX".equals(xx_atyp) || "GTSS".equals(xx_atyp)) {
            	xx_atyp = "GSP";
            }
            
            if (xx_atyp != null && !"".equals(xx_atyp))    {
                gtype = (String) mapInput.get("EL_ATYP");
                if ("WBSS".equals(gtype)) {
                  gtype = "SSVF";
                }
                
                if ("GTLX".equals(gtype) || "GTSS".equals(gtype)) {
                	gtype = "GSP";
                }
                
            } else {
                // �����÷�����
                xx_atyp = (String) mapInput.get("ES_ATYP");
                if (xx_atyp != null && !"".equals(xx_atyp)) {
                	// �����÷����ʹ� Default�� ó��
                    gtype = "MLBT";    
                } else {
                    // ������ũ
                    xx_atyp = (String) mapInput.get("MW_ATYP");
                    if (xx_atyp != null && !"".equals(xx_atyp)) {
                    	// ������ũ�� Default�� ó��
                        gtype = "PMBTL";    
					} else {
						xx_atyp = (String) mapInput.get("AP_ATYP");
						if (xx_atyp != null && !"".equals(xx_atyp))	{
							gtype = "ELV";	// ������ Default�� ó��
						}						
                    }
                }
            }

            if (null == gtype || "".equals(gtype))     {
                if (!"ko".equals(paramVO.getVariable("G_LANG"))) {
                    throw new RequireException("TYPE");
                } else {
                    throw new RequireException("����");
                }
            }
        
            service.genSpecDutySingleMo(
            		               paramVO.getVariable("G_MANDT"), 
            		               gtype, 
            		               dsCond.getColumnAsString(0, "BLOCKGBN"), 
            		               mapInput,
            		               paramVO.getVariable("G_LANG"),
            		               dsCond.getColumnAsString(0, "ZFLG")
            		               );

        } catch (RuntimeException e) {
            e.printStackTrace();
            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
        }

        String prh      = "";
        String prhname  = "";
        String prd      = "";
        String atkla    = "";

        // ������ ��系�� üũ�� 1�� N�������� �����͸� N�� 1���� �������� �ڷ� ���¸� �����Ͽ�
        // dsTemplate1�� �ݿ��� �ش�.
        for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
            prh  = dsTemplate1.getColumnAsString(i, "PRH");
            prhname  = dsTemplate1.getColumnAsString(i, "PRHNAME");
            prd  = (String) mapInput.get(dsTemplate1.getColumnAsString(i, "PRH"));
            atkla = dsTemplate1.getColumnAsString(i, "ATKLA");

            if (prh  == null)  prh   = "";
            if (atkla == null) atkla = "";

            // ������ ��缭 ���� ���� PRH�� ���ؼ� �⺻�� �� ��缭 out�� ���ǵ� ��簪�� �Է��ϰ� ó���Ѵ�.
            if (null != prd && ! "".equals(prd)) {
                dsTemplate1.setColumn(i, "PRH"     , prh );
                dsTemplate1.setColumn(i, "PRHNAME" , prhname );
                // ��缭 PRH ���س����� ���ǰ� VALUE
                dsTemplate1.setColumn(i, "PRD"     , prd );
                dsTemplate1.setColumn(i, "ATKLA"   , atkla);
            }
        }

        MipResultVO resultVO = new MipResultVO();

        // ������ ȭ�鿡 DUTY-CALL ������� �����Ѵ�.
        resultVO.addDataset(dsTemplate1);
        resultVO.addDataset(ds_error);
        model.addAttribute("resultVO", resultVO);

        long lEndTime = System.currentTimeMillis();
        return view;
    }

    //=========================================================================================
    //  �Լ���� : ������-���Ӽ�(���� MO�� ó�� ����)
    //  �Ķ���� : ds_template1(��缭���� �׸��� ȭ�鰪)
    //  ���ϰ�   : dsTemplate1(��缭 �Է�üũ ���� üũ �� �⺻�������� ����� ��缭����)
    //            ,ds_error(��缭 ���� �� �ʼ����� ���� ��� �����޽��� ������)
    //  ���ID   : UF003
    //  �������� : ��� �������� �� ��� üũ ������ ó�� ���� ����
    //  HISTORY  : 2016.01.28 ���� �ڵ� �ڼ��� dependDutySingleMo
    //=========================================================================================
	@RequestMapping(value="dependDutySingleMo")
	public View dependDutySingleMo(MipParameterVO paramVO, Model model) {
        // ������ �� ȭ�鿡�� ���޵Ǵ� �Ķ����
		Dataset dsTemplate1 = paramVO.getDatasetList().getDataset("ds_template1");
		
        // DB ���� ����, G_MANDT ���� ���� ����, ǰ��, � DB�� ���ӵȴ�.
		service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

        // ������ ��系�� N�� 1�� ������ ������ 1�� N���������� ��ȯ�Ͽ� HashMap�� �Է��Ѵ�.
        // SAPHEE.CABN ATNAM  -> SAPHEE.ZSDT1048 PRH
		HashMap<String, String> mapInput = new HashMap<String, String>();
		// ORG ������� �ӽ� ����
		for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
			String value = dsTemplate1.getColumnAsString(i, "PRD");
			if (null != value && ! "".equals(value)) {
				mapInput.put(dsTemplate1.getColumnAsString(i, "PRH"), value);
			}
		}

		Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");// UI�� return�� �����޼��� dataset

		try {

			// ����(ǥ�����ø� ó�� ����)
			String sGtype    = (String) paramVO.getVariable("strGtype");
			String sZflg    = (String) paramVO.getVariable("strZflg");
			String sBlockGbn = "00";
			String gtype     = "";

			String sGubun = (String) mapInput.get("EL_ATYP");
			if (sGubun != null && !"".equals(sGubun))	{
				gtype = (String) mapInput.get("EL_ATYP");
			} else {
				// �����÷�����
				sGubun = (String) mapInput.get("ES_ATYP");
				if (sGubun != null && !"".equals(sGubun))	{
					gtype = "MLBT";	// �����÷����ʹ� Default�� ó��
				}else {
					// ������ũ
					sGubun = (String) mapInput.get("MW_ATYP");
					if (sGubun != null && !"".equals(sGubun))	{
						gtype = "PMBTL";	// ������ũ�� Default�� ó��
					}
				}
			}

			if (null == gtype || "".equals(gtype))	 {
				if (!"ko".equals(paramVO.getVariable("G_LANG")))		{
					throw new RequireException("TYPE");
				}else	{
					throw new RequireException("����");
				}
			}
			
            service.dependDutySingleMo(paramVO.getVariable("G_MANDT"), sGtype, sBlockGbn, mapInput, paramVO.getVariable("G_LANG"), sZflg);
			
		} catch (RuntimeException e) { 
			e.printStackTrace();
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", e.getMessage(), "Y", "Y");
		}

		// ��ǰ���п� �ص��ϴ� ������ �������� �ʴ� ��� �� Template ���� return.
		if (mapInput != null)	{
			String prh  = "";
			String prhname  = "";
			String prd  = "";
			String atkla = "";
			
			for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
				prh      = dsTemplate1.getColumnAsString(i, "PRH");
				prhname  = dsTemplate1.getColumnAsString(i, "PRHNAME");
				prd      = (String) mapInput.get(dsTemplate1.getColumnAsString(i, "PRH"));
				atkla    = dsTemplate1.getColumnAsString(i, "ATKLA");
	
				if (prh   == null) prh   = "";
				if (atkla == null) atkla = "";
					
				if (null != prd && ! "".equals(prd)) {
					dsTemplate1.setColumn(i, "PRH",     prh );
					dsTemplate1.setColumn(i, "PRHNAME", prhname );
					dsTemplate1.setColumn(i, "PRD",     prd );
					dsTemplate1.setColumn(i, "ATKLA",   atkla);
				}
			}
		}

		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(dsTemplate1);
		ds_error.setId("ds_error");
		resultVO.addDataset(ds_error);
		model.addAttribute("resultVO", resultVO);

        long lEndTime = System.currentTimeMillis();

		return view;
	}
	
    //=========================================================================================
    //  �Լ���� : ������-�������� ���Ӽ� üũ(Single MO�� ó�� ����)
	//             ������ �������� ó�� �� �Էµ� ���� ���� ������ üũ�� �����Ѵ�.
	//             ���Ӽ� üũ ���� �� ���� ���� üũ �߻� �� �޽����� ���� üũ ������ �����Ѵ�.
    //  �Ķ���� : ds_template1(��缭���� �׸��� ȭ�鰪)
    //  ���ϰ�   : dsTemplate1(��缭 �Է�üũ ���� üũ �� �⺻�������� ����� ��缭����)
    //            ,ds_error(��缭 ���� �� �ʼ����� ���� ��� �����޽��� ������)
    //  ���ID   : UF003
    //  �������� : ��� �������� �� ��� üũ ������ ó�� ���� ����
    //  HISTORY  : 2016.01.28 ���� �ڵ� �ڼ��� dependDutySingleMo
    //=========================================================================================
	@RequestMapping(value="getQCostDutySingleMo")
	public View getQCostDutySingleMo(MipParameterVO paramVO, Model model) {
        // ������ �� ȭ�鿡�� ���޵Ǵ� �Ķ����
//        String  item = paramVO.getVariable("QTSEQ"); // QTSEQ��ȣ
//        String  zflg = paramVO.getVariable("ZFLG"); // ������������
//        Dataset ds_mospec = paramVO.getDatasetList().getDataset("ds_mospec");
//        int     cnt   = 0;
//        Dataset ds_log = new Dataset("ds_log");
//        
//        // DB ���� ����, G_MANDT ���� ���� ����, ǰ��, � DB�� ���ӵȴ�.
//        service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
//
//        // ������ ��系�� N�� 1�� ������ ������ 1�� N���������� ��ȯ�Ͽ� HashMap�� �Է��Ѵ�.
//        // SAPHEE.CABN ATNAM  -> SAPHEE.ZSDT1048 PRH
//        HashMap<String, String> mapInput = new HashMap<String, String>();
//        for (int i=0; i<ds_mospec.getRowCount(); i++) {
//            String value = ds_mospec.getColumnAsString(i, "PRD");
//            if (null != value && ! "".equals(value)) {
//                mapInput.put(ds_mospec.getColumnAsString(i, "PRH"), value);
//            }
//        }
//
//        // ��� üũ �� �߻��� ���� �޽��� ������ 
//        Dataset ds_error = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
//        try
//        {
//            String gtype = "";
//            String sGubun = "";
//            boolean bgenExe = true;
//
//    		// 2013.01.29 Duty �� Ÿ�� ���� �߰�
//    		String sGubun1 = (String) mapInput.get("EL_DITEMUSE");
//    		String sGubun2 = (String) mapInput.get("EL_ASPLY");
//    		//String sGubun3 =  template.getColumnAsString(0, "PSPID");
//    		if ( sGubun1 == null ) sGubun1 = "";
//    		if ( sGubun2 == null ) sGubun2 = "";
//    		
//    		if ( sGubun1.equals("Y") || sGubun2.equals("Y") ) {
//    			//return true;
//    			bgenExe = false; 
//    		}
//
//    		//if (sGubun3.equals("126394")){ //20131030 kt û������ ��ǥ������ �ʼ�üũ ���� -�輱ȣ
//    		//	return true;
//    		//}
//
//    		sGubun = (String) mapInput.get("EL_ATYP");
//    		if (sGubun != null && !"".equals(sGubun))	{
//    			gtype = "ELV1";
//    			// ���ڿ� ó��
//    			if ("STSH1".equals(sGubun) || "STSH5".equals(sGubun))	{
//    				gtype = "ELV2";
//    			}
//    		}else {
//    			// �����÷�����
//    			sGubun = (String) mapInput.get("ES_ATYP");
//    			if (sGubun != null && !"".equals(sGubun))	{
//    				gtype = "ESC1";	// �����÷����ʹ� Default�� ó��
//    			}else {
//    				// ������ũ
//    				sGubun = (String) mapInput.get("MW_ATYP");
//    				if (sGubun != null && !"".equals(sGubun))	{
//    					gtype = "MWK1";	// ������ũ�� Default�� ó��
//    				} else {	// 2013.01.03 �̿ܿ��� �ʼ� üũ ���ʿ�
//    					bgenExe = false;
//    				}
//    			}
//    		}
//
//    		if (null == gtype || "".equals(gtype))	 {
//    			if (!"ko".equals(paramVO.getVariable("G_LANG"))) {
//    				throw new RequireException("TYPE");
//    			}else	{
//    				throw new RequireException("����");
//    			}
//    		}
//
//    		if( bgenExe ) {
//    			DutyObj dutyObj = new DutyObj();
//                service.genCostDutySingleMo(
//                		                   paramVO.getVariable("G_MANDT"), 
//                		                   gtype, 
//                		                   mapInput,
//                		                   ds_log,
//                		                   paramVO.getVariable("G_LANG"),
//                		                   item,
//                		                   cnt,
//                		                   dutyObj,
//                		                   zflg
//                		                   );
//    		}
//        } catch (RuntimeException e) {
//            ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, "CE00001", "MO [" + item + "] " + e.getMessage(), "Y", "Y");
//        }
//
//		MipResultVO resultVO = new MipResultVO();
//		ds_error.setId("ds_error");
//		resultVO.addDataset(ds_error);
//		model.addAttribute("resultVO", resultVO);
//
//        long lEndTime = System.currentTimeMillis();
		return view;
	}
}

