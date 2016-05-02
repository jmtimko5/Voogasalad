package engine.definitions.concrete;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import engine.IAttribute;
import engine.SpriteGroup;
import engine.definitions.costs.ICost;
import engine.definitions.costs.NullCost;
import engine.definitions.moduledef.ModuleDefinition;
import engine.definitions.moduledef.MovementDefinition;
import engine.definitions.moduledef.StaticMovementDefintion;
import engine.definitions.upgrades.NullUpgradeDefinition;
import engine.definitions.upgrades.UpgradeDefinition;
import engine.modules.GraphicModule;
import engine.modules.IGraphicModule;
import engine.modules.IModule;
import engine.modules.IMovementModule;
import engine.modules.UpgradeModule;
import engine.profile.IProfilable;
import engine.profile.IProfile;
import engine.profile.Profile;
import engine.sprite.ISprite;
import engine.sprite.Sprite;
import engine.sprite.SpriteType;
import util.Coordinate;


/**
 * This class represents the definition of a sprite to be created at a later point
 *
 */
public class SpriteDefinition implements IProfilable {

    private MovementDefinition myMovementDefinition;
    private List<ModuleDefinition> myModuleDefinitions;
    private UpgradeDefinition myUpgrade;
    private Coordinate myLocation;
    private List<AttributeDefinition> myAttributes;
    private IProfile myProfile;
    private ICost myCost;
    private SpriteGroup mySingleGroup;
    private boolean myObstructability;
    private boolean myGoal;

    public SpriteDefinition () {
        // TODO Set a default from resource file. THis is just for view testing
        myMovementDefinition = new StaticMovementDefintion();
        myUpgrade = new NullUpgradeDefinition();
        myModuleDefinitions = new ArrayList<>();
        myAttributes = new ArrayList<>();
        myLocation = new Coordinate(0, 0);
        myProfile = new Profile();
        myCost = new NullCost();
    }

    public ISprite create () {

        ISprite sprite = new Sprite(new SpriteType(myProfile.getName().get()));
        IMovementModule mover = myMovementDefinition.create(sprite);
        IGraphicModule graphicModule = createGraphicModule();
        sprite.initialize(mover, graphicModule, createUpgrade(sprite), createModules(sprite),
                          createAttributes(), createCoordinate());
        sprite.setObstruction(myObstructability);
        sprite.getStatusModule().setIsGoal(myGoal);
        return sprite;
    }

    public void clearGoalObstructable () {
        setObstructability(false);
        setMyGoal(false);
    }

    public void setObstructability () {
        myObstructability = true;
        setMyGoal(false);
    }

    public boolean getObstructability () {
        return myObstructability;
    }

    public boolean isMyGoal () {
        return myGoal;
    }

    public void setMyGoal () {
        this.myGoal = true;
        setObstructability(false);
    }
    
    private void setMyGoal(boolean goal){
        myGoal = goal;
    }
    
    private void setObstructability (boolean obstructable){
        myObstructability = obstructable;
    }
    
    protected UpgradeModule createUpgrade (ISprite parent) {
        return myUpgrade.create(parent);
    }

    protected IGraphicModule createGraphicModule () {
        return new GraphicModule(myProfile.getImage());
    }

    protected Coordinate createCoordinate () {
        return myLocation;
    }

    protected List<IModule> createModules (ISprite sprite) {
        return myModuleDefinitions.stream()
                .map(modDef -> modDef.create(sprite))
                .collect(Collectors.toList());
    }

    protected List<IAttribute> createAttributes () {
        return myAttributes.stream()
                .map(attDef -> attDef.create())
                .collect(Collectors.toList());
    }

    public void addModule (ModuleDefinition definition) {
        myModuleDefinitions.add(definition);
    }

    public void addAttribute (AttributeDefinition attribute) {
        myAttributes.add(attribute);
    }

    public List<AttributeDefinition> getAttributes () {
        return myAttributes;
    }

    public void setAttributes (List<AttributeDefinition> attributes) {
        myAttributes = new ArrayList<AttributeDefinition>(attributes);
    }

    public void removeAttribute (AttributeDefinition attribute) {
        myAttributes.remove(attribute);
    }

    public void setLocation (Coordinate location) {
        myLocation = location;
    }

    public void remove (ModuleDefinition definition) {
        myModuleDefinitions.remove(definition);
    }

    public MovementDefinition getMovementDefinition () {
        return myMovementDefinition;
    }

    public void setMovementDefinition (MovementDefinition definition) {
        myMovementDefinition = definition;
    }
    
    public void setMySingleGroup (SpriteGroup group) {
        mySingleGroup = group;
    }
    
    public SpriteGroup getMySingleGroup () {
        return mySingleGroup;
    }

    @Override
    public IProfile getProfile () {
        return myProfile;
    }

    @Override
    public void setProfile (IProfile profile) {
        myProfile = profile;
    }

    public void setUpgrade (UpgradeDefinition upgrade) {
        myUpgrade = upgrade;
    }
    
    public UpgradeDefinition getUpgrade () {
        return myUpgrade;
    }

    public List<ModuleDefinition> getModuleDefinitions () {
        return myModuleDefinitions;
    }

    public SpriteType getSpriteType () {
        // TODO: check if this should be one reference or new one every time

        return new SpriteType(getProfile().getName().get());
    }
    
    public ICost getCost () {
        return myCost;
    }
    
    public void setCost (ICost cost) {
        myCost = cost;
    }
    


}
