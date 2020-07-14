package com.nauvalatmaja.x.cleanarchitecture.core.order;

public class InvalidRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidRequestException(String property, String error) {
		super(property + " " + error);
	}

}
