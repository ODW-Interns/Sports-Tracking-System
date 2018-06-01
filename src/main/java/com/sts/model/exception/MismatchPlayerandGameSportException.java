package com.sts.model.exception;

public class MismatchPlayerandGameSportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MismatchPlayerandGameSportException() {
		super("Player and Game are not of the same sport category");
	}

}
