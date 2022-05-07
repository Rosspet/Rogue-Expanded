
/**
 * Interface creatures will implement since all subcreatures (Monster's or PLayer's can fight!)
 */
public interface Fightable {

    public void heal();
    public boolean hurt(int damage); // originally returns True/talse is alive/dead

    public int getHealth();
    public int getMaxHealth();
    public int getDamage();
    public void displayStatus();

}