/**
 * 
 */
package hdel.lib.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.helco.xf.comm.CallSAPHdel;
import com.tobesoft.platform.data.Dataset;

import hdel.lib.control.SrmView;
import hdel.lib.domain.MipResultVO;

/**
 * @author Administrator
 *
 */
public class SrmExceptionResolver extends SimpleMappingExceptionResolver implements HandlerExceptionResolver
{

	/**
	 * Logger
	 */
	Logger logger = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerExceptionResolver#resolveException(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse,
			Object handler, Exception ex) {

		logger.error("##ERROR", ex);
		return super.resolveException(httpservletrequest, httpservletresponse, handler, ex);
	}

}
