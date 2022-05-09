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
    
    private static final String HOME_COMMAND = "home";
    private Player player;
    private Monster monster;  // change to be array list of monster!
    private Map map;
    private ArrayList<Entity> entities;

    /**
     * Default World Constructor
     * @param player a Player object of the player to occupy this world.
     * @param monster a Monster object of the monster to occupy this world.
     */
    public World(Player player, Monster monster){
        //pass in references to objects incase players level up, loose health etc.
        // will need to add getter and setter methods for accessing levels and changing form this class.
        this.player = player; 
        this.monster = monster; 
        entities = new ArrayList<Entity>();
        entities.add(player);
        entities.add(monster);
        map = new Map();
    }

    public World(Player player, Scanner inputStream){ //GAME ENGINE SHOULD SET UP FILE STREAM
        this.player = player;
        entities = new ArrayList<Entity>();
        entities.add(player);
        try{
            map = new Map(inputStream);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        // map read top seciton, then anothe function reads te entities section
        //map = new Map(fileName);
        try {
            injectEntities(inputStream);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            
        }
        
    }

    private void injectEntities(Scanner inputStream) throws IOException{
        //String Line;
        String[] entityData;
        try{
            while (inputStream.hasNextLine()){
                entityData = inputStream.nextLine().split(" ");
                switch(entityData[0]){
                    case "player":
                        player.setPosition(Integer.parseInt(entityData[1]), Integer.parseInt(entityData[2]));
                        entities.add(player);
                        break;
                    case "monster":
                        entities.add(new Monster(
                            Integer.parseInt(entityData[1]),  // x pos
                            Integer.parseInt(entityData[2]),  // y pos
                            entityData[3], // Name
                            Integer.parseInt(entityData[4]), // Health
                            Integer.parseInt(entityData[5])  // Damage
                        ));
                        break;
                    case "item":
                        entities.add(new Item(
                            Integer.parseInt(entityData[1]), 
                            Integer.parseInt(entityData[2]), 
                            entityData[3]
                        ));
                        break;
                    }
            }
        }
        catch (Exception e){
            throw new IOException("An error occured while loading the file.");
        }
    }

    /**
     * Logic for running the search scene in which the player can move around and 
     * navigate towards the monster.
     * @return True if the player and monster encounter each other, else, returns False if player returns to home.
     */
    public boolean runSearchScene(){
		boolean validAction = true; // originally had it to only re-render if valid input
		String cmd;
        ArrayList<Entity> encountered;
        /*
        ArrayList<Entity> entities = new ArrayList<Entity>();
        entities.add(player);
        entities.add(monster); */
        map.render(entities); // REMOVE LATER
        boolean levelOver=false;
		do { // while player not dead and warpstone still in world and not default. or if default, return after encounter of if player of monster dead.(make a function for this)        // while not encountered yet. !encountered()
            map.render(entities);
            cmd = GameEngine.scanner.next();
            if (cmd.equals(HOME_COMMAND)){
                
				return false;
			}
            // do monster actions first, otherwise they know your next move!
            // probably best to do both seperately then inject.
            moveMonsters();
            validAction = parseAction(cmd); // TODO: add chagne were invalid commands (OR EMPTY) still result in monsters moving!
            encountered = encountered();
            levelOver = processEncounters(encountered);
			
		} while(!levelOver);
        
		//System.out.println(player.getName() + " encountered a " + monster.getName() + "!\n");
		return true;
	}

    private void moveMonsters(){
        Entity thisEntity;
        //Tile[][] terrain = map.getTerrain();
        for (int i=0; i<entities.size(); i++){
            thisEntity = entities.get(i);
            if (thisEntity instanceof Monster){
                ((Monster)thisEntity).makeMove(player.getPosition(), new Map(map));
                
            }
        }
    }

    


    // return true if player dead
    private boolean processEncounters(ArrayList<Entity> encountered){
        // process all the entities, monsters first!
        boolean playerDead;
        boolean warpTokenObtained;
        Entity thisEntity;
        for (int i=0; i<encountered.size(); i++){ // replcae with iterator. and while loop
            thisEntity = encountered.get(i);
            if (thisEntity instanceof Monster){
                
                Battle battle = new Battle(player, (Monster)thisEntity);
                playerDead = battle.runBattleScene();
                if (playerDead){
                    return true;
                }
                else { // monster dead.
                    entities.remove(thisEntity);
                }
            }
        }
        // monsters all done, just Items left. 
        for (int i=0; i<encountered.size(); i++){
            thisEntity = encountered.get(i);
            if (thisEntity instanceof Item){
                warpTokenObtained = player.parseItem(((Item)thisEntity).getID());
                entities.remove(thisEntity);
                if (warpTokenObtained){
                    System.out.println("--- level up ---");
                    return true; // warpitemObtained.
                } 
            }
        }

        if (GameEngine.getGameMode() && noMonstersLeft()){
            return true; // game fin.
        }

        return false; // player not dead. wapItem not obtained
    }

    private boolean noMonstersLeft(){
        Iterator<Entity> iter = entities.iterator();
        while(iter.hasNext()){
            if (iter.next() instanceof Monster){
                return false;
            }
        }
        return true; //didnt return -> monsters all gone!
    }

    /**
     * Logic for determing whether the player and monster are in the same positon (i.e, have encountered each other)
     * @return Returns True if the monster and player have the same positions, else False. 
     */
    private ArrayList<Entity> encountered(){
        // have this return anything the player encounters
        ArrayList<Entity> encounteredEntities = new ArrayList<Entity>();
        Entity entity;
        for (int i=0; i<entities.size(); i++){ //want to iterate through the actual objects since these may need to be removed from world.
            // will iterate through entites from earliest in the world to youngest.
            entity = entities.get(i); 
            if (player.positionEquals(entity.getPosition())){
                encounteredEntities.add(entity);
            }
        }

        return encounteredEntities;
    } 


    /**
     * Logic for parsing inputted player actions. Mainly 'w','a','s','d' or 'home'
     * 
     * @param action A string representing the input action by player.
     * @return Returns True if the player entered a valid move. Else, returns false if the player
     * is atempting to move out of bounds, or if an invalid command is entered.
     */
    private boolean parseAction(String action){
        //returns false if invalid move.
        switch (action){
            //Position playerPosition = player.getPosition(); // returns a copy.
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
                System.out.println("Invalid action entered. Valid actions: 'w','a','s','d','home'");
                return false; //invalid char entered. Ignore!
            }

        // check if player is in a valid pos.
        if (!map.isValidPosition(player.getPosition())){
            player.undoMove(action); //not valid, go back!
            return false;
        }
        return true; // all good.
    }

    /**
     * Method for checking wether a Position is a valid position in the games world.
     * @param pos A Position object containging x and y coordinates to check whether they're valid
     * @return Returns True if the pos is a valid position, else, False.
     */
    

    /**
     * Resets the players Position to its default initial values.
     */
    public void reset(){
        player.resetPosition();
        player.resetDamage();
    }
}   




