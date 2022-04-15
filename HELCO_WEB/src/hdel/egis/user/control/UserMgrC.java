/**
 * SRM_EGIS_IF
 * <br>e-GIS �ؿ� ���� �ý��� I/F
 */
package hdel.egis.user.control;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.helco.xf.comm.CallSAP;
import com.helco.xf.comm.CallSAPHdel;
import com.helco.xf.wb01.LoginService;
import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

import hdel.egis.user.domain.UserParamVO;
import hdel.egis.user.domain.UserVO;
import hdel.egis.user.service.UserMgrS;
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0020;
import hdel.sd.com.domain.Com0020ParamVO;
import hdel.sd.com.service.Com0020S;
import tit.base.ServiceManagerFactory;
import tit.util.DatasetUtility;

/**
 * UserMgrC.java <br>
 * <br>
 * 2019. 3. 7.
 * 
 * @author Administrator
 */
@Controller
@RequestMapping("IfUserMgrC")
public class UserMgrC {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;

	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private UserMgrS service;
	
	@RequestMapping(value="getUserInfo")
	public View getUserInfo(MipParameterVO paramVO, Model model) {
		
		// RFC 출력 dataset?�� return
		MipResultVO resultVO = new MipResultVO();
					
		try { 
			String mandt = paramVO.getVariable("G_MANDT");
			String lang = paramVO.getVariable("G_LANG");
			String userId = paramVO.getVariable("G_USER_ID");
			String sysId = paramVO.getVariable("G_SYSID");
			
			UserParamVO param = new UserParamVO();
			
			// DAO?��?��
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// ?��?��메터SET
			param.setMandt(mandt);  				// SAP CLIENT
			param.setLang(lang);
			param.setUserid(userId);
			param.setSysid(sysId);
			// ?��비스CALL
			List<UserVO> list = service.selectUser(param);  
			
			String column[] = {
					"SRM_MANDT"
					, "SRM_SYSID"
					, "SRM_USER_GROUP"
					, "SRM_USER_GROUP_R"
					, "SRM_USER_GROUP_B"
					, "SRM_USER_GROUP_M"
					, "SRM_USER_GROUP_E"
					, "SRM_VEND_CODE"
					, "SRM_VEND_CODE_R"
					, "SRM_VEND_CODE_B"
					, "SRM_VEND_CODE_M"
					, "SRM_VEND_CODE_E"
					
					, "SRM_EMAIL"
					, "SRM_USER_NAME"
					, "SRM_USER_ID"
					, "SRM_LANG"
					, "SRM_AREA_CODE_R"
					, "SRM_AREA_CODE_B"
					, "SRM_AREA_CODE_E"
					, "SRM_AREA_CODE"
					
					, "SRM_AREA_NAME"
					, "SRM_VEND_NAME"
					, "SRM_LGORT"
					, "SRM_DPT1"
					, "SRM_WGBN"
					, "SRM_VKBUR"
					, "SRM_VKBUR_NM"
					
					, "SRM_BIZ_PM"
					, "SRM_BIZ_PMNM"
					, "SRM_BSU_PM"
					, "SRM_BSU_PMNM"
					
					,"SRM_USERTELE"
					,"SRM_VKGRP"
					,"SRM_VKGRP_NM"
			};
			
			Dataset ds = new Dataset("ds_login");
			for(int i = 0; i <column.length; i++ ) {
				ds.addColumn(column[i], ColumnInfo.COLTYPE_STRING, 255);
			}
		
			for (int i = 0; i < list.size(); i++) {	
				ds.appendRow(); 	// ?��추�?
				ds.setColumn(i, "SRM_MANDT"   , list.get(i).getSRM_MANDT());
				ds.setColumn(i, "SRM_SYSID"   , list.get(i).getSRM_SYSID());
				ds.setColumn(i, "SRM_USER_GROUP"   , list.get(i).getSRM_USER_GROUP());
				ds.setColumn(i, "SRM_USER_GROUP_R"   , list.get(i).getSRM_USER_GROUP_R());
				ds.setColumn(i, "SRM_USER_GROUP_B"   , list.get(i).getSRM_USER_GROUP_B());
				ds.setColumn(i, "SRM_USER_GROUP_M"   , list.get(i).getSRM_USER_GROUP_M());
				ds.setColumn(i, "SRM_USER_GROUP_E"   , list.get(i).getSRM_USER_GROUP_E());
				ds.setColumn(i, "SRM_VEND_CODE"   , list.get(i).getSRM_VEND_CODE());
				ds.setColumn(i, "SRM_VEND_CODE_R"   , list.get(i).getSRM_VEND_CODE_R());
				ds.setColumn(i, "SRM_VEND_CODE_B"   , list.get(i).getSRM_VEND_CODE_B());
				ds.setColumn(i, "SRM_VEND_CODE_M"   , list.get(i).getSRM_VEND_CODE_M());
				ds.setColumn(i, "SRM_VEND_CODE_E"   , list.get(i).getSRM_VEND_CODE_E());
				
				ds.setColumn(i, "SRM_EMAIL"   , list.get(i).getSRM_EMAIL());
				ds.setColumn(i, "SRM_USER_NAME"   , list.get(i).getSRM_USER_NAME());
				ds.setColumn(i, "SRM_USER_ID"   , list.get(i).getSRM_USER_ID());
				ds.setColumn(i, "SRM_LANG"   , list.get(i).getSRM_LANG());
				ds.setColumn(i, "SRM_AREA_CODE_R"   , list.get(i).getSRM_AREA_CODE_R());
				ds.setColumn(i, "SRM_AREA_CODE_B"   , list.get(i).getSRM_AREA_CODE_B());
				ds.setColumn(i, "SRM_AREA_CODE_E"   , list.get(i).getSRM_AREA_CODE_E());
				ds.setColumn(i, "SRM_AREA_CODE"   , list.get(i).getSRM_AREA_CODE());
				ds.setColumn(i, "SRM_AREA_NAME"   , list.get(i).getSRM_AREA_NAME());
		
				ds.setColumn(i, "SRM_VEND_NAME"   , list.get(i).getSRM_VEND_NAME());
				ds.setColumn(i, "SRM_LGORT"   , list.get(i).getSRM_LGORT());
				ds.setColumn(i, "SRM_DPT1"   , list.get(i).getSRM_DPT1());
				
				ds.setColumn(i, "SRM_WGBN"   , list.get(i).getSRM_WGBN());
				ds.setColumn(i, "SRM_VKBUR"   , list.get(i).getSRM_VKBUR());
				ds.setColumn(i, "SRM_VKBUR_NM"   , list.get(i).getSRM_VKBUR_NM());
				ds.setColumn(i, "SRM_BIZ_PM"   , list.get(i).getSRM_BIZ_PM());
				ds.setColumn(i, "SRM_BIZ_PMNM"   , list.get(i).getSRM_BIZ_PMNM());
				ds.setColumn(i, "SRM_BSU_PM"   , list.get(i).getSRM_BSU_PM());
				ds.setColumn(i, "SRM_BSU_PMNM"   , list.get(i).getSRM_BSU_PMNM());
				
				
				ds.setColumn(i, "SRM_USERTELE"   , list.get(i).getSRM_USERTELE());
				ds.setColumn(i, "SRM_VKGRP"   , list.get(i).getSRM_VKGRP());
				ds.setColumn(i, "SRM_VKGRP_NM"   , list.get(i).getSRM_VKGRP_NM());
				
				
			}
			resultVO.addDataset(ds);
			
			resultVO.addVariable("ErrorCode", "0");
			resultVO.addVariable("ErrorMsg", "OK");
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
			resultVO.addVariable("ErrorCode", "-999999");
			resultVO.addVariable("ErrorMsg", e.getMessage());
		}	    
		
		return view;
	}
}
