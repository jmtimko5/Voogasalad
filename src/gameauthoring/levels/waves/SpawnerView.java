package gameauthoring.levels.waves;

import java.util.ResourceBundle;
import engine.IAdder;
import engine.IGame;
import engine.ILevel;
import engine.definitions.spawnerdef.SpawnerDefinition;
import engine.definitions.spawnerdef.SpawnerModuleDefinition;
import engine.definitions.spawnerdef.WaveDefinition;
import engine.profile.Profile;
import engine.rendering.AuthoringRenderer;
import gameauthoring.creation.cellviews.NameCellView;
import gameauthoring.levels.sprites.Draggable;
import gameauthoring.util.BasicUIFactory;
import gameauthoring.util.Glyph;
import gameauthoring.util.UIFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import splash.LocaleManager;
import util.Coordinate;
import util.ScaleRatio;
import util.StringParser;


/**
 * Responsible for showing the spawner image, the gap time, and associated waves
 * Draggable so the spawner can be dragged onto the level
 *
 * @author RyanStPierre
 * @author TommyRomano
 */
public class SpawnerView implements Glyph, Draggable {

    private static final String DRAG_STRING = "Spawner";
    private static final String IMAGE_SIZE = "ImageSize";
    private static final String EMPTY = "";
    private static final double SIZE = 80;

    private ResourceBundle myLang = ResourceBundle.getBundle("languages/labels", LocaleManager
            .getInstance().getCurrentLocaleProperty().get());
    private ResourceBundle myBundle = ResourceBundle.getBundle("defaults/spawner_view");
    private ResourceBundle myStyle = ResourceBundle.getBundle("defaults/styling_class");

    private HBox myPane = new HBox();
    private UIFactory myFactory = new BasicUIFactory();
    private ListView<WaveDefinition> myWaves;
    private ObservableList<WaveDefinition> myBacking;
    private SpawnerDefinition mySpawner;
    private ILevel myLevel;
    private IGame myGame;
    private TextField myDelay;
    private Button mySetButton;
    private AuthoringRenderer myRenderer;
    private ScaleRatio myScale;

    public SpawnerView (IGame game, ILevel level, AuthoringRenderer renderer, ScaleRatio scale) {
        init();
        myScale = scale;
        myLevel = level;
        myGame = game;
        myRenderer = renderer;
        new SpawnerViewController(this, level);
    }

    private void init () {
        initWaves();
        myPane.getChildren().add(getLeft());
        myPane.getChildren().add(myWaves);
        myPane.getStyleClass().add(myStyle.getString("Bordered"));
    }

    private void initWaves () {
        myBacking = FXCollections.observableArrayList();
        myWaves = new ListView<>(myBacking);
        myWaves.setPlaceholder(myFactory.createLabel(myLang.getString("EmptyWavesPrompt")));
        myWaves.getStyleClass().add(myStyle.getString("GreenList"));
        myWaves.setCellFactory(c -> new NameCellView<WaveDefinition>());
        setSize();
    }

    private void setSize () {
        double width = Double.parseDouble(myBundle.getString("ListWidth"));
        myWaves.setMinWidth(width);
        myWaves.setMaxWidth(width);
    }

    private Node getLeft () {
        VBox vbox = new VBox(Double.parseDouble(myBundle.getString("VSpacing")));
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().add(getView());
        vbox.getChildren().add(getGap());
        vbox.getChildren().add(myFactory.createButton(myLang.getString("Clear"), e -> reset()));
        return vbox;
    }

    private Node getGap () {
        VBox vbox = new VBox();
        myDelay = myFactory.createTextField(myLang.getString("GapPrompt"),
                                            Double.parseDouble(myBundle.getString("TextWidth")));
        mySetButton = myFactory.createButton(myLang.getString("Set"));
        vbox.getChildren().addAll(myDelay, mySetButton);
        return vbox;
    }

    private Node getView () {
        ImageView view = new ImageView(getImage());
        view.setOnDragDetected(e -> setOnDragDetected(e, view));
        view.setFitWidth(Double.parseDouble(myBundle.getString(IMAGE_SIZE)));
        view.setFitHeight(Double.parseDouble(myBundle.getString(IMAGE_SIZE)));
        return view;
    }

    private Image getImage () {
        return new Image(getImageURL());
    }

    private String getImageURL () {
        return myBundle.getString("ImageURL");
    }

    @Override
    public Node draw () {
        return myPane;
    }

    public void add (WaveDefinition wave) {
        myWaves.getItems().add(wave);
    }

    public double getDelay () throws NumberFormatException {
        return new StringParser().parseDouble(myDelay.getText());
    }

    private void reset () {
        myBacking.clear();
        myDelay.clear();
    }

    @Override
    public void setOnDragDetected (MouseEvent e, Node node) {
        createSpawner();
        Dragboard db = node.startDragAndDrop(TransferMode.COPY);
        db.setContent(createClipboard(DRAG_STRING));
        db.setDragView(getImage());
        myRenderer.getPane().setOnDragOver(event -> setOnDragOver(event));
        myRenderer.getPane().setOnDragDropped(event -> setOnDragDropped(event));
    }

    /**
     * Creates the spawner to be put in the level
     */
    private void createSpawner () {
        try {
            SpawnerModuleDefinition spawnerDef =
                    new SpawnerModuleDefinition(getAdder(), myLevel, myWaves.getItems());
            mySpawner = new SpawnerDefinition(myGame);
            mySpawner.setProfile(new Profile(DRAG_STRING, EMPTY, getImageURL(), SIZE, SIZE));
            mySpawner.setMySpawningModule(spawnerDef);
        }
        catch (NumberFormatException e) {
            return;
        }
    }

    private IAdder getAdder () {
        return myLevel;
    }

    @Override
    public void setOnDragOver (DragEvent e) {
        e.acceptTransferModes(TransferMode.COPY);
    }

    @Override
    public void setOnDragDropped (DragEvent e) {
        // mySpawner.setProfile(new Profile());
        myLevel.add(mySpawner.create(), getCoordinate(e.getX(), e.getY()));
        myRenderer.render();
        reset();
    }

    private Coordinate getCoordinate (double x, double y) {
        Coordinate c = new Coordinate(myScale.invert(x), myScale.invert(y));
        return c;
    }

    public void setButtonAction (EventHandler<MouseEvent> event) {
        mySetButton.setOnMouseClicked(event);
    }

}
