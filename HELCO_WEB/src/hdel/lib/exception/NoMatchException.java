package hdel.lib.exception;

import org.apache.log4j.Logger;

public class NoMatchException extends RuntimeException {

	public NoMatchException(String msg) {
		super("No Match Case !!!! : " + msg);
		
		Logger logger = Logger.getLogger(this.getClass());
		logger.error("No Match Case !!!! : " + msg);
	}
	
}
