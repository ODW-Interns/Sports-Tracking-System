package com.sts.model.exception;

public class MismatchGameandTeamSportException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MismatchGameandTeamSportException() {
		super("Game and Team are not of the same sport category");

	}

}
