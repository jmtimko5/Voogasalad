package testing;

import java.util.ArrayList;
import java.util.List;
import com.dooapp.xstreamfx.FXConverters;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import engine.Attribute;
import engine.AttributeType;
import engine.Game;
import engine.IAttribute;
import engine.IGame;
import engine.ISpriteGroup;
import engine.SpriteGroup;
import engine.conditions.OnCollisionCondition;
import engine.definitions.costs.Cost;
import engine.definitions.concrete.AttributeDefinition;
import engine.definitions.concrete.KeyControlDefinition;
import engine.definitions.concrete.SpriteDefinition;
import engine.definitions.moduledef.ConstantMoverDefinition;
import engine.definitions.moduledef.DirectionalFirerDefinition;
import engine.definitions.moduledef.MovementDefinition;
import engine.definitions.moduledef.PathMoverDefinition;
import engine.definitions.moduledef.StaticMovementDefintion;
import engine.definitions.moduledef.UserMoverDefinition;
import engine.effects.DecreaseEffect;
import engine.effects.IEffect;
import engine.events.EventPackage;
import engine.events.EventType;
import engine.events.GameEvent;
import engine.profile.Profile;
import engine.rendering.GameGridConfigNonScaling;
import gameauthoring.shareddata.DefinitionCollection;
import gameplayer.GamePlayer;
import graphics.ImageGraphic;
import javafx.application.Application;
import javafx.stage.Stage;


public class DemoLauncher extends Application {

    private IGame myGame;
    // Health attribute properties
    private static final double HEALTH_START_VAL = 100d;
    private static final String HEALTH_IMAGE_URL = "/images/photo.png";
    private static final String HEALTH_DESCRIPTION = "Health attribute";
    private static final String HEALTH_ATTY_TYPE = "Health";
    private static final double SPRITE_HEIGHT = 50;
    private static final double SPRITE_WIDTH = 50;

    @Override
    public void start (Stage primaryStage) throws Exception {
        makeGame();
       // new GameWriter().serialize(new File("/Users/davidmaydew/Desktop/PlantsZombies.xml"),
        //                           myGame);
                                   // FileChooser chooser = new FileChooser();
                                   // File f = chooser.showOpenDialog(primaryStage);

        // IGame xmlGame = new GameReader().readFile(new
        // File("/Users/davidmaydew/Desktop/test.xml"));
        // IGame xmlGame = new GameReader().readFile(f);
         GamePlayer gp = new GamePlayer(myGame);
    }

    private void makeGame () {
        IGame game =
                new Game(new GameGridConfigNonScaling((int) GamePlayer.PREFWIDTH, (int) GamePlayer.PREFHEIGHT) {

                    @Override
                    public void setYScalingFactor (double scaleY) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void setXScalingFactor (double scaleX) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public double getYScalingFactor () {
                        // TODO Auto-generated method stub
                        return 0;
                    }

                    @Override
                    public double getXScalingFactor () {
                        // TODO Auto-generated method stub
                        return 0;
                    }

                });
        myGame = game;
        createGlobalAtts(game);
        createSpriteDefs(game);
        setBackground();
        //addSpawner1(game);
        //addSpawner2(game);
        //addSpawner3(game);
        //addSpawner4(game);
        createConditions(game);
        XStream xstream = new XStream(new DomDriver());
        FXConverters.configure(xstream);
        xstream.setMode(XStream.SINGLE_NODE_XPATH_RELATIVE_REFERENCES);
        xstream.toXML(game);
    }

    private void createConditions (IGame game) {
        game.getConditionManager().getConditionListProperty().add(missileZombieCollision(game));
        game.getConditionManager().getConditionListProperty().add(plantZombieCollision(game));

    }

    private OnCollisionCondition missileZombieCollision (IGame game) {
        List<SpriteDefinition> g1 = new ArrayList<>();
        g1.add(createMissileDef());
        List<SpriteDefinition> g2 = new ArrayList<>();
        g2.add(createBucket());
        g2.add(createBalloon());

        return new OnCollisionCondition(game, packageForSpriteDefinitions(g1),
                                        packageForSpriteDefinitions(g2),
                                        createEmptyEventPackage(),
                                        createEmptyEventPackage());
    }

    private OnCollisionCondition plantZombieCollision (IGame game) {
        List<SpriteDefinition> g1 = new ArrayList<>();
        g1.add(createShooterDef());
        List<SpriteDefinition> g2 = new ArrayList<>();
        g2.add(createBucket());
        g2.add(createBalloon());

        return new OnCollisionCondition(game, noDeathSpritePackage(g1),// groupa
                                        packageForSpriteDefinitions(g2), // groupb
                                        createEmptyEventPackage(), // othergroup
                                        createAttyChangeOnly()); // global
    }

    private ISpriteGroup createSpriteGroupForDefinition (List<SpriteDefinition> definition) {
        List<SpriteDefinition> mySpritesInGroup = new ArrayList<>();
        mySpritesInGroup.addAll(definition);
        return new SpriteGroup(mySpritesInGroup);
    }

    private EventPackage createAttyChangeOnly () {
        // cahngere asd f
        return new EventPackage(createSpriteGroupForDefinition(),
                                dmgAtty("Lives"), noEvent());
    }

    private List<IEffect> dmgAtty (String attyType) {
        List<IEffect> toReturn = new ArrayList<>();
        toReturn.add(new DecreaseEffect(new AttributeType(attyType),
                                        new Attribute(0d, new AttributeType("cd")), 1d));
        return toReturn;
    }

    private EventPackage createEmptyEventPackage () {
        return new EventPackage(createSpriteGroupForDefinition(), noEffect(), noEvent());
    }

    private ISpriteGroup createSpriteGroupForDefinition () {
        return new SpriteGroup(new ArrayList<>());
    }

    private List<GameEvent> noEvent () {
        return new ArrayList<>();
    }

    private EventPackage packageForSpriteDefinitions (List<SpriteDefinition> list) {
        return new EventPackage(createSpriteGroupForDefinition(list),
                                noEffect(), deathEvent());
    }

    private EventPackage noDeathSpritePackage (List<SpriteDefinition> list) {
        return new EventPackage(createSpriteGroupForDefinition(list),
                                noEffect(), noEvent());
    }

    private List<IEffect> noEffect () {
        return new ArrayList<>();
    }

    private List<GameEvent> deathEvent () {
        List<GameEvent> toReturn = new ArrayList<>();
        GameEvent event = new GameEvent(EventType.DEATH);
        toReturn.add(event);
        return toReturn;
    }

    // private void addSpawner (IGame game) {
    // ILevel level = game.getLevelManager().getCurrentLevel();
    // level.add(createSpawner(), new Coordinate(50, 50));
    // }

    private void setBackground () {
        myGame.getLevelManager().getCurrentLevel()
                .setBackgroundImage(new ImageGraphic(0, 0, "/images/pvz.jpg"));

    }
/*
    private void addSpawner1 (IGame game) {
        SpawnerDefinition s = new SpawnerDefinition(game);
        List<SpriteDefinition> sprites = new ArrayList<SpriteDefinition>();
        sprites.add(createBucket());
        sprites.add(createBalloon());
        sprites.add(createBucket());
        WaveDefinition wave = new WaveDefinition(sprites);
        SpawnerModuleDefinition sM = new SpawnerModuleDefinition(myGame, wave, 5000);
        s.setMySpawningModule(sM);
        ISprite spawner = s.create();
        List<Coordinate> path = new ArrayList<>();
        path.add(new Coordinate(0, 000));
        spawner.setLocation(new Coordinate(800, 000));
        spawner.setPath(path);
        game.add(spawner);
    }

    private void addSpawner2 (IGame game) {
        SpawnerDefinition s = new SpawnerDefinition(game);
        List<SpriteDefinition> sprites = new ArrayList<SpriteDefinition>();
        sprites.add(createBucket());
        sprites.add(createBalloon());
        sprites.add(createBucket());
        WaveDefinition wave = new WaveDefinition(sprites);
        SpawnerModuleDefinition sM = new SpawnerModuleDefinition(myGame, wave, 3000);
        s.setMySpawningModule(sM);
        ISprite spawner = s.create();
        List<Coordinate> path = new ArrayList<>();
        path.add(new Coordinate(0, 100));
        spawner.setLocation(new Coordinate(800, 100));
        spawner.setPath(path);
        game.add(spawner);
    }

    private void addSpawner3 (IGame game) {
        SpawnerDefinition s = new SpawnerDefinition(game);
        List<SpriteDefinition> sprites = new ArrayList<SpriteDefinition>();
        sprites.add(createBucket());
        sprites.add(createBalloon());
        sprites.add(createBucket());
        WaveDefinition wave = new WaveDefinition(sprites);
        SpawnerModuleDefinition sM = new SpawnerModuleDefinition(myGame, wave, 4000);
        s.setMySpawningModule(sM);
        ISprite spawner = s.create();
        List<Coordinate> path = new ArrayList<>();
        path.add(new Coordinate(0, 200));
        spawner.setLocation(new Coordinate(800, 200));
        spawner.setPath(path);
        game.add(spawner);
    }

    private void addSpawner4 (IGame game) {
        SpawnerDefinition s = new SpawnerDefinition(game);
        List<SpriteDefinition> sprites = new ArrayList<SpriteDefinition>();
        sprites.add(createBucket());
        sprites.add(createBalloon());
        sprites.add(createBucket());
        WaveDefinition wave = new WaveDefinition(sprites);
        SpawnerModuleDefinition sM = new SpawnerModuleDefinition(myGame, wave, 7000);
        s.setMySpawningModule(sM);
        ISprite spawner = s.create();
        List<Coordinate> path = new ArrayList<>();
        path.add(new Coordinate(0, 300));
        spawner.setLocation(new Coordinate(800, 300));
        spawner.setPath(path);
        game.add(spawner);
    }
    */

    private Profile healthAttyProfile () {
        return new Profile(HEALTH_ATTY_TYPE, HEALTH_DESCRIPTION,
                           new ImageGraphic(SPRITE_HEIGHT, SPRITE_WIDTH, HEALTH_IMAGE_URL));
    }
    
    private SpriteDefinition createBucket () {
        SpriteDefinition sd1 = new SpriteDefinition();
        AttributeDefinition health = new AttributeDefinition();
        health.setProfile(healthAttyProfile());
        //health.setType("Health");
        health.setStartingValue(10);
        
        sd1.getAttributes().add(health);
        double c = 8;
        ImageGraphic image = new ImageGraphic(446 / c, 774 / c, "/images/Buckethead_Zombie.png");
        sd1.setProfile(new Profile("BucketEnemy", "Buckets", image));
        PathMoverDefinition mover = new PathMoverDefinition();
        mover.setSpeed(.03);
        sd1.setMovementDefinition(mover);
        return sd1;
    }

    private SpriteDefinition createBalloon () {
        SpriteDefinition sd1 = new SpriteDefinition();
        
        double c = 6;
        ImageGraphic image = new ImageGraphic(332 / c, 600 / c, "/images/balloon_zomb.png");
        sd1.setProfile(new Profile("Balloon Enemy", "Buckets", image));
        PathMoverDefinition mover = new PathMoverDefinition();
        mover.setSpeed(.03);
        sd1.setMovementDefinition(mover);
        return sd1;
    }

    private SpriteDefinition createPathMover () {
        SpriteDefinition sd1 = new SpriteDefinition();
        PathMoverDefinition mover = new PathMoverDefinition();
        mover.setSpeed(.1);
        sd1.setMovementDefinition(mover);
        return sd1;
    }

    private SpriteDefinition createProjectile () {
        SpriteDefinition sd1 = new SpriteDefinition();
        sd1.setMovementDefinition(getUserMover());
        return sd1;
    }

    private void createGlobalAtts (IGame game) {
        IAttribute lives = new Attribute(new AttributeType("Lives"));
        lives.setValue(5);
        game.getAttributeManager().getAttributes().add(lives);
    }

    private void createSpriteDefs (IGame game) {
        DefinitionCollection<SpriteDefinition> dc = new DefinitionCollection<>("Towers");

        SpriteDefinition sd1 = createShooterDef();
        sd1.setCost(new Cost(game, new AttributeType("Lives"), 7));

        SpriteDefinition sd2 = new SpriteDefinition();
        ImageGraphic image = new ImageGraphic(100, 100, "/images/C.png");
        sd2.setProfile(new Profile("User Mover", "Controlled By User", image));
        sd2.setMovementDefinition(getUserMover());

        dc.addItem(sd1);
        dc.addItem(sd2);
        game.getAuthorshipData()
                .addCreatedSprites(dc);
        game.getLevelManager().getCurrentLevel().getAddableSprites().addAll(sd1, sd2);
    }

    private SpriteDefinition createShooterDef () {
        SpriteDefinition sd1 = new SpriteDefinition();
        
        ImageGraphic plantImage = new ImageGraphic(50, 50, "/images/plant.png");
        sd1.setProfile(new Profile("Tower 1", "Plant", plantImage));
        sd1.setMovementDefinition(getStaticMover());
        DirectionalFirerDefinition fireDef = new DirectionalFirerDefinition();
        fireDef.setGame(myGame);
        fireDef.setAngle(0);
        fireDef.setWaitTime(3000);
        fireDef.setProjectileDefinition(createMissileDef());
        sd1.addModule(fireDef);
        return sd1;
    }

    private SpriteDefinition createMissileDef () {
        SpriteDefinition sd1 = new SpriteDefinition();
        ImageGraphic plantImage = new ImageGraphic(20, 20, "/images/pea.png");
        sd1.setProfile(new Profile("Pea", "Pea Bullet", plantImage));
        ConstantMoverDefinition mover = new ConstantMoverDefinition();
        double c = 4;
        sd1.setMovementDefinition(mover);

        return sd1;
    }

    private MovementDefinition getUserMover () {
        KeyControlDefinition keys = new KeyControlDefinition();
        keys.setUp("Up");
        keys.setRight("Right");
        keys.setLeft("Left");
        keys.setDown("Down");
        UserMoverDefinition user = new UserMoverDefinition();
        user.setSpeed(.5);
        user.setKeyControlDefintion(keys);
        return user;

    }

    private MovementDefinition getStaticMover () {
        return new StaticMovementDefintion();
    }

    public static void main (String[] args) {
        launch(args);
    }

}
