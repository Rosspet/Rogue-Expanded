/**
 * Entity abstract class for all entities in the world.
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 */
public abstract class Entity extends Position{

    public Entity(Position position){
        super(position);
    }
    public Entity(int x, int y){
        super(x, y);
    }

    public Entity(){
        super();
    }

    /**
     * Every entity should know how to render itself.
     */
    public abstract void render();

}
