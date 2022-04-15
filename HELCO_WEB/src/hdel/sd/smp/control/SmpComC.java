package hdel.sd.smp.control;

import hdel.lib.domain.MipResultVO;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import tit.util.DatasetUtility;

import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;

/**
 * SmpComC
 */
@Controller
@RequestMapping("SmpComC")
public class SmpComC {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * list결과를 dataset으로 생성
	 * 
	 * @param ds 데이터셋 
	 * @param cls 처리할 객체 
	 * @param list 결과셋 리스트
	 * @return 
	 */	
	public static Object moveDs2List(Dataset ds, Class cls, List resultList) 
	{
		Logger logger = Logger.getLogger(SmpComC.class);
		
		Method[] me = cls.getMethods();
		//Dataset dsOutput = new Dataset();
		ds.setId("ds_output");
		
		// generetion에 사용할 컬럼저장용 변수
		String colinfo = "";
		
		// get 메소드만 가져오기
		for ( int ii = 0 ; ii < me.length ; ii++ )
		{
			if ( me[ii].getName().startsWith("get") && !me[ii].getName().equals("getClass") )
			{
				// 한 row에 컬럼정보
				//logger.info("@@@@@@ : "+ii+":"+me[ii].getName().substring(3));
				ds.addColumn(me[ii].getName().substring(3), ColumnInfo.COLTYPE_STRING, 256);
				
				// generetion에 사용할 컬럼구분을 [,]로 하여 문자열로 저장한다.
				colinfo += ",";
				colinfo += (String) me[ii].getName().substring(3);
			}
		}
		
		// generetion에 사용할 컬럼을 배열로 재정렬
		String[] ds_colinfo = colinfo.split(",");
		
		// console에 dataset format이 찍히도록 한다.
		logger.info("@@@@@@@@@@@@@@ generetion DATASET start @@@@@@@@@@@@@@@@");
		for ( int col = 1 ; col < ds_colinfo.length ; col++ )
		{
			//System.out.println("    <colinfo id="+ds_colinfo[col]+" size=256 type=STRING />");
		}
		logger.info("@@@@@@@@@@@@@@ generetion DATASET end @@@@@@@@@@@@@@@@");
		// Description ****
		// 콘솔에서 해당내용을 복사하여 miplatform에 적용하면된다.
		// "" 표시는 없어도 miplatform에 복사하면 자동으로 생성된다.
		
		// list건수만큼 루프
		for ( int i = 0; i < resultList.size(); i++) 
		{
			// 건수만큼 row 생성
			ds.appendRow();
			
			// 총 메소드 건수만큼 루프
			for ( int ii = 0 ; ii < me.length ; ii++ )
			{
				// 메소드중  get인지 여부
				if ( me[ii].getName().startsWith("get") && !me[ii].getName().equals("getClass") )
				{
					try
					{
						// 생성된 데이터중 null체크
						//System.out.println(i+":"+ii+":"+me[ii].getName() + ":" + me[ii].invoke(tmpObj.getClass(), null));
						String val = me[ii].invoke(resultList.get(i), null)== null? "":me[ii].invoke(resultList.get(i), null).toString();
	
						// 저장된 데이터가 null이 아니라면
						if ( val != "" )
						{
							// 생성된 dataset에 set한다.
							ds.setColumn(i, me[ii].getName().substring(3), val);
						}
					
					}catch (InvocationTargetException e)
					{
						//logger.info("@@@@@ InvocationTargetException : "+e);
					}catch (IllegalArgumentException e)
					{
						//logger.info("@@@@@ IllegalArgumentException : "+e);
					}catch (IllegalAccessException e)
					{
						//logger.info("@@@@@ IllegalAccessException : "+e);
					}
					
				}
			}
		}
		//logger.info("@@@@@ result : "+ds);
		return ds;
	}


	/**
	 * 
	 * Dataset의 컬럼을 기준으로 주어진 Class 에 해당 객체를 생성하여 값을 Move 한다. 
	 * 한건의 데이터를 객체로 옮긴 후 Ds2ObjHelper.endRow()를 호출하여 처리 종료를 알린다. 
	 * 
	 * @param ds 데이터셋 
	 * @param cls 처리할 객체 
	 * @param flag 플래그 컬럼 : TMODE와 대칭되는 이름 (같을 경우 정의 필요 없음. )
	 * @return 
	 * 
	 */
	public static Object moveDs2Obj(Dataset ds, Class cls, String flag) {
		if ( flag != null && !flag.equals("")) {
			for( int i = ds.getRowCount()-1; i >= 0; i--) {
				// Flag 컬럼 이외는 삭제 
				if ( DatasetUtility.getString(ds,i,flag, "").equals("") 
					|| DatasetUtility.getString(ds,i,flag, "").equals("0")) {
					ds.deleteRow(i);
				}
			}
		}
		
		// 해당 Type으로 배열 생성 
		Object obj = Array.newInstance(cls, ds.getRowCount());
		Method[] mArr = cls.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			// Set 메소드만 가져오기 
			if ( mArr[i].getName().startsWith("set")) {
				mData.put(mArr[i].getName().substring(3), mArr[i]);
			}
		}
		Object tmpObj = null;
		Method m = null;
		Class setClass = null;
		BigDecimal b = null;
		for( int i = 0; i < ds.getRowCount(); i++ ) {
			try {
				tmpObj = cls.newInstance();
				
				for( int c = 0; c < ds.getColumnCount(); c++) {
					m = (Method) mData.get(ds.getColumnID(c));
					if ( m != null ) {
						setClass = m.getParameterTypes()[0];
						if ( setClass.getName().equals("int")){
							m.invoke(tmpObj, DatasetUtility.getInt(ds,i, ds.getColumnID(c)));
						} else if ( setClass.getName().equals("java.math.BigDecimal")){
							//b = new BigDecimal(DatasetUtility.getDbl(ds, i, ds.getColumnID(c)) + ""); // 수주예상액 계산시 오류 발생 부분 수정
							//System.out.println(b.toPlainString());
							//2016.01.27 framework 수정으로 변경 
							b = new BigDecimal(DatasetUtility.getStringSpace(ds, i, ds.getColumnID(c),"0"));
							
							if ("ZNUMBER".equals(ds.getColumnID(c))) 
							{
								b = b.setScale(0, RoundingMode.DOWN);  // 소수 0자리
							} else if ("ZFORE".equals(ds.getColumnID(c)) || "SHANG".equals(ds.getColumnID(c))) 
							{
								b = b.setScale(1, RoundingMode.DOWN);  // 소수 1자리
							} else 
							{
								b = b.setScale(2, RoundingMode.DOWN);	// 소수 2자리
							}
							m.invoke(tmpObj, b);
						} else {	
							m.invoke(tmpObj, DatasetUtility.getString(ds, i, ds.getColumnID(c)));
						}
					}
				}
				
				if ( !flag.equals("TMODE")) {
					m = (Method) mData.get("TMODE");
					if ( m != null ) {
						setClass = m.getParameterTypes()[0];
						m.invoke(tmpObj, ds.getColumnAsString(i, flag));
					}
				}
				
			} catch( Exception e){
				e.printStackTrace();
			}
			
			// Array 정보 설정 
			Array.set(obj, i, tmpObj);
		}
		return obj;
	}
	
	/**
	 * msg결과를 dataset으로 생성
	 * 
	 * @param ds 데이터셋 
	 * @param cls 처리할 객체 
	 * @param list 결과셋 리스트
	 * @return 
	 */	
	public static Object moveDs2Msg(String code, String cdnm, Model model) 
	{
		// 로거 생성
		Logger logger = Logger.getLogger(SmpComC.class);

		logger.info("@@@@@@@@@@@ moveDs2Msg in");
		
		// [ds_msg]로 dataset생성
		Dataset ds_msg = new Dataset();
		ds_msg.setId("ds_msg");
		ds_msg.addColumn("CODE", ColumnInfo.COLTYPE_STRING, 256);
		ds_msg.addColumn("CODE_NAME", ColumnInfo.COLTYPE_STRING, 256);
		ds_msg.appendRow();
		ds_msg.setColumn(0, "CODE", code);
		ds_msg.setColumn(0, "CODE_NAME", cdnm);

		logger.info("@@@@@@@@@@@ moveDs2Msg dsMsg : "+ds_msg.toString());
		
		// 20120926
		// java exception으로 처리되어 화면에 전송이 안됨.
		
		// 화면단으로 생성된 dataset 전송
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds_msg); 
		model.addAttribute("resultVO", resultVO);     
		
		//logger.info("@@@@@ result : "+ds);
		return 1;
	}



	  /**
	   * lpad 함수
	   *
	   * @param str   대상문자열, len 길이, addStr 대체문자
	   * @return      문자열
	   */

	  public static String lpad(String str, int len, String addStr) {
	      String result = str;
	      int templen   = len - result.length();

	      for (int i = 0; i < templen; i++){
	            result = addStr + result;
	      }
	      
	      return result;
	  }
	  
}
