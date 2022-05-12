/**
 * Interface creatures will implement since all subcreatures (Monster's or
 * PLayer's can fight!)
 * 
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 */
public interface Fightable {

    public void heal();

    public boolean hurt(int damage); // originally returns True/talse is alive/dead

    public int getHealth();

    public int getMaxHealth();

    public int getDamage();

    public void displayStatus();

}