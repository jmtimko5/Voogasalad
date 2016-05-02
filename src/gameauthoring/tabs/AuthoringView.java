package gameauthoring.tabs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import com.dooapp.xstreamfx.FXConverters;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import engine.IGame;
import gameauthoring.creation.factories.TabViewFactory;
import gameauthoring.listdisplay.GameConditionViewer;
import gameauthoring.util.BasicUIFactory;
import gameauthoring.util.ErrorMessage;
import gameauthoring.waves.WaveTabViewer;
import gameplayer.GamePlayer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import serialize.GameReader;
import serialize.GameWriter;
import serialize.LoadErrorException;
import splash.LocaleManager;
import splash.MainUserInterface;


/**
 * Highest hierarchy class for authoring environment. Used composition for each tab viewers and
 * gameFactory. It has Home and Save image buttons for users to easily navigate to home and save the
 * game as XML. It contains "game information", "create objects", and "build scene" tabs. These are
 * divided in order for the users to easily create their own game.
 * 
 * 
 * @author Jin An
 * @author Dhrumil
 *
 */

public class AuthoringView implements IAuthoringView {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final String STYLESHEET = "custom.css";
    public static final String DEFAULT_RESOURCE_PACKAGE = "defaults/";
    public static final String DEFAULT_ENTRYVIEW = "defaultTextEntry";
    private BasicUIFactory myUIFactory;
    public static final String HOME = "Home";
    public static final String SAVE = "Save";
    private TabViewFactory<ITabViewer> myTabFactory;
    private List<ITabViewer> myTabViewers;
    private BorderPane myLayout;
    private IGame myGame;
    private Stage myStage;
    private ResourceBundle myLabel;

    public AuthoringView () {
        
        GameFactory gameFactory = new GameFactory();
        myGame = gameFactory.createGame();
        createView(myGame);
    }

    private void setResourceBundle () {
        myLabel = ResourceBundle.getBundle("languages/labels", LocaleManager.getInstance()
                .getCurrentLocaleProperty().get());
    }

    public AuthoringView (IGame game) {
        myGame = game;
        createView(myGame);
    }
    
    private void createView (IGame game) {
        setResourceBundle();
        myTabFactory = new TabViewFactory<ITabViewer>(myGame);
        myTabViewers = myTabFactory.createTabViewers();
    }

    @Override
    public void init (Stage s) {
        myStage = s;
        myLayout = new BorderPane();
        myLayout.setCenter(createContents());
        myLayout.setTop(createMenuBar());
        Scene scene = new Scene(myLayout, WIDTH, HEIGHT);
        scene.getStylesheets().add(DEFAULT_RESOURCE_PACKAGE + STYLESHEET);
        s.setScene(scene);
        initListeners(s);
        rescale(s.getWidth(), s.getHeight());
    }

    private void initListeners (Stage stage) {
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed (ObservableValue<? extends Number> observableValue,
                                 Number oldSceneWidth,
                                 Number newSceneWidth) {
                rescale(newSceneWidth.doubleValue(), stage.getHeight());
            }
        });

        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed (ObservableValue<? extends Number> observableValue,
                                 Number oldSceneHeight,
                                 Number newSceneHeight) {
                rescale(stage.getWidth(), newSceneHeight.doubleValue());
            }
        });

    }

    private void rescale (double width, double height) {
        myTabViewers.forEach(tab -> tab.rescale(width, height));
    }

    private MenuBar createMenuBar () {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu(myLabel.getString("File"));
        MenuItem goHome = createMenuItems(myLabel.getString("GoHome"), e -> goHome());
        MenuItem saveItem = createMenuItems(myLabel.getString("Save"), e -> saveToXML());
        MenuItem loadItem = createMenuItems(myLabel.getString("Load"), e -> loadGame());
        MenuItem launchItem = createMenuItems(myLabel.getString("Launch"), e -> launchGame());
        fileMenu.getItems().add(goHome);
        fileMenu.getItems().add(saveItem);
        fileMenu.getItems().add(loadItem);
        fileMenu.getItems().add(launchItem);
        menuBar.getMenus().add(fileMenu);
        return menuBar;
    }

    private MenuItem createMenuItems (String itemName, EventHandler<ActionEvent> action) {
        MenuItem newMenuItem = new MenuItem(itemName);
        newMenuItem.setOnAction(action);
        return newMenuItem;
    }

    private Node createContents () {
        TabPane tabPane = createAllTabs();
        GridPane contents = new GridPane();
        contents.add(tabPane, 0, 2);
        return contents;
    }

    private void goHome () {
        new MainUserInterface().init(myStage);
    }

    private void saveToXML () {
        File f = saveFile();
        myGame.createAndSortGlobals();
        if (f != null) {
            try {
                new GameWriter().serialize(f, myGame);
            }
            catch (IOException e) {
                ErrorMessage message = new ErrorMessage(e.getMessage());
                message.showError();
            }
        }

    }

    private void loadGame () {
        File f = getFile();
        try {
            if (f != null) {
                IGame loadedGame = new GameReader().readFile(f);
                AuthoringView aView = new AuthoringView(loadedGame);
                Stage authoringStage = new Stage();
                aView.init(authoringStage);
                authoringStage.show();
            }
        }
        catch (LoadErrorException e) {
            ErrorMessage message = new ErrorMessage(e.getMessage());
            message.showError();
        }
    }

    private void launchGame () {
        XStream xstream = new XStream(new DomDriver());
        FXConverters.configure(xstream);
        xstream.setMode(XStream.SINGLE_NODE_XPATH_RELATIVE_REFERENCES);
        myGame.createAndSortGlobals();
        String xml = xstream.toXML(myGame);

        IGame game = (IGame) xstream.fromXML(xml);
        GamePlayer player = new GamePlayer(game);
    }

    private File saveFile () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(myLabel.getString("SaveGame"));
        fileChooser.setInitialDirectory(new File("resources/saved_games"));
        return fileChooser.showSaveDialog(new Stage());
    }

    private File getFile () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(myLabel.getString("Load"));
        fileChooser.setInitialDirectory(new File("resources/saved_games"));
        return fileChooser.showOpenDialog(new Stage());
    }

    private TabPane createAllTabs () {
        TabPane tabpane = new TabPane();
        tabpane.getStyleClass().add("authoringTabs");
        //myTabFactory = new TabViewFactory<ITabViewer>(myGame);
        //myTabViewers = myTabFactory.createTabViewers();
        tabpane.getTabs().addAll(myTabFactory.createTabs());
        return tabpane;
    }

    private IGame getMyGame () {
        return myGame;
    }
}
