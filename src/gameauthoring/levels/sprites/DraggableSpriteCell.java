package gameauthoring.levels.sprites;

import engine.definitions.concrete.SpriteDefinition;
import engine.rendering.LevelRenderer;
import gameauthoring.levels.SceneController;
import gameauthoring.util.DraggableCell;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;


/**
 * ListView ListCell that allows for dragging of sprites to the screen. Is used by both
 * the authoring environment and the engine for dragging new sprites onto the screen. Eventually
 * this will be extended for the engine to incorporate costs
 * 
 * @author Tommy
 *
 */
public class DraggableSpriteCell extends DraggableCell<SpriteDefinition> {
    private static final String DRAG_STRING = "Sprite";

    private LevelRenderer myTarget;
    private SceneController myController;

    public DraggableSpriteCell (LevelRenderer target, SceneController controller) {
        setTarget(target.getPane());
        myTarget = target;
        myController = controller;
    }

    @Override
    protected Node createSpriteCell (SpriteDefinition profile) {
        Node node = super.createSpriteCell(profile);
        this.setActions(node);
        return node;
    }

    @Override
    public void setOnDragDetected (MouseEvent e, Node node) {
        Dragboard db = node.startDragAndDrop(TransferMode.COPY);
        db.setContent(this.createClipboard(DRAG_STRING));

        db.setDragView(getSpriteImage());

        myTarget.getPane().setOnDragOver(event -> setOnDragOver(event));
        myTarget.getPane().setOnDragDropped(event -> setOnDragDropped(event));
    }

    protected SceneController getController () {
        return myController;
    }

    @Override
    public void setOnDragOver (DragEvent e) {
        e.acceptTransferModes(TransferMode.COPY);
    }

    @Override
    public void setOnDragDropped (DragEvent e) {
        if (checkPlaceableTile(e)) {
            Dragboard db = e.getDragboard();
            if (db.hasString()) {
                addSprite(e.getX(), e.getY());
            }
        }
    }

    protected void addSprite (double x, double y) {
        myController.addSprite(x, y, getProfilable());
        myTarget.render();
    }

    private boolean checkPlaceableTile (DragEvent e) {
        double halfHeight = (0.5) * myController.getRatio().scale((getProfilable().getProfile().getImageHeight().doubleValue()));
        double halfWidth = (0.5) * myController.getRatio().scale((getProfilable().getProfile().getImageWidth().doubleValue()));
        for (int r = (int) ((e.getY()-halfHeight) / myController.getRatio().scale(50)); r < (int) ((e.getY()+halfHeight) / myController.getRatio().scale(50)); r++) {
            for (int c = (int) ((e.getX()-halfWidth) / myController.getRatio().scale(50)); c < (int) ((e.getX()+halfWidth) / myController.getRatio().scale(50)); c++) {
                if (myController.getLevel().getPlaceableTileManager().getPlaceableMap().getBitMap()[r][c])
                    return false;
            }
        }
        return true;
    }
}
