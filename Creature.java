
public abstract class Creature extends Entity implements Fightable {
    // all creatures (player+monster) have these
    private String name;
    private int health;
    private int maxHealth;
    private int damage;
    
    public Creature(
        Position pos, 
        String name, 
        int health, 
        int maxHealth, 
        int damage
        ){

        super(pos);
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.damage = damage;
        
    }
    public Creature(int x, int y){
        super(x, y);
    }

    // not really used because we intialise a monster to none.
    public Creature(){
        this(new Position(), null , 1, 1, 0);
    }
    
    // Implement Fightable
    /**
     * Heal creature to max health
     */
    public void heal(){
        health=maxHealth;
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

    public int getMaxHealth(){
        return maxHealth;
    }

    public int getHealth(){
        return health;
    }

    public String getName(){
        return name;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
    }

    public void setHealth(int health){
        this.health = health;
    }

    

}
