import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream; 


/**
 * This "Player" class is used to create player objects to store information 
 * about each player. This class comes with methods for updating a players position.
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 *
 */
public class Player extends Creature {

    private static final int INITIAL_POS_X = 1;
    private static final int INITIAL_POS_Y = 1;
    private static final int INITIAL_LEVEL = 1;
    private static final int INITIAL_DAMAGE = 1;
    private static final int INITIAL_HEALTH = 17;
    private static final int HEALTH_MULTIPLIER = 3;
    private static final String SAVE_FILE_NAME = "player.dat";
    private static final String NORTH = "w";
    private static final String SOUTH = "s";
    private static final String EAST = "d";
    private static final String WEST = "a";

    private int level;

    /**
     * Constructor for when loading from file
     */
    public Player(String name, int level){
        super(
            new Position(),
            name, 
            INITIAL_HEALTH + level * HEALTH_MULTIPLIER, // initial health
            INITIAL_HEALTH + level * HEALTH_MULTIPLIER, // max health
            INITIAL_DAMAGE + level // initial damage
        );
        /*this.level = INITIAL_LEVEL; 
        setDamage(INITIAL_DAMAGE + level);
        setHealth(INITIAL_HEALTH + level * HEALTH_MULTIPLIER);
        setMaxHealth(getHealth());*/ // health initialised to max!
    }

    //private Position position = new Position(INITIAL_POS_X,INITIAL_POS_Y);
    
    /**
    * No arg constructor for initialisation of player and its attributes to default but leaving name as None.
    */
    public Player(){
        level = INITIAL_LEVEL; 
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
            Scanner scanner = GameEngine.getStdInScanner();
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
    
    public void resetDamage(){
        setDamage(INITIAL_DAMAGE + level);
    }
    
    public void levelUp(){
        level+=1;
    }
    
    /**
     * Undoes the previous move by performing the opposite update
     * on the players positon to what was initially peformed
     * @param action The initial action which resulted in an invalid position and the
     */
    public void undoMove(String action){
        switch (action){
            case NORTH:
                moveSouth();
                break;
            case SOUTH:
                moveNorth();
                break;
            case EAST:
                moveWest();
                break;
            case WEST:
                moveEast();
                break;
            default:
                System.out.println("Internal Error: Code called UndowMove with invalid action");
                System.exit(0);   
                //throw new Exception("Internal Error: Code called UndowMove with invalid action");
        }
    }
    
    @Override
    public String toString()  {
        return getName()+" "+getLevel();
    }
    
    /**
     * Logic for facilitating the saving of player data to default file location
     * @throws NoPlayerException If no player exists in the game to be saved
     * @throws FileNotFoundException If method fails to create the file, or open if already exists.
     */
    public void save() throws NoPlayerException, FileNotFoundException {
        if (getName()!=null){
            try {
                PrintWriter outputStream = new PrintWriter(new FileOutputStream(SAVE_FILE_NAME));
                outputStream.print(this); // will use custome toString() method
                outputStream.close();
            }
            finally {}
        }
        else {
            throw new NoPlayerException(); //("Cannot save player data as no player was found!");
        }
    }
    
    /**
     * method for reading in player data stored in the default file location.
     */
    public void load() {
        Scanner inputStream=null;;
        try {
            inputStream = new Scanner(new FileInputStream(SAVE_FILE_NAME));
            String[] playerData = inputStream.nextLine().split(" ");
            int level = Integer.parseInt(playerData[1]);
            setName(playerData[0]);
            setLevel(level);
            resetDamage();
            updateMaxHealth();
            heal();
            System.out.println("Player data loaded.");
            
        } catch (FileNotFoundException e) {
            System.out.print  ("No player data found.");
        }
        finally {
            inputStream.close();
        }
        
        
        return;
    }

    public void updateMaxHealth(){
        setMaxHealth(INITIAL_HEALTH+level*HEALTH_MULTIPLIER);
    }
    
}


/* Having items interactWwith players now.
// returns true if the item parsed is the warptoken as the game should end if in custom map!
public boolean parseItem(String effectID){
    
    switch (effectID){
        case "+":
            setHealth(getMaxHealth());
            System.out.println("Healed!");
            break;
        case "^":
            setDamage(getDamage()+1);
            System.out.println("Attack up!");
            break;
        case "@":
            levelUp();
            System.out.println("World complete! (You leveled up!)\n");
            return true;
        default:
            System.err.println("Unkown Item reciefed:" + effectID);
            break;
    }
    return false;
}
*/