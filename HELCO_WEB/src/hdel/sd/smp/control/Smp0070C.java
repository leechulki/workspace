package hdel.sd.smp.control;

/**
 * �̵���ȹ ����/����
 * 
 * �ۼ�  ����   : 2012.06
 * HISTORY  : �ű԰���
 */
import hdel.lib.control.SrmView;
import hdel.lib.dao.SrmSqlSession;
import hdel.lib.domain.MipParameterVO;
import hdel.lib.domain.MipResultVO;
import hdel.lib.exception.BizException;
import hdel.sd.smp.domain.Smp0070ParamVO;
import hdel.sd.smp.domain.Smp0070VO;
import hdel.sd.smp.service.Smp0070S;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.tobesoft.platform.data.Dataset;

@Controller
@RequestMapping("smp0070")
public class Smp0070C {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SrmView view;
	
	@Autowired
	private SrmSqlSession sqlSession;
	
	@Autowired
	private Smp0070S service;
	
	/**
	 * ��ü ��ȸ
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="find")
	public View find(MipParameterVO paramVO, Model model) {

		String year = paramVO.getVariable("year");
		logger.info("@@@@@@@@@ dsInput in @@@@@@@@@@@@@@");
		logger.info(year);
		 
		try { 

			//CW00002=�ʼ� �Է��׸��Դϴ�.\nȮ���Ͽ� �ֽʽÿ�.
			if ( "".equals( year ) 
					|| year == null )
			{
				throw new BizException("CW00002,��ȹ�⵵");
			}

			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			Smp0070ParamVO paramV = new Smp0070ParamVO();						// vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); 					// ���Ǳ���
			paramV.setPLAN_YEAR(year);											// ��ȹ�⵵

			logger.info("@@@@@@@@@ paramV in @@@@@@@@@@@@@@");
			logger.info(paramV.toString());
			
			List<Smp0070VO> list = service.find(paramV);
			
			Dataset dsOutput = new Dataset();
			// list����� dataset���� ���
			SmpComC.moveDs2List(dsOutput, Smp0070VO.class, list );
			
			logger.info("@@@@@@@@@ dsOutput in @@@@@@@@@@@@@@");
			logger.info(dsOutput.toString());
			
			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput); 
			model.addAttribute("resultVO", resultVO);     
		} catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if ( message.length <= 1)
			{
				SmpComC.moveDs2Msg(message[0], message[0], model);
			}
			else
			{
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}

	/**
	 * ���ְ�ȹ ����/����
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="copy")
	public View saveCopy(MipParameterVO paramVO, Model model) {

		Dataset dsInput = paramVO.getDataset("ds_input");
		logger.info("@@@@@@@@@ dsInput in @@@@@@@@@@@@@@");
		logger.info(dsInput.toString());
		 
		try { 

			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			// �Է¹��� �������� [����� + ��������]�� �������� ������ ���̺���Ÿ�� ��ȸ�Ѵ�.
			Smp0070ParamVO paramV = new Smp0070ParamVO();						// vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); 					// ���Ǳ���
			paramV.setUSER_ID(paramVO.getVariable("G_USER_ID"));				// �α���ID
			paramV.setZPYM(dsInput.getColumnAsString(0, "ZPYM"));				// �����
			paramV.setZPMONTHS(dsInput.getColumnAsString(0, "ZPMONTHS"));		// ��������
			
			// string -> date
			/*String zpym = "20121002";//paramV.getZPYM();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
			Date dt = sdf.parse(zpym);
			Calendar cal = Calendar.getInstance();
			logger.info("@@@@@@@@@ string -> date : "+ dt.toString() );

			cal.setTime(dt);      
			cal.add(cal.MONTH, +1);
			logger.info("@@@@@@@@@ cal: "+sdf.format(cal.getTime()) );
			System.out.println("SSSSSSSSSSSSSS :"+sdf.format(cal.get(cal.MONTH)+11) );
			System.out.println("SSSSSSSSSSSSSS :"+sdf.format(cal.getTime()) );
			 
			
			Calendar date = Calendar.getInstance();        
			date.set(2005, 7, 31);  // 2005�� 8�� 31��        
			System.out.println("= 6�� �� =");        
			date.add(Calendar.MONTH, -6);        
			System.out.println(sdf.format(date.getTime()) );

			DecimalFormat df  = new DecimalFormat("00");
			Calendar calendar = Calendar.getInstance();
			calendar.set(2005, 7, 31);
			System.out.println("SSSSSSSSSSSSSS :"+df.format(calendar.get(calendar.MONTH)+11) );

			Date d = new Date(calendar.getTimeInMillis());
			System.out.println("dddddddddddddddddd :"+d );*/
			int aa = Integer.parseInt( paramV.getZPYM().substring(4, 6) );
			int bb = Integer.parseInt( paramV.getZPMONTHS() )-1;
			
			int cc = (aa+bb)/12;//�⵵
			int dd = (aa+bb)%12;//��
			logger.info("@@@@@@@@@ aa ����� : "+aa+"////bbb �������� :"+bb );
			logger.info("@@@@@@@@@ cc ���ҳ⵵ : "+cc+"////dd  �� ���:"+dd );
			
			String y;
			String m;
			if ( "0".equals( Integer.toString(dd) ) )
			{
				y = "12";//paramV.setZPYM_TO("12");
				cc = 0;
			}
			else
			{
				m = Integer.toString(dd);//paramV.setZPYM_TO( Integer.toString(dd) );
				if ( m.length() < 2 )
				{
					y = "0"+m;
				}
				else
				{
					y = m;
				}
			}
			if ( "2".equals( Integer.toString(cc)) ) cc = 1;
			logger.info("@@@@@@@@@ ��  :"+y );
			int ee = Integer.parseInt( paramV.getZPYM().substring(0, 4) );
			logger.info("@@@@@@@@@ ee �� :"+ee );
			String val = Integer.toString(ee+cc)+y;
			logger.info("@@@@@@@@@ val  :"+val );
			paramV.setZPYM_TO( val );
			
			paramV.setZMPINC(dsInput.getColumnAsString(0, "ZMPINC"));			// �̵���ȹ����
			String flag = paramVO.getVariable("flag"); 							// ����&��� ����

			logger.info("@@@@@@@@@ paramV in @@@@@@@@@@@@@@");
			logger.info(paramV.toString());
			logger.info(paramVO.getClass().toString());
			
			int rs = 0;
			if ( "copy".equals(flag) )
			{
				rs = service.saveCopy(paramV);			// copy
			}
			else if ( "cancel".equals(flag) )
			{
				rs = service.saveCancel(paramV);		// cancel
			}
			logger.info("@@@@@@@@@ exe result : "+ rs);
			
			Dataset dsOutput = new Dataset();

			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput); 
			model.addAttribute("resultVO", resultVO);     
		} catch (BizException e) {
			String msg = e.getMessage().toString();
			String message[] = msg.split(",");

			if ( message.length <= 1)
			{
				SmpComC.moveDs2Msg(message[0], message[0], model);
			}
			else
			{
				SmpComC.moveDs2Msg(message[0], message[1], model);
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	
	

	/**
	 * �̵���ȹ ����/��� �� ����
	 * @param paramVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="copyNext")
	public View saveCopyNext(MipParameterVO paramVO, Model model) {

		Dataset dsInput = paramVO.getDataset("ds_input");
		logger.info("@@@@@@@@@ dsInput in @@@@@@@@@@@@@@");
		logger.info(dsInput.toString());
		 
		try { 

			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO����
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO����
			
			int rs = 0;
			for ( int i = 0 ; i < dsInput.getRowCount(); i++ )
			{
				Smp0070ParamVO paramV = new Smp0070ParamVO();						// vo
				paramV.setMANDT(paramVO.getVariable("G_MANDT"));					// ���Ǳ���
				paramV.setUSER_ID(paramVO.getVariable("G_USER_ID"));				// �α���ID
				paramV.setZMPINC(dsInput.getColumnAsString(i, "ZMPINC"));			// �̵���ȹ��ȣ
				
				paramV.setZPYM(dsInput.getColumnAsString(i, "ZPYM")); 				// �����
				paramV.setZPMONTHS(dsInput.getColumnAsString(i, "ZPMONTHS"));		// ��������
				paramV.setZOPFLG(dsInput.getColumnAsString(i, "ZOPFLG"));			// ���¿���
				paramV.setZCLFLG(dsInput.getColumnAsString(i, "ZCLFLG"));			// ��������
				paramV.setZRMK(dsInput.getColumnAsString(i, "ZRMK"));				// ���
				paramV.setROW_TYPE(dsInput.getColumnAsString(i, "ROW_TYPE"));		// rowŸ��

				logger.info("@@@@@@@@@ paramV in @@@@@@@@@@@@@@");
				logger.info(paramV.toString());
				
				// ������ ���
				if ( "copy".equals( paramVO.getVariable("flag") ) )
				{
					rs = service.saveCopyNext(paramV);
				}
				// ����� ���
				else if ( "cancel".equals( paramVO.getVariable("flag") ) )
				{
					rs = service.saveCancelNext(paramV);
				}
				
			}
			logger.info("@@@@@@@@@ exe result : "+ rs);	
			
			Dataset dsOutput = new Dataset();

			// RFC ��� dataset�� return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	


}
