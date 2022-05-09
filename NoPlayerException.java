public class NoPlayerException extends Exception {
    
    public NoPlayerException(){
        super("Player does not exist!");
    }

    public NoPlayerException(String message){
        super(message);
    }

}
