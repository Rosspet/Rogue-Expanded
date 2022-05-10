import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class includes the Rogue game's engine and high level logic for instatiating 
 * important objects and calling high level methods to fascilate gameplay.
 * @author Ross Petridis | Rpetridis@student.unimelb.edu.au | 1080249
 */
public class GameEngine {

	private Player player = new Player(); 
	private Monster monster = new Monster();
	//private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private static String fileName;
	/** scanner object for the game to read from System.in */
	public static Scanner scanner = new Scanner(System.in);
	public static Scanner inputStream;
	public static final String FILE_EXTENSION = ".dat";
	public static final String CMD_PROMPT = "> ";
	private static boolean defaultMap;
	public static void main(String[] args) {
		GameEngine gameEngine = new GameEngine();
		gameEngine.runGameLoop();
		scanner.close();
	}

	/**
	 * Returns only once valid integer input has been recieved
	 */
	public static void validIntegerInput(){
		while (!scanner.hasNextInt()){
            System.out.println("Please enter an integer number");
            String invalidInput = scanner.next();
        }
	}
	
	public static boolean getGameMode(){
		return defaultMap;
	}


	/*
	 *  Logic for running the main game loop.
	 */
	private void runGameLoop() {
		
		displayMenu();
		String command;
		String[] cmd_args;
		
		do {
			if (scanner.hasNextLine()){
				 // has to be .next() otherwise doesnt pass tests. but now doesnt run the map from file properly
				cmd_args = scanner.nextLine().trim().split(" ");
				parseCommand(cmd_args);
			} else {
				cmd_args = scanner.next().trim().split(" ");
			}
			
				
		} while (!cmd_args[0].equals("exit"));
		
		// end of program!
	}

	/**
	 * Logic for handling different input commands and actioning the commands
	 * @param command The inputted command from stdin.
	 */
	private void parseCommand(String[] cmd_args){
		//String[] cmd_args = command.split(" ");
		//String worldName=null;
		
		//System.out.println(cmd_args.length);
		String base_cmd = cmd_args[0];
		switch (base_cmd) {
			case "player":
				player.createPlayer();
				returnToMain(); 
				break;
			case "help":
				System.out.println("Type 'commands' to list all available commands");
				System.out.println("Type 'start' to start a new game");
				System.out.print("Create a character, battle monsters, and find treasure!\n\n"+CMD_PROMPT);
				break;
			case "commands":
				System.out.print("help\nplayer\nmonster\nstart\nexit\n\n"+CMD_PROMPT);
				break;
			case "exit":
				System.out.println("Thank you for playing Rogue!");
				break;
			case "monster":
				monster.createMonster();
				returnToMain();
				break;
			case "load":
				player.load();
				//clearNewLine();
				System.out.print("\n"+CMD_PROMPT);
				break;
			case "save":
				try{
					player.save();
					System.out.println("Player data saved.");
				}
				catch (NoPlayerException e) {
					System.out.println("No player data to save.");
				}
				catch (FileNotFoundException e){
					System.out.println("File could not be created. Error: "+e.getMessage());
				} 
				returnToMain();
				break;
			case "start":
				if (player.getName()==null){
					System.out.println("No player found, please create a player with 'player' first.\n");
					returnToMain();
					break;
				}

				/*if (scanner.hasNext()){
					worldName = scanner.nextLine().trim(); 
				}*/
				
				if (cmd_args.length<=1){ //
					// start default game.
					if (monster.getName()==null){
						System.out.println("No monster found, please create a monster with 'monster' first.\n");
						//System.out.flush();
						returnToMain();
						break;
					}
					else {
						// (default map)  - 1 player, 1 monster
						//entities.clear();
						player.heal(); //TODO add 4 lines into 2.
						monster.heal();
						//entities.add(player);
						//entities.add(monster);
						defaultMap = true;
						//startGame(entities);
						startGame();
						break;
					}
				}
				else {
					// have player and loading rest from file. 
					//Start was entered with file name. 
					//entities.clear();
					//String fileName = cmd_args[1]; // check for valid file - raise appropriate exception if not.
					// set up fileStream here too and use this in start Game.
					try {
						loadMapFromFile(cmd_args[1]+FILE_EXTENSION);
					}
					catch (GameLevelNotFoundException e){
						System.out.println("Map not found.\n");
						returnToMain();
					}
					break;
				}
				
			default:
				System.out.print("Invalid command entered. Type 'commands' to see valid commands and try again.\n"+CMD_PROMPT);
				break;
		}

	}

	private void loadMapFromFile(String fileName) throws GameLevelNotFoundException {
		try {
			inputStream = new Scanner(new FileInputStream(fileName));
		}
		catch (FileNotFoundException e){ // update to try again with new file name.
			throw new GameLevelNotFoundException();
		}

		player.heal();
		//entities.add(player);
		defaultMap = false;
		// PARSE FILE HERE, get entities 
		//startGame(entities);
		startGame();
	}

	/**
	 * Engine logic for starting the game. Creates a world and begins the search 
	 * scene until the player and monster have encounterd each other. At this 
	 * point, the battle scene is instatiated and run.
	 */
	private void startGame(){
		World world = defaultMap ? new World(player, monster) : new World(player, inputStream); // change to file Stream!!!
		//World world = new World(player, monster); // make a variable of GameEngine.
		boolean encountered = world.runSearchScene();
		
		if (encountered){
			//Battle battle = new Battle(player, monster);
			//battle.runBattleScene();	
			//NEW ...
			//level up player etc.	
		}
		else {
			System.out.println("> Returning home...\n");
		}
		returnToMain();
		world.reset(); // assuming no save of positions at this stage as nothing in specification re keeping positions if returning home or finished game.
	}


	/**
	 * Called when its time to return to main. Awaits for enter input before 
	 * calling a method to display Menu graphics.
	 */
	private void returnToMain(){
		System.out.print("(Press enter key to return to main menu)\n");
		String cmd = scanner.nextLine();
		cmd = scanner.nextLine();
		// for some reason i need 2 of these for it to wait for player to press enter. 
		// But with or without the player waiting for enter, it still passes all tests which is misleading.
		displayMenu();		 
	}
	/**
	 * Method for diplsaying Menu graphics including health and names.
	 */
	private void displayMenu(){
		displayTitleText();
		player.displayNameAndHealth();
		System.out.print("  | ");
		monster.displayNameAndHealth();
		System.out.println("Please enter a command to continue.");
		System.out.print("Type 'help' to learn how to get started.\n\n"+CMD_PROMPT);

	}
	
	/*
	 *  Displays the title text.
	 */
	private void displayTitleText() {
		
		String titleText = " ____                        \n" + 
				"|  _ \\ ___   __ _ _   _  ___ \n" + 
				"| |_) / _ \\ / _` | | | |/ _ \\\n" + 
				"|  _ < (_) | (_| | |_| |  __/\n" + 
				"|_| \\_\\___/ \\__, |\\__,_|\\___|\n" + 
				"COMP90041   |___/ Assignment ";
		
		System.out.println(titleText);
		System.out.println();

	}

	public static String getFileName(){
		return fileName;
	}
	
	public void clearNewLine(){
		//String cmd = scanner.nextLine();
		//cmd = scanner.nextLine();
	}
	

}