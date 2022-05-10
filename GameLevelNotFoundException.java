public class GameLevelNotFoundException extends Exception{
    public GameLevelNotFoundException(){
        super("Game level not found");
    }
    public GameLevelNotFoundException(String message){
        super(message);
    }

}
