package engine;

import java.util.List;
import engine.conditions.ICondition;
import engine.definitions.concrete.SpriteDefinition;
import engine.profile.IProfilable;
import engine.sprite.ISprite;
import graphics.ImageGraphic;
import javafx.collections.ObservableList;
import util.Bounds;
import util.Coordinate;


/**
 * This interface represents a level of a game and all of its associated editable behavior.
 *
 * @author Joe Timko
 * @author Dhrumil Patel
 * @author David Maydew
 * @author Ryan St.Pierre
 * @author Jonathan Im
 * @author Jin An
 *
 */
public interface ILevel extends Updateable, IAdder, IEventInternalizer, IProfilable {

    /**
     * @return The next level manager object for setting
     *         winning and losing levels
     */
    INextLevelManager getNextLevelManager ();

    /**
     *
     * @return The waveset manager object for this level
     */
    IWaveSetManager getWaveSetManager ();

    /**
     * @return the condition manager for this level
     */
    ObservableList<ICondition> getConditionsListProperty ();

    /**
     * @return the global attribute manager for this level
     */
    IAttributeManager getAttributeManager ();

    /**
     * @return the Image of the background of the level
     */
    ImageGraphic getBackgroundImage ();

    /**
     * @return the object that contains the Bit Map of which terrains are tower-placeable
     */
    IPlaceableTileManager getPlaceableTileManager ();

    void setBackgroundImage (ImageGraphic graphic);

    void initializePlaceableTiles (int row, int column);

    void setBackgroundImageSize (double width, double height);

    /**
     * Add a global resource to this level
     *
     * @param resource to be added
     */
    void addGlobalResource (IResource resource);

    /**
     * Gets the list of sprite definitions that can be chosen by the user
     * to use on this level
     *
     * @return list of sprite definitions
     */
    ObservableList<SpriteDefinition> getAddableSprites ();

    /**
     * @return an observable list of the sprites in this level
     */
    List<ISprite> getSprites ();

    /**
     * This method call will control transition between levels, to stay on the current
     * level
     *
     * @return the next level after this one
     */
    ILevel getNextLevel ();

    /**
     * @return whether or not level should be switched out for the next one
     */
    boolean shouldSwitchLevel ();

    /**
     * @return the list of Drawable items in this level
     */
    List<? extends Drawable> getDrawables ();

    double getBackgroundImageWidth ();

    double getBackgroundImageHeight ();

    /**
     * @param sprite to be removed
     */

    void remove (ISprite sprite);

    void add (ISprite sprite, Coordinate coordinate);

    void add (ISprite sprite);

    /**
     * @return the level bounds
     */
    Bounds getBounds ();

    void setBounds (Bounds bound);

}
