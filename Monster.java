
import java.util.Scanner;

/**
 * Class for instatiating Monster objects to store information
 * about a particular monster and fascilate monster entity in game world
 * and in battle.
 * @author Ross Petridis | rpetridis@gmail.com | 1080249
 */
public class Monster extends Creature{

    private static final int INITIAL_POS_X = 4;
    private static final int INITIAL_POS_Y = 2;
    

    /**
     * monster Constructor which initialises their position.
     */
    public Monster(){
        //currently all monsters set to same initial position.
        setPosition(new Position(INITIAL_POS_X,INITIAL_POS_Y)); // starting position for monster 
        
    }

    public Monster(int x, int y, String name, int health, int damage){
        super(x,y);
        setName(name);
        setHealth(health);
        setMaxHealth(health);
        setDamage(damage);
    }
    /**
     * Displays monsters name a health for use in the main menu
     */
    public void displayNameAndHealth(){
        System.out.print("Monster: ");
        if(getName()!=null){
            displayStatus();
        } 
        else{
            System.out.println("[None]");
        }
        System.out.println("");
    }

    /** 
     * Displays the monsters status for use in the battle. Slightly different to player.
     */
    public void displayStatus(){
        System.out.print(getName() + " " + getHealth() + "/" + getMaxHealth() + "\n");
    }

    /**
     * Logic for prompting user input and assigning values to this monster's attribute.
     */
    public void createMonster(){
        // name health damage
        
        Scanner scanner = GameEngine.scanner;
        System.out.print("Monster name: ");
        setName(scanner.next());

        System.out.print("Monster health: ");
        GameEngine.validIntegerInput();
        int healthInput = scanner.nextInt();
        setMaxHealth(healthInput);
        setHealth(healthInput);

        System.out.print("Monster damage: ");
        GameEngine.validIntegerInput();
        setDamage(scanner.nextInt());
        System.out.println("Monster '" + getName() + "' created.\n");
    }

    public void render(){
        System.out.print(Character.toLowerCase(getName().charAt(0)));
    }
    
}
