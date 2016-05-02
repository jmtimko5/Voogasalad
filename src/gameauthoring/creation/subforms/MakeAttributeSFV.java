package gameauthoring.creation.subforms;

import java.util.ResourceBundle;
import splash.LocaleManager;
import gameauthoring.creation.entryviews.NumberEntryView;
import gameauthoring.tabs.AuthoringView;
import javafx.scene.Node;
import javafx.scene.layout.HBox;


/**
 * Implementation of IMakeAttributeSFV with GridPane Arrangement and NumberEntryView display
 * 
 * @author Jin An
 * @author Joe Lilien
 *
 */
public class MakeAttributeSFV extends SubFormView implements IMakeAttributeSFV {

    private ResourceBundle myLabel = ResourceBundle.getBundle("languages/labels", LocaleManager
            .getInstance().getCurrentLocaleProperty().get());
    private String myStartingValueLabel = myLabel.getString("StartingValue");

    private NumberEntryView myStartingValue;

    public MakeAttributeSFV () {
        myStartingValue =
                new NumberEntryView(myStartingValueLabel, 100, 30, AuthoringView.DEFAULT_ENTRYVIEW);
        initView();
    }

    @Override
    protected void initView () {
    }

    @Override
    public double getStartingValue () {
        return myStartingValue.getData();
    }

    @Override
    public void populateWithData (double value) {
        myStartingValue.setData(value);
    }

    @Override
    public Node draw () {
        HBox box = new HBox(myStartingValue.draw());
        getMyUIFactory().addStyling(box, getStyleClass());
        return box;
    }
}
