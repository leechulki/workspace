package hdel.lib.exception;

import org.apache.log4j.Logger;

public class RequireException extends RuntimeException {

	public RequireException(String field) {
		super(field);
		
		Logger logger = Logger.getLogger(this.getClass());
		logger.error("require field : " + field);
	}
}
