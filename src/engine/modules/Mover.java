package engine.modules;

import java.util.ArrayList;
import java.util.List;
import engine.Attribute;
import engine.AttributeType;
import engine.IAttribute;
import engine.IPositionable;
import engine.effects.DefaultAffectable;
import engine.effects.IEffect;
import engine.interactionevents.KeyIOEvent;
import engine.interactionevents.MouseIOEvent;
import util.Coordinate;
import util.TimeDuration;


/**
 * This abstract class provides the framework required to move sprites. Movement is handled as a
 * function of rate and time or as a coordinate specifying the next position.
 *
 */

public abstract class Mover extends DefaultAffectable implements IMovementModule {

    public static final double NO_MOTION = 0;
    private IAttribute myXVel;
    private IAttribute myYVel;
    private IPositionable myParent;

    public Mover (IPositionable positionable) {
        myXVel = new Attribute(AttributeType.X_VEL);
        myYVel = new Attribute(AttributeType.Y_VEL);
        myParent = positionable;
    }

    private Coordinate getLocation () {
        return myParent.getLocation();
    }

    protected void move (TimeDuration duration) {
        double xChange = distance(getXVel().getValueProperty().get(), duration.getMillis());
        double yChange = distance(getYVel().getValueProperty().get(), duration.getMillis());
        move(getNextCoordinate(xChange, yChange));
    }

    protected void move (Coordinate coordinate) {
        myParent.setLocation(coordinate);
    }

    private Coordinate getNextCoordinate (double xChange, double yChange) {
        return new Coordinate(getLocation().getX() + xChange,
                              getLocation().getY() + yChange);
    }
    
    /*
     * overloaded method to account for orientation below
     * need to discuss
     */
    protected void moveTrig (TimeDuration duration, double heading){
        double xChange = distanceTrigX(getXVel().getValueProperty().get(), duration.getMillis(), myParent.getLocation().getOrientation());
        double yChange = distanceTrigY(getYVel().getValueProperty().get(), duration.getMillis(), myParent.getLocation().getOrientation());
        move(getNextCoordinate(xChange, yChange, heading));
    }
        
    private Coordinate getNextCoordinate(double xChange, double yChange, double orientationChange) {
        return new Coordinate(getLocation().getX() + xChange,
                              getLocation().getY() + yChange, 
//                              getLocation().getOrientation() + orientationChange);
                              orientationChange);
    }

    /*
     * new method to move with angles below
     */
    private double distanceTrigX(double rate, double time, double heading){
        double hypotenuse = rate*time;
        return Math.cos(heading) * hypotenuse;
    }
    
    private double distanceTrigY(double rate, double time, double heading){
        double hypotenuse = rate*time;
        return Math.sin(heading) * hypotenuse;
        
    }
    
    private double distance (double rate, double time) {
        return rate * time;
    }
    
    

    protected double getXDiff (double input) {
        return input - getLocation().getX();
    }

    protected double getYDiff (double input) {
        return input - getLocation().getY();
    }

    @Override
    public abstract void update (TimeDuration duration);

    @Override
    public void applyEffect (IEffect effect) {
        getAttributes().forEach(a -> effect.applyToAttribute(a));

    }

    @Override
    public abstract void registerKeyEvent (KeyIOEvent keyEvent);

    @Override
    public abstract void registerMouseEvent (MouseIOEvent mouseEvent);

    @Override
    public List<IAttribute> getAttributes () {
        List<IAttribute> attributes = new ArrayList<>();
        attributes.add(getXVel());
        attributes.add(getYVel());
        attributes.addAll(getSpecificAttributes());
        return attributes;
    }

    protected abstract List<IAttribute> getSpecificAttributes ();

    protected IAttribute getXVel () {
        return myXVel;
    }

    protected IAttribute getYVel () {
        return myYVel;
    }

}
