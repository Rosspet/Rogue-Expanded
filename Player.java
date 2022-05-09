import java.util.Scanner;
/**
 * This "Player" class is used to create player objects to store information 
 * about each player. This class comes with methods for updating a players position.
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 *
 */
public class Player extends Creature{

    private static final int INITIAL_POS_X = 1;
    private static final int INITIAL_POS_Y = 1;
    private static final int INITIAL_LEVEL = 1;
    private static final int INITIAL_DAMAGE = 1;
    private static final int INITIAL_HEALTH = 17;
    private static final int HEALTH_MULTIPLIER = 3;

    private int level;

    //private Position position = new Position(INITIAL_POS_X,INITIAL_POS_Y);
    
    /**
     * No arg constructor for initialisation of player and its attributes to default.
     */
    public Player(){
        level = INITIAL_LEVEL; 
        setDamage(INITIAL_DAMAGE + level);
        setHealth(INITIAL_HEALTH + level * HEALTH_MULTIPLIER);
        setMaxHealth(getHealth()); // health initialised to max!
    }
    /**
     * Constructor for when loading from file
     */
    public Player(String name, int level){
        this.level = INITIAL_LEVEL; 
        setDamage(INITIAL_DAMAGE + level);
        setHealth(INITIAL_HEALTH + level * HEALTH_MULTIPLIER);
        setMaxHealth(getHealth()); // health initialised to max!
    }

    /**
     * This method is used to set the non default player attributes, such as the name.
     */
    public void createPlayer(){
        if (getName()!=null){
            printPlayerInfo();
        }
        else{
            Scanner scanner = GameEngine.scanner;
            System.out.println("What is your character's name?");
            setName(scanner.next()); 
            System.out.println(String.format("Player '%s' created.\n", getName()));
        }      
    }

    /**
     * Displays a players name and Health for the main menu.
     */
    public void displayNameAndHealth(){

        System.out.print("Player: ");
        if(getName() != null){
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
        System.out.print(getName() + " " + getHealth() + "/" + getMaxHealth());
    }

    /**
     * Logic for printing a players information in the main menu.
     */
    public void printPlayerInfo(){
            System.out.println(String.format("%s (Lv. %d)", getName(), getLevel()));
			System.out.println("Damage: " + getDamage());
			System.out.print(String.format("Health: %d/%d\n\n", getHealth(), getMaxHealth()));
    }

    /**
     * Undoes the previous move by performing the opposite update
     * on the players positon to what was initially peformed
     * @param action The initial action which resulted in an invalid position and the
     */
    public void undoMove(String action){
        switch (action){
            case "w":
                moveSouth();
                break;
            case "s":
                moveNorth();
                break;
            case "d":
                moveWest();
                break;
            case "a":
                moveEast();
                break;
            default:
                System.out.println("Internal Error: Code called UndowMove with invalid action");
                System.exit(0);   
                //throw new Exception("Internal Error: Code called UndowMove with invalid action");
        }
    }

    /**
     * Resets a players positon to the initial default values.
     */
    public void resetPosition(){
        setPosition(INITIAL_POS_X, INITIAL_POS_Y);
    }
    
    public int getLevel(){
        return level;
    }
    public void setLevel(int level){
        this.level = level;
    }

    public void render(){
        System.out.print(Character.toUpperCase(getName().charAt(0)));
    }

    public void increaseHealth(){
        // CHECK NOT PASSING THE MAXIMUM HEALTH!!
    }

    // returns true if the item parsed is the warptoken as the game should end if in custom map!
    public boolean parseItem(String effectID){
        
        switch (effectID){
            case "+":
                setHealth(getMaxHealth());
                System.out.println("Max health'd");
                break;
            case "^":
                setDamage(getDamage()+1);
                System.out.println("Damage up");
                break;
            case "@":
                levelUp();
                System.out.println("leveled up!");
                return true;
            default:
                System.err.println("Unkown Item reciefed:" + effectID);
                break;
        }
        return false;
    }

    public void resetDamage(){
        setDamage(INITIAL_DAMAGE + level);
    }

    private void levelUp(){
        
        level+=1;
    }



}

