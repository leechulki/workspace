package hdel;

import org.apache.log4j.Logger;

public class Trace {

	public static void logBefore(String step, Object[] args) {
		Logger logger = Logger.getLogger(Trace.class);
		logger.trace(step);
//		for (int i=0; i<args.length; i++) {
//			logger.debug("    parameter : " + args[i].getClass().getName() + " " + args[i].toString());
//		}
	}
	
	public static void logAfter(String step, Object ret) {
		Logger logger = Logger.getLogger(Trace.class);
//		logger.debug("    return : " + ret);
		logger.trace(step);
	}
	
	public static void logException(String step, RuntimeException e) {
		Logger logger = Logger.getLogger(Trace.class);
		logger.trace(step);
		logger.trace(e.getMessage());
	}
}
