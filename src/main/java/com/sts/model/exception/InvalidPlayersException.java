package com.sts.model.exception;

public class InvalidPlayersException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPlayersException() {
		super("Invalid player was detected for game");
	}

}
