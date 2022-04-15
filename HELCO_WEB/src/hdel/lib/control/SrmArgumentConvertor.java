package hdel.lib.control;

import javax.servlet.http.HttpServletRequest;

public interface SrmArgumentConvertor {

	public Object convert(HttpServletRequest req);
}
