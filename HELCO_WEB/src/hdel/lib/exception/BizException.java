package hdel.lib.exception;

import org.apache.log4j.Logger;

public class BizException extends RuntimeException {

	public BizException(String s) {
		super(s);
		
		Logger logger = Logger.getLogger(this.getClass());
		logger.error("Logic Error : " + s);
	}
	
}
