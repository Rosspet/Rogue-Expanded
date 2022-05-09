import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
public class Map { // TODO throws exception IO wtv it is (and also for main when making stream)
    
    // why where these static when in the world file?
    private static final int DEFAULT_HEIGHT = 4;
    private static final int DEFAULT_WIDTH = 6;
    private final String DEFAULT_LAND = ".";
    private int height; // y
    private int width; // x
    private Tile[][] terrain; 
    // may need to change this to be a single array for jus the rows (height) then 
    // decalre each element to be another arrray when u create;
    /**
     * Default map constructor for default world
     */
    public Map(){
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;
        
        terrain = new Tile[height][];
        
        for (int row=0; row<height; row++){
            terrain[row] = new Tile[width];
            for (int col=0; col<width; col++){
                terrain[row][col] = new Tile(DEFAULT_LAND);
            }
        }
    }

    /** 
     * Map constructor for when given fileName;
     */
    public Map(Scanner inputStream) throws IOException{
        try {
            String line = inputStream.nextLine();
            String[] size = line.split(" ");
            width = Integer.parseInt(size[0]);
            height = Integer.parseInt(size[1]);
            terrain = new Tile[height][];
            String[] terrainRow;

            for (int row=0; row<height; row++){
                terrain[row] = new Tile[width];
                terrainRow = inputStream.nextLine().split("");
                for (int col=0; col<width; col++){
                    terrain[row][col] = new Tile(terrainRow[col]);
                }
            } // should have finished reading just before entities.

        }
        catch (Exception e) { // more speific error? IOException says it is never thrown.
            throw new IOException("An error occured while loading the file.");
            //System.err.println("Error reading from " + GameEngine.getFileName() + ".");
            //System.err.println(e.getMessage());
        }
    }

    public Map(Map map){
        this.terrain = map.getTerrain();
        this.height = terrain.length;
        this.width = terrain[0].length;
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
            terrain[entity.getY()][entity.getX()].addEntity(entity);
            //addToMap(iter.next());
        }

        //entities added, now render
        for(int row=0; row<height; row++){
            for(int col=0; col<width; col++){
                terrain[row][col].render();
            }
            System.out.println("");
        }
        System.out.println("");

    }

    public boolean isValidPosition(Position pos){
        int x = pos.getX();
        int y = pos.getY();
        return 0<=x && x<width && 0<=y && y<height && (terrain[y][x].isTraversible());
    }

    
    public Map getMap(){
        return new Map(this);  // calls copy constructor

    }


    // retruna copy for data access protection
    public Tile[][] getTerrain(){
        Tile[][] terrainCopy = new Tile[height][];
        for (int row=0; row<height; row++){
            terrainCopy[row] = new Tile[width];
            for (int col=0; col<width; col++){
                terrainCopy[row][col]=terrain[row][col];
            }
        }
        return terrainCopy;
    }

    /* public void addToMap(Entity entity){
        tiles[entity.getY()][entity.getX()].addEntity(entity);
    } */

}
