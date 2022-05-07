import java.util.Scanner;

/**
 * This "Player" class is used to create player objects to store information 
 * about each player. This class comes with methods for updating a players position.
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 *
 */
public class Player {

    private static final int INITIAL_POS_X = 1;
    private static final int INITIAL_POS_Y = 1;
    private static final int INITIAL_LEVEL = 1;
    private static final int INITIAL_DAMAGE = 1;
    private static final int INITIAL_HEALTH = 17;
    private static final int HEALTH_MULTIPLIER = 3;

    private String name;
    private int level;
    private int damage; 
    private int maxHealth;
    private int health;
    private Position position = new Position(INITIAL_POS_X,INITIAL_POS_Y);
    
    /**
     * No arg constructor for initialisation of player and its attributes to default.
     */
    public Player(){
        level = INITIAL_LEVEL; 
        damage = INITIAL_DAMAGE + level;
        maxHealth = INITIAL_HEALTH + level * HEALTH_MULTIPLIER;
        health = maxHealth; // health initialised to max!

    }

    /**
     * This method is used to set the non default player attributes, such as the name.
     */
    public void createPlayer(){
        if (name!=null){
            printPlayerInfo();
        }
        else{
            Scanner scanner = GameEngine.scanner;
            System.out.println("What is your character's name?");
            name = scanner.next();
            System.out.println(String.format("Player '%s' created.\n", name));
        }      
    }

    /**
     * Displays a players name and Health for the main menu.
     */
    public void displayNameAndHealth(){

        System.out.print("Player: ");
        if(name != null){
            displayStatus();//name + " " + health + "/" + maxHealth;
        } 
        else{
            System.out.print("[None]");
        }

    }
    
    /**
     * Displays a players status for during battle.
     */
    public void displayStatus(){
        System.out.print(name + " " + health + "/" + maxHealth);
    }

    /**
     * Logic for printing a players information in the main menu.
     */
    public void printPlayerInfo(){
            System.out.println(String.format("%s (Lv. %d)", name, level));
			System.out.println("Damage: " + damage);
			System.out.print(String.format("Health: %d/%d\n\n", health, maxHealth));
    }

    /**
     * heals player to their max health
     */
    public void heal(){
        health=maxHealth;
    }

    /**
     * Get players position.
     * @return Returns a copy of the players position to avoid data leak.
     */
    public Position getPosition(){
        return new Position(position); 
    }

    /*
    The below "move" methods call updates to the players position. The players position is not 
    avaiable to be updated from the world as it was made private due to data leak concerns.
    Thus, these methods are required to enable position changes from the world class.
    */

    /**
     * Moves player east.
     */
    public void movePlayerEast(){
        position.moveEast();
    }
    /**
     * Moves player west
     */
    public void movePlayerWest(){
        position.moveWest();
    }
    /**
     * Moves player north
     */
    public void movePlayerNorth(){
        position.moveNorth();
    }
    /**
     * Move player south
     */
    public void movePlayerSouth(){
        position.moveSouth();
    }

    /**
     * Undoes the previous move by performing the opposite update
     * on the players positon to what was initially peformed
     * @param action The initial action which resulted in an invalid position and the
     */
    public void undoMove(String action){
        switch (action){
            case "w":
                movePlayerSouth();
                break;
            case "s":
                movePlayerNorth();
                break;
            case "d":
                movePlayerWest();
                break;
            case "a":
                movePlayerEast();
                break;
            default:
                System.out.println("Internal Error: Code called UndowMove with invalid action");
                System.exit(0);   
                //throw new Exception("Internal Error: Code called UndowMove with invalid action");
        }
    }

    /**
     * Hurts a player by decreasing their health 
     * @param damage The amount to decrease the players health by.
     * @return Returns True if the player is dead.
     */
    public boolean hurt(int damage){
        health-=damage;
        if (health<=0){
            return true;
        }
        return false; // not dead
    } 

    /**
     * Resets a players positon to the initial default values.
     */
    public void resetPosition(){
        position.setPosition(INITIAL_POS_X, INITIAL_POS_Y);
    }
    
    public String getName(){
        return name;
    }
    public int getLevel(){
        return level;
    }
    public int getDamage(){
        return damage;
    }
    public int getMaxHealth(){
        return maxHealth;
    }
    public int getHealth(){
        return health;
    }

}

