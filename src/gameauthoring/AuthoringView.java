package gameauthoring;

/**
 * The main view class for the authoring environment.
 * This will handle the selection between different parts of
 * the authoring environment.
 * @author Tommy
 * @author Jin An
 *
 */
public interface AuthoringView {

    GameTabViewer getGameTabViewer();
    CharTabViewer getCharTabViewer();
    LevelTabViewer getLevelTabViewer();
    
    /**
     * Creates initial tabs/views that make up the main view
     */
    void init ();
}
