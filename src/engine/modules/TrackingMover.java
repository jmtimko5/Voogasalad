package engine.modules;

import java.util.List;
import java.util.Optional;
import engine.Attribute;
import engine.AttributeType;
import engine.IAttribute;
import engine.IPositionable;
import engine.interactionevents.KeyIOEvent;
import engine.interactionevents.MouseIOEvent;
import engine.sprite.ISprite;
import util.TimeDuration;


/**
 * This class builds a module that follows the closest enemy
 * 
 * @author Dhrumil
 *
 */
public class TrackingMover extends Mover {

    private IAttribute mySpeed;
    private List<ISprite> myEnemyList;
    private ISprite myEnemy;
    private IPositionable mySprite;
    private EnemyTracker myTracker;

    public TrackingMover (double speed,
                          List<ISprite> enemies,
                          IPositionable sprite) {
        super(sprite);
        mySpeed = new Attribute(speed, AttributeType.SPEED);
        myEnemyList = enemies;
        myTracker = new EnemyTracker();
        myEnemy = myTracker.getClosestEnemy(myEnemyList, mySprite.getLocation());

    }

//    private Optional<ISprite> selectEnemy () {
//        return myEnemyList.stream().sorted( (s1, s2) -> Double.compare(getDistance(s1), getDistance(s2))).findFirst();
//
////        ISprite closestEnemy = null;
////        double distance = Double.MAX_VALUE;
////
////        for (ISprite sprite : myEnemyList) {
////            double xDiff = sprite.getLocation().getX() - mySprite.getLocation().getX();
////            double yDiff = sprite.getLocation().getY() - mySprite.getLocation().getY();
////            Double currDistance = Math.abs(xDiff + yDiff);
////            if (currDistance < distance) {
////                distance = currDistance;
////                closestEnemy = sprite;
////            }
////        }
////
////        return closestEnemy;
//    }
//
//    private double getDistance (ISprite sprite) {
//        double xDiff = sprite.getLocation().getX() - mySprite.getLocation().getX();
//        double yDiff = sprite.getLocation().getY() - mySprite.getLocation().getY();
//        return Math.abs(xDiff + yDiff);
//    }

    /*
     * it seems super odd that the modules handle calculating the angle but not the absolute distance?
     * we can make angle calculation relative, i just don't know what it adds
     * (non-Javadoc)
     * @see engine.modules.Mover#update(util.TimeDuration)
     */
    @Override
    public void update (TimeDuration duration) {
       
        move(duration);

    }

    @Override
    public void registerKeyEvent (KeyIOEvent keyEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void registerMouseEvent (MouseIOEvent mouseEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    protected List<IAttribute> getSpecificAttributes () {
        // TODO Auto-generated method stub
        return null;
    }

}
