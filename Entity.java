
public abstract class Entity extends Position{ // we actually dont want this to Extend position, but we do want it to have a position object!
    
    //private static String id; // keep in subclasses as will be name or itemType.
    //private Position position; // ok to have this public?

    public Entity(Position position){
        super(position);
        //this.position = new Position(position);
    }
    public Entity(int x, int y){
        super(x, y);
        //this.position = new Position(x, y);
    }

    public Entity(){
        super();
    }
    

    // Each entity should know how to render itself.
    public abstract void render();

    /**
     * getPosition so the can access position inside of the Creature classes (and alll sub classes (monster+player))
     * @return
     */
    



    

}
