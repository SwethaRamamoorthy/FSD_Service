package com.taskmanager.exception;

public class BadRequestException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public BadRequestException(String message) {
        super(message, null, false, false);
    }

	public BadRequestException(String message, Throwable ex) {
		super(message, ex, false, false);
	}


}
