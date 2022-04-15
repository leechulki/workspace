package hdel.sd.smp.control;

/**
 * 이동계획 오픈/마감
 * 
 * 작성  일자   : 2012.06
 * HISTORY  : 신규개발
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
	 * 전체 조회
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

			//CW00002=필수 입력항목입니다.\n확인하여 주십시오.
			if ( "".equals( year ) 
					|| year == null )
			{
				throw new BizException("CW00002,계획년도");
			}

			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			Smp0070ParamVO paramV = new Smp0070ParamVO();						// vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); 					// 세션권한
			paramV.setPLAN_YEAR(year);											// 계획년도

			logger.info("@@@@@@@@@ paramV in @@@@@@@@@@@@@@");
			logger.info(paramV.toString());
			
			List<Smp0070VO> list = service.find(paramV);
			
			Dataset dsOutput = new Dataset();
			// list결과를 dataset으로 담기
			SmpComC.moveDs2List(dsOutput, Smp0070VO.class, list );
			
			logger.info("@@@@@@@@@ dsOutput in @@@@@@@@@@@@@@");
			logger.info(dsOutput.toString());
			
			// RFC 출력 dataset을 return
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
	 * 수주계획 복사/삭제
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

			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			// 입력받은 데이터중 [편성년월 + 편성개월수]를 조건으로 복사할 테이블데이타를 조회한다.
			Smp0070ParamVO paramV = new Smp0070ParamVO();						// vo
			paramV.setMANDT(paramVO.getVariable("G_MANDT")); 					// 세션권한
			paramV.setUSER_ID(paramVO.getVariable("G_USER_ID"));				// 로그인ID
			paramV.setZPYM(dsInput.getColumnAsString(0, "ZPYM"));				// 편성년월
			paramV.setZPMONTHS(dsInput.getColumnAsString(0, "ZPMONTHS"));		// 편성개월수
			
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
			date.set(2005, 7, 31);  // 2005년 8월 31일        
			System.out.println("= 6달 전 =");        
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
			
			int cc = (aa+bb)/12;//년도
			int dd = (aa+bb)%12;//월
			logger.info("@@@@@@@@@ aa 편성년월 : "+aa+"////bbb 편성개월수 :"+bb );
			logger.info("@@@@@@@@@ cc 더할년도 : "+cc+"////dd  월 결과:"+dd );
			
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
			logger.info("@@@@@@@@@ 월  :"+y );
			int ee = Integer.parseInt( paramV.getZPYM().substring(0, 4) );
			logger.info("@@@@@@@@@ ee 년 :"+ee );
			String val = Integer.toString(ee+cc)+y;
			logger.info("@@@@@@@@@ val  :"+val );
			paramV.setZPYM_TO( val );
			
			paramV.setZMPINC(dsInput.getColumnAsString(0, "ZMPINC"));			// 이동계획차수
			String flag = paramVO.getVariable("flag"); 							// 마감&취소 구분

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

			// RFC 출력 dataset을 return
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
	 * 이동계획 마감/취소 후 저장
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

			//service.createDao(sqlSession.getSqlSession(SrmSqlSession.HED)); // DAO생성
			service.createDao(sqlSession.getSqlSessionBySysid(paramVO.getVariable("G_SYSID"))); // DAO생성
			
			int rs = 0;
			for ( int i = 0 ; i < dsInput.getRowCount(); i++ )
			{
				Smp0070ParamVO paramV = new Smp0070ParamVO();						// vo
				paramV.setMANDT(paramVO.getVariable("G_MANDT"));					// 세션권한
				paramV.setUSER_ID(paramVO.getVariable("G_USER_ID"));				// 로그인ID
				paramV.setZMPINC(dsInput.getColumnAsString(i, "ZMPINC"));			// 이동계획번호
				
				paramV.setZPYM(dsInput.getColumnAsString(i, "ZPYM")); 				// 편성년월
				paramV.setZPMONTHS(dsInput.getColumnAsString(i, "ZPMONTHS"));		// 편성개월수
				paramV.setZOPFLG(dsInput.getColumnAsString(i, "ZOPFLG"));			// 오픈여부
				paramV.setZCLFLG(dsInput.getColumnAsString(i, "ZCLFLG"));			// 마감여부
				paramV.setZRMK(dsInput.getColumnAsString(i, "ZRMK"));				// 비고
				paramV.setROW_TYPE(dsInput.getColumnAsString(i, "ROW_TYPE"));		// row타입

				logger.info("@@@@@@@@@ paramV in @@@@@@@@@@@@@@");
				logger.info(paramV.toString());
				
				// 마감일 경우
				if ( "copy".equals( paramVO.getVariable("flag") ) )
				{
					rs = service.saveCopyNext(paramV);
				}
				// 취소일 경우
				else if ( "cancel".equals( paramVO.getVariable("flag") ) )
				{
					rs = service.saveCancelNext(paramV);
				}
				
			}
			logger.info("@@@@@@@@@ exe result : "+ rs);	
			
			Dataset dsOutput = new Dataset();

			// RFC 출력 dataset을 return
			MipResultVO resultVO = new MipResultVO();
			resultVO.addDataset(dsOutput); 
			model.addAttribute("resultVO", resultVO);     
		} catch (Exception e) { 
			e.printStackTrace();
		}	    
		
		return view;
	}
	


}
