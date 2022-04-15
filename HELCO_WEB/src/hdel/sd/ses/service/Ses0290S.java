package hdel.sd.ses.service;

import hdel.lib.domain.MipParameterVO;
import hdel.lib.service.SrmService;
import hdel.sd.ses.dao.Ses0290D;
import hdel.sd.ses.domain.Ses0035;
import hdel.sd.ses.domain.Ses0290;
import hdel.sd.ses.domain.Ses0290ParamVO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Service
public class Ses0290S extends SrmService {

	private Ses0290D Ses0290Dao;
	
	public void createDao(SqlSession sqlSession) {
		Ses0290Dao = sqlSession.getMapper(Ses0290D.class);
	}
	
	public List<Ses0290> searchHeader(Ses0290ParamVO param) {
		return Ses0290Dao.selectListHeader(param);
	}

	public List<Ses0290> searchDetail(Ses0290ParamVO param) {
		return Ses0290Dao.selectListDetail(param);
	}
	
	
	public void saveh(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		Dataset ds_list = paramVO.getDataset("ds_header2");
		// INPUT DATASET GET 
		
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0290 	param 		= new Ses0290();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setBlockno(DatasetUtility.getString(ds_list, irow, "BLOCKNO"));
				param.setBlocknm(DatasetUtility.getString(ds_list, irow, "BLOCKNM"));
				param.setGtype(DatasetUtility.getString(ds_list, irow, "GTYPE"));
				param.setBlockgbn(DatasetUtility.getString(ds_list, irow, "BLOCKGBN"));
				param.setZprdgbn(DatasetUtility.getString(ds_list, irow, "ZPRDGBN"));
				param.setSpec1(DatasetUtility.getString(ds_list, irow, "SPEC1"));
				param.setSpecess1(DatasetUtility.getString(ds_list, irow, "SPECESS1"));
				param.setSpecdefv1(DatasetUtility.getString(ds_list, irow, "SPECDEFV1"));
				param.setSpec2(DatasetUtility.getString(ds_list, irow, "SPEC2"));
				param.setSpecess2(DatasetUtility.getString(ds_list, irow, "SPECESS2"));
				param.setSpecdefv2(DatasetUtility.getString(ds_list, irow, "SPECDEFV2"));
				param.setSpec3(DatasetUtility.getString(ds_list, irow, "SPEC3"));
				param.setSpecess3(DatasetUtility.getString(ds_list, irow, "SPECESS3"));
				param.setSpecdefv3(DatasetUtility.getString(ds_list, irow, "SPECDEFV3"));
				param.setSpec4(DatasetUtility.getString(ds_list, irow, "SPEC4"));
				param.setSpecess4(DatasetUtility.getString(ds_list, irow, "SPECESS4"));
				param.setSpecdefv4(DatasetUtility.getString(ds_list, irow, "SPECDEFV4"));
				param.setSpec5(DatasetUtility.getString(ds_list, irow, "SPEC5"));
				param.setSpecess5(DatasetUtility.getString(ds_list, irow, "SPECESS5"));
				param.setSpecdefv5(DatasetUtility.getString(ds_list, irow, "SPECDEFV5"));
				param.setSpec6(DatasetUtility.getString(ds_list, irow, "SPEC6"));
				param.setSpecess6(DatasetUtility.getString(ds_list, irow, "SPECESS6"));
				param.setSpecdefv6(DatasetUtility.getString(ds_list, irow, "SPECDEFV6"));
				param.setSpec7(DatasetUtility.getString(ds_list, irow, "SPEC7"));
				param.setSpecess7(DatasetUtility.getString(ds_list, irow, "SPECESS7"));
				param.setSpecdefv7(DatasetUtility.getString(ds_list, irow, "SPECDEFV7"));
				param.setSpec8(DatasetUtility.getString(ds_list, irow, "SPEC8"));
				param.setSpecess8(DatasetUtility.getString(ds_list, irow, "SPECESS8"));
				param.setSpecdefv8(DatasetUtility.getString(ds_list, irow, "SPECDEFV8"));
				param.setSpec9(DatasetUtility.getString(ds_list, irow, "SPEC9"));
				param.setSpecess9(DatasetUtility.getString(ds_list, irow, "SPECESS9"));
				param.setSpecdefv9(DatasetUtility.getString(ds_list, irow, "SPECDEFV9"));
				param.setSpec10(DatasetUtility.getString(ds_list, irow, "SPEC10"));
				param.setSpecess10(DatasetUtility.getString(ds_list, irow, "SPECESS10"));
				param.setSpecdefv10(DatasetUtility.getString(ds_list, irow, "SPECDEFV10"));
				param.setSpec11(DatasetUtility.getString(ds_list, irow, "SPEC11"));
				param.setSpecess11(DatasetUtility.getString(ds_list, irow, "SPECESS11"));
				param.setSpecdefv11(DatasetUtility.getString(ds_list, irow, "SPECDEFV11"));
				param.setSpec12(DatasetUtility.getString(ds_list, irow, "SPEC12"));
				param.setSpecess12(DatasetUtility.getString(ds_list, irow, "SPECESS12"));
				param.setSpecdefv12(DatasetUtility.getString(ds_list, irow, "SPECDEFV12"));
				param.setSpec13(DatasetUtility.getString(ds_list, irow, "SPEC13"));
				param.setSpecess13(DatasetUtility.getString(ds_list, irow, "SPECESS13"));
				param.setSpecdefv13(DatasetUtility.getString(ds_list, irow, "SPECDEFV13"));
				param.setSpec14(DatasetUtility.getString(ds_list, irow, "SPEC14"));
				param.setSpecess14(DatasetUtility.getString(ds_list, irow, "SPECESS14"));
				param.setSpecdefv14(DatasetUtility.getString(ds_list, irow, "SPECDEFV14"));
				param.setSpec15(DatasetUtility.getString(ds_list, irow, "SPEC15"));
				param.setSpecess15(DatasetUtility.getString(ds_list, irow, "SPECESS15"));
				param.setSpecdefv15(DatasetUtility.getString(ds_list, irow, "SPECDEFV15"));
				param.setSpec16(DatasetUtility.getString(ds_list, irow, "SPEC16"));
				param.setSpecess16(DatasetUtility.getString(ds_list, irow, "SPECESS16"));
				param.setSpecdefv16(DatasetUtility.getString(ds_list, irow, "SPECDEFV16"));
				param.setSpec17(DatasetUtility.getString(ds_list, irow, "SPEC17"));
				param.setSpecess17(DatasetUtility.getString(ds_list, irow, "SPECESS17"));
				param.setSpecdefv17(DatasetUtility.getString(ds_list, irow, "SPECDEFV17"));
				param.setSpec18(DatasetUtility.getString(ds_list, irow, "SPEC18"));
				param.setSpecess18(DatasetUtility.getString(ds_list, irow, "SPECESS18"));
				param.setSpecdefv18(DatasetUtility.getString(ds_list, irow, "SPECDEFV18"));
				param.setSpec19(DatasetUtility.getString(ds_list, irow, "SPEC19"));
				param.setSpecess19(DatasetUtility.getString(ds_list, irow, "SPECESS19"));
				param.setSpecdefv19(DatasetUtility.getString(ds_list, irow, "SPECDEFV19"));
				param.setSpec20(DatasetUtility.getString(ds_list, irow, "SPEC20"));
				param.setSpecess20(DatasetUtility.getString(ds_list, irow, "SPECESS20"));
				param.setSpecdefv20(DatasetUtility.getString(ds_list, irow, "SPECDEFV20"));
				param.setOut1(DatasetUtility.getString(ds_list, irow, "OUT1"));
				param.setOut2(DatasetUtility.getString(ds_list, irow, "OUT2"));
				param.setOut3(DatasetUtility.getString(ds_list, irow, "OUT3"));
				param.setOut4(DatasetUtility.getString(ds_list, irow, "OUT4"));
				param.setOut5(DatasetUtility.getString(ds_list, irow, "OUT5"));
				param.setOut6(DatasetUtility.getString(ds_list, irow, "OUT6"));
				param.setOut7(DatasetUtility.getString(ds_list, irow, "OUT7"));
				param.setOut8(DatasetUtility.getString(ds_list, irow, "OUT8"));
				param.setOut9(DatasetUtility.getString(ds_list, irow, "OUT9"));
				param.setOut10(DatasetUtility.getString(ds_list, irow, "OUT10"));
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
				
				if (param.getBlocknm() == null)  param.setBlocknm("");
				if (param.getGtype() == null)  param.setGtype("");
				if (param.getBlockgbn() == null)  param.setBlockgbn("");
				if (param.getZprdgbn() == null)  param.setZprdgbn("");
				if (param.getSpec1() == null)  param.setSpec1("");
				if (param.getSpecess1() == null)  param.setSpecess1("");
				if (param.getSpecdefv1() == null)  param.setSpecdefv1("");
				if (param.getSpec2() == null)  param.setSpec2("");
				if (param.getSpecess2() == null)  param.setSpecess2("");
				if (param.getSpecdefv2() == null)  param.setSpecdefv2("");
				if (param.getSpec3() == null)  param.setSpec3("");
				if (param.getSpecess3() == null)  param.setSpecess3("");
				if (param.getSpecdefv3() == null)  param.setSpecdefv3("");
				if (param.getSpec4() == null)  param.setSpec4("");
				if (param.getSpecess4() == null)  param.setSpecess4("");
				if (param.getSpecdefv4() == null)  param.setSpecdefv4("");
				if (param.getSpec5() == null)  param.setSpec5("");
				if (param.getSpecess5() == null)  param.setSpecess5("");
				if (param.getSpecdefv5() == null)  param.setSpecdefv5("");
				if (param.getSpec6() == null)  param.setSpec6("");
				if (param.getSpecess6() == null)  param.setSpecess6("");
				if (param.getSpecdefv6() == null)  param.setSpecdefv6("");
				if (param.getSpec7() == null)  param.setSpec7("");
				if (param.getSpecess7() == null)  param.setSpecess7("");
				if (param.getSpecdefv7() == null)  param.setSpecdefv7("");
				if (param.getSpec8() == null)  param.setSpec8("");
				if (param.getSpecess8() == null)  param.setSpecess8("");
				if (param.getSpecdefv8() == null)  param.setSpecdefv8("");
				if (param.getSpec9() == null)  param.setSpec9("");
				if (param.getSpecess9() == null)  param.setSpecess9("");
				if (param.getSpecdefv9() == null)  param.setSpecdefv9("");
				if (param.getSpec10() == null)  param.setSpec10("");
				if (param.getSpecess10() == null)  param.setSpecess10("");
				if (param.getSpecdefv10() == null)  param.setSpecdefv10("");
				if (param.getSpec11() == null)  param.setSpec11("");
				if (param.getSpecess11() == null)  param.setSpecess11("");
				if (param.getSpecdefv11() == null)  param.setSpecdefv11("");
				if (param.getSpec12() == null)  param.setSpec12("");
				if (param.getSpecess12() == null)  param.setSpecess12("");
				if (param.getSpecdefv12() == null)  param.setSpecdefv12("");
				if (param.getSpec13() == null)  param.setSpec13("");
				if (param.getSpecess13() == null)  param.setSpecess13("");
				if (param.getSpecdefv13() == null)  param.setSpecdefv13("");
				if (param.getSpec14() == null)  param.setSpec14("");
				if (param.getSpecess14() == null)  param.setSpecess14("");
				if (param.getSpecdefv14() == null)  param.setSpecdefv14("");
				if (param.getSpec15() == null)  param.setSpec15("");
				if (param.getSpecess15() == null)  param.setSpecess15("");
				if (param.getSpecdefv15() == null)  param.setSpecdefv15("");
				if (param.getSpec16() == null)  param.setSpec16("");
				if (param.getSpecess16() == null)  param.setSpecess16("");
				if (param.getSpecdefv16() == null)  param.setSpecdefv16("");
				if (param.getSpec17() == null)  param.setSpec17("");
				if (param.getSpecess17() == null)  param.setSpecess17("");
				if (param.getSpecdefv17() == null)  param.setSpecdefv17("");
				if (param.getSpec18() == null)  param.setSpec18("");
				if (param.getSpecess18() == null)  param.setSpecess18("");
				if (param.getSpecdefv18() == null)  param.setSpecdefv18("");
				if (param.getSpec19() == null)  param.setSpec19("");
				if (param.getSpecess19() == null)  param.setSpecess19("");
				if (param.getSpecdefv19() == null)  param.setSpecdefv19("");
				if (param.getSpec20() == null)  param.setSpec20("");
				if (param.getSpecess20() == null)  param.setSpecess20("");
				if (param.getSpecdefv20() == null)  param.setSpecdefv20("");
				if (param.getOut1() == null)  param.setOut1("");
				if (param.getOut2() == null)  param.setOut2("");
				if (param.getOut3() == null)  param.setOut3("");
				if (param.getOut4() == null)  param.setOut4("");
				if (param.getOut5() == null)  param.setOut5("");
				if (param.getOut6() == null)  param.setOut6("");
				if (param.getOut7() == null)  param.setOut7("");
				if (param.getOut8() == null)  param.setOut8("");
				if (param.getOut9() == null)  param.setOut9("");
				if (param.getOut10() == null)  param.setOut10("");
				if (param.getZrmk() == null)  param.setZrmk("");

//System.out.println(" 11111111111111111111111111111     "  + param.getMandt());
//System.out.println(" 11111111111111111111111111111     "  + param.getBlockno());
//System.out.println(" 11111111111111111111111111111     "  + param.getBlocknm());
//System.out.println(" 11111111111111111111111111111     "  + param.getGtype());
//System.out.println(" 11111111111111111111111111111     "  + param.getSpecess1());

				saveZSDTDUTY(param); 
			}
			
		 
		}  catch (Exception e) { 
		}
	}
	
	public void saved(MipParameterVO paramVO, Model model, SqlSession session) throws Exception{
		Dataset ds_list = paramVO.getDataset("ds_detail2");
		// INPUT DATASET GET 
		
		try
		{  

			//------------------------------------------------------------------------
			// 변수 선언
			//------------------------------------------------------------------------
			Ses0290 	param 		= new Ses0290();						// 저장 파라메터
			
			//------------------------------------------------------------------------
			// DAO 생성
			//------------------------------------------------------------------------
			createDao(session);	 
			
			
			for( int irow = 0; irow < ds_list.getRowCount(); irow++ ) 
			{  
				param.setMandt(DatasetUtility.getString(ds_list, irow, "MANDT"));
				param.setBlockno(DatasetUtility.getString(ds_list, irow, "BLOCKNO"));
				param.setZseq(DatasetUtility.getString(ds_list, irow, "ZSEQ"));
				param.setSpec1(DatasetUtility.getString(ds_list, irow, "SPEC1"));
				param.setSpec2(DatasetUtility.getString(ds_list, irow, "SPEC2"));
				param.setSpec3(DatasetUtility.getString(ds_list, irow, "SPEC3"));
				param.setSpec4(DatasetUtility.getString(ds_list, irow, "SPEC4"));
				param.setSpec5(DatasetUtility.getString(ds_list, irow, "SPEC5"));
				param.setSpec6(DatasetUtility.getString(ds_list, irow, "SPEC6"));
				param.setSpec7(DatasetUtility.getString(ds_list, irow, "SPEC7"));
				param.setSpec8(DatasetUtility.getString(ds_list, irow, "SPEC8"));
				param.setSpec9(DatasetUtility.getString(ds_list, irow, "SPEC9"));
				param.setSpec10(DatasetUtility.getString(ds_list, irow, "SPEC10"));
				param.setSpec11(DatasetUtility.getString(ds_list, irow, "SPEC11"));
				param.setSpec12(DatasetUtility.getString(ds_list, irow, "SPEC12"));
				param.setSpec13(DatasetUtility.getString(ds_list, irow, "SPEC13"));
				param.setSpec14(DatasetUtility.getString(ds_list, irow, "SPEC14"));
				param.setSpec15(DatasetUtility.getString(ds_list, irow, "SPEC15"));	
				param.setSpec16(DatasetUtility.getString(ds_list, irow, "SPEC16"));	
				param.setSpec17(DatasetUtility.getString(ds_list, irow, "SPEC17"));	
				param.setSpec18(DatasetUtility.getString(ds_list, irow, "SPEC18"));	
				param.setSpec19(DatasetUtility.getString(ds_list, irow, "SPEC19"));	
				param.setSpec20(DatasetUtility.getString(ds_list, irow, "SPEC20"));
				param.setOut1(DatasetUtility.getString(ds_list, irow, "OUT1"));
				param.setOut2(DatasetUtility.getString(ds_list, irow, "OUT2"));
				param.setOut3(DatasetUtility.getString(ds_list, irow, "OUT3"));
				param.setOut4(DatasetUtility.getString(ds_list, irow, "OUT4"));
				param.setOut5(DatasetUtility.getString(ds_list, irow, "OUT5"));
				param.setOut6(DatasetUtility.getString(ds_list, irow, "OUT6"));
				param.setOut7(DatasetUtility.getString(ds_list, irow, "OUT7"));
				param.setOut8(DatasetUtility.getString(ds_list, irow, "OUT8"));
				param.setOut9(DatasetUtility.getString(ds_list, irow, "OUT9"));
				param.setOut10(DatasetUtility.getString(ds_list, irow, "OUT10"));
				param.setZrmk(DatasetUtility.getString(ds_list, irow, "ZRMK"));
				
				if (param.getZseq() == null)  param.setZseq("");
				if (param.getSpec1() == null)  param.setSpec1("");
				if (param.getSpec2() == null)  param.setSpec2("");
				if (param.getSpec3() == null)  param.setSpec3("");
				if (param.getSpec4() == null)  param.setSpec4("");
				if (param.getSpec5() == null)  param.setSpec5("");
				if (param.getSpec6() == null)  param.setSpec6("");
				if (param.getSpec7() == null)  param.setSpec7("");
				if (param.getSpec8() == null)  param.setSpec8("");
				if (param.getSpec9() == null)  param.setSpec9("");
				if (param.getSpec10() == null)  param.setSpec10("");
				if (param.getSpec11() == null)  param.setSpec11("");
				if (param.getSpec12() == null)  param.setSpec12("");
				if (param.getSpec13() == null)  param.setSpec13("");
				if (param.getSpec14() == null)  param.setSpec14("");
				if (param.getSpec15() == null)  param.setSpec15("");
				if (param.getSpec16() == null)  param.setSpec16("");
				if (param.getSpec17() == null)  param.setSpec17("");
				if (param.getSpec18() == null)  param.setSpec18("");
				if (param.getSpec19() == null)  param.setSpec19("");
				if (param.getSpec20() == null)  param.setSpec20("");
				if (param.getOut1() == null)  param.setOut1("");
				if (param.getOut2() == null)  param.setOut2("");
				if (param.getOut3() == null)  param.setOut3("");
				if (param.getOut4() == null)  param.setOut4("");
				if (param.getOut5() == null)  param.setOut5("");
				if (param.getOut6() == null)  param.setOut6("");
				if (param.getOut7() == null)  param.setOut7("");
				if (param.getOut8() == null)  param.setOut8("");
				if (param.getOut9() == null)  param.setOut9("");
				if (param.getOut10() == null)  param.setOut10("");
				if (param.getZrmk() == null)  param.setZrmk("");

//System.out.println(" 11111111111111111111111111111     "  + param.getMandt());
//System.out.println(" 11111111111111111111111111111     "  + param.getBlockno());
//System.out.println(" 11111111111111111111111111111     "  + param.getBlocknm());
//System.out.println(" 11111111111111111111111111111     "  + param.getGtype());
//System.out.println(" 11111111111111111111111111111     "  + param.getSpecess1());

				saveZSDTDUTYD(param); 
			}
			
		 
		}  catch (Exception e) { 
		}
	}
	
	public void saveZSDTDUTY(Ses0290 param) {
		Ses0290Dao.saveZSDTDUTY(param);
	}
	public void saveZSDTDUTYD(Ses0290 param) {
		Ses0290Dao.saveZSDTDUTYD(param);
	}
}
