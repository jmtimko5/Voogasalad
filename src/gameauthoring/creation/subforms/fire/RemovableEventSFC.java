package gameauthoring.creation.subforms.fire;

import engine.definitions.concrete.EventPackageDefinition;
import engine.definitions.moduledef.ModuleDefinition;
import gameauthoring.creation.subforms.ISubFormView;
import gameauthoring.creation.subforms.MultiOptionSFC;

public abstract class RemovableEventSFC extends RemovableSFC<EventPackageDefinition>{

    public RemovableEventSFC (MultiOptionSFC<EventPackageDefinition> sfc) {
        super(sfc);
    }

    @Override
    public void removeModule (Object myMod) {
        if (getMyDefinition() != null) {
            if (getMyDefinition().getMyEventsList().contains(myMod)) {
                getMyDefinition().getMyEventsList().remove(myMod);
            }
        }
    }

}
