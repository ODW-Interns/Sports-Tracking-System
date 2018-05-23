/****************************************************************************
 * FILE: TeamNotFoundException.java
 * DSCRPT: 
 ****************************************************************************/

package model.exception;

import java.text.MessageFormat;

public class TeamNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    
    TeamNotFoundException(String team_)
    {
        super(MessageFormat.format("Unable to locate team, search term:{0}",team_));
    }
    
    
    
    TeamNotFoundException(int team_){
        super(MessageFormat.format("Unable to locate team, ident:{0}",team_));
    }
}
