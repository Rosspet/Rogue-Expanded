/**
 * Custom exception for catching error occuring when trying peform action with
 * player that does not exist.
 * @author Ross Petridis | rpetridis@student.unimelb.edu.au | 1080249
 */
public class NoPlayerException extends Exception {

    public NoPlayerException() {
        super("Player does not exist!");
    }

    public NoPlayerException(String message) {
        super(message);
    }

}
