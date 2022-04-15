package hdel.sd.smp.control;

/**
 * �̵���ȹ
 * �̵���ȹ ��
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.sd.smp.domain.Smp0010ParamVO;
import hdel.sd.smp.domain.SmpComParameterVO;
import hdel.sd.smp.service.Smp0010S;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("smp0010")
public class Smp0010C {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Smp0010S service;
	
	/**
	 * ��ü ��ȸ
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View find(MipParameterVO paramVO, Model model) {

		Dataset dsInput = paramVO.getDataset("ds_input");
		logger.info("@@@@@@@@@ dsInput in @@@@@@@@@@@@@@");
		logger.info(dsInput.toString());
		
		 
		try { 

			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			
			Smp0010ParamVO paramV = new Smp0010ParamVO();//vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); //���Ǳ���
			paramV.setColumn0(dsInput.getColumn(0, "column0").toString());
			paramV.setColumn1(dsInput.getColumn(0, "column1").toString());	
			paramV.setColumn2(dsInput.getColumn(0, "column2").toString());
			paramV.setColumn3(dsInput.getColumn(0, "column3").toString());

			logger.info("@@@@@@@@@ paramV in @@@@@@@@@@@@@@");
			logger.info(paramV.toString());
			

			List<Smp0010ParamVO> list = service.find(paramV);
			logger.info("@@@@@@@@@@@@@@@@@@@@@@@ list : "+list);

			Dataset dsOutput = paramVO.getDataset("ds_output");	// UI�� return�� out dataset  
			
			// ȣ����(list)�� ����Ÿ��(dsList)�� ����
			for ( int iRow = 0 ; iRow < list.size() ; iRow++ ) 
			{

				dsOutput.appendRow(); 	// ���߰�

				for ( int iCol = 0 ; iCol < dsOutput.getColumnCount() ; iCol++ )	
				{
					if (dsOutput.getColumnID(iCol).equals("column0")) 
					{
						dsOutput.setColumn(iRow, iCol, list.get(iRow).getColumn0()); 
					}
					else if (dsOutput.getColumnID(iCol).equals("column1")) 
					{
						dsOutput.setColumn(iRow, iCol, list.get(iRow).getColumn1()); 
					}
					else if (dsOutput.getColumnID(iCol).equals("column2")) 
					{ 
						dsOutput.setColumn(iRow, iCol, list.get(iRow).getColumn2()); 
					}
					else if (dsOutput.getColumnID(iCol).equals("column3")) 
					{
						dsOutput.setColumn(iRow, iCol, list.get(iRow).getColumn3()); 
					}
					else if (dsOutput.getColumnID(iCol).equals("column4")) 
					{
						dsOutput.setColumn(iRow, iCol, list.get(iRow).getColumn4()); 
					}
					else if (dsOutput.getColumnID(iCol).equals("column5")) 
					{
						dsOutput.setColumn(iRow, iCol, list.get(iRow).getColumn5());
					}
					else if (dsOutput.getColumnID(iCol).equals("CHK")) 
					{
						dsOutput.setColumn(iRow, iCol, list.get(iRow).getCHK());
					}
					else if (dsOutput.getColumnID(iCol).equals("STATUS")) 
					{
						dsOutput.setColumn(iRow, iCol, list.get(iRow).getSTATUS());
					}
					
				} 
			} 

			
			logger.info("@@@@@@@@@ dsOutput in @@@@@@@@@@@@@@");
			logger.info(dsOutput.toString());
			
			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	
	/**
	 * ����
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="save")
	public View save(MipParameterVO paramVO, Model model) {

		Dataset dsOutput = paramVO.getDataset("ds_output");
		logger.info("@@@@@@@@@ save in @@@@@@@@@@@@@@");
		logger.info(dsOutput.toString());
		 
		try { 
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			
			
			
			for (int i = 0 ; i < dsOutput.getRowCount() ; i++ ) 
			{
				
				if ( "true".equals(dsOutput.getColumn(i, "CHK")) ) 
				{
					Smp0010ParamVO paramV = new Smp0010ParamVO();//vo
					/*paramV.setColumn0("999");
					paramV.setColumn1("ko");
					paramV.setColumn2("100");
					paramV.setColumn3("110");	*/
					//param.setMandt(paramVO.getVariable("G_MANDT")); //���Ǳ���
					paramV.setColumn0(dsOutput.getColumnAsString(i, "column0"));
					paramV.setColumn1(dsOutput.getColumnAsString(i, "column1"));
					paramV.setColumn2(dsOutput.getColumnAsString(i, "column2"));
					paramV.setColumn3(dsOutput.getColumnAsString(i, "column3"));	
	
					logger.info("@@@@@@@@@ paramV in @@@@@@@@@@@@@@");
					logger.info(paramV.toString());
					
					List<Smp0010ParamVO> list = service.find(paramV);
					if ( list.size() > 0 )
					{
						if ( "update".equals(dsOutput.getColumnAsObject(i, "STATUS")) )
						{
							//update
							service.update(paramV);
							
							//�̰� ��������...ȭ���Ͽ�..
						}
						else
						{
							//delete
							service.delete(paramV);
						}
					}
					else
					{
						//insert
						service.insert(paramV);
					}
				
				}//if end
			}//for end
			
			

		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}



	/**
	 * ����庰 �μ����� ��ȸ(Grid������ �ۼ�)
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="team")
	public View teamCd(MipParameterVO paramVO, Model model) {

		Dataset dsInput = paramVO.getDataset("ds_input");
		logger.info("@@@@@@@@@ teamCd in @@@@@@@@@@@@@@");
		logger.info(dsInput.toString());
		 
		try { 
			
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			SmpComParameterVO paramV = new SmpComParameterVO();//vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); //���Ǳ���
			paramV.setCODE(dsInput.getColumnAsString(0, "CODE"));

			List<SmpComParameterVO> list = service.teamCd(paramV);
			Dataset ds_output = new Dataset();
			ds_output.setId("ds_output");
			ds_output.addColumn("CODE", ColumnInfo.COLTYPE_STRING, 256);
			ds_output.addColumn("CODE_NAME", ColumnInfo.COLTYPE_STRING, 256);
			ds_output.addColumn("FILTER1", ColumnInfo.COLTYPE_STRING, 256);

			for (int i = 0; i < list.size(); i++) {
				ds_output.appendRow();
				ds_output.setColumn(i, 0, list.get(i).getCODE());
				ds_output.setColumn(i, 1, list.get(i).getCODE_NAME());
				ds_output.setColumn(i, 2, list.get(i).getFILTER1());
			}

			logger.info("@@@@@@@@@ team ds_output in @@@@@@@@@@@@@@");
			logger.info(ds_output.toString());
			
			// dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(ds_output);
			model.addAttribute("resultVO", resultVO);  
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	
}
