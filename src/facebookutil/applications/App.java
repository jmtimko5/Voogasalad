package facebookutil.applications;

import java.util.List;
import facebookutil.user.IUser;
import facebookutil.user.profiles.SocialProfile;


/**
 * App that represents the actions an app can do. We are currently unsure
 * if we will support the custom post/high score board post yet because
 * it might require too much setup for the user.
 *
 * In general, the app can be authenticated separately from the user and can
 * do things like notify the people that play the game. Its login scheme should
 * be separate from a user login.
 *
 * @author Tommy
 *
 */
public abstract class App extends SocialProfile {

    public abstract void login ();

    public abstract void notifyUsers (List<IUser> users, String message);

    public abstract void challenge (IUser target, String message);

}
