/**
 * Custom Exeption for when trying to load a level that does not exist.
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 */
public class GameLevelNotFoundException extends Exception{
    public GameLevelNotFoundException(){
        super("Map not found.\n");
    }
    public GameLevelNotFoundException(String message){
        super(message);
    }

}