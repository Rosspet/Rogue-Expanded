/**
 * This Class etends the Entity class and is used to store logic and information
 * about items on the map which the player can interact with
 */
public class Item extends Entity {

    private String effectID;

    /**
     * Item Constructor
     * 
     * @param position The Position of the item on the map
     * @param effectID The ID type of the item
     */
    public Item(Position position, String effectID) {
        super(position);
        this.effectID = effectID;
    }

    /**
     * Item constructor
     * 
     * @param x      The x coordinate of the item on the map
     * @param y      The y coordinate of the item on the map
     * @param itemID The ID type of the item
     */
    public Item(int x, int y, String itemID) {
        super(x, y);
        this.effectID = itemID;
    }

    /**
     * Render method for this item
     */
    public void render() {
        System.out.print(effectID);
    }

    /**
     * 
     * @return the effect id (String)
     */
    public String getID() {
        return effectID;
    }

    /**
     * 
     * @param player The player the item is going to interact with
     * @return True if the interaction results in termination of the level. Else,
     *         false.
     */
    public boolean interactWith(Player player) {

        switch (effectID) {
            case "+":
                player.setHealth(player.getMaxHealth());
                System.out.println("Healed!");
                break;
            case "^":
                player.setDamage(player.getDamage() + 1);
                System.out.println("Attack up!");
                break;
            case "@":
                player.levelUp();
                System.out.println("World complete! (You leveled up!)\n");
                return true;
            default:
                System.err.println("Unkown Item reciefed:" + effectID);
                break;
        }
        return false;
    }

}
