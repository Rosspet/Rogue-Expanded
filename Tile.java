import java.util.ArrayList;

/**
 * this Class contains logic and information necessary to maintain an individual
 * terrain block in the map in the world.
 */
public class Tile {

    private static final String TRAVERSIBLE_LAND = ".";
    private boolean traversible;
    private String landType;
    private ArrayList<Entity> entities = new ArrayList<Entity>(); // ArrayList to inject entities into when rendering.

    /**
     * Simple Constructor
     * 
     * @param landType The Char representing this land.
     */
    public Tile(String landType) {
        this.landType = landType;
        traversible = this.landType.equals(TRAVERSIBLE_LAND) ? true : false;
    }

    /**
     * Copy constructor
     * @param tile The tile to make a duplicate of
     * @return Copy of inputted Tile object
     */
    public Tile(Tile tile){
        this.landType = tile.landType;
        this.traversible = tile.traversible;
    }



    /**
     * Method for rendering this tile. Will render the landType unless an entity(s)
     * are here, in which case, the entity first added into the tile will be
     * rendered.
     */
    public void render() { // throws Exception
        if (entities.size() > 0) { // if entity here, render over the tile!
            /*if (!traversible) { 
                throw new Exception("Attemptig to render an entity on an untraversible tile! How did the entity get here?");
            }*/
            entities.get(0).render();
            entities.clear(); // remove injected entities. They're maintained in world.
            return;
        }
        // no entities, render default tile.
        System.out.print(landType);
    }

    /**
     * 
     * @return Boolean true if this block is traverisble by a creature
     */
    public boolean isTraversible() {
        return traversible;
    }

    /**
     * Injecting entity at this tile location
     * 
     * @param entity - The entity being added to this tile
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
}
