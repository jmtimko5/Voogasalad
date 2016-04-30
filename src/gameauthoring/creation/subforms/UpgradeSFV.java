package gameauthoring.creation.subforms;

import java.util.ResourceBundle;
import splash.LocaleManager;
import engine.AuthorshipData;
import engine.definitions.concrete.AttributeDefinition;
import engine.definitions.concrete.SpriteDefinition;
import gameauthoring.creation.entryviews.CheckEntryView;
import gameauthoring.creation.entryviews.NumberEntryView;
import gameauthoring.creation.entryviews.SingleChoiceEntryView;
import gameauthoring.shareddata.DefinitionCollection;
import gameauthoring.tabs.AuthoringView;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;


/**
 * Allow users to define upgrades for sprites
 * 
 * @author Joe Lilien
 *
 */
public class UpgradeSFV extends SubFormView implements IUpgradeSFV {

    private ResourceBundle myLabel;
    private String myUpgradeChoicesKey;
    private String myUpgradableKey;
    private String myGlobalKey;
    private String myAttributeChoicesKey;
    private String myCostKey;
    private TitledPane myContainer;
    private SingleChoiceEntryView<SpriteDefinition> myUpgradeChoices;
    private SingleChoiceEntryView<AttributeDefinition> myAttributeChoices;
    private CheckEntryView myIsGlobalResource;
    private NumberEntryView myCost;
    private GridPane myPane;

    public UpgradeSFV (AuthorshipData data, DefinitionCollection<SpriteDefinition> nextUpgrades) {
        double width = getParser().parseDouble(getMyNumbers().getString("UpgradeWidth"));
        double height = getParser().parseDouble(getMyNumbers().getString("UpgradeHeight"));
        setResourceBundleAndKey();

        myUpgradeChoices =
                new SingleChoiceEntryView<SpriteDefinition>(myUpgradeChoicesKey,
                                                            nextUpgrades.getItems(),
                                                            AuthoringView.DEFAULT_ENTRYVIEW);
        myAttributeChoices =
                new SingleChoiceEntryView<AttributeDefinition>(myAttributeChoicesKey,
                                                               data.getMyCreatedAttributes()
                                                                       .getItems(),
                                                               AuthoringView.DEFAULT_ENTRYVIEW);
        myIsGlobalResource = new CheckEntryView(myGlobalKey, AuthoringView.DEFAULT_ENTRYVIEW);
        myCost = new NumberEntryView(myCostKey, width, height, AuthoringView.DEFAULT_ENTRYVIEW);
        initView();
        initBinding(data);

    }

    private void setResourceBundleAndKey () {
        myLabel = ResourceBundle.getBundle("languages/labels", LocaleManager
                .getInstance().getCurrentLocaleProperty().get());
        myUpgradeChoicesKey = myLabel.getString("UpgradeChoicesKey");
        myUpgradableKey = myLabel.getString("UpgradableKey");
        myGlobalKey = myLabel.getString("GlobalKey");
        myAttributeChoicesKey = myLabel.getString("AttributeChoicesKey");
        myCostKey = myLabel.getString("CostKey");
    }

    @Override
    protected void initView () {
        myPane = new GridPane();
        myPane.add(myIsGlobalResource.draw(), 0, 0);
        myPane.add(myAttributeChoices.draw(), 1, 0);
        myPane.add(myCost.draw(), 2, 0);
        myPane.add(myUpgradeChoices.draw(), 3, 0);
        myContainer = getMyUIFactory().makeCheckBoxTitledPane(myUpgradableKey, myPane, false);
    }

    private void initBinding (AuthorshipData data) {
        myIsGlobalResource.isCheckedProperty()
                .addListener(c -> updateAttributeChoices(data,
                                                         myIsGlobalResource.isCheckedProperty()));
    }

    private void updateAttributeChoices (AuthorshipData data, BooleanProperty isGlobal) {
        if (isGlobal.get()) {
            myAttributeChoices.setItems(data.getMyCreatedGlobals().getItems());
        }
        else {
            myAttributeChoices.setItems(data.getMyCreatedAttributes().getItems());
        }
    }

    @Override
    public SpriteDefinition getNextUpgrade () {
        return this.myUpgradeChoices.getSelected();
    }

    @Override
    public AttributeDefinition getDepletedAttribute () {
        return this.myAttributeChoices.getSelected();
    }

    @Override
    public double getMyCost () {
        return myCost.getData();
    }

    @Override
    public Node draw () {
        getMyUIFactory().addStyling(myContainer, getStyleClass());
        return myContainer;
    }

    @Override
    public BooleanProperty isUpgradableProperty () {
        return this.myContainer.expandedProperty();
    }

    @Override
    public BooleanProperty isGlobalProperty () {
        return this.myIsGlobalResource.isCheckedProperty();
    }

    @Override
    public void populateWithData (boolean isUpgradable,
                                  SpriteDefinition nextUpgrade,
                                  AttributeDefinition depletedAttribute,
                                  double cost) {
        this.myContainer.setExpanded(isUpgradable);
        myUpgradeChoices.setSelected(nextUpgrade);
        myAttributeChoices.setSelected(depletedAttribute);
        myCost.setData(cost);
    }

}
