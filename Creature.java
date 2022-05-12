/**
 * This class extends the Entity class and is designed to hold the methods for
 * movable entities, "creatures" i the map world.
 * Where these creatures are also capable of fighting with each other, hence the
 * implementation of the Fightable interface.
 * 
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 */
public abstract class Creature extends Entity implements Fightable {
    // all creatures (player+monster) have these
    private String name = null;
    private int health;
    private int maxHealth;
    private int damage;

    /**
     * Creature constructor for when reading from the world file
     * 
     * @param pos       Position of this creature
     * @param name      Name of this creature
     * @param health    Initial health of this creature
     * @param maxHealth Max health of this creature
     * @param damage    Damage this creature can deal to others
     */
    public Creature(
            Position pos,
            String name,
            int health,
            int maxHealth,
            int damage) {

        super(pos);
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.damage = damage;

    }

    /**
     * Constructor used to create creature for default world gameplay
     * 
     * @param x
     * @param y
     */
    public Creature(int x, int y) {
        super(x, y);
    }

    // Default creature constructur
    public Creature() {
        // Call another constructor in this class with following default values.
        this(new Position(), null, 1, 1, 1);
    }

    // Implement Fightable
    /**
     * Heal creature to max health
     */
    public void heal() {
        health = maxHealth;
    }

    /**
     * Decrease the health of this monster by "damage" amount
     * 
     * @param damage The amount to reduce the health by
     * @return Returns True if the monster is dead
     */
    public boolean hurt(int damage) {

        health -= damage;
        if (health <= 0) {
            return true;// monster is dead
        }
        return false; // not dead
    }

    /**
     * Returns the damage this monster can deal
     */
    public int getDamage() {
        return damage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
