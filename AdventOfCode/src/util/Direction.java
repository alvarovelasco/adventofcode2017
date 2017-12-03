package util;

/**
 * @author /u/Philboyd_Studge on 12/26/2016.
 *
 * This enum set is for simplifying movement on a 2d integer grid
 * Assuming the Y axis is -up(north) and +down(south),
 * and X axis is -right(west) and +left(east)
 */
public enum Direction {
    NORTH (0 , -1),
    EAST (1, 0),
    SOUTH (0, 1),
    WEST (-1, 0);

    private int dx, dy;
    private Direction opposite;
    private String alternate;

    static {
        NORTH.opposite = SOUTH;
        NORTH.alternate = "UP";
        EAST.opposite = WEST;
        EAST.alternate = "RIGHT";
        SOUTH.opposite = NORTH;
        SOUTH.alternate = "DOWN";
        WEST.opposite = EAST;
        WEST.alternate = "LEFT";
    }

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() { return dx; }

    public int getDy() { return dy; }

    public Direction getOpposite() { return opposite; }

    public Direction getRight() {
        return Direction.values()[(this.ordinal() + 1) & 3];
    }
    public Direction getLeft() {
        return Direction.values()[(this.ordinal() - 1) & 3];
    }

    public static Direction getDirectionFromAlternate(String s) {
        return getDirectionFromAlternate(s.toUpperCase().charAt(0));
    }

    public static Direction getDirectionFromAlternate(char c) {
        for (Direction each : Direction.values()) {
            if (c == each.alternate.charAt(0)) {
                return each;
            }
        }
        // default to North
        return Direction.NORTH;
    }

    public String getAlternate() {
        return alternate;
    }
    public char getAlternateChar() {
        return alternate.charAt(0);
    }

    // simplest range check: both x and y must be 0 or greater, and less than 'higher'
    public static boolean rangeCheck(int x, int y, int higher) {
        return rangeCheck(x, y, 0, higher);
    }

    // range check for when lower and higher bounds are the same for x and y
    public static boolean rangeCheck(int x, int y, int lower, int higher) {
        return (x >= lower && x < higher &&
                y >= lower && y < higher);
    }
    
    public static boolean rangeCheck(int x, int y, int xLow, int yLow, int xHigh, int yHigh) {
        return (x >= xLow && x < xHigh &&
                y >= yLow && y < yHigh);
    }
}