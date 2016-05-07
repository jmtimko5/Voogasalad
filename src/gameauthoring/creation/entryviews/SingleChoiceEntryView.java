package gameauthoring.creation.entryviews;

import java.util.function.Consumer;
import engine.profile.IProfilable;
import gameauthoring.creation.cellviews.NameCellView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;


/**
 * Allows for Single choice from list of given choices associated with Label given as constructor
 * Arguement
 *
 *
 * @author JoeLilien
 *
 */
public class SingleChoiceEntryView<E extends IProfilable> extends EntryView {
    private ComboBox<E> myChoices;
    private String myComboBoxStyle = "SingleChoice";

    public SingleChoiceEntryView (String label, ObservableList<E> observableList, String cssClass) {
        super(label, cssClass);
        this.myChoices = new ComboBox<E>(observableList);
        myChoices.setCellFactory(c -> new NameCellView<E>());
        myChoices.setButtonCell(new NameCellView<E>());
        getMyFactory().addStyling(myChoices, myComboBoxStyle);
        init();
    }

    @Override
    protected void init () {
        getMyContainer().getChildren().add(myChoices);
    }

    public void setItems (ObservableList<E> items) {
        myChoices.setItems(items);
    }

    public void setSelected (E item) {
        myChoices.getSelectionModel().select(item);
    }

    public void setSelected (int index) {
        myChoices.getSelectionModel().select(0);
    }

    public void clearSelection () {
        myChoices.getSelectionModel().clearSelection();
    }

    public E getSelected () {
        return myChoices.getSelectionModel().getSelectedItem();
    }

    public void addComboItemListener (Consumer<E> action) {
        myChoices.setOnAction(e -> action.accept(myChoices.getSelectionModel().getSelectedItem()));

    }

    @Override
    public Node draw () {
        return getMyContainer();
    }

    public ObservableList<E> getItems () {
        return myChoices.getItems();
    }

}
