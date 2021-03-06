package engine.sprite;

import engine.StringBasedType;


/**
 * This class represents the type of a sprite, with the appropriate implementation to allow for
 * equality tests
 *
 * @author Joe Timko
 * @author Dhrumil Patel
 * @author David Maydew
 * @author Ryan St.Pierre
 * @author Jonathan Im
 *
 */
public class SpriteType extends StringBasedType {

    public SpriteType (String type) {
        super(type);
    }

    @Override
    protected boolean isSameClass (Object obj) {
        return obj instanceof SpriteType;
    }
}
