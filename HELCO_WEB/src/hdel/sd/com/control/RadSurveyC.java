package hdel.sd.com.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.FloorNmVO;
import hdel.sd.com.domain.SuvFloorVO;
import hdel.sd.com.domain.SuvPrhVO;
import hdel.sd.com.service.DutyNS;
import hdel.sd.com.service.RadSurveyS;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.comm.DatasetUtil;
import com.tobesoft.platform.data.Dataset;

/**
 * ���𵨸��� �������� ���� ��Ʈ��
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

@Controller
@RequestMapping("radSurveyC")
public class RadSurveyC {

	// �α׼���
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SrmView view;

	@Autowired
	private RadSurveyS service;

	@Autowired 
	private ResourceLoader resourceLoader;

    @Autowired
    private DutyNS dutyNS;

    @Autowired
    private SrmSqlSession sqlSession;
    
	/** 
	 * ������ ���𵨸� ������� ��ȸ
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findEstSuvList")
	public View findEstSuvList(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsSuvPrh = paramVO.getDataset("ds_suv_prh");
		String gSysid  = paramVO.getVariable("G_SYSID");
		try {
			Dataset ds_cond = paramVO.getDataset("ds_cond");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String MANDT  = paramVO.getVariable("G_MANDT");
			String QTNUM  = ds_cond.getColumnAsString(0, "QTNUM");
			String QTSER  = ds_cond.getColumnAsString(0, "QTSER");
			String QTSEQ  = ds_cond.getColumnAsString(0, "QTSEQ");
			paramMap.put("MANDT", MANDT);
			paramMap.put("QTNUM", QTNUM);
			paramMap.put("QTSER", QTSER);
			paramMap.put("QTSEQ", QTSEQ);

			service.setSqlSessionBySysid(gSysid);
			// ������� ���� ��ȸ
			Map<String, Object> suvtMap = service.selectEstSuvYsno(paramMap);
			
			if(suvtMap != null) {
				String SUVST = (String)suvtMap.get("SUVST");
				String SUVSN = (String)suvtMap.get("SUVSN");
				resultVO.addVariable("SUVSN", SUVSN);
				resultVO.addVariable("SUVYSNO", SUVST);

				// ��ϵ� ������ �����ϸ� ��ȸ�Ѵ�.
				if("Y".equals(SUVST)) {
					paramMap.put("SUVSN", SUVSN);
					service.setSqlSessionBySysid(gSysid);
					List<SuvPrhVO> suvPrhList = service.selectSuvPrhList(paramMap);
					if(suvPrhList.size() > 0) {
						DatasetUtil.moveListToDs(suvPrhList, dsSuvPrh);
						resultVO.addDataset(dsSuvPrh); 			
					}
				}
			} else {
				resultVO.addVariable("SUVYSNO", "N");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError);
		} finally {
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}

	/** 
	 * ���𵨸� ������� ����
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="specFloorOut")
	public View specFloorOut(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError         = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		Dataset dsTemplateFloor = paramVO.getDataset("ds_template_floor");
		Dataset dsFloorSuvPrh = paramVO.getDataset("ds_floorSuvPrh");
		Dataset dsSuvPrh = paramVO.getDataset("ds_suv_prh");
		
		String SUVSN   = paramVO.getVariable("SUVSN");
		String gSysid  = paramVO.getVariable("G_SYSID");
	    try {
	    	// ������� �Է°�
			HashMap<String, String> prhMap = new HashMap<String, String>();
			for (int i = 0; i < dsTemplateFloor.getRowCount(); i++) {
				String value = dsTemplateFloor.getColumnAsString(i, "PRD");
				if (null != value && ! "".equals(value)) {
					prhMap.put(dsTemplateFloor.getColumnAsString(i, "PRH"), value);
				}
			}

			// ������� ����Ʈ�� ��ȸ�Ѵ�.
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String MANDT  = paramVO.getVariable("G_MANDT");
			paramMap.put("MANDT", MANDT);
			paramMap.put("SUVSN", SUVSN);
			service.setSqlSessionBySysid(gSysid);
			List<SuvFloorVO> suvFloorList = service.selectSuvFloorList(paramMap);
			List<Map> suvJambList = service.selectElrEPnlhList(paramMap);

			// ������� ���� ����
			Map<String, FloorNmVO> floorPrhMap = new LinkedHashMap<String, FloorNmVO>();
			// JAMB HIP �������� �Ϸ�
			
			int startIndex = 0;
			int stopIndex = 0;
			for (int i = 0; i < dsSuvPrh.getRowCount(); i++) {
				String suvprh  = dsSuvPrh.getColumnAsString(i, "SUVPRH");
				String suvprd  = dsSuvPrh.getColumnAsString(i, "SUVPRD");
				String modyprd  = dsSuvPrh.getColumnAsString(i, "MODYPRD");
				
				if(suvprd == null) suvprd = "";
				if(modyprd ==null) modyprd = "";
				
				if(!"".equals(modyprd)) {
					suvprd = modyprd; 
				}
				
				if (suvprh != null) {
					if(suvprh.indexOf("ELR_C_EF") > -1) {
						if(!"".equals(suvprd)) {
							startIndex = i;
							break;
						}
					}
				}
			}
			
			for (int i = (dsSuvPrh.getRowCount()-1); i >= 0; i--) {
				String suvprh  = dsSuvPrh.getColumnAsString(i, "SUVPRH");
				String suvprd  = dsSuvPrh.getColumnAsString(i, "SUVPRD");
				String modyprd  = dsSuvPrh.getColumnAsString(i, "MODYPRD");
				
				if(suvprd == null) suvprd = "";
				if(modyprd ==null) modyprd = "";
				
				if(!"".equals(modyprd)) {
					suvprd = modyprd; 
				}
				
				if (suvprh != null) {
					if(suvprh.indexOf("ELR_C_EF") > -1) {
						if(!"".equals(suvprd)) {
							stopIndex = i;
							break;
						}
					}
				}
			}

			// ������ �Էµ� ���� ������ ����			
			// �������� �����͸� �����Ѵ�.
	    	// ������� �߿� ���� �����͸� �����Ѵ�.
			Map<Integer, String> floorSuvMap = new LinkedHashMap<Integer, String>();
			for (int i = stopIndex; i >= startIndex; i--) {
				String suvprh = dsSuvPrh.getColumnAsString(i, "SUVPRH");
				String suvprhname = dsSuvPrh.getColumnAsString(i, "SUVPRHNAME");
				String suvprd  = dsSuvPrh.getColumnAsString(i, "SUVPRD");
				String modyprd  = dsSuvPrh.getColumnAsString(i, "MODYPRD");
				
				if(suvprd == null) suvprd = "";
				if(modyprd ==null) modyprd = "";
				
				if(!"".equals(modyprd)) {
					suvprd = modyprd; 
				}
				
				suvprhname = suvprhname.replace("��", "");
				suvprhname = suvprhname.replace("B", "-");
				suvprhname = suvprhname.trim();
				int floorNum = new Integer(suvprhname).intValue();
				String sFloor = "";
				if(floorNum>=0) {
					sFloor = new Integer(floorNum).toString();
					floorNum = floorNum -1;
				} else {
					sFloor = new Integer(floorNum).toString();
					sFloor = sFloor.replace("-", "B");
				}

				if(!"".equals(suvprd)) {
					sFloor = sFloor + ","+"Y,"+suvprh+", "+suvprd;
				} else {
					sFloor = sFloor + ","+"N,"+suvprh+", "+suvprd;
				}
				floorSuvMap.put(floorNum, sFloor);
			}

			List<String> errMsgList = service.getFloorSpreadOut(SUVSN, prhMap, floorPrhMap, suvFloorList, suvJambList, floorSuvMap);
			// ������� ���� �Ϸᰡ �� ������ ó��
			List<FloorNmVO> floorPrhList = new ArrayList<FloorNmVO>(floorPrhMap.values());
			if(floorPrhList.size() > 0) {
				DatasetUtil.moveListToDs(floorPrhList, dsFloorSuvPrh);
				resultVO.addDataset(dsFloorSuvPrh); 			
			}

			// �Ķ���ͷ� ������� ���� ��� �÷��׸� �����Ѵ�.
			if(errMsgList.size() > 0) {
				resultVO.addVariable("FLOORYSNO", "N");
				// �ش� �޽����� ù��° �����޽����� �����Ѵ�.
				dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", errMsgList.get(0), "Y", "Y");
				dsError.setId("ds_error");
				resultVO.addDataset(dsError);
			} else {
				resultVO.addVariable("FLOORYSNO", "Y");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// �Ķ���ͷ� ������� ���� ��� �÷��׸� �����Ѵ�.
			resultVO.addVariable("FLOORYSNO", "N");
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError);
		} finally {
			model.addAttribute("resultVO", resultVO);
		}

	    return view;
	}

	/** 
	 * ���𵨸� ������� ���� ����
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="applyFloorOut")
	public View applyFloorOut(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		// �뵵����
		String elAuse = paramVO.getVariable("EL_AUSE");
		//  �������Է�
		String  elEmf = paramVO.getVariable("EL_EMF");
		
		String  dpk = paramVO.getVariable("EL_DPK");
		
		Dataset dsFloorSuvPrh = paramVO.getDataset("ds_floorSuvPrh");
		Dataset dsSuvPrh = paramVO.getDataset("ds_suv_prh");
		Dataset dsFloor = paramVO.getDataset("ds_floor");
		
		String SUVSN   = paramVO.getVariable("SUVSN");
		String gMandt  = paramVO.getVariable("G_MANDT");
		String gSysid  = paramVO.getVariable("G_SYSID");
		//String gUserId = paramVO.getVariable("G_USER_ID");
		String gUserName = paramVO.getVariable("G_USER_NAME");
		
		// �뵵
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String MANDT  = paramVO.getVariable("G_MANDT");
			paramMap.put("MANDT", MANDT);
			paramMap.put("SUVSN", SUVSN);
			paramMap.put("G_USER_NAME", gUserName);
			paramMap.put("EL_AUSE", elAuse);
			paramMap.put("EL_EMF", elEmf);
			
			List<FloorNmVO> floorPrhList = new ArrayList<FloorNmVO>();
			for(int i=0; i < dsFloorSuvPrh.getRowCount(); i++) {
				FloorNmVO floorNmVO = new FloorNmVO();
				floorNmVO.setMANDT(gMandt);
				floorNmVO.setSUVSN(SUVSN);
				floorNmVO.setFLOOR (dsFloorSuvPrh.getColumnAsString(i, "FLOOR"));
				floorNmVO.setFLOORNUM(dsFloorSuvPrh.getColumnAsInteger(i, "FLOORNUM"));
				floorNmVO.setFLOORNM(dsFloorSuvPrh.getColumnAsString(i, "FLOORNM"));
				floorNmVO.setJAMBGRP(dsFloorSuvPrh.getColumnAsString(i, "JAMBGRP"));
				floorNmVO.setJAMBMODEL(dsFloorSuvPrh.getColumnAsString(i, "JAMBMODEL"));
				floorNmVO.setHPIMODEL(dsFloorSuvPrh.getColumnAsString(i, "HPIMODEL"));
				floorNmVO.setHPBMODEL(dsFloorSuvPrh.getColumnAsString(i, "HPBMODEL"));
				floorNmVO.setHIPMODEL(dsFloorSuvPrh.getColumnAsString(i, "HIPMODEL"));
				floorNmVO.setHLTNMODEL(dsFloorSuvPrh.getColumnAsString(i, "HLTNMODEL"));
				floorNmVO.setFSWMODEL(dsFloorSuvPrh.getColumnAsString(i, "FSWMODEL"));
				floorNmVO.setHPIPRD(dsFloorSuvPrh.getColumnAsString(i, "HPIPRD"));
				floorNmVO.setHPBPRD(dsFloorSuvPrh.getColumnAsString(i, "HPBPRD"));
				floorNmVO.setHIPPRD(dsFloorSuvPrh.getColumnAsString(i, "HIPPRD"));
				floorNmVO.setHLTNPRD(dsFloorSuvPrh.getColumnAsString(i, "HLTNPRD"));
				floorNmVO.setPRKSWPRD(dsFloorSuvPrh.getColumnAsString(i, "PRKSWPRD"));
				floorNmVO.setFSWPRD(dsFloorSuvPrh.getColumnAsString(i, "FSWPRD"));
				floorNmVO.setHPIAPPLYFLOOR(dsFloorSuvPrh.getColumnAsString(i, "HPIAPPLYFLOOR"));
				floorNmVO.setHPBAPPLYFLOOR(dsFloorSuvPrh.getColumnAsString(i, "HPBAPPLYFLOOR"));
				floorNmVO.setHIPAPPLYFLOOR(dsFloorSuvPrh.getColumnAsString(i, "HIPAPPLYFLOOR"));
				floorNmVO.setHLTNAPPLYFLOOR(dsFloorSuvPrh.getColumnAsString(i, "HLTNAPPLYFLOOR"));
				floorNmVO.setPRKSWAPPLYFLOOR(dsFloorSuvPrh.getColumnAsString(i, "PRKSWAPPLYFLOOR"));
				floorNmVO.setFSWAPPLYFLOOR(dsFloorSuvPrh.getColumnAsString(i, "FSWAPPLYFLOOR"));
				floorNmVO.setJAMBINFO(dsFloorSuvPrh.getColumnAsString(i, "JAMBINFO"));				
				floorNmVO.setAENAM(gUserName);
				floorNmVO.setERNAM(gUserName);
				
				floorNmVO.setSUVPRH(dsFloorSuvPrh.getColumnAsString(i, "SUVPRH"));
				floorNmVO.setSUVPRD(dsFloorSuvPrh.getColumnAsString(i, "SUVPRD"));
				floorPrhList.add(floorNmVO);
			}

	    	// ������� �Է°�
			HashMap<String, String> suvPrhMap = new LinkedHashMap<String, String>();
			for (int i = 0; i < dsSuvPrh.getRowCount(); i++) {
				String suvprh  = dsSuvPrh.getColumnAsString(i, "SUVPRH");
				String suvprd  = dsSuvPrh.getColumnAsString(i, "SUVPRD");
				String modyprd = dsSuvPrh.getColumnAsString(i, "MODYPRD");
				
				if(suvprd == null) suvprd = "";
				if(modyprd ==null) modyprd = "";
				if(!"".equals(modyprd)) {
					suvprd = modyprd;
				}
				
				if (null !=  suvprh) {
					// ���� �����͸� �Է��Ѵ�.
					if(suvprh.indexOf("ELR_C_EF") > -1 || suvprh.equals("ELR_C_EHP") || suvprh.equals("ELR_H_HH")) {
						suvPrhMap.put(suvprh, suvprd);
					}

					// ELR_H_BTNP - Ȧ��ư ��ġ������ ���� ��� ó��
					if(suvprh.equals("ELR_H_BTNP")) {
						suvPrhMap.put(suvprh, suvprd);
					}
				}
			}
			suvPrhMap.put("EL_DPK", dpk);
			
	        // ���Ա� ���嵵 ���� ������
			Map<String, Object> outMap = new LinkedHashMap<String, Object>();
			service.setSqlSessionBySysid(gSysid);
			service.saveFloorOut(paramMap, floorPrhList, suvPrhMap, outMap);

			// ������������� ����� DB�� ����Ѵ�.
			int addRow = -1;
			for( String key : outMap.keySet() ) {
				addRow = dsFloor.appendRow();
				dsFloor.setColumn(addRow, "SUVPRH", key);
				dsFloor.setColumn(addRow, "CALPRD", (String)outMap.get(key));
			}
			resultVO.addDataset(dsFloor);

		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError);
		} finally {
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}

	/** 
	 * ���𵨸� ������ ��� ����
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="radCalApply")
	public View radCalApply(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		Dataset dsSuvPrh = paramVO.getDataset("ds_suv_prh");
		
		String SUVSN   = paramVO.getVariable("SUVSN");
		String gMandt  = paramVO.getVariable("G_MANDT");
		String gSysid  = paramVO.getVariable("G_SYSID");
		//String gUserId = paramVO.getVariable("G_USER_ID");
		String gUserName = paramVO.getVariable("G_USER_NAME");
		
		// �뵵
		//System.out.println("dsSuvPrh:"+dsSuvPrh);
		try {
			String flag = null;
			String modPrd = null;
			String callPrd = null;
			String suvPrh = null;
			String suvType = null;
			List<Map> calList = new ArrayList<Map>();
			List<Map> modyList = new ArrayList<Map>();
			for(int i=0; i < dsSuvPrh.getRowCount(); i++) {
                Map<String, Object> inMap = new HashMap<String, Object>();
                inMap.put("MANDT", gMandt);
                inMap.put("SUVSN", SUVSN);
                inMap.put("USER_NAME", gUserName);
                flag = dsSuvPrh.getColumnAsString(i, "FLAG");
                if("U".equals(flag)) {
                	suvPrh = dsSuvPrh.getColumnAsString(i, "SUVPRH");
                    callPrd = dsSuvPrh.getColumnAsString(i, "CALPRD");
                    modPrd = dsSuvPrh.getColumnAsString(i, "MODYPRD");
                    suvType = dsSuvPrh.getColumnAsString(i, "SUVTYPE");
                    inMap.put("SUVPRH", suvPrh);
                    inMap.put("SUVTYPE", suvType);
                    if(callPrd != null) {
                    	inMap.put("CALPRD", callPrd);
                    	calList.add(inMap);
                    }

                    if(modPrd != null) {
                	    inMap.put("MODYPRD", modPrd);
                	    modyList.add(inMap);
                    }
                }
			}
			service.setSqlSessionBySysid(gSysid);
			// ������Ⱚ���� �������� �����Ѵ�.
			service.saveCalModyPrd(calList, modyList);

		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError);
		} finally {
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}

	/** 
	 * ���𵨸� ��������, ����� ���� ó��
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="radFloorCalApply")
	public View radFloorCalApply(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");

		// ������������� ���� �ʿ��� �����͙V
		Dataset dsTemplateFloor = paramVO.getDataset("ds_template_floor");  //  ds_template_prh
		// ���� ���� �����
		Dataset dsFloor = paramVO.getDataset("ds_floor");

		// �����
		Dataset dsTemplate1 = paramVO.getDatasetList().getDataset("ds_template1");
		String strGtype = paramVO.getVariable("strGtype");
		String strZflg = paramVO.getVariable("strZflg");
		String gLang = paramVO.getVariable("G_LANG");

		Dataset dsSuvPrh = paramVO.getDataset("ds_suv_prh");
		String SUVSN   = paramVO.getVariable("SUVSN");
		String gMandt  = paramVO.getVariable("G_MANDT");
		String gSysid  = paramVO.getVariable("G_SYSID");
		//String gUserId = paramVO.getVariable("G_USER_ID");
		String gUserName = paramVO.getVariable("G_USER_NAME");

		try {
	    	// ������� �Է°�
			HashMap<String, String> prhMap = new HashMap<String, String>();
			Map<String, Object> paramMap = new HashMap<String, Object>();

			// ����������� �Է°��� �����Ѵ�.
			for (int i = 0; i < dsTemplateFloor.getRowCount(); i++) {
				String value = dsTemplateFloor.getColumnAsString(i, "PRD");
				if (null != value && ! "".equals(value)) {
					prhMap.put(dsTemplateFloor.getColumnAsString(i, "PRH"), value);
				}
			}
			paramMap.put("MANDT", gMandt);
			paramMap.put("SUVSN", SUVSN);
			paramMap.put("G_USER_NAME", gUserName);
			paramMap.put("EL_AUSE", prhMap.get("EL_AUSE"));
			paramMap.put("EL_EMF", prhMap.get("EL_EMF"));

			service.setSqlSessionBySysid(gSysid);
			List<SuvFloorVO> suvFloorList = service.selectSuvFloorList(paramMap);
			List<Map> suvJambList = service.selectElrEPnlhList(paramMap);

			// ������� ���� ����
			Map<String, FloorNmVO> floorPrhMap = new LinkedHashMap<String, FloorNmVO>();
			// JAMB HIP �������� �Ϸ�
			int startIndex = 0;
			int stopIndex = 0;
			for (int i = 0; i < dsSuvPrh.getRowCount(); i++) {
				String suvprh  = dsSuvPrh.getColumnAsString(i, "SUVPRH");
				String suvprd  = dsSuvPrh.getColumnAsString(i, "SUVPRD");
				String modyprd  = dsSuvPrh.getColumnAsString(i, "MODYPRD");
				
				if(suvprd == null) suvprd = "";
				if(modyprd ==null) modyprd = "";
				
				if(!"".equals(modyprd)) {
					suvprd = modyprd;
				}
				
				if (suvprh != null) {
					if(suvprh.indexOf("ELR_C_EF") > -1) {
						if(!"".equals(suvprd)) {
							startIndex = i;
							break;
						}
					}
				}
			}
			for (int i = (dsSuvPrh.getRowCount()-1); i >= 0; i--) {
				String suvprh  = dsSuvPrh.getColumnAsString(i, "SUVPRH");
				String suvprd  = dsSuvPrh.getColumnAsString(i, "SUVPRD");
				String modyprd  = dsSuvPrh.getColumnAsString(i, "MODYPRD");
				if(suvprd == null) suvprd = "";
				if(modyprd ==null) modyprd = "";
				
				if(!"".equals(modyprd)) {
					suvprd = modyprd;
				}
				
				if (suvprh != null) {
					if(suvprh.indexOf("ELR_C_EF") > -1) {
						if(!"".equals(suvprd)) {
							stopIndex = i;
							break;
						}
					}
				}
			}

			// ������ �Էµ� ���� ������ ����			
			// �������� �����͸� �����Ѵ�.
	    	// ������� �߿� ���� �����͸� �����Ѵ�.
			Map<Integer, String> floorSuvMap = new LinkedHashMap<Integer, String>();
			for (int i = stopIndex; i >= startIndex; i--) {
				String suvprh = dsSuvPrh.getColumnAsString(i, "SUVPRH");
				String suvprhname = dsSuvPrh.getColumnAsString(i, "SUVPRHNAME");
				String suvprd  = dsSuvPrh.getColumnAsString(i, "SUVPRD");
				String modyprd  = dsSuvPrh.getColumnAsString(i, "MODYPRD");
				if(suvprd == null) suvprd = "";
				if(modyprd ==null) modyprd = "";
				
				if(!"".equals(modyprd)) {
					suvprd = modyprd;
				}

				suvprhname = suvprhname.replace("��", "");
				suvprhname = suvprhname.replace("B", "-");
				suvprhname = suvprhname.trim();
				int floorNum = new Integer(suvprhname).intValue();
				String sFloor = "";
				if(floorNum>=0) {
					sFloor = new Integer(floorNum).toString();
					floorNum = floorNum -1;
				} else {
					sFloor = new Integer(floorNum).toString();
					sFloor = sFloor.replace("-", "B");
				}
				
				if(!"".equals(suvprd)) {
					sFloor = sFloor + ","+"Y,"+suvprh+", "+suvprd;
				} else {
					sFloor = sFloor + ","+"N,"+suvprh+", "+suvprd;
				}
				floorSuvMap.put(floorNum, sFloor);
			}
			
			List<String> errMsgList = service.getFloorSpreadOut(SUVSN, prhMap, floorPrhMap, suvFloorList, suvJambList, floorSuvMap);

			// ���� ���� ����Ʈ�� �����ϸ� �޽��� ǥ������.
			// �Ķ���ͷ� ������� ���� ��� �÷��׸� �����Ѵ�.
			if(errMsgList.size() > 0) {
				resultVO.addVariable("FLOORYSNO", "N");
				resultVO.addVariable("LAYOUTYSNO", "N");				
				// �ش� �޽����� ù��° �����޽����� �����Ѵ�.
				dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", errMsgList.get(0), "Y", "Y");
				dsError.setId("ds_error");
				resultVO.addDataset(dsError);
			} else {
				resultVO.addVariable("FLOORYSNO", "Y");
				// ���������� ����� �� �������� �����͸� �����Ѵ�.
				List<FloorNmVO> floorPrhList = new ArrayList<FloorNmVO>(floorPrhMap.values());
				// DB ���� ó���� ���ؼ� ����� ������ �Է�
				for(int i=0; i < floorPrhList.size(); i++) {
					FloorNmVO floorNmVO = floorPrhList.get(i);
					floorNmVO.setMANDT(gMandt);
					floorNmVO.setSUVSN(SUVSN);
					floorNmVO.setAENAM(gUserName);
					floorNmVO.setERNAM(gUserName);
					if(floorNmVO.getFLOOR() == null) {
						floorNmVO.setFLOOR("");	
					}
					if(floorNmVO.getFLOORNM() == null) {
						floorNmVO.setFLOORNM("");	
					}
					if(floorNmVO.getJAMBGRP() == null) {
						floorNmVO.setJAMBGRP("");	
					}
					if(floorNmVO.getJAMBMODEL() == null) {
						floorNmVO.setJAMBMODEL("");	
					}
					if(floorNmVO.getHPIMODEL() == null) {
						floorNmVO.setHPIMODEL("");	
					}
					if(floorNmVO.getHPBMODEL() == null) {
						floorNmVO.setHPBMODEL("");	
					}
					if(floorNmVO.getHIPMODEL() == null) {
						floorNmVO.setHIPMODEL("");	
					}
					if(floorNmVO.getHLTNMODEL() == null) {
						floorNmVO.setHLTNMODEL("");	
					}
					if(floorNmVO.getFSWMODEL() == null) {
						floorNmVO.setFSWMODEL("");	
					}
					if(floorNmVO.getHPIPRD() == null) {
						floorNmVO.setHPIPRD("");	
					}
					if(floorNmVO.getHPBPRD() == null) {
						floorNmVO.setHPBPRD("");	
					}
					if(floorNmVO.getHIPPRD() == null) {
						floorNmVO.setHIPPRD("");	
					}
					if(floorNmVO.getHLTNPRD() == null) {
						floorNmVO.setHLTNPRD("");	
					}
					if(floorNmVO.getPRKSWPRD() == null) {
						floorNmVO.setPRKSWPRD("");	
					}
					if(floorNmVO.getFSWPRD() == null) {
						floorNmVO.setFSWPRD("");	
					}
					if(floorNmVO.getHPIAPPLYFLOOR() == null) {
						floorNmVO.setHPIAPPLYFLOOR("");	
					}
					if(floorNmVO.getHPBAPPLYFLOOR() == null) {
						floorNmVO.setHPBAPPLYFLOOR("");	
					}
					if(floorNmVO.getHIPAPPLYFLOOR() == null) {
						floorNmVO.setHIPAPPLYFLOOR("");	
					}
					if(floorNmVO.getHLTNAPPLYFLOOR() == null) {
						floorNmVO.setHLTNAPPLYFLOOR("");	
					}
					if(floorNmVO.getPRKSWAPPLYFLOOR() == null) {
						floorNmVO.setPRKSWAPPLYFLOOR("");	
					}
					if(floorNmVO.getFSWAPPLYFLOOR() == null) {
						floorNmVO.setFSWAPPLYFLOOR("");	
					}
					if(floorNmVO.getJAMBINFO() == null) {
						floorNmVO.setJAMBINFO("");	
					}
					if(floorNmVO.getSUVPRH() == null) {
						floorNmVO.setSUVPRH("");	
					}
					if(floorNmVO.getSUVPRD() == null) {
						floorNmVO.setSUVPRD("");	
					}
				}

		    	// ������� �Է°�
				HashMap<String, String> suvPrhMap = new HashMap<String, String>();
				HashMap<String, String> totSuvPrhMap = new HashMap<String, String>();
				for (int i = 0; i < dsSuvPrh.getRowCount(); i++) {
					String suvprh  = dsSuvPrh.getColumnAsString(i, "SUVPRH");
					String suvprd  = dsSuvPrh.getColumnAsString(i, "SUVPRD");
					String modyprd = dsSuvPrh.getColumnAsString(i, "MODYPRD");
					if(modyprd == null) { modyprd = ""; }
					if (null !=  suvprh) {
						// ���� �����͸� �Է��Ѵ�.
						if(suvprh.indexOf("ELR_C_EF") > -1 || suvprh.equals("ELR_C_EHP") || suvprh.equals("ELR_H_HH")) {
							if(!"".equals(modyprd)) {
								// ���� �������� �����ϸ� �������� �Է��� ��� �Ѵ�.
								suvPrhMap.put(suvprh, modyprd);
							} else {
								suvPrhMap.put(suvprh, suvprd);
							}
						}
						
						// ELR_H_BTNP - Ȧ��ư ��ġ������ ���� ��� ó��
						if(suvprh.equals("ELR_H_BTNP")) {
							suvPrhMap.put(suvprh, suvprd);
						}
						
						totSuvPrhMap.put(suvprh, suvprd);
					}
				}
				
				String sdk = prhMap.get("EL_DPK");
				if(sdk == null) {
					sdk = "N";
				} else {
					if("".equals(sdk)) {
						sdk = "N";
					}
				}
				suvPrhMap.put("EL_DPK", sdk);

		        // ���Ա� ���嵵 ���� ������
				Map<String, Object> outMap = new HashMap<String, Object>();
				service.setSqlSessionBySysid(gSysid);
				service.saveFloorOut(paramMap, floorPrhList, suvPrhMap, outMap);

				int addRow = -1;
				for( String key : outMap.keySet() ) {
					addRow = dsFloor.appendRow();
					dsFloor.setColumn(addRow, "SUVPRH", key);
					dsFloor.setColumn(addRow, "CALPRD", (String)outMap.get(key));
				}
				resultVO.addDataset(dsFloor);

				// ������� �����Ѵ�.
				HashMap<String, String> mapInput = new HashMap<String, String>();
				for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
					String value = dsTemplate1.getColumnAsString(i, "PRD");
					if (null != value && ! "".equals(value)) {
						mapInput.put(dsTemplate1.getColumnAsString(i, "PRH"), value);
					}
				}

		        // DB ���� ����, G_MANDT ���� ���� ����, ǰ��, � DB�� ���ӵȴ�.
				dutyNS.createDao(sqlSession.getSqlSessionBySysid(gSysid));
				dutyNS.dependDutySingleMo(gMandt, strGtype, "00", mapInput, gLang, strZflg);				
				String prh      = "";
		        String prhname  = "";
		        String prd      = "";
		        String atkla    = "";
		        String suvPrd   = "";
		        // dsTemplate1�� �ݿ��� �ش�.
		        List<Map> modyList = new ArrayList<Map>();
		        List<Map> calList = new ArrayList<Map>();		        
		        for (int i = 0; i < dsTemplate1.getRowCount(); i++) {
		            prh  = dsTemplate1.getColumnAsString(i, "PRH");
		            prhname  = dsTemplate1.getColumnAsString(i, "PRHNAME");
		            prd  = (String) mapInput.get(dsTemplate1.getColumnAsString(i, "PRH"));
		            atkla = dsTemplate1.getColumnAsString(i, "ATKLA");
		            suvPrd = totSuvPrhMap.get("prh");

		            if (prh  == null)  prh   = "";
		            if (atkla == null) atkla = "";

		            // ������ ��缭 ���� ���� PRH�� ���ؼ� �⺻�� �� ��缭 out�� ���ǵ� ��簪�� �Է��ϰ� ó���Ѵ�.
		            if (null != prd && ! "".equals(prd)) {
		                dsTemplate1.setColumn(i, "PRH"     , prh );
		                dsTemplate1.setColumn(i, "PRHNAME" , prhname );
		                // ��缭 PRH ���س����� ���ǰ� VALUE
		                dsTemplate1.setColumn(i, "PRD"     , prd );
		                dsTemplate1.setColumn(i, "ATKLA"   , atkla);

		                // ���ΰ��� �ٸ���� ó���Ѵ�.
		                if (suvPrd !=null) {
				            // ���ΰ��� �ٸ����
				            if(!prd.equals(suvPrd)) {
				                Map<String, Object> inMap = new HashMap<String, Object>();
				                inMap.put("MANDT", gMandt);
				                inMap.put("SUVSN", SUVSN);
				                inMap.put("USER_NAME", gUserName);
				                inMap.put("MODYPRD", prd);
				                modyList.add(inMap);
				            }
		                }
		            }
		        }

		        // ������Ⱚ���� �������� �����Ѵ�.
				service.saveCalModyPrd(calList, modyList);
				resultVO.addVariable("LAYOUTYSNO", "Y");				
				resultVO.addDataset(dsTemplate1);
		        // ����ó���� ������� �����Ѵ�.
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultVO.addVariable("FLOORYSNO", "N");
			resultVO.addVariable("LAYOUTYSNO", "N");				
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError);
		} finally {
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}

	/** 
     * ���� ���𵨸� ������� ��ȸ
     * @param MipParameterVO
     * @return "MipResultVO"
     * @throws Exception
     */
    @RequestMapping(value="findOrderSuvList")
    public View findOrderSuvList(MipParameterVO paramVO, Model model) {
        MipResultVO resultVO = new MipResultVO();
        Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
        Dataset dsSuvPrh = paramVO.getDataset("ds_suv_prh");
        String gSysid  = paramVO.getVariable("G_SYSID");
        try {
            Dataset ds_cond = paramVO.getDataset("ds_cond");
            Map<String, Object> paramMap = new HashMap<String, Object>();
            String MANDT  = paramVO.getVariable("G_MANDT");
            String PSPID  = ds_cond.getColumnAsString(0, "PSPID");
            String HOGI  = ds_cond.getColumnAsString(0, "HOGI");
            String SEQ  = ds_cond.getColumnAsString(0, "SEQ");
            paramMap.put("MANDT", MANDT);
            paramMap.put("PSPID", PSPID);
            paramMap.put("HOGI", HOGI);
            paramMap.put("SEQ", SEQ);

            service.setSqlSessionBySysid(gSysid);
            // ������� ���� ��ȸ
            Map<String, Object> suvtMap = service.selectOrderSuvYsno(paramMap);
            if(suvtMap != null) {
                String SUVST = (String)suvtMap.get("SUVST");
                String SUVSN = (String)suvtMap.get("SUVSN");
                int   MAX_SEQ = (Integer)suvtMap.get("MAX_SEQ"); 
                resultVO.addVariable("SUVSN", SUVSN);
				resultVO.addVariable("SUVYSNO", SUVST);
				if("Y".equals(SUVST)) {
	                // ��ϵ� ������ �����ϸ� ��ȸ�Ѵ�.
	                paramMap.put("SUVSN", SUVSN);
	                service.setSqlSessionBySysid(gSysid);
	                List<SuvPrhVO> suvPrhList = null;
	                if(MAX_SEQ == 0) {
	                	// 0���� ��� sap ������ ��ȸ
	                    suvPrhList = service.selectSapSuvPrhList(paramMap);
	                } else {
	                	// ���� �����ϴ� ��� �Ķ���� ������ ��ȸ
	                    suvPrhList = service.selectOrderSuvPrhList(paramMap);
	                }
	                if(suvPrhList.size() > 0) {
	                    DatasetUtil.moveListToDs(suvPrhList, dsSuvPrh);
	                    resultVO.addDataset(dsSuvPrh);             
	                }
				}
            } else {
				resultVO.addVariable("SUVYSNO", "N");
			}
        } catch (Exception e) {
            e.printStackTrace();
            dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
            dsError.setId("ds_error");
            resultVO.addDataset(dsError);
        } finally {
            model.addAttribute("resultVO", resultVO);
        }
        return view;
    }
    
    /** 
     * ���� ǥ�ص��� ���� �� ȣ�⺰ ������ȣ, �Ϸù�ȣ, ����, ȣ��, ����������ȣ ��ȸ
     * @param MipParameterVO
     * @return "MipResultVO"
     * @throws Exception
     */
    @RequestMapping(value="findQuotationSuvList")
    public View findQuotationSuvList(MipParameterVO paramVO, Model model) {
        MipResultVO resultVO = new MipResultVO();
        Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
        String gSysid  = paramVO.getVariable("G_SYSID");
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            String MANDT  = paramVO.getVariable("G_MANDT");
            String PSPID  = paramVO.getVariable("PSPID");
            String HOGI   = paramVO.getVariable("HOGI");
            paramMap.put("MANDT", MANDT);
            paramMap.put("PSPID", PSPID);
            paramMap.put("HOGI", HOGI);

            service.setSqlSessionBySysid(gSysid);
            // ������� ���� ��ȸ
            Map<String, Object> suvtMap = service.selectOrderSuvYsno(paramMap);
            if(suvtMap != null) {
            	String QTNUM  = (String)suvtMap.get("QTNUM");
            	Integer QTSER  = (Integer)suvtMap.get("QTSER");
            	Integer QTSEQ  = (Integer)suvtMap.get("QTSEQ");
            	String QSUVSN = (String)suvtMap.get("QSUVSN");
            	String OSUVSN = (String)suvtMap.get("OSUVSN");
            	if(QSUVSN == null) QSUVSN = "";

            	if(QTSEQ != null) {
                	resultVO.addVariable("RAD_QTNUM", QTNUM);
                	resultVO.addVariable("RAD_QTSER", new Integer(QTSER).toString());
                	resultVO.addVariable("RAD_QTSEQ", new Integer(QTSEQ).toString());
                	resultVO.addVariable("RAD_QSUVSN", QSUVSN);
                	resultVO.addVariable("RAD_OSUVSN", OSUVSN);
            	} else {
                	resultVO.addVariable("RAD_QTNUM", "");
                	resultVO.addVariable("RAD_QTSER", "");
                	resultVO.addVariable("RAD_QTSEQ", "");
                	resultVO.addVariable("RAD_QSUVSN", "");
                	resultVO.addVariable("RAD_OSUVSN", "");
            	}
            } else {
				resultVO.addVariable("SUVYSNO", "N");
			}
        } catch (Exception e) {
            e.printStackTrace();
            dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
            dsError.setId("ds_error");
            resultVO.addDataset(dsError);
        } finally {
            model.addAttribute("resultVO", resultVO);
        }
        return view;
    }

	/**
	 * ������簪 ���� ��ȸ
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="findATWTB")
	public View findATWTB(MipParameterVO paramVO, Model model) {
		MipResultVO resultVO = new MipResultVO();
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		String gSysid  = paramVO.getVariable("G_SYSID");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String MANDT  = paramVO.getVariable("G_MANDT");
			String PRH  = paramVO.getVariable("PRH");
			String PRD  = paramVO.getVariable("PRD");
			paramMap.put("MANDT", MANDT);
			paramMap.put("PRH", PRH);
			paramMap.put("PRD", PRD);
			service.setSqlSessionBySysid(gSysid);
			// ������� ���� ��ȸ
			String  ATWTB = service.selectATWTB(paramMap);
			resultVO.addVariable("G_ATWTB", ATWTB);
		} catch (Exception e) {
			e.printStackTrace();
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, "CE00001", e.getMessage(), "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError);
		} finally {
			model.addAttribute("resultVO", resultVO);
		}
		return view;
	}

	/** 
	 * ���� �������� �ٿ�ε�
	 * @param MipParameterVO
	 * @return "MipResultVO"
	 * @throws Exception
	 */
	@RequestMapping(value="dwnSuvExcel")
	public void dwnSuvExcel(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// �ش� �޼ҵ忡 ������ ���� �� �ֱ���.
	    //String path = resourceLoader.getResource("classpath:").getURI().getPath();
	    //path = path.replaceFirst("classes", "excel");
	    // ��ϰ� ���� �и��ؼ� ó���ؾ� �ڱ���.
	    String path =  "/srm/HELCO_WEB/HELCO_WEB.war/WEB-INF/excel/";

	    String excelFormName = path + "rad_excel_001.xlsx";
	    String gSysid  = req.getParameter("SYSID");
		String mandt   = req.getParameter("MANDT");
		String qtnum   = req.getParameter("QTNUM");
		String qtser   = req.getParameter("QTSER");
		String prjnum  = req.getParameter("PRJNUM");

		String fileName = "n";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> excelData = null;
		paramMap.put("MANDT", mandt);
		paramMap.put("QTNUM", qtnum);
		paramMap.put("QTSER", qtser);
		paramMap.put("PRJNUM", prjnum);

		try {
			service.setSqlSessionBySysid(gSysid);
			InputStream is = new BufferedInputStream(new FileInputStream(excelFormName));
			XLSTransformer xls = new XLSTransformer();
			excelData =  service.selectRadExcelData(paramMap, xls);
			if(prjnum == null) {
				fileName = qtnum + "_"+qtser+".xlsx";
			} else  {
				fileName = prjnum+".xlsx";
			}

			// ��Ʈ ���� ó���� ��
			Workbook workbook = xls.transformXLS(is, excelData);
			workbook.write(res.getOutputStream());
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx\"");
	}
}
