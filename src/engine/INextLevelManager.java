package engine;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

interface INextLevelManager extends Updateable{
    
    /**
     * This will return the value as calculated during the update call to help control
     * program flow in the class which holds this
     * @return
     */
    boolean shouldGoToNextLevel();
    
    /**
     * Will return the level that the user should transition to
     * @return
     */
    ILevel getNextLevel();
    
    /**
    *
    * @return the observable list of condition that this class manages
    */
   ObservableList<ObjectProperty<INextLevelCondition>> getConditionListProperty ();
    
}
