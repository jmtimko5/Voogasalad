package engine.modules;

import java.util.ArrayList;
import java.util.List;
import engine.IAdder;
import engine.ILevel;
import engine.Positionable;
import engine.definitions.spawnerdef.WaveDefinition;
import engine.effects.DefaultAffectable;
import engine.sprite.ISprite;
import engine.waves.IWave;
import engine.waves.IWaveSet;
import engine.waves.WaveSet;
import util.TimeDuration;


/**
 * This class is responsible for creating sprites and adding them to a wave for tower defense games.
 *
 */
public class SpawningModule extends DefaultAffectable implements IModule {

    private IAdder myAdder;
    private IWaveSet myWaveSet;
    private Positionable myParent;

    public SpawningModule (IAdder adder,
                           ILevel level,
                           Positionable parent,
                           List<WaveDefinition> waves) {
        myParent = parent;
        myAdder = adder;
        myWaveSet = new WaveSet();
        List<IWave> myWaves = new ArrayList<IWave>();
        waves.stream().forEachOrdered(p -> myWaves.add(p.create()));
        myWaveSet.setWaveList(myWaves);
        myWaveSet.updateCurrentWave();
        if (myWaveSet.getCurrentWave() != null) {
            level.getWaveSetManager().addWaveSet(myWaveSet);
        }

    }

    @Override
    public void update (TimeDuration duration) {
        if (!myWaveSet.getStopWaves() &
            myWaveSet.getCurrentWave().satisfiedSpawnInterval(duration)) {
            ISprite spawn = myWaveSet.getCurrentWave().spawnSprite();
            spawn.setPath(myParent.getPath());
            myAdder.bufferedAdd(spawn, myParent.getLocation());
        }

    }

}
