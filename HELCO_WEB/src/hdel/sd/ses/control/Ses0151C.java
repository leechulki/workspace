package hdel.sd.ses.control;

import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.ses.domain.Ses0150;
import hdel.sd.ses.domain.Ses0150ParamVO;
import hdel.sd.ses.domain.Ses0151;
import hdel.sd.ses.service.Ses0151S;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("ses0151")
public class Ses0151C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Ses0151S service;
	
	/*------------------------------------------------------------
	 *  ������������
	 ------------------------------------------------------------*/
	@RequestMapping(value="save")
	public View Ses0151SaveView(MipParameterVO paramVO, Model model) { 
  
		// �������� DATASET GET
		Dataset ds_error 	= CallSAPHdel.makeErrorInfoCreateDs("ds_error");
		// Session GET
		SqlSession session = sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"));

		// DAO Create
		service.createDao(session);		
		
		try {
			// ���� ȣ��
			service.save(paramVO, model, session);

			} catch (Exception e ) {
			
			e.printStackTrace();
			// ȣ����  
			ds_error = CallSAPHdel.makeErrorInfoColSet(ds_error, e.getMessage(), "", "Y", "Y");
			
		}
		
		MipResultVO resultVO = new MipResultVO(); 
//		System.out.print("\n@@@ ds_error.getRowCount  ===>"+ds_error.getRowCount() 	+ "\n");
		ds_error.setId("ds_error");   
		resultVO.addDataset(ds_error);  	// ����INFO 
		model.addAttribute("resultVO", resultVO);   

		return view;

	}
	
	@RequestMapping(value="detail")
	public View Ses0151DtlView(MipParameterVO paramVO, Model model) {

		Dataset dsCond = paramVO.getDataset("ds_cond");
		Dataset dsList	= paramVO.getDataset("ds_header_file");	// UI�� return�� out dataset  
		 
		try { 
			Ses0150ParamVO param = new Ses0150ParamVO();
			
			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����

			String zrqseq = DatasetUtility.getString(dsCond, "ZRQSEQ");

			if (zrqseq == "") zrqseq = "0";

			
			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT
			param.setZrqseq(zrqseq);


			//--------------------------------------------------------------------------------------------
			// INPUT PARAM INFO     		
//			System.out.print("\n@@@ paramVO.G_MANDT===>"+paramVO.getVariable("G_MANDT") 	     +"\n");  
//			System.out.print("\n@@@ dsCond.ZRQSEQ 	 ===>"+DatasetUtility.getString(dsCond,"ZRQSEQ")+"\n");

			//--------------------------------------------------------------------------------------------
			
			List<Ses0151> list = service.searchDetail(param);  	// ����CALL
			dsList.deleteAll();
			
			for (int i=0;i<list.size();i++) { 			// ȣ����(list)�� ����Ÿ��(dsList)�� ����

				dsList.appendRow(); 	// ���߰�
				dsList.setColumn(i, "MANDT"    , list.get(i).getMandt());
				dsList.setColumn(i, "ZRQSEQ"   , list.get(i).getZrqseq());
				dsList.setColumn(i, "ZATTSEQ"  , list.get(i).getZattseq()); 
				dsList.setColumn(i, "ZATTPATH" , list.get(i).getZattpath()); 
				dsList.setColumn(i, "ZATTFN"   , list.get(i).getZattfn());
			}
			MipResultVO resultVO = new MipResultVO(); 			// ��� dataset�� return
			resultVO.addDataset(dsList); 

//			System.out.println("============== ds_detail.getRowCount() " + dsList.getRowCount());
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	
	
	@RequestMapping(value="seq")
	public View Ses0151SeqView(MipParameterVO paramVO, Model model) {

		Dataset dsSeq	= paramVO.getDataset("ds_seq");	// UI�� return�� out dataset  
		 
		try { 
			Ses0150ParamVO param = new Ses0150ParamVO();
			
			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����


			param.setMandt(paramVO.getVariable("G_MANDT"));	// SAP CLIENT


			//--------------------------------------------------------------------------------------------
			// INPUT PARAM INFO     		
//			System.out.print("\n@@@ paramVO.G_MANDT===>"+paramVO.getVariable("G_MANDT") 	     +"\n");  
			//--------------------------------------------------------------------------------------------
			
			List<Ses0151> list = service.searchSeq(param);  	// ����CALL
			dsSeq.deleteAll();
			
			for (int i=0;i<list.size();i++) { 			// ȣ����(list)�� ����Ÿ��(dsList)�� ����

				dsSeq.appendRow(); 	// ���߰�
				dsSeq.setColumn(i, "ZRQSEQ"      , list.get(i).getZrqseq()); 
			}
			MipResultVO resultVO = new MipResultVO(); 			// ��� dataset�� return
			resultVO.addDataset(dsSeq); 

//			System.out.println("============== ds_seq.getRowCount() " + dsSeq.getRowCount());
			
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}	

}
