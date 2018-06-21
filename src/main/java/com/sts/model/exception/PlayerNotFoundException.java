package com.sts.model.exception;

import java.text.MessageFormat;

public class PlayerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayerNotFoundException(int playerID_) {
        super(MessageFormat.format("Unable to locate player, search term:{0}",playerID_));
	}

}
