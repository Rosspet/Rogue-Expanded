
import java.util.Scanner;

/**
 * Class for instatiating Monster objects to store information
 * about a particular monster and fascilate monster entity in game world
 * and in battle. This extends the Creature class.
 * @author Ross Petridis | rpetridis@gmail.com | 1080249
 */
public class Monster extends Creature{

    private static final int INITIAL_POS_X = 4;
    private static final int INITIAL_POS_Y = 2;
    public static final int VIEW_DIST = 2; // view distance for detecting player's on map.

    /**
     * default Constructor which initialises their position.
     */
    public Monster(){
        //currently all monsters set to same initial position.
        setPosition(new Position(INITIAL_POS_X,INITIAL_POS_Y)); // starting position for monster 
        
    }
    /**
     * Constructor to initialise monster according to information given in input files
     * @param x The initial x coordinate of monster on map
     * @param y The initial y coordinate of monster on map 
     * @param name The name of the monster
     * @param health The initial health of the monster
     * @param damage The damage the monster can dish out
     */
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
     * Used for monsters within the default world
     */
    public void createMonster(){
        // name health damage
        
        Scanner scanner = GameEngine.getStdInScanner();
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
    
    /**
     * Method for rendering monsters
     */
    public void render(){
        System.out.print(Character.toLowerCase(getName().charAt(0)));
    }

    /**
     * This function will move the monster towards the player on the map. The function contains the logic for simple monster AI.
     * @param playerPos The player to move towards
     * @param map The map to move around in. Contains information about whether attempted traversal will be valid or not.
     */
    public void makeMove(Position playerPos, Map map){

        Position monsterPos = getPosition();
        if (Position.cellDistance(playerPos, monsterPos)<=Monster.VIEW_DIST){

            int monsterX = monsterPos.getX();
            int playerX = playerPos.getX();

            // try move in x first - but only if land is traverisble
            if (playerX<monsterX){
                moveWest();
                if (!map.isValidPosition(getPosition())){
                    moveEast();
                }
                else { 
                    return; //position is valid and in correct direction!
                }
            }
            else if (playerX>monsterX) {
                moveEast();
                if (!map.isValidPosition(getPosition())){
                    moveWest();
                }
                else {
                    return;
                } 
            }

            // still trying to move... try y direction
            int monsterY = monsterPos.getY();
            int playerY = playerPos.getY();

            if (playerY>monsterY){
                moveSouth();
                if (!map.isValidPosition(getPosition())){
                    moveNorth();
                }
                else {
                    return;
                }
            }
            else if (playerY<monsterY) {
                moveNorth();
                if (!map.isValidPosition(getPosition())){
                    moveSouth();
                }
                else {
                    return;
                }
            }
        }
        return;
    }
    
    /**
     * Re initialises monster position. Used between default games.
     */
    public void resetPosition(){
        setPosition(INITIAL_POS_X,INITIAL_POS_Y);
    }
}
