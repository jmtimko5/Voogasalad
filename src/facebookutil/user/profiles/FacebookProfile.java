package facebookutil.user.profiles;

import facebookutil.actions.facebook.FacebookCustomPost;
import facebookutil.actions.facebook.FacebookHighScorePost;
import facebookutil.actions.facebook.FacebookScoreBoardPost;
import facebookutil.scores.HighScoreBoard;
import facebookutil.scores.ScoreOrder;
import facebookutil.user.IUser;


/**
 * Profile for Facebook users
 *
 * @author Tommy
 *
 */
public class FacebookProfile extends UserProfile {

    public FacebookProfile (String userID) {
        super(userID);
    }

    @Override
    public void customPost (String message) {
        FacebookCustomPost post = new FacebookCustomPost();
        post.createPost(message, this);
        post.send(getLogin());
    }

    @Override
    public void highScorePost (HighScoreBoard board,
                               String gameName,
                               IUser user,
                               ScoreOrder order) {
        FacebookHighScorePost post = new FacebookHighScorePost();
        post.createPost(board, user, gameName, order);
        post.send(getLogin());
    }

    @Override
    public void highScoreBoardPost (HighScoreBoard board,
                                    String gameName,
                                    ScoreOrder order) {
        FacebookScoreBoardPost post = new FacebookScoreBoardPost();
        post.createBoardPost(board, gameName, order, this);
        post.send(getLogin());
    }

}
