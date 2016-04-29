package gameauthoring.creation.subforms.fire;

import java.util.List;
import engine.IGame;
import gameauthoring.creation.subforms.ClickAndFillView;
import gameauthoring.creation.subforms.ISubFormView;
import gameauthoring.creation.subforms.SubFormView;
import gameauthoring.util.BasicUIFactory;
import gameauthoring.util.UIFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * Implementation of IFiringSFVmult with button display
 * 
 * @author Joe Lilien
 *
 */
public class FiringSFV extends ClickAndFillView {

    private String defaultHelpMessage = "help";

    public FiringSFV (List<String> options) {
        super(options);
        initView();
        setDefaultHelpMessage(defaultHelpMessage );
        showDefaultMessage();
    }

    @Override
    public void addOrSetSFV (ISubFormView subFormView) {
        super.getMyPaneContent().getChildren().add(subFormView.draw());
    }
    
}
