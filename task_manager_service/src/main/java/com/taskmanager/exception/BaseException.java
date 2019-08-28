package com.taskmanager.exception;

public class BaseException extends RuntimeException {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static final String MESSAGE = "task manager runtime exception";

	    public BaseException() {
	        super();
	    }

	    public BaseException( String message) {
	        super(message, null, false, false);
	    }

	    public BaseException( Throwable cause) {
	        super(MESSAGE, cause, false, true);
	    }

	    public BaseException( String message, Throwable cause) {
	        super(message, cause, false, true);
	    }

	    protected BaseException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	        super(message, cause, enableSuppression, writableStackTrace);
	    }

}
