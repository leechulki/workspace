package hdel.lib.control;

import javax.servlet.http.HttpServletRequest;

import hdel.lib.domain.MipParameterVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public class SrmArgumentResolver implements WebArgumentResolver {

	@Autowired
	private SrmArgumentConvertor srmArgumentConvertor;
	
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest nativeWebRequest) throws Exception {
		if (methodParameter.getParameterType().equals(MipParameterVO.class)) {
			return srmArgumentConvertor.convert(nativeWebRequest.getNativeRequest(HttpServletRequest.class));
		}
			
		return UNRESOLVED;
	}

	
}
