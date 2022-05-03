/**
 * This class contains logic and information necessary to fasciliate the
 * search scene in which a player can move around a map, navigating towards
 * the monster.
 * @author Ross Petridis | Rpetridis@student.unimelb.edu.au | 1080249
 *
 */
public class World {
    private static final int HEIGHT = 4;
    private static final int WIDTH = 6;
    private static final String HOME_COMMAND = "home";
    private Player player;
    private Monster monster;

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
    }

    /**
     * Logic for running the search scene in which the player can move around and 
     * navigate towards the monster.
     * @return True if the player and monster encounter each other, else, returns False if player returns to home.
     */
    public boolean runSearchScene(){
		boolean validAction = true;
		String cmd;
        
		while (!encountered()){ // while not encountered yet.
            
			render();
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
     * Engine for rendering the game map for each valid move made.
     */
    private void render(){
        // render the current game world.
        for (int y=0; y<HEIGHT; y++){
            for (int x=0; x< WIDTH; x++){

                if (player.getPosition().equals(x,y)) {
                    System.out.print(Character.toUpperCase(player.getName().charAt(0)));
                }
                else if (monster.getPosition().equals(x,y)) {
                    System.out.print(Character.toLowerCase(monster.getName().charAt(0)));
                }
                else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
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
                player.movePlayerNorth();
                break;
            case "s":
                player.movePlayerSouth();
                break;
            case "a":
                player.movePlayerWest();
                break;
            case "d":
                player.movePlayerEast();
                break;
            default:
                System.out.println("Invalid action entered. Valid actions: 'w','a','s','d','home'");
                return false; //invalid char entered. Ignore!
            }

        // check if player is in a valid pos.
        if (!isValidPosition(player.getPosition())){
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
    private boolean isValidPosition(Position pos){
        int x = pos.getX();
        int y = pos.getY();
        return 0<=x && x<WIDTH && 0<=y && y<HEIGHT;
    }

    /**
     * Logic for determing whether the player and monster are in the same positon (i.e, have encountered each other)
     * @return Returns True if the monster and player have the same positions, else False. 
     */
    public boolean encountered(){
        // returns true if monster and player and in the same position.
        return player.getPosition().equals(monster.getPosition());
    } 

    /**
     * Resets the players Position to its default initial values.
     */
    public void reset(){
        player.resetPosition();
    }
}   




