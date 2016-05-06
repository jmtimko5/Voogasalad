package engine.conditions;

import engine.IOAffectable;
import engine.definitions.concrete.IDefinition;
import engine.profile.IProfilable;


/**
 * This interface represents a generic Condition that will have a trigger condition that it checks
 * each game loop and an effect to fire upon that condition
 *
 * @author Joe Timko
 * @author Dhrumil Patel
 * @author David Maydew
 * @author Ryan St.Pierre
 * @author Jonathan Im
 *
 */
public interface ICondition extends IOAffectable, IDefinition, IProfilable {

}
