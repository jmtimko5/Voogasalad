package gameplayer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.ResourceBundle;
import facebookutil.JavaSocial;
import facebookutil.SocialType;
import gameauthoring.tabs.AuthoringView;
import gameplayer.facebook.FacebookMenu;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import util.StringParser;


/**
 * Displays the tools for the user during game play
 *
 * @author RyanStPierre
 *
 */

public class GamePlayerTools {

    private static final String TOOL_PATH = "defaults/tool_bar";
    private static final String TOOL_PATH_SIZES = "defaults/tool_bar_sizes";
    ResourceBundle myToolButtons = ResourceBundle.getBundle(TOOL_PATH);
    ResourceBundle mySizes = ResourceBundle.getBundle(TOOL_PATH_SIZES);
    ToolBar myTools = new ToolBar();
    IGameEngine myEngine;

    public GamePlayerTools (IGameEngine engine) {
        myEngine = engine;
        init();
    }

    /**
     * Reflectively generates the tool bar given information in the resource bundle that
     * map method call names to image URL's
     */

    private void init () {
        Enumeration<String> keys = myToolButtons.getKeys();
        while (keys.hasMoreElements()) {
            String next = keys.nextElement();
            myTools.getItems().add(createButton(next,
                                                myToolButtons.getString(next)));
        }

    }

    private Node createButton (String key, String url) {

        Button button = new Button();
        Method method;
        try {
            method = this.getClass().getMethod(key);
            button.setOnMouseClicked(event -> callMethod(method));

        }
        catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        button.setGraphic(getImage(url));
        return button;

    }

    private void callMethod (Method method) {
        try {
            method.invoke(this);
        }

        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();

        }

    }

    private ImageView getImage (String url) {
        StringParser parser = new StringParser();
        ImageView image = new ImageView(url);
        image.setFitWidth(parser.parseDouble(mySizes.getString("Width")));
        image.setFitHeight(parser.parseDouble(mySizes.getString("Height")));

        return image;
    }

    /**
     * Following methods public for reflection
     */
    public void play () {
        myEngine.play();
    }

    public void pause () {
        myEngine.pause();
    }

    public void postToFacebook () {
        if (JavaSocial.getInstance().getActiveUser().getProfiles()
                .getProfileByType(SocialType.FACEBOOK) == null) {
            JavaSocial.getInstance().loginUser(SocialType.FACEBOOK);
        }
        else {
            FacebookMenu menu = new FacebookMenu(myEngine.getGame());
            menu.popUp();
        }
    }

    public void launchAuthoring () {
        pause();
        AuthoringView aView = new AuthoringView(myEngine.getGame());
        Stage authoringStage = new Stage();
        aView.init(authoringStage);
        authoringStage.show();
    }

    public Node draw () {
        return myTools;
    }

    public double getHeight () {
        return myTools.getHeight();
    }

}
