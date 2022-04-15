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
	 * list����� dataset���� ����
	 * 
	 * @param ds �����ͼ� 
	 * @param cls ó���� ��ü 
	 * @param list ����� ����Ʈ
	 * @return 
	 */	
	public static Object moveDs2List(Dataset ds, Class cls, List resultList) 
	{
		Logger logger = Logger.getLogger(SmpComC.class);
		
		Method[] me = cls.getMethods();
		//Dataset dsOutput = new Dataset();
		ds.setId("ds_output");
		
		// generetion�� ����� �÷������ ����
		String colinfo = "";
		
		// get �޼ҵ常 ��������
		for ( int ii = 0 ; ii < me.length ; ii++ )
		{
			if ( me[ii].getName().startsWith("get") && !me[ii].getName().equals("getClass") )
			{
				// �� row�� �÷�����
				//logger.info("@@@@@@ : "+ii+":"+me[ii].getName().substring(3));
				ds.addColumn(me[ii].getName().substring(3), ColumnInfo.COLTYPE_STRING, 256);
				
				// generetion�� ����� �÷������� [,]�� �Ͽ� ���ڿ��� �����Ѵ�.
				colinfo += ",";
				colinfo += (String) me[ii].getName().substring(3);
			}
		}
		
		// generetion�� ����� �÷��� �迭�� ������
		String[] ds_colinfo = colinfo.split(",");
		
		// console�� dataset format�� �������� �Ѵ�.
		logger.info("@@@@@@@@@@@@@@ generetion DATASET start @@@@@@@@@@@@@@@@");
		for ( int col = 1 ; col < ds_colinfo.length ; col++ )
		{
			//System.out.println("    <colinfo id="+ds_colinfo[col]+" size=256 type=STRING />");
		}
		logger.info("@@@@@@@@@@@@@@ generetion DATASET end @@@@@@@@@@@@@@@@");
		// Description ****
		// �ֿܼ��� �ش系���� �����Ͽ� miplatform�� �����ϸ�ȴ�.
		// "" ǥ�ô� ��� miplatform�� �����ϸ� �ڵ����� �����ȴ�.
		
		// list�Ǽ���ŭ ����
		for ( int i = 0; i < resultList.size(); i++) 
		{
			// �Ǽ���ŭ row ����
			ds.appendRow();
			
			// �� �޼ҵ� �Ǽ���ŭ ����
			for ( int ii = 0 ; ii < me.length ; ii++ )
			{
				// �޼ҵ���  get���� ����
				if ( me[ii].getName().startsWith("get") && !me[ii].getName().equals("getClass") )
				{
					try
					{
						// ������ �������� nullüũ
						//System.out.println(i+":"+ii+":"+me[ii].getName() + ":" + me[ii].invoke(tmpObj.getClass(), null));
						String val = me[ii].invoke(resultList.get(i), null)== null? "":me[ii].invoke(resultList.get(i), null).toString();
	
						// ����� �����Ͱ� null�� �ƴ϶��
						if ( val != "" )
						{
							// ������ dataset�� set�Ѵ�.
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
	 * Dataset�� �÷��� �������� �־��� Class �� �ش� ��ü�� �����Ͽ� ���� Move �Ѵ�. 
	 * �Ѱ��� �����͸� ��ü�� �ű� �� Ds2ObjHelper.endRow()�� ȣ���Ͽ� ó�� ���Ḧ �˸���. 
	 * 
	 * @param ds �����ͼ� 
	 * @param cls ó���� ��ü 
	 * @param flag �÷��� �÷� : TMODE�� ��Ī�Ǵ� �̸� (���� ��� ���� �ʿ� ����. )
	 * @return 
	 * 
	 */
	public static Object moveDs2Obj(Dataset ds, Class cls, String flag) {
		if ( flag != null && !flag.equals("")) {
			for( int i = ds.getRowCount()-1; i >= 0; i--) {
				// Flag �÷� �ܴ̿� ���� 
				if ( DatasetUtility.getString(ds,i,flag, "").equals("") 
					|| DatasetUtility.getString(ds,i,flag, "").equals("0")) {
					ds.deleteRow(i);
				}
			}
		}
		
		// �ش� Type���� �迭 ���� 
		Object obj = Array.newInstance(cls, ds.getRowCount());
		Method[] mArr = cls.getMethods();
		HashMap mData = new HashMap();
		for ( int i = 0; i < mArr.length; i++) {
			// Set �޼ҵ常 �������� 
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
							//b = new BigDecimal(DatasetUtility.getDbl(ds, i, ds.getColumnID(c)) + ""); // ���ֿ���� ���� ���� �߻� �κ� ����
							//System.out.println(b.toPlainString());
							//2016.01.27 framework �������� ���� 
							b = new BigDecimal(DatasetUtility.getStringSpace(ds, i, ds.getColumnID(c),"0"));
							
							if ("ZNUMBER".equals(ds.getColumnID(c))) 
							{
								b = b.setScale(0, RoundingMode.DOWN);  // �Ҽ� 0�ڸ�
							} else if ("ZFORE".equals(ds.getColumnID(c)) || "SHANG".equals(ds.getColumnID(c))) 
							{
								b = b.setScale(1, RoundingMode.DOWN);  // �Ҽ� 1�ڸ�
							} else 
							{
								b = b.setScale(2, RoundingMode.DOWN);	// �Ҽ� 2�ڸ�
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
			
			// Array ���� ���� 
			Array.set(obj, i, tmpObj);
		}
		return obj;
	}
	
	/**
	 * msg����� dataset���� ����
	 * 
	 * @param ds �����ͼ� 
	 * @param cls ó���� ��ü 
	 * @param list ����� ����Ʈ
	 * @return 
	 */	
	public static Object moveDs2Msg(String code, String cdnm, Model model) 
	{
		// �ΰ� ����
		Logger logger = Logger.getLogger(SmpComC.class);

		logger.info("@@@@@@@@@@@ moveDs2Msg in");
		
		// [ds_msg]�� dataset����
		Dataset ds_msg = new Dataset();
		ds_msg.setId("ds_msg");
		ds_msg.addColumn("CODE", ColumnInfo.COLTYPE_STRING, 256);
		ds_msg.addColumn("CODE_NAME", ColumnInfo.COLTYPE_STRING, 256);
		ds_msg.appendRow();
		ds_msg.setColumn(0, "CODE", code);
		ds_msg.setColumn(0, "CODE_NAME", cdnm);

		logger.info("@@@@@@@@@@@ moveDs2Msg dsMsg : "+ds_msg.toString());
		
		// 20120926
		// java exception���� ó���Ǿ� ȭ�鿡 ������ �ȵ�.
		
		// ȭ������� ������ dataset ����
		MipResultVO resultVO = new MipResultVO();
		resultVO.addDataset(ds_msg); 
		model.addAttribute("resultVO", resultVO);     
		
		//logger.info("@@@@@ result : "+ds);
		return 1;
	}



	  /**
	   * lpad �Լ�
	   *
	   * @param str   ����ڿ�, len ����, addStr ��ü����
	   * @return      ���ڿ�
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
