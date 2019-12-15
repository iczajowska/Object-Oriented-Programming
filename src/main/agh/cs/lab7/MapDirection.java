package agh.cs.lab7;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    @Override
    public String toString() {
        switch (this) {
            case EAST:
                return "wschód";
            case WEST:
                return "zachód";
            case SOUTH:
                return "południe";
            case NORTH:
                return "północ";
        }
        return null;
    }

    public MapDirection next() {
        switch (this) {
            case EAST:
                return SOUTH;
            case WEST:
                return NORTH;
            case SOUTH:
                return WEST;
            case NORTH:
                return EAST;
        }
        return null;
    }

    public MapDirection previous() {
        switch (this) {
            case EAST:
                return NORTH;
            case WEST:
                return SOUTH;
            case NORTH:
                return WEST;
            case SOUTH:
                return EAST;
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
            case NORTH:
                return new Vector2d(0, 1);
        }
        return null;
    }
}
