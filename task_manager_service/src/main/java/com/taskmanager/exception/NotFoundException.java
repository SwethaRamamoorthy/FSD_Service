package com.taskmanager.exception;

public class NotFoundException extends BaseException {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException( String message) {
	        super(message, null, false, false);
	    }

}
