import java.util.ArrayList;

public class Tile {

    private static final String TRAVERSIBLE_LAND = ".";
    private boolean traversible;
    private String landType;
    // entities being injected at this location. Going to render the entity at top of list as this the earliest living.
    // can do more advanced entity rendering algorithm later in render. i.e. if prefer monster over item but if not monster then earliest living item. (spec is not clear and tutors haven't responded to my qn on ed.. :( )
    private ArrayList<Entity> entities = new ArrayList<Entity>(); 


    public Tile(String landType){
        this.landType = landType;
        traversible = this.landType.equals(TRAVERSIBLE_LAND) ? true : false;
    }

    public void render(){
        if (entities.size()>0){ // if entity here, render over the tile!

            entities.get(0).render();
            if (!traversible){ // there's an entity in untraversible land. (add proper error checking.)
                System.out.print("NOT TRAVERSIBLE YET ENTITY IS HERE!! ABORT");
            }
            // remove injections! they were just for rendering.
            entities.clear();
            return;
        }
        // no entities, render default tile. 
        System.out.print(landType);
    }

    public boolean isTraversible(){
        return traversible;
    }

    public void addEntity(Entity entity){
        entities.add(entity);
    }
}
