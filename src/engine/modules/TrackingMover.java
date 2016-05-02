package engine.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import engine.Attribute;
import engine.AttributeType;
import engine.IAttribute;
import engine.IGame;
import engine.Positionable;
import engine.SpriteGroup;
import engine.interactionevents.KeyIOEvent;
import engine.interactionevents.MouseIOEvent;
import engine.sprite.ISprite;
import engine.sprite.SpriteType;
import util.TimeDuration;


/**
 * This class builds a module that follows the closest enemy
 *
 * @author Dhrumil Timko
 * 
 */
public class TrackingMover extends Mover {

    private List<SpriteType> myEnemyList;
    private Positionable mySprite;
    private EnemyTracker myTracker;
    private IGame myGame;

    public TrackingMover (double speed,
                          IGame game,
                          SpriteGroup spriteGroup,
                          Positionable sprite) {
        super(sprite);
        myGame = game;
        setSpeed(speed);
        myEnemyList = spriteGroup.getSpriteTypes();
        mySprite = sprite;
        myTracker = new EnemyTracker();
    }

    @Override
    public void update (TimeDuration duration) {
        if(myPotentialTargets().size() != 0){
            setOrientationFromTracker(myTracker
                                      .calculateOrientationToClosestEnemy(mySprite.getLocation(), myPotentialTargets()));
        }
        
        move(duration);
    }

    private List<ISprite> myPotentialTargets () {

        return myGame.getLevelManager().getCurrentLevel().getSprites().stream()
                .filter(sprite -> myEnemyList.contains(sprite.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public void registerKeyEvent (KeyIOEvent keyEvent) {

    }

    @Override
    public void registerMouseEvent (MouseIOEvent mouseEvent) {
    }

    @Override
    protected List<IAttribute> getSpecificAttributes () {
        List<IAttribute> myList = new ArrayList<IAttribute>();
        return myList;

    }

    @Override
    public int getNextIndex () {
       
        return 0;
    }

    @Override
    public void setNextIndex (int index) {
       
        
    }
}
