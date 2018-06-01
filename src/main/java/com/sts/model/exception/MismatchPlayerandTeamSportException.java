package com.sts.model.exception;

public class MismatchPlayerandTeamSportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MismatchPlayerandTeamSportException() {
		super("Player and Team are not of the same sport category");
	}

}
