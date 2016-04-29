package gameauthoring.creation.subforms.events;

import java.util.List;
import gameauthoring.creation.subforms.ClickAndFillView;
import gameauthoring.creation.subforms.ISubFormView;


public class EventChoiceSFV extends ClickAndFillView {
    
    private String defaultHelpMessage = "help";

    public EventChoiceSFV (List<String> options, String titleKey) {
        super(options, titleKey);
        initView();
        setDefaultHelpMessage(defaultHelpMessage);
        showDefaultMessage();
    }

    @Override
    public void addOrSetSFV (ISubFormView subFormView) {
        super.getMyPaneContent().getChildren().add(subFormView.draw());
    }

}