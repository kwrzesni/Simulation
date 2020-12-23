package maps_elements.data_types;

public enum Direction {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public static final int nDirections = 8;

    public static Direction intToDirection(int direction){
        return Direction.values()[direction];
    }

    public int toInt() {
        return switch(this) {
            case NORTH -> 0;
            case NORTHEAST -> 1;
            case EAST -> 2;
            case SOUTHEAST -> 3;
            case SOUTH -> 4;
            case SOUTHWEST -> 5;
            case WEST -> 6;
            case NORTHWEST -> 7;
        };
    }

    public Vector2d toUnitVector(){
        return switch(this){
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }

    public Direction rotate(int rotation){
        return intToDirection((rotation+toInt())%nDirections);
    }


}