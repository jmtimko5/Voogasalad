package engine;

import java.util.List;
import engine.sprite.ISprite;
import util.Coordinate;


/**
 * This interface manages a list of sprites, and handles access to them.
 * Effects have been moved to a conditions manager, which will have
 * top down views of the individual sprites themselves,
 * thus this interface is not repsonsible for passing events to the sprites
 *
 * @author Joe Timko
 * @author Dhrumil Patel
 * @author David Maydew
 * @author Ryan St.Pierre
 * @author Jonathan Im
 *
 */
public interface ISpriteManager extends Updateable, IAdder, IEventInternalizer {

    /**
     * @return the observable list of sprites
     */
    List<ISprite> getSprites ();

    List<? extends Drawable> getDrawables ();

    /**
     * @param sprite to be removed
     */
    void remove (ISprite sprite);

    void add (ISprite sprite, Coordinate coordinate);

    void add (ISprite sprite);

}
