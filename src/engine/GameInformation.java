package engine;

import graphics.ImageGraphic;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Stores the info of the Game
 *
 * @author Joe Timko
 * @author Dhrumil Patel
 * @author David Maydew
 * @author Ryan St.Pierre
 * @author Jonathan Im
 * @author Jin An
 */

public class GameInformation implements IGameInformation {

    private static final int DEF_IMAGE_WIDTH = 200;
    private static final int DEF_IMAGE_HEIGHT = 300;
    private static final String DEF_IMAGE_LOCATION = "/images/tower.png";
    private StringProperty myTitle;
    private StringProperty myAuthor;
    private StringProperty myDateCreated;
    private ImageGraphic mySplashScreen;

    public GameInformation () {
        myTitle = new SimpleStringProperty();
        myAuthor = new SimpleStringProperty();
        myDateCreated = new SimpleStringProperty();
        mySplashScreen = new ImageGraphic(DEF_IMAGE_WIDTH, DEF_IMAGE_HEIGHT, DEF_IMAGE_LOCATION);
    }

    @Override
    public StringProperty getNameProperty () {
        return myTitle;
    }

    @Override
    public StringProperty getAuthorProperty () {
        return myAuthor;
    }

    @Override
    public StringProperty getDateCreatedProperty () {
        return myDateCreated;
    }

    @Override
    public String getSplashScreenURL () {
        return mySplashScreen.getUrlProperty().get();
    }

    @Override
    public StringProperty getSplashScreenURLProperty () {
        return mySplashScreen.getUrlProperty();
    }

    @Override
    public void setAuthor (String author) {
        myAuthor.setValue(author);
    }

    @Override
    public void setDateCreated (String date) {
        myDateCreated.setValue(date);
    }

    @Override
    public void setName (String name) {
        myTitle.setValue(name);
    }

    @Override
    public void setSplashScreen (String url) {
        mySplashScreen = new ImageGraphic(0, 0, url);
    }

}
