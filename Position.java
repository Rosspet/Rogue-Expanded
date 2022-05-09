
/**
 * This class can be used to make position objects which can store an entities x,y coordinates.
 * This class comes with methods for moving a position in each of 4 basic 
 * compass direcitons: N,S,E,W. 
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 */
public class Position{  // ENTITY HAS ALL THESE PUBLIC THINGS - including the movings.
    private int x;
    private int y;
    
    /**
     * position constructor from integer input
     * @param x The x coordinate for this position
     * @param y The y coordinate for this position
     */
    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }
    
    /**
     *  copy constructor, used in getPosition() methods for high level entities
     * since we desire to return a copy of position for encapsulation reasons. 
     * @param position - The positon object to construct a copy of.
     */
    public Position(Position position){
        this.x = position.x;
        this.y = position.y;
    }
    
    /**
     * Default constructor for this class
     */
    public Position(){
        x=1;
        y=1;
    }
    
    
    /**
     * Returns true if the inputted coordinates match this objects position
     * @param x
     * @param y
     * @return
     */
    public boolean positionEquals(int x, int y){
        // returns true if inputted x and y equal this position objhects x and y
        return (this.x==x && this.y==y);
    }
    /**
     * Returns true if the inputted position object matches this object.
     * @param position
     * @return
     */
    public boolean positionEquals(Position position){
        // returns true if inputted x and y equal this position objhects x and y
        return (this.x==position.x && this.y==position.y);
    }
    /**
     * Sets the coordinates of this position
     * @param x # value for x coordianate
     * @param y # value for y coordianate
     */
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setPosition(Position position){
        this.x = position.getX();
        this.y = position.getY();
    }

    /**
     * Custom toString for help in debugging
     */
    public String toString(){
        return "("+x+","+y+")";
    }


    /** 
     * Moves position North (up)
     */
    public void moveNorth(){
        y-=1;
    }
    /**
     * Moves position South (down)
     */
    public void moveSouth(){
        y+=1;
    }
    /**
     * Moves position East (right)
     */
    public void moveEast(){
        x+=1;
    }
    /**
     * Moves position West (left)
     */
    public void moveWest(){
        x-=1;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
    public Position getPosition(){
        return new Position(x,y);
    }

    public static int cellDistance(Position pos1, Position pos2){
        return Math.max(
            Math.abs(pos1.getX() - pos2.getX()),
            Math.abs(pos2.getY() - pos2.getY())
            );
    }


}
    