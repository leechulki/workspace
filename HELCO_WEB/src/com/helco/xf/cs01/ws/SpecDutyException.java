package com.helco.xf.cs01.ws;

/**
 * ������� ����� ���� ����
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
