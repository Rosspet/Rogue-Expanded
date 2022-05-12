
/**
 * This class contains logic and information necessary to fasciliate the
 * search scene in which a player can move around a map, navigating towards
 * the monster.
 * @author Ross Petridis | Rpetridis@student.unimelb.edu.au | 1080249
 *
 */
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class World {

    private Player player;
    private Monster monster; // used by default world.
    private ArrayList<Entity> entities; // ArrayList for storing all entities in the world. helps maintain order of
                                        // earliest creation for correct rendering.
    private Map map;
    private Scanner stdInScanner = GameEngine.getStdInScanner();
    private static final String HOME_COMMAND = "home";
    private boolean defaultMap;
    /**
     * Default World Constructor
     * 
     * @param player  a Player object of the player to occupy this world.
     * @param monster a Monster object of the monster to occupy this world.
     */
    public World(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
        this.defaultMap = false;
        entities = new ArrayList<Entity>();
        entities.add(player);
        entities.add(monster);
        map = new Map();
    }

    /**
     * Construct World contained within file connect to given inputStream.
     * 
     * @param player
     * @param inputStream The input stream to read in the world terrain and entities
     *                    from.
     */
    public World(Player player, Scanner inputStream) {
        this.defaultMap = true;
        this.player = player;
        entities = new ArrayList<Entity>();
        entities.add(player);

        try {
            map = new Map(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            injectEntities(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Inject the following entities into this world object - reading them in from
     * the input stream
     * 
     * @param inputStream The stream to read the entities from
     * @throws IOException if an error occurs whilst parsing or reading input.
     */
    private void injectEntities(Scanner inputStream) throws IOException {

        String[] entityData;
        try {
            while (inputStream.hasNextLine()) { // while still entities left to parse
                entityData = inputStream.nextLine().split(" ");
                switch (entityData[0]) { // get entity type to create to correct object of subclass.
                    case "player":
                        player.setPosition(Integer.parseInt(entityData[1]), Integer.parseInt(entityData[2]));
                        entities.add(player);
                        break;
                    case "monster":
                        entities.add(new Monster(
                                Integer.parseInt(entityData[1]), // x pos
                                Integer.parseInt(entityData[2]), // y pos
                                entityData[3], // Name
                                Integer.parseInt(entityData[4]), // Health
                                Integer.parseInt(entityData[5]) // Damage
                        ));
                        break;
                    case "item":
                        entities.add(new Item(
                                Integer.parseInt(entityData[1]),
                                Integer.parseInt(entityData[2]),
                                entityData[3]));
                        break;
                }
            }
        } catch (Exception e) {
            throw new IOException("An error occured while loading the file.");
        }
    }

    /**
     * Logic for running the main search scene and game loop in which the entities
     * move around and interact with each other.
     * 
     * @return True if the level is completed, false if the home command was issued.
     */
    public boolean runGameLoop() {

        String cmd;
        ArrayList<Entity> encountered;
        boolean levelOver = false;

        do {
            map.render(entities);

            // get player command
            System.out.print(GameEngine.CMD_PROMPT);
            cmd = stdInScanner.nextLine();

            if (cmd.equals(HOME_COMMAND)) {
                return true;
            }

            // move monsters & action the player cmd
            moveMonsters();
            parseAction(cmd);
            // process player-entity encounters
            encountered = encountered(); // check for encounters
            levelOver = processEncounters(encountered); // combine with above?

        } while (!levelOver);

        // level terminated naturally.
        return false;
    }

    /**
     * calls all monsters to make their move.
     */
    private void moveMonsters() {

        Entity thisEntity;
        Map mapCopy = new Map(map);
        Iterator<Entity> iter = entities.iterator();

        while (iter.hasNext()) { // while still entities to check if they're monsters and move them
            thisEntity = iter.next();
            if (thisEntity instanceof Monster) {
                ((Monster) thisEntity).makeMove(player.getPosition(), mapCopy);
            }

        }
    }

    /**
     * Process all encounters between player and other entities. Monsters are
     * actioned first as design choice.
     * 
     * @param encountered The entities encounterd by the player in this move
     * @return True if the level is over (player died or player obtained warp token)
     */
    private boolean processEncounters(ArrayList<Entity> encountered) {

        boolean playerDead;
        boolean warpTokenObtained;
        Entity thisEntity;
        Iterator<Entity> iter = encountered.iterator();

        // monsters first
        while (iter.hasNext()) {
            thisEntity = iter.next();
            if (thisEntity instanceof Monster) { // if this encountered entity, is a monster -> staart a battle

                Battle battle = new Battle(player, (Monster) thisEntity);
                playerDead = battle.runBattleScene();

                if (playerDead) {
                    return true;
                } else { // monster dead.
                    entities.remove(thisEntity);
                }
            }
        }
        // monsters done. Now, items.
        iter = encountered.iterator();
        while (iter.hasNext()) {
            thisEntity = iter.next();
            if (thisEntity instanceof Item) { // if this entity is an item, interact the item with player!

                warpTokenObtained = ((Item) thisEntity).interactWith(player); // player.parseItem(((Item)thisEntity).getID());
                entities.remove(thisEntity);
                if (warpTokenObtained) {
                    return true;
                }
            }
        }

        // additional check for gameOver in default world.
        if (defaultMap && noMonstersLeft()) {
            monster.resetPosition();
            return true; // game fin.
        }
        return false; // player not dead. wapItem not obtained, not (default and mosnters dead)
    }

    /**
     * used for default game play to determine whether the game should terminate.
     * 
     * @return True if no monsters are left in the world.
     */
    private boolean noMonstersLeft() {
        Iterator<Entity> iter = entities.iterator();
        while (iter.hasNext()) {
            if (iter.next() instanceof Monster) {
                return false;
            }
        }
        return true;
    }

    /**
     * Find all entities the player has encountered right now.
     * 
     * @return ArrayList<Entity> off all the entities the player has encountered.
     *         Return list is order in order of waht was added to the world first.
     */
    private ArrayList<Entity> encountered() {
        // have this return anything the player encounters
        ArrayList<Entity> encounteredEntities = new ArrayList<Entity>();
        Entity entity;
        Iterator<Entity> iter = entities.iterator();

        // checking for equal positions between player and all entities. Keeping track
        // of those in position of player.
        while (iter.hasNext()) {
            entity = iter.next();
            if (player.positionEquals(entity.getPosition())) {
                encounteredEntities.add(entity);
            }
        }

        return encounteredEntities;
    }

    /**
     * Logic for parsing inputted player actions. Mainly 'w','a','s','d' or 'home'
     * 
     * @param action A string representing the input action by player.
     * @return Returns True if the player entered a valid move. Else, returns false
     *         if the player is atempting to move out of bounds, or if an invalid command is
     *         entered.
     */
    private boolean parseAction(String action) {
        // returns false if invalid move.
        switch (action) {
            case "w":
                player.moveNorth();
                break;
            case "s":
                player.moveSouth();
                break;
            case "a":
                player.moveWest();
                break;
            case "d":
                player.moveEast();
                break;
            default:
                // System.out.println("Invalid action entered. Valid actions:
                // 'w','a','s','d','home'");
                return false; // invalid char entered. Ignore!
        }

        // check if player is in a valid pos.
        if (!map.isValidPosition(player.getPosition())) {
            player.undoMove(action); // not valid, go back!
            return false;
        }
        return true; // all good.
    }

    /**
     * Resets the players Position to its default initial values.
     */
    public void reset() {
        player.resetPosition();
        player.resetDamage();
        if (defaultMap) {
            monster.resetPosition();
        }
    }
}

// for (int i=0; i<entities.size(); i++){
// thisEntity = entities.get(i);
