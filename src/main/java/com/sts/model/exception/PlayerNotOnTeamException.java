package com.sts.model.exception;

public class PlayerNotOnTeamException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayerNotOnTeamException() {
		super("Player entered is currently not on this team");
	}

}
