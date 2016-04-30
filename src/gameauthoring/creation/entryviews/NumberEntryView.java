package gameauthoring.creation.entryviews;

import java.util.ResourceBundle;
import gameauthoring.util.ErrorMessage;
import javafx.scene.control.TextInputControl;
import splash.LocaleManager;


/**
 * Allows for Number Data Entry Associated with Label given as constructor Arguement
 * 
 * @author JoeLilien
 *
 */
public class NumberEntryView extends InputEntryView {

    private TextInputControl myTextInput;
    private ResourceBundle myErrors;

    public NumberEntryView (String label, double width, double height, String cssClass) {
        super(label, width, height, cssClass);
        myTextInput = new NumberTextField();
        super.setInputControl(myTextInput);
        myErrors =   ResourceBundle
                .getBundle("defaults/errors");
        super.init();
    }

    public void setData (double data) {
        myTextInput.setText(String.valueOf(data));
    }

    public double getData () {
        try {
            return Double.parseDouble(myTextInput.getText());
        }
        catch (NumberFormatException e) {
            ErrorMessage err = new ErrorMessage(myErrors.getString("NeedNumber"));
            err.showError();
            return 0;
        }
    }

}
