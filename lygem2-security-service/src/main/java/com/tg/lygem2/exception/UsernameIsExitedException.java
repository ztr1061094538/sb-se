package com.tg.lygem2.exception;

public class UsernameIsExitedException extends BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2640289173767554609L;

	public UsernameIsExitedException(String msg) {
        super(msg);
    }

    public UsernameIsExitedException(String msg, Throwable t) {
        super(msg, t);
    }
}