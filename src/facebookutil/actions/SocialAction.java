package facebookutil.actions;

import facebookutil.login.LoginObject;


/**
 * This interface allows the user to utilize social action methods
 *
 */
public interface SocialAction {

    public void send (LoginObject login);
}
