import java.util.Scanner;

/**
 * Class for instatiating Monster objects to store information
 * about a particular monster and fascilate monster entity in game world
 * and in battle.
 * @author Ross Petridis | rpetridis@gmail.com | 1080249
 */
public class Monster {

    private static final int INITIAL_POS_X = 4;
    private static final int INITIAL_POS_Y = 2;
    
    private String name;
    private int health;
    private int maxHealth;
    private int damage;
    private Position position;

    /**
     * monster Constructor which initialises their position.
     */
    public Monster(){
        //currently all monsters set to same initial position.
        position = new Position(INITIAL_POS_X,INITIAL_POS_Y); // starting position for monster 
        
    }

    /**
     * Displays monsters name a health for use in the main menu
     */
    public void displayNameAndHealth(){
        System.out.print("Monster: ");
        if(name!=null){
            displayStatus();
        } 
        else{
            System.out.println("[None]");
        }
        System.out.println("");
    }

    /** 
     * Displays the monsters status for use in the battle
     */
    public void displayStatus(){
        System.out.print(name + " " + health + "/" + maxHealth + "\n");
    }

    /**
     * Logic for prompting user input and assigning values to this monster's attribute.
     */
    public void createMonster(){
        // name health damage
        
        Scanner scanner = GameEngine.scanner;
        System.out.print("Monster name: ");
        name = scanner.next();

        System.out.print("Monster health: ");
        GameEngine.validIntegerInput();
        maxHealth = scanner.nextInt();
        health=maxHealth;

        System.out.print("Monster damage: ");
        GameEngine.validIntegerInput();
        damage = scanner.nextInt();
        System.out.println("Monster '" + name + "' created.\n");
    }

    /**
     * Return monsters name
     * @return The monsters name (String)
     */
    public String getName(){
        return name;
    }
    
    /**
     * Heal monster to maxHealth
     */
    public void heal(){
        health=maxHealth;
    }
    
    /**
     * Return a copy of this monsters position
     */
    public Position getPosition(){
        //retuirn a copy to ensure proper safe encapsulation. So other classes cant direcctly access value
        return new Position(position); 
    }
    
    /**
     * Decrease the health of this monster by "damage" amount
     * @param damage The amount to reduce the health by
     * @return Returns True if the monster is dead
     */
    public boolean hurt(int damage){
        
        health-=damage;
        if (health<=0){
            return true;//monster is dead
        }
        return false; // not dead
    }

    /**
     * Returns the damage this monster can deal
     */
    public int getDamage(){
        return damage;
    }
}
