package gameauthoring.creation.subforms;

import engine.IGame;
import engine.definitions.SpriteDefinition;
import gameauthoring.creation.subforms.fire.FiringSubFormController;
import gameauthoring.creation.subforms.movement.MovementSubFormController;


public class SpriteSFCFactory extends SubFormControllerFactory<SpriteDefinition> {

    public SpriteSFCFactory (IGame game) {
        super(game);
    }

    @Override
    protected ISubFormController<SpriteDefinition> createSubFormController (String type) {

        if (type.equals("Movement")) {
            return new MovementSubFormController(getMyGame());
        }

        else if (type.equals("SelectAttribute")) {
            return new SelectAttributeSFC(getMyAuthorshipData()
                    .getMyCreatedAttributes());
        }
        else if (type.equals("Firing")) {
            return new FiringSubFormController(getMyGame());
        }
        else if (type.equals("Upgrade")) {
            return new UpgradeSFC(getMyGame());
        }

        System.out.println("null");

        return null;
    }

}
