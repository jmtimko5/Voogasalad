package engine.modules;

import engine.Affectable;
import engine.GameEventHandler;
import engine.Updateable;


/**
 * This interface represents a generic module, which defines some compartmentalized functionality
 * for a sprite
 *
 * @author Joe Timko
 * @author Dhrumil Patel
 * @author David Maydew
 * @author Ryan St.Pierre
 * @author Jonathan Im
 *
 */
public interface IModule extends Updateable, Affectable, GameEventHandler {

}
