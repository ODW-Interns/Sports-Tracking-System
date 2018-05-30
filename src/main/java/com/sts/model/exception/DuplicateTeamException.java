/****************************************************************************
 * FILE: DuplicateTeamException.java
 * DSCRPT:
 ****************************************************************************/

package com.sts.model.exception;

import java.text.MessageFormat;

import com.sts.abstractModel.Team;

public class DuplicateTeamException extends Exception {

    private static final long serialVersionUID = 1L;





    public DuplicateTeamException(String str_, Team team_) {
        super(MessageFormat.format("{0}, Team:{1}", str_, team_.getTeamName()));
        
    }

}
