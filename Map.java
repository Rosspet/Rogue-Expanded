import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Map class for keeping track of the terrain size and characteristcs. Contains
 * methods for rendering the map.
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 */
public class Map {

    private static final int DEFAULT_HEIGHT = 4;
    private static final int DEFAULT_WIDTH = 6;
    private final String DEFAULT_LAND = ".";
    private int height; // y
    private int width; // x
    private Tile[][] terrain;

    /**
     * Default map constructor for default world
     */
    public Map() {
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;

        terrain = new Tile[height][];

        for (int row = 0; row < height; row++) {
            terrain[row] = new Tile[width];
            for (int col = 0; col < width; col++) {
                terrain[row][col] = new Tile(DEFAULT_LAND);
            }
        }
    }

    /**
     * Map constructor for when given fileName;
     */
    public Map(Scanner inputStream) throws IOException {
        try {
            readDimensions(inputStream);

            terrain = new Tile[height][];
            String[] terrainRow;

            // Read in terrain, row by row
            for (int row = 0; row < height; row++) {
                terrain[row] = new Tile[width];
                terrainRow = inputStream.nextLine().split("");

                for (int col = 0; col < width; col++) { // Read in this row of the terrain, column by column
                    terrain[row][col] = new Tile(terrainRow[col]);
                }
            } // should have finished reading just before entities.

        } catch (Exception e) { // more speific error? using IOException says it is never thrown.
            throw new IOException("An error occured while loading the file.");
            // System.err.println("Error reading from " + GameEngine.getFileName() + ".");
            // System.err.println(e.getMessage());
        }
    }

    // Copy constructor (used by monster which require knowledge on terrain)
    public Map(Map map) {
        this.terrain = map.getTerrain();
        this.height = terrain.length;
        this.width = terrain[0].length;
    }

    /**
     * Recieves array list of all entities and injects them to the maps tiles, to
     * render correct thing.!
     * 
     * @param entities The entities to be injected to the map for rendering instead
     *                 of the terrain tile ID.
     */
    public void render(ArrayList<Entity> entities) {

        // Add entities to map before rendering
        Iterator<Entity> iter = entities.iterator();
        Entity entity;
        while (iter.hasNext()) {
            entity = iter.next();
            terrain[entity.getY()][entity.getX()].addEntity(entity); // inject into map.
        }

        // entities added, now render
        for (int row = 0; row < height; row++) { // render row
            for (int col = 0; col < width; col++) { // render columns in this row - tile by tile.
                terrain[row][col].render();
            }
            System.out.println("");
        }
        System.out.println("");

    }

    /**
     * Determine wether this is a valid position for entities
     * 
     * @param pos The position of the entity to check if it is valid
     * @return True if the position is valid, else false.
     */
    public boolean isValidPosition(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return 0 <= x && x < width && 0 <= y && y < height && (terrain[y][x].isTraversible());
    }

    // return duplication of this map
    public Map getMap() {
        return new Map(this); // calls copy constructor
    }

    // retruna copy for data access protection
    public Tile[][] getTerrain() {

        Tile[][] terrainCopy = new Tile[height][];

        // Iterate through each tile and make copies.
        for (int row = 0; row < height; row++) {
            terrainCopy[row] = new Tile[width];
            for (int col = 0; col < width; col++) {
                terrainCopy[row][col] = new Tile(terrain[row][col]);
            }
        }
        return terrainCopy;
    }

    private void readDimensions(Scanner inputStream) {
        String line = inputStream.nextLine();
        String[] size = line.split(" ");
        width = Integer.parseInt(size[0]);
        height = Integer.parseInt(size[1]);
    }

}
