package com.sts.model.exception;

public class NegativeAttendanceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NegativeAttendanceException() {
		super("A negative attendance was detected for a game.");
	}

}
