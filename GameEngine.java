import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class includes the Rogue-Expanded game engine and high level logic for
 * instatiating
 * important objects and calling high level methods to fascilate gameplay.
 * 
 * @author Ross Petridis | Rpetridis@student.unimelb.edu.au | 1080249
 */
public class GameEngine {

	private Player player = new Player();
	private Monster monster = new Monster();
	private boolean defaultMap;

	/** scanner object for the game to read from System.in */
	private static Scanner scanner = new Scanner(System.in);
	private Scanner inputStream;
	private String fileName;
	public static final String FILE_EXTENSION = ".dat";
	public static final String CMD_PROMPT = "> ";

	public static void main(String[] args) {
		GameEngine gameEngine = new GameEngine();
		gameEngine.runGame();
		scanner.close();
	}

	/*
	 * Logic for running the main game loop. Mostly Fascilitates the main menu in
	 * side of helper function "parseCommand"
	 */
	private void runGame() {

		displayMenu();

		String[] cmd_args = null;
		// Do main menu.
		do {
			if (scanner.hasNextLine()) {
				cmd_args = scanner.nextLine().trim().split(" ");
			}
			if (cmd_args[0] != "") {
				parseCommand(cmd_args);
			}
		} while (!cmd_args[0].equals("exit"));

	}

	/**
	 * Logic for handling different input commands and actioning the commands by
	 * calling appriopriate methods
	 * 
	 * @param cmd_args The inputted command line args from stdin.
	 */
	private void parseCommand(String[] cmd_args) {

		String base_cmd = cmd_args[0];
		switch (base_cmd) {
			case "player":
				player.createPlayer();
				returnToMain();
				break;
			case "help":
				System.out.println("Type 'commands' to list all available commands");
				System.out.println("Type 'start' to start a new game");
				System.out.print("Create a character, battle monsters, and find treasure!\n\n" + CMD_PROMPT);
				break;
			case "commands":
				System.out.print("help\nplayer\nmonster\nstart\nexit\n\n" + CMD_PROMPT);
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
				System.out.print("\n\n" + CMD_PROMPT);
				break;
			case "save":
				try { // try save player
					player.save();
					System.out.println("Player data saved.");
				} catch (NoPlayerException e) {
					System.out.println("No player data to save.");
				} catch (FileNotFoundException e) {
					System.out.println("File could not be created. Error: " + e.getMessage());
				}
				System.out.print("\n\n" + CMD_PROMPT);
				//returnToMain();
				break;
			case "start":
				if (player.getName() == null) {
					System.out.println("No player found, please create a player with 'player' first.\n");
					returnToMain();
					break;
				}

				if (startingDefualt(cmd_args)) { //
					// start default game.
					if (monster.getName() == null) {
						System.out.println("No monster found, please create a monster with 'monster' first.\n");
						returnToMain();
						break;
					} else { // start default game!
						player.heal();
						monster.heal();
						defaultMap = true; // do we actually use this variable inside of world?
						startGame();
						break;
					}
				} else {
					// have player and loading rest from file.
					try {
						startFromFile(cmd_args[1] + FILE_EXTENSION);
					} catch (GameLevelNotFoundException e) {
						//System.out.println("Map not found.\n");
						System.out.println(e.getMessage());
						returnToMain();
					}
					break;
				}
			default:
				System.out.print(
						"Invalid command entered. Type 'commands' to see valid commands and try again.\n" + CMD_PROMPT);
				break;
		}

	}

	/**
	 * Start game fro input file name
	 * 
	 * @param fileName The file name containing the level
	 * @throws GameLevelNotFoundException if the Game file cannot be found.
	 */
	private void startFromFile(String fileName) throws GameLevelNotFoundException {
		try {
			inputStream = new Scanner(new FileInputStream(fileName));
		} catch (FileNotFoundException e) { // update to try again with new file name.
			throw new GameLevelNotFoundException();
		}

		player.heal();
		defaultMap = false;
		startGame();
	}

	/**
	 * Engine logic for starting the game. Creates a world and begins the search
	 * scene until the player and monster have encounterd each other. At this
	 * point, the battle scene is instatiated and run.
	 */
	private void startGame() {

		// Create world according to word type. (default world or from file)
		World world = defaultMap ? new World(player, monster) : new World(player, inputStream);
		boolean returnedHome = world.runGameLoop();

		if (returnedHome) {
			System.out.println("Returning home...\n");
		}
		world.reset(); // dont heal player until re starting. But update the level here (and new maxHealth)
		returnToMain();
		// game over!
	}

	/**
	 * Called when its time to return to main. Awaits for enter input before
	 * calling a method to display Menu graphics.
	 */
	private void returnToMain() {
		System.out.print("(Press enter key to return to main menu)\n");
		scanner.nextLine();
		// scanner.nextLine();
		displayMenu();
	}

	/**
	 * Method for diplsaying Menu graphics including health and names.
	 */
	private void displayMenu() {
		displayTitleText();
		player.displayNameAndHealth();
		System.out.print("  | ");
		monster.displayNameAndHealth();
		System.out.println("Please enter a command to continue.");
		System.out.print("Type 'help' to learn how to get started.\n\n" + CMD_PROMPT);

	}

	/*
	 * Displays the title text.
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

	public String getFileName() {
		return fileName;
	}

	public static Scanner getStdInScanner() {
		return scanner;
	}

	public Scanner getInputStreamScanner() {
		return inputStream;
	}

	/**
	 * Returns only once valid integer input has been recieved
	 */
	public static void validIntegerInput() {
		while (!scanner.hasNextInt()) {
			System.out.println("Please enter an integer number");
			scanner.next();
		}
	}

	/**
	 * 
	 * @return true if game mode is default
	 */
	public boolean getGameMode() {
		return defaultMap;
	}

	private boolean startingDefualt(String[] cmd_args) {
		return (cmd_args.length <= 1);
	}

}

/*
 * else if (scanner.hasNext()){
 * cmd_args = scanner.next().trim().split(" ");
 * }
 */