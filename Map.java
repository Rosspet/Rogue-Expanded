import java.util.ArrayList;
import java.util.Iterator;

public class Map {
    
    // why where these static when in the world file?
    private static final int DEFAULT_HEIGHT = 4;
    private static final int DEFAULT_WIDTH = 6;
    private final String DEFAULT_LAND = ".";
    private int height; // y
    private int width; // x
    private Tile[][] tiles; 
    // may need to change this to be a single array for jus the rows (height) then 
    // decalre each element to be another arrray when u create;
    /**
     * Default map constructor for default world
     */
    public Map(){
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;
        
        tiles = new Tile[height][];
        
        for (int row=0; row<height; row++){
            tiles[row] = new Tile[width];
            for (int col=0; col<width; col++){
                tiles[row][col] = new Tile(DEFAULT_LAND);
            }
        }
    }

    /** 
     * Map constructor for when given fileName;
     */
    public Map(String fileName){
        
    }
    /**
     * Recieves array list of all entities and injects them to the maps tiles, to render correct thing.!
     * @param entities
     */
    public void render(ArrayList<Entity> entities){

        // Add entities to map before rendering
        Iterator<Entity> iter = entities.iterator();
        Entity entity;
        while(iter.hasNext()){
            entity = iter.next();
            tiles[entity.getY()][entity.getX()].addEntity(entity);
            //addToMap(iter.next());
        }

        //entities added, now render
        for(int row=0; row<height; row++){
            for(int col=0; col<width; col++){
                tiles[row][col].render();
            }
            System.out.println("");
        }
        System.out.println("");

    }

    public boolean isValidPosition(Position pos){
        int x = pos.getX();
        int y = pos.getY();
        return 0<=x && x<width && 0<=y && y<height;
    }

    /* public void addToMap(Entity entity){
        tiles[entity.getY()][entity.getX()].addEntity(entity);
    } */

}