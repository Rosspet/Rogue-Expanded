
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

    // Each entity should know how to render itself.
    public abstract void render();

}
