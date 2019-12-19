package agh.cs.lab8;

import java.util.Random;

public enum MapDirection {
    NORTHWEST,
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST;


    private static Random generator = new Random();
    @Override
    public String toString() {
        switch (this) {
            case EAST:
                return  Character.toString((char) 8594) + " ";
            case WEST:
                return Character.toString((char) 8592) + " ";
            case SOUTH:
                return Character.toString((char) 8595) + " ";
            case SOUTHEAST:
                return Character.toString((char) 8600) + " ";
            case SOUTHWEST:
                return Character.toString((char) 8601) + " ";
            case NORTH:
                return Character.toString((char) 8593) + " ";
            case NORTHEAST:
                return Character.toString((char) 8599) + " ";
            case NORTHWEST:
                return Character.toString((char) 8598) + " ";
        }
        return null;
    }

    public MapDirection next() {
        switch (this) {
            case EAST:
                return SOUTHEAST;
            case SOUTHEAST:
                return SOUTH;
            case SOUTH:
                return SOUTHWEST;
            case SOUTHWEST:
                return WEST;
            case WEST:
                return NORTHWEST;
            case NORTHWEST:
                return NORTH;
            case NORTH:
                return NORTHEAST;
            case NORTHEAST:
                return EAST;
        }
        return null;
    }

    public MapDirection previous() {
        switch (this) {
            case EAST:
                return NORTHEAST;
            case NORTHEAST:
                return NORTH;
            case NORTH:
                return NORTHWEST;
            case NORTHWEST:
                return WEST;
            case WEST:
                return SOUTHWEST;
            case SOUTHWEST:
                return SOUTH;
            case SOUTH:
                return SOUTHEAST;
            case SOUTHEAST:
                return EAST;
        }
        return null;
    }

    public MapDirection opposite(){
        switch (this){
            case EAST:
                return WEST;
            case NORTHEAST:
                return SOUTHWEST;
            case NORTH:
                return SOUTH;
            case NORTHWEST:
                return SOUTHEAST;
            case WEST:
                return EAST;
            case SOUTHWEST:
                return NORTHEAST;
            case SOUTH:
                return NORTH;
            case SOUTHEAST:
                return NORTHWEST;
        }
        return null;
    }


    public Vector2d toUnitVector() {
        switch (this) {
            case EAST:
                return new Vector2d(1, 0);
            case WEST:
                return new Vector2d(-1, 0);
            case SOUTH:
                return new Vector2d(0, -1);
            case SOUTHEAST:
                return new Vector2d(1, -1);
            case SOUTHWEST:
                return new Vector2d(-1, -1);
            case NORTH:
                return new Vector2d(0, 1);
            case NORTHEAST:
                return new Vector2d(1, 1);
            case NORTHWEST:
                return new Vector2d(-1, 1);
        }
        return null;
    }

    public MapDirection rotation(int geneticCodeByIndex) {

        return MapDirection.values()[(this.ordinal()+geneticCodeByIndex)%MapDirection.values().length];
    }



    public static MapDirection getRandomDirection(){
        return MapDirection.values()[generator.nextInt(8)];
    }
}
