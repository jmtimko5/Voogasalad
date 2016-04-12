package gameauthoring.tabs;

import engine.ConditionManager;
import engine.Game;
import engine.GameInformation;
import engine.ILevel;
import engine.Level;
import engine.LevelManager;


/**
 * Factory class for creating game.
 * TODO: loadGame method which loads XML file and creates the game
 * TODO: ResourceBundle for unprotected string
 * 
 * @author Jin An
 *
 */
public class GameFactory {

    public Game createGame () {
        ILevel startingLevel = new Level();
        LevelManager levelManager = new LevelManager(startingLevel);
        String title = "title";
        String author = "author";
        String dateCreated = "date";
        GameInformation info = new GameInformation(title, author, dateCreated);
        ConditionManager conditionManager = new ConditionManager();
        Game game = new Game(levelManager, info, conditionManager);

        return game;
    }

    // public Game loadGame(File xml);
}