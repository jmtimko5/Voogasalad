package engine.modules;

import engine.IStatus;
import engine.effects.DefaultAffectable;
import engine.events.EventType;
import engine.events.GameEvent;


/**
 * This class implements IStatusModule and handles the status of a sprite over the course of a level
 * and game
 *
 * @author Dhrumil
 *
 */
public class SpriteStatus extends DefaultAffectable implements IStatus {

    private boolean myIsDead;
    private boolean myIsGoal;
    private boolean myDoesObstruct;

    @Override
    public void registerEvent (GameEvent event) {
        if (event.getEventType().equals(EventType.DEATH)) {
            remove();
        }
    }

    /**
     * @return whether this sprite should be removed in the next update cycle because it has
     *         received a death event
     */
    @Override
    public boolean shouldBeRemoved () {
        return myIsDead;
    }

    @Override
    public void remove () {
        myIsDead = true;
    }

    @Override
    public boolean doesObstruct () {
        return myDoesObstruct;
    }

    @Override
    public void setObstruction (boolean value) {
        myDoesObstruct = value;

    }

    @Override
    public boolean isGoal () {
        return myIsGoal;
    }

    @Override
    public void setIsGoal (boolean value) {
        myIsGoal = value;
    }

}
