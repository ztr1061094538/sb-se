package com.tg.lygem2.exception;

public class BaseException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 40257372096019538L;

	public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

