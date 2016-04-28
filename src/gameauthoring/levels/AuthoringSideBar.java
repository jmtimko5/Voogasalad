package gameauthoring.levels;

import engine.IGame;
import engine.definitions.concrete.SpriteDefinition;
import engine.rendering.LevelRenderer;
import gameauthoring.creation.cellviews.ProfileCellView;
import gameauthoring.levels.sprites.DragCheckSpriteCell;
import gameauthoring.shareddata.DefinitionCollection;
import gameplayer.SideBarDisplay;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;

public class AuthoringSideBar extends SideBarDisplay {

    public AuthoringSideBar (IGame game, LevelRenderer renderer) {
        super(game, renderer);
    }

    protected void fillAccordion (Accordion accordion) {
        getGame().getAuthorshipData().getMyCreatedSpritesMap().values().stream().forEach(c -> {
            TitledPane toAdd = createAccordionPane(c);
            accordion.getPanes().add(toAdd);
            accordion.expandedPaneProperty().set(toAdd);
        });
    }

    protected TitledPane createAccordionPane (DefinitionCollection<SpriteDefinition> collection) {
        ListView<SpriteDefinition> spriteList = createSpriteList(collection.getItems());
        TitledPane pane = new TitledPane(collection.getTitle(), spriteList);
        return pane;
    }

    protected ProfileCellView<SpriteDefinition> getSpriteCellView () {
        return new DragCheckSpriteCell(getLevelView(), getController());
    }

}
