package engine.definitions.moduledef;

import engine.IGame;
import engine.Positionable;
import engine.modules.DirectionalFirer;


/**
 * This class represents the definition of a directional firer, one that fired at a pre-specified
 * angle (where 0 is to the right)
 *
 */
public class DirectionalFirerDefinition extends FirerDefinition {

    private double myWaitTime;
    private double myAngle;
    private IGame myGame;

    public DirectionalFirerDefinition (IGame game) {
        super(game);
    }

    @Override
    public DirectionalFirer create (Positionable parent) {
        DirectionalFirer myNewFirer = new DirectionalFirer(myGame,
                                                           getProjectileDefinition(), parent,
                                                           myWaitTime, myAngle);
        setSuperVariables(myNewFirer);
        return myNewFirer;
    }

    public void setWaitTime (double time) {
        myWaitTime = time;
    }

    public double getWaitTime () {
        return myWaitTime;
    }

    public void setAngle (double theta) {
        myAngle = theta;
    }

    public double getAngle () {
        return myAngle;
    }
}
