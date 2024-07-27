package gin.authservice.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super(
                "UNAUTHORIZED TOKEN"
        );
    }
}
