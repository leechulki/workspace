package hdel.sd.com.domain;

public class ZLang {

	/**
	 * HELCO_WEB 언어코드를 SAP 언어코드로 변환
	 * @param lang
	 * @return
	 */
	public static String convSapLang(String lang) {
		if (null == lang || "".equals(lang)) return "";
		
		if ("ko".equalsIgnoreCase(lang)) return "3";
		if ("en".equalsIgnoreCase(lang)) return "E";
		
		return "";
	}
}
