package com.helco.xf.cs01.ws;

/**
 * 연산수식 사용자 정의 에러
 */
public class SpecDutyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public SpecDutyException() {
        super();
    }

    public SpecDutyException(String message) {
        super(message);
    }

    public SpecDutyException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpecDutyException(Throwable cause) {
        super(cause);
    }
}
