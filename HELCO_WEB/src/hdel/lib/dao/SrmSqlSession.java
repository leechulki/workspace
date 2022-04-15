package hdel.lib.dao;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import tit.service.business.config.ConfigUtility;

@Component
public class SrmSqlSession {

	@Resource(name = "sqlSessionHED")
	private SqlSession sqlSessionHED;
	
	@Resource(name = "sqlSessionHEQ")
	private SqlSession sqlSessionHEQ;
	
	@Resource(name = "sqlSessionHEP")
	private SqlSession sqlSessionHEP;
	
//	public static String HED = "D";
//	public static String HEQ = "Q";
//	public static String HEP = "P";
	
	public SqlSession getSqlSessionBySysid(String sysid) {
		if(sysid.equals("HED")) return sqlSessionHED;
		if(sysid.equals("HEQ")) return sqlSessionHEQ;
		if(sysid.equals("HEP")) return sqlSessionHEP;

		return null;
	}
	/*public SqlSession getSqlSession(String mandt) {
		String db = getSystem(mandt);
		
		if (db.equals(HED)) return sqlSessionHED;
		if (db.equals(HEQ)) return sqlSessionHEQ;
		if (db.equals(HEP)) return sqlSessionHEP;
		
		return null;
	}*/
	
	/*private static String getSystem(String mandt) 
	{
		String system = "D";	// 개발 
		//String system = "";	// 개발
		if ("910".equals(mandt)) system = "D";
		// 시스템 판단 
		if ( ConfigUtility.getString("PRD_MANDT").indexOf(mandt) != -1 ) 
		{
			system = "P";
		} 
		else if ( ConfigUtility.getString("HEQ_MANDT").indexOf(mandt) != -1 ) 
		{
			system = "Q";	// 품질
		}
		return system;
	}*/
}
