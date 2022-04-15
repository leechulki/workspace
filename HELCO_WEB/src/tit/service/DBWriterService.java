package tit.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

import tit.base.ServiceBase;
import tit.base.ServiceBaseMBean;
import tit.base.ServiceManagerFactory;
import tit.base.ServiceName;
import tit.biz.UIConstants;
import tit.service.business.config.ConfigUtility;
import tit.service.core.logger.Logger;
import tit.service.resource.ResourceFactory;
import tit.service.resource.TransactionResource;
import tit.service.writer.Record;
import tit.service.writer.RecordWriteException;
import tit.service.writer.RecordWriter;
import tit.service.writer.SimpleElement;

import com.helco.xf.comm.Utillity;
import com.helco.xf.cs12.evladm.dbutil.LoggablePreparedStatement;

/**
 * 에러를 데이터베이스등에 기록한다.<p>
 * 백그라운드로 실행되기 때문에 ConnectonFactory 서비스는 UI 에서 사용하는 것과 별개로 사용해야 함. 
 * 
 */
public class DBWriterService extends ServiceBase implements
		RecordWriter, ServiceBaseMBean {

	// resource connection factory
	protected ResourceFactory mResourceFactory;	
	
	// resource connection factory service name
	protected ServiceName mResourceFactoryServiceName;

	Logger logger; 
	/**
	 * 에러 발생시 맵 형태로 데이터를 가지고 호출 됨. 
	 */
	public void write(Record rec) throws RecordWriteException {
		if(getState() != STARTED){
            return;
        }
		
		// 에러 정보 꺼내오기 -> 없으면 에러가 아님. 
		SimpleElement el = (SimpleElement) rec.getElementMap().get("ERROR");
		
		// 정보가 존재하지 않는다.
		if( el == null ) {
			return;
		}
		
		Throwable e = (Throwable) el.getValue();
		String errorTrace = null;
		int errorSqlCode =  0;
		String errorSqlStatus = null;
		
		// 로그 파일 기록 시 파일명으로 사용되는 항목 값  
		String logId = "";
		SimpleElement logEl = (SimpleElement) rec.getElementMap().get(UIConstants.PARAM_LOG_ID); 
		if( logEl != null ){
			logId = (String)logEl.getValue();
		}
		
		// 전체 요청 sqlmap 정보 
		String requestString = "";
		SimpleElement requestEl = (SimpleElement) rec.getElementMap().get("REQUEST"); 
		if( requestEl != null ){
			requestString = (String)requestEl.getValue();
		}
		
		//System.out.println( "#####:" + requestString );
		
		// 원하는 에러 타입에 따라서 처리하세요. 
		if( e instanceof SQLException ) {
			final SQLException sqlE = (SQLException) e;
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			
			// error 에 대한 전체 stack 
			errorTrace = sw.toString();
			errorSqlCode = sqlE.getErrorCode();
			errorSqlStatus = sqlE.getSQLState();
		}
		
		TransactionResource res = null;
		try {
			res = mResourceFactory.makeResource(Utillity.getSapJndi());
			Connection conn = (Connection) res.getResource();
			// Database에 insert 처리하면 됩니다. 
			logger = ServiceManagerFactory.getLogger();
			logger.debug("==> sql err 저장 ["+ errorSqlCode +"] : "+errorSqlStatus);
			//System.out.println("==> sql err 저장 ["+ errorSqlCode +"] : "+errorSqlStatus);
			
			StringBuffer               sqlBuff  = new StringBuffer();
			LoggablePreparedStatement  pstmt    = null;

			sqlBuff.append(" INSERT INTO SAPHEE.ZWBT050        \n");
			sqlBuff.append("  VALUES ( ?, HEX(CURRENT DATE), HEX(CURRENT TIME), ?,?,?)                \n");

			pstmt = new LoggablePreparedStatement(conn, sqlBuff.toString());

			pstmt.setString(1, ConfigUtility.getString("MANDT"));
			pstmt.setString(2, Integer.toString(errorSqlCode));
			pstmt.setString(3, errorTrace.substring(1, 100));
			pstmt.setString(4, requestString);

			pstmt.executeUpdate();

			
			res.commit();
		} catch( Throwable ee) {
			if( res != null ) {
				try {
					res.rollback();
				}catch( Throwable e1){}
			}
			
			ee.printStackTrace();
		} finally {
			if( res != null ) {
				try {
					res.close();
				}catch( Throwable ee){}
			}
		}
	}
	
	public void startService() throws Exception {
		super.startService();
		
		//  
		if (mResourceFactoryServiceName != null
				&& !mResourceFactoryServiceName.equals("")) {
			mResourceFactory = (ResourceFactory) ServiceManagerFactory
				.getServiceObject(mResourceFactoryServiceName);
		}
		
		if( mResourceFactory == null ) {
			throw new IllegalArgumentException("ResourceFactoryService or ResourceFactoryServiceName must be required.");
		}
	}
	
	public void stopService() throws Exception {
		super.stopService();
	}
	
	public ServiceName getResourceFactoryServiceName() {
		return mResourceFactoryServiceName;
	}
	
	public void setResourceFactoryServiceName(
			ServiceName resourceFactoryServiceName) {
		mResourceFactoryServiceName = resourceFactoryServiceName;
	}
}
