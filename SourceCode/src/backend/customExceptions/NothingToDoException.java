package backend.customExceptions;

public class NothingToDoException extends RuntimeException{
    public NothingToDoException(String message){
        super(message);
    }
}
