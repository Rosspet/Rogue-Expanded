import java.io.FileNotFoundException;

public class GameLevelNotFoundException extends Exception{
    public GameLevelNotFoundException(){
        super("Game level not found");
    }
}
