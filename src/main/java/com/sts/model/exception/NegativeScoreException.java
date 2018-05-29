package com.sts.model.exception;

public class NegativeScoreException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NegativeScoreException(){
		super("A negative score was detected for a game.");
	}

}
