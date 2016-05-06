package gameauthoring.creation.subforms.events;

import java.util.ResourceBundle;
import engine.Attribute;
import engine.AttributeType;
import engine.IGame;
import engine.definitions.concrete.AttributeDefinition;
import engine.definitions.concrete.EventPackageDefinition;
import engine.effects.Effect;
import engine.effects.EffectFactory;
import engine.effects.IncreaseEffect;
import engine.profile.ProfileDisplay;
import gameauthoring.creation.subforms.ISubFormView;
import gameauthoring.creation.subforms.fire.RemovableEffectSFC;
import gameauthoring.util.ErrorMessage;
import javafx.collections.ObservableList;


/**
 * Allows for user to create any number of effects to add to an events package
 *
 * @author Joe Lilien
 *
 */
public class EffectSFC extends RemovableEffectSFC {

    private IGame myGame;
    private EffectSFV myView;
    private Effect myEffect;
    private TypeFactory myTypeFactory = new TypeFactory();
    private String myDefaultAttributeType = "length";
    private ResourceBundle myEffects = ResourceBundle.getBundle("defaults/effect_types");
    private double myDefaultLength;
    private double myDefaultValue;

    public EffectSFC (IGame game, EffectChoiceSFC sfc) {
        super(sfc);
        init(game, new IncreaseEffect(null, null, null));
        initializeFields();

    }

    public EffectSFC (IGame game, EffectChoiceSFC sfc, Effect effect) {
        super(sfc);
        init(game, effect);

    }

    private void init (IGame game, Effect effect) {
        myGame = game;
        myEffect = effect;
        myView = new EffectSFV(myGame.getAuthorshipData(), getEffects(), getRemoveMenu());
    }

    private ObservableList<ProfileDisplay> getEffects () {
        return myTypeFactory.getEffectTypes(myEffects);
    }

    @Override
    public void updateItem (EventPackageDefinition item) {
        try {
            AttributeDefinition attrDef = myView.getAttribute();
            Attribute lengthAttr =
                    new Attribute(myView.getLength(), new AttributeType(myDefaultAttributeType));
            double val = myView.getValue();
            myEffect = getEffect(myView.getEffectType(), lengthAttr, attrDef, val);
            item.getMyEffectsList().add(myEffect);
        }
        catch (NullPointerException e) {
            ErrorMessage err =
                    new ErrorMessage(getMyLabels().getString("IncompleteEffect"));
            err.showError();
        }
    }

    private Effect getEffect (String effectType,
                              Attribute length,
                              AttributeDefinition def,
                              double val) {
        return new EffectFactory().getEffect(effectType, length, def, val);
    }

    @Override
    public void initializeFields () {
        myView.populateWithData(null, null, myDefaultValue, myDefaultLength);
    }

    @Override
    public ISubFormView getSubFormView () {
        return myView;
    }

    @Override
    public void populateViewsWithData (EventPackageDefinition item) {
        String type = myTypeFactory.getEffectType(myEffect);
        AttributeDefinition definition = myEffect.getAttributeDefinition();
        double value = myEffect.getAlteringAttribute().getValueProperty().get();
        double length = myEffect.getEffectLengthAttribute().getValueProperty().get();
        myView.populateWithData(type, definition, value, length);
    }

    @Override
    public Effect getModuleDefinition () {
        return myEffect;
    }

}
