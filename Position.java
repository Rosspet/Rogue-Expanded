/**
 * This class can be used to make position objects which can store an entities
 * x,y coordinates.
 * This class comes with methods for moving a position in each of 4 basic
 * compass direcitons: N,S,E,W.
 * 
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 */
public class Position { // ENTITY HAS ALL THESE PUBLIC THINGS - including the movings.
    private int x;
    private int y;
    private int DEFAULT_X = 1;
    private int DEFAULT_Y = 1;

    /**
     * position constructor from integer input
     * 
     * @param x The x coordinate for this position
     * @param y The y coordinate for this position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * copy constructor, used in getPosition() methods for high level entities
     * since we desire to return a copy of position for encapsulation reasons.
     * 
     * @param position - The positon object to construct a copy of.
     */
    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    /**
     * Default constructor for this class
     */
    public Position() {
        x = DEFAULT_X;
        y = DEFAULT_Y;
    }

    /**
     * @param x
     * @param y
     * @return Returns true if the inputted coordinates match this objects position
     */
    public boolean positionEquals(int x, int y) {
        // returns true if inputted x and y equal this position objhects x and y
        return (this.x == x && this.y == y);
    }

    /**
     * @param position
     * @return Returns true if the inputted position object matches this object.
     */
    public boolean positionEquals(Position position) {
        // returns true if inputted x and y equal this position objhects x and y
        return (this.x == position.x && this.y == position.y);
    }

    /**
     * Sets the coordinates of this position
     * 
     * @param x # value for x coordianate
     * @param y # value for y coordianate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(Position position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    /**
     * Custom toString for help in debugging
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Get the cell distance between two positions
     * 
     * @param pos1
     * @param pos2
     * @return (int) the cell distance between 2 positions, diagnals counting as 1.
     */
    public static int cellDistance(Position pos1, Position pos2) {
        return Math.max(
                Math.abs(pos1.getX() - pos2.getX()),
                Math.abs(pos1.getY() - pos2.getY()));
    }

    /**
     * Moves position North (up)
     */
    public void moveNorth() {
        y -= 1;
    }

    /**
     * Moves position South (down)
     */
    public void moveSouth() {
        y += 1;
    }

    /**
     * Moves position East (right)
     */
    public void moveEast() {
        x += 1;
    }

    /**
     * Moves position West (left)
     */
    public void moveWest() {
        x -= 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

}
