package gin.authservice.exception;

public class UserNotFoundEx extends  RuntimeException{
    public UserNotFoundEx(String message) {
        super(message);
    }
}
