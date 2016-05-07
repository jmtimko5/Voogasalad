package gameauthoring.creation.subforms.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import engine.IGame;
import engine.profile.IProfilable;
import gameauthoring.creation.factories.MultiOptionFactory;
import gameauthoring.creation.subforms.ClickAndFillView;
import gameauthoring.creation.subforms.ISubFormController;
import gameauthoring.creation.subforms.ISubFormView;
import gameauthoring.creation.subforms.fire.RemovableSFC;
import splash.LocaleManager;
import util.StringParser;


public abstract class MultiOptionSFC<T extends IProfilable> implements ISubFormController<T> {

    private ClickAndFillView myView;
    private List<RemovableSFC<T>> mySFCs = new ArrayList<>();
    private ResourceBundle myOptionsFile =
            ResourceBundle.getBundle("customization/dynamic_sfc_contents");
    private MultiOptionFactory<T> mySFCFactory;
    private IGame myGame;
    private ResourceBundle myDefClasspaths = ResourceBundle.getBundle("customization/sfc_classpath");
    private ResourceBundle myLabels;
    private StringParser myParser;
    private ResourceBundle myNumbers;
    private List<String> myOptions;

    public MultiOptionSFC (IGame game) {
        myGame = game;
        myLabels =
                ResourceBundle
                        .getBundle("languages/labels",
                                   LocaleManager.getInstance().getCurrentLocaleProperty().get());
        myNumbers = ResourceBundle
                .getBundle("defaults/numbers");
        myParser = new StringParser();
        setMySFCFactory(new MultiOptionFactory<T>(game));
    }

    protected void setActions () {
        for (int i = 0; i < getMyView().getMyButtons().size(); i++) {
            String sfcID = getMyOptions().get(i);
            getMyView().setButtonAction(getMyView().getMyButtons().get(i),
                                        e -> addSFC(mySFCFactory.createSubFormController(
                                                                                         getMyDefClasspaths()
                                                                                                 .getString(sfcID),
                                                                                         getMyGame(),
                                                                                         this)));
        }
    }

    protected void addSFC (RemovableSFC<T> sfc) {
        mySFCs.add(sfc);
        getMyView().addOrSetSFV(sfc.getSubFormView());
    }

    protected void clearSFCs () {
        mySFCs.clear();
        getMyView().clearSFVs();
    }

    @Override
    public void updateItem (T item) {
        resetContents(item);
        mySFCs.forEach(e -> e.updateItem(item));
    }

    protected abstract void resetContents (T item);

    @Override
    public ISubFormView getSubFormView () {
        return getMyView();
    }

    @Override
    public void initializeFields () {
    }

    public void removeSFC (RemovableSFC<T> sfc) {
        mySFCs.remove(sfc);
        getMyView().removeSFV(sfc.getSubFormView());
        if (sfc.getModuleDefinition() != null) {
            sfc.removeModule(sfc.getModuleDefinition());
        }
    }

    protected abstract List<? extends Object> getList (T item);

    @Override
    public void populateViewsWithData (T item) {
        clearSFCs();
        List<? extends Object> objects = getList(item);
        for (Object object : objects) {
            RemovableSFC<T> sfc =
                    getMySFCFactory().createSubFormController(object.getClass().getName(),
                                                              getMyGame(), this,
                                                              object);
            sfc.populateViewsWithData(item);
            addSFC(sfc);
        }
    }

    protected IGame getMyGame () {
        return myGame;
    }

    protected List<RemovableSFC<T>> getMySFCs () {
        return mySFCs;
    }

    protected List<String> getMyOptions () {
        return myOptions;
    }

    protected void setMyOptions (List<String> options) {
        myOptions = options;
    }

    protected ResourceBundle getMyOptionsFile () {
        return myOptionsFile;
    }

    protected ClickAndFillView getMyView () {
        return myView;
    }

    protected void setMyView (ClickAndFillView view) {
        this.myView = view;
    }

    protected MultiOptionFactory<T> getMySFCFactory () {
        return mySFCFactory;
    }

    protected void setMySFCFactory (MultiOptionFactory<T> sfcFactory) {
        this.mySFCFactory = sfcFactory;
    }

    public ResourceBundle getMyDefClasspaths () {
        return myDefClasspaths;
    }

    protected ResourceBundle getMyLabels () {
        return myLabels;
    }

    protected ResourceBundle getMyNumbers () {
        return myNumbers;
    }

    protected StringParser getParser () {
        return myParser;
    }
}
