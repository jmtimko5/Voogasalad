package gameplayer;

import java.util.ResourceBundle;
import gameauthoring.util.Glyph;
import util.DoubleStringParser;

/**
 * Capturing the commonality of Glyphs that need the bundle to size
 * @author RyanStPierre
 *
 */
public abstract class SizeableGlyph implements Glyph {

    private static final String PATH = "defaults/gameplayer";
    ResourceBundle myBundle = ResourceBundle.getBundle(PATH);

    protected double parseString (String input) {
        // TODO return error
        DoubleStringParser parser = new DoubleStringParser();
        return parser.parse(input);
    }

    protected String getString (String key) {
        return myBundle.getString(key);
    }

}