package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0341;
import hdel.sd.ses.domain.Ses0341ParamVO;
import hdel.sd.ses.service.Ses0341S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0341")
public class Ses0341C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0341S service;
	
	@RequestMapping(value="find")
	public View Ses0340FindView(MipParameterVO paramVO, Model model) {
		
		Dataset dsCond  = paramVO.getDataset("ds_cond");
		Dataset dsList  = paramVO.getDataset("ds_list");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		
		try {
			Ses0341ParamVO param = new Ses0341ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			String mandt      = paramVO.getVariable("G_MANDT");
			String lang       = paramVO.getVariable("G_LANG");
			String zbstdkFr   = DatasetUtility.getString(dsCond, "ZBSTDKFR");
			String zbstdkTo   = DatasetUtility.getString(dsCond, "ZBSTDKTO");
			String costzseqFr = DatasetUtility.getString(dsCond, "COSTZSEQFR");
			String costzseqTo = DatasetUtility.getString(dsCond, "COSTZSEQTO");
			
			if ( mandt      == null || mandt      == "" ) mandt      = "";
			if ( lang       == null || lang       == "" ) lang       = "ko";
			if ( zbstdkFr   == null || zbstdkFr   == "" ) zbstdkFr   = "";
			if ( zbstdkTo   == null || zbstdkTo   == "" ) zbstdkTo   = "";
			if ( costzseqFr == null || costzseqFr == "" ) costzseqFr = "";
			if ( costzseqTo == null || costzseqTo == "" ) costzseqTo = "";
			
			param.setMandt     (mandt);
			param.setLang      (lang);
			param.setZbstdkFr  (zbstdkFr);   // 조회조건 - 수주일 시작
			param.setZbstdkTo  (zbstdkTo);   // 조회조건 - 수주일 종료
			param.setCostzseqFr(costzseqFr); // 조회조건 - 코드 시작
			param.setCostzseqTo(costzseqTo); // 조회조건 - 코드 종료
			
			dsList.deleteAll();
			
			List<Ses0341> list = service.selectList(param);
			
			for ( int iRow = 0 ; iRow < list.size() ; iRow++ ) {
				dsList.appendRow();
				dsList.setColumn(iRow, "MANDT"    , list.get(iRow).getMandt()   );
				dsList.setColumn(iRow, "PSPID"    , list.get(iRow).getPspid()   ); // 프로젝트번호
				dsList.setColumn(iRow, "POSID"    , list.get(iRow).getPosid()   );
				dsList.setColumn(iRow, "COSTZSEQ" , list.get(iRow).getCostzseq());
				dsList.setColumn(iRow, "CTEXT1"   , list.get(iRow).getCtext1()  );
				dsList.setColumn(iRow, "CTEXT2"   , list.get(iRow).getCtext2()  );
				dsList.setColumn(iRow, "DTEXT1"   , list.get(iRow).getDtext1()  );
				dsList.setColumn(iRow, "DTEXT2"   , list.get(iRow).getDtext2()  );
				dsList.setColumn(iRow, "DTEXT3"   , list.get(iRow).getDtext3()  );
				dsList.setColumn(iRow, "DTEXT4"   , list.get(iRow).getDtext4()  );
				dsList.setColumn(iRow, "SETTING"  , list.get(iRow).getSetting() );
				dsList.setColumn(iRow, "QNTY"     , list.get(iRow).getQnty()    );
				dsList.setColumn(iRow, "ZUAM"     , list.get(iRow).getZuam()    );
				dsList.setColumn(iRow, "ZCOST"    , list.get(iRow).getZcost()   );
				dsList.setColumn(iRow, "ZRMK"     , list.get(iRow).getZrmk()    );
				dsList.setColumn(iRow, "ZBSTDK"   , list.get(iRow).getZbstdk()  ); // 수주일자
			}
			
			resultVO.addDataset(dsList);
			
			
		} catch (Exception e) { 
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, e.getMessage(), "", "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError);
			e.printStackTrace();
		}
		
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}

	@RequestMapping(value="findFile")
	public View Ses0340FindFileView(MipParameterVO paramVO, Model model) {
		
		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsFile = paramVO.getDataset("ds_file");
		Dataset dsError = CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		
		MipResultVO resultVO = new MipResultVO();
		
		try {
			Ses0341ParamVO param = new Ses0341ParamVO();
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			String mandt = paramVO.getVariable("G_MANDT");
			String pspid = DatasetUtility.getString(dsCond, "PSPID");
			
			if ( mandt == null || mandt == "" ) mandt = "";
			if ( pspid == null || pspid == "" ) pspid = "";
			
			param.setMandt(mandt);
			param.setPspid(pspid); // 조회조건 - 프로젝트번호
			
			dsFile.deleteAll();
			
			List<Ses0341> listFile = service.selectListFile(param);
			
			for ( int iRow = 0 ; iRow < listFile.size() ; iRow++ ) {
				dsFile.appendRow();
				dsFile.setColumn(iRow, "MANDT"   , listFile.get(iRow).getMandt()   );
				dsFile.setColumn(iRow, "PSPID"   , listFile.get(iRow).getPspid()   );
				dsFile.setColumn(iRow, "ZATTSEQ" , listFile.get(iRow).getZattseq() );
				dsFile.setColumn(iRow, "ZATGBN"  , listFile.get(iRow).getZatgbn()  );
				dsFile.setColumn(iRow, "ZATTPATH", listFile.get(iRow).getZattpath());
				dsFile.setColumn(iRow, "ZATTFN"  , listFile.get(iRow).getZattfn()  );
			}
			
			resultVO.addDataset(dsFile);
		} catch (Exception e) {
			dsError = CallSAPHdel.makeErrorInfoColSet(dsError, e.getMessage(), "", "Y", "Y");
			dsError.setId("ds_error");
			resultVO.addDataset(dsError);
			e.printStackTrace();
		}
		
		model.addAttribute("resultVO", resultVO);
		
		return view;
	}
	
}
