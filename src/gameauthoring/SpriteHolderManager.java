package gameauthoring;

import java.util.List;


/**
 * This may end up being an abstract class (with the given methods private),
 * depending on how other objects need to communicate with it
 * 
 * - Has a List<SpriteHolder>
 * - Each SpriteHolder corresponds to a category of sprites: enemies, defenders, weapons, terrain,
 * obstacles
 * 
 * @author Jeremy Schreck
 *
 */
public interface SpriteHolderManager {

    /**
     * Get the SpriteHolderManager's list of SpriteHolders
     * 
     * @return The list of SpriteHolder objects
     */
    List<SpriteHolder> getSpriteHolders ();

    /**
     * Get the SpriteHolder located at the specified index
     * 
     * @param index The index of the SpriteHolder of interest
     * @return The SpriteHolder at the given index
     */
    SpriteHolder getSpriteHolderAtIndex (int index);

}
