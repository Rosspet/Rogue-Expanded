/**
 * This class contains logic and information necessary to fasciliate the
 * Battle scene in which a player and monster attack each other
 * until one dies.
 * @author Ross Petridis | Rpetridis@student.unimelb.edu.au | 1080249
 *
 */
public class Battle {

    private Player player;
    private Monster monster;

    /**
	 * Battle Class constructor for instatiating a battle between a Monster and a Player
	 * @param player Player object to fight against the monster
	 * @param monster Monster object to fight agaisnt the player.
	 */
    public Battle(Player player, Monster monster){
        this.player = player;
        this.monster = monster;
    }

	/**
	 * Logic for running the battle scene. The player and monster takes turns
	 * exchanging attacks until either dies. // returns true if player dead, false if monster dead
	 */
	public boolean runBattleScene(){

		System.out.println(player.getName() + " encountered a " + monster.getName() + "!\n");
		boolean monsterDead=false, playerDead=false;
		
		while(true) { // while neither are dead yet.
			displayStatuses();

			// player attack
			displayAction(player.getName(), monster.getName(), player.getDamage());
			monsterDead = monster.hurt(player.getDamage());
			if(monsterDead){
				displayWinner(player.getName());
				return false;
			}

			// monster attack.
			displayAction(monster.getName(), player.getName(), monster.getDamage());
			playerDead = player.hurt(monster.getDamage()); 
			if (playerDead){
				displayWinner(monster.getName());
				return true;
			}
			System.out.println("");
		} 
	}

	/**
	 * Outputs winning information.
	 * @param name a String of the name of the winner
	 */
    private void displayWinner(String name){
		System.out.println(name+" wins!\n");
	}

	/**
	 * Display text information for the move made by a player or monster.
	 * @param attacker The attacker's name (String)
	 * @param victim The victim's name (String)
	 * @param damage The damage being dealt (Int)
	 */
	private void displayAction(String attacker, String victim, int damage){
		System.out.println(attacker+" attacks "+victim+" for "+damage+" damage.");
	}

	/**
	 * Displays status of player and monster between attacks.
	 */
	private void displayStatuses(){
		player.displayStatus();
        System.out.print(" | ");
		monster.displayStatus();
	}
}
