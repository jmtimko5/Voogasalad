package gameauthoring.creation.subforms.fire;

import engine.IGame;
import engine.definitions.FirerDefinition;
import engine.definitions.SpriteDefinition;
import engine.definitions.TrackingFirerDefinition;
import gameauthoring.creation.entryviews.IFormDataManager;
import gameauthoring.creation.subforms.ISubFormView;


/**
 * This class creates the controller to handle, manage, and interact with user data involving
 * tracking movement modules and projectiles
 * 
 * @author Dhrumil Timko Schreck Lilien
 *
 */

public class TrackingFireSFC extends RemovableSpriteSFC {

    private TrackingFirerSFV myView;
    private IFormDataManager myFormData;
    private IGame myGame;
    private double myDefaultWaitTime = 0;
    private TrackingFirerDefinition myFireDef = new TrackingFirerDefinition();

    public TrackingFireSFC (IGame game, FiringSFCmult sfc) {
        myView = new TrackingFirerSFV(game.getAuthorshipData(), e -> sfc.removeSFC(this));
        myFormData = myView.getData();
        myGame = game;
    }

    @Override
    public void initializeFields () {
        populateViewsWithData(myDefaultWaitTime);
    }

    private void populateViewsWithData (double wait) {
        myFormData.set(myView.getWaitTimeKey(), Double.toString(wait));
    }

    @Override
    public void updateItem (SpriteDefinition item) {
        setMySpriteDefinition(item);
        myFireDef.setGame(myGame);
        Double waitTime =
                Double.valueOf(myFormData.getValueProperty(myView.getWaitTimeKey()).get());
        myFireDef.setWaitTime(waitTime);
        myFireDef.setTargets(myView.getTargetsCoice().getSelected());
        myFireDef.setProjectileDefinition(myView.getSelectedMissile());
        if (!item.getModuleDefinitions().contains(myFireDef)) {
            item.addModule(myFireDef);
        }
    }

    @Override
    public ISubFormView getSubFormView () {
        return myView;
    }
    
    @Override
    public FirerDefinition getFirerDefinition(){
        return myFireDef;
    }

}
