package hdel.sd.com.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0060ParamVO;
import hdel.sd.com.service.Com0060S;
import hdel.sd.smp.control.SmpComC;
import hdel.sd.smp.domain.SmpComParameterVO;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("com0060")
public class Com0060C {
	Logger logger = Logger.getLogger(this.getClass());

	private final String LANG_KO = "ko";
	private final String LANG_EN = "en";
	private final String SPRAS_KO = "3";
	private final String SPRAS_EN = "E";
	
	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0060S service;
	
	@RequestMapping(value="find")
	public View CodeFind(MipParameterVO paramVO, Model model) {
		
		try { 
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			Com0060ParamVO param = new Com0060ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));  // SAP CLIENT
			
			// 2012.11.13 영문코드정보 반영
			if ("KO".equals( paramVO.getVariable("G_LANG").toUpperCase() ) )	{
				param.setLang(LANG_KO);
				param.setSpras(SPRAS_KO);
			}else	{
				param.setLang(LANG_EN);
				param.setSpras(SPRAS_EN);
			}
			
			param.setFilter1(paramVO.getVariable("where"));
			param.setFilter2(paramVO.getVariable("popFlag"));
			logger.info("@@@@ ds_output where: "+ paramVO.getVariable("where"));
			logger.info("@@@@ ds_output popFlag: "+ paramVO.getVariable("popFlag"));
			
			// 서비스CALL
			List<Com0060ParamVO> list = service.code(param);
			
			logger.info("@@@@ list.size() : "+ list.size());
			
			Dataset ds_output = new Dataset();
			ds_output.setId("ds_output");
			ds_output.addColumn("CODE", ColumnInfo.COLTYPE_STRING, 256);
			ds_output.addColumn("CODE_NAME", ColumnInfo.COLTYPE_STRING, 256);
			ds_output.addColumn("FILTER1", ColumnInfo.COLTYPE_STRING, 256);
			if ( paramVO.getVariable("popFlag") == "nature" ) {
				ds_output.addColumn("ATSON", ColumnInfo.COLTYPE_STRING, 256);
			}			
			// 호출결과(list)를 데이타셋(ds_output)에 복사
			for (int i = 0; i < list.size(); i++) 
			{
				ds_output.appendRow();
				ds_output.setColumn(i, "CODE", list.get(i).getCode()==null?"":list.get(i).getCode() );
				ds_output.setColumn(i, "CODE_NAME", list.get(i).getCode_name()==null?"":list.get(i).getCode_name() );
				ds_output.setColumn(i, "FILTER1", list.get(i).getFilter1()==null?"":list.get(i).getFilter1() );
				if ( paramVO.getVariable("popFlag") == "nature" ) {
					ds_output.setColumn(i, "ATSON", list.get(i).getAtson()==null?"":list.get(i).getAtson() );
				}
			}

			//logger.info("@@@@ ds_output : "+ ds_output);
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_output); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}

	/**
	 * 사업장별 부서내역 조회(Grid용으로 작성)
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="teamCd")
	public View teamCd(MipParameterVO paramVO, Model model) {

		Dataset dsInput = paramVO.getDataset("ds_input");
		logger.info("@@@@@@@@@ teamCd in @@@@@@@@@@@@@@");
		logger.info(dsInput.toString());
		 
		try { 

			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			Com0060ParamVO paramV = new Com0060ParamVO();				// vo
			paramV.setMandt(paramVO.getVariable("G_MANDT")); 			// 세션권한
			paramV.setCode(dsInput.getColumnAsString(0, "CODE"));

			List<Com0060ParamVO> list = service.teamCd(paramV);
			Dataset ds_output = new Dataset();
			// list결과를 dataset으로 담기
			SmpComC.moveDs2List(ds_output, Com0060ParamVO.class, list );

			logger.info("@@@@@@@@@ team ds_output in @@@@@@@@@@@@@@");
			//logger.info(ds_output.toString());
			
			// dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_output);
			model.addAttribute("resultVO", resultVO);  
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	

	@RequestMapping(value="ZLCODE")
	public View CodeFindZLCODE(MipParameterVO paramVO, Model model) {
		
		try { 
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			Com0060ParamVO param = new Com0060ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));  // SAP CLIENT
			param.setPopFlag(paramVO.getVariable("popFlag"));
			param.setFilter1(paramVO.getVariable("filter1"));
			param.setFilter2(paramVO.getVariable("filter2"));
			param.setFilter3(paramVO.getVariable("filter3"));
			logger.info("@@@@ ds_output popFlag: "+ paramVO.getVariable("popFlag"));
			logger.info("@@@@ ds_output filter1: "+ paramVO.getVariable("filter1"));
			logger.info("@@@@ ds_output filter2: "+ paramVO.getVariable("filter2"));
			logger.info("@@@@ ds_output filter3: "+ paramVO.getVariable("filter3"));
			
			// 서비스CALL
			List<Com0060ParamVO> list = service.code(param);
			
			logger.info("@@@@ list.size() : "+ list.size());
			
			Dataset ds_output = new Dataset();
			ds_output.setId("ds_output");
			ds_output.addColumn("CODE", ColumnInfo.COLTYPE_STRING, 256);
			ds_output.addColumn("CODE_NAME", ColumnInfo.COLTYPE_STRING, 256);
			ds_output.addColumn("FILTER1", ColumnInfo.COLTYPE_STRING, 256);
			ds_output.addColumn("FILTER2", ColumnInfo.COLTYPE_STRING, 256);
			ds_output.addColumn("FILTER3", ColumnInfo.COLTYPE_STRING, 256);
			
			// 호출결과(list)를 데이타셋(ds_output)에 복사
			for (int i = 0; i < list.size(); i++) 
			{
				ds_output.appendRow();
				ds_output.setColumn(i, "CODE", list.get(i).getCode()==null?"":list.get(i).getCode() );
				ds_output.setColumn(i, "CODE_NAME", list.get(i).getCode_name()==null?"":list.get(i).getCode_name() );
				ds_output.setColumn(i, "FILTER1", list.get(i).getFilter1()==null?"":list.get(i).getFilter1() );
				ds_output.setColumn(i, "FILTER2", list.get(i).getFilter2()==null?"":list.get(i).getFilter2() );
				ds_output.setColumn(i, "FILTER3", list.get(i).getFilter3()==null?"":list.get(i).getFilter3() );
			}

			//logger.info("@@@@ ds_output : "+ ds_output);
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_output); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	
	
	@RequestMapping(value="brndfind")
	public View BrndCodeFind(MipParameterVO paramVO, Model model) {
		
		try { 
			
			// DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			// 파라메터SET
			Com0060ParamVO param = new Com0060ParamVO();
			param.setMandt(paramVO.getVariable("G_MANDT"));  // SAP CLIENT
			
			// 2012.11.13 영문코드정보 반영
			if ("KO".equals( paramVO.getVariable("G_LANG").toUpperCase() ) )	{
				param.setLang(LANG_KO);
				param.setSpras(SPRAS_KO);
			}else	{
				param.setLang(LANG_EN);
				param.setSpras(SPRAS_EN);
			}
			
			String brndseq = paramVO.getVariable("brndseq");
			String brndcd  = paramVO.getVariable("brndcd");
			if(brndseq == null) {
				brndseq = "000";
			} else {
				if("".equals(brndseq)) {
					brndseq = "000";
				}
			}

			if(brndcd == null) {
				brndcd = "NOBRND";
			} else {
				if("".equals(brndcd)) {
					brndcd = "NOBRND";
				}
			}

			param.setFilter1(brndseq);
			param.setFilter2(paramVO.getVariable("class"));
			param.setFilter3(brndcd);
			param.setFilter4(paramVO.getVariable("atkla"));
			
			// 서비스CALL
			List<Com0060ParamVO> list = service.brndfind(param);
			logger.info("@@@@ list.size() : "+ list.size());
			
			Dataset ds_output = new Dataset();
			ds_output.setId("ds_output");
			ds_output.addColumn("CODE", ColumnInfo.COLTYPE_STRING, 256);
			ds_output.addColumn("CODE_NAME", ColumnInfo.COLTYPE_STRING, 256);
			ds_output.addColumn("FILTER1", ColumnInfo.COLTYPE_STRING, 256);
			if ( paramVO.getVariable("popFlag") == "brndfind" ) {
				ds_output.addColumn("ATSON", ColumnInfo.COLTYPE_STRING, 256);
			}			
			// 호출결과(list)를 데이타셋(ds_output)에 복사
			for (int i = 0; i < list.size(); i++) 
			{
				ds_output.appendRow();
				ds_output.setColumn(i, "CODE", list.get(i).getCode()==null?"":list.get(i).getCode() );
				ds_output.setColumn(i, "CODE_NAME", list.get(i).getCode_name()==null?"":list.get(i).getCode_name() );
				ds_output.setColumn(i, "FILTER1", list.get(i).getFilter1()==null?"":list.get(i).getFilter1() );
				if ( paramVO.getVariable("popFlag") == "brndfind" ) {
					ds_output.setColumn(i, "ATSON", list.get(i).getAtson()==null?"":list.get(i).getAtson() );
				}
			}
			//logger.info("@@@@ ds_output : "+ ds_output);
			
			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_output); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}	
	
}
