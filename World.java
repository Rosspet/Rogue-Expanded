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
    private ArrayList<Entity> entities;
    private Map map;
    private Scanner stdInScanner  = GameEngine.getStdInScanner();
    private static final String HOME_COMMAND = "home";

    /**
     * Default World Constructor
     * @param player a Player object of the player to occupy this world.
     * @param monster a Monster object of the monster to occupy this world.
     */
    public World(Player player, Monster monster){
        this.player = player; 
        this.monster = monster;
        entities = new ArrayList<Entity>();
        entities.add(player);
        entities.add(monster);
        map = new Map();
    }

    public World(Player player, Scanner inputStream){
        this.player = player;
        entities = new ArrayList<Entity>();
        entities.add(player);

        try{
            map = new Map(inputStream); 
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        try {
            injectEntities(inputStream);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        
    }

    private void injectEntities(Scanner inputStream) throws IOException{
        
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
     * @return True if the returnHome command was issued, else false.
     */
    public boolean runSearchScene(){ // rename to game Loop ? 
		//boolean validAction = true; // originally had it to only re-render if valid input
		String cmd;
        ArrayList<Entity> encountered;
        boolean levelOver = false;

		do {
            map.render(entities);

            // get player command
            System.out.print(GameEngine.CMD_PROMPT);
            cmd = stdInScanner.nextLine();

            if (cmd.equals(HOME_COMMAND)){
				return true;
			}
            
            // move monsters, parse player cmd, process player-entity encounters
            moveMonsters();
            parseAction(cmd);
            encountered = encountered(); // check for encounters
            levelOver = processEncounters(encountered); // combine with above?
			
		} while(!levelOver);

        // level terminated naturally.
		return false;
	}

    private void moveMonsters(){
        Entity thisEntity;
        Map mapCopy = new Map(map);
        for (int i=0; i<entities.size(); i++){
            thisEntity = entities.get(i);
            if (thisEntity instanceof Monster){
                ((Monster)thisEntity).makeMove(player.getPosition(), mapCopy);
            }
        }
    }


    // return true if level over!
    // process all the encountered entities, monsters first!
    private boolean processEncounters(ArrayList<Entity> encountered){
        
        boolean playerDead;
        boolean warpTokenObtained;
        Entity thisEntity;
        
        //monsters first
        Iterator<Entity> iter = encountered.iterator();

        while(iter.hasNext()){
            thisEntity = iter.next();
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
        // monsters done. Now, items.
        iter = encountered.iterator();
        while(iter.hasNext()){
            thisEntity = iter.next();
            if (thisEntity instanceof Item){

                warpTokenObtained = player.parseItem(((Item)thisEntity).getID());
                entities.remove(thisEntity);
                if (warpTokenObtained){
                    return true;
                } 
            }
        }

        // additional check for gameOver in default world.
        if (GameEngine.getGameMode() && noMonstersLeft()){
            monster.resetPosition();
            return true; // game fin.
        }

        return false; // player not dead. wapItem not obtained, not (default and mosnters dead)
    }

    private boolean noMonstersLeft(){
        Iterator<Entity> iter = entities.iterator();
        while(iter.hasNext()){
            if (iter.next() instanceof Monster){
                return false;
            }
        }
        return true; 
    }

    /** UPDATE
     * Logic for determing whether the player and monster are in the same positon (i.e, have encountered each other)
     * @return Returns True if the monster and player have the same positions, else False. 
     */
    private ArrayList<Entity> encountered(){
        // have this return anything the player encounters
        ArrayList<Entity> encounteredEntities = new ArrayList<Entity>();
        Entity entity;

        Iterator<Entity> iter = entities.iterator();
        while(iter.hasNext()){
            entity = iter.next();
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
                //System.out.println("Invalid action entered. Valid actions: 'w','a','s','d','home'");
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
        if (GameEngine.getGameMode()){
        monster.resetPosition();
        }
    }
}   




