package gameauthoring.creation.subforms.fire;

import engine.IGame;
import engine.definitions.concrete.SpriteDefinition;
import engine.definitions.moduledef.FirerDefinition;
import engine.definitions.moduledef.UserFirerDefinition;
import gameauthoring.creation.subforms.ISubFormView;


/**
 *
 * @author josephtimko1
 *
 */
public class UserFireSFC extends RemovableFireSFC {

    private IUserFireSFV myView;
    private IGame myGame;
    private UserFirerDefinition myFireDef;
    private double myDefaultAngle;
    private double myDefaultWaitTime;
    private double myDefaultRange;
    private boolean myDefaultIsRanged;
    private double myDefaultAngleStep;
    private String myDefaultIncrease = "I";
    private String myDefaultDecrease = "D";
    private String myDefaultFire = "F";

    public UserFireSFC (IGame game, FiringSFC sfc) {
        super(sfc);
        init(game, new UserFirerDefinition(game));
        initializeFields();

    }

    public UserFireSFC (IGame game,
                        FiringSFC myFiringSFC,
                        UserFirerDefinition userFirerDefinition) {
        super(myFiringSFC);
        init(game, userFirerDefinition);
    }

    @Override
    public void updateItem (SpriteDefinition item) {
        setMySpriteDefinition(item);
        myFireDef.setGame(myGame);
        myFireDef.setAngle(myView.getMyAngle());
        myFireDef.setAngleStep(myView.getMyAngleStep());
        myFireDef.setRanged(myView.getMyIsRanged());
        myFireDef.setFireRange(myView.getMyRange());
        myFireDef.setProjectileDefinition(myView.getMissileSelection());
        myFireDef.setDecrease(myView.getMyDecrease());
        myFireDef.setIncrease(myView.getMyIncrease());
        myFireDef.setFire(myView.getMyFire());
        myFireDef.setWaitTime(myView.getMyWaitTime());

        item.addModule(myFireDef);

    }

    private void init (IGame game, UserFirerDefinition userFirerDefinition) {
        myGame = game;
        myFireDef = userFirerDefinition;
        myView = new UserFireSFV(game.getAuthorshipData(), getRemoveMenu());
        initializeFields();

    }

    @Override
    public void initializeFields () {
        myView.populateWithData(null, myDefaultWaitTime, myDefaultRange, myDefaultIsRanged,
                                myDefaultAngle, myDefaultAngleStep, myDefaultIncrease,
                                myDefaultDecrease, myDefaultFire);
    }

    @Override
    public ISubFormView getSubFormView () {
        return myView;
    }

    @Override
    public void populateViewsWithData (SpriteDefinition item) {
        double toDegrees = getParser().parseDouble(getMyNumbers().getString("ToDegrees"));
        myView.populateWithData(myFireDef.getProjectileDefinition(), myFireDef.getWaitTime(),
                                myFireDef.getFireRange(), myFireDef.getRanged(),
                                myFireDef.getAngle() * toDegrees,
                                myFireDef.getAngleStep(),
                                myFireDef.getIncrease(),
                                myFireDef.getDecrease(),
                                myFireDef.getFire());
    }

    @Override
    public FirerDefinition getModuleDefinition () {
        return myFireDef;
    }

}
