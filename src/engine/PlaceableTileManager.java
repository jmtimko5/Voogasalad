package engine;

import util.BitMap;


public class PlaceableTileManager implements IPlaceableTileManager {

    private ILevel myLevel;
    private BitMap myPlaceableMap;

    private int myRowSize;
    private int myColumnSize;

    public PlaceableTileManager (ILevel level, int row, int column) {
        myLevel = level;
        initialize(row, column);
    }

    public void initialize (int row, int column) {
        myPlaceableMap = new BitMap(row, column);
        myRowSize = row;
        myColumnSize = column;
    }

    public boolean checkBitMap(int row, int column){
        return myPlaceableMap.getBitMap()[row][column];
    }
    
    public void setBitMap (int row, int column, boolean bool){
        myPlaceableMap.getBitMap()[row][column] = bool;
    }
    
    public int getRowSize () {
        return myRowSize;
    }

    public int getColumnSize () {
        return myColumnSize;
    }

    public ILevel getLevel () {
        return myLevel;
    }

    @Override
    public BitMap getPlaceableMap () {
        return myPlaceableMap;
    }
}
