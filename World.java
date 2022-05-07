/**
 * This class contains logic and information necessary to fasciliate the
 * search scene in which a player can move around a map, navigating towards
 * the monster.
 * @author Ross Petridis | Rpetridis@student.unimelb.edu.au | 1080249
 *
 */

import java.util.ArrayList;

public class World {
    
    private static final String HOME_COMMAND = "home";
    private Player player;
    private Monster monster;  // change to be array list of monster!
    private Map map;

    /**
     * World Constructor
     * @param player a Player object of the player to occupy this world.
     * @param monster a Monster object of the monster to occupy this world.
     */
    public World(Player player, Monster monster){
        //pass in references to objects incase players level up, loose health etc.
        // will need to add getter and setter methods for accessing levels and changing form this class.
        this.player = player; 
        this.monster = monster; 
        map = new Map();
    }

    public World(Player player, String fileName){
        this.player = player;
        map = new Map(fileName);
        
    }

    /**
     * Logic for running the search scene in which the player can move around and 
     * navigate towards the monster.
     * @return True if the player and monster encounter each other, else, returns False if player returns to home.
     */
    public boolean runSearchScene(){
		boolean validAction = true;
		String cmd;
        ArrayList<Entity> entities = new ArrayList<Entity>();
        entities.add(player);
        entities.add(monster);
		while (!encountered()){ // while not encountered yet.
            System.out.println("bop");
			map.render(entities);
			cmd = GameEngine.scanner.next();
			if (cmd.equals(HOME_COMMAND)){
				return false;
			}
			validAction = parseAction(cmd);
		} 

		System.out.println(player.getName() + " encounterd a " + monster.getName() + "!\n");
		return true;
	}

    /**
     * Engine for rendering the game map for each valid move made. (URRRR- now being done in map with entity injection)
     */
    /*
    private void render(){
        // render the current game world.
        for (int y=0; y<HEIGHT; y++){
            for (int x=0; x< WIDTH; x++){

                if (player.getPosition().positionEquals(x,y)) {
                    player.render();
                }
                else if (monster.getPosition().positionEquals(x,y)) {
                    monster.render();
                }
                else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
    }
    */

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
     * Logic for determing whether the player and monster are in the same positon (i.e, have encountered each other)
     * @return Returns True if the monster and player have the same positions, else False. 
     */
    public boolean encountered(){
        // returns true if monster and player and in the same position.
        return player.getPosition().positionEquals(monster.getPosition());
    } 

    /**
     * Resets the players Position to its default initial values.
     */
    public void reset(){
        player.resetPosition();
    }
}   




