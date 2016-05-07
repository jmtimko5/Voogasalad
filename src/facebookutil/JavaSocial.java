package facebookutil;

import java.util.ArrayList;
import java.util.List;
import facebookutil.applications.AppMap;
import facebookutil.login.LoginObject;
import facebookutil.login.LoginUser;
import facebookutil.scores.HighScoreBoard;
import facebookutil.user.Email;
import facebookutil.user.IUser;
import facebookutil.user.User;
import facebookutil.xstream.HighScoreReader;
import facebookutil.xstream.HighScoreWriter;
import facebookutil.xstream.UserReader;
import facebookutil.xstream.UserWriter;


/**
 * Class that implements the main java social interface.
 * Loads previous users into a list of current users and allows people
 * to log in, record scores, and post.
 *
 * @author Tommy
 *
 */
public class JavaSocial implements IJavaSocial {

    public static final String RESOURCE_FOLDER = "voogasalad/facebookutil/";
    private static final Email GUEST_EMAIL = new Email("guest", "guest.com");

    private static JavaSocial instance = null;

    private List<IUser> myUsers;
    private HighScoreBoard myHighScores;
    private IUser activeUser;
    private AppMap myApps;

    protected JavaSocial () {
        myUsers = loadUsers();
        myHighScores = new HighScoreReader().getBoard();
        myApps = new AppMap();
        myApps.loginApps();
        loginGuest();
    }

    public static JavaSocial getInstance () {
        if (instance == null) {
            instance = new JavaSocial();
        }
        return instance;
    }

    private void loginGuest () {
        IUser guest = createNewUser(GUEST_EMAIL);
        activeUser = guest;
    }

    /**
     * Loads users to list from the XML reader
     *
     * @return
     */
    private List<IUser> loadUsers () {
        UserReader reader = new UserReader();
        return reader.getUsers();
    }

    @Override
    public List<IUser> getUsers () {
        return new ArrayList<>(myUsers);
    }

    @Override
    public IUser getUserByEmail (Email email) {
        for (IUser user : myUsers) {
            if (user.getUserEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public HighScoreBoard getHighScoreBoard () {
        return myHighScores;
    }

    @Override
    public void loginUser (SocialType type, LoginObject login) {
        IUser user = getUserByEmail(login.getEmail());
        if (user == null) {
            user = createNewUser(login.getEmail());
        }
        else {
            System.out.println("User exists");
        }
        user.login(type, login);
        activeUser = user;
        saveState();
    }

    @Override
    public void loginUser (SocialType type) {
        LoginUser login = type.getLogin();
        login.authenticate(this);
    }

    @Override
    public IUser createNewUser (Email email) {
        if (getUserByEmail(email) == null) {
            System.out.println("Creating new User");
            IUser newUser = new User(email);
            myUsers.add(newUser);
            return newUser;
        }
        System.out.println("User already exists");
        return getUserByEmail(email);
    }

    /**
     * Gets the current user of the social environment
     *
     * @return
     */
    public IUser getActiveUser () {
        return activeUser;
    }

    /**
     * Gets the applications associated with the social app
     *
     * @return
     */
    public AppMap getApplications () {
        return myApps;
    }

    @Override
    public void saveState () {
        UserWriter writer = new UserWriter();
        writer.write(getUsers());
        HighScoreWriter scoreWriter = new HighScoreWriter();
        scoreWriter.write(myHighScores);
    }

}
