package hdel.sd.com.control;


import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.com.domain.Com0160;
import hdel.sd.com.domain.Com0160ParamVO;
import hdel.sd.com.service.Com0160S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("com0160")
public class Com0160C {

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Com0160S service;
	
	@RequestMapping(value="find")
	public View Com0160FindView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_build");
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_build 		= paramVO.getDataset("ds_list_build");	// UI�� return�� out dataset  
		 
		try { 
 
			Com0160ParamVO param = new Com0160ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));

			System.out.print("\n@@@ paramVO.G_MANDT ===>"+paramVO.getVariable("G_MANDT") 	        +"\n");  
			System.out.print("\n@@@ dsCond.BUILDUSE	===>"+DatasetUtility.getString(dsCond,"BUILDUSE")	+"\n");
			System.out.print("\n@@@ dsCond.STEXT	===>"+DatasetUtility.getString(dsCond,"STEXT")	+"\n");
			System.out.print("\n@@@ dsCond.WHERE	===>"+DatasetUtility.getString(dsCond,"WHERE")	+"\n");
			
			// �Ķ����SET
			param.setMandt(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			param.setBuilduse(DatasetUtility.getString(dsCond,"BUILDUSE"));	
			param.setStext(DatasetUtility.getString(dsCond,"STEXT"));
			param.setWhere(DatasetUtility.getString(dsCond,"WHERE"));

			// ����CALL
			List<Com0160> list = service.find(param);  
			
			ds_list_build.deleteAll();
			
			// ȣ����(list)�� ����Ÿ��(ds_list_build)�� ����
			for (int iRow=0;iRow<list.size();iRow++)
			{   
				// ���߰�
				ds_list_build.appendRow(); 
	    		
	    		// �÷�SET
				for (int iCol=0;iCol<ds_list_build.getColumnCount();iCol++)
				{    
					if (ds_list_build.getColumnID(iCol).equals("BUILDUSE")) {
						ds_list_build.setColumn(iRow, iCol	, list.get(iRow).getBuilduse());			// �ŷ�ó�ڵ� 
					} else if (ds_list_build.getColumnID(iCol).equals("STEXT")) {
						ds_list_build.setColumn(iRow, iCol	, list.get(iRow).getStext());			// �ŷ�ó�� 
					}
				} 
			} 
			
			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_list_build); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	
	@RequestMapping(value="find_extend")
	public View Com0160Find_extendView(MipParameterVO paramVO, Model model) {
		
		// INPUT DATASET GET
		Dataset dsCond = paramVO.getDataset("ds_cond_build");
		
		// OUTPUT DATASET DECLARE
		Dataset ds_list_build 		= paramVO.getDataset("ds_list_build");	// UI�� return�� out dataset 
        Dataset ds_list_build_l     = paramVO.getDataset("ds_list_build_l"); 
		 
		try { 
 
			Com0160ParamVO param = new Com0160ParamVO();
			
			// DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID")));
			
			// �Ķ����SET
			param.setMandt(paramVO.getVariable("G_MANDT"));  				// SAP CLIENT
			param.setBuilduse(DatasetUtility.getString(dsCond,"BUILDUSE"));	
			param.setStext(DatasetUtility.getString(dsCond,"STEXT"));
			param.setWhere(DatasetUtility.getString(dsCond,"WHERE"));

			// ����CALL
			List<Com0160> listBuild = service.find(param);  
            List<Com0160> listBuild_l = service.find_l(param);

            ds_list_build.deleteAll();
            // ȣ����(list)�� ����Ÿ��(ds_list_build)�� ����
            for (int iRow=0;iRow<listBuild.size();iRow++) {
                // ���߰�
                ds_list_build.appendRow();
                // �÷�SET
                for (int iCol=0;iCol<ds_list_build.getColumnCount();iCol++) {
                    if (ds_list_build.getColumnID(iCol).equals("BUILDUSE")) {
                        // �ŷ�ó�ڵ�
                        ds_list_build.setColumn(iRow, iCol    , listBuild.get(iRow).getBuilduse());
                    } 
                    else if (ds_list_build.getColumnID(iCol).equals("STEXT")) {
                        // �ŷ�ó��
                        ds_list_build.setColumn(iRow, iCol    , listBuild.get(iRow).getStext());
                    }
                    //2020.11.30 jss
                    else if (ds_list_build.getColumnID(iCol).equals("BUILDUSE_L")) {
                        //�ǹ��뵵��з�
                        ds_list_build.setColumn(iRow, iCol    , listBuild.get(iRow).getBuilduse_l());
                    }
                }
            }
            
            //2020.12 jss : �ǹ��뵵 ��з� ����
            ds_list_build_l.deleteAll();
            for (int iRow=0;iRow<listBuild_l.size();iRow++) {
                // ���߰�
                ds_list_build_l.appendRow();
                // �÷�SET
                for (int iCol=0;iCol<ds_list_build_l.getColumnCount();iCol++) {
                    if (ds_list_build_l.getColumnID(iCol).equals("BUILDUSE_L")) {
                        // �ŷ�ó�ڵ�
                        ds_list_build_l.setColumn(iRow, iCol    , listBuild_l.get(iRow).getBuilduse_l());
                    } 
                    else if (ds_list_build_l.getColumnID(iCol).equals("STEXT")) {
                        // �ŷ�ó��
                        ds_list_build_l.setColumn(iRow, iCol    , listBuild_l.get(iRow).getStext());
                    }
                }
            }
            
			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_list_build); 
			resultVO.addDataset(ds_list_build_l); 
			model.addAttribute("resultVO", resultVO);   
			
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
		
	}
	
}
