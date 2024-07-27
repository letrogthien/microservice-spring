package gin.authservice.exception;

public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist() {
        super("User Already Exist");
    }
}
